package cn.hsa.sync.syncrole.service.impl;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sync.syncrole.bo.SyncRoleBO;
import cn.hsa.module.sync.syncrole.dto.SyncRoleDTO;
import cn.hsa.module.sync.syncrole.dto.SyncRoleMenuButtonDTO;
import cn.hsa.module.sync.syncrole.entity.SyncRoleDO;
import cn.hsa.module.sync.syncrole.service.SyncRoleService;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import cn.hsa.util.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @Package_name: cn.hsa.sys.syncrole.service.impl
* @class_name: syncRoleBOImpl
* @Description: 角色API
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/7/30 11:10
* @Company: 创智和宇
**/
@HsafRestPath("/service/center/role")
@Slf4j
@Service("syncRoleService_provider")
public class SyncRoleServiceImpl extends HsafBO implements SyncRoleService {

    @Resource
    private SyncRoleBO syncRoleBO;

    /**
     * @Method: queryRoles
     * @Description: 获取角色列表
     * @Param: [sysRoleDto]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 11:12
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.syncrole.dto.SyncRoleDTO>>
     **/
    @HsafRestPath(value = "/queryRoles", method = RequestMethod.GET)
    @Override
    public WrapperResponse<List<SyncRoleDO>> queryRoles(Map map) {
        try {
            SyncRoleDTO syncRoleDTO = MapUtils.get(map, "syncRoleDTO");
            return WrapperResponse.success(syncRoleBO.queryRoles(syncRoleDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
     * @Method: saveRole
     * @Description: 保存
     * @Param: [sysRoleDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 11:12
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @HsafRestPath(value = "/saveRoles", method = RequestMethod.POST)
    @Override
    public WrapperResponse<Boolean> saveRole(Map map) {
        try {
            SyncRoleDO syncRoleDO = MapUtils.get(map, "syncRoleDO");
            // 参数校验
            if (syncRoleDO == null) {
                return WrapperResponse.error(400,"参数不能为空",null);
            }
            if (StringUtils.isEmpty(syncRoleDO.getCode())) {
                return WrapperResponse.error(400,"编码不能为空",null);
            }
            return WrapperResponse.success(syncRoleBO.saveRole(syncRoleDO));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
     * @Method: deleteRoles
     * @Description: s删除
     * @Param: [sysRoleDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 11:12
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @HsafRestPath(value = "/deleteRoles", method = RequestMethod.POST)
    @Override
    public WrapperResponse<Boolean> deleteRoles(Map map) {
        try {
            SyncRoleDTO syncRoleDTO = MapUtils.get(map, "syncRoleDTO");
            // 参数校验
            if (syncRoleDTO.getIds()==null && syncRoleDTO.getIds().size()<=0) {
                return WrapperResponse.error(400,"ids参数不能为空",null);
            }
            return WrapperResponse.success(syncRoleBO.deleteRoles(syncRoleDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
     * @Method: saveRoleButtons
     * @Description: 保存角色菜单按钮
     * @Param: [sysRoleMenuButtonDto]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 14:47
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @HsafRestPath(value = "/saveRoleButtons", method = RequestMethod.POST)
    @Override
    public WrapperResponse<Boolean> saveRoleButtons(Map map) {
        try {
            SyncRoleMenuButtonDTO syncRoleMenuButtonDTO = MapUtils.get(map, "syncRoleMenuButtonDTO");
            // 参数校验
            if (syncRoleMenuButtonDTO == null) {
                return WrapperResponse.error(400,"参数不能为空",null);
            }
            if (StringUtils.isEmpty(syncRoleMenuButtonDTO.getRoleCode())) {
                return WrapperResponse.error(400,"角色编码不能为空",null);
            }
            return WrapperResponse.success(syncRoleBO.saveRoleButtons(syncRoleMenuButtonDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
     * @Method: getMenuTree
     * @Description: 获取菜单树结构
     * @Param: [sysMenuDo]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 14:09
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
     **/
    @HsafRestPath(value = "/getMenuTree", method = RequestMethod.GET)
    @Override
    public WrapperResponse<List<TreeMenuNode>> getMenuTree(Map map) {
        try {
            SyncRoleDTO syncRoleDTO = MapUtils.get(map, "syncRoleDTO");
            return WrapperResponse.success(TreeUtils.buildByRecursive(syncRoleBO.getMenuTree(syncRoleDTO),"-1"));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }
}
