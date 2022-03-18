package cn.hsa.report.business.bo.impl.factory.settle;

import cn.hsa.hsaf.core.framework.util.DateUtil;
import cn.hsa.module.insure.inpt.service.InsureUnifiedBaseService;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.service.InsureIndividualVisitService;
import cn.hsa.module.report.config.dao.ReportConfigurationDAO;
import cn.hsa.module.report.config.dto.ReportConfigurationDTO;
import cn.hsa.report.business.bean.FeeInfoDTO;
import cn.hsa.report.business.bean.InsurCumulationInfoDTO;
import cn.hsa.report.business.bean.PsnPolicyResDTO;
import cn.hsa.report.business.bean.RegIdetInfoDTO;
import cn.hsa.report.business.bean.RegInsurInfoDTO;
import cn.hsa.report.business.bean.SettleCommonInfoDTO;
import cn.hsa.report.business.bean.SettleInfoDetailResDTO;
import cn.hsa.report.business.bean.SettleInfoResDTO;
import cn.hsa.report.business.bean.SettleSheetBaseInfoDTO;
import cn.hsa.report.business.bean.SettleSheetHeadDTO;
import cn.hsa.report.business.bean.SettleSheetPartFiveDTO;
import cn.hsa.report.business.bean.SettleSheetPartFourDTO;
import cn.hsa.report.business.bean.SettleSheetPartOneDTO;
import cn.hsa.report.business.bean.SettleSheetPartThreeDTO;
import cn.hsa.util.BigDecimalUtils;
import cn.hsa.util.Constants;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName SettleSheetProcess
 * @Deacription 结算单逻辑
 * @Author liuzhuoting
 * @Date 2021/11/27 14:51
 * @Version 1.0
 **/
public abstract class SettleSheetProcess {

    private final String CITY_LEVEL = "00";

    private final String CHRGITM_LV_01 = "01";

    private final String CHRGITM_LV_02 = "02";

    private final String CHRGITM_LV_03 = "03";

    @Resource
    private InsureIndividualVisitService insureIndividualVisitService_consumer;

    @Resource
    private InsureUnifiedBaseService insureUnifiedBaseService;

    @Autowired
    private ReportConfigurationDAO reportConfigurationDAO;
    /**
     * 结算单信息
     *
     * @param baseInfo   基本信息
     * @return SettleSheetInfoDTO
     */
    public <T> T downLoadSettleInfo(SettleSheetBaseInfoDTO baseInfo) {
        return null;
    }

    public <T> T handlerPastFee(SettleSheetBaseInfoDTO baseInfo) {
        return null;
    }

    SettleSheetBaseInfoDTO initBaseInfo(Map<String, Object> map){
        SettleSheetBaseInfoDTO baseInfo = new SettleSheetBaseInfoDTO();
        String insureSettleId = MapUtils.get(map, "insureSettleId"); // 医保结算id
        String hospCode = MapUtils.get(map, "hospCode");
        baseInfo.setSetlId(insureSettleId);
        baseInfo.setHospCode(hospCode);
        InsureIndividualVisitDTO insureIndividualVisitDTO = insureIndividualVisitService_consumer.getInsureIndividualVisitById(map);

        _initBaseInfo(baseInfo,insureIndividualVisitDTO);

        ReportConfigurationDTO configuration = reportConfigurationDAO.queryByTempCode(hospCode, (String) map.get("tempCode"));
        String customConfigStr = configuration.getCustomConfig().replace("\\", "").replace("\"{", "{").replace("}\"", "}");
        // 自定义配置
        Map customConfigMap = JSON.parseObject(customConfigStr, Map.class);
        baseInfo.setCustomConfigMap(customConfigMap);
        return baseInfo;
    }

