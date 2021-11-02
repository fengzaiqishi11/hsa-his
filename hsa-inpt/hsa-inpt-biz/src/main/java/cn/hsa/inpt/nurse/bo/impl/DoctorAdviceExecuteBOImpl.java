package cn.hsa.inpt.nurse.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.clinical.clinicalpathstagedetailitem.dao.ClinicalPathStageDetailItemDAO;
import cn.hsa.module.clinical.clinicalpathstagedetailitem.dto.ClinicalPathStageDetailItemDTO;
import cn.hsa.module.clinical.clinicpathexec.dao.ClinicPathExecDAO;
import cn.hsa.module.clinical.clinicpathexec.dto.ClinicPathExecDTO;
import cn.hsa.module.clinical.inptclinicalpathstate.dao.InptClinicalPathStateDAO;
import cn.hsa.module.clinical.inptclinicalpathstate.dto.InptClinicalPathStateDTO;
import cn.hsa.module.inpt.doctor.dao.InptAdviceDAO;
import cn.hsa.module.inpt.doctor.dao.InptCostDAO;
import cn.hsa.module.inpt.doctor.dao.InptVisitDAO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.nurse.bo.DoctorAdviceExecuteBO;
import cn.hsa.module.inpt.nurse.dao.InptAdviceExecDAO;
import cn.hsa.module.inpt.nurse.dto.InptAdviceExecDTO;
import cn.hsa.module.sys.code.dto.SysCodeSelectDTO;
import cn.hsa.module.sys.code.service.SysCodeService;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *@Package_name: hsa.inpt.nurse.bo.impl
 *@Class_name: DoctorAdviceExecuteBoImpl
 *@Describe: 医嘱执行
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/2 16:21
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class DoctorAdviceExecuteBOImpl extends HsafBO implements DoctorAdviceExecuteBO {

    @Resource
    InptAdviceExecDAO inptAdviceExecDao;
    @Resource
    InptAdviceDAO inptAdviceDao;
    @Resource
    InptCostDAO inptCostDAO;
    @Resource
    SysCodeService sysCodeService_consumer;
    //住院医嘱
    @Resource
    private InptAdviceDAO inptAdviceDAO;

    @Resource
    private InptVisitDAO inptVisitDAO;

    @Resource
    // 阶段明细绑定医嘱
    private ClinicalPathStageDetailItemDAO clinicalPathStageDetailItemDAO;

    // 入径病人信息
    @Resource
    private InptClinicalPathStateDAO inptClinicalPathStateDAO;

    @Resource
    private ClinicPathExecDAO clinicPathExecDAO;

    /**
    * @Method queryDoctorAdviceExecuteInfo
    * @Desrciption 医嘱执行分页查询clinicalPathStageDetailItemDAO
    * @param inptAdviceExecDTO
    * @Author liuqi1
    * @Date   2020/9/2 17:06
    * @Return cn.hsa.base.PageDTO
    **/
    @Override
    public PageDTO queryDoctorAdviceExecuteInfo(InptAdviceExecDTO inptAdviceExecDTO) {
        PageHelper.startPage(inptAdviceExecDTO.getPageNo(),inptAdviceExecDTO.getPageSize());
        List<InptAdviceExecDTO> inptAdviceExecDTOS = inptAdviceExecDao.queryInptAdvicePage(inptAdviceExecDTO);
        return PageDTO.of(inptAdviceExecDTOS);
    }

    /**
    * @Method updateDoctorAdviceExecute
    * @Desrciption 医嘱执行修改
    * @param inptAdviceExecDTOs
    * @Author liuqi1
    * @Date   2020/9/2 17:07
    * @Return java.lang.Boolean
    **/
    @Override
    public Boolean updateDoctorAdviceExecute(List<InptAdviceExecDTO> inptAdviceExecDTOs) {
        if (ListUtils.isEmpty(inptAdviceExecDTOs)) {
            throw new AppException("参数为空");
        }

        for (InptAdviceExecDTO inptAdviceExecDTO: inptAdviceExecDTOs) {
          if("2".equals(inptAdviceExecDTO.getSignCode()) && StringUtils.isEmpty(inptAdviceExecDTO.getExecName())){
            throw new AppException("执行人为空");
          } else if("2".equals(inptAdviceExecDTO.getSignCode()) && inptAdviceExecDTO.getExecTime() == null) {
            throw new AppException("执行人时间为空");
          } else if ("2".equals(inptAdviceExecDTO.getSignCode()) && !StringUtils.isEmpty(inptAdviceExecDTO.getSourceIaId())
            && StringUtils.isEmpty(inptAdviceExecDTO.getIsPositive())){
            throw new AppException("皮试结果为空");
          }else if ("2".equals(inptAdviceExecDTO.getSignCode()) && inptAdviceExecDTO.getExecTime() != null){

              //判断当前执行时间是否小于医嘱时间
              InptAdviceDTO inptAdvice = new InptAdviceDTO() ;
              inptAdvice.setHospCode(inptAdviceExecDTO.getHospCode());
              inptAdvice.setId(inptAdviceExecDTO.getAdviceId());
              inptAdvice = inptAdviceDAO.getInptAdviceById(inptAdvice);
              if(DateUtils.dateCompare(inptAdviceExecDTO.getExecTime(),inptAdvice.getLongStartTime())){
                  throw new AppException("执行时间不能小于医嘱开始时间");
              }
          }
        }

        Map map = new HashMap();
        map.put("hospCode", inptAdviceExecDTOs.get(0).getHospCode());
        map.put("code", "PSJG");
        List<SysCodeSelectDTO> sysCodeSelectDTOList = sysCodeService_consumer.getByCode(map).getData().get("PSJG");
        //医嘱执行批量修改
        int eCount = inptAdviceExecDao.updateInptAdviceExecBatch(inptAdviceExecDTOs);
        // 查出临床路径医嘱
        List<InptAdviceExecDTO> clinicalAdviceEexecList = inptAdviceExecDTOs.stream().
             filter(s -> StringUtils.isNotEmpty(s.getStageDetailItemId()) &&
               StringUtils.isNotEmpty(s.getClinicalPathStageId())).collect(Collectors.toList());
        if(!ListUtils.isEmpty(clinicalAdviceEexecList)) {
          ClinicalAdviceExec(clinicalAdviceEexecList);
        }
        //医嘱执行批量更新费用表的执行人
        int costCount = inptCostDAO.updateInptCostExecuteBatch(inptAdviceExecDTOs);
        //皮试根据来源ID,获取主医嘱记录
        List<InptAdviceDTO> adviceList = inptAdviceExecDao.queryPsList(inptAdviceExecDTOs,map.get("hospCode").toString());

        List<InptAdviceDTO> list = new ArrayList<>();
        Map<String,String> map1 = new HashMap();
        inptAdviceExecDTOs.forEach(dto ->{
            InptAdviceDTO inptAdviceDTO = new InptAdviceDTO();
            inptAdviceDTO.setId(dto.getAdviceId());
            inptAdviceDTO.setHospCode(dto.getHospCode());
            inptAdviceDTO.setIsPositive(dto.getIsPositive());
            inptAdviceDTO.setExecId(dto.getExecId());
            inptAdviceDTO.setExecName(dto.getExecName());
            inptAdviceDTO.setSecondExecId(dto.getSecondExecId());
            inptAdviceDTO.setSecondExecName(dto.getSecondExecName());
            inptAdviceDTO.setSignCode(dto.getSignCode());
            inptAdviceDTO.setContent(dto.getContent());
            inptAdviceDTO.setSourceIaId(dto.getSourceIaId());
            inptAdviceDTO.setSkinResultCode(dto.getSkinResultCode()); //添加皮试结果

            if (!ListUtils.isEmpty(sysCodeSelectDTOList)) {
                for (SysCodeSelectDTO codeSelectDTO:sysCodeSelectDTOList) {
                    if (codeSelectDTO.getValue().equals(dto.getSkinResultCode())) {
                        String newContent = "(" + codeSelectDTO.getLabel() + ")";
                        map1.put(inptAdviceDTO.getSourceIaId(),newContent+"_"+inptAdviceDTO.getIsPositive());
                        inptAdviceDTO.setContent(inptAdviceDTO.getContent()+ newContent);
                    }
                }
            }
            list.add(inptAdviceDTO);
        });
        //批量更新医嘱
        int aCount = inptAdviceDao.updateInptAdviceBatch(list);
        //更新原医嘱表是否阳性,医嘱内容
          if(map1 != null && map1.size() > 0 && !ListUtils.isEmpty(adviceList)){
          for(String key : map1.keySet()){
            for (int j = 0; j < adviceList.size(); j++) {
              if(!StringUtils.isEmpty(key) && key.equals(adviceList.get(j).getId())){
                adviceList.get(j).setContent(adviceList.get(j).getContent() + map1.get(key).split("_")[0]);
                adviceList.get(j).setIsPositive(map1.get(key).split("_")[1]);
              }
            }
          }
          inptAdviceExecDao.updateIsPositive(adviceList);
        }

        //医嘱更新最近执行之间
        if (!ListUtils.isEmpty(inptAdviceExecDTOs)) {
            inptAdviceDAO.updateLastExeTimeByIds(inptAdviceExecDTOs);
        }

        return (eCount>0 && aCount>0 && costCount>0);
    }

    /**
    * @Menthod ClinicalAdviceExec
    * @Desrciption  临床路径医嘱执行回写
    *
    * @Param
    * [inptAdviceExecDTOs]
    *
    * @Author jiahong.yang
    * @Date   2021/10/21 9:33
    * @Return void
    **/
    private void ClinicalAdviceExec(List<InptAdviceExecDTO> clinicalAdviceEexecList) {
      List<ClinicPathExecDTO> addClinicPathExecDTOS = new ArrayList<>();
      for(InptAdviceExecDTO item : clinicalAdviceEexecList) {
          // 路径明细绑定医嘱明细
          ClinicalPathStageDetailItemDTO clinicalDetailItemDTO = new ClinicalPathStageDetailItemDTO();
          clinicalDetailItemDTO.setHospCode(item.getHospCode());
          clinicalDetailItemDTO.setId(item.getStageDetailItemId());
          ClinicalPathStageDetailItemDTO clinicalPathStageDetailItemDTOById = clinicalPathStageDetailItemDAO.getClinicalPathStageDetailItemDTOById(clinicalDetailItemDTO);

          // 删除掉原来的执行记录 然后 重新插入
          ClinicPathExecDTO deleteItem = new ClinicPathExecDTO();
          deleteItem.setHospCode(item.getHospCode());
          deleteItem.setClinicalPathStageId(item.getClinicalPathStageId());
          deleteItem.setDetailItemId(item.getStageDetailItemId());
          deleteItem.setVisitId(item.getVisitId());
          deleteItem.setListId(clinicalPathStageDetailItemDTOById.getListId());
          // 项目分类(XMFL):1诊疗；2医嘱；3；护理； 9其他
          deleteItem.setItemType("2");
          clinicPathExecDAO.deleteClinicPathExec(deleteItem);

          // 临床路径执行实体
          ClinicPathExecDTO additem = new ClinicPathExecDTO();
          additem.setHospCode(item.getHospCode());
          additem.setId(SnowflakeUtils.getId());
          additem.setVisitId(item.getVisitId());
          additem.setBabyId(item.getBabyId());

          // 是否执行  1.是 0.否
          additem.setIsExec("1");
          // 创建人信息填充
          additem.setCrteId(item.getExecId());
          additem.setCrteName(item.getExecName());
          additem.setCrteTime(item.getExecTime());
          // 执行人信息填充
          additem.setExecId(item.getExecId());
          additem.setExecName(item.getExecName());
          additem.setExecTime(item.getExecTime());
          // 路径信息填写
          additem.setListId(clinicalPathStageDetailItemDTOById.getListId());
          additem.setStageId(clinicalPathStageDetailItemDTOById.getStageId());
          additem.setDetailId(clinicalPathStageDetailItemDTOById.getDetailId());
          additem.setDetailItemId(clinicalPathStageDetailItemDTOById.getId());
          additem.setClinicalPathStageId(item.getClinicalPathStageId());
          // 项目ID(clinic_path_stage_detail_item.item_id)
          additem.setItemId(clinicalPathStageDetailItemDTOById.getItemId());
          // 项目分类(XMFL):1诊疗；2医嘱；3；护理； 9其他
          additem.setItemType("2");
          addClinicPathExecDTOS.add(additem);
        }

      if(!ListUtils.isEmpty(addClinicPathExecDTOS)) {
        clinicPathExecDAO.insertClinicPathExec(addClinicPathExecDTOS);
      }
    }
    /**
    * @Menthod updateAdviceExecuteCance
    * @Desrciption 医嘱执行取消
    *
    * @Param
    * [inptAdviceExecDTOs]
    *
    * @Author jiahong.yang
    * @Date   2021/1/13 9:19
    * @Return java.lang.Boolean
    **/
  @Override
  public Boolean updateAdviceExecuteCance(List<InptAdviceExecDTO> inptAdviceExecDTOs) {
    if (ListUtils.isEmpty(inptAdviceExecDTOs)) {
      throw new AppException("参数为空");
    }
    Map map = new HashMap();
    map.put("hospCode", inptAdviceExecDTOs.get(0).getHospCode());
    map.put("code", "PSJG");
    List<SysCodeSelectDTO> sysCodeSelectDTOList = sysCodeService_consumer.getByCode(map).getData().get("PSJG");
    //医嘱执行批量修改
    int eCount = inptAdviceExecDao.updateInptAdviceExecBatchCancel(inptAdviceExecDTOs);
    // 查出临床路径医嘱
    List<InptAdviceExecDTO> clinicalAdviceEexecList = inptAdviceExecDTOs.stream().
      filter(s -> StringUtils.isNotEmpty(s.getStageDetailItemId())).collect(Collectors.toList());
    if(!ListUtils.isEmpty(clinicalAdviceEexecList)) {
      deleteClinicAdviceExec(clinicalAdviceEexecList);
    }
    // 查询最近执行记录
    List<InptAdviceExecDTO> inptAdviceExecDTOS = new ArrayList<>();
    for (int i = 0; i < inptAdviceExecDTOs.size(); i++) {
      InptAdviceExecDTO inptAdviceExecDTO = inptAdviceExecDao.queryAdviceExecLately(inptAdviceExecDTOs.get(i));
      if(inptAdviceExecDTO == null) {
        inptAdviceExecDTO = new InptAdviceExecDTO();
      }
      inptAdviceExecDTO.setHospCode(inptAdviceExecDTOs.get(i).getHospCode());
      inptAdviceExecDTO.setAdviceId(inptAdviceExecDTOs.get(i).getAdviceId());
      inptAdviceExecDTO.setPlanExecTime(inptAdviceExecDTOs.get(i).getPlanExecTime());
      inptAdviceExecDTO.setIsPositive(inptAdviceExecDTOs.get(i).getIsPositive());
      inptAdviceExecDTO.setSourceIaId(inptAdviceExecDTOs.get(i).getSourceIaId());
      inptAdviceExecDTO.setContent(inptAdviceExecDTOs.get(i).getContent());
      inptAdviceExecDTOS.add(inptAdviceExecDTO);
    }
    //医嘱执行批量更新费用表的执行人
    int costCount = inptCostDAO.updateInptCostExecuteBatch(inptAdviceExecDTOS);
    //皮试根据来源ID,获取主医嘱记录
    List<InptAdviceDTO> adviceList = inptAdviceExecDao.queryPsList(inptAdviceExecDTOs,map.get("hospCode").toString());
    List<InptAdviceDTO> list = new ArrayList<>();
    Map<String,String> map1 = new HashMap();
    inptAdviceExecDTOS.forEach(dto ->{
      InptAdviceDTO inptAdviceDTO = new InptAdviceDTO();
      inptAdviceDTO.setId(dto.getAdviceId());
      inptAdviceDTO.setHospCode(dto.getHospCode());
      inptAdviceDTO.setExecId(dto.getExecId());
      inptAdviceDTO.setExecName(dto.getExecName());
      inptAdviceDTO.setSecondExecId(dto.getSecondExecId());
      inptAdviceDTO.setSecondExecName(dto.getSecondExecName());
      if(StringUtils.isEmpty(inptAdviceDTO.getSignCode())){
        inptAdviceDTO.setSignCode("1");
      } else {
        inptAdviceDTO.setSignCode(dto.getSignCode());
      }
      inptAdviceDTO.setContent(dto.getContent());
      inptAdviceDTO.setSourceIaId(dto.getSourceIaId());
      if(!StringUtils.isEmpty(dto.getIsPositive())){
        String content = inptAdviceDTO.getContent().substring(0,inptAdviceDTO.getContent().lastIndexOf("("));
        inptAdviceDTO.setContent(content);
      }
      list.add(inptAdviceDTO);
    });
    //批量更新医嘱
    int aCount = inptAdviceDao.updateInptAdviceBatch(list);
    if(!ListUtils.isEmpty(adviceList)){
      for (int i = 0; i < adviceList.size(); i++) {
          String content = adviceList.get(i).getContent().substring(0,adviceList.get(i).getContent().lastIndexOf("("));
          adviceList.get(i).setContent(content);
          adviceList.get(i).setIsPositive(null);
          inptAdviceExecDao.updateIsPositive(adviceList);
      }
    }
    return true;
  }

  /**
  * @Menthod deleteClinicAdviceExec
  * @Desrciption 取消临床路径医嘱回退执行
  *
  * @Param
  * [clinicalAdviceEexecList]
  *
  * @Author jiahong.yang
  * @Date   2021/10/21 15:35
  * @Return void
  **/
  public void deleteClinicAdviceExec(List<InptAdviceExecDTO> clinicalAdviceEexecList) {
    for(InptAdviceExecDTO item : clinicalAdviceEexecList) {
      // 路径明细绑定医嘱明细
      ClinicalPathStageDetailItemDTO clinicalDetailItemDTO = new ClinicalPathStageDetailItemDTO();
      clinicalDetailItemDTO.setHospCode(item.getHospCode());
      clinicalDetailItemDTO.setId(item.getStageDetailItemId());
      ClinicalPathStageDetailItemDTO clinicalPathStageDetailItemDTOById = clinicalPathStageDetailItemDAO.getClinicalPathStageDetailItemDTOById(clinicalDetailItemDTO);

      // 查询医嘱最近执行记录
      InptAdviceExecDTO inptAdviceExecDTO = inptAdviceExecDao.queryAdviceExecLately(item);
      // 查询医嘱之前是否存在执行记录
      ClinicPathExecDTO saveItem = new ClinicPathExecDTO();
      saveItem.setHospCode(item.getHospCode());
      saveItem.setVisitId(item.getVisitId());
      saveItem.setClinicalPathStageId(item.getClinicalPathStageId());
      saveItem.setListId(clinicalPathStageDetailItemDTOById.getListId());
      saveItem.setStageId(clinicalPathStageDetailItemDTOById.getStageId());
      saveItem.setDetailId(clinicalPathStageDetailItemDTOById.getDetailId());
      saveItem.setDetailItemId(clinicalPathStageDetailItemDTOById.getId());
      // 项目分类(XMFL):1诊疗；2医嘱；3；护理； 9其他
      saveItem.setItemType("2");
      // 判断以前有没有执行记录
      if(inptAdviceExecDTO == null) {
        clinicPathExecDAO.deleteClinicPathExec(saveItem);
      } else {
        List<ClinicPathExecDTO> clinicPathExecDTOS = clinicPathExecDAO.queryClinicPathExecAll(saveItem);
        if(!ListUtils.isEmpty(clinicPathExecDTOS)) {
          ClinicPathExecDTO updateItem = clinicPathExecDTOS.get(0);
          // 重置为上一次执行时间/人
          updateItem.setExecTime(inptAdviceExecDTO.getExecTime());
          updateItem.setExecId(inptAdviceExecDTO.getExecId());
          updateItem.setExecName(inptAdviceExecDTO.getExecName());
          updateItem.setCrteId(inptAdviceExecDTO.getExecId());
          updateItem.setCrteName(inptAdviceExecDTO.getExecName());
          updateItem.setCrteTime(inptAdviceExecDTO.getExecTime());
          clinicPathExecDAO.updateClinicPathExec(updateItem);
        }
      }
    }
  }
  @Override
    public PageDTO queryNoMedical(InptAdviceDTO inptAdviceDTO) {

        PageHelper.startPage(inptAdviceDTO.getPageNo(),inptAdviceDTO.getPageSize());
        List<InptAdviceDTO> inptAdviceExecDTOS = inptAdviceDao.queryNoMedical(inptAdviceDTO);
        return PageDTO.of(inptAdviceExecDTOS);
    }
}
