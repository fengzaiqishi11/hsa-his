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
import cn.hsa.module.insure.fmiownpaypatn.dto.XzFmiOwnpayPatnMdtrtDDTO;
import cn.hsa.module.insure.module.dao.InsureGetInfoDAO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hsa.module.insure.module.dto.InsureSettleInfoDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName PsnInfoReqUtil
 * @Deacription 人员信息获取-1101
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_4202)
public class FmiOwnpayPatnDiseUploadReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

    @Resource
    private SysParameterService sysParameterService_consumer;

    @Override
    public InsureInterfaceParamDTO initRequest(T param) {
        Map map = (Map) param;
        BigDecimal medfeeSumamt = (BigDecimal) map.get("medfeeSumamt");
        InsureConfigurationDTO insureConfigurationDTO =  (InsureConfigurationDTO) map.get("insureConfigurationDTO");
        InptVisitDTO inptVisitDTO = (InptVisitDTO) map.get("inptVisitDTO");
        List<InptDiagnoseDTO> inptDiagnoseDTOList = (List<InptDiagnoseDTO>) map.get("inptDiagnoseDTOList");
        List<InptDiagnoseDTO> inptMatchDiagnoseDTOList = (List<InptDiagnoseDTO>) map.get("inptMatchDiagnoseDTOList");


        InsureSettleInfoDTO insureSettleInfoDTO = (InsureSettleInfoDTO) map.get("insureSettleInfoDTO");


        Map<String, Object> isInsureUnifiedMap = new HashMap<>();
        isInsureUnifiedMap.put("hospCode", insureSettleInfoDTO.getHospCode());
        isInsureUnifiedMap.put("code", "JG_NAME");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
        if (sysParameterDTO == null) {
            throw new AppException("请先配置默认的医疗机构编码参数信息:编码为:JG_NAME,值为对应的医疗机构编码值");
        }

        Map<String, Object> dataMap = new HashMap<>(3);

        dataMap.put("ownPayPatnMdtrtD", HumpUnderlineUtils.humpToUnderline(initMdtrtDDTO(inptVisitDTO, insureConfigurationDTO, sysParameterDTO.getValue(),insureSettleInfoDTO,medfeeSumamt)));
        dataMap.put("ownPayPatnDiagListD", HumpUnderlineUtils.humpToUnderlineArray(initInptDiseListDDTOS(inptDiagnoseDTOList, inptMatchDiagnoseDTOList, insureConfigurationDTO, sysParameterDTO.getValue())));


        HashMap commParam = new HashMap();
        checkRequest(dataMap);
        commParam.put("input", dataMap);
        commParam.put("infno",Constant.UnifiedPay.REGISTER.UP_4202);

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

    private XzFmiOwnpayPatnMdtrtDDTO initMdtrtDDTO(InptVisitDTO inptVisitDTO, InsureConfigurationDTO insureConfigurationDTO, String fixmedinsName,InsureSettleInfoDTO insureSettleInfoDTO
    ,BigDecimal medfeeSumamt) {

        //就诊信息参数mdtrtinfo
        XzFmiOwnpayPatnMdtrtDDTO mdtrtinfoMap = new XzFmiOwnpayPatnMdtrtDDTO();
        mdtrtinfoMap.setFixmedinsMdtrtId(inptVisitDTO.getVisitId());
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

        mdtrtinfoMap.setTrafDeptFlag(null);

        mdtrtinfoMap.setDscgMaindiagCode(inptVisitDTO.getInDiseaseId());//	住院主诊断代码
        mdtrtinfoMap.setDscgMaindiagName(inptVisitDTO.getInDiseaseName());//	住院主诊断名称

        mdtrtinfoMap.setDscgDeptCode(inptVisitDTO.getOutDeptId());
        mdtrtinfoMap.setDscgDeptName(inptVisitDTO.getOutDeptName());
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
            mdtrtinfoMap.setPretFlag("0");//	早产标志
        }
        else{
            mdtrtinfoMap.setMatnType(null);//	生育类别
            mdtrtinfoMap.setLatechbFlag(null);//	晚育标志
            mdtrtinfoMap.setPretFlag(null);//	早产标志
        }
        mdtrtinfoMap.setBirctrlType(null);//	计划生育手术类别
        mdtrtinfoMap.setGesoVal(null);//	孕周数
        mdtrtinfoMap.setFetts( null);//	胎次
        mdtrtinfoMap.setFetusCnt(null);//	胎儿数
        mdtrtinfoMap.setPreyTime(null);
        mdtrtinfoMap.setBirctrlOrMatnDate(null);
        mdtrtinfoMap.setCopFlag(null);
        mdtrtinfoMap.setValiFlag("1");
        mdtrtinfoMap.setMemo("");
        mdtrtinfoMap.setOpterId(insureSettleInfoDTO.getCrteId());
        mdtrtinfoMap.setOpterName(insureSettleInfoDTO.getCrteName());
        mdtrtinfoMap.setOptTime(DateUtils.format(new Date(),DateUtils.Y_M_DH_M_S));
        mdtrtinfoMap.setElecBillCode(null);
        mdtrtinfoMap.setElecBillnoCode(null);
        mdtrtinfoMap.setElecBillnoChkcode(null);
        mdtrtinfoMap.setExpContent("");
        mdtrtinfoMap.setMedfeeSumamt(medfeeSumamt.setScale(2));

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
            diseinfoMap.setDiagCode(inptDiagnoseDTOList.get(i).getInsureInllnessCode());//	诊断代码
            diseinfoMap.setDiagName(inptDiagnoseDTOList.get(i).getInsureInllnessName());//	诊断名称

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


    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}
