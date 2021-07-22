package cn.hsa.module.insure.inpt.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.inpt.service
 * @Class_name: InptService
 * @Describe(描述): 住院医保统一开放调用 Service 接口
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/09 9:25
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

@FeignClient(value = "hsa-insure")
public interface InptService {
    /**
     * @Method insertInptRegister
     * @Desrciption 入院登记
     * @params [param]
     * @Author 廖继广
     * @Date 2020/10/29 11:41
     * @Return java.uti.Map
     **/
    Map<String,Object> insertInptRegister(Map param);

    /**
     * @Method updateInptRegister
     * @Desrciption 修改入院登记
     * @params [param]
     * @Author 廖继广
     * @Date 2020/10/29 11:41
     * @Return Boolean
     **/
    Boolean updateInptRegister(Map param);

    /**
     * @Method deteleInptRegister
     * @Desrciption 取消入院登记
     * @params [param]
     * @Author 廖继广
     * @Date 2020/10/29 11:41
     * @Return Boolean
     **/
    Boolean deteleInptRegister (Map param);

    /**
     * @Method insertRemoteInptRegister
     * @Desrciption 异地入院登记
     * @params [param]
     * @Author 廖继广
     * @Date 2020/10/30 10:48
     * @Return java.uti.Map
     **/
    Map<String,Object> insertRemoteInptRegister(Map param);

    /**
     * @Method deteleRemoteInptRegister
     * @Desrciption 取消异地入院登记
     * @params [param]
     * @Author 廖继广
     * @Date 2020/10/30 14:34
     * @Return Boolean
     **/
    Boolean deteleRemoteInptRegister(Map param);

    /**
     * @Method udapteCanleInptSettle
     * @Desrciption 取消出院结算
     * @params [insureOutptOutFeeDTO]
     * @Author 廖继广
     * @Date 2020/10/29 11:41
     * @Return Boolean
     **/
    Boolean udapteCanleInptSettle(Map<String,Object> param);

    /**
     * @Method udapteCanleRemoteInptSettle
     * @Desrciption 取消异地出院结算
     * @params [insureRemoteInptOutFeeDTO]
     * @Author 廖继广
     * @Date 2020/10/29 11:41
     * @Return Boolean
     **/
    Boolean udapteCanleRemoteInptSettle(Map<String,Object> param);

    /**
     * @Menthod verifyAndCalculateCost
     * @Desrciption 校验并计算费用【住院】
     * @param param 请求参数【必填：医院编码、就诊id、医保机构编码】
     * @Author Ou·Mr
     * @Date 2020/11/30 15:30 
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    Map<String,String> verifyAndCalculateCost(Map<String,Object> param);

    /**
     * @Menthod inptSettlement
     * @Desrciption 住院结算
     * @param param 请求参数 必传值：hospCode:医院编码、visitId:就诊id、insureRegCode:医保编码
     * @Author Ou·Mr
     * @Date 2020/12/3 16:32 
     * @Return java.util.Map<java.lang.String,java.lang.String>
     */
    Map<String,String> inptSettlement(Map<String,Object> param);
    
    /**
     * @Menthod delInptCostTransmit
     * @Desrciption 删除医保住院已传输费用信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/22 16:36 
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    WrapperResponse delInptCostTransmit(Map<String,Object> param);

    /**
     * @Menthod getRemoteDiseaseInfo
     * @Desrciption 获取异地疾病信息
     * @param param 请求参数 必传值：hospCode:医院编码、insureRegCode:医保编码、
     * @Author Ljg
     * @Date 2021/4/13 15:32
     * @Return java.util.Map<java.lang.String,java.lang.String>
     */
    List<Map<String,Object>> getRemoteDiseaseInfo(Map<String,Object> param);
}
