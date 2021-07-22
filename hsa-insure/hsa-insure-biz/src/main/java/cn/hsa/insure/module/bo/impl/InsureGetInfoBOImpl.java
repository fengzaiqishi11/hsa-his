package cn.hsa.insure.module.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.module.bo.InsureGetInfoBO;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureGetInfoDAO;
import cn.hsa.module.insure.module.dto.*;
import cn.hsa.util.DateUtils;
import cn.hsa.util.HttpConnectUtil;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InsureGetInfoBOImpl extends HsafBO implements InsureGetInfoBO {

    @Resource
    private InsureGetInfoDAO insureGetInfoDAO;

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

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
        if (StringUtils.isNotEmpty(aaa)) {
            throw new AppException("该结算单已经上传");
        }
        DecimalFormat df = new DecimalFormat("0.00");
        // 获取医疗机构信息
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        insureConfigurationDTO.setOrgCode(insureSettleInfoDTO.getMedicineOrgCode());  // 医疗机构编码
        InsureConfigurationDTO insureConfigurationDTO1 = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        Map listMap = new HashMap();
        List<Map> payInfoListMap = new ArrayList<>();
        List<Map> opspdiseinfoListMap = new ArrayList<>();
        List<Map> diseinfoListMap = new ArrayList<>();
        List<Map> iteminfoListMap = new ArrayList<>();
        List<Map> oprninfoListMap = new ArrayList<>();
        List<Map> icuinfoListMap = new ArrayList<>();
        // 获取数据库里面的信息
        InsureSettleInfoDTO settleInfoDTO = new InsureSettleInfoDTO();
        if (insureSettleInfoDTO.getIsHospital().equals("1")) {
            settleInfoDTO = insureGetInfoDAO.getSetlInfo(insureSettleInfoDTO);
        } else if(insureSettleInfoDTO.getIsHospital().equals("0")){
            settleInfoDTO = insureGetInfoDAO.getOutSetlInfo(insureSettleInfoDTO);
        }
        List<InsureIndividualFundDTO> payinfoList = insureGetInfoDAO.queryPayinfo(insureSettleInfoDTO);
        List<OpspdiseInfoDTO> opspdiseinfoList = insureGetInfoDAO.queryOpspdiseinfo(insureSettleInfoDTO);
        List<DiseInfoDTO> diseinfoList = insureGetInfoDAO.queryDiseinfo(insureSettleInfoDTO);
        List<ItemInfoDTO> iteminfoList = insureGetInfoDAO.queryIteminfo(insureSettleInfoDTO);
        List<OprninfoDTO> oprninfoList = insureGetInfoDAO.queryOprninfo(insureSettleInfoDTO);
        List<IcuInfoDTO> icuinfoList = insureGetInfoDAO.queryIcuinfo(insureSettleInfoDTO);

        if(StringUtils.isNotEmpty(settleInfoDTO.getInsureSettleId())){
            // 结算清单信息
            Map setlinfo = new HashMap();
            InsureSetlInfoDTO insureSettleInfoDTO1 = new InsureSetlInfoDTO();
            insureSettleInfoDTO1.setId(SnowflakeUtils.getId());
            insureSettleInfoDTO1.setHospCode(settleInfoDTO.getHospCode());
            insureSettleInfoDTO1.setVisitId(settleInfoDTO.getVisitId());
            insureSettleInfoDTO1.setSettleId(settleInfoDTO.getSettleId());
            insureSettleInfoDTO1.setCrteId(insureSettleInfoDTO.getCrteId());
            insureSettleInfoDTO1.setCrteName(insureSettleInfoDTO.getCrteName());
            insureSettleInfoDTO1.setCrteTime(DateUtils.getNow());
            setlinfo.put("mdtrt_id",settleInfoDTO.getMedicalRegNo()); // 就诊ID
            setlinfo.put("psn_no",settleInfoDTO.getAac001()); // 就诊ID
            setlinfo.put("setl_id",settleInfoDTO.getInsureSettleId()); // 结算ID
            setlinfo.put("hi_no",settleInfoDTO.getAac001()); //医保编号
            insureSettleInfoDTO1.setHiNo(settleInfoDTO.getAac001());
            insureSettleInfoDTO1.setFixmedinsName(insureSettleInfoDTO.getHospName());
            setlinfo.put("fixmedins_name",insureSettleInfoDTO.getHospName()); // 定点医药机构名称
            insureSettleInfoDTO1.setFixmedinsCode(insureSettleInfoDTO.getMedicineOrgCode());
            setlinfo.put("fixmedins_code",insureSettleInfoDTO.getMedicineOrgCode()); // 定点医药机构编号
            insureSettleInfoDTO1.setMedcasno(settleInfoDTO.getInProfile());
            setlinfo.put("medcasno",settleInfoDTO.getInProfile()); // 病案号
            insureSettleInfoDTO1.setPsnName(settleInfoDTO.getName());
            setlinfo.put("psn_name",settleInfoDTO.getName()); // 人员姓名
            insureSettleInfoDTO1.setGend(settleInfoDTO.getGenderCode());
            setlinfo.put("gend",settleInfoDTO.getGenderCode()); // 性别
            insureSettleInfoDTO1.setAge(settleInfoDTO.getAge());
            setlinfo.put("age",settleInfoDTO.getAge()); // 年龄
            insureSettleInfoDTO1.setBrdy(settleInfoDTO.getBirthday());
            setlinfo.put("brdy",settleInfoDTO.getBirthday()); // 出生日期
//            insureSettleInfoDTO1.setNtly(settleInfoDTO.getNationalityCation());
//            setlinfo.put("ntly",settleInfoDTO.getNationalityCation()); //国籍
            if (StringUtils.isNotEmpty(settleInfoDTO.getNationCode())) {
                insureSettleInfoDTO1.setNaty(settleInfoDTO.getNationCode());
                setlinfo.put("naty",settleInfoDTO.getNationCode()); // 民族
            } else {
                insureSettleInfoDTO1.setNaty("1");
                setlinfo.put("naty","1"); // 民族
            }
            insureSettleInfoDTO1.setPatnCertType(settleInfoDTO.getCertCode());
            setlinfo.put("patn_cert_type",settleInfoDTO.getCertCode()); // 患者证件类别
            insureSettleInfoDTO1.setCertno(settleInfoDTO.getCertNo());
            setlinfo.put("certno",settleInfoDTO.getCertNo()); // 证件号码
//            insureSettleInfoDTO1.setPrfs(settleInfoDTO.getOccupationCode());
//            setlinfo.put("prfs",settleInfoDTO.getOccupationCode()); // 职业
            insureSettleInfoDTO1.setPrfs("1");
            setlinfo.put("prfs","1"); // 职业
            if(StringUtils.isNotEmpty(settleInfoDTO.getAka130())){
                insureSettleInfoDTO1.setHiType(settleInfoDTO.getAka130());
                setlinfo.put("hi_type",settleInfoDTO.getAka130()); // 医保类型
            } else {
                insureSettleInfoDTO1.setHiType("1");
                setlinfo.put("hi_type","1"); // 医保类型
            }
            insureSettleInfoDTO1.setInsulic(settleInfoDTO.getVisitId());
            setlinfo.put("insuplc",settleInfoDTO.getInsuplcAdmdvs()); // 参保地 *******
            insureSettleInfoDTO1.setInsulic(settleInfoDTO.getInsuplcAdmdvs());
            insureSettleInfoDTO1.setIptMedType(settleInfoDTO.getAka130());
            setlinfo.put("ipt_med_type",settleInfoDTO.getAka130()); // 住院医疗类型
            if (StringUtils.isNotEmpty(settleInfoDTO.getInDeptCode())) {
                insureSettleInfoDTO1.setAdmCaty(settleInfoDTO.getInDeptCode());
                setlinfo.put("adm_caty",settleInfoDTO.getInDeptCode()); // 入院科别
                insureSettleInfoDTO1.setDscgCaty(settleInfoDTO.getOutDeptCode());
                setlinfo.put("dscg_caty",settleInfoDTO.getOutDeptCode()); // 出院科别
            } else {
                insureSettleInfoDTO1.setAdmCaty("111111");
                setlinfo.put("adm_caty","111111"); // 入院科别
                insureSettleInfoDTO1.setDscgCaty("111111");
                setlinfo.put("dscg_caty","111111"); // 出院科别
            }
            insureSettleInfoDTO1.setBillCode(settleInfoDTO.getVisitId());
            setlinfo.put("bill_code",settleInfoDTO.getVisitId()); // 票据代码**********
            insureSettleInfoDTO1.setBillNo(settleInfoDTO.getVisitId());
            setlinfo.put("bill_no",settleInfoDTO.getVisitId()); // 票据号码**********
            insureSettleInfoDTO1.setBizSn(settleInfoDTO.getId());
            setlinfo.put("biz_sn",settleInfoDTO.getId()); // 业务流水号
            insureSettleInfoDTO1.setPsnSelfpay(settleInfoDTO.getPersonPrice());
            setlinfo.put("psn_selfpay",df.format(settleInfoDTO.getPersonPrice())); // 个人自付
            insureSettleInfoDTO1.setPsnOwnpay(settleInfoDTO.getPersonPrice());
            setlinfo.put("psn_ownpay",df.format(settleInfoDTO.getPersonPrice())); // 个人自费
            insureSettleInfoDTO1.setAcctPay(settleInfoDTO.getPersonalPrice());
            setlinfo.put("acct_pay",df.format(settleInfoDTO.getPersonalPrice())); // 个人账户支出
            insureSettleInfoDTO1.setPsnCashpay(settleInfoDTO.getPersonPrice());
            setlinfo.put("psn_cashpay",df.format(settleInfoDTO.getPersonPrice())); // 个人现金支付
            insureSettleInfoDTO1.setHiPaymtd(settleInfoDTO.getSettleway());
            setlinfo.put("hi_paymtd",settleInfoDTO.getSettleway()); // 医保支付方式 *********
            insureSettleInfoDTO1.setHsorg(insureConfigurationDTO1.getName());
            setlinfo.put("hsorg",insureConfigurationDTO1.getName()); // 医保机构
            insureSettleInfoDTO1.setHsorgOpter(settleInfoDTO.getInsureOrgCode());
            setlinfo.put("hsorg_opter",settleInfoDTO.getInsureOrgCode()); // 医保机构经办人
            insureSettleInfoDTO1.setMedinsFillDept(settleInfoDTO.getInsureOrgCode());
            setlinfo.put("medins_fill_dept",settleInfoDTO.getInsureOrgCode()); // 医疗机构填报部门
            insureSettleInfoDTO1.setMedinsFillPsn(settleInfoDTO.getInsureOrgCode());
            setlinfo.put("medins_fill_psn",settleInfoDTO.getInsureOrgCode()); // 医疗机构填报人
            if(StringUtils.isNotEmpty(settleInfoDTO.getNationalityCation())){
                insureSettleInfoDTO1.setNtly(settleInfoDTO.getNationalityCation());
                setlinfo.put("ntly",settleInfoDTO.getNationalityCation()); //国籍
            } else {
                insureSettleInfoDTO1.setNtly("中国");
                setlinfo.put("ntly","中国"); //国籍
            }
                if(StringUtils.isNotEmpty(settleInfoDTO.getContactName())){
                    insureSettleInfoDTO1.setConerName(settleInfoDTO.getContactName());
                    setlinfo.put("coner_name",settleInfoDTO.getContactName()); // 联系人姓名
                } else {
                    insureSettleInfoDTO1.setConerName("亲人");
                    setlinfo.put("coner_name","亲人"); // 联系人姓名
                }
                if (StringUtils.isNotEmpty(settleInfoDTO.getInDeptCode())) {
                    insureSettleInfoDTO1.setAdmCaty(settleInfoDTO.getInDeptCode());
                    setlinfo.put("adm_caty",settleInfoDTO.getInDeptCode()); // 入院科别
                    insureSettleInfoDTO1.setDscgCaty(settleInfoDTO.getOutDeptCode());
                    setlinfo.put("dscg_caty",settleInfoDTO.getOutDeptCode()); // 出院科别
                } else {
                    insureSettleInfoDTO1.setAdmCaty("111111");
                    setlinfo.put("adm_caty","111111"); // 入院科别
                    insureSettleInfoDTO1.setDscgCaty("111111");
                    setlinfo.put("dscg_caty","111111"); // 出院科别
                }
                if(StringUtils.isNotEmpty(settleInfoDTO.getContactName())){
                    insureSettleInfoDTO1.setConerName(settleInfoDTO.getContactName());
                    setlinfo.put("coner_name",settleInfoDTO.getContactName()); // 联系人姓名
                } else {
                    insureSettleInfoDTO1.setConerName("亲人");
                    setlinfo.put("coner_name","亲人"); // 联系人姓名
                }
                if (StringUtils.isNotEmpty(settleInfoDTO.getContactRelaCode())){
                    insureSettleInfoDTO1.setPatnRlts(settleInfoDTO.getContactRelaCode());
                    setlinfo.put("patn_rlts",settleInfoDTO.getContactRelaCode()); // 与患者关系
                } else {
                    insureSettleInfoDTO1.setPatnRlts("亲人");
                    setlinfo.put("patn_rlts","亲人"); // 与患者关系
                }

                if (StringUtils.isNotEmpty(settleInfoDTO.getContactAddress())){
                    insureSettleInfoDTO1.setConerAddr(settleInfoDTO.getContactAddress());
                    setlinfo.put("coner_addr",settleInfoDTO.getContactAddress()); // 联系人地址
                }else{
                    insureSettleInfoDTO1.setConerAddr("中国");
                    setlinfo.put("coner_addr","中国"); // 联系人地址
                }

                if (StringUtils.isNotEmpty(settleInfoDTO.getContactPhone())){
                    insureSettleInfoDTO1.setConerTel(settleInfoDTO.getContactPhone());
                    setlinfo.put("coner_tel",settleInfoDTO.getContactPhone()); // 联系人电话
                } else {
                    insureSettleInfoDTO1.setConerTel("120");
                    setlinfo.put("coner_tel","120"); // 联系人电话
                }
                insureSettleInfoDTO1.setSetlBegnDate(settleInfoDTO.getCrteTime());
                setlinfo.put("setl_begn_date",settleInfoDTO.getCrteTime()); // 结算开始日期
                insureSettleInfoDTO1.setSetlEnDate(settleInfoDTO.getCrteTime());
                setlinfo.put("setl_end_date",settleInfoDTO.getCrteTime()); // 结算结束日期
            // 基金支付信息
            if (payinfoList.size() > 0) {
                for (InsureIndividualFundDTO individualFundDTO : payinfoList){
                    Map payInfo = new HashMap();
                    if (StringUtils.isNotEmpty(individualFundDTO.getFundPayType())) {
                        payInfo.put("fund_pay_type",individualFundDTO.getFundPayType());
                        payInfo.put("fund_payamt",individualFundDTO.getFundPayamt());
                    } else {
                        payInfo.put("fund_pay_type","999998");
                        payInfo.put("fund_payamt","0.00");
                    }
                    payInfoListMap.add(payInfo);
                }
            } else {
                Map payInfo = new HashMap();
                payInfo.put("fund_pay_type","310100");
                payInfo.put("fund_payamt","0.00");
                payInfoListMap.add(payInfo);
            }
            if(insureSettleInfoDTO.getIsHospital().equals("0")){
                if (opspdiseinfoList.size() > 0) {
                    // 门诊慢特病诊断信息
                    for(OpspdiseInfoDTO opspdiseinfoDTO : opspdiseinfoList){
                        Map opspdiseinfo = new HashMap();
                        opspdiseinfo.put("diag_name",opspdiseinfoDTO.getName());// 诊断名称
                        opspdiseinfo.put("diag_code",opspdiseinfoDTO.getDiseaseId());// 诊断代码
                        opspdiseinfo.put("oprn_oprt_code","null"); // 手术操作代码
                        opspdiseinfo.put("oprn_oprt_name","null"); // 手术操作名称
                        opspdiseinfoListMap.add(opspdiseinfo);
                    }
                } else {
                    Map opspdiseinfo = new HashMap();
                    opspdiseinfo.put("diag_name","无");// 诊断名称
                    opspdiseinfo.put("diag_code","0000");// 诊断代码
                    opspdiseinfo.put("oprn_oprt_name","无"); // 手术操作名称
                    opspdiseinfo.put("oprn_oprt_code","0000"); // 手术操作代码
                    opspdiseinfoListMap.add(opspdiseinfo);
                }
            } else if (insureSettleInfoDTO.getIsHospital().equals("1")){
                if (diseinfoList.size() > 0) {
                    // 住院诊断信息
                    for (DiseInfoDTO diseInfoDTO : diseinfoList){
                        Map diseinfo = new HashMap();
                        diseinfo.put("diag_type",diseInfoDTO.getTypeCode()); // 诊断类别
                        diseinfo.put("diag_code",diseInfoDTO.getDiseaseId()); // 诊断代码
                        diseinfo.put("diag_name",diseInfoDTO.getName()); // 诊断名称
                        diseinfo.put("maindiag_flag",diseInfoDTO.getIsMain()); // 主诊断标志
                        diseinfoListMap.add(diseinfo);
                    }
                } else {
                    Map diseinfo = new HashMap();
                    diseinfo.put("diag_type","3"); // 诊断类别
                    diseinfo.put("diag_code","0000"); // 诊断代码
                    diseinfo.put("diag_name","无"); // 诊断名称
                    diseinfo.put("maindiag_flag","1"); // 主诊断标志
                    diseinfoListMap.add(diseinfo);
                }

            }
            if (iteminfoList.size() > 0) {
                // 收费项目信息
                for (ItemInfoDTO itemInfoDTO : iteminfoList){
                    Map iteminfo = new HashMap();
                    iteminfo.put("med_chrgitm",itemInfoDTO.getItemType()); // 医疗收费项目
                    iteminfo.put("amt",df.format(itemInfoDTO.getApplyLastPrice())); // 金额
                    iteminfo.put("claa_sumfee","0"); // 甲类费用合计
                    iteminfo.put("clab_amt","0"); // 乙类金额
                    iteminfo.put("fulamt_ownpay_amt",itemInfoDTO.getFulamtOwnpayAmt()); // 全自费金额
                    iteminfo.put("oth_amt","0"); // 其他金额

                    iteminfoListMap.add(iteminfo);

                }
            }

            // 手术操作信息
            if(oprninfoList.size() > 0 ){
                int i = 0;
                for (OprninfoDTO oprninfoDTO : oprninfoList){
                    Map oprninfo = new HashMap();
                    oprninfo.put("oprn_oprt_type",oprninfoDTO.getRank()); //手术操作类别
//                    oprninfo.put("oprn_oprt_type","2"); //手术操作类别
                    oprninfo.put("oprn_oprt_name",oprninfoDTO.getOperDiseaseName()); //名称
                    oprninfo.put("oprn_oprt_code",oprninfoDTO.getOperDiseaseIcd9()); // 代码
                    oprninfo.put("oprn_oprt_date",oprninfoDTO.getOperPlanTime()); // 日期
                    oprninfo.put("oper_dr_name",oprninfoDTO.getDoctorName()); //主刀医生名称
                    oprninfo.put("oper_dr_code",oprninfoDTO.getDoctorId()); //主刀医生编码
                    if (i == 0) {
                        oprninfo.put("main_oprn_flag","1"); // 手术操作标志，广州市医保必须传 1
                    } else {
                        oprninfo.put("main_oprn_flag","0");
                    }
                    oprninfoListMap.add(oprninfo);
                    i++;
                }
            } else {
                Map oprninfo = new HashMap();
                oprninfo.put("oprn_oprt_type","1"); //手术操作类别
                oprninfo.put("oprn_oprt_name","无"); //名称
                oprninfo.put("oprn_oprt_code","0000"); // 代码
                oprninfo.put("oprn_oprt_date",DateUtils.format(DateUtils.getNow(),DateUtils.Y_M_D)); // 日期
                oprninfo.put("oper_dr_name","无"); //主刀医生名称
                oprninfo.put("oper_dr_code","0000"); //主刀医生编码
                oprninfo.put("main_oprn_flag","1"); // 手术操作标志，广州市医保必须传 1
                oprninfoListMap.add(oprninfo);
            }
            if (icuinfoList.size() > 0 ) {
                // 重症监护信息
                    Map icuinfo = new HashMap();
//            icuinfo.put("scs_cutd_ward_type",""); // 重症监护病房类型
//            icuinfo.put("scs_cutd_inpool_time","");// 重症监护进入时间
//            icuinfo.put("scs_cutd_exit_time",""); // 重症监护退出时间
//            icuinfo.put("scs_cutd_sum_dura",""); // 重症监护合计时长
                    icuinfoListMap.add(icuinfo);
            }

            listMap.put("setlinfo",setlinfo);
            listMap.put("payinfo",payInfoListMap);
            listMap.put("opspdiseinfo",opspdiseinfoListMap);
            listMap.put("diseinfo",diseinfoListMap);
            listMap.put("iteminfo",iteminfoListMap);
            listMap.put("oprninfo",oprninfoListMap);
            listMap.put("icuinfo",icuinfoListMap);
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
                insureSettleInfoDTO1.setSettleNo((String) resultSetl.get("setl_list_id"));
                insureGetInfoDAO.insertSettleInfo(insureSettleInfoDTO1);
            }
        }
        return listMap;
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
     * @Desrciption
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
     * @Desrciption
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

}
