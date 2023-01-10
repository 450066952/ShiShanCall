
package com.inch.action;

import com.inch.utils.BuidRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/schoolnews")
public class SchoolNewsAction extends BaseAction {
	

	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) throws Exception{
		Map<String,Object>  context = getRootMap();
		return forword("schoolnews/schoolnewsinfo",context); 
	}
	

	@RequestMapping("/dataList")
	public void  dataList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		BuidRequest.sendRequest(request,response,"schoolnews/getQueryList.json","get");
	}
	
	@RequestMapping("/save")
	public void save(HttpServletRequest request,HttpServletResponse response) throws Exception{
		BuidRequest.sendRequest(request,response,"schoolnews/save.json","post");
	}
	
	@RequestMapping("/getId")
	public void getId(HttpServletRequest request,HttpServletResponse response) throws Exception{
		BuidRequest.sendRequest(request,response,"schoolnews/getId.json","post");
	}
	
	@RequestMapping("/delete")
	public void delete(String[] id,HttpServletRequest request,HttpServletResponse response) throws Exception{
		BuidRequest.sendRequest(request,response,"schoolnews/delete.json","post");
	}
}
