package cn.hsa.platform.netty.websocket.runner;

import cn.hsa.platform.domain.MessageInfoModel;
import cn.hsa.platform.dto.ImContentModel;
import cn.hsa.platform.netty.websocket.handler.HsaPlatformWebSocketHandler;
import cn.hsa.platform.service.MessageInfoService;
import cn.hsa.platform.util.Constants;
import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class WebSysSocketRunnable implements Runnable {

    private Logger logger = LoggerFactory.getLogger(WebSysSocketRunnable.class);
    private ChannelHandlerContext channelHandlerContext;

    private ImContentModel requestContentModel;

    private MessageInfoService messageInfoService;


    public WebSysSocketRunnable(ChannelHandlerContext ctx, ImContentModel messageRequest, MessageInfoService messageInfoService) {
        this.channelHandlerContext = ctx;
        this.requestContentModel = messageRequest;
        this.messageInfoService =messageInfoService;
    }

    /**
     *
     *  处理线程连接业务逻辑的方法,可以在该方法中执行对应的业务逻辑
     *  例如： 查询参数
     * <p>
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        logger.info("处理系统消息业务逻辑参数为:{}"+requestContentModel.toString());
        sendMessageInfo();
    }

    public void sendMessageInfo(){
        MessageInfoModel param =new MessageInfoModel();
        param.setDeptId(requestContentModel.getDeptId());
        param.setHospCode(requestContentModel.getHospCode());
        param.setReceiverId(requestContentModel.getUnionId()+"");
            //channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(messageInfoModels)));if (Constants.MSG_TYPE.MSG_XT.equals(requestContentModel.getType())) {
            // 推送系统消息
            // 此处可修改为从缓存中获取数据
            // 测试
            List<MessageInfoModel> sysMessageInfoList = new ArrayList<>();
            if (sysMessageInfoList != null && sysMessageInfoList.size() > 0) {
                messageInfoService.updateMessageInfoList(sysMessageInfoList);
                //获取与系统用户主键绑定的channel
                Map<String, Channel> channelMap = HsaPlatformWebSocketHandler.getSysChannelMap();
                channelMap.forEach((k, v) -> {
                    Channel channel = channelMap.get(k);
                    channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(sysMessageInfoList)));
                });
            }
        }
}
