package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.prescribe.dto.OutptPrescribeTempDTO;
import cn.hsa.module.outpt.prescribe.service.OutptPrescribeTempService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *@Package_name: cn.hsa.module.outpt
 *@Class_name: OutptPrescribeTempController
 *@Describe: 处方模板维护
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/19 15:35
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/outpt/prescribeTemp")
@Slf4j
public class OutptPrescribeTempController extends BaseController {

    @Resource
    OutptPrescribeTempService outptPrescribeTempService_consumer;

    /**
    * @Method queryOutptPrescribeTempDTOPage
    * @Desrciption 分页查询
    * @param outptPrescribeTempDTO
    * @Author liuqi1
    * @Date   2020/8/19 15:41
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/queryOutptPrescribeTempDTOPage")
    public WrapperResponse<PageDTO> queryOutptPrescribeTempDTOPage(OutptPrescribeTempDTO outptPrescribeTempDTO,HttpServletRequest req, HttpServletResponse res) {
        Map<String,Object> map = new HashMap<>();
        SysUserDTO userDTO = getSession(req, res) ;
        map.put("hospCode", userDTO.getHospCode());
        outptPrescribeTempDTO.setHospCode(userDTO.getHospCode());
        outptPrescribeTempDTO.setDoctorId(userDTO.getId());
        outptPrescribeTempDTO.setDeptId(userDTO.getLoginBaseDeptDTO() == null? null:
                userDTO.getLoginBaseDeptDTO().getId());
        map.put("outptPrescribeTempDTO",outptPrescribeTempDTO);

        WrapperResponse<PageDTO> pageDTOWrapperResponse = outptPrescribeTempService_consumer.queryOutptPrescribeTempDTOPage(map);
        return pageDTOWrapperResponse;
    }

    /**
     * @Menthod queryOutptPrescribeTempDetails
     * @Desrciption  处方模板明细
     * @param
     * @Author zengfeng
     * @Date   2020/9/2 10:24
     * @Return List<OutptPrescribeTempDetailDTO>
     **/
    @GetMapping("/queryOutptPrescribeTempDetails")
    public WrapperResponse<PageDTO> queryOutptPrescribeTempDetails(OutptPrescribeTempDTO outptPrescribeTempDTO,HttpServletRequest req, HttpServletResponse res) {
        Map paramMap = new HashMap();
        SysUserDTO userDTO = getSession(req, res) ;
        //医院编码
        paramMap.put("hospCode",userDTO.getHospCode());
        //医院编码
        outptPrescribeTempDTO.setHospCode(userDTO.getHospCode());
        //查询数据
        paramMap.put("outptPrescribeTempDTO", outptPrescribeTempDTO);
        WrapperResponse<PageDTO> pageDTOWrapperResponse = outptPrescribeTempService_consumer.queryOutptPrescribeTempDetails(paramMap);
        return pageDTOWrapperResponse;
    }

