package cn.hsa.module.payment.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.fees.entity.OutptPayDO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.payment.dto.PaymentSettleDTO;
import cn.hsa.module.payment.entity.PaymentSettleDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.payment.bo
 * @Class_name: PaymentSettleBO
 * @Describe:
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2022-09-01 11:43
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface PaymentSettleBO {

    /**@Menthod queryById
     * @Describe:通过ID查询单条数据
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param id 主键
     * @Date: 2022-09-01 11：25
     * @return PaymentSettleDO
     */
    PaymentSettleDO queryById(String id);

    /**@Menthod queryAllPaymentSettle
     * @Describe: 查询诊间支付结算列表
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param paymentSettleDTO
     * @Date: 2022-09-01 11：27
     * @return PageDTO
     */
    PageDTO queryAllPaymentSettle(PaymentSettleDTO paymentSettleDTO);

    /**@Menthod insert
     * @Describe:新增诊间支付结算信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param PaymentSettleDO
     * @Date: 2022-09-01 11：30
     * @return Boolean
     */
    Boolean insert(PaymentSettleDO PaymentSettleDO);

    /**@Menthod insertBatch
     * @Describe:批量新增诊间支付结算信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param entities
     * @Date: 2022-09-01 11：32
     * @return Boolean
     */
    Boolean insertBatch(@Param("entities") List<PaymentSettleDO> entities);

    /**@Menthod update
     * @Describe:修改诊间支付结算状态
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param PaymentSettleDO
     * @Date: 2022-09-01 11：35
     * @return Boolean
     */
    Boolean update(PaymentSettleDO PaymentSettleDO);

    /**@Menthod deleteById
     * @Describe:删除诊间支付数据
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param id
     * @Date: 2022-09-01 11：37
     * @return Boolean
     */
    Boolean deleteById(String id);

    /**@Menthod quyeryPaymentInfoByCondition
     * @description 根据医院编码、就诊id、结算id 查询诊间支付结算信息
     * @param paymentSettleDTO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/09/05 10:23
     * @return PaymentSettleDTO
     */
    PaymentSettleDTO quyeryPaymentInfoByCondition(PaymentSettleDTO paymentSettleDTO);

    /**@Menthod updatePaymentSettleStatus
     * @description 调用支付平台支付
     * @param param
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/09/05 10:23
     * @return Map
     */
    Map updatePaymentSettleStatus(Map param);

    /**@Menthod saveSettleInfo
     * @description 生成预结算数据
     * @param outptVisitDTO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/09/05 16:44
     * @return Map
     */
    Boolean saveSettleInfo(OutptVisitDTO outptVisitDTO, OutptSettleDTO outptSettleDTO, List<OutptPayDO> outptPayDTOList);
}
