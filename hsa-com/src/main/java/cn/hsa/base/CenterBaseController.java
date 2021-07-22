package cn.hsa.base;

import cn.hsa.hsaf.core.framework.HsafController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.user.dto.CenterUserDTO;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @公司 创智和宇信息技术股份有限公司 Copyright (c) 2006-2020,All Rights Reserved.
 * @描叙 公共baseController
 * @创建者 zengfeng
 * @修改者 zengfeng
 * @版本 V1.0
 * @日期 2020/7/13  19:04
 */
@Controller
@Slf4j
public class CenterBaseController extends HsafController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    // 会话验证码key
    public static final String SESSION_AUTH_CODE = "CENTER_SESSION_AUTH_CODE";
    // 会话用户信息key
    public static final String SESSION_USER_INFO = "CENTER_SESSION_USER_INFO";

    /**
     * 当前登录用户id
     */
    protected String userId ;
    /**
     * 当前登录用户名称
     */
    protected String userName ;
    /**
     * 请求域
     */
    protected HttpServletRequest request;
    /**
     * 响应域
     */
    protected HttpServletResponse response;
    /**
     * 请求会话
     */
    protected HttpSession session;

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest req, HttpServletResponse res) {
        this.request = req;
        this.response = res;
        this.session = req.getSession();
        CenterUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        if (sysUserDTO != null) {
            this.userId = sysUserDTO.getCode();
            this.userName = sysUserDTO.getName();
        }
    }

    protected Map getAllParameters() {
        return request.getParameterMap();
    }

    protected String getParameter(String name) {
        return request.getParameter(name);
    }

    protected <E> E getParameter(String name, E defaultValue) {
        String value = getParameter(name);
        return (E)(value == null ? defaultValue : value);
    }

    protected String[] getParameterValues(String name) {
        return request.getParameterValues(name);
    }

    protected void setAttribute(String name, Object value) {
        request.setAttribute(name, value);
    }

    protected void setAttribute(Map<String, Object> map) {
        if (!MapUtils.isEmpty(map)) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                request.setAttribute(entry.getKey(), entry.getValue());
            }
        }
    }

    protected <T> T getAttribute(String name) {
        return (T)this.getAttribute(name);
    }

    protected <E> E getAttribute(String name, E defaultValue) {
        Object value = this.request.getAttribute(name);
        return (E)(value == null ? defaultValue : value);
    }

    protected <T> T getSession(String name) {
        return (T)this.getSession(name, (Object) null);
    }

    protected <T> T getSession(String name, Object defaultValue) {
        Object value = session.getAttribute(name);
        return (T)(value == null ? defaultValue : value);
    }

    protected <T> T getAndRemoveSession(String name) {
        Object value = session.getAttribute(name);
        if (value != null) {
            removeSession(name);
        }
        return (T)value;
    }

    protected <E> E getAndRemoveSession(String name, E defaultValue) {
        Object value = getAndRemoveSession(name);
        return (E)(value == null ? defaultValue : value);
    }

    protected void setSession(String name, Object value) {
        session.setAttribute(name, value);
    }

    protected void setSession(String name, Object value, int s) {
        session.setAttribute(name, value);
        session.setMaxInactiveInterval(s);
    }

    protected void removeSession(String name) {
        session.removeAttribute(name);
    }

    /**
     * 获取访问的ip地址
     *
     * @return String
     */
    public String getIP() {
        //20160901 lingang 代理的情况下获取真实IP begin
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
        // end

        return ip;
    }

    /**
     * 基于@ExceptionHandler异常处理
     */
    @ExceptionHandler
    public WrapperResponse exp(Exception ex) {
        if (ex != null) {
            log.error(ex.getMessage(), ex);
        }

        // 访问不到RPC服务：
        // 1、请检查模块是否启动
        // 2、生产者/消费者是否配置
        // 3、nacos是否启动
        if (ex instanceof RpcException) {
            return WrapperResponse.error(-1, "远程服务访问失败：请检查服务是否正常", null);
        }

        // 运行时异常
        if (ex instanceof RuntimeException) {
            String errMsg = ex.getMessage();

            // 访问数据库超时
            // 1、网络是否不稳定
            // 2、数据库服务是否正常
            if (errMsg.contains("CommunicationsException") && errMsg.contains("Communications link failure")) {
                return WrapperResponse.error(-1, "访问数据库超时：请检查服务是否正常", null);
            }

            // SQL错误
            if (errMsg.contains("BadSqlGrammarException") && errMsg.contains("MySQLSyntaxErrorException")) {
                return WrapperResponse.error(-1, "访问数据库异常：请检查SQL是否正常", null);
            }

            // 转换AppExcetion自定义异常
            if (errMsg.contains("AppException")) {
                errMsg = errMsg.split("\n")[0];
                int index = errMsg.indexOf("AppException: ") + "AppException: ".length();
                return WrapperResponse.error(-1, errMsg.substring(index).trim(), null);
            }
        }

        return WrapperResponse.error(-1, ex.getMessage(), null);
    }

    /**
     * @Method 校验并获取返回数据
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/8/5 18:41
     * @Return
     **/
    protected <T> T getData(WrapperResponse wr) {
        if (wr.getCode() != 0) {
            throw new AppException(wr.getMessage());
        }
        return (T)wr.getData();
    }
}
