package cn.hsa.module.base;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.rate.dto.BaseRateDTO;
import cn.hsa.module.base.rate.service.BaseRateService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

;


/**
 * @PackageName: cn.hsa.module.base
 * @Class_name: BaseRateController
 * @Description: 医嘱频率控制层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/7/13 14:44
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/base/baseRate")
@Slf4j
public class BaseRateController extends BaseController {
    /**
     * 医嘱频率Dubbo消费者接口
     */
    @Resource
    private BaseRateService  baseRateService_consumer;
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
    public WrapperResponse<BaseRateDTO> getById(BaseRateDTO baseRateDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        baseRateDTO.setHospCode(sysUserDTO.getHospCode());
        Map map=new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseRateDTO", baseRateDTO);
        return this.baseRateService_consumer.getById(map);
    }

    /**
     * @Method queryPage()
     * @Description 查询医嘱频率
     * @Param
     * 1、baseRateDTO
     * @Author fuhui
     * @Date 2020/7/13 14:44
     * @Return BaseRateDTO
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage( BaseRateDTO baseRateDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        baseRateDTO.setHospCode(sysUserDTO.getHospCode());
        Map map=new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseRateDTO", baseRateDTO);
        return this.baseRateService_consumer.queryPage(map);
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
    public WrapperResponse<List<BaseRateDTO>> queryAll(BaseRateDTO baseRateDTO, HttpServletRequest req, HttpServletResponse res) {
        Map map=new HashMap();
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode", sysUserDTO.getHospCode());
        baseRateDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("baseRateDTO", baseRateDTO);
        return this.baseRateService_consumer.queryAll(map);
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
    public WrapperResponse<Boolean> insert(@RequestBody BaseRateDTO baseRateDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        baseRateDTO.setHospCode(sysUserDTO.getHospCode());
        baseRateDTO.setCrteName(sysUserDTO.getName());
        baseRateDTO.setCrteId(sysUserDTO.getId());
        Map map=new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseRateDTO", baseRateDTO);
        return this.baseRateService_consumer.insert(map);
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
    public WrapperResponse<Boolean> update(@RequestBody BaseRateDTO baseRateDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        baseRateDTO.setHospCode(sysUserDTO.getHospCode());
        Map map=new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseRateDTO", baseRateDTO);
        return this.baseRateService_consumer.update(map);
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
    public WrapperResponse<Boolean> updateIsValid(@RequestBody BaseRateDTO baseRateDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        baseRateDTO.setHospCode(sysUserDTO.getHospCode());
        Map map=new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseRateDTO", baseRateDTO);
        return this.baseRateService_consumer.updateIsValid(map);
    }
}
