package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.InsureUnifiedCommonUtil;
import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
import cn.hsa.module.base.bd.service.BaseDiseaseService;
import cn.hsa.module.base.bi.service.BaseItemService;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.bmm.service.BaseMaterialService;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.base.drug.service.BaseDrugService;
import cn.hsa.module.center.nationstandarddrug.entity.NationStandardDrugDO;
import cn.hsa.module.insure.module.dao.*;
import cn.hsa.module.insure.module.dto.*;
import cn.hsa.module.insure.module.entity.*;
import cn.hsa.module.insure.module.service.InsureDiseaseMatchService;
import cn.hsa.module.insure.module.service.InsureIndividualBasicService;
import cn.hsa.module.insure.module.service.InsureItemMatchService;
import cn.hsa.module.insure.module.service.InsureUnifiedLogService;
import cn.hsa.module.insure.outpt.bo.InsureUnifiedPayRestBO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.bo.impl
 * @class_name: InsureUnifiedPayRestBOImpl
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/3/12 15:18
 * @Company: ????????????
 **/
@Slf4j
@Component
public class InsureUnifiedPayRestBOImpl extends HsafBO implements InsureUnifiedPayRestBO {

    @Resource
    InsureItemMatchDAO insureItemMatchDAO;
    @Resource
    InsureItemDAO insureItemDAO;

    @Resource
    InsureDiseaseDAO insureDiseaseDAO;
    @Resource
    InsureDiseaseMatchDAO insureDiseaseMatchDAO;
    @Resource
    InsureDiseaseMatchService insureDiseaseMatchService_consumer;
    @Resource
    InsureItemMatchService insureItemMatchService_consumer;
    @Resource
    InsureIndividualSettleDAO insureIndividualSettleDAO;
    @Resource
    InsureDirectoryDownLoadDAO insureDirectoryDownLoadDAO;
    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;
    @Resource
    private InsureIndividualVisitDAO insureIndividualVisitDAO;
    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private InsureIndividualBasicService insureIndividualBasicService_consumer;

    @Resource
    private InsureUnifiedCommonUtil insureUnifiedCommonUtil;
    @Resource
    private InsureDictDAO   insureDictDAO;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @param map
     * @Method downLoadItem
     * @Desrciption ?????????????????????????????????????????????
     * @Parammap
     * @Author fuhui
     * @Date 2021/3/12 15:12
     * @Return
     */
    @Override
    public Map<String, Object> UP_1317(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        Date startDate = MapUtils.get(map, "startDate");
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("query_date", DateUtils.format(DateUtils.getNow(), DateUtils.Y_M_D));  // ???????????????
        dataMap.put("fixmedins_code", "");  // ????????????????????????
        dataMap.put("medins_list_codg", "");  // ??????????????????????????????
        dataMap.put("medins_list_name", "");  // ??????????????????????????????
        dataMap.put("insu_admdvs", insureConfigurationDTO.getRegCode());  // ????????????????????????
        dataMap.put("list_type", "");  // ????????????
        dataMap.put("med_list_codg", "");  // ??????????????????
        dataMap.put("begndate", "");  // ????????????
        dataMap.put("vali_flag", Constants.SF.S);  // ????????????
        dataMap.put("updt_time", DateUtils.format(startDate, DateUtils.Y_M_D));  // ????????????
        dataMap.put("page_num", 1);  // ????????????
        dataMap.put("page_size", Integer.MAX_VALUE);  // ???????????????
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("data", dataMap);
        map.put("msgName","????????????????????????????????????");
        map.put("isHospital","");
        map.put("visitId","");
        Map<String, Object> resultMap = insureUnifiedCommonUtil.commonInsureUnified(hospCode, insureConfigurationDTO.getRegCode(), Constant.UnifiedPay.REGISTER.UP_1317, inputMap,map);
        Map<String, Object> outputMap = (Map<String, Object>) resultMap.get("output");
        List<Map<String, Object>> resultDataMap = (List<Map<String, Object>>) outputMap.get("data");
        if (!ListUtils.isEmpty(resultDataMap)) {
            InsureItemMatchDTO insureItemMatchDTO = new InsureItemMatchDTO();
            insureItemMatchDTO.setHospCode(hospCode); // ????????????
            insureItemMatchDTO.setInsureRegCode(insureRegCode); // ??????????????????
            insureItemMatchDTO.setAuditCode(Constants.SF.F);
            insureItemMatchDTO.setIsValid(Constants.SF.S);
            List<InsureItemMatchDTO> itemDTOList = insureItemMatchDAO.queryPageOrAll(insureItemMatchDTO); // ????????????????????????????????????????????????????????????
            Map<String, InsureItemMatchDTO> collect = itemDTOList.stream().collect(Collectors.toMap(InsureItemMatchDTO::getHospItemCode, Function.identity(), (k1, k2) -> k1));
            InsureItemMatchDTO itemMatchDTO = null;
            List<InsureItemMatchDTO> insureItemDTOList = new ArrayList<>();
            for (Map<String, Object> item : resultDataMap) {
                if (!collect.isEmpty() && collect.containsKey(item.get("medins_list_codg").toString())) {
                    itemMatchDTO = new InsureItemMatchDTO();
                    itemMatchDTO.setHospCode(hospCode); // ????????????
                    itemMatchDTO.setHospItemCode(MapUtils.get(item, "medins_list_codg"));  // ??????????????????????????????
                    itemMatchDTO.setInsureRegCode(MapUtils.get(item, "insu_admdvs")); // ????????????????????????
                    itemMatchDTO.setItemCode(MapUtils.get(item, "list_type")); // ????????????
                    itemMatchDTO.setInsureItemCode(MapUtils.get(item, "med_list_codg")); // ??????????????????
                    itemMatchDTO.setTakeDate(DateUtils.parse(MapUtils.get(item, "begndate"), DateUtils.Y_M_DH_M_S)); // ????????????
                    itemMatchDTO.setLoseDate(DateUtils.parse(MapUtils.get(item, "enddate"), DateUtils.Y_M_DH_M_S));  // ????????????
                    if (Constants.SF.S.equals(MapUtils.get(item, "vali_flag"))) {
                        itemMatchDTO.setIsValid(Constants.SF.S); // ????????????
                        itemMatchDTO.setIsMatch(Constants.SF.S); // ????????????
                        itemMatchDTO.setIsTrans(Constants.SF.S); // ????????????
                        itemMatchDTO.setAuditCode(Constants.SF.S); // ????????????
                    } else {
                        itemMatchDTO.setIsValid(Constants.SF.F); // ????????????
                        itemMatchDTO.setIsMatch(Constants.SF.F); // ????????????
                        itemMatchDTO.setIsTrans(Constants.SF.F); // ????????????
                        itemMatchDTO.setAuditCode(Constants.SF.F); // ????????????
                    }
                    insureItemDTOList.add(itemMatchDTO);
                }
            }
            System.out.println("???????????????????????????" + insureItemDTOList.size());
            int batchCount = 300; // ?????????????????????????????????
            int k = 0;
            if (!ListUtils.isEmpty(insureItemDTOList)) {
                boolean stat = true;
                while (true) {
                    List<InsureItemMatchDTO> insureItemMatchDTOList = null;
                    if (insureItemDTOList.size() - k * batchCount >= batchCount) {
                        insureItemMatchDTOList = insureItemDTOList.subList(k * batchCount, k * batchCount + batchCount);
                    } else {
                        insureItemMatchDTOList = insureItemDTOList.subList(k * batchCount, insureItemDTOList.size() - 1);
                        stat = false;
                    }
                    insureItemMatchDAO.updateInsureItemMatchS(insureItemMatchDTOList);
                    if (!stat || k * batchCount == insureItemDTOList.size()) break;
                    k++;
                    System.out.println("????????????" + k);

                }
            }
        }
        return resultMap;
    }

    /**
     * @Method UP_9162
     * @Desrciption ????????????????????????-??????????????????
     * @Param map
     * @Author fuhui
     * @Date 2021/3/15 9:12
     * @Return
     **/
    @Override
    public Map<String, Object> UP_9162(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        String listType = MapUtils.get(map, "listType");
        String crteName = MapUtils.get(map, "crteName");
        String crteId = MapUtils.get(map, "crteId");
        Date startDate = MapUtils.get(map, "startDate");
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        Map httpParam = new HashMap();
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_9162);  //????????????
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //????????????????????????
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //????????????????????????
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //??????????????????
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("fixmedins_hilist_id", ""); // ??????????????????????????????
        paramMap.put("list_type", listType); // ????????????
        paramMap.put("med_list_codg", ""); // ??????????????????
        paramMap.put("begntime", DateUtils.format(startDate, DateUtils.Y_M_D)); // ????????????

        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("data", paramMap);
        httpParam.put("input", inputMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("????????????????????????????????????????????????:" + json);
        String url = insureConfigurationDTO.getUrl();
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(url, json);
        logger.info("????????????????????????????????????????????????:" + resultJson);
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson);
        if (!"0".equals(resultMap.get("infcode").toString())) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        Map<String, Object> outputMap = (Map<String, Object>) resultMap.get("output");
        List<Map<String, Object>> resultDataMap = (List<Map<String, Object>>) outputMap.get("data");
        map.put("insureConfigurationDTO", insureConfigurationDTO);
        List<InsureItemMatchDTO> insureItemDTOList = new ArrayList<>();
        InsureItemMatchDTO insureItemMatchDTO = new InsureItemMatchDTO();
        insureItemMatchDTO.setHospCode(hospCode);
        insureItemMatchDTO.setInsureRegCode(insureRegCode);
        List<InsureItemMatchDTO> insureItemMatchDTOList = insureItemMatchDAO.queryPageOrAll(insureItemMatchDTO);
        Map<String, InsureItemMatchDTO> collect = insureItemMatchDTOList.stream().collect(Collectors.toMap(InsureItemMatchDTO::getHospItemCode, Function.identity(), (k1, k2) -> k1));
        if (!ListUtils.isEmpty(insureItemMatchDTOList)) {
            System.out.println("???????????????????????????");
            for (Map<String, Object> item : resultDataMap) {
                if (collect.isEmpty() || !collect.containsKey(item.get("medins_list_codg").toString())) {
                    InsureItemMatchDTO itemMatchDTO = new InsureItemMatchDTO();
                    itemMatchDTO.setInsureItemCode(MapUtils.get(item, "fixmedins_hilist_id")); // ??????????????????
                    itemMatchDTO.setInsureItemName(null); // ??????????????????
                    itemMatchDTO.setItemCode(MapUtils.get(item, "list_type")); //  ??????????????????
                    itemMatchDTO.setInsureRegCode(MapUtils.get(item, "insu_admdvs")); // ????????????????????????
                    itemMatchDTO.setHospItemCode(MapUtils.get(item, "medins_list_codg")); // ??????????????????????????????
                    itemMatchDTO.setHospItemName(MapUtils.get(item, "medins_list_name")); // ??????????????????????????????
                    itemMatchDTO.setHospItemPrepCode(MapUtils.get(item, "dosform")); // ??????????????????????????????
                    itemMatchDTO.setHospItemUnitCode(MapUtils.get(item, "prcunt")); // ??????
                    itemMatchDTO.setHospItemSpec(MapUtils.get(item, "spec")); // ??????
                    itemMatchDTO.setIsMatch(Constants.SF.S); // ????????????
                    itemMatchDTO.setIsValid(Constants.SF.S);
                    itemMatchDTO.setAuditCode(Constants.SF.S);
                    itemMatchDTO.setIsTrans(Constants.SF.S);
                    itemMatchDTO.setHospCode(hospCode);
                    insureItemDTOList.add(itemMatchDTO);
                }
            }
        }

