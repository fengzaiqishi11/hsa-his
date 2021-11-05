package cn.hsa.module.clinical.inptclinicalpathstage.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO;
import cn.hsa.module.clinical.inptclinicalpathstage.dto.InptClinicalPathStageDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.clinical.clinicalpathlist.entity
 * @Class_name: ClinicPathListDO
 * @Describe: 病人阶段病情记录业务传输层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/8 19:27
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface InptClinicalPathStageService {

  /**
  * @Menthod getInptClinicalPathStageById
  * @Desrciption 查询单个入径阶段病情
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/28 11:10
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.clinical.inptclinicalpathstage.dto.InptClinicalPathStageDTO>
  **/
  WrapperResponse<InptClinicalPathStageDTO> getInptClinicalPathStageById(Map map);

  /**
  * @Menthod queryInptClinicalPathStagePage
  * @Desrciption 分页查询入径阶段病情
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/28 11:10
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  WrapperResponse<PageDTO> queryInptClinicalPathStagePage(Map map);

  /**
  * @Menthod queryInptClinicalPathStageAll
  * @Desrciption 查询入径阶段病情
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/28 11:11
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.clinical.inptclinicalpathstage.dto.InptClinicalPathStageDTO>>
  **/
  WrapperResponse<List<InptClinicalPathStageDTO>> queryInptClinicalPathStageAll(Map map);

  /**
  * @Menthod saveInptClinicalPathStage
  * @Desrciption 保存入径阶段病情
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/28 11:11
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  WrapperResponse<Boolean> saveInptClinicalPathStage(Map map);

  /**
  * @Menthod queryNotExecItem
  * @Desrciption 查询阶段未执行项目
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/10/26 10:30
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO>>
  **/
  WrapperResponse<List<ClinicPathStageDetailDTO>> queryNotExecItem(Map map);

  /**
  * @Menthod deleteInptClinicalPathStage
  * @Desrciption 删除入径阶段病情
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/28 11:11
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  WrapperResponse<Boolean> deleteInptClinicalPathStage(Map map);

}
