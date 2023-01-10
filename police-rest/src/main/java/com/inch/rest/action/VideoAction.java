
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
import com.inch.model.SysUser;
import com.inch.model.VideoModel;
import com.inch.rest.service.Lcd_RelService;
import com.inch.rest.service.VideoService;
import com.inch.rest.utils.RedisUtil;
import com.inch.utils.CommonUtil;
import com.inch.utils.DateUtil;
import com.inch.utils.HtmlUtil;
import com.inch.utils.WebConstant;
import com.inch.utils.WebConstant.SuperAdmin;
import com.socket.server.command.service.TonyCommandService;
import com.socket.server.socket.pub.Command;
 
@Controller
@RequestMapping("/video") 
public class VideoAction extends BaseAction{
	
	private final static Logger log= Logger.getLogger(VideoAction.class);
	
	@Autowired(required=false) 
	private VideoService<VideoModel> videoService; 
	
	@Autowired
	private Lcd_RelService<Lcd_RelModel> lcdRelService;
	
	@Autowired
	private TonyCommandService tonyService;
	
	
	
	/**
	 * json 列表页面
	 * @param url
	 * @param classifyId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/getQueryList.json") 
	public void  dataList(VideoModel model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		SysUser suser =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+model.getUsername());
		
		
		if(SuperAdmin.YES.key ==  suser.getIsadmin()){//超级管理员
			model.setIsadmin(SuperAdmin.YES.key);
		}else{
			model.setIsadmin(SuperAdmin.NO.key);
			model.setClassid(suser.getSchoolids());
		}
		
		HtmlUtil.writerJson(response, videoService.queryByList(model));
	}
	
	/**
	 * 添加或修改数据
	 * @param url
	 * @param classifyId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/save.json")
	public void save(VideoModel bean,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		SysUser suser =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+bean.getUsername());
		
		
		bean.setAdduser(suser.getId());
		
		int ret=0;
		
		if(StringUtils.trimToEmpty(bean.getGuid()).length() == 0){
			bean.setGuid(UUID.randomUUID().toString());
			ret=videoService.add(bean);
		}else{
			ret=videoService.update(bean);
		}

		//显示屏的对应关系
		lcdRelService.addLcdRel(bean.getGuid(), bean.getClassid());
		
		if(ret>0){
			sendSuccessMessage(response, "保存成功~");
			
			bean.setAddtime(DateUtil.getNowPlusTime());
			
			//发送给屏幕端
			tonyService.sendCommandToServer(Command.COMMAND_VIDEO, bean,bean.getClassid());
			
			String delStr=CommonUtil.compare(bean.getClassid(), bean.getOldclassid());
			if(delStr.length()>0){
				tonyService.sendCommandToServer(Command.COMMAND_DEL_VIDEO, bean.getGuid(),delStr);
			}
			
		}else{
			sendFailureMessage(response, "保存失败");
		}
		
		
	}
	
	@RequestMapping("/getId.json")
	public void getId(String id,HttpServletResponse response) throws Exception{
		
		Map<String,Object>  context = new HashMap<String,Object>();
		VideoModel bean  = videoService.queryById(id);
		
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
	
	@RequestMapping("/delete.json")
	public void delete(String id,HttpServletResponse response) throws Exception{
		
		//发送给屏幕端
		tonyService.sendDeleteToServer(Command.COMMAND_DEL_VIDEO,id);
		
		int ret =videoService.deleteBactch(id.split(","));
		
		if(ret>0){
			sendSuccessMessage(response, "删除成功");
		}else{
			this.sendFailureMessage(response, "删除失败");
		}
	}
	
}
