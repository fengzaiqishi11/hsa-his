package cn.hsa.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Package_ame: cn.hsa.util
 * @Class_name: MapUtils
 * @Description: map集合工具类
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/7/1 21:54
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public class MapUtils {
    /**
     * @Method Map集合是否为空
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 17:32
     * @Return
     **/
    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    /**
     * @Method 获取指定索引数据
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 17:29
     * @Return
     **/
    public static <T> T get(Map map, Object key) {
        return (T) map.get(key);
    }

    /**
     * @Method 获取指定索引数据，无数据设置默认值
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 17:29
     * @Return
     **/
    public static <E> E get(Map map, Object key, E defaultValue) {
        Object o = get(map, key);
        if (o == null) {
            return defaultValue;
        }
        return (E) o;
    }


    /**
     * @Method 获取map中数据，如果没有key，抛出异常
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/12/19 20:34
     * @Return
     **/
    public static <T> T getEmptyErr(Map map, Object key, String errMsg) {
        if (!map.containsKey(key)) {
            throw new RuntimeException(errMsg);
        }
        return (T) map.get(key);
    }

    /**
     * @Method 删除集合中指定Key数据
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 17:32
     * @Return
     **/
    public static void remove(Map map, Object key) {
        map.remove(key);
    }

    /**
     * @Method 删除集合中指定Key数据
     * @Description
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/7/27 17:32
     * @Return
     **/
    public static void removeList(Map map, List list) {
        for (Object key : list) {
            map.remove(key);
        }
    }

    /**
     * @Method isEmptyErr
     * @Desrciption 判断map中字段是否存在
     @params [map, keys]
      * @Author chenjun
     * @Date   2020/9/9 8:56
     * @Return void
     **/
    public static void isEmptyErr(Map map, String... keys) {
        if (isEmpty(map) || isEmpty(keys)) {
            throw new RuntimeException("必填参数为空");
        }

        for (int i = 0; i < keys.length; i++ ) {
            isEmptyErr(map, keys[i]);
        }
    }

    public static void isEmptyErr(Map map, String key) {
        if (isEmpty(map) || isEmpty(key)) {
            throw new RuntimeException("必填参数为空");
        }

        isEmptyErr(map.get(key), "The param " + key + " is null or empty!");
    }

    public static <T> T isEmptyErr(Object obj, String errMsg) {
        if (isEmpty(obj)) {
            throw new RuntimeException(errMsg);
        }

        return (T)obj;
    }

    public static boolean isEmpty(Map map, String key) {
        Object obj = map.get(key);
        return isEmpty(obj);
    }

    public static <T> T isEmpty(Object obj, Object defaultValue) {
        if (isEmpty(obj))
            return (T)defaultValue;

        return (T)obj;
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null)
            return true;

        if (obj instanceof String) {
            String str = (String) obj;
            return str == null || str.length() == 0;
        } else if (obj instanceof List) {
            List list = (List) obj;
            return list == null || list.isEmpty();
        } else if (obj instanceof Map) {
            Map map = (Map) obj;
            return map == null || map.isEmpty();
        } else if (obj instanceof BigDecimal) {
            BigDecimal bd = (BigDecimal)obj;
            return bd == null;
        } else if (obj instanceof Long) {
            Long bd = (Long)obj;
            return bd == null;
        }

        return obj == null;
    }


    public static Integer getVI(Map map, String key, Integer defaultValue) {
        Integer i = getVI(map, key);
        return i == null ? defaultValue : i;
    }

    public static String getVS(Map map, String key, String defaultValue) {
        String str = getVS(map, key);
        return isEmpty(str) ? defaultValue : str;
    }

    public static Integer getVI(Map map, String key) {
        Object value = map.get(key);
        if (isEmpty(value))
            return 0;
        if (value instanceof Integer)
            return (Integer) value;
        return Integer.parseInt((String)value);
    }

    public static String getVS(Map map, String key) {
        Object value = map.get(key);
        if (isEmpty(value))
            return "";

        return (String)value;
    }

    public static String map2Xml (String encode,Map<String,Object> map) {
        //创建document对象，
        Document document = DocumentHelper.createDocument();
        if(StringUtils.isEmpty(encode)){
            encode = "UTF-8";
        }
        document.setXMLEncoding(encode);
        //创建根节点
        Element programE = document.addElement("Program");

        Iterator<String> iter = map.keySet().iterator();
        while(iter.hasNext()){
            String key=iter.next();
            if(MapUtils.isEmpty(map.get(key))) {
                Element infoE = programE.addElement(key);
                //参数集
                if(map.get(key) instanceof Map) {
                    Map<String,Object> mapValue =  (Map<String, Object>) map.get(key);
                    for(Object paraKey : mapValue.keySet()){
                        infoE.addElement(paraKey + "").addText(mapValue.get(paraKey) + "");
                    }
                }else if(map.get(key) instanceof String){
                    infoE.addText(map.get(key).toString());
                }else if(map.get(key) instanceof List){
                    List<Map<String,Object>> values = (List<Map<String, Object>>) map.get(key);
                    for(int i=1; i<=values.size(); i++){
                        Element rowE = infoE.addElement("row" + i);
                        Map rowMap = values.get( i-1 );
                        for(Object rowKey : rowMap.keySet()){
                            rowE.addElement(rowKey + "").addText(rowMap.get(rowKey) + "");
                        }
                    }
                }
            }
        }
        return document.asXML();
    }


    public static Map xml2map(String xml) {
        if (StringUtils.isEmpty(xml)) {
            return null;
        } else {
            Map retMap = new LinkedHashMap();
            Document docuemnt = null;
            try {
                docuemnt = DocumentHelper.parseText(xml);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            Element rootE = docuemnt.getRootElement();
            List<Element> secondEs = rootE.elements();
            Iterator var6 = secondEs.iterator();

            while(var6.hasNext()) {
                Element secondE = (Element)var6.next();
                Object value = null;
                List<Element> thirdEs = secondE.elements();
                if (ListUtils.isEmpty(thirdEs)) {
                    value = secondE.getText().trim();
                } else {
                    Map valueMap = new LinkedHashMap();
                    List<Map> valueList = new ArrayList();
                    Iterator var12 = thirdEs.iterator();

                    while(true) {
                        while(var12.hasNext()) {
                            Element thirdE = (Element)var12.next();
                            List<Element> fourthEs = thirdE.elements();
                            if (!ListUtils.isEmpty(fourthEs)) {
                                Map map = new LinkedHashMap();
                                Iterator var16 = fourthEs.iterator();

                                while(true) {
                                    while(var16.hasNext()) {
                                        Element fourthE = (Element)var16.next();
                                        List<Element> fiveEs = fourthE.elements();
                                        if (!ListUtils.isEmpty(fiveEs)) {
                                            List<Map> valuesList = new ArrayList();
                                            Iterator var20 = fiveEs.iterator();

                                            while(var20.hasNext()) {
                                                Element fiveE = (Element)var20.next();
                                                Map maps = new LinkedHashMap();
                                                List<Element> sixEs = fiveE.elements();
                                                Iterator var24 = sixEs.iterator();

                                                while(var24.hasNext()) {
                                                    Element sixE = (Element)var24.next();
                                                    maps.put(sixE.getName().toLowerCase(), sixE.getText().trim());
                                                }

                                                valuesList.add(maps);
                                            }

                                            map.put(fourthE.getName(), valuesList);
                                        } else {
                                            map.put(fourthE.getName().toLowerCase(), fourthE.getText().trim());
                                        }
                                    }

                                    valueList.add(map);
                                    break;
                                }
                            } else{
                                valueMap.put(thirdE.getName().toLowerCase(), thirdE.getText().trim());
                            }
                        }

                        if (!MapUtils.isEmpty(valueMap)) {
                            value = valueMap;
                        } else {
                            value = valueList;
                        }
                        break;
                    }
                }
                retMap.put(secondE.getName().toLowerCase(), value);
            }

            return retMap;
        }
    }

    /**
     * 实体对象转成Map
     *
     * @param obj 实体对象
     * @return
     */
    public static Map<String, Object> object2Map(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
