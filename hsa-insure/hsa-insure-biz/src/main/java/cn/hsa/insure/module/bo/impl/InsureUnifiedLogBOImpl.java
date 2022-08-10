package cn.hsa.insure.module.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.InsureUnifiedCommonUtil;
import cn.hsa.module.insure.module.bo.InsureUnifiedLogBO;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureUnifiedLogDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.entity.InsureFunctionLogDO;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.ResourceTransactionManager;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.module.bo.impl
 * @class_name: InsureUnifiedLogBOImpl
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/10/11 13:47
 * @Company: 创智和宇
 **/
@Component
@Slf4j
public class InsureUnifiedLogBOImpl extends HsafBO implements InsureUnifiedLogBO {
    /**
     * 开启事务
     */
    @Resource
    private ResourceTransactionManager transactionManager;

    @Resource
    private InsureUnifiedLogDAO insureUnifiedLogDAO;

    @Resource
    private InsureUnifiedCommonUtil insureUnifiedCommonUtil;



    /**
     * @param insureFunctionLogDO
     * @Method queryPage
     * @Desrciption 分页查询所有调用医保接口的日志信息
     * @Param map
     * @Author fuhui
     * @Date 2021/10/11 13:44
     * @Return
     */
    @Override
    public PageDTO queryPage(InsureFunctionLogDO insureFunctionLogDO) {
        PageHelper.startPage(insureFunctionLogDO.getPageNo(),insureFunctionLogDO.getPageSize());
        List<InsureFunctionLogDO> insureFunctionLogDOList =  insureUnifiedLogDAO.queryPage(insureFunctionLogDO);
        return PageDTO.of(insureFunctionLogDOList);
    }

    /**
     * @param map
     * @Method insertInsureFunctionLog
     * @Desrciption 保存调用医保接口的日志信息
     * @Param map
     * @Author fuhui
     * @Date 2021/10/11 13:44
     * @Return
     */
    @Override
    public boolean insertInsureFunctionLog(Map<String,Object> map) {

        TransactionStatus status = null;
        boolean functionLog = false;
        try {
            String hospCode = MapUtils.get(map,"hospCode");
            String crteId = MapUtils.get(map,"crteId");
            String crteName = MapUtils.get(map,"crteName");
            String visitId = MapUtils.get(map,"visitId");
            String msgId = MapUtils.get(map,"msgId");
            String msgInfo = MapUtils.get(map,"msgInfo");
            String msgName = MapUtils.get(map,"msgName");
            String isHospital = MapUtils.get(map,"isHospital");
            String paramMapJson = MapUtils.get(map,"paramMapJson");
            String medisCode = MapUtils.get(map,"medisCode");
            String resultStr = MapUtils.get(map,"resultStr");
            InsureFunctionLogDO insureFunctionDO = new InsureFunctionLogDO();
            //先判断返回串是否包含infcode
            String resultCode = "0";
            if (resultStr.contains("infcode")) {
                Map<String,Object> m = JSONObject.parseObject(resultStr,Map.class);
                resultCode = MapUtils.get(m,"infcode","0");
                if(!"0".equals(resultCode)){
                  insureFunctionDO.setErrorMsg("调用失败");
                }else{
                  insureFunctionDO.setErrorMsg("调用成功");
                }
              }else{
                insureFunctionDO.setErrorMsg("调用成功");
              }
            // 开启独立新事务
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            status = transactionManager.getTransaction(def);
            insureFunctionDO.setId(SnowflakeUtils.getId());
            insureFunctionDO.setHospCode(hospCode);
            insureFunctionDO.setVisitId(visitId);
            insureFunctionDO.setMedisCode(medisCode);
            insureFunctionDO.setMsgId(msgId);
            insureFunctionDO.setMsgInfo(msgInfo);
            insureFunctionDO.setMsgName(msgName);
            insureFunctionDO.setInParams(subStringByBytes(paramMapJson,0,4096));
            insureFunctionDO.setOutParams(subStringByBytes(resultStr,0,4096));
            insureFunctionDO.setCode(resultCode);
            insureFunctionDO.setCrteId(crteId);
            insureFunctionDO.setCrteTime(DateUtils.getNow());
            insureFunctionDO.setCrteName(crteName);
            insureFunctionDO.setIsHospital(isHospital);
            insureFunctionDO.setStatus(Constants.SF.F);
            functionLog = insureUnifiedLogDAO.insertInsureFunctionLog(insureFunctionDO);
            // 提交独立事务
            transactionManager.commit(status);
        } catch (Exception e) {
            if (status != null) {
                transactionManager.rollback(status);
            }
            log.error("insert insure function log error: ",e);
        }
        return functionLog;
    }

    /**
     * @param insureFunctionLogDO
     * @Method selectInsureLogs
     * @Desrciption his日志转医保日志入参
     * @Param
     * @Author fuhui
     * @Date 2022/1/4 9:37
     * @Return
     */
    @Override
    public String selectInsureLogs(InsureFunctionLogDO insureFunctionLogDO) {
        insureFunctionLogDO = insureUnifiedLogDAO.selectFunctionLogById(insureFunctionLogDO);
        if(insureFunctionLogDO ==null){
            return "";
        }
        String hospCode = insureFunctionLogDO.getHospCode();
        String inParams = insureFunctionLogDO.getInParams();
        Map<String,Object> map = JSONObject.parseObject(inParams, Map.class);
        String insureRegCode = "";
        if(!MapUtils.isEmpty(map)){
            for(String key : map.keySet()){
                if("insur_code".equals(key)){
                    insureRegCode = MapUtils.get(map,key);
                }
                if("msgid".equals(key)){
                    String msgid = MapUtils.get(map,key);
                    map.put("msgid",msgid.substring(0,msgid.length()-2)+"RC");
                }
            }
        }
        InsureConfigurationDTO insureInsureConfiguration = insureUnifiedCommonUtil.getInsureInsureConfiguration(hospCode, insureRegCode);
        if(insureInsureConfiguration == null){
            throw new AppException("未获取到医保配置机构信息");
        }
        String inputParams = JSONObject.toJSONString(map);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(insureInsureConfiguration.getUrl(),inputParams);
        return resultJson;
    }

    /** 截取字符串的指定字节长度
     * @Param [str, bengin, end]
     * @return java.lang.String
     **/
    public static String subStringByBytes(String str,int bengin,int end){
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        if (str.getBytes().length <= 4096) {
            return str;
        }
        String newStr="";
        try {
            //按指定的编码获取字节数组
            byte[] bytes = str.getBytes("UTF-8");
            //按指定的长度截取新的字符数组
            byte[] newBytes = Arrays.copyOfRange(bytes,bengin,end);
            //将新的字符数组转化为字符串
            newStr = new String(newBytes,"UTF-8");
        }catch (UnsupportedEncodingException e){
            log.info("医保日志记录出入参长度截取-字符转码异常");
        }
        return newStr.trim();
    }
}
