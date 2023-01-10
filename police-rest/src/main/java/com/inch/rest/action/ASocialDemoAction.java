package com.inch.rest.action;

import com.inch.interceptor.Auth;
import com.socket.server.command.service.TonyCommandService;
import com.socket.server.socket.pub.Command;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/social")
public class ASocialDemoAction extends BaseAction {

	private final static Logger log = Logger.getLogger(ASocialDemoAction.class);

	@Autowired
	private TonyCommandService tonyService;

	//业务开始办理
	@Auth(verifyLogin = false)
	@RequestMapping("/startBus")
	public void startBus(String winname,String winid,
						HttpServletResponse response) throws Exception {
		Map<String,Object> map =new HashMap<>();
		map.put("winname",winname);
		map.put("guid",winid);

		tonyService.sendCommandToServer(Command.COMMAND_SOCOAL_START_BUS, map,winid);
		this.sendSuccessMessage(response,"发送成功");
	}

	//签名
	@Auth(verifyLogin = false)
	@RequestMapping("/startsign")
	public void startsign(String winname,String winid,
						 HttpServletResponse response) throws Exception {
		Map<String,Object> map =new HashMap<>();
		map.put("winname",winname);
		map.put("guid",winid);

		tonyService.sendCommandToServer(Command.COMMAND_SOCOAL_START_SIGN, map,winid);
		this.sendSuccessMessage(response,"发送成功");
	}

	//业务结束办理
	@Auth(verifyLogin = false)
	@RequestMapping("/stopBus")
	public void stopBus(String winname,String winid,String wintype,
						HttpServletResponse response) throws Exception {
		Map<String,Object> map =new HashMap<>();

		winname= StringUtils.substringBeforeLast(winname,"-");

		map.put("winname",winname);
		map.put("guid",winid);

		tonyService.sendCommandToServer(Command.COMMAND_SOCOAL_STOP_BUS, map,winid);
		tonyService.sendToBigData(wintype,Command.COMMAND_SOCOAL_STOP_BUS,"");

		this.sendSuccessMessage(response,"发送成功");
	}

	//暂停服务
	@Auth(verifyLogin = false)
	@RequestMapping("/stopService")
	public void stopService(String winname,String winid,Boolean isstop ,HttpServletResponse response) throws Exception {
		Map<String,Object> map =new HashMap<>();
		map.put("isstop",isstop);
		map.put("winname",winname);
		map.put("guid",winid);

		tonyService.sendCommandToServer(Command.COMMAND_SOCOAL_STOP_SERVICE, map,winid);
		tonyService.sendStopToBigData(winid,isstop,Command.COMMAND_SOCOAL_STOP_SERVICE);
		this.sendSuccessMessage(response,"发送成功");


	}

	//重新叫号
	@Auth(verifyLogin = false)
	@RequestMapping("/repeatCall")
	public void repeatCall(String winname,String winid,String num,HttpServletResponse response) throws Exception {
		Map<String,Object> map =new HashMap<>();
		winname= StringUtils.substringBeforeLast(winname,"-");
		map.put("winname",winname);
		map.put("guid",winid);
		map.put("num",num);

		tonyService.sendCommandToServer(Command.COMMAND_SOCOAL_REPEAT_cALL, map,winid);
		this.sendSuccessMessage(response,"发送成功");
	}

}
