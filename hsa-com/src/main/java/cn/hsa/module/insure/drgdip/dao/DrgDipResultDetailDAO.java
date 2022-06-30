package cn.hsa.module.insure.drgdip.dao;

import cn.hsa.module.insure.drgdip.dto.DrgDipResultDetailDTO;
import cn.hsa.module.insure.drgdip.entity.DrgDipResultDetailDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    int insertDrgDipResultDetail(DrgDipResultDetailDTO drgDipResultDetailDTO);

    /**
     * @Author 医保开发二部-湛康
     * @Date 2020-07-17 10:09
     * @Description 新增
     * @param drgDipResultDetailDO
     * @return  int
     */
    int insertDrgDipResultDetailList(@Param("list")List<DrgDipResultDetailDTO> drgDipResultDetailDTOList);

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

     /**
      * 根据质控id获取质控详细信息
      * @param id
      * @Author 医保开发二部-湛康
      * @Date 2022-06-07 9:52
      * @return java.util.List<cn.hsa.module.insure.drgdip.entity.DrgDipResultDetailDO>
      */
     List<DrgDipResultDetailDO> selectListByVisitId(String id);
}
