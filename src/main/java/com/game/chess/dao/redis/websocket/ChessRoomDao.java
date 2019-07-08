package com.game.chess.dao.redis.websocket;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.game.chess.dao.redis.RedisClientTemplate;


/**
 * 
 * @Description 麻将房 Dao
 *
 * @author YingJH
 * @Date 2018年3月14日
 * @version v1.1
 */
@Repository
public class ChessRoomDao {

	@Autowired
	private RedisClientTemplate redisClientTemplate;
	
	private static ConcurrentHashMap<String, String> currentPlayMap= new ConcurrentHashMap<String, String>();

	/**
	 * 将麻将房当前出牌玩家存入Map
	 * @param roomId
	 * @param channelId
	 */
	public void setRoomCurrentPlayChannelId(String roomId, String channelId) {
		currentPlayMap.put(roomId, channelId);
	}

	/**
	 * 获取麻将房当前出牌玩家
	 * @param roomId
	 * @return
	 */
	public String getRoomCurrentPlayChannelId(String roomId) {
		return currentPlayMap.get(roomId);
	}
	
	/**
	 * 
	 * @Description 根据房间号查询客户端ChannelIds
	 *
	 * @author YingJH
	 * @Date 2018年3月14日
	 * @param roomId
	 * @return
	 */
	public Set<String> getChannelIdsByRoomId(String roomId){
		Set<String> channelIds = redisClientTemplate.smembers(roomId);
		return channelIds;
	}
	
	/**
	 * 根据房间号新增客户端ChannelId
	 * @param roomId
	 * @param channelId
	 * @return
	 */
	public Boolean addChannelIdToRoom(String roomId,String channelId){
		Long result = redisClientTemplate.sadd(roomId, channelId);
		if(result == null) return false;
		return true;
	}


}
