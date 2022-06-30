package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.enums.HsaSrvEnum;
import cn.hsa.exception.BizRtException;
import cn.hsa.exception.InsureExecCodesEnum;
import cn.hsa.insure.enums.FunctionEnum;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.module.insure.module.service.InsureUnifiedLogService;
import cn.hsa.util.HttpConnectUtil;
import cn.hsa.util.MapUtils;
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
        params.put("hospCode", interfaceParamDTO.getHospCode());
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

        params.put("crteId", interfaceParamDTO.getOpter());
        params.put("crteName", interfaceParamDTO.getOpter_name());
        params.put("visitId", interfaceParamDTO.getVisitId());
        params.put("isHospital", interfaceParamDTO.getIsHospital());

        String paramMapJson = JSON.toJSONString(interfaceParamDTO);
        params.put("paramMapJson", paramMapJson == null ? "null" : paramMapJson.length() > 4000 ? paramMapJson.substring(0, 4000) : paramMapJson);
        //请求医保接口日志记录
        logger.info("流水号-{},医保业务功能号 {}-{},请求参数-{}", interfaceParamDTO.getMsgid(), functionEnum.getDesc(), functionEnum.getCode(), JSON.toJSONString(interfaceParamDTO));
        try {
            String result = HttpConnectUtil.unifiedPayPostUtil(interfaceParamDTO.getUrl(), JSON.toJSONString(interfaceParamDTO));
            params.put("resultStr", result);
            if (StringUtils.isEmpty(result)) {
                params.put("resultStr", "null");
                params.put("infcode", "null");
                throw new BizRtException(InsureExecCodesEnum.INSURE_RETURN_DATA_EMPTY, new Object[]{HsaSrvEnum.HYGEIA_HGS.getDesc(), interfaceParamDTO.getMsgid(), functionEnum.getDesc(), functionEnum.getCode(),result});
            }
            Map<String, Object> resultMap = JSON.parseObject(result);
            //云助手系统内部错误处理
            if(("999").equals(resultMap.get("code"))){
                params.put("infcode", resultMap.get("code"));
                String errMsg = (String) resultMap.get("msg");
                params.put("resultStr", errMsg == null ? "null" : errMsg.length() > 4000 ? errMsg.substring(0, 4000) : errMsg);
                throw new BizRtException(InsureExecCodesEnum.INSUR_INTF_FAILURE, new Object[]{HsaSrvEnum.HYGEIA_HGS.getDesc(), interfaceParamDTO.getMsgid(), functionEnum.getDesc(), functionEnum.getCode(), resultMap.get("msg")});
            }
            params.put("infcode", resultMap.get("infcode"));
            //云助手调用医保，医保返回错误处理
            if (!("0").equals(resultMap.get("infcode"))) {
                String errMsg = (String) resultMap.get("err_msg");
                params.put("resultStr", errMsg == null ? "null" : errMsg.length() > 4000 ? errMsg.substring(0, 4000) : errMsg);
                throw new BizRtException(InsureExecCodesEnum.INSUR_INTF_FAILURE, new Object[]{HsaSrvEnum.HYGEIA_INSURE.getDesc(), interfaceParamDTO.getMsgid(), functionEnum.getDesc(), functionEnum.getCode(), resultMap.get("err_msg")});
            }
            logger.info("流水号-{},医保业务功能号 {}-{},成功结果-{}", interfaceParamDTO.getMsgid(), functionEnum.getDesc(), functionEnum.getCode(), result);
            params.put("resultStr", result == null ? "null" : result.length() > 4000 ? result.substring(0, 4000) : result);
            return resultMap;
        } catch (Exception e) {
            //调接口后，请求失败插入医保人员信息获取日志
            params.put("resultStr", e.getMessage() == null ? "null" : e.getMessage().length() > 4000 ? e.getMessage().substring(0, 4000) : e.getMessage());
            if (e instanceof BizRtException) {
                throw e;
            } else {
                throw new BizRtException(InsureExecCodesEnum.INSUR_INTF_FAILURE, new Object[]{HsaSrvEnum.HSA_INSURE.getDesc(), interfaceParamDTO.getMsgid(), functionEnum.getDesc(), functionEnum.getCode(), e});
            }
        } finally {
            insureUnifiedLogService_consumer.insertInsureFunctionLog(params).getData();
        }
    }

    /**
     * 针对海南移动支付
     * @param functionEnum
     * @param interfaceParamDTO
     * @Author 医保开发二部-湛康
     * @Date 2022-06-01 8:56
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
  public Map<String, Object> executeHaiNanInsur(FunctionEnum functionEnum, InsureInterfaceParamDTO interfaceParamDTO) throws BizRtException {
    Map<String, Object> params = new HashMap<>();
    params.put("hospCode", interfaceParamDTO.getHospCode());
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

    params.put("crteId", interfaceParamDTO.getOpter());
    params.put("crteName", interfaceParamDTO.getOpter_name());
    params.put("visitId", interfaceParamDTO.getVisitId());
    params.put("isHospital", interfaceParamDTO.getIsHospital());

    String paramMapJson = JSON.toJSONString(interfaceParamDTO);
    params.put("paramMapJson", paramMapJson == null ? "null" : paramMapJson.length() > 4000 ? paramMapJson.substring(0, 4000) : paramMapJson);
    //请求医保接口日志记录
    logger.info("流水号-{},医保业务功能号 {}-{},请求参数-{}", interfaceParamDTO.getMsgid(), functionEnum.getDesc(), functionEnum.getCode(), JSON.toJSONString(interfaceParamDTO));
    try {
      String result = HttpConnectUtil.unifiedPayPostUtil(interfaceParamDTO.getUrl(), JSON.toJSONString(interfaceParamDTO));
      params.put("resultStr", result);
      if (StringUtils.isEmpty(result)) {
        params.put("resultStr", "null");
        params.put("infcode", "null");
        throw new BizRtException(InsureExecCodesEnum.INSURE_RETURN_DATA_EMPTY, new Object[]{HsaSrvEnum.HYGEIA_HGS.getDesc(), interfaceParamDTO.getMsgid(), functionEnum.getDesc(), functionEnum.getCode(),result});
      }
      Map<String, Object> resultMap = JSON.parseObject(result);
      params.put("infcode", resultMap.get("infcode"));
      //云助手调用医保，医保返回错误处理
      if (!("0").equals(resultMap.get("infcode"))) {
        String errMsg = (String) resultMap.get("err_msg");
        params.put("resultStr", errMsg == null ? "null" : errMsg.length() > 4000 ? errMsg.substring(0, 4000) : errMsg);
        throw new BizRtException(InsureExecCodesEnum.INSUR_INTF_FAILURE, new Object[]{HsaSrvEnum.HYGEIA_INSURE.getDesc(), interfaceParamDTO.getMsgid(), functionEnum.getDesc(), functionEnum.getCode(), resultMap.get("err_msg")});
      }
      logger.info("流水号-{},医保业务功能号 {}-{},成功结果-{}", interfaceParamDTO.getMsgid(), functionEnum.getDesc(), functionEnum.getCode(), result);
      params.put("resultStr", result == null ? "null" : result.length() > 4000 ? result.substring(0, 4000) : result);
      return resultMap;
    } catch (Exception e) {
      //调接口后，请求失败插入医保人员信息获取日志
      params.put("resultStr", e.getMessage() == null ? "null" : e.getMessage().length() > 4000 ? e.getMessage().substring(0, 4000) : e.getMessage());
      if (e instanceof BizRtException) {
        throw e;
      } else {
        throw new BizRtException(InsureExecCodesEnum.INSUR_INTF_FAILURE, new Object[]{HsaSrvEnum.HSA_INSURE.getDesc(), interfaceParamDTO.getMsgid(), functionEnum.getDesc(), functionEnum.getCode(), e});
      }
    } finally {
      insureUnifiedLogService_consumer.insertInsureFunctionLog(params).getData();
    }
  }
}
