package cn.hsa.insure.hunansheng.outpt;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.RequestInsure;
import cn.hsa.module.base.bi.service.BaseItemService;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.module.dao.InsureIndividualBusinessDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dao.InsureItemMatchDAO;
import cn.hsa.module.insure.module.dto.InsureIndividualCostDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureItemMatchDTO;
import cn.hsa.module.insure.module.dto.InsureOutptOutFeeDTO;
import cn.hsa.module.insure.module.entity.InsureConfigurationDO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.util.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.insure.hunansheng.outpt
 * @Class_name: OutptFunction
 * @Describe(描述): 门诊调用医保服务
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/04 11:46
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component("hunansheng-outpt")
public class OutptFunction {

    @Resource
    private RequestInsure requestInsure;

    @Resource
    private InsureIndividualVisitDAO insureIndividualVisitDAO;

    @Resource
    private InsureItemMatchDAO insureItemMatchDAO;

    /**
     * @param param 请求医保参数
     * @Menthod BIZC131101
     * @Desrciption 3.3.1.1    普通门诊提取人员信息：
     * 通过个人标识（电脑号、姓名、公民身份号、IC卡号）取参保人信息、个人帐户信息、基金冻结信息。
     * @Author pengbo
     * @Date 2021/01/30 11:49
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     */
    public Map<String, Object> bizc131101(LinkedHashMap<String, Object> param) {
        Map<String, Object> paramMap = new HashMap<>();
        String regCode = MapUtils.getVS(param, "regCode"); //医保编码
        String hospCode = MapUtils.getVS(param, "hospCode"); //医保编码
        String corpId = MapUtils.getVS(param, "aab001"); //单位编码
        InsureConfigurationDO insureConfigurationDO = requestInsure.toConfig(hospCode,regCode);
        paramMap.put("function_id", Constant.HuNanSheng.OUTPT.BIZC131101); // 功能号
        paramMap.put(MapUtils.getVS(param, "bka895"),MapUtils.getVS(param, "bka896"));
        paramMap.put("oper_staffid","000");//员工号
        paramMap.put("oper_centerid",insureConfigurationDO.getCode());//中心编码
        paramMap.put("oper_hospitalid",insureConfigurationDO.getOrgCode());//医疗机构编码
        paramMap.put("hospital_id", insureConfigurationDO.getOrgCode());//医疗机构编码
        paramMap.put("center_id", insureConfigurationDO.getCode()); //中心医保编码
        // 如果是一个电脑号对应多个单位参保信息，需传入单位编码
        if (!StringUtils.isEmpty(corpId)) {
            paramMap.put("corp_id", corpId);
        }
        //业务类型（"11"：普通门诊 ,"51"：生育门诊 ,"41"：工伤门诊 ,"45"：工伤辅助器具）
        paramMap.put("biz_type", MapUtils.get(param, "aka130"));

        //调用医保接口
        Map<String, Object> httpResult = requestInsure.callHNS((String) param.get("hospCode"), regCode, paramMap);
        List<Map<String,Object>> resultList = new ArrayList<>();

        // 个人基本信息
        List<Map<String,Object>> list = (List<Map<String,Object>>)httpResult.get("personinfo");

        // 工伤生育信息
        List<Map<String,Object>> injuryorbirthList = (List<Map<String,Object>>)httpResult.get("injuryorbirthinfo");

        // 特殊业务信息（门诊特殊病）
        List<Map<String,Object>> spinfoList = (List<Map<String,Object>>)httpResult.get("spinfo");

        for (Map map:list) {
            Map<String,Object> insureMap = new HashMap<>();
            insureMap.put("aaa027",map.get("center_id"));
            insureMap.put("aaa027_name",map.get("center_name"));
            insureMap.put("aab001",map.get("corp_id"));
            insureMap.put("aab019",map.get(""));
            insureMap.put("aab069",map.get("corp_name"));
            insureMap.put("aab301",map.get("post_code"));
            insureMap.put("aab999",map.get(""));
            insureMap.put("aac001",map.get("indi_id"));
            insureMap.put("aac002",map.get("idcard"));
            insureMap.put("aac003",map.get("name"));
            insureMap.put("aac004",map.get("sex"));
            insureMap.put("aac006",map.get("birthday"));
            insureMap.put("aac008",map.get("indi_join_sta"));
            insureMap.put("bka008",map.get("corp_name"));
            insureMap.put("aac148",map.get("urban_type"));
            insureMap.put("bacu18",map.get("last_balance"));
            insureMap.put("aae005",map.get("telephone"));
            insureMap.put("baa027",map.get("center_id"));
            insureMap.put("akb021_last",map.get("telephone"));
            insureMap.put("bka888",map.get("freeze_sta"));
            insureMap.put("bac001",map.get("official_code"));
            insureMap.put("bac001_name",map.get("official_name"));
            insureMap.put("bka035",map.get("pers_type_detail"));
            insureMap.put("bka006",map.get("treatment_type"));
            insureMap.put("shbzkh",map.get("insr_code"));
            insureMap.put("telephone",map.get("telephone"));
            resultList.add(insureMap);
        }

        List<Map<String,Object>> injurList = new ArrayList<>();
        if (!ListUtils.isEmpty(injuryorbirthList)) {
            for (Map<String,Object> inj : injuryorbirthList) {
                Map<String,Object> map = new HashMap<>();
                map.put("aac003",inj.get("name"));
                map.put("bka042",inj.get("serial_pers"));
                map.put("aae030",inj.get("accident_date"));
                map.put("accidentDetail",inj.get("accident_detail"));
                map.put("alc022",inj.get("alc022"));
                map.put("identify_flag",inj.get("identify_flag"));
                map.put("serial_pers",inj.get("serial_pers"));
                map.put("injuryBorthSn",inj.get("serial_pers")); // 业务序列号
                injurList.add(map);
            }
            httpResult.put("injuryorbirthinfo",injurList);
        }

        List<Map<String,Object>> spList = new ArrayList<>();
        if (!ListUtils.isEmpty(spinfoList)) {
            for (Map<String,Object> spInfo : spinfoList) {
                Map<String,Object> spMap = new HashMap<>();
                spMap.put("serial_pers",spInfo.get("serial_pers"));
                spMap.put("injuryBorthSn",spInfo.get("serial_pers"));
                spMap.put("aaz267",spInfo.get("serial_apply"));
                spMap.put("akc193",spInfo.get("icd"));
                spMap.put("bkz101",spInfo.get("disease"));
                spMap.put("aae030",spInfo.get("admit_effect"));
                spMap.put("aae031",spInfo.get("admit_date"));
                spMap.put("akb081",spInfo.get("special_in_seg"));
                spList.add(spMap);
            }
            httpResult.put("spinfo",spList);
        }

        httpResult.put("personinfo",resultList);
        return httpResult;
    }


