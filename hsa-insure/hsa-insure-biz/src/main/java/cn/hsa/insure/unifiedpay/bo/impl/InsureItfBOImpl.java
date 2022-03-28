package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.enums.HsaSrvEnum;
import cn.hsa.exception.BizRtException;
import cn.hsa.exception.InsureExecCodesEnum;
import cn.hsa.insure.enums.FunctionEnum;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.module.insure.module.service.InsureUnifiedLogService;
import cn.hsa.util.HttpConnectUtil;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName InsureItfBOImpl
 * @Description TODO
 * @Author qiang.fan
 * @Date 2022/3/3 17:26
 * @Version 1.0
 **/
@Component
public class InsureItfBOImpl {

    @Resource
    private InsureUnifiedLogService insureUnifiedLogService_consumer;


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public Map<String, Object> executeInsur(FunctionEnum functionEnum, InsureInterfaceParamDTO interfaceParamDTO) throws BizRtException {
        Map<String, Object> params = new HashMap<>();
        // 定点医药机构编号
        params.put("medisCode", interfaceParamDTO.getMedins_code());
        // 医保中心编码
        params.put("regCode", interfaceParamDTO.getInsur_code());
        // 就医地医保区划
        params.put("mdtrtareaAdmvs", interfaceParamDTO.getMdtrtarea_admvs());
        // 交易编号
        params.put("infno", interfaceParamDTO.getInfno());
        params.put("msgId", interfaceParamDTO.getMsgid());
        params.put("msgInfo", functionEnum.getCode());
        params.put("msgName", functionEnum.getDesc());

        params.put("paramMapJson", JSON.toJSONString(interfaceParamDTO));
        //请求医保接口日志记录
        logger.info("流水号-{},医保业务功能号 {}-{},请求参数-{}", interfaceParamDTO.getMsgid(), functionEnum.getDesc(), functionEnum.getCode(), JSON.toJSONString(interfaceParamDTO));
        try {
            String result = HttpConnectUtil.unifiedPayPostUtil(interfaceParamDTO.getUrl(), JSON.toJSONString(interfaceParamDTO));
            params.put("resultStr", result);
            if (StringUtils.isEmpty(result)) {
                params.put("resultStr", "null");
                params.put("infcode", "null");
                throw new BizRtException(InsureExecCodesEnum.INSURE_RETURN_DATA_EMPTY, new Object[]{HsaSrvEnum.HYGEIA_HGS.getDesc(), functionEnum.getDesc()});
            }
            Map<String, Object> resultMap = JSON.parseObject(result);
            params.put("infcode", resultMap.get("infcode"));
            if (!resultMap.get("infcode").equals("0")) {
                String errMsg = (String) resultMap.get("err_msg");

                params.put("resultStr", errMsg == null ? "null" : errMsg.length() > 5000 ? errMsg.substring(0, 4500) : errMsg);
                throw new BizRtException(InsureExecCodesEnum.INSUR_SYS_FAILURE, new Object[]{HsaSrvEnum.HYGEIA_HGS.getDesc(), interfaceParamDTO.getMsgid(), functionEnum.getDesc(), functionEnum.getCode(), resultMap.get("err_msg")});
            }
            logger.info("流水号-{},医保业务功能号 {}-{},成功结果-{}", interfaceParamDTO.getMsgid(), functionEnum.getDesc(), functionEnum.getCode(), result);
            params.put("resultStr", result == null ? "null" : result.length() > 5000 ? result.substring(0, 4500) : result);
            return resultMap;
        } catch (Exception e) {
            //调接口后，请求失败插入医保人员信息获取日志
            params.put("resultStr", e.getMessage() == null ? "null" : e.getMessage().length() > 5000 ? e.getMessage().substring(0, 4500) : e.getMessage());
            if (e instanceof BizRtException) {
                throw e;
            } else {
                throw new BizRtException(InsureExecCodesEnum.INSUR_SYS_FAILURE, new Object[]{HsaSrvEnum.HSA_INSURE.getDesc(), interfaceParamDTO.getMsgid(), functionEnum.getDesc(), functionEnum.getCode(), e});
            }
        } finally {
            insureUnifiedLogService_consumer.insertInsureFunctionLog(params).getData();
        }
    }
}
