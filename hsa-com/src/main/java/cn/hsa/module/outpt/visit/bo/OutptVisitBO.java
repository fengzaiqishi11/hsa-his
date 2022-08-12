package cn.hsa.module.outpt.visit.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.inpt.doctor.dto.OutptCostDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.fees.entity.OutptSettleDO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.visit.bo
 * @Class_name: OutptVisitBO
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/9/3 16:16
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface OutptVisitBO {
    /**
     * @Method queryVisitRecords
     * @Desrciption
     * @Param
     * [outptVisitDTO]
     * @Author liaojunjie
     * @Date   2020/9/25 9:04
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryVisitRecords(OutptVisitDTO outptVisitDTO);

    /**
     * @Method updateTranInCode
     * @Desrciption
     * @Param
     * [outptVisitDTO]
     * @Author liaojunjie
     * @Date   2020/9/25 9:04
     * @Return java.lang.Boolean
     **/
    Boolean updateTranInCode(OutptVisitDTO outptVisitDTO);

    /**
     * @Menthod queryByVisitID
     * @Desrciption 根据ID查询门诊患者信息
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/12/17 21:54 
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
     * @param outptSettleDO
     * @Method updateOutptAcctPay
     * @Desrciption 修改
     * @Param
     * @Author fuhui
     * @Date 2021/12/15 11:16
     * @Return
     */
    boolean updateOutptAcctPay(OutptSettleDO outptSettleDO);

    /**
     * @param map
     * @Method selectOutptSettleById
     * @Desrciption 根据就诊id查询门诊结算信息
     * @Param
     * @Author fuhui
     * @Date 2021/12/13 16:34
     * @Return
     */
    OutptSettleDTO selectOutptSettleById(Map<String, Object> map);




    /**
     * @param map
     * @Method selectOutptVisitById
     * @Desrciption 根据就诊id查询门诊患者
     * @Param
     * @Author fuhui
     * @Date 2021/12/13 16:27
     * @Return
     */
    OutptVisitDTO selectOutptVisitById(Map<String, Object> map);

    List<OutptVisitDTO> queryOutptVisitSelfFeePatient(Map<String, String> param);

    List<OutptCostDTO> queryOutptCostByvisitIds(Map<String, Object> reqMap);

    Boolean updateUplod(OutptVisitDTO outptVisitDTO);

    Boolean updateOutptVisitUploadFlag(OutptVisitDTO outptVisitDTO);

    /**
     * @param outptVisitDTO
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.outpt.visit.dto.OutptVisitDTO>
     * @method selectOutptVisitByCertNo
     * @author wang'qiao
     * @date 2022/6/21 21:29
     * @description 根据证件类型和证件号码 查询信息
     **/
    OutptVisitDTO selectOutptVisitByCertNo(OutptVisitDTO outptVisitDTO);

    OutptVisitDTO queryInptVisitInfo(OutptVisitDTO outptVisitDTO);
    InsureIndividualVisitDTO queryInsureVisitInfo(OutptVisitDTO outptVisitDTO);
}