    /**
     * @param param 请求参数
     * @Menthod bizc131110
     * @Desrciption 3.3.1.2    退改费取个人信息、业务信息：
     * 通过个人标识（电脑号、姓名、公民身份号、IC卡号、保险号）取参保人信息、个人业务息、基金冻结信息。
     * @Author pengbo
     * @Date 2021/01/30 9:19
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     */
    public Map<String, Object> bizc131110(HashMap<String, Object> param) {
        //入参Map
        // 人员信息Map
        Map<String, Object> paramMap = new HashMap<>();
        //医保编码
        String regCode = (String) param.get("regCode");
        //功能号
        paramMap.put("function_id", Constant.HuNanSheng.OUTPT.BIZC131110);
        //1.个人电脑号
        paramMap.put("indi_id", MapUtils.getVS(param, "aac001"));
        //2.姓名
        paramMap.put("name", MapUtils.getVS(param, "aac003"));
        //3.参保人身份证号码
        paramMap.put("idcard", MapUtils.getVS(param, "aac002"));
        //4.参保人IC卡号
        //paramMap.put("iccardno", null);
        //5.参保人的保险号
        //paramMap.put("insr_code", null);
        //6.参保人的银行卡号
        //paramMap.put("bank_card", null);
        //医疗机构编码
        paramMap.put("hospital_id", regCode);
        //业务类型（"11"：普通门诊 ,"51"：生育门诊 ,"41"：工伤门诊 ,"45"：工伤辅助器具）
        paramMap.put("biz_type", MapUtils.get(param, "aka130", "11"));
        //中心医保编码
        paramMap.put("center_id", regCode);
        //业务开始时间
        paramMap.put("fromdate", null);
        //业务结束时间
        paramMap.put("todate", null);
        //调用医保接口
        Map<String, Object> httpResult = requestInsure.callHNS((String) param.get("hospCode"), regCode, paramMap);

        //返回参数
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String,Object>> feeList = (List<Map<String, Object>>) httpResult.get("feeList");
        resultMap.put("feeList",feeList);
        return resultMap;
    }

