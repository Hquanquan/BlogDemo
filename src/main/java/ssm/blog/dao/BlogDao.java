package ssm.blog.dao;

import java.util.List;
import java.util.Map;

import ssm.blog.entity.Blog;

public interface BlogDao {

	//通过TypeId 博客类型查询博客blog信息
	public Integer getBlogByTypeId(Integer typeId);

	// 添加博客
	public Integer addBlog(Blog blog);

	// 更新博客信息
	public Integer update(Blog blog);

	//根据id 查询博客信息
	public Blog findById(Integer id);

	//分页查询Blog信息
	public List<Blog> listBlog(Map<String, Object> map);
	
	//获取总记录数
	public Long getTotal(Map<String, Object> map);

	//根据id删除博客
	public Integer deleteBlog(Integer id);

	// 获取博客信息，按照时间分类的
	public List<Blog> getBlogData();

	//获取前一篇blog信息
	public Blog getPrevBlog(Integer id);

	// 获取后一篇博客信息
	public Blog getNextBlog(Integer id);
	

	
	

}
