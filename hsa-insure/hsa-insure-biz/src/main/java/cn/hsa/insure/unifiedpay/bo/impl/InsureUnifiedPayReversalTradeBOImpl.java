package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.InsureUnifiedCommonUtil;
import cn.hsa.insure.util.NumberToCN;
import cn.hsa.module.insure.inpt.service.InsureUnifiedBaseService;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualCostDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.outpt.bo.InsureUnifiedPayReversalTradeBO;
import cn.hsa.module.insure.outpt.dao.InsureReversalTradeDAO;
import cn.hsa.module.insure.outpt.dto.InsureReversalTradeDTO;
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
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.bo.impl
 * @Class_name: insureUnifiedPayReversalTradeBOImpl
 * @Describe: 医保通一支付平台, 冲正交易
 * @Author: liuqi1
 * @Eamil: liuqi1@powersi.com.cn
 * @Date: 2021/4/12 11:16
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@Component
public class InsureUnifiedPayReversalTradeBOImpl extends HsafBO implements InsureUnifiedPayReversalTradeBO {
    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;
    @Resource
    InsureReversalTradeDAO insureReversalTradeDAO;

    @Resource
    InsureIndividualVisitDAO insureIndividualVisitDAO;

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private InsureUnifiedCommonUtil insureUnifiedCommonUtil;

    @Resource
    private InsureUnifiedBaseService insureUnifiedBaseService;

    @Resource
    private InsureIndividualCostDAO insureIndividualCostDAO;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private NumberToCN numberToCN;


    /**
     * 医保通一支付平台,冲正交易接口调用
     *
     * @param map
     * @Method UP_2601
     * @Desrciption
     * @Author liuqi1
     * @Date 2021/4/12 11:18
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     **/
    @Override
    public Boolean updateUP_2601(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        InsureReversalTradeDTO insureReversalTradeDTO = MapUtils.get(map, "insureReversalTradeDTO");
        String insureRegCode = insureReversalTradeDTO.getInsureRegCode();

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("psn_no", insureReversalTradeDTO.getPsnNo()); // 人员编号
        dataMap.put("omsgid", insureReversalTradeDTO.getOmsgid());// 原发送方报文ID
        dataMap.put("oinfno", insureReversalTradeDTO.getOinfno());//原交易编号

        //调用医保统一支付，返回调用结果
        Map<String, Object> resultMap = invokingUpay(hospCode, insureRegCode, Constant.UnifiedPay.OUTPT.UP_2601, dataMap);

        String infcode = String.valueOf(resultMap.get("infcode"));
        if ("0".equals(infcode)) {
            InsureReversalTradeDTO dto = new InsureReversalTradeDTO();
            dto.setId(SnowflakeUtils.getId());
            dto.setHospCode(hospCode);
            dto.setOinfno(String.valueOf(dataMap.get("oinfno")));
            dto.setOinfname(String.valueOf(map.get("oinfname")));
            dto.setOmsgid(String.valueOf(dataMap.get("omsgid")));
            dto.setPsnNo(String.valueOf(dataMap.get("psn_no")));
            dto.setCrteId(String.valueOf(map.get("userId")));
            dto.setCrteName(String.valueOf(map.get("userName")));
            dto.setCrteTime(DateUtils.getNow());
            //新增冲正交易记录
            int num = insureReversalTradeDAO.insertInsureReversalTrade(dto);
            if (num > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            throw new AppException("错误信息：" + MapUtils.get(resultMap, "err_msg"));
        }

    }

    /**
     * 调用医保统一支付，返回调用结果
     *
     * @param hospCode      医院编码
     * @param insureRegCode 医保注册编码
     * @param infno         功能号
     * @param dataMap       接口数据
     * @Method invokingUpay
     * @Desrciption
     * @Author liuqi1
     * @Date 2021/4/13 10:55
     * @Return java.lang.String
     **/
    @Override
    public Map<String, Object> invokingUpay(String hospCode, String insureRegCode, String infno, Map<String, Object> dataMap) {
        //获得医保配置信息
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);

        Map<String, Object> setDataMap = new HashMap<>();
        setDataMap.put("data", dataMap);

        //封装统一支付接口入参
        Map httpParam = new HashMap();
        httpParam.put("infno", infno);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号

        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        if ("1".equals(MapUtils.get(dataMap, "flag"))) {
            dataMap.remove("flag");
            httpParam.put("input", dataMap);
        } else {
            httpParam.put("input", setDataMap);
        }

        String jsonStr = JSONObject.toJSONString(httpParam);
        String url = insureConfigurationDTO.getUrl();

        logger.info("时间：" + DateUtils.format() + "  url:" + url + "   统一支付平台入参：" + jsonStr);
        String resultJson = HttpConnectUtil.sendPostToCall(url, jsonStr);
        if (StringUtils.isEmpty(resultJson)) {
            throw new AppException("系统异常，调用医保接口返参为空");
        }
        int lengthStr = resultJson.length() > 800 ? 800 : resultJson.length();
        logger.info("时间：" + DateUtils.getNow() + "  统一支付平台回参:" + resultJson.substring(0, lengthStr) + "......");
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson);