    /**
     * @param param 请求参数
     * @Menthod bizc131111
     * @Desrciption 3.3.1.3    退改费取费用信息:通过业务序列号、医疗机构编码、费用最大批次获取需要退、改的费用信息。
     * @Author pengbo
     * @Date 2021/01/30 9:19
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     */
    public Map<String, Object> bizc131111(HashMap<String, Object> param) {
        InsureOutptOutFeeDTO insureOutptOutFeeDTO = (InsureOutptOutFeeDTO)param.get("insureOutptOutFeeDTO");
        String insureRegCode = MapUtils.get(param,"insureRegCode");
        String hospCode = MapUtils.get(param,"hospCode");

        Map<String, Object> paramMap = new HashMap<>();
        InsureConfigurationDO insureConfigurationDO = requestInsure.toConfig(hospCode,insureRegCode);
        paramMap.put("function_id", Constant.HuNanSheng.OUTPT.BIZC131111);
        paramMap.put("oper_staffid","000");//员工号
        paramMap.put("oper_centerid",insureConfigurationDO.getCode());//中心编码
        paramMap.put("oper_hospitalid",insureConfigurationDO.getOrgCode());//医疗机构编码
        paramMap.put("center_id", insureConfigurationDO.getCode()); //中心医保编码*/
        paramMap.put("hospital_id", insureConfigurationDO.getOrgCode());//医疗机构编码
        paramMap.put("serial_no", insureOutptOutFeeDTO.getAaz217());//业务序列号
        paramMap.put("fee_batch", "1"); // 最大费用批次,0查询全部
        //调用医保接口
        Map<String, Object> httpResult = requestInsure.callHNS(hospCode, insureRegCode, paramMap);
        List<Map<String,Object>>  feeinfo = (List) httpResult.get("feeinfo");

        //返回参数
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("feeList",feeinfo);
        return resultMap;

    }

