package com.socket.server.server.start;


import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;

import com.socket.server.command.thread.ThreadRunService;
import com.socket.server.server.core.Heartbeat;
import com.socket.server.server.core.ServerDecoder;
import com.socket.server.server.core.ServerHandler;
import com.socket.server.server.core.ServerPipelineFactory;
import com.socket.server.socket.pub.Command;
import com.socket.server.socket.pub.ThroughputMonitor;


public class StartServer {
	private static final Logger logger = Logger.getLogger(StartServer.class);
	private static final int PORT=9299;//9005
	public static void main(String[] args) throws Exception {
		
		logger.info("Start the Server...");
		logger.info("Check EnvironMent start...");
		logger.info("Current OS_NAME is:"+SystemUtils.OS_NAME);
		logger.info("Current OS_VERSION is:"+SystemUtils.OS_VERSION);
		logger.info("Assert JAVA VERSION IS 1.6 OR LETTER:"+SystemUtils.IS_JAVA_1_6);
		logger.info("JAVA_HOME is:"+SystemUtils.JAVA_HOME);
		logger.info("Port Is:"+PORT);
		logger.info("MAX_COMMAND_LENGTH is:"+Command.MAX_COMMAND_LENGTH/1024+"kb");
		
		
		final Timer timer = new HashedWheelTimer();

		//Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*POOL_SIZE); 
		ServerBootstrap bootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
		bootstrap.setPipelineFactory(new ServerPipelineFactory(timer) {
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
//				pipeline.addLast("decoder", new FastLengthFieldBasedFrameDecoder(102400,2,4,0,6)); 
				
				pipeline.addLast("decoder", new ServerDecoder(Command.MAX_COMMAND_LENGTH, 2, 4));//up
				pipeline.addLast("timeout", new IdleStateHandler(timer, 0, 20, 0));//10 读  10 写 此两项为添加心跳机制 10秒查看一次在线的客户端channel是否空闲
				pipeline.addLast("hearbeat", new Heartbeat());
				pipeline.addLast("tony", new ServerHandler());
				
				return pipeline;
			}
		});
		//The defaults are 64K and 16K respectively
		bootstrap.setOption("writeBufferHighWaterMark", 4096);
		bootstrap.setOption("writeBufferLowWaterMark", 1024);
		bootstrap.setOption("child.tcpNoDelay", true);
	    bootstrap.setOption("child.keepAlive", true);
	    bootstrap.setOption("child.reuseAddress", true);
//	    boot.strap.setOption("child.connectTimeoutSeconds", 1800);
		bootstrap.setOption("child.connectTimeoutMillis", 1000);
//	    bootstrap.setOption("readWriteFair", true);
		//装载流量监控器,3000为３秒，此处休眠60秒
		ThroughputMonitor monitor= new ThroughputMonitor(3000*20);
		// Bind and start to accept incoming connections.
		bootstrap.bind(new InetSocketAddress(PORT));
//		logger.info("开始执行ChannelFuture是否关闭检查...");
//		BufferUtil.TimerCheckChannel(2000*10);//20秒执行一次

//		monitor.start();
		
		logger.info("Start the Server success");
	
		
	}
}
