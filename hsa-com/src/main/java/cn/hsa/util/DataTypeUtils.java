package cn.hsa.util;

/**
 * @Package_name: cn.hsa.insure.util
 * @Class_name: ResultBean
 * @Describe(描述):
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/09 14:09
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public class DataTypeUtils {

    /**
     * 数据类型的数据
     * @param object
     * @return
     */
    public static String dataToNumString(Object object) {
        String resultStr = "0";
        if (object == null) {
            return resultStr;
        }
        resultStr = object.toString();
        if ("null".equals(resultStr) || "".equals(resultStr)) {
            resultStr = "0";
        }
        return resultStr;
    }


}
