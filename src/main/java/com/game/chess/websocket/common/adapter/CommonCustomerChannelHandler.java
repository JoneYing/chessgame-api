package com.game.chess.websocket.common.adapter;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.nio.NioSocketChannel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author YingJH
 *
 */
public class CommonCustomerChannelHandler extends ChannelInboundHandlerAdapter {

	//日志打印
    protected Logger logger = LogManager.getLogger();

	@Override
	@SuppressWarnings("unused")
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NioSocketChannel socketChannel = (NioSocketChannel) ctx.channel();

        logger.debug("---My2InboundChannelHandler ....channelRead.... ");

        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        System.out.println("---My2InboundChannelHandler ....channelReadComplete.... ");


        super.channelReadComplete(ctx);
    }
}
