package cn.hsa.module.insure.module.dto;
import cn.hsa.module.insure.module.entity.InsureOutptOutFeeDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InsureOutptOutFeeDTO extends InsureOutptOutFeeDO implements Serializable {

    /**
     * 就诊ID
     */
    private String visitId;

    /**
     * 医院编码
     */
    private String hospCode;

    /**
     * 医疗机构编码
     */
    private String insureOrgCode;

    /**
     * 医保机构编码
     */
    private String insureRegCode;

    /**
     * 就医登记号
     */
    private String medicalRegNo;
    /**
     * 操作员编码
     */
    private String code;

}
