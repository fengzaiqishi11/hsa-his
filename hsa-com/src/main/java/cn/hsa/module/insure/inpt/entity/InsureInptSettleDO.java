package cn.hsa.module.insure.inpt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @Package_name: cn.hsa.module.insure.inpt.entity
 * @class_name: InsureInptSettleDO
 * @Description: 住院结算：住院结算参数
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/2/9 16:36
 * @Company: 创智和宇
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InsureInptSettleDO {
    private String psnNo;    //	人员编号
    private String mdtrtCertType;    //	就诊凭证类型
    private String mdtrtCertNo;    //	就诊凭证编号
    private BigDecimal medfeeSumamt;    //	医疗费总额
    private String psnSetlway;    //	个人结算方式
    private String mdtrtId;    //	就诊ID
    private String insutype;    //	险种类型
    private String acctUsedFlag;    //	个人账户使用标志
    private String invono;    //	发票号
    private String midSetlFlag;    //	中途结算标志
    private BigDecimal fulamtOwnpayAmt;    //	全自费金额
    private BigDecimal overlmtSelfpay;    //	超限价金额
    private BigDecimal preselfpayAmt;    //	先行自付金额
    private BigDecimal inscpScpAmt;    //	符合政策范围金额
    private String saveFlag;    //	保存标志
    private String medinsCode;    //	医疗机构编码
    private String balc;    //	本次业务个人帐户可用金额
    private String dscgDiag;    //	出院疾病
    private String dscgDiagName;    //	出院诊断名称
    private String firstAsistDiag;    //	第一副诊断
    private String secdAsistDiag;    //	第二副诊断
    private String dscgCond;    //	出院详情
    private String opter;    //	操作员工号
    private String opterName;    //	操作员姓名
    private String medMdtrtType;    //	待遇类别
    private String billNo;    //	单据号
    private String matnMdtrtType;    //	生育就诊类型
    private String matnDiseType;    //	生育疾病类型
    private String serialApply;    //	特殊情况对应的申请序号
    private String cashPay;    //	刷卡金额
    private String bilgDrCode;    //	处方医生编号
    private String bilgDrName;    //	处方医生姓名
    private String medinsDiag;    //	医疗机构疾病诊断
    private String medinsFirstAsistDiag;    //	医疗机构第一副诊断
    private String medinsSecdAsistDiag;    //	医疗机构第二副诊断
    private String endtime;    //	业务结束时间
    private String begntime;    //	业务开始时间
    private String dscgStas;    //	出院转归情况
    private BigDecimal acctPay;    //	使用个帐金额
    private String matnType;    //	生育类别
    private String operDate;    //	手术日期
    private String fetusCnt;    //	胎次
    private Integer fetusNum;    //	胎儿数
    private String birthCertNo;    //	出生证号
    private String oprnWay;    //	手术方式
    private String gesoVal;    //	妊娠周期
    private String isElectCert;    //	是否医保电子凭证刷卡
    private String electCertno;    //	扫描医保电子凭证返回的值
    private String certno;    //	身份证号
    private String psnCertType;    //	证件类型
    private String electCertToken;    //	医保电子凭证Token
    private String orderNo;    //	医疗机构订单号或医疗机构就医序列号
    private String mdtrtMode;    //	就诊方式
    private String hcardBasinfo;    //	持卡就诊基本信息
    private String hcardChkinfo;    //	持卡就诊校验信息

}
