package cn.hsa.insure.module.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.unifiedpay.bo.impl.InsureUnifiedBaseBOImpl;
import cn.hsa.insure.util.InsureUnifiedCommonUtil;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.doctor.service.DoctorAdviceService;
import cn.hsa.module.insure.inpt.service.InsureUnifiedBaseService;
import cn.hsa.module.insure.module.bo.InsureGetInfoBO;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureGetInfoDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualCostDAO;
import cn.hsa.module.insure.module.dto.*;
import cn.hsa.module.insure.module.entity.InsureIndividualCostDO;
import cn.hsa.module.insure.outpt.service.InsureUnifiedPayReversalTradeService;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.prescribe.service.OutptDoctorPrescribeService;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import scala.App;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private SysParameterService sysParameterService_consumer;

    @Resource
    private InsureUnifiedBaseService insureUnifiedBaseService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private InsureUnifiedCommonUtil insureUnifiedCommonUtil;


    /**
     * @Method getSettleInfo
     * @Desrciption 1、交易输入结算清单信息为单行数据，输入其他信息均为多行数据；
     * 2、输入项信息按照《医疗保障基金结算清单填写规范》中的规范要求填写；
     * 3、每次接口调用只上传一位患者的信息。
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-10 14:11
     * @Return java.util.Map
     **/
    @Override
    public Map<String,Object> insertSettleInfo(Map<String,Object> map) {
        String hospCode = MapUtils.get(map,"hospCode");
        // 获取医疗机构信息
        InsureIndividualVisitDTO insureIndividualVisitDTO = insureUnifiedCommonUtil.commonGetVisitInfo(map);
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        String insureRegCode = insureIndividualVisitDTO.getInsureRegCode();
        insureConfigurationDTO.setOrgCode(insureIndividualVisitDTO.getMedicineOrgCode());  // 医疗机构编码
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if (insureConfigurationDTO == null) {
            throw new AppException("根据" + insureRegCode + "医保机构编码获取医保机构配置信息为空");
        }
        Map listMap = new HashMap();
        String functionCode =  selectParamSettleInfo(map);
        if(StringUtils.isEmpty(functionCode)){
            throw new AppException("请先维护系统参数SETTLELEVEL" + "值为医疗结算清单功能号");
        }
        if(!"4101A".equals(functionCode)){
            // 收费项目信息节点
            listMap.put("iteminfo", selectItemInfo(map));
            // 基金信息节点
            listMap.put("payinfo", selectPayInfo(map));
        }
        // 门诊慢特病诊断信息
        listMap.put("opspdiseinfo", selectOpsdiseaseInfo(map));
        // 手术操作信息节点
        listMap.put("oprninfo", selectOprninfo(map));
        // 住院诊断信息节点
        listMap.put("diseinfo", selectDiseaseInfo(map));
        // 重症监护信息
        listMap.put("icuinfo", selectIcuInfo(map));
        //  结算清单信息
        listMap.put("setlinfo",selectBaseSetlInfo(map));

        //输血信息
        listMap.put("bldinfo",selectBldInfo(map));

        map.put("msgName","医疗保障结算清单上传");
        map.put("visitId",insureIndividualVisitDTO.getVisitId());
        map.put("isHospital",insureIndividualVisitDTO.getIsHospital());
        Map<String,Object> resultDataMap = insureUnifiedCommonUtil.commonInsureUnified(hospCode, insureRegCode, functionCode, listMap,map);
        Map<String,Object> outputDataMap = MapUtils.get(resultDataMap,"output");
        Map<String,Object> dataMap = MapUtils.get(outputDataMap,"data");
        map.put("setlListId",MapUtils.get(dataMap,"setl_list_id"));
        insureGetInfoDAO.updateInsureGetInfo(map);
        return listMap;
    }

    /**
     * @Method selectBldInfo
     * @Desrciption  查询输血信息节点信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/4 9:05
     * @Return
     **/
    private List<Map<String,Object>> selectBldInfo(Map<String, Object> map) {
        return insureGetInfoDAO.selectBldInfo(map);
    }

    private String selectParamSettleInfo(Map<String, Object> map) {
        map.put("code", "SETTLELEVEL");
        SysParameterDTO data = sysParameterService_consumer.getParameterByCode(map).getData();
        if (data == null) {
            throw new AppException("请先维护系统参数SETTLELEVEL" + "值为医疗结算清单信息");
        }
        String value =  data.getValue();
        String functionCode ="";
        Map<String, Object> stringObjectMap = JSON.parseObject(value, Map.class);
        for (String key : stringObjectMap.keySet()) {
            if ("functionCode".equals(key)) {
                functionCode = MapUtils.get(stringObjectMap, key);
                break;
            }
        }
        return functionCode;
    }

    /**
     * @Method selectBaseSetlInfo
     * @Desrciption 医疗保障基金结算清单 ： 查询结算信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/3 19:17
     * @Return
     **/
    private Map<String,Object> selectBaseSetlInfo(Map<String, Object> map) {
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

    /**
     * @Method selectOprninfo
     * @Desrciption   医疗保障基金结算清单 -- 查询重症监护信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/3 15:44
     * @Return
     **/
    private List<Map<String,Object>> selectIcuInfo(Map<String, Object> map) {
        return insureGetInfoDAO.selectIcuInfo(map);
    }

    /**
     * @Method selectOprninfo
     * @Desrciption   医疗保障基金结算清单 -- 查询住院诊断信息
     * @Param
     *          1.因为his的住院医保业务,有医保入院办理和医保出院办理两个主诊断  但是结算清单只能上传一个
     * @Author fuhui
     * @Date   2021/11/3 15:44
     * @Return
     **/
    private List<Map<String,Object>> selectDiseaseInfo(Map<String, Object> map) {
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

    /**
     * @Method selectOprninfo
     * @Desrciption   医疗保障基金结算清单 -- 查询门诊慢特病诊断信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/3 15:44
     * @Return
     **/
    private List<Map<String,Object>> selectOpsdiseaseInfo(Map<String, Object> map) {
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

    /**
     * @Method selectOprninfo
     * @Desrciption   医疗保障基金结算清单 -- 查询手术操作信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/3 15:44
     * @Return
     **/
    private List<Map<String,Object>> selectOprninfo(Map<String, Object> map) {
        return insureGetInfoDAO.selectOprninfo(map);
    }

    /**
     * @Method selectPayInfo
     * @Desrciption 医疗保障基金结算清单 -- 查询基金支付信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/3 15:16
     * @Return
     **/
    private Object selectPayInfo(Map<String, Object> map) {
        List<Map<String, Object>> mapList = insureGetInfoDAO.selectHanderPayInfo(map);
        if(ListUtils.isEmpty(mapList)){
            throw new AppException("医疗保障基金结算清单上传时,基金支付信息节点入参为空");
        }
        return mapList ;
    }

    /**
     * @Method saveInsureSettleInfo
     * @Desrciption 保存医疗保障基金结算清单信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/3 14:48
     * @Return
     **/
    @Override
    public  Boolean saveInsureSettleInfo(Map<String,Object>map){
        // 保存手术信息
        insertOperInfo(map);
        // 保存重症监护信息
        insertIcuInfo(map);
        // 保存门诊慢特病诊断信息
        insertOpspdiseinfo(map);
        // 保存 住院诊断信息
        insertDiseaseInfo(map);
        // 保存结清单信息
        insertSetleInfo(map);
        // 保存输血信息
        insertBldInfo(map);

        return true;
    }

    /**
     * @Method insertBldInfo
     * @Desrciption
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/3 17:11
     * @Return
     **/
    private void insertBldInfo(Map<String, Object> map) {
        List<Map<String,Object>> bldInfoMapList = MapUtils.get(map,"bldinfo");
        bldInfoMapList = insertCommonSettleInfo(map, bldInfoMapList);
        if(!ListUtils.isEmpty(bldInfoMapList)){
            insureGetInfoDAO.deleteBldInfo(map);
            insureGetInfoDAO.insertBldInfo(bldInfoMapList);
        }
    }

    /**
     * @param map
     * @Method deleteSettleInfo
     * @Desrciption 重置医疗保障结算清单信息
     * 1.如果已经上传则不允许 清空重置
     * @Param
     * @Author fuhui
     * @Date 2021/11/4 13:58
     * @Return
     */
    @Override
    public Boolean deleteSettleInfo(Map<String, Object> map) {
        InsureSettleInfoDTO insureSettleInfoDTO =  insureGetInfoDAO.querySettlInfo(map);
        if(insureSettleInfoDTO !=null && StringUtils.isEmpty(insureSettleInfoDTO.getSettleNo())){
            throw  new AppException("该结算清单信息已经上传至医保,不能重置内容");
        }
        // 重置setleinfo主节点信息
        insureGetInfoDAO.deleteSettleInfo(map);
        // 重置icu节点信息
        insureGetInfoDAO.deleteIcuInfo(map);
        // 重置门诊慢特病节点信息
        insureGetInfoDAO.deleteOpspdiseinfo(map);
        // 重置基金支付节点信息
        insureGetInfoDAO.deletePayInfo(map);
        // 重置住院诊断节点信息
        insureGetInfoDAO.deleteDiseaseInfo(map);
        // 重置收费项目节点信息
        insureGetInfoDAO.deleteItemInfo(map);
        // 重置手术节点信息
        insureGetInfoDAO.deleteOprninfo(map);
        // 重置输血节点信息
        insureGetInfoDAO.deleteBldInfo(map);
        return true;
    }

    /**
     * @param map
     * @Method selectLoadingSettleInfo
     * @Desrciption 加载保存到数据库的数据信息
     * @Param
     * @Author fuhui
     * @Date 2021/11/6 10:21
     * @Return
     */
    @Override
    public Map<String, Object> selectLoadingSettleInfo(Map<String, Object> map) {
        Map<String,Object> resultDataMap = new HashMap<>();
        Map<String, Object> data = insureUnifiedBaseService.querySettleDeInfo(map).getData();
        List<Map<String, Object>> setldetail = MapUtils.get(data,"setldetail");
        // 基金分项排除 职工基本医疗保险统筹基金（310101）     城乡居民基本医疗保险基金（390101）
        // 基金分项排除 职工基本医疗保险统筹基金（310101）     城乡居民基本医疗保险基金（390101）
        setldetail = setldetail.stream().filter(item->!"310101".equals(MapUtils.get(item,"setl_proc_info")) && !"310100".equals(MapUtils.get(item,"setl_proc_info")) &&
                !"390101".equals(MapUtils.get(item,"setl_proc_info")) && !"390100".equals(MapUtils.get(item,"setl_proc_info"))).
                collect(Collectors.toList());
        Map<String, Object> setlinfoMap = MapUtils.get(data,"setlinfo");

        Map<String,Object> setlinfo =  selectLoadingSetlMsg(map);
        // 查询收费项目信息
        List<Map<String, Object>> itemInfoList = insureGetInfoDAO.selectItemInfo(map);
        // 查询重症监护信息
        List<Map<String, Object>> icuinfoList = insureGetInfoDAO.selectIcuInfoForMap(map);
        // 手术操作信息
        List<Map<String, Object>> oprnfoList = insureGetInfoDAO.selectOprninfoForMap(map);
        // 住院诊断信息
        Map<String,Object> diseaseMap =  handerSetleDiseaInfo(map);
        //  门诊慢特病诊断信息
        List<Map<String, Object>> opspdiseinfoList = insureGetInfoDAO.selectOpspdiseinfoForMap(map);
        // 基金支付信息
        List<Map<String, Object>> payinfoList = insureGetInfoDAO.selectPayinfoForMap(map);

        // 输血信息节点
        List<Map<String, Object>> bldinfoList =  insureGetInfoDAO.selectBldInfoForMap(map);
        String   fulamtOwnpayAmt = DataTypeUtils.dataToNumString(MapUtils.get(setlinfoMap,"fulamt_ownpay_amt"));
        String overlmtSelfpay = DataTypeUtils.dataToNumString(MapUtils.get(setlinfoMap,"overlmt_selfpay"));

        String acctPay = DataTypeUtils.dataToNumString(MapUtils.get(setlinfoMap,"acct_pay"));
        String cashPayamt = DataTypeUtils.dataToNumString(MapUtils.get(setlinfoMap,"cash_payamt"));
        BigDecimal psnOwnpay = BigDecimalUtils.add(fulamtOwnpayAmt, overlmtSelfpay);

        setlinfo.put("psnOwnpay", psnOwnpay); // 个人自费
        setlinfo.put("acctPay", BigDecimalUtils.convert(acctPay)); // 个人账户支出
        setlinfo.put("psnCashpay",  BigDecimalUtils.convert(cashPayamt)); // 个人现金支付
        BigDecimal bigDecimal = BigDecimalUtils.add(acctPay, cashPayamt);
        BigDecimal psnSelfpay = BigDecimalUtils.subtract(bigDecimal, psnOwnpay);
        setlinfo.put("psnSelfpay", psnSelfpay); // 个人自付
        resultDataMap.put("setlinfo",setlinfo);
        resultDataMap.put("payinfo",payinfoList);
        resultDataMap.put("opspdiseinfo",opspdiseinfoList);
        resultDataMap.put("diseinfo",diseaseMap);
        resultDataMap.put("iteminfo",itemInfoList);
        resultDataMap.put("oprninfo",oprnfoList);
        resultDataMap.put("icuinfo",icuinfoList);
        resultDataMap.put("bldinfo",bldinfoList);
        resultDataMap.put("hifp_pay",MapUtils.get(setlinfoMap,"hifp_pay"));
        resultDataMap.put("setldetail",setldetail);
        return resultDataMap;
    }

    /**
     * @param map
     * @Method queryPage
     * @Desrciption 查询结算清单左侧人员类别信息
     * @Param
     * @Author fuhui
     * @Date 2021/11/9 15:29
     * @Return
     */
    @Override
    public PageDTO querySetlePage(Map<String, Object> map) {
        int pageNo = Integer.parseInt(MapUtils.get(map, "pageNo") == null ? "1" : MapUtils.get(map, "pageNo"));
        int pageSize = Integer.parseInt(MapUtils.get(map, "pageSize") == null ? "10" : MapUtils.get(map, "pageSize"));
        PageHelper.startPage(pageNo,pageSize);
        List<Map<String,Object>> list =  insureGetInfoDAO.querySetlePage(map);
        return PageDTO.of(list);
    }

    /**
     * @Method handerSetleDiseaInfo
     * @Desrciption  对中医和西医进行分组  201：住院入院诊断   西医  203：住院西医次诊断
     *                                   202：	住院西医确诊诊断  西医   204:住院西医出院诊断
     *                                   303 :住院中医出院诊断   301:住院中医确诊诊断 302  住院中医次诊断
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/7 19:47
     * @Return
     **/
    private Map<String,Object> handerSetleDiseaInfo(Map<String, Object> map) {
        List<InptDiagnoseDTO> inptDiagnoseDTOList = insureGetInfoDAO.selectDiseinfoForMap(map);
        Map<String,Object> diseaseMap = new HashMap<>();
        List<InptDiagnoseDTO> zxCollect = inptDiagnoseDTOList.stream().filter
                (inptDiagnoseDTO -> "303".equals(inptDiagnoseDTO.getTypeCode()) ||  "301".equals(inptDiagnoseDTO.getTypeCode()) ||
                        "302".equals(inptDiagnoseDTO.getTypeCode()))
                .collect(Collectors.toList());
        List<InptDiagnoseDTO> xiCollect = inptDiagnoseDTOList.stream().filter(inptDiagnoseDTO ->!zxCollect.contains(inptDiagnoseDTO) ).collect(Collectors.toList());
        diseaseMap.put("xiCollect",xiCollect);
        diseaseMap.put("zxCollect",zxCollect);
        diseaseMap.put("diseaseCount",inptDiagnoseDTOList.size());
        return diseaseMap;

    }

    /**
     * @Method selectLoadingSetlMsg
     * @Desrciption  查询结算清单信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/6 10:41
     * @Return
     **/
    private Map<String, Object> selectLoadingSetlMsg(Map<String, Object> map) {
        InsureSettleInfoDTO infoDTO = insureGetInfoDAO.selectBaseSetlInfo(map);
        if(infoDTO ==null){
            throw new AppException("未获取到清单信息,请先选中左侧人员列表进行信息补充");
        }
        String isHospital = MapUtils.get(map,"isHospital");
        Map <String,Object> setlinfo = new HashMap<>();
        setlinfo.put("medicalRegNo", MapUtils.get(map,"medicalRegNo") );
        setlinfo.put("visitId",infoDTO.getVisitId());
        setlinfo.put("mdtrtId", MapUtils.get(map,"medicalRegNo") ); // 就医登记号
        setlinfo.put("insureSettleId", infoDTO.getInsureSettleId()); // 结算ID
        setlinfo.put("setlId", infoDTO.getInsureSettleId()); // 结算ID
        setlinfo.put("fixmedinsName", infoDTO.getFixmedinsName()); // 定点医药机构名称
        setlinfo.put("fixmedinsCode", infoDTO.getFixmedinsCode()); // 定点医药机构编号
        setlinfo.put("hiSetlLv", infoDTO.getHiSetlLv());//医保结算等级
        setlinfo.put("psnNo", infoDTO.getHiNo()); // 医保编号
        setlinfo.put("hiNo",infoDTO.getHiNo()); //医保编号
        setlinfo.put("iptMedType",infoDTO.getIptMedType());  // 住院业务类型
        setlinfo.put("settleNo",infoDTO.getSettleNo()); //流水清单号
        setlinfo.put("medcasno", infoDTO.getMedcasno()); // 病案号
        setlinfo.put("dclaTime",DateUtils.getNow()); //结算清单申报时间
        setlinfo.put("psnName", infoDTO.getPsnName());// 人员姓名
        setlinfo.put("gend", infoDTO.getGend());// 性别
        setlinfo.put("brdy", infoDTO.getBrdy()); // 出生日期
        setlinfo.put("age", infoDTO.getAge()==0?null:infoDTO.getAge()); // 年龄
        setlinfo.put("ntly", infoDTO.getNtly()); // 国籍
        setlinfo.put("nwbAge", infoDTO.getNebAge()==0?null:infoDTO.getNebAge()); // （年龄不足1周岁）年龄
        setlinfo.put("naty",  infoDTO.getNaty()); // 民族
        setlinfo.put("patnCertType",  infoDTO.getPatnCertType()); // 患者证件类别
        setlinfo.put("certno", infoDTO.getCertNo()); // 证件号码
        setlinfo.put("prfs", infoDTO.getPrfs()); // 职业
        setlinfo.put("currAddr", infoDTO.getCurrAddr()); // 现住址
        setlinfo.put("empName", infoDTO.getEmpName()); // 单位名称
        setlinfo.put("empAddr", infoDTO.getEmpAddr()); // 单位地址
        setlinfo.put("empTel", infoDTO.getEmpTel()); // 单位电话
        setlinfo.put("poscode", infoDTO.getPoscode()); // 邮编
        setlinfo.put("conerName", infoDTO.getConerName()); // 联系人姓名
        setlinfo.put("patnRlts", infoDTO.getPatnRlts()); // 与患者关系
        setlinfo.put("conerAddr", infoDTO.getConerAddr()); // 联系人地址
        setlinfo.put("conerTel", infoDTO.getConerTel()); // 联系人电话
        setlinfo.put("hiType", infoDTO.getHiType()); // 医保类型  也就是险种   sp_psn_type
        String insuplcAdmdvs = infoDTO.getInsuplc();
        map.put("insuplcAdmdvs",insuplcAdmdvs);
        Map<String,Object> admdvsMap = insureGetInfoDAO.selectInsuplcAdmdvs(map);
        if(!MapUtils.isEmpty(admdvsMap)){
            setlinfo.put("insuplcName", MapUtils.get(admdvsMap,"admdvsName")); // 参保地 *******
        }
        setlinfo.put("insuplc", insuplcAdmdvs); // 参保地 *******
        setlinfo.put("spPsnType", infoDTO.getSpPsnType()); // 特殊人员类型
        setlinfo.put("nwbAdmType", infoDTO.getNwbAdmType()); // 新生儿入院类型

        setlinfo.put("nwbBirWt", infoDTO.getNwbBirWt()); // 新生儿出生体重 *******
        setlinfo.put("nwbAdmWt", infoDTO.getNwbAdmWt()); // 新生儿入院体重 *******
        if(Constants.SF.F.equals(isHospital)){
            setlinfo.put("opspMdtrtDate", infoDTO.getOpspMdtrtDate()); // 门诊慢特病就诊日期 *******
            setlinfo.put("opspDiagCaty",  infoDTO.getOpspDiagCaty()); // 门诊慢特病诊断科别 *******
        }else{
            setlinfo.put("opspMdtrtDate", null); // 门诊慢特病就诊日期 *******
            setlinfo.put("opspDiagCaty",  null); // 门诊慢特病诊断科别 *******
        }
        setlinfo.put("iptMedTpe", infoDTO.getIptMedType()); // 住院医疗类型
        setlinfo.put("admWay", infoDTO.getAdmWay()); // 入院途径 *******
        String trtType = infoDTO.getTrtType();
        if("10".equals(trtType)){
            setlinfo.put("trtType","1"); // 西医
        }else if("21".equals(trtType)){
            setlinfo.put("trtType","2.1"); // 中医
        }else if("22".equals(trtType)){
            setlinfo.put("trtType","2.2"); // 民族医
        }else{
            setlinfo.put("trtType","3"); // 中西医
        }
        setlinfo.put("admTime", infoDTO.getAdmTime()); // 入院时间 *******
        setlinfo.put("admCaty", infoDTO.getAdmCaty()); // 入院科别
        setlinfo.put("refldeptDept", infoDTO.getRefldeptDept()); // 转科科别
        setlinfo.put("dscgTime", infoDTO.getDscgTime()); // 出院时间 *******
        setlinfo.put("dscgCaty", infoDTO.getDscgCaty()); // 出院科别
        setlinfo.put("actIptDays", infoDTO.getActIptDays()); // 实际住院天数 *******

        setlinfo.put("otpWmDise", infoDTO.getOptWmDise()); // 门（急）诊诊断 *******
        setlinfo.put("wmDiseCode", infoDTO.getWmDiswCode()); // 西医诊断疾病代码 *******
        setlinfo.put("otpTcmDise", infoDTO.getOptTcmDise()); // 门（急）诊中医诊断 *******
        setlinfo.put("tcmDiseCode", infoDTO.getTcmDiseCode()); // 中医诊断代码 *******

        setlinfo.put("otpWmDiseCode", infoDTO.getOptWmDiseCode()); // 中医诊断代码 *******
        setlinfo.put("otpTcmDiseCode", infoDTO.getOptTcmDiseCode()); // 中医诊断代码 *******

        setlinfo.put("diagCodeCnt", infoDTO.getDiagCodeCnt()); // 诊断代码计数 *******
        setlinfo.put("oprnOprtCodeCnt",infoDTO.getOprnOprtCodeCnt()); // 手术操作代码计数 *******
        setlinfo.put("ventUsedDura",infoDTO.getVentUsedDura()); // 呼吸机使用时长 *******
        Object inptBeforeDay =  "";
        Object inptBeforeHour =  "";
        Object inptBeforeMinute = "";
        Object inptLastDay = "";
        Object inptLastHour = "";
        Object inptLastMinute = "";
        setlinfo.put("inptBeforeDay",inptBeforeDay);
        setlinfo.put("inptBeforeHour",inptBeforeHour);
        setlinfo.put("inptBeforeMinute",inptBeforeMinute);
        setlinfo.put("inptLastDay",inptLastDay);
        setlinfo.put("inptLastHour",inptLastHour);
        setlinfo.put("inptLastMinute",inptLastMinute);
        setlinfo.put("pwcryBfadmComaDura", infoDTO.getPwcryBfadmComaDura()); // 颅脑损伤患者入院前昏迷时长 *******
        setlinfo.put("pwcryAfadmComaDura", infoDTO.getPwcryAfadmComaDura()); //颅脑损伤患者入院后昏迷时长
        setlinfo.put("bldCat", infoDTO.getBldCat()); //输血品种
        setlinfo.put("bldAmt", infoDTO.getBldAmt()); //输血量
        setlinfo.put("bldUnt", infoDTO.getBldUnt()); //输血计量单位
        setlinfo.put("spgaNurscareDays",infoDTO.getSpgaNurscareDays()); // 特级护理天数 *******
        setlinfo.put("lv1NurscareDays", infoDTO.getLv1NurscareDays()); // 一级护理天数 *******
        setlinfo.put("scdNurscareDays", infoDTO.getScdNurscareDays()); // 二级护理天数 *******
        setlinfo.put("lv3NurscareDays", infoDTO.getLv3NursecareDays()); // 三级护理天数 *******
        setlinfo.put("dscgWay", infoDTO.getDscgWay()); // 离院方式 *******
        setlinfo.put("acpMedinsName",infoDTO.getAcpMedinsName()); // 拟接收机构名称 *******
        setlinfo.put("acpMedinsCode",infoDTO.getAcpMedinsCode()); // 拟接收机构代码 ******

        setlinfo.put("billCode", infoDTO.getBillCode()); // 票据代码**********
        setlinfo.put("billNo", infoDTO.getBillNo()); // 票据号码**********
        setlinfo.put("bizSn", infoDTO.getBizSn()); // 业务流水号
        setlinfo.put("daysRinpFlag31", infoDTO.getDaysRinpFlag31()); // 是否有出院31天再住院计划 *******
        setlinfo.put("daysRinpPup31",infoDTO.getDaysRinpPup31()); // 出院31天内再住院目的 *******
        setlinfo.put("chfpdrName", infoDTO.getChfpdrName()); // 主诊医生姓名 *******
        setlinfo.put("chfpdrCode", infoDTO.getChfpdrCode()); // 主诊医生代码 *******
        setlinfo.put("zrNurseName", infoDTO.getZrNurseName()); // 责任护士名 *******
        setlinfo.put("zrNurseCode", infoDTO.getZrNurseCode()); // 责任护士代码 *******

        setlinfo.put("setlBegnDate", infoDTO.getSetlBegnDate()); //结算开始日期 *******
        setlinfo.put("setlEndDate", infoDTO.getSetlEnDate()); // 结算结束日期 *******

        setlinfo.put("psnSelfpay", infoDTO.getPsnSelfpay()); // 个人自付  overlmt_selfpay
        setlinfo.put("psnOwnpay", infoDTO.getPsnOwnpay()); // 个人自费
        setlinfo.put("acctPay",infoDTO.getAcctPay()); // 个人账户支出
        setlinfo.put("psnCashpay",  infoDTO.getPsnCashpay()); // 个人现金支付
        setlinfo.put("hiPaymtd", infoDTO.getHiPaymtd()); // 医保支付方式
        setlinfo.put("hsorg", ""); // 医保机构经办人
        setlinfo.put("hsorgOpter", ""); // 医保机构经办人
        setlinfo.put("medinsFillDept", infoDTO.getMedinsFillDept()); // 医疗机构填报部门
        setlinfo.put("medinsFillPsn", infoDTO.getMedinsFillPsn()); // 医疗机构填报人
        return setlinfo;
    }

    /**
     * @Method insertSetleInfo
     * @Desrciption  处理医疗保障基金结算清单 -- 保存清单结算信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/3 20:18
     * @Return
     **/
    private void insertSetleInfo(Map<String, Object> map) {
        Map <String,Object> setlInfoMap = MapUtils.get(map,"setlinfo");
        String isHospital = MapUtils.get(map,"isHospital");
        if(MapUtils.isEmpty(setlInfoMap)){
            throw new AppException("清单结算信息节点数据为空");
        }
        setlInfoMap.put("id",SnowflakeUtils.getId());
        setlInfoMap.put("hospCode",MapUtils.get(map,"hospCode"));
        setlInfoMap.put("crteId",MapUtils.get(map,"crteId"));
        setlInfoMap.put("crteName",MapUtils.get(map,"crteName"));
        setlInfoMap.put("crteTime",DateUtils.getNow());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.Y_M_DH_M_S);
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.Y_M_D);
        Date date = new Date();
        getEmptyErr(setlInfoMap,"ntly","国籍不能为空");
        getEmptyErr(setlInfoMap,"naty","民族不能为空");
        getEmptyErr(setlInfoMap,"brdy","出生日期不能为空");
        getEmptyErr(setlInfoMap,"patnCertType","患者证件类别不能为空");
        getEmptyErr(setlInfoMap,"certno","患者证件号码不能为空");
        getEmptyErr(setlInfoMap,"prfs","职业不能为空");
        getEmptyErr(setlInfoMap,"conerName","联系人姓名不能为空");
        getEmptyErr(setlInfoMap,"patnRlts","与患者关系不能为空");
        getEmptyErr(setlInfoMap,"conerAddr","联系人地址不能为空");
        getEmptyErr(setlInfoMap,"conerTel","联系人电话不能为空");
        getEmptyErr(setlInfoMap,"hiType","医保类型不能为空");
        getEmptyErr(setlInfoMap,"insuplc","参保地不能为空");
        getEmptyErr(setlInfoMap,"billNo","票据号码不能为空");
        getEmptyErr(setlInfoMap,"billCode","票据代码不能为空");
        getEmptyErr(setlInfoMap,"bizSn","业务流水号不能为空");
        getEmptyErr(setlInfoMap,"setlBegnDate","结算期间开始时间不能为空");
        getEmptyErr(setlInfoMap,"setlEndDate","结算期间结束时间不能为空");
        getEmptyErr(setlInfoMap,"medinsFillDept","医疗机构填报部门不能为空");
        getEmptyErr(setlInfoMap,"medinsFillPsn","医疗机构填报人不能为空");


        Object objNwbBirWt = MapUtils.get(setlInfoMap,"nwbBirWt"); // 新生儿出生体重
        if(objNwbBirWt instanceof String || objNwbBirWt == null){
            setlInfoMap.put("nwbBirWt",null); // 新生儿出生体重
        }

        Object objNwbAdmWt = MapUtils.get(setlInfoMap,"nwbAdmWt"); //新生儿入院体重
        if(objNwbAdmWt instanceof String || objNwbAdmWt == null){
            setlInfoMap.put("nwbAdmWt",null); // 新生儿入院体重
        }
        if(Constants.SF.S.equals(isHospital)){
            getEmptyErr(setlInfoMap,"daysRinpFlag31","出院31天内再住院计划标志不能为空");
            getEmptyErr(setlInfoMap,"chfpdrName","主诊医师姓名不能为空");
            getEmptyErr(setlInfoMap,"chfpdrCode","主诊医师代码不能为空");
            String chfpdrCodeStr = MapUtils.get(setlInfoMap,"chfpdrCode");
            if(!"D".equals(chfpdrCodeStr.substring(0,1))){
                throw new AppException("主诊断医师代码必须是D开头的国家贯标码");
            }
            getEmptyErr(setlInfoMap,"zrNurseName","责任护士姓名不能为空");
            getEmptyErr(setlInfoMap,"zrNurseCode","责任护士代码不能为空");
            String zrNurseCodeStr = MapUtils.get(setlInfoMap,"zrNurseCode");
            if(!"N".equals(zrNurseCodeStr.substring(0,1))){
                throw new AppException("责任护士代码必须是N开头的国家贯标码");
            }
            getEmptyErr(setlInfoMap,"dscgWay","离院方式不能为空");
            getEmptyErr(setlInfoMap,"dscgCaty","出院科别不能为空");
            getEmptyErr(setlInfoMap,"admTime","入院时间不能为空");
            getEmptyErr(setlInfoMap,"dscgTime","出院时间不能为空");
            getEmptyErr(setlInfoMap,"admCaty","入院科别不能为空");
            if(MapUtils.get(setlInfoMap,"dscgTime") instanceof  Long){
                date.setTime(MapUtils.get(setlInfoMap,"dscgTime"));
                setlInfoMap.put("dscgTime",simpleDateFormat.format(date)); // 出院时间
            }
            if(MapUtils.get(setlInfoMap,"admTime") instanceof  Long){
                date.setTime(MapUtils.get(setlInfoMap,"admTime"));
                setlInfoMap.put("admTime",simpleDateFormat.format(date)); // 入院时间
            }
            if( MapUtils.get(setlInfoMap,"trtType") !=null){
                String trtType = MapUtils.get(setlInfoMap,"trtType").toString();
                if("1".equals(trtType)){
                    setlInfoMap.put("trtType","10"); // 西医
                }else if("2.1".equals(trtType)){
                    setlInfoMap.put("trtType","21"); // 中医
                }else if("2.2".equals(trtType)){
                    setlInfoMap.put("trtType","22"); // 民族医
                }else{
                    setlInfoMap.put("trtType","30"); // 中西医
                }
            }
        }
        else{
            getEmptyErr(setlInfoMap,"opspDiagCaty","门诊慢特病诊断科别不能为空");
            getEmptyErr(setlInfoMap,"opspMdtrtDate","门诊慢特病就诊日期不能为空");

            if(MapUtils.get(setlInfoMap,"opspMdtrtDate") instanceof  Long){
                date.setTime(MapUtils.get(setlInfoMap,"opspMdtrtDate"));
                setlInfoMap.put("opspMdtrtDate",simpleDateFormat.format(date));
            }
        }
        if(MapUtils.get(setlInfoMap,"dclaTime") instanceof  Long){
            date.setTime(MapUtils.get(setlInfoMap,"dclaTime"));
            setlInfoMap.put("dclaTime",simpleDateFormat.format(date));
        }
        /**
         * 前端传的日期时间型  有可能是Integer  和  Long
         */
        Object brdy = MapUtils.get(setlInfoMap,"brdy");
        if(brdy instanceof  Long ){
            date.setTime((Long) brdy);
            setlInfoMap.put("brdy",dateFormat.format(date)); // 出生日期
        }
        if(brdy instanceof Integer){
            date.setTime( ((Integer) brdy).longValue());
            setlInfoMap.put("brdy",dateFormat.format(date)); // 出生日期
        }
        Object setlBegnDate = MapUtils.get(setlInfoMap,"setlBegnDate");
        if(setlBegnDate !=null){
            setlInfoMap.put("setlBegnDate",DateUtils.getNumerToStringDate(setlBegnDate)); //结算开始时间
        }
        Object setlEndDate =  MapUtils.get(setlInfoMap,"setlEndDate");
        if(setlEndDate !=null){
            setlInfoMap.put("setlEndDate",DateUtils.getNumerToStringDate(setlEndDate)); // 结算结束时间
        }
        Object ageObject = MapUtils.get(setlInfoMap, "age");
        if(ageObject instanceof String && StringUtils.isEmpty(ageObject.toString())){
            setlInfoMap.put("age",null); // 年龄
        }
        Object nwbAgeObject = MapUtils.get(setlInfoMap, "nwbAge");
        if(nwbAgeObject instanceof String && StringUtils.isEmpty(nwbAgeObject.toString())){
            setlInfoMap.put("nwbAge",null); // 新生儿年龄
        }
        insureGetInfoDAO.deleteSetleInfo(setlInfoMap);
        insureGetInfoDAO.insertSetleInfo(setlInfoMap);
    }

    /**
     * @Method insertOpspdiseinfo
     * @Desrciption   处理医疗保障基金结算清单 -- 住院诊断信息
     * @Param  zxCollect:中医诊断集合
     *         xiCollect: 西医诊断集合
     * @Author fuhui
     * @Date   2021/11/2 16:13
     * @Return
     **/
    private synchronized void insertDiseaseInfo(Map<String, Object> map) {
        Map<String,Object > diseaseMap =  MapUtils.get(map,"diseinfo");
        if(MapUtils.isEmpty(diseaseMap)){
            return;
        }
        List<Map<String,Object>> zxCollect = MapUtils.get(diseaseMap,"zxCollect") ;
        List<Map<String,Object>> xiCollect = MapUtils.get(diseaseMap,"xiCollect") ;
        List<Map<String,Object>> inptDiagnoseDTOList = new ArrayList<>();
        /**
         * 如果新增时，默认是西医诊断且是非主诊断
         */
        if(!ListUtils.isEmpty(zxCollect)){
            zxCollect = zxCollect.stream().filter
                    (item->StringUtils.isNotEmpty(MapUtils.get(item,"diseaseName")) && StringUtils.isNotEmpty(MapUtils.get(item,"diseaseCode"))).
                    collect(Collectors.toList());
            if(!ListUtils.isEmpty(zxCollect)){
                List<Map<String, Object>> zyIsMain = zxCollect.stream().filter(item -> "1".equals(MapUtils.get(item, "isMain"))).collect(Collectors.toList());
                if(ListUtils.isEmpty(zyIsMain)){
                    zxCollect.get(0).put("isMain","1");
                }
                zxCollect.stream().forEach(item->{
                    if(StringUtils.isEmpty(MapUtils.get(item,"typeCode"))){
                        item.put("typeCode","2");
                    }
                    if(!"1".equals(MapUtils.get(item,"isMain"))){
                        item.put("isMain","0");
                    }
                });
                inptDiagnoseDTOList.addAll(zxCollect);
            }
        }
        /**
         * 如果新增时，默认是中医诊断且是非主诊断
         */
        if(!ListUtils.isEmpty(xiCollect)){
            xiCollect = xiCollect.stream().filter
                    (item->StringUtils.isNotEmpty(MapUtils.get(item,"diseaseName")) && StringUtils.isNotEmpty(MapUtils.get(item,"diseaseCode"))).
                    collect(Collectors.toList());
            if(!ListUtils.isEmpty(xiCollect)){
                List<Map<String, Object>> xiIsMain = xiCollect.stream().filter(item -> "1".equals(MapUtils.get(item, "isMain"))).collect(Collectors.toList());
                if(ListUtils.isEmpty(xiIsMain)){
                    xiCollect.get(0).put("isMain","1");
                }
                System.out.println("--------------"+xiCollect);
                xiCollect.stream().forEach(item->{
                    if(StringUtils.isEmpty(MapUtils.get(item,"typeCode"))){
                        item.put("typeCode","1");
                    }
                    if(!"1".equals(MapUtils.get(item,"isMain"))){
                        item.put("isMain","0");
                    }
                });
                inptDiagnoseDTOList.addAll(xiCollect);
            }
        }
        if(!ListUtils.isEmpty(inptDiagnoseDTOList)){
            List<Map<String, Object>> mapList = insertCommonSettleInfo(map, inptDiagnoseDTOList);
            insureGetInfoDAO.deleteDisease(map);
            mapList.stream().forEach(item->{
                if(StringUtils.isEmpty(MapUtils.get(item,"admCondType"))){
                    throw new AppException("请完善住院诊断信息节点的入院病情");
                }
            });
            insureGetInfoDAO.insertDisease(mapList);
        }
    }

    /**
     * @Method insertOpspdiseinfo
     * @Desrciption   处理医疗保障基金结算清单 -- 门诊慢特病诊断信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/2 16:13
     * @Return
     **/
    private void insertOpspdiseinfo(Map<String, Object> map) {
        List<Map<String,Object>> opspdiseinfoList =  MapUtils.get(map,"opspdiseinfo");
        opspdiseinfoList = opspdiseinfoList.stream().filter(item->StringUtils.isNotEmpty(MapUtils.get(item,"diagCode"))).collect(Collectors.toList());
        List<Map<String, Object>> mapList = insertCommonSettleInfo(map, opspdiseinfoList);
        if(!ListUtils.isEmpty(mapList)){
            insureGetInfoDAO.deleteOpspdiseinfo(map);
            insureGetInfoDAO.insertOpspdiseinfo(mapList);
        }
    }

    /**
     * @Method getSettleInfo
     * @Desrciption 1、交易输入结算清单信息为单行数据，输入其他信息均为多行数据；
     * 2、输入项信息按照《医疗保障基金结算清单填写规范》中的规范要求填写；
     * 3、每次接口调用只上传一位患者的信息。
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-10 14:11
     * @Return java.util.Map
     **/
    @Override
    public Map<String, Object> insertSetlInfo(Map<String,Object> map) {
        Map<String,Object> resultDataMap;
        InsureSettleInfoDTO insureSettleInfoDTO = insureGetInfoDAO.selectBaseSetlInfo(map);
        if(insureSettleInfoDTO !=null){
            resultDataMap = selectLoadingSettleInfo(map);
        }else{
            resultDataMap = new HashMap<>();
            String isHospital = MapUtils.get(map,"isHospital");
            //1.调用费用明细查询接口 得到收费项目信息节点
            List <Map<String,Object>> itemInfoMap =  insertItemInfo(map);
            // 2.调用结算信息查询   得到基金支付信息节点
            Map<String,Object> settleMap  = insertPayInfo(map);
            List<Map<String, Object>> setldetail = MapUtils.get(settleMap,"setldetail");
            if(!ListUtils.isEmpty(setldetail)){
                // 基金分项排除 职工基本医疗保险统筹基金（310101）     城乡居民基本医疗保险基金（390101）
                setldetail = setldetail.stream().filter(item->!"310101".equals(MapUtils.get(item,"setl_proc_info")) && !"310100".equals(MapUtils.get(item,"setl_proc_info")) &&
                        !"390101".equals(MapUtils.get(item,"setl_proc_info")) && !"390100".equals(MapUtils.get(item,"setl_proc_info"))).
                        collect(Collectors.toList());
            }
            Map<String, Object> setlinfo = MapUtils.get(settleMap,"setlinfo");
            List<Map<String,Object>> mapList = new ArrayList<>();
            List<Map<String,Object>> icuInfoMapList = new ArrayList<>();
            List <OperInfoRecordDTO> infoRecordDTOList = new ArrayList<>();
            Map<String,Object> diseaMap = new HashMap<>();
            if(Constants.SF.F.equals(isHospital)){
                // 3.查询门诊诊断信息   得到门诊慢特病诊断信息节点
                mapList = insertOutDiagnose(map);
                resultDataMap.put("opspdiseinfo",mapList);
            }
            else{
                // 5.查询手术操作信息   得到手术操作节点信息
                infoRecordDTOList =  handerOperInfo(map);
                map.put("operCount",infoRecordDTOList.size());
                // 6.查询重症监护信息   得到重症监护信息节点
                icuInfoMapList =  handerIcuInfo(map);
            }
            // 4.查询住院诊断信息   得到住院诊断节点信息
            diseaMap = handerInptDiagnose(map);
            map.put("diseaseCount",MapUtils.get(diseaMap,"diseaseCount"));
            // 7.结算清单基本信息
            Map<String,Object> setlInfoMap =  handerBaseSetlInfo(map,setlinfo);
            // 输血信息
            List<Map<String, Object>> bldInfoListMap = insureGetInfoDAO.selectBldInfo(map);
            resultDataMap.put("iteminfo",itemInfoMap);
            resultDataMap.put("bldinfo",bldInfoListMap);
            resultDataMap.put("setlinfo",setlInfoMap);
            resultDataMap.put("payinfo",setlinfo);
            resultDataMap.put("icuinfo",icuInfoMapList);
            resultDataMap.put("opspdiseinfo",mapList);
            resultDataMap.put("oprninfo",infoRecordDTOList);
            resultDataMap.put("diseinfo",diseaMap);
            resultDataMap.put("setldetail",setldetail);
            resultDataMap.put("hifp_pay",MapUtils.get(setlinfo,"hifp_pay"));
            String json = JSONObject.toJSONString(resultDataMap);
            System.out.println("----======"+json);
        }
        return resultDataMap;
    }



    /**
     * @Method handerBaseSetlInfo
     * @Desrciption  处理医疗保障基金结算清单: 结算清单信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/2 10:19
     * @Return
     **/
    private Map<String, Object> handerBaseSetlInfo(Map<String, Object> map, Map<String, Object> setlinfoMap) {
        Map<String,Object> setlinfo = new HashMap<>();
        Map<String,Object> baseInfoMap = handerBaseInfo(map);
        Map<String,Object> mriBaseInfo = handerMriBaseInfo(map);

        for (String key : setlinfoMap.keySet()) {
            if ("-".equals(MapUtils.get(setlinfoMap, key))) {
                setlinfoMap.put(key,"");
            }
        }
        for (String key : baseInfoMap.keySet()) {
            if ("-".equals(MapUtils.get(baseInfoMap, key))) {
                baseInfoMap.put(key,"");
            }
        }
        for (String key : mriBaseInfo.keySet()) {
            if ("-".equals(MapUtils.get(mriBaseInfo, key))) {
                mriBaseInfo.put(key,"");
            }
        }

        String hospName =MapUtils.get(map,"hospName");
        String hospCode = MapUtils.get(map,"hospCode");
        String visitId = MapUtils.get(map,"visitId");
        InsureIndividualVisitDTO insureIndividualVisitDTO = insureUnifiedCommonUtil.commonGetVisitInfo(map);
        String insureSettleId = MapUtils.get(map,"insureSettleId");
        String medicalRegNo = insureIndividualVisitDTO.getMedicalRegNo();
        String isHospital = insureIndividualVisitDTO.getIsHospital();
        map.put("isHospital",isHospital);
        setlinfo.put("medicalRegNo", medicalRegNo );
        setlinfo.put("visitId",visitId);
        setlinfo.put("mdtrtId", medicalRegNo ); // 就医登记号
        setlinfo.put("insureSettleId", insureSettleId); // 结算ID
        setlinfo.put("setlId", MapUtils.get(map,"insureSettleId")); // 结算ID
        setlinfo.put("fixmedinsName", MapUtils.get(setlinfoMap,"fixmedins_name")); // 定点医药机构名称
        setlinfo.put("fixmedinsCode", insureIndividualVisitDTO.getMedicineOrgCode()); // 定点医药机构编号
        map.put("code", "SETTLELEVEL");
        SysParameterDTO data = sysParameterService_consumer.getParameterByCode(map).getData();
        if (data == null) {
            throw new AppException("请先维护系统参数SETTLELEVEL" + "值为医院结算等级");
        }
        String value =  data.getValue();
        String setlTitle ="";
        String settleLv ="";
        Map<String, Object> stringObjectMap = JSON.parseObject(value, Map.class);
        for (String key : stringObjectMap.keySet()) {
            if ("setlTitle".equals(key)) {
                setlTitle = MapUtils.get(stringObjectMap, key);
            }
            if("settleLv".equals(key)){ //
                settleLv = MapUtils.get(stringObjectMap,key);
            }
        }
        setlinfo.put("setlTitle",setlTitle);//清单抬头
        setlinfo.put("hiSetlLv", settleLv);//医保结算等级
        setlinfo.put("psnNo", insureIndividualVisitDTO.getAac001()); // 就诊ID
        setlinfo.put("hiNo", insureIndividualVisitDTO.getAac001()); //医保编号
        setlinfo.put("medcasno", MapUtils.get(baseInfoMap,"profileNo")); // 病案号
        setlinfo.put("dclaTime",DateUtils.getNow()); //结算清单申报时间
//        String settleNo = generatorOderNo(hospCode,visitId);
        setlinfo.put("settleNo",null);// 清单流水号
        setlinfo.put("psnName", insureIndividualVisitDTO.getAac003());// 人员姓名
        setlinfo.put("gend", insureIndividualVisitDTO.getAac004());// 性别
        setlinfo.put("brdy", insureIndividualVisitDTO.getAac006()); // 出生日期
        // 当年龄单位是天的时候
        String ageUnitCode =  MapUtils.get(baseInfoMap,"age_unit_code");
        if(Constants.LNDW.S.equals(ageUnitCode)){
            setlinfo.put("age", MapUtils.get(baseInfoMap,"age")); // 年龄
            setlinfo.put("nwbAge",null);
        }else{
            int age = MapUtils.get(baseInfoMap,"age");
            if(Constants.LNDW.Y.equals(ageUnitCode)){
                setlinfo.put("nwbAge",age*30 ); // （年龄不足1周岁）年龄
            }else if(Constants.LNDW.Z.equals(ageUnitCode)){
                age = MapUtils.get(baseInfoMap,"age");
                setlinfo.put("nwbAge",age*7);
            }else{
                setlinfo.put("nwbAge",age);
            }

            setlinfo.put("age", null); // 年龄
        }
        setlinfo.put("ntly", MapUtils.getMapVS(mriBaseInfo,"nationality_cation",MapUtils.get(baseInfoMap,"ntly"))); // 国籍
        // his的名族码表是0  1 2 3 医保码表是01 02 03
        Object  mapVS = MapUtils.getMapVS(mriBaseInfo, "nation_code", MapUtils.get(baseInfoMap, "naty"));
        if(mapVS == null){
            setlinfo.put("naty",  ""); // 民族
        }else{
            setlinfo.put("naty",  "0"+mapVS); // 民族
        }
        setlinfo.put("patnCertType",  MapUtils.getMapVS(mriBaseInfo,"cert_code", (String) MapUtils.getMapVS(baseInfoMap,"cert_code","02"))); // 患者证件类别
        setlinfo.put("certno", MapUtils.getMapVS(mriBaseInfo,"cert_no", (String) MapUtils.getMapVS(baseInfoMap,"cert_no",insureIndividualVisitDTO.getMdtrtCertNo()))); // 证件号码
        setlinfo.put("prfs", MapUtils.getMapVS(mriBaseInfo,"occupation_code",MapUtils.get(baseInfoMap,"occupation_code"))); // 职业
        setlinfo.put("currAddr", MapUtils.getMapVS(mriBaseInfo,"now_adress",MapUtils.get(baseInfoMap,"address"))); // 现住址
        setlinfo.put("empName", MapUtils.getMapVS(mriBaseInfo,"work_info",null)); // 单位名称
        setlinfo.put("empAddr", MapUtils.getMapVS(mriBaseInfo,"work_info",null)); // 单位地址
        setlinfo.put("empTel", MapUtils.getMapVS(mriBaseInfo,"work_phone",null)); // 单位电话
        setlinfo.put("poscode", MapUtils.getMapVS(mriBaseInfo,"work_post_code",null)); // 邮编
        setlinfo.put("conerName", MapUtils.getMapVS(mriBaseInfo,"contact_name",MapUtils.get(baseInfoMap,"contact_name"))); // 联系人姓名
        Object contactRelaCode =  MapUtils.getMapVS(mriBaseInfo,"contact_rela_code",MapUtils.get(baseInfoMap,"contact_rela_code"));
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
        setlinfo.put("patnRlts", contactRelaCode); // 与患者关系  默认患者本人
        setlinfo.put("conerAddr", MapUtils.getMapVS(mriBaseInfo,"contact_address",MapUtils.get(baseInfoMap,"contact_address"))); // 联系人地址
        setlinfo.put("conerTel", MapUtils.getMapVS(mriBaseInfo,"contact_phone",MapUtils.get(baseInfoMap,"contact_phone"))); // 联系人电话
        setlinfo.put("hiType", insureIndividualVisitDTO.getAae140()); // 医保类型  也就是险种   sp_psn_type
        String insuplcAdmdvs = insureIndividualVisitDTO.getInsuplcAdmdvs();
        map.put("insuplcAdmdvs",insuplcAdmdvs);
        Map<String,Object> admdvsMap = insureGetInfoDAO.selectInsuplcAdmdvs(map);
        if(!MapUtils.isEmpty(admdvsMap)){
            setlinfo.put("insuplcName", MapUtils.get(admdvsMap,"admdvsName")); // 参保地 *******
        }
        setlinfo.put("insuplc", insureIndividualVisitDTO.getInsuplcAdmdvs()); // 参保地 *******
        map.put("insureIndividualVisitDTO",insureIndividualVisitDTO);
//        Map<String, Object> data1 = insureUnifiedPayReversalTradeService.checkOneSettle(map, insureIndividualVisitDTO).getData();
        setlinfo.put("spPsnType", ""); // 特殊人员类型
        setlinfo.put("nwbAdmType", MapUtils.get(baseInfoMap,"")); // 新生儿入院类型
        setlinfo.put("nwbBirWt", MapUtils.getMapVS(mriBaseInfo,"baby_birth_weight",null)); // 新生儿出生体重 *******
        setlinfo.put("nwbAdmWt", MapUtils.getMapVS(mriBaseInfo,"baby_in_weight",null)); // 新生儿入院体重 *******
        if(Constants.SF.F.equals(isHospital)){
            setlinfo.put("opspMdtrtDate", insureIndividualVisitDTO.getVisitTime()); // 门诊慢特病就诊日期 *******
            setlinfo.put("opspDiagCaty",  MapUtils.get(baseInfoMap,"inDeptNatinCode")); // 门诊慢特病诊断科别 *******
        }else{
            setlinfo.put("opspMdtrtDate", null); // 门诊慢特病就诊日期 *******
            setlinfo.put("opspDiagCaty",  null); // 门诊慢特病诊断科别 *******
            setlinfo.put("admCaty", MapUtils.get(baseInfoMap,"inDeptNatinCode")); // 入院科别
        }
        setlinfo.put("iptMedTpe", "1"); // 住院医疗类型
        setlinfo.put("admWay", MapUtils.getMapVS(mriBaseInfo,"in_way",MapUtils.get(baseInfoMap,"adm_way"))); // 入院途径 *******
        setlinfo.put("trtType", "1"); // 治疗类别
        setlinfo.put("admTime", MapUtils.get(baseInfoMap,"inTime")); // 入院时间 *******

        String refldeptDept =  selectRefldeptDept(map);
        setlinfo.put("refldeptDept", refldeptDept); // 转科科别
        setlinfo.put("dscgTime", MapUtils.get(baseInfoMap,"out_time")); // 出院时间 *******
        setlinfo.put("dscgCaty", MapUtils.get(baseInfoMap,"outDeptNatinCode")); // 出院科别
        setlinfo.put("actIptDays", insureIndividualVisitDTO.getHospitalDay()); // 实际住院天数 *******

        setlinfo.put("otpWmDise", MapUtils.getMapVS(mriBaseInfo,"disease_icd10_name",MapUtils.get(baseInfoMap,"outpt_disease_name"))); // 门（急）诊诊断名称 *******
        setlinfo.put("otpWmDiseCode", MapUtils.getMapVS(mriBaseInfo,"disease_icd10",MapUtils.get(baseInfoMap,"outpt_disease_name"))); // 门（急）诊诊断编码 *******
        setlinfo.put("wmDiseCcode", ""); // 西医诊断疾病代码 *******
        setlinfo.put("otpTcmDise", ""); // 门（急）诊中医诊断 *******
        setlinfo.put("tcmDiseCode", ""); // 中医诊断代码 *******
        setlinfo.put("diagCodeCnt", MapUtils.get(map,"diseaseCount")); // 诊断代码计数 *******
        setlinfo.put("oprnOprtCodeCnt",MapUtils.get(map,"operCount")); // 手术操作代码计数 *******
        setlinfo.put("ventUsedDura", ""); // 呼吸机使用时长 *******
        Object inptBeforeDay =  "";
        Object inptBeforeHour =  "";
        Object inptBeforeMinute = "";
        Object inptLastDay = "";
        Object inptLastHour = "";
        Object inptLastMinute = "";
        inptBeforeDay = MapUtils.getMapVS(mriBaseInfo,"inpt_before_day",null);
        inptBeforeHour = MapUtils.getMapVS(mriBaseInfo,"inpt_before_hour",null);
        inptBeforeMinute = MapUtils.getMapVS(mriBaseInfo,"inpt_before_minute",null);
        inptLastDay = MapUtils.getMapVS(mriBaseInfo,"inpt_last_day",null);
        inptLastHour = MapUtils.getMapVS(mriBaseInfo,"inpt_last_hour",null);
        inptLastMinute = MapUtils.getMapVS(mriBaseInfo,"inpt_last_minute",null);
        setlinfo.put("inptBeforeDay",inptBeforeDay);
        setlinfo.put("inptBeforeHour",inptBeforeHour);
        setlinfo.put("inptBeforeMinute",inptBeforeMinute);
        setlinfo.put("inptLastDay",inptLastDay);
        setlinfo.put("inptLastHour",inptLastHour);
        setlinfo.put("inptLastMinute",inptLastMinute);
        setlinfo.put("pwcryBfadmComaDra", null); // 颅脑损伤患者入院前昏迷时长 *******
        setlinfo.put("pwcryAfadmComaDura", null); //颅脑损伤患者入院后昏迷时长
        setlinfo.put("bldCat", null); //输血品种
        setlinfo.put("bldAmt", null); //输血量
        setlinfo.put("bldUnt", null); //输血计量单位
        setlinfo.put("spgaNurscareDays",MapUtils.get(baseInfoMap,"spgaNurscareDays")); // 特级护理天数 *******
        setlinfo.put("lv1NurscareDays", MapUtils.get(baseInfoMap,"lv1NurscareDays")); // 一级护理天数 *******
        setlinfo.put("scdNurscareDays", MapUtils.get(baseInfoMap,"scdNurscareDays")); // 二级护理天数 *******
        Object nursecareDays = MapUtils.get(baseInfoMap, "lv3NursecareDays");
        if(nursecareDays == null){
            setlinfo.put("lv3NurscareDays",0); // 三级护理天数 *******
        }else{
            setlinfo.put("lv3NurscareDays",MapUtils.get(baseInfoMap,"lv3NursecareDays")); // 三级护理天数 *******
        }
        setlinfo.put("dscgWay",MapUtils.get(baseInfoMap,"out_mode_code")); // 离院方式 *******
        setlinfo.put("acpMedinsName", ""); // 拟接收机构名称 *******
        setlinfo.put("acpMedinsCode", ""); // 拟接收机构代码 ******
        Map<String,Object> billNoMap =  generatorBillNo(map);
        setlinfo.put("billCode", MapUtils.get(billNoMap,"billCode")); // 票据代码**********
        setlinfo.put("billNo", MapUtils.get(billNoMap,"billNo")); // 票据号码**********
        if(StringUtils.isEmpty(insureIndividualVisitDTO.getOmsgid())){
            setlinfo.put("bizSn", StringUtils.createMsgId(insureIndividualVisitDTO.getMedicineOrgCode())); // 业务流水号
        }else{
            setlinfo.put("bizSn", MapUtils.get(setlinfoMap,"setl_id")); // 业务流水号(取结算id)
        }
        setlinfo.put("daysRinpFlag31", MapUtils.getMapVS(mriBaseInfo,"is_inpt","")); // 是否有出院31天再住院计划 *******
        setlinfo.put("daysRinpPup31",MapUtils.getMapVS(mriBaseInfo,"aim","")); // 出院31天内再住院目的 *******
        setlinfo.put("chfpdrName", MapUtils.get(baseInfoMap,"zz_doctor_name")); // 主诊医生姓名 *******
        setlinfo.put("chfpdrCode", MapUtils.get(baseInfoMap,"doctorPracCertiNo")); // 主诊医生代码 *******
        setlinfo.put("zrNurseName", MapUtils.get(baseInfoMap,"resp_nurse_name")); // 责任护士名 *******
        setlinfo.put("zrNurseCode", MapUtils.get(baseInfoMap,"nursePracCertiNo")); // 责任护士代码 *******
        Object setlBegnDate = MapUtils.get(baseInfoMap, "setlBegnDate");
        if(setlBegnDate ==null){
            throw new AppException("结算开始时间为空");
        }else{
            setlinfo.put("setlBegnDate", DateUtils.parse(getDateToString(MapUtils.get(baseInfoMap,"setlBegnDate")),DateUtils.Y_M_D)); //结算开始日期 *******
        }
        Object setlEndDate = MapUtils.get(setlinfoMap, "setl_time");
        if(setlEndDate ==null){
            throw new AppException("结算结束时间为空");
        }else{

            setlinfo.put("setlEndDate", DateUtils.parse((String) setlEndDate,DateUtils.Y_M_D)); // 结算结束日期 *******
        }
        // 全自费金额
        String   fulamtOwnpayAmt = DataTypeUtils.dataToNumString(MapUtils.get(setlinfoMap,"fulamt_ownpay_amt"));
        // 超限价自费金额
        String overlmtSelfpay = DataTypeUtils.dataToNumString(MapUtils.get(setlinfoMap,"overlmt_selfpay"));

        String acctPay = DataTypeUtils.dataToNumString(MapUtils.get(setlinfoMap,"acct_pay"));
        String cashPayamt = DataTypeUtils.dataToNumString(MapUtils.get(setlinfoMap,"cash_payamt"));
        /**
         * 2021/12/09 个人自费 =全自费金额（fulamtOwnpayAmt）  不包含超限价自费金额
         */
        // 个人自费 == 全自费金额（fulamtOwnpayAmt） +超限价自费金额（overlmtSelfpay）
        BigDecimal psnOwnpay = BigDecimalUtils.convert(fulamtOwnpayAmt);
        /**
         *     金额合计=医保统筹基金支付+补充医疗保险支付+医疗救助支付+个人负担
         *     补充医疗保险支付：职工大额补助+居民大病保险+公务员医疗补助
         *     medfeeAumamt:总金额
         *     hifobPay:职工大额医疗费用补助基金支出
         *     hifmiPay:居民大病保险资金支出
         *     cvlserv_pay:公务员医疗补助资金支出
         *     psnPartAmt: 个人负担总金额
         *     hifpPayStr:基本医疗保险统筹基金支出
         *
         */
        String medfeeAumamtStr = DataTypeUtils.dataToNumString(MapUtils.get(setlinfoMap,"medfee_sumamt"));
        String hifpPayStr = DataTypeUtils.dataToNumString(MapUtils.get(setlinfoMap,"hifp_pay"));
        String hifobPayStr = DataTypeUtils.dataToNumString(MapUtils.get(setlinfoMap,"hifob_pay"));
        String hifmiPayStr = DataTypeUtils.dataToNumString(MapUtils.get(setlinfoMap,"hifmi_pay"));
        String cvlservPayStr = DataTypeUtils.dataToNumString(MapUtils.get(setlinfoMap,"cvlserv_pay"));
        String mafPayStr = DataTypeUtils.dataToNumString(MapUtils.get(setlinfoMap,"maf_pay"));
        BigDecimal medfeeAumamt = BigDecimalUtils.convert(medfeeAumamtStr);
        BigDecimal hifobPay = BigDecimalUtils.convert(hifobPayStr);
        BigDecimal hifmiPay = BigDecimalUtils.convert(hifmiPayStr);
        BigDecimal cvlservPay = BigDecimalUtils.convert(cvlservPayStr);
        BigDecimal mafPay = BigDecimalUtils.convert(mafPayStr);
        BigDecimal hifpPay = BigDecimalUtils.convert(hifpPayStr);
        BigDecimal psnPartAmt = BigDecimalUtils.subtractMany(medfeeAumamt,hifobPay,hifmiPay,cvlservPay,mafPay,hifpPay);
        setlinfo.put("psnOwnpay", psnOwnpay); // 个人自费
        setlinfo.put("acctPay", BigDecimalUtils.convert(acctPay)); // 个人账户支出
        setlinfo.put("psnCashpay",  BigDecimalUtils.convert(cashPayamt)); // 个人现金支付
        BigDecimal bigDecimal = BigDecimalUtils.add(acctPay, cashPayamt);
        // 个人自付 = 个人负担 - 个人自费
        BigDecimal psnSelfpay = BigDecimalUtils.subtract(psnPartAmt, psnOwnpay);
        setlinfo.put("psnSelfpay", psnSelfpay); // 个人自付
        setlinfo.put("hiPaymtd", "3"); // 医保支付方式  // 默认是按病种分值
        setlinfo.put("hsorg", ""); // 医保机构经办人
        setlinfo.put("hsorgOpter", ""); // 医保机构经办人
        setlinfo.put("medinsFillDept", "医保科"); // 医疗机构填报部门 默认为 医保科
        setlinfo.put("medinsFillPsn", MapUtils.get(baseInfoMap,"feeCrteTime")); // 医疗机构填报人
        return setlinfo;
    }

    /**
     * @Method selectRefldeptDept
     * @Desrciption  查询患者的转院科室
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/8 14:21
     * @Return
     **/
    private String selectRefldeptDept(Map<String, Object> map) {
        return insureGetInfoDAO.selectRefldeptDept(map);
    }

    /**
     * @Method handerMriBaseInfo
     * @Desrciption 当是住院病人时  患者基础信息可以冲病案首页加载
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/5 10:12
     * @Return
     **/
    private Map<String, Object> handerMriBaseInfo(Map<String, Object> map) {

        return  insureGetInfoDAO.selectMriBaseInfo(map);

    }

    /**
     * @Method getDateToString
     * @Desrciption  时间戳格式转标准字符串格式
     * @Param
     *
     * @Author fuhui
     * @Date   2021/12/4 22:12
     * @Return
     **/
    public String getDateToString(java.sql.Timestamp time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtils.Y_M_D);
        return simpleDateFormat.format(time);
    }

    /**
     * @Method generatorBillNo
     * @Desrciption  生成票据号码
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/3 20:02
     * @Return
     **/
    private Map<String,Object> generatorBillNo(Map<String, Object> map) {
        String isHospital = MapUtils.get(map,"isHospital");
        String billNo = "";
        String billCode = "" ;
        Map<String,Object> dataMap;
        if(Constants.SF.S.equals(isHospital)){
            dataMap = insureGetInfoDAO.selectInptBillNo(map);
        }else{
            dataMap = insureGetInfoDAO.selectOutptBillNo(map);
        }
        if(MapUtils.isEmpty(dataMap)){
            throw new AppException("该患者未产生发票信息");
        }
        billNo = MapUtils.get(dataMap,"invoice_no");
        billCode = MapUtils.get(dataMap,"prefix") ;
        map.put("billNo",billNo);
        map.put("billCode",billCode);
        return map ;
    }

    /**
     * @Method
     * @Desrciption  查询结算清单的基本信息数据
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/2 11:40
     * @Return
     **/
    private Map<String, Object> handerBaseInfo (Map<String,Object> map){
        String isHospital = MapUtils.get(map,"isHospital");
        if(Constants.SF.S.equals(isHospital)){
            map =   insureGetInfoDAO.getInptPatientInfo(map);
        }else{
            map = insureGetInfoDAO.getOutptPatientInfo(map);
        }
        return map;
    }

    /**
     * @Method handerIcuInfo
     * @Desrciption  处理医疗保障基金结算清单 : 重症监护信息节点
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/2 9:57
     * @Return
     **/
    private List<Map<String, Object>> handerIcuInfo(Map<String, Object> map) {

        List<Map<String, Object>> icuMapList = insureGetInfoDAO.selectIcuInfo(map);
        if (ListUtils.isEmpty(icuMapList)) {
//            // 重症监护信息
//            icuinfo = new HashMap();
//            icuinfo.put("scsCutdWardType",""); // 重症监护病房类型
//            icuinfo.put("scsSutdInpoolTime","");// 重症监护进入时间
//            icuinfo.put("scs_cutd_exit_time",""); // 重症监护退出时间
//            icuinfo.put("scs_cutd_sum_dura",""); // 重症监护合计时长
//            icuinfoListMap.add(icuinfo);
        }
        return icuMapList;
    }

    /**
     * @Method handerOperInfo
     * @Desrciption   处理医疗保障基金结算清单 : 手术操作信息节点
     * @Param       1.先查病案首页的手术信息 没有没有查询到 则查
     *
     * @Author fuhui
     * @Date   2021/11/2 9:29
     * @Return
     **/
    private List<OperInfoRecordDTO> handerOperInfo(Map<String, Object> map) {
        List <OperInfoRecordDTO> infoRecordDTOList = new ArrayList<>();
        infoRecordDTOList = insureGetInfoDAO.selectMriOperInfo(map);
        if(!ListUtils.isEmpty(infoRecordDTOList)){
            infoRecordDTOList.get(0).setOprnOprtType("1");
        }
        return infoRecordDTOList;
    }

    /**
     * @Method handerOutDiagnose
     * @Desrciption  处理医疗保障基金结算清单：住院诊断节点信息
     *          1.因为清单上面需要的诊断是出院诊断(从病案首页获取，由于病案首页的无中医出院诊断类型)
     *          his的码表入院病案码表  0：代表情况无   医保码表 4：代表无
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/2 9:03
     * @Return
     **/
    private Map<String,Object> handerInptDiagnose(Map<String, Object> map) {
        Map<String,Object> diseaseMap = new HashMap<>();
        List<InptDiagnoseDTO> inptDiagnoseDTOList = insureGetInfoDAO.selectMriInptDiagNose(map);
        List<InptDiagnoseDTO> zxCollect = new ArrayList<>();
        List<InptDiagnoseDTO> xiCollect = new ArrayList<>();
        if(!ListUtils.isEmpty(inptDiagnoseDTOList)){
            inptDiagnoseDTOList = inptDiagnoseDTOList.stream().filter(inptDiagnoseDTO -> !"201".equals(inptDiagnoseDTO.getTypeCode())).collect(Collectors.toList());
            inptDiagnoseDTOList.stream().forEach(inptDiagnoseDTO ->{
                if("0".equals(inptDiagnoseDTO.getAdmCondType())){
                    inptDiagnoseDTO.setAdmCondType("4");
                }
            });
            zxCollect = inptDiagnoseDTOList.stream().filter
                    (inptDiagnoseDTO -> "303".equals(inptDiagnoseDTO.getTypeCode()) ||  "301".equals(inptDiagnoseDTO.getTypeCode()) ||
                            "302".equals(inptDiagnoseDTO.getTypeCode()))
                    .collect(Collectors.toList());
            if(!ListUtils.isEmpty(zxCollect)){
                zxCollect.stream().forEach(inptDiagnoseDTO -> {
                    inptDiagnoseDTO.setTypeCode("2");
                });
            }
            List<InptDiagnoseDTO> finalZxCollect = zxCollect;
            xiCollect = inptDiagnoseDTOList.stream().filter(inptDiagnoseDTO ->!finalZxCollect.contains(inptDiagnoseDTO) ).collect(Collectors.toList());
            if(!ListUtils.isEmpty(xiCollect)){
                xiCollect.stream().forEach(inptDiagnoseDTO -> {
                    inptDiagnoseDTO.setTypeCode("1");
                });
            }
        }
        diseaseMap.put("xiCollect",xiCollect);
        diseaseMap.put("zxCollect",zxCollect);
        diseaseMap.put("diseaseCount",inptDiagnoseDTOList.size());
        return diseaseMap;
    }

    /**
     * @Method handerOutDiagnose
     * @Desrciption  处理医疗保障基金结算清单：门诊慢特病诊断节点信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/2 9:03
     * @Return
     **/
    private List<Map<String,Object>> insertOutDiagnose(Map<String, Object> map) {

        String hospCode = MapUtils.get(map,"hospCode");
        String visitId = MapUtils.get(map,"visitId");
        String medicalRegNo = MapUtils.get(map,"medicalRegNo");
        String insureSettleId = MapUtils.get(map,"insureSettleId");
        InsureIndividualVisitDTO insureIndividualVisitDTO =  insureGetInfoDAO.selectInsureIndividual(map);
        List<Map<String,Object>> mapList = new ArrayList<>();
        Map<String,Object> diagNoseMap;
        if(insureIndividualVisitDTO !=null){
            diagNoseMap = new HashMap<>();
            diagNoseMap.put("id",SnowflakeUtils.getId());
            diagNoseMap.put("hospCode",hospCode);
            diagNoseMap.put("visitId",visitId);
            diagNoseMap.put("medicalRegNo",medicalRegNo);
            diagNoseMap.put("insureSettleId",insureSettleId);
            if(StringUtils.isEmpty(insureIndividualVisitDTO.getBka006())){
                diagNoseMap.put("diagCode",insureIndividualVisitDTO.getVisitIcdCode());
            }else{
                diagNoseMap.put("diagCode",insureIndividualVisitDTO.getBka006());
            }
            if(StringUtils.isEmpty(insureIndividualVisitDTO.getBka006Name())){
                diagNoseMap.put("diagName",insureIndividualVisitDTO.getVisitIcdName());
            }else{
                diagNoseMap.put("diagName",insureIndividualVisitDTO.getBka006Name());
            }
            diagNoseMap.put("oprnOprtName","");
            diagNoseMap.put("oprnOprtCode","");
            mapList.add(diagNoseMap);
        }
        return mapList;
    }

    /**
     * @Method insertPayInfo
     * @Desrciption  调用结算信息查询接口  -- 处理基金支付信息
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/11/2 8:46
     * @Return
     **/
    private Map<String, Object> insertPayInfo(Map<String, Object> map) {
        Map<String, Object> data = insureUnifiedBaseService.querySettleDeInfo(map).getData();
        List<Map<String, Object>> setldetail = MapUtils.get(data,"setldetail");
        List<Map<String, Object>> mapList = insertCommonSettleInfo(map, setldetail);
        if(!ListUtils.isEmpty(mapList)){
            insureGetInfoDAO.deletePayInfo(map);
            insureGetInfoDAO.insertPayInfo(mapList);
        }
        return data;
    }

    /**
     * @Method insertIcuInfo
     * @Desrciption   处理医疗保障基金结算清单 -- 重症监护信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/2 16:13
     * @Return
     **/
    private void insertIcuInfo(Map<String, Object> map) {

        List<Map<String,Object>> icuinfoList =  MapUtils.get(map,"icuinfo");
        if(!ListUtils.isEmpty(icuinfoList)){
            icuinfoList = icuinfoList.stream().filter(item->StringUtils.isNotEmpty(MapUtils.get(item,"scsCutdWardType"))).collect(Collectors.toList());
            icuinfoList.stream().forEach(item->{
                Object scsCutdInpoolTime = MapUtils.get(item,"scsCutdInpoolTime");
                if(scsCutdInpoolTime !=null){
                    item.put("scsCutdInpoolTime", DateUtils.getNumerToStringDate(scsCutdInpoolTime));
                }
                Object scsCutdExitTime = MapUtils.get(item,"scsCutdExitTime");
                if(scsCutdExitTime !=null){
                    item.put("scsCutdExitTime", DateUtils.getNumerToStringDate(scsCutdExitTime));
                }
            });
            List<Map<String, Object>> mapList = insertCommonSettleInfo(map, icuinfoList);
            if(!ListUtils.isEmpty(mapList)){
                insureGetInfoDAO.deleteIcuInfo(map);
                insureGetInfoDAO.insertIcuInfo(mapList);
            }
        }
    }

    /**
     * @Method insertCommonSettleInfo
     * @Desrciption  公共保存医疗结算清单的方法
     * @Param  mapList 节点信息    map: 结算就诊信息
     *
     * @Author fuhui
     * @Date   2021/11/2 17:23
     * @Return
     **/
    private List<Map<String,Object>> insertCommonSettleInfo(Map<String,Object> map ,List<Map<String,Object>> mapList ){
        String hospCode = MapUtils.get(map,"hospCode");
        String visitId = MapUtils.get(map,"visitId");
        String medicalRegNo = MapUtils.get(map,"medicalRegNo");
        String insureSettleId = MapUtils.get(map,"insureSettleId");
        if(!ListUtils.isEmpty(mapList)){
            mapList.stream().forEach(item->{
                item.put("id",SnowflakeUtils.getId());
                item.put("hospCode",hospCode);
                item.put("medicalRegNo",medicalRegNo);
                item.put("visitId",visitId);
                item.put("insureSettleId",insureSettleId);
            });
        }
        return mapList;
    }

    /**
     * @Method insertOperInfo
     * @Desrciption  处理医疗保障基金结算清单 -- 保存手术信息
     * @Param  map
     *
     * @Author fuhui
     * @Date   2021/11/2 16:04
     * @Return
     **/
    private synchronized void insertOperInfo(Map<String, Object> map) {

        List<Map<String,Object>> opernInfoList =  MapUtils.get(map,"oprninfo");
        if(!ListUtils.isEmpty(opernInfoList)){
            opernInfoList = opernInfoList.stream().filter(item->StringUtils.isNotEmpty(MapUtils.get(item,"oprnOprtCode"))).collect(Collectors.toList());
            if(!ListUtils.isEmpty(opernInfoList)){
                opernInfoList.get(0).put("oprnOprtType","1");
                opernInfoList.stream().forEach(item->{
                    Object oprnOprtStartTime  = MapUtils.get(item,"oprnOprtStartTime");
                    if(oprnOprtStartTime !=null){
                        item.put("oprnOprtStartTime",DateUtils.getNumerToStringDate(oprnOprtStartTime)); // 手术开始时间
                    }
                    Object oprnOprtEndTime  = MapUtils.get(item,"oprnOprtEndTime");
                    if(oprnOprtEndTime !=null){
                        item.put("oprnOprtEndTime",DateUtils.getNumerToStringDate(oprnOprtEndTime)); // 手术结束时间
                    }
                    Object anstDrStartTime  = MapUtils.get(item,"anstDrStartTime");
                    if(anstDrStartTime !=null){
                        item.put("anstDrStartTime",DateUtils.getNumerToStringDate(anstDrStartTime)); // 麻醉开始时间
                    }
                    Object anstDrEndTime  = MapUtils.get(item,"anstDrEndTime");
                    if(anstDrEndTime !=null){
                        item.put("anstDrEndTime",DateUtils.getNumerToStringDate(anstDrEndTime)); // 麻醉结束时间
                    }
                    Object oprnOprtDate =  MapUtils.get(item,"oprnOprtDate");
                    /**
                     * 如果直接在清单上面填写  手术信息，则需要根据手术开始时间和结束时间来确定手术操作日期
                     */
                    if(oprnOprtDate == null && oprnOprtStartTime !=null){
                        item.put("oprnOprtDate",DateUtils.parse(DateUtils.getNumerToStringDate(oprnOprtStartTime),DateUtils.Y_M_D)); // 麻醉结束时间
                    }else{
                        item.put("oprnOprtDate",DateUtils.getNumerToStringDate(oprnOprtDate)); // 手术操作日期
                    }
                    /**
                     * 前端默认第一条数据就是主要手术  1:代表主要手术  2：其他手术
                     */
                    if(!"1".equals(MapUtils.get(item,"oprnOprtType"))){
                        item.put("oprnOprtType","2"); // 手术操作类别
                    }

                });

                List<Map<String, Object>> mapList = insertCommonSettleInfo(map, opernInfoList);
                if(!ListUtils.isEmpty(mapList)){
                    insureGetInfoDAO.deleteOperInfo(map);
                    insureGetInfoDAO.insertOperInfo(mapList);
                }
            }
        }
    }

    /**
     * @Method insertItemInfo
     * @Desrciption  处理医疗保障基金结算清单：收费项目节点
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/1 20:13
     * @Return
     **/
    private List<Map<String,Object>> insertItemInfo(Map<String, Object> map) {

        List<Map<String, Object>> list =  insureGetInfoDAO.selectItemInfo(map);
        List<Map<String, Object>> feeDetailMapList ;
        List<Map<String, Object>> groupListMap = new ArrayList<>();
        if(!ListUtils.isEmpty(list)){
            return list ;
        }
        Map<String, Object> data = insureUnifiedBaseService.queryFeeDetailInfo(map).getData();
        feeDetailMapList = MapUtils.get(data,"outptMap");
        if(ListUtils.isEmpty(feeDetailMapList)){
            throw new AppException("没有获取到医保费用明细数据");
        }
        Map<String, List<Map<String, Object>>> groupMap = feeDetailMapList.stream().
                collect(Collectors.groupingBy(item -> MapUtils.get(item, "med_chrgitm_type")));
        Map<String, Object> pMap = null;
        for (String key : groupMap.keySet()) {
            BigDecimal sumDetItemFeeSumamt = new BigDecimal(0.00); // 总费用
            BigDecimal AClassFee = new BigDecimal(0.00);  // 甲类费用
            BigDecimal BClassFee = new BigDecimal(0.00);  // 乙类费用
            BigDecimal CClassFee = new BigDecimal(0.00);  // 丙类费用
            BigDecimal otherClassFee = new BigDecimal(0.00) ; // 其他费用
            System.out.println(key + "=====" + groupMap.get(key));
            Iterator<Map<String, Object>> iterator = groupMap.get(key).iterator();
            if (iterator.hasNext()) {
                pMap = new HashMap<>();
                List<Map<String, Object>> listMap = groupMap.get(key);
                for (Map<String, Object> item : listMap) {
                    DecimalFormat df1 = new DecimalFormat("0.00");
                    sumDetItemFeeSumamt = BigDecimalUtils.add(sumDetItemFeeSumamt,
                            BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert
                                    (MapUtils.get(item, "det_item_fee_sumamt") == null ? "" :
                                            MapUtils.get(item, "det_item_fee_sumamt").toString()))));
                    if("01".equals(MapUtils.get(item, "chrgitm_lv"))){
                        AClassFee = BigDecimalUtils.add(AClassFee, BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "det_item_fee_sumamt") == null ? "" : MapUtils.get(item, "det_item_fee_sumamt").toString()))));
                    }
                    if("02".equals(MapUtils.get(item, "chrgitm_lv"))){
                        BClassFee = BigDecimalUtils.add(BClassFee, BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "det_item_fee_sumamt") == null ? "" : MapUtils.get(item, "det_item_fee_sumamt").toString()))));
                    }
                    if("03".equals(MapUtils.get(item, "chrgitm_lv"))){
                        CClassFee = BigDecimalUtils.add(CClassFee, BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "fulamt_ownpay_amt") == null ? "" : MapUtils.get(item, "fulamt_ownpay_amt").toString()))));
                    }
                }
                otherClassFee = BigDecimalUtils.subtractMany(sumDetItemFeeSumamt,AClassFee,BClassFee,CClassFee);
                pMap.put("amt", sumDetItemFeeSumamt);
                pMap.put("claaSumfee", AClassFee);
                pMap.put("clabAmt", BClassFee);
                pMap.put("fulamtOwnpayAmt", CClassFee);
                pMap.put("othAmt", otherClassFee);
                pMap.put("medChrgitm", key);
                groupListMap.add(pMap);
            }
        }
        List<Map<String, Object>> mapList = insertCommonSettleInfo(map, groupListMap);
        insureGetInfoDAO.deleteItemInfo(map);
        insureGetInfoDAO.insertItemInfo(mapList);
        return groupListMap;
    }


    /**
     * @return
     * @Method getInsureCost
     * @Desrciption 自费病人费用明细信息上传
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-11 22:54
     * @Return java.util.Map
     */
    @Override
    public Boolean insertInsureCost(InsureSettleInfoDTO insureSettleInfoDTO) {
        // 1.先判断是否选择了医保机构
        InsureConfigurationDTO insureConfigurationDTO = checkInsureConfig(insureSettleInfoDTO);
        insureSettleInfoDTO.setOrgCode(insureConfigurationDTO.getCode());
        // 2.判断是门诊自费病人 还是 住院自费病人  查询费用数据
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (insureSettleInfoDTO.getLx().equals("1")) {

            mapList = handlerInptCostFee(insureSettleInfoDTO);

        } else if (insureSettleInfoDTO.getLx().equals("0")) {
            mapList = handlerOutptCostFee(insureSettleInfoDTO);
        }
        // 3.组装医保接口参数
        List<Map<String, Object>> listMap = new ArrayList<>();
        if (!ListUtils.isEmpty(mapList)) {
            for (Map<String, Object> item : mapList) {
                // 医保上传
                Map feedetail = new HashMap();
                String doctorId = MapUtils.get(item, "doctorId");
                String doctorName = MapUtils.get(item, "doctorName");
                feedetail.put("mdtrt_sn", insureSettleInfoDTO.getVisitNo()); // 就医流水号
                feedetail.put("ipt_otp_no", insureSettleInfoDTO.getVisitNo()); // 住院/门诊号
                if ("1".equals(insureSettleInfoDTO.getLx())) {
                    feedetail.put("med_type", "1201"); // 医疗类别
                } else {
                    feedetail.put("med_type", "11"); // 医疗类别
                }
                feedetail.put("chrg_bchno", "0"); // 收费批次号
                feedetail.put("feedetl_sn", MapUtils.get(item, "id")); // 费用明细流水号
                feedetail.put("psn_cert_type", insureSettleInfoDTO.getCertCode()); // 人员证件类型
                feedetail.put("certno", insureSettleInfoDTO.getCertNo()); // 证件号码
                feedetail.put("psn_name", insureSettleInfoDTO.getName()); // 人员姓名
                if (insureSettleInfoDTO.getLx().equals("1")) {

                    feedetail.put("fee_ocur_time", DateUtils.format((Date) item.get("costTime"), DateUtils.Y_M_DH_M_S)); // 费用发生时间

                } else if (insureSettleInfoDTO.getLx().equals("0")) {
                    feedetail.put("fee_ocur_time", DateUtils.format((Date) item.get("crteTime"), DateUtils.Y_M_DH_M_S)); // 费用发生时间

                }
                BigDecimal cnt = BigDecimalUtils.scale((BigDecimal) item.get("totalNum"), 4);
                BigDecimal price = BigDecimalUtils.scale((BigDecimal) item.get("price"), 4);
                feedetail.put("cnt", cnt); // 数量
                feedetail.put("pric", price); // 单价
                DecimalFormat df1 = new DecimalFormat("0.00");
                String realityPrice = df1.format(BigDecimalUtils.convert(item.get("realityPrice").toString()));
                BigDecimal convertPrice = BigDecimalUtils.convert(realityPrice);
                feedetail.put("det_item_fee_sumamt", convertPrice); // 明细项目费用总额
                feedetail.put("med_list_codg", item.get("insureItemCode") == null ? "" : item.get("insureItemCode").toString()); // 医疗目录编码
                feedetail.put("medins_list_codg", item.get("hospItemCode") == null ? "" : item.get("hospItemCode").toString()); // 医药机构目录编码
                feedetail.put("medins_list_name", MapUtils.get(item, "insureItemName")); // 医药机构目录名称
                feedetail.put("med_chrgitm_type", MapUtils.get(item, "insureItemType")); // 医疗收费项目类别
                feedetail.put("prodname", MapUtils.get(item, "insureItemName")); // 商品名
                feedetail.put("bilg_dept_codg", MapUtils.get(item, "deptId")); // 开单科室编码
                feedetail.put("bilg_dept_name", MapUtils.get(item, "deptName")); // 开单科室名称
                if (StringUtils.isEmpty(MapUtils.get(item, "deptId"))) {
                    feedetail.put("bilg_dr_codg", doctorId); // 开单医生编码
                } else {
                    feedetail.put("bilg_dr_codg", MapUtils.get(item, "deptId")); // 开单医生编码
                }
                if (StringUtils.isEmpty(MapUtils.get(item, "doctorName"))) {
                    feedetail.put("bilg_dr_codg", doctorName); // 开单医生编码
                } else {
                    feedetail.put("bilg_dr_name", MapUtils.get(item, "doctorName")); // 开单医生姓名
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
            param.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());// 就医地医保区划
            for (Map map : listMap) {
                param.put("input", map);  //交易输入
                String json = JSONObject.toJSONString(param);
                logger.info("自费病人费用明细传输入参:" + json);
                String url = insureConfigurationDTO.getUrl();
                String resultJson = HttpConnectUtil.unifiedPayPostUtil(url, json);
                logger.info("自费病人费用明细传输回参:" + resultJson);
                Map<String, Object> resultMap = JSONObject.parseObject(resultJson, Map.class);
                if (StringUtils.isEmpty(resultJson)) {
                    throw new AppException("访问统一支付平台失败");
                }
                if ("999".equals(resultMap.get("infcode").toString())) {
                    throw new AppException((String) resultMap.get("msg"));
                }
                if (!"0".equals(resultMap.get("infcode").toString())) {
                    throw new AppException((String) resultMap.get("err_msg"));
                }

                insertHandlerInsureCost(map, insureSettleInfoDTO);

            }

        }
        return true;
    }

    /**
     * @Method insertHandlerInsureCost
     * @Desrciption 处理自费病人明细数据
     * @Param item ：上传参数
     * infoDTO
     * @Author fuhui
     * @Date 2021/8/21 13:57
     * @Return
     **/
    private void insertHandlerInsureCost(Map item, InsureSettleInfoDTO infoDTO) {
        List<InsureIndividualCostDO> insureIndividualCostDOList = new ArrayList<>();
        InsureIndividualCostDO insureIndividualCostDO = new InsureIndividualCostDO();
        insureIndividualCostDO.setId(SnowflakeUtils.getId());//id
        insureIndividualCostDO.setHospCode(infoDTO.getHospCode());//医院编码
        insureIndividualCostDO.setVisitId(infoDTO.getVisitId());//患者id
        insureIndividualCostDO.setCostId(MapUtils.get(item, "feedetl_sn"));//费用id
        insureIndividualCostDO.setSettleId(null);//结算id
        insureIndividualCostDO.setIsHospital(Constants.SF.S);//是否住院 = 是
        insureIndividualCostDO.setItemType(MapUtils.get(item, "med_chrgitm_type"));//医保项目类别
        insureIndividualCostDO.setItemCode(MapUtils.get(item, "med_list_codg"));//医保项目编码
        insureIndividualCostDO.setItemName(MapUtils.get(item, "medins_list_name"));//医保项目名称
        insureIndividualCostDO.setGuestRatio(null);//自付比例
        insureIndividualCostDO.setPrimaryPrice(MapUtils.get(item, "det_item_fee_sumamt"));//原费用
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
     * @Desrciption 检查医保机构是否已选择
     * @Param insureSettleInfoDTO
     * @Author fuhui
     * @Date 2021/8/21 13:58
     * @Return
     **/
    private InsureConfigurationDTO checkInsureConfig(InsureSettleInfoDTO insureSettleInfoDTO) {
        Map<String, Object> isInsureUnifiedMap = new HashMap<>();
        isInsureUnifiedMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        isInsureUnifiedMap.put("code", "HOSP_INSURE_CODE");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
        if (sysParameterDTO == null) {
            throw new AppException("请先配置默认的医疗机构编码参数信息:编码为:HOSP_INSURE_CODE,值为对应的医疗机构编码值");
        }
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        insureConfigurationDTO.setOrgCode(sysParameterDTO.getValue());
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if (insureConfigurationDTO == null) {
            throw new AppException("医保机构配置信息为空");
        }
        return insureConfigurationDTO;
    }

    /**
     * @Method handlerOutptCostFee
     * @Desrciption 处理门诊患者费用
     * @Param
     * @Author fuhui
     * @Date 2021/8/21 10:54
     * @Return
     **/
    private List<Map<String, Object>> handlerOutptCostFee(InsureSettleInfoDTO insureSettleInfoDTO) {

        String insureRegCode = insureSettleInfoDTO.getOrgCode();
        //判断是否有传输费用信息
        Map<String, Object> insureCostParam = new HashMap<String, Object>();
        insureCostParam.put("hospCode", insureSettleInfoDTO.getHospCode());//医院编码
        insureCostParam.put("statusCode", Constants.ZTBZ.ZC);//状态标志 = 正常
        insureCostParam.put("visitId", insureSettleInfoDTO.getId());//就诊id
        insureCostParam.put("isMatch", Constants.SF.S);//是否匹配 = 是
        insureCostParam.put("transmitCode", Constants.SF.F);//传输标志 = 未传输
        insureCostParam.put("insureRegCode", insureRegCode);// 医保机构编码
        insureCostParam.put("isHospital", Constants.SF.F); // 区分门诊还是住院
        List<Map<String, Object>> insureCostList = insureIndividualCostDAO.queryOutptInsureCostByVisit(insureCostParam);
        if (ListUtils.isEmpty(insureCostList)) {
            throw new AppException("该病人没有可以上传的费用");
        }
        return insureCostList;
    }

    /**
     * @Method handlerInptCostFee
     * @Desrciption 处理住院病人医保费用数据
     * @Param insureSettleInfoDTO
     * @Author fuhui
     * @Date 2021/8/21 10:38
     * @Return
     **/
    private List<Map<String, Object>> handlerInptCostFee(InsureSettleInfoDTO insureSettleInfoDTO) {
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
     * @Desrciption 保存未上传的费用数据
     * @Param insureCostList：未上传的费用集合
     * inptVisitDTO ：患者基本信息
     * @Author fuhui
     * @Date 2021/8/13 9:16
     * @Return
     **/
    private void insertNotUpLoadFee(List<Map<String, Object>> insureCostList, InsureSettleInfoDTO insureSettleInfoDTO) {
        List<InsureIndividualCostDO> insureIndividualCostDOList = new ArrayList<InsureIndividualCostDO>();
        String hospCode = insureSettleInfoDTO.getHospCode();
        String visitId = insureSettleInfoDTO.getId();
        String crteId = insureSettleInfoDTO.getCrteId();
        String crteName = insureSettleInfoDTO.getCrteName();
        for (Map<String, Object> item : insureCostList) {
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
            insureIndividualCostDO.setGuestRatio((String) item.get("deductible"));//自付比例
            insureIndividualCostDO.setPrimaryPrice((BigDecimal) item.get("realityPrice"));//原费用
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
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-13 19:09
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryCost(InsureSettleInfoDTO insureSettleInfoDTO) {

        List<InptCostDTO> costDTOList = new ArrayList<>();
        if (insureSettleInfoDTO.getLx().equals("1")) {
            PageHelper.startPage(insureSettleInfoDTO.getPageNo(), insureSettleInfoDTO.getPageSize());
            costDTOList = insureGetInfoDAO.queryInCost(insureSettleInfoDTO);
        }
        if (insureSettleInfoDTO.getLx().equals("2")) {
            PageHelper.startPage(insureSettleInfoDTO.getPageNo(), insureSettleInfoDTO.getPageSize());
            costDTOList = insureGetInfoDAO.queryOutCost(insureSettleInfoDTO);
        }
        return PageDTO.of(costDTOList);
    }

    /**
     * @Method queryUploadCost
     * @Desrciption
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-13 19:09
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryUploadCost(InsureSettleInfoDTO insureSettleInfoDTO) {

        List<InsureUploadCostDTO> costDTOList = new ArrayList();

        PageHelper.startPage(insureSettleInfoDTO.getPageNo(), insureSettleInfoDTO.getPageSize());
        costDTOList = insureGetInfoDAO.queryAll(insureSettleInfoDTO);

        return PageDTO.of(costDTOList);
    }


    /**
     * @Method queryVisit
     * @Desrciption
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-13 19:10
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryVisit(InsureSettleInfoDTO insureSettleInfoDTO) {
        PageHelper.startPage(insureSettleInfoDTO.getPageNo(), insureSettleInfoDTO.getPageSize());
        List<InptVisitDTO> visitDTOList = insureGetInfoDAO.queryVisit(insureSettleInfoDTO);

        return PageDTO.of(visitDTOList);

    }

    /**
     * @Method queryInsureSettle
     * @Desrciption 为上策基金支付明细
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-13 19:10
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryInsureSettle(InsureSettleInfoDTO insureSettleInfoDTO) {
        PageHelper.startPage(insureSettleInfoDTO.getPageNo(), insureSettleInfoDTO.getPageSize());
        List<PayInfoDTO> payInfoDTO = insureGetInfoDAO.queryInsureSettle(insureSettleInfoDTO);

        return PageDTO.of(payInfoDTO);

    }

    /**
     * @Method queryInsure
     * @Desrciption 未上传费用明细
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-13 19:10
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public PageDTO queryInsure(InsureSettleInfoDTO insureSettleInfoDTO) {
        PageHelper.startPage(insureSettleInfoDTO.getPageNo(), insureSettleInfoDTO.getPageSize());
        List<ItemInfoDTO> itemInfoDTOList = insureGetInfoDAO.queryInsure(insureSettleInfoDTO);

        return PageDTO.of(itemInfoDTOList);

    }

    /**
     * @Method queryUnMatchPage
     * @Desrciption 查询自费病人的匹配费用明细
     * @Param
     * @Author fuhui
     * @Date 2021/8/21 15:37
     * @Return
     **/
    @Override
    public PageDTO queryPage(InsureSettleInfoDTO insureSettleInfoDTO) {
        Map<String, Object> isInsureUnifiedMap = new HashMap<>();
        isInsureUnifiedMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        isInsureUnifiedMap.put("code", "HOSP_INSURE_CODE");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
        if (sysParameterDTO == null) {
            throw new AppException("请先配置默认的医疗机构编码参数信息:编码为:HOSP_INSURE_CODE,值为对应的医疗机构编码值");
        }
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        insureConfigurationDTO.setOrgCode(sysParameterDTO.getValue());
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        insureSettleInfoDTO.setOrgCode(insureConfigurationDTO.getCode());

        List<InptCostDTO> costDTOList = null;
        if ("1".equals(insureSettleInfoDTO.getLx())) {
            PageHelper.startPage(insureSettleInfoDTO.getPageNo(), insureSettleInfoDTO.getPageSize());
            costDTOList = insureGetInfoDAO.queryInptMatchPage(insureSettleInfoDTO);
        }
        if ("0".equals(insureSettleInfoDTO.getLx())) {
            PageHelper.startPage(insureSettleInfoDTO.getPageNo(), insureSettleInfoDTO.getPageSize());
            costDTOList = insureGetInfoDAO.queryOutMatchPage(insureSettleInfoDTO);
        }
        return PageDTO.of(costDTOList);
    }


    /**
     * @Method queryUnMatchPage
     * @Desrciption 查询自费病人的未匹配费用明细
     * @Param
     * @Author fuhui
     * @Date 2021/8/21 15:37
     * @Return
     **/
    @Override
    public PageDTO queryUnMatchPage(InsureSettleInfoDTO insureSettleInfoDTO) {
        List<InptCostDTO> costDTOList = null;
        if ("1".equals(insureSettleInfoDTO.getLx())) {
            PageHelper.startPage(insureSettleInfoDTO.getPageNo(), insureSettleInfoDTO.getPageSize());
            costDTOList = insureGetInfoDAO.queryInptUnMatchPage(insureSettleInfoDTO);
        }
        if ("0".equals(insureSettleInfoDTO.getLx())) {
            PageHelper.startPage(insureSettleInfoDTO.getPageNo(), insureSettleInfoDTO.getPageSize());
            costDTOList = insureGetInfoDAO.queryOutUnMatchPage(insureSettleInfoDTO);
        }
        return PageDTO.of(costDTOList);
    }
    /**
     * @Method selectItemInfo
     * @Desrciption  医疗保障基金结算清单信息 : 查询收费项目信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/3 15:13
     * @Return
     **/
    public List<Map<String,Object>> selectItemInfo(Map<String,Object> map) {
        List<Map<String, Object>> list = insureGetInfoDAO.qureryItemInfo(map);
        if(ListUtils.isEmpty(list)){
            throw new AppException("医疗保障基金结算清单上传时,收费项目信息节点入参为空");
        }
        return list;
    }
    /**
     * @Method getEmptyErr
     * @Desrciption
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/17 14:34
     * @Return
     **/
    public <T> T getEmptyErr(Map map, Object key, String errMsg) {
        if (!map.containsKey(key)) {
            throw new RuntimeException(errMsg);
        }
        Object obj = MapUtils.get(map, key);
        if(obj ==null){
            throw new RuntimeException(errMsg);
        }
        if(obj instanceof  String){
            if(StringUtils.isEmpty((String) obj)){
                throw new RuntimeException(errMsg);
            }
        }
        return (T) map.get(key);
    }

}
