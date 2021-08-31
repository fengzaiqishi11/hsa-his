package cn.hsa.module.center.user.dao;

import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.center.user.dto.CenterUserDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.center.user.dao
 * @class_name: CenterUserDAO
 * @Description: 中心品台 用户数据访问层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/8/3 16:27
 * @Company: 创智和宇
 **/
public interface CenterUserDAO {

    /**
     * @Method: queryById()
     * @Description: 根据用户id 医院编码查找用户信息
     * @Param: id主键
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: CenterUserDTO 用户数据传输对象
     */
    CenterUserDTO queryById(@Param("id") String id);

    /**
    * @Method queryByCode
    * @Param [code]
    * @description   根据用户code 医院编码查找用户信息
    * @author marong
    * @date 2020/10/28 15:47
    * @return cn.hsa.module.center.user.dto.CenterUserDTO
    * @throws
    */
    CenterUserDTO queryByCode(@Param("code") String code);


    /**
     * @Method: queryPage()
     * @Description: 根据条件分页查询 用户信息
     * @Param: CenterUserDTO 用户数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: CenterUserDTO 用户数据传输对象
     */
    List<CenterUserDTO> queryPage(CenterUserDTO centerUser);

    /**
     * @Method: insert()
     * @Description: 新增用户信息
     * @Param: CenterUserDTO 用户数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: 数据库影响的数目
     */
    int insert(CenterUserDTO centerUser);

    /**
     * @Method: update()
     * @Description: 新增用户信息
     * @Param: CenterUserDTO 用户数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: 数据库影响的数目
     */
    int update(CenterUserDTO centerUser);

    /**
     * @Method: deleteById()
     * @Description: 批量删除用户信息
     * @Param: CenterUserDTO 用户数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: 数据库影响的数目
     */
    int updateUserState(CenterUserDTO centerUser);

    /**
     * @Method: queryCode()
     * @Description: 查询用户的工号
     * @Param: CenterUserDTO 用户数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: CenterUserDTO 用户数据传输对象
     */

    CenterUserDTO queryCode(CenterUserDTO centerUserDTO);

    /**
     * @Method: queryStatueCode()
     * @Description: 查询用户的状态
     * @Param: CenterUserDTO 用户数据传输对象集合
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: CenterUserDTO 用户数据传输对象集合
     */
    List<CenterUserDTO> queryStatueCode(CenterUserDTO centerUserDTO);

    /**
     * @Method: queryStatueCode()
     * @Description: 查询用户的状态
     * @Param: CenterUserDTO 用户数据传输对象集合
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: CenterUserDTO 用户数据传输对象集合
     */
    CenterUserDTO queryValidUser(CenterUserDTO centerUserDTO);

    /**
     * @Method: queryByUserCode
     * @Description: 根据用户编码获取菜单权限
     * @Param: [userCode]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/31 17:43
     * @Return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<TreeMenuNode> queryByUserCode(@Param("userCode") String userCode);




    /**
     * @Method updateResetPassword()
     * @Desrciption  重置密码平台用户的密码
     * @Param  id 主键id
     *
     * @Author fuhui
     * @Date   2020/9/14 19:27
     * @Return 影响的行数
     **/
    int updateResetPassword(CenterUserDTO centerUserDTO);

    /**
     * @Method updateActivePassword()
     * @Desrciption  密码激活
     * @Param  id 主键id
     *
     * @Author fuhui
     * @Date   2020/9/14 19:27
     * @Return boolean
     **/
   int updateActivePassword(String id);

   /**
    * @Method queryCertCode()
    * @Desrciption  查询身份证号是否重复
    * @Param  CenterUserDTO 用户数据传输对象集合
    *
    * @Author fuhui
    * @Date   2020/9/20 19:17
    * @Return 影响的行数
   **/
    int queryCertCode(CenterUserDTO centerUserDTO);

    /**
     * @Method updatePswdErrCnt()
     * @Desrciption  记录输入密码的错误次数
     * @Param
     *
     * @Author fuhui
     * @Date   2020/10/9 10:18
     * @Return boolean
     **/
    int updatePswdErrCnt(CenterUserDTO centerUserDTO);

    /**
     * @Method: changePassWord()
     * @Description: 用户修改密码
     * @Param: userCode 用户账号 newPasswordByMd5 新设置的密码
     * @Author: marong
     * @Email: 564541256@qq.com
     * @Date: 2020/9/9 11：37
     * @Return: Boolean
     */
    boolean updatePassWord(@Param("id") String id,@Param("newPasswordByMd5") String newPasswordByMd5);
}