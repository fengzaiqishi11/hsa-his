package cn.hsa.insure.module.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.enums.FunctionEnum;
import cn.hsa.insure.unifiedpay.bo.impl.InsureItfBOImpl;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.BaseReqUtilFactory;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.InsureUnifiedCommonUtil;
import cn.hsa.insure.util.ResultBean;
import cn.hsa.insure.util.UnifiedCommon;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO;
import cn.hsa.module.drgdip.bo.DrgDipBusinessOptInfoBO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.doctor.service.DoctorAdviceService;
import cn.hsa.module.insure.drgdip.bo.DrgDipResultBO;
import cn.hsa.module.insure.drgdip.dto.DrgDipAuthDTO;
import cn.hsa.module.insure.drgdip.dto.DrgDipComboDTO;
import cn.hsa.module.insure.drgdip.dto.DrgDipResultDTO;
import cn.hsa.module.insure.drgdip.dto.DrgDipResultDetailDTO;
import cn.hsa.module.insure.drgdip.service.DrgDipResultService;
import cn.hsa.module.insure.inpt.service.InsureUnifiedBaseService;
import cn.hsa.module.insure.module.bo.InsureGetInfoBO;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureGetInfoDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualCostDAO;
import cn.hsa.module.insure.module.dto.*;
import cn.hsa.module.insure.module.entity.InsureIndividualCostDO;
import cn.hsa.module.insure.module.entity.TcmDiseScoreDO;
import cn.hsa.module.insure.module.service.InsureDictService;
import cn.hsa.module.insure.outpt.service.InsureUnifiedPayReversalTradeService;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.prescribe.service.OutptDoctorPrescribeService;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;


