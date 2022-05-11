package cn.hsa.module.insure.InsureClinica;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.clinica.dto.*;
import cn.hsa.module.insure.inpt.service.InsureUnifiedClinicalService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


/**
 * @Package_name: cn.hsa.module.insure
 * @class_name: InsureUnifiedClinicalControler
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/9/3 15:21
 * @Company: 创智和宇
 **/
@RestController
@RequestMapping("/web/insure/insureUnifiedClinical")
@Slf4j
public class InsureUnifiedClinicalController extends BaseController {

    @Resource
    private InsureUnifiedClinicalService insureUnifiedClinicalService_consumer;

    /**
     * @Method updateClinicalReportRecord
     * @Desrciption 临床检查报告记录 -- 上传到医保
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     **/
    @PostMapping("/updateClinicalExaminationReportRecord")
    public WrapperResponse<Boolean> updateClinicalExaminationReportRecord(@RequestBody ClinicalExaminationInfoDTO clinicalExaminationInfoDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<String,Object>();
        clinicalExaminationInfoDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("clinicalExaminationInfoDTO",clinicalExaminationInfoDTO);
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedClinicalService_consumer.updateClinicalExaminationReportRecord(map);
    }

    /**
     * @Method insertClinicalExaminationReportRecord
     * @Desrciption 临床检查报告记录  -- 保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     **/
    @PostMapping("/insertClinicalExaminationReportRecord")
    public WrapperResponse<Boolean> insertClinicalExaminationReportRecord(@RequestBody ClinicalExaminationInfoDTO clinicalExaminationInfoDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<String,Object>();
        clinicalExaminationInfoDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("clinicalExaminationInfoDTO",clinicalExaminationInfoDTO);
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedClinicalService_consumer.insertClinicalExaminationReportRecord(map);
    }


    /**
     * @Method queryPageClinicalExaminationReportRecord
     * @Desrciption 临床检查报告记录  -- 分页查询
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     **/
    @GetMapping("/queryPageClinicalExaminationReportRecord")
    public WrapperResponse<PageDTO> queryPageClinicalExaminationReportRecord(ClinicalExaminationInfoDTO clinicalExaminationInfoDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<String,Object>();
        clinicalExaminationInfoDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("clinicalExaminationInfoDTO",clinicalExaminationInfoDTO);
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedClinicalService_consumer.queryPageClinicalExaminationReportRecord(map);
    }

    /**
     * @Method updateClinicalReportRecord
     * @Desrciption 临床检验报告记录 ---上传到医保
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     **/
    @PostMapping("/updateClinicalReportRecord")
    public WrapperResponse<Boolean> updateClinicalReportRecord(@RequestBody InsureClinicalCheckoutDTO insureClinicalCheckoutDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<String,Object>();
        insureClinicalCheckoutDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureClinicalCheckoutDTO",insureClinicalCheckoutDTO);
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedClinicalService_consumer.updateClinicalReportRecord(map);
    }


    /**
     * @Method insertClinicalReportRecord
     * @Desrciption 临床检验报告记录 -- 保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     **/
    @PostMapping("/insertClinicalReportRecord")
    public WrapperResponse<Boolean> insertClinicalReportRecord(@RequestBody InsureClinicalCheckoutDTO insureClinicalCheckoutDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<String,Object>();
        insureClinicalCheckoutDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureClinicalCheckoutDTO",insureClinicalCheckoutDTO);
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedClinicalService_consumer.insertClinicalReportRecord(map);
    }



    /**
     * @Method queryPageClinicalReportRecord
     * @Desrciption 临床检验报告记录 -- 分页查询his数据
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     **/
    @GetMapping("/queryPageClinicalReportRecord")
    public WrapperResponse<PageDTO> queryPageClinicalReportRecord(InsureClinicalCheckoutDTO insureClinicalCheckoutDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<String,Object>();
        insureClinicalCheckoutDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureClinicalCheckoutDTO",insureClinicalCheckoutDTO);
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedClinicalService_consumer.queryPageClinicalReportRecord(map);
    }

    /**
     * @Method updateBacterialReportRecord
     * @Desrciption 细菌培养报告记录 -- 上传到医保
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     **/
    @PostMapping("/updateBacterialReportRecord")
    public WrapperResponse<Boolean> updateBacterialReportRecord(@RequestBody InsureBacterialReportDTO insureBacterialReportDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<String,Object>();
        insureBacterialReportDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureBacterialReportDTO",insureBacterialReportDTO);
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedClinicalService_consumer.updateBacterialReportRecord(map);
    }


    /**
     * @Method insertBacterialReportRecord
     * @Desrciption 细菌培养报告记录 -- 保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     **/
    @PostMapping("/insertBacterialReportRecord")
    public WrapperResponse<Boolean> insertBacterialReportRecord(@RequestBody InsureBacterialReportDTO insureBacterialReportDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<String,Object>();
        insureBacterialReportDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureBacterialReportDTO",insureBacterialReportDTO);
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedClinicalService_consumer.insertBacterialReportRecord(map);
    }


