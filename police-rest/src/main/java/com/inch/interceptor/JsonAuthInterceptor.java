package com.inch.interceptor;

import com.inch.model.SysUser;
import com.inch.rest.service.UserinfoService;
import com.inch.rest.utils.RedisUtil;
import com.inch.utils.HtmlUtil;
import com.inch.utils.WebConstant;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 权限拦截器
 * @author lu
 *
 */
public class JsonAuthInterceptor extends HandlerInterceptorAdapter {
	private final static Logger log= Logger.getLogger(JsonAuthInterceptor.class);
	
	@Autowired
	UserinfoService<SysUser> userService;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HandlerMethod method = (HandlerMethod)handler;
		Auth  auth = method.getMethod().getAnnotation(Auth.class);

//		//第一步：验证签名是否正确
//		if(!DataSign.checkIsValid(request)){
//			Map<String, Object> result = new HashMap<String, Object>();
//			result.put("success", false);
//			result.put("errcode", "签名不正确.");
//			result.put("errmessage", "签名不正确.");
//			HtmlUtil.writerJson(response, result);
//			return false;
//		}

		//验证登陆超时问题  auth = null，默认验证
		if( auth == null || auth.verifyLogin()){
			SysUser user = null;
			SysUser model = new SysUser();
			model.setUsername(StringUtils.trimToEmpty(request.getParameter("username")));
			model.setPassword(StringUtils.trimToEmpty(request.getParameter("password")));

			String userName = StringUtils.trimToEmpty(request.getParameter("userName"));

			System.out.println("userName2 = " + userName);

			if (StringUtils.isNotBlank(userName)){
				user = userService.getSysUserInfoSSO(userName.substring(2,8));
				if(user==null){
					Map<String, Object> result = new HashMap<>();
					result.put("success", false);
					result.put("msg", "用户不存在！");
					HtmlUtil.writerJson(response, result);
					return false;
				}else {
					user = userService.getSysUserInfo(user);
					RedisUtil.setObjectByTime(WebConstant.WEBUSER + user.getUsername(), user, 24*60*60);
				}
			}else {
				if(model.getUsername().length()==0){
					Map<String, Object> result = new HashMap<String, Object>();
					result.put("success", false);
					result.put("msg", "用户名不能为空.");
					HtmlUtil.writerJson(response, result);
					return false;
				}

				if(model.getPassword().length()==0){
					Map<String, Object> result = new HashMap<String, Object>();
					result.put("success", false);
					result.put("msg", "密码不能为空.");
					HtmlUtil.writerJson(response, result);
					return false;
				}

				//修改密码的情况----要重新验证用户名密码
				if("modifypwd".equals(method.getMethod().getName())){
					//删除redis缓存
					RedisUtil.del(WebConstant.WEBUSER+model.getUsername());
				}else{
					user =(SysUser)RedisUtil.getObjByKey(WebConstant.WEBUSER+model.getUsername());
				}

				if(user  == null){
					user=userService.getSysUserInfo(model);

					if(user == null){
						Map<String, Object> result = new HashMap<String, Object>();
						result.put("success", false);
						result.put("msg", "用户名或密码不正确.");
						HtmlUtil.writerJson(response, result);
						return false;
					}else{

						user.setWinid("");

						//验证用户名密码是否正确
						if(!user.getPassword().equalsIgnoreCase(model.getPassword())){
							Map<String, Object> result = new HashMap<String, Object>();
							result.put("success", false);
							result.put("msg", "密码不正确.");
							HtmlUtil.writerJson(response, result);
							return false;
						}
						//设置过期时间为1天---单位是秒
						RedisUtil.setObjectByTime(WebConstant.WEBUSER+model.getUsername(), user, -1);
					}
				}else{
					if(!user.getPassword().equalsIgnoreCase(model.getPassword())){

						Map<String, Object> result = new HashMap<String, Object>();
						result.put("success", false);
						result.put("msg", "密码不正确.");
						HtmlUtil.writerJson(response, result);
						return false;
					}
				}
			}

		}
		
		return super.preHandle(request, response, handler);
	}

	
}
