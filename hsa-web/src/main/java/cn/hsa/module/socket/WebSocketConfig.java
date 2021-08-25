package cn.hsa.module.socket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Package_name
 * @class_nameWebSocketConfig
 * @Description
 * @Author youxianlin
 * @Email: 254580179@qq.com
 * @Date2021/2/20 8:53
 * @Company 创智和宇
 **/
@Configuration
public class WebSocketConfig {
    /**
     * ServerEndpointExporter 作用
     * 这个Bean会自动注册使用@ServerEndpoint注解声明的websocket endpoint
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
