package cn.hsa.module.inpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.NoRepeatSubmit;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.bedlist.dto.InptLongCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.nurse.service.AddAccountByInptService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.module.inpt
 *@Class_name: AddAccountByInptController
 *@Describe: 补记账管理
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/9/3 20:45
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/inpt/addAccount")
@Slf4j
public class AddAccountByInptController extends BaseController {

    @Resource
    AddAccountByInptService addAccountByInptService_consumer;


    /**
    * @Method queryPatientInfoPage
    * @Desrciption 住院患者信息分页查询
    * @param inptVisitDTO
    * @Author liuqi1
    * @Date   2020/9/4 10:49
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @RequestMapping(value = "/queryPatientInfoPage")
    public WrapperResponse<PageDTO> queryPatientInfoPage(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        if(!"all".equals(inptVisitDTO.getQueryType())){
            //查询类型不是all时，查询当前操作科室的数据
            inptVisitDTO.setInDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }

        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("inptVisitDTO", inptVisitDTO);

        WrapperResponse<PageDTO> pageDTOWrapperResponse = addAccountByInptService_consumer.queryPatientInfoPage(map);

        return pageDTOWrapperResponse;
    }


    /**
     * @Method queryPatientInfoPage
     * @Desrciption 住院患者信息分页查询
     * @param inptVisitDTO
     * @Author xingyu.xie
     * @Date   2020/9/4 10:49
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @RequestMapping(value = "/queryPatientInfoPageByMoney")
    public WrapperResponse<PageDTO> queryPatientInfoPageByMoney(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("inptVisitDTO", inptVisitDTO);

        WrapperResponse<PageDTO> pageDTOWrapperResponse = addAccountByInptService_consumer.queryPatientInfoPageByMoney(map);

        return pageDTOWrapperResponse;
    }



    /**
    * @Method saveAddAccountByInpt
    * @Desrciption 补记账管理保存
    * @param inptCostDTOs
    * @Author liuqi1
    * @Date   2020/9/3 20:49
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @PostMapping(value = "/saveAddAccountByInpt")
    @NoRepeatSubmit
    WrapperResponse<Boolean> saveAddAccountByInpt(@RequestBody List<InptCostDTO> inptCostDTOs, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("inptCostDTOs", inptCostDTOs);
        map.put("userId",sysUserDTO.getId());
        map.put("userName",sysUserDTO.getName());
        map.put("loginDeptId",sysUserDTO.getLoginBaseDeptDTO().getId());
        map.put("loginDeptName",sysUserDTO.getLoginBaseDeptDTO().getName());

        WrapperResponse<Boolean> booleanWrapperResponse = addAccountByInptService_consumer.saveAddAccountByInpt(map);

        return booleanWrapperResponse;
    }

    /**
       * 手术补记账接口
       * @Author: luonianxin
       * @Email: nianxin.luo@powersi.com
       * @Date: 2021/5/31 11:27
    **/
    @PostMapping(value = "/saveAddSurgicalAccounting")
    @NoRepeatSubmit
    WrapperResponse<Boolean> saveAddSurgicalAccounting(@RequestBody List<InptCostDTO> inptCostDTOs, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        for (int i = 0; i < inptCostDTOs.size(); i++) {
             inptCostDTOs.get(i).setHospCode(sysUserDTO.getHospCode());
       }
        map.put("inptCostDTOs", inptCostDTOs);
        map.put("userId",sysUserDTO.getId());
        map.put("userName",sysUserDTO.getName());
        map.put("loginDeptId",sysUserDTO.getLoginBaseDeptDTO().getId());
        map.put("loginDeptName",sysUserDTO.getLoginBaseDeptDTO().getName());

        WrapperResponse<Boolean> booleanWrapperResponse = addAccountByInptService_consumer.saveAddSurgicalAccounting(map);

        return booleanWrapperResponse;
    }

