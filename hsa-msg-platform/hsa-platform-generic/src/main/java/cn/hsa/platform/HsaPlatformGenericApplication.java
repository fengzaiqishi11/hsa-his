package cn.hsa.platform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 *    消息发送平台启动类
 * @author: nianxin.luo
 * @email: 1423364324@qq.com
 **/
@ImportResource("classpath*:config/spring.xml")
@MapperScan(basePackages={"cn.hsa.platform.dao"})
@SpringBootApplication(scanBasePackages = {"cn.hsa"})
public class HsaPlatformGenericApplication {

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
