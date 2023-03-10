package cn.hsa.module.insure.emr;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.emr.dto.*;
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
import java.util.List;
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
        insureEmrUnifiedDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureEmrUnifiedDTO",insureEmrUnifiedDTO);
        return insureUnifiedEmrService_comsumer.queryInsureUnifiedEmrDetail(map);
    }

    /**
     * @Method queryInsureUnifiedEmrAdminfo
     * @Desrciption  电子病历上传-患者基本信息查询
     * @Param map
     * @Author qiang.fan
     * @Date   2022/3/25 10:03
     * @Return
     **/
    @PostMapping("/queryInsureUnifiedEmrAdminfo")
    public WrapperResponse queryInsureUnifiedEmrAdminfo(@RequestBody InsureEmrUnifiedDTO insureEmrUnifiedDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureEmrUnifiedDTO",insureEmrUnifiedDTO);
        return WrapperResponse.success(insureUnifiedEmrService_comsumer.queryInsureUnifiedEmrAdminfo(map));
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
    public WrapperResponse<InsureEmrAdminfoDTO> updateInsureUnifiedEmrAdminfo(@RequestBody InsureEmrAdminfoDTO insureEmrAdminfoDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        insureEmrAdminfoDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureEmrAdminfoDTO",insureEmrAdminfoDTO);
        return insureUnifiedEmrService_comsumer.updateInsureUnifiedEmrAdminfo(map);
    }

    /**
     * @Method queryInsureUnifiedEmrDiseinfo
     * @Desrciption  电子病历上传-患者诊断信息查询
     * @Param map
     * @Author qiang.fan
     * @Date   2022/3/25 10:03
     * @Return
     **/
    @PostMapping("/queryInsureUnifiedEmrDiseinfo")
    public WrapperResponse queryInsureUnifiedEmrDiseinfo(@RequestBody InsureEmrUnifiedDTO insureEmrUnifiedDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureEmrUnifiedDTO",insureEmrUnifiedDTO);
        return WrapperResponse.success(insureUnifiedEmrService_comsumer.queryInsureUnifiedEmrDiseinfo(map));
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
    public WrapperResponse<InsureEmrDiseinfoDTO> updateInsureUnifiedEmrDiseinfo(@RequestBody InsureEmrDiseinfoDTO insureEmrDiseinfoDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        insureEmrDiseinfoDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureEmrDiseinfoDTO",insureEmrDiseinfoDTO);
        return insureUnifiedEmrService_comsumer.updateInsureUnifiedEmrDiseinfo(map);
    }

    /**
     * @Method queryInsureUnifiedEmrCoursrinfo
     * @Desrciption  电子病历上传-患者病程信息查询
     * @Param map
     * @Author qiang.fan
     * @Date   2022/3/25 10:03
     * @Return
     **/
    @PostMapping("/queryInsureUnifiedEmrCoursrinfo")
    public WrapperResponse queryInsureUnifiedEmrCoursrinfo(@RequestBody InsureEmrUnifiedDTO insureEmrUnifiedDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureEmrUnifiedDTO",insureEmrUnifiedDTO);
        return WrapperResponse.success(insureUnifiedEmrService_comsumer.queryInsureUnifiedEmrCoursrinfo(map));
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
    public WrapperResponse<InsureEmrCoursrinfoDTO> updateInsureUnifiedEmrCoursrinfo(@RequestBody InsureEmrCoursrinfoDTO insureEmrCoursrinfoDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        insureEmrCoursrinfoDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureEmrCoursrinfoDTO",insureEmrCoursrinfoDTO);
        return insureUnifiedEmrService_comsumer.updateInsureUnifiedEmrCoursrinfo(map);
    }

    /**
     * @Method queryInsureUnifiedEmrOprninfo
     * @Desrciption  电子病历上传-患者手术信息查询
     * @Param map
     * @Author qiang.fan
     * @Date   2022/3/25 10:03
     * @Return
     **/
    @PostMapping("/queryInsureUnifiedEmrOprninfo")
    public WrapperResponse queryInsureUnifiedEmrOprninfo(@RequestBody InsureEmrUnifiedDTO insureEmrUnifiedDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureEmrUnifiedDTO",insureEmrUnifiedDTO);
        return WrapperResponse.success(insureUnifiedEmrService_comsumer.queryInsureUnifiedEmrOprninfo(map));
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
    public WrapperResponse<InsureEmrOprninfoDTO> updateInsureUnifiedEmrOprninfo(@RequestBody InsureEmrOprninfoDTO insureEmrOprninfoDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        insureEmrOprninfoDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureEmrOprninfoDTO",insureEmrOprninfoDTO);
        return insureUnifiedEmrService_comsumer.updateInsureUnifiedEmrOprninfo(map);
    }


    /**
     * @Method queryInsureUnifiedEmrRescinfo
     * @Desrciption  电子病历上传-患者病情抢救信息查询
     * @Param map
     * @Author qiang.fan
     * @Date   2022/3/25 10:03
     * @Return
     **/
    @PostMapping("/queryInsureUnifiedEmrRescinfo")
    public WrapperResponse queryInsureUnifiedEmrRescinfo(@RequestBody InsureEmrUnifiedDTO insureEmrUnifiedDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureEmrUnifiedDTO",insureEmrUnifiedDTO);
        return WrapperResponse.success(insureUnifiedEmrService_comsumer.queryInsureUnifiedEmrRescinfo(map));
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
    public WrapperResponse<InsureEmrRescinfoDTO> updateInsureUnifiedEmrRescinfo(@RequestBody InsureEmrRescinfoDTO insureEmrRescinfoDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        insureEmrRescinfoDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureEmrRescinfoDTO",insureEmrRescinfoDTO);
        return insureUnifiedEmrService_comsumer.updateInsureUnifiedEmrRescinfo(map);
    }

    /**
     * @Method queryInsureUnifiedEmrDieinfo
     * @Desrciption  电子病历上传-患者死亡信息查询
     * @Param map
     * @Author qiang.fan
     * @Date   2022/3/25 10:03
     * @Return
     **/
    @PostMapping("/queryInsureUnifiedEmrDieinfo")
    public WrapperResponse queryInsureUnifiedEmrDieinfo(@RequestBody InsureEmrUnifiedDTO insureEmrUnifiedDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureEmrUnifiedDTO",insureEmrUnifiedDTO);
        return WrapperResponse.success(insureUnifiedEmrService_comsumer.queryInsureUnifiedEmrDieinfo(map));
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
    public WrapperResponse updateInsureUnifiedEmrDieinfo(@RequestBody InsureEmrDieinfoDTO insureEmrDieinfoDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        insureEmrDieinfoDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureEmrDieinfoDTO",insureEmrDieinfoDTO);
        return insureUnifiedEmrService_comsumer.updateInsureUnifiedEmrDieinfo(map);
    }

    /**
     * @Method queryInsureUnifiedEmrDscginfo
     * @Desrciption  电子病历上传-患者出院信息查询
     * @Param map
     * @Author qiang.fan
     * @Date   2022/3/25 10:03
     * @Return
     **/
    @PostMapping("/queryInsureUnifiedEmrDscginfo")
    public WrapperResponse queryInsureUnifiedEmrDscginfo(@RequestBody InsureEmrUnifiedDTO insureEmrUnifiedDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureEmrUnifiedDTO",insureEmrUnifiedDTO);
        return WrapperResponse.success(insureUnifiedEmrService_comsumer.queryInsureUnifiedEmrDscginfo(map));
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
    public WrapperResponse updateInsureUnifiedEmrDscginfo(@RequestBody InsureEmrDscginfoDTO insureEmrDscginfoDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        insureEmrDscginfoDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureEmrDscginfoDTO",insureEmrDscginfoDTO);
        return insureUnifiedEmrService_comsumer.updateInsureUnifiedEmrDscginfo(map);
    }


    /**
     * @Method updateInsureUnifiedEmr
     * @Desrciption  电子病历上传 4701
     * @Param map
     * @Author qiang.fan
     * @Date   2022/3/25 10:03
     * @Return
     **/
    @PostMapping("/updateInsureUnifiedEmrUpload")
    public WrapperResponse updateInsureUnifiedEmrUpload(@RequestBody List<InsureEmrUnifiedDTO> insureEmrUnifiedDTOList, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("insureEmrUnifiedDTOList",insureEmrUnifiedDTOList);
        insureUnifiedEmrService_comsumer.updateInsureUnifiedEmrUpload(map);
        return WrapperResponse.success("上传成功");
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
    public WrapperResponse updateInsureUnifiedEmrSync(@RequestBody InsureEmrUnifiedDTO insureEmrUnifiedDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        insureEmrUnifiedDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("insureEmrUnifiedDTO",insureEmrUnifiedDTO);
        insureUnifiedEmrService_comsumer.updateInsureUnifiedEmrSync(map);
        return WrapperResponse.success("同步成功");
    }

    @ApiOperation(value = "导出")
    @PostMapping("/export")
    public void export(@RequestBody InsureEmrUnifiedDTO insureEmrUnifiedDTO, HttpServletRequest req, HttpServletResponse res) {
        try {
            SysUserDTO sysUserDTO = getSession(req, res);
            Map map =new HashMap<>();
            map.put("hospCode", sysUserDTO.getHospCode());
            map.put("insureEmrUnifiedDTO",insureEmrUnifiedDTO);
            insureUnifiedEmrService_comsumer.export(req,map);
        } catch (Exception e) {
            log.error("导出报错", e);
        }
    }
}
