package cn.hsa.insure.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *  自定义环境变量读取配置用于解决nacos配置地址读取的问题
 * @author luonianxin
 * @version 1.0
 * @date 2022/7/14 19:12
 */
public class HsaEnvPostProcessor implements EnvironmentPostProcessor {


    /**
     *  自定义配置文件存放目录
     */
    public static final String CONFIG_FILE_PATH = "./hsa-his/config/application.properties";


    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        try{
            File configFile = new File(CONFIG_FILE_PATH);
            if(configFile.exists()){
                InputStream inputStream = Files.newInputStream(configFile.toPath());
                Properties props = new Properties();
                props.load(inputStream);
                String addr =  props.getProperty("mspring.cloud.nacos.config.server-addr");
                String username =  props.getProperty("mspring.cloud.nacos.config.username");
                String password =  props.getProperty("mspring.cloud.nacos.config.password");
                if(StringUtils.hasText(addr)) {
                    MutablePropertySources propertySources = environment.getPropertySources();
                    OriginTrackedMapPropertySource propertySource = (OriginTrackedMapPropertySource) propertySources
                            .get("applicationConfig: [classpath:/bootstrap.properties]");
                    if (propertySource != null) {
                        Map<String, Object> propertiesMap = new HashMap<>(8);
                        for (String propertyName : propertySource.getPropertyNames()) {
                            Object propertyValue = propertySource.getProperty(propertyName);
                            if (propertyName.startsWith("mspring.cloud.nacos.config.server-addr") && StringUtils.hasText(addr)) {
                                propertiesMap.put(propertyName, addr);
                            } else if (propertyName.startsWith("mspring.cloud.nacos.config.username") && StringUtils.hasText(username)) {
                                propertiesMap.put(propertyName, username);
                            }else if (propertyName.startsWith("mspring.cloud.nacos.config.password") && StringUtils.hasText(password)) {
                                propertiesMap.put(propertyName, password);
                            } else {
                                propertiesMap.put(propertyName, propertyValue);
                            }
                        }
                        propertySource = new OriginTrackedMapPropertySource(propertySource.getName(), propertiesMap);
                        environment.getPropertySources().addLast(propertySource);
                    }
                }
                System.err.println("loaded customer properties :"+configFile.getAbsolutePath()+",contents: "+props);
            }
        }catch (IOException ioe){
            // 输出日志加载错误到控制台
            System.err.println("加载自定义配置文件出错："+ioe.getMessage());
            ioe.printStackTrace(System.err);
        }
    }
}
