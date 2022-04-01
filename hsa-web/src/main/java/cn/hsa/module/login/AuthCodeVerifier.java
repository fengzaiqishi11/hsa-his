package cn.hsa.module.login;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.config.service.CenterGlobalConfigService;
import cn.hsa.module.center.parameter.dto.CenterParameterDTO;

import cn.hsa.util.Constants;
import cn.hsa.util.StringUtils;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


/**
 *  验证码校验器
 */
@Component
public class AuthCodeVerifier{

    private Logger log = LoggerFactory.getLogger(AuthCodeVerifier.class);

    /**
     *  系统参数查询服务
     */
    private CenterGlobalConfigService centerGlobalConfigService_consumer;
    /**
     *  是否校验会话验证码
     */
    private static final String isVerifyAuthCodeParameter = "VERIFY_AUTH_CODE";

    public AuthCodeVerifier(@Autowired  CenterGlobalConfigService service){
        this.centerGlobalConfigService_consumer = service;
    }

    // 设置缓存15分钟后过期
    private Cache<String,CenterParameterDTO> cache = CacheBuilder.newBuilder()
            .initialCapacity(5)
            .expireAfterWrite(15, TimeUnit.MINUTES)
            .build();

    private volatile CenterParameterDTO verifyCodeParameter;

    /**
     *  校验验证码
     * @param authCodeSession 会话中存储的验证码
     * @param inptAuthCode 用户输入的验证码
     */
    public void verifyAuthCode(String authCodeSession,String inptAuthCode) {
        // 获取系统参数是否执行验证码校验逻辑
        try{
            initCenterParameterDTO();
        }catch (ExecutionException e){
            log.error("加载验证码参数出现错误：",e);
        }
        // 跳过验证码验证逻辑
        if(verifyCodeParameter != null && Constants.SF.F.equals(verifyCodeParameter.getValue())) return;

        // 验证码已失效
        if (StringUtils.isEmpty(authCodeSession)) {
            throw new AppException("验证码已失效");
        }

        // 验证码错误
        if (!inptAuthCode.equalsIgnoreCase(authCodeSession)) {
            throw new AppException("验证码错误");
        }
    }

    private <T> T getData(WrapperResponse wr) {
        return (T)wr.getData();
    }

    /**
     *  初始化中心端校验参数
     */
    private void initCenterParameterDTO() throws ExecutionException {
        verifyCodeParameter = cache.get(isVerifyAuthCodeParameter,()->{
            CenterParameterDTO result = getData(centerGlobalConfigService_consumer.getParameterByCode(isVerifyAuthCodeParameter));
            if(null == result){
                // 初始化系统参数变量为空对象避免重复初始化;
                result = new CenterParameterDTO();
            }
            return result;
        });

    }
}
