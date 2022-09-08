package cn.hsa.module.payment.service;

import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.fees.service
 * @Class_name: OutptPaymentService
 * @Describe(描述):诊间支付Service接口
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2022/08/30 19:19
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-payment")
public interface OutptPaymentService {

    /**@Method checkOutptPhonePayAuthority
     * @Author liuliyun
     * @Description 诊间支付权限校验
     * @Date 2022/08/30 19:17
     * @Param [map]
     * @return Boolean
     **/
    Boolean checkOutptPhonePayAuthority(Map param);

    /**@Method checkOutptPrescriptionPayAuthority
     * @Author liuliyun
     * @Description 诊间支付(扫处方二维码)权限校验
     * @Date 2022/08/30 19:26
     * @Param [map]
     * @return Boolean
     **/
    Boolean checkOutptPrescriptionPayAuthority(Map param);


}
