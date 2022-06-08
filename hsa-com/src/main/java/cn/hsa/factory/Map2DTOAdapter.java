package cn.hsa.factory;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.Map;

/**
 * @author luonianxin
 * @version 1.0
 * @date 2022/5/18 15:47
 */
public class Map2DTOAdapter {

        /**
         *  将一个json字符串转换为InsureItemDTO对象
         * @param strJson 接收到的InsureItemDTO的 json字符串
         * @param link  键值之间的对应关系
         * @return  cn.hsa.module.insure.module.dto.InsureItemDTO
         * @throws NoSuchMethodException
         * @throws InvocationTargetException
         * @throws IllegalAccessException
         */
        public static <T>  T filter(String strJson, Map<String, String> link,Class<T> classFilterd) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {

            return filter(JSON.parseObject(strJson, Map.class), link,classFilterd);
        }

        /**
         *  将一个json字符串转换为InsureItemDTO对象
         * @param srcObj 原始map对象
         * @param link  键值之间的对应关系
         * @return  cn.hsa.module.insure.module.dto.InsureItemDTO
         * @throws NoSuchMethodException
         * @throws InvocationTargetException
         * @throws IllegalAccessException
         */
        @SuppressWarnings("unchecked")
        public static <T>  T filter(Map srcObj, Map<String,String> link,Class<T> classFilterd) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
            // 创建待填充的对象
            T obj = (T)classFilterd.newInstance();
            for(String key : link.keySet()){
                Object val = srcObj.get(link.get(key));
                obj.getClass().getMethod("set"+key.substring(0,1).toUpperCase(Locale.ENGLISH)+key.substring(1),String.class).invoke(obj,val.toString());
            }
            return (T) obj;
        }
}


