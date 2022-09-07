package cn.hsa.module.payment.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.payment.entity.PaymentOrderDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.payment.service
 * @Class_name: PaymentOrderService
 * @Description: 诊间支付订单表(PaymentOrder)表服务接口
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2022/09/01 19:45
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-payment")
public interface PaymentOrderService {

     /**@Menthod queryById
      * @description 通过ID查询单条日志信息
      * @Author: liuliyun
      * @Email: liyun.liu@powersi.com
      * @param id
      * @Date: 2022/09/01 19:39
      * @return WrapperResponse<PaymentOrderDO>
      */
     WrapperResponse<PaymentOrderDO> queryById(String id);

     /**@Menthod queryPaymentExecuteLogInfoPage
      * @description 分页查询诊间支付订单信息
      * @Author: liuliyun
      * @Email: liyun.liu@powersi.com
      * @param paymentOrderDO
      * @Date: 2022/09/01 19:49
      * @return WrapperResponse<PageDTO>
      */
    WrapperResponse<PageDTO> queryAllPaymentOrderInfo(PaymentOrderDO paymentOrderDO);

     /**@Menthod insert
      * @description 新增诊间支付订单信息
      * @Author: liuliyun
      * @Email: liyun.liu@powersi.com
      * @param paymentOrderDO
      * @Date: 2022/09/01 19:49
      * @return WrapperResponse
      */
    WrapperResponse insert(PaymentOrderDO paymentOrderDO);

     /**@Menthod updatePaymentOrder
      * @description 更新诊间支付订单信息
      * @Author: liuliyun
      * @Email: liyun.liu@powersi.com
      * @param param
      * @Date: 2022/09/05 14:02
      * @return WrapperResponse
      */
    @PostMapping("/service/payment/paymentOrder/updatePaymentOrder")
    WrapperResponse updatePaymentOrder(Map param);

     /**@Menthod deleteById
      * @description 更新诊间支付订单信息
      * @Author: liuliyun
      * @Email: liyun.liu@powersi.com
      * @param id
      * @Date: 2022/09/01 20:35
      * @return WrapperResponse
      */
    WrapperResponse deleteById(String id);

}
