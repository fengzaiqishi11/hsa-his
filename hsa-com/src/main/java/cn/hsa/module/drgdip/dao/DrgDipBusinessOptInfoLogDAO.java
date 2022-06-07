package cn.hsa.module.drgdip.dao;

import cn.hsa.module.drgdip.entity.DrgDipBusinessOptInfoLogDO;

/**
 * @Description: drg/dip质控业务操作日志表
 * @author： 医保开发二部-湛康
 * @date： 2022-06-07 08:41:51
 */
public interface DrgDipBusinessOptInfoLogDAO {

    /**
     * @Author 医保开发二部-湛康
     * @Date 2020-07-17 10:09
     * @Description 新增
     * @param
     * @return  int
     */
    int insertDrgDipBusinessOptInfoLog(DrgDipBusinessOptInfoLogDO drgDipBusinessOptInfoLogDO);

    /**
     * @Author 医保开发二部-湛康
     * @Date 2020-07-17 10:09
     * @Description 根据主键删除
     * @param
     * @return int
     */
     int deleteById(DrgDipBusinessOptInfoLogDO drgDipBusinessOptInfoLogDO);

    /**
     * @Author 医保开发二部-湛康
     * @Date 2020-07-17 10:09
     * @Description 根据主键修改
     * @param
     * @return int
     */
    int updateById(DrgDipBusinessOptInfoLogDO drgDipBusinessOptInfoLogDO);

    /**
     * @Author 医保开发二部-湛康
     * @Date 2020-07-17 10:09
     * @Description 根据主键查询
     * @param
     * @return int
     */
     DrgDipBusinessOptInfoLogDO selectById(DrgDipBusinessOptInfoLogDO drgDipBusinessOptInfoLogDO);
}
