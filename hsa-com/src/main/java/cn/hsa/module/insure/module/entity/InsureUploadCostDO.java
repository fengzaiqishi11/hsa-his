package cn.hsa.module.insure.module.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 19、费用上传明细表(InsureUploadCost)实体类
 *
 * @author makejava
 * @since 2021-04-15 16:37:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureUploadCostDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -28997311495798122L;
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
    /**
     * 就诊id
     */
    private String visitId;
    /**
     * 就医流水号
     */
    private String mdtrtSn;
    /**
     * 住院/门诊号
     */
    private String iptOtpNo;
    /**
     * 医疗类别
     */
    private String medType;
    /**
     * 收费批次号
     */
    private String chrgBchno;
    /**
     * 费用明细流水号
     */
    private String feedetlSn;
    /**
     * 人员证件类型
     */
    private String psnCertType;
    /**
     * 证件号码
     */
    private String certno;
    /**
     * 人员姓名
     */
    private String psnName;
    /**
     * 费用发生时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date feeOcurTime;
    /**
     * 数量
     */
    private BigDecimal cnt;
    /**
     * 单价
     */
    private BigDecimal pric;
    /**
     * 明细费用总额
     */
    private BigDecimal detItemFeeSumamt;
    /**
     * 医疗目录编码
     */
    private String medListCodg;
    /**
     * 医药机构编码
     */
    private String medinsListCodg;
    /**
     * 医药机构目录名称
     */
    private String medinsListName;
    /**
     * 医疗收费项目类别
     */
    private String medChrgitmType;
    /**
     * 商品名
     */
    private String prodname;
    /**
     * 开单科室编码
     */
    private String bilgDeptCodg;
    /**
     * 开单科室名称
     */
    private String bilgDeptName;
    /**
     * 开单医生编码
     */
    private String bilgDrCodg;
    /**
     * 开单医师姓名
     */
    private String bilgDrName;
    /**
     * 创建人ID
     */
    private String crteId;
    /**
     * 创建人姓名
     */
    private String crteName;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;

    private String costId;

    private String orgCode;

}
