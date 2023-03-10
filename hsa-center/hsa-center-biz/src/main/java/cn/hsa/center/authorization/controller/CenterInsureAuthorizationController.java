package cn.hsa.center.authorization.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.RSAUtil;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.authorization.dto.CenterFunctionAuthorizationDto;
import cn.hsa.module.center.authorization.dto.CenterFunctionDto;
import cn.hsa.module.center.authorization.entity.CenterFunctionAuthorizationDO;
import cn.hsa.module.center.authorization.service.CenterFunctionAuthorizationService;
import cn.hsa.util.DateUtils;
import cn.hsa.util.SnowflakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
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
            System.err.println("?????????????????????:  "+encryptStartTime);
            System.err.println("???????????????????????? "+ decryptStartTime);
            System.err.println("?????????????????????:  "+encryptEndTime);
            System.err.println("???????????????????????? "+ decryptEndTime);
            System.err.println("?????????????????? "+ decryptEndTime.substring(decryptEndTime.lastIndexOf(':')+1));
            System.err.println("??????????????? "+ decryptHospCode);
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
        authorizationDO.setRemark("DRG/DIP??????????????????");
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
            log.error("????????????????????????",e);
        }
            return  centerFunctionAuthorizationService.insertBizAuthorization(authorizationDO);
    }




    /**
     * @Menthod queryHospZzywPage()
     * @Desrciption   ??????????????????????????????????????????
     * @Param
     * 1. [CenterHospitalDTO] ??????????????????DTO??????
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
     * @Desrciption   ????????????????????????????????????
     * @Param
     * 1. [centerFunctionAuthorizationDto] ??????????????????DTO??????
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
     * @Desrciption   ????????????????????????????????????
     * @Param
     * 1. [centerFunctionAuthorizationDto] ??????????????????DTO??????
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
     * @Desrciption   ????????????????????????????????????
     * @Param
     * 1. [centerFunctionAuthorizationDto] ??????????????????DTO??????
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
     * @Desrciption   ??????
     * @Param
     * 1. [centerFunctionAuthorizationDO] ??????????????????DTO??????
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

    /**
     * @Menthod updateAuthorization()
     * @Desrciption   ????????????????????????????????????
     * @Param
     * 1. [centerFunctionAuthorizationDto] ??????????????????DTO??????
     * @Author pengbo
     * @Date   2022/06/28 16:30
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.center.PageDTO>
     **/
    @GetMapping("/queryCenterFunction")
    public WrapperResponse<List<CenterFunctionDto>> queryCenterFunction(CenterFunctionDto centerFunctionDto){

        return centerFunctionAuthorizationService.queryCenterFunction(centerFunctionDto);
    }



    /**
     * @Menthod deleteAuthorizationByCode()
     * @Desrciption   ??????????????????????????????
     * @Param
     * 1. [centerFunctionAuthorizationDto] ??????????????????DTO??????
     * @Author pengbo
     * @Date   2022/06/28 16:30
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.center.PageDTO>
     **/
    @PostMapping("/deleteAuthorizationByCode")
    public WrapperResponse<CenterFunctionAuthorizationDto> deleteAuthorizationByCode(@RequestBody  CenterFunctionAuthorizationDto centerFunctionAuthorizationDto){
        return centerFunctionAuthorizationService.deleteAuthorizationByCode(centerFunctionAuthorizationDto);
    }
}
