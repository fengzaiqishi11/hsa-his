package cn.hsa.platform;

import cn.hsa.platform.netty.websocket.WebsocketInitialization;
import cn.hutool.core.net.Ipv4Util;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.InetAddress;

/**
 *    消息发送平台启动类
 * @author: nianxin.luo
 * @email: 1423364324@qq.com
 **/
@ImportResource("classpath*:config/spring.xml")
@MapperScan(basePackages={"cn.hsa.platform.dao"})
@SpringBootApplication(scanBasePackages = {"cn.hsa"})
@EnableEncryptableProperties
public class HsaPlatformGenericApplication {

    Logger log = LoggerFactory.getLogger(HsaPlatformGenericApplication.class);
    @Resource
    private WebsocketInitialization websocketInitialization;

    /**
     *  初始化websocket服务器运行端口
     */
    @PostConstruct
    public void start() {
        try {
            log.info(Thread.currentThread().getName() + ":websocket启动中......");
            websocketInitialization.init();
           String servingAddress = "ws://"+InetAddress.getLocalHost().getHostAddress()+":"+websocketInitialization.getPort()+"/msg";

            log.info(Thread.currentThread().getName() + "\n* websocket服务启动成功!!!\n" +
                    "* 现在你可以通过 '"+servingAddress+"'来访问接口了\n");
        } catch (Exception e) {
            log.error("websocket发生错误：",e);
        }
    }

     /**
      *  消息推送平台启动函数
      * @author: luonianxin
      * @date: 2021-12-02 17:02
      *
     **/
    public static void main(String[] args) {
        SpringApplication.run(HsaPlatformGenericApplication.class, args);
    }
}
