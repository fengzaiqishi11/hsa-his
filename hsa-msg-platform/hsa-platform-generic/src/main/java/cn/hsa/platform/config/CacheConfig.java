package cn.hsa.platform.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.DefaultLettucePool;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePool;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 *  缓存相关的配置
 * @author luonianxin
 * @version 1.0
 * @date 2022/7/7 10:13
 */
@Configuration
public class CacheConfig {
    @Value("${session.redis.host:172.18.100.104}")
    private String redisHost;
    @Value("${session.redis.port:6379}")
    private int redisPort;
    @Value("${session.redis.password:powersi$123}")
    private String password;
    @Value("${session.redis.database:0}")
    private int database;

    @Bean
    public LettuceConnectionFactory getLettuceConnectionFactory(){
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisHost);
        configuration.setPort(redisPort);
        configuration.setDatabase(database);
        configuration.setPassword(RedisPassword.of(password));
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(configuration);

        return connectionFactory;
    }


    @Bean
    public RedisTemplate<String,Object> redisTemplate(@Autowired RedisConnectionFactory connectionFactory){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        StringRedisSerializer keySerializer = new StringRedisSerializer();
        RedisSerializer valueSerializer = new JdkSerializationRedisSerializer();
        StringRedisSerializer hashKeySerializer = new StringRedisSerializer();
        RedisSerializer hashValueSerializer = new JdkSerializationRedisSerializer();
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setValueSerializer(valueSerializer);
        redisTemplate.setHashKeySerializer(hashKeySerializer);
        redisTemplate.setHashValueSerializer(hashValueSerializer);
        redisTemplate.setConnectionFactory(connectionFactory);

        return redisTemplate;
    }
}
