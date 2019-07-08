package com.game.chess.websocket.bean;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;

import java.util.Map;
import java.util.Set;

import com.game.chess.websocket.adapter.WSHandlerAdapter;

/**
 * 
 * @Description websocket 客户端Bean 类
 *
 * @author YingJH
 * @Date 2018年3月12日
 * @version v1.1
 */
public class WebSocketClient {
	
	//sessionId 将websocket 请求与http session绑定
	private String sessionId;
	
	//房间号
	private String roomId;
	//状态
	private String status;
	//上一个玩家
	private String before;
	//下一个玩家
	private String after;
	//是否创建人
	private Boolean whetherCreate = false;
	
    private WebSocketServerHandshaker handshaker;
    
    private ChannelHandlerContext channelHandlerContext;
    
    /** 请求处理器 **/
    private WSHandlerAdapter handlerAdapter;
    
    //是否有protocol
    private boolean hasSubProtocols;
    private boolean isClosed;
    private String uri;
    private String[] protocols;
    private Set<String> topics;
    private Map<String ,Object> reqParam;

    private Long messageId;
    private Long lastAckTime;
    
	public Boolean getWhetherCreate() {
		return whetherCreate;
	}

	public void setWhetherCreate(Boolean whetherCreate) {
		this.whetherCreate = whetherCreate;
	}

	public String getBefore() {
		return before;
	}

	public void setBefore(String before) {
		this.before = before;
	}

	public String getAfter() {
		return after;
	}

	public void setAfter(String after) {
		this.after = after;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Map<String, Object> getReqParam() {
        return reqParam;
    }

    public void setReqParam(Map<String, Object> reqParam) {
        this.reqParam = reqParam;
    }

    public WebSocketServerHandshaker getHandshaker() {
        return handshaker;
    }

    public void setHandshaker(WebSocketServerHandshaker handshaker) {
        this.handshaker = handshaker;
    }

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }

    public void setChannelHandlerContext(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }


    public WSHandlerAdapter getHandlerAdapter() {
        return handlerAdapter;
    }

    public void setHandlerAdapter(WSHandlerAdapter handlerAdapter) {
        this.handlerAdapter = handlerAdapter;
    }

    public boolean isHasSubProtocols() {
        return hasSubProtocols;
    }

    public void setHasSubProtocols(boolean hasSubProtocols) {
        this.hasSubProtocols = hasSubProtocols;
    }

    public String[] getProtocols() {
        return protocols;
    }

    public void setProtocols(String[] protocols) {
        this.protocols = protocols;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }


    public Set<String> getTopics() {
        return topics;
    }

    public void setTopics(Set<String> topics) {
        this.topics = topics;
    }


    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getLastAckTime() {
        return lastAckTime;
    }

    public void setLastAckTime(Long lastAckTime) {
        this.lastAckTime = lastAckTime;
    }

    public void newLastAckTime() {
        this.lastAckTime = System.currentTimeMillis();
    }


}
