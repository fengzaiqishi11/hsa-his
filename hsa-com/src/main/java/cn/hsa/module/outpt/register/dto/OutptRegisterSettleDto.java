package cn.hsa.module.outpt.register.dto;

import cn.hsa.module.outpt.register.entity.OutptRegisterSettleDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @Package_name: cn.hsa.module.outpt.register.dto
 * @Class_name: OutptRegisterSettleDto
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/8/18 15:03
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OutptRegisterSettleDto extends OutptRegisterSettleDO {
    private BigDecimal bczhzf;

    private BigDecimal posZf;
    private BigDecimal wxZf;
    private BigDecimal zfbZf;
    private BigDecimal xjZf;
    private BigDecimal zzZf;

    private String currNo;
    private String prefix;
    //退款金额
    private BigDecimal tsxj = BigDecimal.valueOf(0);

}