package com.game.chess.dao.redis.websocket;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONArray;
import com.game.chess.dao.redis.RedisClientTemplate;
import com.game.common.utils.StringUtil;


/**
 * 
 * @Description 麻将 Dao
 *
 * @author YingJH
 * @Date 2018年3月14日
 * @version v1.1
 */
@Repository
public class ChessDao {

	@Autowired
	private RedisClientTemplate redisClientTemplate;
	
	/**
	 * 
	 * @Description 根据棋牌id查询棋牌
	 *
	 * @author YingJH
	 * @Date 2018年3月14日
	 * @param chessId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Integer[] getChessByChessId(String chessId){
		String chess = redisClientTemplate.get(chessId);
		Object parse = JSONArray.parse(chess);
		List<Integer> list =  (List<Integer>) parse;
		return (Integer[]) list.toArray();
	}
	
	/**
	 * 新增棋牌
	 * @param chessId
	 * @param chess
	 * @return
	 */
	public Boolean createChess(String chessId,int[] chess){
		String result = redisClientTemplate.set(chessId, Arrays.toString(chess));
		if(StringUtil.isNotEmpty(result)) return true;
		return false;
	}
	
	/**
	 * 删除棋牌
	 * @param chessId
	 * @return
	 */
	public Boolean removeChess(String chessId){
		Long result = redisClientTemplate.del(chessId);
		if(result != null) return true;
		return false;
	}
	
	/**
	 * 批量删除棋牌
	 * @param chessId
	 * @return
	 */
	public void removeChess(String... chessId){
		for(String id : chessId) redisClientTemplate.del(id);
	}
	
	/**
	 * 将剩余的麻将存入redis
	 * @param key
	 * @param residue
	 */
	public void createResidueChess(String key, List<Integer> residue){
		int size = residue.size();
		String[] array = new String[size];
		for(int i=0; i<size; i++){
			array[i]=String.valueOf(residue.get(i));
		}
		redisClientTemplate.lpush(key, array);
	}
	
	/**
	 * 弹出剩余麻将的第一张
	 * @param key
	 * @return
	 */
	public Integer getResidueOneChess(String key){
		String chess = redisClientTemplate.lpop(key);
		if(StringUtil.isBlank(chess) || "nil".equals(chess)) return null;
		return Integer.parseInt(chess);
	}
	
}
