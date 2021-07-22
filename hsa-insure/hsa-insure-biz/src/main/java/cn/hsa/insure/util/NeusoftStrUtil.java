package cn.hsa.insure.util;

import cn.hsa.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package_name: cn.hsa.insure.util
 * @Class_name: NeusoftStrUtil
 * @Describe(描述): 东软动态库回参处理工具类
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2021/01/27 14:38
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public class NeusoftStrUtil {

    /**
     * @Menthod parseText
     * @Desrciption 解析东软动态库返回查询信息
     * @param text 文本内容
     * @Author Ou·Mr
     * @Date 2021/1/27 14:08
     * @Return java.util.List<java.lang.String[]>
     */
    public static List<String[]> parseText(Object text){
        if (text != null){
            List<String[]> data = new ArrayList<String[]>();
            String [] rowStr = String.valueOf(text).split("\n");
            int i = 1;
            for (String row : rowStr){
                row += "\t"+i;
                data.add(row.split("\t"));
                i++;
            }
            return data;
        }
        return null;
    }

}
