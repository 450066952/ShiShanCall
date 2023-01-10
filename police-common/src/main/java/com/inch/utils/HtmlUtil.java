package com.inch.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * <br>
 * <b>功能：</b>详细的功能描述<br>
 * <b>作者：</b>Tony<br>
 * <b>日期：</b> Dec 14, 2011 <br>
 * <b>更新者：</b><br>
 * <b>日期：</b> <br>
 * <b>更新内容：</b><br>
 */
public class HtmlUtil {
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>输出json格式<br>
	 * <b>作者：</b>Tony<br>
	 * <b>日期：</b> Dec 14, 2011 <br>
	 * @param response
	 * @param jsonStr
	 * @throws Exception
	 */
	public static void writerStr(HttpServletResponse response,String jsonStr) {
		try {
			response.setContentType("application/json");//  add by tony	
			writer(response,jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 对象输出
	 * @param response
	 * @param object
	 */
	public static void writerJson(HttpServletResponse response,Object object){
			try {
				response.setContentType("application/json");
				writer(response,FastJsonUtils.toJsonWithNUll(object));
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * 对象输出
	 * @param response
	 * @param object
	 */
	public static void writerWithOutJson(HttpServletResponse response,Object object){
			try {
				response.setContentType("application/json");
				writer(response,FastJsonUtils.toJson(object));
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * add by tony ---------过滤某些字段
	 * @param response
	 * @param object
	 */
	public static void writerJsonFiter(HttpServletResponse response,Object object,Map map){
		try {
			response.setContentType("application/json");
			writer(response,FastJsonUtils.toJsonFiter(object, map));
		} catch (Exception e) {
			e.printStackTrace();
		}
}
	
	/**
	 * 
	 * <br>
	 * <b>功能：</b>输出HTML代码<br>
	 * <b>作者：</b>Tony<br>
	 * <b>日期：</b> Dec 14, 2011 <br>
	 * @param response
	 * @param htmlStr
	 * @throws Exception
	 */
	public static void writerHtml(HttpServletResponse response,String htmlStr) {
		writer(response,htmlStr);
	}
	
	private static void writer(HttpServletResponse response,String str){
		try {
			//设置页面不缓存
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out= null;
			out = response.getWriter();
			out.print(str);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String str="%E4%B8%8A%E8%AF%BE%E6%9C%9F%E9%97%B4%E9%81%B5%E5%AE%88%E7%BA%AA%E5%BE%8B";
		System.out.println(strDecode(str));
	}
	
	public static String strDecode(String str) {
		
		if(str==null){return "";}
		
		String encord="";
		try {
			encord=URLDecoder.decode(str,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			encord=str;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return encord;
	}
	
	public static String paramset(String str){
		
		str = str.replaceAll( "&", "&amp;");
		str = str.replaceAll( "\"", "&quot;");  //"
		str = str.replaceAll( "\t", "&nbsp;&nbsp;");// 替换跳格
		str = str.replaceAll( " ", "&nbsp;");// 替换空格
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll( ">", "&gt;");
		return str;
	}
	
	public static String formatDate(String date)
	  {
	    String strDate = date;
	    String pat1 = "yyyy-MM-dd HH:mm:ss";
	    String pat2 = "yyyy-MM-dd HH:mm";
	    SimpleDateFormat sdf1 = new SimpleDateFormat(pat1);
	    SimpleDateFormat sdf2 = new SimpleDateFormat(pat2);
	    Date d = null;
	    try {
	      d = sdf1.parse(strDate);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return sdf2.format(d);
	  }
	
	public static String strEncode(String str) {
		String encord="";
		try {
			encord=URLEncoder.encode(str,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			encord=str;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return encord;
	}
}
