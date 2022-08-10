package cn.hsa.insure.unifiedpay.util.inptregister;

import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.module.dao.InsureIndividualCostDAO;
import cn.hsa.module.insure.module.dto.InsureIndividualBasicDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualCostDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.dto.InsureInptRegisterDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.MapUtils;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName OutHospRegisterReqUtil
 * @Deacription 出院登记-2402
 * @Author liuzhuoting
 * @Date 2021/3/10 10:33
 * @Version 1.0
 **/
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_2402)
public class OutHospRegisterReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {
    final String ZY_CHU_YUAN = "住院中医出院诊断";
    final String XY_CHU_YUAN = "住院西医出院诊断";
    @Resource
    private InsureIndividualCostDAO insureIndividualCostDAO;

    @Override
    public InsureInterfaceParamDTO initRequest(T param) {
        Map map = (Map) param;
        Map<String, Object> dataMap = new HashMap<>(2);

        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map, "insureIndividualVisitDTO");
        List<InptDiagnoseDTO> inptDiagnoseDTOList = MapUtils.get(map, "inptDiagnoseDTOList");

        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put("mdtrtinfo", initMdtrtinfoMap(insureIndividualVisitDTO));
        inputMap.put("diseinfo", initDiseinfoList(insureIndividualVisitDTO,inptDiagnoseDTOList));

        HashMap commParam = new HashMap();

        checkRequest(map);
        commParam.put("input", inputMap);
        commParam.put("infno",Constant.UnifiedPay.REGISTER.UP_2402);

        commParam.put("opter",MapUtils.get(map,"opter"));
        commParam.put("opter_name",MapUtils.get(map,"opter_name"));
        commParam.put("insuplcAdmdvs",MapUtils.get(map,"insuplcAdmdvs"));
        commParam.put("hospCode",MapUtils.get(map,"hospCode"));
        commParam.put("orgCode",MapUtils.get(map,"orgCode"));
        commParam.put("configCode",MapUtils.get(map,"configCode"));
        commParam.put("configRegCode",MapUtils.get(map,"configRegCode"));