import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class InsureGetInfoBOImpl extends HsafBO implements InsureGetInfoBO {

    @Resource
    private InsureGetInfoDAO insureGetInfoDAO;

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    @Resource
    private InsureIndividualCostDAO insureIndividualCostDAO;


    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private InsureUnifiedBaseService insureUnifiedBaseService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private InsureUnifiedCommonUtil insureUnifiedCommonUtil;

    @Resource
    private InsureItfBOImpl insureItfBO;

    @Resource
    private BaseReqUtilFactory baseReqUtilFactory;

    @Resource
    private DrgDipResultService drgDipResultService;

    @Resource
    private DrgDipResultBO drgDipResultBO;

    @Resource
    DrgDipBusinessOptInfoBO drgDipBusinessOptInfoBO;

    @Resource
    private UnifiedCommon unifiedCommon;

    @Resource
    private InsureDictService insureDictService_consumer;

    private static final String hiPaymtd_tcm = "60";

    @Resource
    RedisUtils redisUtils;
    /**
     * @Method getSettleInfo
     * @Desrciption 1、交易输入结算清单信息为单行数据，输入其他信息均为多行数据；
     * 2、输入项信息按照《医疗保障基金结算清单填写规范》中的规范要求填写；
     * 3、每次接口调用只上传一位患者的信息。
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-10 14:11
     * @Return java.util.Map
     **/
    @Override
    public Map<String, Object> insertSettleInfo(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        // 获取医疗机构信息
        InsureIndividualVisitDTO insureIndividualVisitDTO = insureUnifiedCommonUtil.commonGetVisitInfo(map);
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        String insureRegCode = insureIndividualVisitDTO.getInsureRegCode();
        insureConfigurationDTO.setOrgCode(insureIndividualVisitDTO.getMedicineOrgCode());  // 医疗机构编码
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if (insureConfigurationDTO == null) {
            throw new AppException("根据" + insureRegCode + "医保机构编码获取医保机构配置信息为空");
        }

//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.putAll(map);
//
//        // 参保地医保区划
//        paramMap.put("insuplcAdmdvs", insureIndividualVisitDTO.getInsuplcAdmdvs());
//        paramMap.put("orgCode", insureIndividualVisitDTO.getMedicineOrgCode());
//        paramMap.put("configCode", insureIndividualVisitDTO.getInsureRegCode());
//        paramMap.put("insureIndividualVisit", insureIndividualVisitDTO);
//        paramMap.put("isHospital", Constants.SF.F);
        // 调用统一支付平台接口


//        Map<String,Object> resultDataMap = insureItfBO.executeInsur(FunctionEnum.getEnum(functionCode), paramMap);
        Map listMap = new HashMap();
        String functionCode = selectParamSettleInfo(map);
        if (StringUtils.isEmpty(functionCode)) {
            throw new AppException("请先维护系统参数SETTLELEVEL" + "值为医疗结算清单功能号");
        }
        if (!"4101A".equals(functionCode)) {
            // 收费项目信息节点
            listMap.put("iteminfo", selectItemInfo(map));
            // 基金信息节点
            listMap.put("payinfo", selectPayInfo(map));
        }
        // 门诊慢特病诊断信息
        listMap.put("opspdiseinfo", selectOpsdiseaseInfo(map));
        // 手术操作信息节点
        listMap.put("oprninfo", selectOprninfo(map));
        // 住院诊断信息节点
        listMap.put("diseinfo", selectDiseaseInfo(map));
        // 重症监护信息
        listMap.put("icuinfo", selectIcuInfo(map));
        //  结算清单信息
        listMap.put("setlinfo", selectBaseSetlInfo(map));

        //输血信息
        listMap.put("bldinfo", selectBldInfo(map));

        //TODO 此处插入业务操作日志 类型为4.上传
        Map<String, Object> businessLogMap = new HashMap<>();
        businessLogMap.put("businessId",MapUtils.get(MapUtils.get(listMap, "setlinfo"), "id"));
        businessLogMap.put("optType","4");
        businessLogMap.put("optTypeName","上传");
        businessLogMap.put("type","");
        businessLogMap.put("businessType","1");
        businessLogMap.put("isForce",MapUtils.get(map, "isForce"));
        businessLogMap.put("forceUploadInfo",MapUtils.get(map, "forceUploadInfo"));
        businessLogMap.put("hospCode",hospCode);
        businessLogMap.put("insureRegCode",insureRegCode);
        businessLogMap.put("hospName",MapUtils.get(MapUtils.get(listMap, "setlinfo"), "fixmedins_name"));
        businessLogMap.put("orgCode",MapUtils.get(MapUtils.get(listMap, "setlinfo"), "fixmedins_code"));
        businessLogMap.put("insureSettleId",MapUtils.get(MapUtils.get(listMap, "setlinfo"), "setl_id"));
        businessLogMap.put("medicalRegNo",MapUtils.get(MapUtils.get(listMap, "setlinfo"), "mdtrt_id"));
        businessLogMap.put("settleId",MapUtils.get(map, "setlId"));
        businessLogMap.put("visitId",insureIndividualVisitDTO.getVisitId());
        businessLogMap.put("psnNo",MapUtils.get(MapUtils.get(listMap, "setlinfo"), "psn_no"));
        businessLogMap.put("psnName",MapUtils.get(MapUtils.get(listMap, "setlinfo"), "psn_name"));
        businessLogMap.put("certNo",MapUtils.get(MapUtils.get(listMap, "setlinfo"), "certno"));
        businessLogMap.put("deptId",MapUtils.get(MapUtils.get(listMap, "setlinfo"), "deptId"));
        businessLogMap.put("sex",MapUtils.get(MapUtils.get(listMap, "setlinfo"), "gend"));
//        businessLogMap.put("age",MapUtils.get(map, "age")) ;
        businessLogMap.put("insueType",MapUtils.get(MapUtils.get(listMap, "setlinfo"), "hi_type"));
        businessLogMap.put("inptTime",MapUtils.get(MapUtils.get(listMap, "setlinfo"), "adm_time"));
        businessLogMap.put("outptTime",MapUtils.get(MapUtils.get(listMap, "setlinfo"), "dscg_time"));
        businessLogMap.put("medType",MapUtils.get(MapUtils.get(listMap, "setlinfo"), "medType"));
        businessLogMap.put("medTypeName",MapUtils.get(MapUtils.get(listMap, "setlinfo"), "medTypeName"));
        businessLogMap.put("deptName",MapUtils.get(MapUtils.get(listMap, "setlinfo"), "deptName"));
        businessLogMap.put("doctorId",MapUtils.get(MapUtils.get(listMap, "setlinfo"), "chfpdr_code"));
        businessLogMap.put("doctorName",MapUtils.get(MapUtils.get(listMap, "setlinfo"), "chfpdr_name"));
        businessLogMap.put("inptDiagnose",MapUtils.get(MapUtils.get(listMap, "setlinfo"), "inptDiagnose"));
        businessLogMap.put("outptDiagnose",MapUtils.get(MapUtils.get(listMap, "setlinfo"), "outptDiagnose"));
        businessLogMap.put("totalFee",MapUtils.get(MapUtils.get(listMap, "setlinfo"), "totalFee"));
        businessLogMap.put("payFee",MapUtils.get(MapUtils.get(listMap, "setlinfo"), "payFee"));
        businessLogMap.put("selfFee",MapUtils.get(MapUtils.get(listMap, "setlinfo"), "selfFee"));
        businessLogMap.put("cashPayFee",MapUtils.get(MapUtils.get(listMap, "setlinfo"), "cashPayFee"));
        businessLogMap.put("inputJosn",map);
        businessLogMap.put("crtId",MapUtils.get(map, "crteId"));
        businessLogMap.put("crtName",MapUtils.get(map, "crtName"));
        drgDipBusinessOptInfoBO.insertDrgDipBusinessOptInfoLog(businessLogMap);

        //根据DIP_DRG_MODE值判断质控模式
        DrgDipResultDTO dto = new DrgDipResultDTO();
        //结算清单 business_type 1
        dto.setVisitId(map.get("visitId").toString());
        dto.setHospCode(map.get("hospCode").toString());
        dto.setBusinessType("1");
        //查询权限
        Map<String, Object> map2 = new HashMap<>();
        map2.put("hospCode", map.get("hospCode").toString());
        DrgDipAuthDTO drgDipAuthDTO = drgDipResultService.checkDrgDipBizAuthorization(map2).getData();
        HashMap map1 = new HashMap();
        map1.put("drgDipResultDTO",dto);
        map1.put("hospCode",map.get("hospCode").toString());
        map1.put("drgDipAuthDTO",drgDipAuthDTO);
        DrgDipComboDTO combo = drgDipResultService.getDrgDipInfoByParam(map1).getData();
        //查询质控模式
        Map<String, Object> sysMap = new HashMap<>();
        sysMap.put("hospCode", hospCode);
        sysMap.put("code", "DIP_DRG_MODEL");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(sysMap).getData();
        if (ObjectUtil.isNotEmpty(sysParameterDTO) && Constant.UnifiedPay.ISMAN.S.equals(sysParameterDTO.getValue()) && !Constant.UnifiedPay.ZKZT.ZKWC.equals(combo.getQuaStaus())){
            throw new AppException("违规质控结果处理完成后，才能医保上传");
        }
        map.put("msgName", "医疗保障结算清单上传");
        map.put("visitId", insureIndividualVisitDTO.getVisitId());
        map.put("isHospital", insureIndividualVisitDTO.getIsHospital());
        Map<String, Object> resultDataMap = insureUnifiedCommonUtil.commonInsureUnified(hospCode, insureRegCode, functionCode, listMap, map);
        Map<String, Object> outputDataMap = MapUtils.get(resultDataMap, "output");
        Map<String, Object> dataMap = MapUtils.get(outputDataMap, "data");
        map.put("setlListId", MapUtils.get(dataMap, "setl_list_id"));
        if (StringUtils.isEmpty(MapUtils.get(map, "stasType"))) {
            map.put("stasType", "0");
        }
        insureGetInfoDAO.updateInsureGetInfo(map);
        return listMap;
    }

    /**
     * @Method selectBldInfo
     * @Desrciption 查询输血信息节点信息
     * @Param
     * @Author fuhui
     * @Date 2021/12/4 9:05
     * @Return
     **/
    private List<Map<String, Object>> selectBldInfo(Map<String, Object> map) {
        return insureGetInfoDAO.selectBldInfo(map);
    }

    private String selectParamSettleInfo(Map<String, Object> map) {
        map.put("code", "SETTLELEVEL");
        SysParameterDTO data = sysParameterService_consumer.getParameterByCode(map).getData();
        if (data == null) {
            throw new AppException("请先维护系统参数SETTLELEVEL" + "值为医疗结算清单信息");
        }
        String value = data.getValue();
        String functionCode = "";
        Map<String, Object> stringObjectMap = JSON.parseObject(value, Map.class);
        for (String key : stringObjectMap.keySet()) {
            if ("functionCode".equals(key)) {
                functionCode = MapUtils.get(stringObjectMap, key);
                break;
            }
        }
        return functionCode;
    }

    /**
     * @Method selectBaseSetlInfo
     * @Desrciption 医疗保障基金结算清单 ： 查询结算信息
     * @Param
     * @Author fuhui
     * @Date 2021/11/3 19:17
     * @Return
     **/
    private Map<String, Object> selectBaseSetlInfo(Map<String, Object> map) {
        InsureSettleInfoDTO settleInfoDTO = insureGetInfoDAO.selectBaseSetlInfo(map);
        if (null == settleInfoDTO) {
            throw new AppException("请先维护保存结算清单信息");
        }
        // 湖南 结算清单信息
        Map setlinfo = new HashMap();
        setlinfo.put("psn_no", settleInfoDTO.getHiNo()); // 个人电脑号
        setlinfo.put("mdtrt_id", settleInfoDTO.getMdtrtId()); // 就医登记号
        setlinfo.put("setl_id", settleInfoDTO.getInsureSettleId()); // 结算ID
        setlinfo.put("hi_no", settleInfoDTO.getHiNo()); //医保编号
        setlinfo.put("medcasno", settleInfoDTO.getMedcasno()); // 病案号
        setlinfo.put("dcla_time", DateUtils.format(settleInfoDTO.getDclaTime(), DateUtils.Y_M_D)); //结算清单申报时间

        setlinfo.put("ntly", "CHN"); // 国籍  默认是中国

        setlinfo.put("prfs", settleInfoDTO.getPrfs()); // 职业
        setlinfo.put("curr_addr", settleInfoDTO.getCurrAddr()); // 现住址
        setlinfo.put("emp_name", settleInfoDTO.getEmpName()); // 工作单位 *******
        setlinfo.put("emp_addr", settleInfoDTO.getEmpAddr()); // 工作单位地址 *******
        setlinfo.put("emp_tel", settleInfoDTO.getEmpTel()); // 工作单位电话 *******
        setlinfo.put("poscode", settleInfoDTO.getPoscode()); // 工作单位邮编 *******
        setlinfo.put("coner_name", settleInfoDTO.getConerName()); // 联系人姓名
        setlinfo.put("patn_rlts", settleInfoDTO.getPatnRlts()); // 与患者关系
        setlinfo.put("coner_addr", settleInfoDTO.getConerAddr()); // 联系人地址
        setlinfo.put("coner_tel", settleInfoDTO.getConerTel()); // 联系人电话

        setlinfo.put("nwb_admtype", settleInfoDTO.getNebAdmType()); // 新生儿入院类型
        setlinfo.put("nwbbirwt", settleInfoDTO.getBabyBirthWeight()); // 新生儿出生体重
        setlinfo.put("nwbadmwt", settleInfoDTO.getBabyInWeight()); // 新生儿入院体重

        setlinfo.put("opsp_diag_caty", settleInfoDTO.getVisitDrptName()); // 诊断科别 *******
        setlinfo.put("opsp_mdtrt_date", settleInfoDTO.getOpspMdtrtDate()); // 就诊日期 *******

        setlinfo.put("adm_way", settleInfoDTO.getAdmWay()); // 入院途径 *******
        //根据参数判断治疗类别上传方式
        Map<String, Object> sysMap = new HashMap<>();
        sysMap.put("hospCode", MapUtils.get(map, "hospCode"));
        sysMap.put("code", "TRT_TYPE");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(sysMap).getData();
        if (sysParameterDTO == null) {
            throw new AppException("请先维护系统参数TRT_TYPE" + "值为结算清单治疗类别上传方式");
        }
        //参数值为0的时候按界面展示上传
        if( Constant.UnifiedPay.ISMAN.F.equals(sysParameterDTO.getValue())){
            // 治疗类别 *******
            if (StringUtils.isNotEmpty(settleInfoDTO.getTrtType())) {
                String trtType = settleInfoDTO.getTrtType();
                if ("10".equals(trtType)) {
                    setlinfo.put("trt_type", "1"); // 西医
                } else if ("21".equals(trtType)) {
                    setlinfo.put("trt_type", "2.1"); // 中医
                } else if ("22".equals(trtType)) {
                    setlinfo.put("trt_type", "2.2"); // 民族医
                } else if ("30".equals(trtType)) {
                    setlinfo.put("trt_type", "3"); // 中西医
                } else {
                    setlinfo.put("trt_type", trtType);
                }
            }
        }else if(Constant.UnifiedPay.ISMAN.S.equals(sysParameterDTO.getValue())){
            // 治疗类别 *******
            if (StringUtils.isNotEmpty(settleInfoDTO.getTrtType())) {
                String trtType = settleInfoDTO.getTrtType();
                if ("1".equals(trtType)) {
                    setlinfo.put("trt_type", "10"); // 西医
                } else if ("2.1".equals(trtType)) {
                    setlinfo.put("trt_type", "21"); // 中医
                } else if ("2.2".equals(trtType)) {
                    setlinfo.put("trt_type", "22"); // 民族医
                } else if ("3".equals(trtType)) {
                    setlinfo.put("trt_type", "30"); // 中西医
                } else {
                    setlinfo.put("trt_type", trtType);
                }
            }
        }
        if (ObjectUtil.isNotEmpty(settleInfoDTO.getAdmTime())) { //对时间对象做判空处理才能 用date工具类做处理
            setlinfo.put("adm_time", DateUtils.format(settleInfoDTO.getAdmTime(), DateUtils.Y_M_DH_M_S)); // 入院时间 *******
        }

        setlinfo.put("refldept_dept", settleInfoDTO.getRefldeptDept()); // 转科科别

        if (ObjectUtil.isNotEmpty(settleInfoDTO.getDscgTime())) { //对时间对象做判空处理才能 用date工具类做处理
            setlinfo.put("dscg_time", DateUtils.format(settleInfoDTO.getDscgTime(), DateUtils.Y_M_DH_M_S)); // 出院时间 *******
        }
        setlinfo.put("dscg_caty", settleInfoDTO.getDscgCaty()); // 出院科别

        setlinfo.put("otp_wm_dise", settleInfoDTO.getOptWmDise()); // 门（急）诊诊断 *******
        setlinfo.put("wm_dise_code", settleInfoDTO.getOptWmDiseCode()); // 西医诊断疾病代码 *******

        setlinfo.put("otptcmdise", settleInfoDTO.getOptTcmDise()); // 门（急）诊中医诊断 *******
        setlinfo.put("tcmdisecode", settleInfoDTO.getTcmDiseCode()); // 中医诊断代码 *******

        setlinfo.put("vent_used_dura", settleInfoDTO.getVentUsedDura()); // 呼吸机使用时长 *******
        setlinfo.put("pwcry_bfadm_coma_dura", settleInfoDTO.getPwcryBfadmComaDura()); // 颅脑损伤患者入院前昏迷时长 *******
        setlinfo.put("pwcry_afadm_coma_dura", settleInfoDTO.getPwcryAfadmComaDura()); //颅脑损伤患者入院后昏迷时长

        setlinfo.put("lv1_nurscare_days", settleInfoDTO.getLv1NurscareDays()); // 一级护理天数 *******
        setlinfo.put("scd_nurscare_days", settleInfoDTO.getScdNurscareDays()); // 二级护理天数 *******
        setlinfo.put("lv3_nursecare_days", settleInfoDTO.getLv3NursecareDays()); // 三级护理天数 *******
        setlinfo.put("dscg_way", settleInfoDTO.getDscgWay()); // 离院方式*******
        setlinfo.put("acp_medins_name", settleInfoDTO.getAcpMedinsName()); // 拟接收机构名称 *******
        setlinfo.put("acp_medins_code", settleInfoDTO.getAcpMedinsName()); // 拟接收机构代码 ******

        //判断票据代码不能为空
        if(StringUtils.isEmpty(settleInfoDTO.getBillCode())){
            throw new AppException("票据代码不能为空");
        }
        if(StringUtils.isEmpty(settleInfoDTO.getBillNo())){
            throw new AppException("票据号码不能为空");
        }
        setlinfo.put("bill_code", settleInfoDTO.getBillCode()); // 票据代码**********
        setlinfo.put("bill_no", settleInfoDTO.getBillNo()); // 票据号码**********
        setlinfo.put("biz_sn", settleInfoDTO.getBizSn()); // 业务流水号
        setlinfo.put("days_rinp_flag_31", settleInfoDTO.getDaysRinpFlag31()); // 是否有出院31天再住院计划 *******
        setlinfo.put("days_rinp_pup_31", settleInfoDTO.getDaysRinpPup31()); // 出院31天内再住院目的 *******
        setlinfo.put("chfpdr_name", settleInfoDTO.getChfpdrName()); // 主诊医生姓名 *******
        setlinfo.put("chfpdr_code", settleInfoDTO.getChfpdrCode()); // 主诊医生代码 *******
        if (ObjectUtil.isNotEmpty(settleInfoDTO.getSetlBegnDate())) { //对时间对象做判空处理才能 用date工具类做处理
            setlinfo.put("setl_begn_date", DateUtils.format(settleInfoDTO.getSetlBegnDate(), DateUtils.Y_M_DH_M_S)); // 结算开始日期
        }
        if (ObjectUtil.isNotEmpty(settleInfoDTO.getSetlEnDate())) { //对时间对象做判空处理才能 用date工具类做处理
            setlinfo.put("setl_end_date", DateUtils.format(settleInfoDTO.getSetlEnDate(), DateUtils.Y_M_DH_M_S)); // 结算结束日期
        }

        setlinfo.put("medins_fill_dept", settleInfoDTO.getMedinsFillDept()); // 医疗机构填报部门
        setlinfo.put("medins_fill_psn", settleInfoDTO.getMedinsFillPsn()); // 医疗机构填报人
        setlinfo.put("resp_nurs_code", settleInfoDTO.getZrNurseCode()); // 责任护士

        setlinfo.put("stas_type", "0"); // 状态分类

        setlinfo.put("hi_paymtd", settleInfoDTO.getHiPaymtd()); // 医保支付方式


        //甘肃 江西
        setlinfo.put("fixmedins_name", settleInfoDTO.getFixmedinsName()); // 定点医药机构名称
        setlinfo.put("fixmedins_code", settleInfoDTO.getFixmedinsCode()); // 定点医药机构编号
        setlinfo.put("hi_setl_lv", settleInfoDTO.getHiSetlLv());//医保结算等级
        setlinfo.put("psn_name", settleInfoDTO.getPsnName());// 人员姓名

        setlinfo.put("gend", settleInfoDTO.getGend());// 性别
        if (ObjectUtil.isNotEmpty(settleInfoDTO.getBrdy())) { //对时间对象做判空处理才能 用date工具类做处理
            setlinfo.put("brdy", DateUtils.format(settleInfoDTO.getBrdy(), DateUtils.Y_M_D)); // 出生日期
        }
        setlinfo.put("age", settleInfoDTO.getAge()); // 年龄
        setlinfo.put("nwb_age", settleInfoDTO.getNebAge()); // （年龄不足1周岁）年龄
        setlinfo.put("naty", settleInfoDTO.getNaty()); // 民族
        setlinfo.put("patn_cert_type", settleInfoDTO.getPatnCertType()); // 患者证件类别
        setlinfo.put("certno", settleInfoDTO.getCertNo()); // 证件号码
        setlinfo.put("hi_type", settleInfoDTO.getHiType()); // 医保类型  也就是险种   sp_psn_type
        setlinfo.put("insuplc", settleInfoDTO.getInsuplc()); // 参保地 *******
        setlinfo.put("sp_psn_type", settleInfoDTO.getSpPsnType()); // 特殊人员类型

        setlinfo.put("nwb_adm_type", settleInfoDTO.getNebAdmType()); // 新生儿入院类型
        setlinfo.put("nwb_bir_wt", settleInfoDTO.getBabyBirthWeight()); // 新生儿出生体重
        setlinfo.put("nwb_adm_wt", settleInfoDTO.getBabyInWeight()); // 新生儿入院体重
        setlinfo.put("ipt_med_type", settleInfoDTO.getIptMedType()); // 住院医疗类型

        setlinfo.put("adm_caty", settleInfoDTO.getAdmCaty()); // 入院科别
        setlinfo.put("act_ipt_days", settleInfoDTO.getActIptDays()); // 实际住院天数 *******
        setlinfo.put("otp_tcm_dise", settleInfoDTO.getOptTcmDise()); // 门（急）诊中医诊断 *******
        setlinfo.put("tcm_dise_code", settleInfoDTO.getTcmDiseCode()); // 中医诊断代码 *******
        setlinfo.put("diag_code_cnt", settleInfoDTO.getDiagCodeCnt()); // 诊断代码计数 *******
        setlinfo.put("oprn_oprt_code_cnt", settleInfoDTO.getOprnOprtCodeCnt()); // 手术操作代码计数 *******
        setlinfo.put("bld_cat", settleInfoDTO.getBldCat()); //输血品种
        setlinfo.put("bld_amt", settleInfoDTO.getBldAmt()); //输血量
        setlinfo.put("bld_unt", settleInfoDTO.getBldUnt()); //输血计量单位
        setlinfo.put("spga_nurscare_days", settleInfoDTO.getSpgaNurscareDays()); // 特级护理天数 *******

        setlinfo.put("psn_selfpay", settleInfoDTO.getPsnSelfpay()); // 个人自付
        setlinfo.put("psn_ownpay", settleInfoDTO.getPsnOwnpay()); // 个人自费
        setlinfo.put("acct_pay", settleInfoDTO.getAcctPay()); // 个人账户支出
        setlinfo.put("psn_cashpay", settleInfoDTO.getPsnCashpay()); // 个人现金支付

        setlinfo.put("hsorg", settleInfoDTO.getHsorg()); // 医保机构
        setlinfo.put("hsorg_opter", settleInfoDTO.getHosrgOpter()); // 医保机构经办人

        return setlinfo;
    }

    /**
     * @Method selectOprninfo
     * @Desrciption 医疗保障基金结算清单 -- 查询重症监护信息
     * @Param
     * @Author fuhui
     * @Date 2021/11/3 15:44
     * @Return
     **/
    private List<Map<String, Object>> selectIcuInfo(Map<String, Object> map) {
        return insureGetInfoDAO.selectIcuInfo(map);
    }

    /**
     * @Method selectOprninfo
     * @Desrciption 医疗保障基金结算清单 -- 查询住院诊断信息
     * @Param 1.因为his的住院医保业务, 有医保入院办理和医保出院办理两个主诊断  但是结算清单只能上传一个
     * @Author fuhui
     * @Date 2021/11/3 15:44
     * @Return
     **/
    private List<Map<String, Object>> selectDiseaseInfo(Map<String, Object> map) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, Object> sysMap = new HashMap<>();
        sysMap.put("hospCode", MapUtils.get(map, "hospCode"));
        sysMap.put("code", "ONLY_QUERY_WMDIAG");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(sysMap).getData();
        if (ObjectUtil.isNotEmpty(sysParameterDTO) && "1".equals(sysParameterDTO.getValue())){
            //只查询西医诊断信息
            mapList = insureGetInfoDAO.selectXyDisease(map);
        }else {
            //查询西医诊断信息和中医诊断信息
            mapList = insureGetInfoDAO.selectDiseaseInfo(map);
        }

        List<String> diagnoseList = Stream.of("101", "102", "201", "202", "203").collect(Collectors.toList());
        mapList.stream().forEach(item -> {
            String typeCode = MapUtils.get(item, "diag_type");
            String isMain = MapUtils.getVS(item, "maindiag_flag");
            String diagType = MapUtils.get(item, "diag_type");
            if (diagnoseList.contains(typeCode)) {
                item.put("adm_cond_type", "4");
            }
            if ("201".equals(typeCode) && "1".equals(isMain)) {
                item.put("maindiag_flag", "0");
            }
            if ("204".equals(diagType)) {
                item.put("diag_type", "1");
            }
            //【ID1003812】【广州科大】结算清单上传主诊断取西医主诊断
            if (Constant.UnifiedPay.ISMAN.S.equals(isMain) && "2".equals(diagType)){
                item.put("maindiag_flag", "0");
            }
            //中医诊断码值转换成医保的中医诊断类型码值  1:西医主要诊断 2:西医其它诊断 3: 中医主病诊断 4.中医主证诊断
            if ("2".equals(diagType)) {
                item.put("diag_type", "3");
            }
            if ("3".equals(diagType)) {
                item.put("diag_type", "4");
            }
        });
        return mapList;
    }

    /**
     * @Method selectOprninfo
     * @Desrciption 医疗保障基金结算清单 -- 查询门诊慢特病诊断信息
     * @Param
     * @Author fuhui
     * @Date 2021/11/3 15:44
     * @Return
     **/
    private List<Map<String, Object>> selectOpsdiseaseInfo(Map<String, Object> map) {
        List<Map<String, Object>> mapList = insureGetInfoDAO.selectOpsdiseaseInfo(map);
        if (!ListUtils.isEmpty(mapList)) {
            mapList.stream().forEach(item -> {
                // 调用珠海4101接口时   门诊慢特病诊断信息 :无手术编码和名称报错 所以增加条件判断
                if (StringUtils.isEmpty(MapUtils.get(item, "oprn_oprt_name"))) {
                    item.put("oprn_oprt_name", "无");
                }
                if (StringUtils.isEmpty(MapUtils.get(item, "oprn_oprt_code"))) {
                    item.put("oprn_oprt_code", "无");
                }
            });
        }
        return mapList;
    }

    /**
     * @Method selectOprninfo
     * @Desrciption 医疗保障基金结算清单 -- 查询手术操作信息
     * @Param
     * @Author fuhui
     * @Date 2021/11/3 15:44
     * @Return
     **/
    private List<Map<String, Object>> selectOprninfo(Map<String, Object> map) {
        return insureGetInfoDAO.selectOprninfo(map);
    }

    /**
     * @Method selectPayInfo
     * @Desrciption 医疗保障基金结算清单 -- 查询基金支付信息
     * @Param
     * @Author fuhui
     * @Date 2021/11/3 15:16
     * @Return
     **/
    private Object selectPayInfo(Map<String, Object> map) {
        List<Map<String, Object>> mapList = insureGetInfoDAO.selectHanderPayInfo(map);
        return mapList;
    }

    /**
     * @Method saveInsureSettleInfo
     * @Desrciption 保存医疗保障基金结算清单信息
     * @Param
     * @Author fuhui
     * @Date 2021/11/3 14:48
     * @Return
     **/
    @Override
    public Map saveInsureSettleInfo(Map<String, Object> map) {
        // 保存手术信息
        insertOperInfo(map);
        // 保存重症监护信息
        insertIcuInfo(map);
        // 保存门诊慢特病诊断信息
        insertOpspdiseinfo(map);
        //校验中医病种分值规则
        Map tcmMap = chkTcmDiseMatch(map);
        if (ObjectUtil.isNotEmpty(MapUtil.getStr(tcmMap,"message"))) {
            return tcmMap;
        }
        // 保存 住院诊断信息
        insertDiseaseInfo(map);
        // 保存结清单信息
        Map<String, Object> setleInfoMap = insertSetleInfo(map);
        // 保存输血信息
        insertBldInfo(map);
        //TODO 此处插入业务操作日志 类型为4.上传
        Map<String, Object> businessLogMap = new HashMap<>();
        businessLogMap.put("businessId",MapUtils.get(setleInfoMap, "id"));
        businessLogMap.put("optType","2");
        businessLogMap.put("optTypeName","保存");
        businessLogMap.put("type","1");
        businessLogMap.put("businessType","1");
        businessLogMap.put("isForce",MapUtils.get(setleInfoMap, "isForce"));
        businessLogMap.put("forceUploadInfo",MapUtils.get(setleInfoMap, "forceUploadInfo"));
        businessLogMap.put("hospCode",MapUtils.get(setleInfoMap, "hospCode"));
        businessLogMap.put("insureRegCode",MapUtils.get(setleInfoMap, "insureRegCode"));
        businessLogMap.put("hospName",MapUtils.get(setleInfoMap, "fixmedinsName"));
        businessLogMap.put("orgCode",MapUtils.get(setleInfoMap, "orgCode"));
        businessLogMap.put("insureSettleId",MapUtils.get(setleInfoMap, "insureSettleId"));
        businessLogMap.put("medicalRegNo",MapUtils.get(setleInfoMap, "medicalRegNo"));
        businessLogMap.put("settleId",MapUtils.get(setleInfoMap, "setlId"));
        businessLogMap.put("visitId",MapUtils.get(setleInfoMap, "visitId"));
        businessLogMap.put("psnNo",MapUtils.get(setleInfoMap, "psnNo"));
        businessLogMap.put("psnName",MapUtils.get(setleInfoMap, "psnName"));
        businessLogMap.put("certNo",MapUtils.get(setleInfoMap, "certno"));
        businessLogMap.put("deptId",MapUtils.get(setleInfoMap, "deptId"));
        businessLogMap.put("sex",MapUtils.get(setleInfoMap, "gend"));
//        businessLogMap.put("age",MapUtils.get(map, "age")) ;
        businessLogMap.put("insueType",MapUtils.get(setleInfoMap, "hiType"));
        businessLogMap.put("inptTime",MapUtils.get(setleInfoMap, "admTime"));
        businessLogMap.put("outptTime",MapUtils.get(setleInfoMap, "dscgTime"));
        businessLogMap.put("medType",MapUtils.get(setleInfoMap, "medType"));
        businessLogMap.put("medTypeName",MapUtils.get(setleInfoMap, "medTypeName"));
        businessLogMap.put("deptName",MapUtils.get(setleInfoMap, "deptName"));
        businessLogMap.put("doctorId",MapUtils.get(setleInfoMap, "doctorId"));
        businessLogMap.put("doctorName",MapUtils.get(setleInfoMap, "doctorName"));
        businessLogMap.put("inptDiagnose",MapUtils.get(setleInfoMap, "inptDiagnose"));
        businessLogMap.put("outptDiagnose",MapUtils.get(setleInfoMap, "outptDiagnose"));
        businessLogMap.put("totalFee",MapUtils.get(setleInfoMap, "totalFee"));
        businessLogMap.put("payFee",MapUtils.get(setleInfoMap, "payFee"));
        businessLogMap.put("selfFee",MapUtils.get(setleInfoMap, "selfFee"));
        businessLogMap.put("cashPayFee",MapUtils.get(setleInfoMap, "cashPayFee"));
        businessLogMap.put("inputJosn",map);
        businessLogMap.put("crtId",MapUtils.get(map, "crteId"));
        businessLogMap.put("crtName",MapUtils.get(map, "crtName"));
        drgDipBusinessOptInfoBO.insertDrgDipBusinessOptInfoLog(businessLogMap);
        return new HashMap();
    }

    /**
     * @Method insertBldInfo
     * @Desrciption
     * @Param
     * @Author fuhui
     * @Date 2021/12/3 17:11
     * @Return
     **/
    private void insertBldInfo(Map<String, Object> map) {
        List<Map<String, Object>> bldInfoMapList = MapUtils.get(map, "bldinfo");
        bldInfoMapList = insertCommonSettleInfo(map, bldInfoMapList);
        insureGetInfoDAO.deleteBldInfo(map);
        if (!ListUtils.isEmpty(bldInfoMapList)) {
            bldInfoMapList.stream().forEach(x ->{
                deleteConstantBar(x);
            });
            insureGetInfoDAO.deleteBldInfo(map);
            insureGetInfoDAO.insertBldInfo(bldInfoMapList);
        }
    }

    /**
     * @param map
     * @Method deleteSettleInfo
     * @Desrciption 重置医疗保障结算清单信息
     * 1.如果已经上传则不允许 清空重置
     * @Param
     * @Author fuhui
     * @Date 2021/11/4 13:58
     * @Return
     */
    @Override
    public Boolean deleteSettleInfo(Map<String, Object> map) {
        InsureSettleInfoDTO insureSettleInfoDTO = insureGetInfoDAO.querySettlInfo(map);
        if (insureSettleInfoDTO != null && StringUtils.isEmpty(insureSettleInfoDTO.getSettleNo())) {
            throw new AppException("该结算清单信息已经上传至医保,不能重置内容");
        }
        // 重置setleinfo主节点信息
        insureGetInfoDAO.deleteSettleInfo(map);
        // 重置icu节点信息
        insureGetInfoDAO.deleteIcuInfo(map);
        // 重置门诊慢特病节点信息
        insureGetInfoDAO.deleteOpspdiseinfo(map);
        // 重置基金支付节点信息
        insureGetInfoDAO.deletePayInfo(map);
        // 重置住院诊断节点信息
        insureGetInfoDAO.deleteDiseaseInfo(map);
        // 重置收费项目节点信息
        insureGetInfoDAO.deleteItemInfo(map);
        // 重置手术节点信息
        insureGetInfoDAO.deleteOprninfo(map);
        // 重置输血节点信息
        insureGetInfoDAO.deleteBldInfo(map);
        return true;
    }

    /**
     * @Method updateSettleInfo
     * @Desrciption 修改医疗保障结算清单信息
     * @Param
     * @Author liuhuiming
     * @Date 2022/04/22 13:58
     * @Return
     **/
    @Override
    public Boolean updateSettleInfo(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        // 获取患者信息
        InsureIndividualVisitDTO insureIndividualVisitDTO = insureUnifiedCommonUtil.commonGetVisitInfo(map);
        //获取医保配置
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(insureIndividualVisitDTO.getMedicineOrgCode());  // 医疗机构编码
        insureConfigurationDTO.setRegCode(insureIndividualVisitDTO.getInsureOrgCode());
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if (insureConfigurationDTO == null) {
            throw new AppException("根据" + insureIndividualVisitDTO.getInsureOrgCode() + "医保机构编码获取医保机构配置信息为空");
        }
        //查询状态分类
        InsureSettleInfoDTO insureSettleInfoDTO = insureGetInfoDAO.querySettlInfo(map);
        String stasType = Constant.UnifiedPay.ISMAN.S;
        if (insureSettleInfoDTO == null) {
            throw new AppException("未查询到结算清单信息");
        }
        //如果是已提交，修改为未提交
        if (Constant.UnifiedPay.ISMAN.S.equals(insureSettleInfoDTO.getStasType())) {
            stasType = Constant.UnifiedPay.ISMAN.F;
        }
        //如果是未提交，修改为已提交
        if (Constant.UnifiedPay.ISMAN.F.equals(insureSettleInfoDTO.getStasType())) {
            stasType = Constant.UnifiedPay.ISMAN.S;
        }
        String functionCode = selectParamSettleInfo(map);
        if (StringUtils.isEmpty(functionCode)) {
            throw new AppException("请先维护系统参数SETTLELEVEL" + "值为医疗结算清单功能号");
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("psn_no", insureIndividualVisitDTO.getAac001());
        dataMap.put("setl_id", insureIndividualVisitDTO.getInsureSettleId());
        dataMap.put("stas_type", stasType);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("stastinfo", dataMap);

        paramMap.put("hospCode", hospCode);
        paramMap.put("configRegCode", insureConfigurationDTO.getRegCode());
        paramMap.put("orgCode", insureConfigurationDTO.getOrgCode());
        paramMap.put("isHospital", Constants.SF.S);
        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.MR_PATIENT_SETTL_UPLOAD_UPDATE.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(hospCode);
        interfaceParamDTO.setIsHospital(Constants.SF.S);
        interfaceParamDTO.setVisitId(insureIndividualVisitDTO.getVisitId());
        // 调用统一支付平台接口
        insureItfBO.executeInsur(FunctionEnum.MR_PATIENT_SETTL_UPLOAD_UPDATE, interfaceParamDTO);

        //修改状态
        map.put("stasType", stasType);
        insureGetInfoDAO.updateStasType(map);
        return true;
    }

    /**
     * @param map
     * @Method selectLoadingSettleInfo
     * @Desrciption 加载保存到数据库的数据信息
     * @Param
     * @Author fuhui
     * @Date 2021/11/6 10:21
     * @Return
     */
    @Override
    public Map<String, Object> selectLoadingSettleInfo(Map<String, Object> map) {
        Map<String, Object> resultDataMap = new HashMap<>();
        Map<String, Object> data = insureUnifiedBaseService.querySettleDeInfo(map).getData();
        List<Map<String, Object>> setldetail = MapUtils.get(data, "setldetail");
        // 基金分项排除 职工基本医疗保险统筹基金（310101）     城乡居民基本医疗保险基金（390101）
        // 基金分项排除 职工基本医疗保险统筹基金（310101）     城乡居民基本医疗保险基金（390101）
        setldetail = setldetail.stream().filter(item -> !"310101".equals(MapUtils.get(item, "setl_proc_info")) && !"310100".equals(MapUtils.get(item, "setl_proc_info")) &&
                        !"390101".equals(MapUtils.get(item, "setl_proc_info")) && !"390100".equals(MapUtils.get(item, "setl_proc_info"))).
                collect(Collectors.toList());
        Map<String, Object> setlinfoMap = MapUtils.get(data, "setlinfo");

        Map<String, Object> setlinfo = selectLoadingSetlMsg(map);
        // 查询收费项目信息
        List<Map<String, Object>> itemInfoList = insureGetInfoDAO.selectItemInfo(map);
        //固定项目名称
        List<String> medChrgitmTypeList = Stream.of("01","02","03","04","05","06","07","08","09","10","11","12","13","14","15").collect(Collectors.toList());
        Map<String, List<Map<String, Object>>> groupMap = itemInfoList.stream().
                collect(Collectors.groupingBy(item -> MapUtils.get(item, "medChrgitm")));
        for(String medChrgitmType:medChrgitmTypeList){
            if(!groupMap.containsKey(medChrgitmType)){
                Map<String, Object> pMap = new HashMap<>();
                pMap.put("amt", BigDecimal.ZERO.setScale(2));
                pMap.put("claaSumfee", BigDecimal.ZERO.setScale(2));
                pMap.put("clabAmt", BigDecimal.ZERO.setScale(2));
                pMap.put("fulamtOwnpayAmt", BigDecimal.ZERO.setScale(2));
                pMap.put("othAmt", BigDecimal.ZERO.setScale(2));
                pMap.put("medChrgitm", medChrgitmType);
                itemInfoList.add(pMap);
            }
        }
        //重新排序
        itemInfoList.sort((o1, o2) -> o1.get("medChrgitm").toString().compareTo(o2.get("medChrgitm").toString()));
        // 查询重症监护信息
        List<Map<String, Object>> icuinfoList = insureGetInfoDAO.selectIcuInfoForMap(map);
        // 手术操作信息
        List<Map<String, Object>> oprnfoList = insureGetInfoDAO.selectOprninfoForMap(map);
        List<Map<String, Object>> opspdiseinfoList = new ArrayList<>();
        Map<String, Object> diseaseMap = new HashMap<>();
        String isHospital = MapUtils.get(map, "isHospital");
        if (Constants.SF.F.equals(isHospital)) {
            //  门诊慢特病诊断信息
            opspdiseinfoList = insureGetInfoDAO.selectOpspdiseinfoForMap(map);
            //4.封装住院诊断信息 没有信息，封装空信息
            diseaseMap.put("diseaseCount",0);
            diseaseMap.put("xiCollect",new ArrayList<>());
            diseaseMap.put("zxCollect",new ArrayList<>());
        }else {
            // 住院诊断信息
//        Map<String, Object> diseaseMap = handerSetleDiseaInfo(map);
            diseaseMap = handerInptDiagnose(map);
        }
        // 基金支付信息
        List<Map<String, Object>> payinfoList = insureGetInfoDAO.selectPayinfoForMap(map);
        //单病种费用集合
        List<Map<String, Object>> dbzInfo = new ArrayList<>();
        //判断是否为单病种住院
        if(setlinfo.get("medType")!= null){
            Map<String, Object> dbzInfoMap = new HashMap<>();
            BigDecimal sumDetItemFeeSumamt = new BigDecimal(0.00); // 总费用
            BigDecimal AClassFee = new BigDecimal(0.00);  // 甲类费用
            BigDecimal BClassFee = new BigDecimal(0.00);  // 乙类费用
            BigDecimal CClassFee = new BigDecimal(0.00);  // 丙类费用
            BigDecimal otherClassFee = new BigDecimal(0.00); // 其他费用
            for(Map<String, Object> map1:itemInfoList){
                sumDetItemFeeSumamt = BigDecimalUtils.add(sumDetItemFeeSumamt,BigDecimalUtils.convert(map1.get("amt").toString()));
                AClassFee = BigDecimalUtils.add(AClassFee,BigDecimalUtils.convert(map1.get("claaSumfee").toString()));
                BClassFee = BigDecimalUtils.add(BClassFee,BigDecimalUtils.convert(map1.get("clabAmt").toString()));
                CClassFee = BigDecimalUtils.add(CClassFee,BigDecimalUtils.convert(map1.get("fulamtOwnpayAmt").toString()));
                otherClassFee = BigDecimalUtils.add(otherClassFee,BigDecimalUtils.convert(map1.get("othAmt").toString()));
            }
            dbzInfoMap.put("amt", sumDetItemFeeSumamt);
            dbzInfoMap.put("claaSumfee", AClassFee);
            dbzInfoMap.put("clabAmt", BClassFee);
            dbzInfoMap.put("fulamtOwnpayAmt", CClassFee);
            dbzInfoMap.put("othAmt", otherClassFee);
            dbzInfoMap.put("medChrgitm", setlinfo.get("bka006")+"+"+setlinfo.get("bka006Name"));
            dbzInfo.add(dbzInfoMap);
            for(int i = 0;i < itemInfoList.size(); i++){
                if("15".equals(itemInfoList.get(i).get("medChrgitm"))){
                    itemInfoList.remove(i);
                }
            }
        }

        // 输血信息节点
        List<Map<String, Object>> bldinfoList = insureGetInfoDAO.selectBldInfoForMap(map);
        String fulamtOwnpayAmt = DataTypeUtils.dataToNumString(MapUtils.get(setlinfoMap, "fulamt_ownpay_amt"));
        String overlmtSelfpay = DataTypeUtils.dataToNumString(MapUtils.get(setlinfoMap, "overlmt_selfpay"));

        String acctPay = DataTypeUtils.dataToNumString(MapUtils.get(setlinfoMap, "acct_pay"));
        String cashPayamt = DataTypeUtils.dataToNumString(MapUtils.get(setlinfoMap, "cash_payamt"));

        //修复bug 人自费金额算上了超限额自付的金额
//        BigDecimal psnOwnpay = BigDecimalUtils.add(fulamtOwnpayAmt, overlmtSelfpay);
        BigDecimal psnOwnpay = new BigDecimal(fulamtOwnpayAmt);

        setlinfo.put("psnOwnpay", psnOwnpay); // 个人自费
        setlinfo.put("acctPay", BigDecimalUtils.convert(acctPay)); // 个人账户支出
        setlinfo.put("psnCashpay", BigDecimalUtils.convert(cashPayamt)); // 个人现金支付
        BigDecimal bigDecimal = BigDecimalUtils.add(acctPay, cashPayamt);
        BigDecimal psnSelfpay = BigDecimalUtils.subtract(bigDecimal, psnOwnpay);
        setlinfo.put("psnSelfpay", psnSelfpay); // 个人自付
        resultDataMap.put("dbzInfo", dbzInfo);
        resultDataMap.put("setlinfo", setlinfo);
        resultDataMap.put("payinfo", payinfoList);
        resultDataMap.put("opspdiseinfo", opspdiseinfoList);
        resultDataMap.put("diseinfo", diseaseMap);
        resultDataMap.put("iteminfo", itemInfoList);
        resultDataMap.put("oprninfo", oprnfoList);
        resultDataMap.put("icuinfo", icuinfoList);
        resultDataMap.put("bldinfo", bldinfoList);
        resultDataMap.put("hifp_pay", MapUtils.get(setlinfoMap, "hifp_pay"));
        resultDataMap.put("setldetail", setldetail);
        //新增质控信息
        DrgDipResultDTO dto = new DrgDipResultDTO();
        //清单 business_type 1
        dto.setVisitId(map.get("visitId").toString());
        dto.setHospCode(map.get("hospCode").toString());
        dto.setBusinessType("1");
        //DIP_DRG_MODE值
        Map<String, Object> sysMap = new HashMap<>();
        sysMap.put("hospCode", MapUtils.get(map, "hospCode"));
        sysMap.put("code", "DIP_DRG_MODEL");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(sysMap).getData();
        if (ObjectUtil.isEmpty(sysParameterDTO)){
          resultDataMap.put("DIP_DRG_MODEL",null);
        }else{
          resultDataMap.put("DIP_DRG_MODEL",sysParameterDTO.getValue());
        }

        //返回给前端  提示是否有这个权限
        Map<String, Object> map2 = new HashMap<>();
        map2.put("hospCode", map.get("hospCode").toString());
        DrgDipAuthDTO drgDipAuthDTO = drgDipResultService.checkDrgDipBizAuthorization(map2).getData();
        HashMap map1 = new HashMap();
        map1.put("drgDipResultDTO",dto);
        map1.put("hospCode",map.get("hospCode").toString());
        map1.put("drgDipAuthDTO",drgDipAuthDTO);
        DrgDipComboDTO combo = drgDipResultService.getDrgDipInfoByParam(map1).getData();
        combo.setDip(drgDipAuthDTO.getDip());
        combo.setDrg(drgDipAuthDTO.getDrg());
        combo.setDipMsg(drgDipAuthDTO.getDipMsg());
        combo.setDrgMsg(drgDipAuthDTO.getDrgMsg());
        resultDataMap.put("drgInfo",combo);
        return resultDataMap;
    }

    /**
     * @param map
     * @Method queryPage
     * @Desrciption 查询结算清单左侧人员类别信息
     * @Param
     * @Author fuhui
     * @Date 2021/11/9 15:29
     * @Return
     */
    @Override
    public PageDTO querySetlePage(Map<String, Object> map) {
        int pageNo = Integer.parseInt(MapUtils.get(map, "pageNo") == null ? "1" : MapUtils.get(map, "pageNo"));
        int pageSize = Integer.parseInt(MapUtils.get(map, "pageSize") == null ? "10" : MapUtils.get(map, "pageSize"));
        List<Map<String, Object>> list = insureGetInfoDAO.querySetlePage(map);
        //queryFlag =0表示查询全部数据  直接返回结果集
        if (Constant.UnifiedPay.ISMAN.F.equals(MapUtil.getStr(map,"queryFlag"))) {
            return PageDTO.ofByManual(list,pageNo,pageSize);
        }

        if (ObjectUtil.isEmpty(MapUtil.getStr(map,"queryFlag"))) {
            throw new AppException("查询标志【queryFlag】不能为空，请检查！");
        }
        //根据queryFlag标志按需过滤  queryFlag =1表示查询异地就医数据  queryFlag = 2表示查询本地就医数据
        List<Map<String, Object>> result = new ArrayList<>();
        if (Constant.UnifiedPay.ISMAN.S.equals(MapUtil.getStr(map,"queryFlag"))) {
            //取异地数据
            list.forEach(o->{
                if (ObjectUtil.isNotEmpty(MapUtil.getStr(o,"insureRegCode"))
                        && ObjectUtil.isNotEmpty(MapUtil.getStr(o,"insuplcAdmdvs"))
                        && !MapUtil.getStr(o,"insuplcAdmdvs").substring(0,4).equals(MapUtil.getStr(o,"insureRegCode").substring(0,4))) {
                    result.add(o);
                }
            });
        }else if (Constant.UnifiedPay.ISMAN.E.equals(MapUtil.getStr(map,"queryFlag"))) {
            //取本地数据
            list.forEach(o->{
                if (ObjectUtil.isNotEmpty(MapUtil.getStr(o,"insureRegCode"))
                        && ObjectUtil.isNotEmpty(MapUtil.getStr(o,"insuplcAdmdvs"))
                        && MapUtil.getStr(o,"insuplcAdmdvs").substring(0,4).equals(MapUtil.getStr(o,"insureRegCode").substring(0,4))) {
                    result.add(o);
                }
            });
        }
        return PageDTO.ofByManual(result,pageNo,pageSize);
    }

    /**
     * @Author gory
     * @Description 结算清单DRG
     * @Date 2022/6/6 16:02
     * @Param [map]
     **/
    @Override
    public Map<String, Object> insertInsureSettleInfoForDRG(Map<String, Object> map) {
        /**=============1.请求参数封装 Begin=========**/
        Map<String, Object> dataMap = MapUtils.get(map, "dataMap");
        Map<String, Object> baseInfo = MapUtils.get(dataMap, "baseInfo");
        /**=============请求参数封装 End=========**/

        /**=============2.获取系统参数中配置的结算清单质控drg地址 Begin=========**/
        Map<String, Object> sysMap = new HashMap<>();
        sysMap.put("hospCode", MapUtils.get(map, "hospCode"));
        sysMap.put("code", "JSQD_DRG");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(sysMap).getData();
        String url = "";
        if (sysParameterDTO != null && sysParameterDTO.getValue() != null && !"".equals(sysParameterDTO.getValue())) {
            url = sysParameterDTO.getValue();
        } else {
            throw new AppException("请在系统参数中配置结算清单上传drg时，drg地址  例：JSQD_DRG: url");
        }
        /**===================获取系统参数中配置的结算清单质控drg地址 End==============**/

        Map<String, Object> logMap = new HashMap<>();
        logMap.put("reqTime",DateUtils.getNow());//请求时间
        /**======5.保存日志 begin=========**/
        //记录日志
        logMap.put("hospCode",MapUtils.get(map, "hospCode"));
        logMap.put("orgCode",MapUtils.get(baseInfo, "fixmedins_code"));
        logMap.put("visitId",MapUtils.get(baseInfo, "visit_id"));
        logMap.put("reqContent",JSONObject.toJSONString(dataMap));
        logMap.put("type","1");
        logMap.put("businessType","1");
        logMap.put("infNo","34141");
        logMap.put("infName","结算清单DRG质控");
        logMap.put("crtId",MapUtils.get(map, "crteId"));
        logMap.put("crtName",MapUtils.get(map, "crteName"));
        Map responseDataMap = new HashMap<>();
        responseDataMap.put("name",baseInfo.get("psnName"));// 姓名
        responseDataMap.put("sex",baseInfo.get("gender"));// 性别
        responseDataMap.put("age",baseInfo.get("age"));// 年龄
        responseDataMap.put("hiType",baseInfo.get("hi_type"));// 医保类型
        try{
            // 3.发送请求
            Map<String, Object> responseMap = HttpConnectUtil.sendPost(url, JSONObject.toJSONString(dataMap));

            logMap.put("respTime",DateUtils.getNow());//响应时间
            logMap.put("respContent",JSONObject.toJSONString(responseMap));
            logMap.put("resultCode",MapUtils.get(responseMap, "code"));
            //保存操作日志
            dataMap.put("crteId",MapUtils.get(map, "crteId"));
            dataMap.put("crteName",MapUtils.get(map, "crteName"));
            dataMap.put("hospCode",MapUtils.get(map, "hospCode"));
            dataMap.put("type","1");
            dataMap.put("businessType","1");
            dataMap.put("optTypeName","结算清单DRG质控");


            Integer responseCode = MapUtils.get(responseMap, "code");// 返回码
            if (responseCode != null && 0 != responseCode){
                logger.info("SettleInfoInterface调用DRG接口失败，入参为：{},返参为：{}"
                        , JSONObject.toJSONString(dataMap), JSONObject.toJSONString(responseMap));
//                throw new AppException("调用DRG接口失败");
                throw new AppException("调用DRG接口报错："+MapUtils.get(responseMap, "massege").toString());
            }else {
                logger.info("SettleInfoInterface调用DRG接口成功，入参为：{},返参为：{}"
                        , JSONObject.toJSONString(dataMap), JSONObject.toJSONString(responseMap));
            }
            Map<String,Object> resultMap = MapUtils.get(responseMap, "result");// 结果集
            if (MapUtils.isEmpty(resultMap)){
                throw new AppException("调用DRG,返回结果为空");
            }
            /**======4.获取返回的参数 begin=========**/
            Map<String,Object> baseInfoMap = MapUtils.get(resultMap, "baseInfo");// 基本信息对象
            Map<String,Object> groupInfoMap = MapUtils.get(resultMap, "groupInfo");// 分组信息对象
            List<Map<String,Object>> qualityInfo = MapUtils.get(resultMap, "qualityInfo");// 质控信息集合
            List<Map<String, Object>> qualityInfoList = ListUtils.isEmpty(qualityInfo) ? null :
                    qualityInfo.stream().sorted((a, b) ->
                                     (b.get("rule_level") == null ? "" : b.get("rule_level"))
                                    .toString()
                                    .compareTo(a.get("rule_level").toString()))
                            .collect(Collectors.toList());// 质控信息集合
            /**======获取返回的参数 end=========**/
            //保存质控结果
            DrgDipResultDTO drgDipResultDTO = insertDrgDipResult(dataMap,baseInfoMap,groupInfoMap,qualityInfoList);
            dataMap.put("qulityId",drgDipResultDTO.getId());
            insertDrgDipBusinessOptInfoLog(dataMap);
            /**==========6.返回参数封装 Begin ===========**/
            responseDataMap.put("inNO",baseInfoMap.get("visitId"));// 住院号
            responseDataMap.put("drgCode",groupInfoMap.get("code"));// DRG组编码
            responseDataMap.put("drgName",groupInfoMap.get("name"));// DRG组名称
            responseDataMap.put("weightValue",groupInfoMap.get("weight"));// DRG权重
            responseDataMap.put("ratio",groupInfoMap.get("bl"));// 倍率
            responseDataMap.put("profitAndLossAmount",groupInfoMap.get("profit"));// 盈亏额
            responseDataMap.put("totalFee",baseInfoMap.get("totalFee"));// 总费用
            responseDataMap.put("feeStand",groupInfoMap.get("feeStand"));// 总费用标杆
            responseDataMap.put("proMedicMater",baseInfoMap.get("pro_medic_mater"));// 药占比
            responseDataMap.put("proMedicMaterStand",groupInfoMap.get("pro_medic_mater"));// 药占比标杆
            responseDataMap.put("proConsum",baseInfoMap.get("pro_consum"));// 耗材比
            responseDataMap.put("proConsumStand",groupInfoMap.get("pro_consum"));// 耗材比标杆
            responseDataMap.put("scorePrice",groupInfoMap.get("score_price"));// 分值单价
            //自行计算盈亏额
            if(baseInfoMap.get("totalFee") != null && !"".equals(baseInfoMap.get("totalFee")) && groupInfoMap.get("feeStand")!= null && !"".equals(groupInfoMap.get("feeStand"))){
                responseDataMap.put("profitAndLossAmount",BigDecimalUtils.subtract(BigDecimalUtils.convert(groupInfoMap.get("feeStand").toString()),BigDecimalUtils.convert(baseInfoMap.get("totalFee").toString())).setScale(2));// 盈亏额
            }
            //自行计算标杆费用
            if(groupInfoMap.get("feePay") != null && !"".equals(groupInfoMap.get("feePay")) && groupInfoMap.get("score_price")!= null && !"".equals(groupInfoMap.get("score_price"))){
                responseDataMap.put("feeStand",BigDecimalUtils.multiply(BigDecimalUtils.convert(groupInfoMap.get("feePay").toString()),BigDecimalUtils.convert(groupInfoMap.get("score_price").toString())).setScale(2));// 标杆费用
            }
            //计算预计基金支付
            Map<String,Object> priceMap = new HashMap<>();
            priceMap.put("hospCode",MapUtils.get(map, "hospCode"));
            priceMap.put("visitId",MapUtils.get(map, "visitId"));
            priceMap.put("insureSettleId",MapUtils.get(map, "insureSettleId"));
            PayInfoDTO payInfoDTO = insureGetInfoDAO.queryInsureSettlePrice(priceMap);
            if(payInfoDTO == null){
                responseDataMap.put("estimateFund","-全自费");//预计基金支付
                responseDataMap.put("profitAndLossAmount","-全自费");//盈亏额
                responseDataMap.put("proMedicMater","-");// 药占比
                responseDataMap.put("proConsum","-");// 耗材比
            }else{
                //如果没有报销也算全自费
                if(BigDecimalUtils.equals(BigDecimal.ZERO,payInfoDTO.getInsurePrice())){
                    responseDataMap.put("estimateFund","-全自费");//预计基金支付
                    responseDataMap.put("profitAndLossAmount","-全自费");//盈亏额
                }else {
                    BigDecimal estimateFund = new BigDecimal(0.00);//预计基金支付
                    BigDecimal personPriceSum = BigDecimalUtils.add(payInfoDTO.getPersonalPrice(),payInfoDTO.getPersonPrice(),2);//个人支付合计
                    if(responseDataMap.get("feeStand") != null && !"".equals(responseDataMap.get("feeStand"))){
                        estimateFund = BigDecimalUtils.subtract(BigDecimalUtils.convert(MapUtils.get(responseDataMap, "feeStand").toString()),BigDecimalUtils.add(personPriceSum,payInfoDTO.getRestsPrice(),2)).setScale(2);
                    }
                    //如果小于0当做0处理
                    if(BigDecimalUtils.greater(BigDecimal.ZERO,estimateFund)){
                        estimateFund = BigDecimal.ZERO;
                    }
                    responseDataMap.put("estimateFund",estimateFund);//预计基金支付
                    responseDataMap.put("profitAndLossAmount",BigDecimalUtils.subtract(estimateFund,payInfoDTO.getInsurePrice()));//盈亏额
                }
                //查询医保费用
                Map<String, Object> data = insureUnifiedBaseService.queryFeeDetailInfo(map).getData();
                List<Map<String, Object>> feeMapList = MapUtils.get(data, "outptMap");
                if (ListUtils.isEmpty(feeMapList)) {
                    throw new AppException("没有获取到医保费用明细数据");
                }
                //计算药占比，耗材占比
                BigDecimal sumProMedic = new BigDecimal(0.00); // 药占总费用
                BigDecimal sumProConsum = new BigDecimal(0.00); // 耗材占总费用
                BigDecimal sumPrice = new BigDecimal(0.00); // 总费用
                for(Map feeMap:feeMapList){
                    //累加药占总费用
                    if(Constants.UNIFIED_PAY_TYPE.XY.equals(feeMap.get("list_type")) || Constants.UNIFIED_PAY_TYPE.ZCY.equals(feeMap.get("list_type"))
                            || Constants.UNIFIED_PAY_TYPE.ZYYP.equals(feeMap.get("list_type"))|| Constants.UNIFIED_PAY_TYPE.ZZJ.equals(feeMap.get("list_type"))){
                        sumProMedic = BigDecimalUtils.add(sumProMedic,BigDecimalUtils.convert(feeMap.get("det_item_fee_sumamt").toString()).setScale(2)).setScale(2);
                    }
                    //累加耗材总费用
                    if(Constants.UNIFIED_PAY_TYPE.YYCL.equals(feeMap.get("list_type"))){
                        sumProConsum = BigDecimalUtils.add(sumProConsum,BigDecimalUtils.convert(feeMap.get("det_item_fee_sumamt").toString()).setScale(2)).setScale(2);
                    }
                    //累加总费用
                    sumPrice = BigDecimalUtils.add(sumPrice,BigDecimalUtils.convert(feeMap.get("det_item_fee_sumamt").toString()).setScale(2)).setScale(2);
                }
                BigDecimal proMedicMater =  new BigDecimal(0.00);//药占比
                BigDecimal proConsum =  new BigDecimal(0.00);//耗材占比
                if(!BigDecimalUtils.equals(BigDecimal.ZERO,sumPrice)){
                    proMedicMater = BigDecimalUtils.multiply(BigDecimalUtils.divide(sumProMedic,sumPrice),new BigDecimal(100.00));
                    proConsum = BigDecimalUtils.multiply(BigDecimalUtils.divide(sumProConsum,sumPrice),new BigDecimal(100.00));
                }
                responseDataMap.put("proMedicMater",proMedicMater);// 药占比
                responseDataMap.put("proConsum",proConsum);// 耗材比
            }
            //处理排序号
            if (!ListUtils.isEmpty(qualityInfoList)){
                qualityInfoList.stream().forEach(x ->{
                    if(ObjectUtil.isNotEmpty(x.get("order"))){
                        x.put("sort",Integer.valueOf((String)x.get("order"))-1);
                    }
                });
            }
            responseDataMap.put("quality",qualityInfoList);// 质控信息
            /**==========返回参数封装 End ===========**/
        }catch (Exception e){
            if (e instanceof AppException) {
                throw e;
            } else {
                throw new AppException(e.getMessage());
            }
        }finally {
            drgDipResultService.insertDrgDipQulityInfoLog(logMap);
            /**==========保存日志 end ===========**/
        }
        return responseDataMap;
    }

    /**
     * @Author gory
     * @Description 手术信息
     * @Date 2022/6/6 16:12
     * @Param [map]
     **/
    private List<Map<String, Object>> getInsureSettleOprtInfo(Map<String, Object> map) {
        List<Map<String, Object>> resultList = insureGetInfoDAO.selectMriOperInfoForDRGorDIP(map);
        //病案首页没查到手术则查询结算清单手术
        if(ListUtils.isEmpty(resultList)){
            List<Map<String, Object>> resultList1 = new ArrayList<>();
            // 手术操作信息
            List<Map<String, Object>> oprnfoList = insureGetInfoDAO.selectOprninfoForMap(map);
            if (!ListUtils.isEmpty(oprnfoList)){
                oprnfoList.stream().forEach(x ->{
                    Map<String,Object> resultMap = new HashMap<>();
                    resultMap.put("visit_id",x.get("visitId"));// 就诊id
                    resultMap.put("oprn_oprt_type",x.get("OprnOprtType"));// 手术操作类别
                    resultMap.put("oprn_oprt_name",x.get("oprnOprtName"));// 手术操作名称
                    resultMap.put("oprn_oprt_code",x.get("oprnOprtCode"));// 手术操作代码
                    resultMap.put("anst_way",x.get("anstWay"));// 麻醉方式
                    resultMap.put("oper_dr_code",x.get("operDrCode"));// 术者医师代码
                    resultMap.put("oper_dr_name",x.get("operDrName"));// 术者医师姓名
                    resultMap.put("anst_dr_code",x.get("anstDrCode"));// 麻醉医师代码
                    resultMap.put("anst_dr_name",x.get("anstDrName"));// 麻醉医师姓名
                    if(ObjectUtil.isNotEmpty(x.get("oprnOprtStartTime"))){
                        resultMap.put("oprn_oprt_begntime",DateUtils.format((Date)x.get("oprnOprtStartTime"), DateUtils.Y_M_D));// 手术操作开始时间
                    }
                    if(ObjectUtil.isNotEmpty(x.get("oprnOprtEndTime"))){
                        resultMap.put("oprn_oprt_endtime",DateUtils.format((Date) x.get("oprnOprtEndTime"), DateUtils.Y_M_D));// 手术操作结束时间
                    }
                    if(ObjectUtil.isNotEmpty(x.get("anstDrStartTime"))){
                        resultMap.put("anst_begntime",DateUtils.format((Date) x.get("anstDrStartTime"), DateUtils.Y_M_D));// 麻醉开始时间
                    }
                    if(ObjectUtil.isNotEmpty(x.get("anstDrEndTime"))){
                        resultMap.put("anst_endtime",DateUtils.format((Date) x.get("anstDrEndTime"), DateUtils.Y_M_D));// 麻醉结束时间
                    }
                    resultMap.put("oprt_date",x.get("oprtDate"));// 手术操作日期
                    resultList1.add(resultMap);
                });
            }
            resultList = resultList1;
        }
        //增加排序号
        for(int i = 0 ;i < resultList.size(); i++){
            resultList.get(i).put("sort_no",String.valueOf(i+1));
        }
        return resultList;
    }

    /**
     * @Author gory
     * @Description 诊断信息(只有住院)
     * @Date 2022/6/6 16:11
     * @Param [map]
     **/
    private List<Map<String, Object>> getInsureSettleDiseInfo(Map<String, Object> map) {
        // 4.查询住院诊断信息   得到住院诊断节点信息
        Map<String, Object> diagnoseMap = handerInptDiagnose(map);
        List<InptDiagnoseDTO> xiCollect = MapUtils.get(diagnoseMap, "xiCollect");// 西医诊断
        List<InptDiagnoseDTO> zxCollect = MapUtils.get(diagnoseMap, "zxCollect");// 中西医诊断

        List<Map<String,Object>> resultList = new ArrayList<>();
        if (!ListUtils.isEmpty(xiCollect)){
            xiCollect.stream().forEach(x ->{
                Map<String,Object> resultMap = new HashMap<>();
                resultMap.put("visit_id",x.getVisitId());// 就诊id
                resultMap.put("diag_code",x.getDiseaseCode());// 诊断编码
                resultMap.put("diag_name",x.getDiseaseName());// 诊断名称
                resultMap.put("adm_cond_type",x.getAdmCondType());// 入院病情
                resultMap.put("maindiag_flag",x.getIsMain());// 主诊断标志
                resultList.add(resultMap);
            });
        }
        if (!ListUtils.isEmpty(zxCollect)){
            zxCollect.stream().forEach(x ->{
                Map<String,Object> resultMap = new HashMap<>();
                resultMap.put("visit_id",x.getVisitId());
                resultMap.put("diag_code",x.getDiseaseCode());
                resultMap.put("diag_name",x.getDiseaseName());
                resultMap.put("adm_cond_type",x.getAdmCondType());
                resultMap.put("maindiag_flag",x.getIsMain());
                resultList.add(resultMap);
            });
        }
        //增加排序号
        for(int i = 0 ;i < resultList.size(); i++){
            resultList.get(i).put("sort_no",String.valueOf(i+1));
        }
        return resultList;
    }

    /**
     * @Author gory
     * @Description 基础信息
     * @Date 2022/6/6 16:10
     * @Param [map]
     **/
    private Map<String, Object> getInsureSettleBaseInfo(Map<String, Object> map) {
        Map setlinfo = selectLoadingSetlMsg(map);
        //Map setlinfo = MapUtils.get(baseMap,"setlinfo");
        Map resultMap = new HashMap<>();
        resultMap.put("biz_sn", setlinfo.get("bizSn"));// 业务流水号
        resultMap.put("visit_id", setlinfo.get("visitId"));// 就诊id
        resultMap.put("psn_no", setlinfo.get("psnNo"));// 人员编号
        resultMap.put("set1_id", setlinfo.get("setlId"));// 结算id
        resultMap.put("fixmedins_name", setlinfo.get("fixmedinsName"));// 定点医药机构名称
        resultMap.put("fixmedins_code", setlinfo.get("fixmedinsCode"));// 定点医药机构编号
        resultMap.put("hi_setl_lv", setlinfo.get("hiSetlLv"));
        //结算等级处理
        Map dictMap = new HashMap(2);
        dictMap.put("hospCode", MapUtils.get(map, "hospCode"));
        dictMap.put("insureRegCode", MapUtils.get(setlinfo, "insuplc"));
        dictMap.put("code", "HI_SETL_LV");
        Map<String, String> dictMap1 = insureDictService_consumer.queryDictByCode(dictMap).getData();
        for(String key:dictMap1.keySet()){
            if(dictMap1.get(key).equals(MapUtils.get(setlinfo, "hiSetlLv"))){
                resultMap.put("hi_setl_lv", key);
            }
        }
        resultMap.put("hi_no", setlinfo.get("hiNo"));// 医保编号
        resultMap.put("medcasno", setlinfo.get("medcasno"));// 病案号
        resultMap.put("dcla_time", setlinfo.get("dclaTime"));// 申报时间
        resultMap.put("psn_name", setlinfo.get("psnName"));// 人员姓名
        resultMap.put("gend", setlinfo.get("gend"));// 性别代码
        resultMap.put("brdy", setlinfo.get("brdy"));// 出生日期
        resultMap.put("age", setlinfo.get("age"));// 年龄
        String ntly = setlinfo.get("ntly") == "141" ? "CHN" : "XXX";
        resultMap.put("ntly", ntly);// 国籍代码
        resultMap.put("nwb_age", setlinfo.get("nwbAge"));// 年龄（不足一周岁）
        resultMap.put("naty", setlinfo.get("naty"));// 民族代码
        resultMap.put("patn_cert_type", setlinfo.get("patnCertType"));// 患者证件类别代码
        resultMap.put("certno", setlinfo.get("certno"));// 证件号码
        resultMap.put("prfs", setlinfo.get("prfs"));// 职业代码
        resultMap.put("curr_addr", setlinfo.get("currAddr"));// 现住址
        resultMap.put("emp_name", setlinfo.get("empName"));// 单位名称
        resultMap.put("emp_addr", setlinfo.get("empAddr"));// 单位地址
        resultMap.put("emp_tel", setlinfo.get("empTel"));// 单位电话
        resultMap.put("poscode", setlinfo.get("poscode"));// 邮编
        resultMap.put("coner_name", setlinfo.get("conerName"));// 联系人姓名
        resultMap.put("patn_rlts", setlinfo.get("patnRlts"));// 与患者关系代码
        resultMap.put("coner_addr", setlinfo.get("conerAddr"));// 联系人地址
        resultMap.put("coner_tel", setlinfo.get("conerTel"));// 联系人电话
        resultMap.put("hi_type", setlinfo.get("hiType"));// 医保类型代码
        resultMap.put("insuplc", setlinfo.get("insuplc"));// 参保地
        resultMap.put("sp_psn_type", setlinfo.get("spPsnType"));// 特殊人员类型代码
        resultMap.put("nwb_adm_type", setlinfo.get("nwbAdmType"));// 新生儿入院类型代码
        resultMap.put("nwb_bir_wt", setlinfo.get("nwbBirWt"));// 新生儿出生体重
        resultMap.put("nwb_adm_wt", setlinfo.get("nwbAdmWt"));// 新生儿入院体重
        resultMap.put("mul_nwb_bir_wt", setlinfo.get("nwbBirWt"));// todo 多新生儿出生体重
        resultMap.put("mul_nwb_adm_wt", setlinfo.get("nwbAdmWt"));// todo 多新生儿入院体重
        resultMap.put("opsp_diag_caty_name", setlinfo.get("opspDiagCatyName"));// todo 门诊慢特病诊断科别名称
        resultMap.put("opsp_diag_caty", setlinfo.get("opspDiagCaty"));// 门诊慢特病诊断科别代码
        resultMap.put("opsp_mdtrt_date", setlinfo.get("opspMdtrtDate"));// 门诊慢特病就诊日期
        resultMap.put("ipt_med_type", setlinfo.get("iptMedTpe"));//住院医疗类型代码
        resultMap.put("adm_way", setlinfo.get("admWay"));// 入院途径代码
        resultMap.put("trt_type", setlinfo.get("trtType"));// 治疗类别代码
        resultMap.put("adm_time", setlinfo.get("admTime"));// 入院时间
        // todo 查询科室类别 后续将会使用设计模式实现
        resultMap.put("adm_caty", setlinfo.get("admCaty"));// 入院科别代码
        resultMap.put("dscg_caty", setlinfo.get("dscgCaty"));// 出院科别代码
//        Map<String, Object> deptMap = new HashMap<>();
//        deptMap.put("hospCode",map.get("hospCode"));// 医院编码
//        deptMap.put("platformDictionaryCode","dept");//平台字典编码
//        deptMap.put("platformType","1");// 平台类型
//        deptMap.put("hisCodeValue",setlinfo.get("admCaty"));// his代码值
//        Map<String,Object> resultDeptMap = insureGetInfoDAO.querySettleClockMatchByMap(deptMap);
//        resultMap.put("adm_caty", resultDeptMap == null ? "" : resultDeptMap.get("platformCodeValue"));// 入院科别代码
//        resultMap.put("adm_dept", resultDeptMap == null ? "" : resultDeptMap.get("platformCodeName"));// 入院科别名称
        resultMap.put("refldept_dept", setlinfo.get("refldeptDept"));// 转科科别代码
        resultMap.put("dscg_time", setlinfo.get("dscgTime"));// 出院时间
//        Map<String, Object> deptDscgMap = new HashMap<>();
//        deptDscgMap.put("hospCode",map.get("hospCode"));// 医院编码
//        deptDscgMap.put("platformDictionaryCode","dept");//平台字典编码
//        deptDscgMap.put("platformType","1");// 平台类型
//        deptDscgMap.put("hisCodeValue",setlinfo.get("admCaty"));// his代码值
//        Map<String,Object> resultDscgDeptMap = insureGetInfoDAO.querySettleClockMatchByMap(deptDscgMap);
//        resultMap.put("dscg_caty", resultDscgDeptMap == null ? "" : resultDeptMap.get("platformCodeValue"));// todo 出院科别代码,需要弄一个匹配表
//        resultMap.put("disch_dept", resultDscgDeptMap == null ? "" : resultDeptMap.get("platformCodeName"));// 出院科别
        resultMap.put("act_ipt_days", setlinfo.get("actIptDays"));// 实际住院天数
        resultMap.put("otp_wm_dise", setlinfo.get("otpWmDise"));// 门诊西医诊断名称
        resultMap.put("wm_dise_code", setlinfo.get("wmDiseCode"));// 门诊西医诊断疾病代码
        resultMap.put("otp_tcm_dise", setlinfo.get("otpTcmDise"));// 门诊中医诊断名称
        resultMap.put("tcm_dise_code", setlinfo.get("tcmDiseCode"));// 门诊中医诊断代码
        resultMap.put("vent_used_days", setlinfo.get("ventUsedDays"));// todo 呼吸机使用时长（天）
        resultMap.put("vent_used_hours", setlinfo.get("ventUsedHours"));// todo 呼吸机使用时长（小时）
        resultMap.put("vent_used_mins", setlinfo.get("ventUsedMins"));// todo 呼吸机使用时长（分钟）
        resultMap.put("pwcry_bfadm_coma_days", setlinfo.get("pwcry_bfadm_coma_days"));// todo 颅脑损伤患者入院前昏迷时长（天）
        resultMap.put("pwcry_bfadm_coma_hours", setlinfo.get("pwcry_bfadm_coma_hours"));// todo 颅脑损伤患者入院前昏迷时长（小时）
        resultMap.put("pwcry_bfadm_coma_mins", setlinfo.get("pwcry_bfadm_coma_mins"));// todo 颅脑损伤患者入院前昏迷时长（分钟）
        resultMap.put("pwcry_afadm_coma_days", setlinfo.get("pwcry_afadm_coma_days"));// todo 颅脑损伤患者入院后昏迷时长(天）
        resultMap.put("pwcry_afadm_coma_hours", setlinfo.get("pwcry_afadm_coma_hours"));// todo 颅脑损伤患者入院后昏迷时长（小时）
        resultMap.put("pwcry_afadm_coma_mins", setlinfo.get("pwcry_afadm_coma_mins"));// todo 颅脑损伤患者入院后昏迷时长（分钟）
        resultMap.put("bld_cat", setlinfo.get("bldCat"));// 输血品种代码
        resultMap.put("bld_amt", setlinfo.get("bldAmt"));// 输血量
        resultMap.put("bld_unt", setlinfo.get("bldUnt"));// 输血计量单位
        resultMap.put("spga_nurscare_days", setlinfo.get("spgaNurscareDays"));// 特级护理天数
        resultMap.put("lv1_nurscare_days", setlinfo.get("lv1NurscareDays"));// 一级护理天数
        resultMap.put("scd_nurscare_days", setlinfo.get("scdNurscareDays"));// 二级护理天数
        resultMap.put("lv3_nurscare_days", setlinfo.get("lv3NurscareDays"));// 三级护理天数
        resultMap.put("dscg_way", setlinfo.get("dscgWay"));// 离院方式代码
        resultMap.put("acp_medins_name", setlinfo.get("acpMedinsName"));// 拟接收机构名称
        resultMap.put("acp_optins_code", setlinfo.get("acpMedinsCode"));// 拟接收结构代码
        resultMap.put("bill_code", setlinfo.get("billCode"));// 票据代码
        resultMap.put("bill_no", setlinfo.get("billNo"));// 票据号码
        resultMap.put("days_rinp_flag_31", setlinfo.get("daysRinpFlag31"));// 出院31天内再住院计划标志代码
        resultMap.put("days_rinp_pup_31", setlinfo.get("daysRinpPup31"));// 出院31天内再住院目的
        resultMap.put("chfpdr_name", setlinfo.get("chfpdrName"));// 主诊医师姓名
        resultMap.put("chfpdr_code", setlinfo.get("chfpdrCode"));// 主诊医师代码
        resultMap.put("resp_nurs", setlinfo.get("zrNurseName"));// 责任护士名称
        resultMap.put("resp_nurs_code", setlinfo.get("zrNurseCode"));// 责任护士代码
        resultMap.put("setl_begn_date", setlinfo.get("setlBegnDate"));// 结算开始日期
        resultMap.put("setl_end_date", setlinfo.get("setlEndDate"));// 结算结束日期
        resultMap.put("medins_fill_dept", setlinfo.get("medinsFillDept"));// 医疗机构填报部门
        resultMap.put("medins_fill_psn", setlinfo.get("medinsFillPsn"));// 医疗机构填报人
        // 通过his结算id去获取总费用
        String settleId = MapUtils.get(map,"settleId");
        String hospCode = MapUtils.get(map,"hospCode");
        BigDecimal totalFee = insureGetInfoDAO.queryTotalFee(settleId, hospCode);
        totalFee = totalFee.setScale(4);
        resultMap.put("total_fee", totalFee);
        resultMap.put("hi_paymtd", setlinfo.get("hiPaymtd"));//医保支付方式代码
        return resultMap;
    }

    /**
     * @Author gory
     * @Description 结算清单DIP
     * @Date 2022/6/6 16:02
     * @Param [map]
     **/
    @Override
    public Map<String, Object> insertInsureSettleInfoForDIP(Map<String, Object> map) {
        /**=============1. dip入参封装 begin=========**/
        Map<String, Object> dataMap = MapUtils.get(map, "dataMap");
        Map<String, Object> baseInfo = MapUtils.get(dataMap, "baseInfo");
        /**=============dip入参封装 end=========**/

        /**=============2.获取系统参数中配置的结算清单质控dip地址 Begin=========**/
        Map<String, Object> sysMap = new HashMap<>();
        sysMap.put("hospCode", MapUtils.get(map, "hospCode"));
        sysMap.put("code", "JSQD_DIP");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(sysMap).getData();
        String url = "";
        if (sysParameterDTO != null && sysParameterDTO.getValue() != null && !"".equals(sysParameterDTO.getValue())) {
            url = sysParameterDTO.getValue();
        } else {
            throw new AppException("请在系统参数中配置结算清单上传dip时，dip地址  例：JSQD_DIP: url");
        }
        /**===================获取系统参数中配置的结算清单质控dip地址 End==============**/


        Map<String, Object> logMap = new HashMap<>();
        logMap.put("reqTime",DateUtils.getNow());//请求时间
        /**======5.保存日志 begin=========**/
        logMap.put("hospCode",MapUtils.get(map, "hospCode"));
        logMap.put("orgCode",MapUtils.get(baseInfo, "fixmedins_code"));
        logMap.put("visitId",MapUtils.get(baseInfo, "visit_id"));
        logMap.put("reqContent",JSONObject.toJSONString(dataMap));
        logMap.put("type","2");
        logMap.put("businessType","1");
        logMap.put("infNo","34142");
        logMap.put("infName","结算清单DIP质控");
        logMap.put("crtId",MapUtils.get(map, "crteId"));
        logMap.put("crtName",MapUtils.get(map, "crteName"));
        Map responseDataMap = new HashMap<>();
        responseDataMap.put("name",baseInfo.get("psnName"));// 姓名
        responseDataMap.put("sex",baseInfo.get("gender"));// 性别
        responseDataMap.put("age",baseInfo.get("age"));// 年龄
        responseDataMap.put("hiType",baseInfo.get("hi_type"));// 医保类型
        try{
            /**===================3.调用DIP Begin==============**/
            Map<String, Object> responseMap = HttpConnectUtil.sendPost(url, JSONObject.toJSONString(dataMap));
            /**===================调用DIP End==============**/
            /**======4.获取返回的参数 begin=========**/
            logMap.put("respTime",DateUtils.getNow());//响应时间
            logMap.put("respContent",JSONObject.toJSONString(responseMap));
            logMap.put("resultCode",MapUtils.get(responseMap, "code"));
            //保存操作日志
            dataMap.put("crteId",MapUtils.get(map, "crteId"));
            dataMap.put("crteName",MapUtils.get(map, "crteName"));
            dataMap.put("hospCode",MapUtils.get(map, "hospCode"));
            dataMap.put("type","2");
            dataMap.put("businessType","1");
            dataMap.put("optTypeName","结算清单DIP质控");
            /**======保存日志 end=========**/
            Integer responseCode = MapUtils.get(responseMap, "code");// 返回码
            if (responseCode != null && responseCode != 0){
                logger.info("SettleInfoInterface调用DIP接口失败，入参为：{},返参为：{}"
                        , JSONObject.toJSONString(dataMap), JSONObject.toJSONString(responseMap));
//                throw new AppException("调用DIP接口失败");
                throw new AppException("调用DIP接口报错："+MapUtils.get(responseMap, "massege").toString());
            }else{
                logger.info("SettleInfoInterface调用DIP接口成功，入参为：{},返参为：{}"
                        , JSONObject.toJSONString(dataMap), JSONObject.toJSONString(responseMap));
            }
            Map<String,Object> resultMap = MapUtils.get(responseMap, "result");// 结果集
            if (MapUtils.isEmpty(resultMap)){
                throw new AppException("调用DIP接口失败,返回的结果集为null，请联系管理员");
            }
            Map<String,Object> baseInfoMap = MapUtils.get(resultMap, "baseInfo");// 基本信息对象
            Map<String,Object> groupInfoMap = MapUtils.get(resultMap, "groupInfo");// 分组信息对象
            List<Map<String,Object>> qualityInfo = MapUtils.get(resultMap, "qualityInfo");// 质控信息集合
            List<Map<String, Object>> qualityInfoList = ListUtils.isEmpty(qualityInfo) ? null :
                    qualityInfo.stream().sorted((a, b) ->
                                    (b.get("rule_level") == null ? "" : b.get("rule_level"))
                                            .toString()
                                            .compareTo(a.get("rule_level").toString()))
                            .collect(Collectors.toList());// 质控信息集合

            //6.保存质控结果
            DrgDipResultDTO drgDipResultDTO = insertDrgDipResult(dataMap,baseInfoMap,groupInfoMap,qualityInfoList);
            dataMap.put("qulityId",drgDipResultDTO.getId());
            insertDrgDipBusinessOptInfoLog(dataMap);
            /**==========7.返回参数封装 Begin ===========**/
            responseDataMap.put("inNO",baseInfoMap.get("visitId"));//住院号
            responseDataMap.put("diagCode",groupInfoMap.get("code"));// DIP组编码
            responseDataMap.put("diagName",groupInfoMap.get("name"));// DIP组名称
            responseDataMap.put("diagFeeSco",groupInfoMap.get("feePay"));// 分值
            responseDataMap.put("profitAndLossAmount",groupInfoMap.get("profit"));// 盈亏额
            responseDataMap.put("totalFee",baseInfoMap.get("totalFee"));// 总费用
            responseDataMap.put("feeStand",groupInfoMap.get("feeStand"));// 总费用标杆
            responseDataMap.put("proMedicMater",baseInfoMap.get("pro_medic_mater"));// 药占比
            responseDataMap.put("proMedicMaterStand",groupInfoMap.get("pro_medic_mater"));// 药占比标杆
            responseDataMap.put("proConsum",baseInfoMap.get("pro_consum"));// 耗材比
            responseDataMap.put("proConsumStand",groupInfoMap.get("pro_consum"));// 耗材比标杆
            responseDataMap.put("scorePrice",groupInfoMap.get("score_price"));// 分值单价
            //自行计算盈亏额
            if(baseInfoMap.get("totalFee") != null && !"".equals(baseInfoMap.get("totalFee")) && groupInfoMap.get("feeStand")!= null && !"".equals(groupInfoMap.get("feeStand"))){
                responseDataMap.put("profitAndLossAmount",BigDecimalUtils.subtract(BigDecimalUtils.convert(groupInfoMap.get("feeStand").toString()),BigDecimalUtils.convert(baseInfoMap.get("totalFee").toString())).setScale(2));// 盈亏额
            }
            //自行计算标杆费用
            if(groupInfoMap.get("feePay") != null && !"".equals(groupInfoMap.get("feePay")) && groupInfoMap.get("score_price")!= null && !"".equals(groupInfoMap.get("score_price"))){
                responseDataMap.put("feeStand",BigDecimalUtils.multiply(BigDecimalUtils.convert(groupInfoMap.get("feePay").toString()),BigDecimalUtils.convert(groupInfoMap.get("score_price").toString())).setScale(2));// 标杆费用
            }
            //计算预计基金支付
            Map<String,Object> priceMap = new HashMap<>();
            priceMap.put("hospCode",MapUtils.get(map, "hospCode"));
            priceMap.put("visitId",MapUtils.get(map, "visitId"));
            priceMap.put("insureSettleId",MapUtils.get(map, "insureSettleId"));
            PayInfoDTO payInfoDTO = insureGetInfoDAO.queryInsureSettlePrice(priceMap);
            if(payInfoDTO == null){
                responseDataMap.put("estimateFund","-全自费");//预计基金支付
                responseDataMap.put("profitAndLossAmount","-全自费");//盈亏额
            }else{
                //如果没有报销也算全自费
                if(BigDecimalUtils.equals(BigDecimal.ZERO,payInfoDTO.getInsurePrice())){
                    responseDataMap.put("estimateFund","-全自费");//预计基金支付
                    responseDataMap.put("profitAndLossAmount","-全自费");//盈亏额
                    responseDataMap.put("proMedicMater","-");// 药占比
                    responseDataMap.put("proConsum","-");// 耗材比
                }else {
                    BigDecimal estimateFund = new BigDecimal(0.00);//预计基金支付
                    BigDecimal personPriceSum = BigDecimalUtils.add(payInfoDTO.getPersonalPrice(),payInfoDTO.getPersonPrice(),2);//个人支付合计
                    if(responseDataMap.get("feeStand") != null && !"".equals(responseDataMap.get("feeStand"))){
                        estimateFund = BigDecimalUtils.subtract(BigDecimalUtils.convert(MapUtils.get(responseDataMap, "feeStand").toString()),BigDecimalUtils.add(personPriceSum,payInfoDTO.getRestsPrice(),2)).setScale(2);
                    }
                    //如果小于0当做0处理
                    if(BigDecimalUtils.greater(BigDecimal.ZERO,estimateFund)){
                        estimateFund = BigDecimal.ZERO;
                    }
                    responseDataMap.put("estimateFund",estimateFund);//预计基金支付
                    responseDataMap.put("profitAndLossAmount",BigDecimalUtils.subtract(estimateFund,payInfoDTO.getInsurePrice()));//盈亏额
                }
                //查询医保费用
                Map<String, Object> data = insureUnifiedBaseService.queryFeeDetailInfo(map).getData();
                List<Map<String, Object>> feeMapList = MapUtils.get(data, "outptMap");
                if (ListUtils.isEmpty(feeMapList)) {
                    throw new AppException("没有获取到医保费用明细数据");
                }
                //计算药占比，耗材占比
                BigDecimal sumProMedic = new BigDecimal(0.00); // 药占总费用
                BigDecimal sumProConsum = new BigDecimal(0.00); // 耗材占总费用
                BigDecimal sumPrice = new BigDecimal(0.00); // 总费用
                for(Map feeMap:feeMapList){
                    //累加药占总费用
                    if(Constants.UNIFIED_PAY_TYPE.XY.equals(feeMap.get("list_type")) || Constants.UNIFIED_PAY_TYPE.ZCY.equals(feeMap.get("list_type"))
                            || Constants.UNIFIED_PAY_TYPE.ZYYP.equals(feeMap.get("list_type"))|| Constants.UNIFIED_PAY_TYPE.ZZJ.equals(feeMap.get("list_type"))){
                        sumProMedic = BigDecimalUtils.add(sumProMedic,BigDecimalUtils.convert(feeMap.get("det_item_fee_sumamt").toString()).setScale(2)).setScale(2);
                    }
                    //累加耗材总费用
                    if(Constants.UNIFIED_PAY_TYPE.YYCL.equals(feeMap.get("list_type"))){
                        sumProConsum = BigDecimalUtils.add(sumProConsum,BigDecimalUtils.convert(feeMap.get("det_item_fee_sumamt").toString()).setScale(2)).setScale(2);
                    }
                    //累加总费用
                    sumPrice = BigDecimalUtils.add(sumPrice,BigDecimalUtils.convert(feeMap.get("det_item_fee_sumamt").toString()).setScale(2)).setScale(2);
                }
                BigDecimal proMedicMater =  new BigDecimal(0.00);//药占比
                BigDecimal proConsum =  new BigDecimal(0.00);//耗材占比
                if(!BigDecimalUtils.equals(BigDecimal.ZERO,sumPrice)){
                    proMedicMater = BigDecimalUtils.multiply(BigDecimalUtils.divide(sumProMedic,sumPrice),new BigDecimal(100.00));
                    proConsum = BigDecimalUtils.multiply(BigDecimalUtils.divide(sumProConsum,sumPrice),new BigDecimal(100.00));
                }
                responseDataMap.put("proMedicMater",proMedicMater);// 药占比
                responseDataMap.put("proConsum",proConsum);// 耗材比
            }
            //处理排序号
            if (!ListUtils.isEmpty(qualityInfoList)){
                qualityInfoList.stream().forEach(x ->{
                    if(ObjectUtil.isNotEmpty(x.get("order"))){
                        x.put("sort",Integer.valueOf((String)x.get("order"))-1);
                    }
                });
            }
            responseDataMap.put("quality",qualityInfoList);// 质控信息
            //如果为空返回-
            if(groupInfoMap.get("code") == null ||StringUtils.isEmpty(groupInfoMap.get("code").toString())){
                responseDataMap.put("diagCode", "-");// DIP组编码
            }
            if(groupInfoMap.get("name") == null || StringUtils.isEmpty(groupInfoMap.get("name").toString())){
                responseDataMap.put("diagName", "-");// DIP组名称
            }
            if(groupInfoMap.get("feePay") == null){
                responseDataMap.put("diagFeeSco", "-");// 分值
            }
            if(responseDataMap.get("profitAndLossAmount") == null){
                responseDataMap.put("profitAndLossAmount", "-");// 盈亏额
            }
            if(responseDataMap.get("feeStand") == null){
                responseDataMap.put("feeStand", "-");// 总费用标杆
            }
            if(groupInfoMap.get("score_price") == null){
                responseDataMap.put("scorePrice", "-");// 分值单价
            }
            /**==========返回参数封装 End ===========**/
        }catch (Exception e){
            if (e instanceof AppException) {
                throw e;
            } else {
                throw new AppException(e.getMessage());
            }
        }finally {
            drgDipResultService.insertDrgDipQulityInfoLog(logMap);
            /**====== 插入日志 end=========**/
        }
        return responseDataMap;
    }

    private Map<String, Object> requestDIPorDRGDataMap(Map<String, Object> map) {
        Map<String, Object> dataMap = new HashMap<>();
        Map<String, Object> baseInfo = getInsureSettleBaseInfo(map);// 基础信息
        List<Map<String, Object>> diseInfo = getInsureSettleDiseInfo(map);// 诊断信息
        List<Map<String, Object>> oprtInfo = getInsureSettleOprtInfo(map);// 手术信息
        dataMap.put("baseInfo", baseInfo);
        dataMap.put("diseInfo", diseInfo);
        dataMap.put("oprtInfo", oprtInfo);
        return dataMap;
    }

    @Override
    public Map<String, Object> insertInsureSettleInfoForDRGorDIP(Map<String, Object> map) {
        WrapperResponse<DrgDipAuthDTO> drgDipAuthDTOWrapperResponse = drgDipResultService.checkDrgDipBizAuthorizationSettle(map);
        DrgDipAuthDTO drgDipAuthDTO = drgDipAuthDTOWrapperResponse.getData();
        Map<String,Object> drgData = new HashMap<>();
        Map<String,Object> dipData = new HashMap<>();
        Map<String, Object> dataMap = requestDIPorDRGDataMap(map);
        map.put("dataMap",dataMap);
        if ("true".equals(drgDipAuthDTO.getDrg())){
            drgData = insertInsureSettleInfoForDRG(map);

        }
        if ("true".equals(drgDipAuthDTO.getDip())){
            dipData = insertInsureSettleInfoForDIP(map);

        }
        Map resultMap = new HashMap<>();
        resultMap.put("drgData",drgData);
        resultMap.put("dipData",dipData);
        return resultMap;
    }

    /**
     * @Method handerSetleDiseaInfo
     * @Desrciption 对中医和西医进行分组  201：住院入院诊断   西医  203：住院西医次诊断
     * 202：	住院西医确诊诊断  西医   204:住院西医出院诊断
     * 303 :住院中医出院诊断   301:住院中医确诊诊断 302  住院中医次诊断
     * @Param
     * @Author fuhui
     * @Date 2021/11/7 19:47
     * @Return
     **/
    private Map<String, Object> handerSetleDiseaInfo(Map<String, Object> map) {
        List<InptDiagnoseDTO> inptDiagnoseDTOList = insureGetInfoDAO.selectDiseinfoForMap(map);
        Map<String, Object> diseaseMap = new HashMap<>();
        List<InptDiagnoseDTO> zxCollect = inptDiagnoseDTOList.stream().filter
                        (inptDiagnoseDTO -> "303".equals(inptDiagnoseDTO.getTypeCode()) || "301".equals(inptDiagnoseDTO.getTypeCode()) ||
                                "302".equals(inptDiagnoseDTO.getTypeCode()))
                .collect(Collectors.toList());
        List<InptDiagnoseDTO> xiCollect = inptDiagnoseDTOList.stream().filter(inptDiagnoseDTO -> !zxCollect.contains(inptDiagnoseDTO)).collect(Collectors.toList());
        diseaseMap.put("xiCollect", xiCollect);
        diseaseMap.put("zxCollect", zxCollect);
        diseaseMap.put("diseaseCount", inptDiagnoseDTOList.size());
        return diseaseMap;

    }

    /**
     * @Method selectLoadingSetlMsg
     * @Desrciption 查询结算清单信息
     * @Param
     * @Author fuhui
     * @Date 2021/11/6 10:41
     * @Return
     **/
    private Map<String, Object> selectLoadingSetlMsg(Map<String, Object> map) {
        InsureSettleInfoDTO infoDTO = insureGetInfoDAO.selectBaseSetlInfo(map);
        if (infoDTO == null) {
            throw new AppException("未获取到清单信息,请先选中左侧人员列表进行信息补充");
        }
        String isHospital = MapUtils.get(map, "isHospital");
        Map<String, Object> setlinfo = new HashMap<>();
        setlinfo.put("stasType", infoDTO.getStasType());
        setlinfo.put("medicalRegNo", MapUtils.get(map, "medicalRegNo"));
        setlinfo.put("visitId", infoDTO.getVisitId());
        setlinfo.put("mdtrtId", MapUtils.get(map, "medicalRegNo")); // 就医登记号
        setlinfo.put("insureSettleId", infoDTO.getInsureSettleId()); // 结算ID
        setlinfo.put("setlId", infoDTO.getInsureSettleId()); // 结算ID
        setlinfo.put("fixmedinsName", infoDTO.getFixmedinsName()); // 定点医药机构名称
        setlinfo.put("fixmedinsCode", infoDTO.getFixmedinsCode()); // 定点医药机构编号
        setlinfo.put("hiSetlLv", infoDTO.getHiSetlLv());//医保结算等级
        //如果之前没保存从字典获取医保结算等级
        if(StringUtils.isEmpty(infoDTO.getHiSetlLv())){
            String settleLv = "";
            Map dictMap = new HashMap(2);
            dictMap.put("hospCode", MapUtils.get(map, "hospCode"));
            dictMap.put("insureRegCode", infoDTO.getInsuplc());
            dictMap.put("code", "HI_SETL_LV");
            Map<String, String> dictMap1 = insureDictService_consumer.queryDictByCode(dictMap).getData();
            if(dictMap != null ){
                for (Map.Entry<String,String> entry : dictMap1.entrySet()) {
                    settleLv = entry.getKey();
                    if (settleLv != null) {
                        break;
                    }
                }
            }
            setlinfo.put("hiSetlLv", settleLv);//医保结算等级
        }
        //如果之前没保存发票，再查询发票
        if(StringUtils.isEmpty(infoDTO.getBillCode()) || StringUtils.isEmpty(infoDTO.getBillNo())){
            Map<String, Object> billNoMap = generatorBillNo(map);
            if(ObjectUtil.isNotEmpty(MapUtils.get(billNoMap, "billCode")) && ObjectUtil.isNotEmpty(MapUtils.get(billNoMap, "billNo"))){
                setlinfo.put("billCode", MapUtils.get(billNoMap, "billCode")); // 票据代码**********
                setlinfo.put("billNo", MapUtils.get(billNoMap, "billNo")); // 票据号码**********
                //更新结算清单的数据
                Map<String, Object> updateMap = new HashMap<>();
                updateMap.put("insureSettleId",infoDTO.getInsureSettleId());
                updateMap.put("hospCode",MapUtils.get(map, "hospCode"));
                updateMap.put("visitId",infoDTO.getVisitId());
                updateMap.put("billCode",MapUtils.get(billNoMap, "billCode"));
                updateMap.put("billNo", MapUtils.get(billNoMap, "billNo"));
                insureGetInfoDAO.updateBill(updateMap);
            }
        }
        setlinfo.put("psnNo", infoDTO.getHiNo()); // 医保编号
        setlinfo.put("hiNo", infoDTO.getHiNo()); //医保编号
        setlinfo.put("iptMedType", infoDTO.getIptMedType());  // 住院业务类型
        setlinfo.put("settleNo", infoDTO.getSettleNo()); //流水清单号
        setlinfo.put("medcasno", infoDTO.getMedcasno()); // 病案号
        setlinfo.put("dclaTime", DateUtils.getNow()); //结算清单申报时间
        setlinfo.put("psnName", infoDTO.getPsnName());// 人员姓名
        setlinfo.put("gend", infoDTO.getGend());// 性别
        setlinfo.put("brdy", infoDTO.getBrdy()); // 出生日期
        setlinfo.put("age", infoDTO.getAge() == 0 ? null : infoDTO.getAge()); // 年龄
        setlinfo.put("ntly", infoDTO.getNtly()); // 国籍
        setlinfo.put("nwbAge", infoDTO.getNebAge() == 0 ? null : infoDTO.getNebAge()); // （年龄不足1周岁）年龄
        setlinfo.put("naty", infoDTO.getNaty()); // 民族
        setlinfo.put("patnCertType", infoDTO.getPatnCertType()); // 患者证件类别
        setlinfo.put("certno", infoDTO.getCertNo()); // 证件号码
        setlinfo.put("prfs", infoDTO.getPrfs()); // 职业
        setlinfo.put("currAddr", infoDTO.getCurrAddr()); // 现住址
        //现住址四级地址
        setlinfo.put("province", infoDTO.getProvince()); // 现住址-省
        setlinfo.put("city", infoDTO.getCity()); // 现住址-市
        setlinfo.put("county", infoDTO.getCounty()); // 现住址-县
        setlinfo.put("detailAddress", infoDTO.getDetailAddress()); // 现住址-详细地址
        setlinfo.put("empName", infoDTO.getEmpName()); // 单位名称
        setlinfo.put("empAddr", infoDTO.getEmpAddr()); // 单位地址
        setlinfo.put("empTel", infoDTO.getEmpTel()); // 单位电话
        setlinfo.put("poscode", infoDTO.getPoscode()); // 邮编
        setlinfo.put("conerName", infoDTO.getConerName()); // 联系人姓名
        setlinfo.put("patnRlts", infoDTO.getPatnRlts()); // 与患者关系
        setlinfo.put("conerAddr", infoDTO.getConerAddr()); // 联系人地址
        //联系人地址四级地址
        setlinfo.put("conProvince", infoDTO.getConProvince()); // 联系人地址-省
        setlinfo.put("conCity", infoDTO.getConCity()); // 联系人地址-市
        setlinfo.put("conCounty", infoDTO.getConCounty()); // 联系人地址-县
        setlinfo.put("conDetailAddress", infoDTO.getConDetailAddress()); // 联系人地址-详细地址
        setlinfo.put("conerTel", infoDTO.getConerTel()); // 联系人电话
        setlinfo.put("hiType", infoDTO.getHiType()); // 医保类型  也就是险种   sp_psn_type
        String insuplcAdmdvs = infoDTO.getInsuplc();
        map.put("insuplcAdmdvs", insuplcAdmdvs);
        Map<String, Object> admdvsMap = insureGetInfoDAO.selectInsuplcAdmdvs(map);
        if (!MapUtils.isEmpty(admdvsMap)) {
            setlinfo.put("insuplcName", MapUtils.get(admdvsMap, "admdvsName")); // 参保地 *******
        }
        //参保地显示市加县
        String upInsuplcAdmdvs = null;
        if("00".equals(insuplcAdmdvs.substring(insuplcAdmdvs.length()-2,insuplcAdmdvs.length()))){
            if("00".equals(insuplcAdmdvs.substring(insuplcAdmdvs.length()-4,insuplcAdmdvs.length()-2))){
                upInsuplcAdmdvs = insuplcAdmdvs;
            }else {
                upInsuplcAdmdvs = insuplcAdmdvs.substring(0,insuplcAdmdvs.length()-4) + "0000";
            }
        }else {
            upInsuplcAdmdvs = insuplcAdmdvs.substring(0,insuplcAdmdvs.length()-2) + "00";
        }
        map.put("insuplcAdmdvs", upInsuplcAdmdvs);
        Map<String, Object> upAdmdvsMap = insureGetInfoDAO.selectInsuplcAdmdvs(map);
        if (!MapUtils.isEmpty(upAdmdvsMap)) {
            setlinfo.put("insuplcName", (String)MapUtils.get(upAdmdvsMap, "admdvsName") + setlinfo.get("insuplcName")); // 参保地 *******
        }
        setlinfo.put("insuplc", insuplcAdmdvs); // 参保地 *******
        setlinfo.put("spPsnType", infoDTO.getSpPsnType()); // 特殊人员类型
        setlinfo.put("nwbAdmType", infoDTO.getNwbAdmType()); // 新生儿入院类型

        setlinfo.put("nwbBirWt", infoDTO.getNwbBirWt()); // 新生儿出生体重 *******
        setlinfo.put("nwbAdmWt", infoDTO.getNwbAdmWt()); // 新生儿入院体重 *******
        if (Constants.SF.F.equals(isHospital)) {
            setlinfo.put("opspMdtrtDate", infoDTO.getOpspMdtrtDate()); // 门诊慢特病就诊日期 *******
            setlinfo.put("opspDiagCaty", infoDTO.getOpspDiagCaty()); // 门诊慢特病诊断科别 *******
        } else {
            setlinfo.put("opspMdtrtDate", null); // 门诊慢特病就诊日期 *******
            setlinfo.put("opspDiagCaty", null); // 门诊慢特病诊断科别 *******
        }
        setlinfo.put("iptMedTpe", infoDTO.getIptMedType()); // 住院医疗类型
        setlinfo.put("admWay", infoDTO.getAdmWay()); // 入院途径 *******
        String trtType = infoDTO.getTrtType();
        if ("10".equals(trtType)) {
            setlinfo.put("trtType", "1"); // 西医
        } else if ("21".equals(trtType)) {
            setlinfo.put("trtType", "2.1"); // 中医
        } else if ("22".equals(trtType)) {
            setlinfo.put("trtType", "2.2"); // 民族医
        } else if ("30".equals(trtType)){
            setlinfo.put("trtType", "3"); // 中西医
        }
        setlinfo.put("admTime", infoDTO.getAdmTime()); // 入院时间 *******
        setlinfo.put("admCaty", infoDTO.getAdmCaty()); // 入院科别
        setlinfo.put("refldeptDept", infoDTO.getRefldeptDept()); // 转科科别
        setlinfo.put("dscgTime", infoDTO.getDscgTime()); // 出院时间 *******
        setlinfo.put("dscgCaty", infoDTO.getDscgCaty()); // 出院科别
        setlinfo.put("actIptDays", infoDTO.getActIptDays()); // 实际住院天数 *******

        setlinfo.put("otpWmDise", infoDTO.getOptWmDise()); // 门（急）诊诊断 *******
        setlinfo.put("wmDiseCode", infoDTO.getWmDiswCode()); // 西医诊断疾病代码 *******
        setlinfo.put("otpTcmDise", infoDTO.getOptTcmDise()); // 门（急）诊中医诊断 *******
        setlinfo.put("tcmDiseCode", infoDTO.getTcmDiseCode()); // 中医诊断代码 *******

        setlinfo.put("otpWmDiseCode", infoDTO.getOptWmDiseCode()); // 中医诊断代码 *******
        setlinfo.put("otpTcmDiseCode", infoDTO.getOptTcmDiseCode()); // 中医诊断代码 *******

        setlinfo.put("diagCodeCnt", infoDTO.getDiagCodeCnt()); // 诊断代码计数 *******
        setlinfo.put("oprnOprtCodeCnt", infoDTO.getOprnOprtCodeCnt()); // 手术操作代码计数 *******
        setlinfo.put("ventUsedDura", infoDTO.getVentUsedDura()); // 呼吸机使用时长 *******
        Object inptBeforeDay = "";
        Object inptBeforeHour = "";
        Object inptBeforeMinute = "";
        Object inptLastDay = "";
        Object inptLastHour = "";
        Object inptLastMinute = "";
        setlinfo.put("inptBeforeDay", inptBeforeDay);
        setlinfo.put("inptBeforeHour", inptBeforeHour);
        setlinfo.put("inptBeforeMinute", inptBeforeMinute);
        setlinfo.put("inptLastDay", inptLastDay);
        setlinfo.put("inptLastHour", inptLastHour);
        setlinfo.put("inptLastMinute", inptLastMinute);
        setlinfo.put("pwcryBfadmComaDura", infoDTO.getPwcryBfadmComaDura()); // 颅脑损伤患者入院前昏迷时长 *******
        setlinfo.put("pwcryAfadmComaDura", infoDTO.getPwcryAfadmComaDura()); //颅脑损伤患者入院后昏迷时长
        setlinfo.put("bldCat", infoDTO.getBldCat()); //输血品种
        setlinfo.put("bldAmt", infoDTO.getBldAmt()); //输血量
        setlinfo.put("bldUnt", infoDTO.getBldUnt()); //输血计量单位
        setlinfo.put("spgaNurscareDays", infoDTO.getSpgaNurscareDays()); // 特级护理天数 *******
        setlinfo.put("lv1NurscareDays", infoDTO.getLv1NurscareDays()); // 一级护理天数 *******
        setlinfo.put("scdNurscareDays", infoDTO.getScdNurscareDays()); // 二级护理天数 *******
        setlinfo.put("lv3NurscareDays", infoDTO.getLv3NursecareDays()); // 三级护理天数 *******
        setlinfo.put("dscgWay", infoDTO.getDscgWay()); // 离院方式 *******
        setlinfo.put("acpMedinsName", infoDTO.getAcpMedinsName()); // 拟接收机构名称 *******
        setlinfo.put("acpMedinsCode", infoDTO.getAcpMedinsCode()); // 拟接收机构代码 ******

        setlinfo.put("billCode", infoDTO.getBillCode()); // 票据代码**********
        setlinfo.put("billNo", infoDTO.getBillNo()); // 票据号码**********
        setlinfo.put("bizSn", infoDTO.getBizSn()); // 业务流水号
        setlinfo.put("daysRinpFlag31", infoDTO.getDaysRinpFlag31()); // 是否有出院31天再住院计划 *******
        setlinfo.put("daysRinpPup31", infoDTO.getDaysRinpPup31()); // 出院31天内再住院目的 *******
        setlinfo.put("chfpdrName", infoDTO.getChfpdrName()); // 主诊医生姓名 *******
        setlinfo.put("chfpdrCode", infoDTO.getChfpdrCode()); // 主诊医生代码 *******
        setlinfo.put("zrNurseName", infoDTO.getZrNurseName()); // 责任护士名 *******
        setlinfo.put("zrNurseCode", infoDTO.getZrNurseCode()); // 责任护士代码 *******

        setlinfo.put("setlBegnDate", infoDTO.getSetlBegnDate()); //结算开始日期 *******
        setlinfo.put("setlEndDate", infoDTO.getSetlEnDate()); // 结算结束日期 *******

        setlinfo.put("psnSelfpay", infoDTO.getPsnSelfpay()); // 个人自付  overlmt_selfpay
        setlinfo.put("psnOwnpay", infoDTO.getPsnOwnpay()); // 个人自费
        setlinfo.put("acctPay", infoDTO.getAcctPay()); // 个人账户支出
        setlinfo.put("psnCashpay", infoDTO.getPsnCashpay()); // 个人现金支付
        setlinfo.put("hiPaymtd", infoDTO.getHiPaymtd()); // 医保支付方式
        setlinfo.put("hsorg", infoDTO.getHsorg()); // 医保机构经办人
        setlinfo.put("hsorgOpter", infoDTO.getHosrgOpter()); // 医保机构经办人
        setlinfo.put("medinsFillDept", infoDTO.getMedinsFillDept()); // 医疗机构填报部门
        setlinfo.put("medinsFillPsn", infoDTO.getMedinsFillPsn()); // 医疗机构填报人
        //查询就诊信息
        InsureIndividualVisitDTO insureIndividualVisitDTO = insureUnifiedCommonUtil.commonGetVisitInfo(map);
        //判断是否为单病种住院
        if(Constant.UnifiedPay.YWLX.DBZZY.equals(insureIndividualVisitDTO.getAka130())){
            setlinfo.put("medType", insureIndividualVisitDTO.getAka130());//医疗类别
            setlinfo.put("bka006", insureIndividualVisitDTO.getBka006());//单病种编码
            setlinfo.put("bka006Name", insureIndividualVisitDTO.getBka006Name());//单病种名称
        }
        return setlinfo;
    }

    /**
     * @Method insertSetleInfo
     * @Desrciption 处理医疗保障基金结算清单 -- 保存清单结算信息
     * @Param
     * @Author fuhui
     * @Date 2021/11/3 20:18
     * @Return
     **/
    private Map<String, Object>  insertSetleInfo(Map<String, Object> map) {
        Map<String, Object> setlInfoMap = MapUtils.get(map, "setlinfo");
        String isHospital = MapUtils.get(map, "isHospital");
        if (MapUtils.isEmpty(setlInfoMap)) {
            throw new AppException("清单结算信息节点数据为空");
        }
        //去除空字符串
        deleteConstantBar(setlInfoMap);
        //医保机构处理
        if(MapUtils.get(setlInfoMap, "hsorg") == null){
            setlInfoMap.put("hsorg","");
        }
        //票据代码处理
        if(MapUtils.get(setlInfoMap, "billCode") == null){
            setlInfoMap.put("billCode","");
        }
        //票据号码处理
        if(MapUtils.get(setlInfoMap, "billNo") == null){
            setlInfoMap.put("billNo","");
        }
        setlInfoMap.put("id", SnowflakeUtils.getId());
        setlInfoMap.put("hospCode", MapUtils.get(map, "hospCode"));
        setlInfoMap.put("crteId", MapUtils.get(map, "crteId"));
        setlInfoMap.put("crteName", MapUtils.get(map, "crteName"));
        setlInfoMap.put("crteTime", DateUtils.getNow());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.Y_M_DH_M_S);
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.Y_M_D);
        Date date = new Date();
        getEmptyErr(setlInfoMap, "ntly", "国籍不能为空");
        getEmptyErr(setlInfoMap, "naty", "民族不能为空");
        getEmptyErr(setlInfoMap, "brdy", "出生日期不能为空");
        getEmptyErr(setlInfoMap, "patnCertType", "患者证件类别不能为空");
        getEmptyErr(setlInfoMap, "certno", "患者证件号码不能为空");
        getEmptyErr(setlInfoMap, "prfs", "职业不能为空");
        getEmptyErr(setlInfoMap, "conerName", "联系人姓名不能为空");
        getEmptyErr(setlInfoMap, "patnRlts", "与患者关系不能为空");
        getEmptyErr(setlInfoMap, "conerAddr", "联系人地址不能为空");
        getEmptyErr(setlInfoMap, "conerTel", "联系人电话不能为空");
        getEmptyErr(setlInfoMap, "hiType", "医保类型不能为空");
        getEmptyErr(setlInfoMap, "insuplc", "参保地不能为空");
        //允许先不填发票
