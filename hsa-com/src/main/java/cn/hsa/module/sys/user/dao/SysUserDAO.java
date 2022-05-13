package cn.hsa.module.sys.user.dao;


import cn.hsa.module.sys.user.SysUserRoleDo;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.module.sys.user.dto.SysUserRoleDto;
import cn.hsa.module.sys.user.dto.SysUserSystemDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.sys.user.entity.dao
 * @Class_name:: SysUserDao
 * @Description:
 * @Author: liaojunjie
 * @Date: 2020/7/30 20:42
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface SysUserDAO {

    /**
     * @Method getById
     * @Desrciption  查询单个人员信息
     * @Param
     * [sysUserDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 11:25
     * @Return cn.hsa.module.sys.user.dto.SysUserDTO
     **/
    SysUserDTO getById(SysUserDTO sysUserDTO);

    /**
     * @Method queryAll
     * @Desrciption  查询所有人员信息
     * @Param
     * [sysUserDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 11:26
     * @Return java.util.List<cn.hsa.module.sys.user.dto.SysUserDTO>
     **/
    List<SysUserDTO> queryAll(SysUserDTO sysUserDTO);


    /**
    * @Menthod queryAll
    * @Desrciption 查询安床医生和护士  按权限
    *
    * @Param
    * [sysUserDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/8/4 16:07
    * @Return java.util.List<cn.hsa.module.sys.user.dto.SysUserDTO>
    **/
    List<SysUserDTO> queryBedUserAll(SysUserDTO sysUserDTO);

    /**
     * @Method queryPage
     * @Desrciption 分页查询人员信息
     * @Param
     * [sysUserDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 11:26
     * @Return java.util.List<cn.hsa.module.sys.user.dto.SysUserDTO>
     **/
    List<SysUserDTO> queryPage(SysUserDTO sysUserDTO);

    /**
     * @Method querySysUserSystemAll
     * @Desrciption 查询所有账号信息
     * @Param
     * [sysUserDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 11:26
     * @Return java.util.List<cn.hsa.module.sys.user.dto.SysUserSystemDTO>
     **/
    List<SysUserSystemDTO> querySysUserSystemAll(SysUserDTO sysUserDTO);

    /**
     * @Method insert
     * @Desrciption 新增人员信息
     * @Param
     * [sysUserDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 11:26
     * @Return java.lang.Integer
     **/
    Integer insert(SysUserDTO sysUserDTO);

    /**
     * @Method insertSysUserSystem
     * @Desrciption 新增账号信息
     * @Param
     * [sysUserSystemDTOS]
     * @Author liaojunjie
     * @Date   2020/8/5 11:27
     * @Return java.lang.Integer
     **/
    Integer insertSysUserSystem(List<SysUserSystemDTO> sysUserSystemDTOS);

    /**
     * @Method update
     * @Desrciption  修改用户
     * @Param
     * [sysUserDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 11:27
     * @Return java.lang.Integer
     **/
    Integer update(SysUserDTO sysUserDTO);

    /**
     * @Method updateSysUserSystem
     * @Desrciption 修改账号信息
     * @Param
     * [sysUserSystemDTOS]
     * @Author liaojunjie
     * @Date   2020/8/5 11:27
     * @Return java.lang.Integer
     **/
    Integer updateSysUserSystem(@Param("list") List<SysUserSystemDTO> sysUserSystemDTOS);

    /**
     * @Method deleteSysUserSystem
     * @Desrciption 批量删除账号信息
     * @Param
     * [sysUserDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 11:27
     * @Return java.lang.Integer
     **/
    Integer deleteSysUserSystem(SysUserDTO sysUserDTO);

    /**
     * @Method deleteSysUserSystem
     * @Desrciption 批量删除账号信息
     * @Param
     * [sysUserSystemDTOS]
     * @Author liaojunjie
     * @Date   2020/11/10 16:14
     * @Return java.lang.Integer
     **/
    Integer deleteSysUserSystemS(@Param("list") List<SysUserSystemDTO> sysUserSystemDTOS);

    /**
     * @Method isCodeExist
     * @Desrciption 判断员工工号是否存在
     * @Param
     * [sysUserDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 11:39
     * @Return java.lang.Integer
     **/
    Integer isCodeExist(SysUserDTO sysUserDTO);

    /**
     * @Method isCertNoExist
     * @Desrciption 暂时做判断身份证是否存在
     * @Param
     * [sysUserDTO]
     * @Author liaojunjie
     * @Date   2020/9/2 20:14
     * @Return java.lang.Integer
     **/
    Integer isCertNoExist(SysUserDTO sysUserDTO);

    /**
     * @Method updateResetPassword
     * @Desrciption 重置密码(密码重置后需要提醒用户修改密码)
     * @Param
     * [sysUserDTO]
     * @Author liaojunjie
     * @Date   2020/10/28 14:51
     * @Return java.lang.Integer
     **/
    Integer updateResetPassword(SysUserDTO sysUserDTO);

    /**
     * @Method updateActivePassword
     * @Desrciption 激活密码
     * @Param
     * [sysUserDTO]
     * @Author liaojunjie
     * @Date   2020/10/28 14:51
     * @Return java.lang.Integer
     **/
    Integer updateActivePassword(SysUserDTO sysUserDTO);



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
    * @Date   2020/9/1 11:45
    * @Return java.util.List<cn.hsa.module.sys.user.dto.SysUserDTO>
    **/
    List<SysUserDTO> queryUserByOperationDeptId(Map map);

    List<SysUserSystemDTO> queryUserSysPage(SysUserSystemDTO sysUserSystemDTO);

    List<SysUserRoleDo> queryCheckedUserRole(SysUserRoleDto sysUserRoleDto);

    void saveUserRole(List<SysUserRoleDto> list);

    void deleteUserRole(SysUserRoleDto sysUserRoleDto);

    /**
     * @Method: changePassWord()
     * @Description: 用户修改密码
     * @Param: userCode 用户账号 newPasswordByMd5 新设置的密码
     * @Author: marong
     * @Email: 564541256@qq.com
     * @Date: 2020/9/9 11：37
     * @Return: Boolean
     */
    int updatePassWord(Map<String,Object> params);

    int updatePassWordUnified(Map<String,String> params);

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
    * @date 2020/9/29 20:01
    * @return java.util.List<cn.hsa.module.sys.user.dto.SysUserDTO>
    * @throws
    */
    List<SysUserDTO> queryAllByWorkTypeCode(SysUserDTO sysUserDTO);

    /**
     * @Method queryAllUsCode
     * @Desrciption 查询所有需要删除的usCode
     * @Param
     * [sysUserSystemDTOS]
     * @Author liaojunjie
     * @Date   2020/11/19 16:15
     * @Return java.util.List<java.lang.String>
     **/
    List<String> queryAllUsCode (List<SysUserSystemDTO>sysUserSystemDTOS);

    Boolean deleteSysUserRole(SysUserRoleDto sysUserRoleDto);

    Boolean insertBatch(List<SysUserDTO> sysUserDTOS);

    SysUserDTO querySysUserHaveTeachDoctor(SysUserSystemDTO sysUserSystemDTO);

    List<SysUserDTO> queryVisitDoctorByWorkTypeCode(SysUserDTO sysUserDTO);

    /**
     * @Description: 查询门诊医生与收费员
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/10/20 10:17
     * @Return
     */
    List<SysUserDTO> queryMZAndSFYAllUser(SysUserDTO sysUserDTO);

    /**
     * @Description: 更新已读公告的标识
     * @Param: [sysUserDTO, req, res]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangxuan
     * @Date: 2022-02-17
     */
    Boolean updateIsGuide(SysUserDTO sysUserDTO);

    List<SysUserDTO> queryDeptUser(SysUserDTO sysUserDTO);
}
