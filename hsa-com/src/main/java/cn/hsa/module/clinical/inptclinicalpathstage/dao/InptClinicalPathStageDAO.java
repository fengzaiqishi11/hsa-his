package cn.hsa.module.clinical.inptclinicalpathstage.dao;

import cn.hsa.module.clinical.inptclinicalpathstage.dto.InptClinicalPathStageDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.clinical.clinicalpathlist.entity
 * @Class_name: ClinicPathListDO
 * @Describe: 病人阶段病情记录表数据接口
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/8 19:27
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface InptClinicalPathStageDAO {

  /**
  * @Menthod getInptClinicalPathStageById
  * @Desrciption 查询单个入径阶段病情
  *
  * @Param
  * [inptClinicalPathStageDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/28 11:06
  * @Return cn.hsa.module.clinical.inptclinicalpathstage.dto.InptClinicalPathStageDTO
  **/
  InptClinicalPathStageDTO getInptClinicalPathStageById(InptClinicalPathStageDTO inptClinicalPathStageDTO);

  /**
  * @Menthod queryInptClinicalPathStageAll
  * @Desrciption 查询入径阶段病情
  *
  * @Param
  * [inptClinicalPathStageDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/28 11:07
  * @Return java.util.List<cn.hsa.module.clinical.inptclinicalpathstage.dto.InptClinicalPathStageDTO>
  **/
  List<InptClinicalPathStageDTO> queryInptClinicalPathStageAll(InptClinicalPathStageDTO inptClinicalPathStageDTO);

  /**
  * @Menthod insertInptClinicalPathStage
  * @Desrciption 批量新增
  *
  * @Param
  * [inptClinicalPathStageDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/28 11:07
  * @Return int
  **/
  int insertInptClinicalPathStageList(List<InptClinicalPathStageDTO> inptClinicalPathStageDTOS);

  /**
  * @Menthod insertInptClinicalPathStage
  * @Desrciption 单个新增
  *
  * @Param
  * [inptClinicalPathStageDTOS]
  *
  * @Author jiahong.yang
  * @Date   2021/9/28 13:52
  * @Return int
  **/
  int insertInptClinicalPathStage(InptClinicalPathStageDTO inptClinicalPathStageDTOS);

  /**
  * @Menthod updateInptClinicalPathStage
  * @Desrciption 修改入径阶段病情
  *
  * @Param
  * [inptClinicalPathStageDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/28 11:07
  * @Return int
  **/
  int updateInptClinicalPathStage(InptClinicalPathStageDTO inptClinicalPathStageDTO);

  /**
  * @Menthod deleteInptClinicalPathStage
  * @Desrciption 删除入径阶段病情
  *
  * @Param
  * [inptClinicalPathStageDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/28 11:07
  * @Return int
  **/
  int deleteInptClinicalPathStage(InptClinicalPathStageDTO inptClinicalPathStageDTO);
}
