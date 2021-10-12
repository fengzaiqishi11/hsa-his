package cn.hsa.module.base;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.diagnosistemplate.dto.BaseDiagnosisTemplateDTO;
import cn.hsa.module.base.diagnosistemplate.service.BaseDiagnosisTemplateService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.base
 * @Class_name: BaseDiagnosisTemplateController
 * @Describe: 诊断模板管理控制层
 * @Author: pengbo
 * @Date: 2023/3/27 19:15
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/base/basediagnosistemplate")
@Slf4j
public class BaseDiagnosisTemplateController extends BaseController {

    @Resource
    BaseDiagnosisTemplateService baseDiagnosisTemplateService_customer;


    /**
     * @Menthod queryAll
     * @Desrciption 查询诊断模板
     * @param baseDiagnosisTemplateDTO 诊断模板信息
     * @Author pengbo
     * @Date   2021/3/27 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<BaseDiagnosisTemplateDTO> queryAll(BaseDiagnosisTemplateDTO baseDiagnosisTemplateDTO, HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseDiagnosisTemplateDTO.setHospCode(hospCode);
        baseDiagnosisTemplateDTO.setHospCode(sysUserDTO.getHospCode());
        //使用范围 0 全院，1 科室，2 个人
        if("1".equals(baseDiagnosisTemplateDTO.getUseScopeCode())){
            //baseDiagnosisTemplateDTO.setDeptId(loginDeptId);
            if (sysUserDTO.getLoginBaseDeptDTO() != null) {
                baseDiagnosisTemplateDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
            }
        }else  if("2".equals(baseDiagnosisTemplateDTO.getUseScopeCode())){
            //baseDiagnosisTemplateDTO.setDoctorId(userId);
            baseDiagnosisTemplateDTO.setDoctorId(sysUserDTO.getId());
         }


        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseDiagnosisTemplateDTO",baseDiagnosisTemplateDTO);
        return baseDiagnosisTemplateService_customer.queryAll(map);
    }

    /**
     * @Menthod getById
     * @Desrciption 通过主键ID查询诊断模板信息
     * @param baseDiagnosisTemplateDTO 诊断模板信息
     * @Author pengbo
     * @Date   2021/3/27 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    @GetMapping("/getById")
    public WrapperResponse<BaseDiagnosisTemplateDTO> getById(BaseDiagnosisTemplateDTO baseDiagnosisTemplateDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseDiagnosisTemplateDTO.setHospCode(hospCode);
        baseDiagnosisTemplateDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseDiagnosisTemplateDTO",baseDiagnosisTemplateDTO);
        return baseDiagnosisTemplateService_customer.getById(map);
    }


    /**
     * @Menthod deleteById
     * @Desrciption 根据ID删除诊断模板信息
     * @param baseDiagnosisTemplateDTO 诊断模板信息
     * @Author pengbo
     * @Date   2021/3/27 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    @PostMapping("/deleteById")
    public WrapperResponse<Boolean> deleteById(@RequestBody BaseDiagnosisTemplateDTO baseDiagnosisTemplateDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseDiagnosisTemplateDTO.setHospCode(hospCode);
        baseDiagnosisTemplateDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseDiagnosisTemplateDTO",baseDiagnosisTemplateDTO);
        return baseDiagnosisTemplateService_customer.deleteById(map);
    }



    /**
     * @Menthod save
     * @Desrciption 保存诊断模板信息
     * @param baseDiagnosisTemplateDTO 诊断模板信息
     * @Author pengbo
     * @Date   2021/3/27 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    @PostMapping("/save")
    public WrapperResponse<Boolean> save(@RequestBody BaseDiagnosisTemplateDTO baseDiagnosisTemplateDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseDiagnosisTemplateDTO.setHospCode(hospCode);
        baseDiagnosisTemplateDTO.setHospCode(sysUserDTO.getHospCode());

        //使用范围 0 全院，1 科室，2 个人
        if("2".equals(baseDiagnosisTemplateDTO.getUseScopeCode())){
            //baseDiagnosisTemplateDTO.setDoctorId(userId);
            baseDiagnosisTemplateDTO.setDoctorId(sysUserDTO.getId());
            //baseDiagnosisTemplateDTO.setDoctorName(userName);
            baseDiagnosisTemplateDTO.setDoctorName(sysUserDTO.getName());
        }else if("1".equals(baseDiagnosisTemplateDTO.getUseScopeCode())){
            //baseDiagnosisTemplateDTO.setDeptId(loginDeptId);
            //baseDiagnosisTemplateDTO.setDeptName(loginDeptName);
            if (sysUserDTO.getLoginBaseDeptDTO() != null) {
                baseDiagnosisTemplateDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
                baseDiagnosisTemplateDTO.setDeptName(sysUserDTO.getLoginBaseDeptDTO().getName());
            }
        }
        Map map = new HashMap();
        //map.put("crteId",userId);
        map.put("crteId",sysUserDTO.getId());
        //map.put("crteName",userName);
        map.put("crteName",sysUserDTO.getName());
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseDiagnosisTemplateDTO",baseDiagnosisTemplateDTO);
        return baseDiagnosisTemplateService_customer.save(map);
    }


    /**
     * @Menthod updateById
     * @Desrciption 根据ID 修改诊断模板信息
     * @param baseDiagnosisTemplateDTO 诊断模板信息
     * @Author pengbo
     * @Date   2021/3/27 9:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMaterialDTO>
     **/
    @PostMapping("/updateById")
    public WrapperResponse<Boolean> updateById(@RequestBody BaseDiagnosisTemplateDTO baseDiagnosisTemplateDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseDiagnosisTemplateDTO.setHospCode(hospCode);
        baseDiagnosisTemplateDTO.setHospCode(sysUserDTO.getHospCode());
        //使用范围 0 全院，1 科室，2 个人
        if("2".equals(baseDiagnosisTemplateDTO.getUseScopeCode())){
            //baseDiagnosisTemplateDTO.setDoctorId(userId);
            baseDiagnosisTemplateDTO.setDoctorId(sysUserDTO.getId());
            //baseDiagnosisTemplateDTO.setDoctorName(userName);
            baseDiagnosisTemplateDTO.setDoctorName(sysUserDTO.getName());
        }else if("1".equals(baseDiagnosisTemplateDTO.getUseScopeCode())){
            //baseDiagnosisTemplateDTO.setDeptId(loginDeptId);
            //baseDiagnosisTemplateDTO.setDeptName(loginDeptName);
            if (sysUserDTO.getLoginBaseDeptDTO() != null) {
                baseDiagnosisTemplateDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
                baseDiagnosisTemplateDTO.setDeptName(sysUserDTO.getLoginBaseDeptDTO().getName());
            }
        }
        Map map = new HashMap();
        //map.put("hospCode",hospCode);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseDiagnosisTemplateDTO",baseDiagnosisTemplateDTO);
        return baseDiagnosisTemplateService_customer.updateById(map);
    }

