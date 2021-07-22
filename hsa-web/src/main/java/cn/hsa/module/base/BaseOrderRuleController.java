package cn.hsa.module.base;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bor.dto.BaseOrderRuleDTO;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_ame: cn.hsa.web.base
 * @Class_name: BaseOrderRuleController
 * @Description: 单据生成规则控制层
 * @Author: zhongming
 * @Email: 406224709@qq.com
 * @Date: 2020/7/1 20:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/base/bor")
@Slf4j
public class BaseOrderRuleController extends BaseController {
    /**
     * 单据生成规则dubbo消费者接口
     */
    @Resource
    private BaseOrderRuleService baseOrderRuleService_consumer;

    /**
     * @Method getById()
     * @Description 根据主键ID查询
     *
     * @Param
     * 1、id：主键ID
     *
     * @Author zhongming
     * @Date 2020/7/1 20:53
     * @Return WrapperResponse<BaseOrderRuleDTO>
     **/
    @GetMapping("/getById")
    public WrapperResponse<BaseOrderRuleDTO> getById(Long id, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("id", id);
        return baseOrderRuleService_consumer.getById(map);
    }

    /**
     * @Method queryPage()
     * @Description 分页查询
     *
     * @Param
     * 1、baseOrderRuleDTO：参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage( BaseOrderRuleDTO baseOrderRuleDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseOrderRuleDTO", baseOrderRuleDTO);
        return baseOrderRuleService_consumer.queryPage(map);
    }

    /**
     * @Method insert()
     * @Description 新增单条
     *
     * @Param
     * 1、baseOrderRuleDTO：参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/insert")
    public WrapperResponse<Boolean> insert(@RequestBody BaseOrderRuleDTO baseOrderRuleDTO, HttpServletRequest req, HttpServletResponse res) {
        Map map = new HashMap();
        SysUserDTO sysUserDTO = getSession(req, res);
        baseOrderRuleDTO.setHospCode(sysUserDTO.getHospCode());
        baseOrderRuleDTO.setCrteId(sysUserDTO.getId());
        baseOrderRuleDTO.setCrteName(sysUserDTO.getName());
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseOrderRuleDTO", baseOrderRuleDTO);
        return baseOrderRuleService_consumer.insert(map);

    }

    /**
     * @Method update()
     * @Description 修改单条
     *
     * @Param
     * 1、baseOrderRuleDTO：参数对象
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/update")
    public WrapperResponse<Boolean> update(@RequestBody BaseOrderRuleDTO baseOrderRuleDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("baseOrderRuleDTO", baseOrderRuleDTO);
        return baseOrderRuleService_consumer.update(map);
    }

    /**
     * @Method delete()
     * @Description 单个或者批量删除
     *
     * @Param
     * 1、ids：主键ID集合
     *
     * @Author zhongming
     * @Date 2020/7/1 20:55
     * @Return WrapperResponse<Boolean>
     **/
    @PostMapping("/delete")
    public WrapperResponse<Boolean> delete(@RequestParam String ids, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        List idsa = Arrays.asList(ids.split(","));
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("ids", idsa);
        return baseOrderRuleService_consumer.delete(map);
    }
}
