package com.inch.interceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.inch.action.BaseAction;
import com.inch.annotation.Auth;
import com.inch.model.SysUser;
import com.inch.utils.HtmlUtil;
import com.inch.utils.SessionUtils;

/**
 * 权限拦截器
 * @author lu
 *
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
	private final static Logger log= Logger.getLogger(AuthInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HandlerMethod method = (HandlerMethod)handler;
		Auth  auth = method.getMethod().getAnnotation(Auth.class);
		if( auth == null || auth.verifyLogin()){
			
			String baseUri = request.getContextPath();
			String path = request.getServletPath();
			SysUser user =SessionUtils.getUser(request);
		
			if(user  == null){
				if(path.endsWith(".shtml")){
					//response.setStatus(response.SC_GATEWAY_TIMEOUT);
//					response.sendRedirect(baseUri+"/login.shtml");
					
					throw new TimeoutException();
					
				}else{
					response.setStatus(response.SC_GATEWAY_TIMEOUT);
					Map<String, Object> result = new HashMap<String, Object>();
					result.put(BaseAction.SUCCESS, false);
					result.put(BaseAction.LOGOUT_FLAG, true);//登录标记 true 退出
					result.put(BaseAction.MSG, "登录超时.");
					HtmlUtil.writerJson(response, result);
					return false;
				}
			}
		}
		//验证URL权限
		if( auth == null || auth.verifyURL()){		
			//判断是否超级管理员
			if(!SessionUtils.isAdmin(request)){
				String menuUrl = StringUtils.remove( request.getRequestURI(),request.getContextPath());
				if(!SessionUtils.isAccessUrl(request, StringUtils.trim(menuUrl))){					
					//日志记录
					String username = SessionUtils.getUser(request).getUsername();
					String msg ="URL权限验证不通过:[url="+menuUrl+"][username ="+ username+"]" ;
					log.error(msg);
					
					response.setStatus(response.SC_FORBIDDEN);
					Map<String, Object> result = new HashMap<String, Object>();
					result.put(BaseAction.SUCCESS, false);
					result.put(BaseAction.LOGOUT_FLAG, false);//登录标记 true 退出
					result.put(BaseAction.MSG, "没有权限访问,请联系管理员.");
					HtmlUtil.writerJson(response, result);
					return false;
				}
			}
		}
		return super.preHandle(request, response, handler);
	}

	
}
