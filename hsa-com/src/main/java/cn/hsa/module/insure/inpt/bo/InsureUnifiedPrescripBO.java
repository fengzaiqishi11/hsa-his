package cn.hsa.module.insure.inpt.bo;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.inpt.bo
 * @class_name: InsureUnifiedRemoteBO
 * @Description: 医保统一支付平台：异地医药机构费用结算业务
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/4/23 12:40
 * @Company: 创智和宇
 **/
public interface InsureUnifiedPrescripBO {

    /**
     * @Method updatePrescripUpload_7101
     * @Desrciption 电子处方上传
     * @Param map
     * @Author LiaoJiGuang
     * @Date 2021/9/3 17:02
     * @Return
     **/
    Map<String, Object> updatePrescripUpload_7101(Map<String, Object> map);

    /**
     * @Method updatePrescripAudit_7102
     * @Desrciption 处方审核结果反馈
     * @Param map
     * @Author LiaoJiGuang
     * @Date 2021/9/6 17:02
     * @Return
     **/
    Map<String, Object> updatePrescripAudit_7102(Map<String, Object> map);

    /**
     * @Method updatePrescripAudit_7103
     * @Desrciption 处方购药结果反馈
     * @Param map
     * @Author LiaoJiGuang
     * @Date 2021/9/6 17:02
     * @Return
     **/
    Map<String, Object> updatePrescripBuyMedicine_7103(Map<String, Object> map);

    /**
     * @Method updatePrescripRevoke_7104
     * @Desrciption 电子处方撤销
     * @Param map
     * @Author LiaoJiGuang
     * @Date 2021/9/6 17:02
     * @Return
     **/
    Map<String, Object> updatePrescripRevoke_7104(Map<String, Object> map);

    /**
     * @Method updatePrescripPay_7105
     * @Desrciption 处方支付状态同步
     * @Param map
     * @Author LiaoJiGuang
     * @Date 2021/9/6 17:02
     * @Return
     **/
    Map<String, Object> updatePrescripPay_7105(Map<String, Object> map);

}
