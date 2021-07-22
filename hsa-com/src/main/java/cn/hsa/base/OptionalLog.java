/**
 * Copyright ? 2018 创智和宇. All rights reserved.
 *
 * @Title: OptionalLog
 * @Package: com.powersi.util
 * @Description: TODO
 * @author: yzb
 * @date: 2020/11/22 0022 22:28
 * @version: V1.0
 */

package cn.hsa.base;

import java.lang.annotation.*;

/**
 * @ClassName: OptionalLog
 * @Description: <p>Description:自定义操作日志标签，模块名和方法名 </p>
 * @author: yzb
 * @date: 2020/11/22 0022 22:28
 */

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OptionalLog {
    String czlx()  default "";
    String czsm()  default "";
}
