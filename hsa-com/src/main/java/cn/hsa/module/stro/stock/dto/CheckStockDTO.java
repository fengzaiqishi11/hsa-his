package cn.hsa.module.stro.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Package_name: cn.hsa.module.stro.stock.dto
 * @Class_name: CheckStockDTO
 * @Describe: 库存校验dto
 * @Author: zhangguorui
 * @Eamil: guorui.zhang@powersi.com
 * @Date: 2021/8/10 20:14
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CheckStockDTO implements Serializable {
    private static final long serialVersionUID = -41069628269023745L;

    /** 医生开处方/医嘱时校验库存时取未结算/未核收数量的时间间隔 **/
    private String wjsykc;
    /**
     * 领药药房ID
     */
    private String pharId;
    /**
     * 项目ID（项目/材料）
     */
    private String itemId;
    /**
     * 医院编码
     */
    private String hospCode;

    /**
     * 总数量单位
     */
    private String totalNumUnitCode;

    /**
     * 总数量
     */
    private BigDecimal totalNum;
    /**
     * 频率Id
     */
    private String rateId;
    /**
     * 天数
     */
    private Integer useDays;
    /**
     * 数量
     */
    private BigDecimal num;

    /**
     * 门诊处方明细id/或者 医嘱id
     */
    private String id;
    /**
     * 处方id:新增的时候处方id为null，编辑的时候处方id不为空。
     * 所以编辑的时候忽略掉当前处方的所有药品数量
     */
    private String opId;
}
