package com.inch.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inch.annotation.Auth;
@Controller
public class MyTimoutException {
	
	@Auth(verifyLogin=false,verifyURL=false)
	@RequestMapping("/loginout") 
	public void resolveException(HttpServletRequest request, HttpServletResponse response) throws IOException {  
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		String server = "http://" + request.getServerName() + ":"
		 + request.getServerPort() + request.getContextPath();
		response.getWriter().write("<script>window.parent.location.href='"+server+"/login.shtml'</script>");

    }  
}
