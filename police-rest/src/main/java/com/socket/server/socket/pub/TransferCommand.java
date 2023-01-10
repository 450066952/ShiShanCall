//package com.smartwj.socket.pub;
//
//import java.io.UnsupportedEncodingException;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.log4j.Logger;
//import org.jboss.netty.buffer.ChannelBuffer;
//import org.jboss.netty.buffer.ChannelBuffers;
//
///*************************************************************************
// * FileName:TransferCommand.java
// * @author Tony
// * Create time:2010-4-23    上午10:24:18
// * Last modifed time:2010-4-23
// * Version: Nm V1.0 
// * Function:  服务器中转命令
// * CopyRight (c) Netmarch Company 2010
// *   All rights reserved
// *************************************************************************/
//public class TransferCommand extends Command {
//	private static final Logger logger = Logger.getLogger(TransferCommand.class);
//
//	private Command transferCommand;
//
//	private String sendTo;
//	
//	private ChannelBuffer buffer;
//	
//	
//
//	public ChannelBuffer getBuffer() {
//		return buffer;
//	}
//
//	public void setBuffer(ChannelBuffer buffer) {
//		this.buffer = buffer;
//	}
//
//	public String getSendTo() {
//		return sendTo;
//	}
//
//	public void setSendTo(String sendTo) {
//		this.sendTo = sendTo;
//	}
//
//	public void setTransferCommand(Command transferCommand) {
//		this.transferCommand = transferCommand;
//	}
//
//	public Command getTransferCommand() {
//		return transferCommand;
//	}
//	
//	public TransferCommand(Command toTansfer) {
//		super(Command.COMMAND_SEND_TRANSFER);
//		transferCommand = toTansfer;
//	}
//	
//	public TransferCommand(int command,ChannelBuffer buffer, byte ack){
//		super(command, buffer, ack);
//	}
//
//	@Override
//	protected void putBody(ChannelBuffer buffer) {
//		try {
//			buffer.writeBytes(sendTo.getBytes(DEFAULT_CHARSET));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		byte c = '\0';
//		buffer.writeByte(c);
//		
//		ChannelBuffer msg = ChannelBuffers.dynamicBuffer();
//		transferCommand.fill(msg);
//		writeInt(buffer, msg.readableBytes());//长度
//		buffer.writeBytes(msg);
//		
//	}
//
//	@Override
//	protected void parseBody(ChannelBuffer buffer) {
//		sendTo = StringUtils.trimToEmpty(readString(buffer));
//		
//		logger.debug("接收者:"+sendTo);
//		
//		int length = ChannelBuffers.swapInt(buffer.readInt());//命令内容长度
//		
//		ChannelBuffer b = buffer.copy();
//		this.buffer=b;
//
//		parseTransferCommand(buffer);//不需要解析了
//	}
//	
//	private void parseTransferCommand(ChannelBuffer buffer){
//		logger.debug("开始解析服务器中转命令");
//		
//			//生成相应的Command
//			transferCommand = CommandFactory.parseCommand(buffer);
//	}
//
//}
