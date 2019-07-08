package com.game.chess.websocket.topic.chat;

import io.netty.channel.ChannelHandlerContext;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.game.chess.websocket.annotation.WSTopic;
import com.game.chess.websocket.bean.WebSocketClient;
import com.game.chess.websocket.constant.WebSocketConstant;
import com.game.chess.websocket.server.WSMessage;
import com.game.chess.websocket.service.ChessRoomService;
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
@WSTopic(topic = "chatGroup")
public class ChatGroupTopicHandler extends AbstractTopicHandler {

    @Override
    public void onMessageRecieved(ChannelHandlerContext ctx, WSMessage message) {
        logger.debug(" chat onMessageRecieved : {}" ,message.getContent());

        String channelId = ctx.channel().id().asLongText();
        JSONObject jsonObject = JSONObject.parseObject(message.getContent());
        String content = jsonObject.getString("content");
        String roomId = jsonObject.getString("roomId");
        //发送消息
        ChessRoomService chessRoomService = ApplicationContextHolder.applicationContext.getBean(ChessRoomService.class);
        
        WebSocketClientService webSocketClientService = ApplicationContextHolder.applicationContext.getBean(WebSocketClientService.class);
        Set<String> channelIds = chessRoomService.getChannelIdsByRoomId(roomId);
        if (channelIds == null) return ;
        
    	List<WebSocketClient> clients = webSocketClientService.getWebSocketClientList(channelIds);
    	MessageUtils.installAndSendMessage(channelId, WebSocketConstant.CHAT_GOURP_MESSAGE,
    			WebSocketConstant.MESSAGE_SCUESS, content, clients);

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
