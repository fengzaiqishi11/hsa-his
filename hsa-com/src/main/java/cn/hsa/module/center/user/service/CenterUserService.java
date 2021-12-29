package cn.hsa.module.center.user.service;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.user.dto.CenterUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.center.user.service
 * @class_name: CenterUserService
 * @Description: 中心品台用户信息数据服务层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/8/3 16:27
 * @Company: 创智和宇
 **/
@FeignClient(value = "hsa-center")
public interface CenterUserService {

    /**
     * @Method: queryById()
     * @Description: 根据用户id 医院编码查找用户信息
     * @Param: id主键  hospCode 医院编码
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: CenterUserDTO 用户数据传输对象
     */
    @GetMapping("/service/center/centerUser/getById")
    WrapperResponse<CenterUserDTO> getById(String id);

    /**
    * @Method getByCode
    * @Param [code]
    * @description   根据用户cde 查找用户信息
    * @author marong
    * @date 2020/10/28 15:44
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.user.dto.CenterUserDTO>
    * @throws
    */
    @GetMapping("/service/center/centerUser/getByCode")
    WrapperResponse<CenterUserDTO> getByCode(String code);

    /**
     * @Method: queryPage()
     * @Description: 根据条件分页查询 用户信息
     * @Param: CenterUserDTO 用户数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: CenterUserDTO 用户数据传输对象
     */
    @GetMapping("/service/center/centerUser/queryPage")
     WrapperResponse<PageDTO> queryPage(CenterUserDTO centerUserDTO);

    /**
     * @Method: insert()
     * @Description: 新增用户信息
     * @Param: CenterUserDTO 用户数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: 数据库影响的数目
     */
    @PostMapping("/service/center/centerUser/insert")
    WrapperResponse<Boolean> insert(CenterUserDTO centerUserDTO);

    /**
     * @Method: update()
     * @Description: 编辑用户信息
     * @Param: CenterUserDTO 用户数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: 数据库影响的数目
     */
    @PostMapping("/service/center/centerUser/update")
    WrapperResponse<Boolean> update(CenterUserDTO centerUserDTO);

    /**
     * @Method updatePswdErrCnt()
     * @Desrciption  记录输入密码的错误次数
     * @Param
     *
     * @Author fuhui
     * @Date   2020/10/9 10:18
     * @Return boolean
    **/
    @PostMapping("/service/center/centerUser/updatePswdErrCnt")
    WrapperResponse<Boolean> updatePswdErrCnt(CenterUserDTO centerUserDTO);


    /**
     * @Method: deleteById()
     * @Description: 批量删除用户信息
     * @Param: CenterUserDTO 用户数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: 数据库影响的数目
     */
    @PostMapping("/service/center/centerUser/updateUserState")
     WrapperResponse<Boolean> updateUserState(CenterUserDTO centerUserDTO);

    /**
     * @Method: queryCode()
     * @Description: 根据员工工号查询用户信息
     * @Param: CenterUserDTO 用户数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: CenterUserDTO 用户数据传输对象
     */
    @RequestMapping("/service/center/centerUser/queryCode")
    WrapperResponse<CenterUserDTO> queryCode(CenterUserDTO centerUserDTO);

    /**
     * @Method: queryByUserCode
     * @Description: 根据用户编码获取菜单权限
     * @Param: [userCode]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/31 17:36
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.menu.dto.CenterMenuDTO>
     **/
    WrapperResponse<List<TreeMenuNode>> queryByUserCode(String userCode);


    /**
     * @Method updateResetPassword()
     * @Desrciption  重置密码平台用户的密码
     * @Param  id 主键id
     *
     * @Author fuhui
     * @Date   2020/9/14 19:27
     * @Return boolean
     **/
    @GetMapping("/service/center/centerUser/updateResetPassword")
    WrapperResponse<Boolean> updateResetPassword(String id);

    /**
     * @Method updateActivePassword()
     * @Desrciption  密码激活
     * @Param  id 主键id
     *
     * @Author fuhui
     * @Date   2020/9/14 19:27
     * @Return boolean
     **/
    @GetMapping("/service/center/centerUser/updateActivePassword")
    WrapperResponse<Boolean> updateActivePassword(String id);

    /**
    * @Method changePassWord
    * @Param [changePassWordParam]
    * @description
    * @author marong
    * @date 2020/10/28 15:08
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    * @throws
    */

    @GetMapping("/service/center/centerUser/changePassWord")
    WrapperResponse<Boolean> updatePassWord(Map changePassWordParam);
}