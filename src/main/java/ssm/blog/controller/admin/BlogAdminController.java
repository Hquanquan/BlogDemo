package ssm.blog.controller.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ssm.blog.entity.Blog;
import ssm.blog.lucene.BlogIndex;
import ssm.blog.service.BlogService;
import ssm.blog.util.ResponseUtil;


@Controller
@RequestMapping("/admin/blog")
public class BlogAdminController {
	
	@Autowired
	private BlogService blogService;
	
	private BlogIndex blogIndex=new BlogIndex();
	

	//通过id获取博客实体
	@RequestMapping("/findById")
	public String findById(
			@RequestParam(value="id", required=false)String id,
			HttpServletResponse response) throws Exception {
		
		Blog blog = blogService.findById(Integer.parseInt(id));
		JSONObject result = JSONObject.fromObject(blog);
		ResponseUtil.write(response, result);
		return null;
	}
	
	@RequestMapping("/save")
	public String save(Blog blog, HttpServletResponse response) throws Exception {
		System.out.println("save");
		//接收返回结果记录数
		int resultTotal=0;
		if(blog.getId() == null) { //说明是第一次插入
			System.out.println("================>save");
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
	
	
	/*
	@RequestMapping("/save")
	public String save(Blog blog, HttpServletResponse response) throws Exception {
		System.out.println("save");
		int resultTotal = 0; 
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
