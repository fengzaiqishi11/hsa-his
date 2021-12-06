package cn.hsa.insure.hainansheng.emd;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.RequestInsure;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dao.InsureItemMatchDAO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureItemMatchDTO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Package_name: cn.hsa.insure.hainansheng.emd
 * @Class_name: OutptElectronicBillFunction
 * @Describe(描述): 门诊电子凭证功能类
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2021/3/4 16:29
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

@Component(value = "hainansheng-emd-outpt")
public class OutptElectronicBillFunction {

    @Resource
    private RequestInsure requestInsure;

    @Resource
    private InsureIndividualVisitDAO insureIndividualVisitDAO;

    @Resource
    private InsureItemMatchDAO insureItemMatchDAO;

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;



    /**
     * @Menthod getPatientInfo
     * @Desrciption 门诊电子凭证获取个人信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/3/4 15:00
     * @return: Map
     */
    public Map getPatientInfo(LinkedHashMap<String, Object> param) {
        String hospCode = (String) param.get("hospCode");
        String regCode = (String) param.get("regCode");//医保注册编码
        JSONObject nationResult = JSONObject.parseObject((String) param.get("nationECResult"));//解码参数
        Map<String,Object> data = new HashMap<String,Object>();//入参
        data.put("appId", "37B0389095E640F89DEE9F5C8D763E17");//nationResult.get("chnlId"));//应用渠道编号
        data.put("orgCodg", regCode);//医保机构编号
        data.put("medType", null);//诊断类别
        data.put("ecToken", nationResult.get("ecToken"));//电子凭证授权 token
        data.put("idNo", nationResult.get("idNo"));//证件号码，不可空
        data.put("userName", nationResult.get("userName"));//用户姓名，不可空
        data.put("idType", nationResult.get("idType"));//证件类别，不可空
        data.put("insuCode", nationResult.get("insuOrg"));//统筹区编码，不可空
        data.put("extData", nationResult.get("extra"));//医保扩展数据
        Map<String,Object> httpParam = new HashMap<String,Object>();
        httpParam.put("data",data);
        httpParam.put("orgId",regCode);
        httpParam.put("transType", Constant.hainan.FUNCTION.hosQueryInsu);

        // 查询电子凭证地址
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("hospCode", hospCode);
        parameterMap.put("code", "DZPZ_URL");
        Map<String, String> resultMap = insureIndividualVisitDAO.selectSysParameter(parameterMap);
        if (resultMap.isEmpty() || "".equals(resultMap.get("value"))) {
            throw new AppException("系统参数中未配置电子凭证地址(参数名：DZPZ_URL)，请联系管理员配置");
        }
        Map<String,Object> httpResult = requestInsure.callHaiNan(hospCode,regCode,resultMap.get("value"),httpParam);
        httpResult.put("app",param.get("nationECResult"));
        Map<String,Object> extData = (Map<String, Object>) httpResult.get("extData");//医保扩展数据
        Map<String,Object> baseinfo = (Map<String, Object>) extData.get("baseinfo");//患者基本信息
        List<Map<String,Object>> insuList = (List<Map<String, Object>>) httpResult.get("insuList");//参保险种
        List<Map<String,Object>> condList = (List<Map<String, Object>>) httpResult.get("condList");//特殊病种登记信息
        List<Map<String,Object>> mdtrtprovList = (List<Map<String, Object>>) httpResult.get("mdtrtprovList");//异地就医登记信息
        List<Map<String,Object>> personinfoList = new ArrayList<Map<String,Object>>();//个人信息
        if (insuList != null && !insuList.isEmpty()){
            if (insuList.size() > 1){
                //多条个人信息
                for (Map<String,Object> item : insuList){
                    Map<String,Object> itemExtData = (Map<String, Object>) item.get("extData");
                    Map<String,Object> itemObj = new HashMap<String,Object>();
                    itemObj.put("aac001",baseinfo.get("psnNo"));  // 个人电脑号
                    itemObj.put("aac002",baseinfo.get("certno"));  //社会保障号码
                    itemObj.put("aac003",baseinfo.get("psnName"));  // 姓名
                    itemObj.put("aac006",baseinfo.get("brdy"));   // 出生日期
                    itemObj.put("baa027",itemExtData.get("insuplcAdmdvs"));  //地区编码
                    //itemObj.put("aab001",item.get("psnType"));  //单位编码
                    itemObj.put("bka008",itemExtData.get("empName"));  // 单位名称

                    itemObj.put("bka035",item.get("psnType"));   // 人员类别
                    itemObj.put("aae140",item.get("insutype"));   // 险种
                    personinfoList.add(itemObj);
                    break;
                }
            }else{
                Map<String,Object> item = (Map<String, Object>) insuList.get(0);
                Map<String,Object> itemExtData = (Map<String, Object>) item.get("extData");
                //单条个人信息
                Map<String,Object> itemObj = new HashMap<String,Object>();
//                itemObj.put("aac001",itemExtData.get("psnNum"));//个人编码
//                itemObj.put("aac002",itemExtData.get("idNo"));
//                itemObj.put("aac003",itemExtData.get("name"));
//                itemObj.put("aaa027",itemExtData.get("systemNo"));//系统编号
//                itemObj.put("bka013Name",itemExtData.get("systemNo"));//系统编号
//                itemObj.put("bka008",itemExtData.get("empName"));//单位名
//                itemObj.put("aab001",itemExtData.get("empNum"));//单位代码
//                itemObj.put("aac006",itemExtData.get("birthDate"));//生日
//                itemObj.put("bacu18",item.get("acctBalc"));//余额

                itemObj.put("aac001",baseinfo.get("psnNo"));  // 个人电脑号
                itemObj.put("aac002",baseinfo.get("certno"));  //社会保障号码
                itemObj.put("aac003",baseinfo.get("psnName"));  // 姓名
                itemObj.put("aac006",baseinfo.get("brdy"));   // 出生日期
                itemObj.put("baa027",itemExtData.get("insuplcAdmdvs"));  //地区编码
                //itemObj.put("aab001",item.get("psnType"));  //单位编码
                itemObj.put("bka008",itemExtData.get("empName"));  // 单位名称

                itemObj.put("bka035",item.get("psnType"));   // 人员类别
                itemObj.put("aae140",item.get("insutype"));   // 险种

                personinfoList.add(itemObj);
            }
        }
        Map<String,Object> result = new HashMap<String,Object>(){{
            put("personinfo",personinfoList);//基本信息
            put("electronicBillParam",httpResult);//个人信息（险种信息、特殊病种登记信息、异地就医登记信息）
        }};
        return result;
    }

