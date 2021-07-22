package cn.hsa.module.insure.emd.bo;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.emd.bo
 * @Class_name: InptElectronicBillBO
 * @Describe(描述): 住院电子凭证入口BO
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/09 9:24
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InptElectronicBillBO {
    
    /**
     * @Menthod getPatientInfo
     * @Desrciption 住院电子凭证获取个人信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/3/4 15:00
     * @return: WrapperResponse
     */
    WrapperResponse getPatientInfo(Map<String,Object> param);

    /**
     * @Menthod addPatientCost
     * @Desrciption 住院电子凭证患者费用上传
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/3/4 15:18
     * @return: WrapperResponse
     */
    WrapperResponse addPatientCost(Map<String,Object> param);

    /**
     * @Menthod deletePatientCost
     * @Desrciption 住院电子凭证撤销费用明细上传
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/3/4 15:36
     * @return: WrapperResponse
     */
    WrapperResponse deletePatientCost(Map<String,Object> param);

    /**
     * @Menthod deletePatientCostPremium
     * @Desrciption 住院电子凭证费用退费
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/3/4 15:39
     * @return: WrapperResponse
     */
    WrapperResponse deletePatientCostPremium(Map<String,Object> param);

    /**
     * @Menthod getPayEffect
     * @Desrciption 住院电子凭证获取支付结果
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/3/4 15:53
     * @return: WrapperResponse
     */
    WrapperResponse getPayEffect(Map<String,Object> param);

}
