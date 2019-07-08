package com.game.chess.websocket.utils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.game.chess.websocket.adapter.TopicHandlerAdapter;
import com.game.chess.websocket.bean.WebSocketClient;
import com.game.chess.websocket.server.WSMessage;
import com.game.chess.websocket.topic.WSTopicHandler;

public class MessageUtils {


    public static String getHttpGetUri(String uri){
        int index = -1;
        if (uri != null && uri.length() > 0 && ((index = uri.indexOf("?")) > - 1)) {
            String requestUri = uri.substring(0 , index );
            return requestUri;
        }
        return uri;
    }

    public static Map<String , Object> getHttpGetParams(String uri){
        int index = -1;
        if (uri != null && uri.length() > 0 && ((index = uri.indexOf("?")) > - 1)) {
            String requestUri = uri.substring(index + 1);
            String[] reqs = requestUri.split("&");
            if (reqs != null && reqs.length > 0) {
                Map<String , Object> params = new HashMap<>();
                //name value 交替
                for (String req : reqs) {
                    String[] nameAndValue = req.split("=");
                    if (nameAndValue != null && nameAndValue.length == 2) {
                        String name = nameAndValue[0];
                        String value = nameAndValue[1];
                        params.put(name , value);
                    }
                }
                return params;
            }
        }
        return null;
    }


    /**
     * 判断是否含有特殊字符
     *
     * @param str
     * @return true为包含，false为不包含
     */
    public static boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    public static void sendMessage(Collection<WebSocketClient> clients , String message){
        if (clients != null) {
            for (WebSocketClient client : clients) {
                ChannelHandlerContext channelHandlerContext = client.getChannelHandlerContext();
                TextWebSocketFrame textFrame = new TextWebSocketFrame(message);
                channelHandlerContext.writeAndFlush(textFrame);
            }
        }
    }



    public static String swapMessage( WSMessage message){
        String msg = null;
        if (message != null) {
            message.newId();
            msg = JSONObject.toJSONString(message);
        }
        return msg;
    }



    public static void sendMessage(WebSocketClient client , WSMessage message){
        ChannelHandlerContext channelHandlerContext = client.getChannelHandlerContext();
        TextWebSocketFrame textFrame = new TextWebSocketFrame(JSONObject.toJSONString(message));
        channelHandlerContext.writeAndFlush(textFrame);
    }

    public static void sendMessage(WebSocketClient client , String message){
        ChannelHandlerContext channelHandlerContext = client.getChannelHandlerContext();
        TextWebSocketFrame textFrame = new TextWebSocketFrame(message);
        channelHandlerContext.writeAndFlush(textFrame);
    }


    public static void sendPingMessage(WebSocketClient client){
        ChannelHandlerContext channelHandlerContext = client.getChannelHandlerContext();
        if (channelHandlerContext.channel().isWritable()) {
            PingWebSocketFrame ping = new PingWebSocketFrame();
            channelHandlerContext.writeAndFlush(ping);
        }
    }


    public static WSTopicHandler getTopicHandler(String textMsg , Map<String , WSTopicHandler> topicMap) {
        WSMessage message = parseText(textMsg);
        if (message != null) {
            String topic = message.getTopic();
            if (topic != null) {
                return topicMap.get(topic);
            }
        }
        return null;
    }


    public static WSMessage parseText(String textFrame){
        WSMessage message = null;
        message = JSONObject.parseObject(textFrame , WSMessage.class);
        return message;
    }

    public static void sendMessage(String topic , String message) {
        ApplicationContext applicationContext = ApplicationContextHolder.applicationContext;
        TopicHandlerAdapter topicHandlerAdapter = applicationContext.getBean(TopicHandlerAdapter.class);
        topicHandlerAdapter.sendMessage(topic , message);
    }
    
    public static String installMessage(String sendId, String action, String result, Object content){
    	JSONObject sendContent = new JSONObject();
    	if(sendId !=null) sendContent.put("sendId", sendId);
    	sendContent.put("action", action);
		sendContent.put("result", result);
		sendContent.put("content", content);
		return sendContent.toJSONString();
    }
    
    public static void installAndSendMessage(String sendId, String action, String result, Object content,WebSocketClient webSocketClient){
		sendMessage(webSocketClient, installMessage(sendId, action, result, content));
    }
    
    public static void installAndSendMessage(String sendId, String action, String result, Object content,Collection<WebSocketClient> clients){
    	sendMessage(clients, installMessage(sendId, action, result, content));
    }

}
