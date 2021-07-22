package cn.hsa.module.center;

import cn.hsa.base.BaseController;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.role.dto.CenterRoleDTO;
import cn.hsa.module.center.role.entity.CenterRoleDO;
import cn.hsa.module.center.role.entity.CenterRoleMenuDO;
import cn.hsa.module.center.role.service.CenterRoleService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Package_name: cn.hsa.module.center
* @class_name: CenterRoleController
* @Description: 角色权限管理控制层
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2020/8/4 17:24
* @Company: 创智和宇
**/
@RestController
@RequestMapping("/web/center/centerRole")
public class CenterRoleController extends BaseController {

    @Resource
    private CenterRoleService centerRoleService_consumer;

    /**
     * @Method: queryRoles
     * @Description: 根据参数查询角色列表
     * @Param: [centerRoleDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/4 17:28
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.center.role.entity.CenterRoleDO>>
     **/
    @GetMapping("/queryRoles")
    public WrapperResponse<List<CenterRoleDO>> queryRoles(CenterRoleDTO centerRoleDTO) {
        Map map = new HashMap();
        map.put("sysRoleDto", centerRoleDTO);
        return centerRoleService_consumer.queryRoles(map);
    }

    /**
     * @Method: saveRole
     * @Description: 保存角色
     * @Param: [centerRoleDO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/4 17:30
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/saveRole")
    public WrapperResponse<Boolean> saveRole(@RequestBody CenterRoleDO centerRoleDO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        Map map = new HashMap();
        centerRoleDO.setCrteId(userDTO.getId());
        centerRoleDO.setCrteName(userDTO.getName());
        map.put("centerRoleDO", centerRoleDO);
        return centerRoleService_consumer.saveRole(map);
    }

    /**
     * @Method: deleteRoles
     * @Description: 删除角色
     * @Param: [centerRoleDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/4 17:31
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/deleteRoles")
    public WrapperResponse<Boolean> deleteRoles(@RequestBody CenterRoleDTO centerRoleDTO) {
        Map map = new HashMap();
        map.put("centerRoleDTO", centerRoleDTO);
        return centerRoleService_consumer.deleteRoles(map);
    }

    /**
     * @Method: saveRoleMenus
     * @Description: 保存角色菜单
     * @Param: [centerRoleDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/4 17:32
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("/saveRoleMenus")
    public WrapperResponse<Boolean> saveRoleMenus(@RequestBody CenterRoleDTO centerRoleDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        Map map = new HashMap();
        for (CenterRoleMenuDO centerRoleMenuDO:centerRoleDTO.getMenuDOList()) {
            centerRoleMenuDO.setCrteId(userDTO.getId());
            centerRoleMenuDO.setCrteName(userDTO.getName());
        }
        map.put("centerRoleDTO", centerRoleDTO);
        return centerRoleService_consumer.saveRoleMenus(map);
    }

    /**
     * @Method: getMenuTree
     * @Description: 根据参数查询菜单树状结构
     * @Param: [centerRoleDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/4 17:34
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.base.TreeMenuNode>>
     **/
    @GetMapping("/getMenuTree")
    public WrapperResponse<List<TreeMenuNode>> getMenuTree(CenterRoleDTO centerRoleDTO) {
        Map map = new HashMap();
        map.put("centerRoleDTO", centerRoleDTO);
        return centerRoleService_consumer.getMenuTree(map);
    }

}
