package cn.hsa.module.drgdip.dao;

import cn.hsa.module.drgdip.entity.DrgDipResultDetailDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: DIP/DRG质控信息结果明细表
 * @author： 医保开发二部-湛康
 * @date： 2022-06-07 08:41:51
 */
@Mapper
public interface DrgDipResultDetailDAO  {

    /**
     * @Author 医保开发二部-湛康
     * @Date 2020-07-17 10:09
     * @Description 新增
     * @param drgDipResultDetailDO
     * @return  int
     */
    int insertDrgDipResultDetail(DrgDipResultDetailDO drgDipResultDetailDO);

    /**
     * @Author 医保开发二部-湛康
     * @Date 2020-07-17 10:09
     * @Description 根据主键删除
     * @param drgDipResultDetailDO
     * @return int
     */
     int deleteById(DrgDipResultDetailDO drgDipResultDetailDO);

    /**
     * @Author 医保开发二部-湛康
     * @Date 2020-07-17 10:09
     * @Description 根据主键修改
     * @param drgDipResultDetailDO
     * @return int
     */
    int updateById(DrgDipResultDetailDO drgDipResultDetailDO);

    /**
     * @Author 医保开发二部-湛康
     * @Date 2020-07-17 10:09
     * @Description 根据主键查询
     * @param drgDipResultDetailDO
     * @return int
     */
     DrgDipResultDetailDO selectById(DrgDipResultDetailDO drgDipResultDetailDO);
}
