package cn.hsa.module.payment.service;

import cn.hsa.module.payment.dto.PaymentSettleDTO;
import cn.hsa.module.payment.entity.PaymentSettleDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.payment.service
 * @Class_name: PaymentExecuteLogService
 * @Description: 诊间支付结算表(PaymentSettle)表服务接口
 * @Author: liuliyun
 * @Email: liyun.liu@powersi.com
 * @Date: 2022/09/05 10:34
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-payment")
public interface PaymentSettleService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PaymentSettleDO queryById(String id);

    /**
     * 分页查询
     *
     * @param paymentSettle 筛选条件
     * @param pageRequest   分页对象
     * @return 查询结果
     */
    Page<PaymentSettleDO> queryByPage(PaymentSettleDO paymentSettle, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param paymentSettle 实例对象
     * @return 实例对象
     */
    PaymentSettleDO insert(PaymentSettleDO paymentSettle);

    /**@Menthod updatePaymentSettleInfo
     * @description 根据医院编码、就诊id、结算id 修改诊间支付结算状态
     * @param param
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/09/05 11：07
     * @return PaymentSettleDTO
     */
    @PostMapping("/service/payment/paymentSettle/updatePaymentSettleInfo")
    Boolean updatePaymentSettleInfo(Map param);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    /**@Menthod quyeryPaymentInfoByCondition
     * @description 根据医院编码、就诊id、结算id 查询诊间支付结算信息
     * @param param
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/09/05 10:23
     * @return PaymentSettleDTO
     */
    @GetMapping("/service/payment/paymentSettle/quyeryPaymentInfoByCondition")
    PaymentSettleDTO quyeryPaymentInfoByCondition(Map<String, Object> param);

    /**@Menthod updatePaymentSettleInfo
     * @description 调用第三方支付平台支付
     * @param param
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/09/05 11:33
     * @return map
     */
    @PostMapping("/service/payment/paymentSettle/updatePaymentSettleStatus")
    Map updatePaymentSettleStatus(Map<String, Object> param);

}
