package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.enums.FunctionEnum;
import cn.hsa.enums.HsaSrvEnum;
import cn.hsa.exception.BizRtException;
import cn.hsa.exception.InsureExecCodesEnum;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.insure.module.service.InsureUnifiedLogService;
import cn.hsa.util.Constants;
import cn.hsa.util.HttpConnectUtil;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
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

    public Map<String, Object> executeInsur(String msgId, String url, FunctionEnum functionEnum, String request, Map<String, Object> params) throws BizRtException {
        //请求医保接口日志记录
        logger.info("流水号-{},医保业务功能号 {}-{},请求参数-{}", msgId, functionEnum.getDesc(), functionEnum.getCode(), request);
        //参数校验,规则校验和请求初始化
//        Map<Object, Object> map = getInsurRequest(functionEnum, object);
        Date callTime = new Date();
        try {
//            String requestURL = commonService.queryDictDataByKey(BusinessConstants.SYS_INSUR_REQUEST_URL).getConfigValue();
//            result = HttpClientUtil.doPost(requestURL, JSONObject.toJSONString(insurUtil.makeInterfaceBaseParamDTO(functionEnum.getCode(), JSON.toJSONString(map), insuplcAdmdvs, msgId, sysUserDTO)));
            String result = HttpConnectUtil.unifiedPayPostUtil(url, request);

            params.put("resultStr",result);


//            logger.info("统一支付平台获取人员信息 [1101] 回参<====");
//            logger.info(result);
            if(StringUtils.isEmpty(result)){
                params.put("resultStr","null");
                params.put("infcode","null");
//                insureUnifiedLogService_consumer.insertInsureFunctionLog(params).getData();
                throw new BizRtException(InsureExecCodesEnum.INSURE_RETURN_DATA_EMPTY,new Object[]{HsaSrvEnum.HYGEIA_HGS.getDesc(),functionEnum.getDesc()});
            }
            Map<String, Object> resultMap = JSON.parseObject(result);
            params.put("infcode",resultMap.get("infcode"));
            if (!resultMap.get("infcode").equals("0")) {
                String errMsg = (String) resultMap.get("err_msg");

//                logger.error("流水号-{},医保业务功能号 {}-{}, 失败结果:{}", msgId, functionEnum.getDesc(), functionEnum.getCode(), result);
                params.put("resultStr",errMsg == null ? "null": errMsg.length() > 5000 ? errMsg.substring(0,4500):errMsg);
//                insureUnifiedLogService_consumer.insertInsureFunctionLog(params).getData();
                throw new BizRtException(InsureExecCodesEnum.INSUR_SYS_FAILURE,new Object[]{HsaSrvEnum.HYGEIA_HGS.getDesc(),resultMap.get("err_msg")});
            }
            logger.info("流水号-{},医保业务功能号 {}-{},成功结果-{}", msgId, functionEnum.getDesc(), functionEnum.getCode(), result);
            params.put("resultStr",result == null ? "null": result.length() > 5000 ? result.substring(0,4500):result);
//            insureUnifiedLogService_consumer.insertInsureFunctionLog(params).getData();
            return resultMap;
        } catch (Exception e) {
//            logger.error("流水号-{},医保业务功能号 {}-{}, 错误异常信息:{}", msgId, functionEnum.getDesc(), functionEnum.getCode(), e);
            //调接口后，请求失败插入医保人员信息获取日志
            params.put("resultStr",e.getMessage()== null ? "null": e.getMessage().length() > 5000 ? e.getMessage().substring(0,4500):e.getMessage());
            throw new BizRtException(InsureExecCodesEnum.INSUR_SYS_FAILURE, new Object[]{HsaSrvEnum.HSA_INSURE.getDesc(),e});
        }finally {
            insureUnifiedLogService_consumer.insertInsureFunctionLog(params).getData();
        }

    }
}
