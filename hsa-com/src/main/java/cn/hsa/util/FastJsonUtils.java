
package cn.hsa.util;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.cglib.beans.BeanMap;

import java.util.*;

/**
 * FastJson工具类，为了在代码中序列化和反序列化时应用JSONField注解
 * @author
 * @date 2020-08-20 15:30
 */
public class FastJsonUtils {

    /**
     * 减少new SerializeConfig的性能开销
     */
    private static final SerializeConfig SNAKE_CONFIG = new SerializeConfig();
    /**
     * 减少new SerializeConfig的性能开销
     */
    private static final SerializeConfig AUTO_TIME_CONFIG = new SerializeConfig();

    //初始化SerializeConfig
    static {
        //设置转出为蛇底
        SNAKE_CONFIG.setPropertyNamingStrategy(PropertyNamingStrategy.SnakeCase);
    }

    /**
     * 构造方法私有化
     */
    private FastJsonUtils() {

    }

    /**
     * JavaBean序列化为json字符串，默认不忽略null值
     *
     * @param object JavaBean
     * @return json字符串
     * @author 医保产品二部-罗大可
     * @date 2020-08-17 17:02
     */
    public static Map<String, Object> beanToMap(Object object) {
        //JavaBean序列化为json字符串
        return JSONUtil.toBean(toJson(object), Map.class);
    }

    /**
     * @Author gory
     * @Description bean 转map
     * @Date 2022/7/20 9:09
     * @Param [bean]
     **/
    public static Map<String, Object> newBeanToMap(Object bean) {
        BeanMap beanMap = BeanMap.create(bean);
        Map<String, Object> map = new HashMap<>();

        beanMap.forEach((key, value) -> {
            map.put(String.valueOf(key), value);
        });
        return map;
    }
    /**
     * json字符串反序列化为JavaBean
     *
     * @param jsonStr json字符串
     * @param clazz   指定的类型
     * @return T 指定类型的对象
     * @author 医保产品二部-罗大可
     * @date 2020-08-17 17:02
     */
    public static <T> T fromJson(String jsonStr, Class<T> clazz) {
        //json字符串反序列化为JavaBean
        return JSON.parseObject(jsonStr, clazz);
    }

    /**
     * JavaBean序列化为json字符串，默认不忽略null值
     *
     * @param object JavaBean
     * @return json字符串
     * @author 医保产品二部-罗大可
     * @date 2020-08-17 17:02
     */
    public static String toJson(Object object) {
        //JavaBean序列化为json字符串
        return JSON.toJSONString(object, SNAKE_CONFIG,
                SerializerFeature.WriteBigDecimalAsPlain, SerializerFeature.SortField, SerializerFeature.WriteMapNullValue);
    }

    /**
     * @return java.util.List<T>
     * @Author 医保二部-胡宇全
     * @Date 2020/9/10 10:08
     * @Description 将Json数组转换为Java数组 [jsonStr, clazz]
     */
    public static <T> List<T> fromJsonArray(String jsonStr, Class<T> clazz) {
        return JSON.parseArray(jsonStr, clazz);
    }

    /**
     * JavaBean序列化为json字符串，忽略null值
     *
     * @param object JavaBean
     * @return json字符串
     * @author 医保产品二部-罗大可
     * @date 2020-08-17 17:02
     */
    public static String toJsonNonNull(Object object) {
        //JavaBean序列化为json字符串
        return JSON.toJSONString(object, SNAKE_CONFIG,
                SerializerFeature.WriteBigDecimalAsPlain, SerializerFeature.SortField);
    }

    /**
     * JavaBean序列化为json字符串，自动格式化时间和日期<br/>
     * 日期格式化为"yyyy-MM-dd"<br/>
     * 时间格式化为"yyyy-MM-dd HH:mm:ss"
     *
     * @param object JavaBean
     * @return json字符串
     * @author 医保产品二部-罗大可
     * @date 2020-08-17 17:02
     */
    public static String toJsonRedis(Object object) {
        //JavaBean序列化为json字符串
        return JSON.toJSONString(object, AUTO_TIME_CONFIG, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteBigDecimalAsPlain, SerializerFeature.SortField);
    }

    /**
     * JavaBean序列化为json字符串，忽略null值字段，并自动格式化时间和日期<br/>
     * 日期格式化为"yyyy-MM-dd"<br/>
     * 时间格式化为"yyyy-MM-dd HH:mm:ss"<br/>
     *
     * @param object JavaBean
     * @return json字符串
     * @author 医保产品二部-罗大可
     * @date 2020-08-17 17:02
     */
    public static String toJsonRedisNonNull(Object object) {
        //JavaBean序列化为json字符串
        return JSON.toJSONString(object, AUTO_TIME_CONFIG, SerializerFeature.SortField,
                SerializerFeature.WriteBigDecimalAsPlain, SerializerFeature.SortField);
    }
}
