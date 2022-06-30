package cn.hsa.insure.changsha.inpt;


import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.NeusoftStrUtil;
import cn.hsa.insure.util.RequestInsure;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.module.dto.*;
import cn.hsa.module.insure.module.service.InsureDiseaseMatchService;
import cn.hsa.util.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Package_name: cn.hsa.insure.changsha.inpt
 * @Class_name: inptFunction
 * @Describe(描述): 住院调用医保服务
 * <p>
 * inputData = 业务编号^医疗机构编号^操作员编号^业务周期号^医院交易流水号^中心编码^入参^
 * outputData = 中心交易流水号^业务周期号^输出参数^
 * </p>
 * <p>
 * [交易号]
 * 1807 读取人员信息
 * 2210 入院登记
 * 2230 修改入院登记
 * 2240 取消入院登记
 * 2420 住院预结算
 * 2410 住院结算
 * </p>
 * @Author: 廖继广
 * @Eamil: jiguang.liao@powersi.com.cn
 * @Date: 2020/01/13 08:45
 * @Company: 创智和宇
 */
@Component("changsha-inpt")
public class InptFunction {

    @Resource
    private RequestInsure requestInsure;

    @Resource
    private InsureDiseaseMatchService insureDiseaseMatchService;


    /**
     * @param insureIndividualBasicDTO 请求入参
     * @Menthod FC_1807
     * @Desrciption 读取人员信息 - 1807
     * @Author 廖继广
     * @Date 2020/11/13 08:46
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     */
    public Map<String, Object> CS_1807(InsureIndividualBasicDTO insureIndividualBasicDTO) {
        // 获取公共入参
        Map commonParamMap = StringUtils.getCommonParam(Constant.ChangSha.INPT.CS_1807, insureIndividualBasicDTO.getInsureRegCode(), insureIndividualBasicDTO.getUserCode());
        String commonParams = commonParamMap.get("commonParams").toString();
        // 封装私有入参
        if ("aac001".equals(insureIndividualBasicDTO.getBka895())) {
            insureIndividualBasicDTO.setBka895("01");
        }
        String inParam[] = new String[2];
        inParam[0] = insureIndividualBasicDTO.getBka895(); // 查询条件
        inParam[1] = insureIndividualBasicDTO.getBka896(); // 查询数据
        StringBuffer httpParams = new StringBuffer();
        httpParams.append(commonParams).append(StringUtils.arrayToStr(inParam, "|")).append("^");
        /*调用医保统一访问接口*/
        Map<String, Object> result = requestInsure.callNeusoft(insureIndividualBasicDTO.getHospCode(), insureIndividualBasicDTO.getInsureRegCode(), httpParams.toString());
        List<String[]> data = NeusoftStrUtil.parseText(result.get("data"));
        if (data == null || data.get(0).length == 2) {
            String bka895Name = "";
            switch (inParam[0]) {
                case "01":
                    bka895Name = "个人编号";
                    break;
                case "02":
                    bka895Name = "身份证号码";
                    break;
                case "03":
                    bka895Name = "姓名";
                    break;
                default:
                    break;
            }
            throw new AppException("未查询到该患者参保信息【" + bka895Name + " : " +  inParam[1] + "】");
        }
        //处理回参
        Map<String, Object> resultMap = new HashMap<String, Object>();//返回信息
        List<Map<String, Object>> personinfoList = new ArrayList<>();//人员信息
        for (String[] item : data) {
            Map<String, Object> personinfo = new HashMap<>();
            personinfo.put("aac001", item[0]);//人员ID
            personinfo.put("aab001", item[1]);//单位ID
            personinfo.put("aac002", item[2]);//公民身份号码
            personinfo.put("aac003", item[3]);//姓名
            personinfo.put("aac004", item[4]);//性别
            personinfo.put("mz", item[5]);//民族
            if (StringUtils.isNotEmpty(item[6])) {
                personinfo.put("aac006", DateUtils.format(DateUtils.parse(item[6], DateUtils.YMDHMS), DateUtils.Y_M_D));//出生日期
            }
            personinfo.put("xzzw", item[7]);//行政职务
            if (StringUtils.isNotEmpty(item[8])) {
                personinfo.put("jbrq", DateUtils.format(DateUtils.parse(item[8], DateUtils.YMDHMS), DateUtils.Y_M_D));//经办日期
            }
            personinfo.put("shbzkh", item[9]);//社会保障卡号
            personinfo.put("bka035", item[10]);//人员类别（码表 BKA035）
            personinfo.put("ylzh", item[11]);//医疗证号
            personinfo.put("aac031", item[12]);//人员缴费状态{1参保缴费，2暂停缴费，3终止缴费}
            personinfo.put("cbzt", item[13]);//人员参保状态{0未参保，1正常参保
            personinfo.put("aac066", item[14]);// 异地人员标志
            personinfo.put("gblb", item[15]);//干部类别
            personinfo.put("jfbz", item[16]);//一次性缴费标志(用于退休人员)
            personinfo.put("aab069", item[17]);//单位名称
            personinfo.put("bka008", item[17]);//单位名称
            personinfo.put("aab301", item[18]);//所属行政区代码.常住地
            personinfo.put("aab019", item[19]);//单位类型
            personinfo.put("aae140", item[20]);//险种类型（码表AAE140）
            personinfo.put("bka888", item[21]);//基金冻结状态(0正常、1冻结)

            personinfo.put("aae030", item[23]);//开始日期
            personinfo.put("gwybz", item[27]);//本年公务员补助注入
            personinfo.put("jfys", item[33]);//基本医疗本年缴费月数
            personinfo.put("bacu18", item[34]);//帐户余额
            personinfo.put("sfzy", item[35]); // 判断是否住院
            personinfo.put("fylj", item[36]);//本年医疗费累计
            personinfo.put("tczc", item[39]);//本年统筹支出累计
            personinfo.put("tcze", item[42]);//进入统筹医疗费总额
            personinfo.put("zflj", item[45]);//本年自费累计
            personinfo.put("bzlj", item[47]);//起付标准累计
            personinfo.put("bka010", item[48]);//本年住院次数
            personinfo.put("aaa027", item[60]);//统筹区编码
            personinfo.put("bke054", item[61]);//一站式结算标志
            personinfoList.add(personinfo);
        }
        resultMap.put("personinfo", personinfoList);
        return resultMap;
    }

