package com.game.chess.websocket.adapter.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Collection;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.game.chess.websocket.adapter.KeepAliveHandlerAdapter;
import com.game.chess.websocket.annotation.WSRequestMapping;
import com.game.chess.websocket.bean.WebSocketClient;
import com.game.chess.websocket.constant.WebSocketConstant;
import com.game.chess.websocket.service.WebSocketClientService;
import com.game.chess.websocket.utils.MessageUtils;

/**
 * 棋牌室聊天处理器
 * @author YingJH
 *
 */
@WSRequestMapping(uri = "/chat" )
public class ChatHandlerAdapter extends KeepAliveHandlerAdapter<TextWebSocketFrame> {

    @Override
    public void handlerWebSocketFrameData(ChannelHandlerContext ctx, TextWebSocketFrame webSocketFrame) {
    	logger.debug("---- ChatHandlerAdapter .....handlerWebSocketFrameData ....");

        String content = webSocketFrame.text();
        if (!WebSocketConstant.PING_MESSAGE.equals(content)) {
        	logger.debug("ChatHandlerAdapter ....content : {}",content);
            JSONObject chatContent = JSONObject.parseObject(content);
            String contentText = chatContent.getString("content");
            String targetId = chatContent.getString("targetId");

            WebSocketClientService webSocketClientService = applicationContext.getBean(WebSocketClientService.class);
            WebSocketClient webSocketClient = webSocketClientService.getWebSocketClient(targetId);

            JSONObject sendContent = new JSONObject();
            sendContent.put("content" , contentText);
            sendContent.put("type" , 1);
            sendContent.put("sendId" , ctx.channel().id().asLongText());
            MessageUtils.sendMessage(webSocketClient , sendContent.toJSONString() );
        }

        logger.debug("ChatHandlerAdapter ....content : {}",content);
        
    }


    @Override
    public void handleResponse(Map<String, Object> params) {
    	logger.debug("---- ChatHandlerAdapter .....handleResponse ....");

        WebSocketClientService webSocketCacheManager = applicationContext.getBean(WebSocketClientService.class);
        //聊天通道
        Collection<WebSocketClient> clients = webSocketCacheManager.getClientsByUri(getUri());
        if (clients != null) {
            for (WebSocketClient client : clients) {
                Channel channel = client.getChannelHandlerContext().channel();
                String id = channel.id().asLongText();
                JSONObject json = new JSONObject();
                json.put("id" , id);
                json.put("type" , 0);
                TextWebSocketFrame textFrame = new TextWebSocketFrame(json.toJSONString());
                channel.writeAndFlush(textFrame);
            }
        }

    }



    @Override
    public void onUpgradeCompleted(ChannelHandlerContext ctx, WebSocketClient webSocketClient) {

        String id = ctx.channel().id().asLongText();
        JSONObject json = new JSONObject();
        json.put("id" , id);
        json.put("type" , 0);
        TextWebSocketFrame textFrame = new TextWebSocketFrame(json.toJSONString());
        ctx.writeAndFlush(textFrame);

    }

}
