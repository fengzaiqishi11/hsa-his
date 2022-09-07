package cn.hsa.medical.payment.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.module.payment.bo.PaymentOrderBO;
import cn.hsa.module.payment.dao.PaymentOrderDAO;
import cn.hsa.module.payment.entity.PaymentOrderDO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.medical.payment.bo.impl
 * @class_name: PaymentOrderBOImpl
 * @Description: 诊间支付订单传输层接口
 * @Author: liyun.liu
 * @Email:liyun.liu@powersi.com
 * @Date: 2022/09/01 14:21
 * @Company: 创智和宇
 **/
@Component
public class PaymentOrderBOImpl implements PaymentOrderBO {

    @Resource
    PaymentOrderDAO paymentOrderDAO;

    /**@Menthod queryById
     * @Describe:通过ID查询单条数据
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param id 主键
     * @Date: 2022-09-01 17:08
     * @return PaymentOrderDO
     */
    @Override
    public PaymentOrderDO queryById(String id) {
        return paymentOrderDAO.queryById(id);
    }

    /**@Menthod queryAllPaymentOrderInfo
     * @Describe:查询支付订单信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param paymentOrderDO 主键
     * @Date: 2022-09-01 17:24
     * @return List<PaymentOrderDO>
     */
    @Override
    public PageDTO queryAllPaymentOrderInfo(PaymentOrderDO paymentOrderDO) {
        List<PaymentOrderDO> paymentOrderDOS =paymentOrderDAO.queryAllPaymentOrderInfo(paymentOrderDO);
        return PageDTO.of(paymentOrderDOS);
    }

    /**@Menthod insert
     * @Describe:新增支付订单信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param paymentOrderDO 主键
     * @Date: 2022-09-01 17:24
     * @return Boolean
     */
    @Override
    public Boolean insert(PaymentOrderDO paymentOrderDO) {
        return paymentOrderDAO.insert(paymentOrderDO)>0;
    }

    /**@Menthod insertBatch
     * @Describe:批量新增支付订单信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param entities 主键
     * @Date: 2022-09-01 17:25
     * @return Boolean
     */
    @Override
    public Boolean insertBatch(List<PaymentOrderDO> entities) {
        return paymentOrderDAO.insertBatch(entities)>0;
    }

    /**@Menthod update
     * @Describe:更新支付订单信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param paymentOrderDO 主键
     * @Date: 2022-09-01 17:22
     * @return Boolean
     */
    @Override
    public Boolean update(PaymentOrderDO paymentOrderDO) {
        return paymentOrderDAO.update(paymentOrderDO)>0;
    }

    /**@Menthod deleteById
     * @Describe:删除订单信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param id 主键
     * @Date: 2022-09-01 17：21
     * @return Boolean
     */
    @Override
    public Boolean deleteById(String id) {
        return paymentOrderDAO.deleteById(id)>0;
    }


    /**@Menthod updatePaymentOrder
     * @Describe:更新诊间支付订单状态及更新时间
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param paymentOrderDO 主键
     * @Date: 2022-09-05 14:01
     * @return int
     */
    @Override
    public Boolean updatePaymentOrder(PaymentOrderDO paymentOrderDO) {
        return paymentOrderDAO.updatePaymentOrder(paymentOrderDO)>0;
    }
}
