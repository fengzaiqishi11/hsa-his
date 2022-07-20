package cn.hsa.center.role.bo.impl;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.center.role.bo.CenterRoleBO;
import cn.hsa.module.center.role.dao.CenterRoleDAO;
import cn.hsa.module.center.role.dto.CenterRoleDTO;
import cn.hsa.module.center.role.entity.CenterRoleDO;
import cn.hsa.module.center.role.entity.CenterRoleMenuDO;
import cn.hsa.module.center.user.dto.CenterUserRoleDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.ListUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @Package_name: cn.hsa.center.role.service.impl
* @class_name: CenterRoleBOImpl
* @Description: 角色权限BO实现类
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/8/4 15:52
* @Company: 创智和宇
**/
@Component
@Slf4j
public class CenterRoleBOImpl extends HsafBO implements CenterRoleBO {

    @Resource
    private CenterRoleDAO centerRoleDAO;

    /**
     * @Method: queryRoles
     * @Description: 根据参数查询角色列表
     * @Param: [centerRoleDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/4 16:04
     * @Return: java.util.List<cn.hsa.module.center.role.entity.CenterRoleDO>
     **/
    @Override
    public List<CenterRoleDO> queryRoles(CenterRoleDTO centerRoleDTO) {
        return centerRoleDAO.queryRoles(centerRoleDTO);
    }

    /**
     * @Method: saveRole
     * @Description: 保存角色
     * @Param: [centerRoleDO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/4 16:06
     * @Return: java.lang.Boolean
     **/
    @Override
    public Boolean saveRole(CenterRoleDO centerRoleDO) {
        if(StringUtils.isEmpty(centerRoleDO.getId())){
            centerRoleDO.setId("");
        }
        //根据编码查询数据,校验编码不能重复
        if(centerRoleDAO.getCount(centerRoleDO.getCode(),centerRoleDO.getId()) > 0){
            throw new RuntimeException("编码已存在,code:"+centerRoleDO.getCode());
        }

        if(StringUtils.isEmpty(centerRoleDO.getId())){//新增
            centerRoleDO.setId(SnowflakeUtils.getId());
            centerRoleDO.setCrteTime(DateUtils.getNow());
            centerRoleDO.setIsValid("1");
            return centerRoleDAO.insertRole(centerRoleDO)>0;
        } else {//修改
            return centerRoleDAO.updateRole(centerRoleDO)>0;
        }
    }

    /**
     * @Method: deleteRoles
     * @Description: 删除角色
     * @Param: [centerRoleDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/4 16:51
     * @Return: java.lang.Boolean
     **/
    @Override
    public Boolean deleteRoles(CenterRoleDTO centerRoleDTO) {
        return centerRoleDAO.deleteRoles(centerRoleDTO)>0;
    }

    /**
     * @Method: getMenuTree
     * @Description: 根据参数获取权限树状数据
     * @Param: [centerRoleDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/4 16:51
     * @Return: java.util.List<cn.hsa.base.TreeMenuNode>
     **/
    @Override
    public List<TreeMenuNode> getMenuTree(CenterRoleDTO centerRoleDTO) {
        List<TreeMenuNode> list = centerRoleDAO.getMenuTree(centerRoleDTO);
        list.stream().forEach(node -> {
            list.stream().forEach(node1 -> {
                if (node.getId().equals(node1.getParentId())) {
                    node.setIsAble(false);
                }
            });
        });
        return list;
    }

    /**
     * @Method: saveRoleMenus
     * @Description: 保存权限数据
     * @Param: [centerRoleDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/4 16:52
     * @Return: java.lang.Boolean
     **/
    @Override
    public Boolean saveRoleMenus(CenterRoleDTO centerRoleDTO) {
        //删除角色下面授权的菜单
        centerRoleDAO.deleteRoleMenuByCode(centerRoleDTO);

        //新增需要授权的菜单
        if(!ListUtils.isEmpty(centerRoleDTO.getMenuDOList())) {
            for (CenterRoleMenuDO centerRoleMenuDO:centerRoleDTO.getMenuDOList()) {
                centerRoleMenuDO.setId(SnowflakeUtils.getId());
                centerRoleMenuDO.setCrteTime(DateUtils.getNow());
                centerRoleMenuDO.setRoleCode(centerRoleDTO.getCode());
            }
            return centerRoleDAO.insertRoleMenus(centerRoleDTO)>0;
        }
        return true;
    }

    @Override
    public List<CenterUserRoleDTO> getRoleUsers(CenterUserRoleDTO centerUserRoleDTO) {
        return centerRoleDAO.getRoleUsers(centerUserRoleDTO);
    }

    @Override
    public Boolean saveRoleUsers(CenterUserRoleDTO centerUserRoleDTO) {

        if(!ListUtils.isEmpty(centerUserRoleDTO.getIds())) {
            centerRoleDAO.deleteRoleUsers(centerUserRoleDTO);
            List<CenterUserRoleDTO> list = new ArrayList<CenterUserRoleDTO>();
            CenterUserRoleDTO centerUserRole = null;
            for (String roleCode:centerUserRoleDTO.getIds()){
                centerUserRole = new CenterUserRoleDTO();
                centerUserRole.setId(SnowflakeUtils.getId());
                centerUserRole.setRoleCode(roleCode);
                centerUserRole.setUserCode(centerUserRoleDTO.getUserCode());
                centerUserRole.setCrteId(centerUserRoleDTO.getCrteId());
                centerUserRole.setCrteName(centerUserRoleDTO.getCrteName());
                centerUserRole.setCrteTime(new Date());
                list.add(centerUserRole);
            }
            centerRoleDAO.saveRoleUsers(list);
        }
        return true;
    }
}
