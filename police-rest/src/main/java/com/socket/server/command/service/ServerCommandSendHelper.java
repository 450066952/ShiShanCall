package com.socket.server.command.service;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.MessageEvent;

import com.socket.server.socket.pub.CharacterHelper;
import com.socket.server.socket.pub.Command;
/**
 *  命令发送辅助方法功能类
 * @Title: ServerCommandSendHelper.java
 * @Package com.socket.server.command.service
 * @Description: TODO
 * @author tony
 * @Email 303976091@qq.com
 * @date 2016年5月21日
 */
public abstract class ServerCommandSendHelper {
	private static final Logger logger = Logger.getLogger(ServerCommandSendHelper.class);
	
	/**向客户端写文件,包括文件流协议
	 * @param e
	 */
	/*public void sendFileCommand(MessageEvent e,Command command,OasaDto oasaDto){
			ChannelBuffer header =  ChannelBuffers.dynamicBuffer();
			// if the filedefineList is null ,don`t send the file socket stream
			logger.debug("sendFileCommand 是否发送文件流协议检测开始:");
			List<FileDefineDto> filedefineList=oasaDto.getFileList();
			logger.debug("sendFileCommand文件个数:"+(CharacterHelper.checkListSize(filedefineList)));
			if(CharacterHelper.checkListSize(filedefineList)>0){
				command.fillAppendFileSocketSize(header, oasaDto);
//				delphi客户端要先去掉头5个字节
//				header.skipBytes(5);
				Channel ch = e.getChannel();
				ChannelFuture writeFuture = ch.write(header);
				int i=0;
				int listsize=filedefineList.size();
			    for(;i<listsize;i++){
			    	FileDefineDto rsdto =filedefineList.get(i);
		//	    	sendMoreFile(writeFuture,files);
			    	sendMoreAndSmallFile(writeFuture,rsdto);
					logger.debug("#发送文件第 ["+(i+1)+"] 个成功");
			    }
			    logger.info("==>>Send success ,close the client connect"+getHostIPAddress(writeFuture.getChannel()));
			}
			else{
				logger.debug("----------发送文件流协议时,文件流为空,将只发送文件流协议前面的数据包-----");
				command.fill(header);
//				delphi客户端要先去掉头5个字节
//				header.skipBytes(5);
				ChannelFuture f = e.getChannel().write(header);
//				logger.debug("返回到客户端数据长度:"+ChannelBuffers.hexDump(header).toUpperCase().length()/2);
//				f.addListener(ChannelFutureListener.CLOSE);//使用预定义的监听类来简化代码,此段代码与下面注释掉的代码作用相同
		        f.addListener(new ChannelFutureListener() {   
		        	// set the listener look this is complete.
		            public void operationComplete(ChannelFuture future) {  
//		            	在这里要释放相关资源
		            	 if (!future.isSuccess()) { 
//				                future.getCause().printStackTrace(); 
				                future.getChannel().close(); 
				                return; 
				            } 
//		                Channel ch = future.getChannel(); 
//		                // JAVA客户端测试就要关闭掉,DELPHI端测试不用关闭,将由DELPHI客户端来主动关闭
//		                ch.close();
		    	        logger.info("==>>Send success ,close the client connect"+getHostIPAddress(future.getChannel()));
		            }   
		        });  
			}
	}
	*//** 传输小文件时使用
	 * @param writeFuture
	 * @param dto
	 */
//	private void sendMoreAndSmallFile(ChannelFuture writeFuture ,FileDefineDto dto){
//		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
//		final byte[] rsbyte=dto.getFiles();
//		buffer.writeBytes(rsbyte);
//		writeFuture.getChannel().write(buffer);
//		writeFuture.addListener(new ChannelFutureListener() { 
//	        public void operationComplete(ChannelFuture future)throws Exception {
//	            if (!future.isSuccess()) { 
////	                future.getCause().printStackTrace(); 
//	                future.getChannel().close(); 
//	                return; 
//	            } 
//	        } 
//	    }); 
//	}
	
