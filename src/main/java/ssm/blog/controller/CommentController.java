package ssm.blog.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ssm.blog.entity.Blog;
import ssm.blog.entity.Comment;
import ssm.blog.service.BlogService;
import ssm.blog.service.CommentService;
import ssm.blog.util.ResponseUtil;

@Controller
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;
	@Autowired
	private BlogService blogService;
	
	/**
	 * @param comment：评论信息
	 * @param imageCode：前台传来的验证码
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(Comment comment,
			@RequestParam("imageCode")String imageCode,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session)throws Exception{
		//获取session中正确的验证码，验证码产生后会存到session中的
		String sRand = (String) session.getAttribute("sRand");
		JSONObject result = new JSONObject();
		int resultTotal = 0; //执行记录数
		if(!imageCode.equals(sRand)) {
			result.put("success", false);
			result.put("errorInfo", "验证码有误");
		} else{
			//获取评论用户的ip
			String userIp = request.getRemoteAddr(); 
			 //将userIp设置进去
			comment.setUserIp(userIp); 
			
			if(comment.getId() == null) { //没有id表示添加
				//添加评论
				resultTotal = commentService.addComment(comment); 
				//更新一下博客的评论次数
				Blog blog = blogService.findById(comment.getBlog().getId()); 
				blog.setReplyHit(blog.getReplyHit() + 1);
				blogService.update(blog);
			} else { 
				//有id表示修改
				
			}
		}
			//判断是否添加成功
			if(resultTotal > 0) {
				result.put("success", true);
			}		
			ResponseUtil.write(response, result);		
			return null;
	}
	
}