package cn.hsa.module.insure.module.bo;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.entity.InsureIndividualVisitDO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.insureIndividualVisit.bo
 * @Class_name: InsureIndividualVisitBO
 * @Describe(描述): 医保就诊信息表 BO层
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/18 19:26
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InsureIndividualVisitBO {

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
     * @param insureIndividualVisitDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/11/29 15:57 
     * @Return java.util.List<cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO>
     */
    List<InsureIndividualVisitDTO> findByCondition(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @Menthod editInsureIndividualVisit
     * @Desrciption 编辑医保就诊信息
     * @param insureIndividualVisitDO 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/1 15:01 
     * @Return int 受影响行数
     */
    int editInsureIndividualVisit(InsureIndividualVisitDO insureIndividualVisitDO);

    /**
     * @Menthod insertIndividualVisit
     * @Desrciption 新增医保就诊信息
     * @param insureIndividualVisitDTO 请求参数
     * @Author 廖继广
     * @Date 2020/12/21 14:58
     * @Return Boolean
     */
    Boolean insertIndividualVisit(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @Menthod deleteByVisitId
     * @Desrciption 根据就诊id删除
     * @param visitId 请求参数
     * @Author 廖继广
     * @Date 2020/12/21 14:58
     * @Return Boolean
     */
    Boolean deleteByVisitId(String visitId);

    /**
     * @Method deletePatientSumInfo
     * @Desrciption 删除个人累计信息
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
	Integer insertPatientSumInfoAll(List<Map<String, Object>> resultDataMap);

	/**
     * @param insureVisitParam
     * @Method getInsureIndividualVisitById
     * @Desrciption 根据就诊id和医院编码查询医保就诊信息
     * @Param
     * @Author fuhui
     * @Date 2021/3/4 8:58
     * @Return
     */
    InsureIndividualVisitDTO getInsureIndividualVisitById(Map<String, Object> insureVisitParam);

    /**
     * @param map
     * @Method deleteInsureVisitById
     * @Desrciption 退费以后，取消门诊挂号登记
     * @Param
     * @Author fuhui
     * @Date 2021/3/16 19:50
     * @Return
     */
    Boolean deleteInsureVisitById(Map<String, Object> map);

    /**
     * @param inptVisitDTO
     * @Method updateInsureInidivdual
     * @Desrciption 修改医保病人信息
     * @Param
     * @Author fuhui
     * @Date 2021/5/20 22:41
     * @Return
     */
    Boolean updateInsureInidivdual(InptVisitDTO inptVisitDTO);

    /**
     * @param insureIndividualVisitDTO
     * @Method selectInsureInfo
     * @Desrciption 查询医保就诊信息
     * @Param
     * @Author fuhui
     * @Date 2021/7/30 15:32
     * @Return
     */
    InsureIndividualVisitDTO selectInsureInfo(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @Method updateInsureSettleId
     * @Desrciption  取消结算更新医保就诊表的结算id
     * @Param
     *
     * @Author fuhui
     * @Date   2021/8/4 10:18
     * @Return
     **/
    Boolean updateInsureSettleId(Map<String, Object> settleMap);

    /**
     * @param insureUnifiedPayParam
     * @Method queryAllInsureIndiviualVisit
     * @Desrciption 根据就诊id 查询医保就诊信息
     * @Param
     * @Author fuhui
     * @Date 2021/8/9 19:59
     * @Return
     */
    List<InsureIndividualVisitDTO> queryAllInsureIndiviualVisit(Map<String, Object> insureUnifiedPayParam);

    /**
     * @param insureUnifiedPayParam
     * @Method  selectMaxAndMinRegisterTime
     * @Desrciption
     * @Param
     * @Author fuhui
     * @Date 2021/8/9 20:56
     * @Return
     */
    InsureIndividualVisitDTO selectMaxAndMinRegisterTime(Map<String, Object> insureUnifiedPayParam);

    /**
     * @param inptVisitDTO
     * @Method selectHalfVisit
     * @Desrciption 查询出中途结算次数
     * @Param
     * @Author fuhui
     * @Date 2022/2/15 10:15
     * @Return
     */
    Integer selectHalfVisit(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @param insureIndividualVisitDTO
     * @Method updateInsureSettleCounts
     * @Desrciption 更新中途结算医保标志和次数
     * @Param
     * @Author fuhui
     * @Date 2022/2/16 8:55
     * @Return
     */
    void updateInsureSettleCounts(InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * 根据医保就诊ID查询就诊信息
     * @param insureVisitParam
     * @Author 医保开发二部-湛康
     * @Date 2022-05-07 15:10
     * @return cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO
     */
    InsureIndividualVisitDTO getInsureIndividualVisitByMedRegNo(Map<String, Object> insureVisitParam);

    /**
     * 根据就诊凭证类型和就诊凭证编号获取医保就诊信息
     * @param insureVisitParam
     * @Author 医保开发二部-湛康
     * @Date 2022-06-16 16:37
     * @return cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO
     */
    InsureIndividualVisitDTO getInsureIndividualVisitByMdtrtCertNo(Map<String, Object> insureVisitParam);

}