	/**传输大文件时使用
	 * @param writeFuture
	 * @param file
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean sendMoreFile(ChannelFuture writeFuture ,File file){
		boolean flag=false;
		try{
		final int BUFFER_SIZE=8192;
		final long fileLength = file.length(); 
		
	    final FileInputStream fis= new FileInputStream(file);
	    //改用DataInputStream是为了在网络中传输更稳定
	    final DataInputStream dis= new DataInputStream(fis);
		  final long startdate=System.currentTimeMillis();
		    writeFuture.addListener(new ChannelFutureListener() { 
		        private final ChannelBuffer buffer = ChannelBuffers.buffer(BUFFER_SIZE); //一次发8K
		        private long offset = 0; 
		        public void operationComplete(ChannelFuture future)throws Exception {
		            if (!future.isSuccess()) { 
//		                future.getCause().printStackTrace(); 
		                future.getChannel().close(); 
		                dis.close(); 
		                return; 
		            } 
//		            Thread.sleep(100);
		            buffer.clear(); 
//		            logger.debug("SENDING: " + offset + " / " + fileLength);
		            buffer.writeBytes(dis, (int) Math.min(fileLength - offset, buffer.writableBytes())); 
		            offset += buffer.writerIndex(); 
		            ChannelFuture chunkWriteFuture = future.getChannel().write(buffer); 
		            if (offset < fileLength) { 
		                // Send the next chunk 
		                chunkWriteFuture.addListener(this); 
		            } else { 
		                // Wrote the last chunk - close the connection if the write is done. 
		            	logger.debug("Server to Client DONE: " + fileLength+"耗时:"+((System.currentTimeMillis()-startdate)/1000)+"秒"); 
//		                chunkWriteFuture.addListener(ChannelFutureListener.CLOSE_ON_FAILURE); 
		                dis.close(); 
//		            	logger.debug("offset:"+offset);
		                logger.info("File Send success ,close the client connect"+getHostIPAddress(chunkWriteFuture.getChannel()));
		            } 
		        } 
		    }); 
		    flag=true;
		}catch(Exception se){
			se.printStackTrace();
		}
		return flag;
	}
	
	
	/**server send buffer to client
	 * @param command
	 * @param e 
	 */
	public void sendCommand(Command command,MessageEvent e){
		//声明一个动态buffer
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		command.fill(buffer);
		logger.debug("buffer hex--------old-rrrrrrr---------------- is:"+ ChannelBuffers.hexDump(buffer).toUpperCase());
		
		ChannelFuture f=e.getChannel().write(buffer);
        f.addListener(new ChannelFutureListener() {
        	// set the listener look this is complete.
            public void operationComplete(ChannelFuture future) {
//            	 if (!future.isSuccess()) {
            	if(!future.awaitUninterruptibly(CharacterHelper.DEFAULT_CONNECTION_TIMEOUT,TimeUnit.SECONDS)){
//            		 	logger.error("future is not success ",future.getCause() );
//		                future.getCause().printStackTrace(); 
		                future.getChannel().close(); 
		                logger.error("client time out");
		                return; 
		            } 
//                Channel ch = future.getChannel(); 
//                // JAVA客户端测试就要关闭掉,DELPHI端测试不用关闭,将由DELPHI客户端来主动关闭
//                ch.close();
//    	        logger.info("==>>Send success "+getHostIPAddress(future.getChannel()));
            }   
        });  
	}
	
	/**server send buffer to client
	 * @param command
	 * @param e 
	 */
	public void sendCloseCommand(Command command,MessageEvent e){
		//声明一个动态buffer
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		command.fill(buffer);
		
		ChannelFuture f=e.getChannel().write(buffer);
		
		f.addListener(ChannelFutureListener.CLOSE);//使用预定义的监听类来简化代码,此段代码与下面注释掉的代码作用相同
        
	}
	
	/**此方法用在检测异地登陆时，并且主动断开连接
	 * @param command
	 * @param ch
	 */
	public void sendCommandOtherLogon(Command command,Channel ch,final String username){
		//声明一个动态buffer
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
		command.fill(buffer);
		ChannelFuture f =ch.write(buffer);
//		f.addListener(ChannelFutureListener.CLOSE);//使用预定义的监听类来简化代码,此段代码与下面注释掉的代码作用相同
		
		f.addListener(new ChannelFutureListener() {
        	// set the listener look this is complete.
            public void operationComplete(ChannelFuture future) {
//            	 if (!future.isSuccess()) {
            	if(!future.awaitUninterruptibly(CharacterHelper.DEFAULT_CONNECTION_TIMEOUT,TimeUnit.SECONDS)){
//            		 	logger.error("future is not success ",future.getCause() );
//		                future.getCause().printStackTrace(); 
		                future.getChannel().close(); 
		                logger.error("client time out");
		                return; 
		            } 
               Channel ch = future.getChannel(); 
//                // JAVA客户端测试就要关闭掉,DELPHI端测试不用关闭,将由DELPHI客户端来主动关闭
//                ch.close();
            	
            	if(ch.isConnected()){
            		ch.close();
            	}
            	
//            	CharacterHelper.ALL_LOGIN_USER.remove(username);
            	
    	        logger.info("==>>Send success ,close the client connect"+getHostIPAddress(future.getChannel()));
            }   
        });  
		
	}
	
