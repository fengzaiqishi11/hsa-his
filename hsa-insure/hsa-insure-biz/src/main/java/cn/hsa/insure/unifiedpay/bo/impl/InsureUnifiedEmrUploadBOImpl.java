package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.enums.FunctionEnum;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.BaseReqUtilFactory;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.InsureUnifiedCommonUtil;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.inpt.bo.InsureUnifiedEmrUploadBO;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualSettleDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dao.InsureUnifiedEmrUploadDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.module.mris.mrisHome.dto.MrisBaseInfoDTO;
import cn.hsa.module.mris.mrisHome.entity.MrisCostDO;
import cn.hsa.module.mris.tcmMrisHome.dto.TcmMrisBaseInfoDTO;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.oper.operInforecord.entity.OperInfoRecordDO;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.prescribe.service.OutptDoctorPrescribeService;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import javax.annotation.Resource;
import java.util.*;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.bo.impl
 * @class_name: InsureUnifiedEmrUploadBOImpl
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/4/27 10:09
 * @Company: 创智和宇
 **/
@Slf4j
@Component
public class InsureUnifiedEmrUploadBOImpl extends HsafBO implements InsureUnifiedEmrUploadBO {

    @Resource
    private OutptDoctorPrescribeService doctorPrescribeService_consumer;

    @Resource
    private InsureIndividualVisitDAO insureIndividualVisitDAO;

    @Resource
    private InsureIndividualSettleDAO insureIndividualSettleDAO;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    @Resource
    private InsureUnifiedCommonUtil insureUnifiedCommonUtil;

    @Resource
    private InsureUnifiedEmrUploadDAO  insureUnifiedEmrUploadDAO;

    @Resource
    private BaseReqUtilFactory baseReqUtilFactory;
    @Resource
    private InsureItfBOImpl insureItfBO;
    /**
     * @Method updateInsureUnifiedElec
     * @Desrciption   1.1.1.1电子处方上传   7101
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/27 11:03
     * @Return
    *
     * @return*/
    public Boolean updateInsureUnifiedPrescrib(Map<String,Object>map){
        String hospCode = MapUtils.get(map,"hospCode");
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(map);
        String orgCode = insureIndividualVisitDTO.getInsureOrgCode();
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
        Map<String,Object> dataMap = insureUnifiedDataMap(map);
        Map<String,Object>  detailMap = insureUnifiedDetailMap(map);
        Map<String,Object> mdtrtInfoMap = insureUnifiedMdtrtInfo(map);
        Map<String,Object> diseinfoMap = insureUnifiedDiaseInfo(map);
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("data",dataMap);
        paramMap.put("rxdrugdetail",detailMap);
        paramMap.put("mdtrtinfo",mdtrtInfoMap);
        paramMap.put("diseinfo",diseinfoMap);
        Map<String, Object> resultMap = commonInsureUnified(hospCode, orgCode, Constant.UnifiedPay.REGISTER.UP_7101, paramMap);
        Map<String,Object> resultDataMap  = MapUtils.get(resultMap,"data");
        String hirXno = MapUtils.get(resultDataMap,"hi_rxno");
        return true;

    }

    /**
     * @Method commonGetVisitInfo
     * @Desrciption  医保统一支付：住院结算，统一患者就诊信息查询接口
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/2/14 10:57
     * @Return InsureIndividualVisitDTO
     **/
    public  InsureIndividualVisitDTO commonGetVisitInfo(Map<String, Object> map){
        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        String hospCode = (String) map.get("hospCode");//医院编码
        String visitId = (String) map.get("visitId");//就诊id
        Map<String, Object> insureVisitParam = new HashMap<String, Object>();
        insureVisitParam.put("id", visitId);
        insureVisitParam.put("hospCode", hospCode);
        insureIndividualVisitDTO = insureIndividualVisitDAO.getInsureIndividualVisitById(insureVisitParam);
        if (insureIndividualVisitDTO == null || StringUtils.isEmpty(insureIndividualVisitDTO.getId())) {
            throw new AppException("未查找到医保就诊信息，请做医保登记。");
        }
        return insureIndividualVisitDTO;
    }

