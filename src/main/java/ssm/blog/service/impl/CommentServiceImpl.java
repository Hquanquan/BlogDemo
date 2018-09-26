package ssm.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssm.blog.dao.CommentDao;
import ssm.blog.service.CommentService;

@Service("commentService")
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private CommentDao commentDao;

	@Override
	public Integer deleteCommentByBlogId(Integer blogId) {
		return commentDao.deleteCommentByBlogId(blogId);
	}

}
