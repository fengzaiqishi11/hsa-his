package cn.hsa.module.insure.emr.service;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.emr.dto.*;
import org.springframework.cloud.openfeign.FeignClient;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
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
    WrapperResponse<InsureEmrAdminfoDTO> updateInsureUnifiedEmrAdminfo(Map<String, Object> map);

    /**
     *
     * @param map
     * @return
     */
    WrapperResponse<InsureEmrDiseinfoDTO> updateInsureUnifiedEmrDiseinfo(Map<String, Object> map);

    /**
     *
     * @param map
     * @return
     */
    WrapperResponse<InsureEmrCoursrinfoDTO> updateInsureUnifiedEmrCoursrinfo(Map<String, Object> map);

    /**
     *
     * @param map
     * @return
     */
    WrapperResponse<InsureEmrOprninfoDTO> updateInsureUnifiedEmrOprninfo(Map<String, Object> map);

    /**
     *
     * @param map
     * @return
     */
    WrapperResponse<InsureEmrRescinfoDTO> updateInsureUnifiedEmrRescinfo(Map<String, Object> map);

    /**
     *
     * @param map
     * @return
     */
    WrapperResponse<InsureEmrDieinfoDTO> updateInsureUnifiedEmrDieinfo(Map<String, Object> map);

    /**
     *
     * @param map
     * @return
     */
    WrapperResponse<InsureEmrDscginfoDTO> updateInsureUnifiedEmrDscginfo(Map<String, Object> map);

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

    List<InsureEmrDscginfoDTO> queryInsureUnifiedEmrDscginfo(Map map);

    List<InsureEmrDieinfoDTO> queryInsureUnifiedEmrDieinfo(Map map);

    List<InsureEmrRescinfoDTO> queryInsureUnifiedEmrRescinfo(Map map);

    List<InsureEmrOprninfoDTO> queryInsureUnifiedEmrOprninfo(Map map);

    List<InsureEmrCoursrinfoDTO> queryInsureUnifiedEmrCoursrinfo(Map map);

    List<InsureEmrDiseinfoDTO> queryInsureUnifiedEmrDiseinfo(Map map);

    InsureEmrAdminfoDTO queryInsureUnifiedEmrAdminfo(Map map);
}
