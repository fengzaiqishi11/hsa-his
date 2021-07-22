package cn.hsa.module.insure.module.dto;
import cn.hsa.module.insure.module.entity.InsureInptOutFeeDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InsureInptOutFeeDTO extends InsureInptOutFeeDO implements Serializable {

    /**
     * 医院编码
     */
    private String hospCode;

    /**
     * 医保机构编码
     */
    private String insureOrgCode;

    private String aac001;

    private String bka015;

    private String userCode;
}
