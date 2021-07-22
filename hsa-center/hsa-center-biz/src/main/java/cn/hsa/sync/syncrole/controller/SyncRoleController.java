package cn.hsa.sync.syncrole.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.syncrole.dto.SyncRoleDTO;
import cn.hsa.module.sync.syncrole.dto.SyncRoleMenuButtonDTO;
import cn.hsa.module.sync.syncrole.entity.SyncRoleDO;
import cn.hsa.module.sync.syncrole.entity.SyncRoleMenuButtonDO;
import cn.hsa.module.sync.syncrole.service.SyncRoleService;
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
@RequestMapping("/center/sync/role")
@Slf4j
public class SyncRoleController extends CenterBaseController {

    @Resource
    private SyncRoleService syncRoleService_consumer;

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
    public WrapperResponse<List<SyncRoleDO>> queryRoles(SyncRoleDTO syncRoleDTO) {
        Map map = new HashMap();
        map.put("syncRoleDTO", syncRoleDTO);
        return syncRoleService_consumer.queryRoles(map);
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
    public WrapperResponse<Boolean> saveRole(@RequestBody SyncRoleDO syncRoleDO) {
        Map map = new HashMap();
        syncRoleDO.setCrteId(userId);
        syncRoleDO.setCrteName(userName);
        map.put("syncRoleDO", syncRoleDO);
        return syncRoleService_consumer.saveRole(map);
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
    public WrapperResponse<Boolean> deleteRoles(@RequestBody SyncRoleDTO syncRoleDTO) {
        Map map = new HashMap();
        map.put("syncRoleDTO", syncRoleDTO);
        return syncRoleService_consumer.deleteRoles(map);
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
    public WrapperResponse<Boolean> saveRoleButtons(@RequestBody SyncRoleMenuButtonDTO syncRoleMenuButtonDTO) {
        Map map = new HashMap();
        for (SyncRoleMenuButtonDO roleMenuButtonDo:syncRoleMenuButtonDTO.getButtons()) {
            roleMenuButtonDo.setCrteId(userId);
            roleMenuButtonDo.setCrteName(userName);
            roleMenuButtonDo.setRoleCode(syncRoleMenuButtonDTO.getRoleCode());
        }
        map.put("syncRoleMenuButtonDTO", syncRoleMenuButtonDTO);
        return syncRoleService_consumer.saveRoleButtons(map);
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
    public WrapperResponse<List<TreeMenuNode>> getMenuTree(SyncRoleDTO syncRoleDTO) {
        Map map = new HashMap();
        map.put("syncRoleDTO", syncRoleDTO);
        return syncRoleService_consumer.getMenuTree(map);
    }
}
