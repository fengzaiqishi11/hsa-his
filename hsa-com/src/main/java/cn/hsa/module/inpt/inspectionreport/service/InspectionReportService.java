package cn.hsa.module.inpt.inspectionreport.service;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.inspectionreport.dto.*;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;
import java.util.Map;

/**
   * @author: luonianxin
   * @Email: nianxin.luo@powersi.com
   * @Date: 2021/5/13 19:41
   * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
**/
@FeignClient(value = "hsa-inpt")
public interface InspectionReportService {

    WrapperResponse<List<TreeMenuNode>> getPatientsTree(Map map);
    /**
       * @Class_name: InspectionReportService
       * @Describe: 根据就诊号或住院号查询病人相关信息
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/17 11:13
    **/
    WrapperResponse<PatientInfoDTO> getPatientInfo(Map map);

    /**
       * @Describe: 查询检测检验报告详情
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/18 11:24
    **/
    WrapperResponse<InspectionReportDTO> getInspectionReportInfo(Map map);

    /**
       * @Describe: 查询病人的检验检测项目类型列表
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/18 11:24
    **/
    WrapperResponse<List<PatientInspectItem>> getPatientInspectItems(Map map);

    /**
     * @Describe: 根据就诊号（住院号）以及医嘱号查询病人pacs检测报告详情
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/19 18:20
     **/
    WrapperResponse<PacsImageReportDTO> getPacsImageReportInfo(Map map);

    /**
     *  根据医院code查询医院参数信息
     * @param map  查询参数
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/19 18:20
     **/
    WrapperResponse<PageDTO> getHospitalInfo(Map map);
}
