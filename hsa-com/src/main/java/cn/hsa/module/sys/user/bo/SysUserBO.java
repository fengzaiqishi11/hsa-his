package cn.hsa.module.sys.user.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.module.sys.user.dto.SysUserRoleDto;
import cn.hsa.module.sys.user.dto.SysUserSystemDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.sys.user.entity.bo
 * @Class_name: SysUserBO
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/7/31 15:08
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface SysUserBO {

    /**
     * @Method getById
     * @Desrciption 通过id获取值
     * @Param
     * [sysUserDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 11:23
     * @Return cn.hsa.module.sys.user.dto.SysUserDTO
     **/
    SysUserDTO getById(SysUserDTO sysUserDTO);

    /**
     * @Method queryAll
     * @Desrciption 查询所有人员信息
     * @Param
     * [sysUserDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 11:23
     * @Return java.util.List<cn.hsa.module.sys.user.dto.SysUserDTO>
     **/
    List<SysUserDTO> queryAll(SysUserDTO sysUserDTO);


    /**
     * @Method queryPage
     * @Desrciption 分页查询人员信息
     * @Param
     * [sysUserDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 11:23
     * @Return cn.hsa.base.PageDTO
     **/
    PageDTO queryPage(SysUserDTO sysUserDTO);

    PageDTO queryDeptUser(SysUserDTO sysUserDTO);

    /**
     * @Method updateSysUser
     * @Desrciption 修改用户系统关系
     * @Param
     * [sysUserDTO]
     * @Author liaojunjie
     * @Date   2020/12/8 12:13
     * @Return java.lang.Boolean
     **/
    Boolean updateSysUser(SysUserDTO sysUserDTO);

    /**
     * @Method save
     * @Desrciption 修改、新增人员信息
     * @Param
     * [sysUserDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 11:24
     * @Return java.lang.Boolean
     **/
    Boolean save(SysUserDTO sysUserDTO);

    /**
     * @Method isCertNoExist
     * @Desrciption 暂时做判断身份证是否存在
     * @Param
     * [sysUserDTO]
     * @Author liaojunjie
     * @Date   2020/9/2 19:24
     * @Return java.lang.Boolean
     **/
    Boolean isCertNoExist(SysUserDTO sysUserDTO);

    /**
     * @Method updateResetPassword
     * @Desrciption 重置密码
     * @Param
     * [sysUserDTO]
     * @Author liaojunjie
     * @Date   2020/10/28 14:46
     * @Return java.lang.Boolean
     **/
    Boolean updateResetPassword(SysUserDTO sysUserDTO);

    /**
     * @Method updateActivePassword
     * @Desrciption 激活密码
     * @Param
     * [sysUserDTO]
     * @Author liaojunjie
     * @Date   2020/10/28 14:46
     * @Return java.lang.Boolean
     **/
    Boolean updateActivePassword(SysUserDTO sysUserDTO);




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
    SysUserDTO getByCode(Map map);

    /**
    * @Menthod queryUserByOperationDeptId
    * @Desrciption  通过科室编码deptCode查询用户系统表，找到可以操作此科室用户编码（userCode），再去用户表通过userCode找到相应的用户
     * @param map
    * @Author xingyu.xie
    * @Date   2020/9/1 11:37
    * @Return java.util.List<cn.hsa.module.sys.user.dto.SysUserDTO>
    **/
    List<SysUserDTO> queryUserByOperationDeptId(Map map);

    //分页查询所有用户系统信息
    PageDTO queryUserSysPage(SysUserSystemDTO sysUserSystemDTO);
    //用户角色树
    List<TreeMenuNode> queryUserRoleTree(SysUserRoleDto sysUserRoleDto);
    //绑定用户角色关系
    Boolean saveUserRole(SysUserRoleDto sysUserRoleDto);


    /**
     * @Method: changePassWord()
     * @Description: 用户修改密码
     * @Param: userCode 用户账号 newPasswordByMd5 新设置的密码
     * @Author: marong
     * @Email: 564541256@qq.com
     * @Date: 2020/9/9 11：37
     * @Return: Boolean
     */
    boolean updatePassWord(Map map);

    /**
     * @Method: getTeachDoctor
     * @Description: 根据用户ID获取代教医生信息
     * @Param: [hospCode, id]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/15 15:01
     * @Return: cn.hsa.module.sys.user.dto.SysUserDTO
     **/
    SysUserDTO getTeachDoctor(String hospCode, String id);

    /**
    * @Method queryAllByWorkTypeCode
    * @Param [sysUserDTO]
    * @description   根据职工类型模糊查询
    * @author marong
    * @date 2020/9/29 19:59
    * @return java.lang.Object
    * @throws
    */
    List<SysUserDTO> queryAllByWorkTypeCode(SysUserDTO sysUserDTO);

    Boolean insertUpload(Map map);

    SysUserDTO querySysUserHaveTeachDoctor(Map map);

    Boolean checkSysUserHaveTeachDoctor(Map map);
    /**
     * @Method queryVisitDoctorByWorkTypeCode
     * @Param [sysUserDTO]
     * @description    根据用户状态、科室类型 职工类型模糊查询
     * @author zhangguorui
     * @date 2021/8/23
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.user.dto.SysUserDTO>>
     * @throws
     */
    List<SysUserDTO> queryVisitDoctorByWorkTypeCode(SysUserDTO sysUserDTO);

    /**
     * @Description: 更新已读公告的标识
     * @Param: [sysUserDTO, req, res]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangxuan
     * @Date: 2022-02-17
     */
    Boolean updateIsGuide(SysUserDTO sysUserDTO);
}