    /**
     * @Menthod: updateStatusCode
     * @Desrciption: 审核/作废诊断管理
     * @Param: baseDiagnosisTemplateDTO
     *  审核：checkFlag = 1，作废：checkFlag = 2
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-10-12 13:49
     * @Return: Boolean
     **/
    @PostMapping("/updateStatusCode")
    public WrapperResponse<Boolean> updateStatusCode(@RequestBody BaseDiagnosisTemplateDTO baseDiagnosisTemplateDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req,res);
        baseDiagnosisTemplateDTO.setHospCode(sysUserDTO.getHospCode());
        if (StringUtils.isEmpty(baseDiagnosisTemplateDTO.getCheckFlag())) {
            throw new RuntimeException("请选择审核或作废状态");
        }
        if ("1".equals(baseDiagnosisTemplateDTO.getCheckFlag())) {
            //审核
            baseDiagnosisTemplateDTO.setIsCheck(Constants.SF.S);
            baseDiagnosisTemplateDTO.setCheckId(sysUserDTO.getId());
            baseDiagnosisTemplateDTO.setCheckName(sysUserDTO.getName());
            baseDiagnosisTemplateDTO.setCheckTime(DateUtils.getNow());
        } else if ("2".equals(baseDiagnosisTemplateDTO.getCheckFlag())) {
            //作废
            baseDiagnosisTemplateDTO.setIsValid(Constants.SF.F);
        }
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseDiagnosisTemplateDTO",baseDiagnosisTemplateDTO);
        return baseDiagnosisTemplateService_customer.updateStatusCode(map);
    }

}
