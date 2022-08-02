/**
 * Copyright ? 2020 创智和宇. All rights reserved.
 *
 * @Title: LogAopAction
 * @Package: com.powersi.aop
 * @Description:
 * @author: yzb
 * @date: 2020/11/22 0022 22:32
 * @version: V1.0
 */
package cn.hsa.module.aop;

import cn.hsa.base.BaseController;
import cn.hsa.base.OptionalLog;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.sys.log.dto.HisLogInfoCzDTO;
import cn.hsa.module.sys.log.dto.HisLogInfoYcDTO;
import cn.hsa.module.sys.log.service.HisLogInfoCzService;
import cn.hsa.module.sys.log.service.HisLogInfoYcService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import com.alibaba.fastjson.JSON;

import com.alibaba.nacos.common.utils.UuidUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.rpc.RpcContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: LogAopAction
 * @Description: 日志记录切面
 * @author: yzb
 * @date: 2020/11/22 0022 22:32
 */
@Aspect
@Component
public class LogAopController extends BaseController {

    @Resource
    private HisLogInfoCzService hisLogInfoCzService_consumer;
    //
    @Resource
    private HisLogInfoYcService hisLogInfoYcService_consumer;

    /**
     * @param
     * @return
     * @Description: 设置操作日志切入点 记录操作日志 在注解的位置切入代码
     * @author: yzb
     * @date: 2020/11/23 0023 0:08
     */
    @Pointcut("execution(public * cn.hsa..*Controller.*(..))")
    public void operLogPoinCut() {
        System.out.println("进入环绕通知");
    }


    @Pointcut("execution(public * cn.hsa..*Controller.*(..))")
    public void aroundPoinCut() {
    }

    @Around(value = "LogAopController.aroundPoinCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result=null;

            String traceID = MDC.get("traceID");
            RpcContext.getContext().setAttachment("trace_id", traceID);
        try {
            result = proceedingJoinPoint.proceed();
            if (result != null) {
                WrapperResponse res = (WrapperResponse) result;
                if ("error".equals(res.getType())) {
                    return WrapperResponse.error(-1, res.getMessage(), null);
                }
            }
            return result;
        } catch (Throwable e) {
//            logger.info(e.getMessage());
            try {
                 // TODO 2020-12-24 注释  yzb 检查现场性能测试  出现异常，则记录异常信息。
                // saveErrorLog(proceedingJoinPoint, e);
            } catch (Throwable e2) {
//                logger.info(e2.getMessage());
            }finally {
                throw e;
             }
//            return WrapperResponse.error(-1,e.getMessage(),null);
        }
    }


    /**
     * @param
     * @param keys 返回结果
     * @return
     * @Description: 环绕通知
     * @author: yzb
     * @date: 2020/11/23   19:08
     */
    @AfterReturning(value = "operLogPoinCut()", returning = "keys")
    public Object around(JoinPoint joinPoint, Object keys) {

        Object result = null;

        try {
            // TODO 2020-12-24 注释  yzb 检查现场性能测试 记录正常操作日志
            // saveOperLogInfo(joinPoint, keys);

        } catch (Exception e) {

            return WrapperResponse.error(WrapperResponse.FAIL, e.getMessage(), null);
        }
        return WrapperResponse.success(result);
    }


    /**
     * 异常返回通知，用于拦截异常日志信息 连接点抛出异常后执行
     *
     * @param joinPoint 切入点
     * @param e         异常信息
     */
//    @AfterThrowing(value = "LogAopController.operExceptionLogPoinCut()", throwing = "e")
//    public Object saveExceptionLog(JoinPoint joinPoint, Throwable e) {
//        try {
//            // 出现异常，则记录异常信息。
//            saveErrorLog(joinPoint, e);
//        } catch (Throwable e2) {
//            e2.printStackTrace();
//        } finally {
//            return WrapperResponse.error(WrapperResponse.FAIL, e.getMessage(), null);
//        }
//    }