    /**
     * @param insureInptRegisterDTO 请求入参
     * @Menthod FC_2210
     * @Desrciption 住院登记 - 2210
     * @Author 廖继广
     * @Date 2020/11/13 08:46
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     */
    public Map<String, Object> CS_2210(InsureInptRegisterDTO insureInptRegisterDTO) {
        // 获取公共入参
        Map commonParamMap = StringUtils.getCommonParam(Constant.ChangSha.INPT.CS_2210, insureInptRegisterDTO.getInsureOrgCode(), insureInptRegisterDTO.getUserCode());
        String commonParams = commonParamMap.get("commonParams").toString();
        String hospTransactionNo = commonParamMap.get("hospTransactionNo").toString();
        // 封装私有入参
        String inParam[] = new String[18];
        inParam[0] = insureInptRegisterDTO.getAac001();  // 个人编号	VARHCAR2(20)		没有卡时，要传入个人编号（因有重复身份证号），有卡时传入空
        inParam[1] = hospTransactionNo.replace("-", "").trim(); // 门诊/住院流水号	VARCHAR2(18)	NOT NULL	唯一
        inParam[2] = insureInptRegisterDTO.getAka130();  // 医疗类别	VARCHAR2(3)	NOT NULL	11 普通门诊 12 药店购药 14 门诊特病 16 急诊抢救 21 普通住院 22 市内转院 24 转外就医 51 生育门诊 52 节育门诊 53 生育住院 54 节育住院
        inParam[3] = insureInptRegisterDTO.getAae030();  // 挂号日期	VARCHAR2(14)NOT NULL 	YYYYMMDDHH24MISS
        inParam[4] = insureInptRegisterDTO.getAkc193();  // 诊断疾病编码	VARCHAR2(20)		必须为中心病种编码
        inParam[5] = insureInptRegisterDTO.getBkz101();  // 诊断疾病名称	VARCHAR2(50)
        inParam[6] = insureInptRegisterDTO.getEmrInfo(); // 病历信息	VARCHAR2(100)
        inParam[7] = insureInptRegisterDTO.getBka020();  // 科室名称	VARCHAR2(50)
        inParam[8] = insureInptRegisterDTO.getAke020();  // 床位号	VARCHAR2(20)	住院类需要传入床位号，如果入院时没有分配床位号，可以通过调用住院信息修改交易录入床位号
        inParam[9] = insureInptRegisterDTO.getDoctorId();  // 医生代码	VARCHAR2(20)
        inParam[10] = insureInptRegisterDTO.getAke022(); // 医生姓名	VARCHAR2(20)
        inParam[11] = "0";                               // 挂号费	VARCHAR2(8)		2位小数 只对门诊有用，如要的话传入，否则传0
        inParam[12] = "0";                               // 检查费	VARCHAR2(8)		2位小数 只对门诊有用，如要的话传入，否则传0
        inParam[13] = insureInptRegisterDTO.getBka015(); // 经办人	VARCHAR2(20)	NOT NULL	医疗机构操作员姓名
        inParam[14] = insureInptRegisterDTO.getAkc190(); // 住院号	VARCHAR2(20)
        inParam[15] = insureInptRegisterDTO.getInReson();// 入院原因	VARCHAR2(3)		0非转诊  1转诊 为空或不传时默认为0
        inParam[16] = insureInptRegisterDTO.getQrCode(); // 电子社保卡动态二维码	VARCHAR2(50)	    非电子社保卡登记时传空
        inParam[17] = insureInptRegisterDTO.getQrCodeToken();  // 电子凭证令牌token	VARCHAR2(40)	非电子凭证登记时传空
        StringBuffer httpParams = new StringBuffer(commonParams).append(StringUtils.arrayToStr(inParam, "|")).append("^");
        // 调用动态库，获取数据
        requestInsure.callNeusoft(insureInptRegisterDTO.getHospCode(), insureInptRegisterDTO.getInsureOrgCode(), httpParams.toString());
        // 解析回参数据
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 交易流水号用于取消入院登记
        resultMap.put("aaz217", hospTransactionNo);
        return resultMap;
    }

    /**
     * @param insureInptRegisterDTO 请求入参
     * @Menthod FC_2230
     * @Desrciption 修改住院登记信息 - 2230
     * @Author 廖继广
     * @Date 2020/11/13 08:46
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     */
    public Map<String, Object> CS_2230(InsureInptRegisterDTO insureInptRegisterDTO) {
        // 获取公共入参
        Map commonParamMap = StringUtils.getCommonParam("2230", insureInptRegisterDTO.getInsureOrgCode(), insureInptRegisterDTO.getUserCode());
        String commonParams = commonParamMap.get("commonParams").toString();
        String inParam[] = new String[11];
        inParam[0] = insureInptRegisterDTO.getAac001(); // 个人编号	VARHCAR2(20)	NOT NULL
        inParam[1] = insureInptRegisterDTO.getInNo(); // 住院流水号	VARCHAR2(18)	NOT NULL	    唯一
        inParam[2] = insureInptRegisterDTO.getEmrInfo(); // 病历信息	VARCHAR2(100)
        inParam[3] = insureInptRegisterDTO.getBka020(); // 科室名称	VARCHAR2(100)	NOT NULL
        inParam[4] = insureInptRegisterDTO.getAke020(); // 床位号	VARCHAR2(20)	NOT NULL
        inParam[5] = insureInptRegisterDTO.getDoctorId(); // 医生代码	VARCHAR2(20)	NOT NULL
        inParam[6] = insureInptRegisterDTO.getAke022(); // 医生姓名	VARCHAR2(20)	NOT NULL
        inParam[7] = insureInptRegisterDTO.getBka015(); // 经办人	VARCHAR2(20)	NOT NULL	    医疗机构操作员姓名
        inParam[8] = insureInptRegisterDTO.getAkc193(); // 诊断疾病编码	VARCHAR2(20)	NOT NULL	必须为中心病种编码
        inParam[9] = insureInptRegisterDTO.getBkz101(); // 诊断疾病名称	VARCHAR2(50)	NOT NULL
        inParam[10] = insureInptRegisterDTO.getInNo(); // 住院号	VARCHAR2(20)	NOT NULL
        StringBuffer httpParams = new StringBuffer(commonParams).append(StringUtils.arrayToStr(inParam, "|")).append("^");
        // 调用动态库，获取数据
        requestInsure.callNeusoft(insureInptRegisterDTO.getHospCode(), insureInptRegisterDTO.getInsureOrgCode(), httpParams.toString());
        return new HashMap();
    }

