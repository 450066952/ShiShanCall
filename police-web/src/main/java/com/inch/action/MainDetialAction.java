
package com.inch.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.inch.annotation.Auth;
 
/**
 * 这里为页面的右侧的动态加载类
 * @Title: MainDetialAction.java
 * @Package com.tony.action
 * @Description: TODO
 * @author tony
 * @Email 303976091@qq.com
 * @date 2016年8月9日
 */
@Controller
@RequestMapping("/maindetial") 
public class MainDetialAction extends BaseAction{
	
	private final static Logger log= Logger.getLogger(MainDetialAction.class);
	
	/**
	 * ilook 首页
	 * @param url
	 * @param classifyId
	 * @return
	 */
	@Auth(verifyLogin=false,verifyURL=false)
	@RequestMapping("/main") 
	public ModelAndView  main(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object>  context = getRootMap();
		
		return forword("main/maindetial",context); 
	}
	
}
