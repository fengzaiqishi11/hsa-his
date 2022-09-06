package cn.hsa.platform.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *   websocket配置类,表示启用WebSocket功能
 * @author: luonianxin
 * @date: 2021-12-07
 */
@Slf4j
@Component
public class WebsocketServer {
    @Resource
    private WebsocketChannelInitializer websocketChannelInitializer;

    @Value("${websocket.port}")
    private Integer port;

    /**
     *  websocket 服务端初始化方法
     * @Author: luonianxin
     * @throws InterruptedException
     */
    @Async
    public void init() throws InterruptedException {

        //bossGroup连接线程组，主要负责接受客户端连接，一般一个线程足矣
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //workerGroup工作线程组，主要负责网络IO读写
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {

            //启动辅助类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //bootstrap绑定两个线程组
            serverBootstrap.group(bossGroup, workerGroup);
            //设置通道为NioChannel
            serverBootstrap.channel(NioServerSocketChannel.class);
            //可以对入站\出站事件进行日志记录，从而方便我们进行问题排查。
            serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
            //设置自定义的通道初始化器，用于入站操作
            serverBootstrap.childHandler(websocketChannelInitializer);
            //启动服务器,本质是Java程序发起系统调用，然后内核底层起了一个处于监听状态的服务，生成一个文件描述符FD
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            //异步
            channelFuture.channel().closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     *  返回 websocktServer的服务端口与contextPath
     * @return java.lang.String
     */
    public String getWebSocketPortAndContextPath(){
        return  port+websocketChannelInitializer.getWebSocketContextUrl();
    }
}
