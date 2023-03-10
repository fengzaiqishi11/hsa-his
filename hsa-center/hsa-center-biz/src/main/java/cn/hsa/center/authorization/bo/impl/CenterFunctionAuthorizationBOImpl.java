package cn.hsa.center.authorization.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.RSAUtil;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.authorization.bo.CenterFunctionAuthorizationBO;
import cn.hsa.module.center.authorization.dao.CenterFunctionAuthorizationDAO;
import cn.hsa.module.center.authorization.dao.CenterInterceptUrlRecordDAO;
import cn.hsa.module.center.authorization.dto.CenterFunctionAuthorizationDto;
import cn.hsa.module.center.authorization.dto.CenterFunctionDetailDto;
import cn.hsa.module.center.authorization.dto.CenterFunctionDto;
import cn.hsa.module.center.authorization.entity.CenterFunctionAuthorizationDO;
import cn.hsa.module.center.authorization.entity.CenterInterceptUrlRecordDO;
import cn.hsa.module.center.code.bo.CenterCodeBO;
import cn.hsa.module.center.code.dto.CenterCodeDetailDTO;
import cn.hsa.module.insure.module.dto.InsureDiseaseMatchDTO;
import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author luonianxin
 * @version 1.0
 * @date 2022/6/7 16:30
 */
@Slf4j
@Component
public class CenterFunctionAuthorizationBOImpl implements CenterFunctionAuthorizationBO {

    @Value("${rsa.public.key}")
    private String rsaPublicKey;

    private final String rsaprivateKey = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAiZ5ah6YBBP5FWhgJXZnCSn9Z3y1kl0t6Q/x9QzNecAoiK94CMz28gEWSjPHoV+UJPNS5EhaHK3KrQt/bn5cY8QIDAQABAkARx46kpdLN5Vfqat6S5DGQ1GE1DzVGwq6aJ/269+EEkmlgLJGq0J2PFQEozWcfTZyvqDFZxaOc5fjGl7n9q4AdAiEA3qyShES5SNCSwJCG+0x+vPNY5YGKo95654te8A+Ef9sCIQCeNwEt09dvjaMHv+49LR6kLBGUjk8xzgYD28U70yY6IwIhAJIkKLTudbw4R1hignSDq9pOy9U0w8zwwzEb418ikA9pAiBc9MJLk6CLGTOFNR4bcWwEVyQJHUeoYnykPbZ3PMrD8wIgUHstQTolAIfvDSNJtt5f7XAZDDqI+HXh+cjR2Tj49B0=";

    private static final String CONTACT_US = "?????????400??????400-987500??????????????????????????????";

    /**
     *  ??????????????????????????????????????????????????????
     */
    private static  final String AUTHORIZATION_AUDITING_OR_NOT_FOUND_PREFIX = "????????????????????????";
    /**
     *  ??????????????????????????????????????????????????????
     */
    private static  final String AUTHORIZATION_AUDITING_OR_NOT_FOUND_SUFFIX = "?????????"+CONTACT_US;
    /**
     *  ?????????????????????????????????
     */
    private static  final String AUTHORIZATION_AUDITING_OR_NOT_FOUND_TYPE = "??????????????????";
    /**
     *  ????????????????????????????????????????????????
     */
    private static final String AUTHORIZATION_AUDITING_OR_NOT_FOUND = "????????????????????????DIP???DRG?????????"+CONTACT_US;
    /**
     *  ??????????????????????????????
     */
    private static final String DATA_ACCESS_EXCEPTION = "?????????????????????"+CONTACT_US;
    private static final String SERVICE_NOT_STARTED = "??????????????????????????????????????????????????????";
    private static final String SERVICE_HAS_EXPIRED = "?????????????????????????????????"+CONTACT_US+" ???????????????";
    private CenterFunctionAuthorizationDAO centerFunctionAuthorizationDAO;

    private CenterInterceptUrlRecordDAO centerInterceptUrlRecordDAO;

    @Resource
    private CenterCodeBO centerCodeBO;

    @Autowired
    public void setCenterFunctionAuthorizationDAO(CenterFunctionAuthorizationDAO centerFunctionAuthorizationDAO) {
        this.centerFunctionAuthorizationDAO = centerFunctionAuthorizationDAO;
    }
    @Autowired
    public void setCenterInterceptUrlRecordDAO(CenterInterceptUrlRecordDAO centerInterceptUrlRecordDAO) {
        this.centerInterceptUrlRecordDAO = centerInterceptUrlRecordDAO;
    }

