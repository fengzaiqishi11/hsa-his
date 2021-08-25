package cn.hsa.module.stro.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Package_name: cn.hsa.module.stro.stock.dto
 * @Class_name: CheckStockRespDTO
 * @Describe: 库存校验返回DTO
 * @Author: zhangguorui
 * @Eamil: guorui.zhang@powersi.com
 * @Date: 2021/8/12 9:27
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CheckStockRespDTO implements Serializable {
    private static final long serialVersionUID = -41069628269023745L;
    /**
     * 库存数量
     */
    private BigDecimal strockSplitNum;
    /**
     * 占存数量
     */
    private BigDecimal stockOccupy;
    /**
     * 门诊未结算数量 -- 参数控制
     */
    private BigDecimal totalNumberNoCaculate;
    /**
     * 住院未核收数量 -- 参数控制
     */
    private BigDecimal  totalNumberNoCheck;
    /**
     * 门诊已结算但没有配药的数量
     */
    private BigDecimal prescribeOuptNumber;
    /**
     * 住院已经核收 但是没有配药的数量
     */
    private BigDecimal prescribeInptNumber;

    /**
     * 可用数量
     */
    private BigDecimal result;

}