    private void _initBaseInfo(SettleSheetBaseInfoDTO baseInfo, InsureIndividualVisitDTO insureIndividualVisitDTO){

        //家庭地址
        baseInfo.setAddress(insureIndividualVisitDTO.getAddress());
        //入院床位
        baseInfo.setAdmBed(insureIndividualVisitDTO.getVisitBerth());
        //入院科室名称
        baseInfo.setAdmDeptName(insureIndividualVisitDTO.getVisitDrptName());
        //协议定点统筹区
        baseInfo.setAdmdvs("");
        // 主管医师
        baseInfo.setChfpdrName(insureIndividualVisitDTO.getZzDoctorName());
        // 病种
        String bka006Name = insureIndividualVisitDTO.getBka006Name();
        String visitIcdName = insureIndividualVisitDTO.getVisitIcdName();
        if (StringUtils.isEmpty(bka006Name) || "null".equals(bka006Name)) {
            baseInfo.setDiseName(visitIcdName);
        } else {
            baseInfo.setDiseName(bka006Name);
        }

        baseInfo.setDscgDeptName(insureIndividualVisitDTO.getOutDiseaseName()); // 出院诊断
        baseInfo.setInsuplcAdmdvs(insureIndividualVisitDTO.getInsuplcAdmdvs());

        Map<String, Object> visitMap = new HashMap<>();
        visitMap.put("hospCode", insureIndividualVisitDTO.getHospCode());
        visitMap.put("visitId", insureIndividualVisitDTO.getVisitId());
        visitMap.put("insuplcAdmdvs", insureIndividualVisitDTO.getInsuplcAdmdvs());
//        String insuplcAdmdvsName = insureIndividualVisitDAO.selectInsuplcName(visitMap);
//        baseInfo.setInsuplcAdmdvsName(insuplcAdmdvsName);

//        baseInfo.setInsurMaindiagName();
        // 险种
        baseInfo.setInsutype(insureIndividualVisitDTO.getAae140());
        // 住院号
        baseInfo.setIptOtpNo(insureIndividualVisitDTO.getVisitNo());
        // 医保就诊号
        baseInfo.setMdtrtId(insureIndividualVisitDTO.getMedicalRegNo());
        // 医疗类别
        baseInfo.setMedType(insureIndividualVisitDTO.getAka130());

//        baseInfo.setMedTypeName();
//        // 个人编号
//        baseInfo.setPsnNo(MapUtils.get(setlInfoMap, "psn_no"));
//        // 结算id
//        baseInfo.setSetlId(MapUtils.get(setlInfoMap, "setl_id"));

        String phone = "";
        if (Constants.SF.S.equals(insureIndividualVisitDTO.getIsHospital())) {
            phone = insureIndividualVisitDTO.getInptPhone();
        } else {
            phone = insureIndividualVisitDTO.getOutPhone();
        }
        baseInfo.setTel(phone);
        baseInfo.setVisitId(insureIndividualVisitDTO.getVisitId());

        Map<String, Object> map = new HashMap<>();
        map.put("hospCode",insureIndividualVisitDTO.getHospCode());
        map.put("insureSettleId",insureIndividualVisitDTO.getHospCode());

        Map<String, Object> data = insureUnifiedBaseService.querySettleDeInfo(map).getData();
        SettleInfoResDTO settleInfo = new SettleInfoResDTO();
        settleInfo = (SettleInfoResDTO) data.get("setlinfo");
        List<SettleInfoDetailResDTO> settleInfoDetail = (List<SettleInfoDetailResDTO>) data.get("setldetail");
        baseInfo.setSettleInfo(settleInfo);
        baseInfo.setSettleInfoDetail(settleInfoDetail);
    }


    SettleCommonInfoDTO getSettleCommonInfoDTO(SettleSheetBaseInfoDTO baseInfo) {
        SettleCommonInfoDTO settleCommonInfoDTO = new SettleCommonInfoDTO();

        switch ((String) baseInfo.getCustomConfigMap().get("dataSource")) {
            case "0":
                Map<String, Object> map = new HashMap<>();
                map.put("hospCode",baseInfo.getHospCode());
                map.put("insureSettleId",baseInfo.getSetlId());
                Map<String, Object> settleMap = insureUnifiedBaseService.querySettleDeInfo(map).getData();
                settleCommonInfoDTO.setSettleInfo((SettleInfoResDTO) settleMap.get("setlinfo"));
                settleCommonInfoDTO.setSettleInfoDetail((List<SettleInfoDetailResDTO>) settleMap.get("setldetail"));
                break;
            case "1":
                settleCommonInfoDTO.setSettleInfo(baseInfo.getSettleInfo());
                settleCommonInfoDTO.setSettleInfoDetail(baseInfo.getSettleInfoDetail());
                break;
            default:
        }
        return settleCommonInfoDTO;
    }

