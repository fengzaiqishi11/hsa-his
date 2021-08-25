package cn.hsa.module.base;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.baseorderreceive.dto.BaseOrderReceiveDTO;
import cn.hsa.module.base.baseorderreceive.service.BaseOrderReceiveService;
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
 * @Class_name: BaseOrderReceiveManagementController
 * @Describe: 领药单据类型管理控制层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/7 14:52
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/base/baseOrderReceive")
@Slf4j
public class BaseOrderReceiveController extends BaseController {
    /**
     * 注入消费者接口
     */
    @Resource
    private BaseOrderReceiveService baseOrderReceiveService_customer;

    /**
    * @Menthod getById
    * @Desrciption 通过主键ID查询领药单据类型信息
     * @param baseOrderReceiveDTO 领药单据类型信息表主键ID
    * @Author xingyu.xie
    * @Date   2020/7/17 9:12
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseOrderReceiveDTO>
    **/
    @GetMapping("/getById")
    public WrapperResponse<BaseOrderReceiveDTO> getById(BaseOrderReceiveDTO baseOrderReceiveDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        baseOrderReceiveDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseOrderReceiveDTO",baseOrderReceiveDTO);
        return baseOrderReceiveService_customer.getById(map);
    }

    /**
     * @Menthod queryAll
     * @Desrciption  查询全部领药单据类型信息
     * @Author xingyu.xie
     * @Date   2020/7/15 9:13
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<BaseOrderReceiveDTO>> queryAll(BaseOrderReceiveDTO baseOrderReceiveDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        baseOrderReceiveDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseOrderReceiveDTO",baseOrderReceiveDTO);
        return baseOrderReceiveService_customer.queryAll(map);
    }

    /**
    * @Menthod queryPage
    * @Desrciption  通过数据传输对象分页查询领药单据类型信息表
     * @param baseOrderReceiveDTO 领药单据类型信息数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/17 9:13
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(BaseOrderReceiveDTO baseOrderReceiveDTO, HttpServletRequest req, HttpServletResponse res) {
        log.debug("BaseOrderReceiveDTO:{}", baseOrderReceiveDTO);
        SysUserDTO sysUserDTO = getSession(req, res);
        baseOrderReceiveDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseOrderReceiveDTO",baseOrderReceiveDTO);
        return baseOrderReceiveService_customer.queryPage(map);
    }

    /**
    * @Menthod insert
    * @Desrciption 新增或修改单条领药单据类型信息(无判空条件)
     * @param baseOrderReceiveDTO 领药单据类型信息数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/17 9:14
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/save")
    public WrapperResponse<Boolean> save(@RequestBody BaseOrderReceiveDTO baseOrderReceiveDTO, HttpServletRequest req, HttpServletResponse res) {
        log.debug("baseOrderReceiveDTO:{}", baseOrderReceiveDTO);
        SysUserDTO sysUserDTO = getSession(req, res);
        baseOrderReceiveDTO.setHospCode(sysUserDTO.getHospCode());
        baseOrderReceiveDTO.setCrteId(sysUserDTO.getId());
        baseOrderReceiveDTO.setCrteName(sysUserDTO.getName());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseOrderReceiveDTO",baseOrderReceiveDTO);
        return this.baseOrderReceiveService_customer.save(map);
    }

    /**
     * @Menthod updateBaseOrderReceiveS
     * @Desrciption 修改单条领药单据类型信息(有判空条件)
     * @param baseOrderReceiveDTO 领药单据类型信息数据传输对象
     * @Author xingyu.xie
     * @Date   2020/7/17 9:14
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/updateBaseOrderReceiveS")
    public WrapperResponse<Boolean> updateBaseOrderReceiveS(@RequestBody BaseOrderReceiveDTO baseOrderReceiveDTO, HttpServletRequest req, HttpServletResponse res) {
        log.debug("baseOrderReceiveDTO:{}", baseOrderReceiveDTO);
        SysUserDTO sysUserDTO = getSession(req, res);
        baseOrderReceiveDTO.setHospCode(sysUserDTO.getHospCode());
        baseOrderReceiveDTO.setCrteId(sysUserDTO.getId());
        baseOrderReceiveDTO.setCrteName(sysUserDTO.getName());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseOrderReceiveDTO",baseOrderReceiveDTO);
        return this.baseOrderReceiveService_customer.updateBaseOrderReceiveS(map);
    }


    /**
    * @Menthod updateStatus
    * @Desrciption  通过主键ID删除一条或多条领药单据类型信息
     * @param baseOrderReceiveDTO 领药单据类型数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/17 9:15
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/updateStatus")
    public WrapperResponse<Boolean> updateStatus(@RequestBody BaseOrderReceiveDTO baseOrderReceiveDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        baseOrderReceiveDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseOrderReceiveDTO",baseOrderReceiveDTO);
        return this.baseOrderReceiveService_customer.updateStatus(map);
    }


}
