package cn.hsa.insure.changsha.oupt;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.NeusoftStrUtil;
import cn.hsa.insure.util.RequestInsure;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dao.InsureItemMatchDAO;
import cn.hsa.module.insure.module.dto.InsureIndividualCostDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureItemMatchDTO;
import cn.hsa.module.insure.module.entity.InsureIndividualCostDO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.util.*;
import org.springframework.stereotype.Component;


import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Component("changsha-outpt")
public class OutptFunction {

    @Resource
    private InsureIndividualVisitDAO insureIndividualVisitDAO;
    @Resource
    private InsureItemMatchDAO insureItemMatchDAO;
    @Resource
    private RequestInsure requestInsure;
    /**
     * @Method CS_1807()
     * @Desrciption 门诊医保登记获取人员信息
     * @Param hashMap
     * @Author fuhui
     * @Date 2021/1/25 11:44
     * @Return
     **/
    public Map<String, Object> CS_1807(LinkedHashMap<String, Object> hashMap) {
        String insureRegCode = (String) hashMap.get("regCode"); //医疗机构编码
        String hospCode = (String) hashMap.get("hospCode");    // 医院编码
        String operationCode = (String) hashMap.get("code");  // 操作员编码
        // 获取公共入参
        Map<String, Object> map = StringUtils.getCommonParam(Constant.ChangSha.OUTPT.CS_1807, insureRegCode, operationCode);

        String hospTransactionNo = map.get("hospTransactionNo").toString();  // 医院交易流水号
        String commonRequestStr = map.get("commonParams").toString();  // 公共入参

        // 封装私有入参
        String inParam[] = new String[2];
        inParam[0] = StringUtils.isEmpty((String) hashMap.get("bka895")) ? "" : (String) hashMap.get("bka895"); // 查询条件
        inParam[1] = StringUtils.isEmpty((String) hashMap.get("bka896")) ? "" : (String) hashMap.get("bka896"); // 查询数据
        // 01 个人编号 02 身份证号 03 姓名
        StringBuffer httpParams = new StringBuffer();
        httpParams.append(commonRequestStr).append(StringUtils.arrayToStr(inParam, "|")).append("^");
        Map<String, Object> result = requestInsure.callNeusoft(hospCode, insureRegCode, httpParams.toString());
        List<String[]> data = NeusoftStrUtil.parseText(result.get("data"));
        if (ListUtils.isEmpty(data)) {
            throw new AppException("失败：远程调用医保端未获取到人员信息");
        }
        /**
         * 处理回参
         */
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> personinfoList = new ArrayList<>();
        for (String[] s : data) {
            Map<String, Object> personinfo = new HashMap<>();
            personinfo.put("aac001", s[0]); //个人电脑号 个人编号
            personinfo.put("aab001", s[1]);  // 单位编号
            personinfo.put("aac002", s[2]);  // 公民身份号码
            personinfo.put("aac003", s[3]); // 姓名
            personinfo.put("aac004", s[4]); // 性别
            personinfo.put("aac006", DateUtils.format(DateUtils.parse(s[6], DateUtils.YMDHMS), DateUtils.Y_M_D));//出生日期
            personinfo.put("bac001Name", s[7]); // 行政职务
            personinfo.put("aaz500", s[9]); // 社会保障卡卡号
            personinfo.put("bka035", s[10]);//人员类别（码表 BKA035）
            personinfo.put("bka008", s[17]);  //单位名称
            personinfo.put("bacu18", s[34]);//帐户余额
            personinfo.put("aaa027", s[60]);//统筹区编码
            personinfo.put("aab301", s[18]);//所属行政区代码.常住地
            personinfo.put("isInRegistered", s[35]); // 判断是否住院
            personinfoList.add(personinfo);
        }
        resultMap.put("personinfo", personinfoList);
        return resultMap;
    }
    /**
     * @Method FC_2710()
     * @Desrciption 两病门诊/门特/门诊别收费预结算
     * @Param 预结算的参数由三部分构成：
     * @Author fuhui
     * @Date 2021/1/13 14:59
     * @Return
     **/
    public Map<String, Object> CS_2710(HashMap<String, Object> param) {
        HashMap<String, Object> hashMap = new HashMap<>();
        String hospCode = (String) param.get("hospCode"); // 医院编码
        String visitId = (String) param.get("visitId"); // 就诊id
        String crteId = (String) param.get("crteId");  // 创建人id
        String insureRegCode = (String) param.get("insureRegCode"); // 医疗机构编码
        String crteName = (String) param.get("crteName");  // 创建人姓名
        String settleId = (String) param.get("settleId");//结算id
        String phone ="";
        if(param.containsKey("phone")){
            phone = MapUtils.get(param,"phone");  // 电话号码
        }
        String operateCode = (String) param.get("code"); // 操作人编码
        OutptSettleDTO outptSettleDTO1 = (OutptSettleDTO) param.get("outptSettleDTO1");
        String orderNo ="";
        if(outptSettleDTO1 !=null){
             orderNo = outptSettleDTO1.getSettleNo();
        }

        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        insureIndividualVisitDTO.setVisitId(visitId);
        insureIndividualVisitDTO.setHospCode(hospCode);
        InsureIndividualVisitDTO visitDTO = insureIndividualVisitDAO.selectInsureIndividualVisit(insureIndividualVisitDTO);
        param.put("aac001",visitDTO.getAac001());
        List<OutptCostDTO> outptCostDTOList = (List<OutptCostDTO>) param.get("fees");

        //数组的长度，用来区分是两病门诊/门特/门诊别收费结算:14长度是结算，12是预结算 如果报销标志不为空 则是结算
        Boolean flag;
        Map<String, Object> map;
        String commonRequestStr = "";
        String hospTransactionNo ="";
        int length;
        if("settle".equals(param.get("action") == null ? "" :param.get("action").toString())) {
            length = 14;
            map = StringUtils.getCommonParam(Constant.ChangSha.OUTPT.CS_2720, insureRegCode, operateCode);
            hospTransactionNo = map.get("hospTransactionNo").toString();  // 医院交易流水号
            commonRequestStr = map.get("commonParams").toString();  // 公共入参
            flag =true;
        }else{
            length = 12;
            map = StringUtils.getCommonParam(Constant.ChangSha.OUTPT.CS_2710, insureRegCode, operateCode);
            hospTransactionNo = map.get("hospTransactionNo").toString();  // 医院交易流水号
            commonRequestStr = map.get("commonParams").toString();  // 公共入参
            flag = false;
        }
        String[] paramStr = new String[length];
        String httpParam = "";
        String feeDetailParam = "";
        /**
         * 1.挂号与诊断参数的信息
         */
        // 个人编号 没有卡时，要传入个人编号（因有重复身份证号），有卡时传入空
        paramStr[0] = visitDTO.getAac001();
        // 门诊/住院流水号
        paramStr[1] = hospTransactionNo.replace("-","").trim();

        String registerTime = DateUtils.format(visitDTO.getRegisterTime(), DateUtils.YMDHMS);

        // 挂号日期
        paramStr[2] = registerTime;
        // 经办人
        paramStr[3] = visitDTO.getRegisterCrteName();
        // 医疗类别
        paramStr[4] = visitDTO.getAka130();
        if (!flag) {
            // 诊断疾病编码
            paramStr[5] = StringUtils.isEmpty(visitDTO.getVisitIcdCode()) ? "" : visitDTO.getVisitIcdCode();
            // 诊断疾病名称
            paramStr[6] = StringUtils.isEmpty(visitDTO.getVisitIcdName()) ? "" : visitDTO.getVisitIcdName();
            // 病历信息
            paramStr[7] = "";
            // 科室名称
            paramStr[8] = StringUtils.isEmpty(visitDTO.getVisitDrptName()) ? "" : visitDTO.getVisitDrptName();
            // 床位号
            paramStr[9] = StringUtils.isEmpty(visitDTO.getVisitBerth()) ? "" : visitDTO.getVisitBerth();
            // 医生代码
            paramStr[10] = StringUtils.isEmpty(visitDTO.getDoctorId()) ? "" : visitDTO.getDoctorId();
            // 医生姓名
            paramStr[11] = StringUtils.isEmpty(visitDTO.getDoctorName()) ? "" : visitDTO.getDoctorName();
        } else {
            // 单据号
            paramStr[5] = orderNo;
            // 报销标志
            paramStr[6] = "0";
            // 诊断疾病编码
            paramStr[7] = StringUtils.isEmpty(visitDTO.getVisitIcdCode()) ? "" : visitDTO.getVisitIcdCode();
            // 诊断疾病名称
            paramStr[8] = StringUtils.isEmpty(visitDTO.getVisitIcdName()) ? "" : visitDTO.getVisitIcdName();
            // 病历信息
            paramStr[9] = "";
            // 科室名称
            paramStr[10] = StringUtils.isEmpty(visitDTO.getVisitDrptName()) ? "" : visitDTO.getVisitDrptName();
            // 床位号
            paramStr[11] = StringUtils.isEmpty(visitDTO.getVisitBerth()) ? "" : visitDTO.getVisitBerth();
            // 医生代码
            paramStr[12] = StringUtils.isEmpty(visitDTO.getDoctorId()) ? "" : visitDTO.getDoctorId();
            // 医生姓名
            paramStr[13] = StringUtils.isEmpty(visitDTO.getDoctorName()) ? "" : visitDTO.getDoctorName();
        }
        httpParam = org.apache.commons.lang.StringUtils.join(paramStr, "|").concat("|");

        /**
         * 费用参数
         */
        List<InsureIndividualCostDO> insureIndividualCostDOList = new ArrayList<InsureIndividualCostDO>();
        int count = 0;  // 用来判断是否用#拼接费用明细
        String tempStr = new String();

        if (!ListUtils.isEmpty(outptCostDTOList)) {
            for (OutptCostDTO outptCostDTO : outptCostDTOList) {
                Map<String, Object> itemMatchParam = new HashMap<String, Object>();
                itemMatchParam.put("hospCode", hospCode);
                itemMatchParam.put("insureRegCode", insureRegCode);
                itemMatchParam.put("xmid", outptCostDTO.getItemId());
                List<InsureIndividualCostDTO> individualCostDTOList = new ArrayList<>();
                InsureItemMatchDTO insureItemMatchDTO = insureItemMatchDAO.queryInsureMatchItem(itemMatchParam);
                if (insureItemMatchDTO != null) {
                    String[] outFeeStr = new String[21];
                    // 项目类别
                    outFeeStr[0] = outptCostDTO.getItemCode();
                    // 费用类别
                    outFeeStr[1] = insureItemMatchDTO.getItemCode();
                    // 处方号
                    if (StringUtils.isEmpty(outptCostDTO.getOpId()) || !"1".equals(outptCostDTO.getItemCode())) {
                        outFeeStr[2] = "AAAA";
                        outFeeStr[3] = DateUtils.format(DateUtils.getNow(), DateUtils.YMDHMS);
                    } else {
                        outFeeStr[2] = "CF" +SnowflakeUtils.getId().substring(12);
                        // 处方日期
                        String outptPrescribeCrteTime = DateUtils.format(outptCostDTO.getCrteTime(), DateUtils.YMDHMS);
                        outFeeStr[3] = outptPrescribeCrteTime;
                    }
                    // 医院收费项目编码
                    outFeeStr[4] = insureItemMatchDTO.getHospItemCode();
                    // 收费项目中心编码
                    outFeeStr[5] = insureItemMatchDTO.getInsureItemCode();
                    // 医院收费项目名称
                    outFeeStr[6] = insureItemMatchDTO.getHospItemName();
                    // 单价
                    outFeeStr[7] = String.valueOf(BigDecimalUtils.scale((BigDecimal) outptCostDTO.getPrice(), 2));
                    // 数量
                    outFeeStr[8] = String.valueOf(BigDecimalUtils.scale((BigDecimal) outptCostDTO.getTotalNum(), 2));
                    // 剂型
                    outFeeStr[9] = StringUtils.isEmpty(outptCostDTO.getPrepCode()) ? "" : outptCostDTO.getPrepCode();
                    // 规格
                    outFeeStr[10] = outFeeStr[13] = StringUtils.isEmpty(outptCostDTO.getSpec()) ? "" : outptCostDTO.getSpec();
                    // 每次用量
                    outFeeStr[11] = String.valueOf(BigDecimalUtils.scale((BigDecimal) outptCostDTO.getDosage(), 2));
                    // 使用频次
                    outFeeStr[12] = outFeeStr[13] = StringUtils.isEmpty(outptCostDTO.getRateName()) ? "" : outptCostDTO.getRateName();
                    // 处方医师姓名
                    outFeeStr[13] = StringUtils.isEmpty(outptCostDTO.getDoctorName()) ? "" : outptCostDTO.getDoctorName();
                    // 处方医师编号
                    outFeeStr[14] = StringUtils.isEmpty(outptCostDTO.getDoctorId()) ? "" : outptCostDTO.getDoctorId();
                    // 用法
                    outFeeStr[15] = StringUtils.isEmpty(outptCostDTO.getUsageCode()) ? "" : outptCostDTO.getUsageCode();
                    // 单位
                    outFeeStr[16] = StringUtils.isEmpty(outptCostDTO.getNumUnitCode()) ? "" : outptCostDTO.getNumUnitCode();
                    // 开方科室名称
                    outFeeStr[17] = StringUtils.isEmpty(outptCostDTO.getDeptName()) ? "" : outptCostDTO.getDeptName();
                    // 限定天数
                    outFeeStr[18] = "";
                    // 草药单复方标志
                    outFeeStr[19] = "";
                    // 经办人
                    outFeeStr[20] = StringUtils.isEmpty(outptCostDTO.getCrteName()) ? "" : outptCostDTO.getCrteName();

                    feeDetailParam = org.apache.commons.lang.StringUtils.join(outFeeStr, "|");
                    if (count == 0 || count != outptCostDTOList.size() - 1) {
                        tempStr += feeDetailParam;
                    }
                    if (count != 0 && count != outptCostDTOList.size()) {
                        tempStr = tempStr.concat("#").concat(feeDetailParam);
                    }
                    count++;


                    /**
                     * 如果是结算 把本次的门诊费用信息保存到医保费用表里面
                     */
                    if (flag) {
                        InsureIndividualCostDO insureIndividualCostDO = new InsureIndividualCostDO();
                        insureIndividualCostDO.setId(SnowflakeUtils.getId());//id

                        insureIndividualCostDO.setHospCode(hospCode);//医院编码
                        insureIndividualCostDO.setVisitId(visitId);//就诊id
                        insureIndividualCostDO.setSettleId(settleId);//结算id
                        insureIndividualCostDO.setIsHospital(Constants.SF.F);//是否住院
                        insureIndividualCostDO.setItemType(insureItemMatchDTO.getInsureItemType());//对应医保项目类别
                        insureIndividualCostDO.setItemCode(insureItemMatchDTO.getInsureItemCode());//对应医保项目编码
                        insureIndividualCostDO.setItemName(insureItemMatchDTO.getInsureItemName());//对应医保项目名称
                        insureIndividualCostDO.setGuestRatio(null);//自付比例
                        insureIndividualCostDO.setPrimaryPrice(outptCostDTO.getRealityPrice());//原费用
                        insureIndividualCostDO.setApplyLastPrice(null);//报销后费用
                        insureIndividualCostDO.setOrderNo(count + "");//顺序号
                        insureIndividualCostDO.setTransmitCode(Constants.SF.S);//传输标志
                        insureIndividualCostDO.setCrteId(crteId);//创建人id
                        insureIndividualCostDO.setCrteName(crteName);//创建姓名
                        insureIndividualCostDO.setCrteTime(new Date());//创建时间
                        insureIndividualCostDOList.add(insureIndividualCostDO);
                    }
                }

            }
        } else {
            throw new AppException("门诊收费结算,费用参数为空");
        }
        if(!flag){
            length = 10;
        }else{
            length = 16;
        }
        String[] outHospitalStr = new String[length];
        // 结算日期
        String settleTime = DateUtils.format(DateUtils.getNow(), DateUtils.YMDHMS);
        outHospitalStr[0] = settleTime;
        // 出院日期
        String outTime = DateUtils.format(DateUtils.getNow(), DateUtils.YMDHMS);
        outHospitalStr[1] = outTime;
        // 出院原因
        outHospitalStr[2] = "";
        // 出院诊断疾病编码（主诊断）
        outHospitalStr[3] =  StringUtils.isEmpty(visitDTO.getVisitIcdCode()) ? "" : visitDTO.getVisitIcdCode();
        // 出院诊断疾病名称
        outHospitalStr[4] = "";
        // 病种分型
        outHospitalStr[7] = "";
        // 账户使用标志
        outHospitalStr[8] = "1";
        // 中途结算标志
        outHospitalStr[9] = "0";
        String[] split1 = {};
        if (StringUtils.isNotEmpty(visitDTO.getAccessoryDiagnosisCode())) {
                split1 = visitDTO.getAccessoryDiagnosisCode().split(",");
        }
        int diagnoseLength = split1.length;
        switch (diagnoseLength){
            case 1:
                // 第一副诊断
                outHospitalStr[5] = split1[0];
                // 第二副诊断
                outHospitalStr[6] = "";
                if(flag){
                    // 第三副诊断
                    outHospitalStr[10] = "";
                    // 第四副诊断
                    outHospitalStr[11] ="";
                    // 第五副诊断
                    outHospitalStr[12] = "";
                    // 第六副诊断
                    outHospitalStr[13] = "";
                    /**
                     * 门诊做结算的时候 需要传电话号码
                     */
                    outHospitalStr[14] = phone;
                    outHospitalStr[15] = "";
                }
                break;
            case 2:
                // 第一副诊断
                outHospitalStr[5] = split1[0];
                // 第二副诊断
                outHospitalStr[6] = split1[1];
                if(flag){
                    // 第三副诊断
                    outHospitalStr[10] = "";
                    // 第四副诊断
                    outHospitalStr[11] ="";
                    // 第五副诊断
                    outHospitalStr[12] = "";
                    // 第六副诊断
                    outHospitalStr[13] = "";
                    /**
                     * 门诊做结算的时候 需要传电话号码
                     */
                    outHospitalStr[14] = phone;
                    outHospitalStr[15] = "";
                }
                break;
            case 3:
                // 第一副诊断
                outHospitalStr[5] = split1[0];
                // 第二副诊断
                outHospitalStr[6] = split1[1];
                if(flag){
                    // 第三副诊断
                    outHospitalStr[10] = split1[2];
                    // 第四副诊断
                    outHospitalStr[11] ="";
                    // 第五副诊断
                    outHospitalStr[12] = "";
                    // 第六副诊断
                    outHospitalStr[13] = "";
                    /**
                     * 门诊做结算的时候 需要传电话号码
                     */
                    outHospitalStr[14] = phone;
                    outHospitalStr[15] = "";

                }
                break;
            case 4:
                // 第一副诊断
                outHospitalStr[5] = split1[0];
                // 第二副诊断
                outHospitalStr[6] = split1[1];
                if(flag){
                    // 第三副诊断
                    outHospitalStr[10] = split1[2];
                    // 第四副诊断
                    outHospitalStr[11] =split1[3];
                    // 第五副诊断
                    outHospitalStr[12] = "";
                    // 第六副诊断
                    outHospitalStr[13] = "";
                    /**
                     * 门诊做结算的时候 需要传电话号码
                     */
                    outHospitalStr[14] = phone;
                    outHospitalStr[15] = "";

                }
                break;
            case 5:
                    // 第一副诊断
                    outHospitalStr[5] = split1[0];
                    // 第二副诊断
                    outHospitalStr[6] = split1[1];
                    if(flag){
                        // 第三副诊断
                        outHospitalStr[10] = split1[2];
                        // 第四副诊断
                        outHospitalStr[11] =split1[3];
                        // 第五副诊断
                        outHospitalStr[12] = split1[4];
                        // 第六副诊断
                        outHospitalStr[13] = "";
                        /**
                         * 门诊做结算的时候 需要传电话号码
                         */
                        outHospitalStr[14] = phone;
                        outHospitalStr[15] = "";
                    }
                break;
            case 6:
                        // 第一副诊断
                        outHospitalStr[5] = split1[0];
                        // 第二副诊断
                        outHospitalStr[6] = split1[1];
                        if(flag){
                            // 第三副诊断
                            outHospitalStr[10] = split1[2];
                            // 第四副诊断
                            outHospitalStr[11] =split1[3];
                            // 第五副诊断
                            outHospitalStr[12] = split1[4];
                            // 第六副诊断
                            outHospitalStr[13] = split1[5];
                            /**
                             * 门诊做结算的时候 需要传电话号码
                             */
                            outHospitalStr[14] = phone;
                            outHospitalStr[15] = "";

                        }
                break;
            default:
                if(flag) {
                    outHospitalStr[14] = phone;
                }
                break;
        }
        StringBuilder stringBuilder = new StringBuilder();
        String httpParamStr = stringBuilder.append(commonRequestStr).append(httpParam).append("$").
                append(tempStr).append("$").append(org.apache.commons.lang.StringUtils.join(outHospitalStr, "|").concat("|").concat("$")).
                toString().concat("^");
        if (StringUtils.isEmpty(httpParamStr)) {
            throw new AppException("两病门诊/门特/门诊收费预结算传入参数为空");
        }
        Map<String, Object> result = requestInsure.callNeusoft(hospCode, insureRegCode, httpParamStr);
        String str = result.get("data").toString();
        int i = str.indexOf("^", str.indexOf("^") + 1);
        int j = str.length()-1;
        String[] split;
        String sunStr = str.substring(i+1,j);
        if(sunStr.indexOf("$") == -1){
            split = sunStr.split("|");

        }else{
            int a = sunStr.indexOf("$");
            String firstStr = sunStr.substring(0,a);
            split = firstStr.split("\\|");
            String lastStr = sunStr.substring(a+1);
        }
        /**
         * 处理回参
         */
            HashMap<String, Object> outParam = new HashMap<>();
            outParam.put("akc264", split[10]); // 医疗总费用
            outParam.put("akb067", split[14]);  //个人现金支付
            outParam.put("akb066", split[13]);  // 个人账户支付
            outParam.put("ake035", split[15]);  // 本次公务员补助支出
            outParam.put("ake039", split[11]);  // 医疗保险统筹基金支付
            outParam.put("akb020", hospCode);  //  医院编码
            outParam.put("bka831",split[14]); // 个人支付
            outParam.put("bka832",BigDecimalUtils.subtract(outParam.get("akc264").toString(),outParam.get("akb067").toString()).toString());
            outParam.put("aaz217", hospTransactionNo.replace("-","").trim());  //  流水号

        // 保存反参信息
        hashMap.put("payinfo", outParam);
        /**
         * 如果是结算操作 且集合不为空 则返回
         */

        if (!ListUtils.isEmpty(insureIndividualCostDOList)) {
            hashMap.put("insureIndividualCostDOList", insureIndividualCostDOList);
        }
        return hashMap;
    }

