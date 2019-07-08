package com.game.chess.websocket.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.game.chess.dao.redis.websocket.WebSocketClientDao;
import com.game.chess.websocket.bean.WebSocketClient;
import com.game.chess.websocket.service.WebSocketClientService;

/**
 * 
 * @Description WebSocketClient 服务类
 *
 * @author YingJH
 * @Date 2018年3月12日
 * @version v1.1
 */
@Service
public class WebSocketClientServiceImpl implements WebSocketClientService{

	@Autowired
    private WebSocketClientDao webSocketClientDao;
	
    public void putWebSocketClient(String id , WebSocketClient webSocketClient){
        webSocketClientDao.putWebSocketClient(id , webSocketClient);
    }


    public Collection<WebSocketClient> getClientsByUri (String uri) {
       return webSocketClientDao.getClientsByUri(uri);
    }


    public WebSocketClient getWebSocketClient(String id){
        WebSocketClient webSocketClient = null;
        webSocketClient = webSocketClientDao.getWebSocketClient(id);
        return webSocketClient;
    }
    
	public List<WebSocketClient> getWebSocketClientList(String... channelId) {
		if(channelId == null || channelId.length == 0) return null;
		List<WebSocketClient> list = new ArrayList<WebSocketClient>();
		for(String id : channelId){
			WebSocketClient webSocketClient = webSocketClientDao.getWebSocketClient(id);
			list.add(webSocketClient);
		}
		return list;
	}
	
	public Map<String,WebSocketClient> getWebSocketClientMap(String... channelId) {
		if(channelId == null || channelId.length == 0) return null;
		Map<String,WebSocketClient> map = new HashMap<String, WebSocketClient>();
		for(String id : channelId){
			WebSocketClient webSocketClient = webSocketClientDao.getWebSocketClient(id);
			map.put(id,webSocketClient);
		}
		return map;
	}


    public Collection<WebSocketClient> getAllClients () {
        return webSocketClientDao.getWebSocketClientAll();
    }

    /*
    * 已经发送ping 消息
    *
    * */
    public void putPingClient (String channelId) {
        Integer pingTimes = WebSocketClientDao.pingPongChannelsMap.get(channelId);
        if (pingTimes != null) {
            WebSocketClientDao.pingPongChannelsMap.put(channelId , pingTimes.intValue() + 1);
        } else {
            WebSocketClientDao.pingPongChannelsMap.put(channelId , 1);
        }
    }

    /*
    *
    * 客户端存活，删除ping 消息发送列表
    *
    * */
    public void removePingClient (String channelId) {
        WebSocketClientDao.pingPongChannelsMap.remove(channelId);
    }

    public Collection<WebSocketClient> getPingClients (int pingTimes) {
        if (WebSocketClientDao.pingPongChannelsMap.isEmpty()) {
            return null;
        }
        Collection<WebSocketClient> clients = null;
        Set<String> channelIds = WebSocketClientDao.pingPongChannelsMap.keySet();
        if (channelIds != null) {
            clients = new ArrayList<>();
            for (String channelId : channelIds) {
                Integer mPingTimes = WebSocketClientDao.pingPongChannelsMap.get(channelId);
                //超过ping 限制次数
                if (mPingTimes.intValue() >= pingTimes) {
                    WebSocketClient webSocketClient = webSocketClientDao.getWebSocketClient(channelId);
                    clients.add(webSocketClient);
                }
            }
        }
        return clients;
    }


    public WebSocketClient removeWebSocketClient(String id ){
        return webSocketClientDao.removeChannelHandlerContext(id);
    }


	@Override
	public List<WebSocketClient> getWebSocketClientList(Set<String> channelIds) {
		if(CollectionUtils.isEmpty(channelIds)) return null;
		List<WebSocketClient> list = new ArrayList<WebSocketClient>();
		for(String id : channelIds){
			WebSocketClient webSocketClient = webSocketClientDao.getWebSocketClient(id);
			list.add(webSocketClient);
		}
		return list;
	}






}
