
package com.inch.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.inch.model.GalleryModel;
import com.inch.utils.BuidRequest;
 
@Controller
@RequestMapping("/pic") 
public class PicAction extends BaseAction{

	/**
	 * ilook 首页
	 * @param url
	 * @param classifyId
	 * @return
	 */
	@RequestMapping("/list") 
	public ModelAndView  list(GalleryModel model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = getRootMap();
		
		return forword("pic/picinfo",context); 
	}
	
	/**
	 * json 列表页面
	 * @param url
	 * @param classifyId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/dataList") 
	public void  dataList(HttpServletResponse response,HttpServletRequest request) throws Exception{
		//从rest服务去获取数据
		BuidRequest.sendRequest(request,response,"pic/getQueryList.json","get");
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
		BuidRequest.sendRequest(request,response,"pic/save.json","post");
	}
	
	@RequestMapping("/getId")
	public void getId(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//从rest服务去获取数据
		BuidRequest.sendRequest(request,response,"pic/getId.json","post");
	}
	
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//从rest服务去获取数据
		BuidRequest.sendRequest(request,response,"pic/delete.json","post");
	}
	
}
