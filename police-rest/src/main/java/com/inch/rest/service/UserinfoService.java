/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inch.rest.service;
import com.inch.model.SubClassModel;
import com.inch.model.SysUser;
import com.inch.model.UserSchoolModel;
import com.inch.model.VersionModel;
import com.inch.rest.mapper.SysUserMapper;
import com.inch.utils.WebConstant.SuperAdmin;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("userinfoService")
public class UserinfoService<T> extends BaseService<T>{
	private static final Logger logger = Logger.getLogger(UserinfoService.class);
	
//	@Autowired
//	private SchoolService<SchoolModel> schoolService;

	public SysUser getSysUserInfo(SysUser model){

		SysUser user=getMapper().getSysUserInfo(model);

		if(user==null){
			return user;
		}

		String str="";
		//---------------------获取当前人员可管理的学校信息------------------
		if(SuperAdmin.YES.key ==  user.getIsadmin()){

		}else{
			String[] school=null;
			List<UserSchoolModel> u_s_List=getMapper().getUserSchoolRel(user.getId());
			if(u_s_List!=null&&u_s_List.size()>0){

				school=new String[u_s_List.size()];

				int i=0;
				int oneclass=0;

				for (UserSchoolModel s : u_s_List) {

					if(i==0){
						oneclass=s.getClassid();
					}

					school[i]=s.getClassid()+"";
					str+=s.getClassid()+",";
					i++;

				}

				user.setSchool(school);//设置这个登录人员 能看到哪些学校、年级、班级等信息
				user.setSchoolids(str);
			}
		}


		return user;
	}

	public SysUser queryPcLogin(String username,String pwd){
		SysUser model =new SysUser();
		model.setUsername(username);
		model.setPassword(pwd);

		SysUser user=getMapper().getSysUserInfo(model);
		return user;
	}
    
    public List<SubClassModel> getSubClass(SysUser s ) {
		return getMapper().getSubClass(s);
	}
    
    public List<String> getUserByIds(String ids){
    	return getMapper().getUserByIds(ids);
    }
    
	public VersionModel getVersionByPhone(Map<String,Object> map){
		return getMapper().getVersionByPhone(map);
	}
	
	public int modifypwd(Object o){
		return getMapper().modifypwd(o);
	}
	

    @Autowired
    private SysUserMapper<T> mapper;

		
	public SysUserMapper<T> getMapper() {
		return mapper;
	}

    public SysUser getSysUserInfoSSO(String userName) {
		return getMapper().getSysUserInfoSSO(userName);
    }
}
