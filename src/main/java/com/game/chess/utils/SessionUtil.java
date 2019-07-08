package com.game.chess.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.game.chess.common.session.SessionContainer;
import com.game.chess.common.session.UserSession;

/**
 * 
 * @Description Session共享工具类
 *
 * @author YingJH
 * @Date 2018年3月9日
 * @version v1.1
 */
public class SessionUtil {

	private static SessionContainer<UserSession> sessionContainer;

	public SessionUtil(SessionContainer<UserSession> container) {
		System.out.println(container);
		sessionContainer = container;
	}

	public static void setLogined(HttpServletRequest request, HttpServletResponse response, UserSession session) {
		sessionContainer.set(request, response, session);
	}

	public static void updateLogined(HttpServletRequest request, HttpServletResponse response, UserSession session) {
		sessionContainer.update(request, response, session);
	}

	public static void update(String sessionId, UserSession session) {
		sessionContainer.update(sessionId, session);
	}

	public static UserSession getLogined(HttpServletRequest request, HttpServletResponse response) {
		return sessionContainer.get(request, response);
	}

	public static boolean isLogined(HttpServletRequest request, HttpServletResponse response) {
		return sessionContainer.has(request, response);
	}
}
