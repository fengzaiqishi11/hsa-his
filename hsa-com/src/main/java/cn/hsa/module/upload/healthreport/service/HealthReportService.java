package cn.hsa.module.upload.healthreport.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;
import java.util.Map;

@FeignClient(value = "hsa-upload")
public interface HealthReportService {

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
     * @备注 根据类型查询对应数据
     * @创建者  pengbo
     * @创建时间  2020-12-28
     * @修改者  pengbo
     * @param
     * @return
     */
    WrapperResponse<PageDTO> queryDataPageByType(Map<String, Object> map);

    /**
     * @备注 生成BDF文件,并打包
     * @创建者  pengbo
     * @创建时间  2020-12-28
     * @修改者  pengbo
     * @param
     * @return
     */
    WrapperResponse<Boolean> writeDbfZipFile(Map<String, Object> map);
}
