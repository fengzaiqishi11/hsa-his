package cn.hsa.module.clinical.clinicalpathstagedetailitem.dao;

import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.clinical.clinicalpathstagedetailitem.dto.ClinicalPathStageDetailItemDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.clinical.clinicalpathstagedetailitem.dao
 * @Class_name: ClinicalPathStageDetailItemDAO1
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/17 13:56
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface ClinicalPathStageDetailItemDAO {

  /**
  * @Menthod getClinicalPathStageDetailItemDTOById
  * @Desrciption 查询路径明细绑定医嘱根据id
  *
  * @Param
  * [clinicalPathStageDetailItemDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 16:19
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
  * @Date   2021/9/17 16:19
  * @Return java.util.List<cn.hsa.module.clinical.clinicalpathstagedetailitem.dto.ClinicalPathStageDetailItemDTO>
  **/
  List<ClinicalPathStageDetailItemDTO> queryClinicalPathStageDetailItemDTOAll(ClinicalPathStageDetailItemDTO clinicalPathStageDetailItemDTO);


  /**
  * @Menthod insertClinicalPathStageDetailItemDTO
  * @Desrciption 新增路径明细绑定医嘱
   *   *
  *
  * @Param
  * [clinicalPathStageDetailItemDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 16:19
  * @Return int
  **/
  int insertClinicalPathStageDetailItemDTO(List<ClinicalPathStageDetailItemDTO> clinicalPathStageDetailItemDTOS);


  /**
  * @Menthod updateClinicalPathStageDetailItemDTO
  * @Desrciption 修改路径明细绑定医嘱
  *
  * @Param
  * [clinicalPathStageDetailItemDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 16:19
  * @Return int
  **/
  int updateClinicalPathStageDetailItemDTO(List<ClinicalPathStageDetailItemDTO> clinicalPathStageDetailItemDTOS);


  /**
  * @Menthod deleteClinicalPathStageDetailItemDTOById
  * @Desrciption 删除路径明细绑定医嘱
  *
  * @Param
  * [clinicalPathStageDetailItemDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 16:20
  * @Return int
  **/
  int deleteClinicalPathStageDetailItemDTOById(ClinicalPathStageDetailItemDTO clinicalPathStageDetailItemDTO);

  /**
  * @Menthod quertAllItem
  * @Desrciption 查询所有项目（药品，材料，医嘱项目）
  *
  * @Param
  * [baseDrugDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/22 10:26
  * @Return java.util.List<cn.hsa.module.base.drug.dto.BaseDrugDTO>
  **/
  List<BaseDrugDTO> quertAllItem(BaseDrugDTO baseDrugDTO);
}
