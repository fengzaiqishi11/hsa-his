package cn.hsa.platform.controller;

import cn.hsa.platform.dao.MessageInfoDao;
import cn.hsa.platform.domain.MessageInfoModel;
import cn.hsa.platform.netty.websocket.handler.HsaPlatformWebSocketHandler;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 一个提供webSocket长连接给前端使用的接口
 *
 * @author  luonianxin
 * @Date  2021-12-13
 */
@CrossOrigin
@RequestMapping("index")
@Controller
public class WebSocketController {

    @Autowired
    private MessageInfoDao messageInfoDao;
    /**
     *
     * @param id 用户主键
     * @param idList 要把消息发送给其他用户的主键
     */
    @RequestMapping("hello1")
    public String hello(Long id, List<Long> idList){
        //获取所有连接的客户端,如果是集群环境使用redis的hash数据类型存储即可
        Map<String, Channel> channelMap = HsaPlatformWebSocketHandler.getChannelMap();
        //获取与用户主键绑定的channel,如果是集群环境使用redis的hash数据类型存储即可
        Map<String, String> clientMap = HsaPlatformWebSocketHandler.getClientMap();
        //解决问题六,websocket集群中一个客户端向其他客户端主动发送消息，如何实现？
        clientMap.forEach((k,v)->{
            if (idList.contains(v)){
                Channel channel = channelMap.get(k);
                channel.eventLoop().execute(() -> channel.writeAndFlush(new TextWebSocketFrame(Thread.currentThread().getName()+"服务器时间" + LocalDateTime.now() + "wdy")));
            }
        });
        return "SUCCESS";
    }

    @ResponseBody
    @GetMapping("/msg/list")
    public List getMessageInfoList(MessageInfoModel messageInfoModel){
        return messageInfoDao.queryHistoryMessageInfoList(messageInfoModel);
    }
}


