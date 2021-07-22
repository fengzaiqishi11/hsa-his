package cn.hsa.module.sys;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.module.sys.user.dto.SysUserPasswordDTO;
import cn.hsa.module.sys.user.dto.SysUserRoleDto;
import cn.hsa.module.sys.user.dto.SysUserSystemDTO;
import cn.hsa.module.sys.user.service.SysUserService;
import cn.hsa.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.sys
 * @Class_name: SysUserController
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/7/31 15:40
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/sys/user")
@Slf4j
public class SysUserController extends BaseController {

    /**
     * 用户信息维护dubbo消费者接口
     */
    @Resource
    private SysUserService sysUserService_consumer;

    /**
     * @Method getById
     * @Desrciption 通过id获取值
     * @Param
     * [sysUserDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 11:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.sys.user.dto.SysUserDTO>
     **/
    @GetMapping("/getById")
    public WrapperResponse<SysUserDTO> getById(SysUserDTO sysUserDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTOSession = getSession(req, res);
        sysUserDTO.setHospCode(sysUserDTOSession.getHospCode());
        //封装参数
        Map map = new HashMap();
        map.put("hospCode",sysUserDTOSession.getHospCode());
        map.put("sysUserDTO",sysUserDTO);
        return sysUserService_consumer.getById(map);
    }

    /**
     * @Method queryAll
     * @Desrciption 查询所有人员信息
     * @Param
     * [sysUserDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 11:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.user.dto.SysUserDTO>>
     **/
    @GetMapping("/queryAll")
    public WrapperResponse<List<SysUserDTO>> queryAll(SysUserDTO sysUserDTO, HttpServletRequest req, HttpServletResponse res)
    {
        SysUserDTO sysUserDTOSession = getSession(req, res);
        sysUserDTO.setHospCode(sysUserDTOSession.getHospCode());
        //封装参数
        Map map = new HashMap();
        map.put("hospCode",sysUserDTOSession.getHospCode());
        map.put("sysUserDTO",sysUserDTO);
        WrapperResponse<List<SysUserDTO>> listWrapperResponse = sysUserService_consumer.queryAll(map);
        return listWrapperResponse;
    }

    /**
     * @Method queryFinancial
     * @Desrciption 只查询收费人员
     * @Param
     * [sysUserDTO]
     * @Author lizihuan
     * @Date   2021/06/18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.user.dto.SysUserDTO>>
     **/
    @GetMapping("/queryFinancial")
    public WrapperResponse<List<SysUserDTO>> queryFinancial(SysUserDTO sysUserDTO, HttpServletRequest req, HttpServletResponse res)
    {
        SysUserDTO sysUserDTOSession = getSession(req, res);
        sysUserDTO.setHospCode(sysUserDTOSession.getHospCode());
        //封装参数
        Map map = new HashMap();
        map.put("hospCode",sysUserDTOSession.getHospCode());
        map.put("sysUserDTO",sysUserDTO);
        WrapperResponse<List<SysUserDTO>> listWrapperResponse = sysUserService_consumer.queryAll(map);
        return listWrapperResponse;
    }

    /**查询所有人员信息
    * @Method queryPostAll
    * @Desrciption
    * @param sysUserDTO
    * @Author liuqi1
    * @Date   2021/4/30 14:52
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.user.dto.SysUserDTO>>
    **/
    @PostMapping("/queryPostAll")
    public WrapperResponse<List<SysUserDTO>> queryPostAll(@RequestBody SysUserDTO sysUserDTO, HttpServletRequest req, HttpServletResponse res)
    {
        SysUserDTO sysUserDTOSession = getSession(req, res);
        sysUserDTO.setHospCode(sysUserDTOSession.getHospCode());
        //是否只查登录科室的数据
        if(sysUserDTO.getIsQuerySelfDept() != null && sysUserDTO.getIsQuerySelfDept()){
            sysUserDTO.setDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }

        //封装参数
        Map map = new HashMap();
        map.put("hospCode",sysUserDTOSession.getHospCode());
        map.put("sysUserDTO",sysUserDTO);
        WrapperResponse<List<SysUserDTO>> listWrapperResponse = sysUserService_consumer.queryAll(map);
        return listWrapperResponse;
    }

