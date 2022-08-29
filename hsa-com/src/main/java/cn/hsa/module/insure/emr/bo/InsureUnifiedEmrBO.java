package cn.hsa.module.insure.emr.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.insure.emr.dto.*;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.emr.dto.InsureEmrDetailDTO;
import cn.hsa.module.insure.emr.dto.InsureEmrRescinfoDTO;
import cn.hsa.module.insure.emr.dto.InsureEmrUnifiedDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualSettleDTO;

import java.util.List;
import java.util.Map;

/**
* @ClassName InsureEmrRescinfoBO
* @Deacription 医保电子病历上传
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
public interface InsureUnifiedEmrBO {

    /**
     * @Method queryInsureUnifiedEmrInfo
     * @Desrciption  电子病历上传-患者列表查询
     * @Param insureEmrUnifiedDTO
     * @Author liuhuiming
     * @Date   2022/3/25 10:03
     * @Return
     **/
    PageDTO queryInsureUnifiedEmrInfo(InsureEmrUnifiedDTO insureEmrUnifiedDTO);


    void updateInsureUnifiedEmrUpload(InsureEmrUnifiedDTO insureEmrUnifiedDTO);

    /**
     * @Method queryInsureUnifiedEmrDetail
     * @Desrciption  电子病历上传-患者详情查询
     * @Param insureEmrUnifiedDTO
     * @Author liuhuiming
     * @Date   2022/3/25 10:03
     * @Return
     **/
    InsureEmrDetailDTO queryInsureUnifiedEmrDetail(InsureEmrUnifiedDTO insureEmrUnifiedDTO);

    void updateInsureUnifiedEmrSync(InsureEmrUnifiedDTO insureEmrUnifiedDTO);

    /**
     * @Method queryInsureUnifiedEmrDetail
     * @Desrciption  电子病历上传-患者基本信息修改
     * @Param insureEmrAdminfoDTO
     * @Author liuhuiming
     * @Date   2022/3/25 10:03
     * @Return
     **/
    InsureEmrAdminfoDTO updateInsureUnifiedEmrAdminfo(InsureEmrAdminfoDTO insureEmrAdminfoDTO);

    /**
     * @Method queryInsureUnifiedEmrDetail
     * @Desrciption  电子病历上传-患者诊断信息修改
     * @Param insureEmrDiseinfoDTO
     * @Author liuhuiming
     * @Date   2022/3/25 10:03
     * @Return
     **/
    InsureEmrDiseinfoDTO updateInsureUnifiedEmrDiseinfo(InsureEmrDiseinfoDTO insureEmrDiseinfoDTO);

    /**
     * @Method queryInsureUnifiedEmrDetail
     * @Desrciption  电子病历上传-患者病程信息修改
     * @Param insureEmrDiseinfoDTO
     * @Author liuhuiming
     * @Date   2022/3/25 10:03
     * @Return
     **/
    InsureEmrCoursrinfoDTO updateInsureUnifiedEmrCoursrinfo(InsureEmrCoursrinfoDTO insureEmrCoursrinfoDTO);

    /**
     * @Method queryInsureUnifiedEmrDetail
     * @Desrciption  电子病历上传-患者手术信息修改
     * @Param insureEmrDiseinfoDTO
     * @Author liuhuiming
     * @Date   2022/3/25 10:03
     * @Return
     **/
    InsureEmrOprninfoDTO updateInsureUnifiedEmrOprninfo(InsureEmrOprninfoDTO insureEmrOprninfoDTO);

    /**
     * @Method queryInsureUnifiedEmrDetail
     * @Desrciption  电子病历上传-患者病情抢救信息修改
     * @Param insureEmrDiseinfoDTO
     * @Author liuhuiming
     * @Date   2022/3/25 10:03
     * @Return
     **/
    InsureEmrRescinfoDTO updateInsureUnifiedEmrRescinfo(InsureEmrRescinfoDTO insureEmrRescinfoDTO);

    /**
     * @Method queryInsureUnifiedEmrDetail
     * @Desrciption  电子病历上传-患者死亡信息修改
     * @Param insureEmrDiseinfoDTO
     * @Author liuhuiming
     * @Date   2022/3/25 10:03
     * @Return
     **/
    InsureEmrDieinfoDTO updateInsureUnifiedEmrDieinfo(InsureEmrDieinfoDTO insureEmrDieinfoDTO);

    /**
     * @Method queryInsureUnifiedEmrDetail
     * @Desrciption  电子病历上传-患者出院信息修改
     * @Param insureEmrDiseinfoDTO
     * @Author liuhuiming
     * @Date   2022/3/25 10:03
     * @Return
     **/
    InsureEmrDscginfoDTO updateInsureUnifiedEmrDscginfo(InsureEmrDscginfoDTO insureEmrDscginfoDTO);

    /**
     * @Method queryInsureUnifiedEmrDetail
     * @Desrciption  电子病历上传-导出
     * @Param insureEmrUnifiedDTO
     * @Author liuhuiming
     * @Date   2022/3/25 10:03
     * @Return
     **/
    List<InsureEmrUnifiedDTO> export(InsureEmrUnifiedDTO insureEmrUnifiedDTO);

    List<InsureEmrDscginfoDTO> queryInsureUnifiedEmrDscginfo(InsureEmrUnifiedDTO insureEmrUnifiedDTO);

    List<InsureEmrDieinfoDTO> queryInsureUnifiedEmrDieinfo(InsureEmrUnifiedDTO insureEmrUnifiedDTO);

    List<InsureEmrRescinfoDTO> queryInsureUnifiedEmrRescinfo(InsureEmrUnifiedDTO insureEmrUnifiedDTO);

    List<InsureEmrOprninfoDTO> queryInsureUnifiedEmrOprninfo(InsureEmrUnifiedDTO insureEmrUnifiedDTO);

    List<InsureEmrCoursrinfoDTO> queryInsureUnifiedEmrCoursrinfo(InsureEmrUnifiedDTO insureEmrUnifiedDTO);

    List<InsureEmrDiseinfoDTO> queryInsureUnifiedEmrDiseinfo(InsureEmrUnifiedDTO insureEmrUnifiedDTO);

    InsureEmrAdminfoDTO queryInsureUnifiedEmrAdminfo(InsureEmrUnifiedDTO insureEmrUnifiedDTO);

    /**
     * 根据就诊ID查询入院信息
     * @param mdtrtSn
     * @Author 医保开发二部-湛康
     * @Date 2022-08-22 14:28
     * @return cn.hsa.module.insure.emr.dto.InsureEmrAdminfoDTO
     */
    InsureEmrAdminfoDTO queryAdmInfoByMdtrtSn(String mdtrtSn);

    /**
     * 根据就诊ID查询首次病程信息
     * @param mdtrtSn
     * @Author 医保开发二部-湛康
     * @Date 2022-08-22 16:08
     * @return cn.hsa.module.insure.emr.dto.InsureEmrCoursrinfoDTO
     */
    InsureEmrCoursrinfoDTO queryCoursrInfoByMdtrtSn(String mdtrtSn);

    /**
     * 查询死亡记录信息
     * @param mdtrtSn
     * @Author 医保开发二部-湛康
     * @Date 2022-08-22 17:25
     * @return cn.hsa.module.insure.emr.dto.InsureEmrDieinfoDTO
     */
    InsureEmrDieinfoDTO queryDieInfoByMdtrtSn(String mdtrtSn);

    /**
     * 查询出院小结信息
     * @param mdtrtSn
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 10:31
     * @return cn.hsa.module.insure.emr.dto.InsureEmrDscginfoDTO
     */
    InsureEmrDscginfoDTO queryDscgInfoByMdtrtSn(String mdtrtSn);

    /**
     * 保存电子病历手术信息
     * @param emrTemplateId
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 20:05
     * @return cn.hsa.module.insure.emr.dto.InsureEmrOprninfoDTO
     */
    InsureEmrOprninfoDTO queryOprnInfoByTemplateId(String emrTemplateId);

    /**
     * 根据病历编号查询抢救信息
     * @param emrTemplateId
     * @Author 医保开发二部-湛康
     * @Date 2022-08-24 9:50
     * @return cn.hsa.module.insure.emr.dto.InsureEmrRescinfoDTO
     */
    InsureEmrRescinfoDTO queryRescInfoByTemplateId(String emrTemplateId);

    /**
     * 根据MdtrtSn更新入院信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-22 14:35
     * @return int
     */
    int updateAdmSelectiveByMdtrtSn(Map map);

    /**
     * 根据mdtrtsn更新首次病程信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 9:05
     * @return int
     */
    int updateCoursrSelectiveByMdtrtSn(Map map);

    /**
     * 根据mdtrtsn更新死亡信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 9:38
     * @return int
     */
    int updateDieSelectiveByMdtrtSn(Map map);

    /**
     * 更新出院小结信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 10:47
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
     * @Date 2022-08-22 15:04
     * @return void
     */
    int insertAdminfo(Map map);

    /**
     * 插入医保电子病历首次病程记录
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-22 16:23
     * @return int
     */
    int insertCoursrinfo(Map map);

    /**
     * 插入医保电子病历死亡记录
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 8:49
     * @return int
     */
    int insertDieinfo(Map map);

    /**
     * 插入出院小结信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-23 10:58
     * @return int
     */
    int insertDscgInfo(Map map);

    /**
     * 插入手术信息
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-08-24 9:19
     * @return int
     */
    int insertOprnInfo(Map map);

    int insertRescInfo(Map map);
}
