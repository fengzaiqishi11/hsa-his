package cn.hsa.module.base;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bw.dto.BaseWindowDTO;
import cn.hsa.module.base.bw.service.BaseWindowService;
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
 * @Class_name: BaseWindowManagementController
 * @Describe: 发药窗口管理控制层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/7 14:52
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/base/baseWindow")
@Slf4j
public class BaseWindowController extends BaseController {
    /**
     * 注入消费者接口
     */
    @Resource
    private BaseWindowService baseWindowService_customer;



    /**
    * @Menthod getById
    * @Desrciption 通过主键ID查询发药窗口信息
     * @param baseWindowDTO 发药窗口信息表主键ID
    * @Author xingyu.xie
    * @Date   2020/7/23 9:12
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseWindowDTO>
    **/
    @GetMapping("/getById")
    public WrapperResponse<BaseWindowDTO> getById(BaseWindowDTO baseWindowDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        baseWindowDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseWindowDTO",baseWindowDTO);
        return baseWindowService_customer.getById(map);
    }

    /**
     * @Menthod queryAll
     * @Desrciption  查询全部发药窗口信息
     * @Author xingyu.xie
     * @Date   2020/7/15 9:13
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<BaseWindowDTO>> queryAll(BaseWindowDTO baseWindowDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        baseWindowDTO.setHospCode(sysUserDTO.getHospCode());
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseWindowDTO",baseWindowDTO);
        return baseWindowService_customer.queryAll(map);
    }

    /**
    * @Menthod queryPage
    * @Desrciption  通过数据传输对象分页查询发药窗口信息表
     * @param baseWindowDTO 发药窗口信息数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/23 9:13
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(BaseWindowDTO baseWindowDTO, HttpServletRequest req, HttpServletResponse res) {
        log.debug("BaseWindowDTO:{}", baseWindowDTO);
        SysUserDTO sysUserDTO = getSession(req, res);
        baseWindowDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseWindowDTO",baseWindowDTO);
        return baseWindowService_customer.queryPage(map);
    }

    /**
    * @Menthod insert
    * @Desrciption 插入单条发药窗口信息
     * @param baseWindowDTO 发药窗口信息数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/23 9:14
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/insert")
    public WrapperResponse<Boolean> insert(@RequestBody BaseWindowDTO baseWindowDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        baseWindowDTO.setHospCode(sysUserDTO.getHospCode());
        baseWindowDTO.setCrteId(sysUserDTO.getId());
        baseWindowDTO.setCrteName(sysUserDTO.getName());
        map.put("baseWindowDTO",baseWindowDTO);
        return this.baseWindowService_customer.insert(map);
    }

    /**
    * @Menthod update
    * @Desrciption 更新单条发药窗口信息
     * @param baseWindowDTO 发药窗口信息数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/23 9:15
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/update")
    public WrapperResponse<Boolean> update(@RequestBody BaseWindowDTO baseWindowDTO, HttpServletRequest req, HttpServletResponse res) {
        log.debug("baseWindowDTO:{}", baseWindowDTO);
        SysUserDTO sysUserDTO = getSession(req, res);
        baseWindowDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseWindowDTO",baseWindowDTO);
        return this.baseWindowService_customer.update(map);
    }

    /**
    * @Menthod delete
    * @Desrciption  通过主键ID删除一条或多条发药窗口信息
     * @param baseWindowDTO 发药窗口信息表主键ID
    * @Author xingyu.xie
    * @Date   2020/7/23 9:15
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/delete")
    public WrapperResponse<Boolean> delete(@RequestBody BaseWindowDTO baseWindowDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        baseWindowDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseWindowDTO",baseWindowDTO);
        return this.baseWindowService_customer.delete(map);
    }

    /**
     * @Menthod queryPage
     * @Desrciption  查询当前登录科室的发药窗口
     * @param baseWindowDTO 发药窗口信息数据传输对象
     * @Author xingyu.xie
     * @Date   2020/7/23 9:13
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryByDeptId")
    public WrapperResponse<List<BaseWindowDTO>> queryByDeptId(BaseWindowDTO baseWindowDTO, HttpServletRequest req, HttpServletResponse res) {
        log.debug("BaseWindowDTO:{}", baseWindowDTO);
        SysUserDTO sysUserDTO = getSession(req, res);
        baseWindowDTO.setHospCode(sysUserDTO.getHospCode());
        baseWindowDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseWindowDTO",baseWindowDTO);
        return baseWindowService_customer.queryByDeptId(map);
    }


}
