package cn.hsa.module.insure.module.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
* @Package_name: 
* @Class_name: DO
* @Describe: 表含义： insure：医保 Individual：个人 visit：就诊                                             -&#
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureIndividualVisitDO extends PageDO implements Serializable {
        //序列号
        private static final long serialVersionUID = -5344033250932437962L;
        //主键id
        private String id;
        //医院编码
        private String hospCode;
        //患者就诊id
        private String visitId;
        //个人基本信息id
        private String mibId;
        //医保机构编码
        private String insureOrgCode;
        //医保注册编码
        private String insureRegCode;
        //医疗机构编码
        private String medicineOrgCode;
        //是否住院（SF）
        private String isHospital;
        //住院号/就诊号
        private String visitNo;
        //个人电脑号
        private String aac001;
        //医保登记号
        private String medicalRegNo;
        //业务类型
        private String aka130;
        //业务类型名称
        private String aka130Name;
        //待遇类型
        private String bka006;
        //待遇类型名称
        private String bka006Name;
        //业务申请号
        private String injuryBorthSn;
        //就诊疾病编码
        private String visitIcdCode;
        //就诊疾病名称
        private String visitIcdName;
        //就诊时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date visitTime;
        //就诊科室ID
        private String visitDrptId;
        //就诊科室名称
        private String visitDrptName;
        //就诊病区ID
        private String visitAreaId;
        //就诊病区名称
        private String visitAreaName;
        //就诊床位
        private String visitBerth;
        //起付线金额
        private BigDecimal startingPrice;
        //转入医院编码
        private String shiftHospCode;
        //转出医院编码
        private String outHospCode;
        //住院原因
        private String cause;
        //备注
        private String remark;
        //创建人ID
        private String crteId;
        //创建人姓名
        private String crteName;
        //是否电子凭证医保
        private String isEcqr;
        //是否电子凭证医保
        private String payOrdId;
        //是否电子凭证医保
        private String payToken;
        //创建时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date crteTime;

        // 参保地医保区划
        private String insuplcAdmdvs;
        // 就诊凭证类型
        private String mdtrtCertType;
        // 就诊凭证编号
        private String mdtrtCertNo;
        // 副诊断疾病名称
        private String  accessoryDiagnosisName ;
        // 副诊断疾病编码
        private String  accessoryDiagnosisCode;
        // 交易信息(原交易)中的msgid,发送方报文ID
        private String omsgid;
        // 交易信息(原交易)中的infno
        private String oinfno;
        private String pretFlag; //  早产标志
        private int  fetusCnt ; // 胎儿数
        private int fetts; // 胎次
        private String latechbFlag ; // 晚育标志
        private String birctrlType; // 计划生育手术类别
        private String matnType ; // 生育类别
        private String fpscNo ; // 计划生育服务证号
        private int gesoVal; //孕周数
        @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date birctrlMatnDate ; // 计划生育手术或生育日期

        // 是否一站式
        private String isOneSettle;

        // 人员身份类别
        private String psnIdetType;

        // 人员身份类别码值
        private String psnIdetTypeName;

        // 开始时间
        private Date idetStartDate;

        // 结算时间
        private Date idetEndDate;

        // 登记时获取的人员信息
        private String payTokenDengJi;

        private String hcardBasinfo; // 广州读卡就医基本信息

        private String hcardChkinfo; // 广州读卡就医校验信息
}