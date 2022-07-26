package cn.hsa.center.role.service.impl;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.role.bo.CenterRoleBO;
import cn.hsa.module.center.role.dto.CenterRoleDTO;
import cn.hsa.module.center.role.entity.CenterRoleDO;
import cn.hsa.module.center.role.service.CenterRoleService;
import cn.hsa.module.center.user.dto.CenterUserRoleDTO;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import cn.hsa.util.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

;

/**
* @Package_name: cn.hsa.center.role.bo.impl
* @class_name: SyncRoleServiceImpl
* @Description: 角色service实现层
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/8/4 15:50
* @Company: 创智和宇
**/
@HsafRestPath("/service/center/centerRole")
@Slf4j
@Service("centerRoleService_provider")
public class CenterRoleServiceImpl extends HsafService implements CenterRoleService {

    @Resource
    private CenterRoleBO centerRoleBO;

    /**
     * @Method: queryRoles
     * @Description: 根据参数查询角色列表
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/4 15:58
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.center.role.entity.CenterRoleDO>>
     **/
    @HsafRestPath(value = "/queryRoles", method = RequestMethod.GET)
    @Override
    public WrapperResponse<List<CenterRoleDO>> queryRoles(Map map) {
        try {
            CenterRoleDTO centerRoleDTO = MapUtils.get(map, "centerRoleDTO");
            return WrapperResponse.success(centerRoleBO.queryRoles(centerRoleDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
     * @Method: saveRole
     * @Description: 保存角色
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/4 15:58
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @HsafRestPath(value = "/saveRole", method = RequestMethod.POST)
    @Override
    public WrapperResponse<Boolean> saveRole(Map map) {
        try {
            CenterRoleDO centerRoleDO = MapUtils.get(map, "centerRoleDO");
            // 参数校验
            if (centerRoleDO == null) {
                return WrapperResponse.error(400,"参数不能为空",null);
            }
            if (StringUtils.isEmpty(centerRoleDO.getCode())) {
                return WrapperResponse.error(400,"编码不能为空",null);
            }
            return WrapperResponse.success(centerRoleBO.saveRole(centerRoleDO));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
     * @Method: deleteRoles
     * @Description: 删除
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/4 15:59
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @HsafRestPath(value = "/deleteRoles", method = RequestMethod.POST)
    @Override
    public WrapperResponse<Boolean> deleteRoles(Map map) {
        try {
            CenterRoleDTO centerRoleDTO = MapUtils.get(map, "centerRoleDTO");
            // 参数校验
            if (centerRoleDTO == null) {
                return WrapperResponse.error(400,"参数不能为空",null);
            }
            // 参数校验
            if (centerRoleDTO.getIds()==null && centerRoleDTO.getIds().size()<=0) {
                return WrapperResponse.error(400,"ids参数不能为空",null);
            }
            return WrapperResponse.success(centerRoleBO.deleteRoles(centerRoleDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
     * @Method: getMenuTree
     * @Description: 根据参数查询菜单树状结构
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/4 15:59
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
     **/
    @HsafRestPath(value = "/getMenuTree", method = RequestMethod.GET)
    @Override
    public WrapperResponse<List<TreeMenuNode>> getMenuTree(Map map) {
        try {
            CenterRoleDTO centerRoleDTO = MapUtils.get(map, "centerRoleDTO");
            return WrapperResponse.success(TreeUtils.buildByRecursive(centerRoleBO.getMenuTree(centerRoleDTO),"-1"));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
     * @Method: saveRoleMenus
     * @Description: 保存角色分配的权限
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/4 15:59
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @HsafRestPath(value = "/saveRoleMenus", method = RequestMethod.POST)
    @Override
    public WrapperResponse<Boolean> saveRoleMenus(Map map) {
        try {
            CenterRoleDTO centerRoleDTO = MapUtils.get(map, "centerRoleDTO");
            // 参数校验
            if (centerRoleDTO == null) {
                return WrapperResponse.error(400,"参数不能为空",null);
            }
            return WrapperResponse.success(centerRoleBO.saveRoleMenus(centerRoleDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
     * @param map
     * @Method: queryAll
     * @Description: 获取所有的角色用户列表
     * @Param: [sysSystemDo]
     * @Author: pengbo
     * @Email: 254580179@qq.com
     * @Date: 2022/7/14 9:34
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < cn.hsa.module.sys.system.entity.SysSystemDo>>
     */
    @Override
    public WrapperResponse<List<CenterUserRoleDTO>> getRoleUsers(Map map) {
        CenterUserRoleDTO centerUserRoleDTO = MapUtils.get(map,"centerUserRoleDTO");
        return WrapperResponse.success(centerRoleBO.getRoleUsers(centerUserRoleDTO));
    }

    /**
     * @param map
     * @Method: saveRoleUsers
     * @Description: 保存所有的角色列表
     * @Param: [sysSystemDo]
     * @Author: pengbo
     * @Email: 254580179@qq.com
     * @Date: 2020/7/14 9:34
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < cn.hsa.module.sys.system.entity.SysSystemDo>>
     */
    @Override
    public WrapperResponse<Boolean> saveRoleUsers(Map map) {
        CenterUserRoleDTO centerUserRoleDTO = MapUtils.get(map,"centerUserRoleDTO");
        return WrapperResponse.success(centerRoleBO.saveRoleUsers(centerUserRoleDTO));
    }
}
