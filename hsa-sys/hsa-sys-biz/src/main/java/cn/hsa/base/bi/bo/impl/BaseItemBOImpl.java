package cn.hsa.base.bi.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.ba.dao.BaseAdviceDAO;
import cn.hsa.module.base.ba.dto.BaseAdviceDTO;
import cn.hsa.module.base.ba.dto.BaseAdviceDetailDTO;
import cn.hsa.module.base.ba.service.BaseAdviceService;
import cn.hsa.module.base.bfc.dao.BaseFinanceClassifyDAO;
import cn.hsa.module.base.bfc.dto.BaseFinanceClassifyDTO;
import cn.hsa.module.base.bi.bo.BaseItemBO;
import cn.hsa.module.base.bi.dao.BaseItemDAO;
import cn.hsa.module.base.bi.dto.BaseItemDTO;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.base.log.bo.BaseDataModifyLogBO;
import cn.hsa.module.base.log.dao.BaseDataModifyLogDAO;
import cn.hsa.module.base.log.dto.BaseDataModifyLogDTO;
import cn.hsa.module.base.log.entity.BaseDataModifyLog;
import cn.hsa.module.base.modify.dao.BaseModifyTraceDAO;
import cn.hsa.module.base.modify.dto.BaseModifyTraceDTO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureItemMatchDTO;
import cn.hsa.module.insure.module.service.InsureConfigurationService;
import cn.hsa.module.insure.module.service.InsureItemMatchService;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;
import cn.hsa.module.sys.code.dto.SysCodeSelectDTO;
import cn.hsa.module.sys.code.service.SysCodeService;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.base.bi.bo.impl
 * @Class_name:: BaseItemBOImpl
 * @Description: 项目管理逻辑实现层
 * @Author: liaojunjie
 * @Date: 2020/7/14 14:53
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class BaseItemBOImpl extends HsafBO implements BaseItemBO {

    @Resource
    SysCodeService sysCodeService;
    /**
     * 项目管理数据库访问接口
     */
    @Resource
    private BaseItemDAO baseItemDAO;
    @Resource
    private BaseOrderRuleService baseOrderRuleService;
    @Resource
    private BaseAdviceService baseAdviceService;
    @Resource
    private BaseAdviceDAO baseAdviceDAO;
    @Resource
    private BaseFinanceClassifyDAO baseFinanceClassifyDAO;
    @Resource
    private RedisUtils redisUtils;

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private InsureItemMatchService insureItemMatchService_consumer;

    @Resource
    private InsureConfigurationService insureConfigurationService_consumer;

    @Resource
    private BaseModifyTraceDAO baseModifyTraceDAO;

    @Resource
    private BaseDataModifyLogBO baseDataModifyLogBO;

    /**
     * @Method getById
     * @Desrciption 通过id获取项目信息
     * @Param [BaseItemDTO]
     * @Author liaojunjie
     * @Date 2020/7/16 9:26
     * @Return cn.hsa.module.base.bi.dto.BaseItemDTO
     **/
    @Override
    public BaseItemDTO getById(BaseItemDTO baseItemDTO) {
        BaseItemDTO byId = this.baseItemDAO.getById(baseItemDTO);
        if (StringUtils.isNotEmpty(byId.getTypeCode())) {
            Map map = new HashMap();
            SysCodeDetailDTO sysCodeDetailDTO = new SysCodeDetailDTO();
            sysCodeDetailDTO.setHospCode(baseItemDTO.getHospCode());
            sysCodeDetailDTO.setCode("XMFL");
            map.put("sysCodeDetailDTO", sysCodeDetailDTO);
            map.put("hospCode", baseItemDTO.getHospCode());
            map.put("value", byId.getTypeCode());
            List<String> fathers = sysCodeService.queryFathers(map).getData();
            byId.setFathers(fathers);
        }
        return byId;
    }

    /**
     * @Method queryPage()
     * @Description 分页查询全部项目信息(默认状态为有效)
     * @Param [BaseItemDTO]
     * @Author liaojunjie
     * @Date 2020/7/14 14:53
     * @Return PageDTO
     **/
    @Override
    public PageDTO queryPage(BaseItemDTO baseItemDTO) {

        if (!StringUtils.isEmpty(baseItemDTO.getTypeCode())) {
            HashMap map = new HashMap();
            map.put("hospCode", baseItemDTO.getHospCode());
            map.put("code", "XMFL");
            List<TreeMenuNode> data = sysCodeService.getCodeData(map).getData();
            String chidldrenIds = TreeUtils.getChidldrenIds(data,
                    baseItemDTO.getTypeCode());
            String[] split = chidldrenIds.split(",");
            List<String> list = new ArrayList<>(Arrays.asList(split));
            baseItemDTO.setIds(list);
        }
        baseItemDTO.setTypeCode("");


        PageHelper.startPage(baseItemDTO.getPageNo(), baseItemDTO.getPageSize());
        List<BaseItemDTO> BaseItemDTOList = this.baseItemDAO.queryPage(baseItemDTO);
        if (!ListUtils.isEmpty(BaseItemDTOList)) {
            for (BaseItemDTO item : BaseItemDTOList) {
                item.setContentType("诊疗项目");
            }
        }
        return PageDTO.of(BaseItemDTOList);

    }

    /**
     * @Method queryAll
     * @Desrciption 查询所有项目
     * @Param [baseItemDTO]
     * @Author liaojunjie
     * @Date 2020/7/18 11:54
     * @Return java.util.List<cn.hsa.module.base.bi.dto.BaseItemDTO>
     **/
    @Override
    public List<BaseItemDTO> queryAll(BaseItemDTO baseItemDTO) {
        return this.baseItemDAO.queryAll(baseItemDTO);
    }

    /**
     * @Method save
     * @Desrciption 增加/修改单条项目信息
     * @Param [baseItemDTO]
     * @Author liaojunjie
     * @Date 2020/7/24 16:58
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean save(BaseItemDTO baseItemDTO) {
        BaseModifyTraceDTO baseModifyTraceDTO = new BaseModifyTraceDTO();

        //首先对传入的编码进行判断是否存在
        BaseItemDTO b = new BaseItemDTO();
        b.setId(baseItemDTO.getId());
        b.setHospCode(baseItemDTO.getHospCode());
        try {
            b.setNationCode(baseItemDTO.getNationCode());
            // 禅道bug4063号，要求将国家编码唯一性去除 luoyong 2021.9.13
            // 比如陪床费和床位费两个项目，国家项目编码中是没有陪床费的项目的，这时需要新增一个陪床费项目也是对应床位费的国家编码就回提示国家编码重复
            /*if (!StringUtils.isEmpty(b.getNationCode()) && baseItemDAO.isCodeExist(b) > 0) {
                throw new AppException("国家编码重复");
            }*/
        } catch (NullPointerException e) {
            throw new AppException("国家编码为空");
        }

        //拼音码五笔码自动生成
        if (!StringUtils.isEmpty(baseItemDTO.getName())) {
            //设置名称拼音码
            //设置名称五笔码
            baseItemDTO.setNamePym(PinYinUtils.toFirstPY(baseItemDTO.getName()));
            baseItemDTO.setNameWbm(WuBiUtils.getWBCode(baseItemDTO.getName()));
        }

        if (!StringUtils.isEmpty(baseItemDTO.getAbbr())) {
            //设置简称拼音码
            //设置简称五笔码
            baseItemDTO.setAbbrPym(PinYinUtils.toFirstPY(baseItemDTO.getAbbr()));
            baseItemDTO.setAbbrWbm(WuBiUtils.getWBCode(baseItemDTO.getAbbr()));
        }

        if (StringUtils.isEmpty(baseItemDTO.getId())) {
            //设置id
            baseItemDTO.setId(SnowflakeUtils.getId());
            //设置创建时间
            baseItemDTO.setCrteTime(DateUtils.getNow());
            //如果编码为空自动生成药品编码
            if (StringUtils.isEmpty(baseItemDTO.getCode())) {
                Map orderMap = new HashMap<>();
                orderMap.put("typeCode", "25");
                orderMap.put("hospCode", baseItemDTO.getHospCode());
                String order = baseOrderRuleService.getOrderNo(orderMap).getData();
                baseItemDTO.setCode(order);
            }
            if (this.baseItemDAO.isCodeExist(baseItemDTO) > 0) {
                throw new AppException("编码【" + baseItemDTO.getCode() + "】或国家编码【" + baseItemDTO.getNationCode() + "】的项目已存在，请检查！");
            }
            Integer insert = this.baseItemDAO.insert(baseItemDTO);
            // 存入缓存
//            cacheOperate(baseItemDTO,null,true);

            // 材料修改，写入异动记录
            baseModifyTraceDTO.setId(SnowflakeUtils.getId());
            baseModifyTraceDTO.setHospCode(baseItemDTO.getHospCode());
            baseModifyTraceDTO.setTableName("base_item");
            baseModifyTraceDTO.setUpdtId(baseItemDTO.getCrteId());
            baseModifyTraceDTO.setUpdtName(baseItemDTO.getCrteName());
            Map<String, Object> conentMap = new HashMap<>();
            conentMap.put("before", "-");
            conentMap.put("after", baseItemDTO);
            baseModifyTraceDTO.setUpdtConent(JSONObject.toJSONString(conentMap));
            baseModifyTraceDAO.insert(baseModifyTraceDTO);


            return insert > 0;
        } else {
            BaseItemDTO oldItem = baseItemDAO.getById(baseItemDTO);
            //修改
            BaseAdviceDetailDTO baseAdviceDetailDTO = new BaseAdviceDetailDTO();
            List<BaseAdviceDetailDTO> baseAdviceDetailDTOList = new ArrayList<>();
            //回写项目名称
            baseAdviceDetailDTO.setItemName(baseItemDTO.getName());
            //回写项目单价
            baseAdviceDetailDTO.setPrice(baseItemDTO.getPrice());
            //回写项目单位
            baseAdviceDetailDTO.setUnitCode(baseItemDTO.getUnitCode());
            //回写项目规格
            baseAdviceDetailDTO.setSpec(baseItemDTO.getSpec());
            // 写入项目编码
            baseAdviceDetailDTO.setItemCode(baseItemDTO.getCode());
            baseAdviceDetailDTO.setHospCode(baseItemDTO.getHospCode());
            baseAdviceDetailDTOList.add(baseAdviceDetailDTO);
            Map map = new HashMap<>();
            map.put("hospCode", baseItemDTO.getHospCode());
            map.put("baseAdviceDetailDTOList", baseAdviceDetailDTOList);
            baseAdviceService.updateBaseAdviceAndBaseAdviceDetailsByItemCode(map);
            Integer update = this.baseItemDAO.update(baseItemDTO);
            // 缓存操作 -- 只有有效的时候才进行操作
            if (Constants.SF.S.equals(baseItemDTO.getIsValid())) {
//                cacheOperate(baseItemDTO,null,true);
            }

            // 材料异动记录 写入异动记录
            baseModifyTraceDTO.setId(SnowflakeUtils.getId());
            baseModifyTraceDTO.setHospCode(baseItemDTO.getHospCode());
            baseModifyTraceDTO.setTableName("base_item");
            baseModifyTraceDTO.setUpdtId(baseItemDTO.getCrteId());
            baseModifyTraceDTO.setUpdtName(baseItemDTO.getCrteName());
            Map<String, Object> conentMap = new HashMap<>();
            conentMap.put("before", oldItem);
            conentMap.put("after", baseItemDTO);
            String jsonObject= JSONObject.toJSONString(conentMap);
            baseModifyTraceDTO.setUpdtConent(jsonObject);
            baseModifyTraceDAO.insert(baseModifyTraceDTO);
            if(BigDecimalUtils.equals(oldItem.getPrice(),baseItemDTO.getPrice())){
                // 主价格未发生变化则不记录日志
                return update > 0;
            }
            BaseDataModifyLogDTO baseDataModifyLogDTO = new BaseDataModifyLogDTO();
            baseDataModifyLogDTO.setItemId(oldItem.getId());
            baseDataModifyLogDTO.setItemCode(oldItem.getCode());
            baseDataModifyLogDTO.setItemName(oldItem.getName());
            baseDataModifyLogDTO.setHospCode(oldItem.getHospCode());
            baseDataModifyLogDTO.setBeforeModifying(JSON.toJSONString(oldItem));
            baseDataModifyLogDTO.setAfterModifying(JSON.toJSONString(baseItemDTO));
            baseDataModifyLogDTO.setCreateId(baseItemDTO.getCrteId());
            baseDataModifyLogDTO.setCreateName(baseItemDTO.getCrteName());
            baseDataModifyLogDTO.setPriceBeforeAdjust(oldItem.getPrice());
            baseDataModifyLogDTO.setPriceAfterAdjust(baseItemDTO.getPrice());
            baseDataModifyLogDTO.setCreateTime(new Date());
            baseDataModifyLogDTO.setAdjustmentTime(new Date());
            baseDataModifyLogBO.insertBaseDataModifyLog(baseDataModifyLogDTO);

            return update > 0;
        }
    }

    /**
     * @Method updateStatus()
     * @Description 修改有效标识状态
     * @Param [BaseItemDTO]
     * @Author liaojunjie
     * @Date 2020/7/14 14:54
     * @Return Boolean
     **/
    @Override
    public Boolean updateStatus(BaseItemDTO baseItemDTO) {
        if (!ListUtils.isEmpty(baseItemDTO.getIds())) {
            if (baseItemDTO.getIsValid().equals("0")) {
                List<BaseItemDTO> byIds = this.baseItemDAO.queryIds(baseItemDTO);
                List<String> names = new ArrayList<>();
                if (!ListUtils.isEmpty(byIds)) {
                    for (BaseItemDTO item : byIds) {
                        BaseAdviceDTO baseAdviceDTO = new BaseAdviceDTO();
                        // 项目名
                        baseAdviceDTO.setName(item.getName());
                        //医院编码
                        baseAdviceDTO.setHospCode(baseItemDTO.getHospCode());
                        // 项目编码（查询哪些医嘱含有这个项目用）
                        baseAdviceDTO.setItemCode(item.getCode());
                        List<BaseAdviceDTO> existItemInAdvice = this.baseAdviceDAO.isExistItemInAdvice(item);
                        if (!ListUtils.isEmpty(existItemInAdvice)) {
                            names.add(item.getName());
                        }
                    }
                    if (!ListUtils.isEmpty(names)) {
                        String join = Joiner.on(",").join(names);
                        throw new AppException("【" + join + "】 被医嘱使用，无法作废");
                    }
                }
            }
        } else {
            throw new AppException("请先勾选数据再进行操作");
        }

        List<BaseItemDTO> baseItemDTOS = new ArrayList<>();
        String isValid = baseItemDTO.getIsValid();
        if (Constants.SF.S.equals(isValid)) {
            baseItemDTO.setIsValid(Constants.SF.F);
        } else {
            baseItemDTO.setIsValid(Constants.SF.S);
        }
        baseItemDTOS = baseItemDAO.queryAll(baseItemDTO);

        baseItemDTO.setIsValid(isValid);
        Integer integer = this.baseItemDAO.updateStatus(baseItemDTO);

        if (Constants.SF.F.equals(baseItemDTO.getIsValid())) {
//            cacheOperate(null,baseItemDTOS,false);
        } else {
//            cacheOperate(null,baseItemDTOS,true);
        }
        return integer > 0;
    }

    /**
     * @Method getById
     * @Desrciption 通过id获取项目信息
     * @Param [BaseItemDTO]
     * @Author liaojunjie
     * @Date 2020/7/16 9:26
     * @Return cn.hsa.module.base.bi.dto.BaseItemDTO
     **/
    @Override
    public BaseItemDTO getByCode(BaseItemDTO baseItemDTO) {
        return this.baseItemDAO.getByCode(baseItemDTO);
    }

    /**
     * @Method queryAllBfcId
     * @Desrciption 查询item带bfcid
     * @params [baseItemDTO]
     * @Author chenjun
     * @Date 2020/10/29 15:46
     * @Return cn.hsa.module.base.bi.dto.BaseItemDTO
     **/
    @Override
    public List<BaseItemDTO> queryAllBfcId(BaseItemDTO baseItemDTO) {
        return this.baseItemDAO.queryAllBfcId(baseItemDTO);
    }

    /**
     * @param map
     * @Method insertInsureItemMatch
     * @Desrciption 医保统一支付平台： 同步项目数据到医保匹配表
     * @Param
     * @Author fuhui
     * @Date 2021/3/20 11:05
     * @Return
     */
    @Override
    public Boolean insertInsureItemMatch(Map<String, Object> map) {
        /*map.put("code", "UNIFIED_PAY");
        SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(map).getData();
        if(sys ==null || !Constants.SF.S.equals(sys.getValue())){
            throw new AppException("请先配置走医保统一支付平台,再进行药品同步操作");
        }*/
        String insureRegCode = MapUtils.get(map,"regCode");

        // 根据医保机构编码查询医保配置信息
        InsureConfigurationDTO configDTO = new InsureConfigurationDTO();
        configDTO.setHospCode(MapUtils.get(map,"hospCode")); //医院编码
        configDTO.setRegCode(insureRegCode); // 医保注册编码
        configDTO.setIsValid(Constants.SF.S); // 是否有效
        Map configMap = new LinkedHashMap();
        configMap.put("hospCode", MapUtils.get(map,"hospCode"));
        configMap.put("insureConfigurationDTO", configDTO);
        List<InsureConfigurationDTO> configurationDTOList = insureConfigurationService_consumer.findByCondition(configMap);
        if (ListUtils.isEmpty(configurationDTOList)) {
            throw new RuntimeException("未找到医保机构，请先配置医保信息！");
        }
        InsureConfigurationDTO insureConfigurationDTO = configurationDTOList.get(0);
        // 获取该医保配置是否走统一支付平台，1走，0/null不走
        String isUnifiedPay = insureConfigurationDTO.getIsUnifiedPay();
        if (StringUtils.isEmpty(isUnifiedPay) || !"1".equals(isUnifiedPay)) {
            throw new AppException("请先配置走医保统一支付平台,再进行药品医保同步操作");
        }

        String crteId =  MapUtils.get(map,"crteId");
        String crteName =  MapUtils.get(map,"crteName");
        BaseItemDTO baseItemDTO = new BaseItemDTO();
        baseItemDTO.setIsValid(Constants.SF.S);
        baseItemDTO.setIsNationCode(true);
        baseItemDTO.setHospCode(MapUtils.get(map,"hospCode"));

        // 医院端的药品数据
        List<BaseItemDTO> baseItemDTOList = baseItemDAO.queryAll(baseItemDTO);

        InsureItemMatchDTO insureItemMatchDTO = new InsureItemMatchDTO();
        insureItemMatchDTO.setHospCode(MapUtils.get(map,"hospCode"));
        insureItemMatchDTO.setInsureRegCode(MapUtils.get(map,"insureRegCode"));
        insureItemMatchDTO.setIsValid(Constants.SF.S);
        insureItemMatchDTO.setIsItemCancel(false);
        insureItemMatchDTO.setIsTrans(Constants.SF.S);
        insureItemMatchDTO.setHospItemType(Constants.XMLB.XM);
        map.put("insureItemMatchDTO",insureItemMatchDTO);
        // 医保匹配表的药品数据集合
        insureItemMatchService_consumer.deleteInsureItemMatch(map).getData();
        List<BaseItemDTO> matchItemList = new ArrayList<>();
        if(!ListUtils.isEmpty(baseItemDTOList)){
            for(BaseItemDTO itemDTO : baseItemDTOList){
                    itemDTO.setHospItemId(itemDTO.getId());
                    itemDTO.setId(SnowflakeUtils.getId());
                    itemDTO.setInsureRegCode(insureRegCode); // 医疗机构编码
                    itemDTO.setInsureItemName(itemDTO.getName()); // 医保中心项目名称
                    itemDTO.setItemCode(Constants.UNIFIED_PAY_TYPE.FWXX); // 项目类别标志
                    itemDTO.setHospItemType(Constants.XMLB.XM);
                    itemDTO.setInsureItemCode(itemDTO.getNationCode()); // 医保中心项目编码
                    itemDTO.setInsureItemType(Constants.UNIFIED_PAY_TYPE.FWXX); // 医保中心项目类别
                    itemDTO.setInsureItemUnitCode(null); // 医保中心项目单位
                    itemDTO.setInsureItemPrepCode(null); // 医保中心项目剂型
                    itemDTO.setInsureItemPrice(null); // 医保中心项目价格
                    itemDTO.setDeductible(null); // 自费比例
                    itemDTO.setStandardCode(null); // 本位码
                    itemDTO.setCheckPrice(null); // 限价
                    itemDTO.setIsMatch(Constants.SF.F); // 是否匹配
                    itemDTO.setAuditCode(Constants.SF.F); // 审核状态代码
                    itemDTO.setIsTrans(Constants.SF.F); // 是否传输
                    itemDTO.setLoseDate(null); // 生效日期
                    itemDTO.setTakeDate(null); // 生效日期
                    itemDTO.setCrteTime(DateUtils.getNow());
                    itemDTO.setCrteId(crteId);
                    itemDTO.setCrteName(crteName);
                    matchItemList.add(itemDTO);
            }
            if(!ListUtils.isEmpty(matchItemList)){
                baseItemDAO.insertInsureMatch(matchItemList);
            }

        }
        return true;
    }
    @Override
    public Boolean insertUpload(Map map) {
        String hospCode = (String) map.get("hospCode");
        String userName = (String) map.get("crteName");
        String userId = (String) map.get("crteId");
        Date nowDate = DateUtils.getNow();

        List<List<String>> resultList = (List<List<String>>) map.get("resultList");


        // 拿取所有项目列表
        BaseItemDTO bi = new BaseItemDTO();
        bi.setHospCode(hospCode);
        List<BaseItemDTO> baseItemDTOS = baseItemDAO.queryPage(bi);

        //拿取所有财务分类列表
        List<BaseFinanceClassifyDTO> baseFinanceClassifyDTOS = baseFinanceClassifyDAO.queryDropDownEnd(hospCode);

        // 拿取系统码表列表
        HashMap sysCodeMap = new HashMap();
        sysCodeMap.put("hospCode", hospCode);
        sysCodeMap.put("code", "DW,XMFL,SF,SSJB");
        WrapperResponse<Map<String, List<SysCodeSelectDTO>>> byCode = sysCodeService.getByCode(sysCodeMap);

        Map<String, List<SysCodeSelectDTO>> data = byCode.getData();
        List<SysCodeSelectDTO> DW = data.get("DW");
        List<SysCodeSelectDTO> XMFL = data.get("XMFL");
        List<SysCodeSelectDTO> SF = data.get("SF");


        Map<String, String> xmflMap = XMFL.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue, (v1, v2) -> v2));
        Map<String, String> sfMap =     SF.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue, (v1, v2) -> v2));
        Map<String, String> dwMap = DW.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue, (v1, v2) -> v2));
        Map<String, String> bfcMap = baseFinanceClassifyDTOS.stream().collect(Collectors.toMap(BaseFinanceClassifyDTO::getName, BaseFinanceClassifyDTO::getCode, (v1, v2) -> v2));
        Map<String, String> xmMap = baseItemDTOS.stream().collect(Collectors.toMap(BaseItemDTO::getCode, BaseItemDTO::getId, (v1, v2) -> v2));

        List<BaseItemDTO> insertList = new ArrayList<>();
            // 记录编码重复信息集合
            List<String> repeatCodes = new ArrayList<>();
            // 记录行数
            int i = 1;
            Integer[] numbers = {0, 1, 2, 4, 5};
            for (List<String> item : resultList) {

                try {
                i++;
                List<Integer> exceptionNumbers = new ArrayList<>();
                for (Integer number : numbers) {
                    if (StringUtils.isEmpty(item.get(number))) {
                        exceptionNumbers.add((number+1));
                    }
                }
                if (!ListUtils.isEmpty(exceptionNumbers)) {
                    throw new AppException("第" + i + "行的" + exceptionNumbers.toString() + "列存在必填数据为空");
                }

                BaseItemDTO baseItemDTO = new BaseItemDTO();
                baseItemDTO.setId(SnowflakeUtils.getId());
                baseItemDTO.setHospCode(hospCode);
                //财务分类
                if (!bfcMap.containsKey(item.get(0).trim())) {
                    baseItemDTO.setBfcCode(item.get(0).trim());
                } else {
                    baseItemDTO.setBfcCode(bfcMap.get(item.get(0).trim()));

                }
                // 项目分类
                if (!xmflMap.containsKey(item.get(1).trim())) {
                    baseItemDTO.setTypeCode(item.get(1).trim());
                } else {
                    baseItemDTO.setTypeCode(xmflMap.get(item.get(1).trim()));

                }
                baseItemDTO.setName(item.get(2).trim());

                //编码没有就自动生成
                if (StringUtils.isEmpty(item.get(3).trim())) {
                    // 生成项目编码
                    HashMap ruleMap = new HashMap();
                    ruleMap.put("hospCode", hospCode);
                    ruleMap.put("typeCode", Constants.ORDERRULE.XM);
                    WrapperResponse<String> orderNo = baseOrderRuleService.getOrderNo(ruleMap);
                    String code = orderNo.getData();
                    baseItemDTO.setCode(code);
                } else {
                    if (xmMap.containsKey(item.get(3).trim())) {
                        repeatCodes.add("第" + i + "行的编码重复");
                    }
                    baseItemDTO.setCode(item.get(3).trim());
                }
                //防止自身导入数据有重复编码
                xmMap.put(baseItemDTO.getCode(),baseItemDTO.getId());
                if (!ListUtils.isEmpty(repeatCodes)) {
                    throw new AppException(repeatCodes.toString());
                }


                if (StringUtils.isNotEmpty(item.get(4).trim())) {
                    try {
                        baseItemDTO.setPrice(BigDecimalUtils.convert(item.get(4).trim()));
                    } catch (java.lang.NumberFormatException e) {
                        throw new AppException("第" + i + "行的单价格式错误");
                    }
                }
                String dwName = item.get(5).trim();
                // 单位如果没有自动生成
                if (!dwMap.containsKey(dwName)) {
                    SysCodeDetailDTO unitCode = new SysCodeDetailDTO();
                    Map codeMap = new HashMap();
                    codeMap.put("hospCode", hospCode);
                    codeMap.put("code", "DW");
                    Integer maxSeq = sysCodeService.getMaxSeqno(codeMap).getData();
                    String maxSeqValue = Integer.toString(maxSeq + 1);
                    unitCode.setHospCode(hospCode);
                    unitCode.setCode("DW");
                    unitCode.setName(dwName);
                    unitCode.setValue(maxSeqValue);
                    unitCode.setSeqNo(maxSeqValue);
                    unitCode.setIsValid(Constants.SF.S);
                    unitCode.setCrteId(userId);
                    unitCode.setCrteName(userName);
                    codeMap.clear();
                    codeMap.put("hospCode", hospCode);
                    codeMap.put("sysCodeDetailDTO", unitCode);
                    sysCodeService.saveCodeDetail(codeMap);
                    baseItemDTO.setUnitCode(maxSeqValue);
                    //防止后续判断没有插入多个相同名称的单位
                    dwMap.put(dwName,maxSeqValue);
                } else {
                    baseItemDTO.setUnitCode(dwMap.get(dwName));
                }

                baseItemDTO.setAbbr(item.get(6).trim());
                baseItemDTO.setNationCode(item.get(7).trim());
                baseItemDTO.setSpec(item.get(8).trim());
                if (StringUtils.isNotEmpty(item.get(9).trim())) {
                    try {
                        baseItemDTO.setThreePrice(BigDecimalUtils.convert(item.get(9).trim()));
                    } catch (java.lang.NumberFormatException e) {
                        throw new AppException("第" + i + "行的三级价格格式错误");
                    }
                }
                if (StringUtils.isNotEmpty(item.get(10))) {
                    try {
                        baseItemDTO.setTwoPrice(BigDecimalUtils.convert(item.get(10).trim()));
                    } catch (java.lang.NumberFormatException e) {
                        throw new AppException("第" + i + "行的二级价格格式错误");
                    }
                }
                if (StringUtils.isNotEmpty(item.get(11))) {
                    try {
                        baseItemDTO.setOnePrice(BigDecimalUtils.convert(item.get(11).trim()));
                    } catch (java.lang.NumberFormatException e) {
                        throw new AppException("第" + i + "行的一级价格格式错误");
                    }
                }

                // 有拼音码和五笔码就直接塞入，无就根据名字自动生成
                if (StringUtils.isNotEmpty(item.get(12).trim())) {
                    baseItemDTO.setNamePym(item.get(12).trim());
                } else if (StringUtils.isNotEmpty(item.get(2))) {
                    baseItemDTO.setNamePym(PinYinUtils.toFirstPY(item.get(2).trim()));
                }

                if (StringUtils.isNotEmpty(item.get(13).trim())) {
                    baseItemDTO.setNameWbm(item.get(13).trim());
                } else if (StringUtils.isNotEmpty(item.get(2))) {
                    baseItemDTO.setNameWbm(WuBiUtils.getWBCode(item.get(2)).trim());
                }

                if (StringUtils.isNotEmpty(item.get(6))) {
                    baseItemDTO.setAbbrPym(PinYinUtils.toFirstPY(item.get(6)).trim());
                    baseItemDTO.setAbbrWbm(WuBiUtils.getWBCode(item.get(6)).trim());
                }
                if (StringUtils.isNotEmpty(item.get(14))) {
                    baseItemDTO.setIsOut(sfMap.get(item.get(14)).trim());
                }
                if (StringUtils.isNotEmpty(item.get(15))) {
                    baseItemDTO.setIsIn(sfMap.get(item.get(15)).trim());
                }
                if (StringUtils.isNotEmpty(item.get(16))) {
                    baseItemDTO.setIsCg(sfMap.get(item.get(16)).trim());
                }
                if (StringUtils.isNotEmpty(item.get(17))) {
                    baseItemDTO.setIsMs(sfMap.get(item.get(17)).trim());
                }
                baseItemDTO.setOutDeptCode(item.get(18).trim());
                baseItemDTO.setInDeptCode(item.get(19).trim());
                baseItemDTO.setIsSuppCurtain(sfMap.get(item.get(20).trim()));
                baseItemDTO.setIntension(item.get(21).trim());
                baseItemDTO.setPrompt(item.get(22).trim());
                baseItemDTO.setExcept(item.get(23).trim());
                baseItemDTO.setRemark(item.get(24).trim());

                // 创建信息
                baseItemDTO.setCrteTime(nowDate);
                baseItemDTO.setCrteName(userName);
                baseItemDTO.setCrteId(userId);

                insertList.add(baseItemDTO);
                } catch (IndexOutOfBoundsException | NullPointerException e) {
                    throw new AppException("第"+i+"行 EXCEL数据格式错误，导入失败,原因："+e.getMessage());
                }
            }

        if (!ListUtils.isEmpty(insertList)) {
            this.baseItemDAO.insertBatch(insertList);
        }
        return true;
    }

    /**
     * @param map 材料信息数据传输对象List
     * @Menthod updateNationCodeById
     * @Desrciption 根据ID修改国家编码
     * @Author pengbo
     * @Date 2021/3/25 16:48
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public boolean updateNationCodeById(Map map) {
        BaseItemDTO baseItemDTO = (BaseItemDTO) map.get("baseItemDTO");
        if(baseItemDTO == null || StringUtils.isEmpty(baseItemDTO.getId())){
            throw  new RuntimeException("项目信息不能为空");
        }
        return this.baseItemDAO.updateNationCodeById(baseItemDTO)>0;
    }

    @Override
    public PageDTO queryUnifiedPage(BaseItemDTO baseItemDTO) {
        PageHelper.startPage(baseItemDTO.getPageNo(), baseItemDTO.getPageSize());
        List<BaseItemDTO> baseItemDTOList = this.baseItemDAO.queryUnifiedPage(baseItemDTO);
        return PageDTO.of(baseItemDTOList);
    }

    public void cacheOperate(BaseItemDTO baseItemDTO, List<BaseItemDTO> baseItemDTOS, Boolean operation) {
        if (baseItemDTO != null) {
            String key = StringUtils.createKey("item", baseItemDTO.getHospCode(), baseItemDTO.getId());
            if (redisUtils.hasKey(key)) {
                redisUtils.del(key);
            }
            if (operation) {
                redisUtils.set(key, baseItemDTO);
            }
        }

        if (!ListUtils.isEmpty(baseItemDTOS)) {
            for (BaseItemDTO item : baseItemDTOS) {
                String key = StringUtils.createKey("item", item.getHospCode(), item.getId());
                if (redisUtils.hasKey(key)) {
                    redisUtils.del(key);
                }
                if (operation) {
                    redisUtils.set(key, item);
                }
            }
        }
    }

    public void checkNull(List<String> item, Integer[] numbers, int i) {


    }
}
