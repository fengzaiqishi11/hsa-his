package cn.hsa.base;

import cn.hsa.hsaf.core.framework.context.HsafContextHolder;
import cn.hsa.hsaf.core.framework.util.CurrentUser;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.log.LogContext;
import cn.hsa.hsaf.core.log.LogInfo;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.rpc.RpcContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.UUID;

/***
 * @Description 全链路日志记录，重写
 * @author: zibo.yuan
 * @date: 2020/12/1
 **/
public class HsafLogHandlers {
    private static final Logger logger = LoggerFactory.getLogger(HsafLogHandlers.class);
    private static final String CALL_FROM_HSAF_REST_PATH_CONTROLLER = "HsafRestPathController";
    private static final String CALL_FROM_CONTROLLER = "controller";
    private static final String CALL_FROM_RPC = "rpc";
    private static final String LOG_CONTEXT_KEY = "_logContext";
    private static final String MULTIPART_FORM_DATA = "multipart/form-data";
    @Value("${spring.application.name:}")
    private String applicationName;
    private static ThreadLocal<LogInfo> logContextHolder = new ThreadLocal();

    public HsafLogHandlers() {
    }

    private static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    private String generateTraceID() {
        UUID uuid = UUID.randomUUID();
        if (this.applicationName != null && this.applicationName.length() > 10) {
            this.applicationName = this.applicationName.substring(0, 10);
        }

        return this.applicationName + "-" + uuid;
    }

    private void packTraceInfo(LogContext logContext, String url, String ClassName) {
        logContext.setAppSign(ClassName == null ? "L" : (ClassName.startsWith("L_") ? "L" : "N"));
    }

