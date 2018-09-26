package ssm.blog.service;

import java.util.List;
import java.util.Map;

import ssm.blog.entity.Blog;

public interface BlogService {

	
	// 根据博客类型的id查询该类型下的博客数量
	public Integer getBlogByTypeId(Integer typeId);

	public Integer addBlog(Blog blog);

	public Integer update(Blog blog);

	// 根据id获取博客
	public Blog findById(Integer id);

	public List<Blog> listBlog(Map<String, Object> map);

	public Long getTotal(Map<String, Object> map);

	// 删除博客信息
	public Integer deleteBlog(Integer id);



	


}
