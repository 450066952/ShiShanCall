package com.inch.rest.utils;


import com.inch.model.SysUser;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * Cookie 工具类
 * 
 * getSession()
 *
 */
public final class SessionUtils {
	
	protected static final Logger logger = Logger.getLogger(SessionUtils.class);
	
	private static final String SESSION_JSON_USER = "session_json_user";

	public static final Map<String, SysUser> REST_USER = new ConcurrentHashMap();
	
	/**
	  * 设置session的值
	  * @param request
	  * @param key
	  * @param value
	  */
	 public static void setAttr(HttpServletRequest request,String key,Object value){
		 request.getSession().setAttribute(key, value);
	 }
	 
	 
	 /**
	  * 获取session的值
	  * @param request
	  * @param key
	  * @param value
	  */
	 public static Object getAttr(HttpServletRequest request,String key){
		 return request.getSession().getAttribute(key);
	 }
	 
	 /**
	  * 删除Session值
	  * @param request
	  * @param key
	  */
	 public static void removeAttr(HttpServletRequest request,String key){
		 request.getSession().removeAttribute(key);
		 
	 }
	 
	 /**
	  * 设置用户信息 到session
	  * @param request
	  * @param user
	  */
	 public static void setUser(HttpServletRequest request,SysUser user){
		 request.getSession().setAttribute(SESSION_JSON_USER, user);
		 System.out.println(request.getSession().getAttribute(SESSION_JSON_USER));
		 System.out.println("1111");
	 }
	 
	 
	 /**
	  * 从session中获取用户信息
	  * @param request
	  * @return SysUser
	  */
	 public static SysUser getUser(HttpServletRequest request){
		return (SysUser)request.getSession().getAttribute(SESSION_JSON_USER);
	 }
	 
	 
	 /**
	  * 从session中获取用户信息
	  * @param request
	  * @return SysUser
	  */
	 public static void removeUser(HttpServletRequest request){
		removeAttr(request, SESSION_JSON_USER);
	 }
	 
//	 
//	 
//	 /**
//	  * 判断当前登录用户是否超级管理员
//	  * @param request
//	  * @return
//	  */
//	 public static boolean isAdmin(HttpServletRequest request){ //判断登录用户是否超级管理员
//		 SysUser user =  getUser(request);
//		 if(user == null  || user.getIsadmin() != SuperAdmin.YES.key){
//			 return false;
//		 }
//		 return true;
//	 }
}