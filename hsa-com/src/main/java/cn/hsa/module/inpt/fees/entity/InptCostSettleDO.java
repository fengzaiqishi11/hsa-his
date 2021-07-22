package cn.hsa.module.inpt.fees.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* @Package_name: cn.hsa.module.inpt.fees.entity
* @Class_name: InptCostSettleDO
* @Describe: 结算费用明细Model
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InptCostSettleDO extends PageDO implements Serializable {
        //序列号
        private static final long serialVersionUID = 6992971125305972695L;
        //主键
        private String id;
        //医院编码
        private String hospCode;
        //就诊ID
        private String visitId;
        //婴儿ID
        private String babyId;
        //结算ID（inpt_settle.id）
        private String settleId;
        //费用ID（inpt_cost.id）
        private String costId;

        // 优惠后金额
        private BigDecimal realityPrice;

}