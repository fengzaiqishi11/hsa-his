package cn.hsa.center;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Package_name: cn.hsa.center
 * @Class_name: HsaGenericApplication
 * @Describe: 中心平台模块项目启动启动类
 * @Author: zengfeng
 * @Email: 954123283@qq.com
 * @Date: 2020/7/30 10:32
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@EnableCaching
@ImportResource("classpath*:config/spring.xml")
@MapperScan({"cn.hsa.module.center.*.dao"})
@MapperScan({"cn.hsa.module.sync.*.dao"})
@SpringBootApplication(scanBasePackages = {"cn.hsa"})
@EnableEncryptableProperties
@EnableAsync
@EnableScheduling
public class HsaGenericApplication {
    /**
     * @Method 中心平台模块启动函数
     * @Description 中心平台模块启动函数
     *
     * @Param
     * @Author: zengfeng
     * @Email: 954123283@qq.com
     * @Date: 2020/7/30 10:32
     * @Return void
     **/
    public static void main(String[] args) {
        SpringApplication.run(HsaGenericApplication.class, args);
    }
}
