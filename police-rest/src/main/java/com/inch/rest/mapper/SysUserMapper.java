package com.inch.rest.mapper;

import com.inch.model.SubClassModel;
import com.inch.model.SysUser;
import com.inch.model.UserSchoolModel;
import com.inch.model.VersionModel;

import java.util.List;
import java.util.Map;

public interface SysUserMapper<T> extends BaseMapper<T>{
	
	 SysUser getSysUserInfo(SysUser model);
	 List<UserSchoolModel> getUserSchoolRel(int uid);
	 List<String> getUserByIds(String ids);
	 List<SubClassModel> getSubClass(SysUser model) ;
	 int saveInvite_code(Map<String, Object> map);
	 int modifypwd (Object model);
	 int modifypic (Object model);
	 int modifyphone (Object model);
	 int saveToken(Object model);
	 VersionModel getVersionByPhone(Map<String,Object> map);
	 List<SysUser> getPushUserByClass(Map<String,Object> map);
	 List<SysUser> getPushUserBySchool(Map<String,Object> map);

    SysUser getSysUserInfoSSO(String userName);
}
