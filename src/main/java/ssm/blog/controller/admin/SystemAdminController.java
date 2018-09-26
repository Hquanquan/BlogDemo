package ssm.blog.controller.admin;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import ssm.blog.entity.BlogType;
import ssm.blog.entity.Blogger;
import ssm.blog.service.BlogTypeService;
import ssm.blog.service.BloggerService;
import ssm.blog.util.ResponseUtil;

@Controller
@RequestMapping("/admin/system")
public class SystemAdminController {
	
	@Autowired
	private BlogTypeService blogTypeService;
	
	@Autowired
	private BloggerService bloggerService;
	
	// 刷新系统缓存
	@RequestMapping("/refreshSystemCache")
	public String refreshSystemCache(HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		

		ServletContext application = RequestContextUtils.getWebApplicationContext(request).getServletContext();
		
		// 获取博主信息
		Blogger blogger = bloggerService.getBloggerData();
		blogger.setPassword(null);
		application.setAttribute("blogger", blogger);
		

		// 获取博客类别信息
		List<BlogType> blogTypeList = blogTypeService.getBlogTypeData();
		application.setAttribute("blogTypeList", blogTypeList);
		
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		
		return null;
		
	}

}
