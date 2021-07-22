package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
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
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.prescribe.service.OutptDoctorPrescribeService;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String,Object> baseinfoMap = queryEmcBaseInfo(map);
        Map<String,Object> diseaseInfoMap = queryDiseaseInfo(map);
        Map<String,Object> operationMap = queryOperationInfo(map);
        Map<String,Object> icuinfoMap = queryIcuinInfo(map);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("baseinfo",baseinfoMap);
        paramMap.put("diseinfo",MapUtils.get(diseaseInfoMap,"mapList1"));
        paramMap.put("oprninfo",MapUtils.get(operationMap,"oprationMapList"));
        paramMap.put("icuinfo",MapUtils.get(operationMap,"icuinfoMapList"));
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
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
        logger.info("调用功能号【"+functionCode+"】的反参为"+resultJson);
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson);
        return resultMap;
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
    private Map<String, Object> queryOperationInfo(Map<String, Object> map) {
        InsureIndividualVisitDTO insureIndividualVisitDTO =MapUtils.get(map,"insureIndividualVisitDTO");
        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        inptVisitDTO.setHospCode(insureIndividualVisitDTO.getHospCode());
        inptVisitDTO.setId(MapUtils.get(map,"visitId"));
        map.put("inptVisitDTO",inptVisitDTO);
        List<Map<String,Object>> oprationMapList = new ArrayList<>();
        Map<String,Object> paramMap = null;
        List<MrisOperInfoDO> operInfoDOList = mrisHomeService_consumer.queryAllOperation(map);
        if(!ListUtils.isEmpty(operInfoDOList)){
            int count =0;
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

}
