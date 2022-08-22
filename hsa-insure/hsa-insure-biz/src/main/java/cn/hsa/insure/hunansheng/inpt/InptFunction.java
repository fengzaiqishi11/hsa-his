package cn.hsa.insure.hunansheng.inpt;

import cn.hsa.hsaf.core.framework.util.DateUtil;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.RequestInsure;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.individualInpatient.dto.InsureIndividualInpatientDTO;
import cn.hsa.module.inpt.inregister.service.InptVisitService;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dto.*;
import cn.hsa.module.insure.module.entity.InsureConfigurationDO;
import cn.hsa.module.insure.module.service.InsureDiseaseMatchService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @Package_name: cn.hsa.insure.hunansheng.inpt
 * @Class_name: InptFunction
 * @Describe(描述): 湖南省住院医保接口对接
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2021/01/30 13:44
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component("hunansheng-inpt")
public class InptFunction {

    private static final String QIYANGCODE = "QI_YANG_CODE";
    @Resource
    private RequestInsure requestInsure;

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private InptVisitService inptVisitService;

    @Resource
    private InsureDiseaseMatchService insureDiseaseMatchService;

    /**
      * @method getSysParamDTO
      * @author powersi
      * @date 2022/8/17 10:55
      *	@description 	获取系统参数
      * @param  hospCode, code
      * @return cn.hsa.module.sys.parameter.dto.SysParameterDTO
      *
     **/
    private String getSysParamDTOValue(String hospCode, String code) {
        Map<String, Object> sysMap = new HashMap<>();
        sysMap.put("hospCode", hospCode);
        sysMap.put("code", code);
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(sysMap).getData();
        String value = null;
        if(sysParameterDTO != null){
            value = sysParameterDTO.getValue();
        }
        return value;
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

    /**
     * @Menthod BIZC131201
     * @Desrciption  通过个人标识取人员信息
     * @param insureIndividualBasicDTO 请求参数
     * @Author Ou·Mr
     * @Date 2021/1/30 13:48 
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> bizc131201(InsureIndividualBasicDTO insureIndividualBasicDTO) {
        String hospCode = insureIndividualBasicDTO.getHospCode();//医院编码
        String insureRegCode = insureIndividualBasicDTO.getInsureRegCode();//医保机构编码
        String isRemote = insureIndividualBasicDTO.getIsRemote();//是否异地
        String corpId = insureIndividualBasicDTO.getAab001(); //单位编码
        InsureConfigurationDO insureConfigurationDO = requestInsure.toConfig(hospCode,insureRegCode);
        // 获得祁阳骨科专用的中心编码  如果系统配置没有该参数 就用原来的code
        String sysParamDTOValue =  getSysParamDTOValue(hospCode, QIYANGCODE);
        String code = sysParamDTOValue != null ? sysParamDTOValue : insureConfigurationDO.getCode();
        Map<String,Object> httpParam = new HashMap<String,Object>();//入参
        if("02".equals(insureIndividualBasicDTO.getBka895())){
            httpParam.put("idcard",insureIndividualBasicDTO.getBka896());//查询条件
        }else{
            httpParam.put(insureIndividualBasicDTO.getBka895(),insureIndividualBasicDTO.getBka896());//查询条件
        }
        httpParam.put("hospital_id",insureConfigurationDO.getOrgCode());//医疗机构编码
        httpParam.put("biz_type",insureIndividualBasicDTO.getAka130());//业务类型
        httpParam.put("center_id",code);//医保中心编号
        httpParam.put("oper_staffid","000");//员工号
        httpParam.put("oper_centerid",code);//中心编码
        httpParam.put("oper_hospitalid",insureConfigurationDO.getOrgCode());//医疗机构编码
        // 如果是一个电脑号对应多个单位参保信息，需传入单位编码
        if (!StringUtils.isEmpty(corpId) && !"null".equals(corpId)) {
            httpParam.put("corp_id", corpId);
        }
        Map<String, Object> resultMap = new HashMap<String,Object>();//回参

        if(Constants.SF.F.equals(isRemote)) {
            httpParam.put("function_id", Constant.HuNanSheng.INPT.BIZC131201);//功能号
            resultMap = requestInsure.callHNS(hospCode,insureRegCode,httpParam);
        } else if(Constants.SF.S.equals(isRemote)) {
            httpParam.put("function_id", Constant.HuNanSheng.INPT.REMOTE_BIZC131201);//功能号
            httpParam.put("card_iden",insureIndividualBasicDTO.getCardIden());//卡识别码
            resultMap = requestInsure.callHNS(hospCode,insureRegCode,httpParam);
        }
        List<Map<String,Object>> resultList = new ArrayList<>();
        List<Map<String,Object>> list = (List<Map<String,Object>>)resultMap.get("personinfo");

        // 工伤生育信息
        List<Map<String,Object>> injuryorbirthList = (List<Map<String,Object>>)resultMap.get("injuryorbirthinfo");
        List<Map<String,Object>> injurList = new ArrayList<>();
        if (!ListUtils.isEmpty(injuryorbirthList)) {
            for (Map<String,Object> inj : injuryorbirthList) {
                Map<String,Object> map = new HashMap<>();
                map.put("aac003",inj.get("name"));
                map.put("bka042",inj.get("serial_pers"));
                map.put("aae030",inj.get("accident_date"));
                map.put("accidentDetail",inj.get("accident_detail"));
                map.put("alc022",inj.get("alc022"));
                map.put("identify_flag",inj.get("identify_flag"));
                map.put("serial_pers",inj.get("serial_pers"));
                map.put("injuryBorthSn",inj.get("serial_pers")); // 业务序列号
                injurList.add(map);
            }
            resultMap.put("injuryorbirthinfo",injurList);
        }

        for (Map map:list) {
            Map<String,Object> insureMap = new HashMap<>();
            insureMap.put("aaa027",map.get("center_id"));
            insureMap.put("aaa027_name",map.get("center_name"));
            insureMap.put("aab001",map.get("corp_id"));
            insureMap.put("aab019",map.get(""));
            insureMap.put("aab069",map.get("corp_name"));
            insureMap.put("aab301",map.get("post_code"));
            insureMap.put("aab999",map.get(""));
            insureMap.put("aac001",map.get("indi_id"));
            insureMap.put("aac002",map.get("idcard"));
            insureMap.put("aac003",map.get("name"));
            insureMap.put("aac004",map.get("sex"));
            insureMap.put("aac006",map.get("birthday"));
            insureMap.put("aac008",map.get("indi_join_sta"));
            insureMap.put("bka008",map.get("corp_name"));
            insureMap.put("aac148",map.get("urban_type"));
            insureMap.put("bacu18",map.get("last_balance"));
            insureMap.put("aae005",map.get("telephone"));
            insureMap.put("baa027",map.get("center_id"));
            insureMap.put("akb021_last",map.get("telephone"));
            insureMap.put("bka888",map.get("freeze_sta"));
            insureMap.put("bac001",map.get("official_code"));
            insureMap.put("bac001_name",map.get("official_name"));

            // 省异地医保 - 部分市医保接口人员类别使用pers_type_detail作为返参，部分市医保接口用pers_type作为返参
            insureMap.put("bka035",map.get("pers_type_detail"));
            Object bak035 = map.get("pers_type");
            if (bak035 != null && StringUtils.isNotEmpty(bak035.toString())) {
                insureMap.put("bka035",bak035);
            }
            insureMap.put("bka006",map.get("treatment_type"));
            insureMap.put("shbzkh",map.get("insr_code"));
            insureMap.put("telephone",map.get("telephone"));
            if (list.size() == 1 && !ListUtils.isEmpty(injurList)) {
                    Map<String,Object> inj = injurList.get(0); //
                    insureMap.put("injuryBorthSn",inj.get("serial_pers")); // 业务序列号
            }
            resultList.add(insureMap);
        }
        resultMap.put("personinfo",resultList);
        return resultMap;
    }


    /**
     * @Menthod BIZC131204
     * @Desrciption   校验保存普通住院入院信息（入院登记）
     * @param insureInptRegisterDTO 请求参数
     * @Author Ou·Mr
     * @Date 2021/1/30 13:55 
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String, Object> bizc131204(InsureInptRegisterDTO insureInptRegisterDTO) {
        String hospCode = insureInptRegisterDTO.getHospCode();//医院编码
        String insureRegCode = insureInptRegisterDTO.getInsureOrgCode();//医保机构编码
        InsureConfigurationDO insureConfigurationDO = requestInsure.toConfig(hospCode,insureRegCode);
        // 获得祁阳骨科专用的中心编码  如果系统配置没有该参数 就用原来的code
        String sysParamDTOValue = getSysParamDTOValue(hospCode, QIYANGCODE);
        String code = sysParamDTOValue != null ? sysParamDTOValue : insureConfigurationDO.getCode();
        Map<String,Object> httpParam = new HashMap<String,Object>();
        httpParam.put("function_id",Constant.HuNanSheng.INPT.BIZC131204);//功能号
        httpParam.put("oper_staffid",insureInptRegisterDTO.getUserCode());//员工号
        httpParam.put("oper_centerid",code);//中心编码
        httpParam.put("oper_hospitalid",insureConfigurationDO.getOrgCode());//医疗机构编码
        httpParam.put("center_id",code);//中心编码
        httpParam.put("indi_id",insureInptRegisterDTO.getAac001());//个人电脑号
        httpParam.put("pers_type",insureInptRegisterDTO.getBka035());//人员类别
        httpParam.put("in_area",insureInptRegisterDTO.getBka021());
        httpParam.put("in_area_name",insureInptRegisterDTO.getBka022());
        httpParam.put("biz_type",insureInptRegisterDTO.getAka130());//业务类型
        httpParam.put("treatment_type",insureInptRegisterDTO.getBka006());//待遇类型
        httpParam.put("reg_date",DateUtils.format(insureInptRegisterDTO.getAae036(),DateUtils.Y_M_DH_M_S));//入院登记时间（格式：YYYY-MM-DD hh:mm:ss）
        httpParam.put("reg_staff",insureInptRegisterDTO.getUserCode());//登记人员工号
        httpParam.put("reg_man",insureInptRegisterDTO.getUserName());//登记人姓名
        httpParam.put("reg_flag","0");//入院方式
        httpParam.put("bed_type","0");//床位类型
        httpParam.put("patient_id",insureInptRegisterDTO.getInNo());//住院号
        httpParam.put("rela_hosp_id",insureConfigurationDO.getOrgCode());//关联医疗机构编码
        httpParam.put("rela_serial_no","202102050002");//关联就医登记号*/
        httpParam.put("begin_date", DateUtils.format(insureInptRegisterDTO.getAae036(),DateUtils.Y_M_DH_M_S) );//入院时间（格式：YYYY-MM-DD HH24:MI:SS(24小时)）
        httpParam.put("doctor_no",insureInptRegisterDTO.getUserCode());//处方医生编号
        httpParam.put("doctor_name",insureInptRegisterDTO.getUserName());//处方医生姓名
        httpParam.put("serial_apply","");
        httpParam.put("firstdiag_flag","0");
        httpParam.put("foregift",insureInptRegisterDTO.getBka245());//预付款总额
        httpParam.put("in_dept",insureInptRegisterDTO.getAkf001());
        httpParam.put("in_dept_name",insureInptRegisterDTO.getBka020());
        httpParam.put("in_disease",insureInptRegisterDTO.getAkc193());//入院诊断(中心疾病编码(住院第一诊断))
        httpParam.put("disease",insureInptRegisterDTO.getBkz101());//入院诊断名称(中心疾病诊断名称)
        httpParam.put("biz_times","1");//本年住院次数
        httpParam.put("in_bed",insureInptRegisterDTO.getAke020());//入院病床编号
        httpParam.put("injury_borth_sn",insureInptRegisterDTO.getInjuryBorthSn());
        httpParam.put("aab001",insureInptRegisterDTO.getAab001());// 单位ID
        if (StringUtils.isEmpty(insureInptRegisterDTO.getAke020())) {
            httpParam.put("in_bed","未安床");//入院病床编号
        }

        // httpParam.put("in_disease_his",insureInptRegisterDTO.getBkz101());
        // httpParam.put("in_disease_his",insureInptRegisterDTO.getBkz101());
        // httpParam.put("ic_no","L47791295");


        List<Map<String,Object>> diagnoseinfoList = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> diagnoseinfos = insureInptRegisterDTO.getDiagnoseinfos();
        if (diagnoseinfos != null && !diagnoseinfos.isEmpty()){
            int index = 1;
            for (Map<String,Object> item : diagnoseinfos){
                Map<String,Object> diagnoseinfo = new HashMap<String,Object>();
                diagnoseinfo.put("diagnose_sn",index++);//诊断序号(从1开始按实际诊断序号递增)
                diagnoseinfo.put("diagnose_code","1");//诊断类型("1"：入院诊断 "2"：出院诊断)
                diagnoseinfo.put("icd",item.get("icd"));//诊断编码(医保中心诊断编码)
                diagnoseinfo.put("icd_his",item.get("diagnoseCode"));//医疗机构诊断编码
                diagnoseinfoList.add(diagnoseinfo);
            }

        }
        httpParam.put("diagnoseinfo",diagnoseinfoList);//多诊断信息
        Map<String,Object> resultMap = requestInsure.callHNS(hospCode,insureRegCode,httpParam);

        // serial_no 就医登记号
        // trade_no  发送方交易流水号
        List<Map<String,Object>> bizinfoList = (List<Map<String, Object>>) resultMap.get("bizinfo");
        Map map = bizinfoList.get(0);
        resultMap.put("aaz217",map.get("serial_no"));

        return resultMap;
    }

