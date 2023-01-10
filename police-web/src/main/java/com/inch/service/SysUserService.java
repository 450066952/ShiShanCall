package com.inch.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inch.bean.SysRoleRel;
import com.inch.bean.SysRoleRel.RelType;
import com.inch.mapper.SysUserMapper;
import com.inch.model.SchoolModel;
import com.inch.model.SysUserModel;

/**
 * 
 * <br>
 * <b>功能：</b>SysUserService<br>
 * <b>作者：</b>Tony<br>
 * <b>日期：</b> Dec 9, 2011 <br>
 * <b>版权所有：<b>版权所有(C) 2014 Tony<br>
 */
@Service("sysUserService")
public class SysUserService<T> extends BaseService<T> {
	private final static Logger log= Logger.getLogger(SysUserService.class);
	
	@Autowired
	private SysRoleRelService<SysRoleRel> sysRoleRelService;

	@Override
	public void delete(Object[] ids) throws Exception {
		super.delete(ids);
		for(Object id :  ids){
			sysRoleRelService.deleteByObjId((Integer)id, RelType.USER.key);//删除角色对应关系
			deleteByObjId((Integer)id);//删除用户--学校对应关系
		}
	}
	/**
	 * 检查登录
	 * @param username
	 * @param pwd
	 * @return
	 */
	public T queryLogin(String username,String pwd){
		SysUserModel model = new SysUserModel();
		model.setUsername(username);
		model.setPassword(pwd);
		return getMapper().queryLogin(model);
	}
	
	
	/**
	 * 查询用户权限
	 * @param userId
	 * @return
	 */
	public List<SysRoleRel> getUserRole(Integer userId){
		return sysRoleRelService.queryByObjId(userId,RelType.USER.key);
	}
	
	/**
	 * 添加用户权限
	 * @param userId
	 * @param roleIds
	 * @throws Exception
	 */
	public void addUserRole(Integer userId,String[] roleIds) throws Exception{
		if(userId == null ||  roleIds == null || roleIds.length < 1 ){ 
			return;
		}
		//清除关联关系
		sysRoleRelService.deleteByObjId(userId, RelType.USER.key);
		
		for(String roleId :roleIds ){ 
			SysRoleRel rel = new SysRoleRel();
			rel.setRoleId(NumberUtils.toInt(roleId));
			rel.setObjId(userId);
			rel.setRelType(RelType.USER.key);
			sysRoleRelService.add(rel);
		}
	}
	
	/**
	 * 添加用户--学校对应关系
	 * @param userId
	 * @param roleIds
	 * @throws Exception
	 */
	public void addUserSchool(Integer userId,List<Map<String,Object>> list) throws Exception{
		if(userId == null ){ 
			return;
		}
		
		//清除关联关系
		deleteByObjId(userId);
		
		if(list!=null&&list.size()>0){
			//插入软连关系
			getMapper().insertUserSchoolBatch(list);
		}
	}
	
	
	/**
	 * 根据关联对象id,关联类型删除 
	 * @param objId
	 * @param relType
	 */
	public void deleteByObjId(Integer objId){
		getMapper().deleteUserSchoolRel(objId);
	}
	
	/**
	 * 返回用户学校对应关系
	 * @param uid
	 * @return
	 */
	public List<String> getUserSchoolRel(Integer uid){
		
		return getMapper().getUserSchoolRel(uid);
	}
	
	
	public List<SchoolModel> getAllParent(int id){
		return getMapper().getAllParent(id);
	}

	public List<Map<String,Object>> queryUserByDepart(String departid){
		return getMapper().queryUserByDepart(departid);
	}
	
	
	@Autowired
    private SysUserMapper<T> mapper;

		
	public SysUserMapper<T> getMapper() {
		return mapper;
	}

}
