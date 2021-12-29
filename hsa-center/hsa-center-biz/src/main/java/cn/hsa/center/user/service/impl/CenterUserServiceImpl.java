package cn.hsa.center.user.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.user.bo.CenterUserBO;
import cn.hsa.module.center.user.dto.CenterUserDTO;
import cn.hsa.module.center.user.service.CenterUserService;
import cn.hsa.util.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

;

/**
 * @Package_name: cn.hsa.center.user.service.impl
 * @class_name: CenterUserServiceImpl
 * @Description:
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/8/3 16:57
 * @Company: 创智和宇
 **/
@HsafRestPath("/service/center/centerUser")
@Slf4j
@Service("centerUserService_provider")
public class CenterUserServiceImpl extends HsafService implements CenterUserService {

    /**
     * 中心平台 用户信息业务逻辑层
     */
    @Resource
    private CenterUserBO centerUserBO;

    /**
     * @Method: getById()
     * @Description: 根据id主键查询 用户信息
     * @Param: CenterUserDTO 用户数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: CenterUserDTO 用户数据传输对象
     */

    @Override
    @HsafRestPath(value = "/getById", method = RequestMethod.GET)
    public WrapperResponse<CenterUserDTO> getById(String id) {
        return WrapperResponse.success(centerUserBO.queryById(id));
    }

    /**
    * @Method getByCode
    * @Param [code]
    * @description   根据code查询 用户信息
    * @author marong
    * @date 2020/10/28 15:45
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.user.dto.CenterUserDTO>
    * @throws
    */
    @Override
    @HsafRestPath(value = "/getByCode", method = RequestMethod.GET)
    public WrapperResponse<CenterUserDTO> getByCode(String code) {
        return WrapperResponse.success(centerUserBO.queryByCode(code));
    }

    /**
     * @Method: queryPage()
     * @Description: 根据条件分页查询 用户信息
     * @Param: CenterUserDTO 用户数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: CenterUserDTO 用户数据传输对象
     */
    @Override
    @HsafRestPath(value = "/queryPage", method = RequestMethod.GET)
    public WrapperResponse<PageDTO> queryPage(CenterUserDTO centerUserDTO) {
        PageDTO pageDTO = centerUserBO.queryPage(centerUserDTO);
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Method: insert()
     * @Description: 新增用户信息
     * @Param: CenterUserDTO 用户数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: boolean
     */
    @Override
    @HsafRestPath(value = "/insert", method = RequestMethod.POST)
    public WrapperResponse<Boolean> insert(CenterUserDTO centerUserDTO) {
        return WrapperResponse.success(centerUserBO.insert(centerUserDTO));
    }

    /**
     * @Method: update()
     * @Description: 编辑用户信息
     * @Param: CenterUserDTO 用户数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: 数据库影响的数目
     */
    @Override
    @HsafRestPath(value = "/update", method = RequestMethod.POST)
    public WrapperResponse<Boolean> update(CenterUserDTO centerUserDTO) {

        return WrapperResponse.success(centerUserBO.update(centerUserDTO));

    }

    /**
     * @Method updatePswdErrCnt()
     * @Desrciption  记录输入密码的错误次数
     * @Param
     *
     * @Author fuhui
     * @Date   2020/10/9 10:18
     * @Return boolean
     **/
    @Override
    @HsafRestPath(value = "/updatePswdErrCnt", method = RequestMethod.POST)
    public WrapperResponse<Boolean> updatePswdErrCnt(CenterUserDTO centerUserDTO) {
        return WrapperResponse.success(centerUserBO.updatePswdErrCnt(centerUserDTO));
    }


    /**
     * @Method: deleteById()
     * @Description: 批量删除用户信息
     * @Param: CenterUserDTO 用户数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: 数据库影响的数目
     */
    @Override
    @HsafRestPath(value = "/updateUserState", method = RequestMethod.GET)
    public WrapperResponse<Boolean> updateUserState(CenterUserDTO centerUserDTO) {

        return WrapperResponse.success(centerUserBO.updateUserState(centerUserDTO));

    }

    /**
     * @Method: queryCode()
     * @Description: 根据员工工号查询用户信息
     * @Param: CenterUserDTO 用户数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: CenterUserDTO 用户数据传输对象
     */
    @Override
    @HsafRestPath(value = "/ queryCode", method = RequestMethod.OPTIONS)
    public WrapperResponse<CenterUserDTO> queryCode(CenterUserDTO centerUserDTO) {
        return WrapperResponse.success(centerUserBO.queryCode(centerUserDTO));
    }

    /**
     * @Method: queryByUserCode
     * @Description: 根据用户编码获取菜单权限
     * @Param: [userCode]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/31 17:36
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.center.menu.dto.CenterMenuDTO>
     **/
    @Override
    public WrapperResponse<List<TreeMenuNode>> queryByUserCode(String userCode) {

        return WrapperResponse.success(TreeUtils.buildByRecursive(centerUserBO.queryByUserCode(userCode), "-1"));

    }

    /**
     * @Method updateResetPassword()
     * @Desrciption 重置密码平台用户的密码
     * @Param id 主键id
     * @Author fuhui
     * @Date 2020/9/14 19:27
     * @Return boolean
     **/
    @Override
    @HsafRestPath(value = "/updateResetPassword", method = RequestMethod.GET)
    public WrapperResponse<Boolean> updateResetPassword(String id) {
        return WrapperResponse.success(centerUserBO.updateResetPassword(id));
    }

    /**
     * @Method updateActivePassword()
     * @Desrciption 密码激活
     * @Param id 主键id
     * @Author fuhui
     * @Date 2020/9/14 19:27
     * @Return boolean
     **/
    @Override
    @HsafRestPath(value = "/updateActivePassword", method = RequestMethod.GET)
    public WrapperResponse<Boolean> updateActivePassword(String id) {
        return WrapperResponse.success(centerUserBO.updateActivePassword(id));
    }

    @Override
    @HsafRestPath(value = "/changePassWordParam", method = RequestMethod.POST)
    public WrapperResponse<Boolean> changePassWord(Map changePassWordParam) {
        try {
            return WrapperResponse.success(centerUserBO.updatePassWord(changePassWordParam));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapperResponse.error(500,e.getMessage(),null);
        }
    }
}
