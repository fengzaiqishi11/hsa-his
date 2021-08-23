package cn.hsa.module.sys.user.service;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.sys.user.service
 * @Class_name:: SysUserService
 * @Description:
 * @Author: liaojunjie
 * @Date: 2020/8/5 11:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-sys")
public interface SysUserService {
    /**
     * @Method getById
     * @Desrciption 通过id获取值
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/8/5 11:21
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.user.dto.SysUserDTO>
     **/
    @GetMapping("/getById")
    WrapperResponse<SysUserDTO> getById(Map map);

    /**
     * @Method queryAll
     * @Desrciption 查询所有人员信息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/8/5 11:21
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.user.dto.SysUserDTO>>
     **/
    @GetMapping("/queryAll")
    WrapperResponse<List<SysUserDTO>>queryAll(Map map);


    /**
     * @Method queryPage
     * @Desrciption 分页查询人员信息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/8/5 11:21
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);

    /**
     * @Method save
     * @Desrciption 修改、新增人员信息
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/8/5 11:22
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("save")
    WrapperResponse<Boolean> save(Map map);

    /**
     * @Method isCertNoExist
     * @Desrciption 暂时做判断身份证是否存在
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/9/2 19:22
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @GetMapping("/isCertNoExist")
    WrapperResponse<Boolean> isCertNoExist(Map map);

    /**
     * @Method updateResetPassword
     * @Desrciption 重置密码
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/10/28 14:43
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @GetMapping("/updateResetPassword")
    WrapperResponse<Boolean> updateResetPassword(Map map);

    /**
     * @Method updateActivePassword
     * @Desrciption 账号激活
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/10/28 14:43
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @GetMapping("/updateActivePassword")
    WrapperResponse<Boolean> updateActivePassword(Map map);

    /**
     * @Method updateSysUser
     * @Desrciption 修改用户系统关系
     * @Param
     * [map]
     * @Author liaojunjie
     * @Date   2020/12/16 10:28
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("updateSysUser")
    WrapperResponse<Boolean> updateSysUser(Map map);

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
    WrapperResponse<SysUserDTO> getByCode(Map map);

    /**
    * @Menthod queryUserByOperationDeptCode
    * @Desrciption  通过科室编码deptCode查询用户系统表，找到可以操作此科室用户编码（userCode），再去用户表通过userCode找到相应的用户
     * @param map 医院编码（hospCode），科室编码（deptCode）,职工类型（workTypeCode）等参数
    * @Author xingyu.xie
    * @Date   2020/9/1 11:30
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.user.dto.SysUserDTO>
    **/
    WrapperResponse<List<SysUserDTO>> queryUserByOperationDeptId(Map map);



    //分页查询医院用户系统信息
    WrapperResponse<PageDTO> queryUserSysPage(Map map);
    //查询用户角色树
    WrapperResponse<List<TreeMenuNode>> queryUserRoleTree(Map map);
    //绑定用户角色关系
    WrapperResponse<Boolean> saveUserRole(Map map);

    /**
     * @Method: changePassWord()
     * @Description: 用户修改密码
     * @Param: userCode 用户账号 newPasswordByMd5 新设置的密码
     * @Author: marong
     * @Email: 564541256@qq.com
     * @Date: 2020/9/9 11：37
     * @Return: Boolean
     */
    WrapperResponse<Boolean> changePassWord(Map changePassWordParam);

    /**
     * @Method: getTeachDoctor
     * @Description: 获取带教医生信息
     * @Param: [map]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/18 16:38
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.user.dto.SysUserDTO>
     **/
    WrapperResponse<SysUserDTO> getTeachDoctor(Map map);

    /**
     * @Method queryAll
     * @Desrciption 查询所有人员信息
     * @Param
     * [sysUserDTO]
     * @Author fuhui
     * @Date   2020/8/5 11:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.user.dto.SysUserDTO>>
     **/
    WrapperResponse<List<SysUserDTO>> querySysUserAll(Map map);

    /**
    * @Method queryAllByWorkTypeCode
    * @Param [map]
    * @description   根据职工类型模糊查询
    * @author marong
    * @date 2020/9/29 19:58
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.user.dto.SysUserDTO>>
    * @throws
    */
    WrapperResponse<List<SysUserDTO>> queryAllByWorkTypeCode(Map map);


    WrapperResponse<Boolean> upLoad(Map map);

    WrapperResponse<SysUserDTO> querySysUserHaveTeachDoctor(Map map);

    /**
     * @Method checkSysUserHaveTeachDoctor
     * @Param [map]
     * @description   校验带教医生
     * @author marong
     * @date 2020/9/29 19:58
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.user.dto.SysUserDTO>>
     * @throws
     */
    WrapperResponse<Boolean> checkSysUserHaveTeachDoctor(Map map);
    /**
     * @Method queryVisitDoctorByWorkTypeCode
     * @Param [sysUserDTO]
     * @description    根据用户状态、科室类型 职工类型模糊查询
     * @author zhangguorui
     * @date 2021/8/23
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.user.dto.SysUserDTO>>
     * @throws
     */
    WrapperResponse<List<SysUserDTO>> queryVisitDoctorByWorkTypeCode(Map map);
}
