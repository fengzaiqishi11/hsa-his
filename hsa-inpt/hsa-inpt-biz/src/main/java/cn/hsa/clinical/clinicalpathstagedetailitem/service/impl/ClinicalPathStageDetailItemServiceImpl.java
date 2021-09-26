package cn.hsa.clinical.clinicalpathstagedetailitem.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO;
import cn.hsa.module.clinical.clinicalpathstagedetailitem.bo.ClinicalPathStageDetailItemBO;
import cn.hsa.module.clinical.clinicalpathstagedetailitem.dto.ClinicalPathStageDetailItemDTO;
import cn.hsa.module.clinical.clinicalpathstagedetailitem.service.ClinicalPathStageDetailItemService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.clinical.clinicalpathstagedetailitem.service.impl
 * @Class_name: ClinicalPathStageDetailItemServiceImpl
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/17 14:18
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/inpt/clinicalPathStageDetailItemService_provider")
@Slf4j
@Service("clinicalPathStageDetailItemService_provider")
public class ClinicalPathStageDetailItemServiceImpl implements ClinicalPathStageDetailItemService {

  @Resource
  private ClinicalPathStageDetailItemBO clinicalPathStageDetailItemBO;
  /**
  * @Menthod getClinicalPathStageDetailItemDTOById
  * @Desrciption 查询路径明细绑定医嘱
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 15:44
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.clinical.clinicalpathstagedetailitem.dto.ClinicalPathStageDetailItemDTO>
  **/
  @Override
  public WrapperResponse<ClinicalPathStageDetailItemDTO> getClinicalPathStageDetailItemDTOById(Map map) {
    ClinicalPathStageDetailItemDTO clinicalPathStageDetailItemDTO = MapUtils.get(map,"clinicalPathStageDetailItemDTO");
    return WrapperResponse.success(clinicalPathStageDetailItemBO.getClinicalPathStageDetailItemDTOById(clinicalPathStageDetailItemDTO));
  }

  /**
  * @Menthod queryClinicalPathStageDetailItemDTOPage
  * @Desrciption 分页查询路径明细绑定医嘱
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 15:44
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.clinical.clinicalpathstagedetailitem.dto.ClinicalPathStageDetailItemDTO>>
  **/
  @Override
  public WrapperResponse<PageDTO> queryClinicalPathStageDetailItemDTOPage(Map map) {
    ClinicalPathStageDetailItemDTO clinicalPathStageDetailItemDTO = MapUtils.get(map,"clinicalPathStageDetailItemDTO");
    return WrapperResponse.success(clinicalPathStageDetailItemBO.queryClinicalPathStageDetailItemDTOPage(clinicalPathStageDetailItemDTO));
  }

  /**
  * @Menthod queryClinicalPathStageDetailItemDTOAll
  * @Desrciption 查询所有路径明细绑定医嘱
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 15:44
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @Override
  public WrapperResponse<List<ClinicalPathStageDetailItemDTO>> queryClinicalPathStageDetailItemDTOAll(Map map) {
    ClinicalPathStageDetailItemDTO clinicalPathStageDetailItemDTO = MapUtils.get(map,"clinicalPathStageDetailItemDTO");
    return WrapperResponse.success(clinicalPathStageDetailItemBO.queryClinicalPathStageDetailItemDTOAll(clinicalPathStageDetailItemDTO));
  }

  /**
  * @Menthod saveClinicalPathStageDetailItem
  * @Desrciption 保存路径明细绑定医嘱
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 15:44
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> saveClinicalPathStageDetailItem(Map map) {
    ClinicalPathStageDetailItemDTO clinicalPathStageDetailItemDTO = MapUtils.get(map,"clinicalPathStageDetailItemDTO");
    return WrapperResponse.success(clinicalPathStageDetailItemBO.saveClinicalPathStageDetailItem(clinicalPathStageDetailItemDTO));
  }

  /**
  * @Menthod updateClinicalPathStageDetailItemDTO
  * @Desrciption 修改路径明细绑定医嘱
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 15:44
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> updateClinicalPathStageDetailItemDTO(Map map) {
    ClinicalPathStageDetailItemDTO clinicalPathStageDetailItemDTO = MapUtils.get(map,"clinicalPathStageDetailItemDTO");
    return WrapperResponse.success(clinicalPathStageDetailItemBO.updateClinicalPathStageDetailItemDTO(clinicalPathStageDetailItemDTO));
  }

  /**
  * @Menthod deleteClinicalPathStageDetailItemDTOById
  * @Desrciption 删除路径明细绑定医嘱
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 15:44
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @Override
  public WrapperResponse<Boolean> deleteClinicalPathStageDetailItemDTOById(Map map) {
    ClinicalPathStageDetailItemDTO clinicalPathStageDetailItemDTO = MapUtils.get(map,"clinicalPathStageDetailItemDTO");
    return WrapperResponse.success(clinicalPathStageDetailItemBO.deleteClinicalPathStageDetailItemDTOById(clinicalPathStageDetailItemDTO));
  }

  /**
  * @Menthod quertAllItem
  * @Desrciption 查询所有项目（药品，材料，医嘱项目）
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/22 10:24
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  public WrapperResponse<PageDTO> quertAllItem(Map map) {
    BaseDrugDTO baseDrugDTO = MapUtils.get(map,"baseDrugDTO");
    return WrapperResponse.success(clinicalPathStageDetailItemBO.quertAllItem(baseDrugDTO));
  }
}
