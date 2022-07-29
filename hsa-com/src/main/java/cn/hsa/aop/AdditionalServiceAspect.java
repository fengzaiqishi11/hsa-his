package cn.hsa.aop;

import cn.hsa.base.OpenAdditionalService;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.authorization.service.CenterFunctionAuthorizationService;
import cn.hsa.util.Constants;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * @Package_name: cn.hsa.aop
 * @Class_name:: AdditionalServiceAspect
 * @Description: 增值服务统一调用中心授权切面
 * @Author: yuelong.chen
 * @Date: 2022-07-26 14:45
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Aspect
@Component
@Slf4j
@Order(10000)
public class AdditionalServiceAspect {

    @Resource
    private CenterFunctionAuthorizationService centerFunctionAuthorizationService;

    /**
     * @Method: additionalPointcut
     * @Description: 切入点
     * @Param: execution 过滤的类的集合
     * @Param: annotation 过滤的注解集合
     * @Author: yuelong.chen
     * @Date: 2022-07-26 14:45
     **/
    @Pointcut("execution(* cn.hsa.*.*.service.impl..*.*(..)) && @annotation(cn.hsa.base.OpenAdditionalService)")
    public void additionalPointcut() {
    }

    /**
     * @Method: additionalPointcut
     * @Description: 前置通知（Before advice）
     * @Param: JoinPoint joinPoint
     * @Author: yuelong.chen
     * @Date: 2022-07-26 14:45
     **/
    @Before("additionalPointcut()")
    public void doAround(JoinPoint joinPoint) throws Throwable{

        // 获取方法上的OpenAdditionalService注解的属性
        OpenAdditionalService annotation = ((MethodSignature) joinPoint.getSignature()).getMethod()
                                            .getAnnotation(OpenAdditionalService.class);
        //addEnable属性标记为false，直接返回
        if(annotation == null || !annotation.addEnable()){
            return;
        }else if(Constants.ZZFW.DEFAULT.equals(annotation.orderTypeCode())){
            throw new AppException("orderTypeCode参数为空");
        }
        //获取传入的参数
        Object[] objs = joinPoint.getArgs();

        //参数合法性校验
        checkParam(objs);

        //中心端服务检测调用
        doCenterFunctionAuthorization(objs,annotation);

    }

    /**
     * @Method: checkParam
     * @Description: 参数合法性校验
     * @Param: Object[] objs 参数数组
     * @Author: yuelong.chen
     * @Date: 2022-07-26 16:45
     **/
    private void checkParam(Object[] objs) {
        String hospCode = (String) MapUtils.get((Map)objs[0], "hospCode");
        //校验参数
        if (objs == null || objs.length == 0) {
            throw new AppException("参数为空");
        }
        if (!(objs[0] instanceof Map)) {
            throw new AppException("参数类型错误");
        }
        if (StringUtils.isEmpty(hospCode)) {
            throw new AppException("无医院编码参数");
        }
    }

    /**
     * @Method: doCenterFunctionAuthorization
     * @Description: 中心端服务检测调用
     * @Param: String hospCode 医院编码
     * @Param: OpenAdditionalService annotation 目标方法上的注解
     * @Author: yuelong.chen
     * @Date: 2022-07-26 16:45
     **/
    private void doCenterFunctionAuthorization(Object[] objs, OpenAdditionalService annotation) {
        String hospCode = (String) MapUtils.get((Map)objs[0], "hospCode");
        Map map = new HashMap();
        map.put("hospCode",hospCode);
        map.put("orderTypeCode",annotation.orderTypeCode());
        //调用权限校验
        WrapperResponse wr = centerFunctionAuthorizationService
                .queryBizAuthorizationByOrderTypeCode(map);

        if (wr != null && wr.getCode()
                == WrapperResponse.FAIL){
            log.error("==-==增值服务统一调用中心授权切面失败==-==hospCode:{},desc:{},ERROR:{}",hospCode,annotation.desc(),wr.getMessage());
            throw new AppException(wr.getMessage());
        }
        log.info("==-==增值服务统一调用中心授权切面成功==-==hospCode:{},desc:{},SUCCESS:{}",hospCode,annotation.desc(),wr.getMessage());
    }
}
