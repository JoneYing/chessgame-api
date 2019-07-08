package com.game.chess.websocket.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.game.chess.websocket.bean.WebSocketClient;

/**
 * 
 * @Description WebSocketClient 服务类接口
 *
 * @author YingJH
 * @Date 2018年3月12日
 * @version v1.1
 */
public interface WebSocketClientService {

	public void putWebSocketClient(String id, WebSocketClient webSocketClient);

	public Collection<WebSocketClient> getClientsByUri(String uri);

	public WebSocketClient getWebSocketClient(String id);
	
	public List<WebSocketClient> getWebSocketClientList(String... id);
	
	public List<WebSocketClient> getWebSocketClientList(Set<String> channelIds);
	
//	public Map<String,WebSocketClient> getWebSocketClientMap(String... channelId);

	public Collection<WebSocketClient> getAllClients();

	/*
	 * 已经发送ping 消息
	 */
	public void putPingClient(String channelId);

	/*
	 * 
	 * 客户端存活，删除ping 消息发送列表
	 */
	public void removePingClient(String channelId);

	public Collection<WebSocketClient> getPingClients(int pingTimes);

	public WebSocketClient removeWebSocketClient(String id);

	public Map<String, WebSocketClient> getWebSocketClientMap(String... channelId);

}
