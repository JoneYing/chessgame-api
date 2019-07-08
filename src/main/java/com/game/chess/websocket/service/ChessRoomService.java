package com.game.chess.websocket.service;

import java.util.Set;



/**
 * 
 * @Description 麻将房 Service
 *
 * @author YingJH
 * @Date 2018年3月14日
 * @version v1.1
 */
public interface ChessRoomService {

	/**
	 * 
	 * @Description 根据房间号查询客户端ChannelId
	 *
	 * @author YingJH
	 * @Date 2018年3月14日
	 * @param roomId
	 * @return
	 */
	Set<String> getChannelIdsByRoomId(String roomId);
	
	/**
	 * 
	 * @Description 根据房间号查询排除自己余下客户端ChannelId
	 *
	 * @author YingJH
	 * @Date 2018年3月14日
	 * @param roomId
	 * @return
	 */
	Set<String> getExcludeOneselfChannelIdsByRoomId(String roomId,String channelId);
	
	String createRoom(String channelId);
	
	Boolean addChannelIdToRoom(String roomId, String channelId);
	
	/**
	 * 存入当前出牌的玩家
	 * @param roomId
	 * @return
	 */
	void setRoomCurrentPlayChannelId(String roomId,String channelId);
	
	/**
	 * 获取当前出牌的玩家
	 * @param roomId
	 * @return
	 */
	String getRoomCurrentPlayChannelId(String roomId);

}
