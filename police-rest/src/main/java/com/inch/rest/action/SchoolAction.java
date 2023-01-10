
package com.inch.rest.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.inch.interceptor.Auth;
import com.inch.model.SysDicModel;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inch.model.SchoolModel;
import com.inch.model.SysUser;
import com.inch.rest.service.SchoolService;
import com.inch.rest.utils.RedisUtil;
import com.inch.utils.CommonUtil;
import com.inch.utils.FastJsonUtils;
import com.inch.utils.HtmlUtil;
import com.inch.utils.WebConstant;
import com.inch.utils.WebConstant.SuperAdmin;
import com.socket.server.command.service.TonyCommandService;
import com.socket.server.socket.pub.Command;
 
@Controller
@RequestMapping("/school") 
public class SchoolAction extends BaseAction{
	
	private final static Logger log= Logger.getLogger(SchoolAction.class);
	
	@Autowired(required=false) 
	private SchoolService<SchoolModel> schoolService;
	
	@Autowired
	private TonyCommandService tonyService;
	
	@RequestMapping("/getQueryList2.json")
	public void getRootInfo2(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap<String, Object>();
		
		SysUser suser =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+StringUtils.trimToEmpty(request.getParameter("username")));
		
		Map<String,Object> map=new HashMap<String,Object>();

		map.put("isadmin", suser.getIsadmin());
		map.put("ids", suser.getSchool());

		List<SchoolModel> dataList = schoolService.getAllChild(map);
		
		context.put(SUCCESS, true);
		context.put(MSG, "OK");
		context.put("data", dataList);
		HtmlUtil.writerJson(response, context);
	}
	
	
    public static void main(String[] args) {
    	Map<String,Object>  context = new HashMap<String, Object>();
    	context.put(SUCCESS, true);
		context.put(MSG, "OK");
		SchoolModel schoolModel=new SchoolModel();
		context.put("data", schoolModel);
		System.out.println(FastJsonUtils.toJsonWithNUll(context));
	}

	@RequestMapping("/getId.json")
	public void getId(Integer id,HttpServletResponse response) throws Exception{
		
		Map<String,Object>  context = new HashMap<String, Object>();
		SchoolModel bean  = schoolService.queryById(id);
		if(bean  == null){
			sendFailureMessage(response, "没有找到对应的记录!");
			return;
		}
		context.put(SUCCESS, true);
		context.put(MSG, "OK");
		context.put("data", bean);
		HtmlUtil.writerJson(response, context);
	}
	
	@RequestMapping("/save.json")
	public void save(SchoolModel bean,HttpServletResponse response,HttpServletRequest request) throws Exception{
		Map<String,Object>  context = new HashMap<String, Object>();
		int ret=0;
		String msg="";

		SysUser suser =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+StringUtils.trimToEmpty(request.getParameter("username")));
		bean.setAdduser(suser.getId());
		
		if(bean.getId()==null||bean.getId()==0){
			
			//生成五位随机码
			String invitecode=CommonUtil.getRandom(10000, 100000);
			
			bean.setInvitecode(invitecode);
			
			ret=schoolService.add(bean);
			
			if(SuperAdmin.YES.key !=  suser.getIsadmin()){//超级管理员
				Map<String, Object> map=new HashMap<String,Object>();
				map.put("uid", bean.getAdduser());
				map.put("classid", bean.getId());
				
				schoolService.insertUserSchoolRel(map);
			}
			msg="insert";
			
			RedisUtil.del(WebConstant.WEBUSER+suser.getUsername());//删除redis缓存
		}else{
			msg="update";
			ret=schoolService.update(bean);
		}
		
		if(ret>0){
			context.put(SUCCESS, true);
			context.put(MSG, msg);
			context.put("data", suser);
			HtmlUtil.writerJson(response, context);
			
			if(bean.getPid()==0){

				if("insert".equals(msg)){
					tonyService.sendCommandToServer(Command.COMMAND_SCHOOL_INFO, bean,bean.getId()+"");
				}else{
					SchoolModel bean2  = schoolService.queryById(bean.getId());
					tonyService.sendCommandToServer(Command.COMMAND_SCHOOL_INFO, bean2,bean2.getId()+"");
				}
			}
			
		}else{
			this.sendFailureMessage(response, "保存失败");
		}
	}
	
	
	@RequestMapping("/delete.json")
	public void delete(String[] id,HttpServletResponse response) throws Exception{
		schoolService.delete(id);
		sendSuccessMessage(response, "删除成功");
	}
	
	@RequestMapping("/schooltreeList.json")
	public void  schooltreeList(HttpServletRequest request,HttpServletResponse response,String rootid,String isE) throws Exception{
		
		SysUser suser =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+StringUtils.trimToEmpty(request.getParameter("username")));

		Map<String, Object> mapq=new HashMap<String,Object>();

		mapq.put("isadmin", suser.getIsadmin());
		mapq.put("ids", suser.getSchool());

