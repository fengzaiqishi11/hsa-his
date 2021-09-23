package cn.hsa.module.clinical.clinicalpathstagedetailitem.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.clinical.clinicalpathstagedetailitem.dto.ClinicalPathStageDetailItemDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.clinical.clinicalpathstagedetailitem.bo
 * @Class_name: ClinicalPathStageDetailItemBO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/17 14:02
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface ClinicalPathStageDetailItemBO {
  /**
  * @Menthod getClinicalPathStageDetailItemDTOById
  * @Desrciption 查询路径明细绑定医嘱
  *
  * @Param
  * [clinicalPathStageDetailItemDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 16:08
  * @Return cn.hsa.module.clinical.clinicalpathstagedetailitem.dto.ClinicalPathStageDetailItemDTO
  **/
  ClinicalPathStageDetailItemDTO getClinicalPathStageDetailItemDTOById(ClinicalPathStageDetailItemDTO clinicalPathStageDetailItemDTO);

  /**
  * @Menthod queryClinicalPathStageDetailItemDTOAll
  * @Desrciption 查询所有路径明细绑定医嘱
  *
  * @Param
  * [clinicalPathStageDetailItemDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 16:09
  * @Return java.util.List<cn.hsa.module.clinical.clinicalpathstagedetailitem.dto.ClinicalPathStageDetailItemDTO>
  **/
  List<ClinicalPathStageDetailItemDTO> queryClinicalPathStageDetailItemDTOAll(ClinicalPathStageDetailItemDTO clinicalPathStageDetailItemDTO);

  /**
  * @Menthod queryClinicalPathStageDetailItemDTOPage
  * @Desrciption 分页查询路径明细绑定医嘱
  *
  * @Param
  * [clinicalPathStageDetailItemDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 16:09
  * @Return cn.hsa.base.PageDTO
  **/
  PageDTO queryClinicalPathStageDetailItemDTOPage(ClinicalPathStageDetailItemDTO clinicalPathStageDetailItemDTO);

  /**
  * @Menthod saveClinicalPathStageDetailItemDTO
  * @Desrciption 保存路径明细绑定医嘱
  *
  * @Param
  * [clinicalPathStageDetailItemDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 16:09
  * @Return java.lang.Boolean
  **/
  Boolean saveClinicalPathStageDetailItem(ClinicalPathStageDetailItemDTO clinicalPathStageDetailItemDTO);

  /**
  * @Menthod updateClinicalPathStageDetailItemDTO
  * @Desrciption 修改路径明细绑定医嘱
  *
  * @Param
  * [clinicalPathStageDetailItemDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 16:09
  * @Return java.lang.Boolean
  **/
  Boolean updateClinicalPathStageDetailItemDTO(ClinicalPathStageDetailItemDTO clinicalPathStageDetailItemDTO);

  /**
  * @Menthod deleteClinicalPathStageDetailItemDTOById
  * @Desrciption 删除路径明细绑定医嘱
  *
  * @Param
  * [clinicalPathStageDetailItemDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 16:09
  * @Return java.lang.Boolean
  **/
  Boolean deleteClinicalPathStageDetailItemDTOById(ClinicalPathStageDetailItemDTO clinicalPathStageDetailItemDTO);

  /**
  * @Menthod quertAllItem
  * @Desrciption 查询所有项目（药品，材料，医嘱项目）
  *
  * @Param
  * [baseDrugDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/22 10:24
  * @Return cn.hsa.base.PageDTO
  **/
  PageDTO quertAllItem(BaseDrugDTO baseDrugDTO);
}
