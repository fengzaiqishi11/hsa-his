package cn.hsa.platform.listener;

import cn.hsa.platform.netty.websocket.WebsocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *     一个在系统启动后执行对应方法的类
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApplicationStartedListener implements ApplicationListener<ApplicationStartedEvent> {

    Logger log = LoggerFactory.getLogger(ApplicationStartedListener.class);

    @Resource
    private WebsocketServer websocketInitialization;


    /**
     * Handle an application event.
     *     处理应用启动后事件
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        this.initWebSocketServer();
        this.printStartInfo();
    }


    /**
     *  初始化WebSocket服务器
     */
    private void initWebSocketServer(){
        try {
            log.info(AnsiOutput.toString(AnsiColor.BRIGHT_BLUE, Thread.currentThread().getName() + " : websocket启动中......"));
            websocketInitialization.init();
        } catch (Exception e) {
            log.error("初始化websocket发生错误：",e);
        }
    }

    private void printStartInfo(){
        String servingAddress = null;
        try {
            servingAddress = "ws://"+ InetAddress.getLocalHost().getHostAddress()+":"+websocketInitialization.getWebSocketPortAndContextPath();
        } catch (UnknownHostException e) {
            log.error(e.getMessage(), e);
        }
        log.info(AnsiOutput.toString(AnsiColor.BRIGHT_BLUE, Thread.currentThread().getName() + " : websocket服务启动成功!,接口运行地址是： ") + servingAddress);
    }
}
