package cn.hsa.sys.user.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sys.user.bo.SysUserBO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.module.sys.user.dto.SysUserRoleDto;
import cn.hsa.module.sys.user.service.SysUserService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.sys.user.service.impl
 * @Class_name: SysServiceImpl
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/7/31 15:25
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/sys/user")
@Slf4j
@Service("sysUserService_provider")
public class SysUserServiceImpl extends HsafService implements SysUserService {
    /**
     * 用户业务逻辑接口
     */
    @Resource
    private SysUserBO sysUserBO;

    /**
     * @Method getById
     * @Desrciption 通过id获取值
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/8/5 11:22
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.user.dto.SysUserDTO>
     **/
    @Override
    public WrapperResponse<SysUserDTO> getById(Map map) {
        return  WrapperResponse.success(sysUserBO.getById(MapUtils.get(map,"sysUserDTO")));
    }

    /**
     * @Method queryAll
     * @Desrciption 查询所有人员信息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/8/5 11:22
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.user.dto.SysUserDTO>>
     **/
    @Override
    public WrapperResponse<List<SysUserDTO>> queryAll(Map map) {
        return WrapperResponse.success(sysUserBO.queryAll(MapUtils.get(map,"sysUserDTO")));
    }


    /**
     * @Method queryPage
     * @Desrciption 分页查询人员信息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/8/5 11:22
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPage(Map map) {
        return WrapperResponse.success(sysUserBO.queryPage(MapUtils.get(map,"sysUserDTO")));
    }

    /**
     * @Method save
     * @Desrciption 修改、新增人员信息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/8/5 11:23
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> save(Map map) {
        return WrapperResponse.success(sysUserBO.save(MapUtils.get(map,"sysUserDTO")));
    }

    /**
     * @Method isCertNoExist
     * @Desrciption 暂时做判断身份证是否存在
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/9/2 19:23
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> isCertNoExist(Map map) {
        return WrapperResponse.success(sysUserBO.isCertNoExist(MapUtils.get(map,"sysUserDTO")));
    }

    /**
     * @Method updateResetPassword
     * @Desrciption 重置密码
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/16 10:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> updateResetPassword(Map map) {
        return WrapperResponse.success(sysUserBO.updateResetPassword(MapUtils.get(map,"sysUserDTO")));
    }

    /**
     * @Method updateActivePassword
     * @Desrciption 账号激活
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/16 10:29
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> updateActivePassword(Map map) {
        return WrapperResponse.success(sysUserBO.updateActivePassword(MapUtils.get(map,"sysUserDTO")));
    }

    /**
     * @Method updateSysUser
     * @Desrciption 修改用户系统关系
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/16 10:28
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/

    @Override
    public WrapperResponse<Boolean> updateSysUser(Map map) {
        return WrapperResponse.success(sysUserBO.updateSysUser(MapUtils.get(map,"sysUserDTO")));
    }
    /**
     * @Method getByCode
     * @Description 根据医院编码、登录工号获取用户信息
     *
     * @Param
     *
     * @Author zhongming
     * @Date 2020/8/6 11:54
     * @Return
     **/
    @Override
    @PostMapping("/service/sys/user/getByCode")
    public WrapperResponse<SysUserDTO> getByCode(Map map) {
        return WrapperResponse.success(sysUserBO.getByCode(map));
    }

    /**
    * @Menthod queryUserByOperationDeptCode
    * @Desrciption  通过科室编码deptCode查询用户系统表，找到可以操作此科室用户编码（userCode），再去用户表通过userCode找到相应的用户
     * @param map
    * @Author xingyu.xie
    * @Date   2020/9/1 11:34 
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.user.dto.SysUserDTO>
    **/
    @Override
    @PostMapping("/service/sys/user/queryUserByOperationDeptCode")
    public WrapperResponse<List<SysUserDTO>> queryUserByOperationDeptId(Map map) {
        return WrapperResponse.success(sysUserBO.queryUserByOperationDeptId(map));
    }
    

    @Override
    @PostMapping("/service/sys/user/queryUserSysPage")
    public WrapperResponse<PageDTO> queryUserSysPage(Map map) {
        return WrapperResponse.success(sysUserBO.queryUserSysPage(MapUtils.get(map,"sysUserSystemDTO")));
    }

    @Override
    @PostMapping("/service/sys/user/queryUserRoleTree")
    public WrapperResponse<List<TreeMenuNode>> queryUserRoleTree(Map map) {
        SysUserRoleDto sysUserRoleDto = MapUtils.get(map,"sysUserRoleDto");
        return WrapperResponse.success(sysUserBO.queryUserRoleTree(sysUserRoleDto));
    }

    @Override
    @PostMapping("/service/sys/user/saveUserRole")
    public WrapperResponse<Boolean> saveUserRole(Map map) {
        return WrapperResponse.success(sysUserBO.saveUserRole(MapUtils.get(map,"sysUserRoleDto")));
    }



    @Override
    @PostMapping("/service/sys/user/changePassWord")
    public WrapperResponse<Boolean> changePassWord(Map map) {
        try {
            return WrapperResponse.success(sysUserBO.updatePassWord(map));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }

    /**
     * @Method: getTeachDoctor
     * @Description: 获取带教医生信息
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/18 16:38
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.user.dto.SysUserDTO>
     **/
    @Override
    @GetMapping("/service/sys/user/getTeachDoctor")
    public WrapperResponse<SysUserDTO> getTeachDoctor(Map map) {
        return WrapperResponse.success(sysUserBO.getTeachDoctor(MapUtils.get(map,"hospCode"), MapUtils.get(map,"id")));
    }

    /**
     * @Method querySysUserAll
     * @Desrciption 查询所有人员信息
     * @Param
     * [sysUserDTO]
     * @Author fuhui
     * @Date   2020/8/5 11:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.user.dto.SysUserDTO>>
     **/
    @Override
    @PostMapping("/service/sys/user/querySysUserAll")
    public WrapperResponse<List<SysUserDTO>> querySysUserAll(Map map) {
        return WrapperResponse.success(sysUserBO.queryAll(MapUtils.get(map,"sysUserDTO")));
    }

    /**
    * @Method queryAllByWorkTypeCode
    * @Param [map]
    * @description   根据职工类型模糊查询
    * @author marong
    * @date 2020/9/29 19:58
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.user.dto.SysUserDTO>>
    * @throws
    */
    @Override
    public WrapperResponse<List<SysUserDTO>> queryAllByWorkTypeCode(Map map) {
        return WrapperResponse.success(sysUserBO.queryAllByWorkTypeCode(MapUtils.get(map,"sysUserDTO")));
    }

    @Override
    public WrapperResponse<Boolean> upLoad(Map map) {
        return WrapperResponse.success(sysUserBO.insertUpload(map));
    }
}
