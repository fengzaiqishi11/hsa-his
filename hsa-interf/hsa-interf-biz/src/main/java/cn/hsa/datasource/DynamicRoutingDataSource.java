//package cn.hsa.datasource;
//
//import cn.hsa.module.center.datasource.dto.CenterDatasourceDTO;
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @Package_name: cn.hsa.datasource
// * @Class_name: DynamicRoutingDataSource
// * @Description:
// * @Author: zhongming
// * @Email: 406224709@qq.com
// * @Date: 2020/07/28 13:59
// * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
// **/
//public class DynamicRoutingDataSource extends AbstractRoutingDataSource {
//    private static final Map<Object, Object> LAST_DS_MAP = new HashMap<>();
//
//    @Override
//    public Object determineCurrentLookupKey() {
//        return DynamicDataSourceContextHolder.getDataSourceKey();
//    }
//
//    @Override
//    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
//        super.setTargetDataSources(targetDataSources);
//        super.afterPropertiesSet();
//    }
//
//    /**
//     * @Method 获取所有数据源
//     * @Description
//     *
//     * @Param
//     *
//     * @Author zhongming
//     * @Date 2020/12/3 10:17
//     * @Return
//     **/
//    public static Map<Object, Object> getDataSource() {
//        return LAST_DS_MAP;
//    }
//
//    /**
//     * @Method 新增数据源
//     * @Description
//     *
//     * @Param
//     *
//     * @Author zhongming
//     * @Date 2020/12/3 10:17
//     * @Return
//     **/
//    public static void putDataSource(Object key, Object value) {
//        LAST_DS_MAP.put(key, value);
//    }
//
//    /**
//     * @Method 是否为空数据源
//     * @Description
//     *
//     * @Param
//     *
//     * @Author zhongming
//     * @Date 2020/12/3 10:17
//     * @Return
//     **/
//    public static boolean isEmpty() {
//        return LAST_DS_MAP.isEmpty();
//    }
//
//    /**
//     * @Method 是否存在指定数据源
//     * @Description
//     *
//     * @Param
//     *install
//     * @Author zhongming
//     * @Date 2020/12/3 10:17
//     * @Return
//     **/
//    public static boolean isExist(Object key) {
//        return LAST_DS_MAP.containsKey(key);
//    }
//
//    /**
//     * @Method 获取指定医院数据源
//     * @Description
//     *
//     * @Param
//     *
//     * @Author zhongming
//     * @Date 2020/12/3 10:18
//     * @Return
//     **/
//    public static CenterDatasourceDTO getCenterDatasourceDTO(Object key) {
//        if (isExist(key)) {
//            return ((DruidDataSourceExt)LAST_DS_MAP.get(key)).getCenterDatasourceDTO();
//        }
//        return null;
//    }
//
//    /**
//     * @Method 获取指定数据源
//     * @Description
//     *
//     * @Param
//     *
//     * @Author zhongming
//     * @Date 2020/12/3 10:18
//     * @Return
//     **/
//    public static DruidDataSourceExt getDruidDataSource(Object key) {
//        if (isExist(key)) {
//            return (DruidDataSourceExt) LAST_DS_MAP.get(key);
//        }
//        return null;
//    }
//
//    /**
//     * @Method 刷新数据源
//     * @Description
//     *
//     * @Param
//     *
//     * @Author zhongming
//     * @Date 2020/12/3 12:47
//     * @Return
//     **/
//    public static void refreshDynamicRoutingDataSource(DynamicRoutingDataSource dynamicRoutingDataSource) {
//        dynamicRoutingDataSource.setDefaultTargetDataSource(LAST_DS_MAP.values().toArray()[0]);
//        dynamicRoutingDataSource.setTargetDataSources(LAST_DS_MAP);
//    }
//}
//
