package cn.hsa.module.insure.module.dto;
import cn.hsa.module.insure.module.entity.InsureDoctorInfoDO;
import cn.hsa.module.insure.module.entity.InsureDoctorMgtinfoDO;
import cn.hsa.module.insure.module.entity.InsureInptOutFeeDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InsureDoctorInfoDTO extends InsureDoctorInfoDO implements Serializable {

    private List<InsureDoctorMgtinfoDO> insureDoctorMgtinfos;

    private String crteId;

    private String crteName;

}
