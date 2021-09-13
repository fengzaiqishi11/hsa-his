package cn.hsa.module.insure;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.entity.InsureDoctorMgtinfoDO;
import cn.hsa.module.insure.module.service.InsureDoctorMgtinfoService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
 * @Description: 医保医师执业信息控制层
 * @Author: liaojiguang
 * @Date: 2021-09-13 16:46
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/insure/InsureDoctorMgtinfo")
@Slf4j
public class InsureDoctorMgtinfoController extends BaseController {

    @Resource
    private InsureDoctorMgtinfoService insureDoctorMgtinfoService_consumer;

    /**
     * @Method queryInsureDoctorMgtinfo
     * @Desrciption  获取医师执业信息
     * @Param InsureDoctorMgtinfoDO
     * @Author 廖继广
     * @Date   2021-09-13 16:50
     * @Return java.util.List<InsureDoctorMgtinfoDO>
     **/
    @GetMapping("/queryInsureDoctorMgtinfo")
    public WrapperResponse<List<InsureDoctorMgtinfoDO>> queryInsureDoctorMgtinfo (InsureDoctorMgtinfoDO insureDoctorMgtinfoDO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureDoctorMgtinfoDO.setHospCode(sysUserDTO.getHospCode());

        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureDoctorMgtinfoDO",insureDoctorMgtinfoDO);
        return insureDoctorMgtinfoService_consumer.queryInsureDoctorMgtinfo(map);
    }

    /**
     * @Method insertInsureDoctorMgtinfo
     * @Desrciption  新增医师执业信息
     * @Param InsureDoctorMgtinfoDO
     * @Author 廖继广
     * @Date   2021-09-13 16:50
     * @Return Boolean
     **/
    @GetMapping("/insertInsureDoctorMgtinfo")
    public WrapperResponse<Boolean> insertInsureDoctorMgtinfo (InsureDoctorMgtinfoDO insureDoctorMgtinfoDO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureDoctorMgtinfoDO.setHospCode(sysUserDTO.getHospCode());
        insureDoctorMgtinfoDO.setCrteId(sysUserDTO.getId());
        insureDoctorMgtinfoDO.setCrteName(sysUserDTO.getName());
        insureDoctorMgtinfoDO.setCrteTime(new Date());

        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureDoctorMgtinfoDO",insureDoctorMgtinfoDO);
        return insureDoctorMgtinfoService_consumer.insertInsureDoctorMgtinfo(map);
    }

    /**
     * @Method updateInsureDoctorMgtinfoById
     * @Desrciption  修改医师执业信息
     * @Param InsureDoctorMgtinfoDO
     * @Author 廖继广
     * @Date   2021-09-13 16:50
     * @Return Boolean
     **/
    @GetMapping("/updateInsureDoctorMgtinfoById")
    public WrapperResponse<Boolean> updateInsureDoctorMgtinfoById (InsureDoctorMgtinfoDO insureDoctorMgtinfoDO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureDoctorMgtinfoDO.setHospCode(sysUserDTO.getHospCode());

        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureDoctorMgtinfoDO",insureDoctorMgtinfoDO);
        return insureDoctorMgtinfoService_consumer.updateInsureDoctorMgtinfoById(map);
    }

    /**
     * @Method deleteInsureDoctorMgtinfoById
     * @Desrciption  修改医师执业信息
     * @Param InsureDoctorMgtinfoDO
     * @Author 廖继广
     * @Date   2021-09-13 16:50
     * @Return Boolean
     **/
    @GetMapping("/deleteInsureDoctorMgtinfoById")
    public WrapperResponse<Boolean> deleteInsureDoctorMgtinfoById (InsureDoctorMgtinfoDO insureDoctorMgtinfoDO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureDoctorMgtinfoDO.setHospCode(sysUserDTO.getHospCode());

        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureDoctorMgtinfoDO",insureDoctorMgtinfoDO);
        return insureDoctorMgtinfoService_consumer.deleteInsureDoctorMgtinfoById(map);
    }

    /**
     * @Method getInsureDoctorMgtinfoById
     * @Desrciption  根据ID获取医师执业信息
     * @Param InsureDoctorMgtinfoDO
     * @Author 廖继广
     * @Date   2021-09-13 16:50
     * @Return InsureDoctorMgtinfoDO
     **/
    @GetMapping("/getInsureDoctorMgtinfoById")
    public WrapperResponse<InsureDoctorMgtinfoDO> getInsureDoctorMgtinfoById (InsureDoctorMgtinfoDO insureDoctorMgtinfoDO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        insureDoctorMgtinfoDO.setHospCode(sysUserDTO.getHospCode());

        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("insureDoctorMgtinfoDO",insureDoctorMgtinfoDO);
        return insureDoctorMgtinfoService_consumer.getInsureDoctorMgtinfoById(map);
    }

}
