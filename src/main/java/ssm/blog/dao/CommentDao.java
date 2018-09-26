package ssm.blog.dao;

import java.util.List;
import java.util.Map;

import ssm.blog.entity.Comment;

public interface CommentDao {

	//	通过blogId删除评论
	public Integer deleteCommentByBlogId(Integer blogId);

	//	获取评论信息
	public List<Comment> getCommentData(Map<String, Object> map);

	//获取总记录数
	public Long getTotal(Map<String, Object> map);

	//删除评论（可批量删除）
	public Integer deleteComment(Integer id);

	//	更新评论状态（审核评论）
	public Integer update(Comment comment);

}
