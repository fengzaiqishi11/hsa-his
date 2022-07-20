package cn.hsa.module.center.role.dao;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.center.role.dto.CenterRoleDTO;
import cn.hsa.module.center.role.entity.CenterRoleDO;
import cn.hsa.module.center.user.dto.CenterUserRoleDTO;
import org.apache.ibatis.annotations.Param;

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
public interface CenterRoleDAO {

    /**
     * @Method: queryRoles
     * @Description: 根据参数查询角色列表
     * @Param: [centerRoleDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/4 16:04
     * @Return: java.util.List<cn.hsa.module.center.role.entity.CenterRoleDO>
     **/
    List<CenterRoleDO> queryRoles(CenterRoleDTO centerRoleDTO);

    /**
     * @Method: deleteRoles
     * @Description: 删除角色
     * @Param: [centerRoleDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/4 16:51
     * @Return: java.lang.Boolean
     **/
    int deleteRoles(CenterRoleDTO centerRoleDTO);

    /**
     * @Method: getMenuTree
     * @Description: 根据参数获取权限树状数据
     * @Param: [centerRoleDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/4 16:51
     * @Return: java.util.List<cn.hsa.base.TreeMenuNode>
     **/
    List<TreeMenuNode> getMenuTree(CenterRoleDTO centerRoleDTO);

    /**
     * @Method: getCount
     * @Description: 根据编码和id查询数量
     * @Param: [centerRoleDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/4 16:51
     * @Return: java.util.List<cn.hsa.base.TreeMenuNode>
     **/
    int getCount(String code, String id);

    /**
     * @Method: insertRole
     * @Description: 新增角色
     * @Param: [centerRoleDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/4 16:51
     * @Return: java.util.List<cn.hsa.base.TreeMenuNode>
     **/
    int insertRole(CenterRoleDO centerRoleDO);

    /**
     * @Method: insertRole
     * @Description: 更新角色
     * @Param: [centerRoleDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/4 16:51
     * @Return: java.util.List<cn.hsa.base.TreeMenuNode>
     **/
    int updateRole(CenterRoleDO centerRoleDO);

    /**
     * @Method: deleteRoleMenuByCode
     * @Description: 根据角色编码删除相关权限
     * @Param: [centerRoleDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/4 16:51
     * @Return: java.util.List<cn.hsa.base.TreeMenuNode>
     **/
    int deleteRoleMenuByCode(CenterRoleDTO centerRoleDTO);

    /**
     * @Method: insertRoleMenus
     * @Description: 保存权限
     * @Param: [centerRoleDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/4 16:51
     * @Return: java.util.List<cn.hsa.base.TreeMenuNode>
     **/
    int insertRoleMenus(CenterRoleDTO centerRoleDTO);

    List<CenterUserRoleDTO> getRoleUsers(CenterUserRoleDTO centerUserRoleDTO);

    void deleteRoleUsers(CenterUserRoleDTO centerUserRoleDTO);

    void saveRoleUsers(@Param("list") List<CenterUserRoleDTO> list);
}