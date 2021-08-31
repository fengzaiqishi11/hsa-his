package cn.hsa.module.inpt.advancepay.dto;

import cn.hsa.module.inpt.advancepay.entity.InptAdvancePayDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Package_name: cn.hsa.module.base.baseadvice.dto
 * @Class_name: BaseAdviceDTO
 * @Describe:
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/7/13 16:07
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InptAdvancePayDTO extends InptAdvancePayDO implements Serializable {

    private static final long serialVersionUID = 412020272708327649L;
    // 医嘱名称，编码，五笔码，拼音码的搜索参数
    private String keyword;
    // 冲红金额
    private BigDecimal redPrice;
    // 冲红人id
    private String redUserId;
    // 冲红人姓名
    private String redName;

    //住院号
    private String inNo;

    /**
     * 预交时间
     */
    private String payTime;
    /**
     * 预交金额
     */
    private String payPrice;
    /**
     * 状态标志
     */
    private String payFlag;

}
