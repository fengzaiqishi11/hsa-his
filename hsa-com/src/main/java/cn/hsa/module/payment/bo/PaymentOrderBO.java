package cn.hsa.module.payment.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.payment.entity.PaymentOrderDO;
import cn.hsa.module.payment.entity.PaymentSettleDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.payment.bo
 * @Class_name: PaymentOrderBO
 * @Describe:
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2022-09-01 15:14
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface PaymentOrderBO {

    /**@Menthod queryById
     * @Describe:通过ID查询单条数据
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param id 主键
     * @Date: 2022-09-01 16:08
     * @return PaymentOrderDO
     */
    PaymentOrderDO queryById(String id);

    /**@Menthod queryAllPaymentOrderInfo
     * @Describe:查询支付订单信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param paymentOrderDO 主键
     * @Date: 2022-09-01 16:01
     * @return List<PaymentOrderDO>
     */
    PageDTO queryAllPaymentOrderInfo(PaymentOrderDO paymentOrderDO);

    /**@Menthod insert
     * @Describe:新增支付订单信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param paymentOrderDO 主键
     * @Date: 2022-09-01 16:25
     * @return Boolean
     */
    Boolean insert(PaymentOrderDO paymentOrderDO);

    /**@Menthod insertBatch
     * @Describe:批量新增支付订单信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param entities 主键
     * @Date: 2022-09-01 16:25
     * @return Boolean
     */
    Boolean insertBatch(@Param("entities") List<PaymentOrderDO> entities);

    /**@Menthod update
     * @Describe:更新支付订单信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param paymentOrderDO 主键
     * @Date: 2022-09-01 16:25
     * @return Boolean
     */
    Boolean update(PaymentOrderDO paymentOrderDO);

    /**@Menthod deleteById
     * @Describe:删除订单信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param id 主键
     * @Date: 2022-09-01 17：08
     * @return Boolean
     */
    Boolean deleteById(String id);

    /**@Menthod updatePaymentOrder
     * @description 更新诊间支付订单状态
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @param paymentOrderDO
     * @Date: 2022/09/05 14:02
     * @return WrapperResponse
     */
    Boolean updatePaymentOrder(PaymentOrderDO paymentOrderDO);
}
