package cn.hsa.center.authorization.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.base.RSAUtil;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.admdvs.service.CenterInsureAdmdvsService;
import cn.hsa.module.center.authorization.dto.CenterFunctionAuthorizationDto;
import cn.hsa.module.center.authorization.entity.CenterFunctionAuthorizationDO;
import cn.hsa.module.center.authorization.service.CenterFunctionAuthorizationService;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.user.dto.CenterUserDTO;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.insure.module.dto.InsureDictDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.ServletUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/center/centerFunctionAuthorization")
@Slf4j
public class CenterInsureAuthorizationController extends CenterBaseController {

    @Value("${rsa.public.key}")
    private String rsaPublicKey;

    private String rsaprivateKey = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAiZ5ah6YBBP5FWhgJXZnCSn9Z3y1kl0t6Q/x9QzNecAoiK94CMz28gEWSjPHoV+UJPNS5EhaHK3KrQt/bn5cY8QIDAQABAkARx46kpdLN5Vfqat6S5DGQ1GE1DzVGwq6aJ/269+EEkmlgLJGq0J2PFQEozWcfTZyvqDFZxaOc5fjGl7n9q4AdAiEA3qyShES5SNCSwJCG+0x+vPNY5YGKo95654te8A+Ef9sCIQCeNwEt09dvjaMHv+49LR6kLBGUjk8xzgYD28U70yY6IwIhAJIkKLTudbw4R1hignSDq9pOy9U0w8zwwzEb418ikA9pAiBc9MJLk6CLGTOFNR4bcWwEVyQJHUeoYnykPbZ3PMrD8wIgUHstQTolAIfvDSNJtt5f7XAZDDqI+HXh+cjR2Tj49B0=";
    @Resource
    private CenterFunctionAuthorizationService centerFunctionAuthorizationService;

    @GetMapping("/queryAuthorizationInfo/{hospCode}")
    public WrapperResponse<CenterFunctionAuthorizationDO> queryAdmdvsInfoPage(@PathVariable("hospCode") String hospCode ,HttpServletResponse res){
        Map<String,Object> map = new HashMap<>();
        map.put("hospCode", hospCode);
        map.put("orderTypeCode", "1");
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "GET");
        res.setHeader("Access-Control-Allow-Credentials", "true");
        CenterFunctionAuthorizationDO authorizationDO = centerFunctionAuthorizationService.queryBizAuthorizationByOrderTypeCode(map).getData();
        String str2EncryptStartDate = authorizationDO.getHospCode()+':'+authorizationDO.getOrderTypeCode()+':'+authorizationDO.getStartDate().getTime();
        String str2EncryptEndDate = authorizationDO.getHospCode()+':'+authorizationDO.getOrderTypeCode()+':'+authorizationDO.getEndDate().getTime();
        try {
            String encryptStartTime = RSAUtil.encryptByPublicKey(str2EncryptStartDate.getBytes(), rsaPublicKey);

            String decryptStartTime = RSAUtil.decryptByPrivateKey(org.apache.commons.codec.binary.Base64.decodeBase64(encryptStartTime.getBytes()), rsaprivateKey);

            String encryptEndTime = RSAUtil.encryptByPublicKey(str2EncryptEndDate.getBytes(), rsaPublicKey);

            String decryptEndTime = RSAUtil.decryptByPrivateKey(org.apache.commons.codec.binary.Base64.decodeBase64(encryptEndTime.getBytes()), rsaprivateKey);
            String decryptHospCode = decryptStartTime.substring(0,decryptStartTime.indexOf(':'));
            System.err.println("加密后开始时间:  "+encryptStartTime);
            System.err.println("解密后开始时间： "+ decryptStartTime);
            System.err.println("加密后结束时间:  "+encryptEndTime);
            System.err.println("解密后结束时间： "+ decryptEndTime);
            System.err.println("获取时间戳： "+ decryptEndTime.substring(decryptEndTime.lastIndexOf(':')+1));
            System.err.println("医院编码： "+ decryptHospCode);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return centerFunctionAuthorizationService.queryBizAuthorizationByOrderTypeCode(map);
    }

