package com.game.chess.websocket.service;

import java.util.List;



/**
 * 
 * @Description 麻将 Service
 *
 * @author YingJH
 * @Date 2018年3月14日
 * @version v1.1
 */
public interface ChessService {

	/**
	 * 
	 * @Description 根据棋牌id查询棋牌
	 *
	 * @author YingJH
	 * @Date 2018年3月14日
	 * @param chessId
	 * @return
	 */
	public Integer[] getChessByChessId(String chessId);
	
	/**
	 * 新增棋牌
	 * @param chessId
	 * @param chess
	 * @return
	 */
	public Boolean createChess(String chessId,int[] chess);
	
	/**
	 * 删除棋牌
	 * @param chessId
	 * @return
	 */
	public Boolean removeChess(String chessId);
	
	/**
	 * 批量删除棋牌
	 * @param chessId
	 * @return
	 */
	public void removeChess(String... chessId);
	
	/**
	 * 将剩余麻将装入redis
	 * @param key
	 * @param residue
	 */
	public void createResidueChess(String key, List<Integer> residue);
	
	public Integer getResidueOneChess(String key);
	
}
