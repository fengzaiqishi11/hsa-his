package cn.hsa.insure.unifiedpay.util.settlementlist;

import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.module.dao.InsureGetInfoDAO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureSettleInfoDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName InptCostRevocationReqUtil
 * @Deacription 住院费用明细撤销-2302
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_4101)
public class SettlementListReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Resource
    private InsureGetInfoDAO insureGetInfoDAO;

    @Override
    public String initRequest(T param) {
        Map map = (Map) param;

        Map listMap = new HashMap();

        //  结算清单信息
        listMap.put("setlinfo",selectBaseSetlInfo(map));
        // 门诊慢特病诊断信息
        listMap.put("opspdiseinfo", selectOpsdiseaseInfo(map));
        // 手术操作信息节点
        listMap.put("oprninfo", selectOprninfo(map));
        // 住院诊断信息节点
        listMap.put("diseinfo", selectDiseaseInfo(map));
        // 重症监护信息
        listMap.put("icuinfo", selectIcuInfo(map));
        //输血信息
        listMap.put("bldinfo",selectBldInfo(map));


        checkRequest(listMap);
        map.put("input", listMap);
        return getInsurCommonParam(map);
    }

    private Object selectBldInfo(Map map) {
        return insureGetInfoDAO.selectBldInfo(map);
    }

    private Object selectIcuInfo(Map map) {
        return insureGetInfoDAO.selectIcuInfo(map);
    }

    private Object selectDiseaseInfo(Map map) {
        List<Map<String, Object>> mapList = insureGetInfoDAO.selectDiseaseInfo(map);
        List<String> diagnoseList = Stream.of("101","102","201","202","203").collect(Collectors.toList());
        mapList.stream().forEach(item->{
            String typeCode = MapUtils.get(item,"diag_type");
            String isMain = MapUtils.getVS(item,"maindiag_flag");
            String diagType = MapUtils.get(item,"diag_type");
            if(diagnoseList.contains(typeCode)){
                item.put("adm_cond_type","4");
            }
            if("201".equals(typeCode) && "1".equals(isMain)){
                item.put("maindiag_flag","0");
            }
            if("204".equals(diagType)){
                item.put("diag_type","1");
            }
        });
        return mapList;
    }

    private Object selectOprninfo(Map map) {
        return insureGetInfoDAO.selectOprninfo(map);
    }

    private Object selectOpsdiseaseInfo(Map map) {
        List<Map<String, Object>> mapList = insureGetInfoDAO.selectOpsdiseaseInfo(map);
        if(!ListUtils.isEmpty(mapList)){
            mapList.stream().forEach(item->{
                // 调用珠海4101接口时   门诊慢特病诊断信息 :无手术编码和名称报错 所以增加条件判断
                if(StringUtils.isEmpty(MapUtils.get(item,"oprn_oprt_name"))){
                    item.put("oprn_oprt_name","无");
                }
                if(StringUtils.isEmpty(MapUtils.get(item,"oprn_oprt_code"))){
                    item.put("oprn_oprt_code","无");
                }
            });
        }
        return mapList;
    }

    private Object selectBaseSetlInfo(Map map) {
        InsureSettleInfoDTO settleInfoDTO = insureGetInfoDAO.selectBaseSetlInfo(map);
        // 结算清单信息
        Map setlinfo = new HashMap();
        setlinfo.put("mdtrt_id", settleInfoDTO.getMdtrtId()); // 就医登记号
        setlinfo.put("psn_no", settleInfoDTO.getHiNo()); // 个人电脑号
        setlinfo.put("setl_id", settleInfoDTO.getInsureSettleId()); // 结算ID
        setlinfo.put("insuplc",settleInfoDTO.getInsuplc());
        setlinfo.put("fixmedins_name", settleInfoDTO.getFixmedinsName()); // 定点医药机构名称
        setlinfo.put("fixmedins_code", settleInfoDTO.getFixmedinsCode()); // 定点医药机构编号
        setlinfo.put("hi_setl_lv", settleInfoDTO.getHiSetlLv());//医保结算等级
        setlinfo.put("hi_no", settleInfoDTO.getHiNo()); //医保编号
        setlinfo.put("medcasno", settleInfoDTO.getMedcasno()); // 病案号
        setlinfo.put("dcla_time", DateUtils.format(settleInfoDTO.getDclaTime(),DateUtils.Y_M_DH_M_S)); //结算清单申报时间
        setlinfo.put("psn_name", settleInfoDTO.getPsnName());// 人员姓名
        setlinfo.put("gend", settleInfoDTO.getGend());// 性别
        setlinfo.put("brdy", DateUtils.format(settleInfoDTO.getBrdy(),DateUtils.Y_M_DH_M_S)); // 出生日期
        setlinfo.put("age", settleInfoDTO.getAge()); // 年龄
        setlinfo.put("ntly", settleInfoDTO.getNtly()); // 国籍
        setlinfo.put("nwb_age", settleInfoDTO.getNebAge()); // （年龄不足1周岁）年龄
        setlinfo.put("naty", settleInfoDTO.getNaty()); // 民族
        setlinfo.put("patn_cert_type", settleInfoDTO.getPatnCertType()); // 患者证件类别
        setlinfo.put("certno", settleInfoDTO.getCertNo()); // 证件号码
        setlinfo.put("prfs", settleInfoDTO.getPrfs()); // 职业
        setlinfo.put("curr_addr", settleInfoDTO.getAddress()); // 现住址
        setlinfo.put("emp_name", settleInfoDTO.getWork()); // 工作单位 *******
        setlinfo.put("emp_addr", settleInfoDTO.getWorkAddress()); // 工作单位地址 *******
        setlinfo.put("emp_tel", settleInfoDTO.getWorkPhone()); // 工作单位电话 *******
        setlinfo.put("poscode", settleInfoDTO.getWorkPostCode()); // 工作单位邮编 *******
        setlinfo.put("coner_name", settleInfoDTO.getConerName()); // 联系人姓名
        setlinfo.put("patn_rlts", settleInfoDTO.getPatnRlts()); // 与患者关系
        setlinfo.put("coner_addr", settleInfoDTO.getConerAddr()); // 联系人地址
        setlinfo.put("coner_tel", settleInfoDTO.getConerTel()); // 联系人电话
        setlinfo.put("hi_type", settleInfoDTO.getHiType()); // 医保类型  也就是险种   sp_psn_type
        setlinfo.put("insuplc", settleInfoDTO.getInsuplc()); // 参保地 *******
        setlinfo.put("sp_psn_type", settleInfoDTO.getSpPsnType()); // 特殊人员类型
        setlinfo.put("nwb_adm_type", settleInfoDTO.getNebAdmType()); // 新生儿入院类型
        setlinfo.put("nwb_bir_wt", settleInfoDTO.getBabyBirthWeight()); // 新生儿出生体重
        setlinfo.put("nwb_adm_wt", settleInfoDTO.getBabyInWeight()); // 新生儿入院体重
        setlinfo.put("opsp_diag_caty", settleInfoDTO.getVisitDrptName()); // 诊断科别 *******
        setlinfo.put("opsp_mdtrt_date", settleInfoDTO.getOpspMdtrtDate()); // 就诊日期 *******
        setlinfo.put("ipt_med_type", settleInfoDTO.getIptMedType()); // 住院医疗类型
        setlinfo.put("adm_way", settleInfoDTO.getInWay()); // 入院途径 *******
        setlinfo.put("trt_type", settleInfoDTO.getTrtType()); // 治疗类别 *******
        setlinfo.put("adm_time", settleInfoDTO.getAdmTime()); // 入院时间 *******
        setlinfo.put("adm_caty", settleInfoDTO.getAdmCaty()); // 入院科别
        setlinfo.put("refldept_dept", settleInfoDTO.getRefldeptDept()); // 转科科别
        setlinfo.put("dscg_caty", settleInfoDTO.getDscgCaty()); // 出院科别
        setlinfo.put("dscg_time", DateUtils.format(settleInfoDTO.getDscgTime(),DateUtils.Y_M_DH_M_S)); // 出院时间 *******
        setlinfo.put("act_ipt_days", settleInfoDTO.getActIptDays()); // 实际住院天数 *******
        setlinfo.put("otp_wm_dise", settleInfoDTO.getOptWmDise()); // 门（急）诊诊断 *******
        setlinfo.put("wm_dise_code", settleInfoDTO.getWmDiswCode()); // 西医诊断疾病代码 *******
        setlinfo.put("otp_tcm_dise", settleInfoDTO.getOptTcmDise()); // 门（急）诊中医诊断 *******
        setlinfo.put("tcm_dise_code", settleInfoDTO.getTcmDiseCode()); // 中医诊断代码 *******
        setlinfo.put("diag_code_cnt", settleInfoDTO.getDiagCodeCnt()); // 诊断代码计数 *******
        setlinfo.put("oprn_oprt_code_cnt", settleInfoDTO.getOprnOprtCodeCnt()); // 手术操作代码计数 *******
        setlinfo.put("vent_used_dura", settleInfoDTO.getVentUsedDura()); // 呼吸机使用时长 *******
        setlinfo.put("pwcry_bfadm_coma_dura", settleInfoDTO.getPwcryBfadmComaDura()); // 颅脑损伤患者入院前昏迷时长 *******
        setlinfo.put("pwcry_afadm_coma_dura", settleInfoDTO.getPwcryAfadmComaDura()); //颅脑损伤患者入院后昏迷时长
        setlinfo.put("bld_cat", settleInfoDTO.getBldCat()); //输血品种
        setlinfo.put("bld_amt", settleInfoDTO.getBldAmt()); //输血量
        setlinfo.put("bld_unt", settleInfoDTO.getBldUnt()); //输血计量单位
        setlinfo.put("spgaNurscare_Days", settleInfoDTO.getSpgaNurscareDays()); // 特级护理天数 *******
        setlinfo.put("lv1Nurscare_Days", settleInfoDTO.getLv1NurscareDays()); // 一级护理天数 *******
        setlinfo.put("scdNurscare_Days", settleInfoDTO.getScdNurscareDays()); // 二级护理天数 *******
        setlinfo.put("lv3Nursecare_Days", settleInfoDTO.getLv3NursecareDays()); // 三级护理天数 *******
        setlinfo.put("dscg_way", settleInfoDTO.getDscgWay()); // 离院方式*******
        setlinfo.put("acp_medins_name", settleInfoDTO.getAcpMedinsName()); // 拟接收机构名称 *******
        setlinfo.put("acp_medins_code", settleInfoDTO.getAcpMedinsName()); // 拟接收机构代码 ******
        setlinfo.put("bill_code", settleInfoDTO.getBillCode()); // 票据代码**********
        setlinfo.put("bill_no", settleInfoDTO.getBillNo()); // 票据号码**********
        setlinfo.put("biz_sn", settleInfoDTO.getBizSn()); // 业务流水号
        setlinfo.put("days_rinp_flag_31", settleInfoDTO.getDaysRinpFlag31()); // 是否有出院31天再住院计划 *******
        setlinfo.put("days_rinp_pup_31", settleInfoDTO.getDaysRinpPup31()); // 出院31天内再住院目的 *******
        setlinfo.put("chfpdr_name", settleInfoDTO.getChfpdrName()); // 主诊医生姓名 *******
        setlinfo.put("chfpdr_code", settleInfoDTO.getChfpdrCode()); // 主诊医生代码 *******
        setlinfo.put("setl_begn_date", DateUtils.format(settleInfoDTO.getSetlBegnDate(),DateUtils.Y_M_DH_M_S)); // 结算开始日期
        setlinfo.put("setl_end_date", DateUtils.format(settleInfoDTO.getSetlEnDate(),DateUtils.Y_M_DH_M_S)); // 结算结束日期
        setlinfo.put("psn_selfpay", settleInfoDTO.getPsnSelfpay()); // 个人自付
        setlinfo.put("psn_ownpay",settleInfoDTO.getPsnOwnpay()); // 个人自费
        setlinfo.put("acct_pay", settleInfoDTO.getAcctPay()); // 个人账户支出
        setlinfo.put("psn_cashpay", settleInfoDTO.getPsnCashpay()); // 个人现金支付
        setlinfo.put("hi_paymtd", settleInfoDTO.getHiPaymtd()); // 医保支付方式
        setlinfo.put("hsorg", settleInfoDTO.getHsorg()); // 医保机构
        setlinfo.put("hsorg_opter",settleInfoDTO.getHosrgOpter()); // 医保机构经办人
        setlinfo.put("medins_fill_dept", settleInfoDTO.getMedinsFillDept()); // 医疗机构填报部门
        setlinfo.put("medins_fill_psn", settleInfoDTO.getMedinsFillPsn()); // 医疗机构填报人
        setlinfo.put("resp_nurs_code", settleInfoDTO.getZrNurseCode()); // 责任护士
        return setlinfo;
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}
