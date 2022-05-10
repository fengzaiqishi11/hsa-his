package cn.hsa.insure.module.bo.impl;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.module.bo.InsureInptTransfusionRecordBO;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureInptTransfusionRecordDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureInptTransfusionRecordDTO;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author luonianxin
 */
@Component
@Slf4j
public class InsureInptTransfusionRecordBOImpl  implements InsureInptTransfusionRecordBO {

    @Resource
    private InsureInptTransfusionRecordDAO insureInptTransfusionRecordDAO;

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    /**
     * 新增医保输血信息记录
     *
     * @param insureInptTransfusionRecordDTO 输血信息传输实体对象
     * @return 是否成功
     */
    @Override
    public Boolean insertInsureInptTransfusionRecord(InsureInptTransfusionRecordDTO insureInptTransfusionRecordDTO) {
        insureInptTransfusionRecordDTO.setId(SnowflakeUtils.getId());
        insureInptTransfusionRecordDTO.setMdtrtSn(insureInptTransfusionRecordDTO.getId());
        insureInptTransfusionRecordDTO.setIsTransmission(Constants.SF.F);
        return insureInptTransfusionRecordDAO.insertInsureInptTransfusionRecord(insureInptTransfusionRecordDTO) > 0;
    }
    /**
     * 编辑医保输血信息记录
     *
     * @param insureInptTransfusionRecordDTO 输血信息传输实体对象
     * @return 是否成功
     */
    @Override
    public Boolean updateInsureInptTransfusionRecord(InsureInptTransfusionRecordDTO insureInptTransfusionRecordDTO) {
        return insureInptTransfusionRecordDAO.updateInsureInptTransfusionRecord(insureInptTransfusionRecordDTO) > 0;
    }

    /**
     * 更新记录传输状态
     *
     * @param insureInptTransfusionRecordDTO 输血信息传输实体对象
     * @return 是否成功
     */
    @Override
    public Boolean updateInsureTransfusionRecordTransferred(InsureInptTransfusionRecordDTO insureInptTransfusionRecordDTO) {
        return insureInptTransfusionRecordDAO.updateInsureTransfusionRecordTransferred(insureInptTransfusionRecordDTO) > 0;
    }

    @Override
    public Boolean updateTransferInsureInptTranfusionRecords(Map params) {

        String strIds = MapUtils.get(params,"ids");
        String hospCode = MapUtils.get(params,"hospCode");
        String orgCode =  MapUtils.get(params,"orgCode");
        String regCode =  MapUtils.get(params,"regCode");
        List<String> idList = Arrays.asList(strIds.split(","));
        List<Map> dataMapList = insureInptTransfusionRecordDAO.queryInsureInptTransfusionRecordsMap(hospCode,idList);
        Map inputMap = new HashMap(4);
        inputMap.put("data",dataMapList);

        commonInsureUnified(hospCode,orgCode,regCode, Constant.UnifiedPay.INPT.UP_4601,inputMap);
        InsureInptTransfusionRecordDTO paramDTO = new InsureInptTransfusionRecordDTO();
        paramDTO.setIdList(idList);
        paramDTO.setIsTransmission(Constants.SF.S);
        paramDTO.setTransmissionTime(new Date());
        paramDTO.setHospCode(hospCode);
        insureInptTransfusionRecordDAO.updateInsureTransfusionRecordTransferred(paramDTO);
        return true;
    }

    /**
     *  传输病人输送血信息至统一支付平台
     * @param hospCode 医院编码
     * @param orgCode 医疗机构编码
     * @param functionCode 接口代码
     * @param paramMap 需要传输的参数
     * @return 调用结果
     */
    private Map<String, Object> commonInsureUnified(String hospCode, String orgCode,String regCode,
                                                    String functionCode, Map<String, Object> paramMap) {
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(orgCode);
        insureConfigurationDTO.setRegCode(regCode);
        insureConfigurationDTO.setIsValid(Constants.SF.S);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        Map httpParam = new HashMap();
        httpParam.put("infno", functionCode);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        httpParam.put("input", paramMap);
        String json = JSONObject.toJSONString(httpParam);
        log.info("调用功能号【" + functionCode + "】的入参为" + json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
        if (StringUtils.isEmpty(resultJson)) {
            throw new AppException("无法访问统一支付平台");
        }
        log.info("调用功能号【" + functionCode + "】的反参为" + resultJson);
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson, Map.class);
        if ("999".equals(MapUtils.get(resultMap, "code"))) {
            throw new AppException((String) resultMap.get("msg"));
        }
        if (!MapUtils.get(resultMap, "infcode").equals("0")) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        return resultMap;
    }

    /**
     * 查询病人输血信息列表
     *
     * @param param 参数
     * @return java.util.List 病人输血信息列表
     */
    @Override
    public List<InsureInptTransfusionRecordDTO> queryInsureInptTransfusionRecords(Map<String, Object> param) {
        return insureInptTransfusionRecordDAO.queryInsureInptTransfusionRecords(param);
    }


}
