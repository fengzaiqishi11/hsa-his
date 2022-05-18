package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.infusionRegister.dto.OutptInfusionRegisterDTO;
import cn.hsa.module.outpt.infusionRegister.service.OutptInfusionRegisterService;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt
 * @Class_name: OutptInfusionRegisterController
 * @Describe: 门诊输液登记控制器
 * @Author: luoyong
 * @Email: luoyong@powersi.com.cn
 * @Date: 2020/8/12 9:13
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/outpt/outptInfusionRegister")
@Slf4j
public class OutptInfusionRegisterController extends BaseController {

    @Resource
    private OutptInfusionRegisterService outptInfusionRegisterService_consumer;

    /**
     * @Menthod: queryPage()
     * @Desrciption: 根据条件分页《未登记》查询患者列表
     * @Param: outptInfusionRegisterDTO 门诊输液登记传输对象
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/12 10:48
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(OutptInfusionRegisterDTO outptInfusionRegisterDTO,HttpServletRequest req, HttpServletResponse res) {
//        outptInfusionRegisterDTO.setDeptId(loginDeptId);
        SysUserDTO userDTO = getSession(req, res) ;
        outptInfusionRegisterDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", userDTO.getHospCode());
        map.put("outptInfusionRegisterDTO", outptInfusionRegisterDTO);
        return outptInfusionRegisterService_consumer.queryPage(map);
    }

    /**
     * @Menthod: queryPageByInfu()
     * @Desrciption: 根据条件分页查询《已登记》患者列表
     * @Param: outptInfusionRegisterDTO 门诊输液登记传输对象
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/12 10:48
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    @GetMapping("/queryPageByInfu")
    public WrapperResponse<PageDTO> queryPageByInfu(OutptInfusionRegisterDTO outptInfusionRegisterDTO,HttpServletRequest req, HttpServletResponse res) {
//        outptInfusionRegisterDTO.setDeptId(loginDeptId);
        SysUserDTO userDTO = getSession(req, res) ;
        outptInfusionRegisterDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", userDTO.getHospCode());
        map.put("outptInfusionRegisterDTO", outptInfusionRegisterDTO);
        return outptInfusionRegisterService_consumer.queryPageByInfu(map);
    }

    /**
     * @Menthod: save()
     * @Desrciption: 输液登记
     * @Param: 1.outptVisitDTO-门诊就诊传输DTO对象
     * 2.opdId-处方明细id
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/12 10:48
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     **/
    @PostMapping("/save")
    public WrapperResponse<Boolean> save(@RequestBody List<OutptPrescribeDetailsDTO> outptPrescribeDetailsDTOS,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptPrescribeDetailsDTOS.forEach(item ->item.setHospCode(userDTO.getHospCode()));
//        outptVisitDTO.setHospCode(hospCode);
        Map map = new HashMap();

        map.put("hospCode", userDTO.getHospCode());
        map.put("outptPrescribeDetailsDTOS", outptPrescribeDetailsDTOS);
        map.put("crteId", userDTO.getId());
        map.put("crteName", userDTO.getName());
        return outptInfusionRegisterService_consumer.save(map);
    }

    /**
     * @Menthod: getByVisitId()
     * @Desrciption: 根据患者visitId分页查询出对应的处方明细列表
     * @Param: outptVisitDTO-门诊就诊DTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/12 16:15
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    @GetMapping("/getByVisitId")
    public WrapperResponse<PageDTO> getByVisitId(OutptVisitDTO outptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        outptVisitDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", userDTO.getHospCode());
        map.put("outptVisitDTO", outptVisitDTO);
        return outptInfusionRegisterService_consumer.getByVisitId(map);
    }

    /**
     * @Menthod: queryCost()
     * @Desrciption: 根据患者visitId分页查询出对应的费用列表
     * @Param: outptVisitDTO-门诊就诊DTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/8/12 16:15
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    @GetMapping("/queryCost")
    public WrapperResponse<PageDTO> queryCost(OutptVisitDTO outptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(outptVisitDTO.getId())) {
            throw new RuntimeException("未选择就诊人");
        }
        SysUserDTO userDTO = getSession(req, res) ;
        outptVisitDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode", userDTO.getHospCode());
        map.put("outptVisitDTO", outptVisitDTO);
        return outptInfusionRegisterService_consumer.queryCost(map);
    }
}
