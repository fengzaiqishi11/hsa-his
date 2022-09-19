package cn.hsa.module.outpt.fees.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.fees.dto.OutptPayDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.fees.service
 * @Class_name: OutptOutTmakePriceFormService
 * @Describe(描述):门诊退费Service接口
 * @Author: liaojiguang
 * @Eamil: jiguang.liao@powersi.com.cn
 * @Date: 2020/09/06 10:30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-outpt")
public interface OutptOutTmakePriceFormService {
    /**
     * @Menthod updateOutptOutFee
     * @Desrciption 门诊退费
     * @param param 请求参数
     * @Author liaojiguang
     * @Date 2020/9/06 10:46
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    @PutMapping(value = "/service/outpt/outTmakePriceForm/updateOutptOutFee")
    WrapperResponse updateOutptOutFee(Map param);

    /**
     * @Menthod updateOutptOutFee
     * @Desrciption 门诊退费-查询已完成结算的数据
     * @param param 请求参数
     * @Author liaojiguang
     * @Date 2020/9/06 10:46
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    @GetMapping(value = "/service/outpt/outTmakePriceForm/queryOutChargePage")
    WrapperResponse queryOutChargePage(Map param);

    /**
     * @Menthod queryOutptPrescribes
     * @Desrciption 门诊退费-获取个人处方信息
     * @param param 请求参数
     * @Author liaojiguang
     * @Date 2020/9/08 10:46
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    @GetMapping(value = "/service/outpt/outTmakePriceForm/queryOutptPrescribe")
    WrapperResponse queryOutptPrescribes(Map param);

    /**
     * @Menthod queryOutptPrescribe
     * @Desrciption 查询门诊处方详细信息
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
     * @param outptSettleDTO 查询条件
     * @Author liaojiguang
     * @Date 2020/10/21 13:34
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    WrapperResponse getInvoiceInfo(Map param);

    /**
     * @Menthod updateOutptOPharInfo
     * @Desrciption 门诊退费 - 判断是否已经完成发药或退药
     * @param param
     * @Author liaojiguang
     * @Date 2020/9/09 13:34
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    WrapperResponse updateOutptOPharInfo(Map params);

    /**
     * @Method updateOutptRegister
     * @Desrciption  医保统一支付平台：门诊挂号取消
     * @Param
     *
     * @Author fuhui
     * @Date   2021/5/8 8:37
     * @Return
     **/
    WrapperResponse<Boolean> updateOutptRegister(Map<String, Object> map);

    /**
     * @Method updateOutptRegister
     * @Desrciption  医保统一支付平台：门急诊诊疗记录【4301】
     * @Param id-就诊id
     *
     * @Author luoyong
     * @Date   2021/8/20 8:37
     * @Return
     **/
    WrapperResponse<Boolean> addOutptVisitRecordUpload(Map<String, Object> map);

    /**
     * @Menthod: addOperAndRescue
     * @Desrciption: 统一支付平台-急诊留观手术及抢救信息【4302】
     * @Param: visitId-就诊id
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-23 13:50
     * @Return:
     **/
    WrapperResponse<Boolean> addOperAndRescue(Map<String, Object> map);

    /**
     * @Menthod: getPayInfoByParams
     * @Desrciption: 获取支付信息
     * @Param: outptPayDTO
     * @Author: 廖继广
     * @Email: jiguang.liao@powersi.com.cn
     * @Date: 2021-10-13 13:44
     * @Return:
     **/
    WrapperResponse<OutptPayDTO> getPayInfoByParams(Map<String,Object> map);

    @PostMapping(value = "/service/outpt/outTmakePriceForm/saveBackCostWithOutpt")
    WrapperResponse<Boolean> saveBackCostWithOutpt(Map<String,Object> map);

    /**
     * @Menthod updateOutptOnlinePayOutFee
     * @Desrciption 门诊诊间支付退费
     * @param param 请求参数
     * @Author liuliyun
     * @Date 2022/09/19 10:11
     * @email liyun.liu@powersi.com
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    @PutMapping(value = "/service/outpt/outTmakePriceForm/updateOutptOnlinePayOutFee")
    WrapperResponse updateOutptOnlinePayOutFee(Map param);
}
