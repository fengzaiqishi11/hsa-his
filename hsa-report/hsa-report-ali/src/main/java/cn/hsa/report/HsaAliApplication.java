package cn.hsa.report;

import com.taobao.pandora.boot.PandoraBootstrap;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;

@EnableCaching
@ImportResource("classpath:config/spring.xml")
@MapperScan({"cn.hsa.report.module1.dao", "cn.hsa.report.module2.dao"})
@SpringBootApplication(scanBasePackages = {"cn.hsa"})
public class HsaAliApplication {

    public static void main(String[] args) {
        // 启动 Pandora Boot 用于加载 Pandora 容器
        PandoraBootstrap.run(args);//使用hsf rpc则打开此注释

        SpringApplication.run(HsaAliApplication.class, args);

        // 标记服务启动完成,并设置线程 wait。防止用户业务代码运行完毕退出后，导致容器退出。
        PandoraBootstrap.markStartupAndWait();
    }

}
