package ssm.blog.controller.admin;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ssm.blog.entity.Blogger;
import ssm.blog.service.BloggerService;
import ssm.blog.util.CryptographyUtil;
import ssm.blog.util.DateUtil;
import ssm.blog.util.ResponseUtil;


@Controller
@RequestMapping("/admin/blogger")
public class BloggerAdminController {
	
	@Autowired
	private BloggerService bloggerService;
	
	
	/** 查询博主信息
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findBlogger")
	public String findBlogger(HttpServletResponse response) throws Exception{
//		利用bloggerService.getBloggerData() 获取blogger信息，并赋值给blogger
		Blogger blogger = bloggerService.getBloggerData();
		JSONObject jsonObject = new JSONObject();
		ResponseUtil.write(response, jsonObject);
		return null;
	}
	
	
	
	/**修改保存blogger信息
	 * 
	 * @param imageFile:上传文件的参数：接收页面传过来的文件
	 * @param blogger
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(@RequestParam("imageFile") MultipartFile imageFile,
			Blogger blogger,HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		//如果用户有传过照片，就更新图片
		if(!imageFile.isEmpty()){
			//获取服务器根路径
			String filePath = request.getServletContext().getRealPath("/");

			String imageName = DateUtil.getCurrentDateStr() + "." +imageFile.getOriginalFilename().split("\\.")[1];
			
			imageFile.transferTo(new File(filePath+"static/userImages/"+imageName));
			blogger.setImagename(imageName);
		}
		int resultTotal=bloggerService.updateBlogger(blogger);
		
		JSONObject result=new JSONObject();
		
		if(resultTotal>0){
			result.put("success", true);
		}else{
			result.put("success", false);
		}
		ResponseUtil.write(response, result);
		return null;
		
	}
	
	/** 修改博主密码
	 * @param password
	 * @param response
	 * @return
	 * @throws Exception
	 */
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
	
	
	/** 退出登录
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/logout")
	public String logout() throws Exception {
		SecurityUtils.getSubject().logout();
		return "redirect:/login.jsp";
	}
	
	
	
	
	
	

}
