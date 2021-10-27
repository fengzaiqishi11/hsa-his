package cn.hsa.clinical.clinicalpathstagedetailitem.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.clinical.clinicalpathstagedetailitem.bo.ClinicalPathStageDetailItemBO;
import cn.hsa.module.clinical.clinicalpathstagedetailitem.dao.ClinicalPathStageDetailItemDAO;
import cn.hsa.module.clinical.clinicalpathstagedetailitem.dto.ClinicalPathStageDetailItemDTO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
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
  * @Desrciption 保存路径明细绑定医嘱/ 病历
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
    if(StringUtils.isEmpty(clinicalItem.getClassify())) {
      throw new AppException("系统归类属性为空");
    }
    // 系统归类 1医嘱 2病历
    if("2".equals(clinicalItem.getClassify())) {
      return saveClinicalMedicalRecordDetailItem(clinicalItem);
    }
    // 获取保存的项目明细
    List<ClinicalPathStageDetailItemDTO> clinicalPathStageDetailItemDTOS = clinicalItem.getClinicalPathStageDetailItemDTOS();
    // 是否又要删除的数据
    if(!ListUtils.isEmpty(clinicalItem.getIds())) {
      clinicalPathStageDetailItemDAO.deleteClinicalPathStageDetailItemDTOById(clinicalItem);
    }
    if (!ListUtils.isEmpty(clinicalPathStageDetailItemDTOS)) {
      List<ClinicalPathStageDetailItemDTO> newList = clinicalPathStageDetailItemDTOS;
      // 如果系统归类为医嘱  进行组号添加
      newList = buildInptAdviceDTOGroupNo(clinicalPathStageDetailItemDTOS);
      // 新增绑定明细列表
      List<ClinicalPathStageDetailItemDTO> addList = new ArrayList<>();
      // 编辑绑定明细列表
      List<ClinicalPathStageDetailItemDTO> editList = new ArrayList<>();
      for (ClinicalPathStageDetailItemDTO item : newList) {
        item.setHospCode(clinicalItem.getHospCode());
        // 没有id即为新增
        if (StringUtils.isNotEmpty(item.getId())) {
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
      if (!ListUtils.isEmpty(addList)) {
        clinicalPathStageDetailItemDAO.insertClinicalPathStageDetailItemDTO(addList);
      }
      if (!ListUtils.isEmpty(editList)) {
        clinicalPathStageDetailItemDAO.updateClinicalPathStageDetailItemDTO(editList);
      }
      return true;
    }
    return true;
  }

  /**
  * @Menthod saveClinicalMedicalRecordDetailItem
  * @Desrciption 路径明细系统归类为病历的  绑定病历
 *
  * @Param
  * [clinicalItem]
  *
  * @Author jiahong.yang
  * @Date   2021/10/15 11:51
  * @Return java.lang.Boolean
  **/
  public Boolean saveClinicalMedicalRecordDetailItem(ClinicalPathStageDetailItemDTO clinicalItem) {
    // 获取保存的项目明细
    List<ClinicalPathStageDetailItemDTO> clinicalPathStageDetailItemDTOS = clinicalItem.getClinicalPathStageDetailItemDTOS();
    if(ListUtils.isEmpty(clinicalPathStageDetailItemDTOS)) {
      throw new AppException("保存病历信息为空");
    }
    clinicalPathStageDetailItemDAO.deleteClinicalPathStageDetailItemDTOById(clinicalItem);
    for (ClinicalPathStageDetailItemDTO item : clinicalPathStageDetailItemDTOS) {
      item.setHospCode(clinicalItem.getHospCode());
      item.setId(SnowflakeUtils.getId());
      item.setCrteId(clinicalItem.getCrteId());
      item.setCrteName(clinicalItem.getCrteName());
      item.setCrteTime(clinicalItem.getCrteTime());
      // 是否有效  1是 0否
      item.setIsValid("1");
    }
    clinicalPathStageDetailItemDAO.insertClinicalPathStageDetailItemDTO(clinicalPathStageDetailItemDTOS);
    return true;
  }
  /**
  * @Menthod buildInptAdviceDTOGroupNo
  * @Desrciption 构建组号
  *
  * @Param
  * [clinicalPathStageDetailItemDTOS]
  *
  * @Author jiahong.yang
  * @Date   2021/9/23 20:04
  * @Return java.util.List<cn.hsa.module.clinical.clinicalpathstagedetailitem.dto.ClinicalPathStageDetailItemDTO>
  **/
  public List<ClinicalPathStageDetailItemDTO> buildInptAdviceDTOGroupNo(List<ClinicalPathStageDetailItemDTO> clinicalPathStageDetailItemDTOS){
    List<ClinicalPathStageDetailItemDTO> outptGroupNoList = clinicalPathStageDetailItemDTOS;
    //组号
    int groupNo = 0;
    //组内序号
    int groupSeqNo = 1;
    if(!ListUtils.isEmpty(clinicalPathStageDetailItemDTOS)){
      //获取最大组号
      groupNo = clinicalPathStageDetailItemDAO.getMaxGroupNo(outptGroupNoList.get(0));
    }
    boolean isTrue = true;
    for(int i = 0; i < clinicalPathStageDetailItemDTOS.size() ; i++){
      if(i == 0){
        outptGroupNoList.get(i).setGroupNo(0);
        outptGroupNoList.get(i).setGroupSeqNo(0);
      }else{
        //用法是否同上
        if("0".equals(outptGroupNoList.get(i).getUsageCode())){
          //判断是否是连续联录，多条序号不需要添加
          if(isTrue){
            groupNo = groupNo + 1;
            isTrue = false;
            //序号(修改第一条序号)
            outptGroupNoList.get(i-1).setGroupSeqNo(groupSeqNo);
            //组号修改第一条组号)
            outptGroupNoList.get(i-1).setGroupNo(groupNo);
          }
          groupSeqNo = groupSeqNo + 1;
          outptGroupNoList.get(i).setGroupNo(groupNo);
          //序号
          outptGroupNoList.get(i).setGroupSeqNo(groupSeqNo);
          //频率
          outptGroupNoList.get(i).setRateId(outptGroupNoList.get(i-1).getRateId());
          //执行科室
          outptGroupNoList.get(i).setExecDeptId(outptGroupNoList.get(i-1).getExecDeptId());
          //用药性质
          if(StringUtils.isEmpty(outptGroupNoList.get(i).getUseCode())){
            outptGroupNoList.get(i).setUseCode(outptGroupNoList.get(i-1).getUseCode());
          }
          //使用天数
          outptGroupNoList.get(i).setUseDays(outptGroupNoList.get(i-1).getUseDays());
          // 用法
          outptGroupNoList.get(i).setUsageCode(outptGroupNoList.get(i-1).getUsageCode());
        }else{
          isTrue = true;
          outptGroupNoList.get(i).setGroupNo(0);
          outptGroupNoList.get(i).setGroupSeqNo(0);
          groupSeqNo = 1;
        }
      }
    }
    return outptGroupNoList;
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
