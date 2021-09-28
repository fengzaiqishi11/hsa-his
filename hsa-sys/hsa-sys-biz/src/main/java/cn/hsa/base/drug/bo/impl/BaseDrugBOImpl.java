package cn.hsa.base.drug.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.base.TreeMenuNode;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bfc.dao.BaseFinanceClassifyDAO;
import cn.hsa.module.base.bfc.dto.BaseFinanceClassifyDTO;
import cn.hsa.module.base.bmm.dao.BaseMaterialDAO;
import cn.hsa.module.base.bmm.dto.BaseMaterialDTO;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.base.bp.dao.BaseProductDAO;
import cn.hsa.module.base.bp.dto.BaseProductDTO;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.base.drug.bo.BaseDrugBO;
import cn.hsa.module.base.drug.dao.BaseDrugDAO;
import cn.hsa.module.base.drug.dto.BaseDrugDTO;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureItemMatchDTO;
import cn.hsa.module.insure.module.service.InsureConfigurationService;
import cn.hsa.module.insure.module.service.InsureItemMatchService;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.sys.code.dto.SysCodeDetailDTO;
import cn.hsa.module.sys.code.dto.SysCodeSelectDTO;
import cn.hsa.module.sys.code.service.SysCodeService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

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
public class BaseDrugBOImpl extends HsafBO implements BaseDrugBO {

    /**
     * 药品管理数据库访问接口
     */
    @Resource
    private BaseDrugDAO baseDrugDAO;

    @Resource
    private BaseOrderRuleService baseOrderRuleService;

    @Resource
    private SysCodeService sysCodeService;

    @Resource
    private BaseFinanceClassifyDAO baseFinanceClassifyDAO;

    @Resource
    private BaseMaterialDAO baseMaterialDAO;

    @Resource
    private BaseProductDAO baseProductDAO;


    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private InsureItemMatchService insureItemMatchService_consumer;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private InsureConfigurationService insureConfigurationService_consumer;

    public final static Map PREPMAP = new HashMap() {{
        put("241", "口服常释剂型");
        put("242", "缓释控释剂型");
        put("243", "口服散剂");
        put("172", "口服液体剂");
        put("15", "丸剂");
        put("53", "颗粒剂");
        put("209", "外用散剂");
        put("47", "软膏剂");
        put("88", "贴剂");
        put("184", "外用液体剂");
        put("50", "硬膏剂");
        put("25", "凝胶剂");
        put("186", "涂剂");
        put("62", "栓剂");
        put("42", "滴眼剂");
        put("43", "滴鼻剂");
        put("71", "吸入剂");
        put("138", "注射剂");
    }};

    /**
     * @Method getById
     * @Desrciption 通过id获取项目信息
     * @Param [baseDrugDTO]
     * @Author liaojunjie
     * @Date 2020/7/16 9:26
     * @Return cn.hsa.module.base.bi.dto.BaseDrugDTO
     **/
    @Override
    public BaseDrugDTO getById(BaseDrugDTO baseDrugDTO) {
        BaseDrugDTO byId = this.baseDrugDAO.getById(baseDrugDTO);
        if (byId != null && StringUtils.isNotEmpty(byId.getTypeCode())) {
            Map map = new HashMap();
            SysCodeDetailDTO sysCodeDetailDTO = new SysCodeDetailDTO();
            sysCodeDetailDTO.setHospCode(baseDrugDTO.getHospCode());
            sysCodeDetailDTO.setCode("YPFL");
            map.put("sysCodeDetailDTO", sysCodeDetailDTO);
            map.put("hospCode", baseDrugDTO.getHospCode());
            map.put("value", byId.getTypeCode());
            List<String> fathers = sysCodeService.queryFathers(map).getData();
            byId.setFathers(fathers);
        }

        return byId;
    }

    /**
     * @Method: getByCode
     * @Description: 根据编码获取药品信息
     * @Param: [baseDrugDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/10/9 15:59
     * @Return: cn.hsa.module.base.drug.dto.BaseDrugDTO
     **/
    @Override
    public BaseDrugDTO getByCode(BaseDrugDTO baseDrugDTO) {
        return this.baseDrugDAO.getByCode(baseDrugDTO);
    }

    /**
     * @Method queryPage()
     * @Description 分页查询全部项目信息(默认状态为有效)
     * @Param [baseDrugDTO]
     * @Author liaojunjie
     * @Date 2020/7/14 14:53
     * @Return PageDTO
     **/
    @Override
    public PageDTO queryPage(BaseDrugDTO baseDrugDTO) {
        if (!StringUtils.isEmpty(baseDrugDTO.getTypeCode())) {
            HashMap map = new HashMap();
            map.put("hospCode", baseDrugDTO.getHospCode());
            map.put("code", "YPFL");
            List<TreeMenuNode> treeMenuNodeList = sysCodeService.getCodeData(map).getData();
            String chidldrenIds = TreeUtils.getChidldrenIds(treeMenuNodeList,
                    baseDrugDTO.getTypeCode());
            String[] split = chidldrenIds.split(",");
            List<String> list = new ArrayList<>(Arrays.asList(split));
            baseDrugDTO.setIds(list);
        }
        baseDrugDTO.setTypeCode("");
        //处理默认值
        BigDecimal zero = new BigDecimal(0);
        BigDecimal one = new BigDecimal(1);
        if (BigDecimalUtils.equals(baseDrugDTO.getPrice(), zero)) {
            baseDrugDTO.setPrice(null);
        }
        if (BigDecimalUtils.equals(baseDrugDTO.getSplitRatio(), one)) {
            baseDrugDTO.setSplitRatio(null);
        }
        if (BigDecimalUtils.equals(baseDrugDTO.getSplitPrice(), zero)) {
            baseDrugDTO.setSplitPrice(null);
        }
        //设置分页信息
        PageHelper.startPage(baseDrugDTO.getPageNo(), baseDrugDTO.getPageSize());
        List<BaseDrugDTO> BaseDrugDTOList = this.baseDrugDAO.queryPage(baseDrugDTO);
        if (!ListUtils.isEmpty(BaseDrugDTOList)) {
            for (BaseDrugDTO drug : BaseDrugDTOList) {
                if (StringUtils.isNotEmpty(drug.getPrepCode())) {
                    String prep = drug.getPrepCode();
                    if (PREPMAP.containsKey(prep)) {
                        drug.setPrepName((String) PREPMAP.get(prep));
                    } else {
                        drug.setPrepName("其它剂型");
                    }
                } else {
                    drug.setPrepName("无剂型");
                }
                drug.setContentType("药品");
            }
        }

        return PageDTO.of(BaseDrugDTOList);

    }

