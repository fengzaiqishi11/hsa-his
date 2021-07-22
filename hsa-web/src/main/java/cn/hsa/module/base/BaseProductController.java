package cn.hsa.module.base;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bp.dto.BaseProductDTO;
import cn.hsa.module.base.bp.service.BaseProductService;
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
 * @Class_name: BaseProductManagementController
 * @Describe: 生产企业管理控制层
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/7 14:52
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/base/baseProduct")
@Slf4j
public class BaseProductController extends BaseController {
    /**
     * 注入消费者接口
     */
    @Resource
    private BaseProductService baseProductService_customer;

    /**
    * @Menthod getById
    * @Desrciption 通过主键ID查询生产企业信息
     * @param baseProductDTO 生产企业信息表主键ID
    * @Author xingyu.xie
    * @Date   2020/7/17 9:12
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.base.bmm.dto.BaseProductDTO>
    **/
    @GetMapping("/getById")
    public WrapperResponse<BaseProductDTO> getById(BaseProductDTO baseProductDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        baseProductDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseProductDTO",baseProductDTO);
        return baseProductService_customer.getById(map);
    }

    /**
     * @Menthod queryAll
     * @Desrciption  查询全部生产企业信息
     * @Author xingyu.xie
     * @Date   2020/7/15 9:13
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<BaseProductDTO>> queryAll(BaseProductDTO baseProductDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        baseProductDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseProductDTO",baseProductDTO);
        return baseProductService_customer.queryAll(map);
    }

    /**
    * @Menthod queryPage
    * @Desrciption  通过数据传输对象分页查询生产企业信息表
     * @param baseProductDTO 生产企业信息数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/17 9:13
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(BaseProductDTO baseProductDTO, HttpServletRequest req, HttpServletResponse res) {
        log.debug("BaseProductDTO:{}", baseProductDTO);
        SysUserDTO sysUserDTO = getSession(req, res);
        baseProductDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseProductDTO",baseProductDTO);
        return baseProductService_customer.queryPage(map);
    }

    /**
    * @Menthod insert
    * @Desrciption 新增或修改单条生产企业信息
     * @param baseProductDTO 生产企业信息数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/17 9:14
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/save")
    public WrapperResponse<Boolean> save(@RequestBody BaseProductDTO baseProductDTO, HttpServletRequest req, HttpServletResponse res) {
        log.debug("baseProductDTO:{}", baseProductDTO);
        SysUserDTO sysUserDTO = getSession(req, res);
        baseProductDTO.setHospCode(sysUserDTO.getHospCode());
        baseProductDTO.setCrteId(sysUserDTO.getId());
        baseProductDTO.setCrteName(sysUserDTO.getName());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseProductDTO",baseProductDTO);
        return this.baseProductService_customer.save(map);
    }


    /**
    * @Menthod updateStatus
    * @Desrciption  通过主键ID删除一条或多条生产企业信息
     * @param baseProductDTO 生产企业数据传输对象
    * @Author xingyu.xie
    * @Date   2020/7/17 9:15
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping("/updateStatus")
    public WrapperResponse<Boolean> updateStatus(@RequestBody BaseProductDTO baseProductDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        baseProductDTO.setHospCode(sysUserDTO.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("baseProductDTO",baseProductDTO);
        return this.baseProductService_customer.updateStatus(map);
    }


}
