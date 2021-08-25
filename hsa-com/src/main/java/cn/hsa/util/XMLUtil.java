package cn.hsa.util;

import org.dom4j.*;

import java.util.*;

/**
 * @Package_name: cn.hsa.util
 * @Class_name: XMLUtils
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2021/1/26 9:52
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public class XMLUtil {
    /**
     * <p>
     * 将xml串转换成Map类型，如果有重复节点，自动转换为list类型存储，所以最终转换并非绝对Map
     * </p>
     *
     * @param xml
     * @return
     * @throws DocumentException
     */
    @SuppressWarnings("unchecked")
    public static Map parseXmlToMap(String xml) throws DocumentException {
        Document doc = DocumentHelper.parseText(xml);
        Element root = doc.getRootElement();
        List<Element> list = root.elements();
        Map resultMap = new HashMap();
        for (Element e : list) {
            String name = e.getName();
            String text = e.getTextTrim();
            if (e.elements().size() == 0) {
                resultMap.put(name, text);
            } else {
                if (!resultMap.containsKey(name)) {
                    resultMap.put(name, parseXmlToMap(e.asXML()));
                } else {
                    List<Map> tmplist = null;
                    if (resultMap.get(name) instanceof java.util.Map) {
                        tmplist = new ArrayList<Map>();
                        tmplist.add((Map) resultMap.get(name));
                    } else if (resultMap.get(name) instanceof java.util.List) {
                        tmplist = (List<Map>) resultMap.get(name);
                    }
                    tmplist.add(parseXmlToMap(e.asXML()));
                    resultMap.put(name, tmplist);
                }
            }
        }
        return resultMap;
    }



    /**
     * xml字符串转为map集合
     * @param xmlStr
     * @return
     */
    public static Map<String, Object> xmlToMap(String xmlStr){
        List<Map<String, String>> resultList = iterateWholeXML(xmlStr);
        Map<String, Object> retMap = new HashMap<String, Object>();
        for (int i = 0; i < resultList.size(); i++) {
            Map<String,Object> map = (Map)resultList.get(i);

            for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
                String key = (String) iterator.next();
                retMap.put(key, map.get(key));

            }
        }
        return retMap;
    }

    /**
     * 递归解析任意的xml 遍历每个节点和属性
     *
     * @param xmlStr
     */
    private static List<Map<String, String>> iterateWholeXML(String xmlStr) {

        List<Map<String, String>> list = new ArrayList();

        try {
            Document document = DocumentHelper.parseText(xmlStr);
            Element root = document.getRootElement();
            recursiveNode(root, list);
            return list;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 递归遍历所有的节点获得对应的值
     *
     * @param
     */
    private static void recursiveNode(Element root, List<Map<String, String>> list) {

        // 遍历根结点的所有孩子节点
        HashMap<String, String> map = new HashMap();
        for (Iterator iter = root.elementIterator(); iter.hasNext();) {
            Element element = (Element) iter.next();
            if (element == null)
                continue;
            // 获取属性和它的值
            for (Iterator attrs = element.attributeIterator(); attrs.hasNext();) {
                Attribute attr = (Attribute) attrs.next();
                if (attr == null)
                    continue;
                String attrName = attr.getName();
                String attrValue = attr.getValue();

                map.put(attrName, attrValue);
            }
            // 如果有PCDATA，则直接提出
            if (element.isTextOnly()) {
                String innerName = element.getName();
                String innerValue = element.getText();
                map.put(innerName, innerValue);
                list.add(map);
            } else {
                // 递归调用
                recursiveNode(element, list);
            }
        }
    }
}
