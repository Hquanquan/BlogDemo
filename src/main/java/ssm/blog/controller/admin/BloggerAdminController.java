package ssm.blog.controller.admin;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ssm.blog.entity.Blogger;
import ssm.blog.service.BloggerService;
import ssm.blog.util.CryptographyUtil;
import ssm.blog.util.ResponseUtil;


@Controller
@RequestMapping("/admin/blogger")
public class BloggerAdminController {
	
	@Autowired
	private BloggerService bloggerService;
	
	//修改博主密码
	@RequestMapping("/modifyPassword")
	public String modifyPassword(
			@RequestParam("password") String password,
			HttpServletResponse response) throws Exception {
//		新建一个Blogger 对象blogger
		Blogger blogger = new Blogger();
//		给该对象设置一个用md5加密的密码
		blogger.setPassword(CryptographyUtil.md5(password, "javacoder"));
//		利用bloggerService对象的updateBlogger更新blogger的密码
		int resultTotal = bloggerService.updateBlogger(blogger);
//		新建一个JSONObject result对象，用来返回结果
		JSONObject result = new JSONObject();
		if(resultTotal > 0) {
			result.put("success", true);
		} else {
			result.put("success", false);
		}
		ResponseUtil.write(response, result);
		return null;
	}
	
	
	// 退出
	@RequestMapping("/logout")
	public String logout() throws Exception {
		
		SecurityUtils.getSubject().logout();
		return "redirect:/login.jsp";
	}
	
	
	
	
	
	

}
