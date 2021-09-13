package cn.hsa.module.clinical;


import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.clinical.clinicalpathstage.dto.ClinicalPathStageDTO;
import cn.hsa.module.clinical.clinicalpathstage.service.ClinicalPathStageService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.clinical
 * @Class_name: ClinicPathListController
 * @Describe: 临床路径阶段描述
 * @Author: zhangguorui
 * @Email: guorui.zhang@powersi.com
 * @Date: 2021/9/10 14:59
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/clinical/clinicalPathStageController")
@Slf4j
public class ClinicalPathStageController extends BaseController {
    @Resource
    private ClinicalPathStageService clinicalPathStageService_consumer;

    /**
     * @Meth:queryClinicalPathStage
     * @Description: 通过条件查询临床路径阶段
     * @Param: []
     * @return: WrapperResponse<PageDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/10
     */
    @GetMapping("/queryClinicalPathStage")
    public WrapperResponse<PageDTO> queryClinicalPathStage(ClinicalPathStageDTO clinicalPathStageDTO,
                                                           HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req,res);
        String hospCode = sysUserDTO.getHospCode();
        clinicalPathStageDTO.setHospCode(hospCode);
        clinicalPathStageDTO.setCrteId(sysUserDTO.getId());
        clinicalPathStageDTO.setCrteName(sysUserDTO.getName());
        Map map = new HashMap<>();
        map.put("hospCode",hospCode);
        map.put("clinicalPathStageDTO",clinicalPathStageDTO);
        return clinicalPathStageService_consumer.queryClinicalPathStage(map);
    }

    /**
     * @Meth: addOrupdateClinicalPathStage
     * @Description:  根据路径目录ID 添加或者编辑路径阶段
     * @Param:
     * @return:
     * @Author: zhangguorui
     * @Date: 2021/9/11
    */
    @PostMapping("/addOrUpdateClinicalPathStage")
    public WrapperResponse<Boolean> addOrUpdateClinicalPathStage(@RequestBody ClinicalPathStageDTO clinicalPathStageDTO,
                                                                 HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req,res);
        String hospCode = sysUserDTO.getHospCode();
        clinicalPathStageDTO.setHospCode(hospCode);
        clinicalPathStageDTO.setCrteId(sysUserDTO.getId());
        clinicalPathStageDTO.setCrteName(sysUserDTO.getName());
        Map map = new HashMap<>();
        map.put("hospCode",hospCode);
        map.put("clinicalPathStageDTO",clinicalPathStageDTO);
        return clinicalPathStageService_consumer.addOrUpdateClinicalPathStage(map);
    }

    /**
     * @Meth: deleteClinicalPathStage
     * @Description: 删除路径阶段描述
     * @Param: [clinicalPathStageDTO, req, res]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangguorui
     * @Date: 2021/9/11
     */
    @PostMapping("/deleteClinicalPathStage")
    public WrapperResponse<Boolean> deleteClinicalPathStage(@RequestBody ClinicalPathStageDTO clinicalPathStageDTO,
                                                            HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req,res);
        String hospCode = sysUserDTO.getHospCode();
        clinicalPathStageDTO.setHospCode(hospCode);
        clinicalPathStageDTO.setCrteId(sysUserDTO.getId());
        clinicalPathStageDTO.setCrteName(sysUserDTO.getName());
        Map map = new HashMap<>();
        map.put("hospCode",hospCode);
        map.put("clinicalPathStageDTO",clinicalPathStageDTO);
        return clinicalPathStageService_consumer.deleteClinicalPathStage(map);
    }
    /**
     * @Meth: queryClinicalPathStageById
     * @Description: 通过id查询临床路径阶段描述
     * @Param: [clinicalPathStageDTO, req, res]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.clinical.clinicalpathstage.dto.ClinicalPathStageDTO>
     * @Author: zhangguorui
     * @Date: 2021/9/13
     */
    @GetMapping("/queryClinicalPathStageById")
    public WrapperResponse<ClinicalPathStageDTO> queryClinicalPathStageById(ClinicalPathStageDTO clinicalPathStageDTO,
                                                                            HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req,res);
        String hospCode = sysUserDTO.getHospCode();
        clinicalPathStageDTO.setHospCode(hospCode);
        clinicalPathStageDTO.setCrteId(sysUserDTO.getId());
        clinicalPathStageDTO.setCrteName(sysUserDTO.getName());
        Map map = new HashMap<>();
        map.put("hospCode",hospCode);
        map.put("clinicalPathStageDTO",clinicalPathStageDTO);
        return clinicalPathStageService_consumer.queryClinicalPathStageById(map);
    }
}
