package cn.hsa.sync.syncrole.bo.impl;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.sync.syncrole.bo.SyncRoleBO;
import cn.hsa.module.sync.syncrole.dao.SyncRoleDAO;
import cn.hsa.module.sync.syncrole.dto.SyncRoleDTO;
import cn.hsa.module.sync.syncrole.dto.SyncRoleMenuButtonDTO;
import cn.hsa.module.sync.syncrole.entity.SyncRoleDO;
import cn.hsa.module.sync.syncrole.entity.SyncRoleMenuButtonDO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.ListUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
* @Package_name: cn.hsa.sys.syncrole.bo.impl
* @class_name: SysRoleBOImpl
* @Description: 角色
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/7/30 11:25
* @Company: 创智和宇
**/
@Component
@Slf4j
public class SncRoleBOImpl extends HsafBO implements SyncRoleBO {

    @Resource
    private SyncRoleDAO syncRoleDAO;

    /**
     * @Method: queryRoles
     * @Description: 获取角色列表
     * @Param: [sysRoleDto]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 11:26
     * @Return: java.util.List<cn.hsa.module.sys.syncrole.entity.SyncRoleDO>
     **/
    @Override
    public List<SyncRoleDO> queryRoles(SyncRoleDTO syncRoleDTO) {
        return syncRoleDAO.queryRoles(syncRoleDTO);
    }

    /**
     * @Method: saveRoles
     * @Description: 保存角色
     * @Param: [syncRoleDO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 11:26
     * @Return: java.lang.Boolean
     **/
    @Override
    public Boolean saveRole(SyncRoleDO syncRoleDO) {
        if(StringUtils.isEmpty(syncRoleDO.getId())){
            syncRoleDO.setId("");
        }
        //根据编码查询数据,校验编码不能重复
        if(syncRoleDAO.getCount(syncRoleDO.getCode(),syncRoleDO.getId()) > 0){
            throw new RuntimeException("编码已存在,重复编码:"+syncRoleDO.getCode());
        }

        if(StringUtils.isEmpty(syncRoleDO.getId())){//新增
            syncRoleDO.setId(SnowflakeUtils.getId());
            syncRoleDO.setCrteTime(DateUtils.getNow());
            return syncRoleDAO.insertRole(syncRoleDO)>0;
        } else {//修改
            return syncRoleDAO.updateRole(syncRoleDO)>0;
        }
    }

    /**
     * @Method: deleteRoles
     * @Description: 删除
     * @Param: [sysRoleDto]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 11:26
     * @Return: java.lang.Boolean
     **/
    @Override
    public Boolean deleteRoles(SyncRoleDTO syncRoleDTO) {
        return syncRoleDAO.deleteRoles(syncRoleDTO)>0;
    }

    /**
     * @Method: saveRoleButtons
     * @Description: 保存角色菜单按钮
     * @Param: [sysRoleMenuButtonDto]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 14:53
     * @Return: java.lang.Boolean
     **/
    @Override
    public Boolean saveRoleButtons(SyncRoleMenuButtonDTO syncRoleMenuButtonDTO) {
        //删除角色下面授权的菜单
        syncRoleDAO.deleteRoleButtonByCode(syncRoleMenuButtonDTO);
        if(!ListUtils.isEmpty(syncRoleMenuButtonDTO.getButtons())){
          //新增需要授权的菜单
          for (SyncRoleMenuButtonDO roleMenuButtonDo:syncRoleMenuButtonDTO.getButtons()) {
            roleMenuButtonDo.setId(SnowflakeUtils.getId());
            roleMenuButtonDo.setCrteTime(DateUtils.getNow());
          }
          return syncRoleDAO.insertRoleButtons(syncRoleMenuButtonDTO)>0;
        }
        return true;
    }

    /**
     * @Method: getCodeTree
     * @Description: 获取菜单树结构
     * @Param: [sysMenuDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 14:12
     * @Return: java.util.List<cn.hsa.base.TreeMenuNode>
     **/
    @Override
    public List<TreeMenuNode> getMenuTree(SyncRoleDTO syncRoleDTO) {
        List<TreeMenuNode> list = syncRoleDAO.getMenuTree(syncRoleDTO);
        list.stream().forEach(node -> {
            list.stream().forEach(node1 -> {
                if (node.getId().equals(node1.getParentId())) {
                    node.setIsAble(false);
                }
            });
        });
        return list;
    }
}
