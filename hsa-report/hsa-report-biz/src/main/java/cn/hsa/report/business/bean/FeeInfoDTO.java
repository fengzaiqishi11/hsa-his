package cn.hsa.report.business.bean;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName FeeInfoDTO
 * @Deacription 医保费用明细表
 * @Author caizeming
 * @Date 2021-06-10
 * @Version 1.0
 **/
@Data
public class FeeInfoDTO implements Serializable {

    /**
     * 费用ID
     */
    private Long feeId;
    /**
     * 就诊ID
     */
    private String mdtrtId;
    /**
     * 人员编号
     */
    private String psnNo;
    /**
     * 费用明细流水号
     */
    private String feedetlSn;
    /**
     * 原费用流水号
     */
    private String initFeedetlSn;
    /**
     * 医疗类别
     */
    private String medType;
    /**
     * 费用发生时间
     */
    private Date feeOcurTime;
    /**
     * 费用发生日期
     */
    private String feeOcurDate;
    /**
     * 费用执行时间
     */
    private Date occurTime;
    /**
     * 医疗目录编码
     */
    private String medListCodg;
    /**
     * 医药机构目录编码
     */
    private String medinsListCodg;
    /**
     * 费用类别
     */
    private String feeType;
    /**
     * 明细项目费用总额
     */
    private BigDecimal detItemFeeSumamt;
    /**
     * 项目名称
     */
    private String itemName;
    /**
     * 医嘱号
     */
    private String drordNo;
    /**
     * 单位
     */
    private String unit;
    /**
     * 数量
     */
    private BigDecimal cnt;
    /**
     * 单价
     */
    private BigDecimal pric;
    /**
     * 开单科室编码
     */
    private String bilgDeptCodg;
    /**
     * 开单科室名称
     */
    private String bilgDeptName;
    /**
     * 开单医生医保编码
     */
    private String bilgDrInsurCode;
    /**
     * 开单医生编码
     */
    private String bilgDrCodg;
    /**
     * 开单医师姓名
     */
    private String bilgDrName;
    /**
     * 受单科室编码
     */
    private String acordDeptCodg;
    /**
     * 受单科室名称
     */
    private String acordDeptName;
    /**
     * 受单医生医保编码
     */
    private String ordersDrInsurCode;
    /**
     * 受单医生编码
     */
    private String ordersDrCode;
    /**
     * 受单医生姓名
     */
    private String ordersDrName;
    /**
     * 医院审批标志
     */
    private String hospApprFlag;
    /**
     * 中药使用方式
     */
    private String tcmdrugUsedWay;
    /**
     * 外检标志
     */
    private String etipFlag;
    /**
     * 外检医院编码
     */
    private String etipHospCode;
    /**
     * 出院带药标志
     */
    private String dscgTkdrugFlag;
    /**
     * 生育费用标志
     */
    private String matnFeeFlag;
    /**
     * 定价上限金额
     */
    private BigDecimal pricUplmtAmt;
    /**
     * 自付比例
     */
    private BigDecimal selfpayProp;
    /**
     * 全自费金额
     */
    private BigDecimal fulamtOwnpayAmt;
    /**
     * 超限价金额
     */
    private BigDecimal overlmtAmt;
    /**
     * 先行自付金额
     */
    private BigDecimal preselfpayAmt;
    /**
     * 符合政策范围金额
     */
    private BigDecimal inscpScpAmt;
    /**
     * 收费项目等级
     */
    private String chrgitmLv;
    /**
     * 医疗收费项目类别
     */
    private String medChrgitmType;
    /**
     * 基本药物标志
     */
    private String basMednFlag;
    /**
     * 医保谈判药品标志
     */
    private String hiNegoDrugFlag;
    /**
     * 儿童用药标志
     */
    private String chldMedcFlag;
    /**
     * 目录特项标志
     */
    private String listSpItemFlag;
    /**
     * 限制使用标志
     */
    private String lmtUsedFlag;
    /**
     * 直报标志
     */
    private String drtReimFlag;
    /**
     * 费用来源
     */
    private String feeSource;
    /**
     * 备注
     */
    private String memo;
    /**
     * 状态
     */
    private String status;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * his就诊流水号
     */
    private String visitId;

    /**
     *
     */
    private String isQa;

}
