package cn.hsa.medical.payment.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.module.payment.bo.PaymentExecuteLogBO;
import cn.hsa.module.payment.dao.PaymentExecuteLogDAO;
import cn.hsa.module.payment.entity.PaymentExecuteLogDO;
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
public class PaymentExecuteLogBOImpl implements PaymentExecuteLogBO {

    @Resource
    PaymentExecuteLogDAO paymentExecuteLogDAO;

    /**@Menthod queryById
     * @Describe:通过ID查询诊间支付日志
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param id
     * @Date: 2022-09-01 19:14
     * @return PaymentExecuteLogDO
     */
    @Override
    public PaymentExecuteLogDO queryById(String id) {
        return paymentExecuteLogDAO.queryById(id);
    }

    /**@Menthod queryAllByLimit
     * @Describe:查询诊间支付日志信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param paymentExecuteLogDO
     * @Date: 2022-09-01 17:24
     * @return List<PaymentOrderDO>
     */
    @Override
    public PageDTO queryAllByLimit(PaymentExecuteLogDO paymentExecuteLogDO) {
        List<PaymentExecuteLogDO> paymentExecuteLogDOList =paymentExecuteLogDAO.queryAllByLimit(paymentExecuteLogDO);
        return PageDTO.of(paymentExecuteLogDOList);
    }

    /**@Menthod count
     * @Describe:查询诊间支付日志条数
     * @param paymentExecuteLogDO
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @Date: 2022-09-01 19:20
     * @return long
     */
    @Override
    public long count(PaymentExecuteLogDO paymentExecuteLogDO) {
        return paymentExecuteLogDAO.count(paymentExecuteLogDO);
    }

    /**@Menthod insert
     * @Describe:新增诊间支付日志信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param paymentExecuteLogDO
     * @Date: 2022-09-01 17:24
     * @return Boolean
     */
    @Override
    public Boolean insert(PaymentExecuteLogDO paymentExecuteLogDO) {
        return paymentExecuteLogDAO.insert(paymentExecuteLogDO)>0;
    }

    /**@Menthod insertBatch
     * @Describe:批量新增诊间支付日志信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param entities
     * @Date: 2022-09-01 19:25
     * @return Boolean
     */
    @Override
    public Boolean insertBatch(List<PaymentExecuteLogDO> entities) {
        return paymentExecuteLogDAO.insertBatch(entities)>0;
    }

    /**@Menthod update
     * @Describe:更新支付订单信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param paymentExecuteLogDO
     * @Date: 2022-09-01 19:17
     * @return Boolean
     */
    @Override
    public Boolean update(PaymentExecuteLogDO paymentExecuteLogDO) {
        return paymentExecuteLogDAO.update(paymentExecuteLogDO)>0;
    }

    /**@Menthod deleteById
     * @Describe:删除订单信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param id
     * @Date: 2022-09-01 19:18
     * @return Boolean
     */
    @Override
    public Boolean deleteById(String id) {
        return paymentExecuteLogDAO.deleteById(id)>0;
    }
}
