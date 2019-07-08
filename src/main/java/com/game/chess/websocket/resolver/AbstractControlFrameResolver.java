package com.game.chess.websocket.resolver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.*;

/**
 * 
 * @Description 
 *
 * @author YingJH
 * @Date 2018年3月12日
 * @version v1.1
 */
public abstract class AbstractControlFrameResolver implements ControlFrameResolver {

	//日志打印
    protected Logger logger = LogManager.getLogger();

    public void onWebSocketFrameClosed(ChannelHandlerContext ctx , CloseWebSocketFrame closeFrame , WebSocketServerHandshaker handshaker){
        doOnWebSocketFrameClosed(ctx , closeFrame , handshaker);
        if (handshaker != null){
            handshaker.close(ctx.channel(), closeFrame.retain());
        }
    }

    public void onWebSocketFramePing(ChannelHandlerContext ctx , PingWebSocketFrame pingFrame){
        //ping 消息一定要回复 pong
        try {
            doOnWebSocketFramePing(ctx , pingFrame);
        } finally {
            ctx.channel().write(
                    new PongWebSocketFrame(pingFrame.content().retain()));
        }
    }

    public void onWebSocketFramePong(ChannelHandlerContext ctx , PongWebSocketFrame pongFrame ){
        //获取pong
        doOnWebSocketFramePong(ctx ,pongFrame );
    }

    /*
    * 继承以下方法重写
    *
    * */
    protected void doOnWebSocketFrameClosed(ChannelHandlerContext ctx , CloseWebSocketFrame closeFrame , WebSocketServerHandshaker handshaker){
    }

    protected void doOnWebSocketFramePing(ChannelHandlerContext ctx , PingWebSocketFrame pingFrame){
    }

    protected void doOnWebSocketFramePong(ChannelHandlerContext ctx , PongWebSocketFrame pongFrame ){
    }



}
