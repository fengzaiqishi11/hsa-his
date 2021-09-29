package cn.hsa.module.insure.outpt.entity;

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

/** 清算申请
 * @Package_name:
 * @Class_name: InsureReckonDO
 * @Describe:
 * @Author: liaojiguang
 * @Email: liaojiguang@powersi.com.cn
 * @Date: 2021/9/8 17:50
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureReckonDO extends PageDO implements Serializable {

    /** 版本号 */
    private static final long serialVersionUID = -5298214259610217086L;

    /** 主键ID */
    private String id;

    /** 医院编码 */
    private String hospCode;

    /** 医保注册编码 */
    private String insureRegCode;

    /** 医疗机构编码 */
    private String medicineOrgCode;

    /** 清算类别 */
    private String clrType;

    /** 清算方式 */
    private String clrWay;

    // 清算中心
    private String clrOptins;

    /** 清算年月 */
    private String setlym;

    /** 清算人数 */
    private Integer psntime;

    /** 医疗总金额 */
    private BigDecimal medfeeSumamt;

    /** 医保认可费用总额 */
    private BigDecimal medSumfee;

    /** 基金申报总额 */
    private BigDecimal fundAppySum;

    /** 现金支付金额 */
    private BigDecimal cashPayamt;

    /** 个人账户支出 */
    private BigDecimal acctPay;

    /** 开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date begndate;

    /** 结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date enddate;

    /** 是否申报 */
    private String isDeclare;

    /** 申报时间 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date declareTime;

    /** 申报人姓名 */
    private String declareName;

    /** 机构清算申请事件ID */
    private String clrAppyEvtId;

    /** 创建人姓名 */
    private String crteName;

    /** 创建人id */
    private String crteId;

    /** 创建时间 */
    private Date crteTime;

}
