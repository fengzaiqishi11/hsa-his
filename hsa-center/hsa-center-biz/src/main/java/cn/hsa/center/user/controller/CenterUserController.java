package cn.hsa.center.user.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.user.dto.CenterUserDTO;
import cn.hsa.module.center.user.service.CenterUserService;
import cn.hsa.util.MD5Utils;
import cn.hsa.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * @Package_name: cn.hsa.module.center
 * @class_name: UserController
 * @Description:
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/8/3 17:12
 * @Company: 创智和宇
 **/
@RestController
@RequestMapping("/center/centerUser")
public class CenterUserController extends CenterBaseController {

    /**
     * 中心平台 用户信息消费者接口
     */
    @Resource
    private CenterUserService centerUserService_consumer;

    /**
     * @Method: getById()
     * @Description: 根据id主键查询 用户信息
     * @Param: CenterUserDTO 用户数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: CenterUserDTO 用户数据传输对象
     */
    @GetMapping("/getById")
    public WrapperResponse<CenterUserDTO> getById(String id){
        if(StringUtils.isEmpty(id)){
            throw new AppException("查询id参数为空");
        }
        return centerUserService_consumer.getById(id);
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
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(CenterUserDTO centerUserDTO){
        return centerUserService_consumer.queryPage(centerUserDTO);
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
    @PostMapping("/insert")
    public WrapperResponse<Boolean> insert(@RequestBody CenterUserDTO centerUserDTO){
        if(centerUserDTO ==null){
            throw new AppException("新增参数为空");
        }
        centerUserDTO.setCrteId(userId);
        centerUserDTO.setCrteName(userName);
        return centerUserService_consumer.insert(centerUserDTO);
    }

    /**
     * @Method: update()
     * @Description: 编辑用户信息
     * @Param: CenterUserDTO 用户数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: boolean
     */
    @PostMapping("/update")
    public WrapperResponse<Boolean> update(@RequestBody CenterUserDTO centerUserDTO){
        if(centerUserDTO ==null){
            throw new AppException("修改参数为空");
        }
        return centerUserService_consumer.update(centerUserDTO);
    }

    /**
     * @Method: deleteById()
     * @Description: 批量删除用户信息
     * @Param: CenterUserDTO 用户数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: boolean
     */
    @PostMapping("/updateUserState")
    public WrapperResponse<Boolean> updateUserState(@RequestBody CenterUserDTO centerUserDTO){
        if(centerUserDTO.getIds().size()<=0){
            return WrapperResponse.fail("请选择要批量删除的数据", null);
        }
        return centerUserService_consumer.updateUserState(centerUserDTO);
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
    @RequestMapping ("/queryCode")
    public WrapperResponse<CenterUserDTO> queryCode(CenterUserDTO centerUserDTO){
        if(StringUtils.isEmpty(centerUserDTO.getCode())){
            throw new AppException("员工工号参数为空");
        }
        return centerUserService_consumer.queryCode(centerUserDTO);
    }

    /**
     * @Method updateResetPassword()
     * @Desrciption  重置密码平台用户的密码
     * @Param  id 主键id
     *
     * @Author fuhui
     * @Date   2020/9/14 19:27
     * @Return boolean
    **/
    @GetMapping("/updateResetPassword")
    public WrapperResponse<Boolean> updateResetPassword(String id){
        if(StringUtils.isEmpty(id)){
            throw new AppException("id参数值为空");
        }
        return centerUserService_consumer.updateResetPassword(id);
    }

    /**
     * @Method updateActivePassword()
     * @Desrciption  密码激活
     * @Param  id 主键id
     *
     * @Author fuhui
     * @Date   2020/9/14 19:27
     * @Return boolean
     **/
    @GetMapping("/updateActivePassword")
    public WrapperResponse<Boolean> updateActivePassword(String id){
        if(StringUtils.isEmpty(id)){
            throw new AppException("id参数值为空");
        }
        return centerUserService_consumer.updateActivePassword(id);
    }


    /**
     * @Method: changePassWord()
     * @Description: 用户修改密码
     * @Param: CenterUserDTO 用户数据传输对象
     * @Author: marong
     * @Email: 564541256@qq.com
     * @Date: 2020/9/9 11：37
     * @Return: Boolean
     */
    @RequestMapping ("/changePassWord")
    public WrapperResponse<Boolean> changePassWord(@RequestBody CenterUserDTO centerUserDTO){
        if (centerUserDTO == null  || StringUtils.isEmpty(centerUserDTO.getOldPassWord()) ||
                StringUtils.isEmpty(centerUserDTO.getNewPassword()) ) {
            return WrapperResponse.error(-1,"参数不能为空",null);
        }

        // 获取用户信息
        CenterUserDTO centerUser = getData(centerUserService_consumer.getByCode(userId));

        // 校验用户信息
        if (centerUser == null) {
            throw new AppException("员工账号不存在！");
        }

        // 是否停用
        if ("2".equals(centerUser.getStatusCode())) {
            throw new AppException("当前账号已被停用！");
        }

        // 是否锁定
        if ("3".equals(centerUser.getStatusCode())) {
            throw new AppException("当前账号已被锁定！");
        }

        // 密码错误
        if (!MD5Utils.verifyMd5AndSha(centerUserDTO.getOldPassWord(), centerUser.getPassword())) {
            throw new AppException("原始密码错误！");
        }
        String newPasswordByMd5 = MD5Utils.getMd5AndSha(centerUserDTO.getNewPassword());
        Map changePassWordParam = new HashMap<>();
        changePassWordParam.put("oldPassWord",centerUserDTO.getOldPassWord());
        changePassWordParam.put("newPasswordByMd5",newPasswordByMd5);
        changePassWordParam.put("id",centerUser.getId());
        return centerUserService_consumer.changePassWord(changePassWordParam);
    }
}
