package cn.hsa.center.authorization.bo.impl;

import cn.hsa.base.PageDO;
import cn.hsa.base.PageDTO;
import cn.hsa.base.RSAUtil;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.authorization.bo.CenterFunctionAuthorizationBO;
import cn.hsa.module.center.authorization.dao.CenterFunctionAuthorizationDAO;
import cn.hsa.module.center.authorization.dao.CenterInterceptUrlRecordDAO;
import cn.hsa.module.center.authorization.dto.CenterFunctionAuthorizationDto;
import cn.hsa.module.center.authorization.entity.CenterFunctionAuthorizationDO;
import cn.hsa.module.center.authorization.entity.CenterInterceptUrlRecordDO;
import cn.hsa.module.center.code.bo.CenterCodeBO;
import cn.hsa.module.center.code.dto.CenterCodeDetailDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import cn.hsa.util.ListUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private static final String CONTACT_US = "请通过400电话400-987500或者企业微信联系我们";
    /**
     *  权限未开通或正在审核都返回此文案
     */
    private static final String AUTHORIZATION_AUDITING_OR_NOT_FOUND = "未查询到本机构的DIP，DRG质控信息，"+CONTACT_US;
    /**
     *  数据被篡改返回此文案
     */
    private static final String DATA_ACCESS_EXCEPTION = "权限数据异常，"+CONTACT_US;
    private static final String SERVICE_NOT_STARTED = "已开通服务，未到服务时间。服务时间：";
    private static final String SERVICE_HAS_EXPIRED = "服务已过期，如有疑问，"+CONTACT_US+" 服务时间：";
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
            log.error("==-==权限单据类型不存在==-==httpStatus:{}, hospCode：{},orderTypeCode：{}", HttpStatus.NOT_FOUND.value(),hospCode, orderTypeCode);
            return WrapperResponse.error(-1, AUTHORIZATION_AUDITING_OR_NOT_FOUND, null);
        }
        // 1.校验是否审核完成
        if(!Constants.SF.S.equals(functionAuthorizationDO.getAuditStatus())) {
            log.info("==-==authorization not audit==-==httpStatus:{}, hospCode：{},orderTypeCode：{}",HttpStatus.UNAUTHORIZED.value(), hospCode, orderTypeCode);
            return WrapperResponse.error(-1, AUTHORIZATION_AUDITING_OR_NOT_FOUND, null);
        }
        // 2.校验时间是否被人为修改，医院编码与权限类型是否一致  医院编码:单据类型:时间戳 加密串解密
        String encryptStartDate = functionAuthorizationDO.getEncryptStartDate();
        String encryptEndDate = functionAuthorizationDO.getEncryptEndDate();
        String decryptStartTime = null;
        String decryptEndTime = null;
        try {
            decryptStartTime = RSAUtil.decryptByPrivateKey(org.apache.commons.codec.binary.Base64.decodeBase64(encryptStartDate.getBytes()), rsaprivateKey);
            decryptEndTime = RSAUtil.decryptByPrivateKey(org.apache.commons.codec.binary.Base64.decodeBase64(encryptEndDate.getBytes()), rsaprivateKey);
        } catch (Exception e) {
            log.error("解密功能授权使用时间出现异常 "+e);
            log.warn("解密功能授权使用时间出现异常==-==httpStatus:{}, hospCode：{},orderTypeCode：{}",HttpStatus.EXPECTATION_FAILED.value(),hospCode,orderTypeCode);
            return WrapperResponse.error(-1, DATA_ACCESS_EXCEPTION,null);
        }
        Long  startDateTimeStamp = Long.parseLong(decryptStartTime.substring(decryptStartTime.lastIndexOf(':')+1));
        Long  endDateTimeStamp = Long.parseLong(decryptEndTime.substring(decryptEndTime.lastIndexOf(':')+1));
        String decryptHospCode = decryptStartTime.substring(0,decryptStartTime.indexOf(':'));
        if(!(startDateTimeStamp.equals(functionAuthorizationDO.getStartDate().getTime())
                && endDateTimeStamp.equals(functionAuthorizationDO.getEndDate().getTime())
                && hospCode.equals(decryptHospCode))){
            log.info("==-==医院编码【 {} 】非法调用授权接口,解密后的开始时间：{},结束时间：{};数据库数据读取的开始时间：{},结束时间戳 ：{}",hospCode,startDateTimeStamp,endDateTimeStamp,
                    functionAuthorizationDO.getStartDate().getTime(),functionAuthorizationDO.getEndDate().getTime());
            return WrapperResponse.error(-1, DATA_ACCESS_EXCEPTION ,null);
        }
        // 3.校验是否在有效期内调用
        Long nowTimeStamp = DateUtils.getTimestamp();

        StringBuilder builder = new StringBuilder();
        builder.append("医院编码为【 ").append(functionAuthorizationDO.getHospCode()).append('】')
                    .append("功能授权已过期或未到授权开始时间,请在授权使用时间范围内调用，授权时间范围：").append(functionAuthorizationDO.getStartDate() +" ")
                    .append('-').append(" " +functionAuthorizationDO.getEndDate())
                    .append(", 权限类型代码：").append(orderTypeCode).append("httpStatus：").append(HttpStatus.FORBIDDEN.value());
        log.info("==-=="+builder);
        String dateRange = DateUtils.format(functionAuthorizationDO.getStartDate(),DateUtils.Y_M_D) +" - " +DateUtils.format(functionAuthorizationDO.getEndDate(),DateUtils.Y_M_D);
        if(startDateTimeStamp.compareTo(nowTimeStamp) > 0){
            return WrapperResponse.error(-1,SERVICE_NOT_STARTED+dateRange,null);
        }else if(nowTimeStamp.compareTo(endDateTimeStamp) > 0){
            // 已过期
            return WrapperResponse.error(-1,SERVICE_HAS_EXPIRED+dateRange,null);
        }
        return WrapperResponse.success(functionAuthorizationDO);
    }

    /**
     * 新增功能授权数据
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
     * 查询中心端需要拦截的uri列表
     *
     * @param params 参数
     * @return 需要拦截的uri列表
     */
    @Override
    public WrapperResponse<List<CenterInterceptUrlRecordDO>> queryAllCenterInterceptUrlRecords(Map<String, Object> params) {
        List<CenterInterceptUrlRecordDO> centerInterceptUrlRecordDOList = centerInterceptUrlRecordDAO.queryAllCenterInterceptUrlRecords();
        return WrapperResponse.success(centerInterceptUrlRecordDOList);
    }



    @Override
    public Map<String,Object> queryHospZzywPage(CenterFunctionAuthorizationDto centerFunctionAuthorizationDto) {

        CenterCodeDetailDTO centerCodeDetailDTO = new CenterCodeDetailDTO();
        centerCodeDetailDTO.setCode("QXDJ");
        centerCodeDetailDTO.setPageNo(0);
        centerCodeDetailDTO.setPageSize(10000);
        List<CenterCodeDetailDTO> centerCodeDetailDos = (List<CenterCodeDetailDTO>) centerCodeBO.queryCodeDetailPage(centerCodeDetailDTO).getResult();

        if (ListUtils.isEmpty(centerCodeDetailDos)){
            return null;
        }

        List<Map<String,Object>> childList = null;
        List<Map<String,Object>> tableHead = new ArrayList<>();
        StringBuffer sql1 = new StringBuffer(" , find_in_set('0',concat_ws(',',");
        StringBuffer sql2 = new StringBuffer();

        Map<String,Object> parentMap = null;
        Map<String,Object> childMap = null;
        for(CenterCodeDetailDTO centerCodeDetail :centerCodeDetailDos){
            sql1.append(centerCodeDetail.getCode() + centerCodeDetail.getValue() +"SH,");

            sql2.append("MAX(case when cf.order_type_code = '"+ centerCodeDetail.getValue() +"' then '"+ centerCodeDetail.getValue() +"' end) as "+ centerCodeDetail.getCode() + centerCodeDetail.getValue() +",");
            sql2.append("MAX(case when cf.order_type_code = '"+ centerCodeDetail.getValue() +"' then cf.audit_status end) as  "+ centerCodeDetail.getCode() + centerCodeDetail.getValue() +"SH,");
            sql2.append("MAX(case when cf.order_type_code = '"+ centerCodeDetail.getValue() +"' then concat(cf.start_date,' ~ ',cf.end_date)  end )as  "+ centerCodeDetail.getCode() + centerCodeDetail.getValue() +"YXQ,");

            parentMap = new HashMap();
            parentMap.put("label",centerCodeDetail.getName() );


            childList = new ArrayList<>();
            childMap = new HashMap();
            childMap.put("label","开通状态");
            childMap.put("prop",centerCodeDetail.getCode() + centerCodeDetail.getValue());
            childMap.put("type","text");
            childMap.put("minWidth","100");
            childList.add(childMap);

            childMap = new HashMap();
            childMap.put("label","审核状态");
            childMap.put("prop",centerCodeDetail.getCode() + centerCodeDetail.getValue()+"SH");
            childMap.put("type","text");
            childMap.put("minWidth","100");
            childList.add(childMap);

            childMap = new HashMap();
            childMap.put("label","服务时间");
            childMap.put("prop",centerCodeDetail.getCode() + centerCodeDetail.getValue()+"YXQ");
            childMap.put("type","text");
            childMap.put("minWidth","100");
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
        resultMap.put("tableData",list);




        return resultMap;
    }

    @Override
    public PageDTO queryPage(CenterFunctionAuthorizationDto centerFunctionAuthorizationDto) {
        PageHelper.startPage(centerFunctionAuthorizationDto.getPageNo(), centerFunctionAuthorizationDto.getPageSize());
        List<Map<String,Object>> list = centerFunctionAuthorizationDAO.queryPage(centerFunctionAuthorizationDto);
        return PageDTO.of(list);
    }

    @Override
    public Boolean updateAuthorization(CenterFunctionAuthorizationDto centerFunctionAuthorizationDto) {
        return centerFunctionAuthorizationDAO.updateAuthorization(centerFunctionAuthorizationDto)>0;
    }

    @Override
    public Boolean updateAuthorizationAudit(CenterFunctionAuthorizationDto centerFunctionAuthorizationDto) {
        return centerFunctionAuthorizationDAO.updateAuthorizationAudit(centerFunctionAuthorizationDto)>0;
    }
}
