package cn.hsa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.FlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @Package_name: cn.hsa
 * @Class_name: HsaWebApplication
 * @Describe: 云HIS V2.0 统一提供前端接口服务模块
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/7/1 20:32
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@EnableCaching
@ImportResource("classpath*:config/spring.xml")
@ServletComponentScan(basePackages = {"cn.hsa.filter"})
@SpringBootApplication(scanBasePackages = {"cn.hsa"})
@EnableScheduling
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 7200,redisNamespace = "spring:session",flushMode = FlushMode.ON_SAVE)
public class HsaWebApplication {
    /**
     * @Method 前端接口模块启动函数
     * @Description 前端接口模块启动函数
     *
     * @Param
     * @Author zhongming
     * @Date 2020/7/1 20:43
     * @Return void
     **/
    public static void main(String[] args) {
        SpringApplication.run(HsaWebApplication.class, args);
    }
}