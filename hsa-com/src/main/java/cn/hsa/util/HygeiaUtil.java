package cn.hsa.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;

/**
 * 
 * @任务编号
 * @任务标题
 * @修改日期： 
 * @修改说明： 医保参数工具类
 * @作者：kuangyang
 * @创建时间：2019年12月16日 上午10:49:59
 */
public class HygeiaUtil {
	
	
	
	/**
	 *
	 * @任务编号
	 * @任务标题
	 * @修改日期： 
	 * @修改说明：map转xml 
	 * @作者：kuangyang
	 * @创建时间：2019年12月16日 下午1:36:59
	 */
	public static String map2Xml (String encode,Map<String,Object> map) {
	        //创建document对象，
	        Document document = DocumentHelper.createDocument();
	        if(UtilFunc.isBlank(encode)){
	            encode = "UTF-8";
	        }
	        document.setXMLEncoding(encode);
	        //创建根节点
	        Element programE = document.addElement("Program");

	        Iterator<String> iter = map.keySet().iterator();
	        while(iter.hasNext()){
	            String key=iter.next();
	            if(UtilFunc.isNotBlank(map.get(key))) {
	                Element infoE = programE.addElement(key);
	                //参数集
	                if(map.get(key) instanceof Map) {
	                	 Map<String,Object> mapValue =  (Map<String, Object>) map.get(key);
	                    for(Object paraKey : mapValue.keySet()){
	                        Object item = mapValue.get(paraKey);
	                        if (item instanceof String){
                                infoE.addElement(paraKey + "").addText(mapValue.get(paraKey) + "");
                            } else if (item instanceof  List){
                                Element infoE2 =  programE.addElement(paraKey + "");
                                List<Map<String,Object>> values = (List<Map<String, Object>>) item;
                                for(int i=1; i<=values.size(); i++){
                                   Element rowE = infoE2.addElement("row" + i);
                                    Map rowMap = values.get( i-1 );
                                    for(Object rowKey : rowMap.keySet()){
                                        rowE.addElement(rowKey + "").addText(rowMap.get(rowKey) + "");
                                    }
                                }
                            }
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
	
	
    public static Map xml2map(String xml) throws DocumentException {
        if (UtilFunc.isBlank(xml)) {
            return null;
        } else {
            Map retMap = new LinkedHashMap();
            Document docuemnt = DocumentHelper.parseText(xml);
            Element rootE = docuemnt.getRootElement();
            List<Element> secondEs = rootE.elements();
            Iterator var6 = secondEs.iterator();

            while(var6.hasNext()) {
                Element secondE = (Element)var6.next();
                Object value = null;
                List<Element> thirdEs = secondE.elements();
                if (UtilFunc.isEmpty(thirdEs)) {
                    value = secondE.getText().trim();
                } else {
                    Map valueMap = new LinkedHashMap();
                    List<Map> valueList = new ArrayList();
                    Iterator var12 = thirdEs.iterator();

                    while(true) {
                        while(var12.hasNext()) {
                            Element thirdE = (Element)var12.next();
                            List<Element> fourthEs = thirdE.elements();
                            if (!UtilFunc.isEmpty(fourthEs)) {
                                Map map = new LinkedHashMap();
                                Iterator var16 = fourthEs.iterator();

                                while(true) {
                                    while(var16.hasNext()) {
                                        Element fourthE = (Element)var16.next();
                                        List<Element> fiveEs = fourthE.elements();
                                        if (!UtilFunc.isEmpty(fiveEs)) {
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

                        if (!UtilFunc.isEmpty(valueMap)) {
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
	
}
