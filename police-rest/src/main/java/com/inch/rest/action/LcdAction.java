
package com.inch.rest.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.inch.interceptor.Auth;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inch.model.LcdModel;
import com.inch.model.LcdOnOffModel;
import com.inch.model.SchoolModel;
import com.inch.model.SysUser;
import com.inch.rest.service.LcdOnOffService;
import com.inch.rest.service.LcdService;
import com.inch.rest.service.SchoolService;
import com.inch.rest.utils.RedisUtil;
import com.inch.utils.DateUtil;
import com.inch.utils.HtmlUtil;
import com.inch.utils.WebConstant;
import com.inch.utils.WebConstant.SuperAdmin;
import com.socket.server.command.dto.ReauestLoginDto;
import com.socket.server.command.service.TonyCommandService;
import com.socket.server.socket.pub.CharacterHelper;
import com.socket.server.socket.pub.Command;
 
@Controller
@RequestMapping("/lcd") 
public class LcdAction extends BaseAction{
	
	private final static Logger log= Logger.getLogger(LcdAction.class);
	
	@Autowired
	private LcdService<LcdModel> lcdService; 
	
	@Autowired
	private SchoolService<SchoolModel> schoolService;
	
	@Autowired
	private LcdOnOffService<LcdOnOffModel> lcdOnOffService;
	
	@Autowired
	private TonyCommandService tonyService;
	
	@RequestMapping("/getQueryList.json")
	public void  dataList(LcdModel model,HttpServletResponse response) throws Exception{
		
		SysUser suser =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+model.getUsername());
		
		if(SuperAdmin.YES.key ==  suser.getIsadmin()){
			model.setIsadmin(SuperAdmin.YES.key);
		}else{
			model.setIsadmin(SuperAdmin.NO.key);
			
			model.setClassname(suser.getSchoolids());
		}
		