    /**
     * @Menthod addPatientCost
     * @Desrciption 门诊电子凭证患者费用上传
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/3/4 15:18
     * @return: Map
     */

    public Map addPatientCost(HashMap<String, Object> param) {
        String hospCode = (String) param.get("hospCode");//医院编码
        String regCode = (String) param.get("insureRegCode");//机构编码
        String medOrgOrd = (String) param.get("medOrgOrd");//医院结算id
        List<OutptCostDTO> outptCostDTOList = (List<OutptCostDTO>) param.get("outptCostDTOList");//患者费用
        OutptVisitDTO outptVisitDTO = (OutptVisitDTO) param.get("outptVisitDTO");//个人基本信息
        //查询医保就诊信息
        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        insureIndividualVisitDTO.setHospCode(hospCode);
        insureIndividualVisitDTO.setVisitId(outptVisitDTO.getId());
        List<InsureIndividualVisitDTO> insureIndividualVisitDTOList = insureIndividualVisitDAO.findByCondition(insureIndividualVisitDTO);
        if (insureIndividualVisitDTOList == null || insureIndividualVisitDTOList.isEmpty()){ throw new AppException("未找到医保就诊信息。"); }
        insureIndividualVisitDTO = insureIndividualVisitDTOList.get(0);
        if (StringUtils.isNotEmpty(insureIndividualVisitDTO.getPayOrdId())){
            //注销费用
//            HashMap<String,Object> deletePatientParam = new HashMap<String,Object>();
//            deletePatientParam.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
//            deletePatientParam.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
//            deletePatientParam.put("hospCode",hospCode);//医院编码
//            deletePatientParam.put("insureRegCode",regCode);//机构编码
//            this.deletePatientCost(deletePatientParam);
            throw new AppException("电子凭证已经上传了费用，请撤销费用后重新登记再上传");
        }
        //费用传输
        JSONObject app = JSONObject.parseObject(insureIndividualVisitDTO.getPayToken());
        Map<String,Object> httpParam = new HashMap<String,Object>();
        JSONObject data = new JSONObject();
        data.put("appId", "37B0389095E640F89DEE9F5C8D763E17");//app.get("chnlId"));//应用渠道编号
        data.put("orgCodg",insureIndividualVisitDTO.getMedicineOrgCode());//机构编码，不可空
        data.put("orgId",app.get("insuOrg"));//电子凭证机构号，可空
        data.put("ecToken",app.get("ecToken"));//电子凭证授权 ecToken，可空
        data.put("insuCode",app.get("insuOrg"));//参保人所在统筹区编码
        data.put("idNo",app.get("idNo"));//证件号码
        data.put("userName",app.get("userName"));//用户姓名
        data.put("idType",app.get("idType"));//证件类别
        data.put("medOrgOrd",medOrgOrd);//医疗机构订单号，不可空
        data.put("initRxOrd","");//要续方的原处方流水，可空
        data.put("erFlag","0");//急诊标志，可空
        data.put("trumFlag","0");//外伤标志，可空

        data.put("feeType", "01");  // 费用类型，目前写死门诊就诊 不可为空 TODO
        data.put("otpType","1");//门诊类别，不可空 todo
        data.put("medType",insureIndividualVisitDTO.getAka130());//诊疗类别，不可空 todo
        Date now = new Date();
        data.put("chrgDate", DateUtils.format(now,DateUtils.YMD));//收费日期，不可空
        data.put("chrgTime",DateUtils.format(now,"HHmmss"));//收费时间,不可空


        //查询挂号费用信息
        Map<String,String> outptParam = new HashMap<String,String>();
        outptParam.put("hospCode",hospCode);
        outptParam.put("id",outptVisitDTO.getRegisterId());
        outptParam.put("visitId",outptVisitDTO.getId());
        Map<String,Object> outptResult = insureIndividualVisitDAO.selectOutptRegisterById(outptParam);
        /*String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(String.valueOf(outptResult.get("register_no")));
        data.put("rgstSn", m.replaceAll("").trim());//医保挂号流水号*/
        data.put("rgstSn", "");//医保挂号流水号，可空
        data.put("rgstFee",String.valueOf(outptResult.get("rgstFee")));//挂号费用，不可空 todo
        data.put("rgstDeptName",outptResult.get("dept_name"));//挂号科室名称，可空
        data.put("drName",outptResult.get("doctor_name"));//医生姓名，不可空
        if (outptResult.get("doctor_name") == null || "".equals(outptResult.get("doctor_name"))) {
            throw new AppException("挂号医生不能为空");
        }
        data.put("medreformDot","Y");//是否医改网点，不可空

        Map<String,String> certParam = new HashMap<String,String>();
        certParam.put("hospCode",hospCode);
        certParam.put("id",outptVisitDTO.getDoctorId());
        Map<String,Object> sysUser = insureIndividualVisitDAO.selectSysUserByid(certParam);
        data.put("drDeptCodg",sysUser.get("dept_code"));//医生科室编号，不可空
        data.put("caty","");//科室类别
        data.put("drCertNo",sysUser.get("prac_certi_no"));//医生证件号码，不可空
        data.put("rxItemVal",outptCostDTOList.size());//明细上传数量，不可空
        data.put("iptType","");//住院类别，可空
        data.put("iptOpNo",outptVisitDTO.getVisitNo());//住院/门诊号，不可空
        data.put("iptDeptCodg","");//住院科室编码，可空
        data.put("iptDeptName","");//住院科室名称，可空
        data.put("iptBegnDate","");//住院起始日期，可空
        data.put("iptEndDate","");//住院截止日期，可空
        data.put("dscgWay","");//离院方式，可空
        data.put("iptDays","");//住院天数，可空
        data.put("birctrlType","");//计划生育手术类别，可空
        data.put("birmtd","");//生育类别(方式)，可空
        data.put("babyNum","");//胎儿数，可空
        data.put("birctrlDate","");//计划生育手术或生育日期，可空
        data.put("prgDays","");//怀孕天数，可空
        data.put("insuType",insureIndividualVisitDTO.getAae140());//险种类型，不可空 todo
        data.put("psnNo",insureIndividualVisitDTO.getAac001());//人员编码，不可空 todo
        data.put("admDiagDscr","");//入院诊断描述 ，可空
        data.put("admDeptCodg","");//入院科室编码 ，可空
        data.put("admDeptName","");//入院科室名称 ，可空
        data.put("admBed","");//入院床位 ，可空
        data.put("dscgMaindiagCode","");//住院主诊断代码，可空
        data.put("dscgMaindiagName","");//住院主诊断名称，可空
        data.put("midSetlFlag","");//中途结算标志，可空
        String chrgBchnoId = SnowflakeUtils.getId();//获取收费批次号
        data.put("chrgBchno",chrgBchnoId);//收费批次号，不可空 todo
        data.put("psnSetlway","01");//结算方式,不可空 todo
        data.put("rxCircFlag","0");//电子处方流转标志，可空 1：电子处方 ，0不是电子处方，默认0，
        JSONObject extData = new JSONObject();
        extData.put("opter",outptVisitDTO.getCrteName());//经办人
        extData.put("psnNum",insureIndividualVisitDTO.getAac001());//个人编号
        extData.put("docno",outptVisitDTO.getId());//单据号
        extData.put("systemNo","7");
        extData.put("crtChrgCnt","");
        extData.put("crtRgstCnt","");
        JSONArray fees = new JSONArray();//费用信息
        for (OutptCostDTO outptCostDTO : outptCostDTOList){
            JSONObject feesItem = new JSONObject();
            feesItem.put("rxDetlNo",outptCostDTO.getOpdId());//处方明细流水号
            String chrgitmType = Constant.hainan.DICT.chrgitmType.get(outptCostDTO.getItemCode());
            feesItem.put("chrgitmType",StringUtils.isEmpty(chrgitmType) ? "2" : chrgitmType);//收费项目种类
            String chrgType = Constant.hainan.DICT.chrgType.get(outptCostDTO.getBfcCode());
            feesItem.put("chrgType",StringUtils.isEmpty(chrgType) ? "91" : chrgType);//收费类别


            feesItem.put("chigitmCentCodg",outptCostDTO.getCode());//收费项目中心编码
            feesItem.put("used",outptCostDTO.getUsageCode());//用法
            feesItem.put("minunt",outptCostDTO.getNumUnitCode());//最小使用单位
            feesItem.put("fulamtOwnpayFlag","0");
            fees.add(feesItem);
        }
        extData.put("list",fees);//费用明细
        data.put("extData",extData);//医保扩展数据

        JSONArray diseList = new JSONArray();

        String diseaseId = insureIndividualVisitDTO.getVisitIcdCode();
        String diseaseName = insureIndividualVisitDTO.getVisitIcdName();
        if (StringUtils.isNotEmpty(diseaseId)){
            String [] diseaseIdArr = diseaseId.split(",");
            String [] diseaseNameArr = diseaseName.split(",");
            int index = 1;
           for (int i = 0; i < diseaseIdArr.length; i++){
                JSONObject diseItem = new JSONObject();
                diseItem.put("diseDetlType","1");//诊断类别
                diseItem.put("diseDetlSrtNo",String.valueOf(i+1));//诊断排序号
                diseItem.put("diseDetlCodg",diseaseIdArr[i]);//诊断或症状明细编码
                diseItem.put("diseDetlName",diseaseNameArr[i]);//诊断或症状名称
               if(i<1){
                   diseItem.put("maindiagFlag","1");//主诊断标识
               }else{
                   diseItem.put("maindiagFlag","0");//非主诊断标识
               }

                diseList.add(diseItem);
            }
            data.put("diseList",diseList);//诊断或症状明细
        }
        String invoItemNo = SnowflakeUtils.getId();//发票编号
        //获取医保匹配药品项目信息
        Map<String,Object> itemMatchParam = new HashMap<String,Object>();
        itemMatchParam.put("hospCode",hospCode);//医院编码
        itemMatchParam.put("isValid",Constants.SF.S);//是否有效 = 是
        itemMatchParam.put("isMatch",Constants.SF.S);//是否匹配 = 是
        List<String> itemIds = new ArrayList<String>();
        outptCostDTOList.stream().forEach(outptCostDTO -> { itemIds.add(outptCostDTO.getItemId()); });
        itemMatchParam.put("itemIds",itemIds);
        List<InsureItemMatchDTO> insureItemMatchDTOList = insureItemMatchDAO.queryByHospItemId(itemMatchParam);
        BigDecimal totalPrice = new BigDecimal(0);
        JSONArray rxList = new JSONArray();
        for (OutptCostDTO outptCostDTO : outptCostDTOList){
            InsureItemMatchDTO insureItemMatchDTO = null;
            for (InsureItemMatchDTO insureItemMatchDTO2 : insureItemMatchDTOList){
                if (outptCostDTO.getItemId().equals(insureItemMatchDTO2.getHospItemId()) && outptCostDTO.getItemCode().equals(insureItemMatchDTO2.getHospItemType())){
                    insureItemMatchDTO = insureItemMatchDTO2;
                    break;
                }
            }

            JSONObject rxItem = new JSONObject();
            rxItem.put("rxno",outptCostDTO.getOpId());//处方号
            rxItem.put("rxDetlNo",outptCostDTO.getOpdId());//处方明细流水号
            rxItem.put("itemNo",insureItemMatchDTO == null ? outptCostDTO.getItemCode() : insureItemMatchDTO.getInsureItemCode());//项目编号
            rxItem.put("medinsListCodg", regCode);//医药机构目录编码
            rxItem.put("itemName",insureItemMatchDTO == null ? outptCostDTO.getItemName() : insureItemMatchDTO.getInsureItemName());//项目名称
           // rxItem.put("invoItemNo",invoItemNo);//发票项目编号，。todo，rxList定义中“invoItemNo”、“hiItem”字段删除
           // rxItem.put("hiItem",insureItemMatchDTO == null ? 'N' : 'Y');//是否医保项目 todo，rxList定义中“invoItemNo”、“hiItem”字段删除
            //计算单价（优惠后总金额 / 数量 = 单价）
            BigDecimal price = BigDecimalUtils.divide(outptCostDTO.getRealityPrice(),outptCostDTO.getTotalNum());
            totalPrice = BigDecimalUtils.add(outptCostDTO.getRealityPrice(),totalPrice);
            rxItem.put("itemPric",String.valueOf(price));//项目单价
            rxItem.put("itemEmp",outptCostDTO.getNumUnitCode());//项目单位
            rxItem.put("itemSpec",outptCostDTO.getSpec());//项目规格
            rxItem.put("itemCnt",String.valueOf(outptCostDTO.getTotalNum()));//项目数量
            rxItem.put("itemAmt",String.valueOf(outptCostDTO.getRealityPrice()));//项目金额
            rxItem.put("prodBarc","");//商品条形编码s
            rxItem.put("drugFrqu","");//药品频率
            rxItem.put("drugDos","");//药品用量
            rxItem.put("drutDays","");//药品天数
            rxItem.put("dosform","");//剂型
            rxItem.put("empMedcEmp","");//单位用药单位
            rxItem.put("drugTotlnt","");//取药总量
            rxItem.put("drugTotlntEmp","");//取药总量单位
            rxItem.put("drugDelvWay","");//给药途径
            rxItem.put("drugCntDays","");//药量天数
            rxItem.put("drName",outptCostDTO.getDoctorName());//医生姓名

            Map<String,String> itemCertParam = new HashMap<String,String>();
            itemCertParam.put("hospCode",hospCode);
            itemCertParam.put("id",outptCostDTO.getDoctorId());
            Map<String,Object> itemCert = insureIndividualVisitDAO.selectSysUserByid(itemCertParam);
            rxItem.put("drCertNo",itemCert.get("prac_certi_no"));//医生证件号码

            rxItem.put("rxDate", DateUtils.format(outptCostDTO.getCrteTime(),DateUtils.Y_M_D));//处方日期
            rxItem.put("rxTime",DateUtils.format(now,"HHmmss"));//处方时间
            // 根据科室id查询科室信息
            itemCertParam.put("id", outptCostDTO.getDeptId());
            Map<String,Object> dept = insureIndividualVisitDAO.getBaseDeptById(itemCertParam);
            rxItem.put("rxDeptCodg",dept.get("code"));//处方科室编码
            rxItem.put("rxDept",dept.get("name"));//处方科室

            // 根据科室id查询科室信息
            itemCertParam.put("id", outptCostDTO.getExecDeptId());
            Map<String,Object> execDept = insureIndividualVisitDAO.getBaseDeptById(itemCertParam);
            rxItem.put("rxCircFlag","0");//外购处方标志 todo,RxList中无此属性值
            rxItem.put("acordDeptCodg",execDept.get("code"));//受单科室编码
            rxItem.put("acordDeptName",execDept.get("name"));//受单科室名称
            rxItem.put("ordersDrCode","");//受单医生编码
            rxItem.put("ordersDrName","");//受单医生姓名
            rxItem.put("hospApprFlag","1");//医院审批标志 todo,RxList中无此属性值
            rxItem.put("tcmdrugUsedWay","");//中药使用方式
            rxItem.put("etipFlag","");//外检标志
            rxItem.put("etipHospCode","");//外检医院编码
            rxItem.put("dscgTkdrugFlag","");//出院带药标志
            rxItem.put("matnFeeFlag","");//生育费用标志
            rxList.add(rxItem);
        }
        data.put("rxList",rxList);//费用明细
        data.put("feeSumamt",String.valueOf(totalPrice));//费用总金额，不可空

        JSONArray condList = new JSONArray();
        JSONObject condItem = new JSONObject();
        condItem.put("condCodg", insureIndividualVisitDTO.getBka006());//病情编码
        condItem.put("condName", insureIndividualVisitDTO.getBka006Name());//病情名称

        condItem.put("mainCondDscr", insureIndividualVisitDTO.getBka006Name());//主要病情描 todo,CondList中无此属性值

        condList.add(condItem);
        data.put("condList",condList);//病情编码列表

        httpParam.put("data",data);
        httpParam.put("orgId",insureIndividualVisitDTO.getMedicineOrgCode());
        httpParam.put("transType",Constant.hainan.FUNCTION.hosUploadFee);
        // 查询医保访问路径
        // 查询电子凭证地址
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("hospCode", hospCode);
        parameterMap.put("code", "DZPZ_URL");
        Map<String, String> resultMap = insureIndividualVisitDAO.selectSysParameter(parameterMap);
        if (resultMap.isEmpty() || "".equals(resultMap.get("value"))) {
            throw new AppException("系统参数中未配置电子凭证地址(参数名：DZPZ_URL)，请联系管理员配置");
        }
        Map<String,Object> httpResult = requestInsure.callHaiNan(hospCode,regCode,resultMap.get("value"),httpParam);
        httpResult.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
        return httpResult;
    }