    /**
     * @Method queryPageBacterialReportRecord
     * @Desrciption 细菌培养报告记录 -- 分页查询
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     **/
    @GetMapping("/queryPageBacterialReportRecord")
    public WrapperResponse<PageDTO> queryPageBacterialReportRecord(InsureBacterialReportDTO insureBacterialReportDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<String,Object>();
        insureBacterialReportDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureBacterialReportDTO",insureBacterialReportDTO);
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedClinicalService_consumer.queryPageBacterialReportRecord(map);
    }

    /**
     * @Method updateDrugSensitivityReportRecord
     * @Desrciption 药敏记录报告记录 --- 上传到医保
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     **/
    @PostMapping("/updateDrugSensitivityReportRecord")
    public WrapperResponse<Boolean> updateDrugSensitivityReportRecord(@RequestBody InsureDrugsensitiveReportDTO insureDrugsensitiveReportDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<String,Object>();
        insureDrugsensitiveReportDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureDrugsensitiveReportDTO",insureDrugsensitiveReportDTO);
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedClinicalService_consumer.updateDrugSensitivityReportRecord(map);
    }


    /**
     * @Method insertDrugSensitivityReportRecord
     * @Desrciption 药敏记录报告记录  ----保存到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     **/
    @PostMapping("/insertDrugSensitivityReportRecord")
    public WrapperResponse<Boolean> insertDrugSensitivityReportRecord(@RequestBody InsureDrugsensitiveReportDTO insureDrugsensitiveReportDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<String,Object>();
        insureDrugsensitiveReportDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureDrugsensitiveReportDTO",insureDrugsensitiveReportDTO);
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedClinicalService_consumer.insertDrugSensitivityReportRecord(map);
    }

    /**
     * @Method queryDrugSensitivityReportRecord
     * @Desrciption 药敏记录报告记录  ----分页查询
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     **/
    @GetMapping("/queryDrugSensitivityReportRecord")
    public WrapperResponse<PageDTO> queryDrugSensitivityReportRecord(InsureDrugsensitiveReportDTO insureDrugsensitiveReportDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<String,Object>();
        insureDrugsensitiveReportDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureDrugsensitiveReportDTO",insureDrugsensitiveReportDTO);
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedClinicalService_consumer.queryDrugSensitivityReportRecord(map);
    }


    /**
     * @Method updatePathologicalReportRecord
     * @Desrciption 病理检查报告记录---上传到医保
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     **/
    @PostMapping("/updatePathologicalReportRecord")
    public WrapperResponse<Boolean> updatePathologicalReportRecord(@RequestBody InsurePathologicalReportDTO insurePathologicalReportDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<String,Object>();
        insurePathologicalReportDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insurePathologicalReportDTO",insurePathologicalReportDTO);
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedClinicalService_consumer.updatePathologicalReportRecord(map);
    }

    /**
     * @Method updatePathologicalReportRecord
     * @Desrciption 病理检查报告记录---新增到his数据库
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     **/
    @PostMapping("/insertPathologicalReportRecord")
    public WrapperResponse<Boolean> insertPathologicalReportRecord(@RequestBody InsurePathologicalReportDTO insurePathologicalReportDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<String,Object>();
        insurePathologicalReportDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insurePathologicalReportDTO",insurePathologicalReportDTO);
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedClinicalService_consumer.insertPathologicalReportRecord(map);
    }

    /**
     * @Method queryPagePathologicalReportRecord
     * @Desrciption 病理检查报告记录---分页查询
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     **/
    @GetMapping("/queryPagePathologicalReportRecord")
    public WrapperResponse<PageDTO> queryPagePathologicalReportRecord(InsurePathologicalReportDTO insurePathologicalReportDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<String,Object>();
        insurePathologicalReportDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insurePathologicalReportDTO",insurePathologicalReportDTO);
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedClinicalService_consumer.queryPagePathologicalReportRecord(map);
    }

    /**
     * @Method updateNoStructReportRecord
     * @Desrciption 非结构化报告记录--上传到医保
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     **/
    @PostMapping("/updateNoStructReportRecord")
    public WrapperResponse<Boolean> updateNoStructReportRecord(Map<String, Object> map, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedClinicalService_consumer.updateNoStructReportRecord(map);
    }

    /**
     * @Method updateNoStructReportRecord
     * @Desrciption 非结构化报告记录--新增数据
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     **/
    @PostMapping("/insertNoStructReportRecord")
    public WrapperResponse<Boolean> insertNoStructReportRecord(Map<String, Object> map, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedClinicalService_consumer.insertNoStructReportRecord(map);
    }

    /**
     * @Method queryPageNoStructReportRecord
     * @Desrciption 非结构化报告记录--分页查询
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 10:18
     * @Return
     **/
    @GetMapping("/queryPageNoStructReportRecord")
    public WrapperResponse<PageDTO> queryPageNoStructReportRecord(Map<String, Object> map, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedClinicalService_consumer.queryPageNoStructReportRecord(map);
    }
}