    /**
     * @Menthod OutptRegisterDTO
     * @Desrciption  保存处方模板
     * @param outptPrescribeTempDTO 处方模板信息
     * @Author zengfeng
     * @Date   2020/9/7 10:24
     * @Return Boolean
     **/
    @PostMapping("/savePrescribeTemp")
    public WrapperResponse<Boolean> savePrescribeTemp(@RequestBody OutptPrescribeTempDTO outptPrescribeTempDTO,HttpServletRequest req, HttpServletResponse res) {
        Map paramMap = new HashMap();
        SysUserDTO userDTO = getSession(req, res) ;
        //医院编码
        paramMap.put("hospCode",userDTO.getHospCode());
        //医院编码
        outptPrescribeTempDTO.setHospCode(userDTO.getHospCode());
        //登录部门ID
        outptPrescribeTempDTO.setDeptId(userDTO.getLoginBaseDeptDTO() == null? null:
                userDTO.getLoginBaseDeptDTO().getId());
        //登录部门
        outptPrescribeTempDTO.setDeptName(userDTO.getLoginBaseDeptDTO() == null? null:
                userDTO.getLoginBaseDeptDTO().getName());
        //医生ID
        outptPrescribeTempDTO.setDoctorId(userDTO.getId());
        //医生
        outptPrescribeTempDTO.setDoctorName(userDTO.getName());
        //医生ID
        outptPrescribeTempDTO.setCrteName(userDTO.getName());
        //医生
        outptPrescribeTempDTO.setCrteId(userDTO.getId());
        //个人模板
        if("2".equals(outptPrescribeTempDTO.getTypeCode())){
            //审核状态
            outptPrescribeTempDTO.setAuditCode(Constants.SHZT.SHWC);
            outptPrescribeTempDTO.setAuditName(userDTO.getName());
            outptPrescribeTempDTO.setAuditId(userDTO.getId());
            outptPrescribeTempDTO.setAuditTime(DateUtils.getNow());
        }else{
            //审核状态
            outptPrescribeTempDTO.setAuditCode(Constants.SHZT.WSH);
        }
        //模板处方信息
        paramMap.put("outptPrescribeTempDTO", outptPrescribeTempDTO);
        // 保存模板
        return outptPrescribeTempService_consumer.savePrescribeTemp(paramMap);
    }

    /**
     * @Menthod OutptRegisterDTO
     * @Desrciption  删除模板信息
     * @param outptPrescribeTempDTO 删除模板信息
     * @Author zengfeng
     * @Date   2020/9/7 10:24
     * @Return Boolean
     **/
    @PostMapping("/deletePrescribeTemp")
    public WrapperResponse<Boolean> deletePrescribeTemp(@RequestBody OutptPrescribeTempDTO outptPrescribeTempDTO,HttpServletRequest req, HttpServletResponse res) {
        Map paramMap = new HashMap();
        SysUserDTO userDTO = getSession(req, res) ;
        //医院编码
        paramMap.put("hospCode",userDTO.getHospCode());
        //医院编码
        outptPrescribeTempDTO.setHospCode(userDTO.getHospCode());
        //无效
        outptPrescribeTempDTO.setIsValid(Constants.SF.F);
        //审核状态
        outptPrescribeTempDTO.setAuditCode(Constants.SHZT.ZF);
        //模板处方信息
        paramMap.put("outptPrescribeTempDTO", outptPrescribeTempDTO);
        // 保存模板
        return outptPrescribeTempService_consumer.updateOutptPrescribeTempDTO(paramMap);
    }

    /**
     * @Menthod auditPrescribeTemp
     * @Desrciption  审核模板信息
     * @param outptPrescribeTempDTO 模板信息
     * @Author zengfeng
     * @Date   2020/9/7 10:24
     * @Return Boolean
     **/
    @PostMapping("/auditPrescribeTemp")
    public WrapperResponse<Boolean> auditPrescribeTemp(@RequestBody OutptPrescribeTempDTO outptPrescribeTempDTO,HttpServletRequest req, HttpServletResponse res) {
        Map paramMap = new HashMap();
        SysUserDTO userDTO = getSession(req, res) ;
        //医院编码
        paramMap.put("hospCode",userDTO.getHospCode());
        //医院编码
        outptPrescribeTempDTO.setHospCode(userDTO.getHospCode());
        //审核状态
        outptPrescribeTempDTO.setAuditCode(Constants.SHZT.SHWC);
        //审核人ID
        outptPrescribeTempDTO.setAuditId(userDTO.getId());
        //审核人
        outptPrescribeTempDTO.setAuditName(userDTO.getName());
        //审核时间
        outptPrescribeTempDTO.setAuditTime(DateUtils.getNow());
        //模板处方信息
        paramMap.put("outptPrescribeTempDTO", outptPrescribeTempDTO);
        // 修改
        return outptPrescribeTempService_consumer.updateOutptPrescribeTempDTO(paramMap);
    }
}