    /**
     * @param insureInptOutFeeDTO 请求入参
     * @Menthod FC_2240
     * @Desrciption 取消住院登记信息 - 2240
     * @Author 廖继广
     * @Date 2020/11/13 08:46
     * @Return Boolean
     */
    public Boolean CS_2240(InsureInptOutFeeDTO insureInptOutFeeDTO) {
        // 获取公共入参
        Map commonParamMap = StringUtils.getCommonParam("2240", insureInptOutFeeDTO.getInsureOrgCode(), insureInptOutFeeDTO.getUserCode());
        String commonParams = commonParamMap.get("commonParams").toString();
        // 封装私有入参
        String inParam[] = new String[3];
        inParam[0] = insureInptOutFeeDTO.getAac001(); // 个人编号	VARHCAR2(20)		没有卡时，要传入个人编号（因有重复身份证号），有卡时传入空
        inParam[1] = insureInptOutFeeDTO.getAaz217(); // 发送方(医疗机构)撤销交易流水号	VARCHAR2(30)	NOT NULL
        inParam[2] = insureInptOutFeeDTO.getBka015(); // 经办员姓名	VARCHAR2(20)	NOT NULL
        StringBuffer httpParams = new StringBuffer(commonParams).append(StringUtils.arrayToStr(inParam, "|")).append("^");
        // 调用动态库，获取数据
        requestInsure.callNeusoft(insureInptOutFeeDTO.getHospCode(), insureInptOutFeeDTO.getInsureOrgCode(), httpParams.toString());
        return true;
    }


    /**
     * @param param 请求入参
     * @Menthod FC_2420
     * @Desrciption 预结算 - 2420
     * @Author 廖继广
     * @Date 2020/11/13 08:46
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     */
    public Map<String, Object> CS_2420(HashMap<String, Object> param) {
        InptVisitDTO inptVisitDTO = (InptVisitDTO) param.get("inptVisit");
        InsureIndividualVisitDTO insureIndividualVisit = (InsureIndividualVisitDTO) param.get("insureIndividualVisit");
        String settleNo = param.get("settleNo").toString();

        // 获取公共入参
        Map commonParamMap = StringUtils.getCommonParam("2420", insureIndividualVisit.getInsureOrgCode(), inptVisitDTO.getUserCode());
        String commonParams = commonParamMap.get("commonParams").toString();

        // 获取诊断信息
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("hospCode",inptVisitDTO.getHospCode());
        paramsMap.put("visitId",inptVisitDTO.getId());
        paramsMap.put("insureRegCode",insureIndividualVisit.getInsureOrgCode());
        List<Map<String,Object>> disaseList =  insureDiseaseMatchService.queryInptDiseaseInfoByVisitId(paramsMap);
        if (ListUtils.isEmpty(disaseList)) {
            throw new AppException("未查询到医保中心疾病,请先完成医保疾病匹配！");
        }
        for (Map<String,Object> diseaseMap:disaseList) {
            if (!"1".equals(diseaseMap.get("auditCode")) || diseaseMap.get("insureIllnessCode") == null) {
                throw new AppException("诊断【" + diseaseMap.get("hospIllnessName") + "】未完成医保疾病匹配，请匹配后重试!");
            }
        }

        String isMainDiseaseCode = "";
        String isMainDiseaseName = "";
        String otherDiseaseCodes[] = new String[25];
        int i = 0;
        for(Map<String,Object> map : disaseList) {
            String isMain = MapUtils.get(map,"isMain");
            if ("1".equals(isMain)) {
                isMainDiseaseCode = MapUtils.get(map,"insureIllnessCode");
                isMainDiseaseName = MapUtils.get(map,"insureIllnessName");
            } else {
                otherDiseaseCodes[i] = MapUtils.get(map,"insureIllnessCode");
                i++;
            }
        }

        // 封装私有入参
        String inParam[] = new String[16];
        inParam[0] = insureIndividualVisit.getMedicalRegNo().replace("-", "").trim(); // 住院流水号(门诊流水号)	VARCHAR2(18)	NOT NULL	同登记时的住院流水号（门诊流水号）
        inParam[1] = settleNo; // 单据号	VARCHAR2(18)	NOT NULL	唯一
        inParam[2] = String.valueOf(insureIndividualVisit.getAka130()); // 医疗类别	VARCHAR2(3)	NOT NULL	医疗类别 (见二级代码)
        inParam[3] = DateUtils.SDF_YMD.format(DateUtils.getNow()); // 结算日期	VARCHAR2(14)	NOT NULL	YYYYMMDDHH24MISS，医院上传数据不能为空
        inParam[4] = DateUtils.SDF_YMD.format(inptVisitDTO.getOutTime()); // 出院日期	VARCHAR2(14)		YYYYMMDDHH24MISS，医院上传数据不能为空
        inParam[5] = ""; // 出院原因	VARCHAR2(3) '0'_正常治愈出院 '1'_未治愈出院 '2'_医院要求转院 '3'_病人要求转院 '4'_院内相关疾病转科治疗 '5'_院内无关疾病专科治疗 '6'_死亡 '7'_双向转诊
        inParam[6] = isMainDiseaseCode; // 出院诊断疾病编码（主诊断）	VARCHAR2(20)		必须为中心病种编码
        inParam[7] = isMainDiseaseName; // 出院诊断疾病名称	VARCHAR2(50)
        inParam[8] = otherDiseaseCodes[0];
        inParam[9] = otherDiseaseCodes[1]; // 第二副诊断	VARCHAR2(20)		必须为中心病种编码
        inParam[10] = "001"; // 病种分型	VARCHAR2(3)		影响中心和定点结算
        inParam[11] = "0"; // 报销类别	VARCHAR2(3)	NOT NULL	定点医疗机构全部传“0”
        inParam[12] = "1"; // 账户使用标志	VARCHAR2(3)	NOT NULL	0帐户不支付,1账户支付
        inParam[13] = "0"; // 中途结算标志	VARCHAR2(3)		0_非中途结算 1：中途结算
        inParam[14] = String.valueOf(inptVisitDTO.getCrteName()); // 经办人	VARCHAR2(20)	NOT NULL	医疗机构操作员姓名
        inParam[15] = ""; // 是否保存处方标志	VARCHAR2(3)		Null

        StringBuffer httpParams = new StringBuffer();
        httpParams.append(commonParams).append(StringUtils.arrayToStr(inParam, "|")).append("^");

        // 调用动态库，获取数据
        Map insureMap = requestInsure.callNeusoft(insureIndividualVisit.getHospCode(), insureIndividualVisit.getInsureRegCode(), httpParams.toString());
        String data = String.valueOf(insureMap.get("data"));
        if (StringUtils.isEmpty(data)) {
            throw new AppException("长沙市医保试算失败：未获取到医保费用");
        }

        int strStartIndex = data.substring(0, data.length() - 1).lastIndexOf("^");

        /* 开始截取 */
        data = data.substring(strStartIndex + 1, data.length() - 1);
        String resultData[] = data.split("\\|");
        // 解析回参数据
        Map<String, Object> resultMap = new HashMap();
        resultMap.put("akb020", insureIndividualVisit.getInsureOrgCode());
        resultMap.put("aaz217", insureIndividualVisit.getMedicalRegNo());
        resultMap.put("aka151", resultData[53]); // 起付标准
        resultMap.put("akb066", resultData[13]); // 本次帐户支出
        resultMap.put("akb067", resultData[14]); // 本次现金支出
        resultMap.put("akc264", resultData[10]); // 本次医疗费用
        resultMap.put("ake026", ""); // 企业补充医疗保险基金支付
        resultMap.put("ake029", ""); // 大额医疗费用补助基金支付
        resultMap.put("ake035", resultData[15]); // 公务员医疗补助基金支付
        resultMap.put("ake039", resultData[11]); // 医疗保险统筹基金支付
        resultMap.put("bka801", ""); // 床位费超额金额
        resultMap.put("bka821", resultData[81]); // 民政救助金支付
        resultMap.put("bka825", resultData[46]); // 全自费费用
        resultMap.put("bka826", resultData[47]); // 部分自费费用
        resultMap.put("bka831", resultData[14]); // 个人自付bka831 = akb067 + akb066
        resultMap.put("bka832", BigDecimalUtils.subtract(resultData[10],resultData[14]).toString()); // 医保支付bka832 = 医疗总费用 - 医保个人自付费用
        resultMap.put("bka842", resultData[60]); // 医院垫付
        resultMap.put("bka843", resultData[80]); // 特惠保
        resultMap.put("bka844", resultData[82]); // 医院减免
        resultMap.put("bka845", resultData[83]); // 政府兜底
        return resultMap;
    }

