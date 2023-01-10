
package com.inch.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.inch.model.LcdModel;
import com.inch.model.LcdOnOffModel;
import com.inch.utils.BuidRequest;
import com.inch.utils.TonyResult;
 
@Controller
@RequestMapping("/lcd") 
public class LcdAction extends BaseAction{
	
	private final static Logger log= Logger.getLogger(LcdAction.class);
	@RequestMapping("/list")
	public ModelAndView  list(LcdModel model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = getRootMap();
		return forword("lcd/lcdinfo",context); 
	}
	
	
	@RequestMapping("/dataList")
	public void  dataList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		BuidRequest.sendRequest(request,response,"lcd/getQueryList.json","get");
	}
	
	@RequestMapping("/save")
	public void save(HttpServletResponse response,HttpServletRequest request) throws Exception{
		BuidRequest.sendRequest(request,response,"lcd/save.json","post");
	}
	
	@RequestMapping("/saveOnOff")
	public void saveOnOff(HttpServletResponse response,HttpServletRequest request) throws Exception{
		BuidRequest.sendRequest(request,response,"lcd/saveOnOff.json","post");
	}
	
	//---------------------------设备初始化数据------------------------------------
	@RequestMapping("/lcdInitData")
	public void lcdInitData(HttpServletResponse response,HttpServletRequest request) throws Exception{
		BuidRequest.sendRequest(request,response,"lcd/lcdInitData.json","post");
	}
	
	
	@RequestMapping("/getId")
	public void getId(String id,HttpServletResponse response,HttpServletRequest request) throws Exception{
		BuidRequest.sendRequest(request,response,"lcd/getId.json","post");
	}
	
	@RequestMapping("/delete")
	public void delete(HttpServletResponse response,HttpServletRequest request) throws Exception{
		BuidRequest.sendRequest(request,response,"lcd/delete.json","post");
	}
}
