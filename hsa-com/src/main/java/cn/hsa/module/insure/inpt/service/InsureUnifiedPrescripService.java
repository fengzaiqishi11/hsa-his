package cn.hsa.module.insure.inpt.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.inpt.service
 * @class_name: InsureElecPrescripService
 * @Description: 处方中心服务
 * @Author: LiaoJiGuang
 * @Email: jiguang.liao@powersi.com
 * @Date: 2021/9/3 15:07
 * @Company: 创智和宇
 **/
@FeignClient(value = "hsa-insure")
public interface InsureUnifiedPrescripService {

    /**
     * @Method updatePrescripUpload_7101
     * @Desrciption 电子处方上传
     * @Param map
     * @Author LiaoJiGuang
     * @Date 2021/9/3 17:02
     * @Return
     **/
    WrapperResponse<Map<String, Object>> updatePrescripUpload_7101(Map<String, Object> map);

    /**
     * @Method updatePrescripAudit_7102
     * @Desrciption 处方审核结果反馈
     * @Param map
     * @Author LiaoJiGuang
     * @Date 2021/9/6 17:02
     * @Return
     **/
    WrapperResponse<Map<String, Object>> updatePrescripAudit_7102(Map<String, Object> map);

    /**
     * @Method updatePrescripAudit_7103
     * @Desrciption 处方购药结果反馈
     * @Param map
     * @Author LiaoJiGuang
     * @Date 2021/9/6 17:02
     * @Return
     **/
    WrapperResponse<Map<String, Object>> updatePrescripBuyMedicine_7103(Map<String, Object> map);

    /**
     * @Method updatePrescripRevoke_7104
     * @Desrciption 电子处方撤销
     * @Param map
     * @Author LiaoJiGuang
     * @Date 2021/9/6 17:02
     * @Return
     **/
    WrapperResponse<Map<String, Object>> updatePrescripRevoke_7104(Map<String, Object> map);

    /**
     * @Method updatePrescripPay_7105
     * @Desrciption 处方支付状态同步
     * @Param map
     * @Author LiaoJiGuang
     * @Date 2021/9/6 17:02
     * @Return
     **/
    WrapperResponse<Map<String, Object>> updatePrescripPay_7105(Map<String, Object> map);

}