    /**
     * o
     *
     * @param param 请求入参
     * @Menthod FC_2410
     * @Desrciption 结算 - 2410
     * @Author 廖继广
     * @Date 2020/11/13 08:46
     * @Return java.util.Map<java.lang.String, java.lang.Object>
     */
    public Map<String, Object> CS_2410(HashMap<String, Object> param) {

        InptVisitDTO inptVisitDTO = (InptVisitDTO) param.get("inptVisit");
        InsureIndividualVisitDTO insureIndividualVisit = (InsureIndividualVisitDTO) param.get("insureIndividualVisit");
        String settleNo = param.get("settleNo").toString();

        // 获取公共入参
        Map commonParamMap = StringUtils.getCommonParam("2410", insureIndividualVisit.getInsureOrgCode(), inptVisitDTO.getUserCode());
        String commonParams = commonParamMap.get("commonParams").toString();

        // 获取诊断信息
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("hospCode",inptVisitDTO.getHospCode());
        paramsMap.put("visitId",inptVisitDTO.getId());
        paramsMap.put("insureRegCode",insureIndividualVisit.getInsureOrgCode());
        List<Map<String,Object>> disaseList =  insureDiseaseMatchService.queryInptDiseaseInfoByVisitId(paramsMap);
        if (ListUtils.isEmpty(disaseList)) {
            throw new AppException("未查询到医保中心疾病,请先完成医保疾病匹配！");
        }
        for (Map<String,Object> diseaseMap:disaseList) {
            if (!"1".equals(diseaseMap.get("auditCode")) || diseaseMap.get("insureIllnessCode") == null) {
                throw new AppException("诊断【" + diseaseMap.get("hospIllnessName") + "】未进行医保疾病匹配，请匹配后重试!");
            }
        }

        String isMainDiseaseCode = "";
        String isMainDiseaseName = "";
        String otherDiseaseCodes[] = new String[25];
        int i = 0;
        for(Map<String,Object> map : disaseList) {
            String isMain = MapUtils.get(map,"isMain");
            if ("1".equals(isMain)) {
                isMainDiseaseCode = MapUtils.get(map,"insureIllnessCode");
                isMainDiseaseName = MapUtils.get(map,"insureIllnessName");
            } else {
                otherDiseaseCodes[i] = MapUtils.get(map,"insureIllnessCode");
                i++;
            }
        }

        // 封装私有入参
        String inParam[] = new String[41];
        inParam[0] = insureIndividualVisit.getAac001(); // 个人编号	VARHCAR2(20)		没有卡时，要传入个人编号（因有重复身份证号），有卡时传入空
        inParam[1] = insureIndividualVisit.getMedicalRegNo().replace("-", "").trim(); // 住院流水号(门诊流水号)	VARCHAR2(18)	NOT NULL	同登记时的住院流水号（门诊流水号）
        inParam[2] = settleNo; // 单据号	VARCHAR2(18)	NOT NULL	唯一
        inParam[3] = insureIndividualVisit.getAka130(); // 医疗类别	VARCHAR2(3)	NOT NULL	医疗类别(见二级代码)
        inParam[4] = DateUtils.SDF_YMD.format(DateUtils.getNow()); // 结算日期	VARCHAR2(14)	NOT NULL	YYYYMMDDHH24MISS，医院上传数据不能为空
        inParam[5] = DateUtils.SDF_YMD.format(inptVisitDTO.getOutTime()); // 出院日期	VARCHAR2(14)		YYYYMMDDHH24MISS，医院上传数据不能为空
        inParam[6] = ""; // 出院原因	VARCHAR2(3)		'0'_正常治愈出院 '1'_未治愈出院 '2'_医院要求转院 '3'_病人要求转院 '4'_院内相关疾病转科治疗 '5'_院内无关疾病专科治疗 '6'_死亡  '7'_双向转诊
        inParam[7] = isMainDiseaseCode; // 出院诊断疾病编码（主诊断）	VARCHAR2(20)		必须为中心病种编码
        inParam[8] = isMainDiseaseName; // 出院诊断疾病名称	VARCHAR2(50)
        inParam[9] = otherDiseaseCodes[0]; // 第一副诊断	VARCHAR2(20)		必须为中心病种编码
        inParam[10] = otherDiseaseCodes[1]; // 第二副诊断	VARCHAR2(20)		必须为中心病种编码
        inParam[11] = "001"; // 病种分型	VARCHAR2(3)		影响中心和定点结算,见后面代码表
        inParam[12] = "0"; // 报销类别	VARCHAR2(3)	NOT NULL	定点医疗机构全部传“0”
        inParam[13] = "1"; // 账户使用标志	VARCHAR2(3)	NOT NULL	0帐户不支付,1账户支付
        inParam[14] = "0"; // 中途结算标志	VARCHAR2(3)		0_非中途结算 1：中途结算
        inParam[15] = inptVisitDTO.getCrteName(); // 经办人	VARCHAR2(20)	NOT NULL	医疗机构操作员姓名
        inParam[16] = ""; // 是否保存处方标志	VARCHAR2(3)		Null
        inParam[17] = inptVisitDTO.getPhone(); // 联系电话	VARCHAR2(20)
        inParam[18] = otherDiseaseCodes[2]; // 第3副诊断	VARCHAR2(20)		必须为中心病种编码
        inParam[19] = otherDiseaseCodes[3]; // 第4副诊断	VARCHAR2(20)		必须为中心病种编码
        inParam[20] = otherDiseaseCodes[4]; // 第5副诊断	VARCHAR2(20)		必须为中心病种编码
        inParam[21] = otherDiseaseCodes[5]; // 第6副诊断	VARCHAR2(20)		必须为中心病种编码
        inParam[22] = otherDiseaseCodes[6]; // 第7副诊断	VARCHAR2(20)		必须为中心病种编码
        inParam[23] = otherDiseaseCodes[7]; // 第8副诊断	VARCHAR2(20)		必须为中心病种编码
        inParam[24] = otherDiseaseCodes[8]; // 第9副诊断	VARCHAR2(20)		必须为中心病种编码
        inParam[25] = otherDiseaseCodes[9]; // 第10副诊断	VARCHAR2(20)		必须为中心病种编码
        inParam[26] = otherDiseaseCodes[10]; // 第11副诊断	VARCHAR2(20)		必须为中心病种编码
        inParam[27] = otherDiseaseCodes[11]; // 第12副诊断	VARCHAR2(20)		必须为中心病种编码
        inParam[28] = otherDiseaseCodes[12]; // 第13副诊断	VARCHAR2(20)		必须为中心病种编码
        inParam[29] = otherDiseaseCodes[13]; // 第14副诊断	VARCHAR2(20)		必须为中心病种编码
        inParam[30] = otherDiseaseCodes[14]; // 第15副诊断	VARCHAR2(20)		必须为中心病种编码
        inParam[31] = otherDiseaseCodes[15]; // 第16副诊断	VARCHAR2(20)		必须为中心病种编码
        inParam[32] = otherDiseaseCodes[16]; // 第17副诊断	VARCHAR2(20)		必须为中心病种编码
        inParam[33] = otherDiseaseCodes[17]; // 第18副诊断	VARCHAR2(20)		必须为中心病种编码
        inParam[34] = otherDiseaseCodes[18]; // 第19副诊断	VARCHAR2(20)		必须为中心病种编码
        inParam[35] = otherDiseaseCodes[19]; // 第20副诊断	VARCHAR2(20)		必须为中心病种编码
        inParam[36] = otherDiseaseCodes[20]; // 第21副诊断	VARCHAR2(20)		必须为中心病种编码
        inParam[37] = otherDiseaseCodes[21]; // 第22副诊断	VARCHAR2(20)		必须为中心病种编码
        //inParam[38] = inptVisitDTO.getTreatmentCode(); // 是否手术	VARCHAR2(20)		0 非手术 1 传统手术 2 介入手术3 微创手术4 恶性肿瘤放射治疗5 恶性肿瘤化学治疗6 恶性肿瘤放疗加化疗7 恶性肿瘤姑息治疗
        inParam[38] = inptVisitDTO.getTreatmentCode() == null ? "" : inptVisitDTO.getTreatmentCode();
        inParam[39] = ""; // 电子社保卡动态二维码	VARCHAR2(50)		非电子社保卡结算时传空
        inParam[40] = ""; // 电子凭证令牌token	VARCHAR2(40)		非电子凭证结算时传空

        StringBuffer httpParams = new StringBuffer();
        httpParams.append(commonParams).append(StringUtils.arrayToStr(inParam, "|")).append("^");

        Map insureMap = requestInsure.callNeusoft(insureIndividualVisit.getHospCode(), insureIndividualVisit.getInsureRegCode(), httpParams.toString());
        Integer code = Integer.valueOf(String.valueOf(insureMap.get("code")));
        if (code < 0) {
            throw new AppException("长沙市医保结算失败");
        }

        // 解析回参数据
        Map<String, Object> resultMap = new HashMap();
        return resultMap;
    }

