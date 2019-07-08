package com.game.chess.websocket.adapter.room;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.game.chess.websocket.adapter.KeepAliveHandlerAdapter;
import com.game.chess.websocket.annotation.WSRequestMapping;
import com.game.chess.websocket.bean.WebSocketClient;
import com.game.chess.websocket.constant.WebSocketConstant;
import com.game.chess.websocket.service.ChessRoomService;
import com.game.chess.websocket.service.WebSocketClientService;
import com.game.chess.websocket.utils.ApplicationContextHolder;
import com.game.chess.websocket.utils.MessageUtils;

/**
 * 棋牌室处理器
 * @author YingJH
 *
 */
@WSRequestMapping(uri = "/room" )
public class RoomHandlerAdapter extends KeepAliveHandlerAdapter<TextWebSocketFrame> {

    @Override
    public void handlerWebSocketFrameData(ChannelHandlerContext ctx, TextWebSocketFrame webSocketFrame) {
    	logger.debug("---- RoomHandlerAdapter .....handlerWebSocketFrameData ....");
        String content = webSocketFrame.text();
        logger.debug("RoomHandlerAdapter ....content : {}",content);
        WebSocketClientService webSocketClientService = ApplicationContextHolder.applicationContext.getBean(WebSocketClientService.class);
        ChessRoomService chessRoomService = ApplicationContextHolder.applicationContext.getBean(ChessRoomService.class);
        
        if (WebSocketConstant.PING_MESSAGE.equals(content)) return;
        JSONObject contentJson = JSONObject.parseObject(content);
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
            MessageUtils.installAndSendMessage(null, WebSocketConstant.ROOM_JOIN_MESSAGE, 
            		WebSocketConstant.MESSAGE_SCUESS, "加入成功.", webSocketClient);
		default:
			break;
		}
        logger.debug("RoomHandlerAdapter ....content : {}",content);
    }


    @Override
    public void handleResponse(Map<String, Object> params) {
    	logger.debug("---- RoomHandlerAdapter .....handleResponse ....");

//        WebSocketClientService webSocketCacheManager = applicationContext.getBean(WebSocketClientService.class);
//        //聊天通道
//        Collection<WebSocketClient> clients = webSocketCacheManager.getClientsByUri(getUri());
//        if (clients != null) {
//            for (WebSocketClient client : clients) {
//                Channel channel = client.getChannelHandlerContext().channel();
//                String id = channel.id().asLongText();
//                JSONObject json = new JSONObject();
//                json.put("id" , id);
//                json.put("type" , 0);
//                TextWebSocketFrame textFrame = new TextWebSocketFrame(json.toJSONString());
//                channel.writeAndFlush(textFrame);
//            }
//        }

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
