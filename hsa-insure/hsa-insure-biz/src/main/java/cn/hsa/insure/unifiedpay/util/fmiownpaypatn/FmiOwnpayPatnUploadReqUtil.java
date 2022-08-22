package cn.hsa.insure.unifiedpay.util.fmiownpaypatn;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.fmiownpaypatn.dto.FmiOwnpayPatnDiseListDDTO;
import cn.hsa.module.insure.fmiownpaypatn.dto.FmiOwnpayPatnFeeListDDTO;
import cn.hsa.module.insure.fmiownpaypatn.dto.FmiOwnpayPatnMdtrtDDTO;
import cn.hsa.module.insure.module.dao.InsureGetInfoDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.module.insure.module.dto.InsureSettleInfoDTO;
import cn.hsa.module.insure.module.dto.InsureUploadCostDTO;
import cn.hsa.module.insure.module.dto.ItemInfoDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.BigDecimalUtils;
import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import cn.hsa.util.ListUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName PsnInfoReqUtil
 * @Deacription 人员信息获取-1101
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_4261)
public class FmiOwnpayPatnUploadReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Resource
    private SysParameterService sysParameterService_consumer;
    @Resource
    private InsureGetInfoDAO insureGetInfoDAO;

    @Override
    public InsureInterfaceParamDTO initRequest(T param) {
        Map map = (Map) param;
        InsureConfigurationDTO insureConfigurationDTO =  (InsureConfigurationDTO) map.get("insureConfigurationDTO");
        InptVisitDTO inptVisitDTO = (InptVisitDTO) map.get("inptVisitDTO");
        List<InptDiagnoseDTO> inptDiagnoseDTOList = (List<InptDiagnoseDTO>) map.get("inptDiagnoseDTOList");
        List<InptDiagnoseDTO> inptMatchDiagnoseDTOList = (List<InptDiagnoseDTO>) map.get("inptMatchDiagnoseDTOList");

        OutptVisitDTO outptVisitDTO = (OutptVisitDTO) map.get("outptVisitDTO");
        List<OutptDiagnoseDTO> outptDiagnoseDTOList = (List<OutptDiagnoseDTO>) map.get("outptDiagnoseDTOList");
        List<OutptDiagnoseDTO> outptMatchDiagnoseDTOList = (List<OutptDiagnoseDTO>) map.get("outptMatchDiagnoseDTOList");

        InsureSettleInfoDTO insureSettleInfoDTO = (InsureSettleInfoDTO) map.get("insureSettleInfoDTO");

        List<Map<String, Object>> feeList = MapUtils.get(map, "mapList");

        Map<String, Object> isInsureUnifiedMap = new HashMap<>();
        isInsureUnifiedMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        isInsureUnifiedMap.put("code", "JG_NAME");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
        if (sysParameterDTO == null) {
            throw new AppException("请先配置默认的医疗机构编码参数信息:编码为:HOSP_INSURE_CODE,值为对应的医疗机构编码值");
        }

        Map<String, Object> dataMap = new HashMap<>(3);
        if (insureSettleInfoDTO.getLx().equals("1")) {
            dataMap.put("fmiOwnpayPatnMdtrtDDTO", initMdtrtDDTO(inptVisitDTO,insureConfigurationDTO,sysParameterDTO.getValue()));
            dataMap.put("fmiOwnpayPatnDiseListDDTOS", initInptDiseListDDTOS(inptDiagnoseDTOList,inptMatchDiagnoseDTOList,insureConfigurationDTO,sysParameterDTO.getValue()));
            dataMap.put("fmiOwnpayPatnFeeListDDTO",initFeeListDDTO(insureSettleInfoDTO, feeList,insureConfigurationDTO,sysParameterDTO.getValue()));
        }else {
            FmiOwnpayPatnMdtrtDDTO fmiOwnpayPatnMdtrtDDTO = initMdtrtDDTO(outptVisitDTO,insureConfigurationDTO,sysParameterDTO.getValue());
            dataMap.put("fmiOwnpayPatnMdtrtDDTO", fmiOwnpayPatnMdtrtDDTO);
            dataMap.put("fmiOwnpayPatnDiseListDDTOS", initOutptDiseListDDTOS(outptDiagnoseDTOList,outptMatchDiagnoseDTOList,insureConfigurationDTO,sysParameterDTO.getValue(),
                    fmiOwnpayPatnMdtrtDDTO));
            dataMap.put("fmiOwnpayPatnFeeListDDTO", initFeeListDDTO(insureSettleInfoDTO, feeList,insureConfigurationDTO,sysParameterDTO.getValue()));
        }
//        List<InsureUploadCostDTO> itemInfoDTOList = insureGetInfoDAO.queryAll(insureSettleInfoDTO);
//        if(!itemInfoDTOList.isEmpty() && itemInfoDTOList.size() > 0 ){
//            dataMap.put("upType", "1");
//        }else{
//            dataMap.put("upType", "0");
//        }
        dataMap.put("upType", "1");
        HashMap commParam = new HashMap();
        checkRequest(dataMap);
        commParam.put("input", dataMap);
        commParam.put("infno",Constant.UnifiedPay.REGISTER.UP_4261);

        commParam.put("msgId",MapUtils.get(map,"msgId"));
        commParam.put("opter",MapUtils.get(map,"opter"));
        commParam.put("opter_name",MapUtils.get(map,"opter_name"));
        commParam.put("insuplcAdmdvs",MapUtils.get(map,"insuplcAdmdvs"));
        commParam.put("hospCode",MapUtils.get(map,"hospCode"));
        commParam.put("orgCode",MapUtils.get(map,"orgCode"));
        commParam.put("configCode",MapUtils.get(map,"configCode"));
        commParam.put("configRegCode",MapUtils.get(map,"configRegCode"));
        return getInsurCommonParam(commParam);
    }

    private FmiOwnpayPatnMdtrtDDTO initMdtrtDDTO(InptVisitDTO inptVisitDTO,InsureConfigurationDTO insureConfigurationDTO, String fixmedinsName) {

        //就诊信息参数mdtrtinfo
        FmiOwnpayPatnMdtrtDDTO mdtrtinfoMap = new FmiOwnpayPatnMdtrtDDTO();
        mdtrtinfoMap.setMdtrtId(inptVisitDTO.getVisitId());
        mdtrtinfoMap.setFixmedinsCode(insureConfigurationDTO.getOrgCode());
        mdtrtinfoMap.setFixmedinsName(fixmedinsName);

        // 证件类型
        mdtrtinfoMap.setPsnCertType(inptVisitDTO.getCertCode());
        mdtrtinfoMap.setCertno(inptVisitDTO.getCertNo());//	证件号码
        // 人员姓名
        mdtrtinfoMap.setPsnName(inptVisitDTO.getName());
        mdtrtinfoMap.setGend(inptVisitDTO.getGenderCode());//	性别
        mdtrtinfoMap.setBrdy(DateUtils.format(inptVisitDTO.getBirthday(),DateUtils.Y_M_DH_M_S));//	出生日期
        mdtrtinfoMap.setAge(String.valueOf(inptVisitDTO.getAge()));
        mdtrtinfoMap.setConerName(inptVisitDTO.getContactName());
        mdtrtinfoMap.setTel(inptVisitDTO.getContactPhone());//	联系电话
        mdtrtinfoMap.setAddr(inptVisitDTO.getContactAddress());//	联系地址


        mdtrtinfoMap.setBegntime(DateUtils.format(inptVisitDTO.getInTime(),DateUtils.Y_M_D));//	开始时间
        if(null != inptVisitDTO.getOutTime()){
            mdtrtinfoMap.setEndtime(DateUtils.format(inptVisitDTO.getOutTime(),DateUtils.Y_M_D));
        }


        mdtrtinfoMap.setMedType("2101");//	医疗类别
        mdtrtinfoMap.setIptOpNo(inptVisitDTO.getInNo());//	住院/门诊号

        mdtrtinfoMap.setMedrcdno(null);

        mdtrtinfoMap.setChfpdrCode(inptVisitDTO.getPracCertiNo());//inptVisitDTO.getZzDoctorId());//	主治医生编码
        mdtrtinfoMap.setChfpdrName(inptVisitDTO.getZzDoctorName());//inptVisitDTO.getZzDoctorName());//	主治医师姓名

        mdtrtinfoMap.setAdmDiseDscr(inptVisitDTO.getInDiseaseName());//	入院诊断描述
        mdtrtinfoMap.setAdmDeptCode( inptVisitDTO.getInDeptId());//	入院科室编码
        mdtrtinfoMap.setAdmDeptName( inptVisitDTO.getInDeptName());//	入院科室名称
        mdtrtinfoMap.setAdmBed( inptVisitDTO.getBedName());//	入院床位
        mdtrtinfoMap.setWardareaBed(null);

        mdtrtinfoMap.setIfturnDept(null);

        mdtrtinfoMap.setDscgDiseCode(inptVisitDTO.getInDiseaseId());//	住院主诊断代码
        mdtrtinfoMap.setDscgDiseName(inptVisitDTO.getInDiseaseName());//	住院主诊断名称

        mdtrtinfoMap.setDscgDeptCode(inptVisitDTO.getOutDeptId());
        mdtrtinfoMap.setDscgDiseName(inptVisitDTO.getOutDeptName());
        mdtrtinfoMap.setDscgBed(inptVisitDTO.getBedName());
        mdtrtinfoMap.setDscgWay(inptVisitDTO.getOutModeCode());

        mdtrtinfoMap.setMainCondDesc(inptVisitDTO.getInDiseaseName());

        String diseCode = inptVisitDTO.getInDiseaseId();
        String diseCodeName = inptVisitDTO.getInDiseaseName();
        if(StringUtils.isEmpty(diseCode) || "null".equals(diseCode)){
            mdtrtinfoMap.setDiseNo("");//	病种编码
        }else{
            mdtrtinfoMap.setDiseNo(diseCode);//	病种编码
        }
        if(StringUtils.isEmpty(diseCodeName) || "null".equals(diseCodeName)){
            mdtrtinfoMap.setDiseName("");//	病种名称
        }else{
            mdtrtinfoMap.setDiseName(diseCodeName);//	病种名称
        }

        mdtrtinfoMap.setOprnOprtCode(null);//	手术操作代码
        mdtrtinfoMap.setOprnOprtName(null);//	手术操作名称

        mdtrtinfoMap.setOpDiseInfo(inptVisitDTO.getOutptDiseaseName());
        mdtrtinfoMap.setInhospStas(inptVisitDTO.getStatusCode());
        mdtrtinfoMap.setDieDate(inptVisitDTO.getDietType());
        mdtrtinfoMap.setIptDays(inptVisitDTO.getTotalIn());

        mdtrtinfoMap.setFpscNo(null);//	计划生育服务证号
        if("52".equals(inptVisitDTO.getPatientCode())){
            mdtrtinfoMap.setMatnType("1");//	生育类别
            if(inptVisitDTO.getAge().compareTo(23) ==1){
                mdtrtinfoMap.setLatechbFlag("1");//	晚育标志
            }else{
                mdtrtinfoMap.setLatechbFlag("0");//	晚育标志
            }
            mdtrtinfoMap.setPrematureFlag("0");//	早产标志
        }
        else{
            mdtrtinfoMap.setMatnType(null);//	生育类别
            mdtrtinfoMap.setLatechbFlag(null);//	晚育标志
            mdtrtinfoMap.setPrematureFlag(null);//	早产标志
        }
        mdtrtinfoMap.setBirctrlType(null);//	计划生育手术类别
        mdtrtinfoMap.setGesoVal(null);//	孕周数
        mdtrtinfoMap.setFetts( null);//	胎次
        mdtrtinfoMap.setFetusCnt(null);//	胎儿数
        mdtrtinfoMap.setGestationTime(null);
        mdtrtinfoMap.setBirctrlOrMatnTime(null);
        mdtrtinfoMap.setCmplctFlag(null);
        mdtrtinfoMap.setValiFlag("1");
        mdtrtinfoMap.setMemo("");

        return mdtrtinfoMap;
    }

    private FmiOwnpayPatnMdtrtDDTO initMdtrtDDTO(OutptVisitDTO outptVisitDTO,InsureConfigurationDTO insureConfigurationDTO, String fixmedinsName) {

        //就诊信息参数mdtrtinfo
        FmiOwnpayPatnMdtrtDDTO mdtrtinfoMap = new FmiOwnpayPatnMdtrtDDTO();
        mdtrtinfoMap.setMdtrtId(outptVisitDTO.getId());
        mdtrtinfoMap.setFixmedinsCode(insureConfigurationDTO.getOrgCode());
        mdtrtinfoMap.setFixmedinsName(fixmedinsName);

        // 证件类型
        mdtrtinfoMap.setPsnCertType(outptVisitDTO.getCertCode());
        mdtrtinfoMap.setCertno(outptVisitDTO.getCertNo());//	证件号码
        // 人员姓名
        mdtrtinfoMap.setPsnName(outptVisitDTO.getName());
        mdtrtinfoMap.setGend(outptVisitDTO.getGenderCode());//	性别
        mdtrtinfoMap.setBrdy(DateUtils.format(outptVisitDTO.getBirthday(),DateUtils.Y_M_DH_M_S));//	出生日期
        mdtrtinfoMap.setAge(String.valueOf(outptVisitDTO.getAge()));
        mdtrtinfoMap.setConerName(outptVisitDTO.getContactName());
        mdtrtinfoMap.setTel(outptVisitDTO.getContactPhone());//	联系电话
        mdtrtinfoMap.setAddr(outptVisitDTO.getContactAddress());//	联系地址


        mdtrtinfoMap.setBegntime(DateUtils.format(outptVisitDTO.getVisitTime(),DateUtils.Y_M_D));//	开始时间
        mdtrtinfoMap.setEndtime(null);

        mdtrtinfoMap.setMedType("11");//	医疗类别
        mdtrtinfoMap.setIptOpNo(null);//	住院/门诊号
        mdtrtinfoMap.setMedrcdno(null);
        mdtrtinfoMap.setChfpdrCode(outptVisitDTO.getPracCertiNo());//inptVisitDTO.getZzDoctorId());//	主治医生编码
        mdtrtinfoMap.setChfpdrName(outptVisitDTO.getDoctorName());//inptVisitDTO.getZzDoctorName());//	主治医师姓名

        mdtrtinfoMap.setAdmDiseDscr(null);//	入院诊断描述
        mdtrtinfoMap.setAdmDeptCode(null);//	入院科室编码
        mdtrtinfoMap.setAdmDeptName(null);//	入院科室名称
        mdtrtinfoMap.setAdmBed(null);//	入院床位
        mdtrtinfoMap.setWardareaBed(null);
        mdtrtinfoMap.setIfturnDept(null);
        mdtrtinfoMap.setDscgDiseCode(null);//	住院主诊断代码
        mdtrtinfoMap.setDscgDiseName(null);//	住院主诊断名称
        mdtrtinfoMap.setDscgDeptCode(null);
        mdtrtinfoMap.setDscgDiseName(null);
        mdtrtinfoMap.setDscgBed(null);
        mdtrtinfoMap.setDscgWay(null);

        mdtrtinfoMap.setMainCondDesc(null);

        String diseCode = outptVisitDTO.getInDiseaseId();
        String diseCodeName = outptVisitDTO.getInDiseaseName();
        if(StringUtils.isEmpty(diseCode) || "null".equals(diseCode)){
            mdtrtinfoMap.setDiseNo("");//	病种编码
        }else{
            mdtrtinfoMap.setDiseNo(diseCode);//	病种编码
        }
        if(StringUtils.isEmpty(diseCodeName) || "null".equals(diseCodeName)){
            mdtrtinfoMap.setDiseName("");//	病种名称
        }else{
            mdtrtinfoMap.setDiseName(diseCodeName);//	病种名称
        }

        mdtrtinfoMap.setOprnOprtCode(null);//	手术操作代码
        mdtrtinfoMap.setOprnOprtName(null);//	手术操作名称

        mdtrtinfoMap.setOpDiseInfo(outptVisitDTO.getOutptDiseaseName());
        mdtrtinfoMap.setInhospStas("0");
        mdtrtinfoMap.setDieDate(null);
        mdtrtinfoMap.setIptDays(null);

        mdtrtinfoMap.setFpscNo(null);//	计划生育服务证号
        if("52".equals(outptVisitDTO.getPatientCode())){
            mdtrtinfoMap.setMatnType("1");//	生育类别
            if(outptVisitDTO.getAge().compareTo(23) ==1){
                mdtrtinfoMap.setLatechbFlag("1");//	晚育标志
            }else{
                mdtrtinfoMap.setLatechbFlag("0");//	晚育标志
            }
            mdtrtinfoMap.setPrematureFlag("0");//	早产标志
        }
        else{
            mdtrtinfoMap.setMatnType(null);//	生育类别
            mdtrtinfoMap.setLatechbFlag(null);//	晚育标志
            mdtrtinfoMap.setPrematureFlag(null);//	早产标志
        }
        mdtrtinfoMap.setBirctrlType(null);//	计划生育手术类别
        mdtrtinfoMap.setGesoVal(null);//	孕周数
        mdtrtinfoMap.setFetts( null);//	胎次
        mdtrtinfoMap.setFetusCnt(null);//	胎儿数
        mdtrtinfoMap.setGestationTime(null);
        mdtrtinfoMap.setBirctrlOrMatnTime(null);
        mdtrtinfoMap.setCmplctFlag(null);
        mdtrtinfoMap.setValiFlag("1");
        mdtrtinfoMap.setMemo("");

        return mdtrtinfoMap;
    }

    private List<FmiOwnpayPatnDiseListDDTO> initInptDiseListDDTOS(List<InptDiagnoseDTO> inptDiagnoseDTOList,List<InptDiagnoseDTO> inptMatchDiagnoseDTOList,
                                                                  InsureConfigurationDTO insureConfigurationDTO, String fixmedinsName) {
        List<FmiOwnpayPatnDiseListDDTO> diseinfoList = new ArrayList<FmiOwnpayPatnDiseListDDTO>();
        commonHandlerDisease(inptMatchDiagnoseDTOList,inptDiagnoseDTOList,null);
        for(int i=0;i<inptDiagnoseDTOList.size();i++){
            FmiOwnpayPatnDiseListDDTO diseinfoMap = new FmiOwnpayPatnDiseListDDTO();

            diseinfoMap.setFixmedinsCode(insureConfigurationDTO.getOrgCode());
            diseinfoMap.setFixmedinsName(fixmedinsName);
            diseinfoMap.setDiagSrtNo(i);//	诊断排序号
            diseinfoMap.setDiagInfoId(inptDiagnoseDTOList.get(i).getId());
            diseinfoMap.setMdtrtId(inptDiagnoseDTOList.get(i).getVisitId());
//            diseinfoMap.setInoutDiagType("");

            String type = inptDiagnoseDTOList.get(i).getTypeCode();
            switch(type){
                case "204" :
                case "303" :
                    diseinfoMap.setInoutDiagType("2");
                    break;
                case "101" :
                case "102" :
                case "201" :
                case "202" :
                case "203" :
                    diseinfoMap.setInoutDiagType("1");
                    break;
                default : //可选
                    diseinfoMap.setInoutDiagType(null);
            }

            diseinfoMap.setDiagType(type);//	诊断类别
            diseinfoMap.setMaindiagFlag(inptDiagnoseDTOList.get(i).getIsMain());//	主诊断标志
            diseinfoMap.setDiagCode(inptMatchDiagnoseDTOList.get(i).getInsureInllnessCode());//	诊断代码
            diseinfoMap.setDiagName(inptMatchDiagnoseDTOList.get(i).getInsureInllnessName());//	诊断名称

            diseinfoMap.setAdmCond(null);//	入院病情
            diseinfoMap.setDiagDept(inptDiagnoseDTOList.get(i).getInDeptName());//	诊断科室
            diseinfoMap.setDiagDrCode(inptDiagnoseDTOList.get(i).getPracCertiNo());//	诊断医生编码
            diseinfoMap.setDiagDrName(inptDiagnoseDTOList.get(i).getZzDoctorName());//	诊断医生姓名
            diseinfoMap.setDiagTime(DateUtils.format(inptDiagnoseDTOList.get(i).getCrteTime(),DateUtils.Y_M_DH_M_S));//	诊断时间

            diseinfoMap.setPoolarea(null);

            diseinfoList.add(diseinfoMap);

        }
        return diseinfoList;
    }

    /**
     * @Method commonHandlerDisease
     * @Desrciption  医保入院登记和出院办理时，验证诊断是否匹配
     * @Param   inptDiagnoseDTOList：未关联医保疾病匹配表
     *          data:关联疾病匹配表
     *          type:业务类型  2401  医保入院登记  2402  医保出院办理
     * @Author fuhui
     * @Date   2021/9/2 9:01
     * @Return
     **/
    private void commonHandlerDisease(List<InptDiagnoseDTO> inptDiagnoseDTOList,List<InptDiagnoseDTO> data,String type) {

        List<InptDiagnoseDTO> list = data.stream().filter(inptDiagnoseDTO ->
                Constants.SF.S.equals(inptDiagnoseDTO.getIsMain())).collect(Collectors.toList());
        if(inptDiagnoseDTOList.size() != data.size()){
            List<String> dataCollect = data.stream().map(InptDiagnoseDTO::getDiseaseName).distinct().collect(Collectors.toList());
            List<String> inptDataCollect = inptDiagnoseDTOList.stream().map(InptDiagnoseDTO::getDiseaseName).distinct().collect(Collectors.toList());
            List<String> collect = dataCollect.stream().filter(item -> !inptDataCollect.contains(item)).collect(Collectors.toList());
            StringBuilder stringBuilder = new StringBuilder();
            if(!ListUtils.isEmpty(collect)) {
                for (String s : collect) {
                    stringBuilder.append(s).append(",");
                }
                throw new AppException("该患者开的"+stringBuilder+"还没有进行疾病匹配,请先做好匹配工作");
            }
        }
    }

    private List<FmiOwnpayPatnDiseListDDTO> initOutptDiseListDDTOS(List<OutptDiagnoseDTO> outptDiagnoseDTOList,List<OutptDiagnoseDTO> outptMatchDiagnoseDTOList,
                                                              InsureConfigurationDTO insureConfigurationDTO, String fixmedinsName,FmiOwnpayPatnMdtrtDDTO fmiOwnpayPatnMdtrtDDTO) {
        List<FmiOwnpayPatnDiseListDDTO> diseinfoList = new ArrayList<FmiOwnpayPatnDiseListDDTO>();

        commonHandlerDisease(outptMatchDiagnoseDTOList, outptDiagnoseDTOList);
        for(int i=0;i<outptMatchDiagnoseDTOList.size();i++) {
            FmiOwnpayPatnDiseListDDTO diseinfoMap = new FmiOwnpayPatnDiseListDDTO();

            diseinfoMap.setFixmedinsCode(insureConfigurationDTO.getOrgCode());
            diseinfoMap.setFixmedinsName(fixmedinsName);
            diseinfoMap.setDiagSrtNo(i + 1);//	诊断排序号
            diseinfoMap.setDiagInfoId(outptMatchDiagnoseDTOList.get(i).getId());
            diseinfoMap.setMdtrtId(outptMatchDiagnoseDTOList.get(i).getVisitId());
            diseinfoMap.setInoutDiagType("1");

            diseinfoMap.setDiagType("1");//	诊断类别
            diseinfoMap.setMaindiagFlag(outptMatchDiagnoseDTOList.get(i).getIsMain());//	主诊断标志
            diseinfoMap.setDiagCode(outptMatchDiagnoseDTOList.get(i).getInsureInllnessCode());//	诊断代码
            diseinfoMap.setDiagName(outptMatchDiagnoseDTOList.get(i).getInsureInllnessName());//	诊断名称
            if("1".equals(diseinfoMap.getMaindiagFlag())){
                fmiOwnpayPatnMdtrtDDTO.setDiseNo(outptMatchDiagnoseDTOList.get(i).getInsureInllnessCode());
                fmiOwnpayPatnMdtrtDDTO.setDiseName(outptMatchDiagnoseDTOList.get(i).getInsureInllnessName());
            }
            diseinfoMap.setAdmCond(null);//	入院病情
            diseinfoMap.setDiagDept(outptMatchDiagnoseDTOList.get(i).getInDeptName());//	诊断科室
            diseinfoMap.setDiagDrCode(outptMatchDiagnoseDTOList.get(i).getPracCertiNo());//	诊断医生编码
            diseinfoMap.setDiagDrName(outptMatchDiagnoseDTOList.get(i).getZzDoctorName());//	诊断医生姓名
            diseinfoMap.setDiagTime(DateUtils.format(outptMatchDiagnoseDTOList.get(i).getCrteTime(), DateUtils.Y_M_DH_M_S));//	诊断时间

            diseinfoMap.setPoolarea(null);

            diseinfoList.add(diseinfoMap);
        }
        // 封装诊断信
        return diseinfoList;
    }

    /**
     * @Method commonHandlerDisease
     * @Desrciption 医保入院登记和出院办理时，验证诊断是否匹配
     * @Param
     * @Author fuhui
     * @Date 2021/9/2 9:01
     * @Return
     **/
    private void commonHandlerDisease(List<OutptDiagnoseDTO> inptDiagnoseDTOList, List<OutptDiagnoseDTO> data) {
        List<OutptDiagnoseDTO> list = data.stream().filter(inptDiagnoseDTO ->
                Constants.SF.S.equals(inptDiagnoseDTO.getIsMain())).collect(Collectors.toList());
        int size = list.size();
        if (size == 0) {
            throw new AppException("没有门诊主诊断");
        }
        if (size > 1) {
            throw new AppException("门诊主诊断数量大于1");
        }
        if (inptDiagnoseDTOList.size() != data.size()) {
            List<String> dataCollect = data.stream().map(OutptDiagnoseDTO::getDiseaseName).distinct().collect(Collectors.toList());
            List<String> inptDataCollect = inptDiagnoseDTOList.stream().map(OutptDiagnoseDTO::getDiseaseName).distinct().collect(Collectors.toList());
            List<String> collect = dataCollect.stream().filter(item -> !inptDataCollect.contains(item)).collect(Collectors.toList());
            StringBuilder stringBuilder = new StringBuilder();
            if (!ListUtils.isEmpty(collect)) {
                for (String s : collect) {
                    stringBuilder.append(s).append(",");
                }
                throw new AppException("该患者开的" + stringBuilder + "还没有进行疾病匹配,请先做好匹配工作");
            }
        }

    }

    private Object initFeeListDDTO(InsureSettleInfoDTO insureSettleInfoDTO,List<Map<String, Object>> feeList, InsureConfigurationDTO insureConfigurationDTO
                                        , String fixmedinsName) {
        List<FmiOwnpayPatnFeeListDDTO> listMap = new ArrayList<>();
        if (!ListUtils.isEmpty(feeList)) {
            for (Map<String, Object> item : feeList) {
                // 医保上传
                FmiOwnpayPatnFeeListDDTO feedetail = new FmiOwnpayPatnFeeListDDTO();
                String doctorId = MapUtils.get(item, "doctorId");
                String doctorName = MapUtils.get(item, "doctorName");

                feedetail.setBkkpSn(MapUtils.get(item, "id"));

                if (insureSettleInfoDTO.getLx().equals("1")) {

                    feedetail.setFeeOcurTime( DateUtils.format((Date) item.get("costTime"), DateUtils.Y_M_DH_M_S)); // 费用发生时间

                } else if (insureSettleInfoDTO.getLx().equals("0")) {
                    feedetail.setFeeOcurTime( DateUtils.format((Date) item.get("crteTime"), DateUtils.Y_M_DH_M_S)); // 费用发生时间

                }
                feedetail.setFixmedinsCode(insureConfigurationDTO.getOrgCode());
                feedetail.setFixmedinsName(fixmedinsName);
                BigDecimal cnt = BigDecimalUtils.scale((BigDecimal) item.get("totalNum"), 4);
                BigDecimal price = BigDecimalUtils.scale((BigDecimal) item.get("price"), 4);
                feedetail.setCnt(cnt); // 数量
                feedetail.setPric(price); // 单价

                DecimalFormat df1 = new DecimalFormat("0.00");
                String realityPrice = df1.format(BigDecimalUtils.convert(item.get("realityPrice").toString()));
                BigDecimal convertPrice = BigDecimalUtils.convert(realityPrice);
                feedetail.setDetItemFeeSumamt(convertPrice); // 明细项目费用总额

                feedetail.setMedListCodg(item.get("insureItemCode") == null ? "" : item.get("insureItemCode").toString()); // 医疗目录编码
                feedetail.setMedinsListCodg(item.get("hospItemCode") == null ? "" : item.get("hospItemCode").toString()); // 医药机构目录编码
                feedetail.setMedinsListName(MapUtils.get(item, "insureItemName")); // 医药机构目录名称
                feedetail.setMedChrgitmType(MapUtils.get(item, "insureItemType")); // 医疗收费项目类别
                feedetail.setProdname(MapUtils.get(item, "insureItemName")); // 商品名

                feedetail.setBilgDeptCodg(MapUtils.get(item, "deptId")); // 开单科室编码
                feedetail.setBilgDeptName(MapUtils.get(item, "deptName")); // 开单科室名称

                if (StringUtils.isEmpty(MapUtils.get(item, "deptId"))) {
                    feedetail.setBilgDrCodg( doctorId); // 开单医生编码
                } else {
                    feedetail.setBilgDrCodg( MapUtils.get(item, "deptId")); // 开单医生编码
                }
                if (StringUtils.isEmpty(MapUtils.get(item, "doctorName"))) {
                    feedetail.setBilgDrName( doctorName); // 开单医生编码
                } else {
                    feedetail.setBilgDrName( MapUtils.get(item, "doctorName")); // 开单医生姓名
                }

                feedetail.setOrdersDeptCode(null);
                feedetail.setOrdersDeptName(null);
                feedetail.setOrdersDrCode(feedetail.getBilgDrCodg());
                feedetail.setOrdersDrName(feedetail.getBilgDrName());

                feedetail.setTcmdrugUsedWay(null);
                feedetail.setExtinsFlag(null);
                feedetail.setExtinsHospCode(null);

                feedetail.setDscgTkdrugFlag(null);
                feedetail.setSinDosDscr(null);
                feedetail.setUsedFrquDscr(null);
                feedetail.setPrdDays(null);
                feedetail.setMedcWayDscr(null);
                feedetail.setMemo(null);

                listMap.add(feedetail);
            }

        }

        return listMap;
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}