    @Override
    public WrapperResponse<CenterFunctionAuthorizationDO> queryBizAuthorizationByOrderTypeCode(String hospCode, String orderTypeCode) {
        CenterFunctionAuthorizationDO functionAuthorizationDO = centerFunctionAuthorizationDAO.queryBizAuthorizationByOrderTypeCode(hospCode,orderTypeCode);
        if(null == functionAuthorizationDO) {
            log.error("==-==???????????????????????????==-==httpStatus:{}, hospCode???{},orderTypeCode???{}", HttpStatus.NOT_FOUND.value(),hospCode, orderTypeCode);
            String resultStr = resultString(orderTypeCode);
            return WrapperResponse.error(-1, resultStr, null);
        }
        // 1.????????????????????????
        if(!Constants.SF.S.equals(functionAuthorizationDO.getAuditStatus())) {
            log.info("==-==authorization not audit==-==httpStatus:{}, hospCode???{},orderTypeCode???{}",HttpStatus.UNAUTHORIZED.value(), hospCode, orderTypeCode);
            String resultStr = resultString(orderTypeCode);
            return WrapperResponse.error(-1, resultStr, null);
        }
        // 2.???????????????????????????????????????????????????????????????????????????  ????????????:????????????:????????? ???????????????
        String encryptStartDate = functionAuthorizationDO.getEncryptStartDate();
        String encryptEndDate = functionAuthorizationDO.getEncryptEndDate();
        String decryptStartTime = null;
        String decryptEndTime = null;
        try {
            decryptStartTime = RSAUtil.decryptByPrivateKey(org.apache.commons.codec.binary.Base64.decodeBase64(encryptStartDate.getBytes()), rsaprivateKey);
            decryptEndTime = RSAUtil.decryptByPrivateKey(org.apache.commons.codec.binary.Base64.decodeBase64(encryptEndDate.getBytes()), rsaprivateKey);
        } catch (Exception e) {
            log.error("?????????????????????????????????????????? "+e);
            log.warn("??????????????????????????????????????????==-==httpStatus:{}, hospCode???{},orderTypeCode???{}",HttpStatus.EXPECTATION_FAILED.value(),hospCode,orderTypeCode);
            return WrapperResponse.error(-1, DATA_ACCESS_EXCEPTION,null);
        }
        Long  startDateTimeStamp = Long.parseLong(decryptStartTime.substring(decryptStartTime.lastIndexOf(':')+1));
        Long  endDateTimeStamp = Long.parseLong(decryptEndTime.substring(decryptEndTime.lastIndexOf(':')+1));
        String decryptHospCode = decryptStartTime.substring(0,decryptStartTime.indexOf(':'));
        if(!(startDateTimeStamp.equals(functionAuthorizationDO.getStartDate().getTime())
                && endDateTimeStamp.equals(functionAuthorizationDO.getEndDate().getTime())
                && hospCode.equals(decryptHospCode))){
            log.info("==-==??????????????? {} ???????????????????????????,???????????????????????????{},???????????????{};???????????????????????????????????????{},??????????????? ???{}",hospCode,startDateTimeStamp,endDateTimeStamp,
                    functionAuthorizationDO.getStartDate().getTime(),functionAuthorizationDO.getEndDate().getTime());
            return WrapperResponse.error(-1, DATA_ACCESS_EXCEPTION ,null);
        }
        // 3.?????????????????????????????????
        Long nowTimeStamp = DateUtils.getTimestamp();

        StringBuilder builder = new StringBuilder();
        builder.append("?????????????????? ").append(functionAuthorizationDO.getHospCode()).append('???')
                    .append("????????????????????????????????????????????????,???????????????????????????????????????????????????????????????").append(functionAuthorizationDO.getStartDate() +" ")
                    .append('-').append(" " +functionAuthorizationDO.getEndDate())
                    .append(", ?????????????????????").append(orderTypeCode).append("httpStatus???").append(HttpStatus.FORBIDDEN.value());
        log.info("==-=="+builder);
        String dateRange = DateUtils.format(functionAuthorizationDO.getStartDate(),DateUtils.Y_M_D) +" - " +DateUtils.format(functionAuthorizationDO.getEndDate(),DateUtils.Y_M_D);
        if(startDateTimeStamp.compareTo(nowTimeStamp) > 0){
            return WrapperResponse.error(-1,SERVICE_NOT_STARTED+dateRange,null);
        }else if(nowTimeStamp.compareTo(endDateTimeStamp) > 0){
            // ?????????
            return WrapperResponse.error(-1,SERVICE_HAS_EXPIRED+dateRange,null);
        }
        return WrapperResponse.success(functionAuthorizationDO);
    }
    /**
     * ????????????
     *
     * @param orderTypeCode
     * @return
     */
    private String resultString(String orderTypeCode) {
        String resStr = AUTHORIZATION_AUDITING_OR_NOT_FOUND_TYPE;
        CenterCodeDetailDTO codeDetailDTO = centerFunctionAuthorizationDAO.queryCodeValue(orderTypeCode);
        if(null != codeDetailDTO){
            resStr = codeDetailDTO.getName();
        }
        return  AUTHORIZATION_AUDITING_OR_NOT_FOUND_PREFIX + resStr + AUTHORIZATION_AUDITING_OR_NOT_FOUND_SUFFIX;
    }

