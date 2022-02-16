package cn.hsa.report.config;

import com.bstek.ureport.console.UReportServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @Package_ame: cn.hsa.report.config
 * @Class_name: UReportConfig
 * @Description: 本地开发模式下，从properties文件中加载配置信息
 * @Author: liuzhuoting
 * @Email: zhuoting.liu@powersi.com
 * @Date: 2022/02/15 09:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Configuration
@ImportResource("classpath:ureport-console-context.xml")
@PropertySource(value = "classpath:config/ureport/ureport.properties", ignoreResourceNotFound = true)
public class UReportConfig {

    @Bean
    public ServletRegistrationBean buildUReportServlet() {
        return new ServletRegistrationBean(new UReportServlet(), "/ureport/*");
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer buildUReportConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer= new PropertySourcesPlaceholderConfigurer();
        configurer.setIgnoreUnresolvablePlaceholders(true);
        configurer.setOrder(1);
        return configurer;
    }

}
