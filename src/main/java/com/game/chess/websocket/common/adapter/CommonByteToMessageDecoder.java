package com.game.chess.websocket.common.adapter;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.game.common.constant.GameConstant;

/**
 * 
 * @author YingJH
 *
 */
public class CommonByteToMessageDecoder extends ByteToMessageDecoder {

    //日志打印
    protected Logger logger = LogManager.getLogger();

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        logger.debug("...===============CommonByteToMessageDecoder======================...");
        int len = byteBuf.readableBytes();
        byte[] buf = new byte[len];
        byteBuf.readBytes(buf);
        String message = new String(buf , GameConstant.GAME_ENCODE);
        logger.debug("...===============CommonByteToMessageDecoder : {}",message);
    }
}
