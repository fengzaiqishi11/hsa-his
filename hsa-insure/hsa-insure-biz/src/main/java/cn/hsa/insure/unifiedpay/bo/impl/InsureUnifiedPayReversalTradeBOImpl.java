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
import cn.hsa.module.insure.module.dto.InsureIndividualSettleDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.outpt.bo.InsureUnifiedPayReversalTradeBO;
import cn.hsa.module.insure.outpt.dao.InsureReversalTradeDAO;
import cn.hsa.module.insure.outpt.dto.InsureReversalTradeDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.BigDecimalUtils;
import cn.hsa.util.Constants;
import cn.hsa.util.DataTypeUtils;
import cn.hsa.util.DateUtils;
import cn.hsa.util.HttpConnectUtil;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.MoneyUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.bo.impl
 * @Class_name: insureUnifiedPayReversalTradeBOImpl
 * @Describe: ????????????????????????, ????????????
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
     * ????????????????????????,????????????????????????
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
        dataMap.put("psn_no", insureReversalTradeDTO.getPsnNo()); // ????????????
        dataMap.put("omsgid", insureReversalTradeDTO.getOmsgid());// ??????????????????ID
        dataMap.put("oinfno", insureReversalTradeDTO.getOinfno());//???????????????

        //?????????????????????????????????????????????
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
            //????????????????????????
            int num = insureReversalTradeDAO.insertInsureReversalTrade(dto);
            if (num > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            throw new AppException("???????????????" + MapUtils.get(resultMap, "err_msg"));
        }

    }

    /**
     * ?????????????????????????????????????????????
     *
     * @param hospCode      ????????????
     * @param insureRegCode ??????????????????
     * @param infno         ?????????
     * @param dataMap       ????????????
     * @Method invokingUpay
     * @Desrciption
     * @Author liuqi1
     * @Date 2021/4/13 10:55
     * @Return java.lang.String
     **/
    @Override
    public Map<String, Object> invokingUpay(String hospCode, String insureRegCode, String infno, Map<String, Object> dataMap) {
        //????????????????????????
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);

        Map<String, Object> setDataMap = new HashMap<>();
        setDataMap.put("data", dataMap);

        //??????????????????????????????
        Map httpParam = new HashMap();
        httpParam.put("infno", infno);  //????????????
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //????????????????????????
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //????????????????????????

        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //??????????????????
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

        logger.info("?????????" + DateUtils.format() + "  url:" + url + "   ???????????????????????????" + jsonStr);
        String resultJson = HttpConnectUtil.sendPostToCall(url, jsonStr);
        if (StringUtils.isEmpty(resultJson)) {
            throw new AppException("?????????????????????????????????????????????");
        }
        int lengthStr = resultJson.length() > 800 ? 800 : resultJson.length();
        logger.info("?????????" + DateUtils.getNow() + "  ????????????????????????:" + resultJson.substring(0, lengthStr) + "......");
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson);

        if ("-1".equals(MapUtils.get(resultMap, "infcode"))) {
            throw new AppException("????????????????????????:" + MapUtils.get(resultMap, "err_msg"));
        }
        return resultMap;
    }

    /**
     * ??????????????????
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
        // ??????????????????
        PageHelper.startPage(insureReversalTradeDTO.getPageNo(), insureReversalTradeDTO.getPageSize());
        // ?????????????????????
        List<InsureReversalTradeDTO> insureReversalTradeDTOS = insureReversalTradeDAO.queryReversalTradePage(insureReversalTradeDTO);
        return PageDTO.of(insureReversalTradeDTOS);
    }


    /**
     * ?????????????????????????????????????????????
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

        // ??????????????????
        PageHelper.startPage(pageNo, pageSize);
        // ?????????????????????
        List<Map<String, Object>> list = insureReversalTradeDAO.queryDataWith5261(map);
        return PageDTO.of(list);
    }

    /**
     * ???????????????????????????????????????
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

        // ??????????????????
        PageHelper.startPage(pageNo, pageSize);

        //??????????????????
        if (StringUtils.isEmpty(MapUtils.get(map, "startDate"))) {
            map.put("startDate", DateUtils.format(DateUtils.getNow(), DateUtils.Y_M_D));
        }
        if (StringUtils.isEmpty(MapUtils.get(map, "endDate"))) {
            map.put("endDate", DateUtils.format(DateUtils.getNow(), DateUtils.Y_M_D));
        }

        // ???????????????REFD_SETL_FLAG = 1 ?????????????????????
        Map<String,Object> selectParamterCodeMap = new HashMap<>();
        selectParamterCodeMap.put("hospCode",MapUtils.get(map,"hospCode"));
        selectParamterCodeMap.put("code","REFD_SETL_FLAG");
        WrapperResponse<SysParameterDTO> wr = sysParameterService_consumer.getParameterByCode(selectParamterCodeMap);
        if (wr != null && wr.getData() != null && "1".equals(wr.getData().getValue()) ) {
            map.put("state","0");
        }

        // ?????????????????????
        List<Map<String, Object>> list = insureReversalTradeDAO.queryDataWith3201(map);
        //?????????????????????????????????
        selectParamterCodeMap.put("code","REFD_SETL_XIZANG");
        SysParameterDTO xz = sysParameterService_consumer.getParameterByCode(selectParamterCodeMap).getData();
        if(xz != null && Constants.SF.S.equals(xz.getValue())){
            // ??????????????????
            list = insureReversalTradeDAO.queryDataXiZangWith3201(map);
        }
        //?????????????????? ??? ???????????? ?????????????????? ??????????????????
        map.put("list", list);
        List<Map<String, Object>> list3202 = insureReversalTradeDAO.queryDataWith3202(map);
        List<Map<String, Object>> innerList = null;
            for (Map om : list) {
                innerList = new ArrayList<>();
                for (Map<String, Object> im : list3202) {
                    if("369900".equals(MapUtils.get(map, "insureRegCode"))){
                        if (MapUtils.get(om, "insutype").equals(MapUtils.get(im, "insutype")) ) {
                            innerList.add(im);
                        }
                    }else{
                        if (MapUtils.get(om, "insutype").equals(MapUtils.get(im, "insutype")) &&
                                MapUtils.get(om, "is_hospital").equals(MapUtils.get(im, "is_hospital"))) {
                            innerList.add(im);
                        }
                    }

                }
                om.put("detailList", innerList);
                om.put("stmt_begndate", MapUtils.get(map, "startDate"));
                om.put("stmt_enddate", MapUtils.get(map, "endDate"));
            }



        return PageDTO.of(list);
    }
    /**
     * ????????????????????????,?????????????????????????????????????????????
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

        //??????????????????????????????
        Map dataMap = new HashMap();
        dataMap.put("insutype", MapUtils.get(parameterMap, "insutype"));//??????
        dataMap.put("clr_type", MapUtils.get(parameterMap, "clr_type"));//????????????
        dataMap.put("REFD_SETL_FLAG", '0'); // ????????????????????????

        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if (insureConfigurationDTO == null) {
            throw new AppException("?????????????????????????????????");
        }
        dataMap.put("setl_optins", MapUtils.get(parameterMap, "clr_optins"));//????????????????????????????????????
        dataMap.put("stmt_begndate", MapUtils.get(parameterMap, "stmt_begndate"));//??????????????????
        dataMap.put("stmt_enddate", MapUtils.get(parameterMap, "stmt_enddate"));//??????????????????


        // ??????????????????
        String medfeeSumamt = MapUtils.get(parameterMap, "medfee_sumamt").toString();
        DecimalFormat df1 = new DecimalFormat("0.00");
        dataMap.put("medfee_sumamt", df1.format(BigDecimalUtils.convert(medfeeSumamt))); //???????????????
        dataMap.put("fund_pay_sumamt", MapUtils.get(parameterMap, "fund_pay_sumamt"));//??????????????????
        dataMap.put("acct_pay", MapUtils.get(parameterMap, "acct_pay"));//????????????????????????
        dataMap.put("fixmedins_setl_cnt", MapUtils.get(parameterMap, "fixmedins_setl_cnt"));//??????????????????????????????
        Map<String, Object> resultMap = invokingUpay(hospCode, insureRegCode, Constant.UnifiedPay.OUTPT.UP_3201, dataMap);
        return resultMap;
    }

    /**
     * ????????????????????????,????????????????????????????????????????????????
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
        dataMap.put("setl_optins", MapUtils.get(parameterMap, "clr_optins"));//??????????????????
        dataMap.put("clr_type", MapUtils.get(parameterMap, "clr_type"));//??????????????????
        dataMap.put("stmt_begndate", stmtBegndate);//??????????????????
        dataMap.put("stmt_enddate", stmtEnddate);//??????????????????
        dataMap.put("medfee_sumamt", MapUtils.get(parameterMap, "medfee_sumamt"));//???????????????
        dataMap.put("fund_pay_sumamt", MapUtils.get(parameterMap, "fund_pay_sumamt"));//??????????????????
        dataMap.put("cash_payamt", MapUtils.get(parameterMap, "person_price"));//??????????????????
        dataMap.put("fixmedins_setl_cnt", MapUtils.get(parameterMap, "fixmedins_setl_cnt"));//??????????????????????????????
        dataMap.put("fixmedins_code", MapUtils.get(parameterMap, "fixmedins_code"));// ??????????????????
        dataMap.put("REFD_SETL_FLAG", MapUtils.get(parameterMap, "refdSetlFlag")); // ????????????????????????
        dataMap.put("fixmedins_refd_setl_flag", MapUtils.get(parameterMap, "refdSetlFlag"));//??????????????????????????????

        //??????????????????
        Map<String, Object> selectDataListMap = new HashMap();
        selectDataListMap.put("startDate", stmtBegndate);
        selectDataListMap.put("endDate", stmtEnddate);
        selectDataListMap.put("clrType", MapUtils.get(parameterMap, "clr_type"));
        selectDataListMap.put("clrOptins", MapUtils.get(parameterMap, "clr_optins"));//??????????????????
        selectDataListMap.put("hospCode", hospCode);
        selectDataListMap.put("insureRegCode", insureRegCode);
        selectDataListMap.put("refdSetlFlag", MapUtils.get(parameterMap, "refdSetlFlag"));

        // ???????????????REFD_SETL_FLAG = 1 ?????????????????????
        Map<String,Object> selectParamterCodeMap = new HashMap<>();
        selectParamterCodeMap.put("hospCode",hospCode);
        selectParamterCodeMap.put("code","REFD_SETL_FLAG");
        WrapperResponse<SysParameterDTO> wr = sysParameterService_consumer.getParameterByCode(selectParamterCodeMap);
        if (wr != null && wr.getData() != null && "1".equals(wr.getData().getValue()) ) {
            selectDataListMap.put("state","0");
        }


        List<Map<String, Object>> datailList = insureReversalTradeDAO.queryDataWith3202(selectDataListMap);
        if (ListUtils.isEmpty(datailList)) {
            throw new AppException("??????????????????????????????????????????????????????");
        }
        Map<String, Object> httpParam = new HashMap<>();
        httpParam.put("data", dataMap);
        httpParam.put("fsUploadIn", datailList);
        httpParam.put("flag", "1");
        resultMap = invokingUpay(hospCode, insureRegCode, Constant.UnifiedPay.OUTPT.UP_3202, httpParam);
        return resultMap;
    }

    /**
     * ????????????????????????,???????????????????????????
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
        dataMap.put("mdtrt_id", MapUtils.get(parameterMap, "mdtrtId"));//??????ID
        dataMap.put("setl_id", MapUtils.get(parameterMap, "setlId"));//??????ID
        dataMap.put("psn_no", MapUtils.get(parameterMap, "psnNo"));//????????????

        Map<String, Object> resultMap = invokingUpay(hospCode, insureRegCode, Constant.UnifiedPay.OUTPT.UP_5261, dataMap);
        //Map<String, Object> resultMap = JSONObject.parseObject("{\t\"output\":{\t\t\"setlinfo\":{\t\t\t\"setl_time\":\"2021-04-09 13:49:42\",\t\t\t\"cvlserv_pay\":0.00,\t\t\t\"insu_admdvs\":\"\",\t\t\t\"med_fee_sumamt\":3300.00,\t\t\t\"year\":\"\",\t\t\t\"med_type\":\"2101\",\t\t\t\"psn_pay\":\"\",\t\t\t\"opter_id\":\"\",\t\t\t\"begndate\":\"\",\t\t\t\"hifmi_pay\":0.00,\t\t\t\"flxempe_flag\":\"\",\t\t\t\"psn_no\":\"46000000000000003501209658\",\t\t\t\"act_pay_dedc\":0.00,\t\t\t\"medins_setl_id\":\"H46010500002202104091349412420\",\t\t\t\"acct_mulaid_pay\":\"\",\t\t\t\"clr_way\":\"3\",\t\t\t\"oth_pay\":0,\t\t\t\"gend\":\"2\",\t\t\t\"mdtrt_id\":\"310000088057\",\t\t\t\"acct_pay\":0.00,\t\t\t\"fix_blng_admdvs\":\"\",\t\t\t\"insutype\":\"310\",\t\t\t\"inscp_scp_amt\":\"\",\t\t\t\"invono\":\"\",\t\t\t\"psn_part_amt\":0,\t\t\t\"enddate\":\"\",\t\t\t\"cash_payamt\":\"\",\t\t\t\"psn_type\":\"1101\",\t\t\t\"hifp_pay\":0.00,\t\t\t\"fixmedins_name\":\"\",\t\t\t\"overlmt_selfpay\":0.00,\t\t\t\"pay_loc\":\"\",\t\t\t\"clr_type\":\"21\",\t\t\t\"othfund_pay\":\"\",\t\t\t\"refd_setl_flag\":\"\",\t\t\t\"psnCash_pay\":0,\t\t\t\"cvlserv_flag\":\"0\",\t\t\t\"emp_name\":\"\",\t\t\t\"brdy\":\"1983-02-27 00:00:00\",\t\t\t\"naty\":\"1\",\t\t\t\"dise_codg\":\"\",\t\t\t\"fixmedins_code\":\"\",\t\t\t\"mdtrt_cert_type\":\"02\",\t\t\t\"opter_name\":\"\",\t\t\t\"dedc_hosp_lv\":\"\",\t\t\t\"balc\":0.00,\t\t\t\"psn_cert_type\":\"01\",\t\t\t\"fixmedins_poolarea\":\"\",\t\t\t\"hifob_pay\":0.00,\t\t\t\"nwb_flag\":\"\",\t\t\t\"inscp_amt\":\"\",\t\t\t\"hifes_pay\":0.00,\t\t\t\"cert_no\":\"460003198302276864\",\t\t\t\"fund_pay_sumamt\":0.00,\t\t\t\"opt_time\":\"\",\t\t\t\"fulamt_ownpay_amt\":3300.00,\t\t\t\"setl_id\":\"300000078066\",\t\t\t\"hosp_part_amt\":0,\t\t\t\"psn_name\":\"???????????????\",\t\t\t\"maf_pay\":0.00,\t\t\t\"prese_ifpay_amt\":0.00,\t\t\t\"dise_no\":\"\",\t\t\t\"clr_optins\":\"460400\",\t\t\t\"pool_prop_selfpay\":0.0000,\t\t\t\"dise_name\":\"\",\t\t\t\"lmtpric_hosp_lv\":\"\",\t\t\t\"age\":38.0,\t\t\t\"hosp_lv\":\"\"\t\t}\t},\t\"infcode\":\"0\",\t\"warn_msg\":\"\",\t\"cainfo\":\"\",\t\"err_msg\":\"\",\t\"refmsg_time\":\"20210415113416064\",\t\"signtype\":\"\",\t\"respond_time\":\"20210415113416108\",\t\"inf_refmsgid\":\"\"}");

        return resultMap;
    }

    /**
     * ????????????????????????,??????????????????????????????
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
        dataMap.put("mdtrt_id", MapUtils.get(parameterMap, "mdtrtId"));//??????ID
        dataMap.put("setl_id", MapUtils.get(parameterMap, "setlId"));//??????ID
        dataMap.put("psn_no", MapUtils.get(parameterMap, "psnNo"));//????????????

        Map<String, Object> resultMap = invokingUpay(hospCode, insureRegCode, Constant.UnifiedPay.OUTPT.UP_5262, dataMap);
        //Map<String, Object> resultMap = JSONObject.parseObject("{\t\"output\":{\t\t\"feesttinfo\":[\t\t\t{\t\t\t\t\"item_clab_amt\":0.00,\t\t\t\t\"med_chrgitm_type\":\"14\",\t\t\t\t\"fulamtOwnpay_amt\":\"\",\t\t\t\t\"overlmt_selfpay\":\"\",\t\t\t\t\"preselfpay_amt\":\"\",\t\t\t\t\"item_sumamt\":3300.00,\t\t\t\t\"item_claa_amt\":0.00\t\t\t}\t\t],\t\t\"setlinfo\":{\t\t\t\"setl_time\":\"2021-04-09 13:49:42\",\t\t\t\"cvlserv_pay\":0.00,\t\t\t\"insu_admdvs\":\"\",\t\t\t\"med_fee_sumamt\":3300.00,\t\t\t\"year\":\"\",\t\t\t\"med_type\":\"2101\",\t\t\t\"psn_pay\":\"\",\t\t\t\"opter_id\":\"\",\t\t\t\"begndate\":\"\",\t\t\t\"hifmi_pay\":0.00,\t\t\t\"flxempe_flag\":\"\",\t\t\t\"psn_no\":\"46000000000000003501209658\",\t\t\t\"act_pay_dedc\":0.00,\t\t\t\"medins_setl_id\":\"H46010500002202104091349412420\",\t\t\t\"acct_mulaid_pay\":\"\",\t\t\t\"clr_way\":\"3\",\t\t\t\"oth_pay\":0,\t\t\t\"gend\":\"2\",\t\t\t\"mdtrt_id\":\"310000088057\",\t\t\t\"acct_pay\":0.00,\t\t\t\"fix_blng_admdvs\":\"\",\t\t\t\"insutype\":\"310\",\t\t\t\"inscp_scp_amt\":\"\",\t\t\t\"invono\":\"\",\t\t\t\"psn_part_amt\":0,\t\t\t\"enddate\":\"\",\t\t\t\"cash_payamt\":\"\",\t\t\t\"psn_type\":\"1101\",\t\t\t\"hifp_pay\":0.00,\t\t\t\"fixmedins_name\":\"\",\t\t\t\"overlmt_selfpay\":0.00,\t\t\t\"pay_loc\":\"\",\t\t\t\"clr_type\":\"21\",\t\t\t\"othfund_pay\":\"\",\t\t\t\"refd_setl_flag\":\"\",\t\t\t\"psnCash_pay\":0,\t\t\t\"cvlserv_flag\":\"0\",\t\t\t\"emp_name\":\"\",\t\t\t\"brdy\":\"1983-02-27 00:00:00\",\t\t\t\"naty\":\"1\",\t\t\t\"dise_codg\":\"\",\t\t\t\"fixmedins_code\":\"\",\t\t\t\"mdtrt_cert_type\":\"02\",\t\t\t\"opter_name\":\"\",\t\t\t\"dedc_hosp_lv\":\"\",\t\t\t\"balc\":0.00,\t\t\t\"psn_cert_type\":\"01\",\t\t\t\"fixmedins_poolarea\":\"\",\t\t\t\"hifob_pay\":0.00,\t\t\t\"nwb_flag\":\"\",\t\t\t\"inscp_amt\":\"\",\t\t\t\"hifes_pay\":0.00,\t\t\t\"cert_no\":\"460003198302276864\",\t\t\t\"fund_pay_sumamt\":0.00,\t\t\t\"opt_time\":\"\",\t\t\t\"fulamt_ownpay_amt\":3300.00,\t\t\t\"setl_id\":\"300000078066\",\t\t\t\"hosp_part_amt\":0,\t\t\t\"psn_name\":\"???????????????\",\t\t\t\"maf_pay\":0.00,\t\t\t\"prese_ifpay_amt\":0.00,\t\t\t\"dise_no\":\"\",\t\t\t\"clr_optins\":\"460400\",\t\t\t\"pool_prop_selfpay\":0.0000,\t\t\t\"dise_name\":\"\",\t\t\t\"lmtpric_hosp_lv\":\"\",\t\t\t\"age\":38.0,\t\t\t\"hosp_lv\":\"\"\t\t}\t},\t\"infcode\":\"0\",\t\"warn_msg\":\"\",\t\"cainfo\":\"\",\t\"err_msg\":\"\",\t\"refmsg_time\":\"20210415113450337\",\t\"signtype\":\"\",\t\"respond_time\":\"20210415113450383\",\t\"inf_refmsgid\":\"\"}");


        return resultMap;
    }

    /**
     * @Method downLoadSettleInfo
     * @Desrciption ???????????????????????????
     * @Param C0000_BIZ2106   ???????????????????????????????????????????????????(??????)?????????  F0000_BIZ2106    ???????????????
     * C0000_BIZ2107   ??????????????????????????????????????????????????????(??????)?????????  F0000_BIZ2107  ???????????????
     * C0000_BIZ22   ????????????????????????????????????????????????????????????  F0000_BIZ22 ???????????????
     * C0000_BIZ51   ??????????????????????????????????????????????????????????????????  F0000_BIZ51 ???????????????
     * C0000_BIZ52  ??????????????????????????????????????????????????????????????????  F0000_BIZ52 ???????????????
     * C0000_BIZ53   ?????????????????????????????????????????????????????????????????????  F0000_BIZ53 ???????????????
     * C0000_BIZ9901  ????????????????????????????????????????????????????????????  F0000_BIZ9901 ???????????????
     * C0000_BIZ9903   ??????????????????????????????????????????????????????????????????  F0000_BIZ9903 ???????????????
     * C0000_BIZ11 :  ????????????????????????????????????????????????????????????  F0000_BIZ11 ???????????????
     * C0000_BIZ14 :   ???????????????????????????????????????????????????????????????  F0000_BIZ14 ???????????????
     * C0000_BIZ2101 :  ????????????????????????????????????????????????????????????  F0000_BIZ2101 ???????????????
     * C0000_BIZ2102  : ???????????????????????????????????????????????????????????????  F0000_BIZ2102 ???????????????
     * C0000_BIZ13  ??????????????????????????????????????????????????????  F0000_BIZ13 ???????????????
     * <p>
     * D320101 ???  ?????????????????????
     * D330101??? ????????????????????????????????????????????????
     * D310101/D390101 ??????????????????
     * D310301/D390201 ????????????
     * D310201/D390501  ??????????????????
     * D610101   ??????????????????
     * type:  1???????????????   0:???????????????   3??????????????????  4?????????????????????
     * @Author fuhui
     * @Date 2021/10/14 11:18
     * @Return
     **/
    public Map<String, Object> downLoadSettleInfo(Map<String, Object> map) {
        String insureSettleId = MapUtils.get(map, "insureSettleId"); // ????????????id
        String hospCode = MapUtils.get(map, "hospCode");
        InsureIndividualVisitDTO insureIndividualVisitDTO = insureUnifiedCommonUtil.commonGetVisitInfo(map);
        String insuplcAdmdvs = insureIndividualVisitDTO.getInsuplcAdmdvs(); // ???????????????
        String mdtrtareaAdmvs = insureIndividualVisitDTO.getMdtrtareaAdmvs(); //  ???????????????
        String isHospital = insureIndividualVisitDTO.getIsHospital(); // ????????????
        String aae140 = insureIndividualVisitDTO.getAae140(); // ??????
        String hospLv = ""; // ????????????
        String hospName = "";// ????????????
        String mdOrInsuplcAdmdvs = "";
        String settleTitle; // ???????????????
        String provinceName = "";
        String mzOrZyName; // ?????? ????????????
        String mdOrInsuplcAdmdvsName = "";
        String titleEnd = ""; // ????????????
        String specialName = "";// ?????????????????????
        String aee140Str = ""; // ??????
        Boolean specialOneSettle = false; // ??????????????????????????????????????????
        boolean oneSettle = false; // ???????????????????????????
        String psnIdetType = ""; //??????????????????
        boolean isRemote = false; // ?????????????????????????????????
        boolean snRemote = false; // ?????????????????????????????????
        boolean jxSettle = false; // ????????????????????????
        boolean gsSettle = false; // ????????????????????????
        Map<String, Object> oneSettleMap;
        Map<String, Object> fourPartMap;
        map.put("code", "HOSP_MEDICINS_INFO");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(map).getData();
        if (sysParameterDTO == null || StringUtils.isEmpty(sysParameterDTO.getValue())) {
            throw new AppException("????????????????????????HOSP_MEDICINS_INFO");
        }
        else {
            String value = sysParameterDTO.getValue();
            Map<String, Object> stringObjectMap = JSON.parseObject(value, Map.class);
            for (String key : stringObjectMap.keySet()) {
                if ("hospLv".equals(key)) {  // ????????????
                    hospLv = MapUtils.get(stringObjectMap, key);
                }
                if ("hospName".equals(key)) { // ????????????
                    hospName = MapUtils.get(stringObjectMap, key);
                }
                if ("provinceName".equals(key)) { // ????????????
                    provinceName = MapUtils.get(stringObjectMap, key);
                }
                if ("titleEnd".equals(key)) {
                    titleEnd = MapUtils.get(stringObjectMap, key);
                }
                if ("specialName".equals(key)) {
                    specialName = MapUtils.get(stringObjectMap, key);
                }
                if ("aee140Str".equals(key)) {
                    aee140Str = MapUtils.get(stringObjectMap, key);
                }
                if ("jxSettle".equals(key)) {
                    jxSettle = MapUtils.get(stringObjectMap, key);
                }
                if ("gsSettle".equals(key)) {
                    gsSettle = MapUtils.get(stringObjectMap, key);
                }
                if ("mdOrInsuplcAdmdvs".equals(key)) {
                    mdOrInsuplcAdmdvs = MapUtils.get(stringObjectMap, key); // ????????????????????????????????? ?????????????????????
                }
                // ?????????1  ??????????????????????????????
                // ?????????0 ??????????????????????????????
                if (Constants.SF.S.equals(mdOrInsuplcAdmdvs)) {
                    if (insuplcAdmdvs.equals(key)) {
                        mdOrInsuplcAdmdvsName = MapUtils.get(stringObjectMap, key);
                    }
                } else {
                    if (mdtrtareaAdmvs.equals(key)) {
                        mdOrInsuplcAdmdvsName = MapUtils.get(stringObjectMap, key);
                    }
                }
            }
            if (Constants.SF.S.equals(isHospital) && !"340".equals(aae140)) {
                mzOrZyName = "??????";
            } else if ("340".equals(aae140)) {
                mzOrZyName = "??????";
            } else {
                mzOrZyName = "(???????????????)??????";
            }
            settleTitle = new StringBuilder().append(provinceName).append(mdOrInsuplcAdmdvsName).
                    append(mzOrZyName).append(titleEnd).toString();

        }
        if (StringUtils.isNotEmpty(aee140Str)) {
            String[] split = aee140Str.split(",");
            List<String> strings = Arrays.asList(split);
            if ("?????????".equals(specialName) && Constants.SF.S.equals(isHospital) && strings.contains(aae140)) {
                specialOneSettle = true;
            }
        }
        if (StringUtils.isEmpty(insuplcAdmdvs)) {
            throw new AppException("?????????????????????");
        }
        if (StringUtils.isEmpty(mdtrtareaAdmvs)) {
            throw new AppException("?????????????????????");
        }
        Map<String, Object> medisnInfMap = new HashMap<>();
        medisnInfMap.put("settleTitle", settleTitle);
        medisnInfMap.put("hospLv", hospLv);
        medisnInfMap.put("hospName", hospName);
        medisnInfMap.put("printDate", DateUtils.format(new Date(), DateUtils.Y_M_D));
        // ????????????????????????  ??????????????????
        Map<String, Object> data = insureUnifiedBaseService.querySettleDeInfoBySettleId(map).getData();
        Map<String, Object> setlInfoMap = MapUtils.get(data, "setlinfo"); // ??????????????????
        List<Map<String, Object>> setldetail = MapUtils.get(data, "setldetail"); // ??????????????????

        Object obj = MapUtils.get(setlInfoMap, "act_pay_dedc");
        BigDecimal actPayDedc = new BigDecimal(0.00);
        //  ????????????????????????????????????????????????decimal?????????
        if (obj instanceof String) {
            actPayDedc = BigDecimalUtils.convert(MapUtils.get(setlInfoMap, "act_pay_dedc"));
        } else {
            actPayDedc = MapUtils.get(setlInfoMap, "act_pay_dedc");
        }
        if (!mdtrtareaAdmvs.substring(0, 2).equals(insuplcAdmdvs.substring(0, 2)) && Constants.SF.S.equals(isHospital)) {

            isRemote = true;
        }
        if (Constants.SF.S.equals(isHospital) && !isRemote && !jxSettle && !snRemote) {
            oneSettleMap = checkOneSettle(map, insureIndividualVisitDTO);
            if (!MapUtils.isEmpty(oneSettleMap)) {
                oneSettle = MapUtils.get(oneSettleMap, "flag");
                psnIdetType = MapUtils.get(oneSettleMap, "psnIdetType");
            }
        }
        InsureIndividualSettleDTO individualSettleDTO = insureReversalTradeDAO.querySettleInfo(map);
        Map<String, Object> baseInfoMap = handlerInptSettleParam(insureIndividualVisitDTO, setlInfoMap,
                oneSettle, psnIdetType, specialOneSettle, jxSettle, gsSettle);
        /**
         * ????????????????????????????????????????????? ??????????????????????????????????????????
         * 1.????????????????????????????????????????????????????????????????????????
         */
        if (isRemote || snRemote) {
            List<Map<String, Object>> mapList = insureReversalTradeDAO.selectMdOrIns(hospCode);
            if (!ListUtils.isEmpty(mapList)) {
                String insuplcAdmdvsName = ""; // ???????????????????????????
                String mdtrtareaAdmvsName = ""; // ???????????????????????????
                String admdvsCode = ""; // ????????????
                String admdvsName = ""; // ????????????
                for (Map<String, Object> item : mapList) {
                    admdvsCode = MapUtils.get(item, "admdvs_code");
                    admdvsName = MapUtils.get(item, "admdvs_name");
                    if (insuplcAdmdvs.equals(admdvsCode)) {
                        insuplcAdmdvsName = admdvsName;
                        baseInfoMap.put("insuplcAdmdvsName", insuplcAdmdvsName);
                        continue;
                    }
                    if (mdtrtareaAdmvs.equals(admdvsCode)) {
                        mdtrtareaAdmvsName = admdvsName;
                        baseInfoMap.put("mdtrtareaAdmvsName", mdtrtareaAdmvsName);
                        continue;
                    }
                }
            }
        }
        if (!mdtrtareaAdmvs.substring(0, 4).equals(insuplcAdmdvs.substring(0, 4)) && Constants.SF.S.equals(isHospital)) {
            snRemote = true;
        }
        fourPartMap = handlerInsureSettleFee(setlInfoMap, isRemote,snRemote, individualSettleDTO, setldetail, jxSettle, gsSettle);

        /**
         * ???????????????
         *
         */
        BigDecimal totalAdvance = insureIndividualVisitDTO.getTotalAdvance(); // ???????????????
        Object psnCashPay = MapUtils.get(fourPartMap, "psnCashPay"); // ??????????????????

        BigDecimal psnCashPayBigDecimal;
        if (ObjectUtils.isEmpty(psnCashPay)) {
            psnCashPayBigDecimal = new BigDecimal(0.00);
        } else if (psnCashPay instanceof String) {
            psnCashPayBigDecimal = BigDecimalUtils.convert((String) psnCashPay);
        } else {
            psnCashPayBigDecimal = (BigDecimal) psnCashPay;
        }
        if (BigDecimalUtils.greater(psnCashPayBigDecimal, totalAdvance)) {
            BigDecimal price = BigDecimalUtils.subtract(psnCashPayBigDecimal, totalAdvance);
            baseInfoMap.put("yjyjj", price); // ???????????????
            baseInfoMap.put("yjyjjCN", numberToCN.number2CNMontrayUnit(price)); // ???????????????
            baseInfoMap.put("ytyjjCN", "??????"); // ???????????????
            baseInfoMap.put("ytyjj", new BigDecimal(0.00)); // ???????????????
        } else {
            BigDecimal price = BigDecimalUtils.subtract(totalAdvance, psnCashPayBigDecimal);
            baseInfoMap.put("ytyjj", price); // ???????????????
            baseInfoMap.put("ytyjjCN", numberToCN.number2CNMontrayUnit(price)); // ???????????????
            baseInfoMap.put("yjyjjCN", "??????"); // ???????????????
            baseInfoMap.put("yjyjj", new BigDecimal(0.00)); // ???????????????
        }

        BigDecimal hifpPay = MapUtils.get(setlInfoMap, "hifp_pay"); // ???????????????
        BigDecimal medfeeSumamt = MapUtils.get(setlInfoMap, "medfee_sumamt"); // ???????????????
        List<Map<String, Object>> sumInfoMapList = new ArrayList<>();
        /**
         *  ?????????true ???????????????????????? ??????????????????????????????????????????????????????????????????????????????
         *  ??????????????????????????????????????????????????????????????????????????????????????????????????????
         */
        Map<String, Object> setlProcInfoMap = new HashMap<>();
        if ((oneSettle || specialOneSettle) && Constants.SF.S.equals(isHospital) && !jxSettle) {
            Map<String, Object> sumInfoMap = insureUnifiedBaseService.queryPatientSumInfo(map).getData();
            sumInfoMapList = MapUtils.get(sumInfoMap, "resultDataMap");
            /**
             * ??????????????????????????????
             */
            if (individualSettleDTO != null) {
                setlInfoMap.put("psn_part_amt", individualSettleDTO.getPsnPartAmt());
            }
            setlProcInfoMap = handSetlProcInfo(setldetail, setlInfoMap);
            map.put("setlProcInfoMap", setlProcInfoMap);

        } else {
            if (Constants.SF.F.equals(isHospital)) {
                insureIndividualVisitDTO.setInsureSettleId(null);
            } else {
                insureIndividualVisitDTO.setInsureSettleId(null);
            }
            if (!jxSettle) {
                // ???????????????
                sumInfoMapList = insureIndividualVisitDAO.querySelectInsureSumInfo(insureIndividualVisitDTO);
            }
        }

        Map<String, Object> pastFeeMap = new HashMap<>();
        // ????????????  340  ???????????????????????????  ????????????
        if (!"340".equals(aae140) && !jxSettle) {
            pastFeeMap = handlerPastFee(sumInfoMapList, isHospital, oneSettle, specialOneSettle,gsSettle);
        }
        if ("340".equals(aae140) && (!oneSettle || !specialOneSettle) && !jxSettle) {
            // ?????????????????????
            BigDecimal pastFeeFund = handlerFeeFund(setldetail);
            map.put("pastFeeFund", pastFeeFund); // ????????????
            pastFeeMap = handlerSpecialPastFee(sumInfoMapList);
            fourPartMap.put("pastFeeFund",pastFeeFund);
        }

        /**
         * ??????his???????????????,????????????????????????  ??????his?????????????????????????????????????????????????????????????????????????????????
         */

        List<Map<String, Object>> feeMapList;
        Map<String, Object> feeMap = insureUnifiedBaseService.queryFeeDetailInfo(map).getData();
        List<Map<String, Object>> outptMap = MapUtils.get(feeMap, "outptMap");
        if (!jxSettle) {
            // ????????????????????????
            feeMapList = handlerInsureIndividualCost(outptMap);
        } else {
            feeMapList = handlerJxCostFee(outptMap);
        }

        /**
         * ????????????
         * 1.????????????????????????  ???????????????????????????
         */
        if (Constants.SF.S.equals(isHospital) && ((!oneSettle && !specialOneSettle) || isRemote) && !jxSettle && !gsSettle) {
            Map<String, Object> policyMap = insureUnifiedBaseService.queryPolicyInfo(map).getData();
            List<Map<String, Object>> policyMapList = MapUtils.get(policyMap, "outptMap");
            map.put("policyMapList", policyMapList);
        }

        /**
         * ????????????
         * 1.????????????????????????  ????????????????????????
         */
        BigDecimal sumBxFeeNumber = new BigDecimal(0.00);
        if (Constants.SF.S.equals(isHospital) && (oneSettle || specialOneSettle) && !jxSettle && !gsSettle) {
            Map<String, Object> policyMap = insureUnifiedBaseService.queryPolicyInfo(map).getData();
            List<Map<String, Object>> policyMapList = MapUtils.get(policyMap, "outptMap");
            if (!MapUtils.isEmpty(setlProcInfoMap)) {
                sumBxFeeNumber = MapUtils.get(setlProcInfoMap, "sumBxFeeNumber");
            }
            Map<String, Object> handlerPolicyMap = handlerPolicy(policyMapList, hospName, actPayDedc, hifpPay, medfeeSumamt, insureIndividualVisitDTO, sumBxFeeNumber);
            map.put("handlerPolicyMap", handlerPolicyMap);
        }
        map.put("setldetail", setldetail); // ????????????
        map.put("pastFeeMap", pastFeeMap); // ?????????????????????????????????
        map.put("feeMapList", feeMapList); // ???????????????????????????
        map.put("fourPartMap", fourPartMap);// ??????????????????
        map.put("baseInfoMap", baseInfoMap);//???????????????????????????????????????
        map.put("aae140", aae140); // ?????????????????? 340  ?????????????????????
        if ((oneSettle || specialOneSettle) && !jxSettle) {
            settleTitle = new StringBuilder().append(provinceName).append(mdOrInsuplcAdmdvsName).
                    append("????????????????????????????????????????????????").toString();
            medisnInfMap.put("settleTitle", settleTitle);
            map.put("oneSettle", true); //  ??????????????????????????????????????????????????????????????????
        } else if (isRemote && !jxSettle) {
            settleTitle = "??????????????????????????????????????????????????????????????????";
            medisnInfMap.put("settleTitle", settleTitle);
            map.put("oneSettle", false);
        } else if (snRemote && !jxSettle) {
            settleTitle = "????????????????????????????????????????????????";
            medisnInfMap.put("settleTitle", settleTitle);
            map.put("oneSettle", false);
        } else if (jxSettle) {
            settleTitle = "??????????????????????????????????????????????????????";
            medisnInfMap.put("settleTitle", settleTitle);
            map.put("oneSettle", false);
        }else if(gsSettle) {
            settleTitle = "??????????????????????????????????????????";
            medisnInfMap.put("settleTitle", settleTitle);
            map.put("oneSettle", false);
        }else {
            map.put("oneSettle", false); //  ??????????????????????????????????????????????????????????????????
        }
        map.put("jxSettle", jxSettle);
        map.put("gsSettle", gsSettle);
        map.put("snRemote", snRemote);
        map.put("isHospital", isHospital);
        map.put("isRemote", isRemote);
        medisnInfMap.put("medisnCode", insureIndividualVisitDTO.getMedicineOrgCode()); // ??????????????????
        map.put("medisnInfMap", medisnInfMap); // ???????????????????????????
        map.put("insuplcAdmdvs", insuplcAdmdvs);
        map.put("mdtrtareaAdmvs", mdtrtareaAdmvs);
        if(map.get("insureIndividualVisitDTO") != null){
            Map<String,Object> insureIndividualVisitDTOMap = JSONObject.parseObject(JSON.toJSONString(map.get("insureIndividualVisitDTO")));
            map.put("insureIndividualVisitDTOMap",insureIndividualVisitDTOMap);
        }
        String json = JSONObject.toJSONString(map);
        System.out.println("----======" + json);
        return map;
    }

    /**
     * @Method handlerJxCostFee
     * @Desrciption ??????????????????????????????????????????
     * ???????????? det_item_fee_sumamt
     * ?????????????????????inscp_scp_amt
     * ??????????????? preselfpay_amt
     * ???????????? overlmt_amt
     * ?????????fulamt_ownpay_amt
     * @Param
     * @Author fuhui
     * @Date 2021/12/6 11:37
     * @Return
     **/
    private List<Map<String, Object>> handlerJxCostFee(List<Map<String, Object>> feeDetailMapList) {
        List<Map<String, Object>> groupListMap = new ArrayList<>();
        if (!ListUtils.isEmpty(feeDetailMapList)) {
            feeDetailMapList = feeDetailMapList.stream().filter(item -> StringUtils.isNotEmpty(MapUtils.get(item, "med_chrgitm_type"))).collect(Collectors.toList());
            Map<String, List<Map<String, Object>>> groupMap = feeDetailMapList.stream().
                    collect(Collectors.groupingBy(item -> MapUtils.get(item, "med_chrgitm_type")));
            Map<String, Object> pMap = null;
            for (String key : groupMap.keySet()) {
                BigDecimal sumDetItemFeeSumamt = new BigDecimal(0.00); // ?????????
                BigDecimal inscpScpAmt = new BigDecimal(0.00); // ??????????????????
                BigDecimal preselfpayAmt = new BigDecimal(0.00);  //  ????????????
                BigDecimal overlmtAmt = new BigDecimal(0.00);  //  ?????????
                BigDecimal fulamtOwnpayAmt = new BigDecimal(0.00);  // ??????
                System.out.println(key + "=====" + groupMap.get(key));
                Iterator<Map<String, Object>> iterator = groupMap.get(key).iterator();
                if (iterator.hasNext()) {
                    pMap = new HashMap<>();
                    List<Map<String, Object>> listMap = groupMap.get(key);
                    for (Map<String, Object> item : listMap) {
                        sumDetItemFeeSumamt = BigDecimalUtils.add(sumDetItemFeeSumamt,BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(item, "det_item_fee_sumamt"))));
                        inscpScpAmt = BigDecimalUtils.add(inscpScpAmt,BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(item, "inscp_scp_amt"))));
                        preselfpayAmt = BigDecimalUtils.add(preselfpayAmt,BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(item, "preselfpay_amt"))));
                        overlmtAmt = BigDecimalUtils.add(overlmtAmt,BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(item, "overlmt_amt"))));
                        fulamtOwnpayAmt = BigDecimalUtils.add(fulamtOwnpayAmt,BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(item, "fulamt_ownpay_amt"))));
                    }
                    pMap.put("sumDetItemFeeSumamt", sumDetItemFeeSumamt);
                    pMap.put("inscpScpAmt", inscpScpAmt);
                    pMap.put("preselfpayAmt", preselfpayAmt);
                    pMap.put("overlmtAmt", overlmtAmt);
                    pMap.put("fulamtOwnpayAmt", fulamtOwnpayAmt);
                    pMap.put("medChrgitmType", key);
                    groupListMap.add(pMap);
                }
            }
        }
        return groupListMap;
    }

    /**
     * @Method handlerPolicy
     * @Desrciption ???????????????????????????????????????
     * @Param medfeeSumamt??????????????????   sumBxFeeNumber????????????????????????  hifpPay;??????????????????
     * insureIndividualVisitDTO??????????????????DTO actPayDedc:?????????
     * hospName???????????????  policyMapList;?????????????????????
     * @Author fuhui
     * @Date 2021/10/26 19:10
     * @Return
     **/
    private Map<String, Object> handlerPolicy(List<Map<String, Object>> policyMapList, String hospName,
                                              BigDecimal actPayDedc, BigDecimal hifpPay, BigDecimal medfeeSumamt,
                                              InsureIndividualVisitDTO insureIndividualVisitDTO, BigDecimal sumBxFeeNumber) {
        Map<String, Object> handlerPolicyMap = new HashMap<>();
        BigDecimal s1 = new BigDecimal(0.00);
        BigDecimal s2 = new BigDecimal(0.00);
        BigDecimal s3 = new BigDecimal(0.00);
        BigDecimal s4 = new BigDecimal(0.00);
        BigDecimal s5 = new BigDecimal(0.00);
        String polItemName = "";
        BigDecimal polItemPaySum = new BigDecimal(0.00);
        for (Map<String, Object> item : policyMapList) {
            polItemName = MapUtils.get(item, "polItemName");
            polItemPaySum = MapUtils.get(item, "polItemPaySum");
            if ("????????????????????????".equals(polItemName.trim()) || "?????????".equals(polItemName.trim()) || "???????????????".equals(polItemName.trim())) {
                s1 = BigDecimalUtils.add(s1, polItemPaySum);
                continue;
            }
            if ("????????????????????????".equals(polItemName.trim()) || "???????????????".equals(polItemName.trim())) {
                s2 = BigDecimalUtils.add(s2, polItemPaySum);
                continue;
            }
            if ("??????????????????????????????".equals(polItemName.trim())) {
                s3 = BigDecimalUtils.add(s3, polItemPaySum);
                continue;
            }
            if ("???????????????".equals(polItemName.trim())) {
                s4 = BigDecimalUtils.add(s4, polItemPaySum);
                continue;
            }
        }
        handlerPolicyMap.put("s1", s1);
        handlerPolicyMap.put("s2", s2);
        handlerPolicyMap.put("s3", s3);
        handlerPolicyMap.put("s4", s4);
        handlerPolicyMap.put("hospName", hospName); // ?????????????????? ?????????
        handlerPolicyMap.put("hifpPayNumber", hifpPay); // ????????????????????????????????????
        handlerPolicyMap.put("hifpPay", numberToCN.number2CNMontrayUnit(hifpPay)); // ????????????????????????????????????
//        if (BigDecimalUtils.isZero(hifpPay)){
//            s5 = hifpPay; // ????????????????????????0  ?????????10%????????????????????????0
//            handlerPolicyMap.put("divideFormat","0.00%"); // ?????????
//        }
//        else{
//            s5 = BigDecimalUtils.multiply(sumBxFeeNumber,new BigDecimal(0.10));
//            BigDecimal divide = BigDecimalUtils.divide(sumBxFeeNumber, medfeeSumamt);
//            DecimalFormat decimalFormat = new DecimalFormat("0.00%");
//            String divideFormat = decimalFormat.format(divide);
//            handlerPolicyMap.put("divideFormat",divideFormat); // ?????????
//        }
        handlerPolicyMap.put("divideFormat", ""); // ?????????
        if (BigDecimalUtils.isZero(hifpPay)) {
            s5 = hifpPay; // ????????????????????????0  ?????????10%????????????????????????0
            handlerPolicyMap.put("divideFormat", "0.00%"); // ?????????
        } else {
            s5 = BigDecimalUtils.multiply(sumBxFeeNumber, new BigDecimal(0.10));
            BigDecimal divide = BigDecimalUtils.divide(sumBxFeeNumber, medfeeSumamt);
            DecimalFormat decimalFormat = new DecimalFormat("0.00%");
            String divideFormat = decimalFormat.format(divide);
            handlerPolicyMap.put("divideFormat", divideFormat); // ?????????
        }
        List<Map<String, Object>> sumInfo = insureIndividualVisitDAO.querySelectInsureSumInfo(insureIndividualVisitDTO);
        String cumTypeCode = "";
        BigDecimal cum = new BigDecimal(0.00);
        BigDecimal sumCum = new BigDecimal(0.00);
        for (Map<String, Object> item : sumInfo) {
            cumTypeCode = MapUtils.get(item, "cum_type_code");
            cum = MapUtils.get(item, "cum");
            if ("Q0000".equals(cumTypeCode)) {   // ???????????????(????????????)
                sumCum = BigDecimalUtils.add(cum, sumCum);
                break;
            }
        }
        handlerPolicyMap.put("sumCum", sumCum);// ???????????????(????????????)
        handlerPolicyMap.put("s5", s5);
        handlerPolicyMap.put("actPayDedc", actPayDedc);
        return handlerPolicyMap;
    }

    /**
     * @Method handSetlProcInfo
     * @Desrciption ??????????????????????????????????????????
     * @Param setldetail  ????????????????????????
     * @Author fuhui
     * @Date 2021/10/26 13:48
     * @Return
     **/
    private Map<String, Object> handSetlProcInfo(List<Map<String, Object>> setldetail, Map<String, Object> setlInfoMap) {
        Map<String, Object> setlProcMap = new HashMap<>();
        String setlProcInfo = ""; //????????????
        BigDecimal hifpPay = MapUtils.get(setlInfoMap, "hifp_pay");//????????????????????????????????????
        BigDecimal psnPartAmt = MapUtils.get(setlInfoMap, "psn_part_amt"); // ?????????????????????
        BigDecimal fundPaySumamt = MapUtils.get(setlInfoMap, "fund_pay_sumamt"); // ???????????????
        BigDecimal fundPayamt = new BigDecimal(0.00); // ??????????????????
        BigDecimal s2 = new BigDecimal(0.00); // ????????????????????????
        BigDecimal s3 = new BigDecimal(0.00); // ????????????
        BigDecimal s4 = new BigDecimal(0.00);  // ?????????????????????
        BigDecimal s5 = new BigDecimal(0.00); //??????????????????
        BigDecimal s6 = new BigDecimal(0.00); // ??????????????????
        BigDecimal s7 = new BigDecimal(0.00);  // ????????????????????????
        BigDecimal s9 = new BigDecimal(0.00); // ??????????????????
        for (Map<String, Object> map : setldetail) {
            setlProcInfo = MapUtils.get(map, "setl_proc_info");
            if (MapUtils.get(map, "fund_payamt") instanceof String) {
                fundPayamt = BigDecimalUtils.convert(MapUtils.get(map, "fund_payamt"));
            } else {
                fundPayamt = MapUtils.get(map, "fund_payamt");
            }
            if ("390201".equals(setlProcInfo)) {  // ????????????????????????
                s2 = BigDecimalUtils.add(s2, fundPayamt);
            }
            if ("390401".equals(setlProcInfo)) {  // ????????????
                s3 = BigDecimalUtils.add(s3, fundPayamt);
            }
            if ("620101".equals(setlProcInfo)) {  // ?????????????????????
                s4 = BigDecimalUtils.add(s4, fundPayamt);
            }
            if ("610101".equals(setlProcInfo)) {  // ??????????????????
                s5 = BigDecimalUtils.add(s5, fundPayamt);
            }
            if ("630101".equals(setlProcInfo)) {  // ??????????????????
                s6 = BigDecimalUtils.add(s6, fundPayamt);
            }
            if ("640101".equals(setlProcInfo)) {  // ????????????????????????
                s7 = BigDecimalUtils.add(s7, fundPayamt);
            }
            if ("999996".equals(setlProcInfo)) {  // ??????????????????
                s9 = BigDecimalUtils.add(s9, fundPayamt);
            }
        }
        Set<BigDecimal> bigDecimalSet = new HashSet<>();
        BigDecimal sumBxFee = new BigDecimal(0.00);
        bigDecimalSet.add(hifpPay);
        bigDecimalSet.add(s2);
        bigDecimalSet.add(s3);
        bigDecimalSet.add(s4);
        bigDecimalSet.add(s5);
        bigDecimalSet.add(s6);
        bigDecimalSet.add(s7);
        bigDecimalSet.add(s9);
        for (BigDecimal s : bigDecimalSet) {
            sumBxFee = BigDecimalUtils.add(sumBxFee, s);
        }

        setlProcMap.put("hifpPay", hifpPay);
        setlProcMap.put("psnPartAmt", psnPartAmt);
        setlProcMap.put("fundPaySumamt", fundPaySumamt);
        setlProcMap.put("s2", s2);
        setlProcMap.put("s3", s3);
        setlProcMap.put("s4", s4);
        setlProcMap.put("s5", s5);
        setlProcMap.put("s6", s6);
        setlProcMap.put("s7", s7);
        setlProcMap.put("s9", s9);
        setlProcMap.put("sumBxFeeNumber", sumBxFee);
        setlProcMap.put("sumBxFee", numberToCN.number2CNMontrayUnit(sumBxFee));
        return setlProcMap;
    }

    /**
     * @Method checkOneSettle
     * @Desrciption ????????????????????????????????????
     * @Param map insureIndividualVisitDTO
     * @Author fuhui
     * @Date 2021/10/23 15:20
     * @Return
     **/
    public Map<String, Object> checkOneSettle(Map<String, Object> map, InsureIndividualVisitDTO insureIndividualVisitDTO) {
        Map<String, Object> infoMap = new HashMap<>();
        //??????????????????????????????????????????????????????
        if (Constant.UnifiedPay.CKLX.DZPZ.equals(insureIndividualVisitDTO.getMdtrtCertType())) {
            insureIndividualVisitDTO.setMdtrtCertType(Constant.UnifiedPay.CKLX.SFZ);
            insureIndividualVisitDTO.setMdtrtCertNo(insureIndividualVisitDTO.getAac003());
        }
        map.put("insureIndividualVisitDTO", insureIndividualVisitDTO);
        Map<String, Object> dataMap = insureUnifiedBaseService.checkOneSettle(map).
                getData();
        Map<String, Object> outputMap = MapUtils.get(dataMap, "output");
        List<Map<String, Object>> listMap = MapUtils.get(outputMap, "idetinfo");
        if (ListUtils.isEmpty(listMap)) {
            infoMap.put("flag", false);
        } else {

            /**
             * ?????????????????????????????????????????????????????????????????????????????????
             */
            List<Map<String, Object>> collect = listMap.stream().filter(item -> (StringUtils.isNotEmpty(MapUtils.get(item, "begntime")) && StringUtils.isEmpty(MapUtils.get(item, "endtime"))) || DateUtils.betweenDate(DateUtils.parse(MapUtils.get(item, "begntime"), DateUtils.Y_M_DH_M_S),
                    DateUtils.parse(MapUtils.get(item, "endtime"), DateUtils.Y_M_DH_M_S), DateUtils.getNow()))
                    .collect(Collectors.toList());
            if (ListUtils.isEmpty(collect)) {
                infoMap.put("flag", false);
            } else {
                String psnIdetType = MapUtils.get(collect.get(0), "psn_idet_type");
                infoMap.put("flag", true);
                infoMap.put("psnIdetType", psnIdetType);
            }

        }
        return infoMap;
    }

    /**
     * @param paraMap
     * @Method querySumDeclareInfoPrint
     * @Desrciption ??????????????????????????????
     * @Author liaojiguang
     * @Date 2021/10/21 09:01
     * @Return
     **/
    @Override
    public Map<String, Object> querySumDeclareInfoPrint(Map<String, Object> paraMap) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> resultMap = new HashMap<>();
        String declaraType = MapUtils.get(paraMap, "declaraType");
        switch (declaraType) {
            case Constants.SBLX.CZJM_ZY: // ????????????????????????
                paraMap.put("insutype", Constant.UnifiedPay.XZLX.CZZG);
                paraMap.put("isHospital", Constants.SF.S);
                resultList = insureReversalTradeDAO.querySumDeclareInfosPage(paraMap);
                break;
            case Constants.SBLX.CXJM_ZY: // ????????????????????????
                paraMap.put("insutype", Constant.UnifiedPay.XZLX.CXJM);
                paraMap.put("isHospital", Constants.SF.S);
                resultList = insureReversalTradeDAO.querySumDeclareInfosPage(paraMap);
                break;
            case Constants.SBLX.LX_ZY: // ??????????????????
                paraMap.put("insutype", Constant.UnifiedPay.XZLX.LX);
                paraMap.put("isHospital", Constants.SF.S);
                resultList = insureReversalTradeDAO.querySumDeclareInfosPage(paraMap);
                break;
            case Constants.SBLX.MZ: // ??????
                paraMap.put("isHospital", Constants.SF.F);
                resultList = insureReversalTradeDAO.queryOutptSumDeclareInfosPage(paraMap);
            case Constants.SBLX.YZS: // ?????????
                paraMap.put("insutype", Constant.UnifiedPay.XZLX.CXJM);
                resultList = insureReversalTradeDAO.queryYZSSumDeclareInfosPage(paraMap);
            default:
                break;
        }

        InsureConfigurationDTO insureConfInfo = new InsureConfigurationDTO();
        insureConfInfo.setHospCode(MapUtils.get(paraMap, "hospCode"));
        insureConfInfo.setRegCode(MapUtils.get(paraMap, "insureRegCode"));
        insureConfInfo = insureConfigurationDAO.queryInsureIndividualConfig(insureConfInfo);
        if (insureConfInfo == null) {
            throw new AppException("????????????????????????");
        }
        insureConfInfo.setCrteName(MapUtils.get(paraMap, "crteName"));
        insureConfInfo.setCrteId(MapUtils.get(paraMap, "crteId"));
        insureConfInfo.setStartDate(MapUtils.get(paraMap, "startDate"));
        insureConfInfo.setEndDate(MapUtils.get(paraMap, "endDate"));
        resultMap.put("resultList", resultList);
        resultMap.put("baseInfo", JSONObject.parseObject(JSON.toJSONString(insureConfInfo)));
        return resultMap;
    }

    /**
     * @param paraMap
     * @Method querySumDeclareInfos
     * @Desrciption ??????????????????????????????
     * @Author liuhuiming
     * @Date 2022/3/16 09:01
     * @Return
     **/
    @Override
    public Map<String, Object> querySumDeclareInfos(Map<String, Object> paraMap) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> resultMap = new HashMap<>();
        String declaraType = MapUtils.get(paraMap, "declaraType");
        switch (declaraType) {
            case Constants.SBLX.CZJM_ZY: // ????????????
                paraMap.put("insutype", Constant.UnifiedPay.XZLX.CZZG);
                resultList = insureReversalTradeDAO.querySumDeclareInfos(paraMap);
                break;
            case Constants.SBLX.CXJM_ZY: // ???????????????
                paraMap.put("insutype", Constant.UnifiedPay.XZLX.CXJM);
                paraMap.put("isValidOneSettle",Constants.SF.F);
                resultList = insureReversalTradeDAO.querySumDeclareInfos(paraMap);
                break;
            case Constants.SBLX.LX_ZY: // ??????????????????
                paraMap.put("insutype", Constant.UnifiedPay.XZLX.LX);
                resultList = insureReversalTradeDAO.querySumDeclareInfos(paraMap);
                break;
            case Constants.SBLX.YZS: // ?????????
                paraMap.put("insutype", Constant.UnifiedPay.XZLX.CXJM);
                paraMap.put("isValidOneSettle",Constants.SF.S);
                resultList = insureReversalTradeDAO.queryYZSSumDeclareInfosPage(paraMap);
                break;
            default:
                break;
        }

        InsureConfigurationDTO insureConfInfo = queryInsureIndividualConfig(paraMap);
        resultMap.put("resultList", resultList);
        resultMap.put("baseInfo", JSONObject.parseObject(JSON.toJSONString(insureConfInfo)));
        return resultMap;
    }

    /**
     * @param paraMap
     * @Method queryDeclareInfos
     * @Desrciption ??????????????????????????????
     * @Author liuhuiming
     * @Date 2022/3/16 09:01
     * @Return
     **/
    @Override
    public  Map<String, Object> queryDeclareInfosPrint(Map<String, Object> paraMap) {
        List<Map<String,Object>> resultList = new ArrayList<>();
        Map<String, Object> resultMap = new HashMap<>();

        String declaraType = MapUtils.get(paraMap, "declaraType");
        switch (declaraType) {
            case Constants.SBLX.CZJM_ZY: // ????????????????????????
                paraMap.put("insutype", Constant.UnifiedPay.XZLX.CZZG);
                resultList = insureReversalTradeDAO.queryDeclareInfos(paraMap);
                break;
            case Constants.SBLX.CXJM_ZY: // ????????????
                paraMap.put("insutype", Constant.UnifiedPay.XZLX.CXJM);
                paraMap.put("isValidOneSettle",Constants.SF.F);
                resultList = insureReversalTradeDAO.queryDeclareInfos(paraMap);
                break;
            case Constants.SBLX.LX_ZY: // ??????????????????
                paraMap.put("insutype", Constant.UnifiedPay.XZLX.LX);
                resultList = insureReversalTradeDAO.queryDeclareInfos(paraMap);
                break;
            case Constants.SBLX.YZS: // ????????? queryYZSSumDeclareInfosPage
                paraMap.put("insutype", Constant.UnifiedPay.XZLX.CXJM);
                paraMap.put("isValidOneSettle",Constants.SF.S);
                resultList = insureReversalTradeDAO.queryYZSDeclareInfosPage(paraMap);
            default:
                break;

        }
        InsureConfigurationDTO insureConfInfo = queryInsureIndividualConfig(paraMap);
        if(resultList !=null && resultList.size() > 0){
            //????????????
            int index =1;
            for(Map map:resultList){
                map.put("index",index);
                index++;

                map.put("hospName",insureConfInfo.getHospName());
            }
        }
        resultMap.put("result",resultList);


        resultMap.put("baseInfo", JSONObject.parseObject(JSON.toJSONString(insureConfInfo)));

        return resultMap;
    }
    /**
     * @param paraMap
     * @Method queryDeclareInfos
     * @Desrciption ??????????????????????????????
     * @Author liuhuiming
     * @Date 2022/3/16 09:01
     * @Return
     **/
    @Override
    public Map<String, Object> queryDeclareInfosPrint1(Map paraMap) {
        List<Map<String,Object>> resultList = new ArrayList<>();
        Map<String, Object> resultMap = new HashMap<>();

        String declaraType = MapUtils.get(paraMap, "declaraType");
        switch (declaraType) {
            case Constants.SBLX.CZJM_ZY: // ????????????????????????
                paraMap.put("insutype", Constant.UnifiedPay.XZLX.CZZG);
                resultList = insureReversalTradeDAO.queryDeclareInfos1(paraMap);
                break;
            case Constants.SBLX.CXJM_ZY: // ????????????
                paraMap.put("insutype", Constant.UnifiedPay.XZLX.CXJM);
                paraMap.put("isValidOneSettle",Constants.SF.F);
                resultList = insureReversalTradeDAO.queryDeclareInfos1(paraMap);
                break;
            case Constants.SBLX.LX_ZY: // ??????????????????
                paraMap.put("insutype", Constant.UnifiedPay.XZLX.LX);
                resultList = insureReversalTradeDAO.queryDeclareInfos1(paraMap);
                break;
            case Constants.SBLX.YZS: // ????????? queryYZSSumDeclareInfosPage
                paraMap.put("insutype", Constant.UnifiedPay.XZLX.CXJM);
                paraMap.put("isValidOneSettle",Constants.SF.S);
                resultList = insureReversalTradeDAO.queryYZSDeclareInfosPage(paraMap);
            default:
                break;

        }
        InsureConfigurationDTO insureConfInfo = queryInsureIndividualConfig(paraMap);
        if(resultList !=null && resultList.size() > 0){
            //????????????
            int index =1;
            for(Map map:resultList){
                map.put("index",index);
                index++;

                map.put("hospName",insureConfInfo.getHospName());
            }
        }
        resultMap.put("result",resultList);


        resultMap.put("baseInfo", JSONObject.parseObject(JSON.toJSONString(insureConfInfo)));

        return resultMap;
    }

    /**
     * @Method handlerFeeFund
     * @Desrciption ????????????????????????????????????
     * @Param
     * @Author fuhui
     * @Date 2021/10/23 15:20
     * @Return
     **/
    private BigDecimal handlerFeeFund(List<Map<String, Object>> setldetail) {
        BigDecimal bigDecimal = new BigDecimal(0.00);
        if (!ListUtils.isEmpty(setldetail)) {
            for (Map<String, Object> item : setldetail) {
                String fundPayType = MapUtils.get(item, "fund_pay_type"); //  ??????????????????
                BigDecimal fundPayamt = MapUtils.get(item, "fund_payamt"); //  ??????????????????
                if ("340100".equals(fundPayType)) { // ??????????????????
                    bigDecimal = BigDecimalUtils.add(bigDecimal, fundPayamt);
                }
            }
        }
        return bigDecimal;
    }

    /**
     * @Method handlerSpecialPastFee
     * @Desrciption ??????????????????????????? ??????????????????
     * @Param
     * @Author fuhui
     * @Date 2021/10/19 19:44
     * @Return
     **/
    private Map<String, Object> handlerSpecialPastFee(List<Map<String, Object>> sumInfoMapList) {
        Map<String, Object> partThreeMap = new HashMap<>();
        BigDecimal totalCount = new BigDecimal(0.00); // ??????????????????
        BigDecimal s1 = new BigDecimal(0.00);  // ???????????????
        BigDecimal s2 = new BigDecimal(0.00); // ??????????????????
        BigDecimal s3 = new BigDecimal(0.00); // ??????????????????
        DecimalFormat df1 = new DecimalFormat("0.00");
        String cumTypeCode = "";
        BigDecimal cum = new BigDecimal(0.00);
        for (Map<String, Object> item : sumInfoMapList) {
            cumTypeCode = MapUtils.get(item, "cum_type_code"); // ??????????????????
            cum = BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "cum") == null ? "" : MapUtils.get(item, "cum").toString())));
            // ????????????
            if ("C0000".equals(cumTypeCode)) {
                totalCount = BigDecimalUtils.add(totalCount, cum);
            }
            //???????????????
            if ("S0000".equals(cumTypeCode)) {
                s1 = BigDecimalUtils.add(s1, cum);
            }
            // ??????????????????
            if ("D340101".equals(cumTypeCode)) {
                s2 = BigDecimalUtils.add(s2, cum);
            }
            //??????????????????
            if ("D310201".equals(cumTypeCode)) {
                s3 = BigDecimalUtils.add(s3, cum);
            }
        }
        partThreeMap.put("totalCount", totalCount);
        partThreeMap.put("s1", s1);
        partThreeMap.put("s2", s2);
        partThreeMap.put("s3", s3);
        return partThreeMap;
    }


    /**
     * @Method handlerInsureSettleFee
     * @Desrciption ????????????????????? ??????  ????????????
     * @Param
     * @Author fuhui
     * @Date 2021/10/19 20:09
     * @Return
     **/
    private Map<String, Object> handlerInsureSettleFee(Map<String, Object> setlInfoMap,
                                                       boolean isRemote,boolean snRemote,
                                                       InsureIndividualSettleDTO individualSettleDTO, List<Map<String, Object>> setldetail, boolean jxSettle,boolean gsSettle) {
        Map<String, Object> partFourMap = new HashMap<>();
        BigDecimal hospPrice = new BigDecimal(0.00);
        DecimalFormat df1 = new DecimalFormat("0.00");
        BigDecimal s1 = BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(setlInfoMap, "medfee_sumamt")))));
        BigDecimal s2 = BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(setlInfoMap, "hifp_pay")))));
        BigDecimal s3 = BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(setlInfoMap, "cvlserv_pay")))));
        BigDecimal s4 = BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(setlInfoMap, "hifob_pay")))));
        BigDecimal s5 = BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(setlInfoMap, "hifmi_pay")))));
        BigDecimal s6 = BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(setlInfoMap, "hifob_pay")))));
        BigDecimal s7 = BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(setlInfoMap, "acct_pay")))));
        BigDecimal s8 = BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(setlInfoMap, "act_pay_dedc")))));
        BigDecimal s9 = BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(setlInfoMap, "cash_payamt")))));
        BigDecimal s10 = BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(setlInfoMap, "balc")))));
        BigDecimal s12 = BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(setlInfoMap, "maf_pay")))));
        // ????????????
        BigDecimal s11 = BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(setlInfoMap, "oth_pay")))));
        //???????????????
        BigDecimal fulamtOwnpayAmt = BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(setlInfoMap, "fulamt_ownpay_amt")))));
        //?????????????????????
        BigDecimal overlmtSelfpay = BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(setlInfoMap, "overlmt_selfpay")))));
        //??????????????????
        BigDecimal preselfpayAmt = BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(DataTypeUtils.dataToNumString(MapUtils.get(setlInfoMap, "preselfpay_amt")))));
        BigDecimal complianceFee = BigDecimalUtils.subtractMany(s1,s8,fulamtOwnpayAmt,overlmtSelfpay,preselfpayAmt);
        partFourMap.put("medfeeSumamt", s1); // ?????????????????????
        partFourMap.put("hifpPay", s2); // ??????????????????
        partFourMap.put("cvlservPay", s3); // ?????????????????????
        partFourMap.put("hifobPay", s4); // ??????????????????
        partFourMap.put("hifmiPay", s5); // ??????????????????
        partFourMap.put("hifobPay", s6); // ??????????????????
        partFourMap.put("acctPay", s7); // ????????????????????????
        partFourMap.put("actPayDedc", s8); // ?????????????????????
        partFourMap.put("psnCashPay", s9); // ??????????????????
        partFourMap.put("mafPay", s12); // ????????????
        partFourMap.put("othPay",s11); // ????????????
        partFourMap.put("fulamtOwnpayAmt",fulamtOwnpayAmt); // ???????????????
        partFourMap.put("overlmtSelfpay",overlmtSelfpay); // ?????????????????????
        partFourMap.put("preselfpayAmt",preselfpayAmt); // ??????????????????
        partFourMap.put("complianceFee",complianceFee); // ????????????
        partFourMap.put("lixiuPrice",new BigDecimal(0).setScale(2)); // ??????????????????
        partFourMap.put("hifobPayAndHifmiPay", BigDecimalUtils.add(s4, s5)); // ??????????????????

        if (individualSettleDTO != null) {
            hospPrice = individualSettleDTO.getHospPrice();
            partFourMap.put("hospPrice", individualSettleDTO.getHospPrice()); // ????????????
        } else {
            partFourMap.put("hospPrice", new BigDecimal(0.00)); // ????????????
        }
        partFourMap.put("balc", s10); // ??????????????????
        if (isRemote || jxSettle || snRemote || gsSettle) {
            if (!ListUtils.isEmpty(setldetail)) {
                BigDecimal fundSumAmt = new BigDecimal(0.00);
                String fundPayamt = ""; // ??????????????????
                String fundPayType = ""; // ????????????????????????
                BigDecimal fundPayamtPrice = new BigDecimal(0.00);
                for (Map<String, Object> item : setldetail) {
                    fundPayamt = DataTypeUtils.dataToNumString(MapUtils.get(item, "fund_payamt"));
                    fundPayamtPrice = BigDecimalUtils.convert(fundPayamt);
                    fundPayType = MapUtils.get(item, "fund_pay_type");
                    fundSumAmt = BigDecimalUtils.add(fundSumAmt, fundPayamtPrice);
                    // ?????????????????????????????????
                    if ("610102".equals(fundPayType)) {
                        partFourMap.put("seriousPrice", fundPayamtPrice);
                    }
                    // ????????????????????????
                    if ("340100".equals(fundPayType)) {
                        partFourMap.put("lixiuPrice", fundPayamtPrice);
                    }

                }
                partFourMap.put("otherPayPrice", BigDecimalUtils.add(s11, MapUtils.get(partFourMap, "seriousPrice")));
                partFourMap.put("fundSumAmt", fundSumAmt); // ????????????
                partFourMap.put("fundSumAmtCN", numberToCN.number2CNMontrayUnit(fundSumAmt)); // ????????????
            }
        }
        if (isRemote || snRemote || gsSettle) {
            partFourMap.put("medfeeSumamtCN", numberToCN.number2CNMontrayUnit(s1)); // ?????????????????????
            partFourMap.put("hifpPayCN", numberToCN.number2CNMontrayUnit(s2)); // ??????????????????
            partFourMap.put("cvlservPayCN", numberToCN.number2CNMontrayUnit(s3)); // ?????????????????????
            partFourMap.put("hifobPayCN", numberToCN.number2CNMontrayUnit(s4)); // ??????????????????
            partFourMap.put("hifmipPayCN", numberToCN.number2CNMontrayUnit(s5)); // ??????????????????
            partFourMap.put("hifobPayCN", numberToCN.number2CNMontrayUnit(s6)); // ??????????????????
            partFourMap.put("acctPayCN", numberToCN.number2CNMontrayUnit(s7)); // ????????????????????????
            partFourMap.put("actPayDedcCN", numberToCN.number2CNMontrayUnit(s8)); // ?????????????????????
            partFourMap.put("hospPriceCN", numberToCN.number2CNMontrayUnit(hospPrice)); // ??????????????????
            partFourMap.put("psnCashPayCN", numberToCN.number2CNMontrayUnit(s9)); // ??????????????????
            partFourMap.put("balcCN", numberToCN.number2CNMontrayUnit(s10)); // ??????????????????
            partFourMap.put("othPayCN", numberToCN.number2CNMontrayUnit(s11)); // ????????????
            partFourMap.put("mafPayCN", numberToCN.number2CNMontrayUnit(s12)); // ????????????
            partFourMap.put("complianceFeeCN", numberToCN.number2CNMontrayUnit(complianceFee)); // ????????????
        }
        if(gsSettle){
            // ??????/???????????????
            if(BigDecimalUtils.compareTo(s6,new BigDecimal(0.00)) > 0){
                partFourMap.put("hifobPayOrHifmiPay", s6);
                partFourMap.put("hifobPayOrHifmiPayCN", numberToCN.number2CNMontrayUnit(s6));
            }else if(BigDecimalUtils.compareTo(s5,new BigDecimal(0.00)) > 0){
                partFourMap.put("hifobPayOrHifmiPay", s5);
                partFourMap.put("hifobPayOrHifmiPayCN", numberToCN.number2CNMontrayUnit(s5));
            }else {
                partFourMap.put("hifobPayOrHifmiPay",new BigDecimal(0.00) );
                partFourMap.put("hifobPayOrHifmiPayCN", numberToCN.number2CNMontrayUnit(new BigDecimal(0.00)));
            }
        }
        return partFourMap;
    }

    /**
     * @Method handlerPastFee
     * @Desrciption ????????????????????????
     * @Param
     * @Author fuhui
     * @Date 2021/10/15 16:20
     * @Return
     **/
    private Map<String, Object> handlerPastFee(List<Map<String, Object>> sumInfoMapList, String isHospital, boolean oneSettle, boolean specialOneSettle, boolean gsSettle) {
        Map<String, Object> map = new HashMap<>();
        BigDecimal inptCount = new BigDecimal(0.00); // ??????????????????????????????
        BigDecimal outptCount = new BigDecimal(0.00); //??????????????????????????????
        BigDecimal s1 = new BigDecimal(0.00);  // ?????????????????????????????????
        BigDecimal s2 = new BigDecimal(0.00); // ???????????????
        BigDecimal s3 = new BigDecimal(0.00); // ???????????????
        BigDecimal s4 = new BigDecimal(0.00);  // ????????????
        BigDecimal s5 = new BigDecimal(0.00); //????????????
        BigDecimal s6 = new BigDecimal(0.00); // ????????????
        BigDecimal s7 = new BigDecimal(0.00);  // ??????????????????
        BigDecimal s8 = new BigDecimal(0.00); // ????????????????????????
        BigDecimal s9 = new BigDecimal(0.00); // ??????????????????
        BigDecimal s10 = new BigDecimal(0.00); // ??????????????????
        BigDecimal s11 = new BigDecimal(0.00); // ?????????????????????
        BigDecimal s12 = new BigDecimal(0.00); // ??????????????????
        DecimalFormat df1 = new DecimalFormat("0.00");
        String cumTypeCode = "";
        BigDecimal cum = new BigDecimal(0.00);

        if(gsSettle){
            for (Map<String, Object> item : sumInfoMapList) {
                cumTypeCode = MapUtils.get(item, "cum_type_code"); // ??????????????????
                cum = BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "cum") == null ? "" : MapUtils.get(item, "cum").toString())));
                if("C0000".equals(cumTypeCode)){
                    inptCount = BigDecimalUtils.add(inptCount, cum); // ??????????????????
                }
                if("S0000".equals(cumTypeCode)){
                    s1 = BigDecimalUtils.add(s1, cum); // ?????????????????????????????????
                }
                if("D310101".equals(cumTypeCode)||
                        "D390101".equals(cumTypeCode)
                        ){
                    s4 = BigDecimalUtils.add(s4, cum); // ????????????
                }
                if ("D330101".equals(cumTypeCode)) {
                    s7 = BigDecimalUtils.add(s7, cum); // ??????????????????
                }
                if ("D390201".equals(cumTypeCode)) {
                    s9 = BigDecimalUtils.add(s9, cum); // ??????????????????
                }
                if ("D320101".equals(cumTypeCode)) {
                    s11 = BigDecimalUtils.add(s11, cum); // ?????????????????????
                }
                if ("D310201".equals(cumTypeCode)) {
                    s12 = BigDecimalUtils.add(s12, cum); // ??????????????????
                }
            }
            map.put("inptCount", inptCount);
            map.put("s1", s1);
            map.put("s4", s4);
            map.put("s7", s7);
            map.put("s9", s9);
            map.put("s11", s11);
            map.put("s12", s12);
        }

        else if (Constants.SF.S.equals(isHospital) && (!oneSettle && !specialOneSettle)) {
            for (Map<String, Object> item : sumInfoMapList) {
                cumTypeCode = MapUtils.get(item, "cum_type_code"); // ??????????????????
                cum = BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "cum") == null ? "" : MapUtils.get(item, "cum").toString())));
                if ("C0000_BIZ2101".equals(cumTypeCode) ||
                        "C0000_BIZ2102".equals(cumTypeCode) ||
                        "C0000_BIZ22".equals(cumTypeCode) ||
                        "C0000_BIZ2106".equals(cumTypeCode) ||
                        "C0000_BIZ2107".equals(cumTypeCode) ||
                        "C0000_BIZ52".equals(cumTypeCode)) {
                    inptCount = BigDecimalUtils.add(inptCount, cum);
                }

                if ("S0000_BIZ2101".equals(cumTypeCode) ||
                        "S0000_BIZ2102".equals(cumTypeCode) ||
                        "S0000_BIZ22".equals(cumTypeCode) ||
                        "S0000_BIZ2106".equals(cumTypeCode) ||
                        "S0000_BIZ2107".equals(cumTypeCode) ||
                        "S0000_BIZ52".equals(cumTypeCode)) {
                    s1 = BigDecimalUtils.add(s1, cum);
                }
                if ("F0000_BIZ2101".equals(cumTypeCode) ||
                        "F0000_BIZ2102".equals(cumTypeCode) ||
                        "F0000_BIZ22".equals(cumTypeCode) ||
                        "F0000_BIZ2106".equals(cumTypeCode) ||
                        "F0000_BIZ2107".equals(cumTypeCode) ||
                        "F0000_BIZ52".equals(cumTypeCode)) {
                    s2 = BigDecimalUtils.add(s2, cum);
                }
                if ("Q0000_BIZ2101".equals(cumTypeCode) ||
                        "Q0000_BIZ2102".equals(cumTypeCode) ||
                        "Q0000_BIZ22".equals(cumTypeCode) ||
                        "Q0000_BIZ2106".equals(cumTypeCode) ||
                        "Q0000_BIZ2107".equals(cumTypeCode) ||
                        "Q0000_BIZ52".equals(cumTypeCode)) {
                    s3 = BigDecimalUtils.add(s3, cum);
                }
                if ("D310101_BIZ2101".equals(cumTypeCode) ||
                        "D310101_BIZ2102".equals(cumTypeCode) ||
                        "D310101_BIZ22".equals(cumTypeCode) ||
                        "D310101_BIZ2106".equals(cumTypeCode) ||
                        "D310101_BIZ2107".equals(cumTypeCode) ||
                        "D310101_BIZ52".equals(cumTypeCode) ||
                        "D390101_BIZ2101".equals(cumTypeCode) ||
                        "D390101_BIZ2102".equals(cumTypeCode) ||
                        "D310101_BIZ22".equals(cumTypeCode) ||
                        "D390101_BIZ22".equals(cumTypeCode) ||
                        "D390101_BIZ2106".equals(cumTypeCode) ||
                        "D390101_BIZ2107".equals(cumTypeCode) ||
                        "D390101_BIZ52".equals(cumTypeCode)) {
                    s4 = BigDecimalUtils.add(s4, cum);
                }
                if ("Z0000_BIZ2101_LAB101".equals(cumTypeCode) ||
                        "Z0000_BIZ2102_LAB101".equals(cumTypeCode) ||
                        "Z0000_BIZ22_LAB101".equals(cumTypeCode) ||
                        "Z0000_BIZ2106_LAB101".equals(cumTypeCode) ||
                        "Z0000_BIZ2107_LAB101".equals(cumTypeCode) ||
                        "Z0000_BIZ52_LAB101".equals(cumTypeCode) ||
                        "Z0000_BIZ2101_LAB103".equals(cumTypeCode) ||
                        "Z0000_BIZ22_LAB103".equals(cumTypeCode) ||
                        "Z0000_BIZ2106_LAB103".equals(cumTypeCode) ||
                        "Z0000_BIZ52_LAB103".equals(cumTypeCode)) {
                    s5 = BigDecimalUtils.add(s5, cum);
                }
                if ("Z0000_BIZ2101_LAB102".equals(cumTypeCode) ||
                        "Z0000_BIZ2102_LAB102".equals(cumTypeCode) ||
                        "Z0000_BIZ22_LAB102".equals(cumTypeCode) ||
                        "Z0000_BIZ2106_LAB102".equals(cumTypeCode) ||
                        "Z0000_BIZ2107_LAB102".equals(cumTypeCode) ||
                        "Z0000_BIZ52_LAB102".equals(cumTypeCode)) {
                    s6 = BigDecimalUtils.add(s6, cum);
                }
                if ("D330101".equals(cumTypeCode)) {
                    s7 = BigDecimalUtils.add(s7, cum);
                }
                if ("TS390201".equals(cumTypeCode)) {
                    s8 = BigDecimalUtils.add(s8, cum);
                }
                if ("D390201".equals(cumTypeCode)) {
                    s9 = BigDecimalUtils.add(s9, cum);
                }
                if ("D610101".equals(cumTypeCode)) {
                    s10 = BigDecimalUtils.add(s10, cum);
                }
            }
            map.put("inptCount", inptCount);
            map.put("s1", s1);
            map.put("s2", s2);
            map.put("s3", s3);
            map.put("s4", s4);
            map.put("s5", s5);
            map.put("s6", s6);
            map.put("s7", s7);
            map.put("s8", s8);
            map.put("s9", s9);
            map.put("s10", s10);
        }
        // ???????????????????????????
        else if (Constants.SF.F.equals(isHospital) || ((!oneSettle || !specialOneSettle) && Constants.SF.F.equals(isHospital))) {
            for (Map<String, Object> item : sumInfoMapList) {
                cumTypeCode = MapUtils.get(item, "cum_type_code"); // ??????????????????
                cum = BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "cum").toString())));
                if ("C0000_BIZ11".equals(cumTypeCode) || "C0000_BIZ13".equals(cumTypeCode) ||
                        "C0000_BIZ51".equals(cumTypeCode) || "C0000_BIZ9901".equals(cumTypeCode) ||
                        "C0000_BIZ14".equals(cumTypeCode)) {
                    outptCount = BigDecimalUtils.add(outptCount, cum);
                }
                // F0000_BIZ9901    ????????????????????????????????????????????????????????????
                // F0000_BIZ51      ??????????????????????????????????????????????????????????????????
                // F0000_BIZ11      ????????????????????????????????????????????????????????????
                // F0000_BIZ13      ??????????????????????????????????????????????????????
                // F0000_BIZ14      ???????????????????????????????????????????????????????????????
                if ("F0000_BIZ9901".equals(cumTypeCode) || "F0000_BIZ51".equals(cumTypeCode) ||
                        "F0000_BIZ11".equals(cumTypeCode) || "F0000_BIZ13".equals(cumTypeCode) ||
                        "F0000_BIZ14".equals(cumTypeCode)) {
                    s1 = BigDecimalUtils.add(s1, cum);
                }
                // D390102_BIZ11  ??????????????????????????????????????????????????????????????????????????????
                // D390101_BIZ14  ?????????????????????????????????????????????????????????????????????????????????
                // D310101_BIZ13  ?????????????????????????????????????????????????????????????????????????????????
                // D310101_BIZ14 ???????????????????????????????????????????????????????????????????????????
                // D390101_BIZ13 ????????????????????????????????????????????????????????????????????????
                // D390102_BIZ9901 ????????????????????????????????????????????????????????????????????????
                if ("D390102_BIZ11".equals(cumTypeCode) || "D390101_BIZ14".equals(cumTypeCode) ||
                        "D310101_BIZ13".equals(cumTypeCode) || "D310101_BIZ14".equals(cumTypeCode) ||
                        "D390101_BIZ13".equals(cumTypeCode) || "D390102_BIZ9901".equals(cumTypeCode)) {
                    s2 = BigDecimalUtils.add(s2, cum);
                }
                // D330101  ??????????????????
                if ("D330101_BIZ13".equals(cumTypeCode) || "D330101_BIZ14".equals(cumTypeCode)) {
                    s3 = BigDecimalUtils.add(s3, cum);
                }
                // D320101 ?????????????????????
                if ("D320101".equals(cumTypeCode)) {
                    s4 = BigDecimalUtils.add(s4, cum);
                }
                // D390201 ??????????????????
                if ("D390201".equals(cumTypeCode)) {
                    s5 = BigDecimalUtils.add(s5, cum);
                }
                // D310201 ??????????????????
                if ("D310201".equals(cumTypeCode)) {
                    s6 = BigDecimalUtils.add(s6, cum);
                }
                // D610101  ??????????????????
                if ("D610101".equals(cumTypeCode)) {
                    s7 = BigDecimalUtils.add(s7, cum);
                }
            }
            map.put("outptCount", outptCount); // ????????????
            map.put("s1", s1); // ??????????????????
            map.put("s2", s2);  // ??????????????????
            map.put("s3", s3);  // ??????????????????
            map.put("s4", s4); //  ?????????????????????
            map.put("s5", s5);  // ??????????????????
            map.put("s6", s6);  // ??????????????????
            map.put("s7", s7); //  ??????????????????
        } else {
            // ??????????????????????????????????????????
            String str = "D310101_BIZ2101+D310101_BIZ2102+D310101_BIZ22+D310101_BIZ2106+D310101_BIZ2107+D310101_BIZ52+D390101_BIZ2101+D390101_BIZ2102+D390101_BIZ22+D390101_BIZ2106+D390101_BIZ2107+D390101_BIZ52";
            String[] split = str.split("\\+");
            List<String> strings = Arrays.asList(split);
            for (Map<String, Object> item : sumInfoMapList) {
                cumTypeCode = MapUtils.get(item, "cum_type_code"); // ??????????????????
                cum = BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "cum") == null ? "" : MapUtils.get(item, "cum").toString())));
                if (strings.contains(cumTypeCode)) {
                    s1 = BigDecimalUtils.add(s1, cum); // ??????????????????????????????
                }
                if ("F0000_BIZ2101".equals(cumTypeCode) ||
                        "F0000_BIZ2102".equals(cumTypeCode) ||
                        "F0000_BIZ22".equals(cumTypeCode) ||
                        "F0000_BIZ2106".equals(cumTypeCode) ||
                        "F0000_BIZ2107".equals(cumTypeCode) ||
                        "F0000_BIZ52".equals(cumTypeCode)) {
                    s2 = BigDecimalUtils.add(s2, cum); // ?????????????????????
                }
                if ("D390201".equals(cumTypeCode)) {
                    s3 = BigDecimalUtils.add(s3, cum);  // ??????????????????????????????
                }
                if ("D620101".equals(cumTypeCode)) {
                    s4 = BigDecimalUtils.add(s4, cum); // ?????????????????????????????????
                }
                if ("D610101".equals(cumTypeCode)) {
                    s5 = BigDecimalUtils.add(s5, cum); // ????????????????????????
                }
                if ("D630101".equals(cumTypeCode)) { // ????????????????????????
                    s6 = BigDecimalUtils.add(s6, cum);
                }
                if ("D640101".equals(cumTypeCode)) {
                    s7 = BigDecimalUtils.add(s7, cum); // ??????????????????????????????
                }
                if ("D999996".equals(cumTypeCode)) {  // ????????????????????????
                    s8 = BigDecimalUtils.add(s8, cum);
                }
                if ("TQ610101_BIZ2101".equals(cumTypeCode) ||
                        "TS390201_BIZ2101".equals(cumTypeCode) ||
                        "TS610101_BIZ2101".equals(cumTypeCode) ||
                        "TQ390201_BIZ2102".equals(cumTypeCode) ||
                        "TS390201_BIZ2102".equals(cumTypeCode) ||
                        "TQ610101_BIZ2102".equals(cumTypeCode) ||
                        "TS610101_BIZ2102".equals(cumTypeCode) ||
                        "TQ610101_BIZ22".equals(cumTypeCode) ||
                        "TQ390201_BIZ22".equals(cumTypeCode) ||
                        "TS390201_BIZ22".equals(cumTypeCode) ||
                        "TS610101_BIZ22".equals(cumTypeCode)) {
                    s9 = BigDecimalUtils.add(s9, cum); // ???????????????????????????
                }
                Set<BigDecimal> bigDecimalSet = new HashSet<>();
                BigDecimal sumBxFee = new BigDecimal(0.00);
                bigDecimalSet.add(s1);
                bigDecimalSet.add(s3);
                bigDecimalSet.add(s4);
                bigDecimalSet.add(s5);
                bigDecimalSet.add(s6);
                bigDecimalSet.add(s7);
                bigDecimalSet.add(s8);
                for (BigDecimal s : bigDecimalSet) {
                    sumBxFee = BigDecimalUtils.add(sumBxFee, s);
                }
                map.put("s1", s1);
                map.put("s2", s2);
                map.put("s3", s3);
                map.put("s4", s4);
                map.put("s5", s5);
                map.put("s6", s6);
                map.put("s7", s7);
                map.put("s8", s8);
                map.put("s9", s9);
                map.put("sumBxFeeNumber", sumBxFee);
                map.put("sumBxFee", numberToCN.number2CNMontrayUnit(sumBxFee));
            }
        }
        return map;
    }

    /**
     * @Method handlerInptSettleParam
     * @Desrciption ????????????????????? ??????
     * @Param
     * @Author fuhui
     * @Date 2021/10/15 15:23
     * @Return
     **/
    private Map<String, Object> handlerInptSettleParam(InsureIndividualVisitDTO insureIndividualVisitDTO,
                                                       Map<String, Object> setlInfoMap, boolean oneSettle,
                                                       String psnIdetType, boolean specialOneSettle, boolean jsSettle, boolean gsSettle) {
        Map<String, Object> map = new HashMap<>();
        map.put("fixmedinsName", insureIndividualVisitDTO.getFixmedinsName()); // ??????????????????
        map.put("hospLv", insureIndividualVisitDTO.getHospLv()); // ????????????
        map.put("mdtrtId", insureIndividualVisitDTO.getMedicalRegNo()); // ?????????
        map.put("psnName", MapUtils.get(setlInfoMap, "psn_name")); // ????????????
        map.put("gend", MapUtils.get(setlInfoMap, "gend")); // ??????
        Object ageObj = MapUtils.get(setlInfoMap, "age");
        if (ageObj == null) {
            map.put("age", insureIndividualVisitDTO.getAge()); // ??????
        } else if (ageObj instanceof String && StringUtils.isEmpty((String) ageObj)) {
            map.put("age", insureIndividualVisitDTO.getAge()); // ??????
        } else {
            map.put("age", new BigDecimal(MapUtils.get(setlInfoMap, "age").toString()).intValue()  ); // ??????
        }
        map.put("psnNo", MapUtils.get(setlInfoMap, "psn_no")); // ????????????
        if(MapUtils.get(setlInfoMap, "psn_no") == null || "".equals(MapUtils.get(setlInfoMap, "psn_no"))){
            map.put("psnNo", insureIndividualVisitDTO.getAac001());
        }
        if (StringUtils.isEmpty(MapUtils.get(setlInfoMap, "psn_idet_type"))) {
            map.put("psnIdetType", "???"); //????????????
        } else {
            map.put("psnIdetType", MapUtils.get(setlInfoMap, "psn_idet_type")); //????????????
        }
        if (jsSettle) {
            Map<String, Object> visitMap = new HashMap<>();
            visitMap.put("hospCode", insureIndividualVisitDTO.getHospCode());
            visitMap.put("visitId", insureIndividualVisitDTO.getVisitId());

            visitMap.put("insuplcAdmdvs", insureIndividualVisitDTO.getInsuplcAdmdvs());
            String insuplcAdmdvsName = insureIndividualVisitDAO.selectInsuplcName(visitMap);
            map.put("insuplcAdmdvsName", insuplcAdmdvsName);

            String begndate = MapUtils.get(setlInfoMap, "begndate");
            map.put("begndate", begndate);
            String enddate = MapUtils.get(setlInfoMap, "enddate");
            map.put("enddate", enddate);
            visitMap.put("begntime", begndate);
            visitMap.put("endtime", enddate);
            Map<String, Object> data = insureUnifiedBaseService.queryVisitInfo(visitMap).getData();
            List<Map<String, Object>> resultDataMap = MapUtils.get(data, "resultDataMap");
            Map<String, Object> objectMap = new HashMap<>();
            if (!ListUtils.isEmpty(resultDataMap)) {
                objectMap = resultDataMap.get(0);
            }
            String mafPsnFlag = MapUtils.get(objectMap, "maf_psn_flag", "");
            if (StringUtils.isEmpty(mafPsnFlag)) {
                map.put("mafPsnFlag", "???"); // ??????????????????
            } else {
                map.put("mafPsnFlag", "???"); // ??????????????????
            }
        }
        map.put("setlId", MapUtils.get(setlInfoMap, "setl_id")); // ??????id
        if (StringUtils.isNotEmpty(insureIndividualVisitDTO.getBka008())) {
            map.put("empName", insureIndividualVisitDTO.getBka008()); // ????????????
        } else {
            map.put("empName", "???"); // ????????????
        }
        String bka006Name = insureIndividualVisitDTO.getBka006Name();
        String visitIcdName = insureIndividualVisitDTO.getVisitIcdName();
        if (StringUtils.isEmpty(bka006Name) || "null".equals(bka006Name)) {
            map.put("diseName", visitIcdName); // ??????
        } else {
            map.put("diseName", bka006Name); // ??????
        }
        map.put("psnType", MapUtils.get(setlInfoMap, "psn_type")); // ????????????
        String phone = "";

        if (Constants.SF.S.equals(insureIndividualVisitDTO.getIsHospital())) {
            phone = insureIndividualVisitDTO.getInptPhone();
        } else {
            phone = insureIndividualVisitDTO.getOutPhone();
        }
        if (jsSettle && StringUtils.isNotEmpty(phone)) {
            map.put("tel", phone.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2")); // ????????????
        } else {
            map.put("tel", insureIndividualVisitDTO.getInptPhone()); // ????????????
        }
        map.put("cvlservFlag", MapUtils.get(setlInfoMap, "cvlserv_flag")); // ???????????????
        map.put("iptNo", insureIndividualVisitDTO.getVisitNo()); // ?????????
        map.put("admDeptName", insureIndividualVisitDTO.getVisitDrptName()); // ??????
        map.put("admBed", insureIndividualVisitDTO.getVisitBerth()); // ?????????
        map.put("begntime", insureIndividualVisitDTO.getInTime()); // ????????????
        map.put("endtime", insureIndividualVisitDTO.getOutTime()); // ????????????
        map.put("endtimeBegntime", insureIndividualVisitDTO.getHospitalDay()); // ????????????
        String certno = MapUtils.get(setlInfoMap, "certno");
        if (!jsSettle && StringUtils.isNotEmpty(certno)) {
            map.put("certno", certno); // ????????????
        } else {
            map.put("certno", certno.replaceAll("(\\w{6})\\w*(\\w{3})", "$1*********$2")); // ????????????
        }
        map.put("mdtrtCertType", MapUtils.get(setlInfoMap, "psn_cert_type")); // ????????????
        map.put("dscgMaindiagName", insureIndividualVisitDTO.getOutDiseaseName()); // ????????????
        map.put("medType", insureIndividualVisitDTO.getAka130()); // ????????????
        if (jsSettle) {
            Object setlTime = MapUtils.get(setlInfoMap, "setl_time");
            if (setlTime != null && StringUtils.isNotEmpty((String) setlTime)) {
                map.put("setlTime", DateUtils.parse(MapUtils.get(setlInfoMap, "setl_time"), DateUtils.Y_M_D)); // ????????????
            }
        } else {
            map.put("setlTime", MapUtils.get(setlInfoMap, "setl_time")); // ????????????
        }
        map.put("chfpdrName", insureIndividualVisitDTO.getZzDoctorName()); // ????????????
        map.put("balc", MapUtils.get(setlInfoMap, "balc")); // ??????????????????
        map.put("address", insureIndividualVisitDTO.getAddress()); // ??????
        map.put("setlTime", MapUtils.get(setlInfoMap, "setl_time")); // ????????????
        map.put("brdy", MapUtils.get(setlInfoMap, "brdy")); // ????????????
        map.put("aae140", insureIndividualVisitDTO.getAae140()); // ??????
        map.put("totalAdvance", insureIndividualVisitDTO.getTotalAdvance()); // ???????????????

        if (insureIndividualVisitDTO.getTotalAdvance() == null || BigDecimalUtils.isZero(insureIndividualVisitDTO.getTotalAdvance())) {
            map.put("totalAdvanceCN", "??????"); // ???????????????
            insureIndividualVisitDTO.setTotalAdvance(new BigDecimal(0.00)); // ???????????????
        } else {
            map.put("totalAdvanceCN", numberToCN.number2CNMontrayUnit(insureIndividualVisitDTO.getTotalAdvance())); // ???????????????
        }
        map.put("inDiseaseName", insureIndividualVisitDTO.getInDiseaseName()); // ??????????????????
        map.put("outDiseaseName", insureIndividualVisitDTO.getOutDiseaseName()); // ??????????????????
        map.put("visitNationCode", insureIndividualVisitDTO.getVisitNationCode()); // ??????
        if (StringUtils.isEmpty(insureIndividualVisitDTO.getBka006Name()) || "null".equals(insureIndividualVisitDTO.getBka006Name())) {
            map.put("bka006Name", insureIndividualVisitDTO.getAka130Name()); // ????????????
        } else {
            map.put("bka006Name", insureIndividualVisitDTO.getBka006Name()); // ????????????
        }
        /**
         * ????????????????????????????????????
         */
        if (oneSettle || specialOneSettle) {
            String diseaseName = insureIndividualVisitDAO.selectOutDiagnose(insureIndividualVisitDTO);
            map.put("outDiseaseName", diseaseName); // ????????????
            map.put("psnIdetType", psnIdetType); // ????????????
        }
        //???????????????????????????????????????
        if(gsSettle){
           if(insureIndividualVisitDTO.getInTime() == null || insureIndividualVisitDTO.getOutTime() == null){
               map.put("begntime", insureIndividualVisitDTO.getVisitTime()); // ????????????
               map.put("endtime", insureIndividualVisitDTO.getVisitTime()); // ????????????
           }
        }
        return map;
    }

    /**
     * @Method handlerInsureIndividualCost
     * @Desrciption ?????????????????????????????? ??????????????????
     * @Param feeDetailMapList?????????????????????
     *
     * @Author fuhui
     * @Date 2021/10/14 11:44
     * @Return
     **/
    /**
     * @Method handlerInsureIndividualCost
     * @Desrciption ?????????????????????????????? ??????????????????
     * @Param feeDetailMapList?????????????????????
     * @Author fuhui
     * @Date 2021/10/14 11:44
     * @Return
     **/
    private List<Map<String, Object>> handlerInsureIndividualCost(List<Map<String, Object>> feeDetailMapList) {

        List<Map<String, Object>> groupListMap = new ArrayList<>();
        if (!ListUtils.isEmpty(feeDetailMapList)) {
            feeDetailMapList = feeDetailMapList.stream().filter(item -> StringUtils.isNotEmpty(MapUtils.get(item, "med_chrgitm_type"))).collect(Collectors.toList());
            Map<String, List<Map<String, Object>>> groupMap = feeDetailMapList.stream().
                    collect(Collectors.groupingBy(item -> MapUtils.get(item, "med_chrgitm_type")));
            Map<String, Object> pMap = null;
            for (String key : groupMap.keySet()) {
                BigDecimal sumDetItemFeeSumamt = new BigDecimal(0.00); // ?????????
                BigDecimal inscpScpAmt = new BigDecimal(0.00); // ????????????????????????
                BigDecimal preselfpayAmt = new BigDecimal(0.00); // ??????????????????
                BigDecimal DClassFee = new BigDecimal(0.00); // ?????????????????????
                BigDecimal AClassFee = new BigDecimal(0.00);  // ????????????
                BigDecimal BClassFee = new BigDecimal(0.00);  // ????????????
                BigDecimal CClassFee = new BigDecimal(0.00);  // ????????????
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
                        preselfpayAmt = BigDecimalUtils.add(preselfpayAmt,
                                BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert
                                        (MapUtils.get(item, "preselfpay_amt") == null ? "" :
                                                MapUtils.get(item, "preselfpay_amt").toString()))));
                        if ("01".equals(MapUtils.get(item, "chrgitm_lv"))) {
                            AClassFee = BigDecimalUtils.add(AClassFee, BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "det_item_fee_sumamt") == null ? "" : MapUtils.get(item, "det_item_fee_sumamt").toString()))));
                        }
                        if ("02".equals(MapUtils.get(item, "chrgitm_lv"))) {
                            BClassFee = BigDecimalUtils.add(BClassFee, BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "det_item_fee_sumamt") == null ? "" : MapUtils.get(item, "det_item_fee_sumamt").toString()))));
                            inscpScpAmt = BigDecimalUtils.add(inscpScpAmt,
                                    BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert
                                            (MapUtils.get(item, "inscp_scp_amt") == null ? "" :
                                                    MapUtils.get(item, "inscp_scp_amt").toString()))));
                        }
                        if ("03".equals(MapUtils.get(item, "chrgitm_lv"))) {
                            CClassFee = BigDecimalUtils.add(CClassFee, BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "fulamt_ownpay_amt") == null ? "" : MapUtils.get(item, "fulamt_ownpay_amt").toString()))));
                        }
                    }
                    pMap.put("sumDetItemFeeSumamt", sumDetItemFeeSumamt);
                    pMap.put("inscpScpAmt", inscpScpAmt);
                    pMap.put("preselfpayAmt", preselfpayAmt);
                    pMap.put("AClassFee", AClassFee);
                    pMap.put("BClassFee", BClassFee);
                    pMap.put("CClassFee", CClassFee);
                    DClassFee = BigDecimalUtils.add(AClassFee, BClassFee);
                    pMap.put("DClassFee", DClassFee);
                    pMap.put("medChrgitmType", key);
                    groupListMap.add(pMap);
                }
            }
        }
        return groupListMap;
    }

    /**
     * ????????????????????????,???????????????????????????
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
            throw new AppException("?????????????????????????????????????????????????????????????????????");
        }
        String insuplcAdmdvs = individualVisitDTO.getInsuplcAdmdvs(); // ???????????????
        String medicineOrgCode = individualVisitDTO.getMedicineOrgCode();  // ??????????????????
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(medicineOrgCode);
        insureConfigurationDTO.setRegCode(individualVisitDTO.getInsureRegCode());
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        String mdtrtareaAdmvs = insureConfigurationDTO.getMdtrtareaAdmvs();
        if (insureConfigurationDTO == null) {
            throw new AppException("????????????????????????????????????????????????????????????????????????");
        }
        if (StringUtils.isEmpty(mdtrtareaAdmvs)) {
            throw new AppException("?????????????????????????????????????????????????????????");
        }
        if (StringUtils.isEmpty(insuplcAdmdvs)) {
            throw new AppException("??????????????????????????????????????????");
        }

        if(Constants.YBQHKT.GD.equals(mdtrtareaAdmvs.substring(0, 2))){
            //?????????????????????????????????
            dataMap.put("setl_id", setlId);//??????ID
            dataMap.put("psn_no", psnNo);//????????????
            dataMap.put("mdtrt_id", mdtrtId);//???????????????
            resultMap = invokingUpay(hospCode, insureRegCode, Constant.UnifiedPay.OUTPT.UP_5260, dataMap);
        }else{
            // ??????????????????????????????????????????????????????????????????
            //??????????????????????????????????????????????????????????????????(?????????????????????????????????)
            if (mdtrtareaAdmvs.substring(0, 2).equals(insuplcAdmdvs.substring(0, 2))) {
                dataMap.put("setl_id", setlId);//??????ID
                dataMap.put("psn_no", psnNo);//????????????
                dataMap.put("mdtrt_id", mdtrtId);//???????????????
                resultMap = invokingUpay(hospCode, insureRegCode, Constant.UnifiedPay.OUTPT.UP_5265, dataMap);

            } else {
                dataMap.put("mdtrtRegno", setlId);//??????ID
                dataMap.put("mdtrtId", mdtrtId);//???????????????
                dataMap.put("medinsNo", insureConfigurationDTO.getOrgCode());//???????????????
                if ("1".equals(individualVisitDTO.getIsHospital())) {
                    resultMap = invokingUpay(hospCode, insureRegCode, Constant.UnifiedPay.OUTPT.UP_5270, dataMap);
                } else {
                    resultMap = invokingUpay(hospCode, insureRegCode, Constant.UnifiedPay.OUTPT.UP_5269, dataMap);
                }
            }
        }

        return resultMap;
    }

    /**
     * @param paraMap
     * @Method queryStatementInfo
     * @Desrciption ?????????????????????
     * @Author liaojiguang
     * @Date 2021/10/21 09:01
     * @Return
     **/
    @Override
    public Map<String, Object> queryStatementInfo(Map<String, Object> paraMap) {
        Map<String, Object> resultMap = new HashMap<>();
        String type = MapUtils.get(paraMap, "statementType");
        List<Map<String, Object>> statementList = new ArrayList<>();
        switch (type) {
            case Constants.DZDLX.CX_PT: // 390 - (11 14 2101 2102 9904)
                paraMap.put("aae140", "390");
                resultMap = this.getOrdinaryData(paraMap);
                break;
            case Constants.DZDLX.CX_DBBX: // 390 - ????????????????????????????????????
                break;
            case Constants.DZDLX.CX_YWSH: // 390 -(9903,22)
                paraMap.put("aae140", "390");
                resultMap = this.getYWSHData(paraMap);
                break;
            case Constants.DZDLX.ZG_PT: // 310 - (11 14 2101 2102 9904)
                paraMap.put("aae140", "310");
                resultMap = this.getOrdinaryData(paraMap);
                break;
            case Constants.DZDLX.ZG_SYBX: // 310 - (51,52)
                paraMap.put("aae140", "310");
                resultMap = this.getSYBXData(paraMap);
                break;
            case Constants.DZDLX.ZG_YWSH: // 310 -(9903,22)
                paraMap.put("aae140", "310");
                resultMap = this.getYWSHData(paraMap);
                break;
            default:
                break;
        }

        // ?????????????????????
        if (resultMap.containsKey("paraMap")) {
            Map<String, Object> sumMap = insureReversalTradeDAO.getStatementSumInfo(MapUtils.get(resultMap, "paraMap"));
            if (sumMap.containsKey("medfeeSumamt")) {
                sumMap.put("medfeeSumamtChina", MoneyUtils.convert(Double.parseDouble(MapUtils.get(sumMap, "medfeeSumamt").toString())));
            }

            if (sumMap.containsKey("planCXHJSumPrice")) {
                sumMap.put("planCXHJSumPriceChina", MoneyUtils.convert(Double.parseDouble(MapUtils.get(sumMap, "planCXHJSumPrice").toString())));
            }

            if (sumMap.containsKey("planSumPrice")) {
                sumMap.put("planSumPriceChina", MoneyUtils.convert(Double.parseDouble(MapUtils.get(sumMap, "planSumPrice").toString())));
            }
            resultMap.put("sumMap", sumMap);
        }

        // ????????????????????????
        InsureConfigurationDTO insureConfInfo = new InsureConfigurationDTO();
        insureConfInfo.setHospCode(MapUtils.get(paraMap, "hospCode"));
        insureConfInfo.setRegCode(MapUtils.get(paraMap, "insureRegCode"));
        insureConfInfo = insureConfigurationDAO.queryInsureIndividualConfig(insureConfInfo);
        insureConfInfo.setCrteName(MapUtils.get(paraMap, "statistician"));
        insureConfInfo.setStartDate(MapUtils.get(paraMap, "startDate"));
        insureConfInfo.setEndDate(MapUtils.get(paraMap, "endDate"));
        resultMap.put("insureConfInfo", insureConfInfo);
        return resultMap;
    }

    /**
     * @param paraMap
     * @Method queryDeclareInfos
     * @Desrciption ??????????????????
     * @Author liaojiguang
     * @Date 2021/10/21 09:01
     * @Return
     **/
    @Override
    public PageDTO queryDeclareInfosPage(Map<String, Object> paraMap) {
        int pageNo = Integer.parseInt(MapUtils.get(paraMap, "pageNo") == null ? "1" : MapUtils.get(paraMap, "pageNo"));
        int pageSize = Integer.parseInt(MapUtils.get(paraMap, "pageSize") == null ? "10" : MapUtils.get(paraMap, "pageSize"));

        // ??????????????????
        PageHelper.startPage(pageNo, pageSize);

        // ??????List
        List<Map<String,Object>> resultList = new ArrayList<>();

        // ?????????????????? IS_VALID_ONE_SETTLE,????????????????????? 1??? ??????  ???0????????????
        paraMap.put("isValidOneSettle",Constants.SF.S); // ?????????????????????
        Map<String,Object> sysParameterMap = new HashMap<>();
        sysParameterMap.put("hospCode",MapUtils.get(paraMap,"hospCode"));
        sysParameterMap.put("code", "IS_VALID_ONE_SETTLE");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(sysParameterMap).getData();
        if (sysParameterDTO != null && Constants.SF.F.equals(sysParameterDTO.getValue())) {
            paraMap.put("isValidOneSettle",Constants.SF.F); // ????????????????????????
        }

        String declaraType = MapUtils.get(paraMap, "declaraType");
        switch (declaraType) {
            case Constants.SBLX.CZJM_ZY: // ????????????????????????
                paraMap.put("insutype", Constant.UnifiedPay.XZLX.CZZG);
                paraMap.put("isHospital", Constants.SF.S);
                break;
            case Constants.SBLX.CXJM_ZY: // ????????????????????????????????????
                paraMap.put("insutype", Constant.UnifiedPay.XZLX.CXJM);
                paraMap.put("isHospital", Constants.SF.S);
                break;
            case Constants.SBLX.LX_ZY: // ??????????????????
                paraMap.put("insutype", Constant.UnifiedPay.XZLX.LX);
                paraMap.put("isHospital", Constants.SF.S);
                break;
            case Constants.SBLX.MZ: // ??????
                paraMap.put("isHospital", Constants.SF.F);
                resultList = insureReversalTradeDAO.queryOutptDeclareInfosPage(paraMap);
                resultList.removeAll(Collections.singleton(null));
                return PageDTO.of(resultList);
            case Constants.SBLX.YZS: // ????????? queryYZSSumDeclareInfosPage
                paraMap.put("insutype", Constant.UnifiedPay.XZLX.CXJM);
                resultList = insureReversalTradeDAO.queryYZSDeclareInfosPage(paraMap);
                resultList.removeAll(Collections.singleton(null));
                return PageDTO.of(resultList);
            case Constants.SBLX.ZG_MZ: // ????????????
                paraMap.put("isHospital", Constants.SF.F);
                paraMap.put("insutype", Constant.UnifiedPay.XZLX.CZZG);
                resultList = insureReversalTradeDAO.queryOutptDeclareInfosPage(paraMap);
                resultList.removeAll(Collections.singleton(null));
                return PageDTO.of(resultList);
            case Constants.SBLX.CX_MZ: // ??????????????????
                paraMap.put("isHospital", Constants.SF.F);
                paraMap.put("insutype", Constant.UnifiedPay.XZLX.CXJM);
                resultList = insureReversalTradeDAO.queryOutptDeclareInfosPage(paraMap);
                resultList.removeAll(Collections.singleton(null));
                return PageDTO.of(resultList);
            case Constants.SBLX.LX_MZ: // ????????????
                paraMap.put("isHospital", Constants.SF.F);
                paraMap.put("insutype", Constant.UnifiedPay.XZLX.LX);
                resultList = insureReversalTradeDAO.queryOutptDeclareInfosPage(paraMap);
                resultList.removeAll(Collections.singleton(null));
                return PageDTO.of(resultList);
            default:
                break;

        }
        resultList = insureReversalTradeDAO.queryDeclareInfosPage(paraMap);
        resultList.removeAll(Collections.singleton(null));
        return PageDTO.of(resultList);
    }

    /**
     * @param paraMap
     * @Method querySumDeclareInfosPage
     * @Desrciption ????????????????????????
     * @Author liaojiguang
     * @Date 2021/10/21 09:01
     * @Return
     **/
    @Override
    public PageDTO querySumDeclareInfosPage(Map<String, Object> paraMap) {
        int pageNo = Integer.parseInt(MapUtils.get(paraMap, "pageNo") == null ? "1" : MapUtils.get(paraMap, "pageNo"));
        int pageSize = Integer.parseInt(MapUtils.get(paraMap, "pageSize") == null ? "10" : MapUtils.get(paraMap, "pageSize"));

        // ??????????????????
        PageHelper.startPage(pageNo, pageSize);

        // ??????List
        List<Map<String,Object>> resultList = new ArrayList<>();


        // ?????????????????? IS_VALID_ONE_SETTLE,????????????????????? 1??? ??????  ???0????????????
        paraMap.put("isValidOneSettle",Constants.SF.S); // ?????????????????????
        Map<String,Object> sysParameterMap = new HashMap<>();
        sysParameterMap.put("hospCode",MapUtils.get(paraMap,"hospCode"));
        sysParameterMap.put("code", "IS_VALID_ONE_SETTLE");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(sysParameterMap).getData();
        if (sysParameterDTO != null && Constants.SF.F.equals(sysParameterDTO.getValue())) {
            paraMap.put("isValidOneSettle",Constants.SF.F); // ????????????????????????
        }

        String declaraType = MapUtils.get(paraMap, "declaraType");
        switch (declaraType) {
            case Constants.SBLX.CZJM_ZY: // ????????????????????????
                paraMap.put("insutype", Constant.UnifiedPay.XZLX.CZZG);
                paraMap.put("isHospital", Constants.SF.S);
                break;
            case Constants.SBLX.CXJM_ZY: // ????????????????????????
                paraMap.put("insutype", Constant.UnifiedPay.XZLX.CXJM);
                paraMap.put("isHospital", Constants.SF.S);
                break;
            case Constants.SBLX.LX_ZY: // ??????????????????
                paraMap.put("insutype", Constant.UnifiedPay.XZLX.LX);
                paraMap.put("isHospital", Constants.SF.S);
                break;
            case Constants.SBLX.MZ: // ??????
                paraMap.put("isHospital", Constants.SF.F);
                resultList = insureReversalTradeDAO.queryOutptSumDeclareInfosPage(paraMap);
                resultList.removeAll(Collections.singleton(null));
                return PageDTO.of(resultList);
            case Constants.SBLX.YZS: // ?????????
                paraMap.put("insutype", Constant.UnifiedPay.XZLX.CXJM);
                resultList = insureReversalTradeDAO.queryYZSSumDeclareInfosPage(paraMap);
                resultList.removeAll(Collections.singleton(null));
                return PageDTO.of(resultList);
            case Constants.SBLX.ZG_MZ: // ????????????
                paraMap.put("isHospital", Constants.SF.F);
                paraMap.put("insutype", Constant.UnifiedPay.XZLX.CZZG);
                resultList = insureReversalTradeDAO.queryOutptSumDeclareInfosPage(paraMap);
                resultList.removeAll(Collections.singleton(null));
                return PageDTO.of(resultList);
            case Constants.SBLX.CX_MZ: // ??????????????????
                paraMap.put("isHospital", Constants.SF.F);
                paraMap.put("insutype", Constant.UnifiedPay.XZLX.CXJM);
                resultList = insureReversalTradeDAO.queryOutptSumDeclareInfosPage(paraMap);
                resultList.removeAll(Collections.singleton(null));
                return PageDTO.of(resultList);
            case Constants.SBLX.LX_MZ: // ????????????
                paraMap.put("isHospital", Constants.SF.F);
                paraMap.put("insutype", Constant.UnifiedPay.XZLX.LX);
                resultList = insureReversalTradeDAO.queryOutptSumDeclareInfosPage(paraMap);
                resultList.removeAll(Collections.singleton(null));
                return PageDTO.of(resultList);
            default:
                break;

        }
        resultList = insureReversalTradeDAO.querySumDeclareInfosPage(paraMap);
        resultList.removeAll(Collections.singleton(null));
        return PageDTO.of(resultList);
    }

    // ?????????????????????
    private Map<String, Object> getSYBXData(Map<String, Object> paraMap) {
        Map<String, Object> resultMap = new HashMap<>();

        // ????????????
        List<String> localOrdinaryOutptList = new ArrayList<>();
        localOrdinaryOutptList.add(Constant.UnifiedPay.YWLX.SYMZ);
        localOrdinaryOutptList.add(Constant.UnifiedPay.YWLX.JBYLSYZY);
        localOrdinaryOutptList.add(Constant.UnifiedPay.YWLX.QTSYMZ);
        localOrdinaryOutptList.add(Constant.UnifiedPay.YWLX.ZZRSMZ);
        paraMap.put("list", localOrdinaryOutptList);
        List<Map<String, Object>> localOrdinaryOutptMap = insureReversalTradeDAO.queryStatementInfo(paraMap);
        if (!ListUtils.isEmpty(localOrdinaryOutptMap)) {
            for (Map<String, Object> map : localOrdinaryOutptMap) {
                String isRemote = MapUtils.get(map, "isRemote");
                if (Constants.SF.F.equals(isRemote)) {
                    resultMap.put("localOrdinaryOutpt", map);
                } else {
                    resultMap.put("offSiteOrdinaryOutpt", map);
                }
            }
        }

        // ????????????
        List<String> localOrdinaryInptList = new ArrayList<>();
        localOrdinaryInptList.add(Constant.UnifiedPay.YWLX.SYZY);
        localOrdinaryInptList.add(Constant.UnifiedPay.YWLX.ZZRSZY);
        paraMap.put("list", localOrdinaryInptList);
        List<Map<String, Object>> localOrdinaryInptMapList = insureReversalTradeDAO.queryStatementInfo(paraMap);
        if (!ListUtils.isEmpty(localOrdinaryInptMapList)) {
            for (Map<String, Object> map : localOrdinaryInptMapList) {
                String isRemote = MapUtils.get(map, "isRemote");
                if (Constants.SF.F.equals(isRemote)) {
                    resultMap.put("localOrdinaryInpt", map);
                } else {
                    resultMap.put("offSiteOrdinaryInpt", map);
                }
            }
        }
        resultMap.put("paraMap", paraMap);
        return resultMap;
    }

    // ?????????(????????????)
    private Map<String, Object> getYWSHData(Map<String, Object> paraMap) {
        Map<String, Object> resultMap = new HashMap<>();
        // ????????????
        List<String> localOrdinaryOutptList = new ArrayList<>();
        localOrdinaryOutptList.add(Constant.UnifiedPay.YWLX.YWSHMZ);
        localOrdinaryOutptList.add(Constant.UnifiedPay.YWLX.DXSWSMZ);
        paraMap.put("list", localOrdinaryOutptList);
        List<Map<String, Object>> localOrdinaryOutptMap = insureReversalTradeDAO.queryStatementInfo(paraMap);
        if (!ListUtils.isEmpty(localOrdinaryOutptMap)) {
            for (Map<String, Object> map : localOrdinaryOutptMap) {
                String isRemote = MapUtils.get(map, "isRemote");
                if (Constants.SF.F.equals(isRemote)) {
                    resultMap.put("localOrdinaryOutpt", map);
                } else {
                    resultMap.put("offSiteOrdinaryOutpt", map);
                }
            }
        }

        // ????????????
        List<String> localOrdinaryInptList = new ArrayList<>();
        localOrdinaryInptList.add(Constant.UnifiedPay.YWLX.WSZY);
        paraMap.put("list", localOrdinaryInptList);
        List<Map<String, Object>> localOrdinaryInptMap = insureReversalTradeDAO.queryStatementInfo(paraMap);
        if (!ListUtils.isEmpty(localOrdinaryInptMap)) {
            for (Map<String, Object> map : localOrdinaryInptMap) {
                String isRemote = MapUtils.get(map, "isRemote");
                if (Constants.SF.F.equals(isRemote)) {
                    resultMap.put("localOrdinaryInpt", map);
                } else {
                    resultMap.put("offSiteOrdinaryInpt", map);
                }
            }
        }
        resultMap.put("paraMap", paraMap);
        return resultMap;
    }

    // ?????????????????????
    private Map<String, Object> getOrdinaryData(Map<String, Object> paraMap) {
        Map<String, Object> resultMap = new HashMap<>();

        // ????????????
        List<String> localOrdinaryOutptList = new ArrayList<>();
        localOrdinaryOutptList.add(Constant.UnifiedPay.YWLX.PTMZ);
        localOrdinaryOutptList.add(Constant.UnifiedPay.YWLX.GH);
        localOrdinaryOutptList.add(Constant.UnifiedPay.YWLX.JZ);
        localOrdinaryOutptList.add(Constant.UnifiedPay.YWLX.MZTC);
        localOrdinaryOutptList.add(Constant.UnifiedPay.YWLX.MZQJ);
        paraMap.put("list", localOrdinaryOutptList);
        List<Map<String, Object>> localOrdinaryOutptMap = insureReversalTradeDAO.queryStatementInfo(paraMap);
        if (!ListUtils.isEmpty(localOrdinaryOutptMap)) {
            for (Map<String, Object> map : localOrdinaryOutptMap) {
                String isRemote = MapUtils.get(map, "isRemote");
                if (Constants.SF.F.equals(isRemote)) {
                    resultMap.put("localOrdinaryOutpt", map);
                } else {
                    resultMap.put("offSiteOrdinaryOutpt", map);
                }
            }
        }

        // ??????/??????/?????? ??????
        List<String> localSpecialOutptList = new ArrayList<>();
        localSpecialOutptList.add(Constant.UnifiedPay.YWLX.MZTSB);
        localSpecialOutptList.add(Constant.UnifiedPay.YWLX.MZMXB);
        localSpecialOutptList.add(Constant.UnifiedPay.YWLX.MZLB);
        paraMap.put("list", localSpecialOutptList);
        List<Map<String, Object>> localSpecialOutptMap = insureReversalTradeDAO.queryStatementInfo(paraMap);
        if (!ListUtils.isEmpty(localSpecialOutptMap)) {
            for (Map<String, Object> map : localSpecialOutptMap) {
                String isRemote = MapUtils.get(map, "isRemote");
                if (Constants.SF.F.equals(isRemote)) {
                    resultMap.put("localSpecialOutpt", map);
                } else {
                    resultMap.put("offSiteSpecialOutpt", map);
                }
            }
        }

        // ????????????
        List<String> localOrdinaryInptList = new ArrayList<>();
        localOrdinaryInptList.add(Constant.UnifiedPay.YWLX.PTZY);
        localOrdinaryInptList.add(Constant.UnifiedPay.YWLX.DBZZY);
        localOrdinaryInptList.add(Constant.UnifiedPay.YWLX.PTZYWZM);
        localOrdinaryInptList.add(Constant.UnifiedPay.YWLX.SYPCJM);
        localOrdinaryInptList.add(Constant.UnifiedPay.YWLX.SYPGCJM);
        localOrdinaryInptList.add(Constant.UnifiedPay.YWLX.JSBZY);
        paraMap.put("list", localOrdinaryInptList);
        List<Map<String, Object>> localOrdinaryInptMap = insureReversalTradeDAO.queryStatementInfo(paraMap);
        if (!ListUtils.isEmpty(localOrdinaryInptMap)) {
            for (Map<String, Object> map : localOrdinaryInptMap) {
                String isRemote = MapUtils.get(map, "isRemote");
                if (Constants.SF.F.equals(isRemote)) {
                    resultMap.put("localOrdinaryInpt", map);
                } else {
                    resultMap.put("offSitelOrdinaryInpt", map);
                }
            }
        }
        localOrdinaryOutptList.addAll(localSpecialOutptList);
        localOrdinaryOutptList.addAll(localOrdinaryInptList);
        paraMap.put("list", localOrdinaryOutptList);
        // ???????????????????????????????????????????????????
        resultMap.put("paraMap", paraMap);
        return resultMap;
    }

    private InsureConfigurationDTO queryInsureIndividualConfig(Map paraMap){
        InsureConfigurationDTO insureConfInfo = new InsureConfigurationDTO();
        insureConfInfo.setHospCode(MapUtils.get(paraMap, "hospCode"));
        insureConfInfo.setRegCode(MapUtils.get(paraMap, "insureRegCode"));
        insureConfInfo = insureConfigurationDAO.queryInsureIndividualConfig(insureConfInfo);
        if (insureConfInfo == null) {
            throw new AppException("????????????????????????");
        }
        insureConfInfo.setCrteName(MapUtils.get(paraMap, "crteName"));
        insureConfInfo.setCrteId(MapUtils.get(paraMap, "crteId"));
        insureConfInfo.setStartDate(MapUtils.get(paraMap, "startDate"));
        insureConfInfo.setEndDate(MapUtils.get(paraMap, "endDate"));
        return insureConfInfo;
    }


}
