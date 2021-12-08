package cn.hsa.platform.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.FlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;
import redis.clients.jedis.JedisPoolConfig;


/**
 * redis全局session存储 配置类
 *
 * @author luonianxin
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 7200,redisNamespace = "spring:session",flushMode = FlushMode.ON_SAVE)
public class RedisSessionConfig extends RedisHttpSessionConfiguration {

    @Value("${session.redis.host}")
    private String host;
    @Value("${session.redis.port}")
    private int port;
    @Value("${session.redis.host}")
    private String password;
    @Value("${session.redis.database}")
    private int database = 0;
    @Value("${session.redis.timeout}")
    private int timeout;
    @Value("${session.redis.pool.maxIdle}")
    private int maxIdle;
    @Value("${session.redis.pool.maxTotal}")
    private int maxTotal;
    @Value("${session.redis.pool.maxWaitMillis}")
    private long maxWaitMillis;
    @Value("${session.redis.pool.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${session.redis.pool.testOnReturn}")
    private boolean testOnReturn;

    /**
     *  redis数据库连接池配置
     * @return JedisPoolConfig
     */
    @Bean
    public JedisPoolConfig poolConfig(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMaxWaitMillis(maxWaitMillis);
        poolConfig.setTestOnBorrow(testOnBorrow);
        poolConfig.setTestOnReturn(testOnReturn);
        return poolConfig;
    }

    /**
     *  session 管理器redis连接配置
     * @return  JedisConnectionFactory
     */
    @Bean
    public JedisConnectionFactory connectionFactory(@Autowired JedisPoolConfig poolConfig) {

        // 单机版redis配置
        RedisStandaloneConfiguration rsc = new RedisStandaloneConfiguration();
        // 设置服务器地址
        rsc.setHostName(host);
        // 如果redis服务器设置了密码,则设置密码
        rsc.setPassword(RedisPassword.of(password));
        // 端口设置
        rsc.setPort(port);
        rsc.setDatabase(database);

        // 获取最新版本的jedis连接配置构造器
        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfigurationBuilder = JedisClientConfiguration.builder();
        // 设置连接池配置
        jedisClientConfigurationBuilder.usePooling().poolConfig(poolConfig);
        // 获取构造完成的配置信息
        JedisClientConfiguration configuration = jedisClientConfigurationBuilder.build();
        // 返回redis连接工厂
        return new JedisConnectionFactory(rsc,configuration);
    }
}