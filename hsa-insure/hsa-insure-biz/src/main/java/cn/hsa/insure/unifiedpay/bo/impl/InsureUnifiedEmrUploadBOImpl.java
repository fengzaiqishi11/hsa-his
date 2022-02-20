package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.base.dept.service.BaseDeptService;
import cn.hsa.insure.util.InsureUnifiedCommonUtil;
import cn.hsa.module.emr.emrpatientrecord.entity.EmrPatientRecordDO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.inpt.bo.InsureUnifiedEmrUploadBO;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualSettleDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dao.InsureUnifiedEmrUploadDAO;
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
import org.springframework.util.ObjectUtils;

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


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    @Resource
    private BaseDeptService baseDeptService;

    @Resource
    private InsureUnifiedCommonUtil insureUnifiedCommonUtil;

    @Resource
    private InsureUnifiedEmrUploadDAO  insureUnifiedEmrUploadDAO;

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
        InsureIndividualVisitDTO insureIndividualVisitDTO = insureUnifiedCommonUtil.commonGetVisitInfo(map);
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
        String orgCode = insureIndividualVisitDTO.getInsureOrgCode();
        StringBuffer stringBuffer = new StringBuffer();
        String mdtrtSn = stringBuffer.append(insureIndividualVisitDTO.getMedicineOrgCode()).append(insureIndividualVisitDTO.getMedicalRegNo()).toString();
        //  输入-基本信息（节点标识：baseinfo）
        Map<String,Object> baseinfoMap = queryEmcBaseInfo(map,mdtrtSn);
        // 病案首页流水号
        MrisBaseInfoDTO mrisBaseInfoDTO = MapUtils.get(map,"mrisBaseInfoDTO");
        // 输入-诊断信息（节点标识：diseinfo）
        String mid = mrisBaseInfoDTO.getId();
        Map<String,Object> diseaseInfoMap = queryDiseaseInfo(map,mid,mdtrtSn);
        // 输入-手术记录（节点标识：oprninfo）
        Map<String,Object> operationMap = queryOperationInfo(map,mid,mdtrtSn);
        //  输入-重症监护信息（节点标识：icuinfo）
        Map<String,Object> icuinfoMap = queryIcuinInfo(map,mid,mdtrtSn);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("baseinfo",baseinfoMap);
        paramMap.put("diseinfo",MapUtils.get(diseaseInfoMap,"mrisDiagnoseList"));
        paramMap.put("oprninfo",MapUtils.get(operationMap,"oprationMapList"));
        paramMap.put("icuinfo",MapUtils.get(icuinfoMap,"icuinfoMapList"));
        map.put("msgName","住院病案首页上传");
        map.put("isHospital",insureIndividualVisitDTO.getIsHospital());
        map.put("visitId",insureIndividualVisitDTO.getVisitId());
        insureUnifiedCommonUtil.commonInsureUnified(hospCode,orgCode,Constant.UnifiedPay.INPT.UP_4401,paramMap,map);
        // 上传完医保以后  更新病案首页表数据 标注为已经上传
        insureUnifiedEmrUploadDAO.updateEmrBaseInfo(mrisBaseInfoDTO);
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
    private Map<String, Object> queryIcuinInfo(Map<String, Object> map,String mid,String mdtrtSn) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map,"insureIndividualVisitDTO");
        List<Map<String,Object>> mapList = new ArrayList<>();
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("vali_flag",Constants.SF.F);
        paramMap.put("ipt_medcas_hmpg_sn",mid);
        StringBuffer stringBuffer = new StringBuffer();
        paramMap.put("mdtrt_sn",mdtrtSn);
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
    private Map<String, Object> queryOperationInfo(Map<String, Object> map,String mid,String mdtrtSn) {
        InsureIndividualVisitDTO insureIndividualVisitDTO =MapUtils.get(map,"insureIndividualVisitDTO");
        map.put("insureRegCode",insureIndividualVisitDTO.getInsureRegCode());
        List<Map<String,Object>> operInfoDOList = insureUnifiedEmrUploadDAO.selectOperInfo(map);
        if(!ListUtils.isEmpty(operInfoDOList)){
            operInfoDOList.stream().forEach(item->{
                item.put("oprn_oprt_sn",""); // 手术操作序列号
                item.put("oprn_oper_part",""); //手术操作部位
                item.put("oprn_oper_part_code",""); // 手术操作部位代码
                item.put("oprn_con_time",""); // 手术持续时间
                item.put("anst_lv_name",""); // 麻醉分级名称
                item.put("anst_lv_code",""); // 麻醉分级代码
                item.put("oprn_patn_type",""); // 手术患者类型
                item.put("oprn_patn_type _code",""); //手术患者类型代码
                if(!"1".equals(MapUtils.get(item,"oprn_oprt_sn"))){
                    item.put("main_oprn_flag","0"); // 主要手术标志
                }else {
                    item.put("main_oprn_flag","1"); // 主要手术标志
                }
                item.put("anst_asa_lv_code",""); // 麻醉ASA分级名称
                item.put("anst_asa_lv_name",""); // 麻醉ASA分级名称
                item.put("anst_medn_code",""); // 麻醉药物代码
                item.put("anst_medn_name",""); // 麻醉药物名称
                item.put("anst_medn_dos",""); // 麻醉药物剂量
                item.put("unt",""); //计量单位
                item.put("anst_begntime",""); // 麻醉开始时间
                item.put("anst_endtime",""); //麻醉结束时间
                item.put("anst_copn_code",""); // 麻醉合并症代码
                item.put("anst_copn_name",""); // 麻醉合并症名称
                item.put("anst_copn_dscr",""); // 麻醉合并症描述
                item.put("pacu_begntime",""); // 复苏室开始时间
                item.put("pacu_endtime",""); // 复苏室结束时间
                item.put("canc_oprn_flag",""); //取消手术标志
                item.put("vali_flag",Constants.SF.S); // 有效标志
                item.put("mdtrt_sn",mdtrtSn);  //  就医流水号
                item.put("ipt_medcas_hmpg_sn",mid); // 住院病案首页流水号
            });
        }else{
            // 用测试环境测试时，医保必须传手术节点
            Map<String,Object> operInfoMap = new HashMap<>();
            operInfoMap.put("vali_flag",Constants.SF.F); // 有效标志
            operInfoMap.put("mdtrt_sn",mdtrtSn);  //  就医流水号
            operInfoMap.put("ipt_medcas_hmpg_sn",mid); // 住院病案首页流水号
            operInfoDOList.add(operInfoMap);
        }
        map.put("oprationMapList",operInfoDOList);
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
    private Map<String, Object> queryDiseaseInfo(Map<String, Object> map,String midId,String mdtrtSn) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map,"insureIndividualVisitDTO");
        map.put("insureRegCode",insureIndividualVisitDTO.getInsureRegCode());
        String medicalRegNo = insureIndividualVisitDTO.getMedicalRegNo();
        List<Map<String,Object>> mrisDiagnoseList = insureUnifiedEmrUploadDAO.selectDiseinfo(map);
        if(!ListUtils.isEmpty(mrisDiagnoseList)){
            mrisDiagnoseList.stream().forEach(item->{
                item.put("palg_no","");//	病理号
                item.put("ipt_patn_disediag_type_code",null);//	住院患者疾病诊断类型代码
                item.put("disediag_type",null);//	疾病诊断类型
                if(!"1".equals(MapUtils.get(item,"maindiag_flag"))){
                    item.put("maindiag_flag","0");//	主诊断标志
                }else{
                    item.put("maindiag_flag",MapUtils.get(item,"maindiag_flag"));//	主诊断标志
                }
                String disgCodeValue = MapUtils.get(item,"diag_code");
                item.put("inhosp_diag_code",MapUtils.get(item,"disease_icd10"));//	院内诊断代码
                String diseaseIcd10Name = MapUtils.get(item,"disease_icd10_name");
                item.put("inhosp_diag_name",diseaseIcd10Name);//	院内诊断名称

                if(StringUtils.isEmpty(disgCodeValue)){
                    throw new AppException("病案首页的"+diseaseIcd10Name+"还未匹配");
                }
                item.put("diag_code",MapUtils.get(item,"diag_code"));//	诊断代码
                item.put("diag_name",MapUtils.get(item,"diag_name"));//	诊断名称
                item.put("adm_dise_cond_name",null);//	入院疾病病情名称
                item.put("adm_dise_cond_code",null);//	入院疾病病情代码
                String admCondValue = MapUtils.get(item,"adm_cond");
                item.put("adm_cond_code",admCondValue);//	入院时病情代码
                if("1".equals(admCondValue)){
                    item.put("adm_cond","有");//	入院时病情名称
                }else if("2".equals(admCondValue)){
                    item.put("adm_cond","临床未确定");//	入院时病情名称
                }else if("3".equals(admCondValue)){
                    item.put("adm_cond","情况不明");//	入院时病情名称
                }else{
                    item.put("adm_cond","无");//	入院时病情名称
                }
                item.put("high_diag_evid",null);//	最高诊断依据
                item.put("bkup_deg",null);//	分化程度
                item.put("bkup_deg_code", null);//分化程度代码	分化程度代码分化程度代码
                item.put("vali_flag",Constants.SF.S);//	有效标志
                item.put("ipt_medcas_hmpg_sn",midId);//	住院病案首页流水号
                item.put("mdtrt_sn",mdtrtSn);//	就医流水号
            });
        }
        map.put("mrisDiagnoseList",mrisDiagnoseList);
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
    private Map<String, Object> queryEmcBaseInfo(Map<String, Object> map,String mdtrtSn) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map,"insureIndividualVisitDTO");
        MrisCostDO mrisCostDO =  MapUtils.get(map,"mrisCostDO");
        MrisBaseInfoDTO mrisBaseInfoDTO = MapUtils.get(map,"mrisBaseInfoDTO");
        Map<String,Object> baseInfoMap = new HashMap<>();
        StringBuffer stringBuffer = new StringBuffer();
        baseInfoMap.put("mdtrt_sn",mdtrtSn); // 就医流水号 定点医药机构编号+院内唯一流水号
        insureUnifiedCommonUtil.getEmptyErr(mrisBaseInfoDTO.getInProfile(),"病案号为空");
        baseInfoMap.put("medcasno",mrisBaseInfoDTO.getInProfile()); // 病案号
        baseInfoMap.put("mdtrt_id",insureIndividualVisitDTO.getMedicalRegNo()); //就诊ID
        baseInfoMap.put("psn_no",insureIndividualVisitDTO.getAac001()); //人员编号
        baseInfoMap.put("patn_ipt_cnt",mrisBaseInfoDTO.getInCnt()); //患者住院次数
        baseInfoMap.put("ipt_no",mrisBaseInfoDTO.getInNo()); //住院号
        insureUnifiedCommonUtil.getEmptyErr(mrisBaseInfoDTO.getName(),"病案号为空");
        baseInfoMap.put("psn_name",mrisBaseInfoDTO.getName()); //人员姓名
        baseInfoMap.put("gend",mrisBaseInfoDTO.getGenderCode()); //性别
        baseInfoMap.put("brdy",mrisBaseInfoDTO.getBirthday()); //出生日期
        baseInfoMap.put("ntly","CHN"); // 国籍 默认是中国
        baseInfoMap.put("ntly_name",mrisBaseInfoDTO.getNationalityName()); //国籍名称
        baseInfoMap.put("nwb_bir_wt",mrisBaseInfoDTO.getBabyBirthWeight()); // 新生儿出生体重
        baseInfoMap.put("nwb_adm_wt",mrisBaseInfoDTO.getBabyInWeight()); // 新生儿入院体重
        baseInfoMap.put("birplc",mrisBaseInfoDTO.getBirthAdress()); // 出生地
        baseInfoMap.put("napl",mrisBaseInfoDTO.getNativePlace()); // 籍贯
        baseInfoMap.put("naty_name",mrisBaseInfoDTO.getNationName()); // 民族名称
        baseInfoMap.put("naty","0"+mrisBaseInfoDTO.getNationCode()); //民族
        baseInfoMap.put("certno",mrisBaseInfoDTO.getCertNo()); //证件号码
        baseInfoMap.put("prfs",mrisBaseInfoDTO.getOccupationCode()); //职业
        baseInfoMap.put("mrg_stas",mrisBaseInfoDTO.getMarryCode()); //婚姻状态
        baseInfoMap.put("curr_addr_poscode",mrisBaseInfoDTO.getNowPostCode()); //现住址-邮政编码
        baseInfoMap.put("curr_addr",mrisBaseInfoDTO.getNowAdress()); //现住址
        baseInfoMap.put("psn_tel",mrisBaseInfoDTO.getPhone()); //个人联系电话
        baseInfoMap.put("resd_addr_prov",mrisBaseInfoDTO.getNativeProvName()); //户口地址-省（自治区、直辖市）
        baseInfoMap.put("resd_addr_city",mrisBaseInfoDTO.getNativeCityName()); //户口地址-市（地区）
        baseInfoMap.put("resd_addr_coty",mrisBaseInfoDTO.getNativeAreaName()); //户口地址-县（区）

        baseInfoMap.put("resd_addr_subd",""); //户口地址-乡（镇、街道办事处）
        baseInfoMap.put("resd_addr_vil",""); //户口地址-村（街、路、弄等）
        baseInfoMap.put("resd_addr_housnum",""); //户口地址-门牌号码
        baseInfoMap.put("resd_addr_poscode",mrisBaseInfoDTO.getNativePostCode()); //户口地址- 邮政编码
        baseInfoMap.put("resd_addr",mrisBaseInfoDTO.getNativeAdress()); //户口地址

        baseInfoMap.put("empr_tel",mrisBaseInfoDTO.getWorkPhone()); //工作单位联系电话
        baseInfoMap.put("empr_poscode",mrisBaseInfoDTO.getWorkPostCode()); //工作单位- 邮政编码
        baseInfoMap.put("empr_addr",mrisBaseInfoDTO.getWorkAddress()); //工作单位及地址
        baseInfoMap.put("coner_tel",mrisBaseInfoDTO.getContactPhone()); //联系人电话
        baseInfoMap.put("coner_name",mrisBaseInfoDTO.getContactName()); //联系人姓名
        baseInfoMap.put("coner_addr",mrisBaseInfoDTO.getContactAddress()); //联系人地址
        String contactRelaCode = mrisBaseInfoDTO.getContactRelaCode();
        if("0".equals(contactRelaCode)){
            contactRelaCode = "1";
        }else if("1".equals(contactRelaCode)){
            contactRelaCode = "10";
        }else if("2".equals(contactRelaCode)){
            contactRelaCode = "20";
        }else if("3".equals(contactRelaCode)){
            contactRelaCode = "30";
        }else if("4".equals(contactRelaCode)){
            contactRelaCode = "40";
        }else if("5".equals(contactRelaCode)){
            contactRelaCode = "50";
        }else if("6".equals(contactRelaCode)){
            contactRelaCode = "69";
        }
        else if("7".equals(contactRelaCode)){
            contactRelaCode = "70";
        }else if("8".equals(contactRelaCode)){
            contactRelaCode = "99";
        }else{
            contactRelaCode = "99";
        }
        baseInfoMap.put("coner_rlts_code",contactRelaCode); //与联系人关系代码

        baseInfoMap.put("adm_way_name",""); //入院途径名称
        baseInfoMap.put("adm_way_code",mrisBaseInfoDTO.getInWay()); // 入院途径代码
        baseInfoMap.put("trt_type_name",""); //治疗类别名称
        baseInfoMap.put("trt_type",""); //治疗类别
        baseInfoMap.put("adm_ward",mrisBaseInfoDTO.getInWard()); //入院病房
        baseInfoMap.put("adm_caty",mrisBaseInfoDTO.getInDeptName()); //入院科别
        baseInfoMap.put("adm_date",mrisBaseInfoDTO.getInTime()); //入院日期
        baseInfoMap.put("dscg_date",mrisBaseInfoDTO.getOutTime()); // 出院日期
        baseInfoMap.put("dscg_caty",mrisBaseInfoDTO.getOutDeptName()); //出院科别
        baseInfoMap.put("Refldept_caty_name",mrisBaseInfoDTO.getTurnDept1()); // 转科科别名称
        baseInfoMap.put("dscg_ward",mrisBaseInfoDTO.getInWard2()); // 出院病房
        baseInfoMap.put("drug_dicm_flag",mrisBaseInfoDTO.getIsAllergy()); // 药物过敏标志
        baseInfoMap.put("dicm_drug_name",mrisBaseInfoDTO.getAllergyList()); // 过敏药物名称
        baseInfoMap.put("die_autp_flag",mrisBaseInfoDTO.getIsAutopsy()); // 死亡患者尸检标志
        baseInfoMap.put("abo_code",mrisBaseInfoDTO.getBloodCode()); //ABO血型代码
        baseInfoMap.put("abo_name",mrisBaseInfoDTO.getBloodName()); //ABO血型名称
        baseInfoMap.put("rh_code",mrisBaseInfoDTO.getRhCode()); //Rh血型代码
        baseInfoMap.put("rh_name",mrisBaseInfoDTO.getRhName()); //RH血型
        baseInfoMap.put("die_flag",null); //死亡标志
        baseInfoMap.put("ipt_days",mrisBaseInfoDTO.getInDays()); //住院天数
        baseInfoMap.put("deptdrt_name",mrisBaseInfoDTO.getDirectorName1()); //科主任姓名
        baseInfoMap.put("chfdr_name",mrisBaseInfoDTO.getDirectorName2()); //主任( 副主任)医师姓名
        baseInfoMap.put("atddr_name",mrisBaseInfoDTO.getZzDoctorName()); //主治医生姓名
        baseInfoMap.put("chfpdr_name",mrisBaseInfoDTO.getZgDoctorName()); //主诊医师姓名
        baseInfoMap.put("ipt_dr_name",""); //住院医师姓名
        baseInfoMap.put("resp_nurs_name",mrisBaseInfoDTO.getZrNurseName()); //责任护士姓名
        baseInfoMap.put("train_dr_name",mrisBaseInfoDTO.getJxDoctorName()); //进修医师姓名
        baseInfoMap.put("intn_dr_name",mrisBaseInfoDTO.getSxDoctorName()); //实习医师姓名
        baseInfoMap.put("codr_name",mrisBaseInfoDTO.getDoctorCoderName()); //编码员姓名
        baseInfoMap.put("qltctrl_dr_name",mrisBaseInfoDTO.getZkDoctorName()); //质控医师姓名
        baseInfoMap.put("qltctrl_nurs_name",mrisBaseInfoDTO.getZkNurseName()); //质控护士姓名
        baseInfoMap.put("medcas_qlt_name",mrisBaseInfoDTO.getEmrQualityName()); //病案质量名称
        baseInfoMap.put("medcas_qlt_code",mrisBaseInfoDTO.getEmrQualityCode()); //病案质量代码
        baseInfoMap.put("qltctrl_date",mrisBaseInfoDTO.getZkTime()); //质控日期
        baseInfoMap.put("dscg_way_name",mrisBaseInfoDTO.getOutModeName()); //离院方式名称
        baseInfoMap.put("dscg_way",mrisBaseInfoDTO.getOutModeCode()); //离院方式
        baseInfoMap.put("acp_medins_code",""); //拟接收医疗机构代码
        baseInfoMap.put("acp_medins_name",mrisBaseInfoDTO.getTurnOrgName()); // 拟接收医疗机构名称
        baseInfoMap.put("dscg_31days_rinp_flag", mrisBaseInfoDTO.getIsInpt()); // 出院 31天内再住院计划标志
        baseInfoMap.put("dscg_31days_rinp_pup",mrisBaseInfoDTO.getAim());// 出院31天内再住院目的
        baseInfoMap.put("damg_intx_ext_rea",mrisBaseInfoDTO.getDamageReason()); //损伤、中毒的外部原因
        baseInfoMap.put("damg_intx_ext_rea_disecode",mrisBaseInfoDTO.getDiseaseIcd10Other()); //损伤、中毒的外部原因疾病编码
        baseInfoMap.put("brn_damg_bfadm_coma_dura",mrisBaseInfoDTO.getInptBeforeDay()); //颅脑损伤患者入院前昏迷时长
        baseInfoMap.put("brn_damg_afadm_coma_dura",mrisBaseInfoDTO.getInptLastDay()); //颅脑损伤患者入院后昏迷时长
        baseInfoMap.put("vent_used_dura",null); //呼吸机使用时长

        baseInfoMap.put("cnfm_date",""); //确诊日期
        baseInfoMap.put("patn_dise_diag_crsp",""); //患者疾病诊断对照
        baseInfoMap.put("patn_dise_diag_crsp_code",""); //住院患者疾病诊断对照代码
        baseInfoMap.put("ipt_patn_diag_inscp",""); //住院患者诊断符合情况
        baseInfoMap.put("ipt_patn_diag_inscp_code",""); //住院患者诊断符合情况代码
        baseInfoMap.put("dscg_trt_rslt",""); //出院治疗结果
        baseInfoMap.put("dscg_trt_rslt_code",""); //出院治疗结果代码
        baseInfoMap.put("medins_orgcode",""); //医疗机构组织机构代码
        baseInfoMap.put("age",Integer.valueOf(mrisBaseInfoDTO.getAge().substring(1,mrisBaseInfoDTO.getAge().length()))); //年龄
        baseInfoMap.put("aise",""); // 过敏源
        baseInfoMap.put("pote_intn_dr_name", ""); //	研究生实习医师姓名
        baseInfoMap.put("hbsag",""); // 乙肝表面抗原（HBsAg）
        baseInfoMap.put("hcv-ab",""); //丙型肝炎抗体（HCV-Ab）
        baseInfoMap.put("hiv-ab",""); //艾滋病毒抗体（hiv-ab）
        baseInfoMap.put("resc_cnt",mrisBaseInfoDTO.getRescueCount()); //抢救次数
        baseInfoMap.put("resc_succ_cnt",mrisBaseInfoDTO.getRescueSuccessCount()); //抢救成功次数
        baseInfoMap.put("hosp_dise_fsttime",""); //手术、治疗、检查、诊断为本院第一例
        baseInfoMap.put("hif_pay_way_name",""); //医保基金付费方式名称
        baseInfoMap.put("hif_pay_way_code",""); //医保基金付费方式代码
        baseInfoMap.put("med_fee_paymtd_name",mrisBaseInfoDTO.getPayWayName()); //医疗费用支付方式名称
        baseInfoMap.put("medfee_paymtd_code",mrisBaseInfoDTO.getPayWayCode()); //医疗费用支付方式代码

        baseInfoMap.put("selfpay_amt",mrisCostDO.getFy07()); //自付金额
        insureUnifiedCommonUtil.getEmptyErr(mrisCostDO.getFy01(),"医疗费总额不能为空");
        baseInfoMap.put("medfee_sumamt",mrisCostDO.getFy01()); //医疗费总额
        baseInfoMap.put("ordn_med_servfee",mrisCostDO.getZhylfwl01()); //一般医疗服务费
        baseInfoMap.put("ordn_trt_oprt_fee",mrisCostDO.getZhylfwl02()); //一般治疗操作费
        baseInfoMap.put("nurs_fee",mrisCostDO.getZhylfwl03()); //护理费
        baseInfoMap.put("com_med_serv_oth_fee",mrisCostDO.getZhylfwl04()); // 综合医疗服务类其他费用
        baseInfoMap.put("palg_diag_fee", mrisCostDO.getZdl01()); // 病理诊断费
        baseInfoMap.put("lab_diag_fee",mrisCostDO.getZdl02());// 实验室诊断费
        baseInfoMap.put("rdhy_diag_fee", mrisCostDO.getZdl03()); //影像学诊断费
        baseInfoMap.put("clnc_dise_fee",mrisCostDO.getZdl04()); //临床诊断项目费
        baseInfoMap.put("nsrgtrt_item_fee",mrisCostDO.getZll01()); //非手术治疗项目费
        baseInfoMap.put("clnc_phys_trt_fee",mrisCostDO.getZll02()); //临床物理治疗费
        baseInfoMap.put("rgtrt_trt_fee",mrisCostDO.getZll03()); //手术治疗费
        baseInfoMap.put("anst_fee",mrisCostDO.getZll04()); //麻醉费
        baseInfoMap.put("rgtrt_fee",mrisCostDO.getZll05()); //手术费
        baseInfoMap.put("rhab_fee",mrisCostDO.getKfl01()); //康复费
        baseInfoMap.put("tcm_trt_fee",mrisCostDO.getZyl01()); //中医治疗费
        baseInfoMap.put("wm_fee",mrisCostDO.getXyl01()); //西药费
        baseInfoMap.put("abtl_medn_fee",mrisCostDO.getZdl04()); //抗菌药物费用
        baseInfoMap.put("tcmpat_fee",mrisCostDO.getFy04()); //中成药费
        baseInfoMap.put("tcmherb_fee",mrisCostDO.getFy03()); //中药饮片费
        baseInfoMap.put("blo_fee",mrisCostDO.getXyzpl01()); //血费
        baseInfoMap.put("albu_fee",mrisCostDO.getXyzpl03()); // 球蛋白类制品费
        baseInfoMap.put("glon_fee", mrisCostDO.getXyzpl04()); // 凝血因子类制品费
        baseInfoMap.put("clotfac_fee",mrisCostDO.getXyzpl05()); // 细胞因子类制品费
        baseInfoMap.put("cyki_fee",mrisCostDO.getHcl01()); //检查用一次性医用材料费
        baseInfoMap.put("exam_dspo_matl_fee",mrisCostDO.getHcl02()); //治疗用一次性医用材料费
        baseInfoMap.put("trt_dspo_matl_fee", mrisCostDO.getHcl03()); //手术用一次性医用材料费
        baseInfoMap.put("oth_fee", mrisCostDO.getFy06()); //其他费
        baseInfoMap.put("vali_flag",Constants.SF.S); // 有效标志
        String caseClassification = mrisBaseInfoDTO.getCaseClassification();
        insureUnifiedCommonUtil.getEmptyErr(caseClassification,"病例分型值为空,请先维护");
        // 由于数据存的是 1 2 3 4  对应  A B  C  D
        if("1".equals(caseClassification)) {
            baseInfoMap.put("ctd","A"); // 病例分型
        }else if("2".equals(caseClassification)){
            baseInfoMap.put("ctd","B"); // 病例分型
        }else if("3".equals(caseClassification)){
            baseInfoMap.put("ctd","C"); // 病例分型
        }else{
            baseInfoMap.put("ctd","D"); // 病例分型
        }
        insureUnifiedCommonUtil.getEmptyErr(mrisBaseInfoDTO.getIptDrCode(),"住院医师国家代码为空,请先维护");
        baseInfoMap.put("ipt_dr_code",mrisBaseInfoDTO.getIptDrCode()); // 住院医师代码

        insureUnifiedCommonUtil.getEmptyErr(mrisBaseInfoDTO.getRespNursCode(),"责任护士国家代码为空,请先维护");
        baseInfoMap.put("resp_nurs_code",mrisBaseInfoDTO.getRespNursCode()); // 责任护士代码

        insureUnifiedCommonUtil.getEmptyErr(mrisBaseInfoDTO.getDeptdrtCode(),"科主任国家代码为空,请先维护");
        baseInfoMap.put("deptdrt_code",mrisBaseInfoDTO.getDeptdrtCode()); // 科主任代码

        insureUnifiedCommonUtil.getEmptyErr(mrisBaseInfoDTO.getAtddrCode(),"主治医生国家代码为空,请先维护");
        baseInfoMap.put("atddr_code",mrisBaseInfoDTO.getAtddrCode()); // 主治医生代码

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
        return inptVisit.getHospCode() + "_" + inptVisit.getId() + "_insureEmrInfo" ;
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
            diseinfoMap.put("abtl_medn_days","0");//	预防使用抗菌药物天数
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
