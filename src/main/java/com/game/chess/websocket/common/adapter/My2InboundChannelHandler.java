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
public class My2InboundChannelHandler extends ChannelInboundHandlerAdapter {

	//日志打印
    protected Logger logger = LogManager.getLogger();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.debug("---My2InboundChannelHandler ....channelRead.... ");
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.debug("---My2InboundChannelHandler ....channelReadComplete.... ");
        super.channelReadComplete(ctx);
    }
}
