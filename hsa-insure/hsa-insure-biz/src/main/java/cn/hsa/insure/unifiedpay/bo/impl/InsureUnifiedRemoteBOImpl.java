package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.inpt.bo.InsureUnifiedRemoteBO;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualSettleDAO;
import cn.hsa.module.insure.module.dao.InsureUnifiedRemoteDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.entity.InsureUnifiedRemoteDO;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.bo.impl
 * @class_name: InsureUnifiedRemoteBOImpl
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/4/23 12:37
 * @Company: 创智和宇
 **/
@Slf4j
@Component
public class InsureUnifiedRemoteBOImpl extends HsafBO implements InsureUnifiedRemoteBO {

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    @Resource
    private InsureIndividualSettleDAO insureIndividualSettleDAO;

    @Resource
    private InsureUnifiedRemoteDAO insureUnifiedRemoteDAO;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @Method UP_3206  selectRemoteDetail
     * @Desrciption 提取异地清分明细:就医地使用此交易提取省内异地外来就医月度结算清分明细,供医疗机构进行确认处理
     * @Param 循环调用当前月份的3206接口  提取数据每次不能超过100条
     * @Author fuhui
     * @Date 2021/4/23 12:47
     * @Return
     **/
    public Map<String, Object> insertRemoteDetail(Map<String, Object> map) {

        String hospCode = MapUtils.get(map, "hospCode");
        String crteId = MapUtils.get(map, "crteId");
        String crteName = MapUtils.get(map, "crteName");
        String regCode = MapUtils.get(map, "insureRegCode");
        /**
         * 获取访问的url地址
         */
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(regCode); // 医疗机构编码;
        insureConfigurationDTO = getInsureConfiguration(insureConfigurationDTO);
        List<Map<String, Object>> resultDataMap = null;
        List<Map<String, Object>> sumDataMap = new ArrayList<>();
        Map<String, Object> inputMap = null;
        Map<String, Object> dataMap = null;
        Map<String, Object> paramMap = null;
        List<Map<String, Object>> filterDataMap = null;
        // 调用统一支付平台 上传费用明细
        inputMap = new HashMap<>();
        dataMap = new HashMap<>();
        paramMap = new HashMap<>();
        String msgId = StringUtils.createMsgId(insureConfigurationDTO.getOrgCode());
        inputMap.put("infno", Constant.UnifiedPay.REGISTER.UP_3260);  // 交易编号
        inputMap.put("insuplc_admdvs", insureConfigurationDTO.getRegCode());  //TODO 参保地医保区划
        inputMap.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        inputMap.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        inputMap.put("msgid", msgId);
        inputMap.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());// 就医地医保区划
        /**
         * 公共入参
         */
        String beganTime = MapUtils.get(map, "updtTime");
        String yearStr = beganTime.substring(0, 4);
        String monthStr = beganTime.substring(5, 7);
        dataMap.put("trt_year", yearStr);
        dataMap.put("trt_month", monthStr);
        Integer startRow = 1;
        do {
            dataMap.put("startrow", startRow);
            paramMap.put("data", dataMap);
            inputMap.put("input", paramMap);  // 入参具体数据
            String json = JSONObject.toJSONString(inputMap);
            logger.info("提取异地清分明细入参:" + json);
            String resultJson = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
            if(StringUtils.isEmpty(resultJson)){
                throw new AppException("无法访问统一支付平台");
            }
            Map<String, Object> resultMap = JSONObject.parseObject(resultJson, Map.class);
            if ("999".equals(MapUtils.get(resultMap, "code"))) {
                throw new AppException((String) resultMap.get("msg"));
            }
            if (!MapUtils.get(resultMap, "infcode").equals("0")) {
                throw new AppException((String) resultMap.get("err_msg"));
            }
            logger.info("提取异地清分明细回参:" + resultJson);
            Map<String, Object> outptMap = MapUtils.get(resultMap, "output");
            resultDataMap = MapUtils.get(outptMap, "data");
            filterDataMap = resultDataMap.stream().filter(item -> StringUtils.isNotEmpty(MapUtils.get(item, "mdtrt_id"))).
                    collect(Collectors.toList());
            startRow += resultDataMap.size();
            sumDataMap.addAll(filterDataMap);
        } while (!ListUtils.isEmpty(filterDataMap) && startRow >= 100);
        map.put("sumDataMap", sumDataMap);
        InsureUnifiedRemoteDO insureUnifiedRemoteDO = new InsureUnifiedRemoteDO();
        List<InsureUnifiedRemoteDO> unifiedRemoteDOList = new ArrayList<>();
        if (!ListUtils.isEmpty(sumDataMap)) {
            for (Map<String, Object> item : sumDataMap) {
                insureUnifiedRemoteDO = new InsureUnifiedRemoteDO();
                DecimalFormat df1 = new DecimalFormat("0.00");
                insureUnifiedRemoteDO.setId(SnowflakeUtils.getId());
                insureUnifiedRemoteDO.setHospCode(hospCode);
                insureUnifiedRemoteDO.setSeqno(MapUtils.get(item, "seqno").toString());
                insureUnifiedRemoteDO.setTrtMonth(monthStr);
                insureUnifiedRemoteDO.setTrtYear(yearStr);
                insureUnifiedRemoteDO.setMdtrtarea(MapUtils.get(item, "mdtrtarea"));
                insureUnifiedRemoteDO.setMedinsNo(MapUtils.get(item, "medins_no"));
                insureUnifiedRemoteDO.setCertno(MapUtils.get(item, "certno"));
                insureUnifiedRemoteDO.setMdtrtId(MapUtils.get(item, "mdtrt_id"));
                insureUnifiedRemoteDO.setMdtrtSetlTime(DateUtils.parse(MapUtils.get(item, "mdtrt_setl_time"), DateUtils.Y_M_DH_M_S));
                insureUnifiedRemoteDO.setSetlSn(MapUtils.get(item, "setl_sn"));
                insureUnifiedRemoteDO.setFulamtAdvpayFlag(MapUtils.get(item, "fulamt_advpay_flag"));
                insureUnifiedRemoteDO.setMedfeeSumamt(BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "medfee_sumamt") == null ? "":MapUtils.get(item, "medfee_sumamt").toString()))));
                insureUnifiedRemoteDO.setOptinsPaySumamt(BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "optins_pay_sumamt") == null ? "":MapUtils.get(item, "optins_pay_sumamt").toString()))));
                insureUnifiedRemoteDO.setCrteId(crteId);
                insureUnifiedRemoteDO.setCnfmFlag(null);
                insureUnifiedRemoteDO.setCrteName(crteName);
                insureUnifiedRemoteDO.setCrteTime(DateUtils.getNow());
                insureUnifiedRemoteDO.setIsFlag("0");
                unifiedRemoteDOList.add(insureUnifiedRemoteDO);
            }
        }
        insureUnifiedRemoteDO.setHospCode(hospCode);
        List<InsureUnifiedRemoteDO> insertRemoteList = new ArrayList<>();
        List<InsureUnifiedRemoteDO> remoteDOList = insureUnifiedRemoteDAO.queryAll(insureUnifiedRemoteDO);
        Map<String, InsureUnifiedRemoteDO> collect = remoteDOList.stream().collect(Collectors.toMap(InsureUnifiedRemoteDO::getSetlSn, Function.identity(), (k1, k2) -> k1));
        if (!ListUtils.isEmpty(unifiedRemoteDOList)) {
            for (InsureUnifiedRemoteDO unifiedRemoteDO : unifiedRemoteDOList) {
                if (!collect.isEmpty() && collect.containsKey(unifiedRemoteDO.getSetlSn())) {
                    break;
                } else {
                    insertRemoteList.add(unifiedRemoteDO);
                }
            }
        }
        if (!ListUtils.isEmpty(insertRemoteList)) {
            insureUnifiedRemoteDAO.insertRemoteFeeDetail(insertRemoteList);
        }
        return map;
    }


    /**
     * @Method UP_3261 selectRemoteConfirmResult
     * @Desrciption 异地清分结果确认:就医地使用此交易提交省内异地外来就医月度结算清分确认结果。
     * @Param
     * @Author fuhui
     * @Date 2021/4/23 12:47
     * @Return
     **/
    public Map<String, Object> updateRemoteConfirmResult(Map<String, Object> map) {

        String hospCode = MapUtils.get(map, "hospCode");
        String regCode = MapUtils.get(map, "insureRegCode");
        /**
         * 获取访问的url地址
         */
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(regCode); // 医疗机构编码;
        insureConfigurationDTO = getInsureConfiguration(insureConfigurationDTO);


        // 调用统一支付平台 上传费用明细
        Map<String, Object> inputMap = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();
        List<Map<String, Object>> detailMapList = new ArrayList<>();

        /**
         * 公共入参
         */
        String osgId = StringUtils.createMsgId(insureConfigurationDTO.getOrgCode());
        inputMap.put("infno", Constant.UnifiedPay.REGISTER.UP_3261);  // 交易编号
        inputMap.put("insuplc_admdvs", insureConfigurationDTO.getRegCode());  //TODO 参保地医保区划
        inputMap.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        inputMap.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        inputMap.put("msgid", osgId);
        inputMap.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());// 就医地医保区划
        String isFlag = MapUtils.get(map, "isFlag");
        String beganTime = MapUtils.get(map, "updtTime");
        String yearStr = beganTime.substring(0, 4);
        String monthStr = beganTime.substring(5, 7);
        dataMap.put("trt_year", yearStr);
        dataMap.put("trt_month", monthStr);
        InsureUnifiedRemoteDO insureUnifiedRemoteDO = new InsureUnifiedRemoteDO();
        insureUnifiedRemoteDO.setHospCode(hospCode);
        insureUnifiedRemoteDO.setTrtYear(yearStr);
        insureUnifiedRemoteDO.setTrtMonth(monthStr);
        List<InsureUnifiedRemoteDO> unifiedRemoteDOList = insureUnifiedRemoteDAO.queryAll(insureUnifiedRemoteDO);
        Map<String, Object> detailMap = null;
        for (InsureUnifiedRemoteDO unifiedRemoteDO : unifiedRemoteDOList) {
            unifiedRemoteDO.setOmgsId(osgId);
            unifiedRemoteDO.setIsFlag("1");
            detailMap = new HashMap<>();
            detailMap.put("certno", unifiedRemoteDO.getCertno());
            detailMap.put("mdtrt_id", unifiedRemoteDO.getMdtrtId());
            detailMap.put("mdtrt_setl_time", DateUtils.format(unifiedRemoteDO.getMdtrtSetlTime(), DateUtils.Y_M_DH_M_S));
            detailMap.put("setl_sn", unifiedRemoteDO.getSetlSn());
            detailMap.put("medfee_sumamt", unifiedRemoteDO.getMedfeeSumamt());
            detailMap.put("optins_pay_sumamt", unifiedRemoteDO.getOptinsPaySumamt());
            detailMap.put("cnfm_flag", isFlag);
            detailMapList.add(detailMap);
        }
        String url = insureConfigurationDTO.getUrl();
        int batchCount = 100;
        String resultJson = "";
        Map<String, Object> map1 = new HashMap<>();
        List<Map<String, Object>> transmitList = new ArrayList<>();
        if (detailMapList.size() > 100) {
            int leng = 0;
            for (int i = 0; i < detailMapList.size(); i++) {
                transmitList.add(detailMapList.get(i));
                if (batchCount == transmitList.size() || i == transmitList.size() - 1) {
                    try {
                        if (leng > 0) {
                            Thread.sleep(2000);
                        }
                        map1 = new HashMap<>();
                        dataMap.put("totalrow", batchCount);
                        map1.put("data", dataMap);
                        map1.put("detail", detailMapList);
                        inputMap.put("input", map1);
                        inputMap.put("input", dataMap);
                        String json = JSONObject.toJSONString(inputMap);
                        logger.info("异地清分结果确认入参:" + json);
                        updateUnifiedRemoteFlag(unifiedRemoteDOList);
                        transmitList.clear();
                    } catch (Exception e) {

                    }
                }
            }
            leng++;
        } else {
            dataMap.put("totalrow", unifiedRemoteDOList.size());
            map1.put("data", dataMap);
            map1.put("detail", detailMapList);
            inputMap.put("input", map1);
            String json = JSONObject.toJSONString(inputMap);
            logger.info("异地清分结果确认入参入参:" + json);
            resultJson = HttpConnectUtil.unifiedPayPostUtil(url, json);
            if (StringUtils.isEmpty(resultJson)) {
                throw new AppException("无法访问统一支付平台");
            }
            Map<String, Object> resultMap = JSONObject.parseObject(resultJson, Map.class);
            if (!"0".equals(resultMap.get("infcode").toString())) {
                throw new AppException((String) resultMap.get("err_msg"));
            }
            if ("999".equals(resultMap.get("infcode").toString())) {
                throw new AppException((String) resultMap.get("msg"));
            }
            updateUnifiedRemoteFlag(unifiedRemoteDOList);
        }
        return map;
    }

    private void updateUnifiedRemoteFlag(List<InsureUnifiedRemoteDO> unifiedRemoteDOList) {
        insureUnifiedRemoteDAO.updateUnifiedRemoteFlag(unifiedRemoteDOList);
    }


    /**
     * @Method UP_3262 updateRemoteConfirmBack
     * @Desrciption 异地清分结果确认回退:就医地使用此交易回退已经提交的就医月度结算清分确认结果。
     * @Param
     * @Author fuhui
     * @Date 2021/4/23 12:47
     * @Return
     **/
    public Map<String, Object> updateRemoteConfirmBack(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String regCode = MapUtils.get(map, "insureRegCode");
        /**
         * 获取访问的url地址
         */
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(regCode); // 医疗机构编码;
        insureConfigurationDTO = getInsureConfiguration(insureConfigurationDTO);

        // 调用统一支付平台 上传费用明细
        Map<String, Object> inputMap = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();
        Map<String, Object> detailMap = new HashMap<>();

        /**
         * 公共入参
         */
        inputMap.put("infno", Constant.UnifiedPay.REGISTER.UP_3262);  // 交易编号
        inputMap.put("insuplc_admdvs", insureConfigurationDTO.getRegCode());  //TODO 参保地医保区划
        inputMap.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        inputMap.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        inputMap.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        inputMap.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());// 就医地医保区划

        String beganTime = MapUtils.get(map, "updtTime");
        String yearStr = beganTime.substring(0, 4);
        String monthStr = beganTime.substring(5, 7);

        InsureUnifiedRemoteDO insureUnifiedRemoteDO = new InsureUnifiedRemoteDO();
        insureUnifiedRemoteDO.setHospCode(hospCode);
        insureUnifiedRemoteDO.setTrtYear(yearStr);
        insureUnifiedRemoteDO.setTrtMonth(monthStr);
        insureUnifiedRemoteDO.setIsFlag("1");
        List<InsureUnifiedRemoteDO> unifiedRemoteDOList = insureUnifiedRemoteDAO.queryAll(insureUnifiedRemoteDO);

        dataMap.put("trt_year", yearStr);
        dataMap.put("trt_month", monthStr);
        dataMap.put("otransid", "0");
        inputMap.put("input", dataMap);  // 入参具体数据
        String json = JSONObject.toJSONString(inputMap);
        logger.info("异地清分结果确认回退入参:" + json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
        if (StringUtils.isEmpty(resultJson)) {
            throw new AppException("无法访问统一支付平台");
        }
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson, Map.class);
        logger.info("异地清分结果确认回退回参:" + resultJson);
        if (!"0".equals(resultMap.get("infcode").toString())) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        if ("999".equals(resultMap.get("infcode").toString())) {
            throw new AppException((String) resultMap.get("msg"));
        }
        unifiedRemoteDOList.stream().forEach(insureUnifiedRemoteDO1 -> {
            insureUnifiedRemoteDO1.setIsFlag("0");
            insureUnifiedRemoteDO1.setOmgsId("");
        });
        insureUnifiedRemoteDAO.updateUnifiedRemoteFlag(unifiedRemoteDOList);
        return map;
    }

    /**
     * @param insureUnifiedRemoteDO@Method 查询异地清分明细信息
     * @Desrciption
     * @Param
     * @Author fuhui
     * @Date 2021/6/8 20:22
     * @Return
     */
    @Override
    public PageDTO queryPage(InsureUnifiedRemoteDO insureUnifiedRemoteDO) {

        String startDate = insureUnifiedRemoteDO.getStartDate();
        String endDate = insureUnifiedRemoteDO.getEndDate();
        if(StringUtils.isNotEmpty(startDate)){
           startDate = startDate.substring(0,7);
        }
        if(StringUtils.isNotEmpty(endDate)){
            endDate = endDate.substring(0,7);
        }
        insureUnifiedRemoteDO.setStartDate(startDate);
        insureUnifiedRemoteDO.setEndDate(endDate);
        PageHelper.startPage(insureUnifiedRemoteDO.getPageNo(), insureUnifiedRemoteDO.getPageSize());
        List<InsureUnifiedRemoteDO> insureUnifiedRemoteDOList = insureUnifiedRemoteDAO.queryPage(insureUnifiedRemoteDO);
        return PageDTO.of(insureUnifiedRemoteDOList);
    }


    /**
     * @Method getInsureConfiguration
     * @Desrciption 根据医院编码和医疗机构编码查询对应医保的url和参保区划地址
     * @Param
     * @Author fuhui
     * @Date 2021/3/3 10:52
     * @Return
     **/
    public InsureConfigurationDTO getInsureConfiguration(InsureConfigurationDTO insureConfigurationDTO) {
        return insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
    }
}