    /**
     * @param paramMap request获取的参数数组
     * @return
     * @Description: 转换request 请求参数
     * @author: yzb
     * @date: 2020/2/23 0023 0:07
     */
    public Map<String, String> converMap(Map<String, String[]> paramMap) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        for (String key : paramMap.keySet()) {
            rtnMap.put(key, paramMap.get(key)[0]);
        }
        return rtnMap;
    }

    /**
     * @param exceptionName    异常名称
     * @param exceptionMessage 异常信息
     * @param elements         堆栈信息
     * @return
     * @Description: 转换异常信息为字符串
     * @author: yzb
     * @date: 2020/2/23 0023 0:07
     */
    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuffer strbuff = new StringBuffer();
        for (StackTraceElement stet : elements) {
            strbuff.append(stet + "\n");
        }
        String message = exceptionName + ":" + exceptionMessage + "\n\t" + strbuff.toString();
        return message;
    }

    /**
     * @param
     * @return
     * @Description: 获取当前IP
     * @author: yzb
     * @date: 2020/11/22 0022 23:58
     */
    public String getIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-real-ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        //判断是否有多个IP ，防止多层代理   多层代理X-Forwarded-For：192.168.1.110， 192.168.1.120， 192.168.1.130取第一个
        if (ip != null && ip.lastIndexOf(",") != -1) {
            String[] ips = ip.split(",");
            ip = ips[0];
        }
        return ip;
    }

    /**
     * @param joinPoint
     * @param keys
     * @Description 保存正常操作日志
     * @author: zibo.yuan
     * @date: 2020/12/1
     * @return: void
     **/
    public void saveOperLogInfo(JoinPoint joinPoint, Object keys) {

        String traceID = gettraceID();
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        //从session中获取当前登录用户
        HisLogInfoCzDTO hisCzLog = new HisLogInfoCzDTO();
        try {
            // 主键ID
            hisCzLog.setId(UuidUtils.generateUuid());
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取操作
            OptionalLog opLog = method.getAnnotation(OptionalLog.class);
            if (opLog != null) {
                String czlx = opLog.czlx();
                String czms = opLog.czsm();
                // 操作类型
                hisCzLog.setCzlx(czlx);
                // 操作描述
                hisCzLog.setCzms(czms);
            }
            HttpSession session = request.getSession();
            SysUserDTO sysUserDTO = (SysUserDTO) session.getAttribute("SESSION_USER_INFO");

            String hospCode = request.getParameter("hospCode");
            if (sysUserDTO != null) {
                hospCode = sysUserDTO.getHospCode();
            }

            if (StringUtils.isEmpty(hospCode)) {
                return;
            }
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;
            // 请求方法
            hisCzLog.setCzff(methodName);
            // 请求的参数
            Map<String, String> rtnMap = converMap(request.getParameterMap());
            // 将参数所在的数组转换成json
            String params = JSON.toJSONString(rtnMap);
            // 请求参数
            hisCzLog.setQqcs(params);
            // 返回结果
            hisCzLog.setFhcs(JSON.toJSONString(keys));
            // 请求用户ID
            hisCzLog.setCzrid(sysUserDTO != null ? sysUserDTO.getUsId() : "");
            // 请求用户名称
            hisCzLog.setCzr(sysUserDTO != null ? sysUserDTO.getName() : "");
            // 请求IP
            hisCzLog.setCzip(getIP(request));
            // 请求URI
            hisCzLog.setQqlj(request.getRequestURI());
            // 创建时间
            hisCzLog.setXzsj(new Date());

            hisCzLog.setHospCode(hospCode);

            hisCzLog.setTraceId(traceID);
//            Map map = new HashMap();
//            map.put("hospCode", hisCzLog.getHospCode());
//            map.put("hisLogInfoCzDTO", hisCzLog);
//            hisLogInfoCzService_consumer.saveLogCz(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void saveErrorLog(JoinPoint proceedingJoinPoint, Throwable e) throws Throwable {

        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        // 获取切入点所在的方法
        Method method = signature.getMethod();

        String traceID = gettraceID();
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        //从session中获取当前登录用户
        HisLogInfoYcDTO hisYcLog = new HisLogInfoYcDTO();

        HttpSession session = request.getSession();
        SysUserDTO sysUserDTO = (SysUserDTO) session.getAttribute("SESSION_USER_INFO");

        String hospCode = request.getParameter("hospCode");
        if (sysUserDTO != null) {
            hospCode = sysUserDTO.getHospCode();
        }

        // 从切面织入点处通过反射机制获取织入点处的方法
        // 获取切入点所在的方法
        hisYcLog.setId(UuidUtils.generateUuid());


        // 获取请求的类名
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        // 获取请求的方法名
        String methodName = method.getName();
        methodName = className + "." + methodName;
        // 请求的参数
        Map<String, String> rtnMap = converMap(request.getParameterMap());
        // 将参数所在的数组转换成json
        String params = JSON.toJSONString(rtnMap);
        // 请求参数
        hisYcLog.setQqcs(params);
        // 请求方法名
        hisYcLog.setCzff(methodName);
        // 异常名称
        hisYcLog.setYcmc(e.getClass().getName());
        // 异常信息
        hisYcLog.setYcxx(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace()));

        hisYcLog.setCzrid(sysUserDTO != null ? sysUserDTO.getUsId() : "");
        // 请求用户名称
        hisYcLog.setCzr(sysUserDTO != null ? sysUserDTO.getName() : "");
        // 操作URI
        hisYcLog.setQqlj(request.getRequestURI());
        // 操作员IP
        hisYcLog.setCzip(getIP(request));
        // 发生异常时间
        hisYcLog.setXzsj(new Date());


        // 获取操作
        OptionalLog opLog = method.getAnnotation(OptionalLog.class);
        if (opLog != null) {
            // 操作描述
            hisYcLog.setCzms(opLog.czsm());
            // 操作类型
            hisYcLog.setCzlx(opLog.czlx());
        }


        hisYcLog.setTraceId(traceID);
        // 机构编码
        hisYcLog.setHospCode(hospCode);

        // 执行插入
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("hisLogInfoYcDTO", hisYcLog);
        hisLogInfoYcService_consumer.saveLogYc(map);
    }

    /***
     * @Description 获取远程UUID
     * @param
     * @author: zibo.yuan
     * @date: 2020/11/30
     * @return: java.lang.String
     **/
    public String gettraceID() {
        String trace_id = MDC.get("traceID");
        trace_id = trace_id.replaceAll("hsa-web", "");
        return trace_id;
    }

}
