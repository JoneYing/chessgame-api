package com.game.chess.websocket.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;

import com.game.chess.websocket.bean.WebSocketClient;
import com.game.chess.websocket.service.WebSocketClientService;

/**
 * 
 * @Description WebSocket 关闭处理器
 *
 * @author YingJH
 * @Date 2018年3月12日
 * @version v1.1
 */
public class WebSocketOutboundChannelHandler extends ChannelOutboundHandlerAdapter {

    private WebSocketClientService webSocketClientService;

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        super.disconnect(ctx, promise);
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        String id = ctx.channel().id().asLongText();
        WebSocketClient webSocketClient = webSocketClientService.getWebSocketClient(id);
        if (webSocketClient != null) {
            WebSocketServerHandshaker handshaker = webSocketClient.getHandshaker();
            Channel channel = ctx.channel();
            if (channel.isOpen()) {
                handshaker.close(ctx.channel() , new CloseWebSocketFrame());
            }
            webSocketClientService.removeWebSocketClient(id);
        }
        super.close(ctx, promise);
    }

	public WebSocketClientService getWebSocketClientService() {
		return webSocketClientService;
	}

	public void setWebSocketClientService(WebSocketClientService webSocketClientService) {
		this.webSocketClientService = webSocketClientService;
	}

}
