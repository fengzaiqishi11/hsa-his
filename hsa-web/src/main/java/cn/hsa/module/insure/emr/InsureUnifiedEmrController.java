package cn.hsa.module.insure.emr;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.emr.dto.InsureEmrDetailDTO;
import cn.hsa.module.insure.emr.dto.InsureEmrUnifiedDTO;
import cn.hsa.module.insure.emr.service.InsureUnifiedEmrService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure
 * @class_name: InsureUnifiedEmrController
 * @Description: 电子病历 医保业务控制层
 * @Author: qiang.fan
 * @Date: 2022/3/25 8:58
 * @Company: 创智和宇
 **/
@RestController
@RequestMapping("/web/insure/insureUnifiedEmr")
@Slf4j
public class InsureUnifiedEmrController extends BaseController {

    @Resource
    InsureUnifiedEmrService insureUnifiedEmrService_comsumer;


    /**
     * @Method queryInsureUnifiedEmrInfo
     * @Desrciption  电子病历上传-患者列表查询
     * @Param map
     * @Author qiang.fan
     * @Date   2022/3/25 10:03
     * @Return
     **/
    @PostMapping("/queryInsureUnifiedEmrInfo")
    public WrapperResponse<PageDTO> queryInsureUnifiedEmrInfo(@RequestBody InsureEmrUnifiedDTO insureEmrUnifiedDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map =new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureEmrUnifiedDTO",insureEmrUnifiedDTO);
        return insureUnifiedEmrService_comsumer.queryInsureUnifiedEmrInfo(map);
    }

    /**
     * @Method queryInsureUnifiedEmrDetail
     * @Desrciption  电子病历上传-患者详情查询
     * @Param map
     * @Author qiang.fan
     * @Date   2022/3/25 10:03
     * @Return
     **/
    @PostMapping("/queryInsureUnifiedEmrDetail")
    public WrapperResponse<InsureEmrDetailDTO> queryInsureUnifiedEmrDetail(@RequestBody InsureEmrUnifiedDTO insureEmrUnifiedDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map =new HashMap<>();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureEmrUnifiedDTO",insureEmrUnifiedDTO);
        return insureUnifiedEmrService_comsumer.queryInsureUnifiedEmrDetail(map);
    }

    /**
     * @Method updateInsureUnifiedEmrAdminfo
     * @Desrciption  电子病历上传-患者基本信息修改
     * @Param map
     * @Author qiang.fan
     * @Date   2022/3/25 10:03
     * @Return
     **/
    @PostMapping("/updateInsureUnifiedEmrAdminfo")
    public WrapperResponse updateInsureUnifiedEmrAdminfo(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedEmrService_comsumer.updateInsureUnifiedEmrAdminfo(map);
    }

    /**
     * @Method updateInsureUnifiedEmrDiseinfo
     * @Desrciption  电子病历上传-患者诊断信息修改
     * @Param map
     * @Author qiang.fan
     * @Date   2022/3/25 10:03
     * @Return
     **/
    @PostMapping("/updateInsureUnifiedEmrDiseinfo")
    public WrapperResponse updateInsureUnifiedEmrDiseinfo(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedEmrService_comsumer.updateInsureUnifiedEmrDiseinfo(map);
    }

    /**
     * @Method updateInsureUnifiedEmrCoursrinfo
     * @Desrciption  电子病历上传-患者病程信息修改
     * @Param map
     * @Author qiang.fan
     * @Date   2022/3/25 10:03
     * @Return
     **/
    @PostMapping("/updateInsureUnifiedEmrCoursrinfo")
    public WrapperResponse updateInsureUnifiedEmrCoursrinfo(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedEmrService_comsumer.updateInsureUnifiedEmrCoursrinfo(map);
    }

    /**
     * @Method updateInsureUnifiedEmrOprninfo
     * @Desrciption  电子病历上传-患者手术信息修改
     * @Param map
     * @Author qiang.fan
     * @Date   2022/3/25 10:03
     * @Return
     **/
    @PostMapping("/updateInsureUnifiedEmrOprninfo")
    public WrapperResponse updateInsureUnifiedEmrOprninfo(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedEmrService_comsumer.updateInsureUnifiedEmrOprninfo(map);
    }



    /**
     * @Method updateInsureUnifiedEmrRescinfo
     * @Desrciption  电子病历上传-患者病情抢救信息修改
     * @Param map
     * @Author qiang.fan
     * @Date   2022/3/25 10:03
     * @Return
     **/
    @PostMapping("/updateInsureUnifiedEmrRescinfo")
    public WrapperResponse updateInsureUnifiedEmrRescinfo(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedEmrService_comsumer.updateInsureUnifiedEmrRescinfo(map);
    }

    /**
     * @Method updateInsureUnifiedEmrDieinfo
     * @Desrciption  电子病历上传-患者死亡信息修改
     * @Param map
     * @Author qiang.fan
     * @Date   2022/3/25 10:03
     * @Return
     **/
    @PostMapping("/updateInsureUnifiedEmrDieinfo")
    public WrapperResponse updateInsureUnifiedEmrDieinfo(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedEmrService_comsumer.updateInsureUnifiedEmrDieinfo(map);
    }

    /**
     * @Method updateInsureUnifiedEmrDscginfo
     * @Desrciption  电子病历上传-患者出院信息修改
     * @Param map
     * @Author qiang.fan
     * @Date   2022/3/25 10:03
     * @Return
     **/
    @PostMapping("/updateInsureUnifiedEmrDscginfo")
    public WrapperResponse updateInsureUnifiedEmrDscginfo(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        return insureUnifiedEmrService_comsumer.updateInsureUnifiedEmrDscginfo(map);
    }


    /**
     * @Method updateInsureUnifiedEmr
     * @Desrciption  电子病历上传
     * @Param map
     * @Author qiang.fan
     * @Date   2022/3/25 10:03
     * @Return
     **/
    @PostMapping("/updateInsureUnifiedEmrUpload")
    public WrapperResponse updateInsureUnifiedEmrUpload(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        insureUnifiedEmrService_comsumer.updateInsureUnifiedEmrUpload(map);
        return WrapperResponse.success(null);
    }

    /**
     * @Method updateInsureUnifiedEmrSync
     * @Desrciption  电子病历-数据同步
     * @Param map
     * @Author qiang.fan
     * @Date   2022/3/25 10:03
     * @Return
     **/
    @PostMapping("/updateInsureUnifiedEmrSync")
    public WrapperResponse updateInsureUnifiedEmrSync(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        insureUnifiedEmrService_comsumer.updateInsureUnifiedEmrSync(map);
        return WrapperResponse.success(null);
    }

    @ApiOperation(value = "导出")
    @PostMapping("/export")
    public void export(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res) {
        try {
            SysUserDTO sysUserDTO = getSession(req, res);
            map.put("hospCode", sysUserDTO.getHospCode());
            insureUnifiedEmrService_comsumer.export(req,map);
        } catch (Exception e) {
            log.error("导出报错", e);
        }
    }
}
