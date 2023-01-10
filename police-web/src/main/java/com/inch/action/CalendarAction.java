
package com.inch.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import com.inch.annotation.Auth;
import com.inch.model.CalendarModel;
import com.inch.model.LcdOnOffModel;
import com.inch.utils.BuidRequest;
import com.inch.utils.TonyResult;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
 
@Controller
@RequestMapping("/fullcalendar") 
public class CalendarAction extends BaseAction{
	
	private final static Logger log= Logger.getLogger(CalendarAction.class);
	
	@RequestMapping("/list")
	public ModelAndView  list(CalendarModel model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = getRootMap();
		
		String json=BuidRequest.getRequestResult(request,response,"lcd/getLcdOnOff.json","get");
		TonyResult taotaoResult = TonyResult.formatToPojo(json, LcdOnOffModel.class);
		context.put("sinfo", taotaoResult.getData());
		
		return forword("fullcalendar/calendar",context); 
	}
	
	@RequestMapping("/event") 
	@Auth(verifyURL=false)
	public ModelAndView  event(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = getRootMap();
		
		//从rest服务去获取数据
		String json=BuidRequest.getRequestResult(request,response,"fullcalendar/getId.json","get");
		TonyResult taotaoResult = TonyResult.formatToPojo(json, CalendarModel.class);
		
		context.put("data", taotaoResult.getData());
		
		return forword("fullcalendar/event",context); 
	}

	@RequestMapping("/dataList")
	public void  dataList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		BuidRequest.sendRequest(request,response,"fullcalendar/getQueryList.json","get");
	}
	@RequestMapping("/save")
	public void save(CalendarModel bean,HttpServletRequest request,HttpServletResponse response) 
			throws Exception{
		BuidRequest.sendRequest(request,response,"fullcalendar/save.json","post");
	}
	@RequestMapping("/delete")
	public void delete(HttpServletResponse response,HttpServletRequest request) throws Exception{
		BuidRequest.sendRequest(request,response,"fullcalendar/delete.json","post");
	}
}