        return getInsurCommonParam(commParam);

    }

    private Object initDiseinfoList(InsureIndividualVisitDTO insureIndividualVisitDTO, List<InptDiagnoseDTO> inptDiagnoseDTOList) {
        List<Map<String, Object>> diseinfoList = new ArrayList<Map<String, Object>>();
        Map<String, Object> diseinfoMap =null;
        for(int i=0;i<inptDiagnoseDTOList.size();i++) {
            if("1".equals(insureIndividualVisitDTO.getIsHalfSettle())){
                if("204".equals(inptDiagnoseDTOList.get(i).getTypeCode()) ||
                        "202".equals(inptDiagnoseDTOList.get(i).getTypeCode())||
                        "203".equals(inptDiagnoseDTOList.get(i).getTypeCode())){
                    diseinfoMap = new HashMap<>();
                    diseinfoMap.put("mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo());//	mdtrt_id
                    diseinfoMap.put("psn_no", insureIndividualVisitDTO.getAac001());//	人员编号
                    diseinfoMap.put("diag_type", "1");//	诊断类别
                    diseinfoMap.put("maindiag_flag", inptDiagnoseDTOList.get(i).getIsMain());//	主诊断标志
                    diseinfoMap.put("diag_srt_no", i);//	诊断排序号
                    diseinfoMap.put("diag_code", inptDiagnoseDTOList.get(i).getInsureInllnessCode());//	诊断代码
                    diseinfoMap.put("diag_name", inptDiagnoseDTOList.get(i).getInsureInllnessName());//	诊断名称
                    diseinfoMap.put("diag_dept", inptDiagnoseDTOList.get(i).getInDeptName());//	诊断科室
                    diseinfoMap.put("dise_dor_no", inptDiagnoseDTOList.get(i).getPracCertiNo());//	诊断医生编码
                    diseinfoMap.put("dise_dor_name", inptDiagnoseDTOList.get(i).getZzDoctorName());//	诊断医生姓名
                    diseinfoMap.put("diag_time", DateUtils.format(inptDiagnoseDTOList.get(i).getCrteTime(), DateUtils.Y_M_DH_M_S));//	诊断时间
                    diseinfoList.add(diseinfoMap);
                }
            }
            else{
                if ("204".equals(inptDiagnoseDTOList.get(i).getTypeCode()) ||
                        "303".equals(inptDiagnoseDTOList.get(i).getTypeCode())) {
                    diseinfoMap = new HashMap<>();
                    diseinfoMap.put("mdtrt_id", insureIndividualVisitDTO.getMedicalRegNo());//	mdtrt_id
                    diseinfoMap.put("psn_no", insureIndividualVisitDTO.getAac001());//	人员编号
                    diseinfoMap.put("diag_type", "1");//	诊断类别
                    diseinfoMap.put("maindiag_flag", inptDiagnoseDTOList.get(i).getIsMain());//	主诊断标志
                    diseinfoMap.put("diag_srt_no", i);//	诊断排序号
                    diseinfoMap.put("diag_code", inptDiagnoseDTOList.get(i).getInsureInllnessCode());//	诊断代码
                    diseinfoMap.put("diag_name", inptDiagnoseDTOList.get(i).getInsureInllnessName());//	诊断名称
                    diseinfoMap.put("diag_dept", inptDiagnoseDTOList.get(i).getInDeptName());//	诊断科室
                    diseinfoMap.put("dise_dor_no", inptDiagnoseDTOList.get(i).getPracCertiNo());//	诊断医生编码
                    diseinfoMap.put("dise_dor_name", inptDiagnoseDTOList.get(i).getZzDoctorName());//	诊断医生姓名
                    diseinfoMap.put("diag_time", DateUtils.format(inptDiagnoseDTOList.get(i).getCrteTime(), DateUtils.Y_M_DH_M_S));//	诊断时间
                    diseinfoList.add(diseinfoMap);
                }
            }
        }
        return diseinfoList;
    }

    private Object initMdtrtinfoMap(InsureIndividualVisitDTO insureIndividualVisitDTO) {
        //出院信息参数dscginfo
        Map<String, Object> dscginfoMap = new HashMap<>();
        dscginfoMap.put("mdtrt_id",insureIndividualVisitDTO.getMedicalRegNo());//	就诊ID
        dscginfoMap.put("psn_no", insureIndividualVisitDTO.getAac001());//	人员编号
        dscginfoMap.put("insutype", insureIndividualVisitDTO.getAae140());//	险种类型

        /**
         * 判断是否是中途结算，如果是中途结算的话  医保出院日期就取（住院费用传输的选择费用区间的结束日期）
         */
        if("1".equals(insureIndividualVisitDTO.getIsHalfSettle()) && insureIndividualVisitDTO.getSettleCount() >0){
            insureIndividualVisitDTO.setInsureSettleId("0");
            InsureIndividualCostDTO individualCostDTO = insureIndividualCostDAO.selectHalfTransmitFee(insureIndividualVisitDTO);
            if(individualCostDTO != null){
                dscginfoMap.put("endtime", DateUtils.format(individualCostDTO.getFeeEndTime(),DateUtils.Y_M_DH_M_S));//	结束时间
            }

        }else{
            dscginfoMap.put("endtime", DateUtils.format(insureIndividualVisitDTO.getOutTime(),DateUtils.Y_M_DH_M_S));//	结束时间
        }
        dscginfoMap.put("dise_code", ObjectUtil.isNotEmpty(insureIndividualVisitDTO.getBka006()) ? insureIndividualVisitDTO.getBka006() : insureIndividualVisitDTO.getVisitIcdCode());//	病种编码
        dscginfoMap.put("dise_name", ObjectUtil.isNotEmpty(insureIndividualVisitDTO.getBka006Name()) ? insureIndividualVisitDTO.getBka006Name() : insureIndividualVisitDTO.getVisitIcdName());//	病种名称
        dscginfoMap.put("oprn_oprt_code", null);//	手术操作代码
        dscginfoMap.put("fpsc_no", insureIndividualVisitDTO.getFpscNo());//	计划生育服务证号
        dscginfoMap.put("matn_type", insureIndividualVisitDTO.getMatnType());//	生育类别
        dscginfoMap.put("birctrl_type", insureIndividualVisitDTO.getBirctrlType());//	计划生育手术类别
        dscginfoMap.put("latechb_flag", insureIndividualVisitDTO.getLatechbFlag());//	晚育标志
        dscginfoMap.put("geso_val", insureIndividualVisitDTO.getGesoVal());//	孕周数
        dscginfoMap.put("fetts", insureIndividualVisitDTO.getFetts());//	胎次
        dscginfoMap.put("fetus_cnt", insureIndividualVisitDTO.getFetusCnt());//	胎儿数
        dscginfoMap.put("pret_flag", insureIndividualVisitDTO.getPretFlag());//	早产标志
        dscginfoMap.put("birctrl_matn_date", insureIndividualVisitDTO.getBirctrlMatnDate());//	计划生育手术或生育日期
        dscginfoMap.put("cop_flag", null);//	伴有并发症标志
        dscginfoMap.put("dscg_dept_code", insureIndividualVisitDTO.getOutDeptId());//	出院科室编码
        dscginfoMap.put("dscg_dept_name", insureIndividualVisitDTO.getOutDeptName());//	出院科室名称
        dscginfoMap.put("dscg_bed", null);//	出院床位
        dscginfoMap.put("dscg_way", insureIndividualVisitDTO.getOutModeCode());//	离院方式
        dscginfoMap.put("die_date", null);//	死亡日期
        dscginfoMap.put("expi_gestation_nub_of_m", null);//	终止妊娠月数

        return dscginfoMap;
    }

    @Override
    public boolean checkRequest(Map param) {
        return true;
    }

}