    private Map<String, Object> insureUnifiedDiaseInfo(Map<String, Object> map) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map,"insureIndividualVisitDTO");
        String hospCode = insureIndividualVisitDTO.getHospCode();
        String visitId = insureIndividualVisitDTO.getVisitId();
        String medicalRegNo = insureIndividualVisitDTO.getMedicalRegNo();
        String psnNo = insureIndividualVisitDTO.getAac001();
        OutptPrescribeDTO outptPrescribeDTO =new OutptPrescribeDTO();
        outptPrescribeDTO.setHospCode(hospCode);
        outptPrescribeDTO.setVisitId(visitId);
        map.put("hospCode",hospCode);
        List<OutptDiagnoseDTO> data = doctorPrescribeService_consumer.getOutptDiagnose(map).getData();
        Map<String, Object> diseinfoMap = null;
        Integer count =0;
        List<Map<String,Object>> mapListl = new ArrayList<>();
        if(!ListUtils.isEmpty(data)){
            for(OutptDiagnoseDTO outptDiagnoseDTO : data){
                diseinfoMap  = new HashMap<>();
                diseinfoMap.put("diag_type",outptDiagnoseDTO.getTypeCode());//	诊断类别
                diseinfoMap.put("maindiag_flag",outptDiagnoseDTO.getIsMain());//	主诊断标志
                diseinfoMap.put("diag_srt_no",count++);//	诊断排序号
                diseinfoMap.put("diag_code",outptDiagnoseDTO.getDiseaseCode());//	诊断代码
                diseinfoMap.put("diag_name",outptDiagnoseDTO.getDiseaseName());//	诊断名称
                diseinfoMap.put("adm_cond",null);//	入院病情
                diseinfoMap.put("diag_dept",outptDiagnoseDTO.getDeptName());//	诊断科室
                diseinfoMap.put("dise_dor_no",outptDiagnoseDTO.getDoctorId());//	诊断医生编码
                diseinfoMap.put("dise_dor_name",outptDiagnoseDTO.getDoctorName());//	诊断医生姓名
                diseinfoMap.put("diag_time", DateUtils.format(outptDiagnoseDTO.getCrteTime(),DateUtils.Y_M_DH_M_S));//	诊断时间
                mapListl.add(diseinfoMap);
            }
        }
        map.put("mapListl",mapListl);
        return map;
    }

    /**
     * @Method insureUnifiedMdtrtInfo
     * @Desrciption  输入-门诊信息（节点标识：mdtrtinfo）
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/27 11:19
     * @Return
    **/
    private Map<String, Object> insureUnifiedMdtrtInfo(Map<String, Object> map) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map,"insureIndividualVisitDTO");
        map.put("mdtrt_id",insureIndividualVisitDTO.getMedicalRegNo()); // 就诊ID
        map.put("med_type",insureIndividualVisitDTO.getAka130()); // 医疗类别
        map.put("ipt_op_no",insureIndividualVisitDTO.getVisitNo()); // 住院/门诊号
        map.put("psn_no",insureIndividualVisitDTO.getAac001()); // 人员编号
        map.put("patn_name",insureIndividualVisitDTO.getAac003()); // 患者姓名
        map.put("age",insureIndividualVisitDTO.getAge()); // 年龄
        map.put("patn_ht","");
        map.put("patn_wt","");
        map.put("gend",insureIndividualVisitDTO.getAac004()); // 性别
        map.put("geso_val","");
        map.put("nwb_flag","");
        map.put("nwb_age","");
        map.put("suck_prd_flag","");
        map.put("algs_his","");
        map.put("insuplc_admdvs",insureIndividualVisitDTO.getInsuplcAdmdvs()); // 参保地医保区划
        map.put("psn_cert_type",insureIndividualVisitDTO.getMdtrtCertType()); // 人员证件类型
        map.put("certno",insureIndividualVisitDTO.getMdtrtCertNo()); // 证件号码
        map.put("insutype",insureIndividualVisitDTO.getAae140()); // 险种类型
        map.put("prsc_dept_name",insureIndividualVisitDTO.getVisitIcdName());
        map.put("prsc_dept_code",insureIndividualVisitDTO.getVisitDrptId());
        map.put("prsc_dr_name",insureIndividualVisitDTO.getDoctorName());
        map.put("prsc_dr_cert_type","");
        map.put("prsc_dr_certno","");
        map.put("dr_profttl_codg","");
        map.put("dr_profttl_name","");
        map.put("phar_cert_type","");
        map.put("phar_certno","");
        map.put("phar_name","");
        map.put("phar_prac_cert_no","");
        map.put("phar_chk_time","");
        map.put("mdtrt_time","");
        map.put("dise_codg","");
        map.put("dise_name","");
        map.put("sp_dise_flag","");
        map.put("diag_code","");
        map.put("diag_name","");
        map.put("dise_cond_dscr","");
        map.put("hi_feesetl_type","");
        map.put("hi_feesetl_name","");
        map.put("rgst_fee","");
        map.put("medfee_sumamt","");
        map.put("fstdiag","");
        return map;
    }

    /**
     * @Method insureUnifiedDetailMap
     * @Desrciption  输入-处方明细信息（节点标识：rxdrugdetail）
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/27 11:12
     * @Return
     **/
    private Map<String, Object> insureUnifiedDetailMap(Map<String, Object> map) {
        String hospCode = MapUtils.get(map,"hospCod");
        String visitId = MapUtils.get(map,"visitId");
        OutptPrescribeDTO outptPrescribeDTO = new OutptPrescribeDTO();
        outptPrescribeDTO.setHospCode(hospCode);
        outptPrescribeDTO.setVisitId(visitId);
        map.put("outptPrescribeDTO",outptPrescribeDTO);
        List<OutptPrescribeDetailsDTO> mapList = doctorPrescribeService_consumer.queryAllPrescribe(map);
        Map<String,Object> detailMap = null;
        if(!ListUtils.isEmpty(mapList)){
            for(OutptPrescribeDetailsDTO item : mapList){
                detailMap.put("med_list_codg",item.getInsureItemCode()); // 医疗目录编码
                detailMap.put("fixmedins_hilist_id",item.getItemId()); // 定点医药机构目录编号
                detailMap.put("hosp_prep_flag",""); // 医疗机构制剂标志
                detailMap.put("rx_item_type_code",""); // 处方项目分类代码
                detailMap.put("rx_item_type_name",""); // 处方项目分类名称
                detailMap.put("tcmdrug_type_name","");
                detailMap.put("tcmdrug_type_code","");
                detailMap.put("tcmherb_foote","");
                detailMap.put("medn_type_code","");
                detailMap.put("medn_type_name","");
                detailMap.put("main_medc_flag","");
                detailMap.put("urgt_flag","");
                detailMap.put("bas_medn_flag","");
                detailMap.put("bas_medn_flag","");
                detailMap.put("prod_barc","");
                detailMap.put("drug_prodname","");
                detailMap.put("genname_codg","");
                detailMap.put("drug_genname",""); // 药品通用名
                detailMap.put("chemname","");
                detailMap.put("drugstdcode","");
                detailMap.put("drug_dosform","");
                detailMap.put("drug_spec","");
                detailMap.put("prdr_name","");
                detailMap.put("drug_pric","");
                detailMap.put("drug_cnt","");
                detailMap.put("drug_cnt_unit","");
                detailMap.put("drug_sumamt","");
                detailMap.put("drug_sumamt","");
                detailMap.put("medc_way_dscr","");
                detailMap.put("medc_starttime","");
                detailMap.put("medc_endtime","");
                detailMap.put("medc_days","");
                detailMap.put("drug_dosunt","");
                detailMap.put("sin_doscnt","");
                detailMap.put("sin_dosunt","");
                detailMap.put("used_frqu_codg","");
                detailMap.put("used_frqu_name","");
                detailMap.put("drug_totlnt","");
                detailMap.put("drug_totlnt_emp","");
                detailMap.put("dise_codg","");
            }
        }
        return map;
    }

    /**
     * @Method insureUnifiedDataMap
     * @Desrciption   输入-处方信息（节点标识：data）
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/27 11:09
     * @Return
     **/
    private Map<String, Object> insureUnifiedDataMap(Map<String, Object> dataMap) {
        dataMap.put("hosp_rxno","");  // 定点医疗机构处方编号
        dataMap.put("init_rxno",""); // 续方的原处方编号
        dataMap.put("rx_type_code","");
        dataMap.put("prsc_time","");
        dataMap.put("rx_drug_nums","");
        dataMap.put("rx_way_codg","");
        dataMap.put("rx_way_name","");
        dataMap.put("rx_freq_codg","");
        dataMap.put("rx_freq_name","");
        dataMap.put("rx_dosunt","");
        dataMap.put("rx_doscnt","");
        dataMap.put("rx_drord_dscr","");
        dataMap.put("valid_days","");
        dataMap.put("valid_end_time","");
        dataMap.put("rept_flag","");
        dataMap.put("max_rept_cnt","");
        dataMap.put("reptd_cnt","");
        dataMap.put("min_inrv_days","");
        dataMap.put("dr_sign_info","");
        dataMap.put("phar_sign_info","");
        dataMap.put("fixmedins_sign_info","");
        dataMap.put("rx_cotn_flag","");
        return dataMap;
    }

    /**
     * @Method updateInsureUnifiedMri
     * @Desrciption  住院病案首页信息上传
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/4/27 14:31
     * @Return
     **/
    public Boolean updateInsureUnifiedMri(Map<String, Object> map){
        String hospCode =MapUtils.get(map,"hospCode");
        InsureIndividualVisitDTO insureIndividualVisitDTO = insureUnifiedCommonUtil.commonGetVisitInfo(map);
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);


        Map<String,Object> paramMap = new HashMap<String,Object>();
        // 参保地医保区划
        paramMap.putAll(map);

        paramMap.put("orgCode", insureIndividualVisitDTO.getMedicineOrgCode());
        paramMap.put("configCode", insureIndividualVisitDTO.getInsureRegCode());
        paramMap.put("configRegCode", insureIndividualVisitDTO.getInsureRegCode());

        //参数校验,规则校验和请求初始化
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.INSUR_MRI_UPLOAD.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(hospCode);
        interfaceParamDTO.setIsHospital(Constants.SF.F);
        interfaceParamDTO.setVisitId(insureIndividualVisitDTO.getVisitId());

        insureItfBO.executeInsur(FunctionEnum.INSUR_MRI_UPLOAD, interfaceParamDTO);

        //病案首页类型
        String mrisPageType = MapUtil.getStr(map, "mrisPageType");
        //中医病案首页
        if ("1".equals(mrisPageType)) {
            TcmMrisBaseInfoDTO tcmmrisBaseInfoDTO = MapUtils.get(map, "tcmMrisBaseInfo");
            // 上传完医保以后  更新病案首页表数据 标注为已经上传
            insureUnifiedEmrUploadDAO.updateTcmEmrBaseInfo(tcmmrisBaseInfoDTO);
        //西医病案首页
        }else {
            MrisBaseInfoDTO mrisBaseInfoDTO = MapUtils.get(map,"mrisBaseInfoDTO");
            // 上传完医保以后  更新病案首页表数据 标注为已经上传
            insureUnifiedEmrUploadDAO.updateEmrBaseInfo(mrisBaseInfoDTO);
        }