    /**
     * @Method FC_2310()
     * @Desrciption 处方明细上报
     * @Param
     * @Author fuhui
     * @Date 2021/1/13 14:59
     * @Return
     **/
    public Boolean CS_2310(InptVisitDTO inptVisitDTO) {
        //费用信息
        List<Map<String, Object>> insureCostList = inptVisitDTO.getInsureCostList();
        // 获取公共入参
        Map commonParamMap = StringUtils.getCommonParam("2310", inptVisitDTO.getInsureRegCode(), inptVisitDTO.getUserCode());
        String commonParams = commonParamMap.get("commonParams").toString();

        String crteName = inptVisitDTO.getCrteName();
        int count = 0;
        StringBuffer tempStr = new StringBuffer();
        for (Map<String, Object> item : insureCostList) {
            String[] param = new String[23];

            param[0] = inptVisitDTO.getMedicalRegNo().replace("-", "").trim();//住院流水号(门诊流水号）
            param[1] = (String) item.get("itemCode");//项目类别  1药品、2诊疗项目、3服务设施
            param[2] = (String) item.get("insureMatchItemCode");//费用类别
            param[3] = (String) item.get("id");//处方号;
            param[4] = DateUtils.format((Date) item.get("crteTime"), DateUtils.YMDHMS);//处方日期
            param[5] = (String) item.get("hospItemCode");//医院收费项目编码
            param[6] = (String) item.get("insureItemCode");//收费项目中心编码
            param[7] = (String) item.get("hospItemName");//医院收费项目名称
            param[8] = String.valueOf(BigDecimalUtils.scale(BigDecimalUtils.divide((BigDecimal) item.get("realityPrice"), (BigDecimal) item.get("totalNum")), 4));//单价
            param[9] = String.valueOf(BigDecimalUtils.scale((BigDecimal) item.get("totalNum"), 2));//数量
            param[10] = StringUtils.isEmpty((String) item.get("prepCode")) ? "" : (String) item.get("prepCode");//剂型
            param[11] = StringUtils.isEmpty((String) item.get("spec")) ? "" : (String) item.get("spec");//规格
            param[12] = String.valueOf(item.get(BigDecimalUtils.scale((BigDecimal) item.get("dosage"), 2)));//每次用量
            param[13] = (String) item.get("rateName");//使用频次
            param[14] = (String) item.get("doctorName");//医生姓名
            param[15] = (String) item.get("doctorCode");//处方医师编码
            param[16] = (String) item.get("usageCode");//用法
            param[17] = (String) item.get("numUnitCode");//单位
            param[18] = (String) item.get("inDeptName");//科别名称
            param[19] = "";//执行天数
            param[20] = "";//草药单复方标志
            param[21] = crteName;//经办人
            String inParamsStr = StringUtils.arrayToStr(param, "|");
            if (count > 0) {
                tempStr.append("$");
            }
            tempStr.append(inParamsStr);
            count++;
        }
        String requestParam = new StringBuilder(commonParams).append(tempStr).append("^").toString();
        requestInsure.callNeusoft(inptVisitDTO.getHospCode(), inptVisitDTO.getInsureRegCode(), requestParam);
        return true;
    }

