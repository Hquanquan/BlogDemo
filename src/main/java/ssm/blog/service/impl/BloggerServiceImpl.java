package ssm.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssm.blog.dao.BloggerDao;
import ssm.blog.entity.Blogger;
import ssm.blog.service.BloggerService;

/**
 * @author 黄权权
 *
 */
@Service("bloggerService")
public class BloggerServiceImpl implements BloggerService {
	
	@Autowired
	private BloggerDao bloggerDao;
	
	//根据用户名查询博主信息，用于登陆
	public Blogger getByUsername(String username) {
		return bloggerDao.getByUsername(username);
	}

	//获取博主信息
	public Blogger getBloggerData() {
		return bloggerDao.getBloggerData();
	}

	//更新博主密码
	public Integer updateBlogger(Blogger blogger) {
		return bloggerDao.updateBlogger(blogger);
	}

}