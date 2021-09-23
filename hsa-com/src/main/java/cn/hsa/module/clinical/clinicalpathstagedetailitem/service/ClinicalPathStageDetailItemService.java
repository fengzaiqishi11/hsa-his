package cn.hsa.module.clinical.clinicalpathstagedetailitem.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.clinical.clinicalpathstagedetailitem.dto.ClinicalPathStageDetailItemDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.clinical.clinicalpathstagedetailitem.service
 * @Class_name: ClinicalPathStageDetailItemService1
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/17 13:52
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface ClinicalPathStageDetailItemService {

  /**
  * @Menthod getClinicalPathStageDetailItemDTOById
  * @Desrciption 查询路径明细绑定医嘱
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 15:39
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.clinical.clinicalpathstagedetailitem.dto.ClinicalPathStageDetailItemDTO>
  **/
  WrapperResponse<ClinicalPathStageDetailItemDTO> getClinicalPathStageDetailItemDTOById(Map map);

  /**
  * @Menthod queryClinicalPathStageDetailItemDTOPage
  * @Desrciption 分页查询路径明细绑定医嘱
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 15:40
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.clinical.clinicalpathstagedetailitem.dto.ClinicalPathStageDetailItemDTO>>
  **/
  WrapperResponse<PageDTO> queryClinicalPathStageDetailItemDTOPage(Map map);

  /**
  * @Menthod queryClinicalPathStageDetailItemDTOAll
  * @Desrciption 查询所有路径明细绑定医嘱
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 15:40
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  WrapperResponse<List<ClinicalPathStageDetailItemDTO>> queryClinicalPathStageDetailItemDTOAll(Map map);

  /**
  * @Menthod 保存路径明细绑定医嘱
  * @Desrciption 新增路径明细绑定医嘱
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 15:40
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  WrapperResponse<Boolean> saveClinicalPathStageDetailItem(Map map);

  /**
  * @Menthod updateClinicalPathStageDetailItemDTO
  * @Desrciption 修改路径明细绑定医嘱
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 15:40
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  WrapperResponse<Boolean> updateClinicalPathStageDetailItemDTO(Map map);

  /**
  * @Menthod deleteClinicalPathStageDetailItemDTOById
  * @Desrciption 删除路径明细绑定医嘱
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 15:40
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  WrapperResponse<Boolean> deleteClinicalPathStageDetailItemDTOById(Map map);

  /**
  * @Menthod quertAllItem
  * @Desrciption 查询所有项目（药品，材料，医嘱项目）
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/9/22 10:23
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  WrapperResponse<PageDTO> quertAllItem(Map map);
}