    /**
     * @Method FC_2720()
     * @Desrciption 两病门诊/门特/门诊别收费结算
     * @Param 结算的参数由三部分构成：
     * @Author fuhui
     * @Date 2021/1/13 14:59
     * @Return
     **/
    public Map<String, Object> CS_2720(HashMap<String, Object> param) {
        return CS_2710(param);
    }

    /**
     * @Method selectCheckInfo
     * @Desrciption  读取审批信息
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/2/1 19:31
     * @Return map
     **/
    private  Map<String, Object> CS_1806(Map<String, Object> hashMap) {
        String[] paramStr = new String[3];
        String insureRegCode = hashMap.get("insureRegCode").toString();
        String operatorCode = hashMap.get("code").toString();
        String hospCode = hashMap.get("hospCode").toString();
        String httpParam = "";
        Map<String, Object> map = StringUtils.getCommonParam(Constant.ChangSha.RESTS.CS_1806, insureRegCode, operatorCode);

        String hospTransactionNo = map.get("hospTransactionNo").toString();  // 医院交易流水号
        String commonRequestStr = map.get("commonParams").toString();  // 公共入参
        String requestParam = "";
        // 开始日期
        String startDate = DateUtils.format(DateUtils.getNow(), DateUtils.YMDHMS);
        paramStr[0] = startDate;
        // 结束日期
        paramStr[1] = "";
        // 个人编号
        paramStr[2] = (String) hashMap.get("aac001");
        httpParam = org.apache.commons.lang.StringUtils.join(paramStr, "|").concat("|");
        StringBuffer stringBuffer = new StringBuffer();
        requestParam = stringBuffer.append(commonRequestStr).append(httpParam).append("^").toString();
        if (StringUtils.isEmpty(requestParam)) {
            throw new AppException("医院审批信息查询参数为空");
        }
        Map<String, Object> result = requestInsure.callNeusoft(hospCode, insureRegCode, requestParam);
        List<String[]> data = NeusoftStrUtil.parseText(result.get("data"));
        if (!ListUtils.isEmpty(data)) {
            String[] strings = data.get(0);
            if(strings[0].equals("") && strings[1].equals("1")){
                throw new AppException("获取审批信息失败");
            }else {
                hashMap.put("resultData", data);
                return hashMap;
            }
        }
        return null;
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
    }

