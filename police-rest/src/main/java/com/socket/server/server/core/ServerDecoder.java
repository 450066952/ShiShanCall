package com.socket.server.server.core;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.CorruptedFrameException;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.jboss.netty.handler.codec.frame.TooLongFrameException;

import com.socket.server.socket.pub.Command;
import com.socket.server.socket.pub.CommandFactory;

/**
 * 简单的理 解，upstream event是处理来自外部的请求的过程，而downstream event是处理向外发送请求的过程(客户端)。
 * 
 * 
 * 接收服务器响应的数据， 并切割成一个个数据段， 每个数据段都包含完整的一个命令该包含的数据
 * 主要用于数据包的解码工作
 * 比如一个命令包含的数据 称为一个frame
       在这个frame 中， 头是0x4e4d  两个字节
       然后是后面整个数据的长度，有四个字节，读出来的就是frameLength
       再往后就是剩下的frameLength指定长度的数据
       等到一个命令读完之后， 把buffer的起始位置设到这个命令数据的最后位置
 * @author Tony
 * 
 * 
 * 
 * maxFrameLength 这个定义最大帧的长度
lengthFieldOffset 长度属性的起始指针(偏移量)
lengthFieldLength 长度属性的长度，即存放数据包长度的变量的的字节所占的长度
lengthFieldEndOffset 这个是一个快捷属性，是根据lengthFieldOffset和lengthFieldLength计算出来的，即就是起始偏移量+长度=结束偏移量
lengthAdjustment 这个是一个长度调节值，例如当总长包含头部信息的时候，这个可以是个负数，就比较好实现了
initialBytesToStrip 这个属性也比较好理解，就是解码后的数据包需要跳过的头部信息的字节数
failFast 这个和DelimiterBasedFrameDecoder是一致的，就是如果设置成true，当发现解析的数据超过maxFrameLenght就立马报错，否则当整个帧的数据解析完后才报错
discardingTooLongFrame 这个也是一个导出属性，就是当前编码器的状态，是不是处于丢弃超长帧的状态
tooLongFrameLength 这个是当出现超长帧的时候，这个超长帧的长度
bytesToDiscard 这个来定义，当出现超长帧的时候，丢弃的数据的字节数
  接下来我们就深入主题，来看这个类的实现，最后我们再分析下javadoc，这样我们就能够彻底的掌握这个编码器了。
 *
 *
 */
public class ServerDecoder extends FrameDecoder {
	private static final Logger logger = Logger.getLogger(ServerDecoder.class);
    private final int maxFrameLength;
    private final int lengthFieldOffset;
    private final int lengthFieldLength;
    private final int lengthFieldEndOffset;
    private final int lengthAdjustment;
    private final int initialBytesToStrip;
    private volatile boolean discardingTooLongFrame;
    private volatile long tooLongFrameLength;
    private volatile long bytesToDiscard;

    public ServerDecoder(
            int maxFrameLength,
            int lengthFieldOffset, int lengthFieldLength) {
        this(maxFrameLength, lengthFieldOffset, lengthFieldLength, 0, 0);
        
    }

    private ServerDecoder(
            int maxFrameLength,
            int lengthFieldOffset, int lengthFieldLength,
            int lengthAdjustment, int initialBytesToStrip) {
        if (maxFrameLength <= 0) {
            throw new IllegalArgumentException(
                    "maxFrameLength must be a positive integer: " +
                    maxFrameLength);
        }

        if (lengthFieldOffset < 0) {
            throw new IllegalArgumentException(
                    "lengthFieldOffset must be a non-negative integer: " +
                    lengthFieldOffset);
        }

        if (initialBytesToStrip < 0) {
            throw new IllegalArgumentException(
                    "initialBytesToStrip must be a non-negative integer: " +
                    initialBytesToStrip);
        }

        if (lengthFieldLength != 1 && lengthFieldLength != 2 &&
            lengthFieldLength != 3 && lengthFieldLength != 4 &&
            lengthFieldLength != 8) {
            throw new IllegalArgumentException(
                    "lengthFieldLength must be either 1, 2, 3, 4 or 8: " +
                    lengthFieldLength);
        }

        if (lengthFieldOffset > maxFrameLength - lengthFieldLength) {
            throw new IllegalArgumentException(
                    "maxFrameLength (" + maxFrameLength + ") " +
                    "must be equal to or greater than " +
                    "lengthFieldOffset (" + lengthFieldOffset + ") + " +
                    "lengthFieldLength (" + lengthFieldLength + ").");
        }

        this.maxFrameLength = maxFrameLength;
        this.lengthFieldOffset = lengthFieldOffset;//2
        this.lengthFieldLength = lengthFieldLength;//4
        this.lengthAdjustment = lengthAdjustment;
        lengthFieldEndOffset = lengthFieldOffset + lengthFieldLength;
        this.initialBytesToStrip = initialBytesToStrip;
    }

