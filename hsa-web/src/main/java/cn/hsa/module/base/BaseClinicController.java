package cn.hsa.module.base;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.clinic.dto.BaseClinicDTO;
import cn.hsa.module.base.clinic.service.BaseClinicService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
   * @Package_name: cn.hsa.module.base
   * @Class_name: BaseClinicController
   * @Describe: 诊室信息管理控制层
   * @Author: luonianxin
   * @Email: nianxin.luo@powersi.com
   * @Date: 2021/6/16 16:35
   * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
**/
@RestController
@RequestMapping("/web/base/baseClinic")
@Slf4j
public class BaseClinicController extends BaseController {
    /**
     * 分诊室Dubbo消费者接口
     */
    @Resource
    private BaseClinicService baseClinicService_consumer;
    /**
     * @Method getById()
     * @Description 查询医嘱频率
     * @Param baseRateDTO：医嘱频率数据参数对象
     *
     * @Author fuhui
     * @Date 2020/7/13 14:44
     * @Return BaseRateDTO
     **/
    @GetMapping("/getById")
    public WrapperResponse<BaseClinicDTO> getById(BaseClinicDTO baseClinicDTO, HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        Map map=new HashMap();
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO.getHospCode());
        //baseClinicDTO.setHospCode(hospCode);
        baseClinicDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("baseClinicDTO", baseClinicDTO);
        return baseClinicService_consumer.getById(map);
    }

    /**
     * @Method queryAll()
     * @Description 查询全部医嘱频率
     * @Param
     * 1、baseRateDTO
     * @Author fuhui
     * @Date 2020/7/13 14:44
     * @Return BaseRateDTO
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<PageDTO> queryAll(BaseClinicDTO baseClinicDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        Map map=new HashMap();
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO.getHospCode());
        //baseClinicDTO.setHospCode(hospCode);
        baseClinicDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("baseClinicDTO", baseClinicDTO);
        return this.baseClinicService_consumer.queryAll(map);
    }


    /**
     * @Method insert()
     * @Description 新增医嘱频率
     *
     * @Param
     * 1、baseRateDTO：医嘱频率数据参数对象
     * @Author fuhui
     * @Date 2020/7/13 14:45
     * @Return Boolean
     **/
    @PostMapping("/insert")
    public WrapperResponse<Boolean> insert(@RequestBody BaseClinicDTO baseClinicDTO,HttpServletRequest req, HttpServletResponse res){
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseClinicDTO.setHospCode(hospCode);
        baseClinicDTO.setHospCode(sysUserDTO.getHospCode());
        //baseClinicDTO.setCrteName(userName);
        baseClinicDTO.setCrteName(sysUserDTO.getName());
        //baseClinicDTO.setCrteId(userId);
        baseClinicDTO.setCrteId(sysUserDTO.getId());
        Map map=new HashMap();
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseClinicDTO", baseClinicDTO);
        return this.baseClinicService_consumer.insert(map);
    }
    /**
     * @Method update()
     * @Description 修改医嘱频率
     *
     * @Param
     * 1、baseRateDTO：医嘱频率数据参数对象
     * @Author fuhui
     * @Date 2020/7/13 14:44
     * @Return Boolean
     **/
    @PutMapping("/update")
    public WrapperResponse<Boolean> update(@RequestBody BaseClinicDTO baseClinicDTO,HttpServletRequest req, HttpServletResponse res){
       // SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseClinicDTO.setHospCode(hospCode);
        baseClinicDTO.setHospCode(sysUserDTO.getHospCode());
        Map map=new HashMap();
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseClinicDTO", baseClinicDTO);
        return this.baseClinicService_consumer.update(map);
    }
    /**
     * @Method  updateIsValid()
     * @Description 根据医嘱频率数据参数对象()医嘱频率标识符
     *
     * @Param
     * 1、医嘱频率数据参数对象
     * @Author fuhui
     * @Date 2020/7/13 14:44
     * @Return Boolean
     **/
    @PostMapping("/updateIsValid")
    public WrapperResponse<Boolean> updateIsValid(@RequestBody BaseClinicDTO baseClinicDTO,HttpServletRequest req, HttpServletResponse res){
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        //baseClinicDTO.setHospCode(hospCode);
        baseClinicDTO.setHospCode(sysUserDTO.getHospCode());
        Map map=new HashMap();
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseClinicDTO", baseClinicDTO);
        return this.baseClinicService_consumer.updateIsValid(map);
    }

    /**
     * @Method getBaseClinicDTOByDeptId()
     * @Description 查询根据科室查询相关诊室信息
     * @Param baseRateDTO：医嘱频率数据参数对象
     * @Author fuhui
     * @Date 2020/7/13 14:44
     * @Return BaseRateDTO
     **/
    @GetMapping("/getBaseClinicDTOByDeptId")
    public WrapperResponse<List<BaseClinicDTO>> getBaseClinicDTOByDeptId(BaseClinicDTO baseClinicDTO,HttpServletRequest req, HttpServletResponse res) {
        //SysUserDTO sysUserDTO = getSession(SESSION_USER_INFO);
        SysUserDTO sysUserDTO = getSession(req,res);
        Map map=new HashMap();
        //map.put("hospCode", hospCode);
        map.put("hospCode", sysUserDTO.getHospCode());
        //baseClinicDTO.setHospCode(hospCode);
        baseClinicDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("baseClinicDTO", baseClinicDTO);
        return baseClinicService_consumer.getBaseClinicDTOByDeptId(map);
    }
}
