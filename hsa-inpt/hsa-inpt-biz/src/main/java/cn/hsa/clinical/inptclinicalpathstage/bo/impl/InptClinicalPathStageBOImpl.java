package cn.hsa.clinical.inptclinicalpathstage.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.clinical.clinicalpathstage.dao.ClinicalPathStageDAO;
import cn.hsa.module.clinical.clinicalpathstage.dto.ClinicalPathStageDTO;
import cn.hsa.module.clinical.inptclinicalpathstage.bo.InptClinicalPathStageBO;
import cn.hsa.module.clinical.inptclinicalpathstage.dao.InptClinicalPathStageDAO;
import cn.hsa.module.clinical.inptclinicalpathstage.dto.InptClinicalPathStageDTO;
import cn.hsa.module.clinical.inptclinicalpathstate.dao.InptClinicalPathStateDAO;
import cn.hsa.module.clinical.inptclinicalpathstate.dto.InptClinicalPathStateDTO;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.clinical.clinicalpathlist.entity
 * @Class_name: ClinicPathListDO
 * @Describe: 病人阶段病情记录业务处理层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/9/8 19:27
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class InptClinicalPathStageBOImpl implements InptClinicalPathStageBO {

  @Resource
  private InptClinicalPathStageDAO inptClinicalPathStageDAO;

  @Resource
  private ClinicalPathStageDAO clinicalPathStageDAO;

  @Resource
  private InptClinicalPathStateDAO inptClinicalPathStateDAO;

  /**
  * @Menthod getInptClinicalPathStageById
  * @Desrciption 查询单个入径阶段病情
  *
  * @Param
  * [inptClinicalPathStageDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/28 11:12
  * @Return cn.hsa.module.clinical.inptclinicalpathstage.dto.InptClinicalPathStageDTO
  **/
  @Override
  public InptClinicalPathStageDTO getInptClinicalPathStageById(InptClinicalPathStageDTO inptClinicalPathStageDTO) {
    return inptClinicalPathStageDAO.getInptClinicalPathStageById(inptClinicalPathStageDTO);
  }

  /**
  * @Menthod queryInptClinicalPathStageAll
  * @Desrciption 查询入径阶段病情
  *
  * @Param
  * [inptClinicalPathStageDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/28 11:12
  * @Return java.util.List<cn.hsa.module.clinical.inptclinicalpathstage.dto.InptClinicalPathStageDTO>
  **/
  @Override
  public List<InptClinicalPathStageDTO> queryInptClinicalPathStageAll(InptClinicalPathStageDTO inptClinicalPathStageDTO) {
    return inptClinicalPathStageDAO.queryInptClinicalPathStageAll(inptClinicalPathStageDTO);
  }

  /**
   * @Menthod queryInptClinicalPathStageAll
   * @Desrciption 查询入径阶段病情
   *
   * @Param
   * [inptClinicalPathStageDTO]
   *
   * @Author jiahong.yang
   * @Date   2021/9/28 11:12
   * @Return java.util.List<cn.hsa.module.clinical.inptclinicalpathstage.dto.InptClinicalPathStageDTO>
   **/
  @Override
  public PageDTO queryInptClinicalPathStagePage(InptClinicalPathStageDTO inptClinicalPathStageDTO) {
    PageHelper.startPage(inptClinicalPathStageDTO.getPageNo(),inptClinicalPathStageDTO.getPageSize());
    List<InptClinicalPathStageDTO> inptClinicalPathStageDTOS = inptClinicalPathStageDAO.queryInptClinicalPathStageAll(inptClinicalPathStageDTO);
    return PageDTO.of(inptClinicalPathStageDTOS);
  }

  /**
  * @Menthod saveInptClinicalPathStage
  * @Desrciption 保存入径阶段病情
  *
  * @Param
  * [inptClinicalPathStageDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/28 11:12
  * @Return boolean
  **/
  @Override
  public boolean saveInptClinicalPathStage(InptClinicalPathStageDTO inptClinicalPathStageDTO) {
    if(StringUtils.isEmpty(inptClinicalPathStageDTO.getId())) {
      throw new AppException("病情阶段错误");
    }
    inptClinicalPathStageDAO.updateInptClinicalPathStage(inptClinicalPathStageDTO);
    // 如果状态是2  进入下一阶段
    if("2".equals(inptClinicalPathStageDTO.getSaveFlag())) {

      /***用于查询下一阶段id**/
      if(StringUtils.isEmpty(inptClinicalPathStageDTO.getSortNo())){
        throw new AppException("当前阶段序号为空或者未传值，不能进入下一阶段");
      }
      ClinicalPathStageDTO clinicalPathStageDTO = new ClinicalPathStageDTO();
      clinicalPathStageDTO.setHospCode(inptClinicalPathStageDTO.getHospCode());
      clinicalPathStageDTO.setListId(inptClinicalPathStageDTO.getListId());
      clinicalPathStageDTO.setSortNo(inptClinicalPathStageDTO.getSortNo());
      // 查询下一阶段
      ClinicalPathStageDTO nextClinicalPathStage = clinicalPathStageDAO.getNextClinicalPathStage(clinicalPathStageDTO);
      if(nextClinicalPathStage == null) {
        throw new AppException("该阶段已是最后阶段，不能进入下一阶段");
      }
      /**用于更新入径病人当前阶段**/
      InptClinicalPathStateDTO inptClinicalPathStateDTO = new InptClinicalPathStateDTO();
      inptClinicalPathStateDTO.setHospCode(inptClinicalPathStageDTO.getHospCode());
      // 当前阶段
      inptClinicalPathStateDTO.setStageId(nextClinicalPathStage.getId());
      // 当前阶段名称
      inptClinicalPathStateDTO.setStageName(nextClinicalPathStage.getName());
      inptClinicalPathStateDTO.setId(inptClinicalPathStageDTO.getClinicalPathStageId());
      inptClinicalPathStateDAO.updateInptClinicalPathStateByVisitId(inptClinicalPathStateDTO);

      /**进入下一阶段*/
      InptClinicalPathStageDTO nextStage = new InptClinicalPathStageDTO();
      // 主键id
      nextStage.setId(SnowflakeUtils.getId());
      // 就诊id
      nextStage.setVisitId(inptClinicalPathStageDTO.getVisitId());
      // 医院编码
      nextStage.setHospCode(inptClinicalPathStageDTO.getHospCode());
      // 入径状态表id
      nextStage.setClinicalPathStageId(inptClinicalPathStageDTO.getClinicalPathStageId());
      // 路径目录id
      nextStage.setListId(inptClinicalPathStageDTO.getListId());
      // 阶段id
      nextStage.setStageId(nextClinicalPathStage.getId());
      // 阶段开始时间
      nextStage.setBeginTime(inptClinicalPathStageDTO.getCrteTime());
      // 创建人id
      nextStage.setCrteId(inptClinicalPathStageDTO.getCrteId());
      // 创建人姓名
      nextStage.setCrteName(inptClinicalPathStageDTO.getCrteName());
      // 创建时间
      nextStage.setCrteTime(inptClinicalPathStageDTO.getCrteTime());
      // 病情阶段新增下一阶段
      inptClinicalPathStageDAO.insertInptClinicalPathStage(nextStage);
    }
    // 如果状态是3  退出路径
    if("3".equals(inptClinicalPathStageDTO.getSaveFlag())) {
      InptClinicalPathStateDTO inptClinicalPathStateDTO = new InptClinicalPathStateDTO();
      inptClinicalPathStateDTO.setHospCode(inptClinicalPathStageDTO.getHospCode());
      inptClinicalPathStateDTO.setId(inptClinicalPathStageDTO.getClinicalPathStageId());
      inptClinicalPathStateDTO.setEndCrteId(inptClinicalPathStageDTO.getCrteId());
      inptClinicalPathStateDTO.setEndCrteTime(inptClinicalPathStageDTO.getEndCrteTime());
      inptClinicalPathStateDTO.setEndCrteName(inptClinicalPathStageDTO.getCrteName());
      inptClinicalPathStateDTO.setEndPathType(inptClinicalPathStageDTO.getEndPathType());
      inptClinicalPathStateDTO.setEndPathRemarke(inptClinicalPathStageDTO.getEndPathRemarke());
      inptClinicalPathStateDTO.setEndStageName(inptClinicalPathStageDTO.getStageName());
      inptClinicalPathStateDTO.setEndStageId(inptClinicalPathStageDTO.getStageId());
      inptClinicalPathStateDTO.setPathState("2");
      inptClinicalPathStateDAO.updateInptClinicalPathStateByVisitId(inptClinicalPathStateDTO);
    }
    return true;
  }

  /**
  * @Menthod deleteInptClinicalPathStage
  * @Desrciption  删除入径阶段病情
  *
  * @Param
  * [inptClinicalPathStageDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/9/28 11:12
  * @Return boolean
  **/
  @Override
  public boolean deleteInptClinicalPathStage(InptClinicalPathStageDTO inptClinicalPathStageDTO) {
    return inptClinicalPathStageDAO.deleteInptClinicalPathStage(inptClinicalPathStageDTO) > 0;
  }
}
