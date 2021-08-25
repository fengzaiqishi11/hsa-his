package cn.hsa.module.sync.syncrole.dao;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.sync.syncrole.dto.SyncRoleDTO;
import cn.hsa.module.sync.syncrole.dto.SyncRoleMenuButtonDTO;
import cn.hsa.module.sync.syncrole.entity.SyncRoleDO;
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
public interface SyncRoleDAO {

    /**
     * @Method: queryRoles
     * @Description: 获取角色列表
     * @Param: [sysRoleDto]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 11:26
     * @Return: java.util.List<cn.hsa.module.sys.syncrole.entity.SyncRoleDO>
     **/
    List<SyncRoleDO> queryRoles(SyncRoleDTO sysRoleDto);

    /**
     * @Method: save
     * @Description: 根据编码查询数量
     * @Param: [sysRoleDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 11:26
     * @Return: java.lang.Boolean
     **/
    int getCount(@Param("code") String code, @Param("id") String id);

    /**
     * @Method: saveRole
     * @Description: 保存角色
     * @Param: [syncRoleDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 11:26
     * @Return: java.lang.Boolean
     **/
    int insertRole(SyncRoleDO syncRoleDo);

    /**
     * @Method: saveRole
     * @Description: 更新角色
     * @Param: [syncRoleDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 11:26
     * @Return: java.lang.Boolean
     **/
    int updateRole(SyncRoleDO syncRoleDo);

    /**
     * @Method: deleteRoles
     * @Description: 删除
     * @Param: [sysRoleDto]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 11:26
     * @Return: java.lang.Boolean
     **/
    int deleteRoles(SyncRoleDTO sysRoleDto);

    /**
     * @Method: saveRoleButtons
     * @Description: 保存角色菜单按钮
     * @Param: [syncRoleMenuButtonDto]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 14:53
     * @Return: java.lang.Boolean
     **/
    int deleteRoleButtonByCode(SyncRoleMenuButtonDTO syncRoleMenuButtonDto);

    /**
     * @Method: insertRoleButtons
     * @Description: 保存角色菜单按钮
     * @Param: [syncRoleMenuButtonDto]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 14:53
     * @Return: java.lang.Boolean
     **/
    int insertRoleButtons(SyncRoleMenuButtonDTO syncRoleMenuButtonDto);

    /**
     * @Method: getCodeTree
     * @Description: 获取树形数据
     * @Param: [sysMenuButtonDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 14:11
     * @Return: boolean
     **/
    List<TreeMenuNode> getMenuTree(SyncRoleDTO sysRoleDto);
}