    SettleSheetHeadDTO handlerMedisnInfo(SettleInfoResDTO settleInfoResDTO, SettleSheetBaseInfoDTO baseInfo) {
        SettleSheetHeadDTO head = new SettleSheetHeadDTO();
        String orgLv = (String) baseInfo.getCustomConfigMap().get("orgLv");
        head.setHospLv(StringUtils.isNotEmpty(orgLv) ? orgLv : settleInfoResDTO.getHospLv());
        head.setHospLvName(transDict(head.getHospLv(), "hosp_lv"));
        String orgName = (String) baseInfo.getCustomConfigMap().get("orgName");
        head.setHospName(StringUtils.isNotEmpty(orgName) ? orgName : settleInfoResDTO.getFixmedinsName());
        head.setPrintDate(new Date());
        head.setUnit("元");
        head.setOpterName(initOpterName((String) baseInfo.getCustomConfigMap().get("opter"), settleInfoResDTO));
        String provinceName = initProvinceName(baseInfo);
        String cityName = initCityName(baseInfo);
        String headName = provinceName + cityName;
        if (cityName.contains(provinceName)) {
            headName = cityName;
        }
        head.setSettleTitle(headName + baseInfo.getCustomConfigMap().get("headDetail"));
        return head;
    }

    private String initOpterName(String sheetOpter, SettleInfoResDTO settleInfo) {
        String opterName = null;
        //省级统筹区
        switch (sheetOpter) {
            case "1":
                opterName = settleInfo.getOpterId();
                break;
            case "2":
                opterName = settleInfo.getOpterName();
                break;
            default:
        }
        return opterName;
    }

    private String initProvinceName(SettleSheetBaseInfoDTO baseInfo) {
        String admdvs = "430199";
        String headProvince = (String) baseInfo.getCustomConfigMap().get("headProvince");
        //省级统筹区
        switch (headProvince) {
            case "1":
//                admdvs = sysUserDTO.getDistrictId();
                break;
            case "2":
                admdvs = baseInfo.getInsuplcAdmdvs();
                break;
            default:
        }
//        return registerBO.queryAdmdvsByInsuplcAdmdvs(admdvs.substring(0, 2) + "0000").getInsuplcAdmdvsName();
        return admdvs;
    }

    private String initCityName(SettleSheetBaseInfoDTO baseInfo) {
        String cityName = "";
        String headCity = (String) baseInfo.getCustomConfigMap().get("headCity");
        //市级统筹区
        switch (headCity) {
            case "1":
//                cityName = registerBO.queryAdmdvsByInsuplcAdmdvs(sysUserDTO.getDistrictId()).getInsuplcAdmdvsName();
                break;
            case "2":
//                cityName = registerBO.queryAdmdvsByInsuplcAdmdvs(sysUserDTO.getDistrictId().substring(0, 2) + "9900").getInsuplcAdmdvsName();
                break;
            case "3":
//                cityName = registerBO.queryAdmdvsByInsuplcAdmdvs(insurUtil.getMdtrtareaAdmvs(baseInfo.getAdmdvs())).getInsuplcAdmdvsName();
                break;
            case "4":
                if (baseInfo.getInsuplcAdmdvs().equals(baseInfo.getInsuplcAdmdvs().substring(0, 4) + CITY_LEVEL)) {
//                    cityName = registerBO.queryAdmdvsByInsuplcAdmdvs(baseInfo.getInsuplcAdmdvs()).getInsuplcAdmdvsName();
                } else {
//                    String cityA = registerBO.queryAdmdvsByInsuplcAdmdvs(baseInfo.getInsuplcAdmdvs().substring(0, 4) + CITY_LEVEL).getInsuplcAdmdvsName();
//                    String cityB = registerBO.queryAdmdvsByInsuplcAdmdvs(baseInfo.getInsuplcAdmdvs()).getInsuplcAdmdvsName();
//                    cityName = cityA + cityB;
                }
                break;
            default:
        }
        return cityName;
    }