        return map;
    }


    /**
     * @Method UP_3301
     * @Desrciption ?????????????????????????????????????????????
     * @Param map
     * @Author fuhui
     * @Date 2021/3/15 10:10
     * @Return
     **/
    @Override
    public Map<String, Object> UP_3301(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String crteName = MapUtils.get(map, "crteName");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        String hospItemType = MapUtils.get(map, "hospItemType");
        String crteId = MapUtils.get(map, "crteId");
        Date startDate = MapUtils.get(map, "startDate");
        Date endDate = MapUtils.get(map, "endDate");
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);

        InsureItemMatchDTO insureItemMatchDTO = new InsureItemMatchDTO();
        insureItemMatchDTO.setHospCode(hospCode);
        insureItemMatchDTO.setInsureRegCode(insureRegCode);
        insureItemMatchDTO.setHospItemType(hospItemType);
        insureItemMatchDTO.setIsValid(Constants.SF.S);
        insureItemMatchDTO.setIsTrans(Constants.SF.F);
        List<InsureItemMatchDTO> insureItemDTOList = insureItemMatchDAO.queryPageOrAll(insureItemMatchDTO);
        if (ListUtils.isEmpty(insureItemDTOList)) {
            throw new AppException("?????????????????????");
        }
        Map httpParam = new HashMap();
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_3301);  //????????????
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //????????????????????????
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //????????????????????????
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //??????????????????
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        Map<String, Object> catalogcompinMap = null;
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (!ListUtils.isEmpty(insureItemDTOList)) {
            for (InsureItemMatchDTO itemDTO : insureItemDTOList) {
                if (itemDTO == null || StringUtils.isEmpty(itemDTO.getInsureItemCode())) {
                    continue;
                } else {
                    catalogcompinMap = new HashMap<>();
                    catalogcompinMap.put("fixmedins_hilist_id", itemDTO.getHospItemCode());  // ??????????????????????????????
                    catalogcompinMap.put("fixmedins_hilist_name", itemDTO.getHospItemName()); // ??????????????????????????????
                    catalogcompinMap.put("list_type", itemDTO.getItemCode()); // ????????????
                    catalogcompinMap.put("med_list_codg", itemDTO.getInsureItemCode()); // ??????????????????
                    catalogcompinMap.put("begndate", DateUtils.format(startDate, DateUtils.Y_M_DH_M_S));  // ????????????
                    catalogcompinMap.put("enddate", DateUtils.format(endDate, DateUtils.Y_M_DH_M_S)); // ????????????
                    catalogcompinMap.put("aprvno", ""); // ????????????
                    catalogcompinMap.put("dosform", ""); // ??????
                    catalogcompinMap.put("exct_cont", ""); // ????????????
                    catalogcompinMap.put("item_cont", ""); // ????????????
                    catalogcompinMap.put("prcunt", ""); // ????????????
                    catalogcompinMap.put("spec", ""); // ??????
                    catalogcompinMap.put("pacspec", ""); // ????????????
                    catalogcompinMap.put("memo", ""); // ??????
                    catalogcompinMap.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); // ????????????
                    catalogcompinMap.put("medins_code", hospCode); // ????????????
                    catalogcompinMap.put("match_type", ""); // ????????????
                    catalogcompinMap.put("hosp_dosg", ""); // ??????????????????
                    catalogcompinMap.put("pric", ""); // ??????
                    catalogcompinMap.put("item_name", ""); // ??????????????????
                    catalogcompinMap.put("opter", crteId); // ???????????????
                    catalogcompinMap.put("opter_name", crteName); // ???????????????
                    catalogcompinMap.put("trade_code", itemDTO.getItemCode()); // ?????????????????????
                    catalogcompinMap.put("trade_name", ""); // ?????????????????????
                    catalogcompinMap.put("valid_flag", Constants.SF.S);
                    catalogcompinMap.put("chk_stas", "");
                    catalogcompinMap.put("staple_flag", ""); // ????????????
                    catalogcompinMap.put("chrg_sour", ""); // ??????????????????
                    mapList.add(catalogcompinMap);
                }
            }
        }
        Map<String, Object> dataMap = null;
        String resultJson = "";
        System.out.println("??????????????????????????????????????????????????????:" + mapList.size());
        map.put("msgName","??????????????????");
        map.put("isHospital","");
        map.put("visitId","");
        String url = insureConfigurationDTO.getUrl();
        if (mapList.size() > 100) {
            int toIndex = 100;
            for (int i = 0; i < mapList.size(); i += 100) {
                if (i + 100 > mapList.size()) {
                    toIndex = mapList.size() - i;
                }
                dataMap = new HashMap<>();
                List newList = mapList.subList(i, i + toIndex);
                dataMap.put("data", newList);
                Map<String, Object> resultMap = insureUnifiedCommonUtil.commonInsureUnified(hospCode, insureConfigurationDTO.getRegCode(), Constant.UnifiedPay.REGISTER.UP_3301, dataMap,map);
            }

        } else {
            dataMap = new HashMap<>();
            dataMap.put("data", mapList);
            Map<String, Object> resultMap = insureUnifiedCommonUtil.commonInsureUnified(hospCode, insureConfigurationDTO.getRegCode(), Constant.UnifiedPay.REGISTER.UP_3301, dataMap,map);
        }
        map.put("insureItemDTOList", insureItemDTOList);
        return map;
    }

    /**
     * @Method UP_3302
     * @Desrciption ?????????????????????????????????????????????
     * @Param ????????????????????????????????????????????? ????????????????????????
     * @Author fuhui
     * @Date 2021/3/29 14:2001
     * @Return
     **/
    @Override
    public Map<String, Object> UP_3302(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        String hospItemType = MapUtils.get(map, "hospItemType");
        InsureItemMatchDTO matchDTO = MapUtils.get(map, "itemMatchDTO");
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, Object> dataMap = null;
        if (matchDTO != null) {
            dataMap = new HashMap<>();
            dataMap.put("fixmedins_code", insureConfigurationDTO.getOrgCode());
            dataMap.put("fixmedins_hilist_id", matchDTO.getHospItemCode());
            dataMap.put("list_type", matchDTO.getItemCode());
            dataMap.put("med_list_codg", matchDTO.getInsureItemCode());
            mapList.add(dataMap);
        } else {

            InsureItemMatchDTO insureItemMatchDTO = new InsureItemMatchDTO();
            insureItemMatchDTO.setHospCode(hospCode);
            insureItemMatchDTO.setInsureRegCode(insureRegCode);
            insureItemMatchDTO.setIsTrans(Constants.SF.S);
            insureItemMatchDTO.setAuditCode(Constants.SF.S);
            insureItemMatchDTO.setHospItemType(hospItemType);
            List<InsureItemMatchDTO> insureItemDTOList = insureItemMatchDAO.queryPageOrAll(insureItemMatchDTO);
            if (!ListUtils.isEmpty(insureItemDTOList)) {
                for (InsureItemMatchDTO itemMatchDTO : insureItemDTOList) {
                    dataMap = new HashMap<>();
                    dataMap.put("fixmedins_code", insureConfigurationDTO.getOrgCode());
                    dataMap.put("fixmedins_hilist_id", itemMatchDTO.getHospItemCode());
                    dataMap.put("list_type", itemMatchDTO.getItemCode());
                    dataMap.put("med_list_codg", itemMatchDTO.getInsureItemCode());
                    mapList.add(dataMap);
                }
            } else {
                throw new AppException("??????????????????????????????");
            }
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("purcinfo", mapList);

        map.put("msgName","??????????????????");
        map.put("isHospital","");
        map.put("visitId","");
        Map<String, Object> resultMap = insureUnifiedCommonUtil.commonInsureUnified(hospCode, insureConfigurationDTO.getRegCode(), Constant.UnifiedPay.REGISTER.UP_3302, paramMap,map);
        Map<String, Object> outputMap = (Map<String, Object>) resultMap.get("output");
        List<Map<String, Object>> resultDataMap = (List<Map<String, Object>>) outputMap.get("sucessData");
        if (ListUtils.isEmpty(resultDataMap)) {
            throw new AppException("????????????");
        }
        if (matchDTO == null) {
            InsureItemMatchDTO insureItemMatchDTO = null;
            List<InsureItemMatchDTO> insureItemMatchDTOList = new ArrayList<>();
            for (Map<String, Object> item : resultDataMap) {
                insureItemMatchDTO = new InsureItemMatchDTO();
                insureItemMatchDTO.setHospCode(hospCode);
                insureItemMatchDTO.setInsureRegCode(insureRegCode);
                insureItemMatchDTO.setIsTrans(Constants.SF.F);
                insureItemMatchDTO.setAuditCode(Constants.SF.F);
                insureItemMatchDTO.setInsureItemCode(MapUtils.get(item, "med_list_codg"));
                insureItemMatchDTOList.add(insureItemMatchDTO);
            }
            map.put("insureItemMatchDTOList", insureItemMatchDTOList);
        }
        return map;
    }

    /**
     * @Method selectSettleQuery
     * @Desrciption ????????????????????????????????????
     * @Param map
     * @Author fuhui
     * @Date 2021/4/13 19:38
     * @Return
     **/
    @Override
    public Map<String, Object> selectSettleQuery(Map<String, Object> map) {
        String hospCode = map.get("hospCode").toString();
        /**
         * ????????????????????????
         */
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(map);
        InsureIndividualSettleDTO individualSettleDTO = new InsureIndividualSettleDTO();
        individualSettleDTO.setHospCode(hospCode);
        String visitId = (String) map.get("visitId");//??????id
        individualSettleDTO.setVisitId(visitId);
        individualSettleDTO.setState("0");
        individualSettleDTO = insureIndividualSettleDAO.getById(individualSettleDTO);

        /**
         * ???????????????url??????
         */
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(insureIndividualVisitDTO.getMedicineOrgCode()); // ??????????????????;
        insureConfigurationDTO = getInsureConfiguration(insureConfigurationDTO);


        Map httpParam = new HashMap();
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_1201);  //????????????
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //????????????????????????
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //????????????????????????
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //??????????????????
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("psn_no", insureIndividualVisitDTO.getAac001());
        dataMap.put("setl_id", insureIndividualVisitDTO.getInsureSettleId());
        dataMap.put("mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo());
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("input", dataMap);
        httpParam.put("data", inputMap);

        String json = JSONObject.toJSONString(httpParam);
        logger.info("?????????????????????:" + json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
        logger.info("?????????????????????:" + resultJson);
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson);
        if (!"0".equals(resultMap.get("infcode").toString())) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        Map<String, Object> outputMap = (Map<String, Object>) resultMap.get("output");
        Map<String, Object> resultDataMap = MapUtils.get(outputMap, "setlinfo");
        return resultDataMap;
    }

    /**
     * @Method selectPersonTreatment
     * @Desrciption ???????????????????????????????????????????????????
     * @Param map
     * @Author fuhui
     * @Date 2021/4/13 14:56
     * @Return
     **/

    @Override
    public Map<String, Object> selectPersonTreatment(Map<String, Object> map) {
        String hospCode = map.get("hospCode").toString();
        String regCode = MapUtils.get(map,"regCode");

        /**
         * ???????????????url??????
         */
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(regCode); // ??????????????????;
        insureConfigurationDTO = getInsureConfiguration(insureConfigurationDTO);
        if(insureConfigurationDTO == null){
            throw new AppException("??????"+regCode+"??????????????????????????????????????????");
        }
        map.put("regCode",insureConfigurationDTO.getCode());
        Map<String, Object> data = insureIndividualBasicService_consumer.queryInsureInfo(map).getData();
        String psnNo = MapUtils.get(data, "psnNo");

        Map<String, Object> dataMap = new HashMap<>();
        if(StringUtils.isEmpty(psnNo)){
            throw new AppException("??????????????????????????????");
        }
        dataMap.put("psn_no", psnNo);
        if(StringUtils.isEmpty(MapUtils.get(map,"insutype"))){
            throw new AppException("????????????????????????");
        }
        dataMap.put("insutype", MapUtils.get(map,"insutype"));
        dataMap.put("fixmedins_code", insureConfigurationDTO.getOrgCode());
        if(StringUtils.isEmpty(MapUtils.get(map,"medType"))){
            throw new AppException("????????????????????????");
        }
        dataMap.put("med_type", MapUtils.get(map,"medType"));
        dataMap.put("begntime", MapUtils.get(map, "startDate"));
        dataMap.put("endtime", null);
        dataMap.put("dise_codg", "");
        dataMap.put("dise_name", "");
        dataMap.put("oprn_oprt_code", null);
        dataMap.put("oprn_oprt_name", null);
        dataMap.put("matn_type", null);
        dataMap.put("birctrl_type", null);
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("data", dataMap);
        map.put("msgName","????????????????????????");
        map.put("isHospital","");
        map.put("visitId","");
        Map<String, Object> resultMap = insureUnifiedCommonUtil.commonInsureUnified(hospCode, insureConfigurationDTO.getRegCode(), Constant.UnifiedPay.REGISTER.UP_2001, dataMap,map);
        Map<String, Object> outputMap = (Map<String, Object>) resultMap.get("output");
        List<Map<String, Object>> resultDataMap = MapUtils.get(outputMap, "trtinfo");
        map.put("resultDataMap", resultDataMap);
        return map;
    }


    /**
     * @Method UP_1201
     * @Desrciption ???????????????????????????????????????????????????
     * @Param
     * @Author fuhui
     * @Date 2021/4/13 14:56
     * @Return
     **/
    public Map<String, Object> getMedisnInfo(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("hospCode",hospCode);
        paramMap.put("code","HOSP_INSURE_CODE");
        String orgCode = "";
        SysParameterDTO data = sysParameterService_consumer.getParameterByCode(paramMap).getData();
        if(data !=null){
            orgCode = data.getValue();
        }else{
            throw  new AppException("????????????????????????????????????");
        }
        String insureServiceType = MapUtils.get(map, "insureServiceType");
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(orgCode);
        insureConfigurationDTO.setRegCode(MapUtils.get(map,"regCode"));
        insureConfigurationDTO.setIsValid(Constants.SF.S);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);

        Map httpParam = new HashMap();
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_1201);  //????????????
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //????????????????????????
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //????????????????????????
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //??????????????????
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("fixmedins_type", insureServiceType);
        dataMap.put("fixmedins_name", MapUtils.get(map, "medinsName"));
        dataMap.put("fixmedins_code", MapUtils.get(map, "medinsCode"));
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("medinsinfo", dataMap);
        httpParam.put("input", inputMap);

        String json = JSONObject.toJSONString(httpParam);
        logger.info("????????????????????????????????????:" + json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
        if (StringUtils.isEmpty(resultJson)) {
            throw new AppException("????????????????????????????????????");
        }
        logger.info("??????????????????????????????????????????:" + resultJson);
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson);
        if ("999".equals(MapUtils.get(resultMap, "code"))) {
            throw new AppException((String) resultMap.get("msg"));
        }
        if (!MapUtils.get(resultMap, "infcode").equals("0")) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        Map<String, Object> outputMap = (Map<String, Object>) resultMap.get("output");
        List<Map<String, Object>> resultDataMap = MapUtils.get(outputMap, "medinsinfo");
        return resultDataMap.get(0);
    }


    /**
     * @Method getInsureConfiguration
     * @Desrciption ????????????????????????????????????????????????????????????url?????????????????????
     * @Param
     * @Author fuhui
     * @Date 2021/3/3 10:52
     * @Return
     **/
    public InsureConfigurationDTO getInsureConfiguration(InsureConfigurationDTO insureConfigurationDTO) {
        return insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
    }

    /**
     * @Method commonGetVisitInfo
     * @Desrciption ????????????????????????????????????????????????????????????????????????
     * @Param map
     * @Author fuhui
     * @Date 2021/2/14 10:57
     * @Return InsureIndividualVisitDTO
     **/
    public InsureIndividualVisitDTO commonGetVisitInfo(Map<String, Object> map) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        String hospCode = (String) map.get("hospCode");//????????????
        String visitId = (String) map.get("visitId");//??????id
        Map<String, Object> insureVisitParam = new HashMap<String, Object>();
        insureVisitParam.put("id", visitId);
        insureVisitParam.put("hospCode", hospCode);
        insureIndividualVisitDTO = insureIndividualVisitDAO.getInsureIndividualVisitById(insureVisitParam);
        if (insureIndividualVisitDTO == null || StringUtils.isEmpty(insureIndividualVisitDTO.getId())) {
            throw new AppException("??????????????????????????????????????????????????????");
        }
        return insureIndividualVisitDTO;
    }

    /**
     * @param insureItemDTO
     * @Method queryPageUnifiedItem
     * @Desrciption ??????????????????????????????????????????????????????????????????
     * @Param
     * @Author fuhui
     * @Date 2021/4/10 1:28
     * @Return
     */
    @Override
    public PageDTO queryPageUnifiedItem(InsureItemDTO insureItemDTO) {
        PageHelper.startPage(insureItemDTO.getPageNo(), insureItemDTO.getPageSize());
        List<InsureItemDTO> insureItemDTOList = insureItemDAO.queryPageUnifiedItem(insureItemDTO);
        return PageDTO.of(insureItemDTOList);
    }

    /**
     * @param insureItemMatchDTO
     * @Method insertUnifiedHandMatch
     * @Desrciption ????????????
     * @Param hospItemType:??????????????????????????????????????????????????????
     * @Author liaojiguang
     * @Date 2021/4/10 1:45
     * @Return
     */
    @Override
    public Boolean insertUnifiedHandMatch(InsureItemMatchDTO insureItemMatchDTO) {
        String hospItemType = insureItemMatchDTO.getHospItemType();
        if (Constants.XMLB.YP.equals(hospItemType) || Constants.XMLB.CL.equals(hospItemType) || Constants.XMLB.XM.equals(hospItemType)) {
            insureItemMatchDTO.setId(insureItemMatchDTO.getInsureId());
            InsureItemDTO insureItemDTO = insureItemDAO.getInsureItemById(insureItemMatchDTO);
            if (insureItemDTO == null) {
                throw new AppException("??????????????????????????????????????????????????????");
            }
            insureItemMatchDTO.setId(insureItemMatchDTO.getHospId());
            insureItemMatchDTO.setItemCode(insureItemDTO.getItemMark()); // ??????????????????
            insureItemMatchDTO.setInsureItemName(insureItemDTO.getItemName()); // ????????????????????????
            insureItemMatchDTO.setInsureItemCode(insureItemDTO.getItemCode()); // ????????????????????????
            insureItemMatchDTO.setInsureItemType(insureItemDTO.getItemType()); // ????????????????????????
            insureItemMatchDTO.setInsureItemSpec(insureItemDTO.getItemSpec()); // ????????????????????????
            insureItemMatchDTO.setInsureItemUnitCode(insureItemDTO.getItemUnitCode()); //????????????????????????
            insureItemMatchDTO.setInsureItemPrepCode(insureItemDTO.getItemDosage()); //????????????????????????
            insureItemMatchDTO.setDeductible(insureItemDTO.getDeductible()); // ????????????
            insureItemMatchDTO.setCheckPrice(insureItemDTO.getCheckPrice()); // ??????
            insureItemMatchDTO.setIsMatch(Constants.SF.S); // ????????????
            insureItemMatchDTO.setIsValid(Constants.SF.S); // ????????????
            insureItemMatchDTO.setIsTrans(Constants.SF.F); // ????????????
            insureItemMatchDTO.setAuditCode(Constants.SF.F); // ????????????
            insureItemMatchDTO.setTakeDate(insureItemDTO.getTakeDate()); // ????????????
            insureItemMatchDTO.setLoseDate(insureItemDTO.getLoseDate()); // ????????????
            insureItemMatchDAO.updateInsureItemMatch(insureItemMatchDTO);
        } else {
            insureItemMatchDTO.setId(insureItemMatchDTO.getInsureId());
            InsureDiseaseDTO insureDiseaseDTO = insureDiseaseDAO.getInsureDiseaseById(insureItemMatchDTO);
            if (insureDiseaseDTO == null) {
                throw new AppException("?????????????????????????????????");
            }

            InsureDiseaseMatchDTO insureDiseaseMatchDTO = new InsureDiseaseMatchDTO();
            insureDiseaseMatchDTO.setId(insureItemMatchDTO.getHospId());
            insureDiseaseMatchDTO.setHospCode(insureItemMatchDTO.getHospCode());
            insureDiseaseMatchDTO.setIsMatch(Constants.SF.S);
            insureDiseaseMatchDTO.setIsTrans(Constants.SF.S);
            insureDiseaseMatchDTO.setAuditCode(Constants.SF.S);
            insureDiseaseMatchDTO.setInsureIllnessName(insureDiseaseDTO.getInsureIllnessName());
            insureDiseaseMatchDTO.setInsureIllnessCode(insureDiseaseDTO.getInsureIllnessCode());
            insureDiseaseMatchDAO.updateDisease(insureDiseaseMatchDTO);
        }
        return true;
    }

   /* *//**
     * @Method insertHandlerItem
     * @Desrciption ????????????????????????????????????????????????
     * @Param
     * @Author fuhui
     * @Date 2021/4/10 2:18
     * @Return
     **//*
    private void insertHandlerMaterial(InsureItemDTO itemDTO, BaseMaterialDTO baseMaterialDTO, InsureItemMatchDTO itemMatchDTO) {
        List<InsureItemMatchDTO> list = insureItemMatchDAO.selectById(itemMatchDTO);
        if (!ListUtils.isEmpty(list)) {
            throw new AppException("??????" + list.get(0).getHospItemName() + "???????????????");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("id", SnowflakeUtils.getId());
        map.put("insureRegCode", itemMatchDTO.getInsureRegCode());
        map.put("hospCode", itemMatchDTO.getHospCode());
        map.put("itemCode", "301"); // ??????????????????
        map.put("hospItemId", baseMaterialDTO.getId()); // ????????????id
        map.put("hospItemName", baseMaterialDTO.getName()); // ??????????????????
        map.put("hospItemCode", baseMaterialDTO.getCode()); // ??????????????????
        map.put("hospItemType", Constants.XMLB.CL); // ??????????????????
        map.put("hospItemSpec", baseMaterialDTO.getSpec()); // ??????????????????
        map.put("hospItemUnitCode", baseMaterialDTO.getUnitCode()); // ??????????????????
        map.put("hospItemUnitCode", null); // ??????????????????
        map.put("hospItemPrice", baseMaterialDTO.getPrice()); // ??????????????????
        map.put("insureItemName", itemDTO.getItemName()); // ????????????????????????
        map.put("insureItemCode", itemDTO.getItemCode()); // ????????????????????????
        map.put("insureItemType", itemDTO.getItemType()); // ????????????????????????
        map.put("insureItemSpec", itemDTO.getItemSpec()); // ????????????????????????
        map.put("insureItemUnitCode", itemDTO.getItemUnitCode()); // ????????????????????????
        map.put("insureItemPrepCode", itemDTO.getItemDosage()); // ????????????????????????
        map.put("manufacturer", itemDTO.getProd());
        map.put("takeDate", itemDTO.getTakeDate());
        map.put("loseDate", itemDTO.getLoseDate());
        map.put("isValid", Constants.SF.S);
        map.put("isTrans", Constants.SF.F);
        map.put("auditCode", Constants.SF.F);
        map.put("isMatch", Constants.SF.S);
        map.put("crteTime", itemMatchDTO.getCrteTime());
        map.put("crteName", itemMatchDTO.getCrteName());
        map.put("crteId", itemMatchDTO.getCrteId());
        map.put("crteTime", DateUtils.getNow());
        insureItemMatchDAO.insertHandMatch(map);
    }

    *//**
     * @Method insertHandlerItem
     * @Desrciption ????????????????????????????????????????????????
     * @Param
     * @Author fuhui
     * @Date 2021/4/10 2:18
     * @Return
     **//*
    private void insertHandlerItem(InsureItemDTO itemDTO, BaseItemDTO baseItemDTO, InsureItemMatchDTO itemMatchDTO) {
        List<InsureItemMatchDTO> list = insureItemMatchDAO.selectById(itemMatchDTO);
        if (!ListUtils.isEmpty(list)) {
            throw new AppException("??????" + list.get(0).getHospItemName() + "???????????????");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id", SnowflakeUtils.getId());
        map.put("hospCode", itemMatchDTO.getHospCode());
        map.put("insureRegCode", itemMatchDTO.getInsureRegCode());
        map.put("itemCode", "201"); // ??????????????????
        map.put("hospItemId", baseItemDTO.getId()); // ????????????id
        map.put("hospItemName", baseItemDTO.getName()); // ??????????????????
        map.put("hospItemCode", baseItemDTO.getCode()); // ??????????????????
        map.put("hospItemType", Constants.XMLB.XM); // ??????????????????
        map.put("hospItemSpec", baseItemDTO.getSpec()); // ??????????????????
        map.put("hospItemUnitCode", baseItemDTO.getUnitCode()); // ??????????????????
        map.put("hospItemUnitCode", null); // ??????????????????
        map.put("hospItemPrice", baseItemDTO.getPrice()); // ??????????????????
        map.put("insureItemName", itemDTO.getItemName()); // ????????????????????????
        map.put("insureItemCode", itemDTO.getItemCode()); // ????????????????????????
        map.put("insureItemType", itemDTO.getItemType()); // ????????????????????????
        map.put("insureItemSpec", itemDTO.getItemSpec()); // ????????????????????????
        map.put("insureItemUnitCode", itemDTO.getItemUnitCode()); // ????????????????????????
        map.put("insureItemPrepCode", itemDTO.getItemDosage()); // ????????????????????????
        map.put("manufacturer", itemDTO.getProd());
        map.put("takeDate", baseItemDTO.getTakeDate());
        map.put("isValid", Constants.SF.S);
        map.put("isTrans", Constants.SF.F);
        map.put("auditCode", Constants.SF.F);
        map.put("isMatch", Constants.SF.S);
        map.put("crteTime", itemMatchDTO.getCrteTime());
        map.put("crteName", itemMatchDTO.getCrteName());
        map.put("crteId", itemMatchDTO.getCrteId());
        map.put("crteTime", DateUtils.getNow());
        insureItemMatchDAO.insertHandMatch(map);
    }*/

    /**
     * @param insureItemDTO
     * @Method queryInsureItemAll
     * @Desrciption :??????????????????????????????????????????????????????
     * @Param insureItemDTO???itemName:???????????????itemCode??????
     * @Author fuhui
     * @Date 2021/1/27 11:17
     * @Return
     */
    @Override
    public PageDTO queryInsureItemAll(InsureItemDTO insureItemDTO) {
        String downLoadType = insureItemDTO.getDownLoadType();
        if (Constant.UnifiedPay.JBLIST.containsKey(downLoadType)) {
            InsureDiseaseDTO insureDiseaseDTO = new InsureDiseaseDTO();
            insureDiseaseDTO.setHospCode(insureItemDTO.getHospCode());
            if (StringUtils.isEmpty(insureItemDTO.getDownLoadType())) {
                insureDiseaseDTO.setDownLoadType("1307");
            } else {
                insureDiseaseDTO.setDownLoadType(insureItemDTO.getDownLoadType());
            }
            insureDiseaseDTO.setKeyword(insureItemDTO.getKeyword());
            insureDiseaseDTO.setInsureRegCode(insureItemDTO.getInsureRegCode());
            PageHelper.startPage(insureItemDTO.getPageNo(), insureItemDTO.getPageSize());
            List<InsureDiseaseDTO> insureDiseaseDTOList = insureDiseaseDAO.queryAll(insureDiseaseDTO);
            return PageDTO.of(insureDiseaseDTOList);
        } else {
            if (StringUtils.isEmpty(downLoadType)) {
                insureItemDTO.setDownLoadType("1301");
            }
            PageHelper.startPage(insureItemDTO.getPageNo(), insureItemDTO.getPageSize());
            List<InsureItemDTO> insureItemDTOList = insureItemDAO.queryInsureItemAll(insureItemDTO);
            return PageDTO.of(insureItemDTOList);
        }
    }

    /**
     * @param map
     * @Method queryHospItemPage
     * @Desrciption :?????? ???????????????????????????????????????
     * @Author liaojiguang
     * @Date 2021/8/24 10:05
     * @Return
     */
    @Override
    public PageDTO queryHospItemPage(Map<String, Object> map) {
        PageDTO pageDTO;
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        String itemCode = MapUtils.get(map, "itemCode");
        String hospCode = MapUtils.get(map, "hospCode");
        String keyWord = MapUtils.get(map, "keyword");
        Integer pageSize = Integer.valueOf(map.get("pageSize").toString());
        Integer pageNo = Integer.valueOf(map.get("pageNo").toString());

        // ??????????????????
        if (Constants.XMLB.YP.equals(itemCode) || Constants.XMLB.XM.equals(itemCode) || Constants.XMLB.CL.equals(itemCode)) {
            Map<String, Object> selectItemMap = new HashMap<>();
            InsureItemMatchDTO insureItemMatchDTO = new InsureItemMatchDTO();
            selectItemMap.put("hospCode", hospCode);
            selectItemMap.put("insureRegCode", insureRegCode);
            insureItemMatchDTO.setHospCode(hospCode);
            insureItemMatchDTO.setInsureRegCode(insureRegCode);
            insureItemMatchDTO.setIsMatch("0");
            insureItemMatchDTO.setPageNo(pageNo);
            insureItemMatchDTO.setPageSize(pageSize);
            insureItemMatchDTO.setHospItemType(itemCode);
            insureItemMatchDTO.setKeyword(keyWord);
            selectItemMap.put("insureItemMatchDTO", insureItemMatchDTO);
            WrapperResponse<PageDTO> wr = insureItemMatchService_consumer.queryUnMacthAllPage(selectItemMap);
            pageDTO = wr.getData();
        } else {
            Map<String, Object> selectDiseaseMap = new HashMap<>();
            InsureDiseaseMatchDTO insureDiseaseMatchDTO = new InsureDiseaseMatchDTO();
            insureDiseaseMatchDTO.setHospCode(hospCode);
            insureDiseaseMatchDTO.setInsureRegCode(insureRegCode);
            insureDiseaseMatchDTO.setIsMatch("0");
            insureDiseaseMatchDTO.setAuditCode("0");
            insureDiseaseMatchDTO.setPageNo(pageNo);
            insureDiseaseMatchDTO.setPageSize(pageSize);
            insureDiseaseMatchDTO.setKeyword(keyWord);
            selectDiseaseMap.put("hospCode", hospCode);
            selectDiseaseMap.put("insureDiseaseMatchDTO", insureDiseaseMatchDTO);
            WrapperResponse<PageDTO> wr = insureDiseaseMatchService_consumer.queryUnMacthAllPage(selectDiseaseMap);
            pageDTO = wr.getData();
        }
        return pageDTO;
    }

    /**
     * @param itemMatchDTO
     * @Method insertUnifiedAutoMatch
     * @Desrciption ??????????????????
     * @Param
     * @Author liaojiguang update
     * @Date 2021-08-23
     * @Return
     */
    @Override
    public Integer insertUnifiedAutoMatch(InsureItemMatchDTO itemMatchDTO) {
        // ??????????????????
        String isUpay = itemMatchDTO.getIsUpay();
        String itemCode = itemMatchDTO.getMatchCode();
        String insureRegCode = itemMatchDTO.getRegCode();
        String hospCode = itemMatchDTO.getHospCode();
        Integer integer = 0;
        if (Constants.XMLB.YP.equals(itemCode) || Constants.XMLB.CL.equals(itemCode) || Constants.XMLB.XM.equals(itemCode)) {
            if (Constants.SF.F.equals(isUpay)) {
                integer = this.insertInsureMatch(itemMatchDTO);
            } else {
                InsureItemMatchDTO insureItemMatchDTO = new InsureItemMatchDTO();
                insureItemMatchDTO.setHospCode(hospCode);
                insureItemMatchDTO.setInsureRegCode(insureRegCode);
                insureItemMatchDTO.setIsMatch("0");
                insureItemMatchDTO.setHospItemType(itemCode);
                List<InsureItemMatchDTO> list = insureItemMatchDAO.queryUnMacthAllDtoPage(insureItemMatchDTO);
                if (ListUtils.isEmpty(list)) {
                    throw new AppException("???????????????????????????");
                }

                InsureItemDTO insureItemDTO = new InsureItemDTO();
                insureItemDTO.setHospCode(hospCode);
                insureItemDTO.setInsureRegCode(insureRegCode);
                List<InsureItemDTO> insureDiseaseDTOList = insureItemDAO.queryPageUnifiedItem(insureItemDTO);
                if (ListUtils.isEmpty(insureDiseaseDTOList)) {
                    throw new AppException("??????????????????????????????!");
                }
                //?????????down_load_type????????????????????????????????????????????????????????????
                insureDiseaseDTOList = insureDiseaseDTOList.stream().filter(o -> ObjectUtil.isNotEmpty(o.getDownLoadType())).collect(Collectors.toList());
                List<InsureItemMatchDTO> itemMatchDTOList = handItemAutoMatch(list, insureDiseaseDTOList,itemMatchDTO);
                insureItemMatchDAO.deleteInsureItemInfos(insureItemMatchDTO);
                insureItemMatchDAO.insertMatchItem(itemMatchDTOList);
                for (InsureItemMatchDTO dto : itemMatchDTOList) {
                    if ("1".equals(dto.getIsMatch())) {
                        integer ++;
                    }
                }
            }
        } else {
            InsureDiseaseMatchDTO insureDiseaseMatchDTO = new InsureDiseaseMatchDTO();
            insureDiseaseMatchDTO.setHospCode(hospCode);
            insureDiseaseMatchDTO.setInsureRegCode(insureRegCode);
            insureDiseaseMatchDTO.setIsMatch("0");
            insureDiseaseMatchDTO.setAuditCode("0");
            List<InsureDiseaseMatchDTO> list = insureDiseaseMatchDAO.queryPageOrAll(insureDiseaseMatchDTO);
            if (ListUtils.isEmpty(list)) {
                throw new AppException("???????????????????????????");
            }
            InsureDiseaseDTO insureDiseaseDTO = new InsureDiseaseDTO();
            insureDiseaseDTO.setHospCode(hospCode);
            insureDiseaseDTO.setInsureRegCode(insureRegCode);
            List<InsureDiseaseDTO> insureDiseaseDTOList = insureDiseaseDAO.queryAll(insureDiseaseDTO);
            if (ListUtils.isEmpty(insureDiseaseDTOList)) {
                throw new AppException("??????????????????????????????!");
            }
            //?????????down_load_type????????????????????????????????????????????????????????????
            insureDiseaseDTOList = insureDiseaseDTOList.stream().filter(o -> ObjectUtil.isNotEmpty(o.getDownLoadType())).collect(Collectors.toList());
            List<InsureDiseaseMatchDTO> diseaseMatchDTOList = handDiseaseAutoMatch(list, insureDiseaseDTOList,itemMatchDTO);
            insureDiseaseMatchDAO.deleteInsureDiseaseInfos(insureDiseaseMatchDTO);
            insureDiseaseMatchDAO.insertMatchDisease(diseaseMatchDTOList);

            for (InsureDiseaseMatchDTO dto : diseaseMatchDTOList) {
                if ("1".equals(dto.getAuditCode())) {
                    integer ++;
                }
            }
        }
        return integer;
    }

    /**
     * @param itemMatchDTO
     * @Method queryPageInsureItemMatch
     * @Desrciption ????????????????????????????????????????????????
     * @Param
     * @Author fuhui
     * @Date 2021/4/12 21:51
     * @Return
     */
    @Override
    public PageDTO queryPageInsureItemMatch(InsureItemMatchDTO itemMatchDTO) {
        if (!"5".equals(itemMatchDTO.getHospItemType())) {
            PageHelper.startPage(itemMatchDTO.getPageNo(), itemMatchDTO.getPageSize());
            List<InsureItemMatchDTO> insureItemMatchDTOList = insureItemMatchDAO.queryPageOrAll(itemMatchDTO);
            return PageDTO.of(insureItemMatchDTOList);
        } else {
            //??????????????????
            InsureDiseaseMatchDTO insureDiseaseMatchDTO = new InsureDiseaseMatchDTO();
            insureDiseaseMatchDTO.setHospCode(itemMatchDTO.getHospCode());
            insureDiseaseMatchDTO.setKeyword(itemMatchDTO.getKeyword());
            insureDiseaseMatchDTO.setInsureRegCode(itemMatchDTO.getInsureRegCode());
            PageHelper.startPage(insureDiseaseMatchDTO.getPageNo(), insureDiseaseMatchDTO.getPageSize());
            List<InsureDiseaseMatchDTO> insureDiseaseMatchDTOS = insureDiseaseMatchDAO.queryPageOrAll(insureDiseaseMatchDTO);
            return PageDTO.of(insureDiseaseMatchDTOS);
        }
    }

    /**
     * @param diseaseDTOList
     * @param insureDiseaseDTOList
     * @Method handDiseaseAutoMatch
     * @Desrciption ????????????????????????
     * @Param baseDrugDTOList:????????????????????????
     * insureItemDTOList???????????????????????????????????????
     * map?????????????????????????????????????????????????????? ????????????id?????????
     * @Author fuhui
     * @Date 2021/1/27 21:12
     * @Return
     */
    private List<InsureDiseaseMatchDTO> handDiseaseAutoMatch(List<InsureDiseaseMatchDTO> diseaseDTOList, List<InsureDiseaseDTO> insureDiseaseDTOList, InsureItemMatchDTO itemMatchDTO) {
        String matchType = itemMatchDTO.getMatchType();
        Map<String, InsureDiseaseDTO> collect;
        if (Constant.UnifiedPay.MATCHCODE.MC.equals(matchType)) {
            collect = insureDiseaseDTOList.stream().collect(Collectors.toMap(InsureDiseaseDTO::getInsureIllnessName, Function.identity(), (k1, k2) -> k1));
            for (InsureDiseaseMatchDTO insureDiseaseMatchDTO : diseaseDTOList) {
                if (!collect.isEmpty() && collect.containsKey(insureDiseaseMatchDTO.getHospIllnessName())) {
                    InsureDiseaseDTO insureDiseaseDTO = collect.get(insureDiseaseMatchDTO.getHospIllnessName());
                    insureDiseaseMatchDTO.setId(SnowflakeUtils.getId()); //??????id
                    insureDiseaseMatchDTO.setHospCode(insureDiseaseDTO.getHospCode()); //  ????????????
                    insureDiseaseMatchDTO.setInsureRegCode(insureDiseaseDTO.getInsureRegCode()); // ??????????????????
                    insureDiseaseMatchDTO.setInsureIllnessId(insureDiseaseDTO.getInsureIllnessId()); // ??????????????????ID
                    insureDiseaseMatchDTO.setInsureIllnessCode(insureDiseaseDTO.getInsureIllnessCode()); // ????????????ICD??????
                    insureDiseaseMatchDTO.setInsureIllnessName(insureDiseaseDTO.getInsureIllnessName()); // ????????????ICD??????
                    insureDiseaseMatchDTO.setIsMatch(Constants.SF.S); // ????????????
                    insureDiseaseMatchDTO.setIsTrans(Constants.SF.S); // ????????????
                    insureDiseaseMatchDTO.setAuditCode(Constants.SF.S); //??????????????????
                    insureDiseaseMatchDTO.setRemark(insureDiseaseDTO.getRemark()); // ??????
                    insureDiseaseMatchDTO.setTypeCode(insureDiseaseDTO.getDownLoadType());
                }
            }
        } else {
            collect = insureDiseaseDTOList.stream().collect(Collectors.toMap(InsureDiseaseDTO::getInsureIllnessCode, Function.identity(), (k1, k2) -> k1));
            for (InsureDiseaseMatchDTO insureDiseaseMatchDTO : diseaseDTOList) {
                if (!collect.isEmpty() && collect.containsKey(insureDiseaseMatchDTO.getHospIllnessCode())) {
                    InsureDiseaseDTO insureDiseaseDTO = collect.get(insureDiseaseMatchDTO.getHospIllnessCode());
                    insureDiseaseMatchDTO.setId(SnowflakeUtils.getId()); //??????id
                    insureDiseaseMatchDTO.setHospCode(insureDiseaseDTO.getHospCode()); //  ????????????
                    insureDiseaseMatchDTO.setInsureRegCode(insureDiseaseDTO.getInsureRegCode()); // ??????????????????
                    insureDiseaseMatchDTO.setInsureIllnessId(insureDiseaseDTO.getInsureIllnessId()); // ??????????????????ID
                    insureDiseaseMatchDTO.setInsureIllnessCode(insureDiseaseDTO.getInsureIllnessCode()); // ????????????ICD??????
                    insureDiseaseMatchDTO.setInsureIllnessName(insureDiseaseDTO.getInsureIllnessName()); // ????????????ICD??????
                    insureDiseaseMatchDTO.setIsMatch(Constants.SF.S); // ????????????
                    insureDiseaseMatchDTO.setIsTrans(Constants.SF.S); // ????????????
                    insureDiseaseMatchDTO.setAuditCode(Constants.SF.S); //??????????????????
                    insureDiseaseMatchDTO.setRemark(insureDiseaseDTO.getRemark()); // ??????
                    insureDiseaseMatchDTO.setTypeCode(insureDiseaseDTO.getDownLoadType());
                }
            }
        }
        return diseaseDTOList;
    }

    /**
     * @Method handMaterialAutoMatch
     * @Desrciption ????????????????????????
     * @Param baseDrugDTOList:????????????????????????
     * insureItemDTOList???????????????????????????????????????
     * map?????????????????????????????????????????????????????? ????????????id?????????
     * @Author fuhui
     * @Date 2021/1/27 21:12
     * @Return
     **/
    private List<InsureItemMatchDTO> handMaterialAutoMatch(List<BaseMaterialDTO> baseMaterialDTOList, List<InsureItemDTO> insureItemDTOList, Map map) {
        InsureItemMatchDTO itemMatchDTO = MapUtils.get(map, "itemMatchDTO");
        String userId = itemMatchDTO.getCrteId();
        String userName = itemMatchDTO.getCrteName();
        String matchType = itemMatchDTO.getMatchType();
        List<InsureItemMatchDTO> insureItemMatchDTOList = new ArrayList<>();
        Map<String, InsureItemDTO> collect = null;
        if (Constant.UnifiedPay.MATCHCODE.MC.equals(matchType)) {
            collect = insureItemDTOList.stream().collect(Collectors.toMap(InsureItemDTO::getItemName, Function.identity(), (k1, k2) -> k1));
            for (BaseMaterialDTO baseMaterialDTO : baseMaterialDTOList) {
                if (!collect.isEmpty() && collect.containsKey(baseMaterialDTO.getName())) {
                    InsureItemMatchDTO insureItemMatchDTO = new InsureItemMatchDTO();
                    insureItemMatchDTO.setId(SnowflakeUtils.getId());
                    insureItemMatchDTO.setHospCode(baseMaterialDTO.getHospCode()); //
                    insureItemMatchDTO.setInsureRegCode(collect.get(baseMaterialDTO.getName()).getInsureRegCode());
                    insureItemMatchDTO.setItemCode(collect.get(baseMaterialDTO.getName()).getItemMark()); // ??????????????????  --- ??????????????? ???????????????
                    insureItemMatchDTO.setMolssItemId(null); // ???????????????id
                    insureItemMatchDTO.setPqccItemId(null); // ???????????????id
                    insureItemMatchDTO.setHospItemName(baseMaterialDTO.getName()); //??????????????????
                    insureItemMatchDTO.setHospItemId(baseMaterialDTO.getId()); // ????????????id
                    insureItemMatchDTO.setHospItemCode(baseMaterialDTO.getCode()); // ??????????????????
                    insureItemMatchDTO.setHospItemType(Constants.XMLB.CL); // ??????????????????
                    insureItemMatchDTO.setHospItemSpec(baseMaterialDTO.getSpec()); //  ??????????????????
                    insureItemMatchDTO.setHospItemUnitCode(baseMaterialDTO.getUnitCode()); // ??????????????????
                    insureItemMatchDTO.setHospItemPrepCode(null); //??????????????????
                    insureItemMatchDTO.setHospItemPrice(baseMaterialDTO.getPrice()); //??????????????????
                    insureItemMatchDTO.setLmtUserFlag(collect.get(baseMaterialDTO.getName()).getLmtUsedFlag()); // ????????????????????????
                    insureItemMatchDTO.setInsureItemName(collect.get(baseMaterialDTO.getName()).getItemName()); // ????????????????????????
                    insureItemMatchDTO.setInsureItemCode(collect.get(baseMaterialDTO.getName()).getItemCode()); // ????????????????????????
                    insureItemMatchDTO.setInsureItemType(collect.get(baseMaterialDTO.getName()).getItemType()); // ????????????????????????
                    insureItemMatchDTO.setInsureItemSpec(collect.get(baseMaterialDTO.getName()).getItemSpec()); // ????????????????????????
                    insureItemMatchDTO.setInsureItemUnitCode(collect.get(baseMaterialDTO.getName()).getItemUnitCode()); //????????????????????????
                    insureItemMatchDTO.setInsureItemPrepCode(collect.get(baseMaterialDTO.getName()).getItemDosage()); //????????????????????????
                    insureItemMatchDTO.setInsureItemPrice(null); // ????????????????????????
                    insureItemMatchDTO.setDeductible(collect.get(baseMaterialDTO.getName()).getDeductible()); // ????????????
                    insureItemMatchDTO.setStandardCode(null); // ?????????
                    insureItemMatchDTO.setCheckPrice(collect.get(baseMaterialDTO.getName()).getCheckPrice()); // ??????
                    insureItemMatchDTO.setManufacturer(null); // ????????????
                    insureItemMatchDTO.setAuditCode(Constants.SF.F); // ??????????????????
                    insureItemMatchDTO.setIsMatch(Constants.SF.S); // ????????????
                    insureItemMatchDTO.setIsTrans(Constants.SF.F); // ????????????
                    insureItemMatchDTO.setIsValid(baseMaterialDTO.getIsValid()); // ????????????
                    insureItemMatchDTO.setTakeDate(collect.get(baseMaterialDTO.getName()).getTakeDate()); // ????????????
                    insureItemMatchDTO.setLoseDate(collect.get(baseMaterialDTO.getName()).getLoseDate()); // ????????????
                    insureItemMatchDTO.setPym(baseMaterialDTO.getPym()); // ?????????
                    insureItemMatchDTO.setWbm(baseMaterialDTO.getWbm()); // ?????????
                    insureItemMatchDTO.setCrteId(userId); // ?????????ID
                    insureItemMatchDTO.setCrteTime(DateUtils.getNow()); // ???????????????
                    insureItemMatchDTO.setCrteName(userName); // ????????????
                    insureItemMatchDTOList.add(insureItemMatchDTO);
                }
            }
        } else {
            collect = insureItemDTOList.stream().collect(Collectors.toMap(InsureItemDTO::getItemCode, Function.identity(), (k1, k2) -> k1));
            for (BaseMaterialDTO baseMaterialDTO : baseMaterialDTOList) {
                if (!collect.isEmpty() && collect.containsKey(baseMaterialDTO.getNationCode()) && !StringUtils.isEmpty(baseMaterialDTO.getNationCode())) {
                    InsureItemMatchDTO insureItemMatchDTO = new InsureItemMatchDTO();
                    insureItemMatchDTO.setId(SnowflakeUtils.getId());
                    insureItemMatchDTO.setHospCode(baseMaterialDTO.getHospCode()); //
                    insureItemMatchDTO.setInsureRegCode(collect.get(baseMaterialDTO.getNationCode()).getInsureRegCode());
                    insureItemMatchDTO.setItemCode(collect.get(baseMaterialDTO.getNationCode()).getItemMark()); // ??????????????????  --- ??????????????? ???????????????
                    insureItemMatchDTO.setMolssItemId(null); // ???????????????id
                    insureItemMatchDTO.setPqccItemId(null); // ???????????????id
                    insureItemMatchDTO.setHospItemName(baseMaterialDTO.getName()); //??????????????????
                    insureItemMatchDTO.setHospItemId(baseMaterialDTO.getId()); // ????????????id
                    insureItemMatchDTO.setHospItemCode(baseMaterialDTO.getCode()); // ??????????????????
                    insureItemMatchDTO.setHospItemType(Constants.XMLB.CL); // ??????????????????
                    insureItemMatchDTO.setHospItemSpec(baseMaterialDTO.getSpec()); //  ??????????????????
                    insureItemMatchDTO.setHospItemUnitCode(baseMaterialDTO.getUnitCode()); // ??????????????????
                    insureItemMatchDTO.setHospItemPrepCode(null); //??????????????????
                    insureItemMatchDTO.setHospItemPrice(baseMaterialDTO.getPrice()); //??????????????????
                    insureItemMatchDTO.setLmtUserFlag(collect.get(baseMaterialDTO.getNationCode()).getLmtUsedFlag());  // ????????????????????????
                    insureItemMatchDTO.setInsureItemName(collect.get(baseMaterialDTO.getNationCode()).getItemName()); // ????????????????????????
                    insureItemMatchDTO.setInsureItemCode(collect.get(baseMaterialDTO.getNationCode()).getItemCode()); // ????????????????????????
                    insureItemMatchDTO.setInsureItemType(collect.get(baseMaterialDTO.getNationCode()).getItemType()); // ????????????????????????
                    insureItemMatchDTO.setInsureItemSpec(collect.get(baseMaterialDTO.getNationCode()).getItemSpec()); // ????????????????????????
                    insureItemMatchDTO.setInsureItemUnitCode(collect.get(baseMaterialDTO.getNationCode()).getItemUnitCode()); //????????????????????????
                    insureItemMatchDTO.setInsureItemPrepCode(collect.get(baseMaterialDTO.getNationCode()).getItemDosage()); //????????????????????????
                    insureItemMatchDTO.setInsureItemPrice(null); // ????????????????????????
                    insureItemMatchDTO.setDeductible(collect.get(baseMaterialDTO.getNationCode()).getDeductible()); // ????????????
                    insureItemMatchDTO.setStandardCode(null); // ?????????
                    insureItemMatchDTO.setCheckPrice(collect.get(baseMaterialDTO.getNationCode()).getCheckPrice()); // ??????
                    insureItemMatchDTO.setManufacturer(null); // ????????????
                    insureItemMatchDTO.setAuditCode(Constants.SF.F); // ??????????????????
                    insureItemMatchDTO.setIsMatch(Constants.SF.S); // ????????????
                    insureItemMatchDTO.setIsTrans(Constants.SF.F); // ????????????
                    insureItemMatchDTO.setIsValid(baseMaterialDTO.getIsValid()); // ????????????
                    insureItemMatchDTO.setTakeDate(collect.get(baseMaterialDTO.getNationCode()).getTakeDate()); // ????????????
                    insureItemMatchDTO.setLoseDate(collect.get(baseMaterialDTO.getNationCode()).getLoseDate()); // ????????????
                    insureItemMatchDTO.setPym(baseMaterialDTO.getPym()); // ?????????
                    insureItemMatchDTO.setWbm(baseMaterialDTO.getWbm()); // ?????????
                    insureItemMatchDTO.setCrteId(userId); // ?????????ID
                    insureItemMatchDTO.setCrteTime(DateUtils.getNow()); // ???????????????
                    insureItemMatchDTO.setCrteName(userName); // ????????????
                    insureItemMatchDTOList.add(insureItemMatchDTO);
                }
            }
        }
        itemMatchDTO.setIsMatch(Constants.SF.S);
        itemMatchDTO.setHospItemType(Constants.XMLB.CL);
        /**
         *      1.?????????????????????????????????
         *      2.???????????????????????????????????????????????????????????????????????? ??????????????????
         *      3.????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
         */
        List<InsureItemMatchDTO> matchDTOList = insureItemMatchDAO.queryPageOrAll(itemMatchDTO);
        if (!ListUtils.isEmpty(matchDTOList)) {
            List<InsureItemMatchDTO> dtoList = new ArrayList<>();
            Map<String, InsureItemMatchDTO> drugMatchDTOMap = matchDTOList.stream().collect(Collectors.toMap(InsureItemMatchDTO::getHospItemCode, Function.identity(), (k1, k2) -> k1));
            if (!ListUtils.isEmpty(insureItemMatchDTOList)) {
                for (InsureItemMatchDTO matchDTO : insureItemMatchDTOList) {
                    if (!drugMatchDTOMap.isEmpty() && !drugMatchDTOMap.containsKey(matchDTO.getHospItemCode())) {
                        dtoList.add(matchDTO);
                    }
                }
            }
            return dtoList;
        } else {
            return insureItemMatchDTOList;
        }
    }

    /**
     * @Method handItemAutoMatch
     * @Desrciption ????????????????????????
     * @Param baseDrugDTOList:????????????????????????
     * insureItemDTOList???????????????????????????????????????
     * map?????????????????????????????????????????????????????? ????????????id?????????
     * @Author fuhui
     * @Date 2021/1/27 21:12
     * @Return
     **/
    private List<InsureItemMatchDTO> handItemAutoMatch(List<InsureItemMatchDTO> insureItemMatchList, List<InsureItemDTO> insureItemDTOList,InsureItemMatchDTO itemMatchDTO) {
        String matchType = itemMatchDTO.getMatchType();
        Map<String, InsureItemDTO> collect = null;
        if (Constant.UnifiedPay.MATCHCODE.MC.equals(matchType)) {
            collect = insureItemDTOList.stream().collect(Collectors.toMap(InsureItemDTO::getItemName, Function.identity(), (k1, k2) -> k1));
            for (InsureItemMatchDTO insureItemMatchDTO : insureItemMatchList) {
                if (!collect.isEmpty() && collect.containsKey(insureItemMatchDTO.getHospItemName())) {
                    InsureItemDTO itemDto = collect.get(insureItemMatchDTO.getHospItemName());
                    //insureItemMatchDTO.setItemCode(itemDto.getItemMark()); // ??????????????????
                    insureItemMatchDTO.setInsureItemName(itemDto.getItemName()); // ????????????????????????
                    insureItemMatchDTO.setInsureItemCode(itemDto.getItemCode()); // ????????????????????????
                    insureItemMatchDTO.setInsureItemType(itemDto.getItemType()); // ????????????????????????
                    insureItemMatchDTO.setInsureItemSpec(itemDto.getItemSpec()); // ????????????????????????
                    insureItemMatchDTO.setInsureItemUnitCode(itemDto.getItemUnitCode()); //????????????????????????
                    insureItemMatchDTO.setInsureItemPrepCode(itemDto.getItemDosage()); //????????????????????????
                    insureItemMatchDTO.setDeductible(itemDto.getDeductible()); // ????????????
                    insureItemMatchDTO.setCheckPrice(itemDto.getCheckPrice()); // ??????
                    insureItemMatchDTO.setIsMatch(Constants.SF.S); // ????????????
                    insureItemMatchDTO.setIsValid(Constants.SF.S); // ????????????
                    insureItemMatchDTO.setIsTrans(Constants.SF.F); // ????????????
                    insureItemMatchDTO.setAuditCode(Constants.SF.F); // ????????????
                    insureItemMatchDTO.setTakeDate(itemDto.getTakeDate()); // ????????????
                    insureItemMatchDTO.setLoseDate(itemDto.getLoseDate()); // ????????????
                }
            }
        } else {
            collect = insureItemDTOList.stream().collect(Collectors.toMap(InsureItemDTO::getItemCode, Function.identity(), (k1, k2) -> k1));
            for (InsureItemMatchDTO insureItemMatchDTO : insureItemMatchList) {
                if (insureItemMatchDTO.getNationCode() == null) {
                    continue;
                }
                if (!collect.isEmpty() && collect.containsKey(insureItemMatchDTO.getNationCode())) {
                    InsureItemDTO itemDto = collect.get(insureItemMatchDTO.getNationCode());
                    if (itemDto == null) {
                        continue;
                    }
                    insureItemMatchDTO.setItemCode(itemDto.getItemMark()); // ??????????????????
                    insureItemMatchDTO.setInsureItemName(itemDto.getItemName()); // ????????????????????????
                    insureItemMatchDTO.setInsureItemCode(itemDto.getItemCode()); // ????????????????????????
                    insureItemMatchDTO.setInsureItemType(itemDto.getItemType()); // ????????????????????????
                    insureItemMatchDTO.setInsureItemSpec(itemDto.getItemSpec()); // ????????????????????????
                    insureItemMatchDTO.setInsureItemUnitCode(itemDto.getItemUnitCode()); //????????????????????????
                    insureItemMatchDTO.setInsureItemPrepCode(itemDto.getItemDosage()); //????????????????????????
                    insureItemMatchDTO.setDeductible(itemDto.getDeductible()); // ????????????
                    insureItemMatchDTO.setCheckPrice(itemDto.getCheckPrice()); // ??????
                    insureItemMatchDTO.setIsMatch(Constants.SF.S); // ????????????
                    insureItemMatchDTO.setIsValid(Constants.SF.S); // ????????????
                    insureItemMatchDTO.setIsTrans(Constants.SF.F); // ????????????
                    insureItemMatchDTO.setAuditCode(Constants.SF.F); // ????????????
                    insureItemMatchDTO.setTakeDate(itemDto.getTakeDate()); // ????????????
                    insureItemMatchDTO.setLoseDate(itemDto.getLoseDate()); // ????????????
                }
            }
        }
        return insureItemMatchList;
    }

    /**
     * @Method handDrugAutoMatch
     * @Desrciption ????????????????????????
     * @Param baseDrugDTOList:????????????????????????
     * insureItemDTOList???????????????????????????????????????
     * map?????????????????????????????????????????????????????? ????????????id?????????
     * @Author fuhui
     * @Date 2021/1/27 21:12
     * @Return
     **/
    private List<InsureItemMatchDTO> handDrugAutoMatch(List<BaseDrugDTO> baseDrugDTOList, List<InsureItemDTO> insureItemDTOList, Map map) {
        InsureItemMatchDTO itemMatchDTO = MapUtils.get(map, "itemMatchDTO");
        String userId = itemMatchDTO.getCrteId();
        String userName = itemMatchDTO.getCrteName();
        String matchType = itemMatchDTO.getMatchType();
        List<InsureItemMatchDTO> insureItemMatchDTOList = new ArrayList<>();
        Map<String, InsureItemDTO> collect = null;
        if (Constant.UnifiedPay.MATCHCODE.MC.equals(matchType)) {
            collect = insureItemDTOList.stream().collect(Collectors.toMap(InsureItemDTO::getItemName, Function.identity(), (v1, v2) -> v1));
            for (BaseDrugDTO baseDrugDTO : baseDrugDTOList) {
                if (!collect.isEmpty() && collect.containsKey(baseDrugDTO.getUsualName())) {
                    InsureItemMatchDTO insureItemMatchDTO = new InsureItemMatchDTO();
                    insureItemMatchDTO.setId(SnowflakeUtils.getId());
                    insureItemMatchDTO.setHospCode(baseDrugDTO.getHospCode()); //
                    insureItemMatchDTO.setInsureRegCode(collect.get(baseDrugDTO.getUsualName()).getInsureRegCode());
                    insureItemMatchDTO.setItemCode(collect.get(baseDrugDTO.getUsualName()).getItemMark()); // ??????????????????
                    insureItemMatchDTO.setMolssItemId(null); // ???????????????id
                    insureItemMatchDTO.setPqccItemId(null); // ???????????????id
                    if (StringUtils.isEmpty(baseDrugDTO.getGoodName())) {
                        insureItemMatchDTO.setHospItemName(baseDrugDTO.getUsualName()); //??????????????????
                    } else {
                        insureItemMatchDTO.setHospItemName(baseDrugDTO.getGoodName()); //??????????????????
                    }
                    insureItemMatchDTO.setHospItemId(baseDrugDTO.getId()); // ????????????id
                    insureItemMatchDTO.setHospItemCode(baseDrugDTO.getCode()); // ??????????????????
                    insureItemMatchDTO.setHospItemType(Constants.XMLB.YP); // ??????????????????
                    insureItemMatchDTO.setHospItemSpec(baseDrugDTO.getSpec()); //  ??????????????????
                    insureItemMatchDTO.setHospItemUnitCode(baseDrugDTO.getUnitCode()); // ??????????????????
                    insureItemMatchDTO.setHospItemPrepCode(baseDrugDTO.getPrepCode()); //??????????????????
                    insureItemMatchDTO.setHospItemPrice(baseDrugDTO.getPrice()); //??????????????????
                    insureItemMatchDTO.setLmtUserFlag(collect.get(baseDrugDTO.getUsualName()).getLmtUsedFlag()); // ????????????????????????
                    insureItemMatchDTO.setInsureItemName(collect.get(baseDrugDTO.getUsualName()).getItemName()); // ????????????????????????
                    insureItemMatchDTO.setInsureItemCode(collect.get(baseDrugDTO.getUsualName()).getItemCode()); // ????????????????????????
                    insureItemMatchDTO.setInsureItemType(collect.get(baseDrugDTO.getUsualName()).getItemType()); // ????????????????????????
                    insureItemMatchDTO.setInsureItemSpec(collect.get(baseDrugDTO.getUsualName()).getItemSpec()); // ????????????????????????
                    insureItemMatchDTO.setInsureItemUnitCode(collect.get(baseDrugDTO.getUsualName()).getItemUnitCode()); //????????????????????????
                    insureItemMatchDTO.setInsureItemPrepCode(collect.get(baseDrugDTO.getUsualName()).getItemDosage()); //????????????????????????
                    insureItemMatchDTO.setInsureItemPrice(null); // ????????????????????????
                    insureItemMatchDTO.setDeductible(collect.get(baseDrugDTO.getUsualName()).getDeductible()); // ????????????
                    insureItemMatchDTO.setStandardCode(baseDrugDTO.getInsureRegCode()); // ?????????
                    insureItemMatchDTO.setCheckPrice(collect.get(baseDrugDTO.getUsualName()).getCheckPrice()); // ??????
                    insureItemMatchDTO.setManufacturer(baseDrugDTO.getProdName()); // ????????????
                    insureItemMatchDTO.setAuditCode(Constants.SF.F); // ??????????????????
                    insureItemMatchDTO.setIsMatch(Constants.SF.S); // ????????????
                    insureItemMatchDTO.setIsTrans(Constants.SF.F); // ????????????
                    insureItemMatchDTO.setIsValid(baseDrugDTO.getIsValid()); // ????????????
                    insureItemMatchDTO.setTakeDate(collect.get(baseDrugDTO.getUsualName()).getTakeDate()); // ????????????
                    insureItemMatchDTO.setLoseDate(collect.get(baseDrugDTO.getUsualName()).getLoseDate()); // ????????????
                    insureItemMatchDTO.setPym(baseDrugDTO.getUsualPym()); // ?????????
                    insureItemMatchDTO.setWbm(baseDrugDTO.getUsualWbm()); // ?????????
                    insureItemMatchDTO.setCrteId(userId); // ?????????ID
                    insureItemMatchDTO.setCrteTime(DateUtils.getNow()); // ???????????????
                    insureItemMatchDTO.setCrteName(userName); // ????????????
                    insureItemMatchDTOList.add(insureItemMatchDTO);
                }
            }
        } else if (Constant.UnifiedPay.MATCHCODE.GJBM.equals(matchType)) {
            collect = insureItemDTOList.stream().collect(Collectors.toMap(InsureItemDTO::getItemCode, Function.identity(), (v1, v2) -> v1));
            for (BaseDrugDTO baseDrugDTO : baseDrugDTOList) {
                if (!collect.isEmpty() && collect.containsKey(baseDrugDTO.getNationCode()) && !StringUtils.isEmpty(baseDrugDTO.getNationCode())) {
                    InsureItemMatchDTO insureItemMatchDTO = new InsureItemMatchDTO();
                    insureItemMatchDTO.setId(SnowflakeUtils.getId());
                    insureItemMatchDTO.setHospCode(baseDrugDTO.getHospCode()); //
                    insureItemMatchDTO.setInsureRegCode(collect.get(baseDrugDTO.getNationCode()).getInsureRegCode());
                    insureItemMatchDTO.setItemCode(collect.get(baseDrugDTO.getNationCode()).getItemMark()); // ??????????????????
                    insureItemMatchDTO.setMolssItemId(null); // ???????????????id
                    insureItemMatchDTO.setPqccItemId(null); // ???????????????id
                    if (StringUtils.isEmpty(baseDrugDTO.getGoodName())) {
                        insureItemMatchDTO.setHospItemName(baseDrugDTO.getUsualName()); //??????????????????
                    } else {
                        insureItemMatchDTO.setHospItemName(baseDrugDTO.getGoodName()); //??????????????????
                    }
                    insureItemMatchDTO.setHospItemId(baseDrugDTO.getId()); // ????????????id
                    insureItemMatchDTO.setHospItemCode(baseDrugDTO.getCode()); // ??????????????????
                    insureItemMatchDTO.setHospItemType(Constants.XMLB.YP); // ??????????????????
                    insureItemMatchDTO.setHospItemSpec(baseDrugDTO.getSpec()); //  ??????????????????
                    insureItemMatchDTO.setHospItemUnitCode(baseDrugDTO.getUnitCode()); // ??????????????????
                    insureItemMatchDTO.setHospItemPrepCode(baseDrugDTO.getPrepCode()); //??????????????????
                    insureItemMatchDTO.setHospItemPrice(baseDrugDTO.getPrice()); //??????????????????
                    insureItemMatchDTO.setLmtUserFlag(collect.get(baseDrugDTO.getNationCode()).getLmtUsedFlag()); // ????????????????????????
                    insureItemMatchDTO.setInsureItemName(collect.get(baseDrugDTO.getNationCode()).getItemName()); // ????????????????????????
                    insureItemMatchDTO.setInsureItemCode(collect.get(baseDrugDTO.getNationCode()).getItemCode()); // ????????????????????????
                    insureItemMatchDTO.setInsureItemType(collect.get(baseDrugDTO.getNationCode()).getItemType()); // ????????????????????????
                    insureItemMatchDTO.setInsureItemSpec(collect.get(baseDrugDTO.getNationCode()).getItemSpec()); // ????????????????????????
                    insureItemMatchDTO.setInsureItemUnitCode(collect.get(baseDrugDTO.getNationCode()).getItemUnitCode()); //????????????????????????
                    insureItemMatchDTO.setInsureItemPrepCode(collect.get(baseDrugDTO.getNationCode()).getItemDosage()); //????????????????????????
                    insureItemMatchDTO.setInsureItemPrice(null); // ????????????????????????
                    insureItemMatchDTO.setDeductible(collect.get(baseDrugDTO.getNationCode()).getDeductible()); // ????????????
                    insureItemMatchDTO.setStandardCode(baseDrugDTO.getInsureRegCode()); // ?????????
                    insureItemMatchDTO.setCheckPrice(collect.get(baseDrugDTO.getNationCode()).getCheckPrice()); // ??????
                    insureItemMatchDTO.setManufacturer(baseDrugDTO.getProdName()); // ????????????
                    insureItemMatchDTO.setAuditCode(Constants.SF.F); // ??????????????????
                    insureItemMatchDTO.setIsMatch(Constants.SF.S); // ????????????
                    insureItemMatchDTO.setIsTrans(Constants.SF.F); // ????????????
                    insureItemMatchDTO.setIsValid(baseDrugDTO.getIsValid()); // ????????????
                    insureItemMatchDTO.setTakeDate(collect.get(baseDrugDTO.getNationCode()).getTakeDate()); // ????????????
                    insureItemMatchDTO.setLoseDate(collect.get(baseDrugDTO.getNationCode()).getLoseDate()); // ????????????
                    insureItemMatchDTO.setPym(baseDrugDTO.getUsualPym()); // ?????????
                    insureItemMatchDTO.setWbm(baseDrugDTO.getUsualWbm()); // ?????????
                    insureItemMatchDTO.setCrteId(userId); // ?????????ID
                    insureItemMatchDTO.setCrteTime(DateUtils.getNow()); // ???????????????
                    insureItemMatchDTO.setCrteName(userName); // ????????????
                    insureItemMatchDTOList.add(insureItemMatchDTO);
                }
            }
        } else {
            //matchAutoStardCode(baseDrugDTOList, insureItemDTOList, insureItemMatchDTOList, userId, userName);
        }
        itemMatchDTO.setIsMatch(Constants.SF.S);
        itemMatchDTO.setHospItemType(Constants.XMLB.YP);
        /**
         *      1.?????????????????????????????????
         *      2.???????????????????????????????????????????????????????????????????????? ??????????????????
         *      3.????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
         */
        List<InsureItemMatchDTO> matchDTOList = insureItemMatchDAO.queryPageOrAll(itemMatchDTO);
        if (!ListUtils.isEmpty(matchDTOList)) {
            List<InsureItemMatchDTO> dtoList = new ArrayList<>();
            Map<String, InsureItemMatchDTO> drugMatchDTOMap = matchDTOList.stream().collect(Collectors.toMap(InsureItemMatchDTO::getHospItemCode, Function.identity(), (k1, k2) -> k1));
            if (!ListUtils.isEmpty(insureItemMatchDTOList)) {
                for (InsureItemMatchDTO matchDTO : insureItemMatchDTOList) {
                    if (!drugMatchDTOMap.isEmpty() && !drugMatchDTOMap.containsKey(matchDTO.getHospItemCode())) {
                        dtoList.add(matchDTO);
                    }
                }
            }
            return dtoList;
        } else {
            return insureItemMatchDTOList;
        }
    }

    /**
     * @Method insertDiseaseHandler
     * @Desrciption ????????????????????????????????????????????????
     * @Param
     * @Author fuhui
     * @Date 2021/4/10 2:18
     * @Return
     **/
    private void insertHandlerDrug(InsureItemDTO insureItemDTO, BaseDrugDTO baseDrugDTO, InsureItemMatchDTO itemMatchDTO) {

        List<InsureItemMatchDTO> list = insureItemMatchDAO.selectById(itemMatchDTO);
        if (!ListUtils.isEmpty(list)) {
            throw new AppException("??????" + list.get(0).getHospItemName() + "???????????????");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id", SnowflakeUtils.getId());
        map.put("hospCode", itemMatchDTO.getHospCode());
        map.put("insureRegCode", itemMatchDTO.getInsureRegCode());
        if ("0".equals(baseDrugDTO.getBigTypeCode())) {
            map.put("itemCode", "101"); // ??????????????????
        } else if ("1".equals(baseDrugDTO.getBigTypeCode())) {
            map.put("itemCode", "102"); // ??????????????????
        } else {
            map.put("itemCode", "103"); // ??????????????????
        }
        map.put("hospItemId", baseDrugDTO.getId()); // ????????????id
        if(StringUtils.isEmpty(baseDrugDTO.getUsualName())){
            map.put("hospItemName", baseDrugDTO.getGoodName()); // ??????????????????
        } else {
            map.put("hospItemName", baseDrugDTO.getUsualName()); // ??????????????????
        }
        map.put("hospItemCode", baseDrugDTO.getCode()); // ??????????????????
        map.put("hospItemType", Constants.XMLB.YP); // ??????????????????
        map.put("hospItemSpec", baseDrugDTO.getSpec()); // ??????????????????
        map.put("hospItemUnitCode", baseDrugDTO.getUnitCode()); // ??????????????????
        map.put("hospItemUnitCode", baseDrugDTO.getPrepCode()); // ??????????????????
        map.put("hospItemPrice", baseDrugDTO.getAvgBuyPrice()); // ??????????????????
        map.put("insureItemName", insureItemDTO.getItemName()); // ????????????????????????
        map.put("insureItemCode", insureItemDTO.getItemCode()); // ????????????????????????
        map.put("insureItemType", insureItemDTO.getItemType()); // ????????????????????????
        map.put("insureItemSpec", insureItemDTO.getItemSpec()); // ????????????????????????
        map.put("insureItemUnitCode", insureItemDTO.getItemUnitCode()); // ????????????????????????
        map.put("insureItemPrepCode", insureItemDTO.getItemDosage()); // ????????????????????????
        map.put("manufacturer", baseDrugDTO.getManufacturer());
        map.put("takeDate", insureItemDTO.getTakeDate());
        map.put("isValid", Constants.SF.S);
        map.put("isTrans", Constants.SF.F);
        map.put("auditCode", Constants.SF.F);
        map.put("isMatch", Constants.SF.S);
        map.put("crteTime", itemMatchDTO.getCrteTime());
        map.put("crteName", itemMatchDTO.getCrteName());
        map.put("crteId", itemMatchDTO.getCrteId());
        map.put("crteTime", DateUtils.getNow());
        insureItemMatchDAO.insertHandMatch(map);

    }

    /**
     * @Method insertDiseaseHandler
     * @Desrciption ????????????????????????????????????????????????
     * @Param
     * @Author fuhui
     * @Date 2021/4/10 2:18
     * @Return
     **/
    private void insertDiseaseHandler(InsureDiseaseDTO insureDiseaseDTO, BaseDiseaseDTO baseDiseaseDTO, InsureItemMatchDTO itemMatchDTO) {

        InsureDiseaseMatchDTO insureDiseaseMatchDTO = new InsureDiseaseMatchDTO();
        insureDiseaseMatchDTO.setHospIllnessId(itemMatchDTO.getHospItemId());
        insureDiseaseMatchDTO.setHospCode(itemMatchDTO.getHospCode());
        insureDiseaseMatchDTO.setInsureRegCode(itemMatchDTO.getInsureRegCode());
        List<InsureDiseaseMatchDTO> insureDiseaseMatchDTOList = insureDiseaseMatchDAO.selectById(insureDiseaseMatchDTO);
        if (!ListUtils.isEmpty(insureDiseaseMatchDTOList)) {
            throw new AppException("??????" + insureDiseaseMatchDTOList.get(0).getHospIllnessName() + "?????????????????????");
        }
        insureDiseaseMatchDTO.setId(SnowflakeUtils.getId()); // ??????id
        insureDiseaseMatchDTO.setHospCode(itemMatchDTO.getHospCode()); // ????????????
        insureDiseaseMatchDTO.setInsureRegCode(itemMatchDTO.getInsureRegCode()); // ??????????????????
        insureDiseaseMatchDTO.setHospIllnessId(baseDiseaseDTO.getId()); // ????????????ID
        insureDiseaseMatchDTO.setHospIllnessName(baseDiseaseDTO.getName()); // ??????ICD??????
        insureDiseaseMatchDTO.setHospIllnessCode(baseDiseaseDTO.getCode()); // ??????ICD??????
        insureDiseaseMatchDTO.setInsureIllnessId(insureDiseaseDTO.getInsureIllnessId()); // ??????????????????ID
        insureDiseaseMatchDTO.setInsureIllnessName(insureDiseaseDTO.getInsureIllnessName()); // ????????????ICD??????
        insureDiseaseMatchDTO.setInsureIllnessCode(insureDiseaseDTO.getInsureIllnessCode()); //????????????ICD??????
        insureDiseaseMatchDTO.setIsTrans(Constants.SF.S); // ????????????
        insureDiseaseMatchDTO.setIsMatch(Constants.SF.S); // ????????????
        insureDiseaseMatchDTO.setAuditCode(Constants.SF.S); // ??????????????????
        insureDiseaseMatchDTO.setRemark(insureDiseaseDTO.getRemark()); // ??????
        insureDiseaseMatchDTO.setCrteId(itemMatchDTO.getCrteId()); // ?????????ID
        insureDiseaseMatchDTO.setCrteTime(DateUtils.getNow());
        insureDiseaseMatchDTO.setCrteName(itemMatchDTO.getCrteName());
        insureDiseaseMatchDTO.setTypeCode(insureDiseaseDTO.getDownLoadType());
        insureDiseaseMatchDAO.insertDiseaseMatch(insureDiseaseMatchDTO);
    }

    /**
     * @Method ???????????????????????????????????????????????????
     * num: ???????????????       size:??????????????????????????????
     * @Desrciption
     * @Param
     * @Author fuhui
     * @Date 2021/6/4 15:41
     * @Return
     **/
    public Map<String, Object> insertDownloadItem(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        String itemType = MapUtils.get(map, "downLoadType");
        if(StringUtils.isEmpty(itemType)){
            throw new AppException("????????????????????????");
        }
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        map.put("code", "UNIFIED_DOWNLOAD");
        SysParameterDTO data = sysParameterService_consumer.getParameterByCode(map).getData();
        int num = 0;
        int size = 0;
        if (data != null) {
            // ??????????????????????????????????????????   ????????????????????????
            String value = data.getValue();
            Map<String, Object> stringObjectMap = JSON.parseObject(value, Map.class);
            for (String key : stringObjectMap.keySet()) {
                if ("pageNum".equals(key)) {
                    num = MapUtils.get(stringObjectMap, key);
                }
                if ("pageSize".equals(key)) {
                    size = MapUtils.get(stringObjectMap, key);
                }
            }
        }
        Map<String, Object> dataMap = new HashMap<>();
        Map httpParam = new HashMap();
        if (ObjectUtil.isEmpty(insureRegCode)) {
            throw new AppException("??????????????????????????????!");
        }
        map.put("subRegCode",insureRegCode.substring(0,2));
        int records = 0;
        if (Constant.UnifiedPay.JBLIST.containsKey(itemType)) {
            InsureDiseaseDTO insureDiseaseDTO = insureDiseaseDAO.selectLatestVer(map);
            if (ObjectUtil.isNotEmpty(insureDiseaseDTO)) {
                //???????????????????????????????????????
                checkIsNewItem(insureRegCode, itemType, insureConfigurationDTO.getUrl(), insureDiseaseDTO.getRecordCounts(),insureDiseaseDTO.getNum(),insureDiseaseDTO.getSize());
            }

            if (insureDiseaseDTO != null) {
                //?????????????????????????????????????????????
                int pageNum = insureDiseaseDTO.getNum();
                map.put("lastPage",pageNum);
                List<InsureDiseaseDTO> lastPageList = insureDiseaseDAO.selectLastPageList(map);
                List<String> ids = lastPageList.stream().map(InsureDiseaseDTO::getId).collect(Collectors.toList());
                if(!ListUtils.isEmpty(lastPageList)&&lastPageList.size()<insureDiseaseDTO.getSize()){
                    //?????????????????????????????????????????????????????????????????????????????????
                    map.put("ids",ids);
                    insureDiseaseDAO.deleteLastPage(map);
                    dataMap.put("page_num", pageNum);
                    num = pageNum;
                }else{
                    num = ++pageNum;
                    dataMap.put("page_num", num);

                }
                records = insureDiseaseDTO.getRecordCounts();
            } else {
                dataMap.put("page_num", num);
            }
            dataMap.put("page_size", size);
            dataMap.put("recordCounts", records);
        } else {
            InsureItemDTO insureItemDTO = insureItemDAO.selectLatestVer(map);
            if (ObjectUtil.isNotEmpty(insureItemDTO)) {
                //???????????????????????????????????????
                checkIsNewItem(insureRegCode, itemType, insureConfigurationDTO.getUrl(), insureItemDTO.getRecordCounts(),insureItemDTO.getNum(),insureItemDTO.getSize());
            }
              if (insureItemDTO != null) {
                  //?????????????????????????????????????????????
                  int pageNum = insureItemDTO.getNum();
                  map.put("lastPage",pageNum);
                  List<InsureItemDTO> lastPageList = insureItemDAO.selectLastPageList(map);
                  List<String> ids = lastPageList.stream().map(InsureItemDTO::getId).collect(Collectors.toList());

                 if(!ListUtils.isEmpty(lastPageList)&&lastPageList.size()<insureItemDTO.getSize()){
                     //?????????????????????????????????????????????????????????????????????????????????
                     map.put("ids",ids);
                     insureItemDAO.deleteLastPage(map);
                     dataMap.put("page_num", pageNum);
                     num = pageNum;
                 }else{
                     num = ++pageNum;
                     dataMap.put("page_num", num);

                 }
                  records = insureItemDTO.getRecordCounts();
            } else {
                dataMap.put("page_num", num);
            }
            dataMap.put("page_size", size);
            dataMap.put("recordCounts", records);
        }
        switch (itemType) {
            case "1301":  // ????????????
                httpParam.put("infno", Constant.UnifiedPay.DOWNLOAD.UP_1301);  //???????????? ????????????????????????
                break;
            case "1302": //????????????
                httpParam.put("infno", Constant.UnifiedPay.DOWNLOAD.UP_1302);  //????????????????????????
                break;
            case "1303": // ?????????
                httpParam.put("infno", Constant.UnifiedPay.DOWNLOAD.UP_1303);  //??????????????????????????????
                break;
            case "1304": // ?????????
                httpParam.put("infno", Constant.UnifiedPay.DOWNLOAD.UP_1304);  //????????????????????????
                dataMap.put("updt_time", new Date());
                break;
            case "1305": // ????????????
                httpParam.put("infno", Constant.UnifiedPay.DOWNLOAD.UP_1305);  //??????????????????????????????
                break;
            case "1306": // ????????????
                httpParam.put("infno", Constant.UnifiedPay.DOWNLOAD.UP_1306);  //????????????????????????
                break;
            case "1307": // ???????????????????????????
                httpParam.put("infno", Constant.UnifiedPay.DOWNLOAD.UP_1307);  //????????????????????????
                break;
            case "1308": // ????????????????????????
                httpParam.put("infno", Constant.UnifiedPay.DOWNLOAD.UP_1308);  //????????????????????????
                break;
            case "1309": // ??????????????????????????????
                httpParam.put("infno", Constant.UnifiedPay.DOWNLOAD.UP_1309);  //????????????????????????
                break;
            case "1310": // ?????????????????????????????????
                httpParam.put("infno", Constant.UnifiedPay.DOWNLOAD.UP_1310);  //????????????????????????
                break;
            case "1311": // ????????????????????????????????????
                httpParam.put("infno", Constant.UnifiedPay.DOWNLOAD.UP_1311);  //????????????????????????
                break;
            case "1313": // ????????????????????????????????????
                httpParam.put("infno", Constant.UnifiedPay.DOWNLOAD.UP_1313);  //????????????????????????
                break;
            case "1314": // ????????????????????????
                httpParam.put("infno", Constant.UnifiedPay.DOWNLOAD.UP_1314);  //????????????????????????
                break;
            default:
                httpParam.put("infno", Constant.UnifiedPay.DOWNLOAD.UP_1315);  //????????????
        }
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //????????????????????????
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //????????????????????????
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //??????????????????
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        Map<String, Object> paramMap = new HashMap<>();
        //??????????????????????????????  his:??????????????????his
        dataMap.put("source","his");
        paramMap.put("data", dataMap);
        httpParam.put("input", paramMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("??????????????????????????????" + itemType + ":" + json);
        String url = insureConfigurationDTO.getUrl();
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(url, json);
        logger.info("??????????????????????????????" + itemType + ":" + resultJson);
        if (StringUtils.isEmpty(resultJson)) {
            throw new AppException("????????????????????????????????????");
        }
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson);
        if ("999".equals(MapUtil.getStr(resultMap, "code"))) {
            throw new AppException("?????????????????????????????????????????????????????????"+MapUtil.getStr(resultMap,"msg"));
        }
        if ("-1".equals(MapUtil.getStr(resultMap, "code"))) {
            throw new AppException("?????????????????????????????????????????????????????????"+MapUtil.getStr(resultMap,"message"));
        }
        if (!"0".equals(MapUtil.getStr(resultMap, "infcode"))) {
            throw new AppException("?????????????????????????????????????????????????????????"+MapUtil.getStr(resultMap,"err_msg"));
        }
        Map<String, Object> outptMap = (Map<String, Object>) resultMap.get("output");
        Object obj = outptMap.get("data");
        List<Map<String, Object>> dataResultMap;
        // ?????????????????????string?????????????????????????????????????????????????????????
        if(obj instanceof String){
            logger.info("??????????????????????????????" + itemType + ":" + obj);
        }else if(obj instanceof List){
            dataResultMap = (List<Map<String, Object>>) obj;
            if (ListUtils.isEmpty(dataResultMap)) {
                throw new AppException("??????" + itemType + "?????????????????????????????????");
            }
            int recordCounts = MapUtils.get(outptMap, "recordCounts");
            map.put("size", size);
            map.put("num", num);
            map.put("recordCounts", recordCounts);
            if (!ListUtils.isEmpty(dataResultMap)) {
                logger.info("??????????????????????????????" + itemType + ":" + new JSONObject(dataResultMap.get(0)));
            }
            switch (itemType) {
                case "1301":
                    insert_1301(dataResultMap, map);
                    break;
                case "1302":
                    insert_1302(dataResultMap, map);
                    break;
                case "1303":
                    insert_1303(dataResultMap, map);
                    break;
                case "1304":
                    insert_1304(dataResultMap, map);
                    break;

                case "1305":
                    insert_1305(dataResultMap, map);
                    break;
                case "1306":
                    insert_1306(dataResultMap, map);
                    break;
                case "1307":
                    insert_1307(dataResultMap, map);
                    break;
                case "1308":
                    insert_1308(dataResultMap, map);
                    break;

                case "1309":
                    insert_1309(dataResultMap, map);
                    break;
                case "1310":
                    insert_1310(dataResultMap, map);
                    break;
                case "1311":
                    insert_1311(dataResultMap, map);
                    break;
                case "1312":
                    insert_1312(dataResultMap, map);
                    break;
                case "1313":
                    insert_1313(dataResultMap, map);
                    break;
                case "1314":
                    insert_1314(dataResultMap, map);
                    break;
                case "1315":
                    insert_1315(dataResultMap, map);
                    break;
            }
        }
        return resultMap;
    }

    /**
     * @Description ??????????????????????????????????????????
     * @Author ????????????-??????
     * @Date 2022-06-13 10:15
     * @param insureRegCode
     * @param itemType
     * @param url_in
     * @param recordCounts
     * @return void
     */
    private void checkIsNewItem(String insureRegCode, String itemType, String url_in, int recordCounts,int pageNum, int pageSize) {

            HashMap<String, Object> inMap = new HashMap<>();
            inMap.put("infno",itemType);
            inMap.put("insurCode",insureRegCode.substring(0,2));
            String url = url_in.substring(0, url_in.lastIndexOf("/"))+"/getMiDirectoryCount";
            String centerCount = "0";
            try{
                centerCount = HttpConnectUtil.unifiedPayPostUtil(url, JSON.toJSONString(inMap));
            }catch (Exception ex){
                throw new AppException("???????????????????????????????????????!"+ex.getMessage());
            }
            if (Integer.valueOf(centerCount) <= recordCounts && recordCounts < pageNum * pageSize ) {
                throw new AppException("???????????????????????????????????????");
            }



    }

    private void insert_1312(List<Map<String, Object>> dataResultMap, Map<String, Object> map) {
    }

    /**
     * @Method insert_1304
     * @Desrciption ?????????????????????????????????????????????
     * @Param dataResultMap , map
     * @Author fuhui
     * @Date 2021/4/9 15:18
     * @Return
     **/
    private void insert_1304(List<Map<String, Object>> dataResultMap, Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String crteId = MapUtils.get(map, "crteId");
        String listType = MapUtils.get(map, "downLoadType");
        String crteName = MapUtils.get(map, "crteName");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        List<InsureItemDTO> insureItemDTOList = new ArrayList<>();
        int size = MapUtils.get(map, "size");
        int num = MapUtils.get(map, "num");
        int recordCounts = MapUtils.get(map, "recordCounts");
        InsureItemDTO itemDTO = null;
        if (!ListUtils.isEmpty(dataResultMap)) {
            for (Map<String, Object> item : dataResultMap) {
                itemDTO = new InsureItemDTO();
                itemDTO.setRecordCounts(recordCounts);
                itemDTO.setSize(size);
                itemDTO.setNum(num);
                // ??????
                itemDTO.setId(SnowflakeUtils.getId());
                // ????????????
                itemDTO.setHospCode(hospCode);
                // ??????????????????
                itemDTO.setInsureRegCode(insureRegCode);
                // ??????????????????  --????????????
                itemDTO.setItemMark("1304");
                //????????????????????????
                itemDTO.setItemCode(MapUtils.get(item, "med_list_codg"));
                // ????????????????????????
                itemDTO.setItemType("1304");
                // ????????????????????????
                itemDTO.setItemSpec(MapUtils.get(item, "drug_spec"));
                itemDTO.setItemName(MapUtils.get(item, "drug_prodname"));
                // ????????????????????????
                itemDTO.setItemDosage(MapUtils.get(item, "dosform"));
                // ????????????????????????
                itemDTO.setItemPrice(null);
                // ????????????????????????
                itemDTO.setItemUnitCode(MapUtils.get(item, "min_useunt"));
                // ????????????
                itemDTO.setProd(MapUtils.get(item, "manufacturer_name"));
                // ????????????
                itemDTO.setDeductible(null);
                // ??????
                itemDTO.setCheckPrice(null);
                // ?????????????????????0.??????1.??????2.????????????
                itemDTO.setDirectory(MapUtils.get(item, "null"));
                // ????????????
                if (!StringUtils.isEmpty(MapUtils.get(item, "begndate")) && !"null".equals(MapUtils.get(item, "begndate"))) {
                    itemDTO.setLoseDate(DateUtils.parse(MapUtils.get(item, "begndate"), DateUtils.Y_M_DH_M_S));
                } else {
                    itemDTO.setTakeDate(null);
                }
                //????????????
                if (!StringUtils.isEmpty(MapUtils.get(item, "enddate")) && !"null".equals(MapUtils.get(item, "enddate"))) {
                    itemDTO.setLoseDate(DateUtils.parse(MapUtils.get(item, "enddate"), DateUtils.Y_M_DH_M_S));
                } else {
                    itemDTO.setLoseDate(null);
                }
                // ?????????
                itemDTO.setPym(MapUtils.get(item, "pinyin"));
                // ?????????
                itemDTO.setWbm(MapUtils.get(item, "wubi"));
                // ???????????????SF???
                itemDTO.setIsValid(MapUtils.get(item, "vali_flag"));
                // ?????????ID
                itemDTO.setCrteId(crteId);
                // ???????????????
                itemDTO.setCrteName(crteName);
                // ????????????
                itemDTO.setCrteTime(DateUtils.getNow());
                itemDTO.setCompoundLogo(MapUtils.get(item, "compound_logo"));
                itemDTO.setVer(MapUtils.get(item, "ver")); // ?????????
                itemDTO.setVerName(MapUtils.get(item, "ver_name")); // ?????????
                itemDTO.setDownLoadType(listType);
                insureItemDTOList.add(itemDTO);
            }
            if (!ListUtils.isEmpty(insureItemDTOList)) {
                insureItemDAO.insertInsureItem(insureItemDTOList);
            }
        }
    }

    /**
     * @Method insert_1314
     * @Desrciption ???????????????????????????????????????????????????
     * @Param dataResultMap , map
     * @Author fuhui
     * @Date 2021/4/9 15:18
     * @Return
     **/
    private void insert_1315(List<Map<String, Object>> dataResultMap, Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String crteId = MapUtils.get(map, "crteId");
        String listType = MapUtils.get(map, "downLoadType");
        String crteName = MapUtils.get(map, "crteName");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        List<InsureDiseaseDTO> insureDiseaseDTOList = new ArrayList<>();
        int size = MapUtils.get(map, "size");
        int num = MapUtils.get(map, "num");
        String ver = MapUtils.get(map, "ver");
        int recordCounts = MapUtils.get(map, "recordCounts");
        InsureDiseaseDTO insureDiseaseDTO = null;
        if (!ListUtils.isEmpty(dataResultMap)) {
            for (Map<String, Object> item : dataResultMap) {
                insureDiseaseDTO = new InsureDiseaseDTO();
                insureDiseaseDTO.setRecordCounts(recordCounts);
                insureDiseaseDTO.setSize(size);
                insureDiseaseDTO.setNum(num);
                insureDiseaseDTO.setId(SnowflakeUtils.getId()); // ??????
                insureDiseaseDTO.setHospCode(hospCode); // ????????????
                insureDiseaseDTO.setInsureRegCode(insureRegCode); // ????????????????????????
                insureDiseaseDTO.setInsureIllnessId(MapUtils.get(item, "tcm_syndrome_id")); // ????????????ID
                insureDiseaseDTO.setInsureIllnessCode(MapUtils.get(item, "syndrome_type_code")); // ??????????????????
                insureDiseaseDTO.setInsureIllnessName(MapUtils.get(item, "syndrome_type_name")); // ??????????????????
                insureDiseaseDTO.setIcd10(MapUtils.get(item, "tcm_syndrome_id")); // ICD??????
                insureDiseaseDTO.setPym(null); // ?????????
                insureDiseaseDTO.setWbm(null); // ?????????
                insureDiseaseDTO.setLoseDate(null); //????????????
                insureDiseaseDTO.setTakeDate(null); // ????????????
                insureDiseaseDTO.setRemark(MapUtils.get(item, "memo")); // ??????
                insureDiseaseDTO.setCrteId(crteId); // ?????????ID
                insureDiseaseDTO.setCrteName(crteName); // ???????????????
                insureDiseaseDTO.setCrteTime(DateUtils.getNow()); //????????????
                insureDiseaseDTO.setDownLoadType(listType);
                insureDiseaseDTO.setVer(MapUtils.get(item, "ver"));
                if (num > (recordCounts / size)) {
                    insureDiseaseDTO.setVerName(MapUtils.get(item, "ver_name"));
                } else {
                    insureDiseaseDTO.setVerName(ver); // ?????????
                }
                insureDiseaseDTOList.add(insureDiseaseDTO);
            }
            if (!ListUtils.isEmpty(insureDiseaseDTOList)) {
                insureDiseaseDAO.insertDisease(insureDiseaseDTOList);
            }
        }
    }

    /**
     * @Method insert_1314
     * @Desrciption ??????????????????????????????1314???????????????????????????
     * @Param dataResultMap , map
     * @Author fuhui
     * @Date 2021/4/9 15:18
     * @Return
     **/
    private void insert_1314(List<Map<String, Object>> dataResultMap, Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String crteId = MapUtils.get(map, "crteId");
        String listType = MapUtils.get(map, "downLoadType");
        String crteName = MapUtils.get(map, "crteName");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        List<InsureDiseaseDTO> insureDiseaseDTOList = new ArrayList<>();
        int size = MapUtils.get(map, "size");
        int num = MapUtils.get(map, "num");
        int recordCounts = MapUtils.get(map, "recordCounts");
        InsureDiseaseDTO insureDiseaseDTO = null;
        if (!ListUtils.isEmpty(dataResultMap)) {
            for (Map<String, Object> item : dataResultMap) {
                String item2 = JSON.toJSONString(item);
                insureDiseaseDTO = new InsureDiseaseDTO();
                insureDiseaseDTO.setRecordCounts(recordCounts);
                insureDiseaseDTO.setSize(size);
                insureDiseaseDTO.setNum(num);
                insureDiseaseDTO.setId(SnowflakeUtils.getId()); // ??????
                insureDiseaseDTO.setHospCode(hospCode); // ????????????
                insureDiseaseDTO.setInsureRegCode(insureRegCode); // ????????????????????????
                insureDiseaseDTO.setInsureIllnessId(MapUtils.get(item, "dise_caty_code")); // ????????????ID
                insureDiseaseDTO.setInsureIllnessCode(MapUtils.get(item, "dise_caty_code")); // ??????????????????
                insureDiseaseDTO.setInsureIllnessName(MapUtils.get(item, "dise_caty_name")); // ??????????????????
                insureDiseaseDTO.setIcd10(MapUtils.get(item, "dise_caty_code")); // ICD??????
                insureDiseaseDTO.setPym(null); // ?????????
                insureDiseaseDTO.setWbm(null); // ?????????
                insureDiseaseDTO.setLoseDate(null); //????????????
                insureDiseaseDTO.setTakeDate(null); // ????????????
                insureDiseaseDTO.setRemark(MapUtils.get(item, "memo")); // ??????
                insureDiseaseDTO.setCrteId(crteId); // ?????????ID
                insureDiseaseDTO.setCrteName(crteName); // ???????????????
                insureDiseaseDTO.setCrteTime(DateUtils.getNow()); //????????????
                insureDiseaseDTO.setDownLoadType(listType);
                insureDiseaseDTO.setVer(MapUtils.get(item, "ver"));
                insureDiseaseDTO.setVerName(MapUtils.get(item, "ver_name"));
                insureDiseaseDTOList.add(insureDiseaseDTO);
            }
            if (!ListUtils.isEmpty(insureDiseaseDTOList)) {
                insureDiseaseDAO.insertDisease(insureDiseaseDTOList);
            }
        }
    }


    /**
     * @Method insert_1313
     * @Desrciption ??????????????????????????????????????????????????????
     * @Param dataResultMap , map
     * @Author fuhui
     * @Date 2021/4/9 15:18
     * @Return
     **/
    private void insert_1313(List<Map<String, Object>> dataResultMap, Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String crteId = MapUtils.get(map, "crteId");
        String listType = MapUtils.get(map, "downLoadType");
        String crteName = MapUtils.get(map, "crteName");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        List<InsureDiseaseDTO> insureDiseaseDTOList = new ArrayList<>();
        int size = MapUtils.get(map, "size");
        int num = MapUtils.get(map, "num");
        int recordCounts = MapUtils.get(map, "recordCounts");
        InsureDiseaseDTO insureDiseaseDTO = null;
        if (!ListUtils.isEmpty(dataResultMap)) {
            for (Map<String, Object> item : dataResultMap) {
                insureDiseaseDTO = new InsureDiseaseDTO();
                insureDiseaseDTO.setRecordCounts(recordCounts);
                insureDiseaseDTO.setSize(size);
                insureDiseaseDTO.setNum(num);
                insureDiseaseDTO.setId(SnowflakeUtils.getId()); // ??????
                insureDiseaseDTO.setHospCode(hospCode); // ????????????
                insureDiseaseDTO.setInsureRegCode(insureRegCode); // ????????????????????????
                insureDiseaseDTO.setInsureIllnessId(MapUtils.get(item, "tumour_morphology_id")); // ????????????ID
                insureDiseaseDTO.setInsureIllnessCode(MapUtils.get(item, "morphology_classify_code")); // ??????????????????
                insureDiseaseDTO.setInsureIllnessName(MapUtils.get(item, "morphology_classify_name")); // ??????????????????
                insureDiseaseDTO.setIcd10(MapUtils.get(item, "tumour_morphology_id")); // ICD??????
                insureDiseaseDTO.setPym(null); // ?????????
                insureDiseaseDTO.setWbm(null); // ?????????
                insureDiseaseDTO.setLoseDate(null); //????????????
                insureDiseaseDTO.setTakeDate(null); // ????????????
                insureDiseaseDTO.setRemark(MapUtils.get(item, "memo")); // ??????
                insureDiseaseDTO.setCrteId(crteId); // ?????????ID
                insureDiseaseDTO.setCrteName(crteName); // ???????????????
                insureDiseaseDTO.setCrteTime(DateUtils.getNow()); //????????????
                insureDiseaseDTO.setDownLoadType(listType);
                insureDiseaseDTO.setVer(MapUtils.get(item, "ver"));
                insureDiseaseDTO.setVerName(MapUtils.get(item, "ver_name"));
                insureDiseaseDTOList.add(insureDiseaseDTO);
            }
            if (!ListUtils.isEmpty(insureDiseaseDTOList)) {
                insureDiseaseDAO.insertDisease(insureDiseaseDTOList);
            }
        }
    }
    /**
     * @Method insert_1313
     * @Desrciption ?????????????????????????????????????????????????????????
     * @Param dataResultMap , map
     * @Author fuhui
     * @Date 2021/4/9 15:18
     * @Return
     **/
    private void insert_1318(List<Map<String, Object>> dataResultMap, Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String crteId = MapUtils.get(map, "crteId");
        String listType = MapUtils.get(map, "downLoadType");
        String crteName = MapUtils.get(map, "crteName");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        List<InsureDiseaseDTO> insureDiseaseDTOList = new ArrayList<>();
        int size = MapUtils.get(map, "size");
        int num = MapUtils.get(map, "num");
        String ver = MapUtils.get(map, "ver");
        int recordCounts = MapUtils.get(map, "recordCounts");
        if (!ListUtils.isEmpty(dataResultMap)) {
            List<InsureUnifiedLimitPriceDO> insertList = new ArrayList<>();
            InsureUnifiedLimitPriceDO insureUnifiedLimitPriceDO = null;
            for (Map<String, Object> item : dataResultMap) {
                    insureUnifiedLimitPriceDO = new InsureUnifiedLimitPriceDO();
                    insureUnifiedLimitPriceDO.setId(SnowflakeUtils.getId());
                    insureUnifiedLimitPriceDO.setHospCode(hospCode);
                    insureUnifiedLimitPriceDO.setHilistCode(MapUtils.get(item, "hilist_code"));
                    insureUnifiedLimitPriceDO.setHilistLmtpricType(MapUtils.get(item, "hilist_lmtpric_type"));
                    insureUnifiedLimitPriceDO.setOverlmtDspoWay(MapUtils.get(item, "overlmt_dspo_way"));
                    insureUnifiedLimitPriceDO.setInsuAdmdvs(MapUtils.get(item, "insu_admdvs"));
                    if (StringUtils.isEmpty(MapUtils.get(item, "begndate"))) {
                        insureUnifiedLimitPriceDO.setBegndate(null);
                    } else {
                        insureUnifiedLimitPriceDO.setBegndate(DateUtils.parse(MapUtils.get(item, "begndate"), DateUtils.Y_M_DH_M_S));
                    }
                    if (StringUtils.isEmpty(MapUtils.get(item, "enddate"))) {
                        insureUnifiedLimitPriceDO.setEnddate(null);
                    } else {
                        insureUnifiedLimitPriceDO.setEnddate(DateUtils.parse(MapUtils.get(item, "enddate"), DateUtils.Y_M_DH_M_S));
                    }
                    insureUnifiedLimitPriceDO.setHilistPricUplmtAmt(MapUtils.get(item, "hilist_pric_uplmt_amt"));
                    insureUnifiedLimitPriceDO.setValiFlag(MapUtils.get(item, "vali_flag"));
                    insureUnifiedLimitPriceDO.setRid(MapUtils.get(item, "rid"));


                    if (StringUtils.isEmpty(MapUtils.get(item, "crte_time"))) {
                        insureUnifiedLimitPriceDO.setCrteTime(null);
                    } else {
                        insureUnifiedLimitPriceDO.setCrteTime(DateUtils.parse(MapUtils.get(item, "crte_time"), DateUtils.Y_M_DH_M_S));
                    }
                    insureUnifiedLimitPriceDO.setOptTime(null);
                    if (StringUtils.isEmpty(MapUtils.get(item, "updt_time"))) {
                        insureUnifiedLimitPriceDO.setUpdtTime(null);
                    } else {
                        insureUnifiedLimitPriceDO.setUpdtTime(DateUtils.parse(MapUtils.get(item, "updt_time"), DateUtils.Y_M_DH_M_S));
                    }

                    insureUnifiedLimitPriceDO.setCrterId(MapUtils.get(item, "crter_id"));
                    insureUnifiedLimitPriceDO.setCrterName(MapUtils.get(item, "crter_name"));
                    insureUnifiedLimitPriceDO.setCrteOptinsNo(MapUtils.get(item, "crte_optins_no"));
                    insureUnifiedLimitPriceDO.setOpterId(MapUtils.get(item, "opter_id"));
                    insureUnifiedLimitPriceDO.setOpterName(MapUtils.get(item, "opter_name"));
                    insureUnifiedLimitPriceDO.setOptinsNo(MapUtils.get(item, "optins_no"));
                    insureUnifiedLimitPriceDO.setTabname(MapUtils.get(item, "tabname"));
                    insureUnifiedLimitPriceDO.setPoolareaNo(MapUtils.get(item, "poolarea_no"));
                    insertList.add(insureUnifiedLimitPriceDO);

            }
            if (!ListUtils.isEmpty(insertList)) {
                int toIndex = 1000;
                for (int i = 0; i < insertList.size(); i += 1000) {
                    if (i + 1000 > insertList.size()) {
                        toIndex = insertList.size() - i;
                    }
                    List newList = insertList.subList(i, i + toIndex);
                    insureDirectoryDownLoadDAO.insertInsureUnfiedLimitPrice(newList);
                }
            }
        }

    }
    /**
     * @Method insert_1313
     * @Desrciption ??????????????????????????????????????????????????????????????????
     * @Param dataResultMap , map
     * @Author fuhui
     * @Date 2021/4/9 15:18
     * @Return
     **/
    private void insert_1319(List<Map<String, Object>> dataResultMap, Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String crteId = MapUtils.get(map, "crteId");
        String listType = MapUtils.get(map, "downLoadType");
        String crteName = MapUtils.get(map, "crteName");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        List<InsureDiseaseDTO> insureDiseaseDTOList = new ArrayList<>();
        int size = MapUtils.get(map, "size");
        int num = MapUtils.get(map, "num");
        String ver = MapUtils.get(map, "ver");
        int recordCounts = MapUtils.get(map, "recordCounts");
        if (!ListUtils.isEmpty(dataResultMap)) {
            InsureUnifidRatioDO insureUnifidRatioDO = null;
            List<InsureUnifidRatioDO> insureDirectoryInfoDOList = new ArrayList<>();
            for (Map<String, Object> item : dataResultMap) {
                    insureUnifidRatioDO = new InsureUnifidRatioDO();
                    insureUnifidRatioDO.setId(SnowflakeUtils.getId());
                    insureUnifidRatioDO.setHospCode(hospCode);
                    insureUnifidRatioDO.setHilistCode(MapUtils.get(item, "hilist_code"));
                    insureUnifidRatioDO.setSelfpayPropPsnType(MapUtils.get(item, "selfpay_prop_psn_type"));
                    insureUnifidRatioDO.setSelfpayPropType(MapUtils.get(item, "selfpay_prop_type"));
                    if (StringUtils.isEmpty(MapUtils.get(item, "begndate"))) {
                        insureUnifidRatioDO.setBegndate(null);
                    } else {
                        insureUnifidRatioDO.setBegndate(DateUtils.parse(MapUtils.get(item, "begndate"), DateUtils.Y_M_DH_M_S));
                    }
                    if (StringUtils.isEmpty(MapUtils.get(item, "enddate"))) {
                        insureUnifidRatioDO.setEnddate(null);
                    } else {
                        insureUnifidRatioDO.setEnddate(DateUtils.parse(MapUtils.get(item, "enddate"), DateUtils.Y_M_DH_M_S));
                    }
                    insureUnifidRatioDO.setInsuAdmdvs(MapUtils.get(item, "insu_admdvs"));
                    insureUnifidRatioDO.setSelfpayProp(MapUtils.get(item, "selfpay_prop"));
                    insureUnifidRatioDO.setValiFlag(MapUtils.get(item, "vali_flag"));
                    insureUnifidRatioDO.setRid(MapUtils.get(item, "rid"));

                    if(StringUtils.isNotEmpty(MapUtils.get(item, "updt_time"))){
                        insureUnifidRatioDO.setUpdtTime(DateUtils.parse(MapUtils.get(item, "updt_time"), DateUtils.Y_M_DH_M_S));
                    }else{
                        insureUnifidRatioDO.setUpdtTime(null);
                    }

                    insureUnifidRatioDO.setCrterId(MapUtils.get(item, "crter_id"));
                    insureUnifidRatioDO.setCrterName(MapUtils.get(item, "crter_name"));
                    if(StringUtils.isNotEmpty(MapUtils.get(item, "crte_time"))){
                        insureUnifidRatioDO.setCrteTime(DateUtils.parse(MapUtils.get(item, "crte_time"), DateUtils.Y_M_DH_M_S));
                    }else{
                        insureUnifidRatioDO.setCrteTime(null);
                    }
                    insureUnifidRatioDO.setCrteOptinsNo(MapUtils.get(item, "crte_optins_no"));
                    insureUnifidRatioDO.setOpterId(MapUtils.get(item, "opter_id"));
                    insureUnifidRatioDO.setOpterName(MapUtils.get(item, "opter_name"));
                    insureUnifidRatioDO.setOptinsNo(MapUtils.get(item, "optins_no"));
                    insureUnifidRatioDO.setPoolareaNo(MapUtils.get(item, "poolarea_no"));

                    if(StringUtils.isNotEmpty(MapUtils.get(item, "opt_time"))){
                        insureUnifidRatioDO.setOptTime(DateUtils.parse(MapUtils.get(item, "opt_time"), DateUtils.Y_M_DH_M_S));
                    }else{
                        insureUnifidRatioDO.setOptTime(null);
                    }
                    insureDirectoryInfoDOList.add(insureUnifidRatioDO);
                }

            if (!ListUtils.isEmpty(insureDirectoryInfoDOList)) {
                int toIndex = 1000;
                for (int i = 0; i < insureDirectoryInfoDOList.size(); i += 1000) {
                    if (i + 1000 > insureDirectoryInfoDOList.size()) {
                        toIndex = insureDirectoryInfoDOList.size() - i;
                    }
                    List newList = insureDirectoryInfoDOList.subList(i, i + toIndex);
                    insureDirectoryDownLoadDAO.insertInsureUnifiedRation(newList);
                }
            }
            if (!ListUtils.isEmpty(insureDiseaseDTOList)) {
                insureDiseaseDAO.insertDisease(insureDiseaseDTOList);
            }
        }
    }


    /**
     * @Method insert_1309
     * @Desrciption ???????????????????????????????????????????????????????????????
     * @Param dataResultMap , map
     * @Author fuhui
     * @Date 2021/4/9 15:18
     * @Return
     **/
    private void insert_1311(List<Map<String, Object>> dataResultMap, Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String crteId = MapUtils.get(map, "crteId");
        String listType = MapUtils.get(map, "downLoadType");
        String crteName = MapUtils.get(map, "crteName");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        List<InsureDiseaseDTO> insureDiseaseDTOList = new ArrayList<>();
        int size = MapUtils.get(map, "size");
        int num = MapUtils.get(map, "num");
        int recordCounts = MapUtils.get(map, "recordCounts");
        String ver = MapUtils.get(map, "ver");
        InsureDiseaseDTO insureDiseaseDTO = null;
        if (!ListUtils.isEmpty(dataResultMap)) {
            for (Map<String, Object> item : dataResultMap) {
                insureDiseaseDTO = new InsureDiseaseDTO();
                insureDiseaseDTO.setRecordCounts(recordCounts);
                insureDiseaseDTO.setSize(size);
                insureDiseaseDTO.setNum(num);
                insureDiseaseDTO.setId(SnowflakeUtils.getId()); // ??????
                insureDiseaseDTO.setHospCode(hospCode); // ????????????
                insureDiseaseDTO.setInsureRegCode(insureRegCode); // ????????????????????????
                insureDiseaseDTO.setInsureIllnessId(null); // ????????????ID
                insureDiseaseDTO.setInsureIllnessCode(MapUtils.get(item, "daytime_oprt_dise_code")); // ??????????????????
                insureDiseaseDTO.setInsureIllnessName(MapUtils.get(item, "daytime_oprt_dise_name")); // ??????????????????
                insureDiseaseDTO.setIcd10(null); // ICD??????
                insureDiseaseDTO.setPym(null); // ?????????
                insureDiseaseDTO.setWbm(null); // ?????????
                insureDiseaseDTO.setLoseDate(null); //????????????
                insureDiseaseDTO.setTakeDate(null); // ????????????
                insureDiseaseDTO.setRemark(MapUtils.get(item, "memo")); // ??????
                insureDiseaseDTO.setCrteId(crteId); // ?????????ID
                insureDiseaseDTO.setCrteName(crteName); // ???????????????
                insureDiseaseDTO.setCrteTime(DateUtils.getNow()); //????????????
                insureDiseaseDTO.setDownLoadType(listType);
                insureDiseaseDTO.setVer(MapUtils.get(item, "ver"));
                if (num > (recordCounts / size)) {
                    insureDiseaseDTO.setVerName(MapUtils.get(item, "ver_name"));
                } else {
                    insureDiseaseDTO.setVerName(ver); // ?????????
                }
                insureDiseaseDTOList.add(insureDiseaseDTO);
            }
            if (!ListUtils.isEmpty(insureDiseaseDTOList)) {
                insureDiseaseDAO.insertDisease(insureDiseaseDTOList);
            }
        }
    }

    /**
     * @Method insert_1309
     * @Desrciption ????????????????????????????????????????????????????????????
     * @Param dataResultMap , map
     * @Author fuhui
     * @Date 2021/4/9 15:18
     * @Return
     **/
    private void insert_1310(List<Map<String, Object>> dataResultMap, Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String crteId = MapUtils.get(map, "crteId");
        String listType = MapUtils.get(map, "downLoadType");
        String crteName = MapUtils.get(map, "crteName");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        List<InsureDiseaseDTO> insureDiseaseDTOList = new ArrayList<>();
        int size = MapUtils.get(map, "size");
        int num = MapUtils.get(map, "num");
        String ver = MapUtils.get(map, "ver");
        int recordCounts = MapUtils.get(map, "recordCounts");
        InsureDiseaseDTO insureDiseaseDTO = null;
        if (!ListUtils.isEmpty(dataResultMap)) {
            for (Map<String, Object> item : dataResultMap) {
                insureDiseaseDTO = new InsureDiseaseDTO();
                insureDiseaseDTO.setRecordCounts(recordCounts);
                insureDiseaseDTO.setSize(size);
                insureDiseaseDTO.setNum(num);
                insureDiseaseDTO.setId(SnowflakeUtils.getId()); // ??????
                insureDiseaseDTO.setHospCode(hospCode); // ????????????
                insureDiseaseDTO.setInsureRegCode(insureRegCode); // ????????????????????????
                insureDiseaseDTO.setInsureIllnessId(MapUtils.get(item, "ise_selt_list_id")); // ????????????IDdise_selt_list_id
                insureDiseaseDTO.setInsureIllnessCode(MapUtils.get(item, "dise_selt_list_code")); // ??????????????????
                insureDiseaseDTO.setInsureIllnessName(MapUtils.get(item, "dise_selt_list_name")); // ??????????????????
                System.out.println("=============" + MapUtils.get(item, "dise_selt_list_name"));
                insureDiseaseDTO.setIcd10(MapUtils.get(item, "ise_selt_list_id")); // ICD??????
                insureDiseaseDTO.setPym(null); // ?????????
                insureDiseaseDTO.setWbm(null); // ?????????
                insureDiseaseDTO.setLoseDate(null); //????????????
                insureDiseaseDTO.setTakeDate(null); // ????????????
                insureDiseaseDTO.setRemark(MapUtils.get(item, "memo")); // ??????
                insureDiseaseDTO.setCrteId(crteId); // ?????????ID
                insureDiseaseDTO.setCrteName(crteName); // ???????????????
                insureDiseaseDTO.setCrteTime(DateUtils.getNow()); //????????????
                insureDiseaseDTO.setDownLoadType(listType);
                insureDiseaseDTO.setVer(MapUtils.get(item, "ver"));
                if (num > (recordCounts / size)) {
                    insureDiseaseDTO.setVerName(MapUtils.get(item, "ver_name"));
                } else {
                    insureDiseaseDTO.setVerName(ver); // ?????????
                }
                insureDiseaseDTOList.add(insureDiseaseDTO);
            }
            int batchCount = 1000; // ?????????????????????????????????
            int k = 0;
            if (!ListUtils.isEmpty(insureDiseaseDTOList)) {

                int toIndex = 1000;
                for (int i = 0; i < insureDiseaseDTOList.size(); i += 1000) {
                    if (i + 1000 > insureDiseaseDTOList.size()) {
                        toIndex = insureDiseaseDTOList.size() - i;
                    }
                    List newList = insureDiseaseDTOList.subList(i, i + toIndex);
                    insureDiseaseDAO.insertDisease(newList);
                }
            }
        }
    }

    /**
     * @Method insert_1309
     * @Desrciption ?????????????????????????????????????????????????????????
     * @Param dataResultMap , map
     * @Author fuhui
     * @Date 2021/4/9 15:18
     * @Return
     **/
    private void insert_1309(List<Map<String, Object>> dataResultMap, Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String crteId = MapUtils.get(map, "crteId");
        String listType = MapUtils.get(map, "downLoadType");
        String crteName = MapUtils.get(map, "crteName");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        List<InsureDiseaseDTO> insureDiseaseDTOList = new ArrayList<>();
        int size = MapUtils.get(map, "size");
        int num = MapUtils.get(map, "num");
        String ver = MapUtils.get(map, "ver");
        int recordCounts = MapUtils.get(map, "recordCounts");
        InsureDiseaseDTO insureDiseaseDTO = null;
        if (!ListUtils.isEmpty(dataResultMap)) {
            for (Map<String, Object> item : dataResultMap) {
                insureDiseaseDTO = new InsureDiseaseDTO();
                insureDiseaseDTO.setRecordCounts(recordCounts);
                insureDiseaseDTO.setSize(size);
                insureDiseaseDTO.setNum(num);
                insureDiseaseDTO.setId(SnowflakeUtils.getId()); // ??????
                insureDiseaseDTO.setHospCode(hospCode); // ????????????
                insureDiseaseDTO.setInsureRegCode(insureRegCode); // ????????????????????????
                insureDiseaseDTO.setInsureIllnessId(null); // ????????????ID
                insureDiseaseDTO.setInsureIllnessCode(MapUtils.get(item, "opsp_dise_code")); // ??????????????????
                insureDiseaseDTO.setInsureIllnessName(MapUtils.get(item, "opsp_dise_name")); // ??????????????????
                insureDiseaseDTO.setIcd10(null); // ICD??????
                insureDiseaseDTO.setPym(null); // ?????????
                insureDiseaseDTO.setWbm(null); // ?????????
                insureDiseaseDTO.setLoseDate(null); //????????????
                insureDiseaseDTO.setTakeDate(null); // ????????????
                insureDiseaseDTO.setRemark(MapUtils.get(item, "memo")); // ??????
                insureDiseaseDTO.setCrteId(crteId); // ?????????ID
                insureDiseaseDTO.setCrteName(crteName); // ???????????????
                insureDiseaseDTO.setCrteTime(DateUtils.getNow()); //????????????
                insureDiseaseDTO.setDownLoadType(listType);
                insureDiseaseDTO.setVer(MapUtils.get(item, "ver"));
                if (num > (recordCounts / size)) {
                    insureDiseaseDTO.setVerName(MapUtils.get(item, "ver_name"));
                } else {
                    insureDiseaseDTO.setVerName(ver); // ?????????
                }
                insureDiseaseDTOList.add(insureDiseaseDTO);
            }
            if (!ListUtils.isEmpty(insureDiseaseDTOList)) {
                insureDiseaseDAO.insertDisease(insureDiseaseDTOList);
            }
        }
    }

    /**
     * @Method insert_1308
     * @Desrciption ??????????????????????????? ????????????????????????
     * @Param dataResultMap , map
     * @Author fuhui
     * @Date 2021/4/9 15:18
     * @Return
     **/
    private void insert_1308(List<Map<String, Object>> dataResultMap, Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String crteId = MapUtils.get(map, "crteId");
        String listType = MapUtils.get(map, "downLoadType");
        String crteName = MapUtils.get(map, "crteName");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        List<InsureDiseaseDTO> insureDiseaseDTOList = new ArrayList<>();
        int size = MapUtils.get(map, "size");
        int num = MapUtils.get(map, "num");
        int recordCounts = MapUtils.get(map, "recordCounts");
        String ver = MapUtils.get(map, "ver");
        InsureDiseaseDTO insureDiseaseDTO = null;
        if (!ListUtils.isEmpty(dataResultMap)) {
            for (Map<String, Object> item : dataResultMap) {
                insureDiseaseDTO = new InsureDiseaseDTO();
                insureDiseaseDTO.setRecordCounts(recordCounts);
                insureDiseaseDTO.setSize(size);
                insureDiseaseDTO.setNum(num);
                insureDiseaseDTO.setId(SnowflakeUtils.getId()); // ??????
                insureDiseaseDTO.setHospCode(hospCode); // ????????????
                insureDiseaseDTO.setInsureRegCode(insureRegCode); // ????????????????????????
                insureDiseaseDTO.setInsureIllnessId(null); // ????????????ID
                insureDiseaseDTO.setInsureIllnessCode(MapUtils.get(item, "oprn_oprt_code")); // ??????????????????
                insureDiseaseDTO.setInsureIllnessName(MapUtils.get(item, "oprn_oprt_name")); // ??????????????????
                insureDiseaseDTO.setIcd10(null); // ICD??????
                insureDiseaseDTO.setPym(null); // ?????????
                insureDiseaseDTO.setWbm(null); // ?????????
                insureDiseaseDTO.setLoseDate(null); //????????????
                insureDiseaseDTO.setTakeDate(null); // ????????????
                insureDiseaseDTO.setRemark(MapUtils.get(item, "memo")); // ??????
                insureDiseaseDTO.setCrteId(crteId); // ?????????ID
                insureDiseaseDTO.setCrteName(crteName); // ???????????????
                insureDiseaseDTO.setCrteTime(DateUtils.getNow()); //????????????
                insureDiseaseDTO.setDownLoadType(listType);
                insureDiseaseDTO.setVer(MapUtils.get(item, "ver"));
                insureDiseaseDTO.setVerName(MapUtils.get(item, "ver_name"));
                insureDiseaseDTOList.add(insureDiseaseDTO);

            }
            if (!ListUtils.isEmpty(insureDiseaseDTOList)) {
                insureDiseaseDAO.insertDisease(insureDiseaseDTOList);

            }
        }
    }

    /**
     * @Method insert_1307
     * @Desrciption ??????????????????????????? ???????????????????????????
     * @Param dataResultMap , map
     * @Author fuhui
     * @Date 2021/4/9 15:18
     * @Return
     **/
    private void insert_1307(List<Map<String, Object>> dataResultMap, Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String crteId = MapUtils.get(map, "crteId");
        String listType = MapUtils.get(map, "downLoadType");
        String crteName = MapUtils.get(map, "crteName");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        List<InsureDiseaseDTO> insureDiseaseDTOList = new ArrayList<>();
        int size = MapUtils.get(map, "size");
        int num = MapUtils.get(map, "num");
        int recordCounts = MapUtils.get(map, "recordCounts");
        String ver = MapUtils.get(map, "ver");
        InsureDiseaseDTO insureDiseaseDTO = null;
        if (!ListUtils.isEmpty(dataResultMap)) {
            for (Map<String, Object> item : dataResultMap) {
                insureDiseaseDTO = new InsureDiseaseDTO();
                insureDiseaseDTO.setRecordCounts(recordCounts);
                insureDiseaseDTO.setSize(size);
                insureDiseaseDTO.setNum(num);
                insureDiseaseDTO.setId(SnowflakeUtils.getId()); // ??????
                insureDiseaseDTO.setHospCode(hospCode); // ????????????
                insureDiseaseDTO.setInsureRegCode(insureRegCode); // ????????????????????????
                insureDiseaseDTO.setInsureIllnessId(MapUtils.get(item, "illness_id")); // ????????????ID
                insureDiseaseDTO.setInsureIllnessCode(MapUtils.get(item, "diag_code")); // ??????????????????
                insureDiseaseDTO.setInsureIllnessName(MapUtils.get(item, "diag_name")); // ??????????????????
                insureDiseaseDTO.setIcd10(MapUtils.get(item, "illness_id")); // ICD??????
                insureDiseaseDTO.setPym(null); // ?????????
                insureDiseaseDTO.setWbm(null); // ?????????
                insureDiseaseDTO.setLoseDate(null); //????????????
                insureDiseaseDTO.setTakeDate(null); // ????????????
                insureDiseaseDTO.setRemark(MapUtils.get(item, "memo")); // ??????
                insureDiseaseDTO.setCrteId(crteId); // ?????????ID
                insureDiseaseDTO.setCrteName(crteName); // ???????????????
                insureDiseaseDTO.setCrteTime(DateUtils.getNow()); //????????????
                insureDiseaseDTO.setDownLoadType(listType);
                insureDiseaseDTO.setVer(MapUtils.get(item, "ver"));
                insureDiseaseDTO.setVerName(MapUtils.get(item, "ver_name"));
                insureDiseaseDTOList.add(insureDiseaseDTO);
            }
            if (!ListUtils.isEmpty(insureDiseaseDTOList)) {
                insureDiseaseDAO.insertDisease(insureDiseaseDTOList);

            }
        }
    }


    /**
     * @Method insert_1306
     * @Desrciption ??????????????????????????? ????????????????????????
     * @Param dataResultMap , map
     * @Author fuhui
     * @Date 2021/4/9 15:18
     * @Return
     **/
    private void insert_1306(List<Map<String, Object>> dataResultMap, Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String crteId = MapUtils.get(map, "crteId");
        String listType = MapUtils.get(map, "downLoadType");
        String crteName = MapUtils.get(map, "crteName");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        List<InsureItemDTO> insureItemDTOList = new ArrayList<>();
        int size = MapUtils.get(map, "size");
        int num = MapUtils.get(map, "num");
        String ver = MapUtils.get(map, "ver");
        int recordCounts = MapUtils.get(map, "recordCounts");
        logger.info("?????????????????????:" + dataResultMap.size());
        InsureItemDTO itemDTO = null;
        if (!ListUtils.isEmpty(dataResultMap)) {
            for (Map<String, Object> item : dataResultMap) {
                itemDTO = new InsureItemDTO();
                itemDTO.setRecordCounts(recordCounts);
                itemDTO.setSize(size);
                itemDTO.setNum(num);
                // ??????
                itemDTO.setId(SnowflakeUtils.getId());
                // ????????????
                itemDTO.setHospCode(hospCode);
                // ??????????????????
                itemDTO.setInsureRegCode(insureRegCode);
                // ??????????????????  --????????????
                itemDTO.setItemMark("301");
                //????????????????????????
                itemDTO.setItemCode(MapUtils.get(item, "med_list_codg"));
                // ????????????????????????
                itemDTO.setItemName(MapUtils.get(item, "ins_common_name"));
                //????????????????????????
                itemDTO.setItemType("301");
                // ????????????????????????
                itemDTO.setItemSpec(MapUtils.get(item, "spec"));
                // ????????????????????????
                itemDTO.setItemDosage("");
                // ????????????????????????
                itemDTO.setItemPrice(null);
                // ????????????????????????
                itemDTO.setItemUnitCode(MapUtils.get(item, "min_useunt"));
                // ????????????
                itemDTO.setProd(MapUtils.get(item, "manu_name"));
                // ????????????
                itemDTO.setDeductible(null);
                // ??????
                itemDTO.setCheckPrice(null);
                // ?????????????????????0.??????1.??????2.????????????
                itemDTO.setDirectory(null);
                itemDTO.setTakeDate(null);
                itemDTO.setLoseDate(null);
                // ?????????
                itemDTO.setPym(null);
                // ?????????
                itemDTO.setWbm(null);
                // ???????????????SF???
                itemDTO.setIsValid(MapUtils.get(item, "vali_flag"));
                // ?????????ID
                itemDTO.setCrteId(crteId);
                // ???????????????
                itemDTO.setCrteName(crteName);
                // ????????????
                itemDTO.setCrteTime(DateUtils.getNow());
                itemDTO.setCompoundLogo(null);
                itemDTO.setVerName(MapUtils.get(item, "ver_name"));
                itemDTO.setVer(MapUtils.get(item, "ver")); // ?????????
                itemDTO.setLmtUsedFlag(MapUtils.get(item, "lmt_used_flag"));
                itemDTO.setDownLoadType(listType);
                insureItemDTOList.add(itemDTO);
            }
            if (!ListUtils.isEmpty(insureItemDTOList)) {
                int toIndex = 1000;
                for (int i = 0; i < insureItemDTOList.size(); i += 1000) {
                    if (i + 1000 > insureItemDTOList.size()) {
                        toIndex = insureItemDTOList.size() - i;
                    }
                    List newList = insureItemDTOList.subList(i, i + toIndex);
                    insureItemDAO.insertInsureItem(newList);
                }
            }
        }
    }

    /**
     * @Method insert_1305
     * @Desrciption ??????????????????????????? ??????????????????????????????
     * @Param dataResultMap , map
     * @Author fuhui
     * @Date 2021/4/9 15:18
     * @Return
     **/
    private void insert_1305(List<Map<String, Object>> dataResultMap, Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String crteId = MapUtils.get(map, "crteId");
        String listType = MapUtils.get(map, "downLoadType");
        String crteName = MapUtils.get(map, "crteName");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        List<InsureItemDTO> insureItemDTOList = new ArrayList<>();
        System.out.println("????????????????????????:" + dataResultMap.size());
        int size = MapUtils.get(map, "size");
        int num = MapUtils.get(map, "num");
        int recordCounts = MapUtils.get(map, "recordCounts");
        String ver = MapUtils.get(map, "ver");
        InsureItemDTO itemDTO = null;
        if (!ListUtils.isEmpty(dataResultMap)) {
            for (Map<String, Object> item : dataResultMap) {
                itemDTO = new InsureItemDTO();
                itemDTO.setRecordCounts(recordCounts);
                itemDTO.setSize(size);
                itemDTO.setNum(num);
                // ??????
                itemDTO.setId(SnowflakeUtils.getId());
                // ????????????
                itemDTO.setHospCode(hospCode);
                // ??????????????????
                itemDTO.setInsureRegCode(insureRegCode);
                // ??????????????????  --????????????
                itemDTO.setItemMark("201");
                //????????????????????????
                itemDTO.setItemCode(MapUtils.get(item, "med_list_codg"));
                // ????????????????????????
                itemDTO.setItemName(MapUtils.get(item, "med_item_name"));
                //????????????????????????
                itemDTO.setItemType("201");
                // ????????????????????????
                itemDTO.setItemSpec(MapUtils.get(item, "spec"));
                // ????????????????????????
                itemDTO.setItemDosage("");
                // ????????????????????????
                itemDTO.setItemPrice(null);
                // ????????????????????????
                itemDTO.setItemUnitCode(MapUtils.get(item, "min_useunt"));
                // ????????????
                itemDTO.setProd(MapUtils.get(item, "manu_name"));
                // ????????????
                itemDTO.setDeductible(null);
                // ??????
                itemDTO.setCheckPrice(null);
                // ?????????????????????0.??????1.??????2.????????????
                itemDTO.setDirectory(null);
                itemDTO.setTakeDate(null);
                itemDTO.setLoseDate(null);
                // ?????????
                itemDTO.setPym(null);
                // ?????????
                itemDTO.setWbm(null);
                // ???????????????SF???
                itemDTO.setIsValid(MapUtils.get(item, "vali_flag"));
                // ?????????ID
                itemDTO.setCrteId(crteId);
                // ???????????????
                itemDTO.setCrteName(crteName);
                // ????????????
                itemDTO.setCrteTime(DateUtils.getNow());
                itemDTO.setLmtUsedFlag(MapUtils.get(item, "lmt_used_flag"));  // ??????????????????
                itemDTO.setCompoundLogo(MapUtils.get(item, "compound_logo"));
                itemDTO.setVer(MapUtils.get(item, "ver")); // ?????????
                // ??????????????????????????????????????????????????????????????????  ???????????????????????????????????????????????????
                itemDTO.setVerName(MapUtils.get(item, "ver_name"));
                itemDTO.setDownLoadType(listType);
                insureItemDTOList.add(itemDTO);
            }
            if (!ListUtils.isEmpty(insureItemDTOList)) {
                insureItemDAO.insertInsureItem(insureItemDTOList);
            }
        }
    }

    /**
     * @Method insert_1303
     * @Desrciption ??????????????????????????? ??????????????????????????????
     * @Param dataResultMap , map
     * @Author fuhui
     * @Date 2021/4/9 15:18
     * @Return
     **/
    private void insert_1303(List<Map<String, Object>> dataResultMap, Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String crteId = MapUtils.get(map, "crteId");
        String listType = MapUtils.get(map, "downLoadType");
        String crteName = MapUtils.get(map, "crteName");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        List<InsureItemDTO> insureItemDTOList = new ArrayList<>();
        int size = MapUtils.get(map, "size");
        int num = MapUtils.get(map, "num");
        String ver = MapUtils.get(map, "ver");
        int recordCounts = MapUtils.get(map, "recordCounts");
        InsureItemDTO itemDTO = null;
        if (!ListUtils.isEmpty(dataResultMap)) {
            for (Map<String, Object> item : dataResultMap) {
                itemDTO = new InsureItemDTO();
                // ??????
                itemDTO.setId(SnowflakeUtils.getId());
                itemDTO.setRecordCounts(recordCounts);
                itemDTO.setSize(size);
                itemDTO.setNum(num);
                // ????????????
                itemDTO.setHospCode(hospCode);
                // ??????????????????
                itemDTO.setInsureRegCode(insureRegCode);
                // ??????????????????  --????????????
                itemDTO.setItemMark("104");
                //????????????????????????
                itemDTO.setItemCode(MapUtils.get(item, "med_list_codg"));
                // ????????????????????????
                itemDTO.setItemName(MapUtils.get(item, "drug_prodname"));
                //????????????????????????
                itemDTO.setItemType("104");
                // ????????????????????????
                itemDTO.setItemSpec(MapUtils.get(item, "drug_spec"));
                // ????????????????????????
                itemDTO.setItemDosage(MapUtils.get(item, "dosform_name"));
                // ????????????????????????
                itemDTO.setItemPrice(null);
                // ????????????????????????
                itemDTO.setItemUnitCode(MapUtils.get(item, "min_useunt"));
                // ????????????
                itemDTO.setProd(MapUtils.get(item, "manufacturer_name"));
                // ????????????
                itemDTO.setDeductible(null);
                // ??????
                itemDTO.setCheckPrice(null);
                // ?????????????????????0.??????1.??????2.????????????
                itemDTO.setDirectory(MapUtils.get(item, "nat_ins_paypolicy"));
                // ????????????
                // ????????????
                itemDTO.setTakeDate(null);
                itemDTO.setLoseDate(null);
                // ?????????
                itemDTO.setPym(null);
                // ?????????
                itemDTO.setWbm(null);
                // ???????????????SF???
                itemDTO.setIsValid(MapUtils.get(item, "vali_flag"));
                // ?????????ID
                itemDTO.setCrteId(crteId);
                // ???????????????
                itemDTO.setCrteName(crteName);
                // ????????????
                itemDTO.setCrteTime(DateUtils.getNow());
                itemDTO.setCompoundLogo(MapUtils.get(item, "compound_logo"));
                itemDTO.setVer(MapUtils.get(item, "ver")); // ?????????
                // ??????????????????????????????????????????????????????????????????  ???????????????????????????????????????????????????
                itemDTO.setVerName(MapUtils.get(item, "ver_name"));
                itemDTO.setDownLoadType(listType);
                insureItemDTOList.add(itemDTO);
            }
            if (!ListUtils.isEmpty(insureItemDTOList)) {
                insureItemDAO.insertInsureItem(insureItemDTOList);
            }
        }
    }

    /**
     * @Method insert_1302
     * @Desrciption ??????????????????????????? ??????????????????
     * @Param dataResultMap , map
     * @Author fuhui
     * @Date 2021/4/9 15:18
     * @Return
     **/
    private void insert_1302(List<Map<String, Object>> dataResultMap, Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String crteId = MapUtils.get(map, "crteId");
        String listType = MapUtils.get(map, "downLoadType");
        String crteName = MapUtils.get(map, "crteName");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        List<InsureItemDTO> insureItemDTOList = new ArrayList<>();
        int size = MapUtils.get(map, "size");
        int num = MapUtils.get(map, "num");
        String ver = MapUtils.get(map, "ver");
        int recordCounts = MapUtils.get(map, "recordCounts");
        InsureItemDTO itemDTO = null;
        if (!ListUtils.isEmpty(dataResultMap)) {
            for (Map<String, Object> item : dataResultMap) {
                itemDTO = new InsureItemDTO();
                // ??????
                itemDTO.setId(SnowflakeUtils.getId());
                itemDTO.setRecordCounts(recordCounts);
                itemDTO.setSize(size);
                itemDTO.setNum(num);
                // ????????????
                itemDTO.setHospCode(hospCode);
                // ??????????????????
                itemDTO.setInsureRegCode(insureRegCode);
                // ??????????????????  --????????????
                itemDTO.setItemMark("103");
                //????????????????????????
                itemDTO.setItemCode(MapUtils.get(item, "med_list_codg"));
                // ????????????????????????
                itemDTO.setItemName(MapUtils.get(item, "med_name"));
                //????????????????????????
                itemDTO.setItemType("103");
                // ????????????????????????
                itemDTO.setItemSpec("");
                // ????????????????????????
                itemDTO.setItemDosage("");
                // ????????????????????????
                itemDTO.setItemPrice(null);
                // ????????????????????????
                itemDTO.setItemUnitCode("");
                // ????????????
                itemDTO.setProd("");
                // ????????????
                itemDTO.setDeductible("");
                // ??????
                itemDTO.setCheckPrice(null);
                // ?????????????????????0.??????1.??????2.????????????
                itemDTO.setDirectory("");
                itemDTO.setTakeDate(null);
                itemDTO.setLoseDate(null);
                // ?????????
                itemDTO.setPym("");
                // ?????????
                itemDTO.setWbm("");
                // ???????????????SF???
                itemDTO.setIsValid(MapUtils.get(item, "vali_flag"));
                // ?????????ID
                itemDTO.setCrteId(crteId);
                // ???????????????
                itemDTO.setCrteName(crteName);
                // ????????????
                itemDTO.setCrteTime(DateUtils.getNow());
                itemDTO.setCompoundLogo(MapUtils.get(item, "compound_logo")); // ????????????
                itemDTO.setVer(MapUtils.get(item, "ver")); // ?????????
                // ??????????????????????????????????????????????????????????????????  ???????????????????????????????????????????????????
                itemDTO.setVerName(MapUtils.get(item, "ver_name"));
                itemDTO.setDownLoadType(listType);
                insureItemDTOList.add(itemDTO);
            }
            if (!ListUtils.isEmpty(insureItemDTOList)) {
                insureItemDAO.insertInsureItem(insureItemDTOList);
            }
        }
    }

    /**
     * @Method insert_1301
     * @Desrciption ??????????????????????????? ??????????????????????????????
     * @Param dataResultMap , map
     * @Author fuhui
     * @Date 2021/4/9 15:18
     * @Return
     **/
    private void insert_1301(List<Map<String, Object>> dataResultMap, Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String crteId = MapUtils.get(map, "crteId");
        String listType = MapUtils.get(map, "downLoadType");
        String crteName = MapUtils.get(map, "crteName");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        List<InsureItemDTO> insureItemDTOList = new ArrayList<>();
        int size = MapUtils.get(map, "size");
        int num = MapUtils.get(map, "num");
        int recordCounts = MapUtils.get(map, "recordCounts");
        logger.info("??????????????????????????????:" + dataResultMap.size());
        InsureItemDTO itemDTO = null;
        for (Map<String, Object> item : dataResultMap) {
            itemDTO = new InsureItemDTO();
            // ??????
            itemDTO.setId(SnowflakeUtils.getId());
            // ????????????
            itemDTO.setHospCode(hospCode);
            itemDTO.setRecordCounts(recordCounts);
            itemDTO.setSize(size);
            itemDTO.setNum(num);
            // ??????????????????
            itemDTO.setInsureRegCode(insureRegCode);
            // ???????????????
            if (Constants.SF.S.equals(MapUtils.get(item, "patent_med_logo"))) {
                // ??????????????????  --????????????
                itemDTO.setItemMark("102");
                itemDTO.setItemType("102");
            } else {
                itemDTO.setItemMark("101");
                itemDTO.setItemType("101");
            }

            //????????????????????????
            itemDTO.setItemCode(MapUtils.get(item, "med_list_codg"));
            // ????????????????????????
            if (StringUtils.isEmpty(MapUtils.get(item, "drug_prodname")) || "???".equals(MapUtils.get(item, "drug_prodname")) ||
                    "null".equals(MapUtils.get(item, "drug_prodname"))) {
                itemDTO.setItemName(MapUtils.get(item, "drug_genname"));
            } else {
                itemDTO.setItemName(MapUtils.get(item, "drug_prodname"));
            }
            // ????????????????????????
            itemDTO.setItemSpec(MapUtils.get(item, "drug_spec"));
            // ????????????????????????
            itemDTO.setItemDosage(MapUtils.get(item, "nat_med_insure_catalog"));
            // ????????????????????????
            itemDTO.setItemPrice(null);
            // ????????????????????????
            itemDTO.setItemUnitCode(MapUtils.get(map, "min_paunt"));
            // ????????????
            itemDTO.setProd(MapUtils.get(map, "manufacturer_name"));
            // ????????????
            itemDTO.setDeductible(null);
            // ??????
            itemDTO.setCheckPrice(null);
            itemDTO.setDrugadmStrdcode(MapUtils.get(item, "drugadm_strdcode"));
            // ?????????????????????0.??????1.??????2.????????????
            itemDTO.setDirectory(MapUtils.get(item, "nat_med_insure_code"));
            // ????????????
            // ????????????
            String strStart = MapUtils.get(item, "begndate");
            itemDTO.setTakeDate(null);
            String strEnd = MapUtils.get(item, "begndate");
            //????????????
            itemDTO.setLoseDate(null);
            // ?????????
            itemDTO.setPym(MapUtils.get(item, "pinyin"));
            // ?????????
            itemDTO.setWbm(MapUtils.get(item, "wubi"));
            // ???????????????SF???
            itemDTO.setIsValid(MapUtils.get(item, "vali_flag"));
            // ?????????ID
            itemDTO.setCrteId(crteId);
            // ???????????????
            itemDTO.setCrteName(crteName);
            // ????????????
            itemDTO.setCrteTime(DateUtils.getNow());
            // ??????????????????????????????????????????????????????????????????  ???????????????????????????????????????????????????
            itemDTO.setVerName(MapUtils.get(item, "ver_name"));
            itemDTO.setVer(MapUtils.get(item, "ver")); // ?????????
            itemDTO.setLmtUsedFlag(MapUtils.get(item, "lmt_used_flag")); // ??????????????????
            itemDTO.setDownLoadType(listType);
            insureItemDTOList.add(itemDTO);
        }
        if (!ListUtils.isEmpty(insureItemDTOList)) {
            int toIndex = 1000;
            for (int i = 0; i < insureItemDTOList.size(); i += 1000) {
                if (i + 1000 > insureItemDTOList.size()) {
                    toIndex = insureItemDTOList.size() - i;
                }
                List newList = insureItemDTOList.subList(i, i + toIndex);
                insureItemDAO.insertInsureItem(newList);
            }
        }
    }

    /**
     * @Method UP_1319
     * @Desrciption ???????????????????????????????????????
     * @Param map
     * @Author fuhui
     * @Date 2021/4/16 8:41
     * @Return
     **/
    public Map<String, Object> insertInsureUnifiedRation(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        String listType = MapUtils.get(map, "listType");
        String crteName = MapUtils.get(map, "crteName");
        String crteId = MapUtils.get(map, "crteId");
        Date startDate = MapUtils.get(map, "startDate");
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);

        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("query_date", ""); // ???????????????
        paramMap.put("hilist_code", MapUtils.get(map, "hilistCode")); // ??????????????????
        paramMap.put("selfpay_prop_psn_type", ""); //????????????????????????????????????
        paramMap.put("selfpay_prop_type", ""); // ????????????????????????

        paramMap.put("insu_admdvs", ""); //????????????????????????
        paramMap.put("begndate", null); // ????????????
        paramMap.put("enddate", null); // ????????????
        paramMap.put("vali_flag", "1"); // ????????????
        paramMap.put("rid", ""); // ???????????????
        paramMap.put("tabname", null); // ??????
        paramMap.put("poolarea_no", ""); // ?????????
        paramMap.put("updt_time", MapUtils.get(map, "updtTime")); // ????????????
        paramMap.put("page_num", MapUtils.get(map, "pageNum")); // ????????????
        paramMap.put("page_size", MapUtils.get(map, "pageSize")); // ???????????????
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("data", paramMap);
        map.put("msgName","???????????????????????????????????????");
        map.put("isHospital","");
        map.put("visitId","");
        Map<String, Object> resultMap = insureUnifiedCommonUtil.commonInsureUnified(hospCode, insureConfigurationDTO.getRegCode(), Constant.UnifiedPay.REGISTER.UP_1319, inputMap,map);
        Map<String, Object> outMap = MapUtils.get(resultMap, "output");

       /* if(ObjectUtil.isEmpty(MapUtils.get(outMap, "data"))){
            map.put("msgName","??????"+MapUtils.get(map, "hilistCode")+" ?????????????????????????????????????????????");
            return map;
        }*/
        List<Map<String, Object>> mapList = MapUtils.get(outMap, "data");
        map.put("mapList", mapList);
        if(ObjectUtil.isEmpty(MapUtils.get(outMap, "data"))){
            map.put("msgName","??????????????????????????????");
            return map;
        }
        List<InsureUnifidRatioDO> list = insureDirectoryDownLoadDAO.queryAllInsureUnifiedRatio(map);
        Map<String, InsureUnifidRatioDO> collect = list.stream().collect(Collectors.toMap(InsureUnifidRatioDO::getRid, Function.identity(), (k1, k2) -> k1));
        if (!ListUtils.isEmpty(mapList)) {
            InsureUnifidRatioDO insureUnifidRatioDO = null;
            List<InsureUnifidRatioDO> insureDirectoryInfoDOList = new ArrayList<>();
            for (Map<String, Object> item : mapList) {
                if (collect.isEmpty() || !collect.containsKey(MapUtils.get(item, "rid"))) {
                    insureUnifidRatioDO = new InsureUnifidRatioDO();
                    insureUnifidRatioDO.setId(SnowflakeUtils.getId());
                    insureUnifidRatioDO.setHospCode(hospCode);
                    insureUnifidRatioDO.setHilistCode(MapUtils.get(item, "hilist_code"));
                    insureUnifidRatioDO.setSelfpayPropPsnType(MapUtils.get(item, "selfpay_prop_psn_type"));
                    insureUnifidRatioDO.setSelfpayPropType(MapUtils.get(item, "selfpay_prop_type"));
                    if (StringUtils.isEmpty(MapUtils.get(item, "begndate"))) {
                        insureUnifidRatioDO.setBegndate(null);
                    } else {
                        insureUnifidRatioDO.setBegndate(DateUtils.parse(MapUtils.get(item, "begndate"), DateUtils.Y_M_DH_M_S));
                    }
                    if (StringUtils.isEmpty(MapUtils.get(item, "enddate"))) {
                        insureUnifidRatioDO.setEnddate(null);
                    } else {
                        insureUnifidRatioDO.setEnddate(DateUtils.parse(MapUtils.get(item, "enddate"), DateUtils.Y_M_DH_M_S));
                    }
                    insureUnifidRatioDO.setInsuAdmdvs(MapUtils.get(item, "insu_admdvs"));
                    insureUnifidRatioDO.setSelfpayProp(MapUtils.get(item, "selfpay_prop"));
                    insureUnifidRatioDO.setValiFlag(MapUtils.get(item, "vali_flag"));
                    insureUnifidRatioDO.setRid(MapUtils.get(item, "rid"));

                    if(StringUtils.isNotEmpty(MapUtils.get(item, "updt_time"))){
                        insureUnifidRatioDO.setUpdtTime(DateUtils.parse(MapUtils.get(item, "updt_time"), DateUtils.Y_M_DH_M_S));
                    }else{
                        insureUnifidRatioDO.setUpdtTime(null);
                    }

                    insureUnifidRatioDO.setCrterId(MapUtils.get(item, "crter_id"));
                    insureUnifidRatioDO.setCrterName(MapUtils.get(item, "crter_name"));
                    if(StringUtils.isNotEmpty(MapUtils.get(item, "crte_time"))){
                        insureUnifidRatioDO.setCrteTime(DateUtils.parse(MapUtils.get(item, "crte_time"), DateUtils.Y_M_DH_M_S));
                    }else{
                        insureUnifidRatioDO.setCrteTime(null);
                    }
                    insureUnifidRatioDO.setCrteOptinsNo(MapUtils.get(item, "crte_optins_no"));
                    insureUnifidRatioDO.setOpterId(MapUtils.get(item, "opter_id"));
                    insureUnifidRatioDO.setOpterName(MapUtils.get(item, "opter_name"));
                    insureUnifidRatioDO.setOptinsNo(MapUtils.get(item, "optins_no"));
                    insureUnifidRatioDO.setPoolareaNo(MapUtils.get(item, "poolarea_no"));

                    if(StringUtils.isNotEmpty(MapUtils.get(item, "opt_time"))){
                        insureUnifidRatioDO.setOptTime(DateUtils.parse(MapUtils.get(item, "opt_time"), DateUtils.Y_M_DH_M_S));
                    }else{
                        insureUnifidRatioDO.setOptTime(null);
                    }
                    insureDirectoryInfoDOList.add(insureUnifidRatioDO);
                }
            }
            if (!ListUtils.isEmpty(insureDirectoryInfoDOList)) {
                int toIndex = 1000;
                for (int i = 0; i < insureDirectoryInfoDOList.size(); i += 1000) {
                    if (i + 1000 > insureDirectoryInfoDOList.size()) {
                        toIndex = insureDirectoryInfoDOList.size() - i;
                    }
                    List newList = insureDirectoryInfoDOList.subList(i, i + toIndex);
                    insureDirectoryDownLoadDAO.insertInsureUnifiedRation(newList);
                }
            }
        }else{
            throw new AppException("??????"+MapUtils.get(map, "hilistCode")+" ?????????????????????????????????????????????");
        }
        return map;
    }
    public Map<String, Object> insureUnifiedRationParam(Map<String, Object> map,Map<String, Object> paramMap) {

        paramMap.put("query_date", ""); // ???????????????
        paramMap.put("hilist_code", MapUtils.get(map, "hilistCode")); // ??????????????????
        paramMap.put("selfpay_prop_psn_type", ""); //????????????????????????????????????
        paramMap.put("selfpay_prop_type", ""); // ????????????????????????

        paramMap.put("insu_admdvs", ""); //????????????????????????
        paramMap.put("begndate", null); // ????????????
        paramMap.put("enddate", null); // ????????????
        paramMap.put("vali_flag", "1"); // ????????????
        paramMap.put("rid", ""); // ???????????????
        paramMap.put("tabname", null); // ??????
        paramMap.put("poolarea_no", ""); // ?????????
        paramMap.put("updt_time", MapUtils.get(map, "updtTime")); // ????????????

        return paramMap;
    }

    /**
     * @param map
     * @Method insertUnifiedDict
     * @Desrciption ????????????????????????????????????
     *    1.??????????????? ??????????????????????????????
     * @Param
     * @Author fuhui
     * @Date 2021/4/14 23:28
     * @Return
     */
    @Override
    public Map<String, Object> insertUnifiedDict(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        String crteId = MapUtils.get(map, "crteId");
        String crteName = MapUtils.get(map, "crteName");
        String type = MapUtils.get(map, "type");
        String remark = MapUtils.get(map, "remark");
        String code = type.toUpperCase();
        InsureDictDTO insureDictDTO = new InsureDictDTO();
        insureDictDTO.setHospCode(hospCode);
        insureDictDTO.setInsureRegCode(insureRegCode);
        insureDictDTO.setCode(code);
        List<InsureDictDTO> dictDTOList = insureDictDAO.queryDictByCode(insureDictDTO);
        if(!ListUtils.isEmpty(dictDTOList)){
            throw new AppException("??????????????????"+code+"???????????????");
        }

        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO.setIsValid(Constants.SF.S);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("type", type);
        dataMap.put("date", DateUtils.format(DateUtils.getNow(), DateUtils.Y_M_DH_M_S));
        dataMap.put("admdvs", "");
        dataMap.put("vali_flag", Constants.SF.S);
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("data", dataMap);
        map.put("msgName","????????????");
        map.put("isHospital","");
        map.put("visitId","");
        Map<String, Object> resultMap = insureUnifiedCommonUtil.commonInsureUnified(hospCode, insureConfigurationDTO.getRegCode(),  Constant.UnifiedPay.REGISTER.UP_1901, inputMap,map);
        Map<String, Object> outputMap = (Map<String, Object>) resultMap.get("output");
        List<Map<String, Object>> resultDataMap = MapUtils.get(outputMap, "list");
        if (ListUtils.isEmpty(resultDataMap)) {
            throw new AppException("???????????????????????????");
        }
        List<InsureDictDO> insureDictDTOList = new ArrayList<>();
        //??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        if (insureRegCode.startsWith(Constant.UnifiedPay.YBBMQZ.GD)) {
            for (Map<String, Object> item : resultDataMap) {
                if (type.equalsIgnoreCase(MapUtils.get(item, "dic_type_code"))) {
                    insureDictDTO = new InsureDictDTO();
                    insureDictDTO.setId(SnowflakeUtils.getId());
                    insureDictDTO.setHospCode(hospCode);
                    insureDictDTO.setInsureRegCode(insureRegCode);
                    insureDictDTO.setCode(MapUtils.get(item, "dic_type_code"));
                    insureDictDTO.setValue(MapUtils.get(item, "place_dic_val_code"));
                    insureDictDTO.setName(MapUtils.get(item, "place_dic_val_name"));
                    insureDictDTO.setRemark(remark);
                    insureDictDTO.setCrteId(crteId);
                    insureDictDTO.setCrteTime(DateUtils.getNow());
                    insureDictDTO.setCrteName(crteName);
                    insureDictDTOList.add(insureDictDTO);
                }
            }
        }else {
            for (Map<String, Object> item : resultDataMap) {
                insureDictDTO = new InsureDictDTO();
                insureDictDTO.setId(SnowflakeUtils.getId());
                insureDictDTO.setHospCode(hospCode);
                insureDictDTO.setInsureRegCode(insureRegCode);
                insureDictDTO.setCode(MapUtils.get(item, "type"));
                insureDictDTO.setValue(MapUtils.get(item, "value"));
                insureDictDTO.setName(MapUtils.get(item, "label"));
                insureDictDTO.setRemark(remark);
                insureDictDTO.setCrteId(crteId);
                insureDictDTO.setCrteTime(DateUtils.getNow());
                insureDictDTO.setCrteName(crteName);
                insureDictDTOList.add(insureDictDTO);
            }
        }
        if (!ListUtils.isEmpty(insureDictDTOList)) {
            map.put("insureDictDTOList", insureDictDTOList);
        }
        return map;
    }

    /**
     * @param map
     * @Method queryMedicnInfo
     * @Desrciption ????????????????????? ????????????
     * 1316????????????????????????????????????????????????
     * 1318?????????????????????????????????
     * 1319??????????????????????????????????????????
     * @Param map
     * @Author fuhui
     * @Date 2021/4/16 9:03
     * @Return
     */
    @Override
    public Map<String, Object> insertMedicnInfo(Map<String, Object> map) {
        String selectType = MapUtils.get(map, "selectType");
        Map<String, Object> resultMap = null;
        if (Constant.UnifiedPay.REGISTER.UP_1319.equals(selectType)) {
            resultMap = insertInsureUnifiedRation(map);
        } else if (Constant.UnifiedPay.REGISTER.UP_1316.equals(selectType)) {
            resultMap = insertInsureUnfiedMatch(map);
        } else if (Constant.UnifiedPay.REGISTER.UP_1317.equals(selectType)) {
            resultMap = insertInsureMedicinesMatch(map);
        } else if (Constant.UnifiedPay.REGISTER.UP_1312.equals(selectType)) {
            resultMap = insertInsureDirectory(map);
        } else if (Constant.UnifiedPay.REGISTER.UP_1304.equals(selectType)) {
            resultMap = insertNationDrug(map);
        } else {
            resultMap = insertInsureUnfiedLimitPrice(map);
        }

        return resultMap;
    }

    /**
     * @Method insertNationDrug
     * @Desrciption ????????????????????????
     * @Param
     * @Author fuhui
     * @Date 2021/4/27 9:56
     * @Return
     **/
    private Map<String, Object> insertNationDrug(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        Map httpParam = new HashMap();
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_1304);  //????????????
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //????????????????????????
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //????????????????????????
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //??????????????????
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("drug_genname", MapUtils.get(map, "drugGenname")); // ???????????????
        paramMap.put("drug_prodname", MapUtils.get(map, "drugProdname")); //???????????????
        paramMap.put("med_list_codg", MapUtils.get(map, "hilistCode")); // ??????????????????
        paramMap.put("vali_flag", Constants.SF.S); // ????????????
        paramMap.put("updt_time", MapUtils.get(map, "updtTime")); // ????????????
        paramMap.put("page_num", MapUtils.get(map, "pageNum")); // ????????????
        paramMap.put("page_size", MapUtils.get(map, "pageSize")); // ???????????????

        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("data", paramMap);
        httpParam.put("input", inputMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("??????????????????????????????:" + json);
        String url = insureConfigurationDTO.getUrl();
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(url, json);
        logger.info("??????????????????????????????:" + resultJson);
        if (StringUtils.isEmpty(resultJson)) {
            throw new AppException("????????????????????????????????????");
        }
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson);
        if ("999".equals(MapUtils.get(resultMap, "code"))) {
            throw new AppException((String) resultMap.get("msg"));
        }
        if (!"0".equals(MapUtils.get(resultMap, "infcode"))) {
            throw new AppException((String) resultMap.get("err_msg"));
        }

        Map<String, Object> outMap = MapUtils.get(resultMap, "output");
        List<Map<String, Object>> mapList = MapUtils.get(outMap, "data");
        map.put("mapList", mapList);
        if(ObjectUtil.isEmpty(MapUtils.get(outMap, "data"))){
            map.put("msgName","??????????????????????????????");
            return map;
        }
        List<InsureUnifiedNationDrugDO> unifiedNationDrudDOList = insureDirectoryDownLoadDAO.queyrAllInsureNationDrug(map);
        Map<String, InsureUnifiedNationDrugDO> collect = unifiedNationDrudDOList.stream().collect(Collectors.toMap(InsureUnifiedNationDrugDO::getRid, Function.identity(), (k1, k2) -> k1));
        if (!ListUtils.isEmpty(mapList)) {
            List<InsureUnifiedNationDrugDO> insertList = new ArrayList<>();
            InsureUnifiedNationDrugDO InsureUnifiedNationDrugDO = null;
            for (Map<String, Object> item : mapList) {
                if ((collect.isEmpty() || !collect.containsKey(MapUtils.get(item, "rid"))) && !StringUtils.isEmpty(MapUtils.get(item, "med_list_codg"))) {
                    InsureUnifiedNationDrugDO = new InsureUnifiedNationDrugDO();
                    InsureUnifiedNationDrugDO.setId(SnowflakeUtils.getId());
                    InsureUnifiedNationDrugDO.setHospCode(hospCode);
                    InsureUnifiedNationDrugDO.setMedListCodg(MapUtils.get(item, "med_list_codg"));
                    InsureUnifiedNationDrugDO.setGennameCodg(MapUtils.get(item, "genname_codg"));
                    InsureUnifiedNationDrugDO.setDrugGenname(MapUtils.get(item, "drug_genname"));
                    InsureUnifiedNationDrugDO.setDrugProdname(MapUtils.get(item, "drug_prodname"));
                    InsureUnifiedNationDrugDO.setValiFlag(MapUtils.get(item, "vali_flag"));
                    InsureUnifiedNationDrugDO.setEthdrugType(MapUtils.get(item, "ethdrug_type"));
                    InsureUnifiedNationDrugDO.setChemname(MapUtils.get(item, "chemname"));
                    InsureUnifiedNationDrugDO.setAlis(MapUtils.get(item, "alis"));
                    InsureUnifiedNationDrugDO.setEngName(MapUtils.get(item, "eng_name"));
                    InsureUnifiedNationDrugDO.setDosform(MapUtils.get(item, "dosform"));
                    InsureUnifiedNationDrugDO.setEachDos(MapUtils.get(item, "each_dos"));
                    InsureUnifiedNationDrugDO.setUsedFrqu(MapUtils.get(item, "used_frqu"));
                    InsureUnifiedNationDrugDO.setNatDrugNo(MapUtils.get(item, "nat_drug_no"));
                    InsureUnifiedNationDrugDO.setUsedMtd(MapUtils.get(item, "used_mtd"));
                    InsureUnifiedNationDrugDO.setIng(MapUtils.get(item, "ing"));
                    InsureUnifiedNationDrugDO.setChrt(MapUtils.get(item, "chrt"));
                    InsureUnifiedNationDrugDO.setDefs(MapUtils.get(item, "defs"));
                    InsureUnifiedNationDrugDO.setTabo(MapUtils.get(item, "tabo"));
                    InsureUnifiedNationDrugDO.setMnan(MapUtils.get(item, "mnan"));
                    InsureUnifiedNationDrugDO.setStog(MapUtils.get(item, "stog"));
                    InsureUnifiedNationDrugDO.setDrugSpec(MapUtils.get(item, "drug_spec"));
                    InsureUnifiedNationDrugDO.setPrcuntType(MapUtils.get(item, "prcunt_type"));
                    InsureUnifiedNationDrugDO.setOtcFlag(MapUtils.get(item, "otc_flag"));
                    InsureUnifiedNationDrugDO.setPacmatl(MapUtils.get(item, "pacmatl"));
                    InsureUnifiedNationDrugDO.setPacspec(MapUtils.get(item, "pacspec"));
                    InsureUnifiedNationDrugDO.setMinUseunt(MapUtils.get(item, "min_useunt"));
                    InsureUnifiedNationDrugDO.setMinSalunt(MapUtils.get(item, "min_salunt"));
                    InsureUnifiedNationDrugDO.setManl(MapUtils.get(item, "manl"));
                    InsureUnifiedNationDrugDO.setRute(MapUtils.get(item, "rute"));
                    InsureUnifiedNationDrugDO.setPhamType(MapUtils.get(item, "pham_type"));
                    InsureUnifiedNationDrugDO.setMemo(MapUtils.get(item, "memo"));
                    InsureUnifiedNationDrugDO.setPacCnt(MapUtils.get(item, "pac_cnt"));
                    InsureUnifiedNationDrugDO.setMinUnt(MapUtils.get(item, "min_unt"));
                    InsureUnifiedNationDrugDO.setMinPacCnt(MapUtils.get(item, "min_pac_cnt"));
                    InsureUnifiedNationDrugDO.setMinPacCnt(MapUtils.get(item, "min_pacunt"));
                    InsureUnifiedNationDrugDO.setMinPrepunt(MapUtils.get(item, "min_prepunt"));
                    InsureUnifiedNationDrugDO.setDrugExpy(MapUtils.get(item, "drug_expy"));
                    InsureUnifiedNationDrugDO.setEfccAtd(MapUtils.get(item, "efcc_atd"));
                    InsureUnifiedNationDrugDO.setMinPrcunt(MapUtils.get(item, "min_prcunt"));
                    InsureUnifiedNationDrugDO.setWubi(MapUtils.get(item, "wubi"));
                    InsureUnifiedNationDrugDO.setPinyin(MapUtils.get(item, "pinyin"));
                    InsureUnifiedNationDrugDO.setValiFlag(MapUtils.get(item, "vali_flag"));
                    InsureUnifiedNationDrugDO.setRid(MapUtils.get(item, "rid"));
                    InsureUnifiedNationDrugDO.setCrterId(MapUtils.get(item, "crter_id"));
                    InsureUnifiedNationDrugDO.setCrterName(MapUtils.get(item, "crter_name"));
                    InsureUnifiedNationDrugDO.setCrteOptinsNo(MapUtils.get(item, "crte_optins_no"));
                    InsureUnifiedNationDrugDO.setOpterId(MapUtils.get(item, "opter_id"));
                    InsureUnifiedNationDrugDO.setOpterName(MapUtils.get(item, "opter_name"));
                    InsureUnifiedNationDrugDO.setOptinsNo(MapUtils.get(item, "optins_no"));
                    InsureUnifiedNationDrugDO.setVer(MapUtils.get(item, "ver"));


                    if (StringUtils.isEmpty(MapUtils.get(item, "begndate"))) {
                        InsureUnifiedNationDrugDO.setBegndate(null);
                    } else {
                        InsureUnifiedNationDrugDO.setBegndate(DateUtils.parse(MapUtils.get(item, "begndate"), DateUtils.Y_M_DH_M_S));
                    }
                    if (StringUtils.isEmpty(MapUtils.get(item, "enddate"))) {
                        InsureUnifiedNationDrugDO.setEnddate(null);
                    } else {
                        InsureUnifiedNationDrugDO.setEnddate(DateUtils.parse(MapUtils.get(item, "enddate"), DateUtils.Y_M_DH_M_S));
                    }
                    if (StringUtils.isEmpty(MapUtils.get(item, "crte_time"))) {
                        InsureUnifiedNationDrugDO.setCrteTime(null);
                    } else {
                        InsureUnifiedNationDrugDO.setCrteTime(DateUtils.parse(MapUtils.get(item, "crte_time"), DateUtils.Y_M_DH_M_S));
                    }
                    InsureUnifiedNationDrugDO.setOptTime(null);
                    if (StringUtils.isEmpty(MapUtils.get(item, "updt_time"))) {
                        InsureUnifiedNationDrugDO.setUpdtTime(null);
                    } else {
                        InsureUnifiedNationDrugDO.setUpdtTime(DateUtils.parse(MapUtils.get(item, "updt_time"), DateUtils.Y_M_DH_M_S));
                    }
                    insertList.add(InsureUnifiedNationDrugDO);
                }
            }
            if (!ListUtils.isEmpty(insertList)) {
                int toIndex = 1000;
                for (int i = 0; i < insertList.size(); i += 1000) {
                    if (i + 1000 > insertList.size()) {
                        toIndex = insertList.size() - i;
                    }
                    List newList = insertList.subList(i, i + toIndex);
                    insureDirectoryDownLoadDAO.insertInsureNationDrug(newList);
                }
            }
        }
        return map;
    }

    private Map<String, Object> nationDrugParam(Map<String, Object> map,Map<String, Object> dataMap) {
        dataMap.put("drug_genname", MapUtils.get(map, "drugGenname")); // ???????????????
        dataMap.put("drug_prodname", MapUtils.get(map, "drugProdname")); //???????????????
        dataMap.put("med_list_codg", MapUtils.get(map, "hilistCode")); // ??????????????????
        dataMap.put("vali_flag", Constants.SF.S); // ????????????
        dataMap.put("updt_time", MapUtils.get(map, "updtTime")); // ????????????
        return dataMap;
    }

    /**
     * @Method insertInsureMedicinesMatch
     * @Desrciption ????????????????????????????????????
     * @Param
     * @Author fuhui
     * @Date 2021/4/22 23:18
     * @Return
     **/
    private Map<String, Object> insertInsureMedicinesMatch(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("query_date", DateUtils.format(DateUtils.getNow(), DateUtils.Y_M_DH_M_S)); // ???????????????
        paramMap.put("fixmedins_code", ""); // ????????????????????????
        paramMap.put("medins_list_codg", MapUtils.get(map, "medinsListCodg")); //??????????????????????????????
        paramMap.put("medins_list_name", ""); // ??????????????????????????????
        paramMap.put("insu_admdvs", ""); //????????????????????????
        paramMap.put("med_list_codg", MapUtils.get(map, "medListCodg")); // ??????????????????
        paramMap.put("list_type", ""); // ????????????
        paramMap.put("begndate", ""); // ????????????
        paramMap.put("vali_flag", Constants.SF.S); // ????????????
        paramMap.put("updt_time", MapUtils.get(map, "updtTime")); // ????????????
        paramMap.put("page_num", MapUtils.get(map, "pageNum")); // ????????????
        paramMap.put("page_size", MapUtils.get(map, "pageSize")); // ???????????????

        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("data", paramMap);
        map.put("msgName","????????????????????????????????????");
        map.put("isHospital","");
        map.put("visitId","");
        Map<String, Object> resultMap = insureUnifiedCommonUtil.commonInsureUnified(hospCode, insureConfigurationDTO.getRegCode(), Constant.UnifiedPay.REGISTER.UP_1317, inputMap,map);
        Map<String, Object> outMap = MapUtils.get(resultMap, "output");
        if(ObjectUtil.isEmpty(MapUtils.get(outMap, "data"))){
            map.put("msgName","??????"+MapUtils.get(map, "hilistCode")+"??????????????????????????????");
            return map;
        }
        List<Map<String, Object>> mapList = MapUtils.get(outMap, "data");
        List<InsureUnifiedMatchMedicinsDO> unifiedMatchDOList = insureDirectoryDownLoadDAO.queyrAllInsureMedicinesMatch(map);
        Map<String, InsureUnifiedMatchMedicinsDO> collect = unifiedMatchDOList.stream().collect(Collectors.toMap(InsureUnifiedMatchMedicinsDO::getRid, Function.identity(), (k1, k2) -> k1));
        if (!ListUtils.isEmpty(mapList)) {
            List<InsureUnifiedMatchMedicinsDO> insertList = new ArrayList<>();
            InsureUnifiedMatchMedicinsDO insureUnifiedMatchMedicinsDO = null;
            for (Map<String, Object> item : mapList) {
                if ((collect.isEmpty() || !collect.containsKey(MapUtils.get(item, "rid"))) && !StringUtils.isEmpty(MapUtils.get(item, "med_list_codg"))) {
                    insureUnifiedMatchMedicinsDO = new InsureUnifiedMatchMedicinsDO();
                    insureUnifiedMatchMedicinsDO.setId(SnowflakeUtils.getId());
                    insureUnifiedMatchMedicinsDO.setHospCode(hospCode);
                    insureUnifiedMatchMedicinsDO.setFixmedinsCode(MapUtils.get(item, "fixmedins_code"));
                    insureUnifiedMatchMedicinsDO.setMedinsListCodg(MapUtils.get(item, "medins_list_codg"));
                    insureUnifiedMatchMedicinsDO.setMedListCodg(MapUtils.get(item, "med_list_codg"));
                    insureUnifiedMatchMedicinsDO.setMedinsListName(MapUtils.get(item, "medins_list_name"));
                    insureUnifiedMatchMedicinsDO.setListType(MapUtils.get(item, "list_type"));
                    insureUnifiedMatchMedicinsDO.setInsuAdmdvs(MapUtils.get(item, "insu_admdvs"));
                    insureUnifiedMatchMedicinsDO.setMedListCodg(MapUtils.get(item, "med_list_codg"));
                    if (StringUtils.isEmpty(MapUtils.get(item, "begndate"))) {
                        insureUnifiedMatchMedicinsDO.setBegndate(null);
                    } else {
                        insureUnifiedMatchMedicinsDO.setBegndate(DateUtils.parse(MapUtils.get(item, "begndate"), DateUtils.Y_M_DH_M_S));
                    }
                    if (StringUtils.isEmpty(MapUtils.get(item, "enddate"))) {
                        insureUnifiedMatchMedicinsDO.setEnddate(null);
                    } else {
                        insureUnifiedMatchMedicinsDO.setEnddate(DateUtils.parse(MapUtils.get(item, "enddate"), DateUtils.Y_M_DH_M_S));
                    }
                    insureUnifiedMatchMedicinsDO.setMemo(MapUtils.get(item, "memo"));
                    insureUnifiedMatchMedicinsDO.setValiFlag(MapUtils.get(item, "vali_flag"));
                    insureUnifiedMatchMedicinsDO.setRid(MapUtils.get(item, "rid"));
                    insureUnifiedMatchMedicinsDO.setAprvno(MapUtils.get(item, "aprvno"));
                    insureUnifiedMatchMedicinsDO.setDosform(MapUtils.get(item, "dosform"));
                    insureUnifiedMatchMedicinsDO.setExctCont(MapUtils.get(item, "exctCont"));
                    insureUnifiedMatchMedicinsDO.setItemCont(MapUtils.get(item, "item_cont"));
                    insureUnifiedMatchMedicinsDO.setPrcunt(MapUtils.get(item, "prcunt"));
                    insureUnifiedMatchMedicinsDO.setSpec(MapUtils.get(item, "spec"));
                    insureUnifiedMatchMedicinsDO.setPacspec(MapUtils.get(item, "pacspec"));
                    insureUnifiedMatchMedicinsDO.setMemo(MapUtils.get(item, "memo"));
                    if (StringUtils.isEmpty(MapUtils.get(item, "crte_time"))) {
                        insureUnifiedMatchMedicinsDO.setCrteTime(null);
                    } else {
                        insureUnifiedMatchMedicinsDO.setCrteTime(DateUtils.parse(MapUtils.get(item, "crte_time"), DateUtils.Y_M_DH_M_S));
                    }
                    insureUnifiedMatchMedicinsDO.setOptTime(null);
                    if (StringUtils.isEmpty(MapUtils.get(item, "updt_time"))) {
                        insureUnifiedMatchMedicinsDO.setUpdtTime(null);
                    } else {
                        insureUnifiedMatchMedicinsDO.setUpdtTime(DateUtils.parse(MapUtils.get(item, "updt_time"), DateUtils.Y_M_DH_M_S));
                    }
                    insureUnifiedMatchMedicinsDO.setCrterId(MapUtils.get(item, "crter_id"));
                    insureUnifiedMatchMedicinsDO.setCrterName(MapUtils.get(item, "crter_name"));
                    insureUnifiedMatchMedicinsDO.setCrteOptinsNo(MapUtils.get(item, "crte_optins_no"));
                    insureUnifiedMatchMedicinsDO.setOpterId(MapUtils.get(item, "opter_id"));
                    insureUnifiedMatchMedicinsDO.setOpterName(MapUtils.get(item, "opter_name"));
                    insureUnifiedMatchMedicinsDO.setOptinsNo(MapUtils.get(item, "optins_no"));
                    insureUnifiedMatchMedicinsDO.setPoolareaNo(MapUtils.get(item, "poolarea_no"));
                    insertList.add(insureUnifiedMatchMedicinsDO);
                }
            }
            if (!ListUtils.isEmpty(insertList)) {
                int toIndex = 1000;
                for (int i = 0; i < insertList.size(); i += 1000) {
                    if (i + 1000 > insertList.size()) {
                        toIndex = insertList.size() - i;
                    }
                    List newList = insertList.subList(i, i + toIndex);
                    insureDirectoryDownLoadDAO.insertInsureMedicinesMatch(newList);
                }
            }
        }else{
            throw new AppException("??????"+MapUtils.get(map, "hilistCode")+" ??????????????????????????????????????????");
        }
        return map;
    }

    /**
     * @param insureUnifiedLimitPriceDO
     * @Method queryPageInsureUnifiedLimitPrice
     * @Desrciption ???????????????????????????????????????
     * @Param
     * @Author fuhui
     * @Date 2021/4/21 19:16
     * @Return
     */
    @Override
    public PageDTO queryPageInsureUnifiedLimitPrice(InsureUnifiedLimitPriceDO insureUnifiedLimitPriceDO) {
        PageHelper.startPage(insureUnifiedLimitPriceDO.getPageNo(), insureUnifiedLimitPriceDO.getPageSize());
        List<InsureUnifiedLimitPriceDO> insureUnifiedLimitPriceDOList = insureDirectoryDownLoadDAO.queryPageInsureUnifiedLimitPrice(insureUnifiedLimitPriceDO);
        return PageDTO.of(insureUnifiedLimitPriceDOList);
    }

    /**
     * @param insureUnifiedMatchDO
     * @Method queryPageInsureUnifiedMatch
     * @Desrciption ?????????????????????????????????????????????????????????
     * @Param
     * @Author fuhui
     * @Date 2021/4/21 19:16
     * @Return
     */
    @Override
    public PageDTO queryPageInsureUnifiedMatch(InsureUnifiedMatchDO insureUnifiedMatchDO) {
        PageHelper.startPage(insureUnifiedMatchDO.getPageNo(), insureUnifiedMatchDO.getPageSize());
        List<InsureUnifiedMatchDO> insureUnifiedMatchDOList = insureDirectoryDownLoadDAO.queryPageInsureUnifiedMatch(insureUnifiedMatchDO);
        return PageDTO.of(insureUnifiedMatchDOList);
    }

    /**
     * @Method queryPageInsureUnifiedMatch
     * @Desrciption ??????????????????????????????
     * @Param
     * @Author fuhui
     * @Date 2021/4/21 19:16
     * @Return
     **/
    @Override
    public PageDTO queryPageInsureUnifiedDirectory(InsureDirectoryInfoDO insureDirectoryInfoDO) {
        PageHelper.startPage(insureDirectoryInfoDO.getPageNo(), insureDirectoryInfoDO.getPageSize());
        List<InsureUnifidRatioDO> insureUnifidRatioDOList = insureDirectoryDownLoadDAO.queryPageInsureUnifiedDirectory(insureDirectoryInfoDO);
        return PageDTO.of(insureUnifidRatioDOList);
    }

    /**
     * @param insureUnifidRatioDO
     * @Method queryPageInsureUnifiedLimitPrice
     * @Desrciption ???????????????????????????????????????
     * @Param
     * @Author fuhui
     * @Date 2021/4/21 19:16
     * @Return
     */
    @Override
    public PageDTO queryPageInsureUnifiedRatio(InsureUnifidRatioDO insureUnifidRatioDO) {
        PageHelper.startPage(insureUnifidRatioDO.getPageNo(), insureUnifidRatioDO.getPageSize());
        List<InsureDirectoryInfoDO> directoryInfoDOList = insureDirectoryDownLoadDAO.queryPageInsureUnifiedRatio(insureUnifidRatioDO);
        return PageDTO.of(directoryInfoDOList);
    }

    /**
     * @param insureUnifiedMatchMedicinsDO
     * @Method queryPageInsureUnifiedMatch
     * @Desrciption ???1317???????????????????????????????????????
     * @Param
     * @Author fuhui
     * @Date 2021/4/21 19:16
     * @Return
     */
    @Override
    public PageDTO queryPageInsureMedicinesMatch(InsureUnifiedMatchMedicinsDO insureUnifiedMatchMedicinsDO) {
        PageHelper.startPage(insureUnifiedMatchMedicinsDO.getPageNo(), insureUnifiedMatchMedicinsDO.getPageSize());
        List<InsureUnifiedMatchMedicinsDO> insureUnifiedMatchMedicinsDOList = insureDirectoryDownLoadDAO.queyrPageInsureMedicinesMatch(insureUnifiedMatchMedicinsDO);
        return PageDTO.of(insureUnifiedMatchMedicinsDOList);
    }


    /**
     * ????????????
     *
     * @param insureItemMatchDTO
     * @return
     */
    @Override
    public Integer insertInsureMatch(InsureItemMatchDTO insureItemMatchDTO) {
        String insureRegCode = insureItemMatchDTO.getRegCode();
        String hospCode = insureItemMatchDTO.getHospCode();
        InsureItemMatchDTO insureItemMatch = new InsureItemMatchDTO();
        insureItemMatch.setHospCode(hospCode);
        insureItemMatch.setInsureRegCode(insureRegCode);
        List<InsureItemMatchDTO> resultList = insureItemMatchDAO.queryPageOrAll(insureItemMatch);
        if (ListUtils.isEmpty(resultList)) {
            throw new AppException("??????????????????????????????????????????");
        }

        InsureItemDTO insureItemDTO = new InsureItemDTO();
        insureItemDTO.setHospCode(hospCode);
        insureItemDTO.setInsureRegCode(insureRegCode);
        List<InsureItemDTO> insureItemDTOList = insureItemDAO.queryInsureItemAll(insureItemDTO);
        if (ListUtils.isEmpty(insureItemDTOList)) {
            throw new AppException("??????????????????????????????");
        }
        //?????????down_load_type????????????????????????????????????????????????????????????
        insureItemDTOList = insureItemDTOList.stream().filter(o -> ObjectUtil.isNotEmpty(o.getDownLoadType())).collect(Collectors.toList());
        List<InsureItemMatchDTO> insertList = new ArrayList<>();
            for (InsureItemMatchDTO hospItem : resultList) {
            for (InsureItemDTO insureItem : insureItemDTOList) {
                if (hospItem.getHospItemCode().equals(insureItem.getHospItemCode())
                        && hospItem.getHospItemName().equals(insureItem.getHospItemName())) {
                    hospItem.setInsureItemCode(insureItem.getItemCode());
                    hospItem.setInsureItemName(insureItem.getItemName());
                    hospItem.setMatchType(insureItem.getItemType());
                    hospItem.setTakeDate(insureItem.getTakeDate());
                    hospItem.setLoseDate(insureItem.getLoseDate());
                    hospItem.setAuditCode("1");
                    hospItem.setIsMatch("1");
                    hospItem.setIsTrans("1");
                    hospItem.setIsValid("1");
                    insertList.add(hospItem);
                    break;
                }
            }
        }

        Integer size = 0;
        if (!ListUtils.isEmpty(insertList)) {
            size = insertList.size();
            insureItemMatchDAO.updateInsureItemMatchS(insertList);
        }
        return size;
    }

    /**
     * @param insureItemDTO
     * @Method insertUnifiedItem
     * @Desrciption ??????????????????????????? ????????????????????????
     * @Param insureItemDTO:???????????????
     * @Author fuhui
     * @Date 2021/6/15 11:29
     * @Return
     */
    @Override
    public Boolean insertUnifiedItem(InsureItemDTO insureItemDTO) {
        Map<String, Object> map = new HashMap<>();
        String hospCode = insureItemDTO.getHospCode();
        map.put("hospCode", hospCode);
        map.put("code", "HOSP_INSURE_CODE");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(map).getData();
        String orgCode = "";
        if (sysParameterDTO == null) {
            throw new AppException("????????????????????????:" + "HOSP_INSURE_CODE" + "?????????????????????????????????");
        } else {
            orgCode = sysParameterDTO.getValue(); // ????????????????????????
        }
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setOrgCode(orgCode);
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureItemDTO.getInsureRegCode());
        insureConfigurationDTO = getInsureConfiguration(insureConfigurationDTO);
        insureItemDTO.setId(SnowflakeUtils.getId());
        insureItemDTO.setInsureRegCode(insureConfigurationDTO.getRegCode());
        insureItemDTO.setItemType(insureItemDTO.getItemMark());
        return insureItemDAO.insertUnifiedItem(insureItemDTO);
    }

    /**
     * @Author ????????????-?????????
     * @Date 2022-05-05 11:40
     * @Description ??????????????????????????????????????????
     * @param insureUnifiedNationDrugDO
     * @return cn.hsa.base.PageDTO
     */
    @Override
    public PageDTO queryPageInsureUnifiedNationDrug(InsureUnifiedNationDrugDO insureUnifiedNationDrugDO) {
        PageHelper.startPage(insureUnifiedNationDrugDO.getPageNo(), insureUnifiedNationDrugDO.getPageSize());
        List<InsureUnifiedNationDrugDO> insureUnifiedNationDrugDOList = insureDirectoryDownLoadDAO.queryPageInsureUnifiedNationDrug(insureUnifiedNationDrugDO);
        return PageDTO.of(insureUnifiedNationDrugDOList);
    }
    /**
     * @param map
     * @Method getMedisnInfo
     * @Desrciption ??????????????????;????????????????????????
     * @Param map
     * @Author fuhui
     * @Date 2021/4/13 20:28
     * @Return
     */
    @Override
    public PageDTO getMedisnInfoByMedisnInName(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("hospCode",hospCode);
        paramMap.put("code","HOSP_INSURE_CODE");
        String orgCode = "";
        SysParameterDTO data = sysParameterService_consumer.getParameterByCode(paramMap).getData();
        if(data !=null){
            orgCode = data.getValue();
        }else{
            throw  new AppException("????????????????????????????????????");
        }
        String insureServiceType = MapUtils.get(map, "insureServiceType");
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(orgCode);
        insureConfigurationDTO.setRegCode(MapUtils.get(map,"regCode"));
        insureConfigurationDTO.setIsValid(Constants.SF.S);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);

        Map httpParam = new HashMap();
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_1201);  //????????????
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //????????????????????????
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //????????????????????????
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //??????????????????
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("fixmedins_type", insureServiceType);
        dataMap.put("fixmedins_name", MapUtils.get(map, "medinsName"));
        dataMap.put("fixmedins_code", MapUtils.get(map, "medinsCode"));
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("medinsinfo", dataMap);
        httpParam.put("input", inputMap);

        String json = JSONObject.toJSONString(httpParam);
        logger.info("????????????????????????????????????:" + json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
        if (StringUtils.isEmpty(resultJson)) {
            throw new AppException("????????????????????????????????????");
        }
        logger.info("??????????????????????????????????????????:" + resultJson);
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson);
        if ("999".equals(MapUtils.get(resultMap, "code"))) {
            throw new AppException((String) resultMap.get("msg"));
        }
        if (!MapUtils.get(resultMap, "infcode").equals("0")) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        Map<String, Object> outputMap = (Map<String, Object>) resultMap.get("output");
        List<Map<String, Object>> resultDataMap = MapUtils.get(outputMap, "medinsinfo");
        if(ListUtils.isEmpty(resultDataMap)){
            return PageDTO.of(new ArrayList<>());
        }
        for(Map map1:resultDataMap){
            String fixmedinsCode = MapUtils.get(map1,"fixmedins_code");
            String mdtrtAdvmsCode = fixmedinsCode.substring(1,7);
            map1.put("mdtrtareaAdmdvs",mdtrtAdvmsCode);
        }
        String pageNoStr = MapUtils.get(map,"pageNo");
        String pageSizeStr = MapUtils.get(map,"pageSize");
        int pageNo = 0;
        int pageSize=0;
        int firstNum = 0;
        int lastNum = 0;
        if(ObjectUtil.isEmpty(pageSizeStr)){
            pageSize = 10;
        }else{
            pageSize = Integer.valueOf(pageSizeStr).intValue();
        }
        if(ObjectUtil.isEmpty(pageNoStr)){
            pageNo = 1;
        }else{
            pageNo  = Integer.valueOf(pageNoStr).intValue();
        }
        if(pageNo==1){
            lastNum = pageSize;
        }
        if (pageNo>1) {
            firstNum = (pageNo-1) *pageSize+1;
            lastNum = pageNo*pageSize;
        }
        if(lastNum>resultDataMap.size()){
            lastNum = resultDataMap.size();
        }

        List<Map<String, Object>> returnList = resultDataMap.subList(firstNum,lastNum);
        PageDTO pageDTO = new PageDTO();
        PageInfo pageInfo = new PageInfo(resultDataMap);
        pageDTO = PageDTO.of(returnList);
        pageDTO.setTotal(pageInfo.getTotal());
        return pageDTO;
    }

    /**
     * @Method UP_1312
     * @Desrciption ????????????????????????
     * @Param
     * @Author fuhui
     * @Date 2021/4/21 17:14
     * @Return
     **/
    private Map<String, Object> insertInsureDirectory(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);

        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("query_date", DateUtils.format(DateUtils.getNow(), DateUtils.Y_M_DH_M_S)); // ???????????????
        paramMap.put("hilist_code", MapUtils.get(map, "hilistCode")); // ????????????
        paramMap.put("insu_admdvs", ""); //????????????????????????
        paramMap.put("begndate", ""); // ????????????
        paramMap.put("wubi", ""); //???????????????
        paramMap.put("pinyin", ""); // ???????????????
        paramMap.put("med_chrgitm_type", ""); //????????????????????????
        paramMap.put("chrgitm_lv", ""); // ??????????????????
        paramMap.put("lmt_used_flag", ""); //??????????????????
        paramMap.put("list_type", MapUtils.get(map, "listType")); // ????????????
        paramMap.put("med_use_flag", ""); //??????????????????
        paramMap.put("matn_used_flag", ""); // ??????????????????
        paramMap.put("hilist_use_type", ""); //????????????????????????
        paramMap.put("lmt_cpnd_type", ""); // ?????????????????????
        paramMap.put("vali_flag", Constants.SF.S); // ????????????
        paramMap.put("rid", ""); // ???????????????
        paramMap.put("tabname", ""); // ??????
        paramMap.put("poolarea_no", ""); // ?????????
        paramMap.put("updt_time", MapUtils.get(map, "updtTime")); // ????????????
        paramMap.put("page_num", MapUtils.get(map, "pageNum")); // ????????????
        paramMap.put("page_size", MapUtils.get(map, "pageSize")); // ???????????????
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("data", paramMap);
        map.put("msgName","????????????????????????");
        map.put("isHospital","");
        map.put("visitId","");
        Map<String, Object> resultMap = insureUnifiedCommonUtil.commonInsureUnified(hospCode, insureConfigurationDTO.getRegCode(),  Constant.UnifiedPay.REGISTER.UP_1312, inputMap,map);
        Map<String, Object> outMap = MapUtils.get(resultMap, "output");
        if(ObjectUtil.isEmpty(MapUtils.get(outMap, "data"))){
            map.put("msgName","??????"+MapUtils.get(map, "hilistCode")+"??????????????????????????????");
            return map;
        }
        List<Map<String, Object>> mapList = MapUtils.get(outMap, "data");
        List<InsureDirectoryInfoDO> list = insureDirectoryDownLoadDAO.queryAllInsureDirectory(map);
        Map<String, InsureDirectoryInfoDO> collect = list.stream().collect(Collectors.toMap(InsureDirectoryInfoDO::getRid, Function.identity(), (k1, k2) -> k1));
        if (!ListUtils.isEmpty(mapList)) {
            InsureDirectoryInfoDO insureDirectoryInfoDO = null;
            List<InsureDirectoryInfoDO> insureDirectoryInfoDOList = new ArrayList<>();
            for (Map<String, Object> item : mapList) {
                if ((collect.isEmpty() || !collect.containsKey(MapUtils.get(item, "rid"))) && !StringUtils.isEmpty(MapUtils.get(item, "hilist_code"))) {
                    insureDirectoryInfoDO = new InsureDirectoryInfoDO();
                    insureDirectoryInfoDO.setId(SnowflakeUtils.getId());
                    insureDirectoryInfoDO.setHospCode(hospCode);
                    insureDirectoryInfoDO.setHilistCode(MapUtils.get(item, "hilist_code"));
                    insureDirectoryInfoDO.setHilistName(MapUtils.get(item, "hilist_name"));
                    insureDirectoryInfoDO.setInsuAdmdvs(MapUtils.get(item, "insu_admdvs"));
                    if (StringUtils.isEmpty(MapUtils.get(item, "begndate"))) {
                        insureDirectoryInfoDO.setBegndate(null);
                    } else {
                        insureDirectoryInfoDO.setBegndate(DateUtils.parse(MapUtils.get(item, "begndate"), DateUtils.Y_M_DH_M_S));
                    }
                    if (StringUtils.isEmpty(MapUtils.get(item, "enddate"))) {
                        insureDirectoryInfoDO.setEnddate(null);
                    } else {
                        insureDirectoryInfoDO.setEnddate(DateUtils.parse(MapUtils.get(item, "enddate"), DateUtils.Y_M_DH_M_S));
                    }
                    insureDirectoryInfoDO.setMedChrgitmType(MapUtils.get(item, "med_chrgitm_type"));
                    insureDirectoryInfoDO.setChrgitmLv(MapUtils.get(item, "chrgitm_lv"));
                    insureDirectoryInfoDO.setLmtUsedFlag(MapUtils.get(item, "lmt_used_flag"));
                    insureDirectoryInfoDO.setListType(MapUtils.get(item, "list_type"));
                    insureDirectoryInfoDO.setMedUseFlag(MapUtils.get(item, "med_use_flag"));
                    insureDirectoryInfoDO.setMatnUsedFlag(MapUtils.get(item, "matn_used_flag"));
                    insureDirectoryInfoDO.setHilistUseType(MapUtils.get(item, "hilist_use_type"));
                    insureDirectoryInfoDO.setLmtCpndType(MapUtils.get(item, "lmt_cpnd_type"));
                    insureDirectoryInfoDO.setWubi(MapUtils.get(item, "wubi"));
                    insureDirectoryInfoDO.setPinyin(MapUtils.get(item, "pinyin"));
                    insureDirectoryInfoDO.setMemo(MapUtils.get(item, "memo"));
                    insureDirectoryInfoDO.setValiFlag(MapUtils.get(item, "vali_flag"));
                    insureDirectoryInfoDO.setRid(MapUtils.get(item, "rid"));

                    if (StringUtils.isEmpty(MapUtils.get(item, "updt_time"))) {
                        insureDirectoryInfoDO.setUpdtTime(null);
                    } else {
                        insureDirectoryInfoDO.setUpdtTime(DateUtils.parse(MapUtils.get(item, "updt_time"), DateUtils.Y_M_DH_M_S));
                    }
                    insureDirectoryInfoDO.setCrterId(MapUtils.get(item, "crter_id"));
                    insureDirectoryInfoDO.setCrterName(MapUtils.get(item, "crter_name"));
                    if (StringUtils.isEmpty(MapUtils.get(item, "crte_time"))) {
                        insureDirectoryInfoDO.setCrteTime(null);
                    } else {
                        insureDirectoryInfoDO.setCrteTime(DateUtils.parse(MapUtils.get(item, "crte_time"), DateUtils.Y_M_DH_M_S));
                    }
                    if (StringUtils.isEmpty(MapUtils.get(item, "opt_time"))) {
                        insureDirectoryInfoDO.setOptTime(null);
                    } else {
                        insureDirectoryInfoDO.setOptTime(DateUtils.parse(MapUtils.get(item, "opt_time"), DateUtils.Y_M_DH_M_S));
                    }
                    insureDirectoryInfoDO.setCrteOptinsNo(MapUtils.get(item, "crte_optins_no"));
                    insureDirectoryInfoDO.setOpterId(MapUtils.get(item, "opter_id"));
                    insureDirectoryInfoDO.setOpterName(MapUtils.get(item, "opter_name"));
                    insureDirectoryInfoDO.setOptinsNo(MapUtils.get(item, "optins_no"));
                    insureDirectoryInfoDO.setPoolareaNo(MapUtils.get(item, "poolarea_no"));
                    insureDirectoryInfoDOList.add(insureDirectoryInfoDO);
                }
            }
            if (!ListUtils.isEmpty(insureDirectoryInfoDOList)) {
                int toIndex = 1000;
                for (int i = 0; i < insureDirectoryInfoDOList.size(); i += 1000) {
                    if (i + 1000 > insureDirectoryInfoDOList.size()) {
                        toIndex = insureDirectoryInfoDOList.size() - i;
                    }
                    List newList = insureDirectoryInfoDOList.subList(i, i + toIndex);
                    insureDirectoryDownLoadDAO.insertInsureDirectory(newList);
                }
            }
        }else {
            throw new AppException("??????"+MapUtils.get(map, "hilistCode")+"??????????????????????????????");
        }
        return map;
    }
    private Map<String, Object> insureDirectoryParam(Map<String, Object> map,Map<String, Object> paramMap) {
        paramMap.put("query_date", DateUtils.format(DateUtils.getNow(), DateUtils.Y_M_DH_M_S)); // ???????????????
        paramMap.put("hilist_code", MapUtils.get(map, "hilistCode")); // ????????????
        paramMap.put("insu_admdvs", ""); //????????????????????????
        paramMap.put("begndate", ""); // ????????????
        paramMap.put("wubi", ""); //???????????????
        paramMap.put("pinyin", ""); // ???????????????
        paramMap.put("med_chrgitm_type", ""); //????????????????????????
        paramMap.put("chrgitm_lv", ""); // ??????????????????
        paramMap.put("lmt_used_flag", ""); //??????????????????
        paramMap.put("list_type", MapUtils.get(map, "listType")); // ????????????
        paramMap.put("med_use_flag", ""); //??????????????????
        paramMap.put("matn_used_flag", ""); // ??????????????????
        paramMap.put("hilist_use_type", ""); //????????????????????????
        paramMap.put("lmt_cpnd_type", ""); // ?????????????????????
        paramMap.put("vali_flag", Constants.SF.S); // ????????????
        paramMap.put("rid", ""); // ???????????????
        paramMap.put("tabname", ""); // ??????
        paramMap.put("poolarea_no", ""); // ?????????
        paramMap.put("updt_time", MapUtils.get(map, "updtTime")); // ????????????
        return paramMap;
    }
    /**
     * @Method UP_1316
     * @Desrciption ?????????????????????????????????????????????
     * @Param map
     * @Author fuhui
     * @Date 2021/4/16 9:22
     * @Return
     **/
    private Map<String, Object> insertInsureUnfiedMatch(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("query_date", DateUtils.format(DateUtils.getNow(), DateUtils.Y_M_DH_M_S)); // ???????????????
        paramMap.put("hilist_code", MapUtils.get(map, "hilistCode")); // ??????????????????
        paramMap.put("medins_list_codg", MapUtils.get(map, "medinsListCodg")); //??????????????????????????????
        paramMap.put("list_type", MapUtils.get(map, "listType")); // ????????????????????????
        paramMap.put("insu_admdvs", ""); //????????????????????????
        paramMap.put("begndate", ""); // ????????????
        paramMap.put("enddate", ""); // ????????????
        paramMap.put("vali_flag", Constants.SF.S); // ????????????
        paramMap.put("rid", ""); // ???????????????
        paramMap.put("tabname", ""); // ??????
        paramMap.put("poolarea_no", ""); // ?????????
        paramMap.put("updt_time", MapUtils.get(map, "updtTime")); // ????????????
        paramMap.put("page_num", MapUtils.get(map, "pageNum")); // ????????????
        paramMap.put("page_size", MapUtils.get(map, "pageSize")); // ???????????????

        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("data", paramMap);
        map.put("msgName","?????????????????????????????????????????????");
        map.put("isHospital","");
        map.put("visitId","");
        Map<String, Object> resultMap = insureUnifiedCommonUtil.commonInsureUnified(hospCode, insureConfigurationDTO.getRegCode(),  Constant.UnifiedPay.REGISTER.UP_1316, inputMap,map);
        Map<String, Object> outMap = MapUtils.get(resultMap, "output");
        if(ObjectUtil.isEmpty(MapUtils.get(outMap, "data"))){
            map.put("msgName","??????"+MapUtils.get(map, "hilistCode")+"??????????????????????????????");
            return map;
        }
        List<Map<String, Object>> mapList = MapUtils.get(outMap, "data");
        List<InsureUnifiedMatchDO> unifiedMatchDOList = insureDirectoryDownLoadDAO.queyrAllInsureUnifiedMatch(map);
        Map<String, InsureUnifiedMatchDO> collect = unifiedMatchDOList.stream().collect(Collectors.toMap(InsureUnifiedMatchDO::getRid, Function.identity(), (k1, k2) -> k1));
        if (!ListUtils.isEmpty(mapList)) {
            List<InsureUnifiedMatchDO> insertList = new ArrayList<>();
            InsureUnifiedMatchDO insureUnifiedMatchDO = null;
            for (Map<String, Object> item : mapList) {
                if ((collect.isEmpty() || !collect.containsKey(MapUtils.get(item, "rid"))) && !StringUtils.isEmpty(MapUtils.get(item, "med_list_codg"))) {
                    insureUnifiedMatchDO = new InsureUnifiedMatchDO();
                    insureUnifiedMatchDO.setId(SnowflakeUtils.getId());
                    insureUnifiedMatchDO.setHospCode(hospCode);
                    insureUnifiedMatchDO.setMedListCodg(MapUtils.get(item, "med_list_codg"));
                    insureUnifiedMatchDO.setHilistCode(MapUtils.get(item, "hilist_code"));
                    insureUnifiedMatchDO.setListType(MapUtils.get(item, "list_type"));
                    insureUnifiedMatchDO.setInsuAdmdvs(MapUtils.get(item, "insu_admdvs"));
                    if (StringUtils.isEmpty(MapUtils.get(item, "begndate"))) {
                        insureUnifiedMatchDO.setBegndate(null);
                    } else {
                        insureUnifiedMatchDO.setBegndate(DateUtils.parse(MapUtils.get(item, "begndate"), DateUtils.Y_M_DH_M_S));
                    }
                    if (StringUtils.isEmpty(MapUtils.get(item, "enddate"))) {
                        insureUnifiedMatchDO.setEnddate(null);
                    } else {
                        insureUnifiedMatchDO.setEnddate(DateUtils.parse(MapUtils.get(item, "enddate"), DateUtils.Y_M_DH_M_S));
                    }
                    insureUnifiedMatchDO.setMemo(MapUtils.get(item, "memo"));
                    insureUnifiedMatchDO.setValiFlag(MapUtils.get(item, "vali_flag"));
                    insureUnifiedMatchDO.setRid(MapUtils.get(item, "rid"));

                    if (StringUtils.isEmpty(MapUtils.get(item, "crte_time"))) {
                        insureUnifiedMatchDO.setCrteTime(null);
                    } else {
                        insureUnifiedMatchDO.setCrteTime(DateUtils.parse(MapUtils.get(item, "crte_time"), DateUtils.Y_M_DH_M_S));
                    }
                    insureUnifiedMatchDO.setOptTime(null);
                    if (StringUtils.isEmpty(MapUtils.get(item, "updt_time"))) {
                        insureUnifiedMatchDO.setUpdtTime(null);
                    } else {
                        insureUnifiedMatchDO.setUpdtTime(DateUtils.parse(MapUtils.get(item, "updt_time"), DateUtils.Y_M_DH_M_S));
                    }
                    insureUnifiedMatchDO.setCrterId(MapUtils.get(item, "crter_id"));
                    insureUnifiedMatchDO.setCrterName(MapUtils.get(item, "crter_name"));
                    insureUnifiedMatchDO.setCrteOptinsNo(MapUtils.get(item, "crte_optins_no"));
                    insureUnifiedMatchDO.setOpterId(MapUtils.get(item, "opter_id"));
                    insureUnifiedMatchDO.setOpterName(MapUtils.get(item, "opter_name"));
                    insureUnifiedMatchDO.setOptinsNo(MapUtils.get(item, "optins_no"));
                    insureUnifiedMatchDO.setPoolareaNo(MapUtils.get(item, "poolarea_no"));
                    insertList.add(insureUnifiedMatchDO);
                }
            }
            if (!ListUtils.isEmpty(insertList)) {
                int toIndex = 1000;
                for (int i = 0; i < insertList.size(); i += 1000) {
                    if (i + 1000 > insertList.size()) {
                        toIndex = insertList.size() - i;
                    }
                    List newList = insertList.subList(i, i + toIndex);
                    insureDirectoryDownLoadDAO.insertInsureUnfiedMatch(newList);
                }
            }
        }else{
            throw new AppException("??????"+MapUtils.get(map, "hilistCode")+"???????????????????????????????????????????????????");
        }
        return map;
    }

    /**
     * @Method UP_1318
     * @Desrciption ??????????????????????????????
     * @Param map
     * @Author fuhui
     * @Date 2021/4/16 9:22
     * @Return
     **/
    private Map<String, Object> insertInsureUnfiedLimitPrice(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("query_date", DateUtils.format(DateUtils.getNow(), DateUtils.Y_M_DH_M_S)); // ???????????????
        paramMap.put("hilist_code", MapUtils.get(map, "hilistCode")); // ??????????????????
        paramMap.put("hilist_lmtpric_type", ""); // ????????????????????????
        paramMap.put("overlmt_dspo_way", ""); // ??????????????????????????????
        paramMap.put("insu_admdvs", ""); //????????????????????????
        paramMap.put("begndate", ""); // ????????????
        paramMap.put("enddate", ""); // ????????????
        paramMap.put("vali_flag", Constants.SF.S); // ????????????
        paramMap.put("rid", ""); // ???????????????
        paramMap.put("tabname", ""); // ??????
        paramMap.put("poolarea_no", ""); // ?????????
        paramMap.put("updt_time", MapUtils.get(map, "updtTime")); // ????????????
        paramMap.put("page_num", MapUtils.get(map, "pageNum")); // ????????????
        paramMap.put("page_size", MapUtils.get(map, "pageSize")); // ???????????????
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("data", paramMap);
        map.put("msgName","??????????????????????????????");
        map.put("isHospital","");
        map.put("visitId","");
        Map<String, Object> resultMap = insureUnifiedCommonUtil.commonInsureUnified(hospCode, insureConfigurationDTO.getRegCode(),  Constant.UnifiedPay.REGISTER.UP_1318, inputMap,map);
        Map<String, Object> outMap = MapUtils.get(resultMap, "output");
        List<Map<String, Object>> mapList = MapUtils.get(outMap, "data");
        List<InsureUnifiedLimitPriceDO> unifiedMatchDOList = insureDirectoryDownLoadDAO.queyrAllInsureUnifiedLimitPrice(map);
        Map<String, InsureUnifiedLimitPriceDO> collect = unifiedMatchDOList.stream().collect(Collectors.toMap(InsureUnifiedLimitPriceDO::getRid, Function.identity(), (k1, k2) -> k1));
        if (!ListUtils.isEmpty(mapList)) {
            List<InsureUnifiedLimitPriceDO> insertList = new ArrayList<>();
            InsureUnifiedLimitPriceDO insureUnifiedLimitPriceDO = null;
            for (Map<String, Object> item : mapList) {
                if ((collect.isEmpty() || !collect.containsKey(MapUtils.get(item, "rid"))) && !StringUtils.isEmpty(MapUtils.get(item, "hilist_code"))) {
                    insureUnifiedLimitPriceDO = new InsureUnifiedLimitPriceDO();
                    insureUnifiedLimitPriceDO.setId(SnowflakeUtils.getId());
                    insureUnifiedLimitPriceDO.setHospCode(hospCode);
                    insureUnifiedLimitPriceDO.setHilistCode(MapUtils.get(item, "hilist_code"));
                    insureUnifiedLimitPriceDO.setHilistLmtpricType(MapUtils.get(item, "hilist_lmtpric_type"));
                    insureUnifiedLimitPriceDO.setOverlmtDspoWay(MapUtils.get(item, "overlmt_dspo_way"));
                    insureUnifiedLimitPriceDO.setInsuAdmdvs(MapUtils.get(item, "insu_admdvs"));
                    if (StringUtils.isEmpty(MapUtils.get(item, "begndate"))) {
                        insureUnifiedLimitPriceDO.setBegndate(null);
                    } else {
                        insureUnifiedLimitPriceDO.setBegndate(DateUtils.parse(MapUtils.get(item, "begndate"), DateUtils.Y_M_DH_M_S));
                    }
                    if (StringUtils.isEmpty(MapUtils.get(item, "enddate"))) {
                        insureUnifiedLimitPriceDO.setEnddate(null);
                    } else {
                        insureUnifiedLimitPriceDO.setEnddate(DateUtils.parse(MapUtils.get(item, "enddate"), DateUtils.Y_M_DH_M_S));
                    }
                    insureUnifiedLimitPriceDO.setHilistPricUplmtAmt(MapUtils.get(item, "hilist_pric_uplmt_amt"));
                    insureUnifiedLimitPriceDO.setValiFlag(MapUtils.get(item, "vali_flag"));
                    insureUnifiedLimitPriceDO.setRid(MapUtils.get(item, "rid"));


                    if (StringUtils.isEmpty(MapUtils.get(item, "crte_time"))) {
                        insureUnifiedLimitPriceDO.setCrteTime(null);
                    } else {
                        insureUnifiedLimitPriceDO.setCrteTime(DateUtils.parse(MapUtils.get(item, "crte_time"), DateUtils.Y_M_DH_M_S));
                    }
                    insureUnifiedLimitPriceDO.setOptTime(null);
                    if (StringUtils.isEmpty(MapUtils.get(item, "updt_time"))) {
                        insureUnifiedLimitPriceDO.setUpdtTime(null);
                    } else {
                        insureUnifiedLimitPriceDO.setUpdtTime(DateUtils.parse(MapUtils.get(item, "updt_time"), DateUtils.Y_M_DH_M_S));
                    }

                    insureUnifiedLimitPriceDO.setCrterId(MapUtils.get(item, "crter_id"));
                    insureUnifiedLimitPriceDO.setCrterName(MapUtils.get(item, "crter_name"));
                    insureUnifiedLimitPriceDO.setCrteOptinsNo(MapUtils.get(item, "crte_optins_no"));
                    insureUnifiedLimitPriceDO.setOpterId(MapUtils.get(item, "opter_id"));
                    insureUnifiedLimitPriceDO.setOpterName(MapUtils.get(item, "opter_name"));
                    insureUnifiedLimitPriceDO.setOptinsNo(MapUtils.get(item, "optins_no"));
                    insureUnifiedLimitPriceDO.setTabname(MapUtils.get(item, "tabname"));
                    insureUnifiedLimitPriceDO.setPoolareaNo(MapUtils.get(item, "poolarea_no"));
                    insertList.add(insureUnifiedLimitPriceDO);
                }
            }
            if (!ListUtils.isEmpty(insertList)) {
                int toIndex = 1000;
                for (int i = 0; i < insertList.size(); i += 1000) {
                    if (i + 1000 > insertList.size()) {
                        toIndex = insertList.size() - i;
                    }
                    List newList = insertList.subList(i, i + toIndex);
                    insureDirectoryDownLoadDAO.insertInsureUnfiedLimitPrice(newList);
                }
            }
        }
        else{
            throw new AppException("??????"+MapUtils.get(map, "hilistCode")+"??????????????????????????????????????????");
        }
        return map;
    }
    private Map<String, Object> insureUnfiedLimitPriceParam(Map<String, Object> map,Map<String, Object> paramMap) {
        paramMap.put("query_date", DateUtils.format(DateUtils.getNow(), DateUtils.Y_M_DH_M_S)); // ???????????????
        paramMap.put("hilist_code", MapUtils.get(map, "hilistCode")); // ??????????????????
        paramMap.put("hilist_lmtpric_type", ""); // ????????????????????????
        paramMap.put("overlmt_dspo_way", ""); // ??????????????????????????????
        paramMap.put("insu_admdvs", ""); //????????????????????????
        paramMap.put("begndate", ""); // ????????????
        paramMap.put("enddate", ""); // ????????????
        paramMap.put("vali_flag", Constants.SF.S); // ????????????
        paramMap.put("rid", ""); // ???????????????
        paramMap.put("tabname", ""); // ??????
        paramMap.put("poolarea_no", ""); // ?????????
        paramMap.put("updt_time", MapUtils.get(map, "updtTime")); // ????????????

        return paramMap;
    }
}
