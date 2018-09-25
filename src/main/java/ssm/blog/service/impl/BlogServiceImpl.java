package ssm.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssm.blog.dao.BlogDao;
import ssm.blog.service.BlogService;

@Service("blogService")
public class BlogServiceImpl implements BlogService{
	
	@Autowired
	private BlogDao blogDao;

	@Override
	public Integer getBlogByTypeId(Integer typeId) {
		return blogDao.getBlogByTypeId(typeId);
	}

}
