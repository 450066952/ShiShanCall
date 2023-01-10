package com.socket.server.command.service;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.MessageEvent;

import com.socket.server.command.web.RequestAllCommand;
import com.socket.server.socket.pub.Command;

/**
 * 主要作用是处理ServlerHandler类里当messageReceived成功时,服务器端发送到客户端数据包的对外服务类
 * @Title: ServerHandCommandService.java
 * @Package com.socket.server.command.service
 * @Description: TODO
 * @author tony
 * @Email 303976091@qq.com
 * @date 2016年5月21日
 */

public class ServerHandCommandService extends ServerCommandSendHelper {
	private static final Logger logger = Logger
			.getLogger(ServerHandCommandService.class);
	
	
	/**
	 * 主要作用是处理ServlerHandler类里当messageReceived成功时,服务器端发送到客户端数据包进行包装的命令发送对外服务类
	 */
	public ServerHandCommandService() {
	}
	
	/**
	 * @param command
	 * @param e
	 */
	public void HandCommand(Command command, MessageEvent e) {
		if (command != null) {
			
			ServiceHandCommandDetail detail=new ServiceHandCommandDetail();
			
			// 获取客户端发送来的Command,根据不同的命令进行相应的逻辑处理
//			logger.info("+++++++++++++++++++++command is :"+command.command);
			try {
				switch (command.command) {
				
				// 登陆
				case Command.COMMAND_USER_LOGIN:
					logger.info("接收到用户登录命令,");
					detail.LogonDetail(command, e);
					
					break;
				case Command.COMMAND_PC_LOGIN:
					logger.info("PC端用户登录");
					detail.pcLogin(command, e);
					break;

				case Command.COMMAND_DATA_USER_LOGIN:
						logger.info("大数据用户登录");
						detail.big_Data_Login(command, e);
						break;

				case Command.COMMAND_WIN_STATE:
						logger.info("获取窗口状态");
						detail.getWindowState(e);
						break;





					
				// 请求离线消息
				case Command.COMMAND_HISTORY:
					logger.info("接收到用户请求离线消息");
					detail.GetHistoryMsgDetail(command, e);

					break;
					
				// 客户端通知服务端接收ok
				case Command.COMMAND_OK:
					logger.info("客户端通知服务端接收ok");
					detail.notifySendOK(command, e);
					
					break;
					
					// 客户端首次登陆--初始化本地数据
				case Command.COMMAND_FIRST_INIT:
					logger.info("接收到用户请求初始化数据");
					detail.initLodaData(command, e);
					
					break;

				case Command.COMMAND_SEND_EVA:
					logger.info("接收到用户进行评价");
					detail.receiveEva(command, e);

					break;

					// 心跳
				case Command.COMMAND_HEARTBEAT:
					//logger.info("心跳");
					RequestAllCommand c = new RequestAllCommand(Command.COMMAND_HEARTBEAT);
					c.setInfo("111");
					this.sendCommand(c, e);
					
					break;
					
				}
			} catch (IllegalArgumentException ex) {
				logger.error("命令格式不正确, 请求命令中不包含此命令!", ex.getCause());
			}

		}
	}
}
