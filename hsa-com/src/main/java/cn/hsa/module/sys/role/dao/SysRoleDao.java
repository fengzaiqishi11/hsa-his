package cn.hsa.module.sys.role.dao;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.sys.role.dto.SysRoleDto;
import cn.hsa.module.sys.role.dto.SysRoleMenuButtonDto;
import cn.hsa.module.sys.role.entity.SysRoleDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name
 * @class_nameSysMenuDao
 * @Description 菜单
 * @Author youxianlin
 * @Email: 254580179@qq.com
 * @Date2020/7/28 14:32
 * @Company 创智和宇
 **/
public interface SysRoleDao {

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
     * @Method: save
     * @Description: 根据编码查询数量
     * @Param: [sysRoleDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 11:26
     * @Return: java.lang.Boolean
     **/
    int getCount(@Param("code") String code, @Param("hospCode") String hospCode, @Param("id") String id);

    /**
     * @Method: saveRole
     * @Description: 保存角色
     * @Param: [sysRoleDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 11:26
     * @Return: java.lang.Boolean
     **/
    int insertRole(SysRoleDo sysRoleDo);

    /**
     * @Method: saveRole
     * @Description: 更新角色
     * @Param: [sysRoleDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 11:26
     * @Return: java.lang.Boolean
     **/
    int updateRole(SysRoleDo sysRoleDo);

    /**
     * @Method: deleteRoles
     * @Description: 删除
     * @Param: [sysRoleDto]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 11:26
     * @Return: java.lang.Boolean
     **/
    int deleteRoles(SysRoleDto sysRoleDto);

    /**
     * @Method: saveRoleButtons
     * @Description: 保存角色菜单按钮
     * @Param: [sysRoleMenuButtonDto]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 14:53
     * @Return: java.lang.Boolean
     **/
    int deleteRoleButtonByCode(SysRoleMenuButtonDto sysRoleMenuButtonDto);

    /**
     * @Method: insertRoleButtons
     * @Description: 保存角色菜单按钮
     * @Param: [sysRoleMenuButtonDto]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 14:53
     * @Return: java.lang.Boolean
     **/
    int insertRoleButtons(SysRoleMenuButtonDto sysRoleMenuButtonDto);

    /**
     * @Method: getCodeTree
     * @Description: 获取树形数据
     * @Param: [sysMenuButtonDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 14:11
     * @Return: boolean
     **/
    List<TreeMenuNode> getMenuTree(SysRoleDto sysRoleDto);
}
