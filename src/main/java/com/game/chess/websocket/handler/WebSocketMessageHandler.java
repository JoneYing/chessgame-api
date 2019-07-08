package com.game.chess.websocket.handler;

import io.netty.handler.codec.http.websocketx.WebSocketFrame;

/**
 * 
 * @author YingJH
 *
 */
public interface WebSocketMessageHandler {


    public void onMessage(WebSocketFrame webSocketFrame);

}