    /**
     * ????????????????????????
     *
     * @param functionAuthorizationDO
     * @return
     */
    @Override
    public WrapperResponse<CenterFunctionAuthorizationDO> insertBizAuthorization(CenterFunctionAuthorizationDO functionAuthorizationDO) {
        int affectRows = centerFunctionAuthorizationDAO.insertAuthorization(functionAuthorizationDO);
        return WrapperResponse.success(functionAuthorizationDO);
    }

    /**
     * ??????????????????????????????uri??????
     *
     * @param params ??????
     * @return ???????????????uri??????
     */
    @Override
    public WrapperResponse<List<CenterInterceptUrlRecordDO>> queryAllCenterInterceptUrlRecords(Map<String, Object> params) {
        List<CenterInterceptUrlRecordDO> centerInterceptUrlRecordDOList = centerInterceptUrlRecordDAO.queryAllCenterInterceptUrlRecords();
        return WrapperResponse.success(centerInterceptUrlRecordDOList);
    }


    @Override
    public Map<String,Object> queryHospZzywPage(CenterFunctionAuthorizationDto centerFunctionAuthorizationDto) {

        CenterFunctionDto centerFunction = new CenterFunctionDto();
        centerFunction.setCode(centerFunctionAuthorizationDto.getServiceCode());
        List<CenterFunctionDto> centerFunctionDtos = centerFunctionAuthorizationDAO.queryCenterFuctionPage(centerFunction);

        if (ListUtils.isEmpty(centerFunctionDtos)){
            return null;
        }

        List<Map<String,Object>> childList = null;
        List<Map<String,Object>> tableHead = new ArrayList<>();
        StringBuffer sql1 = new StringBuffer(" , find_in_set('0',concat_ws(',',");
        StringBuffer sql2 = new StringBuffer();

        Map<String,Object> parentMap = null;
        Map<String,Object> childMap = null;
        for(CenterFunctionDto centerFunctionDto :centerFunctionDtos){
            sql1.append(centerFunctionDto.getCode()  +"SH,");

            sql2.append("if( MAX(case when cf.service_code = '"+ centerFunctionDto.getCode() +"' then cf.id end) is null,'0','1')  as "+ centerFunctionDto.getCode() +",");
            sql2.append("MAX(case when cf.service_code = '"+ centerFunctionDto.getCode() +"' then cf.audit_status end) as  "+ centerFunctionDto.getCode() +"SH,");
            sql2.append("MAX(case when cf.service_code = '"+ centerFunctionDto.getCode() +"' then concat(cf.start_date,' ~ ',cf.end_date)  end )as  "+ centerFunctionDto.getCode() +"YXQ,");

            parentMap = new HashMap();
            parentMap.put("label",centerFunctionDto.getName() );


            childList = new ArrayList<>();
            childMap = new HashMap();
            childMap.put("label","????????????");
            childMap.put("prop",centerFunctionDto.getCode());
            childMap.put("minWidth","150");
            childMap.put("code","KTZT");
            childList.add(childMap);

            childMap = new HashMap();
            childMap.put("label","????????????");
            childMap.put("prop",centerFunctionDto.getCode() +"SH");
            childMap.put("minWidth","150");
            childMap.put("code","YYSHZT");
            childList.add(childMap);

            childMap = new HashMap();
            childMap.put("label","????????????");
            childMap.put("prop",centerFunctionDto.getCode() +"YXQ");
            childMap.put("minWidth","150");
            childList.add(childMap);


            parentMap.put("children",childList);
            tableHead.add(parentMap);
        }
        sql1 = new StringBuffer(sql1.substring(0,sql1.length()-1));
        sql1.append(" )) sfysh") ;
        centerFunctionAuthorizationDto.setSql1(sql1.toString());
        centerFunctionAuthorizationDto.setSql2(sql2.toString());
        PageHelper.startPage(centerFunctionAuthorizationDto.getPageNo(), centerFunctionAuthorizationDto.getPageSize());
        List<Map<String,Object>> list = centerFunctionAuthorizationDAO.queryHospZzywPage(centerFunctionAuthorizationDto);

        Map<String,Object> resultMap = new HashMap();
        resultMap.put("tableHead",tableHead);
        resultMap.put("tableData",PageDTO.of(list));




        return resultMap;
    }

