package cn.hsa.base;

import cn.hsa.util.Constants;

import java.lang.annotation.*;

/**
 * @ClassName: OpenAdditionalService
 * @Description: 自定义开启增值服务调用中心端校验
 * @author: yuelong.chen
 * @date: 2022-07-26 14:45
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OpenAdditionalService {
    /**
     *  描述信息
     */
    String desc() default  "";
    /**
     *  如果为true，则类下面的OpenAdditionalService启作用，否则忽略
     */
    boolean addEnable() default true;
    /**
     *  填写增值服务的码表值(常量类中的ZZFW,新增服务需要在常量中补充对应码值)
     */
    String orderTypeCode() default Constants.ZZFW.DEFAULT;
}
