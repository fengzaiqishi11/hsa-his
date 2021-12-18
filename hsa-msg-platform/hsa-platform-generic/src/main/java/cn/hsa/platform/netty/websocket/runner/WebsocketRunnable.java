package cn.hsa.platform.netty.websocket.runner;

import cn.hsa.platform.dto.ImContentModel;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WebsocketRunnable implements Runnable {

    private Logger logger = LoggerFactory.getLogger(WebsocketRunnable.class);
    private ChannelHandlerContext channelHandlerContext;

    private ImContentModel requestContentModel;


    public WebsocketRunnable(ChannelHandlerContext ctx, ImContentModel messageRequest) {
        this.channelHandlerContext = ctx;
        this.requestContentModel = messageRequest;
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
    }
}
