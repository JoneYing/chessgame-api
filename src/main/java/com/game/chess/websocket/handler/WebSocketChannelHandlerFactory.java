package com.game.chess.websocket.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.game.chess.websocket.mapping.WSRequestHandlerMapping;
import com.game.chess.websocket.resolver.UpgradeResolver;
import com.game.chess.websocket.service.WebSocketClientService;

/**
 * 
 * @Description websocket channelHandler 请求处理工厂类 生成 WebSocketNettyServer 中的netty
 *              的ChannelHandler 的处理类
 *
 * @author YingJH
 * @Date 2018年3月12日
 * @version v1.1
 */
@Component
public class WebSocketChannelHandlerFactory {

	@Autowired
	private UpgradeResolver upgradeResolver;

	@Autowired
	private WSRequestHandlerMapping requestHandlerMapping;

	@Autowired
	private WebSocketClientService webSocketClientService;

	public WebSocketChannelHandler newWebSocketChannelHandler() {
		WebSocketChannelHandler webSocketChannelHandler = new WebSocketChannelHandler();
		// 设置请求uri 与请求处理器的 映射处理器
		webSocketChannelHandler.setRequestHandlerMapping(requestHandlerMapping);
		// websocket 升级请求处理器
		webSocketChannelHandler.setUpgradeResolver(upgradeResolver);
		// webscoket 客户端存储器
		webSocketChannelHandler.setWebSocketClientService(webSocketClientService);
		return webSocketChannelHandler;
	}

	public WebSocketOutboundChannelHandler newWebSocketOutboundChannelHandler() {
		WebSocketOutboundChannelHandler webSocketOutboundChannelHandler = new WebSocketOutboundChannelHandler();
		webSocketOutboundChannelHandler.setWebSocketClientService(webSocketClientService);
		return webSocketOutboundChannelHandler;
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