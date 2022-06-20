package cn.hsa.sys.user.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.event.PasswordModifyEvent;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.base.dept.service.BaseDeptService;
import cn.hsa.module.sys.code.dto.SysCodeSelectDTO;
import cn.hsa.module.sys.code.service.SysCodeService;
import cn.hsa.module.sys.parameter.dao.SysParameterDAO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.role.dao.SysRoleDao;
import cn.hsa.module.sys.role.dto.SysRoleDto;
import cn.hsa.module.sys.role.entity.SysRoleDo;
import cn.hsa.module.sys.user.SysUserRoleDo;
import cn.hsa.module.sys.user.bo.SysUserBO;
import cn.hsa.module.sys.user.dao.SysUserDAO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.module.sys.user.dto.SysUserRoleDto;
import cn.hsa.module.sys.user.dto.SysUserSystemDTO;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.sys.user.bo.impl
 * @Class_name: SysUserBOImpl
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/7/31 15:25
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class SysUserBOImpl extends HsafBO implements SysUserBO {

    /**
     * 用户数据库访问接口
     */
    @Resource
    private SysUserDAO sysUserDAO;
    @Resource
    private SysRoleDao sysRoleDao;
    /**
     *  系统参数数据库访问层
     */
    @Resource
    private SysParameterDAO sysParameterDao;
    @Resource
    private ApplicationEventPublisher publisher;

    /**
     * 单据生成规则dubbo消费者接口
     */
    @Resource
    private BaseOrderRuleService baseOrderRuleService;

    @Resource
    private BaseDeptService baseDeptService;

    @Resource
    private SysCodeService sysCodeService;

    /**
     *  是否开启外网登录提醒框系统参数名
     */
    private static final String whetherEnableReminderBoxParamName = "WHETHER_ENABLE_REMINDER_BOX";
    /**
     *  医保专网访问地址参数名
     */
    private static final String insurePrivateInternetAddressParamName = "INSURE_PRIVATE_ADDRESS";




    /**
     * @Method getById
     * @Desrciption 通过id查询
     * @Param [sysUserDTO]
     * @Author liaojunjie
     * @Date 2020/8/5 11:25
     * @Return cn.hsa.module.sys.user.dto.SysUserDTO
     **/
    @Override
    public SysUserDTO getById(SysUserDTO sysUserDTO) {
        SysUserDTO byId = this.sysUserDAO.getById(sysUserDTO);
        byId.setWhetherPrivateInnerAddress(sysUserDTO.getWhetherPrivateInnerAddress());
        List<SysUserSystemDTO> sysUserSystemDTOS = sysUserDAO.querySysUserSystemAll(byId);
        if (!ListUtils.isEmpty(sysUserSystemDTOS)) {
            try {
                List<SysUserSystemDTO> newSysSystem = new ArrayList<>();
                Map<String, List<SysUserSystemDTO>> collect = sysUserSystemDTOS.stream().collect(Collectors.groupingBy(SysUserSystemDTO::getSystemCode));
                for (String key : collect.keySet()) {
                    SysUserSystemDTO s = new SysUserSystemDTO();
                    BeanUtils.copyProperties((SysUserSystemDTO)collect.get(key).get(0), s);
                    List<String> deptCodes = collect.get(key).stream().map(SysUserSystemDTO::getDeptCode).collect(Collectors.toList());
                    List<String> deptNames = collect.get(key).stream().map(SysUserSystemDTO::getDeptName).collect(Collectors.toList());
                    s.setDeptCodes(deptCodes);
                    s.setDeptNames(deptNames);
                    newSysSystem.add(s);
                    byId.setSysUserSystemDTOS(newSysSystem);
                }
            } catch (Exception e) {
                log.error("账号存在系统编码为空的数据!子系统具体数据如下：{}", JSONArray.toJSONString(sysUserSystemDTOS));
                throw new AppException("账号存在系统编码为空的数据");
            }
        }
        fillPrivateInternetAccessInfo(byId);
        return byId;
    }

    /**
     *  填充医保专网访问配置信息
     * @param byId
     */
    private void fillPrivateInternetAccessInfo(SysUserDTO byId) {
        // 1.属于内网访问则无需填充多余信息
        if(Constants.SF.S.equals(byId.getWhetherPrivateInnerAddress())){
            byId.setWhetherEnableReminderBox(Constants.SF.F);
            return;
        }
       String hospCode = byId.getHospCode();
        // 2.查询是否显示提示框参数
        SysParameterDTO reminderParameterDTO = sysParameterDao.getParameterByCode(hospCode,whetherEnableReminderBoxParamName);
        // 未配置则默认不开启提示框
        if(null == reminderParameterDTO){
            byId.setWhetherEnableReminderBox(Constants.SF.F);
            return;
        }
        byId.setWhetherEnableReminderBox(reminderParameterDTO.getValue());
        // 3.查询配置的系统参数医保专网地址
        SysParameterDTO insureAddressParameterDTO = sysParameterDao.getParameterByCode(hospCode,whetherEnableReminderBoxParamName);
        if(null == insureAddressParameterDTO){
            byId.setInsurePrivateInternetAddress("请配置医保专网访问ip地址系统参数：【INSURE_PRIVATE_ADDRESS】的值");
            return;
        }
        byId.setInsurePrivateInternetAddress(insureAddressParameterDTO.getValue());
    }

    /**
     * @Method queryAll
     * @Desrciption 查询所有
     * @Param [sysUserDTO]
     * @Author liaojunjie
     * @Date 2020/8/5 11:24
     * @Return java.util.List<cn.hsa.module.sys.user.dto.SysUserDTO>
     **/
    @Override
    public List<SysUserDTO> queryAll(SysUserDTO sysUserDTO) {
         if("1".equals(sysUserDTO.getFlag())) {
            return this.sysUserDAO.queryBedUserAll(sysUserDTO);
         }
         // 111表示取门诊医生与财务人员
         if ("111".equals(sysUserDTO.getFlag())) {
            return this.sysUserDAO.queryMZAndSFYAllUser(sysUserDTO);
         }
        return this.sysUserDAO.queryAll(sysUserDTO);
    }



    /**
     * @Method queryPage
     * @Desrciption 分页查询
     * @Param [sysUserDTO]
     * @Author liaojunjie
     * @Date 2020/8/5 11:24
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryPage(SysUserDTO sysUserDTO) {
        PageHelper.startPage(sysUserDTO.getPageNo(), sysUserDTO.getPageSize());
        List<SysUserDTO> sysUserDTOS = this.sysUserDAO.queryPage(sysUserDTO);
        if (!ListUtils.isEmpty(sysUserDTOS)) {
            for (int i = 0; i <sysUserDTOS.size() ; i++) {
                SysUserDTO byId = getById(sysUserDTOS.get(i));
                sysUserDTOS.set(i,byId);
            }
        }
        return PageDTO.of(sysUserDTOS);
    }

    @Override
    public PageDTO queryDeptUser(SysUserDTO sysUserDTO) {

        PageHelper.startPage(sysUserDTO.getPageNo(), sysUserDTO.getPageSize());
        List<SysUserDTO> sysUserDTOS = this.sysUserDAO.queryDeptUser(sysUserDTO);
        if (!ListUtils.isEmpty(sysUserDTOS)) {
            for (int i = 0; i <sysUserDTOS.size() ; i++) {
                SysUserDTO byId = getById(sysUserDTOS.get(i));
                sysUserDTOS.set(i,byId);
            }
        }
        return PageDTO.of(sysUserDTOS);
    }

    /**
     * @Method save
     * @Desrciption 新增/编辑
     * @Param [sysUserDTO]
     * @Author liaojunjie
     * @Date 2020/8/5 11:24
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean save(SysUserDTO sysUserDTO) {
        //首先对传入的员工工号进行判断是否存在
        if (sysUserDAO.isCodeExist(sysUserDTO) > 0) {
            throw new AppException("员工工号重复");
        }
        if (!StringUtils.isEmpty(sysUserDTO.getCertNo())) {
            int count = sysUserDAO.isCertNoExist(sysUserDTO);
            if (count > 0) {
                throw new AppException("该证件号码已经重复");
            }
        }
        //自动生成拼音码+五笔码
        if (!StringUtils.isEmpty(sysUserDTO.getName())) {
            sysUserDTO.setPym(PinYinUtils.toFirstPY(sysUserDTO.getName()));
            sysUserDTO.setWbm(WuBiUtils.getWBCode(sysUserDTO.getName()));
        }

        //如果传过来的DTO中的id是空的 代表进行新增操作
        if (StringUtils.isEmpty(sysUserDTO.getId())) {
            //设置id
            sysUserDTO.setId(SnowflakeUtils.getId());
            sysUserDTO.setPassword(MD5Utils.getMd5AndSha("888888"));
            //根据身份证传过来的数据进行参数解析 暂时只做身份证
            this.sysUserDAO.insert(sysUserDTO);
            return true;
        } else {
            //对明细单进行更新
            this.sysUserDAO.update(sysUserDTO);
            return true;
        }
    }

    /**
     * @Method updateSysUser
     * @Desrciption 修改用户系统关系
     * @Param
     * [sysUserDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 10:33
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean updateSysUser(SysUserDTO sysUserDTO){
        if (!ListUtils.isEmpty(sysUserDTO.getSysUserSystemDTOS())) {
            //将传过来的用户系统关系进行分组
            Map<String, List<SysUserSystemDTO>> newSysUser = sysUserDTO.getSysUserSystemDTOS().stream().collect(Collectors.groupingBy(SysUserSystemDTO::getSystemCode));
            //查询出该用户原有的用户系统关系并进行分组
            List<SysUserSystemDTO> sysUserSystemDTOS = sysUserDAO.querySysUserSystemAll(sysUserDTO);
            Map<String, List<SysUserSystemDTO>> oldSysUser = new HashMap<>();
            if(!ListUtils.isEmpty(sysUserSystemDTOS)){
                oldSysUser = sysUserSystemDTOS.stream().collect(Collectors.groupingBy(SysUserSystemDTO::getSystemCode));
            }
            //建立三个List用于存储需要新增的关系和需要删除的关系和需要修改的关系
            List<SysUserSystemDTO>addList = new ArrayList<>();
            List<SysUserSystemDTO>deleteList = new ArrayList<>();
            List<SysUserSystemDTO>updateList = new ArrayList<>();

            for (String key : oldSysUser.keySet()) {
                if(newSysUser.containsKey(key)){
                    //如果新传来的用户关系中包含这个子系统
                    //查出原有的该系统的科室编码 以及现在新传过来的科室编码
                    List<String> oldDeptCodes = oldSysUser.get(key).stream().map(SysUserSystemDTO::getDeptCode).collect(Collectors.toList());
                    List<String> newDeptCodes = newSysUser.get(key).stream().map(SysUserSystemDTO::getDeptCode).collect(Collectors.toList());

                    List<String> deleteDeptCodes = new ArrayList();
                    deleteDeptCodes.addAll(oldDeptCodes);
                    //查出新传来的和原来的共有的科室编码
                    oldDeptCodes.retainAll(newDeptCodes);

                    // newDeptCodes为需要新增的用户系统关系
                    newDeptCodes.removeAll(oldDeptCodes);
                    if(!ListUtils.isEmpty(newDeptCodes)){
                        for(String deptCode : newDeptCodes){
                            SysUserSystemDTO sysUserSystemDTO = new SysUserSystemDTO();
                            sysUserSystemDTO.setDeptCode(deptCode);
                            sysUserSystemDTO.setSystemCode(key);
                            sysUserSystemDTO.setUserCode(sysUserDTO.getCode());
                            sysUserSystemDTO.setTeacherCode(newSysUser.get(key).get(0).getTeacherCode());
                            addList.add(sysUserSystemDTO);
                        }
                    }

                    // deleteDeptCodes为需要删除的用户系统关系
                    deleteDeptCodes.removeAll(oldDeptCodes);
                    if(!ListUtils.isEmpty(deleteDeptCodes)){
                        for(String deptCode : deleteDeptCodes){
                            SysUserSystemDTO sysUserSystemDTO = new SysUserSystemDTO();
                            sysUserSystemDTO.setHospCode(sysUserDTO.getHospCode());
                            sysUserSystemDTO.setDeptCode(deptCode);
                            sysUserSystemDTO.setSystemCode(key);
                            sysUserSystemDTO.setUserCode(sysUserDTO.getCode());
                            deleteList.add(sysUserSystemDTO);
                        }
                    }
                    // oldDeptCodes 为需要修改的用户系统关系
                    if(!ListUtils.isEmpty(oldDeptCodes)){
                        for(String deptCode : oldDeptCodes){
                            SysUserSystemDTO sysUserSystemDTO = new SysUserSystemDTO();
                            sysUserSystemDTO.setHospCode(sysUserDTO.getHospCode());
                            sysUserSystemDTO.setDeptCode(deptCode);
                            sysUserSystemDTO.setSystemCode(key);
                            sysUserSystemDTO.setUserCode(sysUserDTO.getCode());
                            sysUserSystemDTO.setTeacherCode(newSysUser.get(key).get(0).getTeacherCode());
                            updateList.add(sysUserSystemDTO);
                        }
                    }
                }else{
                    //如果新传来的用户关系中不包含这个子系统，那么其中的都为删除的
                    if(!ListUtils.isEmpty(oldSysUser.get(key))){
                        for (SysUserSystemDTO s : oldSysUser.get(key)){
                            deleteList.add(s);
                        }
                    }
                }
            }

            for (String key : newSysUser.keySet()){
                if(!oldSysUser.containsKey(key)){
                    for (SysUserSystemDTO s : newSysUser.get(key)){
                        addList.add(s);
                    }
                }
            }

            for (SysUserSystemDTO s : addList) {
                //设置id
                s.setId(SnowflakeUtils.getId());
                //设置医院编码
                s.setHospCode(sysUserDTO.getHospCode());
                //设置用户子系统关系编码
                Map codeMap = new HashMap();
                codeMap.put("typeCode", "15");
                codeMap.put("hospCode", sysUserDTO.getHospCode());
                String code = baseOrderRuleService.getOrderNo(codeMap).getData();
                s.setUsCode(code);
                //设置创建人信息
                s.setCrteId(sysUserDTO.getCrteId());
                s.setCrteName(sysUserDTO.getCrteName());
                s.setCrteTime(sysUserDTO.getCrteTime());
            }
            //删除账号
            if(!ListUtils.isEmpty(deleteList)){
                //删除已经分配的权限
                List<String> usCodes = this.sysUserDAO.queryAllUsCode(deleteList);
                SysUserRoleDto sysUserRoleDto = new SysUserRoleDto();
                sysUserRoleDto.setUsCodes(usCodes);
                sysUserRoleDto.setHospCode(sysUserDTO.getHospCode());
                this.sysUserDAO.deleteSysUserRole(sysUserRoleDto);
                this.sysUserDAO.deleteSysUserSystemS(deleteList);
            }


            //新增账号
            if(!ListUtils.isEmpty(addList)){
                this.sysUserDAO.insertSysUserSystem(addList);
            }
            //修改账号
            if(!ListUtils.isEmpty(updateList)){
//                for (int i = updateList.size()-1; i >=0; i--) {
//                    if(StringUtils.isEmpty(updateList.get(i).getTeacherCode())){
//                        updateList.remove(i);
//                    }
//                }
//                if(!ListUtils.isEmpty(updateList)){
//                    this.sysUserDAO.updateSysUserSystem(updateList);
//                }
                this.sysUserDAO.updateSysUserSystem(updateList);
            }
        } else {
            //删除此用户已经建立的账号
            this.sysUserDAO.deleteSysUserSystem(sysUserDTO);
        }
        return true;
    }

    /**
     * @Method isCertNoExist
     * @Desrciption 暂时只能做身份证判断是否重复
     * @Param [sysUserDTO]
     * @Author liaojunjie
     * @Date 2020/9/2 19:25
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean isCertNoExist(SysUserDTO sysUserDTO) {
        return sysUserDAO.isCertNoExist(sysUserDTO) > 0 ? false : true;
    }

    /**
     * @Method updateResetPassword
     * @Desrciption 重置密码
     * @Param [sysUserDTO]
     * @Author liaojunjie
     * @Date 2020/10/28 14:50
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean updateResetPassword(SysUserDTO sysUserDTO) {
        sysUserDTO.setPassword(MD5Utils.getMd5AndSha("888888"));
        sysUserDTO.setIsPasswordChange(Constants.SF.F);
        return sysUserDAO.updateResetPassword(sysUserDTO) > 0;
    }

    /**
     * @Method updateActivePassword
     * @Desrciption 激活
     * @Param [sysUserDTO]
     * @Author liaojunjie
     * @Date 2020/10/28 14:50
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean updateActivePassword(SysUserDTO sysUserDTO) {
        SysUserDTO userDTO = sysUserDAO.getById(sysUserDTO);
        if (!"2".equals(userDTO.getStatusCode())) {
            throw new AppException("该用户没有处于冻结状态，不需要进行激活操作");
        }
        return sysUserDAO.updateActivePassword(sysUserDTO) > 0;
    }

    /**
     * @Method getByCode
     * @Description 根据医院编码、登录工号获取用户信息
     * @Param
     * @Author zhongming
     * @Date 2020/8/6 11:54
     * @Return
     **/
    @Override
    public SysUserDTO getByCode(Map map) {
        return sysUserDAO.getByCode(map);
    }

    /**
     * @param map
     * @Menthod queryUserByOperationDeptId
     * @Desrciption 通过科室编码deptCode查询用户系统表，找到可以操作此科室用户编码（userCode），再去用户表通过userCode找到相应的用户
     * @Author xingyu.xie
     * @Date 2020/9/1 11:45
     * @Return java.util.List<cn.hsa.module.sys.user.dto.SysUserDTO>
     **/
    @Override
    public List<SysUserDTO> queryUserByOperationDeptId(Map map) {
        // 通过科室id拿到科室编码
        BaseDeptDTO baseDeptDTO = new BaseDeptDTO();
        baseDeptDTO.setId(MapUtils.get(map, "deptId"));
        baseDeptDTO.setHospCode(MapUtils.get(map, "hospCode"));
        Map deptMap = new HashMap();
        deptMap.put("hospCode", baseDeptDTO.getHospCode());
        deptMap.put("baseDeptDTO", baseDeptDTO);
        WrapperResponse<List<BaseDeptDTO>> listWrapperResponse = baseDeptService.queryAll(deptMap);
        List<BaseDeptDTO> data = listWrapperResponse.getData();
        if (ListUtils.isEmpty(data)) {
            throw new AppException("该科室id不存在，请检查数据正确性");
        }
        String deptCode = data.get(0).getCode();
        map.put("deptCode", deptCode);

        return sysUserDAO.queryUserByOperationDeptId(map);
    }

    @Override
    public PageDTO queryUserSysPage(SysUserSystemDTO sysUserSystemDTO) {
        PageHelper.startPage(sysUserSystemDTO.getPageNo(), sysUserSystemDTO.getPageSize());
        List<SysUserSystemDTO> sysUserSystemDTOList = this.sysUserDAO.queryUserSysPage(sysUserSystemDTO);
        return PageDTO.of(sysUserSystemDTOList);
    }

    @Override
    public List<TreeMenuNode> queryUserRoleTree(SysUserRoleDto sysUserRoleDto) {
        SysRoleDto sysRoleDto = new SysRoleDto();
        sysRoleDto.setHospCode(sysUserRoleDto.getHospCode());
        List<SysRoleDo> roleList = sysRoleDao.queryRoles(sysRoleDto);
        List<SysUserRoleDo> userRoleList = sysUserDAO.queryCheckedUserRole(sysUserRoleDto);
        if (ListUtils.isEmpty(roleList)) {
            throw new AppException("请先配置角色");
        }
        String headNodeId = "-1";
        List<TreeMenuNode> treeMenuNodeList = new ArrayList<>();
        List<TreeMenuNode> treeMenuNodeListTem = new ArrayList<>();
        roleList.forEach(sysRoleDo -> {
            TreeMenuNode treeMenuNodeTem = new TreeMenuNode();
            treeMenuNodeTem.setId(sysRoleDo.getCode());
            treeMenuNodeTem.setLabel(sysRoleDo.getName());
            treeMenuNodeTem.setParentId(headNodeId);
            treeMenuNodeTem.setIsMenuOrBtn("1");
            if (!ListUtils.isEmpty(userRoleList)) {
                for (SysUserRoleDo sysUserRoleDo : userRoleList) {
                    if (sysUserRoleDo.getRoleCode().equals(sysRoleDo.getCode())) {
                        treeMenuNodeTem.setIsAble(true);
                        break;
                    }
                }
            }
            treeMenuNodeListTem.add(treeMenuNodeTem);
        });
        TreeMenuNode treeMenuNode = new TreeMenuNode();
        treeMenuNode.setId(headNodeId);
        treeMenuNode.setLabel("用户角色");
        treeMenuNode.setIsMenuOrBtn("1");
        treeMenuNode.setChildren(treeMenuNodeListTem);
        treeMenuNodeList.add(treeMenuNode);
        return treeMenuNodeList;
    }

    @Override
    public Boolean saveUserRole(SysUserRoleDto sysUserRoleDto) {
        List<SysUserRoleDto> sysUserRoleDtoList = new ArrayList<>();
        List<String> roleCodes = sysUserRoleDto.getRoleCodes();
        if (ListUtils.isEmpty(roleCodes)) {
            throw new AppException("roleCodes为空，请选择权限");
        }
        roleCodes.forEach(roleCode -> {
            SysUserRoleDto sysUserRoleDtoTem = new SysUserRoleDto();
            sysUserRoleDtoTem.setId(SnowflakeUtils.getId());
            sysUserRoleDtoTem.setHospCode(sysUserRoleDto.getHospCode());
            sysUserRoleDtoTem.setUsCode(sysUserRoleDto.getUsCode());
            sysUserRoleDtoTem.setRoleCode(roleCode);
            sysUserRoleDtoTem.setCrteId(sysUserRoleDto.getCrteId());
            sysUserRoleDtoTem.setCrteName(sysUserRoleDto.getCrteName());
            sysUserRoleDtoTem.setCrteTime(new Date());
            sysUserRoleDtoList.add(sysUserRoleDtoTem);
        });
        sysUserDAO.deleteUserRole(sysUserRoleDto);
        sysUserDAO.saveUserRole(sysUserRoleDtoList);
        return true;
    }

    /**
     * @Method: changePassWord()
     * @Description: 用户修改密码
     * @Param: userCode 用户账号 newPasswordByMd5 新设置的密码
     * @Author: marong
     * @Email: 564541256@qq.com
     * @Date: 2020/9/9 11：37
     * @Return: Boolean
     */
    @Override
    public boolean updatePassWord(Map map) {
        String id = map.get("id") == null ? "" : map.get("id").toString();
        String newPasswordByMd5 = map.get("newPasswordByMd5") == null ? "" : map.get("newPasswordByMd5").toString();
        String hospCode = map.get("hospCode") == null ? "" : map.get("hospCode").toString();
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(newPasswordByMd5) || StringUtils.isEmpty(hospCode)) {
            throw new AppException("数据不能为空！");
        }

        // 发布密码更新事件异步插入中心端数据库
        publisher.publishEvent(new PasswordModifyEvent(this,map));
        map.put("lastUsedPassword",map.get("oldPassWord"));
        int affectRows = sysUserDAO.updatePassWord(map);
        return affectRows > 0;
    }

    /**
     * @Method: getTeachDoctor
     * @Description: 根据用户ID获取代教医生信息
     * @Param: [hospCode, id]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/15 15:01
     * @Return: cn.hsa.module.sys.user.dto.SysUserDTO
     **/
    @Override
    public SysUserDTO getTeachDoctor(String hospCode, String id) {
        if (StringUtils.isEmpty(id)) {
            throw new AppException("用户ID为空");
        }
        return sysUserDAO.getTeachDoctor(hospCode, id);
    }

    /**
     * @return java.util.List<cn.hsa.module.sys.user.dto.SysUserDTO>
     * @throws
     * @Method queryAllByWorkTypeCode
     * @Param [sysUserDTO]
     * @description 根据职工类型模糊查询
     * @author marong
     * @date 2020/9/29 20:01
     */
    @Override
    public List<SysUserDTO> queryAllByWorkTypeCode(SysUserDTO sysUserDTO) {
        return sysUserDAO.queryAllByWorkTypeCode(sysUserDTO);
    }

    @Override
    public Boolean insertUpload(Map map) {
        String hospCode = (String) map.get("hospCode");
        String userName = (String) map.get("crteName");
        String userId = (String) map.get("crteId");
        Date nowDate = DateUtils.getNow();

        List<List<String>> resultList = (List<List<String>>) map.get("resultList");

        // 拿取系统码表列表
        HashMap sysCodeMap = new HashMap();
        sysCodeMap.put("hospCode",hospCode);
        sysCodeMap.put("code","XB,MZ,SF,YHZT,HYZK,ZJLB,XL,YPJB,DMTX,KJYPJB,RYZW,SSJB,RYZC,KSXZ");
        WrapperResponse<Map<String, List<SysCodeSelectDTO>>> byCode = sysCodeService.getByCode(sysCodeMap);

        Map<String, List<SysCodeSelectDTO>> data = byCode.getData();
        //性别
        List<SysCodeSelectDTO> XB = data.get("XB");
        //民族
        List<SysCodeSelectDTO> MZ = data.get("MZ");
        //是否
        List<SysCodeSelectDTO> SF = data.get("SF");
        //用户状态
        List<SysCodeSelectDTO> YHZT = data.get("YHZT");

        List<SysCodeSelectDTO> ZJLB = data.get("ZJLB");
        //婚姻状态
        List<SysCodeSelectDTO> HYZK = data.get("HYZK");
        //学历
        List<SysCodeSelectDTO> XL = data.get("XL");

        List<SysCodeSelectDTO> YPJB = data.get("YPJB");
        //毒麻
        List<SysCodeSelectDTO> DMTX = data.get("DMTX");
        //抗菌药品级别
        List<SysCodeSelectDTO> KJYPJB = data.get("KJYPJB");
        //人员职位
        List<SysCodeSelectDTO> RYZW = data.get("RYZW");
        //手术级别
        List<SysCodeSelectDTO> SSJB = data.get("SSJB");
        // 职称
        List<SysCodeSelectDTO> RYZC = data.get("RYZC");
        // 科室名称(科室性质)
        List<SysCodeSelectDTO> KSXZ = data.get("KSXZ");

        Map<String, String> xbMap = XB.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue));
        Map<String, String> mzMap = MZ.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue));
        Map<String, String> sfMap = SF.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue));
        Map<String, String> yhztMap = YHZT.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue));
        Map<String, String> hyzkMap = HYZK.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue));
        Map<String, String> zjlbMap = ZJLB.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue));
        Map<String, String> xlMap = XL.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue));
        Map<String, String> ypjbMap = YPJB.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue));
        Map<String, String> dmtxMap = DMTX.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue));
        Map<String, String> kjypjbMap = KJYPJB.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue));
        Map<String, String> ryzwMap = RYZW.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue));
        Map<String, String> ssjbMap = SSJB.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue));
        Map<String, String> ryzcMap = RYZC.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue));
        Map<String, String> ksxzMap = KSXZ.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue));


        List<SysUserDTO> insertList = new ArrayList<>();
        try {
        for (List<String> item : resultList){
            if (StringUtils.isEmpty(item.get(0)) || StringUtils.isEmpty(item.get(1)) || StringUtils.isEmpty(item.get(2))
                    || StringUtils.isEmpty(item.get(3)) || StringUtils.isEmpty(item.get(4)) || StringUtils.isEmpty(item.get(5))
                    || StringUtils.isEmpty(item.get(6)) || StringUtils.isEmpty(item.get(7) )|| StringUtils.isEmpty(item.get(8))
                    || StringUtils.isEmpty(item.get(9)) || StringUtils.isEmpty(item.get(10)) ){
                throw new AppException("存在必填字段为空，请检查！");
            }
            SysUserDTO sysUserDTO = new SysUserDTO();
            //ID
            sysUserDTO.setId(SnowflakeUtils.getId());
            // 医院编码
            sysUserDTO.setHospCode(hospCode);
            // 所属科室编码
            if(!ksxzMap.containsKey(item.get(0).trim())){
                throw new AppException("未找到对应科室 : '" +item.get(0)+"' 的信息,请检查！");
            }
            sysUserDTO.setDeptCode(ksxzMap.get(item.get(0).trim()));
            // 员工工号（登录账户）
            sysUserDTO.setCode(item.get(1));
            // 员工密码
            sysUserDTO.setPassword(MD5Utils.getMd5AndSha("888888"));
            // 姓名
            sysUserDTO.setName(item.get(2));
            // 年龄
            sysUserDTO.setAge(Integer.parseInt(item.get(3)));
            // 出生日期
            if(StringUtils.isNotEmpty(item.get(4))){
                try {
                    sysUserDTO.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(item.get(4)));
                } catch (ParseException e) {
                    throw new AppException("出生日期格式错误");
                }
            }
            // 性别
            sysUserDTO.setGenderCode(xbMap.get(item.get(5).trim()));
            // 证件类型
            sysUserDTO.setCertCode(zjlbMap.get(item.get(6).trim()));
            // 证件号码
            sysUserDTO.setCertNo(item.get(7));
            // 职工类型
            sysUserDTO.setWorkTypeCode(ryzwMap.get(item.get(8).trim()));
            //是否在职
            sysUserDTO.setIsInJob(sfMap.get(item.get(9).trim()));
            // 用户状态
            sysUserDTO.setStatusCode(yhztMap.get(item.get(10).trim()));
            // 民族
            sysUserDTO.setNationCode(mzMap.get(item.get(11).trim()));
            // 籍贯
            sysUserDTO.setNativePlace(item.get(12));
            // 婚姻状况
            sysUserDTO.setMarryCode(hyzkMap.get(item.get(13).trim()));
            // 是否党员
            sysUserDTO.setIsPm(sfMap.get(item.get(14).trim()));
            // 入党时间
            if(StringUtils.isNotEmpty(item.get(15))){
                try {
                    sysUserDTO.setInPmDate(new SimpleDateFormat("yyyy-MM-dd").parse(item.get(15)));
                } catch (ParseException e) {
                    throw new AppException("入党时间格式错误");
                }
            }
            // 所学专业
            sysUserDTO.setMajorCode(item.get(16));
            // 家庭地址
            sysUserDTO.setAddress(item.get(17));
            // 电子邮件
            sysUserDTO.setEmail(item.get(18));
            // 联系电话
            sysUserDTO.setPhone(item.get(19));
            // 学历
            sysUserDTO.setEducationCode(xlMap.get(item.get(20).trim()));
            // 毕业学校
            sysUserDTO.setEducationCode(item.get(21));
            // 毕业时间
            if(StringUtils.isNotEmpty(item.get(22))){
                try {
                    sysUserDTO.setSchoolDate(new SimpleDateFormat("yyyy-MM-dd").parse(item.get(22)));
                } catch (ParseException e) {
                    throw new AppException("毕业时间设置错误");
                }
            }
            // 参加工作时间
            if(StringUtils.isNotEmpty(item.get(23))){
                try {
                    sysUserDTO.setWorkDate(new SimpleDateFormat("yyyy-MM-dd").parse(item.get(23)));
                } catch (ParseException e) {
                    throw new AppException("参加工作时间错误");
                }
            }
            // 职称
            sysUserDTO.setDutiesCode(ryzcMap.get(item.get(24).trim()));
            // 执业证书编号
            sysUserDTO.setPracCertiNo(item.get(25));
            // 药品级别
            sysUserDTO.setDurgCode(ypjbMap.get(item.get(26).trim()));
            // 毒麻药品
            sysUserDTO.setPhCode(dmtxMap.get(item.get(27).trim()));
            // 抗菌药品
            sysUserDTO.setAntibacterialCode(kjypjbMap.get(item.get(28).trim()));
            // 手术级别
            sysUserDTO.setOpeartionCode(ssjbMap.get(item.get(29).trim()));

            if(StringUtils.isNotEmpty(sysUserDTO.getName())){
                sysUserDTO.setPym(PinYinUtils.toFirstPY(sysUserDTO.getName()));
                sysUserDTO.setWbm(WuBiUtils.getWBCode(sysUserDTO.getName()));
            }


            // 创建信息
            sysUserDTO.setCrteTime(nowDate);
            sysUserDTO.setCrteName(userName);
            sysUserDTO.setCrteId(userId);

            insertList.add(sysUserDTO);
        }} catch (IndexOutOfBoundsException | NullPointerException  e){
            throw new AppException("EXCEL数据格式错误，导入失败");
        }
        if(!ListUtils.isEmpty(insertList)){
            this.sysUserDAO.insertBatch(insertList);
        }
        return true;
    }

    @Override
    public SysUserDTO querySysUserHaveTeachDoctor(Map map) {
        SysUserSystemDTO sysUserSystemDTO  = MapUtils.get(map,"sysUserSystemDTO");
        if(sysUserSystemDTO == null){
            throw new AppException("未获取到医生信息!");
        }
        SysUserDTO sysUserDTO = sysUserDAO.querySysUserHaveTeachDoctor(sysUserSystemDTO);

        return sysUserDTO;
    }

    @Override
    public Boolean checkSysUserHaveTeachDoctor(Map map) {
        SysUserSystemDTO sysUserSystemDTO  = MapUtils.get(map,"sysUserSystemDTO");
        if(sysUserSystemDTO == null){
            throw new AppException("未获取到医生信息!");
        }
        if(StringUtils.isEmpty(sysUserSystemDTO.getPassword())){
            throw new AppException("密码不能为空!");
        }
        sysUserSystemDTO.setPassword(MD5Utils.getMd5AndSha(sysUserSystemDTO.getPassword()));

       SysUserDTO sysUserDTO = sysUserDAO.querySysUserHaveTeachDoctor(sysUserSystemDTO);
        if(sysUserDTO == null){
            throw new AppException("验证失败!");
        }
        return true;
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
    @Override
    public List<SysUserDTO> queryVisitDoctorByWorkTypeCode(SysUserDTO sysUserDTO) {
        return sysUserDAO.queryVisitDoctorByWorkTypeCode(sysUserDTO);
    }

    /**
     * @Description: 更新已读公告的标识
     * @Param: [sysUserDTO, req, res]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangxuan
     * @Date: 2022-02-17
     */
    @Override
    public Boolean updateIsGuide(SysUserDTO sysUserDTO) {
        return sysUserDAO.updateIsGuide(sysUserDTO);
    }

    @Override
    public WrapperResponse<Boolean> updatePassWordUnified(Map changePassWordParam) {
        int affectedRows = sysUserDAO.updatePassWordUnified(changePassWordParam);
        if(affectedRows > 0)
            return WrapperResponse.success(true);
        return WrapperResponse.error(HttpStatus.NOT_FOUND.value(), "用户不存在，请检查输入是否有误",false);
    }
}
