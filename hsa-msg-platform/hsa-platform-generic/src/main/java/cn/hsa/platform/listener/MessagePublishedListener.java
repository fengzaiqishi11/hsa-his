package cn.hsa.platform.listener;

import cn.hsa.platform.domain.MessageInfoModel;
import cn.hsa.platform.event.MessagePublishEvent;
import cn.hsa.platform.netty.websocket.WebsocketServer;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *     一个在系统启动后执行对应方法的类
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE+10)
public class MessagePublishedListener implements ApplicationListener<MessagePublishEvent> {

    Logger log = LoggerFactory.getLogger(MessagePublishedListener.class);

    /**
     *  缓存操作模板类
     */
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * Handle an application event.
     *     处理消息发布后事件
     * @param event the event to respond to
     */

    @EventListener
    @Async
    @Override
    public void onApplicationEvent(MessagePublishEvent event) {
        log.info("线程 {} ,处理消息发布事件, 传播的消息是：{}",Thread.currentThread().getName(),event.getPublishedEventJsonObj());
        MessageInfoModel messageInfoModel = JSON.parseObject(event.getPublishedEventJsonObj(),MessageInfoModel.class);
        String hospCode = messageInfoModel.getHospCode();
        String hashKey = messageInfoModel.getId();
        stringRedisTemplate.boundHashOps(hospCode).put(hashKey,event.getPublishedEventJsonObj());
    }

}
