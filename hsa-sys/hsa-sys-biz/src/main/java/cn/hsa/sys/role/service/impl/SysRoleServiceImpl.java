package cn.hsa.sys.role.service.impl;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sys.role.bo.SysRoleBO;
import cn.hsa.module.sys.role.dto.SysRoleDto;
import cn.hsa.module.sys.role.dto.SysRoleMenuButtonDto;
import cn.hsa.module.sys.role.entity.SysRoleDo;
import cn.hsa.module.sys.role.service.SysRoleService;
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
* @Package_name: cn.hsa.sys.role.service.impl
* @class_name: sysRoleBOImpl
* @Description: 角色API
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/7/30 11:10
* @Company: 创智和宇
**/
@HsafRestPath("/service/sys/role")
@Slf4j
@Service("sysRoleService_provider")
public class SysRoleServiceImpl extends HsafBO implements SysRoleService {

    @Resource
    private SysRoleBO sysRoleBO;

    /**
     * @Method: queryRoles
     * @Description: 获取角色列表
     * @Param: [sysRoleDto]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/7/30 11:12
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.role.dto.SysRoleDto>>
     **/
    @HsafRestPath(value = "/queryRoles", method = RequestMethod.GET)
    @Override
    public WrapperResponse<List<SysRoleDo>> queryRoles(Map map) {
        try {
            SysRoleDto sysRoleDto = MapUtils.get(map, "sysRoleDto");
            return WrapperResponse.success(sysRoleBO.queryRoles(sysRoleDto));
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
            SysRoleDo sysRoleDo = MapUtils.get(map, "sysRoleDo");
            // 参数校验
            if (sysRoleDo == null) {
                return WrapperResponse.error(400,"参数不能为空",null);
            }
            if (StringUtils.isEmpty(sysRoleDo.getCode())) {
                return WrapperResponse.error(400,"编码不能为空",null);
            }
            return WrapperResponse.success(sysRoleBO.saveRole(sysRoleDo));
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
            SysRoleDto sysRoleDto = MapUtils.get(map, "sysRoleDto");
            // 参数校验
            if (sysRoleDto.getIds()==null && sysRoleDto.getIds().size()<=0) {
                return WrapperResponse.error(400,"ids参数不能为空",null);
            }
            return WrapperResponse.success(sysRoleBO.deleteRoles(sysRoleDto));
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
            SysRoleMenuButtonDto sysRoleMenuButtonDto = MapUtils.get(map, "sysRoleMenuButtonDto");
            // 参数校验
            if (sysRoleMenuButtonDto == null) {
                return WrapperResponse.error(400,"参数不能为空",null);
            }
            if (StringUtils.isEmpty(sysRoleMenuButtonDto.getRoleCode())) {
                return WrapperResponse.error(400,"角色编码不能为空",null);
            }
            return WrapperResponse.success(sysRoleBO.saveRoleButtons(sysRoleMenuButtonDto));
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
            SysRoleDto sysRoleDto = MapUtils.get(map, "sysRoleDto");
            return WrapperResponse.success(TreeUtils.buildByRecursive1(sysRoleBO.getMenuTree(sysRoleDto),"-1"));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }
}
