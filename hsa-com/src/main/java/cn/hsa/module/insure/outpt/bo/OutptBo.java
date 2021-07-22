package cn.hsa.module.insure.outpt.bo;

import cn.hsa.module.insure.module.dto.InsureOutptOutFeeDTO;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.outpt.bo
 * @Class_name: OutptBo
 * @Describe(描述): 门诊医保统一开放调用 Bo 接口
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/09 9:26
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptBo {

    /**
     * @Method getAdvicePateintInfo
     * @Desrciption 退费获取门诊业务信息
     * @params [param]
     * @Author 廖继广
     * @Date 2020/11/08 16:54
     * @Return java.uti.Map
     **/
    Map<String,Object> getInsureOutptOutFeeInfo(Map<String,Object> map);

    /**
     * @Menthod getOutptVisitInfo
     * @Desrciption 门诊获取医保个人信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/10 14:33
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    Map<String,Object> getOutptVisitInfo(Map<String, Object> param);

    /**
     * @Menthod setOutptCostUpload
     * @Desrciption 门诊费用上传并试算
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/26 10:02
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    Map<String,Object> setOutptCostUploadAndTrial(Map<String,Object> param);

    /**
     * @Menthod setOutptCostUploadAndSettlement
     * @Desrciption 门诊费用上传并结算
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/26 10:07
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
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
    Map<String, Object> selectCheckInfo(Map<String, Object> map);

    /**
     * @Desrciption  uploadCheckInfo 医院审批信息上报
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/2/1 19:28
     * @Return boolean
     **/
    Boolean uploadCheckInfo(Map<String, Object> map);

    /**
     * @param map
     * @Desrciption uploadCheckInfo 门特病人取消结算以后 取消登记
     * @Param map
     * @Author fuhui
     * @Date 2021/2/1 19:28
     * @Return boolean
     */
    Boolean cancelRegister(Map<String, Object> map);
}
