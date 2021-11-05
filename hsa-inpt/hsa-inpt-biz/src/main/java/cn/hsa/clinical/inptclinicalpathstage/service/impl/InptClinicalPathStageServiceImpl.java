package cn.hsa.clinical.inptclinicalpathstage.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO;
import cn.hsa.module.clinical.inptclinicalpathstage.bo.InptClinicalPathStageBO;
import cn.hsa.module.clinical.inptclinicalpathstage.dto.InptClinicalPathStageDTO;
import cn.hsa.module.clinical.inptclinicalpathstage.service.InptClinicalPathStageService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
@HsafRestPath("/service/inpt/inptClinicalPathStageService")
@Slf4j
@Service("inptClinicalPathStageService_provider")
public class InptClinicalPathStageServiceImpl implements InptClinicalPathStageService {

  @Resource
  private InptClinicalPathStageBO inptClinicalPathStageBO;


  /**
  * @Menthod getInptClinicalPathStageById
  * @Desrciption 查询单个入径阶段病情
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/29 16:42
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.clinical.inptclinicalpathstage.dto.InptClinicalPathStageDTO>
  **/
  @Override
  public WrapperResponse<InptClinicalPathStageDTO> getInptClinicalPathStageById(Map map) {
    InptClinicalPathStageDTO inptClinicalPathStageDTO = inptClinicalPathStageBO.getInptClinicalPathStageById(MapUtils.get(map, "inptClinicalPathStageDTO"));
    return WrapperResponse.success(inptClinicalPathStageDTO);
  }

  /**
  * @Menthod queryInptClinicalPathStagePage
  * @Desrciption 查询入径阶段病情
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/28 11:13
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @Override
  public WrapperResponse<PageDTO> queryInptClinicalPathStagePage(Map map) {
    return WrapperResponse.success(inptClinicalPathStageBO.queryInptClinicalPathStagePage(MapUtils.get(map, "inptClinicalPathStageDTO")));
  }

  /**
  * @Menthod queryInptClinicalPathStageAll
  * @Desrciption
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/28 11:13
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.clinical.inptclinicalpathstage.dto.InptClinicalPathStageDTO>>
  **/
  @Override
  public WrapperResponse<List<InptClinicalPathStageDTO>> queryInptClinicalPathStageAll(Map map) {
    return WrapperResponse.success(inptClinicalPathStageBO.queryInptClinicalPathStageAll(MapUtils.get(map, "inptClinicalPathStageDTO")));
  }

  /**
  * @Menthod saveInptClinicalPathStage
  * @Desrciption  保存入径阶段病情
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/28 11:13
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> saveInptClinicalPathStage(Map map) {
    return WrapperResponse.success(inptClinicalPathStageBO.saveInptClinicalPathStage(MapUtils.get(map, "inptClinicalPathStageDTO")));
  }

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
  @Override
  public WrapperResponse<List<ClinicPathStageDetailDTO>> queryNotExecItem(Map map) {
    return WrapperResponse.success(inptClinicalPathStageBO.queryNotExecItem(MapUtils.get(map, "inptClinicalPathStageDTO")));
  }

  /**
  * @Menthod deleteInptClinicalPathStage
  * @Desrciption
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/28 11:13
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> deleteInptClinicalPathStage(Map map) {
    return WrapperResponse.success(inptClinicalPathStageBO.deleteInptClinicalPathStage(MapUtils.get(map, "inptClinicalPathStageDTO")));
  }
}
