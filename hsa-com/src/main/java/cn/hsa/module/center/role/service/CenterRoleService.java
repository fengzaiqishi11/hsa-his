package cn.hsa.module.center.role.service;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.role.entity.CenterRoleDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
* @Package_name: cn.hsa.module.center.role.service
* @class_name: CenterRoleService
* @Description: 角色权限service
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/8/4 15:34
* @Company: 创智和宇
**/
@FeignClient(value = "hsa-center")
public interface CenterRoleService {

    /**
     * @Method: queryRoles
     * @Description: 根据参数查询角色列表
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/4 15:58
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.center.role.entity.CenterRoleDO>>
     **/
    @GetMapping("/service/center/role/queryRoles")
    WrapperResponse<List<CenterRoleDO>> queryRoles(Map map);

    /**
     * @Method: saveRole
     * @Description: 保存角色
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/4 15:58
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/center/role/saveRole")
    WrapperResponse<Boolean> saveRole(Map map);

    /**
     * @Method: deleteRoles
     * @Description: 删除
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/4 15:59
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/center/role/deleteRoles")
    WrapperResponse<Boolean> deleteRoles(Map map);

    /**
     * @Method: getMenuTree
     * @Description: 根据参数查询菜单树状结构
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/4 15:59
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
     **/
    @GetMapping("/service/center/menu/getMenuTree")
    WrapperResponse<List<TreeMenuNode>> getMenuTree(Map map);

    /**
     * @Method: saveRoleMenus
     * @Description: 保存角色分配的权限
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/4 15:59
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/service/center/role/saveRoleMenus")
    WrapperResponse<Boolean> saveRoleMenus(Map map);
}