    /**
     * @Method queryAll
     * @Desrciption 查询所有药品信息
     * @Param [baseDrugDTO]
     * @Author liaojunjie
     * @Date 2020/7/18 11:55
     * @Return java.util.List<cn.hsa.module.base.drug.dto.BaseDrugDTO>
     **/
    @Override
    public List<BaseDrugDTO> queryAll(BaseDrugDTO baseDrugDTO) {
        //处理默认值
        BigDecimal zero = new BigDecimal(0);
        BigDecimal one = new BigDecimal(1);
        if (BigDecimalUtils.equals(baseDrugDTO.getPrice(), zero)) {
            baseDrugDTO.setPrice(null);
        }
        if (BigDecimalUtils.equals(baseDrugDTO.getSplitRatio(), one)) {
            baseDrugDTO.setSplitRatio(null);
        }
        if (BigDecimalUtils.equals(baseDrugDTO.getSplitPrice(), zero)) {
            baseDrugDTO.setSplitPrice(null);
        }
        return this.baseDrugDAO.queryAll(baseDrugDTO);
    }

    /**
     * @Method save
     * @Desrciption 增加/修改单条药品信息
     * @Param [baseDrugDTO]
     * @Author liaojunjie
     * @Date 2020/7/24 16:58
     * @Return java.lang.Boolean
     **/
    @Override
    public Boolean save(BaseDrugDTO baseDrugDTO) {
        //接受页面传递的拼音码、五笔码
        String usualPym = baseDrugDTO.getUsualPym();
        String usualWbm = baseDrugDTO.getUsualWbm();

        if ((!StringUtils.isEmpty(baseDrugDTO.getUsualName()) || !StringUtils.isEmpty(baseDrugDTO.getGoodName())) && StringUtils.isEmpty(usualPym) && StringUtils.isEmpty(usualWbm)) {
            //设置通用名拼音码
            //设置通用名五笔码
            baseDrugDTO.setUsualPym(PinYinUtils.toFirstPY(baseDrugDTO.getUsualName()));
            baseDrugDTO.setUsualWbm(WuBiUtils.getWBCode(baseDrugDTO.getUsualName()));
        }
        if (!StringUtils.isEmpty(baseDrugDTO.getGoodName()) && (StringUtils.isEmpty(usualPym) && StringUtils.isEmpty(usualWbm))) {
            //设置商品名拼音码
            //设置商品名五笔码
            baseDrugDTO.setGoodPym(PinYinUtils.toFirstPY(baseDrugDTO.getGoodName()));
            baseDrugDTO.setGoodWbm(WuBiUtils.getWBCode(baseDrugDTO.getGoodName()));
        }

        //  增加药品大类：0 西药，1 成药，2 草药
//        if (StringUtils.isNotEmpty(baseDrugDTO.getTypeCode())) {
//            baseDrugDTO.setBigTypeCode(baseDrugDTO.getTypeCode().substring(0, 1));
//        }
        if (StringUtils.isNotEmpty(baseDrugDTO.getBigTypeCode())) {
          baseDrugDTO.setBigTypeCode(baseDrugDTO.getBigTypeCode());
        }

        //计算拆零单价
        if (baseDrugDTO.getSplitRatio() != null && baseDrugDTO.getPrice() != null) {
            if (BigDecimalUtils.greater(baseDrugDTO.getSplitRatio(), new BigDecimal(0))) {
                BigDecimal divide = BigDecimalUtils.divide(baseDrugDTO.getPrice(), baseDrugDTO.getSplitRatio());
                baseDrugDTO.setSplitPrice(divide);
            } else {
                throw new AppException("拆分比设置错误");
            }
        }


        if (StringUtils.isEmpty(baseDrugDTO.getId())) {
            //设置id
            baseDrugDTO.setId(SnowflakeUtils.getId());
            //自动生成药品编码
            Map orderMap = new HashMap<>();
            orderMap.put("typeCode", "24");
            orderMap.put("hospCode", baseDrugDTO.getHospCode());
            if (StringUtils.isEmpty(baseDrugDTO.getCode())) {
                String order = baseOrderRuleService.getOrderNo(orderMap).getData();
                baseDrugDTO.setCode(order);
            }
            //设置创建时间
            baseDrugDTO.setCrteTime(DateUtils.getNow());
            Integer codeExist = baseDrugDAO.isCodeExist(baseDrugDTO);
            if (codeExist > 0) {
                throw new AppException("编码重复或该药品已经存在");
            }
            Integer insert = this.baseDrugDAO.insert(baseDrugDTO);
            // 存入缓存
//            cacheOperate(baseDrugDTO,null,true);
            return insert > 0;
        } else {
            //修改
            Integer update = this.baseDrugDAO.updateBaseDrug(baseDrugDTO);
            // 缓存操作 -- 只有有效的时候才进行操作
            if (Constants.SF.S.equals(baseDrugDTO.getIsValid())) {
//                cacheOperate(baseDrugDTO,null,true);
            }
            return update > 0;
        }
    }


