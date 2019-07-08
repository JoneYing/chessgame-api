package com.game.chess.websocket.resolver;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.*;

/**
 * 
 * @Description 处理控制帧接口
 *
 * @author YingJH
 * @Date 2018年3月12日
 * @version v1.1
 */
public interface ControlFrameResolver {


    public void onWebSocketFrameClosed(ChannelHandlerContext ctx, CloseWebSocketFrame closeFrame, WebSocketServerHandshaker handshaker);

    public void onWebSocketFramePing(ChannelHandlerContext ctx, PingWebSocketFrame pingFrame);

    public void onWebSocketFramePong(ChannelHandlerContext ctx, PongWebSocketFrame pongFrame);


}
