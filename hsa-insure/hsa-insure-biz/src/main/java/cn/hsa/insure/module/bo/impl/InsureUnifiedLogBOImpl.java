package cn.hsa.insure.module.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.insure.module.bo.InsureUnifiedLogBO;
import cn.hsa.module.insure.module.dao.InsureUnifiedLogDAO;
import cn.hsa.module.insure.module.entity.InsureFunctionLogDO;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.SnowflakeUtils;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.ResourceTransactionManager;

import javax.annotation.Resource;
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
        Map<String,Object> m = JSONObject.parseObject(resultStr,Map.class);
        String resultCode = MapUtils.get(m,"infcode","");
        TransactionStatus status = null;
        boolean functionLog = false;
        try {
            // 开启独立新事务
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            status = transactionManager.getTransaction(def);
            InsureFunctionLogDO insureFunctionDO = new InsureFunctionLogDO();
            insureFunctionDO.setId(SnowflakeUtils.getId());
            insureFunctionDO.setHospCode(hospCode);
            insureFunctionDO.setVisitId(visitId);
            insureFunctionDO.setMedisCode(medisCode);
            insureFunctionDO.setMsgId(msgId);
            insureFunctionDO.setMsgInfo(msgInfo);
            insureFunctionDO.setMsgName(msgName);
            insureFunctionDO.setInParams(paramMapJson);
            insureFunctionDO.setOutParams(resultStr);
            insureFunctionDO.setCode(resultCode);
            insureFunctionDO.setCrteId(crteId);
            insureFunctionDO.setCrteTime(DateUtils.getNow());
            insureFunctionDO.setCrteName(crteName);
            if(!"0".equals(resultCode)){
                insureFunctionDO.setErrorMsg(MapUtils.get(m,"err_msg"));
            }else{
                insureFunctionDO.setErrorMsg("调用成功");
            }
            insureFunctionDO.setIsHospital(isHospital);
            insureFunctionDO.setStatus(Constants.SF.F);
            functionLog = insureUnifiedLogDAO.insertInsureFunctionLog(insureFunctionDO);
            // 提交独立事务
            transactionManager.commit(status);
        } catch (Exception e) {
            if (status != null) {
                transactionManager.rollback(status);
            }
            System.out.println(e.getMessage());
        }
        return functionLog;
    }
}
