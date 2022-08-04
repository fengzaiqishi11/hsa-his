package cn.hsa.filter;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.RedisUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package_name: cn.hsa.filter
 * @Class_name: LoginFilter
 * @Description: 登录过滤器
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/08/10 16:55
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@WebFilter(filterName = "loginFilter", urlPatterns = "/*")
public class LoginFilter implements Filter {
    /**
     * 不过滤集合
     */
    private static final List<String> excludeList = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        excludeList.add("/web/login/authCode");
        excludeList.add("/web/login/doLogin");
        excludeList.add("/monitor/health");
        excludeList.add("/web/login/migration");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 不过滤
        if (isExclude(request)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // 获取session
            SysUserDTO userDto = (SysUserDTO)request.getSession().getAttribute("SESSION_USER_INFO");
//            SysUserDTO userDto = (SysUserDTO) session.getAttribute("SESSION_USER_INFO");
            // 判断session中是否有用户数据，如果有，则返回true，继续向下执行
            if (userDto != null) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                // {"code":99,"message":"未登录或接口访问超时，请重新登录！","type":"error"}
                WrapperResponse wr = WrapperResponse.error(99, "未登录或访问超时，请重新登录！", null);
                String retJson = JSONObject.toJSONString(wr);
                // 返回前端无权限信息
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=UTF-8");
                PrintWriter pw = response.getWriter();
                pw.write(retJson);
                pw.flush();
                pw.close();
            }
        }
    }

    /**
     * @Method 校验当前请求是否不过滤
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/8/12 14:44
     * @Return
     **/
    private boolean isExclude(HttpServletRequest request) {
        // 校验当前请求是否不过滤
        String currURI = request.getRequestURI();
        for(String exclude : excludeList) {
            if (currURI.endsWith(exclude)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}
