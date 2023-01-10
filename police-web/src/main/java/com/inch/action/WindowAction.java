
package com.inch.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.inch.annotation.Auth;
import com.inch.model.SchoolModel;
import com.inch.model.SysDicModel;
import com.inch.utils.BuidRequest;
import com.inch.utils.FastJsonUtils;
import com.inch.utils.TonyResult;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/window")
public class WindowAction extends BaseAction{

	@RequestMapping("/list")
	public ModelAndView  list(HttpServletRequest request,HttpServletResponse response) throws Exception{

		Map<String,Object>  context = getRootMap();

		String json=BuidRequest.getRequestResult(request,response,"school/getOrgLists","get");
		TonyResult t = TonyResult.formatToList(json,SchoolModel.class);
		context.put("orglist", t.getData());

		return forword("window/window",context);
	}

	@Auth(verifyURL = false)
	@RequestMapping("/dataList")
	public void  dataList(HttpServletResponse response,HttpServletRequest request) throws Exception{
		BuidRequest.sendRequest(request,response,"window/getQueryList","get");
	}

	@Auth(verifyURL = false)
	@RequestMapping("/opList")
	public void  opList(HttpServletResponse response,HttpServletRequest request) throws Exception{
		BuidRequest.sendRequest(request,response,"window/opList","get");
	}
	
	@RequestMapping("/save")
	public void save(HttpServletRequest request,HttpServletResponse response) throws Exception{
		BuidRequest.sendRequest(request,response,"window/save","post");
	}
	
	@RequestMapping("/getId")
	public void getId(HttpServletRequest request,HttpServletResponse response) throws Exception{
		BuidRequest.sendRequest(request,response,"window/getId","post");
	}
	
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request,HttpServletResponse response) throws Exception{
		BuidRequest.sendRequest(request,response,"window/delete","post");
	}
	
}
