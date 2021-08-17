package cn.hsa.insure.unifiedpay.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.base.dept.service.BaseDeptService;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.fees.dto.InptSettleDTO;
import cn.hsa.module.insure.inpt.bo.InsureUnifiedBaseBO;
import cn.hsa.module.insure.inpt.service.InsureUnifiedBaseService;
import cn.hsa.module.insure.inpt.service.InsureUnifiedPayInptService;
import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualCostDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualSettleDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualVisitDAO;
import cn.hsa.module.insure.module.dto.*;
import cn.hsa.module.insure.module.service.InsureIndividualBasicService;
import cn.hsa.module.insure.outpt.service.InsureUnifiedPayRestService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.server.upgrade.UpgradeSnapShotV1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import scala.App;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.bo.impl
 * @class_name: InusreUnifiedBaseBOImpl
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/4/24 14:52
 * @Company: 创智和宇
 **/
@Slf4j
@Component
public class InsureUnifiedBaseBOImpl extends HsafBO implements InsureUnifiedBaseBO {

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    @Resource
    private InsureIndividualVisitDAO insureIndividualVisitDAO;

    @Resource
    private InsureIndividualCostDAO insureIndividualCostDAO;

    @Resource
    private InsureIndividualSettleDAO insureIndividualSettleDAO;

    @Resource
    private InsureIndividualBasicService insureIndividualBasicService_consumer;

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Resource
    private BaseDeptService baseDeptService_consumer;

    @Resource
    private InsureUnifiedPayInptService insureUnifiedPayInptService;

    @Resource
    private InsureUnifiedPayRestService insureUnifiedPayRestService;
    @Resource
    private InsureIndividualBasicService insureIndividualBasicService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @Method queryUnifiedDept
     * @Desrciption 科室信息查询
     * @Param
     * @Author fuhui
     * @Date 2021/4/23 12:47
     * @Return
     **/
    public Map<String, Object> queryUnifiedDept(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String regCode = MapUtils.get(map, "regCode");
        Map<String, Object> dataMap = new HashMap<>();
        Map<String, Object> resultMap = commonInsureUnified(hospCode, regCode, Constant.UnifiedPay.REGISTER.UP_5101, dataMap);
        Map<String, Object> outptMap = MapUtils.get(resultMap, "output");
        List<Map<String, Object>> resultDataMap = MapUtils.get(outptMap, "feedetail");
        map.put("resultDataMap", resultDataMap);
        return map;
    }


    /**
     * @Method queryDoctorInfo
     * @Desrciption 医执人员信息查询
     * @Param
     * @Author fuhui
     * @Date 2021/4/23 12:47
     * @Return
     **/
    public Map<String, Object> queryDoctorInfo(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String orgCode = MapUtils.get(map, "orgCode");
        Map<String, Object> dataMap = new HashMap<>();
        Map<String, Object> paramMap = new HashMap<>();

        dataMap.put("prac_psn_type", MapUtils.get(map, "pracPsnType"));
        dataMap.put("psn_cert_type", MapUtils.get(map, "psnCertType"));
        dataMap.put("certno", MapUtils.get(map, "certno"));
        dataMap.put("prac_psn_name", MapUtils.get(map, "pracPsnName"));
        dataMap.put("prac_psn_code", MapUtils.get(map, "pracPsnCode"));

        paramMap.put("data", dataMap);
        Map<String, Object> resultMap = commonInsureUnified(hospCode, orgCode, Constant.UnifiedPay.REGISTER.UP_5102, paramMap);
        Map<String, Object> outptMap = MapUtils.get(resultMap, "output");
        List<Map<String, Object>> resultDataMap = MapUtils.get(outptMap, "feedetail");
        map.put("resultDataMap", resultDataMap);
        return map;
    }


