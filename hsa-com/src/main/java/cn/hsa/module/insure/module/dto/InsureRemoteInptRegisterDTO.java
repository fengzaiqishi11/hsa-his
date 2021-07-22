package cn.hsa.module.insure.module.dto;

import cn.hsa.module.insure.module.entity.InsureRemoteInptRegisterDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InsureRemoteInptRegisterDTO extends InsureRemoteInptRegisterDO implements Serializable {

    /**
     * 医院编码
     */
    private String hospCode;

    /**
     * 医保机构编码
     */
    private String insureOrgCode;

    /**
     * 就医登记号
     */
    private String aaz217;

    /**
     * 参保人行政区代码
     */
    private String baa027;
}
