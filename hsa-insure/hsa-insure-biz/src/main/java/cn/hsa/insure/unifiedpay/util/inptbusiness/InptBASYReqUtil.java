package cn.hsa.insure.unifiedpay.util.inptbusiness;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.InsureUnifiedCommonUtil;
import cn.hsa.module.insure.module.dao.InsureUnifiedEmrUploadDAO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.module.mris.mrisHome.dto.MrisBaseInfoDTO;
import cn.hsa.module.mris.mrisHome.entity.MrisCostDO;
import cn.hsa.module.mris.tcmMrisHome.dto.TcmMrisBaseInfoDTO;
import cn.hsa.module.mris.tcmMrisHome.entity.TcmMrisCostDO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.Constants;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName InptBASYReqUtil
 * @Deacription 住院病案首页信息-4401
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.INPT.UP_4401)
public class InptBASYReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Resource
    private InsureUnifiedEmrUploadDAO insureUnifiedEmrUploadDAO;
    @Resource
    private InsureUnifiedCommonUtil insureUnifiedCommonUtil;
    @Resource
    private SysParameterService sysParameterService;

    @Override
    public InsureInterfaceParamDTO initRequest(T param) {
        Map map = (Map) param;

        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map, "insureIndividualVisitDTO");

        Map<String, Object> dataMap = new HashMap<>(4);

        StringBuffer stringBuffer = new StringBuffer();
        String mdtrtSn = stringBuffer.append(insureIndividualVisitDTO.getMedicineOrgCode()).append(insureIndividualVisitDTO.getMedicalRegNo()).toString();

        Map<String, Object> baseinfoMap = new HashMap<>();
        Map<String, Object> diseaseInfoMap =new HashMap<>();
        Map<String, Object> operationMap = new HashMap<>();
        Map<String, Object> icuinfoMap = new HashMap<>();
        //病案首页类型
        String mrisPageType = MapUtil.getStr(map, "mrisPageType");
        //中医病案首页
        if ("1".equals(mrisPageType)) {
            //  输入-基本信息（节点标识：baseinfo）
            baseinfoMap = queryTcmEmcBaseInfo(map, mdtrtSn);
            // 病案首页流水号
            TcmMrisBaseInfoDTO mrisBaseInfoDTO = MapUtils.get(map, "tcmMrisBaseInfo");
            // 输入-诊断信息（节点标识：diseinfo）
            String mid = mrisBaseInfoDTO.getId();
            diseaseInfoMap = queryTcmDiseaseInfo(map, mid, mdtrtSn);
            // 输入-手术记录（节点标识：oprninfo）
            operationMap = queryTcmOperationInfo(map, mid, mdtrtSn);
            //  输入-重症监护信息（节点标识：icuinfo）
            icuinfoMap = queryIcuinInfo(map, mid, mdtrtSn);
        //西医病案首页
        }else {
            //  输入-基本信息（节点标识：baseinfo）
            baseinfoMap = queryEmcBaseInfo(map, mdtrtSn);
            // 病案首页流水号
            MrisBaseInfoDTO mrisBaseInfoDTO = MapUtils.get(map, "mrisBaseInfoDTO");
            // 输入-诊断信息（节点标识：diseinfo）
            String mid = mrisBaseInfoDTO.getId();
            diseaseInfoMap = queryDiseaseInfo(map, mid, mdtrtSn);
            // 输入-手术记录（节点标识：oprninfo）
            operationMap = queryOperationInfo(map, mid, mdtrtSn);
            //  输入-重症监护信息（节点标识：icuinfo）
            icuinfoMap = queryIcuinInfo(map, mid, mdtrtSn);
        }
        checkRequest(baseinfoMap);
        dataMap.put("baseinfo",baseinfoMap);
        dataMap.put("diseinfo",MapUtils.get(diseaseInfoMap,"mrisDiagnoseList"));
        dataMap.put("oprninfo",MapUtils.get(operationMap,"oprationMapList"));
        dataMap.put("icuinfo",MapUtils.get(icuinfoMap,"icuinfoMapList"));
        HashMap commParam = new HashMap();

        commParam.put("input", dataMap);
        commParam.put("infno",Constant.UnifiedPay.INPT.UP_4401);

        commParam.put("opter",MapUtils.get(map,"opter"));
        commParam.put("opter_name",MapUtils.get(map,"opter_name"));
        commParam.put("insuplcAdmdvs",MapUtils.get(map,"insuplcAdmdvs"));
        commParam.put("hospCode",MapUtils.get(map,"hospCode"));
        commParam.put("orgCode",MapUtils.get(map,"orgCode"));
        commParam.put("configCode",MapUtils.get(map,"configCode"));
        commParam.put("configRegCode",MapUtils.get(map,"configRegCode"));

        return getInsurCommonParam(commParam);
    }

    @Override
    public boolean checkRequest(Map param) {
        getEmptyErr(param.get("medcasno"),"病案号为空");
        getEmptyErr(param.get("psn_name"),"人员姓名为空");

        return true;
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

        baseInfoMap.put("mdtrt_sn",mdtrtSn); // 就医流水号 定点医药机构编号+院内唯一流水号
        baseInfoMap.put("medcasno",mrisBaseInfoDTO.getInProfile()); // 病案号
        baseInfoMap.put("mdtrt_id",insureIndividualVisitDTO.getMedicalRegNo()); //就诊ID
        baseInfoMap.put("psn_no",insureIndividualVisitDTO.getAac001()); //人员编号
        baseInfoMap.put("patn_ipt_cnt",mrisBaseInfoDTO.getInCnt()); //患者住院次数
        baseInfoMap.put("ipt_no",mrisBaseInfoDTO.getInNo()); //住院号
        baseInfoMap.put("psn_name",mrisBaseInfoDTO.getName()); //人员姓名
        baseInfoMap.put("gend",mrisBaseInfoDTO.getGenderCode()); //性别
        baseInfoMap.put("brdy",mrisBaseInfoDTO.getBirthday()); //出生日期
        baseInfoMap.put("ntly","CHN"); // 国籍 默认是中国
        baseInfoMap.put("ntly_name",mrisBaseInfoDTO.getNationalityName()); //国籍名称
        String babyBirthWeight = mrisBaseInfoDTO.getBabyBirthWeight();
        String babyInWeight = mrisBaseInfoDTO.getBabyInWeight();
        if(StringUtils.isEmpty(babyBirthWeight) || "-".equals(babyBirthWeight) ){
            baseInfoMap.put("nwb_bir_wt",""); // 新生儿出生体重
        }else{
            baseInfoMap.put("nwb_bir_wt", Double.parseDouble(babyBirthWeight)); // 新生儿出生体重
        }
        if(StringUtils.isEmpty(babyInWeight) || "-".equals(babyInWeight) ){
            baseInfoMap.put("nwb_adm_wt",""); // 新生儿出生体重
        }else{
            baseInfoMap.put("nwb_adm_wt", Integer.parseInt(babyInWeight)); // 新生儿入院体重
        }

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

        /**
         * 因为广东省的病案首页年龄字段值和其他省份不同 所以需要根据系统特殊处理
         */

        map.put("code","SHOW_GDSBASY");
        SysParameterDTO sysParameterDTO = sysParameterService.getParameterByCode(map).getData();
        String age = mrisBaseInfoDTO.getAge();
        if(sysParameterDTO !=null && Constants.SF.S.equals(sysParameterDTO.getValue())){
            baseInfoMap.put("age",Integer.valueOf(age.substring(1))); //年龄
        }else{
            baseInfoMap.put("age",Integer.valueOf(age)); //年龄
        }

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
     * @Description 中医病案首页信息  ---- 基本信息
     * @Author 产品三部-郭来
     * @Date 2022-05-31 20:49
     * @param map
     * @param mdtrtSn
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    private Map<String, Object> queryTcmEmcBaseInfo(Map<String, Object> map,String mdtrtSn) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map,"insureIndividualVisitDTO");
        if (ObjectUtil.isEmpty(insureIndividualVisitDTO)) {
            throw new AppException("未获取到医保登记信息！");
        }
        TcmMrisCostDO mrisCostDO =  MapUtils.get(map,"tcmmrisCostDO");
        if (ObjectUtil.isEmpty(mrisCostDO)) {
            throw new AppException("未获取到病案首页费用信息！");
        }
        TcmMrisBaseInfoDTO mrisBaseInfoDTO = MapUtils.get(map,"tcmMrisBaseInfo");
        if (ObjectUtil.isEmpty(mrisBaseInfoDTO)) {
            throw new AppException("未获取到病案首页基础信息！");
        }
        Map<String,Object> baseInfoMap = new HashMap<>();

        baseInfoMap.put("mdtrt_sn",mdtrtSn); // 就医流水号 定点医药机构编号+院内唯一流水号
        baseInfoMap.put("medcasno",mrisBaseInfoDTO.getInProfile()); // 病案号
        baseInfoMap.put("mdtrt_id",insureIndividualVisitDTO.getMedicalRegNo()); //就诊ID
        baseInfoMap.put("psn_no",insureIndividualVisitDTO.getAac001()); //人员编号
        baseInfoMap.put("patn_ipt_cnt",mrisBaseInfoDTO.getInCnt()); //患者住院次数
        baseInfoMap.put("ipt_no",mrisBaseInfoDTO.getInNo()); //住院号
        baseInfoMap.put("psn_name",mrisBaseInfoDTO.getName()); //人员姓名
        baseInfoMap.put("gend",mrisBaseInfoDTO.getGenderCode()); //性别
        baseInfoMap.put("brdy",mrisBaseInfoDTO.getBirthday()); //出生日期
        baseInfoMap.put("ntly","CHN"); // 国籍 默认是中国
        baseInfoMap.put("ntly_name",mrisBaseInfoDTO.getNationalityName()); //国籍名称
        String babyBirthWeight = mrisBaseInfoDTO.getBabyBirthWeight();
        String babyInWeight = mrisBaseInfoDTO.getBabyInWeight();
        if(StringUtils.isEmpty(babyBirthWeight) || "-".equals(babyBirthWeight) ){
            baseInfoMap.put("nwb_bir_wt",""); // 新生儿出生体重
        }else{
            baseInfoMap.put("nwb_bir_wt", Integer.parseInt(babyBirthWeight)); // 新生儿出生体重
        }
        if(StringUtils.isEmpty(babyInWeight) || "-".equals(babyInWeight) ){
            baseInfoMap.put("nwb_adm_wt",""); // 新生儿出生体重
        }else{
            baseInfoMap.put("nwb_adm_wt", Integer.parseInt(babyInWeight)); // 新生儿入院体重
        }

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

        /**
         * 因为广东省的病案首页年龄字段值和其他省份不同 所以需要根据系统特殊处理
         */

        map.put("code","SHOW_GDSBASY");
        SysParameterDTO sysParameterDTO = sysParameterService.getParameterByCode(map).getData();
        String age = mrisBaseInfoDTO.getAge();
        if(sysParameterDTO !=null && Constants.SF.S.equals(sysParameterDTO.getValue())){
            baseInfoMap.put("age",Integer.valueOf(age.substring(1))); //年龄
        }else{
            baseInfoMap.put("age",Integer.valueOf(age)); //年龄
        }

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
                item.put("adm_dise_cond_name","");//	入院疾病病情名称
                //广东病案首页上传此字段为必传，但可以传空值  000001：普通病种床日分值结算；000002：精神病种床日分值结算；000003：新生儿母婴同室；空值：其他病案 ；
                item.put("adm_dise_cond_code","");//	入院疾病病情代码
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
                // 医保的住院病案流水号是唯一主键，所以占时用诊断id替代
                item.put("ipt_medcas_hmpg_sn",MapUtils.get(item,"id"));//	住院病案首页流水号
                item.put("mdtrt_sn",mdtrtSn);//	就医流水号
            });
        }
        map.put("mrisDiagnoseList",mrisDiagnoseList);
        return map;
    }

    /**
     * @Description 住院病案首页信息   -中医病案诊断信息
     * @Author 产品三部-郭来
     * @Date 2022-06-01 16:41
     * @param map
     * @param midId
     * @param mdtrtSn
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    private Map<String, Object> queryTcmDiseaseInfo(Map<String, Object> map,String midId,String mdtrtSn) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map,"insureIndividualVisitDTO");
        map.put("insureRegCode",insureIndividualVisitDTO.getInsureRegCode());
        List<Map<String,Object>> mrisDiagnoseList = insureUnifiedEmrUploadDAO.selectTcmDiseinfo(map);
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
                item.put("adm_dise_cond_name","");//	入院疾病病情名称
                //广东病案首页上传此字段为必传，但可以传空值  000001：普通病种床日分值结算；000002：精神病种床日分值结算；000003：新生儿母婴同室；空值：其他病案 ；
                item.put("adm_dise_cond_code","");//	入院疾病病情代码
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
                // 医保的住院病案流水号是唯一主键，所以占时用诊断id替代
                item.put("ipt_medcas_hmpg_sn",MapUtils.get(item,"id"));//	住院病案首页流水号
                item.put("mdtrt_sn",mdtrtSn);//	就医流水号
            });
        }
        map.put("mrisDiagnoseList",mrisDiagnoseList);
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
                item.put("ipt_medcas_hmpg_sn",MapUtils.get(item,"id")); // 住院病案首页流水号

                //广东医保病案上传接口oprn_oprt_date、oprn_oprt_code、oprn_oprt_name为必填项，这里做特殊处理
                if ("44".startsWith(insureIndividualVisitDTO.getInsureRegCode())) {
                    if (ObjectUtil.isEmpty(item.get("oprn_oprt_date"))) {
                        throw new AppException("病案首页上传-手术操作日期不能为空，请检查！");
                    }
                    if (ObjectUtil.isEmpty(item.get("oprn_oprt_name")) || ObjectUtil.isEmpty(item.get("oprn_oprt_code"))) {
                        if (ObjectUtil.isEmpty(item.get("oper_disease_id"))) {
                            throw new AppException("病案首页上传-手术/操作名称或手术/操作代码不能为空，请检查！");
                        }
                        map.put("operDiseaseId",item.get("oper_disease_id"));
                        Map<String, Object> matchMap = insureUnifiedEmrUploadDAO.selectInsureDiseaseMatch(map);
                        if (ObjectUtil.isEmpty(matchMap)) {
                            throw new AppException("病案首页上传-手术/操作名称或手术/操作代码不能为空，请检查！");
                        }
                        item.put("oprn_oprt_code",matchMap.get("insure_illness_code"));
                        item.put("oprn_oprt_name",matchMap.get("insure_illness_name"));
                    }
                }
            });
        }else{
            map.put("code","SHOW_GDSBASY");
            SysParameterDTO sysParameterDTO = sysParameterService.getParameterByCode(map).getData();

            if(sysParameterDTO !=null && Constants.SF.S.equals(sysParameterDTO.getValue())){
                //广东医保传空集合
            }else{
                // 用测试环境测试时，医保必须传手术节点
                Map<String,Object> operInfoMap = new HashMap<>();
                operInfoMap.put("vali_flag",Constants.SF.F); // 有效标志
                operInfoMap.put("mdtrt_sn",mdtrtSn);  //  就医流水号
                operInfoMap.put("ipt_medcas_hmpg_sn",mid); // 住院病案首页流水号
                operInfoDOList.add(operInfoMap);
            }
        }
        map.put("oprationMapList",operInfoDOList);
        return map;
    }
    /**
     * @Description 查询中医病案手术信息
     * @Author 产品三部-郭来
     * @Date 2022-06-01 16:58
     * @param map
     * @param mid
     * @param mdtrtSn
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    private Map<String, Object> queryTcmOperationInfo(Map<String, Object> map,String mid,String mdtrtSn) {
        InsureIndividualVisitDTO insureIndividualVisitDTO =MapUtils.get(map,"insureIndividualVisitDTO");
        map.put("insureRegCode",insureIndividualVisitDTO.getInsureRegCode());
        List<Map<String,Object>> operInfoDOList = insureUnifiedEmrUploadDAO.selectTcmOperInfo(map);
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
                item.put("ipt_medcas_hmpg_sn",MapUtils.get(item,"id")); // 住院病案首页流水号

                //广东医保病案上传接口oprn_oprt_date、oprn_oprt_code、oprn_oprt_name为必填项，这里做特殊处理
                if ("44".startsWith(insureIndividualVisitDTO.getInsureRegCode())) {
                    if (ObjectUtil.isEmpty(item.get("oprn_oprt_date"))) {
                        throw new AppException("病案首页上传-手术操作日期不能为空，请检查！");
                    }
                    if (ObjectUtil.isEmpty(item.get("oprn_oprt_name")) || ObjectUtil.isEmpty(item.get("oprn_oprt_code"))) {
                        if (ObjectUtil.isEmpty(item.get("oper_disease_id"))) {
                            throw new AppException("病案首页上传-手术/操作名称或手术/操作代码不能为空，请检查！");
                        }
                        map.put("operDiseaseId",item.get("oper_disease_id"));
                        Map<String, Object> matchMap = insureUnifiedEmrUploadDAO.selectInsureDiseaseMatch(map);
                        if (ObjectUtil.isEmpty(matchMap)) {
                            throw new AppException("病案首页上传-手术/操作名称或手术/操作代码不能为空，请检查！");
                        }
                        item.put("oprn_oprt_code",matchMap.get("insure_illness_code"));
                        item.put("oprn_oprt_name",matchMap.get("insure_illness_name"));
                    }
                }
            });
        }else{
            map.put("code","SHOW_GDSBASY");
            SysParameterDTO sysParameterDTO = sysParameterService.getParameterByCode(map).getData();

            if(sysParameterDTO !=null && Constants.SF.S.equals(sysParameterDTO.getValue())){
                //广东医保传空集合
            }else{
                // 用测试环境测试时，医保必须传手术节点
                Map<String,Object> operInfoMap = new HashMap<>();
                operInfoMap.put("vali_flag",Constants.SF.F); // 有效标志
                operInfoMap.put("mdtrt_sn",mdtrtSn);  //  就医流水号
                operInfoMap.put("ipt_medcas_hmpg_sn",mid); // 住院病案首页流水号
                operInfoDOList.add(operInfoMap);
            }
        }
        map.put("oprationMapList",operInfoDOList);
        return map;
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
        List<Map<String,Object>> mapList = new ArrayList<>();
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("vali_flag",Constants.SF.F);
        paramMap.put("ipt_medcas_hmpg_sn",mid);
        paramMap.put("mdtrt_sn",mdtrtSn);
        mapList.add(paramMap);
        map.put("icuinfoMapList",mapList);
        return map;
    }
}
