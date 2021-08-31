package cn.hsa.xxljobexecutor;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;

/**
 * @Package_name: cn.hsa.base
 * @Class_name: HsaGenericApplication
 * @Describe: 医保管理模块项目启动启动类
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/7/27 20:32
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@EnableCaching
@ImportResource("classpath*:config/spring.xml")
@MapperScan({"cn.hsa.module.base.*.dao"})
@MapperScan({"cn.hsa.module.inpt.*.dao"})
@MapperScan({"cn.hsa.module.stro.*.dao"})
@MapperScan({"cn.hsa.module.insure.*.dao"})
@MapperScan({"cn.hsa.module.outpt.*.dao"})
@MapperScan({"cn.hsa.module.phar.*.dao"})
@MapperScan({"cn.hsa.module.xxljob.dao"})
@SpringBootApplication(scanBasePackages = {"cn.hsa"})
@EnableEncryptableProperties
public class XxlJobExecutorApplication {
    /**
     * @Method 医保管理模块启动函数
     * @Description 医保管理启动函数
     *
     * @Param
     * @Author: zhongming
     * @Email: 406224709@qq.com
     * @Date: 2020/7/27 20:32
     * @Return void
     **/
    public static void main(String[] args) {
        SpringApplication.run(XxlJobExecutorApplication.class, args);
    }
}
