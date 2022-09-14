package cn.hsa.platform.netty.websocket.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *  ping类型消息处理器
 * @author luonianxin
 * @version 1.0
 * @date 2022/8/26 14:10
 */
@Component
@Slf4j
@ChannelHandler.Sharable
public class ChannelPingHandler extends SimpleChannelInboundHandler<PingWebSocketFrame> {
    /**
     * Is called for each message of type {@link I}.
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
     *            belongs to
     * @param msg the message to handle
     * @throws Exception is thrown if an error occurred
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PingWebSocketFrame msg) throws Exception {
        ctx.write(new PongWebSocketFrame(msg.content()).retain());
    }
}
