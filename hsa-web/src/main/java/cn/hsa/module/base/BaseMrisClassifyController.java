package cn.hsa.module.base;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bmc.dto.BaseMrisClassifyDTO;
import cn.hsa.module.base.bmc.service.BaseMrisClassifyService;
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
 * @Class_name: BaseMrisClassifyManagementController
 * @Describe: 病案费用归类管理控制层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/7 14:52
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/base/baseMrisClassify")
@Slf4j
public class BaseMrisClassifyController extends BaseController {
    /**
     * 注入消费者接口
     */
    @Resource
    private BaseMrisClassifyService baseMrisClassifyService_customer;

    /**
    * @Menthod getById
    * @Desrciption 通过主键ID查询病案费用归类信息
     * @param baseMrisClassifyDTO 病案费用归类信息表主键ID
    * @Author xingyu.xie
    * @Date   2020/7/17 9:12
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseMrisClassifyDTO>
    **/
    @GetMapping("/getById")
    public WrapperResponse<BaseMrisClassifyDTO> getById(BaseMrisClassifyDTO baseMrisClassifyDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        baseMrisClassifyDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseMrisClassifyDTO",baseMrisClassifyDTO);
        return baseMrisClassifyService_customer.getById(map);
    }

    /**
     * @Menthod queryAll
     * @Desrciption  查询全部病案费用归类信息
     * @Author xingyu.xie
     * @Date   2020/7/15 9:13
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<BaseMrisClassifyDTO>> queryAll(BaseMrisClassifyDTO baseMrisClassifyDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        baseMrisClassifyDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseMrisClassifyDTO",baseMrisClassifyDTO);
        return baseMrisClassifyService_customer.queryAll(map);
    }

    /**
    * @Menthod queryPage
    * @Desrciption  通过数据传输对象分页查询病案费用归类信息表
     * @param baseMrisClassifyDTO 病案费用归类信息数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/17 9:13
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(BaseMrisClassifyDTO baseMrisClassifyDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        log.debug("BaseMrisClassifyDTO:{}", baseMrisClassifyDTO);
        baseMrisClassifyDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseMrisClassifyDTO",baseMrisClassifyDTO);
        return baseMrisClassifyService_customer.queryPage(map);
    }

    /**
    * @Menthod insert
    * @Desrciption 新增或修改单条病案费用归类信息(无判空条件)
     * @param baseMrisClassifyDTO 病案费用归类信息数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/17 9:14
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/save")
    public WrapperResponse<Boolean> save(@RequestBody BaseMrisClassifyDTO baseMrisClassifyDTO, HttpServletRequest req, HttpServletResponse res) {
        log.debug("baseMrisClassifyDTO:{}", baseMrisClassifyDTO);
        SysUserDTO sysUserDTO = getSession(req, res);
        baseMrisClassifyDTO.setHospCode(sysUserDTO.getHospCode());
        baseMrisClassifyDTO.setCrteId(sysUserDTO.getId());
        baseMrisClassifyDTO.setCrteName(sysUserDTO.getName());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseMrisClassifyDTO",baseMrisClassifyDTO);
        return this.baseMrisClassifyService_customer.save(map);
    }

    /**
     * @Menthod updateBaseMrisClassifyS
     * @Desrciption 修改单条病案费用归类信息(有判空条件)
     * @param baseMrisClassifyDTO 病案费用归类信息数据传输对象
     * @Author xingyu.xie
     * @Date   2020/7/17 9:14
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/updateBaseMrisClassifyS")
    public WrapperResponse<Boolean> updateBaseMrisClassifyS(@RequestBody BaseMrisClassifyDTO baseMrisClassifyDTO, HttpServletRequest req, HttpServletResponse res) {
        log.debug("baseMrisClassifyDTO:{}", baseMrisClassifyDTO);
        SysUserDTO sysUserDTO = getSession(req, res);
        baseMrisClassifyDTO.setHospCode(sysUserDTO.getHospCode());
        baseMrisClassifyDTO.setCrteId(sysUserDTO.getId());
        baseMrisClassifyDTO.setCrteName(sysUserDTO.getName());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseMrisClassifyDTO",baseMrisClassifyDTO);
        return this.baseMrisClassifyService_customer.updateBaseMrisClassifyS(map);
    }


    /**
    * @Menthod updateStatus
    * @Desrciption  通过主键ID删除一条或多条病案费用归类信息
     * @param baseMrisClassifyDTO 病案费用归类数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/17 9:15
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/updateStatus")
    public WrapperResponse<Boolean> updateStatus(@RequestBody BaseMrisClassifyDTO baseMrisClassifyDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        baseMrisClassifyDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseMrisClassifyDTO",baseMrisClassifyDTO);
        return this.baseMrisClassifyService_customer.updateStatus(map);
    }


}
