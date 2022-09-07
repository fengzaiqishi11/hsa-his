package cn.hsa.module.payment.dao;

import cn.hsa.module.payment.entity.PaymentOrderDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.payment.dao
 * @Class_name: PaymentExecuteLogDAO
 * @Describe(描述): 诊间支付订单Dao
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2022-09-01 15:06
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface PaymentOrderDAO {

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
    List<PaymentOrderDO> queryAllPaymentOrderInfo(PaymentOrderDO paymentOrderDO);

    /**@Menthod insert
     * @Describe:新增支付订单信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param paymentOrderDO 主键
     * @Date: 2022-09-01 16:25
     * @return int
     */
    int insert(PaymentOrderDO paymentOrderDO);

    /**@Menthod insertBatch
     * @Describe:批量新增支付订单信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param entities 主键
     * @Date: 2022-09-01 16:25
     * @return int
     */
    int insertBatch(@Param("entities") List<PaymentOrderDO> entities);

    /**@Menthod update
     * @Describe:更新支付订单信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param paymentOrderDO 主键
     * @Date: 2022-09-01 16:25
     * @return int
     */
    int update(PaymentOrderDO paymentOrderDO);

    /**@Menthod deleteById
     * @Describe:删除订单信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param id 主键
     * @Date: 2022-09-01 17：08
     * @return int
     */
    int deleteById(String id);

    /**@Menthod updatePaymentOrder
     * @Describe:更新诊间支付订单状态及更新时间
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param paymentOrderDO 主键
     * @Date: 2022-09-05 14:01
     * @return int
     */
    int updatePaymentOrder(PaymentOrderDO paymentOrderDO);

}

