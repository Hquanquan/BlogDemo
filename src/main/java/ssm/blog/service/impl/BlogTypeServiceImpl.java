package ssm.blog.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssm.blog.dao.BlogTypeDao;
import ssm.blog.entity.BlogType;
import ssm.blog.service.BlogTypeService;

@Service("blogTypeService")
public class BlogTypeServiceImpl implements BlogTypeService{
	
	@Autowired
	private BlogTypeDao blogTypeDao;

	@Override
	public List<BlogType> getBlogTypeData() {
		return blogTypeDao.getBlogTypeData();
	}

	@Override
	public List<BlogType> listBlogType(Map<String, Object> map) {
		return blogTypeDao.listBlogType(map);
	}

	
	
	@Override
	public Long getTotal(Map<String, Object> map) {
		return blogTypeDao.getTotal(map);
	}

	@Override
	public Integer addBlogType(BlogType blogType) {
		return blogTypeDao.addBlogType(blogType);
	}

	@Override
	public Integer updateBlogType(BlogType blogType) {
		return blogTypeDao.updateBlogType(blogType);
	}

	@Override
	public Integer deleteBlogType(Integer id) {
		return blogTypeDao.deleteBlogType(id);
	}

}
