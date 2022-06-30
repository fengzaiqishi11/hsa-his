package cn.hsa.inpt;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;

/**
 * @Package_name: cn.hsa.inpt
 * @Class_name: HsaGenericApplication
 * @Describe: 入出院子项目启动启动类
 * @Author: zengfeng
 * @Email: 954123283@qq.com
 * @Date: 2020/9/1 20:32
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@EnableCaching
@ImportResource("classpath*:config/spring.xml")
@MapperScan({"cn.hsa.module.inpt.*.dao"})
@MapperScan({"cn.hsa.module.clinical.*.dao"})
@MapperScan({"cn.hsa.module.oper.*.dao"})
@MapperScan({"cn.hsa.module.emr.*.dao"})
@MapperScan({"cn.hsa.module.mris.*.dao"})
@MapperScan({"cn.hsa.module.upload.*.dao"})
@MapperScan({"cn.hsa.module.insure.drgdip.dao"})
@SpringBootApplication(scanBasePackages = {"cn.hsa"})
@EnableEncryptableProperties
public class HsaGenericApplication {
    /**
     * @Method 入出院启动函数
     * @Description 住院护士站启动函数
     *
     * @Param
     * @Author: zengfeng
     * @Email: 954123283@qq.com
     * @Date: 2020/9/1 20:32
     * @Return void
     **/
    public static void main(String[] args) {
        SpringApplication.run(HsaGenericApplication.class, args);
    }
}
