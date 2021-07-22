package cn.hsa.module.center.user.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.module.center.user.dto.CenterUserDTO;

import java.util.List;
import java.util.Map;


/**
 * @Package_name: cn.hsa.module.center.user.bo
 * @class_name: CenterUserBO
 * @Description: 中心平台 用户信息业务逻辑处理层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/8/3 16:37
 * @Company: 创智和宇
 **/
public interface CenterUserBO {
    /**
     * @Method: queryById()
     * @Description: 根据用户id 医院编码查找用户信息
     * @Param: id主键
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: CenterUserDTO 用户数据传输对象
     */
    CenterUserDTO queryById(String id);

    /**
    * @Method queryByCode
    * @Param [code]
    * @description   根据用户code 医院编码查找用户信息
    * @author marong
    * @date 2020/10/28 15:46
    * @return cn.hsa.module.center.user.dto.CenterUserDTO
    * @throws
    */
    CenterUserDTO queryByCode(String code);


    /**
     * @Method: queryPage()
     * @Description: 根据条件分页查询 用户信息
     * @Param: CenterUserDTO 用户数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: CenterUserDTO 用户数据传输对象
     */
    PageDTO queryPage(CenterUserDTO centerUser);

    /**
     * @Method: insert()
     * @Description: 新增用户信息
     * @Param: CenterUserDTO 用户数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: 数据库影响的数目
     */
    boolean insert(CenterUserDTO centerUser);

    /**
     * @Method: update()
     * @Description: 编辑用户信息
     * @Param: CenterUserDTO 用户数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: 数据库影响的数目
     */
    boolean update(CenterUserDTO centerUser);

    /**
     * @Method: updateUserState()
     * @Description: 批量修改用户的状态
     * @Param: CenterUserDTO 用户数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: 数据库影响的数目
     */
    boolean updateUserState(CenterUserDTO centerUser);

    /**
     * @Method: queryCode()
     * @Description: 根据员工工号查询用户信息
     * @Param: CenterUserDTO 用户数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: CenterUserDTO 用户数据传输对象
     */
    CenterUserDTO queryCode(CenterUserDTO centerUserDTO);

    /**
     * @Method: queryByUserCode
     * @Description: 根据用户编码获取菜单权限
     * @Param: [userCode]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/31 17:43
     * @Return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    List<TreeMenuNode> queryByUserCode(String userCode);



    /**
     * @Method updateResetPassword()
     * @Desrciption  重置密码平台用户的密码
     * @Param  id 主键id
     *
     * @Author fuhui
     * @Date   2020/9/14 19:27
     * @Return boolean
     **/
    boolean updateResetPassword(String id);

    /**
     * @Method updateActivePassword()
     * @Desrciption  密码激活
     * @Param  id 主键id
     *
     * @Author fuhui
     * @Date   2020/9/14 19:27
     * @Return boolean
     **/
    boolean  updateActivePassword(String id);

    /**
     * @Method updatePswdErrCnt()
     * @Desrciption  记录输入密码的错误次数
     * @Param
     *
     * @Author fuhui
     * @Date   2020/10/9 10:18
     * @Return boolean
     **/
    boolean updatePswdErrCnt(CenterUserDTO centerUserDTO);

    /**
    * @Method changePassWord
    * @Param [changePassWordParam] 
    * @description   
    * @author marong 
    * @date 2020/10/28 15:13
    * @return boolean  
    * @throws   
    */
    boolean changePassWord(Map changePassWordParam);


}
