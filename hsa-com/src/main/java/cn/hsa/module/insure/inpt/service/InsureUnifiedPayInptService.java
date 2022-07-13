package cn.hsa.module.insure.inpt.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.inpt.service
 * @class_name: InsureUnifiedPayInptBO
 * @Description: 医保统一支付平台：住院业务模块服务层创建
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/2/10 9:05
 * @Company: 创智和宇
 **/
@FeignClient(value = "hsa-insure")
public interface InsureUnifiedPayInptService {


    /**
     * @Author pengbo
     * @Description：住院管理-入院办理
     * @Date 17:05 2021-02-20
     * @Param
     * @return
     **/
    Map<String, Object> UP_2401(Map<String,Object> map);

    /**
     * @Author pengbo
     * @Description：住院管理-出院办理
     * @Date 2021-02-20 17:11
     * @Param
     * @return
     **/
    Map<String, Object> UP_2402(Map<String,Object> map);
    /**
     * @Author pengbo
     * @Description：住院管理-住院信息变更
     * @Date 2021-02-20 17:11
     * @Param
     * @return
     **/
    WrapperResponse<Boolean> UP_2403(Map<String,Object> map);
    /**
     * @Author pengbo
     * @Description：住院管理-入院办理撤销
     * @Date 2021-02-20 17:11
     * @Param
     * @return
     **/
    Map<String, Object> UP_2404(Map<String,Object> map);
    /**
     * @Author pengbo
     * @Description：住院管理-出院办理撤销
     * @Date 2021-02-20 17:11
     * @Param
     * @return
     **/
    Map<String, Object> UP_2405(Map<String,Object> map);

    /**
     * @param map
     * @Method saveInptFeeTransmit
     * @Desrciption 医保统一支付平台：住院业务模块--住院费用传输
     * @Param map
     * @Author fuhui
     * @Date 2021/2/10 9:52
     * @Return boolean
     */
    WrapperResponse<Map<String,Object>> UP_2301(Map<String,Object> map);

    /**
     * @Method UP_2302
     * @Desrciption  住院费用明细撤销
     * @Param insureUnifiedMap
     *
     * @Author fuhui
     * @Date   2021/2/24 14:43
     * @Return
    **/
    WrapperResponse<Boolean> UP_2302(Map<String, Object> insureUnifiedMap);

    /**
     * @Method UP_2303
     * @Desrciption  住院预结算
     * @Param unifiedMap
     *
     * @Author fuhui
     * @Date   2021/2/25 10:32
     * @Return map
    **/
    WrapperResponse<Map<String, String>> UP_2303(Map<String, Object> unifiedMap);

    /**
     * @Method UP_2304
     * @Desrciption  住院结算
     * @Param settleMap
     *
     * @Author fuhui
     * @Date   2021/2/25 11:04
     * @Return map
    **/
    WrapperResponse<Map<String, String>> UP_2304(Map<String, Object> settleMap);
    
    /**
     * @Menthod: UP_4602
     * @Desrciption: 统一支付平台-护理操作生命体征测量记录【4602】
     * @Param: visitId-就诊id
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-21 16:22
     * @Return: 
     **/

    /**
     * @Menthod: UP_2305
     * @Desrciption: 统一支付平台-住院结算撤销
     * @Param: 节点标识-flag-节点标识：data
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-02-10 11:00
     * @Return:
     **/
    Map<String,Object> UP_2305(Map<String,Object> insureUnifiedMap);

    WrapperResponse<Map<String, Object>> UP_4602(Map<String, Object> map);

    /**
     * 【2001】人员待遇享受检查
     * @param insureUnifiedMap
     * @Author 医保开发二部-湛康
     * @Date 2022-07-13 8:57
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    Map<String,Object> UP_2001(Map<String,Object> insureUnifiedMap);
}
