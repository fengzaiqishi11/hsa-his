package cn.hsa.module.insure.module.dto;

import cn.hsa.module.insure.module.entity.InsureInptRegisterDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InsureInptRegisterDTO extends InsureInptRegisterDO implements Serializable {

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
     * 经办时间
     */
    private Date aae036;

    /**
     * 入院诊断名称
     */
    private String bkz101;

    // 长沙医保必要入参
    private String inNo; // 住院号
    private String emrInfo; // 病历信息
    private String doctorId; // 病历信息
    private String inReson; // 入院原因
    private String qrCode;
    private String qrCodeToken;
    private String userCode;// 员工工号
    private String userName;//员工姓名
    private String injuryBorthSn;// 业务申请序列号
}
