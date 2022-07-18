package cn.hsa.platform.netty.websocket.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Base64;
import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  通道鉴权处理器
 * @author luonianxin
 * @version 1.0
 * @date 2022/7/7 9:26
 */
@Component
@Slf4j
@ChannelHandler.Sharable
public class ChannelAuthHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    /**
     *  redis会话key前缀
     */
    private static final String SESSION_PREFIX = "spring:session:sessions:";

    @Value("${websocket.url:/msg}")
    private String webSocketUrl;
    @Value("${session.cookie.base64Encode:true}")
    private boolean base64Encoding;
    private RedisTemplate<String,Object> redisTemplate;
    /**
     *  连接失败计数器
     */
    private final AtomicInteger failedConnectionCounts = new AtomicInteger(0);

    @Autowired
    public void setRedisTemplate(RedisTemplate<String,Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        String uri = request.uri();
        HttpHeaders headers = request.headers();
        String proxyRealIp = headers.get("X-Real-IP");
        String [] temp = uri.split("/");
        try {
            String userId = temp[2];
            String token = base64Encoding ? base64Decode(temp[3]) : temp[3]; // 初步阶段是cookie值
            if (verifyAuth(userId, token)) {
                request.setUri(webSocketUrl);
                // 对事件进行传播，直到完成WebSocket连接。
                ctx.fireChannelRead(request.retain());
                // 从这个通道移除鉴权handler避免每次都验证
                ctx.pipeline().remove(this);
                return;
            }
        }catch (Exception e){
            log.error("socket auth failed ：",e);
            InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
            String redirectClientIp = inetSocketAddress.getAddress().getHostAddress();
            String clientIp = proxyRealIp == null ? redirectClientIp : proxyRealIp;
            log.warn("a new client connect failed ,failed connection counts: {},current client ip is: {},requested uri is {}",failedConnectionCounts.incrementAndGet(),clientIp,uri);
        }
        ctx.channel().close();
    }

    /**
     *  校验当前连接是否已登录
     * @param userId 用户id
     * @param token 令牌，初步阶段是会话cookie
     * @return
     */
    private boolean verifyAuth(String userId, String token) {
        Long userInfoEntryKeyCounts = redisTemplate.opsForHash().size(SESSION_PREFIX+token);

        return userInfoEntryKeyCounts.intValue() > 0;
    }


    private String base64Decode(String base64Value) {
        try {
            byte[] decodedCookieBytes = Base64.getDecoder().decode(base64Value);
            return new String(decodedCookieBytes);
        }
        catch (Exception ex) {
            log.error("Unable to Base64 decode value: " + base64Value);
            return null;
        }
    }
}
