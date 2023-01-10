/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inch.rest.action;

import com.github.pagehelper.PageHelper;
import com.inch.interceptor.Auth;
import com.inch.model.SubClassModel;
import com.inch.model.SysUser;
import com.inch.model.SysUserModel;
import com.inch.model.VersionModel;
import com.inch.rest.service.UserinfoService;
import com.inch.rest.utils.RedisUtil;
import com.inch.utils.HtmlUtil;
import com.inch.utils.MD5;
import com.inch.utils.WebConstant;
import com.inch.utils.WebConstant.SuperAdmin;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sys") 
public class SysUserAction extends BaseAction{

	Logger log = Logger.getLogger(SysUserAction.class);

	@Autowired
	UserinfoService<SysUser> userService;

//	@Autowired
//	private TonyCommandService tonyService;

	@RequestMapping(value = "toLogin.json", method = { RequestMethod.GET,RequestMethod.POST })
	public void toLogin(HttpServletRequest request,HttpServletResponse response) {
		
		Map<String,Object> jsonMap = new HashMap<>();
		String userName = "";
		if (StringUtils.isNotBlank(request.getParameter("username"))){
			userName = request.getParameter("username");
		}else {
			userName = request.getParameter("userName").substring(2,8);
		}
		System.out.println("userName1 = " + userName);

		SysUser suser =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+StringUtils.trimToEmpty(userName));

//		//通知评价器
//		tonyService.sendPersonToPad(suser.getWinid(),suser.getUsername());

		jsonMap.put(SUCCESS, true);
		jsonMap.put(MSG, "OK");
		jsonMap.put("data",suser);
		
		HtmlUtil.writerJson(response, jsonMap);
	}
	
	@RequestMapping("/getSubClass.json")
	public void  dataList(HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		SysUser suser =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+StringUtils.trimToEmpty(request.getParameter("username")));

		List<SubClassModel> dataList = userService.getSubClass(suser);
		
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put(SUCCESS, true);
		jsonMap.put(MSG, "OK");
		jsonMap.put("data",dataList);
		HtmlUtil.writerJson(response, jsonMap);
	}
	
	@RequestMapping(value = "refreshUser.json", method = { RequestMethod.GET,RequestMethod.POST })
	public void refreshUser( HttpServletRequest request,HttpServletResponse response) {
		
		String delusername=StringUtils.trimToEmpty(request.getParameter("delusername"));
		
		if(delusername.length()>0){
			RedisUtil.del(WebConstant.WEBUSER+delusername);//删除redis缓存
			
		}else{
			String ids=StringUtils.trimToEmpty(request.getParameter("id"));
			if(ids.length()>0){
				//根据id去查询用户再进行删除
				List<String> list=userService.getUserByIds(ids);
				
				for (int i = 0; i < list.size(); i++) {
					RedisUtil.hdel(WebConstant.WEBUSER+list.get(i));//删除redis缓存
				}
				
			}
		}
	}

	@RequestMapping(value = "resetpwd.json", method = {RequestMethod.POST })
	public void  resetpwd(SysUser bean,HttpServletResponse response) throws Exception{

		bean.setPassword(MD5.getMD5String("000000"));
		int ret=userService.modifypwd(bean);
		if(ret>0){
			this.sendSuccessMessage(response, "修改成功");
		}else{
			this.sendFailureMessage(response, "修改失败！");
		}
	}

	@RequestMapping(value = "modifypwd.json", method = { RequestMethod.GET,RequestMethod.POST })
	public void  modifypwd(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		SysUser suser =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+StringUtils.trimToEmpty(request.getParameter("username")));
		suser.setPassword(StringUtils.trimToEmpty(request.getParameter("newpwd")));
		int ret=userService.modifypwd(suser);
		
		if(ret>0){
			this.sendSuccessMessage(response, "修改成功");
			RedisUtil.del(WebConstant.WEBUSER+suser.getUsername());
		}else{
			this.sendFailureMessage(response, "修改失败！");
		}
	}
	

	@Auth(verifyLogin=false,verifyParentLogin=false)
	@RequestMapping(value = "checkVersion.json", method = { RequestMethod.GET,RequestMethod.POST })
	public void  checkVersion(HttpServletRequest request,HttpServletResponse response,String schoolid,String flag) throws Exception{
		
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("schoolid", schoolid);
		jsonMap.put("flag", flag);
		
		VersionModel m=userService.getVersionByPhone(jsonMap);
		
		jsonMap.clear();
		jsonMap.put(SUCCESS, true);
		jsonMap.put(MSG, "OK");
		jsonMap.put("data",m);
		
		HtmlUtil.writerWithOutJson(response, jsonMap);
	}
	
	@RequestMapping(value = "queryUserList.json", method = { RequestMethod.GET,RequestMethod.POST })
	public void  queryUserList(HttpServletRequest request,HttpServletResponse response,SysUserModel model) throws Exception{
		
		//设置页面数据
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		SysUser suser =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+model.getUsername());
		
		if( SuperAdmin.YES.key ==  suser.getIsadmin()){//超级管理员查看所有
			model.setIsadmin(SuperAdmin.YES.key);
		}else{
			model.setSchoolid(suser.getSchoolid());
		}
		
		//所教课班级
		PageHelper.startPage(1, 10);
		List<SubClassModel> classData = userService.getSubClass(suser);
		if(classData.size()<5){
			jsonMap.put("classData",classData);
		}else{
			jsonMap.put("classData",new ArrayList<SubClassModel>());
		}

		List<SysUser> dataList = userService.queryByListT(model);
		jsonMap.put("data",dataList);
		HtmlUtil.writerWithOutJson(response, jsonMap);
	}

}
