package cn.hsa.module.payment.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
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
     * @param param   筛选条件
     * @author liuliyun
     * @date 2022-09-15 10:47
     * @email liyun.liu@powersi.com
     * @return 查询结果
     */
    WrapperResponse<PageDTO> queryByPage(Map<String,Object> param);

    /**@Menthod insert
     * @description 新增诊间支付结算信息
     * @param param
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/09/22 11:16
     * @return Boolean
     */
    @PostMapping("/service/payment/paymentSettle/insert")
    Boolean insert(Map<String,Object> param);

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

    /**
     * @Menthod queryPaymentSettle
     * @Desrciption 查询诊间支付结算数据
     * @param param
     * @Author liuliyun
     * @Date 2022/9/21 16:45
     * @Return PaymentSettleDTO
     */
    @GetMapping("/service/payment/paymentSettle/queryPaymentSettle")
    PaymentSettleDTO queryPaymentSettle(Map<String,Object> param);

}
