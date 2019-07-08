package com.game.chess.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.game.chess.common.session.UserSession;
import com.game.chess.utils.SessionUtil;
import com.game.common.bean.BaseBean;

/**
 * 
 * @Description 公共Controller,所有的Controller都应直接或间接的继承该类
 *
 * @author YingJH
 * @Date 2018年3月9日
 * @version v1.1
 */
public abstract class BaseController {

	protected Logger logger = LogManager.getLogger();
	
	@Autowired
	protected HttpServletRequest request;// 注入request
	
	@Autowired
	protected HttpServletResponse response;// 注入response;
	
	/**
	 * 
	 * @Description 获取用户Session
	 *
	 * @author YingJH
	 * @Date 2018年3月9日
	 * @return
	 */
	protected UserSession getSession() {
		return SessionUtil.getLogined(request, response);
	}
	
	/**
	 * 
	 * @Description 补充基本信息
	 *
	 * @author YingJH
	 * @Date 2018年3月9日
	 * @param bean
	 */
	protected void supplementBasic(BaseBean bean) {
		UserSession session = SessionUtil.getLogined(request, response);
		StringBuilder sb = new StringBuilder();
		sb.append(session.getUserId());
		sb.append("-");
		sb.append(session.getName());
		String str = sb.toString();
		bean.setCreateBy(str);
		bean.setLastUpdateBy(str);
		bean.setCreateTime(new Date());
		bean.setLastUpdateTime(new Date());
	}
	
	/**
	 * 
	 * @Description 补充基本信息-用于批量
	 *
	 * @author YingJH
	 * @Date 2018年3月9日
	 * @param bean
	 * @param session
	 */
	protected void supplementBasic(BaseBean bean,UserSession session) {
		StringBuilder sb = new StringBuilder();
		sb.append(session.getUserId());
		sb.append("-");
		sb.append(session.getName());
		String str = sb.toString();
		bean.setCreateBy(str);
		bean.setLastUpdateBy(str);
		bean.setCreateTime(new Date());
		bean.setLastUpdateTime(new Date());
	}
	
	/**
	 * 
	 * @Description 补充最后更新人信息
	 *
	 * @author YingJH
	 * @Date 2018年3月9日
	 * @param bean
	 */
	protected void supplementLastUpdate(BaseBean bean) {
		UserSession session = SessionUtil.getLogined(request, response);
		StringBuilder sb = new StringBuilder();
		sb.append(session.getUserId());
		sb.append("-");
		sb.append(session.getName());
		bean.setLastUpdateBy(sb.toString());
		bean.setLastUpdateTime(new Date());
	}
	
	/**
	 * 
	 * @Description 补充最后更新人信息 -用于批量
	 *
	 * @author YingJH
	 * @Date 2018年3月9日
	 * @param bean
	 * @param session
	 */
	protected void supplementLastUpdate(BaseBean bean,UserSession session) {
		StringBuilder sb = new StringBuilder();
		sb.append(session.getUserId());
		sb.append("-");
		sb.append(session.getName());
		bean.setLastUpdateBy(sb.toString());
		bean.setLastUpdateTime(new Date());
	}
	
}
