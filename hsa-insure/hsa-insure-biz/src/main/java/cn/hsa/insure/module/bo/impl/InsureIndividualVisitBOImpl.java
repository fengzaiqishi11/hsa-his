package cn.hsa.insure.module.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.module.bo.InsureIndividualVisitBO;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualBasicDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualBusinessDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.entity.InsureIndividualBasicDO;
import cn.hsa.module.insure.module.entity.InsureIndividualBusinessDO;
import cn.hsa.module.insure.module.entity.InsureIndividualVisitDO;
import cn.hsa.module.insure.outpt.service.InsureUnifiedPayOutptService;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.outpt.visit.service.OutptVisitService;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.module.bo.impl
 * @Class_name: InsureIndividualVisitBOImpl
 * @Describe(描述):
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/18 20:18
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class InsureIndividualVisitBOImpl extends HsafBO implements InsureIndividualVisitBO {

    @Resource
    private InsureIndividualVisitDAO insureIndividualVisitDAO;

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    @Resource
    private InsureIndividualBasicDAO insureIndividualBasicDAO;

    @Resource
    private InsureIndividualBusinessDAO insureIndividualBusinessDAO;


    @Resource
    private InsureUnifiedPayOutptService insureUnifiedPayOutptService;

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private OutptVisitService outptVisitService_consumer;


    /**
     * @param params 请求参数
     * @Menthod addInsureIndividualVisit
     * @Desrciption 新增医保就诊信息
     * @Author Ou·Mr
     * @Date 2020/11/18 20:16
     * @Return InsureIndividualVisitDO 医保就诊信息
     */
    @Override
    public InsureIndividualVisitDO addInsureIndividualVisit(Map<String, Object> params) {
        OutptVisitDTO outptVisitDTO = (OutptVisitDTO) params.get("param");
        String patientCode = outptVisitDTO.getPatientCode();
        String preferentialTypeId = outptVisitDTO.getPreferentialTypeId();
        //医保信息
        JSONObject jsonObject = JSONObject.parseObject(outptVisitDTO.getInsure());
        Map<String, Object> info = jsonObject.get("info") == null ? new HashMap<String, Object>() : (Map<String, Object>) jsonObject.get("info");//选择的医保信息
        Map<String, String> personinfo = jsonObject.get("personinfo") == null ? new HashMap<String, String>() : (Map<String, String>) jsonObject.get("personinfo");//医保个人信息
        Map<String, String> spinfo = jsonObject.get("spinfo") == null || "".equals(jsonObject.get("spinfo")) ? new HashMap<String, String>() : (Map<String, String>) jsonObject.get("spinfo");//医保个人特殊业务信息
        Map<String, String> injuryorbirthinfo = jsonObject.get("injuryorbirthinfo") == null ? new HashMap<String, String>() : (Map<String, String>) jsonObject.get("injuryorbirthinfo");//医保个人生育信息


        String hospCode = outptVisitDTO.getHospCode();//医院编码
        String visitId = outptVisitDTO.getId();//就诊id
        String insureVisitId = SnowflakeUtils.getId();//医保就诊信息id
        Date now = new Date();//当前时间
        String doctorName = outptVisitDTO.getDoctorName();
        String doctorId = outptVisitDTO.getDoctorId();
        String crteId = outptVisitDTO.getCrteId();//创建人id
        String crteName = outptVisitDTO.getCrteName();//创建人姓名
        String deptId = outptVisitDTO.getDeptId();//就诊科室id
        String deptName = outptVisitDTO.getDeptName();//就诊科室名称
        String iIBId = SnowflakeUtils.getId();//医保基本信息id
        String insureRegCode = (String) info.get("regCode");//医保注册编码
        String aka130 = (String) info.get("aka130");//业务类型
        String aka130Name = (String) info.get("aka130Name");//业务类型名称
        String bka006 = (String) info.get("bka006");//待遇类型
        String bka006Name = (String) info.get("bka006Name");//待遇类型名称
        String hcardBasinfo = (String) info.get("hcardBasinfo");//读卡就诊信息
        String hcardChkinfo = (String) info.get("hcardChkinfo");//读卡校验信息
        String aac001 = personinfo.get("aac001");//个人电脑号
        JSONArray jsonArray = (JSONArray) info.get("diagnose");//诊断信息
        if (jsonArray.size() == 0) {
            throw new AppException("未录入诊断信息");
        }

        //根据医院编码、医保注册编码查询医保配置信息
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);//医院编码
        insureConfigurationDTO.setRegCode(insureRegCode);//医保注册编码
        insureConfigurationDTO.setIsValid(Constants.SF.S);//是否有效
        List<InsureConfigurationDTO> insureConfigurationDTOList = insureConfigurationDAO.findByCondition(insureConfigurationDTO);
        if (insureConfigurationDTOList == null || insureConfigurationDTOList.isEmpty()) {
            throw new NullPointerException("未找到医保机构，请重新获取人员信息。");
        }
        insureConfigurationDTO = insureConfigurationDTOList.get(0);

        // 获取该医保配置是否走统一支付平台，1走，0/null不走
        String isUnifiedPay = insureConfigurationDTO.getIsUnifiedPay();

        /*Map<String, Object> isInsureUnifiedMap = new HashMap<>();
        isInsureUnifiedMap.put("hospCode", hospCode);
        isInsureUnifiedMap.put("code", "UNIFIED_PAY");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();*/
        // 满足条件 说明不是走医保统一支付平台
//        if (sysParameterDTO == null || !Constants.SF.S.equals(sysParameterDTO.getValue())) {
        if (StringUtils.isEmpty(isUnifiedPay) || "0".equals(isUnifiedPay) || "qrcode".equals(info.get("bka895")) || !"1".equals(isUnifiedPay)) {
            //根据就诊id删除医保就诊信息
            insureIndividualVisitDAO.deleteByVisitId(visitId);
        }
//        if (sysParameterDTO != null && Constants.SF.S.equals(sysParameterDTO.getValue())) {
        if (StringUtils.isNotEmpty(isUnifiedPay) && "1".equals(isUnifiedPay)) {
            InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
            insureIndividualVisitDTO.setVisitId(visitId);
            insureIndividualVisitDTO.setHospCode(hospCode);
            insureIndividualVisitDTO = insureIndividualVisitDAO.selectInsureIndividualVisit(insureIndividualVisitDTO);
            if (insureIndividualVisitDTO != null && !StringUtils.isEmpty(insureIndividualVisitDTO.getMedicalRegNo())) {
                throw new AppException("该患者已经已经做了医保登记");
            }
        }
        //医保就诊信息
        InsureIndividualVisitDO insureIndividualVisitDO = new InsureIndividualVisitDO();
        insureIndividualVisitDO.setId(insureVisitId);//id
        insureIndividualVisitDO.setHospCode(hospCode);//医院编码
        insureIndividualVisitDO.setVisitId(visitId);//就诊id
        insureIndividualVisitDO.setMibId(iIBId);//个人基本信息id
        insureIndividualVisitDO.setInsureOrgCode(insureConfigurationDTO.getCode());//医保机构编码
        insureIndividualVisitDO.setInsureRegCode(insureConfigurationDTO.getRegCode());//医保注册编码
        insureIndividualVisitDO.setMedicineOrgCode(insureConfigurationDTO.getOrgCode());//医疗机构编码
        insureIndividualVisitDO.setIsHospital(Constants.SF.F);//是否住院（SF）
        insureIndividualVisitDO.setVisitNo(outptVisitDTO.getVisitNo());//住院号/就诊号
        insureIndividualVisitDO.setAac001(aac001);//个人电脑号
        insureIndividualVisitDO.setMedicalRegNo(null);//医保登记号
        insureIndividualVisitDO.setAka130(aka130);//业务类型
        insureIndividualVisitDO.setAka130Name(aka130Name);//业务类型名称
        insureIndividualVisitDO.setBka006(bka006);//待遇类型
        insureIndividualVisitDO.setBka006Name(bka006Name);//待遇类型名称
        insureIndividualVisitDO.setInjuryBorthSn(null);//业务申请号
        insureIndividualVisitDO.setHcardBasinfo(hcardBasinfo); // 读卡就诊基本信息
        insureIndividualVisitDO.setHcardChkinfo(hcardChkinfo); // 读卡就诊校验信息
        /**
         * 长沙市门特病人  必填主副诊断
         */
        String accessoryDiagnosisName = "";
        String accessoryDiagnosisCode = "";
        if ("14".equals(info.get("aka130").toString()) && "43010150159".equals(info.get("regCode").toString()) && jsonArray.size() > 1) {
            for (int i = 1; i < jsonArray.size(); i++) {
                accessoryDiagnosisName += (String) jsonArray.getJSONObject(i).get("insureIllnessName");
                accessoryDiagnosisName = accessoryDiagnosisName.concat(",");
                accessoryDiagnosisCode += (String) jsonArray.getJSONObject(i).get("insureIllnessCode");
                accessoryDiagnosisCode = accessoryDiagnosisCode.concat(",");
            }
            insureIndividualVisitDO.setVisitIcdCode((String) jsonArray.getJSONObject(0).get("insureIllnessCode"));//就诊疾病编码
            insureIndividualVisitDO.setVisitIcdName((String) jsonArray.getJSONObject(0).get("insureIllnessName"));//就诊疾病名称
            accessoryDiagnosisName = accessoryDiagnosisName.substring(0, accessoryDiagnosisName.lastIndexOf(","));
            accessoryDiagnosisCode = accessoryDiagnosisCode.substring(0, accessoryDiagnosisCode.lastIndexOf(","));
            insureIndividualVisitDO.setAccessoryDiagnosisCode(accessoryDiagnosisCode);
            insureIndividualVisitDO.setAccessoryDiagnosisName(accessoryDiagnosisName);
        } else {
            insureIndividualVisitDO.setVisitIcdCode((String) jsonArray.getJSONObject(0).get("insureIllnessCode"));//就诊疾病编码
            insureIndividualVisitDO.setVisitIcdName((String) jsonArray.getJSONObject(0).get("insureIllnessName"));//就诊疾病名称
        }
        // 电子凭证医保登记标识
        if ("qrcode".equals(info.get("bka895"))) {
            JSONObject electronicBillParam = (JSONObject) jsonObject.get("electronicBillParam");
            String app = (String) electronicBillParam.get("app");
            if (StringUtils.isEmpty(app)) {
                throw new AppException("使用电子凭证未进行扫码，请进行扫码。");
            }
            insureIndividualVisitDO.setIsEcqr(Constants.SF.S);
            insureIndividualVisitDO.setPayToken(app);//电子凭证token
            insureIndividualVisitDO.setPayTokenDengJi(app);
        }
        insureIndividualVisitDO.setVisitTime(now);//就诊时间
        insureIndividualVisitDO.setVisitDrptId(deptId);//就诊科室ID
        insureIndividualVisitDO.setVisitDrptName(deptName);//就诊科室名称
        insureIndividualVisitDO.setVisitAreaId(null);//就诊病区ID
        insureIndividualVisitDO.setVisitAreaName(null);//就诊病区名称
        insureIndividualVisitDO.setVisitBerth(null);//就诊床位
        insureIndividualVisitDO.setStartingPrice(null);//起付线金额
        insureIndividualVisitDO.setShiftHospCode(null);//转入医院编码
        insureIndividualVisitDO.setOutHospCode(null);//转出医院编码
        insureIndividualVisitDO.setCause(null);//住院原因
        insureIndividualVisitDO.setRemark(null);//备注
        insureIndividualVisitDO.setCrteId(crteId);//创建人id
        insureIndividualVisitDO.setCrteName(crteName);//创建人姓名
        insureIndividualVisitDO.setCrteTime(now);//创建时间
        insureIndividualVisitDO.setInsuplcAdmdvs(personinfo.get("insuplc_admdvs"));  // 参保地医保区划
        insureIndividualVisitDO.setInjuryBorthSn(injuryorbirthinfo.get("serial_pers"));
        insureIndividualVisitDO.setMdtrtCertType((String) info.get("bka895"));
        insureIndividualVisitDO.setMdtrtCertNo((String) info.get("bka896"));
        insureIndividualVisitDO.setMatnType(MapUtils.get(info,"matnType"));
        insureIndividualVisitDO.setBirctrlType(MapUtils.get(info,"birctrlType"));
        if(!StringUtils.isEmpty(MapUtils.get(info,"birctrlMatnDate"))){
            insureIndividualVisitDO.setBirctrlMatnDate(DateUtils.parse(MapUtils.get(info,"birctrlMatnDate"),DateUtils.Y_M_D));
        }else{
            insureIndividualVisitDO.setBirctrlMatnDate(null);
        }
        insureIndividualVisitDAO.insertSelective(insureIndividualVisitDO);


        //根据医院编码、个人电脑号
        Map<String, String> delBasicParam = new HashMap<String, String>();
        delBasicParam.put("hospCode", hospCode);
        delBasicParam.put("aac001", aac001);
        //医保个人基本信息
        InsureIndividualBasicDO insureIndividualBasicDO = new InsureIndividualBasicDO();
        insureIndividualBasicDO.setId(iIBId);//主键id
        insureIndividualBasicDO.setHospCode(hospCode);//医院编码
        insureIndividualBasicDO.setAac001(aac001);//个人电脑号
        insureIndividualBasicDO.setInjuryBorthSn(injuryorbirthinfo.get("serial_pers"));//个人业务号(工伤、生育)
        insureIndividualBasicDO.setAaa027(personinfo.get("aaa027"));//分级统筹中心编码
        insureIndividualBasicDO.setAaa027Name(personinfo.get("aaa027_name"));//分级统筹中心名称
        insureIndividualBasicDO.setAac003(personinfo.get("aac003"));//姓名
        insureIndividualBasicDO.setAac004(personinfo.get("aac004"));//性别
        insureIndividualBasicDO.setAac002(personinfo.get("aac002"));//公民身份号码
        insureIndividualBasicDO.setAaz500(personinfo.get("bka100"));//社会保障号码
        insureIndividualBasicDO.setAae005(personinfo.get("aae005"));//联系电话
        insureIndividualBasicDO.setAac006(personinfo.get("aac006"));//出生日期
        insureIndividualBasicDO.setOrgcode(personinfo.get("baa027"));//地区编码
        insureIndividualBasicDO.setAab999(personinfo.get("aab999"));//单位管理码
        insureIndividualBasicDO.setAab019(personinfo.get("aab019"));//单位类型
        insureIndividualBasicDO.setAab001(personinfo.get("aab001"));//单位编码
        insureIndividualBasicDO.setBka008(personinfo.get("bka008"));//单位名称
        insureIndividualBasicDO.setBka035(personinfo.get("bka035"));//人员类别编码
        insureIndividualBasicDO.setBka035Name(personinfo.get("bka035Name"));//人员类别名称
        insureIndividualBasicDO.setAac008(personinfo.get("aac008"));//人员状态编码
        insureIndividualBasicDO.setAac008Name(personinfo.get("aac008Name"));//人员状态名称
        insureIndividualBasicDO.setBac001(personinfo.get("bac001"));//公务员级别编码
        insureIndividualBasicDO.setBac001Name(personinfo.get("bac001Name"));//公务员级别名称
        insureIndividualBasicDO.setAka130(aka130);//业务类型
        insureIndividualBasicDO.setAka130Name(aka130Name);//业务类型名称
        insureIndividualBasicDO.setBka006(bka006);//待遇类型
        insureIndividualBasicDO.setBka006Name(bka006Name);//待遇类型名称
        insureIndividualBasicDO.setAac148(personinfo.get("aac148"));//补助类型
        insureIndividualBasicDO.setAac148Name(personinfo.get("aac148_name"));//补助类型名称
        insureIndividualBasicDO.setAac013(personinfo.get("aac013"));//用工形式编码
        insureIndividualBasicDO.setAac013Name(personinfo.get("aac013Name"));//用工形式名称
        insureIndividualBasicDO.setAae140(personinfo.get("aae140"));//险种类型（码表AAE140）
        insureIndividualBasicDO.setBka888(personinfo.get("bka888"));//基金冻结状态
        insureIndividualBasicDO.setAkc252(BigDecimalUtils.convert(personinfo.get("bacu18")));//个人帐户余额
        insureIndividualBasicDO.setAac066(personinfo.get("aac066"));//参保身份
        insureIndividualBasicDO.setAab301(personinfo.get("aab301"));//所属行政区代码.常住地
        insureIndividualBasicDO.setAac031(personinfo.get("aac031"));//人员缴费状态{1参保缴费，2暂停缴费，3终止缴费}
        insureIndividualBasicDO.setAae030Last(StringUtils.isEmpty(personinfo.get("aae030")) ? null : DateUtils.parse(personinfo.get("aae030"), DateUtils.YMD));//上次住院入院日期
        insureIndividualBasicDO.setAae031Last(StringUtils.isEmpty(personinfo.get("aae031")) ? null : DateUtils.parse(personinfo.get("aae031"), DateUtils.YMD));//上次住院出院日期
        insureIndividualBasicDO.setAae030Special(StringUtils.isEmpty(personinfo.get("aae030_special")) ? null : DateUtils.parse(personinfo.get("aae030_special"), DateUtils.YMD));//特殊业务申请有效开始时间
        insureIndividualBasicDO.setAae031Special(StringUtils.isEmpty(personinfo.get("aae031_special")) ? null : DateUtils.parse(personinfo.get("aae031_special"), DateUtils.YMD));//特殊业务申请有效结束时间
        insureIndividualBasicDO.setAaz267(spinfo.get("aaz267"));//医疗待遇申请事件ID
        insureIndividualBasicDO.setBaa027(personinfo.get("baa027"));//参保地中心编码
        insureIndividualBasicDO.setBaa027Name(personinfo.get("baa027_name"));//参保地中心名称
        insureIndividualBasicDO.setAkc193(spinfo.get("akc193"));//疾病ICD编码
        insureIndividualBasicDO.setAkc193Name(spinfo.get("bkz101"));//疾病ICD名称
        insureIndividualBasicDO.setAac158(null);//低保对象标识
        insureIndividualBasicDO.setAkc026(null);//参加公务员医疗补助标识
        insureIndividualBasicDO.setBaa301(personinfo.get("baa027"));//参保地行政区划代码(指参保人所在地的行政区划代码)
        insureIndividualBasicDO.setAab300(personinfo.get("baa027_name"));//参保地社会保险经办机构名称(指参保人所在地的社会保险经办机构名称)
        insureIndividualBasicDO.setAkc009(personinfo.get("bka035"));//参保人员类别
        insureIndividualBasicDO.setBka010(null);//本年住院次数
        insureIndividualBasicDO.setBkh015(personinfo.get("bkh015"));//套餐标识
        insureIndividualBasicDO.setCrteId(crteId);//创建人id
        insureIndividualBasicDO.setCrteName(crteName);//创建人姓名
        insureIndividualBasicDO.setCrteTime(now);//创建时间
        insureIndividualBasicDO.setCardIden(MapUtils.get(info,"cardIden"));
        insureIndividualBasicDAO.insertSelective(insureIndividualBasicDO);

        insureIndividualBusinessDAO.deleteByVisitId(visitId);
        //医保个人业务申请信息
        InsureIndividualBusinessDO insureIndividualBusinessDO = new InsureIndividualBusinessDO();
        insureIndividualBusinessDO.setId(SnowflakeUtils.getId());//id
        insureIndividualBusinessDO.setHospCode(hospCode);//医院编码
        insureIndividualBusinessDO.setVisitId(visitId);//就诊id
        insureIndividualBusinessDO.setMibId(iIBId);//个人基本信息id
        insureIndividualBusinessDO.setAaz267(spinfo.get("aaz267"));//业务申请序列号
        insureIndividualBusinessDO.setBearNo(injuryorbirthinfo.get("bka042"));//生育序列号
        insureIndividualBusinessDO.setAka130(aka130);//业务类型
        insureIndividualBusinessDO.setAka130Name(aka130Name);//业务名称
        insureIndividualBusinessDO.setVoipCode(injuryorbirthinfo.get("identify_flag"));//业务认定编号
        insureIndividualBusinessDO.setBka006(bka006);//待遇类型
        insureIndividualBusinessDO.setBka006Name(bka006Name);//待遇名称
        insureIndividualBusinessDO.setAka083(spinfo.get("aka083"));//申请内容编码
        insureIndividualBusinessDO.setAka083Name(spinfo.get("aka083_name"));//申请内容名称
        insureIndividualBusinessDO.setAae030(StringUtils.isEmpty(spinfo.get("aae030")) ? null : DateUtils.parse(spinfo.get("aae030"), DateUtils.YMD));//申请生效日期
        insureIndividualBusinessDO.setAae031(StringUtils.isEmpty(spinfo.get("aae031")) ? null : DateUtils.parse(spinfo.get("aae031"), DateUtils.YMD));//申请失效日期
        insureIndividualBusinessDO.setAka120(spinfo.get("aka120"));//申请病种编码/疾病编码
        insureIndividualBusinessDO.setAka121(spinfo.get("aka121"));//申请病种名称/疾病名称
        insureIndividualBusinessDO.setVulnerability(injuryorbirthinfo.get("alc022"));//受伤部位
        insureIndividualBusinessDO.setPayMark(null);//先行支付标志（SF）
        insureIndividualBusinessDO.setCrteId(crteId);//创建人ID
        insureIndividualBusinessDO.setCrteName(crteName);//创建人姓名
        insureIndividualBusinessDO.setCrteTime(now);//创建时间
        insureIndividualBusinessDAO.insertSelective(insureIndividualBusinessDO);
        /**
         * 通过获取系统参数来判断 是否走医保统一支付平台
         * 如果是走医保统一支付平台，需要判断是否重复登记（根据就诊id，医院编码查询insure_individual_visit表）
         */
//        if (sysParameterDTO != null && Constants.SF.S.equals(sysParameterDTO.getValue())) {
        InsureIndividualVisitDTO responseData = new InsureIndividualVisitDTO();
        if (StringUtils.isNotEmpty(isUnifiedPay) && "1".equals(isUnifiedPay)) {
            try {
                /**
                 * 医保登记成功以后，也就是身份验证以后 上传诊断信息
                 *
                 */
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("hospCode", hospCode);
                paramMap.put("crteId",crteId);
                paramMap.put("crteName",crteName);
                paramMap.put("visitId", visitId);
                paramMap.put("jsonArray", jsonArray);
                /**
                 * 对接医保统一支付平台： 门诊挂号、登记
                 * 医保登记成功以后保存医保唯一返回流水号
                 */
                if (!"qrcode".equals(info.get("bka895"))) {  // 不是电子凭证才执行
                    responseData = insureUnifiedPayOutptService.UP_2201(paramMap).getData();
                    insureIndividualVisitDAO.updateInsureMedicalRegNo(responseData);
                }
                Map<String, Object> outptVisitMap = new HashMap<>();
                outptVisitDTO = new OutptVisitDTO();
                outptVisitDTO.setHospCode(hospCode);
                outptVisitDTO.setId(visitId);
                outptVisitDTO.setDoctorId(doctorId);
                outptVisitDTO.setDoctorName(doctorName);
                outptVisitDTO.setPatientCode(patientCode);
                outptVisitDTO.setPreferentialTypeId(preferentialTypeId);
                outptVisitMap.put("hospCode", hospCode);
                outptVisitMap.put("outptVisitDTO", outptVisitDTO);
                paramMap.put("crteName",crteName);
                paramMap.put("crteId",crteId);
                paramMap.put("visitId",visitId);
                paramMap.put("outptVisitDTO", outptVisitDTO);
                outptVisitService_consumer.updateOutptVisit(outptVisitMap).getData();
                if (!"qrcode".equals(info.get("bka895"))) {
                    insureUnifiedPayOutptService.UP_2203(paramMap);
                }
            } catch (Exception e) {
                log.error("就诊id:"+visitId+"门诊登记调医保接口失败！"+e.getMessage(),e.getStackTrace());
                if (ObjectUtil.isNotEmpty(responseData)
                        && ObjectUtil.isNotEmpty(responseData.getMedicalRegNo())
                        && ObjectUtil.isNotEmpty(responseData.getVisitNo())) {
                    log.error("=======调门诊挂号撤销开始=======");
                    Map<String, Object> httpParam = initBackParam(personinfo, crteId, crteName, aac001, insureConfigurationDTO, responseData);
                    log.error("调门诊挂号撤销入参为：{}", JSON.toJSONString(httpParam));
                    String s = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), JSON.toJSONString(httpParam));
                    log.error("=======调门诊挂号撤销结束=======");
                }
                throw new AppException("门诊登记调医保接口失败！"+e.getMessage());
            }
        }
        return insureIndividualVisitDO;
    }

    private Map<String, Object> initBackParam(Map<String, String> personinfo, String crteId, String crteName, String aac001, InsureConfigurationDTO insureConfigurationDTO, InsureIndividualVisitDTO responseData) {
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("psn_no",aac001);
        inputMap.put("mdtrt_id",responseData.getMedicalRegNo());
        inputMap.put("ipt_otp_no",responseData.getVisitNo());

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("data",inputMap);

        Map<String, Object> httpParam = new HashMap<>();
        httpParam.put("infno","2202");// 交易编号
        httpParam.put("insuplc_admdvs",personinfo.get("insuplc_admdvs"));// 参保地医保区划
        httpParam.put("medins_code",insureConfigurationDTO.getOrgCode());// 定点医药机构编号
        httpParam.put("insur_code",insureConfigurationDTO.getRegCode());// 医保中心编码
        httpParam.put("mdtrtarea_admvs",insureConfigurationDTO.getMdtrtareaAdmvs());// 就医地医保区划
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        httpParam.put("opter",crteId);
        httpParam.put("opter_name",crteName);
        httpParam.put("input", JSON.toJSON(dataMap));
        return httpParam;
    }

    /**
     * @Menthod findByCondition
     * @Desrciption 查询医保就诊信息
     * @param insureIndividualVisitDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/11/29 15:57
     * @Return java.util.List<cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO>
     */
    @Override
    public List<InsureIndividualVisitDTO> findByCondition(InsureIndividualVisitDTO insureIndividualVisitDTO) {
        return insureIndividualVisitDAO.findByCondition(insureIndividualVisitDTO);
    }

    /**
     * @Menthod editInsureIndividualVisit
     * @Desrciption 编辑医保就诊信息
     * @param insureIndividualVisitDO 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/1 15:01
     * @Return int 受影响行数
     */
    @Override
    public int editInsureIndividualVisit(InsureIndividualVisitDO insureIndividualVisitDO) {
        return insureIndividualVisitDAO.updateByPrimaryKeySelective(insureIndividualVisitDO);
    }

    /**
     * @Menthod insertIndividualVisit
     * @Desrciption 新增医保就诊信息
     * @param insureIndividualVisitDTO 请求参数
     * @Author 廖继广
     * @Date 2020/12/21 14:58
     * @Return Boolean
     */
    @Override
    public Boolean insertIndividualVisit(InsureIndividualVisitDTO insureIndividualVisitDTO) {
        return insureIndividualVisitDAO.insert(insureIndividualVisitDTO) > 0;
    }


    /**
     * @Menthod insertIndividualVisit
     * @Desrciption 新增医保就诊信息
     * @param visitId 请求参数
     * @Author 廖继广
     * @Date 2020/12/21 14:58
     * @Return Boolean
     */
    @Override
    public Boolean deleteByVisitId(String visitId) {
        return insureIndividualVisitDAO.deleteByVisitId(visitId) > 0;
    }


    /**
     * @param insureVisitParam
     * @Method getInsureIndividualVisitById
     * @Desrciption 根据就诊id和医院编码查询医保就诊信息
     * @Param
     * @Author fuhui
     * @Date 2021/3/4 8:58
     * @Return
     */
    @Override
    public InsureIndividualVisitDTO getInsureIndividualVisitById(Map<String, Object> insureVisitParam) {
        return insureIndividualVisitDAO.getInsureIndividualVisitById(insureVisitParam);
    }

    /**
     * @param map
     * @Method deleteInsureVisitById
     * @Desrciption 退费以后，取消门诊挂号登记
     * @Param
     * @Author fuhui
     * @Date 2021/3/16 19:50
     * @Return
     */
    @Override
    public Boolean deleteInsureVisitById(Map<String, Object> map) {
        return insureIndividualVisitDAO.deleteInsureVisitById(map);
    }

    /**
     * @param inptVisitDTO
     * @Method updateInsureInidivdual
     * @Desrciption 修改医保病人信息
     * @Param
     * @Author fuhui
     * @Date 2021/5/20 22:41
     * @Return
     */
    @Override
    public Boolean updateInsureInidivdual(InptVisitDTO inptVisitDTO) {
        return insureIndividualVisitDAO.updateInsureInidivdual(inptVisitDTO);
    }

    /**
     * @param insureIndividualVisitDTO
     * @Method selectInsureInfo
     * @Desrciption 查询医保就诊信息
     * @Param
     * @Author fuhui
     * @Date 2021/7/30 15:32
     * @Return
     */
    @Override
    public InsureIndividualVisitDTO selectInsureInfo(InsureIndividualVisitDTO insureIndividualVisitDTO) {
        return insureIndividualVisitDAO.selectInsureInfo(insureIndividualVisitDTO);
    }

    /**
     * @Method updateInsureSettleId
     * @Desrciption  取消结算更新医保就诊表的结算id
     * @Param
     *
     * @Author fuhui
     * @Date   2021/8/4 10:18
     * @Return
     **/
    @Override
    public Boolean updateInsureSettleId(Map<String, Object> settleMap)
    {
        return insureIndividualVisitDAO.updateInsureSettleId(settleMap);
    }

    /**
     * @param insureUnifiedPayParam
     * @Method queryAllInsureIndiviualVisit
     * @Desrciption 根据就诊id 查询医保就诊信息
     * @Param
     * @Author fuhui
     * @Date 2021/8/9 19:59
     * @Return
     */
    @Override
    public List<InsureIndividualVisitDTO> queryAllInsureIndiviualVisit(Map<String, Object> insureUnifiedPayParam) {

        return insureIndividualVisitDAO.queryAllInsureIndiviualVisit(insureUnifiedPayParam);
    }

    /**
     * @param insureUnifiedPayParam
     * @Method selectMaxAndMinRegisterTime
     * @Desrciption
     * @Param
     * @Author fuhui
     * @Date 2021/8/9 20:56
     * @Return
     */
    @Override
    public InsureIndividualVisitDTO selectMaxAndMinRegisterTime(Map<String, Object> insureUnifiedPayParam) {
        return insureIndividualVisitDAO.selectMaxAndMinRegisterTime(insureUnifiedPayParam);
    }

    /**
     * @param insureIndividualVisitDTO
     * @Method selectHalfVisit
     * @Desrciption 查询出中途结算次数
     * @Param
     * @Author fuhui
     * @Date 2022/2/15 10:15
     * @Return
     */
    @Override
    public Integer selectHalfVisit(InsureIndividualVisitDTO insureIndividualVisitDTO) {
        return insureIndividualVisitDAO.selectHalfVisit(insureIndividualVisitDTO);
    }

    /**
     * @param insureIndividualVisitDTO
     * @Method updateInsureSettleCounts
     * @Desrciption 更新中途结算医保标志和次数
     * @Param
     * @Author fuhui
     * @Date 2022/2/16 8:55
     * @Return
     */
    @Override
    public void updateInsureSettleCounts(InsureIndividualVisitDTO insureIndividualVisitDTO) {
        insureIndividualVisitDAO.updateInsureSettleCounts(insureIndividualVisitDTO);
    }

    /**
     * 根据医保就诊ID获取医保就诊信息
     * @param insureVisitParam
     * @Author 医保开发二部-湛康
     * @Date 2022-05-07 15:12
     * @return cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO
     */
    @Override
    public InsureIndividualVisitDTO getInsureIndividualVisitByMedRegNo(Map<String, Object> insureVisitParam) {
      return insureIndividualVisitDAO.getInsureIndividualVisitByMedRegNo(insureVisitParam);
    }

    /**
     * 根据就诊凭证类型和就诊凭证编号获取医保就诊信息
     * @param insureVisitParam
     * @Author 医保开发二部-湛康
     * @Date 2022-06-16 16:38
     * @return cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO
     */
    @Override
    public InsureIndividualVisitDTO getInsureIndividualVisitByMdtrtCertNo(Map<String, Object> insureVisitParam) {
      return insureIndividualVisitDAO.getInsureIndividualVisitByMdtrtCertNo(insureVisitParam);
    }
}
