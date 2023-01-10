
package com.inch.rest.action;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inch.model.Lcd_RelModel;
import com.inch.model.NoticeModel;
import com.inch.model.SysUser;
import com.inch.rest.service.Lcd_RelService;
import com.inch.rest.service.NoticeService;
import com.inch.rest.utils.RedisUtil;
import com.inch.utils.CommonUtil;
import com.inch.utils.DateUtil;
import com.inch.utils.HtmlUtil;
import com.inch.utils.WebConstant;
import com.inch.utils.WebConstant.SuperAdmin;
import com.socket.server.command.service.TonyCommandService;
import com.socket.server.socket.pub.Command;
 
@Controller
@RequestMapping("/notice") 
public class NoticeAction extends BaseAction{
	
	private final static Logger log= Logger.getLogger(NoticeAction.class);
	
	@Autowired 
	private NoticeService<NoticeModel> noticeService; 
	
	@Autowired
	private Lcd_RelService<Lcd_RelModel> lcdRelService;
	
	@Autowired
	private TonyCommandService tonyService;
	
	@RequestMapping("/getQueryList.json")
	public void  dataList(NoticeModel model,HttpServletResponse response) throws Exception{
		
		SysUser suser =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+model.getUsername());

		if(StringUtils.trimToEmpty(model.getClassid()).length()==0){
			if(SuperAdmin.YES.key ==  suser.getIsadmin()){//超级管理员
				model.setIsadmin(SuperAdmin.YES.key);
			}else{
				model.setIsadmin(SuperAdmin.NO.key);
				model.setClassid(suser.getSchoolids());
			}
		}
		HtmlUtil.writerJson(response, noticeService.queryByList(model));
	}
	
	@RequestMapping("/save.json")
	public void save(NoticeModel bean,HttpServletRequest request,HttpServletResponse response) throws Exception{
		int ret;
		SysUser suser =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+StringUtils.trimToEmpty(request.getParameter("username")));
		bean.setAdduser(suser.getId());
		
		if(StringUtils.trimToEmpty(bean.getGuid()).length() == 0){
			bean.setGuid(UUID.randomUUID().toString());
			ret=noticeService.add(bean);
		}else{
			ret=noticeService.update(bean);
		}
		
		if(ret>0){
			
			//显示屏的对应关系
			lcdRelService.addLcdRel(bean.getGuid(), bean.getClassid());
			
//			if(bean.getIsscreen()==WebConstant.ISSCREEN){
				
				bean.setAddtime(DateUtil.getNowPlusTime());
				//发送给屏幕端
				tonyService.sendCommandToServer(Command.COMMAND_NOTICE, bean,bean.getClassid());
//			}
			
			String delStr=CommonUtil.compare(bean.getClassid(), bean.getOldclassid());
			if(delStr.length()>0){
				tonyService.sendCommandToServer(Command.COMMAND_DEL_NOTICE, bean.getGuid(),delStr);
			}

			sendSuccessMessage(response, "保存成功~");
		}else{
			sendFailureMessage(response, "保存失败");
		}
	}
	
	@RequestMapping("/getId")
	public void getId(String id,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap<String,Object>();
		NoticeModel bean  = noticeService.queryById(id);
		if(bean  == null){
			sendFailureMessage(response, "没有找到对应的记录!");
			return;
		}
		
		if(bean.getClassid()==null){
			bean.setClassid("");
		}
		
		bean.setClassids(bean.getClassid().split(","));
		bean.setOldclassid(bean.getClassid());
		
		context.put(SUCCESS, true);
		context.put("data", bean);
		HtmlUtil.writerJson(response, context);
	}
	
	@RequestMapping("/delete")
	public void delete(String id,HttpServletResponse response) throws Exception{
		//发送给屏幕端
	    tonyService.sendDeleteToServer(Command.COMMAND_DEL_NOTICE,id);
	    
	    int ret =noticeService.deleteBactch(id.split(","));
		
		if(ret>0){
			sendSuccessMessage(response, "删除成功");
		}else{
			this.sendFailureMessage(response, "删除失败");
		}
	}
	
}
