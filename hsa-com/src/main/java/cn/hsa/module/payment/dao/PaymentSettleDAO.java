package cn.hsa.module.payment.dao;

import cn.hsa.module.payment.dto.PaymentSettleDTO;
import cn.hsa.module.payment.entity.PaymentSettleDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.payment.dao
 * @Class_name: PaymentSettleDAO
 * @Describe(描述): 诊间支付结算Dao
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2022-09-01 11：25
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface PaymentSettleDAO {

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
     * @Describe:查询诊间支付结算列表
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param PaymentSettleDO 主键
     * @Date: 2022-09-01 11：27
     * @return List<PaymentSettleDO>
     */
    List<PaymentSettleDO> queryAllPaymentSettle(PaymentSettleDO PaymentSettleDO);

    /**@Menthod insert
     * @Describe:新增诊间支付结算信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param PaymentSettleDO
     * @Date: 2022-09-01 11：30
     * @return int
     */
    int insert(PaymentSettleDO PaymentSettleDO);

    /**@Menthod insertBatch
     * @Describe:批量新增诊间支付结算信息
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param entities
     * @Date: 2022-09-01 11：32
     * @return int
     */
    int insertBatch(@Param("entities") List<PaymentSettleDO> entities);

    /**@Menthod update
     * @Describe:修改诊间支付结算状态
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param PaymentSettleDO
     * @Date: 2022-09-01 11：35
     * @return int
     */
    int update(PaymentSettleDO PaymentSettleDO);

    /**@Menthod deleteById
     * @Describe:删除诊间支付数据
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param id
     * @Date: 2022-09-01 11：37
     * @return int
     */
    int deleteById(String id);

    /**@Menthod quyeryPaymentInfoByCondition
     * @description 根据医院编码、就诊id、结算id 查询诊间支付结算信息
     * @param paymentSettleDTO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/09/05 10:23
     * @return PaymentSettleDTO
     */
    PaymentSettleDTO quyeryPaymentInfoByCondition(PaymentSettleDTO paymentSettleDTO);

    /**@Menthod queryPaymentBillList
     * @Describe: 查询诊间支付对账数据
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param paymentSettleDTO 主键
     * @Date: 2022-09-15 11:52
     * @return List<PaymentSettleDO>
     */
    List<Map<String, Object>> queryPaymentBillList(PaymentSettleDTO paymentSettleDTO);

    /**@Menthod queryPaymentBillDetailList
     * @Describe: 查询诊间支付对账明细数据
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param paymentSettleDTO 主键
     * @Date: 2022-09-15 14:57
     * @return List<PaymentSettleDO>
     */
    List<Map<String, Object>> queryPaymentBillDetailList(PaymentSettleDTO paymentSettleDTO);

    /**@Menthod queryPaymentBillInfo
     * @Describe: 查询诊间支付对账数据(一种支付方式)
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param paymentSettleDTO 主键
     * @Date: 2022-09-16 10:40
     * @return Map<String, Object>
     */
    Map<String, Object> queryPaymentBillInfo(PaymentSettleDTO paymentSettleDTO);

    /**
     * @Menthod delOutptPaymentSettleByParam
     * @Desrciption 根据条件删除诊间支付试算数据
     * @param param 请求参数
     * @Author liuliyun
     * @Date 2022/9/19 09:25
     * @Return int 受影响行数
     */
    int delOutptPaymentSettleByParam(Map<String,String> param);

}

