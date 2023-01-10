package com.inch.rest.utils;

import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.inch.utils.MD5;
import com.inch.utils.WebConstant;

public class DataSign {

	/**
	 * 数字签名认证
	 * 例如： 前后加上secret
	 * a=1&b=3&c=5&d=6&
	 * 
	 * secret&param&secret
	 * secret&a=1&b=3&c=5&d=6&secret
	 * @param request
	 * @return
	 */
	public static Boolean checkIsValid(HttpServletRequest request) {

		String sign=StringUtils.trimToEmpty(request.getParameter("sign"));
		
		if(sign.length()==0){
			return false;
		}
		
		
		String paramName="";
		String paramValue="";
		StringBuffer sb=new StringBuffer(WebConstant.SECRET);
		
		request.getParameterMap();
		Map<String,String> map=new TreeMap<String,String>();
		
		Enumeration<?> paramNames = request.getParameterNames();  
	   
	    for (Enumeration<?> e = paramNames ; e.hasMoreElements() ;) {
	    	 paramName=StringUtils.trimToEmpty(e.nextElement().toString());
	    	 paramValue=StringUtils.trimToEmpty(request.getParameter(paramName));
	         
	    	 map.put(paramName, paramValue);
	    }
	    
	    //将接收到参数放在treep进行排序再加密------需注意 如是数据比较大--不进行数据签名？
	    for (String key : map.keySet()) {  
	    	 if(!"sign".equals(key)){
	    		 if(map.get(key).length()<=200){//字数大于200的不进行加密
	    			 sb.append("&"+key+"="+map.get(key));
	    		 }
	    	 }
		}
	     
	     sb.append("&"+WebConstant.SECRET);
	     
	     if(sign.equals(MD5.getMD5String(sb.toString()))){
	    	 return true;
	     }
	     
		return false;  
	}
	
	
	public static void main(String[] args){
		String aaa="aaaa";
		String bbb="cccc";
		
		
		System.out.print(aaa.length());
		System.out.print(bbb.length());
		
//		Map<String,String> map=new HashMap<String,String>();
		
		Map<String,String> map=new TreeMap<String,String>();
		
//		for(int i=0;i<20;i++){
//			map.put("keys"+i, i+"@@@@");
//			
//			
//			action======
//				username======
//				password======
//				logintype======
//				currentphonetype======
//				currentphoneversion======
//				token======
//				ChannelId======
//				UserId======
//		}
		
		map.put("username", "1");
		map.put("password",  "1");
		map.put("logintype",  "1");
		map.put("currentphonetype",  "1");
		map.put("currentphoneversion",  "1");
		map.put("token",  "1");
		map.put("username",  "1");
		map.put("ChannelId",  "1");
		map.put("UserId",  "1");
		
		for (String key : map.keySet()) {  
			System.out.println(key);
		}
		
		System.out.println("--------------------------------------------");
//		
//		
//		Map<String,String> map2=new TreeMap<String,String>();
//		
//		for(int i=0;i<20;i++){
//			map2.put("keys"+i, i+"@@@@");
//		}
//		
//		for (String key : map2.keySet()) {  
//			System.out.println(map2.get(key));
//		}
		
		
	}
}
