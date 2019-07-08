package com.game.chess.websocket.adapter;

import io.netty.channel.ChannelHandlerContext;

import java.util.Map;

import com.game.chess.websocket.bean.WebSocketClient;

/**
 * 处理器接口
 * @author YingJH
 *
 */
public interface WSHandlerAdapter {

    /*
    *
    * 处理客户端请求
    * */
    public void handleRequest(ChannelHandlerContext ctx, Object msg , WebSocketClient webSocketClient);
    
    /*
    *
    * 服务端处理(或者是推送处理)
    * */
    public void handleResponse(Map<String , Object> params);

    /*
    * 连接完成时调用
    * */
    public void onUpgradeCompleted(ChannelHandlerContext ctx,   WebSocketClient webSocketClient);

}
