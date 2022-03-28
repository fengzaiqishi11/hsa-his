package cn.hsa.module.insure.emr.service;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.emr.dto.InsureEmrDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.inpt.service
 * @class_name: InsureUnifiedEmrService
 * @Description: TODO
 * @Author: qiang.fan
 * @Date: 2022/3/25 10:43
 * @Company: 创智和宇
 **/
@FeignClient(value = "hsa-insure")
public interface InsureUnifiedEmrService {


    /**
     *
     * @param map
     * @return
     */
    WrapperResponse<PageDTO> queryInsureUnifiedEmrInfo(Map<String, Object> map);

    /**
     *
     * @param map
     * @return
     */
    WrapperResponse<InsureEmrDetailDTO> queryInsureUnifiedEmrDetail(Map<String, Object> map);

    /**
     *
     * @param map
     * @return
     */
    WrapperResponse updateInsureUnifiedEmrAdminfo(Map<String, Object> map);

    /**
     *
     * @param map
     * @return
     */
    WrapperResponse updateInsureUnifiedEmrDiseinfo(Map<String, Object> map);

    /**
     *
     * @param map
     * @return
     */
    WrapperResponse updateInsureUnifiedEmrCoursrinfo(Map<String, Object> map);

    /**
     *
     * @param map
     * @return
     */
    WrapperResponse updateInsureUnifiedEmrOprninfo(Map<String, Object> map);

    /**
     *
     * @param map
     * @return
     */
    WrapperResponse updateInsureUnifiedEmrRescinfo(Map<String, Object> map);

    /**
     *
     * @param map
     * @return
     */
    WrapperResponse updateInsureUnifiedEmrDieinfo(Map<String, Object> map);

    /**
     *
     * @param map
     * @return
     */
    WrapperResponse updateInsureUnifiedEmrDscginfo(Map<String, Object> map);

    /**
     *
     * @param map
     * @return
     */
    void updateInsureUnifiedEmrUpload(Map<String, Object> map);

    /**
     *
     * @param map
     * @return
     */
    void updateInsureUnifiedEmrSync(Map<String, Object> map);

    void export(HttpServletRequest req, Map<String, Object> map);
}
