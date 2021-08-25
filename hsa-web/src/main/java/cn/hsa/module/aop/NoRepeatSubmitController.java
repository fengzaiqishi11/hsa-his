package cn.hsa.module.aop;

import cn.hsa.base.BaseController;
import cn.hsa.base.NoRepeatSubmit;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.util.RedisUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Package_ame: cn.hsa.module.aop
 * @Class_name: hsa-his
 * @Description: 防止重复提交
 * @Author: zengfeng
 * @Email: 954123283@qq.com
 * @Date: 2021/1/18  17:40
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Aspect
@Component
public class NoRepeatSubmitController extends BaseController {

    @Resource
    private RedisUtils redisUtils;

    @Around("execution(* cn.hsa.module.*.*Controller.*(..)) && @annotation(nrs)")
    public Object arround(ProceedingJoinPoint pjp, NoRepeatSubmit nrs) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
        HttpServletRequest request = attributes.getRequest();
        String key = sessionId + "_" + request.getServletPath();
        if (!redisUtils.hasKey(key)) {// 如果缓存中有这个url视为重复提交
            redisUtils.set(key, key, 2);
            try {
                Object proceed = pjp.proceed();
                if (proceed != null) {
                    WrapperResponse res = (WrapperResponse) proceed;
                    if ("error".equals(res.getType())) {
                        return WrapperResponse.error(-1, res.getMessage(), null);
                    }
                }
                return proceed;
             } catch (Throwable throwable) {
                throw new AppException(throwable.getMessage());
            }
        }
        return WrapperResponse.error(-1, "2秒内不能重复操作", null);
    }
}
