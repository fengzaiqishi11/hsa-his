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
import cn.hsa.module.stro.stock.dto.StroStockDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
     * @description 获取当前医院库存列表
     * @author yuelong.chen
     * @date 2021/8/24 10:50
     */
    @Override
    public Map<String, Object> queryAll(Map<String, Object> map) {
        // 获得前端参数
        // 当前第几页
        String currentPageNumber = MapUtils.get(map, "pageNo");
        // 当前页显示多少条
        String totalRecordCount = MapUtils.get(map, "pageSize");
        // 判断是材料还是药品 1：药品 2：材料
        String isDrugOrMaterial = MapUtils.get(map, "isDrugOrMaterial");
        // 医院编码
        String hospCode = MapUtils.get(map, "hospCode");
        // 调用方法 获得当前医院的token
//        Map<String, Object> accessTokenMap = getToken(map);
//        String token = MapUtils.get(accessTokenMap, "accessToken");
//        if (StringUtils.isEmpty(token)) {
//            throw new AppException("医院的token为空，无法调用医保接口");
//        }
        // 调用方法 获得医保注册号
        String insureRegCode = getOrgCode(map);
        if (StringUtils.isEmpty(insureRegCode)) {
            throw new AppException("医院的医保注册号为空，请先配置系统参数HOSP_INSURE_CODE");
        }
        // 封装调用参数
        Map<String, Object> httpParam = new HashMap<String, Object>();
        // 必填参数 accessToken
        httpParam.put("accessToken", "b5577575-0bc4-4c29-ae71-1bd8a1a2e183");
        // 必填参数 currentPageNumber 当前是第几页
        httpParam.put("currentPageNumber", currentPageNumber);
        // todo 这个是我猜的 当前显示多少条
        httpParam.put("totalRecordCount", totalRecordCount);
        // 调医保接口 获得医保返回的库存列表
        Map<String, Object> resultMap = new HashMap<>();
        if ("1".equals(isDrugOrMaterial)){
            // 医保编号为8501 查询药品
            resultMap= this.commonInsureUnified(hospCode, insureRegCode, Constant.UnifiedPay.ZC.UP_8501, httpParam);
        }else {
            // 医保编号为8506 查询材料
            resultMap= this.commonInsureUnified(hospCode, insureRegCode, Constant.UnifiedPay.ZC.UP_8506, httpParam);
        }
        return resultMap;
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
        if (StringUtils.isEmpty(insureRegCode)) {
            throw new RuntimeException("未选择医保机构编码，请选择");
        }
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
        }
        if (!ListUtils.isEmpty(result)) {
            for (Map<String, Object> map : result) {
                BigDecimal splitRatio = MapUtils.get(map, "splitRatio");
                if (BigDecimalUtils.compareTo(splitRatio, new BigDecimal(1)) > 0) {
                    map.put("trdnFla", Constants.SF.S);
                } else {
                    map.put("trdnFla", Constants.SF.F);
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
    public List<Map<String, Object>> queryDrugSells(InsureRecruitPurchaseDTO insureRecruitPurchaseDTO) {
        String hospCode = insureRecruitPurchaseDTO.getHospCode();
        Map map = new HashMap();
        map.put("hospCode",hospCode);
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
        // 封装入参
        Map<String, Object> inptMap = new HashMap<String, Object>();
        inptMap.put("accessToken", token); //调用凭证Token
        inptMap.put("currentPageNumber", insureRecruitPurchaseDTO.getPageNo()); //当前页码数
        inptMap.put("totalPageCount",insureRecruitPurchaseDTO.getPageSize()); // 当前页码最多显示多少页
        inptMap.put("prodIdInfo", insureRecruitPurchaseDTO.getProdIdInfo()); //产品编号集合 非必须
        inptMap.put("sellType", insureRecruitPurchaseDTO.getSellType()); //销售类型 （1：销售 2：退货） 非必须
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("data", inptMap);

        // 调用海南招采公共接口
        Map<String, Object> resultMap = new HashMap<>();
        if ("1".equals(insureRecruitPurchaseDTO.getItemCode())) {
            // 调用药品的接口
            resultMap = this.commonInsureUnified(hospCode, insureRegCode, Constant.UnifiedPay.ZC.UP_8503, data);
        } else {
            // 调用耗材的接口
            resultMap =  this.commonInsureUnified(hospCode, insureRegCode, Constant.UnifiedPay.ZC.UP_8508, data);
        }

        List<Map<String, Object>> list = MapUtils.get(resultMap, "output");
        return list;
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
        List<Map<String, Object>> dataList = MapUtils.get(map, "dataList");
        // 判断是材料还是药品 1：药品 2：材料
        String itemCode = MapUtils.get(map, "itemCode");
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
        // 封装入参
        Map<String, Object> inptMap = new HashMap<String, Object>();
        inptMap.put("accessToken", token); //调用凭证Token
        inptMap.put("sellInfo", dataList); //销售信息

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("data", inptMap);

        // 调用海南招采公共接口
        Map<String, Object> resultMap = new HashMap<>();
        if ("1".equals(itemCode)){
            // 药品销售上传
            resultMap = this.commonInsureUnified(hospCode, insureRegCode, Constant.UnifiedPay.ZC.UP_8504, data);
        }else{
            // 耗材销售上传
            resultMap = this.commonInsureUnified(hospCode, insureRegCode, Constant.UnifiedPay.ZC.UP_8509, data);
        }

        // 费用ids，用于更新上传状态
        return this.updateCostIsUpload(dataList) > 0;
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
        List<Map<String, Object>> dataList = MapUtils.get(map, "dataList");
        // 判断是材料还是药品 1：药品 2：材料
        String itemCode = MapUtils.get(map, "itemCode");
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
        }else{
            // 耗材销售退货
            resultMap = this.commonInsureUnified(hospCode, insureRegCode, Constant.UnifiedPay.ZC.UP_8510, data);
        }
        // 费用ids，用于更新上传状态
        return this.updateCostIsUpload(dataList) > 0;
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
    private int updateCostIsUpload(List<Map<String, Object>> dataList) {
        Map<String, Object> map = new HashMap<>();
        // 费用ids，用于更新上传状态
        List<String> ids = dataList.stream().map(map1 -> (String) MapUtils.get(map1, "id")).collect(Collectors.toList());
        // 更新费用表上传状态
        int num = 0;
        if (!ListUtils.isEmpty(ids)) {
            map.put("ids", ids);
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
        insureConfigurationDTO.setOrgCode(insureRegCode);
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
        String hospCode = MapUtils.get(map,"hospCode");
        String orgCode = getOrgCode(map);
        Map<String,Object> paramMap = new HashMap<>();
        String accessToken = hospCode + Constant.UnifiedPay.ZC.UP_8102;
        String tokenValue = "";
        if(redisUtils.hasKey(accessToken)){
            tokenValue  = redisUtils.get(accessToken);
        }else{
            Map<String, Object> stringObjectMap = commonInsureUnified(hospCode, orgCode, Constant.UnifiedPay.ZC.UP_8102, paramMap);
            Map<String,Object> outputMap =  MapUtils.get(stringObjectMap,"output");
            Map<String,Object> dataMap = MapUtils.get(outputMap, "data");
            tokenValue = MapUtils.get(dataMap,"accessToken");

        }
        redisUtils.set(accessToken,tokenValue,1800);
        map.put("accessToken",tokenValue);
        return map;
    }

    /**
     * @param map
     * @Meth: uploadToInsure
     * @Description: 药品库存上传变更
     * @Param: [map]
     * @return: java.lang.Boolean
     * @Author: zhangguorui
     * @Date: 2021/10/20
     */
    @Override
    public Boolean updateToInsure(Map<String, Object> map) {
        return null;
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
