package cn.hsa.module.clinical.inptclinicalpathstage.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO;
import cn.hsa.module.clinical.inptclinicalpathstage.dto.InptClinicalPathStageDTO;
import cn.hsa.module.clinical.inptclinicalpathstate.dto.InptClinicalPathStateDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.clinical.clinicalpathlist.entity
 * @Class_name: ClinicPathListDO
 * @Describe: 病人阶段病情记录业务处理层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/8 19:27
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface InptClinicalPathStageBO {
  /**
   * @Menthod getInptClinicalPathStageById
   * @Desrciption 查询单个入径阶段病情
   *
   * @Param
   * [map]
   *
   * @Author jiahong.yang
   * @Date   2021/9/28 11:06
   * @Return cn.hsa.module.clinical.inptclinicalpathstage.dto.InptClinicalPathStageDTO
   **/
  InptClinicalPathStageDTO getInptClinicalPathStageById(InptClinicalPathStageDTO inptClinicalPathStageDTO);

  /**
  * @Menthod queryInptClinicalPathStagePage
  * @Desrciption
  *
  * @Param
  * [inptClinicalPathStageDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/28 11:15
  * @Return cn.hsa.base.PageDTO
  **/
  PageDTO queryInptClinicalPathStagePage(InptClinicalPathStageDTO inptClinicalPathStageDTO);

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
   * @Menthod saveInptClinicalPathStage
   * @Desrciption 保存入径阶段病情
   *
   * @Param
   * [inptClinicalPathStageDTO]
   *
   * @Author jiahong.yang
   * @Date   2021/9/28 11:07
   * @Return int
   **/
  boolean saveInptClinicalPathStage(InptClinicalPathStageDTO inptClinicalPathStageDTO);

  /**
  * @Menthod queryNotExecItem
  * @Desrciption 查询阶段未执行项目
  *
  * @Param
  * [inptClinicalPathStageDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/10/26 10:31
  * @Return java.util.List<cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO>
  **/
  List<ClinicPathStageDetailDTO> queryNotExecItem(InptClinicalPathStageDTO inptClinicalPathStageDTO);

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
  boolean deleteInptClinicalPathStage(InptClinicalPathStageDTO inptClinicalPathStageDTO);
}