    @Override
    public List<CenterFunctionAuthorizationDto> queryPage(CenterFunctionAuthorizationDto centerFunctionAuthorizationDto) {
        List<CenterFunctionAuthorizationDto> list = centerFunctionAuthorizationDAO.queryPage(centerFunctionAuthorizationDto);
        List<CenterFunctionDetailDto> centerFunctionDetailDtos = centerFunctionAuthorizationDAO.queryCenterFunctionDetailPage(centerFunctionAuthorizationDto);
        Map<String,List<CenterFunctionDetailDto>> detailDtoMaps = centerFunctionDetailDtos.stream().collect(Collectors.groupingBy(CenterFunctionDetailDto::getFunctionCode));

        for (CenterFunctionAuthorizationDto functionAuthorizationDto :list){
            List<CenterFunctionDetailDto> detailDtos = detailDtoMaps.get(functionAuthorizationDto.getServiceCode());
            if(!ListUtils.isEmpty(detailDtoMaps.get(functionAuthorizationDto.getServiceCode())) && detailDtoMaps.get(functionAuthorizationDto.getServiceCode()).size()>1){
                CenterFunctionDetailDto centerFunctionDetailDto = new CenterFunctionDetailDto();
                centerFunctionDetailDto.setValue(detailDtos.stream().map(CenterFunctionDetailDto::getValue).collect(Collectors.joining(",")));
                centerFunctionDetailDto.setName(detailDtos.stream().map(CenterFunctionDetailDto::getName).collect(Collectors.joining("/")));
                detailDtos.add(centerFunctionDetailDto);
            }
            functionAuthorizationDto.setCenterFunctionDetailDtoList(detailDtos);
        }
        return list;
    }

    @Override
    public Boolean updateAuthorization(CenterFunctionAuthorizationDto centerFunctionAuthorizationDto) {
        return centerFunctionAuthorizationDAO.updateAuthorization(centerFunctionAuthorizationDto)>0;
    }

    @Override
    public CenterFunctionAuthorizationDto updateAuthorizationAudit(CenterFunctionAuthorizationDto centerFunctionAuthorizationDto) {
        centerFunctionAuthorizationDAO.updateAuthorizationAudit(centerFunctionAuthorizationDto);
        centerFunctionAuthorizationDto = getCenterFunctionAuthorizationDTO(centerFunctionAuthorizationDto);

        return  centerFunctionAuthorizationDto;
    }