    /**
     * @Menthod BIZC131271
     * @Desrciption 提取个人业务信息
     * @param insureIndividualInpatientDTO
     * @Author Ou·Mr
     * @Date 2021/1/30 13:59
     * @Return cn.hsa.module.inpt.individualInpatient.dto.InsureIndividualInpatientDTO
     */
    public InsureIndividualInpatientDTO bizc131271(InsureIndividualInpatientDTO insureIndividualInpatientDTO) {
        String hospCode = insureIndividualInpatientDTO.getHospCode();//医院编码
        String insureRegCode = insureIndividualInpatientDTO.getRegCode();//医保注册编码
        InsureConfigurationDO insureConfigurationDO = requestInsure.toConfig(hospCode,insureRegCode);
        Map<String,Object> httpParam = new HashMap<String,Object>();//医保入参
        httpParam.put("function_id",Constant.HuNanSheng.INPT.BIZC131271);//功能号
        httpParam.put(insureIndividualInpatientDTO.getBka895(),insureIndividualInpatientDTO.getBka896());//查询条件
        httpParam.put("biz_type",insureIndividualInpatientDTO.getAka130());//业务类型
        Map<String, Object> resultMap = requestInsure.callHNS(hospCode,insureRegCode,httpParam);
        /*处理参数*/
        insureIndividualInpatientDTO = getIndividualInpatientProcessingData(resultMap,insureIndividualInpatientDTO);
        return insureIndividualInpatientDTO;
    }

    /**
     * @Menthod BIZC131205
     * @Desrciption 校验保存住院信息修改
     * @param insureInptRegisterDTO 入参
     * @Author Ou·Mr
     * @Date 2021/1/30 14:04 
     * @Return java.lang.Boolean
     */
    public Boolean bizc131205(InsureInptRegisterDTO insureInptRegisterDTO) {
        String hospCode = insureInptRegisterDTO.getHospCode();//医院编码
        String insureRegCode = insureInptRegisterDTO.getInsureOrgCode();//医保机构编码
        Map<String, Object> httpParam = new HashMap<String,Object>();//入参
        httpParam.put("function_id",Constant.HuNanSheng.INPT.BIZC131205);//功能号
        httpParam.put("serial_no",insureInptRegisterDTO.getAaz217());//就医登记号
        httpParam.put("indi_id",insureInptRegisterDTO.getAac001());//个人电脑号
        httpParam.put("biz_type",insureInptRegisterDTO.getAka130());//业务类型
        httpParam.put("reg_staff",insureInptRegisterDTO.getUserCode());//操作员工号
        httpParam.put("reg_man",insureInptRegisterDTO.getUserName());//操作员姓名
        httpParam.put("begin_date",DateUtils.format(insureInptRegisterDTO.getAae036(),DateUtils.Y_M_DH_M_S));//业务开始日期（格式：YYYY-MM-DD HH24:MI:SS(24小时)）
        httpParam.put("in_dept",insureInptRegisterDTO.getAkf001());//入院科室编号
        httpParam.put("in_dept_name",insureInptRegisterDTO.getBka020());//入院科室名称
        httpParam.put("in_area",insureInptRegisterDTO.getBka021());//入院病区编号
        httpParam.put("in_area_name",insureInptRegisterDTO.getBka022());//入院病区名称
        httpParam.put("in_bed",insureInptRegisterDTO.getAke020());//入院病床编号
        if (StringUtils.isEmpty(insureInptRegisterDTO.getAke020())) {
            httpParam.put("in_bed","未安床");//入院病床编号
        }
        httpParam.put("bed_type","0");//床位类型("0"：普通床位 "1"：急救 "2"：留观 "3"：高干)
        httpParam.put("patient_id",insureInptRegisterDTO.getAkc190());//住院号
        httpParam.put("old_patient_id",insureInptRegisterDTO.getAkc190());//原住院号
        httpParam.put("in_disease",insureInptRegisterDTO.getAkc193());//入院诊断(中心疾病编码)
        httpParam.put("disease",insureInptRegisterDTO.getBkz101());//入院诊断名称(中心疾病诊断名称)
        httpParam.put("treatment_type",insureInptRegisterDTO.getBka006());//待遇类别
        httpParam.put("remark",null);//备注
        requestInsure.callHNS(hospCode,insureRegCode,httpParam);
        return true;
    }

    /**
     * @Menthod bizc200024
     * @Desrciption 校验保存住院诊断信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/1/30 14:08 
     * @Return void
     */
    public void bizc200024(HashMap<String,Object> param){
        String hospCode = (String) param.get("hospCode");//医院编码
        String insureOrgCode = (String) param.get("insureOrgCode");//医保机构编码
        Map<String,Object> httpParam = new HashMap<String,Object>();
        httpParam.put("serial_no",null);//就医登记号
        httpParam.put("biz_type",null);//业务类型
        httpParam.put("input_staff",param.get("userCode"));//操作员工号
        httpParam.put("input_man",param.get("userName"));//操作员姓名
        List<Map<String,Object>> diagnoseinfo = new ArrayList<Map<String,Object>>();
        if (!diagnoseinfo.isEmpty()){
            for (Map<String,Object> item : diagnoseinfo){
                Map<String,Object> diagnoseinfoItem = new HashMap<String,Object>();
                diagnoseinfoItem.put("diagnose_sn",null);//诊断序号（同一集合中诊断序号不能重复）
                diagnoseinfoItem.put("diagnose_code",null);//诊断类别编码（1入院诊断，2出院诊断，3疑似诊断）
                diagnoseinfoItem.put("icd",null);//医保中心疾病编码
                diagnoseinfoItem.put("ICDname",null);//疾病
                diagnoseinfoItem.put("our_circs_code",null);//治疗情况编码（01治愈，02好转，03无效，04未愈，05死亡，99其它）
                diagnoseinfoItem.put("icd_his",null);//医疗机构疾病编码（医疗机构的疾病编码）
                diagnoseinfo.add(diagnoseinfoItem);
            }
        }
        httpParam.put("diagnoseinfo",diagnoseinfo);
        requestInsure.callHNS(hospCode,insureOrgCode,httpParam);
    }

    /**
     * @Menthod bizc110101
     * @Desrciption 校验医院项目或药品是否匹配
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/1/30 14:41 
     * @Return void
     */
    public void bizc110101(HashMap<String,Object> param){
        String hospCode ="0003";//医院编码
        String insureOrgCode = "431201";//医保机构编码
        Map<String,Object> httpParam = new HashMap<>();
        httpParam.put("oper_staffid","000");//员工号
        httpParam.put("oper_centerid","431201");//中心编码
        httpParam.put("oper_hospitalid","4330010001");//医疗机构编码
        httpParam.put("function_id",Constant.HuNanSheng.INPT.BIZC110101);//功能号
        httpParam.put("center_id","431201");//医保中心编码
        httpParam.put("Indi_id","746060");//个人电脑号
        httpParam.put("hospital_id","4330010001");//医疗机构编码
        httpParam.put("hosp_code","100079.001");//医院目录编码
        httpParam.put("match_type","1");//匹配类型
        httpParam.put("query_date",null);//费用发生时间
        httpParam.put("hospital_price",null);//单价
        httpParam.put("hospital_dosage",null);//数量
        httpParam.put("hospital_money",null);//金额
        httpParam.put("treatment_type","120");//待遇类型
        requestInsure.callHNS(hospCode,insureOrgCode,httpParam);
    }
    
