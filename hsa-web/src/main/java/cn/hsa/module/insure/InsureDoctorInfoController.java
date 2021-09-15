package cn.hsa.module.insure;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureDoctorInfoDTO;
import cn.hsa.module.insure.module.service.InsureDoctorInfoService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure
 * @Class_name:: InsureFunctionController
 * @Description: 医保医师信息控制层
 * @Author: liaojiguang
 * @Date: 2021-09-13 16:46
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/insure/InsureDoctorInfo")
@Slf4j
public class InsureDoctorInfoController extends BaseController {

    @Resource
    private InsureDoctorInfoService insureDoctorInfoService_consumer;

    /**
     * @Method queryInsureDoctorInfo
     * @Desrciption  获取医师信息
     * @Param InsureDoctorInfoDTO
     * @Author 廖继广
     * @Date   2021-09-13 16:50
     * @Return java.util.List<InsureDoctorInfoDTO>
     **/
    @GetMapping("/queryInsureDoctorInfo")
    public WrapperResponse<List<InsureDoctorInfoDTO>> queryInsureDoctorInfo (InsureDoctorInfoDTO insureDoctorInfoDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureDoctorInfoDTO.setHospCode(sysUserDTO.getHospCode());

        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureDoctorInfoDTO",insureDoctorInfoDTO);
        return insureDoctorInfoService_consumer.queryInsureDoctorInfo(map);
    }

    /**
     * @Method insertInsureDoctorInfo
     * @Desrciption  新增医师信息
     * @Param InsureDoctorInfoDO
     * @Author 廖继广
     * @Date   2021-09-13 16:50
     * @Return Boolean
     **/
    @PostMapping("/insertInsureDoctorInfo")
    public WrapperResponse<Boolean> insertInsureDoctorInfo (@RequestBody InsureDoctorInfoDTO insureDoctorInfoDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureDoctorInfoDTO.setHospCode(sysUserDTO.getHospCode());
        insureDoctorInfoDTO.setCrteId(sysUserDTO.getId());
        insureDoctorInfoDTO.setCrteName(sysUserDTO.getName());
        insureDoctorInfoDTO.setCrteTime(new Date());

        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureDoctorInfoDTO",insureDoctorInfoDTO);
        return insureDoctorInfoService_consumer.insertInsureDoctorInfo(map);
    }

    /**
     * @Method updateInsureDoctorInfoById
     * @Desrciption  修改医师信息
     * @Param InsureDoctorInfoDO
     * @Author 廖继广
     * @Date   2021-09-13 16:50
     * @Return Boolean
     **/
    @PostMapping("/updateInsureDoctorInfoById")
    public WrapperResponse<Boolean> updateInsureDoctorInfoById (@RequestBody InsureDoctorInfoDTO insureDoctorInfoDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureDoctorInfoDTO.setHospCode(sysUserDTO.getHospCode());
        insureDoctorInfoDTO.setCrteId(sysUserDTO.getId());
        insureDoctorInfoDTO.setCrteName(sysUserDTO.getName());
        insureDoctorInfoDTO.setCrteTime(new Date());

        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureDoctorInfoDTO",insureDoctorInfoDTO);
        return insureDoctorInfoService_consumer.updateInsureDoctorInfoById(map);
    }

    /**
     * @Method deleteInsureDoctorInfoById
     * @Desrciption  删除医师信息
     * @Param InsureDoctorInfoDTO
     * @Author 廖继广
     * @Date   2021-09-13 16:50
     * @Return Boolean
     **/
    @PostMapping("/deleteInsureDoctorInfoById")
    public WrapperResponse<Boolean> deleteInsureDoctorInfoById (@RequestBody InsureDoctorInfoDTO insureDoctorInfoDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureDoctorInfoDTO.setHospCode(sysUserDTO.getHospCode());

        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureDoctorInfoDTO",insureDoctorInfoDTO);
        return insureDoctorInfoService_consumer.deleteInsureDoctorInfoById(map);
    }

    /**
     * @Method getInsureDoctorInfoById
     * @Desrciption  根据ID获取医师信息
     * @Param insureDoctorInfoDTO
     * @Author 廖继广
     * @Date   2021-09-13 16:50
     * @Return insureDoctorInfoDTO
     **/
    @GetMapping("/getInsureDoctorInfoById")
    public WrapperResponse<InsureDoctorInfoDTO> getInsureDoctorInfoById (InsureDoctorInfoDTO insureDoctorInfoDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureDoctorInfoDTO.setHospCode(sysUserDTO.getHospCode());

        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureDoctorInfoDTO",insureDoctorInfoDTO);
        return insureDoctorInfoService_consumer.getInsureDoctorInfoById(map);
    }

    /**
     * @Method UpdateToInsureUpload
     * @Desrciption  医师信息上传
     * @Param insureDoctorInfoDTO
     * @Author 廖继广
     * @Date   2021-09-13 16:50
     * @Return insureDoctorInfoDTO
     **/
    @PostMapping("/updateToInsureUpload")
    public WrapperResponse<Boolean> updateToInsureUpload (@RequestBody InsureDoctorInfoDTO insureDoctorInfoDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureDoctorInfoDTO.setHospCode(sysUserDTO.getHospCode());

        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureDoctorInfoDTO",insureDoctorInfoDTO);
        return insureDoctorInfoService_consumer.updateToInsureUpload(map);
    }

    /**
     * @Method updateToInsureEdit
     * @Desrciption  医师信息变更
     * @Param insureDoctorInfoDTO
     * @Author 廖继广
     * @Date   2021-09-13 16:50
     * @Return insureDoctorInfoDTO
     **/
    @PostMapping("/updateToInsureEdit")
    public WrapperResponse<Boolean> updateToInsureEdit (@RequestBody InsureDoctorInfoDTO insureDoctorInfoDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureDoctorInfoDTO.setHospCode(sysUserDTO.getHospCode());

        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureDoctorInfoDTO",insureDoctorInfoDTO);
        return insureDoctorInfoService_consumer.updateToInsureEdit(map);
    }

    /**
     * @Method updateToInsureDelete
     * @Desrciption  医师信息撤销
     * @Param insureDoctorInfoDTO
     * @Author 廖继广
     * @Date   2021-09-13 16:50
     * @Return insureDoctorInfoDTO
     **/
    @PostMapping("/updateToInsureDelete")
    public WrapperResponse<Boolean> updateToInsureDelete (@RequestBody InsureDoctorInfoDTO insureDoctorInfoDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureDoctorInfoDTO.setHospCode(sysUserDTO.getHospCode());

        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureDoctorInfoDTO",insureDoctorInfoDTO);
        return insureDoctorInfoService_consumer.updateToInsureDelete(map);
    }





}
