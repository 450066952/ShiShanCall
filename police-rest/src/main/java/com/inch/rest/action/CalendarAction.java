
package com.inch.rest.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inch.model.CalendarModel;
import com.inch.model.SysUser;
import com.inch.rest.service.CalendarService;
import com.inch.rest.utils.RedisUtil;
import com.inch.utils.CommonUtil;
import com.inch.utils.HtmlUtil;
import com.inch.utils.WebConstant;
 
@Controller
@RequestMapping("/fullcalendar") 
public class CalendarAction extends BaseAction{
	
	private final static Logger log= Logger.getLogger(CalendarAction.class);
	
	@Autowired
	private CalendarService<CalendarModel> calendarService; 
	
	
	@RequestMapping("/getId.json") 
	public void  event(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = new HashMap<String,Object>();
		
		int id=CommonUtil.parseInt(request.getParameter("id"));
		
		CalendarModel bean=null;
		
		if(id>0){
			bean  = calendarService.queryById(id);
			context.put("data", bean);
		}else{
			bean  = new CalendarModel();
			context.put("data", bean);
		}
		
		context.put(SUCCESS, true);
		context.put(MSG, "OK");
		context.put("data", bean);
		HtmlUtil.writerJson(response, context);
	}
	
	
	/**
	 * json 列表页面
	 * @param url
	 * @param classifyId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/getQueryList.json") 
	public void  dataList(CalendarModel model ,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		SysUser suser =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+model.getUsername());
		model.setSchoolid(suser.getSchoolid());
		
		List<CalendarModel> dataList = calendarService.queryByListCalendar(model);
		
		HtmlUtil.writerJson(response, dataList);
	}
	
	/**
	 * 添加或修改数据
	 * @param url
	 * @param classifyId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/save.json")
	public void save(CalendarModel bean,HttpServletRequest request,HttpServletResponse response) 
			throws Exception{
		
		SysUser suser =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+bean.getUsername());
		
		if("1".equals(bean.getIson())){
			//关机
			bean.setColor("#ee3939");
			bean.setTitle("关机");
		}else{
			bean.setColor("#27c24c");
			bean.setTitle("开机");
		}
		
		
		
		if(StringUtils.trimToEmpty(bean.getEnd()).length()==0){
			bean.setEnd(bean.getStart());
		}
		
		bean.setAdduser(suser.getId());
		bean.setSchoolid(suser.getSchoolid());
		
		if(bean.getId() == null||bean.getId()==0){
			calendarService.add(bean);
		}else{
			calendarService.update(bean);
		}
		
		sendSuccessMessage(response, "保存成功~");
	}
	
	@RequestMapping("/delete.json")
	public void delete(Integer[] id,HttpServletResponse response) throws Exception{
		calendarService.delete(id);
		sendSuccessMessage(response, "删除成功");
	}
}
