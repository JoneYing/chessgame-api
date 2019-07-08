package com.game.chess.websocket.topic.chat;

import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.game.chess.websocket.annotation.WSTopic;
import com.game.chess.websocket.bean.WebSocketClient;
import com.game.chess.websocket.server.WSMessage;
import com.game.chess.websocket.topic.AbstractTopicHandler;
import com.game.chess.websocket.utils.MessageUtils;

/**
 * 实现聊天在线人员的推送
 * @author YingJH
 *
 */
@Component
@WSTopic(topic = "chatOnlineList")
public class ChatOnlineListTopicHandler extends AbstractTopicHandler {


    private static ConcurrentHashMap<String , Object> onlineList = new ConcurrentHashMap<>();
    private static Object defaultObj = new Object();

    @Override
    public void onSubscribe(ChannelHandlerContext ctx) {
        super.onSubscribe(ctx);
        String id = ctx.channel().id().asLongText();
        if (!onlineList.contains(id)) {
            onlineList.put( id, defaultObj);
            pushOnlineList();
        }
        pushCurrentUser(ctx);
    }


    @Override
    public void onUnSubscribe(ChannelHandlerContext ctx) {
        super.onUnSubscribe(ctx);
        onlineList.remove(ctx.channel().id().asLongText());
    }


    public void pushCurrentUser(ChannelHandlerContext ctx) {
        String id = ctx.channel().id().asLongText();
        Map<String , Object> map = new HashMap<String , Object>();
        map.put("currentUser" ,id );
        MessageUtils.sendMessage("chatOnlineList" , JSONObject.toJSONString(map));
    }

    /*
    * 推送人员列表
    *
    * */
    public void pushOnlineList(){
        if (onlineList.size() > 0) {
            List<String > list = new ArrayList<>(onlineList.size());
            Set<String > keyset = onlineList.keySet();
            for (String key : keyset) {
                list.add(key);
            }
            Map<String , Object> map = new HashMap<String , Object>();
            map.put("chatOnlineList" ,list );
            MessageUtils.sendMessage("chatOnlineList" , JSONObject.toJSONString(map));
        }
    }


    @Override
    public void onMessageRecieved(ChannelHandlerContext ctx, WSMessage message) {
        logger.debug(" chatOnlineList onMessageRecieved : {}" ,message.getContent());
    }

    @Override
    public void onTopicRegistyCompleted(ChannelHandlerContext ctx, WebSocketClient webSocketClient) {
        logger.debug(" chatOnlineList onTopicRegistyCompleted : -------------" );
    }

}
