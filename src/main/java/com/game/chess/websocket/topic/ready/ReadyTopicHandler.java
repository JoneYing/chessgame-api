package com.game.chess.websocket.topic.ready;

import io.netty.channel.ChannelHandlerContext;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.game.analysis.changde.init.InitMJ;
import com.game.analysis.utils.ChessTable;
import com.game.chess.websocket.annotation.WSTopic;
import com.game.chess.websocket.bean.WebSocketClient;
import com.game.chess.websocket.constant.WebSocketConstant;
import com.game.chess.websocket.server.WSMessage;
import com.game.chess.websocket.service.ChessRoomService;
import com.game.chess.websocket.service.ChessService;
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
@WSTopic(topic = "ready")
public class ReadyTopicHandler extends AbstractTopicHandler {

    @Override
    public void onMessageRecieved(ChannelHandlerContext ctx, WSMessage message) {
        logger.debug(" ready onMessageRecieved : {}" ,message.getContent());
        WebSocketClientService webSocketClientService = ApplicationContextHolder.applicationContext.getBean(WebSocketClientService.class);
        ChessRoomService chessRoomService = ApplicationContextHolder.applicationContext.getBean(ChessRoomService.class);
        ChessService chessService = ApplicationContextHolder.applicationContext.getBean(ChessService.class);

        //JSONObject contentJson = JSONObject.parseObject(message.getContent());
        //String action = contentJson.getString("action");
        String channelId = ctx.channel().id().asLongText();
        
        // 取出该房间中其他成员
        String roomId = webSocketClientService.getWebSocketClient(channelId).getRoomId();
        Set<String> channelIds = chessRoomService.getChannelIdsByRoomId(roomId);
        Map<String,WebSocketClient> clients = webSocketClientService.getWebSocketClientMap(channelIds.toArray(new String[channelIds.size()]));
        Collection<WebSocketClient> readyClients = clients.values();
        clients.get(channelId).setStatus("ready");
        
        List<WebSocketClient> clientList = readyClients.stream().filter(f -> f.getStatus() != "ready").collect(Collectors.toList());
        // 2.检查成员是否都已经是已准备状态,如果是则开始游戏(发牌)
        if(CollectionUtils.isEmpty(clientList)){
        	process(chessService, roomId, clients);
        	return;
        }
    	MessageUtils.installAndSendMessage(channelId, WebSocketConstant.READY_MESSAGE,
			WebSocketConstant.MESSAGE_SCUESS, channelId+"已准备.", readyClients);
    }

	private void process(ChessService chessService, String roomId,
			Map<String, WebSocketClient> clients) {
		//开始游戏,初始化棋牌
		List<List<Integer>> list = InitMJ.init();
		// 将剩余的牌存入redis,剩余的牌角标为4(固定不变)
		StringBuilder sb = new StringBuilder(WebSocketConstant.CHESS);
		String residueId = sb.append(roomId).toString();
		chessService.createResidueChess(residueId, list.get(4));
		
		//保存棋牌到redis并发送棋牌给玩家
		int index = 0;
		for (Map.Entry<String,WebSocketClient> entry : clients.entrySet()){
			WebSocketClient client = entry.getValue();
			int[] chessArray = ChessTable.conversion(list.get(index));
			if(client.getWhetherCreate()){
				Integer chessIndex = chessService.getResidueOneChess(residueId);
				chessArray[chessIndex]+=1;
			}
			chessService.createChess(entry.getKey(), chessArray);
			MessageUtils.installAndSendMessage(null, WebSocketConstant.START_MESSAGE,
	    			WebSocketConstant.MESSAGE_SCUESS, chessArray, client);
			index++;
		}
	}

    @Override
    public void onTopicRegistyCompleted(ChannelHandlerContext ctx, WebSocketClient webSocketClient) {
        logger.debug(" ready onTopicRegistyCompleted : -------------" );
    }

    @Override
    public void onSubscribe(ChannelHandlerContext ctx) {
        super.onSubscribe(ctx);
    }
    
}
