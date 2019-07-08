package com.game.chess.websocket.topic.chat;

import io.netty.channel.ChannelHandlerContext;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.game.chess.websocket.annotation.WSTopic;
import com.game.chess.websocket.bean.WebSocketClient;
import com.game.chess.websocket.server.WSMessage;
import com.game.chess.websocket.service.WebSocketClientService;
import com.game.chess.websocket.topic.AbstractTopicHandler;
import com.game.chess.websocket.utils.ApplicationContextHolder;
import com.game.chess.websocket.utils.MessageUtils;

/**
 * 实现聊天功能
 * @author YingJH
 *
 */
@Component
@WSTopic(topic = "chat")
public class ChatTopicHandler extends AbstractTopicHandler {

    @Override
    public void onMessageRecieved(ChannelHandlerContext ctx, WSMessage message) {
        logger.debug(" chat onMessageRecieved : {}" ,message.getContent());

        JSONObject jsonObject = JSONObject.parseObject(message.getContent());
        String content = jsonObject.getString("content");
        String targetId = jsonObject.getString("targetId");
        String sendUserId = jsonObject.getString("sendUserId");

        //发送消息
        WebSocketClientService webSocketClientService = ApplicationContextHolder.applicationContext.getBean(WebSocketClientService.class);
        WebSocketClient client = webSocketClientService.getWebSocketClient(targetId);
        if (client != null) {
            JSONObject msg = new JSONObject();
            msg.put("content" , content );
            msg.put("sendUserId" , sendUserId );
            MessageUtils.sendMessage(client , msg.toJSONString());
        }

    }

    @Override
    public void onTopicRegistyCompleted(ChannelHandlerContext ctx, WebSocketClient webSocketClient) {
        logger.debug(" chat onTopicRegistyCompleted : -------------" );
    }

    @Override
    public void onSubscribe(ChannelHandlerContext ctx) {
        super.onSubscribe(ctx);
    }

}
