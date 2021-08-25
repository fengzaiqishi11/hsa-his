package cn.hsa.module.insure.outpt.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @PackageName: cn.hsa.module.insure.outpt.entity
 * @ClassName: InsureOutptSettleDataDO
 * @Describe: 统一支付平台-门诊结算-数据输入（节点标识：data）
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-02-09 17:02
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureOutptSettleDataDO implements Serializable {
    private static final long serialVersionUID = -689396183903340546L;
    private String setlId; //结算ID
    private String mdtrtId; //就诊ID
    private String psnNo; //人员编号
    private String insuplcAdmdvs; //医保中心编码
    private String medinsCode; //医疗机构编码
    private String medinsName; //医疗机构名称
    private String medType; //业务类型
    private String medMdtrtType; //医疗待遇类型
    private String mdtrtDate; //就诊发生日期
    private String opter; //登记人员工号
    private String opterName; //登记人姓名
    private String wardareaCode; //病区编码
    private String wardareaName; //病区名称
    private String deptCode; //就诊科室
    private String deptName; //就诊科室名称
    private String diseCode; //诊断/登记诊断
    private String diseName; //诊断名称/登记诊断名称
    private String saveFlag; //保存标志
    private String setlFlag; //结算标识
    private String photoBase64; //照片base64编码
    private String balc; //个人帐户支付金额
    private String injuryBorthSn; //工伤个人业务序号 （或生育资格认定号）
    private String rxno; //处方号
    private String bilgDrCode; //处方医生编号
    private String bilgDrName; //处方医生姓名
    private String memo; //备注
    private String serialApply; //门诊特殊病业务申请号
    private String cashPay; //刷卡金额
}
