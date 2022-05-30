package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.inspectionreport.dto.*;
import cn.hsa.module.inpt.inspectionreport.service.InspectionReportService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.Constants;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
   * @Description: 检验检查报告，PACK查询打印
   * @Author: luonianxin
   * @Email: nianxin.luo@powersi.com
   * @Date: 2021/5/13 17:15
   * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
**/
@RestController
@RequestMapping("/web/inpt/inspectionReport")
public class InspectionReportController extends BaseController {

    @Resource
    private InspectionReportService inspectionReportService;

    @PostMapping(value = "/getPatientsTree")
    public WrapperResponse<List<TreeMenuNode>> getPatientsTree(@RequestBody PatientTreeQueryDTO patientTreeQueryDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> params = new HashMap<>();
        params.put("hospCode", sysUserDTO.getHospCode());
        patientTreeQueryDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        patientTreeQueryDTO.setHospCode(sysUserDTO.getHospCode());
        params.put("patientTreeQueryDTO",patientTreeQueryDTO);
        return inspectionReportService.getPatientsTree(params);
    }
    @GetMapping("/getPatientInfo")
//    public WrapperResponse<PatientInfoDTO> getPatientInfo(@RequestParam("inOrVisitNo") String inOrVisitNo){
    public WrapperResponse<PatientInfoDTO> getPatientInfo(PatientTreeQueryDTO patientTreeQueryDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        patientTreeQueryDTO.setHospCode(sysUserDTO.getHospCode());
        Map<String,Object> params = new HashMap<>();
        params.put("hospCode", sysUserDTO.getHospCode());
//        params.put("inOrVisitNo",inOrVisitNo);
        params.put("bean",patientTreeQueryDTO);
        return inspectionReportService.getPatientInfo(params);
    }

    @PostMapping("/getInspectionReportInfo")
    public WrapperResponse<InspectionReportDTO> getInspectionReportInfo(@RequestBody PatientInspectItem inspectItem, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap<>();
        ExaminationItem examinationItem = new ExaminationItem();
        examinationItem.setInNo(inspectItem.getInNo());
        examinationItem.setAdviceId(inspectItem.getOpdId());
        examinationItem.setApplyNo(inspectItem.getApplyNo());
        examinationItem.setZhId(inspectItem.getZhId());
        map.put("examinationItem",examinationItem);
        map.put("hospCode", sysUserDTO.getHospCode());
        InspectionReportDTO inspectionReportDTO = inspectionReportService.getInspectionReportInfo(map).getData();
        if(inspectionReportDTO != null) {
            inspectionReportDTO.setHospName(sysUserDTO.getHospName());
            inspectionReportDTO.setContent(inspectItem.getContent());
        }
        return WrapperResponse.success(inspectionReportDTO);
    }


    /**
       * @Describe: 根据住院号或就诊号，申请时间查询检验检测类型列表(LIS)
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/18 16:48
    **/
    @PostMapping("/getPatientInspectTypeItems")
    public WrapperResponse<List<PatientInspectItem>> getPatientInspectTypeItems(@RequestBody PatientInspectItem inspectItem, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        if(inspectItem == null){
            throw new AppException("必填参数为空，请检查");
        }
        Map map = new HashMap(4);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("patientInspectItem",inspectItem);
        return inspectionReportService.getPatientInspectItems(map);
    }

    /**
       * @Describe: 获取检查报告具体信息(PACS影像)
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/19 14:22
    **/
    @PostMapping("/getPatientPACSImageTypeItems")
    public WrapperResponse<List<PatientInspectItem>> getPatientPACSImageTypeItems(@RequestBody PatientInspectItem inspectItem, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        if(inspectItem == null){
            throw new AppException("必填参数为空，请检查");
        }
        Map map = new HashMap(4);
        map.put("hospCode", sysUserDTO.getHospCode());
        inspectItem.setTypeCode(Constants.InspectionType.PACS);
        map.put("patientInspectItem",inspectItem);
        return inspectionReportService.getPatientInspectItems(map);
    }

    /**
     * @Describe: 根据住院号或就诊号，申请时间查询检测类型列表(PACS影像)
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/5/19 14:22
     **/
    @PostMapping("/getPacsImageReportInfo")
    public WrapperResponse<PacsImageReportDTO> getPacsImageReportInfo(@RequestBody PatientInspectItem patientInspectItem, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        if(patientInspectItem == null){
            throw new AppException("必填参数为空，请检查");
        }
        Map map = new HashMap(4);
        map.put("hospCode", sysUserDTO.getHospCode());
        patientInspectItem.setHospCode(sysUserDTO.getHospCode());
        map.put("patientInspectItem",patientInspectItem);
        WrapperResponse<PacsImageReportDTO> result = inspectionReportService.getPacsImageReportInfo(map);
        PacsImageReportDTO pacsImageReportDTO = result.getData();
        if(pacsImageReportDTO == null) {
            return WrapperResponse.success(null);
        }
        pacsImageReportDTO.setHospName(sysUserDTO.getHospName());
        pacsImageReportDTO.setContent(patientInspectItem.getContent());

        return WrapperResponse.success(pacsImageReportDTO);
    }

    @GetMapping("/getHospitalInfo")
    public WrapperResponse<PageDTO> getHospitalInfo(Map<String,String> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,String> map1 = new HashMap<>();
        map1.put("hospCode", sysUserDTO.getHospCode());
        return inspectionReportService.getHospitalInfo(map1);
    }

    /**
     * @description 根据住院号查询病人lis信息
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/1/4 15:06
     **/
    @GetMapping("/queryPatientAllMedicResult")
    public WrapperResponse<List<ExaminationItem>> queryPatientAllMedicResult(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map =new HashMap();
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO", inptVisitDTO);
        return inspectionReportService.queryPatientAllMedicResult(map);
    }
}