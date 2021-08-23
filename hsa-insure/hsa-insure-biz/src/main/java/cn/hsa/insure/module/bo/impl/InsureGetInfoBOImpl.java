package cn.hsa.insure.module.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.unifiedpay.bo.impl.InsureUnifiedBaseBOImpl;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.module.bo.InsureGetInfoBO;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureGetInfoDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualCostDAO;
import cn.hsa.module.insure.module.dto.*;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

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
    private InsureUnifiedBaseBOImpl insureUnifiedBaseBOImpl;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * @Method getSettleInfo
     * @Desrciption
     * 1、交易输入结算清单信息为单行数据，输入其他信息均为多行数据；
     * 2、输入项信息按照《医疗保障基金结算清单填写规范》中的规范要求填写；
     * 3、每次接口调用只上传一位患者的信息。
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-10 14:11
     * @Return java.util.Map
     **/
    @Override
    public Map insertSettleInfo(InsureSettleInfoDTO insureSettleInfoDTO) {
        String aaa = insureGetInfoDAO.getSetlInfoLocal(insureSettleInfoDTO);

        DecimalFormat df = new DecimalFormat("0.00");
        // 获取医疗机构信息
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        insureConfigurationDTO.setOrgCode(insureSettleInfoDTO.getMedicineOrgCode());  // 医疗机构编码
        InsureConfigurationDTO insureConfigurationDTO1 = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        Map listMap = new HashMap();

        // 获取数据库里面的信息
        InsureSettleInfoDTO settleInfoDTO = new InsureSettleInfoDTO();

        if (insureSettleInfoDTO.getIsHospital().equals("1")) {
            settleInfoDTO = insureGetInfoDAO.getSetlInfo(insureSettleInfoDTO);
        } else if(insureSettleInfoDTO.getIsHospital().equals("0")){
            settleInfoDTO = insureGetInfoDAO.getOutSetlInfo(insureSettleInfoDTO);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("insureSettleId",insureSettleInfoDTO.getInsureSettleId());
        map.put("visitId",insureSettleInfoDTO.getVisitId());
        map.put("hospCode",insureSettleInfoDTO.getHospCode());

        listMap.put("iteminfo", iteminfoListMap(insureSettleInfoDTO,map));
        listMap.put("payinfo",payInfoListMap(insureSettleInfoDTO,map));

        List<InsureIndividualFundDTO> payinfoList = insureGetInfoDAO.queryPayinfo(insureSettleInfoDTO);
        Map<String, Object> stringObjectMap2 = insureUnifiedBaseBOImpl.querySettleDeInfo(map);

        Map<String, Object> setlinfoMap = MapUtils.get(stringObjectMap2, "setlinfo");
        if(StringUtils.isEmpty(settleInfoDTO.getInsureSettleId())){
          throw new AppException("该病人未进行医保结算");
        }
        // 结算清单信息
        Map setlinfo = new HashMap();
        DecimalFormat df1 = new DecimalFormat("0.00");
        setlinfo.put("id",SnowflakeUtils.getId()); // id
        setlinfo.put("hospCode",settleInfoDTO.getHospCode()); // 医院编码
        setlinfo.put("visitId",settleInfoDTO.getVisitId()); // 本地就诊id
        setlinfo.put("mdtrt_id",settleInfoDTO.getMedicalRegNo()); // 就医登记号
        setlinfo.put("psn_no",settleInfoDTO.getAac001()); // 就诊ID
        setlinfo.put("setl_id",settleInfoDTO.getInsureSettleId()); // 结算ID
        setlinfo.put("hi_no",settleInfoDTO.getAac001()); //医保编号
        setlinfo.put("dcla_time",settleInfoDTO.getDclaTime()); //结算清单申报时间
        setlinfo.put("settleNo",settleInfoDTO.getSettleNo());// 清单流水号
        setlinfo.put("hi_setl_lv",settleInfoDTO.getHiSetlLv());//医保结算等级
        setlinfo.put("fixmedins_name",insureSettleInfoDTO.getHospName()); // 定点医药机构名称
        setlinfo.put("fixmedins_code",insureSettleInfoDTO.getMedicineOrgCode()); // 定点医药机构编号
        setlinfo.put("medcasno",settleInfoDTO.getInProfile()); // 病案号
        setlinfo.put("psn_name",settleInfoDTO.getName());// 人员姓名
        setlinfo.put("gend",settleInfoDTO.getGenderCode());// 性别
        setlinfo.put("age",settleInfoDTO.getAge()); // 年龄
        setlinfo.put("brdy",settleInfoDTO.getBirthday()); // 出生日期
        if (StringUtils.isNotEmpty(settleInfoDTO.getNationCode())) {
            setlinfo.put("naty",settleInfoDTO.getNationCode()); // 民族
        } else {
            setlinfo.put("naty","1"); // 民族
        }
        setlinfo.put("patn_cert_type",settleInfoDTO.getCertCode()); // 患者证件类别
        setlinfo.put("certno",settleInfoDTO.getCertNo()); // 证件号码
        if(StringUtils.isNotEmpty(settleInfoDTO.getOccupationCode())){
            setlinfo.put("prfs",settleInfoDTO.getOccupationCode()); // 职业
        } else {
            setlinfo.put("prfs","1"); // 职业
        }
        if(StringUtils.isNotEmpty(settleInfoDTO.getHiType())){
            setlinfo.put("hi_type",settleInfoDTO.getHiType()); // 医保类型
        } else {
            setlinfo.put("hi_type","1"); // 医保类型0
        }
        if(StringUtils.isNotEmpty(settleInfoDTO.getAddress())){
            setlinfo.put("curr_addr",settleInfoDTO.getAddress()); // 现住址
        } else {
            setlinfo.put("curr_addr","中国"); // 现住址
        }
        if(StringUtils.isNotEmpty(settleInfoDTO.getInsuplc())) {
            setlinfo.put("insuplc", settleInfoDTO.getInsuplc()); // 参保地 *******
        }else{
            setlinfo.put("insuplc","中国"); // 参保地 *******
        }
        setlinfo.put("ipt_med_type",StringUtils.isEmpty(settleInfoDTO.getIptMedType())? "1":settleInfoDTO.getIptMedType()); // 住院医疗类型
        setlinfo.put("emp_name",settleInfoDTO.getWork()); // 工作单位 *******
        setlinfo.put("emp_addr",settleInfoDTO.getWorkAddress()); // 工作单位地址 *******
        setlinfo.put("emp_tel",settleInfoDTO.getWorkPhone()); // 工作单位电话 *******
        setlinfo.put("poscode",settleInfoDTO.getWorkPostCode()); // 工作单位邮编 *******
        if(StringUtils.isNotEmpty(settleInfoDTO.getBabyBirthWeight()) ){
            if(!"-".equals(settleInfoDTO.getBabyBirthWeight())){
                setlinfo.put("nwb_bir_wt",settleInfoDTO.getBabyBirthWeight()); // 新生儿出生体重 *******
            }
        }
        if(StringUtils.isNotEmpty(settleInfoDTO.getBabyInWeight()) ) {
            if (!"-".equals(settleInfoDTO.getBabyInWeight())) {
                setlinfo.put("nwb_adm_w", settleInfoDTO.getBabyInWeight()); // 新生儿入院体重 *******
            }
        }
        setlinfo.put("opsp_diag_caty",settleInfoDTO.getVisitDrptName()); // 诊断科别 *******
        setlinfo.put("opsp_mdtrt_date",settleInfoDTO.getVisitTime()); // 就诊日期 *******
        setlinfo.put("adm_way",settleInfoDTO.getInWay()); // 入院途径 *******
        setlinfo.put("adm_time",settleInfoDTO.getInTime()); // 入院时间 *******
        if (StringUtils.isNotEmpty(settleInfoDTO.getInDeptCode())) {
            setlinfo.put("adm_caty",settleInfoDTO.getInDeptCode()); // 入院科别
        } else {
            setlinfo.put("adm_caty","-"); // 入院科别
        }
        if (StringUtils.isNotEmpty(settleInfoDTO.getOutDeptCode())) {
            setlinfo.put("dscg_caty",settleInfoDTO.getOutDeptCode()); // 出院科别
        } else {
            setlinfo.put("dscg_caty","-"); // 出院科别
        }
        setlinfo.put("refldept_dept",settleInfoDTO.getTurnDeptIds()); // 转科科别
        setlinfo.put("dscg_time",settleInfoDTO.getOutTime()); // 出院时间 *******
        setlinfo.put("act_ipt_days",settleInfoDTO.getInDays()); // 实际住院天数 *******
        setlinfo.put("otp_wm_dise",settleInfoDTO.getOutptDiseaseName()); // 门（急）诊诊断 *******
        setlinfo.put("wm_dise_code",settleInfoDTO.getVisitIcdCode()); // 疾病代码 *******
        setlinfo.put("in_situation_name",settleInfoDTO.getInSituationName()); // 入院病情 *******
        setlinfo.put("in_situation_name",settleInfoDTO.getInSituationName()); // 其他诊断 *******
        if(StringUtils.isNotEmpty(settleInfoDTO.getInptBeforeDay()) && StringUtils.isNotEmpty(settleInfoDTO.getInptBeforeHour()) && StringUtils.isNotEmpty(settleInfoDTO.getInptBeforeMinute())) {
            if(!("-".equals(settleInfoDTO.getInptBeforeDay()) && "-".equals(settleInfoDTO.getInptBeforeHour()) && "-".equals(settleInfoDTO.getInptBeforeMinute()))){
                setlinfo.put("pwcry_bfadm_coma_dura", (settleInfoDTO.getInptBeforeDay() + "/" + settleInfoDTO.getInptBeforeHour() + "/" + settleInfoDTO.getInptBeforeMinute())); // 入院前昏迷时长
            }
        }
        if(StringUtils.isNotEmpty(settleInfoDTO.getInptLastDay()) && StringUtils.isNotEmpty(settleInfoDTO.getInptLastHour()) && StringUtils.isNotEmpty(settleInfoDTO.getInptLastMinute())){
            if(!("-".equals(settleInfoDTO.getInptLastDay()) && "-".equals(settleInfoDTO.getInptLastHour()) && "-".equals(settleInfoDTO.getInptLastMinute()))){
                setlinfo.put("pwcry_afadm_coma_dura",(settleInfoDTO.getInptLastDay() + "/"  + settleInfoDTO.getInptLastHour() + "/" + settleInfoDTO.getInptLastMinute())); // 入院后昏迷时长
            }
        }
        setlinfo.put("spgaNurscare_Days",settleInfoDTO.getSpgaNurscareDays()); // 特级护理天数 *******
        setlinfo.put("lv1Nurscare_Days",settleInfoDTO.getLv1NurscareDays()); // 一级护理天数 *******
        setlinfo.put("scdNurscare_Days",settleInfoDTO.getScdNurscareDays()); // 二级护理天数 *******
        setlinfo.put("lv3Nursecare_Days",settleInfoDTO.getLv3NursecareDays()); // 三级护理天数 *******
        setlinfo.put("dscg_way_nam",settleInfoDTO.getOutModeName()); // 离院方式名称 *******
        setlinfo.put("days_rinp_flag_31",settleInfoDTO.getIsInpt()); // 是否有出院31天再住院计划 *******
        if(StringUtils.isNotEmpty(settleInfoDTO.getZgDoctorName())){
        setlinfo.put("chfpdr_name",settleInfoDTO.getZgDoctorName()); // 主诊医生姓名 *******
        }else {
            setlinfo.put("chfpdr_name","无"); // 主诊医生姓名 *******
        }
        if(StringUtils.isNotEmpty(settleInfoDTO.getZgDoctorId())){
            setlinfo.put("chfpdr_code",settleInfoDTO.getZgDoctorId()); // 主诊医生代码 *******
        }else {
            setlinfo.put("chfpdr_code","无"); // 主诊医生代码 *******
        }
        if(StringUtils.isNotEmpty(settleInfoDTO.getZrNurseName())){
            setlinfo.put("zr_Nurse_Name",settleInfoDTO.getZrNurseName()); // 责任护士名 *******
        }else {
            setlinfo.put("zr_Nurse_Name","无"); // 责任护士名 *******
        }
        if(StringUtils.isNotEmpty(settleInfoDTO.getZrNurseId())){
            setlinfo.put("zr_Nurse_Code",settleInfoDTO.getZrNurseId()); // 责任护士代码 *******
        }else {
            setlinfo.put("zr_Nurse_Code","无"); // 责任护士代码 *******
        }
        if(StringUtils.isEmpty(settleInfoDTO.getInvoiceDetailId()) || StringUtils.isEmpty(settleInfoDTO.getInvoiceNo())){
            throw new AppException("该病人未开发票，请先开具发票！");
        }
        setlinfo.put("bill_code",settleInfoDTO.getInvoiceDetailId()); // 票据代码**********
        setlinfo.put("bill_no",settleInfoDTO.getInvoiceNo()); // 票据号码**********
        setlinfo.put("biz_sn",settleInfoDTO.getOmsgid()); // 业务流水号
        setlinfo.put("psn_selfpay",df.format(BigDecimalUtils.subtract(
                BigDecimalUtils.convert(MapUtils.get(setlinfoMap,"psn_pay").toString()),
                BigDecimalUtils.convert(
                        df1.format(BigDecimalUtils.add(
                                BigDecimalUtils.convert(MapUtils.get(setlinfoMap,"preselfpay_amt").toString()),
                                BigDecimalUtils.convert(MapUtils.get(setlinfoMap,"fulamt_ownpay_amt").toString()))))))); // 个人自付
        setlinfo.put("psn_ownpay",df.format(BigDecimalUtils.convert(df1.format(BigDecimalUtils.add(BigDecimalUtils.convert(MapUtils.get(setlinfoMap,"preselfpay_amt").toString()),
                BigDecimalUtils.convert(MapUtils.get(setlinfoMap,"fulamt_ownpay_amt").toString())))))); // 个人自费
        setlinfo.put("acct_pay",df.format(settleInfoDTO.getPersonalPrice())); // 个人账户支出
        setlinfo.put("psn_cashpay",df.format(BigDecimalUtils.convert(setlinfoMap.get("cash_payamt").toString()))); // 个人现金支付
        setlinfo.put("hi_paymtd",settleInfoDTO.getSettleway()); // 医保支付方式 *********（有个pay_way_name：医疗支付方式mris_base_info表）  //结算方式,01 普通结算,02 包干结算
        setlinfo.put("hsorg",insureConfigurationDTO1.getName()); // 医保机构
        setlinfo.put("hsorg_opter",StringUtils.isEmpty(settleInfoDTO.getHosrgOpter())? insureSettleInfoDTO.getHosrgOpter(): settleInfoDTO.getHosrgOpter()); // 医保机构经办人
        setlinfo.put("medins_fill_dept",StringUtils.isEmpty(settleInfoDTO.getMedinsFillDept())? insureSettleInfoDTO.getMedinsFillDept(): settleInfoDTO.getMedinsFillDept()); // 医疗机构填报部门
        setlinfo.put("medins_fill_psn",StringUtils.isEmpty(settleInfoDTO.getMedinsFillPsn())? insureSettleInfoDTO.getMedinsFillPsn(): settleInfoDTO.getMedinsFillPsn()); // 医疗机构填报人
        setlinfo.put("crteId",insureSettleInfoDTO.getCrteId()); // 操作人id
        setlinfo.put("crteName",insureSettleInfoDTO.getCrteName()); // 操作人姓名
        setlinfo.put("crteTime",DateUtils.getNow()); // 操作时间
        if(StringUtils.isNotEmpty(settleInfoDTO.getNationalityCation())){
            setlinfo.put("ntly",settleInfoDTO.getNationalityCation()); //国籍
        } else {
            setlinfo.put("ntly","141"); //国籍
        }
        if(StringUtils.isNotEmpty(settleInfoDTO.getContactName())){
            setlinfo.put("coner_name",settleInfoDTO.getContactName()); // 联系人姓名
        } else {
            setlinfo.put("coner_name",settleInfoDTO.getName()); // 联系人姓名
        }
        if (StringUtils.isNotEmpty(settleInfoDTO.getContactRelaCode())){
            setlinfo.put("patn_rlts",settleInfoDTO.getContactRelaCode()); // 与患者关系
        } else {
            setlinfo.put("patn_rlts","1"); // 与患者关系
        }
        if (StringUtils.isNotEmpty(settleInfoDTO.getContactAddress())){
            setlinfo.put("coner_addr",settleInfoDTO.getContactAddress()); // 联系人地址
        }else{
            setlinfo.put("coner_addr","中国"); // 联系人地址
        }
        if (StringUtils.isNotEmpty(settleInfoDTO.getContactPhone())){
            setlinfo.put("coner_tel",settleInfoDTO.getContactPhone()); // 联系人电话
        } else {
            setlinfo.put("coner_tel","120"); // 联系人电话
        }
        setlinfo.put("spPsnType",settleInfoDTO.getBka035_name()); // 特殊病人类型
        setlinfo.put("setl_begn_date",settleInfoDTO.getStartTime()); // 结算开始日期
        setlinfo.put("setl_end_date",settleInfoDTO.getEndTime()); // 结算结束日期

//            if(insureSettleInfoDTO.getIsHospital().equals("0")){//门诊慢特病诊断信息
            listMap.put("opspdiseinfo",opspdiseinfoListMap(insureSettleInfoDTO));
//            } else if (insureSettleInfoDTO.getIsHospital().equals("1")){//住院诊断信息
            listMap.put("diseinfo",MapUtils.get(diseinfoListMap(insureSettleInfoDTO),"diseinfo"));
            listMap.put("diseinfoListMapIsNotMain",MapUtils.get(diseinfoListMap(insureSettleInfoDTO),"diseinfoListMapIsNotMain"));
            listMap.put("diseinfoMap",MapUtils.get(diseinfoListMap(insureSettleInfoDTO),"diseinfoMap"));
//            }
        listMap.put("oprninfo",oprninfoListMap(insureSettleInfoDTO));
        listMap.put("icuinfo",icuinfoListMap(insureSettleInfoDTO));
        listMap.put("setlinfo",setlinfo);
        listMap.put("setlinfoMap",setlinfoMap);

        if (StringUtils.isEmpty(insureSettleInfoDTO.getUpload())) {
            /**
             * 公共入参
             */
            Map param = new HashMap();
            param.put("infno", "4101");  //交易编号
            param.put("msgid", StringUtils.createMsgId(insureConfigurationDTO1.getOrgCode()));
            param.put("insuplc_admdvs", insureConfigurationDTO1.getMdtrtareaAdmvs()); //参保地医保区划分
            param.put("medins_code", insureConfigurationDTO1.getOrgCode()); //定点医药机构编号
            param.put("insur_code", insureConfigurationDTO1.getRegCode()); //医保中心编码
            param.put("mdtrtarea_admvs",insureConfigurationDTO1.getMdtrtareaAdmvs());// 就医地医保区划
            param.put("input", listMap);  //交易输入
            String json = JSONObject.toJSONString(param);
            logger.info("基金结算单上传传输入参:" + json);
            String url= insureConfigurationDTO1.getUrl();
            String resultJson = HttpConnectUtil.unifiedPayPostUtil(url, json);
            logger.info("基金结算单上传传输回参:" + resultJson);
            Map<String,Object> resultMap = JSONObject.parseObject(resultJson,Map.class);
            if(StringUtils.isEmpty(resultJson)){
                throw new AppException("访问统一支付平台失败");
            }
            if("999".equals(resultMap.get("infcode").toString())){
                throw new AppException((String) resultMap.get("msg"));
            }
            if(!"0".equals(resultMap.get("infcode").toString())){
                throw new AppException((String)resultMap.get("err_msg"));
            }
            Map resultData = (Map) resultMap.get("output");
            Map resultSetl = (Map) resultData.get("data");
            setlinfo.put("settleNo",resultSetl.get("setl_list_id")); // 上传结算流水号
            if(insureGetInfoDAO.queryID(insureSettleInfoDTO)>0){
                insureGetInfoDAO.deleteByVisitID(insureSettleInfoDTO);
            }
            insureGetInfoDAO.insertSettleInfo(setlinfo);
        }
        return listMap;
    }

    /**
     * @Method getSettleInfo
     * @Desrciption
     * 1、交易输入结算清单信息为单行数据，输入其他信息均为多行数据；
     * 2、输入项信息按照《医疗保障基金结算清单填写规范》中的规范要求填写；
     * 3、每次接口调用只上传一位患者的信息。
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-10 14:11
     * @Return java.util.Map
     **/
    @Override
    public Map<String,Object> getSettleInfo(InsureSettleInfoDTO insureSettleInfoDTO) {
//        list<Map>
        Map<String,Object> resultMap = insureGetInfoDAO.getSettleInfo(insureSettleInfoDTO);

        return resultMap;
    }


    /**
     * @Method getInsureCost
     * @Desrciption 自费病人费用明细信息上传
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-11 22:54
     * @Return java.util.Map
     *
     * @return*/
    @Override
    public Boolean insertInsureCost(InsureSettleInfoDTO insureSettleInfoDTO){

        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        if(StringUtils.isEmpty(insureSettleInfoDTO.getMedicineOrgCode())){
            throw new AppException("请选择医保机构");
        }
        insureConfigurationDTO.setOrgCode(insureSettleInfoDTO.getMedicineOrgCode());  // 医疗机构编码
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        List<Map> listMap = new ArrayList<>();
        List<InptCostDTO> costDTOList = new ArrayList<>();
        List<InsureUploadCostDTO> insureUploadCostDTOList = new ArrayList<>();
        if (insureSettleInfoDTO.getLx().equals("1")) {
            costDTOList = insureGetInfoDAO.queryInCost(insureSettleInfoDTO);

        } else if (insureSettleInfoDTO.getLx().equals("2")) {
            costDTOList = insureGetInfoDAO.queryOutCost(insureSettleInfoDTO);

        }
        if (costDTOList.size()>0){
            for (InptCostDTO inptCostDTO : costDTOList) {
                // 医保上传
                Map feedetail = new HashMap();
                feedetail.put("mdtrt_sn",insureSettleInfoDTO.getId()); // 就医流水号
                feedetail.put("ipt_otp_no",inptCostDTO.getId()); // 住院/门诊号
                feedetail.put("med_type",inptCostDTO.getMedType()); // 医疗类别
                feedetail.put("chrg_bchno","0"); // 收费批次号
                feedetail.put("feedetl_sn",inptCostDTO.getId()); // 费用明细流水号
                feedetail.put("psn_cert_type",insureSettleInfoDTO.getNationCode()); // 人员证件类型
                feedetail.put("certno",insureSettleInfoDTO.getCertNo()); // 证件号码
                feedetail.put("psn_name",insureSettleInfoDTO.getName()); // 人员姓名
                feedetail.put("fee_ocur_time", DateUtils.format(inptCostDTO.getCrteTime(),DateUtils.Y_M_DH_M_S)); // 费用发生时间
                feedetail.put("cnt",inptCostDTO.getTotalNum()); // 数量
                feedetail.put("pric",inptCostDTO.getPrice()); // 单价
                feedetail.put("det_item_fee_sumamt",inptCostDTO.getTotalPrice()); // 明细项目费用总额
                feedetail.put("med_list_codg",inptCostDTO.getItemCode()); // 医疗目录编码
                feedetail.put("medins_list_codg",inptCostDTO.getItemCode()); // 医药机构目录编码
                feedetail.put("medins_list_name",inptCostDTO.getItemName()); // 医药机构目录名称
                feedetail.put("med_chrgitm_type","1"); // 医疗收费项目类别
                feedetail.put("prodname",inptCostDTO.getItemName()); // 商品名
                feedetail.put("bilg_dept_codg",inptCostDTO.getDeptId()); // 开单科室编码
                feedetail.put("bilg_dept_name",inptCostDTO.getDeptName()); // 开单科室名称
                feedetail.put("bilg_dr_codg",inptCostDTO.getDoctorId()); // 开单医生编码
                feedetail.put("bilg_dr_name", inptCostDTO.getDoctorName()); // 开单医生姓名
                listMap.add(feedetail);

                // 本地留存记录
                InsureUploadCostDTO insureUploadCostDTO = new InsureUploadCostDTO();
                insureUploadCostDTO.setId(SnowflakeUtils.getId());
                insureUploadCostDTO.setHospCode(insureSettleInfoDTO.getHospCode());
                insureUploadCostDTO.setVisitId(insureSettleInfoDTO.getId());
                insureUploadCostDTO.setCostId(inptCostDTO.getId());
                insureUploadCostDTO.setMdtrtSn(insureSettleInfoDTO.getId());
                insureUploadCostDTO.setIptOtpNo(inptCostDTO.getId());
                insureUploadCostDTO.setMedType(inptCostDTO.getMedType());
                insureUploadCostDTO.setChrgBchno("0");
                insureUploadCostDTO.setFeedetlSn(inptCostDTO.getId());
                insureUploadCostDTO.setPsnCertType(insureSettleInfoDTO.getNationCode());
                insureUploadCostDTO.setCertno(insureSettleInfoDTO.getCertNo());
                insureUploadCostDTO.setPsnName(insureSettleInfoDTO.getName());
                insureUploadCostDTO.setFeeOcurTime(inptCostDTO.getCrteTime());
                insureUploadCostDTO.setCnt(inptCostDTO.getTotalNum());
                insureUploadCostDTO.setPric(inptCostDTO.getPrice());
                insureUploadCostDTO.setDetItemFeeSumamt(inptCostDTO.getTotalPrice());
                insureUploadCostDTO.setMedListCodg(inptCostDTO.getItemCode());
                insureUploadCostDTO.setMedinsListCodg(inptCostDTO.getItemCode());
                insureUploadCostDTO.setMedinsListName(inptCostDTO.getItemCode());
                insureUploadCostDTO.setMedChrgitmType("1");
                insureUploadCostDTO.setProdname(inptCostDTO.getItemName());
                insureUploadCostDTO.setBilgDeptCodg(inptCostDTO.getDeptId());
                insureUploadCostDTO.setBilgDeptName(inptCostDTO.getDeptName());
                insureUploadCostDTO.setBilgDrCodg(inptCostDTO.getDoctorId());
                insureUploadCostDTO.setBilgDrName(inptCostDTO.getDoctorName());
                insureUploadCostDTO.setOrgCode(insureSettleInfoDTO.getMedicineOrgCode());
                insureUploadCostDTO.setCrteId(insureSettleInfoDTO.getCrteId());
                insureUploadCostDTO.setCrteName(insureSettleInfoDTO.getCrteName());
                insureUploadCostDTO.setCrteTime(DateUtils.getNow());
                insureUploadCostDTOList.add(insureUploadCostDTO);
            }

            /**
             * 公共入参
             */
            Map param = new HashMap();
            param.put("infno", "4201");  //交易编号
            param.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
            param.put("insuplc_admdvs", insureConfigurationDTO.getMdtrtareaAdmvs()); //参保地医保区划分
            param.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
            param.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
            param.put("mdtrtarea_admvs",insureConfigurationDTO.getMdtrtareaAdmvs());// 就医地医保区划
            for(Map map : listMap){
                param.put("input", map);  //交易输入
                String json = JSONObject.toJSONString(param);
                logger.info("自费病人费用明细传输入参:" + json);
                String url= insureConfigurationDTO.getUrl();
                String resultJson = HttpConnectUtil.unifiedPayPostUtil(url, json);
                logger.info("自费病人费用明细传输回参:" + resultJson);
                Map<String,Object> resultMap = JSONObject.parseObject(resultJson,Map.class);
                if(StringUtils.isEmpty(resultJson)){
                    throw new AppException("访问统一支付平台失败");
                }
                if("999".equals(resultMap.get("infcode").toString())){
                    throw new AppException((String) resultMap.get("msg"));
                }
                if(!"0".equals(resultMap.get("infcode").toString())){
                    throw new AppException((String)resultMap.get("err_msg"));
                }
            }
            if(insureUploadCostDTOList.size()>0){
                insureGetInfoDAO.insertCost(insureUploadCostDTOList);
            }

        }
        return true;
    }

    /**
     * @Method queryCost
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 19:09
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryCost(InsureSettleInfoDTO insureSettleInfoDTO){

        List<InptCostDTO> costDTOList = new ArrayList<>();
        if (insureSettleInfoDTO.getLx().equals("1")) {
            PageHelper.startPage(insureSettleInfoDTO.getPageNo(),insureSettleInfoDTO.getPageSize());
            costDTOList = insureGetInfoDAO.queryInCost(insureSettleInfoDTO);
        }
        if (insureSettleInfoDTO.getLx().equals("2")) {
            PageHelper.startPage(insureSettleInfoDTO.getPageNo(),insureSettleInfoDTO.getPageSize());
            costDTOList = insureGetInfoDAO.queryOutCost(insureSettleInfoDTO);
        }
        return PageDTO.of(costDTOList);
    }

    /**
     * @Method queryUploadCost
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 19:09
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryUploadCost(InsureSettleInfoDTO insureSettleInfoDTO){

        List<InsureUploadCostDTO> costDTOList = new ArrayList();

        PageHelper.startPage(insureSettleInfoDTO.getPageNo(),insureSettleInfoDTO.getPageSize());
        costDTOList = insureGetInfoDAO.queryAll(insureSettleInfoDTO);

        return PageDTO.of(costDTOList);
    }


    /**
     * @Method queryVisit
     * @Desrciption
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 19:10
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryVisit(InsureSettleInfoDTO insureSettleInfoDTO){
        PageHelper.startPage(insureSettleInfoDTO.getPageNo(),insureSettleInfoDTO.getPageSize());
        List<InptVisitDTO> visitDTOList = insureGetInfoDAO.queryVisit(insureSettleInfoDTO);

        return PageDTO.of(visitDTOList);

    }

    /**
     * @Method queryInsureSettle
     * @Desrciption 为上策基金支付明细
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 19:10
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryInsureSettle(InsureSettleInfoDTO insureSettleInfoDTO){
        PageHelper.startPage(insureSettleInfoDTO.getPageNo(),insureSettleInfoDTO.getPageSize());
        List<PayInfoDTO> payInfoDTO = insureGetInfoDAO.queryInsureSettle(insureSettleInfoDTO);

        return PageDTO.of(payInfoDTO);

    }

    /**
     * @Method queryInsure
     * @Desrciption 未上传费用明细
     * @Param
     * [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date   2021-04-13 19:10
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryInsure(InsureSettleInfoDTO insureSettleInfoDTO){
        PageHelper.startPage(insureSettleInfoDTO.getPageNo(),insureSettleInfoDTO.getPageSize());
        List<ItemInfoDTO> itemInfoDTOList = insureGetInfoDAO.queryInsure(insureSettleInfoDTO);

        return PageDTO.of(itemInfoDTOList);

    }

    /**
     * @Description: 年龄工具类
     * @Param:
     * @return:
     * @Author: zhangxuan
     * @Date: 2021-06-25
     */
    public String getAgeDetail(String date) {
        //如果有空格
        int index = date.indexOf(" ");
        if (index != -1) {
            date = date.substring(0, index);
        }
        String[] data = date.split("-");
        Calendar birthday = new GregorianCalendar(Integer.valueOf(data[0]), Integer.valueOf(data[1]), Integer.valueOf(data[2]));
        Calendar now = Calendar.getInstance();
        int day = now.get(Calendar.DAY_OF_MONTH) - birthday.get(Calendar.DAY_OF_MONTH);
        //月份从0开始计算，所以需要+1
        int month = now.get(Calendar.MONTH) + 1 - birthday.get(Calendar.MONTH);
        int year = now.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
        //按照减法原理，先day相减，不够向month借；然后month相减，不够向year借；最后year相减。
        if (day < 0) {
            month -= 1;
            now.add(Calendar.MONTH, -1);//得到上一个月，用来得到上个月的天数。
            day = day + now.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        if (month < 0) {
            month = (month + 12) % 12;
            year--;
        }
        //System.out.println("年龄：" + year +"岁" + month +"月" + day +"天");
        StringBuffer tag = new StringBuffer();
        if (year > 0) {
            tag.append(year + "岁");
        }
        if (month > 0) {
            tag.append(month + "月");
        }
        if (year == 0 && month == 0 && day == 0) {
            tag.append("今日出生");
        }
        return String.valueOf(tag);
    }


    /**
     * @Description: payInfoListMap// 基金支付信息
     * @Param:insureSettleInfoDTO
     * @return:
     * @Author: yuelong.chen
     * @Date: 2021-08-18
     */
    public List<Map> payInfoListMap(InsureSettleInfoDTO insureSettleInfoDTO,Map map){
        // 基金支付信息
        List<Map> payInfoListMap = new ArrayList<>();
        List<Map<String, Object>> setldetailList = new ArrayList<>();
        Map<String, Object> stringObjectMap2 = insureUnifiedBaseBOImpl.querySettleDeInfo(map);
        setldetailList = MapUtils.get(stringObjectMap2, "setldetail");
        if (!ListUtils.isEmpty(setldetailList)) {
            for(Map<String, Object> map1 : setldetailList){
                Map payInfo = new HashMap();
                payInfo.put("fund_pay_type",map1.get("fund_pay_type"));
                payInfo.put("fund_payamt",map1.get("fund_payamt"));
                payInfoListMap.add(payInfo);
            }
        }
        return payInfoListMap;
    }


    /**
     * @Description: icuinfoListMap重症监护信息
     * @Param:insureSettleInfoDTO
     * @return:
     * @Author: yuelong.chen
     * @Date: 2021-08-18
     */
    public List<Map> icuinfoListMap(InsureSettleInfoDTO insureSettleInfoDTO){
        List<Map> icuinfoListMap = new ArrayList<>();
        List<IcuInfoDTO> icuinfoList = insureGetInfoDAO.queryIcuinfo(insureSettleInfoDTO);
        if (icuinfoList.size() > 0 ) {
            // 重症监护信息
            Map icuinfo = new HashMap();
//            icuinfo.put("scs_cutd_ward_type",""); // 重症监护病房类型
//            icuinfo.put("scs_cutd_inpool_time","");// 重症监护进入时间
//            icuinfo.put("scs_cutd_exit_time",""); // 重症监护退出时间
//            icuinfo.put("scs_cutd_sum_dura",""); // 重症监护合计时长
            icuinfoListMap.add(icuinfo);
        }
        return icuinfoListMap;
    }



    /**
     * @Description: oprninfoListMap手术操作信息
     * @Param:insureSettleInfoDTO
     * @return:
     * @Author: yuelong.chen
     * @Date: 2021-08-18
     */
    public List<Map> oprninfoListMap(InsureSettleInfoDTO insureSettleInfoDTO){
        List<Map> oprninfoListMap = new ArrayList<>();
        List<OprninfoDTO> oprninfoList = insureGetInfoDAO.queryOprninfo(insureSettleInfoDTO);
        // 手术操作信息
        if(oprninfoList.size() > 0 ){
            for (OprninfoDTO oprninfoDTO : oprninfoList){
                Map oprninfo = new HashMap();
                oprninfo.put("oprn_oprt_type",oprninfoDTO.getRank()); //手术操作类别
//                    oprninfo.put("oprn_oprt_type","2"); //手术操作类别
                oprninfo.put("oprn_oprt_name",oprninfoDTO.getOperDiseaseName()); //名称
                oprninfo.put("oprn_oprt_code",oprninfoDTO.getOperDiseaseIcd9()); // 代码
                oprninfo.put("oprn_oprt_date",oprninfoDTO.getOperPlanTime()); // 日期
                oprninfo.put("anst_way",oprninfoDTO.getAnaCode()); // 麻醉方式
                oprninfo.put("oper_dr_name",oprninfoDTO.getDoctorName()); //主刀医生名称
                oprninfo.put("oper_dr_code",oprninfoDTO.getDoctorId()); //主刀医生编码

                oprninfo.put("assistant_id1",oprninfoDTO.getAssistantId1()); //手术一助编码
                oprninfo.put("oassistant_name1",oprninfoDTO.getAssistantName1()); // 手术一助名称
                oprninfo.put("oassistant_name2",oprninfoDTO.getAssistantName2()); // 手术2助名称
                oprninfo.put("assistant_id2",oprninfoDTO.getAssistantId2()); // 手术2助编码
                oprninfo.put("oassistant_name3",oprninfoDTO.getAssistantName3()); //手术3助名称
                oprninfo.put("assistant_id3",oprninfoDTO.getAssistantId3()); //手术3助编码
                oprninfo.put("ana_id1",oprninfoDTO.getAnaId1()); //第一麻醉医生代码
                oprninfo.put("ana_name1",oprninfoDTO.getAnaName1()); // 第一麻醉医生名称
                oprninfo.put("ana_id2",oprninfoDTO.getAnaId2()); // 第2麻醉医生代码
                oprninfo.put("ana_name2",oprninfoDTO.getAnaName2()); // 第2麻醉医生名称
                oprninfo.put("ana_id3",oprninfoDTO.getAnaId3()); //第3麻醉医生代码
                oprninfo.put("ana_name3",oprninfoDTO.getAnaName3()); //第3麻醉医生名称
                oprninfoListMap.add(oprninfo);
            }
        } else {
            Map oprninfo = new HashMap();
            oprninfo.put("oprn_oprt_type","1"); //手术操作类别
            oprninfo.put("oprn_oprt_name","无"); //名称
            oprninfo.put("oprn_oprt_code","0000"); // 代码
            oprninfo.put("oprn_oprt_date",DateUtils.format(DateUtils.getNow(),DateUtils.Y_M_D)); // 日期
            oprninfo.put("anst_way","无"); // 麻醉方式
            oprninfo.put("oper_dr_name","无"); //主刀医生名称
            oprninfo.put("oper_dr_code","0000"); //主刀医生编码

            oprninfoListMap.add(oprninfo);
        }
        return oprninfoListMap;
    }


    /**
     * @Description: diseinfoListMap住院诊断信息
     * @Param:insureSettleInfoDTO
     * @return:
     * @Author: yuelong.chen
     * @Date: 2021-08-18
     */
    public Map diseinfoListMap(InsureSettleInfoDTO insureSettleInfoDTO){
        Map listMap = new HashMap();
        List<Map> diseinfoListMap = new ArrayList<>();
        Map diseinfoMap = new HashMap();
        List<Map> diseinfoListMapIsNotMain = new ArrayList<>();
        List<DiseInfoDTO> diseinfoList = insureGetInfoDAO.queryDiseinfo(insureSettleInfoDTO);
        if (diseinfoList.size() > 0) {
            // 住院诊断信息
            for (DiseInfoDTO diseInfoDTO : diseinfoList){
                Map diseinfo = new HashMap();
                if(diseInfoDTO.getIsMain().equals("1") && !"201".equals(diseInfoDTO.getTypeCode())){
                    diseInfoDTO.setIsMain("0");
                }
                diseinfo.put("diag_type",diseInfoDTO.getTypeCode()); // 诊断类别
                diseinfo.put("diag_code",diseInfoDTO.getDiseaseId()); // 疾病代码
                diseinfo.put("diag_name",diseInfoDTO.getName()); // 疾病名称
                diseinfo.put("maindiag_flag",diseInfoDTO.getIsMain()); // 主诊断标志
                diseinfoListMap.add(diseinfo);
                if(diseInfoDTO.getIsMain().equals("1") && "201".equals(diseInfoDTO.getTypeCode())){
                    diseinfoMap = diseinfo;
                } else {
                    diseinfoListMapIsNotMain.add(diseinfo);
                }
            }
        } else {
            Map diseinfo = new HashMap();
            diseinfo.put("diag_type","3"); // 诊断类别
            diseinfo.put("diag_code","0000"); // 诊断代码
            diseinfo.put("diag_name","无"); // 诊断名称
            diseinfo.put("maindiag_flag","1"); // 主诊断标志
            diseinfoListMap.add(diseinfo);
        }
        listMap.put("diseinfo",diseinfoListMap);
        listMap.put("diseinfoMap",diseinfoMap);
        listMap.put("diseinfoListMapIsNotMain",diseinfoListMapIsNotMain);
        return listMap;
    }



    /**
     * @Description: opspdiseinfoListMap门诊慢特病诊断信息
     * @Param:insureSettleInfoDTO
     * @return:
     * @Author: yuelong.chen
     * @Date: 2021-08-18
     */
    public List<Map> opspdiseinfoListMap(InsureSettleInfoDTO insureSettleInfoDTO){
        List<Map> opspdiseinfoListMap = new ArrayList<>();
        List<OpspdiseInfoDTO> opspdiseinfoList = insureGetInfoDAO.queryOpspdiseinfo(insureSettleInfoDTO);
        if (opspdiseinfoList.size() > 0) {
            // 门诊慢特病诊断信息
            for(OpspdiseInfoDTO opspdiseinfoDTO : opspdiseinfoList){
                Map opspdiseinfo = new HashMap();
                opspdiseinfo.put("diag_name",opspdiseinfoDTO.getName());// 诊断名称
                opspdiseinfo.put("diag_code",opspdiseinfoDTO.getDiseaseId());// 疾病代码
                opspdiseinfo.put("oprn_oprt_code",StringUtils.isEmpty(opspdiseinfoDTO.getOperDiseaseIcd9())? '无':opspdiseinfoDTO.getOperDiseaseIcd9()); // 手术操作代码
                opspdiseinfo.put("oprn_oprt_name",StringUtils.isEmpty(opspdiseinfoDTO.getOperDiseaseName())? '无':opspdiseinfoDTO.getOperDiseaseName()); // 手术操作名称
                opspdiseinfoListMap.add(opspdiseinfo);
            }
        } else {
            Map opspdiseinfo = new HashMap();
            opspdiseinfo.put("diag_name","无");// 诊断名称
            opspdiseinfo.put("diag_code","0000");// 疾病代码
            opspdiseinfo.put("oprn_oprt_name","无"); // 手术操作名称
            opspdiseinfo.put("oprn_oprt_code","0000"); // 手术操作代码
            opspdiseinfoListMap.add(opspdiseinfo);
        }
        return opspdiseinfoListMap;
    }
    /**
     * @Description: iteminfoListMap
     * @Param:
     * @return:
     * @Author: yuelong.chen
     * @Date: 2021-08-18
     */
    public List<Map> iteminfoListMap(InsureSettleInfoDTO insureSettleInfoDTO,Map map){
        List<Map> iteminfoListMap = new ArrayList<>();
        List<Map<String, Object>> feeDetailMapList = new ArrayList<>();
        if("1".equals(insureSettleInfoDTO.getIsHospital())){
            feeDetailMapList = insureGetInfoDAO.selectIsSetlleFee(map);
        }else{
            Map<String, Object> stringObjectMap = insureUnifiedBaseBOImpl.queryFeeDetailInfo(map);
            feeDetailMapList = MapUtils.get(stringObjectMap, "outptMap");
        }
        if (!ListUtils.isEmpty(feeDetailMapList)) {
            Map<String, List<Map<String, Object>>> groupMap = feeDetailMapList.stream().collect(Collectors.groupingBy(item -> MapUtils.get(item, "med_chrgitm_type")));
            Map<String, Object> pMap = null;
            for (String key : groupMap.keySet()) {
                BigDecimal sumDetItemFeeSumamt = new BigDecimal(0.00);//总费用
                BigDecimal fulamtOwnpayAmt = new BigDecimal(0.00);//全自付
                /*BigDecimal preselfpayAmt = new BigDecimal(0.00);*/
                BigDecimal claaSumfee = new BigDecimal(0.00);//甲类
                BigDecimal clabAmt = new BigDecimal(0.00);//乙类
                BigDecimal othAmt = new BigDecimal(0.00);//其他
                BigDecimal clabAmtSum = new BigDecimal(0.00);//全自付金额
                BigDecimal fqzfjs = new BigDecimal(0.00);//非全自付金额
                BigDecimal clabAmtProp = new BigDecimal(0.0000);//全自付比例
                BigDecimal feeSumamt = new BigDecimal(0.00);//费用总额
                BigDecimal x = new BigDecimal(1.0000);//
                DecimalFormat df1 = new DecimalFormat("0.00");
                String medChrgitm =null;
                System.out.println(key + "=====" + groupMap.get(key));
                Iterator<Map<String, Object>> iterator = groupMap.get(key).iterator();
                if (iterator.hasNext()) {
                    pMap = new HashMap<>();
                    List<Map<String, Object>> listMap1 = groupMap.get(key);
                    for (Map<String, Object> item : listMap1) {
                        if(item.containsKey("det_item_fee_sumamt") && StringUtils.isNotEmpty(MapUtils.get(item, "det_item_fee_sumamt").toString())){
                            feeSumamt = BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "det_item_fee_sumamt").toString())));//费用总额
                        }
                        if(item.containsKey("fulamt_ownpay_amt") && StringUtils.isNotEmpty(MapUtils.get(item, "fulamt_ownpay_amt").toString())){
                            clabAmtSum = BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "fulamt_ownpay_amt").toString())));//全自付金额
                        }
                        if(item.containsKey("selfpay_prop") && StringUtils.isNotEmpty(MapUtils.get(item, "selfpay_prop").toString())){
                            clabAmtProp = BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "selfpay_prop").toString())));//全自付比例
                        }
                        if (("01").equals(MapUtils.get(item, "chrgitm_lv").toString())) {
                            claaSumfee = BigDecimalUtils.add(claaSumfee, feeSumamt);
                            fulamtOwnpayAmt = BigDecimalUtils.add(fulamtOwnpayAmt, BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(clabAmtSum.toString()))));
                        } else if (("02").equals(MapUtils.get(item, "chrgitm_lv").toString())){
                            fqzfjs = BigDecimalUtils.multiply(feeSumamt,BigDecimalUtils.subtract(x,clabAmtProp));//非全自付金额
                            clabAmt = BigDecimalUtils.add(clabAmt, BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(fqzfjs.toString()))));
                            fulamtOwnpayAmt = BigDecimalUtils.add(fulamtOwnpayAmt, BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(clabAmtSum.toString()))));
                            othAmt = BigDecimalUtils.add(othAmt, BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert((BigDecimalUtils.subtract(feeSumamt,fqzfjs)).toString()))));
                        }else{
                            othAmt = BigDecimalUtils.add(othAmt, BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert((BigDecimalUtils.subtract(feeSumamt,clabAmtSum)).toString()))));
                            fulamtOwnpayAmt = BigDecimalUtils.add(fulamtOwnpayAmt, BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(clabAmtSum.toString()))));
                        }
                        if (item.containsKey("det_item_fee_sumamt") && MapUtils.get(item, "det_item_fee_sumamt").toString().indexOf(".") > 0) {
                            sumDetItemFeeSumamt = BigDecimalUtils.add(sumDetItemFeeSumamt, BigDecimalUtils.scale(BigDecimalUtils.convert(MapUtils.get(item, "det_item_fee_sumamt").toString()), 2));
                        } else {
                            sumDetItemFeeSumamt = BigDecimalUtils.add(sumDetItemFeeSumamt, BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "det_item_fee_sumamt")))));
                        }
                        medChrgitm = MapUtils.get(item, "med_chrgitm_type");
                    }
                    pMap.put("amt", sumDetItemFeeSumamt);
                    pMap.put("fulamt_ownpay_amt", fulamtOwnpayAmt);
                    pMap.put("claa_sumfee", claaSumfee);
                    pMap.put("clab_amt", clabAmt);
                    pMap.put("oth_amt", othAmt);
                    pMap.put("med_chrgitm", medChrgitm);
                    pMap.put("medChrgitmType", key);
                    iteminfoListMap.add(pMap);
                }
            }
        }
        return  iteminfoListMap;
    }

}
