package cn.hsa.center.user.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.syncorderrule.service.SyncOrderRuleService;
import cn.hsa.module.center.user.bo.CenterUserBO;
import cn.hsa.module.center.user.dao.CenterUserDAO;
import cn.hsa.module.center.user.dto.CenterUserDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.center.user.service.impl
 * @class_name: CenterUserBOImpl
 * @Description: 中心平台 用户信息业务逻辑实现层
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2020/8/3 16:52
 * @Company: 创智和宇
 **/
@Component
@Slf4j
public class CenterUserBOImpl extends HsafBO implements CenterUserBO {
    /**
     * 中心平台 用户信息数据访问层
     */
    @Resource
    private CenterUserDAO centerUserDAO;

    /**
     * 中心平台 单据规则生成service层
     */
    @Resource
    private SyncOrderRuleService syncOrderRuleService;

    /**
     * @Method: queryById()
     * @Description: 根据用户id 查找用户信息
     * @Param: id主键
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: CenterUserDTO 用户数据传输对象
     */
    @Override
    public CenterUserDTO queryById(String id) {
        return centerUserDAO.queryById(id);
    }

    /**
    * @Method queryByCode
    * @Param [code]
    * @description   根据用户code 查找用户信息
    * @author marong
    * @date 2020/10/28 15:46
    * @return cn.hsa.module.center.user.dto.CenterUserDTO
    * @throws
    */
    @Override
    public CenterUserDTO queryByCode(String code) {
        return centerUserDAO.queryByCode(code);
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
    public PageDTO queryPage(CenterUserDTO centerUser) {
        // 设置分页信息
        PageHelper.startPage(centerUser.getPageNo(), centerUser.getPageSize());
        List<CenterUserDTO> centerUserList = centerUserDAO.queryPage(centerUser);
        return PageDTO.of(centerUserList);
    }

    /**
     * @Method: insert()
     * @Description: 新增用户信息
     * @Param: CenterUserDTO 用户数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: booelean
     */
    @Override
    public boolean insert(CenterUserDTO centerUserDTO) {
        centerUserDTO.setCrteTime(DateUtils.getNow());
        return saveCenterUser(centerUserDTO);
    }

    /**
     * @Method: saveCenterUser()
     * @Description: 删除/新增共用方法
     * @Param:   CenterUserDTO 用户数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/4 22:41
     * @Return:
     */
    private boolean saveCenterUser(CenterUserDTO centerUserDTO) {
        // 查询工号是否重复
        CenterUserDTO dto=centerUserDAO.queryCode(centerUserDTO);
        if( dto!=null && !StringUtils.isEmpty(dto.getCode() ) ){
            throw new AppException("该工号已经重复:"+dto.getCode());
        }
        if(dto!=null && !StringUtils.isEmpty(centerUserDTO.getCertNo())){
            int count = centerUserDAO.queryCertCode(centerUserDTO);
            if(count >0){
                throw new AppException("该证件号码已经重复:"+centerUserDTO.getCertNo());
            }
        }
        //生成拼音码
        if (StringUtils.isEmpty(centerUserDTO.getPym())) {
            centerUserDTO.setPym(PinYinUtils.toFirstPY(centerUserDTO.getName()));
        }
        //生成五笔码
        if (StringUtils.isEmpty(centerUserDTO.getWbm())) {
            centerUserDTO.setWbm(WuBiUtils.getWBCode(centerUserDTO.getName()));
        }
        // id不为空 说明为新增操作
        if(StringUtils.isEmpty(centerUserDTO.getId())){
            centerUserDTO.setId(SnowflakeUtils.getId());

            //初始密码加密
            centerUserDTO.setPassword(MD5Utils.getMd5AndSha("888888"));
            return centerUserDAO.insert(centerUserDTO) > 0;
        }
        else{
            return centerUserDAO.update(centerUserDTO)>0;
        }
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
    @Override
    public boolean update(CenterUserDTO centerUser) {
        return saveCenterUser(centerUser);
    }

    /**
     * @Method: updateUserState()
     * @Description: 批量修改用户的状态
     * @Param: CenterUserDTO 用户数据传输对象
     * @Author: fuhui
     * @Email: 3277857701@qq.com
     * @Date: 2020/8/3 16:31
     * @Return: boolean
     */

    @Override
    public boolean updateUserState(CenterUserDTO centerUser) {
        if(centerUser.getIds().size() <=0){
            throw new AppException("要操作的参数Id为空");
        }
        else {
            return centerUserDAO.updateUserState(centerUser) > 0;
        }
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
    public CenterUserDTO queryCode(CenterUserDTO centerUserDTO) {
        return centerUserDAO.queryValidUser(centerUserDTO);
    }

    /**
     * @Method: queryByUserCode
     * @Description: 根据用户编码获取菜单权限
     * @Param: [userCode]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/8/31 17:43
     * @Return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public List<TreeMenuNode> queryByUserCode(String userCode) {
        return centerUserDAO.queryByUserCode(userCode);
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
    @Override
    public boolean updateResetPassword(String id) {
        CenterUserDTO centerUserDTO =new CenterUserDTO();
        centerUserDTO.setId(id);
        centerUserDTO.setPassword(MD5Utils.getMd5AndSha("888888"));
        return centerUserDAO.updateResetPassword(centerUserDTO)>0;
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
    @Override
    public boolean updateActivePassword(String id) {
        CenterUserDTO centerUserDTO = centerUserDAO.queryById(id);
        if(!"2".equals(centerUserDTO.getStatusCode())){
            throw new AppException("该用户没有处于冻结状态，不需要进行激活操作");
        }
        return centerUserDAO.updateActivePassword(id)>0;
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
    public boolean updatePswdErrCnt(CenterUserDTO centerUserDTO) {
        return centerUserDAO.updatePswdErrCnt(centerUserDTO)>0;
    }

    @Override
    public boolean changePassWord(Map map) {
        String id= map.get("id")==null?"":map.get("id").toString();
        String newPasswordByMd5= map.get("newPasswordByMd5")==null?"":map.get("newPasswordByMd5").toString();
        if(StringUtils.isEmpty(id) || StringUtils.isEmpty(newPasswordByMd5) ){
            throw new AppException("数据不能为空！");
        }
        return centerUserDAO.updatePassWord(id,newPasswordByMd5);
    }
}
