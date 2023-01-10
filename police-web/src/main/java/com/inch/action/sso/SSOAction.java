package com.inch.action.sso;

import com.inch.action.BaseAction;
import com.inch.annotation.Auth;
import com.inch.model.SysUser;
import com.inch.utils.BuidRequest;
import com.inch.utils.HtmlUtil;
import com.inch.utils.SessionUtils;
import com.inch.utils.TonyResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 紫橙科技专用
 */

@Controller
@RequestMapping("/sso")
public class SSOAction extends BaseAction {

	private static final Logger log = LoggerFactory.getLogger(SSOAction.class);

	@Auth(verifyURL = false,verifyLogin = false)
	@RequestMapping("/caslogin")
	public ModelAndView list(HttpServletRequest	request) throws Exception{
		Map<String,Object> context = getRootMap();
		return forword("sso/caslogin",context);
	}

	@Auth(verifyURL=false,verifyLogin = false)
	@RequestMapping("/toLogin")
	public void  toLogin(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> jsonMap = new HashMap<>();
		String userName = request.getParameter("userName");

		if(StringUtils.isBlank(userName)){
			sendFailureMessage(response, "参数不完整");
		}

		System.out.println("userName0 = " + userName);

		String msg = "单点登录日志:";
		
		String json= BuidRequest.getRequestResult(request,response,"sys/toLogin.json","post");
		TonyResult taotaoResult = TonyResult.formatToPojo(json, SysUser.class);

		if(taotaoResult.getSuccess()){
			SysUser user =(SysUser)taotaoResult.getData();
			user.setPassword(user.getPassword());
			SessionUtils.setUser(request,user);
		}else{
			sendFailureMessage(response, taotaoResult.getMsg());
		}
		
		log.debug(msg+"["+userName+"]"+"登录成功");
		request.getSession().setAttribute("ctx", request.getContextPath());

		jsonMap.put(SUCCESS, true);
		jsonMap.put(MSG, "OK");

		HtmlUtil.writerJson(response, jsonMap);
	}
}
