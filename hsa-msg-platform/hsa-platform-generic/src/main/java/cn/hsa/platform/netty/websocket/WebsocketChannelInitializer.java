package cn.hsa.platform.netty.websocket;

import cn.hsa.platform.netty.websocket.handler.ChannelAuthHandler;
import cn.hsa.platform.netty.websocket.handler.ChannelPingHandler;
import cn.hsa.platform.netty.websocket.handler.HsaPlatformWebSocketHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 *  WebSocket连接通道处理Handler初始化
 */
@Component
public class WebsocketChannelInitializer extends ChannelInitializer<SocketChannel> {


    private HsaPlatformWebSocketHandler platformWebSocketHandler;

    private ChannelAuthHandler channelAuthHandler;

    private ChannelPingHandler channelPingHandler;

    @Value("${websocket.url}")
    private String websocketUrl;

    @Autowired
    public void setPlatformWebSocketHandler(HsaPlatformWebSocketHandler platformHandler){
        this.platformWebSocketHandler = platformHandler;
    }
    @Autowired
    public void setChannelAuthHandler(ChannelAuthHandler authHandler){
        this.channelAuthHandler = authHandler;
    }
   @Autowired
    public void setChannelPingHandler(ChannelPingHandler channelPingHandler){
        this.channelPingHandler = channelPingHandler;
    }
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        //获取pipeline通道
        ChannelPipeline pipeline = socketChannel.pipeline();
        //因为基于http协议，使用http的编码和解码器
        pipeline.addLast(new HttpServerCodec());
        //是以块方式写，添加ChunkedWriteHandler处理器
        pipeline.addLast(new ChunkedWriteHandler());
        /*
          说明
          1. http数据在传输过程中是分段, HttpObjectAggregator ，就是可以将多个段聚合
          2. 这就就是为什么，当浏览器发送大量数据时，就会发出多次http请求
        */
        pipeline.addLast(new HttpObjectAggregator(8192));
        // 鉴权handler必须加在WebSocket协议处理器之前否则当协议切换后该handler不会触发
        pipeline.addLast(channelAuthHandler);
        pipeline.addLast(channelPingHandler);
        /* 说明
          1. 对应websocket ，它的数据是以 帧(frame) 形式传递
          2. 可以看到WebSocketFrame 下面有六个子类
          3. 浏览器请求时 ws://localhost:7000/msg 表示请求的uri
          4. WebSocketServerProtocolHandler 核心功能是将 http协议升级为 ws协议 , 保持长连接
          5. 是通过一个 状态码 101
        */
        pipeline.addLast(new WebSocketServerProtocolHandler(websocketUrl));
        //自定义的handler ，处理业务逻辑
        pipeline.addLast(platformWebSocketHandler);

    }

    public String getWebSocketContextUrl(){
        return websocketUrl;
    }
}