package com.socket.server.server.core;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;
//@ChannelPipelineCoverage("all")  
public class Heartbeat extends //SimpleChannelUpstreamHandler
IdleStateAwareChannelHandler 
{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(Heartbeat.class);

	int i = 0;

	//add by tony 2016-11-28----当收到消息的时候给他设置为0
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {

		//ctx.sendUpstream(e);
		super.messageReceived(ctx, e);
		
		i=0;
	}
	
	@Override
	public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e)
			throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("channelIdle(ChannelHandlerContext, IdleStateEvent) - start"); //$NON-NLS-1$
		}
		super.channelIdle(ctx, e);
		
		
		if (e.getState() == IdleState.READER_IDLE){
			i++;
		}
		logger.info("e.getState():"+e.getState()+"值为:"+i+"-----------"+e.getChannel().getRemoteAddress().toString());
		if (i == 3) {
			e.getChannel().close();
			logger.info("ID为" + e.getChannel().getId() + "客户端IP:"
					+ e.getChannel().getRemoteAddress().toString() + "掉线");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("channelIdle(ChannelHandlerContext, IdleStateEvent) - end"); //$NON-NLS-1$
		}
	}
}
