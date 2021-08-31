package cn.hsa.module.insure.inpt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.insure.inpt.entity
 * @class_name: InsureInptTrialSettleDO
 * @Description: 住院结算：预结算参数信息
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/2/9 15:56
 * @Company: 创智和宇
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InsureInptTrialSettleDO {
    // 人员编号
    private String psnNo;
    // 就诊凭证类型
    private String mdtrtCertType;
    // 就诊凭证编号
    private String mdtrtCertNo;
    // 医疗费总额
    private BigDecimal medfeeSumamt;
    // 个人结算方式
    private String psnSetlway;
    // 就诊ID
    private String mdtrtId;
    // 个人账户使用标志
    private String acctUsedFlag;
    // 险种类型
    private String insutype;
    // 发票号
    private String invono;
    // 中途结算标志
    private String midSetlFlag;
    // 全自费金额
    private BigDecimal fulamtOwnpayAmt;
    // 超限价金额
    private BigDecimal overlmtSelfpay;
    //  先行自付金额
    private BigDecimal preselfpayAmt;
    //  符合政策范围金额
    private BigDecimal inscpScpAmt ;
    // 保存标志
    private String saveFlag;
    // 医疗机构编码
    private String medinsCode;
    // 就医登记号
    private String serialNo;
    // 本次业务个人帐户可用金额
    private String balc;
    // 出院疾病
    private String dscgDiag;
    // 出院诊断名称
    private String dscgDiagName;
    //出院日期
    private Date endtime;
    // 第一副诊断
    private String firstAsistDiag;
    // 第二副诊断
    private String secdAsistDiag;
    // 出院详情
    private String dscgCond;
    // 操作员工号
    private String opter;
    // 操作员姓名
    private String opterName;
    // 待遇类别
    private String medMdtrtType;
    // 单据号
    private String billNo;
    // 生育就诊类型
    private String matnMdtrtType;
    // 生育疾病类型
    private String matnDiseTyp;
    // 特殊情况对应的申请序号
    private String serialApply;
    // 刷卡金额
    private String cashPay;
    // 处方医生编号
    private String bilgDrCode;
    // 处方医生姓名
    private String bilgDrName;
    // 医疗机构疾病诊断
    private String medinsDiagCode;
    // 医疗机构第一副诊断
    private String medinsFirstAsistDiag;
    // 医疗机构第二副诊断
    private String medinsSecdAsistDiag;
    // 卡识别码
    private String cardSn;
    // 医疗机构订单号或医疗机构就医序列号
    private String orderNo;
    // 就诊方式
    private String mdtrtMode;
    // 持卡就诊基本信息
    private String hcardBasinfo;
    // 持卡就诊校验信息
    private String hcardChkinfo;

}
