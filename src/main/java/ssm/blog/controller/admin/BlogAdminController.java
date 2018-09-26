package ssm.blog.controller.admin;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ssm.blog.entity.Blog;
import ssm.blog.lucene.BlogIndex;
import ssm.blog.service.BlogService;
import ssm.blog.util.ResponseUtil;


@Controller
@RequestMapping("/admin/blog")
public class BlogAdminController {
	
	@Autowired
	private BlogService blogService;
	
	private BlogIndex blogIndex;
	
	
	public String save(Blog blog,HttpServletResponse response) throws Exception{
		System.out.println("save");
		
		//声明一个返回结果记录数的变量
		int resultTotal=0;
		//判断blogId是否为空，空则说明是第一次插入，不为空即有ID,是修改
		if(blog.getId()==null){
//			插入blog 
			resultTotal=blogService.addBlog(blog);
			 //添加博客的索引
			blogIndex.addIndex(blog);
			
		}else{//有ID,是修改
			resultTotal=blogService.update(blog);
			//更新博客的索引
			blogIndex.updateIndex(blog);
		}
//		声明JSONObject result对象用于接收返回的信息
		JSONObject result = new JSONObject();
		
//		判断resultTotal 是否大于 0；是则说明操作成功，否则就是失败
		if(resultTotal>0){
			result.put("success", true);
		}else {
			result.put("success", false);
		}
		ResponseUtil.write(response, result);
		return null;
		
	}
	
	
	
	/*
	@RequestMapping("/save")
	public String save(Blog blog, HttpServletResponse response) throws Exception {
		System.out.println("save");
		int resultTotal = 0; //接收返回结果记录数
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
*/
}
