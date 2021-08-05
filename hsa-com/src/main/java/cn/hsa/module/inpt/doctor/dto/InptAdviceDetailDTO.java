package cn.hsa.module.inpt.doctor.dto;

import cn.hsa.module.inpt.doctor.entity.InptAdviceDetailDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *@Package_name: cn.hsa.module.inpt.dto
 *@Class_name: InptAdviceDetailDTO
 *@Describe: 住院医嘱
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020-09-01 19:46:53
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class InptAdviceDetailDTO extends InptAdviceDetailDO implements Serializable {

    private static final long serialVersionUID = -1277937909549637353L;

    //就诊名称
    private String visitName;
    private BigDecimal preferentialPrice; //优惠金额
    private BigDecimal realityPrice;    // 优惠后金额
    private BigDecimal valuationPrice;  //计价金额
    private String babyId;   // 婴儿id
    private String queryBaby; // 是否查询婴儿
    private String isHospFlag;
    /**
     * 限制使用标志
     */
    private String limUserExplain;
    /**
     * 限制使用说明
     */
    private String lmtUserFlag;
    /**
     * 是否报销
     */
    private String isReimburse;
    private String hospItemCode;

}
