
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
@RequestMapping("/notice") 
public class NoticeAction extends BaseAction{
	
	private final static Logger log= Logger.getLogger(NoticeAction.class);

	/**
	 * ilook 首页
	 * @param url
	 * @param classifyId
	 * @return
	 */
	@RequestMapping("/list") 
	public ModelAndView  list(HttpServletRequest request) throws Exception{
		Map<String,Object>  context = getRootMap();
		return forword("notice/noticeinfo",context); 
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
		BuidRequest.sendRequest(request,response,"notice/getQueryList.json","get");
	}
	
	/**
	 * 添加或修改数据
	 * @param url
	 * @param classifyId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/save")
	public void save(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//从rest服务去获取数据
		BuidRequest.sendRequest(request,response,"notice/save.json","post");
	}
	
	@RequestMapping("/getId")
	public void getId(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//从rest服务去获取数据
		BuidRequest.sendRequest(request,response,"notice/getId.json","post");
	}
	
	@RequestMapping("/delete")
	public void delete(String[] id,HttpServletRequest request,HttpServletResponse response) throws Exception{
		//从rest服务去获取数据
		BuidRequest.sendRequest(request,response,"notice/delete.json","post");
	}
	
}
