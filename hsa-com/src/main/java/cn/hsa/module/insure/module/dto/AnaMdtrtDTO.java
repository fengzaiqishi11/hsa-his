package cn.hsa.module.insure.module.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 就诊信息
 */
@Data
public class AnaMdtrtDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 就诊ID
     */
//    @JSONField(name = "mdtrt_id")
    private String mdtrtId;
    /**
     * 医疗服务机构标识
     */
//    @JSONField(name = "medins_id")
    private String medinsId;
    /**
     * 医疗机构名称
     */
//    @JSONField(name = "medins_name")
    private String medinsName;
    /**
     * 医疗机构行政区划编码
     */
//    @JSONField(name = "medins_admdvs")
    private String medinsAdmdvs;
    /**
     * 医疗服务机构类型
     */
//    @JSONField(name = "medins_type")
    private String medinsType;
    /**
     * 医疗机构等级
     */
//    @JSONField(name = "medins_lv")
    private String medinsLv;
    /**
     * 病区标识
     */
//    @JSONField(name = "wardarea_codg")
    private String wardareaCodg;
    /**
     * 病房号
     */
//    @JSONField(name = "wardno")
    private String wardno;
    /**
     * 病房号
     */
//    @JSONField(name = "bedno")
    private String bedno;
    /**
     * 入院日期
     */
//    @JSONField(name = "adm_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date admDate;
    /**
     * 出院日期
     */
//    @JSONField(name = "dscg_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date dscgDate;
    /**
     * 主诊断编码
     */
//    @JSONField(name = "dscg_main_dise_codg")
    private String dscgMainDiseCodg;
    /**
     * 主诊断名称
     */
//    @JSONField(name = "dscg_main_dise_name")
    private String dscgMainDiseName;
    /**
     * 医师标识
     */
//    @JSONField(name = "dr_codg")
    private String drCodg;
    /**
     * 入院科室编码
     */
//    @JSONField(name = "adm_dept_codg")
    private String admDeptCodg;
    /**
     * 入院科室名称
     */
//    @JSONField(name = "adm_dept_name")
    private String admDeptName;
    /**
     * 出院科室编码
     */
//    @JSONField(name = "dscg_dept_codg")
    private String dscgDeptCodg;
    /**
     * 出院科室名称
     */
//    @JSONField(name = "dscg_dept_name")
    private String dscgDeptName;
    /**
     * 就诊类型
     */
//    @JSONField(name = "med_mdtrt_type")
    private String medMdtrtType;
    /**
     * 医疗类被
     */
//    @JSONField(name = "med_type")
    private String medType;
    /**
     * 生育状态
     */
//    @JSONField(name = "matn_stas")
    private String matnStas;
    /**
     * 医疗总费用
     */
//    @JSONField(name = "medfee_sumamt")
    private BigDecimal medfeeSumamt;
    /**
     * 自费金额
     */
//    @JSONField(name = "ownpay_amt")
    private BigDecimal ownpayAmt;
    /**
     * 自付金额
     */
//    @JSONField(name = "selfpay_amt")
    private BigDecimal selfpayAmt;
    /**
     * 救助金支付金额
     */
//    @JSONField(name = "ma_amt")
    private BigDecimal maAmt;
    /**
     * 个人账户支付金额
     */
//    @JSONField(name = "acct_payamt")
    private BigDecimal acctPayamt;
    /**
     * 统筹金支付金额
     */
//    @JSONField(name = "hifp_payamt")
    private BigDecimal hifpPayamt;
    /**
     * 结算总次数
     */
//    @JSONField(name = "setl_totlnum")
    private BigDecimal setlTotlnum;
    /**
     * 险种
     */
//    @JSONField(name = "insutype")
    private String insutype;
    /**
     * 报销标志
     */

//    @JSONField(name = "reim_flag")
    private String reimFlag;
    /**
     * 异地结算标志
     */
//    @JSONField(name = "out_setl_flag")
    private String outSetlFlag;
    /**
     * 手术操作集合 fsi_operation_dtos
     */
//    @JSONField(name = "fsi_encounter_dtos")
    private List<AnaOperationDTO> fsiEncounterDtos;
    /**
     * 处方(医嘱)信息 fsi_order_dtos
     */
//    @JSONField(name = "fsi_order_dtos")
    private List<AnaOrderDTO> fsiOrderDtos;
    /**
     * 诊断信息DTO  fsi_diagnose_dtos
     */
//    @JSONField(name = "fsi_diagnose_dtos")
    private List<AnaDiagnoseDTO> fsiDiagnoseDtos;
    /**
     * 公务员标志   【0:否,1:是】 -- 海南必传
     */
    private String cvlservFlag;
    /**
     * 处方(医嘱)信息  --海南
     */
    private List<AnaOrderDTO> orderDtos;
    /**
     * 诊断信息   --海南
     */
    private List<AnaDiagnoseDTO> diagnoseDtos;
}
