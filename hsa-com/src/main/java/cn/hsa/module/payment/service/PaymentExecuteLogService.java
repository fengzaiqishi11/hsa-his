package cn.hsa.module.payment.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.payment.entity.PaymentExecuteLogDO;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Package_name: cn.hsa.module.payment.service
 * @Class_name: PaymentExecuteLogService
 * @Description: 诊间支付日志(PaymentOrder)服务接口
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2022/09/01 19:37
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-payment")
public interface PaymentExecuteLogService {

    /**@Menthod queryById
     * @description 通过ID查询单条日志信息
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @param id
     * @Date: 2022/09/01 19:39
     * @return WrapperResponse<PaymentExecuteLogDO>
     */
    WrapperResponse<PaymentExecuteLogDO> queryById(String id);

    /**@Menthod queryPaymentExecuteLogInfoPage
     * @description 分页查询日志信息
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @param paymentExecuteLogDO
     * @Date: 2022/09/01 19:40
     * @return WrapperResponse<PageDTO>
     */
    WrapperResponse<PageDTO> queryPaymentExecuteLogInfoPage(PaymentExecuteLogDO paymentExecuteLogDO);

    /**@Menthod insert
     * @description 新增日志信息
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @param paymentExecuteLogDO
     * @Date: 2022/09/01 19:41
     * @return WrapperResponse
     */
    WrapperResponse insert(PaymentExecuteLogDO paymentExecuteLogDO);

    /**@Menthod update
     * @description 更新日志信息
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @param paymentExecuteLogDO
     * @Date: 2022/09/01 19:44
     * @return WrapperResponse
     */
    WrapperResponse update(PaymentExecuteLogDO paymentExecuteLogDO);

    /**@Menthod deleteById
     * @description 更新日志信息
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @param id
     * @Date: 2022/09/01 19：44
     * @return WrapperResponse
     */
    WrapperResponse deleteById(String id);

}
