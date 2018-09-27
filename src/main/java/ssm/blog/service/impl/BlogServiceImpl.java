package ssm.blog.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssm.blog.dao.BlogDao;
import ssm.blog.entity.Blog;
import ssm.blog.service.BlogService;

@Service("blogService")
public class BlogServiceImpl implements BlogService{
	
	@Autowired
	private BlogDao blogDao;

	@Override
	public Integer getBlogByTypeId(Integer typeId) {
		return blogDao.getBlogByTypeId(typeId);
	}

	@Override
	public Integer addBlog(Blog blog) {
		return blogDao.addBlog(blog);
	}

	@Override
	public Integer update(Blog blog) {
		return blogDao.update(blog);
	}

	@Override
	public Blog findById(Integer id) {
		return blogDao.findById(id);
	}
	
	@Override
	public List<Blog> listBlog(Map<String, Object> map) {
		return blogDao.listBlog(map);
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		return blogDao.getTotal(map);
	}

	@Override
	public Integer deleteBlog(Integer id) {
		return blogDao.deleteBlog(id);
	}

	@Override
	public List<Blog> getBlogData() {
		return blogDao.getBlogData();
	}

	// 获取上前一篇博客信息
	@Override
	public Blog getPrevBlog(Integer id) {
		return blogDao.getPrevBlog(id);
	}

	@Override
	public Blog getNextBlog(Integer id) {
		return blogDao.getNextBlog(id);
	}

}
