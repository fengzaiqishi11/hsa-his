package cn.hsa.module.inpt.inspectionreport.dao;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.inspectionreport.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
   * @Describe: 检查检验报告查询数据库访问层
   * @Author: luonianxin
   * @Email: nianxin.luo@powersi.com
   * @Date: 2021/5/13 19:36
**/
@Mapper
public interface InspectionReportDAO {

    /**
       * @Describe: 获取病人树列表(部门部分)
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/13 20:10
    **/
    List<TreeMenuNode> getPatientsTreeOfDepartment(PatientTreeQueryDTO queryDTO);
    /**
     * @Describe: 获取病人树列表(病人数据部分)
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/29 14:53
     **/
    List<TreeMenuNode> getPatientsTreeOfPatients(PatientTreeQueryDTO queryDTO);

    /**
       * @Describe: 根据就诊号或住院号获取病人基本信息
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/17 10:48
    **/
    PatientInfoDTO getPatientInfo(@Param("inVisitNo") String inVisitNo);

    /**
       * @Describe: 根据住院号或就诊号查询病人检验检查信息
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/18 8:39
    **/
    List<PatientInspectItem> getPatientInspectItems(PatientInspectItem patientInspectItem);

    /**
       * 根据住院号或就诊号查询病人检验检查信息
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/18 8:39
    **/
    List<ExaminationItem> getExaminationItems(ExaminationItem examinationItem);

    /**
       * 根据住院号或就诊号以及处方x
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/18 8:39
    **/
    InspectionReportDTO getInspectionReportInfo(ExaminationItem examinationItem);

    /**
       * @Describe: 根据就诊号（住院号）以及医嘱号查询病人pacs检测报告详情
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/19 18:20
    **/
    PacsImageReportDTO getPacsImageReportInfo(PatientInspectItem patientInspectItem);

    Map<String,String> getDepartmentById(Map<String,String> map);

    /**
       * 获取医院基本信息
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/25 17:43
    **/
    List<Map<String,String>> getHospitalInfo(Map<String,String> map);
    /**
       * 获取所有顶级部门代码列表
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/6/29 16:27
    **/
    List<String> getAllParentDeptCode();

    /**
     * @description 根据住院号查询病人lis信息
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/1/4 14:45
     **/
    List<ExaminationItem> queryPatientAllMedicResult(InptVisitDTO inptVisitDTO);
}