    /**
     * @Menthod deletePatientCost
     * @Desrciption 门诊电子凭证撤销费用明细上传
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/3/4 15:36
     * @return: Map
     */
    public Map deletePatientCost(HashMap<String, Object> param) {
        String hospCode = (String) param.get("hospCode");//医院编码
        String regCode = (String) param.get("insureRegCode");//机构编码
        InsureIndividualVisitDTO insureIndividualVisitDTO = (InsureIndividualVisitDTO) param.get("insureIndividualVisitDTO");//个人基本信息
        //JSONObject app = JSONObject.parseObject(insureIndividualVisitDTO.getPayToken());
        Map<String,Object> httpParam = new HashMap<String,Object>();
        JSONObject data = new JSONObject();
        data.put("appId", "37B0389095E640F89DEE9F5C8D763E17");//app.get("chnlId"));//应用渠道编号
        data.put("payOrdId",insureIndividualVisitDTO.getPayOrdId());//支付订单号
        data.put("orgCodg",insureIndividualVisitDTO.getMedicineOrgCode());//机构编号
        data.put("payToken",insureIndividualVisitDTO.getPayToken());//支付 token，可空
        // data.put("idNo",app.get("idNo"));//证件号码
        // data.put("userName",app.get("userName"));//用户姓名
        // data.put("idType",app.get("idType"));//证件类别
        JSONObject extData = new JSONObject();
        extData.put("systemNo",hospCode);//所属系统标识
        data.put("extData",extData);//医保扩展数据
        httpParam.put("data",data);//请求参数
        httpParam.put("transType",Constant.hainan.FUNCTION.hosRevokeOrder);//操作类型
        httpParam.put("orgId",insureIndividualVisitDTO.getMedicineOrgCode());

        // 查询电子凭证地址
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("hospCode", hospCode);
        parameterMap.put("code", "DZPZ_URL");
        Map<String, String> resultMap = insureIndividualVisitDAO.selectSysParameter(parameterMap);
        if (resultMap.isEmpty() || "".equals(resultMap.get("value"))) {
            throw new AppException("系统参数中未配置电子凭证地址(参数名：DZPZ_URL)，请联系管理员配置");
        }
        Map<String,Object> httpResult = requestInsure.callHaiNan(hospCode, regCode,resultMap.get("value"),httpParam);

        return httpResult;
    }

