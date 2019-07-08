package com.game.chess.websocket.constant;

/**
 * 
 * @Description websocket 常量类
 *
 * @author YingJH
 * @Date 2018年3月12日
 * @version v1.1
 */
public interface WebSocketConstant {

    public final static String WEBSOCKET= "websocket";
    
    public final static String Upgrade= "Upgrade";
    
    public final static String SEC_WEBSOCKET_PROTOCOL= "Sec-WebSocket-Protocol";
    
    public final static String DEFAULT_WEBSOCKET_ADDRESS_FORMAT="ws://%s";

    public final static String PING_MESSAGE="ping";
    
    public final static String ROOM_CREATE_MESSAGE="Room-Create";
    
    public final static String ROOM_JOIN_MESSAGE="Room-Join";
    
    public final static String READY_MESSAGE="ready";
    
    public final static String START_MESSAGE="start";

    public final static String CHAT_GOURP_MESSAGE="Chat-Group";
    
    public final static String MESSAGE_SCUESS="scuess";

    public final static String MESSAGE_FAILURE="failure";
    
    public final static String CHESS="chess-";
    
}
