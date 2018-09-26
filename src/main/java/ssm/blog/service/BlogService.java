package ssm.blog.service;

import ssm.blog.entity.Blog;

public interface BlogService {

	
	// 根据博客类型的id查询该类型下的博客数量
	public Integer getBlogByTypeId(Integer typeId);

	public Integer addBlog(Blog blog);

	public Integer update(Blog blog);

	// 根据id获取博客
	public Blog findById(Integer id);


}
