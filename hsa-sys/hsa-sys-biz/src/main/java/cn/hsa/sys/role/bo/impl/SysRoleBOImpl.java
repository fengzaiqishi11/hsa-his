package cn.hsa.sys.role.bo.impl;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.sys.role.bo.SysRoleBO;
import cn.hsa.module.sys.role.dao.SysRoleDao;
import cn.hsa.module.sys.role.dto.SysRoleDto;
import cn.hsa.module.sys.role.dto.SysRoleMenuButtonDto;
import cn.hsa.module.sys.role.entity.SysRoleDo;
import cn.hsa.module.sys.role.entity.SysRoleMenuButtonDo;
import cn.hsa.util.DateUtils;
import cn.hsa.util.ListUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
* @Package_name: cn.hsa.sys.role.bo.impl
* @class_name: SysRoleBOImpl
* @Description: 角色
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/7/30 11:25
* @Company: 创智和宇
**/
@Component
@Slf4j
public class SysRoleBOImpl extends HsafBO implements SysRoleBO {

    @Resource
    private SysRoleDao sysRoleDao;

    /**
     * @Method: queryRoles
     * @Description: 获取角色列表
     * @Param: [sysRoleDto]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 11:26
     * @Return: java.util.List<cn.hsa.module.sys.role.entity.SysRoleDo>
     **/
    @Override
    public List<SysRoleDo> queryRoles(SysRoleDto sysRoleDto) {
        return sysRoleDao.queryRoles(sysRoleDto);
    }

    /**
     * @Method: saveRoles
     * @Description: 保存角色
     * @Param: [sysRoleDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 11:26
     * @Return: java.lang.Boolean
     **/
    @Override
    public Boolean saveRole(SysRoleDo sysRoleDo) {
        if(StringUtils.isEmpty(sysRoleDo.getId())){
            sysRoleDo.setId("");
        }
        //根据编码查询数据,校验编码不能重复
        if(sysRoleDao.getCount(sysRoleDo.getCode(),sysRoleDo.getHospCode(),sysRoleDo.getId()) > 0){
            throw new RuntimeException("编码已存在,重复编码:"+sysRoleDo.getCode());
        }

        if(StringUtils.isEmpty(sysRoleDo.getId())){//新增
            sysRoleDo.setId(SnowflakeUtils.getId());
            sysRoleDo.setCrteTime(DateUtils.getNow());
            return sysRoleDao.insertRole(sysRoleDo)>0;
        } else {//修改
            return sysRoleDao.updateRole(sysRoleDo)>0;
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
    public Boolean deleteRoles(SysRoleDto sysRoleDto) {
        return sysRoleDao.deleteRoles(sysRoleDto)>0;
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
    public Boolean saveRoleButtons(SysRoleMenuButtonDto sysRoleMenuButtonDto) {
        //删除角色下面授权的菜单
        sysRoleDao.deleteRoleButtonByCode(sysRoleMenuButtonDto);
        if(!ListUtils.isEmpty(sysRoleMenuButtonDto.getButtons())){
          //新增需要授权的菜单
          for (SysRoleMenuButtonDo roleMenuButtonDo:sysRoleMenuButtonDto.getButtons()) {
            roleMenuButtonDo.setId(SnowflakeUtils.getId());
            roleMenuButtonDo.setCrteTime(DateUtils.getNow());
          }
          return sysRoleDao.insertRoleButtons(sysRoleMenuButtonDto)>0;
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
    public List<TreeMenuNode> getMenuTree(SysRoleDto sysRoleDto) {
        List<TreeMenuNode> list = sysRoleDao.getMenuTree(sysRoleDto);
        list.stream().forEach(node -> {
            list.stream().forEach(node1 -> {
                if (node.getCode().equals(node1.getParentCode())) {
                    node.setIsAble(false);
                }
            });
        });
        return list;
    }
}
