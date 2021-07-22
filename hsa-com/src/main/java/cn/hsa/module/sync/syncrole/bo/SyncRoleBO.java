package cn.hsa.module.sync.syncrole.bo;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.sync.syncrole.dto.SyncRoleDTO;
import cn.hsa.module.sync.syncrole.dto.SyncRoleMenuButtonDTO;
import cn.hsa.module.sync.syncrole.entity.SyncRoleDO;

import java.util.List;

/**
* @Package_name: cn.hsa.module.sys.syncrole.bo
* @class_name: SyncRoleBO
* @Description: 角色
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/7/30 11:43
* @Company: 创智和宇
**/
public interface SyncRoleBO {

    /**
     * @Method: queryRoles
     * @Description: 获取角色列表
     * @Param: [sysRoleDto]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 11:26
     * @Return: java.util.List<cn.hsa.module.sys.syncrole.entity.SyncRoleDO>
     **/
    List<SyncRoleDO> queryRoles(SyncRoleDTO syncRoleDTO);

    /**
     * @Method: saveRoles
     * @Description: 保存角色
     * @Param: [syncRoleDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 11:26
     * @Return: java.lang.Boolean
     **/
    Boolean saveRole(SyncRoleDO syncRoleDO);

    /**
     * @Method: deleteRoles
     * @Description: 删除
     * @Param: [sysRoleDto]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 11:26
     * @Return: java.lang.Boolean
     **/
    Boolean deleteRoles(SyncRoleDTO syncRoleDTO);

    /**
     * @Method: saveRoleButtons
     * @Description: 保存角色菜单按钮
     * @Param: [syncRoleMenuButtonDto]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 14:53
     * @Return: java.lang.Boolean
     **/
    Boolean saveRoleButtons(SyncRoleMenuButtonDTO syncRoleMenuButtonDTO);

    /**
     * @Method: getMenuTree
     * @Description: 获取菜单树结构
     * @Param: [sysMenuDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 14:12
     * @Return: java.util.List<cn.hsa.base.TreeMenuNode>
     **/
    List<TreeMenuNode> getMenuTree(SyncRoleDTO syncRoleDTO);
}
