package ssm.blog.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ssm.blog.entity.Blog;
import ssm.blog.entity.Blogger;
import ssm.blog.entity.Comment;
import ssm.blog.service.BloggerService;
import ssm.blog.util.CryptographyUtil;
import ssm.blog.util.PageUtil;
import ssm.blog.util.StringUtil;

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
	
	/**
	 * blogger 博主信息
	 * @return
	 */
	@RequestMapping("/aboutme")
	public ModelAndView aboutme(){
		ModelAndView modelAndView = new ModelAndView();
		Blogger blogger = bloggerService.getBloggerData();
		modelAndView.addObject("blogger", blogger);
		modelAndView.addObject("commonPage", "foreground/blogger/bloggerInfo.jsp");
		modelAndView.addObject("title", "个人博客");
		modelAndView.setViewName("mainTemp");
		return modelAndView;
		
	}
	
	
	@RequestMapping("/myalbum")
	public ModelAndView myAlbum() {
		ModelAndView modelAndView = new ModelAndView();
		//要写一个相册的service获取相册
		//要建一个相册表
		//....
		modelAndView.addObject("commonPage", "foreground/blogger/myAlbum.jsp");
		modelAndView.setViewName("mainTemp");
		return modelAndView;
	}
	
	@RequestMapping("/resource")
	public ModelAndView resource() {
		ModelAndView modelAndView = new ModelAndView();
		//
		//....
		modelAndView.addObject("commonPage", "foreground/blogger/resource.jsp");
		modelAndView.setViewName("mainTemp");
		return modelAndView;
	}
	
	
	
}
