package cn.hsa.stro;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;

/**
 * @Package_name: cn.hsa.stro
 * @Class_name: HsaGenericApplication
 * @Describe: 药库模块项目启动启动类
 * @Author: zengfeng
 * @Email: 954123283@qq.com
 * @Date: 2020/7/30 10:32
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@EnableCaching
@ImportResource("classpath*:config/spring.xml")
@MapperScan({"cn.hsa.module.stro.*.dao"})
@MapperScan({"cn.hsa.module.phar.*.dao"})
@SpringBootApplication(scanBasePackages = {"cn.hsa"})
@EnableEncryptableProperties
public class HsaGenericApplication {
    /**
     * @Method 药库模块启动函数
     * @Description 药库模块启动函数
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