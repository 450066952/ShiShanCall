package com.inch.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.inch.model.SysDicModel;
import com.inch.utils.TonyResult;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inch.bean.SysRole;
import com.inch.bean.SysRoleRel;
import com.inch.model.SchoolModel;
import com.inch.model.SysUser;
import com.inch.model.SysUserModel;
import com.inch.service.SysRoleService;
import com.inch.service.SysUserService;
import com.inch.utils.BuidRequest;
import com.inch.utils.HtmlUtil;
import com.inch.utils.MD5;
import com.inch.utils.SessionUtils;
import com.inch.utils.WebConstant.SuperAdmin;
 
@Controller
@RequestMapping("/sysUser") 
public class SysUserAction extends BaseAction{
	
	private final static Logger log= Logger.getLogger(SysUserAction.class);
	
	@Autowired
	private SysUserService<SysUser> sysUserService; 
	
	@RequestMapping("/list")
	public ModelAndView  list(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = getRootMap();

		String json=BuidRequest.getRequestResult(request,response,"sysDic/getDic","get");
		TonyResult t = TonyResult.formatToList(json,SysDicModel.class);
		context.put("itemlist", t.getData());

		return forword("sys/sysUser",context); 
	}
	
	
	@RequestMapping("/dataList")
	public void  dataList(SysUserModel model ,HttpServletRequest request,HttpServletResponse response) throws Exception{

		SysUser user=SessionUtils.getUser(request);

		if(user != null && SuperAdmin.YES.key ==  user.getIsadmin()){//超级管理员查看所有

		}else{
			model.setSchoolids(user.getSchoolids());
		}

		model.setIsadmin(user.getIsadmin());
		model.setSchoolid(user.getSchoolid());

		PageHelper.startPage(model.getPageno(), model.getLength());
		List<SysUser> dataList = sysUserService.queryByList(model);
		PageInfo page = new PageInfo(dataList);

		//设置页面数据
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("data",dataList);

		jsonMap.put("draw", model.getDraw());
		jsonMap.put("recordsTotal", page.getTotal());
		jsonMap.put("recordsFiltered",page.getTotal());
		HtmlUtil.writerJson(response, jsonMap);
	}
	
	@RequestMapping("/save")
	public void save(SysUser bean,HttpServletRequest request,HttpServletResponse response) 
			throws Exception{

		SysUser suser=SessionUtils.getUser(request);
		bean.setAdduser(suser.getId());
		
	    String roleStr=StringUtils.trimToEmpty(request.getParameter("selRoles"));
	    String classStr=StringUtils.trimToEmpty(request.getParameter("classid"));
	    
	    String[] roles=null,classids=null;
	    if(roleStr.length()>0){
	    	roles=roleStr.split(",");
	    }
	    		
		if(classStr.length()>0){
			classids=classStr.split(",");
		}
		
		StringBuffer sb=new StringBuffer();
		if(classids!=null){
			
			for (int i = 0; i < classids.length; i++) {
				
				if(i==0){
					List<SchoolModel> slist=sysUserService.getAllParent(NumberUtils.toInt(classids[i]));//所有父节点
					
					if(slist.size()>0){
						
						for (SchoolModel schoolModel : slist) {
							if(schoolModel.getPid()==0){
								bean.setSchoolid(schoolModel.getId());
								break;
							}
						}
					}
				}
				sb.append(classids[i]+",");
			}
		}

		if(bean.getSchoolid()==0){
			bean.setSchoolid(suser.getSchoolid());
		}
		
		bean.setSchoolids(sb.toString());
		
		if(bean.getId() == 0){
			
			bean.setPassword(MD5.getMD5String("000000"));
			
			int ret=sysUserService.add(bean);
			
			if(ret==0){
				
				this.sendFailureMessage(response, "用户名已存在~");
				return;
			}
			
			sysUserService.addUserRole(bean.getId(), roles);
		}else{
			sysUserService.update(bean);
			
			sysUserService.addUserRole(bean.getId(), roles);
			
			BuidRequest.sendRefreshUser(request,"/sys/refreshUser.json",bean.getUsername());
		}

		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		
		if(classids!=null){
			
			for (int i = 0; i < classids.length; i++) {
				
				Map<String,Object> user_school= new HashMap<String,Object>();  
				user_school.put("id", bean.getId());  
				user_school.put("classid", classids[i]);  
				
				list.add(user_school);
				
			}
			
		}
		
		sysUserService.addUserSchool(bean.getId(),list);
		sendSuccessMessage(response, "保存成功~");
	}
	
	@RequestMapping("/getId")
	public void getId(Integer id,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = getRootMap();
		SysUser bean  = sysUserService.queryById(id);
		if(bean  == null){
			sendFailureMessage(response, "没有找到对应的记录!");
			return;
		}
		
		
		Integer[] roleIds = null;
		List<SysRoleRel>  roles  =sysUserService.getUserRole(bean.getId());
		if(roles != null){
			roleIds = new Integer[roles.size()];
			int i = 0;
			for(SysRoleRel rel : roles ){
				roleIds[i] = rel.getRoleId();
				i++;
			}
		}
		
		bean.setRoleIds(roleIds);
		
		String[] school=null;
		List<String> list=sysUserService.getUserSchoolRel(bean.getId());
		
		if(list != null){
			school = new String[list.size()];
			int i = 0;
			for(String s: list ){
				school[i] = s;
				i++;
			}
		}
		
		bean.setSchool(school);
		context.put(SUCCESS, true);
		context.put("data", bean);
		
		HtmlUtil.writerJson(response, context);
	}
	
	@RequestMapping("/delete")
	public void delete(Integer[] id,HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		BuidRequest.sendRefreshUser(request,"/sys/refreshUser.json","");
		
		sysUserService.delete(id);
		sendSuccessMessage(response, "删除成功");
	}

	@RequestMapping("/resetpwd")
	public void resetpwd(HttpServletResponse response,HttpServletRequest request) throws Exception{

		BuidRequest.sendRefreshUser(request,"/sys/refreshUser.json", StringUtils.trimToEmpty(request.getParameter("username")));
		BuidRequest.sendRequest(request,response,"sys/resetpwd.json","post");

	}

	@RequestMapping("/getUser")
	public void getUser(Integer id,HttpServletResponse response)  throws Exception{
		Map<String,Object>  context = getRootMap();
		SysUser bean  = sysUserService.queryById(id);
		if(bean  == null){
			sendFailureMessage(response, "没有找到对应的记录!");
			return;
		}
		Integer[] roleIds = null;
		List<SysRoleRel>  roles  =sysUserService.getUserRole(bean.getId());
		if(roles != null){
			roleIds = new Integer[roles.size()];
			int i = 0;
			for(SysRoleRel rel : roles ){
				roleIds[i] = rel.getRoleId();
				i++;
			}
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", bean.getId());
		data.put("username", bean.getUsername());
		data.put("roleIds", roleIds);
		context.put(SUCCESS, true);
		context.put("data", data);
		HtmlUtil.writerJson(response, context);
		
	}

	@RequestMapping("/getUserByDepart")
	public void loadDepartUser(HttpServletResponse response,String departid) throws Exception{
		List<Map<String,Object>>  roloList = sysUserService.queryUserByDepart(departid);
		HtmlUtil.writerJson(response, roloList);
	}

	public static void main(String[] args) {
		
		//zyf125
		System.out.println(MD5.getMD5String("zyf125"));
	}
}
