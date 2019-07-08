package com.game.chess.websocket.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.game.chess.dao.redis.websocket.ChessDao;
import com.game.chess.websocket.service.ChessService;


/**
 * 
 * @Description 麻将 Service
 *
 * @author YingJH
 * @Date 2018年3月14日
 * @version v1.1
 */
@Service
public class ChessServiceImpl implements ChessService{
	
	@Autowired
	private ChessDao chessDao;

	/**
	 * 
	 * @Description 根据棋牌id查询棋牌
	 *
	 * @author YingJH
	 * @Date 2018年3月14日
	 * @param chessId
	 * @return
	 */
	public Integer[] getChessByChessId(String chessId){
		return chessDao.getChessByChessId(chessId);
	}
	
	/**
	 * 新增棋牌
	 * @param chessId
	 * @param chess
	 * @return
	 */
	public Boolean createChess(String chessId,int[] chess){
		return chessDao.createChess(chessId, chess);
	}
	
	/**
	 * 删除棋牌
	 * @param chessId
	 * @return
	 */
	public Boolean removeChess(String chessId){
		return chessDao.removeChess(chessId);
	}
	
	/**
	 * 批量删除棋牌
	 * @param chessId
	 * @return
	 */
	public void removeChess(String... chessId){
		chessDao.removeChess(chessId);
	}

	@Override
	public void createResidueChess(String key, List<Integer> residue) {
		if(CollectionUtils.isEmpty(residue)) return;
		chessDao.createResidueChess(key, residue);
	}

	@Override
	public Integer getResidueOneChess(String key) {
		return chessDao.getResidueOneChess(key);
	}
	
}