//        String orgCode = insureIndividualVisitDTO.getInsureOrgCode();
//        StringBuffer stringBuffer = new StringBuffer();
//        String mdtrtSn = stringBuffer.append(insureIndividualVisitDTO.getMedicineOrgCode()).append(insureIndividualVisitDTO.getMedicalRegNo()).toString();
//        //  输入-基本信息（节点标识：baseinfo）
//        Map<String,Object> baseinfoMap = queryEmcBaseInfo(map,mdtrtSn);
//        // 病案首页流水号
//        MrisBaseInfoDTO mrisBaseInfoDTO = MapUtils.get(map,"mrisBaseInfoDTO");
//        // 输入-诊断信息（节点标识：diseinfo）
//        String mid = mrisBaseInfoDTO.getId();
//        Map<String,Object> diseaseInfoMap = queryDiseaseInfo(map,mid,mdtrtSn);
//        // 输入-手术记录（节点标识：oprninfo）
//        Map<String,Object> operationMap = queryOperationInfo(map,mid,mdtrtSn);
//        //  输入-重症监护信息（节点标识：icuinfo）
//        Map<String,Object> icuinfoMap = queryIcuinInfo(map,mid,mdtrtSn);
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("baseinfo",baseinfoMap);
//        paramMap.put("diseinfo",MapUtils.get(diseaseInfoMap,"mrisDiagnoseList"));
//        paramMap.put("oprninfo",MapUtils.get(operationMap,"oprationMapList"));
//        paramMap.put("icuinfo",MapUtils.get(icuinfoMap,"icuinfoMapList"));
//        map.put("msgName","住院病案首页上传");
//        map.put("isHospital",insureIndividualVisitDTO.getIsHospital());
//        map.put("visitId",insureIndividualVisitDTO.getVisitId());
//        insureUnifiedCommonUtil.commonInsureUnified(hospCode,orgCode,Constant.UnifiedPay.INPT.UP_4401,paramMap,map);
//        // 上传完医保以后  更新病案首页表数据 标注为已经上传
//        insureUnifiedEmrUploadDAO.updateEmrBaseInfo(mrisBaseInfoDTO);
        return true;
    }

    /**
     * @Method  commonInsureUnified
     * @Desrciption 调用统一支付平台公共接口方法
     * @Param  hospCode:医院编码
     *         orgCode:医保机构编码
     *         functionCode：功能号
     *         paramMap:入参
     *
     * @Author fuhui
     * @Date   2021/4/28 19:51
     * @Return
     **/
    private Map<String,Object> commonInsureUnified(String hospCode,String orgCode, String functionCode, Map<String, Object> paramMap) {
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(orgCode);
        insureConfigurationDTO.setIsValid(Constants.SF.S);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        Map httpParam = new HashMap();
        httpParam.put("infno", functionCode);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        httpParam.put("input",paramMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("调用功能号【"+functionCode+"】的入参为"+json);
        String resultStr = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
        logger.info("调用功能号【"+functionCode+"】的反参为"+resultStr);
        logger.info("医保统一支付平台入院办理回参:" + resultStr);
        if (StringUtils.isEmpty(resultStr)){
            throw new RuntimeException("调用统一支付平台无响应!");
        }

        Map<String, Object> m = (Map) JSON.parse(resultStr);
        String resultCode = MapUtils.get(m,"infcode","");
        if (StringUtils.isEmpty(resultCode)){
            throw new RuntimeException("调用统一支付平台无响应!");
        }
        if (!"0".equals(resultCode)){
            throw new RuntimeException("调用统一支付平台错误,原因："+MapUtils.get(m,"err_msg",""));
        }
        return m;
    }


    /**
     * @Method queryAdminfoInfo
     * @Desrciption  医保统一支付平台：电子病历上传 入院信息
     *          入院记录的部分信息：由于是护士填写的 所以需要从护理记录单抽取数据
     * @Param map
     *
     * @Author liuliyun
     * @Date   2021/8/21 10:59
     * @Return
     **/
    private Map<String, Object> queryAdminfoInfo(Map<String, Object> map) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map,"insureIndividualVisitDTO");
        InptVisitDTO inptVisit = MapUtils.get(map,"inptVisitDTO");
        String medicalRegNo = insureIndividualVisitDTO.getMedicalRegNo();
        Map<String, Object> detailMap = new HashMap<>();
        detailMap.put("mdtrt_sn",inptVisit.getId()); // 就医流水号
        detailMap.put("mdtrt_id",medicalRegNo); // 医保就诊ID（医保必填）
        detailMap.put("psn_no",inptVisit.getInsureNo()); // 人员编号(医保必填)
        detailMap.put("mdtrtsn",inptVisit.getInNo()); // 住院号
        detailMap.put("name",inptVisit.getName()); // 姓名
        detailMap.put("gend",inptVisit.getGenderCode()); // 性别
        detailMap.put("age",inptVisit.getAge()); // 年龄
        detailMap.put("adm_rec_no",inptVisit.getInNo()); // 入院记录流水号
        detailMap.put("wardarea_name",inptVisit.getWardName()); // 病区名称
        detailMap.put("dept_code",inptVisit.getNationCode()); // 科室编码
        detailMap.put("dept_name",inptVisit.getInDeptName()); // 科室名称
        detailMap.put("bedno",inptVisit.getBedName()); // 病床号
        detailMap.put("adm_time",DateUtils.format(inptVisit.getInTime(),DateUtils.Y_M_DH_M_S)); // 入院时间
        String zgPracCertiNo =  inptVisit.getOutptPracCertiNo();
        String outptDoctorName = inptVisit.getOutptDoctorName();
        String zzPracCertiNo = inptVisit.getZzPracCertiNo();
        String zzDoctorName = inptVisit.getZzDoctorName();
        detailMap.put("chfpdr_code",zzPracCertiNo); // 主诊医师代码
        detailMap.put("chfpdr_name",zzDoctorName); // 主诊医师姓名
        if(StringUtils.isEmpty(outptDoctorName)){
            detailMap.put("rec_doc_code",zgPracCertiNo); // 接诊医生编号
            detailMap.put("rec_doc_name",zgPracCertiNo); // 接诊医生姓名
        }else{
            detailMap.put("rec_doc_code",zzPracCertiNo); // 接诊医生编号
            detailMap.put("rec_doc_name",zzDoctorName); // 接诊医生姓名
        }
        detailMap.put("ipdr_code",inptVisit.getJzPracCertiNo()); // 住院医师编号
        detailMap.put("ipdr_name",inptVisit.getJzDoctorName()); // 住院医师姓名
        detailMap.put("chfdr_code",inptVisit.getZgPracCertiNo()); // 主任医师编号
        detailMap.put("chfdr_name",inptVisit.getZgDoctorName()); // 主任医师姓名
        detailMap.put("main_symp",inptVisit.getDiseaseName()); // 主要症状
        detailMap.put("adm_rea",inptVisit.getInRemark()); // 入院原因
        detailMap.put("adm_way",inptVisit.getInModeCode()); // 入院途径



        MapUtils.isEmptyErr("illhis_stte_name","入院记录所属病历内容的病史陈述者姓名为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("illhis_stte_rltl","入院记录所属病历内容的陈述者与患者关系代码为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("stte_rele","入院记录所属病历内容的陈述内容是否可靠标识为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("chfcomp","入院记录所属病历内容的主诉为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("dise_now","入院记录所属病历内容的现病史为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("hlcon","入院记录所属病历内容的健康状况为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("dise_his","入院记录所属病历内容的疾病史（含外伤）为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("ifet","入院记录所属病历内容的月经史为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("ifet_his","入院记录所属病历内容的患者传染性标志为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("prev_vcnt","入院记录所属病历内容的传染病史为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("oprn_his","入院记录所属病历内容的手术史为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("bld_his","入院记录所属病历内容的输血史为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("algs_his","入院记录所属病历内容的过敏史为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("psn_his","入院记录所属病历内容的个人史为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("mrg_his","入院记录所属病历内容的婚育史为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("mena_his","入院记录所属病历内容的体格检查 -- 外生殖器检查结果为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("fmhis","入院记录所属病历内容的月经史为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("physexm_tprt","入院记录所属病历内容的家族史为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("physexm_pule","入院记录所属病历内容的体格检查 -- 脉率（次 /mi数字）为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("physexm_vent_frqu","入院记录所属病历内容的体格检查--呼吸频率为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("physexm_systolic_pre","入院记录所属病历内容的体格检查 -- 收缩压 （mmHg）为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("physexm_dstl_pre","入院记录所属病历内容的体格检查 -- 舒张压 （mmHg）为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("physexm_height","入院记录所属病历内容的体格检查--身高（cm）为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("physexm_wt","入院记录所属病历内容的体格检查--体重（kg）为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("physexm_genital_area","入院记录所属病历内容的体格检查 -- 外生殖器检查结果为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("physexm_ordn_stas","入院记录所属病历内容的体格检查 -- 一般状况 检查结果为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("physexm_skin_musl","入院记录所属病历内容的体格检查 -- 皮肤和黏膜检查结果为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("physexm_spef_lymph","入院记录所属病历内容的体格检查 -- 全身浅表淋巴结检查结果为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("physexm_head","入院记录所属病历内容的体格检查 -- 头部及其器官检查结果为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("physexm_neck","入院记录所属病历内容的体格检查 -- 颈部检查结果为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("physexm_chst","入院记录所属病历内容的体格检查 -- 胸部检查结果为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("physexm_abd","入院记录所属病历内容的体格检查 -- 腹部检查结果为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("physexm_finger_exam","入院记录所属病历内容的体格检查 -- 肛门指诊检查结果描述为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("physexm_genital_area","入院记录所属病历内容的体格检查 -- 外生殖器检查结果为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("physexm_spin","入院记录所属病历内容的体格检查 -- 脊柱检查结果为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("physexm_all_fors","入院记录所属病历内容的体格检查 -- 四肢检查结果为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("nersys","入院记录所属病历内容的体格检查 -- 神经系统检查结果为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("spcy_info","入院记录所属病历内容的专科情况为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("asst_exam_rslt","入院记录所属病历内容的辅助检查结果为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("apgr","入院记录所属病历内容的评分值(Apgar)为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("diet_info","入院记录所属病历内容的饮食情况为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("growth_deg","入院记录所属病历内容的发育程度为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("mtl_stas_norm","入院记录所属病历内容的精神状态正常标志为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("slep_info","入院记录所属病历内容的睡眠状况为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("sp_info","入院记录所属病历内容的特殊情况为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("mind_info","入院记录所属病历内容的心理状态为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("nurt","入院记录所属病历内容的营养状态为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("self_ablt","入院记录所属病历内容的自理能力为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("nurscare_obsv_item_name","入院记录所属病历内容的护理观察项目名称为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("nurscare_obsv_rslt","入院记录所属病历内容的护理观察结果为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("smoke","入院记录所属病历内容的吸烟标志为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("stop_smok_days","入院记录所属病历内容的停止吸烟天数为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("smok_info","入院记录所属病历内容的吸烟状况为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("smok_day","入院记录所属病历内容的日吸烟量（支）为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("drnk","入院记录所属病历内容的饮酒标志为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("drnk_frqu","入院记录所属病历内容的饮酒频率为空,请先确认好是否匹配好元素,或者是否填写");
        MapUtils.isEmptyErr("drnk_day","入院记录所属病历内容的日饮酒量（mL）为空,请先确认好是否匹配好元素,或者是否填写");
        detailMap.put("resp_nurs_name",inptVisit.getRespNurseName()); // 责任护士姓名
        detailMap.put("vali_flag",Constants.SF.S); // 有效标志
        return detailMap;
    }

    /**
     * @Method updateInsureUnifiedEmr
     * @Desrciption   4701
     *     1.上传电子病历到医保的时候需要先判断病人是否有就诊信息，且是医保患者
     *     2.患者是否已经出院结算状态
     *     3.电子病历是否有书写记录
     *     4.电子病历元素内容是否和医保上传的节点信息做了匹配工作（因为节点字段不一样，所以需要匹配）
     *     5.上传医保
     *     6.更新上传标志
     * @Param
     * @Author liuliyun
     * @Date   2021/8/21 11:18
     * @return*/
    public Boolean updateInsureUnifiedEmr(Map<String,Object>map){
        String hospCode = MapUtils.get(map,"hospCode");
        // 1.先验证住院信息
        InptVisitDTO inptVisitDTO =  insureIndividualVisitDAO.selectInptVisit(map);
        if(inptVisitDTO == null){
            throw new AppException("未获取到住院信息");
        }
        // 2.需要先判断病人是否是医保患者
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(map);
        inptVisitDTO.setInsureRegCode(insureIndividualVisitDTO.getInsureRegCode());
        // 3.患者是否已经出院结算状态
        checkIsSettleAndOutHospital(insureIndividualVisitDTO);
        String orgCode = insureIndividualVisitDTO.getInsureOrgCode();
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
        Map<String,Object>  adminfoInfo = queryAdminfoInfo(map); // 入院信息
        Map<String,Object>  diseInfoList= queryDiagnoseInfo(map); // 诊断信息
        List<Map<String,Object>>  coursrinfoList = queryEmrCoursrInfo(map,inptVisitDTO); // 病程记录信息
        List<Map<String,Object>> operationInfoList = queryEmrOperationInfo(map); // 手术信息
        List<Map<String,Object>>  rescInfoList = queryEmrRescInfo(map,inptVisitDTO); // 病情抢救信息
        List<Map<String,Object>>  dieInfoList = queryEmrDieInfo(map,inptVisitDTO); // 病情抢救记录
        List<Map<String,Object>> dscgoInfoList = queryEmrDscgoInfo(map,inptVisitDTO); // 出院小结
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("adminfoInfo",adminfoInfo);
        paramMap.put("diseinfo",diseInfoList.get("diagnoseList"));
        paramMap.put("coursrinfo",coursrinfoList);
        paramMap.put("oprninfo",operationInfoList);
        paramMap.put("rescInfo",rescInfoList);
        paramMap.put("dieInfo",dieInfoList);
        paramMap.put("dscgoInfo",dscgoInfoList);
        // 调用医保上传接口
        commonInsureUnified(hospCode, orgCode, Constant.UnifiedPay.REGISTER.UP_4701, paramMap);
        // 上传成功以后 更新上传标识
        insureUnifiedEmrUploadDAO.updateEmrPatientFlag(inptVisitDTO);
        return true;

    }
    
    /**
     * @Method checkIsSettleAndOutHospital
     * @Desrciption  验证患者是否已经出院结算
     * @Param 
     * 
     * @Author fuhui
     * @Date   2022/2/23 9:33 
     * @Return 
    **/
    private void checkIsSettleAndOutHospital(InsureIndividualVisitDTO insureIndividualVisitDTO) {
        int count = insureIndividualSettleDAO.queryInsureSettle(insureIndividualVisitDTO);
        if(count<1){
            throw new AppException("该患者还未正常结算,不能上传医保电子病历信息");
        }
    }

    /**
     * @Method queryDiagnoseInfo
     * @Desrciption 获取诊断信息
     * @Param
     * @Author liuliyun
     * @Date   2021/8/21 14:52
     * @return*/
    private Map<String, Object> queryDiagnoseInfo(Map<String, Object> map) {
        List<InptDiagnoseDTO> diagnoseDTOS = MapUtils.get(map,"diagnoseDTOS");
        Map<String, Object> diseinfoMap;
        InptDiagnoseDTO diagnoseDTO ;
        List<Map<String,Object>> diagnoseList = new ArrayList<>();
        if(ListUtils.isEmpty(diagnoseDTOS)){
            throw new AppException("未获取到住院诊断信息");
        }
        for(int i=0;i<diagnoseDTOS.size();i++){
            diagnoseDTO=diagnoseDTOS.get(i);
            diseinfoMap  = new HashMap<>();
            String typeCode = diagnoseDTO.getTypeCode();
            if("201".equals(typeCode)){
                diseinfoMap.put("inout_diag_type","1");//	出入院诊断类别  1.代表入院诊断  2 代表出院诊断
            }else{
                diseinfoMap.put("inout_diag_type","2");//	出入院诊断类别  1.代表入院诊断  2 代表出院诊断
            }
            // 云his的入院，和出院是分别有一个主诊断 需要特殊处理
            if("201".equals(typeCode) && Constants.SF.S.equals(diagnoseDTO.getIsMain())){
                diseinfoMap.put("maindiag_flag", "0");//主诊断标志
            }else{
                diseinfoMap.put("maindiag_flag", diagnoseDTO.getIsMain());//主诊断标志
            }
            diseinfoMap.put("diag_seq",i+1);//	诊断序列号
            diseinfoMap.put("diag_time",DateUtils.format(diagnoseDTO.getCrteTime(),DateUtils.Y_M_DH_M_S));//诊断时间
            diseinfoMap.put("wm_diag_code",diagnoseDTO.getInsureInllnessName());//	西医诊断编码
            diseinfoMap.put("wm_diag_name",diagnoseDTO.getInsureInllnessCode());//	西医诊断名称
            diseinfoMap.put("tcm_dise_code","");//	中医病名代码
            diseinfoMap.put("tcm_dise_name","");//	中医病名
            diseinfoMap.put("tcmsymp_code","");//	中医证候代码
            diseinfoMap.put("tcmsymp","");//	中医证候
            diseinfoMap.put("vali_flag",Constants.SF.S);//	有效标志
            diagnoseList.add(diseinfoMap);
        }
        map.put("diagnoseList",diagnoseList);
        return map;
    }
    /**
     * @Method queryOperationInfo
     * @Desrciption 获取手术信息
     * @Param
     * @Author liuliyun
     * @Date   2021/8/21 15:39
     * @return*/
    private List<Map<String, Object>> queryEmrOperationInfo(Map<String, Object> map) {
        List<OperInfoRecordDTO> operInfoRecordInfos = MapUtils.get(map,"operInfoRecordInfos");
        OperInfoRecordDTO operInfoRecordDTO;
        Map<String, Object> operInfoMap = null;
        String rank ="";
        List<Map<String,Object>> operationInfoList = new ArrayList<>();
        if(!ListUtils.isEmpty(operInfoRecordInfos)){
            for(int i=0;i<operInfoRecordInfos.size();i++){
                operInfoRecordDTO=operInfoRecordInfos.get(i);
                operInfoMap  = new HashMap<>();
                operInfoMap.put("oprn_appy_id",operInfoRecordDTO.getId());//	手术申请单号
                operInfoMap.put("oprn_seq",i);//	手术序列号
                operInfoMap.put("blotype_abo",operInfoRecordDTO.getBloodCode());//	血型代码
                operInfoMap.put("oprn_time",DateUtils.format(operInfoRecordDTO.getOperPlanTime(),DateUtils.Y_M_DH_M_S));//手术日期
                operInfoMap.put("oprn_type_code","无"); // 手术分类代码
                operInfoMap.put("oprn_type_name","无");//  手术分类名称
                operInfoMap.put("bfpn_diag_code",""); //术前诊断代码
                operInfoMap.put("bfpn_oprn_diag_name",operInfoRecordDTO.getOperDiseaseAfter()); //术前诊断名称
                operInfoMap.put("bfpn_inhosp_ifet","");//	术前是否发生院内感染
                operInfoMap.put("afpn_diag_code","");//	术后诊断代码
                operInfoMap.put("afpn_diag_name",operInfoRecordDTO.getOperDiseaseAfter());//	术后诊断名称
                operInfoMap.put("sinc_heal_lv",operInfoRecordDTO.getHealCode()); //手术切口愈合等级代码
                operInfoMap.put("sinc_heal_lv_code",""); // 手术切口愈合等级
                operInfoMap.put("back_oprn","");//	是否重返手术（明确定义）
                operInfoMap.put("selv","");//	是否择期
                operInfoMap.put("prev_abtl_medn","");//	是否预防使用抗菌药物
                operInfoMap.put("abtl_medn_days","");//	预防使用抗菌药物天数
                operInfoMap.put("oprn_oprt_code",operInfoRecordDTO.getOperDiseaseIcd9()); //手术操作代码
                operInfoMap.put("oprn_oprt_name",operInfoRecordDTO.getOperDiseaseName()); //手术操作名称
                if("1".equals(rank)){
                    operInfoMap.put("oprn_lv_code","1"); //手术级别代码
                    operInfoMap.put("oprn_lv_name","一级");//手术级别名称
                }
                else if("2".equals(rank)){
                    operInfoMap.put("oprn_lv_code","2"); //手术级别代码
                    operInfoMap.put("oprn_lv_name","二级手术");//手术级别名称
                }
                else if("3".equals(rank)){
                    operInfoMap.put("oprn_lv_code","3"); //手术级别代码
                    operInfoMap.put("oprn_lv_name","三级手术");//手术级别名称
                }
                else if("4".equals(rank)){
                    operInfoMap.put("oprn_lv_code","4"); //手术级别代码
                    operInfoMap.put("oprn_lv_name","四级手术");//手术级别名称
                }
                else{
                    operInfoMap.put("oprn_lv_code","5"); //手术级别代码
                    operInfoMap.put("oprn_lv_name","其他等级");//手术级别名称
                }
                operInfoMap.put("anst_mtd_code",operInfoRecordDTO.getAnaCode());//	麻醉-方法代码
                operInfoMap.put("anst_mtd_name",operInfoRecordDTO.getAnaCode());//	麻醉-方法名称
                operInfoMap.put("anst_lv_code","无");//	麻醉分级代码
                operInfoMap.put("anst_lv_name","无");//	麻醉分级名称
                operInfoMap.put("exe_anst_dept_code","无");//	麻醉执行科室代码
                operInfoMap.put("exe_anst_dept_name","无");//	麻醉执行科室名称
                operInfoMap.put("anst_efft","");// 麻醉效果
                operInfoMap.put("oprn_begntime",operInfoRecordDTO.getCrteTime());//	手术开始时间
                operInfoMap.put("oprn_endtime",operInfoRecordDTO.getOperEndTime());//	手术结束时间
                operInfoMap.put("oprn_asps","");//	麻醉分级名称
                operInfoMap.put("oprn_asps_ifet","");//	无菌手术是否感染
                operInfoMap.put("afpn_info","");//	手术后情况
                operInfoMap.put("oprn_merg","");//	是否手术合并症
                operInfoMap.put("oprn_conc","");//	是否手术并发症
                operInfoMap.put("oprn_anst_dept_code",operInfoRecordDTO.getOperDeptId());//	手术执行科室代码
                operInfoMap.put("oprn_anst_dept_name",operInfoRecordDTO.getOperRoomName());//	手术执行科室名称
                operInfoMap.put("palg_dise","");// 病理检查
                operInfoMap.put("oth_med_dspo",""); //其他医学处置
                operInfoMap.put("out_std_oprn_time",""); //是否超出手术时间
                operInfoMap.put("oprn_oper_name",operInfoRecordDTO.getDoctorName());//	手术者姓名
                operInfoMap.put("oprn_asit_name1",operInfoRecordDTO.getAssistantName1());//	助手I姓名
                operInfoMap.put("oprn_asit_name2",operInfoRecordDTO.getAssistantName2());//	助手Ⅱ姓名
                operInfoMap.put("anst_dr_name",operInfoRecordDTO.getAnaName1());// 麻醉医师姓名
                operInfoMap.put("anst_asa_lv_code","");//麻醉ASA分级代码
                operInfoMap.put("anst_asa_lv_name","");// 麻醉ASA分级名称
                operInfoMap.put("anst_medn_code","");// 麻醉药物代码
                operInfoMap.put("anst_medn_name",""); // 麻醉药物名称
                operInfoMap.put("anst_medn_dos",""); // 麻醉药物剂量
                operInfoMap.put("anst_dosunt","");// 计量单位
                operInfoMap.put("anst_begntime",""); // 麻醉开始时间
                operInfoMap.put("anst_endtime",""); // 麻醉结束时间
                operInfoMap.put("anst_merg_symp_code",""); // 麻醉合并症代码
                operInfoMap.put("anst_merg_symp",""); // 麻醉合并症名称
                operInfoMap.put("anst_merg_symp_dscr",""); // 麻醉合并症描述
                operInfoMap.put("pacu_begntime",""); // 入复苏室时间
                operInfoMap.put("pacu_endtime",""); // 出复苏室时间
                operInfoMap.put("oprn_selv",""); // 是否择期手术
                operInfoMap.put("canc_oprn",""); // 是否择取消手术
                operInfoMap.put("vali_flag",Constants.SF.S); //有效标志
                operationInfoList.add(operInfoMap);
            }
        }

        return operationInfoList;
    }

    /**
     * @Method queryDiagnoseInfo
     * @Desrciption 获取病程记录
     * @Param
     * @Author liuliyun
     * @Date   2021/8/21 14:52
     * @return*/
    private List<Map<String, Object>> queryEmrCoursrInfo(Map<String, Object> map, InptVisitDTO inptVisitDTO) {
        List<Map<String,Object>> emrCoReList  = MapUtils.get(map,"coursrinfo");
        List<Map<String,Object>> resultList = new ArrayList<>();
        if(!ListUtils.isEmpty(emrCoReList)){
            for(Map<String,Object> item : emrCoReList){
                // 科室代码
                item.put("dept",inptVisitDTO.getInDeptId());
                // 科室名称
                item.put("dept_name",inptVisitDTO.getInDeptName());
                // 病区名称
                item.put("wardarea_name",inptVisitDTO.getWardName());
                // 床位号
                item.put("bedno",inptVisitDTO.getBedName());
                MapUtils.checkEmptyErr(item,"dept","病程记录所属病历内容的科室代码为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"dept_name","病程记录所属病历内容的科室名称为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"wardarea_name","病程记录所属病历内容的病区名称为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"bedno","病程记录所属病历内容的病床号为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"chfcomp","病程记录所属病历内容的主诉为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"cas_ftur","病程记录所属病历内容的病例特点为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"dise_evid","病程记录所属病历内容的诊断依据为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"prel_wm_diag_code","病程记录所属病历内容的初步诊断-西医诊断编码为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"prel_tcm_dise_name","病程记录所属病历内容的初步诊断-西医诊断名称为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"finl_wm_diag_code","病程记录所属病历内容的鉴别诊断-西医诊断编码为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"finl_wm_diag_name","病程记录所属病历内容的鉴别诊断-西医诊断名称为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"dise_plan","病程记录所属病历内容的诊疗计划为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"ipdr_code","病程记录所属病历内容的住院医师编号为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"ipdr_name","病程记录所属病历内容的住院医师姓名为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"prnt_doc_name","病程记录所属病历内容的上级医师姓名为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"vali_flag",Constants.SF.S);
            }
        }
        return resultList;
    }

    /**
     * @Method queryEmrRescInfo
     * @Desrciption  医保统一支付平台：电子病历上传 病情抢救记录
     * @Param map
     * @Author liuliyun
     * @Date   2021/8/21 17:05
     * @Return
     **/
    private List<Map<String, Object>> queryEmrRescInfo(Map<String, Object> map, InptVisitDTO inptVisitDTO) {
        List<Map<String,Object>> emrRescReList = MapUtils.get(map,"rescinfo");
        if(!ListUtils.isEmpty(emrRescReList)){
            for(Map<String,Object> item : emrRescReList){
                // 科室代码
                item.put("dept",inptVisitDTO.getInDeptCode());
                // 科室名称
                item.put("dept_name",inptVisitDTO.getInDeptName());
                // 病区名称
                item.put("wardarea_name",inptVisitDTO.getWardName());
                // 床位号
                item.put("bedno",inptVisitDTO.getBedName());
                // 入院诊断名称
                item.put("adm_dise",inptVisitDTO.getInsureIllnessName());
                // 入院诊断编码
                item.put("diag_code",inptVisitDTO.getInsureIllnessCode());
                MapUtils.checkEmptyErr(item,"dept","病情抢救记录所属病历内容的科室代码为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"dept_name","病情抢救记录所属病历内容的科室名称为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"wardarea_name","病情抢救记录所属病历内容的病区名称为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"bedno","病情抢救记录所属病历内容的病床号为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"diag_name","病情抢救记录所属病历内容的诊断名称为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"diag_code","病情抢救记录所属病历内容的诊断代码为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"cond_chg","病情抢救记录所属病历内容的病情变化情况为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"resc_mes","病情抢救记录所属病历内容的抢救措施为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"oprn_oprt_code","病情抢救记录所属病历内容的手术操作代码为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"oprn_oprt_name","病情抢救记录所属病历内容的手术操作名称为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"oprn_oper_part","病情抢救记录所属病历内容的手术及操作目标部位名称为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"itvt_name","病情抢救记录所属病历内容的介入物名称为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"oprt_mtd","病情抢救记录所属病历内容的操作方法为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"oprt_cnt","病情抢救记录所属病历内容的操作次数为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"resc_begntime","病情抢救记录所属病历内容的抢救开始日期时间为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"resc_endtime","病情抢救记录所属病历内容的抢救结束日期时间为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"dise_item_name","病情抢救记录所属病历内容的检查/检验项目名称为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"dise_ccls","病情抢救记录所属病历内容的检查/检验结果为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"dise_ccls_qunt","病情抢救记录所属病历内容的检查/检验定量结果为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"dise_ccls_code","病情抢救记录所属病历内容的检查/检验结果代码为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"mnan","病情抢救记录所属病历内容的注意事项为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"resc_psn_list","病情抢救记录所属病历内容的参加抢救人员名单为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"proftechttl_code","病情抢救记录所属病历内容的专业技术职务类别代码为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"doc_code","病情抢救记录所属病历内容的医师编号为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"dr_name","病情抢救记录所属病历内容的医师姓名为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"vali_flag",Constants.SF.S);
            }
        }
        return  emrRescReList;
    }

    /**
     * @Method queryEmrDieInfo
     * @Desrciption  死亡记录
     * @Param map
     * @Author liuliyun
     * @Date   2021/8/21 17:20
     * @Return
     **/
    private List<Map<String, Object>> queryEmrDieInfo(Map<String, Object> map, InptVisitDTO inptVisitDTO) {
        List<Map<String,Object>> emrDieReList = MapUtils.get(map,"dieinfo");
        if(!ListUtils.isEmpty(emrDieReList)){
            for(Map<String,Object> item : emrDieReList){
                // 科室代码
                item.put("dept",inptVisitDTO.getInDeptCode());
                // 科室名称
                item.put("dept_name",inptVisitDTO.getInDeptName());
                // 病区名称
                item.put("wardarea_name",inptVisitDTO.getWardName());
                // 床位号
                item.put("bedno",inptVisitDTO.getBedName());
                // 入院时间
                item.put("adm_time",DateUtils.format(inptVisitDTO.getInTime(),DateUtils.Y_M_DH_M_S));
                // 入院诊断编码
                item.put("adm_dise",inptVisitDTO.getInsureIllnessCode());
                // 入院情况
                item.put("adm_info",inptVisitDTO.getInSituationName());
                MapUtils.checkEmptyErr(item,"dept","死亡记录所属病历内容的科室代码为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"dept_name","死亡记录所属病历内容的科室名称为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"wardarea_name","死亡记录所属病历内容的病区名称为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"bedno","死亡记录所属病历内容的病床号为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"adm_time","死亡记录所属病历内容的入院时间为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"adm_dise","死亡记录所属病历内容的入院诊断编码为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"adm_info","死亡记录所属病历内容的入院情况为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"trt_proc_dscr","死亡记录所属病历内容的诊疗过程描述为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"die_time","死亡记录所属病历内容的死亡时间为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"die_drt_rea","死亡记录所属病历内容的直接死亡原因名称为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"die_drt_rea_code","死亡记录所属病历内容的直接死亡原因编码为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"die_dise_name","死亡记录所属病历内容的死亡诊断名称为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"die_diag_code","死亡记录所属病历内容的死亡诊断编码为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"agre_corp_dset","死亡记录所属病历内容的家属是否同意尸体解剖标志为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"ipdr_name","死亡记录所属病历内容的住院医师姓名为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"chfpdr_name","死亡记录所属病历内容的主诊医师姓名为空,请先确认好是否匹配好元素,或者是否填写");
                MapUtils.checkEmptyErr(item,"chfdr_name","死亡记录所属病历内容的主任医师姓名为空,请先确认好是否匹配好元素,或者是否填写");
                item.put("sign_time",DateUtils.format(DateUtils.Y_M_DH_M_S));//	签字日期时间
                item.put("vali_flag",Constants.SF.S);//	是否有效
            }
        }
        return emrDieReList;
    }

    /**
     * @Method queryEmrDscgoInfo
     * @Desrciption  出院小结
     * @Param map
     * @Author liuliyun
     * @Date   2021/8/21 17:35
     * @Return
     **/
    private List<Map<String, Object>> queryEmrDscgoInfo(Map<String, Object> map, InptVisitDTO inptVisitDTO) {
        List<Map<String,Object>> emrOutReList = MapUtils.get(map,"dscginfo");
        if (!ListUtils.isEmpty(emrOutReList)){
            for(Map<String,Object> item : emrOutReList){
                // 出院日期
                item.put("dscg_date",DateUtils.format(inptVisitDTO.getOutTime(),DateUtils.Y_M_DH_M_S));
                // 科别
                item.put("caty",inptVisitDTO.getOutptNationCode());
                // 出院诊断
                item.put("dscg_dise_dscr",inptVisitDTO.getInsureIllnessCode());
                //有效标志
                item.put("vali_flag",Constants.SF.S);
            }
        }else{
            throw new AppException("请检查是否书写电子病历内容:出院小结信息等");
        }
        return emrOutReList;
    }

}