        if ("-1".equals(MapUtils.get(resultMap, "infcode"))) {
            throw new AppException("调用医保接口失败:" + MapUtils.get(resultMap, "err_msg"));
        }
        return resultMap;
    }

    /**
     * 冲正交易查询
     *
     * @param insureReversalTradeDTO
     * @Method queryPage
     * @Desrciption
     * @Author liuqi1
     * @Date 2021/4/12 19:48
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryReversalTradePage(InsureReversalTradeDTO insureReversalTradeDTO) {
        // 设置分页信息
        PageHelper.startPage(insureReversalTradeDTO.getPageNo(), insureReversalTradeDTO.getPageSize());
        // 根据条件查询所
        List<InsureReversalTradeDTO> insureReversalTradeDTOS = insureReversalTradeDAO.queryReversalTradePage(insureReversalTradeDTO);
        return PageDTO.of(insureReversalTradeDTOS);
    }


    /**
     * 医药机构费用结算对总账数据查询
     *
     * @param map
     * @Method queryDataWith3201
     * @Desrciption
     * @Author liuqi1
     * @Date 2021/4/13 20:18
     * @Return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     **/
    @Override
    public PageDTO queryDataWith5261(Map<String, Object> map) {
        int pageNo = Integer.parseInt(MapUtils.get(map, "pageNo") == null ? "1" : MapUtils.get(map, "pageNo"));
        int pageSize = Integer.parseInt(MapUtils.get(map, "pageSize") == null ? "10" : MapUtils.get(map, "pageSize"));

        // 设置分页信息
        PageHelper.startPage(pageNo, pageSize);
        // 根据条件查询所
        List<Map<String, Object>> list = insureReversalTradeDAO.queryDataWith5261(map);
        return PageDTO.of(list);
    }

    /**
     * 医药机构费用结算对总账查询
     *
     * @param map
     * @Method queryDataWith3201
     * @Desrciption
     * @Author liuqi1
     * @Date 2021/4/16 11:20
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryDataWith3201(Map<String, Object> map) {
        int pageNo = Integer.parseInt(MapUtils.get(map, "pageNo") == null ? "1" : MapUtils.get(map, "pageNo"));
        int pageSize = Integer.parseInt(MapUtils.get(map, "pageSize") == null ? "10" : MapUtils.get(map, "pageSize"));

        // 设置分页信息
        PageHelper.startPage(pageNo, pageSize);

        //设置对账时间
        if (StringUtils.isEmpty(MapUtils.get(map, "startDate"))) {
            map.put("startDate", DateUtils.format(DateUtils.getNow(), DateUtils.Y_M_D));
        }
        if (StringUtils.isEmpty(MapUtils.get(map, "endDate"))) {
            map.put("endDate", DateUtils.format(DateUtils.getNow(), DateUtils.Y_M_D));
        }

        // 是否退费，REFD_SETL_FLAG = 1 则只查询正常的
        Map<String,Object> selectParamterCodeMap = new HashMap<>();
        selectParamterCodeMap.put("hospCode",MapUtils.get(map,"hospCode"));
        selectParamterCodeMap.put("code","REFD_SETL_FLAG");
        WrapperResponse<SysParameterDTO> wr = sysParameterService_consumer.getParameterByCode(selectParamterCodeMap);
        if (wr != null && wr.getData() != null && "1".equals(wr.getData().getValue()) ) {
            map.put("state","0");
        }

        // 根据条件查询所
        List<Map<String, Object>> list = insureReversalTradeDAO.queryDataWith3201(map);

        //根据险种类型 和 清算类别 ，获得对应的 费用结算明细
        map.put("list", list);
        List<Map<String, Object>> list3202 = insureReversalTradeDAO.queryDataWith3202(map);
        List<Map<String, Object>> innerList = null;
        for (Map om : list) {
            innerList = new ArrayList<>();
            for (Map<String, Object> im : list3202) {
                if (MapUtils.get(om, "insutype").equals(MapUtils.get(im, "insutype")) &&
                        MapUtils.get(om, "is_hospital").equals(MapUtils.get(im, "is_hospital"))) {
                    innerList.add(im);
                }
            }
            om.put("detailList", innerList);
            om.put("stmt_begndate", MapUtils.get(map, "startDate"));
            om.put("stmt_enddate", MapUtils.get(map, "endDate"));
        }

        return PageDTO.of(list);
    }

    /**
     * 医保通一支付平台,医药机构费用结算对总账接口调用
     *
     * @param parameterMap
     * @Method updateUP_3201
     * @Desrciption
     * @Author liuqi1
     * @Date 2021/4/13 17:02
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     **/
    @Override
    public Map<String, Object> updateUP_3201(Map<String, Object> parameterMap) {
        String hospCode = MapUtils.get(parameterMap, "hospCode");
        String insureRegCode = MapUtils.get(parameterMap, "insureRegCode");

        //封装统一支付接口入参
        Map dataMap = new HashMap();
        dataMap.put("insutype", MapUtils.get(parameterMap, "insutype"));//险种
        dataMap.put("clr_type", MapUtils.get(parameterMap, "clr_type"));//清算类别
        dataMap.put("REFD_SETL_FLAG", '0'); // 是否包含退费数据

        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if (insureConfigurationDTO == null) {
            throw new AppException("未查询到医保配置信息！");
        }
        dataMap.put("setl_optins", insureConfigurationDTO.getMdtrtareaAdmvs());//结算经办机构 (统筹区规划)
        dataMap.put("stmt_begndate", MapUtils.get(parameterMap, "stmt_begndate"));//对账开始日期
        dataMap.put("stmt_enddate", MapUtils.get(parameterMap, "stmt_enddate"));//对账结束日期


        // 保留两位小数
        String medfeeSumamt = MapUtils.get(parameterMap, "medfee_sumamt").toString();
        DecimalFormat df1 = new DecimalFormat("0.00");
        dataMap.put("medfee_sumamt", df1.format(BigDecimalUtils.convert(medfeeSumamt))); //医疗费总额
        dataMap.put("fund_pay_sumamt", MapUtils.get(parameterMap, "fund_pay_sumamt"));//基金支付总额
        dataMap.put("acct_pay", MapUtils.get(parameterMap, "acct_pay"));//个人账户支付金额
        dataMap.put("fixmedins_setl_cnt", MapUtils.get(parameterMap, "fixmedins_setl_cnt"));//定点医药机构结算笔数
        Map<String, Object> resultMap = invokingUpay(hospCode, insureRegCode, Constant.UnifiedPay.OUTPT.UP_3201, dataMap);
        return resultMap;
    }

    /**
     * 医保通一支付平台,医药机构费用结算对明细账接口调用
     *
     * @param parameterMap
     * @Method updateUP_3202
     * @Desrciption
     * @Author liuqi1
     * @Date 2021/4/13 17:30
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     **/
    @Override
    public Map<String, Object> updateUP_3202(Map<String, Object> parameterMap) {
        Map<String, Object> resultMap = new HashMap<>();
        String hospCode = MapUtils.get(parameterMap, "hospCode");
        String insureRegCode = MapUtils.get(parameterMap, "insureRegCode");
        String stmtBegndate = MapUtils.get(parameterMap, "stmt_begndate");
        String stmtEnddate = MapUtils.get(parameterMap, "stmt_enddate");

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("setl_optins", MapUtils.get(parameterMap, "clr_optins"));//结算经办机构
        dataMap.put("clr_type", MapUtils.get(parameterMap, "clr_type"));//结算经办机构
        dataMap.put("stmt_begndate", stmtBegndate);//对账开始日期
        dataMap.put("stmt_enddate", stmtEnddate);//对账结束日期
        dataMap.put("medfee_sumamt", MapUtils.get(parameterMap, "medfee_sumamt"));//医疗费总额
        dataMap.put("fund_pay_sumamt", MapUtils.get(parameterMap, "fund_pay_sumamt"));//基金支付总额
        dataMap.put("cash_payamt", MapUtils.get(parameterMap, "person_price"));//现金支付金额
        dataMap.put("fixmedins_setl_cnt", MapUtils.get(parameterMap, "fixmedins_setl_cnt"));//定点医药机构结算笔数
        dataMap.put("fixmedins_code", MapUtils.get(parameterMap, "fixmedins_code"));// 医疗机构编码
        dataMap.put("REFD_SETL_FLAG", MapUtils.get(parameterMap, "refdSetlFlag")); // 是否包含退费数据
        dataMap.put("fixmedins_refd_setl_flag", MapUtils.get(parameterMap, "refdSetlFlag"));//定点医药机构结算笔数

        //设置对账时间
        Map<String, Object> selectDataListMap = new HashMap();
        selectDataListMap.put("startDate", stmtBegndate);
        selectDataListMap.put("endDate", stmtEnddate);
        selectDataListMap.put("clrType", MapUtils.get(parameterMap, "clr_type"));
        selectDataListMap.put("hospCode", hospCode);
        selectDataListMap.put("insureRegCode", insureRegCode);
        selectDataListMap.put("refdSetlFlag", MapUtils.get(parameterMap, "refdSetlFlag"));

        // 是否退费，REFD_SETL_FLAG = 1 则只查询正常的
        Map<String,Object> selectParamterCodeMap = new HashMap<>();
        selectParamterCodeMap.put("hospCode",hospCode);
        selectParamterCodeMap.put("code","REFD_SETL_FLAG");
        WrapperResponse<SysParameterDTO> wr = sysParameterService_consumer.getParameterByCode(selectParamterCodeMap);
        if (wr != null && wr.getData() != null && "1".equals(wr.getData().getValue()) ) {
            selectDataListMap.put("state","0");
        }


        List<Map<String, Object>> datailList = insureReversalTradeDAO.queryDataWith3202(selectDataListMap);
        if (ListUtils.isEmpty(datailList)) {
            throw new AppException("对明细账失败：未获取到费用明细信息！");
        }
        Map<String, Object> httpParam = new HashMap<>();
        httpParam.put("data", dataMap);
        httpParam.put("fsUploadIn", datailList);
        httpParam.put("flag", "1");
        resultMap = invokingUpay(hospCode, insureRegCode, Constant.UnifiedPay.OUTPT.UP_3202, httpParam);
        return resultMap;
    }

    /**
     * 医保通一支付平台,结算单查询接口调用
     *
     * @param parameterMap
     * @Method updateUP_5261
     * @Desrciption
     * @Author liuqi1
     * @Date 2021/4/13 17:30
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     **/
    @Override
    public Map<String, Object> updateUP_5261(Map<String, Object> parameterMap) {
        String hospCode = MapUtils.get(parameterMap, "hospCode");
        String insureRegCode = MapUtils.get(parameterMap, "insureRegCode");

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("mdtrt_id", MapUtils.get(parameterMap, "mdtrtId"));//就诊ID
        dataMap.put("setl_id", MapUtils.get(parameterMap, "setlId"));//结算ID
        dataMap.put("psn_no", MapUtils.get(parameterMap, "psnNo"));//人员编号

        Map<String, Object> resultMap = invokingUpay(hospCode, insureRegCode, Constant.UnifiedPay.OUTPT.UP_5261, dataMap);
        //Map<String, Object> resultMap = JSONObject.parseObject("{\t\"output\":{\t\t\"setlinfo\":{\t\t\t\"setl_time\":\"2021-04-09 13:49:42\",\t\t\t\"cvlserv_pay\":0.00,\t\t\t\"insu_admdvs\":\"\",\t\t\t\"med_fee_sumamt\":3300.00,\t\t\t\"year\":\"\",\t\t\t\"med_type\":\"2101\",\t\t\t\"psn_pay\":\"\",\t\t\t\"opter_id\":\"\",\t\t\t\"begndate\":\"\",\t\t\t\"hifmi_pay\":0.00,\t\t\t\"flxempe_flag\":\"\",\t\t\t\"psn_no\":\"46000000000000003501209658\",\t\t\t\"act_pay_dedc\":0.00,\t\t\t\"medins_setl_id\":\"H46010500002202104091349412420\",\t\t\t\"acct_mulaid_pay\":\"\",\t\t\t\"clr_way\":\"3\",\t\t\t\"oth_pay\":0,\t\t\t\"gend\":\"2\",\t\t\t\"mdtrt_id\":\"310000088057\",\t\t\t\"acct_pay\":0.00,\t\t\t\"fix_blng_admdvs\":\"\",\t\t\t\"insutype\":\"310\",\t\t\t\"inscp_scp_amt\":\"\",\t\t\t\"invono\":\"\",\t\t\t\"psn_part_amt\":0,\t\t\t\"enddate\":\"\",\t\t\t\"cash_payamt\":\"\",\t\t\t\"psn_type\":\"1101\",\t\t\t\"hifp_pay\":0.00,\t\t\t\"fixmedins_name\":\"\",\t\t\t\"overlmt_selfpay\":0.00,\t\t\t\"pay_loc\":\"\",\t\t\t\"clr_type\":\"21\",\t\t\t\"othfund_pay\":\"\",\t\t\t\"refd_setl_flag\":\"\",\t\t\t\"psnCash_pay\":0,\t\t\t\"cvlserv_flag\":\"0\",\t\t\t\"emp_name\":\"\",\t\t\t\"brdy\":\"1983-02-27 00:00:00\",\t\t\t\"naty\":\"1\",\t\t\t\"dise_codg\":\"\",\t\t\t\"fixmedins_code\":\"\",\t\t\t\"mdtrt_cert_type\":\"02\",\t\t\t\"opter_name\":\"\",\t\t\t\"dedc_hosp_lv\":\"\",\t\t\t\"balc\":0.00,\t\t\t\"psn_cert_type\":\"01\",\t\t\t\"fixmedins_poolarea\":\"\",\t\t\t\"hifob_pay\":0.00,\t\t\t\"nwb_flag\":\"\",\t\t\t\"inscp_amt\":\"\",\t\t\t\"hifes_pay\":0.00,\t\t\t\"cert_no\":\"460003198302276864\",\t\t\t\"fund_pay_sumamt\":0.00,\t\t\t\"opt_time\":\"\",\t\t\t\"fulamt_ownpay_amt\":3300.00,\t\t\t\"setl_id\":\"300000078066\",\t\t\t\"hosp_part_amt\":0,\t\t\t\"psn_name\":\"鍒樻檽鐕�\",\t\t\t\"maf_pay\":0.00,\t\t\t\"prese_ifpay_amt\":0.00,\t\t\t\"dise_no\":\"\",\t\t\t\"clr_optins\":\"460400\",\t\t\t\"pool_prop_selfpay\":0.0000,\t\t\t\"dise_name\":\"\",\t\t\t\"lmtpric_hosp_lv\":\"\",\t\t\t\"age\":38.0,\t\t\t\"hosp_lv\":\"\"\t\t}\t},\t\"infcode\":\"0\",\t\"warn_msg\":\"\",\t\"cainfo\":\"\",\t\"err_msg\":\"\",\t\"refmsg_time\":\"20210415113416064\",\t\"signtype\":\"\",\t\"respond_time\":\"20210415113416108\",\t\"inf_refmsgid\":\"\"}");

        return resultMap;
    }

    /**
     * 医保通一支付平台,结算信息查询接口调用
     *
     * @param parameterMap
     * @Method updateUP_5262
     * @Desrciption
     * @Author liuqi1
     * @Date 2021/4/13 17:30
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     **/
    @Override
    public Map<String, Object> updateUP_5262(Map<String, Object> parameterMap) {
        String hospCode = MapUtils.get(parameterMap, "hospCode");
        String insureRegCode = MapUtils.get(parameterMap, "insureRegCode");

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("mdtrt_id", MapUtils.get(parameterMap, "mdtrtId"));//就诊ID
        dataMap.put("setl_id", MapUtils.get(parameterMap, "setlId"));//结算ID
        dataMap.put("psn_no", MapUtils.get(parameterMap, "psnNo"));//人员编号

        Map<String, Object> resultMap = invokingUpay(hospCode, insureRegCode, Constant.UnifiedPay.OUTPT.UP_5262, dataMap);
        //Map<String, Object> resultMap = JSONObject.parseObject("{\t\"output\":{\t\t\"feesttinfo\":[\t\t\t{\t\t\t\t\"item_clab_amt\":0.00,\t\t\t\t\"med_chrgitm_type\":\"14\",\t\t\t\t\"fulamtOwnpay_amt\":\"\",\t\t\t\t\"overlmt_selfpay\":\"\",\t\t\t\t\"preselfpay_amt\":\"\",\t\t\t\t\"item_sumamt\":3300.00,\t\t\t\t\"item_claa_amt\":0.00\t\t\t}\t\t],\t\t\"setlinfo\":{\t\t\t\"setl_time\":\"2021-04-09 13:49:42\",\t\t\t\"cvlserv_pay\":0.00,\t\t\t\"insu_admdvs\":\"\",\t\t\t\"med_fee_sumamt\":3300.00,\t\t\t\"year\":\"\",\t\t\t\"med_type\":\"2101\",\t\t\t\"psn_pay\":\"\",\t\t\t\"opter_id\":\"\",\t\t\t\"begndate\":\"\",\t\t\t\"hifmi_pay\":0.00,\t\t\t\"flxempe_flag\":\"\",\t\t\t\"psn_no\":\"46000000000000003501209658\",\t\t\t\"act_pay_dedc\":0.00,\t\t\t\"medins_setl_id\":\"H46010500002202104091349412420\",\t\t\t\"acct_mulaid_pay\":\"\",\t\t\t\"clr_way\":\"3\",\t\t\t\"oth_pay\":0,\t\t\t\"gend\":\"2\",\t\t\t\"mdtrt_id\":\"310000088057\",\t\t\t\"acct_pay\":0.00,\t\t\t\"fix_blng_admdvs\":\"\",\t\t\t\"insutype\":\"310\",\t\t\t\"inscp_scp_amt\":\"\",\t\t\t\"invono\":\"\",\t\t\t\"psn_part_amt\":0,\t\t\t\"enddate\":\"\",\t\t\t\"cash_payamt\":\"\",\t\t\t\"psn_type\":\"1101\",\t\t\t\"hifp_pay\":0.00,\t\t\t\"fixmedins_name\":\"\",\t\t\t\"overlmt_selfpay\":0.00,\t\t\t\"pay_loc\":\"\",\t\t\t\"clr_type\":\"21\",\t\t\t\"othfund_pay\":\"\",\t\t\t\"refd_setl_flag\":\"\",\t\t\t\"psnCash_pay\":0,\t\t\t\"cvlserv_flag\":\"0\",\t\t\t\"emp_name\":\"\",\t\t\t\"brdy\":\"1983-02-27 00:00:00\",\t\t\t\"naty\":\"1\",\t\t\t\"dise_codg\":\"\",\t\t\t\"fixmedins_code\":\"\",\t\t\t\"mdtrt_cert_type\":\"02\",\t\t\t\"opter_name\":\"\",\t\t\t\"dedc_hosp_lv\":\"\",\t\t\t\"balc\":0.00,\t\t\t\"psn_cert_type\":\"01\",\t\t\t\"fixmedins_poolarea\":\"\",\t\t\t\"hifob_pay\":0.00,\t\t\t\"nwb_flag\":\"\",\t\t\t\"inscp_amt\":\"\",\t\t\t\"hifes_pay\":0.00,\t\t\t\"cert_no\":\"460003198302276864\",\t\t\t\"fund_pay_sumamt\":0.00,\t\t\t\"opt_time\":\"\",\t\t\t\"fulamt_ownpay_amt\":3300.00,\t\t\t\"setl_id\":\"300000078066\",\t\t\t\"hosp_part_amt\":0,\t\t\t\"psn_name\":\"鍒樻檽鐕�\",\t\t\t\"maf_pay\":0.00,\t\t\t\"prese_ifpay_amt\":0.00,\t\t\t\"dise_no\":\"\",\t\t\t\"clr_optins\":\"460400\",\t\t\t\"pool_prop_selfpay\":0.0000,\t\t\t\"dise_name\":\"\",\t\t\t\"lmtpric_hosp_lv\":\"\",\t\t\t\"age\":38.0,\t\t\t\"hosp_lv\":\"\"\t\t}\t},\t\"infcode\":\"0\",\t\"warn_msg\":\"\",\t\"cainfo\":\"\",\t\"err_msg\":\"\",\t\"refmsg_time\":\"20210415113450337\",\t\"signtype\":\"\",\t\"respond_time\":\"20210415113450383\",\t\"inf_refmsgid\":\"\"}");


        return resultMap;
    }

    /**
     * @Method downLoadSettleInfo
     * @Desrciption  本地下载医保结算单
     *
     * @Param       C0000_BIZ2106   业务总次数且医疗业务类别为生育平产(居民)的累计  F0000_BIZ2106    总费用累计
     *              C0000_BIZ2107   业务总次数且医疗业务类别为生育剖宫产(居民)的累计  F0000_BIZ2107  总费用累计
     *              C0000_BIZ22   业务总次数且医疗业务类别为外伤住院的累计  F0000_BIZ22 总费用累计
     *              C0000_BIZ51   业务总次数且医疗业务类别为职工生育门诊的累计  F0000_BIZ51 总费用累计
     *              C0000_BIZ52  业务总次数且医疗业务类别为职工生育住院的累计  F0000_BIZ52 总费用累计
     *              C0000_BIZ53   业务总次数且医疗业务类别为计划生育手术费的累计  F0000_BIZ53 总费用累计
     *              C0000_BIZ9901  业务总次数且医疗业务类别为门诊两病的累计  F0000_BIZ9901 总费用累计
     *              C0000_BIZ9903   业务总次数且医疗业务类别为意外伤害门诊的累计  F0000_BIZ9903 总费用累计
     *              C0000_BIZ11 :  业务总次数且医疗业务类别为普通门诊的累计  F0000_BIZ11 总费用累计
     *              C0000_BIZ14 :   业务总次数且医疗业务类别为门诊慢特病的累计  F0000_BIZ14 总费用累计
     *              C0000_BIZ2101 :  业务总次数且医疗业务类别为普通住院的累计  F0000_BIZ2101 总费用累计
     *              C0000_BIZ2102  : 业务总次数且医疗业务类别为单病种住院的累计  F0000_BIZ2102 总费用累计
     *              C0000_BIZ13  业务总次数且医疗业务类别为急诊的累计  F0000_BIZ13 总费用累计
     *
     *              D320101 ：  公务员基金累计
     *              D330101： 基金为大额医疗费用补助基金的累计
     *              D310101/D390101 统筹基金支付
     *              D310301/D390201 大病保险
     *              D310201/D390501  个人账户支付
     *              D610101   医疗救助支付
     *  type:  1住院结算单   0:门诊结算单   3：离休结算单  4：一站式结算单
     *
     *
     * @Author fuhui
     * @Date   2021/10/14 11:18
     * @Return
     **/
    public Map<String,Object> downLoadSettleInfo( Map<String,Object> map){
        String insureSettleId = MapUtils.get(map, "insureSettleId");
        InsureIndividualVisitDTO insureIndividualVisitDTO =  insureUnifiedCommonUtil.commonGetVisitInfo(map);
        String insuplcAdmdvs = insureIndividualVisitDTO.getInsuplcAdmdvs(); // 参保地区划
        String mdtrtareaAdmvs = insureIndividualVisitDTO.getMdtrtareaAdmvs(); //  就医地区划
        Boolean isRemote =  insureIndividualVisitDTO.getIsRemote(); // 是否异地判断  包含省本级
        String isHospital = insureIndividualVisitDTO.getIsHospital();
        String aae140 = insureIndividualVisitDTO.getAae140();
        String type  = MapUtils.get(map,"type");
        String hospLv = "";
        String hospName ="";
        String mdOrInsuplcAdmdvs ="";
        String settleTitle ="";
        String provinceName = "";
        String mzOrZyName =""; // 门诊 或者住院
        String mdOrInsuplcAdmdvsName ="";
        String titleEnd = "";
        map.put("code","HOSP_MEDICINS_INFO");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(map).getData();
        if(sysParameterDTO ==null || StringUtils.isEmpty(sysParameterDTO.getValue())){
            throw new AppException("请先配置系统参数HOSP_MEDICINS_INFO");
        }
        else{
            String value = sysParameterDTO.getValue();
            Map<String,Object> stringObjectMap = JSON.parseObject(value,Map.class);
            for(String key : stringObjectMap.keySet()){
                if("hospLv".equals(key)){  // 医院等级
                    hospLv = MapUtils.get(stringObjectMap,key);
                }
                if("hospName".equals(key)){ // 医院名称
                    hospName = MapUtils.get(stringObjectMap,key);
                }
                if("provinceName".equals(key)){ // 省份名称
                    provinceName = MapUtils.get(stringObjectMap,key);
                }
                if("titleEnd".equals(key)){
                    titleEnd = MapUtils.get(stringObjectMap,key);
                }
                if("mdOrInsuplcAdmdvs".equals(key)){
                    mdOrInsuplcAdmdvs = MapUtils.get(stringObjectMap,key); // 医院结算单取参保地区划 还是就医地区划
                    // 如果是1  结算单就取参保地区划
                    // 如果是0 结算单就取就医地区划
                    if(Constants.SF.S.equals(mdOrInsuplcAdmdvs)){
                        if(insuplcAdmdvs.equals(key)){
                            mdOrInsuplcAdmdvsName = MapUtils.get(stringObjectMap,key);
                        }
                        if(mdtrtareaAdmvs.equals(key)){
                            mdOrInsuplcAdmdvsName = MapUtils.get(stringObjectMap,key);
                        }
                    }
                }
            }
            if(Constants.SF.S.equals(isHospital) && !"340".equals(aae140)){
                mzOrZyName ="住院";
            }
            else if("340".equals(aae140)){
                mzOrZyName = "离休";
            }
            else{
                mzOrZyName ="(普通、特殊)门诊";
            }
            settleTitle = new StringBuilder().append(provinceName).append(mdOrInsuplcAdmdvsName).
                    append(mzOrZyName).append(titleEnd).toString();

        }
        Map<String, Object> medisnInfMap = new HashMap<>();
        medisnInfMap.put("settleTitle",settleTitle);
        medisnInfMap.put("hospLv",hospLv);
        medisnInfMap.put("hospName",hospName);
        // 获取医保结算信息  调用医保接口
        Map<String, Object> data = insureUnifiedBaseService.querySettleDeInfo(map).getData();
        Map<String,Object> setlInfoMap = MapUtils.get(data,"setlinfo");
        Object obj = MapUtils.get(setlInfoMap,"act_pay_dedc");
        BigDecimal actPayDedc = new BigDecimal(0.00);
        if(obj instanceof  String){
             actPayDedc = BigDecimalUtils.convert(MapUtils.get(setlInfoMap,"act_pay_dedc"));
        }else{
             actPayDedc = MapUtils.get(setlInfoMap,"act_pay_dedc");
        }
        List<Map<String, Object>> setldetail  = MapUtils.get(data,"setldetail");

        Map<String,Object> oneSettleMap = checkOneSettle(map, insureIndividualVisitDTO);
        boolean oneSettle = MapUtils.get(oneSettleMap,"flag");
        String psnIdetType = MapUtils.get(oneSettleMap,"psnIdetType");
        Map<String,Object> baseInfoMap  = handlerInptSettleParam(insureIndividualVisitDTO,setlInfoMap,oneSettle,psnIdetType);
        Map<String,Object> fourPartMap =  handlerInsureSettleFee(setlInfoMap);
        BigDecimal hifpPay = MapUtils.get(setlInfoMap,"hifp_pay"); // 报销总金额
        BigDecimal medfeeSumamt = MapUtils.get(setlInfoMap,"medfee_sumamt"); // 费用总金额
        List<Map<String, Object>> sumInfoMapList ;
        /**
         *  如果为true 则是一站式结算单 只有一站式结算单才需要结算完后调用个人累计信息查询。
         *  如果不是一站式结算单，则需要患者办理预出院时，存库个人累计信息查询。
         */
        if(oneSettle && Constants.SF.S.equals(isHospital)){
            Map<String, Object> sumInfoMap = insureUnifiedBaseService.queryPatientSumInfo(map).getData();
            sumInfoMapList =  MapUtils.get(sumInfoMap,"resultDataMap");
            /**
             * 本次综合医疗保障报销
             */
            Map<String,Object> setlProcInfoMap =  handSetlProcInfo(setldetail,setlInfoMap);
            map.put("setlProcInfoMap",setlProcInfoMap);

        }else{
            if(Constants.SF.F.equals(isHospital)){
                insureIndividualVisitDTO.setInsureSettleId(insureSettleId);
            }
            sumInfoMapList =  insureIndividualVisitDAO.querySelectInsureSumInfo(insureIndividualVisitDTO);
        }

        Map<String,Object> pastFeeMap = new HashMap<>();
        // 既往分类  340  非离休人员医疗保障  非一站式
        if(!"340".equals(aae140)){
            pastFeeMap = handlerPastFee(sumInfoMapList,isHospital,oneSettle);
        }
        if("340".equals(aae140)  && !oneSettle){
            // 计算离休基金的
            BigDecimal pastFeeFund = handlerFeeFund(setldetail);
            map.put("pastFeeFund",pastFeeFund); // 离休基金
            pastFeeMap = handlerSpecialPastFee(sumInfoMapList);
        }

        /**
         * 查询his费用表集合,分组合并计算费用  如果his数据库没有存医保反参费用明细则调用医保接口获取费用信息
         */
        List<Map<String, Object>> settleFeeMap = insureIndividualCostDAO.selectIsSetlleFee(map);
        List<Map<String, Object>> feeMapList;
        if(ListUtils.isEmpty(settleFeeMap)){
            Map<String, Object> feeMap = insureUnifiedBaseService.queryFeeDetailInfo(map).getData();
            List<Map<String, Object>> outptMap  = MapUtils.get(feeMap,"outptMap");
            // 费用分组统计合并
             feeMapList = handlerInsureIndividualCost(outptMap);
        }else{
            // 费用分组统计合并
             feeMapList = handlerInsureIndividualCost(settleFeeMap);
        }
        /**
         * 住院独有
         * 1.调用政策查询接口  且是非一站式结算单
         */
        if(Constants.SF.S.equals(isHospital) && !oneSettle){
            Map<String, Object> policyMap = insureUnifiedBaseService.queryPolicyInfo(map).getData();
            List <Map<String, Object>>  policyMapList =  MapUtils.get(policyMap,"outptMap");
            map.put("policyMapList",policyMapList);
        }
        if(Constants.SF.S.equals(isHospital) && oneSettle){
            Map<String, Object> policyMap = insureUnifiedBaseService.queryPolicyInfo(map).getData();
            List <Map<String, Object>>  policyMapList =  MapUtils.get(policyMap,"outptMap");
            Map<String, Object> handlerPolicyMap = handlerPolicy(policyMapList,hospName,actPayDedc,hifpPay,medfeeSumamt,insureIndividualVisitDTO);
            map.put("handlerPolicyMap",handlerPolicyMap);
        }
        map.put("setldetail",setldetail); // 基金信息
        map.put("medisnInfMap",medisnInfMap); // 结算单第一部分数据
        map.put("pastFeeMap",pastFeeMap); // 结算单显示第三部分数据
        map.put("feeMapList",feeMapList); // 结算单第四部分数据
        map.put("fourPartMap",fourPartMap);// 尾部结算数据
        map.put("baseInfoMap",baseInfoMap);//患者就诊的基本信息数据部分
        map.put("aae140",aae140); // 如果险种返回 340  就打印离休结算
        map.put("oneSettle",oneSettle); //  如果符合一站式结算要去，前端打印一站式结算单
        map.put("isHospital",isHospital);
        map.put("insuplcAdmdvs",insuplcAdmdvs);
        map.put("mdtrtareaAdmvs",mdtrtareaAdmvs);
        String json = JSONObject.toJSONString(map);
        System.out.println("----======"+json);
        return map;
    }

    /**
     * @Method handlerPolicy
     * @Desrciption  一站式结算单处理政策项费用
     * @Param
     *
     * @Author fuhui
     * @Date   2021/10/26 19:10
     * @Return
    **/
    private Map<String, Object> handlerPolicy(List<Map<String, Object>> policyMapList, String hospName,
                                              BigDecimal actPayDedc, BigDecimal hifpPay, BigDecimal medfeeSumamt,
                                              InsureIndividualVisitDTO insureIndividualVisitDTO) {
        Map<String, Object> handlerPolicyMap = new HashMap<>();
        BigDecimal s1 = new BigDecimal(0.00);
        BigDecimal s2 = new BigDecimal(0.00);
        BigDecimal s3 = new BigDecimal(0.00);
        BigDecimal s4 = new BigDecimal(0.00);
        BigDecimal s5 = new BigDecimal(0.00);
        String polItemName ="";
        BigDecimal polItemPaySum = new BigDecimal(0.00);
        for(Map<String, Object> item : policyMapList){
            polItemName = MapUtils.get(item,"polItemName");
            polItemPaySum = MapUtils.get(item,"polItemPaySum");
            if("完全政策自付费用".equals(polItemName.trim())){
                s1 = polItemPaySum;
                continue;
            }
            if("部分政策自付费用".equals(polItemName.trim())){
                s2 = polItemPaySum;
                continue;
            }
            if("县外就医转外自付费用".equals(polItemName.trim())){
                s3 = polItemPaySum;
                continue;
            }
            if("大病起付线".equals(polItemName.trim())){
                s4 = polItemPaySum;
                continue;
            }
        }
        handlerPolicyMap.put("s1",s1);
        handlerPolicyMap.put("s2",s2);
        handlerPolicyMap.put("s3",s3);
        handlerPolicyMap.put("s4",s4);
        handlerPolicyMap.put("hospName",hospName); // 核算机构名称 核算人
        handlerPolicyMap.put("hifpPay",numberToCN.number2CNMontrayUnit(hifpPay)); // 基本医保报销金额（大写）
        if (BigDecimalUtils.isZero(hifpPay)){
            s5 = hifpPay; // 如果报销金额等于0  则提高10%的报销金额还是为0
            handlerPolicyMap.put("divideFormat","0.00%"); // 百分比
        }
        else{
            s5 = BigDecimalUtils.add(hifpPay,BigDecimalUtils.multiply(hifpPay,new BigDecimal(0.10)));
            BigDecimal divide = BigDecimalUtils.divide(hifpPay, medfeeSumamt);
            DecimalFormat decimalFormat = new DecimalFormat("0.00%");
            String divideFormat = decimalFormat.format(divide);
            handlerPolicyMap.put("divideFormat",divideFormat); // 百分比
        }
        List<Map<String, Object>> sumInfo = insureIndividualVisitDAO.querySelectInsureSumInfo(insureIndividualVisitDTO);
        String cumTypeCode ="";
        BigDecimal cum = new BigDecimal(0.00);
        BigDecimal sumCum = new BigDecimal(0.00);
        for(Map<String, Object> item : sumInfo){
              cumTypeCode = MapUtils.get(item,"cum_type_code");
              cum = MapUtils.get(item,"cum");
              if("Q0000".equals(cumTypeCode)){   // 累计起付线(不含本次)
                  sumCum = BigDecimalUtils.add(cum,sumCum);
                  break;
              }
        }
        handlerPolicyMap.put("sumCum",sumCum);// 累计起付线(不含本次)
        handlerPolicyMap.put("s5",s5);
        handlerPolicyMap.put("actPayDedc",actPayDedc);
        return handlerPolicyMap;
    }

    /**
     * @Method handSetlProcInfo
     * @Desrciption  计算处理本次综合医疗保障报销
     * @Param setldetail  基金分项数据集合
     *
     * @Author fuhui
     * @Date   2021/10/26 13:48
     * @Return
    **/
    private Map<String, Object> handSetlProcInfo(List<Map<String, Object>> setldetail, Map<String, Object> setlInfoMap) {
        Map<String,Object> setlProcMap = new HashMap<>();
        String setlProcInfo = ""; //基金类别
        BigDecimal hifpPay = MapUtils.get(setlInfoMap,"hifp_pay");//基本医疗保险统筹基金支出
        BigDecimal psnPartAmt  = MapUtils.get(setlInfoMap,"psn_part_amt"); // 个人负担总金额
        BigDecimal fundPaySumamt = MapUtils.get(setlInfoMap,"fund_pay_sumamt"); // 基金总金额
        BigDecimal fundPayamt  = new BigDecimal(0.00); // 基金支付金额
        BigDecimal s2 = new BigDecimal(0.00) ; // 大病保险报销金额
        BigDecimal s3 = new BigDecimal(0.00); // 意外伤害
        BigDecimal s4 = new BigDecimal(0.00);  // 大病补充特惠保
        BigDecimal s5 = new BigDecimal(0.00); //医疗救助金额
        BigDecimal s6 = new BigDecimal(0.00); // 医院减免金额
        BigDecimal s7 = new BigDecimal(0.00);  // 财政兜底报销金额
        BigDecimal s9 = new BigDecimal(0.00); // 其它报销金额
        for(Map<String, Object> map : setldetail){
            setlProcInfo = MapUtils.get(map,"setl_proc_info");
            if(MapUtils.get(map,"inscp_scp_amt") instanceof String){
                fundPayamt = BigDecimalUtils.convert(MapUtils.get(map,"inscp_scp_amt"));
            }else{
                fundPayamt = MapUtils.get(map,"inscp_scp_amt");
            }
            if("390201".equals(setlProcInfo)){  // 大病保险报销金额
                s2 = BigDecimalUtils.add(s2,fundPayamt);
            }
            if("390401".equals(setlProcInfo)){  // 意外伤害
                s3 = BigDecimalUtils.add(s3,fundPayamt);
            }
            if("620101".equals(setlProcInfo)){  // 大病补充特惠保
                s4 = BigDecimalUtils.add(s4,fundPayamt);
            }
            if("610100".equals(setlProcInfo)){  // 医疗救助金额
                s5 = BigDecimalUtils.add(s5,fundPayamt);
            }
            if("630101".equals(setlProcInfo)){  // 医院减免金额
                s6 = BigDecimalUtils.add(s6,fundPayamt);
            }
            if("640101".equals(setlProcInfo)){  // 财政兜底报销金额
                s7 = BigDecimalUtils.add(s7,fundPayamt);
            }
            if("999996".equals(setlProcInfo)){  // 其它报销金额
                s9 = BigDecimalUtils.add(s9,fundPayamt);
            }
        }
        Set <BigDecimal> bigDecimalSet = new HashSet<>();
        BigDecimal sumBxFee = new BigDecimal(0.00);
        bigDecimalSet.add(s2);
        bigDecimalSet.add(s3);
        bigDecimalSet.add(s4);
        bigDecimalSet.add(s5);
        bigDecimalSet.add(s6);
        bigDecimalSet.add(s7);
        bigDecimalSet.add(s9);
        for(BigDecimal s : bigDecimalSet){
            sumBxFee = BigDecimalUtils.add(sumBxFee,s);
        }

        setlProcMap.put("hifpPay",hifpPay);
        setlProcMap.put("psnPartAmt",psnPartAmt);
        setlProcMap.put("fundPaySumamt",fundPaySumamt);
        setlProcMap.put("s2",s2);
        setlProcMap.put("s3",s3);
        setlProcMap.put("s4",s4);
        setlProcMap.put("s5",s5);
        setlProcMap.put("s6",s6);
        setlProcMap.put("s7",s7);
        setlProcMap.put("s9",s9);
        setlProcMap.put("sumBxFee",numberToCN.number2CNMontrayUnit(sumBxFee));
        return setlProcMap;
    }

    /**
     * @Method checkOneSettle
     * @Desrciption 判读是否打印一站式结算单
     * @Param map insureIndividualVisitDTO
     *
     * @Author fuhui
     * @Date   2021/10/23 15:20
     * @Return
    **/
    public Map<String,Object> checkOneSettle(Map<String, Object> map, InsureIndividualVisitDTO insureIndividualVisitDTO) {
        Map<String,Object> infoMap = new HashMap<>();
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
       Map<String,Object> dataMap =  insureUnifiedBaseService.checkOneSettle(map).
               getData();
        Map<String, Object> outputMap = MapUtils.get(dataMap, "output");
        List<Map<String, Object>> listMap =   MapUtils.get(outputMap,"idetinfo");
        if(ListUtils.isEmpty(listMap)){
            infoMap.put("flag", false);
        }else{
            List<Map<String, Object>> collect = listMap.stream().filter(item ->
                DateUtils.betweenDate(DateUtils.parse(MapUtils.get(item, "begntime"),DateUtils.Y_M_DH_M_S),
                        DateUtils.parse(MapUtils.get(item, "endtime"),DateUtils.Y_M_DH_M_S), DateUtils.getNow())).collect(Collectors.toList());
            if(ListUtils.isEmpty(collect)){
                infoMap.put("flag", false);
            }else{
                String psnIdetType = MapUtils.get(collect.get(0), "psn_idet_type");
                infoMap.put("flag",true);
                infoMap.put("psnIdetType",psnIdetType);
            }

        }
        return infoMap;
    }

    /**
     * @Method handlerFeeFund
     * @Desrciption  离休结算单：计算离休基金
     * @Param
     *
     * @Author fuhui
     * @Date   2021/10/23 15:20
     * @Return
    **/
    private BigDecimal handlerFeeFund(List<Map<String, Object>> setldetail) {
        BigDecimal  bigDecimal = new BigDecimal(0.00);
        if(!ListUtils.isEmpty(setldetail)){
            for(Map<String, Object> item : setldetail){
                String fundPayType = MapUtils.get(item,"fund_pay_type"); //  基金支付类型
                BigDecimal fundPayamt  =  MapUtils.get(item,"fund_payamt"); //  基金支付金额
                if("340100".equals(fundPayType)){ // 离休支付基金
                    bigDecimal = BigDecimalUtils.add(bigDecimal,fundPayamt);
                }
            }
        }
        return bigDecimal;
    }

    /**
     * @Method handlerSpecialPastFee
     * @Desrciption  离休人员费用结算单 本年既往费用
     * @Param
     *
     * @Author fuhui
     * @Date   2021/10/19 19:44
     * @Return
     **/
    private Map<String, Object> handlerSpecialPastFee(List<Map<String, Object>> sumInfoMapList) {
        Map<String,Object> partThreeMap = new HashMap<>();
            BigDecimal totalCount = new BigDecimal(0.00); // 本年住院次数
            BigDecimal s1 = new BigDecimal(0.00);  // 医疗费合计
            BigDecimal s2 = new BigDecimal(0.00) ; // 离休基金支付
            BigDecimal s3 = new BigDecimal(0.00); // 个人账户支付
            DecimalFormat df1 = new DecimalFormat("0.00");
            String cumTypeCode ="";
            BigDecimal cum = new BigDecimal(0.00);
            for(Map<String, Object> item : sumInfoMapList){
                cumTypeCode = MapUtils.get(item,"cum_type_code"); // 累计类别代码
                cum = BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "cum") == null ?"":MapUtils.get(item, "cum").toString())));
                // 住院次数
                if("C0000".equals(cumTypeCode)){
                    totalCount= BigDecimalUtils.add(totalCount,cum);
                }
                //医疗费合计
                if("S0000".equals(cumTypeCode)){
                    s1 = BigDecimalUtils.add(s1,cum);
                }
                // 离休基金支付
                if("D340101".equals(cumTypeCode)){
                    s2 = BigDecimalUtils.add(s2,cum);
                }
                //个人账户支付
                if("D310201".equals(cumTypeCode)){
                    s3 = BigDecimalUtils.add(s3,cum);
                }
            }
            partThreeMap.put("totalCount",totalCount);
            partThreeMap.put("s1",s1);
            partThreeMap.put("s2",s2);
            partThreeMap.put("s3",s3);
            return partThreeMap;
    }

    /**
     * @Method handlerInsureSettleFee
     * @Desrciption  结算单公共部分 尾部  结算信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/10/19 20:09
     * @Return
     **/
    private Map<String, Object> handlerInsureSettleFee(Map<String, Object> setlInfoMap) {
        Map<String,Object> partFourMap = new HashMap<>();
        BigDecimal s1= MapUtils.get(setlInfoMap,"medfee_sumamt") == null ? new BigDecimal(0.00) : MapUtils.get(setlInfoMap,"medfee_sumamt");
        BigDecimal s2 = MapUtils.get(setlInfoMap,"hifp_pay") == null ? new BigDecimal(0.00) : MapUtils.get(setlInfoMap,"hifp_pay");
        BigDecimal s3 =MapUtils.get(setlInfoMap,"cvlserv_pay") == null ? new BigDecimal(0.00) : MapUtils.get(setlInfoMap,"cvlserv_pay");
        BigDecimal s4 = MapUtils.get(setlInfoMap,"hifob_pay") == null ? new BigDecimal(0.00) : MapUtils.get(setlInfoMap,"hifob_pay");
        BigDecimal s5 = MapUtils.get(setlInfoMap,"hifmi_pay") == null ? new BigDecimal(0.00) : MapUtils.get(setlInfoMap,"hifmi_pay");

        BigDecimal s6= MapUtils.get(setlInfoMap,"hifob_pay") == null ? new BigDecimal(0.00) : MapUtils.get(setlInfoMap,"hifob_pay");
        BigDecimal s7 = MapUtils.get(setlInfoMap,"acct_pay") == null ? new BigDecimal(0.00) : MapUtils.get(setlInfoMap,"acct_pay");
        BigDecimal s8 = MapUtils.get(setlInfoMap,"act_pay_dedc") == null ? new BigDecimal(0.00) : MapUtils.get(setlInfoMap,"act_pay_dedc");
        BigDecimal s9 = MapUtils.get(setlInfoMap,"cash_payamt")== null ? new BigDecimal(0.00) : MapUtils.get(setlInfoMap,"cash_payamt");
        BigDecimal s10 = MapUtils.get(setlInfoMap,"balc") == null ? new BigDecimal(0.00) : MapUtils.get(setlInfoMap,"balc");

        partFourMap.put("medfeeSumamt",s1); // 本次医疗费总额
        partFourMap.put("hifpPay",s2); // 统筹基金支付
        partFourMap.put("cvlservPay",s3); // 公务员补助支付
        partFourMap.put("hifobPay",s4); // 大额基金支付
        partFourMap.put("hifmiPay",s5); // 大病保险支付
        partFourMap.put("hifobPay",s6); // 大额基金支付
        partFourMap.put("acctPay",s7); // 个人账户支付金额
        partFourMap.put("actPayDedc",s8); // 实际支付起付线
        partFourMap.put("psnCashPay",s9); // 现金支付金额
        partFourMap.put("balc",s10); // 个人账户余额

//        if(1 == 1){
//            partFourMap.put("medfeeSumamtCN",numberToCN.number2CNMontrayUnit(s1)); // 本次医疗费总额
//            partFourMap.put("hifpPayCN",numberToCN.number2CNMontrayUnit(s2)); // 统筹基金支付
//            partFourMap.put("cvlservPayCN",numberToCN.number2CNMontrayUnit(s3)); // 公务员补助支付
//            partFourMap.put("hifobPayCN",numberToCN.number2CNMontrayUnit(s4)); // 大额基金支付
//            partFourMap.put("hifmiPayCN",numberToCN.number2CNMontrayUnit(s5)); // 大病保险支付
//            partFourMap.put("hifobPayCN",numberToCN.number2CNMontrayUnit(s6)); // 大额基金支付
//            partFourMap.put("acctPayCN",numberToCN.number2CNMontrayUnit(s7)); // 个人账户支付金额
//            partFourMap.put("actPayDedcCN",numberToCN.number2CNMontrayUnit(s8)); // 实际支付起付线
//            partFourMap.put("psnCashPayCN",numberToCN.number2CNMontrayUnit(s9)); // 现金支付金额
//            partFourMap.put("balcCN",numberToCN.number2CNMontrayUnit(s10)); // 个人账户余额
//        }
        return partFourMap;
    }

    /**
     * @Method handlerPastFee
     * @Desrciption  计算既往费用的；
     * @Param
     *
     * @Author fuhui
     * @Date   2021/10/15 16:20
     * @Return
     **/
    private Map<String, Object> handlerPastFee(List<Map<String, Object>> sumInfoMapList, String isHospital, boolean oneSettle) {
        Map<String,Object> map = new HashMap<>();
        BigDecimal inptCount = new BigDecimal(0.00); // 本年住院次数（住院）
        BigDecimal outptCount = new BigDecimal(0.00); //本年住院次数（门诊）
        BigDecimal s1 = new BigDecimal(0.00);  // 本年度分段计算费用累计
        BigDecimal s2 = new BigDecimal(0.00) ; // 医疗费合计
        BigDecimal s3 = new BigDecimal(0.00); // 已付起付线
        BigDecimal s4 = new BigDecimal(0.00);  // 统筹支付
        BigDecimal s5 = new BigDecimal(0.00); //政策自费
        BigDecimal s6 = new BigDecimal(0.00); // 政策自付
        BigDecimal s7 = new BigDecimal(0.00);  // 大额基金支付
        BigDecimal s8 = new BigDecimal(0.00); // 大病保险合规费用
        BigDecimal s9 = new BigDecimal(0.00); // 大病保险支付
        BigDecimal s10 = new BigDecimal(0.00); // 医疗救助支付
        DecimalFormat df1 = new DecimalFormat("0.00");
        String cumTypeCode ="";
        BigDecimal cum = new BigDecimal(0.00);
        if(Constants.SF.S.equals(isHospital)){
            for(Map<String, Object> item : sumInfoMapList){
                cumTypeCode = MapUtils.get(item,"cum_type_code"); // 累计类别代码
                cum = BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "cum")==null?"":MapUtils.get(item, "cum").toString())));
                if("C0000_BIZ2101".equals(cumTypeCode) ||
                        "C0000_BIZ2102".equals(cumTypeCode) ||
                        "C0000_BIZ22".equals(cumTypeCode)||
                        "C0000_BIZ2106".equals(cumTypeCode) ||
                        "C0000_BIZ2107".equals(cumTypeCode)||
                        "C0000_BIZ52".equals(cumTypeCode)){
                    inptCount  = BigDecimalUtils.add(inptCount,cum);
                }

                if("S0000_BIZ2101".equals(cumTypeCode)||
                        "S0000_BIZ2102".equals(cumTypeCode) ||
                        "S0000_BIZ22".equals(cumTypeCode) ||
                        "S0000_BIZ2106".equals(cumTypeCode) ||
                        "S0000_BIZ2107".equals(cumTypeCode) ||
                        "S0000_BIZ52".equals(cumTypeCode)){
                    s1  = BigDecimalUtils.add(s1,cum);
                }
                if("F0000_BIZ2101".equals(cumTypeCode)||
                        "F0000_BIZ2102".equals(cumTypeCode) ||
                        "F0000_BIZ22".equals(cumTypeCode) ||
                        "F0000_BIZ2106".equals(cumTypeCode) ||
                        "F0000_BIZ2107".equals(cumTypeCode) ||
                        "F0000_BIZ52".equals(cumTypeCode)){
                    s2  = BigDecimalUtils.add(s2,cum);
                }
                if("Q0000_BIZ2101".equals(cumTypeCode)||
                        "Q0000_BIZ2102".equals(cumTypeCode) ||
                        "Q0000_BIZ22".equals(cumTypeCode) ||
                        "Q0000_BIZ2106".equals(cumTypeCode) ||
                        "Q0000_BIZ2107".equals(cumTypeCode) ||
                        "Q0000_BIZ52".equals(cumTypeCode)){
                    s3  = BigDecimalUtils.add(s3,cum);
                }
                if("D310101_BIZ2101".equals(cumTypeCode)||
                        "D310101_BIZ2102".equals(cumTypeCode) ||
                        "D310101_BIZ22".equals(cumTypeCode) ||
                        "D310101_BIZ2106".equals(cumTypeCode) ||
                        "D310101_BIZ2107".equals(cumTypeCode) ||
                        "D310101_BIZ52".equals(cumTypeCode) ||
                        "D390101_BIZ2101".equals(cumTypeCode)||
                        "D390101_BIZ2102".equals(cumTypeCode) ||
                        "D310101_BIZ22".equals(cumTypeCode) ||
                        "D390101_BIZ22".equals(cumTypeCode) ||
                        "D390101_BIZ2106".equals(cumTypeCode) ||
                        "D390101_BIZ2107".equals(cumTypeCode) ||
                        "D390101_BIZ52".equals(cumTypeCode)) {
                    s4 = BigDecimalUtils.add(s4, cum);
                }
                if("Z0000_BIZ2101_LAB101".equals(cumTypeCode)||
                        "Z0000_BIZ2102_LAB101".equals(cumTypeCode) ||
                        "Z0000_BIZ22_LAB101".equals(cumTypeCode) ||
                        "Z0000_BIZ2106_LAB101".equals(cumTypeCode) ||
                        "Z0000_BIZ2107_LAB101".equals(cumTypeCode) ||
                        "Z0000_BIZ52_LAB101".equals(cumTypeCode) ||
                        "Z0000_BIZ2101_LAB103".equals(cumTypeCode)||
                        "Z0000_BIZ22_LAB103".equals(cumTypeCode) ||
                        "Z0000_BIZ2106_LAB103".equals(cumTypeCode) ||
                        "Z0000_BIZ52_LAB103".equals(cumTypeCode)){
                    s5  = BigDecimalUtils.add(s5,cum);
                }
                if("Z0000_BIZ2101_LAB102".equals(cumTypeCode)||
                        "Z0000_BIZ2102_LAB102".equals(cumTypeCode) ||
                        "Z0000_BIZ22_LAB102".equals(cumTypeCode) ||
                        "Z0000_BIZ2106_LAB102".equals(cumTypeCode) ||
                        "Z0000_BIZ2107_LAB102".equals(cumTypeCode) ||
                        "Z0000_BIZ52_LAB102".equals(cumTypeCode)){
                    s6  = BigDecimalUtils.add(s6,cum);
                }
                if("D330101".equals(cumTypeCode)){
                    s7  = BigDecimalUtils.add(s7,cum);
                }
                if("TS390201".equals(cumTypeCode)){
                    s8  = BigDecimalUtils.add(s8,cum);
                }
                if("D390201".equals(cumTypeCode)){
                    s9  = BigDecimalUtils.add(s9,cum);
                }
                if("D610101".equals(cumTypeCode)){
                    s10  = BigDecimalUtils.add(s10,cum);
                }
            }
            map.put("inptCount",inptCount);
            map.put("s1",s1);
            map.put("s2",s2);
            map.put("s3",s3);
            map.put("s4",s4);
            map.put("s5",s5);
            map.put("s6",s6);
            map.put("s7",s7);
            map.put("s8",s8);
            map.put("s9",s9);
            map.put("s10",s10);
        }
        else if(Constants.SF.F.equals(isHospital) || (!oneSettle && Constants.SF.F.equals(isHospital))) {
            for(Map<String, Object> item : sumInfoMapList){
                cumTypeCode = MapUtils.get(item,"cum_type_code"); // 累计类别代码
                cum = BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "cum").toString())));
                if("C0000_BIZ11".equals(cumTypeCode) || "C0000_BIZ13".equals(cumTypeCode) ||
                        "C0000_BIZ51".equals(cumTypeCode) || "C0000_BIZ9901".equals(cumTypeCode) ||
                        "C0000_BIZ14".equals(cumTypeCode)){
                    outptCount = BigDecimalUtils.add(outptCount,cum);
                }
                // F0000_BIZ9901    业务总费用且医疗业务类别为门诊两病的累计
                // F0000_BIZ51      业务总费用且医疗业务类别为职工生育门诊的累计
                // F0000_BIZ11      业务总费用且医疗业务类别为普通门诊的累计
                // F0000_BIZ13      业务总费用且医疗业务类别为急诊的累计
                // F0000_BIZ14      业务总费用且医疗业务类别为门诊慢特病的累计
                if("F0000_BIZ9901".equals(cumTypeCode) || "F0000_BIZ51".equals(cumTypeCode) ||
                        "F0000_BIZ11".equals(cumTypeCode) || "F0000_BIZ13".equals(cumTypeCode) ||
                        "F0000_BIZ14".equals(cumTypeCode)){
                    s1 = BigDecimalUtils.add(s1,cum);
                }
                // D390102_BIZ11  基金且城乡居民统筹基金且医疗业务类别为普通门诊的累计
                // D390101_BIZ14  基金且城乡居民统筹基金且医疗业务类别为门诊慢特病的累计
                // D310101_BIZ13  基金且城乡居民统筹基金且医疗业务类别为门诊慢特病的累计
                // D310101_BIZ14 基金且职工统筹基金且医疗业务类别为门诊慢特病的累计
                // D390101_BIZ13 基金且城乡居民统筹基金且医疗业务类别为急诊的累计
                // D390102_BIZ9901 基金且城乡居民统筹基金且医疗业务类别为急诊的累计
                if("D390102_BIZ11".equals(cumTypeCode) || "D390101_BIZ14".equals(cumTypeCode)||
                        "D310101_BIZ13".equals(cumTypeCode) || "D310101_BIZ14".equals(cumTypeCode)||
                        "D390101_BIZ13".equals(cumTypeCode) || "D390102_BIZ9901".equals(cumTypeCode)){
                    s2 = BigDecimalUtils.add(s2,cum);
                }
                // D330101  大额基金支付
                if("D330101_BIZ13".equals(cumTypeCode) || "D330101_BIZ14".equals(cumTypeCode)){
                    s3 = BigDecimalUtils.add(s3,cum);
                }
                // D320101 公务员补助支付
                if("D320101".equals(cumTypeCode)){
                    s4 = BigDecimalUtils.add(s4,cum);
                }
                // D390201 大病保险支付
                if("D390201".equals(cumTypeCode)){
                    s5 = BigDecimalUtils.add(s5,cum);
                }
                // D310201 个人账户支付
                if("D310201".equals(cumTypeCode)){
                    s6 = BigDecimalUtils.add(s6,cum);
                }
                // D610101  医疗救助支付
                if("D610101".equals(cumTypeCode)){
                    s7 = BigDecimalUtils.add(s7,cum);
                }
            }
            map.put("outptCount",outptCount); // 住院次数
            map.put("s1",s1); // 医疗费用合计
            map.put("s2",s2);  // 统筹基金支付
            map.put("s3",s3);  // 大额基金支付
            map.put("s4",s4); //  公务员补助支付
            map.put("s5",s5);  // 大病保险支付
            map.put("s6",s6);  // 个人账户支付
            map.put("s7",s7); //  医疗救助支付
        }
        else{
            for(Map<String, Object> item : sumInfoMapList) {
                cumTypeCode = MapUtils.get(item, "cum_type_code"); // 累计类别代码
                cum = BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "cum") == null ? "" : MapUtils.get(item, "cum").toString())));
                if("S0000_BIZ2101".equals(cumTypeCode)||
                        "S0000_BIZ2102".equals(cumTypeCode) ||
                        "S0000_BIZ22".equals(cumTypeCode) ||
                        "S0000_BIZ2106".equals(cumTypeCode) ||
                        "S0000_BIZ2107".equals(cumTypeCode) ||
                        "S0000_BIZ52".equals(cumTypeCode)){
                    s1  = BigDecimalUtils.add(s1,cum); // 累计基本医保报销金额
                }
                if("F0000_BIZ2101".equals(cumTypeCode)||
                        "F0000_BIZ2102".equals(cumTypeCode) ||
                        "F0000_BIZ22".equals(cumTypeCode) ||
                        "F0000_BIZ2106".equals(cumTypeCode) ||
                        "F0000_BIZ2107".equals(cumTypeCode) ||
                        "F0000_BIZ52".equals(cumTypeCode)){
                    s2  = BigDecimalUtils.add(s2,cum); // 累计医疗总费用
                }
                if("D390201_BIZ22".equals(cumTypeCode)||
                        "D390201_BIZ2101".equals(cumTypeCode) ||
                        "D390201_BIZ2102".equals(cumTypeCode)){
                    s3  = BigDecimalUtils.add(s3,cum);  // 累计大病保险报销金额
                }
                if("D620101".equals(cumTypeCode)){
                    s4  = BigDecimalUtils.add(s4,cum); // 累计扶贫特惠保报销金额
                }
                if("TS610101_BIZ2101".equals(cumTypeCode) || "TS610101_BIZ2102".equals(cumTypeCode))
                {
                    s5  = BigDecimalUtils.add(s5,cum); // 累计医疗救助金额
                }
                if("TS630101".equals(cumTypeCode)){ // 累计医院减免金额
                    s6  = BigDecimalUtils.add(s6,cum);
                }
                if("TS640101".equals(cumTypeCode)){
                    s7  = BigDecimalUtils.add(s7,cum); // 累计财政兜底报销金额
                }
                if("D999996".equals(cumTypeCode)){  // 累计其它报销金额
                    s8  = BigDecimalUtils.add(s8,cum);
                }
                if("TQ610101_BIZ2101".equals(cumTypeCode) ||
                       "TS390201_BIZ2101".equals(cumTypeCode) ||
                       "TS610101_BIZ2101".equals(cumTypeCode) ||
                       "TQ390201_BIZ2102".equals(cumTypeCode)||
                       "TS390201_BIZ2102".equals(cumTypeCode)||
                       "TQ610101_BIZ2102".equals(cumTypeCode)||
                       "TS610101_BIZ2102".equals(cumTypeCode)||
                       "TQ610101_BIZ22".equals(cumTypeCode) ||
                               "TQ390201_BIZ22".equals(cumTypeCode) ||
                               "TS390201_BIZ22".equals(cumTypeCode)||
                               "TS610101_BIZ22".equals(cumTypeCode)){
                   s9  = BigDecimalUtils.add(s9,cum); // 累计政策范围内费用
               }
                Set <BigDecimal> bigDecimalSet = new HashSet<>();
                BigDecimal sumBxFee = new BigDecimal(0.00);
                bigDecimalSet.add(s1);
                bigDecimalSet.add(s3);
                bigDecimalSet.add(s4);
                bigDecimalSet.add(s5);
                bigDecimalSet.add(s6);
                bigDecimalSet.add(s7);
                bigDecimalSet.add(s8);
                for(BigDecimal s : bigDecimalSet){
                    sumBxFee = BigDecimalUtils.add(sumBxFee,s);
                }
                map.put("s1",s1);
                map.put("s2",s2);
                map.put("s3",s3);
                map.put("s4",s4);
                map.put("s5",s5);
                map.put("s6",s6);
                map.put("s7",s7);
                map.put("s8",s8);
                map.put("s9",s9);
                map.put("sumBxFee",numberToCN.number2CNMontrayUnit(sumBxFee));
            }
        }
        return map;
    }

    /**
     * @Method handlerInptSettleParam
     * @Desrciption  组装住院结算单 参数
     * @Param
     *
     * @Author fuhui
     * @Date   2021/10/15 15:23
     * @Return
     **/
    private Map<String, Object> handlerInptSettleParam(InsureIndividualVisitDTO insureIndividualVisitDTO,
                                                       Map<String, Object> setlInfoMap, boolean oneSettle, String psnIdetType) {
        Map<String,Object> map = new HashMap<>();
        map.put("fixmedinsName",insureIndividualVisitDTO.getFixmedinsName()); // 医疗机构名称
        map.put("hospLv",insureIndividualVisitDTO.getHospLv()); // 医院等级
        map.put("mdtrtId",insureIndividualVisitDTO.getMedicalRegNo()); // 就诊号
        map.put("psnName",MapUtils.get(setlInfoMap,"psn_name")); // 人员姓名
        map.put("gend",MapUtils.get(setlInfoMap,"gend")); // 性别
        map.put("age",MapUtils.get(setlInfoMap,"age")); // 年龄
        map.put("psnNo",MapUtils.get(setlInfoMap,"psn_no")); // 个人编号
        if(StringUtils.isEmpty(MapUtils.get(setlInfoMap,"psn_idet_type"))){
            map.put("psnIdetType","无"); //补助类别
        }else{
            map.put("psnIdetType",MapUtils.get(setlInfoMap,"psn_idet_type")); //补助类别
        }

        if(StringUtils.isNotEmpty(insureIndividualVisitDTO.getBka008())){
            map.put("empName",insureIndividualVisitDTO.getBka008()); // 单位名称
        }else{
            map.put("empName","无"); // 单位名称
        }
        String bka006Name  = insureIndividualVisitDTO.getBka006Name();
        String visitIcdName  = insureIndividualVisitDTO.getVisitIcdName();
        if(StringUtils.isEmpty(bka006Name) || "null".equals(bka006Name)){
            map.put("diseName",visitIcdName); // 病种
        }else{
            map.put("diseName",bka006Name); // 病种
        }
        map.put("psnType",MapUtils.get(setlInfoMap,"psn_type")); // 人员类别
        map.put("tel",insureIndividualVisitDTO.getInptPhone()); // 联系电话
        map.put("cvlservFlag",MapUtils.get(setlInfoMap,"cvlserv_flag")); // 公务员标志
        map.put("iptNo",insureIndividualVisitDTO.getVisitNo()); // 住院号
        map.put("admDeptName",insureIndividualVisitDTO.getVisitDrptName()); // 科室
        map.put("admBed",insureIndividualVisitDTO.getVisitBerth()); // 床位号
        map.put("begntime",insureIndividualVisitDTO.getInTime()); // 入院日期
        map.put("endtime",insureIndividualVisitDTO.getOutTime()); // 出院日期
        map.put("endtimeBegntime",insureIndividualVisitDTO.getHospitalDay()); // 住院天数
        map.put("certno",MapUtils.get(setlInfoMap,"certno")); // 证件号码
        map.put("mdtrtCertType",MapUtils.get(setlInfoMap,"psn_cert_type")); // 证件类型
        map.put("dscgMaindiagName",insureIndividualVisitDTO.getOutDiseaseName()); // 出院诊断
        map.put("medType",insureIndividualVisitDTO.getAka130()); // 医疗类别
        map.put("setlTime",MapUtils.get(setlInfoMap,"setl_time")); // 结算时间
        map.put("chfpdrName",insureIndividualVisitDTO.getZzDoctorName()); // 主管医师
        map.put("balc",MapUtils.get(setlInfoMap,"balc")); // 个人账户余额
        map.put("address",insureIndividualVisitDTO.getAddress()); // 地址
        map.put("tel",insureIndividualVisitDTO.getPhone()); // 地址
        map.put("setlTime",MapUtils.get(setlInfoMap,"setl_time")); // 报销时间
        map.put("brdy",MapUtils.get(setlInfoMap,"brdy")); // 出生年月
        /**
         * 一站式结算单需要出院诊断
         */
        if(oneSettle){
            String diseaseName = insureIndividualVisitDAO.selectOutDiagnose(insureIndividualVisitDTO);
            map.put("outDiseaseName",diseaseName); // 出院诊断
            map.put("psnIdetType",psnIdetType); // 个人属性
        }
        return map;
    }

    /**
     * @Method handlerInsureIndividualCost
     * @Desrciption  对医保费用表进行分组 统计合并费用
     * @Param  feeDetailMapList：医保费用集合
     *
     * @Author fuhui
     * @Date   2021/10/14 11:44
     * @Return
     **/
    private List<Map<String, Object>>  handlerInsureIndividualCost(List<Map<String, Object>> feeDetailMapList ) {

        List<Map<String, Object>> groupListMap = new ArrayList<>();
        feeDetailMapList = feeDetailMapList.stream().filter(item -> StringUtils.isNotEmpty(MapUtils.get(item, "med_chrgitm_type"))).collect(Collectors.toList());
        Map<String, List<Map<String, Object>>> groupMap = feeDetailMapList.stream().
                collect(Collectors.groupingBy(item -> MapUtils.get(item, "med_chrgitm_type")));
        Map<String, Object> pMap = null;
        for (String key : groupMap.keySet()) {
            BigDecimal sumDetItemFeeSumamt = new BigDecimal(0.00); // 总费用
            BigDecimal AClassFee = new BigDecimal(0.00);  // 甲类费用
            BigDecimal BClassFee = new BigDecimal(0.00);  // 乙类费用
            BigDecimal CClassFee = new BigDecimal(0.00);  // 丙类费用
            System.out.println(key + "=====" + groupMap.get(key));
            Iterator<Map<String, Object>> iterator = groupMap.get(key).iterator();
            if (iterator.hasNext()) {
                pMap = new HashMap<>();
                List<Map<String, Object>> listMap = groupMap.get(key);
                for (Map<String, Object> item : listMap) {
                    DecimalFormat df1 = new DecimalFormat("0.00");
                    sumDetItemFeeSumamt = BigDecimalUtils.add(sumDetItemFeeSumamt,
                            BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert
                                    (MapUtils.get(item, "det_item_fee_sumamt") == null ? "" :
                                            MapUtils.get(item, "det_item_fee_sumamt").toString()))));
                    if("01".equals(MapUtils.get(item, "chrgItemLv"))){
                        AClassFee = BigDecimalUtils.add(AClassFee, BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "inscp_scp_amt") == null ? "" : MapUtils.get(item, "inscp_scp_amt").toString()))));
                        continue;
                    }
                    if("02".equals(MapUtils.get(item, "chrgItemLv"))){
                        BClassFee = BigDecimalUtils.add(BClassFee, BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "inscp_scp_amt") == null ? "" : MapUtils.get(item, "inscp_scp_amt").toString()))));
                        continue;
                    }
                    if("03".equals(MapUtils.get(item, "chrgItemLv"))){
                        CClassFee = BigDecimalUtils.add(CClassFee, BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "inscp_scp_amt") == null ? "" : MapUtils.get(item, "inscp_scp_amt").toString()))));
                        continue;
                    }
                }
                pMap.put("sumDetItemFeeSumamt", sumDetItemFeeSumamt);
                pMap.put("AClassFee", AClassFee);
                pMap.put("BClassFee", BClassFee);
                pMap.put("CClassFee", CClassFee);
                pMap.put("medChrgitmType", key);
                groupListMap.add(pMap);
            }
        }
        return groupListMap;
    }

    /**
     * 医保通一支付平台,结算单下载接口调用
     *
     * @param parameterMap
     * @Method updateUP_5265
     * @Desrciption
     * @Author liuqi1
     * @Date 2021/4/15 11:56
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     **/
    @Override
    public Map<String, Object> updateUP_5265(Map<String, Object> parameterMap) {
        String hospCode = MapUtils.get(parameterMap, "hospCode");
        String insureRegCode = MapUtils.get(parameterMap, "insureRegCode");
        String setlId = MapUtils.get(parameterMap, "setl_id");
        String psnNo = MapUtils.get(parameterMap, "psn_no");
        String mdtrtId = MapUtils.get(parameterMap, "mdtrt_id");
        Map<String, Object> dataMap = new HashMap<>();
        Map<String, Object> resultMap = new HashMap<>();
        InsureIndividualVisitDTO individualVisitDTO = insureIndividualVisitDAO.queryInsureIndividualVisit(parameterMap);
        if (individualVisitDTO == null) {
            throw new AppException("根据医保登记号和个人电脑号查询无此医保病人信息");
        }
        String insuplcAdmdvs = individualVisitDTO.getInsuplcAdmdvs(); // 就医地区划
        String medicineOrgCode = individualVisitDTO.getMedicineOrgCode();  // 医疗机构编码
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(medicineOrgCode);
        insureConfigurationDTO.setRegCode(individualVisitDTO.getInsureRegCode());
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        String mdtrtareaAdmvs = insureConfigurationDTO.getMdtrtareaAdmvs();
        if (insureConfigurationDTO == null) {
            throw new AppException("根据就诊人的医疗机构编码查询无此医保机构配置信息");
        }
        if(StringUtils.isEmpty(mdtrtareaAdmvs)){
            throw new AppException("医保配置信息管理里面就医地区划信息为空");
        }
        if(StringUtils.isEmpty(insuplcAdmdvs)){
            throw new AppException("该医保患者的参参保地区划为空");
        }
        // 就诊人的参保地区划和就医地区划不一致就是异地
        if (mdtrtareaAdmvs.substring(0,4).equals(insuplcAdmdvs.substring(0,4))) {
            dataMap.put("setl_id", setlId);//结算ID
            dataMap.put("psn_no", psnNo);//人员编号
            dataMap.put("mdtrt_id", mdtrtId);//就医登记号
            resultMap = invokingUpay(hospCode, insureRegCode, Constant.UnifiedPay.OUTPT.UP_5265, dataMap);

        } else {
            dataMap.put("mdtrtRegno", setlId);//结算ID
            dataMap.put("mdtrtId", mdtrtId);//就医登记号
            dataMap.put("medinsNo", insureConfigurationDTO.getOrgCode());//就医登记号
            if ("1".equals(individualVisitDTO.getIsHospital())) {
                resultMap = invokingUpay(hospCode, insureRegCode, Constant.UnifiedPay.OUTPT.UP_5270, dataMap);
            } else {
                resultMap = invokingUpay(hospCode, insureRegCode, Constant.UnifiedPay.OUTPT.UP_5269, dataMap);
            }
        }
        return resultMap;
    }

    /**
     * @param paraMap
     * @Method queryStatementInfo
     * @Desrciption 对账单查询打印
     * @Author liaojiguang
     * @Date 2021/10/21 09:01
     * @Return
     **/
    @Override
    public Map<String, Object> queryStatementInfo(Map<String, Object> paraMap) {
        Map<String,Object> resultMap = new HashMap<>();
        String type = MapUtils.get(paraMap,"statementType");
        List<Map<String,Object>> statementList = new ArrayList<>();
        switch (type) {
            case Constants.DZDLX.CX_PT: // 390 - (11 14 2101 2102 9904)
                paraMap.put("aae140","390");
                resultMap = this.getOrdinaryData(paraMap);
                break;
            case Constants.DZDLX.CX_DBBX: // 390 - 大病保险没有这个业务类型
                break;
            case Constants.DZDLX.CX_YWSH: // 390 -(9903,22)
                paraMap.put("aae140","390");
                resultMap = this.getYWSHData(paraMap);
                break;
            case Constants.DZDLX.ZG_PT: // 310 - (11 14 2101 2102 9904)
                paraMap.put("aae140","310");
                resultMap = this.getOrdinaryData(paraMap);
                break;
            case Constants.DZDLX.ZG_SYBX: // 310 - (51,52)
                paraMap.put("aae140","310");
                resultMap = this.getSYBXData(paraMap);
                break;
            case Constants.DZDLX.ZG_YWSH: // 310 -(9903,22)
                paraMap.put("aae140","310");
                resultMap = this.getYWSHData(paraMap);
                break;
            default:
                break;
        }

        // 查询合计金额行
        if (resultMap.containsKey("paraMap")) {
            Map<String,Object> sumMap = insureReversalTradeDAO.getStatementSumInfo(MapUtils.get(resultMap,"paraMap"));
            if (sumMap.containsKey("medfeeSumamt")) {
                sumMap.put("medfeeSumamtChina", MoneyUtils.convert(Double.parseDouble(MapUtils.get(sumMap, "medfeeSumamt").toString())));
            }

            if (sumMap.containsKey("planCXHJSumPrice")) {
                sumMap.put("planCXHJSumPriceChina",MoneyUtils.convert(Double.parseDouble(MapUtils.get(sumMap,"planCXHJSumPrice").toString())));
            }

            if (sumMap.containsKey("planSumPrice")) {
                sumMap.put("planSumPriceChina",MoneyUtils.convert(Double.parseDouble(MapUtils.get(sumMap,"planSumPrice").toString())));
            }
            resultMap.put("sumMap",sumMap);
        }

        // 查询医保基础信息
        InsureConfigurationDTO insureConfInfo = new InsureConfigurationDTO();
        insureConfInfo.setHospCode(MapUtils.get(paraMap,"hospCode"));
        insureConfInfo.setRegCode(MapUtils.get(paraMap,"insureRegCode"));
        insureConfInfo = insureConfigurationDAO.queryInsureIndividualConfig(insureConfInfo);
        insureConfInfo.setCrteName(MapUtils.get(paraMap,"statistician"));
        insureConfInfo.setStartDate(MapUtils.get(paraMap,"startDate"));
        insureConfInfo.setEndDate(MapUtils.get(paraMap,"endDate"));
        resultMap.put("insureConfInfo",insureConfInfo);
        return resultMap;
    }

    /**
     * @param paraMap
     * @Method queryDeclareInfos
     * @Desrciption 清算申报报表
     * @Author liaojiguang
     * @Date 2021/10/21 09:01
     * @Return
     **/
    @Override
    public PageDTO queryDeclareInfosPage(Map<String, Object> paraMap) {
        int pageNo = Integer.parseInt(MapUtils.get(paraMap, "pageNo") == null ? "1" : MapUtils.get(paraMap, "pageNo"));
        int pageSize = Integer.parseInt(MapUtils.get(paraMap, "pageSize") == null ? "10" : MapUtils.get(paraMap, "pageSize"));

        // 设置分页信息
        PageHelper.startPage(pageNo, pageSize);
        String declaraType = MapUtils.get(paraMap,"declaraType");
        switch (declaraType) {
            case Constants.SBLX.CZJM_ZY: // 城镇职工（住院）
                paraMap.put("insutype",Constant.UnifiedPay.XZLX.CZZG);
                paraMap.put("isHospital",Constants.SF.S);
                break;
            case Constants.SBLX.CXJM_ZY: // 城乡居民（住院）
                paraMap.put("insutype",Constant.UnifiedPay.XZLX.CXJM);
                paraMap.put("isHospital",Constants.SF.S);
                break;
            case Constants.SBLX.LX_ZY: // 离休（住院）
                paraMap.put("insutype",Constant.UnifiedPay.XZLX.LX);
                paraMap.put("isHospital",Constants.SF.S);
                break;
            case Constants.SBLX.MZ: // 门诊
                paraMap.put("isHospital",Constants.SF.F);
                return PageDTO.of(insureReversalTradeDAO.queryOutptDeclareInfosPage(paraMap));
            default:
                break;

        }
        return PageDTO.of(insureReversalTradeDAO.queryDeclareInfosPage(paraMap));
    }

    /**
     * @param paraMap
     * @Method querySumDeclareInfosPage
     * @Desrciption 清算申报合计报表
     * @Author liaojiguang
     * @Date 2021/10/21 09:01
     * @Return
     **/
    @Override
    public PageDTO querySumDeclareInfosPage(Map<String, Object> paraMap) {
        int pageNo = Integer.parseInt(MapUtils.get(paraMap, "pageNo") == null ? "1" : MapUtils.get(paraMap, "pageNo"));
        int pageSize = Integer.parseInt(MapUtils.get(paraMap, "pageSize") == null ? "10" : MapUtils.get(paraMap, "pageSize"));

        // 设置分页信息
        PageHelper.startPage(pageNo, pageSize);
        String declaraType = MapUtils.get(paraMap,"declaraType");
        switch (declaraType) {
            case Constants.SBLX.CZJM_ZY: // 城镇职工（住院）
                paraMap.put("insutype",Constant.UnifiedPay.XZLX.CZZG);
                paraMap.put("isHospital",Constants.SF.S);
                break;
            case Constants.SBLX.CXJM_ZY: // 城乡居民（住院）
                paraMap.put("insutype",Constant.UnifiedPay.XZLX.CXJM);
                paraMap.put("isHospital",Constants.SF.S);
                break;
            case Constants.SBLX.LX_ZY: // 离休（住院）
                paraMap.put("insutype",Constant.UnifiedPay.XZLX.LX);
                paraMap.put("isHospital",Constants.SF.S);
                break;
            case Constants.SBLX.MZ: // 门诊
                paraMap.put("isHospital",Constants.SF.F);
                return PageDTO.of(insureReversalTradeDAO.queryOutptSumDeclareInfosPage(paraMap));
            default:
                break;

        }
        return PageDTO.of(insureReversalTradeDAO.querySumDeclareInfosPage(paraMap));
    }

    // 对账单（生育）
    private Map<String, Object> getSYBXData(Map<String, Object> paraMap) {
        Map<String,Object> resultMap = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add(Constant.UnifiedPay.YWLX.SYMZ);
        list.add(Constant.UnifiedPay.YWLX.SYZY);
        paraMap.put("list",list);
        List<Map<String,Object>> statementList = insureReversalTradeDAO.queryStatementInfo(paraMap);
        if (!ListUtils.isEmpty(statementList)) {
            for (Map<String,Object> map : statementList) {
                String aka130 = MapUtils.get(map,"aka130"); // 医保业务类型
                String isRemote = MapUtils.get(map,"isRemote"); // 是否异地
                if (Constants.SF.F.equals(isRemote)) {
                    switch (aka130) {
                        case Constant.UnifiedPay.YWLX.SYMZ:
                            resultMap.put("localOrdinaryOutpt",map);
                            break;
                        case Constant.UnifiedPay.YWLX.SYZY:
                            resultMap.put("localOrdinaryInpt",map);
                            break;
                    }
                } else {
                    switch (aka130) {
                        case Constant.UnifiedPay.YWLX.SYMZ:
                            resultMap.put("offSiteOrdinaryOutpt",map);
                            break;
                        case Constant.UnifiedPay.YWLX.SYZY:
                            resultMap.put("offSiteOrdinaryInpt",map);
                            break;
                    }

                }
            }
        }
        resultMap.put("paraMap",paraMap);
        return resultMap;
    }

    // 对账单(意外伤害)
    private Map<String, Object> getYWSHData(Map<String, Object> paraMap) {
        Map<String,Object> resultMap = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add(Constant.UnifiedPay.YWLX.YWSHMZ);
        list.add(Constant.UnifiedPay.YWLX.WSZY);
        paraMap.put("list",list);
        List<Map<String,Object>> statementList = insureReversalTradeDAO.queryStatementInfo(paraMap);
        if (!ListUtils.isEmpty(statementList)) {
            for (Map<String,Object> map : statementList) {
                String aka130 = MapUtils.get(map,"aka130"); // 医保业务类型
                String isRemote = MapUtils.get(map,"isRemote"); // 是否异地
                if (Constants.SF.F.equals(isRemote)) {
                    switch (aka130) {
                        case Constant.UnifiedPay.YWLX.YWSHMZ:
                            resultMap.put("localOrdinaryOutpt",map);
                            break;
                        case Constant.UnifiedPay.YWLX.WSZY:
                            resultMap.put("localOrdinaryInpt",map);
                            break;
                    }
                } else {
                    switch (aka130) {
                        case Constant.UnifiedPay.YWLX.YWSHMZ:
                            resultMap.put("offSiteOrdinaryOutpt",map);
                            break;
                        case Constant.UnifiedPay.YWLX.WSZY:
                            resultMap.put("offSiteOrdinaryInpt",map);
                            break;
                    }

                }
            }
        }
        resultMap.put("paraMap",paraMap);
        return resultMap;
    }

    // 对账单（普通）
    private Map<String,Object> getOrdinaryData(Map<String, Object> paraMap) {
        Map<String,Object> resultMap = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add(Constant.UnifiedPay.YWLX.PTMZ);
        list.add(Constant.UnifiedPay.YWLX.MZMXB);
        list.add(Constant.UnifiedPay.YWLX.PTZYWZM);
        list.add(Constant.UnifiedPay.YWLX.DBTY);
        paraMap.put("list",list);
        List<Map<String,Object>> statementList = insureReversalTradeDAO.queryStatementInfo(paraMap);
        if (!ListUtils.isEmpty(statementList)) {
            for (Map<String,Object> map : statementList) {
                String aka130 = MapUtils.get(map,"aka130"); // 医保业务类型
                String isRemote = MapUtils.get(map,"isRemote"); // 是否异地
                if (Constants.SF.F.equals(isRemote)) {
                    switch (aka130) {
                        case Constant.UnifiedPay.YWLX.PTMZ:
                            resultMap.put("localOrdinaryOutpt",map);
                            break;
                        case Constant.UnifiedPay.YWLX.MZMXB:
                            resultMap.put("localSpecialOutpt",map);
                            break;
                        case Constant.UnifiedPay.YWLX.PTZYWZM:
                            resultMap.put("localOrdinaryInpt",map);
                            break;
                        case Constant.UnifiedPay.YWLX.DBTY:
                            resultMap.put("localDBTY",map);
                            break;
                    }
                } else {
                    switch (aka130) {
                        case Constant.UnifiedPay.YWLX.PTMZ:
                            resultMap.put("offSiteOrdinaryOutpt",map);
                            break;
                        case Constant.UnifiedPay.YWLX.MZMXB:
                            resultMap.put("offSiteSpecialOutpt",map);
                            break;
                        case Constant.UnifiedPay.YWLX.PTZYWZM:
                            resultMap.put("offSitelOrdinaryInpt",map);
                            break;
                        case Constant.UnifiedPay.YWLX.DBTY:
                            resultMap.put("offSiteDBTY",map);
                            break;
                    }

                }
            }
        }
        resultMap.put("paraMap",paraMap);
        return resultMap;
    }



}
