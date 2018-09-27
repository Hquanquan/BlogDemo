package ssm.blog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ssm.blog.entity.Blog;
import ssm.blog.entity.PageBean;
import ssm.blog.service.BlogService;
import ssm.blog.util.PageUtil;
import ssm.blog.util.StringUtil;

@Controller
@RequestMapping("/")
public class IndexController {
	
	@Autowired
	private BlogService blogService;
	
	/**前端首页页面
	 * @param page
	 * @param typeId
	 * @param releaseDateStr
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/index")
	public ModelAndView index(
			@RequestParam(value="page",required=false)String page,
			@RequestParam(value="typeId",required=false)String typeId,
			@RequestParam(value="releaseDateStr",required=false)String releaseDateStr,
			 HttpServletRequest request)throws Exception{
//		声明一个ModelAndView modelAndView对象
		ModelAndView modelAndView = new ModelAndView();
//		判断page是否为空，若为空赋值为字符串:"1"
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		
//		声明获取分页的bean  //每页显示10条数据
		PageBean pageBean=new PageBean(Integer.parseInt(page),10);
		
//		声明一个map 集合，封装起始页和每页的记录数
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("start", pageBean.getStart());
		map.put("pageSize", pageBean.getPageSize());
		map.put("typeId", typeId);
		map.put("releaseDateStr", releaseDateStr);
		
//		调用blogService.listBlog(map)方法获取blog信息，并赋值到List集合中
		List<Blog> blogList = blogService.listBlog(map);
		
//		遍历blogList 对象
		for (Blog blog : blogList) {
//			获取blog博客对象里的图片列表
			List<String> imageList = blog.getImageList();
			
//			获取blog博客内容
			String blogInfo = blog.getContent();
			
//			将blog 博客内容（网页中也就是一些html）转为jsoup 的Document
			Document doc = Jsoup.parse(blogInfo);
			
			//获取<img>标签中所有后缀是.jpg的元素
			Elements jpgs = doc.select("img[src$=.jpg]");
			
//			遍历jpgs
			for (int i = 0; i < jpgs.size(); i++) {
//				获取到单个元素
				Element jpg = jpgs.get(i);
//				把图片信息保存到imageList中
				imageList.add(jpg.toString());
				if(i==2){
					//只存三张图片信息
					break;
				}
			}
		}
		
//		声明一个StringBuffer 对象 param ,用来拼装参数
		StringBuffer param =new StringBuffer();
//		拼接参数，主要对于点击文章分类或者日期分类后，查出来的分页，要拼接具体的参数
//		typeId不为空就拼接到param
		if(StringUtil.isNotEmpty(typeId)){
			param.append("typeId="+typeId+"&");;
		}
//		releaseDateStr不为空就拼接到param
		if(StringUtil.isNotEmpty(releaseDateStr)){
			param.append("releaseDateStr"+releaseDateStr+"&");
		}
		modelAndView.addObject("pageCode", 
				PageUtil.genPagination( //调用代码生成的工具类生成前台显示
				request.getContextPath() + "/index.html", //还是请求该controller的index方法
				blogService.getTotal(map), 
				Integer.parseInt(page), 10,
				param.toString()));
//		添加属性对象到modelAndView
		modelAndView.addObject("blogList", blogList);
		modelAndView.addObject("commonPage", "foreground/blog/blogList.jsp");
		modelAndView.addObject("title", "博客主页 - 个人博客");
		modelAndView.setViewName("mainTemp");
		
		return modelAndView;
	}

}
