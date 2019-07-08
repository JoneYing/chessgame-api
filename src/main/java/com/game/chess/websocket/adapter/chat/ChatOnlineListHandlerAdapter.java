package com.game.chess.websocket.adapter.chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.game.chess.websocket.adapter.KeepAliveHandlerAdapter;
import com.game.chess.websocket.annotation.WSRequestMapping;
import com.game.chess.websocket.bean.WebSocketClient;
import com.game.chess.websocket.service.WebSocketClientService;
import com.game.chess.websocket.utils.MessageUtils;

/**
 * 在线人员推送
 * @author YingJH
 *
 */
@WSRequestMapping(uri = "/chatOnlineList")
public class ChatOnlineListHandlerAdapter extends KeepAliveHandlerAdapter<TextWebSocketFrame> {


    @Override
    public void handlerWebSocketFrameData(ChannelHandlerContext ctx, TextWebSocketFrame webSocketFrame) {
    	logger.debug("---- ChatHandlerAdapter .....handlerWebSocketFrameData ....");
        String content = webSocketFrame.text();
        logger.debug("ChatHandlerAdapter ....content:{}",content);
    }


    @Override
    public void handleResponse(Map<String, Object> params) {
    	logger.debug("---- ChatHandlerAdapter .....handleResponse ....");

        WebSocketClientService webSocketCacheManager = applicationContext.getBean(WebSocketClientService.class);
        //聊天通道
        Collection<WebSocketClient> clients = webSocketCacheManager.getClientsByUri("/chat.do");

        //推送通道
        Collection<WebSocketClient> subscribeClients = webSocketCacheManager.getClientsByUri(getUri());
        if (clients == null) return ;
        Map<String , Object > onLineList = new HashMap<String , Object >();
        for (WebSocketClient client : clients) {
            String id = client.getChannelHandlerContext().channel().id().asLongText();
            onLineList.put(id , 1);
        }
        for (WebSocketClient client : subscribeClients) {
            JSONObject message = new JSONObject(onLineList);
            JSONObject newMessage = (JSONObject) message.clone();
            MessageUtils.sendMessage(client , newMessage.toJSONString());
        }
    }


    @Override
    public void onUpgradeCompleted(ChannelHandlerContext ctx, WebSocketClient webSocketClient) {
    }


}