    /**
     * @Desrciption  uploadCheckInfo 医院审批信息上报
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/2/1 19:28
     * @Return boolean
     **/
    public Boolean CS_3110(Map<String, Object> hashMap) {
        String insureRegCode = hashMap.get("insureRegCode").toString(); // 医疗机构编码
        String operatorCode = hashMap.get("code").toString(); // 操作员编码
        String hospCode = hashMap.get("hospCode").toString(); // 医院编码
        Map<String, Object> map = StringUtils.getCommonParam(Constant.ChangSha.RESTS.CS_3110, insureRegCode, operatorCode);

        String hospTransactionNo = map.get("hospTransactionNo").toString();  // 医院交易流水号
        String commonRequestStr = map.get("commonParams").toString();  // 公共入参
        String[] paramStr = new String[25];
        paramStr[0] = (hashMap.get("aac001")).toString();
        paramStr[1] = "17";
        paramStr[2] = "";
        paramStr[3] = "";
        paramStr[4] = "";
        paramStr[5] = "";
        paramStr[7] = "";
        paramStr[8] = "";
        paramStr[9] = "";
        paramStr[10] = "";
        paramStr[11] = "";
        paramStr[12] = "";
        paramStr[13] = "";
        paramStr[14] = "";
        paramStr[15] = "";
        paramStr[16] = "";
        paramStr[17] = "";
        paramStr[18] = "";
        paramStr[19] = "";
        paramStr[20] = "";
        paramStr[21] = "";
        paramStr[22] = hashMap.get("crteName").toString();
        paramStr[23] = "";
        paramStr[24] = "";
        String httpParam = org.apache.commons.lang.StringUtils.join(paramStr, "|").concat("|");
        StringBuffer stringBuffer = new StringBuffer();
        String requestParam = stringBuffer.append(commonRequestStr).append(httpParam).append("^").toString();
        if (StringUtils.isEmpty(requestParam)) {
            throw new AppException("医院审批信息上报参数为空");
        }
        Map<String, Object> result = requestInsure.callNeusoft(hospCode, insureRegCode, requestParam);
        List<String[]> data = NeusoftStrUtil.parseText(result.get("data"));
        return true;
    }
    