    /**
     * @Menthod bizc131272
     * @Desrciption 校验保存住院费用明细信息
     * @param inptVisitDTO 入参
     * @Author Ou·Mr
     * @Date 2021/1/30 14:51 
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> bizc131272(InptVisitDTO inptVisitDTO) {
        List<Map<String,Object>> insureCostList = inptVisitDTO.getInsureCostList();//本次传输费用信息
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setRegCode(inptVisitDTO.getInsureRegCode());
        insureConfigurationDTO.setHospCode(inptVisitDTO.getHospCode());
        Map<String,Object> httpParam = new HashMap<String,Object>();
        String advieDoctorCode ="";
        String advieDoctorName = "";
        if(!ListUtils.isEmpty(insureCostList)){
            advieDoctorName = insureCostList.get(0).get("doctorName")==null ?"":insureCostList.get(0).get("doctorName").toString();
        }
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        // 获得祁阳骨科专用的中心编码  如果系统配置没有该参数 就用原来的code
        String sysParamDTOValue = getSysParamDTOValue(inptVisitDTO.getHospCode(), QIYANGCODE);
        String code = sysParamDTOValue != null ? sysParamDTOValue : insureConfigurationDTO.getCode();
        if(insureConfigurationDTO !=null){
            httpParam.put("oper_hospitalid",insureConfigurationDTO.getOrgCode());
            httpParam.put("oper_centerid",code);
        }
        httpParam.put("function_id", Constant.HuNanSheng.INPT.BIZC131272);//交易号
        httpParam.put("hospital_id", inptVisitDTO.getInsureRegCode());//医疗机构编码
        httpParam.put("indi_id",inptVisitDTO.getAac001());//个人电脑号
        httpParam.put("biz_type",inptVisitDTO.getAka130());//业务类型
        httpParam.put("serial_no",inptVisitDTO.getMedicalRegNo());//就医登记号
        httpParam.put("input_staff",inptVisitDTO.getCode());//录入人工号
        httpParam.put("input_man",inptVisitDTO.getCrteName());//录入人姓名
        httpParam.put("recipe_no","");//处方号
        httpParam.put("doctor_no","");//处方医生编号
        httpParam.put("doctor_name",advieDoctorName);//处方医生姓名
        httpParam.put("oper_staffid",inptVisitDTO.getCode());   // 操作员编码  员工工号
        List<Map<String,Object>> costParamList = new ArrayList<>();
        for (Map<String,Object> item : insureCostList){
            Map<String,Object> costItem = new HashMap<String,Object>();
            String itemStr =""; //项目药品类型
            // 说明是西药
            if("11".equals(item.get("insureMatchItemCode"))){
                itemStr ="1";
            }
            //中成药
            else if("12".equals(item.get("insureMatchItemCode"))){
                itemStr ="2";
            }
            //中草药
            else if("13".equals(item.get("insureMatchItemCode"))){
                itemStr ="3";
            }
            // 说明是项目
            else{
                itemStr ="0";
            }
            costItem.put("medi_item_type",itemStr);//项目药品类型
            costItem.put("his_item_code",item.get("hospItemCode"));//医院药品项目编码
            costItem.put("his_item_name",item.get("hospItemName"));//医院药品项目名称
            costItem.put("drug_standard_code",item.get("pqccItemId"));//药品本位码
            costItem.put("model","");//剂型
            costItem.put("factory","");//厂家
            costItem.put("standard","");//规格
            String now = DateUtils.format((Date) item.get("costTime"),DateUtils.Y_M_DH_M_S);
            costItem.put("fee_date",now);//费用发生时间(格式：YYYY-MM-DD HH24:MI:SS(24小时))
            //costItem.put("unit",item.get("dosage"));//计量单位
            costItem.put("price",item.get("price").toString());//单价（保留小数点4位）
            costItem.put("dosage",item.get("totalNum").toString());//用量（保留小数点4位）

            DecimalFormat df1 = new DecimalFormat("0.00");
            String realityPrice = df1.format(BigDecimalUtils.convert(item.get("realityPrice").toString()));
            costItem.put("money",realityPrice);
            costItem.put("usage_flag","0");//用药标志（"0"：普通 "1"：出院带药 "2"：抢救）
            costItem.put("usage_days", item.get("useDays") ==null ? 0 : item.get("useDays"));//出院带药天数

            if(Constants.ZTBZ.CH.equals(item.get("statusCode"))) {
                costItem.put("opp_serial_fee", "1"+SnowflakeUtils.getId().substring(12)+(int)(Math.random()*10));//对应费用序列号
            }else{
                costItem.put("opp_serial_fee","");//对应费用序列号（如果收费则为空，如果是退费则必须为对应收费的serial_fee）
            }
            costItem.put("hos_serial",item.get("id"));//医院费用序列号（医院费用的唯一标识）
            costItem.put("remark","");//备注（用法用量等说明）
            costItem.put("input_staff",inptVisitDTO.getCode());//录入人工号
            costItem.put("input_man",inptVisitDTO.getCrteName());//录入人姓名
            costItem.put("recipe_no","");//处方号
            costItem.put("doctor_no","");//处方医生编号
            costItem.put("doctor_name","");//处方医生姓名
            costItem.put("dept_name","");//执行科室名称
            costParamList.add(costItem);
        }
        httpParam.put("feeinfo",costParamList);//住院费用明细信息
        Map<String, Object> resultMap = requestInsure.callHNS(inptVisitDTO.getHospCode(), inptVisitDTO.getInsureRegCode(),httpParam);
        return resultMap;
    }

    /**
     * @Menthod bizc131253
     * @Desrciption 提取已保存的费用明细信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/1/30 14:56 
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> bizc131253(HashMap<String,Object> param){
        String hospCode = (String) param.get("hospCode");//医院编码
        String insureOrgCode = (String) param.get("insureOrgCode");//医保机构编码
        Map<String,Object> httpParam = new HashMap<String,Object>();
        httpParam.put("function_id",Constant.HuNanSheng.INPT.BIZC131253);//功能号
        httpParam.put("serial_no",null);//就医登记号
        Map<String,Object> httpResult = requestInsure.callHNS(hospCode,insureOrgCode,httpParam);
        return httpResult;
    }

    /**
     * @Menthod BIZC131254
     * @Desrciption 通过药品编码提取需退费及已退费的药品项目的信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/1/30 15:00 
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> bizc131254(HashMap<String,Object> param){
        String hospCode = (String) param.get("hospCode");//医院编码
        String insureOrgCode = (String) param.get("insureOrgCode");//医保机构编码
        Map<String,Object> httpParam = new HashMap<String,Object>();
        httpParam.put("function_id",Constant.HuNanSheng.INPT.BIZC131254);//功能号
        httpParam.put("serial_no",null);//就医登记号
        httpParam.put("his_item_code",null);//医院目录编码
        httpParam.put("medi_item_type",null);//药品项目类型
        Map<String,Object> httpResult = requestInsure.callHNS(hospCode,insureOrgCode,httpParam);
        return httpResult;
    }


    /**
     * @Menthod BIZC131255
     * @Desrciption 住院费用计算
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/5 10:44
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> bizc131255(HashMap<String,Object> param) {
        InptVisitDTO inptVisitDTO = (InptVisitDTO) param.get("inptVisit");//住院就诊信息
        InsureIndividualVisitDTO insureIndividualVisitDTO = (InsureIndividualVisitDTO) param.get("insureIndividualVisit");//医保就诊信息
        if (inptVisitDTO == null || insureIndividualVisitDTO == null){
            throw new AppException("未获取个人医保就诊信息。");
        }
        InsureConfigurationDO insureConfigurationDO = requestInsure.toConfig(insureIndividualVisitDTO.getHospCode(),insureIndividualVisitDTO.getInsureRegCode());
        // 获得祁阳骨科专用的中心编码
        String code = getSysParamDTOValue(inptVisitDTO.getHospCode(), QIYANGCODE);
        // 获取诊断信息
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("hospCode",inptVisitDTO.getHospCode());
        paramsMap.put("visitId",inptVisitDTO.getId());
        String s = code != null ? code : insureConfigurationDO.getCode();
        paramsMap.put("insureRegCode",s);
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
        String otherDiseaseCodes[] = new String[25];
        int i = 0;
        for(Map<String,Object> map : disaseList) {
            String isMain = MapUtils.get(map,"isMain");
            if ("1".equals(isMain)) {
                isMainDiseaseCode = MapUtils.get(map,"insureIllnessCode");
            } else {
                otherDiseaseCodes[i] = MapUtils.get(map,"insureIllnessCode");
                i++;
            }
        }

        Map<String,Object> httpParam = new HashMap<>();
        httpParam.put("function_id",Constant.HuNanSheng.INPT.BIZC131255);//功能号
        httpParam.put("corp_id",insureIndividualVisitDTO.getAab001());// 单位ID
        httpParam.put("corp_name",insureIndividualVisitDTO.getBka008());// 单位名称
        httpParam.put("oper_staffid",inptVisitDTO.getUserCode());//员工号
        httpParam.put("oper_centerid",s);//中心编码
        httpParam.put("oper_hospitalid",insureConfigurationDO.getOrgCode());//医疗机构编码
        if (Constants.IS_USER_ACCOUNT.S.equals(insureIndividualVisitDTO.getInsureAccoutFlag())) {
            httpParam.put("last_balance",insureIndividualVisitDTO.getAkc252());//本次业务个人帐户可用金额(系统默认使用个人帐户余额)
        } else {
            httpParam.put("last_balance","0");//本次业务个人帐户可用金额(系统默认使用个人帐户余额)
        }
        httpParam.put("serial_no",insureIndividualVisitDTO.getMedicalRegNo());//就医登记号
        httpParam.put("save_flag",Constants.SF.F);//保存标志（是否保存计算结果（0：否 1：是））
        httpParam.put("treatment_type",insureIndividualVisitDTO.getBka006());//待遇类别
        httpParam.put("end_disease",isMainDiseaseCode);//出院诊断
        httpParam.put("end_date",DateUtils.format(inptVisitDTO.getOutOperTime(),DateUtils.Y_M_DH_M_S));//出院日期（格式：YYYY-MM-DD HH24:MI:SS(24小时)）
        httpParam.put("reg_flag","");//生育就诊类型（生育住院不能为空，D：定额，P：普通消费 T：特殊情况）

        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setRegCode(inptVisitDTO.getInsureOrgCode());
        insureConfigurationDTO.setHospCode(inptVisitDTO.getHospCode());
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if(insureConfigurationDTO !=null) {
            httpParam.put("oper_staffid", inptVisitDTO.getUserCode());//员工号
            if (StringUtils.isEmpty(code)) {
                httpParam.put("oper_centerid", insureConfigurationDTO.getCode());//中心编码
            } else {
                httpParam.put("oper_centerid", code);//中心编码
            }
            httpParam.put("oper_hospitalid", insureConfigurationDTO.getOrgCode());//医疗机构编码
        }
       /* httpParam.put("civil_affairs_subsidy",null);//民政医疗补助（试算时传入民政补助金额，会返回401基金。（目前仅限常德一站式结算使用））
        httpParam.put("health_poverty_flag",null);//健康扶贫资助（试算时传入健康扶贫资助金额，会返回405基金。（目前仅限常德一站式结算使用））*/
        Map<String,Object> httpResult = requestInsure.callHNS((String)param.get("hospCode"),(String) param.get("insureRegCode"),httpParam);
        List<Map<String,Object>> payInfoList = (List<Map<String,Object>>) httpResult.get("payinfo");
        if (ListUtils.isEmpty(payInfoList)) {
            throw new AppException("未获取到费用信息");
        }

        BigDecimal akc264 = BigDecimal.ZERO; // 医疗总费用
        BigDecimal ake039 = BigDecimal.ZERO; // 医疗保险统筹基金
        BigDecimal akb066 = BigDecimal.ZERO; // 个人账户
        BigDecimal ake029 = BigDecimal.ZERO; // 医疗保险大病互助基金
        BigDecimal ake035 = BigDecimal.ZERO; // 医疗保险公务员补助
        BigDecimal akb067 =  BigDecimal.ZERO; // 现金
        BigDecimal bka842 =  BigDecimal.ZERO; // 医院支付金

