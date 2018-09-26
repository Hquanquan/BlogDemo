package ssm.blog.dao;

import ssm.blog.entity.Blog;

public interface BlogDao {

	
	public Integer getBlogByTypeId(Integer typeId);

	// 添加博客
	public Integer addBlog(Blog blog);

	// 更新博客信息
	public Integer update(Blog blog);
	

	
	

}
