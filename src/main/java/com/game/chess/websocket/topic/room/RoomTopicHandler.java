package com.game.chess.websocket.topic.room;

import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
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
import com.game.common.utils.StringUtil;

/**
 * 实现聊天功能
 * @author YingJH
 *
 */
@Component
@WSTopic(topic = "room")
public class RoomTopicHandler extends AbstractTopicHandler {

    @Override
    public void onMessageRecieved(ChannelHandlerContext ctx, WSMessage message) {
        logger.debug(" room onMessageRecieved : {}" ,message.getContent());
        WebSocketClientService webSocketClientService = ApplicationContextHolder.applicationContext.getBean(WebSocketClientService.class);
        ChessRoomService chessRoomService = ApplicationContextHolder.applicationContext.getBean(ChessRoomService.class);

        JSONObject contentJson = JSONObject.parseObject(message.getContent());
        String action = contentJson.getString("action");
        String contentText = contentJson.getString("content");
        String roomId = null, channelId = ctx.channel().id().asLongText();
        WebSocketClient webSocketClient = webSocketClientService.getWebSocketClient(channelId);
        
        //判断action是否为创建房间
        switch (action) {
		case WebSocketConstant.ROOM_CREATE_MESSAGE:
			roomId = chessRoomService.createRoom(channelId);
        	if(roomId == null){
                MessageUtils.installAndSendMessage(null, WebSocketConstant.ROOM_CREATE_MESSAGE, 
                		WebSocketConstant.MESSAGE_FAILURE, "创建失败.", webSocketClient);
                break;
        	}
        	webSocketClient.setRoomId(roomId);
        	webSocketClient.setWhetherCreate(true);
            MessageUtils.installAndSendMessage(null, WebSocketConstant.ROOM_CREATE_MESSAGE, 
            		WebSocketConstant.MESSAGE_SCUESS,roomId, webSocketClient);
			break;
		case WebSocketConstant.ROOM_JOIN_MESSAGE:
			roomId = contentText;
        	Boolean result = chessRoomService.addChannelIdToRoom(roomId, channelId);
        	if(!result){
        		MessageUtils.installAndSendMessage(null, WebSocketConstant.ROOM_JOIN_MESSAGE, 
        				WebSocketConstant.MESSAGE_FAILURE, "加入失败.", webSocketClient);
        		break;
        	}
        	webSocketClient.setRoomId(roomId);
        	// 取出该房间中其他成员
            Set<String> channelIds = chessRoomService.getExcludeOneselfChannelIdsByRoomId(roomId, channelId);
            Map<String,WebSocketClient> clients = webSocketClientService.getWebSocketClientMap(channelIds.toArray(new String[channelIds.size()]));
        	
            //设置当前玩家的上下游玩家 并组装房间玩家上下游关系json
            JSONArray array = new JSONArray();
            for (Map.Entry<String,WebSocketClient> entry : clients.entrySet()){
            	String key = entry.getKey();
        		WebSocketClient client = entry.getValue();
        		//设置当前玩家的上游玩家
        		if(StringUtil.isBlank(client.getAfter())){
            		client.setAfter(channelId);
            		webSocketClient.setBefore(key);
            	}
        		//当该房间其他玩家为3位,表示当前玩家为房间最后一位玩家。则需要为自己寻找下游玩家
        		if(clients.size() == 3 && StringUtil.isBlank(webSocketClient.getAfter()) &&
        				StringUtil.isBlank(client.getBefore())){
    				client.setBefore(channelId);
    				webSocketClient.setAfter(entry.getKey());
        		}
        		
        		JSONObject json = new JSONObject(); 
        		json.put("channelId", key);
        		json.put("before", client.getBefore());
        		json.put("after", client.getAfter());
        		array.add(json);
            }
            
            JSONObject json = new JSONObject(); 
            json.put("channelId", channelId);
            json.put("before", webSocketClient.getBefore());
            json.put("after", webSocketClient.getAfter());
            array.add(json);
            //推送上下游关系至玩家
            
            MessageUtils.installAndSendMessage("系统消息", WebSocketConstant.ROOM_JOIN_MESSAGE, 
            		WebSocketConstant.MESSAGE_SCUESS, array.toString(), webSocketClient);
		default:
			break;
		}
    }

    @Override
    public void onTopicRegistyCompleted(ChannelHandlerContext ctx, WebSocketClient webSocketClient) {
        logger.debug(" room onTopicRegistyCompleted : -------------" );
    }

    @Override
    public void onSubscribe(ChannelHandlerContext ctx) {
        super.onSubscribe(ctx);
    }

}
