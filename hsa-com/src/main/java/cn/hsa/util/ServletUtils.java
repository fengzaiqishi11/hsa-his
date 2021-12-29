package cn.hsa.util;

import cn.hutool.extra.servlet.ServletUtil;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Servlet 工具类.
 *
 */
public class ServletUtils {

    private ServletUtils() {
    }

    /**
     * Gets current http servlet request.
     *  获取当前Servlet请求
     * @return an optional http servlet request
     */
    @NonNull
    public static Optional<HttpServletRequest> getCurrentRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(requestAttributes -> requestAttributes instanceof ServletRequestAttributes)
                .map(requestAttributes -> (ServletRequestAttributes) requestAttributes)
                .map(ServletRequestAttributes::getRequest);
    }

    /**
     * Gets request ip.
     *  返回当前请求ip地址
     * @return ip address or null
     */
    @Nullable
    public static String getRequestIp() {
        return getCurrentRequest().map(ServletUtil::getClientIP).orElse(null);
    }


}
