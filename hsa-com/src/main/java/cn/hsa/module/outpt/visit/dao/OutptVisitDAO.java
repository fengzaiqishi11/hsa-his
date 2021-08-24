package cn.hsa.module.outpt.visit.dao;

import cn.hsa.module.insure.module.dto.InsureIndividualBasicDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualSettleDTO;
import cn.hsa.module.insure.module.entity.InsureIndividualSettleDO;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptMedicalRecordDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsExtDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.visit.dao
 * @Class_name: OutptVisitDAO
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/8/19 10:00
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptVisitDAO {

    void insert(OutptVisitDTO outptVisitDTO);

    /**
     * @Method queryVisitRecords
     * @Desrciption 查询就诊记录
     * @Param
     * [outptVisitDTO] profileId：个人档案ID
     * @Author liaojunjie
     * @Date   2020/9/8 16:33
     * @Return java.util.List<cn.hsa.module.outpt.visit.dto.OutptVisitDTO>
     **/
    List<OutptVisitDTO> queryVisitRecords(OutptVisitDTO outptVisitDTO);

    /**
     * @Menthod getMedicalRecord
     * @Desrciption  获取病历记录
     * @param map visitId：就诊ID  hospCode ：医院编码
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<Map<String, Object>>
     **/
    List<OutptVisitDTO> getVisitById(Map map);

    /**
     * @Method queryAll
     * @Desrciption 查询全部门诊就诊信息(分页不分页都支持,分页在bo层加PageHelper)
     * @Param
     * [outptVisitDTO]
     * @Author liaojunjie
     * @Date   2020/9/8 16:35
     * @Return java.util.List<cn.hsa.module.outpt.visit.dto.OutptVisitDTO>
     **/
    List<OutptVisitDTO> queryAll(OutptVisitDTO outptVisitDTO);

    /**
     * @Method updateTranInCode
     * @Desrciption 更新转入院代码
     * @Param
     * [outptVisitDTO]
     * @Author liaojunjie
     * @Date   2020/9/17 13:53
     * @Return
     **/
    Integer updateTranInCode(OutptVisitDTO outptVisitDTO);

    void updateProfile(OutptVisitDTO outptVisitDTO);

    OutptVisitDTO getVisitByParams(Map<String, Object> map);

    /**
     * @Method getInsureBasicById
     * @Desrciption 获取医保个人信息
     * @Param
     * [selectMap]
     * @Author liaojiguang
     * @Date   2020/11/30 19:23
     * @Return
     **/
    InsureIndividualBasicDTO getInsureBasicById(Map selectMap);

    /**
     * @Menthod queryByVisitID
     * @Desrciption 根据就诊id查询门诊患者信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/17 21:55
     * @Return cn.hsa.module.outpt.visit.dto.OutptVisitDTO
     */
    OutptVisitDTO queryByVisitID(Map<String,String> param);

    /**
     * @Method updateOutptVisit
     * @Desrciption  走统一支付平台时，登记挂号成功以后，修改病人类型
     * @Param outptVisitMap
     *
     * @Author fuhui
     * @Date   2021/3/8 15:25
     * @Return
     **/
    Boolean updateOutptVisit(OutptVisitDTO outptVisitDTO);

    /**
     * @Method insurtInsureSettleInfo
     * @Desrciption
     * @Param insureIndividualSettleDO
     *
     * @Author lioajiguang
     * @Date   2021/4/8 15:25
     * @Return
     **/
    int insertInsureSettleInfo(InsureIndividualSettleDO insureIndividualSettleDO);

    int updateInsureSettleInfo(InsureIndividualSettleDO insureIndividualSettleDO);

    void updateInsureSettle(InsureIndividualSettleDTO individualSettleDTO);

    /**
     * @param map
     * @Method updateVisitTime
     * @Desrciption 更新患者的就诊时间
     * @Param
     * @Author fuhui
     * @Date 2021/6/24 0:47
     * @Return
     */
    Boolean updateVisitTime(Map<String, Object> map);

    // 根据就诊id查询病历信息(outpt_medical_record)
    List<OutptMedicalRecordDTO> queryMedicalRecordByVisitId(Map<String, Object> map);

    // 根据就诊id查询诊断信息(outpt_diagnose)
    List<OutptDiagnoseDTO> queryDiagnoseByVisitId(Map<String, Object> map);

    // 根据就诊id查询处方信息(outpt_prescribe_detail_ext)
    List<OutptPrescribeDetailsExtDTO> queryPreDetailExtByVisitId(Map<String, Object> map);

    // 根据就诊id查询手术信息
    List<OperInfoRecordDTO> queryOperInfoRecordByVistiId(Map<String, Object> map);
}
