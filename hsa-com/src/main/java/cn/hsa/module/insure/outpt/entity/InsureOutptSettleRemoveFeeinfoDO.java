package cn.hsa.module.insure.outpt.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Package_name: cn.hsa.module.insure.outpt.entity
 * @Class_name: InsureOutptSettleRemoveFeeinfoDO
 * @Describe: 统一支付平台-门诊结算撤销-数据集输入（节点标识：feeinfo）
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-02-09 14:13
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureOutptSettleRemoveFeeinfoDO implements Serializable {
    private static final long serialVersionUID = -3789158196251016877L;
    private String medinsListCode; //医院药品项目编码
    private String medinsListName; //医院药品项目名称
    private String drugDosform; //剂型/药品剂型
    private String prdrName; //厂家
    private String spec; //规格
    private String feeOcurTime; //费用发生日期
    private String sinDosSscr; //计量单位
    private String pric; //单价
    private String cnt; //用量
    private String detItemFeeSumamt; //金额
    private String oppSerialFee; //费用序号
    private String rxno; //处方号
    private String bilgDrCode; //处方医生编号
    private String bilgDrName; //处方医生姓名
    private String hospSerial; //医院费用的唯一标识
    private String listType; //项目药品类型
    private String medChrgitmType; //费用统计类别
    private String medListCode; //中心药品项目编码
    private String usageFlag; //用药标志
    private String usageDays; //出院带药天数
    private String opter; //录入工号
    private String opterName; //录入人
    private String optTime; //录入日期
}
