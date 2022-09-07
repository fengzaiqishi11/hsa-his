package cn.hsa.insure.module.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.UnifiedCommon;
import cn.hsa.module.insure.module.bo.InsureRecruitPurchaseBO;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureRecruitPurchaseDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureRecruitPurchaseDTO;
import cn.hsa.module.insure.stock.entity.InsureGoodInfoDelete;
import cn.hsa.module.insure.stock.entity.InsureGoodSell;
import cn.hsa.module.insure.stock.entity.InsureInventoryStockUpdate;
import cn.hsa.module.stro.stock.dto.StroStockDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @Package_name: cn.hsa.module.insure
 * @Class_name:: InsureRecruitPurchaseBOImpl
 * @Description: 海南招采
 * @Author: yuelong.chen
 * @Date: 2021/8/23 16:17
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class InsureRecruitPurchaseBOImpl extends HsafBO implements InsureRecruitPurchaseBO {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private InsureRecruitPurchaseDAO insureRecruitPurchaseDAO;

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    @Resource
    private UnifiedCommon unifiedCommon;

    @Resource
    private SysParameterService sysParameterService;

    @Resource
    private RedisUtils redisUtils;

    /**
     * @return
     * @throws
     * @Method queryAll
     * @Param [map]
     * @description 获取当前医院库存列表 默认获取材料
     * @author yuelong.chen
     * @date 2021/8/24 10:50
     */
    @Override
    public PageDTO queryAll(InsureRecruitPurchaseDTO insureRecruitPurchaseDTO) {
        // 获得前端参数
        // 当前第几页
        int currentPageNumber = insureRecruitPurchaseDTO.getPageNo();
        // 当前页显示多少条
        int totalRecordCount = insureRecruitPurchaseDTO.getPageSize();
        // 判断是材料还是药品 1：药品 2：材料
        String isDrugOrMaterial = insureRecruitPurchaseDTO.getItemCode();
        // 医院编码
        String hospCode = insureRecruitPurchaseDTO.getHospCode();

        String orgCode = insureRecruitPurchaseDTO.getOrgCode();
        // 调用方法 获得当前医院的token
        Map map = new HashMap();
        map.put("hospCode", hospCode);
        map.put("regCode", orgCode);
        Map<String, Object> accessTokenMap = getToken(map);
        String token = MapUtils.get(accessTokenMap, "accessToken");
        if (StringUtils.isEmpty(token)) {
            throw new AppException("医院的token为空，无法调用医保接口");
        }
        // 调用方法 获得医保注册号
        String insureRegCode = getOrgCode(map);
        if (StringUtils.isEmpty(insureRegCode)) {
            throw new AppException("医院的医保注册号为空，请先配置系统参数HOSP_INSURE_CODE");
        }
        // 封装调用参数
        Map<String, Object> httpParam = new HashMap<String, Object>();
        Map<String, Object> inputData = new HashMap<String, Object>();
        // 必填参数 accessToken
        httpParam.put("accessToken", token);
        // 必填参数 currentPageNumber 当前是第几页
        httpParam.put("currentPageNumber", currentPageNumber);
        // todo 这个是我猜的 当前显示多少条
        httpParam.put("totalRecordCount", totalRecordCount);
        inputData.put("data",httpParam);
        // 调医保接口 获得医保返回的库存列表
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> resultMap2 = new HashMap<>();
        if ("1".equals(isDrugOrMaterial)) {
            // 医保编号为8501 查询药品
            resultMap = this.commonInsureUnified(hospCode, orgCode, Constant.UnifiedPay.ZC.UP_8501, inputData);
            resultMap2 = MapUtils.get(MapUtils.get(resultMap, "output"), "dataList");
        } else {
            // 医保编号为8506 查询材料
            resultMap = this.commonInsureUnified(hospCode, orgCode, Constant.UnifiedPay.ZC.UP_8506, inputData);
            resultMap2 = MapUtils.get(MapUtils.get(resultMap, "output"), "data");
        }

        List<Map<String, Object>> list = new ArrayList<>();
        if (!MapUtils.isEmpty(resultMap2)) {
            list = MapUtils.get(resultMap2, "dataList");
        }
        return PageDTO.of(list);
    }

    @Override
    public void insertinvChgMedinsInfo() {

    }

    @Override
    public void updateinvChgMedinsInfo() {

    }


    /**
     * @return
     * @throws
     * @Method queryCommoditySalesRecord
     * @Param [map]
     * @description 获取当前医院库存列表
     * @author yuelong.chen
     * @date 2021/8/24 10:50
     */
    @Override
    public Map<String, Object> queryCommoditySalesRecord(Map<String, Object> map) {
        return insureRecruitPurchaseDAO.queryCommoditySalesRecord(map);
    }

    @Override
    public void insertCommoditySalesReturnRecord() {

    }


    /**
     * @Menthod: queryPersonList
     * @Desrciption: 查询(门诊 / 住院)存在(销售 / 退货)(药品 / 材料)记录的人员列表
     * @Param: 1.itemType-药品1，材料2
     * 2.queryType-全院0，门诊1，住院2
     * 3.keyword-搜索条件(姓名、证件号、就诊号/住院号、住院床号)
     * 4.startDate-搜索开始日期
     * 5.endDate-搜索结束日期
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-31 10:17
     * @Return:
     **/
    @Override
    public PageDTO queryPersonList(InsureRecruitPurchaseDTO insureRecruitPurchaseDTO) {
        String itemType = insureRecruitPurchaseDTO.getItemType();
        String insureRegCode = insureRecruitPurchaseDTO.getInsureRegCode();
        String sellType = insureRecruitPurchaseDTO.getSellType();
        if (StringUtils.isEmpty(itemType)) {
            throw new RuntimeException("未选择业务类型，药品1或者材料2");
        }
//        if (StringUtils.isEmpty(insureRegCode)) {
//            throw new RuntimeException("未选择医保机构编码，请选择");
//        }
        if (StringUtils.isEmpty(sellType)) {
            throw new RuntimeException("未选择业务类型，销售1或者退货2");
        }
        if ("1".equals(itemType)) {
            // 查询存在【药品】销售/退货记录的人员列表
            insureRecruitPurchaseDTO.setItemCode(Constants.XMLB.YP);
        } else {
            // 查询存在【材料】销售/退货记录的人员列表
            insureRecruitPurchaseDTO.setItemCode(Constants.XMLB.CL);
        }
        PageHelper.startPage(insureRecruitPurchaseDTO.getPageNo(), insureRecruitPurchaseDTO.getPageSize());
        List<Map<String, Object>> result = insureRecruitPurchaseDAO.queryPersonList(insureRecruitPurchaseDTO);
        return PageDTO.of(result);
    }

    /**
     * @Menthod: queryItemList
     * @Desrciption: 根据就诊id查询人员对应的药品/材料信息
     * @Param: 1.visitId-就诊id
     * 2.itemType-药品1，材料2
     * 3.queryType-门诊1，住院2
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-09-01 15:13
     * @Return:
     **/
    @Override
    public PageDTO queryItemList(InsureRecruitPurchaseDTO insureRecruitPurchaseDTO) {
        String itemType = insureRecruitPurchaseDTO.getItemType();
        List<Map<String, Object>> result = new ArrayList<>();
        PageHelper.startPage(insureRecruitPurchaseDTO.getPageNo(), insureRecruitPurchaseDTO.getPageSize());
        if ("1".equals(itemType)) {
            // 药品
            insureRecruitPurchaseDTO.setItemCode(Constants.XMLB.YP);
            result = insureRecruitPurchaseDAO.queryDrugList(insureRecruitPurchaseDTO);
        } else {
            // todo 耗材
            insureRecruitPurchaseDTO.setItemCode(Constants.XMLB.CL);
            result = insureRecruitPurchaseDAO.queryDrugList(insureRecruitPurchaseDTO);
        }
        if (!ListUtils.isEmpty(result)) {
            for (Map<String, Object> map : result) {
                // 把查询到的负数转成正数
                BigDecimal selRetnCnt = MapUtils.get(map, "selRetnCnt");
                BigDecimal finlTrnsPric = MapUtils.get(map, "finlTrnsPric");
                if (BigDecimalUtils.lessZero(selRetnCnt)){
                    selRetnCnt = BigDecimalUtils.negate(selRetnCnt);
                }
                if (BigDecimalUtils.lessZero(finlTrnsPric)){
                    finlTrnsPric = BigDecimalUtils.negate(finlTrnsPric);
                }
                map.put("selRetnCnt", selRetnCnt);
                map.put("finlTrnsPric", finlTrnsPric);
                // 判断项目是否存在拆分比，存在则判断是否拆零并赋值
                if (!MapUtils.isEmpty(map, "splitRatio")){
                    BigDecimal splitRatio = MapUtils.get(map, "splitRatio");
                    if (BigDecimalUtils.compareTo(splitRatio, new BigDecimal(1)) > 0) {
                        map.put("trdnFlag", Constants.SF.S);
                    } else {
                        map.put("trdnFlag", Constants.SF.F);
                    }
                }else {
                    throw new AppException("请先维护["+ MapUtils.get(map, "itemName") + "]项目的拆分比" );
                }
                // 判断是否为材料，给材料设置是否处方药标志字段用于上传必填字段
                if (!"1".equals(itemType)) {
                    map.put("rxFlag", Constants.SF.F);
                }
            }
        }
        return PageDTO.of(result);
    }

    /**
     * @Menthod:
     * @Desrciption: 海南招采接口-药品/材料销售列表查询【8503】
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-26 10:27
     * @Return: list
     **/
    @Override
    public PageDTO queryDrugSells(InsureRecruitPurchaseDTO insureRecruitPurchaseDTO) {
        String hospCode = insureRecruitPurchaseDTO.getHospCode();
        String insureRegCode = insureRecruitPurchaseDTO.getInsureRegCode();
        Map map = new HashMap();
        String token = getToken(hospCode, insureRegCode);
        if (StringUtils.isEmpty(token)) {
            throw new AppException("医院的token为空，无法调用医保接口");
        }
        // 封装入参
        Map<String, Object> inptMap = new HashMap<String, Object>();
        inptMap.put("accessToken", token); //调用凭证Token
        inptMap.put("currentPageNumber", insureRecruitPurchaseDTO.getPageNo()); //当前页码数
        inptMap.put("totalPageCount", insureRecruitPurchaseDTO.getPageSize()); // 当前页码最多显示多少页
        inptMap.put("prodIdInfo", insureRecruitPurchaseDTO.getProdIdInfo()); //产品编号集合 非必须
        inptMap.put("sellType", insureRecruitPurchaseDTO.getSellType()); //销售类型 （1：销售 2：退货） 非必须
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("data", inptMap);

        // 调用海南招采公共接口
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> resultMap2 = new HashMap<>();
        if ("1".equals(insureRecruitPurchaseDTO.getItemCode())) {
            // 调用药品的接口
            resultMap = this.commonInsureUnified(hospCode, insureRegCode, Constant.UnifiedPay.ZC.UP_8503, data);
            resultMap2 = MapUtils.get(MapUtils.get(resultMap, "output"), "data");
        } else {
            // 调用耗材的接口
            resultMap = this.commonInsureUnified(hospCode, insureRegCode, Constant.UnifiedPay.ZC.UP_8508, data);
            resultMap2 = MapUtils.get(MapUtils.get(resultMap, "output"), "data");
        }

        List<Map<String, Object>> list = new ArrayList<>();
        if (!MapUtils.isEmpty(resultMap2,"dataList")) {
            list = MapUtils.get(resultMap2, "dataList");
        }
        return PageDTO.of(list);
    }

    /**
     * @Menthod:
     * @Desrciption: 海南招采接口-药品/材料销售【8504】
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-26 10:27
     * @Return: Boolean
     **/
    @Override
    public Boolean addDrugSells(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        if (ObjectUtil.isEmpty(insureRegCode)) {
            throw new AppException("医保机构编码不能为空！");
        }
        List<Map<String, Object>> dataList = MapUtils.get(map, "dataList");
        // 判断是材料还是药品 1：药品 2：材料
        String itemCode = MapUtils.get(map, "itemCode");
        if (StringUtils.isEmpty(itemCode)) {
            throw new AppException("itemCode不能为空！【1：药品 2：材料】");
        }
        String token = getToken(hospCode, insureRegCode);
        if (StringUtils.isEmpty(token)) {
            throw new AppException("医院的token为空，无法调用医保接口");
        }
        // 封装入参
        Map<String, Object> inptMap = new HashMap<String, Object>();
        inptMap.put("accessToken", token ); //调用凭证Token
        inptMap.put("sellInfo", dataList); //销售信息

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("data", inptMap);

        // 调用海南招采公共接口
        Map<String, Object> resultMap = new HashMap<>();
        if ("1".equals(itemCode)){
            // 药品销售上传
            resultMap = this.commonInsureUnified(hospCode, insureRegCode, Constant.UnifiedPay.ZC.UP_8504, data);
        } else if ("2".equals(itemCode)){
            // 耗材销售上传
            resultMap = this.commonInsureUnified(hospCode, insureRegCode, Constant.UnifiedPay.ZC.UP_8509, data);
        }

        // 费用ids，用于更新上传状态
        this.updateCostIsUpload(dataList,Constants.SF.S);

        List<InsureGoodInfoDelete> listData = new ArrayList<>();
        for (Map<String, Object> dataMap : dataList) {
            InsureGoodInfoDelete insureGoodInfoDelete = new InsureGoodInfoDelete();
            insureGoodInfoDelete.setId(SnowflakeUtils.getId());
            insureGoodInfoDelete.setFixmedinsBchno(MapUtil.getStr(dataMap,"fixmedinsBchno"));
            insureGoodInfoDelete.setHospCode(hospCode);
            insureGoodInfoDelete.setUploadTime(DateUtils.getNow());
            insureGoodInfoDelete.setInsureType(insureRegCode);
            insureGoodInfoDelete.setInvDataType("4");
            insureGoodInfoDelete.setCertId(MapUtil.getStr(dataMap,"certId"));
            listData.add(insureGoodInfoDelete);
        }
        return this.updateStroAndSaveResultData(listData, hospCode, "1");
    }


    /**
     * @Menthod:
     * @Desrciption: 海南招采接口-药品/材料 销售退货【8505】
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-26 10:27
     * @Return: Boolean
     **/
    @Override
    public Boolean deleteDrugSells(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String insureRegCode = MapUtils.get(map, "insureRegCode");
        if (ObjectUtil.isEmpty(insureRegCode)) {
            throw new AppException("医保机构编码不能为空！");
        }
        List<Map<String, Object>> dataList = MapUtils.get(map, "dataList");
        // 判断是材料还是药品 1：药品 2：材料
        String itemCode = MapUtils.get(map, "itemCode");
        if (StringUtils.isEmpty(itemCode)) {
            throw new AppException("itemCode不能为空！【1：药品 2：材料】");
        }
        String token = getToken(hospCode, insureRegCode);
        if (StringUtils.isEmpty(token)) {
            throw new AppException("医院的token为空，无法调用医保接口");
        }
        // 封装入参
        Map<String, Object> inptMap = new HashMap<String, Object>();
        inptMap.put("accessToken", token); //调用凭证Token
        inptMap.put("retInfo", dataList); //销售信息

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("data", inptMap);

        // 调用海南招采公共接口
        Map<String, Object> resultMap = new HashMap<>();
        if ("1".equals(itemCode)){
            // 药品销售退货
            resultMap = this.commonInsureUnified(hospCode, insureRegCode, Constant.UnifiedPay.ZC.UP_8505, data);
        }else if ("2".equals(itemCode)){
            // 耗材销售退货
            resultMap = this.commonInsureUnified(hospCode, insureRegCode, Constant.UnifiedPay.ZC.UP_8510, data);
        }
        // 费用ids，用于更新上传状态
        this.updateCostIsUpload(dataList,Constants.SF.F);

        List<InsureGoodInfoDelete> listData = new ArrayList<>();
        for (Map<String, Object> dataMap : dataList) {
            InsureGoodInfoDelete insureGoodInfoDelete = new InsureGoodInfoDelete();
            insureGoodInfoDelete.setId(SnowflakeUtils.getId());
            insureGoodInfoDelete.setFixmedinsBchno(MapUtil.getStr(dataMap,"fixmedinsBchno"));
            insureGoodInfoDelete.setHospCode(hospCode);
            insureGoodInfoDelete.setUploadTime(DateUtils.getNow());
            insureGoodInfoDelete.setInsureType(insureRegCode);
            insureGoodInfoDelete.setInvDataType("4");
            insureGoodInfoDelete.setCertId(MapUtil.getStr(dataMap,"certId"));
            listData.add(insureGoodInfoDelete);
        }
        return this.updateStroAndSaveResultData(listData, hospCode, "0");
    }

    // 获取医保配置信息
    private InsureConfigurationDTO getInsureConfigInfo(String hospCode, String insureRegCode) {
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO.setIsValid(Constants.SF.S);
        return insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
    }

    // 更新门诊/住院费用表上传标志
    private int updateCostIsUpload(List<Map<String, Object>> dataList,String isUpload) {
        Map<String, Object> map = new HashMap<>();
        // 费用ids，用于更新上传状态
        List<String> ids = dataList.stream().map(map1 -> (String) MapUtils.get(map1, "id")).collect(Collectors.toList());
        // 更新费用表上传状态
        int num = 0;
        if (!ListUtils.isEmpty(ids)) {
            map.put("ids", ids);
            map.put("hospCode",MapUtils.get(dataList.get(0),"hospCode"));
            map.put("isUpload",isUpload);
            num = insureRecruitPurchaseDAO.updateCostIsUpload(map);
        }
        return num;
    }

    /**
     * @Method commonInsureUnified
     * @Desrciption 海南招采公共接口方法
     * @Param hospCode:医院编码
     * insureRegCode:医保机构编码
     * functionCode：功能号
     * paramMap:入参
     * @Author fuhui
     * @Date 2021/4/28 19:51
     * @Return
     **/
    private Map<String, Object> commonInsureUnified(String hospCode, String insureRegCode, String functionCode, Map<String, Object> paramMap) {
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO.setIsValid(Constants.SF.S);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if (insureConfigurationDTO == null) {
            throw new AppException("查询医保机构配置信息为空");
        }

        Map httpParam = new HashMap();
        httpParam.put("infno", functionCode);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        httpParam.put("input", paramMap);
        String dataJson = JSONObject.toJSONString(httpParam);
        logger.debug("海南招采接口【" + functionCode + "】入参:" + dataJson);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), dataJson);
        logger.debug("海南招采接口【" + functionCode + "】回参:" + resultJson);
        if (StringUtils.isEmpty(resultJson)) {
            throw new AppException("无法访问统一支付平台");
        }

        Map<String, Object> resultMap = JSONObject.parseObject(resultJson, Map.class);
        if ("999".equals(MapUtils.get(resultMap, "code"))) {
            throw new AppException((String) resultMap.get("msg"));
        }
        if (!"0".equals(MapUtils.get(resultMap, "infcode"))) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        return resultMap;
    }


    /**
     * @Description 海南8102接口医保调用
     * @Author 产品三部-郭来
     * @Date 2022-08-29 16:17
     * @param hospCode
     * @param insureRegCode
     * @param functionCode
     * @param paramMap
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    private Map<String, Object> commonInsure_8102(String hospCode, String insureRegCode, String functionCode, Map<String, Object> paramMap) {
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO.setIsValid(Constants.SF.S);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if (insureConfigurationDTO == null) {
            throw new AppException("查询医保机构配置信息为空");
        }

        Map httpParam = new HashMap();
        httpParam.put("infno", functionCode);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        httpParam.put("input", paramMap);
        String dataJson = JSONObject.toJSONString(httpParam);
        logger.debug("海南招采接口【" + functionCode + "】入参:" + dataJson);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), dataJson);
        logger.debug("海南招采接口【" + functionCode + "】回参:" + resultJson);
        if (StringUtils.isEmpty(resultJson)) {
            throw new AppException("无法访问统一支付平台");
        }
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson, Map.class);
        return resultMap;
    }
    /**
     * @param map
     * @Method selectCommonInterfaceTest
     * @Desrciption 招采接口： 接口连通性测试
     * @Param
     * @Author fuhui
     * @Date 2021/8/26 9:43
     * @Return
     */
    @Override
    public Map<String, Object> selectCommonInterfaceTest(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String orgCode = getOrgCode(map);
        Map<String, Object> paramMap = new HashMap<>();
        Map<String, Object> stringObjectMap = commonInsureUnified(hospCode, orgCode, Constant.UnifiedPay.ZC.UP_8101, paramMap);
        return stringObjectMap;
    }


    /**
     * @param map
     * @Method selectCommonInterfaceTest
     * @Desrciption 招采接口：tOken获取
     * @Param
     * @Author fuhui
     * @Date 2021/8/26 9:43
     * @Return
     */
    @Override
    public Map<String, Object> getToken(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String regCode = MapUtils.get(map, "regCode");
        Map<String, Object> paramMap = new HashMap<>();
        String tokenValue = "";
        Map<String, Object> stringObjectMap = commonInsure_8102(hospCode, regCode, Constant.UnifiedPay.ZC.UP_8102, paramMap);
        Map<String, Object> outputMap = MapUtil.get(stringObjectMap, "output",Map.class);
        Map<String, Object> dataMap = MapUtil.get(outputMap, "data",Map.class);
        tokenValue = MapUtil.getStr(dataMap, "accessToken");
        map.put("accessToken", tokenValue);
        return map;
    }
    /**
     * 海南-查询药品库存变更信息
     * @param insureInventoryStockUpdate
     * @return
     */
    @Override
    public List<InsureInventoryStockUpdate> queryYpInsureInventoryStockUpdatePage(InsureInventoryStockUpdate insureInventoryStockUpdate) {
        PageHelper.startPage(insureInventoryStockUpdate.getPageNo(), insureInventoryStockUpdate.getPageSize());
        String invChgType = insureInventoryStockUpdate.getInvChgType();
        String itemCode = insureInventoryStockUpdate.getItemCode();
        if (StringUtils.isEmpty(invChgType)) {
            throw new AppException("变更类型不能为空！！！");
        }
        if (StringUtils.isEmpty(itemCode)) {
            throw new AppException("项目类别【itemCode】不能为空！！！");
        }

        List<String> outinCodeList = new ArrayList<>();
        Map<String, List<String>> map = new HashMap();
        // 封装参数 后期把数字维护到常量或者枚举类里面
        // 调拨入库 对应 本地进销存 没有,先设置为它本身
        map.put("101", Arrays.asList("101"));
        // 调拨出库 对应 本地进销存 没有,先设置为它本身
        map.put("102", Arrays.asList("102"));
        // 盘盈 对应 本地进销存 7 盘盈盘亏
        map.put("103", Arrays.asList("7"));
        // 盘存 对应 本地进销存 8 报损报溢
        map.put("104", Arrays.asList("8"));
        // 销毁 对应 本地进销存 没有,先设置为它本身
        map.put("105", Arrays.asList("105"));
        // 其他入库 对应本地进销存 门诊退药、住院退药
        map.put("106", Arrays.asList("25", "28"));
        // 其他出库 对应本地进销存 门诊发药、住院发药
        map.put("107", Arrays.asList("23", "27"));
        // 初始化入库 对应 本地进销存 1,2 采购入库、直接入库
        map.put("108", Arrays.asList("1", "2"));
        outinCodeList = MapUtils.get(map, invChgType);
        if (ListUtils.isEmpty(outinCodeList)) {
            throw new AppException("请选择库存变更类型");
        } else {
            insureInventoryStockUpdate.setOutinCodeList(outinCodeList);
        }

        try{
            //调用海南医保库存查询接口
            InsureRecruitPurchaseDTO insureRecruitPurchaseDTO = new InsureRecruitPurchaseDTO();
            // 医院编码
            String hospCode = insureInventoryStockUpdate.getHospCode();
            insureRecruitPurchaseDTO.setHospCode(hospCode);
            insureRecruitPurchaseDTO.setItemCode(itemCode);
            insureRecruitPurchaseDTO.setPageNo(insureInventoryStockUpdate.getPageNo());
            insureRecruitPurchaseDTO.setPageSize(insureInventoryStockUpdate.getPageSize());
            insureRecruitPurchaseDTO.setOrgCode(insureInventoryStockUpdate.getInsureRegCode());
            this.queryAll(insureRecruitPurchaseDTO);
        }catch(Exception e){
            e.printStackTrace();
            logger.info("调用海南招采库存查询接口失败！");
        }finally {

        }
        List<InsureInventoryStockUpdate> list = insureRecruitPurchaseDAO.queryInsureInventoryStockUpdatePage(insureInventoryStockUpdate);
        return list;
    }

    @Override
    public String getToken(String hospCode,String insureRegCode) {
        Map<String, Object> paramMap = new HashMap<>();
        Map<String, Object> stringObjectMap = commonInsure_8102(hospCode, insureRegCode, Constant.UnifiedPay.ZC.UP_8102, paramMap);
        Map<String, Object> outputMap = MapUtil.get(stringObjectMap, "output",Map.class);
        Map<String, Object> dataMap = MapUtil.get(outputMap, "data",Map.class);
        String tokenValue = MapUtil.getStr(dataMap, "accessToken");
        return tokenValue;
    }


    /**
     * @Meth: uploadToInsure
     * @Description: 药品/材料 库存上传变更
     * 1.查询本地库存表中没有上传的数据 通过字段 is_upload_to_insure，还需要匹配医保匹配表
     * 2.转换参数（在sql中进行转换）
     * 3.获得医院的token 、 医保机构编码
     * 4. 封装参数 调用接口上传
     * 5.成功之后回写库存表
     * @Param: [map]
     * @return: java.lang.Boolean
     * @Author: zhangguorui
     * @Date: 2021/10/20
     */
    @Override
    public Boolean updateToInsure(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String regCode = MapUtils.getEmptyErr(map, "orgCode", "医保机构编码不能为空！");
        //------- 1. 查询本地库存表中需要上传的数据 begin ----------
        if(ObjectUtil.isEmpty(MapUtils.get(map, "itemCode"))){
            throw new AppException("项目编码【itemCode】不能为空！");
        }
        // 判断是材料还是药品 1：药品 2：材料
        String isDrugOrMaterial = MapUtils.get(map, "itemCode");
        // 封装查询参数
        StroStockDTO stroStockDTO = new StroStockDTO();
        stroStockDTO.setHospCode(hospCode);
        stroStockDTO.setIsUploadToInsure(Constants.SF.F);
        stroStockDTO.setItemCode(isDrugOrMaterial);
        // ------- 查询本地库存表中需要上传的数据 end ----------

        // -----------3.获得医院的token 、 医保机构编码 begin-------------
        // 调用方法 获得当前医院的token
        map.put("regCode",regCode);
        Map<String, Object> accessTokenMap = getToken(map);
        String token = MapUtils.get(accessTokenMap, "accessToken");
        if (StringUtils.isEmpty(token)) {
            throw new AppException("医院的token为空，无法调用医保接口");
        }
        // 调用方法 获得医保注册号
        String insureRegCode = getOrgCode(map);
        if (StringUtils.isEmpty(insureRegCode)) {
            throw new AppException("医院的医保注册号为空，请先配置系统参数HOSP_INSURE_CODE");
        }
        // -----------3.获得医院的token 、 医保机构编码 end-------------

        //------------- 4.封装参数 调用接口上传 begin --------
        // 封装调用参数
        Map<String, Object> httpParam = new LinkedHashMap<>();
        // 必填参数 accessToken
        httpParam.put("accessToken", token);
        // 调医保接口 获得医保返回的库存列表 医保编号为
        Map<String, Object> resultMap = new HashMap<>();
        String certId = MapUtil.getStr(map, "crteId");
        List<InsureInventoryStockUpdate> listInsureInventoryStockUpdate = MapUtils.getEmptyErr(map, "listInsureInventoryStockUpdate", "未获取到需要上传的数据！");
        if (!ListUtils.isEmpty(listInsureInventoryStockUpdate)) {
            listInsureInventoryStockUpdate = JSONObject.parseArray(JSONObject.toJSONString(listInsureInventoryStockUpdate), InsureInventoryStockUpdate.class);
        }
        List<Map<String, Object>> listMap = new ArrayList<>();
        Map<String, Object> dataMap = null;
        Map<String, Object> inputData = new LinkedHashMap<>();
        for (InsureInventoryStockUpdate insureInventoryStockUpdate : listInsureInventoryStockUpdate) {
            if(ObjectUtil.isEmpty(insureInventoryStockUpdate.getPric())||
                    new BigDecimal(insureInventoryStockUpdate.getPric()).doubleValue()<0){
                throw new AppException("商品价格不能为空或商品价格不能小于0，请核对上传数据"+"，商品编码为【"+insureInventoryStockUpdate.getFixmedinsHilistId()+"】");
            }
            if(ObjectUtil.isEmpty(insureInventoryStockUpdate.getCnt())||
                    new BigDecimal(insureInventoryStockUpdate.getCnt()).doubleValue()<0){
                throw new AppException("商品数量不能为空或商品数量不能小于0，请核对上传数据"+"，商品编码为【"+insureInventoryStockUpdate.getFixmedinsHilistId()+"】");
            }
            dataMap = new LinkedHashMap<>();
            dataMap.put("prodCode", insureInventoryStockUpdate.getFixmedinsHilistId());//,insureInventoryStockUpdate.getMedListCodg())	;//产品唯一编码
            dataMap.put("fixmedinsHilistId", insureInventoryStockUpdate.getFixmedinsHilistId());//定点医药机构目录编号
            dataMap.put("fixmedinsHilistName", insureInventoryStockUpdate.getFixmedinsHilistName());//定点医药机构目录名称
            dataMap.put("rxFlag", insureInventoryStockUpdate.getRxFlag());//处方药标志
            dataMap.put("invdate", insureInventoryStockUpdate.getInvChgTime());//库存变更时间
            dataMap.put("manuLotnum",insureInventoryStockUpdate.getFixmedinsBchno());
            dataMap.put("fixmedinsBchno", insureInventoryStockUpdate.getFixmedinsBchno());//定点医药机构批次流水号
            dataMap.put("manuDate","");
            dataMap.put("expyEnd","");
            dataMap.put("memo", "");//备注
            dataMap.put("invChgType", insureInventoryStockUpdate.getInvChgType());//库存变更类型
            dataMap.put("pric",new BigDecimal(insureInventoryStockUpdate.getPric()).doubleValue());//单价
            dataMap.put("invCnt", new BigDecimal(insureInventoryStockUpdate.getCnt()).doubleValue());//数量
            dataMap.put("trdnFlag", insureInventoryStockUpdate.getTrdnFlag());//拆零标志
            listMap.add(dataMap);
        }
        if (listMap.size() > 100){
               int toIndex = 100;
               for (int i =0; i<listMap.size();i+= 100){
                   if (i + 100 > listMap.size()){
                       toIndex = listMap.size() - i;
                   }
                   List newList = listMap.subList(i,i + toIndex);
                   httpParam.put("invChgMedinsInfo", newList);
                   inputData.put("data",httpParam);
                   if ("1".equals(isDrugOrMaterial)){
                       // 药品变更上传
                       resultMap = this.commonInsureUnified(hospCode, regCode, Constant.UnifiedPay.ZC.UP_8502, inputData);
                   } else  if ("2".equals(isDrugOrMaterial)){
                       // 材料变更上传
                       resultMap = this.commonInsureUnified(hospCode, regCode, Constant.UnifiedPay.ZC.UP_8507, inputData);
                   }
                   try {
                       // 因为调用间隔不能低于五秒，所以暂时休眠五秒
                       Thread.sleep(1000*5);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
        } else{
            httpParam.put("invChgMedinsInfo", listMap);
            inputData.put("data",httpParam);
            if ("1".equals(isDrugOrMaterial)){
                // 药品变更上传
               resultMap = this.commonInsureUnified(hospCode, regCode, Constant.UnifiedPay.ZC.UP_8502, inputData);
            } else if ("2".equals(isDrugOrMaterial)){
                // 材料变更上传
                resultMap = this.commonInsureUnified(hospCode, regCode, Constant.UnifiedPay.ZC.UP_8507, inputData);
            }
        }
        //------------- 4.封装参数 调用接口上传 end --------

        // 5.如果上传成功 回写库存表
        if ("0".equals(MapUtils.get(resultMap, "infcode"))) {
            // 获得修改的ids

            List<InsureGoodInfoDelete> listData = new ArrayList<>();
            for (InsureInventoryStockUpdate insureInventoryStockUpdate : listInsureInventoryStockUpdate) {
                InsureGoodInfoDelete insureGoodInfoDelete = new InsureGoodInfoDelete();
                insureGoodInfoDelete.setId(SnowflakeUtils.getId());
                insureGoodInfoDelete.setFixmedinsBchno(insureInventoryStockUpdate.getFixmedinsBchno());
                insureGoodInfoDelete.setHospCode(hospCode);
                insureGoodInfoDelete.setInsureType(regCode);
                insureGoodInfoDelete.setUploadTime(DateUtils.getNow());
                insureGoodInfoDelete.setInvDataType("2");
                insureGoodInfoDelete.setCertId(certId);
                listData.add(insureGoodInfoDelete);
            }

            this.updateStroAndSaveResultData(listData, hospCode, "1");
            return true;
        } else {
            return false;
        }
    }
    /**
     * @Meth: updateStroAndSaveResultData
     * @Description: 上传之后更新进销存、并插入到医保上传表
     * 注意： resultList中的对象必须要有 id, hosp_code,insure_type, fixmedins_bchno, inv_data_type, upload_time, cert_id
     * statusCode : 0 ： 未上传    1：采购/销售 上传  2：退货
     * @Param: [resultList, hospCode, statusCode]
     * @return: boolean
     * @Author: zhangguorui
     * @Date: 2021/11/5
     */
    private boolean updateStroAndSaveResultData(List<InsureGoodInfoDelete> resultList, String hospCode, String statusCode) {
        if (!ListUtils.isEmpty(resultList)) {
            // 过滤出进销存id
            List<String> ids = resultList.stream().map(InsureGoodInfoDelete::getFixmedinsBchno).collect(Collectors.toList());
            //查询已上传的商品
            List<InsureGoodInfoDelete> fixmedinsBchnos = insureRecruitPurchaseDAO.queryStockUpBatch(ids);
            //过滤出需要新增上传表的数据
            resultList.removeIf(dto->fixmedinsBchnos.contains(dto.getFixmedinsBchno()));
            // 更新进销存的上传状态
            insureRecruitPurchaseDAO.updateStatus(ids, hospCode, statusCode);
            // 插入上传表
            insureRecruitPurchaseDAO.insertStockUploadBatch(resultList);
        }
        return true;
    }
    /**
     * @Method getOrgCode
     * @Desrciption 获取新医保医疗机构 系统参数配置
     * @Param
     * @Author fuhui
     * @Date 2021/8/26 10:53
     * @Return
     **/
    public String getOrgCode(Map<String, Object> map) {
        map.put("code", "HOSP_INSURE_CODE");
        SysParameterDTO sysParameterDTO = sysParameterService.getParameterByCode(map).getData();
        if (sysParameterDTO == null) {
            throw new AppException("请先配置系统参数HOSP_INSURE_CODE:" + "参数值是医疗机构编码");
        }
        return sysParameterDTO.getValue();
    }
}