    /**
     * @Menthod deletePatientCostPremium
     * @Desrciption 门诊电子凭证费用退费
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/3/4 15:39
     * @return: Map
     */
    public Map deletePatientCostPremium(HashMap<String, Object> param) {
        String hospCode = (String) param.get("hospCode");//医院编码
        String regCode = (String) param.get("insureRegCode");//机构编码
        OutptVisitDTO outptVisitDTO = (OutptVisitDTO) param.get("outptVisitDTO");//个人基本信息
        OutptSettleDTO outptSettleDTO = (OutptSettleDTO) param.get("outptSettleDTO");

        //查询医保就诊信息
        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        insureIndividualVisitDTO.setHospCode(hospCode);
        insureIndividualVisitDTO.setVisitId(outptVisitDTO.getId());
        List<InsureIndividualVisitDTO> insureIndividualVisitDTOList = insureIndividualVisitDAO.findByCondition(insureIndividualVisitDTO);
        if (insureIndividualVisitDTOList == null || insureIndividualVisitDTOList.isEmpty()){ throw new AppException("未找到医保就诊信息。"); }
        insureIndividualVisitDTO = insureIndividualVisitDTOList.get(0);

        Map<String,Object> httpParam = new HashMap<String,Object>();
        JSONObject data = new JSONObject();
        data.put("appId", "37B0389095E640F89DEE9F5C8D763E17");//null);//应用渠道编号
        data.put("payOrdId", insureIndividualVisitDTO.getPayOrdId());//支付订单号
        data.put("appRefdSn", "11");//应用退款流水号
        data.put("appRefdTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));//应用退费时间
        data.put("totlRefdAmt", String.valueOf(outptSettleDTO.getTotalPrice()));//总退费金额
        data.put("psnAcctRefdAmt", String.valueOf(outptSettleDTO.getAcctPay() != null ? outptSettleDTO.getAcctPay() : "0.00"));//医保个人账户支付
        data.put("fundRefdAmt", String.valueOf(outptSettleDTO.getMiPrice()));//基金支付
        data.put("cashRefdAmt", String.valueOf(outptSettleDTO.getSelfPrice()));//现金退费金额
        data.put("ecToken", insureIndividualVisitDTO.getPayToken());//电子凭证授权 Token
        data.put("refdType", "ALL");//退费类型

        JSONObject extData = new JSONObject();
        extData.put("opter",null);//经办人
        extData.put("systemNo",null);//所属系统标识
        data.put("extData",extData);//医保扩展数据

        httpParam.put("data",data);//
        httpParam.put("orgId",insureIndividualVisitDTO.getMedicineOrgCode());
        httpParam.put("transType",Constant.hainan.FUNCTION.hosRefundSetl);//操作类型

        // 查询电子凭证地址
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("hospCode", hospCode);
        parameterMap.put("code", "DZPZ_URL");
        Map<String, String> resultMap = insureIndividualVisitDAO.selectSysParameter(parameterMap);
        if (resultMap.isEmpty() || "".equals(resultMap.get("value"))) {
            throw new AppException("系统参数中未配置电子凭证地址(参数名：DZPZ_URL)，请联系管理员配置");
        }
        Map<String,Object> httpResult = requestInsure.callHaiNan(hospCode,regCode,resultMap.get("value"),httpParam);

        return httpResult;
    }

    /**
     * @Menthod getPayEffect
     * @Desrciption 门诊电子凭证获取支付结果
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/3/4 15:53
     * @return: Map
     */
    public Map getPayEffect(HashMap<String, Object> param) {
        return null;
    }

}
