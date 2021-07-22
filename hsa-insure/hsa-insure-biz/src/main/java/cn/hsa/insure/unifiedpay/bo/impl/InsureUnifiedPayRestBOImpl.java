package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
import cn.hsa.module.base.bd.service.BaseDiseaseService;
import cn.hsa.module.base.bi.dto.BaseItemDTO;
import cn.hsa.module.base.bi.service.BaseItemService;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.bmm.service.BaseMaterialService;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.base.drug.service.BaseDrugService;
import cn.hsa.module.insure.module.dao.*;
import cn.hsa.module.insure.module.dto.*;
import cn.hsa.module.insure.module.entity.*;
import cn.hsa.module.insure.module.service.InsureDiseaseMatchService;
import cn.hsa.module.insure.outpt.bo.InsureUnifiedPayRestBO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
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
 * @Company: 创智和宇
 **/
@Slf4j
@Component
public class InsureUnifiedPayRestBOImpl extends HsafBO implements InsureUnifiedPayRestBO {

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    @Resource
    private InsureIndividualVisitDAO insureIndividualVisitDAO;

    @Resource
    InsureItemMatchDAO insureItemMatchDAO;

    @Resource
    InsureItemDAO insureItemDAO;

    @Resource
    BaseDrugService baseDrugService;
    @Resource
    BaseMaterialService baseMaterialService;

    @Resource
    BaseItemService baseItemService;

    @Resource
    BaseDiseaseService baseDiseaseService;

    @Resource
    InsureDiseaseDAO insureDiseaseDAO;

    @Resource
    InsureDiseaseMatchDAO insureDiseaseMatchDAO;

    @Resource
    InsureDiseaseMatchService insureDiseaseMatchService;

    @Resource
    InsureIndividualSettleDAO insureIndividualSettleDAO;

