package ssm.blog.dao;

import java.util.List;
import java.util.Map;

import ssm.blog.entity.Link;

public interface LinkDao {

	//获取友情链接信息
	public List<Link> listLinkData(Map<String, Object> map);

	//获取友情链接总记录数
	public Long getTotal(Map<String, Object> map);

	//	添加友情链接
	public Integer addLink(Link link);

	//更新友情链接
	public Integer updateLink(Link link);

	//根据id 删除友情链接
	public Integer deleteLink(int id);

	//获取友情链接信息(在系统缓存中)
	public List<Link> getLinkData();

}
