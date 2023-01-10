
package com.inch.rest.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inch.interceptor.Auth;
import com.inch.rest.utils.RedisUtil;
import com.inch.utils.FastJsonUtils;
import com.inch.utils.HtmlUtil;
 
@Controller
@RequestMapping("/inchread") 
public class TestInchAction extends BaseAction{
	
	private static final Logger logger = Logger.getLogger(TestInchAction.class);
	
	@Auth(verifyLogin=false,verifyParentLogin=false)
	@RequestMapping("/readCnt") 
	public void addReadCnt(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String callback = request.getParameter("callback"); 
		
		String val=RedisUtil.get("inchread");
		
		int cnt=0;
		
		if(StringUtils.isBlank(val)){
			cnt=1;
		}else{
			cnt=NumberUtils.toInt(val)+1;
		}
		
		RedisUtil.set("inchread", cnt+"",-1);
		
		
		Map<String,Object>  context = new HashMap<String,Object>();
		context.put(SUCCESS, true);
		context.put("cnt", cnt);

		if(StringUtils.isBlank(callback)){
			HtmlUtil.writerJson(response, context);
		}else{
			HtmlUtil.writerStr(response,callback+"("+FastJsonUtils.toJsonWithNUll(context)+")");
		}
		
		
	}
}
