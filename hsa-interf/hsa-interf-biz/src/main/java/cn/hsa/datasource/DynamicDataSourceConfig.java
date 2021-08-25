//package cn.hsa.datasource;
//
//import cn.hsa.hsaf.core.framework.web.WrapperResponse;
//import cn.hsa.hsaf.core.framework.web.exception.AppException;
//import cn.hsa.module.center.datasource.dto.CenterDatasourceDTO;
//import cn.hsa.module.center.datasource.service.CenterDatasourceService;
//import cn.hsa.util.DBUtils;
//import cn.hsa.util.SpringUtils;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.scheduling.annotation.Scheduled;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//import java.sql.SQLException;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
///**
// * @Package_name: cn.hsa.datasource
// * @Class_name: DynamicDataSourceConfig
// * @Description: 初始化医院数据源
// * @Author: zhongming
// * @Email: 406224709@qq.com
// * @Date: 2020/07/28 14:14
// * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
// **/
//@Configuration
//public class DynamicDataSourceConfig {
//    /**
//     * 中心平台数据源消费者
//     */
//    @Resource
//    private CenterDatasourceService centerDatasourceService_consumer;
//
//    /**
//     * @Method 注册动态数据源
//     * @Description
//     * 1、查询中心端医院对应数据库关系
//     * 2、如果存在新的数据源，需要重新加入新数据源
//     * 3、如果存在变更的数据源，需要修改数据源
//     * 4、如果存在删除的数据源，需要将删除的数据源close掉
//     *
//     * @Param
//     *
//     * @Author zhongming
//     * @Date 2020/7/31 14:45
//     * @Return
//     **/
//    private void initDynamicDataSource(DynamicRoutingDataSource dynamicRoutingDataSource) throws Exception {
//        // 获取最新中心平台数据源集合
//        WrapperResponse<List<CenterDatasourceDTO>> wr = this.centerDatasourceService_consumer.queryCenterHospitalDatasourceAll();
//
//        if (wr.getCode() != 0) {
//            throw new AppException("查询中心平台医院数据源失败：" + wr.getMessage());
//        }
//
//        // 获取中心端最新医院数据源
//        List<CenterDatasourceDTO> newList = wr.getData();
//        if (newList == null || newList.isEmpty()) {
//            throw new AppException("中心平台暂未配置医院数据源信息");
//        }
//
//        // 数据结构转换
//        // key   ->> 医院编码
//        // value ->> 医院数据源信息
//        Map<String, CenterDatasourceDTO> newMap = newList.stream().collect(Collectors.toMap(CenterDatasourceDTO::getCode, a -> a,(k1, k2) -> k1));
//
//        // 过滤连接不上的数据源
//        for (Iterator<Map.Entry<String, CenterDatasourceDTO>> it = newMap.entrySet().iterator(); it.hasNext();){
//            Map.Entry<String, CenterDatasourceDTO> item = it.next();
//            CenterDatasourceDTO dto = item.getValue();
//            if(!DBUtils.testConnection(dto.getDriverName(), dto.getUrl(), dto.getUsername(), dto.getPassword())){
//                it.remove();
//            }
//        }
//
//        if (newMap == null || newMap.isEmpty()) {
//            throw new AppException("中心平台暂无可用医院数据源信息");
//        }
//
//        // 是否刷新数据源
//        boolean isRefresh = false;
//
//        // 暂无数据源
//        if (DynamicRoutingDataSource.isEmpty()) {
//            for (Map.Entry<String, CenterDatasourceDTO> entry: newMap.entrySet()) {
//                initDruidDataSource(entry.getValue(), new DruidDataSourceExt());
//                isRefresh = true;
//            }
//        }
//        else {
//            for (Map.Entry<String, CenterDatasourceDTO> entry: newMap.entrySet()) {
//                CenterDatasourceDTO lastDto = DynamicRoutingDataSource.getCenterDatasourceDTO(entry.getKey());
//                if (lastDto == null) {
//                    initDruidDataSource(entry.getValue(), new DruidDataSourceExt());
//                    isRefresh = true;
//                }
//                else {
//                    if (isChangeDataSource(entry.getValue(), lastDto)) {
//                        DruidDataSourceExt dataSource = DynamicRoutingDataSource.getDruidDataSource(entry.getKey());
//                        if(dataSource.isInited()){
//                            dataSource.close();
//                            dataSource.restart();
//                        }
//
//                        initDruidDataSource(entry.getValue(), dataSource);
//                    }
//                }
//            }
//
//            // 过滤是否有失效的数据源
//            Iterator it = DynamicRoutingDataSource.getDataSource().entrySet().iterator();
//            while (it.hasNext()) {
//                Map.Entry<String, DruidDataSourceExt> item = (Map.Entry<String, DruidDataSourceExt>) it.next();
//                if (!newMap.containsKey(item.getKey())) {
//                    DruidDataSourceExt dataSource = item.getValue();
//                    if(dataSource != null && dataSource.isInited()){
//                        dataSource.close();
//                    }
//                    it.remove();
//                    isRefresh = true;
//                }
//            }
//        }
//
//        if (isRefresh) {
//            DynamicRoutingDataSource.refreshDynamicRoutingDataSource(dynamicRoutingDataSource);
//        }
//    }
//
//    /**
//     * @Method 初始化数据源
//     * @Description
//     *
//     * @Param
//     *
//     * @Author zhongming
//     * @Date 2020/12/2 20:25
//     * @Return
//     **/
//    private void initDruidDataSource(CenterDatasourceDTO dto, DruidDataSourceExt dataSource) throws SQLException {
//        dataSource.setDriverClassName(dto.getDriverName());
//        dataSource.setUrl(dto.getUrl());
//        dataSource.setUsername(dto.getUsername());
//        dataSource.setPassword(dto.getPassword());
//        dataSource.setCenterDatasourceDTO(dto);
//        dataSource.init();
//        DynamicRoutingDataSource.putDataSource(dto.getCode(), dataSource);
//    }
//
//    /**
//     * @Method 判断数据源是否发生改变
//     * @Description
//     *
//     * @Param
//     *
//     * @Author zhongming
//     * @Date 2020/12/2 20:25
//     * @Return
//     **/
//    private boolean isChangeDataSource(CenterDatasourceDTO newDto, CenterDatasourceDTO lastDto) {
//        return !(newDto.getUrl().equals(lastDto.getUrl()) && newDto.getDriverName().equals(lastDto.getDriverName()) &&
//                newDto.getUsername().equals(lastDto.getUsername()) && newDto.getPassword().equals(lastDto.getPassword()));
//    }
//
//    /**
//     * @Method 初始化数据源，托管给Spring
//     * @Description
//     *
//     * @Param
//     *
//     * @Author zhongming
//     * @Date 2020/12/3 15:15
//     * @Return
//     **/
//    @Primary
//    @Bean({"dataSource"})
//    public DataSource dataSource() throws Exception {
//        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
//        this.initDynamicDataSource(dynamicRoutingDataSource);
//        return dynamicRoutingDataSource;
//    }
//
//    /**
//     * @Method 每一分钟获取一次远程医院数据库配置信息
//     * @Description
//     *
//     * @Param
//     *
//     * @Author zhongming
//     * @Date 2020/12/3 15:14
//     * @Return
//     **/
//    @Scheduled(
//            initialDelay = 60000L,
//            fixedRate = 60000000L
//    )
//    public void rateLoadDataSource() {
//        try {
//            this.initDynamicDataSource((DynamicRoutingDataSource) SpringUtils.getBean("dataSource"));
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//}
