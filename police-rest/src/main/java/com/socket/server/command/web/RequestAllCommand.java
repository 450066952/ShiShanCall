package com.socket.server.command.web;

import org.apache.commons.lang.StringUtils;
import org.jboss.netty.buffer.ChannelBuffer;

import com.socket.server.socket.pub.Command;

import java.util.Date;

/**
 * 
 * @Title: RequestLoginCommand.java
 * @Package com.smartwj.command.in
 * @Description: 用户登录协议
 * @author tony
 * @Email 303976091@qq.com
 * @date 2016年5月3日
 */
public class RequestAllCommand extends Command {

	private String info;
	
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public RequestAllCommand(int command) {
		super(command);
	}

	public RequestAllCommand(int command,Date date) {
		super(command,date);
	}

	public RequestAllCommand(int command, ChannelBuffer buffer, byte ack) {
		super(command, buffer, ack);
	}

	@Override
	protected void putBody(ChannelBuffer buffer) {
		if(StringUtils.trimToEmpty(info).length()>0){
			this.writeString(buffer, info);
		}
	}

	@Override
	protected void parseBody(ChannelBuffer buffer) {

//		System.out.println(length);
		
		String msg=this.readString(buffer);
		
		this.setInfo(msg);
		
	}
}
