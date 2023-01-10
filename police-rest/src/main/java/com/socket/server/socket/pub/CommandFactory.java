package com.socket.server.socket.pub;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import com.socket.server.command.web.RequestAllCommand;

public class CommandFactory {
	private static final Logger logger = Logger.getLogger(CommandFactory.class);

	public static Command parseCommand(ChannelBuffer buffer) {
		
		Command command = null;
		logger.debug("buffer hex--------old---yuanshi-------------- is:"+ ChannelBuffers.hexDump(buffer).toUpperCase());
		logger.debug("buffer writerIndex:===========" + buffer.writerIndex());
		// 是正常的命令
		buffer.readShort();//startTag 4E4D -------------2
		int length = ChannelBuffers.swapInt(buffer.readInt());
		logger.debug("长度为:" + (length + 6));
		// 懒的管最后一位
		// 跳过前面的14个字节,原来做的时候是没有解析日期和时间,现在加上日期和时间
		buffer.skipBytes(12);
		short year = ChannelBuffers.swapShort(buffer.readShort());// -----------2字节
		byte[] times = new byte[5];// 5字节
		buffer.readBytes(times);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 5; i++) {
			int b = times[i];
			if (b < 10)
				sb.append("0" + b);
			else
				sb.append(b);
		}
		logger.debug("year:" + year + "-" + sb.toString());
		int commandvalue = ChannelBuffers.swapInt(buffer.readInt());
		byte ack = buffer.readByte();
		logger.debug("commandvalue is:" + commandvalue);
		
		command = new RequestAllCommand(commandvalue, buffer, ack);
		
		return  command;
	}
	
	public static void main(String[] args) {
		short aaa=782;
		 
		System.out.println(ChannelBuffers.swapShort(aaa));
	}
}
