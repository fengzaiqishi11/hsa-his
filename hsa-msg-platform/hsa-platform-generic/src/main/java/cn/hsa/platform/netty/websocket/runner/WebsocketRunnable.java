package cn.hsa.platform.netty.websocket.runner;

import cn.hsa.platform.domain.MessageInfoModel;
import cn.hsa.platform.dto.ImContentModel;
import cn.hsa.platform.netty.websocket.handler.HsaPlatformWebSocketHandler;
import cn.hsa.platform.service.MessageInfoService;
import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class WebsocketRunnable implements Runnable {

    private Logger logger = LoggerFactory.getLogger(WebsocketRunnable.class);
    private ChannelHandlerContext channelHandlerContext;

    private ImContentModel requestContentModel;

    private MessageInfoService messageInfoService;


    public WebsocketRunnable(ChannelHandlerContext ctx, ImContentModel messageRequest,MessageInfoService messageInfoService) {
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
        logger.info("处理业务逻辑参数为:{}"+requestContentModel.toString());
        sendMessageInfo();
    }

    public void sendMessageInfo(){
        MessageInfoModel param =new MessageInfoModel();
        param.setDeptId(requestContentModel.getDeptId());
        param.setHospCode(requestContentModel.getHospCode());
        param.setReceiverId(requestContentModel.getUnionId()+"");
        // 推送科室消息
        List<MessageInfoModel> messageInfoModels =messageInfoService.getDeptMessageInfoList(param);
        if (messageInfoModels!=null &&messageInfoModels.size()>0) {
            messageInfoService.updateMessageInfoList(messageInfoModels);
            //获取与用户主键绑定的channel
            Map<String, Channel> channelMap = HsaPlatformWebSocketHandler.getChannelMap();
            Map<String, ImContentModel> imContentModelMap = HsaPlatformWebSocketHandler.getClientParamMap();
            channelMap.forEach((k, v) -> {
                Channel channel = channelMap.get(k);
                ImContentModel contentModel = imContentModelMap.get(k);
                if (requestContentModel.getDeptId().equals(contentModel.getDeptId())) {
                    channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(messageInfoModels)));
                }
            });
        }
            //channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(messageInfoModels)));
        // 推送系统消息
        List<MessageInfoModel> sysMessageInfoList =messageInfoService.getSysMessageInfoList(param);
        if (sysMessageInfoList!=null &&sysMessageInfoList.size()>0){
            messageInfoService.updateMessageInfoList(sysMessageInfoList);
            //获取与用户主键绑定的channel
            Map<String, Channel> channelMap = HsaPlatformWebSocketHandler.getChannelMap();
            channelMap.forEach((k,v)->{
                Channel channel = channelMap.get(k);
                channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(sysMessageInfoList)));
            });
        }
//        List<MessageInfoModel> messageInfoModels =messageInfoDao.queryMessageInfoByType(param);
//        // 查询系统消息
//        List<MessageInfoModel> sysMessageInfoList = messageInfoDao.querySysMessageInfoList(param);
//        messageInfoModels.addAll(sysMessageInfoList);
//        if (messageInfoModels!=null&&messageInfoModels.size()>0){
//            List<String> ids =new ArrayList<>();
//            for (MessageInfoModel msg:messageInfoModels){
//                ids.add(msg.getId());
//                msg.setCount(msg.getCount()+1); // 发送次数+1
//            }
//            if (ids!=null &&ids.size()>0){
//                messageInfoDao.updateMssageInfoBatchByMsgId(messageInfoModels);
//            }
//        }

    }

}
