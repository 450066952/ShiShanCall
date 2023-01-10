package com.socket.server.server.core;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;

import com.socket.server.socket.pub.ThroughputMonitor;
/**
 * @Title: Server.java
 * @Package com.socket.server.server.core
 * @Description: TODO
 * @author tony
 * @Email 303976091@qq.com
 * @date 2016年5月21日
 */
public class Server {
	private static final Logger logger = Logger.getLogger(Server.class);
	Timer timer = new HashedWheelTimer();
//	private static final InternalLogger logger = Log4JLoggerFactory.getInstance(EchoServerHandler.class);
	public static void main(String[] args) throws Exception {
		logger.debug("Start the Server..");
	}
	public void startServer(){
		ServerBootstrap bootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
		bootstrap.setPipelineFactory(new ServerPipelineFactory(timer) {
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				//ClientDecoder的createCommand要进行修改
				//CommandFactory类里面的parseCommand方法进行修改
				/*比如一个命令包含的数据 称为一个frame
				在这个frame 中， 头是0x4e4d  两个字节
				然后是后面整个数据的长度，有四个字节，读出来的就是frameLength
				再往后就是剩下的frameLength指定长度的数据*/
				pipeline.addLast("decoder", new ServerDecoder(10240, 5, 4));
				//加入控制器，也就是处理中心，负责处理客户端的请求
				pipeline.addLast("xx", new ServerHandler());
				return pipeline;
			}
		});
		//装载流量监控器
		ThroughputMonitor monitor= new ThroughputMonitor(3000);
		// Bind and start to accept incoming connections.
		bootstrap.bind(new InetSocketAddress(9001));
		monitor.start();
		logger.debug("Start the Server success");
	}
}
