package cn.hsa.clinical.inptclinicalpathstate.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.clinical.clinicalpathstage.dao.ClinicalPathStageDAO;
import cn.hsa.module.clinical.clinicalpathstage.dto.ClinicalPathStageDTO;
import cn.hsa.module.clinical.clinicalpathstagedetail.dto.ClinicPathStageDetailDTO;
import cn.hsa.module.clinical.inptclinicalpathstate.bo.InptClinicalPathStateBO;
import cn.hsa.module.clinical.inptclinicalpathstate.dao.InptClinicalPathStateDAO;
import cn.hsa.module.clinical.inptclinicalpathstate.dto.InptClinicalPathStateDTO;
import cn.hsa.module.emr.emrclassify.dto.EmrClassifyDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.util.ListUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.Op;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InptClinicalPathStateBOImpl implements InptClinicalPathStateBO {
    @Resource
    private InptClinicalPathStateDAO inptClinicalPathStateDAO;

    @Resource
    private ClinicalPathStageDAO clinicalPathStageDAO;

    /**
     * @Meth: queryClinicalPathStageDetail
     * @Description: 根据目录id 、阶段id查询阶段明细(供诊疗、其他、护理使用)
     * @Param: [clinicPathStageDetailDTO]
     * @return: cn.hsa.base.PageDTO
     * @Author: zhangguorui
     * @Date: 2021/9/23
     */
    @Override
    public PageDTO queryClinicalPathStageDetail(InptClinicalPathStateDTO inptClinicalPathStateDTO) {
        PageHelper.startPage(inptClinicalPathStateDTO.getPageNo(), inptClinicalPathStateDTO.getPageSize());
        // 判断
        Optional.ofNullable(inptClinicalPathStateDTO.getHospCode()).orElseThrow(() -> new AppException("医院编码不能为空"));
        Optional.ofNullable(inptClinicalPathStateDTO.getListId()).orElseThrow(() -> new AppException("路径目录id不能为空"));
        Optional.ofNullable(inptClinicalPathStateDTO.getStageId()).orElseThrow(() -> new AppException("当前阶段id不能为空"));
        // 查询临床路径阶段明细表、临床路径项目表获得阶段项目名称
        List<ClinicPathStageDetailDTO> clinicPathStageDetailDTOS = inptClinicalPathStateDAO.queryClinicalItem(inptClinicalPathStateDTO);
        return PageDTO.of(clinicPathStageDetailDTOS);
    }

    /**
     * @Meth: queryInptClinicalPathState
     * @Description: 根据条件查询临床路径病人信息
     * @Param: [inptClinicalPathStateDTO]
     * @return: cn.hsa.base.PageDTO
     * @Author: zhangguorui
     * @Date: 2021/9/23
     */
    @Override
    public PageDTO queryInptClinicalPathState(InptClinicalPathStateDTO inptClinicalPathStateDTO) {
        PageHelper.startPage(inptClinicalPathStateDTO.getPageNo(),inptClinicalPathStateDTO.getPageSize());
        List<InptClinicalPathStateDTO> resultList = inptClinicalPathStateDAO.queryInptClinicalPathState(inptClinicalPathStateDTO);
        return PageDTO.of(resultList);
    }

    /**
     * @Meth: queryInptClinicalPathStateByVisitId
     * @Description: 通过病人信息查询单个临床病人记录
     * @Param: [inptClinicalPathStateDTO]
     * @return: cn.hsa.module.clinical.inptclinicalpathstate.dto.InptClinicalPathStateDTO
     * @Author: zhangguorui
     * @Date: 2021/9/23
     */
    @Override
    public InptClinicalPathStateDTO queryInptClinicalPathStateByVisitId(InptClinicalPathStateDTO inptClinicalPathStateDTO) {
        Optional.ofNullable(inptClinicalPathStateDTO.getHospCode()).orElseThrow(() -> new AppException("医院编码不能为空"));
        InptClinicalPathStateDTO inptClinicalPathState = inptClinicalPathStateDAO.queryInptClinicalPathStateByVisitId(inptClinicalPathStateDTO);
        return Optional.ofNullable(inptClinicalPathState).orElse(new InptClinicalPathStateDTO());
    }

    /**
    * @Menthod updateInptClinicalPathStateByVisitId
    * @Desrciption 修改病人入径信息
    *
    * @Param
    * [inptClinicalPathStateDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/9/24 14:20
    * @Return java.lang.Boolean
    **/
    @Override
    public Boolean updateInptClinicalPathStateByVisitId(InptClinicalPathStateDTO inptClinicalPathStateDTO) {
      return inptClinicalPathStateDAO.updateInptClinicalPathStateByVisitId(inptClinicalPathStateDTO) > 0;
    }

    /**
    * @Menthod insertInptClinicalPathStateByVisitId
    * @Desrciption 新增病人入径
    *
    * @Param
    * [inptClinicalPathStateDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/9/24 14:20
    * @Return java.lang.Boolean
    **/
    @Override
    public Boolean insertInptClinicalPathStateByVisitId(InptClinicalPathStateDTO inptClinicalPathStateDTO) {
      if(StringUtils.isEmpty(inptClinicalPathStateDTO.getListId())) {
        throw new AppException("路径目录为空");
      }
      // 前端传过来得数据
      List<InptClinicalPathStateDTO> inptClinicalPathStateDTOList = inptClinicalPathStateDTO.getInptClinicalPathStateDTOList();
      if(ListUtils.isEmpty(inptClinicalPathStateDTOList)) {
        throw new AppException("新增数据为空");
      }
      ClinicalPathStageDTO clinicalPathStageDTO = new ClinicalPathStageDTO();
      clinicalPathStageDTO.setHospCode(inptClinicalPathStateDTO.getHospCode());
      clinicalPathStageDTO.setListId(inptClinicalPathStateDTO.getListId());
      // 根据序号排序  获取改目录下的所有阶段
      List<ClinicalPathStageDTO> clinicalPathStageDTOS = clinicalPathStageDAO.queryClinicalPathStage(clinicalPathStageDTO);
      if(ListUtils.isEmpty(clinicalPathStageDTOS)) {
        throw new AppException("该目录没有阶段，请先添加阶段");
      }
      // 默认为第一阶段
      String stageId = clinicalPathStageDTOS.get(0).getId();

      for(InptClinicalPathStateDTO item : inptClinicalPathStateDTOList) {
        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        inptVisitDTO.setHospCode(inptClinicalPathStateDTO.getHospCode());
        inptVisitDTO.setId(item.getVisitId());
        // 根据就诊id查询患者信息
        List<InptVisitDTO> inptVisitDTOS = inptClinicalPathStateDAO.queryPatientPage(inptVisitDTO);
        if(ListUtils.isEmpty(inptVisitDTOS)) {
          throw new AppException(item.getName() + "不是在院状态");
        }
        if("1".equals(inptVisitDTOS.get(0).getIsClinicalList())) {
          throw new AppException(item.getName() + "已入径");
        }
        // 医院编码
        item.setHospCode(inptClinicalPathStateDTO.getHospCode());
        // 主键id
        item.setId(SnowflakeUtils.getId());
        // 路径目录id
        item.setListId(inptClinicalPathStateDTO.getListId());
        // 创建人
        item.setPathCrteId(inptClinicalPathStateDTO.getPathCrteId());
        // 创建人姓名
        item.setPathCrteName(inptClinicalPathStateDTO.getPathCrteName());
        // 创建时间
        item.setPathCrteTime(inptClinicalPathStateDTO.getPathCrteTime());
        // 默认第一阶段
        item.setStageId(stageId);
        item.setPathState("1");
      }
      return inptClinicalPathStateDAO.insertInptClinicalPathStateByVisitId(inptClinicalPathStateDTOList) > 0;
    }

    /**
    * @Menthod queryDeptByClinicalPathDeptId
    * @Desrciption 查询科室
    *
    * @Param
    * [inptClinicalPathStateDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/9/24 16:42
    * @Return java.util.List<cn.hsa.module.base.dept.dto.BaseDeptDTO>
    **/
    @Override
    public List<BaseDeptDTO> queryDeptByClinicalPathDeptId(InptClinicalPathStateDTO inptClinicalPathStateDTO) {
      return inptClinicalPathStateDAO.queryDeptByClinicalPathDeptId(inptClinicalPathStateDTO);
    }

    /**
    * @Menthod queryInptClinicalPathState
    * @Desrciption 查询患者信息
    *
    * @Param
    * [inptVisitDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/9/26 11:27
    * @Return cn.hsa.base.PageDTO
    **/
    @Override
    public PageDTO queryPatientPage(InptVisitDTO inptVisitDTO) {
      PageHelper.startPage(inptVisitDTO.getPageNo(),inptVisitDTO.getPageSize());
      List<InptVisitDTO> inptVisitDTOS = inptClinicalPathStateDAO.queryPatientPage(inptVisitDTO);
      return PageDTO.of(inptVisitDTOS);
    }

    /**
    * @Menthod getPatientByVisitID
    * @Desrciption 用于出径病人信息展示
    *
    * @Param
    * [inptClinicalPathStateDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/9/27 15:01
    * @Return cn.hsa.module.clinical.inptclinicalpathstate.dto.InptClinicalPathStateDTO
    **/
    @Override
    public InptClinicalPathStateDTO getPatientByVisitID(InptClinicalPathStateDTO inptClinicalPathStateDTO) {
      if(StringUtils.isEmpty(inptClinicalPathStateDTO.getVisitId())) {
        throw new AppException("患者信息为空");
      }
      if(StringUtils.isEmpty(inptClinicalPathStateDTO.getListId())) {
        throw new AppException("该患者路径目录为空");
      }
      return inptClinicalPathStateDAO.getPatientByVisitID(inptClinicalPathStateDTO);
    }

}
