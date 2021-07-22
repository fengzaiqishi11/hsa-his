package cn.hsa.module.insure.module.entity;

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
 * 结算清单信息表(InsureSetlInfo)实体类
 *
 * @author makejava
 * @since 2021-04-16 15:33:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureSetlInfoDO implements Serializable {
    private static final long serialVersionUID = -43448456795346079L;
    /**
     * 主键ID
     */
    private String id;

    private String hospCode; //医院编码
    /**
     * 就诊id
     */
    private String visitId;
    /**
     * 结算id
     */
    private String settleId;
    /**
     * 定点医药机构名称
     */
    private String fixmedinsName;
    /**
     * 定点医疗机构编号
     */
    private String fixmedinsCode;
    /**
     * 医保结算等级
     */
    private String hiSetlLv;
    /**
     * 医保编号
     */
    private String hiNo;
    /**
     * 病案号
     */
    private String medcasno;
    /**
     * 申报时间
     */
    private Date dclaTime;
    /**
     * 人员姓名
     */
    private String psnName;
    /**
     * 性别
     */
    private String gend;
    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date brdy;
    /**
     * 年龄
     */
    private int age;
    /**
     * 国籍
     */
    private String ntly;
    /**
     * 年龄（不足一岁）
     */
    private Integer nebAge;
    /**
     * 民族
     */
    private String naty;
    /**
     * 患者证件类别
     */
    private String patnCertType;
    /**
     * 证件号码
     */
    private String certno;
    /**
     * 职业
     */
    private String prfs;
    /**
     * 现住址
     */
    private String currAddr;
    /**
     * 单位名称
     */
    private String empName;
    /**
     * 单位地址
     */
    private String empAddr;
    /**
     * 单位电话
     */
    private String empTel;
    /**
     * 邮编
     */
    private String poscode;
    /**
     * 联系人姓名
     */
    private String conerName;
    /**
     * 与患者关系
     */
    private String patnRlts;
    /**
     * 联系人地址
     */
    private String conerAddr;
    /**
     * 联系人电话
     */
    private String conerTel;
    /**
     * 医保类型
     */
    private String hiType;
    /**
     * 参保地
     */
    private String insulic;
    /**
     * 特殊人员类型
     */
    private String spPsnType;
    /**
     * 新生儿入院类型
     */
    private String nwbAdmType;
    /**
     * 新生儿出生体重
     */
    private BigDecimal nwbBirWt;
    /**
     * 新生儿入院体重
     */
    private BigDecimal nwbAdmWt;
    /**
     * 门诊慢特病诊断科别
     */
    private String opspDiagCaty;
    /**
     * 门诊慢特病就诊日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date opspMdtrtDate;
    /**
     * 住院医疗类型
     */
    private String iptMedType;
    /**
     * 入院途径
     */
    private String admWay;
    /**
     * 治疗类别
     */
    private String trtType;
    /**
     * 入院时间
     */
    private Date admTime;
    /**
     * 入院科别
     */
    private String admCaty;
    /**
     * 转科科别
     */
    private String refldeptDept;
    /**
     * 出院时间
     */
    private Date dscgTime;
    /**
     * 出院科别
     */
    private String dscgCaty;
    /**
     * 实际住院天数
     */
    private String actIptDays;
    /**
     * 门（急）诊西医诊断
     */
    private String optWmDise;
    /**
     * 西医诊断疾病代码
     */
    private String wmDiswCode;
    /**
     * 门（急）诊中医诊断
     */
    private String optTcmDise;
    /**
     * 中医诊断代码
     */
    private String tcmDiseCode;

    private String inSituationName; // 入院病情
    /**
     * 诊断代码计数
     */
    private String diagCodeCnt;
    /**
     * 手术操作代码计数
     */
    private String oprnOprtCodeCnt;
    /**
     * 呼吸机使用时长
     */
    private String ventUsedDurg;
    /**
     * 颅脑损伤患者入院前昏迷时长
     */
    private String pwcryBfadmcomaDura;
    /**
     * 颅脑损伤患者入院后昏迷时长
     */
    private String pwcryAfadmcomaDura;
    /**
     * 输血品种
     */
    private String bldCat;
    /**
     * 输血量
     */
    private String bldAmt;
    /**
     * 输血计量单位
     */
    private String bldUnt;
    /**
     * 特级护理天数
     */
    private String spgaNurscareDays;
    /**
     * 一级护理天数
     */
    private String lv1NurscareDays;
    /**
     * 二级护理天数
     */
    private String scdNurscareDays;
    /**
     * 三级护理天数
     */
    private String lv3NursecareDays;
    /**
     * 离院方式
     */
    private String dscgWay;
    /**
     * 拟接收机构名称
     */
    private String acpMedinsName;
    /**
     * 拟接收机构代码
     */
    private String acpOptinsCode;
    /**
     * 票据代码
     */
    private String billCode;
    /**
     * 票据号码
     */
    private String billNo;
    /**
     * 业务流水号
     */
    private String bizSn;
    /**
     * 出院31天内再住院计划标志
     */
    private String daysRinpFlag31;
    /**
     * 出院31天内再住院目的
     */
    private String daysRinpPup31;
    /**
     * 主诊医师姓名
     */
    private String chfpdrName;
    /**
     * 主诊医师代码
     */
    private String chfpdrCode;
    /**
     * 结算开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date setlBegnDate;
    /**
     * 结算结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date setlEnDate;
    /**
     * 个人自付
     */
    private BigDecimal psnSelfpay;
    /**
     * 个人自费
     */
    private BigDecimal psnOwnpay;
    /**
     * 个人账户支出
     */
    private BigDecimal acctPay;
    /**
     * 个人现金支付
     */
    private BigDecimal psnCashpay;
    /**
     * 医保支付方式
     */
    private String hiPaymtd;
    /**
     * 医保机构
     */
    private String hsorg;
    /**
     * 医保机构经办人
     */
    private String hsorgOpter;
    /**
     * 医疗机构填报部门
     */
    private String medinsFillDept;
    /**
     * 医疗机构填报人
     */
    private String medinsFillPsn;
    /**
     * 操作人id
     */
    private String crteId;
    /**
     * 操作人姓名
     */
    private String crteName;
    /**
     * 操作时间
     */
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crteTime;

    private String settleNo;



}
