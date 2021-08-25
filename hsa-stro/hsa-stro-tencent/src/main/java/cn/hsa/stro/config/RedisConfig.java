package cn.hsa.stro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.ConfigureRedisAction;

/**
 * 解决腾讯云Redis问题：Spring session redis ERR unknown command 'CONFIG'
 * https://www.cnblogs.com/coderzl/p/7512644.html
 */
@Configuration
public class RedisConfig {
    @Bean
    public ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }
}
