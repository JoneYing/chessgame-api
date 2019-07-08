package com.game.chess.websocket.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;

import com.game.chess.websocket.adapter.WSHandlerAdapter;
import com.game.chess.websocket.bean.WebSocketClient;
import com.game.chess.websocket.mapping.WSRequestHandlerMapping;
import com.game.chess.websocket.resolver.UpgradeResolver;
import com.game.chess.websocket.service.WebSocketClientService;
import com.game.chess.websocket.utils.MessageUtils;

/**
 * 
 * @Description WebSocket 通道处理器
 *
 * @author YingJH
 * @Date 2018年3月12日
 * @version v1.1
 */
public class WebSocketChannelHandler extends ChannelInboundHandlerAdapter {

	private UpgradeResolver upgradeResolver;

	private WSRequestHandlerMapping requestHandlerMapping;

	private WebSocketClientService webSocketClientService;

	/**
	 * 读取通道中请求,进行请求分发
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		// 判断是否为http请求
		if (msg instanceof FullHttpRequest) {
			// 处理http请求
			httpHandler(ctx, msg);
		} else if (msg instanceof WebSocketFrame) {
			// 处理websocket请求
			wsHandler(ctx, msg);
		} else {
			throw new RuntimeException("无法处理的请求");
		}
	}

	private void wsHandler(ChannelHandlerContext ctx, Object msg) {
		String id = getChannelCtxId(ctx);
		// 获取请求处理器
		WSHandlerAdapter handlerAdapter = requestHandlerMapping.getFrameHandlerAdapterById(id);
		if (handlerAdapter == null)
			return;

		WebSocketFrame webSocketFrame = (WebSocketFrame) msg;
		WebSocketClient webSocketClient = webSocketClientService.getWebSocketClient(id);
		// 处理请求
		handlerAdapter.handleRequest(ctx, webSocketFrame, webSocketClient);
	}

	private void httpHandler(ChannelHandlerContext ctx, Object msg) {

		FullHttpRequest request = (FullHttpRequest) msg;
		String uri = request.uri();
		if (uri.startsWith("//"))
			request.setUri(uri.substring(1));

		WebSocketClient webSocketClient = new WebSocketClient();
		String id = getChannelCtxId(ctx);
		try {
			// 先注册请求处理器
			requestHandlerMapping.registHandlerAdapter(request, webSocketClient);
		} catch (Exception e) {
			e.printStackTrace();
			// 处理异常 没有具体映射的请求处理器
			upgradeResolver.handleRequestError(ctx, request, e);
			return;
		}
		WebSocketServerHandshaker handshaker = null;
		// upgrade 与 websocket 握手过程
		if ((handshaker = upgradeResolver.handleRequest(ctx, request)) == null)
			return;

		// 设置uri
		webSocketClient.setUri(MessageUtils.getHttpGetUri(request.uri()));
		// 设置请求参数
		webSocketClient.setReqParam(MessageUtils.getHttpGetParams(request.uri()));
		webSocketClient.setChannelHandlerContext(ctx);
		webSocketClient.setHandshaker(handshaker);
		// 注册连接管理器
		webSocketClientService.putWebSocketClient(id, webSocketClient);
		// 完成后调用
		webSocketClient.getHandlerAdapter().onUpgradeCompleted(ctx, webSocketClient);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
	}

	public String getChannelCtxId(ChannelHandlerContext ctx) {
		String id = ctx.channel().id().asLongText();
		return id;
	}

	public UpgradeResolver getUpgradeResolver() {
		return upgradeResolver;
	}

	public void setUpgradeResolver(UpgradeResolver upgradeResolver) {
		this.upgradeResolver = upgradeResolver;
	}

	public WSRequestHandlerMapping getRequestHandlerMapping() {
		return requestHandlerMapping;
	}

	public void setRequestHandlerMapping(WSRequestHandlerMapping requestHandlerMapping) {
		this.requestHandlerMapping = requestHandlerMapping;
	}

	public WebSocketClientService getWebSocketClientService() {
		return webSocketClientService;
	}

	public void setWebSocketClientService(WebSocketClientService webSocketClientService) {
		this.webSocketClientService = webSocketClientService;
	}

}
