package cn.hsa.module.insure.module.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.entity.InsureIndividualVisitDO;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.insureIndividualVisit.service
 * @Class_name: InsureIndividualVisitService
 * @Describe(描述): 医保就诊信息表 service层
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/18 19:26
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-insure")
public interface InsureIndividualVisitService {

    /**
     * @Menthod addInsureIndividualVisit
     * @Desrciption 新增医保就诊信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/18 20:16
     * @Return int 受影响行数
     */
    InsureIndividualVisitDO addInsureIndividualVisit(Map<String,Object> param);

    /**
     * @Menthod findByCondition
     * @Desrciption 查询医保就诊信息
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/11/29 15:57
     * @Return java.util.List<cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO>
     */
    List<InsureIndividualVisitDTO> findByCondition(Map<String,Object> param);

    /**
     * @Menthod editInsureIndividualVisit
     * @Desrciption 编辑医保就诊信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/1 14:58
     * @Return int 受影响行数
     */
    int editInsureIndividualVisit(Map<String,Object> param);

    /**
     * @Menthod insertIndividualVisit
     * @Desrciption 新增医保就诊信息
     * @param insertMap 请求参数
     * @Author 廖继广
     * @Date 2020/12/21 14:58
     * @Return Boolean
     */
    Boolean insertIndividualVisit(Map<String, Object> insertMap);



    /**
     * @Menthod insertIndividualVisit
     * @Desrciption 新增医保就诊信息
     * @param param 请求参数
     * @Author 廖继广
     * @Date 2020/12/21 14:58
     * @Return Boolean
     */
    Boolean deleteByVisitId(Map<String, Object> param);

    /**
     * @Method deletePatientSumInfo
     * @Desrciption 根据psnNo删除个人累计信息
     * @Param
     * @Author wangqiao
     * @Date 2022/09/29 10:58
     * @Return
     **/
    Integer deletePatientSumInfoByPsnNo(Map<String, Object> map);

    /**
     * @Method insertPatientSumInfoAll
     * @Desrciption 医保登记的时候, 保存个人年度累计信息
     * @Param resultDataMap
     * @Author wangqiao
     * @Date 2022/09/08 10:58
     * @Return
     **/
    Integer insertPatientSumInfoAll(Map<String, Object> resultDataMap);

    /**
     * @Method
     * @Desrciption
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/4 9:02
     * @Return
     **/
    InsureIndividualVisitDTO getInsureIndividualVisitById(Map<String, Object> insureVisitParam);

    /**
     * @Method deleteInsureVisitById
     * @Desrciption  退费以后，取消门诊挂号登记
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/16 19:50
     * @Return
     **/
    WrapperResponse<Boolean> deleteInsureVisitById(Map<String, Object> map);

    /**
     * @Method updateInsureInidivdual
     * @Desrciption  修改医保病人信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/5/20 22:41
     * @Return
    **/
    WrapperResponse<Boolean> updateInsureInidivdual(Map<String,Object> map);

    /**
     * @Method selectInsureInfo
     * @Desrciption  查询医保就诊信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/7/30 15:32
     * @Return
    **/
    WrapperResponse<InsureIndividualVisitDTO> selectInsureInfo(Map<String,Object> map);



    /**
     * @Method updateInsureSettleId
     * @Desrciption  取消结算更新医保就诊表的结算id
     * @Param
     *
     * @Author fuhui
     * @Date   2021/8/4 10:18
     * @Return
     **/
    WrapperResponse<Boolean> updateInsureSettleId(Map<String, Object> settleMap);

    /**
     * @Method queryAllInsureIndiviualVisit
     * @Desrciption  根据就诊id 查询医保就诊信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/8/9 19:59
     * @Return
    **/
    WrapperResponse<List<InsureIndividualVisitDTO>> queryAllInsureIndiviualVisit(Map<String, Object> insureUnifiedPayParam);

    /**
     * @Method selectMaxAndMinRegisterTime
     * @Desrciption
     * @Param
     *
     * @Author fuhui
     * @Date   2021/8/9 20:56
     * @Return
    **/
    WrapperResponse<InsureIndividualVisitDTO> selectMaxAndMinRegisterTime(Map<String, Object> insureUnifiedPayParam);

    /**
     * @Method selectHalfVisit
     * @Desrciption  查询出中途结算次数
     * @Param
     *
     * @Author fuhui
     * @Date   2022/2/15 10:15
     * @Return
    **/
    WrapperResponse<Integer> selectHalfVisit(Map<String,Object> map);

    /**
     * @Method updateInsureSettleCounts
     * @Desrciption  更新中途结算医保标志和次数
     * @Param
     *
     * @Author fuhui
     * @Date   2022/2/16 8:55
     * @Return
    **/
    void updateInsureSettleCounts(Map<String, Object> param);

    /**
     * 根据医保就诊ID获取医保就诊信息
     * @param insureVisitParam
     * @Author 医保开发二部-湛康
     * @Date 2022-05-07 14:56
     * @return cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO
     */
    InsureIndividualVisitDTO getInsureIndividualVisitByMedRegNo(Map<String, Object> insureVisitParam);

    /**
     * 根据就诊凭证类型和就诊凭证编号获取医保就诊信息
     * @param insureVisitParam
     * @Author 医保开发二部-湛康
     * @Date 2022-06-16 16:32
     * @return cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO
     */
    InsureIndividualVisitDTO getInsureIndividualVisitByMdtrtCertNo(Map<String, Object> insureVisitParam);

    /***
     * 根据visitId集合查询医保登记信息
     * @param paramMap
     * @return
     */
    List<InptVisitDTO> queryInsureIndividualVisits(Map<String, Object> paramMap);
}
