package com.game.chess.common.session;

import com.game.common.bean.BaseBean;

/**
 * 
 * @Description Session父级抽象类,Session共享对象应当继承该类
 *
 * @author YingJH
 * @Date 2018年3月9日
 * @version v1.1
 */
public abstract class SessionObject extends BaseBean{

	private static final long serialVersionUID = 7901710245297579809L;
	private String sessionId;
	private Integer userId;
	
	public SessionObject() {
		super();
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public SessionObject(Integer userId) {
		super();
		this.userId = userId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