//        getEmptyErr(setlInfoMap, "billNo", "票据号码不能为空");
//        getEmptyErr(setlInfoMap, "billCode", "票据代码不能为空");
        getEmptyErr(setlInfoMap, "bizSn", "业务流水号不能为空");
        getEmptyErr(setlInfoMap, "setlBegnDate", "结算期间开始时间不能为空");
        getEmptyErr(setlInfoMap, "setlEndDate", "结算期间结束时间不能为空");
        getEmptyErr(setlInfoMap, "medinsFillDept", "医疗机构填报部门不能为空");
        getEmptyErr(setlInfoMap, "medinsFillPsn", "医疗机构填报人不能为空");


        //结算等级处理
        Map dictMap = new HashMap(2);
        dictMap.put("hospCode", MapUtils.get(map, "hospCode"));
        dictMap.put("insureRegCode", MapUtils.get(setlInfoMap, "insuplc"));
        dictMap.put("code", "HI_SETL_LV");
        Map<String, String> dictMap1 = insureDictService_consumer.queryDictByCode(dictMap).getData();
        for(String key:dictMap1.keySet()){
            if(dictMap1.get(key).equals(MapUtils.get(setlInfoMap, "hiSetlLv"))){
                setlInfoMap.put("hiSetlLv", key);
            }
        }

        //护理天数为空处理
        String lv1NurscareDays = MapUtil.getStr(setlInfoMap, "lv1NurscareDays") ;
        String lv3NurscareDays = MapUtil.getStr(setlInfoMap, "lv3NurscareDays");
        String spgaNurscareDays = MapUtil.getStr(setlInfoMap, "spgaNurscareDays");
        String scdNurscareDays = MapUtil.getStr(setlInfoMap, "scdNurscareDays");
        if(StringUtils.isEmpty(lv1NurscareDays)){
            setlInfoMap.put("lv1NurscareDays",null);
        }
        if(StringUtils.isEmpty(lv3NurscareDays)){
            setlInfoMap.put("lv3NurscareDays",null);
        }
        if(StringUtils.isEmpty(spgaNurscareDays)){
            setlInfoMap.put("spgaNurscareDays",null);
        }
        if(StringUtils.isEmpty(scdNurscareDays)){
            setlInfoMap.put("scdNurscareDays",null);
        }
        Object objNwbBirWt = MapUtils.get(setlInfoMap, "nwbBirWt"); // 新生儿出生体重
        if (ObjectUtil.isEmpty(objNwbBirWt)){
          setlInfoMap.put("nwbBirWt", null); // 新生儿出生体重
        }
        //校验新生儿出生体重
        if (ObjectUtil.isNotEmpty(objNwbBirWt)){
            String nwbBirWt = String.valueOf(objNwbBirWt) ;
            for(int i = 0;i < nwbBirWt.length();i++){
                if(!(Integer.valueOf(nwbBirWt.charAt(i)) >= Integer.valueOf('0') && Integer.valueOf(nwbBirWt.charAt(i)) <= Integer.valueOf('9'))
                        && Integer.valueOf(nwbBirWt.charAt(i)) != Integer.valueOf(',') ){
                    throw new AppException("新生儿出生体重填写格式不正确");
                }
            }
        }
        /*if (objNwbBirWt instanceof String || objNwbBirWt == null) {
            setlInfoMap.put("nwbBirWt", null); // 新生儿出生体重
        }*/
        Object objNwbAdmWt = MapUtils.get(setlInfoMap, "nwbAdmWt"); //新生儿入院体重
        if (ObjectUtil.isEmpty(objNwbAdmWt)){
          setlInfoMap.put("nwbAdmWt", null); // 新生儿入院体重
        }
        /*if (objNwbAdmWt instanceof String || objNwbAdmWt == null) {
            setlInfoMap.put("nwbAdmWt", null); // 新生儿入院体重
        }*/
        if (Constants.SF.S.equals(isHospital)) {
            getEmptyErr(setlInfoMap, "daysRinpFlag31", "出院31天内再住院计划标志不能为空");
            getEmptyErr(setlInfoMap, "chfpdrName", "主诊医师姓名不能为空");
            getEmptyErr(setlInfoMap, "chfpdrCode", "主诊医师代码不能为空");
            String chfpdrCodeStr = MapUtils.get(setlInfoMap, "chfpdrCode");
            if (!"D".equals(chfpdrCodeStr.substring(0, 1))) {
                throw new AppException("主诊断医师代码必须是D开头的国家贯标码");
            }
            getEmptyErr(setlInfoMap, "zrNurseName", "责任护士姓名不能为空");
            getEmptyErr(setlInfoMap, "zrNurseCode", "责任护士代码不能为空");
            String zrNurseCodeStr = MapUtils.get(setlInfoMap, "zrNurseCode");
            if (!"N".equals(zrNurseCodeStr.substring(0, 1))) {
                throw new AppException("责任护士代码必须是N开头的国家贯标码");
            }
            getEmptyErr(setlInfoMap, "dscgWay", "离院方式不能为空");
            getEmptyErr(setlInfoMap, "dscgCaty", "出院科别不能为空");
            getEmptyErr(setlInfoMap, "admTime", "入院时间不能为空");
            getEmptyErr(setlInfoMap, "dscgTime", "出院时间不能为空");
            getEmptyErr(setlInfoMap, "admCaty", "入院科别不能为空");
            if (MapUtils.get(setlInfoMap, "dscgTime") instanceof Long) {
                date.setTime(MapUtils.get(setlInfoMap, "dscgTime"));
                setlInfoMap.put("dscgTime", simpleDateFormat.format(date)); // 出院时间
            }
            if (MapUtils.get(setlInfoMap, "admTime") instanceof Long) {
                date.setTime(MapUtils.get(setlInfoMap, "admTime"));
                setlInfoMap.put("admTime", simpleDateFormat.format(date)); // 入院时间
            }

            // update by qiang.fan
            if (MapUtils.get(setlInfoMap, "trtType") != null) {
                String trtType = MapUtils.get(setlInfoMap, "trtType").toString();
                if ("1".equals(trtType)) {
                    setlInfoMap.put("trtType", "10"); // 西医
                } else if ("2.1".equals(trtType)) {
                    setlInfoMap.put("trtType", "21"); // 中医
                } else if ("2.2".equals(trtType)) {
                    setlInfoMap.put("trtType", "22"); // 民族医
                } else if ("3".equals(trtType)){
                    setlInfoMap.put("trtType", "30"); // 中西医
                }
            }
        } else {
            getEmptyErr(setlInfoMap, "opspDiagCaty", "门诊慢特病诊断科别不能为空");
            getEmptyErr(setlInfoMap, "opspMdtrtDate", "门诊慢特病就诊日期不能为空");

            if (MapUtils.get(setlInfoMap, "opspMdtrtDate") instanceof Long) {
                date.setTime(MapUtils.get(setlInfoMap, "opspMdtrtDate"));
                setlInfoMap.put("opspMdtrtDate", simpleDateFormat.format(date));
            }
            //门特也需要处理入院时间，出院时间
            if (MapUtils.get(setlInfoMap, "dscgTime") instanceof Long) {
                date.setTime(MapUtils.get(setlInfoMap, "dscgTime"));
                setlInfoMap.put("dscgTime", simpleDateFormat.format(date)); // 出院时间
            }
            if (MapUtils.get(setlInfoMap, "admTime") instanceof Long) {
                date.setTime(MapUtils.get(setlInfoMap, "admTime"));
                setlInfoMap.put("admTime", simpleDateFormat.format(date)); // 入院时间
            }
            // 门特也需要处理治疗类别
            if (MapUtils.get(setlInfoMap, "trtType") != null) {
                String trtType = MapUtils.get(setlInfoMap, "trtType").toString();
                if ("1".equals(trtType)) {
                    setlInfoMap.put("trtType", "10"); // 西医
                } else if ("2.1".equals(trtType)) {
                    setlInfoMap.put("trtType", "21"); // 中医
                } else if ("2.2".equals(trtType)) {
                    setlInfoMap.put("trtType", "22"); // 民族医
                } else if ("3".equals(trtType)){
                    setlInfoMap.put("trtType", "30"); // 中西医
                }
            }
        }
        if (MapUtils.get(setlInfoMap, "dclaTime") instanceof Long) {
            date.setTime(MapUtils.get(setlInfoMap, "dclaTime"));
            setlInfoMap.put("dclaTime", simpleDateFormat.format(date));
        }
        /**
         * 前端传的日期时间型  有可能是Integer  和  Long
         */
        Object brdy = MapUtils.get(setlInfoMap, "brdy");
        if (brdy instanceof Long) {
            date.setTime((Long) brdy);
            setlInfoMap.put("brdy", dateFormat.format(date)); // 出生日期
        }
        if (brdy instanceof Integer) {
            date.setTime(((Integer) brdy).longValue());
            setlInfoMap.put("brdy", dateFormat.format(date)); // 出生日期
        }
        Object setlBegnDate = MapUtils.get(setlInfoMap, "setlBegnDate");
        if (setlBegnDate != null) {
            setlInfoMap.put("setlBegnDate", DateUtils.getNumerToStringDate(setlBegnDate)); //结算开始时间
        }
        Object setlEndDate = MapUtils.get(setlInfoMap, "setlEndDate");
        if (setlEndDate != null) {
            setlInfoMap.put("setlEndDate", DateUtils.getNumerToStringDate(setlEndDate)); // 结算结束时间
        }
        Object ageObject = MapUtils.get(setlInfoMap, "age");
        if (ageObject instanceof String && StringUtils.isEmpty(ageObject.toString())) {
            setlInfoMap.put("age", null); // 年龄
        }
        Object nwbAgeObject = MapUtils.get(setlInfoMap, "nwbAge");
        if (nwbAgeObject instanceof String && StringUtils.isEmpty(nwbAgeObject.toString())) {
            setlInfoMap.put("nwbAge", null); // 新生儿年龄
        }
        insureGetInfoDAO.deleteSetleInfo(setlInfoMap);
        insureGetInfoDAO.insertSetleInfo(setlInfoMap);
        return setlInfoMap;
    }

    /**
     * @Method insertOpspdiseinfo
     * @Desrciption 处理医疗保障基金结算清单 -- 住院诊断信息
     * @Param zxCollect:中医诊断集合
     * xiCollect: 西医诊断集合
     * @Author fuhui
     * @Date 2021/11/2 16:13
     * @Return
     **/
    private synchronized void insertDiseaseInfo(Map<String, Object> map) {
        Map<String, Object> diseaseMap = MapUtils.get(map, "diseinfo");
        if (MapUtils.isEmpty(diseaseMap)) {
            return;
        }
        List<Map<String, Object>> zxCollect = MapUtils.get(diseaseMap, "zxCollect");
        List<Map<String, Object>> xiCollect = MapUtils.get(diseaseMap, "xiCollect");
        List<Map<String, Object>> inptDiagnoseDTOList = new ArrayList<>();
        /**
         * 如果新增时，默认是西医诊断且是非主诊断
         */
        if (!ListUtils.isEmpty(zxCollect)) {
            zxCollect = zxCollect.stream().filter
                            (item -> StringUtils.isNotEmpty(MapUtils.get(item, "diseaseName")) && StringUtils.isNotEmpty(MapUtils.get(item, "diseaseCode"))).
                    collect(Collectors.toList());
            if (!ListUtils.isEmpty(zxCollect)) {
                List<Map<String, Object>> zyIsMain = zxCollect.stream().filter(item -> "1".equals(MapUtils.get(item, "isMain"))).collect(Collectors.toList());
                if (ListUtils.isEmpty(zyIsMain)) {
                    zxCollect.get(0).put("isMain", "1");
                }
                zxCollect.stream().forEach(item -> {
                    if (StringUtils.isEmpty(MapUtils.get(item, "typeCode"))) {
                        item.put("typeCode", "2");
                    }
                    if (!"1".equals(MapUtils.get(item, "isMain"))) {
                        item.put("isMain", "0");
                    }
                });
                inptDiagnoseDTOList.addAll(zxCollect);
            }
        }
        /**
         * 如果新增时，默认是中医诊断且是非主诊断
         */
        if (!ListUtils.isEmpty(xiCollect)) {
            xiCollect = xiCollect.stream().filter
                            (item -> StringUtils.isNotEmpty(MapUtils.get(item, "diseaseName")) && StringUtils.isNotEmpty(MapUtils.get(item, "diseaseCode"))).
                    collect(Collectors.toList());
            if (!ListUtils.isEmpty(xiCollect)) {
                List<Map<String, Object>> xiIsMain = xiCollect.stream().filter(item -> "1".equals(MapUtils.get(item, "isMain"))).collect(Collectors.toList());
                if (ListUtils.isEmpty(xiIsMain)) {
                    xiCollect.get(0).put("isMain", "1");
                }
                System.out.println("--------------" + xiCollect);
                xiCollect.stream().forEach(item -> {
                    if (StringUtils.isEmpty(MapUtils.get(item, "typeCode"))) {
                        item.put("typeCode", "1");
                    }
                    if (!"1".equals(MapUtils.get(item, "isMain"))) {
                        item.put("isMain", "0");
                    }
                });
                inptDiagnoseDTOList.addAll(xiCollect);
            }
        }
        if (!ListUtils.isEmpty(inptDiagnoseDTOList)) {
            List<Map<String, Object>> mapList = insertCommonSettleInfo(map, inptDiagnoseDTOList);
            insureGetInfoDAO.deleteDisease(map);
            mapList.stream().forEach(item -> {
                if (StringUtils.isEmpty(MapUtils.get(item, "admCondType"))) {
                    throw new AppException("请完善住院诊断信息节点的入院病情");
                }
            });
            insureGetInfoDAO.insertDisease(mapList);
        }
    }


    /**
     * @Method insertOpspdiseinfo
     * @Desrciption 处理医疗保障基金结算清单 -- 门诊慢特病诊断信息
     * @Param
     * @Author fuhui
     * @Date 2021/11/2 16:13
     * @Return
     **/
    private void insertOpspdiseinfo(Map<String, Object> map) {
        List<Map<String, Object>> opspdiseinfoList = MapUtils.get(map, "opspdiseinfo");
        opspdiseinfoList = opspdiseinfoList.stream().filter(item -> StringUtils.isNotEmpty(MapUtils.get(item, "diagCode"))).collect(Collectors.toList());
        insureGetInfoDAO.deleteOpspdiseinfo(map);
        List<Map<String, Object>> mapList = insertCommonSettleInfo(map, opspdiseinfoList);
        if (!ListUtils.isEmpty(mapList)) {
            insureGetInfoDAO.deleteOpspdiseinfo(map);
            insureGetInfoDAO.insertOpspdiseinfo(mapList);
        }
    }

    /**
     * @Method getSettleInfo
     * @Desrciption 1、交易输入结算清单信息为单行数据，输入其他信息均为多行数据；
     * 2、输入项信息按照《医疗保障基金结算清单填写规范》中的规范要求填写；
     * 3、每次接口调用只上传一位患者的信息。
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-10 14:11
     * @Return java.util.Map
     **/
    @Override
    public Map<String, Object> insertSetlInfo(Map<String, Object> map) {
        Map<String, Object> resultDataMap;
        InsureSettleInfoDTO insureSettleInfoDTO = insureGetInfoDAO.selectBaseSetlInfo(map);
        if (insureSettleInfoDTO != null) {
            resultDataMap = selectLoadingSettleInfo(map);
        } else {
            resultDataMap = new HashMap<>();
            String isHospital = MapUtils.get(map, "isHospital");
            //1.调用费用明细查询接口 得到收费项目信息节点
            List<Map<String, Object>> itemInfoMap = insertItemInfo(map);
            // 2.调用结算信息查询   得到基金支付信息节点
            Map<String, Object> settleMap = insertPayInfo(map);
            List<Map<String, Object>> setldetail = MapUtils.get(settleMap, "setldetail");
            if (!ListUtils.isEmpty(setldetail)) {
                // 基金分项排除 职工基本医疗保险统筹基金（310101）     城乡居民基本医疗保险基金（390101）
                setldetail = setldetail.stream().filter(item -> !"310101".equals(MapUtils.get(item, "setl_proc_info")) && !"310100".equals(MapUtils.get(item, "setl_proc_info")) &&
                                !"390101".equals(MapUtils.get(item, "setl_proc_info")) && !"390100".equals(MapUtils.get(item, "setl_proc_info"))).
                        collect(Collectors.toList());
            }
            Map<String, Object> setlinfo = MapUtils.get(settleMap, "setlinfo");
            List<Map<String, Object>> mapList = new ArrayList<>();
            List<Map<String, Object>> icuInfoMapList = new ArrayList<>();
            List<OperInfoRecordDTO> infoRecordDTOList = new ArrayList<>();
            Map<String, Object> diseaMap = new HashMap<>();
            if (Constants.SF.F.equals(isHospital)) {
                // 3.查询门诊诊断信息   得到门诊慢特病诊断信息节点
                mapList = insertOutDiagnose(map);
                resultDataMap.put("opspdiseinfo", mapList);
                //4.封装住院诊断信息 没有信息，封装空信息
                diseaMap.put("diseaseCount",0);
                diseaMap.put("xiCollect",new ArrayList<>());
                diseaMap.put("zxCollect",new ArrayList<>());
            } else {
                // 5.查询手术操作信息   得到手术操作节点信息
                infoRecordDTOList = handerOperInfo(map);
                map.put("operCount", infoRecordDTOList.size());
                // 6.查询重症监护信息   得到重症监护信息节点
                icuInfoMapList = handerIcuInfo(map);
                // 4.查询住院诊断信息   得到住院诊断节点信息
                diseaMap = handerInptDiagnose(map);
            }

            map.put("diseaseCount", MapUtils.get(diseaMap, "diseaseCount"));
            map.put("xiCollect", MapUtils.get(diseaMap, "xiCollect"));
            // 7.结算清单基本信息
            Map<String, Object> setlInfoMap = handerBaseSetlInfo(map, setlinfo);
            //单病种费用集合
            List<Map<String, Object>> dbzInfo = new ArrayList<>();
            //判断是否为单病种住院
            if(setlInfoMap.get("medType")!= null){
                Map<String, Object> dbzInfoMap = new HashMap<>();
                BigDecimal sumDetItemFeeSumamt = new BigDecimal(0.00); // 总费用
                BigDecimal AClassFee = new BigDecimal(0.00);  // 甲类费用
                BigDecimal BClassFee = new BigDecimal(0.00);  // 乙类费用
                BigDecimal CClassFee = new BigDecimal(0.00);  // 丙类费用
                BigDecimal otherClassFee = new BigDecimal(0.00); // 其他费用
                for(Map<String, Object> map1:itemInfoMap){
                    sumDetItemFeeSumamt = BigDecimalUtils.add(sumDetItemFeeSumamt,BigDecimalUtils.convert(map1.get("amt").toString()));
                    AClassFee = BigDecimalUtils.add(AClassFee,BigDecimalUtils.convert(map1.get("claaSumfee").toString()));
                    BClassFee = BigDecimalUtils.add(BClassFee,BigDecimalUtils.convert(map1.get("clabAmt").toString()));
                    CClassFee = BigDecimalUtils.add(CClassFee,BigDecimalUtils.convert(map1.get("fulamtOwnpayAmt").toString()));
                    otherClassFee = BigDecimalUtils.add(otherClassFee,BigDecimalUtils.convert(map1.get("othAmt").toString()));
                }
                dbzInfoMap.put("amt", sumDetItemFeeSumamt);
                dbzInfoMap.put("claaSumfee", AClassFee);
                dbzInfoMap.put("clabAmt", BClassFee);
                dbzInfoMap.put("fulamtOwnpayAmt", CClassFee);
                dbzInfoMap.put("othAmt", otherClassFee);
                dbzInfoMap.put("medChrgitm", setlInfoMap.get("bka006")+"+"+setlInfoMap.get("bka006Name"));
                dbzInfo.add(dbzInfoMap);
                for(int i = 0;i < itemInfoMap.size(); i++){
                    if("15".equals(itemInfoMap.get(i).get("medChrgitm"))){
                        itemInfoMap.remove(i);
                    }
                }
            }
            // 输血信息
            List<Map<String, Object>> bldInfoListMap = insureGetInfoDAO.selectBldInfo(map);
            resultDataMap.put("dbzInfo", dbzInfo);
            resultDataMap.put("iteminfo", itemInfoMap);
            resultDataMap.put("bldinfo", bldInfoListMap);
            resultDataMap.put("setlinfo", setlInfoMap);
            resultDataMap.put("payinfo", setlinfo);
            resultDataMap.put("icuinfo", icuInfoMapList);
            resultDataMap.put("opspdiseinfo", mapList);
            resultDataMap.put("oprninfo", infoRecordDTOList);
            resultDataMap.put("diseinfo", diseaMap);
            resultDataMap.put("setldetail", setldetail);
            resultDataMap.put("hifp_pay", MapUtils.get(setlinfo, "hifp_pay"));
            String json = JSONObject.toJSONString(resultDataMap);
            System.out.println("----======" + json);
            //新增质控信息
            DrgDipResultDTO dto = new DrgDipResultDTO();
            //清单 business_type 1
            dto.setVisitId(map.get("visitId").toString());
            dto.setHospCode(map.get("hospCode").toString());
            dto.setBusinessType("1");
            //DIP_DRG_MODE值
            Map<String, Object> sysMap = new HashMap<>();
            sysMap.put("hospCode", MapUtils.get(map, "hospCode"));
            sysMap.put("code", "DIP_DRG_MODEL");
            SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(sysMap).getData();
            if (ObjectUtil.isEmpty(sysParameterDTO)){
              resultDataMap.put("DIP_DRG_MODEL",null);
            }else{
              resultDataMap.put("DIP_DRG_MODEL",sysParameterDTO.getValue());
            }
            //返回给前端  提示是否有这个权限
            Map<String, Object> map2 = new HashMap<>();
            map2.put("hospCode", map.get("hospCode").toString());
            DrgDipAuthDTO drgDipAuthDTO = drgDipResultService.checkDrgDipBizAuthorization(map2).getData();
            HashMap map1 = new HashMap();
            map1.put("drgDipResultDTO",dto);
            map1.put("hospCode",map.get("hospCode").toString());
            map1.put("drgDipAuthDTO",drgDipAuthDTO);
            DrgDipComboDTO combo = drgDipResultService.getDrgDipInfoByParam(map1).getData();
            combo.setDip(drgDipAuthDTO.getDip());
            combo.setDrg(drgDipAuthDTO.getDrg());
            combo.setDipMsg(drgDipAuthDTO.getDipMsg());
            combo.setDrgMsg(drgDipAuthDTO.getDrgMsg());
            resultDataMap.put("drgInfo",combo);
        }
        resultDataMap.put("mrisType", map.get("mrisType"));
        return resultDataMap;
    }


    /**
     * @Method handerBaseSetlInfo
     * @Desrciption 处理医疗保障基金结算清单: 结算清单信息
     * @Param
     * @Author fuhui
     * @Date 2021/11/2 10:19
     * @Return
     **/
    private Map<String, Object> handerBaseSetlInfo(Map<String, Object> map, Map<String, Object> setlinfoMap) {
        Map<String, Object> setlinfo = new HashMap<>();
        Map<String, Object> baseInfoMap = handerBaseInfo(map);
        Map<String, Object> mriBaseInfo = handerMriBaseInfo(map);
        String otpTcmDise = "";
        String otpTcmDiseCode = "";
        List<InptDiagnoseDTO> tcmZyDiagnoseDTOS = insureGetInfoDAO.selectTcmSyndromesMriInptDiagNose(map);
        if (ObjectUtil.isNotEmpty(tcmZyDiagnoseDTOS) && tcmZyDiagnoseDTOS.size() > 0) {
            for (InptDiagnoseDTO diagnoseDTO : tcmZyDiagnoseDTOS) {
                if ("1".equals(diagnoseDTO.getIsMain())) {
                    otpTcmDise = diagnoseDTO.getDiseaseName();
                    otpTcmDiseCode = diagnoseDTO.getDiseaseCode();
                }
            }
        }

        if (MapUtils.isEmpty(baseInfoMap)) {
            throw new AppException("结算清单的基本信息数据为空，请先维护数据");
        }
//        if(MapUtils.isEmpty(mriBaseInfo)){
//            throw new AppException("病案首页的信息数据为空，请先维护数据");
//        }
        for (String key : setlinfoMap.keySet()) {
            if ("-".equals(MapUtils.get(setlinfoMap, key))) {
                setlinfoMap.put(key, "");
            }
        }
        for (String key : baseInfoMap.keySet()) {
            if ("-".equals(MapUtils.get(baseInfoMap, key))) {
                baseInfoMap.put(key, "");
            }
        }
        if (mriBaseInfo != null) {
            for (String key : mriBaseInfo.keySet()) {
                if ("-".equals(MapUtils.get(mriBaseInfo, key))) {
                    mriBaseInfo.put(key, "");
                }
            }
        }

        String hospName = MapUtils.get(map, "hospName");
        String hospCode = MapUtils.get(map, "hospCode");
        String visitId = MapUtils.get(map, "visitId");
        InsureIndividualVisitDTO insureIndividualVisitDTO = insureUnifiedCommonUtil.commonGetVisitInfo(map);
        String insureSettleId = MapUtils.get(map, "insureSettleId");
        String medicalRegNo = insureIndividualVisitDTO.getMedicalRegNo();
        String isHospital = insureIndividualVisitDTO.getIsHospital();
        map.put("isHospital", isHospital);
        setlinfo.put("medicalRegNo", medicalRegNo);
        setlinfo.put("visitId", visitId);
        setlinfo.put("mdtrtId", medicalRegNo); // 就医登记号
        setlinfo.put("insureSettleId", insureSettleId); // 结算ID
        setlinfo.put("setlId", MapUtils.get(map, "insureSettleId")); // 结算ID
        setlinfo.put("fixmedinsName", MapUtils.get(setlinfoMap, "fixmedins_name")); // 定点医药机构名称
        setlinfo.put("fixmedinsCode", insureIndividualVisitDTO.getMedicineOrgCode()); // 定点医药机构编号
        //判断是否为单病种住院
        if(Constant.UnifiedPay.YWLX.DBZZY.equals(insureIndividualVisitDTO.getAka130())){
            setlinfo.put("medType", insureIndividualVisitDTO.getAka130());//医疗类别
            setlinfo.put("bka006", insureIndividualVisitDTO.getBka006());//单病种编码
            setlinfo.put("bka006Name", insureIndividualVisitDTO.getBka006Name());//单病种名称
        }
        map.put("code", "SETTLELEVEL");
        SysParameterDTO data = insureGetInfoDAO.getParameterByCode(hospCode,"SETTLELEVEL");
        if (data == null) {
            throw new AppException("请先维护系统参数SETTLELEVEL" + "值为医院结算等级");
        }
        String value = data.getValue();
        String setlTitle = "";
        String settleLv = "";
        Map<String, Object> stringObjectMap = JSON.parseObject(value, Map.class);
        for (String key : stringObjectMap.keySet()) {
            if ("setlTitle".equals(key)) {
                setlTitle = MapUtils.get(stringObjectMap, key);
            }
//            if ("settleLv".equals(key)) { //
//                settleLv = MapUtils.get(stringObjectMap, key);
//            }
        }
        //医保结算等级取字典值
        Map dictMap = new HashMap(2);
        dictMap.put("hospCode", MapUtils.get(map, "hospCode"));
        dictMap.put("insureRegCode", insureIndividualVisitDTO.getInsuplcAdmdvs());
        dictMap.put("code", "HI_SETL_LV");
        Map<String, String> dictMap1 = insureDictService_consumer.queryDictByCode(dictMap).getData();
        if(dictMap != null ){
            for (Map.Entry<String,String> entry : dictMap1.entrySet()) {
                settleLv = entry.getKey();
                if (settleLv != null) {
                    break;
                }
            }
        }
        setlinfo.put("setlTitle", setlTitle);//清单抬头
        setlinfo.put("hiSetlLv", settleLv);//医保结算等级
        setlinfo.put("psnNo", insureIndividualVisitDTO.getAac001()); // 就诊ID
        setlinfo.put("hiNo", insureIndividualVisitDTO.getAac001()); //医保编号
        setlinfo.put("medcasno", MapUtils.get(baseInfoMap, "profileNo")); // 病案号
        setlinfo.put("dclaTime", DateUtils.getNow()); //结算清单申报时间
//        String settleNo = generatorOderNo(hospCode,visitId);
        setlinfo.put("settleNo", null);// 清单流水号
        setlinfo.put("psnName", insureIndividualVisitDTO.getAac003());// 人员姓名
        setlinfo.put("gend", insureIndividualVisitDTO.getAac004());// 性别
        setlinfo.put("brdy", insureIndividualVisitDTO.getAac006()); // 出生日期
        // 当年龄单位是天的时候
        String ageUnitCode = MapUtils.get(baseInfoMap, "age_unit_code");
        if (Constants.LNDW.S.equals(ageUnitCode)) {
            setlinfo.put("age", MapUtils.get(baseInfoMap, "age")); // 年龄
            setlinfo.put("nwbAge", null);
        } else {
            int age = MapUtils.get(baseInfoMap, "age");
            if (Constants.LNDW.Y.equals(ageUnitCode)) {
                setlinfo.put("nwbAge", age * 30); // （年龄不足1周岁）年龄
            } else if (Constants.LNDW.Z.equals(ageUnitCode)) {
                age = MapUtils.get(baseInfoMap, "age");
                setlinfo.put("nwbAge", age * 7);
            } else {
                setlinfo.put("nwbAge", age);
            }

            setlinfo.put("age", null); // 年龄
        }
        setlinfo.put("ntly", MapUtils.getMapVS(mriBaseInfo, "nationality_cation", MapUtils.get(baseInfoMap, "ntly"))); // 国籍
        // his的名族码表是0  1 2 3 医保码表是01 02 03
        Object mapVS = MapUtils.getMapVS(mriBaseInfo, "nation_code", MapUtils.get(baseInfoMap, "naty"));
        if (mapVS == null) {
            setlinfo.put("naty", ""); // 民族
        } else {
            setlinfo.put("naty", "0" + mapVS); // 民族
        }
        setlinfo.put("patnCertType", MapUtils.getMapVS(mriBaseInfo, "cert_code", (String) MapUtils.getMapVS(baseInfoMap, "cert_code", "02"))); // 患者证件类别
        setlinfo.put("certno", MapUtils.getMapVS(mriBaseInfo, "cert_no", (String) MapUtils.getMapVS(baseInfoMap, "cert_no", insureIndividualVisitDTO.getMdtrtCertNo()))); // 证件号码
        setlinfo.put("prfs", MapUtils.getMapVS(mriBaseInfo, "occupation_code", MapUtils.get(baseInfoMap, "occupation_code"))); // 职业
        setlinfo.put("currAddr", MapUtils.getMapVS(mriBaseInfo, "now_adress", MapUtils.get(baseInfoMap, "address"))); // 现住址
        //现住址四级地址
        setlinfo.put("province", MapUtils.getMapVS(mriBaseInfo, "province", MapUtils.get(baseInfoMap, "province"))); // 现住址-省
        setlinfo.put("city", MapUtils.getMapVS(mriBaseInfo, "city", MapUtils.get(baseInfoMap, "city"))); // 现住址-市
        setlinfo.put("county", MapUtils.getMapVS(mriBaseInfo, "county", MapUtils.get(baseInfoMap, "county"))); // 现住址-县
        setlinfo.put("detailAddress", MapUtils.getMapVS(mriBaseInfo, "detail_address", MapUtils.get(baseInfoMap, "detail_address"))); // 现住址-详细地址
        setlinfo.put("empName", MapUtils.getMapVS(mriBaseInfo, "work_info", null)); // 单位名称
        setlinfo.put("empAddr", MapUtils.getMapVS(mriBaseInfo, "work_info", null)); // 单位地址
        setlinfo.put("empTel", MapUtils.getMapVS(mriBaseInfo, "work_phone", null)); // 单位电话
        setlinfo.put("poscode", MapUtils.getMapVS(mriBaseInfo, "work_post_code", null)); // 邮编
        setlinfo.put("conerName", MapUtils.getMapVS(mriBaseInfo, "contact_name", MapUtils.get(baseInfoMap, "contact_name"))); // 联系人姓名
        Object contactRelaCode = MapUtils.getMapVS(mriBaseInfo, "contact_rela_code", MapUtils.get(baseInfoMap, "contact_rela_code"));
        if ("0".equals(contactRelaCode)) {
            contactRelaCode = "1";
        } else if ("1".equals(contactRelaCode)) {
            contactRelaCode = "10";
        } else if ("2".equals(contactRelaCode)) {
            contactRelaCode = "20";
        } else if ("3".equals(contactRelaCode)) {
            contactRelaCode = "30";
        } else if ("4".equals(contactRelaCode)) {
            contactRelaCode = "40";
        } else if ("5".equals(contactRelaCode)) {
            contactRelaCode = "50";
        } else if ("6".equals(contactRelaCode)) {
            contactRelaCode = "69";
        } else if ("7".equals(contactRelaCode)) {
            contactRelaCode = "70";
        } else if ("8".equals(contactRelaCode)) {
            contactRelaCode = "99";
        } else {
            contactRelaCode = "99";
        }
        setlinfo.put("patnRlts", contactRelaCode); // 与患者关系  默认患者本人
        setlinfo.put("conerAddr", MapUtils.getMapVS(mriBaseInfo, "contact_address", MapUtils.get(baseInfoMap, "contact_address"))); // 联系人地址
        //联系人地址四级地址
        setlinfo.put("conProvince", MapUtils.getMapVS(mriBaseInfo, "con_province", MapUtils.get(baseInfoMap, "con_province"))); // 联系人地址-省
        setlinfo.put("conCity", MapUtils.getMapVS(mriBaseInfo, "con_city", MapUtils.get(baseInfoMap, "con_city"))); // 联系人地址-市
        setlinfo.put("conCounty", MapUtils.getMapVS(mriBaseInfo, "con_county", MapUtils.get(baseInfoMap, "con_county"))); // 联系人地址-县
        setlinfo.put("conDetailAddress", MapUtils.getMapVS(mriBaseInfo, "con_detail_address", MapUtils.get(baseInfoMap, "con_detail_address"))); // 联系人地址-详细地址
        setlinfo.put("conerTel", MapUtils.getMapVS(mriBaseInfo, "contact_phone", MapUtils.get(baseInfoMap, "contact_phone"))); // 联系人电话
        setlinfo.put("hiType", insureIndividualVisitDTO.getAae140()); // 医保类型  也就是险种   sp_psn_type
        String insuplcAdmdvs = insureIndividualVisitDTO.getInsuplcAdmdvs();
        map.put("insuplcAdmdvs", insuplcAdmdvs);
        Map<String, Object> admdvsMap = insureGetInfoDAO.selectInsuplcAdmdvs(map);
        if (!MapUtils.isEmpty(admdvsMap)) {
            setlinfo.put("insuplcName", MapUtils.get(admdvsMap, "admdvsName")); // 参保地 *******
        }
        //参保地显示市加县
        String upInsuplcAdmdvs = null;
        if("00".equals(insuplcAdmdvs.substring(insuplcAdmdvs.length()-2,insuplcAdmdvs.length()))){
            if("00".equals(insuplcAdmdvs.substring(insuplcAdmdvs.length()-4,insuplcAdmdvs.length()-2))){
                upInsuplcAdmdvs = insuplcAdmdvs;
            }else {
                upInsuplcAdmdvs = insuplcAdmdvs.substring(0,insuplcAdmdvs.length()-4) + "0000";
            }
        }else {
            upInsuplcAdmdvs = insuplcAdmdvs.substring(0,insuplcAdmdvs.length()-2) + "00";
        }
        map.put("insuplcAdmdvs", upInsuplcAdmdvs);
        Map<String, Object> upAdmdvsMap = insureGetInfoDAO.selectInsuplcAdmdvs(map);
        if (!MapUtils.isEmpty(upAdmdvsMap)) {
            setlinfo.put("insuplcName", (String)MapUtils.get(upAdmdvsMap, "admdvsName") + setlinfo.get("insuplcName")); // 参保地 *******
        }
        setlinfo.put("insuplc", insureIndividualVisitDTO.getInsuplcAdmdvs()); // 参保地 *******
        map.put("insureIndividualVisitDTO", insureIndividualVisitDTO);
//        Map<String, Object> data1 = insureUnifiedPayReversalTradeService.checkOneSettle(map, insureIndividualVisitDTO).getData();
        setlinfo.put("spPsnType", ""); // 特殊人员类型
        setlinfo.put("nwbAdmType", MapUtils.get(baseInfoMap, "")); // 新生儿入院类型
        setlinfo.put("nwbBirWt", MapUtils.getMapVS(mriBaseInfo, "baby_birth_weight", null)); // 新生儿出生体重 *******
        setlinfo.put("nwbAdmWt", MapUtils.getMapVS(mriBaseInfo, "baby_in_weight", null)); // 新生儿入院体重 *******
        if (Constants.SF.F.equals(isHospital)) {
            setlinfo.put("opspMdtrtDate", insureIndividualVisitDTO.getVisitTime()); // 门诊慢特病就诊日期 *******
            setlinfo.put("opspDiagCaty", MapUtils.get(baseInfoMap, "inDeptNatinCode")); // 门诊慢特病诊断科别 *******
        } else {
            setlinfo.put("opspMdtrtDate", null); // 门诊慢特病就诊日期 *******
            setlinfo.put("opspDiagCaty", null); // 门诊慢特病诊断科别 *******
            setlinfo.put("admCaty", MapUtils.get(baseInfoMap, "inDeptNatinCode")); // 入院科别
        }
        setlinfo.put("iptMedType", "1"); // 住院医疗类型
        setlinfo.put("admWay", MapUtils.getMapVS(mriBaseInfo, "in_way", MapUtils.get(baseInfoMap, "adm_way"))); // 入院途径 *******
        setlinfo.put("trtType", "1"); // 治疗类别
        setlinfo.put("admTime", MapUtils.get(baseInfoMap, "inTime")); // 入院时间 *******

        String refldeptDept = selectRefldeptDept(map);
        setlinfo.put("refldeptDept", refldeptDept); // 转科科别
        setlinfo.put("dscgTime", MapUtils.get(baseInfoMap, "out_time")); // 出院时间 *******
        setlinfo.put("dscgCaty", MapUtils.get(baseInfoMap, "outDeptNatinCode")); // 出院科别
        setlinfo.put("actIptDays", insureIndividualVisitDTO.getHospitalDay()); // 实际住院天数 *******

        setlinfo.put("otpWmDise", MapUtils.getMapVS(mriBaseInfo, "diseaseName", MapUtils.get(baseInfoMap, "outpt_disease_name"))); // 门（急）诊诊断名称 *******
        setlinfo.put("otpWmDiseCode", MapUtils.getMapVS(mriBaseInfo, "diseaseCode", MapUtils.get(baseInfoMap, "outpt_disease_icd10"))); // 门（急）诊诊断编码 *******
        setlinfo.put("wmDiseCcode", ""); // 西医诊断疾病代码 *******
        setlinfo.put("otpTcmDise", otpTcmDise); // 门（急）诊中医诊断 *******
        setlinfo.put("otpTcmDiseCode", otpTcmDiseCode); // 门（急）诊中医诊断 *******
        setlinfo.put("tcmDiseCode", ""); // 中医诊断代码 *******
        setlinfo.put("diagCodeCnt", MapUtils.get(map, "diseaseCount")); // 诊断代码计数 *******
        setlinfo.put("oprnOprtCodeCnt", MapUtils.get(map, "operCount")); // 手术操作代码计数 *******
        //计数为0则返回空
        if(MapUtils.get(map, "diseaseCount")!= null && 0 == (int)MapUtils.get(map, "diseaseCount")){
            setlinfo.put("diagCodeCnt", null); // 诊断代码计数 *******
        }
        if(MapUtils.get(map, "operCount")!= null && 0 == (int)MapUtils.get(map, "operCount")){
            setlinfo.put("oprnOprtCodeCnt", null); // 诊断代码计数 *******
        }
        setlinfo.put("ventUsedDura", ""); // 呼吸机使用时长 *******
        Object inptBeforeDay = "";
        Object inptBeforeHour = "";
        Object inptBeforeMinute = "";
        Object inptLastDay = "";
        Object inptLastHour = "";
        Object inptLastMinute = "";
        inptBeforeDay = MapUtils.getMapVS(mriBaseInfo, "inpt_before_day", null);
        inptBeforeHour = MapUtils.getMapVS(mriBaseInfo, "inpt_before_hour", null);
        inptBeforeMinute = MapUtils.getMapVS(mriBaseInfo, "inpt_before_minute", null);
        inptLastDay = MapUtils.getMapVS(mriBaseInfo, "inpt_last_day", null);
        inptLastHour = MapUtils.getMapVS(mriBaseInfo, "inpt_last_hour", null);
        inptLastMinute = MapUtils.getMapVS(mriBaseInfo, "inpt_last_minute", null);
        setlinfo.put("inptBeforeDay", inptBeforeDay);
        setlinfo.put("inptBeforeHour", inptBeforeHour);
        setlinfo.put("inptBeforeMinute", inptBeforeMinute);
        setlinfo.put("inptLastDay", inptLastDay);
        setlinfo.put("inptLastHour", inptLastHour);
        setlinfo.put("inptLastMinute", inptLastMinute);
        setlinfo.put("pwcryBfadmComaDra", null); // 颅脑损伤患者入院前昏迷时长 *******
        setlinfo.put("pwcryAfadmComaDura", null); //颅脑损伤患者入院后昏迷时长
        setlinfo.put("bldCat", null); //输血品种
        setlinfo.put("bldAmt", null); //输血量
        setlinfo.put("bldUnt", null); //输血计量单位

        setlinfo.put("spgaNurscareDays", MapUtils.get(baseInfoMap, "spgaNurscareDays")); // 特级护理天数 *******
        setlinfo.put("lv1NurscareDays", MapUtils.get(baseInfoMap, "lv1NurscareDays")); // 一级护理天数 *******
        setlinfo.put("scdNurscareDays", MapUtils.get(baseInfoMap, "scdNurscareDays")); // 二级护理天数 *******
        setlinfo.put("lv3NurscareDays", MapUtils.get(baseInfoMap, "lv3NursecareDays")); // 三级护理天数 *******
        //计数为0则返回空
        if(MapUtils.get(setlinfo, "spgaNurscareDays")!= null && "0".equals(MapUtil.getStr(setlinfo, "spgaNurscareDays"))){
            setlinfo.put("spgaNurscareDays", null); // 特级护理天数 *******
        }
        if(MapUtils.get(setlinfo, "lv1NurscareDays")!= null && "0".equals(MapUtil.getStr(setlinfo, "lv1NurscareDays"))){
            setlinfo.put("lv1NurscareDays", null); // 一级护理天数 *******
        }
        if(MapUtils.get(setlinfo, "scdNurscareDays")!= null && "0".equals(MapUtil.getStr(setlinfo, "scdNurscareDays"))){
            setlinfo.put("scdNurscareDays", null); // 二级护理天数 *******
        }
        if(MapUtils.get(setlinfo, "lv3NurscareDays")!= null && "0".equals(MapUtil.getStr(setlinfo, "lv3NurscareDays"))){
            setlinfo.put("lv3NurscareDays", null); // 三级护理天数 *******
        }

        setlinfo.put("dscgWay", MapUtils.get(baseInfoMap, "out_mode_code")); // 离院方式 *******
        setlinfo.put("acpMedinsName", ""); // 拟接收机构名称 *******
        setlinfo.put("acpMedinsCode", ""); // 拟接收机构代码 ******
        Map<String, Object> billNoMap = generatorBillNo(map);
        setlinfo.put("billCode", MapUtils.get(billNoMap, "billCode")); // 票据代码**********
        setlinfo.put("billNo", MapUtils.get(billNoMap, "billNo")); // 票据号码**********
        if (StringUtils.isEmpty(insureIndividualVisitDTO.getOmsgid())) {
            setlinfo.put("bizSn", StringUtils.createMsgId(insureIndividualVisitDTO.getMedicineOrgCode())); // 业务流水号
        } else {
            setlinfo.put("bizSn", MapUtils.get(setlinfoMap, "setl_id")); // 业务流水号(取结算id)
        }
        setlinfo.put("daysRinpFlag31", MapUtils.getMapVS(mriBaseInfo, "is_inpt", "")); // 是否有出院31天再住院计划 *******
        setlinfo.put("daysRinpPup31", MapUtils.getMapVS(mriBaseInfo, "aim", "")); // 出院31天内再住院目的 *******
        // 更换主治医生和责任医生的数据源 从inpt_visit表 切换至 mris_base_info表
        setlinfo.put("chfpdrName", MapUtils.getMapVS(mriBaseInfo,"zzDoctor_name",null)); // 主诊医生姓名 *******
        setlinfo.put("chfpdrCode", MapUtils.getMapVS(mriBaseInfo,"zz_doctor_code",null)); // 主诊医生代码 *******
        setlinfo.put("zrNurseName", MapUtils.getMapVS(mriBaseInfo,"zrNurse_name",null)); // 责任护士名 *******
        setlinfo.put("zrNurseCode", MapUtils.getMapVS(mriBaseInfo,"zr_nurse_code",null)); // 责任护士代码 *******
        //根据参数判断主治医师是否取住院医师
        SysParameterDTO sysParameterDTO = insureGetInfoDAO.getParameterByCode(hospCode,"ZZ_OR_ZG");
        if(sysParameterDTO != null && Constant.UnifiedPay.ISMAN.S.equals(sysParameterDTO.getValue())){
            setlinfo.put("chfpdrName", MapUtils.getMapVS(mriBaseInfo,"zg_doctor_name",null)); // 主诊医生姓名 *******
            setlinfo.put("chfpdrCode", MapUtils.getMapVS(mriBaseInfo,"zg_doctor_code",null)); // 主诊医生代码 *******
        }
        Object setlBegnDate = MapUtils.get(setlinfoMap, "begndate");
        if (ObjectUtil.isEmpty(setlBegnDate)) {
            throw new AppException("结算开始时间为空");
        } else {
            setlinfo.put("setlBegnDate", DateUtils.parse((String) setlBegnDate, DateUtils.Y_M_D)); //结算开始日期 *******
        }
        Object setlEndDate = MapUtils.get(setlinfoMap, "enddate");
        if (ObjectUtil.isEmpty(setlEndDate)) {
            throw new AppException("结算结束时间为空");
        } else {
            if (ObjectUtil.isNotEmpty(setlEndDate)){
              setlinfo.put("setlEndDate", DateUtils.parse((String) setlEndDate, DateUtils.Y_M_D)); // 结算结束日期 *******
            }else{
              setlinfo.put("setlEndDate", null);
            }
        }
        // 全自费金额
        String fulamtOwnpayAmt = DataTypeUtils.dataToNumString(MapUtils.get(setlinfoMap, "fulamt_ownpay_amt"));
        // 超限价自费金额
        String overlmtSelfpay = DataTypeUtils.dataToNumString(MapUtils.get(setlinfoMap, "overlmt_selfpay"));

        String acctPay = DataTypeUtils.dataToNumString(MapUtils.get(setlinfoMap, "acct_pay"));
        String cashPayamt = DataTypeUtils.dataToNumString(MapUtils.get(setlinfoMap, "cash_payamt"));
        /**
         * 2021/12/09 个人自费 =全自费金额（fulamtOwnpayAmt）  不包含超限价自费金额
         */
        // 个人自费 == 全自费金额（fulamtOwnpayAmt） +超限价自费金额（overlmtSelfpay）
        BigDecimal psnOwnpay = BigDecimalUtils.convert(fulamtOwnpayAmt);
        /**
         *     金额合计=医保统筹基金支付+补充医疗保险支付+医疗救助支付+个人负担
         *     补充医疗保险支付：职工大额补助+居民大病保险+公务员医疗补助
         *     medfeeAumamt:总金额
         *     hifobPay:职工大额医疗费用补助基金支出
         *     hifmiPay:居民大病保险资金支出
         *     cvlserv_pay:公务员医疗补助资金支出
         *     psnPartAmt: 个人负担总金额
         *     hifpPayStr:基本医疗保险统筹基金支出
         *
         */
        String medfeeAumamtStr = DataTypeUtils.dataToNumString(MapUtils.get(setlinfoMap, "medfee_sumamt"));
        String hifpPayStr = DataTypeUtils.dataToNumString(MapUtils.get(setlinfoMap, "hifp_pay"));
        String hifobPayStr = DataTypeUtils.dataToNumString(MapUtils.get(setlinfoMap, "hifob_pay"));
        String hifmiPayStr = DataTypeUtils.dataToNumString(MapUtils.get(setlinfoMap, "hifmi_pay"));
        String cvlservPayStr = DataTypeUtils.dataToNumString(MapUtils.get(setlinfoMap, "cvlserv_pay"));
        String mafPayStr = DataTypeUtils.dataToNumString(MapUtils.get(setlinfoMap, "maf_pay"));
        BigDecimal medfeeAumamt = BigDecimalUtils.convert(medfeeAumamtStr);
        BigDecimal hifobPay = BigDecimalUtils.convert(hifobPayStr);
        BigDecimal hifmiPay = BigDecimalUtils.convert(hifmiPayStr);
        BigDecimal cvlservPay = BigDecimalUtils.convert(cvlservPayStr);
        BigDecimal mafPay = BigDecimalUtils.convert(mafPayStr);
        BigDecimal hifpPay = BigDecimalUtils.convert(hifpPayStr);
        BigDecimal psnPartAmt = BigDecimalUtils.subtractMany(medfeeAumamt, hifobPay, hifmiPay, cvlservPay, mafPay, hifpPay);
        setlinfo.put("psnOwnpay", psnOwnpay); // 个人自费
        setlinfo.put("acctPay", BigDecimalUtils.convert(acctPay)); // 个人账户支出
        setlinfo.put("psnCashpay", BigDecimalUtils.convert(cashPayamt)); // 个人现金支付
        BigDecimal bigDecimal = BigDecimalUtils.add(acctPay, cashPayamt);
        // 个人自付 = 个人负担 - 个人自费
        BigDecimal psnSelfpay = BigDecimalUtils.subtract(psnPartAmt, psnOwnpay);
        setlinfo.put("psnSelfpay", psnSelfpay); // 个人自付
        setlinfo.put("hiPaymtd", "3"); // 医保支付方式  // 默认是按病种分值
        setlinfo.put("hsorg", ""); // 医保机构经办人
        setlinfo.put("hsorgOpter", ""); // 医保机构经办人
        setlinfo.put("medinsFillDept", "医保科"); // 医疗机构填报部门 默认为 医保科
        setlinfo.put("stasType", "0"); // 状态分类 默认为 未提交
        setlinfo.put("medinsFillPsn", MapUtils.get(baseInfoMap, "feeCrteTime")); // 医疗机构填报人
        //门特患者信息处理，没有病案首页，取档案信息
        if (Constants.SF.F.equals(isHospital)) {
            setlinfo.put("hiPaymtd", "1"); // 医保支付方式   门特默认是按项目
            setlinfo.put("admTime", insureIndividualVisitDTO.getVisitTime()); // 入院时间 *******
            setlinfo.put("dscgTime", insureIndividualVisitDTO.getVisitTime()); // 出院时间 *******
            setlinfo.put("admCaty", MapUtils.get(baseInfoMap, "inDeptNatinCode")); // 入院科别
            setlinfo.put("dscgCaty", MapUtils.get(baseInfoMap, "inDeptNatinCode")); // 出院科别
            //查询档案信息
            Map<String, Object> profileMap = new HashMap<>();
            profileMap.put("hospCode",hospCode);
            profileMap.put("certNo",MapUtils.get(baseInfoMap,"cert_no"));
            OutptProfileFileDTO outptProfileFileDTO = insureGetInfoDAO.queryProfileFile(profileMap);
            if(outptProfileFileDTO != null){
                profileMap = JSONObject.parseObject(JSON.toJSONString(outptProfileFileDTO));
            }
            setlinfo.put("ntly",MapUtils.getMapVS(profileMap, "nationalityCation", (String) MapUtils.getMapVS(baseInfoMap, "ntly", "141"))); // 国籍,默认中国
            Object naty =  MapUtils.getMapVS(profileMap, "nationCode", (String) MapUtils.getMapVS(baseInfoMap, "nation_code", "1"));
            if (naty == null) {
                setlinfo.put("naty", ""); // 民族
            } else {
                setlinfo.put("naty", "0" + naty); // 民族
            }
            setlinfo.put("patnCertType", MapUtils.getMapVS(profileMap, "certCode", (String) MapUtils.getMapVS(baseInfoMap, "cert_code", "01"))); // 患者证件类别
            setlinfo.put("certno", MapUtils.getMapVS(profileMap, "certNo", (String) MapUtils.getMapVS(baseInfoMap, "cert_no", insureIndividualVisitDTO.getMdtrtCertNo()))); // 证件号码
            setlinfo.put("prfs", MapUtils.getMapVS(profileMap, "occupationCode", MapUtils.get(baseInfoMap, "occupation_code"))); // 职业
            setlinfo.put("currAddr", MapUtils.getMapVS(profileMap, "nowAddress", MapUtils.get(baseInfoMap, "now_address"))); // 现住址
            setlinfo.put("empName", MapUtils.getMapVS(profileMap, "work", null)); // 单位名称
            setlinfo.put("empAddr", MapUtils.getMapVS(profileMap, "workAddress", null)); // 单位地址
            setlinfo.put("empTel", MapUtils.getMapVS(profileMap, "workPhone", null)); // 单位电话
            setlinfo.put("poscode", MapUtils.getMapVS(profileMap, "workPostCode", null)); // 邮编
            setlinfo.put("conerName", MapUtils.getMapVS(profileMap, "contactName", MapUtils.get(baseInfoMap, "contact_name"))); // 联系人姓名
            Object contactRelaCode1 = MapUtils.getMapVS(profileMap, "contactRelaCode", MapUtils.get(baseInfoMap, "contact_rela_code"));
            if ("0".equals(contactRelaCode1)) {
                contactRelaCode1 = "1";
            } else if ("1".equals(contactRelaCode1)) {
                contactRelaCode1 = "10";
            } else if ("2".equals(contactRelaCode1)) {
                contactRelaCode1 = "20";
            } else if ("3".equals(contactRelaCode1)) {
                contactRelaCode1 = "30";
            } else if ("4".equals(contactRelaCode1)) {
                contactRelaCode1 = "40";
            } else if ("5".equals(contactRelaCode1)) {
                contactRelaCode1 = "50";
            } else if ("6".equals(contactRelaCode1)) {
                contactRelaCode1 = "69";
            } else if ("7".equals(contactRelaCode1)) {
                contactRelaCode1 = "70";
            } else if ("8".equals(contactRelaCode1)) {
                contactRelaCode1 = "99";
            } else {
                contactRelaCode1 = "99";
            }
            setlinfo.put("patnRlts", contactRelaCode1); // 与患者关系  默认患者本人
            setlinfo.put("conerAddr", MapUtils.getMapVS(profileMap, "contactAddress", MapUtils.get(baseInfoMap, "contact_address"))); // 联系人地址
            setlinfo.put("conerTel", MapUtils.getMapVS(profileMap, "contactPhone", MapUtils.get(baseInfoMap, "contact_phone"))); // 联系人电话
        }
        return setlinfo;
    }

    /**
     * @Method selectRefldeptDept
     * @Desrciption 查询患者的转院科室
     * @Param
     * @Author fuhui
     * @Date 2021/11/8 14:21
     * @Return
     **/
    private String selectRefldeptDept(Map<String, Object> map) {
        return insureGetInfoDAO.selectRefldeptDept(map);
    }

    /**
     * @Method handerMriBaseInfo
     * @Desrciption 当是住院病人时  患者基础信息可以冲病案首页加载
     * @Param
     * @Author fuhui
     * @Date 2021/11/5 10:12
     * @Return
     **/
    private Map<String, Object> handerMriBaseInfo(Map<String, Object> map) {
        Map<String, Object> baseInfo = insureGetInfoDAO.selectMriBaseInfo(map);
        if (ObjectUtil.isEmpty(baseInfo)) {
            baseInfo = insureGetInfoDAO.selectTcmMriBaseInfo(map);
        }
        return  baseInfo;

    }

    /**
     * @Method getDateToString
     * @Desrciption 时间戳格式转标准字符串格式
     * @Param
     * @Author fuhui
     * @Date 2021/12/4 22:12
     * @Return
     **/
    public String getDateToString(java.sql.Timestamp time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.Y_M_D);
        return simpleDateFormat.format(time);
    }

    /**
     * @Method generatorBillNo
     * @Desrciption 生成票据号码
     * @Param
     * @Author fuhui
     * @Date 2021/11/3 20:02
     * @Return
     **/
    private Map<String, Object> generatorBillNo(Map<String, Object> map) {
        String isHospital = MapUtils.get(map, "isHospital");
        String billNo = "";
        String billCode = "";
        Map<String, Object> dataMap;
        if (Constants.SF.S.equals(isHospital)) {
            dataMap = insureGetInfoDAO.selectInptBillNo(map);
        } else {
            dataMap = insureGetInfoDAO.selectOutptBillNo(map);
        }

        Map sysMap = new HashMap();
        sysMap.put("hospCode",MapUtils.get(map, "hospCode"));
        sysMap.put("code","IS_JUGE_INVOICE");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(sysMap).getData();
        // 如果该参数是1 则提醒该错误。如果是 0 则不提醒该错误
        if(sysParameterDTO == null || Constants.SF.S.equals(sysParameterDTO.getValue())){
            if (MapUtils.isEmpty(dataMap)) {
                throw new AppException("该患者未产生发票信息");
            }
        }
        //如果允许不开发票先存空
        if (MapUtils.isEmpty(dataMap)) {
            map.put("billNo", "");
            map.put("billCode", "");
        }else {
            billNo = MapUtils.get(dataMap, "invoice_no");
            billCode = MapUtils.get(dataMap, "prefix");
            map.put("billNo", billNo);
            map.put("billCode", billCode);
        }
        return map;
    }

    /**
     * @Method
     * @Desrciption 查询结算清单的基本信息数据
     * @Param
     * @Author fuhui
     * @Date 2021/11/2 11:40
     * @Return
     **/
    private Map<String, Object> handerBaseInfo(Map<String, Object> map) {
        String isHospital = MapUtils.get(map, "isHospital");
        if (Constants.SF.S.equals(isHospital)) {
            map = insureGetInfoDAO.getInptPatientInfo(map);
        } else {
            map = insureGetInfoDAO.getOutptPatientInfo(map);
        }
        return map;
    }

    /**
     * @Method handerIcuInfo
     * @Desrciption 处理医疗保障基金结算清单 : 重症监护信息节点
     * @Param
     * @Author fuhui
     * @Date 2021/11/2 9:57
     * @Return
     **/
    private List<Map<String, Object>> handerIcuInfo(Map<String, Object> map) {

        List<Map<String, Object>> icuMapList = insureGetInfoDAO.selectIcuInfo(map);
        if (ListUtils.isEmpty(icuMapList)) {
//            // 重症监护信息
//            icuinfo = new HashMap();
//            icuinfo.put("scsCutdWardType",""); // 重症监护病房类型
//            icuinfo.put("scsSutdInpoolTime","");// 重症监护进入时间
//            icuinfo.put("scs_cutd_exit_time",""); // 重症监护退出时间
//            icuinfo.put("scs_cutd_sum_dura",""); // 重症监护合计时长
//            icuinfoListMap.add(icuinfo);
        }
        return icuMapList;
    }

    /**
     * @Method handerOperInfo
     * @Desrciption 处理医疗保障基金结算清单 : 手术操作信息节点
     * @Param 1.先查病案首页的手术信息 没有没有查询到 则查
     * @Author fuhui
     * @Date 2021/11/2 9:29
     * @Return
     **/
    private List<OperInfoRecordDTO> handerOperInfo(Map<String, Object> map) {
        List <OperInfoRecordDTO> infoRecordDTOList = new ArrayList<>();
        //先查询是否有从医嘱开的手术
        infoRecordDTOList = insureGetInfoDAO.selectMriOperInfoAdvice(map);
        //再查询病案手术
        infoRecordDTOList.addAll(insureGetInfoDAO.selectMriOperInfo(map));
        //西医病案首页手术信息没取到则取中医病案首页
        if (ObjectUtil.isEmpty(infoRecordDTOList)) {
            infoRecordDTOList = insureGetInfoDAO.selectTcmMriOperInfo(map);
        }
        if(!ListUtils.isEmpty(infoRecordDTOList)){
            infoRecordDTOList.get(0).setOprnOprtType("1");
        }
        return infoRecordDTOList;
    }

    /**
     * @Method handerOutDiagnose
     * @Desrciption 处理医疗保障基金结算清单：住院诊断节点信息
     * 1.因为清单上面需要的诊断是出院诊断(从病案首页获取，由于病案首页的无中医出院诊断类型)
     * his的码表入院病案码表  0：代表情况无   医保码表 4：代表无
     * @Param
     * @Author fuhui
     * @Date 2021/11/2 9:03
     * @Return
     **/
    private Map<String, Object> handerInptDiagnose(Map<String, Object> map) {
        Map<String, Object> diseaseMap = new HashMap<>();
        List<InptDiagnoseDTO> inptDiagnoseDTOList = insureGetInfoDAO.selectMriInptDiagNose(map);
        List<InptDiagnoseDTO> tcmXyDiagnoseDTOS = new ArrayList<>();//中医病案的西医诊断信息
        List<InptDiagnoseDTO> tcmZyDiagnoseDTOS = new ArrayList<>();//中医病案的中医诊断信息
        if (ObjectUtil.isEmpty(inptDiagnoseDTOList)) {
            tcmXyDiagnoseDTOS = insureGetInfoDAO.selectTcmMriInptDiagNose(map);//中医病案的西医诊断信息
            tcmZyDiagnoseDTOS = insureGetInfoDAO.selectTcmSyndromesMriInptDiagNose(map);
        }
        if (ObjectUtil.isEmpty(inptDiagnoseDTOList) && ObjectUtil.isEmpty(tcmXyDiagnoseDTOS) && ObjectUtil.isEmpty(tcmZyDiagnoseDTOS)) {
            throw new AppException("未获取到诊断信息，请检查！");
        }
        //判断是否有未匹配的数据
        if (!ObjectUtil.isEmpty(inptDiagnoseDTOList)) {
            String code = "";
            for(InptDiagnoseDTO inptDiagnoseDTO:inptDiagnoseDTOList){
                if(StringUtils.isEmpty(inptDiagnoseDTO.getDiseaseCode())){
                    if(StringUtils.isEmpty(code)){
                        code = inptDiagnoseDTO.getIcd10();
                    }else {
                        code = code + "、"+ inptDiagnoseDTO.getIcd10();
                    }
                }
            }
            if(StringUtils.isNotEmpty(code)){
                throw new AppException("存在以下未匹配的疾病编码:"+code+",请检查");
            }
        }
        List<InptDiagnoseDTO> zxCollect = new ArrayList<>();
        List<InptDiagnoseDTO> xiCollect = new ArrayList<>();
        Integer diseaseCount = 0;
        if (!ListUtils.isEmpty(inptDiagnoseDTOList)) {
            inptDiagnoseDTOList.stream().forEach(inptDiagnoseDTO -> {
                if ("0".equals(inptDiagnoseDTO.getAdmCondType())) {
                    inptDiagnoseDTO.setAdmCondType("4");
                }
            });
            xiCollect = inptDiagnoseDTOList;
            if (!ListUtils.isEmpty(xiCollect)) {
                xiCollect.stream().forEach(inptDiagnoseDTO -> {
                    //2022-06-28 zhangjinping 西医病案诊断信息表中出现了中医病名，归纳到对应的中医和西医诊断信息中
                    if(!Constants.JBFL.ZYBM.equals(inptDiagnoseDTO.getTypeCode())&&!Constants.JBFL.ZYHZZD.equals(inptDiagnoseDTO.getTypeCode())&&!Constants.JBFL.ZYZH.equals(inptDiagnoseDTO.getTypeCode())){
                        inptDiagnoseDTO.setTypeCode("1");
                    }

                    if(Constants.JBFL.ZYBM.equals(inptDiagnoseDTO.getTypeCode())||Constants.JBFL.ZYHZZD.equals(inptDiagnoseDTO.getTypeCode())||Constants.JBFL.ZYZH.equals(inptDiagnoseDTO.getTypeCode())){
                        inptDiagnoseDTO.setTypeCode("2");
                    }

                });
                List<InptDiagnoseDTO> zyDiagnoseList = xiCollect.stream().filter(dto->Constants.JBFL.ZLXT.equals(dto.getTypeCode())).collect(Collectors.toList());
                if(!ListUtils.isEmpty(zyDiagnoseList)){
                    zxCollect = zyDiagnoseList;
                }
            }
            diseaseCount = inptDiagnoseDTOList.size();
            map.put("mrisType",Constant.UnifiedPay.MRISTYPE.XY);
        }else {
            if (ObjectUtil.isNotEmpty(tcmZyDiagnoseDTOS)) {
                tcmZyDiagnoseDTOS.stream().forEach(diag ->{
                    diag.setTypeCode("2");
                    //中医病案的中医症候是存在TcmSyndromesId和TcmSyndromesName字段的，
                    //这里取出来赋值给DiseaseCode和DiseaseName
                    if (Constant.UnifiedPay.ISMAN.F.equals(diag.getIsMain())) {
                        diag.setDiseaseCode(diag.getTcmSyndromesId());
                        diag.setDiseaseName(diag.getTcmSyndromesName());
                    }
                });
                zxCollect = tcmZyDiagnoseDTOS;
            }
            if (ObjectUtil.isNotEmpty(tcmXyDiagnoseDTOS)) {
                xiCollect = tcmXyDiagnoseDTOS;
                if (!ListUtils.isEmpty(xiCollect)) {
                    xiCollect.stream().forEach(inptDiagnoseDTO -> {
                        inptDiagnoseDTO.setTypeCode("1");
                    });
                }
            }
            diseaseCount = zxCollect.size()+xiCollect.size();
            map.put("mrisType",Constant.UnifiedPay.MRISTYPE.ZY);
        }
        //排序
        List<InptDiagnoseDTO> zxCollect1 = new ArrayList<>();
        List<InptDiagnoseDTO> zxCollect2 = new ArrayList<>();
//        List<InptDiagnoseDTO> xiCollect1 = new ArrayList<>();
//        List<InptDiagnoseDTO> xiCollect2 = new ArrayList<>();
//        if(ObjectUtil.isNotEmpty(xiCollect)){
//            for(InptDiagnoseDTO inptDiagnoseDTO:xiCollect){
//                if(StringUtils.isEmpty(inptDiagnoseDTO.getIsMain())){
//                    inptDiagnoseDTO.setIsMain("0");
//                }
//            }
//            //主诊断排第一位
//            for(int i = 0;i < xiCollect.size(); i++){
//                if(Constant.UnifiedPay.ISMAN.S.equals(xiCollect.get(i).getIsMain())){
//                    xiCollect2.add(xiCollect.get(i));
//                    xiCollect.remove(i);
//                }
//            }
//            //根据id排序
//            xiCollect1 = ListUtils.isEmpty(xiCollect) ? null :
//                    xiCollect.stream().sorted((a, b) ->
//                            (b.getId() == null ? "" : b.getId())
//                                    .compareTo(a.getId()))
//                            .collect(Collectors.toList());
//            if(ObjectUtil.isNotEmpty(xiCollect1)){
//                xiCollect2.addAll(xiCollect1);
//            }
//        }
        if(ObjectUtil.isNotEmpty(zxCollect)){
            for(InptDiagnoseDTO inptDiagnoseDTO:zxCollect){
                if(StringUtils.isEmpty(inptDiagnoseDTO.getIsMain())){
                    inptDiagnoseDTO.setIsMain("0");
                }
            }
            //主诊断排第一位
            for(int i = 0;i < zxCollect.size(); i++){
                if(Constant.UnifiedPay.ISMAN.S.equals(zxCollect.get(i).getIsMain())){
                    zxCollect2.add(zxCollect.get(i));
                    zxCollect.remove(i);
                }
            }
            //根据id排序
            zxCollect1 = ListUtils.isEmpty(zxCollect) ? null :
                    zxCollect.stream().sorted((a, b) ->
                            (b.getId() == null ? "" : b.getId())
                                    .compareTo(a.getId()))
                            .collect(Collectors.toList());
            if(ObjectUtil.isNotEmpty(zxCollect1)){
                zxCollect2.addAll(zxCollect1);
            }
        }
        diseaseMap.put("xiCollect",xiCollect);
        diseaseMap.put("zxCollect",zxCollect2);
        diseaseMap.put("diseaseCount",diseaseCount);
        return diseaseMap;
    }

    /**
     * @Method handerOutDiagnose
     * @Desrciption 处理医疗保障基金结算清单：门诊慢特病诊断节点信息
     * @Param
     * @Author fuhui
     * @Date 2021/11/2 9:03
     * @Return
     **/
    private List<Map<String, Object>> insertOutDiagnose(Map<String, Object> map) {

        String hospCode = MapUtils.get(map, "hospCode");
        String visitId = MapUtils.get(map, "visitId");
        String medicalRegNo = MapUtils.get(map, "medicalRegNo");
        String insureSettleId = MapUtils.get(map, "insureSettleId");
        InsureIndividualVisitDTO insureIndividualVisitDTO = insureGetInfoDAO.selectInsureIndividual(map);
        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, Object> diagNoseMap;
        //查询门特手术信息
        List <OperInfoRecordDTO> infoRecordDTOList = new ArrayList<>();
        infoRecordDTOList = insureGetInfoDAO.selectOperInfoAdvice(map);
        if (insureIndividualVisitDTO != null && !ListUtils.isEmpty(infoRecordDTOList)) {
            for(OperInfoRecordDTO operInfoRecordDTO:infoRecordDTOList){
                diagNoseMap = new HashMap<>();
                diagNoseMap.put("id", SnowflakeUtils.getId());
                diagNoseMap.put("hospCode", hospCode);
                diagNoseMap.put("visitId", visitId);
                diagNoseMap.put("medicalRegNo", medicalRegNo);
                diagNoseMap.put("insureSettleId", insureSettleId);
                if (StringUtils.isEmpty(insureIndividualVisitDTO.getBka006())) {
                    diagNoseMap.put("diagCode", insureIndividualVisitDTO.getVisitIcdCode());
                } else {
                    diagNoseMap.put("diagCode", insureIndividualVisitDTO.getBka006());
                }
                if (StringUtils.isEmpty(insureIndividualVisitDTO.getBka006Name())) {
                    diagNoseMap.put("diagName", insureIndividualVisitDTO.getVisitIcdName());
                } else {
                    diagNoseMap.put("diagName", insureIndividualVisitDTO.getBka006Name());
                }
                diagNoseMap.put("oprnOprtName",operInfoRecordDTO.getOprnOprtName());
                diagNoseMap.put("oprnOprtCode", operInfoRecordDTO.getOprnOprtCode());
                mapList.add(diagNoseMap);
            }
        }
        if(ListUtils.isEmpty(mapList)){
            if (insureIndividualVisitDTO != null) {
                diagNoseMap = new HashMap<>();
                diagNoseMap.put("id", SnowflakeUtils.getId());
                diagNoseMap.put("hospCode", hospCode);
                diagNoseMap.put("visitId", visitId);
                diagNoseMap.put("medicalRegNo", medicalRegNo);
                diagNoseMap.put("insureSettleId", insureSettleId);
                if (StringUtils.isEmpty(insureIndividualVisitDTO.getBka006())) {
                    diagNoseMap.put("diagCode", insureIndividualVisitDTO.getVisitIcdCode());
                } else {
                    diagNoseMap.put("diagCode", insureIndividualVisitDTO.getBka006());
                }
                if (StringUtils.isEmpty(insureIndividualVisitDTO.getBka006Name())) {
                    diagNoseMap.put("diagName", insureIndividualVisitDTO.getVisitIcdName());
                } else {
                    diagNoseMap.put("diagName", insureIndividualVisitDTO.getBka006Name());
                }
                diagNoseMap.put("oprnOprtName", "");
                diagNoseMap.put("oprnOprtCode", "");
                mapList.add(diagNoseMap);
            }
        }
        return mapList;
    }

    /**
     * @Method insertPayInfo
     * @Desrciption 调用结算信息查询接口  -- 处理基金支付信息
     * @Param map
     * @Author fuhui
     * @Date 2021/11/2 8:46
     * @Return
     **/
    private Map<String, Object> insertPayInfo(Map<String, Object> map) {
        Map<String, Object> data = insureUnifiedBaseService.querySettleDeInfo(map).getData();
        List<Map<String, Object>> setldetail = MapUtils.get(data, "setldetail");
        List<Map<String, Object>> mapList = insertCommonSettleInfo(map, setldetail);
        if (!ListUtils.isEmpty(mapList)) {
            insureGetInfoDAO.deletePayInfo(map);
            insureGetInfoDAO.insertPayInfo(mapList);
        }
        return data;
    }

    /**
     * @Method insertIcuInfo
     * @Desrciption 处理医疗保障基金结算清单 -- 重症监护信息
     * @Param
     * @Author fuhui
     * @Date 2021/11/2 16:13
     * @Return
     **/
    private void insertIcuInfo(Map<String, Object> map) {

        List<Map<String, Object>> icuinfoList = MapUtils.get(map, "icuinfo");
        if (!ListUtils.isEmpty(icuinfoList)) {
            icuinfoList = icuinfoList.stream().filter(item -> StringUtils.isNotEmpty(MapUtils.get(item, "scsCutdWardType"))).collect(Collectors.toList());
            insureGetInfoDAO.deleteIcuInfo(map);
            icuinfoList.stream().forEach(item -> {
                Object scsCutdInpoolTime = MapUtils.get(item, "scsCutdInpoolTime");
                if (scsCutdInpoolTime != null) {
                    item.put("scsCutdInpoolTime", DateUtils.getNumerToStringDate(scsCutdInpoolTime));
                }
                Object scsCutdExitTime = MapUtils.get(item, "scsCutdExitTime");
                if (scsCutdExitTime != null) {
                    item.put("scsCutdExitTime", DateUtils.getNumerToStringDate(scsCutdExitTime));
                }
            });
            List<Map<String, Object>> mapList = insertCommonSettleInfo(map, icuinfoList);
            if (!ListUtils.isEmpty(mapList)) {
                insureGetInfoDAO.deleteIcuInfo(map);
                insureGetInfoDAO.insertIcuInfo(mapList);
            }
        }
    }

    /**
     * @Method insertCommonSettleInfo
     * @Desrciption 公共保存医疗结算清单的方法
     * @Param mapList 节点信息    map: 结算就诊信息
     * @Author fuhui
     * @Date 2021/11/2 17:23
     * @Return
     **/
    private List<Map<String, Object>> insertCommonSettleInfo(Map<String, Object> map, List<Map<String, Object>> mapList) {
        String hospCode = MapUtils.get(map, "hospCode");
        String visitId = MapUtils.get(map, "visitId");
        String medicalRegNo = MapUtils.get(map, "medicalRegNo");
        String insureSettleId = MapUtils.get(map, "insureSettleId");
        if (!ListUtils.isEmpty(mapList)) {
            mapList.stream().forEach(item -> {
                item.put("id", SnowflakeUtils.getId());
                item.put("hospCode", hospCode);
                item.put("medicalRegNo", medicalRegNo);
                item.put("visitId", visitId);
                item.put("insureSettleId", insureSettleId);
            });
        }
        return mapList;
    }

    /**
     * @Method insertOperInfo
     * @Desrciption 处理医疗保障基金结算清单 -- 保存手术信息
     * @Param map
     * @Author fuhui
     * @Date 2021/11/2 16:04
     * @Return
     **/
    private synchronized void insertOperInfo(Map<String, Object> map) {

        List<Map<String, Object>> opernInfoList = MapUtils.get(map, "oprninfo");
        if (!ListUtils.isEmpty(opernInfoList)) {
            opernInfoList = opernInfoList.stream().filter(item -> StringUtils.isNotEmpty(MapUtils.get(item, "oprnOprtCode"))).collect(Collectors.toList());
            insureGetInfoDAO.deleteOperInfo(map);
            if (!ListUtils.isEmpty(opernInfoList)) {
                opernInfoList.get(0).put("oprnOprtType", "1");
                opernInfoList.stream().forEach(item -> {
                    Object oprnOprtStartTime = MapUtils.get(item, "oprnOprtStartTime");
                    if (oprnOprtStartTime != null) {
                        item.put("oprnOprtStartTime", DateUtils.getNumerToStringDate(oprnOprtStartTime)); // 手术开始时间
                    }
                    Object oprnOprtEndTime = MapUtils.get(item, "oprnOprtEndTime");
                    if (oprnOprtEndTime != null) {
                        item.put("oprnOprtEndTime", DateUtils.getNumerToStringDate(oprnOprtEndTime)); // 手术结束时间
                    }
                    Object anstDrStartTime = MapUtils.get(item, "anstDrStartTime");
                    if (anstDrStartTime != null) {
                        item.put("anstDrStartTime", DateUtils.getNumerToStringDate(anstDrStartTime)); // 麻醉开始时间
                    }
                    Object anstDrEndTime = MapUtils.get(item, "anstDrEndTime");
                    if (anstDrEndTime != null) {
                        item.put("anstDrEndTime", DateUtils.getNumerToStringDate(anstDrEndTime)); // 麻醉结束时间
                    }
                    Object oprnOprtDate = MapUtils.get(item, "oprnOprtDate");
                    /**
                     * 如果直接在清单上面填写  手术信息，则需要根据手术开始时间和结束时间来确定手术操作日期
                     */
                    if (oprnOprtDate == null && oprnOprtStartTime != null) {
                        item.put("oprnOprtDate", DateUtils.parse(DateUtils.getNumerToStringDate(oprnOprtStartTime), DateUtils.Y_M_D)); // 麻醉结束时间
                    } else {
                        item.put("oprnOprtDate", DateUtils.getNumerToStringDate(oprnOprtDate)); // 手术操作日期
                    }
                    /**
                     * 前端默认第一条数据就是主要手术  1:代表主要手术  2：其他手术
                     */
                    if (!"1".equals(MapUtils.get(item, "oprnOprtType"))) {
                        item.put("oprnOprtType", "2"); // 手术操作类别
                    }

                });

                List<Map<String, Object>> mapList = insertCommonSettleInfo(map, opernInfoList);
                if (!ListUtils.isEmpty(mapList)) {
                    insureGetInfoDAO.deleteOperInfo(map);
                    insureGetInfoDAO.insertOperInfo(mapList);
                }
            }
        }
    }

    /**
     * @Method insertItemInfo
     * @Desrciption 处理医疗保障基金结算清单：收费项目节点
     * @Param
     * @Author fuhui
     * @Date 2021/11/1 20:13
     * @Return
     **/
    private List<Map<String, Object>> insertItemInfo(Map<String, Object> map) {

        List<Map<String, Object>> list = insureGetInfoDAO.selectItemInfo(map);
        List<Map<String, Object>> feeDetailMapList;
        List<Map<String, Object>> groupListMap = new ArrayList<>();
        List<Map<String, Object>> feeListMap = new ArrayList<>();
        //固定项目名称
        List<String> medChrgitmTypeList = Stream.of("01","02","03","04","05","06","07","08","09","10","11","12","13","14","15").collect(Collectors.toList());
        if (!ListUtils.isEmpty(list)) {
            Map<String, List<Map<String, Object>>> groupMap = list.stream().
                    collect(Collectors.groupingBy(item -> MapUtils.get(item, "medChrgitm")));
            for(String medChrgitmType:medChrgitmTypeList){
                if(!groupMap.containsKey(medChrgitmType)){
                    Map<String, Object> pMap = new HashMap<>();
                    pMap.put("amt", BigDecimal.ZERO.setScale(2));
                    pMap.put("claaSumfee", BigDecimal.ZERO.setScale(2));
                    pMap.put("clabAmt", BigDecimal.ZERO.setScale(2));
                    pMap.put("fulamtOwnpayAmt", BigDecimal.ZERO.setScale(2));
                    pMap.put("othAmt", BigDecimal.ZERO.setScale(2));
                    pMap.put("medChrgitm", medChrgitmType);
                    list.add(pMap);
                }
            }
            //重新排序
            list.sort((o1, o2) -> o1.get("medChrgitm").toString().compareTo(o2.get("medChrgitm").toString()));
            return list;
        }
        Map<String, Object> data = insureUnifiedBaseService.queryFeeDetailInfo(map).getData();
        feeDetailMapList = MapUtils.get(data, "outptMap");
        if (ListUtils.isEmpty(feeDetailMapList)) {
            throw new AppException("没有获取到医保费用明细数据");
        }

        Map<String, List<Map<String, Object>>> groupMap = feeDetailMapList.stream().
                collect(Collectors.groupingBy(item -> MapUtils.get(item, "med_chrgitm_type")));
        for(String medChrgitmType:medChrgitmTypeList){
            if(groupMap.containsKey(medChrgitmType)){
                BigDecimal sumDetItemFeeSumamt = new BigDecimal(0.00); // 总费用
                BigDecimal AClassFee = new BigDecimal(0.00);  // 甲类费用
                BigDecimal BClassFee = new BigDecimal(0.00);  // 乙类费用
                BigDecimal CClassFee = new BigDecimal(0.00);  // 丙类费用
                BigDecimal otherClassFee = new BigDecimal(0.00); // 其他费用
                System.out.println(medChrgitmType + "=====" + groupMap.get(medChrgitmType));
                Iterator<Map<String, Object>> iterator = groupMap.get(medChrgitmType).iterator();
                if (iterator.hasNext()) {
                    Map<String, Object> pMap = new HashMap<>();
                    List<Map<String, Object>> listMap = groupMap.get(medChrgitmType);
                    for (Map<String, Object> item : listMap) {
                        DecimalFormat df1 = new DecimalFormat("0.00");
                        sumDetItemFeeSumamt = BigDecimalUtils.add(sumDetItemFeeSumamt,
                                BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert
                                        (MapUtils.get(item, "det_item_fee_sumamt") == null ? "" :
                                                MapUtils.get(item, "det_item_fee_sumamt").toString()))));
                        if ("01".equals(MapUtils.get(item, "chrgitm_lv"))) {
                            AClassFee = BigDecimalUtils.add(AClassFee, BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "det_item_fee_sumamt") == null ? "" : MapUtils.get(item, "det_item_fee_sumamt").toString()))));
                        }
                        if ("02".equals(MapUtils.get(item, "chrgitm_lv"))) {
                            BClassFee = BigDecimalUtils.add(BClassFee, BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "det_item_fee_sumamt") == null ? "" : MapUtils.get(item, "det_item_fee_sumamt").toString()))));
                        }
                        if ("03".equals(MapUtils.get(item, "chrgitm_lv"))) {
                            CClassFee = BigDecimalUtils.add(CClassFee, BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "fulamt_ownpay_amt") == null ? "" : MapUtils.get(item, "fulamt_ownpay_amt").toString()))));
                        }
                    }
                    otherClassFee = BigDecimalUtils.subtractMany(sumDetItemFeeSumamt, AClassFee, BClassFee, CClassFee);
                    pMap.put("amt", sumDetItemFeeSumamt);
                    pMap.put("claaSumfee", AClassFee);
                    pMap.put("clabAmt", BClassFee);
                    pMap.put("fulamtOwnpayAmt", CClassFee);
                    pMap.put("othAmt", otherClassFee);
                    pMap.put("medChrgitm", medChrgitmType);
                    groupListMap.add(pMap);
                    feeListMap.add(pMap);
                }
            }else {
                Map<String, Object> pMap = new HashMap<>();
                pMap.put("amt", BigDecimal.ZERO.setScale(2));
                pMap.put("claaSumfee", BigDecimal.ZERO.setScale(2));
                pMap.put("clabAmt", BigDecimal.ZERO.setScale(2));
                pMap.put("fulamtOwnpayAmt", BigDecimal.ZERO.setScale(2));
                pMap.put("othAmt", BigDecimal.ZERO.setScale(2));
                pMap.put("medChrgitm", medChrgitmType);
                groupListMap.add(pMap);
            }
        }
