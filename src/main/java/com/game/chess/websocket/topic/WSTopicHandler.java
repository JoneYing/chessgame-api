package com.game.chess.websocket.topic;

import io.netty.channel.ChannelHandlerContext;

import com.game.chess.websocket.bean.WebSocketClient;
import com.game.chess.websocket.server.WSMessage;

/**
 * 
 * @Description websocket topic 处理器
 *
 * @author YingJH
 * @Date 2018年3月12日
 * @version v1.1
 */
public interface WSTopicHandler {

    public void onSubscribe(ChannelHandlerContext ctx );

    public void onUnSubscribe( ChannelHandlerContext ctx );

    /*
    * 接收到客户端消息
    *
    * */
    public void onMessageRecieved(ChannelHandlerContext ctx, WSMessage message);

    /*
    * 连接建立
    *
    * */
    public void onTopicRegistyCompleted(ChannelHandlerContext ctx, WebSocketClient webSocketClient);

}