    /**
     * @Method CS_2320()
     * @Desrciption 撤销费用明细
     * @Param
     * @Author fuhui
     * @Date 2021/1/13 14:59
     * @Return
     **/
    public Boolean CS_2320(HashMap<String, Object> param) {
        InsureIndividualVisitDTO insureIndividualVisit = (InsureIndividualVisitDTO) param.get("insureIndividualVisitDTO");
        InptVisitDTO inptVisitDTO = (InptVisitDTO) param.get("inptVisitDTO");
        String hospCode = insureIndividualVisit.getHospCode();//医院编码
        String insureRegCode = insureIndividualVisit.getInsureRegCode();//医保编码
        // 公共入参
        // 获取公共入参
        Map commonParamMap = StringUtils.getCommonParam("2320", insureIndividualVisit.getInsureRegCode(), inptVisitDTO.getUserCode());
        String commonParams = commonParamMap.get("commonParams").toString();

        String[] paramArr = new String[3];
        paramArr[0] = insureIndividualVisit.getMedicalRegNo().replace("-", "").trim();//住院流水号(门诊流水号)
        paramArr[1] = "";//发送方(医疗机构)撤销交易流水号(如果只撤销一部分，则此值不为空)
        paramArr[2] = inptVisitDTO.getCrteName();//经办员姓名

        String inParamsStr = StringUtils.arrayToStr(paramArr, "|");

        String requestParam = new StringBuilder(commonParams).append(inParamsStr).append("^").toString();
        requestInsure.callNeusoft(hospCode, insureRegCode, requestParam);
        return true;
    }

    /**
     * @Method CS_2430()
     * @Desrciption 住院反结算-费用结算撤销
     * @Param
     * @Author fuhui
     * @Date 2021/1/13 14:59
     * @Return
     **/
    public Boolean CS_2430(HashMap<String, Object> param) {
        InsureIndividualVisitDTO insureIndividualVisit = (InsureIndividualVisitDTO) param.get("insureIndividualVisit");
        String userName = String.valueOf(param.get("userName"));
        String userCode = String.valueOf(param.get("userCode"));
        String hospCode = insureIndividualVisit.getHospCode();//医院编码
        // 公共入参
        // 获取公共入参
        Map commonParamMap = StringUtils.getCommonParam("2430", insureIndividualVisit.getInsureRegCode(), userCode);
        String commonParams = commonParamMap.get("commonParams").toString();
        String[] paramArr = new String[17];
        paramArr[0] = insureIndividualVisit.getAac001();//个人编号
        paramArr[1] = insureIndividualVisit.getMedicalRegNo().replace("-", "").trim();//发送方(医疗机构)撤销交易流水号
        paramArr[2] = String.valueOf(param.get("settleNo"));//单据号
        paramArr[3] = insureIndividualVisit.getAka130();//医疗类别
        paramArr[4] = "";//结算日期
        paramArr[5] = "";//出院日期
        paramArr[6] = "";//出院原因
        paramArr[7] = "";//出院诊断疾病编码
        paramArr[8] = "";//出院诊断疾病名称
        paramArr[9] = "";//第一副诊断
        paramArr[10] = "";//第二副诊断
        paramArr[11] = "";//病种分型
        paramArr[12] = "0";//报销类别
        paramArr[13] = "";//账户使用标志
        paramArr[14] = "";//中途结算标志
        paramArr[15] = userName;//经办员姓名
        paramArr[16] = "1";//是否保存处方标志 默认不保存
        String inParamsStr = StringUtils.arrayToStr(paramArr, "|");
        String requestParam = new StringBuilder(commonParams).append(inParamsStr).append("^").toString();

        requestInsure.callNeusoft(hospCode, insureIndividualVisit.getInsureRegCode(), requestParam);
        return true;
    }


