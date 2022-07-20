package cn.hsa.center.role.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.role.dto.CenterRoleDTO;
import cn.hsa.module.center.role.entity.CenterRoleDO;
import cn.hsa.module.center.role.service.CenterRoleService;
import cn.hsa.module.center.user.dto.CenterUserRoleDTO;
import cn.hsa.module.sys.user.dto.SysUserRoleDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@RequestMapping("/center/center/role")
@Slf4j
public class CenterRoleController extends CenterBaseController {

    @Resource
    private CenterRoleService centerRoleService_consumer;

    /**
     * @Method: queryAll
     * @Description: 获取所有的角色列表
     * @Param: [centerSystemDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 9:34
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.system.entity.SysSystemDo>>
     **/
    @GetMapping("/queryRoles")
    public WrapperResponse<List<CenterRoleDO>> queryRoles(CenterRoleDTO centerRoleDTO) {
        Map map = new HashMap();
        map.put("centerRoleDTO", centerRoleDTO);
        return centerRoleService_consumer.queryRoles(map);
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
    public WrapperResponse<Boolean> saveRole(@RequestBody CenterRoleDO centerRoleDO) {
        Map map = new HashMap();
        centerRoleDO.setCrteId(userId);
        centerRoleDO.setCrteName(userName);
        map.put("centerRoleDO", centerRoleDO);
        return centerRoleService_consumer.saveRole(map);
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
    public WrapperResponse<Boolean> deleteRoles(@RequestBody CenterRoleDTO centerRoleDTO) {
        Map map = new HashMap();
        map.put("centerRoleDTO", centerRoleDTO);
        return centerRoleService_consumer.deleteRoles(map);
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
    public WrapperResponse<List<TreeMenuNode>> getMenuTree(CenterRoleDTO centerRoleDTO) {
        Map map = new HashMap();
        map.put("centerRoleDTO", centerRoleDTO);
        return centerRoleService_consumer.getMenuTree(map);
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
    @PostMapping("/saveRoleMenus")
    public WrapperResponse<Boolean> saveRoleMenus(@RequestBody CenterRoleDTO centerRoleDTO) {
        Map map = new HashMap();
        map.put("centerRoleDTO", centerRoleDTO);
        return centerRoleService_consumer.saveRoleMenus(map);
    }


    /**
     * @Method: getRoleUsers
     * @Description: 获取所有的角色用户列表
     * @Param: [sysSystemDo]
     * @Author: pengbo
     * @Email: 254580179@qq.com
     * @Date: 2022/7/14 9:34
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.system.entity.SysSystemDo>>
     **/
    @GetMapping("/getRoleUsers")
    public WrapperResponse<List<CenterUserRoleDTO>> getRoleUsers(CenterUserRoleDTO centerUserRoleDTO) {
        Map map = new HashMap();
        map.put("centerUserRoleDTO", centerUserRoleDTO);
        return centerRoleService_consumer.getRoleUsers(map);
    }


    /**
     * @Method: saveRoleUsers
     * @Description: 保存所有的角色列表
     * @Param: [sysSystemDo]
     * @Author: pengbo
     * @Email: 254580179@qq.com
     * @Date: 2020/7/14 9:34
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.system.entity.SysSystemDo>>
     **/
    @PostMapping("/saveRoleUsers")
    public WrapperResponse<Boolean> saveRoleUsers(@RequestBody CenterUserRoleDTO centerUserRoleDTO) {
        Map map = new HashMap();
        centerUserRoleDTO.setCrteId(userId);
        centerUserRoleDTO.setCrteName(userName);
        map.put("centerUserRoleDTO", centerUserRoleDTO);
        return centerRoleService_consumer.saveRoleUsers(map);
    }
}