    public <T> T handlerBaseInfo(SettleInfoResDTO settleInfoResDTO, SettleSheetBaseInfoDTO baseInfo) {
        return null;
    }

    SettleSheetPartOneDTO handlerCommonBaseInfo(SettleInfoResDTO settleInfoResDTO, SettleSheetBaseInfoDTO baseInfo) {
        List<RegIdetInfoDTO> idetList = new ArrayList<>();
//        List<RegIdetInfoDTO> idetList = regIdetInfoBO.queryByPsnNo(baseInfo.getPsnNo());
        if (CollectionUtil.isNotEmpty(idetList)) {
//            idetList = idetList.stream().filter(item -> item.getEndtime() == null || DateUtil.betweenDate(item.getBegntime(), item.getEndtime(), new Date())).collect(Collectors.toList());
        }

        RegInsurInfoDTO insurInfo = new RegInsurInfoDTO();
//        RegInsurInfoDTO insurInfo = regInsurInfoBO.getInsurInfo(baseInfo.getPsnNo(), baseInfo.getInsutype(), baseInfo.getInsuplcAdmdvs());

        SettleSheetPartOneDTO partOne = new SettleSheetPartOneDTO();
        BeanUtils.copyProperties( settleInfoResDTO, partOne);
        partOne.setPsnType(StringUtils.isNotEmpty(settleInfoResDTO.getPsnType()) ? settleInfoResDTO.getPsnType() : insurInfo.getPsnType());
        partOne.setGendName(transDict(partOne.getGend(), "gend"));
        partOne.setPsnIdetType(CollectionUtil.isEmpty(idetList) ? "无" : idetList.get(0).getPsnIdetType());
        partOne.setPsnIdetTypeName(transDict(partOne.getPsnIdetType(), "psn_idet_type", "mat_idet_code"));
        partOne.setCvlservFlagName(transDict(partOne.getCvlservFlag(), "cvlserv_flag"));
        partOne.setPsnTypeName(transDict(partOne.getPsnType(), "psn_type"));
        partOne.setMdtrtCertTypeName(transDict(partOne.getMdtrtCertType(), "mdtrt_cert_type"));
        partOne.setEmpName(insurInfo == null ? "无" : insurInfo.getEmpName());
        partOne.setTel(baseInfo.getTel());
        partOne.setAge(settleInfoResDTO.getAge() == null ? null : settleInfoResDTO.getAge().intValue());
        partOne.setIptNo(baseInfo.getIptOtpNo());
        partOne.setAdmDeptName(baseInfo.getAdmDeptName());
        partOne.setAdmBed(baseInfo.getAdmBed());
        partOne.setBegntime(settleInfoResDTO.getBegndate());
        partOne.setEndtime(settleInfoResDTO.getEnddate());
        partOne.setChfpdrName(baseInfo.getChfpdrName());
        partOne.setInsuplcAdmdvsName(baseInfo.getInsuplcAdmdvsName());
//        long days = DateUtil.getDays(settleInfoResDTO.getBegndate(), settleInfoResDTO.getEnddate());
        long days = 0L;
        partOne.setEndtimeBegntime(days == 0L ? 1L : days);
        partOne.setDscgMaindiagName(baseInfo.getInsurMaindiagName());
        partOne.setDiseName(baseInfo.getDiseName());
        partOne.setMedTypeName(baseInfo.getMedTypeName());
        return partOne;
    }