    /**
     * @param param 请求参数
     * @Menthod bizc131104
     * @Desrciption3.3.1.4 3.3.1.4 校验计算并保存录入的费用明细信息：校验计算并保存录入的费用明细信息
     * @Author liaojiguang
     * @Date 2021/01/30 9:19
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     */
    public Map<String, Object> bizc131104(HashMap<String, Object> param) {
        String hospCode = MapUtils.getVS(param, "hospCode");
        String visitId = MapUtils.getVS(param, "visitId");
        String insureRegCode = MapUtils.getVS(param, "insureRegCode");
        String saveFlag = MapUtils.getVS(param, "saveFlag");
        String serialNo = MapUtils.getVS(param, "aaz217");
        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        insureIndividualVisitDTO.setHospCode(hospCode);
        insureIndividualVisitDTO.setVisitId(visitId);
        List<InsureIndividualVisitDTO> insureIndividualVisitDTOList = insureIndividualVisitDAO.findByCondition(insureIndividualVisitDTO);
        if (ListUtils.isEmpty(insureIndividualVisitDTOList)) {
            throw new AppException("未找到医保个人就诊信息。");
        }
        insureIndividualVisitDTO = insureIndividualVisitDTOList.get(0);

        // 医保机构信息
        InsureConfigurationDO insureConfigurationDO = requestInsure.toConfig(hospCode,insureRegCode);
        if (insureConfigurationDO == null) {
            throw new AppException("医保机构信息不存在");
        }

        //入参Map
        Map<String, Object> httpParam = new HashMap<>();
        httpParam.put("function_id", Constant.HuNanSheng.OUTPT.BIZC131104); // 功能号
        httpParam.put("corp_id",insureIndividualVisitDTO.getAab001()); // 单位编码
        httpParam.put("corp_name",insureIndividualVisitDTO.getBka008()); // 单位名称
        httpParam.put("serial_no",serialNo); // 就医登记号
        httpParam.put("fee_batch","1"); // 费用批次
        httpParam.put("oper_staffid","000");//员工号
        httpParam.put("oper_centerid",insureConfigurationDO.getCode());//中心编码
        httpParam.put("oper_hospitalid",insureConfigurationDO.getOrgCode());//医疗机构编码
        httpParam.put("center_id", insureConfigurationDO.getCode()); //中心编码
        httpParam.put("hospital_id", insureConfigurationDO.getOrgCode()); // 医疗机构编码
        httpParam.put("indi_id", insureIndividualVisitDTO.getAac001());  // 个人编号
        httpParam.put("biz_type", insureIndividualVisitDTO.getAka130()); //业务类型"11"：普通门诊 "51"：生育门诊 "41"：工伤门诊 "45"：工伤辅助器具
        httpParam.put("treatment_type", insureIndividualVisitDTO.getBka006()); //待遇类型
        httpParam.put("reg_staff", "000"); //登记人员工号
        httpParam.put("reg_man", insureIndividualVisitDTO.getCrteName()); //登记人姓名
        if (insureIndividualVisitDTO.getVisitTime() != null) {
            httpParam.put("diagnose_date", DateUtils.format(insureIndividualVisitDTO.getVisitTime(),DateUtils.Y_M_DH_M_S));  //就诊时间 格式：YYYY-MM-DD HH24:MI:SS(24小时)
        }
        httpParam.put("diagnose", insureIndividualVisitDTO.getVisitIcdCode()); //登记诊断 使用中心疾病icd编码
       /* httpParam.put("in_disease_name", insureIndividualVisitDTO.getVisitIcdName()); //登记诊断名称 使用中心疾病名称*/
        httpParam.put("save_flag", saveFlag); //计算保存标志 "0"：试算 "1"：收费
        //httpParam.put("last_balance", "487.55");
        httpParam.put("last_balance", insureIndividualVisitDTO.getAkc252()); //个人帐户支付金额
        httpParam.put("injury_borth_sn", insureIndividualVisitDTO.getInjuryBorthSn()); //工伤个人业务序号 （或生育资格认定号）
        // （工伤门诊、辅助器具业务不能为空）如果是工伤门诊或者工伤辅助器具业务，biz_type＝41或者biz_type＝45，必须传入该值； 如果是生育业务，biz_type=51,则除了计生手术外的其他生育业务都必须传入该值
        httpParam.put("recipe_no", insureIndividualVisitDTO.getId());//处方号
        httpParam.put("doctor_no", insureIndividualVisitDTO.getCode());  //处方医生编号
        httpParam.put("doctor_name", insureIndividualVisitDTO.getAdviceDoctorName()); //处方医生姓名
        httpParam.put("note", insureIndividualVisitDTO.getRemark()); //备注
        httpParam.put("serial_apply", insureIndividualVisitDTO.getInjuryBorthSn());//门诊特殊病业务申请号
        httpParam.put("bill_no", ""); //单据号 唯一省直中心可为空长沙中心如果为空系统自动分配
        httpParam.put("cash_money", "0.00");//刷卡金额 格式：0.00
        //paramMap.put("patient_id", null); //His端门诊号 目前只长沙县核三系统用到
        //paramMap.put("his_serial", null); //His端业务流水号 目前只长沙县核三系统用到

        //获取费用信息
        List<OutptCostDTO> feeInfoList = (List<OutptCostDTO>) param.get("fees");
        List<String> itemIdList = new ArrayList<>();
        for (OutptCostDTO outptCostDTO : feeInfoList) {
            itemIdList.add(outptCostDTO.getItemId());
        }

        // 批量获取费用匹配信息
        Map<String, Object> itemMatchParam = new HashMap<String, Object>();
        itemMatchParam.put("hospCode", hospCode);
        itemMatchParam.put("insureRegCode", insureRegCode);
        itemMatchParam.put("xmid", itemIdList);
        List<InsureItemMatchDTO> insureItemMatchDTOList = insureItemMatchDAO.queryInsureMatchItemByItems(itemMatchParam);
        if (ListUtils.isEmpty(insureItemMatchDTOList)) {
            throw new AppException("未找到医保匹配的药品/项目/材料");
        }

        List<Map<String,Object>> feeinfo = new ArrayList<>();
        BigDecimal otherMoney = BigDecimal.ZERO;
        for(OutptCostDTO outptCostDTO : feeInfoList) {
            Map<String, Object> feeinfoMap = new HashMap<>();
            feeinfoMap.put("model",outptCostDTO.getPrepCode() == null ? "" : outptCostDTO.getPrepCode());//	剂型
            feeinfoMap.put("factory","");//	厂家
            feeinfoMap.put("standard",outptCostDTO.getSpec() == null ? "" : outptCostDTO.getSpec());// 规格
            feeinfoMap.put("fee_date", DateUtils.format(outptCostDTO.getCrteTime(),DateUtils.Y_M_DH_M_S));//	费用发生时间
            feeinfoMap.put("unit","");//	计量单位


            //String str = df1.format(num);

            feeinfoMap.put("price",String.valueOf(outptCostDTO.getPrice())); //	单价
            feeinfoMap.put("dosage",outptCostDTO.getTotalNum());//	用量

            DecimalFormat df1 = new DecimalFormat("0.00");
            BigDecimal inptMoney = BigDecimalUtils.convert(outptCostDTO.getRealityPrice().toString());
            String realityPrice = df1.format(inptMoney);
            feeinfoMap.put("money",realityPrice);//	金额
            feeinfoMap.put("usage_flag","0");//	用药标志 "0"：普通"1"：出院带药"2"：抢救
            feeinfoMap.put("usage_days","");//	出院带药天数
            feeinfoMap.put("input_staff","000");//	录入工号
            feeinfoMap.put("input_man",outptCostDTO.getCrteName());//	录入人
            feeinfoMap.put("input_date",DateUtils.format(outptCostDTO.getCrteTime(),DateUtils.Y_M_DH_M_S));//	录入日期
            feeinfoMap.put("recipe_no",outptCostDTO.getOpId());//		处方号
            feeinfoMap.put("doctor_no","");//	处方医生编号
            feeinfoMap.put("input_money",realityPrice);
          //  feeinfoMap.put("query_flag", "1"); // 省门特需要收（改）费标志出错（0:收费，1：改费）
            feeinfoMap.put("doctor_name",outptCostDTO.getDoctorName());// 处方医生姓名
            for (InsureItemMatchDTO insureItemMatchDTO : insureItemMatchDTOList) {
                if (insureItemMatchDTO.getHospItemId().equals(outptCostDTO.getItemId())) {
                    String hosSerial = outptCostDTO.getId();
                    // 说明是退费
                    if (StringUtils.isNotEmpty(serialNo)) {
                        hosSerial = outptCostDTO.getOldCostId();
                        //String oppSerialFee =hosSerial.substring(7);
                        feeinfoMap.put("opp_serial_fee","");//	对应费用序列号(12位)
                        feeinfoMap.put("hos_serial",hosSerial);//	医院费用序列号（20位）
                        feeinfoMap.put("money", "0");// 金额 12 否
                        feeinfoMap.put("input_money",realityPrice);
                        feeinfoMap.put("old_money",BigDecimalUtils.negate(inptMoney).toString());
                    } else {
                        feeinfoMap.put("opp_serial_fee","");//	对应费用序列号(12位)
                        feeinfoMap.put("hos_serial",hosSerial);//	医院费用序列号（20位）
                    }
                    String feeBatch =hosSerial.substring(14);
                    feeinfoMap.put("fee_batch",feeBatch); // 费用批次（5位）
                    String itemStr =""; //项目药品类型
                    // 说明是西药
                    if("11".equals(insureItemMatchDTO.getItemCode())){
                        itemStr ="1";
                    }
                    //中成药
                    else if("12".equals(insureItemMatchDTO.getItemCode())){
                        itemStr ="2";
                    }
                    //中草药
                    else if("13".equals(insureItemMatchDTO.getItemCode())){
                        itemStr ="3";
                    }
                    // 说明是项目
                    else{
                        itemStr ="0";
                    }
                    feeinfoMap.put("medi_item_type",itemStr);// 项目药品类型
                    feeinfoMap.put("his_item_code",insureItemMatchDTO.getHospItemCode());//	医院药品项目编码
                    feeinfoMap.put("his_item_name",insureItemMatchDTO.getHospItemName());//	医院药品项目名称
                    feeinfoMap.put("stat_type",insureItemMatchDTO.getItemCode());     // 费用统计类别
                    //feeinfoMap.put("item_code",insureItemMatchDTO.getInsureItemCode());// 中心药品项目编码
                    feeinfo.add(feeinfoMap);
                    break;
                }
            }
        }
        httpParam.put("feeinfo",feeinfo); // 费用信息

        /* 调用医保接口 ------------------ */
        Map<String, Object> httpResult = requestInsure.callHNS((String) param.get("hospCode"), insureRegCode, httpParam);

        /* 处理医保回参 ------------------
        *   fund_pay：基金支付金额
        *   cash_pay_com：个人自付现金部分
        *   acct_pay_com：个人自付个人帐户部分
        *   cash_pay_own：个人自费现金部分
        *   acct_pay_own：个人自费个人帐户部分
        *   hosp_pay: 医院分担费用
        *
        * */
        BigDecimal akc264;//医保项目总费用
        BigDecimal bka831;//个人自付
        BigDecimal bka832;//医保支付
        BigDecimal ake039 = BigDecimal.ZERO;//医疗保险统筹基金支付（基金支付）
        BigDecimal akb066;//个人账户支付 // 账户支付 = 个人自付个人帐户部分 + 个人自费个人帐户部分
        BigDecimal akb067;//个人现金支付 // 现金支付 = 个人自付现金部分 + 个人自费现金部分
        BigDecimal bka842 = BigDecimal.ZERO;//医院支付
        BigDecimal bka825 = BigDecimal.ZERO;//全自费费用
        BigDecimal bka826 = BigDecimal.ZERO;//部分自费费用
        BigDecimal acctPayOwn = BigDecimal.ZERO;// 个人自费个人帐户部分
        BigDecimal acctPayCom = BigDecimal.ZERO;// 个人自付个人帐户部分

        // 获取费用信息
        List<Map<String,String>> payinfoList = MapUtils.get(httpResult,"payinfo");
        if (!ListUtils.isEmpty(payinfoList)) {
            for (Map<String,String> item : payinfoList) {
                BigDecimal aae019 = BigDecimalUtils.convert(item.get("real_pay")); // 基金支付金额
                String bka573 = item.get("fund_name");// 基金名称
                switch (bka573) {
                    case "fund_pay": // 基金支付金额
                        ake039 = BigDecimalUtils.add(ake039,aae019);
                        break;
                    case "cash_pay_com" : // 个人自付现金部分
                        bka826 = BigDecimalUtils.add(bka826,aae019);
                        break;
                    case "acct_pay_com": // 个人自付个人帐户部分
                        acctPayCom = BigDecimalUtils.add(acctPayCom,aae019);
                        break;
                    case "cash_pay_own" :// 个人自费现金部分
                        bka825 = BigDecimalUtils.add(bka825,aae019);
                        break;
                    case "acct_pay_own" : // 个人自费个人帐户部分
                        acctPayOwn = BigDecimalUtils.add(acctPayOwn,aae019);
                        break;
                    case "hosp_pay" : // 医院垫付
                        bka842 = BigDecimalUtils.add(bka842,aae019);
                        break;
                }
            }
        }

        // 个人账户支付 = 个人自付个人帐户部分 + 个人自费个人帐户部分
        akb066 = BigDecimalUtils.add(acctPayCom,acctPayOwn);

        // 医保支付 = 基金支付金额 + 个人账户支付 + 医院垫付
        bka832 = BigDecimalUtils.add(ake039,akb066).add(bka842);

        // 个人现金支付 = 个人自费现金部分 + 个人自付现金部分
        akb067 = BigDecimalUtils.add(bka826,bka825);

        // 个人支付 = 个人现金支付
        bka831 = akb067;

        // 医保项目总费用 = 个人支付 + 医保支付
        akc264 = BigDecimalUtils.add(bka831,bka832);
        Map<String,Object> payinfo = new HashMap<>();
        payinfo.put("akb020",insureRegCode);
        payinfo.put("akc264",akc264.toString());
        payinfo.put("ake039",ake039.toString());
        payinfo.put("bka831",bka831.toString());
        payinfo.put("bka832",bka832.toString());
        payinfo.put("bka825",bka825.toString());
        payinfo.put("bka826",bka826.toString());
        payinfo.put("bka842",bka842.toString());
        payinfo.put("akb066",akb066.toString());
        payinfo.put("akb067",akb067.toString());
        payinfo.put("bka826","0");
        payinfo.put("bka840","0");
        payinfo.put("bka841","0");
        payinfo.put("bka843","0");
        payinfo.put("ake026","0");
        payinfo.put("ake029","0");
        payinfo.put("ake035","0");
        payinfo.put("bka801","0");
        payinfo.put("bka821","0");

        List<Map<String,String>> bizInfoList = (List<Map<String, String>>) httpResult.get("bizinfo");
        if (!ListUtils.isEmpty(bizInfoList)) {
            String aaz217 = bizInfoList.get(0).get("serial_no");
            payinfo.put("aaz217",aaz217);
        }

        //返回参数
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("payinfo",payinfo);
        return resultMap;
    }


