package com.inch.rest.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.inch.model.InchModel;
import com.inch.utils.HtmlUtil;

public class BaseAction{
	
	public final static String SUCCESS ="success";  
	
	public final static String MSG ="msg";  
	 
	 public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public void sendSuccessMessage(HttpServletResponse response,  String message) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SUCCESS, true);
		result.put(MSG, message);
		HtmlUtil.writerJson(response, result);
	}
	
	public void sendSuccessData(HttpServletResponse response,  Object data) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SUCCESS, true);
		result.put(MSG, "");
		result.put("data", data);
		HtmlUtil.writerJson(response, result);
	}

	public void sendFailureMessage(HttpServletResponse response,String message) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SUCCESS, false);
		result.put(MSG, message);
		HtmlUtil.writerJson(response, result);
	}


	public InchModel successData(String msg, Object obj){

		return new InchModel(true,msg,obj);
	}

	public InchModel failData(String msg,Object obj){

		return new InchModel(false,msg,obj);
	}

	public InchModel successMsg(String msg){

		return new InchModel(true,msg,null);
	}

	public InchModel failMsg(String msg){

		return new InchModel(false,msg,null);
	}
	
}