    /**
     * @Method CS_2430
     * @Desrciption  
     * @Param 
     * 
     * @Author fuhui
     * @Date   2021/2/5 14:43 
     * @Return 
    **/
    public Boolean CS_2240( LinkedHashMap<String,Object> param){
        String hospCode = (String)param.get("hospCode");//医院编码
        String insureOrgCode = (String)param.get("insureRegCode");//医保编码
        String crteName = (String)param.get("crteName");
        String code = (String)param.get("code");// 操作员编码
        String ac0011 =  (String)param.get("aac001"); // 个人编号

        Map<String, Object> map = StringUtils.getCommonParam(Constant.ChangSha.OUTPT.CS_2240, insureOrgCode, code);
        String hospTransactionNo = map.get("hospTransactionNo").toString();  // 医院交易流水号
        String commonRequestStr = map.get("commonParams").toString();  // 公共入参
        String[] paramArr = new String[3];
        paramArr[0]= ac0011;
        paramArr[1]= hospTransactionNo;
        paramArr[2] = crteName;
        StringBuffer stringBuffer = new StringBuffer();
        String requestParam = stringBuffer.append(commonRequestStr).append(paramArr).append("^").toString();
        if (StringUtils.isEmpty(requestParam)) {
            throw new AppException("取消登记参数为空");
        }
        Map<String, Object> result = requestInsure.callNeusoft(hospCode, insureOrgCode, requestParam);
        return true;

    }

