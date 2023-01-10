package com.socket.server.server.core;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.DefaultChannelPipeline;
import org.jboss.netty.util.Timer;

/**
 * 通信管道处理工厂,里面已包装好了相关的消息处理器与数据包编码解码器
 * @Title: ServerPipelineFactory.java
 * @Package com.socket.server.server.core
 * @Description: TODO
 * @author tony
 * @Email 303976091@qq.com
 * @date 2016年5月21日
 */
public class ServerPipelineFactory implements ChannelPipelineFactory {

	ServerEncoder encoder;
	ServerDecoder decoder;
//	FastLengthFieldBasedFrameDecoder decoder;
	private Timer timer;
	ServerHandler handler;
	 
	 public ServerPipelineFactory(Timer timer){
	  
	  this.timer = timer;
	 }

	public ServerEncoder getEncoder() {
		return encoder;
	}

	public void setEncoder(ServerEncoder encoder) {
		this.encoder = encoder;
	}

	
//	/**
//	 * @return the decoder
//	 */
//	public FastLengthFieldBasedFrameDecoder getDecoder() {
//		return decoder;
//	}
//
//	/**
//	 * @param decoder the decoder to set
//	 */
//	public void setDecoder(FastLengthFieldBasedFrameDecoder decoder) {
//		this.decoder = decoder;
//	}
	public ServerDecoder getDecoder() {
		return decoder;
	}

	public void setDecoder(ServerDecoder decoder) {
		this.decoder = decoder;
	}

	public ServerHandler getHandler() {
		return handler;
	}


	public void setHandler(ServerHandler handler) {
		this.handler = handler;
	}

	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = new DefaultChannelPipeline();
		pipeline.addLast("decoder", decoder);
		pipeline.addLast("encoder", encoder);
		pipeline.addLast("tony", handler);
		
		return pipeline;
		
	}

}
