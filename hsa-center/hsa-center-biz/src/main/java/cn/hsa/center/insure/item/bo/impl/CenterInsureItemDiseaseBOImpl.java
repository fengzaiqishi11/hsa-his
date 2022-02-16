package cn.hsa.center.insure.item.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.center.utils.Constant;

import cn.hsa.module.center.datasource.dto.CenterDatasourceDTO;
import cn.hsa.module.center.insure.item.bo.CenterInsureItemDiseaseBO;
import cn.hsa.module.center.insure.item.dao.CenterInsureItemDiseaseDAO;
import cn.hsa.module.insure.module.dao.*;
import cn.hsa.module.insure.module.dto.*;

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
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  中心端医保医疗疾病匹配下载,项目统一下载功能
 * @author fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/3/12 15:18
 **/
@Slf4j
@Component
public class CenterInsureItemDiseaseBOImpl implements CenterInsureItemDiseaseBO {


    CenterInsureItemDiseaseDAO centerInsureItemDiseaseDAO;


    private InsureConfigurationDAO insureConfigurationDAO;

    @Resource
    private SysParameterService sysParameterService_consumer;

    /**
     *  中心端数据源缓冲
     */
    private final Map<String, InsureConfigurationDTO> insureConfigurationCacheContainer = new ConcurrentHashMap<>(160);


    private final Logger logger = LoggerFactory.getLogger(CenterInsureItemDiseaseBOImpl.class);




