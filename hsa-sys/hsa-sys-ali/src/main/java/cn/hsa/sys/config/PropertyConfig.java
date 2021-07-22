package cn.hsa.sys.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * 本地开发模式下，从properties文件中加载配置信息
 */
@Profile("local")
@Configuration
@PropertySource(value = "classpath:config/cache/cache.properties", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:config/db/db.properties", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:config/fsstore/fsstore.properties", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:config/gateway/gateway.properties", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:config/idgen/idgenerator.properties", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:config/job/job.properties", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:config/mq/mq.properties", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:config/security/security.properties", ignoreResourceNotFound = true)
@PropertySource(value = "classpath:config/workflow/workflow.properties", ignoreResourceNotFound = true)
public class PropertyConfig {


}
