package cn.hsa.module.inpt.inspectionreport.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.inspectionreport.dto.*;

import java.util.List;
import java.util.Map;

/**
   * @Describe: 检验检查报告，PACK影像查询打印
   * @Author: luonianxin
   * @Email: nianxin.luo@powersi.com
   * @Date: 2021/5/13 19:28
   * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
**/
public interface InspectionReportBO {

    /**
       * @Describe: 查询检验报告页面的用户树列表数据
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/13 19:31
    **/
    List<TreeMenuNode> getPatientsTree(PatientTreeQueryDTO patientTreeQueryDTO);

    /**
     * @Describe: 查询检验报告病人详细信息
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/13 19:31
     **/
    PatientInfoDTO getPatientInfo(String inNO_or_VisitNo);

    /**
       * @Describe: 根据就诊号或住院号查询病人检查项目
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/17 14:26
    **/
    List<PatientInspectItem> getPatientInspectItems(PatientInspectItem inspectItem);

    /**
     * 根据住院号或就诊号以及处方x
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/18 8:39
     **/
    InspectionReportDTO getInspectionReportInfo(Map params);

    /**
     * @Describe: 根据就诊号（住院号）以及医嘱号查询病人pacs检测报告详情
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/19 18:20
     **/
    PacsImageReportDTO getPacsImageReportInfo(PatientInspectItem patientInspectItem);

    /**
     * @Describe: 根据就诊号（住院号）以及医嘱号查询病人pacs检测报告详情
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/19 18:20
     **/
    PageDTO getHospitalInfo(Map<String,String> params);

    /**
     * @description 根据住院号查询病人lis信息
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/1/4 14:45
     **/
    List<ExaminationItem> queryPatientAllMedicResult(InptVisitDTO inptVisitDTO);
}
