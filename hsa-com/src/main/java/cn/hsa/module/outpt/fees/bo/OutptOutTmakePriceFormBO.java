package cn.hsa.module.outpt.fees.bo;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.fees.dto.OutptPayDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.fees.bo
 * @Class_name: OutptTmakePriceFormBO
 * @Describe(描述):门诊划价收费BO层
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/08/25 10:37
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptOutTmakePriceFormBO {

    /**
     * @Menthod updateOutptOutFee
     * @Desrciption 门诊退费
     * @param outptVisitDTO,outptSettleDTO 请求参数
     * @Author liaojiguang
     * @Date 2020/9/06 10:46
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    WrapperResponse updateOutptOutFee(OutptVisitDTO outptVisitDTO, OutptSettleDTO outptSettleDTO, List<OutptPayDTO> outptPayDOList);

    /**
     * @Menthod queryOutChargePage
     * @Desrciption 门诊退费 查询已结算的患者信息
     * @param
     * @Author liaojiguang
     * @Date 2020/9/08 13:53
     * @Return PageDTOupda
     */
    WrapperResponse queryOutChargePage(OutptSettleDTO outptSettleDTO);

    /**
     * @Menthod queryOutptPrescribes
     * @Desrciption 门诊退费 查询费用详细信息
     * @param
     * @Author liaojiguang
     * @Date 2020/9/08 13:53
     * @Return List
     */
    WrapperResponse queryOutptPrescribes(Map param);

    /**
     * @Menthod queryOutptPrescribe
     * @Desrciption 查询门诊处方类别详细信息
     * @param param 查询条件
     * @Author liaojiguang
     * @Date 2020/9/09 13:34
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    WrapperResponse queryOutptPrescribe(Map param);

    /**
     * @Menthod getDiagnoseInfo
     * @Desrciption 查询门诊诊断信息
     * @param param 查询条件
     * @Author liaojiguang
     * @Date 2020/10/21 13:34
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    WrapperResponse getDiagnoseInfo(Map param);

    /**
     * @Menthod getInvoiceInfo
     * @Desrciption 获取发票信息
     * @param param 查询条件
     * @Author liaojiguang
     * @Date 2020/10/21 13:34
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    WrapperResponse getInvoiceInfo(Map param);

    /**
     * @Menthod updateOutptOPharInfo
     * @Desrciption 门诊退费 - 判断是否已经完成发药或退药
     * @param params
     * @Author liaojiguang
     * @Date 2020/9/09 13:34
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    WrapperResponse updateOutptOPharInfo(OutptVisitDTO outptVisitDTO, OutptSettleDTO outptSettleDTO);

    /**
     * @param map
     * @Method updateOutptRegister
     * @Desrciption 医保统一支付平台：门诊挂号取消
     * @Param
     * @Author fuhui
     * @Date 2021/5/8 8:37
     * @Return
     */
    Boolean updateOutptRegister(Map<String, Object> map);

    /**
     * @Method updateOutptRegister
     * @Desrciption  医保统一支付平台：门急诊诊疗记录【4301】
     * @Param id-就诊id
     *
     * @Author luoyong
     * @Date   2021/8/20 8:37
     * @Return
     **/
    Boolean addOutptVisitRecordUpload(Map<String, Object> map);

    /**
     * @Menthod: addOperAndRescue
     * @Desrciption: 统一支付平台-急诊留观手术及抢救信息【4302】
     * @Param: visitId-就诊id
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-23 13:50
     * @Return:
     **/
    Boolean addOperAndRescue(Map<String, Object> map);

    /**
     * @param outptPayDTO
     * @Menthod: getPayInfoByParams
     * @Desrciption: 获取支付信息
     * @Param: outptPayDTO
     * @Author: 廖继广
     * @Email: jiguang.liao@powersi.com.cn
     * @Date: 2021-10-13 13:44
     * @Return:
     */
    OutptPayDTO getPayInfoByParams(OutptPayDTO outptPayDTO);

    Boolean saveBackCostWithOutpt(Map<String, Object> map);

    /**
     * @Menthod saveOutptPaymentSettle
     * @Desrciption 诊间支付门诊退费
     * @param outptVisitDTO,outptSettleDTO,outptPayDOList 请求参数
     * @Author liuliyun
     * @Date 2022/09/06 09:36
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    WrapperResponse updateOutptOnlinePayOutFee(OutptVisitDTO outptVisitDTO, OutptSettleDTO outptSettleDTO, List<OutptPayDTO> outptPayDOList);
}
