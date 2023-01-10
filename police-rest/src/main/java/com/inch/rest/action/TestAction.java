
package com.inch.rest.action;

import com.inch.interceptor.Auth;
import com.inch.model.VersionModel;
import com.socket.server.command.service.TonyCommandService;
import com.socket.server.socket.pub.Command;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/updateVersion") 
public class TestAction extends BaseAction{
	
	private final static Logger log= Logger.getLogger(TestAction.class);
	
	@Autowired
	private TonyCommandService tonyService;
	
	
	@Auth(verifyLogin=false)
	@RequestMapping("/send.json")
	public void save(HttpServletRequest request,HttpServletResponse response,String url) throws Exception{
		
		//------------------------------验证是否需要升级----------------------------------------------
		VersionModel vmodel=new VersionModel();
		String sn=StringUtils.trimToEmpty(request.getParameter("sn"));
//		vmodel.setUrl("http://display.incich.com:9208/Upload/"+StringUtils.trimToEmpty(request.getParameter("filename")));

		if(StringUtils.isBlank(url)){
			this.sendFailureMessage(response, "参数不完整~~~");
			return;
		}
		vmodel.setUrl(url);
		vmodel.setVersion("10000");

		tonyService.sendCommandToDevice(Command.COMMAND_UPGRADED,vmodel,sn);
		
		sendSuccessMessage(response, "发送----成功~");
	}
	
	@Auth(verifyLogin=false)
	@RequestMapping("/updateBySchool.json")
	public void sendSchool(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		//------------------------------验证是否需要升级----------------------------------------------
		VersionModel vmodel=new VersionModel();
		String schoolid=StringUtils.trimToEmpty(request.getParameter("schoolid"));
		String url=StringUtils.trimToEmpty(request.getParameter("url"));
		
		if(schoolid.length()==0||url.length()==0){
			this.sendFailureMessage(response, "参数不完整~~~");
			return;
		}


		
//		vmodel.setUrl("http://display.incich.com:9208/Upload/"+fname);
		vmodel.setUrl(url);
		vmodel.setVersion("0");

		tonyService.sendCommandToServer(Command.COMMAND_UPGRADED,vmodel,schoolid);
		
		sendSuccessMessage(response, "发送----成功~");
	}

	@Auth(verifyLogin=false)
	@RequestMapping("/initBySchool.json")
	public void initBySchool(HttpServletRequest request,HttpServletResponse response) throws Exception{

		//------------------------------验证是否需要升级----------------------------------------------
		VersionModel vmodel=new VersionModel();
		String schoolid=StringUtils.trimToEmpty(request.getParameter("schoolid"));

		if(schoolid.length()==0){
			this.sendFailureMessage(response, "参数不完整~~~");
			return;
		}

		vmodel.setVersion("0");

		tonyService.sendCommandToServer(Command.COMMAND_SEND_INIT_SCHOOL,vmodel,schoolid);

		sendSuccessMessage(response, "发送----成功~");
	}

}
