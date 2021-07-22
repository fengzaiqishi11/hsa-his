package cn.hsa.module.sync.syncrole.service;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.syncrole.entity.SyncRoleDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name
 * @class_nameSysRoleApi
 * @Description 角色
 * @Author youxianlin
 * @Email: 254580179@qq.com
 * @Date2020/7/28 14:20
 * @Company 创智和宇
 **/
@FeignClient(value = "hsa-center")
public interface SyncRoleService {

    /**
     * @Method: queryRoles
     * @Description: 获取角色列表
     * @Param: [sysRoleDto]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 11:12
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.syncrole.dto.SyncRoleDTO>>
     **/
    @GetMapping("/service/center/role/queryRoles")
    WrapperResponse<List<SyncRoleDO>> queryRoles(Map map);

    /**
     * @Method: saveRole
     * @Description: 保存
     * @Param: [sysRoleDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 11:12
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/center/role/saveRoles")
    WrapperResponse<Boolean> saveRole(Map map);

    /**
     * @Method: deleteRoles
     * @Description: s删除
     * @Param: [sysRoleDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 11:12
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/center/role/deleteRoles")
    WrapperResponse<Boolean> deleteRoles(Map map);

    /**
     * @Method: saveRoleButtons
     * @Description: 保存角色菜单按钮
     * @Param: [sysRoleMenuButtonDto]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 14:47
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/center/role/saveRoleButtons")
    WrapperResponse<Boolean> saveRoleButtons(Map map);

    /**
     * @Method: getMenuTree
     * @Description: 获取菜单树结构
     * @Param: [sysMenuDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 14:09
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
     **/
    @GetMapping("/service/center/menu/getMenuTree")
    WrapperResponse<List<TreeMenuNode>> getMenuTree(Map map);

}
