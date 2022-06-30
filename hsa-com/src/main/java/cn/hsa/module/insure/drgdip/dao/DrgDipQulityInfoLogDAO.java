package cn.hsa.module.insure.drgdip.dao;

import cn.hsa.module.insure.drgdip.entity.DrgDipQulityInfoLogDO;

/**
 * @Description: DIP/DRG质控信息日志表
 * @author： 医保开发二部-湛康
 * @date： 2022-06-07 08:41:51
 */
public interface DrgDipQulityInfoLogDAO  {

    /**
     * @Author 医保开发二部-湛康
     * @Date 2020-07-17 10:09
     * @Description 新增
     * @param DrgDipQulityInfoLogDO
     * @return  int
     */
    int insertDrgDipQulityInfoLog(DrgDipQulityInfoLogDO drgDipQulityInfoLogDO);

    /**
     * @Author 医保开发二部-湛康
     * @Date 2020-07-17 10:09
     * @Description 根据主键删除
     * @param DrgDipQulityInfoLogDO
     * @return int
     */
     int deleteById(DrgDipQulityInfoLogDO drgDipQulityInfoLogDO);

    /**
     * @Author 医保开发二部-湛康
     * @Date 2020-07-17 10:09
     * @Description 根据主键修改
     * @param DrgDipQulityInfoLogDO
     * @return int
     */
    int updateById(DrgDipQulityInfoLogDO drgDipQulityInfoLogDO);

    /**
     * @Author 医保开发二部-湛康
     * @Date 2020-07-17 10:09
     * @Description 根据主键查询
     * @param DrgDipQulityInfoLogDO
     * @return int
     */
     DrgDipQulityInfoLogDO selectById(DrgDipQulityInfoLogDO drgDipQulityInfoLogDO);
}
