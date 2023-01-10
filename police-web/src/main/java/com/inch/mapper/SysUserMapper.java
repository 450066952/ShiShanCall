package com.inch.mapper;

import java.util.List;
import java.util.Map;

import com.inch.model.SchoolModel;
import com.inch.model.SysUserModel;

/**
 * SysUser Mapper
 * @author Administrator
 *
 */
public interface SysUserMapper<T> extends BaseMapper<T> {
	
	/**
	 * 检查登录
	 * @param email
	 * @param pwd
	 * @return
	 */
	public T queryLogin(SysUserModel model);
	
	/**
	 * 删除用户学校对应关系
	 */
	public void  deleteUserSchoolRel(Object id);
	
	/**
	 * 批量插入用户学校对应关系
	 * @param list
	 * @return
	 */
	public int insertUserSchoolBatch(List list);
	
	/**
	 * 获取用户--学校对应关系
	 * @param id
	 * @return
	 */
	public List<String> getUserSchoolRel(Object id);
	
	public List<SchoolModel> getAllParent(int id);


	public List<Map<String,Object>> queryUserByDepart(String departid);
	
	
}
