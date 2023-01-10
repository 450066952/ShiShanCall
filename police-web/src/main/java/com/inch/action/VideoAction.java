
package com.inch.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.inch.utils.BuidRequest;
 
@Controller
@RequestMapping("/video") 
public class VideoAction extends BaseAction{
	
	private final static Logger log= Logger.getLogger(VideoAction.class);
	
	/**
	 * ilook 首页
	 * @param url
	 * @param classifyId
	 * @return
	 */
	@RequestMapping("/list") 
	public ModelAndView  list(HttpServletRequest request) throws Exception{
		Map<String,Object>  context = getRootMap();
		return forword("video/video",context); 
	}
	
	@RequestMapping("/list2") 
	public ModelAndView  list2(HttpServletRequest request) throws Exception{
		Map<String,Object>  context = getRootMap();
		return forword("video/video",context); 
	}
	
	
	/**
	 * json 列表页面
	 * @param url
	 * @param classifyId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/dataList") 
	public void  dataList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//从rest服务去获取数据
		BuidRequest.sendRequest(request,response,"video/getQueryList.json","get");
	}
	
	/**
	 * 添加或修改数据
	 * @param url
	 * @param classifyId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/save")
	public void save(HttpServletRequest request,HttpServletResponse response,String[] classid) throws Exception{
		//从rest服务去获取数据
		BuidRequest.sendRequest(request,response,"video/save.json","post");
	}
	
	@RequestMapping("/getId")
	public void getId(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//从rest服务去获取数据
		BuidRequest.sendRequest(request,response,"video/getId.json","post");
	}
	
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//从rest服务去获取数据
		BuidRequest.sendRequest(request,response,"video/delete.json","post");
	}
}
