package cn.hsa.insure.fmiownpaypatn.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.enums.FunctionEnum;
import cn.hsa.insure.unifiedpay.bo.impl.InsureItfBOImpl;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.BaseReqUtilFactory;
import cn.hsa.insure.util.Constant;

import cn.hsa.insure.util.*;

import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.doctor.dto.OutptCostDTO;
import cn.hsa.module.inpt.doctor.service.DoctorAdviceService;
import cn.hsa.module.inpt.inregister.service.InptVisitService;
import cn.hsa.module.insure.fmiownpaypatn.dto.FmiOwnpayPatnDiseListDDTO;
import cn.hsa.module.insure.fmiownpaypatn.dto.FmiOwnpayPatnFeeListDDTO;
import cn.hsa.module.insure.fmiownpaypatn.dto.FmiOwnpayPatnMdtrtDDTO;

import cn.hsa.module.insure.module.dao.InsureConfigurationDAO;
import cn.hsa.module.insure.module.dao.InsureGetInfoDAO;
import cn.hsa.module.insure.module.dao.InsureIndividualCostDAO;
import cn.hsa.module.insure.module.dto.*;
import cn.hsa.module.insure.module.entity.InsureIndividualCostDO;
import cn.hsa.module.insure.fmiownpaypatn.bo.InsureFmiOwnpayPatnBO;
import cn.hsa.module.outpt.fees.dao.OutptCostDAO;
import cn.hsa.module.outpt.prescribe.dao.OutptDoctorPrescribeDAO;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.prescribe.service.OutptDoctorPrescribeService;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDTO;
import cn.hsa.module.outpt.visit.dao.OutptVisitDAO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.outpt.visit.service.OutptVisitService;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.BigDecimalUtils;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import cn.hsa.util.HttpConnectUtil;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class InsureFmiOwnpayPatnBOImpl extends HsafBO implements InsureFmiOwnpayPatnBO {

    @Resource
    private InsureGetInfoDAO insureGetInfoDAO;

    @Resource
    private InsureConfigurationDAO insureConfigurationDAO;

    @Resource
    private InsureIndividualCostDAO insureIndividualCostDAO;


    @Resource
    private SysParameterService sysParameterService_consumer;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private DoctorAdviceService doctorAdviceService_consumer;
    @Resource
    private OutptDoctorPrescribeService outptDoctorPrescribeService;
    @Resource
    private InptVisitService inptVisitService_consumer;
    @Resource
    private OutptVisitService outptVisitService_consumer;
    @Resource
    private InsureItfBOImpl insureItfBO;

    @Resource
    private BaseReqUtilFactory baseReqUtilFactory;

    @Resource
    private UnifiedCommon unifiedCommon;

    @Resource
    private OutptVisitDAO outptVisitDAO;

    @Resource
    private OutptDoctorPrescribeDAO outptDoctorPrescribeDAO;

    @Resource
    private OutptCostDAO outptCostDAO;

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
        // ????????????????????????
        Map<String, Object> isInsureUnifiedMap = new HashMap<>();
        isInsureUnifiedMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        isInsureUnifiedMap.put("code", "HOSP_INSURE_CODE");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
        if (sysParameterDTO == null) {
            throw new AppException("???????????????????????????????????????????????????:?????????:HOSP_INSURE_CODE,????????????????????????????????????");
        }
        for(InptVisitDTO inptVisitDTO :visitDTOList){
            inptVisitDTO.setOrgCode(sysParameterDTO.getValue());
        }


        return PageDTO.of(visitDTOList);

    }

    /**
     * @Method queryUnMatchPage
     * @Desrciption ???????????????????????????????????????
     * @Param
     * @Author fuhui
     * @Date 2021/8/21 15:37
     * @Return
     **/
    @Override
    public PageDTO queryMatchFeePage(InsureSettleInfoDTO insureSettleInfoDTO) {
        Map<String, Object> isInsureUnifiedMap = new HashMap<>();
        isInsureUnifiedMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        isInsureUnifiedMap.put("code", "HOSP_INSURE_CODE");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
        if (sysParameterDTO == null) {
            throw new AppException("???????????????????????????????????????????????????:?????????:HOSP_INSURE_CODE,????????????????????????????????????");
        }
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        insureConfigurationDTO.setOrgCode(sysParameterDTO.getValue());
        insureConfigurationDTO.setRegCode(insureSettleInfoDTO.getInsureRegCode());
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
     * @Desrciption ??????????????????????????????????????????
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
     * @return
     * @Method getInsureCost
     * @Desrciption ????????????????????????????????????
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-11 22:54
     * @Return java.util.Map
     */
    @Override
    public Boolean insertInsureFmiOwnPayPatnCost(InsureSettleInfoDTO insureSettleInfoDTO) {
        // 1.????????????????????????????????????
        InsureConfigurationDTO  insureConfigurationDTO = checkInsureConfig(insureSettleInfoDTO);
        insureSettleInfoDTO.setOrgCode(insureConfigurationDTO.getCode());
        // 2.??????????????????????????? ?????? ??????????????????  ??????????????????
        List<Map<String, Object>> mapList = new ArrayList<>();
        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        List<InptDiagnoseDTO> inptDiagnoseDTOList = null;
        List<InptDiagnoseDTO> inptMatchDiagnoseDTOList = null;

        OutptVisitDTO outptVisitDTO = new OutptVisitDTO();
        List<OutptDiagnoseDTO> outptDiagnoseDTOList = null;
        List<OutptDiagnoseDTO> outptMatchDiagnoseDTOList = null;


        if (insureSettleInfoDTO.getLx().equals("1")) {
            inptVisitDTO.setHospCode(insureSettleInfoDTO.getHospCode());
            inptVisitDTO.setId(insureSettleInfoDTO.getId());
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("hospCode", insureSettleInfoDTO.getHospCode());
            reqMap.put("inptVisitDTO", inptVisitDTO);
            inptVisitDTO = inptVisitService_consumer.getInptVisitById(reqMap).getData();

            String insureOrgCode = insureConfigurationDTO.getRegCode();
            inptVisitDTO.setInsureRegCode(insureOrgCode);
            List<String> diagnoseList = Stream.of("101","102","201","202","203","303","204").collect(Collectors.toList());
            inptVisitDTO.setVisitId(insureSettleInfoDTO.getId());
            inptVisitDTO.setDiagnoseList(diagnoseList);
            reqMap.put("inptVisitDTO",inptVisitDTO);
            inptDiagnoseDTOList = doctorAdviceService_consumer.getInptDiagnose(reqMap).getData();
            if(ListUtils.isEmpty(inptDiagnoseDTOList)) {
                throw new AppException("??????????????????????????????");
            }
            /**
             * ????????????????????????????????????
             * 1.??????????????????????????????
             * 2.?????????????????????????????????
             */
            inptMatchDiagnoseDTOList = doctorAdviceService_consumer.queryInptDiagnose(reqMap).getData();

            mapList = handlerInptCostFee(insureSettleInfoDTO);
            //????????????
            inptVisitDTO.setIsUplodDise("1");

        } else if (insureSettleInfoDTO.getLx().equals("0")) {
            Map<String, String> visitMap = new HashMap();
            visitMap.put("id",insureSettleInfoDTO.getId());
            visitMap.put("hospCode",insureSettleInfoDTO.getHospCode());

            outptVisitDTO = outptVisitService_consumer.queryByVisitID(visitMap);

            Map<String, Object> map = new HashMap<>();
            map.put("id",insureSettleInfoDTO.getId());
            map.put("hospCode",insureSettleInfoDTO.getHospCode());
            List<String> diagnoseList = Stream.of("101", "102").collect(Collectors.toList());
            OutptDiagnoseDTO outptDiagnoseDTO = new OutptDiagnoseDTO();
            outptDiagnoseDTO.setHospCode(insureSettleInfoDTO.getHospCode());
            outptDiagnoseDTO.setVisitId(insureSettleInfoDTO.getId());
            outptDiagnoseDTO.setInsureRegCode(insureSettleInfoDTO.getInsureRegCode());
            map.put("outptDiagnoseDTO", outptDiagnoseDTO);

            OutptPrescribeDTO outptPrescribeDTO = new OutptPrescribeDTO();
            outptPrescribeDTO.setVisitId(insureSettleInfoDTO.getId());
            outptPrescribeDTO.setHospCode(insureSettleInfoDTO.getHospCode());
            outptPrescribeDTO.setDiagnoseList(diagnoseList);
            map.put("outptPrescribeDTO", outptPrescribeDTO);
            outptDiagnoseDTOList = outptDoctorPrescribeService.getOutptDiagnose(map).getData();
            if (ListUtils.isEmpty(outptDiagnoseDTOList)) {
                throw new AppException("??????????????????????????????");
            }
            outptMatchDiagnoseDTOList = outptDoctorPrescribeService.queryOutptDiagnose(map).getData();

            mapList = handlerOutptCostFee(insureSettleInfoDTO);
            //????????????
            outptVisitDTO.setIsUploadDise("1");
        }


        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("insureSettleInfoDTO",insureSettleInfoDTO);

        paramMap.put("inptVisitDTO",inptVisitDTO);
        paramMap.put("inptDiagnoseDTOList",inptDiagnoseDTOList);
        paramMap.put("inptMatchDiagnoseDTOList",inptMatchDiagnoseDTOList);

        paramMap.put("outptVisitDTO",outptVisitDTO);
        paramMap.put("outptDiagnoseDTOList",outptDiagnoseDTOList);
        paramMap.put("outptMatchDiagnoseDTOList",outptMatchDiagnoseDTOList);

        paramMap.put("insureConfigurationDTO",insureConfigurationDTO);


        paramMap.put("mapList",mapList);
        paramMap.put("visitId", insureSettleInfoDTO.getId());
        paramMap.put("hospCode",insureSettleInfoDTO.getHospCode());
        // ?????????????????????
//        paramMap.put("insuplcAdmdvs", insureConfigurationDTO.getInsuplcAdmdvs());
        paramMap.put("configRegCode", insureConfigurationDTO.getRegCode());
        paramMap.put("orgCode", insureConfigurationDTO.getOrgCode());
        paramMap.put("isHospital", Constants.SF.F);
        //????????????,??????????????????????????????
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.FMI_OWNPAY_PATN_UPLOD.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        interfaceParamDTO.setFixmedins_code(insureSettleInfoDTO.getOrgCode());
        interfaceParamDTO.setFixmedins_name(insureConfigurationDTO.getHospName());
        interfaceParamDTO.setIsHospital(insureSettleInfoDTO.getLx());
        interfaceParamDTO.setVisitId(insureSettleInfoDTO.getId());
        // ??????????????????????????????
        insureItfBO.executeInsur(FunctionEnum.FMI_OWNPAY_PATN_UPLOD, interfaceParamDTO);

        insertHandlerInsureCost(mapList, insureSettleInfoDTO);
        //????????????
        if(insureSettleInfoDTO.getLx().equals("1")){
            Map<String, Object> updateMap = new HashMap<>();
            updateMap.put("hospCode", insureSettleInfoDTO.getHospCode());
            updateMap.put("inptVisitDTO", inptVisitDTO);
            inptVisitService_consumer.updateUplod(updateMap);
        }else if (insureSettleInfoDTO.getLx().equals("0")){
            Map paramMap1 = new HashMap();
            paramMap1.put("hospCode",insureSettleInfoDTO.getHospCode());
            paramMap1.put("outptVisitDTO",outptVisitDTO);
            outptVisitService_consumer.updateOutptVisitUploadFlag(paramMap1);
        }
        return true;
    }

    @Override
    public Map<String, Object> queryFmiOwnPayPatnReconciliationInfo(InsureSettleInfoDTO insureSettleInfoDTO) {
        Map<String, Object> paramMap = new HashMap<>();

        Map<String, Object> isInsureUnifiedMap = new HashMap<>();
        isInsureUnifiedMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        isInsureUnifiedMap.put("code", "HOSP_INSURE_CODE");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
        if (sysParameterDTO == null) {
            throw new AppException("???????????????????????????????????????????????????:?????????:HOSP_INSURE_CODE,????????????????????????????????????");
        }

        paramMap.put("fixmedinsCode",sysParameterDTO.getValue());
        paramMap.put("visitId", insureSettleInfoDTO.getId());
        paramMap.put("certno", insureSettleInfoDTO.getCertNo());
        paramMap.put("pageNum", insureSettleInfoDTO.getPageNo());
        paramMap.put("pageSize", insureSettleInfoDTO.getPageSize());
        // ?????????????????????
//        paramMap.put("insuplcAdmdvs", insureConfigurationDTO.getInsuplcAdmdvs());
//        paramMap.put("orgCode", insureConfigurationDTO.getOrgCode());
//        paramMap.put("isHospital", Constants.SF.F);
        paramMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        paramMap.put("configRegCode",insureSettleInfoDTO.getInsureRegCode());
        //????????????,??????????????????????????????
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.FMI_OWNPAY_PATN_LEDGER_DETAIL.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        interfaceParamDTO.setIsHospital(insureSettleInfoDTO.getLx());
        interfaceParamDTO.setVisitId(insureSettleInfoDTO.getId());
        // ??????????????????????????????
        Map<String, Object> res = insureItfBO.executeInsur(FunctionEnum.FMI_OWNPAY_PATN_LEDGER_DETAIL, interfaceParamDTO);
        return res;
    }

    @Override
    public Map<String, Object> queryFmiOwnPayPatnReconciliation(InsureSettleInfoDTO insureSettleInfoDTO) {
        Map<String, Object> paramMap = new HashMap<>();

        Map<String, Object> isInsureUnifiedMap = new HashMap<>();
        isInsureUnifiedMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        isInsureUnifiedMap.put("code", "HOSP_INSURE_CODE");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
        if (sysParameterDTO == null) {
            throw new AppException("???????????????????????????????????????????????????:?????????:HOSP_INSURE_CODE,????????????????????????????????????");
        }

        paramMap.put("fixmedinsCode",sysParameterDTO.getValue());
        paramMap.put("visitId", insureSettleInfoDTO.getId());
        paramMap.put("mdtrtId", insureSettleInfoDTO.getId());

        BigDecimal sum = new BigDecimal(0);
        List<InsureUploadCostDTO> costDTOList = insureGetInfoDAO.queryAll(insureSettleInfoDTO);
        if(!costDTOList.isEmpty() && costDTOList.size() > 0) {
            for(InsureUploadCostDTO insureUploadCostDTO : costDTOList){
                sum = sum.add(insureUploadCostDTO.getDetItemFeeSumamt());
            }
        }
        paramMap.put("totalFeeSumamt", sum);
        // ?????????????????????
//        paramMap.put("insuplcAdmdvs", insureConfigurationDTO.getInsuplcAdmdvs());
        paramMap.put("orgCode", insureSettleInfoDTO.getOrgCode());
        paramMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        paramMap.put("configRegCode",insureSettleInfoDTO.getInsureRegCode());
//        paramMap.put("isHospital", Constants.SF.F);
        //????????????,??????????????????????????????
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.FMI_OWNPAY_PATN_LEDGER.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        interfaceParamDTO.setIsHospital(insureSettleInfoDTO.getLx());
        interfaceParamDTO.setVisitId(insureSettleInfoDTO.getId());
        // ??????????????????????????????
        Map<String, Object> res = insureItfBO.executeInsur(FunctionEnum.FMI_OWNPAY_PATN_LEDGER, interfaceParamDTO);
        return res;
    }

    /**
     * @return
     * @Method getInsureCost
     * @Desrciption ????????????????????????????????????
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-11 22:54
     * @Return java.util.Map
     */
    @Override
    public Boolean insertInsureCost(InsureSettleInfoDTO insureSettleInfoDTO) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        // 1.????????????????????????????????????
        InsureConfigurationDTO  insureConfigurationDTO = checkInsureConfig(insureSettleInfoDTO);
        insureSettleInfoDTO.setOrgCode(insureConfigurationDTO.getCode());
        // 2.??????????????????????????? ?????? ??????????????????  ??????????????????
        if (insureSettleInfoDTO.getLx().equals("1")) {
            mapList = handlerInptCostFee(insureSettleInfoDTO);
        } else if (insureSettleInfoDTO.getLx().equals("0")) {
            mapList = handlerOutptCostFee(insureSettleInfoDTO);
        }
        // 3.????????????????????????
        List<Map<String, Object>> listMap = new ArrayList<>();
        if (!ListUtils.isEmpty(mapList)) {

            for (Map<String, Object> item : mapList) {
                // ????????????
                Map feedetail = new HashMap();
                String doctorId = MapUtils.get(item, "doctorId");
                String doctorName = MapUtils.get(item, "doctorName");
                feedetail.put("mdtrt_sn", insureSettleInfoDTO.getVisitNo()); // ???????????????
                feedetail.put("ipt_otp_no", insureSettleInfoDTO.getVisitNo()); // ??????/?????????
                if ("1".equals(insureSettleInfoDTO.getLx())) {
                    feedetail.put("med_type", "1201"); // ????????????
                } else {
                    feedetail.put("med_type", "11"); // ????????????
                }
                feedetail.put("chrg_bchno", "0"); // ???????????????
                feedetail.put("feedetl_sn", MapUtils.get(item, "id")); // ?????????????????????
                feedetail.put("psn_cert_type", insureSettleInfoDTO.getCertCode()); // ??????????????????
                feedetail.put("certno", insureSettleInfoDTO.getCertNo()); // ????????????
                feedetail.put("psn_name", insureSettleInfoDTO.getName()); // ????????????
                if (insureSettleInfoDTO.getLx().equals("1")) {

                    feedetail.put("fee_ocur_time", DateUtils.format((Date) item.get("costTime"), DateUtils.Y_M_DH_M_S)); // ??????????????????

                } else if (insureSettleInfoDTO.getLx().equals("0")) {
                    feedetail.put("fee_ocur_time", DateUtils.format((Date) item.get("crteTime"), DateUtils.Y_M_DH_M_S)); // ??????????????????

                }
                BigDecimal cnt = BigDecimalUtils.scale((BigDecimal) item.get("totalNum"), 4);
                BigDecimal price = BigDecimalUtils.scale((BigDecimal) item.get("price"), 4);
                feedetail.put("cnt", cnt); // ??????
                feedetail.put("pric", price); // ??????
                DecimalFormat df1 = new DecimalFormat("0.00");
                String realityPrice = df1.format(BigDecimalUtils.convert(item.get("realityPrice").toString()));
                BigDecimal convertPrice = BigDecimalUtils.convert(realityPrice);
                feedetail.put("det_item_fee_sumamt", convertPrice); // ????????????????????????
                feedetail.put("med_list_codg", item.get("insureItemCode") == null ? "" : item.get("insureItemCode").toString()); // ??????????????????
                feedetail.put("medins_list_codg", item.get("hospItemCode") == null ? "" : item.get("hospItemCode").toString()); // ????????????????????????
                feedetail.put("medins_list_name", MapUtils.get(item, "insureItemName")); // ????????????????????????
                feedetail.put("med_chrgitm_type", MapUtils.get(item, "insureItemType")); // ????????????????????????
                feedetail.put("prodname", MapUtils.get(item, "insureItemName")); // ?????????
                feedetail.put("bilg_dept_codg", MapUtils.get(item, "deptId")); // ??????????????????
                feedetail.put("bilg_dept_name", MapUtils.get(item, "deptName")); // ??????????????????
                if (StringUtils.isEmpty(MapUtils.get(item, "deptId"))) {
                    feedetail.put("bilg_dr_codg", doctorId); // ??????????????????
                } else {
                    feedetail.put("bilg_dr_codg", MapUtils.get(item, "deptId")); // ??????????????????
                }
                if (StringUtils.isEmpty(MapUtils.get(item, "doctorName"))) {
                    feedetail.put("bilg_dr_codg", doctorName); // ??????????????????
                } else {
                    feedetail.put("bilg_dr_name", MapUtils.get(item, "doctorName")); // ??????????????????
                }
                listMap.add(feedetail);

            }
        }
            /**
             * ????????????
             */
        Map param = new HashMap();
        param.put("infno", "4201");  //????????????
        param.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        param.put("insuplc_admdvs", ""); //????????????????????????
        param.put("medins_code", insureConfigurationDTO.getOrgCode()); //????????????????????????
        param.put("insur_code", insureConfigurationDTO.getRegCode()); //??????????????????
        param.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());// ?????????????????????
        for (Map map : listMap) {
            param.put("input", map);  //????????????
            String json = JSONObject.toJSONString(param);
            logger.info("????????????????????????????????????:" + json);
            String url = insureConfigurationDTO.getUrl();
            String resultJson = HttpConnectUtil.unifiedPayPostUtil(url, json);
            logger.info("????????????????????????????????????:" + resultJson);
            Map<String, Object> resultMap = JSONObject.parseObject(resultJson, Map.class);
            if (StringUtils.isEmpty(resultJson)) {
                throw new AppException("??????????????????????????????");
            }
            if ("999".equals(resultMap.get("infcode").toString())) {
                throw new AppException((String) resultMap.get("msg"));
            }
            if (!"0".equals(resultMap.get("infcode").toString())) {
                throw new AppException((String) resultMap.get("err_msg"));
            }
        }
        insertHandlerInsureCost(listMap, insureSettleInfoDTO);
        return true;
    }

    /**
     * @Author ????????????-?????????
     * @Date 2022-05-17 9:01
     * @Description ????????????????????????????????????
     * @param outptVisitDTO
     * @return cn.hsa.base.PageDTO
     */
    @Override
    public PageDTO queryInsureOutptMedicTreatMent(OutptVisitDTO outptVisitDTO) {
        // ??????????????????
        PageHelper.startPage(outptVisitDTO.getPageNo(),outptVisitDTO.getPageSize());
       /* int pageNo = outptVisitDTO.getPageNo();
        int pageSize = outptVisitDTO.getPageSize();
        int satrtIndex = 1;
        int endIndex = 1;
        if(pageNo==1){
            satrtIndex = 0;
            endIndex = pageSize;
        }else if(pageNo>1){
            satrtIndex = (pageNo-1)*pageSize;
            endIndex = pageNo*pageSize;
        }*/
        Map visitMap = new HashMap();
        visitMap.put("hospCode", outptVisitDTO.getHospCode());
        //???????????????????????????????????????
        List<OutptVisitDTO> outptVisitDTOS = outptVisitService_consumer.queryOutptVisitSelfFeePatient(visitMap);
        /*//??????????????????id??????
        List<String> ids = outptVisitDTOS.stream().map(OutptVisitDTO::getId).collect(Collectors.toList());
        //????????????????????????-??????id???ids???
        List<OutptDiagnoseDTO> outptDiagnoseDTOS = outptDoctorPrescribeDAO.queryOutptDiagnoseByVisitIds(ids);
        //??????????????????????????????-??????id???ids???
        List<OutptCostDTO> outptCostDTOS = outptCostDAO.queryOutptCostByvisitIds(ids);

        //????????????
        List<InsureOutptMedicTreatMentDTO> medicTreatMentDTOList = new ArrayList<>();
        for(OutptVisitDTO visitDTO:outptVisitDTOS){
            List<OutptDiagnoseDTO> diagnoseDTOList = new ArrayList<>();
            List<OutptCostDTO> costDTOList = new ArrayList<>();
            InsureOutptMedicTreatMentDTO medicTreatMentDTO = new InsureOutptMedicTreatMentDTO();
            //??????????????????
            for(OutptDiagnoseDTO diagnoseDTO:outptDiagnoseDTOS){
                if(visitDTO.getId().equals(diagnoseDTO.getVisitId())){
                    diagnoseDTOList.add(diagnoseDTO);
                }
            }
            //????????????????????????
            for(OutptCostDTO costDTO:outptCostDTOS){
                if(visitDTO.getId().equals(costDTO.getVisitId())){
                    costDTOList.add(costDTO);
                }
            }
            medicTreatMentDTO.setMdtrtinfo(visitDTO);
            medicTreatMentDTO.setDiseinfo(diagnoseDTOList);
            medicTreatMentDTO.setFeedetail(costDTOList);
            medicTreatMentDTOList.add(medicTreatMentDTO);
        }

        if(pageNo*pageSize>medicTreatMentDTOList.size()){
            endIndex = medicTreatMentDTOList.size();
        }
        List<InsureOutptMedicTreatMentDTO> dataList1 = medicTreatMentDTOList.subList(satrtIndex,endIndex);
        PageInfo pageInfo = new PageInfo(medicTreatMentDTOList);
        PageDTO dto = new PageDTO();
        dto = PageDTO.of(dataList1);
        dto.setTotal(pageInfo.getTotal());*/
        return PageDTO.of(outptVisitDTOS);
    }

    @Override
    public Boolean insertOutptMedicTreatMent(InsureSettleInfoDTO insureSettleInfoDTO) {
        if(StringUtils.isEmpty(insureSettleInfoDTO.getId())){
            throw  new AppException("????????????id?????????");
        }
        // 1.????????????????????????????????????
        InsureConfigurationDTO  insureConfigurationDTO = checkInsureConfig(insureSettleInfoDTO);

        List<OutptDiagnoseDTO> outptDiagnoseDTOList = new ArrayList<>();
        List<OutptDiagnoseDTO> outptMatchDiagnoseDTOList = new ArrayList<>();
        List<OutptCostDTO> outptCostDTOList = new ArrayList<>();
        List<Map<String, Object>> mapList = new ArrayList<>();
        OutptVisitDTO outptVisitDTO = new OutptVisitDTO();

        outptVisitDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        outptVisitDTO.setId(insureSettleInfoDTO.getId());

        Map map = new HashMap();
        map.put("id",insureSettleInfoDTO.getId());
        map.put("hospCode",insureSettleInfoDTO.getHospCode());
        outptVisitDTO = outptVisitService_consumer.queryByVisitID(map);
        //??????????????????
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        reqMap.put("outptVisitDTO", outptVisitDTO);
        String insureOrgCode = insureConfigurationDTO.getRegCode();
        outptVisitDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        outptVisitDTO.setVisitId(insureSettleInfoDTO.getId());
        outptVisitDTO.setInsureRegCode(insureOrgCode);
        List<String> diagnoseList = Stream.of("101","102","201","202","203","303","204").collect(Collectors.toList());
        outptVisitDTO.setDiagnoseList(diagnoseList);
        reqMap.put("outptVisitDTO", outptVisitDTO);
        outptDiagnoseDTOList = outptDoctorPrescribeService.queryOutptDiagnoseByVisitIds(reqMap).getData();
        if(ListUtils.isEmpty(outptDiagnoseDTOList)) {
            throw new AppException("??????????????????????????????");
        }
        /**
         * ????????????????????????????????????
         * 1.??????????????????????????????
         * 2.?????????????????????????????????
         */
        outptMatchDiagnoseDTOList = outptDoctorPrescribeService.queryOutptMatchDiagnose(reqMap).getData();
        mapList = handlerOutptCostFee(insureSettleInfoDTO);
        BigDecimal sum = new BigDecimal(0);
        if(!mapList.isEmpty() && mapList.size() > 0) {
            for(Map<String, Object> item : mapList){
                DecimalFormat df1 = new DecimalFormat("0.00");
                String realityPrice = df1.format(BigDecimalUtils.convert(item.get("realityPrice").toString()));
                BigDecimal convertPrice = BigDecimalUtils.convert(realityPrice);
                sum = sum.add(convertPrice);
            }
        }
        //????????????????????????
        //outptCostDTOList = outptVisitService_consumer.queryOutptCostByvisitIds(reqMap);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("insureSettleInfoDTO",insureSettleInfoDTO);

        paramMap.put("outptVisitDTO",outptVisitDTO);
        paramMap.put("outptDiagnoseDTOList",outptDiagnoseDTOList);
        paramMap.put("outptMatchDiagnoseDTOList",outptMatchDiagnoseDTOList);
        paramMap.put("outptCostDTOList",mapList);
        paramMap.put("medfeeSumamt",sum);

        paramMap.put("insureConfigurationDTO",insureConfigurationDTO);
        paramMap.put("visitId", insureSettleInfoDTO.getId());
        paramMap.put("hospCode",insureSettleInfoDTO.getHospCode());
        paramMap.put("configRegCode", insureConfigurationDTO.getRegCode());
        paramMap.put("orgCode", insureConfigurationDTO.getOrgCode());
        paramMap.put("isHospital", Constants.SF.S);
        //????????????,??????????????????????????????
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.FMI_OWNPAY_PATN_DISE_FEE_UPLOD.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        interfaceParamDTO.setIsHospital(Constants.SF.S);
        interfaceParamDTO.setVisitId(insureSettleInfoDTO.getId());
        // ??????????????????????????????
        insureItfBO.executeInsur(FunctionEnum.FMI_OWNPAY_PATN_DISE_FEE_UPLOD, interfaceParamDTO);
        //??????????????????????????????
        outptVisitDTO.setIsUploadDise("1");
        outptVisitDTO.setCpltFlag("0");
        Map paramMap1 = new HashMap();
        paramMap1.put("hospCode",insureSettleInfoDTO.getHospCode());
        paramMap1.put("outptVisitDTO",outptVisitDTO);
        outptVisitService_consumer.updateOutptVisitUploadFlag(paramMap1);
        return true;
    }

    @Override
    public Boolean deleteOutptMedicTreatMent(InsureSettleInfoDTO insureSettleInfoDTO) {
        if(StringUtils.isEmpty(insureSettleInfoDTO.getId())){
            throw  new AppException("????????????id?????????");
        }
        String hospCode = insureSettleInfoDTO.getHospCode();
        String insureRegCode = insureSettleInfoDTO.getInsureRegCode();
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(hospCode);
        insureConfigurationDTO.setRegCode(insureRegCode);
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if(insureConfigurationDTO == null){
            throw new AppException("????????????????????????????????????????????????????????????");
        }
        Map httpParam = new HashMap();
        httpParam.put("infno", Constant.UnifiedPay.REGISTER.UP_4206);  //????????????
        httpParam.put("insuplc_admdvs", insureConfigurationDTO.getRegCode()); //????????????????????????
        httpParam.put("medins_code", insureConfigurationDTO.getOrgCode()); //????????????????????????
        httpParam.put("insur_code", insureConfigurationDTO.getRegCode()); //??????????????????
        httpParam.put("mdtrtarea_admvs", insureConfigurationDTO.getMdtrtareaAdmvs());
        httpParam.put("msgid", StringUtils.createMsgId(insureConfigurationDTO.getOrgCode()));
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("fixmedins_mdtrt_id",insureSettleInfoDTO.getId()); // ??????????????????ID
        paramMap.put("fixmedins_code",insureConfigurationDTO.getOrgCode()); //????????????????????????

        Map<String, Object> inputMap = new HashMap<>();
        //inputMap.put("data", paramMap);
        httpParam.put("input", paramMap);
        String json = JSONObject.toJSONString(httpParam);
        logger.info("????????????????????????????????????:" + json);
        String url = insureConfigurationDTO.getUrl();
        String resultJson = HttpConnectUtil.unifiedPayPostUtil(url, json);
        Map<String, Object> resultMap = JSONObject.parseObject(resultJson);
        if (!"0".equals(resultMap.get("infcode").toString())) {
            throw new AppException((String) resultMap.get("err_msg"));
        }
        logger.info("????????????????????????????????????:" + resultJson);

        //??????????????????
        Map paramMap1 = new HashMap();
        OutptVisitDTO outptVisitDTO = new OutptVisitDTO();
        outptVisitDTO.setIsUploadDise("0");
        outptVisitDTO.setCpltFlag("0");
        outptVisitDTO.setId(insureSettleInfoDTO.getId());
        outptVisitDTO.setHospCode(hospCode);
        paramMap1.put("outptVisitDTO",outptVisitDTO);
        paramMap1.put("hospCode",hospCode);
        outptVisitService_consumer.updateOutptVisitUploadFlag(paramMap1);
        return true;
    }


    /**
     * @return
     * @Method getInsureCost
     * @Desrciption ?????? -- 4202?????????????????????????????????????????????
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-11 22:54
     * @Return java.util.Map
     */
    @Override
    public Boolean insertInsureMdtrtAndDiag(InsureSettleInfoDTO insureSettleInfoDTO) {
        // 1.????????????????????????????????????
        InsureConfigurationDTO  insureConfigurationDTO = checkInsureConfig(insureSettleInfoDTO);
        insureSettleInfoDTO.setOrgCode(insureConfigurationDTO.getCode());
        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        List<Map<String, Object>> mapList = new ArrayList<>();
        List<InptDiagnoseDTO> inptDiagnoseDTOList = new ArrayList<>();
        List<InptDiagnoseDTO> inptMatchDiagnoseDTOList = new ArrayList<>();
        inptVisitDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        inptVisitDTO.setId(insureSettleInfoDTO.getId());
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        reqMap.put("inptVisitDTO", inptVisitDTO);
        inptVisitDTO = inptVisitService_consumer.getInptVisitById(reqMap).getData();
        if(inptVisitDTO == null){
            throw new AppException("?????????????????????????????????");
        }
        String insureOrgCode = insureConfigurationDTO.getRegCode();
        inptVisitDTO.setInsureRegCode(insureOrgCode);
        List<String> diagnoseList = Stream.of("101","102","201","202","203","303","204").collect(Collectors.toList());
        inptVisitDTO.setVisitId(insureSettleInfoDTO.getId());
        inptVisitDTO.setDiagnoseList(diagnoseList);
        reqMap.put("inptVisitDTO",inptVisitDTO);
        inptDiagnoseDTOList = doctorAdviceService_consumer.getInptDiagnose(reqMap).getData();
        if(ListUtils.isEmpty(inptDiagnoseDTOList)) {
            throw new AppException("??????????????????????????????");
        }
        /**
         * ????????????????????????????????????
         * 1.??????????????????????????????
         * 2.?????????????????????????????????
         */
        inptMatchDiagnoseDTOList = doctorAdviceService_consumer.queryInptDiagnose(reqMap).getData();
        mapList = handlerInptCostFee(insureSettleInfoDTO);
        BigDecimal sum = new BigDecimal(0);
        List<InsureUploadCostDTO> costDTOList = insureGetInfoDAO.queryAll(insureSettleInfoDTO);
        if(!mapList.isEmpty() && mapList.size() > 0) {
            for(Map<String, Object> item : mapList){
                DecimalFormat df1 = new DecimalFormat("0.00");
                String realityPrice = df1.format(BigDecimalUtils.convert(item.get("realityPrice").toString()));
                BigDecimal convertPrice = BigDecimalUtils.convert(realityPrice);
                sum = sum.add(convertPrice);
            }
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("insureSettleInfoDTO",insureSettleInfoDTO);

        paramMap.put("inptVisitDTO",inptVisitDTO);
        paramMap.put("inptDiagnoseDTOList",inptDiagnoseDTOList);
        paramMap.put("inptMatchDiagnoseDTOList",inptMatchDiagnoseDTOList);
        paramMap.put("medfeeSumamt",sum);

        paramMap.put("insureConfigurationDTO",insureConfigurationDTO);
        paramMap.put("visitId", insureSettleInfoDTO.getId());
        paramMap.put("hospCode",insureSettleInfoDTO.getHospCode());
        paramMap.put("configRegCode", insureConfigurationDTO.getRegCode());
        paramMap.put("orgCode", insureConfigurationDTO.getOrgCode());
        paramMap.put("isHospital", Constants.SF.S);
        //????????????,??????????????????????????????
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.FMI_OWNPAY_PATN_DISE_UPLOD.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        interfaceParamDTO.setIsHospital(Constants.SF.S);
        interfaceParamDTO.setVisitId(insureSettleInfoDTO.getId());
        // ??????????????????????????????
        insureItfBO.executeInsur(FunctionEnum.FMI_OWNPAY_PATN_DISE_UPLOD, interfaceParamDTO);

        //????????????
        inptVisitDTO.setIsUplodDise("1");
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        updateMap.put("inptVisitDTO", inptVisitDTO);
        inptVisitService_consumer.updateUplod(updateMap);
        return true;
    }

    /**
     * @return
     * @Method getInsureCost
     * @Desrciption ?????? -- 4203????????????????????????????????????????????????
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-11 22:54
     * @Return java.util.Map
     */
    @Override
    public Boolean insertInsureFinish(InsureSettleInfoDTO insureSettleInfoDTO) {
        // 1.????????????????????????????????????
        InsureConfigurationDTO  insureConfigurationDTO = checkInsureConfig(insureSettleInfoDTO);
        Map<String, Object> paramMap = new HashMap<>();
        if(StringUtils.isEmpty(insureSettleInfoDTO.getCpltFlag())){
            throw new AppException("?????????????????????");
        }
        // 2.??????????????????????????? ?????? ??????????????????  ??????????????????
        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        OutptVisitDTO outptVisitDTO = new OutptVisitDTO();

        if (insureSettleInfoDTO.getLx().equals("1")) {
            inptVisitDTO.setHospCode(insureSettleInfoDTO.getHospCode());
            inptVisitDTO.setId(insureSettleInfoDTO.getId());
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("hospCode", insureSettleInfoDTO.getHospCode());
            reqMap.put("inptVisitDTO", inptVisitDTO);
            inptVisitDTO = inptVisitService_consumer.getInptVisitById(reqMap).getData();
            inptVisitDTO.setCpltFlag("1");

        } else if (insureSettleInfoDTO.getLx().equals("0")) {
            Map<String, String> visitMap = new HashMap();
            visitMap.put("id",insureSettleInfoDTO.getId());
            visitMap.put("hospCode",insureSettleInfoDTO.getHospCode());

            outptVisitDTO = outptVisitService_consumer.queryByVisitID(visitMap);
            outptVisitDTO.setCpltFlag("1");
        }
        paramMap.put("insureConfigurationDTO",insureConfigurationDTO);
        paramMap.put("cpltFlag", insureSettleInfoDTO.getCpltFlag());
        paramMap.put("visitId", insureSettleInfoDTO.getId());
        paramMap.put("hospCode",insureSettleInfoDTO.getHospCode());
        paramMap.put("configRegCode", insureConfigurationDTO.getRegCode());
        paramMap.put("orgCode", insureConfigurationDTO.getOrgCode());
        paramMap.put("isHospital", insureSettleInfoDTO.getLx());
        //????????????,??????????????????????????????
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.FMI_OWNPAY_PATN_FINISH.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        interfaceParamDTO.setIsHospital(insureSettleInfoDTO.getLx());
        interfaceParamDTO.setVisitId(insureSettleInfoDTO.getId());
        // ??????????????????????????????
        insureItfBO.executeInsur(FunctionEnum.FMI_OWNPAY_PATN_FINISH, interfaceParamDTO);
        //????????????
        if(insureSettleInfoDTO.getLx().equals("1")){
            Map<String, Object> updateMap = new HashMap<>();
            updateMap.put("hospCode", insureSettleInfoDTO.getHospCode());
            updateMap.put("inptVisitDTO", inptVisitDTO);
            inptVisitService_consumer.updateUplod(updateMap);
        }
        if(insureSettleInfoDTO.getLx().equals("0")){
            Map<String, Object> updateMap = new HashMap<>();
            updateMap.put("hospCode", insureSettleInfoDTO.getHospCode());
            updateMap.put("outptVisitDTO",outptVisitDTO);
            outptVisitService_consumer.updateUplod(updateMap);
        }
        return true;
    }

    /**
     * @Method insertInsureFmiOwnPayPatnCost
     * @Desrciption ????????????????????????????????????
     * 1.?????????????????????????????????
     * 2.????????????????????????????????????
     * 3.??????????????????
     * 4.??????????????????
     * @Param
     * [insureSettleInfoDTO]
     * @Author yuelong.chen
     * @Date   2022-05-17 13:51
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
     **/
    @Override
    public Boolean deleteInsureFmiOwnPayPatnCost(InsureSettleInfoDTO insureSettleInfoDTO) {
        // 1.????????????????????????????????????
        InsureConfigurationDTO  insureConfigurationDTO = checkInsureConfig(insureSettleInfoDTO);
//        insureSettleInfoDTO.setOrgCode(insureConfigurationDTO.getCode());
        //1.?????????????????????????????????
        List<InsureUploadCostDTO> costDTOList = insureIndividualCostDAO.queryFeeInfoDetailPage(insureSettleInfoDTO);
        if(ListUtils.isEmpty(costDTOList)){
            throw new AppException("??????????????????????????????!");
        }
        //2.????????????
        Map paramMap = new HashMap();
        paramMap.put("orgCode", insureConfigurationDTO.getOrgCode());
        paramMap.put("hospCode", insureConfigurationDTO.getHospCode());
        paramMap.put("configRegCode",insureConfigurationDTO.getRegCode());
        //??????????????????
        Map<String, Object> feedetailMap = new HashMap<>();
        feedetailMap.put("fixmedins_mdtrt_id", insureSettleInfoDTO.getVisitId());
        feedetailMap.put("fixmedins_code", insureConfigurationDTO.getOrgCode());
        paramMap.put("feedetail",feedetailMap);
        //???????????????
        List<String> feeIdList = insureSettleInfoDTO.getFeeIdList();
        if(feeIdList == null ){
            throw new AppException("??????????????????????????????");
        }
        if(feeIdList != null && insureSettleInfoDTO.getFeeIdList().size() != costDTOList.size()){
            List<Map<String,Object>> bkkpSnList = new ArrayList<>();
            for (String s : feeIdList) {
                Map<String,Object> bkkpMap = new HashMap<>();
                bkkpMap.put("bkkp_sn",s);
                bkkpSnList.add(bkkpMap);
            }
            paramMap.put("feedetl",bkkpSnList);
        }
        //????????????,??????????????????????????????
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.FMI_OWNPAY_PATN_DELETE.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        interfaceParamDTO.setIsHospital(insureSettleInfoDTO.getLx());
        interfaceParamDTO.setVisitId(insureSettleInfoDTO.getId());
        // ??????????????????????????????
        Map<String, Object> res = insureItfBO.executeInsur(FunctionEnum.FMI_OWNPAY_PATN_DELETE, interfaceParamDTO);
        //??????????????????
        insureGetInfoDAO.deleteFmiOwnPayPatnCost(feeIdList,insureSettleInfoDTO.getHospCode());
        //????????????????????????????????????
        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        inptVisitDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        inptVisitDTO.setId(insureSettleInfoDTO.getVisitId());
        inptVisitDTO.setIsUplodCost("0");
        inptVisitDTO.setCpltFlag("0");
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        updateMap.put("inptVisitDTO", inptVisitDTO);
        inptVisitService_consumer.updateUplod(updateMap);

        return true;
    }

    @Override
    public PageDTO queryFeeInfoDetailPage(InsureSettleInfoDTO insureSettleInfoDTO) {
        PageHelper.startPage(insureSettleInfoDTO.getPageNo(), insureSettleInfoDTO.getPageSize());
        List<InsureUploadCostDTO> costDTOList = insureIndividualCostDAO.queryFeeInfoDetailPage(insureSettleInfoDTO);
        return PageDTO.of(costDTOList);
    }


    @Override
    public PageDTO queryFmiOwnPayInfoDetail(InsureSettleInfoDTO insureSettleInfoDTO) {
        // 1.????????????????????????????????????
        InsureConfigurationDTO insureConfigurationDTO = checkInsureConfig(insureSettleInfoDTO);
        insureSettleInfoDTO.setOrgCode(insureConfigurationDTO.getCode());
        // 2.??????????????????????????????
        Map<String, Object> paramMap = new HashMap<>();
        logger.info("????????????" + insureSettleInfoDTO + paramMap.toString());
        paramMap.put("psn_cert_type", insureSettleInfoDTO.getPsnCertType());
        paramMap.put("certno", insureSettleInfoDTO.getCertNo());
        paramMap.put("psn_name", insureSettleInfoDTO.getPsnName());
        paramMap.put("begntime", insureSettleInfoDTO.getBegntime());
        paramMap.put("endtime", insureSettleInfoDTO.getEndtime());
        paramMap.put("medfee_sumamt", insureSettleInfoDTO.getMedfeeSumamt());
        paramMap.put("elec_billno_code", insureSettleInfoDTO.getElecBillnoCode());
        paramMap.put("cplt_flag", insureSettleInfoDTO.getCpltFlag());
        paramMap.put("page_num", insureSettleInfoDTO.getPageNo());
        paramMap.put("page_size", insureSettleInfoDTO.getPageSize());
        //?????????????????????????????????
        Map<String, Object> stringObjectMap = unifiedCommon.commonInsureUnified(insureSettleInfoDTO.getHospCode(), insureSettleInfoDTO.getInsureRegCode(), Constant.UnifiedPay.REGISTER.UP_4208, paramMap);
        //??????????????????
        Map<String, Object> resultMap = MapUtils.get(stringObjectMap, "output");
        List<FmiOwnpayPatnMdtrtDDTO> resultList = new ArrayList<>();
        if (!MapUtils.isEmpty(resultMap)) {
            resultList = MapUtils.get(resultMap, "data");
        }

        return PageDTO.of(resultList);
    }

    @Override
    public PageDTO queryFmiOwnPayPatnFeeListDetail(InsureSettleInfoDTO insureSettleInfoDTO) {
        // 1.????????????????????????????????????
        InsureConfigurationDTO insureConfigurationDTO = checkInsureConfig(insureSettleInfoDTO);
        insureSettleInfoDTO.setOrgCode(insureConfigurationDTO.getCode());
        // 2.????????????????????????????????????
        Map<String, Object> paramMap = new HashMap<>();
        logger.info("????????????" + insureSettleInfoDTO);
        paramMap.put("fixmedins_mdtrt_id", insureSettleInfoDTO.getFixmedinsMdtrtId());
        paramMap.put("fixmedins_code", insureSettleInfoDTO.getFixmedinsCode());
        paramMap.put("page_num", insureSettleInfoDTO.getPageNo());
        paramMap.put("page_size", insureSettleInfoDTO.getPageSize());

        //?????????????????????????????????
        Map<String, Object> stringObjectMap = unifiedCommon.commonInsureUnified(insureSettleInfoDTO.getHospCode(), insureSettleInfoDTO.getInsureRegCode(), Constant.UnifiedPay.REGISTER.UP_4207, paramMap);
        //??????????????????
        Map<String, Object> resultMap = MapUtils.get(stringObjectMap, "output");
        List<FmiOwnpayPatnFeeListDDTO> resultLists = new ArrayList<>();
        resultLists = MapUtils.get(resultMap, "data");
        return PageDTO.of(resultLists);
    }

    @Override
    public PageDTO queryFmiOwnPayDiseListDetail(InsureSettleInfoDTO insureSettleInfoDTO) {
        // 1.????????????????????????????????????
        InsureConfigurationDTO insureConfigurationDTO = checkInsureConfig(insureSettleInfoDTO);
        insureSettleInfoDTO.setOrgCode(insureConfigurationDTO.getCode());
        // 2.????????????????????????????????????
        Map<String, Object> paramMap = new HashMap<>();
        logger.info("????????????" + insureSettleInfoDTO);
        paramMap.put("fixmedins_mdtrt_id", insureSettleInfoDTO.getFixmedinsMdtrtId());
        paramMap.put("fixmedins_code", insureSettleInfoDTO.getFixmedinsCode());
        paramMap.put("page_num", insureSettleInfoDTO.getPageNo());
        paramMap.put("page_size", insureSettleInfoDTO.getPageSize());

        //???????????? ?????????????????????
        Map<String, Object> stringObjectMap = unifiedCommon.commonInsureUnified(insureSettleInfoDTO.getHospCode(), insureSettleInfoDTO.getInsureRegCode(), Constant.UnifiedPay.REGISTER.UP_4209, paramMap);
        //??????????????????
        Map<String, Object> outputMap = MapUtils.get(stringObjectMap, "output");
        List<FmiOwnpayPatnDiseListDDTO> resultList = MapUtils.get(outputMap, "data");
        return PageDTO.of(resultList);
    }

    @Override
    public Boolean insertInsureInputCost(InsureSettleInfoDTO insureSettleInfoDTO) {
        // 1.????????????????????????????????????
        InsureConfigurationDTO insureConfigurationDTO = checkInsureConfig(insureSettleInfoDTO);
        insureSettleInfoDTO.setOrgCode(insureConfigurationDTO.getCode());
        //??????????????????
        InptVisitDTO inptVisitDTO = new InptVisitDTO();
        inptVisitDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        inptVisitDTO.setId(insureSettleInfoDTO.getId());
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        reqMap.put("inptVisitDTO", inptVisitDTO);
        inptVisitDTO = inptVisitService_consumer.getInptVisitById(reqMap).getData();
        if(inptVisitDTO == null){
            throw new AppException("?????????????????????????????????");
        }
        //????????????
        List<Map<String, Object>> mapList = new ArrayList<>();
        mapList = handlerInptCostFee(insureSettleInfoDTO);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("insureSettleInfoDTO",insureSettleInfoDTO);
        paramMap.put("insureConfigurationDTO",insureConfigurationDTO);
        paramMap.put("mapList",mapList);
        paramMap.put("visitId", insureSettleInfoDTO.getId());
        paramMap.put("hospCode",insureSettleInfoDTO.getHospCode());
        paramMap.put("configRegCode", insureConfigurationDTO.getRegCode());
        paramMap.put("orgCode", insureConfigurationDTO.getOrgCode());
        paramMap.put("isHospital", Constants.SF.F);
        //????????????,??????????????????????????????
        BaseReqUtil reqUtil = baseReqUtilFactory.getBaseReqUtil("newInsure" + FunctionEnum.FMI_OWNPAY_PATN_INPUT_UPLOD.getCode());
        InsureInterfaceParamDTO interfaceParamDTO = reqUtil.initRequest(paramMap);
        interfaceParamDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        interfaceParamDTO.setIsHospital(insureSettleInfoDTO.getLx());
        interfaceParamDTO.setVisitId(insureSettleInfoDTO.getId());
        // ??????????????????????????????
        insureItfBO.executeInsur(FunctionEnum.FMI_OWNPAY_PATN_INPUT_UPLOD, interfaceParamDTO);

        //????????????
        inptVisitDTO.setIsUplodCost("1");
        inptVisitDTO.setCpltFlag("0");
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        updateMap.put("inptVisitDTO", inptVisitDTO);
        inptVisitService_consumer.updateUplod(updateMap);
        insertHandlerInsureCost(mapList, insureSettleInfoDTO);
        return true;
    }

    /**
     * @Method insertHandlerInsureCost
     * @Desrciption ??????????????????????????????
     * @Param item ???????????????
     * infoDTO
     * @Author fuhui
     * @Date 2021/8/21 13:57
     * @Return
     **/
    private void insertHandlerInsureCost(List<Map<String, Object>> feeList, InsureSettleInfoDTO infoDTO) {
        List<InsureUploadCostDTO> insureIndividualCostDOList = new ArrayList<>();
        for(Map<String, Object> item : feeList){
            InsureUploadCostDTO insureUploadCostDTO = new InsureUploadCostDTO();
            insureUploadCostDTO.setId(SnowflakeUtils.getId());//id
            insureUploadCostDTO.setHospCode(infoDTO.getHospCode());//????????????
            insureUploadCostDTO.setVisitId(infoDTO.getId());//??????id

            insureUploadCostDTO.setMdtrtSn("");
            insureUploadCostDTO.setIptOtpNo("");
            insureUploadCostDTO.setMedType("");
            insureUploadCostDTO.setChrgBchno("");
            insureUploadCostDTO.setPsnCertType("");
            insureUploadCostDTO.setCertno("");
            insureUploadCostDTO.setPsnName("");
            insureUploadCostDTO.setOrgCode(infoDTO.getInsureRegCode());
            insureUploadCostDTO.setCostId(MapUtils.get(item, "id"));//??????id

            insureUploadCostDTO.setCrteId(infoDTO.getCrteId());//??????id
            insureUploadCostDTO.setCrteName(infoDTO.getCrteName());//???????????????
            insureUploadCostDTO.setCrteTime(new Date());//????????????

            String doctorId = MapUtils.get(item, "doctorId");
            String doctorName = MapUtils.get(item, "doctorName");
            insureUploadCostDTO.setFeedetlSn(MapUtils.get(item, "id"));
            if (infoDTO.getLx().equals("1")) {
                insureUploadCostDTO.setFeeOcurTime((Date) item.get("costTime")); // ??????????????????
            } else if (infoDTO.getLx().equals("0")) {
                insureUploadCostDTO.setFeeOcurTime((Date) item.get("crteTime")); // ??????????????????
            }

            BigDecimal cnt = BigDecimalUtils.scale((BigDecimal) item.get("totalNum"), 4);
            BigDecimal price = BigDecimalUtils.scale((BigDecimal) item.get("price"), 4);
            insureUploadCostDTO.setCnt(cnt); // ??????
            insureUploadCostDTO.setPric(price); // ??????

            DecimalFormat df1 = new DecimalFormat("0.00");
            String realityPrice = df1.format(BigDecimalUtils.convert(item.get("realityPrice").toString()));
            BigDecimal convertPrice = BigDecimalUtils.convert(realityPrice);
            insureUploadCostDTO.setDetItemFeeSumamt(convertPrice); // ????????????????????????

            insureUploadCostDTO.setMedListCodg(item.get("insureItemCode") == null ? "" : item.get("insureItemCode").toString()); // ??????????????????
            insureUploadCostDTO.setMedinsListCodg(item.get("hospItemCode") == null ? "" : item.get("hospItemCode").toString()); // ????????????????????????
            insureUploadCostDTO.setMedinsListName(MapUtils.get(item, "insureItemName")); // ????????????????????????
            insureUploadCostDTO.setMedChrgitmType(item.get("insureItemType") == null ? "" : item.get("insureItemType").toString()); // ????????????????????????
            insureUploadCostDTO.setProdname(MapUtils.get(item, "insureItemName")); // ?????????

            insureUploadCostDTO.setBilgDeptCodg(MapUtils.get(item, "deptId")); // ??????????????????
            insureUploadCostDTO.setBilgDeptName(MapUtils.get(item, "deptName")); // ??????????????????

            if (StringUtils.isEmpty(MapUtils.get(item, "deptId"))) {
                insureUploadCostDTO.setBilgDrCodg( doctorId); // ??????????????????
            } else {
                insureUploadCostDTO.setBilgDrCodg( MapUtils.get(item, "deptId")); // ??????????????????
            }
            if (StringUtils.isEmpty(MapUtils.get(item, "doctorName"))) {
                insureUploadCostDTO.setBilgDrName( doctorName); // ??????????????????
            } else {
                insureUploadCostDTO.setBilgDrName( MapUtils.get(item, "doctorName")); // ??????????????????
            }
            insureIndividualCostDOList.add(insureUploadCostDTO);
        }
        insureGetInfoDAO.deleteCost(infoDTO.getId(),infoDTO.getHospCode());
        insureGetInfoDAO.insertCost(insureIndividualCostDOList);
    }

    /**
     * @Method checkInsureConfig
     * @Desrciption ?????????????????????????????????
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
            throw new AppException("???????????????????????????????????????????????????:?????????:HOSP_INSURE_CODE,????????????????????????????????????");
        }
        if (insureSettleInfoDTO.getInsureRegCode() == null) {
            throw new AppException("????????????????????????");
        }
        InsureConfigurationDTO insureConfigurationDTO = new InsureConfigurationDTO();
        insureConfigurationDTO.setHospCode(insureSettleInfoDTO.getHospCode());
        insureConfigurationDTO.setOrgCode(sysParameterDTO.getValue());
        insureConfigurationDTO.setRegCode(insureSettleInfoDTO.getInsureRegCode());
        insureConfigurationDTO = insureConfigurationDAO.queryInsureIndividualConfig(insureConfigurationDTO);
        if (insureConfigurationDTO == null) {
            throw new AppException("??????????????????????????????");
        }
        return insureConfigurationDTO;
    }

    /**
     * @Method handlerOutptCostFee
     * @Desrciption ????????????????????????
     * @Param
     * @Author fuhui
     * @Date 2021/8/21 10:54
     * @Return
     **/
    private List<Map<String, Object>> handlerOutptCostFee(InsureSettleInfoDTO insureSettleInfoDTO) {

        String insureRegCode = insureSettleInfoDTO.getInsureRegCode();
        //?????????????????????????????????
        Map<String, Object> insureCostParam = new HashMap<String, Object>();
        insureCostParam.put("hospCode", insureSettleInfoDTO.getHospCode());//????????????
        insureCostParam.put("statusCode", Constants.ZTBZ.ZC);//???????????? = ??????
        insureCostParam.put("visitId", insureSettleInfoDTO.getId());//??????id
        insureCostParam.put("isMatch", Constants.SF.S);//???????????? = ???
//        insureCostParam.put("transmitCode", Constants.SF.F);//???????????? = ?????????
        insureCostParam.put("insureRegCode", insureRegCode);// ??????????????????
        insureCostParam.put("isHospital", Constants.SF.F); // ????????????????????????
        insureCostParam.put("settle_code", "2");//??????????????????
        List<Map<String, Object>> insureCostList = insureIndividualCostDAO.queryOutptInsureCostByVisit(insureCostParam);
        if (ListUtils.isEmpty(insureCostList)) {
            throw new AppException("????????????????????????????????????");
        }
        return insureCostList;
    }

    /**
     * @Method handlerInptCostFee
     * @Desrciption ????????????????????????????????????
     * @Param insureSettleInfoDTO
     * @Author fuhui
     * @Date 2021/8/21 10:38
     * @Return
     **/
    private List<Map<String, Object>> handlerInptCostFee(InsureSettleInfoDTO insureSettleInfoDTO) {
        Map<String, String> insureCostParam = new HashMap<String, String>();
        String hospCode = insureSettleInfoDTO.getHospCode();
        String visitId = insureSettleInfoDTO.getId();
        insureCostParam.put("hospCode", hospCode);//????????????
        insureCostParam.put("statusCode", Constants.ZTBZ.ZC);//???????????? = ??????
        insureCostParam.put("visitId", visitId);//??????id
        insureCostParam.put("isMatch", Constants.SF.S);//???????????? = ???
//        insureCostParam.put("transmitCode", Constants.SF.F);//???????????? = ?????????
        insureCostParam.put("insureRegCode", insureSettleInfoDTO.getOrgCode());// ??????????????????
        insureCostParam.put("queryBaby", "N");// ??????????????????
        insureCostParam.put("settle_code", "2");//??????????????????
        List<Map<String, Object>> insureCostList = insureIndividualCostDAO.queryInsureCostByVisit(insureCostParam);
        if (ListUtils.isEmpty(insureCostList)) {
            throw new AppException("????????????????????????????????????????????????");
        }
        InsureIndividualVisitDTO insureIndividualVisitDTO = new InsureIndividualVisitDTO();
        insureIndividualVisitDTO.setHospCode(hospCode);
        insureIndividualVisitDTO.setVisitId(visitId);
        // ??????????????????????????????,??????????????????????????????
        List<InptCostDTO> inptCostDTOList = insureIndividualCostDAO.queryBackInptFee(insureIndividualVisitDTO);
        // ?????????????????????????????????
        List<Map<String, Object>> individualCostDTOList = insureIndividualCostDAO.queryInsureInptCost(insureIndividualVisitDTO);
        int num = 0;
        /**
         * ?????????????????????????????????????????????,????????????????????????????????????????????????????????????????????????
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
            // ??????????????????????????????????????????????????? ????????????????????????????????????
            insertNotUpLoadFee(insureCostList, insureSettleInfoDTO);
            return new ArrayList<>();
        } else {
            // ???????????????????????? ????????????????????????
            List<Map<String, Object>> list2 = new ArrayList<>();  // ??????
            List<Map<String, Object>> list1 = new ArrayList<>();
            List<Map<String, Object>> list3 = new ArrayList<>(); // ?????????????????????????????????
            if (!ListUtils.isEmpty(inptCostDTOList)) {
                Map<String, InptCostDTO> collect = inptCostDTOList.stream().collect(Collectors.toMap(InptCostDTO::getOldCostId, Function.identity()));
                // ??????????????????    ??????????????????????????? 10???  ???4???     ??????????????? ???  ???-10  ???6
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
                // ????????????????????????
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
     * @Desrciption ??????????????????????????????
     * @Param insureCostList???????????????????????????
     * inptVisitDTO ?????????????????????
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
            insureIndividualCostDO.setHospCode(hospCode);//????????????
            insureIndividualCostDO.setVisitId(visitId);//??????id
            insureIndividualCostDO.setCostId((String) item.get("id"));//??????id
            insureIndividualCostDO.setSettleId(null);//??????id
            insureIndividualCostDO.setIsHospital(Constants.SF.S);//???????????? = ???
            insureIndividualCostDO.setItemType((String) item.get("insureItemType"));//??????????????????
            insureIndividualCostDO.setItemCode((String) item.get("insureItemCode"));//??????????????????
            insureIndividualCostDO.setItemName((String) item.get("insureItemName"));//??????????????????
            insureIndividualCostDO.setGuestRatio((String) item.get("deductible"));//????????????
            insureIndividualCostDO.setPrimaryPrice((BigDecimal) item.get("realityPrice"));//?????????
            insureIndividualCostDO.setApplyLastPrice(null);//???????????????
            insureIndividualCostDO.setOrderNo(null);//?????????
            insureIndividualCostDO.setInsureIsTransmit(Constants.SF.F);
            insureIndividualCostDO.setTransmitCode(Constants.SF.S);//???????????? = ?????????
            insureIndividualCostDO.setCrteId(crteId);//??????id
            insureIndividualCostDO.setCrteName(crteName);//???????????????
            insureIndividualCostDO.setCrteTime(new Date());//????????????
            insureIndividualCostDOList.add(insureIndividualCostDO);
        }
        insureIndividualCostDAO.insertInsureCost(insureIndividualCostDOList);
    }


    /**
     * @Method queryUploadCost
     * @Desrciption
     * @Param [insureSettleInfoDTO]
     * @Author zhangxuan
     * @Date 2021-04-13 19:09
     * @Return cn.hsa.base.PageDTO
     **/
    public PageDTO queryUploadCost(InsureSettleInfoDTO insureSettleInfoDTO) {

        List<InsureUploadCostDTO> costDTOList = new ArrayList();

        PageHelper.startPage(insureSettleInfoDTO.getPageNo(), insureSettleInfoDTO.getPageSize());
        costDTOList = insureGetInfoDAO.queryAll(insureSettleInfoDTO);

        return PageDTO.of(costDTOList);
    }




}