    /**
     * @Method updateStatus()
     * @Description 修改有效标识状态
     * @Param [baseDrugDTO]
     * @Author liaojunjie
     * @Date 2020/7/14 14:54
     * @Return Boolean
     **/
    @Override
    public Boolean updateStatus(BaseDrugDTO baseDrugDTO) {
//        if (!ListUtils.isEmpty(baseDrugDTO.getIds())) {
//            if (baseDrugDTO.getIsValid().equals("0")) {
//                List<BaseDrugDTO> byIds = this.baseDrugDAO.queryIds(baseDrugDTO);
//                List<String> names = new ArrayList<>();
//                if(!ListUtils.isEmpty(byIds)){
//                    for (BaseDrugDTO drug : byIds){
//                        if(drug.getStockFlag()>0){
//                            names.add(drug.getGoodName()+"（"+drug.getCode()+"）");
//                        }
//                    }
//                    if(!ListUtils.isEmpty(names)){
//                        String join = Joiner.on(",").join(names);
//                        throw new AppException("【"+join+"】 在库存中已经使用，无法作废");
//                    }
//                }
//            }
//        } else {
//            throw new AppException("操作失败");
//        }
        //缓存操作
        List<BaseDrugDTO> baseDrugDTOS = new ArrayList<>();
        String isValid = baseDrugDTO.getIsValid();
        if (Constants.SF.S.equals(isValid)) {
            baseDrugDTO.setIsValid(Constants.SF.F);
        } else {
            baseDrugDTO.setIsValid(Constants.SF.S);
        }
        baseDrugDTOS = baseDrugDAO.queryAll(baseDrugDTO);

        baseDrugDTO.setIsValid(isValid);
        Integer integer = this.baseDrugDAO.updateStatus(baseDrugDTO);

        if (Constants.SF.F.equals(baseDrugDTO.getIsValid())) {
//            cacheOperate(null,baseDrugDTOS,false);
        } else {
//            cacheOperate(null,baseDrugDTOS,true);
        }
        return integer > 0;
    }

    @Override
    public Boolean updateAllById(List<BaseDrugDTO> baseDrugDTOS) {
        return this.baseDrugDAO.updateAllById(baseDrugDTOS) > 0;
    }

