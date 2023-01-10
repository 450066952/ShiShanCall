package com.socket.server.server.core;

import java.io.IOException;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.inch.utils.FastJsonUtils;
import com.socket.server.command.dto.PcLoginModel;
import com.socket.server.command.dto.ReauestLoginDto;
import com.socket.server.command.service.ServerCommandSendHelper;
import com.socket.server.command.web.RequestAllCommand;
import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import com.socket.server.command.service.ServerHandCommandService;
import com.socket.server.command.service.ServiceHandCommandDetail;
import com.socket.server.socket.pub.CharacterHelper;
import com.socket.server.socket.pub.Command;

/**
 * 本Handler是单例的
 * @Title: ServerHandler.java
 * @Package com.socket.server.server.core
 * @Description: TODO
 * @author tony
 * @Email 303976091@qq.com
 * @date 2016年5月21日
 */
//@ChannelPipelineCoverage("all") 
public class ServerHandler extends SimpleChannelUpstreamHandler {
	private static final Logger logger = Logger.getLogger(ServerHandler.class);
	private static final AtomicLong transferredBytes = new AtomicLong();
	private static final AtomicInteger clientCount = new AtomicInteger();
	public static long getTransferredBytes() {
		return transferredBytes.get();
	}
	public static int getClientCount(){
		
		return clientCount.intValue();
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		
		Command command = (Command) e.getMessage();
		logger.debug("messageReceived start"+e.getChannel().getRemoteAddress().toString());
		if(e.getChannel().isConnected()){
			ServerHandCommandService server = new ServerHandCommandService();
			logger.debug("Command is:"+command);
			server.HandCommand(command, e);			
		}
	}
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
	    logger.info(getHostIPAddress(e.getChannel())+"is channelConnected ");
	    
	    clientCount.incrementAndGet();
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
		logger.error("tony:"+e);
		Channel ch = e.getChannel();  
		Boolean flag=false;
		
		if(e.getCause() instanceof IOException){
			//logger.warn("IOException........2016/08/06 此种异常时断开连接");
			//flag=true;
			//临时测试下  先不断开
		}
		
		if(e.getCause() instanceof SocketException){
			logger.warn("SocketException.........2016/08/06 此种异常时断开连接");
			flag=true;
		}
		
		if(flag&&ch.isConnected()){

			removeOffLine(ch);
			
			ch.close();
			logger.warn("ex");
		}
		logger.error("**********************exceptionCaught...Client Ip IS:"+getHostIPAddress(e.getChannel()),e.getCause());
	}
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		super.channelClosed(ctx, e);
		Channel ch = e.getChannel();
		removeOffLine(ch);
		if(ch.isConnected()){
			ch.close();
			logger.warn("c");
		}
	    logger.info("**********************channelClosed Client IP IS:"+getHostIPAddress(ch));
	}
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx,ChannelStateEvent e) throws Exception {
		super.channelDisconnected(ctx, e);
		Channel ch = e.getChannel();  

		removeOffLine(ch);
		if(ch.isConnected()){
			ch.close();
			logger.warn("d");
		}

		logger.info("**********************channelDisconnected Client IP IS:"+getHostIPAddress(e.getChannel()));
	}

	private void removeOffLine(Channel ch){

		ServiceHandCommandDetail server = new ServiceHandCommandDetail();
		Map<String,String> map=server.getUsernameByChannel(ch);

		if(map!=null&&map.size()>0){

			for(String m:map.keySet()){
				if("loginuser".equals(m)){
					CharacterHelper.ALL_LOGIN_USER.remove(map.get(m));
					logger.warn("loginuser 离线MAP里 加入注销用户名为： "+map.get(m));
				}
				else if("datauser".equals(m)){
					CharacterHelper.ALL_BIG_DATA_USER.remove(map.get(m));
					logger.warn("datauser 离线MAP里 加入注销用户名为： "+map.get(m));
				}
				else{

					try{
						RequestAllCommand rcmd = new RequestAllCommand(Command.COMMAND_PC_LOG_OUT);
						PcLoginModel p=CharacterHelper.ALL_PC_USER.get(map.get(m));
						if(p!=null){
							Map<String,String> tmap=new HashMap<>();
							tmap.put("winid",p.getWinid());
							ServiceHandCommandDetail h=new ServiceHandCommandDetail();
							rcmd.setInfo(FastJsonUtils.toJson(tmap));
							h.sendToDataUser(rcmd);
						}
					}catch (Exception e){
						e.printStackTrace();
					}


					CharacterHelper.ALL_PC_USER.remove(map.get(m));
					logger.warn("pcuser 离线MAP里 加入注销用户名为： "+map.get(m));
				}
			}
		}
	}

	public String getHostIPAddress(Channel session){
		return session.getRemoteAddress().toString();
	}
	
	
}
