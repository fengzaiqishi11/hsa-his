package cn.hsa.module.inpt.fees.dto;

import cn.hsa.module.inpt.fees.entity.InptCostSettleDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
* @Package_name: cn.hsa.module.inpt.fees.dto
* @Class_name: InptCostSettleDTO
* @Describe: 结算费用明细DTO
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InptCostSettleDTO extends InptCostSettleDO implements Serializable {
        //序列号
        private static final long serialVersionUID = 6992971125305972695L;
}