    /**
     * @Method FC_2430()
     * @Desrciption 门诊结算取消
     * @Param
     * @Author fuhui
     * @Date 2021/1/13 14:59
     * @Return
     **/
    public Map<String, Object> CS_2430(HashMap<String,Object> param) {
        String hospCode = (String)param.get("hospCode");//医院编码
        String insureOrgCode = (String)param.get("insureRegCode");//医保编码
        String code = (String)param.get("code");// 操作员编码
        String visitId =  (String)param.get("visitId"); // 就诊id
        OutptSettleDTO outptSettleDTO = (OutptSettleDTO) param.get("oldOutptSettleDTO");
        String orderNo ="";
        if(outptSettleDTO !=null){
            orderNo = outptSettleDTO.getSettleNo();
        }

        Map<String, Object> map = StringUtils.getCommonParam(Constant.ChangSha.OUTPT.CS_2430, insureOrgCode, code);
        String hospTransactionNo = map.get("hospTransactionNo").toString();  // 医院交易流水号
        String commonRequestStr = map.get("commonParams").toString();  // 公共入参
        /**
         * 根据就诊id 查询医保病人的就诊信息
         */
        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        insureIndividualVisitDTO.setVisitId(visitId);
        insureIndividualVisitDTO.setHospCode(hospCode);

        InsureIndividualVisitDTO visitDTO = insureIndividualVisitDAO.selectInsureIndividualVisit(insureIndividualVisitDTO);
        String[] paramArr = new String[17];
        paramArr[0] = visitDTO.getAac001();//个人编号
        paramArr[1] = visitDTO.getMedicalRegNo().replace("-","").trim();//发送方(医疗机构)撤销交易流水号
        paramArr[2] = orderNo;//单据号
        paramArr[3] = visitDTO.getAka130();//医疗类别
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
        paramArr[15] = (String) param.get("crteName");//经办员姓名
        paramArr[16] = "0";//是否保存处方标志 默认不保存
        String httpParam = org.apache.commons.lang.StringUtils.join(paramArr, "|").concat("|");
        StringBuffer stringBuffer = new StringBuffer();
        String requestParam = stringBuffer.append(commonRequestStr).append(httpParam).append("^").toString();
        Map<String,Object> result = requestInsure.callNeusoft(hospCode, insureOrgCode, requestParam);
        result.put("feeList",new ArrayList<Map<String,Object>>());
        return result;
    }
}
