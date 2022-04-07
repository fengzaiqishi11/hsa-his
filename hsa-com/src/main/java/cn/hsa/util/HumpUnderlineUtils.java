package cn.hsa.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName HumpUnderlineUtils
 * @Deacription JSON 驼峰，下划线互转
 * @Author caizeming
 * @Date 2021/6/11 10:40
 * @Version 3.0
 */
@Data
@Slf4j
public class HumpUnderlineUtils {

    /**
     * @Description: 驼峰实体转下划线Json对象
     * @Author: caizeming
     * @Date: 2021/6/11 10:41
     * @param object:
     * @return: java.lang.String
     */
    public static JSONObject humpToUnderline(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JSONObject jsonObject = null;
        try {
            String jsonString = mapper.writeValueAsString(object);
            jsonObject = JSON.parseObject(jsonString);
        } catch (JsonProcessingException e) {
            log.error("驼峰实体转下划线Json对象异常：",e);
        }
        return jsonObject;
    }

    /**
     * @Description: 驼峰实体集合转下划线Json对象数组
     * @Author: caizeming
     * @Date: 2021/6/11 10:41
     * @param list:
     * @return: java.lang.String
     */
    public static JSONArray humpToUnderlineArray(List<?> list) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JSONArray jsonArray = null;
        try {
            String jsonString = mapper.writeValueAsString(list);
            jsonArray = JSON.parseArray(jsonString);
        } catch (JsonProcessingException e) {
            log.error("驼峰实体集合转下划线Json对象数组异常：",e);
        }
        return jsonArray;
    }

    /**
     * @Description: 下划线json对象转驼峰实体
     * @Author: caizeming
     * @Date: 2021/6/11 11:10
     * @param jsonObject:
     * @param valueType:
     * @return: java.lang.Object
     */
    public static <T> T underlineToHump(JSONObject jsonObject, Class<T> valueType) {
        if(jsonObject==null){
            return null;
        }
        ObjectMapper json = new ObjectMapper();
        json.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        json.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        T t = null;
        try {
            String params = json.writeValueAsString(jsonObject);
            t = json.readValue(params, valueType);
        } catch (Exception e) {
            log.error("下划线json对象转驼峰实体异常：",e);
        }
        return t;
    }

    /**
     * @Description: 下划线json数组对象转驼峰实体集合
     * @Author: caizeming
     * @Date: 2021/6/11 11:10
     * @param :
     * @param valueType:
     * @return: java.lang.Object
     */
    public static <T> List<T> underlineToHumpArray(JSONArray jsonArray, Class<T> valueType) {
        if(jsonArray==null||jsonArray.size()==0){
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, valueType);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<T> t = null;
        try {
            String params = objectMapper.writeValueAsString(jsonArray);
            t = objectMapper.readValue(params, javaType);
        } catch (Exception e) {
            log.error("下划线json数组对象转驼峰实体集合异常：",e);
        }
        return t;
    }

}
