package cn.hsa.module.payment.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.medic.data.dto.MedicalDataDTO;
import cn.hsa.module.medic.data.dto.MedicalDataDetailDTO;
import cn.hsa.module.payment.entity.PaymentExecuteLogDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.payment.bo
 * @Class_name: PaymentExecuteLogBO
 * @Describe:
 * @Author: liuliyun
 * @Eamil: liyun.liu@powersi.com
 * @Date: 2022-09-01 11:37
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface PaymentExecuteLogBO {

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
     * @return PageDTO
     */
    PageDTO queryAllByLimit(PaymentExecuteLogDO PaymentExecuteLogDO);

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
     * @return boolean
     */
    Boolean insert(PaymentExecuteLogDO PaymentExecuteLogDO);

    /**@Menthod insertBatch
     * @Describe:批量新增
     * @param entities
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @Date: 2022-08-31 15:15
     * @return boolean
     */
    Boolean insertBatch(@Param("entities") List<PaymentExecuteLogDO> entities);

    /**@Menthod update
     * @Describe:更新日志
     * @param PaymentExecuteLogDO
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @Date: 2022-08-31 15:18
     * @return boolean
     */
    Boolean update(PaymentExecuteLogDO PaymentExecuteLogDO);

    /**@Menthod deleteById
     * @Describe:删除日志
     * @param id
     * @Author: liuliyun
     * @Eamil: liyun.liu@powersi.com
     * @Date: 2022-08-31 15:19
     * @return boolean
     */
    Boolean deleteById(String id);

}