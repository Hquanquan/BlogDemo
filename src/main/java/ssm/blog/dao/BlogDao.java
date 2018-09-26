package ssm.blog.dao;

import java.util.List;
import java.util.Map;

import ssm.blog.entity.Blog;

public interface BlogDao {

	
	public Integer getBlogByTypeId(Integer typeId);

	// 添加博客
	public Integer addBlog(Blog blog);

	// 更新博客信息
	public Integer update(Blog blog);

	public Blog findById(Integer id);

	public List<Blog> listBlog(Map<String, Object> map);

	public Long getTotal(Map<String, Object> map);
	

	
	

}
