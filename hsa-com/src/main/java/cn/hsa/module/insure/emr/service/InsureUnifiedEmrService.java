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

    /**
     * 根据就诊ID查询入院信息
     * @param paramMap
     * @Author 医保开发二部-湛康
     * @Date 2022-08-22 14:26
     * @return cn.hsa.module.insure.emr.dto.InsureEmrAdminfoDTO
     */
    InsureEmrAdminfoDTO queryAdmInfoByMdtrtSn(Map paramMap);

    /**
     * 查询首次病程记录信息
     * @param paramMap
     * @Author 医保开发二部-湛康
     * @Date 2022-08-22 17:17
     * @return cn.hsa.module.insure.emr.dto.InsureEmrCoursrinfoDTO
     */
    InsureEmrCoursrinfoDTO queryCoursrInfoByMdtrtSn(Map paramMap);

    /**
     * 查询死亡记录信息
     * @param paramMap
     * @Author 医保开发二部-湛康
     * @Date 2022-08-22 17:19
     * @return
     */
    InsureEmrDieinfoDTO queryDieInfoByMdtrtSn(Map paramMap);

    /**
     * 查询电子病历出院小结信息
     * @param paramMap
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 10:27
     * @return cn.hsa.module.insure.emr.dto.InsureEmrDscginfoDTO
     */
    InsureEmrDscginfoDTO queryDscgInfoByMdtrtSn(Map paramMap);

    /**
     * 查询电子病历手术信息
     * @param paramMap
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 19:57
     * @return cn.hsa.module.insure.emr.dto.InsureEmrOprninfoDTO
     */
    InsureEmrOprninfoDTO queryOprnInfoByTemplateId(Map paramMap);

    /**
     * 根据病历编号查询抢救信息
     * @param paramMap
     * @Author 医保开发二部-湛康
     * @Date 2022-08-24 9:49
     * @return cn.hsa.module.insure.emr.dto.InsureEmrRescinfoDTO
     */
    InsureEmrRescinfoDTO queryRescInfoByTemplateId(Map paramMap);

    /**
     * 根据MdtrtSn更新入院信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-22 14:31
     * @return int
     */
    int updateAdmSelectiveByMdtrtSn(Map map);

    /**
     * 根据mdtrtsn更新首次病程信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 9:04
     * @return int
     */
    int updateCoursrSelectiveByMdtrtSn(Map map);

    /**
     * 根据mdtrtsn更新死亡信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 9:37
     * @return int
     */
    int updateDieSelectiveByMdtrtSn(Map map);

    /**
     * 更新出院小结信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 10:43
     * @return int
     */
    int updateDscgSelectiveByMdtrtSn(Map map);

    /**
     * 更新手术信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 20:25
     * @return int
     */
    int updateOprnSelectiveByTemplateId(Map map);

    int updateRescSelectiveByTemplateId(Map map);

    /**
     * 插入医保电子病历入院信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-22 15:02
     * @return int
     */
    int insertAdminfo(Map map);

    /**
     * 插入医保电子病历首次病程记录
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-22 16:22
     * @return int
     */
    int insertCoursrinfo(Map map);

    /**
     * 插入医保电子病历死亡记录
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 8:45
     * @return int
     */
    int insertDieinfo(Map map);

    /**
     * 插入出院小结信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 10:57
     * @return int
     */
    int insertDscgInfo(Map map);

    /**
     * 插入手术信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-24 9:09
     * @return int
     */
    int insertOprnInfo(Map map);

    int insertRescInfo(Map map);
}