    /**
     *  <p>同步医保平台疾病以及项目数据
     *  <p>根据下载类型的版本号：分页下载数据
     *  <p> num: 下载第几页       size:下载每页数据量的大小
     * @param map 请求参数
     * @return java.util.Map
     */
    @Override
    public Map<String, Object> syncItemDiseaseFromUnifiedPlatform(Map<String, Object> map) {
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
            num = (int) Optional.ofNullable(MapUtils.get(stringObjectMap, "pageNum")).orElse(0);
            size = (int) Optional.ofNullable(MapUtils.get(stringObjectMap, "pageSize")).orElse(0);
        }
        Map<String, Object> dataMap = new HashMap<>();
        Map<String,Object> httpParam = new HashMap<>();
        if (Constant.UnifiedPay.JBLIST.containsKey(itemType)) {
            InsureDiseaseDTO insureDiseaseDTO = centerInsureItemDiseaseDAO.selectCenterInsureDiseaseLatestVer(map);
            if (insureDiseaseDTO != null && (insureDiseaseDTO.getRecordCounts() < insureDiseaseDTO.getNum() * insureDiseaseDTO.getSize())) {
                throw new AppException("已经是最新数据了");
            }
            if (insureDiseaseDTO != null) {
                num = insureDiseaseDTO.getNum();
                dataMap.put("page_num", ++num);
            } else {
                dataMap.put("page_num", num);
            }
            dataMap.put("page_size", size);
        } else {
            InsureItemDTO insureItemDTO = centerInsureItemDiseaseDAO.selectCenterInsureItemLatestVer(map);
            if (insureItemDTO != null && (insureItemDTO.getRecordCounts() < insureItemDTO.getNum() * insureItemDTO.getSize())) {
                throw new AppException("已经是最新数据了");
            }
            if (insureItemDTO != null) {
                num = insureItemDTO.getNum();
                dataMap.put("page_num", ++num);
            } else {
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
        if (ListUtils.isEmpty(dataResultMap)) {
            throw new AppException("调用" + itemType + "功能号下载接口反参为空");
        }
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
                centerInsureItemDiseaseDAO.insertCenterInsureItem(insureItemDTOList);
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
        String ver = MapUtils.get(map, "ver");
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
                if (num > (recordCounts / size)) {
                    insureDiseaseDTO.setVerName(MapUtils.get(item, "ver_name"));
                } else {
                    insureDiseaseDTO.setVerName(ver); // 版本号
                }
                insureDiseaseDTOList.add(insureDiseaseDTO);
            }
            if (!ListUtils.isEmpty(insureDiseaseDTOList)) {
                centerInsureItemDiseaseDAO.insertCenterDisease(insureDiseaseDTOList);
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
                centerInsureItemDiseaseDAO.insertCenterDisease(insureDiseaseDTOList);
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
                centerInsureItemDiseaseDAO.insertCenterDisease(insureDiseaseDTOList);
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
        String ver = MapUtils.get(map, "ver");
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
                if (num > (recordCounts / size)) {
                    insureDiseaseDTO.setVerName(MapUtils.get(item, "ver_name"));
                } else {
                    insureDiseaseDTO.setVerName(ver); // 版本号
                }
                insureDiseaseDTOList.add(insureDiseaseDTO);
            }
            if (!ListUtils.isEmpty(insureDiseaseDTOList)) {
                centerInsureItemDiseaseDAO.insertCenterDisease(insureDiseaseDTOList);
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
        String ver = MapUtils.get(map, "ver");
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
                if (num > (recordCounts / size)) {
                    insureDiseaseDTO.setVerName(MapUtils.get(item, "ver_name"));
                } else {
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
                    centerInsureItemDiseaseDAO.insertCenterDisease(newList);
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
        String ver = MapUtils.get(map, "ver");
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
                if (num > (recordCounts / size)) {
                    insureDiseaseDTO.setVerName(MapUtils.get(item, "ver_name"));
                } else {
                    insureDiseaseDTO.setVerName(ver); // 版本号
                }
                insureDiseaseDTOList.add(insureDiseaseDTO);
            }
            if (!ListUtils.isEmpty(insureDiseaseDTOList)) {
                centerInsureItemDiseaseDAO.insertCenterDisease(insureDiseaseDTOList);
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
        String ver = MapUtils.get(map, "ver");
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
                centerInsureItemDiseaseDAO.insertCenterDisease(insureDiseaseDTOList);

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
        String ver = MapUtils.get(map, "ver");
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
                centerInsureItemDiseaseDAO.insertCenterDisease(insureDiseaseDTOList);

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
        String ver = MapUtils.get(map, "ver");
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
                itemDTO.setTakeDate(null);
                itemDTO.setLoseDate(null);
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
                    centerInsureItemDiseaseDAO.insertCenterInsureItem(newList);
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
        String ver = MapUtils.get(map, "ver");
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
                itemDTO.setTakeDate(null);
                itemDTO.setLoseDate(null);
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
                centerInsureItemDiseaseDAO.insertCenterInsureItem(insureItemDTOList);
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
        String ver = MapUtils.get(map, "ver");
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
                itemDTO.setTakeDate(null);
                itemDTO.setLoseDate(null);
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
                itemDTO.setVerName(MapUtils.get(item, "ver_name"));
                itemDTO.setDownLoadType(listType);
                insureItemDTOList.add(itemDTO);
            }
            if (!ListUtils.isEmpty(insureItemDTOList)) {
                centerInsureItemDiseaseDAO.insertCenterInsureItem(insureItemDTOList);
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
        String ver = MapUtils.get(map, "ver");
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
                itemDTO.setTakeDate(null);
                itemDTO.setLoseDate(null);
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
                itemDTO.setVerName(MapUtils.get(item, "ver_name"));
                itemDTO.setDownLoadType(listType);
                insureItemDTOList.add(itemDTO);
            }
            if (!ListUtils.isEmpty(insureItemDTOList)) {
                centerInsureItemDiseaseDAO.insertCenterInsureItem(insureItemDTOList);
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
            if (StringUtils.isEmpty(MapUtils.get(item, "drug_prodname")) || "无".equals(MapUtils.get(item, "drug_prodname")) ||
                    "null".equals(MapUtils.get(item, "drug_prodname"))) {
                itemDTO.setItemName(MapUtils.get(item, "drug_genname"));
            } else {
                itemDTO.setItemName(MapUtils.get(item, "drug_prodname"));
            }
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
            itemDTO.setTakeDate(null);
            String strEnd = MapUtils.get(item, "begndate");
            //失效日期
            itemDTO.setLoseDate(null);
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
                centerInsureItemDiseaseDAO.insertCenterInsureItem(newList);
            }
        }
    }

    /**
     * 分页查询医保项目信息
     *
     * @param insureItemDTO
     * @return
     */
    @Override
    public PageDTO queryInsureItemPage(InsureItemDTO insureItemDTO) {
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
            List<InsureDiseaseDTO> insureDiseaseDTOList = centerInsureItemDiseaseDAO.queryCenterInsureDiseasePage(insureDiseaseDTO);
            return PageDTO.of(insureDiseaseDTOList);
        } else {
            if (StringUtils.isEmpty(downLoadType)) {
                insureItemDTO.setDownLoadType("1301");
            }
            PageHelper.startPage(insureItemDTO.getPageNo(), insureItemDTO.getPageSize());
            List<InsureItemDTO> insureItemDTOList = centerInsureItemDiseaseDAO.queryCenterInsureItemPage(insureItemDTO);
            return PageDTO.of(insureItemDTOList);
        }
    }


}
