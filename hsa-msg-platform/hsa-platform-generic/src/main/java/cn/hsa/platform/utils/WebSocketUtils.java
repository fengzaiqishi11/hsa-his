package cn.hsa.platform.utils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;


/**
 *  webSocket 工具类,简单封装信息写入
 */
public class WebSocketUtils {

    /**
     *  将信息回写socketChannel
     * @param context 通信渠道上下文
     * @param messageFrame 消息帧
     */
    public static void writeAndFlushToChannel(ChannelHandlerContext context, WebSocketFrame messageFrame){
        context.channel().writeAndFlush(messageFrame);
    }
}