    @Resource
    InsureDirectoryDownLoadDAO insureDirectoryDownLoadDAO;
    @Resource
    private SysParameterService sysParameterService_consumer;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @param map
     * @Method downLoadItem
     * @Desrciption 医保通一支付平台，项目匹配查询
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
        Map httpParam = new HashMap();
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_1317);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("query_date", DateUtils.format(DateUtils.getNow(), DateUtils.Y_M_D));  // 查询时间点
        dataMap.put("fixmedins_code", "");  // 定点医药机构编号
        dataMap.put("medins_list_codg", "");  // 定点医药机构目录编号
        dataMap.put("medins_list_name", "");  // 定点医药机构目录名称
        dataMap.put("insu_admdvs", insureConfigurationDTO.getRegCode());  // 参保机构医保区划
        dataMap.put("list_type", "");  // 目录类别
        dataMap.put("med_list_codg", "");  // 医疗目录编码
        dataMap.put("begndate", "");  // 开始日期
        dataMap.put("vali_flag", Constants.SF.S);  // 有效标志
        dataMap.put("updt_time", DateUtils.format(startDate, DateUtils.Y_M_D));  // 更新时间
        dataMap.put("page_num", 1);  // 当前页数
        dataMap.put("page_size", Integer.MAX_VALUE);  // 本页数据量
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("data", dataMap);
        httpParam.put("input", inputMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("统一支付平台目录匹配信息查询入参:" + json);
        String url = insureConfigurationDTO.getUrl();
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(url, json);
        logger.info("统一支付平台目录匹配信息查询回参:" + resultJson);
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson);
        if (!"0".equals(resultMap.get("infcode").toString())) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        if ("999".equals(MapUtils.get(resultMap, "code"))) {
            throw new AppException((String) resultMap.get("msg"));
        }
        if (!MapUtils.get(resultMap, "infcode").equals("0")) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        Map<String, Object> outputMap = (Map<String, Object>) resultMap.get("output");
        List<Map<String, Object>> resultDataMap = (List<Map<String, Object>>) outputMap.get("data");
        if (!ListUtils.isEmpty(resultDataMap)) {
            InsureItemMatchDTO insureItemMatchDTO = new InsureItemMatchDTO();
            insureItemMatchDTO.setHospCode(hospCode); // 医院编码
            insureItemMatchDTO.setInsureRegCode(insureRegCode); // 医保机构编码
            insureItemMatchDTO.setAuditCode(Constants.SF.F);
            insureItemMatchDTO.setIsValid(Constants.SF.S);
            List<InsureItemMatchDTO> itemDTOList = insureItemMatchDAO.queryPageOrAll(insureItemMatchDTO); // 查询出上次下载的医保药品数据，且已经存库
            Map<String, InsureItemMatchDTO> collect = itemDTOList.stream().collect(Collectors.toMap(InsureItemMatchDTO::getHospItemCode, Function.identity(), (k1, k2) -> k1));
            InsureItemMatchDTO itemMatchDTO = null;
            List<InsureItemMatchDTO> insureItemDTOList = new ArrayList<>();
            for (Map<String, Object> item : resultDataMap) {
                if (!collect.isEmpty() && collect.containsKey(item.get("medins_list_codg").toString())) {
                    itemMatchDTO = new InsureItemMatchDTO();
                    itemMatchDTO.setHospCode(hospCode); // 医院编码
                    itemMatchDTO.setHospItemCode(MapUtils.get(item, "medins_list_codg"));  // 定点医药机构目录编号
                    itemMatchDTO.setInsureRegCode(MapUtils.get(item, "insu_admdvs")); // 参保机构医保区划
                    itemMatchDTO.setItemCode(MapUtils.get(item, "list_type")); // 目录类别
                    itemMatchDTO.setInsureItemCode(MapUtils.get(item, "med_list_codg")); // 医疗目录编码
                    itemMatchDTO.setTakeDate(DateUtils.parse(MapUtils.get(item, "begndate"), DateUtils.Y_M_DH_M_S)); // 开始日期
                    itemMatchDTO.setLoseDate(DateUtils.parse(MapUtils.get(item, "enddate"), DateUtils.Y_M_DH_M_S));  // 结束日期
                    if (Constants.SF.S.equals(MapUtils.get(item, "vali_flag"))) {
                        itemMatchDTO.setIsValid(Constants.SF.S); // 有效标志
                        itemMatchDTO.setIsMatch(Constants.SF.S); // 是否匹配
                        itemMatchDTO.setIsTrans(Constants.SF.S); // 是否传输
                        itemMatchDTO.setAuditCode(Constants.SF.S); // 审核状态
                    } else {
                        itemMatchDTO.setIsValid(Constants.SF.F); // 有效标志
                        itemMatchDTO.setIsMatch(Constants.SF.F); // 是否匹配
                        itemMatchDTO.setIsTrans(Constants.SF.F); // 是否传输
                        itemMatchDTO.setAuditCode(Constants.SF.F); // 审核状态
                    }
                    insureItemDTOList.add(itemMatchDTO);
                }
            }
            System.out.println("数据处理成功！！！" + insureItemDTOList.size());
            int batchCount = 300; // 定义分批处理的数据大小
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
                    System.out.println("循环次数" + k);

                }
            }
        }
        return resultMap;
    }

    /**
     * @Method UP_9162
     * @Desrciption 医保统一支付平台-目录对照下载
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
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_9162);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("fixmedins_hilist_id", ""); // 定点医药机构目录编号
        paramMap.put("list_type", listType); // 目录类别
        paramMap.put("med_list_codg", ""); // 医疗目录编码
        paramMap.put("begntime", DateUtils.format(startDate, DateUtils.Y_M_D)); // 开始时间

        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("data", paramMap);
        httpParam.put("input", inputMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("统一支付平台目录目录对照下载入参:" + json);
        String url = insureConfigurationDTO.getUrl();
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(url, json);
        logger.info("统一支付平台目录目录对照下载回参:" + resultJson);
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
            System.out.println("数据接收成功！！！");
            for (Map<String, Object> item : resultDataMap) {
                if (collect.isEmpty() || !collect.containsKey(item.get("medins_list_codg").toString())) {
                    InsureItemMatchDTO itemMatchDTO = new InsureItemMatchDTO();
                    itemMatchDTO.setInsureItemCode(MapUtils.get(item, "fixmedins_hilist_id")); // 中心项目编码
                    itemMatchDTO.setInsureItemName(null); // 中心项目名称
                    itemMatchDTO.setItemCode(MapUtils.get(item, "list_type")); //  中心收费类别
                    itemMatchDTO.setInsureRegCode(MapUtils.get(item, "insu_admdvs")); // 定点医疗机构编码
                    itemMatchDTO.setHospItemCode(MapUtils.get(item, "medins_list_codg")); // 定点医疗机构项目编码
                    itemMatchDTO.setHospItemName(MapUtils.get(item, "medins_list_name")); // 定点医疗机构项目名称
                    itemMatchDTO.setHospItemPrepCode(MapUtils.get(item, "dosform")); // 定点医疗机构药品剂型
                    itemMatchDTO.setHospItemUnitCode(MapUtils.get(item, "prcunt")); // 单位
                    itemMatchDTO.setHospItemSpec(MapUtils.get(item, "spec")); // 规格
                    itemMatchDTO.setIsMatch(Constants.SF.S); // 审核标志
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
     * @Desrciption 医保统一支付平台：目录对照上传
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
            throw new AppException("没有上传的数据");
        }
        Map httpParam = new HashMap();
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_3301);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
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
                    catalogcompinMap.put("fixmedins_hilist_id", itemDTO.getHospItemCode());  // 定点医药机构目录编号
                    catalogcompinMap.put("fixmedins_hilist_name", itemDTO.getHospItemName()); // 定点医药机构目录名称
                    catalogcompinMap.put("list_type", itemDTO.getItemCode()); // 目录类别
                    catalogcompinMap.put("med_list_codg", itemDTO.getInsureItemCode()); // 医疗目录编码
                    catalogcompinMap.put("begndate", DateUtils.format(startDate, DateUtils.Y_M_DH_M_S));  // 开始日期
                    catalogcompinMap.put("enddate", DateUtils.format(endDate, DateUtils.Y_M_DH_M_S)); // 结束日期
                    catalogcompinMap.put("aprvno", ""); // 批准文号
                    catalogcompinMap.put("dosform", ""); // 剂型
                    catalogcompinMap.put("exct_cont", ""); // 除外内容
                    catalogcompinMap.put("item_cont", ""); // 项目内涵
                    catalogcompinMap.put("prcunt", ""); // 计价单位
                    catalogcompinMap.put("spec", ""); // 规格
                    catalogcompinMap.put("pacspec", ""); // 包装规格
                    catalogcompinMap.put("memo", ""); // 备注
                    catalogcompinMap.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); // 中心编码
                    catalogcompinMap.put("medins_code", hospCode); // 医院编码
                    catalogcompinMap.put("match_type", ""); // 匹配类型
                    catalogcompinMap.put("hosp_dosg", ""); // 医院目录剂型
                    catalogcompinMap.put("pric", ""); // 单价
                    catalogcompinMap.put("item_name", ""); // 中心目录名称
                    catalogcompinMap.put("opter", crteId); // 操作员工号
                    catalogcompinMap.put("opter_name", crteName); // 操作员姓名
                    catalogcompinMap.put("trade_code", itemDTO.getItemCode()); // 中心的项目编码
                    catalogcompinMap.put("trade_name", ""); // 中心的项目名称
                    catalogcompinMap.put("valid_flag", Constants.SF.S);
                    catalogcompinMap.put("chk_stas", "");
                    catalogcompinMap.put("staple_flag", ""); // 甲乙标志
                    catalogcompinMap.put("chrg_sour", ""); // 收费标准来源
                    mapList.add(catalogcompinMap);
                }
            }
        }
        Map<String, Object> dataMap = null;
        String resultJson = "";
        System.out.println("统一支付平台目录对照上传数据量总数为:" + mapList.size());
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
                httpParam.put("input", dataMap);
                String json = JSONObject.toJSONString(httpParam);
                logger.info("统一支付平台目录对照上传入参:" + json);
                resultJson = HttpConnectUtil.unifiedPayPostUtil(url, json);
                if (StringUtils.isEmpty(resultJson)) {
                    throw new AppException("无法访问医保统一支付平台");
                }
                Map<String, Object> resultMap = JSONObject.parseObject(resultJson, Map.class);
                if ("999".equals(MapUtils.get(resultMap, "code"))) {
                    throw new RuntimeException((String) resultMap.get("msg"));
                }
                if (!MapUtils.get(resultMap, "infcode").equals("0")) {
                    throw new RuntimeException((String) resultMap.get("err_msg"));
                }
                logger.info("统一支付平台目录对照上传回参:" + resultJson);
            }

        } else {
            dataMap = new HashMap<>();
            dataMap.put("data", mapList);
            httpParam.put("input", dataMap);
            String json = JSONObject.toJSONString(httpParam);
            logger.info("统一支付平台目录对照上传入参:" + json);
            resultJson = HttpConnectUtil.unifiedPayPostUtil(url, json);
            if (StringUtils.isEmpty(resultJson)) {
                throw new AppException("无法访问医保统一支付平台");
            }
            Map<String, Object> resultMap = JSONObject.parseObject(resultJson, Map.class);
            if ("999".equals(MapUtils.get(resultMap, "code"))) {
                throw new RuntimeException((String) resultMap.get("msg"));
            }
            if (!MapUtils.get(resultMap, "infcode").equals("0")) {
                throw new RuntimeException((String) resultMap.get("err_msg"));
            }
            logger.info("统一支付平台目录对照上传回参:" + resultJson);
        }
        map.put("insureItemDTOList", insureItemDTOList);
        return map;
    }

    /**
     * @Method UP_3302
     * @Desrciption 医保统一支付平台：目录对照撤销
     * @Param 需要判斷是刪除數據時候對照撤銷 還是選擇撤銷全部
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
        Map httpParam = new HashMap();
        Map<String, Object> dataMap = null;
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_3302);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
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
                throw new AppException("没有要撤销的匹配数据");
            }
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("purcinfo", mapList);
        httpParam.put("input", paramMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("统一支付平台目录对照撤销入参:" + json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
        if (StringUtils.isEmpty(resultJson)) {
            throw new AppException("无法访问医保统一支付平台");
        }
        logger.info("统一支付平台目录对照撤销回参:" + resultJson);
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson);
        if ("999".equals(MapUtils.get(resultMap, "code"))) {
            throw new AppException((String) resultMap.get("msg"));
        }
        if (!MapUtils.get(resultMap, "infcode").equals("0")) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        Map<String, Object> outputMap = (Map<String, Object>) resultMap.get("output");
        List<Map<String, Object>> resultDataMap = (List<Map<String, Object>>) outputMap.get("sucessData");
        if (ListUtils.isEmpty(resultDataMap)) {
            throw new AppException("撤销失败");
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
     * @Desrciption 医保统一支付：结算单查询
     * @Param map
     * @Author fuhui
     * @Date 2021/4/13 19:38
     * @Return
     **/
    @Override
    public Map<String, Object> selectSettleQuery(Map<String, Object> map) {
        String hospCode = map.get("hospCode").toString();
        /**
         * 获取医保就诊信息
         */
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(map);
        InsureIndividualSettleDTO individualSettleDTO = new InsureIndividualSettleDTO();
        individualSettleDTO.setHospCode(hospCode);
        String visitId = (String) map.get("visitId");//就诊id
        individualSettleDTO.setVisitId(visitId);
        individualSettleDTO.setState("0");
        individualSettleDTO = insureIndividualSettleDAO.getById(individualSettleDTO);

        /**
         * 获取访问的url地址
         */
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(insureIndividualVisitDTO.getMedicineOrgCode()); // 医疗机构编码;
        insureConfigurationDTO = getInsureConfiguration(insureConfigurationDTO);


        Map httpParam = new HashMap();
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_1201);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
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
        logger.info("结算单查询入参:" + json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
        logger.info("结算单查询回参:" + resultJson);
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
     * @Desrciption 医保统一支付平台：人员待遇享受检查
     * @Param map
     * @Author fuhui
     * @Date 2021/4/13 14:56
     * @Return
     **/

    @Override
    public Map<String, Object> selectPersonTreatment(Map<String, Object> map) {
        String hospCode = map.get("hospCode").toString();
        /**
         * 获取医保就诊信息
         */
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(map);
        /**
         * 获取访问的url地址
         */
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(insureIndividualVisitDTO.getMedicineOrgCode()); // 医疗机构编码;
        insureConfigurationDTO = getInsureConfiguration(insureConfigurationDTO);
        Map httpParam = new HashMap();
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_2001);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("psn_no", insureIndividualVisitDTO.getAac001());
        dataMap.put("insutype", insureIndividualVisitDTO.getAae140());
        dataMap.put("fixmedins_code", insureConfigurationDTO.getOrgCode());
        dataMap.put("med_type", insureIndividualVisitDTO.getAka130());
        dataMap.put("begntime", MapUtils.get(map, "startDate"));
        dataMap.put("endtime", MapUtils.get(map, "endDate"));
        dataMap.put("dise_codg", insureIndividualVisitDTO.getVisitIcdCode());
        dataMap.put("dise_name", insureIndividualVisitDTO.getVisitIcdName());
        dataMap.put("oprn_oprt_code", null);
        dataMap.put("oprn_oprt_name", null);
        dataMap.put("matn_type", null);
        dataMap.put("birctrl_type", null);

        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("data", dataMap);
        httpParam.put("input", inputMap);

        String json = JSONObject.toJSONString(httpParam);
        logger.info("人员待遇享受检查入参:" + json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
        if (StringUtils.isEmpty(resultJson)) {
            throw new AppException("无法访问医保统一支付平台");
        }
        logger.info("人员待遇享受检查回参:" + resultJson);
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson);
        if ("999".equals(MapUtils.get(resultMap, "code"))) {
            throw new AppException((String) resultMap.get("msg"));
        }
        if (!MapUtils.get(resultMap, "infcode").equals("0")) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        Map<String, Object> outputMap = (Map<String, Object>) resultMap.get("output");
        List<Map<String, Object>> resultDataMap = MapUtils.get(outputMap, "trtinfo");
        map.put("resultDataMap", resultDataMap);
        return map;
    }


    /**
     * @Method UP_1201
     * @Desrciption 医保统一支付平台：获取医疗机构信息
     * @Param
     * @Author fuhui
     * @Date 2021/4/13 14:56
     * @Return
     **/
    public Map<String, Object> getMedisnInfo(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String orgCode = MapUtils.get(map, "orgCode");
        String insureServiceType = MapUtils.get(map, "insureServiceType");
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(orgCode);
        insureConfigurationDTO.setIsValid(Constants.SF.S);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);

        Map httpParam = new HashMap();
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_1201);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
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
        logger.info("获取定点医药机构信息入参:" + json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
        logger.info("获取定点医药机构信息入参回参:" + resultJson);
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson);
        Map<String, Object> outputMap = (Map<String, Object>) resultMap.get("output");
        List<Map<String, Object>> resultDataMap = MapUtils.get(outputMap, "medinsinfo");
        return resultDataMap.get(0);
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

    /**
     * @Method commonGetVisitInfo
     * @Desrciption 医保统一支付：门诊患者，统一患者就诊信息查询接口
     * @Param map
     * @Author fuhui
     * @Date 2021/2/14 10:57
     * @Return InsureIndividualVisitDTO
     **/
    public InsureIndividualVisitDTO commonGetVisitInfo(Map<String, Object> map) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        String hospCode = (String) map.get("hospCode");//医院编码
        String visitId = (String) map.get("visitId");//就诊id
        Map<String, Object> insureVisitParam = new HashMap<String, Object>();
        insureVisitParam.put("id", visitId);
        insureVisitParam.put("hospCode", hospCode);
        insureIndividualVisitDTO = insureIndividualVisitDAO.getInsureIndividualVisitById(insureVisitParam);
        if (insureIndividualVisitDTO == null || StringUtils.isEmpty(insureIndividualVisitDTO.getId())) {
            throw new AppException("未查找到医保就诊信息，请做医保登记。");
        }
        return insureIndividualVisitDTO;
    }

    /**
     * @param insureItemDTO
     * @Method queryPageUnifiedItem
     * @Desrciption 医保统一支付平台：分页查询医保下载回来的数据
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
     * @param itemMatchDTO
     * @Method insertUnifiedHandMatch
     * @Desrciption 医保统一支付平台：手动匹配医保项目信息和医院his的项目信息
     * 手动匹配根据医院的项目id 查找出对应的项目信息。
     * 根据医保的id查询医保的项目信息，两者进行组合
     * @Param hospItemType:医院项目类别，用来区分手动匹配的类型
     * @Author fuhui
     * @Date 2021/4/10 1:45
     * @Return
     */
    @Override
    public Boolean insertUnifiedHandMatch(InsureItemMatchDTO itemMatchDTO) {
        Map<String, Object> map = new HashMap<>();
        String hospCode = itemMatchDTO.getHospCode();
        String hospItemId = itemMatchDTO.getHospItemId();
        map.put("hospCode", hospCode);
        map.put("id", hospItemId);
        String hospItemType = itemMatchDTO.getHospItemType();
        if (StringUtils.isEmpty(hospItemType)) {
            hospItemType = "1";
        }
        BaseDrugDTO baseDrugDTO = null;
        BaseMaterialDTO baseMaterialDTO = null;
        BaseItemDTO baseItemDTO = null;
        InsureItemDTO itemDTO = null;
        InsureDiseaseDTO insureDiseaseDTO = null;
        BaseDiseaseDTO baseDiseaseDTO = null;
        if (Constants.XMLB.YP.equals(hospItemType)) {
            baseDrugDTO = new BaseDrugDTO();
            baseDrugDTO.setId(hospItemId);
            baseDrugDTO.setHospCode(hospCode);
            map.put("baseDrugDTO", baseDrugDTO);
            itemMatchDTO.setIsDiseaseHandler(false);
            baseDrugDTO = baseDrugService.getById(map).getData();
        }
        if (Constants.XMLB.CL.equals(hospItemType)) {
            baseMaterialDTO = new BaseMaterialDTO();
            baseMaterialDTO.setHospCode(hospCode);
            baseMaterialDTO.setId(hospItemId);
            map.put("baseMaterialDTO", baseMaterialDTO);
            map.put("type", "CLFL");
            itemMatchDTO.setIsDiseaseHandler(false);
            baseMaterialDTO = baseMaterialService.getById(map).getData();
        }
        if (Constants.XMLB.XM.equals(hospItemType)) {
            baseItemDTO = new BaseItemDTO();
            baseItemDTO.setId(hospItemId);
            baseItemDTO.setHospCode(hospCode);
            map.put("baseItemDTO", baseItemDTO);
            itemMatchDTO.setIsDiseaseHandler(false);
            baseItemDTO = baseItemService.getById(map).getData();
        }
        if ("5".equals(itemMatchDTO.getHospItemType())) {
            baseDiseaseDTO = new BaseDiseaseDTO();
            baseDiseaseDTO.setId(hospItemId);
            baseDiseaseDTO.setHospCode(hospCode);
            map.put("baseDiseaseDTO", baseDiseaseDTO);
            itemMatchDTO.setIsDiseaseHandler(true);
            baseDiseaseDTO = baseDiseaseService.getById(map).getData();
        }
        if (false == itemMatchDTO.getIsDiseaseHandler()) {
            itemDTO = insureItemDAO.getById(itemMatchDTO);
        } else {
            insureDiseaseDTO = insureDiseaseDAO.getById(itemMatchDTO);
        }
        if (baseDiseaseDTO != null && insureDiseaseDTO != null) {
            insertDiseaseHandler(insureDiseaseDTO, baseDiseaseDTO, itemMatchDTO);
        }
        if (baseDrugDTO != null && itemDTO != null) {
            insertHandlerDrug(itemDTO, baseDrugDTO, itemMatchDTO);
        }
        if (baseItemDTO != null && itemDTO != null) {
            insertHandlerItem(itemDTO, baseItemDTO, itemMatchDTO);
        }
        if (baseMaterialDTO != null && itemDTO != null) {
            insertHandlerMaterial(itemDTO, baseMaterialDTO, itemMatchDTO);
        }
        return true;
    }

    /**
     * @Method insertHandlerItem
     * @Desrciption 医保统一支付：材料类型的手动匹配
     * @Param
     * @Author fuhui
     * @Date 2021/4/10 2:18
     * @Return
     **/
    private void insertHandlerMaterial(InsureItemDTO itemDTO, BaseMaterialDTO baseMaterialDTO, InsureItemMatchDTO itemMatchDTO) {
        List<InsureItemMatchDTO> list = insureItemMatchDAO.selectById(itemMatchDTO);
        if (!ListUtils.isEmpty(list)) {
            throw new AppException("该【" + list.get(0).getHospItemName() + "】已经匹配");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("id", SnowflakeUtils.getId());
        map.put("insureRegCode", itemMatchDTO.getInsureRegCode());
        map.put("hospCode", itemMatchDTO.getHospCode());
        map.put("itemCode", "301"); // 项目类别标志
        map.put("hospItemId", baseMaterialDTO.getId()); // 医院项目id
        map.put("hospItemName", baseMaterialDTO.getName()); // 医院项目名称
        map.put("hospItemCode", baseMaterialDTO.getCode()); // 医院项目编码
        map.put("hospItemType", Constants.XMLB.CL); // 医院项目类别
        map.put("hospItemSpec", baseMaterialDTO.getSpec()); // 医院项目规格
        map.put("hospItemUnitCode", baseMaterialDTO.getUnitCode()); // 医院项目单位
        map.put("hospItemUnitCode", null); // 医院项目剂型
        map.put("hospItemPrice", baseMaterialDTO.getPrice()); // 医院项目价格
        map.put("insureItemName", itemDTO.getItemName()); // 医保中心项目名称
        map.put("insureItemCode", itemDTO.getItemCode()); // 医保中心项目编码
        map.put("insureItemType", itemDTO.getItemType()); // 医保中心项目类别
        map.put("insureItemSpec", itemDTO.getItemSpec()); // 医保中心项目规格
        map.put("insureItemUnitCode", itemDTO.getItemUnitCode()); // 医保中心项目单位
        map.put("insureItemPrepCode", itemDTO.getItemDosage()); // 医保中心项目剂型
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

    /**
     * @Method insertHandlerItem
     * @Desrciption 医保统一支付：项目类型的手动匹配
     * @Param
     * @Author fuhui
     * @Date 2021/4/10 2:18
     * @Return
     **/
    private void insertHandlerItem(InsureItemDTO itemDTO, BaseItemDTO baseItemDTO, InsureItemMatchDTO itemMatchDTO) {
        List<InsureItemMatchDTO> list = insureItemMatchDAO.selectById(itemMatchDTO);
        if (!ListUtils.isEmpty(list)) {
            throw new AppException("该【" + list.get(0).getHospItemName() + "】已经匹配");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id", SnowflakeUtils.getId());
        map.put("hospCode", itemMatchDTO.getHospCode());
        map.put("insureRegCode", itemMatchDTO.getInsureRegCode());
        map.put("itemCode", "201"); // 项目类别标志
        map.put("hospItemId", baseItemDTO.getId()); // 医院项目id
        map.put("hospItemName", baseItemDTO.getName()); // 医院项目名称
        map.put("hospItemCode", baseItemDTO.getCode()); // 医院项目编码
        map.put("hospItemType", Constants.XMLB.XM); // 医院项目类别
        map.put("hospItemSpec", baseItemDTO.getSpec()); // 医院项目规格
        map.put("hospItemUnitCode", baseItemDTO.getUnitCode()); // 医院项目单位
        map.put("hospItemUnitCode", null); // 医院项目剂型
        map.put("hospItemPrice", baseItemDTO.getPrice()); // 医院项目价格
        map.put("insureItemName", itemDTO.getItemName()); // 医保中心项目名称
        map.put("insureItemCode", itemDTO.getItemCode()); // 医保中心项目编码
        map.put("insureItemType", itemDTO.getItemType()); // 医保中心项目类别
        map.put("insureItemSpec", itemDTO.getItemSpec()); // 医保中心项目规格
        map.put("insureItemUnitCode", itemDTO.getItemUnitCode()); // 医保中心项目单位
        map.put("insureItemPrepCode", itemDTO.getItemDosage()); // 医保中心项目剂型
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
    }

    /**
     * @param insureItemDTO
     * @Method queryInsureItemAll
     * @Desrciption :查询，调用长沙医保返回回来的项目数据
     * @Param insureItemDTO：itemName:项目名称，itemCode编码
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
     * @Desrciption :根据 条件分页查询 his系统的药品，材料，项目，疾病信息
     * @Param insureItemDTO：itemName:项目名称，itemCode编码
     * @Author fuhui
     * @Date 2021/1/27 11:17
     * @Return
     */
    @Override
    public PageDTO queryHospItemPage(Map<String, Object> map) {
        String itemCode = map.get("itemCode").toString();
        String hospCode = map.get("hospCode").toString();
        Integer pageSize = Integer.valueOf(map.get("pageSize").toString());
        Integer pageNo = Integer.valueOf(map.get("pageNo").toString());
        PageDTO data;
        if (StringUtils.isEmpty(itemCode)) {    // 默认查询药品
            itemCode = "1";
        }
        if (Constants.XMLB.YP.equals(itemCode)) {
            BaseDrugDTO baseDrugDTO = new BaseDrugDTO();
            baseDrugDTO.setHospCode(hospCode);
            baseDrugDTO.setKeyword(map.get("keyword").toString());
            baseDrugDTO.setIsValid("1");
            baseDrugDTO.setPageNo(pageNo);
            baseDrugDTO.setPageSize(pageSize);
            map.put("baseDrugDTO", baseDrugDTO);
            WrapperResponse<PageDTO> pageDTOWrapperResponse = baseDrugService.queryUnifiedPage(map);
            data = pageDTOWrapperResponse.getData();
        } else if (Constants.XMLB.XM.equals(itemCode)) {
            BaseItemDTO baseItemDTO = new BaseItemDTO();
            baseItemDTO.setHospCode(hospCode);
            baseItemDTO.setKeyword(map.get("keyword").toString());
            baseItemDTO.setIsValid("1");
            baseItemDTO.setPageNo(pageNo);
            baseItemDTO.setPageSize(pageSize);
            map.put("baseItemDTO", baseItemDTO);
            WrapperResponse<PageDTO> pageDTOWrapperResponse = baseItemService.queryUnifiedPage(map);
            data = pageDTOWrapperResponse.getData();
        } else if (Constants.XMLB.CL.equals(itemCode)) {
            BaseMaterialDTO baseMaterialDTO = new BaseMaterialDTO();
            baseMaterialDTO.setHospCode(hospCode);
            baseMaterialDTO.setKeyword(map.get("keyword").toString());
            baseMaterialDTO.setIsValid("1");
            baseMaterialDTO.setPageSize(pageSize);
            baseMaterialDTO.setPageNo(pageNo);
            map.put("baseMaterialDTO", baseMaterialDTO);
            WrapperResponse<PageDTO> pageDTOWrapperResponse = baseMaterialService.queryUnifiedPage(map);
            data = pageDTOWrapperResponse.getData();
        } else {
            BaseDiseaseDTO baseDiseaseDTO = new BaseDiseaseDTO();
            baseDiseaseDTO.setHospCode(hospCode);
            baseDiseaseDTO.setKeyword(map.get("keyword").toString());
            baseDiseaseDTO.setIsValid("1");
            baseDiseaseDTO.setPageNo(pageNo);
            baseDiseaseDTO.setPageSize(pageSize);
            map.put("baseDiseaseDTO", baseDiseaseDTO);
            WrapperResponse<PageDTO> pageDTOWrapperResponse = baseDiseaseService.queryPage(map);
            data = pageDTOWrapperResponse.getData();
        }
        if (data != null) {
            return data;
        } else {
            return null;
        }
    }

    /**
     * @param itemMatchDTO
     * @Method insertUnifiedAutoMatch
     * @Desrciption 医保统一支付平台：自动匹配医保项目信息和医院his的项目信息
     * @Param
     * @Author fuhui
     * @Date 2021/4/10 1:45
     * @Return
     */
    @Override
    public Integer insertUnifiedAutoMatch(InsureItemMatchDTO itemMatchDTO) {
        String itemCode = itemMatchDTO.getMatchCode();   // 匹配项目
        String insureRegCode = itemMatchDTO.getRegCode(); // 医疗机构编码
        String hospCode = itemMatchDTO.getHospCode(); // 医院编码
        InsureItemDTO insureItemDTO = new InsureItemDTO();
        insureItemDTO.setHospCode(hospCode);
        insureItemDTO.setInsureRegCode(insureRegCode);
        Integer integer = null;
        Map map = new HashMap();
        map.put("hospCode", hospCode);
        map.put("regCode", insureRegCode);
        map.put("itemMatchDTO", itemMatchDTO);
        if (Constants.XMLB.YP.equals(itemCode)) {
            BaseDrugDTO baseDrugDTO = new BaseDrugDTO();
            baseDrugDTO.setDownLoadFlag("1");
            baseDrugDTO.setHospCode(hospCode);
            baseDrugDTO.setIsValid("1");
            if(Constant.UnifiedPay.MATCHCODE.GJBM.equals(itemMatchDTO.getMatchType())){
                baseDrugDTO.setIsNationCode(true);
            }
            map.put("baseDrugDTO", baseDrugDTO);
            WrapperResponse<List<BaseDrugDTO>> listWrapperResponse = baseDrugService.queryAll(map);
            List<BaseDrugDTO> baseDrugDTOList = listWrapperResponse.getData();  // 医院端药品
            List<String> stringList = new ArrayList<String>() {{
                add("1301");
                add("1302");
                add("1303");
                add("1304");
            }};
            insureItemDTO.setHandItemList(stringList);
            List<InsureItemDTO> insureItemDTOList = insureItemDAO.queryInsureItemAll(insureItemDTO);
            if (ListUtils.isEmpty(baseDrugDTOList) || ListUtils.isEmpty(insureItemDTOList)) {
                throw new AppException("自动匹配时，医院端或者医保下载回来的药品数据集为空");
            }
            List<InsureItemMatchDTO> insureItemMatchDTOList = handDrugAutoMatch(baseDrugDTOList, insureItemDTOList, map);
            if (!ListUtils.isEmpty(insureItemMatchDTOList)) {
                integer = insureItemMatchDAO.insertMatchItem(insureItemMatchDTOList);
            }

        } else if (Constants.XMLB.CL.equals(itemCode)) {
            BaseMaterialDTO baseMaterialDTO = new BaseMaterialDTO();
            baseMaterialDTO.setHospCode(hospCode);
            baseMaterialDTO.setIsValid("1");
            if(Constant.UnifiedPay.MATCHCODE.GJBM.equals(itemMatchDTO.getMatchType())){
                baseMaterialDTO.setIsNationCode(true);
            }
            baseMaterialDTO.setDownLoadFlag("1");
            map.put("baseMaterialDTO", baseMaterialDTO);
            WrapperResponse<List<BaseMaterialDTO>> listWrapperResponse2 = baseMaterialService.queryAll(map);
            List<BaseMaterialDTO> baseMaterialDTOList = listWrapperResponse2.getData(); // 医院端材料
            insureItemDTO.setDownLoadType("1306");
            List<InsureItemDTO> insureItemDTOList = insureItemDAO.queryInsureItemAll(insureItemDTO);
            List<InsureItemMatchDTO> insureItemMatchDTOList = handMaterialAutoMatch(baseMaterialDTOList, insureItemDTOList, map);
            if (!ListUtils.isEmpty(insureItemMatchDTOList)) {
                integer = insureItemMatchDAO.insertMatchItem(insureItemMatchDTOList);
            }

        } else if (Constants.XMLB.XM.equals(itemCode)) {
            BaseItemDTO baseItemDTO = new BaseItemDTO();
            baseItemDTO.setHospCode(hospCode);
            baseItemDTO.setIsValid("1");
            baseItemDTO.setDownLoadFlag("1");
            if(Constant.UnifiedPay.MATCHCODE.GJBM.equals(itemMatchDTO.getMatchType())){
                baseItemDTO.setIsNationCode(true);
            }
            map.put("baseItemDTO", baseItemDTO);
            WrapperResponse<List<BaseItemDTO>> listWrapperResponse1 = baseItemService.queryAll(map);
            List<BaseItemDTO> baseItemDTOList = listWrapperResponse1.getData(); // 医院端项目
            insureItemDTO.setDownLoadType("1305");
            insureItemDTO.setIsValid(Constants.SF.S);
            List<InsureItemDTO> insureItemDTOList = insureItemDAO.queryInsureItemAll(insureItemDTO);
            List<InsureItemMatchDTO> insureItemMatchDTOList = handItemAutoMatch(baseItemDTOList, insureItemDTOList, map);
            if (!ListUtils.isEmpty(insureItemMatchDTOList)) {
                integer = insureItemMatchDAO.insertMatchItem(insureItemMatchDTOList);
            }
        } else {
            BaseDiseaseDTO baseDiseaseDTO = new BaseDiseaseDTO();
            baseDiseaseDTO.setHospCode(map.get("hospCode").toString());
            baseDiseaseDTO.setIsValid("1");
            map.put("baseDiseaseDTO", baseDiseaseDTO);
            WrapperResponse<List<BaseDiseaseDTO>> wrapperResponse = baseDiseaseService.queryAll(map);
            List<BaseDiseaseDTO> baseDiseaseDTOList = wrapperResponse.getData(); // 医院端疾病
            InsureDiseaseDTO insureDiseaseDTO = new InsureDiseaseDTO();
            insureDiseaseDTO.setHospCode(map.get("hospCode").toString());
            insureDiseaseDTO.setInsureRegCode(insureRegCode);
            List<InsureDiseaseDTO> insureDiseaseDTOList = insureDiseaseDAO.queryAll(insureDiseaseDTO); // 医保中心端的疾病
            List<InsureDiseaseMatchDTO> insureDiseaseMatchDTOList = handDiseaseAutoMatch(baseDiseaseDTOList, insureDiseaseDTOList, map);
            if (!ListUtils.isEmpty(insureDiseaseMatchDTOList)) {
                integer = insureDiseaseMatchDAO.insertMatchDisease(insureDiseaseMatchDTOList);
            }
        }
        return integer;
    }

    /**
     * @param itemMatchDTO
     * @Method queryPageInsureItemMatch
     * @Desrciption 医保统一支付平台：显示匹配数据项
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
            //设置分页信息
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
     * @param baseDiseaseDTOList
     * @param insureDiseaseDTOList
     * @param map
     * @Method handDiseaseAutoMatch
     * @Desrciption 处理疾病自动匹配
     * @Param baseDrugDTOList:医院端的疾病集合
     * insureItemDTOList：从医保下载回来的疾病集合
     * map：封装参数信息医院编码，医疗机构编码 创建人，id，时间
     * @Author fuhui
     * @Date 2021/1/27 21:12
     * @Return
     */
    private List<InsureDiseaseMatchDTO> handDiseaseAutoMatch(List<BaseDiseaseDTO> baseDiseaseDTOList, List<InsureDiseaseDTO> insureDiseaseDTOList, Map map) {
        InsureItemMatchDTO itemMatchDTO = MapUtils.get(map, "itemMatchDTO");
        String userId = itemMatchDTO.getCrteId();
        String userName = itemMatchDTO.getCrteName();
        String matchType = itemMatchDTO.getMatchType();
        Map<String, InsureDiseaseDTO> collect = null;
        List<InsureDiseaseMatchDTO> insureDiseaseMatchDTOList = new ArrayList<>();
        if (Constant.UnifiedPay.MATCHCODE.MC.equals(matchType)) {
            collect = insureDiseaseDTOList.stream().collect(Collectors.toMap(InsureDiseaseDTO::getInsureIllnessName, Function.identity(), (k1, k2) -> k1));
            for (BaseDiseaseDTO baseDiseaseDTO : baseDiseaseDTOList) {
                if (!collect.isEmpty() && collect.containsKey(baseDiseaseDTO.getName())) {
                    InsureDiseaseMatchDTO insureDiseaseMatchDTO = new InsureDiseaseMatchDTO();
                    insureDiseaseMatchDTO.setId(SnowflakeUtils.getId()); //主键id
                    insureDiseaseMatchDTO.setHospCode(baseDiseaseDTO.getHospCode()); //  医院编码
                    insureDiseaseMatchDTO.setInsureRegCode(collect.get(baseDiseaseDTO.getName()).getInsureRegCode()); // 医保注册编码
                    insureDiseaseMatchDTO.setHospIllnessId(baseDiseaseDTO.getId()); //医院疾病ID
                    insureDiseaseMatchDTO.setHospIllnessCode(baseDiseaseDTO.getCode()); // 医院ICD编码
                    insureDiseaseMatchDTO.setHospIllnessName(baseDiseaseDTO.getName()); // 医院ICD名称
                    insureDiseaseMatchDTO.setInsureIllnessId(collect.get(baseDiseaseDTO.getName()).getInsureIllnessId()); // 医保中心疾病ID
                    insureDiseaseMatchDTO.setInsureIllnessCode(collect.get(baseDiseaseDTO.getName()).getInsureIllnessCode()); // 医保中心ICD编码
                    insureDiseaseMatchDTO.setInsureIllnessName(collect.get(baseDiseaseDTO.getName()).getInsureIllnessName()); // 医保中心ICD名称
                    insureDiseaseMatchDTO.setIsMatch(Constants.SF.S); // 是否匹配
                    insureDiseaseMatchDTO.setIsTrans(Constants.SF.S); // 是否传输
                    insureDiseaseMatchDTO.setAuditCode(Constants.SF.S); //审批状态代码
                    insureDiseaseMatchDTO.setRemark(collect.get(baseDiseaseDTO.getName()).getRemark()); // 备注
                    insureDiseaseMatchDTO.setCrteId(userId); // 创建人ID
                    insureDiseaseMatchDTO.setCrteName(userName); // 创建人姓名
                    insureDiseaseMatchDTO.setCrteTime(DateUtils.getNow()); // 创建时间
                    insureDiseaseMatchDTO.setTypeCode(collect.get(baseDiseaseDTO.getName()).getDownLoadType());
                    insureDiseaseMatchDTOList.add(insureDiseaseMatchDTO);

                }
            }
        } else {
            collect = insureDiseaseDTOList.stream().collect(Collectors.toMap(InsureDiseaseDTO::getInsureIllnessCode, Function.identity(), (k1, k2) -> k1));
            for (BaseDiseaseDTO baseDiseaseDTO : baseDiseaseDTOList) {
                if (!collect.isEmpty() && collect.containsKey(baseDiseaseDTO.getNationCode()) && !StringUtils.isEmpty(baseDiseaseDTO.getNationCode())) {
                    InsureDiseaseMatchDTO insureDiseaseMatchDTO = new InsureDiseaseMatchDTO();
                    insureDiseaseMatchDTO.setId(SnowflakeUtils.getId()); //主键id
                    insureDiseaseMatchDTO.setHospCode(baseDiseaseDTO.getHospCode()); //  医院编码
                    insureDiseaseMatchDTO.setInsureRegCode(collect.get(baseDiseaseDTO.getNationCode()).getInsureRegCode()); // 医保注册编码
                    insureDiseaseMatchDTO.setHospIllnessId(baseDiseaseDTO.getId()); //医院疾病ID
                    insureDiseaseMatchDTO.setHospIllnessCode(baseDiseaseDTO.getCode()); // 医院ICD编码
                    insureDiseaseMatchDTO.setHospIllnessName(baseDiseaseDTO.getName()); // 医院ICD名称
                    insureDiseaseMatchDTO.setInsureIllnessId(collect.get(baseDiseaseDTO.getNationCode()).getInsureIllnessId()); // 医保中心疾病ID
                    insureDiseaseMatchDTO.setInsureIllnessCode(collect.get(baseDiseaseDTO.getNationCode()).getInsureIllnessCode()); // 医保中心ICD编码
                    insureDiseaseMatchDTO.setInsureIllnessName(collect.get(baseDiseaseDTO.getNationCode()).getInsureIllnessName()); // 医保中心ICD名称
                    insureDiseaseMatchDTO.setIsMatch(Constants.SF.S); // 是否匹配
                    insureDiseaseMatchDTO.setIsTrans(Constants.SF.S); // 是否传输
                    insureDiseaseMatchDTO.setAuditCode(Constants.SF.S); //审批状态代码
                    insureDiseaseMatchDTO.setRemark(collect.get(baseDiseaseDTO.getNationCode()).getRemark()); // 备注
                    insureDiseaseMatchDTO.setCrteId(userId); // 创建人ID
                    insureDiseaseMatchDTO.setCrteName(userName); // 创建人姓名
                    insureDiseaseMatchDTO.setCrteTime(DateUtils.getNow()); // 创建时间
                    insureDiseaseMatchDTO.setDownLoadType(collect.get(baseDiseaseDTO.getNationCode()).getDownLoadType());
                    insureDiseaseMatchDTOList.add(insureDiseaseMatchDTO);

                }
            }
        }
        InsureDiseaseMatchDTO insureDiseaseMatchDTO = new InsureDiseaseMatchDTO();
        insureDiseaseMatchDTO.setHospCode(map.get("hospCode").toString());
        insureDiseaseMatchDTO.setInsureRegCode(map.get("regCode").toString());
        insureDiseaseMatchDTO.setIsMatch(Constants.SF.S);
        /**
         *      1.查询已经匹配的疾病信息
         *      2.如果匹配疾病为空，则直接跳出不做判断，返回上一步 匹配好的数据
         *      3.如果不为空，则需要把刚刚自动匹配好的数据和以前匹配的数据做对比，防止重复插入数据
         */
        List<InsureDiseaseMatchDTO> diseaseMatchDTOList = insureDiseaseMatchDAO.queryAll(insureDiseaseMatchDTO);
        if (!ListUtils.isEmpty(diseaseMatchDTOList)) {
            List<InsureDiseaseMatchDTO> matchDTOList = new ArrayList<>();
            Map<String, InsureDiseaseMatchDTO> diseaseMatchDTOMap = diseaseMatchDTOList.stream().collect(Collectors.toMap(InsureDiseaseMatchDTO::getHospIllnessId, Function.identity(), (k1, k2) -> k1));
            if (!ListUtils.isEmpty(insureDiseaseMatchDTOList)) {
                for (InsureDiseaseMatchDTO diseaseMatchDTO : insureDiseaseMatchDTOList) {
                    if (!diseaseMatchDTOMap.isEmpty() && !diseaseMatchDTOMap.containsKey(diseaseMatchDTO.getHospIllnessId())) {
                        matchDTOList.add(diseaseMatchDTO);
                    }
                }
            }
            return matchDTOList;
        } else {
            return insureDiseaseMatchDTOList;
        }
    }

    /**
     * @Method matchAutoStardCode
     * @Desrciption  自动匹配：根据药品本位码，两个集合组成一个新的集合
     *
     * @Param
     *
     * @Author fuhui
     * @Date   2021/7/19 8:52
     * @Return
     **/
    private void matchAutoStardCode(List<BaseDrugDTO> baseDrugDTOList, List<InsureItemDTO> insureItemDTOList, List<InsureItemMatchDTO> insureItemMatchDTOList, String userId, String userName) {
        insureItemDTOList = insureItemDTOList.stream().filter(insureItemDTO -> !"".equals(insureItemDTO.getDrugadmStrdcode())).collect(Collectors.toList());
        Map<String, InsureItemDTO> collect = insureItemDTOList.stream().collect(Collectors.toMap(InsureItemDTO::getDrugadmStrdcode, Function.identity(), (v1, v2) -> v1));
        InsureItemMatchDTO insureItemMatchDTO;
        for (BaseDrugDTO baseDrugDTO : baseDrugDTOList) {
            if (!collect.isEmpty() && collect.containsKey(baseDrugDTO.getInsureCode()) && !StringUtils.isEmpty(baseDrugDTO.getInsureCode())) {
                insureItemMatchDTO = new InsureItemMatchDTO();
                insureItemMatchDTO.setId(SnowflakeUtils.getId());
                insureItemMatchDTO.setHospCode(baseDrugDTO.getHospCode()); //
                insureItemMatchDTO.setInsureRegCode(collect.get(baseDrugDTO.getInsureCode()).getInsureRegCode());
                insureItemMatchDTO.setItemCode(collect.get(baseDrugDTO.getInsureCode()).getItemMark()); // 项目类别标志
                insureItemMatchDTO.setMolssItemId(null); // 人社部药品id
                insureItemMatchDTO.setPqccItemId(null); // 卫计委药品id
                if (StringUtils.isEmpty(baseDrugDTO.getGoodName())) {
                    insureItemMatchDTO.setHospItemName(baseDrugDTO.getUsualName()); //医院项目名称
                } else {
                    insureItemMatchDTO.setHospItemName(baseDrugDTO.getGoodName()); //医院项目名称
                }
                insureItemMatchDTO.setHospItemId(baseDrugDTO.getId()); // 医院项目id
                insureItemMatchDTO.setHospItemCode(baseDrugDTO.getCode()); // 医院项目编码
                insureItemMatchDTO.setHospItemType(Constants.XMLB.YP); // 医院项目类别
                insureItemMatchDTO.setHospItemSpec(baseDrugDTO.getSpec()); //  医院项目规格
                insureItemMatchDTO.setHospItemUnitCode(baseDrugDTO.getUnitCode()); // 医院项目单位
                insureItemMatchDTO.setHospItemPrepCode(baseDrugDTO.getPrepCode()); //医院项目剂型
                insureItemMatchDTO.setHospItemPrice(baseDrugDTO.getPrice()); //医院项目价格
                insureItemMatchDTO.setLmtUserFlag(collect.get(baseDrugDTO.getInsureCode()).getLmtUsedFlag()); // 目录限制使用标志
                insureItemMatchDTO.setInsureItemName(collect.get(baseDrugDTO.getInsureCode()).getItemName()); // 医保中心项目名称
                insureItemMatchDTO.setInsureItemCode(collect.get(baseDrugDTO.getInsureCode()).getItemCode()); // 医保中心项目编码
                insureItemMatchDTO.setInsureItemType(collect.get(baseDrugDTO.getInsureCode()).getItemType()); // 医保中心项目类别
                insureItemMatchDTO.setInsureItemSpec(collect.get(baseDrugDTO.getInsureCode()).getItemSpec()); // 医保中心项目规格
                insureItemMatchDTO.setInsureItemUnitCode(collect.get(baseDrugDTO.getInsureCode()).getItemUnitCode()); //医保中心项目单位
                insureItemMatchDTO.setInsureItemPrepCode(collect.get(baseDrugDTO.getInsureCode()).getItemDosage()); //医保中心项目剂型
                insureItemMatchDTO.setInsureItemPrice(null); // 医保中心项目价格
                insureItemMatchDTO.setDeductible(collect.get(baseDrugDTO.getInsureCode()).getDeductible()); // 自费比例
                insureItemMatchDTO.setStandardCode(baseDrugDTO.getInsureCode()); // 本位码
                insureItemMatchDTO.setCheckPrice(collect.get(baseDrugDTO.getNationCode()).getCheckPrice()); // 限价
                insureItemMatchDTO.setManufacturer(baseDrugDTO.getProdName()); // 生产厂家
                insureItemMatchDTO.setAuditCode(Constants.SF.F); // 审核状态代码
                insureItemMatchDTO.setIsMatch(Constants.SF.S); // 是否匹配
                insureItemMatchDTO.setIsTrans(Constants.SF.F); // 是否传输
                insureItemMatchDTO.setIsValid(baseDrugDTO.getIsValid()); // 是否有效
                insureItemMatchDTO.setTakeDate(collect.get(baseDrugDTO.getInsureCode()).getTakeDate()); // 生效日期
                insureItemMatchDTO.setLoseDate(collect.get(baseDrugDTO.getInsureCode()).getLoseDate()); // 失效日期
                insureItemMatchDTO.setPym(baseDrugDTO.getUsualPym()); // 拼音码
                insureItemMatchDTO.setWbm(baseDrugDTO.getUsualWbm()); // 五笔码
                insureItemMatchDTO.setCrteId(userId); // 创建人ID
                insureItemMatchDTO.setCrteTime(DateUtils.getNow()); // 创建人姓名
                insureItemMatchDTO.setCrteName(userName); // 创建时间
                insureItemMatchDTOList.add(insureItemMatchDTO);
            }
        }
    }

    /**
     * @Method handMaterialAutoMatch
     * @Desrciption 处理材料自动匹配
     * @Param baseDrugDTOList:医院端的材料集合
     * insureItemDTOList：从医保下载回来的材料集合
     * map：封装参数信息医院编码，医疗机构编码 创建人，id，时间
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
                    insureItemMatchDTO.setItemCode(collect.get(baseMaterialDTO.getName()).getItemMark()); // 项目类别标志  --- 长沙市医保 传费用类别
                    insureItemMatchDTO.setMolssItemId(null); // 人社部药品id
                    insureItemMatchDTO.setPqccItemId(null); // 卫计委药品id
                    insureItemMatchDTO.setHospItemName(baseMaterialDTO.getName()); //医院项目名称
                    insureItemMatchDTO.setHospItemId(baseMaterialDTO.getId()); // 医院项目id
                    insureItemMatchDTO.setHospItemCode(baseMaterialDTO.getCode()); // 医院项目编码
                    insureItemMatchDTO.setHospItemType(Constants.XMLB.CL); // 医院项目类别
                    insureItemMatchDTO.setHospItemSpec(baseMaterialDTO.getSpec()); //  医院项目规格
                    insureItemMatchDTO.setHospItemUnitCode(baseMaterialDTO.getUnitCode()); // 医院项目单位
                    insureItemMatchDTO.setHospItemPrepCode(null); //医院项目剂型
                    insureItemMatchDTO.setHospItemPrice(baseMaterialDTO.getPrice()); //医院项目价格
                    insureItemMatchDTO.setLmtUserFlag(collect.get(baseMaterialDTO.getName()).getLmtUsedFlag()); // 目录限制使用标志
                    insureItemMatchDTO.setInsureItemName(collect.get(baseMaterialDTO.getName()).getItemName()); // 医保中心项目名称
                    insureItemMatchDTO.setInsureItemCode(collect.get(baseMaterialDTO.getName()).getItemCode()); // 医保中心项目编码
                    insureItemMatchDTO.setInsureItemType(collect.get(baseMaterialDTO.getName()).getItemType()); // 医保中心项目类别
                    insureItemMatchDTO.setInsureItemSpec(collect.get(baseMaterialDTO.getName()).getItemSpec()); // 医保中心项目规格
                    insureItemMatchDTO.setInsureItemUnitCode(collect.get(baseMaterialDTO.getName()).getItemUnitCode()); //医保中心项目单位
                    insureItemMatchDTO.setInsureItemPrepCode(collect.get(baseMaterialDTO.getName()).getItemDosage()); //医保中心项目剂型
                    insureItemMatchDTO.setInsureItemPrice(null); // 医保中心项目价格
                    insureItemMatchDTO.setDeductible(collect.get(baseMaterialDTO.getName()).getDeductible()); // 自费比例
                    insureItemMatchDTO.setStandardCode(null); // 本位码
                    insureItemMatchDTO.setCheckPrice(collect.get(baseMaterialDTO.getName()).getCheckPrice()); // 限价
                    insureItemMatchDTO.setManufacturer(null); // 生产厂家
                    insureItemMatchDTO.setAuditCode(Constants.SF.F); // 审核状态代码
                    insureItemMatchDTO.setIsMatch(Constants.SF.S); // 是否匹配
                    insureItemMatchDTO.setIsTrans(Constants.SF.F); // 是否传输
                    insureItemMatchDTO.setIsValid(baseMaterialDTO.getIsValid()); // 是否有效
                    insureItemMatchDTO.setTakeDate(collect.get(baseMaterialDTO.getName()).getTakeDate()); // 生效日期
                    insureItemMatchDTO.setLoseDate(collect.get(baseMaterialDTO.getName()).getLoseDate()); // 失效日期
                    insureItemMatchDTO.setPym(baseMaterialDTO.getPym()); // 拼音码
                    insureItemMatchDTO.setWbm(baseMaterialDTO.getWbm()); // 五笔码
                    insureItemMatchDTO.setCrteId(userId); // 创建人ID
                    insureItemMatchDTO.setCrteTime(DateUtils.getNow()); // 创建人姓名
                    insureItemMatchDTO.setCrteName(userName); // 创建时间
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
                    insureItemMatchDTO.setItemCode(collect.get(baseMaterialDTO.getNationCode()).getItemMark()); // 项目类别标志  --- 长沙市医保 传费用类别
                    insureItemMatchDTO.setMolssItemId(null); // 人社部药品id
                    insureItemMatchDTO.setPqccItemId(null); // 卫计委药品id
                    insureItemMatchDTO.setHospItemName(baseMaterialDTO.getName()); //医院项目名称
                    insureItemMatchDTO.setHospItemId(baseMaterialDTO.getId()); // 医院项目id
                    insureItemMatchDTO.setHospItemCode(baseMaterialDTO.getCode()); // 医院项目编码
                    insureItemMatchDTO.setHospItemType(Constants.XMLB.CL); // 医院项目类别
                    insureItemMatchDTO.setHospItemSpec(baseMaterialDTO.getSpec()); //  医院项目规格
                    insureItemMatchDTO.setHospItemUnitCode(baseMaterialDTO.getUnitCode()); // 医院项目单位
                    insureItemMatchDTO.setHospItemPrepCode(null); //医院项目剂型
                    insureItemMatchDTO.setHospItemPrice(baseMaterialDTO.getPrice()); //医院项目价格
                    insureItemMatchDTO.setLmtUserFlag(collect.get(baseMaterialDTO.getNationCode()).getLmtUsedFlag());  // 目录限制使用标志
                    insureItemMatchDTO.setInsureItemName(collect.get(baseMaterialDTO.getNationCode()).getItemName()); // 医保中心项目名称
                    insureItemMatchDTO.setInsureItemCode(collect.get(baseMaterialDTO.getNationCode()).getItemCode()); // 医保中心项目编码
                    insureItemMatchDTO.setInsureItemType(collect.get(baseMaterialDTO.getNationCode()).getItemType()); // 医保中心项目类别
                    insureItemMatchDTO.setInsureItemSpec(collect.get(baseMaterialDTO.getNationCode()).getItemSpec()); // 医保中心项目规格
                    insureItemMatchDTO.setInsureItemUnitCode(collect.get(baseMaterialDTO.getNationCode()).getItemUnitCode()); //医保中心项目单位
                    insureItemMatchDTO.setInsureItemPrepCode(collect.get(baseMaterialDTO.getNationCode()).getItemDosage()); //医保中心项目剂型
                    insureItemMatchDTO.setInsureItemPrice(null); // 医保中心项目价格
                    insureItemMatchDTO.setDeductible(collect.get(baseMaterialDTO.getNationCode()).getDeductible()); // 自费比例
                    insureItemMatchDTO.setStandardCode(null); // 本位码
                    insureItemMatchDTO.setCheckPrice(collect.get(baseMaterialDTO.getNationCode()).getCheckPrice()); // 限价
                    insureItemMatchDTO.setManufacturer(null); // 生产厂家
                    insureItemMatchDTO.setAuditCode(Constants.SF.F); // 审核状态代码
                    insureItemMatchDTO.setIsMatch(Constants.SF.S); // 是否匹配
                    insureItemMatchDTO.setIsTrans(Constants.SF.F); // 是否传输
                    insureItemMatchDTO.setIsValid(baseMaterialDTO.getIsValid()); // 是否有效
                    insureItemMatchDTO.setTakeDate(collect.get(baseMaterialDTO.getNationCode()).getTakeDate()); // 生效日期
                    insureItemMatchDTO.setLoseDate(collect.get(baseMaterialDTO.getNationCode()).getLoseDate()); // 失效日期
                    insureItemMatchDTO.setPym(baseMaterialDTO.getPym()); // 拼音码
                    insureItemMatchDTO.setWbm(baseMaterialDTO.getWbm()); // 五笔码
                    insureItemMatchDTO.setCrteId(userId); // 创建人ID
                    insureItemMatchDTO.setCrteTime(DateUtils.getNow()); // 创建人姓名
                    insureItemMatchDTO.setCrteName(userName); // 创建时间
                    insureItemMatchDTOList.add(insureItemMatchDTO);
                }
            }
        }
        itemMatchDTO.setIsMatch(Constants.SF.S);
        itemMatchDTO.setHospItemType(Constants.XMLB.CL);
        /**
         *      1.查询已经匹配的药品信息
         *      2.如果匹配药品为空，则直接跳出不做判断，返回上一步 匹配好的数据
         *      3.如果不为空，则需要把刚刚自动匹配好的数据和以前匹配的数据做对比，防止重复插入数据
         */
        List<InsureItemMatchDTO> matchDTOList = insureItemMatchDAO.queryPageOrAll(itemMatchDTO);
        if (!ListUtils.isEmpty(matchDTOList)) {
            List<InsureItemMatchDTO> dtoList = new ArrayList<>();
            Map<String, InsureItemMatchDTO> drugMatchDTOMap = matchDTOList.stream().collect(Collectors.toMap(InsureItemMatchDTO::getHospItemCode, Function.identity()));
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
     * @Desrciption 处理项目自动匹配
     * @Param baseDrugDTOList:医院端的药品集合
     * insureItemDTOList：从医保下载回来的项目集合
     * map：封装参数信息医院编码，医疗机构编码 创建人，id，时间
     * @Author fuhui
     * @Date 2021/1/27 21:12
     * @Return
     **/
    private List<InsureItemMatchDTO> handItemAutoMatch(List<BaseItemDTO> baseItemDTOList, List<InsureItemDTO> insureItemDTOList, Map map) {
        InsureItemMatchDTO itemMatchDTO = MapUtils.get(map, "itemMatchDTO");
        String userId = itemMatchDTO.getCrteId();
        String userName = itemMatchDTO.getCrteName();
        String matchType = itemMatchDTO.getMatchType();
        List<InsureItemMatchDTO> insureItemMatchDTOList = new ArrayList<>();
        Map<String, InsureItemDTO> collect = null;
        if (Constant.UnifiedPay.MATCHCODE.MC.equals(matchType)) {
            collect = insureItemDTOList.stream().collect(Collectors.toMap(InsureItemDTO::getItemName, Function.identity(), (k1, k2) -> k1));
            InsureItemMatchDTO insureItemMatchDTO = null;
            for (BaseItemDTO baseItemDTO : baseItemDTOList) {
                if (!collect.isEmpty() && collect.containsKey(baseItemDTO.getName())) {
                    insureItemMatchDTO = new InsureItemMatchDTO();
                    insureItemMatchDTO.setId(SnowflakeUtils.getId());
                    insureItemMatchDTO.setHospCode(baseItemDTO.getHospCode()); //
                    insureItemMatchDTO.setInsureRegCode(collect.get(baseItemDTO.getName()).getInsureRegCode());
                    insureItemMatchDTO.setItemCode(collect.get(baseItemDTO.getName()).getItemMark()); // 项目类别标志
                    insureItemMatchDTO.setMolssItemId(null); // 人社部药品id
                    insureItemMatchDTO.setPqccItemId(null); // 卫计委药品id
                    insureItemMatchDTO.setHospItemName(baseItemDTO.getName()); //医院项目名称
                    insureItemMatchDTO.setHospItemId(baseItemDTO.getId()); // 医院项目id
                    insureItemMatchDTO.setHospItemCode(baseItemDTO.getCode()); // 医院项目编码
                    insureItemMatchDTO.setHospItemType(Constants.XMLB.XM); // 医院项目类别
                    insureItemMatchDTO.setHospItemSpec(baseItemDTO.getSpec()); //  医院项目规格
                    insureItemMatchDTO.setHospItemUnitCode(baseItemDTO.getUnitCode()); // 医院项目单位
                    insureItemMatchDTO.setHospItemPrepCode(null); //医院项目剂型
                    insureItemMatchDTO.setHospItemPrice(baseItemDTO.getPrice()); //医院项目价格
                    insureItemMatchDTO.setInsureItemName(collect.get(baseItemDTO.getName()).getItemName()); // 医保中心项目名称
                    insureItemMatchDTO.setInsureItemCode(collect.get(baseItemDTO.getName()).getItemCode()); // 医保中心项目编码
                    insureItemMatchDTO.setInsureItemType(collect.get(baseItemDTO.getName()).getItemType()); // 医保中心项目类别
                    insureItemMatchDTO.setInsureItemSpec(collect.get(baseItemDTO.getName()).getItemSpec()); // 医保中心项目规格
                    insureItemMatchDTO.setInsureItemUnitCode(collect.get(baseItemDTO.getName()).getItemUnitCode()); //医保中心项目单位
                    insureItemMatchDTO.setInsureItemPrepCode(collect.get(baseItemDTO.getName()).getItemDosage()); //医保中心项目剂型
                    insureItemMatchDTO.setInsureItemPrice(null); // 医保中心项目价格
                    insureItemMatchDTO.setDeductible(collect.get(baseItemDTO.getName()).getDeductible()); // 自费比例
                    insureItemMatchDTO.setStandardCode(null); // 本位码
                    insureItemMatchDTO.setCheckPrice(collect.get(baseItemDTO.getName()).getCheckPrice()); // 限价
                    insureItemMatchDTO.setManufacturer(null); // 生产厂家
                    insureItemMatchDTO.setAuditCode(Constants.SF.F); // 审核状态代码
                    insureItemMatchDTO.setIsMatch(Constants.SF.S); // 是否匹配
                    insureItemMatchDTO.setIsTrans(Constants.SF.F); // 是否传输
                    insureItemMatchDTO.setIsValid(baseItemDTO.getIsValid()); // 是否有效
                    insureItemMatchDTO.setTakeDate(collect.get(baseItemDTO.getName()).getTakeDate()); // 生效日期
                    insureItemMatchDTO.setLoseDate(collect.get(baseItemDTO.getName()).getLoseDate()); // 失效日期
                    insureItemMatchDTO.setPym(baseItemDTO.getNamePym()); // 拼音码
                    insureItemMatchDTO.setWbm(baseItemDTO.getNameWbm()); // 五笔码
                    insureItemMatchDTO.setCrteId(userId); // 创建人ID
                    insureItemMatchDTO.setCrteTime(DateUtils.getNow()); // 创建人姓名
                    insureItemMatchDTO.setCrteName(userName); // 创建时间
                    insureItemMatchDTOList.add(insureItemMatchDTO);
                }
            }
        } else {
            collect = insureItemDTOList.stream().collect(Collectors.toMap(InsureItemDTO::getItemCode, Function.identity()));
            InsureItemMatchDTO insureItemMatchDTO = null;
            for (BaseItemDTO baseItemDTO : baseItemDTOList) {
                if (!collect.isEmpty() && collect.containsKey(baseItemDTO.getNationCode()) && !StringUtils.isEmpty(baseItemDTO.getNationCode())) {
                    insureItemMatchDTO = new InsureItemMatchDTO();
                    insureItemMatchDTO.setId(SnowflakeUtils.getId());
                    insureItemMatchDTO.setHospCode(baseItemDTO.getHospCode()); //
                    insureItemMatchDTO.setInsureRegCode(collect.get(baseItemDTO.getNationCode()).getInsureRegCode());
                    insureItemMatchDTO.setItemCode(collect.get(baseItemDTO.getNationCode()).getItemMark()); // 项目类别标志
                    insureItemMatchDTO.setMolssItemId(null); // 人社部药品id
                    insureItemMatchDTO.setPqccItemId(null); // 卫计委药品id
                    insureItemMatchDTO.setHospItemName(baseItemDTO.getName()); //医院项目名称
                    insureItemMatchDTO.setHospItemId(baseItemDTO.getId()); // 医院项目id
                    insureItemMatchDTO.setHospItemCode(baseItemDTO.getCode()); // 医院项目编码
                    insureItemMatchDTO.setHospItemType(Constants.XMLB.XM); // 医院项目类别
                    insureItemMatchDTO.setHospItemSpec(baseItemDTO.getSpec()); //  医院项目规格
                    insureItemMatchDTO.setHospItemUnitCode(baseItemDTO.getUnitCode()); // 医院项目单位
                    insureItemMatchDTO.setHospItemPrepCode(null); //医院项目剂型
                    insureItemMatchDTO.setHospItemPrice(baseItemDTO.getPrice()); //医院项目价格
                    insureItemMatchDTO.setInsureItemName(collect.get(baseItemDTO.getNationCode()).getItemName()); // 医保中心项目名称
                    insureItemMatchDTO.setInsureItemCode(collect.get(baseItemDTO.getNationCode()).getItemCode()); // 医保中心项目编码
                    insureItemMatchDTO.setInsureItemType(collect.get(baseItemDTO.getNationCode()).getItemType()); // 医保中心项目类别
                    insureItemMatchDTO.setInsureItemSpec(collect.get(baseItemDTO.getNationCode()).getItemSpec()); // 医保中心项目规格
                    insureItemMatchDTO.setInsureItemUnitCode(collect.get(baseItemDTO.getNationCode()).getItemUnitCode()); //医保中心项目单位
                    insureItemMatchDTO.setInsureItemPrepCode(collect.get(baseItemDTO.getNationCode()).getItemDosage()); //医保中心项目剂型
                    insureItemMatchDTO.setInsureItemPrice(null); // 医保中心项目价格
                    insureItemMatchDTO.setDeductible(collect.get(baseItemDTO.getNationCode()).getDeductible()); // 自费比例
                    insureItemMatchDTO.setStandardCode(null); // 本位码
                    insureItemMatchDTO.setCheckPrice(collect.get(baseItemDTO.getNationCode()).getCheckPrice()); // 限价
                    insureItemMatchDTO.setManufacturer(null); // 生产厂家
                    insureItemMatchDTO.setAuditCode(Constants.SF.F); // 审核状态代码
                    insureItemMatchDTO.setIsMatch(Constants.SF.S); // 是否匹配
                    insureItemMatchDTO.setIsTrans(Constants.SF.F); // 是否传输
                    insureItemMatchDTO.setIsValid(baseItemDTO.getIsValid()); // 是否有效
                    insureItemMatchDTO.setTakeDate(collect.get(baseItemDTO.getNationCode()).getTakeDate()); // 生效日期
                    insureItemMatchDTO.setLoseDate(collect.get(baseItemDTO.getNationCode()).getLoseDate()); // 失效日期
                    insureItemMatchDTO.setPym(baseItemDTO.getNamePym()); // 拼音码
                    insureItemMatchDTO.setWbm(baseItemDTO.getNameWbm()); // 五笔码
                    insureItemMatchDTO.setCrteId(userId); // 创建人ID
                    insureItemMatchDTO.setCrteTime(DateUtils.getNow()); // 创建人姓名
                    insureItemMatchDTO.setCrteName(userName); // 创建时间
                    insureItemMatchDTOList.add(insureItemMatchDTO);
                }
            }
        }
        itemMatchDTO.setIsMatch(Constants.SF.S);
        itemMatchDTO.setHospItemType(Constants.XMLB.XM);
        /**
         *      1.查询已经匹配的药品信息
         *      2.如果匹配药品为空，则直接跳出不做判断，返回上一步 匹配好的数据
         *      3.如果不为空，则需要把刚刚自动匹配好的数据和以前匹配的数据做对比，防止重复插入数据
         */
        List<InsureItemMatchDTO> matchDTOList = insureItemMatchDAO.queryPageOrAll(itemMatchDTO);
        if (!ListUtils.isEmpty(matchDTOList)) {
            List<InsureItemMatchDTO> dtoList = new ArrayList<>();
            Map<String, InsureItemMatchDTO> drugMatchDTOMap = matchDTOList.stream().collect(Collectors.toMap(InsureItemMatchDTO::getHospItemCode, Function.identity()));
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
     * @Method handDrugAutoMatch
     * @Desrciption 处理药品自动匹配
     * @Param baseDrugDTOList:医院端的药品集合
     * insureItemDTOList：从医保下载回来的药品集合
     * map：封装参数信息医院编码，医疗机构编码 创建人，id，时间
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
                    insureItemMatchDTO.setItemCode(collect.get(baseDrugDTO.getUsualName()).getItemMark()); // 项目类别标志
                    insureItemMatchDTO.setMolssItemId(null); // 人社部药品id
                    insureItemMatchDTO.setPqccItemId(null); // 卫计委药品id
                    if (StringUtils.isEmpty(baseDrugDTO.getGoodName())) {
                        insureItemMatchDTO.setHospItemName(baseDrugDTO.getUsualName()); //医院项目名称
                    } else {
                        insureItemMatchDTO.setHospItemName(baseDrugDTO.getGoodName()); //医院项目名称
                    }
                    insureItemMatchDTO.setHospItemId(baseDrugDTO.getId()); // 医院项目id
                    insureItemMatchDTO.setHospItemCode(baseDrugDTO.getCode()); // 医院项目编码
                    insureItemMatchDTO.setHospItemType(Constants.XMLB.YP); // 医院项目类别
                    insureItemMatchDTO.setHospItemSpec(baseDrugDTO.getSpec()); //  医院项目规格
                    insureItemMatchDTO.setHospItemUnitCode(baseDrugDTO.getUnitCode()); // 医院项目单位
                    insureItemMatchDTO.setHospItemPrepCode(baseDrugDTO.getPrepCode()); //医院项目剂型
                    insureItemMatchDTO.setHospItemPrice(baseDrugDTO.getPrice()); //医院项目价格
                    insureItemMatchDTO.setLmtUserFlag(collect.get(baseDrugDTO.getUsualName()).getLmtUsedFlag()); // 目录限制使用标志
                    insureItemMatchDTO.setInsureItemName(collect.get(baseDrugDTO.getUsualName()).getItemName()); // 医保中心项目名称
                    insureItemMatchDTO.setInsureItemCode(collect.get(baseDrugDTO.getUsualName()).getItemCode()); // 医保中心项目编码
                    insureItemMatchDTO.setInsureItemType(collect.get(baseDrugDTO.getUsualName()).getItemType()); // 医保中心项目类别
                    insureItemMatchDTO.setInsureItemSpec(collect.get(baseDrugDTO.getUsualName()).getItemSpec()); // 医保中心项目规格
                    insureItemMatchDTO.setInsureItemUnitCode(collect.get(baseDrugDTO.getUsualName()).getItemUnitCode()); //医保中心项目单位
                    insureItemMatchDTO.setInsureItemPrepCode(collect.get(baseDrugDTO.getUsualName()).getItemDosage()); //医保中心项目剂型
                    insureItemMatchDTO.setInsureItemPrice(null); // 医保中心项目价格
                    insureItemMatchDTO.setDeductible(collect.get(baseDrugDTO.getUsualName()).getDeductible()); // 自费比例
                    insureItemMatchDTO.setStandardCode(baseDrugDTO.getInsureRegCode()); // 本位码
                    insureItemMatchDTO.setCheckPrice(collect.get(baseDrugDTO.getUsualName()).getCheckPrice()); // 限价
                    insureItemMatchDTO.setManufacturer(baseDrugDTO.getProdName()); // 生产厂家
                    insureItemMatchDTO.setAuditCode(Constants.SF.F); // 审核状态代码
                    insureItemMatchDTO.setIsMatch(Constants.SF.S); // 是否匹配
                    insureItemMatchDTO.setIsTrans(Constants.SF.F); // 是否传输
                    insureItemMatchDTO.setIsValid(baseDrugDTO.getIsValid()); // 是否有效
                    insureItemMatchDTO.setTakeDate(collect.get(baseDrugDTO.getUsualName()).getTakeDate()); // 生效日期
                    insureItemMatchDTO.setLoseDate(collect.get(baseDrugDTO.getUsualName()).getLoseDate()); // 失效日期
                    insureItemMatchDTO.setPym(baseDrugDTO.getUsualPym()); // 拼音码
                    insureItemMatchDTO.setWbm(baseDrugDTO.getUsualWbm()); // 五笔码
                    insureItemMatchDTO.setCrteId(userId); // 创建人ID
                    insureItemMatchDTO.setCrteTime(DateUtils.getNow()); // 创建人姓名
                    insureItemMatchDTO.setCrteName(userName); // 创建时间
                    insureItemMatchDTOList.add(insureItemMatchDTO);
                }
            }
        }
        else if(Constant.UnifiedPay.MATCHCODE.GJBM.equals(matchType)) {
            collect = insureItemDTOList.stream().collect(Collectors.toMap(InsureItemDTO::getItemCode, Function.identity(), (v1, v2) -> v1));
            for (BaseDrugDTO baseDrugDTO : baseDrugDTOList) {
                if (!collect.isEmpty() && collect.containsKey(baseDrugDTO.getNationCode()) && !StringUtils.isEmpty(baseDrugDTO.getNationCode())) {
                    InsureItemMatchDTO insureItemMatchDTO = new InsureItemMatchDTO();
                    insureItemMatchDTO.setId(SnowflakeUtils.getId());
                    insureItemMatchDTO.setHospCode(baseDrugDTO.getHospCode()); //
                    insureItemMatchDTO.setInsureRegCode(collect.get(baseDrugDTO.getNationCode()).getInsureRegCode());
                    insureItemMatchDTO.setItemCode(collect.get(baseDrugDTO.getNationCode()).getItemMark()); // 项目类别标志
                    insureItemMatchDTO.setMolssItemId(null); // 人社部药品id
                    insureItemMatchDTO.setPqccItemId(null); // 卫计委药品id
                    if (StringUtils.isEmpty(baseDrugDTO.getGoodName())) {
                        insureItemMatchDTO.setHospItemName(baseDrugDTO.getUsualName()); //医院项目名称
                    } else {
                        insureItemMatchDTO.setHospItemName(baseDrugDTO.getGoodName()); //医院项目名称
                    }
                    insureItemMatchDTO.setHospItemId(baseDrugDTO.getId()); // 医院项目id
                    insureItemMatchDTO.setHospItemCode(baseDrugDTO.getCode()); // 医院项目编码
                    insureItemMatchDTO.setHospItemType(Constants.XMLB.YP); // 医院项目类别
                    insureItemMatchDTO.setHospItemSpec(baseDrugDTO.getSpec()); //  医院项目规格
                    insureItemMatchDTO.setHospItemUnitCode(baseDrugDTO.getUnitCode()); // 医院项目单位
                    insureItemMatchDTO.setHospItemPrepCode(baseDrugDTO.getPrepCode()); //医院项目剂型
                    insureItemMatchDTO.setHospItemPrice(baseDrugDTO.getPrice()); //医院项目价格
                    insureItemMatchDTO.setLmtUserFlag(collect.get(baseDrugDTO.getNationCode()).getLmtUsedFlag()); // 目录限制使用标志
                    insureItemMatchDTO.setInsureItemName(collect.get(baseDrugDTO.getNationCode()).getItemName()); // 医保中心项目名称
                    insureItemMatchDTO.setInsureItemCode(collect.get(baseDrugDTO.getNationCode()).getItemCode()); // 医保中心项目编码
                    insureItemMatchDTO.setInsureItemType(collect.get(baseDrugDTO.getNationCode()).getItemType()); // 医保中心项目类别
                    insureItemMatchDTO.setInsureItemSpec(collect.get(baseDrugDTO.getNationCode()).getItemSpec()); // 医保中心项目规格
                    insureItemMatchDTO.setInsureItemUnitCode(collect.get(baseDrugDTO.getNationCode()).getItemUnitCode()); //医保中心项目单位
                    insureItemMatchDTO.setInsureItemPrepCode(collect.get(baseDrugDTO.getNationCode()).getItemDosage()); //医保中心项目剂型
                    insureItemMatchDTO.setInsureItemPrice(null); // 医保中心项目价格
                    insureItemMatchDTO.setDeductible(collect.get(baseDrugDTO.getNationCode()).getDeductible()); // 自费比例
                    insureItemMatchDTO.setStandardCode(baseDrugDTO.getInsureRegCode()); // 本位码
                    insureItemMatchDTO.setCheckPrice(collect.get(baseDrugDTO.getNationCode()).getCheckPrice()); // 限价
                    insureItemMatchDTO.setManufacturer(baseDrugDTO.getProdName()); // 生产厂家
                    insureItemMatchDTO.setAuditCode(Constants.SF.F); // 审核状态代码
                    insureItemMatchDTO.setIsMatch(Constants.SF.S); // 是否匹配
                    insureItemMatchDTO.setIsTrans(Constants.SF.F); // 是否传输
                    insureItemMatchDTO.setIsValid(baseDrugDTO.getIsValid()); // 是否有效
                    insureItemMatchDTO.setTakeDate(collect.get(baseDrugDTO.getNationCode()).getTakeDate()); // 生效日期
                    insureItemMatchDTO.setLoseDate(collect.get(baseDrugDTO.getNationCode()).getLoseDate()); // 失效日期
                    insureItemMatchDTO.setPym(baseDrugDTO.getUsualPym()); // 拼音码
                    insureItemMatchDTO.setWbm(baseDrugDTO.getUsualWbm()); // 五笔码
                    insureItemMatchDTO.setCrteId(userId); // 创建人ID
                    insureItemMatchDTO.setCrteTime(DateUtils.getNow()); // 创建人姓名
                    insureItemMatchDTO.setCrteName(userName); // 创建时间
                    insureItemMatchDTOList.add(insureItemMatchDTO);
                }
            }
        }
        else{
           matchAutoStardCode(baseDrugDTOList, insureItemDTOList,insureItemMatchDTOList,userId,userName);
        }
        itemMatchDTO.setIsMatch(Constants.SF.S);
        itemMatchDTO.setHospItemType(Constants.XMLB.YP);
        /**
         *      1.查询已经匹配的药品信息
         *      2.如果匹配药品为空，则直接跳出不做判断，返回上一步 匹配好的数据
         *      3.如果不为空，则需要把刚刚自动匹配好的数据和以前匹配的数据做对比，防止重复插入数据
         */
        List<InsureItemMatchDTO> matchDTOList = insureItemMatchDAO.queryPageOrAll(itemMatchDTO);
        if (!ListUtils.isEmpty(matchDTOList)) {
            List<InsureItemMatchDTO> dtoList = new ArrayList<>();
            Map<String, InsureItemMatchDTO> drugMatchDTOMap = matchDTOList.stream().collect(Collectors.toMap(InsureItemMatchDTO::getHospItemCode, Function.identity()));
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
     * @Desrciption 医保统一支付：药品类型的手动匹配
     * @Param
     * @Author fuhui
     * @Date 2021/4/10 2:18
     * @Return
     **/
    private void insertHandlerDrug(InsureItemDTO insureItemDTO, BaseDrugDTO baseDrugDTO, InsureItemMatchDTO itemMatchDTO) {

        List<InsureItemMatchDTO> list = insureItemMatchDAO.selectById(itemMatchDTO);
        if (!ListUtils.isEmpty(list)) {
            throw new AppException("该【" + list.get(0).getHospItemName() + "】已经匹配");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id", SnowflakeUtils.getId());
        map.put("hospCode", itemMatchDTO.getHospCode());
        map.put("insureRegCode", itemMatchDTO.getInsureRegCode());
        if ("0".equals(baseDrugDTO.getBigTypeCode())) {
            map.put("itemCode", "101"); // 项目类别标志
        } else if ("1".equals(baseDrugDTO.getBigTypeCode())) {
            map.put("itemCode", "102"); // 项目类别标志
        } else {
            map.put("itemCode", "103"); // 项目类别标志
        }
        map.put("hospItemId", baseDrugDTO.getId()); // 医院项目id
        if(StringUtils.isEmpty(baseDrugDTO.getUsualName())){
            map.put("hospItemName", baseDrugDTO.getGoodName()); // 医院项目名称
        }else{
            map.put("hospItemName", baseDrugDTO.getUsualName()); // 医院项目名称
        }
        map.put("hospItemCode", baseDrugDTO.getCode()); // 医院项目编码
        map.put("hospItemType", Constants.XMLB.YP); // 医院项目类别
        map.put("hospItemSpec", baseDrugDTO.getSpec()); // 医院项目规格
        map.put("hospItemUnitCode", baseDrugDTO.getUnitCode()); // 医院项目单位
        map.put("hospItemUnitCode", baseDrugDTO.getPrepCode()); // 医院项目剂型
        map.put("hospItemPrice", baseDrugDTO.getAvgBuyPrice()); // 医院项目价格
        map.put("insureItemName", insureItemDTO.getItemName()); // 医保中心项目名称
        map.put("insureItemCode", insureItemDTO.getItemCode()); // 医保中心项目编码
        map.put("insureItemType", insureItemDTO.getItemType()); // 医保中心项目类别
        map.put("insureItemSpec", insureItemDTO.getItemSpec()); // 医保中心项目规格
        map.put("insureItemUnitCode", insureItemDTO.getItemUnitCode()); // 医保中心项目单位
        map.put("insureItemPrepCode", insureItemDTO.getItemDosage()); // 医保中心项目剂型
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
     * @Desrciption 医保统一支付：疾病类型的手动匹配
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
            throw new AppException("该【" + insureDiseaseMatchDTOList.get(0).getHospIllnessName() + "】疾病已经匹配");
        }
        insureDiseaseMatchDTO.setId(SnowflakeUtils.getId()); // 主键id
        insureDiseaseMatchDTO.setHospCode(itemMatchDTO.getHospCode()); // 医院编码
        insureDiseaseMatchDTO.setInsureRegCode(itemMatchDTO.getInsureRegCode()); // 医保注册编码
        insureDiseaseMatchDTO.setHospIllnessId(baseDiseaseDTO.getId()); // 医院疾病ID
        insureDiseaseMatchDTO.setHospIllnessName(baseDiseaseDTO.getName()); // 医院ICD编码
        insureDiseaseMatchDTO.setHospIllnessCode(baseDiseaseDTO.getCode()); // 医院ICD名称
        insureDiseaseMatchDTO.setInsureIllnessId(insureDiseaseDTO.getInsureIllnessId()); // 医保中心疾病ID
        insureDiseaseMatchDTO.setInsureIllnessName(insureDiseaseDTO.getInsureIllnessName()); // 医保中心ICD编码
        insureDiseaseMatchDTO.setInsureIllnessCode(insureDiseaseDTO.getInsureIllnessCode()); //医保中心ICD名称
        insureDiseaseMatchDTO.setIsTrans(Constants.SF.S); // 是否传输
        insureDiseaseMatchDTO.setIsMatch(Constants.SF.S); // 是否匹配
        insureDiseaseMatchDTO.setAuditCode(Constants.SF.S); // 审批状态代码
        insureDiseaseMatchDTO.setRemark(insureDiseaseDTO.getRemark()); // 备注
        insureDiseaseMatchDTO.setCrteId(itemMatchDTO.getCrteId()); // 创建人ID
        insureDiseaseMatchDTO.setCrteTime(DateUtils.getNow());
        insureDiseaseMatchDTO.setCrteName(itemMatchDTO.getCrteName());
        insureDiseaseMatchDTO.setTypeCode(insureDiseaseDTO.getDownLoadType());
        insureDiseaseMatchDAO.insertDiseaseMatch(insureDiseaseMatchDTO);
    }

    /**
     * @Method 根据下载类型的版本号：分页下载数据
     * num: 下载第几页       size:下载每页数据量的大小
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
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        map.put("code", "UNIFIED_DOWNLOAD");
        SysParameterDTO data = sysParameterService_consumer.getParameterByCode(map).getData();
        int num = 0;
        int size = 0;
        if (data != null) {
            // 获取系统参数定义的数据量大小   和当前下载的页数
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
        if (Constant.UnifiedPay.JBLIST.containsKey(itemType)) {
            InsureDiseaseDTO insureDiseaseDTO = insureDiseaseDAO.selectLatestVer(map);
            if(insureDiseaseDTO !=null && (insureDiseaseDTO.getRecordCounts() < insureDiseaseDTO.getNum() * insureDiseaseDTO.getSize() ) ){
                throw new AppException("已经是最新数据了");
            }
            if(insureDiseaseDTO !=null){
                num = insureDiseaseDTO.getNum();
                dataMap.put("page_num", ++num);
            }else {
                dataMap.put("page_num", num);
            }
            dataMap.put("page_size", size);
        }
        else {
            InsureItemDTO insureItemDTO = insureItemDAO.selectLatestVer(map);
            if(insureItemDTO !=null && (insureItemDTO.getRecordCounts() < insureItemDTO.getNum() * insureItemDTO.getSize() ) ){
                throw new AppException("已经是最新数据了");
            }
            if(insureItemDTO !=null){
                num = insureItemDTO.getNum();
                dataMap.put("page_num", ++num);
            }else {
                dataMap.put("page_num", num);
            }
            dataMap.put("page_size", size);

        }
        switch (itemType) {
            case "1301":  // 代表西药
                httpParam.put("infno", Constant.UnifiedPay.DOWNLOAD.UP_1301);  //交易编号 西药和中成药下载
                break;
            case "1302": //中药饮片
                httpParam.put("infno", Constant.UnifiedPay.DOWNLOAD.UP_1302);  //中药饮片目录下载
                break;
            case "1303": // 自制剂
                httpParam.put("infno", Constant.UnifiedPay.DOWNLOAD.UP_1303);  //医疗机构制剂目录下载
                break;
            case "1304": // 民族药
                httpParam.put("infno", Constant.UnifiedPay.DOWNLOAD.UP_1304);  //民族药品目录查询
                break;
            case "1305": // 服务项目
                httpParam.put("infno", Constant.UnifiedPay.DOWNLOAD.UP_1305);  //医疗服务项目目录下载
                break;
            case "1306": // 医用材料
                httpParam.put("infno", Constant.UnifiedPay.DOWNLOAD.UP_1306);  //医用耗材目录下载
                break;
            case "1307": // 疾病与诊断目录下载
                httpParam.put("infno", Constant.UnifiedPay.DOWNLOAD.UP_1307);  //医用耗材目录下载
                break;
            case "1308": // 手术操作目录下载
                httpParam.put("infno", Constant.UnifiedPay.DOWNLOAD.UP_1308);  //手术操作目录下载
                break;
            case "1309": // 门诊慢特病种目录下载
                httpParam.put("infno", Constant.UnifiedPay.DOWNLOAD.UP_1309);  //医用耗材目录下载
                break;
            case "1310": // 按病种付费病种目录下载
                httpParam.put("infno", Constant.UnifiedPay.DOWNLOAD.UP_1310);  //医用耗材目录下载
                break;
            case "1311": // 日间手术治疗病种目录下载
                httpParam.put("infno", Constant.UnifiedPay.DOWNLOAD.UP_1311);  //医用耗材目录下载
                break;
            case "1313": // 日间手术治疗病种目录下载
                httpParam.put("infno", Constant.UnifiedPay.DOWNLOAD.UP_1313);  //医用耗材目录下载
                break;
            case "1314": // 中医疾病目录下载
                httpParam.put("infno", Constant.UnifiedPay.DOWNLOAD.UP_1314);  //医用耗材目录下载
                break;
            default:
                httpParam.put("infno", Constant.UnifiedPay.DOWNLOAD.UP_1315);  //交易编号
        }
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("data", dataMap);
        httpParam.put("input", paramMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("统一支付平台下载入参" + itemType + ":" + json);
        String url = insureConfigurationDTO.getUrl();
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(url, json);
        if (StringUtils.isEmpty(resultJson)) {
            throw new AppException("无法访问医保统一支付平台");
        }
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson);
        if ("999".equals(MapUtils.get(resultMap, "code"))) {
            throw new AppException((String) resultMap.get("msg"));
        }
        if (!MapUtils.get(resultMap, "infcode").equals("0")) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        Map<String, Object> outptMap = (Map<String, Object>) resultMap.get("output");
        List<Map<String, Object>> dataResultMap = (List<Map<String, Object>>) outptMap.get("data");
        int recordCounts = MapUtils.get(outptMap, "recordCounts");
        map.put("size", size);
        map.put("num", num);
        map.put("recordCounts", recordCounts);
        if (!ListUtils.isEmpty(dataResultMap)) {
            logger.info("统一支付平台下载回参" + itemType + ":" + new JSONObject(dataResultMap.get(0)));
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
        return resultMap;
    }

    private void insert_1312(List<Map<String, Object>> dataResultMap, Map<String, Object> map) {
    }

    /**
     * @Method insert_1304
     * @Desrciption 医保统一支付平台：名族药品下载
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
                // 主键
                itemDTO.setId(SnowflakeUtils.getId());
                // 医院编码
                itemDTO.setHospCode(hospCode);
                // 医疗机构编码
                itemDTO.setInsureRegCode(insureRegCode);
                // 项目类别标志  --费用类别
                itemDTO.setItemMark("1304");
                //医保中心项目编码
                itemDTO.setItemCode(MapUtils.get(item, "med_list_codg"));
                // 医保中心项目名称
                itemDTO.setItemType("1304");
                // 医保中心项目规格
                itemDTO.setItemSpec(MapUtils.get(item, "drug_spec"));
                itemDTO.setItemName(MapUtils.get(item, "drug_prodname"));
                // 医保中心项目剂型
                itemDTO.setItemDosage(MapUtils.get(item, "dosform"));
                // 医保中心项目价格
                itemDTO.setItemPrice(null);
                // 医保中心项目单位
                itemDTO.setItemUnitCode(MapUtils.get(item, "min_useunt"));
                // 生产厂家
                itemDTO.setProd(MapUtils.get(item, "manufacturer_name"));
                // 自费比例
                itemDTO.setDeductible(null);
                // 限价
                itemDTO.setCheckPrice(null);
                // 医保目录标志（0.甲、1.乙、2.全自费）
                itemDTO.setDirectory(MapUtils.get(item, "null"));
                // 生效日期
                if (!StringUtils.isEmpty(MapUtils.get(item, "begndate")) && !"null".equals(MapUtils.get(item, "begndate"))) {
                    itemDTO.setLoseDate(DateUtils.parse(MapUtils.get(item, "begndate"), DateUtils.Y_M_DH_M_S));
                } else {
                    itemDTO.setTakeDate(null);
                }
                //失效日期
                if (!StringUtils.isEmpty(MapUtils.get(item, "enddate")) && !"null".equals(MapUtils.get(item, "enddate"))) {
                    itemDTO.setLoseDate(DateUtils.parse(MapUtils.get(item, "enddate"), DateUtils.Y_M_DH_M_S));
                } else {
                    itemDTO.setLoseDate(null);
                }
                // 拼音码
                itemDTO.setPym(MapUtils.get(item, "pinyin"));
                // 五笔码
                itemDTO.setWbm(MapUtils.get(item, "wubi"));
                // 是否有效（SF）
                itemDTO.setIsValid(MapUtils.get(item, "vali_flag"));
                // 创建人ID
                itemDTO.setCrteId(crteId);
                // 创建人姓名
                itemDTO.setCrteName(crteName);
                // 创建时间
                itemDTO.setCrteTime(DateUtils.getNow());
                itemDTO.setCompoundLogo(MapUtils.get(item, "compound_logo"));
                itemDTO.setVer(MapUtils.get(item, "ver")); // 版本号
                itemDTO.setVerName(MapUtils.get(item, "ver_name")); // 版本号
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
     * @Desrciption 医保统一支付平台：中医证候目录下载
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
        String ver = MapUtils.get(map,"ver");
        int recordCounts = MapUtils.get(map, "recordCounts");
        InsureDiseaseDTO insureDiseaseDTO = null;
        if (!ListUtils.isEmpty(dataResultMap)) {
            for (Map<String, Object> item : dataResultMap) {
                insureDiseaseDTO = new InsureDiseaseDTO();
                insureDiseaseDTO.setRecordCounts(recordCounts);
                insureDiseaseDTO.setSize(size);
                insureDiseaseDTO.setNum(num);
                insureDiseaseDTO.setId(SnowflakeUtils.getId()); // 主键
                insureDiseaseDTO.setHospCode(hospCode); // 医院编码
                insureDiseaseDTO.setInsureRegCode(insureRegCode); // 医保机构注册编码
                insureDiseaseDTO.setInsureIllnessId(MapUtils.get(item, "tcm_syndrome_id")); // 中心疾病ID
                insureDiseaseDTO.setInsureIllnessCode(MapUtils.get(item, "syndrome_type_code")); // 中心疾病编码
                insureDiseaseDTO.setInsureIllnessName(MapUtils.get(item, "syndrome_type_name")); // 中心疾病名称
                insureDiseaseDTO.setIcd10(MapUtils.get(item, "tcm_syndrome_id")); // ICD编码
                insureDiseaseDTO.setPym(null); // 拼音码
                insureDiseaseDTO.setWbm(null); // 五笔码
                insureDiseaseDTO.setLoseDate(null); //失效日期
                insureDiseaseDTO.setTakeDate(null); // 生效日期
                insureDiseaseDTO.setRemark(MapUtils.get(item, "memo")); // 备注
                insureDiseaseDTO.setCrteId(crteId); // 创建人ID
                insureDiseaseDTO.setCrteName(crteName); // 创建人姓名
                insureDiseaseDTO.setCrteTime(DateUtils.getNow()); //创建时间
                insureDiseaseDTO.setDownLoadType(listType);
                insureDiseaseDTO.setVer(MapUtils.get(item, "ver"));
                if(num > (recordCounts / size)){
                    insureDiseaseDTO.setVerName(MapUtils.get(item, "ver_name"));
                }else{
                    insureDiseaseDTO.setVerName(ver); // 版本号
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
     * @Desrciption 医保统一支付平台：【1314】中医疾病目录下载
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
                insureDiseaseDTO.setId(SnowflakeUtils.getId()); // 主键
                insureDiseaseDTO.setHospCode(hospCode); // 医院编码
                insureDiseaseDTO.setInsureRegCode(insureRegCode); // 医保机构注册编码
                insureDiseaseDTO.setInsureIllnessId(MapUtils.get(item, "dise_caty_code")); // 中心疾病ID
                insureDiseaseDTO.setInsureIllnessCode(MapUtils.get(item, "dise_caty_code")); // 中心疾病编码
                insureDiseaseDTO.setInsureIllnessName(MapUtils.get(item, "dise_caty_name")); // 中心疾病名称
                insureDiseaseDTO.setIcd10(MapUtils.get(item, "dise_caty_code")); // ICD编码
                insureDiseaseDTO.setPym(null); // 拼音码
                insureDiseaseDTO.setWbm(null); // 五笔码
                insureDiseaseDTO.setLoseDate(null); //失效日期
                insureDiseaseDTO.setTakeDate(null); // 生效日期
                insureDiseaseDTO.setRemark(MapUtils.get(item, "memo")); // 备注
                insureDiseaseDTO.setCrteId(crteId); // 创建人ID
                insureDiseaseDTO.setCrteName(crteName); // 创建人姓名
                insureDiseaseDTO.setCrteTime(DateUtils.getNow()); //创建时间
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
     * @Desrciption 医保统一支付平台：肿瘤形态学目录下载
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
                insureDiseaseDTO.setId(SnowflakeUtils.getId()); // 主键
                insureDiseaseDTO.setHospCode(hospCode); // 医院编码
                insureDiseaseDTO.setInsureRegCode(insureRegCode); // 医保机构注册编码
                insureDiseaseDTO.setInsureIllnessId(MapUtils.get(item, "tumour_morphology_id")); // 中心疾病ID
                insureDiseaseDTO.setInsureIllnessCode(MapUtils.get(item, "morphology_classify_code")); // 中心疾病编码
                insureDiseaseDTO.setInsureIllnessName(MapUtils.get(item, "morphology_classify_name")); // 中心疾病名称
                insureDiseaseDTO.setIcd10(MapUtils.get(item, "tumour_morphology_id")); // ICD编码
                insureDiseaseDTO.setPym(null); // 拼音码
                insureDiseaseDTO.setWbm(null); // 五笔码
                insureDiseaseDTO.setLoseDate(null); //失效日期
                insureDiseaseDTO.setTakeDate(null); // 生效日期
                insureDiseaseDTO.setRemark(MapUtils.get(item, "memo")); // 备注
                insureDiseaseDTO.setCrteId(crteId); // 创建人ID
                insureDiseaseDTO.setCrteName(crteName); // 创建人姓名
                insureDiseaseDTO.setCrteTime(DateUtils.getNow()); //创建时间
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
     * @Method insert_1309
     * @Desrciption 医保统一支付平台：日间手术治疗病种目录下载
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
        String ver = MapUtils.get(map,"ver");
        InsureDiseaseDTO insureDiseaseDTO = null;
        if (!ListUtils.isEmpty(dataResultMap)) {
            for (Map<String, Object> item : dataResultMap) {
                insureDiseaseDTO = new InsureDiseaseDTO();
                insureDiseaseDTO.setRecordCounts(recordCounts);
                insureDiseaseDTO.setSize(size);
                insureDiseaseDTO.setNum(num);
                insureDiseaseDTO.setId(SnowflakeUtils.getId()); // 主键
                insureDiseaseDTO.setHospCode(hospCode); // 医院编码
                insureDiseaseDTO.setInsureRegCode(insureRegCode); // 医保机构注册编码
                insureDiseaseDTO.setInsureIllnessId(null); // 中心疾病ID
                insureDiseaseDTO.setInsureIllnessCode(MapUtils.get(item, "daytime_oprt_dise_code")); // 中心疾病编码
                insureDiseaseDTO.setInsureIllnessName(MapUtils.get(item, "daytime_oprt_dise_name")); // 中心疾病名称
                insureDiseaseDTO.setIcd10(null); // ICD编码
                insureDiseaseDTO.setPym(null); // 拼音码
                insureDiseaseDTO.setWbm(null); // 五笔码
                insureDiseaseDTO.setLoseDate(null); //失效日期
                insureDiseaseDTO.setTakeDate(null); // 生效日期
                insureDiseaseDTO.setRemark(MapUtils.get(item, "memo")); // 备注
                insureDiseaseDTO.setCrteId(crteId); // 创建人ID
                insureDiseaseDTO.setCrteName(crteName); // 创建人姓名
                insureDiseaseDTO.setCrteTime(DateUtils.getNow()); //创建时间
                insureDiseaseDTO.setDownLoadType(listType);
                insureDiseaseDTO.setVer(MapUtils.get(item, "ver"));
                if(num > (recordCounts / size)){
                    insureDiseaseDTO.setVerName(MapUtils.get(item, "ver_name"));
                }else{
                    insureDiseaseDTO.setVerName(ver); // 版本号
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
     * @Desrciption 医保统一支付平台：按病种付费病种目录下载
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
        String ver = MapUtils.get(map,"ver");
        int recordCounts = MapUtils.get(map, "recordCounts");
        InsureDiseaseDTO insureDiseaseDTO = null;
        if (!ListUtils.isEmpty(dataResultMap)) {
            for (Map<String, Object> item : dataResultMap) {
                insureDiseaseDTO = new InsureDiseaseDTO();
                insureDiseaseDTO.setRecordCounts(recordCounts);
                insureDiseaseDTO.setSize(size);
                insureDiseaseDTO.setNum(num);
                insureDiseaseDTO.setId(SnowflakeUtils.getId()); // 主键
                insureDiseaseDTO.setHospCode(hospCode); // 医院编码
                insureDiseaseDTO.setInsureRegCode(insureRegCode); // 医保机构注册编码
                insureDiseaseDTO.setInsureIllnessId(MapUtils.get(item, "ise_selt_list_id")); // 中心疾病IDdise_selt_list_id
                insureDiseaseDTO.setInsureIllnessCode(MapUtils.get(item, "dise_selt_list_code")); // 中心疾病编码
                insureDiseaseDTO.setInsureIllnessName(MapUtils.get(item, "dise_selt_list_name")); // 中心疾病名称
                System.out.println("=============" + MapUtils.get(item, "dise_selt_list_name"));
                insureDiseaseDTO.setIcd10(MapUtils.get(item, "ise_selt_list_id")); // ICD编码
                insureDiseaseDTO.setPym(null); // 拼音码
                insureDiseaseDTO.setWbm(null); // 五笔码
                insureDiseaseDTO.setLoseDate(null); //失效日期
                insureDiseaseDTO.setTakeDate(null); // 生效日期
                insureDiseaseDTO.setRemark(MapUtils.get(item, "memo")); // 备注
                insureDiseaseDTO.setCrteId(crteId); // 创建人ID
                insureDiseaseDTO.setCrteName(crteName); // 创建人姓名
                insureDiseaseDTO.setCrteTime(DateUtils.getNow()); //创建时间
                insureDiseaseDTO.setDownLoadType(listType);
                insureDiseaseDTO.setVer(MapUtils.get(item, "ver"));
                if(num > (recordCounts / size)){
                    insureDiseaseDTO.setVerName(MapUtils.get(item, "ver_name"));
                }else{
                    insureDiseaseDTO.setVerName(ver); // 版本号
                }
                insureDiseaseDTOList.add(insureDiseaseDTO);
            }
            int batchCount = 1000; // 定义分批处理的数据大小
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
     * @Desrciption 医保统一支付平台：门诊慢特病种目录下载
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
        String ver = MapUtils.get(map,"ver");
        int recordCounts = MapUtils.get(map, "recordCounts");
        InsureDiseaseDTO insureDiseaseDTO = null;
        if (!ListUtils.isEmpty(dataResultMap)) {
            for (Map<String, Object> item : dataResultMap) {
                insureDiseaseDTO = new InsureDiseaseDTO();
                insureDiseaseDTO.setRecordCounts(recordCounts);
                insureDiseaseDTO.setSize(size);
                insureDiseaseDTO.setNum(num);
                insureDiseaseDTO.setId(SnowflakeUtils.getId()); // 主键
                insureDiseaseDTO.setHospCode(hospCode); // 医院编码
                insureDiseaseDTO.setInsureRegCode(insureRegCode); // 医保机构注册编码
                insureDiseaseDTO.setInsureIllnessId(null); // 中心疾病ID
                insureDiseaseDTO.setInsureIllnessCode(MapUtils.get(item, "opsp_dise_code")); // 中心疾病编码
                insureDiseaseDTO.setInsureIllnessName(MapUtils.get(item, "opsp_dise_name")); // 中心疾病名称
                insureDiseaseDTO.setIcd10(null); // ICD编码
                insureDiseaseDTO.setPym(null); // 拼音码
                insureDiseaseDTO.setWbm(null); // 五笔码
                insureDiseaseDTO.setLoseDate(null); //失效日期
                insureDiseaseDTO.setTakeDate(null); // 生效日期
                insureDiseaseDTO.setRemark(MapUtils.get(item, "memo")); // 备注
                insureDiseaseDTO.setCrteId(crteId); // 创建人ID
                insureDiseaseDTO.setCrteName(crteName); // 创建人姓名
                insureDiseaseDTO.setCrteTime(DateUtils.getNow()); //创建时间
                insureDiseaseDTO.setDownLoadType(listType);
                insureDiseaseDTO.setVer(MapUtils.get(item, "ver"));
                if(num > (recordCounts / size)){
                    insureDiseaseDTO.setVerName(MapUtils.get(item, "ver_name"));
                }else{
                    insureDiseaseDTO.setVerName(ver); // 版本号
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
     * @Desrciption 医保统一支付平台： 手术操作目录下载
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
        String ver = MapUtils.get(map,"ver");
        InsureDiseaseDTO insureDiseaseDTO = null;
        if (!ListUtils.isEmpty(dataResultMap)) {
            for (Map<String, Object> item : dataResultMap) {
                insureDiseaseDTO = new InsureDiseaseDTO();
                insureDiseaseDTO.setRecordCounts(recordCounts);
                insureDiseaseDTO.setSize(size);
                insureDiseaseDTO.setNum(num);
                insureDiseaseDTO.setId(SnowflakeUtils.getId()); // 主键
                insureDiseaseDTO.setHospCode(hospCode); // 医院编码
                insureDiseaseDTO.setInsureRegCode(insureRegCode); // 医保机构注册编码
                insureDiseaseDTO.setInsureIllnessId(null); // 中心疾病ID
                insureDiseaseDTO.setInsureIllnessCode(MapUtils.get(item, "oprn_oprt_code")); // 中心疾病编码
                insureDiseaseDTO.setInsureIllnessName(MapUtils.get(item, "oprn_oprt_name")); // 中心疾病名称
                insureDiseaseDTO.setIcd10(null); // ICD编码
                insureDiseaseDTO.setPym(null); // 拼音码
                insureDiseaseDTO.setWbm(null); // 五笔码
                insureDiseaseDTO.setLoseDate(null); //失效日期
                insureDiseaseDTO.setTakeDate(null); // 生效日期
                insureDiseaseDTO.setRemark(MapUtils.get(item, "memo")); // 备注
                insureDiseaseDTO.setCrteId(crteId); // 创建人ID
                insureDiseaseDTO.setCrteName(crteName); // 创建人姓名
                insureDiseaseDTO.setCrteTime(DateUtils.getNow()); //创建时间
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
     * @Desrciption 医保统一支付平台： 疾病与诊断目录下载
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
        String ver = MapUtils.get(map,"ver");
        InsureDiseaseDTO insureDiseaseDTO = null;
        if (!ListUtils.isEmpty(dataResultMap)) {
            for (Map<String, Object> item : dataResultMap) {
                insureDiseaseDTO = new InsureDiseaseDTO();
                insureDiseaseDTO.setRecordCounts(recordCounts);
                insureDiseaseDTO.setSize(size);
                insureDiseaseDTO.setNum(num);
                insureDiseaseDTO.setId(SnowflakeUtils.getId()); // 主键
                insureDiseaseDTO.setHospCode(hospCode); // 医院编码
                insureDiseaseDTO.setInsureRegCode(insureRegCode); // 医保机构注册编码
                insureDiseaseDTO.setInsureIllnessId(MapUtils.get(item, "illness_id")); // 中心疾病ID
                insureDiseaseDTO.setInsureIllnessCode(MapUtils.get(item, "diag_code")); // 中心疾病编码
                insureDiseaseDTO.setInsureIllnessName(MapUtils.get(item, "diag_name")); // 中心疾病名称
                insureDiseaseDTO.setIcd10(MapUtils.get(item, "illness_id")); // ICD编码
                insureDiseaseDTO.setPym(null); // 拼音码
                insureDiseaseDTO.setWbm(null); // 五笔码
                insureDiseaseDTO.setLoseDate(null); //失效日期
                insureDiseaseDTO.setTakeDate(null); // 生效日期
                insureDiseaseDTO.setRemark(MapUtils.get(item, "memo")); // 备注
                insureDiseaseDTO.setCrteId(crteId); // 创建人ID
                insureDiseaseDTO.setCrteName(crteName); // 创建人姓名
                insureDiseaseDTO.setCrteTime(DateUtils.getNow()); //创建时间
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
     * @Desrciption 医保统一支付平台： 医用耗材目录下载
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
        String ver = MapUtils.get(map,"ver");
        int recordCounts = MapUtils.get(map, "recordCounts");
        logger.info("材料下载数据量:" + dataResultMap.size());
        InsureItemDTO itemDTO = null;
        if (!ListUtils.isEmpty(dataResultMap)) {
            for (Map<String, Object> item : dataResultMap) {
                itemDTO = new InsureItemDTO();
                itemDTO.setRecordCounts(recordCounts);
                itemDTO.setSize(size);
                itemDTO.setNum(num);
                // 主键
                itemDTO.setId(SnowflakeUtils.getId());
                // 医院编码
                itemDTO.setHospCode(hospCode);
                // 医疗机构编码
                itemDTO.setInsureRegCode(insureRegCode);
                // 项目类别标志  --费用类别
                itemDTO.setItemMark("301");
                //医保中心项目编码
                itemDTO.setItemCode(MapUtils.get(item, "med_list_codg"));
                // 医保中心项目名称
                itemDTO.setItemName(MapUtils.get(item, "ins_common_name"));
                //医保中心项目类别
                itemDTO.setItemType("301");
                // 医保中心项目规格
                itemDTO.setItemSpec(MapUtils.get(item, "spec"));
                // 医保中心项目剂型
                itemDTO.setItemDosage("");
                // 医保中心项目价格
                itemDTO.setItemPrice(null);
                // 医保中心项目单位
                itemDTO.setItemUnitCode(MapUtils.get(item, "min_useunt"));
                // 生产厂家
                itemDTO.setProd(MapUtils.get(item, "manu_name"));
                // 自费比例
                itemDTO.setDeductible(null);
                // 限价
                itemDTO.setCheckPrice(null);
                // 医保目录标志（0.甲、1.乙、2.全自费）
                itemDTO.setDirectory(null);

                // 生效日期
                String strStart = MapUtils.get(item, "begndate");
                if (!StringUtils.isEmpty(strStart) && !"null".equals(strStart)) {
                    Date date = new Date(strStart);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:MM:ss");
                    itemDTO.setTakeDate(DateUtils.parse(simpleDateFormat.format(date), DateUtils.Y_M_DH_M_S));
                } else {
                    itemDTO.setTakeDate(null);
                }
                String strEnd = MapUtils.get(item, "begndate");
                //失效日期
                if (!StringUtils.isEmpty(strEnd) && !"null".equals(strEnd)) {
                    Date date = new Date(strEnd);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:MM:ss");
                    itemDTO.setTakeDate(DateUtils.parse(simpleDateFormat.format(date), DateUtils.Y_M_DH_M_S));
                } else {
                    itemDTO.setLoseDate(null);
                }

                // 拼音码
                itemDTO.setPym(null);
                // 五笔码
                itemDTO.setWbm(null);
                // 是否有效（SF）
                itemDTO.setIsValid(MapUtils.get(item, "vali_flag"));
                // 创建人ID
                itemDTO.setCrteId(crteId);
                // 创建人姓名
                itemDTO.setCrteName(crteName);
                // 创建时间
                itemDTO.setCrteTime(DateUtils.getNow());
                itemDTO.setCompoundLogo(null);
                itemDTO.setVerName(MapUtils.get(item, "ver_name"));
                itemDTO.setVer(MapUtils.get(item, "ver")); // 版本号
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
     * @Desrciption 医保统一支付平台： 医疗服务项目目录下载
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
        System.out.println("项目下载数据数量:" + dataResultMap.size());
        int size = MapUtils.get(map, "size");
        int num = MapUtils.get(map, "num");
        int recordCounts = MapUtils.get(map, "recordCounts");
        String ver = MapUtils.get(map,"ver");
        InsureItemDTO itemDTO = null;
        if (!ListUtils.isEmpty(dataResultMap)) {
            for (Map<String, Object> item : dataResultMap) {
                itemDTO = new InsureItemDTO();
                itemDTO.setRecordCounts(recordCounts);
                itemDTO.setSize(size);
                itemDTO.setNum(num);
                // 主键
                itemDTO.setId(SnowflakeUtils.getId());
                // 医院编码
                itemDTO.setHospCode(hospCode);
                // 医疗机构编码
                itemDTO.setInsureRegCode(insureRegCode);
                // 项目类别标志  --费用类别
                itemDTO.setItemMark("201");
                //医保中心项目编码
                itemDTO.setItemCode(MapUtils.get(item, "med_list_codg"));
                // 医保中心项目名称
                itemDTO.setItemName(MapUtils.get(item, "med_item_name"));
                //医保中心项目类别
                itemDTO.setItemType("201");
                // 医保中心项目规格
                itemDTO.setItemSpec(MapUtils.get(item, "spec"));
                // 医保中心项目剂型
                itemDTO.setItemDosage("");
                // 医保中心项目价格
                itemDTO.setItemPrice(null);
                // 医保中心项目单位
                itemDTO.setItemUnitCode(MapUtils.get(item, "min_useunt"));
                // 生产厂家
                itemDTO.setProd(MapUtils.get(item, "manu_name"));
                // 自费比例
                itemDTO.setDeductible(null);
                // 限价
                itemDTO.setCheckPrice(null);
                // 医保目录标志（0.甲、1.乙、2.全自费）
                itemDTO.setDirectory(null);
                itemDTO.setTakeDate(DateUtils.parse(MapUtils.get(item,"begndate"),DateUtils.Y_M_D));
                itemDTO.setLoseDate(DateUtils.parse(MapUtils.get(item,"enddate"),DateUtils.Y_M_D));
                // 拼音码
                itemDTO.setPym(null);
                // 五笔码
                itemDTO.setWbm(null);
                // 是否有效（SF）
                itemDTO.setIsValid(MapUtils.get(item, "vali_flag"));
                // 创建人ID
                itemDTO.setCrteId(crteId);
                // 创建人姓名
                itemDTO.setCrteName(crteName);
                // 创建时间
                itemDTO.setCrteTime(DateUtils.getNow());
                itemDTO.setLmtUsedFlag(MapUtils.get(item, "lmt_used_flag"));  // 限制使用标志
                itemDTO.setCompoundLogo(MapUtils.get(item, "compound_logo"));
                itemDTO.setVer(MapUtils.get(item, "ver")); // 版本号
                // 如果第一次根据版本号下载返回回来的数据的大小  大于默认数量，则继续使用当前版本号
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
     * @Desrciption 医保统一支付平台： 医疗机构制剂目录下载
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
        String ver = MapUtils.get(map,"ver");
        int recordCounts = MapUtils.get(map, "recordCounts");
        InsureItemDTO itemDTO = null;
        if (!ListUtils.isEmpty(dataResultMap)) {
            for (Map<String, Object> item : dataResultMap) {
                itemDTO = new InsureItemDTO();
                // 主键
                itemDTO.setId(SnowflakeUtils.getId());
                itemDTO.setRecordCounts(recordCounts);
                itemDTO.setSize(size);
                itemDTO.setNum(num);
                // 医院编码
                itemDTO.setHospCode(hospCode);
                // 医疗机构编码
                itemDTO.setInsureRegCode(insureRegCode);
                // 项目类别标志  --费用类别
                itemDTO.setItemMark("104");
                //医保中心项目编码
                itemDTO.setItemCode(MapUtils.get(item, "med_list_codg"));
                // 医保中心项目名称
                itemDTO.setItemName(MapUtils.get(item, "drug_prodname"));
                //医保中心项目类别
                itemDTO.setItemType("104");
                // 医保中心项目规格
                itemDTO.setItemSpec(MapUtils.get(item, "drug_spec"));
                // 医保中心项目剂型
                itemDTO.setItemDosage(MapUtils.get(item, "dosform_name"));
                // 医保中心项目价格
                itemDTO.setItemPrice(null);
                // 医保中心项目单位
                itemDTO.setItemUnitCode(MapUtils.get(item, "min_useunt"));
                // 生产厂家
                itemDTO.setProd(MapUtils.get(item, "manufacturer_name"));
                // 自费比例
                itemDTO.setDeductible(null);
                // 限价
                itemDTO.setCheckPrice(null);
                // 医保目录标志（0.甲、1.乙、2.全自费）
                itemDTO.setDirectory(MapUtils.get(item, "nat_ins_paypolicy"));
                // 生效日期
                // 生效日期
                String strStart = MapUtils.get(item, "begndate");
                if (!StringUtils.isEmpty(strStart) && !"null".equals(strStart)) {
                    Date date = new Date(strStart);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:MM:ss");
                    itemDTO.setTakeDate(DateUtils.parse(simpleDateFormat.format(date), DateUtils.Y_M_DH_M_S));
                } else {
                    itemDTO.setTakeDate(null);
                }
                String strEnd = MapUtils.get(item, "begndate");
                //失效日期
                if (!StringUtils.isEmpty(strEnd) && !"null".equals(strEnd)) {
                    Date date = new Date(strEnd);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:MM:ss");
                    itemDTO.setLoseDate(DateUtils.parse(simpleDateFormat.format(date), DateUtils.Y_M_DH_M_S));
                } else {
                    itemDTO.setLoseDate(null);
                }
                // 拼音码
                itemDTO.setPym(null);
                // 五笔码
                itemDTO.setWbm(null);
                // 是否有效（SF）
                itemDTO.setIsValid(MapUtils.get(item, "vali_flag"));
                // 创建人ID
                itemDTO.setCrteId(crteId);
                // 创建人姓名
                itemDTO.setCrteName(crteName);
                // 创建时间
                itemDTO.setCrteTime(DateUtils.getNow());
                itemDTO.setCompoundLogo(MapUtils.get(item, "compound_logo"));
                itemDTO.setVer(MapUtils.get(item, "ver")); // 版本号
                // 如果第一次根据版本号下载返回回来的数据的大小  大于默认数量，则继续使用当前版本号
                if(num > (recordCounts / size)){
                    itemDTO.setVerName(MapUtils.get(item, "ver_name"));
                }else{
                    itemDTO.setVerName(ver); // 版本号
                }
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
     * @Desrciption 医保统一支付平台： 中药目录下载
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
        String ver = MapUtils.get(map,"ver");
        int recordCounts = MapUtils.get(map, "recordCounts");
        InsureItemDTO itemDTO = null;
        if (!ListUtils.isEmpty(dataResultMap)) {
            for (Map<String, Object> item : dataResultMap) {
                itemDTO = new InsureItemDTO();
                // 主键
                itemDTO.setId(SnowflakeUtils.getId());
                itemDTO.setRecordCounts(recordCounts);
                itemDTO.setSize(size);
                itemDTO.setNum(num);
                // 医院编码
                itemDTO.setHospCode(hospCode);
                // 医疗机构编码
                itemDTO.setInsureRegCode(insureRegCode);
                // 项目类别标志  --费用类别
                itemDTO.setItemMark("103");
                //医保中心项目编码
                itemDTO.setItemCode(MapUtils.get(item, "med_list_codg"));
                // 医保中心项目名称
                itemDTO.setItemName(MapUtils.get(item, "med_name"));
                //医保中心项目类别
                itemDTO.setItemType("103");
                // 医保中心项目规格
                itemDTO.setItemSpec("");
                // 医保中心项目剂型
                itemDTO.setItemDosage("");
                // 医保中心项目价格
                itemDTO.setItemPrice(null);
                // 医保中心项目单位
                itemDTO.setItemUnitCode("");
                // 生产厂家
                itemDTO.setProd("");
                // 自费比例
                itemDTO.setDeductible("");
                // 限价
                itemDTO.setCheckPrice(null);
                // 医保目录标志（0.甲、1.乙、2.全自费）
                itemDTO.setDirectory("");
                map.put("code", "HOSP_APPR_FLAG");
                SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(map).getData();
                if (sysParameterDTO != null && !StringUtils.isEmpty(sysParameterDTO.getValue())) {
                    String value = sysParameterDTO.getValue();
                    Map<String, Object> stringObjectMap = JSON.parseObject(value, Map.class);
                    if (stringObjectMap.containsKey(insureRegCode)) {
                        // 生效日期
                        String strStart = MapUtils.get(item, "begndate");
                        if (!StringUtils.isEmpty(strStart) && !"null".equals(strStart)) {
                            Date date = new Date(strStart);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:MM:ss");
                            itemDTO.setTakeDate(DateUtils.parse(simpleDateFormat.format(date), DateUtils.Y_M_DH_M_S));
                        } else {
                            itemDTO.setTakeDate(null);
                        }
                        String strEnd = MapUtils.get(item, "begndate");
                        //失效日期
                        if (!StringUtils.isEmpty(strEnd) && !"null".equals(strEnd)) {
                            Date date = new Date(strEnd);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:MM:ss");
                            itemDTO.setLoseDate(DateUtils.parse(simpleDateFormat.format(date), DateUtils.Y_M_DH_M_S));
                        } else {
                            itemDTO.setLoseDate(null);
                        }
                    } else {
                        // 生效日期
                        if (!StringUtils.isEmpty(MapUtils.get(item, "begndate")) && !"null".equals(MapUtils.get(item, "begndate"))) {
                            itemDTO.setTakeDate(DateUtils.parse(MapUtils.get(item, "begndate"), DateUtils.Y_M_DH_M_S));
                        } else {
                            itemDTO.setTakeDate(null);
                        }
                        //失效日期
                        if (!StringUtils.isEmpty(MapUtils.get(item, "enddate")) && !"null".equals(MapUtils.get(item, "enddate"))) {
                            itemDTO.setLoseDate(DateUtils.parse(MapUtils.get(item, "enddate"), DateUtils.Y_M_DH_M_S));
                        } else {
                            itemDTO.setLoseDate(null);
                        }
                    }
                }
                // 拼音码
                itemDTO.setPym("");
                // 五笔码
                itemDTO.setWbm("");
                // 是否有效（SF）
                itemDTO.setIsValid(MapUtils.get(item, "vali_flag"));
                // 创建人ID
                itemDTO.setCrteId(crteId);
                // 创建人姓名
                itemDTO.setCrteName(crteName);
                // 创建时间
                itemDTO.setCrteTime(DateUtils.getNow());
                itemDTO.setCompoundLogo(MapUtils.get(item, "compound_logo")); // 复方标志
                itemDTO.setVer(MapUtils.get(item, "ver")); // 版本号
                // 如果第一次根据版本号下载返回回来的数据的大小  大于默认数量，则继续使用当前版本号
                if(num > (recordCounts / size)){
                    itemDTO.setVerName(MapUtils.get(item, "ver_name"));
                }else{
                    itemDTO.setVerName(ver); // 版本号
                }
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
     * @Desrciption 医保统一支付平台： 西药和中成药目录下载
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
        logger.info("西药中成药下载数据量:" + dataResultMap.size());
        InsureItemDTO itemDTO = null;
        for (Map<String, Object> item : dataResultMap) {
            itemDTO = new InsureItemDTO();
            // 主键
            itemDTO.setId(SnowflakeUtils.getId());
            // 医院编码
            itemDTO.setHospCode(hospCode);
            itemDTO.setRecordCounts(recordCounts);
            itemDTO.setSize(size);
            itemDTO.setNum(num);
            // 医疗机构编码
            itemDTO.setInsureRegCode(insureRegCode);
            // 是否中成藥
            if (Constants.SF.S.equals(MapUtils.get(item, "patent_med_logo"))) {
                // 项目类别标志  --费用类别
                itemDTO.setItemMark("102");
                itemDTO.setItemType("102");
            } else {
                itemDTO.setItemMark("101");
                itemDTO.setItemType("101");
            }

            //医保中心项目编码
            itemDTO.setItemCode(MapUtils.get(item, "med_list_codg"));
            // 医保中心项目名称
            itemDTO.setItemName(MapUtils.get(item, "drug_prodname"));
            // 医保中心项目规格
            itemDTO.setItemSpec(MapUtils.get(item, "drug_spec"));
            // 医保中心项目剂型
            itemDTO.setItemDosage(MapUtils.get(item, "nat_med_insure_catalog"));
            // 医保中心项目价格
            itemDTO.setItemPrice(null);
            // 医保中心项目单位
            itemDTO.setItemUnitCode(MapUtils.get(map, "min_paunt"));
            // 生产厂家
            itemDTO.setProd(MapUtils.get(map, "manufacturer_name"));
            // 自费比例
            itemDTO.setDeductible(null);
            // 限价
            itemDTO.setCheckPrice(null);
            itemDTO.setDrugadmStrdcode(MapUtils.get(item, "drugadm_strdcode"));
            // 医保目录标志（0.甲、1.乙、2.全自费）
            itemDTO.setDirectory(MapUtils.get(item, "nat_med_insure_code"));
            // 生效日期
            // 生效日期
            String strStart = MapUtils.get(item, "begndate");
            if (!StringUtils.isEmpty(strStart) && !"null".equals(strStart)) {
                Date date = new Date(strStart);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:MM:ss");
                itemDTO.setTakeDate(DateUtils.parse(simpleDateFormat.format(date), DateUtils.Y_M_DH_M_S));
            } else {
                itemDTO.setTakeDate(null);
            }
            String strEnd = MapUtils.get(item, "begndate");
            //失效日期
            if (!StringUtils.isEmpty(strEnd) && !"null".equals(strEnd)) {
                Date date = new Date(strEnd);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:MM:ss");
                itemDTO.setLoseDate(DateUtils.parse(simpleDateFormat.format(date), DateUtils.Y_M_DH_M_S));
            } else {
                itemDTO.setLoseDate(null);
            }
            // 拼音码
            itemDTO.setPym(MapUtils.get(item, "pinyin"));
            // 五笔码
            itemDTO.setWbm(MapUtils.get(item, "wubi"));
            // 是否有效（SF）
            itemDTO.setIsValid(MapUtils.get(item, "vali_flag"));
            // 创建人ID
            itemDTO.setCrteId(crteId);
            // 创建人姓名
            itemDTO.setCrteName(crteName);
            // 创建时间
            itemDTO.setCrteTime(DateUtils.getNow());
            // 如果第一次根据版本号下载返回回来的数据的大小  大于默认数量，则继续使用当前版本号
            itemDTO.setVerName(MapUtils.get(item, "ver_name"));
            itemDTO.setVer(MapUtils.get(item, "ver")); // 版本号
            itemDTO.setLmtUsedFlag(MapUtils.get(item, "lmt_used_flag")); // 限制使用标志
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
     * @Desrciption 医保目录先自付比例信息查询
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
        Map httpParam = new HashMap();
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_1319);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("query_date", ""); // 查询时间点
        paramMap.put("hilist_code", MapUtils.get(map, "hilistCode")); // 医保目录编码
        paramMap.put("selfpay_prop_psn_type", ""); //医保目录自付比例人员类别
        paramMap.put("selfpay_prop_type", ""); // 目录自付比例类别

        paramMap.put("insu_admdvs", ""); //参保机构医保区划
        paramMap.put("begndate", null); // 开始日期
        paramMap.put("enddate", null); // 结束日期
        paramMap.put("vali_flag", "1"); // 有效标志
        paramMap.put("rid", ""); // 唯一记录号
        paramMap.put("tabname", null); // 表名
        paramMap.put("poolarea_no", ""); // 统筹区
        paramMap.put("updt_time", MapUtils.get(map, "updtTime")); // 更新时间
        paramMap.put("page_num", MapUtils.get(map, "pageNum")); // 当前页数
        paramMap.put("page_size", MapUtils.get(map, "pageSize")); // 本页数据量
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("data", paramMap);
        httpParam.put("input", inputMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("医保目录先自付比例信息查询入参:" + json);
        String url = insureConfigurationDTO.getUrl();
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(url, json);
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson);
        Map<String, Object> outMap = MapUtils.get(resultMap, "output");
        List<Map<String, Object>> mapList = MapUtils.get(outMap, "data");
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
                    insureUnifidRatioDO.setUpdtTime(DateUtils.parse(MapUtils.get(item, "updt_time"), DateUtils.Y_M_DH_M_S));
                    insureUnifidRatioDO.setCrterId(MapUtils.get(item, "crter_id"));
                    insureUnifidRatioDO.setCrterName(MapUtils.get(item, "crter_name"));
                    insureUnifidRatioDO.setCrteTime(DateUtils.parse(MapUtils.get(item, "crte_time"), DateUtils.Y_M_DH_M_S));
                    insureUnifidRatioDO.setCrteOptinsNo(MapUtils.get(item, "crte_optins_no"));
                    insureUnifidRatioDO.setOpterId(MapUtils.get(item, "opter_id"));
                    insureUnifidRatioDO.setOpterName(MapUtils.get(item, "opter_name"));
                    insureUnifidRatioDO.setOptinsNo(MapUtils.get(item, "optins_no"));
                    insureUnifidRatioDO.setPoolareaNo(MapUtils.get(item, "poolarea_no"));
                    insureUnifidRatioDO.setOptTime(DateUtils.parse(MapUtils.get(item, "opt_time"), DateUtils.Y_M_DH_M_S));
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
        }
        return map;
    }

    /**
     * @param map
     * @Method insertUnifiedDict
     * @Desrciption 查询下载医保统一支付码表
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
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO.setIsValid(Constants.SF.S);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);

        Map httpParam = new HashMap();
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_1901);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("type", type);
        dataMap.put("date", DateUtils.format(DateUtils.getNow(), DateUtils.Y_M_DH_M_S));
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("data", dataMap);
        httpParam.put("input", inputMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("码表字典下载入参:" + json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
        if (StringUtils.isEmpty(resultJson)) {
            throw new AppException("无法访问统一支付平台");
        }
        logger.info("码表字典下载回参:" + resultJson);
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson);
        Map<String, Object> outputMap = (Map<String, Object>) resultMap.get("output");
        if ("999".equals(MapUtils.get(resultMap, "code"))) {
            throw new AppException((String) resultMap.get("msg"));
        }
        if (!MapUtils.get(resultMap, "infcode").equals("0")) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        List<Map<String, Object>> resultDataMap = MapUtils.get(outputMap, "list");
        if (ListUtils.isEmpty(resultDataMap)) {
            throw new AppException("下载的码表数据为空");
        }

        InsureDictDTO insureDictDTO = null;
        List<InsureDictDO> insureDictDTOList = new ArrayList<>();
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
        if (!ListUtils.isEmpty(insureDictDTOList)) {
            map.put("insureDictDTOList", insureDictDTOList);
        }
        return map;
    }

    /**
     * @param map
     * @Method queryMedicnInfo
     * @Desrciption 通过功能号查询 医保信息
     * 1316：医疗目录与医保目录匹配信息查询
     * 1318：医保目录限价信息查询
     * 1319：医保目录先自付比例信息查询
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
     * @Desrciption 名族药品查询下载
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
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_1304);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("drug_genname", MapUtils.get(map, "drugGenname")); // 药品通用名
        paramMap.put("drug_prodname", MapUtils.get(map, "drugProdname")); //药品商品名
        paramMap.put("med_list_codg", MapUtils.get(map, "hilistCode")); // 医疗目录编码
        paramMap.put("vali_flag", Constants.SF.S); // 有效标志
        paramMap.put("updt_time", MapUtils.get(map, "updtTime")); // 更新时间
        paramMap.put("page_num", MapUtils.get(map, "pageNum")); // 当前页数
        paramMap.put("page_size", MapUtils.get(map, "pageSize")); // 本页数据量

        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("data", paramMap);
        httpParam.put("input", inputMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("民族药品目录查询入参:" + json);
        String url = insureConfigurationDTO.getUrl();
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(url, json);
        logger.info("民族药品目录查询回参:" + resultJson);
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson);
        Map<String, Object> outMap = MapUtils.get(resultMap, "output");
        List<Map<String, Object>> mapList = MapUtils.get(outMap, "data");
        map.put("mapList", mapList);
        return map;
    }

    /**
     * @Method insertInsureMedicinesMatch
     * @Desrciption 医药机构目录匹配信息查询
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
        Map httpParam = new HashMap();
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_1317);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("query_date", DateUtils.format(DateUtils.getNow(), DateUtils.Y_M_DH_M_S)); // 查询时间点
        paramMap.put("fixmedins_code", ""); // 定点医药机构编号
        paramMap.put("medins_list_codg", MapUtils.get(map, "medinsListCodg")); //定点医药机构目录编号
        paramMap.put("medins_list_name", ""); // 定点医药机构目录名称
        paramMap.put("insu_admdvs", ""); //参保机构医保区划
        paramMap.put("med_list_codg", MapUtils.get(map, "medListCodg")); // 医疗目录编码
        paramMap.put("list_type", ""); // 目录类别
        paramMap.put("begndate", ""); // 开始日期
        paramMap.put("vali_flag", Constants.SF.S); // 有效标志
        paramMap.put("updt_time", MapUtils.get(map, "updtTime")); // 更新时间
        paramMap.put("page_num", MapUtils.get(map, "pageNum")); // 当前页数
        paramMap.put("page_size", MapUtils.get(map, "pageSize")); // 本页数据量

        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("data", paramMap);
        httpParam.put("input", inputMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("医药机构目录匹配信息查询入参:" + json);
        String url = insureConfigurationDTO.getUrl();
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(url, json);
        logger.info("医药机构目录匹配信息查询回参:" + resultJson);
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson);
        Map<String, Object> outMap = MapUtils.get(resultMap, "output");
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
        }
        return map;
    }

    /**
     * @param insureUnifiedLimitPriceDO
     * @Method queryPageInsureUnifiedLimitPrice
     * @Desrciption 分页查询所有的目录限价信息
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
     * @Desrciption 分页查询所有医疗目录与医保目录匹配信息
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
     * @Desrciption 分页查询医保目录信息
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
     * @Desrciption 分页查询所有的目录限价信息
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
     * @Desrciption 【1317】医药机构目录匹配信息查询
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
     * 自动匹配
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
            throw new AppException("请先生成医院端收费项目数据！");
        }

        InsureItemDTO insureItemDTO = new InsureItemDTO();
        insureItemDTO.setHospCode(hospCode);
        insureItemDTO.setInsureRegCode(insureRegCode);
        List<InsureItemDTO> insureItemDTOList = insureItemDAO.queryInsureItemAll(insureItemDTO);
        if (ListUtils.isEmpty(insureItemDTOList)) {
            throw new AppException("医保端下载数据为空！");
        }

        List<InsureItemMatchDTO> insertList = new ArrayList<>();
        for (InsureItemMatchDTO hospItem : resultList) {
            for (InsureItemDTO insureItem : insureItemDTOList) {
                if (hospItem.getHospItemCode().equals(insureItem.getHospItemCode())
                    && hospItem.getHospItemName().equals(insureItem.getHospItemName()))
                {
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
     * @Desrciption 医保统一只支付平台 新增项目新增功能
     * @Param insureItemDTO:医保项目表
     * @Author fuhui
     * @Date 2021/6/15 11:29
     * @Return
     */
    @Override
    public Boolean insertUnifiedItem(InsureItemDTO insureItemDTO) {
        Map<String,Object> map = new HashMap<>();
        String hospCode = insureItemDTO.getHospCode();
        map.put("hospCode",hospCode);
        map.put("code","HOSP_INSURE_CODE");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(map).getData();
        String orgCode ="";
        if(sysParameterDTO ==null){
           throw  new AppException("请先配置系统参数:" +"HOSP_INSURE_CODE"+"值为医院的医疗机构编码");
        }else{
            orgCode = sysParameterDTO.getValue(); // 获取医疗机构编码
        }
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setOrgCode(orgCode);
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO = getInsureConfiguration(insureConfigurationDTO);
        insureItemDTO.setId(SnowflakeUtils.getId());
        insureItemDTO.setInsureRegCode(insureConfigurationDTO.getRegCode());
        insureItemDTO.setItemType(insureItemDTO.getItemMark());
        return insureItemDAO.insertUnifiedItem(insureItemDTO);
    }

    /**
     * @Method UP_1312
     * @Desrciption 医保目录信息查询
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
        Map httpParam = new HashMap();
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_1312);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("query_date", DateUtils.format(DateUtils.getNow(), DateUtils.Y_M_DH_M_S)); // 查询时间点
        paramMap.put("hilist_code", MapUtils.get(map, "hilistCode")); // 目录类别
        paramMap.put("insu_admdvs", ""); //参保机构医保区划
        paramMap.put("begndate", ""); // 开始日期
        paramMap.put("wubi", ""); //五笔助记码
        paramMap.put("pinyin", ""); // 拼音助记码

        paramMap.put("med_chrgitm_type", ""); //医疗收费项目类别
        paramMap.put("chrgitm_lv", ""); // 收费项目等级
        paramMap.put("lmt_used_flag", ""); //限制使用标志
        paramMap.put("list_type", MapUtils.get(map, "listType")); // 目录类别
        paramMap.put("med_use_flag", ""); //医疗使用标志
        paramMap.put("matn_used_flag", ""); // 生育使用标志
        paramMap.put("hilist_use_type", ""); //医保目录使用类别
        paramMap.put("lmt_cpnd_type", ""); // 限复方使用类型
        paramMap.put("vali_flag", Constants.SF.S); // 有效标志
        paramMap.put("rid", ""); // 唯一记录号
        paramMap.put("tabname", ""); // 表名
        paramMap.put("poolarea_no", ""); // 统筹区
        paramMap.put("updt_time", MapUtils.get(map, "updtTime")); // 更新时间
        paramMap.put("page_num", MapUtils.get(map, "pageNum")); // 当前页数
        paramMap.put("page_size", MapUtils.get(map, "pageSize")); // 本页数据量

        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("data", paramMap);
        httpParam.put("input", inputMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("医保目录信息查询查询入参:" + json);
        String url = insureConfigurationDTO.getUrl();
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(url, json);
        if (StringUtils.isEmpty(resultJson)) {
            throw new AppException("访问统一支付平台失败");
        }
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson);
        Map<String, Object> outMap = MapUtils.get(resultMap, "output");
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
        }
        return map;
    }

    /**
     * @Method UP_1316
     * @Desrciption 医疗目录与医保目录匹配信息查询
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
        Map httpParam = new HashMap();
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_1316);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("query_date", DateUtils.format(DateUtils.getNow(), DateUtils.Y_M_DH_M_S)); // 查询时间点
        paramMap.put("hilist_code", MapUtils.get(map, "hilistCode")); // 医保目录编码
        paramMap.put("medins_list_codg", MapUtils.get(map, "medinsListCodg")); //定点医药机构目录编号
        paramMap.put("list_type", MapUtils.get(map, "listType")); // 目录自付比例类别
        paramMap.put("insu_admdvs", ""); //参保机构医保区划
        paramMap.put("begndate", ""); // 开始日期
        paramMap.put("enddate", ""); // 结束日期
        paramMap.put("vali_flag", Constants.SF.S); // 有效标志
        paramMap.put("rid", ""); // 唯一记录号
        paramMap.put("tabname", ""); // 表名
        paramMap.put("poolarea_no", ""); // 统筹区
        paramMap.put("updt_time", MapUtils.get(map, "updtTime")); // 更新时间
        paramMap.put("page_num", MapUtils.get(map, "pageNum")); // 当前页数
        paramMap.put("page_size", MapUtils.get(map, "pageSize")); // 本页数据量

        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("data", paramMap);
        httpParam.put("input", inputMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("医疗目录与医保目录匹配信息查询入参:" + json);
        String url = insureConfigurationDTO.getUrl();
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(url, json);
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson);
        Map<String, Object> outMap = MapUtils.get(resultMap, "output");
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
        }
        return map;
    }

    /**
     * @Method UP_1318
     * @Desrciption 医保目录限价信息查询
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
        Map httpParam = new HashMap();
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_1318);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("query_date", DateUtils.format(DateUtils.getNow(), DateUtils.Y_M_DH_M_S)); // 查询时间点
        paramMap.put("hilist_code", MapUtils.get(map, "hilistCode")); // 医保目录编码
        paramMap.put("hilist_lmtpric_type", ""); // 医保目录限价类型
        paramMap.put("overlmt_dspo_way", ""); // 医保目录超限处理方式
        paramMap.put("insu_admdvs", ""); //参保机构医保区划
        paramMap.put("begndate", ""); // 开始日期
        paramMap.put("enddate", ""); // 结束日期
        paramMap.put("vali_flag", Constants.SF.S); // 有效标志
        paramMap.put("rid", ""); // 唯一记录号
        paramMap.put("tabname", ""); // 表名
        paramMap.put("poolarea_no", ""); // 统筹区
        paramMap.put("updt_time", MapUtils.get(map, "updtTime")); // 更新时间
        paramMap.put("page_num", MapUtils.get(map, "pageNum")); // 当前页数
        paramMap.put("page_size", MapUtils.get(map, "pageSize")); // 本页数据量

        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("data", paramMap);
        httpParam.put("input", inputMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("医保目录限价信息查询入参:" + json);
        String url = insureConfigurationDTO.getUrl();
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(url, json);
        logger.info("医保目录限价信息查询回参:" + resultJson);
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson);
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
        return map;
    }
}
