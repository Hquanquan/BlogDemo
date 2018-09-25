package ssm.blog.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ssm.blog.entity.Blogger;
import ssm.blog.service.BloggerService;
import ssm.blog.util.CryptographyUtil;

@Controller
@RequestMapping("/blogger")
public class BloggerController {

	@Autowired
	private BloggerService bloggerService;
	
	/**
	 * 博客管理者登录
	 * @param blogger
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	public String login(Blogger blogger, HttpServletRequest request) {
		//获取当前登陆的主体
		Subject subject = SecurityUtils.getSubject(); 
		//将密码使用md5加密
		String newPassword = CryptographyUtil.md5(blogger.getPassword(), "javacoder");
		//将用户信息封装到token中
		UsernamePasswordToken token = new UsernamePasswordToken(blogger.getUsername(), newPassword);
		try {
			//会调用MyRealm中的doGetAuthenticationInfo方法进行身份认证,认证成功跳转到admin/main.jsp页面
			subject.login(token); 
			return "redirect:/admin/main.jsp";
		} catch (AuthenticationException e) {
			e.printStackTrace();
			request.setAttribute("bloger", blogger);
			request.setAttribute("errorInfo", "用户名或密码错误");
			return "login";
		} 
		
		
		
		

	}
	
	
}