        BigDecimal bka840 = BigDecimal.ZERO; // 其他支付
        BigDecimal other202 =  BigDecimal.ZERO; // 离休基金
        BigDecimal other401 =  BigDecimal.ZERO; // 医疗补偿金
        BigDecimal other501 =  BigDecimal.ZERO; // 工伤保险基金
        BigDecimal other511 =  BigDecimal.ZERO; // 生育保险基金
        BigDecimal other801 =  BigDecimal.ZERO; // 居民统筹基金
        BigDecimal other901 =  BigDecimal.ZERO; // 意外伤害基金
        BigDecimal other803 =  BigDecimal.ZERO; // 居民大病保险基金
        Map<String,Object> resultMap = new HashMap<>();
        for (Map map : payInfoList) {
            String fundId = String.valueOf(map.get("fund_id")); // 基金类型
            String realPay = String.valueOf(map.get("real_pay")); // 基金金额
            akc264 = BigDecimalUtils.add(akc264.toString(),realPay);
            switch (fundId) {
                case "001" :
                    ake039 = BigDecimalUtils.add(ake039.toString(),realPay);
                    break;
                case "003" :
                    akb066 = BigDecimalUtils.add(akb066.toString(),realPay);
                    break;
                case "201" :
                    ake029 = BigDecimalUtils.add(ake029.toString(),realPay);
                    break;
                case "202" :
                    other202 = BigDecimalUtils.add(other202.toString(),realPay);
                    break;
                case "301" :
                    ake035 = BigDecimalUtils.add(ake035.toString(),realPay);
                    break;
                case "401" :
                    other401 = BigDecimalUtils.add(other401.toString(),realPay);
                    break;
                case "501" :
                    other501 = BigDecimalUtils.add(other501.toString(),realPay);
                    break;
                case "511" :
                    other511 = BigDecimalUtils.add(other511.toString(),realPay);
                    break;
                case "801" :
                    other801 = BigDecimalUtils.add(other801.toString(),realPay);
                    break;
                case "901" :
                    other901 = BigDecimalUtils.add(other901.toString(),realPay);
                    break;
                case "999" :
                    akb067 = BigDecimalUtils.add(akb067.toString(),realPay);
                    break;
                case "996" :
                    bka842 = BigDecimalUtils.add(bka842.toString(),realPay);
                    break;
                case "803" :
                    other803 = BigDecimalUtils.add(other803.toString(),realPay);
                    break;
                default:
                    break;
            }
        }
        resultMap.put("akc264",akc264.toString()); // 医疗费用总金额
        resultMap.put("akb066",akb066.toString()); // 医疗保险个人账户
        resultMap.put("ake039",ake039.toString()); // 医疗保险个人账户
        resultMap.put("ake029",ake029.toString()); // 医疗保险个人账户
        resultMap.put("ake035",ake035.toString()); // 医疗保险公务员补助
        resultMap.put("akb067",akb067.toString()); // 个人现金自付
        resultMap.put("bka831",akb067.toString()); // 个人现金自付
        BigDecimal bigDecimal = new BigDecimal(0);
        bka840 = bigDecimal.add(other202).add(other401).add(other501).add(other511).add(other801).add(other901).add(other803);
        resultMap.put("bka840",bka840.toString()); // 其他基金支付
        resultMap.put("bka832",BigDecimalUtils.subtract(akc264,akb067).toString());
        return resultMap;
    }

    /**
     * @Menthod BIZC131256
     * @Desrciption 住院出院结算
     * @param param 必传值：hospCode:医院编码、visitId:就诊id、insureRegCode:医保编码
     * @Author Ou·Mr
     * @Date 2020/11/5 10:56
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> bizc131256(HashMap<String,Object> param){
        InptVisitDTO inptVisitDTO = (InptVisitDTO) param.get("inptVisit");
        InsureIndividualVisitDTO insureIndividualVisitDTO = (InsureIndividualVisitDTO) param.get("insureIndividualVisit");
        Map<String,Object> httpParam = new HashMap<>();
        // 获得祁阳骨科专用的中心编码
        String code = getSysParamDTOValue(inptVisitDTO.getHospCode(), QIYANGCODE);
        // 获取诊断信息
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("hospCode",inptVisitDTO.getHospCode());
        paramsMap.put("visitId",inptVisitDTO.getId());
        paramsMap.put("insureRegCode",insureIndividualVisitDTO.getInsureOrgCode());
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

        httpParam.put("function_id",Constant.HuNanSheng.INPT.BIZC131256);
        httpParam.put("oper_staffid",inptVisitDTO.getUserCode());//员工号
        httpParam.put("save_flag","3");//保存标志（"3"：出院登记和结算）
        httpParam.put("serial_no",insureIndividualVisitDTO.getMedicalRegNo());//就医登记号
        httpParam.put("indi_id",insureIndividualVisitDTO.getAac001());//个人电脑号
        if (Constants.IS_USER_ACCOUNT.S.equals(insureIndividualVisitDTO.getInsureAccoutFlag())) {
            httpParam.put("last_balance",insureIndividualVisitDTO.getAkc252());//本次业务个人帐户可用金额(系统默认使用个人帐户余额)
        } else {
            httpParam.put("last_balance","0");//本次业务个人帐户可用金额(系统默认使用个人帐户余额)
        }
        httpParam.put("end_disease",isMainDiseaseCode);//出院疾病（中心疾病编码）
        httpParam.put("end_disease_name",isMainDiseaseName);//出院诊断名称（中心疾病名称）
        httpParam.put("end_date",DateUtils.format(inptVisitDTO.getOutTime(),DateUtils.Y_M_DH_M_S));//出院日期（格式：YYYY-MM-DD HH24:MI:SS(24小时)）
        httpParam.put("fin_disease1",otherDiseaseCodes[0]);//第一副诊断（中心疾病编码）
        httpParam.put("fin_disease2",otherDiseaseCodes[1]);//第二副诊断（中心疾病编码）
        httpParam.put("fin_info",Constant.Xiangtan.DEFAULTVAL.BKA066.get(inptVisitDTO.getOutSituationCode()));//出院详情（好转 其他 死亡 未愈 无效 治愈 转科 转院（病人要求） 转院（医院要求））
        httpParam.put("staff_id",inptVisitDTO.getUserCode());//操作员工号
        httpParam.put("staff_name",inptVisitDTO.getOutOperName());//操作员姓名
        httpParam.put("treatment_type",insureIndividualVisitDTO.getBka006());//待遇类别
        httpParam.put("bill_no","");//单据号
        httpParam.put("reg_flag","");//生育就诊类型（生育住院不能为空，D：定额，P：普通消费 T：特殊情况（只有省直需要传此参数））
        httpParam.put("reg_info","");//生育疾病类型（生育住院不能为空（见*说明1），（只有省直需要传此参数））
        httpParam.put("serial_apply","");//特殊情况对应的申请序号（生育住院为T（特殊情况）时不能为空，获取对应参数值参见：BIZC131275（只有省直需要传此参数））
        httpParam.put("cash_money","0.00");//刷卡金额（格式：0.00）
        httpParam.put("doctor_no","");//处方医生编号（若医保中心出院结算需要校验定岗医师,则该参数为必填）
        httpParam.put("doctor_name","");//处方医生姓名（若医保中心出院结算需要校验定岗医师,则该参数为必填）
        httpParam.put("fin_disease_his","");//医疗机构疾病诊断（医疗机构疾病编码）
        httpParam.put("fin_disease1_his","");//医疗机构第一副诊断（医疗机构疾病编码）
        httpParam.put("fin_disease2_his","");//医疗机构第二副诊断（医疗机构疾病编码））
        httpParam.put("aka999","1"); // 是否垫付伙食费(默认是)
        httpParam.put("visitId",inptVisitDTO.getId()); // 就诊ID

        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setRegCode(inptVisitDTO.getInsureOrgCode());
        insureConfigurationDTO.setHospCode(inptVisitDTO.getHospCode());
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if(insureConfigurationDTO !=null) {
            httpParam.put("oper_staffid", inptVisitDTO.getUserCode());//员工号
            if (StringUtils.isEmpty(code)){
                httpParam.put("oper_centerid", insureConfigurationDTO.getCode());//中心编码
            }else{
                httpParam.put("oper_centerid", code);//中心编码
            }
            httpParam.put("oper_hospitalid", insureConfigurationDTO.getOrgCode());//医疗机构编码
        }
        Map<String,Object> httpResult = requestInsure.callHNS((String)param.get("hospCode"),(String) param.get("insureRegCode"),httpParam);
        return httpResult;
    }

    /**
     * @Menthod BIZC131275
     * @Desrciption 提取生育特殊情况申请审核信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/1/30 15:12 
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> bizc131275(HashMap<String,Object> param){
        String hospCode = (String) param.get("hospCode");//医院编码
        String insureOrgCode = (String) param.get("insureOrgCode");//医保机构编码
        Map<String,Object> httpParam = new HashMap<String,Object>();
        httpParam.put("serial_no",null);//就医登记号
        httpParam.put("indi_id",null);//个人电脑号
        httpParam.put("audit_flag",null);//审核标志
        Map<String,Object> httpResult = requestInsure.callHNS(hospCode,insureOrgCode,httpParam);
        return httpResult;
    }

    /**
     * @Menthod BIZC200101
     * @Desrciption 提取普通住院业务结算信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/1/30 15:46 
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> bizc200101(HashMap<String,Object> param){
        String hospCode = (String) param.get("hospCode");//医院编码
        String insureOrgCode = (String) param.get("insureOrgCode");//医保机构编码
        Map<String,Object> httpParam = new HashMap<String,Object>();
        httpParam.put("serial_no",null);//业务序列号
        Map<String,Object> httpResult = requestInsure.callHNS(hospCode,insureOrgCode,httpParam);
        return httpResult;
    }

    /**
     * @Menthod BIZC200111
     * @Desrciption 提取工伤住院业务结算信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/1/30 15:49 
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> bizc200111(HashMap<String,Object> param){
        String hospCode = (String) param.get("hospCode");//医院编码
        String insureOrgCode = (String) param.get("insureOrgCode");//医保机构编码
        Map<String,Object> httpParam = new HashMap<String,Object>();
        httpParam.put("serial_no",null);//业务序列号
        Map<String,Object> httpResult = requestInsure.callHNS(hospCode,insureOrgCode,httpParam);
        return httpResult;
    }

    /**
     * @Method bizh120004
     * @Desrciption  湖南省医保，费用上传删除接口
     * @Param param
     *
     * @Author fuhui
     * @Date   2021/2/9 11:44
     * @Return
    **/
    public Boolean bizh120004(HashMap<String,Object> httpParam){
        InsureIndividualVisitDTO insureIndividualVisitDTO =(InsureIndividualVisitDTO) httpParam.get("insureIndividualVisitDTO");
        // 获得祁阳骨科专用的中心编码
        String code = getSysParamDTOValue(insureIndividualVisitDTO.getHospCode(), QIYANGCODE);
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setRegCode(insureIndividualVisitDTO.getInsureRegCode());
        insureConfigurationDTO.setHospCode(insureIndividualVisitDTO.getHospCode());
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        String s = code != null ? code : insureConfigurationDTO.getCode();
        if(insureConfigurationDTO !=null){
            httpParam.put("oper_hospitalid",insureConfigurationDTO.getOrgCode());
            httpParam.put("oper_centerid",s);
        }
        httpParam.put("function_id", Constant.HuNanSheng.INPT.BIZC131274);//交易号
        httpParam.put("hospital_id", insureConfigurationDTO.getOrgCode());//医疗机构编码
        httpParam.put("serial_no",insureIndividualVisitDTO.getMedicalRegNo());//就医登记号
        httpParam.put("input_staff",httpParam.get("code").toString());//录入人工号
        httpParam.put("input_man",httpParam.get("crteName").toString());//录入人姓名
        httpParam.put("fin_staff", httpParam.get("code").toString() );
        httpParam.put("fin_man", httpParam.get("crteName").toString() );
        httpParam.put("oper_staffid",httpParam.get("code").toString());   // 操作员编码  员工工号
        Map map = requestInsure.callHNS(insureIndividualVisitDTO.getHospCode(), insureIndividualVisitDTO.getInsureRegCode(), httpParam);
        return true ;
    }

    /* //TODO 异地住院 start */

    /**
     * @Menthod Remote_BIZC131201
     * @Desrciption 通过个人标识取人员信息
     * @param insureIndividualBasicDTO 请求参数
     * @Author Ou·Mr
     * @Date 2021/1/30 16:04
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> remote_bizc131201(InsureIndividualBasicDTO insureIndividualBasicDTO) {
        String hospCode = insureIndividualBasicDTO.getHospCode();//医院编码
        String insureRegCode = insureIndividualBasicDTO.getInsureRegCode();//医保机构编码
        String isRemote = insureIndividualBasicDTO.getIsRemote();//是否异地
        InsureConfigurationDO insureConfigurationDO = requestInsure.toConfig(hospCode,insureRegCode);
        // 获得祁阳骨科专用的中心编码
        String code = getSysParamDTOValue(hospCode, QIYANGCODE);
        String s = code != null ? code : insureConfigurationDO.getCode();
        Map<String,Object> httpParam = new HashMap<String,Object>();//入参
        httpParam.put("oper_staffid", insureIndividualBasicDTO.getUserCode());//员工号
        httpParam.put("oper_centerid", s);//中心编码
        httpParam.put("oper_hospitalid", insureConfigurationDO.getOrgCode());//医疗机构编码
        httpParam.put(insureIndividualBasicDTO.getBka895(),insureIndividualBasicDTO.getBka896());//查询条件
        httpParam.put("hospital_id",insureConfigurationDO.getOrgCode());//医疗机构编码
        httpParam.put("biz_type",insureIndividualBasicDTO.getAka130());//业务类型
        httpParam.put("center_id",s);//医保中心编号
        Map<String, Object> resultMap = new HashMap<String,Object>();//回参
        if(Constants.SF.F.equals(isRemote)) {
            httpParam.put("function_id", Constant.HuNanSheng.INPT.BIZC131201);//功能号
            resultMap = requestInsure.callHNS(hospCode,insureRegCode,httpParam);
        } else if(Constants.SF.S.equals(isRemote)) {
            httpParam.put("function_id", Constant.HuNanSheng.INPT.REMOTE_BIZC131201);//功能号
            httpParam.put("card_iden",insureIndividualBasicDTO.getCardIden());//卡识别码
            resultMap = requestInsure.callHNS(hospCode,insureRegCode,httpParam);
        }
        return resultMap;
    }

    /**
     * @Menthod remote_bizc131204
     * @Desrciption 校验保存异地就医入院信息
     * @param insureRemoteInptRegisterDTO 请求参数
     * @Author Ou·Mr
     * @Date 2021/1/30 16:14 
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> remote_bizc131204(InsureRemoteInptRegisterDTO insureRemoteInptRegisterDTO) {
        String hospCode = insureRemoteInptRegisterDTO.getHospCode();//医院编码
        String insureRegCode = insureRemoteInptRegisterDTO.getInsureOrgCode();//医保机构编码
        InsureConfigurationDO insureConfigurationDO = requestInsure.toConfig(hospCode,insureRegCode);
        Map<String,Object> httpParam = new HashMap<String,Object>();
        httpParam.put("function_id",Constant.HuNanSheng.INPT.REMOTE_BIZC131204);//功能号
        httpParam.put("oper_staffid", insureRemoteInptRegisterDTO.getAae011());//员工号
        httpParam.put("oper_centerid", insureConfigurationDO.getCode());//中心编码
        httpParam.put("oper_hospitalid", insureConfigurationDO.getOrgCode());//医疗机构编码

        // 异地人员使用当地参保地医保中心编码
        httpParam.put("center_id",insureRemoteInptRegisterDTO.getAaa027());//中心编码
        httpParam.put("card_iden",insureRemoteInptRegisterDTO.getCardIden());//卡识别码
        httpParam.put("indi_id",insureRemoteInptRegisterDTO.getAac001());//个人电脑号
        httpParam.put("pers_type",insureRemoteInptRegisterDTO.getBka035());//人员类别
        httpParam.put("biz_type",insureRemoteInptRegisterDTO.getAka130());//业务类型
        httpParam.put("treatment_type",insureRemoteInptRegisterDTO.getBka006());//待遇类型
        httpParam.put("reg_date",insureRemoteInptRegisterDTO.getAae030());//入院登记时间（格式：YYYY-MM-DD hh:mm:ss）
        httpParam.put("reg_staff",insureRemoteInptRegisterDTO.getAae011());//登记人员工号
        httpParam.put("reg_man",insureRemoteInptRegisterDTO.getBka015());//登记人姓名
        httpParam.put("reg_flag","0");//入院方式
        httpParam.put("serial_apply","");//业务申请序列号
        httpParam.put("rela_hospital_id","");//关联医疗机构编码
        httpParam.put("rela_serial_no","");//关联就医登记号
        httpParam.put("begin_date",insureRemoteInptRegisterDTO.getAae030());//入院时间（格式：YYYY-MM-DD HH24:MI:SS(24小时)）
        httpParam.put("injury_borth_sn",insureRemoteInptRegisterDTO.getInjuryBorthSn());//工伤个人业务序号（或生育资格认定号)如果是工伤住院业务，biz_type＝42，必须传入该值。(对应获取人员信息中的serial_bo_no)
        httpParam.put("biz_times","");//本年住院次数
        httpParam.put("in_dept",insureRemoteInptRegisterDTO.getAkf001());//入院科室编号
        httpParam.put("in_dept_name",insureRemoteInptRegisterDTO.getBka020());//入院科室名称
        httpParam.put("in_area",insureRemoteInptRegisterDTO.getBka021());//入院病区编号
        httpParam.put("in_area_name",insureRemoteInptRegisterDTO.getBka022());//入院病区名称
        httpParam.put("in_bed",insureRemoteInptRegisterDTO.getAke020());//入院病床编号
        httpParam.put("bed_type","0");//床位类型
        httpParam.put("patient_id",insureRemoteInptRegisterDTO.getAkc190());//住院号
        httpParam.put("foregift",insureRemoteInptRegisterDTO.getBka245());//预付款总额
        httpParam.put("in_disease",insureRemoteInptRegisterDTO.getAkc193());//入院诊断(中心疾病编码(住院第一诊断))
        httpParam.put("disease","");//入院诊断名称(中心疾病诊断名称)
        httpParam.put("ic_flag",insureRemoteInptRegisterDTO.getBka036());//用卡标志（"0"：不使用IC卡 "1"：使用IC卡）
        if ("1".equals(insureRemoteInptRegisterDTO.getBka036())) {
            httpParam.put("ic_no",insureRemoteInptRegisterDTO.getBka100());//IC卡号（读卡录入的人员必须输入卡号）
        }
        httpParam.put("remark","");//病情备注
        httpParam.put("name",insureRemoteInptRegisterDTO.getAac003());//姓名
        httpParam.put("sex",insureRemoteInptRegisterDTO.getAac004());//性别
        httpParam.put("idcard",insureRemoteInptRegisterDTO.getAac002());//身份证号码
        httpParam.put("corp_id",insureRemoteInptRegisterDTO.getAab001());//单位编码
        httpParam.put("corp_name",insureRemoteInptRegisterDTO.getBka008());//单位名称
        httpParam.put("telephone",insureRemoteInptRegisterDTO.getAae005());//联系电话
        httpParam.put("birthday","");//出生日期
        httpParam.put("office_grade",insureRemoteInptRegisterDTO.getBac001());//公务员级别
        StringBuffer str = new StringBuffer(SnowflakeUtils.getId());
        String feeId = str.reverse().substring(5);
        httpParam.put("fee_batch",feeId);//费用批次
//        httpParam.put("card_iden",insureRemoteInptRegisterDTO.getCardIden());//卡识别码

        List<Map<String,Object>> diagnosingList = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> diagnosing = insureRemoteInptRegisterDTO.getBka026info();
        if (!ListUtils.isEmpty(diagnosing)){
            int index = 1;
            for (Map<String,Object> item : diagnosing){
                Map<String,Object> diagnosed = new HashMap<String,Object>();
                diagnosed.put("diagnose_sn",index);//诊断序号(从1开始按实际诊断序号递增)
                diagnosed.put("diagnose_code","1");//诊断类型("1"：入院诊断 "2"：出院诊断)
                diagnosed.put("icd",item.get("icd"));//诊断编码(医保中心诊断编码)
                diagnosingList.add(diagnosed);
                index++;
            }
        }
        httpParam.put("diagnoseinfo",diagnosingList);//多诊断信息
        Map<String,Object> resultMap = requestInsure.callHNS(hospCode,insureRegCode,httpParam);
        List<Map<String,Object>> bingoList = MapUtils.get(resultMap,"bizinfo");
        Map map = bingoList.get(0);
        resultMap.put("aaz217",map.get("serial_no"));
        return resultMap;

    }


    /**
     * @Menthod remote_bizc131251
     * @Desrciption 提取异地就医个人业务信息
     * @param insureIndividualInpatientDTO 请求参数
     * @Author Ou·Mr
     * @Date 2021/1/30 16:16 
     * @Return cn.hsa.module.inpt.individualInpatient.dto.InsureIndividualInpatientDTO
     */
    public InsureIndividualInpatientDTO remote_bizc131251(InsureIndividualInpatientDTO insureIndividualInpatientDTO) {
        Map<String,Object> httpParam = new HashMap<String,Object>();
        httpParam.put("function_id",Constant.HuNanSheng.INPT.REMOTE_BIZC131251);//功能号
        httpParam.put(insureIndividualInpatientDTO.getBka895(),insureIndividualInpatientDTO.getBka896());//查询条件
        httpParam.put("biz_type",insureIndividualInpatientDTO.getAka130());//业务类型（"12"：住院）
        Map<String, Object> resultMap = requestInsure.callHNS(insureIndividualInpatientDTO.getHospCode(),insureIndividualInpatientDTO.getRegCode(),httpParam);
        /*处理参数*/
        insureIndividualInpatientDTO = getIndividualInpatientProcessingData(resultMap,insureIndividualInpatientDTO);
        return insureIndividualInpatientDTO;
    }

    
    /**
     * @Menthod remote_bizc131252
     * @Desrciption 校验保存住院费用明细信息(异地)
     * @param inptVisitDTO 请求参数
     * @Author Ou·Mr
     * @Date 2021/1/30 16:22 
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> remote_bizc131252(InptVisitDTO inptVisitDTO){
        Map<String,Object> httpParam = new HashMap<String,Object>();
        httpParam.put("function_id", Constant.HuNanSheng.INPT.REMOTE_BIZC131252);//交易号
        httpParam.put("indi_id",inptVisitDTO.getAac001());//个人电脑号
        httpParam.put("biz_type",inptVisitDTO.getAka130());//业务类型
        httpParam.put("serial_no",inptVisitDTO.getMedicalRegNo());//就医登记号
        httpParam.put("input_staff",inptVisitDTO.getCode());//录入人工号
        httpParam.put("input_man",inptVisitDTO.getCrteName());//录入人姓名
        httpParam.put("card_iden",inptVisitDTO.getCardIden());//卡识别码
        httpParam.put("recipe_no","");//处方号
        httpParam.put("doctor_no","");//处方医生编号
        httpParam.put("doctor_name","");//处方医生姓名

        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setRegCode(inptVisitDTO.getInsureRegCode());
        insureConfigurationDTO.setHospCode(inptVisitDTO.getHospCode());
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if(insureConfigurationDTO !=null) {
            httpParam.put("oper_staffid", inptVisitDTO.getUserCode());//员工号
            httpParam.put("oper_centerid", insureConfigurationDTO.getCode());//中心编码
            httpParam.put("oper_hospitalid", insureConfigurationDTO.getOrgCode());//医疗机构编码
        }


        List<Map<String,Object>> insureCostList = inptVisitDTO.getInsureCostList();//本次传输费用信息
        List<Map<String,Object>> costParamList = new ArrayList<Map<String,Object>>();
        for (Map<String,Object> item : insureCostList){
            Map<String,Object> costItem = new HashMap<String,Object>();
            String itemStr =""; //项目药品类型
            // 说明是西药
            if("11".equals(item.get("insureMatchItemCode"))){
                itemStr ="1";
            }
            //中成药
            else if("12".equals(item.get("insureMatchItemCode"))){
                itemStr ="2";
            }
            //中草药
            else if("13".equals(item.get("insureMatchItemCode"))){
                itemStr ="3";
            }
            // 说明是项目
            else{
                itemStr ="0";
            }
            //costItem.put("center_id",insureConfigurationDTO.getCode());
            costItem.put("medi_item_type",itemStr);//项目药品类型
            costItem.put("stat_type",itemStr);//项目药品类型
            costItem.put("his_item_code",item.get("hospItemCode"));//医院药品项目编码
            costItem.put("his_item_name",item.get("hospItemName"));//医院药品项目名称
            costItem.put("item_code",item.get("insureItemCode"));//中心药品项目编码
            costItem.put("item_name",item.get("insureItemName"));//中心药品项目名称
            costItem.put("drug_standard_code",item.get("pqccItemId"));//药品本位码
            costItem.put("model",item.get("prepCode"));//剂型
            costItem.put("factory",null);//厂家
            costItem.put("standard",item.get("spec"));//规格
            String now = DateUtils.format((Date) item.get("costTime"),DateUtils.Y_M_DH_M_S);
            costItem.put("fee_date",now);//费用发生时间(格式：YYYY-MM-DD HH24:MI:SS(24小时))
            costItem.put("unit","");//计量单位
            costItem.put("price",item.get("price").toString());//单价（保留小数点4位）
            costItem.put("dosage",item.get("totalNum").toString());//用量（保留小数点4位）

            DecimalFormat df1 = new DecimalFormat("0.00");
            String realityPrice = df1.format(BigDecimalUtils.convert(item.get("realityPrice").toString()));
            costItem.put("money",realityPrice);//金额（保留小数点2位 单价×数量=金额）
            costItem.put("usage_flag","0");//用药标志（"0"：普通 "1"：出院带药 "2"：抢救）
            costItem.put("usage_days","");//出院带药天数
            costItem.put("hos_serial",item.get("id"));//医院费用序列号（医院费用的唯一标识）
            costItem.put("remark","");//备注（用法用量等说明）
            costItem.put("make_flag","");//非工伤费用标志（1：工伤住院业务使用，表示录入的费用为非工伤费用，非工伤费用全部现金支付。该标志用于识别工伤住院期间发生非工伤的医疗费）
            if(Constants.ZTBZ.CH.equals(item.get("statusCode"))) {
                // costItem.put("opp_serial_fee", "1"+SnowflakeUtils.getId().substring(12)+(int)(Math.random()*10));//对应费用序列号
                costItem.put("opp_serial_fee", item.get("oldCostId"));//对应费用序列号
            }else{
                costItem.put("opp_serial_fee","");//对应费用序列号（如果收费则为空，如果是退费则必须为对应收费的serial_fee）
            }
            //costItem.put("stat_type","001");
            costParamList.add(costItem);
        }
        httpParam.put("feeinfo",costParamList);//住院费用明细信息
        Map<String, Object> resultMap = requestInsure.callHNS(inptVisitDTO.getHospCode(), inptVisitDTO.getInsureRegCode(), httpParam);
        return resultMap;
    }

    /**
     * @Method bizh120004
     * @Desrciption  湖南省医保，费用上传删除接口(异地)
     * @Param param
     *
     * @Author fuhui
     * @Date   2021/2/9 11:44
     * @Return
     **/
    public Boolean remote_bizc131274(HashMap<String,Object> httpParam) {
        InsureIndividualVisitDTO insureIndividualVisitDTO =(InsureIndividualVisitDTO) httpParam.get("insureIndividualVisitDTO");
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setRegCode(insureIndividualVisitDTO.getInsureRegCode());
        insureConfigurationDTO.setHospCode(insureIndividualVisitDTO.getHospCode());
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if(insureConfigurationDTO !=null){
            httpParam.put("oper_hospitalid",insureConfigurationDTO.getOrgCode());
            httpParam.put("oper_centerid",insureConfigurationDTO.getCode());
        }
        httpParam.put("function_id", Constant.HuNanSheng.INPT.REMOTE_BIZC131274);//交易号
        httpParam.put("hospital_id", insureConfigurationDTO.getOrgCode());//医疗机构编码
        httpParam.put("serial_no",insureIndividualVisitDTO.getMedicalRegNo());//就医登记号
        httpParam.put("input_staff",httpParam.get("code").toString());//录入人工号
        httpParam.put("input_man",httpParam.get("crteName").toString());//录入人姓名
        httpParam.put("card_iden",insureIndividualVisitDTO.getCardIden());//卡识别码
        httpParam.put("fin_staff", httpParam.get("code").toString() );
        httpParam.put("fin_man", httpParam.get("crteName").toString() );
        httpParam.put("oper_staffid",httpParam.get("code").toString());   // 操作员编码  员工工号
        Map map = requestInsure.callHNS(insureIndividualVisitDTO.getHospCode(), insureIndividualVisitDTO.getInsureRegCode(), httpParam);
        return true ;
    }

    /**
     * @Menthod Remote_BIZC131253
     * @Desrciption 提取已保存的费用明细信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/1/30 14:56
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> remote_bizc131253(HashMap<String,Object> param){
        String hospCode = (String) param.get("hospCode");//医院编码
        String insureOrgCode = (String) param.get("insureOrgCode");//医保机构编码
        Map<String,Object> httpParam = new HashMap<String,Object>();
        httpParam.put("function_id",Constant.HuNanSheng.INPT.REMOTE_BIZC131253);
        httpParam.put("serial_no",null);//就医登记号
        Map<String,Object> httpResult = requestInsure.callHNS(hospCode,insureOrgCode,httpParam);
        return httpResult;
    }


    /**
     * @Menthod Remote_BIZC131254
     * @Desrciption 通过药品编码提取需退费及已退费的药品项目的信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/1/30 15:00
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> remote_bizc131254(HashMap<String,Object> param) {
        String hospCode = (String) param.get("hospCode");//医院编码
        String insureOrgCode = (String) param.get("insureOrgCode");//医保机构编码
        Map<String,Object> httpParam = new HashMap<String,Object>();
        httpParam.put("function_id",Constant.HuNanSheng.INPT.REMOTE_BIZC131254);//功能号
        httpParam.put("serial_no",null);//就医登记号
        httpParam.put("his_item_code",null);//医院目录编码
        httpParam.put("medi_item_type",null);//药品项目类型
        Map<String,Object> httpResult = requestInsure.callHNS(hospCode,insureOrgCode,httpParam);
        return httpResult;
    }

    /**
     * @Menthod Remote_BIZC131255
     * @Desrciption 异地就医住院费用计算(异地)
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/30 20:51
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> remote_bizc131255(HashMap<String,Object> param) {
        InptVisitDTO inptVisitDTO = (InptVisitDTO) param.get("inptVisit");//住院就诊信息
        InsureIndividualVisitDTO insureIndividualVisitDTO = (InsureIndividualVisitDTO) param.get("insureIndividualVisit");//医保就诊信息
        if (inptVisitDTO == null || insureIndividualVisitDTO == null){
            throw new AppException("未获取个人医保就诊信息。");
        }
        InsureConfigurationDO insureConfigurationDO = requestInsure.toConfig(insureIndividualVisitDTO.getHospCode(),insureIndividualVisitDTO.getInsureRegCode());

        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("hospCode",inptVisitDTO.getHospCode());
        paramsMap.put("visitId",inptVisitDTO.getId());
        paramsMap.put("insureRegCode",insureConfigurationDO.getCode());
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
        String otherDiseaseCodes[] = new String[25];
        int i = 0;
        for(Map<String,Object> map : disaseList) {
            String isMain = MapUtils.get(map,"isMain");
            if ("1".equals(isMain)) {
                isMainDiseaseCode = MapUtils.get(map,"insureIllnessCode");
            } else {
                otherDiseaseCodes[i] = MapUtils.get(map,"insureIllnessCode");
                i++;
            }
        }

        Map<String,Object> httpParam = new HashMap<>();
        httpParam.put("function_id",Constant.HuNanSheng.INPT.REMOTE_BIZC131255);//功能号
        httpParam.put("card_iden",inptVisitDTO.getCardIden());//卡识别码
        httpParam.put("oper_staffid",inptVisitDTO.getUserCode());//员工号
        httpParam.put("oper_centerid",insureConfigurationDO.getCode());//中心编码
        httpParam.put("oper_hospitalid",insureConfigurationDO.getOrgCode());//医疗机构编码
        httpParam.put("serial_no",insureIndividualVisitDTO.getMedicalRegNo());//就医登记号
        if (Constants.IS_USER_ACCOUNT.S.equals(insureIndividualVisitDTO.getInsureAccoutFlag())) {
            httpParam.put("last_balance",insureIndividualVisitDTO.getAkc252());//本次业务个人帐户可用金额(系统默认使用个人帐户余额)
        } else {
            httpParam.put("last_balance","0");//本次业务个人帐户可用金额(系统默认使用个人帐户余额)
        }
        httpParam.put("save_flag",Constants.SF.F);//保存标志（是否保存计算结果（0：否 1：是））
        httpParam.put("treatment_type",insureIndividualVisitDTO.getBka006());//待遇类别
        httpParam.put("end_disease",isMainDiseaseCode);//出院诊断
        httpParam.put("end_date",DateUtils.format(inptVisitDTO.getOutTime(),DateUtils.Y_M_DH_M_S));//出院日期（格式：YYYY-MM-DD HH24:MI:SS(24小时)）
        httpParam.put("reg_flag","");//生育就诊类型（生育住院不能为空，D：定额，P：普通消费 T：特殊情况）
        httpParam.put("card_iden",insureIndividualVisitDTO.getCardIden());//卡识别码（跨省异地就医业务必传卡识别码）
        Map<String,Object> httpResult = requestInsure.callHNS((String)param.get("hospCode"),(String) param.get("insureRegCode"),httpParam);
        List<Map<String,Object>> payInfoList = (List<Map<String,Object>>) httpResult.get("payinfo");
        if (ListUtils.isEmpty(payInfoList)) {
            throw new AppException("未获取到费用信息");
        }

        BigDecimal akc264 = BigDecimal.ZERO;
        Map<String,Object> resultMap = new HashMap<>();
        for (Map map : payInfoList) {
            String fundId = String.valueOf(map.get("fund_id"));
            String realPay = String.valueOf(map.get("real_pay"));
            akc264 = BigDecimalUtils.add(akc264.toString(),realPay);
            switch (fundId) {
                case "001" :
                    resultMap.put("ake039",realPay);
                    break;
                case "003" :
                    resultMap.put("akb066",realPay);
                    break;
                case "201" :
                    resultMap.put("ake029",realPay);
                    break;
                case "202" : // 医疗保险离休基金
                    resultMap.put("",realPay);
                    break;
                case "301" :
                    resultMap.put("ake035",realPay);
                    break;
                case "401" : // 医疗补偿金
                    resultMap.put("",realPay);
                    break;
                case "501" : // 工伤保险基金
                    resultMap.put("",realPay);
                    break;
                case "511" : // 生育保险基金
                    resultMap.put("",realPay);
                    break;
                case "801" : // 居民统筹基金
                    resultMap.put("",realPay);
                    break;
                case "901" : // 意外伤害基金
                    resultMap.put("",realPay);
                    break;
                case "999" :
                    String akb067 = MapUtils.get(resultMap,"akb067");
                    if (!StringUtils.isEmpty(akb067)) {
                        realPay = BigDecimalUtils.add(akb067,realPay).toString();
                    }
                    resultMap.put("akb067",realPay);
                    break;
                case "996" :
                    resultMap.put("bka842",realPay);
                    break;
                case "803" : // 居民大病保险基金
                    resultMap.put("",realPay);
                    break;
                default:
                    break;
            }
        }

        BigDecimal akb066 = BigDecimalUtils.convert(MapUtils.get(resultMap,"akb066"));
        BigDecimal akb067 = BigDecimalUtils.convert(MapUtils.get(resultMap,"akb067"));
        resultMap.put("akb066",akb066.toString()); // 医疗保险个人账户
        resultMap.put("bka831",akb067.toString()); // 个人现金自付
        resultMap.put("akc264",akc264.toString()); // 医疗费用总金额
        resultMap.put("bka832",BigDecimalUtils.subtract(akc264,akb067).toString());
        return resultMap;
    }


    /**
     * @Menthod remote_bizc131256
     * @Desrciption 异地就医住院出院结算(异地)
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/5 11:25
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> remote_bizc131256(HashMap<String,Object> param) {
        InptVisitDTO inptVisitDTO = (InptVisitDTO) param.get("inptVisit");
        InsureIndividualVisitDTO insureIndividualVisitDTO = (InsureIndividualVisitDTO) param.get("insureIndividualVisit");
        Map<String,Object> httpParam = new HashMap<>();
        // 获取诊断信息
        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("hospCode",inptVisitDTO.getHospCode());
        paramsMap.put("visitId",inptVisitDTO.getId());
        paramsMap.put("insureRegCode",insureIndividualVisitDTO.getInsureOrgCode());
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
        String[] otherDiseaseCodes = new String[25];
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
        httpParam.put("function_id",Constant.HuNanSheng.INPT.REMOTE_BIZC131256);//功能号
        httpParam.put("card_iden",inptVisitDTO.getCardIden());//卡识别码
        httpParam.put("save_flag","3");//保存标志（"3"：出院登记和结算）
        httpParam.put("serial_no",insureIndividualVisitDTO.getMedicalRegNo());//就医登记号
        httpParam.put("indi_id",insureIndividualVisitDTO.getAac001());//个人电脑号
        if (Constants.IS_USER_ACCOUNT.S.equals(insureIndividualVisitDTO.getInsureAccoutFlag())) {
            httpParam.put("last_balance",insureIndividualVisitDTO.getAkc252());//本次业务个人帐户可用金额(系统默认使用个人帐户余额)
        } else {
            httpParam.put("last_balance","0");//本次业务个人帐户可用金额(系统默认使用个人帐户余额)
        }
        httpParam.put("end_disease",isMainDiseaseCode);//出院疾病（中心疾病编码）
        httpParam.put("end_disease_name",isMainDiseaseName);//出院诊断名称（中心疾病名称）
        httpParam.put("end_date",DateUtils.format(inptVisitDTO.getOutOperTime(),DateUtils.Y_M_DH_M_S));//出院日期（格式：YYYY-MM-DD HH24:MI:SS(24小时)）
        httpParam.put("fin_disease1",otherDiseaseCodes[0] == null ? "" : otherDiseaseCodes[0] );//第一副诊断（中心疾病编码）
        httpParam.put("fin_disease2",otherDiseaseCodes[1] == null ? "" : otherDiseaseCodes[1]);//第二副诊断（中心疾病编码）
        httpParam.put("fin_info",Constant.Xiangtan.DEFAULTVAL.BKA066.get(inptVisitDTO.getOutSituationCode()));//出院详情（好转 其他 死亡 未愈 无效 治愈 转科 转院（病人要求） 转院（医院要求））
        httpParam.put("staff_id",inptVisitDTO.getUserCode());//操作员工号
        httpParam.put("staff_name",inptVisitDTO.getOutOperName());//操作员姓名
        httpParam.put("treatment_type",insureIndividualVisitDTO.getBka006());//待遇类别
        httpParam.put("bill_no",inptVisitDTO.getInNo());//单据号
        httpParam.put("reg_flag","");//生育就诊类型（生育住院不能为空，D：定额，P：普通消费 T：特殊情况（只有省直需要传此参数））
        httpParam.put("reg_info","");//生育疾病类型（生育住院不能为空（见*说明1），（只有省直需要传此参数））
        httpParam.put("serial_apply","");//特殊情况对应的申请序号
        httpParam.put("card_iden",insureIndividualVisitDTO.getCardIden());//卡识别码
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setRegCode(inptVisitDTO.getInsureOrgCode());
        insureConfigurationDTO.setHospCode(inptVisitDTO.getHospCode());
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if(insureConfigurationDTO !=null) {
            httpParam.put("oper_staffid", inptVisitDTO.getUserCode());//员工号
            httpParam.put("oper_centerid", insureConfigurationDTO.getCode());//中心编码
            httpParam.put("oper_hospitalid", insureConfigurationDTO.getOrgCode());//医疗机构编码
        }

        Map<String, Object> httpResult = requestInsure.callHNS((String) param.get("hospCode"), (String) param.get("insureRegCode"), httpParam);
        return httpResult;
    }

    /**
     * @Menthod remote_bizc131206
     * @Desrciption  异地取消住院登记
     * @param insureRemoteInptRegisterDTO 请求参数
     * @Author Ou·Mr
     * @Date 2021/1/30 16:38 
     * @Return java.lang.Boolean
     */
    public Boolean remote_bizc131206(InsureRemoteInptRegisterDTO insureRemoteInptRegisterDTO) {
        Map<String,Object> httpParam = new HashMap<>();
        InsureConfigurationDO insureConfigurationDO = requestInsure.toConfig(insureRemoteInptRegisterDTO.getHospCode(),insureRemoteInptRegisterDTO.getInsureOrgCode());
        httpParam.put("oper_staffid", insureRemoteInptRegisterDTO.getAae011());//员工号
        httpParam.put("oper_centerid", insureConfigurationDO.getCode());//中心编码
        httpParam.put("oper_hospitalid", insureConfigurationDO.getOrgCode());//医疗机构编码
        httpParam.put("function_id",Constant.HuNanSheng.INPT.REMOTE_BIZC131206);//功能号
        httpParam.put("serial_no",insureRemoteInptRegisterDTO.getAaz217());//就医登记号
        httpParam.put("fin_staff",insureRemoteInptRegisterDTO.getAae011());//操作员工号
        httpParam.put("fin_man",insureRemoteInptRegisterDTO.getBka015());//操作员姓名
        httpParam.put("card_iden",insureRemoteInptRegisterDTO.getCardIden());//卡识别码
        /*调用医保统一访问接口*/
        requestInsure.callHNS(insureRemoteInptRegisterDTO.getHospCode(),insureRemoteInptRegisterDTO.getInsureOrgCode(),httpParam);
        return true;
    }

    /**
     * @Menthod bizc131206
     * @Desrciption  取消住院登记（猜测）
     * @param insureInptOutFeeDTO 请求参数
     * @Author Ou·Mr
     * @Date 2021/1/30 16:38
     * @Return java.lang.Boolean
     */
    public Boolean bizc131206(InsureInptOutFeeDTO insureInptOutFeeDTO) {
        Map<String,Object> httpParam = new HashMap<String,Object>();
        httpParam.put("function_id",Constant.HuNanSheng.INPT.BIZC131206);//功能号
        httpParam.put("serial_no",insureInptOutFeeDTO.getAaz217());//就医登记号
        httpParam.put("fin_staff",insureInptOutFeeDTO.getUserCode());//操作员工号
        httpParam.put("fin_man",insureInptOutFeeDTO.getBka015());//操作员姓名

        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setRegCode(insureInptOutFeeDTO.getInsureOrgCode());
        insureConfigurationDTO.setHospCode(insureInptOutFeeDTO.getHospCode());
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        // 获得祁阳骨科专用的中心编码
        String code = getSysParamDTOValue(insureInptOutFeeDTO.getHospCode(), QIYANGCODE);
        String s = code != null ? code : insureConfigurationDTO.getCode();
        if(insureConfigurationDTO !=null) {
            httpParam.put("oper_staffid", insureInptOutFeeDTO.getUserCode());//员工号
            httpParam.put("oper_centerid", s);//中心编码
            httpParam.put("oper_hospitalid", insureConfigurationDTO.getOrgCode());//医疗机构编码
        }
        /*调用医保统一访问接口*/
        requestInsure.callHNS(insureInptOutFeeDTO.getHospCode(),insureInptOutFeeDTO.getInsureOrgCode(),httpParam);
        return true;
    }

    /**
     * @Menthod bizc131259
     * @Desrciption 取消出院结算
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/1/30 16:40
     * @Return java.lang.Boolean
     */
    public Boolean bizc131259(HashMap<String,Object> param) {
        InsureIndividualVisitDTO insureIndividualVisit = (InsureIndividualVisitDTO) param.get("insureIndividualVisit");
        Map<String,Object> httpParam = new HashMap<String,Object>();
        httpParam.put("function_id",Constant.HuNanSheng.INPT.BIZC131259);//功能号
        InsureConfigurationDO insureConfigurationDO = requestInsure.toConfig(insureIndividualVisit.getHospCode(),insureIndividualVisit.getInsureRegCode());
        // 获得祁阳骨科专用的中心编码
        String code = getSysParamDTOValue(insureIndividualVisit.getHospCode(), QIYANGCODE);
        String s = code != null ? code : insureConfigurationDO.getCode();
        httpParam.put("serial_no",insureIndividualVisit.getMedicalRegNo());//就医登记号
        httpParam.put("indi_id",insureIndividualVisit.getAac001());
        httpParam.put("oper_centerid",s);//中心编码
        httpParam.put("oper_hospitalid",insureConfigurationDO.getOrgCode());//医疗机构编码
        httpParam.put("oper_staffid",param.get("userCode"));//员工号
        httpParam.put("ic_flag", "1");//IC设备
        httpParam.put("biz_type", insureIndividualVisit.getAka130());//业务类型
        httpParam.put("patient_id", insureIndividualVisit.getInptVisitNo());//住院号
        // httpParam.put("insur_no", insureIndividualVisit.getAka130());//业务类型
        httpParam.put("treatment_type", insureIndividualVisit.getBka006());//待遇类型（省直中心可为空 长沙中心不为空）
        httpParam.put("staff_id",param.get("userCode"));//员工号
        httpParam.put("staff_name",param.get("userName"));//员工姓名
        httpParam.put("save_flag","3");//保存标志（"3"：取消出院登记和结算）


        //调用医保统一访问接口
        requestInsure.callHNS(insureIndividualVisit.getHospCode(),insureIndividualVisit.getInsureRegCode(),httpParam);
        return true;
    }


    /**
     * @Menthod Remote_BIZC131259
     * @Desrciption 异地取消出院结算
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/1/30 16:40
     * @Return java.lang.Boolean
     */
    public Boolean remote_bizc131259(HashMap<String,Object> param) {
        InsureIndividualVisitDTO insureIndividualVisit = (InsureIndividualVisitDTO) param.get("insureIndividualVisit");
        Map<String,Object> httpParam = new HashMap<>();
        httpParam.put("function_id",Constant.HuNanSheng.INPT.REMOTE_BIZC131259);//功能号
        InsureConfigurationDO insureConfigurationDO = requestInsure.toConfig(insureIndividualVisit.getHospCode(),insureIndividualVisit.getInsureRegCode());
        httpParam.put("serial_no",insureIndividualVisit.getMedicalRegNo());//就医登记号
        httpParam.put("indi_id",insureIndividualVisit.getAac001());
        httpParam.put("oper_centerid",insureConfigurationDO.getCode());//中心编码
        httpParam.put("oper_hospitalid",insureConfigurationDO.getOrgCode());//医疗机构编码
        httpParam.put("oper_staffid",param.get("userCode"));//员工号
        httpParam.put("ic_flag", "1");//IC设备
        httpParam.put("biz_type", insureIndividualVisit.getAka130());//业务类型
        httpParam.put("patient_id", insureIndividualVisit.getInptVisitNo());//住院号
        // httpParam.put("insur_no", insureIndividualVisit.getAka130());//业务类型
        httpParam.put("treatment_type", insureIndividualVisit.getBka006());//待遇类型（省直中心可为空 长沙中心不为空）
        httpParam.put("staff_id",param.get("userCode"));//员工号
        httpParam.put("staff_name",param.get("userName"));//员工姓名
        httpParam.put("save_flag","3");//保存标志（"3"：取消出院登记和结算）
        httpParam.put("card_iden",insureIndividualVisit.getCardIden());//卡识别码

        //调用医保统一访问接口
        requestInsure.callHNS(insureIndividualVisit.getHospCode(),insureIndividualVisit.getInsureRegCode(),httpParam);
        return true;
    }


    /**
     * @Menthod Remote_BIZC200101
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
        Map<String,Object> httpParam = new HashMap<String,Object>();
        httpParam.put("function_id",Constant.HuNanSheng.INPT.REMOTE_BIZC200101);//功能号
        httpParam.put("serial_no",inptVisitDTO.getInjuryBorthSn());//业务序列号
        Map<String,Object> httpResult = requestInsure.callHNS((String)param.get("hospCode"),(String) param.get("insureRegCode"),param);
        return httpResult;
    }

    /**
     * @Menthod Remote_BIZC131210
     * @Desrciption 提取异地就医人员封顶线
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/1/30 16:48 
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String,Object> remote_bizc131210(HashMap<String,Object> param){
        String hospCode = (String) param.get("hospCode");//医院编码
        String insureOrgCode = (String) param.get("insureOrgCode");//医保编码
        Map<String,Object> httpParam = new HashMap<String,Object>();
        httpParam.put("Indi_id",null);//个人电脑号
        Map<String,Object> httpResult = requestInsure.callHNS(hospCode,insureOrgCode,httpParam);
        return httpResult;
    }
    /* //TODO 异地住院 end */

    /**
     * 项目匹配
     * @param map
     * @return
     */
    public List<Map<String,Object>> BIZC110118(HashMap<String,String> map) {
            Map<String,Object> httpParam = new HashMap();
            String hospCode = map.get("hospCode");
            String insureRegCode = map.get("insureRegCode");
            String condition = map.get("condition");
            InsureConfigurationDO insureConfigurationDO = requestInsure.toConfig(hospCode,insureRegCode);
        // 获得祁阳骨科专用的中心编码
        String code = getSysParamDTOValue(hospCode, QIYANGCODE);
        String s = code != null ? code : insureConfigurationDO.getCode();
            httpParam.put("function_id",Constant.HuNanSheng.MATCH.BIZC110118);
            httpParam.put("oper_hospitalid", insureConfigurationDO.getOrgCode());
            httpParam.put("oper_centerid",s);
            httpParam.put("oper_staffid", "000");
            String onceFind = map.get("onceFind");
            if (StringUtils.isEmpty(onceFind)) {
                onceFind = "0";
            }
            httpParam.put("once_find", onceFind);
            httpParam.put("type", map.get("type"));
            httpParam.put("first_version_id",map.get("firstVersionId"));
            httpParam.put("last_version_id",map.get("lastVersionId"));
            httpParam.put("first_row",map.get("firstRow"));
            httpParam.put("last_row",map.get("lastRow"));
            httpParam.put("condition", condition);

            List<Map<String,Object>> pageinfo = new ArrayList<>();
            Map<String,Object> resultMap = requestInsure.callHNS(hospCode,insureRegCode,httpParam);
            if (resultMap.get("pageinfo") != null && resultMap.get("pageinfo") != "") {
                pageinfo = (List<Map<String, Object>>) resultMap.get("pageinfo");
                if (ListUtils.isEmpty(pageinfo)) {
                    pageinfo = new ArrayList<>();
                }
            }
        /* 返回结果集处理 */
        return pageinfo;
    }

    /**
     * @Menthod Remote_BIZC200017
     * @Desrciption 提取异地疾病信息
     * @param param 请求参数
     * @Author Ljg
     * @Date 2021/4/12 21:02
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    public List<Map<String,Object>> Remote_BIZC200017(HashMap<String,String> param) {
        List<Map<String, Object>> diseaseinfoList = new ArrayList<>();
        Map<String, Object> httpParam = new HashMap<>();
        String hospCode = MapUtils.get(param, "hospCode");
        String insureRegCode = MapUtils.get(param, "insureRegCode");
        String serviceCenterId = MapUtils.get(param, "serviceCenterId"); // 异地医保统筹中心编码
        String searchItem = MapUtils.get(param, "searchItem");
        String searchValue = MapUtils.get(param, "keyword");
        if (StringUtils.isEmpty(searchValue) || StringUtils.isEmpty(searchItem)) {
            return diseaseinfoList;
        }
        InsureConfigurationDO insureConfigurationDO = requestInsure.toConfig(hospCode, insureRegCode);
        httpParam.put("function_id", Constant.HuNanSheng.INPT.Remote_BIZC200017);
        httpParam.put("oper_hospitalid", insureConfigurationDO.getRegCode());
        httpParam.put("oper_centerid", insureConfigurationDO.getCode());
        httpParam.put("oper_staffid", "000");
        httpParam.put("service_centerid", serviceCenterId);
        httpParam.put("center_id", serviceCenterId);
        httpParam.put("querydate", DateUtils.format(DateUtils.getNow(), DateUtils.YMD));// YYYY-MM-DD
        switch (searchItem) {
            case "0":
                httpParam.put("disease", searchValue); // 疾病名称
                break;
            case "1":
                httpParam.put("icd", searchValue); // icd
                break;
            case "2":
                httpParam.put("code_py", searchValue); // 首拼码
                break;
            case "3":
                httpParam.put("code_wb", searchValue); // 五笔码
                break;
        }
        Map<String, Object> resultMap = requestInsure.callHNS(hospCode, insureRegCode, httpParam);
        diseaseinfoList = (List<Map<String, Object>>) resultMap.get("diseaseinfo");
        List<Map<String, Object>> resultList = new ArrayList<>();
        if (!ListUtils.isEmpty(diseaseinfoList)) {
            for (Map<String, Object> map : diseaseinfoList) {
                Map<String, Object> reMap = new HashMap<>();
                reMap.put("diseaseName", map.get("disease"));
                reMap.put("diseaseCode", map.get("icd"));
                reMap.put("expireTime", map.get("expire_date"));
                resultList.add(reMap);
            }
        }
        return resultList;
    }

    private void test () {
        Map httpParam = new HashMap();
        httpParam.put("function_id",Constant.HuNanSheng.INPT.Remote_BIZC200017);
        httpParam.put("oper_hospitalid","4330010339");
        httpParam.put("oper_centerid","431201");
        httpParam.put("oper_staffid", "000");
        httpParam.put("service_centerid","439900");
        httpParam.put("center_id","439900");
        httpParam.put("querydate",DateUtils.format(DateUtils.getNow(),DateUtils.Y_M_D));// YYYY-MM-DD
        //httpParam.put("disease","慢性咽炎"); // 疾病名称
        httpParam.put("code_py","MXYY"); // 首拼码
        //httpParam.put("code_wb",""); // 五笔码
        //httpParam.put("icd",""); // icd*/
        long startTime = System.currentTimeMillis();
        Map resultMap = requestInsure.callHNS("0003","431201",httpParam);
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        System.out.println(time);
        if (1 == 1) {
            throw new AppException("" + time);
        }
    }

    private Map<String,Object> localTest() {
        Map param = new HashMap();
        param.put("function_id","BIZC200017");//功能号
        param.put("querydate","2021-03-25");//医疗机构编码
        param.put("icd","J31.203");
        param.put("center_id","431201");
        param.put("oper_staffid","000");//员工号
        param.put("oper_centerid","431201");//中心编码
        param.put("oper_hospitalid","4330010339");//医疗机构编码
        param.put("hospital_id","4330010339");//医疗机构编码
        return param;
    }

}
