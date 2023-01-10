
package com.inch.rest.action;

import com.inch.interceptor.Auth;
import com.inch.model.SchoolNewsModel;
import com.inch.model.SysUser;
import com.inch.rest.service.SchoolNewsService;
import com.inch.rest.utils.RedisUtil;
import com.inch.utils.HtmlUtil;
import com.inch.utils.WebConstant;
import com.inch.utils.WebConstant.SuperAdmin;
import com.socket.server.command.service.TonyCommandService;
import com.socket.server.socket.pub.Command;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/schoolnews")
public class SchoolNewsAction extends BaseAction {

	@Autowired
	private TonyCommandService tonyService;

	@Autowired
	private SchoolNewsService<SchoolNewsModel> schoolnewsService;
	private static final Logger logger = Logger.getLogger(SchoolNewsAction.class);
	
	@RequestMapping("/getQueryList")
	public void  dataList(SchoolNewsModel model,HttpServletResponse response,HttpServletRequest request) throws Exception{

		SysUser suser =(SysUser) RedisUtil.getObjByKey(WebConstant.WEBUSER+ org.apache.commons.lang.StringUtils.trimToEmpty(request.getParameter("username")));

		if(SuperAdmin.YES.key ==  suser.getIsadmin()){
			model.setIsadmin(SuperAdmin.YES.key);
		}else{
			model.setIsadmin(SuperAdmin.NO.key);
		}
		
		HtmlUtil.writerJson(response, schoolnewsService.queryByList(model));
	}
	
	@RequestMapping("/save")
	public void save(SchoolNewsModel bean,HttpServletRequest request,HttpServletResponse response) throws Exception{
		int ret=0;
		SysUser suser =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+ org.apache.commons.lang.StringUtils.trimToEmpty(request.getParameter("username")));

		bean.setAdduser(suser.getId());
		String[] users=StringUtils.trimToEmpty(bean.getSelusers()).split(",");
		bean.setSchoolid(suser.getSchoolid());
		String guid=UUID.randomUUID().toString();
		if(StringUtils.trimToEmpty(bean.getGuid()).length() == 0){
			bean.setGuid(guid);
			ret=schoolnewsService.add(bean);
		}else{
			ret=schoolnewsService.update(bean);
		}
		
		if(ret>0){

			List<Map<String,Object>> selList=new ArrayList<>();
			List<String> recList=new ArrayList<>();
			if(users!=null){
				for (int i = 0; i < users.length; i++) {
					Map<String,Object> user_school= new HashMap<>();
					user_school.put("pguid", bean.getGuid());
					user_school.put("username", users[i]);
					selList.add(user_school);
					recList.add(users[i]);
				}
			}
			schoolnewsService.addSelUsers(bean.getGuid(),selList);

			//通知接收的人
			bean.setNr("view/detial.shtml?id="+guid);
			tonyService.sendMsgToPC(recList,bean, Command.COMMAND_SEND_MSG);
			sendSuccessMessage(response, "保存成功~");
		}else{
			sendFailureMessage(response, "保存失败");
		}
	}
	
	@Auth(verifyLogin = false)
	@RequestMapping("/getId")
	public void getId(String id,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap<>();
		SchoolNewsModel bean  = schoolnewsService.queryById(id);
		if(bean  == null){
			sendFailureMessage(response, "没有找到对应的记录!");
			return;
		}

		String[] depart=null;
		List<String> listd=schoolnewsService.getSelUsers(bean.getGuid());

		if(listd != null){
			depart = new String[listd.size()];
			int i = 0;
			for(String s: listd ){
				depart[i] = s;
				i++;
			}
		}

		context.put("users", depart);
		context.put(SUCCESS, true);
		context.put("data", bean);
		HtmlUtil.writerJson(response, context);
	}
	
	
	@RequestMapping("/delete")
	public void delete(String id,HttpServletResponse response) throws Exception{
		
		int ret =schoolnewsService.deleteBactch(id.split(","));
		
		if(ret>0){
			sendSuccessMessage(response, "删除成功");
		}else{
			this.sendFailureMessage(response, "删除失败");
		}
	}
}
