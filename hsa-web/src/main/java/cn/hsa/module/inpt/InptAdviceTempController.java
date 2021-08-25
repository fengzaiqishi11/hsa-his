package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.NoRepeatSubmit;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDetailTempDTO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceTempDTO;
import cn.hsa.module.inpt.doctor.service.InptAdviceTempService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.module.inpt
 *@Class_name: InptAdviceTempController
 *@Describe: 医嘱模板
 *@Author: zengfeng
 *@Eamil: zengfeng@powersi.com.cn
 *@Date: 2020-10-20 19:46:53
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/inpt/inptAdviceTempController")
@Slf4j
public class InptAdviceTempController extends BaseController {

    @Resource
    InptAdviceTempService inptAdviceTempService_consumer;

    /**
     * @Method queryInptAdviceTempPage
     * @Desrciption 分页查询
     * @param inptAdviceTempDTO
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryInptAdviceTempPage")
    public WrapperResponse<PageDTO> queryInptAdviceTempPage(InptAdviceTempDTO inptAdviceTempDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        inptAdviceTempDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("inptAdviceTempDTO",inptAdviceTempDTO);
        WrapperResponse<PageDTO> pageDTOWrapperResponse = inptAdviceTempService_consumer.queryInptAdviceTempPage(map);
        return pageDTOWrapperResponse;
    }


    /**
     * @Menthod queryInptAdviceTemp
     * @Desrciption  查询模板信息
     * @param inptAdviceTempDTO 医嘱模板
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<InptAdviceTempDTO>
     **/
    @GetMapping("/queryInptAdviceTemp")
    public WrapperResponse<List<InptAdviceTempDTO>> queryInptAdviceTemp(InptAdviceTempDTO inptAdviceTempDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        inptAdviceTempDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("inptAdviceTempDTO",inptAdviceTempDTO);
        WrapperResponse<List<InptAdviceTempDTO>> inptAdviceTempWrapperResponse = inptAdviceTempService_consumer.queryInptAdviceTemp(map);
        return inptAdviceTempWrapperResponse;
    }

    /**
     * @Menthod queryInptAdviceTemp
     * @Desrciption  查询模板信息
     * @param inptAdviceTempDTO 医嘱模板
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<InptAdviceTempDTO>
     **/
    @GetMapping("/queryInptAdviceDetailTemp")
    public WrapperResponse<List<InptAdviceDetailTempDTO>> queryInptAdviceDetailTemp(InptAdviceTempDTO inptAdviceTempDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        inptAdviceTempDTO.setHospCode(sysUserDTO.getHospCode());
        inptAdviceTempDTO.setLoginDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        map.put("inptAdviceTempDTO",inptAdviceTempDTO);
        WrapperResponse<List<InptAdviceDetailTempDTO>> inptAdviceTempDetailWrapperResponse = inptAdviceTempService_consumer.queryInptAdviceDetailTemp(map);
        return inptAdviceTempDetailWrapperResponse;
    }

    /**
     * @Menthod OutptRegisterDTO
     * @Desrciption  保存处方模板
     * @param inptAdviceTempDTO 处方模板信息
     * @Author zengfeng
     * @Date   2020/9/7 10:24
     * @Return Boolean
     **/
    @NoRepeatSubmit
    @PostMapping("/saveAdciceTemp")
    public WrapperResponse<Boolean> saveAdciceTemp(@RequestBody InptAdviceTempDTO inptAdviceTempDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map paramMap = new HashMap();
        //医院编码
        paramMap.put("hospCode", sysUserDTO.getHospCode());
        //医院编码
        inptAdviceTempDTO.setHospCode(sysUserDTO.getHospCode());
        //登录部门ID
        inptAdviceTempDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        //登录部门
        inptAdviceTempDTO.setDeptName(sysUserDTO.getLoginBaseDeptDTO().getName());
        //医生ID
        inptAdviceTempDTO.setDoctorId(sysUserDTO.getId());
        //医生
        inptAdviceTempDTO.setDoctorName(sysUserDTO.getName());
        //医生ID
        inptAdviceTempDTO.setCrteName(sysUserDTO.getName());
        //医生
        inptAdviceTempDTO.setCrteId(sysUserDTO.getId());
        //个人模板
        if("2".equals(inptAdviceTempDTO.getTypeCode())){
            //审核状态
            inptAdviceTempDTO.setAuditCode(Constants.SHZT.SHWC);
            inptAdviceTempDTO.setAuditName(sysUserDTO.getName());
            inptAdviceTempDTO.setAuditId(sysUserDTO.getId());
            inptAdviceTempDTO.setAuditTime(DateUtils.getNow());
        }else{
            //审核状态
            inptAdviceTempDTO.setAuditCode(Constants.SHZT.WSH);
        }

        //模板医嘱信息
        paramMap.put("inptAdviceTempDTO", inptAdviceTempDTO);
        // 保存模板
        return inptAdviceTempService_consumer.saveAdciceTemp(paramMap);
    }

    /**
     * @Menthod deleteAdviceTemp
     * @Desrciption  删除模板信息
     * @param inptAdviceTempDTO 删除模板信息
     * @Author zengfeng
     * @Date   2020/9/7 10:24
     * @Return Boolean
     **/
    @NoRepeatSubmit
    @PostMapping("/deleteAdviceTemp")
    public WrapperResponse<Boolean> deleteAdviceTemp(@RequestBody InptAdviceTempDTO inptAdviceTempDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map paramMap = new HashMap();
        //医院编码
        paramMap.put("hospCode", sysUserDTO.getHospCode());
        //医院编码
        inptAdviceTempDTO.setHospCode(sysUserDTO.getHospCode());
        //无效
        inptAdviceTempDTO.setIsValid(Constants.SF.F);
        //审核状态
        inptAdviceTempDTO.setAuditCode(Constants.SHZT.ZF);
        //模板处方信息
        paramMap.put("inptAdviceTempDTO", inptAdviceTempDTO);
        // 保存模板
        return inptAdviceTempService_consumer.updateAdviceTemp(paramMap);
    }

    /**
     * @Menthod auditAdviceTemp
     * @Desrciption  审核模板信息
     * @param inptAdviceTempDTO 模板信息
     * @Author zengfeng
     * @Date   2020/9/7 10:24
     * @Return Boolean
     **/
    @NoRepeatSubmit
    @PostMapping("/auditAdviceTemp")
    public WrapperResponse<Boolean> auditAdviceTemp(@RequestBody InptAdviceTempDTO inptAdviceTempDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map paramMap = new HashMap();
        //医院编码
        paramMap.put("hospCode", sysUserDTO.getHospCode());
        //医院编码
        inptAdviceTempDTO.setHospCode(sysUserDTO.getHospCode());
        //审核状态
        inptAdviceTempDTO.setAuditCode(Constants.SHZT.SHWC);
        //审核人ID
        inptAdviceTempDTO.setAuditId(sysUserDTO.getId());
        //审核人
        inptAdviceTempDTO.setAuditName(sysUserDTO.getName());
        //审核时间
        inptAdviceTempDTO.setAuditTime(DateUtils.getNow());
        //模板处方信息
        paramMap.put("inptAdviceTempDTO", inptAdviceTempDTO);
        // 修改
        return inptAdviceTempService_consumer.updateAdviceTemp(paramMap);
    }
}
