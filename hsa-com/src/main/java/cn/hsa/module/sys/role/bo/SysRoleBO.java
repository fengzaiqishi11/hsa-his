package cn.hsa.module.sys.role.bo;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.sys.role.dto.SysRoleDto;
import cn.hsa.module.sys.role.dto.SysRoleMenuButtonDto;
import cn.hsa.module.sys.role.entity.SysRoleDo;

import java.util.List;

/**
* @Package_name: cn.hsa.module.sys.role.bo
* @class_name: SysRoleBO
* @Description: 角色
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/7/30 11:43
* @Company: 创智和宇
**/
public interface SysRoleBO {

    /**
     * @Method: queryRoles
     * @Description: 获取角色列表
     * @Param: [sysRoleDto]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 11:26
     * @Return: java.util.List<cn.hsa.module.sys.role.entity.SysRoleDo>
     **/
    List<SysRoleDo> queryRoles(SysRoleDto sysRoleDto);

    /**
     * @Method: saveRoles
     * @Description: 保存角色
     * @Param: [sysRoleDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 11:26
     * @Return: java.lang.Boolean
     **/
    Boolean saveRole(SysRoleDo sysRoleDo);

    /**
     * @Method: deleteRoles
     * @Description: 删除
     * @Param: [sysRoleDto]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 11:26
     * @Return: java.lang.Boolean
     **/
    Boolean deleteRoles(SysRoleDto sysRoleDto);

    /**
     * @Method: saveRoleButtons
     * @Description: 保存角色菜单按钮
     * @Param: [sysRoleMenuButtonDto]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 14:53
     * @Return: java.lang.Boolean
     **/
    Boolean saveRoleButtons(SysRoleMenuButtonDto sysRoleMenuButtonDto);

    /**
     * @Method: getMenuTree
     * @Description: 获取菜单树结构
     * @Param: [sysMenuDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 14:12
     * @Return: java.util.List<cn.hsa.base.TreeMenuNode>
     **/
    List<TreeMenuNode> getMenuTree(SysRoleDto sysRoleDto);
}
