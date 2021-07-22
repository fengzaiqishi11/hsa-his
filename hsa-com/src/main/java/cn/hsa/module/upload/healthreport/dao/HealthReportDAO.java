package cn.hsa.module.upload.healthreport.dao;


import cn.hsa.module.upload.healthreport.dto.ZybasyDTO;
import cn.hsa.module.upload.healthreport.entity.ZybasyDO;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @公司 创智和宇信息技术股份有限公司 Copyright (c) 2006-2020,All Rights Reserved.
 * @备注 卫生数据直报dao层接口
 * @创建者 pengbo
 * @修改者 pengbo
 * @版本 1
 * @日期 2020-12-29  08:55
 */
public interface HealthReportDAO {

    /**
     * @备注 1.	卫健统2表(表号N02)
     * @创建者  pengbo
     * @创建时间  2020-12-28
     * @修改者  pengbo
     * @param
     * @return
     */
    List<LinkedHashMap<String,Object>> queryData(@Param(value = "sql")String sql);

    /**
     * @备注 2. 查询需要导出的数据
     * @创建者  pengbo
     * @创建时间  2020-12-28
     * @修改者  pengbo
     * @param
     * @return
     */
    List<Map<String,Object>>  queryUploadType(Map<String, Object> map);


    /**
     * @备注 2. 根据types查询需要导出的数据
     * @创建者  pengbo
     * @创建时间  2020-12-28
     * @修改者  pengbo
     * @param
     * @return
     */
    List<Map<String,Object>>  queryUploadTypeList(Map<String, Object> map);
    /**
     * @备注 2. 根据types查询需要相关配置信息
     * @创建者  pengbo
     * @创建时间  2020-12-28
     * @修改者  pengbo
     * @param
     * @return
     */
    Map<String, Object> queryUploadTypeByType(Map<String, Object> map);
    /**
     * @备注 2. 根据sql_str 分页查询数据
     * @创建者  pengbo
     * @创建时间  2020-12-28
     * @修改者  pengbo
     * @param
     * @return
     */
    List<ZybasyDTO> queryDataByTypePage(String sql_str);
}
