package cn.hsa.medical.payment;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;

/**
 * @Method 诊间支付模块启动函数
 * @Description 门急诊项模块启动函数
 * @Param args
 * @Author yuelong.chen
 * @Date 2022/8/30 11:43
 * @Return void
 **/
@EnableCaching
@ImportResource("classpath*:config/spring.xml")
@MapperScan({"cn.hsa.module.payment.dao"})
@SpringBootApplication(scanBasePackages = {"cn.hsa"})
@EnableEncryptableProperties
public class HsaGenericApplication {
    public static void main(String[] args) {
        SpringApplication.run(HsaGenericApplication.class, args);
    }
}