    @Override
    public CenterFunctionAuthorizationDto saveBizAuthorization(CenterFunctionAuthorizationDto centerFunctionAuthorizationDto) {

        if(centerFunctionAuthorizationDto.getStartDate() == null){
            throw new RuntimeException("????????????????????????!");
        }

        if(centerFunctionAuthorizationDto.getEndDate() == null){
            throw new RuntimeException("????????????????????????!");
        }

        if(centerFunctionAuthorizationDto.getHospCode() == null){
            throw new RuntimeException("????????????????????????!");
        }

        if(centerFunctionAuthorizationDto.getOrderTypeCode() == null){
            throw new RuntimeException("????????????????????????!");
        }

        centerFunctionAuthorizationDto.setAuditStatus("0");
        centerFunctionAuthorizationDto.setIsValid("1");
        if(StringUtils.isEmpty(centerFunctionAuthorizationDto.getRemark())){
            centerFunctionAuthorizationDto.setRemark(centerFunctionAuthorizationDto.getName()+"????????????");
        }

        centerFunctionAuthorizationDto.setAuditTime(new Date());
        centerFunctionAuthorizationDto.setCrteTime(new Date());
        // ??????????????????
        String str2EncryptStartDate = centerFunctionAuthorizationDto.getHospCode()+':'+centerFunctionAuthorizationDto.getOrderTypeCode()+':'+centerFunctionAuthorizationDto.getStartDate().getTime();
        // ??????????????????
        String str2EncryptEndDate = centerFunctionAuthorizationDto.getHospCode()+':'+centerFunctionAuthorizationDto.getOrderTypeCode()+':'+centerFunctionAuthorizationDto.getEndDate().getTime();
        try {
            String encryptStartTime = RSAUtil.encryptByPublicKey(str2EncryptStartDate.getBytes(), rsaPublicKey);
            centerFunctionAuthorizationDto.setEncryptStartDate(encryptStartTime);
            String encryptEndTime = RSAUtil.encryptByPublicKey(str2EncryptEndDate.getBytes(), rsaPublicKey);
            centerFunctionAuthorizationDto.setEncryptEndDate(encryptEndTime);
        }catch (Exception e){
            log.error("????????????????????????",e);
        }

        centerFunctionAuthorizationDAO.deleteAuthorization(centerFunctionAuthorizationDto);

        List<CenterFunctionAuthorizationDto> list = new ArrayList<CenterFunctionAuthorizationDto>();
        if(StringUtils.isNotEmpty(centerFunctionAuthorizationDto.getOrderTypeCode())){
            String [] types = centerFunctionAuthorizationDto.getOrderTypeCode().split(",");
            CenterFunctionAuthorizationDto newCenterFunctionAuthorizationDto = null ;
            for(String type:types){
                newCenterFunctionAuthorizationDto = new CenterFunctionAuthorizationDto();
               BeanUtils.copyProperties(centerFunctionAuthorizationDto,newCenterFunctionAuthorizationDto);
                newCenterFunctionAuthorizationDto.setOrderTypeCode(type);
                newCenterFunctionAuthorizationDto.setId(SnowflakeUtils.getId());
                list.add(newCenterFunctionAuthorizationDto);
            }
            centerFunctionAuthorizationDAO.insertBtchAuthorization(list);
        }

        centerFunctionAuthorizationDto = getCenterFunctionAuthorizationDTO(centerFunctionAuthorizationDto);

        return  centerFunctionAuthorizationDto;
    }

    @Override
    public List<CenterFunctionDto> queryCenterFunction(CenterFunctionDto centerFunctionDto) {
        return centerFunctionAuthorizationDAO.queryCenterFuctionPage(null);
    }

    @Override
    public CenterFunctionAuthorizationDto deleteAuthorizationByCode(CenterFunctionAuthorizationDto centerFunctionAuthorizationDto) {
        centerFunctionAuthorizationDAO.deleteAuthorization(centerFunctionAuthorizationDto);
        centerFunctionAuthorizationDto = getCenterFunctionAuthorizationDTO(centerFunctionAuthorizationDto);

        return  centerFunctionAuthorizationDto;
    }




    public CenterFunctionAuthorizationDto getCenterFunctionAuthorizationDTO(CenterFunctionAuthorizationDto centerFunctionAuthorizationDto) {

        List<CenterFunctionAuthorizationDto> list = centerFunctionAuthorizationDAO.queryPage(centerFunctionAuthorizationDto);
        if (!ListUtils.isEmpty(list)) {
            centerFunctionAuthorizationDto = list.get(0);
            List<CenterFunctionDetailDto> centerFunctionDetailDtos = centerFunctionAuthorizationDAO.queryCenterFunctionDetailPage(centerFunctionAuthorizationDto);
            Map<String,List<CenterFunctionDetailDto>> detailDtoMaps = centerFunctionDetailDtos.stream().collect(Collectors.groupingBy(CenterFunctionDetailDto::getFunctionCode));
            if (!MapUtils.isEmpty(detailDtoMaps)) {
                List<CenterFunctionDetailDto> detailDtos = detailDtoMaps.get(centerFunctionAuthorizationDto.getServiceCode());
                if(!ListUtils.isEmpty(detailDtoMaps.get(centerFunctionAuthorizationDto.getServiceCode())) && detailDtoMaps.get(centerFunctionAuthorizationDto.getServiceCode()).size()>1){
                    CenterFunctionDetailDto centerFunctionDetailDto = new CenterFunctionDetailDto();
                    centerFunctionDetailDto.setValue(detailDtos.stream().map(CenterFunctionDetailDto::getValue).collect(Collectors.joining(",")));
                    centerFunctionDetailDto.setName(detailDtos.stream().map(CenterFunctionDetailDto::getName).collect(Collectors.joining("/")));
                    detailDtos.add(centerFunctionDetailDto);
                }
                centerFunctionAuthorizationDto.setCenterFunctionDetailDtoList(detailDtos);
            }
        }
        return centerFunctionAuthorizationDto;
    }
}
