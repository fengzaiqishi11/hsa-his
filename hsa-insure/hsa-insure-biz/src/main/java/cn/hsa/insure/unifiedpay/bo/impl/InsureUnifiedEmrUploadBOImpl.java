package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.base.dept.service.BaseDeptService;
import cn.hsa.module.emr.emrpatientrecord.entity.EmrPatientRecordDO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.inpt.bo.InsureUnifiedEmrUploadBO;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualSettleDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualSettleDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.mris.mrisHome.dto.MrisBaseInfoDTO;
import cn.hsa.module.mris.mrisHome.entity.MrisCostDO;
import cn.hsa.module.mris.mrisHome.entity.MrisDiagnoseDO;
import cn.hsa.module.mris.mrisHome.entity.MrisOperInfoDO;
import cn.hsa.module.mris.mrisHome.service.MrisHomeService;
import cn.hsa.module.oper.operInforecord.entity.OperInfoRecordDO;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.prescribe.service.OutptDoctorPrescribeService;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.util.*;
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

    @Resource
    private MrisHomeService mrisHomeService_consumer;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    @Resource
    private BaseDeptService baseDeptService;

    @Resource
    private RedisUtils redisUtils;
    
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
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(map);
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
        String orgCode = insureIndividualVisitDTO.getInsureOrgCode();
        //  输入-基本信息（节点标识：baseinfo）
        Map<String,Object> baseinfoMap = queryEmcBaseInfo(map);
        // 输入-诊断信息（节点标识：diseinfo）
        Map<String,Object> diseaseInfoMap = queryDiseaseInfo(map);
        // 病案首页流水号
        MrisBaseInfoDTO mrisBaseInfoDTO = MapUtils.get(map,"mrisBaseInfoDTO");
        String mid = mrisBaseInfoDTO.getId();
        // 输入-手术记录（节点标识：oprninfo）
        Map<String,Object> operationMap = queryOperationInfo(map,mid);
        //  输入-重症监护信息（节点标识：icuinfo）
        Map<String,Object> icuinfoMap = queryIcuinInfo(map);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("baseinfo",baseinfoMap);
        paramMap.put("diseinfo",MapUtils.get(diseaseInfoMap,"mapList1"));
        paramMap.put("oprninfo",MapUtils.get(operationMap,"oprationMapList"));
        paramMap.put("icuinfo",MapUtils.get(icuinfoMap,"icuinfoMapList"));
        Map<String,Object> resultMap = commonInsureUnified(hospCode,orgCode,Constant.UnifiedPay.INPT.UP_4401,paramMap);
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
     * @Method queryIcuinInfo
     * @Desrciption 输入-重症监护信息（节点标识：icuinfo）
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/27 14:31
     * @Return
    **/
    private Map<String, Object> queryIcuinInfo(Map<String, Object> map) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map,"insureIndividualVisitDTO");
        MrisCostDO mrisCostDO =  MapUtils.get(map,"mrisCostDO");
        MrisBaseInfoDTO mrisBaseInfoDTO = MapUtils.get(map,"mrisBaseInfoDTO");
        List<Map<String,Object>> mapList = new ArrayList<>();
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("vali_flag",Constants.SF.S);
        paramMap.put("ipt_medcas_hmpg_sn",mrisBaseInfoDTO.getId());
        paramMap.put("mdtrt_sn",insureIndividualVisitDTO.getMedicalRegNo());
        mapList.add(paramMap);
        map.put("icuinfoMapList",mapList);
        return map;
    }

    /**
     * @Method queryOperationInfo
     * @Desrciption
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/27 14:31
     * @Return
    **/
    private Map<String, Object> queryOperationInfo(Map<String, Object> map,String mid) {
        InsureIndividualVisitDTO insureIndividualVisitDTO =MapUtils.get(map,"insureIndividualVisitDTO");
        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        inptVisitDTO.setHospCode(insureIndividualVisitDTO.getHospCode());
        inptVisitDTO.setId(MapUtils.get(map,"visitId"));
        map.put("inptVisitDTO",inptVisitDTO);
        List<Map<String,Object>> oprationMapList = new ArrayList<>();
        Map<String,Object> paramMap = null;
        List<MrisOperInfoDO> operInfoDOList = mrisHomeService_consumer.queryAllOperation(map);
        if(!ListUtils.isEmpty(operInfoDOList)){
            for(MrisOperInfoDO mrisOperInfoDO : operInfoDOList){
                paramMap = new HashMap<>();
                paramMap.put("oprn_oprt_date",mrisOperInfoDO.getOperTime()); // 手术操作日期
                paramMap.put("oprn_oprt_name",mrisOperInfoDO.getOperDiseaseName()); // 手术操作名称
                paramMap.put("oprn_oprt_code",mrisOperInfoDO.getOperDiseaseIcd9()); // 手术操作代码
                paramMap.put("oprn_oprt_sn",mrisOperInfoDO.getColumnsNum()); // 手术操作序列号
                paramMap.put("oprn_lv_code",mrisOperInfoDO.getOperCode()); // 手术级别代码
                paramMap.put("oprn_lv_name",mrisOperInfoDO.getOperName()); // 手术级别名称
                paramMap.put("oper_name",mrisOperInfoDO.getOperDoctorName()); // 手术者姓名
                paramMap.put("asit_1_name",mrisOperInfoDO.getAssistantName1()); // 助手Ⅰ姓名
                paramMap.put("asit_name2",mrisOperInfoDO.getAssistantName2()); // 助手Ⅱ姓名
                paramMap.put("sinc_heal_lv",""); // 手术切口愈合等级
                paramMap.put("sinc_heal_lv_code",mrisOperInfoDO.getHealCode()); // 手术切口愈合等级代码
                paramMap.put("anst_mtd_name",mrisOperInfoDO.getAnaName()); // 麻醉-方法名称
                paramMap.put("anst_mtd_code",mrisOperInfoDO.getAnaCode()); // 麻醉-方法代码
                paramMap.put("anst_dr_name",mrisOperInfoDO.getAnaName1()); // 麻醉医师姓名
                paramMap.put("oprn_oper_part",mrisOperInfoDO.getOperPositionName()); //手术操作部位
                paramMap.put("oprn_oper_part_code",mrisOperInfoDO.getOperPosition()); // 手术操作部位代码
                paramMap.put("oprn_con_time",""); // 手术持续时间
                paramMap.put("anst_lv_name",""); // 麻醉分级名称
                paramMap.put("anst_lv_code",""); // 麻醉分级代码
                paramMap.put("oprn_patn_type",""); // 手术患者类型
                paramMap.put("oprn_patn_type _code",""); //手术患者类型代码
                paramMap.put("main_oprn_flag",""); // 主要手术标志
                paramMap.put("anst_asa_lv_code",""); // 麻醉ASA分级名称
                paramMap.put("anst_asa_lv_name",""); // 麻醉ASA分级名称
                paramMap.put("anst_medn_code",""); // 麻醉药物代码
                paramMap.put("anst_medn_name",""); // 麻醉药物名称
                paramMap.put("anst_medn_dos",""); // 麻醉药物剂量
                paramMap.put("unt",""); //计量单位
                paramMap.put("anst_begntime",""); // 麻醉开始时间
                paramMap.put("anst_endtime",""); //麻醉结束时间
                paramMap.put("anst_copn_code",""); // 麻醉合并症代码
                paramMap.put("anst_copn_name",""); // 麻醉合并症名称
                paramMap.put("anst_copn_dscr",""); // 麻醉合并症描述
                paramMap.put("pacu_begntime",""); // 复苏室开始时间
                paramMap.put("pacu_endtime",""); // 复苏室结束时间
                paramMap.put("canc_oprn_flag",""); //取消手术标志
                paramMap.put("vali_flag",Constants.SF.S); // 有效标志
                paramMap.put("mdtrt_sn",insureIndividualVisitDTO.getMedicalRegNo());  //  就医流水号
                paramMap.put("ipt_medcas_hmpg_sn",mrisOperInfoDO.getMbiId()); // 住院病案首页流水号
                oprationMapList.add(paramMap);
            }
        }else{
            paramMap = new HashMap<>();
            paramMap.put("oprn_oprt_date",""); // 手术操作日期
            paramMap.put("oprn_oprt_name","无"); // 手术操作名称
            paramMap.put("oprn_oprt_code","无"); // 手术操作代码
            paramMap.put("oprn_oprt_sn","无"); // 手术操作序列号
            paramMap.put("oprn_lv_code","无"); // 手术级别代码
            paramMap.put("oprn_lv_name","无"); // 手术级别名称
            paramMap.put("oper_name","无"); // 手术者姓名
            paramMap.put("asit_1_name","无"); // 助手Ⅰ姓名
            paramMap.put("asit_name2","无"); // 助手Ⅱ姓名
            paramMap.put("sinc_heal_lv",""); // 手术切口愈合等级
            paramMap.put("sinc_heal_lv_code","无"); // 手术切口愈合等级代码
            paramMap.put("anst_mtd_name","无"); // 麻醉-方法名称
            paramMap.put("anst_mtd_code","无"); // 麻醉-方法代码
            paramMap.put("anst_dr_name","无"); // 麻醉医师姓名
            paramMap.put("oprn_oper_part","无"); //手术操作部位
            paramMap.put("oprn_oper_part_code","无"); // 手术操作部位代码
            paramMap.put("oprn_con_time",""); // 手术持续时间
            paramMap.put("anst_lv_name",""); // 麻醉分级名称
            paramMap.put("anst_lv_code",""); // 麻醉分级代码
            paramMap.put("oprn_patn_type",""); // 手术患者类型
            paramMap.put("oprn_patn_type _code",""); //手术患者类型代码
            paramMap.put("main_oprn_flag",""); // 主要手术标志
            paramMap.put("anst_asa_lv_code",""); // 麻醉ASA分级名称
            paramMap.put("anst_asa_lv_name",""); // 麻醉ASA分级名称
            paramMap.put("anst_medn_code",""); // 麻醉药物代码
            paramMap.put("anst_medn_name",""); // 麻醉药物名称
            paramMap.put("anst_medn_dos",""); // 麻醉药物剂量
            paramMap.put("unt",""); //计量单位
            paramMap.put("anst_begntime",""); // 麻醉开始时间
            paramMap.put("anst_endtime",""); //麻醉结束时间
            paramMap.put("anst_copn_code",""); // 麻醉合并症代码
            paramMap.put("anst_copn_name",""); // 麻醉合并症名称
            paramMap.put("anst_copn_dscr",""); // 麻醉合并症描述
            paramMap.put("pacu_begntime",""); // 复苏室开始时间
            paramMap.put("pacu_endtime",""); // 复苏室结束时间
            paramMap.put("canc_oprn_flag",""); //取消手术标志
            paramMap.put("vali_flag",Constants.SF.S); // 有效标志
            paramMap.put("mdtrt_sn",insureIndividualVisitDTO.getMedicalRegNo());  //  就医流水号
            paramMap.put("ipt_medcas_hmpg_sn",mid); // 住院病案首页流水号
            oprationMapList.add(paramMap);
        }
        map.put("oprationMapList",oprationMapList);
        return map;
    }

    /**
     * @Method queryDiseaseInfo
     * @Desrciption 住院病案首页信息   -诊断信息（节点标识：diseinfo）
     * @Param 
     * 
     * @Author fuhui
     * @Date   2021/4/27 14:22 
     * @Return 
    **/
    private Map<String, Object> queryDiseaseInfo(Map<String, Object> map) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map,"insureIndividualVisitDTO");
        String medicalRegNo = insureIndividualVisitDTO.getMedicalRegNo();
        String psnNo = insureIndividualVisitDTO.getAac001();
        List<MrisDiagnoseDO> mrisDiagnoseList =  MapUtils.get(map,"mrisDiagnoseList");
        Map<String, Object> diseinfoMap = null;
        List<Map<String,Object>> mapList1 = new ArrayList<>();
        if(!ListUtils.isEmpty(mrisDiagnoseList)){
            for(MrisDiagnoseDO mrisDiagnoseDO : mrisDiagnoseList){
                diseinfoMap  = new HashMap<>();
                diseinfoMap.put("palg_no",mrisDiagnoseDO.getInBlh());//	病理号
                diseinfoMap.put("ipt_patn_disediag_type_code",null);//	住院患者疾病诊断类型代码
                diseinfoMap.put("disediag_type",mrisDiagnoseDO.getDiseaseCode());//	疾病诊断类型
                diseinfoMap.put("maindiag_flag",null);//主诊断标志
                diseinfoMap.put("diag_code",mrisDiagnoseDO.getDiseaseCode());//	诊断代码
                diseinfoMap.put("diag_name",mrisDiagnoseDO.getDiseaseName());//	诊断名称
                diseinfoMap.put("inhosp_diag_code",mrisDiagnoseDO.getDiseaseName());//	诊断名称
                diseinfoMap.put("inhosp_diag_name",mrisDiagnoseDO.getDiseaseName());//	诊断名称
                diseinfoMap.put("adm_dise_cond_name",null);//	入院病情
                diseinfoMap.put("adm_dise_cond_code",null);//	入院疾病病情代码
                diseinfoMap.put("adm_cond",mrisDiagnoseDO.getInSituationCode());//	入院病情
                diseinfoMap.put("adm_cond_code",null);//	入院时病情代码
                diseinfoMap.put("high_diag_evid",null);//	最高诊断依据
                diseinfoMap.put("inhosp_diag_code",null);//	诊断科室
                diseinfoMap.put("inhosp_diag_name",null);//	诊断医生编码
                diseinfoMap.put("adm_dise_cond_name",null);//	诊断医生姓名
                diseinfoMap.put("adm_dise_cond_code", null);//	诊断时间
                diseinfoMap.put("adm_cond_code",null);//	诊断医生编码
                diseinfoMap.put("bkup_deg",null);//	分化程度
                diseinfoMap.put("bkup_deg_code", null);//分化程度代码	分化程度代码分化程度代码
                diseinfoMap.put("vali_flag",Constants.SF.S);//	有效标志
                diseinfoMap.put("ipt_medcas_hmpg_sn",mrisDiagnoseDO.getMbiId());//	住院病案首页流水号
                diseinfoMap.put("mdtrt_sn",medicalRegNo);//	就医流水号
                mapList1.add(diseinfoMap);
            }
        }
        map.put("mapList1",mapList1);
        return map;
    }

    /**
     * @Method queryEmcBaseInfo
     * @Desrciption 住院病案首页信息  ---- 基本信息（节点标识：baseinfo）
     * @Param 
     * 
     * @Author fuhui
     * @Date   2021/4/27 14:18 
     * @Return 
    **/
    private Map<String, Object> queryEmcBaseInfo(Map<String, Object> map) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map,"insureIndividualVisitDTO");
        MrisCostDO mrisCostDO =  MapUtils.get(map,"mrisCostDO");
        MrisBaseInfoDTO mrisBaseInfoDTO = MapUtils.get(map,"mrisBaseInfoDTO");
        Map<String,Object> baseInfoMap = new HashMap<>();
        baseInfoMap.put("mdtrt_sn",insureIndividualVisitDTO.getMedicalRegNo()); // 就医流水号
        baseInfoMap.put("medcasno",mrisBaseInfoDTO.getInProfile()); // 病案号
        baseInfoMap.put("psn_name",mrisBaseInfoDTO.getName()); // 人员姓名
        baseInfoMap.put("vali_flag", Constants.SF.S); // 有效标志
        baseInfoMap.put("medfee_sumamt",mrisCostDO.getFy01());
        return baseInfoMap;
    }


    /**
     * @Method insertInsureEmr
     * @Desrciption  电子病历上传
     * @Param
     *
     * @Author fuhui
     * @Date   2021/5/11 10:28
     * @Return
    **/
    private Boolean insertInsureEmr(Map<String, Object> map){

        Map<String,Object> coursrinInfoMap =  queryCoursrinInfo(map);

        return true;

    }
    
    /**
     * @Method queryCoursrinInfo
     * @Desrciption  医保统一支付平台：电子病历上传 病程记录信息
     * @Param map
     * 
     * @Author fuhui
     * @Date   2021/5/11 10:34 
     * @Return 
    **/
    private Map<String, Object> queryCoursrinInfo(Map<String, Object> map) {

        List<Map<String,Object>> listMap = new ArrayList<>();
        if(!ListUtils.isEmpty(listMap)){

        }
        return  map;
    }

    /**
     * @Method queryAdminfoInfo
     * @Desrciption  医保统一支付平台：电子病历上传 入院信息
     * @Param map
     *
     * @Author liuliyun
     * @Date   2021/8/21 10:59
     * @Return
     **/
    private Map<String, Object> queryAdminfoInfo(Map<String, Object> map) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map,"insureIndividualVisitDTO");
        InptVisitDTO inptVisit = MapUtils.get(map,"inptVisit");
        String medicalRegNo = insureIndividualVisitDTO.getMedicalRegNo();

        if(inptVisit == null) {
            throw new AppException("未查询到患者就诊信息！");
        }
        Map<String, Object> detailMap = new HashMap<>();
        detailMap.put("mdtrt_sn",inptVisit.getId()); // 就医流水号
        detailMap.put("mdtrt_id",medicalRegNo); // 医保就诊ID（医保必填）
        detailMap.put("psn_no",inptVisit.getInsureNo()); // 人员编号(医保必填)
        detailMap.put("mdtrtsn",inptVisit.getInNo()); // 住院号
        detailMap.put("name",inptVisit.getName()); // 姓名
        detailMap.put("gend",inptVisit.getGenderCode()); // 性别
        detailMap.put("age",inptVisit.getAge()); // 年龄
        detailMap.put("adm_rec_no",inptVisit.getInNo()); // 入院记录流水号
        BaseDeptDTO inwardInfo = this.getInwardInfo(inptVisit.getInWardId(), inptVisit.getHospCode());
        if (inwardInfo == null) {
            inwardInfo.setName("住院病区");
        }
        detailMap.put("wardarea_name",inwardInfo.getName()); // 病区名称
        detailMap.put("dept_code",inptVisit.getInDeptId()); // 科室编码
        detailMap.put("dept_name",inptVisit.getInDeptName()); // 科室名称
        detailMap.put("bedno",inptVisit.getBedName()); // 病床号
        detailMap.put("adm_time",inptVisit.getInTime()); // 入院时间
        detailMap.put("rec_doc_code",inptVisit.getOutptDoctorId()); // 接诊医生编号
        detailMap.put("rec_doc_name",inptVisit.getOutptDoctorName()); // 接诊医生姓名
        detailMap.put("ipdr_code",inptVisit.getZzDoctorId()); // 住院医师编号
        detailMap.put("ipdr_name",inptVisit.getZzDoctorName()); // 住院医师姓名
        detailMap.put("chfdr_code",inptVisit.getZgDoctorId()); // 主任医师编号
        detailMap.put("chfdr_name",inptVisit.getZzDoctorName()); // 主任医师姓名
        detailMap.put("chfpdr_code",inptVisit.getJzDoctorId()); // 主诊医师代码
        detailMap.put("chfpdr_name",inptVisit.getJzDoctorName()); // 主诊医师姓名
        detailMap.put("main_symp",inptVisit.getDiseaseName()); // 主要症状
        detailMap.put("adm_rea",inptVisit.getInRemark()); // 入院原因
        detailMap.put("adm_way",inptVisit.getInModeCode()); // 入院途径

        detailMap.put("illhis_stte_name",inptVisit.getName()); // 病史陈述者姓名
        detailMap.put("illhis_stte_rltl","-"); // 陈述者与患者关系代码
        detailMap.put("stte_rele","1"); // 陈述内容是否可靠标识
        detailMap.put("chfcomp","-"); // 主诉
        detailMap.put("dise_now","-"); // 现病史
        detailMap.put("hlcon","-");  // 健康状况
        detailMap.put("dise_his","-"); // 疾病史
        detailMap.put("ifet",""); // 患者传染性标志
        detailMap.put("ifet_his","无"); // 传染病史
        detailMap.put("prev_vcnt","-");  // 预防接种史
        detailMap.put("oprn_his","-");  // 手术史
        detailMap.put("bld_his","-"); // 输血史
        detailMap.put("algs_his","-"); // 过敏史
        detailMap.put("psn_his","-"); // 个人史
        detailMap.put("mrg_his","-"); // 婚育史
        detailMap.put("mena_his","-"); // 月经史
        detailMap.put("fmhis","-"); // 家族史
        detailMap.put("physexm_tprt",0); // 体温体格检查
        detailMap.put("physexm_pule",0); // 体格检查 -- 脉率（次 /mi数字）
        detailMap.put("physexm_vent_frqu","-"); // 呼吸频率
        detailMap.put("physexm_systolic_pre","-"); //体格检查 -- 收缩压 （mmHg）
        detailMap.put("physexm_dstl_pre","-"); // 体格检查 -- 舒张压 （mmHg）
        detailMap.put("physexm_height",0); // 体格检查--身高（cm）
        detailMap.put("physexm_wt",0); // 体格检查--体重（kg）
        detailMap.put("physexm_ordn_stas","-"); // 体格检查 -- 一般状况 检查结果
        detailMap.put("physexm_skin_musl","-"); // 体格检查 -- 皮肤和黏膜检查结果
        detailMap.put("physexm_spef_lymph","-"); // 体格检查 -- 全身浅表淋巴结检查结果
        detailMap.put("physexm_head","-");  // 体格检查 -- 头部及其器官检查结果
        detailMap.put("physexm_neck","-"); // 体格检查 -- 颈部检查结果
        detailMap.put("physexm_chst","-"); // 体格检查 -- 胸部检查结果
        detailMap.put("physexm_abd","-"); // 体格检查 -- 腹部检查结果
        detailMap.put("physexm_finger_exam","-"); // 体格检查 -- 肛门指诊检查结果描述
        detailMap.put("physexm_genital_area","-"); // 体格检查 -- 外生殖器检查结果
        detailMap.put("physexm_spin","-");   // 体格检查 -- 脊柱检查结果
        detailMap.put("physexm_all_fors","-"); // 体格检查 -- 四肢检查结果
        detailMap.put("nersys","-");  // 体格检查 -- 神经系统检查结果
        detailMap.put("spcy_info","-"); // 专科情况
        detailMap.put("asst_exam_rslt","-"); // 辅助检查结果
        detailMap.put("tcm4d_rslt",null); // 中医“四诊”观察结果描述
        detailMap.put("syddclft",null); // 辨证分型代码
        detailMap.put("syddclft_name",null); // 辩证分型名称
        detailMap.put("prnp_trt",null); // 治则治法
        detailMap.put("apgr","-"); // 评分值
        detailMap.put("diet_info","-"); // 饮食情况
        detailMap.put("growth_deg","-"); // 发育情况
        detailMap.put("slep_info","-"); // 睡眠状况
        detailMap.put("sp_info","-"); // 特殊情况
        detailMap.put("mind_info","-"); // 心理状态
        detailMap.put("nurt","-"); // 营养状态
        detailMap.put("self_ablt","-"); // 自理能力
        detailMap.put("nurscare_obsv_item_name","-"); // 护理观察项目名称
        detailMap.put("smoke","-"); // 吸烟标志
        detailMap.put("stop_smok_days",0); // 停止吸烟天数
        detailMap.put("smok_info","-"); // 吸烟状况
        detailMap.put("smok_day",0); // 日吸烟量（支）
        detailMap.put("drnk","0"); // 饮酒标志
        detailMap.put("drnk_frqu","0"); // 饮酒频率
        detailMap.put("drnk_day",0); // 日饮酒量（mL）
        detailMap.put("eval_time",DateUtils.format(DateUtils.Y_M_DH_M_S)); // 评估日期时间
        detailMap.put("resp_nurs_name",inptVisit.getRespNurseName()); // 责任护士姓名
        detailMap.put("vali_flag",Constants.SF.S); // 有效标志

        String redisKey = this.getRedisKey(inptVisit) ;
        Map<String,String> insureEmrInfo = redisUtils.get(redisKey);
        Map<String,Object> adminfoMap = MapUtils.get(insureEmrInfo,"adminfo");

        // 合并，有值的后面会覆盖前面的
        Map<String, Object> combineResultMap = new HashMap();
        combineResultMap.putAll(detailMap);
        combineResultMap.putAll(adminfoMap);
        return combineResultMap;
    }


    private String getRedisKey (InptVisitDTO inptVisit) {
        return inptVisit.getHospCode() + "_" + inptVisit.getVisitId() + "_insureEmrInfo" ;
    }

    /**
     * @Method updateInsureUnifiedEmr
     * @Desrciption   4701
     * @Param
     * @Author liuliyun
     * @Date   2021/8/21 11:18
     * @return*/
    public Boolean updateInsureUnifiedEmr(Map<String,Object>map){
        String hospCode = MapUtils.get(map,"hospCode");
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(map);
        String orgCode = insureIndividualVisitDTO.getInsureOrgCode();
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
        Map<String,Object>  adminfoInfo = queryAdminfoInfo(map); // 入院信息
        Map<String,Object>  diseInfoList= queryDiagnoseInfo(map); // 诊断信息
        List<Map<String,Object>>  coursrinfoList = queryEmrCoursrInfo(map); // 病程记录信息
        List<Map<String,Object>> operationInfoList = queryEmrOperationInfo(map); // 手术信息
        List<Map<String,Object>>  rescInfoList = queryEmrRescInfo(map); // 抢救信息
        List<Map<String,Object>>  dieInfoList = queryEmrDieInfo(map); // 死亡记录
        List<Map<String,Object>> dscgoInfo = queryEmrDscgoInfo(map); // 出院小结

        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("adminfoInfo",adminfoInfo);
        paramMap.put("diseinfo",diseInfoList.get("diagnoseList"));
        paramMap.put("coursrinfo",coursrinfoList);
        paramMap.put("oprninfo",operationInfoList);
        paramMap.put("rescInfo",rescInfoList.get(0));
        paramMap.put("dieInfo",dieInfoList.get(0));
        paramMap.put("dscgoInfo",dscgoInfo.get(0));
        Map<String, Object> resultMap = commonInsureUnified(hospCode, orgCode, Constant.UnifiedPay.REGISTER.UP_4701, paramMap);
        return true;

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
        Map<String, Object> diseinfoMap = null;
        List<Map<String,Object>> diagnoseList = new ArrayList<>();
        if(ListUtils.isEmpty(diagnoseDTOS)){
            throw new AppException("诊断信息不能为空！");
        }

        for(int i=0;i<diagnoseDTOS.size();i++){
            InptDiagnoseDTO diagnoseDTO=diagnoseDTOS.get(i);
            diseinfoMap  = new HashMap<>();
            diseinfoMap.put("inout_diag_type",diagnoseDTO.getTypeCode());//	出入院诊断类别
            if (StringUtils.isEmpty(diagnoseDTO.getIsMain())) {
                diagnoseDTO.setIsMain("0");
            }
            diseinfoMap.put("maindiag_flag", diagnoseDTO.getIsMain());//主诊断标志
            diseinfoMap.put("diag_seq",i+1);//	诊断序列号
            diseinfoMap.put("diag_time",diagnoseDTO.getCrteTime());//诊断时间
            diseinfoMap.put("wm_diag_code",diagnoseDTO.getIcd10());//	西医诊断编码
            diseinfoMap.put("wm_diag_name",diagnoseDTO.getDiseaseName());//	西医诊断名称
            diseinfoMap.put("tcm_dise_code","无");//	中医病名代码
            diseinfoMap.put("tcm_dise_name","无");//	中医病名
            diseinfoMap.put("tcmsymp_code","无");//	中医证候代码
            diseinfoMap.put("tcmsymp","无");//	中医证候
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
        List<OperInfoRecordDO> diagnoseDTOS = MapUtils.get(map,"operInfoRecordInfos");
        InptVisitDTO inptVisit = MapUtils.get(map,"inptVisit");
        Map<String, Object> diseinfoMap = null;
        List<Map<String,Object>> operationInfoList = new ArrayList<>();
        String redisKey = this.getRedisKey(inptVisit) ;
        Map<String,String> insureEmrInfo = redisUtils.get(redisKey);
        List<Map<String,Object>> coursrinfoList = MapUtils.get(insureEmrInfo,"oprninfo");
        if(!ListUtils.isEmpty(diagnoseDTOS)){
            for(int i=0;i<diagnoseDTOS.size();i++){
                OperInfoRecordDO operInfoRecordDO=diagnoseDTOS.get(i);
                diseinfoMap  = new HashMap<>();
                diseinfoMap.put("oprn_appy_id","无");//	手术申请单号
                diseinfoMap.put("oprn_seq","");//	手术序列号
                diseinfoMap.put("blotype_abo",operInfoRecordDO.getBloodCode());//	血型代码
                diseinfoMap.put("oprn_time",operInfoRecordDO.getOperPlanTime());//手术日期
                diseinfoMap.put("oprn_type_code","无"); // 手术分类代码
                diseinfoMap.put("oprn_type_name","无");//  手术分类名称
                diseinfoMap.put("bfpn_diag_code",operInfoRecordDO.getInDiseaseIcd10()); //术前诊断代码
                diseinfoMap.put("bfpn_oprn_diag_name",operInfoRecordDO.getInDiseaseName()); //术前诊断名称
                diseinfoMap.put("bfpn_inhosp_ifet","无");//	术前是否发生院内感染
                diseinfoMap.put("afpn_diag_code","无");//	术后诊断代码
                diseinfoMap.put("afpn_diag_name","无");//	术后诊断名称
                diseinfoMap.put("sinc_heal_lv",operInfoRecordDO.getHealCode()); //手术切口愈合等级代码
                diseinfoMap.put("sinc_heal_lv_code","无"); // 手术切口愈合等级
                diseinfoMap.put("back_oprn","无");//	是否重返手术（明确定义）
                diseinfoMap.put("selv","无");//	是否择期
                diseinfoMap.put("prev_abtl_medn","无");//	是否预防使用抗菌药物
                diseinfoMap.put("abtl_medn_days","0");//	预防使用抗菌药物天数
                diseinfoMap.put("oprn_oprt_code",operInfoRecordDO.getOperDiseaseIcd9()); //手术操作代码
                diseinfoMap.put("oprn_oprt_name",operInfoRecordDO.getOperDiseaseIcd9()); //手术操作名称
                diseinfoMap.put("oprn_lv_code",operInfoRecordDO.getRank()); //手术级别代码
                diseinfoMap.put("oprn_lv_name",operInfoRecordDO.getRank());//手术级别名称
                diseinfoMap.put("anst_mtd_code",operInfoRecordDO.getAnaCode());//	麻醉-方法代码
                diseinfoMap.put("anst_mtd_name",operInfoRecordDO.getAnaCode());//	麻醉-方法名称
                diseinfoMap.put("anst_lv_code","无");//	麻醉分级代码
                diseinfoMap.put("anst_lv_name","无");//	麻醉分级名称
                diseinfoMap.put("exe_anst_dept_code","无");//	麻醉执行科室代码
                diseinfoMap.put("exe_anst_dept_name","无");//	麻醉执行科室名称
                diseinfoMap.put("anst_efft","无");// 麻醉效果
                diseinfoMap.put("oprn_begntime",operInfoRecordDO.getCrteTime());//	手术开始时间
                diseinfoMap.put("oprn_endtime",operInfoRecordDO.getOperEndTime());//	手术结束时间
                diseinfoMap.put("oprn_asps",Constants.SF.S);//	麻醉分级名称
                diseinfoMap.put("oprn_asps_ifet","无");//	无菌手术是否感染
                diseinfoMap.put("afpn_info","无");//	手术后情况
                diseinfoMap.put("oprn_merg","无");//	是否手术合并症
                diseinfoMap.put("oprn_conc","无");//	是否手术并发症
                diseinfoMap.put("oprn_anst_dept_code",operInfoRecordDO.getOperDeptId());//	手术执行科室代码
                diseinfoMap.put("oprn_anst_dept_name",operInfoRecordDO.getOperRoomName());//	手术执行科室名称
                diseinfoMap.put("palg_dise","无");// 病理检查
                diseinfoMap.put("oth_med_dspo","无"); //其他医学处置
                diseinfoMap.put("out_std_oprn_time","无"); //是否超出手术时间
                diseinfoMap.put("oprn_oper_name",operInfoRecordDO.getDoctorName());//	手术者姓名
                diseinfoMap.put("oprn_asit_name1",operInfoRecordDO.getAssistantName1());//	助手I姓名
                diseinfoMap.put("oprn_asit_name2",operInfoRecordDO.getAssistantName2());//	助手Ⅱ姓名
                diseinfoMap.put("anst_dr_name",operInfoRecordDO.getAnaName1());// 麻醉医师姓名
                diseinfoMap.put("anst_asa_lv_code","无");//麻醉ASA分级代码
                diseinfoMap.put("anst_asa_lv_name","无");// 麻醉ASA分级名称
                diseinfoMap.put("anst_medn_code","无");// 麻醉药物代码
                diseinfoMap.put("anst_medn_name","无"); // 麻醉药物名称
                diseinfoMap.put("anst_medn_dos","无"); // 麻醉药物剂量
                diseinfoMap.put("anst_dosunt","无");// 计量单位
                diseinfoMap.put("anst_begntime",""); // 麻醉开始时间
                diseinfoMap.put("anst_endtime",""); // 麻醉结束时间
                diseinfoMap.put("anst_merg_symp_code","无"); // 麻醉合并症代码
                diseinfoMap.put("anst_merg_symp","无"); // 麻醉合并症名称
                diseinfoMap.put("anst_merg_symp_dscr","无"); // 麻醉合并症描述
                diseinfoMap.put("pacu_begntime",""); // 入复苏室时间
                diseinfoMap.put("pacu_endtime",""); // 出复苏室时间
                diseinfoMap.put("oprn_selv","无"); // 是否择期手术
                diseinfoMap.put("canc_oprn","无"); // 是否择取消手术
                diseinfoMap.put("vali_flag",Constants.SF.S); //有效标志

                if (!coursrinfoList.isEmpty()) {
                    for (Map<String,Object> courMap : coursrinfoList) {
                        // 合并，有值的后面会覆盖前面的
                        Map<String, Object> combineResultMap = new HashMap();
                        combineResultMap.putAll(diseinfoMap);
                        combineResultMap.putAll(courMap);
                        operationInfoList.add(combineResultMap);
                    }
                } else {
                    operationInfoList.add(diseinfoMap);
                }
            }
        } else {
            diseinfoMap  = new HashMap<>();
            diseinfoMap.put("oprn_appy_id","无");//	手术申请单号
            diseinfoMap.put("oprn_seq","无");//	手术序列号
            diseinfoMap.put("blotype_abo","无");//	血型代码
            diseinfoMap.put("oprn_time",DateUtils.format(DateUtils.Y_M_DH_M_S));//手术日期
            diseinfoMap.put("oprn_type_code","无"); // 手术分类代码
            diseinfoMap.put("oprn_type_name","无");//  手术分类名称
            diseinfoMap.put("bfpn_diag_code","无"); //术前诊断代码
            diseinfoMap.put("bfpn_oprn_diag_name","无"); //术前诊断名称
            diseinfoMap.put("bfpn_inhosp_ifet","无");//	术前是否发生院内感染
            diseinfoMap.put("afpn_diag_code","无");//	术后诊断代码
            diseinfoMap.put("afpn_diag_name","无");//	术后诊断名称
            diseinfoMap.put("sinc_heal_lv","无"); //手术切口愈合等级代码
            diseinfoMap.put("sinc_heal_lv_code","无"); // 手术切口愈合等级
            diseinfoMap.put("back_oprn","无");//	是否重返手术（明确定义）
            diseinfoMap.put("selv","无");//	是否择期
            diseinfoMap.put("prev_abtl_medn","无");//	是否预防使用抗菌药物
            diseinfoMap.put("abtl_medn_days","无");//	预防使用抗菌药物天数
            diseinfoMap.put("oprn_oprt_code","无"); //手术操作代码
            diseinfoMap.put("oprn_oprt_name","无"); //手术操作名称
            diseinfoMap.put("oprn_lv_code","无"); //手术级别代码
            diseinfoMap.put("oprn_lv_name","无");//手术级别名称
            diseinfoMap.put("anst_mtd_code","无");//	麻醉-方法代码
            diseinfoMap.put("anst_mtd_name","无");//	麻醉-方法名称
            diseinfoMap.put("anst_lv_code","无");//	麻醉分级代码
            diseinfoMap.put("anst_lv_name","无");//	麻醉分级名称
            diseinfoMap.put("exe_anst_dept_code","无");//	麻醉执行科室代码
            diseinfoMap.put("exe_anst_dept_name","无");//	麻醉执行科室名称
            diseinfoMap.put("anst_efft","无");// 麻醉效果
            diseinfoMap.put("oprn_begntime",DateUtils.format(DateUtils.Y_M_DH_M_S));//	手术开始时间
            diseinfoMap.put("oprn_endtime",DateUtils.format(DateUtils.Y_M_DH_M_S));//	手术结束时间
            diseinfoMap.put("oprn_asps",Constants.SF.S);//	麻醉分级名称
            diseinfoMap.put("oprn_asps_ifet","无");//	无菌手术是否感染
            diseinfoMap.put("afpn_info","无");//	手术后情况
            diseinfoMap.put("oprn_merg","无");//	是否手术合并症
            diseinfoMap.put("oprn_conc","无");//	是否手术并发症
            diseinfoMap.put("oprn_anst_dept_code","无");//	手术执行科室代码
            diseinfoMap.put("oprn_anst_dept_name","无");//	手术执行科室名称
            diseinfoMap.put("palg_dise","无");// 病理检查
            diseinfoMap.put("oth_med_dspo","无"); //其他医学处置
            diseinfoMap.put("out_std_oprn_time",""); //是否超出手术时间
            diseinfoMap.put("oprn_oper_name","无");//	手术者姓名
            diseinfoMap.put("oprn_asit_name1","无");//	助手I姓名
            diseinfoMap.put("oprn_asit_name2","无");//	助手Ⅱ姓名
            diseinfoMap.put("anst_dr_name","无");// 麻醉医师姓名
            diseinfoMap.put("anst_asa_lv_code","无");//麻醉ASA分级代码
            diseinfoMap.put("anst_asa_lv_name","无");// 麻醉ASA分级名称
            diseinfoMap.put("anst_medn_code","无");// 麻醉药物代码
            diseinfoMap.put("anst_medn_name","无"); // 麻醉药物名称
            diseinfoMap.put("anst_medn_dos","无"); // 麻醉药物剂量
            diseinfoMap.put("anst_dosunt","无");// 计量单位
            diseinfoMap.put("anst_begntime",""); // 麻醉开始时间
            diseinfoMap.put("anst_endtime",""); // 麻醉结束时间
            diseinfoMap.put("anst_merg_symp_code","无"); // 麻醉合并症代码
            diseinfoMap.put("anst_merg_symp","无"); // 麻醉合并症名称
            diseinfoMap.put("anst_merg_symp_dscr","无"); // 麻醉合并症描述
            diseinfoMap.put("pacu_begntime",DateUtils.format(DateUtils.Y_M_DH_M_S)); // 入复苏室时间
            diseinfoMap.put("pacu_endtime",DateUtils.format(DateUtils.Y_M_DH_M_S)); // 出复苏室时间
            diseinfoMap.put("oprn_selv","无"); // 是否择期手术
            diseinfoMap.put("canc_oprn","无"); // 是否择取消手术
            diseinfoMap.put("vali_flag",Constants.SF.S); //有效标志
            operationInfoList.add(diseinfoMap);
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
    private List<Map<String, Object>> queryEmrCoursrInfo(Map<String, Object> map) {
        EmrPatientRecordDO diagnoseDTO = MapUtils.get(map,"courseRecord");
        InptVisitDTO inptVisit = MapUtils.get(map,"inptVisit");
        Map<String, Object> diseinfoMap = null;
        List<Map<String,Object>> resultList = new ArrayList<>();
        if(diagnoseDTO != null) {
                diseinfoMap  = new HashMap<>();
                diseinfoMap.put("dept_code",inptVisit.getInDeptId());//	科室代码
                diseinfoMap.put("dept_name",inptVisit.getInDeptName());//	科室名称
                diseinfoMap.put("wardarea_name",inptVisit.getInWardId());//	病区名称
                diseinfoMap.put("bedno",inptVisit.getBedName());//病床号
                diseinfoMap.put("rcd_time",diagnoseDTO.getCrteTime());//	记录日期时间
                diseinfoMap.put("chfcomp","无");//	主诉
                diseinfoMap.put("cas_ftur","无");//	病例特点
                diseinfoMap.put("tcm4d_rslt","无");//	中医“四诊”观察结果
                diseinfoMap.put("dise_evid",inptVisit.getInDiseaseName());//	诊断依据
                if (StringUtils.isEmpty(inptVisit.getInDiseaseIcd10()) || StringUtils.isEmpty(inptVisit.getInDiseaseName()) ) {
                    inptVisit.setInDiseaseIcd10("无");
                    inptVisit.setInDiseaseName("无");
                }
                diseinfoMap.put("prel_wm_diag_code",inptVisit.getInDiseaseIcd10());//	初步诊断-西医诊断编码
                diseinfoMap.put("prel_wm_dise_name",inptVisit.getInDiseaseName());//	初步诊断-西医诊断名称
                diseinfoMap.put("prel_tcm_diag_code","无");//	初步诊断-中医病名代码
                diseinfoMap.put("prel_tcm_dise_name","无");//	初步诊断-中医病名
                diseinfoMap.put("prel_tcmsymp_code","无");//	初步诊断-中医证候代码
                diseinfoMap.put("prel_tcmsymp","无");//	初步诊断-中医证候
                diseinfoMap.put("finl_wm_diag_code","无");//	鉴别诊断-西医诊断编码
                diseinfoMap.put("finl_wm_diag_name","无");//	鉴别诊断-西医诊断名称
                diseinfoMap.put("finl_tcm_dise_code","无");//	鉴别诊断-中医病名代码
                diseinfoMap.put("finl_tcm_dise_name","无");//	鉴别诊断-中医病名
                diseinfoMap.put("finl_tcmsymp_code","无");//	鉴别诊断-中医证候代码

                diseinfoMap.put("finl_tcmsymp","无");//	鉴别诊断-中医证候
                diseinfoMap.put("dise_plan","无");//	诊疗计划
                diseinfoMap.put("prnp_trt","无");//	治则治法
                diseinfoMap.put("ipdr_code",inptVisit.getZzDoctorId());//	住院医师编号
                diseinfoMap.put("ipdr_name",inptVisit.getZzDoctorName());//	住院医师姓名
                diseinfoMap.put("prnt_doc_name",inptVisit.getZgDoctorName());//	上级医师姓名
                diseinfoMap.put("vali_flag",Constants.SF.S);//	有效标志
        } else {
            diseinfoMap  = new HashMap<>();
            diseinfoMap.put("dept_code","无");//	科室代码
            diseinfoMap.put("dept_name","无");//	科室名称
            diseinfoMap.put("wardarea_name","无");//	病区名称
            diseinfoMap.put("bedno","无");//病床号
            diseinfoMap.put("rcd_time",DateUtils.format(DateUtils.Y_M_DH_M_S));//	记录日期时间
            diseinfoMap.put("chfcomp","无");//	主诉
            diseinfoMap.put("cas_ftur","无");//	病例特点
            diseinfoMap.put("tcm4d_rslt","无");//	中医“四诊”观察结果
            diseinfoMap.put("dise_evid","无");//	诊断依据
            diseinfoMap.put("prel_wm_diag_code","无");//	初步诊断-西医诊断编码
            diseinfoMap.put("prel_wm_dise_name","无");//	初步诊断-西医诊断名称
            diseinfoMap.put("prel_tcm_diag_code","无");//	初步诊断-中医病名代码
            diseinfoMap.put("prel_tcm_dise_name","无");//	初步诊断-中医病名
            diseinfoMap.put("prel_tcmsymp_code","无");//	初步诊断-中医证候代码
            diseinfoMap.put("prel_tcmsymp","无");//	初步诊断-中医证候
            diseinfoMap.put("finl_wm_diag_code","无");//	鉴别诊断-西医诊断编码
            diseinfoMap.put("finl_wm_diag_name","无");//	鉴别诊断-西医诊断名称
            diseinfoMap.put("finl_tcm_dise_code","无");//	鉴别诊断-中医病名代码
            diseinfoMap.put("finl_tcm_dise_name","无");//	鉴别诊断-中医病名
            diseinfoMap.put("finl_tcmsymp_code","无");//	鉴别诊断-中医证候代码
            diseinfoMap.put("finl_tcmsymp","无");//	鉴别诊断-中医证候
            diseinfoMap.put("dise_plan","无");//	诊疗计划
            diseinfoMap.put("prnp_trt","无");//	治则治法
            diseinfoMap.put("ipdr_code","无");//	住院医师编号
            diseinfoMap.put("ipdr_name","无");//	住院医师姓名
            diseinfoMap.put("prnt_doc_name","无");//	上级医师姓名
            diseinfoMap.put("vali_flag",Constants.SF.S);//	有效标志
        }

        String redisKey = this.getRedisKey(inptVisit) ;
        Map<String,String> insureEmrInfo = redisUtils.get(redisKey);
        List<Map<String,Object>> coursrinfoList = MapUtils.get(insureEmrInfo,"coursrinfo");
        if (!coursrinfoList.isEmpty()) {
            for (Map<String,Object> courMap : coursrinfoList) {
                // 合并，有值的后面会覆盖前面的
                Map<String, Object> combineResultMap = new HashMap();
                combineResultMap.putAll(diseinfoMap);
                combineResultMap.putAll(courMap);
                resultList.add(combineResultMap);
            }
        } else {
            resultList.add(diseinfoMap);
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
    private List<Map<String, Object>> queryEmrRescInfo(Map<String, Object> map) {
        List<Map<String,Object>> resultList = new ArrayList<>();
        InptVisitDTO inptVisit = MapUtils.get(map,"inptVisit");
        Map<String, Object> diseinfoMap = null;
        if (inptVisit!=null){
            diseinfoMap  = new HashMap<>();
            diseinfoMap.put("dept",inptVisit.getInDeptId());//	科室代码
            diseinfoMap.put("dept_name",inptVisit.getInDeptName());//	科室名称
            diseinfoMap.put("wardarea_name",inptVisit.getInWardId());//	病区名称
            diseinfoMap.put("bedno",inptVisit.getBedName());//病床号
            if (StringUtils.isEmpty(inptVisit.getInDiseaseIcd10()) || StringUtils.isEmpty(inptVisit.getInDiseaseName()) ) {
                inptVisit.setInDiseaseIcd10("无");
                inptVisit.setInDiseaseName("无");
            }
            diseinfoMap.put("diag_name",inptVisit.getDiseaseIcd10());//	诊断名称
            diseinfoMap.put("diag_code",inptVisit.getDiseaseCode());//	诊断代码
            diseinfoMap.put("cond_chg","无");//	病情变化情况
            diseinfoMap.put("resc_mes","无");//	抢救措施
            diseinfoMap.put("oprn_oprt_code","无");//	手术操作代码
            diseinfoMap.put("oprn_oprt_name","无");//	手术操作名称
            diseinfoMap.put("oprn_oper_part","无");//	手术及操作目标部位名称
            diseinfoMap.put("itvt_name","无");//	介入物名称
            diseinfoMap.put("oprt_mtd","无");//	操作方法
            diseinfoMap.put("oprt_cnt",0);//	操作次数
            diseinfoMap.put("resc_begntime",DateUtils.format(DateUtils.Y_M_DH_M_S));//	抢救开始日期时间
            diseinfoMap.put("resc_endtime",DateUtils.format(DateUtils.Y_M_DH_M_S));//	抢救结束日期时间
            diseinfoMap.put("dise_item_name","无");//	检查/检验项目名称
            diseinfoMap.put("dise_ccls_qunt",0);// 定量检查结果
            diseinfoMap.put("dise_ccls","无");//	检查/检验结果
            diseinfoMap.put("dise_ccls_code","无");//	检查/检验结果代码
            diseinfoMap.put("mnan","无");//	注意事项
            diseinfoMap.put("resc_psn_list","无");//	参加抢救人员名单
            diseinfoMap.put("proftechttl_code","无");//	专业技术职务类别代码
            diseinfoMap.put("doc_code","无");//	医师编号
            diseinfoMap.put("dr_name","无");//	医师姓名
            diseinfoMap.put("vali_flag",Constants.SF.S);//	有效标志
        }

        String redisKey = this.getRedisKey(inptVisit) ;
        Map<String,String> insureEmrInfo = redisUtils.get(redisKey);
        List<Map<String,Object>> coursrinfoList = MapUtils.get(insureEmrInfo,"rescinfo");
        if (!coursrinfoList.isEmpty()) {
            for (Map<String,Object> courMap : coursrinfoList) {
                // 合并，有值的后面会覆盖前面的
                Map<String, Object> combineResultMap = new HashMap();
                combineResultMap.putAll(diseinfoMap);
                combineResultMap.putAll(courMap);
                resultList.add(combineResultMap);
            }
        } else {
            resultList.add(diseinfoMap);
        }

        return  resultList;
    }

    /**
     * @Method queryEmrDieInfo
     * @Desrciption  死亡记录
     * @Param map
     * @Author liuliyun
     * @Date   2021/8/21 17:20
     * @Return
     **/
    private List<Map<String, Object>> queryEmrDieInfo(Map<String, Object> map) {
        InptVisitDTO inptVisit = MapUtils.get(map,"inptVisit");
        List<Map<String,Object>> resultList = new ArrayList<>();
        Map<String, Object> diseinfoMap = null;
        if (inptVisit!=null){
            diseinfoMap  = new HashMap<>();
            diseinfoMap.put("dept",inptVisit.getInDeptId());//	科室代码
            diseinfoMap.put("dept_name",inptVisit.getInDeptName());//	科室名称
            diseinfoMap.put("wardarea_name",inptVisit.getInWardId());//	病区名称
            diseinfoMap.put("bedno",inptVisit.getBedName());//病床号
            diseinfoMap.put("adm_time",inptVisit.getInTime());//	入院时间
            if (StringUtils.isEmpty(inptVisit.getInDiseaseIcd10())) {
                inptVisit.setInDiseaseIcd10("无");
            }
            diseinfoMap.put("adm_dise",inptVisit.getInDiseaseIcd10());//	入院诊断编码
            diseinfoMap.put("adm_info",inptVisit.getInSituationCode());//	入院情况
            diseinfoMap.put("trt_proc_dscr","无");//	诊疗过程描述
            diseinfoMap.put("die_time",DateUtils.format(DateUtils.Y_M_DH_M_S));//	死亡时间
            diseinfoMap.put("die_drt_rea","无");//	直接死亡原因名称
            diseinfoMap.put("die_drt_rea_code","无");//	直接死亡原因编码
            diseinfoMap.put("die_dise_name","无");//	死亡诊断名称
            diseinfoMap.put("die_diag_code","无");//	死亡诊断编码
            diseinfoMap.put("agre_corp_dset","无");//	家属是否同意尸体解剖标志
            diseinfoMap.put("ipdr_name","无");//	住院医师姓名
            diseinfoMap.put("chfpdr_code","无");//	主诊医师代码
            diseinfoMap.put("chfpdr_name","无");//	主诊医师姓名
            diseinfoMap.put("chfdr_name","无");//	主任医师姓名
            diseinfoMap.put("sign_time",DateUtils.format(DateUtils.Y_M_DH_M_S));//	签字日期时间
            diseinfoMap.put("vali_flag",Constants.SF.S);//	有效标志
        }

        String redisKey = this.getRedisKey(inptVisit) ;
        Map<String,String> insureEmrInfo = redisUtils.get(redisKey);
        List<Map<String,Object>> dieinfoList = MapUtils.get(insureEmrInfo,"dieinfo");
        if (!dieinfoList.isEmpty()) {
            for (Map<String,Object> dieMap : dieinfoList) {
                // 合并，有值的后面会覆盖前面的
                Map<String, Object> combineResultMap = new HashMap();
                combineResultMap.putAll(diseinfoMap);
                combineResultMap.putAll(dieMap);
                resultList.add(combineResultMap);
            }
        } else {
            resultList.add(diseinfoMap);
        }
        return  resultList;
    }

    /**
     * @Method queryEmrDscgoInfo
     * @Desrciption  出院小结
     * @Param map
     * @Author liuliyun
     * @Date   2021/8/21 17:35
     * @Return
     **/
    private List<Map<String, Object>> queryEmrDscgoInfo(Map<String, Object> map) {
        List<Map<String,Object>> resultList = new ArrayList<>();
        EmrPatientRecordDO outRecord = MapUtils.get(map,"outRecord");
        InptVisitDTO inptVisit = MapUtils.get(map,"inptVisit");
        Map<String, Object> diseinfoMap = null;
        if (outRecord!=null){
            diseinfoMap  = new HashMap<>();
            diseinfoMap.put("dscg_date",inptVisit.getOutTime());//	出院日期
            if (StringUtils.isEmpty(inptVisit.getDiseaseName()) || StringUtils.isEmpty(inptVisit.getOutDiseaseName())) {
                inptVisit.setDiseaseName("无");
                inptVisit.setOutDiseaseName("无");
            }
            diseinfoMap.put("adm_diag_dscr",inptVisit.getDiseaseName());//	入院诊断描述
            diseinfoMap.put("dscg_dise_dscr",inptVisit.getOutDiseaseName());//	出院诊断
            diseinfoMap.put("adm_info","无");//入院情况
            diseinfoMap.put("trt_proc_rslt_dscr","无");//	诊治经过及结果（含手术日期名称及结果）
            diseinfoMap.put("dscg_info","无");//	出院情况（含治疗效果）
            diseinfoMap.put("dscg_drord","无");//	出院医嘱
            diseinfoMap.put("caty","无");//	科别
            diseinfoMap.put("rec_doc","无");// 记录医师
            diseinfoMap.put("main_drug_name","无");//主要药品名称
            diseinfoMap.put("oth_imp_info","无");//	其他重要信息
            diseinfoMap.put("vali_flag",Constants.SF.S);//有效标志
        }else {
            diseinfoMap  = new HashMap<>();
            diseinfoMap.put("dscg_date",DateUtils.format(DateUtils.Y_M_DH_M_S));//	出院日期
            diseinfoMap.put("adm_diag_dscr","无");//	入院诊断描述
            diseinfoMap.put("dscg_dise_dscr","无");//	出院诊断
            diseinfoMap.put("adm_info","无");//入院情况
            diseinfoMap.put("trt_proc_rslt_dscr","无");//	诊治经过及结果（含手术日期名称及结果）
            diseinfoMap.put("dscg_info","无");//	出院情况（含治疗效果）
            diseinfoMap.put("dscg_drord","无");//	出院医嘱
            diseinfoMap.put("caty","无");//	科别
            diseinfoMap.put("rec_doc","无");// 记录医师
            diseinfoMap.put("main_drug_name","无");//主要药品名称
            diseinfoMap.put("oth_imp_info","无");//	其他重要信息
            diseinfoMap.put("vali_flag",Constants.SF.S);//有效标志
        }

        String redisKey = this.getRedisKey(inptVisit) ;
        Map<String,String> insureEmrInfo = redisUtils.get(redisKey);
        List<Map<String,Object>> dscginfoList = MapUtils.get(insureEmrInfo,"dscginfo");
        if (!dscginfoList.isEmpty()) {
            for (Map<String,Object> dscginfoMap : dscginfoList) {
                // 合并，有值的后面会覆盖前面的
                Map<String, Object> combineResultMap = new HashMap();
                combineResultMap.putAll(diseinfoMap);
                combineResultMap.putAll(dscginfoMap);
                resultList.add(combineResultMap);
            }
        } else {
            resultList.add(diseinfoMap);
        }
        return  resultList;
    }

    // 获取病区信息
    private BaseDeptDTO getInwardInfo(String deptId,String hospCode) {
        Map<String,Object> selectMap = new HashMap<>();
        BaseDeptDTO baseDeptDTO = new BaseDeptDTO();
        baseDeptDTO.setId(deptId);
        baseDeptDTO.setHospCode(hospCode);
        selectMap.put("hospCode",hospCode);
        selectMap.put("baseDeptDTO",baseDeptDTO);
        WrapperResponse<BaseDeptDTO> wr = baseDeptService.getById(selectMap);
        if (wr != null && wr.getData() != null) {
            baseDeptDTO = wr.getData();
        }
        return baseDeptDTO;
    }

}
