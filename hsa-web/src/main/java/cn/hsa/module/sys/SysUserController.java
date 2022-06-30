package cn.hsa.module.sys;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.module.sys.user.dto.SysUserPasswordDTO;
import cn.hsa.module.sys.user.dto.SysUserRoleDto;
import cn.hsa.module.sys.user.dto.SysUserSystemDTO;
import cn.hsa.module.sys.user.service.SysUserService;
import cn.hsa.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    /** default weak password **/
    private static final String DEFAULT_PASSWORD = MD5Utils.getMd5AndSha("888888");

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
        String whetherPrivateInnerAddress = req.getHeader("Internet-Flag");
        logger.error("==-==request.getHeader('Internet-Flag')= {}(String)",whetherPrivateInnerAddress);
        logger.error("==-==request.getIp= {}",ServletUtils.getRequestIp());
        logger.error("==-==request.getHeader('Host')= {}",req.getHeader("Host"));
        sysUserDTO.setWhetherPrivateInnerAddress(whetherPrivateInnerAddress);
        sysUserDTO.setHospCode(sysUserDTOSession.getHospCode());
        //封装参数
        Map map = new HashMap();
        map.put("hospCode",sysUserDTOSession.getHospCode());
        map.put("sysUserDTO",sysUserDTO);
        sysUserDTO = getData(sysUserService_consumer.getById(map));
        if(DEFAULT_PASSWORD.equals(sysUserDTO.getPassword())) {
            sysUserDTO.setIsPasswordChange("-2");
        }
        sysUserDTO.setPassword(null);
        sysUserDTO.setLastUsedPassword(null);
        return WrapperResponse.success(sysUserDTO);
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
        sysUserDTO.setLoginDeptCode(sysUserDTOSession.getLoginBaseDeptDTO().getCode());
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
    @PostMapping("/queryDeptUser")
    public WrapperResponse<PageDTO> queryDeptUser(@RequestBody SysUserDTO sysUserDTO, HttpServletRequest req, HttpServletResponse res)
    {
        SysUserDTO sysUserDTOSession = getSession(req, res);
        sysUserDTO.setHospCode(sysUserDTOSession.getHospCode());
        //封装参数
        Map map = new HashMap();
        map.put("hospCode",sysUserDTOSession.getHospCode());
        map.put("sysUserDTO",sysUserDTO);
        return sysUserService_consumer.queryDeptUser(map);
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
            return WrapperResponse.error(HttpStatus.NO_CONTENT.value(), "id参数值为空",false);
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
            return WrapperResponse.error(HttpStatus.NO_CONTENT.value(), "职工类型为空！",null);
        }
        if (StringUtils.isEmpty(sysUserDTO.getLoginDeptId())){
            return WrapperResponse.error(HttpStatus.NO_CONTENT.value(), "科室Id为空！",null);
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
            return WrapperResponse.error(-1,"参数不能为空",false);
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
            return WrapperResponse.error(HttpStatus.NOT_FOUND.value(), "员工账号不存在！",false);
        }
        // 是否停用
        if ("2".equals(sysUserDTO.getStatusCode())) {
            return WrapperResponse.error(HttpStatus.LOCKED.value(), "当前账号已被停用！",false);
        }

        // 是否锁定
        if ("3".equals(sysUserDTO.getStatusCode())) {
            return WrapperResponse.error(HttpStatus.LOCKED.value(), "当前账号已被锁定！",false);
        }

        // 密码错误
        if (!MD5Utils.verifySha(sysUserPasswordDTO.getOldPassWord(), sysUserDTO.getPassword())) {
            return WrapperResponse.error(HttpStatus.EXPECTATION_FAILED.value(), "原始密码错误！",false);
        }
        String newPasswordByMd5 = sysUserPasswordDTO.getNewPassword();
        // 新旧密码不能相同
        if(newPasswordByMd5.equals(sysUserDTO.getLastUsedPassword())){
            return WrapperResponse.error(HttpStatus.EXPECTATION_FAILED.value(), "新密码不能与近期使用过的密码相同！",false);
        }

        Map changePassWordParam = new HashMap<>();
        changePassWordParam.put("oldPassWord",sysUserDTO.getPassword());
        changePassWordParam.put("newPasswordByMd5",newPasswordByMd5);
        changePassWordParam.put("hospCode",sysUserDTOSession.getHospCode());
        changePassWordParam.put("id",sysUserDTOSession.getId());
        BaseDeptDTO loginBaseDeptDTO = sysUserDTOSession.getLoginBaseDeptDTO();
        changePassWordParam.put("deptId",loginBaseDeptDTO == null?"" : loginBaseDeptDTO.getId());
        changePassWordParam.put("deptName",loginBaseDeptDTO == null? "" : loginBaseDeptDTO.getName());
        changePassWordParam.put("userName",sysUserDTOSession.getName());
        changePassWordParam.put("userCode",sysUserDTOSession.getCode());
        changePassWordParam.put("ip",ServletUtils.getRequestIp());

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

    /**
     * @Method queryVisitDoctorByWorkTypeCode
     * @Param [sysUserDTO]
     * @description    根据用户状态、科室类型 职工类型模糊查询
     * @author zhangguorui
     * @date 2021/8/23
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.sys.user.dto.SysUserDTO>>
     * @throws
     */
    @GetMapping("/queryVisitDoctorByWorkTypeCode")
    public WrapperResponse<List<SysUserDTO>> queryVisitDoctorByWorkTypeCode(SysUserDTO sysUserDTO, HttpServletRequest req, HttpServletResponse res)
    {
        SysUserDTO sysUserDTOSession = getSession(req, res);
        sysUserDTO.setHospCode(sysUserDTOSession.getHospCode());
        //封装参数
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("sysUserDTO",sysUserDTO);
        WrapperResponse<List<SysUserDTO>> listWrapperResponse = sysUserService_consumer.queryVisitDoctorByWorkTypeCode(map);
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

    /**
     * @Method querySysUserHaveTeachDoctor
     * @Param [sysUserDTO]
     * @description    判断当前医生是否有带教医生
     * @author pengbo
     * @date 2021/8/08 19:56
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     * @throws
     */
    @GetMapping("/querySysUserHaveTeachDoctor")
    public WrapperResponse<SysUserDTO> querySysUserHaveTeachDoctor(SysUserDTO sysUserDTO, HttpServletRequest req, HttpServletResponse res)
    {
        SysUserDTO sysUserDTOSession = getSession(req, res);
        sysUserDTO.setHospCode(sysUserDTOSession.getHospCode());
        SysUserSystemDTO sysUserSystemDTO = new SysUserSystemDTO();
        sysUserSystemDTO.setUserCode(sysUserDTOSession.getCode());
        sysUserSystemDTO.setHospCode(sysUserDTOSession.getHospCode());
        sysUserSystemDTO.setDeptCode(sysUserDTOSession.getLoginBaseDeptDTO().getCode());
        sysUserSystemDTO.setSystemCode(sysUserDTOSession.getSystemCode());

        //封装参数
        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("sysUserSystemDTO",sysUserSystemDTO);
        WrapperResponse<SysUserDTO> wrapperResponse = sysUserService_consumer.querySysUserHaveTeachDoctor(map);
        return wrapperResponse;
    }



    /**
     * @Method querySysUserHaveTeachDoctor
     * @Param [sysUserDTO]
     * @description    校验带教医生
     * @author pengbo
     * @date 2021/8/08 19:56
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
     * @throws
     */
    @PostMapping("/checkSysUserHaveTeachDoctor")
    public WrapperResponse<Boolean> checkSysUserHaveTeachDoctor(@RequestBody  SysUserSystemDTO sysUserSystemDTO, HttpServletRequest req, HttpServletResponse res)
    {
        SysUserDTO sysUserDTOSession = getSession(req, res);
        sysUserSystemDTO.setUserCode(sysUserDTOSession.getCode());
        sysUserSystemDTO.setHospCode(sysUserDTOSession.getHospCode());
        sysUserSystemDTO.setDeptCode(sysUserDTOSession.getLoginBaseDeptDTO().getCode());
        sysUserSystemDTO.setSystemCode(sysUserDTOSession.getSystemCode());
        //封装参数
        Map map = new HashMap();
        map.put("hospCode",sysUserSystemDTO.getHospCode());
        map.put("sysUserSystemDTO",sysUserSystemDTO);
        WrapperResponse<Boolean> wrapperResponse = sysUserService_consumer.checkSysUserHaveTeachDoctor(map);
        return wrapperResponse;
    }

    /**
     * @Description: 更新已读公告的标识
     * @Param: [sysUserDTO, req, res]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangxuan
     * @Date: 2022-02-17
     */
    @PostMapping( value = "/updateIsGuide")
    public WrapperResponse<Boolean> updateIsGuide(@RequestBody  SysUserDTO sysUserDTO, HttpServletRequest req, HttpServletResponse res){

        SysUserDTO sysUserDTOSession = getSession(req, res);
        sysUserDTO.setCode(sysUserDTOSession.getCode());
        sysUserDTO.setHospCode(sysUserDTOSession.getHospCode());

        Map map = new HashMap();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("sysUserDTO",sysUserDTO);
        return sysUserService_consumer.updateIsGuide(map);

    }

}