    /**
     * 获取累计信息
     *
     * @param baseInfo
     * @return
     */
    List<InsurCumulationInfoDTO> getInsurCumulationInfoDTO(SettleSheetBaseInfoDTO baseInfo) {
        List<InsurCumulationInfoDTO> cumulationInfo = null;
//        List<InsurCumulationInfoDTO> cumulationInfo = insurCumulationInfoBO.queryListByPsnNo(baseInfo.getMdtrtId(), baseInfo.getPsnNo());
        if (CollectionUtil.isEmpty(cumulationInfo)) {
//            cumulationInfo = insurCumulationInfoBO.saveInsurBatch(baseInfo.getMdtrtId(), baseInfo.getPsnNo(), baseInfo.getInsutype());
        }
        return cumulationInfo;
    }

    List<SettleSheetPartThreeDTO> handlerInsureIndividualCost(String mdtrtId) {
        List<SettleSheetPartThreeDTO> partThreeList = new ArrayList<>();
//        CostQueryDTO query = new CostQueryDTO();
//        query.setMdtrtId(mdtrtId);
//        query = costUploadBO.assem(query);
//        BasInsurDictTypeDTO dictQuery = new BasInsurDictTypeDTO();
//        dictQuery.setType(BusinessConstants.MED_CHRGITM_TYPE);
//        List<BasInsurDictTypeDTO> dictList = basInsurDictTypeBO.queryPage(dictQuery);
//        Map<String, BasInsurDictTypeDTO> dictMap = dictList.stream().collect(Collectors.toMap(k -> k.getCode(), k -> k));

//        List<FeeInfoDTO> feeInfoDTOList = costUploadBO.queryByMdtrtId(query);
        List<FeeInfoDTO> feeInfoDTOList = new ArrayList<>();
        if (CollectionUtil.isEmpty(feeInfoDTOList)) {
            return partThreeList;
        }
        feeInfoDTOList = feeInfoDTOList.stream().filter(item -> StringUtils.isNotEmpty(item.getMedChrgitmType())).collect(Collectors.toList());
        Map<String, List<FeeInfoDTO>> groupMap = feeInfoDTOList.stream().collect(Collectors.groupingBy(item -> item.getMedChrgitmType()));
        SettleSheetPartThreeDTO partThree;
        for (String key : groupMap.keySet()) {
            Iterator<FeeInfoDTO> iterator = groupMap.get(key).iterator();
            if (iterator.hasNext()) {
                partThree = new SettleSheetPartThreeDTO();
                partThree.setScale();
                List<FeeInfoDTO> listMap = groupMap.get(key);
                for (FeeInfoDTO item : listMap) {
                    handlerCost(partThree, item.getChrgitmLv(), item.getDetItemFeeSumamt());
                }
                partThree.setDClassFee(BigDecimalUtils.add(partThree.getAClassFee(), partThree.getBClassFee()));
                partThree.setMedChrgitmType(key);
                partThree.setMedChrgitmTypeName(transDict(partThree.getMedChrgitmType(), "med_chrgitm_type"));
//                if (!MapUtils.isEmpty(dictMap) && dictMap.get(key) != null) {
//                    partThree.setIsort(dictMap.get(key).getIsort());
//                }
                partThree.setScale();
                partThreeList.add(partThree);
            }
        }
        partThreeList.sort(SettleSheetPartThreeDTO::compareTo);
        return partThreeList;
    }

    void handlerCost(SettleSheetPartThreeDTO partThree, String chrgitmLv, BigDecimal detItemFeeSumamt) {
        partThree.setSumDetItemFeeSumamt(BigDecimalUtils.add(partThree.getSumDetItemFeeSumamt(), detItemFeeSumamt));
        if (CHRGITM_LV_01.equals(chrgitmLv)) {
            partThree.setAClassFee(BigDecimalUtils.add(partThree.getAClassFee(), detItemFeeSumamt));
        }
        if (CHRGITM_LV_02.equals(chrgitmLv)) {
            partThree.setBClassFee(BigDecimalUtils.add(partThree.getBClassFee(), detItemFeeSumamt));
        }
        if (CHRGITM_LV_03.equals(chrgitmLv)) {
            partThree.setCClassFee(BigDecimalUtils.add(partThree.getCClassFee(), detItemFeeSumamt));
        }
    }


