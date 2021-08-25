//package cn.hsa.datasource;
//
///**
// * @Package_name: cn.hsa.datasource
// * @Class_name: DynamicDataSourceContextHolder
// * @Description:
// * @Author: zhongming
// * @Email: 406224709@qq.com
// * @Date: 2020/07/28 13:58
// * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
// **/
//public class DynamicDataSourceContextHolder {
//    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
//
//    /**
//     * To switch DataSource
//     *
//     * @param key the key
//     */
//    public static void setDataSourceKey(String key) {
//        contextHolder.set(key);
//    }
//
//    /**
//     * Get current DataSource
//     *
//     * @return data source key
//     */
//    public static String getDataSourceKey() {
//        return contextHolder.get();
//    }
//
//    /**
//     * To set DataSource as default
//     */
//    public static void clearDataSourceKey() {
//        contextHolder.remove();
//    }
//}
