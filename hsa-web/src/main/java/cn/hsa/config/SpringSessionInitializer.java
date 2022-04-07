package cn.hsa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.filter.CharacterEncodingFilter;

/***
 *  spring Session初始化配置类
 * @Author: luonianxin
 * @Date: 2021-09-30 14:33
 */
@Configuration
public class SpringSessionInitializer extends AbstractHttpSessionApplicationInitializer {
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