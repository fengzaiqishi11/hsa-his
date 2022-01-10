package cn.hsa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;
import org.springframework.session.web.http.DefaultCookieSerializer;
import redis.clients.jedis.JedisPoolConfig;


/**
 * redis全局session存储 配置类
 *
 * @author luonianxin
 */
@Configuration
@Primary()
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 7200,redisNamespace = "spring:session",redisFlushMode = RedisFlushMode.ON_SAVE)
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
     *  session 管理器redis连接配置
     * @return
     */
    @Bean
    public JedisConnectionFactory connectionFactory() {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setHostName(host);
        connectionFactory.setPort(port);
        connectionFactory.setDatabase(database);
        connectionFactory.setTimeout(timeout);
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMaxWaitMillis(maxWaitMillis);
        poolConfig.setTestOnBorrow(testOnBorrow);
        poolConfig.setTestOnReturn(testOnReturn);
        connectionFactory.setPoolConfig(poolConfig);
        return connectionFactory;
    }

    /**
     *  <p>自定义 Cookie序列化器，解决同一服务器不同应用串 Session的问题
     *  <p> 注意 cookiePath需要与对应应用的servlet.context-path 保持一致,
     *  <p>否则会出现验证码一直不正确导致无法登录的情况
     * @see org.springframework.session.web.http.DefaultCookieSerializer
     * @return org.springframework.session.web.http.DefaultCookieSerializer
     */
    @Bean
    public DefaultCookieSerializer getDefaultCookieSerializer(){
        DefaultCookieSerializer  cookieSerializer = new DefaultCookieSerializer();
        cookieSerializer.setCookiePath("/hsa-web");
        return cookieSerializer;
    }
}