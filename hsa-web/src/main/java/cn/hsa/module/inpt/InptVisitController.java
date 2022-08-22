package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.NoRepeatSubmit;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.doctor.dto.InptBabyDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.inregister.service.InptVisitService;
import cn.hsa.module.insure.inpt.service.InsureUnifiedPayInptService;
import cn.hsa.module.insure.outpt.service.InsureUnifiedPayMatterService;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Package_name: cn.hsa.module.inpt
 * @Class_name: InptVisitController
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/9/15 15:16
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/inpt/visit")
@Slf4j
public class InptVisitController extends BaseController {

    @Resource
    InptVisitService inptVisitService_consumer;

    @Resource
    InsureUnifiedPayInptService insureUnifiedPayInptService_consumer;

    /**
     * @Method queryRegisteredPage
     * @Desrciption 查询已就诊的记录
     * @Param [inptVisitDTO]
     * @Author liaojunjie
     * @Date 2020/9/25 9:04
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping(value = "/queryRegisteredPage")
    public WrapperResponse<PageDTO> queryRegisteredPage(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO", inptVisitDTO);
        return inptVisitService_consumer.queryRegisteredPage(map);
    }

    /**
     * @Method queryUnregisteredPage
     * @Desrciption 查询未就诊但门诊需要转入院的记录
     * @Param [inptVisitDTO]
     * @Author liaojunjie
     * @Date 2020/9/25 9:05
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping(value = "/queryUnregisteredPage")
    public WrapperResponse<PageDTO> queryUnregisteredPage(OutptVisitDTO outptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        outptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("outptVisitDTO", outptVisitDTO);
        return inptVisitService_consumer.queryUnregisteredPage(map);
    }

    /**
     * @Method save
     * @Desrciption 新增/编辑入院登记
     * @Param [inptVisitDTO]
     * @Author liaojunjie
     * @Date 2020/9/25 9:05
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @NoRepeatSubmit
    @PostMapping(value = "/save")
    public WrapperResponse<String> save(@RequestBody InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        inptVisitDTO.setCrteId(sysUserDTO.getId());
        inptVisitDTO.setCrteName(sysUserDTO.getName());
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO", inptVisitDTO);
        return inptVisitService_consumer.save(map);

    }


    /**
     * @Method getInptVisitById
     * @Desrciption 获取就诊信息
     * @Param [inptVisitDTO]
     * @Author caoliang
     * @Date 2020/12/21 20:44
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping(value = "/queryByCertNo")
    public WrapperResponse<InptVisitDTO> queryByCertNo(@RequestBody InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO", inptVisitDTO);
        return inptVisitService_consumer.queryByCertNo(map);
    }

    /**
     * @Method deleteRegister
     * @Desrciption 取消入院登记
     * @Param [inptVisitDTO]
     * @Author liaojunjie
     * @Date 2020/9/25 9:06
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping(value = "/deleteRegister")
    public WrapperResponse<Boolean> deleteRegister(@RequestBody InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO", inptVisitDTO);
        return inptVisitService_consumer.deleteRegister(map);
    }
    /**
     * @Method queryPrintInpt
     * @Desrciption 查询打印住院证
     * @Param [OutptVisitDTO]
     * @Author yuelong.chen
     * @Date 2021/11/22 16:08
     * @Return cn.hsa.base.PageDTO
     **/
    @GetMapping(value = "/queryPrintInpt")
    public WrapperResponse<OutptVisitDTO> queryPrintInpt(@RequestParam("id") String id, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("id", id);
        return inptVisitService_consumer.queryPrintInpt(map);
    }
    /**
     * @Method deleteInsureRegister
     * @Desrciption 取消医保入院登记
     * @Param [inptVisitDTO]
     * @Author 廖继广
     * @Date 2020/12/21 18:59
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping(value = "/deleteInsureRegister")
    public WrapperResponse<Boolean> deleteInsureRegister(@RequestBody InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(inptVisitDTO.getId())) {
            throw new AppException("参数错误:未获取到就诊ID");
        }
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        inptVisitDTO.setCrteId(sysUserDTO.getId());
        inptVisitDTO.setCrteName(sysUserDTO.getName());
        inptVisitDTO.setUserCode(sysUserDTO.getCode());
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO", inptVisitDTO);
        return inptVisitService_consumer.deleteInsureRegister(map);
    }

    /**
     * @Method queryInsureRegisterPage
     * @Desrciption 医保登记人员信息获取
     * @Param [inptVisitDTO]
     * @Author 廖继广
     * @Date 2020/12/22 14:43
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @GetMapping(value = "/queryInsureRegisterPage")
    public WrapperResponse<PageDTO> queryInsureRegisterPage(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        if (StringUtils.isEmpty(inptVisitDTO.getIsInsureRegister())) {
            throw new AppException("isInsureRegister参数为空:0未登记 1已登记");
        }

        Map map = new HashMap<>();
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO", inptVisitDTO);
        return inptVisitService_consumer.queryInsureRegisterPage(map);
    }


    /**
     * @Method saveInsureInRegister
     * @Desrciption 医保入院登记
     * @Param [inptVisitDTO]
     * @Author 廖继广
     * @Date 2020/12/21 20:44
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping(value = "/saveInsureInRegister")
    public WrapperResponse<Boolean> saveInsureInRegister(@RequestBody InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap<>();
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        inptVisitDTO.setCrteId(sysUserDTO.getId());
        inptVisitDTO.setCrteName(sysUserDTO.getName());
        inptVisitDTO.setUserCode(sysUserDTO.getCode());
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO", inptVisitDTO);
        return inptVisitService_consumer.saveInsureInRegister(map);
    }
    // 农合
    @PostMapping(value = "/saveNHInsureData")
    public WrapperResponse<Boolean> saveNHInsureData(@RequestBody InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap<>();
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO", inptVisitDTO);
        return inptVisitService_consumer.saveNHInsureData(map);
    }

    // 农合
    @PostMapping(value = "/saveNHInsureDrugItemData")
    public WrapperResponse<Boolean> saveNHInsureDrugItemData(@RequestBody Map<String, Object> tempMark, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("mark", MapUtils.get(tempMark, "mark"));
        map.put("ids", MapUtils.get(tempMark, "ids"));
        return inptVisitService_consumer.saveNHInsureDrugItemData(map);
    }
    /**
     * @Method getInptVisitById
     * @Desrciption 获取就诊信息
     * @Param [inptVisitDTO]
     * @Author zengfeng
     * @Date 2020/12/21 20:44
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping(value = "/getInptVisitById")
    public WrapperResponse<InptVisitDTO> getInptVisitById(@RequestBody InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO", inptVisitDTO);
        return inptVisitService_consumer.getInptVisitById(map);
    }

    //事前事中分析服务
    @Resource
    private InsureUnifiedPayMatterService insureUnifiedPayMatterService_consumer;

    @PostMapping("/UP_3660")
    public WrapperResponse<Map<String, Object>> UP_3660(@RequestBody Map<String,Object> paramMap, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        paramMap.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedPayMatterService_consumer.UP_3660(paramMap);
    }

    @PostMapping("/UP_3661")
    public WrapperResponse<Map<String, Object>> UP_3661(@RequestBody Map<String,Object> paramMap, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        paramMap.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedPayMatterService_consumer.UP_3661(paramMap);
    }

    /**
    * @Menthod saveInsureInRegisterEMD
    * @Desrciption  医保电子凭证登记
     * @param inptVisitDTO
    * @Author xingyu.xie
    * @Date   2021/3/1 14:45
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/saveInsureInRegisterEMD")
    public WrapperResponse<Boolean> saveInsureInRegisterEMD(@RequestBody InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());//医院编码
        inptVisitDTO.setCrteId(sysUserDTO.getId());//创建人id
        inptVisitDTO.setCrteName(sysUserDTO.getName());//创建人姓名
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("hospCode", sysUserDTO.getHospCode());//医院编码
        param.put("hospName", sysUserDTO.getHospName());//医院名称
        param.put("inptVisitDTO",inptVisitDTO);//请求参数
        if (inptVisitDTO.getInsureIndividualBasicDTO() == null || StringUtils.isEmpty(inptVisitDTO.getElectronicBillParam())){
            return WrapperResponse.error(WrapperResponse.FAIL,"请先获取就诊人员参保信息",false);
        }
        return inptVisitService_consumer.saveInsureInRegisterEMD(param);
    }

    /**
     * @Menthod queryPatients
     * @Desrciption  在院病人信息查询
     * @param
     * @Author pengbo
     * @Date   2021/3/5 14:45
     * @return PageDTO
     **/
    @GetMapping("/queryPatients")
    public WrapperResponse<PageDTO> queryPatients(HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String, Object>  paramMap = new HashMap<>();
        paramMap.put("hospCode", sysUserDTO.getHospCode());
        paramMap.put("hospName", sysUserDTO.getHospName());
        paramMap.put("pageNo", req.getParameter("pageNo"));
        paramMap.put("pageSize", req.getParameter("pageSize"));
        paramMap.put("keyword", req.getParameter("keyword"));
        paramMap.put("status_code", req.getParameter("status_code"));
        paramMap.put("in_ward_id", req.getParameter("in_ward_id"));
        paramMap.put("in_dept_id", req.getParameter("in_dept_id"));
        paramMap.put("in_start_time", req.getParameter("in_start_time"));
        paramMap.put("in_end_time", req.getParameter("in_end_time"));
        paramMap.put("out_start_time", req.getParameter("out_start_time"));
        paramMap.put("out_end_time", req.getParameter("out_end_time"));
        paramMap.put("js_start_time", req.getParameter("js_start_time"));
        paramMap.put("js_end_time", req.getParameter("js_end_time"));
        paramMap.put("patient_code", req.getParameter("patient_code"));
        if(StringUtils.isNotEmpty(req.getParameter("psnIdetType"))){
            String[] psnIdetTypes = req.getParameter("psnIdetType").split(",");
            if(psnIdetTypes.length > 0){
                List<String> psnIdetType = Arrays.asList(psnIdetTypes);
                paramMap.put("psnIdetType", psnIdetType);
            }
        }
        return inptVisitService_consumer.queryPatients(paramMap);
    }
/*
    *//**
     * @Method: inRegisterByArtificial
     * @Description: 手工登记入院
     * @Param: map
     * @Author: liaojiguang
     * @Email: 847025096@qq.com
     * @Date: 2021/3/18 9:17
     * @Return: cn.hsa.module.inpt.doctor.dto.InptVisitDTO
     **//*
    @PostMapping("/inRegisterByArtificial")
    public WrapperResponse<Boolean> inRegisterByArtificial(@RequestBody InptVisitDTO inptVisitDTO) {
        if (StringUtils.isEmpty(inptVisitDTO.getId())) {
            throw new AppException("请选择患者信息");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        map.put("inptVisitDTO", inptVisitDTO);
        return inptVisitService_consumer.inRegisterByArtificial(map);
    }*/

    @PostMapping("/updateInptPatientInfo")
    public WrapperResponse<Boolean> updateInptPatientInfo(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("crteId", sysUserDTO.getId());
        map.put("crteName", sysUserDTO.getName());
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedPayInptService_consumer.UP_2403(map);
    }


    /**
     * @Method queryLeaveHospitalRecallPage
     * @Desrciption 出院召回分页查询
     * @param inptVisitDTO
     * @Author liuqi1
     * @Date   2020/9/16 11:49
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping(value = "/queryInptVisitAndZkPage")
    public WrapperResponse<PageDTO> queryInptVisitAndZkPage(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        inptVisitDTO.setInDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("inptVisitDTO",inptVisitDTO);

        WrapperResponse<PageDTO> response = inptVisitService_consumer.queryInptVisitAndZkPage(map);
        return response;
    }

    @PostMapping("/getBabyInfoByVisitId")
    public WrapperResponse<List<InptBabyDTO>> getBabyInfoByVisitId(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        return inptVisitService_consumer.getBabyInfoByVisitId(map);
    }
}