    /**
     * @Method commonGetVisitInfo
     * @Desrciption 医保统一支付：住院结算，统一患者就诊信息查询接口
     * @Param map
     * @Author fuhui
     * @Date 2021/2/14 10:57
     * @Return InsureIndividualVisitDTO
     **/
    public InsureIndividualVisitDTO commonGetVisitInfo(Map<String, Object> map) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        String hospCode = (String) map.get("hospCode");//医院编码
        String visitId = (String) map.get("visitId");//就诊id
        String medicalRegNo = MapUtils.get(map, "medicalRegNo");
        Map<String, Object> insureVisitParam = new HashMap<String, Object>();
        insureVisitParam.put("id", visitId);
        insureVisitParam.put("medicalRegNo", medicalRegNo);
        insureVisitParam.put("hospCode", hospCode);
        insureIndividualVisitDTO = insureIndividualVisitDAO.getInsureIndividualVisitById(insureVisitParam);
        if (insureIndividualVisitDTO == null || StringUtils.isEmpty(insureIndividualVisitDTO.getId())) {
            throw new AppException("未查找到医保就诊信息，请做医保登记。");
        }
        return insureIndividualVisitDTO;
    }

    /**
     * @Method queryDoctorInfo
     * @Desrciption 就诊信息查询
     * @Param
     * @Author fuhui
     * @Date 2021/4/23 12:47
     * @Return
     **/
    public Map<String, Object> queryVisitInfo(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");

        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(map);
        Map<String, Object> paramMap = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("psn_no", insureIndividualVisitDTO.getAac001());
        dataMap.put("begntime", MapUtils.get(map, "begntime"));
        dataMap.put("endtime", MapUtils.get(map, "endtime"));
        dataMap.put("med_type", insureIndividualVisitDTO.getAka130());
        dataMap.put("mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo());
        paramMap.put("data", dataMap);
        Map<String, Object> resultMap = commonInsureUnified(hospCode, insureIndividualVisitDTO.getMedicineOrgCode(), Constant.UnifiedPay.REGISTER.UP_5201, paramMap);
        Map<String, Object> outptMap = MapUtils.get(resultMap, "output");
        List<Map<String, Object>> resultDataMap = MapUtils.get(outptMap, "mdtrtinfo");
        map.put("resultDataMap", resultDataMap);
        return map;
    }


    /**
     * @Method queryDiagnoseInfo
     * @Desrciption 诊断信息查询
     * @Param
     * @Author fuhui
     * @Date 2021/4/23 12:47
     * @Return
     **/
    public Map<String, Object> queryDiagnoseInfo(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(map);
        Map<String, Object> dataMap = new HashMap<>();
        Map<String, Object> paramMap = new HashMap<>();
        dataMap.put("psn_no", insureIndividualVisitDTO.getAac001());
        dataMap.put("mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo());

        paramMap.put("data", dataMap);
        Map<String, Object> resultMap = commonInsureUnified(hospCode, insureIndividualVisitDTO.getMedicineOrgCode(), Constant.UnifiedPay.REGISTER.UP_5202, paramMap);
        Map<String, Object> outptMap = MapUtils.get(resultMap, "output");
        List<Map<String, Object>> resultDataMap = MapUtils.get(outptMap, "diseinfo");
        map.put("resultDataMap", resultDataMap);
        return map;
    }

    /**
     * @Method updateFormalData
     * @Desrciption 门诊选点改点
     * @Param
     * @Author caoliang
     * @Date 2021/6/29 10:40
     * @Return
     **/
    public Map<String, Object> updateFormalData(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(map);
        Map<String, Object> dataMap = new HashMap<>();
        Map<String, Object> paramMap = new HashMap<>();
        Map<String, Object> psnFixedEvtDetlMap = new HashMap<>();
        dataMap.put("psn_no", insureIndividualVisitDTO.getAac001());
        dataMap.put("chg_rea", MapUtils.get(map, "chgRea"));
        dataMap.put("begndate", MapUtils.get(map, "begndate"));
        dataMap.put("enddate", MapUtils.get(map, "enddate"));
        psnFixedEvtDetlMap.put("begndate", MapUtils.get(map, "begndate"));
        psnFixedEvtDetlMap.put("enddate", MapUtils.get(map, "enddate"));
        psnFixedEvtDetlMap.put("fixmedins_code", MapUtils.get(map, "fixmedinsCode"));
        psnFixedEvtDetlMap.put("fixmedins_type", MapUtils.get(map, "fixmedinsType"));
        psnFixedEvtDetlMap.put("fixmedins_name", MapUtils.get(map, "fixmedinsName"));
        psnFixedEvtDetlMap.put("medins_lv", MapUtils.get(map, "medinsLv"));
        List<Map<String, Object>> psnFixedEvtDetlList = new ArrayList<Map<String, Object>>();
        ;
        psnFixedEvtDetlList.add(psnFixedEvtDetlMap);
        dataMap.put("psn_fixed_evt_detl_list", psnFixedEvtDetlList);
        paramMap.put("data", dataMap);
        Map<String, Object> resultMap = commonInsureUnified(hospCode, insureIndividualVisitDTO.getMedicineOrgCode(), Constant.UnifiedPay.REGISTER.UP_2579, paramMap);
        Map<String, Object> outptMap = MapUtils.get(resultMap, "output");
        List<Map<String, Object>> resultDataMap = MapUtils.get(outptMap, "diseinfo");
        map.put("resultDataMap", resultDataMap);
        return map;
    }

    public static void main(String[] args) {

    }

    /**
     * @param
     * @Method querySettleInfo
     * @Desrciption 结算信息查询
     * 1.判断是异地还是非异地
     * 2.如果是非异地住院的，直接调用5204接口获取费用明细
     * 3.如果是异地住院的 调用2301接口获取费用明细信息
     * 调用2303获取对应的试算信息
     * @Param
     * @Author fuhui
     * @Date 2021/4/23 12:47
     * @Return
     */

    public static int daysBetween(Date date1, Date date2) {
        return DateUtils.differentDays(date1, date2);
    }

    public Map<String, Object> querySettleInfo(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String insureSettleId = MapUtils.get(map, "insureSettleId");

        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(map);
        if ("null".equals(insureIndividualVisitDTO.getBka006Name())) {
            insureIndividualVisitDTO.setBka006Name("");
        }

        /**
         * 如果是中途结算 1.则出院时间取结算表里面的结束日期
         *              2.天数需要根据医保结算表的结束时间和开始时间计算
         */
        InptSettleDTO inptSettleDTO = insureIndividualSettleDAO.queryInptSettle(map);
        if(inptSettleDTO == null){
            throw new AppException("根据就诊Id,结算id查询患者结算信息为空");
        }
        Date startTime = inptSettleDTO.getStartTime();
        Date endTime = inptSettleDTO.getEndTime();
        int daysBetween = daysBetween(startTime, endTime);
        if ("1".equals(insureIndividualVisitDTO.getIsHalfSettle())) {
            daysBetween = daysBetween +1;
            insureIndividualVisitDTO.setHospitalDay(daysBetween); // 住院天数
        }else{
            insureIndividualVisitDTO.setHospitalDay(daysBetween); // 住院天数
        }
        insureIndividualVisitDTO.setSettleTime(inptSettleDTO.getSettleTime()); // 结算时间
        insureIndividualVisitDTO.setInTime(inptSettleDTO.getStartTime()); // 入院时间
        insureIndividualVisitDTO.setOutTime(inptSettleDTO.getEndTime());// 出院时间
        String medicineOrgCode = insureIndividualVisitDTO.getMedicineOrgCode();
        InsureConfigurationDTO configurationDTO = new InsureConfigurationDTO();
        configurationDTO.setHospCode(hospCode);  // 医院编码
        configurationDTO.setOrgCode(medicineOrgCode); // 医疗机构编码
        configurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(configurationDTO);
        if (configurationDTO == null) {
            throw new AppException("获取医保配置信息为空");
        }
        Map<String, Object> dataMap = new HashMap<>();
        Map<String, Object> paramMap = new HashMap<>();
        dataMap.put("setl_id", insureSettleId);
        dataMap.put("psn_no", insureIndividualVisitDTO.getAac001());
        dataMap.put("mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo());
        paramMap.put("data", dataMap);
        map.put("code", "UNIFIED_PAY");
        SysParameterDTO data = sysParameterService_consumer.getParameterByCode(map).getData();
        Map<String, Object> outptMap = new HashMap<>();
        InsureIndividualSettleDTO individualSettleDTO = new InsureIndividualSettleDTO();
        individualSettleDTO.setHospCode(hospCode);
        individualSettleDTO.setVisitId(MapUtils.get(map, "visitId"));
        individualSettleDTO.setInsureSettleId(insureSettleId);
        individualSettleDTO.setState(Constants.SF.F);
        individualSettleDTO.setSettleState(Constants.SF.S);
        individualSettleDTO = insureIndividualSettleDAO.selectInsureSettInfo(individualSettleDTO);
        /**
         * 省外医保  接口无数据返回。只能查询本地保存的费用数据
         */
        if (("03".equals(insureIndividualVisitDTO.getMdtrtCertType()) || "06".equals(insureIndividualVisitDTO.getMdtrtCertType())) && data != null && "1".equals(data.getValue())) {
            Map<String, Object> setlinfoMap = insureIndividualSettleDAO.querySettleForMap(map);
            if(MapUtils.isEmpty(setlinfoMap)){
                throw  new AppException("根据就诊id,结算id查询医保信息为空");
            }
            List<Map<String, Object>> setldetailListMap = insureIndividualSettleDAO.queryInsureFundListMap(map);
            if(ListUtils.isEmpty(setldetailListMap)){
                throw  new AppException("根据就诊id,查询基金信息为空");
            }
            outptMap.put("setlinfo", setlinfoMap);
            outptMap.put("setldetail", setldetailListMap);
            map.put("insureServiceType", "1"); //默认根据定点医疗机构去查找  1：定点医疗机构 2：定点零售药店
            map.put("medinsCode", configurationDTO.getOrgCode());
            map.put("orgCode", configurationDTO.getOrgCode());
            Map<String, Object> medisnInfMap = insureUnifiedPayRestService.getMedisnInfo(map).getData();
            setlinfoMap.put("fixmedins_name", MapUtils.get(medisnInfMap, "fixmedins_name"));
            setlinfoMap.put("fixmedins_code", MapUtils.get(medisnInfMap, "fixmedins_code"));
            setlinfoMap.put("hosp_lv", MapUtils.get(medisnInfMap, "hosp_lv"));
            InsureIndividualBasicDTO insureIndividualBasicDTO = new InsureIndividualBasicDTO();
            insureIndividualBasicDTO.setHospCode(hospCode);
            insureIndividualBasicDTO.setVisitId(insureIndividualVisitDTO.getVisitId());
            insureIndividualBasicDTO.setMedicalRegNo(insureIndividualVisitDTO.getMedicalRegNo());
            map.put("insureIndividualBasicDTO", insureIndividualBasicDTO);
            insureIndividualBasicDTO = insureIndividualBasicService.getByVisitId(map).getData();
            if(insureIndividualBasicDTO == null){
                throw new AppException("根据就诊id,个人信息id查询医保基本信息为空");
            }
            setlinfoMap.put("emp_name", insureIndividualBasicDTO.getBka008());
            setlinfoMap.put("certno", insureIndividualBasicDTO.getAac002());
            setlinfoMap.put("brdy", DateUtils.format(MapUtils.get(setlinfoMap, "birthday"), DateUtils.Y_M_D));
            setlinfoMap.put("insutype", insureIndividualBasicDTO.getAae140());
            setlinfoMap.put("mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo());
            setlinfoMap.put("setl_time", DateUtils.format(individualSettleDTO.getCrteTime(), DateUtils.Y_M_DH_M_S));
            setlinfoMap.put("gend", insureIndividualBasicDTO.getAac004()); // act_pay_dedc
            if (null == MapUtils.get(setlinfoMap, "insure_price")) {
                setlinfoMap.put("hifp_pay", 0.00); // 起付线
            } else {
                setlinfoMap.put("hifp_pay", MapUtils.get(setlinfoMap, "insure_price")); // 起付线
            }

            map.put("outptMap", outptMap);
        }
        else {
            Map<String, Object> resultMap = commonInsureUnified(hospCode, insureIndividualVisitDTO.getMedicineOrgCode(), Constant.UnifiedPay.REGISTER.UP_5203, paramMap);
            outptMap = MapUtils.get(resultMap, "output");
            map.put("outptMap", outptMap);
        }
        List<Map<String, Object>> feeDetailMapList = new ArrayList<>();
        if ("1".equals(insureIndividualVisitDTO.getIsHospital())) {
            feeDetailMapList = insureIndividualCostDAO.selectIsSetlleFee(map);
        } else {
            Map<String, Object> stringObjectMap = queryFeeDetailInfo(map);
            feeDetailMapList = MapUtils.get(stringObjectMap, "outptMap");
        }
        List<Map<String, Object>> groupListMap = new ArrayList<>();
        if (!ListUtils.isEmpty(feeDetailMapList)) {
            Map<String, List<Map<String, Object>>> groupMap = feeDetailMapList.stream().collect(Collectors.groupingBy(item -> MapUtils.get(item, "med_chrgitm_type")));
            Map<String, Object> pMap = null;
            for (String key : groupMap.keySet()) {
                BigDecimal sumDetItemFeeSumamt = new BigDecimal(0.00);
                BigDecimal fulamtOwnpayAmt = new BigDecimal(0.00);
                BigDecimal preselfpayAmt = new BigDecimal(0.00);
                System.out.println(key + "=====" + groupMap.get(key));
                Iterator<Map<String, Object>> iterator = groupMap.get(key).iterator();
                if (iterator.hasNext()) {
                    pMap = new HashMap<>();
                    List<Map<String, Object>> listMap = groupMap.get(key);
                    for (Map<String, Object> item : listMap) {
                        DecimalFormat df1 = new DecimalFormat("0.00");
                        sumDetItemFeeSumamt = BigDecimalUtils.add(sumDetItemFeeSumamt, BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "det_item_fee_sumamt").toString()))));
                        fulamtOwnpayAmt = BigDecimalUtils.add(fulamtOwnpayAmt, BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "fulamt_ownpay_amt").toString()))));
                        preselfpayAmt = BigDecimalUtils.add(preselfpayAmt, BigDecimalUtils.convert(df1.format(BigDecimalUtils.convert(MapUtils.get(item, "preselfpay_amt").toString()))));
                    }
                    pMap.put("sumDetItemFeeSumamt", sumDetItemFeeSumamt);
                    pMap.put("fulamtOwnpayAmt", fulamtOwnpayAmt);
                    pMap.put("preselfpayAmt", preselfpayAmt);
                    pMap.put("medChrgitmType", key);
                    groupListMap.add(pMap);
                }

            }
        }
        map.put("groupListMap", groupListMap);
        map.put("individualSettleDTO", individualSettleDTO);
        map.put("insureIndividualVisitDTO", insureIndividualVisitDTO);
        return map;
    }


    /**
     * @Method queryFeeDetailInfo
     * @Desrciption 费用明细查询
     * @Param
     * @Author fuhui
     * @Date 2021/4/23 12:47
     * @Return
     **/
    public Map<String, Object> queryFeeDetailInfo(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String insureSettleId = MapUtils.get(map, "insureSettleId");
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(map);
        Map<String, Object> dataMap = new HashMap<>();
        Map<String, Object> paramMap = new HashMap<>();
        dataMap.put("setl_id", insureSettleId);
        dataMap.put("psn_no", insureIndividualVisitDTO.getAac001());
        dataMap.put("mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo());
        paramMap.put("data", dataMap);
        Map<String, Object> resultMap = commonInsureUnified(hospCode, insureIndividualVisitDTO.getMedicineOrgCode(), Constant.UnifiedPay.REGISTER.UP_5204, paramMap);
        List<Map<String, Object>> outptMap = MapUtils.get(resultMap, "output");
        Map<String, Object> resultDataMap = new HashMap<>();
        resultDataMap.put("outptMap", outptMap);
        return resultDataMap;
    }

    /**
     * @Method querySettleDeInfo
     * @Desrciption 结算信息查询
     * @Param
     * @Author caoliang
     * @Date 2021/7/19 19:00
     * @Return
     **/
    public Map<String, Object> querySettleDeInfo(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String insureSettleId = MapUtils.get(map, "insureSettleId");
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(map);
        Map<String, Object> dataMap = new HashMap<>();
        Map<String, Object> paramMap = new HashMap<>();
        dataMap.put("setl_id", insureSettleId);
        dataMap.put("psn_no", insureIndividualVisitDTO.getAac001());
        dataMap.put("mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo());
        paramMap.put("data", dataMap);
        Map<String, Object> resultMap = commonInsureUnified(hospCode, insureIndividualVisitDTO.getMedicineOrgCode(), Constant.UnifiedPay.REGISTER.UP_5203, paramMap);
        List<Map<String, Object>> setldetail = MapUtils.get(MapUtils.get(resultMap, "output"), "setldetail");
        Map<String, Object> setlinfo = MapUtils.get(MapUtils.get(resultMap, "output"), "setlinfo");
        Map<String, Object> resultDataMap = new HashMap<>();
        resultDataMap.put("setldetail", setldetail);
        resultDataMap.put("setlinfo", setlinfo);
        return resultDataMap;
    }


    /**
     * @Method querySpecialUserDrug
     * @Desrciption 人员慢特病用药记录查询
     * @Param
     * @Author fuhui
     * @Date 2021/4/23 12:47
     * @Return
     **/
    public Map<String, Object> querySpecialUserDrug(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(map);
        Map<String, Object> inputMap = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();
        Map<String, Object> paramMap = new HashMap<>();
        dataMap.put("psn_no", insureIndividualVisitDTO.getAac001());
        dataMap.put("begntime", MapUtils.get(map, "begntime"));
        dataMap.put("endtime", MapUtils.get(map, "endtime"));
        paramMap.put("data", dataMap);
        Map<String, Object> resultMap = commonInsureUnified(hospCode, insureIndividualVisitDTO.getMedicineOrgCode(), Constant.UnifiedPay.REGISTER.UP_5205, paramMap);
        Map<String, Object> outptMap = MapUtils.get(resultMap, "output");
        List<Map<String, Object>> resultDataMap = MapUtils.get(outptMap, "feedetail");
        map.put("resultDataMap", resultDataMap);
        return map;
    }


    /**
     * @Method queryPatientSumInfo
     * @Desrciption 人员累计信息查询
     * @Param
     * @Author fuhui
     * @Date 2021/4/23 12:47
     * @Return
     **/
    public Map<String, Object> queryPatientSumInfo(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(map);
        /**
         * 获取访问的url地址
         */
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(insureIndividualVisitDTO.getMedicineOrgCode());
        insureConfigurationDTO = getInsureConfiguration(insureConfigurationDTO);

        Map<String, Object> inputMap = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();
        Map<String, Object> paramMap = new HashMap<>();

        /**
         * 公共入参
         */
        inputMap.put("infno", Constant.UnifiedPay.REGISTER.UP_5206);  // 交易编号
        inputMap.put("insuplc_admdvs", insureConfigurationDTO.getRegCode());  //TODO 参保地医保区划
        inputMap.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        inputMap.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        inputMap.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        inputMap.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());// 就医地医保区划


        dataMap.put("psn_no", insureIndividualVisitDTO.getAac001());
        dataMap.put("cum_ym", MapUtils.get(map, "cumYm"));

        paramMap.put("data", dataMap);
        inputMap.put("input", paramMap);

        String json = JSONObject.toJSONString(inputMap);
        logger.info("人员累计信息查询入参:" + json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
        if (StringUtils.isEmpty(resultJson)) {
            throw new AppException("无法访问医保统一支付平台");
        }
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson, Map.class);
        logger.info("人员累计信息查询回参:" + resultJson);
        Map<String, Object> outptMap = MapUtils.get(resultMap, "output");
        List<Map<String, Object>> resultDataMap = MapUtils.get(outptMap, "cuminfo");
        map.put("resultDataMap", resultDataMap);
        return map;
    }

    /**
     * @Method queryItemConfirm
     * @Desrciption 项目互认信息查询
     * @Param
     * @Author fuhui
     * @Date 2021/4/23 12:47
     * @Return
     **/
    public Map<String, Object> queryItemConfirm(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(map);
        /**
         * 获取访问的url地址
         */
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(insureIndividualVisitDTO.getMedicineOrgCode());
        insureConfigurationDTO = getInsureConfiguration(insureConfigurationDTO);
        Map<String, Object> inputMap = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();
        Map<String, Object> paramMap = new HashMap<>();

        /**
         * 公共入参
         */
        inputMap.put("infno", Constant.UnifiedPay.REGISTER.UP_5401);  // 交易编号
        inputMap.put("insuplc_admdvs", insureConfigurationDTO.getRegCode());  //TODO 参保地医保区划
        inputMap.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        inputMap.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        inputMap.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        inputMap.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());// 就医地医保区划

        dataMap.put("psn_no", MapUtils.get(map, "psnNo"));
        dataMap.put("exam_org_code", MapUtils.get(map, "examOrgCode"));
        dataMap.put("exam_org_name", MapUtils.get(map, "examOrgName"));
        dataMap.put("exam_item_code", MapUtils.get(map, "examItemCode"));
        dataMap.put("exam_item_name", MapUtils.get(map, "examItemName"));
        paramMap.put("data", dataMap);
        inputMap.put("input", paramMap);
        String json = JSONObject.toJSONString(inputMap);
        logger.info("项目互认信息查询入参:" + json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson, Map.class);
        logger.info("项目互认信息查询回参:" + resultJson);
        Map<String, Object> outptMap = MapUtils.get(resultMap, "output");
        List<Map<String, Object>> resultDataMap = MapUtils.get(outptMap, "bilgiteminfo");
        map.put("resultDataMap", resultDataMap);
        return map;
    }

    /**
     * @param insureIndividualVisitDTO
     * @Method queryPage
     * @Desrciption 分页查询医保就诊信息
     * @Param
     * @Author fuhui
     * @Date 2021/4/26 9:20
     * @Return
     */
    @Override
    public PageDTO queryPage(InsureIndividualVisitDTO insureIndividualVisitDTO) {
        PageHelper.startPage(insureIndividualVisitDTO.getPageNo(), insureIndividualVisitDTO.getPageSize());
        List<InsureIndividualVisitDTO> list = insureIndividualVisitDAO.queryPage(insureIndividualVisitDTO);
        return PageDTO.of(list);
    }

    /**
     * @param map
     * @Method queryPatientInfo
     * @Desrciption 在院信息查询
     * @Param
     * @Author fuhui
     * @Date 2021/4/23 12:47
     * @Return
     */
    @Override
    public Map<String, Object> queryPatientInfo(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        Map<String, Object> dataMap = new HashMap<>();
        String orgCode = MapUtils.get(map, "orgCode");
        Map<String, Object> paramMap = new HashMap<>();
        dataMap.put("psn_no", MapUtils.get(map, "psnNo"));
        dataMap.put("begntime", MapUtils.get(map, "startDate"));
        dataMap.put("endtime", MapUtils.get(map, "endDate"));
        paramMap.put("data", dataMap);
        Map<String, Object> resultMap = commonInsureUnified(hospCode, orgCode, Constant.UnifiedPay.REGISTER.UP_5303, paramMap);
        Map<String, Object> outptMap = MapUtils.get(resultMap, "output");
        List<Map<String, Object>> resultDataMap = MapUtils.get(outptMap, "data");
        map.put("resultDataMap", resultDataMap);
        return map;
    }

    /**
     * @Description: 人员定点信息查询
     * @Param: [map]
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     * @Author: zhangxuan
     * @Date: 2021-04-28
     */
    @Override
    public Map<String, Object> queryPersonFixInfo(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String orgCode = MapUtils.get(map, "insureRegCode");
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("psn_no", MapUtils.get(map, "psnNo"));
        dataMap.put("biz_appy_type", MapUtils.get(map, "bizAppyType"));
        Map<String, Object> resultMap = commonInsureUnified(hospCode, orgCode, Constant.UnifiedPay.REGISTER.UP_5302, dataMap);
        Map<String, Object> outptMap = new HashMap<>(1);
        outptMap = MapUtils.get(resultMap, "output");
        List<Map<String, Object>> psnfixmedinMap = MapUtils.get(outptMap, "psnfixmedin");
        map.put("psnfixmedinMap", psnfixmedinMap);
        return map;
    }

    /**
     * @Description: 报告明细信息查询
     * @Param: [map]
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     * @Author: zhangxuan
     * @Date: 2021-04-28
     */
    @Override
    public Map<String, Object> queryReportDetails(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        InsureIndividualVisitDTO insureIndividualVisitDTO = commonGetVisitInfo(map);
        /**
         * 获取访问的url地址
         */
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(insureIndividualVisitDTO.getMedicineOrgCode());
        insureConfigurationDTO = getInsureConfiguration(insureConfigurationDTO);

        Map<String, Object> inputMap = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();
        Map<String, Object> paramMap = new HashMap<>();

        /**
         * 公共入参
         */
        inputMap.put("infno", Constant.UnifiedPay.REGISTER.UP_5402);  // 交易编号
        inputMap.put("insuplc_admdvs", insureConfigurationDTO.getRegCode());  //TODO 参保地医保区划
        inputMap.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        inputMap.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        inputMap.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        inputMap.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());// 就医地医保区划

        dataMap.put("psn_no", MapUtils.get(map, "psnNo"));
        dataMap.put("rpotc_no", MapUtils.get(map, "rpotcNo"));
        dataMap.put("fixmedins_code", MapUtils.get(map, "fixmedinsCode"));

        paramMap.put("data", dataMap);
        inputMap.put("input", paramMap);

        String json = JSONObject.toJSONString(inputMap);
        logger.info("报告明细信息查询入参:" + json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson, Map.class);
        logger.info("报告明细信息查询回参:" + resultJson);
        Map<String, Object> outptMap = MapUtils.get(resultMap, "output");
        map.put("resultDataMap", outptMap);
        return map;
    }


    /**
     * @param map
     * @Method queryTransfInfo
     * @Desrciption 转院信息查询
     * @Param
     * @Author fuhui
     * @Date 2021/4/23 12:47
     * @Return
     */
    @Override
    public Map<String, Object> queryTransfInfo(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String orgCode = MapUtils.get(map, "orgCode");
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("psn_no", MapUtils.get(map, "psnNo"));
        if (!StringUtils.isEmpty(MapUtils.get(map, "begntime"))) {
            dataMap.put("begntime", DateUtils.parse(MapUtils.get(map, "begntime"), DateUtils.Y_M_DH_M_S));
        } else {
            throw new AppException("转院信息查询开始时间不能为空");
        }
        if (!StringUtils.isEmpty(MapUtils.get(map, "endtime"))) {
            dataMap.put("endtime", DateUtils.parse(MapUtils.get(map, "endtime"), DateUtils.Y_M_DH_M_S));
        } else {
            dataMap.put("endtime", null);
        }
        Map<String, Object> resultMap = commonInsureUnified(hospCode, orgCode, Constant.UnifiedPay.REGISTER.UP_5304, dataMap);
        Map<String, Object> outptMap = MapUtils.get(resultMap, "output");
        List<Map<String, Object>> psnfixmedinMap = MapUtils.get(outptMap, "refmedin");
        map.put("psnfixmedinMap", psnfixmedinMap);
        return map;
    }

    /**
     * @param map
     * @Method queryFixRecordInfo
     * @Desrciption 人员定点备案信息查询
     * @Param
     * @Author fuhui
     * @Date 2021/4/23 12:47
     * @Return
     */
    @Override
    public Map<String, Object> queryFixRecordInfo(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        map.put("code", "HOSP_INSURE_CODE");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(map).getData();
        String orgCode = "";
        if (sysParameterDTO != null) {
            orgCode = sysParameterDTO.getValue(); // 获取医疗机构编码
        }
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(orgCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        map.put("regCode", insureConfigurationDTO.getRegCode());
        Map<String, Object> data = insureIndividualBasicService_consumer.queryInsureInfo(map).getData();
        String psnNo = MapUtils.get(data, "psnNo");
        String bizAppyType = MapUtils.get(map, "bizAppyType");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("psn_no", psnNo);
        paramMap.put("biz_appy_type", bizAppyType);
        Map<String, Object> resultMap = commonInsureUnified(hospCode, orgCode, Constant.UnifiedPay.REGISTER.UP_5302, paramMap);
        Map<String, Object> outptMap = MapUtils.get(resultMap, "output");
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) MapUtils.get(outptMap, "psnfixmedin");
        map.put("mapList", mapList);
        return map;
    }

    /**
     * @param map
     * @Method querySpecialRecord
     * @Desrciption 人员慢特病备案信息查询
     * @Param
     * @Author fuhui
     * @Date 2021/4/23 12:47
     * @Return
     */
    @Override
    public Map<String, Object> querySpecialRecord(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String orgCode = MapUtils.get(map, "orgCode");
        String psnNo = "";
        if (StringUtils.isEmpty(MapUtils.get(map, "psnNo"))) {
            Map<String, Object> data = insureIndividualBasicService_consumer.queryInsureInfo(map).getData();
            psnNo = MapUtils.get(data, "psnNo");
        } else {
            psnNo = (MapUtils.get(map, "psnNo"));
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("psn_no", psnNo);
        Map<String, Object> resultMap = commonInsureUnified(hospCode, orgCode, Constant.UnifiedPay.REGISTER.UP_5301, paramMap);
        Map<String, Object> outptMap = new HashMap<>(1);
        outptMap = MapUtils.get(resultMap, "output");
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) MapUtils.get(outptMap, "feedetail");
        map.put("mapList", mapList);
        return map;
    }

    /**
     * @param map
     * @Method queryMzSpecialLimitPrice
     * @Desrciption 门诊特病限额使用情况查询
     * @Param
     * @Author fuhui
     * @Date 2021/6/8 14:09
     * @Return
     */
    @Override
    public Map<String, Object> queryMzSpecialLimitPrice(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String orgCode = MapUtils.get(map, "orgCode");
        Map<String, Object> data = insureIndividualBasicService_consumer.queryInsureInfo(map).getData();
        String psnNo = MapUtils.get(data, "psnNo");
        String queryYear = MapUtils.get(map, "queryYear");
        String substring = queryYear.substring(0, 4);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("psn_no", psnNo);
        paramMap.put("queryYear", substring);
        Map<String, Object> resultMap = commonInsureUnified(hospCode, orgCode, Constant.UnifiedPay.REGISTER.UP_5301, paramMap);
        Map<String, Object> outptMap = new HashMap<>(1);
        outptMap = MapUtils.get(resultMap, "output");
        List<Map<String, Object>> mapList = (List<Map<String, Object>>) MapUtils.get(outptMap, "cuminfo");
        map.put("mapList", mapList);
        return map;
    }

    /**
     * @param map
     * @Method updateUnifiedDeptInfo
     * @Desrciption 科室信息变更
     * @Param
     * @Author fuhui
     * @Date 2021/6/10 15:00
     * @Return
     */
    @Override
    public Map<String, Object> updateUnifiedDeptInfo(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        BaseDeptDTO deptDTO = baseDeptService_consumer.getById(map).getData();
        map.put("code", "HOSP_INSURE_CODE");
        SysParameterDTO data = sysParameterService_consumer.getParameterByCode(map).getData();
        String orgCode = "";
        if (data != null) {
            orgCode = data.getValue(); // 获取医疗机构编码
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("hosp_dept_codg", deptDTO.getId()); // 医院科室编码  主键id当编码
        paramMap.put("hosp_dept_name", deptDTO.getName()); // 医院科室名称
        paramMap.put("begntime", deptDTO.getBegntime()); // 开始时间
        paramMap.put("endtime", deptDTO.getEndtime()); // 开始时间
        paramMap.put("itro", deptDTO.getIntro()); // 简介
        paramMap.put("dept_resper_name", deptDTO.getDeptResperName()); // 科室负责人姓名
        paramMap.put("dept_resper_tel", deptDTO.getDeptResperTel()); // 科室负责人电话
        paramMap.put("dept_med_serv_scp", deptDTO.getDeptMedServScp()); // 科室医疗服务范围
        paramMap.put("caty", deptDTO.getNationCode()); // 科别
        paramMap.put("dept_estbdat", deptDTO.getCrteTime()); // 科室成立日期
        paramMap.put("aprv_bed_cnt", deptDTO.getAprvBedCnt()); // 批准床位数量
        paramMap.put("hi_crtf_bed_cnt", deptDTO.getHiCrtfBedCnt());
        paramMap.put("poolarea_no", deptDTO.getPoolareaNo());
        paramMap.put("dr_psncnt", deptDTO.getDrPsncnt());
        paramMap.put("phar_psncnt", deptDTO.getPharPsncnt());
        paramMap.put("nurs_psncnt", deptDTO.getNursPsncnt());
        paramMap.put("tecn_psncnt", deptDTO.getTecnPsncnt());
        paramMap.put("memo", deptDTO.getRemark());
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("deptinfo", paramMap);
        Map<String, Object> resultMap = commonInsureUnified(hospCode, orgCode, Constant.UnifiedPay.REGISTER.UP_3403, dataMap);
        return resultMap;
    }

    /**
     * @param map
     * @Method updateUnifiedDept
     * @Desrciption 科室信息上传
     * @Param
     * @Author fuhui
     * @Date 2021/6/10 14:45
     * @Return
     */
    @Override
    public Map<String, Object> updateUnifiedDept(Map<String, Object> map) {
        List<BaseDeptDTO> deptDTOList = baseDeptService_consumer.queryAll(map).getData();
        map.put("code", "HOSP_INSURE_CODE");
        SysParameterDTO data = sysParameterService_consumer.getParameterByCode(map).getData();
        String orgCode = "";
        if (data != null) {
            orgCode = data.getValue(); // 获取医疗机构编码
        }
        String hospCode = MapUtils.get(map, "hospCode");
        List<Map<String, Object>> mapList = new ArrayList<>();
        Map<String, Object> inputMap = new HashMap<>();
        deptDTOList.stream().forEach(deptDTO -> {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("hosp_dept_codg", deptDTO.getId()); // 医院科室编码  主键id当编码
            paramMap.put("hosp_dept_name", deptDTO.getName()); // 医院科室名称
            paramMap.put("begntime", deptDTO.getBegntime()); // 开始时间
            paramMap.put("endtime", deptDTO.getEndtime()); // 开始时间
            paramMap.put("itro", deptDTO.getIntro()); // 简介
            paramMap.put("dept_resper_name", deptDTO.getDeptResperName()); // 科室负责人姓名
            paramMap.put("dept_resper_tel", deptDTO.getDeptResperTel()); // 科室负责人电话
            paramMap.put("dept_med_serv_scp", deptDTO.getDeptMedServScp()); // 科室医疗服务范围
            paramMap.put("caty", deptDTO.getNationCode()); // 科别
            paramMap.put("dept_estbdat", deptDTO.getCrteTime()); // 科室成立日期
            paramMap.put("aprv_bed_cnt", deptDTO.getAprvBedCnt()); // 批准床位数量
            paramMap.put("hi_crtf_bed_cnt", deptDTO.getHiCrtfBedCnt());
            paramMap.put("poolarea_no", deptDTO.getPoolareaNo());
            paramMap.put("dr_psncnt", deptDTO.getDrPsncnt());
            paramMap.put("phar_psncnt", deptDTO.getPharPsncnt());
            paramMap.put("nurs_psncnt", deptDTO.getNursPsncnt());
            paramMap.put("tecn_psncnt", deptDTO.getTecnPsncnt());
            paramMap.put("memo", deptDTO.getRemark());
            mapList.add(paramMap);
        });
        inputMap.put("deptinfo", mapList);
        Map<String, Object> resultMap = commonInsureUnified(hospCode, orgCode, Constant.UnifiedPay.REGISTER.UP_3403, inputMap);
        map.put("deptDTOList", deptDTOList);
        baseDeptService_consumer.updateBatchDept(map).getData();
        return map;
    }

    /**
     * @param map
     * @Method deleteUnifiedDeptInfo
     * @Desrciption 撤销科室信息。
     * @Param
     * @Author fuhui
     * @Date 2021/6/10 15:02
     * @Return
     */
    @Override
    public Map<String, Object> deleteUnifiedDeptInfo(Map<String, Object> map) {
        String hospCode = MapUtils.get(map, "hospCode");
        String id = MapUtils.get(map, "id");
        String code = MapUtils.get(map, "code");
        String deptName = MapUtils.get(map, "name");
        String startTime = MapUtils.get(map, "begntime");
        map.put("code", "HOSP_INSURE_CODE");
        SysParameterDTO data = sysParameterService_consumer.getParameterByCode(map).getData();
        String orgCode = "";
        if (data != null) {
            orgCode = data.getValue(); // 获取医疗机构编码
        }
        Map<String, Object> dataMap = new HashMap<>();
        Map<String, Object> paramMap = new HashMap<>();
        dataMap.put("hosp_dept_codg", code);
        dataMap.put("hosp_dept_name", deptName);
        dataMap.put("begntime", startTime);
        paramMap.put("data", dataMap);
        Map<String, Object> resultMap = commonInsureUnified(hospCode, orgCode, Constant.UnifiedPay.REGISTER.UP_3403, paramMap);
        map.put("isUpload", "0");
        baseDeptService_consumer.update(map).getData();
        return resultMap;
    }


    /**
     * @Method commonInsureUnified
     * @Desrciption 调用统一支付平台公共接口方法
     * @Param hospCode:医院编码
     * orgCode:医疗机构编码
     * functionCode：功能号
     * paramMap:入参
     * @Author fuhui
     * @Date 2021/4/28 19:51
     * @Return
     **/
    private Map<String, Object> commonInsureUnified(String hospCode, String orgCode, String functionCode, Map<String, Object> paramMap) {
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setOrgCode(orgCode);
        insureConfigurationDTO.setIsValid(Constants.SF.S);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        Map httpParam = new HashMap();
        httpParam.put("infno", functionCode);  //交易编号
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //参保地医保区划分
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //定点医药机构编号
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //医保中心编码
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        httpParam.put("input", paramMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("调用功能号【" + functionCode + "】的入参为" + json);
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(insureConfigurationDTO.getUrl(), json);
        if (StringUtils.isEmpty(resultJson)) {
            throw new AppException("无法访问统一支付平台");
        }
        logger.info("调用功能号【" + functionCode + "】的反参为" + resultJson);
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson, Map.class);
        if ("999".equals(MapUtils.get(resultMap, "code"))) {
            throw new AppException((String) resultMap.get("msg"));
        }
        if (!MapUtils.get(resultMap, "infcode").equals("0")) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        return resultMap;
    }


    /**
     * @Method getInsureConfiguration
     * @Desrciption 根据医院编码和医疗机构编码查询对应医保的url和参保区划地址
     * @Param
     * @Author fuhui
     * @Date 2021/3/3 10:52
     * @Return
     **/
    public InsureConfigurationDTO getInsureConfiguration(InsureConfigurationDTO insureConfigurationDTO) {
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if (insureConfigurationDTO == null) {
            throw new AppException("无法获取到医保机构配置信息");
        }
        return insureConfigurationDTO;
    }

}
