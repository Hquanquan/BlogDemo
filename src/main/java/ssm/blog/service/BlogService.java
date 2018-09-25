package ssm.blog.service;

public interface BlogService {

	
	// 根据博客类型的id查询该类型下的博客数量
	public Integer getBlogByTypeId(Integer typeId);

}