    @GetMapping("/insertAuthorization/{hospCode}/{orderTypeCode}/{startDate}/{endDate}")
    public WrapperResponse<CenterFunctionAuthorizationDO>  insertAuthorization(@PathVariable("hospCode") String hospCode,@PathVariable("orderTypeCode") String orderTypeCode,
                                                                               @PathVariable("startDate") String startDate,@PathVariable("endDate") String endDate){

        Map<String,Object> map = new HashMap<>();
        map.put("hospCode", "0001");
        map.put("orderTypeCode", "1");
        CenterFunctionAuthorizationDO authorizationDO = new CenterFunctionAuthorizationDO();
        String defaultStartDate = "2022-06-14";
        String defaultEndDate = "2023-06-15";
        authorizationDO.setStartDate(DateUtils.parse(startDate==null?defaultStartDate:startDate,DateUtils.Y_M_D));
        authorizationDO.setEndDate(DateUtils.parse(endDate==null?defaultEndDate:endDate,DateUtils.Y_M_D));
        authorizationDO.setAuditId("1");
        authorizationDO.setAuditName("admin");
        authorizationDO.setAuditStatus("1");
        authorizationDO.setIsValid("1");
        authorizationDO.setRemark("DRG/DIP相关授权信息");
        authorizationDO.setUpdateTime(new Date());
        authorizationDO.setId(SnowflakeUtils.getId());
        authorizationDO.setAuditTime(new Date());
        authorizationDO.setCrteTime(new Date());
        authorizationDO.setUpdateTime(new Date());
        authorizationDO.setHospCode(hospCode);
        authorizationDO.setOrderTypeCode(orderTypeCode);

        String str2EncryptStartDate = authorizationDO.getHospCode()+':'+authorizationDO.getOrderTypeCode()+':'+authorizationDO.getStartDate().getTime();
        String str2EncryptEndDate = authorizationDO.getHospCode()+':'+authorizationDO.getOrderTypeCode()+':'+authorizationDO.getEndDate().getTime();
        try {
            String encryptStartTime = RSAUtil.encryptByPublicKey(str2EncryptStartDate.getBytes(), rsaPublicKey);
            authorizationDO.setEncryptStartDate(encryptStartTime);
            String encryptEndTime = RSAUtil.encryptByPublicKey(str2EncryptEndDate.getBytes(), rsaPublicKey);
            authorizationDO.setEncryptEndDate(encryptEndTime);
        }catch (Exception e){
            log.error("加密时间出现问题",e);
        }
            return  centerFunctionAuthorizationService.insertBizAuthorization(authorizationDO);
    }




    /**
     * @Menthod queryHospZzywPage()
     * @Desrciption   根据条件分页查询医院增值业务
     * @Param
     * 1. [CenterHospitalDTO] 参数数据传输DTO对象
     * @Author pengbo
     * @Date   2022/06/28 16:30
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.center.PageDTO>
     **/
    @GetMapping("/queryHospZzywPage")
    public WrapperResponse<Map<String,Object>> queryHospZzywPage(CenterFunctionAuthorizationDto centerFunctionAuthorizationDto){
        return centerFunctionAuthorizationService.queryHospZzywPage(centerFunctionAuthorizationDto);
    }



    /**
     * @Menthod queryPage()
     * @Desrciption   根据条件分页查询参数信息
     * @Param
     * 1. [centerFunctionAuthorizationDto] 参数数据传输DTO对象
     * @Author pengbo
     * @Date   2022/06/28 16:30
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.center.PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<List<CenterFunctionAuthorizationDto>> queryPage(CenterFunctionAuthorizationDto centerFunctionAuthorizationDto){
        return centerFunctionAuthorizationService.queryPage(centerFunctionAuthorizationDto);
    }

    /**
     * @Menthod updateAuthorization()
     * @Desrciption   根据条件分页查询参数信息
     * @Param
     * 1. [centerFunctionAuthorizationDto] 参数数据传输DTO对象
     * @Author pengbo
     * @Date   2022/06/28 16:30
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.center.PageDTO>
     **/
    @GetMapping("/updateAuthorization")
    public WrapperResponse<Boolean> updateAuthorization(CenterFunctionAuthorizationDto centerFunctionAuthorizationDto){
        return centerFunctionAuthorizationService.updateAuthorization(centerFunctionAuthorizationDto);
    }


    /**
     * @Menthod updateAuthorizationAudit()
     * @Desrciption   根据条件分页查询参数信息
     * @Param
     * 1. [centerFunctionAuthorizationDto] 参数数据传输DTO对象
     * @Author pengbo
     * @Date   2022/06/28 16:30
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.center.PageDTO>
     **/
    @PostMapping("/updateAuthorizationAudit")
    public WrapperResponse<CenterFunctionAuthorizationDto> updateAuthorizationAudit(@RequestBody  CenterFunctionAuthorizationDto centerFunctionAuthorizationDto){
        return centerFunctionAuthorizationService.updateAuthorizationAudit(centerFunctionAuthorizationDto);
    }


