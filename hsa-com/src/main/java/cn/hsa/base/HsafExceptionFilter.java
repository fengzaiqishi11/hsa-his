package cn.hsa.base;

import cn.hsa.hsaf.core.log.HsafLogHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.utils.ReflectUtils;
import org.apache.dubbo.rpc.*;
import org.apache.dubbo.rpc.filter.ExceptionFilter;
import org.apache.dubbo.rpc.service.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;


/**
 * @Package_name:cn.hsa.module.sys.log.bo
 * @Class_name:HsafExceptionFilter
 * @Project_name:hsa-Com
 * @Describe: Dubbo  统一异常判断处理，针对异常无法判定指定异常返回
 * @Author: zibo.yuan
 * @Date: 2020/12/30 11:10
 * @Email: zibo.yuan@powersi.com.cn
 */
@Activate(
        group = {"provider"},
        order = 99999999
)
public class HsafExceptionFilter   implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(HsafExceptionFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result = invoker.invoke(invocation);
        isException(invoker,result);
        return result;

    }

    /***
     * @Description 判断是否存在异常信息
     * @param invoker
     * @param appResponse
     * @author: zibo.yuan
     * @date: 2020/12/30
     * @return: void
     **/
    private void isException(Invoker<?> invoker,Result appResponse ){
        if (appResponse.hasException() && GenericService.class != invoker.getInterface()) {
            Throwable exception = appResponse.getException();
//            logger.error("系统存在异常信息~");
            logger.error( RpcContext.getContext().getRemoteHost() + ". service: " + invoker.getInterface().getName() +", exception: " + exception.getClass().getName() + ": " + exception.getMessage(), exception);
            if (exception instanceof RuntimeException || !(exception instanceof Exception)) {
                setException(appResponse,exception);
            }

        }
    }

    /***
     * @Description 常见异常直接捕获
     * @param appResponse
     * @param exception
     * @author: zibo.yuan
     * @date: 2020/12/30
     * @return: void
     **/
    protected   void setException(Result appResponse,Throwable exception){
        if(exception!=null ){
            if (exception instanceof NullPointerException) {
                appResponse.setException(new RuntimeException("系统出现【空指针】异常"));
            }else if(exception instanceof IOException){
                appResponse.setException(new RuntimeException("系统出现【IO】异常"));
            }else if(exception instanceof ClassCastException){
                appResponse.setException(new RuntimeException("系统出现【类转换】异常"));
            }else if(exception instanceof SQLException){
                appResponse.setException(new RuntimeException("系统出现【SQL】异常"));
            }else if(exception instanceof IndexOutOfBoundsException){
                appResponse.setException(new RuntimeException("系统出现【数组下标越界】异常"));
            }else if(exception instanceof ClassNotFoundException){
                appResponse.setException(new RuntimeException("系统出现【类加载】异常"));
            }else if(exception instanceof OutOfMemoryError){
                appResponse.setException(new RuntimeException("系统出现【内存】异常"));
            }else if(exception instanceof FileNotFoundException){
                appResponse.setException(new RuntimeException("系统出现【文件不存在】异常"));
            }else {
                appResponse.setException(new RuntimeException(exception.getMessage()));
            }
            appResponse.getException().setStackTrace(exception.getStackTrace());
        }

    }
}
