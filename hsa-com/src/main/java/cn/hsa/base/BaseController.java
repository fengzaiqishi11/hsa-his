package cn.hsa.base;

import cn.hsa.hsaf.core.framework.HsafController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.MapUtils;
import cn.hsa.util.RedisUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.Resource;
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
public class BaseController extends HsafController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    // 会话验证码key
    public static final String SESSION_AUTH_CODE = "SESSION_AUTH_CODE";
    // 会话用户信息key
    public static final String SESSION_USER_INFO = "SESSION_USER_INFO";
    @Resource
    private RedisUtils redisUtils;
    /** Session缓存操作 **/
    @Resource
    private RedisOperationsSessionRepository redisSessionRepository;

   /* *//**
     * 当前登录用户所属医院编码
     *//*
    protected String hospCode = "1000001";
    *//**
     * 当前登录用户所属医院名称
     *//*
    protected String hospName;
    *//**
     * 医院级别
     *//*
    protected String levelCode;
    *//**
     * 当前登录用户id
     *//*
    protected String userId ="1290816158941450240";
    *//**
     * 当前登录用户名称
     *//*
    protected String userName;
    *//**
     * 当前登录用户所属科室id
     *//*
    protected String deptId;
    *//**
     * 当前登录用户所属科室名称
     *//*
    protected String deptName;
    *//**
     * 当前登录用户所属科室科室性质
     *//*
    protected String deptType;
    *//**
     * 当前登录用户操作科室ID
     *//*
    protected String loginDeptId = "1295734346764480512";
    *//**
     * 当前登录用户操作科室名称
     *//*
    protected String loginDeptName;
    *//**
     * 当前登录用户操作科室科室性质
     *//*
    protected String loginDeptType;
    *//**
     * 当前登录用户操作科室科室类别标识（中药、西药、材料等）
     *//*
    protected String loginTypeIdentity;
    *//**
     * 存储系统编码
     *//*
    protected String systemCode;

    *//**
     * 请求域
     *//*
    protected HttpServletRequest request;
    *//**
     * 响应域
     *//*
    protected HttpServletResponse response;
    *//**
     * 请求会话
     *//*
    protected HttpSession session;*/

    public <T> T getSession(HttpServletRequest req, HttpServletResponse res) {
        Session session = redisSessionRepository.findById(req.getRequestedSessionId());
        SysUserDTO value = session.getAttribute("SESSION_USER_INFO");
        return (T)(value == null ? null : value);
    }

    protected void setSession(String name, Object value, int s, HttpServletRequest req, HttpServletResponse res) {
        req.getSession().setAttribute(name, value);
        req.getSession().setMaxInactiveInterval(s);
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


    protected <T> T getAndRemoveSession(HttpServletRequest req, HttpServletResponse res) {
       Session sesion = redisSessionRepository.findById(req.getRequestedSessionId()) ;
        Object value = sesion.getAttribute(SESSION_AUTH_CODE);
        if (value != null) {
            sesion.removeAttribute(SESSION_AUTH_CODE);
        }
        return (T)value;
    }

    protected String getParameterError(HttpServletRequest req, String name, String errMsg) {
        String param = req.getParameter(name);
        if (StringUtils.isEmpty(param)) {
            throw new RuntimeException(errMsg);
        }

        return param;
    }

    /**
     * 获取访问的ip地址
     *
     * @return String
     */
    public String getIP(HttpServletRequest req, HttpServletResponse res) {
        //20160901 lingang 代理的情况下获取真实IP begin
        String ip = req.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("X-real-ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getRemoteAddr();
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
     *
     *
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
            else  if (errMsg.contains("BadSqlGrammarException") && errMsg.contains("MySQLSyntaxErrorException")) {
                return WrapperResponse.error(-1, "访问数据库异常：请检查SQL是否正常", null);
            }else {
                return WrapperResponse.error(-1, errMsg, null);
            }


            // 转换AppExcetion自定义异常 yzb 2020-12-30日志统一拦截处理,后续不做日志拦截,此处注释
//            else if (errMsg.contains("AppException")) {
//                errMsg = errMsg.split("\n")[0];
//                int index = errMsg.indexOf("AppException: ") + "AppException: ".length();
//                return WrapperResponse.error(-1, errMsg.substring(index).trim(), null);
//            }else {
//                errMsg = errMsg.split("\n")[0];
//                int index = errMsg.indexOf("Exception: ") + "Exception: ".length();
//                return WrapperResponse.error(-1, errMsg.substring(index).trim(), null);
//            }
        }

        return WrapperResponse.error(-1, ex.getMessage(), null);
    }


    /***
     * @Description 重新获取session
     * @param request
     * @author: zibo.yuan
     * @date: 2020/12/1
     * @return: javax.servlet.http.HttpSession
     **/
    protected HttpSession getSessionManager(HttpServletRequest request) {
        return (HttpSession) request.getSession();
    }
}
