package com.game.chess.websocket.topic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.channel.ChannelHandlerContext;

import com.game.chess.websocket.bean.WebSocketClient;
import com.game.chess.websocket.server.WSMessage;

/**
 * 
 * @Description 
 *
 * @author YingJH
 * @Date 2018年3月12日
 * @version v1.1
 */
public abstract class AbstractTopicHandler implements WSTopicHandler {

	//日志打印
    protected Logger logger = LogManager.getLogger();

    @Override
    public void onSubscribe(ChannelHandlerContext ctx) {
    }

    @Override
    public void onUnSubscribe(ChannelHandlerContext ctx) {
    }

    //@Override
    public void onMessageRecieved(ChannelHandlerContext ctx, WSMessage message) {
    }

    @Override
    public void onTopicRegistyCompleted(ChannelHandlerContext ctx, WebSocketClient webSocketClient) {
    }




}
