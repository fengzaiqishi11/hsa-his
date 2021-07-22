package cn.hsa.base;

import java.lang.annotation.*;

/**
 * @Package_ame: cn.hsa.base
 * @Class_name: hsa-his
 * @Description: 防止重复提交
 * @Author: zengfeng
 * @Email: 954123283@qq.com
 * @Date: 2021/1/18  17:31
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoRepeatSubmit {
}