    /**
     * @param baseDeptDTO
     * @Method queryStockDrugInfoOfDept
     * @Desrciption 查询某库位的项目(药品或材料)信息
     * @Author liuqi1
     * @Date 2020/8/12 11:56
     * @Return java.util.List<cn.hsa.module.base.drug.dto.BaseDrugDTO>
     **/
    @Override
    public PageDTO queryStockItemInfoPage(BaseDeptDTO baseDeptDTO) {
        PageHelper.startPage(baseDeptDTO.getPageNo(), baseDeptDTO.getPageSize());

        if ("13".equals(baseDeptDTO.getLoginDeptType()) || "14".equals(baseDeptDTO.getLoginDeptType())) {
            //如果登录科室是 材料库 或者 材料药房，查询的是库存材料信息

            BaseMaterialDTO baseMaterialDTO = new BaseMaterialDTO();
            baseMaterialDTO.setHospCode(baseDeptDTO.getHospCode());
            baseMaterialDTO.setBizId(baseDeptDTO.getLoginDeptId());
            baseMaterialDTO.setKeyword(baseDeptDTO.getKeyword());
            baseMaterialDTO.setIsStoreGtZero(baseDeptDTO.getIsStoreGtZero());

            List<BaseMaterialDTO> baseMaterialDTOS = baseMaterialDAO.queryNewStockMaterialInfoPage(baseMaterialDTO);
            return PageDTO.of(baseMaterialDTOS);
        } else {
            //否则查询的是库存药品信息

            BaseDrugDTO baseDrugDTO = new BaseDrugDTO();
            baseDrugDTO.setHospCode(baseDeptDTO.getHospCode());
            baseDrugDTO.setBizId(baseDeptDTO.getLoginDeptId());
            baseDrugDTO.setKeyword(baseDeptDTO.getKeyword());
            baseDrugDTO.setIsStoreGtZero(baseDeptDTO.getIsStoreGtZero());
            List<BaseDrugDTO> baseDrugDTOS = baseDrugDAO.queryNewStockDrugInfoPage(baseDrugDTO);
            return PageDTO.of(baseDrugDTOS);
        }
    }
    /**
     * @param map
     * @Method insertInsureDrugMatch
     * @Desrciption 医保统一支付平台： 同步药品数据到医保匹配表
     * @Param
     * @Author fuhui
     * @Date 2021/3/20 11:05
     * @Return
     */
    @Override
    public Boolean insertInsureDrugMatch(Map<String, Object> map) {
        /*map.put("code", "UNIFIED_PAY");
        SysParameterDTO sys = sysParameterService_consumer.getParameterByCode(map).getData();
        if(sys ==null || !Constants.SF.S.equals(sys.getValue())){
            throw new AppException("请先配置走医保统一支付平台,再进行药品医保同步操作");
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
        BaseDrugDTO baseDrugDTO = new BaseDrugDTO();
        baseDrugDTO.setIsValid(Constants.SF.S);
        baseDrugDTO.setHospCode(MapUtils.get(map,"hospCode"));
        baseDrugDTO.setIsNationCode(true);
        // 医院端的药品数据
        List<BaseDrugDTO> baseDrugDTOList = baseDrugDAO.queryAll(baseDrugDTO);

        InsureItemMatchDTO insureItemMatchDTO = new InsureItemMatchDTO();
        insureItemMatchDTO.setHospCode(MapUtils.get(map,"hospCode"));
        insureItemMatchDTO.setInsureRegCode(insureRegCode);
        insureItemMatchDTO.setHospItemType(Constants.XMLB.YP);
        insureItemMatchDTO.setIsTrans(Constants.SF.S);
        insureItemMatchDTO.setIsItemCancel(false);
        map.put("insureItemMatchDTO",insureItemMatchDTO);
        // 医保匹配表的药品数据集合
        insureItemMatchService_consumer.deleteInsureItemMatch(map).getData();
        List<BaseDrugDTO> matchDrugList = new ArrayList<>();
        if(!ListUtils.isEmpty(baseDrugDTOList)){
            for(BaseDrugDTO drugDTO : baseDrugDTOList){
                    drugDTO.setHospItemId(drugDTO.getId());
                    drugDTO.setId(SnowflakeUtils.getId());
                    drugDTO.setInsureRegCode(insureRegCode); // 医疗机构编码
                    drugDTO.setInsureItemName(drugDTO.getUsualName()); // 医保中心项目名称
                    drugDTO.setInsureItemCode(drugDTO.getNationCode()); // 医保中心项目编码
                    if("1".equals(drugDTO.getBigTypeCode())){   //成药
                        drugDTO.setItemCode(Constants.UNIFIED_PAY_TYPE.ZCY);
                        drugDTO.setInsureItemType(Constants.UNIFIED_PAY_TYPE.ZCY);
                    }
                    if("0".equals(drugDTO.getBigTypeCode())){
                        drugDTO.setItemCode(Constants.UNIFIED_PAY_TYPE.XY);
                        drugDTO.setInsureItemType(Constants.UNIFIED_PAY_TYPE.XY);
                    }
                    if("2".equals(drugDTO.getBigTypeCode())){
                        drugDTO.setItemCode(Constants.UNIFIED_PAY_TYPE.ZYYP);
                        drugDTO.setInsureItemType(Constants.UNIFIED_PAY_TYPE.ZYYP);
                    }
                    drugDTO.setPqccItemId(null);
                    drugDTO.setPqccItemId(null);
                    drugDTO.setInsureItemUnitCode(null); // 医保中心项目单位
                    drugDTO.setInsureItemPrepCode(null); // 医保中心项目剂型
                    drugDTO.setInsureItemPrice(null); // 医保中心项目价格
                    drugDTO.setDeductible(null); // 自费比例
                    drugDTO.setStandardCode(drugDTO.getInsureCode()); // 本位码
                    drugDTO.setCheckPrice(null); // 限价
                    drugDTO.setIsMatch(Constants.SF.F); // 是否匹配
                    drugDTO.setAuditCode(Constants.SF.F); // 审核状态代码
                    drugDTO.setIsTrans(Constants.SF.F); // 是否传输
                    drugDTO.setLoseDate(null); // 生效日期
                    drugDTO.setTakeDate(null); // 生效日期
                    drugDTO.setCrteTime(DateUtils.getNow());
                    drugDTO.setCrteId(crteId);
                    drugDTO.setCrteName(crteName);
                    matchDrugList.add(drugDTO);
            }
            if(!ListUtils.isEmpty(matchDrugList)){
                baseDrugDAO.insertInsureMatch(matchDrugList);
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
        if(ListUtils.isEmpty(resultList)){
            throw new RuntimeException("未获取到需要上传的药品信息!");
        }

        // 拿取所有药品列表
        BaseDrugDTO bd = new BaseDrugDTO();
        bd.setHospCode(hospCode);
        List<BaseDrugDTO> baseDrugDTOS = baseDrugDAO.queryPage(bd);

        //拿取所有财务分类列表
        List<BaseFinanceClassifyDTO> baseFinanceClassifyDTOS = baseFinanceClassifyDAO.queryDropDownEnd(hospCode);

        //拿取所有生产企业列表
        BaseProductDTO baseProductDTO = new BaseProductDTO();
        baseProductDTO.setHospCode(hospCode);
        baseProductDTO.setIsValid(Constants.SF.S);
        List<BaseProductDTO> baseProductDTOS = baseProductDAO.queryPage(baseProductDTO);


        // 拿取系统码表列表
        HashMap sysCodeMap = new HashMap();
        sysCodeMap.put("hospCode", hospCode);
        sysCodeMap.put("code", "YPFL,YPDL,JXFL,JLDW,SF,DW,YF,YPJB,DMTX,KJYPJB,JY,XB,ZYYF");
        WrapperResponse<Map<String, List<SysCodeSelectDTO>>> byCode = sysCodeService.getByCode(sysCodeMap);
        BaseFinanceClassifyDTO baseFinanceClassifyDTO = new BaseFinanceClassifyDTO();
        baseFinanceClassifyDTO.setHospCode(hospCode);
        baseFinanceClassifyDTO.setIsValid(Constants.SF.S);

        //获取各个码表列表
        Map<String, List<SysCodeSelectDTO>> data = byCode.getData();
        List<SysCodeSelectDTO> YPFL = data.get("YPFL");
        List<SysCodeSelectDTO> YPDL = data.get("YPDL");
        List<SysCodeSelectDTO> JXFL = data.get("JXFL");
        List<SysCodeSelectDTO> JLDW = data.get("JLDW");
        List<SysCodeSelectDTO> YPJB = data.get("YPJB");
        List<SysCodeSelectDTO> SF = data.get("SF");
        List<SysCodeSelectDTO> DW = data.get("DW");
        List<SysCodeSelectDTO> YF = data.get("YF");
        List<SysCodeSelectDTO> ZYYF = data.get("ZYYF");
        List<SysCodeSelectDTO> DMTX = data.get("DMTX");
        List<SysCodeSelectDTO> KJYPJB = data.get("KJYPJB");
        List<SysCodeSelectDTO> JY = data.get("JY");
        List<SysCodeSelectDTO> XB = data.get("XB");

        //获取对应的码表key->值
        Map<String, String> ypflMap = YPFL.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue, (v1, v2) -> v2));
        Map<String, String> ypdlMap = YPDL.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue, (v1, v2) -> v2));
        Map<String, String> jxflMap = JXFL.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue, (v1, v2) -> v2));
        Map<String, String> jldwMap = JLDW.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue, (v1, v2) -> v2));
        Map<String, String> sfMap = SF.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue, (v1, v2) -> v2));
        Map<String, String> dwMap = DW.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue, (v1, v2) -> v2));
        Map<String, String> yfMap = YF.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue, (v1, v2) -> v2));
        Map<String, String> ypjbMap = YPJB.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue, (v1, v2) -> v2));
        Map<String, String> kjypjbMap = KJYPJB.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue, (v1, v2) -> v2));
        Map<String, String> jyMap = JY.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue, (v1, v2) -> v2));
        Map<String, String> zyyfMap = ZYYF.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue, (v1, v2) -> v2));
        Map<String, String> dmtxMap = DMTX.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue, (v1, v2) -> v2));
        Map<String, String> xbMap = XB.stream().collect(Collectors.toMap(SysCodeSelectDTO::getLabel, SysCodeSelectDTO::getValue, (v1, v2) -> v2));
        Map<String, String> bfcMap = baseFinanceClassifyDTOS.stream().collect(Collectors.toMap(BaseFinanceClassifyDTO::getName, BaseFinanceClassifyDTO::getCode, (v1, v2) -> v2));
        Map<String, String> ypMap = baseDrugDTOS.stream().collect(Collectors.toMap(BaseDrugDTO::getCode, BaseDrugDTO::getId, (v1, v2) -> v2));
        Map<String, String> scqyMap = baseProductDTOS.stream().collect(Collectors.toMap(BaseProductDTO::getName, BaseProductDTO::getCode, (v1, v2) -> v2));

        List<BaseDrugDTO> insertList = new ArrayList<>();
        //定义必填列
        Integer[] numbers = {0, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        // 记录行数
        int i = 1;
        try {
            for (List<String> item : resultList) {
                i++;
                //判断必填字段是否为空
                List<Integer> exceptionNumbers = new ArrayList<>();
                for (int val:numbers) {
                    if (StringUtils.isEmpty(item.get(val))) {
                        exceptionNumbers.add((val+1));
                    }
                }
                if (!ListUtils.isEmpty(exceptionNumbers)){
                    throw new AppException( "第" + i + "行的" + exceptionNumbers.toString() + "列存在必填数据为空");
                }

                BaseDrugDTO baseDrugDTO = new BaseDrugDTO();
                baseDrugDTO.setId(SnowflakeUtils.getId());
                baseDrugDTO.setHospCode(hospCode);
                //财务分类
                if (!bfcMap.containsKey(item.get(0).trim())) {
                    baseDrugDTO.setBfcCode(item.get(0).trim());
                } else {
                    baseDrugDTO.setBfcCode(bfcMap.get(item.get(0).trim()));
                }
                // 药品大类
                if (!ypdlMap.containsKey(item.get(1).trim())) {
                    baseDrugDTO.setBigTypeCode(item.get(1).trim());
                } else {
                    baseDrugDTO.setBigTypeCode(ypdlMap.get(item.get(1).trim()));
                }
                // 药品分类
                if (!ypflMap.containsKey(item.get(2).trim())) {
                    baseDrugDTO.setTypeCode(item.get(2).trim());
                } else {
                    baseDrugDTO.setTypeCode(ypflMap.get(item.get(2).trim()));
                }
                //药品编码
                if (StringUtils.isEmpty(item.get(3).trim())) {
                    // 生成药品编码
                    HashMap ruleMap = new HashMap();
                    ruleMap.put("hospCode", hospCode);
                    ruleMap.put("typeCode", Constants.ORDERRULE.YP);
                    WrapperResponse<String> orderNo = baseOrderRuleService.getOrderNo(ruleMap);
                    String code = orderNo.getData();
                    baseDrugDTO.setCode(code);
                } else {
                    if (ypMap.containsKey(item.get(3).trim())) {
                        throw new AppException("第" + i + "行的编码重复");
                    }
                    baseDrugDTO.setCode(item.get(3).trim());
                }
                //防止导入数据本身有编码重复
                if (ypMap == null){
                    ypMap = new HashMap<>() ;
                }
                ypMap.put(baseDrugDTO.getCode(),baseDrugDTO.getId());


                baseDrugDTO.setGoodName(item.get(4).trim());
                baseDrugDTO.setSpec(item.get(5).trim());
                // 单位如果没有自动生成
                String dwName = item.get(6).trim();
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
                    baseDrugDTO.setUnitCode(maxSeqValue);
                    //防止后续判断没有插入多个相同名称的单位
                    dwMap.put(dwName,maxSeqValue);
                }else {
                    baseDrugDTO.setUnitCode(dwMap.get(dwName));
                }

                //拆零单位如果没有自动生成
                String cldwName = item.get(7).trim();
                if (!dwMap.containsKey(cldwName)) {
                    SysCodeDetailDTO unitCode = new SysCodeDetailDTO();
                    Map codeMap = new HashMap();
                    codeMap.put("hospCode", hospCode);
                    codeMap.put("code", "DW");
                    Integer maxSeq = sysCodeService.getMaxSeqno(codeMap).getData();
                    String maxSeqValue = Integer.toString(maxSeq + 1);
                    unitCode.setHospCode(hospCode);
                    unitCode.setCode("DW");
                    unitCode.setName(cldwName);
                    unitCode.setValue(maxSeqValue);
                    unitCode.setSeqNo(maxSeqValue);
                    unitCode.setIsValid(Constants.SF.S);
                    unitCode.setCrteId(userId);
                    unitCode.setCrteName(userName);
                    codeMap.clear();
                    codeMap.put("hospCode", hospCode);
                    codeMap.put("sysCodeDetailDTO", unitCode);
                    sysCodeService.saveCodeDetail(codeMap);
                    baseDrugDTO.setSplitUnitCode(maxSeqValue);
                    //防止后续判断没有插入多个相同名称的单位
                    dwMap.put(cldwName,maxSeqValue);
                }else {
                    baseDrugDTO.setSplitUnitCode(dwMap.get(cldwName));
                }

                //剂型如果没有自动生成
                String jxName = item.get(8).trim();
                if (!jxflMap.containsKey(jxName)) {
                    SysCodeDetailDTO unitCode = new SysCodeDetailDTO();
                    Map codeMap = new HashMap();
                    codeMap.put("hospCode", hospCode);
                    codeMap.put("code", "JXFL");
                    Integer maxSeq = sysCodeService.getMaxSeqno(codeMap).getData();
                    String maxSeqValue = Integer.toString(maxSeq + 1);
                    unitCode.setHospCode(hospCode);
                    unitCode.setCode("JXFL");
                    unitCode.setName(jxName);
                    unitCode.setValue(maxSeqValue);
                    unitCode.setSeqNo(maxSeqValue);
                    unitCode.setIsValid(Constants.SF.S);
                    unitCode.setCrteId(userId);
                    unitCode.setCrteName(userName);
                    codeMap.clear();
                    codeMap.put("hospCode", hospCode);
                    codeMap.put("sysCodeDetailDTO", unitCode);
                    sysCodeService.saveCodeDetail(codeMap);
                    baseDrugDTO.setPrepCode(maxSeqValue);
                    //防止后续判断没有插入多个相同名称的单位
                    jxflMap.put(jxName,maxSeqValue);
                }else {
                    baseDrugDTO.setPrepCode(jxflMap.get(jxName));
                }

                //剂量
                try{
                    baseDrugDTO.setDosage(BigDecimalUtils.convert(item.get(9).trim()));
                }catch (Exception e){
                    throw new AppException("第"+i+"行剂量【"+item.get(9)+"】数据格式有误请检查");
                }
                //剂量单位如果没有自动生成
                String jldwName = item.get(10).trim();
                if (!jldwMap.containsKey(jldwName)) {
                    SysCodeDetailDTO unitCode = new SysCodeDetailDTO();
                    Map codeMap = new HashMap();
                    codeMap.put("hospCode", hospCode);
                    codeMap.put("code", "JLDW");
                    Integer maxSeq = sysCodeService.getMaxSeqno(codeMap).getData();
                    String maxSeqValue = Integer.toString(maxSeq + 1);
                    unitCode.setHospCode(hospCode);
                    unitCode.setCode("JLDW");
                    unitCode.setName(jldwName);
                    unitCode.setValue(maxSeqValue);
                    unitCode.setSeqNo(maxSeqValue);
                    unitCode.setIsValid(Constants.SF.S);
                    unitCode.setCrteId(userId);
                    unitCode.setCrteName(userName);
                    codeMap.clear();
                    codeMap.put("hospCode", hospCode);
                    codeMap.put("sysCodeDetailDTO", unitCode);
                    sysCodeService.saveCodeDetail(codeMap);
                    baseDrugDTO.setDosageUnitCode(maxSeqValue);
                    //防止后续判断没有插入多个相同名称的单位
                    jldwMap.put(jldwName,maxSeqValue);
                }else {
                    baseDrugDTO.setDosageUnitCode(jldwMap.get(jldwName));
                }

                // 门诊单位
                if (item.get(11).trim().equals(item.get(6).trim()) || item.get(11).trim().equals(item.get(7).trim())) {
                    baseDrugDTO.setOutUnitCode(dwMap.get(item.get(11).trim()));
                } else {
                    throw new AppException("第" + i + "行的门诊单位不符合要求");
                }

                // 住院单位
                if (item.get(12).trim().equals(item.get(6).trim()) || item.get(12).trim().equals(item.get(7).trim())) {
                    baseDrugDTO.setInUnitCode(dwMap.get(item.get(12).trim()));
                } else {
                    throw new AppException("第" + i + "行的门诊住院单位不符合要求");
                }

                //零售价
                if (StringUtils.isNotEmpty(item.get(13))) {
                    try {
                        baseDrugDTO.setPrice(BigDecimalUtils.convert(item.get(13).trim()));
                    } catch (java.lang.NumberFormatException e) {
                        throw new AppException("第" + i + "行的单价格式错误");
                    }
                }

                //拆零分比
                if (StringUtils.isNotEmpty(item.get(14))) {
                    try {
                        baseDrugDTO.setSplitRatio(BigDecimalUtils.convert(item.get(14).trim()));
                    } catch (java.lang.NumberFormatException e) {
                        throw new AppException("第" + i + "行的拆分比格式错误");
                    }
                }
                //拆零单价
                if (StringUtils.isNotEmpty(item.get(15))) {
                    try {
                        baseDrugDTO.setSplitPrice(BigDecimalUtils.convert(item.get(15).trim()));
                    } catch (java.lang.NumberFormatException e) {
                        throw new AppException("第" + i + "行的拆零单价格式错误");
                    }
                }

                //通用名
                baseDrugDTO.setUsualName(item.get(16).trim());
                //频率
                baseDrugDTO.setRateCode(item.get(17).trim());

                //用法
                if ("2".equals(ypdlMap.get(item.get(1).trim()))) {
                    baseDrugDTO.setUsageCode(zyyfMap.get(item.get(18).trim()));
                } else {
                    baseDrugDTO.setUsageCode(yfMap.get(item.get(18).trim()));
                }

                //门诊使用标志
                baseDrugDTO.setIsOut(sfMap.get(item.get(19).trim()));
                //住院使用标志
                baseDrugDTO.setIsIn(sfMap.get(item.get(20).trim()));

                //大输液
                baseDrugDTO.setIsLvp(sfMap.get(item.get(21).trim()));
                //医保本位码编码
                baseDrugDTO.setInsureCode(item.get(22).trim());
                //国家卫健委编码
                baseDrugDTO.setNationCode(item.get(23).trim());

                if (StringUtils.isNotEmpty(item.get(24).trim())) {
                    baseDrugDTO.setMaxDosage(BigDecimalUtils.convert(item.get(24).trim()));
                }

                if (StringUtils.isNotEmpty(item.get(25).trim())) {
                    baseDrugDTO.setMinDosage(BigDecimalUtils.convert(item.get(25).trim()));
                }

                baseDrugDTO.setGenderCode(xbMap.get(item.get(26).trim()));

                if (StringUtils.isNotEmpty(item.get(27).trim())) {
                    baseDrugDTO.setMinAge(Integer.parseInt(item.get(27)));
                }

                if (StringUtils.isNotEmpty(item.get(28).trim())) {
                    baseDrugDTO.setMinAge(Integer.parseInt(item.get(28).trim()));
                }

                baseDrugDTO.setDdd(item.get(29).trim());
                baseDrugDTO.setDurgCode(ypjbMap.get(item.get(30).trim()));
                baseDrugDTO.setPhCode(dmtxMap.get(item.get(31).trim()));
                baseDrugDTO.setAntibacterialCode(kjypjbMap.get(item.get(32).trim()));
                baseDrugDTO.setIsSkin(sfMap.get(item.get(34).trim()));
                baseDrugDTO.setIsBasic(sfMap.get(item.get(35).trim()));
                baseDrugDTO.setBasicCode(jyMap.get(item.get(36).trim()));
                baseDrugDTO.setProtonPump(item.get(37).trim());
                baseDrugDTO.setNdan(item.get(38).trim());


                //没有就生成 生产企业
                String scqyName = item.get(39).trim();
                if(!scqyMap.containsKey(scqyName)) {
                    baseProductDTO = new BaseProductDTO() ;
                    baseProductDTO.setId(SnowflakeUtils.getId());

                    map = new HashMap();
                    map.put("hospCode",baseDrugDTO.getHospCode());
                    map.put("typeCode", Constants.ORDERRULE.SCQY);
                    WrapperResponse<String> orderNo = baseOrderRuleService.getOrderNo(map);
                    baseProductDTO.setHospCode(baseDrugDTO.getHospCode());
                    baseProductDTO.setCode(orderNo.getData());
                    baseProductDTO.setName(scqyName);
                    baseProductDTO.setPym(PinYinUtils.toFirstPY(scqyName));
                    baseProductDTO.setWbm(WuBiUtils.getWBCode(scqyName));
                    baseProductDTO.setIsValid(Constants.SF.S);
                    baseProductDTO.setCrteTime(DateUtils.getNow());
                    baseProductDAO.insert(baseProductDTO);

                    baseDrugDTO.setProdCode(baseProductDTO.getCode());
                    scqyMap.put(scqyName,baseProductDTO.getCode());
                } else {
                    baseDrugDTO.setProdCode(scqyMap.get(scqyName));
                }


                // 有拼音码和五笔码就直接塞入，无就根据名字自动生成
                if (StringUtils.isNotEmpty(item.get(40).trim())) {
                    baseDrugDTO.setUsualPym(item.get(40).trim());
                } else if (StringUtils.isNotEmpty(item.get(16).trim())) {
                    baseDrugDTO.setUsualPym(PinYinUtils.toFirstPY(item.get(16).trim()));
                }

                if (StringUtils.isNotEmpty(item.get(41).trim())) {
                    baseDrugDTO.setUsualWbm(item.get(41).trim());
                } else if (StringUtils.isNotEmpty(item.get(16).trim())) {
                    baseDrugDTO.setUsualWbm(WuBiUtils.getWBCode(item.get(16).trim()));
                }

                // 有拼音码和五笔码就直接塞入，无就根据名字自动生成
                if (StringUtils.isNotEmpty(item.get(42).trim())) {
                    baseDrugDTO.setGoodPym(item.get(42).trim());
                } else if (StringUtils.isNotEmpty(item.get(4).trim())) {
                    baseDrugDTO.setGoodPym(PinYinUtils.toFirstPY(item.get(4).trim()));
                }

                if (StringUtils.isNotEmpty(item.get(43).trim())) {
                    baseDrugDTO.setGoodWbm(item.get(43).trim());
                } else if (StringUtils.isNotEmpty(item.get(4).trim())) {
                    baseDrugDTO.setGoodWbm(WuBiUtils.getWBCode(item.get(4).trim()));
                }


                // 创建信息
                baseDrugDTO.setCrteTime(nowDate);
                baseDrugDTO.setCrteName(userName);
                baseDrugDTO.setCrteId(userId);

                insertList.add(baseDrugDTO);
            }

        } catch (IndexOutOfBoundsException e) {
            throw new AppException("第" + i +"行EXCEL数据格式错误，导入失败");
        } catch (NullPointerException e){
            throw new AppException("null");
        }catch (AppException e){
            throw e;
        }catch (Exception e){
            throw new AppException(e.getMessage());
        }
        if (!ListUtils.isEmpty(insertList)) {
            this.baseDrugDAO.insertBatch(insertList);
        }
        return true;
    }

    /**
     * @param baseDrugDTO
     * @Method updateBaseDrugS()
     * @Description 修改单条药品信息 (修改国家编码)
     * @Param [baseDrugDTO]
     * @Author pengbo
     * @Date 2021/3/24 18:57
     * @Return WrapperResponse<Boolean>
     */
    @Override
    public Boolean updateBaseDrugS(BaseDrugDTO baseDrugDTO) {
        return baseDrugDAO.updateBaseDrugS(baseDrugDTO)>0;
    }

    @Override
    public PageDTO queryUnifiedPage(BaseDrugDTO baseDrugDTO) {
        PageHelper.startPage(baseDrugDTO.getPageNo(),baseDrugDTO.getPageSize());
        List<BaseDrugDTO> baseDrugDTOList = this.baseDrugDAO.queryUnifiedPage(baseDrugDTO);
        return PageDTO.of(baseDrugDTOList);
    }
    /**
     * @Meth: queryEnableCancel
     * @Description: 查看是否能够作废药品
     *  1.判断费用表是否有未发药品。
     *  2.长期医嘱是否开了该药品.如果有,不允许作废.
     * @Param: [baseDrugDTO]
     * @return: java.lang.Boolean
     * @Author: zhangguorui
     * @Date: 2021/9/27
     */
    @Override
    public Boolean queryEnableCancel(BaseDrugDTO baseDrugDTO) {
        // 获得作废药品id
        List<String> ids = baseDrugDTO.getIds();
        if (ListUtils.isEmpty(ids)) {
            throw new AppException("作废的药品不能为空");
        }
        // 1.判断费用表是否存在未发药药品
        // 住院费用表
        List<InptCostDTO> inptCostDTOS = baseDrugDAO.queryCostIsInptOut(baseDrugDTO);
        // 过滤出药品名称
        List<String> inptCostItemNames = inptCostDTOS.stream().map(InptCostDTO::getItemName).collect(Collectors.toList());
        // 门诊费用表
        List<OutptCostDTO> outptCostDTOS = baseDrugDAO.queryCostIsOutptOut(baseDrugDTO);
        // 过滤
        List<String> ouptCostItemNames = inptCostDTOS.stream().map(InptCostDTO::getItemName).collect(Collectors.toList());
        String errorMessage = "";
        if (!ListUtils.isEmpty(inptCostItemNames)) {
            errorMessage += "住院业务中：" + inptCostItemNames.toString() + "未发药，不可进行作废操作;";
        }
        if (!ListUtils.isEmpty(ouptCostItemNames)) {
            errorMessage += "门诊业务中：" + ouptCostItemNames.toString() + "未发药，不可进行作废操作";
        }
        if (StringUtils.isNotEmpty(errorMessage)) {
            throw new AppException(errorMessage);
        }
        // 2.长期医嘱是否开了该药品，如果有，不允许作废
        List<InptAdviceDTO> inptAdviceDTOS = baseDrugDAO.queryInptAdviceIsLong(baseDrugDTO);
        List<String> inptAdviceItemNames = inptAdviceDTOS.stream().map(InptAdviceDTO::getItemName).collect(Collectors.toList());
        if (!ListUtils.isEmpty(inptAdviceItemNames)) {
            throw new AppException("长期医嘱中存在：" + inptAdviceItemNames.toString() + "未停医嘱，不可进行作废操作");
        }
        return true;
    }

    public void cacheOperate(BaseDrugDTO baseDrugDTO, List<BaseDrugDTO> baseDrugDTOS, Boolean operation) {
        if (baseDrugDTO != null) {
            String key = StringUtils.createKey("drug", baseDrugDTO.getHospCode(), baseDrugDTO.getId());
            if (redisUtils.hasKey(key)) {
                redisUtils.del(key);
            }
            if (operation) {
                redisUtils.set(key, baseDrugDTO);
            }
        }

        if (!ListUtils.isEmpty(baseDrugDTOS)) {
            for (BaseDrugDTO drug : baseDrugDTOS) {
                String key = StringUtils.createKey("drug", drug.getHospCode(), drug.getId());
                if (redisUtils.hasKey(key)) {
                    redisUtils.del(key);
                }
                if (operation) {
                    redisUtils.set(key, drug);
                }
            }
        }
    }
}
