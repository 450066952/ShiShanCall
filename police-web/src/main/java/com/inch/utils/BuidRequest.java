package com.inch.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;

import com.inch.model.SysUser;
import com.inch.utils.HtmlUtil;
import com.inch.utils.HttpClientUtil;
import com.inch.utils.MD5;
import com.inch.utils.WebConstant;

public class BuidRequest {
	
	public static  String REST_BASE_URL="";

	static {
        try {
        	PropertiesConfiguration jconfig=new PropertiesConfiguration("config.properties");
        		
        	REST_BASE_URL=jconfig.getString("restserver");
        	
        } catch (Exception e) {
        	
        	e.printStackTrace();
        }
    }
	
	
	
	/**
	 * 
	 * 
	 * 遍历request参数
	 * @param request
	 * @return
	 */
	public static Map<String, Object> buildReQuestMap(HttpServletRequest request){
		
		SysUser suser=SessionUtils.getUser(request);
		
		Map<String,Object> map=new TreeMap<String,Object>();
		Enumeration<?> paramNames = request.getParameterNames();  
		   
		String paramName="";
		String paramValue="";
		
		StringBuffer sb=new StringBuffer(WebConstant.SECRET);
		
		
	    for (Enumeration<?> e = paramNames ; e.hasMoreElements() ;) {
	    	 paramName=StringUtils.trimToEmpty(e.nextElement().toString());
	    	 
	    	 String[] paramValues = request.getParameterValues(paramName);  
	    	 
	         if (paramValues.length == 1) {  
	        	 paramValue = paramValues[0];  
	        	 
	        	 if("password".equals(paramName)){
	        		 paramValue = MD5.getMD5String(paramValues[0]);  
		    	 }else{
		    		 paramValue = paramValues[0];   
		    	 }
	        	 
	             map.put(paramName, paramValue);  
	             
	         }else{
	        	
	        	 paramValue="";
	        	 
	        	 for (String s : paramValues) {
		        		 paramValue+=s+",";
				 }
	        	 
	        	 if(paramValue.length()>0){
	        		 paramValue=StringUtils.substringBeforeLast(paramValue, ",");
	        	 }
	        	 
	        	 map.put(paramName, paramValue);
	         } 
	    }
	    
	    	
	    	if(suser!=null){
	    		map.put("username", suser.getUsername());
	    		
	    		if(map.containsKey("newpwd")){
//	    			//主要是修改密码的时候使用
	    			map.put("newpwd", MD5.getMD5String(map.get("newpwd").toString()));
					map.put("password", MD5.getMD5String(map.get("oldpwd").toString()));
	    		}else{
	    			map.put("password", suser.getPassword());
	    		}
	    		
	    	}
	    
	    
	    //将接收到参数放在treep进行排序再加密------需注意 如是数据比较大--不进行数据签名？
	    for (String key : map.keySet()) {  
	    	 if(!"sign".equals(key)){
	    		 
	    		 if(String.valueOf(map.get(key)).length()<=200){//字数大于200的不进行加密
	    			 sb.append("&"+key+"="+map.get(key));
	    		 }
	    	 }
		}
	    
	    
	    
	    sb.append("&"+WebConstant.SECRET);
	    map.put("sign", MD5.getMD5String(sb.toString()));
	    
		return map;

	}
	
	/**
	 * 发送请求通用构造函数
	 * @param request
	 * @param response
	 * @param url
	 * @param type
	 */
	public static String sendRefreshUser(HttpServletRequest request,String url,String delusername){
		String json ="";
		Map<String, Object> map=new HashMap<String, Object>();
		SysUser suser=SessionUtils.getUser(request);
		
		if(suser!=null){
		    map.put("username", suser.getUsername());
		    map.put("password", suser.getPassword());
		}
		
		map.put("delusername", delusername);
		
		json=HttpClientUtil.doPost(REST_BASE_URL + url,map);
		
		return json;
	}
	
	/**
	 * 发送请求通用构造函数
	 * @param request
	 * @param response
	 * @param url
	 * @param type
	 */
	public static void sendRequest(HttpServletRequest request,HttpServletResponse response,String url,String type){
		String json ="";
		
		if("post".equals(type)){
			 json = HttpClientUtil.doPost(REST_BASE_URL + url,buildReQuestMap(request));
		}else{
			 json = HttpClientUtil.doGet(REST_BASE_URL + url,buildReQuestMap(request));
		}
				
		HtmlUtil.writerStr(response, json);
	}
	
	/**
	 * 发送请求通用构造函数
	 * @param request
	 * @param response
	 * @param url
	 * @param type
	 */
	public static String getRequestResult(HttpServletRequest request,HttpServletResponse response,String url,String type){
		String json ="";
		
		if("post".equals(type)){
			 json = HttpClientUtil.doPost(REST_BASE_URL + url,buildReQuestMap(request));
		}else{
			 json = HttpClientUtil.doGet(REST_BASE_URL + url,buildReQuestMap(request));
		}
		
		return json;
	}
}
