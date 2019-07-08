package com.game.chess.websocket.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.chess.dao.redis.websocket.ChessRoomDao;
import com.game.chess.websocket.service.ChessRoomService;
import com.game.common.utils.StringUtil;


/**
 * 
 * @Description 麻将房 Service
 *
 * @author YingJH
 * @Date 2018年3月14日
 * @version v1.1
 */
@Service
public class ChessRoomServiceImpl implements ChessRoomService {
	
	@Autowired
	private ChessRoomDao chessRoomDao;
	
	public String createRoom(String channelId){
		StringBuilder sbRoomId = new StringBuilder("room-");
		sbRoomId.append(System.currentTimeMillis());
		String roomId = sbRoomId.toString();
		Boolean result = chessRoomDao.addChannelIdToRoom(roomId, channelId);
		if(result) return roomId;
		return null;
	}
	
	public Boolean addChannelIdToRoom(String roomId, String channelId){
		return chessRoomDao.addChannelIdToRoom(roomId, channelId);
	}
	

	@Override
	public Set<String> getChannelIdsByRoomId(String roomId) {
		return chessRoomDao.getChannelIdsByRoomId(roomId);
	}

	@Override
	public Set<String> getExcludeOneselfChannelIdsByRoomId(String roomId, String channelId) {
		Set<String> channelIds = chessRoomDao.getChannelIdsByRoomId(roomId);
		channelIds.remove(channelId);
		return channelIds;
	}

	@Override
	public void setRoomCurrentPlayChannelId(String roomId, String channelId) {
		if(StringUtil.isNotBlank(roomId) && StringUtil.isNotBlank(channelId)){
			chessRoomDao.setRoomCurrentPlayChannelId(roomId, channelId);
		}
	}

	@Override
	public String getRoomCurrentPlayChannelId(String roomId) {
		if(StringUtil.isBlank(roomId)){
			return null;
		}
		return chessRoomDao.getRoomCurrentPlayChannelId(roomId);
	}
	
}