    /**
     * @Menthod save()
     * @Desrciption   保存
     * @Param
     * 1. [centerFunctionAuthorizationDO] 参数数据传输DTO对象
     * @Author pengbo
     * @Date   2022/06/28 16:30
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<CenterFunctionAuthorizationDO>
     **/
    @PostMapping("/save")
    public WrapperResponse<CenterFunctionAuthorizationDto> save(@RequestBody  CenterFunctionAuthorizationDto centerFunctionAuthorizationDto){
        return centerFunctionAuthorizationService.saveBizAuthorization(centerFunctionAuthorizationDto);
    }




    /**
     * @Menthod queryHospZzywPage()
     * @Desrciption   根据条件分页查询医院增值业务
     * @Param
     * 1. [CenterHospitalDTO] 参数数据传输DTO对象
     * @Author pengbo
     * @Date   2022/06/28 16:30
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.center.PageDTO>
     **/
    @GetMapping("/queryHospZzywPage")
    public WrapperResponse<Map<String,Object>> queryHospZzywPage(CenterFunctionAuthorizationDto centerFunctionAuthorizationDto){
        return centerFunctionAuthorizationService.queryHospZzywPage(centerFunctionAuthorizationDto);
    }



    /**
     * @Menthod queryPage()
     * @Desrciption   根据条件分页查询参数信息
     * @Param
     * 1. [centerFunctionAuthorizationDto] 参数数据传输DTO对象
     * @Author pengbo
     * @Date   2022/06/28 16:30
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.center.PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<List<CenterFunctionAuthorizationDto>> queryPage(CenterFunctionAuthorizationDto centerFunctionAuthorizationDto){
        return centerFunctionAuthorizationService.queryPage(centerFunctionAuthorizationDto);
    }

    /**
     * @Menthod updateAuthorization()
     * @Desrciption   根据条件分页查询参数信息
     * @Param
     * 1. [centerFunctionAuthorizationDto] 参数数据传输DTO对象
     * @Author pengbo
     * @Date   2022/06/28 16:30
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.center.PageDTO>
     **/
    @GetMapping("/updateAuthorization")
    public WrapperResponse<Boolean> updateAuthorization(CenterFunctionAuthorizationDto centerFunctionAuthorizationDto){

        return centerFunctionAuthorizationService.updateAuthorization(centerFunctionAuthorizationDto);
    }


    /**
     * @Menthod updateAuthorizationAudit()
     * @Desrciption   根据条件分页查询参数信息
     * @Param
     * 1. [centerFunctionAuthorizationDto] 参数数据传输DTO对象
     * @Author pengbo
     * @Date   2022/06/28 16:30
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.center.PageDTO>
     **/
    @PostMapping("/updateAuthorizationAudit")
    public WrapperResponse<CenterFunctionAuthorizationDto> updateAuthorizationAudit(@RequestBody  CenterFunctionAuthorizationDto centerFunctionAuthorizationDto){
        if(centerFunctionAuthorizationDto != null){
            centerFunctionAuthorizationDto.setAuditId(userId);
            centerFunctionAuthorizationDto.setAuditName(userName);
        }
        return centerFunctionAuthorizationService.updateAuthorizationAudit(centerFunctionAuthorizationDto);
    }


    /**
     * @Menthod save()
     * @Desrciption   保存
     * @Param
     * 1. [centerFunctionAuthorizationDO] 参数数据传输DTO对象
     * @Author pengbo
     * @Date   2022/06/28 16:30
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<CenterFunctionAuthorizationDO>
     **/
    @PostMapping("/save")
    public WrapperResponse<CenterFunctionAuthorizationDto> save(@RequestBody  CenterFunctionAuthorizationDto centerFunctionAuthorizationDto){

        if(centerFunctionAuthorizationDto != null  && StringUtils.isEmpty(centerFunctionAuthorizationDto.getId())){
            centerFunctionAuthorizationDto.setCrteId(userId);
            centerFunctionAuthorizationDto.setCrteName(userName);
            centerFunctionAuthorizationDto.setCrteTime(new Date());
        }else{
            centerFunctionAuthorizationDto.setUpdateId(userId);
            centerFunctionAuthorizationDto.setUpdateName(userName);
            centerFunctionAuthorizationDto.setUpdateTime(new Date());
        }

        return centerFunctionAuthorizationService.saveBizAuthorization(centerFunctionAuthorizationDto);
    }
}
