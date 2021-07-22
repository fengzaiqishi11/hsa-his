package cn.hsa.module.insure.inpt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @Package_name: cn.hsa.module.insure.inpt.entity
 * @class_name: InsureInptCostTransmitDataOutDataDO
 * @Description: T住院结算：费用明细上传返回参数
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/2/9 15:40
 * @Company: 创智和宇
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InsureInptCostTransmitDataOutDataDO {
    // 费用明细流水号
    private String feedetlSn ;
    // 明细项目费用总额
    private BigDecimal detItemFeeSumamt;
    // 数量
    private BigDecimal cnt;
    // 单价
    private BigDecimal pric;
    // 定价上限金额
    private BigDecimal pricUplmtAmt ;
    // 自付比例
    private BigDecimal selfpayProp;
    // 全自费金额
    private BigDecimal fulamtOwnpayAmt;
    // 超限价金额
    private BigDecimal overlmtAmt;
    // 先行自付金额
    private BigDecimal preselfpayAmt ;
    // 符合政策范围金额
    private BigDecimal inscpScpAmt;
    // 收费项目等级
    private String chrgitmLv;
    // 医疗收费项目类别
    private String medChrgitmType;
    // 基本药物标志
    private String basMednFlag ;
    // 医保谈判药品标志
    private String hiNegoDrugFlag;
    // 儿童用药标志
    private String chldMedcFlag;
    // 目录特项标志
    private String listSpItemFlag;
    // 限制使用标志
    private String lmtUsedFlag ;
    // 直报标志
    private String drtReimFlag;
    // 备注
    private String memo;
}
