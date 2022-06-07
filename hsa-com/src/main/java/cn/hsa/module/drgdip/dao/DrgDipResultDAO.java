package cn.hsa.module.drgdip.dao;

import cn.hsa.module.drgdip.entity.DrgDipResultDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: DIP/DRG质控结果表
 * @author： 医保开发二部-湛康
 * @date： 2022-06-07 08:41:51
 */
public interface DrgDipResultDAO {

    /**
     * @Author 医保开发二部-湛康
     * @Date 2020-07-17 10:09
     * @Description 新增
     * @param DrgDipResultDO
     * @return  int
     */
    int insertDrgDipResult(DrgDipResultDO drgDipResultDO);

    /**
     * @Author 医保开发二部-湛康
     * @Date 2020-07-17 10:09
     * @Description 根据主键删除
     * @param DrgDipResultDO
     * @return int
     */
     int deleteById(DrgDipResultDO drgDipResultDO);

    /**
     * @Author 医保开发二部-湛康
     * @Date 2020-07-17 10:09
     * @Description 根据主键修改
     * @param DrgDipResultDO
     * @return int
     */
    int updateById(DrgDipResultDO drgDipResultDO);

    /**
     * @Author 医保开发二部-湛康
     * @Date 2020-07-17 10:09
     * @Description 根据主键查询
     * @param DrgDipResultDO
     * @return int
     */
     DrgDipResultDO selectById(DrgDipResultDO drgDipResultDO);

     /**
      * 根据就诊ID倒叙查询DIP/DRG质控结果
      * @param visitId
      * @Author 医保开发二部-湛康
      * @Date 2022-06-07 9:38
      * @return cn.hsa.module.drgdip.entity.DrgDipResultDO
      */
     DrgDipResultDO selectListByVisitIdDesc(String visitId);
}
