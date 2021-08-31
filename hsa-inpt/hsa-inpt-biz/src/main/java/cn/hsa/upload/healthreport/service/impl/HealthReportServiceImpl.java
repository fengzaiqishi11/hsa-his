package cn.hsa.upload.healthreport.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.upload.healthreport.bo.HealthReportBO;
import cn.hsa.module.upload.healthreport.service.HealthReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @公司 创智和宇信息技术股份有限公司 Copyright (c) 2006-2020,All Rights Reserved.
 * @备注 卫生数据直报service实现层
 * @创建者 pengbo
 * @修改者 pengbo
 * @版本 1
 * @日期 2020-12-29  09:18
 */
@Slf4j
@HsafRestPath("/service/upload/healthreport")
@Service("healthReportService_provider")
public class HealthReportServiceImpl extends HsafService implements HealthReportService {

    @Resource
    HealthReportBO healthReportBO;

    /**
     * @param map
     * @return
     * @备注 1查询数据上传TYPE
     * @创建者 pengbo
     * @创建时间 2020-12-28
     * @修改者 pengbo
     */
    @Override
    public WrapperResponse<List<Map<String, Object>>> queryUploadTypeList(Map<String, Object> map) {
        return healthReportBO.queryUploadTypeList(map);
    }

    /**
     * @param map
     * @return
     * @备注 根据类型查询对应数据
     * @创建者 pengbo
     * @创建时间 2020-12-28
     * @修改者 pengbo
     */
    @Override
    public  WrapperResponse<PageDTO> queryDataPageByType(Map<String, Object> map) {
        return healthReportBO.queryDataPageByType(map);
    }


    /**
     * @param map
     * @return
     * @备注 生成BDF文件, 并打包
     * @创建者 pengbo
     * @创建时间 2020-12-28
     * @修改者 pengbo
     */
    @Override
    public WrapperResponse<Boolean> writeDbfZipFile(Map<String, Object> map) {
        return healthReportBO.writeDbfZipFile(map);
    }
}
