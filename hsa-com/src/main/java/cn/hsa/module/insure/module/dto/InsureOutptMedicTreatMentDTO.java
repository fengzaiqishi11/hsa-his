package cn.hsa.module.insure.module.dto;

import cn.hsa.module.inpt.doctor.dto.OutptCostDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InsureOutptMedicTreatMentDTO {
    private OutptVisitDTO mdtrtinfo;//自费病人门诊就诊信息
    private List<OutptDiagnoseDTO> diseinfo;//自费病人门诊诊断信息
    private List<OutptCostDTO> feedetail;//自费病人门诊费用明细
    private String hospCode;
    private String hospName;
}