	/**server send buffer to client ,不需要关闭连接
	 * @param command
	 * @param e 
	 */
	public void sendCommandC(Command command,Channel ch){
		// 管道有效的情况下
		if(ch.isConnected()){
			//声明一个动态buffer
			ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
			command.fill(buffer);
			
			logger.debug("buffer hex--------old-rrrrrrr---------------- is:"+ ChannelBuffers.hexDump(buffer).toUpperCase());
			
			//调用这个Channel通道对象的write方法向远程节点写入返回数据。
			ChannelFuture f =ch.write(buffer);
//			logger.debug("return client buffer data length:"+ChannelBuffers.hexDump(buffer).toUpperCase().length()/2);
			//增加一个通知事件，当ChannelFuture监听器监听到处理完消息时，马上关闭此连接
//			f.addListener(ChannelFutureListener.CLOSE);//使用预定义的监听类来简化代码,此段代码与下面注释掉的代码作用相同
	        f.addListener(new ChannelFutureListener() {
	        	// set the listener look this is complete.
	            public void operationComplete(ChannelFuture future) {
//	            	 if (!future.isSuccess()) {
	            	if(!future.awaitUninterruptibly(CharacterHelper.DEFAULT_CONNECTION_TIMEOUT,TimeUnit.SECONDS)){
//	            		 	logger.error("future is not success ",future.getCause() );
//			                future.getCause().printStackTrace(); 
			                future.getChannel().close(); 
			                logger.error("client time out");
			                return; 
			            } 
//	                Channel ch = future.getChannel(); 
//	                // JAVA客户端测试就要关闭掉,DELPHI端测试不用关闭,将由DELPHI客户端来主动关闭
//	                ch.close();
//	    	        logger.info("==>>Send success"+getHostIPAddress(future.getChannel()));
	            }   
	        });  
		}
		
	}
	
	/**直接转发消息
	 * @param buffer
	 * @param ch
	 */
	public void sendTrCommand(ChannelBuffer buffer,Channel ch ){
		
		
		logger.debug("buffer hex--------old-buffer---------------- is:"+ ChannelBuffers.hexDump(buffer).toUpperCase());
		
		// 管道有效的情况下
		if(ch.isConnected()){
			//调用这个Channel通道对象的write方法向远程节点写入返回数据。
			ChannelFuture f = ch.write(buffer);
			
//			f.
			
			//增加一个通知事件，当ChannelFuture监听器监听到处理完消息时，马上关闭此连接
//			f.addListener(ChannelFutureListener.CLOSE);//使用预定义的监听类来简化代码,此段代码与下面注释掉的代码作用相同
	        f.addListener(new ChannelFutureListener() {
	        	// set the listener look this is complete.
	            public void operationComplete(ChannelFuture future) {
	            	 if (!future.isSuccess()) {
//	            	if(!future.awaitUninterruptibly(CharacterHelper.DEFAULT_CONNECTION_TIMEOUT,TimeUnit.SECONDS)){
//	            		 	logger.error("future is not success ",future.getCause() );
			                future.getChannel().close(); 
			                
			                logger.error("client time out---client time out---client time out--------client time out------------------");
			                return; 
			            } 
//	                Channel ch = future.getChannel(); 
//	                ch.close();
	    	        logger.info("==>>Send success ,close the client connect"+getHostIPAddress(future.getChannel()));
	            }   
	        });  
		}
	}
	/** 获取客户端的IP地址与端口号
	 * @return
	 */
	public String getHostIPAddress(Channel session){
		return session.getRemoteAddress().toString();
	}
	/**测试向客户端写文件
	 * 此方法没有另外一个方法安全
	 * @param e
	 * @param buffer
	 */
	
	public void sendFile(MessageEvent e){
	
		try {
			File f = new File("g:\\test.exe");
			String params = "GetFile|test.gif";
			byte[] baseParams = params.getBytes("UTF-8");
			ChannelBuffer header = ChannelBuffers.buffer(8 + baseParams.length);
			header.writeInt(baseParams.length);
			header.writeInt((int)f.length());
			header.writeBytes(baseParams);
			// write the header info first
			ChannelFuture future = e.getChannel().write(header);
			FileInputStream fis = new FileInputStream(f);
			byte[] bs = new byte[1024];
			while (fis.read(bs) > 0) {
				//COPY读取的一段并发送
				 //TODO
				ChannelBuffer buffer = ChannelBuffers.copiedBuffer(bs);
				//这里用while (!future.getChannel().isWritable())有点问题，会wait。。
//				while (!future.getChannel().isWritable()) {
//					System.out.println("wait");
//					Thread.sleep(5000);
//				}
				future.getChannel().write(buffer);
			}
			fis.close();
		} catch (Exception se) {
			se.printStackTrace();
		}
	}

}
