package cn.hsa.medical.payment.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.module.payment.bo.PaymentSettleBO;
import cn.hsa.module.payment.dao.PaymentSettleDAO;
import cn.hsa.module.payment.dto.PaymentSettleDTO;
import cn.hsa.module.payment.entity.PaymentSettleDO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.medical.payment.bo.impl
 * @class_name: PaymentOrderBOImpl
 * @Description: 诊间诊间支付结算传输层接口
 * @Author: liyun.liu
 * @Email:liyun.liu@powersi.com
 * @Date: 2022/09/01 14:21
 * @Company: 创智和宇
 **/
@Component
public class PaymentSettleBOImpl implements PaymentSettleBO {

    @Resource
    PaymentSettleDAO paymentSettleDAO;

    @Resource
    PaymentTransferBoImpl paymentTransferBo;

    /**@Menthod queryById
     * @Describe:通过ID查询单条数据
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param id
     * @Date: 2022-09-01 19:10
     * @return PaymentOrderDO
     */
    @Override
    public PaymentSettleDO queryById(String id) {
        return paymentSettleDAO.queryById(id);
    }

    /**@Menthod queryAllPaymentSettle
     * @Describe:查询诊间支付结算信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param paymentSettleDO 主键
     * @Date: 2022-09-01 19:10
     * @return List<PaymentOrderDO>
     */
    @Override
    public PageDTO queryAllPaymentSettle(PaymentSettleDO paymentSettleDO) {
        List<PaymentSettleDO> paymentSettleDOList =paymentSettleDAO.queryAllPaymentSettle(paymentSettleDO);
        return PageDTO.of(paymentSettleDOList);
    }

    /**@Menthod insert
     * @Describe:新增诊间支付结算信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param paymentOrderDO 主键
     * @Date: 2022-09-01 19:10
     * @return Boolean
     */
    @Override
    public Boolean insert(PaymentSettleDO paymentOrderDO) {
        return paymentSettleDAO.insert(paymentOrderDO)>0;
    }

    /**@Menthod insertBatch
     * @Describe:批量新增诊间支付结算信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param entities
     * @Date: 2022-09-01 19:08
     * @return Boolean
     */
    @Override
    public Boolean insertBatch(List<PaymentSettleDO> entities) {
        return paymentSettleDAO.insertBatch(entities)>0;
    }

    /**@Menthod update
     * @Describe:更新诊间支付结算信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param paymentSettleDO
     * @Date: 2022-09-01 19:07
     * @return Boolean
     */
    @Override
    public Boolean update(PaymentSettleDO paymentSettleDO) {
        return paymentSettleDAO.update(paymentSettleDO)>0;
    }

    /**@Menthod deleteById
     * @Describe:删除诊间支付结算信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param id
     * @Date: 2022-09-01 19:06
     * @return Boolean
     */
    @Override
    public Boolean deleteById(String id) {
        return paymentSettleDAO.deleteById(id)>0;
    }


    /**@Menthod quyeryPaymentInfoByCondition
     * @description 根据医院编码、就诊id、结算id 查询诊间支付结算信息
     * @param paymentSettleDTO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/09/05 10:37
     * @return PaymentSettleDTO
     */
    @Override
    public PaymentSettleDTO quyeryPaymentInfoByCondition(PaymentSettleDTO paymentSettleDTO) {
        return paymentSettleDAO.quyeryPaymentInfoByCondition(paymentSettleDTO);
    }


    /**@Menthod updatePaymentSettleStatus
     * @description 调用支付平台支付
     * @param param
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/09/05 11:46
     * @return Map
     */
    @Override
    public Map updatePaymentSettleStatus(Map param) {
        /*todo 调用支付平台支付*/
        return paymentTransferBo.transferPayment();
    }
}
