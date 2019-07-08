package com.game.chess.websocket.resolver;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.*;

/**
 * 
 * @Description 
 *
 * @author YingJH
 * @Date 2018年3月12日
 * @version v1.1
 */
public interface DataFrameResolver<T extends WebSocketFrame> {


    /*
    * 处理数据帧接口
    */
    public void handlerWebSocketFrameData(ChannelHandlerContext ctx, T webSocketFrame);


}
