package cn.hsa.platform.netty.websocket.handler;

import cn.hsa.platform.dao.MessageInfoDao;
import cn.hsa.platform.domain.MessageInfoModel;
import cn.hsa.platform.dto.ImContentModel;
import cn.hsa.platform.netty.websocket.runner.WebSysSocketRunnable;
import cn.hsa.platform.netty.websocket.runner.WebsocketRunnable;
import cn.hsa.platform.service.MessageInfoService;
import cn.hsa.platform.util.Constants;
import cn.hsa.platform.utils.WebSocketUtils;
import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private static Map<String, String> clientMap = new ConcurrentHashMap<>();

    /**
     * 任务map，存储future，用于停止队列任务
     */
    private static Map<String, ImContentModel> paramMap = new ConcurrentHashMap<>();

    /**
     *  存储系统消息连接用户
     */
    private static Map<String, Channel> sysChannelMap = new ConcurrentHashMap<>();

    @Resource
    private MessageInfoDao messageInfoDao;

    @Resource
    private MessageInfoService messageInfoService;

    private static final String PING_MSG = "ping";
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
            log.info("received msg from client,chanel id is: {} msg text is: {}",ctx.channel().id().asShortText(),msa);
            if(PING_MSG.equalsIgnoreCase(msa)) return;
            //接受客户端发送的消息
            ImContentModel messageRequest = JSON.parseObject(msg.text(), ImContentModel.class);

            //每个channel都有id，asLongText是全局channel唯一id
            String key = ctx.channel().id().asLongText();
            //存储channel的id和用户的主键
            clientMap.put(key, messageRequest.getUnionId());
            // 存储客户端请求参数
            paramMap.put(key,messageRequest);
            log.info("accept msg from client......" + ctx.channel().remoteAddress() + "-params[" + messageRequest.getUnionId() + "]");
            if (!channelMap.containsKey(key)&&!sysChannelMap.containsKey(key)) {
                Future future =null;
                // 存储系统消息连接用户
                if (messageRequest.getType()!=null && Constants.MSG_TYPE.MSG_XT.equals(messageRequest.getType())) {
                    sysChannelMap.put(key, ctx.channel());
                    //使用channel中的任务队列，做周期循环推送客户端消息，解决问题二和问题五
                     future = ctx.channel().eventLoop().scheduleAtFixedRate(new WebSysSocketRunnable(ctx, messageRequest,messageInfoService), 0, 20, TimeUnit.SECONDS);
                }else {
                    //存储客户端和服务的通信的Chanel
                    channelMap.put(key, ctx.channel());
                    //使用channel中的任务队列，做周期循环推送客户端消息，解决问题二和问题五
                    future = ctx.channel().eventLoop().scheduleAtFixedRate(new WebsocketRunnable(ctx, messageRequest,messageInfoService), 0, 20, TimeUnit.SECONDS);
                }
                //存储每个channel中的future，保证每个channel中有一个定时任务在执行
                futureMap.put(key, future);
                if (messageRequest.getType()!=null && Constants.MSG_TYPE.MSG_HQ.equals(messageRequest.getType())) {
//                    MessageInfoModel param = new MessageInfoModel();
//                    param.setDeptId(messageRequest.getDeptId());
//                    param.setReceiverId(messageRequest.getUnionId() + "");
//                    param.setHospCode(messageRequest.getHospCode());
//                    List<MessageInfoModel> messageInfoModel = messageInfoDao.queryUnReadMessageInfoList(param);
//                    // 查询系统消息
//                    List<MessageInfoModel> sysMessageInfoList = messageInfoDao.querySysMessageInfoList(param);
//                    messageInfoModel.addAll(sysMessageInfoList);
                    List<MessageInfoModel> messageInfoModels = messageInfoService.getUnReadMsgList(getParam(messageRequest));
                    ctx.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(messageInfoModels)));
                    log.info("first connected do business. the param is:{}" , JSON.toJSONString(messageInfoModels));
                }
            } else {
                //每次客户端和服务的主动通信，和服务端周期向客户端推送消息互不影响
                // 主动通信业务在此处填写
                if (messageRequest.getType()!=null && Constants.MSG_TYPE.MSG_HQ.equals(messageRequest.getType())) {
//                    MessageInfoModel param = new MessageInfoModel();
//                    param.setDeptId(messageRequest.getDeptId());
//                    param.setReceiverId(messageRequest.getUnionId()+"");
//                    param.setHospCode(messageRequest.getHospCode());
//                    List<MessageInfoModel> messageInfoModel = messageInfoDao.queryMessageInfoByType(param);
//                    // 查询系统消息
//                    List<MessageInfoModel> sysMessageInfoList = messageInfoDao.querySysMessageInfoList(param);
//                    messageInfoModel.addAll(sysMessageInfoList);
                    List<MessageInfoModel> messageInfoModels = messageInfoService.getMessageInfoList(getParam(messageRequest));
//                    if (messageInfoModel != null && messageInfoModel.size() > 0) {
//                        List<String> ids = new ArrayList<>();
//                        String hospCode = messageInfoModel.get(0).getHospCode();
//                        for (MessageInfoModel infoModel : messageInfoModel) {
//                            ids.add(infoModel.getId());
//                            infoModel.setCount(infoModel.getCount()+1); // 发送次数+1
//                        }
//                        if (ids != null && ids.size() > 0) {
//                            messageInfoDao.updateMssageInfoBatchByMsgId(messageInfoModel);
//                        }
//                    }
                    ctx.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(messageInfoModels)));
                    log.info("active processing business :{}"+JSON.toJSONString(messageInfoModels));
                    messageInfoService.updateMessageInfoList(messageInfoModels);
                }else if (messageRequest.getType()!=null && Constants.MSG_TYPE.MSG_YD.equals(messageRequest.getType())){
//                       MessageInfoModel messageInfoModel =new MessageInfoModel();
//                       messageInfoModel.setId(messageRequest.getMsgId());
//                       messageInfoModel.setHospCode(messageRequest.getHospCode());
//                       messageInfoModel.setStatusCode(Constants.MSGZT.MSG_YD);
                       messageInfoDao.updateMssageInfoById(getParam(messageRequest));
                }
            }

        } catch (Exception e) {
            log.error("an error occurs during websocket server push msg to client ：", e);
            WebSocketUtils.writeAndFlushToChannel(ctx,new TextWebSocketFrame("websocket服务器处理连接发生错误："+e.getMessage()+ "服务器时间" + LocalDateTime.now()));
        }

    }

    public MessageInfoModel getParam(ImContentModel messageRequest){
        if (messageRequest.getType()!=null && Constants.MSG_TYPE.MSG_HQ.equals(messageRequest.getType())) {
            MessageInfoModel param = new MessageInfoModel();
            param.setDeptId(messageRequest.getDeptId());
            param.setReceiverId(messageRequest.getUnionId() + "");
            param.setHospCode(messageRequest.getHospCode());
            return param;
        }else if (messageRequest.getType()!=null && Constants.MSG_TYPE.MSG_YD.equals(messageRequest.getType())){
            MessageInfoModel messageInfoModel =new MessageInfoModel();
            messageInfoModel.setId(messageRequest.getMsgId());
            messageInfoModel.setHospCode(messageRequest.getHospCode());
            messageInfoModel.setStatusCode(Constants.MSGZT.MSG_YD);
            return messageInfoModel;
        }
        return new MessageInfoModel();
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
            //移除和用户绑定的参数
            paramMap.remove(key);
            //移除和系统消息用户绑定的channel
            sysChannelMap.remove(key);
            //关闭掉线客户端的future
            Optional.ofNullable(futureMap.get(key)).ifPresent(future -> {
            future.cancel(true);
            futureMap.remove(key);
            });
            log.info("a client removed......, channel id: {}. socket address is {}" ,key,ctx.channel().remoteAddress());
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
            //移除和用户绑定的参数
            paramMap.remove(key);
            //移除和系统消息用户绑定的channel
            sysChannelMap.remove(key);
            //移除定时任务
            Optional.ofNullable(futureMap.get(key)).ifPresent(future -> {
            future.cancel(true);
            futureMap.remove(key);
            });
            //关闭长连接
            ctx.close();
            log.error("error: " + cause.getMessage(),cause);
    }

    public static Map<String, Channel> getChannelMap() {
        return channelMap;
    }

    public static Map<String, Future> getFutureMap() {
        return futureMap;
    }

    public static Map<String, String> getClientMap() {
        return clientMap;
    }

    public static Map<String, ImContentModel> getClientParamMap() {
        return paramMap;
    }

    public static Map<String, Channel> getSysChannelMap() {
        return sysChannelMap;
    }

}