package cn.hsa.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 *  重写ServletHttpRequet请求对象用以确保 getInputStream()
 *  调用后无法重复使用的问题，注意@Order的值必须比
 *  {@link org.springframework.session.web.http.SessionRepositoryFilter}
 *  的@Order的值要小,确保优先于Spring-Session过滤器执行(Order的值越小优先级越高)
 * @Author: luonianxin
 * @Date: 2021-10-25
 */
@Configuration
@Slf4j
@Order(Integer.MIN_VALUE + 49)
public class BodyReaderRequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        BodyReaderRequestWrapper requestWrapper  = new BodyReaderRequestWrapper(httpServletRequest);
        chain.doFilter(requestWrapper,response);
    }

    @Override
    public void destroy() {

    }

    /***
     *  一个重写了getInputSteam()方法的包装类用于
     *  解决获取InptStream后,Stream流失效导致
     *  Spring MVC解析@RequestBody对象报错
     *  参数体丢失的问题
     * @Author: luonianxin
     * @Date: 2021-10-25
     */
    private class BodyReaderRequestWrapper extends HttpServletRequestWrapper {
        private final String body;

        /**
         * 包装原始http请求对象, 注意读取流时使用的字符集要明确指定，否则就会出现中文乱码
         * @param request HttpServletRequest请求
         */
        public BodyReaderRequestWrapper(HttpServletRequest request) throws IOException{
            super(request);
            StringBuilder sb = new StringBuilder();
            InputStream ins = request.getInputStream();
            BufferedReader isr = null;
            try{
                if(ins != null){
                    isr = new BufferedReader(new InputStreamReader(ins, StandardCharsets.UTF_8));
                    char[] charBuffer = new char[128];
                    int readCount = 0;
                    while((readCount = isr.read(charBuffer)) != -1){
                        sb.append(charBuffer,0,readCount);
                    }
                }
            }catch (IOException e){
                throw e;
            }finally {
                if(isr != null) {
                    isr.close();
                }
            }
            body = sb.toString();
            log.info("==requestBody==转换后内容为："+body);
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(this.getInputStream(),StandardCharsets.UTF_8));
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            final ByteArrayInputStream byteArrayIns = new ByteArrayInputStream(body.getBytes());
            ServletInputStream servletIns = new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {

                }

                @Override
                public int read() throws IOException {
                    return byteArrayIns.read();
                }
            };
            return  servletIns;
        }
    }

}
