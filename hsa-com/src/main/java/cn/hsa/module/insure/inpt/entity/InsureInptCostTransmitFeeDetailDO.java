package cn.hsa.module.insure.inpt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Package_name: cn.hsa.module.insure.inpt.entity
 * @class_name: InsureInptCostTransmitFeeDetailDO
 * @Description:  医保统一支付：住院结算费用明细上传参数（第二部分）费用明细实体类
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/2/9 14:49
 * @Company: 创智和宇
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InsureInptCostTransmitFeeDetailDO implements Serializable {
    // 费用明细流水号
    private String feedetlSn ;
    // 原费用流水号
    private String initFeedetlSn ;
    // 就诊ID
    private String mdtrtId ;
    // 医嘱号
    private String drordNo ;
    // 人员编号
    private String psnNo;
    // 医疗类别
    private String medType ;
    // 费用发生时间
    private Date feeOcurTime ;
    // 医疗目录编码
    private String medListCodg ;
    // 医药机构目录编码
    private String medinsListCodg ;
    // 明细项目费用总额
    private BigDecimal detItemFeeSumamt ;
    // 数量
    private BigDecimal cnt ;
    // 单价
    private BigDecimal pric ;
    // 开单科室编码
    private String bilgDeptCodg ;
    // 开单科室名称
    private String bilgDeptName;
    // 开单医生编码
    private String bilgDrCodg;
    // 开单医师姓名
    private String bilgDrName;
    // 受单科室编码
    private String acordDeptCodg;
    // 受单科室名称
    private String acordDeptName;
    // 受单医生编码
    private String ordersDrCode;
    // 受单医生姓名
    private String ordersDrName;
    // 医院审批标志
    private String hospApprFlag ;
    // 中药使用方式
    private String tcmdrugUsedWay ;
    // 外检标志
    private String etipFlag ;
    // 外检医院编码
    private String etipHospCode ;
    // 出院带药标志
    private String dscgTkdrugFlag;
    // 生育费用标志
    private String matnFeeFlag ;
    // 备注
    private String memo ;
    // 项目药品类型
    private String listType ;
    // 医院药品项目编码
    private String medinsListCode;
    // 医院药品项目名称
    private String medinsListName ;
    // 药品本位码
    private String drugStandCode ;
    // 剂型
    private String drugDosform ;
    // 厂家
    private String prdrName ;
    // 规格
    private String spec ;
    // 计量单位
    private String sinDosDscr ;
    // 用量
//    private String cnt ;
//    // 用药标志
    private String usedFlag ;
    // 出院带药天数
    private String dscgTkdrugDays ;
    // 对应费用序列号
    private String oppSerialFee;
    // 录入人工号
    private String opter;
    // 录入人姓名
    private String opterName;
    // 处方号
    private String rxno;
    //人员医疗费用明细ID
    private String feedetlId;
    // 收费项目等级
    private String chrgitmLv;
    // 医患最终结算日期
    private String seltDate;
    // 中心药品项目编码
    private String medListCode;
    // 中心药品项目名称
    private String medListName;
    // 药监局药品编码
    private String orgDrugCode;
    //费用批次
    private String feeBchno;
    // 退费金额
    private String refdAmt;
    // 录入时间
    private String optTime;
    // 费用冻结标志，用来表识参保人所在单位的基本医疗保险被冻结期间录入的费用。0：未冻结；1：已冻结；2：冻结已处理
    private String fundStas;
    // 费用上传时间
    private String uploadTime;
    // 城职对应待遇值（自付比例支付类型）
    private String citySelfpayProp;
    // 是否在岗医师标识：0，非在岗；1，在岗
    private String workStats;
    // 超时标志，0未超时，1超时上传未申诉，2超时上传正在申诉，3超时上传申诉审核同意，4超时上传申诉审核不同意
    private String outTimeFlag;
    // 用药频次描述
    private String frquDscr ;
    // 用药周期天数
    private String prdDays;
    // 用药途径描述
    private String medcWayDscr;
    // 单次剂量
    private String sinDos;
    // 单次剂量单位
    private String sinDosunt;
    // 使用天数
    private String usedDays;
    // 发药总量
    private String dismedAmt;
    // 发药总量单位
    private String dismedUnt;
    // 不进行审核标志
    private String unchkFlag;
    // 不进行审核说明
    private String unchkMemo;


}
