package cn.hsa.module.sys;

import cn.hsa.base.BaseController;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sys.role.dto.SysRoleDto;
import cn.hsa.module.sys.role.dto.SysRoleMenuButtonDto;
import cn.hsa.module.sys.role.entity.SysRoleDo;
import cn.hsa.module.sys.role.entity.SysRoleMenuButtonDo;
import cn.hsa.module.sys.role.service.SysRoleService;
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
* @Package_name: cn.hsa.module.sys
* @class_name: SysSystemController
* @Description: 角色控制层
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/7/30 9:31
* @Company: 创智和宇
**/
@RestController
@RequestMapping("/web/sys/role")
@Slf4j
public class SysRoleController extends BaseController {

    @Resource
    private SysRoleService sysRoleService_consumer;

    /**
     * @Method: queryAll
     * @Description: 获取所有的角色列表
     * @Param: [sysSystemDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 9:34
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.system.entity.SysSystemDo>>
     **/
    @GetMapping("/queryRoles")
    public WrapperResponse<List<SysRoleDo>> queryRoles(SysRoleDto sysRoleDto, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        sysRoleDto.setHospCode(sysUserDTO.getHospCode());
        map.put("sysRoleDto", sysRoleDto);
        return sysRoleService_consumer.queryRoles(map);
    }

    /**
     * @Method: saveRoles
     * @Description: 保存角色
     * @Param: [sysSystemDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 9:45
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/saveRole")
    public WrapperResponse<Boolean> saveRole(@RequestBody SysRoleDo sysRoleDo, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        sysRoleDo.setHospCode(sysUserDTO.getHospCode());
        sysRoleDo.setCrteId(sysUserDTO.getId());
        sysRoleDo.setCrteName(sysUserDTO.getName());
        map.put("sysRoleDo", sysRoleDo);
        return sysRoleService_consumer.saveRole(map);
    }

    /**
     * @Method: deleteRoles
     * @Description: 删除角色
     * @Param: [sysSystemDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 9:45
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/deleteRoles")
    public WrapperResponse<Boolean> deleteRoles(@RequestBody SysRoleDto sysRoleDto, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        map.put("sysRoleDto", sysRoleDto);
        return sysRoleService_consumer.deleteRoles(map);
    }

    /**
     * @Method: saveRoleButtons
     * @Description: 保存角色菜单按钮
     * @Param: [sysSystemDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 9:45
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/saveRoleButtons")
    public WrapperResponse<Boolean> saveRoleButtons(@RequestBody SysRoleMenuButtonDto sysRoleMenuButtonDto, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        sysRoleMenuButtonDto.setHospCode(sysUserDTO.getHospCode());
        for (SysRoleMenuButtonDo roleMenuButtonDo:sysRoleMenuButtonDto.getButtons()) {
            roleMenuButtonDo.setHospCode(sysUserDTO.getHospCode());
            roleMenuButtonDo.setCrteId(sysUserDTO.getId());
            roleMenuButtonDo.setCrteName(sysUserDTO.getName());
            roleMenuButtonDo.setRoleCode(sysRoleMenuButtonDto.getRoleCode());
        }
        map.put("sysRoleMenuButtonDto", sysRoleMenuButtonDto);
        return sysRoleService_consumer.saveRoleButtons(map);
    }

    /**
     * @Method: queryAll
     * @Description: 获取所有的角色列表
     * @Param: [sysSystemDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 9:34
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.system.entity.SysSystemDo>>
     **/
    @GetMapping("/getMenuTree")
    public WrapperResponse<List<TreeMenuNode>> getMenuTree(SysRoleDto sysRoleDto, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode", sysUserDTO.getHospCode());
        sysRoleDto.setHospCode(sysUserDTO.getHospCode());
        map.put("sysRoleDto", sysRoleDto);
        return sysRoleService_consumer.getMenuTree(map);
    }
}
