package com.game.chess.websocket.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.game.chess.websocket.handler.WebSocketChannelHandlerFactory;

/**
 * 
 * @Description netty websocket 服务器
 *
 * @author YingJH
 * @Date 2018年3月12日
 * @version v1.1
 */
public class WebSocketNettyServer {

    /** 端口号 **/
    private int port;
    private WebSocketChannelHandlerFactory webSocketChannelHandlerFactory;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public WebSocketNettyServer() {
        super();
    }


    public void startNettyServer() {
        executorService.execute(new Runnable() {

            @Override
            public void run() {
            	System.out.println("NettyServer Start..........");
                EventLoopGroup boss = new NioEventLoopGroup();
                EventLoopGroup worker = new NioEventLoopGroup();
                try {
                    ServerBootstrap bootstrap = new ServerBootstrap();
                    bootstrap.group(boss, worker);
                    bootstrap.channel(NioServerSocketChannel.class);
                    bootstrap.option(ChannelOption.SO_BACKLOG, 1024); //连接数
                    bootstrap.option(ChannelOption.TCP_NODELAY, true);  //不延迟，消息立即发送
		            //bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000);  //超时时间
                    bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true); //长连接
                    bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel)
                                throws Exception {
                            ChannelPipeline p = socketChannel.pipeline();

                            p.addLast("http-codec", new HttpServerCodec());
                            p.addLast("aggregator", new HttpObjectAggregator(65536));
                            p.addLast("http-chunked", new ChunkedWriteHandler());
                            //请求处理
                            p.addLast("inboundHandler", webSocketChannelHandlerFactory.newWebSocketChannelHandler());
                            //关闭处理
                            p.addLast("outboundHandler", webSocketChannelHandlerFactory.newWebSocketOutboundChannelHandler());
                        }
                    });

                    ChannelFuture f = bootstrap.bind(port).sync();
                    if (f.isSuccess()) {
                    }
                    f.channel().closeFuture().sync();
                } catch (Exception e) {
                	e.printStackTrace();
                } finally {
                    boss.shutdownGracefully();
		            worker.shutdownGracefully();
                }
            }
        });


    }


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    public WebSocketChannelHandlerFactory getWebSocketChannelHandlerFactory() {
        return webSocketChannelHandlerFactory;
    }

    public void setWebSocketChannelHandlerFactory(WebSocketChannelHandlerFactory webSocketChannelHandlerFactory) {
        this.webSocketChannelHandlerFactory = webSocketChannelHandlerFactory;
    }
}