    private static void packHttpLogInfo(LogInfo logInfo, JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null && attributes.getRequest() != null) {
            HttpServletRequest request = attributes.getRequest();
            logInfo.setRequestUrl(request.getRequestURL().toString());
            logInfo.setRequestUri(request.getRequestURI());
            logInfo.setClientIP(getIpAddr(request));
            logInfo.setRequestMethod(request.getMethod());
            logInfo.setClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            logInfo.setClazz(joinPoint.getSignature().getDeclaringType().getSimpleName());
            if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
                String requestParams = "";
                logger.debug("request.getMethod()=" + request.getMethod() + ";request.getContentType()=" + request.getContentType());
                if ("GET".equalsIgnoreCase(request.getMethod())) {
                    requestParams = Arrays.toString(joinPoint.getArgs());
                } else {
                    try {
                        if (request.getContentType() == null || request.getContentType().indexOf("multipart/form-data") == -1) {
                            requestParams = JSONObject.toJSONString(joinPoint.getArgs()[0]);
                        }
                    } catch (Exception var6) {
                        requestParams = "";
                    }
                }

                logInfo.setRequestParams(requestParams);
            }

        }
    }

    private static void packRpcLogInfo(LogInfo logInfo, JoinPoint joinPoint) {
        logInfo.setRequestUrl("");
        logInfo.setClientIP("");
        logInfo.setRequestMethod("rpc");
        logInfo.setClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logInfo.setClazz(joinPoint.getSignature().getDeclaringType().getSimpleName());
        if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
            String requestParams = Arrays.toString(joinPoint.getArgs());
            logInfo.setRequestParams(requestParams);
        }

    }

    private LogContext generateLogContext(LogInfo logInfo) {
        LogContext logContext = new LogContext();
        logContext.setTraceID(logInfo.getTraceID());
        this.packTraceInfo(logContext, logInfo.getRequestUri(), logInfo.getClazz());
        return logContext;
    }

    private void doBefore(LogInfo logInfo) {
        logger.debug("====[request data]====");
        logger.debug(logInfo.toString());
        logger.debug(logInfo.getLogString());
        logContextHolder.set(logInfo);
        HsafContextHolder.getContext().addProperty("_logContext", this.generateLogContext(logInfo));
    }

    public void doControllerBefore(JoinPoint joinPoint) {
        logger.debug("HsafLogHandler.doControllerBefore");

        try {
            LogInfo logInfo = new LogInfo();
            // 唯一码
            String trace_id = gettraceID();

            LogContext logContext = (LogContext) HsafContextHolder.getContext().getProperty("_logContext");
            if (logContext == null && trace_id == null) {
                logInfo.setTraceID(this.generateTraceID());
            } else {
                logInfo.setTraceID(trace_id);
            }

            MDC.put("traceID", logInfo.getTraceID());
            CurrentUser curUser = HsafContextHolder.getContext().getCurrentUser();
            logInfo.setUserAcctId(curUser.getUserAcctID());
            logInfo.setRequestTime((new SimpleDateFormat("yyyyMMddHHmmssSSS")).format(Calendar.getInstance().getTime()));
            packHttpLogInfo(logInfo, joinPoint);
            logInfo.setCallFrom("controller");
            this.doBefore(logInfo);
        } catch (Exception var5) {
            logger.error("HsafLogHandler.doControllerBefore.exception", var5);
        }

    }

    private LogInfo getLogInfo4Service() {
        LogInfo logInfo = (LogInfo) logContextHolder.get();
        if (logInfo != null) {
            return logInfo;
        } else {
            LogContext logContext = (LogContext) HsafContextHolder.getContext().getProperty("_logContext");
            String trace_id = gettraceID();

            if (logContext != null) {
                logInfo = new LogInfo();
                logInfo.setTraceID(trace_id);
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if (attributes != null) {
                    logInfo.setCallFrom("HsafRestPathController");
                } else {
                    logInfo.setCallFrom("rpc");
                }
            }

            if (logInfo == null) {
                logInfo = new LogInfo();
                if (StringUtils.isEmpty(trace_id)) {
                    logInfo.setTraceID(this.generateTraceID());
                } else {
                    logInfo.setTraceID(trace_id);
                }
                logInfo.setCallFrom("HsafRestPathController");
            }

            return logInfo;
        }
    }

    public void doServiceBefore(JoinPoint joinPoint) {
        logger.debug("HsafLogHandler.doServiceBefore");

        try {
            LogInfo logInfo = this.getLogInfo4Service();
            String callFrom = logInfo.getCallFrom();
            logger.debug("callFrom:" + callFrom);
            if ("controller".equals(callFrom)) {
                return;
            }
            String trace_id = gettraceID();

            if ("rpc".equals(callFrom)) {
                MDC.put("traceID", trace_id);
                logInfo.setRequestTime((new SimpleDateFormat("yyyyMMddHHmmssSSS")).format(Calendar.getInstance().getTime()));
                packRpcLogInfo(logInfo, joinPoint);
            } else if ("HsafRestPathController".equals(callFrom)) {
                MDC.put("traceID", trace_id);
                logInfo.setRequestTime((new SimpleDateFormat("yyyyMMddHHmmssSSS")).format(Calendar.getInstance().getTime()));
                packHttpLogInfo(logInfo, joinPoint);
            }

            CurrentUser curUser = HsafContextHolder.getContext().getCurrentUser();
            logInfo.setUserAcctId(curUser.getUserAcctID());
            this.doBefore(logInfo);
        } catch (Exception var5) {
            logger.error("HsafLogHandler.doServiceBefore.exception", var5);
        }

    }

    private static void doAfterReturning(Object ret, LogInfo logInfo) {
        if (ret != null && ret.getClass().isAssignableFrom(WrapperResponse.class)) {
            logInfo.setCode(((WrapperResponse) ret).getCode());
            logger.debug(JSONObject.toJSONString(ret));
        }

        logger.debug("====[response data]====");
        logger.info(logInfo.getLogString());
        MDC.remove("traceID");
    }

    public void doControllerAfterReturning(Object ret) {
        logger.debug("HsafLogHandler.doControllerAfterReturning");

        try {
            LogInfo logInfo = (LogInfo) logContextHolder.get();
            if (logInfo == null) {
                return;
            }

            doAfterReturning(ret, logInfo);
        } catch (Exception var3) {
            logger.error("HsafLogHandler.doControllerAfterReturning.exception", var3);
        }

    }

    public void doServiceAfterReturning(Object ret) {
        logger.debug("HsafLogHandler.doServiceAfterReturning");

        try {
            LogInfo logInfo = (LogInfo) logContextHolder.get();
            if (logInfo == null) {
                return;
            }

            if ("controller".equals(logInfo.getCallFrom())) {
                return;
            }

            doAfterReturning(ret, logInfo);
        } catch (Exception var3) {
            logger.error("HsafLogHandler.doServiceAfterReturning.exception", var3);
        }

    }

    private static void doAround(long startTime, LogInfo logInfo) throws Throwable {
        long timeConsuming = System.currentTimeMillis() - startTime;
        logger.debug("[timeConsuming:] " + timeConsuming);
        logInfo.setTimeConsuming(timeConsuming);
    }

    public Object doControllerAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.debug("HsafLogHandler.doControllerAround");
        long startTime = System.currentTimeMillis();
        Object object = joinPoint.proceed();

        try {
            LogInfo logInfo = (LogInfo) logContextHolder.get();
            if (logInfo == null) {
                return object;
            }

            doAround(startTime, logInfo);
        } catch (Exception var6) {
            logger.error("HsafLogHandler.doControllerAround.exception", var6);
        }

        return object;
    }

    public Object doServiceAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.debug("HsafLogHandler.doServiceAround");
        long startTime = System.currentTimeMillis();
        Object object = joinPoint.proceed();

        try {
            LogInfo logInfo = (LogInfo) logContextHolder.get();
            if (logInfo == null) {
                return object;
            }

            if ("controller".equals(logInfo.getCallFrom())) {
                return object;
            }

            doAround(startTime, logInfo);
        } catch (Exception var6) {
            logger.error("HsafLogHandler.doServiceAround.exception", var6);
        }

        return object;
    }

    /***
     * @Description 获取远程UUID
     * @param
     * @author: zibo.yuan
     * @date: 2020/11/30
     * @return: java.lang.String
     **/
    public String gettraceID() {
        String trace_id = RpcContext.getContext().getAttachment("trace_id");
        if (StringUtils.isEmpty(trace_id)) {
            trace_id = generateTraceID();
        } else {
            trace_id = trace_id.replaceAll("hsa-web", applicationName);
        }
        return trace_id;
    }
}
