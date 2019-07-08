package com.game.chess.common.session;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import com.game.chess.common.enums.ChessGameEnum;
import com.game.chess.dao.redis.RedisClientTemplate;
import com.game.chess.utils.SecurityUtil;
import com.game.common.utils.PropertiesUtil;

/**
 * 
 * @Description Session 容器,用于共享Session。实际存储于Redis
 *
 * @author YingJH
 * @Date 2018年3月9日
 * @version v1.1
 */
@Component("sessionContainer")
public class SessionContainer<T extends SessionObject> {
	
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	
	private RedisSerializer<String> keySerializer = new StringRedisSerializer();
	private RedisSerializer<Object> valSerializer = new JdkSerializationRedisSerializer();
	
	private final String cookieName=ChessGameEnum.CookieName.SessionId.getValue();
	private int timeout=60*30;	
	private String cookiePath;
	
	public void set(HttpServletRequest request, HttpServletResponse response, T sessionObject){
		if(sessionObject!=null){
			String sessionId=generateSessionId(request,sessionObject.getUserId());
			sessionObject.setSessionId(sessionId);
			redisClientTemplate.setex(keySerializer.serialize(sessionId), timeout,
					valSerializer.serialize(sessionObject));
			setCookie(request, response, sessionId);
		}else{
			this.remove(request, response);
		}
	}

	public void update(HttpServletRequest request, HttpServletResponse response, T sessionObject){		
		if(sessionObject!=null){
			String sessionId=this.getCookie(request);
			if(StringUtils.isNotBlank(sessionId)){
				redisClientTemplate.setex(keySerializer.serialize(sessionId), timeout,
						valSerializer.serialize(sessionObject));
			}	
		}else{
			this.remove(request, response);
		}
	}
	public void update(String sessionId, T sessionObject){		
		if(sessionObject!=null && StringUtils.isNotBlank(sessionId)){
			redisClientTemplate.setex(keySerializer.serialize(sessionId), timeout,
					valSerializer.serialize(sessionObject));
		}			
	}

	@SuppressWarnings("unchecked")
	public T get(HttpServletRequest request, HttpServletResponse response){
		String sessionId=this.getCookie(request);		
		if(StringUtils.isBlank(sessionId)){
			return null;
		}
		byte[] val = redisClientTemplate.get(keySerializer.serialize(sessionId));
		return (T)valSerializer.deserialize(val);
	}
	
	private void remove(HttpServletRequest request, HttpServletResponse response){
		String sessionId=this.getCookie(request);
		if(StringUtils.isNotBlank(sessionId)){
			redisClientTemplate.del(sessionId);
			setCookie(request, response, null);
		}		
	}
	
	public Boolean has(String sessionId){
		if(StringUtils.isBlank(sessionId)){
			return null;
		}
		byte[] val = redisClientTemplate.get(keySerializer.serialize(sessionId));
		redisClientTemplate.expire(keySerializer.serialize(sessionId), timeout);
		return val != null;
	}
	
	public Boolean has(HttpServletRequest request, HttpServletResponse response){
		return has(getCookie(request));
	}
	
	/**
	 * 
	 * @Description 生成sessionId
	 *
	 * @author YingJH
	 * @Date 2018年3月8日
	 * @param request
	 * @param userId
	 * @return
	 */
	private String generateSessionId(HttpServletRequest request, long userId){
		String sessionId=null;
		int count=0;
		do{
			sessionId=SecurityUtil.md5(String.valueOf(userId+"%"+(++count)), "YjRiNzU=");	
		}while(has(sessionId));
		return sessionId;
	}
	
	private String getCookie(HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
		if(cookies!=null)
		for(Cookie cookie : cookies){
			if(cookieName.equals(cookie.getName())){
				return cookie.getValue();
			}
		}
		return null;
	}
	
	private void setCookie(HttpServletRequest request, HttpServletResponse response, String sessionId){
		Cookie cookie = new Cookie(cookieName,sessionId);
		cookie.setPath(this.cookiePath==null ? request.getContextPath() : this.cookiePath);
		//cookie.setMaxAge(sessionId==null ? 0 : timeout);
		cookie.setDomain(PropertiesUtil.getConfig("domain"));
		response.addCookie(cookie);
	}	
}