    List<SettleSheetPartFourDTO> handlerPolicy(SettleSheetBaseInfoDTO baseInfo) {
        List<SettleSheetPartFourDTO> partFourList = new ArrayList<>();

//        PsnPolicyReqDTO query = new PsnPolicyReqDTO();
//        query.setSetlId(baseInfo.getSetlId());
//        query.setPsnNo(baseInfo.getPsnNo());
//        query.setMdtrtId(baseInfo.getMdtrtId());
//        List<PsnPolicyResDTO> psnPolicyResDTOList = insurQueryBO.getPsnPolicy(query, baseInfo.getInsuplcAdmdvs());
        List<PsnPolicyResDTO> psnPolicyResDTOList = new ArrayList<>();
        if (CollectionUtil.isEmpty(psnPolicyResDTOList)) {
            return partFourList;
        }
        partFourList = psnPolicyResDTOList.stream().map((entity) -> {
                    SettleSheetPartFourDTO info = new SettleSheetPartFourDTO();
                    info.setScale();
                    BeanUtils.copyProperties(entity, info);
                    if (StringUtils.isEmpty(info.getSelfPayProp()) || "0".equals(info.getSelfPayProp())) {
                        info.setSelfPayProp("0.00%");
                    }
                    if (StringUtils.isEmpty(info.getFundPayProp()) || "0".equals(info.getFundPayProp())) {
                        info.setFundPayProp("0.00%");
                    }
                    if (StringUtils.isEmpty(info.getPolItemName())) {
                        info.setPolItemName(transLocalDict(info.getPolItemCode(), "pol_item_code"));
                    }
                    info.setScale();
                    return info;
                }
        ).collect(Collectors.toList());

        return partFourList;
    }

    SettleSheetPartFiveDTO handlerCommonInsureSettleFee(SettleInfoResDTO setlInfo) {
        SettleSheetPartFiveDTO partFive = new SettleSheetPartFiveDTO();
        partFive.setScale();
        BeanUtils.copyProperties(setlInfo,partFive);
        partFive.setHospPrice(setlInfo.getMafPay());
        partFive.setPsnCashPay(setlInfo.getCashPayamt());
        partFive.setHifmipPay(setlInfo.getHifmiPay());
        partFive.setFundSumAmt(setlInfo.getFundPaySumamt());
        return partFive;
    }







    private String transDict(String code, String key) {
//        Map<String, String> map = redisUtil.getJson(RedisBusinessConstants.LOCK_PREFIX_INSUR_CODE + key, Map.class);
        Map<String, String> map = new HashMap<>();
        return MapUtils.isEmpty(map) ? code : (StringUtils.isEmpty(map.get(code)) ? code : map.get(code));
    }

    private String transDict(String code, String key1, String key2) {
//        Map<String, String> map1 = redisUtil.getJson(RedisBusinessConstants.LOCK_PREFIX_INSUR_CODE + key1, Map.class);
//        Map<String, String> map2 = redisUtil.getJson(RedisBusinessConstants.LOCK_PREFIX_INSUR_CODE + key2, Map.class);
        Map<String, String> map1 = new HashMap<>();
        Map<String, String> map2 = new HashMap<>();
        return MapUtils.isEmpty(map1) ?
                (MapUtils.isEmpty(map2) ?
                        code : (StringUtils.isEmpty(map2.get(code)) ? "无" : map2.get(code))
                )
                : (StringUtils.isEmpty(map1.get(code)) ? "无" : map1.get(code));
    }

    private String transLocalDict(String code, String key) {
//        Map<String, String> map = redisUtil.getJson(RedisBusinessConstants.LOCK_PREFIX_LOCAL_CODE + key, Map.class);
        Map<String, String> map = new HashMap<>();
        return MapUtils.isEmpty(map) ? code : (StringUtils.isEmpty(map.get(code)) ? code : map.get(code));
    }

}
