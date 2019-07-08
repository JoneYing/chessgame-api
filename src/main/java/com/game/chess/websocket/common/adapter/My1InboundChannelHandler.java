package com.game.chess.websocket.common.adapter;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 
 * @author YingJH
 *
 */
public class My1InboundChannelHandler extends ChannelInboundHandlerAdapter {

	//日志打印
    protected Logger logger = LogManager.getLogger();

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.debug("---My1InboundChannelHandler ....channelRead.... ");

        ctx.fireChannelRead(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        logger.debug("---My1InboundChannelHandler ....channelReadComplete.... ");
    }
}
