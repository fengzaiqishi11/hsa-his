package cn.hsa.module.insure.outpt.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.outpt.service
 * @Class_name: OutptService
 * @Describe(描述): 门诊医保统一开放调用 Service 接口
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/09 9:27
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-insure")
public interface OutptService {
    /**
     * @Method getInsureOutptOutFeeInfo
     * @Desrciption
     * 医保退费时提取门诊业务信息 - 返回医保中心数据
     * 远程调用号：bizh110103
     * @param param
     * @Author 廖继广
     * @Date   2020/10/29 09:56
     * @Return java.util.Map
     **/
    Map<String,Object> getInsureOutptOutFeeInfo(Map<String,Object> param);
    
    /**
     * @Menthod getOutptVisitInfo
     * @Desrciption 门诊获取医保个人信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/10 14:33 
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    @GetMapping("serive/insure/outpt/getOutptVisitInfo")
    WrapperResponse getOutptVisitInfo(Map<String,Object> param);

    /**
     * @Menthod setOutptCostUpload
     * @Desrciption 门诊费用上传并试算
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/26 10:02 
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    @GetMapping("serive/insure/outpt/setOutptCostUploadAndTrial")
    Map<String,Object> setOutptCostUploadAndTrial(Map<String,Object> param);
    
    /**
     * @Menthod setOutptCostUploadAndSettlement
     * @Desrciption 门诊费用上传并结算
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/26 10:07 
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    @GetMapping("serive/insure/outpt/setOutptCostUploadAndSettlement")
    Map<String,Object> setOutptCostUploadAndSettlement(Map<String,Object> param);

    /**
     * @Method selectCheckInfo
     * @Desrciption  读取审批信息
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/2/1 19:31
     * @Return map
     **/
    @GetMapping("serive/insure/outpt/selectCheckInfo")
    Map<String, Object> selectCheckInfo(Map<String, Object> map);

    /**
     * @Desrciption  uploadCheckInfo 医院审批信息上报
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/2/1 19:28
     * @Return boolean
     **/
    @GetMapping("serive/insure/outpt/uploadCheckInfo")
    Boolean uploadCheckInfo(Map<String, Object> map);

    /**
     * @param map
     * @Desrciption uploadCheckInfo 门特病人取消结算以后 取消登记
     * @Param map
     * @Author fuhui
     * @Date 2021/2/1 19:28
     * @Return boolean
     */
    @GetMapping("serive/insure/outpt/cancelRegister")
    Boolean cancelRegister(Map<String, Object> map);
}