    @Override
    protected Command decode(
            ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
    	
    	logger.debug("buffer hex--------最最开始接收到的-------------- is:"+ ChannelBuffers.hexDump(buffer).toUpperCase());

    	
        if (discardingTooLongFrame) {//false
            long bytesToDiscard = this.bytesToDiscard;
            int localBytesToDiscard = (int) Math.min(bytesToDiscard, buffer.readableBytes());
            buffer.skipBytes(localBytesToDiscard);
            bytesToDiscard -= localBytesToDiscard;
            this.bytesToDiscard = bytesToDiscard;
            if (bytesToDiscard == 0) {
                // Reset to the initial state and tell the handlers that
                // the frame was too large.
                discardingTooLongFrame = false;
                long tooLongFrameLength = this.tooLongFrameLength;
                this.tooLongFrameLength = 0;
                throw new TooLongFrameException(
                        "Adjusted frame length exceeds " + maxFrameLength +
                        ": " + tooLongFrameLength);
            } else {
                // Keep discarding.
            	logger.warn("error,Keep discarding");
                return null;
            }
        }
        //104 my length:104
        int bytesleng=buffer.readableBytes();//第二次为1，第三次为41错掉了
//        logger.debug("byteslength:"+bytesleng);
        if (bytesleng < lengthFieldEndOffset) {
            return null;
        }
        int actualLengthFieldOffset = buffer.readerIndex() + lengthFieldOffset;
        logger.debug("actualLengthFieldOffset is:"+actualLengthFieldOffset);
        long frameLength=0l;
        //4
        switch (lengthFieldLength) {
        case 1:
            frameLength = buffer.getUnsignedByte(actualLengthFieldOffset);
            break;
        case 2:
        	
            frameLength = ChannelBuffers.swapInt(buffer.getUnsignedShort(actualLengthFieldOffset));
            logger.debug("2222222222222frameLength:"+frameLength);
            break;
        case 3:
            frameLength = ChannelBuffers.swapInt(buffer.getUnsignedMedium(actualLengthFieldOffset));
            break;
        case 4:
            frameLength = ChannelBuffers.swapInt((int)buffer.getUnsignedInt(actualLengthFieldOffset));
            logger.debug("4444444444444frameLength:"+frameLength);
            break;
        case 8:
            frameLength = ChannelBuffers.swapLong(buffer.getLong(actualLengthFieldOffset));
            break;
        default:
            throw new Error("should not reach here");
        }
//        logger.debug("frameLength:"+frameLength);
        //229 is right, my 224
        if (frameLength < 0) {
            buffer.skipBytes(lengthFieldEndOffset);
            throw new CorruptedFrameException(
                    "negative pre-adjustment length field: " + frameLength);
        }
//0 8
        frameLength += lengthAdjustment + lengthFieldEndOffset;
        if (frameLength < lengthFieldEndOffset) {
            buffer.skipBytes(lengthFieldEndOffset);
            throw new CorruptedFrameException(
                    "Adjusted frame length (" + frameLength + ") is less " +
                    "than lengthFieldEndOffset: " + lengthFieldEndOffset);
        }
        //238-5= 233
      logger.debug("ServerDecoder frameLength:"+frameLength);
        if (frameLength > maxFrameLength) {
//        	new FastLengthFieldBasedFrameDecoder().decode(ctx, channel, buffer);
            // Enter the discard mode and discard everything received so far.
            discardingTooLongFrame = true;
            tooLongFrameLength = frameLength;
            bytesToDiscard = frameLength - buffer.readableBytes();
            buffer.skipBytes(buffer.readableBytes());
            //DELPHI`s  data buffers is error here
            logger.debug("171 null");

            return null;
        }
        // never overflows because it's less than maxFrameLength
        int frameLengthInt = (int) frameLength;
        int readableBytes =buffer.readableBytes();
        logger.debug("readableBytes length:"+readableBytes);
        if ( readableBytes< frameLengthInt) {
            return null;
        }
        if (initialBytesToStrip > frameLengthInt) {
            buffer.skipBytes(frameLengthInt);
            throw new CorruptedFrameException(
                    "Adjusted frame length (" + frameLength + ") is less " +
                    "than initialBytesToStrip: " + initialBytesToStrip);
        }
        buffer.skipBytes(initialBytesToStrip);
        logger.debug("ServerDecoder2222222222222222222====:"+frameLengthInt+"----"+initialBytesToStrip);
        ChannelBuffer b = buffer.readBytes(frameLengthInt - initialBytesToStrip);
        return createCommand(b);
    }

    /**获取接收到的数据包，并解析生成相应的 Command
     * 一个完整的数据包生成命令
     * @param readBytes
     * @return
     */
	public Command createCommand(ChannelBuffer buffer) {
		logger.debug("createCommand  开始解析命令");
		//233
//		logger.debug("createCommand hexDump buffer: "+ChannelBuffers.hexDump(buffer).toUpperCase());
		//;
//		short startTag = buffer.readShort();
//		if(startTag == Command.START_TAG){
//			//生成相应的Command
//		buffer.skipBytes(1);//跳过第一个byte 0x00
		CommandFactory factory = new CommandFactory();
		logger.debug("createCommand(ChannelBuffer buffer) ");
		return factory.parseCommand(buffer);
	}
	
}