    /**
     * @param param 请求参数
     * @Menthod bizc200102
     * @Desrciption 3.3.1.3    提取门诊业务结算信息：通过医院编码(hospital_id)和业务序列号(serial_no)提取门诊业务结算信息。
     * @Author pengbo
     * @Date 2021/01/30 9:19
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     */
    public Map<String, Object> bizc200102(HashMap<String, Object> param) {
        //入参Map
        // 人员信息Map
        Map<String, Object> paramMap = new HashMap<>();
        //功能号
        paramMap.put("function_id", Constant.HuNanSheng.OUTPT.BIZC200102);
        //医保编码
        String regCode = (String) param.get("regCode");
        //医疗机构编码
        paramMap.put("hospital_id", regCode);
        //业务序列号
        paramMap.put("serial_no", null);
        //调用医保接口
        Map<String, Object> httpResult = requestInsure.callHNS((String) param.get("hospCode"), regCode, param);
        //返回参数
        Map<String, Object> resultMap = new HashMap<>();
        //TODO  设置返回参数

        return resultMap;
    }

    /**
     * @param param 请求参数
     * @Menthod bizc20010201
     * @Desrciption 3.3.1.3    提取门诊业务结算信息：通过医院编码(hospital_id)和业务序列号(serial_no)提取门诊业务结算信息。
     * @Author pengbo
     * @Date 2021/01/30 9:19
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     */
    public Map<String, Object> bizc20010201(LinkedHashMap<String, Object>  param) {
        String regCode = MapUtils.getVS(param, "regCode"); //医保编码
        String hospCode = MapUtils.getVS(param, "hospCode"); //医院编码
        InsureConfigurationDO insureConfigurationDO = requestInsure.toConfig(hospCode,regCode);
        //入参Map
        // 人员信息Map
        Map<String, Object> paramMap = new HashMap<>();
        //功能号
        paramMap.put("function_id", Constant.HuNanSheng.OUTPT.BIZC200102);
        //医疗机构编码
        paramMap.put("hospital_id", insureConfigurationDO.getOrgCode());//医疗机构编码
        //业务序列号
        paramMap.put("serial_no", MapUtils.getVS(param, "medicalRegNo"));
        //调用医保接口
        Map<String, Object> httpResult = requestInsure.callHNS((String) param.get("hospCode"), regCode, paramMap);
        //返回参数
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> outptMap = MapUtils.get(httpResult, "info");

        //TODO  设置返回参数
        resultMap.put("psn_no",MapUtils.get(outptMap,"indi_id"));//人员编号
        resultMap.put("psn_cert_type","01");//人员证件类型
        resultMap.put("certno",MapUtils.get(outptMap,"idcard"));//证件号码
        resultMap.put("psn_name",MapUtils.get(outptMap,"name"));//姓名
        resultMap.put("gend",MapUtils.get(outptMap,"sex"));//性别
        resultMap.put("insutype","");//险种类型
        resultMap.put("psn_type",MapUtils.get(outptMap,"pers_name"));//人员类别
        resultMap.put("cvlserv_flag","");//公务员标志
        resultMap.put("begntime",MapUtils.get(outptMap,"begin_date"));//开始时间
        resultMap.put("endtime",MapUtils.get(outptMap,"end_date"));//结束时间
        resultMap.put("med_type","41");//医疗类别
        resultMap.put("ipt_otp_no",MapUtils.get(outptMap,"patient_id"));//住院/门诊号
        resultMap.put("adm_dept_name",MapUtils.get(outptMap,"in_dept_name"));//入院科室名称
        resultMap.put("dscg_maindiag_code","");//住院主诊断代码
        resultMap.put("dscg_maindiag_name",MapUtils.get(outptMap,"in_disease"));//住院主诊断名称


        return resultMap;
    }

}
