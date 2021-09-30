package cn.hsa.insure.xiangtan.inpt;

import cn.hsa.hsaf.core.framework.util.DateUtil;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.RequestInsure;
import cn.hsa.module.inpt.doctor.dto.InptBabyDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.doctor.service.InptBabyService;
import cn.hsa.module.inpt.individualInpatient.dto.InsureIndividualInpatientDTO;
import cn.hsa.module.inpt.inregister.service.InptVisitService;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualCostDAO;
import cn.hsa.module.insure.module.dto.*;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Package_name: cn.hsa.insure.xiangtan.inpt
 * @Class_name: inptFunction
 * @Describe(描述): 住院调用医保服务
 * @Author: 廖继广
 * @Eamil: jiguang.liao@powersi.com.cn
 * @Date: 2020/11/05 09:37
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component("xiangtan-inpt")
public class InptFunction {

    @Resource
    private RequestInsure requestInsure;

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    @Resource
    private InsureIndividualCostDAO insureIndividualCostDAO;

    @Resource
    private InptVisitService inptVisitService;

    @Resource
    private InptBabyService inptBabyService;


    /**
     * @Menthod bizh120003
     * @Desrciption 校验并计算费用信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/5 10:44
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> bizh120003(HashMap<String,Object> param){
        InptVisitDTO inptVisitDTO = (InptVisitDTO) param.get("inptVisit");//住院就诊信息
        InsureIndividualVisitDTO insureIndividualVisitDTO = (InsureIndividualVisitDTO) param.get("insureIndividualVisit");//医保就诊信息
        if (inptVisitDTO == null || insureIndividualVisitDTO == null){
            throw new AppException("未获取个人医保就诊信息。");
        }
        Map<String,Object> httpParam = new HashMap<String,Object>();
        httpParam.put("function_id",Constant.Xiangtan.INPT.BIZH120003);
        httpParam.put("akb020",insureIndividualVisitDTO.getInsureOrgCode());//医疗机构编码
        httpParam.put("aaz217",insureIndividualVisitDTO.getMedicalRegNo());//就医登记号
        httpParam.put("akc196",inptVisitDTO.getOutDiseaseIcd10());//出院诊断疾病编码
        httpParam.put("aae031",DateUtils.format(inptVisitDTO.getOutTime(),DateUtils.YMD));//业务结束时间
        httpParam.put("aae240",null);//使用个帐金额
        httpParam.put("bka438",Constant.Xiangtan.DICT.YWCJ);//业务场景阶段 (1：业务开始有且仅能为1)
        Map<String,Object> httpResult = requestInsure.call((String)param.get("hospCode"),(String) param.get("insureRegCode"),httpParam);
        httpResult.put("bka831",MapUtils.get(httpResult,"akb067")); // 个人自付 = 个人现金支付
        return httpResult;
    }

    /**
     * @Menthod remote_bizc131255
     * @Desrciption 异地医保费用试算
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/30 20:51
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,String> remote_bizc131255(HashMap<String,Object> param){
        InptVisitDTO inptVisitDTO = (InptVisitDTO) param.get("inptVisit");//住院就诊信息
        InsureIndividualVisitDTO insureIndividualVisitDTO = (InsureIndividualVisitDTO) param.get("insureIndividualVisit");//医保就诊信息
        if (inptVisitDTO == null || insureIndividualVisitDTO == null){
            throw new AppException("未获取个人医保就诊信息。");
        }
        Map<String,Object> httpParam = new HashMap<String,Object>();
        httpParam.put("function_id", Constant.Xiangtan.INPT.REMOTE_BIZC131255);//功能号
        httpParam.put("akb020",insureIndividualVisitDTO.getInsureOrgCode());//医疗机构编码
        httpParam.put("aaz217",insureIndividualVisitDTO.getMedicalRegNo());//就医登记号
        httpParam.put("akc252",null);//本次业务个人 帐户可用金额
        httpParam.put("bka066",Constants.SF.F);//保存标志 是否保存计算结果（0：否 1：是）
        httpParam.put("bka006",insureIndividualVisitDTO.getBka006());//待遇类别
        httpParam.put("akc196",inptVisitDTO.getOutDiseaseIcd10());//出院诊断
        httpParam.put("aae031",DateUtils.format(inptVisitDTO.getOutTime(),DateUtils.YMD));//出院日期
        if (Constant.Xiangtan.DEFAULTVAL.SY_AKA130.equals(insureIndividualVisitDTO.getAka130())){
            httpParam.put("bka016","P");//生育就诊类型 生育住院不能为空，D：定额， P：普通消费 T：特殊情况
        }
        httpParam.put("bke550",null);//卡识别码 跨省异地就医业务必传卡识别码
        Map<String,Object> resultMap = requestInsure.call((String)param.get("hospCode"),(String) param.get("insureRegCode"),httpParam);
        List<Map<String,String>> payinfoList = (List<Map<String, String>>) resultMap.get("payinfo");
        BigDecimal bka832 = new BigDecimal(0);//医保支付
        BigDecimal bka842 = new BigDecimal(0);//医院支付
        BigDecimal bka825 = new BigDecimal(0);//全自费费用
        BigDecimal bka826 = new BigDecimal(0);//部分自费费用
        BigDecimal bka840 = new BigDecimal(0);//其他基金支付
        BigDecimal bka841 = new BigDecimal(0);//单位支付
        BigDecimal bka843 = new BigDecimal(0);//特惠保
        BigDecimal ake026 = new BigDecimal(0);//企业补充医疗保险基金支付
        BigDecimal ake029 = new BigDecimal(0);//大额医疗费用补助基金支付
        BigDecimal ake035 = new BigDecimal(0);//公务员医疗补助基金支付
        BigDecimal ake039 = new BigDecimal(0);//医疗保险统筹基金支付 1
        BigDecimal bka801 = new BigDecimal(0);//床位费超额金额
        BigDecimal bka821 = new BigDecimal(0);//民政救助金支付
        BigDecimal akb066 = new BigDecimal(0);//个人账户支付 1

        if (payinfoList != null && !payinfoList.isEmpty()){
            for (Map<String,String> item : payinfoList){
                String aaa157 = item.get("aaa157");//基金类别
                BigDecimal aae019 = BigDecimalUtils.convert(item.get("aae019"));//基金支付金额
                String bka573 = item.get("bka573");//基金名称
                if("999".equals(aaa157)){
                    //排除现金支付
                    if ("个人现金自费".equals(bka573)){
                        bka825 = BigDecimalUtils.add(bka825,aae019);
                    }
                    continue;
                }
                if ("996".equals(aaa157)){
                    //医院支付金
                    bka842 = BigDecimalUtils.add(bka842,aae019);
                }else{
                    //医保报销金额
                    bka832 = BigDecimalUtils.add(bka832,aae019);
                    if ("001".equals(aaa157)){
                        //医疗保险统筹基金
                        ake039 = BigDecimalUtils.add(ake039,aae019);
                    }else if ("003".equals(aaa157)){
                        //医疗保险个人帐户
                        akb066 = BigDecimalUtils.add(akb066,aae019);
                    }else if ("201".equals(aaa157)){
                        //医疗保险大病互助基金
                        ake029 = BigDecimalUtils.add(ake029,aae019);
                    }else if ("202".equals(aaa157)){
                        //医疗保险离休基金
                        ake026 = BigDecimalUtils.add(ake026,aae019);
                    }else if ("301".equals(aaa157)){
                        //医疗保险公务员补助
                        ake035 = BigDecimalUtils.add(ake035,aae019);
                    }else if ("401".equals(aaa157)){
                        //医疗补偿金
                        bka840 = BigDecimalUtils.add(bka840,aae019);
                    }else if ("501".equals(aaa157)){
                        //工伤保险基金
                        bka840 = BigDecimalUtils.add(bka840,aae019);
                    }else if ("511".equals(aaa157)){
                        //生育保险基金
                        bka840 = BigDecimalUtils.add(bka840,aae019);
                    }else if ("801".equals(aaa157)){
                        //居民统筹基金
                        bka821 = BigDecimalUtils.add(bka821,aae019);
                    }else if ("901".equals(aaa157)){
                        //意外伤害基金
                        bka840 = BigDecimalUtils.add(bka840,aae019);
                    }else if ("803".equals(aaa157)){
                        //居民大病保险基金
                        ake029 = BigDecimalUtils.add(ake029,aae019);
                    }
                }
            }
        }
        Map<String,String> result = new HashMap<String,String>();
        result.put("bka832",bka832.toString());//医保支付
        result.put("bka842",bka842.toString());//医院支付
        result.put("bka825",bka825.toString());//全自费费用
        result.put("bka826",bka826.toString());//部分自费费用
        result.put("bka840",bka840.toString());//其他基金支付
        result.put("bka841",bka841.toString());//单位支付
        result.put("bka843",bka843.toString());//特惠保
        result.put("ake026",ake026.toString());//企业补充医疗保险基金支付
        result.put("ake029",ake029.toString());//大额医疗费用补助基金支付
        result.put("ake035",ake035.toString());//公务员医疗补助基金支付
        result.put("ake039",ake039.toString());//医疗保险统筹基金支付
        result.put("bka801",bka801.toString());//床位费超额金额
        result.put("bka821",bka821.toString());//民政救助金支付
        result.put("akb066",akb066.toString());//个人账户支付
        return result;
    }

    /**
     * @Menthod bizh120106
     * @Desrciption 出院结算
     * @param param 必传值：hospCode:医院编码、visitId:就诊id、insureRegCode:医保编码
     * @Author Ou·Mr
     * @Date 2020/11/5 10:56
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> bizh120106(HashMap<String,Object> param){
        String hospCode = (String) param.get("hospCode");
        String visitId = (String) param.get("visitId");
        InptVisitDTO inptVisitDTO = (InptVisitDTO) param.get("inptVisit");
        InsureIndividualVisitDTO insureIndividualVisitDTO = (InsureIndividualVisitDTO) param.get("insureIndividualVisit");
        List<InptBabyDTO> inptBabyDTOS = new ArrayList<InptBabyDTO>();
        int ts = 0;
        String cszh = "";
        if (Constant.Xiangtan.DEFAULTVAL.SY_AKA130.equals(insureIndividualVisitDTO.getAka130())
                && Constant.Xiangtan.DEFAULTVAL.SY_BKA006.equals(insureIndividualVisitDTO.getBka006())){
            //查询生育信息
            Map<String,Object> inptBabyParam = new HashMap<String,Object>();
            inptBabyParam.put("hospCode",hospCode);
            InptBabyDTO inptBabyDTO = new InptBabyDTO();
            inptBabyDTO.setHospCode(hospCode);//医院编码
            inptBabyDTO.setVisitId(visitId);//就诊id
            inptBabyParam.put("inptBabyDTO",inptBabyDTO);
            inptBabyDTOS = inptBabyService.findByCondition(inptBabyParam);
            if (inptBabyDTOS != null && !inptBabyDTOS.isEmpty()){
                for (InptBabyDTO inptBabyDTO1 : inptBabyDTOS){
                    ++ts;
                    cszh += inptBabyDTO1.getId();
                    if (ts != inptBabyDTOS.size()){
                        cszh += " ";
                    }
                }
            }
        }
        Map<String,Object> httpParam = new HashMap<String,Object>();
        httpParam.put("function_id",Constant.Xiangtan.INPT.BIZH120106);
        httpParam.put("akb020",insureIndividualVisitDTO.getMedicineOrgCode());//医疗机构编码
        httpParam.put("aaz217",insureIndividualVisitDTO.getMedicalRegNo());//就医登记号
        httpParam.put("bka046",inptVisitDTO.getOutOperId());//完成人工号
        httpParam.put("bka047",inptVisitDTO.getOutOperName());//完成人
        httpParam.put("akc196",inptVisitDTO.getOutDiseaseIcd10());//出院诊断疾病编码
        httpParam.put("bkz102",inptVisitDTO.getOutDiseaseName());//出院诊断疾病名称
        httpParam.put("ake021",inptVisitDTO.getZzDoctorName());//诊断医生
        httpParam.put("aae031",DateUtils.format(inptVisitDTO.getOutTime(),DateUtils.YMD));//业务结束时间
        httpParam.put("aae030",DateUtils.format(inptVisitDTO.getInTime(),DateUtils.YMD));//业务开始时间
        httpParam.put("bka066",Constant.Xiangtan.DEFAULTVAL.BKA066.get(inptVisitDTO.getOutSituationCode()));//出院转归情况（A-治愈 B-好转 C-无效 D-未愈 E-死亡 F-其它）
        httpParam.put("aae240",null);//使用个帐金额
        httpParam.put("aka242","0");//是否医保电子凭证刷卡
        //判断是否是单病种
        /*if (Constant.Xiangtan.DEFAULTVAL.DBZ_BKA006.equals(insureIndividualVisitDTO.getBka006())){
            String icdCodeStr = insureIndividualVisitDTO.getVisitIcdCode();
            String visitIcdCode = StringUtils.isEmpty(icdCodeStr) ? null : icdCodeStr.split(",")[0];
            httpParam.put("ake025","1");
        }*/
        if (ts > 0){
            httpParam.put("amc026",null);//生育类别（1-正常产 2-未满4个月终止妊娠 3-4个月以上终止妊娠 4- 难产 5- 产前检查）
            httpParam.put("amc020",null);//手术日期 （格式yyyyMMdd）
            httpParam.put("amc031",null);//胎次
            httpParam.put("amc028",String.valueOf(ts));//胎儿数（胎儿数必须与出生证号数相等）
            httpParam.put("amc022",cszh);//出生证号（多个出生证号使用空格隔开）
            httpParam.put("ake025",null);//手术方式（码值详见ka14.xls,取ka14中aka127所对应的值）
            httpParam.put("bmc030",null);//妊娠周期
        }
        Map<String,Object> httpResult = requestInsure.call((String)param.get("hospCode"),(String) param.get("insureRegCode"),httpParam);
        httpResult.put("bka831",MapUtils.get(httpResult,"akb067")); // 个人自付 = 个人现金支付
        return httpResult;
    }

    /**
     * @Menthod remote_bizc131256
     * @Desrciption 异地就医住院出院结算
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/5 11:25
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> remote_bizc131256(HashMap<String,Object> param) {
        InptVisitDTO inptVisitDTO = (InptVisitDTO) param.get("inptVisit");
        InsureIndividualVisitDTO insureIndividualVisitDTO = (InsureIndividualVisitDTO) param.get("insureIndividualVisit");
        Map<String,Object> httpParam = new HashMap<String,Object>();
        httpParam.put("function_id", Constant.Xiangtan.INPT.REMOTE_BIZC131256);
        httpParam.put("bkm100", Constant.Xiangtan.DICT.BCBZ_YDJS);//保存标志（只能填：3）
        httpParam.put("akb020", insureIndividualVisitDTO.getMedicineOrgCode());//医疗机构编码
        httpParam.put("aaz217", insureIndividualVisitDTO.getMedicalRegNo());//就医登记号
        httpParam.put("aac001", insureIndividualVisitDTO.getAac001());//个人电脑号
        httpParam.put("akc252", null);//本次业务个人帐户可用金额（如果为空，默认使用个人帐户。如果不为空就录入可使用的个人帐户值，如果不使用输入0）
        httpParam.put("akc196", inptVisitDTO.getOutDiseaseIcd10());//出院疾病（中心疾病编码）
        httpParam.put("bkz102", inptVisitDTO.getOutDiseaseName());//出院诊断名称（中心疾病名称）
        httpParam.put("aae031", DateUtils.format(inptVisitDTO.getOutTime(),DateUtils.Y_M_DH_M_S));//出院日期（格式：YYYY-MM-DD HH24:MI:SS(24小时)）
        httpParam.put("akc188", null);//第一副诊断（中心疾病编码）
        httpParam.put("akc189", null);//第二副诊断（中心疾病编码）
        httpParam.put("bka066", Constant.Xiangtan.DEFAULTVAL.BKA066.get(inptVisitDTO.getOutSituationCode()));//出院详情（好转、其他、死亡、未愈、无效、治愈、转科、转院（病人要求）、转院（医院要求））
        httpParam.put("aae014", inptVisitDTO.getOutOperId());//操作员工号
        httpParam.put("bka034", inptVisitDTO.getOutOperName());//操作员姓名
        httpParam.put("bka006", insureIndividualVisitDTO.getBka006());//待遇类别
        httpParam.put("baz205", inptVisitDTO.getInNo());//单据号
        httpParam.put("bka016", null);//生育就诊类型（生育住院不能为空，D：定额，P：普通消费 T：特殊情况（只有省直需要传此参数））
        httpParam.put("bka018", null);//生育疾病类型（生育住院不能为空（见*说明1），（只有省直需要传此参数））
        httpParam.put("aaz267", null);//特殊情况对应的申请序号（生育住院为T（特殊情况）时不能为空，获取对应参数值参见：BIZC131275（只有省直需要传此参数））
        httpParam.put("bke550", null);//卡识别码（跨省异地就医业务必传卡识别码）
        Map<String, Object> httpResult = requestInsure.call((String) param.get("hospCode"), (String) param.get("insureRegCode"), httpParam);
        return httpResult;
    }


    /**
     * @Menthod remote_bizc200101
     * @Desrciption 提取异地住院业务结算信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/5 14:18
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> remote_bizc200101(HashMap<String,Object> param){
        String hospCode = (String) param.get("hospCode");
        String visitId = (String) param.get("visitId");
        //查询住院患者信息
        Map<String,Object> inptVisitParam = new HashMap<String,Object>();
        inptVisitParam.put("hospCode",hospCode);
        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        inptVisitDTO.setHospCode(hospCode);
        inptVisitDTO.setId(visitId);
        inptVisitParam.put("inptVisitDTO",inptVisitDTO);
        inptVisitDTO = inptVisitService.getInptVisitById(inptVisitParam).getData();
        if (inptVisitDTO == null){
            throw new AppException("未查询到住院患者信息。");
        }
        param.put("function_id",Constant.Xiangtan.INPT.REMOTE_BIZC200101);
        param.put("akb020",inptVisitDTO.getMedicineOrgCode());//医疗机构编码
        param.put("aaz217",inptVisitDTO.getInjuryBorthSn());//业务序列号
        param.put("akc190",inptVisitDTO.getMedicalRegNo());//住院号
        Map<String,Object> httpResult = requestInsure.call((String)param.get("hospCode"),(String) param.get("insureRegCode"),param);
        return httpResult;
    }


    /**
     * @Method bizh120103
     * @Desrciption 入院登记
     * @params [insureInptRegisterDTO]
     * @Author 廖继广
     * @Date 2020/10/29 11:41
     * @Return java.util.Map
     */
    public Map<String, Object> bizh120103(InsureInptRegisterDTO insureInptRegisterDTO) {
        Map<String,Object> httpParam = new HashMap();
        httpParam.put("function_id", Constant.Xiangtan.INPT.BIZH120103);
        httpParam.put("aaa027",insureInptRegisterDTO.getAaa027());
        httpParam.put("aaa129",insureInptRegisterDTO.getAaa129());
        httpParam.put("aab001",insureInptRegisterDTO.getAab001());
        httpParam.put("aab019",insureInptRegisterDTO.getAab019());
        httpParam.put("aab999",insureInptRegisterDTO.getAab999());
        httpParam.put("aac001",insureInptRegisterDTO.getAac001());
        httpParam.put("aac002",insureInptRegisterDTO.getAac002());
        httpParam.put("aaa003",insureInptRegisterDTO.getAac003());
        httpParam.put("aaa004",insureInptRegisterDTO.getAac004());
        httpParam.put("aac006",insureInptRegisterDTO.getAac006());
        httpParam.put("aac148",insureInptRegisterDTO.getAac148());
        httpParam.put("aae004",insureInptRegisterDTO.getAae004());
        httpParam.put("aae005",insureInptRegisterDTO.getAae005());
        httpParam.put("aae006",insureInptRegisterDTO.getAae006());
        httpParam.put("aae011",insureInptRegisterDTO.getAae011());
        httpParam.put("aae030",insureInptRegisterDTO.getAae030());
        httpParam.put("aae140",insureInptRegisterDTO.getAae140());
        httpParam.put("aka130",insureInptRegisterDTO.getAka130());
        httpParam.put("akb020",insureInptRegisterDTO.getAkb020());
        httpParam.put("akc190",insureInptRegisterDTO.getAkc190());
        httpParam.put("akc193",insureInptRegisterDTO.getAkc193());
        httpParam.put("ake020",insureInptRegisterDTO.getAke020());
        httpParam.put("ake022",insureInptRegisterDTO.getAke022());
        httpParam.put("aae024",insureInptRegisterDTO.getAke024());
        httpParam.put("akf001",insureInptRegisterDTO.getAkf001());
        httpParam.put("baa027",insureInptRegisterDTO.getBaa027());
        httpParam.put("bac001",insureInptRegisterDTO.getBac001());
        httpParam.put("bka006",insureInptRegisterDTO.getBka006());
        httpParam.put("bka008",insureInptRegisterDTO.getBka008());
        httpParam.put("bka015",insureInptRegisterDTO.getBka015());
        httpParam.put("bka020",insureInptRegisterDTO.getBka020());
        httpParam.put("bka021",insureInptRegisterDTO.getBka021());
        httpParam.put("bka022",insureInptRegisterDTO.getBka022());
        httpParam.put("bka035",insureInptRegisterDTO.getBka035());
        httpParam.put("bka061",insureInptRegisterDTO.getBka061());
        httpParam.put("bka245",insureInptRegisterDTO.getBka245());
        httpParam.put("diagnoseinfos",insureInptRegisterDTO.getDiagnoseinfos());

        /*调用医保统一访问接口*/
        Map<String,Object> resultMap = requestInsure.call(insureInptRegisterDTO.getHospCode(),insureInptRegisterDTO.getInsureOrgCode(),httpParam);

        /* 返回结果集处理 */
        Integer returnCode = Integer.valueOf(resultMap.get("return_code").toString());
        if (returnCode < 0) {
            throw new AppException("入院登记失败,远程调用号（" + Constant.FUNCTION.BIZH120103 + "）:【 " + resultMap.get("return_code_message") + "】");
        }
        return resultMap;
    }

    /**
     * @Method bizh120104
     * @Desrciption 修改入院登记
     * @params [insureInptOutFeeDTO]
     * @Author 廖继广
     * @Date 2020/10/29 11:41
     * @Return Boolean
     **/
    public Boolean bizh120104(InsureInptRegisterDTO insureInptRegisterDTO) {
        Map<String, Object> httpParam = new HashMap();
        httpParam.put("function_id", Constant.Xiangtan.INPT.BIZH120104);
        httpParam.put("aae006", insureInptRegisterDTO.getAae006());
        httpParam.put("aae030", insureInptRegisterDTO.getAae030());
        httpParam.put("aae036", insureInptRegisterDTO.getAae036());
        httpParam.put("aaz217", insureInptRegisterDTO.getAaz217());
        httpParam.put("akb020", insureInptRegisterDTO.getAkb020());
        httpParam.put("akc190", insureInptRegisterDTO.getAkc190());
        httpParam.put("akc193", insureInptRegisterDTO.getAkc193());
        httpParam.put("ake020", insureInptRegisterDTO.getAke020());
        httpParam.put("ake022", insureInptRegisterDTO.getAke022());
        httpParam.put("ake024", insureInptRegisterDTO.getAke024());
        httpParam.put("akf001", insureInptRegisterDTO.getAkf001());
        httpParam.put("bka020", insureInptRegisterDTO.getBka020());
        httpParam.put("bka021", insureInptRegisterDTO.getBka021());
        httpParam.put("bka022", insureInptRegisterDTO.getBka022());
        httpParam.put("bkz101", insureInptRegisterDTO.getBkz101());
        httpParam.put("diagnoseinfo", insureInptRegisterDTO.getDiagnoseinfos());

        /*调用医保统一访问接口*/
        Map<String,Object> resultMap = requestInsure.call(insureInptRegisterDTO.getHospCode(),insureInptRegisterDTO.getInsureOrgCode(),httpParam);

        /* 返回结果集处理 */
        Integer returnCode = Integer.valueOf(resultMap.get("return_code").toString());
        if (returnCode < 0) {
            throw new AppException("修改入院登记信息失败,远程调用号（" + Constant.FUNCTION.BIZH120104 + "）:【 " + resultMap.get("return_code_message") + "】");
        }
        return true;
    }

    /**
     * @Method bizh120109
     * @Desrciption 取消入院登记
     * @params [insureInptOutFeeDTO]
     * @Author 廖继广
     * @Date 2020/10/29 11:41
     * @Return java.util.Map
     **/
    public Boolean bizh120109(InsureInptOutFeeDTO insureInptOutFeeDTO) {
        Map<String,Object> httpParam = new HashMap();
        httpParam.put("function_id",Constant.Xiangtan.INPT.BIZH120109);
        httpParam.put("akb020",insureInptOutFeeDTO.getAkb020());
        httpParam.put("aaz217",insureInptOutFeeDTO.getAaz217());

        /*调用医保统一访问接口*/
        Map<String,Object> resultMap = requestInsure.call(insureInptOutFeeDTO.getHospCode(),insureInptOutFeeDTO.getInsureOrgCode(),httpParam);

        /* 返回结果集处理 */
        Integer returnCode = Integer.valueOf(resultMap.get("return_code").toString());
        if (returnCode < 0) {
            throw new AppException("取消入院登记失败,远程调用号（" +Constant.FUNCTION.BIZH120109 + "）:【 " + resultMap.get("return_code_message") + "】");
        }
        return true;
    }

    /**
     * @Method Remote_BIZC131204
     * @Desrciption 异地医保入院登记
     * @params [insureRemoteInptRegisterDTO]
     * @Author 廖继广
     * @Date 2020/10/30 11:41
     * @Return java.util.Map
     **/
    public Map<String,Object> Remote_BIZC131204(InsureRemoteInptRegisterDTO insureRemoteInptRegisterDTO) {
        Map<String, Object> httpParam = new HashMap<>();
        httpParam.put("function_id", Constant.Xiangtan.INPT.REMOTE_BIZC131204);
        httpParam.put("aac001",insureRemoteInptRegisterDTO.getAac001()); // 个人电脑号
        httpParam.put("aaa027",insureRemoteInptRegisterDTO.getAaa027()); // 参保人行政编码
        httpParam.put("akb020",insureRemoteInptRegisterDTO.getAkb020()); // 医保中心编码
        httpParam.put("aac002",insureRemoteInptRegisterDTO.getAac002()); // 社会保障号
        httpParam.put("aka130",insureRemoteInptRegisterDTO.getAka130()); // 业务类型
        httpParam.put("bka006",insureRemoteInptRegisterDTO.getBka006()); // 待遇类型
        httpParam.put("aae030",insureRemoteInptRegisterDTO.getAae030()); // 入院日期
        httpParam.put("aae011",insureRemoteInptRegisterDTO.getAae011()); // 登记人工号
        httpParam.put("bka015",insureRemoteInptRegisterDTO.getBka015()); // 登记人姓名
        httpParam.put("bka021",insureRemoteInptRegisterDTO.getBka021()); // 病区编码
        httpParam.put("bka022",insureRemoteInptRegisterDTO.getBka022()); // 入院病区名称
        httpParam.put("akf001",insureRemoteInptRegisterDTO.getAkf001()); // 入院科室编号
        httpParam.put("bka020",insureRemoteInptRegisterDTO.getBka020()); // 科室名称
        httpParam.put("akc193",insureRemoteInptRegisterDTO.getAkc193()); // 入院疾病诊断（icd码）
        httpParam.put("ake020",insureRemoteInptRegisterDTO.getAke020()); // 入院病床编号
        httpParam.put("akc190",insureRemoteInptRegisterDTO.getAkc190()); // 住院号
        httpParam.put("bka245",insureRemoteInptRegisterDTO.getBka245()); // 预付款总额
        httpParam.put("aae005",insureRemoteInptRegisterDTO.getAae005()); // 联系电话
        httpParam.put("bka036",insureRemoteInptRegisterDTO.getBka036()); // 是否读卡

        /*调用医保统一访问接口*/
        Map<String,Object> resultMap = requestInsure.call(insureRemoteInptRegisterDTO.getHospCode(),insureRemoteInptRegisterDTO.getAkb020(),httpParam);
        List<Map<String,Object>> bizinfoList = (ArrayList)resultMap.get("bizinfo");
        return bizinfoList.get(0);
    }

    /**
     * @Method Remote_BIZC131206
     * @Desrciption 取消异地医保入院登记
     * @params [insureRemoteInptRegisterDTO]
     * @Author 廖继广
     * @Date 2020/10/30 14:46
     * @Return Boolean
     **/
    public Boolean Remote_BIZC131206(InsureRemoteInptRegisterDTO insureRemoteInptRegisterDTO) {
        Map<String, Object> httpParam = new HashMap<>();
        httpParam.put("function_id", Constant.Xiangtan.INPT.REMOTE_BIZC131206);
        httpParam.put("akb020", insureRemoteInptRegisterDTO.getAkb020());
        httpParam.put("aaz217", insureRemoteInptRegisterDTO.getAaz217());
        httpParam.put("aae011", insureRemoteInptRegisterDTO.getAae011());
        httpParam.put("bka015", insureRemoteInptRegisterDTO.getBka015());
        httpParam.put("bke550", insureRemoteInptRegisterDTO.getCardIden());

        /*调用医保统一访问接口*/
        Map<String,Object> resultMap = requestInsure.call(insureRemoteInptRegisterDTO.getHospCode(),insureRemoteInptRegisterDTO.getInsureOrgCode(),httpParam);
        return true;
    }

    /**
     * @Method bizh120107
     * @Desrciption 医保取消出院结算
     * @param param
     * @Author 廖继广
     * @Date   2020/10/29 09:56
     * @Return java.util.Map
     **/
    public Boolean bizh120107(HashMap<String,Object> param) {
        String hospCode = (String) param.get("hospCode");//医院编码
        String insureOrgCode = (String) param.get("insureOrgCode");//医保编码
        InsureIndividualVisitDTO insureIndividualVisit = (InsureIndividualVisitDTO) param.get("insureIndividualVisit");
        Map<String,Object> httpParam = new HashMap();
        httpParam.put("function_id",Constant.Xiangtan.INPT.BIZH120107);
        httpParam.put("aaz217",insureIndividualVisit.getMedicalRegNo());
        httpParam.put("akb020",insureIndividualVisit.getMedicineOrgCode());
        /*调用医保统一访问接口*/
        Map<String,Object> resultMap = requestInsure.call(hospCode,insureOrgCode,httpParam);
        return true;
    }

    /**
     * @Method Remote_BIZC131259
     * @Desrciption 医保取消异地出院结算
     * @param param
     * @Author 廖继广
     * @Date   2020/10/29 09:56
     * @Return java.lang.Boolean
     **/
    public Boolean Remote_BIZC131259(HashMap<String,Object> param) {
        String hospCode = (String) param.get("hospCode");//医院编码
        String insureOrgCode = (String) param.get("insureOrgCode");//医保编码
        String userName = (String) param.get("userName");//操作人姓名
        InsureIndividualVisitDTO insureIndividualVisit = (InsureIndividualVisitDTO) param.get("insureIndividualVisit");
        Map<String,Object> httpParam = new HashMap();
        httpParam.put("function_id",Constant.Xiangtan.INPT.REMOTE_BIZC131259);
        httpParam.put("aaz217",insureIndividualVisit.getMedicalRegNo());
        httpParam.put("akb020",insureIndividualVisit.getMedicineOrgCode());
        httpParam.put("aac001",insureIndividualVisit.getAac001());
        httpParam.put("aae014",userName);
        httpParam.put("bka006",insureIndividualVisit.getBka006());
        httpParam.put("bke550",null);
        httpParam.put("bkm100","3");
        /*调用医保统一访问接口*/
        Map<String,Object> resultMap = requestInsure.call(hospCode,insureOrgCode,httpParam);
        return true;
    }

    /**
     * @Method getIndividualInpatient
     * @Param [insureIndividualInpatientDTO]
     * @description   获取住院业务信息
     * @author marong
     * @date 2020/11/5 17:15
     * @return cn.hsa.module.insure.insureIndividualInpatient.dto.InsureIndividualInpatientDTO
     * @throws
     */
    public InsureIndividualInpatientDTO bizh120102(InsureIndividualInpatientDTO insureIndividualInpatientDTO) {
        /* 获取病人类型 */
        String patientType = insureIndividualInpatientDTO.getPatientType();

        /*获取医保配置参数*/
        List<InsureConfigurationDTO> insureConfigurationDTOS = queryInsureConfig(insureIndividualInpatientDTO.getHospCode());

        /*拼装入参*/
        Map<String,Object> paramIn = new HashMap();
        paramIn.put("function_id", Constant.Xiangtan.INPT.BIZH120102);
        paramIn.put("aae030",insureIndividualInpatientDTO.getAae030());
        paramIn.put("aae031",insureIndividualInpatientDTO.getAae031());
        paramIn.put("aka130",insureIndividualInpatientDTO.getAka130());
        paramIn.put("akb020",insureIndividualInpatientDTO.getAkb020());
        paramIn.put("bka891",insureIndividualInpatientDTO.getBka891());
        paramIn.put("bka895",insureIndividualInpatientDTO.getBka895());
        paramIn.put("bka896",insureIndividualInpatientDTO.getBka896());
        paramIn.put("bka977",insureIndividualInpatientDTO.getBka977());

        Map<String, Object> resultMap = requestInsure.call(insureIndividualInpatientDTO.getHospCode(),insureConfigurationDTOS.get(0).getRegCode(),paramIn);
        Integer returnCode = Integer.valueOf(resultMap.get("return_code").toString());
        if (returnCode < 0) {
            throw new AppException("获取住院业务信息失败,远程调用号（" + Constant.FUNCTION.BIZH120102 + "）:【 " + resultMap.get("msg") + "】");
        }
        /*处理参数*/
        insureIndividualInpatientDTO = getIndividualInpatientProcessingData(resultMap,insureIndividualInpatientDTO);
        return insureIndividualInpatientDTO;
    }

    /**
     * @Method getIndividualInpatientByRemote
     * @Param [insureIndividualInpatientDTO]
     * @description   获取异地住院业务信息
     * @author marong
     * @date 2020/11/5 17:18
     * @return cn.hsa.module.insure.insureIndividualInpatient.dto.InsureIndividualInpatientDTO
     * @throws
     */
    public InsureIndividualInpatientDTO remote_bizc131251(InsureIndividualInpatientDTO insureIndividualInpatientDTO) {
        /* 获取病人类型 */
        String patientType = insureIndividualInpatientDTO.getPatientType();

        /*获取医保配置参数*/
        List<InsureConfigurationDTO> insureConfigurationDTOS = queryInsureConfig(insureIndividualInpatientDTO.getHospCode());

        /*拼装入参   aaz217:就医登记号     查询业务*/
        Map<String,Object> paramIn = new HashMap();
        paramIn.put("function_id",Constant.Xiangtan.INPT.REMOTE_BIZC131251);
        paramIn.put("aaz217",insureIndividualInpatientDTO.getAaz217());
        paramIn.put("akb020 ",insureIndividualInpatientDTO.getAkb020());
        paramIn.put("aka130",insureIndividualInpatientDTO.getAka130());

        Map<String, Object> resultMap = requestInsure.call(insureIndividualInpatientDTO.getHospCode(),insureConfigurationDTOS.get(0).getRegCode(),paramIn);
        Integer returnCode = Integer.valueOf(resultMap.get("return_code").toString());
        InsureIndividualInpatientDTO bizinfoDTO = new InsureIndividualInpatientDTO();
        if (returnCode < 0) {
            throw new AppException("住院获取业务信息失败,远程调用号（" + Constant.FUNCTION.REMOTE_BIZC131251 + "）:【 " + resultMap.get("msg") + "】");
        }
        if(!MapUtils.isEmpty(resultMap.get("bizinfo"))){
            Map<String,Object> bizinfo  = (Map<String, Object>) resultMap.get("bizinfo");
            bizinfoDTO = JSON.parseObject(JSON.toJSONString(bizinfo), InsureIndividualInpatientDTO.class);
        };
        return bizinfoDTO;
    }

    /**
     * @Method getPatientsInfoByInpt
     * @Param [insureIndividualBasicDTO]
     * @description   住院获取个人信息,  inputType 前端必填 本地还是异地  01 本地   02 异地
     * @author marong
     * @date 2020/11/5 17:21
     * @return java.util.List<cn.hsa.module.insure.insureIndividualBasic.dto.InsureIndividualBasicDTO>
     * @throws
     *
     *  @update by liaojiguang on 2020-11-19
     *
     */
    public Map<String,Object> bizh120001(InsureIndividualBasicDTO insureIndividualBasicDTO) {

        Map<String,Object> httpParam = new HashMap();
        Map<String, Object> resultMap = new HashMap<String,Object>();

        /*获取医保配置参数*/
        List<InsureConfigurationDTO> insureConfigurationDTOS = queryInsureConfig(insureIndividualBasicDTO.getHospCode());

        // DOTO 默认本地医保
        String isRemote = insureIndividualBasicDTO.getIsRemote();

        // DOTO 医保机构编码
        insureIndividualBasicDTO.setAkb020(insureIndividualBasicDTO.getInsureRegCode());
        if(Constants.SF.F.equals(isRemote)) {
            httpParam.put("function_id",Constant.Xiangtan.INPT.BIZH120001);
            httpParam.put("baa027",insureIndividualBasicDTO.getBaa027());
            httpParam.put("bka895",insureIndividualBasicDTO.getBka895());
            httpParam.put("bka896",insureIndividualBasicDTO.getBka896());
            httpParam.put("bka920",insureIndividualBasicDTO.getBka920());
            httpParam.put("akb020",insureIndividualBasicDTO.getAkb020());
            httpParam.put("aka130",insureIndividualBasicDTO.getAka130());
            httpParam.put("aae030",insureIndividualBasicDTO.getAae030());
            httpParam.put("bka006",insureIndividualBasicDTO.getBka006());
            httpParam.put("aaz507",insureIndividualBasicDTO.getAaz507());
            resultMap = requestInsure.call(insureIndividualBasicDTO.getHospCode(),insureIndividualBasicDTO.getInsureRegCode(),httpParam);
        } else if(Constants.SF.S.equals(isRemote)) {
            /*拼装入参*/
            httpParam.put("function_id",Constant.Xiangtan.INPT.REMOTE_BIZC131201);
            httpParam.put("aac002",insureIndividualBasicDTO.getBka896());
            httpParam.put("akb020",insureIndividualBasicDTO.getAkb020());
            httpParam.put("aka130",insureIndividualBasicDTO.getAka130());
            httpParam.put("aaa027",insureIndividualBasicDTO.getAkb020());
            httpParam.put("bke550",insureIndividualBasicDTO.getBke550());
            httpParam.put("aac0021",insureIndividualBasicDTO.getAac0021());
            httpParam.put("serial_apply",insureIndividualBasicDTO.getSerialApply());
            resultMap = requestInsure.call(insureIndividualBasicDTO.getHospCode(),insureConfigurationDTOS.get(0).getRegCode(),httpParam);
        }

        Integer returnCode = MapUtils.isEmpty(resultMap)?-1:Integer.valueOf(resultMap.get("return_code").toString());
        if (returnCode < 0) {
            throw new AppException("住院登记获取个人信息失败,远程调用号（" + Constant.Xiangtan.INPT.REMOTE_BIZC131201  + "）:【 " + resultMap.get("msg") + "】");
        }
        return resultMap;
    }

    /**
     * @Method saveCostByRemoteInpt
     * @Param [insureIndividualCostDTO]
     * @description   校验保存住院费用明细信息（Remote_BIZC131252）
     * @author marong
     * @date 2020/11/5 17:27
     * @return java.lang.Boolean
     * @throws
     */
    public Boolean remote_bizc131252(InsureIndividualCostDTO insureIndividualCostDTO) {

        /*获取医保配置参数*/
        List<InsureConfigurationDTO> insureConfigurationDTOS = queryInsureConfig(insureIndividualCostDTO.getHospCode());
        Map<String,Object> httpParam = new HashMap();
        httpParam.put("function_id", Constant.Xiangtan.INPT.REMOTE_BIZC131252);
        httpParam.put("aac001 ",insureIndividualCostDTO.getAac001 ());
        httpParam.put("aka130",insureIndividualCostDTO.getAka130());
        httpParam.put("aaz217",insureIndividualCostDTO.getAaz217());
        httpParam.put("bka063",insureIndividualCostDTO.getBka063());
        httpParam.put("bka064",insureIndividualCostDTO.getBka064());
        httpParam.put("bka070",insureIndividualCostDTO.getBka070());
        httpParam.put("bka074",insureIndividualCostDTO.getBka074());
        httpParam.put("bka075",insureIndividualCostDTO.getBka075());
        Map<String,String> feeinfoMap = new HashMap();
        feeinfoMap.put("ake003",insureIndividualCostDTO.getAke003());
        feeinfoMap.put("aka063",insureIndividualCostDTO.getAka063());
        feeinfoMap.put("ake005",insureIndividualCostDTO.getAke005());
        feeinfoMap.put("ake001",insureIndividualCostDTO.getAke001());
        feeinfoMap.put("ake006",insureIndividualCostDTO.getAke006());
        feeinfoMap.put("aka070",insureIndividualCostDTO.getAka070());
        feeinfoMap.put("bka073",insureIndividualCostDTO.getBka073());
        feeinfoMap.put("aka074",insureIndividualCostDTO.getAka074());
        feeinfoMap.put("ake007",insureIndividualCostDTO.getAke007());
        feeinfoMap.put("aka067",insureIndividualCostDTO.getAka067());
        feeinfoMap.put("bka040",insureIndividualCostDTO.getBka040());
        feeinfoMap.put("akc226",insureIndividualCostDTO.getAkc226());
        feeinfoMap.put("aae019",insureIndividualCostDTO.getAae019());
        feeinfoMap.put("bkz103",insureIndividualCostDTO.getBkz103());
        feeinfoMap.put("bka061",insureIndividualCostDTO.getBka061());
        feeinfoMap.put("bka062",insureIndividualCostDTO.getBka062());
        feeinfoMap.put("aaz213",insureIndividualCostDTO.getAaz213());
        feeinfoMap.put("aae013",insureIndividualCostDTO.getAae013());
        feeinfoMap.put("bka063",insureIndividualCostDTO.getBka063());
        feeinfoMap.put("bka064",insureIndividualCostDTO.getBka064());
        httpParam.put("feeinfo",feeinfoMap);
        /*调用医保统一访问接口*/
        Map<String,Object> resultMap = requestInsure.call(insureIndividualCostDTO.getHospCode(),insureConfigurationDTOS.get(0).getOrgCode(),httpParam);

        /* 返回结果集处理 */
        Integer returnCode = Integer.valueOf(resultMap.get("return_code").toString());
        if (returnCode < 0) {
            throw new AppException("住院费用传输失败,远程调用号（" + Constant.FUNCTION.REMOTE_BIZC131252 + "）:【 " + resultMap.get("msg") + "】");
        }
        return true;
    }


    /**
     * @Menthod bizh120004
     * @Desrciption 删除住院业务费用明细
     * @param param 请求参数（医保机构编码、医保登记号）
     * @Author Ou·Mr
     * @Date 2020/12/22 21:11 
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> bizh120004(HashMap<String,Object> param) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = (InsureIndividualVisitDTO) param.get("insureIndividualVisitDTO");
        /*获取医保配置参数*/
        String hospCode = insureIndividualVisitDTO.getHospCode();//医院编码
        String insureOrgCode = insureIndividualVisitDTO.getInsureOrgCode();//医保机构编码
        String medicalRegNo = insureIndividualVisitDTO.getMedicalRegNo();//医保登记号
        if (StringUtils.isEmpty(hospCode) || StringUtils.isEmpty(insureOrgCode) || StringUtils.isEmpty(medicalRegNo)){
            throw new AppException("参数错误。");
        }
        Map<String,Object> httpParam = new HashMap<String,Object>();
        httpParam.put("function_id", Constant.Xiangtan.INPT.BIZH120004);
        httpParam.put("akb020", insureOrgCode);
        httpParam.put("aaz217", medicalRegNo);
        return requestInsure.call(hospCode,insureOrgCode,httpParam);
    }

    /**
     * @Menthod remote_bizc131274
     * @Desrciption 删除异地就医住院业务上传明细
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/1/5 14:10
     * @Return java.lang.Boolean
     */
    public Boolean remote_bizc131274(HashMap<String,Object> param) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = (InsureIndividualVisitDTO) param.get("insureIndividualVisitDTO");
        InsureIndividualBasicDTO insureIndividualBasicDTO = (InsureIndividualBasicDTO) param.get("insureIndividualBasicDTO");
        String hospCode = insureIndividualVisitDTO.getHospCode();//医院编码
        String insureOrgCode = insureIndividualVisitDTO.getInsureOrgCode();//医保机构编码
        String medicalRegNo = insureIndividualVisitDTO.getMedicalRegNo();//医保登记号
        if (StringUtils.isEmpty(hospCode) || StringUtils.isEmpty(insureOrgCode) || StringUtils.isEmpty(medicalRegNo)){
            throw new AppException("参数错误。");
        }
        Map<String,Object> httpParam = new HashMap<String,Object>();
        httpParam.put("function_id",Constant.Xiangtan.INPT.REMOTE_BIZC131274);//功能号
        httpParam.put("akb020",insureOrgCode);//医疗机构编码
        httpParam.put("aaz217",medicalRegNo);//就医登记号
        httpParam.put("bka015",insureIndividualBasicDTO.getCrteName());//就诊登记名称
        httpParam.put("aae011",insureIndividualBasicDTO.getCrteId());//就诊登记工号
        httpParam.put("aaa027",insureIndividualBasicDTO.getAaa027());//医院所属中心编码
        Map<String, Object> resultMap = requestInsure.call(hospCode,insureOrgCode,httpParam);
        return true;
    }


    private List<InsureConfigurationDTO> queryInsureConfig(String  hospCode) {
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        return  insureConfigurationDAO.queryAll(insureConfigurationDTO);
    }

    private InsureIndividualInpatientDTO getIndividualInpatientProcessingData(Map<String, Object> resultMap,InsureIndividualInpatientDTO insureIndividualInpatientDTO) {
        Map<String,Object> bizinfoMap = (Map<String, Object>) resultMap.get("bizinfo");
        if(MapUtils.isEmpty(bizinfoMap)){
            throw new AppException("获取业务信息出错。");
        }
        insureIndividualInpatientDTO.setAaa027(MapUtils.get(bizinfoMap,"aaa027",""));
        insureIndividualInpatientDTO.setAab001(MapUtils.get(bizinfoMap,"aab001",""));
        insureIndividualInpatientDTO.setAab019(MapUtils.get(bizinfoMap,"aab019",""));
        insureIndividualInpatientDTO.setAac001(MapUtils.get(bizinfoMap,"aac001",""));
        insureIndividualInpatientDTO.setAac002(MapUtils.get(bizinfoMap,"aac002",""));
        insureIndividualInpatientDTO.setAac003(MapUtils.get(bizinfoMap,"aac003",""));
        insureIndividualInpatientDTO.setAac004(MapUtils.get(bizinfoMap,"aac004",""));
        insureIndividualInpatientDTO.setAac006(DateUtil.stringToDate((String)bizinfoMap.get("aac006"),"yyyy-MM-dd"));
        insureIndividualInpatientDTO.setAac148(MapUtils.get(bizinfoMap,"aac148",""));
        insureIndividualInpatientDTO.setAae004(MapUtils.get(bizinfoMap,"aae004",""));
        insureIndividualInpatientDTO.setAae005(MapUtils.get(bizinfoMap,"aae005",""));
        insureIndividualInpatientDTO.setAae006(MapUtils.get(bizinfoMap,"aae006",""));
        insureIndividualInpatientDTO.setAae030(DateUtil.stringToDate((String)bizinfoMap.get("aae030"),"yyyy-MM-dd"));
        insureIndividualInpatientDTO.setAae031(DateUtil.stringToDate((String)bizinfoMap.get("aae031"),"yyyy-MM-dd"));
        insureIndividualInpatientDTO.setAae036(DateUtil.stringToDate((String)bizinfoMap.get("aae036"),"yyyy-MM-dd"));
        insureIndividualInpatientDTO.setAae140(MapUtils.get(bizinfoMap,"aae140",""));
        insureIndividualInpatientDTO.setAaz217(MapUtils.get(bizinfoMap,"aaz217",""));
        insureIndividualInpatientDTO.setAaz267(MapUtils.get(bizinfoMap,"aaz267",""));
        insureIndividualInpatientDTO.setAka030(MapUtils.get(bizinfoMap,"aka030",""));
        insureIndividualInpatientDTO.setAka101(MapUtils.get(bizinfoMap,"aka101",""));
        insureIndividualInpatientDTO.setAka130(MapUtils.get(bizinfoMap,"aka130",""));
        insureIndividualInpatientDTO.setAkb020(MapUtils.get(bizinfoMap,"akb020",""));
        insureIndividualInpatientDTO.setAkb020(MapUtils.get(bizinfoMap,"akb020",""));
        insureIndividualInpatientDTO.setAkb021(MapUtils.get(bizinfoMap,"akb021",""));
        insureIndividualInpatientDTO.setAkc190(MapUtils.get(bizinfoMap,"akc190",""));
        insureIndividualInpatientDTO.setAkc193(MapUtils.get(bizinfoMap,"akc193",""));
        insureIndividualInpatientDTO.setAke020(MapUtils.get(bizinfoMap,"ake020",""));
        insureIndividualInpatientDTO.setAke022(MapUtils.get(bizinfoMap,"ake022",""));
        insureIndividualInpatientDTO.setAke024(MapUtils.get(bizinfoMap,"ake024",""));
        insureIndividualInpatientDTO.setAkf001(MapUtils.get(bizinfoMap,"akf001",""));
        insureIndividualInpatientDTO.setBaa027(MapUtils.get(bizinfoMap,"baa027",""));
        insureIndividualInpatientDTO.setBac001(MapUtils.get(bizinfoMap,"bac001",""));
        insureIndividualInpatientDTO.setBka001(MapUtils.get(bizinfoMap,"bka001",""));
        insureIndividualInpatientDTO.setBka006(MapUtils.get(bizinfoMap,"bka006",""));
        insureIndividualInpatientDTO.setBka008(MapUtils.get(bizinfoMap,"bka008",""));
        insureIndividualInpatientDTO.setBka015(MapUtils.get(bizinfoMap,"bka015",""));
        insureIndividualInpatientDTO.setBka020(MapUtils.get(bizinfoMap,"bka020",""));
        insureIndividualInpatientDTO.setBka021(MapUtils.get(bizinfoMap,"bka021",""));
        insureIndividualInpatientDTO.setBka022(MapUtils.get(bizinfoMap,"bka022",""));
        insureIndividualInpatientDTO.setBka030(MapUtils.get(bizinfoMap,"bka030",""));
        insureIndividualInpatientDTO.setBka034(MapUtils.get(bizinfoMap,"bka034",""));
        insureIndividualInpatientDTO.setBka035(MapUtils.get(bizinfoMap,"bka035",""));
        insureIndividualInpatientDTO.setBka039(MapUtils.get(bizinfoMap,"bka039",""));
        insureIndividualInpatientDTO.setBka041(MapUtils.get(bizinfoMap,"bka041",""));
        insureIndividualInpatientDTO.setBka042(MapUtils.get(bizinfoMap,"bka042",""));
        insureIndividualInpatientDTO.setBka044(MapUtils.get(bizinfoMap,"bka044",""));
        insureIndividualInpatientDTO.setBka061(MapUtils.get(bizinfoMap,"bka061",""));
        insureIndividualInpatientDTO.setBka072(MapUtils.get(bizinfoMap,"bka072",""));
        insureIndividualInpatientDTO.setBka245(MapUtils.get(bizinfoMap,"bka245",""));
        insureIndividualInpatientDTO.setBke301(MapUtils.get(bizinfoMap,"bke301",""));
        insureIndividualInpatientDTO.setBkz101(MapUtils.get(bizinfoMap,"bkz101",""));
        insureIndividualInpatientDTO.setBacu18(MapUtils.get(bizinfoMap,"bacu18",""));

        return insureIndividualInpatientDTO;
    }

    public Map<String,Object> BIZC110118(HashMap<String,String> map){
        /*获取医保配置参数*/
        Map<String,Object> httpParam = new HashMap();
        httpParam.put("function_id",Constant.Xiangtan.MATCH.BIZC110118);
        httpParam.put("aaa027", map.get("insureRegCode"));
        httpParam.put("akb020", map.get("insureRegCode"));
        httpParam.put("first_row", map.get("firstrow"));
        httpParam.put("last_row", map.get("lastrow"));
        httpParam.put("once_find", 1);
        if("finMatchCata".equals((String)map.get("condition"))){ ;
            httpParam.put("condition", "finMatchCata");
        }else if ("disease".equals(map.get("condition"))){
            httpParam.put("condition", "disease");
        }
        Map<String,Object> resultMap = requestInsure.call(map.get("hospCode"),map.get("insureRegCode"),httpParam);
        /* 返回结果集处理 */
        Integer returnCode = Integer.valueOf(resultMap.get("return_code").toString());
        if (returnCode < 0) {
            throw new AppException("项目/疾病匹配失败,远程调用号（" + Constant.FUNCTION.BIZC110118 + "）:【 " + resultMap.get("msg") + "】");
        }
        return resultMap;
    }
    /**
     * @Method REMOTE_BIZC131251()
     * @Desrciption  异地就医费用上传
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/12 9:53
     * @Return
     **/
    public Map<String,Object> Remote_BIZC131252(InptVisitDTO inptVisitDTO){
        List<Map<String,Object>> mapList = new ArrayList<>();
        Map<String,Object> httpResult = new HashMap<>();
        httpResult.put("function_id", Constant.Xiangtan.INPT.REMOTE_BIZC131252);//交易号
        httpResult.put("aaz217",inptVisitDTO.getMedicalRegNo() );//就医登记号
        httpResult.put("akb020", inptVisitDTO.getMedicineOrgCode());//医疗机构编码
        httpResult.put("aac001", inptVisitDTO.getAac001());//个人电脑号
        httpResult.put("aka130", inptVisitDTO.getAka130());//业务类型
        httpResult.put("bka063", inptVisitDTO.getCode());//录入人工号
        httpResult.put("bka064", inptVisitDTO.getCrteName());//录入人姓名
        httpResult.put("bka070", "");//医嘱号
        httpResult.put("bka074", "");//开嘱医生编号
        httpResult.put("bka075", "");//开嘱医生姓名
        // 循环遍历 传费用上传参数值
        List<Map<String,Object>> insureCostList = inptVisitDTO.getInsureCostList();//本次传输费用信息
        for (Map<String,Object> item : insureCostList){
                        Map<String,Object> httpParamMap = new HashMap<>();
                        String itemStr =""; //项目药品类型
                        String feeType=""; //费用统计类别
                        // 说明是西药
                        if("11".equals(item.get("insureMatchItemCode"))){
                            itemStr ="1";
                            feeType="001";
                        }
                        //中成药
                        else if("12".equals(item.get("insureMatchItemCode"))){
                            itemStr ="2";
                            feeType="002";
                        }
                        //中草药
                        else if("13".equals(item.get("insureMatchItemCode"))){
                            itemStr ="3";
                            feeType="003";
                        }
                        // 说明是项目
                        else{
                            itemStr ="0";
                            feeType="000";
                        }
                        httpParamMap.put("ake003", itemStr);//项目药品类型
                        httpParamMap.put("aka063", feeType);//费用统计类别
                        httpParamMap.put("ake005", item.get("hospItemCode"));//医院药品项目编码
                        httpParamMap.put("ake001", item.get("insureItemCode"));//中心药品项目编码
                        httpParamMap.put("ake006", item.get("hospItemName"));//医院药品项目名称
                        httpParamMap.put("aka070", StringUtils.isEmpty((String) item.get("prepCode")) ? "" : item.get("prepCode"));//剂型
                        httpParamMap.put("bka073", StringUtils.isEmpty((String) item.get("productName")) ? "" : item.get("productName"));//厂家
                        httpParamMap.put("aka074", StringUtils.isEmpty((String) item.get("spec")) ? "" : item.get("spec"));//规格
                        String now = DateUtils.format((Date) item.get("costTime"),DateUtils.YMD);
                        httpParamMap.put("ake007", now);//费用发生时间
                        httpParamMap.put("aka067", StringUtils.isEmpty((String) item.get("dosageUnitCode")) ? "" : item.get("dosageUnitCode"));//计量单位
                        httpParamMap.put("bka040", BigDecimalUtils.scale(BigDecimalUtils.divide((BigDecimal)item.get("realityPrice"),(BigDecimal)item.get("totalNum")),4));
                        httpParamMap.put("akc226", BigDecimalUtils.scale((BigDecimal) item.get("totalNum"),4));//用量
                        httpParamMap.put("aae019", BigDecimalUtils.scale((BigDecimal)item.get("realityPrice"),2));//金额
                        httpParamMap.put("bkz103", "0");//用药标志
                        httpParamMap.put("bka061", item.get("useDays") ==null ? 0 : item.get("useDays"));//出院带药天数
                        //如果收费则 为空，如果是 退费则必须 为对应收费 的 aaz213
                        if(Constants.ZTBZ.BCH.equals(item.get("statusCode"))){
                            httpParamMap.put("bka062", "1"+SnowflakeUtils.getId().substring(12)+(int)(Math.random()*10));//对应费用序列号
                        }else{
                            httpParamMap.put("bka062", "");//对应费用序列号
                        }
                        httpParamMap.put("aaz213", "1"+SnowflakeUtils.getId().substring(12)+(int)(Math.random()*10));//医院费用序列号
                        httpParamMap.put("aae013", "");//用法用量等说明
                        if("42".equals(inptVisitDTO.getAka130())){
                            httpParamMap.put("bkm109", "1");//工伤费用标志
                        }
                         else{
                            httpParamMap.put("bkm109", "");//非工伤费用标志
                        }
                        httpParamMap.put("bka063", inptVisitDTO.getCode());//录入人工号
                        httpParamMap.put("bka064", inptVisitDTO.getCrteName());//录入人姓名
                        System.out.println(httpParamMap);
                        mapList.add(httpParamMap);
            }
        httpResult.put("feeinfo",mapList);
        Map<String, Object> resultMap = requestInsure.call(inptVisitDTO.getHospCode(),
                inptVisitDTO.getInsureRegCode(), httpResult);
        return resultMap;
    }

    /**
     * @Method bizh120005()
     * @Desrciption  住院费用上传（方式二）
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/17 16:59
     * @Return
     **/
    public Map<String,Object> bizh120005(InptVisitDTO inptVisitDTO) {
        //判断是否有传输费用信息
        Map<String,String> param = new HashMap<String,String>();
        param.put("hospCode",inptVisitDTO.getHospCode());//医院编码
        param.put("statusCode",Constants.ZTBZ.ZC);//状态标志 = 正常
        param.put("visitId",inptVisitDTO.getId());//就诊id
        param.put("isMatch",Constants.SF.S);//是否匹配 = 是
        param.put("transmitCode",Constants.SF.F);//传输标志 = 未传输
        List<Map<String,Object>> insureCostList = insureIndividualCostDAO.queryInsureCostByVisit(param);
        if(insureCostList == null || insureCostList.isEmpty()){
            throw new AppException("该医保病人没有可传输的费用");
        }
        Map<String,Object> httpParam = new HashMap<>();
        httpParam.put("function_id", Constant.Xiangtan.INPT.BIZH120005);//交易号
        httpParam.put("aaz217",inptVisitDTO.getMedicalRegNo());//就医登记号
        httpParam.put("akb020", inptVisitDTO.getMedicineOrgCode()); //医疗机构编码
        List<Map<String,Object>> costParam = new ArrayList<Map<String,Object>>();
        // 循环遍历 传费用上传参数值
        insureCostList.stream().forEach(item -> {
            Map<String,Object> itemParam = new HashMap<>();
            itemParam.put("aae019", item.get("realityPrice"));//(优惠后)金额
            itemParam.put("akc226", item.get("totalNum"));//数量
            itemParam.put("bka040", BigDecimalUtils.divide(BigDecimalUtils.convert((String) item.get("realityPrice")),BigDecimalUtils.convert((String)item.get("totalNum"))));//标准单价
            itemParam.put("aaz213", item.get("id"));//人员医疗费用明细ID
            itemParam.put("AKA065", "");//收费项目等级
            itemParam.put("AKA067", item.get("dosage"));//药品剂量单位
            itemParam.put("AKA070", item.get("prepCode"));//剂型
            itemParam.put("AKA074", item.get("spec"));//规格
            itemParam.put("AKC194", "");//医患最终结算日期
            itemParam.put("AKE001", item.get("insureItemCode"));//中心药品项目编码
            itemParam.put("AKE002", item.get("insureItemName"));//中心药品项目名称
            itemParam.put("AKE003", item.get("insureItemType"));//药品项目类型
            itemParam.put("ake005", item.get("hospItemCode"));//医院药品项目编码
            itemParam.put("ake006", item.get("hospItemName"));//医院药品项目名称
            String now = DateUtils.format((Date) item.get("costTime"),DateUtils.YMD);
            itemParam.put("ake007", now);//费用发生日期
            itemParam.put("AKE105", item.get("pqccItemId"));//药监局药品编码
            itemParam.put("BKA001", "");//费用批次
            itemParam.put("BKA059", "");//退费金额
            itemParam.put("BKA062", "");//退费对应的费用序列号
            itemParam.put("bka063", inptVisitDTO.getCode());//录入人员工号
            itemParam.put("bka064", inptVisitDTO.getCrteName());//录入人名称
            itemParam.put("BKA065", now);//录入时间
            itemParam.put("BKA067", "");//费用冻结标志，用来表识参保人所在单位的基本医疗保险被冻结期间录入的费用。0：未冻结；1：已冻结；2：冻结已处理
            itemParam.put("BKA069", "");//费用上传时间
            itemParam.put("bka070", "");//处方号
            itemParam.put("BKA071", "");//医院费用的唯一标识 
            itemParam.put("bka073", "");//厂家
            itemParam.put("bka074", "");//处方医师编号
            itemParam.put("bka075", "");//方医师姓名 mr
            itemParam.put("BKA511", "");//城职对应待遇值（自付比例支付类型
            itemParam.put("BKM001", "");//是否在岗医师标识：0，非在岗；1，在岗
            itemParam.put("BKM002", "");//超时标志，0未超时，1超时上传未申诉，2超时上传正在申诉，3超时上传申诉审核同意，4超时上传申诉审核不同意
            costParam.add(itemParam);
        });
        httpParam.put("feeinfo",costParam);
        Map<String, Object> resultMap = requestInsure.call(inptVisitDTO.getHospCode(), inptVisitDTO.getInsureRegCode(), httpParam);
        resultMap.put("mapList",costParam);
        return resultMap;
    }

    /**
     * @Method BIZH120002()
     * @Desrciption  医保病人费用上传（方式一）
     * @Param
     *
     * @Author fuhui
     * @Date   2020/11/16 14:59
     * @Return
     **/
    public Map<String,Object> bizh120002(InptVisitDTO inptVisitDTO) {
        Map<String,Object> httpParam = new HashMap<>();
        httpParam.put("function_id", Constant.Xiangtan.INPT.BIZH120002);//交易号
        httpParam.put("aaz217",inptVisitDTO.getMedicalRegNo());//就医登记号
        httpParam.put("akb020", inptVisitDTO.getMedicineOrgCode());//医疗机构编码
        List<Map<String,Object>> insureCostList = inptVisitDTO.getInsureCostList();//本次传输费用信息
        List<Map<String,Object>> costParamList = new ArrayList<Map<String,Object>>();
        for (Map<String,Object> item : insureCostList){
            Map<String,Object> itemParam = new HashMap<>();
            itemParam.put("aae019", BigDecimalUtils.scale((BigDecimal)item.get("realityPrice"),2));//(优惠后)金额
            itemParam.put("akc226", BigDecimalUtils.scale((BigDecimal)item.get("totalNum"),2));//数量
            itemParam.put("bka040", BigDecimalUtils.scale(BigDecimalUtils.divide((BigDecimal)item.get("realityPrice"),(BigDecimal)item.get("totalNum")),2));//标准单价
            itemParam.put("aaz213", "1"+SnowflakeUtils.getId().substring(12)+(int)(Math.random()*10));//人员医疗费用明细ID
            itemParam.put("AKA065", "");//收费项目等级
            itemParam.put("AKA067", item.get("dosage"));//药品剂量单位
            itemParam.put("AKA070", item.get("prepCode"));//剂型
            itemParam.put("AKA074", item.get("spec"));//规格
            itemParam.put("AKC194", "");//医患最终结算日期
            itemParam.put("AKE001", item.get("insureItemCode"));//中心药品项目编码
            itemParam.put("AKE002", item.get("insureItemName"));//中心药品项目名称
            itemParam.put("AKE003", item.get("insureItemType"));//药品项目类型
            itemParam.put("ake005", item.get("hospItemCode"));//医院药品项目编码
            itemParam.put("ake006", item.get("hospItemName"));//医院药品项目名称 
            String now = DateUtils.format((Date) item.get("costTime"),DateUtils.YMD);
            itemParam.put("ake007", now);//费用发生日期
            itemParam.put("AKE105", item.get("pqccItemId"));//药监局药品编码
            itemParam.put("BKA001", "");//费用批次
            itemParam.put("BKA059", "");//退费金额
            itemParam.put("BKA062", "");//退费对应的费用序列号
            itemParam.put("bka063", inptVisitDTO.getCode());//录入人员工号
            itemParam.put("bka064", inptVisitDTO.getCrteName());//录入人名称
            itemParam.put("BKA065", now);//录入时间
            itemParam.put("BKA067", "");//费用冻结标志，用来表识参保人所在单位的基本医疗保险被冻结期间录入的费用。0：未冻结；1：已冻结；2：冻结已处理
            itemParam.put("BKA069", "");//费用上传时间
            itemParam.put("bka070", "");//处方号
            itemParam.put("BKA071", "");//医院费用的唯一标识 
            itemParam.put("bka073", "");//厂家
            itemParam.put("bka074", "");//处方医师编号
            itemParam.put("bka075", "");//方医师姓名 mr
            itemParam.put("BKA511", "");//城职对应待遇值（自付比例支付类型
            itemParam.put("BKM001", "");//是否在岗医师标识：0，非在岗；1，在岗
            itemParam.put("BKM002", "");//超时标志，0未超时，1超时上传未申诉，2超时上传正在申诉，3超时上传申诉审核同意，4超时上传申诉审核不同意
            costParamList.add(itemParam);
        }
        httpParam.put("feeinfo",costParamList);
        Map<String, Object> resultMap = requestInsure.call(inptVisitDTO.getHospCode(), inptVisitDTO.getInsureRegCode(),httpParam);
        return resultMap;
    }
}