//        for (String key : groupMap.keySet()) {
//            BigDecimal sumDetItemFeeSumamt = new BigDecimal(0.00); // 总费用
//            BigDecimal AClassFee = new BigDecimal(0.00);  // 甲类费用
//            BigDecimal BClassFee = new BigDecimal(0.00);  // 乙类费用
//            BigDecimal CClassFee = new BigDecimal(0.00);  // 丙类费用
//            BigDecimal otherClassFee = new BigDecimal(0.00); // 其他费用
//            System.out.println(key + "=====" + groupMap.get(key));
//            Iterator<Map<String, Object>> iterator = groupMap.get(key).iterator();
//            if (iterator.hasNext()) {
//                pMap = new HashMap<>();
//                List<Map<String, Object>> listMap = groupMap.get(key);
//                for (Map<String, Object> item : listMap) {
//                    DecimalFormat df1 = new DecimalFormat("0.00");
//                    sumDetItemFeeSumamt = BigDecimalUtils.add(sumDetItemFeeSumamt,
//                            BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert
//                                    (MapUtils.get(item, "det_item_fee_sumamt") == null ? "" :
//                                            MapUtils.get(item, "det_item_fee_sumamt").toString()))));
//                    if ("01".equals(MapUtils.get(item, "chrgitm_lv"))) {
//                        AClassFee = BigDecimalUtils.add(AClassFee, BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "det_item_fee_sumamt") == null ? "" : MapUtils.get(item, "det_item_fee_sumamt").toString()))));
//                    }
//                    if ("02".equals(MapUtils.get(item, "chrgitm_lv"))) {
//                        BClassFee = BigDecimalUtils.add(BClassFee, BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "det_item_fee_sumamt") == null ? "" : MapUtils.get(item, "det_item_fee_sumamt").toString()))));
//                    }
//                    if ("03".equals(MapUtils.get(item, "chrgitm_lv"))) {
//                        CClassFee = BigDecimalUtils.add(CClassFee, BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "fulamt_ownpay_amt") == null ? "" : MapUtils.get(item, "fulamt_ownpay_amt").toString()))));
//                    }
//                }
//                otherClassFee = BigDecimalUtils.subtractMany(sumDetItemFeeSumamt, AClassFee, BClassFee, CClassFee);
//                pMap.put("amt", sumDetItemFeeSumamt);
//                pMap.put("claaSumfee", AClassFee);
//                pMap.put("clabAmt", BClassFee);
//                pMap.put("fulamtOwnpayAmt", CClassFee);
//                pMap.put("othAmt", otherClassFee);
//                pMap.put("medChrgitm", key);
//                groupListMap.add(pMap);
//            }
//        }
        List<Map<String, Object>> mapList = insertCommonSettleInfo(map, feeListMap);
        insureGetInfoDAO.deleteItemInfo(map);
        insureGetInfoDAO.insertItemInfo(mapList);
        //重新排序
        groupListMap.sort((o1, o2) -> o1.get("medChrgitm").toString().compareTo(o2.get("medChrgitm").toString()));
        return groupListMap;
    }


    /**
     * @return
     * @Method getInsureCost
     * @Desrciption 自费病人费用明细信息上传
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-11 22:54
     * @Return java.util.Map
     */
    @Override
    public Boolean insertInsureCost(InsureSettleInfoDTO insureSettleInfoDTO) {
        // 1.先判断是否选择了医保机构
        InsureConfigurationDTO insureConfigurationDTO = checkInsureConfig(insureSettleInfoDTO);
        insureSettleInfoDTO.setOrgCode(insureConfigurationDTO.getCode());
        // 2.判断是门诊自费病人 还是 住院自费病人  查询费用数据
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (insureSettleInfoDTO.getLx().equals("1")) {

            mapList = handlerInptCostFee(insureSettleInfoDTO);

        } else if (insureSettleInfoDTO.getLx().equals("0")) {
            mapList = handlerOutptCostFee(insureSettleInfoDTO);
        }
        // 3.组装医保接口参数
        List<Map<String, Object>> listMap = new ArrayList<>();
        if (!ListUtils.isEmpty(mapList)) {
            for (Map<String, Object> item : mapList) {
                // 医保上传
                Map feedetail = new HashMap();
                String doctorId = MapUtils.get(item, "doctorId");
                String doctorName = MapUtils.get(item, "doctorName");
                feedetail.put("mdtrt_sn", insureSettleInfoDTO.getId()); // 就医流水号
                feedetail.put("ipt_otp_no", insureSettleInfoDTO.getVisitNo()); // 住院/门诊号
                if ("1".equals(insureSettleInfoDTO.getLx())) {
                    feedetail.put("med_type", "1201"); // 医疗类别
                } else {
                    feedetail.put("med_type", "11"); // 医疗类别
                }
                feedetail.put("chrg_bchno", "0"); // 收费批次号
                feedetail.put("feedetl_sn", MapUtils.get(item, "id")); // 费用明细流水号
                feedetail.put("psn_cert_type", insureSettleInfoDTO.getCertCode()); // 人员证件类型
                feedetail.put("certno", insureSettleInfoDTO.getCertNo()); // 证件号码
                feedetail.put("psn_name", insureSettleInfoDTO.getName()); // 人员姓名
                if (insureSettleInfoDTO.getLx().equals("1")) {

                    feedetail.put("fee_ocur_time", DateUtils.format((Date) item.get("costTime"), DateUtils.Y_M_DH_M_S)); // 费用发生时间

                } else if (insureSettleInfoDTO.getLx().equals("0")) {
                    feedetail.put("fee_ocur_time", DateUtils.format((Date) item.get("crteTime"), DateUtils.Y_M_DH_M_S)); // 费用发生时间

                }
                BigDecimal cnt = BigDecimalUtils.scale((BigDecimal) item.get("totalNum"), 4);
                BigDecimal price = BigDecimalUtils.scale((BigDecimal) item.get("price"), 4);
                feedetail.put("cnt", cnt); // 数量
                feedetail.put("pric", price); // 单价
                DecimalFormat df1 = new DecimalFormat("0.00");
                String realityPrice = df1.format(BigDecimalUtils.convert(item.get("realityPrice").toString()));
                BigDecimal convertPrice = BigDecimalUtils.convert(realityPrice);
                feedetail.put("det_item_fee_sumamt", convertPrice); // 明细项目费用总额
                feedetail.put("med_list_codg", item.get("insureItemCode") == null ? "" : item.get("insureItemCode").toString()); // 医疗目录编码
                feedetail.put("medins_list_codg", item.get("hospItemCode") == null ? "" : item.get("hospItemCode").toString()); // 医药机构目录编码
                feedetail.put("medins_list_name", MapUtils.get(item, "insureItemName")); // 医药机构目录名称
                feedetail.put("med_chrgitm_type", MapUtils.get(item, "insureItemType")); // 医疗收费项目类别
                if (StringUtils.isEmpty(MapUtils.get(feedetail, "med_chrgitm_type"))) {
                    feedetail.put("med_chrgitm_type", "01");
                }
                feedetail.put("prodname", MapUtils.get(item, "insureItemName")); // 商品名
                feedetail.put("bilg_dept_codg", MapUtils.get(item, "deptId")); // 开单科室编码
                feedetail.put("bilg_dept_name", MapUtils.get(item, "deptName")); // 开单科室名称
                if (StringUtils.isEmpty(MapUtils.get(item, "deptId"))) {
                    feedetail.put("bilg_dr_codg", doctorId); // 开单医生编码
                } else {
                    feedetail.put("bilg_dr_codg", MapUtils.get(item, "deptId")); // 开单医生编码
                }
                if (StringUtils.isEmpty(MapUtils.get(item, "doctorName"))) {
                    feedetail.put("bilg_dr_codg", doctorName); // 开单医生编码
                } else {
                    feedetail.put("bilg_dr_name", MapUtils.get(item, "doctorName")); // 开单医生姓名
                }
                listMap.add(feedetail);

            }

            /**
             * 公共入参
             */
            Map param = new HashMap();
            param.put("infno", "4201");  //交易编号
            param.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
            param.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
            param.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
            param.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
            param.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());// 就医地医保区划
            for (Map map : listMap) {
                param.put("input", map);  //交易输入
                String json = JSONObject.toJSONString(param);
                logger.info("自费病人费用明细传输入参:" + json);
                String url = insureConfigurationDTO.getUrl();
                String resultJson = HttpConnectUtil.unifiedPayPostUtil(url, json);
                logger.info("自费病人费用明细传输回参:" + resultJson);
                Map<String, Object> resultMap = JSONObject.parseObject(resultJson, Map.class);
                if (StringUtils.isEmpty(resultJson)) {
                    throw new AppException("访问统一支付平台失败");
                }
                if ("999".equals(resultMap.get("infcode").toString())) {
                    throw new AppException((String) resultMap.get("msg"));
                }
                if (!"0".equals(resultMap.get("infcode").toString())) {
                    throw new AppException((String) resultMap.get("err_msg"));
                }
            }
            insertHandlerInsureCost(mapList, insureSettleInfoDTO);
        }
        return true;
    }

    /**
     * @Method insertHandlerInsureCost
     * @Desrciption 处理自费病人明细数据
     * @Param item ：上传参数
     * infoDTO
     * @Author fuhui
     * @Date 2021/8/21 13:57
     * @Return
     **/
    private void insertHandlerInsureCost(List<Map<String, Object>> feeList, InsureSettleInfoDTO infoDTO) {
        List<InsureUploadCostDTO> insureIndividualCostDOList = new ArrayList<>();
        for (Map<String, Object> item : feeList) {
            InsureUploadCostDTO insureUploadCostDTO = new InsureUploadCostDTO();
            insureUploadCostDTO.setId(SnowflakeUtils.getId());//id
            insureUploadCostDTO.setHospCode(infoDTO.getHospCode());//医院编码
            insureUploadCostDTO.setVisitId(infoDTO.getId());//患者id

            insureUploadCostDTO.setMdtrtSn("");
            insureUploadCostDTO.setIptOtpNo("");
            insureUploadCostDTO.setMedType("");
            insureUploadCostDTO.setChrgBchno("");
            insureUploadCostDTO.setPsnCertType("");
            insureUploadCostDTO.setCertno("");
            insureUploadCostDTO.setPsnName("");
            insureUploadCostDTO.setOrgCode(infoDTO.getInsureRegCode());
            insureUploadCostDTO.setCostId(MapUtils.get(item, "id"));//费用id

            insureUploadCostDTO.setCrteId(infoDTO.getCrteId());//创建id
            insureUploadCostDTO.setCrteName(infoDTO.getCrteName());//创建人姓名
            insureUploadCostDTO.setCrteTime(new Date());//创建时间

            String doctorId = MapUtils.get(item, "doctorId");
            String doctorName = MapUtils.get(item, "doctorName");
            insureUploadCostDTO.setFeedetlSn(MapUtils.get(item, "id"));
            if (infoDTO.getLx().equals("1")) {
                insureUploadCostDTO.setFeeOcurTime((Date) item.get("costTime")); // 费用发生时间
            } else if (infoDTO.getLx().equals("0")) {
                insureUploadCostDTO.setFeeOcurTime((Date) item.get("crteTime")); // 费用发生时间
            }

            BigDecimal cnt = BigDecimalUtils.scale((BigDecimal) item.get("totalNum"), 4);
            BigDecimal price = BigDecimalUtils.scale((BigDecimal) item.get("price"), 4);
            insureUploadCostDTO.setCnt(cnt); // 数量
            insureUploadCostDTO.setPric(price); // 单价

            DecimalFormat df1 = new DecimalFormat("0.00");
            String realityPrice = df1.format(BigDecimalUtils.convert(item.get("realityPrice").toString()));
            BigDecimal convertPrice = BigDecimalUtils.convert(realityPrice);
            insureUploadCostDTO.setDetItemFeeSumamt(convertPrice); // 明细项目费用总额

            insureUploadCostDTO.setMedListCodg(item.get("insureItemCode") == null ? "" : item.get("insureItemCode").toString()); // 医疗目录编码
            insureUploadCostDTO.setMedinsListCodg(item.get("hospItemCode") == null ? "" : item.get("hospItemCode").toString()); // 医药机构目录编码
            insureUploadCostDTO.setMedinsListName(MapUtils.get(item, "insureItemName")); // 医药机构目录名称
            insureUploadCostDTO.setMedChrgitmType(item.get("insureItemType") == null ? "" : item.get("insureItemType").toString()); // 医疗收费项目类别
            insureUploadCostDTO.setProdname(MapUtils.get(item, "insureItemName")); // 商品名

            insureUploadCostDTO.setBilgDeptCodg(MapUtils.get(item, "deptId")); // 开单科室编码
            insureUploadCostDTO.setBilgDeptName(MapUtils.get(item, "deptName")); // 开单科室名称

            if (StringUtils.isEmpty(MapUtils.get(item, "deptId"))) {
                insureUploadCostDTO.setBilgDrCodg(doctorId); // 开单医生编码
            } else {
                insureUploadCostDTO.setBilgDrCodg(MapUtils.get(item, "deptId")); // 开单医生编码
            }
            if (StringUtils.isEmpty(MapUtils.get(item, "doctorName"))) {
                insureUploadCostDTO.setBilgDrName(doctorName); // 开单医生编码
            } else {
                insureUploadCostDTO.setBilgDrName(MapUtils.get(item, "doctorName")); // 开单医生姓名
            }
            insureIndividualCostDOList.add(insureUploadCostDTO);
        }
        insureGetInfoDAO.deleteCost(infoDTO.getId(), infoDTO.getHospCode());
        insureGetInfoDAO.insertCost(insureIndividualCostDOList);
    }

    /**
     * @Method checkInsureConfig
     * @Desrciption 检查医保机构是否已选择
     * @Param insureSettleInfoDTO
     * @Author fuhui
     * @Date 2021/8/21 13:58
     * @Return
     **/
    private InsureConfigurationDTO checkInsureConfig(InsureSettleInfoDTO insureSettleInfoDTO) {
        Map<String, Object> isInsureUnifiedMap = new HashMap<>();
        isInsureUnifiedMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        isInsureUnifiedMap.put("code", "HOSP_INSURE_CODE");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
        if (sysParameterDTO == null) {
            throw new AppException("请先配置默认的医疗机构编码参数信息:编码为:HOSP_INSURE_CODE,值为对应的医疗机构编码值");
        }
        if (insureSettleInfoDTO.getInsureRegCode() == null) {
            throw new AppException("请先选择要上传的医保机构");
        }
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        insureConfigurationDTO.setOrgCode(sysParameterDTO.getValue());
        insureConfigurationDTO.setRegCode(insureSettleInfoDTO.getInsureRegCode());
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if (insureConfigurationDTO == null) {
            throw new AppException("医保机构配置信息为空");
        }
        return insureConfigurationDTO;
    }

    /**
     * @Method handlerOutptCostFee
     * @Desrciption 处理门诊患者费用
     * @Param
     * @Author fuhui
     * @Date 2021/8/21 10:54
     * @Return
     **/
    private List<Map<String, Object>> handlerOutptCostFee(InsureSettleInfoDTO insureSettleInfoDTO) {

        String insureRegCode = insureSettleInfoDTO.getOrgCode();
        //判断是否有传输费用信息
        Map<String, Object> insureCostParam = new HashMap<String, Object>();
        insureCostParam.put("hospCode", insureSettleInfoDTO.getHospCode());//医院编码
        insureCostParam.put("statusCode", Constants.ZTBZ.ZC);//状态标志 = 正常
        insureCostParam.put("visitId", insureSettleInfoDTO.getId());//就诊id
        insureCostParam.put("isMatch", Constants.SF.S);//是否匹配 = 是
//        insureCostParam.put("transmitCode", Constants.SF.F);//传输标志 = 未传输
        insureCostParam.put("insureRegCode", insureRegCode);// 医保机构编码
        insureCostParam.put("isHospital", Constants.SF.F); // 区分门诊还是住院
        insureCostParam.put("settle_code", "2");//费用结算状态
        List<Map<String, Object>> insureCostList = insureIndividualCostDAO.queryOutptInsureCostByVisit(insureCostParam);
        if (ListUtils.isEmpty(insureCostList)) {
            throw new AppException("该病人没有可以上传的费用");
        }
        return insureCostList;
    }

    /**
     * @Method handlerInptCostFee
     * @Desrciption 处理住院病人医保费用数据
     * @Param insureSettleInfoDTO
     * @Author fuhui
     * @Date 2021/8/21 10:38
     * @Return
     **/
    private List<Map<String, Object>> handlerInptCostFee(InsureSettleInfoDTO insureSettleInfoDTO) {
        Map<String, String> insureCostParam = new HashMap<String, String>();
        String hospCode = insureSettleInfoDTO.getHospCode();
        String visitId = insureSettleInfoDTO.getId();
        insureCostParam.put("hospCode", hospCode);//医院编码
        insureCostParam.put("statusCode", Constants.ZTBZ.ZC);//状态标志 = 正常
        insureCostParam.put("visitId", visitId);//就诊id
        insureCostParam.put("isMatch", Constants.SF.S);//是否匹配 = 是
//        insureCostParam.put("transmitCode", Constants.SF.F);//传输标志 = 未传输
        insureCostParam.put("insureRegCode", insureSettleInfoDTO.getOrgCode());// 医保机构编码
        insureCostParam.put("queryBaby", "N");// 医保机构编码
        insureCostParam.put("settle_code", "2");//费用结算状态
        List<Map<String, Object>> insureCostList = insureIndividualCostDAO.queryInsureCostByVisit(insureCostParam);
        if (ListUtils.isEmpty(insureCostList)) {
            throw new AppException("该自费病人没有匹配的费用明细数据");
        }
        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        insureIndividualVisitDTO.setHospCode(hospCode);
        insureIndividualVisitDTO.setVisitId(visitId);
        // 查询有退费的数据集合,且未上传的的数据集合
        List<InptCostDTO> inptCostDTOList = insureIndividualCostDAO.queryBackInptFee(insureIndividualVisitDTO);
        // 查询已经上传的费用数据
        List<Map<String, Object>> individualCostDTOList = insureIndividualCostDAO.queryInsureInptCost(insureIndividualVisitDTO);
        int num = 0;
        /**
         * 本次上传如果没有正常的费用数据,则不上传到医保，直接把退费的数据插入到医保费用表
         */
        if (!ListUtils.isEmpty(insureCostList)) {
            for (Map<String, Object> item : insureCostList) {
                if ("0".equals(MapUtils.get(item, "statusCode"))) {
                    num++;
                } else {
                    continue;
                }
            }
        }
        if (num == 0) {
            // 直接把全开，全退的费用保存到费用表 但是不进行调用医保的操作
            insertNotUpLoadFee(insureCostList, insureSettleInfoDTO);
            return new ArrayList<>();
        } else {
            // 说明有正常的数据 需要调用医保接口
            List<Map<String, Object>> list2 = new ArrayList<>();  // 处理
            List<Map<String, Object>> list1 = new ArrayList<>();
            List<Map<String, Object>> list3 = new ArrayList<>(); // 处理正负直接相抵的集合
            if (!ListUtils.isEmpty(inptCostDTOList)) {
                Map<String, InptCostDTO> collect = inptCostDTOList.stream().collect(Collectors.toMap(InptCostDTO::getOldCostId, Function.identity()));
                // 传正常的数据    假如最原始已经上传 10条  退4条     第二次传输 则  传-10  正6
                for (Map<String, Object> item : insureCostList) {
                    if (!MapUtils.isEmpty(collect) && collect.containsKey(MapUtils.get(item, "id"))) {
                        list3.add(item);
                        continue;
                    } else if (!MapUtils.isEmpty(collect) && collect.containsKey(MapUtils.get(item, "oldCostId")) &&
                            BigDecimalUtils.less(MapUtils.get(item, "totalNum"), new BigDecimal(0.00))) {
                        list3.add(item);
                        continue;
                    } else {
                        list1.add(item);
                    }
                }
                // 传退费对应的数据
                if (!ListUtils.isEmpty(individualCostDTOList)) {
                    for (Map<String, Object> item : individualCostDTOList) {
                        if (collect.containsKey(MapUtils.get(item, "costId"))) {
                            list2.add(item);
                        }
                    }
                }

                if (!ListUtils.isEmpty(list3)) {
                    insertNotUpLoadFee(list3, insureSettleInfoDTO);
                }
                list2.addAll(list1);
            } else {
                list2.addAll(insureCostList);
            }
            return list2;
        }

    }

    /**
     * @Method
     * @Desrciption 保存未上传的费用数据
     * @Param insureCostList：未上传的费用集合
     * inptVisitDTO ：患者基本信息
     * @Author fuhui
     * @Date 2021/8/13 9:16
     * @Return
     **/
    private void insertNotUpLoadFee(List<Map<String, Object>> insureCostList, InsureSettleInfoDTO insureSettleInfoDTO) {
        List<InsureIndividualCostDO> insureIndividualCostDOList = new ArrayList<InsureIndividualCostDO>();
        String hospCode = insureSettleInfoDTO.getHospCode();
        String visitId = insureSettleInfoDTO.getId();
        String crteId = insureSettleInfoDTO.getCrteId();
        String crteName = insureSettleInfoDTO.getCrteName();
        for (Map<String, Object> item : insureCostList) {
            InsureIndividualCostDO insureIndividualCostDO = new InsureIndividualCostDO();
            insureIndividualCostDO.setId(SnowflakeUtils.getId());//id
            insureIndividualCostDO.setHospCode(hospCode);//医院编码
            insureIndividualCostDO.setVisitId(visitId);//患者id
            insureIndividualCostDO.setCostId((String) item.get("id"));//费用id
            insureIndividualCostDO.setSettleId(null);//结算id
            insureIndividualCostDO.setIsHospital(Constants.SF.S);//是否住院 = 是
            insureIndividualCostDO.setItemType((String) item.get("insureItemType"));//医保项目类别
            insureIndividualCostDO.setItemCode((String) item.get("insureItemCode"));//医保项目编码
            insureIndividualCostDO.setItemName((String) item.get("insureItemName"));//医保项目名称
            insureIndividualCostDO.setGuestRatio((String) item.get("deductible"));//自付比例
            insureIndividualCostDO.setPrimaryPrice((BigDecimal) item.get("realityPrice"));//原费用
            insureIndividualCostDO.setApplyLastPrice(null);//报销后费用
            insureIndividualCostDO.setOrderNo(null);//顺序号
            insureIndividualCostDO.setInsureIsTransmit(Constants.SF.F);
            insureIndividualCostDO.setTransmitCode(Constants.SF.S);//传输标志 = 已传输
            insureIndividualCostDO.setCrteId(crteId);//创建id
            insureIndividualCostDO.setCrteName(crteName);//创建人姓名
            insureIndividualCostDO.setCrteTime(new Date());//创建时间
            insureIndividualCostDOList.add(insureIndividualCostDO);
        }
        insureIndividualCostDAO.insertInsureCost(insureIndividualCostDOList);
    }

    /**
     * @Method queryCost
     * @Desrciption
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-13 19:09
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryCost(InsureSettleInfoDTO insureSettleInfoDTO) {

        List<InptCostDTO> costDTOList = new ArrayList<>();
        if (insureSettleInfoDTO.getLx().equals("1")) {
            PageHelper.startPage(insureSettleInfoDTO.getPageNo(), insureSettleInfoDTO.getPageSize());
            costDTOList = insureGetInfoDAO.queryInCost(insureSettleInfoDTO);
        }
        if (insureSettleInfoDTO.getLx().equals("2")) {
            PageHelper.startPage(insureSettleInfoDTO.getPageNo(), insureSettleInfoDTO.getPageSize());
            costDTOList = insureGetInfoDAO.queryOutCost(insureSettleInfoDTO);
        }
        return PageDTO.of(costDTOList);
    }

    /**
     * @Method queryUploadCost
     * @Desrciption
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-13 19:09
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryUploadCost(InsureSettleInfoDTO insureSettleInfoDTO) {

        List<InsureUploadCostDTO> costDTOList = new ArrayList();

        PageHelper.startPage(insureSettleInfoDTO.getPageNo(), insureSettleInfoDTO.getPageSize());
        costDTOList = insureGetInfoDAO.queryAll(insureSettleInfoDTO);

        return PageDTO.of(costDTOList);
    }


    /**
     * @Method queryVisit
     * @Desrciption
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-13 19:10
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryVisit(InsureSettleInfoDTO insureSettleInfoDTO) {
        PageHelper.startPage(insureSettleInfoDTO.getPageNo(), insureSettleInfoDTO.getPageSize());
        List<InptVisitDTO> visitDTOList = insureGetInfoDAO.queryVisit(insureSettleInfoDTO);

        return PageDTO.of(visitDTOList);

    }

    /**
     * @Method queryInsureSettle
     * @Desrciption 为上策基金支付明细
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-13 19:10
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryInsureSettle(InsureSettleInfoDTO insureSettleInfoDTO) {
        PageHelper.startPage(insureSettleInfoDTO.getPageNo(), insureSettleInfoDTO.getPageSize());
        List<PayInfoDTO> payInfoDTO = insureGetInfoDAO.queryInsureSettle(insureSettleInfoDTO);

        return PageDTO.of(payInfoDTO);

    }

    /**
     * @Method queryInsure
     * @Desrciption 未上传费用明细
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-13 19:10
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryInsure(InsureSettleInfoDTO insureSettleInfoDTO) {
        PageHelper.startPage(insureSettleInfoDTO.getPageNo(), insureSettleInfoDTO.getPageSize());
        List<ItemInfoDTO> itemInfoDTOList = insureGetInfoDAO.queryInsure(insureSettleInfoDTO);

        return PageDTO.of(itemInfoDTOList);

    }

    /**
     * @Method queryUnMatchPage
     * @Desrciption 查询自费病人的匹配费用明细
     * @Param
     * @Author fuhui
     * @Date 2021/8/21 15:37
     * @Return
     **/
    @Override
    public PageDTO queryPage(InsureSettleInfoDTO insureSettleInfoDTO) {
        Map<String, Object> isInsureUnifiedMap = new HashMap<>();
        isInsureUnifiedMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        isInsureUnifiedMap.put("code", "HOSP_INSURE_CODE");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
        if (sysParameterDTO == null) {
            throw new AppException("请先配置默认的医疗机构编码参数信息:编码为:HOSP_INSURE_CODE,值为对应的医疗机构编码值");
        }
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        insureConfigurationDTO.setOrgCode(sysParameterDTO.getValue());
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        insureSettleInfoDTO.setOrgCode(insureConfigurationDTO.getCode());

        List<InptCostDTO> costDTOList = null;
        if ("1".equals(insureSettleInfoDTO.getLx())) {
            PageHelper.startPage(insureSettleInfoDTO.getPageNo(), insureSettleInfoDTO.getPageSize());
            costDTOList = insureGetInfoDAO.queryInptMatchPage(insureSettleInfoDTO);
        }
        if ("0".equals(insureSettleInfoDTO.getLx())) {
            PageHelper.startPage(insureSettleInfoDTO.getPageNo(), insureSettleInfoDTO.getPageSize());
            costDTOList = insureGetInfoDAO.queryOutMatchPage(insureSettleInfoDTO);
        }
        return PageDTO.of(costDTOList);
    }


    /**
     * @Method queryUnMatchPage
     * @Desrciption 查询自费病人的未匹配费用明细
     * @Param
     * @Author fuhui
     * @Date 2021/8/21 15:37
     * @Return
     **/
    @Override
    public PageDTO queryUnMatchPage(InsureSettleInfoDTO insureSettleInfoDTO) {
        List<InptCostDTO> costDTOList = null;
        if ("1".equals(insureSettleInfoDTO.getLx())) {
            PageHelper.startPage(insureSettleInfoDTO.getPageNo(), insureSettleInfoDTO.getPageSize());
            costDTOList = insureGetInfoDAO.queryInptUnMatchPage(insureSettleInfoDTO);
        }
        if ("0".equals(insureSettleInfoDTO.getLx())) {
            PageHelper.startPage(insureSettleInfoDTO.getPageNo(), insureSettleInfoDTO.getPageSize());
            costDTOList = insureGetInfoDAO.queryOutUnMatchPage(insureSettleInfoDTO);
        }
        return PageDTO.of(costDTOList);
    }

    /**
     * @Method selectItemInfo
     * @Desrciption 医疗保障基金结算清单信息 : 查询收费项目信息
     * @Param
     * @Author fuhui
     * @Date 2021/11/3 15:13
     * @Return
     **/
    public List<Map<String, Object>> selectItemInfo(Map<String, Object> map) {
        List<Map<String, Object>> list = insureGetInfoDAO.qureryItemInfo(map);
        if (ListUtils.isEmpty(list)) {
            throw new AppException("医疗保障基金结算清单上传时,收费项目信息节点入参为空");
        }
        return list;
    }

    /**
     * @Method getEmptyErr
     * @Desrciption
     * @Param
     * @Author fuhui
     * @Date 2021/11/17 14:34
     * @Return
     **/
    public <T> T getEmptyErr(Map map, Object key, String errMsg) {
        if (!map.containsKey(key)) {
            throw new RuntimeException(errMsg);
        }
        Object obj = MapUtils.get(map, key);
        if (obj == null) {
            throw new RuntimeException(errMsg);
        }
        if (obj instanceof String) {
            if (StringUtils.isEmpty((String) obj)) {
                throw new RuntimeException(errMsg);
            }
        }
        return (T) map.get(key);
    }

    //保存操作日志
    private void insertDrgDipBusinessOptInfoLog(Map<String, Object> dataMap){
        Map<String, Object> baseInfo = MapUtils.get(dataMap, "baseInfo");// 基础信息
        Map<String, Object> businessLogMap = new HashMap<>();
        businessLogMap.put("businessId",MapUtils.get(baseInfo, "id"));
        businessLogMap.put("optType","3");
        businessLogMap.put("optTypeName",MapUtils.get(dataMap, "optTypeName"));
        businessLogMap.put("type",MapUtils.get(dataMap, "type"));
        businessLogMap.put("qulityId",MapUtils.get(dataMap, "qulityId"));
        businessLogMap.put("businessType",MapUtils.get(dataMap, "businessType"));
        businessLogMap.put("isForce",MapUtils.get(baseInfo, "isForce"));
        businessLogMap.put("forceUploadInfo",MapUtils.get(baseInfo, "forceUploadInfo"));
        businessLogMap.put("hospCode",MapUtils.get(dataMap, "hospCode"));
        businessLogMap.put("insureRegCode",MapUtils.get(baseInfo, "insureRegCode"));
        businessLogMap.put("hospName",MapUtils.get(baseInfo, "hospName"));
        businessLogMap.put("orgCode",MapUtils.get(baseInfo, "orgCode"));
        businessLogMap.put("insureSettleId",MapUtils.get(baseInfo, "insureSettleId"));
        businessLogMap.put("medicalRegNo",MapUtils.get(baseInfo, "medicalRegCode"));
        businessLogMap.put("settleId",MapUtils.get(baseInfo, "settleId"));
        businessLogMap.put("visitId",MapUtils.get(baseInfo, "visit_id"));
        businessLogMap.put("psnNo",MapUtils.get(baseInfo, "psn_no"));
        businessLogMap.put("psnName",MapUtils.get(baseInfo, "psn_name"));
        businessLogMap.put("certNo",MapUtils.get(baseInfo, "certno"));
        businessLogMap.put("sex",MapUtils.get(baseInfo, "gend"));
//        businessLogMap.put("age",MapUtils.get(baseInfo, "age"));
        businessLogMap.put("insueType",MapUtils.get(baseInfo, "hi_type"));
        businessLogMap.put("inptTime",MapUtils.get(baseInfo, "adm_time"));
        businessLogMap.put("outptTime,",MapUtils.get(baseInfo, "dscg_time"));
        businessLogMap.put("medType",MapUtils.get(baseInfo, "medType"));
        businessLogMap.put("medTypeName",MapUtils.get(baseInfo, "medTypeName"));
        businessLogMap.put("doctorId",MapUtils.get(baseInfo, "chfpdr_code"));
        businessLogMap.put("doctorName",MapUtils.get(baseInfo, "chfpdr_name"));

        businessLogMap.put("inptDiagnose",MapUtils.get(baseInfo, "inptDiagnose"));
        businessLogMap.put("outptDiagnose",MapUtils.get(baseInfo, "outptDiagnose"));
        businessLogMap.put("totalFee",MapUtils.get(baseInfo, "totalFee"));
        businessLogMap.put("payFee",MapUtils.get(baseInfo, "payFee"));
        businessLogMap.put("selfFee",MapUtils.get(baseInfo, "selfFee"));
        businessLogMap.put("cashPayFee",MapUtils.get(baseInfo, "cashPayFee"));
        businessLogMap.put("inputJosn",dataMap);
        businessLogMap.put("crtId",MapUtils.get(dataMap, "crteId"));
        businessLogMap.put("crtName",MapUtils.get(dataMap, "crtName"));
        drgDipBusinessOptInfoBO.insertDrgDipBusinessOptInfoLog(businessLogMap);
    }

    //保存质控结果
    private DrgDipResultDTO insertDrgDipResult(Map<String, Object> dataMap,Map<String, Object> baseInfoMap,Map<String, Object> groupInfo,List<Map<String, Object>> qualityInfo) {
        Map<String, Object> baseInfo = MapUtils.get(dataMap, "baseInfo");// 基础信息
        //查询患者信息
        Map<String,Object> map = new HashMap<>() ;
        map.put("hospCode",MapUtils.get(dataMap, "hospCode"));
        map.put("visitId",MapUtils.get(baseInfo, "visit_id"));
        InsureIndividualVisitDTO insureIndividualVisitDTO = unifiedCommon.commonGetVisitInfo(map);
        //冗余基本信息
        DrgDipResultDTO drgDipResultDTO = new DrgDipResultDTO();
        drgDipResultDTO.setVisitId(MapUtils.get(baseInfo, "visit_id"));
        drgDipResultDTO.setMedcasno(MapUtils.get(baseInfo, "medcasno"));
        drgDipResultDTO.setPsnNo(MapUtils.get(baseInfo, "psn_no"));
        drgDipResultDTO.setAge(MapUtils.get(baseInfo, "age"));
        drgDipResultDTO.setGend(MapUtils.get(baseInfo, "gend"));
        drgDipResultDTO.setCertno(MapUtils.get(baseInfo, "certno"));
        drgDipResultDTO.setPsnName(MapUtils.get(baseInfo, "psn_name"));
        drgDipResultDTO.setInTime(MapUtils.get(baseInfo, "adm_time"));
        drgDipResultDTO.setOutTime(MapUtils.get(baseInfo, "dscg_time"));
        drgDipResultDTO.setDoctorId(MapUtils.get(baseInfo, "chfpdr_code"));
        drgDipResultDTO.setDoctorName(MapUtils.get(baseInfo, "chfpdr_name"));
        drgDipResultDTO.setInsutype(MapUtils.get(baseInfo, "hi_type"));
        drgDipResultDTO.setInsureSettleId(MapUtils.get(baseInfo, "set1_id"));
        drgDipResultDTO.setCrtId(MapUtils.get(dataMap, "crteId"));
        drgDipResultDTO.setCrtName(MapUtils.get(dataMap, "crteName"));
        drgDipResultDTO.setBusinessType(MapUtils.get(dataMap, "businessType"));
        drgDipResultDTO.setType(MapUtils.get(dataMap, "type"));
        drgDipResultDTO.setHospCode(MapUtils.get(dataMap, "hospCode"));
        drgDipResultDTO.setMedType(insureIndividualVisitDTO.getAka130());
        drgDipResultDTO.setMedTypeName(insureIndividualVisitDTO.getAka130Name());
        drgDipResultDTO.setInptDiagnose(insureIndividualVisitDTO.getInDiseaseName());
        drgDipResultDTO.setOutptDiagnose(insureIndividualVisitDTO.getOutDiseaseName());
        drgDipResultDTO.setDeptId(insureIndividualVisitDTO.getOutDeptId());
        drgDipResultDTO.setDeptName(insureIndividualVisitDTO.getOutDeptName());
        drgDipResultDTO.setMedicalRegNo(insureIndividualVisitDTO.getMedicalRegNo());
        drgDipResultDTO.setVisitNo(insureIndividualVisitDTO.getVisitNo());
        //保存质控结果
        drgDipResultDTO.setDrgDipName(MapUtils.get(groupInfo, "name"));
        drgDipResultDTO.setDrgDipCode(MapUtils.get(groupInfo, "code"));
        drgDipResultDTO.setBl(MapUtils.get(groupInfo, "bl"));
        drgDipResultDTO.setGroupMessages(JSONObject.toJSONString(MapUtils.get(groupInfo, "groupMessages")));
        if(MapUtils.get(baseInfoMap, "totalFee") !=null){
            drgDipResultDTO.setTotalFee(BigDecimalUtils.convert(MapUtils.get(baseInfoMap, "totalFee").toString()).setScale(2));
        }
        if(MapUtils.get(baseInfoMap, "pro_medic_mater") !=null){
            drgDipResultDTO.setProMedicMater(MapUtils.get(baseInfoMap, "pro_medic_mater").toString());
        }
        if(MapUtils.get(baseInfoMap, "pro_consum") !=null){
            drgDipResultDTO.setProConsum(MapUtils.get(baseInfoMap, "pro_consum").toString());
        }
        if(MapUtils.get(groupInfo, "weight") !=null){
            drgDipResultDTO.setWeightValue(MapUtils.get(groupInfo, "weight").toString());
        }
        if(MapUtils.get(groupInfo, "feeStand") !=null){
            drgDipResultDTO.setStandFee(BigDecimalUtils.convert(MapUtils.get(groupInfo, "feeStand").toString()).setScale(2));
        }
        if( drgDipResultDTO.getStandFee()!=null && drgDipResultDTO.getTotalFee() != null){
            drgDipResultDTO.setProfit(BigDecimalUtils.subtract(drgDipResultDTO.getStandFee(),drgDipResultDTO.getTotalFee()));
        }
        if(MapUtils.get(groupInfo, "feePay") !=null){
            drgDipResultDTO.setFeePay(BigDecimalUtils.convert(MapUtils.get(groupInfo, "feePay").toString()).setScale(2));
        }
        if(MapUtils.get(groupInfo, "pro_medic_mater") !=null){
            drgDipResultDTO.setStandProMedicMater(MapUtils.get(groupInfo, "pro_medic_mater").toString());
        }
        if(MapUtils.get(groupInfo, "pro_consum") !=null){
            drgDipResultDTO.setStandProConsum(MapUtils.get(groupInfo, "pro_consum").toString());
        }
        if(MapUtils.get(groupInfo, "score_price") !=null){
            drgDipResultDTO.setScorePrice(BigDecimalUtils.convert(MapUtils.get(groupInfo, "score_price").toString()).setScale(2));
        }
        List<DrgDipResultDetailDTO> drgDipResultDetailDTOList = FastJsonUtils.fromJsonArray(JSONArray.toJSONString(qualityInfo),DrgDipResultDetailDTO.class);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("hospCode",MapUtils.get(dataMap, "hospCode"));
        resultMap.put("drgDipResultDTO",drgDipResultDTO);
        resultMap.put("drgDipResultDetailDTOList",drgDipResultDetailDTOList);
        DrgDipResultDTO drgDipResultDTO1 = drgDipResultService.insertDrgDipResult(resultMap).getData();
        return drgDipResultDTO1;
    }

    //保存去除空字符串
    private Map<String, Object> deleteConstantBar(Map<String, Object> dataMap){
        for (String key : dataMap.keySet()) {
            if ("".equals(MapUtils.get(dataMap, key))) {
                dataMap.put(key, null);
            }
        }
        return dataMap;
    }

    /**
     * @Description  校验中医诊断与西医诊断的匹配
     * 2、在住院医保结算清单保存的时候，对该医院进行校验，判断出院中医诊断的主诊断编码是否在1维护的中医诊断范围；
     *
     * 2.1、如果在范围内(比如为：急性腰扭伤/闪腰A03.06.04.08)，根据1维护的对照关系，获取对应的西医诊断的编码（可能有多个，比如：腰部扭伤 S33.501、腰椎扭伤和劳损 S33.500、腰椎扭伤 S33.502、腰骶关节扭伤 S33.500x011），再校验结算清单中出院西医诊断的主诊断，是否在其中。
     *
     * 2.1.1、如果在其中，则将HI_PAYMTD的值修改为60保存并上传。
     *
     * 2.1.2、如果不在其中，则提示“中医主要诊断急性腰扭伤/闪腰A03.06.04.08)符合《中医优势住院病种分值表》，西医主要诊断不符合。该中医诊断映射的西医诊断有“腰部扭伤 S33.501、腰椎扭伤和劳损 S33.500、腰椎扭伤 S33.502、腰骶关节扭伤 S33.500x011，请根据映射关系修改西医主诊断编码和名称””
     *
     * 2.2、如果不存在1维护的中医诊断范围，按照正常的业务流程走。
     * @Author 产品三部-郭来
     * @Date 2022-07-19 10:44
     * @param map
     * @return void
     */
    public Map chkTcmDiseMatch(Map map){

        Map<String, Object> sysMap = new HashMap<>();
        sysMap.put("hospCode",MapUtil.getStr(map,"hospCode"));
        sysMap.put("code","CHK_TCMDISE_MATCH");
        WrapperResponse<SysParameterDTO> response = sysParameterService_consumer.getParameterByCode(sysMap);
        if (WrapperResponse.SUCCESS != response.getCode()) {
            throw new AppException(response.getMessage());
        }
        //1.开关配置  1:开启   0：关闭
        if (ObjectUtil.isEmpty(response.getData()) || !Constants.SF.S.equals(response.getData().getValue())) {
            return new HashMap();
        }
        //非住院业务不校验
        if (!Constants.SF.S.equals(MapUtil.getStr(map,"isHospital"))) {
            return new HashMap();
        }
        Map setlInfoMap = MapUtil.get(map, "setlinfo", Map.class);
        //是否按中医优势病种支付方式进行保存   1;是    0：否
        String tcmAdvPayFlag = MapUtil.getStr(map, "tcmAdvPayFlag");
        //医保支付方式
        String hiPaymtd = "";
        if (ObjectUtil.isNotEmpty(setlInfoMap)) {
            hiPaymtd = MapUtil.getStr(setlInfoMap, "hiPaymtd");
        }
        //中医主诊断编码
        String tcmMainCode = "";
        //中医主诊断名称
        String tcmMainName = "";
        //西医主诊断编码
        String wmMainCode = "";
        //西医主诊断名称
        String wmMainName = "";
        Map<String, Object> diseaseMap = MapUtils.get(map, "diseinfo");
        if (MapUtils.isEmpty(diseaseMap)) {
            log.info("中医病种分值校验—未获取到诊断信息");
            return new HashMap();
        }
        List<Map<String, Object>> zxCollect = MapUtils.get(diseaseMap, "zxCollect");
        List<Map<String, Object>> xiCollect = MapUtils.get(diseaseMap, "xiCollect");
        if (ObjectUtil.isEmpty(zxCollect)) {
            log.info("中医病种分值校验—未获取到中医诊断信息");
            return new HashMap();
        }
        if (ObjectUtil.isEmpty(xiCollect)) {
            log.info("中医病种分值校验—未获取到西医诊断信息");
            return new HashMap();
        }
        //中医主诊断
        for (Map<String, Object> objectMap : zxCollect) {
            if (Constants.SF.S.equals(MapUtil.getStr(objectMap,"isMain"))) {
                    tcmMainCode = MapUtil.getStr(objectMap,"diseaseCode");
                    tcmMainName = MapUtil.getStr(objectMap,"diseaseName");
            }
        }

        //西医主诊断
        for (Map<String, Object> objectMap : xiCollect) {
            if (Constants.SF.S.equals(MapUtil.getStr(objectMap,"isMain"))) {
                wmMainCode = MapUtil.getStr(objectMap,"diseaseCode");
                wmMainName = MapUtil.getStr(objectMap,"diseaseName");
            }
        }
        if (StringUtils.isEmpty(tcmMainCode)) {
            log.info("中医病种分值校验—未获取到中医主诊断信息");
            return new HashMap();
        }
        if (StringUtils.isEmpty(wmMainCode)) {
            log.info("中医病种分值校验—未获取到西医主诊断信息");
            return new HashMap();
        }
        //2.判断出院中医诊断的主诊断编码是否在1维护的中医诊断范围；
        List<TcmDiseScoreDO> tcmDiseScoreDOS = insureGetInfoDAO.queryByTcmDiseCode(tcmMainCode);
        //2.2、如果不存在1维护的中医诊断范围，按照正常的业务流程走。
        if (CollectionUtils.isEmpty(tcmDiseScoreDOS)) {
            log.info("中医病种分值校验—中医主诊断"+tcmMainName+tcmMainCode+"不在维护的中医诊断范围内");
            return new HashMap();
        }

        StringBuilder builder = new StringBuilder();
        List<String> wmDiseList = new ArrayList<>();
        for (TcmDiseScoreDO tcmDiseScoreDO : tcmDiseScoreDOS) {
            wmDiseList.add(tcmDiseScoreDO.getWmDiseCode());
            builder.append(tcmDiseScoreDO.getWmDiseName())
                    .append(tcmDiseScoreDO.getWmDiseCode())
                    .append(";");
        }
        if (CollectionUtils.isEmpty(wmDiseList)) {
            throw new AppException("未查询到中医主诊断"+tcmMainName+tcmMainCode+"对应的西医诊断信息,请先维护！");
        }
        //如果选了中医优势病种支付方式但不满足条件的，抛出提示信息
        if (!wmDiseList.contains(wmMainCode) && hiPaymtd_tcm.equals(hiPaymtd)) {
            throw new AppException("当前医保支付方式为中医优势病种，中医主要诊断【"+tcmMainName+tcmMainCode+"】符合《中医优势住院病种分值表》，" +
                    "但西医主要诊断"+wmMainName+wmMainCode+"不符合。该中医诊断映射的西医诊断有【"+builder.toString()+"】，如果选择继续以中医优势病种支付方式保存，请修改西医诊断为对应的诊断，否则，请将医保支付方式修改为其他方式，再进行保存");
        }

        if (!wmDiseList.contains(wmMainCode) && !hiPaymtd_tcm.equals(hiPaymtd)
                && (ObjectUtil.isEmpty(tcmAdvPayFlag) || Constants.SF.F.equals(tcmAdvPayFlag))) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("showTip","1");
            resultMap.put("message","中医主要诊断【"+tcmMainName+tcmMainCode+"】符合《中医优势住院病种分值表》，" +
                    "但西医主要诊断"+wmMainName+wmMainCode+"不符合，并且医保支付方式未选择中医优势病种结算，该中医诊断映射的西医诊断有【"+builder.toString()+
                    "】，请选择是否按中医优势病种结算支付方式进行保存？如果选择是，请修改西医诊断为对应的诊断，并修改医保支付方式为中医优势病种结算，再进行保存");
            return resultMap;
        }
        //如果满足条件但没有选择中医优势病种支付方式的，抛出提示信息
        if (wmDiseList.contains(wmMainCode) && !hiPaymtd_tcm.equals(hiPaymtd) && ObjectUtil.isEmpty(tcmAdvPayFlag)) {
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("showTip","1");
                resultMap.put("message","该结算清单符合中医优势病种结算条件，是否需要按中医优势病种结算支付方式进行保存？" +
                        "如果选择是，请修改医保支付方式为中医优势病种结算，再进行保存");
                return resultMap;
        }
        return new HashMap();
    }

    /**
     * @Method selectItemInfo
     * @Desrciption 查询医保区划
     * @Param
     * @Author fuhui
     * @Date 2021/11/3 15:13
     * @Return
     **/
    public List<Map<String, Object>> queryAdmdvs(Map<String, Object> map) {
        if(ObjectUtil.isEmpty(map.get("upAdmdvsCode"))){
            map.put("admdvsCode","0000");
        }
        String key = "admdvs_code_" + map.get("hospCode") + "_" + map.get("upAdmdvsCode");
        if (redisUtils.hasKey(key)) {
            return redisUtils.get(key);
        }
        List<Map<String, Object>> list = insureGetInfoDAO.queryAdmdvs(map);
        redisUtils.set(key,list);
        return list;
    }
}