    /**
     * @Method queryAll
     * @Desrciption 查询所有人员信息
     * @Param
     * [sysUserDTO]
     * @Author fuhui
     * @Date   2020/8/5 11:18
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.user.dto.SysUserDTO>>
     **/
    @PostMapping("/querySysUserAll")
    public WrapperResponse<List<SysUserDTO>> querySysUserAll(@RequestBody SysUserDTO sysUserDTO, HttpServletRequest req, HttpServletResponse res)
    {
        SysUserDTO sysUserDTOSession = getSession(req, res);
        sysUserDTO.setHospCode(sysUserDTOSession.getHospCode());
        //封装参数
        Map map = new HashMap();
        map.put("hospCode",sysUserDTOSession.getHospCode());
        map.put("sysUserDTO",sysUserDTO);
        return sysUserService_consumer.querySysUserAll(map);
    }
    /**
     * @Method queryPage
     * @Desrciption 分页查询人员信息
     * @Param
     * [sysUserDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 11:19
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(SysUserDTO sysUserDTO, HttpServletRequest req, HttpServletResponse res)
    {
        SysUserDTO sysUserDTOSession = getSession(req, res);
        sysUserDTO.setHospCode(sysUserDTOSession.getHospCode());
        //封装参数
        Map map = new HashMap();
        map.put("hospCode",sysUserDTOSession.getHospCode());
        map.put("sysUserDTO",sysUserDTO);
        return sysUserService_consumer.queryPage(map);
    }

    /**
     * @Method save
     * @Desrciption 修改、新增人员信息/账号信息
     * @Param
     * [sysUserDTO]
     * @Author liaojunjie
     * @Date   2020/8/5 11:19
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("save")
    public WrapperResponse<Boolean> save(@RequestBody SysUserDTO sysUserDTO, HttpServletRequest req, HttpServletResponse res)
    {
        SysUserDTO sysUserDTOSession = getSession(req, res);
        sysUserDTO.setHospCode(sysUserDTOSession.getHospCode());
        sysUserDTO.setCrteId(sysUserDTOSession.getId());
        sysUserDTO.setCrteName(sysUserDTOSession.getName());
        sysUserDTO.setCrteTime(DateUtils.getNow());
        //封装参数
        Map map = new HashMap();
        map.put("hospCode",sysUserDTOSession.getHospCode());
        map.put("sysUserDTO",sysUserDTO);
        return sysUserService_consumer.save(map);
    }

    /**
     * @Method updateSysUser
     * @Desrciption 修改用户系统关系
     * @Param
     * [sysUserDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 10:28
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping("updateSysUser")
    public WrapperResponse<Boolean> updateSysUser(@RequestBody SysUserDTO sysUserDTO, HttpServletRequest req, HttpServletResponse res)
    {
        SysUserDTO sysUserDTOSession = getSession(req, res);
        sysUserDTO.setHospCode(sysUserDTOSession.getHospCode());
        sysUserDTO.setCrteId(sysUserDTOSession.getId());
        sysUserDTO.setCrteName(sysUserDTOSession.getName());
        sysUserDTO.setCrteTime(DateUtils.getNow());
        //封装参数
        Map map = new HashMap();
        map.put("hospCode",sysUserDTOSession.getHospCode());
        map.put("sysUserDTO",sysUserDTO);
        return sysUserService_consumer.updateSysUser(map);
    }

    /**
     * @Method isCertNoExist
     * @Desrciption  暂时只做判断身份证是否重复
     * @Param
     * [sysUserDTO]
     * @Author liaojunjie
     * @Date   2020/8/27 12:57
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @GetMapping("/isCertNoExist")
    public WrapperResponse<Boolean> isCertNoExist(SysUserDTO sysUserDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTOSession = getSession(req, res);
        sysUserDTO.setHospCode(sysUserDTOSession.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTOSession.getHospCode());
        map.put("sysUserDTO",sysUserDTO);
        return sysUserService_consumer.isCertNoExist(map);
    }

    /**
     * @Method updateResetPassword
     * @Desrciption  重置密码
     * @Param
     * [sysUserDTO]
     * @Author liaojunjie
     * @Date   2020/10/28 14:42
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @GetMapping("/updateResetPassword")
    public WrapperResponse<Boolean> updateResetPassword(SysUserDTO sysUserDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTOSession = getSession(req, res);
        if(StringUtils.isEmpty(sysUserDTO.getId())){
            throw new AppException("id参数值为空");
        }else{
            sysUserDTO.setHospCode(sysUserDTOSession.getHospCode());
            //封装参数
            Map map = new HashMap();
            map.put("hospCode",sysUserDTOSession.getHospCode());
            map.put("sysUserDTO",sysUserDTO);
            return sysUserService_consumer.updateResetPassword(map);
        }
    }

    /**
     * @Method updateActivePassword
     * @Desrciption  密码激活
     * @Param
     * [sysUserDTO]
     * @Author liaojunjie
     * @Date   2020/10/28 14:41
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @GetMapping("/updateActivePassword")
    public WrapperResponse<Boolean> updateActivePassword(SysUserDTO sysUserDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTOSession = getSession(req, res);
        if(StringUtils.isEmpty(sysUserDTO.getId())){
            throw new AppException("id参数值为空");
        }else{
            sysUserDTO.setHospCode(sysUserDTOSession.getHospCode());
            //封装参数
            Map map = new HashMap();
            map.put("hospCode",sysUserDTOSession.getHospCode());
            map.put("sysUserDTO",sysUserDTO);
            return sysUserService_consumer.updateActivePassword(map);
        }
    }





    @GetMapping("/queryUserSysPage")
    public WrapperResponse<PageDTO> queryUserSysPage(SysUserSystemDTO sysUserSystemDTO, HttpServletRequest req, HttpServletResponse res)
    {
        SysUserDTO sysUserDTOSession = getSession(req, res);
        sysUserSystemDTO.setHospCode(sysUserDTOSession.getHospCode());
        //封装参数
        Map map = new HashMap();
        map.put("hospCode",sysUserDTOSession.getHospCode());
        map.put("sysUserSystemDTO",sysUserSystemDTO);
        return sysUserService_consumer.queryUserSysPage(map);
    }


    @GetMapping("queryUserRoleTree")
    public WrapperResponse<List<TreeMenuNode>> queryUserRoleTree(SysUserRoleDto sysUserRoleDto, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTOSession = getSession(req, res);
        sysUserRoleDto.setHospCode(sysUserDTOSession.getHospCode());
        Map map = new HashMap();
        map.put("hospCode",sysUserDTOSession.getHospCode());
        map.put("sysUserRoleDto",sysUserRoleDto);
        return sysUserService_consumer.queryUserRoleTree(map);
    }

    @PostMapping("saveUserRole")
    public WrapperResponse<Boolean> saveUserRole(@RequestBody SysUserRoleDto sysUserRoleDto, HttpServletRequest req, HttpServletResponse res)
    {
        SysUserDTO sysUserDTOSession = getSession(req, res);
        sysUserRoleDto.setHospCode(sysUserDTOSession.getHospCode());
        sysUserRoleDto.setCrteId(sysUserDTOSession.getId());
        sysUserRoleDto.setCrteName(sysUserDTOSession.getName());
        //封装参数
        Map map = new HashMap();
        map.put("hospCode",sysUserDTOSession.getHospCode());
        map.put("sysUserRoleDto",sysUserRoleDto);
        return sysUserService_consumer.saveUserRole(map);
    }

    /**
     * @Menthod queryUserByOperationDeptId
     * @Desrciption
     * @param  sysUserDTO List<String>职工类型 deptId 科室id
     * @Author xingyu.xie
     * @Date   2020/9/2 15:19
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.user.dto.SysUserDTO>>
     **/
    @PostMapping("/queryUserByOperationDeptId")
    public WrapperResponse<List<SysUserDTO>> queryUserByOperationDeptId(@RequestBody SysUserDTO sysUserDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTOSession = getSession(req, res);
        //ids作为临时的存workTypeCode的参数
        // LoginDeptId作为临时存deptId的参数
        List<String> workTypeCode = sysUserDTO.getIds();
        String deptId = sysUserDTO.getLoginDeptId();
        if (ListUtils.isEmpty(sysUserDTO.getIds())){
            throw new AppException("职工类型为空！");
        }
        if (StringUtils.isEmpty(sysUserDTO.getLoginDeptId())){
            throw new AppException("科室Id为空！");
        }
        Map map = new HashMap();
        map.put("workTypeCode",workTypeCode);
        map.put("deptId",deptId);
        map.put("hospCode",sysUserDTOSession.getHospCode());
        return sysUserService_consumer.queryUserByOperationDeptId(map);
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
    public WrapperResponse<Boolean> changePassWord(@RequestBody SysUserPasswordDTO sysUserPasswordDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTOSession = getSession(req, res);
        if (sysUserPasswordDTO == null  || StringUtils.isEmpty(sysUserPasswordDTO.getOldPassWord()) ||
                StringUtils.isEmpty(sysUserPasswordDTO.getNewPassword()) ) {
            return WrapperResponse.error(-1,"参数不能为空",null);
        }

        // 指定医院数据源查询用户信息
        Map paramMap = new HashMap<>();
        SysUserDTO sysUser = new SysUserDTO();
        sysUser.setHospCode(sysUserDTOSession.getHospCode());
        sysUser.setId(sysUserDTOSession.getId());
        paramMap.put("hospCode", sysUserDTOSession.getHospCode());
        paramMap.put("userCode", sysUserDTOSession.getName());
        paramMap.put("sysUserDTO",sysUser);

        // 获取用户信息
        SysUserDTO sysUserDTO = getData(sysUserService_consumer.getById(paramMap));

        // 校验用户信息
        if (sysUserDTO == null) {
            throw new AppException("员工账号不存在！");
        }

        // 是否停用
        if ("2".equals(sysUserDTO.getStatusCode())) {
            throw new AppException("当前账号已被停用！");
        }

        // 是否锁定
        if ("3".equals(sysUserDTO.getStatusCode())) {
            throw new AppException("当前账号已被锁定！");
        }

        // 密码错误
        if (!MD5Utils.verifyMd5AndSha(sysUserPasswordDTO.getOldPassWord(), sysUserDTO.getPassword())) {
            throw new AppException("原始密码错误！");
        }
        String newPasswordByMd5 = MD5Utils.getMd5AndSha(sysUserPasswordDTO.getNewPassword());
        Map changePassWordParam = new HashMap<>();
        changePassWordParam.put("oldPassWord",sysUserPasswordDTO.getOldPassWord());
        changePassWordParam.put("newPasswordByMd5",newPasswordByMd5);
        changePassWordParam.put("hospCode",sysUserDTOSession.getHospCode());
        changePassWordParam.put("id",sysUserDTOSession.getId());
        return sysUserService_consumer.changePassWord(changePassWordParam);
    }

    /**
     * @Method queryAll
     * @Param [sysUserDTO]
     * @description    根据职工类型模糊查询
     * @author marong
     * @date 2020/9/29 19:56
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.user.dto.SysUserDTO>>
     * @throws
     */
    @GetMapping("/queryAllByWorkTypeCode")
    public WrapperResponse<List<SysUserDTO>> queryAllByWorkTypeCode(SysUserDTO sysUserDTO, HttpServletRequest req, HttpServletResponse res)
    {
        SysUserDTO sysUserDTOSession = getSession(req, res);
        sysUserDTO.setHospCode(sysUserDTOSession.getHospCode());
        //封装参数
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("sysUserDTO",sysUserDTO);
        WrapperResponse<List<SysUserDTO>> listWrapperResponse = sysUserService_consumer.queryAllByWorkTypeCode(map);
        return listWrapperResponse;
    }

    @PostMapping("/upLoad")
    public WrapperResponse<Boolean> upLoad(@RequestParam MultipartFile file, HttpServletRequest req, HttpServletResponse res) {

        Map<String, Object> stringObjectMap = UploadByExcel.readExcel(file);
        Boolean flag = (Boolean) stringObjectMap.get("isSuccess");

        List<List<String>> resultList = (List<List<String>>) stringObjectMap.get("resultList");

        if(!flag){
            throw new AppException("解析错误，文件类型或格式不对");
        }
        if (ListUtils.isEmpty(resultList)){
            throw new AppException("解析错误，数据为空");
        }

        SysUserDTO sysUserDTOSession = getSession(req, res);
        Map map = new HashMap();
        map.put("hospCode",sysUserDTOSession.getHospCode());
        map.put("resultList",resultList);
        map.put("crteName",sysUserDTOSession.getName());
        map.put("crteId",sysUserDTOSession.getId());
        return this.sysUserService_consumer.upLoad(map);
    }
}
