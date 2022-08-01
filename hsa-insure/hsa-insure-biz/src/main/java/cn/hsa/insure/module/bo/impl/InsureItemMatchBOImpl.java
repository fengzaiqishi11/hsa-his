package cn.hsa.insure.module.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.Transpond;
import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
import cn.hsa.module.base.bd.service.BaseDiseaseService;
import cn.hsa.module.base.bi.dto.BaseItemDTO;
import cn.hsa.module.base.bi.service.BaseItemService;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.bmm.service.BaseMaterialService;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.base.drug.service.BaseDrugService;
import cn.hsa.module.insure.module.bo.InsureItemMatchBO;
import cn.hsa.module.insure.module.dao.*;
import cn.hsa.module.insure.module.dto.*;
import cn.hsa.module.insure.outpt.service.InsureUnifiedPayRestService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.insure.module.insureItemMatch.bo.impl
 * @Class_name: InsureItemMatchImpl
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2020/11/7 11:05
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class InsureItemMatchBOImpl extends HsafBO implements InsureItemMatchBO {

    @Resource
    InsureItemMatchDAO insureItemMatchDAO;

    @Resource
    InsureItemDAO insureItemDAO;

    @Resource
    InsureDiseaseDAO insureDiseaseDAO;
    @Resource
    BaseDrugService baseDrugService;
    @Resource
    BaseItemService baseItemService;
    @Resource
    BaseMaterialService baseMaterialService;
    @Resource
    BaseDiseaseService baseDiseaseService;
    @Resource
    InsureConfigurationDAO insureConfigurationDAO;
    @Resource
    private InsureDiseaseMatchDAO insureDiseaseMatchDAO;
    @Resource
    private Transpond transpond;

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private InsureUnifiedPayRestService insureUnifiedPayRestService;


    /**
     * @Method queryPage
     * @Desrciption 分页查询
     * @Param [insureItemMatchDTO]
     * @Author liaojunjie
     * @Date 2020/11/7 11:11
     * @Return java.util.List<cn.hsa.module.insure.insureItemMatch.dto.InsureItemMatchDTO>
     **/
    @Override
    public PageDTO queryPage(InsureItemMatchDTO insureItemMatchDTO) {
        //设置分页信息
        PageHelper.startPage(insureItemMatchDTO.getPageNo(), insureItemMatchDTO.getPageSize());
        List<InsureItemMatchDTO> insureItemMatchDTOS = insureItemMatchDAO.queryPageOrAll(insureItemMatchDTO);
        for (InsureItemMatchDTO itemMatchDTO : insureItemMatchDTOS) {
            if (Constant.UnifiedPay.LISTTYPE.containsKey(itemMatchDTO.getItemCode())) {
                if (Constant.UnifiedPay.DOWNLOADTYPE.XY.equals(itemMatchDTO.getItemCode())) {
                    itemMatchDTO.setItemCode("西药");
                }
                if (Constant.UnifiedPay.DOWNLOADTYPE.ZCY.equals(itemMatchDTO.getItemCode())) {
                    itemMatchDTO.setItemCode("中成药");
                }
                if (Constant.UnifiedPay.DOWNLOADTYPE.ZYYP.equals(itemMatchDTO.getItemCode())) {
                    itemMatchDTO.setItemCode("中药饮片");
                }
                if (Constant.UnifiedPay.DOWNLOADTYPE.YYCL.equals(itemMatchDTO.getItemCode())) {
                    itemMatchDTO.setItemCode("医用材料");
                }
                if (Constant.UnifiedPay.DOWNLOADTYPE.FWXX.equals(itemMatchDTO.getItemCode())) {
                    itemMatchDTO.setItemCode("服务项目");
                }
                if (Constant.UnifiedPay.DOWNLOADTYPE.MZY.equals(itemMatchDTO.getItemCode())) {
                    itemMatchDTO.setItemCode("民族药");
                }
                if (Constant.UnifiedPay.DOWNLOADTYPE.ZZJ.equals(itemMatchDTO.getItemCode())) {
                    itemMatchDTO.setItemCode("自制剂");
                }
            }
        }
        return PageDTO.of(insureItemMatchDTOS);
    }

    /**
     * @Method queryAll
     * @Desrciption 查询全部
     * @Param [insureItemMatchDTO]
     * @Author liaojunjie
     * @Date 2020/12/1 11:49
     * @Return java.util.List<cn.hsa.module.insure.module.dto.InsureItemMatchDTO>
     **/
    @Override
    public List<InsureItemMatchDTO> queryAll(InsureItemMatchDTO insureItemMatchDTO) {
        List<InsureItemMatchDTO> insureItemMatchDTOS = insureItemMatchDAO.queryPageOrAll(insureItemMatchDTO);
        return insureItemMatchDTOS;
    }

    /**
     * @Method addItemMatch
     * @Desrciption 项目生成
     * 1.当前库存在的ID数据进行update（hosp_item_name、hosp_item_code、hosp_item_type、hosp_item_spec、hosp_item_unit_code、hosp_item_prep_code、hosp_item_price）
     * 2.查询当前库中ID对应不上的数据进行新增
     * @Param [insureItemMatchDTO]
     * @Author liaojunjie
     * @Date 2020/11/7 11:11
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean addItemMatch(InsureItemMatchDTO insureItemMatchDTO) {
        // 查询有效的药品、项目、材料基础表信息
        Map selectMap = new HashMap();
        selectMap.put("hospCode", insureItemMatchDTO.getHospCode());
        selectMap.put("isValid", Constants.SF.S);
        List<BaseDrugDTO> oldDrugs = insureItemMatchDAO.queryAllDrugsInfo(selectMap);
        List<BaseItemDTO> oldItems = insureItemMatchDAO.queryAllItemsInfo(selectMap);
        List<BaseMaterialDTO> oldMaterials = insureItemMatchDAO.queryAllInsureMaterialsInfo(selectMap);

        // 基础表中的ID
        List<String> oldDrugIds = oldDrugs.stream().map(BaseDrugDTO::getId).collect(Collectors.toList());
        List<String> oldItemIds = oldItems.stream().map(BaseItemDTO::getId).collect(Collectors.toList());
        List<String> oldMaterialIds = oldMaterials.stream().map(BaseMaterialDTO::getId).collect(Collectors.toList());

        // 查询出匹配表所有的数据
        InsureItemMatchDTO temp = new InsureItemMatchDTO();
        temp.setHospCode(insureItemMatchDTO.getHospCode());
        temp.setInsureRegCode(insureItemMatchDTO.getInsureRegCode());
        List<InsureItemMatchDTO> insureItemMatchDTOS = insureItemMatchDAO.queryPageOrAll(temp);

        // insureItemMatchDTOS 不为空，则是修改和新增，为空只新增
        if (!ListUtils.isEmpty(insureItemMatchDTOS)) {
            Map<String, List<InsureItemMatchDTO>> collect = insureItemMatchDTOS.stream().collect(Collectors.groupingBy(InsureItemMatchDTO::getHospItemType));
            List<InsureItemMatchDTO> drugs = new ArrayList<>();
            List<InsureItemMatchDTO> items = new ArrayList<>();
            List<InsureItemMatchDTO> materials = new ArrayList<>();

            for (String key : collect.keySet()) {
                switch (key) {
                    case Constants.XMLB.YP:
                        drugs.addAll(collect.get(key));
                        break;
                    case Constants.XMLB.XM:
                        items.addAll(collect.get(key));
                        break;
                    case Constants.XMLB.CL:
                        materials.addAll(collect.get(key));
                        break;
                    default:
                        break;
                }
            }
            List<String> newDrugIds = drugs.stream().map(InsureItemMatchDTO::getHospItemId).collect(Collectors.toList());
            List<String> newItemIds = items.stream().map(InsureItemMatchDTO::getHospItemId).collect(Collectors.toList());
            List<String> newMaterialIds = materials.stream().map(InsureItemMatchDTO::getHospItemId).collect(Collectors.toList());

            //需要新增的药品
            oldDrugIds.removeAll(newDrugIds);

            //需要新增的项目
            oldItemIds.removeAll(newItemIds);

            //需要新增的材料
            oldMaterialIds.removeAll(newMaterialIds);
        }

        List<BaseDrugDTO> collect2 = oldDrugs.stream().filter((BaseDrugDTO dto) -> oldDrugIds.contains(dto.getId())).collect(Collectors.toList());
        for (BaseDrugDTO b : collect2) {
            //项目类别 = 药品
            b.setTypeCode(Constants.XMLB.YP);
            b.setInsureRegCode(insureItemMatchDTO.getInsureRegCode());
            b.setInsureId(SnowflakeUtils.getId());
            b.setCrteId(insureItemMatchDTO.getCrteId());
            b.setCrteName(insureItemMatchDTO.getCrteName());
            if ("0".equals(b.getBigTypeCode())) {
                b.setBigTypeCode("11");
            }
            if ("1".equals(b.getBigTypeCode())) {
                b.setBigTypeCode("12");
            }
            if ("2".equals(b.getBigTypeCode())) {
                b.setBigTypeCode("13");
            }
        }

        // 需要新增的项目
        List<BaseItemDTO> collect4 = oldItems.stream().filter((BaseItemDTO dto) -> oldItemIds.contains(dto.getId())).collect(Collectors.toList());
        for (BaseItemDTO i : collect4) {
            //项目类别 = 项目
            i.setTypeCode(Constants.XMLB.XM);
            i.setInsureRegCode(insureItemMatchDTO.getInsureRegCode());
            i.setInsureId(SnowflakeUtils.getId());
            i.setCrteId(insureItemMatchDTO.getCrteId());
            i.setCrteName(insureItemMatchDTO.getCrteName());
            i.setBigTypeCode("2");
        }

        // 需要新增的材料
        List<BaseMaterialDTO> collect6 = oldMaterials.stream().filter((BaseMaterialDTO dto) -> oldMaterialIds.contains(dto.getId())).collect(Collectors.toList());
        for (BaseMaterialDTO i : collect6) {
            // 项目类别 = 材料
            i.setTypeCode(Constants.XMLB.CL);
            i.setInsureRegCode(insureItemMatchDTO.getInsureRegCode());
            i.setInsureId(SnowflakeUtils.getId());
            i.setCrteId(insureItemMatchDTO.getCrteId());
            i.setCrteName(insureItemMatchDTO.getCrteName());
            i.setBigTypeCode("3");
        }

        //根据医保注册编码和医院项目编码查看表里是否已存在,已存在不新增
        checkListExist(collect2,"drug",insureItemMatchDTOS);
        checkListExist(collect4, "item",insureItemMatchDTOS);
        checkListExist(collect6, "material",insureItemMatchDTOS);

        if (!ListUtils.isEmpty(collect2)) {
            saveBatchList(collect2, "drug");
        }
        if (!ListUtils.isEmpty(collect4)) {
            saveBatchList(collect4, "item");
        }
        if (!ListUtils.isEmpty(collect6)) {
            saveBatchList(collect6, "material");
        }
        return true;
    }

    /**
     * @Method deleteItemMatch
     * @Desrciption 清空某医保机构联合医院生成的数据
     * @Param [insureItemMatchDTO]
     * @Author liaojunjie
     * @Date 2020/12/1 11:53
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean deleteItemMatch(InsureItemMatchDTO insureItemMatchDTO) {
        return insureItemMatchDAO.deleteHospItem(insureItemMatchDTO);
    }


    /**
     * @Method addDownload
     * @Desrciption 将下载后的数据导入项目匹配表
     * @Param [insureItemMatchDTO]
     * @Author liaojunjie
     * @Date 2020/11/7 11:10
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean addDownload(InsureItemMatchDTO insureItemMatchDTO) {
        insureItemMatchDTO.setArea("");
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(insureItemMatchDTO.getHospCode());
        insureConfigurationDTO.setIsValid(Constants.SF.S);
        List<InsureConfigurationDTO> insureConfigurationDTOS = insureConfigurationDAO.queryAll(insureConfigurationDTO);
        Map<String, InsureConfigurationDTO> collect = insureConfigurationDTOS.stream().collect(Collectors.toMap(InsureConfigurationDTO::getCode, Function.identity()));
        if (collect.containsKey(insureItemMatchDTO.getInsureRegCode())) {
            InsureConfigurationDTO config = collect.get(insureItemMatchDTO.getInsureRegCode());
            if ("湖南省医保".equals(config.getName())) {
                insureItemMatchDTO.setArea(Constants.YBQY.HNS);
            }
        }
        if (Constants.YBQY.HNS.equals(insureItemMatchDTO.getArea())) {
            addDownloadByHNS(insureItemMatchDTO);
        } else {
            Map<String, String> map = new HashMap();
            String firstrow = "0";
            String lastrow = "99999999";
            map.put("condition", "finMatchCata");
            map.put("hospCode", insureItemMatchDTO.getHospCode());
            map.put("insureRegCode", insureItemMatchDTO.getInsureRegCode());
            map.put("firstrow", firstrow);
            map.put("lastrow", lastrow);
            Map<String, Object> finMatchCata = transpond.to(insureItemMatchDTO.getHospCode(), insureItemMatchDTO.getInsureRegCode(), Constant.HuNanSheng.MATCH.BIZC110118, map);
            //对返回的数据进行处理
            handleDownload(finMatchCata, insureItemMatchDTO);
        }
        return true;
    }

    /**
     * @Method queryHospItem()
     * @Desrciption 展示医院端自己的项目匹配内容
     * 1.药品 2.材料 3.项目 5.疾病
     * @Param itemName:项目名称 itemCode:项目类型
     * @Author fuhui
     * @Date 2021/1/27 10:13
     * @Return
     **/
    @Override
    public PageDTO queryHospItem(Map map) {
        String hospCode = map.get("hospCode").toString();
        Integer pageSize = Integer.valueOf(map.get("pageSize").toString());
        Integer pageNo = Integer.valueOf(map.get("pageNo").toString());
        BaseDiseaseDTO baseDiseaseDTO = new BaseDiseaseDTO();
        baseDiseaseDTO.setHospCode(hospCode);
        baseDiseaseDTO.setKeyword(map.get("keyword").toString());
        baseDiseaseDTO.setIsValid("1");
        baseDiseaseDTO.setPageNo(pageNo);
        baseDiseaseDTO.setPageSize(pageSize);
        map.put("baseDiseaseDTO", baseDiseaseDTO);
        WrapperResponse<PageDTO> pageDTOWrapperResponse = baseDiseaseService.queryPage(map);
        return pageDTOWrapperResponse.getData();
    }

    /**
     * @Method insertInsureItem
     * @Desrciption 长沙医保下载：根据项目编码，开始时间下载数据
     * @Param startDate:开始时间 也是医保端那边已经处理好匹配数据，如果时间选择当天或者最近，有可能返回空数据
     * downLoadType:下载类型：01药品目录 02：诊疗项目信息 03：医疗服务设施信息
     * 04：费用类别信息 05：病种信息 06：项目对照信息  07：病种分型信息
     * @Author fuhui
     * @Date 2021/1/27 10:30
     * @Return
     **/
    @Override
    public Boolean insertInsureItem(InsureItemDTO insureItemDTO) {
        String hospCode = insureItemDTO.getHospCode();
        String insureRegCode = insureItemDTO.getInsureRegCode();
        Map<String, Object> map = new HashMap<>();
//        map.put("code", "UNIFIED_PAY");
        map.put("hospCode", hospCode);  // 医院编码
        map.put("insureRegCode", insureItemDTO.getInsureRegCode());
        map.put("startDate", insureItemDTO.getStartDate());

        // 根据医保机构编码查询医保配置信息
        InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
        configDTO.setHospCode(hospCode); //医院编码
        configDTO.setRegCode(insureRegCode); // 医保注册编码
        configDTO.setIsValid(Constants.SF.S); // 是否有效
        List<InsureConfigurationDTO> configurationDTOList = insureConfigurationDAO.findByCondition(configDTO);
        if (ListUtils.isEmpty(configurationDTOList)) {
            throw new RuntimeException("未找到医保机构，请先配置医保信息！");
        }
        InsureConfigurationDTO insureConfigurationDTO = configurationDTOList.get(0);
        // 获取该医保配置是否走统一支付平台，1走，0/null不走
        String isUnifiedPay = insureConfigurationDTO.getIsUnifiedPay();

        /*SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(map).getData();
        if (sys != null && Constants.SF.S.equals(sys.getValue())) {*/
        if (StringUtils.isNotEmpty(isUnifiedPay) && "1".equals(isUnifiedPay)) {
            Map<String, Object> data = insureUnifiedPayRestService.UP_1317(map).getData();
        } else {
            Map<String, Object> finMatchData = transpond.to(hospCode, insureRegCode, Constant.ChangSha.RESTS.CS_1300, insureItemDTO);
            // 医保下载回来的数据
            List<Map<String, Object>> downLoadInfo = (List<Map<String, Object>>) finMatchData.get("downLoadInfo");
            if ("05".equals(insureItemDTO.getDownLoadType())) {
                handInsertDisease(downLoadInfo, insureItemDTO);
            } else {
                handInsertItem(downLoadInfo, insureItemDTO);
            }
        }
        return true;
    }

    /**
     * @Method handInsertDrug
     * @Desrciption 把从医保下载回来的疾病信息，保存到insure_disease表
     * 1.因为是根据时间从医保那边下载数据回来，存在可能下载多次的情况，分别需要判断每次下载的数据是否已经存库
     * @Param downLoadInfo: 保存医保端的疾病信息
     * insureItemDTO: 保存医保药品表时候的创建信息
     * @Author fuhui
     * @Date 2021/1/28 11:18
     * @Return
     **/
    private void handInsertDrug(List<Map<String, Object>> downLoadInfo, InsureItemDTO insureItemDTO) {
        List<InsureItemDTO> insureItemDTOList = new ArrayList<>();
        insureItemDTO.setItemType("1");
        List<InsureItemDTO> itemDTOList = insureItemDAO.queryInsureItemAll(insureItemDTO); // 查询出上次下载的医保药品数据，且已经存库
        Map<String, InsureItemDTO> collect = itemDTOList.stream().collect(Collectors.toMap(InsureItemDTO::getCode, Function.identity()));
        for (Map<String, Object> map : downLoadInfo) {
            if (collect.isEmpty() || !collect.containsKey(map.get("AKA060").toString())) {
                InsureItemDTO itemDTO = new InsureItemDTO();
                // 主键
                itemDTO.setId(SnowflakeUtils.getId());
                // 医院编码
                itemDTO.setHospCode(insureItemDTO.getHospCode());
                // 医疗机构编码
                itemDTO.setInsureRegCode(insureItemDTO.getInsureRegCode());
                // 项目类别标志  --费用类别
                itemDTO.setItemMark(map.get("AKA063").toString());
                //医保中心项目编码
                itemDTO.setItemCode(map.get("AKA060").toString());
                // 医保中心项目名称
                itemDTO.setItemName(map.get("AKA061").toString());
                //医保中心项目类别
                itemDTO.setItemType("1");
                // 医保中心项目规格
                itemDTO.setItemSpec(map.get("AKA077").toString());
                // 医保中心项目剂型
                itemDTO.setItemDosage(map.get("AKA070").toString());
                // 医保中心项目价格
                itemDTO.setItemPrice(BigDecimalUtils.convert(map.get("AKA068").toString()));
                // 医保中心项目单位
                itemDTO.setItemUnitCode(map.get("AKA076").toString());
                // 生产厂家
                itemDTO.setProd(null);
                // 自费比例
                itemDTO.setDeductible(null);
                // 限价
                itemDTO.setCheckPrice(BigDecimalUtils.convert(map.get("AKA068").toString()));
                // 医保目录标志（0.甲、1.乙、2.全自费）
                itemDTO.setDirectory(map.get("AKA065").toString());
                // 生效日期
                itemDTO.setTakeDate(null);
                //失效日期
                itemDTO.setLoseDate(null);
                // 拼音码
                itemDTO.setPym(map.get("AKA066").toString());
                // 五笔码
                itemDTO.setWbm(map.get("AKA074").toString());
                // 是否有效（SF）
                itemDTO.setIsValid(map.get("isValid").toString());
                // 创建人ID
                itemDTO.setCrteId(insureItemDTO.getCrteId());
                // 创建人姓名
                itemDTO.setCrteName(insureItemDTO.getCrteName());
                // 创建时间
                itemDTO.setCrteTime(DateUtils.getNow());
                insureItemDTOList.add(itemDTO);
            }
        }
        int batchCount = 1000; // 定义分批处理的数据大小
        if (!ListUtils.isEmpty(insureItemDTOList)) {
            if (insureItemDTOList.size() >= 1000) {
                List<InsureItemDTO> arrayList = new ArrayList<>();
                for (int i = 0; i < insureItemDTOList.size(); i++) {
                    arrayList.add(insureItemDTOList.get(i));
                    if (batchCount == arrayList.size() || i == insureItemDTOList.size() - 1) {
                        insureItemDAO.insertInsureItem(arrayList);
                        arrayList.clear();
                    }
                }
            } else {
                insureItemDAO.insertInsureItem(insureItemDTOList);
            }
        }

    }

    /**
     * @Method handInsertItem
     * @Desrciption 把从医保下载回来的疾病信息，保存到insure_disease表
     * @Param downLoadInfo: 保存医保端的疾病信息
     * insureItemDTO: 保存医保药品表时候的创建信息
     * @Author fuhui
     * @Date 2021/1/28 11:18
     * @Return
     **/
    private void handInsertItem(List<Map<String, Object>> downLoadInfo, InsureItemDTO insureItemDTO) {
        List<InsureItemMatchDTO> insureItemDTOList = new ArrayList<>();
        InsureItemMatchDTO insureItemMatchDTO = new InsureItemMatchDTO();
        insureItemMatchDTO.setHospCode(insureItemDTO.getHospCode()); // 医院编码
        insureItemMatchDTO.setInsureRegCode(insureItemDTO.getInsureRegCode());
        List<InsureItemMatchDTO> itemDTOList = insureItemMatchDAO.queryPageOrAll(insureItemMatchDTO); // 查询出上次下载的医保药品数据，且已经存库
        Map<String, InsureItemMatchDTO> collect = itemDTOList.stream().collect(Collectors.toMap(InsureItemMatchDTO::getHospItemId, Function.identity(), (k1, k2) -> k1));
        System.out.println("数据接收成功！！！");
        for (Map<String, Object> map : downLoadInfo) {
            if (collect.isEmpty() || !collect.containsKey(map.get("AKC222").toString())) {
                InsureItemMatchDTO itemMatchDTO = new InsureItemMatchDTO();
                itemMatchDTO.setInsureItemCode(map.get("AKC222").toString()); // 中心项目编码
                itemMatchDTO.setInsureItemName(map.get("AKA122").toString()); // 中心项目名称
                itemMatchDTO.setItemCode(map.get("AKA063").toString()); //  中心收费类别
                itemMatchDTO.setInsureRegCode(map.get("AKB020").toString()); // 定点医疗机构编码
                itemMatchDTO.setHospItemCode(map.get("AKC515").toString()); // 定点医疗机构项目编码
                itemMatchDTO.setHospItemName(map.get("AKC516").toString()); // 定点医疗机构项目名称
                itemMatchDTO.setHospItemPrepCode(map.get("AKA070").toString()); // 定点医疗机构药品剂型
                itemMatchDTO.setHospItemUnitCode(map.get("AKA076").toString()); // 单位
                itemMatchDTO.setHospItemSpec(map.get("AKA077").toString()); // 规格
                itemMatchDTO.setIsMatch(map.get("AKC175").toString()); // 审核标志
                itemMatchDTO.setIsValid(Constants.SF.S);
                itemMatchDTO.setAuditCode(Constants.SF.S);
                itemMatchDTO.setIsTrans(Constants.SF.S);
                itemMatchDTO.setHospCode(insureItemDTO.getHospCode());
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

    /**
     * @Method handInsertDisease
     * @Desrciption 把从医保下载回来的疾病信息，保存到insure_disease表
     * @Param downLoadInfo: 保存医保端的疾病信息
     * insureItemDTO: 保存医保疾病表时候的创建信息
     * @Author fuhui
     * @Date 2021/1/28 11:18
     * @Return
     **/
    private void handInsertDisease(List<Map<String, Object>> downLoadInfo, InsureItemDTO insureItemDTO) {
        List<InsureDiseaseDTO> insureDiseaseDTOList = new ArrayList<>();
        InsureDiseaseDTO diseaseDTO = new InsureDiseaseDTO();
        diseaseDTO.setHospCode(insureItemDTO.getHospCode());
        diseaseDTO.setInsureRegCode(insureItemDTO.getInsureRegCode());
        List<InsureDiseaseDTO> insureDiseaseMatchDTOList = insureDiseaseDAO.queryAll(diseaseDTO);
        Map<String, InsureDiseaseDTO> collect = insureDiseaseMatchDTOList.stream().collect(Collectors.toMap(InsureDiseaseDTO::getInsureIllnessCode, Function.identity(), (k1, k2) -> k1));
        for (Map<String, Object> map : downLoadInfo) {
            if (collect.isEmpty() || !collect.containsKey(map.get("aka120").toString())) {
                InsureDiseaseDTO insureDiseaseDTO = new InsureDiseaseDTO();
                insureDiseaseDTO.setId(SnowflakeUtils.getId()); // 主键
                insureDiseaseDTO.setHospCode(insureItemDTO.getHospCode()); // 医院编码
                insureDiseaseDTO.setInsureRegCode(insureItemDTO.getInsureRegCode()); // 医保机构注册编码
                insureDiseaseDTO.setInsureIllnessId(null); // 中心疾病ID
                insureDiseaseDTO.setInsureIllnessCode(map.get("aka120").toString()); // 中心疾病编码
                insureDiseaseDTO.setInsureIllnessName(map.get("AKA123").toString()); // 中心疾病名称
                insureDiseaseDTO.setIcd10(map.get("aka120").toString()); // ICD编码
                insureDiseaseDTO.setPym(map.get("AKA628").toString()); // 拼音码
                insureDiseaseDTO.setWbm(map.get("AKA066").toString()); // 五笔码
                insureDiseaseDTO.setLoseDate(null); //失效日期
                insureDiseaseDTO.setTakeDate(null); // 生效日期
                insureDiseaseDTO.setRemark(map.get("AAE013").toString()); // 备注
                insureDiseaseDTO.setCrteId(insureItemDTO.getCrteId()); // 创建人ID
                insureDiseaseDTO.setCrteName(insureItemDTO.getCrteName()); // 创建人姓名
                insureDiseaseDTO.setCrteTime(DateUtils.getNow()); //创建时间
                insureDiseaseDTOList.add(insureDiseaseDTO);
            }
        }
        int batchCount = 1000; // 定义分批处理的数据大小
        if (!ListUtils.isEmpty(insureDiseaseDTOList)) {
            if (insureDiseaseDTOList.size() >= 1000) {
                List<InsureDiseaseDTO> diseaseDTOList = new ArrayList<>();
                for (int i = 0; i < insureDiseaseDTOList.size(); i++) {
                    diseaseDTOList.add(insureDiseaseDTOList.get(i));
                    if (batchCount == diseaseDTOList.size() || i == insureDiseaseDTOList.size() - 1) {
                        insureDiseaseDAO.insertDisease(diseaseDTOList);
                        diseaseDTOList.clear();
                    }
                }
            } else {
                insureDiseaseDAO.insertDisease(insureDiseaseDTOList);
            }
        }
    }

    /**
     * @Method queryInsureItemAll
     * @Desrciption :查询，调用长沙医保返回回来的项目数据
     * @Param insureItemDTO：itemName:项目名称，itemCode编码
     * @Author fuhui
     * @Date 2021/1/27 11:17
     * @Return
     **/
    @Override
    public PageDTO queryInsureItemAll(InsureItemDTO insureItemDTO) {
        PageHelper.startPage(insureItemDTO.getPageNo(), insureItemDTO.getPageSize());
        InsureDiseaseDTO insureDiseaseDTO = new InsureDiseaseDTO();
        insureDiseaseDTO.setHospCode(insureItemDTO.getHospCode());
        insureDiseaseDTO.setInsureRegCode(insureItemDTO.getInsureRegCode());
        insureDiseaseDTO.setKeyword(insureItemDTO.getKeyword());
        insureDiseaseDTO.setStartDate(insureItemDTO.getStartDate());
        List<InsureDiseaseDTO> insureDiseaseDTOList = insureDiseaseDAO.queryAll(insureDiseaseDTO);
        return PageDTO.of(insureDiseaseDTOList);
    }

    /**
     * @Method handMatch
     * @Desrciption 处理手动匹配
     * 1.处理手动匹配时，需要确定该项目，材料，疾病，药品是否已经做了匹配
     * @Param map
     * @Author fuhui
     * @Date 2021/1/27 14:25
     * @Return
     **/
    @Override
    public Boolean insertHandMatch(Map<String, Object> map) {
        map.put("id", SnowflakeUtils.getId());
        map.put("crteTime", DateUtils.getNow());
        if ("05".equals(map.get("downLoadType").toString()) && null != map.get("downLoadType").toString()) {
            List<InsureDiseaseMatchDTO> insureDiseaseMatchDTOList = insureDiseaseMatchDAO.queryDiseaseIsMatch(map);
            if (!ListUtils.isEmpty(insureDiseaseMatchDTOList)) {
                for (InsureDiseaseMatchDTO insureDiseaseMatchDTO : insureDiseaseMatchDTOList) {
                    throw new AppException("医院端的" + insureDiseaseMatchDTO.getHospIllnessName() + "已经匹配了医保中心的:" + insureDiseaseMatchDTO.getInsureIllnessName());
                }
            }
            return insureDiseaseMatchDAO.insertDiseaseMatchDAO(map);
        } else {
            List<InsureItemMatchDTO> insureItemMatchDTOList = insureItemMatchDAO.queryItemIsMatch(map);
            if (!ListUtils.isEmpty(insureItemMatchDTOList)) {
                for (InsureItemMatchDTO insureItemMatchDTO : insureItemMatchDTOList) {
                    throw new AppException("医院端的" + insureItemMatchDTO.getHospItemName() + "已经匹配了医保中心的:" + insureItemMatchDTO.getInsureItemName());
                }
            }
            return insureItemMatchDAO.insertHandMatch(map);
        }
    }

    /**
     * @Method autoMatch()
     * @Desrciption 1.长沙医保匹配：根据名称进行匹配
     * 2.首先确定要匹配类型：药品 疾病，项目（包含材料）
     * 3.查询出医院端要匹配的数据
     * 4.查询从医保下载回来的数据
     * 5.根据名称进行匹配，名称可能存在多个，如果匹配到第一个则跳出
     * 6.还需要判断对应的数据 是否已经做了匹配
     * @Param
     * @Author fuhui
     * @Date 2021/1/27 10:02
     * @Return
     **/
    @Override
    public Boolean insertAutoMatch(Map<String, Object> map) {
        String itemCode = map.get("matchCode").toString();   // 匹配项目
        String insureRegCode = map.get("regCode").toString(); // 医疗机构编码
        String hospCode = map.get("hospCode").toString(); // 医院编码
        InsureItemDTO insureItemDTO = new InsureItemDTO();
        insureItemDTO.setHospCode(map.get("hospCode").toString());
        insureItemDTO.setInsureRegCode(insureRegCode);
        if (Constants.XMLB.YP.equals(itemCode)) {
            BaseDrugDTO baseDrugDTO = new BaseDrugDTO();
            baseDrugDTO.setHospCode(hospCode);
            baseDrugDTO.setIsValid("1");
            map.put("baseDrugDTO", baseDrugDTO);
            WrapperResponse<List<BaseDrugDTO>> listWrapperResponse = baseDrugService.queryAll(map);
            List<BaseDrugDTO> baseDrugDTOList = listWrapperResponse.getData();  // 医院端药品
            insureItemDTO.setItemType("1");
            List<InsureItemDTO> insureItemDTOList = insureItemDAO.queryInsureItemAll(insureItemDTO);
            if (ListUtils.isEmpty(baseDrugDTOList) || ListUtils.isEmpty(insureItemDTOList)) {
                throw new AppException("自动匹配时，医院端或者医保下载回来的药品数据集为空");
            }
            List<InsureItemMatchDTO> insureItemMatchDTOList = handDrugAutoMatch(baseDrugDTOList, insureItemDTOList, map);
            if (!ListUtils.isEmpty(insureItemDTOList)) {
                insureItemMatchDAO.insertMatchItem(insureItemMatchDTOList);
            }

        } else if (Constants.XMLB.CL.equals(itemCode)) {
            BaseMaterialDTO baseMaterialDTO = new BaseMaterialDTO();
            baseMaterialDTO.setHospCode(hospCode);
            baseMaterialDTO.setIsValid(Constants.SF.S);
            map.put("baseMaterialDTO", baseMaterialDTO);
            WrapperResponse<List<BaseMaterialDTO>> listWrapperResponse2 = baseMaterialService.queryAll(map);
            List<BaseMaterialDTO> baseMaterialDTOList = listWrapperResponse2.getData(); // 医院端材料
            insureItemDTO.setItemType("2");
            List<InsureItemDTO> insureItemDTOList = insureItemDAO.queryInsureItemAll(insureItemDTO);
            if (ListUtils.isEmpty(insureItemDTOList) || ListUtils.isEmpty(insureItemDTOList)) {
                throw new AppException("自动匹配时，医院端或者医保下载回来的材料数据集为空");
            }
            List<InsureItemMatchDTO> insureItemMatchDTOList = handMaterialAutoMatch(baseMaterialDTOList, insureItemDTOList, map);
            if (!ListUtils.isEmpty(insureItemMatchDTOList)) {
                insureItemMatchDAO.insertMatchItem(insureItemMatchDTOList);
            }

        } else if (Constants.XMLB.XM.equals(itemCode)) {
            BaseItemDTO baseItemDTO = new BaseItemDTO();
            baseItemDTO.setIsValid(Constants.SF.S);
            baseItemDTO.setHospCode(hospCode);
            map.put("baseItemDTO", baseItemDTO);
            WrapperResponse<List<BaseItemDTO>> listWrapperResponse1 = baseItemService.queryAll(map);
            List<BaseItemDTO> baseItemDTOList = listWrapperResponse1.getData(); // 医院端项目
            insureItemDTO.setItemType("2");
            List<InsureItemDTO> insureItemDTOList = insureItemDAO.queryInsureItemAll(insureItemDTO);
            List<InsureItemMatchDTO> insureItemMatchDTOList = handItemAutoMatch(baseItemDTOList, insureItemDTOList, map);
            if (ListUtils.isEmpty(insureItemDTOList) || ListUtils.isEmpty(insureItemDTOList)) {
                throw new AppException("自动匹配时，医院端或者医保下载回来的项目数据集为空");
            }
            if (!ListUtils.isEmpty(insureItemMatchDTOList)) {
                insureItemMatchDAO.insertMatchItem(insureItemMatchDTOList);
            }
        } else {
            InsureDiseaseMatchDTO insureDiseaseMatchDTO = new InsureDiseaseMatchDTO();
            insureDiseaseMatchDTO.setHospCode(hospCode);
            insureDiseaseMatchDTO.setInsureRegCode(insureRegCode);
            insureDiseaseMatchDTO.setIsMatch("0");
            insureDiseaseMatchDTO.setAuditCode("0");
            List<Map<String, Object>> list = insureDiseaseMatchDAO.queryUnMacthAllPage(insureDiseaseMatchDTO);
            if (ListUtils.isEmpty(list)) {
                throw new AppException("请进行项目生成！");
            }

            InsureDiseaseDTO insureDiseaseDTO = new InsureDiseaseDTO();
            insureDiseaseDTO.setHospCode(map.get("hospCode").toString());
            insureDiseaseDTO.setInsureRegCode(insureRegCode);
            List<InsureDiseaseDTO> insureDiseaseDTOList = insureDiseaseDAO.queryAll(insureDiseaseDTO); // 医保中心端的疾病
            if (ListUtils.isEmpty(insureDiseaseDTOList)) {
                throw new AppException("自动匹配时，医保端疾病数据集为空!");
            }
            List<InsureDiseaseMatchDTO> diseaseMatchDTOList = handDiseaseAutoMatch(list, insureDiseaseDTOList);

            while (diseaseMatchDTOList.size() > 1000) {
                List<InsureDiseaseMatchDTO> updateList = diseaseMatchDTOList.subList(0, 1000);
                insureDiseaseMatchDAO.updateInsureDiseaseMatchS(updateList);
                diseaseMatchDTOList.removeAll(diseaseMatchDTOList.subList(0, 1000));
            }
            insureDiseaseMatchDAO.updateInsureDiseaseMatchS(diseaseMatchDTOList);
        }
        return true;
    }

    private Boolean addDownloadByHNS(InsureItemMatchDTO insureItemMatchDTO) {
        Map<String, String> map = new HashMap();
        String firstrow = "0";
        String lastrow = "100000";
        if ("1".equals(insureItemMatchDTO.getDownloadType())) {
            map.put("condition", "bs_medi");
        } else {
            map.put("condition", "bs_item");
        }
        map.put("hospCode", insureItemMatchDTO.getHospCode());
        map.put("hospitalId", insureItemMatchDTO.getRegCode());
        map.put("centerId", insureItemMatchDTO.getInsureRegCode());
        map.put("insureRegCode", insureItemMatchDTO.getInsureRegCode());
        map.put("firstrow", firstrow);
        map.put("lastrow", lastrow);
        Map<String, Object> bsMedi = transpond.to(insureItemMatchDTO.getHospCode(), insureItemMatchDTO.getInsureRegCode(), Constant.FUNCTION.BIZC110118, map);
//        //对返回的数据进行处理
        handleHNSDownload(bsMedi, insureItemMatchDTO);
        return false;
    }


    /**
     * @Method handDrugAutoMatch
     * @Desrciption 处理材料自动匹配
     * @Param baseDrugDTOList:医院端的材料集合
     * insureItemDTOList：从医保下载回来的材料集合
     * map：封装参数信息医院编码，医疗机构编码 创建人，id，时间
     * @Author fuhui
     * @Date 2021/1/27 21:12
     * @Return
     **/
    private List<InsureItemMatchDTO> handMaterialAutoMatch(List<BaseMaterialDTO> baseMaterialDTOList, List<InsureItemDTO> insureItemDTOList, Map map) {
        String userId = map.get("userId").toString();
        String userName = map.get("userName").toString();
        List<InsureItemMatchDTO> insureItemMatchDTOList = new ArrayList<>();
        Map<String, InsureItemDTO> collect = insureItemDTOList.stream().collect(Collectors.toMap(InsureItemDTO::getItemName, Function.identity(), (k1, k2) -> k1));
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
                insureItemMatchDTO.setAuditCode(Constants.SF.S); // 审核状态代码
                insureItemMatchDTO.setIsMatch(Constants.SF.S); // 是否匹配
                insureItemMatchDTO.setIsTrans(null); // 是否传输
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
        InsureItemMatchDTO insureItemMatchDTO = new InsureItemMatchDTO();
        insureItemMatchDTO.setHospCode(map.get("hospCode").toString());
        insureItemMatchDTO.setInsureRegCode(map.get("regCode").toString());
        insureItemMatchDTO.setIsMatch(Constants.SF.S);
        insureItemMatchDTO.setHospItemType(Constants.XMLB.CL);
        /**
         *      1.查询已经匹配的药品信息
         *      2.如果匹配药品为空，则直接跳出不做判断，返回上一步 匹配好的数据
         *      3.如果不为空，则需要把刚刚自动匹配好的数据和以前匹配的数据做对比，防止重复插入数据
         */
        List<InsureItemMatchDTO> matchDTOList = insureItemMatchDAO.queryPageOrAll(insureItemMatchDTO);
        if (!ListUtils.isEmpty(matchDTOList)) {
            List<InsureItemMatchDTO> dtoList = new ArrayList<>();
            Map<String, InsureItemMatchDTO> diseaseMatchDTOMap = matchDTOList.stream().collect(Collectors.toMap(InsureItemMatchDTO::getHospItemId, Function.identity(), (k1, k2) -> k1));
            if (!ListUtils.isEmpty(insureItemMatchDTOList)) {
                for (InsureItemMatchDTO itemMatchDTO : matchDTOList) {
                    if (!diseaseMatchDTOMap.isEmpty() && !diseaseMatchDTOMap.containsKey(itemMatchDTO.getHospItemId())) {
                        dtoList.add(itemMatchDTO);
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
     * @Desrciption 处理项目自动匹配
     * @Param baseDrugDTOList:医院端的药品集合
     * insureItemDTOList：从医保下载回来的项目集合
     * map：封装参数信息医院编码，医疗机构编码 创建人，id，时间
     * @Author fuhui
     * @Date 2021/1/27 21:12
     * @Return
     **/
    private List<InsureItemMatchDTO> handItemAutoMatch(List<BaseItemDTO> baseItemDTOList, List<InsureItemDTO> insureItemDTOList, Map map) {
        String userId = map.get("userId").toString();
        String userName = map.get("userName").toString();
        List<InsureItemMatchDTO> insureItemMatchDTOList = new ArrayList<>();
        Map<String, InsureItemDTO> collect = insureItemDTOList.stream().collect(Collectors.toMap(InsureItemDTO::getItemName, Function.identity(), (k1, k2) -> k1));
        for (BaseItemDTO baseItemDTO : baseItemDTOList) {
            if (!collect.isEmpty() && collect.containsKey(baseItemDTO.getName())) {
                InsureItemMatchDTO insureItemMatchDTO = new InsureItemMatchDTO();
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
                insureItemMatchDTO.setAuditCode(Constants.SF.S); // 审核状态代码
                insureItemMatchDTO.setIsMatch(Constants.SF.S); // 是否匹配
                insureItemMatchDTO.setIsTrans(null); // 是否传输
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
        InsureItemMatchDTO insureItemMatchDTO = new InsureItemMatchDTO();
        insureItemMatchDTO.setHospCode(map.get("hospCode").toString());
        insureItemMatchDTO.setInsureRegCode(map.get("regCode").toString());
        insureItemMatchDTO.setIsMatch(Constants.SF.S);
        insureItemMatchDTO.setHospItemType(Constants.XMLB.XM);
        /**
         *      1.查询已经匹配的材料信息
         *      2.如果匹配材料为空，则直接跳出不做判断，返回上一步 匹配好的数据
         *      3.如果不为空，则需要把刚刚自动匹配好的数据和以前匹配的数据做对比，防止重复插入数据
         */
        List<InsureItemMatchDTO> matchDTOList = insureItemMatchDAO.queryPageOrAll(insureItemMatchDTO);
        if (!ListUtils.isEmpty(matchDTOList)) {
            List<InsureItemMatchDTO> dtoList = new ArrayList<>();
            Map<String, InsureItemMatchDTO> diseaseMatchDTOMap = matchDTOList.stream().collect(Collectors.toMap(InsureItemMatchDTO::getHospItemId, Function.identity(), (k1, k2) -> k1));
            if (!ListUtils.isEmpty(insureItemMatchDTOList)) {
                for (InsureItemMatchDTO itemMatchDTO : matchDTOList) {
                    if (!diseaseMatchDTOMap.isEmpty() && !diseaseMatchDTOMap.containsKey(itemMatchDTO.getHospItemId())) {
                        dtoList.add(itemMatchDTO);
                    }
                }
            }
            return dtoList;
        } else {
            return insureItemMatchDTOList;
        }
    }

    /**
     * @param
     * @Method cancelMatch
     * @Desrciption 1.首先根据 downLoadType 标志来区分：
     * 2.是取消疾病匹配，还是项目匹配
     * @Param
     * @Author fuhui
     * @Date 2021/1/30 14:10
     * @Return
     */
    @Override
    public Boolean deleteInsureMatch(Map<String, Object> map) {
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        /* map.put("code", "UNIFIED_PAY");
        SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(map).getData();*/

        // 根据医保机构编码查询医保配置信息
        InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
        configDTO.setHospCode(MapUtils.get(map, "hospCode")); //医院编码
        configDTO.setRegCode(insureRegCode); // 医保注册编码
        configDTO.setIsValid(Constants.SF.S); // 是否有效
        List<InsureConfigurationDTO> configurationDTOList = insureConfigurationDAO.findByCondition(configDTO);
        if (ListUtils.isEmpty(configurationDTOList)) {
            throw new RuntimeException("未找到医保机构，请先配置医保信息！");
        }
        InsureConfigurationDTO insureConfigurationDTO = configurationDTOList.get(0);
        // 获取该医保配置是否走统一支付平台，1走，0/null不走
        String isUnifiedPay = insureConfigurationDTO.getIsUnifiedPay();
//        if (sys != null && Constants.SF.S.equals(sys.getValue())) {
        if (StringUtils.isNotEmpty(isUnifiedPay) && "1".equals(isUnifiedPay)) {
            insureUnifiedPayRestService.UP_3302(map).getData();
        } else {
            if ("isDisease".equals(map.get("downLoadType").toString())) {
                return insureDiseaseMatchDAO.deleteInsureMatch(map);
            }
            if ("isItem".equals(map.get("downLoadType").toString())) {
                return insureItemMatchDAO.deleteInsureMatch(map);
            }
        }

        return true;
    }

    /**
     * @param insureItemDTO@Method uploadItem
     * @Desrciption 医保统一支付平台;项目对照上传
     * @Param insureItemDTO
     * @Author fuhui
     * @Date 2021/3/15 17:21
     * @Return Boolean
     */
    @Override
    public Integer updateIsTrans(InsureItemDTO insureItemDTO) {
        Map<String, Object> map = new HashMap<>();
//        map.put("code", "UNIFIED_PAY");
        map.put("startDate", insureItemDTO.getStartDate());
        map.put("endDate", insureItemDTO.getEndDate());
        map.put("crteId", insureItemDTO.getCrteId());
        map.put("crteName", insureItemDTO.getCrteName());
        map.put("hospItemType", insureItemDTO.getHospItemType());
        map.put("hospCode", insureItemDTO.getHospCode());  // 医院编码
        map.put("insureRegCode", insureItemDTO.getInsureRegCode());

        // 根据医保机构编码查询医保配置信息
        InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
        configDTO.setHospCode(insureItemDTO.getHospCode()); //医院编码
        configDTO.setRegCode(insureItemDTO.getInsureRegCode()); // 医保注册编码
        configDTO.setIsValid(Constants.SF.S); // 是否有效
        List<InsureConfigurationDTO> configurationDTOList = insureConfigurationDAO.findByCondition(configDTO);
        if (ListUtils.isEmpty(configurationDTOList)) {
            throw new RuntimeException("未找到医保机构，请先配置医保信息！");
        }
        InsureConfigurationDTO insureConfigurationDTO = configurationDTOList.get(0);
        // 获取该医保配置是否走统一支付平台，1走，0/null不走
        String isUnifiedPay = insureConfigurationDTO.getIsUnifiedPay();

        Integer size = 0;
       /* SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(map).getData();
        if (sys != null && Constants.SF.S.equals(sys.getValue())) {*/
        if (StringUtils.isNotEmpty(isUnifiedPay) && "1".equals(isUnifiedPay)) {
            Map<String, Object> resultMap = insureUnifiedPayRestService.uploadItem(map).getData();
            List<InsureItemMatchDTO> insureItemDTOList = MapUtils.get(resultMap, "insureItemDTOList");
            size = insureItemDTOList.size();
            if (!ListUtils.isEmpty(insureItemDTOList)) {
                for (InsureItemMatchDTO itemMatchDTO : insureItemDTOList) {
                    itemMatchDTO.setIsTrans(Constants.SF.S);
                    itemMatchDTO.setAuditCode(Constants.SF.S);
                }
                insureItemMatchDAO.modifyInsureItem(insureItemDTOList);
            }
        }
        return size;
    }

    /**
     * @param insureItemMatchDTO
     * @Method deleteInsureItemMatch
     * @Desrciption 医保通一支付平台：删除匹配数据
     * @Param 如果已經審核，則調用對照撤銷功能
     * @Author fuhui
     * @Date 2021/3/28 12:50
     * @Return
     */
    @Override
    public Boolean deleteInsureItemMatch(InsureItemMatchDTO insureItemMatchDTO) {
        String insureRegCode = insureItemMatchDTO.getInsureRegCode();
        String hospCode = insureItemMatchDTO.getHospCode();

        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", insureItemMatchDTO.getHospCode());  // 医院编码
       /*map.put("code", "UNIFIED_PAY");

        SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(map).getData();*/
        // 根据医保机构编码查询医保配置信息
        InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
        configDTO.setHospCode(hospCode); //医院编码
        configDTO.setRegCode(insureRegCode); // 医保注册编码
        configDTO.setIsValid(Constants.SF.S); // 是否有效
        List<InsureConfigurationDTO> configurationDTOList = insureConfigurationDAO.findByCondition(configDTO);
        if (ListUtils.isEmpty(configurationDTOList)) {
            throw new RuntimeException("未找到医保机构，请先配置医保信息！");
        }
        InsureConfigurationDTO insureConfigurationDTO = configurationDTOList.get(0);
        // 获取该医保配置是否走统一支付平台，1走，0/null不走
        String isUnifiedPay = insureConfigurationDTO.getIsUnifiedPay();

//        if (sys != null && Constants.SF.S.equals(sys.getValue())) {
        if (StringUtils.isNotEmpty(isUnifiedPay) && "1".equals(isUnifiedPay)) {
            InsureItemMatchDTO itemMatchDTO = insureItemMatchDAO.selectInsureItemMatch(insureItemMatchDTO);
            if (itemMatchDTO != null&&Constants.SF.S.equals(itemMatchDTO.getAuditCode())) {
                map.put("itemMatchDTO", itemMatchDTO);
                map.put("insureRegCode", itemMatchDTO.getInsureRegCode());
                insureUnifiedPayRestService.UP_3302(map);
            }
            return insureItemMatchDAO.deleteUnifiedInsureItemMatch(insureItemMatchDTO);
        }
        return insureItemMatchDAO.deleteInsureItemMatch(insureItemMatchDTO);

    }

    /**
     * @param itemMatchDTO
     * @Method updateInsureItemMatch
     * @Desrciption 修改医保匹配项目
     * @Param
     * @Author fuhui
     * @Date 2021/4/8 16:50
     * @Return
     */
    @Override
    public Boolean updateInsureItemMatch(InsureItemMatchDTO itemMatchDTO) {
        return insureItemMatchDAO.updateInsureItemMatch(itemMatchDTO);
    }

    /**
     * @param insureItemMatchDTO
     * @Method updateUplaodInsureItem
     * @Desrciption 项目对照撤销
     * @Param
     * @Author fuhui
     * @Date 2021/4/28 10:23
     * @Return
     */
    @Override
    public Integer updateUplaodInsureItem(InsureItemMatchDTO insureItemMatchDTO) {
        Map<String, Object> map = new HashMap<>();
        map.put("hospCode", insureItemMatchDTO.getHospCode());
        map.put("insureRegCode", insureItemMatchDTO.getInsureRegCode());
        map.put("hospItemType", insureItemMatchDTO.getHospItemType());
        Map<String, Object> resultMap = insureUnifiedPayRestService.UP_3302(map).getData();
        List<InsureItemMatchDTO> insureItemMatchDTOList = MapUtils.get(resultMap, "insureItemMatchDTOList");
        if (!ListUtils.isEmpty(insureItemMatchDTOList)) {
            insureItemMatchDAO.modifyInsureItem(insureItemMatchDTOList);
        }
        return insureItemMatchDTOList.size();
    }


    /**
     * @Method updateInsureItemMatchInfo
     * @Desrciption 导入医保匹配数据
     * @Param
     * @Author 廖继广
     * @Date 2021/05/20 05:20
     * @Return
     **/
    @Override
    public Boolean updateInsureItemMatchInfo(Map map) {
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        String hospCode = MapUtils.get(map, "hospCode");
        List<List<String>> resultList = (List<List<String>>) map.get("resultList");
        List<Map<String, String>> inputList = new ArrayList<>();

        // 封装数据
        int i = 1;
        int[] numbers = {0, 1, 2, 3};
        for (List<String> item : resultList) {
            Map<String, String> inputMap = new HashMap<>();
            for (int number : numbers) {
                String key = item.get(number);
                if (StringUtils.isEmpty(key)) {
                    throw new AppException("第" + (i + 1) + "行的" + (number + 1) + "列数据为空");
                }
                this.createMapData(number, key, inputMap);
            }
            inputMap.put("insureRegCode", insureRegCode);
            inputMap.put("hospCode", hospCode);
            inputList.add(inputMap);
            i++;
        }

        // 批量更新
        if (ListUtils.isEmpty(inputList)) {
            throw new AppException("系统未获取到数据");
        }
        return insureItemMatchDAO.updateInsureItemMatchInfo(inputList) > 0;
    }

    /**
     * @Method insertInsureItemInfos
     * @Desrciption 批量查询医保项目数据（下载）
     * @Param
     * @Author 廖继广
     * @Date 2021/05/25 05:20
     * @Return
     **/
    @Override
    public Map<String, Object> insertInsureItemInfos(Map<String, String> map) {
        Map<String, Object> sysParamMap = new HashMap<>();
        String hospCode = map.get("hospCode");
        String insureRegCode = map.get("insureRegCode");
        String crteId = map.get("userId");
        String crteName = map.get("userName");
        sysParamMap.put("hospCode", hospCode);
        sysParamMap.put("code", insureRegCode);
        WrapperResponse<SysParameterDTO> wr = sysParameterService_consumer.getParameterByCode(sysParamMap);
        if (wr == null || wr.getData() == null || wr.getData().getValue() == null) {
            throw new AppException("请配置系统参数(" + insureRegCode + ")");
        }
        JSONObject JsonObject = JSONObject.parseObject(wr.getData().getValue()).getJSONObject("downloadParams");
        if (JsonObject == null) {
            throw new AppException("系统参数【" + insureRegCode + "】未配置下载参数(downloadParams)");
        }

        /**
         * 参数描述
         *
         * size : 单次最大下载条数(建议500条，不超过1000条)
         * type : 下载对照类型(比如 type = 'version',表示按版本号下载）
         * firstVersionId : 起始版本号ID
         * lastVersionId : 结束版本号ID
         * firstRow : 起始条数
         * lastRow : 结束条数
         * condition : 下载类型（项目、药品、材料）
         * insureResultList : 医保下载的所有数据集合
         * totalSize : 下载总条数
         * downCount : 循环次数
         */
        int size = Integer.parseInt(JsonObject.getString("size"));
        String type = JsonObject.getString("type");
        String firstVersionId = JsonObject.getString("firstVersionId");
        String lastVersionId = JsonObject.getString("lastVersionId");
        int firstRow = Integer.parseInt(JsonObject.getString("firstRow"));
        int lastRow = Integer.parseInt(JsonObject.getString("lastRow"));
        String condition = JsonObject.getString("condition");
        String onceFind = JsonObject.getString("onceFind");
        List<Map<String, Object>> insureResultList = new ArrayList<>();
        int totalSize = lastRow - firstRow + 1;
        int downCount = 1;
        if (totalSize > size) {
            downCount = (int) Math.ceil(totalSize / size);
        }

        for (int i = 0; i < downCount; i++) {
            try {
                Map<String, Object> selectMap = new HashMap<>();
                selectMap.put("size", String.valueOf(size));
                selectMap.put("firstVersionId", firstVersionId);
                selectMap.put("lastVersionId", lastVersionId);
                selectMap.put("type", type);
                selectMap.put("firstRow", String.valueOf(firstRow + size * i));
                selectMap.put("lastRow", String.valueOf(firstRow + (i + 1) * size - 1));
                selectMap.put("condition", condition);
                selectMap.put("hospCode", hospCode);
                selectMap.put("insureRegCode", insureRegCode);
                selectMap.put("onceFind", onceFind);
                List<Map<String, Object>> resultInfo = transpond.to(hospCode, insureRegCode, Constant.FUNCTION.BIZC110118, selectMap);
                if (ListUtils.isEmpty(resultInfo)) {
                    continue;
                } else {
                    insureResultList.addAll(resultInfo);
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (ListUtils.isEmpty(insureResultList)) {
            throw new AppException("医保接口获取下载行数为空（" + firstRow + " - " + lastRow + ")");
        }
        return this.updateInsureItemInfo(hospCode, insureRegCode, condition, crteId, crteName, insureResultList);
    }

    /**
     * 医保项目数据更新
     *
     * @param hospCode
     * @param insureRegCode
     * @param condition
     * @param insureResultList
     * @return
     */
    private Map<String, Object> updateInsureItemInfo(String hospCode, String insureRegCode, String condition, String crteId, String crteName, List<Map<String, Object>> insureResultList) {
        Map<String, Object> resultMap = new HashMap<>();
        List<InsureItemDTO> firstArrayList = new ArrayList<>();
        for (Map<String, Object> insureMap : insureResultList) {
            InsureItemDTO insureItemDTO = new InsureItemDTO();
            insureItemDTO.setId(SnowflakeUtils.getId());
            insureItemDTO.setHospCode(hospCode);
            insureItemDTO.setInsureRegCode(insureRegCode);
            insureItemDTO.setItemMark(MapUtils.get(insureMap, "stat_type"));
            insureItemDTO.setDownLoadType(condition);
            insureItemDTO.setVer(MapUtils.get(insureMap, "version_id"));
            insureItemDTO.setVerName(MapUtils.get(insureMap, "version_id"));
            insureItemDTO.setHospItemName(MapUtils.get(insureMap, "hosp_name"));
            insureItemDTO.setHospItemCode(MapUtils.get(insureMap, "hosp_code"));
            insureItemDTO.setItemCode(MapUtils.get(insureMap, "item_code"));
            insureItemDTO.setItemName(MapUtils.get(insureMap, "item_name"));
            insureItemDTO.setItemType(MapUtils.get(insureMap, "match_type"));
            insureItemDTO.setItemDosage(MapUtils.get(insureMap, "model"));
            insureItemDTO.setItemSpec(MapUtils.get(insureMap, "standard"));
            insureItemDTO.setItemPrice(BigDecimalUtils.convert(MapUtils.get(insureMap, "hosp_price")));
            insureItemDTO.setItemUnitCode(MapUtils.get(insureMap, "unit"));
            insureItemDTO.setProd(MapUtils.get(insureMap, "factory"));
            insureItemDTO.setDeductible(MapUtils.get(insureMap, "self_scale"));
            insureItemDTO.setDirectory(MapUtils.get(insureMap, "staple_flag"));

            // add 时间数据处理 by liaojiguang on 2021-08-13
            String auditDate = MapUtils.get(insureMap, "audit_date").toString();
            String expireDate = MapUtils.get(insureMap, "expire_date").toString();
            if (StringUtils.isNotEmpty(auditDate)) {
                insureItemDTO.setTakeDate(DateUtils.parse(auditDate, DateUtils.Y_M_DH_M_S));
            }
            if (StringUtils.isNotEmpty(expireDate)) {
                insureItemDTO.setLoseDate(DateUtils.parse(expireDate, DateUtils.Y_M_DH_M_S));
            }
            insureItemDTO.setPym(MapUtils.get(insureMap, "code_py"));
            insureItemDTO.setWbm(MapUtils.get(insureMap, "code_wb"));
            insureItemDTO.setIsValid(MapUtils.get(insureMap, "audit_flag"));
            insureItemDTO.setCrteId(crteId);
            insureItemDTO.setCrteName(crteName);
            insureItemDTO.setCrteTime(DateUtils.getNow());
            firstArrayList.add(insureItemDTO);
        }

        if (!ListUtils.isEmpty(firstArrayList)) {
            // 清除当前医保的下载数据
            InsureItemDTO insureItemDTO = new InsureItemDTO();
            insureItemDTO.setHospCode(hospCode);
            insureItemDTO.setDownLoadType(condition);
            insureItemDTO.setInsureRegCode(insureRegCode);
            insureItemDAO.deleteInsureItemByRegCode(insureItemDTO);

            // 新下载数据插入
            insureItemDAO.insertInsureItem(firstArrayList);
        }
        return resultMap;
    }

    /**
     * @Menthod: queryLimitDrugList
     * @Desrciption: 查询医保限制级用药列表
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-07-19 11:42
     * @Return:
     */
    @Override
    public List<InsureItemMatchDTO> queryLimitDrugList(InsureItemMatchDTO insureItemMatchDTO) {
        /*// 已审核
        insureItemMatchDTO.setAuditCode(Constants.SHZT.SHWC);
        // 有效
        insureItemMatchDTO.setIsValid(Constants.SF.S);
        // 已匹配
        insureItemMatchDTO.setIsMatch(Constants.SF.S);
        // 已传输
        insureItemMatchDTO.setIsTrans(Constants.SF.S);
        // 属限制级用药
        insureItemMatchDTO.setLmtUserFlag(Constants.SF.S);*/

        // 查询已匹配、已传输的限制级用药列表
        List<InsureItemMatchDTO> insureItemMatchDTOS = insureItemMatchDAO.queryPageOrAll(insureItemMatchDTO);
        return insureItemMatchDTOS;
    }

    @Override
    public PageDTO queryUnMacthAllPage(InsureItemMatchDTO insureItemMatchDTO) {
        PageHelper.startPage(insureItemMatchDTO.getPageNo(), insureItemMatchDTO.getPageSize());
        List<Map<String, Object>> insureDiseaseDTOList = insureItemMatchDAO.queryUnMacthAllPage(insureItemMatchDTO);
        return PageDTO.of(insureDiseaseDTOList);
    }

    /**
     * @param itemMatchDTO
     * @return java.util.List<cn.hsa.module.insure.module.dto.InsureItemMatchDTO>
     * @method queryByHospItemId
     * @author wang'qiao
     * @date 2022/6/21 21:28
     * @description 根据hospItemId查询项目信息
     **/
    @Override
    public List<InsureItemMatchDTO> queryByHospItemId(InsureItemMatchDTO itemMatchDTO) {
        return insureItemMatchDAO.queryByHospItemIdIsItemId(itemMatchDTO);
    }

    /**
     * 封装导入的数据
     *
     * @param num
     * @param key
     * @param inputMap
     * @return
     */
    private Map<String, String> createMapData(int num, String key, Map<String, String> inputMap) {
        switch (num) {
            case 0:
                inputMap.put("hospItemCode", key);
                break;
            case 1:
                inputMap.put("hospItemName", key);
                break;
            case 2:
                inputMap.put("insureItemCode", key);
                break;
            case 3:
                inputMap.put("insureItemName", key);
                break;
            default:
                break;
        }
        return inputMap;
    }

    /**
     * @Method handDrugAutoMatch
     * @Desrciption 处理药品自动匹配
     * @Param baseDrugDTOList:医院端的药品集合
     * insureItemDTOList：从医保下载回来的药品集合
     * map：封装参数信息医院编码，医疗机构编码 创建人，id，时间
     * @Author fuhui
     * @Date 2021/1/27 21:12
     * @Return insureItemMatchDTOList:封装好已经匹配的数据集合
     **/
    private List<InsureItemMatchDTO> handDrugAutoMatch(List<BaseDrugDTO> baseDrugDTOList, List<InsureItemDTO> insureItemDTOList, Map map) {
        String userId = map.get("userId").toString();
        String userName = map.get("userName").toString();
        List<InsureItemMatchDTO> insureItemMatchDTOList = new ArrayList<>();
        Map<String, InsureItemDTO> collect = insureItemDTOList.stream().collect(Collectors.toMap(InsureItemDTO::getItemName, Function.identity(), (k1, k2) -> k1));
        for (BaseDrugDTO baseDrugDTO : baseDrugDTOList) {
            if (!collect.isEmpty() && collect.containsKey(baseDrugDTO.getUsualName())) {
                InsureItemMatchDTO insureItemMatchDTO = new InsureItemMatchDTO();
                insureItemMatchDTO.setId(SnowflakeUtils.getId());
                insureItemMatchDTO.setHospCode(baseDrugDTO.getHospCode()); //
                insureItemMatchDTO.setInsureRegCode(collect.get(baseDrugDTO.getUsualName()).getInsureRegCode());
                insureItemMatchDTO.setItemCode(collect.get(baseDrugDTO.getUsualName()).getItemMark()); // 项目类别标志
                insureItemMatchDTO.setMolssItemId(null); // 人社部药品id
                insureItemMatchDTO.setPqccItemId(baseDrugDTO.getInsureCode()); // 卫计委药品id
                insureItemMatchDTO.setHospItemName(baseDrugDTO.getUsualName()); //医院项目名称
                insureItemMatchDTO.setHospItemId(baseDrugDTO.getItemId()); // 医院项目id
                insureItemMatchDTO.setHospItemCode(baseDrugDTO.getCode()); // 医院项目编码
                insureItemMatchDTO.setHospItemType(Constants.XMLB.YP); // 医院项目类别
                insureItemMatchDTO.setHospItemSpec(baseDrugDTO.getSpec()); //  医院项目规格
                insureItemMatchDTO.setHospItemUnitCode(baseDrugDTO.getUnitCode()); // 医院项目单位
                insureItemMatchDTO.setHospItemPrepCode(baseDrugDTO.getPrepCode()); //医院项目剂型
                insureItemMatchDTO.setHospItemPrice(baseDrugDTO.getPrice()); //医院项目价格
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
                insureItemMatchDTO.setAuditCode(Constants.SF.S); // 审核状态代码
                insureItemMatchDTO.setIsMatch(Constants.SF.S); // 是否匹配
                insureItemMatchDTO.setIsTrans(null); // 是否传输
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
        InsureItemMatchDTO insureItemMatchDTO = new InsureItemMatchDTO();
        insureItemMatchDTO.setHospCode(map.get("hospCode").toString());
        insureItemMatchDTO.setInsureRegCode(map.get("insureRegCode").toString());
        insureItemMatchDTO.setIsMatch(Constants.SF.S);
        insureItemMatchDTO.setHospItemType(Constants.XMLB.YP);
        /**
         *      1.查询已经匹配的药品信息
         *      2.如果匹配药品为空，则直接跳出不做判断，返回上一步 匹配好的数据
         *      3.如果不为空，则需要把刚刚自动匹配好的数据和以前匹配的数据做对比，防止重复插入数据
         */
        List<InsureItemMatchDTO> matchDTOList = insureItemMatchDAO.queryPageOrAll(insureItemMatchDTO);
        if (!ListUtils.isEmpty(matchDTOList)) {
            List<InsureItemMatchDTO> dtoList = new ArrayList<>();
            Map<String, InsureItemMatchDTO> diseaseMatchDTOMap = matchDTOList.stream().collect(Collectors.toMap(InsureItemMatchDTO::getHospItemId, Function.identity(), (k1, k2) -> k1));
            if (!ListUtils.isEmpty(insureItemMatchDTOList)) {
                for (InsureItemMatchDTO itemMatchDTO : matchDTOList) {
                    if (!diseaseMatchDTOMap.isEmpty() && !diseaseMatchDTOMap.containsKey(itemMatchDTO.getHospItemId())) {
                        dtoList.add(itemMatchDTO);
                    }
                }
            }
            return dtoList;
        } else {
            return insureItemMatchDTOList;
        }
    }

    /**
     * @param uNMacthList
     * @param insureDiseaseDTOList
     * @param
     *
     * @Author 廖继广 update by 2021-08-23
     * @Date 2021/1/27 21:12
     * @Return
     */
    private List<InsureDiseaseMatchDTO> handDiseaseAutoMatch(List<Map<String, Object>> uNMacthList, List<InsureDiseaseDTO> insureDiseaseDTOList) {
        List<InsureDiseaseMatchDTO> insureDiseaseMatchDTOList = new ArrayList<>();
        for (Map<String, Object> uNMatchMap : uNMacthList) {
            for (InsureDiseaseDTO insureDiseaseDTO : insureDiseaseDTOList) {
                if (uNMatchMap.get("hospItemCode").equals(insureDiseaseDTO.getInsureIllnessCode())
                        || uNMatchMap.get("hospItemName").equals(insureDiseaseDTO.getInsureIllnessName())) {
                    InsureDiseaseMatchDTO insureDiseaseMatchDTO = new InsureDiseaseMatchDTO();
                    insureDiseaseMatchDTO.setId(uNMatchMap.get("id").toString());
                    insureDiseaseMatchDTO.setInsureRegCode(uNMatchMap.get("insureRegCode").toString());
                    insureDiseaseMatchDTO.setHospCode(insureDiseaseDTO.getHospCode());
                    insureDiseaseMatchDTO.setIsMatch("1");
                    insureDiseaseMatchDTO.setAuditCode("1");
                    insureDiseaseMatchDTO.setInsureIllnessCode(insureDiseaseDTO.getInsureIllnessCode());
                    insureDiseaseMatchDTO.setInsureIllnessName(insureDiseaseDTO.getInsureIllnessName());
                    insureDiseaseMatchDTOList.add(insureDiseaseMatchDTO);
                    break;
                }
            }
        }
        return insureDiseaseMatchDTOList;
    }


    Boolean handleDownload(Map<String, Object> finMatchCata, InsureItemMatchDTO insureItemMatchDTO) {
        if (finMatchCata.get("pageinfo") == null) {
            return true;
        }

        //查询出已录入匹配表的医院的信息
        InsureItemMatchDTO temp = new InsureItemMatchDTO();
        temp.setHospCode(insureItemMatchDTO.getHospCode());
        temp.setInsureRegCode(insureItemMatchDTO.getInsureRegCode());
        List<InsureItemMatchDTO> insureItemMatchDTOS = insureItemMatchDAO.queryPageOrAll(temp);


        if (!ListUtils.isEmpty(insureItemMatchDTOS)) {
            Map<String, InsureItemMatchDTO> collect = insureItemMatchDTOS.stream().collect(Collectors.toMap(InsureItemMatchDTO::getHospItemCode, Function.identity()));

            List itemList = (List) finMatchCata.get("pageinfo");
            List<InsureItemMatchDTO> items = new ArrayList<>();
            for (int i = 0; i < itemList.size(); i++) {
                Map tempMap = (Map) itemList.get(i);
                //拿到项目id
                String hospItemId = (String) tempMap.get("ake005");
                if (StringUtils.isNotEmpty(hospItemId)) {
                    //如果匹配上
                    if (collect.containsKey(hospItemId)) {
                        InsureItemMatchDTO item = collect.get(hospItemId);
                        // 中心项目名称
                        if (handNull(tempMap.get("bkc143"))) {
                            item.setInsureItemName((String) tempMap.get("bkc143"));
                        }
                        // 中心项目id
                        if (handNull(tempMap.get("bkc144"))) {
                            item.setInsureItemCode((String) tempMap.get("bkc144"));
                        }
                        // 生效日期
                        if (handNull(tempMap.get("aae030"))) {
                            try {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Date date = simpleDateFormat.parse((String) tempMap.get("aae030"));
                                item.setTakeDate(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                        // 失效日期
                        if (handNull(tempMap.get("aae031"))) {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = null;
                            try {
                                date = simpleDateFormat.parse((String) tempMap.get("aae031"));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            item.setLoseDate(date);
                        }
                        // 中心项目类别标志
                        if (handNull(tempMap.get("bpe001"))) {
                            String typeCode = (String) tempMap.get("bpe001");
                            if ("1".equals(typeCode)) {
                                item.setInsureItemType("11");//西药
                            } else if ("2".equals(typeCode)) {
                                item.setInsureItemType("12");//中成药
                            } else if ("3".equals(typeCode)) {
                                item.setInsureItemType("13");//中草药
                            } else {
                                item.setInsureItemType("2"); //项目
                            }
                        }
                        // 审核标识
                        if (handNull(tempMap.get("aae016"))) {
                            String shzt = (String) tempMap.get("aae016");
                            if ("1".equals(shzt)) {
                                item.setAuditCode("1"); //审核通过
                            } else if ("2".equals(shzt)) {
                                item.setAuditCode("3"); //审核未通过
                            } else {
                                item.setAuditCode("0"); //未审核
                            }
                        }
                        // 自付比例
                        if (handNull(tempMap.get("aka057"))) {
                            item.setDeductible((String) tempMap.get("aka057"));
                        }
                        //中心项目规格
                        if (handNull(tempMap.get("bkc139"))) {
                            item.setInsureItemSpec((String) tempMap.get("bkc139"));
                        }
                        //中心项目价格
                        if (handNull(tempMap.get("bkc140"))) {
                            BigDecimal bd = new BigDecimal((String) tempMap.get("bkc140"));
                            item.setInsureItemPrice(bd);
                        }
                        item.setIsMatch("1");
                        item.setIsTrans("1");
                        item.setIsValid("1");
                        items.add(item);
                    }
                }
            }
            if (!ListUtils.isEmpty(items)) {
                List list = new ArrayList<>();
                for (int i = 0; i < items.size(); i++) {
                    list.add(items.get(i));
                    if ((i + 1) % 1000 == 0) {
                        this.insureItemMatchDAO.updateInsureItemMatchS(list);
                        //重新初始
                        list = new ArrayList<>();
                    } else if (i == items.size() - 1 && i % 1000 != 0) {
                        this.insureItemMatchDAO.updateInsureItemMatchS(list);
                    }
                }
            }
        }
        return true;
    }

    Boolean handleHNSDownload(Map<String, Object> bsMedi, InsureItemMatchDTO insureItemMatchDTO) {
        if (bsMedi.get("pageinfo") == null) {
            return true;
        }

        //查询出已录入匹配表的医院的信息
        InsureItemMatchDTO temp = new InsureItemMatchDTO();
        temp.setHospCode(insureItemMatchDTO.getHospCode());
        temp.setInsureRegCode(insureItemMatchDTO.getInsureRegCode());
        List<InsureItemMatchDTO> insureItemMatchDTOS = insureItemMatchDAO.queryPageOrAll(temp);


        if (!ListUtils.isEmpty(insureItemMatchDTOS)) {
            Map<String, InsureItemMatchDTO> collect = insureItemMatchDTOS.stream().collect(Collectors.toMap(InsureItemMatchDTO::getHospItemCode, Function.identity()));

            List itemList = (List) bsMedi.get("pageinfo");
            List<InsureItemMatchDTO> items = new ArrayList<>();
            for (int i = 0; i < itemList.size(); i++) {
                Map tempMap = (Map) itemList.get(i);
                // 如果未审核 直接跳过
                if (Constants.SF.F.equals(tempMap.get("valid_flag"))) {
                    continue;
                }
                //拿到项目code
                String hospItemCode = (String) tempMap.get("medi_code");
                if (StringUtils.isNotEmpty(hospItemCode)) {
                    //如果匹配上
                    if (collect.containsKey(hospItemCode)) {
                        InsureItemMatchDTO item = collect.get(hospItemCode);
                        // 中心项目名称
                        if (handNull(tempMap.get("medi_name"))) {
                            item.setInsureItemName((String) tempMap.get("medi_name"));
                        }
                        // 中心项目code
                        item.setInsureItemName(hospItemCode);
                        // 中心项目id
                        if (handNull(tempMap.get("serial_medi"))) {
                            item.setInsureItemCode((String) tempMap.get("serial_medi"));
                        }
                        // 中心项目类别标志
                        if (handNull(tempMap.get("bpe001"))) {
                            String typeCode = (String) tempMap.get("bpe001");
                            if ("1".equals(typeCode)) {
                                item.setInsureItemType("11");//西药
                            } else if ("2".equals(typeCode)) {
                                item.setInsureItemType("12");//中成药
                            } else if ("3".equals(typeCode)) {
                                item.setInsureItemType("13");//中草药
                            } else {
                                item.setInsureItemType("2"); //项目
                            }
                        }
                        // 审核标识
                        if (handNull(tempMap.get("valid_flag"))) {
                            String shzt = (String) tempMap.get("valid_flag");
                            if ("1".equals(shzt)) {
                                item.setAuditCode("1"); //审核通过
                            } else if ("0".equals(shzt)) {
                                item.setAuditCode("3"); //审核未通过
                            } else {
                                item.setAuditCode("0"); //未审核
                            }
                        }
                        //中心项目剂型
                        if (handNull(tempMap.get("model"))) {
                            item.setInsureItemPrepCode((String) tempMap.get("model"));
                        }
                        //中心项目价格
                        if (handNull(tempMap.get("price"))) {
                            BigDecimal bd = new BigDecimal((String) tempMap.get("price"));
                            item.setInsureItemPrice(bd);
                        }
                        item.setIsMatch(Constants.SF.S);
                        item.setIsTrans(Constants.SF.S);
                        item.setIsValid(Constants.SF.S);
                        items.add(item);
                    }
                }
            }
            if (!ListUtils.isEmpty(items)) {
                List list = new ArrayList<>();
                for (int i = 0; i < items.size(); i++) {
                    list.add(items.get(i));
                    if ((i + 1) % 1000 == 0) {
                        this.insureItemMatchDAO.updateInsureItemMatchS(list);
                        //重新初始
                        list = new ArrayList<>();
                    } else if (i == items.size() - 1 && i % 1000 != 0) {
                        this.insureItemMatchDAO.updateInsureItemMatchS(list);
                    }
                }
            }
        }
        return true;
    }


    Boolean handNull(Object object) {
        return object != null;
    }

    /**
     * 根据医保注册编码和医院项目编码是否已存在,已存在不新增
     * @param dataList
     * @param type
     * @Author 医保开发二部-湛康
     * @Date 2022-04-12 15:22
     * @return void
     */
    private void checkListExist(List dataList, String type ,List<InsureItemMatchDTO> list) {
      List existList = new ArrayList();
      //药品
      if (ObjectUtil.isNotEmpty(type) && type.equals("drug")) {
        for (int i = 0; i < dataList.size() ; i++) {
          BaseDrugDTO drug = (BaseDrugDTO)dataList.get(i);
          for (InsureItemMatchDTO dto : list) {
            //判断医保注册编码和医院项目编码是否已存在,已存在不新增
            if (ObjectUtil.isEmpty(drug.getCode())){
              existList.add(dataList.get(i));
            }else if (drug.getInsureRegCode().equals(dto.getInsureRegCode()) && drug.getCode().equals(dto.getHospItemCode())){
              existList.add(dataList.get(i));
            }
          }
        }
         /*for (Object obj : dataList) {
           //获取字段对应的值
            Field filed1 = obj.getClass().getDeclaredField("insureItemCode");
            Field filed2 = obj.getClass().getDeclaredField("code");
            filed1.setAccessible(true);
            filed2.setAccessible(true);
            String insureItemCode = filed1.get(obj).toString();
            String hospItemCode = filed2.get(obj).toString();
          BaseDrugDTO dto = (BaseDrugDTO) obj;
          selectMap.put("insureItemCode", dto.getInsureRegCode());
          selectMap.put("hospItemCode", dto.getCode());
          //根据医保注册编码和医院项目编码查看表里是否已存在
          List<InsureItemMatchDTO> matchList = insureItemMatchDAO.selectItemsByParams(selectMap);
          if (CollectionUtil.isNotEmpty(matchList)) {
            //如果已存在，则不新增这条数据
            dataList.remove(obj);
          }
        }
      }*/
      }
      //项目
      if (ObjectUtil.isNotEmpty(type) && type.equals("item")) {
        for (int i = 0; i < dataList.size() ; i++) {
          BaseItemDTO item = (BaseItemDTO) dataList.get(i);
          for (InsureItemMatchDTO dto : list) {
            //判断医保注册编码和医院项目编码是否已存在,已存在不新增
            if (ObjectUtil.isEmpty(item.getCode())){
              existList.add(dataList.get(i));
            }else if (item.getInsureRegCode().equals(dto.getInsureRegCode()) && item.getCode().equals(dto.getHospItemCode())) {
              existList.add(dataList.get(i));
            }
          }
        }
      }
      //材料
      if (ObjectUtil.isNotEmpty(type) && type.equals("material")) {
        for (int i = 0; i < dataList.size(); i++) {
          BaseMaterialDTO material = (BaseMaterialDTO) dataList.get(i);
          for (InsureItemMatchDTO dto : list) {
            //判断医保注册编码和医院项目编码是否已存在,已存在不新增
            if (ObjectUtil.isEmpty(material.getCode())){
              existList.add(dataList.get(i));
            }else if (material.getInsureRegCode().equals(dto.getInsureRegCode()) && material.getCode().equals(dto.getHospItemCode())) {
              existList.add(dataList.get(i));
            }
          }
        }
      }
      //去除已存在的
      dataList.removeAll(existList);
    }

    private Boolean saveBatchList(List dataList, String type) {
        /*int dataSize = dataList.size();
        if (dataSize == 0) {
            return true;
        } else if (dataSize > 0 && dataSize <= 1000) {
            insertItems(type, dataList);
        } else if (dataSize > 1000) {
            List dataTemp = new ArrayList<>();
            for (int i = 0; i < dataSize; i++) {
                dataTemp.add(dataList.get(i));
                if (dataTemp.size() == 1000 || (i + 1) == dataSize) {
                    insertItems(type, dataTemp);
                    dataTemp = new ArrayList();
                }
            }
        }*/
      this.insertItems(type,dataList);
        return true;
    }

    private void insertItems(String type, List dataTemp) {
        switch (type) {
            case "drug":
                insureItemMatchDAO.insertHospDrug(dataTemp);
                break;
            case "item":
                insureItemMatchDAO.insertHospItem(dataTemp);
                break;
            case "material":
                insureItemMatchDAO.insertHospMaterial(dataTemp);
                break;
        }
    }


}
