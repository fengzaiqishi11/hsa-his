package cn.hsa.filter;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  前端请求参数过滤器
 * @Author: luonianxin
 * @Date: 2021-10-25
 */

@Slf4j
public class RequestParamFilter  extends OncePerRequestFilter {

    /** 请求白名单集合 **/
    private static final List<String> excludeList = new ArrayList<>();

    static final String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
            + "(\\b(select|update|and|or|delete|insert|trancate|char|like|show|table|database|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";

    public RequestParamFilter(){
        super();
        excludeList.add("/web/login/authCode");
        excludeList.add("/web/login/doLogin");
    }
    /**
     *    参数匹配器,忽略大小写
     */
    static Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);



    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request  httpServletRequest请求(中间可转化)
     * @param response  HttpServletResponse响应(中间可转化)
     * @param filterChain 过滤器链
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isParameterValid(request)) {
            filterChain.doFilter(request, response);
        } else {
            WrapperResponse wr = WrapperResponse.error(1403, "请求参数包含非法字符,请检查!", null);
            String retJson = JSONObject.toJSONString(wr);
            // 请求参数包含非法字符
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter pw = response.getWriter();
            pw.write(retJson);
            pw.flush();
            pw.close();
        }
    }



    /***
     *  校验请求参数是否合法
     * @param request HttpServletRequest
     * @return 校验匹配结果
     */
    private boolean isParameterValid(HttpServletRequest request){
        String bodyParams = getRequestBodyParam(request);
        if(bodyParams != null) {
            String bodyParamsString = bodyParams.toString();
            Matcher matcher = sqlPattern.matcher(bodyParamsString);
            if (matcher.find()) {
                log.error("maybe an attack request-请求参数{} 存在非法字符，请确认：{}。请求IP：{}",bodyParamsString,matcher.group(), getRemoteIP(request));
                return false;
            }
        }
        Enumeration<String> params =  request.getParameterNames();
        while(params.hasMoreElements()){
            String paramName = params.nextElement();
            String paramValue = request.getParameter(paramName);
            Matcher matcher1 = sqlPattern.matcher(paramValue);
            if(matcher1.find()){
                log.error("maybe an attack request-请求参数{} 存在非法字符，请确认：{}。请求IP：{}",paramValue,matcher1.group(), getRemoteIP(request));
                return false;
            }
        }

        return true;
    }


    private String getRequestBodyParam(HttpServletRequest servletRequest){
        String params = null;
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(servletRequest.getInputStream(), StandardCharsets.UTF_8));
            String inptStr = null;
            StringBuilder sb = new StringBuilder();

            while((inptStr = reader.readLine()) != null)
                sb.append(inptStr);
            params = JSON.toJSONString(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return params;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String currURI = request.getRequestURI();
        for(String exclude : excludeList) {
            if (currURI.endsWith(exclude)) {
                return true;
            }
        }
        return super.shouldNotFilter(request);
    }

    public String getRemoteIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }
}
