package cn.hsa.module.login;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.config.service.CenterGlobalConfigService;
import cn.hsa.module.center.parameter.dto.CenterParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.Constants;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 *  验证码校验器
 */
@Component
public class AuthCodeVerifier{
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

    /**
     *  校验验证码
     * @param authCodeSession 会话中存储的验证码
     * @param inptAuthCode 用户输入的验证码
     */
    public void verifyAuthCode(String authCodeSession,String inptAuthCode){
        //获取系统参数是否执行验证码校验逻辑
        CenterParameterDTO verifyCodeParameter = getData(centerGlobalConfigService_consumer.getParameterByCode(isVerifyAuthCodeParameter));
        // 跳过验证码验证逻辑
        if(verifyCodeParameter != null){
            String verifyCodeParameterValue = verifyCodeParameter.getValue();
            if(Constants.SF.F.equals(verifyCodeParameterValue))
            return;
        }
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
}