		List<LcdModel> dataList = lcdService.queryByListT(model);
		if(dataList!=null){
			ReauestLoginDto dto=null;
			for(int i=0;i<dataList.size();i++){
				dto=CharacterHelper.ALL_LOGIN_USER.get(dataList.get(i).getGuid());
				if(dto==null){
					dataList.get(i).setState("0");
					dataList.get(i).setVersion("");
				}else{
					dataList.get(i).setState("1");
					dataList.get(i).setVersion(dto.getVersion());
				}
			}
		}
		
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("data",dataList);
		jsonMap.put("draw", model.getDraw());
		HtmlUtil.writerJson(response, jsonMap);
	}
	
	@RequestMapping("/save")
	public void save(LcdModel bean,HttpServletResponse response,HttpServletRequest request) throws Exception{
		
		int schoolid=0;int glassid=0;int classid=0;int ret;
		List<SchoolModel> plist=schoolService.getAllParent(bean.getClassid());//所有父节点
		
		if(plist.size()==3){
			for(int i=0;i<plist.size();i++){
				if(plist.get(i).getPid()==0){
					schoolid=plist.get(i).getId();
				}else if(bean.getClassid()==plist.get(i).getId()){
					classid=plist.get(i).getId();
				}else{
					glassid=plist.get(i).getId();
				}
			}
		}else if(plist.size()==2){
			for(int i=0;i<plist.size();i++){
				if(plist.get(i).getPid()==0){
					schoolid=plist.get(i).getId();
				}else{
					glassid=plist.get(i).getId();
				}
			}
		}else if(plist.size()==1){
			schoolid=plist.get(0).getId();
		}
		
		bean.setSchoolid(schoolid);
		bean.setGradeid(glassid);
		bean.setClassid(classid);
		
		SysUser suser =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+StringUtils.trimToEmpty(request.getParameter("username")));

		bean.setAdduser(suser.getId());
		
		if(StringUtils.trimToEmpty(bean.getGuid()).length() == 0){
			bean.setGuid(UUID.randomUUID().toString());
			ret=lcdService.add(bean);
		}else{
			ret=lcdService.update(bean);

		}

		if(ret>0){
			RedisUtil.del(WebConstant.SCREENUSER+bean.getGuid());//删除redis缓存
			lcdService.addLcdWindow(bean.getGuid(),bean.getWinguid());
		}
		
		tonyService.sendCommandToDevice(Command.COMMAND_MODEL, bean.getModel(),bean.getGuid());
		
		RedisUtil.del(WebConstant.SCREENUSER+bean.getGuid());
		
		sendSuccessMessage(response, "保存成功~");
	}

	@Auth(verifyLogin = false)
	@RequestMapping("/getId")
	public void getId(String id,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap<String,Object>();
		LcdModel bean  = lcdService.queryById(id);
		if(bean  == null){
			sendFailureMessage(response, "没有找到对应的记录!");
			return;
		}

		bean.setWinguids(StringUtils.split(bean.getWinguid(),","));

		context.put(SUCCESS, true);
		context.put("data", bean);
		HtmlUtil.writerJson(response, context);
	}
	
	@RequestMapping("/delete")
	public void delete(String[] id,HttpServletResponse response) throws Exception{
		lcdService.delete(id);
		
		if(id!=null){
			for(Object did : id ){
				RedisUtil.del(WebConstant.SCREENUSER+did);//删除redis缓存
			}
		}
		
		sendSuccessMessage(response, "删除成功");
	}
	
	//---------------------------设备初始化数据------------------------------------
	@RequestMapping("/lcdInitData")
	public void lcdInitData(String id,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap<String,Object>();
		LcdModel bean  = lcdService.queryById(id);
		if(bean  == null){
			sendFailureMessage(response, "没有找到对应的记录!");
			return;
		}
		context.put(SUCCESS, true);
		context.put("data", bean);
		HtmlUtil.writerJson(response, context);
		
		
		//发送给屏幕端------进行初始化
		tonyService.sendCommandToServer(Command.COMMAND_SEND_INIT, "",bean.getClassid()+"");
	}
	
	//---------------------------以下为设置设备开关机--( 获取开关机时间)---------------------------------------------------
	@RequestMapping("/getLcdOnOff")
	public void getLcdOnOff(HttpServletResponse response,HttpServletRequest request) throws Exception{

		SysUser suser =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+StringUtils.trimToEmpty(request.getParameter("username")));
		
		LcdOnOffModel c=lcdOnOffService.queryById(suser.getSchoolid());
		
		if(c==null){
			c=new LcdOnOffModel();
		}
		
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put(SUCCESS, true);
		jsonMap.put(MSG, "OK");
		jsonMap.put("data",c);
		
		HtmlUtil.writerJson(response, jsonMap);
		
	}
	
	//---------------------------以下为设置设备开关机--(设置开关机时间)---------------------------------------------------	
	@RequestMapping("/saveOnOff")
	public void saveOnOff(LcdOnOffModel model,HttpServletResponse response,HttpServletRequest request) throws Exception{

		SysUser suser =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+model.getUsername());
		
		model.setSchoolid(suser.getSchoolid());
		model.setAdduser(suser.getId());
		
		int ret=0;
		
		//if(StringUtils.trimToEmpty(model.getGuid()).length()>0){
		//	ret=lcdOnOffService.update(model);
		//}else{
		//	model.setGuid(UUID.randomUUID().toString());
		//	ret=lcdOnOffService.add(model);
		//}
		
		model.setGuid(UUID.randomUUID().toString());
		ret=lcdOnOffService.saveLcdOnOff(model.getSchoolid(),model);
		
//		lcdOnOffService.delete(model.getSchoolid());
//		ret=lcdOnOffService.add(model);
		
		if(ret>0){
			
			Map<String,Object> jsonMap = new HashMap<String,Object>();
			jsonMap.put(SUCCESS, true);
			jsonMap.put(MSG, "保存成功~");
			jsonMap.put("data",model);
			
			HtmlUtil.writerJson(response, jsonMap);
			
			
			model.setAddtime(DateUtil.getNowPlusTime());
			//发送给屏幕端
			tonyService.sendCommandToServer(Command.COMMAND_ONOFF_TIME, model,model.getSchoolid()+"");

		}else{
			sendFailureMessage(response, "保存失败~");
		}
	}
}
