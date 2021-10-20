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

    /**
     * @Method queryAll
     * @Param [map]
     * @description   获取当前医院库存列表
     * @author yuelong.chen
     * @date 2021/8/24 10:50
     * @return
     * @throws
     */
    @Override
    public Map<String,Object> queryAll(Map<String,Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        return insureRecruitPurchaseDAO.queryAll(map);
    }
    /**
     * @Method insertinvChgMedinsInfo
     * @Param [map]
     * @description    新增、修改当前医院库存表
     * @author yuelong.chen
     * @date 2021/8/24 10:50
     * @return
     * @throws
     */
    @Override
    public void insertinvChgMedinsInfo() {

    }
    /**
     * @Method queryAll
     * @Param [map]
     * @description    新增、修改当前医院库存表
     * @author yuelong.chen
     * @date 2021/8/24 10:50
     * @return
     * @throws
     */
    @Override
    public void updateinvChgMedinsInfo() {

    }
    /**
     * @Method queryCommoditySalesRecord
     * @Param [map]
     * @description   获取当前医院库存列表
     * @author yuelong.chen
     * @date 2021/8/24 10:50
     * @return
     * @throws
     */
    @Override
    public Map<String,Object> queryCommoditySalesRecord(Map<String,Object> map){
        return insureRecruitPurchaseDAO.queryCommoditySalesRecord(map);
    }
    /**
     * @Method queryAll
     * @Param [map]
     * @description   获取当前医院库存列表
     * @author yuelong.chen
     * @date 2021/8/24 10:50
     * @return
     * @throws
     */
    @Override
    public void insertCommoditySalesReturnRecord() {

    }

    /**
     * @Menthod: queryPersonList
     * @Desrciption: 查询(门诊/住院)存在(销售/退货)(药品/材料)记录的人员列表
     * @Param:
     *      1.itemType-药品1，材料2
     *      2.queryType-全院0，门诊1，住院2
     *      3.keyword-搜索条件(姓名、证件号、就诊号/住院号、住院床号)
     *      4.startDate-搜索开始日期
     *      5.endDate-搜索结束日期
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
     * @Param:
     *      1.visitId-就诊id
     *      2.itemType-药品1，材料2
     *      3.queryType-门诊1，住院2
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
     * @Desrciption: 海南招采接口-药品销售列表查询【8503】
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-26 10:27
     * @Return: list
     **/
    @Override
    public List<Map<String, Object>> queryDrugSells(InsureRecruitPurchaseDTO insureRecruitPurchaseDTO) {
        String hospCode = insureRecruitPurchaseDTO.getHospCode();
        String insureRegCode = insureRecruitPurchaseDTO.getInsureRegCode();

        // 封装入参
        Map<String, Object> inptMap = new HashMap<String, Object>();
        inptMap.put("accessToken", ""); //调用凭证Token
        inptMap.put("currentPageNumber", insureRecruitPurchaseDTO.getPageNo()); //当前页码数
        inptMap.put("prodIdInfo", insureRecruitPurchaseDTO.getProdIdInfo()); //产品编号集合 非必须
        inptMap.put("sellType", insureRecruitPurchaseDTO.getSellType()); //销售类型 （1：销售 2：退货） 非必须

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("data", inptMap);

        // 调用海南招采公共接口
        Map<String, Object> resultMap = this.commonInsureUnified(hospCode, insureRegCode, Constant.UnifiedPay.ZC.UP_8503, data);

        List<Map<String, Object>> list = MapUtils.get(resultMap, "output");

        return list;
    }

    /**
     * @Menthod:
     * @Desrciption: 海南招采接口-药品销售【8504】
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
        List<Map<String, Object>> dataList = MapUtils.get(map, "dataList");

        // 封装入参
        Map<String, Object> inptMap = new HashMap<String, Object>();
        inptMap.put("accessToken", ""); //调用凭证Token
        inptMap.put("sellInfo", dataList); //销售信息

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("data", inptMap);

        // 调用海南招采公共接口
        Map<String, Object> resultMap = this.commonInsureUnified(hospCode, insureRegCode, Constant.UnifiedPay.ZC.UP_8504, data);

        // 费用ids，用于更新上传状态
        return this.updateCostIsUpload(dataList) > 0;
    }


    /**
     * @Menthod:
     * @Desrciption: 海南招采接口-药品销售退货【8505】
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
        List<Map<String, Object>> dataList = MapUtils.get(map, "dataList");

        // 封装入参
        Map<String, Object> inptMap = new HashMap<String, Object>();
        inptMap.put("accessToken", ""); //调用凭证Token
        inptMap.put("retInfo", dataList); //销售信息

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("data", inptMap);

        // 调用海南招采公共接口
        Map<String, Object> resultMap = this.commonInsureUnified(hospCode, insureRegCode, Constant.UnifiedPay.ZC.UP_8505, data);

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
        List<String> ids = dataList.stream().map(map1 -> (String)MapUtils.get(map1, "id")).collect(Collectors.toList());
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
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO.setIsValid(Constants.SF.S);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if(insureConfigurationDTO ==null){
            throw new AppException("查询医保机构配置信息为空");
        }

        // 调用海南招采接口
        Map<String, Object> httpParam = new HashMap<String, Object>();
        httpParam.put("infno", functionCode); //交易编号
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode())); //发送方报文ID
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs()); //就医地医保区划
        httpParam.put("recer_sys_code", ""); //接收方系统代码
        httpParam.put("infver", ""); //接口版本号
        httpParam.put("opter_type", ""); //经办人类别
        httpParam.put("opter", ""); //经办人
        httpParam.put("opter_name", ""); //经办人姓名
        httpParam.put("inf_time", DateUtils.getNow()); //交易时间
        httpParam.put("fixmedins_code", ""); //定点医药机构编号
        httpParam.put("fixmedins_name", ""); //定点医药机构名称
        httpParam.put("input", paramMap); //交易输入

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
        String hospCode = MapUtils.get(map,"hospCode");
        String orgCode = getOrgCode(map);
        Map<String,Object> paramMap = new HashMap<>();
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
        Map<String, Object> stringObjectMap = commonInsureUnified(hospCode, orgCode, Constant.UnifiedPay.ZC.UP_8101, paramMap);
        return stringObjectMap;
    }

    /**
     * @Method getOrgCode
     * @Desrciption  获取新医保医疗机构 系统参数配置
     * @Param
     *
     * @Author fuhui
     * @Date   2021/8/26 10:53
     * @Return
     **/
    public String  getOrgCode(Map<String, Object> map){
        map.put("code", "HOSP_INSURE_CODE");
        SysParameterDTO sysParameterDTO = sysParameterService.getParameterByCode(map).getData();
        if(sysParameterDTO == null){
            throw new AppException("请先配置系统参数HOSP_INSURE_CODE:" +"参数值是医疗机构编码");
        }
        return  sysParameterDTO.getValue();
    }
}
