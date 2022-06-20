package cn.hsa.module.outpt.fees.dao;

import cn.hsa.module.outpt.fees.entity.PayOnlineInfoDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: 线上支付信息推送表：
 * @author： 医保开发二部-湛康
 * @date： 2022-06-20 09:25:28
 */
@Mapper
public interface PayOnlineInfoDAO {

    /**
     * @Author 医保开发二部-湛康
     * @Date 2020-07-17 10:09
     * @Description 新增
     * @param PayOnlineInfoDO
     * @return  int
     */
    int insertPayOnlineInfo(PayOnlineInfoDO payOnlineInfoDO);

    /**
     * @Author 医保开发二部-湛康
     * @Date 2020-07-17 10:09
     * @Description 根据主键删除
     * @param PayOnlineInfoDO
     * @return int
     */
     int deleteById(PayOnlineInfoDO payOnlineInfoDO);

    /**
     * @Author 医保开发二部-湛康
     * @Date 2020-07-17 10:09
     * @Description 根据主键修改
     * @param PayOnlineInfoDO
     * @return int
     */
    int updateById(PayOnlineInfoDO payOnlineInfoDO);

    /**
     * @Author 医保开发二部-湛康
     * @Date 2020-07-17 10:09
     * @Description 根据主键查询
     * @param PayOnlineInfoDO
     * @return int
     */
     PayOnlineInfoDO selectById(PayOnlineInfoDO payOnlineInfoDO);
}
