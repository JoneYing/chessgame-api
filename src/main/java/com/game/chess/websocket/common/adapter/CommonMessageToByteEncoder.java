package com.game.chess.websocket.common.adapter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.game.common.constant.GameConstant;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 
 * @author YingJH
 *
 */
@SuppressWarnings("rawtypes")
public class CommonMessageToByteEncoder extends MessageToByteEncoder {

	//日志打印
    protected Logger logger = LogManager.getLogger();

    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
    	logger.debug("...=============CommonMessageToByteEncoder========================...");
        String message = (String)msg;
        out.writeBytes(message.getBytes(GameConstant.GAME_ENCODE));

    }
}
