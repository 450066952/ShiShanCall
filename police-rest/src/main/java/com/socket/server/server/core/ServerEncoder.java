package com.socket.server.server.core;

import org.jboss.netty.channel.ChannelDownstreamHandler;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;


/**
 * 本身的是应该将Command对象组装成发送数据的，但是每个command 对象都已经包含相应的数据包生成，所以这里默认
 * 主要用于构造数据包的编码工作
 * @Title: ServerEncoder.java
 * @Package com.socket.server.server.core
 * @Description: TODO
 * @author tony
 * @Email 303976091@qq.com
 * @date 2016年5月21日
 */
public class ServerEncoder implements ChannelDownstreamHandler{

	public void handleDownstream(ChannelHandlerContext ctx, ChannelEvent e)
			throws Exception {
		ctx.sendDownstream(e);
	}

}
