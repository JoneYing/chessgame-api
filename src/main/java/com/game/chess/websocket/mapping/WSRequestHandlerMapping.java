package com.game.chess.websocket.mapping;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.StringUtils;

import com.game.chess.websocket.adapter.TopicHandlerAdapter;
import com.game.chess.websocket.adapter.WSHandlerAdapter;
import com.game.chess.websocket.annotation.WSRequestMapping;
import com.game.chess.websocket.annotation.WSTopic;
import com.game.chess.websocket.bean.WebSocketClient;
import com.game.chess.websocket.constant.WebSocketConstant;
import com.game.chess.websocket.service.WebSocketClientService;
import com.game.chess.websocket.topic.WSTopicHandler;
import com.game.chess.websocket.utils.MessageUtils;

/**
 * 
 * @Description 请求映射处理器
 *
 * @author YingJH
 * @Date 2018年3月12日
 * @version v1.1
 */
public class WSRequestHandlerMapping implements
	ApplicationContextAware , ApplicationListener<ContextRefreshedEvent> ,BeanDefinitionRegistryPostProcessor{

    private static ApplicationContext applicationContext;
    private static BeanDefinitionRegistry beanRegistry;
//    private static ConfigurableListableBeanFactory beanFactory;

    private static ConcurrentHashMap<String , WSHandlerAdapter> uriAndHandlerAdapterMap = new ConcurrentHashMap<>();


    private volatile boolean isInit = false;
    /*
    * 获取请求uri 绑定的请求处理器
    *
    * */
    public WSHandlerAdapter getFrameHandlerAdapterByUri(String uri) {
        if (!uri.startsWith("/")) {
            uri = "/".concat(uri.trim());
        }
        return uriAndHandlerAdapterMap.get(uri);
    }


    public void init() {
        if (!isInit) {
            synchronized (this) {
                if (!isInit) {
                    /*
                    *
                    * 初始化请求处理器
                    * 把uri 对应的请求处理器加载到内存
                    * */
                    String[] handlerAdapterNames = applicationContext.getBeanNamesForType(WSHandlerAdapter.class);
                    if (handlerAdapterNames != null && handlerAdapterNames.length > 0) {
                        for (String handlerAdapterName : handlerAdapterNames ) {
                            WSHandlerAdapter handlerAdapter = applicationContext.getBean(handlerAdapterName ,WSHandlerAdapter.class );
                            WSRequestMapping requestMapping = handlerAdapter.getClass().getAnnotation(WSRequestMapping.class);
                            if (requestMapping != null) {
                                String uri = requestMapping.uri();
                                initHandlerAdapter(uri , handlerAdapter);
                            }
                        }
                    }


                    String[] topicHandlerNames = applicationContext.getBeanNamesForType(WSTopicHandler.class);
                    if (topicHandlerNames != null && topicHandlerNames.length > 0) {
                        for (String topicHandlerName : topicHandlerNames ) {
                            WSTopicHandler topicHandler = applicationContext.getBean(topicHandlerName ,WSTopicHandler.class );

                            WSTopic topicAnnotation = topicHandler.getClass().getAnnotation(WSTopic.class);
                            if (topicAnnotation != null) {
                                // 已经有单uri 请求的处理器了 WSHandlerAdapter
                                String uri = topicAnnotation.uri();
                                String topic = topicAnnotation.topic();

                                TopicHandlerAdapter topicHandlerAdapter = null;
                                WSHandlerAdapter handlerAdapter = uriAndHandlerAdapterMap.get(uri);
                                if (handlerAdapter == null) {
                                    //在spring 中注册一个 WSHandlerAdapter 的 bean
                                    GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
                                    beanDefinition.setBeanClass(TopicHandlerAdapter.class);
                                    String beanName = uri + "_" + TopicHandlerAdapter.class.getName();
                                    beanRegistry.registerBeanDefinition(beanName ,beanDefinition );
                                    topicHandlerAdapter = applicationContext.getBean(beanName ,TopicHandlerAdapter.class );
                                    uriAndHandlerAdapterMap.put(uri , topicHandlerAdapter);
                                } else if (handlerAdapter instanceof TopicHandlerAdapter) {
                                    topicHandlerAdapter = (TopicHandlerAdapter)handlerAdapter;
                                } else {
                                    throw new RuntimeException("已经有uri : " + uri + " 的请求处理器");
                                }
                                topicHandlerAdapter.subscribeHandler(topic ,topicHandler );
                            }
                        }
                    }
                    isInit = true;
                }
            }
        }
    }



    /*
    * 获取当前连接绑定的请求处理器
    *
    * */
    public WSHandlerAdapter getFrameHandlerAdapterById(String id) {
        WebSocketClientService webSocketClientService = applicationContext.getBean(WebSocketClientService.class);
        WebSocketClient webSocketClient = webSocketClientService.getWebSocketClient(id);
        if (webSocketClient != null) {
            return webSocketClient.getHandlerAdapter();
        }
        return null;
    }



    /*
    *
    * 为请求注册请求处理器
    *
    * */
    public void registHandlerAdapter(FullHttpRequest request , WebSocketClient webSocketClient ) {
        init();
        HttpHeaders httpHeaders = request.headers();
        String protocols = httpHeaders.get( WebSocketConstant.SEC_WEBSOCKET_PROTOCOL);
        if (protocols == null || protocols.equals("null")) {
            protocols = "";
        }
        String uri = request.uri();
        uri = MessageUtils.getHttpGetUri(uri);
        WSHandlerAdapter handlerAdapter = null;
        if (StringUtils.hasLength(uri)) {
            if ((handlerAdapter = getFrameHandlerAdapterByUri(uri)) == null) {
                throw new RuntimeException("未找到合适的请求处理器 : " + uri);
            }
        }
        webSocketClient.setHandlerAdapter(handlerAdapter);
    }


    /*
    *
    * 请求处理器初始化
    *
    * */
    public void initHandlerAdapter(String uri , WSHandlerAdapter handlerAdapter ) {
        uriAndHandlerAdapterMap.put(uri , handlerAdapter);
    }


    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            init();
        }
    }


	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//		WSRequestHandlerMapping.beanFactory = beanFactory;
	}


	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanRegistry) throws BeansException {
		WSRequestHandlerMapping.beanRegistry = beanRegistry;
	}

}
