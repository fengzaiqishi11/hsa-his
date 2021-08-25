package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.outpt.bo.InsureUnifiedPayReversalTradeBO;
import cn.hsa.module.insure.outpt.dao.InsureReversalTradeDAO;
import cn.hsa.module.insure.outpt.dto.InsureReversalTradeDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private Logger logger = LoggerFactory.getLogger(this.getClass());


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
        dataMap.put("REFD_SETL_FLAG", MapUtils.get(parameterMap, "refdSetlFlag")); // 是否包含退费数据
        dataMap.put("setl_optins", MapUtils.get(parameterMap, "clr_optins"));//结算经办机构
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

    public static void main(String args[]) {
        String  medfeeSumamt = "10.1254";
        DecimalFormat df1 = new DecimalFormat("0.00");
        String a = df1.format(BigDecimalUtils.convert(medfeeSumamt)); //医疗费总额
        System.out.println(a);
    }


}