    /**
     * @Method FC_1806()
     * @Desrciption 医院审批信息查询
     * @Param
     * @Author fuhui
     * @Date 2021/1/13 14:59
     * @Return
     **/
    public Boolean CS_1806(HashMap<String, Object> hashMap) {
        String[] paramStr = new String[3];
        String httpParam = "";
        String commonRequestStr = (String) hashMap.get("commonRequestStr");
        String requestParam = "";
        // 开始日期
        String startDate = DateUtils.format((Date) hashMap.get("startDate"), DateUtils.YMDHMS);
        paramStr[0] = (String) hashMap.get(startDate);
        // 结束日期
        String endDate = DateUtils.format((Date) hashMap.get("endDate"), DateUtils.YMDHMS);
        paramStr[1] = (String) hashMap.get(endDate);
        // 个人编号
        paramStr[2] = (String) hashMap.get("aac001");
        httpParam = org.apache.commons.lang.StringUtils.join(paramStr, "|").concat("|");
        StringBuffer stringBuffer = new StringBuffer();
        requestParam = stringBuffer.append(commonRequestStr).append(httpParam).append("^").toString();
        if (StringUtils.isEmpty(requestParam)) {
            throw new AppException("医院审批信息查询参数为空");
        }
        // 个人编号
        // 审批类别
        // 住院流水号
        // 转外就诊医院名称
        // 医院等级
        // 病种编码
        // 病种名称
        // 医院意见
        // 项目编码
        // 项目名称
        // 审批数量
        // 医院申报日期
        // 开始时间
        // 终止时间
        // 经办人
        // 转外类别
        // 审批标志
        // 姓名
        // 身份证号
        // 生育证号
        // 申报医院编号
        // 申报日期
        // 事故编号
        // 审批编号
        // 复审标志
        // 药店编号
        // 联系电话
        // 家庭住址
        // 工作单位
        // 备注
        // 就诊医院编号
        // 特门第一副诊断编码
        // 特门第一副诊断名称
        // 特门第二副诊断编码
        // 特门第二副诊断名称
        return true;
    }

