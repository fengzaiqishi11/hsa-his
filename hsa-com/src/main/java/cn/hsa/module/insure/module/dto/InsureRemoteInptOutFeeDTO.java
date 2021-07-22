package cn.hsa.module.insure.module.dto;

import cn.hsa.module.insure.module.entity.InsureRemoteInptOutFeeDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InsureRemoteInptOutFeeDTO extends InsureRemoteInptOutFeeDO implements Serializable {

    /**
     * 医院编码
     */
    private String hospCode;

    /**
     * 医疗机构编码
     */
    private String insureOrgCode;
}
