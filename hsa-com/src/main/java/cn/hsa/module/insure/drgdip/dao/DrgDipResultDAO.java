package cn.hsa.module.insure.drgdip.dao;

import cn.hsa.module.insure.drgdip.dto.DrgDipResultDTO;
import cn.hsa.module.insure.drgdip.dto.DrgDipResultDetailDTO;
import cn.hsa.module.insure.drgdip.entity.DrgDipResultDO;

import java.util.List;

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
     * @param
     * @return  int
     */
    int insertDrgDipResult(DrgDipResultDTO drgDipResultDTO);

    /**
     * @Author 医保开发二部-湛康
     * @Date 2020-07-17 10:09
     * @Description 根据主键删除
     * @param
     * @return int
     */
     int deleteById(DrgDipResultDO drgDipResultDO);

    /**
     * @Author 医保开发二部-湛康
     * @Date 2020-07-17 10:09
     * @Description 根据主键修改
     * @param
     * @return int
     */
    int updateById(DrgDipResultDO drgDipResultDO);

    /**
     * @Author 医保开发二部-湛康
     * @Date 2020-07-17 10:09
     * @Description 根据主键修改
     * @param
     * @return int
     */
    int updateByVisitId(DrgDipResultDTO drgDipResultDTO);

    /**
     * @Author 医保开发二部-湛康
     * @Date 2020-07-17 10:09
     * @Description 根据主键查询
     * @param
     * @return int
     */
     DrgDipResultDO selectById(DrgDipResultDO drgDipResultDO);

     /**
      * 根据就诊ID倒叙查询DIP/DRG质控结果
      * @param result
      * @Author 医保开发二部-湛康
      * @Date 2022-06-07 9:38
      * @return cn.hsa.module.drgdip.entity.DrgDipResultDO
      */
     List<DrgDipResultDO> queryListByVisitIdDesc(DrgDipResultDTO result);

     /**
      * 质控结果-结算清单
      * @param result
      * @Author lhm
      * @Date 2022-06-07 9:38
      * @return cn.hsa.module.drgdip.entity.DrgDipResultDO
      */
     List<DrgDipResultDTO> queryDrgDipResultSetlinfo(DrgDipResultDTO result);

     /**
      * 质控结果-病案首页
      * @param result
      * @Author lhm
      * @Date 2022-06-07 9:38
      * @return cn.hsa.module.drgdip.entity.DrgDipResultDO
      */
     List<DrgDipResultDTO> queryDrgDipResultMris(DrgDipResultDTO result);

     /**
      * 质控结果查询-详细
      * @param result
      * @Author lhm
      * @Date 2022-06-07 9:38
      * @return cn.hsa.module.drgdip.entity.DrgDipResultDO
      */
     DrgDipResultDTO queryDrgDipResultByVisitId(DrgDipResultDTO result);

     /**
      * 质控结果查询-详细明细
      * @param result
      * @Author lhm
      * @Date 2022-06-07 9:38
      * @return cn.hsa.module.drgdip.entity.DrgDipResultDO
      */
     List<DrgDipResultDetailDTO> queryDrgDipResultById(DrgDipResultDTO result);

     /**
      * 质控结果查询-出院病人
      * @param result
      * @Author lhm
      * @Date 2022-06-07 9:38
      * @return cn.hsa.module.drgdip.entity.DrgDipResultDO
      */
     int querySettle(DrgDipResultDTO result);

     /**
      * @Author 医保二部-张金平
      * @Date 2022-07-04 15:34
      * @Description 质控违规信息查询-病案首页
      * @param drgDipResultDTO
      * @return java.util.List<cn.hsa.module.insure.drgdip.dto.DrgDipResultDTO>
      */
    List<DrgDipResultDTO> queryDrgDipNoRegulationsMris(DrgDipResultDTO drgDipResultDTO);
    /**
     * @Author zhangjinping
     * @Date 2022-07-06 10:03
     * @Description 质控违规信息查询-结算清单
     * @param drgDipResultDTO
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     */
    List<DrgDipResultDTO> queryDrgDipNoRegulationSetlinfo(DrgDipResultDTO drgDipResultDTO);
}