//		if(SuperAdmin.YES.key ==  suser.getIsadmin()){//超级管理员
//			mapq.put("id", "0");
//			mapq.put("ids", null);
//		}else{
//
//			if("true".equals(isE)){
//				mapq.put("id", suser.getSchoolid());
//				mapq.put("ids", null);
//			}else{
//				mapq.put("id", "0");
//				mapq.put("ids", suser.getSchool());
//			}
//
//
//		}
		
		List<SchoolModel> list= schoolService.getAllChild(mapq);
		
		List<SchoolModel> list2=new ArrayList<SchoolModel>();
		
		Map<String, String> map=new HashMap<String,String>();
		
		
		SchoolModel dd=null;
		
		SchoolModel dd2=null;
		
		SchoolModel dd3=null;
		
		
		for (int i = 0; i < list.size(); i++) {
			dd=list.get(i);
			
			if (map.containsKey(dd.getId()+"")) {
				
			}else{
				
				List<SchoolModel> childlist=new ArrayList<SchoolModel>();
				for (int j = i+1; j < list.size(); j++) {
					dd2=list.get(j);
					
					if(dd.getId()==dd2.getPid()){
						
						childlist.add(dd2);
						
						map.put(dd2.getId()+"", dd2.getId()+"");
						
					}
					
					List<SchoolModel> childlist2=new ArrayList<SchoolModel>();
					for (int k = 0; k < list.size(); k++) {
						
						dd3=list.get(k);
						
						if(dd2.getId()==dd3.getPid()){
							
							childlist2.add(dd3);
							
							map.put(dd3.getId()+"", dd3.getId()+"");
						}
					}
					
					dd2.setChildren(childlist2);
				}
				
				dd.setChildren(childlist);
				list2.add(dd);
			}
		}
		
		HtmlUtil.writerJson(response, list2);
	}
	
	/**
	 * 返回树形结构数据
	 * @param request
	 * @param response
	 * @param rootid
	 * @param isE
	 * @param bj
	 * @throws Exception
	 */
	@RequestMapping("/schooltree.json") 
	public void  schooltree(HttpServletRequest request,HttpServletResponse response,String rootid,String isE) throws Exception{
		
		SysUser suser =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+StringUtils.trimToEmpty(request.getParameter("username")));

		Map<String, Object> mapq=new HashMap<String,Object>();

		mapq.put("isadmin", suser.getIsadmin());
		mapq.put("ids", suser.getSchool());

		/*if(SuperAdmin.YES.key ==  suser.getIsadmin()){//超级管理员
			mapq.put("id", "0");
			mapq.put("ids", null);
		}else{
			
			if("true".equals(isE)){
				mapq.put("id", suser.getSchoolid());
				mapq.put("ids", null);
			}else{
				mapq.put("id", "0");
				mapq.put("ids", suser.getSchool());
			}
		}*/
		
		List<SchoolModel> list= schoolService.getAllChild(mapq);
		
		HtmlUtil.writerJson(response, list);
	}
	
	/**
	 * json 列表页面
	 * @param url
	 * @param classifyId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/getAllParent.json") 
	public void  getAllParent(SchoolModel model,HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		
		List<SchoolModel> plist=schoolService.getAllParent(NumberUtils.toInt(request.getParameter("classid")));//所有父节点
		Map<String,Object>  context = new HashMap<String, Object>();
		
		context.put(SUCCESS, true);
		context.put(MSG, "OK");
		context.put("data", plist);
		HtmlUtil.writerJson(response, context);
		
	}

	@Auth(verifyLogin = false)
	@RequestMapping("/getOrgLists")
	public void getOrgLists(HttpServletResponse response, HttpServletRequest request) throws Exception{
		Map<String,Object> context=new HashMap<>();
		SchoolModel smodel=new SchoolModel();
		smodel.setPid(0);
		List<SchoolModel> slist=schoolService.queryByListT(smodel);
		context.put("data",slist);
		context.put(SUCCESS,true);
		HtmlUtil.writerJson(response, context);
	}

	@Auth(verifyLogin = false)
	@RequestMapping("/initNum")
	public void initNum(HttpServletResponse response, HttpServletRequest request) throws Exception{

		int orgid=NumberUtils.toInt(request.getParameter("orgid"));

		schoolService.updateStartNumByOrg(orgid);

		this.sendSuccessMessage(response,"操作成功");
	}

	@Auth(verifyLogin = false)
	@RequestMapping("/getMyOrgLists")
	public void getMyOrgLists(HttpServletResponse response, HttpServletRequest request) throws Exception{
		SysUser suser =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+StringUtils.trimToEmpty(request.getParameter("username")));
		Map<String,Object> context=new HashMap<>();
		SchoolModel smodel=new SchoolModel();
		smodel.setPid(0);
		if(suser.getIsadmin()==SuperAdmin.NO.key){
			smodel.setIds(new String[]{suser.getSchoolid()+""});
		}
		List<SchoolModel> slist=schoolService.queryByListT(smodel);
		context.put("data",slist);
		context.put(SUCCESS,true);
		HtmlUtil.writerJson(response, context);
	}

}
