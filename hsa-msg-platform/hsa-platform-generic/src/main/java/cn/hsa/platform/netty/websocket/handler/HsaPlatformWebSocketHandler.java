package cn.hsa.platform.netty.websocket.handler;

import cn.hsa.platform.dto.ImContentModel;
import cn.hsa.platform.netty.websocket.runner.WebsocketRunnable;
import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 保证处理器，在整个生命周期中就是以单例的形式存在，方便统计客户端的在线数量
 */
@Component
@Slf4j
@ChannelHandler.Sharable
public class HsaPlatformWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * `通道map，存储channel，用于群发消息，以及统计客户端的在线数量，解决问题问题三，
     *    如果是集群环境使用redis的hash数据类型存储即可
     */
    private static Map<String, Channel> channelMap = new ConcurrentHashMap<>();
    /**
     * 任务map，存储future，用于停止队列任务
     */
    private static Map<String, Future> futureMap = new ConcurrentHashMap<>();
    /**
     *  存储channel的id和用户主键的映射，客户端保证用户主键传入的是唯一值，
     *  解决问题四，如果是集群中需要换成redis的hash数据类型存储即可
     */
    private static Map<String, Long> clientMap = new ConcurrentHashMap<>();

    /**
     * 客户端发送给服务端的消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        try {
            String msa = msg.text();
            //接受客户端发送的消息
            ImContentModel messageRequest = JSON.parseObject(msg.text(), ImContentModel.class);

            //每个channel都有id，asLongText是全局channel唯一id
            String key = ctx.channel().id().asLongText();
            //存储channel的id和用户的主键
            clientMap.put(key, messageRequest.getUnionId());
            log.info("接受客户端的消息......" + ctx.channel().remoteAddress() + "-参数[" + messageRequest.getUnionId() + "]");

            if (!channelMap.containsKey(key)) {
                //使用channel中的任务队列，做周期循环推送客户端消息，解决问题二和问题五
                Future future = ctx.channel().eventLoop().scheduleAtFixedRate(new WebsocketRunnable(ctx, messageRequest), 0, 10, TimeUnit.SECONDS);
                //存储客户端和服务的通信的Chanel
                channelMap.put(key, ctx.channel());
                //存储每个channel中的future，保证每个channel中有一个定时任务在执行
                futureMap.put(key, future);
            } else {
                //每次客户端和服务的主动通信，和服务端周期向客户端推送消息互不影响 解决问题一
                ctx.channel().writeAndFlush(new TextWebSocketFrame(Thread.currentThread().getName() + "服务器时间" + LocalDateTime.now() + "hsa-msg-platform"));
            }

        } catch (Exception e) {

            log.error("websocket服务器推送消息发生错误：", e);

        }

    }



    /**
     * 客户端掉线时的操作
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

            String key = ctx.channel().id().asLongText();
            //移除通信过的channel
            channelMap.remove(key);
            //移除和用户绑定的channel
            clientMap.remove(key);
            //关闭掉线客户端的future
            Optional.ofNullable(futureMap.get(key)).ifPresent(future -> {
            future.cancel(true);
            futureMap.remove(key);
            });
            log.info("一个客户端移除......" + ctx.channel().remoteAddress());
            ctx.close();
    }

    /**
     * 发生异常时执行的操作
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            String key = ctx.channel().id().asLongText();
            //移除通信过的channel
            channelMap.remove(key);
            //移除和用户绑定的channel
            clientMap.remove(key);
            //移除定时任务
            Optional.ofNullable(futureMap.get(key)).ifPresent(future -> {
            future.cancel(true);
            futureMap.remove(key);
            });
            //关闭长连接
            ctx.close();
            log.info("异常发生 " + cause.getMessage());
    }

    public static Map<String, Channel> getChannelMap() {
        return channelMap;
    }

    public static Map<String, Future> getFutureMap() {
        return futureMap;
    }

    public static Map<String, Long> getClientMap() {
        return clientMap;
    }

}