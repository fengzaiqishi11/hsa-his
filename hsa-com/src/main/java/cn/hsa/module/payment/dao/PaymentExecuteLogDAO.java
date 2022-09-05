package cn.hsa.module.payment.dao;

import cn.hsa.module.payment.entity.PaymentExecuteLogDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.payment.dao
 * @Class_name: PaymentExecuteLogDAO
 * @Describe(描述): 诊间支付日志Dao
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2022-08-31 14:10:34
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface PaymentExecuteLogDAO {

    /**@Menthod queryById
     * @Describe:通过ID查询单条数据
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @param id 主键
     * @Date: 2022-08-31 15:12
     * @return PaymentExecuteLogDO
     */
    PaymentExecuteLogDO queryById(String id);

    /**@Menthod queryAllByLimit
     * @Describe:查询诊间支付日志列表
     * @param PaymentExecuteLogDO
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @Date: 2022-08-31 15:12
     * @return List<PaymentExecuteLogDO>
     */
    List<PaymentExecuteLogDO> queryAllByLimit(PaymentExecuteLogDO PaymentExecuteLogDO);

    /**@Menthod count
     * @Describe:查询诊间支付日志条数
     * @param PaymentExecuteLogDO
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @Date: 2022-08-31 15:15
     * @return long
     */
    long count(PaymentExecuteLogDO PaymentExecuteLogDO);

    /**@Menthod insert
     * @Describe:新增日志
     * @param PaymentExecuteLogDO
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @Date: 2022-08-31 15:15
     * @return int
     */
    int insert(PaymentExecuteLogDO PaymentExecuteLogDO);

    /**@Menthod insertBatch
     * @Describe:批量新增
     * @param entities
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @Date: 2022-08-31 15:15
     * @return int
     */
    int insertBatch(@Param("entities") List<PaymentExecuteLogDO> entities);

    /**@Menthod update
     * @Describe:更新日志
     * @param PaymentExecuteLogDO
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @Date: 2022-08-31 15:18
     * @return int
     */
    int update(PaymentExecuteLogDO PaymentExecuteLogDO);

    /**@Menthod deleteById
     * @Describe:删除日志
     * @param id
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @Date: 2022-08-31 15:19
     * @return int
     */
    int deleteById(String id);

}