    /**
    * @Method queryBackCostWithInptPage
    * @Desrciption 补记账退费分页查询
    * @param inptCostDTO
    * @Author liuqi1
    * @Date   2020/9/4 14:17
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @PostMapping(value = "/queryBackCostWithAddAccountPage")
    WrapperResponse<PageDTO> queryBackCostWithAddAccountPage(@RequestBody InptCostDTO inptCostDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("inptCostDTO", inptCostDTO);

        WrapperResponse<PageDTO> pageDTOWrapperResponse = addAccountByInptService_consumer.queryBackCostWithAddAccountPage(map);
        return pageDTOWrapperResponse;
    }

    /**
     * @Method queryLongCostByVistIdAndSoureTypeCode
     * @Desrciption 补记账长期费用查询
     * 根据visitId and soureTypeCode 查询这个病人录入的长期费用补记账
     * @param inptCostDTO
     * @Author pengbo
     * @Date   2021/5/24 20:49
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @GetMapping(value = "/queryLongCostByVistIdAndSoureTypeCode")
    WrapperResponse<PageDTO> queryLongCostByVistIdAndSoureTypeCode(InptCostDTO inptCostDTO, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("inptCostDTO", inptCostDTO);
        map.put("userId",sysUserDTO.getId());
        map.put("userName",sysUserDTO.getName());
        map.put("loginDeptId",sysUserDTO.getLoginBaseDeptDTO().getId());
        map.put("loginDeptName",sysUserDTO.getLoginBaseDeptDTO().getName());

        WrapperResponse<PageDTO> wrapperResponse = addAccountByInptService_consumer.queryLongCostByVistIdAndSoureTypeCode(map);
        return wrapperResponse;
    }


    /**
     * @Method saveAddAccountByInpt
     * @Desrciption 补记账长期费用管理保存
     * @param longCostDtoList
     * @Author pengbo
     * @Date   2021/5/24 20:49
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping(value = "/saveAccountRepairLong")
    @NoRepeatSubmit
    WrapperResponse<Boolean> saveAccountRepairLong(@RequestBody List<InptLongCostDTO> longCostDtoList, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("longCostDtoList", longCostDtoList);
        map.put("userId",sysUserDTO.getId());
        map.put("userName",sysUserDTO.getName());
        map.put("loginDeptId",sysUserDTO.getLoginBaseDeptDTO().getId());
        map.put("loginDeptName",sysUserDTO.getLoginBaseDeptDTO().getName());

        WrapperResponse<Boolean> booleanWrapperResponse = addAccountByInptService_consumer.saveAccountRepairLong(map);
        return booleanWrapperResponse;
    }


    /**
     * @Method canleAccountRepairLong
     * @Desrciption 补记账长期费用取消
     * @param longCostDto
     * @Author pengbo
     * @Date   2021/5/24 20:49
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @PostMapping(value = "/canleAccountRepairLong")
    @NoRepeatSubmit
    WrapperResponse<Boolean> canleAccountRepairLong(@RequestBody InptLongCostDTO longCostDto, HttpServletRequest req, HttpServletResponse res){
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("longCostDto", longCostDto);
        map.put("userId",sysUserDTO.getId());
        map.put("userName",sysUserDTO.getName());
        map.put("loginDeptId",sysUserDTO.getLoginBaseDeptDTO().getId());
        map.put("loginDeptName",sysUserDTO.getLoginBaseDeptDTO().getName());

        WrapperResponse<Boolean> booleanWrapperResponse = addAccountByInptService_consumer.updateAccountRepairLong(map);
        return booleanWrapperResponse;
    }

    /**
     * @Method queryPatientBabyInfoPage
     * @Desrciption 住院患者信息分页查询
     * @param inptVisitDTO
     * @Author liuqi1
     * @Date   2020/9/4 10:49
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @RequestMapping(value = "/queryPatientBabyInfoPage")
    public WrapperResponse<PageDTO> queryPatientBabyInfoPage(InptVisitDTO inptVisitDTO, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        inptVisitDTO.setHospCode(sysUserDTO.getHospCode());
        if(!"all".equals(inptVisitDTO.getQueryType())){
            //查询类型不是all时，查询当前操作科室的数据
            inptVisitDTO.setInDeptId(sysUserDTO.getLoginBaseDeptDTO().getId());
        }

        Map<String,Object> map = new HashMap<>();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("inptVisitDTO", inptVisitDTO);

        WrapperResponse<PageDTO> pageDTOWrapperResponse = addAccountByInptService_consumer.queryBabyPatientInfoPage(map);

        return pageDTOWrapperResponse;
    }

}
