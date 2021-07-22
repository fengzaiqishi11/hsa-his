package cn.hsa.module.upload.healthreport.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;

import java.util.List;
import java.util.Map;

/**
 * @公司 创智和宇信息技术股份有限公司 Copyright (c) 2006-2020,All Rights Reserved.
 * @备注 111
 * @创建者 pengbo
 * @修改者 pengbo
 * @版本 1
 * @日期 2020-12-29  08:55
 */
public interface HealthReportBO {

    /**
     * @备注 1查询数据上传TYPE
     * @创建者  pengbo
     * @创建时间  2020-12-28
     * @修改者  pengbo
     * @param
     * @return
     */
    WrapperResponse<List<Map<String, Object>>> queryUploadTypeList(Map<String, Object> map);

    /**
     * @备注 生成BDF文件,并打包
     * @创建者  pengbo
     * @创建时间  2020-12-28
     * @修改者  pengbo
     * @param
     * @return
     */
    WrapperResponse<Boolean> writeDbfZipFile(Map<String, Object> map);
    /**
     * @备注 根据类型查询对应数据
     * @创建者  pengbo
     * @创建时间  2020-12-28
     * @修改者  pengbo
     * @param
     * @return
     */
    WrapperResponse<PageDTO> queryDataPageByType(Map<String, Object> map);
}
