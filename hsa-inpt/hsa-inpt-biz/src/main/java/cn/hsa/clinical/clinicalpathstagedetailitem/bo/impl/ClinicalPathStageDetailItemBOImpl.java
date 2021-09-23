package cn.hsa.clinical.clinicalpathstagedetailitem.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.clinical.clinicalpathstagedetailitem.bo.ClinicalPathStageDetailItemBO;
import cn.hsa.module.clinical.clinicalpathstagedetailitem.dao.ClinicalPathStageDetailItemDAO;
import cn.hsa.module.clinical.clinicalpathstagedetailitem.dto.ClinicalPathStageDetailItemDTO;
import cn.hsa.util.ListUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package_name: cn.hsa.clinical.clinicalpathstagedetailitem.bo.impl
 * @Class_name: ClinicalPathStageDetailItemBOImpl
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/17 14:18
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class ClinicalPathStageDetailItemBOImpl implements ClinicalPathStageDetailItemBO {
  @Resource
  private ClinicalPathStageDetailItemDAO clinicalPathStageDetailItemDAO;

  /**
  * @Menthod getClinicalPathStageDetailItemDTOById
  * @Desrciption 查询路径明细绑定医嘱
  *
  * @Param
  * [clinicalPathStageDetailItemDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 16:13
  * @Return cn.hsa.module.clinical.clinicalpathstagedetailitem.dto.ClinicalPathStageDetailItemDTO
  **/
  @Override
  public ClinicalPathStageDetailItemDTO getClinicalPathStageDetailItemDTOById(ClinicalPathStageDetailItemDTO clinicalPathStageDetailItemDTO) {
    return clinicalPathStageDetailItemDAO.getClinicalPathStageDetailItemDTOById(clinicalPathStageDetailItemDTO);
  }

  /**
  * @Menthod queryClinicalPathStageDetailItemDTOAll
  * @Desrciption  查询所有路径明细绑定医嘱
  *
  * @Param
  * [clinicalPathStageDetailItemDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 16:13
  * @Return java.util.List<cn.hsa.module.clinical.clinicalpathstagedetailitem.dto.ClinicalPathStageDetailItemDTO>
  **/
  @Override
  public List<ClinicalPathStageDetailItemDTO> queryClinicalPathStageDetailItemDTOAll(ClinicalPathStageDetailItemDTO clinicalPathStageDetailItemDTO) {
    return clinicalPathStageDetailItemDAO.queryClinicalPathStageDetailItemDTOAll(clinicalPathStageDetailItemDTO);
  }

  /**
  * @Menthod queryClinicalPathStageDetailItemDTOPage
  * @Desrciption 分页查询路径明细绑定医嘱
  *
  * @Param
  * [clinicalPathStageDetailItemDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 16:13
  * @Return cn.hsa.base.PageDTO
  **/
  @Override
  public PageDTO queryClinicalPathStageDetailItemDTOPage(ClinicalPathStageDetailItemDTO clinicalPathStageDetailItemDTO) {
    PageHelper.startPage(clinicalPathStageDetailItemDTO.getPageNo(),clinicalPathStageDetailItemDTO.getPageSize());
    List<ClinicalPathStageDetailItemDTO> clinicalPathStageDetailItemDTOS = clinicalPathStageDetailItemDAO.queryClinicalPathStageDetailItemDTOAll(clinicalPathStageDetailItemDTO);
    return PageDTO.of(clinicalPathStageDetailItemDTOS);
  }

  /**
  * @Menthod insertClinicalPathStageDetailItemDTO
  * @Desrciption 保存路径明细绑定医嘱
  *
  * @Param
  * [clinicalPathStageDetailItemDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 16:13
  * @Return java.lang.Boolean
  **/
  @Override
  public Boolean saveClinicalPathStageDetailItem(ClinicalPathStageDetailItemDTO clinicalItem) {
    List<ClinicalPathStageDetailItemDTO> clinicalPathStageDetailItemDTOS = clinicalItem.getClinicalPathStageDetailItemDTOS();
    if(ListUtils.isEmpty(clinicalPathStageDetailItemDTOS)) {
      throw new AppException("绑定医嘱明细数据为空");
    }
    // 新增医嘱绑定明细列表
    List<ClinicalPathStageDetailItemDTO> addList = new ArrayList<>();
    // 编辑医嘱绑定明细列表
    List<ClinicalPathStageDetailItemDTO> editList = new ArrayList<>();
    for(ClinicalPathStageDetailItemDTO item : clinicalPathStageDetailItemDTOS) {
      if(StringUtils.isNotEmpty(item.getId())) {
        editList.add(item);
        continue;
      }
      item.setId(SnowflakeUtils.getId());
      item.setHospCode(clinicalItem.getHospCode());
      item.setCrteId(clinicalItem.getCrteId());
      item.setCrteName(clinicalItem.getCrteName());
      item.setCrteTime(clinicalItem.getCrteTime());
      item.setIsValid("1");
      addList.add(item);
    }
    if(!ListUtils.isEmpty(addList)) {
      clinicalPathStageDetailItemDAO.insertClinicalPathStageDetailItemDTO(addList);
    }
    if(!ListUtils.isEmpty(editList)) {
      clinicalPathStageDetailItemDAO.insertClinicalPathStageDetailItemDTO(editList);
    }
    return true;
  }

  /**
  * @Menthod updateClinicalPathStageDetailItemDTO
  * @Desrciption 修改路径明细绑定医嘱
  *
  * @Param
  * [clinicalPathStageDetailItemDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 16:13
  * @Return java.lang.Boolean
  **/
  @Override
  public Boolean updateClinicalPathStageDetailItemDTO(ClinicalPathStageDetailItemDTO clinicalPathStageDetailItemDTO) {
    return clinicalPathStageDetailItemDAO.updateClinicalPathStageDetailItemDTO(clinicalPathStageDetailItemDTO.getClinicalPathStageDetailItemDTOS()) > 0;
  }

  /**
  * @Menthod deleteClinicalPathStageDetailItemDTOById
  * @Desrciption 删除路径明细绑定医嘱
  *
  * @Param
  * [clinicalPathStageDetailItemDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/17 16:13
  * @Return java.lang.Boolean
  **/
  @Override
  public Boolean deleteClinicalPathStageDetailItemDTOById(ClinicalPathStageDetailItemDTO clinicalPathStageDetailItemDTO) {
    return clinicalPathStageDetailItemDAO.deleteClinicalPathStageDetailItemDTOById(clinicalPathStageDetailItemDTO) > 0;
  }

  /**
  * @Menthod quertAllItem
  * @Desrciption 查询所有项目（药品，材料，医嘱项目）
  *
  * @Param
  * [baseDrugDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/22 10:26
  * @Return cn.hsa.base.PageDTO
  **/
  @Override
  public PageDTO quertAllItem(BaseDrugDTO baseDrugDTO) {
    PageHelper.startPage(baseDrugDTO.getPageNo(),baseDrugDTO.getPageSize());
    List<BaseDrugDTO> baseDrugDTOS = clinicalPathStageDetailItemDAO.quertAllItem(baseDrugDTO);
    return PageDTO.of(baseDrugDTOS);
  }
}
