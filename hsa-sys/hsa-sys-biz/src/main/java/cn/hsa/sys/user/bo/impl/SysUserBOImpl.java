package cn.hsa.sys.user.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.RSAUtil;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.URLEncoder;
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
     * ???????????????????????????
     */
    @Resource
    private SysUserDAO sysUserDAO;
    @Resource
    private SysRoleDao sysRoleDao;
    /**
     *  ??????????????????????????????
     */
    @Resource
    private SysParameterDAO sysParameterDao;
    @Resource
    private ApplicationEventPublisher publisher;

    /**
     * ??????????????????dubbo???????????????
     */
    @Resource
    private BaseOrderRuleService baseOrderRuleService;

    @Resource
    private BaseDeptService baseDeptService;

    @Resource
    private SysCodeService sysCodeService;

    /**
     *  ????????????????????????????????????????????????
     */
    private static final String whetherEnableReminderBoxParamName = "WHETHER_ENABLE_REMINDER_BOX";
    /**
     *  ?????????????????????????????????
     */
    private static final String insurePrivateInternetAddressParamName = "INSURE_PRIVATE_ADDRESS";


    private String rsaPublicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAImeWoemAQT+RVoYCV2Zwkp/Wd8tZJdLekP8fUMzXnAKIiveAjM9vIBFkozx6FflCTzUuRIWhytyq0Lf25+XGPECAwEAAQ==";


    /**
     * @Method getById
     * @Desrciption ??????id??????
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
                log.error("???????????????????????????????????????!??????????????????????????????{}", JSONArray.toJSONString(sysUserSystemDTOS));
                throw new AppException("???????????????????????????????????????");
            }
        }
        fillPrivateInternetAccessInfo(byId);
        return byId;
    }

    /**
     *  ????????????????????????????????????
     * @param byId
     */
    private void fillPrivateInternetAccessInfo(SysUserDTO byId) {
        String hospCode = byId.getHospCode();
        SysParameterDTO reminderParameterDTO = sysParameterDao.getParameterByCode(hospCode,whetherEnableReminderBoxParamName);
        //????????????
        if(reminderParameterDTO != null && Constants.SF.S.equals(reminderParameterDTO.getValue()) ){
            //???????????????
            if(Constants.SF.F.equals(byId.getWhetherPrivateInnerAddress())){
                // 3.?????????????????????????????????????????????
                SysParameterDTO insureAddressParameterDTO = sysParameterDao.getParameterByCode(hospCode,insurePrivateInternetAddressParamName);
                String insurePrivateInternetAddress = "http://XXX.XXX.XXX.XXX:xxxx/his-web";
                if(null != insureAddressParameterDTO){
                    insurePrivateInternetAddress = insureAddressParameterDTO.getValue();
                }
                String url = insurePrivateInternetAddress+"/?hospCode=";
                String rsaKey = "";
                try {
                    rsaKey = RSAUtil.encryptByPublicKey(hospCode.getBytes(), rsaPublicKey);
                    rsaKey = URLEncoder.encode(rsaKey,"UTF-8");
                } catch (Exception e) {
                    throw new AppException("????????????????????????,?????????"+e.getMessage());
                }
                url = url + rsaKey;
                byId.setInsurePrivateInternetAddress(url);
                byId.setWhetherEnableReminderBox(Constants.SF.S);
            }else{
                byId.setWhetherEnableReminderBox(Constants.SF.F);
            }
        }
        else{
            byId.setWhetherEnableReminderBox(Constants.SF.F);
        }
    }

    /**
     * @Method queryAll
     * @Desrciption ????????????
     * @Param [sysUserDTO]
     * @Author liaojunjie
     * @Date 2020/8/5 11:24
     * @Return java.util.List<cn.hsa.module.sys.user.dto.SysUserDTO>
     **/
    @Override
    public List<SysUserDTO> queryAll(SysUserDTO sysUserDTO) {
         if("1".equals(sysUserDTO.getFlag())) {
             List<SysUserDTO> sysUserDTOS = sysUserDAO.queryBedUserAll(sysUserDTO);
             for (SysUserDTO user:sysUserDTOS){
                 if (user!=null) {
                     String praCertNo = user.getPracCertiNo() == null ? "" : StringUtils.stringEncrypt(user.getPracCertiNo());
                     user.setPracCertiNo(praCertNo); // ????????????
                 }
             }
            return this.sysUserDAO.queryBedUserAll(sysUserDTO);
         }
         // 111????????????????????????????????????
         if ("111".equals(sysUserDTO.getFlag())) {
            return this.sysUserDAO.queryMZAndSFYAllUser(sysUserDTO);
         }
        return this.sysUserDAO.queryAll(sysUserDTO);
    }



    /**
     * @Method queryPage
     * @Desrciption ????????????
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
     * @Desrciption ??????/??????
     * @Param [sysUserDTO]
     * @Author liaojunjie
     * @Date 2020/8/5 11:24
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean save(SysUserDTO sysUserDTO) {
        //??????????????????????????????????????????????????????
        if (sysUserDAO.isCodeExist(sysUserDTO) > 0) {
            throw new AppException("??????????????????");
        }
        if (!StringUtils.isEmpty(sysUserDTO.getCertNo())) {
            int count = sysUserDAO.isCertNoExist(sysUserDTO);
            if (count > 0) {
                throw new AppException("???????????????????????????");
            }
        }
        //?????????????????????+?????????
        if (!StringUtils.isEmpty(sysUserDTO.getName())) {
            sysUserDTO.setPym(PinYinUtils.toFirstPY(sysUserDTO.getName()));
            sysUserDTO.setWbm(WuBiUtils.getWBCode(sysUserDTO.getName()));
        }

        //??????????????????DTO??????id????????? ????????????????????????
        if (StringUtils.isEmpty(sysUserDTO.getId())) {
            //??????id
            sysUserDTO.setId(SnowflakeUtils.getId());
            sysUserDTO.setPassword(MD5Utils.getMd5AndSha("888888"));
            //??????????????????????????????????????????????????? ?????????????????????
            this.sysUserDAO.insert(sysUserDTO);
            return true;
        } else {
            //????????????????????????
            this.sysUserDAO.update(sysUserDTO);
            return true;
        }
    }

    /**
     * @Method updateSysUser
     * @Desrciption ????????????????????????
     * @Param
     * [sysUserDTO]
     * @Author liaojunjie
     * @Date   2020/12/16 10:33
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean updateSysUser(SysUserDTO sysUserDTO){
        if (!ListUtils.isEmpty(sysUserDTO.getSysUserSystemDTOS())) {
            //?????????????????????????????????????????????
            Map<String, List<SysUserSystemDTO>> newSysUser = sysUserDTO.getSysUserSystemDTOS().stream().collect(Collectors.groupingBy(SysUserSystemDTO::getSystemCode));
            //????????????????????????????????????????????????????????????
            List<SysUserSystemDTO> sysUserSystemDTOS = sysUserDAO.querySysUserSystemAll(sysUserDTO);
            Map<String, List<SysUserSystemDTO>> oldSysUser = new HashMap<>();
            if(!ListUtils.isEmpty(sysUserSystemDTOS)){
                oldSysUser = sysUserSystemDTOS.stream().collect(Collectors.groupingBy(SysUserSystemDTO::getSystemCode));
            }
            //????????????List?????????????????????????????????????????????????????????????????????????????????
            List<SysUserSystemDTO>addList = new ArrayList<>();
            List<SysUserSystemDTO>deleteList = new ArrayList<>();
            List<SysUserSystemDTO>updateList = new ArrayList<>();

            for (String key : oldSysUser.keySet()) {
                if(newSysUser.containsKey(key)){
                    //??????????????????????????????????????????????????????
                    //??????????????????????????????????????? ???????????????????????????????????????
                    List<String> oldDeptCodes = oldSysUser.get(key).stream().map(SysUserSystemDTO::getDeptCode).collect(Collectors.toList());
                    List<String> newDeptCodes = newSysUser.get(key).stream().map(SysUserSystemDTO::getDeptCode).collect(Collectors.toList());

                    List<String> deleteDeptCodes = new ArrayList();
                    deleteDeptCodes.addAll(oldDeptCodes);
                    //???????????????????????????????????????????????????
                    oldDeptCodes.retainAll(newDeptCodes);

                    // newDeptCodes????????????????????????????????????
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

                    // deleteDeptCodes????????????????????????????????????
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
                    // oldDeptCodes ????????????????????????????????????
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
                    //??????????????????????????????????????????????????????????????????????????????????????????
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
                //??????id
                s.setId(SnowflakeUtils.getId());
                //??????????????????
                s.setHospCode(sysUserDTO.getHospCode());
                //?????????????????????????????????
                Map codeMap = new HashMap();
                codeMap.put("typeCode", "15");
                codeMap.put("hospCode", sysUserDTO.getHospCode());
                String code = baseOrderRuleService.getOrderNo(codeMap).getData();
                s.setUsCode(code);
                //?????????????????????
                s.setCrteId(sysUserDTO.getCrteId());
                s.setCrteName(sysUserDTO.getCrteName());
                s.setCrteTime(sysUserDTO.getCrteTime());
            }
            //????????????
            if(!ListUtils.isEmpty(deleteList)){
                //???????????????????????????
                List<String> usCodes = this.sysUserDAO.queryAllUsCode(deleteList);
                SysUserRoleDto sysUserRoleDto = new SysUserRoleDto();
                sysUserRoleDto.setUsCodes(usCodes);
                sysUserRoleDto.setHospCode(sysUserDTO.getHospCode());
                this.sysUserDAO.deleteSysUserRole(sysUserRoleDto);
                this.sysUserDAO.deleteSysUserSystemS(deleteList);
            }


            //????????????
            if(!ListUtils.isEmpty(addList)){
                this.sysUserDAO.insertSysUserSystem(addList);
            }
            //????????????
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
            //????????????????????????????????????
            this.sysUserDAO.deleteSysUserSystem(sysUserDTO);
        }
        return true;
    }

    /**
     * @Method isCertNoExist
     * @Desrciption ??????????????????????????????????????????
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
     * @Desrciption ????????????
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
     * @Desrciption ??????
     * @Param [sysUserDTO]
     * @Author liaojunjie
     * @Date 2020/10/28 14:50
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean updateActivePassword(SysUserDTO sysUserDTO) {
        SysUserDTO userDTO = sysUserDAO.getById(sysUserDTO);
        if (!"2".equals(userDTO.getStatusCode())) {
            throw new AppException("???????????????????????????????????????????????????????????????");
        }
        return sysUserDAO.updateActivePassword(sysUserDTO) > 0;
    }

    /**
     * @Method getByCode
     * @Description ???????????????????????????????????????????????????
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
     * @Desrciption ??????????????????deptCode??????????????????????????????????????????????????????????????????userCode???????????????????????????userCode?????????????????????
     * @Author xingyu.xie
     * @Date 2020/9/1 11:45
     * @Return java.util.List<cn.hsa.module.sys.user.dto.SysUserDTO>
     **/
    @Override
    public List<SysUserDTO> queryUserByOperationDeptId(Map map) {
        // ????????????id??????????????????
        BaseDeptDTO baseDeptDTO = new BaseDeptDTO();
        baseDeptDTO.setId(MapUtils.get(map, "deptId"));
        baseDeptDTO.setHospCode(MapUtils.get(map, "hospCode"));
        Map deptMap = new HashMap();
        deptMap.put("hospCode", baseDeptDTO.getHospCode());
        deptMap.put("baseDeptDTO", baseDeptDTO);
        WrapperResponse<List<BaseDeptDTO>> listWrapperResponse = baseDeptService.queryAll(deptMap);
        List<BaseDeptDTO> data = listWrapperResponse.getData();
        if (ListUtils.isEmpty(data)) {
            throw new AppException("?????????id????????????????????????????????????");
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
            throw new AppException("??????????????????");
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
        treeMenuNode.setLabel("????????????");
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
            throw new AppException("roleCodes????????????????????????");
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
     * @Description: ??????????????????
     * @Param: userCode ???????????? newPasswordByMd5 ??????????????????
     * @Author: marong
     * @Email: 564541256@qq.com
     * @Date: 2020/9/9 11???37
     * @Return: Boolean
     */
    @Override
    public boolean updatePassWord(Map map) {
        String id = map.get("id") == null ? "" : map.get("id").toString();
        String newPasswordByMd5 = map.get("newPasswordByMd5") == null ? "" : map.get("newPasswordByMd5").toString();
        String hospCode = map.get("hospCode") == null ? "" : map.get("hospCode").toString();
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(newPasswordByMd5) || StringUtils.isEmpty(hospCode)) {
            throw new AppException("?????????????????????");
        }

        // ??????????????????????????????????????????????????????
        publisher.publishEvent(new PasswordModifyEvent(this,map));
        map.put("lastUsedPassword",map.get("oldPassWord"));
        int affectRows = sysUserDAO.updatePassWord(map);
        return affectRows > 0;
    }

    /**
     * @Method: getTeachDoctor
     * @Description: ????????????ID????????????????????????
     * @Param: [hospCode, id]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/9/15 15:01
     * @Return: cn.hsa.module.sys.user.dto.SysUserDTO
     **/
    @Override
    public SysUserDTO getTeachDoctor(String hospCode, String id) {
        if (StringUtils.isEmpty(id)) {
            throw new AppException("??????ID??????");
        }
        return sysUserDAO.getTeachDoctor(hospCode, id);
    }

    /**
     * @return java.util.List<cn.hsa.module.sys.user.dto.SysUserDTO>
     * @throws
     * @Method queryAllByWorkTypeCode
     * @Param [sysUserDTO]
     * @description ??????????????????????????????
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

        // ????????????????????????
        HashMap sysCodeMap = new HashMap();
        sysCodeMap.put("hospCode",hospCode);
        sysCodeMap.put("code","XB,MZ,SF,YHZT,HYZK,ZJLB,XL,YPJB,DMTX,KJYPJB,RYZW,SSJB,RYZC,KSXZ");
        WrapperResponse<Map<String, List<SysCodeSelectDTO>>> byCode = sysCodeService.getByCode(sysCodeMap);

        Map<String, List<SysCodeSelectDTO>> data = byCode.getData();
        //??????
        List<SysCodeSelectDTO> XB = data.get("XB");
        //??????
        List<SysCodeSelectDTO> MZ = data.get("MZ");
        //??????
        List<SysCodeSelectDTO> SF = data.get("SF");
        //????????????
        List<SysCodeSelectDTO> YHZT = data.get("YHZT");

        List<SysCodeSelectDTO> ZJLB = data.get("ZJLB");
        //????????????
        List<SysCodeSelectDTO> HYZK = data.get("HYZK");
        //??????
        List<SysCodeSelectDTO> XL = data.get("XL");

        List<SysCodeSelectDTO> YPJB = data.get("YPJB");
        //??????
        List<SysCodeSelectDTO> DMTX = data.get("DMTX");
        //??????????????????
        List<SysCodeSelectDTO> KJYPJB = data.get("KJYPJB");
        //????????????
        List<SysCodeSelectDTO> RYZW = data.get("RYZW");
        //????????????
        List<SysCodeSelectDTO> SSJB = data.get("SSJB");
        // ??????
        List<SysCodeSelectDTO> RYZC = data.get("RYZC");
        // ????????????(????????????)
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
                throw new AppException("???????????????????????????????????????");
            }
            SysUserDTO sysUserDTO = new SysUserDTO();
            //ID
            sysUserDTO.setId(SnowflakeUtils.getId());
            // ????????????
            sysUserDTO.setHospCode(hospCode);
            // ??????????????????
            if(!ksxzMap.containsKey(item.get(0).trim())){
                throw new AppException("????????????????????? : '" +item.get(0)+"' ?????????,????????????");
            }
            sysUserDTO.setDeptCode(ksxzMap.get(item.get(0).trim()));
            // ??????????????????????????????
            sysUserDTO.setCode(item.get(1));
            // ????????????
            sysUserDTO.setPassword(MD5Utils.getMd5AndSha("888888"));
            // ??????
            sysUserDTO.setName(item.get(2));
            // ??????
            sysUserDTO.setAge(Integer.parseInt(item.get(3)));
            // ????????????
            if(StringUtils.isNotEmpty(item.get(4))){
                try {
                    sysUserDTO.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(item.get(4)));
                } catch (ParseException e) {
                    throw new AppException("????????????????????????");
                }
            }
            // ??????
            sysUserDTO.setGenderCode(xbMap.get(item.get(5).trim()));
            // ????????????
            sysUserDTO.setCertCode(zjlbMap.get(item.get(6).trim()));
            // ????????????
            sysUserDTO.setCertNo(item.get(7));
            // ????????????
            sysUserDTO.setWorkTypeCode(ryzwMap.get(item.get(8).trim()));
            //????????????
            sysUserDTO.setIsInJob(sfMap.get(item.get(9).trim()));
            // ????????????
            sysUserDTO.setStatusCode(yhztMap.get(item.get(10).trim()));
            // ??????
            sysUserDTO.setNationCode(mzMap.get(item.get(11).trim()));
            // ??????
            sysUserDTO.setNativePlace(item.get(12));
            // ????????????
            sysUserDTO.setMarryCode(hyzkMap.get(item.get(13).trim()));
            // ????????????
            sysUserDTO.setIsPm(sfMap.get(item.get(14).trim()));
            // ????????????
            if(StringUtils.isNotEmpty(item.get(15))){
                try {
                    sysUserDTO.setInPmDate(new SimpleDateFormat("yyyy-MM-dd").parse(item.get(15)));
                } catch (ParseException e) {
                    throw new AppException("????????????????????????");
                }
            }
            // ????????????
            sysUserDTO.setMajorCode(item.get(16));
            // ????????????
            sysUserDTO.setAddress(item.get(17));
            // ????????????
            sysUserDTO.setEmail(item.get(18));
            // ????????????
            sysUserDTO.setPhone(item.get(19));
            // ??????
            sysUserDTO.setEducationCode(xlMap.get(item.get(20).trim()));
            // ????????????
            sysUserDTO.setEducationCode(item.get(21));
            // ????????????
            if(StringUtils.isNotEmpty(item.get(22))){
                try {
                    sysUserDTO.setSchoolDate(new SimpleDateFormat("yyyy-MM-dd").parse(item.get(22)));
                } catch (ParseException e) {
                    throw new AppException("????????????????????????");
                }
            }
            // ??????????????????
            if(StringUtils.isNotEmpty(item.get(23))){
                try {
                    sysUserDTO.setWorkDate(new SimpleDateFormat("yyyy-MM-dd").parse(item.get(23)));
                } catch (ParseException e) {
                    throw new AppException("????????????????????????");
                }
            }
            // ??????
            sysUserDTO.setDutiesCode(ryzcMap.get(item.get(24).trim()));
            // ??????????????????
            sysUserDTO.setPracCertiNo(item.get(25));
            // ????????????
            sysUserDTO.setDurgCode(ypjbMap.get(item.get(26).trim()));
            // ????????????
            sysUserDTO.setPhCode(dmtxMap.get(item.get(27).trim()));
            // ????????????
            sysUserDTO.setAntibacterialCode(kjypjbMap.get(item.get(28).trim()));
            // ????????????
            sysUserDTO.setOpeartionCode(ssjbMap.get(item.get(29).trim()));

            if(StringUtils.isNotEmpty(sysUserDTO.getName())){
                sysUserDTO.setPym(PinYinUtils.toFirstPY(sysUserDTO.getName()));
                sysUserDTO.setWbm(WuBiUtils.getWBCode(sysUserDTO.getName()));
            }


            // ????????????
            sysUserDTO.setCrteTime(nowDate);
            sysUserDTO.setCrteName(userName);
            sysUserDTO.setCrteId(userId);

            insertList.add(sysUserDTO);
        }} catch (IndexOutOfBoundsException | NullPointerException  e){
            throw new AppException("EXCEL?????????????????????????????????");
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
            throw new AppException("????????????????????????!");
        }
        SysUserDTO sysUserDTO = sysUserDAO.querySysUserHaveTeachDoctor(sysUserSystemDTO);

        return sysUserDTO;
    }

    @Override
    public Boolean checkSysUserHaveTeachDoctor(Map map) {
        SysUserSystemDTO sysUserSystemDTO  = MapUtils.get(map,"sysUserSystemDTO");
        if(sysUserSystemDTO == null){
            throw new AppException("????????????????????????!");
        }
        if(StringUtils.isEmpty(sysUserSystemDTO.getPassword())){
            throw new AppException("??????????????????!");
        }
        sysUserSystemDTO.setPassword(MD5Utils.getMd5AndSha(sysUserSystemDTO.getPassword()));

       SysUserDTO sysUserDTO = sysUserDAO.querySysUserHaveTeachDoctor(sysUserSystemDTO);
        if(sysUserDTO == null){
            throw new AppException("????????????!");
        }
        return true;
    }
    /**
     * @Method queryVisitDoctorByWorkTypeCode
     * @Param [sysUserDTO]
     * @description    ????????????????????????????????? ????????????????????????
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
     * @Description: ???????????????????????????
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
        return WrapperResponse.error(HttpStatus.NOT_FOUND.value(), "?????????????????????????????????????????????",false);
    }
}
