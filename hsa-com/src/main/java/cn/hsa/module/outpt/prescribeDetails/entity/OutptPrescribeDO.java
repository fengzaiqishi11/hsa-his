package cn.hsa.module.outpt.prescribeDetails.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.outpt.prescribeDetails.entity
 * @Class_name: OutptPrescribeDetailsDO
 * @Describe: 门诊处方entity
 * @Author: zengfeng
 * @Email: zengfeng@powersi.com.cn
 * @Date: 2020/9/8 9:13
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutptPrescribeDO extends PageDO implements Serializable {
    private static final long serialVersionUID = 8336494033412956753L;

    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 就诊ID
     */
    private String visitId;
    /**
     * 诊断ID
     */
    private String diagnoseIds;
    /**
     * 处方单号
     */
    private String orderNo;
    /**
     * 开方医生
     */
    private String doctorId;
    /**
     * 开方医生名称
     */
    private String doctorName;
    /**
     * 开方科室
     */
    private String deptId;
    /**
     * 开方科室名称
     */
    private String deptName;
    /**
     * 处方类别代码
     */
    private String typeCode;
    /**
     * 处方类型代码
     */
    private String prescribeTypeCode;
    /**
     * 结算ID
     */
    private String settleId;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否结算
     */
    private String isSettle;
    /**
     * 是否作废
     */
    private String isCancel;
    /**
     * 是否打印（SF）
     */
    private String isPrint;
    /**
     * 中草药是否本院煎药（SF）
     */
    private String isHerbHospital;
    /**
     * 中草药付（剂）数
     */
    private BigDecimal herbNum;
    /**
     * 中草药用法（ZYYF）
     */
    private String herbUseCode;
    /**
     * 体重（儿科）
     */
    private BigDecimal weight;
    /**
     * 代办人姓名
     */
    private String agentName;
    /**
     * 代办人身份编号
     */
    private String agentCertNo;
    /**
     * 作废人ID
     */
    private String cancelId;
    /**
     * 作废人
     */
    private String cancelName;
    /**
     * 作废时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cancelDate;
    /**
     * 作废原因
     */
    private String cancelReason;
    /**
     * 创建人ID
     */
    private String crteId;
    /**
     * 创建人姓名
     */
    private String crteName;

    /**
     * 创建时间（开方日期）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8" )
    private Date crteTime;
    /**
     * 是否提交
     */
    private String isSubmit;

    /**
     * 提交人ID
     */
    private String submitId;
    /**
     * 提交人
     */
    private String submitName;

    /**
     * 提交时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8" )
    private Date submitTime;
}
