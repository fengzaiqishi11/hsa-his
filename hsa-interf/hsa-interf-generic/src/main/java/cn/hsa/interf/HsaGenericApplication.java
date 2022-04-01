package cn.hsa.interf;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;

/**
 * @Package_name: cn.hsa.outpt
 * @Class_name: HsaGenericApplication
 * @Describe: his对外接口启动启动类
 * @Author: zengfeng
 * @Email: 954123283@qq.com
 * @Date: 2020/8/11 10:43
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@EnableCaching
@ImportResource("classpath*:config/spring.xml")
@MapperScan({"cn.hsa.module.interf.*.dao"})
@MapperScan({"cn.hsa.module.insure.*.dao"})
@SpringBootApplication(scanBasePackages = {"cn.hsa"})
@EnableEncryptableProperties
public class HsaGenericApplication {
    /**
     * @Method his对外接口启动函数
     * @Description his对外接口启动函数
     *
     * @Param
     * @Author zengfeng
     * @Date 2020/8/11 10:43
     * @Return void
     **/
    public static void main(String[] args) {
        SpringApplication.run(HsaGenericApplication.class, args);
    }
}