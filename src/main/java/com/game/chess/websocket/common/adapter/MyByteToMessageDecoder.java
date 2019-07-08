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
public class MyByteToMessageDecoder extends ByteToMessageDecoder {
   
	//日志打印
    protected Logger logger = LogManager.getLogger();

	@Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.isReadable()) {
            int readLength = byteBuf.readableBytes();
            byte[] buf = new byte[readLength];
            byteBuf.readBytes(buf);
            String content = new String(buf , GameConstant.GAME_ENCODE);
            logger.debug("  MyByteToMessageDecoder .... content : {}",content);
            list.add(content);
        }

        logger.debug("  MyByteToMessageDecoder .... decode ");

    }
}
