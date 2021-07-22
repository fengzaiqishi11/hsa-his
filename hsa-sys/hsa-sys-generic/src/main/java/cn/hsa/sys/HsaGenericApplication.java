package cn.hsa.sys;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;

/**
 * @Package_name: cn.hsa.base
 * @Class_name: HsaGenericApplication
 * @Describe: 系统管理模块项目启动启动类
 * @Author: zengfeng
 * @Email: 954123283@qq.com
 * @Date: 2020/7/27 20:32
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@EnableCaching
@ImportResource("classpath*:config/spring.xml")
@MapperScan({"cn.hsa.module.sys.*.dao"})
@MapperScan({"cn.hsa.module.base.*.dao"})

@SpringBootApplication(scanBasePackages = {"cn.hsa"})
@EnableEncryptableProperties
public class HsaGenericApplication {
    /**
     * @Method 系统管理模块启动函数
     * @Description 系统管理启动函数
     *
     * @Param
     * @Author: zengfeng
     * @Email: 954123283@qq.com
     * @Date: 2020/7/27 20:32
     * @Return void
     **/
    public static void main(String[] args) {
        SpringApplication.run(HsaGenericApplication.class, args);
    }
}
