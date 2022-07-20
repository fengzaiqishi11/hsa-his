package cn.hsa.module.center.role.bo;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.center.role.dto.CenterRoleDTO;
import cn.hsa.module.center.role.entity.CenterRoleDO;
import cn.hsa.module.center.user.dto.CenterUserRoleDTO;

import java.util.List;

/**
* @Package_name: cn.hsa.module.center.role.bo
* @class_name: CenterRoleBO
* @Description: 角色权限BO
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/8/4 15:45
* @Company: 创智和宇
**/
public interface CenterRoleBO {


    List<CenterRoleDO> queryRoles(CenterRoleDTO centerRoleDTO);


    Boolean saveRole(CenterRoleDO centerRoleDO);


    Boolean deleteRoles(CenterRoleDTO centerRoleDTO);


    List<TreeMenuNode> getMenuTree(CenterRoleDTO centerRoleDTO);


    Boolean saveRoleMenus(CenterRoleDTO centerRoleDTO);

    List<CenterUserRoleDTO> getRoleUsers(CenterUserRoleDTO centerUserRoleDTO);

    Boolean saveRoleUsers(CenterUserRoleDTO centerUserRoleDTO);
}