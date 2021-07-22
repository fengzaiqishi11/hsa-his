package cn.hsa.module.insure.inpt.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Package_name: cn.hsa.module.insure.inpt.entity
 * @Class_name: InsureInptSettleRemoveSetldetailDO
 * @Describe: 统一支付平台-住院结算撤销-输出-结算基金分项信息（节点标识：setldetail）
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-02-09 15:16
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InsureInptSettleRemoveSetldetailDO implements Serializable {
    private static final long serialVersionUID = -2205197929708023466L;
    private String fundPayType; //基金支付类型
    private BigDecimal inscpScpAmt; //符合政策范围金额
    private BigDecimal crtPaybLmtAmt; //本次可支付限额金额
    private BigDecimal fundPayamt; //基金支付金额
    private String fundPayTypeName; //基金支付类型名称
    private String setlProcInfo; //结算过程信息
}