    /**
     * @Method FC_1300()
     * @Desrciption 批量数据查询下载
     * @Param
     * @Author fuhui
     * @Date 2021/1/13 14:59
     * @Return
     **/
    public Map<String, Object> CS_1300(InsureItemDTO insureItemDTO) throws Exception {
        // 下载类型
        String type = insureItemDTO.getDownLoadType();
        // 医院编码
        String hospCode = insureItemDTO.getHospCode();
        // 医疗机构编码
        String regCode = insureItemDTO.getInsureRegCode();
        // 操作员编码
        String code = insureItemDTO.getCode();
        Date date = insureItemDTO.getStartDate();
        HashMap<String, Object> resultMap = new HashMap<>();
        String[] paramStr = new String[2];
        Map<String, Object> map = StringUtils.getCommonParam(Constant.ChangSha.RESTS.CS_1300, regCode, code);
        String requestParam = "";
        String httpParam = "";
        // 开始日期
        String startDate = DateUtils.format(date, DateUtils.YMDHMS);
        // 项目编码
        paramStr[0] = type;
        paramStr[1] = startDate;
        httpParam = org.apache.commons.lang.StringUtils.join(paramStr, "|").concat("|");
        StringBuffer stringBuffer = new StringBuffer();
        String hospTransactionNo = map.get("hospTransactionNo").toString();  // 医院交易流水号
        String commonRequestStr = map.get("commonParams").toString();  // 公共入参
        requestParam = stringBuffer.append(commonRequestStr).append(httpParam).append("^").toString();
        if (StringUtils.isEmpty(requestParam)) {
            throw new AppException("根据项目编码批量查询下载请求参数为空");
        }
        Map<String, Object> result = requestInsure.callNeusoft(hospCode, regCode, requestParam);
        List<String[]> insureResultList = new ArrayList<>();
        insureResultList = NeusoftStrUtil.parseText(result.get("data"));
        if(ListUtils.isEmpty(insureResultList)){
            throw new AppException("下载匹配数据为空");
        }
        // 定义集合 保存下载回来的参数信息
        List<Map<String, Object>> downLoadInfo = new ArrayList<>();
        HashMap<String, Object> hashMap =null;
        for (String[] s : insureResultList) {
            hashMap = new HashMap<>();
            // 药品目录
            if (Constant.ChangSha.DOWNLOADITEM.YPML.equals(type)) {
                // 药品编码
                hashMap.put("AKA060", s[0]);
                // 通用名称
                hashMap.put("AKA061", s[1]);
                // 英文名称
                hashMap.put("AKA062", s[2]);
                // 收费类别
                hashMap.put("AKA063", s[3]);
                // 收费项目等级
                hashMap.put("AKA065", s[4]);
                // 拼音码
                hashMap.put("AKA066", s[5]);
                // 药品剂量单位
                hashMap.put("AKA067", s[6]);
                // 最高价格
                hashMap.put("AKA068", s[7]);
                // 院内制剂标志
                hashMap.put("AKA084", s[8]);
                // 是否需要审批标志
                hashMap.put("AKC173", s[9]);
                // 特药标志
                hashMap.put("AKA608", s[10]);
                // 剂型
                hashMap.put("AKA070", s[11]);
                // 五笔助记码
                hashMap.put("AKA074", s[12]);
                // 单位
                hashMap.put("AKA076", s[13]);
                // 规格
                hashMap.put("AKA077", s[14]);
                // 经办人
                hashMap.put("AAE011", s[15]);
                // 经办日期
                hashMap.put("AAE036", s[16]);
                // 开始时间
                hashMap.put("AAE030", s[17]);
                // 终止时间
                hashMap.put("AAE031", s[18]);
                // 备注
                hashMap.put("AAE013", s[19]);
                // 自定义码
                hashMap.put("AKA605", s[20]);
                // 国家目录编码
                hashMap.put("AKA610", s[21]);
                // 限制使用范围
                hashMap.put("AKA613", s[22]);
                // 有效标志
                hashMap.put("isValid", s[23]);
                // 工伤标识
                hashMap.put("BKA101", s[24]);
                // 生育标识
                hashMap.put("BKA102", s[25]);
                // 城居人员使用标识
                hashMap.put("BKA104", s[26]);
                // 城居儿童使用标识
                hashMap.put("BKA105", s[27]);

            }
            // 诊疗项目目录信息
            else if (Constant.ChangSha.DOWNLOADITEM.ZLXMXX.equals(type)) {
                // 项目编码
                hashMap.put("AKA090", s[0]);
                // 开始时间
                hashMap.put("AAE030", s[1]);
                // 项目名称
                hashMap.put("AKA091", s[2]);
                // 费用类别
                hashMap.put("AKA063", s[3]);
                // 收费项目等级
                hashMap.put("AKA065", s[4]);
                // 是否需要审批标志
                hashMap.put("AKC173", s[5]);
                // 特检特制标志
                hashMap.put("AKA629", s[6]);
                // 最高价格
                hashMap.put("AKA068", s[7]);
                // 单位
                hashMap.put("AKA076", s[8]);
                // 拼音助记码
                hashMap.put("AKA066", s[9]);
                // 五笔助记码
                hashMap.put("AKA074", s[10]);
                // 自定义码
                hashMap.put("AKA605", s[11]);
                // 经办人
                hashMap.put("AAE011", s[12]);
                // 经办日期
                hashMap.put("AAE036", s[13]);
                // 终止时间
                hashMap.put("AAE031", s[14]);
                // 备注
                hashMap.put("AAE013", s[15]);
                // 限制使用范围
                hashMap.put("AKA613", s[16]);
                // 有效标志
                hashMap.put("isValid", s[17]);
                // 工伤标识
                hashMap.put("BKA101", s[18]);
                // 生育标识
                hashMap.put("BKA102", s[19]);

            }
            // 医疗服务设施信息
            else if (Constant.ChangSha.DOWNLOADITEM.YLFWSSXX.equals(type)) {
                // 服务设施编码
                hashMap.put("AKA100", "");
                // 服务设施名称
                hashMap.put("AKA102", "");
                // 开始时间
                hashMap.put("AAE030", "");
                // 费用类别
                hashMap.put("AKA063", "");
                // 医院等级
                hashMap.put("AKA101", "");
                // 病床等级
                hashMap.put("AKA103", "");
                // 支付标准
                hashMap.put("AKA104", "");
                // 最高价格
                hashMap.put("AKA068", "");
                // 拼音助记码
                hashMap.put("AKA066", "");
                // 五笔助记码
                hashMap.put("AKA074", "");
                // 限制使用范围
                hashMap.put("AKA613", "");
                // 终止时间
                hashMap.put("AAE031", "");
                // 经办人
                hashMap.put("AAE011", "");
                // 经办时间
                hashMap.put("AAE036", "");
                // 有效标志
                hashMap.put("AAE100", "");
            }
            // 费用类别信息
            else if (Constant.ChangSha.DOWNLOADITEM.FYLBXX.equals(type)) {
                // 费用类别编码
                hashMap.put("AKA120", "");
                // 费用类别名称
                hashMap.put("AKA122", "");
                // 收费大类编码
                hashMap.put("AKA123", "");
                // 疾病名称
                hashMap.put("AKA121", "");
            }
            // 病种信息
            else if (Constant.ChangSha.DOWNLOADITEM.BZXX.equals(type)) {
                // 疾病编码
                hashMap.put("aka120", s[0]);
                // 疾病种类
                hashMap.put("AKA122", s[1]);
                // 病种分类
                hashMap.put("AKA123", s[3]);
                // 疾病名称
                hashMap.put("aka121", s[4]);
                // 病种报销标志
                hashMap.put("AKA128", s[5]);
                // 特病分类
                hashMap.put("AKA628", s[6]);
                // 拼音助记码
                hashMap.put("AKA066", s[7]);
                // 五笔助记码
                hashMap.put("AKA074", s[8]);
                // 有效标志
                hashMap.put("AAE100", s[9]);
                // 经办人
                hashMap.put("AAE011", s[10]);
                // 经办时间
                hashMap.put("AAE036", s[11]);
                // 备注
                hashMap.put("AAE013", s[12]);
                // 工伤标识
                hashMap.put("BKA101", s[13]);
                // 生育标识
                hashMap.put("BKA102", s[14]);
            }
            // 项目对照信息
            else if (Constant.ChangSha.DOWNLOADITEM.XMDZXX.equals(type)) {
                // 中心项目编码
                hashMap.put("AKC222", s[0]);
                // 中心项目名称
                hashMap.put("AKA122", s[1]);
                // 中心收费类别
                hashMap.put("AKA063", s[2]);
                // 定点医疗机构编码
                hashMap.put("AKB020", s[3]);
                // 定点医疗机构项目编码
                hashMap.put("AKC515", s[4]);
                // 定点医疗机构项目名称
                hashMap.put("AKC516", s[5]);
                // 定点医疗机构药品剂型
                hashMap.put("AKA070", s[6]);
                // 收费项目种类
                hashMap.put("AKC224", s[7]);
                // 单位
                hashMap.put("AKA076",s[8]);
                // 规格
                hashMap.put("AKA077", s[9]);
                // 审核标志
                hashMap.put("AKC175", s[10]);
                // 审核时间
                hashMap.put("AAE036", s[11]);
                // 备注
                hashMap.put("AAE013", s[12]);
                // 生效日期
                hashMap.put("AAE030", s[13]);
                // 单价
                hashMap.put("AAE031", s[14]);
                // 生产厂家
                hashMap.put("AKA083", s[15]);
                // 失效日期
                hashMap.put("AAE031", s[16]);
                // 批准文号
                hashMap.put("AKA085", s[17]);
                // 初审人
                hashMap.put("AKA087", s[18]);
                // 初审时间
                hashMap.put("AKA088", s[19]);
                // 复审人
                hashMap.put("AKA089", s[20]);
                // 复审时间
                hashMap.put("AKA090", s[21]);
            }
            // 病种分型信息
            else {
                // 定点医疗机构编码
                hashMap.put("AKB020", "");
                // 病种分型编码
                hashMap.put("AKA129", "");
                // 病种分型名称
                hashMap.put("AKA121", "");
                // 病种分型限额
                hashMap.put("AKA068", "");
                // 开始时间
                hashMap.put("AAE030", "");
                // 终止时间
                hashMap.put("AAE031", "");
                // 经办人
                hashMap.put("AAE011", "");
                // 经办时间
                hashMap.put("AAE036", "");
                // 有效标志
                hashMap.put("AAE100", "");
            }
            downLoadInfo.add(hashMap);
        }
        resultMap.put("downloadType", type);
        resultMap.put("downLoadInfo", downLoadInfo);
        return resultMap;
    }
}
