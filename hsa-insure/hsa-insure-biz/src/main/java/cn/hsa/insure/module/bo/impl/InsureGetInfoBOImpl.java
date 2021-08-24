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
import cn.hsa.module.insure.module.entity.InsureIndividualCostDO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
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
import java.util.function.Function;
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


    @Resource
    private SysParameterService sysParameterService_consumer;

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
        if(settleInfoDTO.getAge()>1){
            setlinfo.put("sage",""); // 年龄小于一岁
        }else{
            setlinfo.put("sage","settleInfoDTO.getAge()");
        }
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
         // 1.先判断是否选择了医保机构
        InsureConfigurationDTO insureConfigurationDTO  = checkInsureConfig(insureSettleInfoDTO);
        insureSettleInfoDTO.setOrgCode(insureConfigurationDTO.getCode());
        // 2.判断是门诊自费病人 还是 住院自费病人  查询费用数据
        List<Map<String,Object>> mapList = new ArrayList<>();
        if (insureSettleInfoDTO.getLx().equals("1")) {

            mapList = handlerInptCostFee(insureSettleInfoDTO);

        } else if (insureSettleInfoDTO.getLx().equals("0")) {
            mapList = handlerOutptCostFee(insureSettleInfoDTO);
        }
        // 3.组装医保接口参数
        List<Map<String,Object>> listMap = new ArrayList<>();
        if (!ListUtils.isEmpty(mapList)){
            for (Map<String,Object> item : mapList) {
                // 医保上传
                Map feedetail = new HashMap();
                String doctorId = MapUtils.get(item,"doctorId");
                String doctorName = MapUtils.get(item,"doctorName");
                feedetail.put("mdtrt_sn",insureSettleInfoDTO.getVisitNo()); // 就医流水号
                feedetail.put("ipt_otp_no",insureSettleInfoDTO.getVisitNo()); // 住院/门诊号
                if("1".equals(insureSettleInfoDTO.getLx())){
                    feedetail.put("med_type","1201"); // 医疗类别
                }else{
                    feedetail.put("med_type","11"); // 医疗类别
                }
                feedetail.put("chrg_bchno","0"); // 收费批次号
                feedetail.put("feedetl_sn",MapUtils.get(item,"id")); // 费用明细流水号
                feedetail.put("psn_cert_type",insureSettleInfoDTO.getCertCode()); // 人员证件类型
                feedetail.put("certno",insureSettleInfoDTO.getCertNo()); // 证件号码
                feedetail.put("psn_name",insureSettleInfoDTO.getName()); // 人员姓名
                if (insureSettleInfoDTO.getLx().equals("1")) {

                    feedetail.put("fee_ocur_time", DateUtils.format((Date) item.get("costTime"),DateUtils.Y_M_DH_M_S)); // 费用发生时间

                } else if (insureSettleInfoDTO.getLx().equals("0")) {
                    feedetail.put("fee_ocur_time", DateUtils.format((Date) item.get("crteTime"),DateUtils.Y_M_DH_M_S)); // 费用发生时间

                }
                BigDecimal cnt = BigDecimalUtils.scale((BigDecimal) item.get("totalNum"), 4);
                BigDecimal price = BigDecimalUtils.scale((BigDecimal) item.get("price"), 4);
                feedetail.put("cnt",cnt); // 数量
                feedetail.put("pric",price); // 单价
                DecimalFormat df1 = new DecimalFormat("0.00");
                String realityPrice = df1.format(BigDecimalUtils.convert(item.get("realityPrice").toString()));
                BigDecimal convertPrice = BigDecimalUtils.convert(realityPrice);
                feedetail.put("det_item_fee_sumamt",convertPrice); // 明细项目费用总额
                feedetail.put("med_list_codg",item.get("insureItemCode") ==null?"":item.get("insureItemCode").toString()); // 医疗目录编码
                feedetail.put("medins_list_codg",item.get("hospItemCode")==null?"":item.get("hospItemCode").toString()); // 医药机构目录编码
                feedetail.put("medins_list_name",MapUtils.get(item,"insureItemName")); // 医药机构目录名称
                feedetail.put("med_chrgitm_type",MapUtils.get(item,"insureItemType")); // 医疗收费项目类别
                feedetail.put("prodname",MapUtils.get(item,"insureItemName")); // 商品名
                feedetail.put("bilg_dept_codg",MapUtils.get(item,"deptId")); // 开单科室编码
                feedetail.put("bilg_dept_name",MapUtils.get(item,"deptName")); // 开单科室名称
                if(StringUtils.isEmpty(MapUtils.get(item,"deptId"))){
                    feedetail.put("bilg_dr_codg",doctorId); // 开单医生编码
                }else {
                    feedetail.put("bilg_dr_codg",MapUtils.get(item,"deptId")); // 开单医生编码
                }
                if(StringUtils.isEmpty(MapUtils.get(item,"doctorName"))){
                    feedetail.put("bilg_dr_codg",doctorName); // 开单医生编码
                }else {
                    feedetail.put("bilg_dr_name",MapUtils.get(item,"doctorName")); // 开单医生姓名
                }
                listMap.add(feedetail);

            }

            /**
             * 公共入参
             */
            Map param = new HashMap();
            param.put("infno", "4201");  //交易编号
            param.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
            param.put("insuplc_admdvs", ""); //参保地医保区划分
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

             insertHandlerInsureCost(map,insureSettleInfoDTO);

            }

        }
        return true;
    }

    /**
     * @Method insertHandlerInsureCost
     * @Desrciption  处理自费病人明细数据
     * @Param  item ：上传参数
     * infoDTO
     *
     * @Author fuhui
     * @Date   2021/8/21 13:57
     * @Return
    **/
    private void insertHandlerInsureCost(Map item,InsureSettleInfoDTO infoDTO) {
        List<InsureIndividualCostDO> insureIndividualCostDOList = new ArrayList<>();
        InsureIndividualCostDO insureIndividualCostDO = new InsureIndividualCostDO();
        insureIndividualCostDO.setId(SnowflakeUtils.getId());//id
        insureIndividualCostDO.setHospCode(infoDTO.getHospCode());//医院编码
        insureIndividualCostDO.setVisitId(infoDTO.getVisitId());//患者id
        insureIndividualCostDO.setCostId(MapUtils.get(item,"feedetl_sn"));//费用id
        insureIndividualCostDO.setSettleId(null);//结算id
        insureIndividualCostDO.setIsHospital(Constants.SF.S);//是否住院 = 是
        insureIndividualCostDO.setItemType(MapUtils.get(item,"med_chrgitm_type"));//医保项目类别
        insureIndividualCostDO.setItemCode(MapUtils.get(item,"med_list_codg"));//医保项目编码
        insureIndividualCostDO.setItemName(MapUtils.get(item,"medins_list_name"));//医保项目名称
        insureIndividualCostDO.setGuestRatio(null);//自付比例
        insureIndividualCostDO.setPrimaryPrice(MapUtils.get(item,"det_item_fee_sumamt"));//原费用
        insureIndividualCostDO.setApplyLastPrice(null);//报销后费用
        insureIndividualCostDO.setOrderNo(null);//顺序号
        insureIndividualCostDO.setInsureIsTransmit(Constants.SF.F);
        insureIndividualCostDO.setTransmitCode(Constants.SF.S);//传输标志 = 已传输
        insureIndividualCostDO.setCrteId(infoDTO.getCrteId());//创建id
        insureIndividualCostDO.setCrteName(infoDTO.getCrteName());//创建人姓名
        insureIndividualCostDO.setCrteTime(new Date());//创建时间
        insureIndividualCostDOList.add(insureIndividualCostDO);
        insureIndividualCostDAO.insertInsureCost(insureIndividualCostDOList);
    }

    /**
     * @Method checkInsureConfig
     * @Desrciption  检查医保机构是否已选择
     * @Param insureSettleInfoDTO
     *
     * @Author fuhui
     * @Date   2021/8/21 13:58
     * @Return
    **/
    private InsureConfigurationDTO checkInsureConfig(InsureSettleInfoDTO insureSettleInfoDTO) {
        Map<String, Object> isInsureUnifiedMap = new HashMap<>();
        isInsureUnifiedMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        isInsureUnifiedMap.put("code", "HOSP_INSURE_CODE");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
        if(sysParameterDTO == null){
            throw new AppException("请先配置默认的医疗机构编码参数信息:编码为:HOSP_INSURE_CODE,值为对应的医疗机构编码值");
        }
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        insureConfigurationDTO.setOrgCode(sysParameterDTO.getValue());
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if(insureConfigurationDTO ==null){
            throw new AppException("医保机构配置信息为空");
        }
        return insureConfigurationDTO;
}

    /**
     * @Method handlerOutptCostFee
     * @Desrciption  处理门诊患者费用
     * @Param
     *
     * @Author fuhui
     * @Date   2021/8/21 10:54
     * @Return
    **/
    private List<Map<String, Object>> handlerOutptCostFee(InsureSettleInfoDTO insureSettleInfoDTO) {

        String insureRegCode = insureSettleInfoDTO.getOrgCode();
        //判断是否有传输费用信息
        Map<String,String> insureCostParam = new HashMap<String,String>();
        insureCostParam.put("hospCode",insureSettleInfoDTO.getHospCode());//医院编码
        insureCostParam.put("statusCode",Constants.ZTBZ.ZC);//状态标志 = 正常
        insureCostParam.put("visitId",insureSettleInfoDTO.getId());//就诊id
        insureCostParam.put("isMatch",Constants.SF.S);//是否匹配 = 是
        insureCostParam.put("transmitCode",Constants.SF.F);//传输标志 = 未传输
        insureCostParam.put("insureRegCode",insureRegCode);// 医保机构编码
        insureCostParam.put("isHospital",Constants.SF.F); // 区分门诊还是住院
        List<Map<String,Object>> insureCostList = insureIndividualCostDAO.queryOutptInsureCostByVisit(insureCostParam);
        if(ListUtils.isEmpty(insureCostList)){
            throw new AppException("该病人没有可以上传的费用");
        }
        return  insureCostList;
    }

    /**
     * @Method handlerInptCostFee
     * @Desrciption  处理住院病人医保费用数据
     * @Param insureSettleInfoDTO
     *
     * @Author fuhui
     * @Date   2021/8/21 10:38
     * @Return
    **/
    private List<Map<String,Object>> handlerInptCostFee(InsureSettleInfoDTO insureSettleInfoDTO) {
        Map<String, String> insureCostParam = new HashMap<String, String>();
        String hospCode = insureSettleInfoDTO.getHospCode();
        String visitId = insureSettleInfoDTO.getId();
        insureCostParam.put("hospCode", hospCode);//医院编码
        insureCostParam.put("statusCode", Constants.ZTBZ.ZC);//状态标志 = 正常
        insureCostParam.put("visitId", visitId);//就诊id
        insureCostParam.put("isMatch", Constants.SF.S);//是否匹配 = 是
        insureCostParam.put("transmitCode", Constants.SF.F);//传输标志 = 未传输
        insureCostParam.put("insureRegCode", insureSettleInfoDTO.getOrgCode());// 医保机构编码
        insureCostParam.put("queryBaby", "N");// 医保机构编码
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
     * @Desrciption  保存未上传的费用数据
     * @Param  insureCostList：未上传的费用集合
     *          inptVisitDTO ：患者基本信息
     *
     * @Author fuhui
     * @Date   2021/8/13 9:16
     * @Return
     **/
    private void insertNotUpLoadFee(List<Map<String, Object>> insureCostList, InsureSettleInfoDTO insureSettleInfoDTO) {
        List<InsureIndividualCostDO> insureIndividualCostDOList = new ArrayList<InsureIndividualCostDO>();
        String hospCode = insureSettleInfoDTO.getHospCode();
        String visitId = insureSettleInfoDTO.getId();
        String crteId = insureSettleInfoDTO.getCrteId();
        String crteName = insureSettleInfoDTO.getCrteName();
        for (Map<String,Object> item : insureCostList){
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
            insureIndividualCostDO.setGuestRatio((String)item.get("deductible"));//自付比例
            insureIndividualCostDO.setPrimaryPrice((BigDecimal)item.get("realityPrice"));//原费用
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
     * @Method queryUnMatchPage
     * @Desrciption  查询自费病人的匹配费用明细
     * @Param
     *
     * @Author fuhui
     * @Date   2021/8/21 15:37
     * @Return
     **/
    @Override
    public PageDTO queryPage(InsureSettleInfoDTO insureSettleInfoDTO) {
        Map<String, Object> isInsureUnifiedMap = new HashMap<>();
        isInsureUnifiedMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        isInsureUnifiedMap.put("code", "HOSP_INSURE_CODE");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
        if(sysParameterDTO == null){
            throw new AppException("请先配置默认的医疗机构编码参数信息:编码为:HOSP_INSURE_CODE,值为对应的医疗机构编码值");
        }
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        insureConfigurationDTO.setOrgCode(sysParameterDTO.getValue());
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        insureSettleInfoDTO.setOrgCode(insureConfigurationDTO.getCode());

        List<InptCostDTO> costDTOList =null;
        if ("1".equals(insureSettleInfoDTO.getLx())) {
            PageHelper.startPage(insureSettleInfoDTO.getPageNo(),insureSettleInfoDTO.getPageSize());
            costDTOList = insureGetInfoDAO.queryInptMatchPage(insureSettleInfoDTO);
        }
        if ( "0".equals(insureSettleInfoDTO.getLx())) {
            PageHelper.startPage(insureSettleInfoDTO.getPageNo(),insureSettleInfoDTO.getPageSize());
            costDTOList = insureGetInfoDAO.queryOutMatchPage(insureSettleInfoDTO);
        }
        return PageDTO.of(costDTOList);
    }


    /**
     * @Method queryUnMatchPage
     * @Desrciption  查询自费病人的未匹配费用明细
     * @Param
     *
     * @Author fuhui
     * @Date   2021/8/21 15:37
     * @Return
    **/
    @Override
    public PageDTO queryUnMatchPage(InsureSettleInfoDTO insureSettleInfoDTO) {
        List<InptCostDTO> costDTOList =null;
        if ( "1".equals(insureSettleInfoDTO.getLx())) {
            PageHelper.startPage(insureSettleInfoDTO.getPageNo(),insureSettleInfoDTO.getPageSize());
            costDTOList = insureGetInfoDAO.queryInptUnMatchPage(insureSettleInfoDTO);
        }
        if ( "0".equals(insureSettleInfoDTO.getLx())) {
            PageHelper.startPage(insureSettleInfoDTO.getPageNo(),insureSettleInfoDTO.getPageSize());
            costDTOList = insureGetInfoDAO.queryOutUnMatchPage(insureSettleInfoDTO);
        }
        return PageDTO.of(costDTOList);
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
