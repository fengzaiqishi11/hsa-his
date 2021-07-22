package cn.hsa.module.insure.module.dto;

import cn.hsa.module.insure.module.entity.InsureIndividualCostDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class ItemInfoDTO extends InsureIndividualCostDO {
    private String medChrgitm; // 医疗收费项目
    private BigDecimal amt; // 金额
    private BigDecimal claaSumfee; // 甲类费用合计
    private BigDecimal clabAmt; // 乙类金额
    private BigDecimal fulamtOwnpayAmt; // 全自费金额
    private BigDecimal othAmt; // 其他金额
    private String insureSettleId;


//    private String medChrgitm; // 医疗收费项目
//    private BigDecimal amt; // 金额
//    private BigDecimal claaSumfee; // 甲类费用合计
//    private BigDecimal clabAmt; // 乙类金额
//    private BigDecimal fulamtOwnpayAmt; // 全自费金额
//    private BigDecimal othAmt; // 其他金额
}
