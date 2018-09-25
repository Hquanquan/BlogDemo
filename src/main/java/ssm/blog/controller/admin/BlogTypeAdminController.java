package ssm.blog.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ssm.blog.entity.BlogType;
import ssm.blog.entity.BlogTypeService;
import ssm.blog.entity.PageBean;
import ssm.blog.util.ResponseUtil;


@Controller
@RequestMapping("/admin/blogType")
public class BlogTypeAdminController {
	
	@Autowired
	private BlogTypeService blogTypeService;

	// 分页查询博客类别
	@RequestMapping("/listBlogType")
	public String listBlogType(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "rows", required = false) String rows,
			HttpServletResponse response) throws Exception {

		PageBean pageBean = new PageBean(Integer.parseInt(page),
				Integer.parseInt(rows));
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("start", pageBean.getStart());
		map.put("pageSize", pageBean.getPageSize());
		List<BlogType> blogTypeList = blogTypeService.listBlogType(map);
		Long total = blogTypeService.getTotal(map);

		JSONObject result = new JSONObject();
		JSONArray jsonArray = JSONArray.fromObject(blogTypeList);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
		return null;
	}
}
