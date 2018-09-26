package ssm.blog.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ssm.blog.entity.Blog;
import ssm.blog.entity.PageBean;
import ssm.blog.lucene.BlogIndex;
import ssm.blog.service.BlogService;
import ssm.blog.service.CommentService;
import ssm.blog.util.DateJsonValueProcessor;
import ssm.blog.util.ResponseUtil;
import ssm.blog.util.StringUtil;


@Controller
@RequestMapping("/admin/blog")
public class BlogAdminController {
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CommentService commentService;
	
	private BlogIndex blogIndex=new BlogIndex();
	

	/**通过id获取博客实体
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findById")
	public String findById(
			@RequestParam(value="id", required=false)String id,
			HttpServletResponse response) throws Exception {
		System.out.println("findById");
		Blog blog = blogService.findById(Integer.parseInt(id));
		JSONObject result = JSONObject.fromObject(blog);
		ResponseUtil.write(response, result);
		return null;
	}
	
	
	/**  新增和修改blog博客
	 * @param blog
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(Blog blog, HttpServletResponse response) throws Exception {
		//接收返回结果记录数
		int resultTotal=0;
		if(blog.getId() == null) { //说明是第一次插入
			resultTotal = blogService.addBlog(blog);
			blogIndex.addIndex(blog); //添加博客的索引
		} else { //有id表示修改
			resultTotal = blogService.update(blog);
			blogIndex.updateIndex(blog);
		}
		JSONObject result = new JSONObject();
		if(resultTotal > 0) {
			result.put("success", true);
		} else {
			result.put("success", false);
		}
		ResponseUtil.write(response, result);
		return null;
	}
	
	
	
	/**后台分页查询博客信息
	 * @param page
	 * @param rpws
	 * @param s_blog
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listBlog")
	public String listBlog(@RequestParam(value="page",required=false)String page,
						   @RequestParam(value="rows",required=false)String rpws,
			  Blog s_blog, HttpServletResponse response)throws Exception{
		
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rpws));
		Map<String,Object> map = new HashMap<String, Object>();
//		模糊查询
		map.put("title", StringUtil.formatLike(s_blog.getTitle()));
		map.put("start", pageBean.getStart());
		map.put("pageSize", pageBean.getPageSize());
		
		List<Blog> blogList=blogService.listBlog(map);
		Long total=blogService.getTotal(map);
		
		JSONObject result = new JSONObject();
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray jsonArray=JSONArray.fromObject(blogList,jsonConfig);
		result.put("rows",jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
		return null;
	}
	/**博客信息删除
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("/delete")
	public String deleteBlog(
			@RequestParam(value="ids", required=false)String ids,
			HttpServletResponse response) throws Exception {
		
		System.out.println("deleteBlog");
		
		
		String[] idsStr = ids.split(",");
		for(int i = 0; i < idsStr.length; i++) {
			int id = Integer.parseInt(idsStr[i]);
			commentService.deleteCommentByBlogId(id);
			blogService.deleteBlog(id);
			blogIndex.deleteIndex(idsStr[i]);
		}
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		
		
		return null;
		
		
	}
	
}	
	
	

