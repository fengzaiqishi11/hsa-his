package cn.hsa.module.payment.dto;

import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.payment.entity.PaymentSettleDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *@Package_name: cn.hsa.module.stro.adjust.dto
 *@Class_name: StroAdjustDetailDTO
 *@Describe: 调价明细
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2020/8/2 8:30
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class  PaymentSettleDTO extends PaymentSettleDO implements Serializable {
    private static final long serialVersionUID = 8482163899015207131L;
    private String startTime;
    private String endTime;
    private String keyword;
    private String authCode; // 付款码
    private BigDecimal totalBillNum; // 总笔数
    private OutptSettleDTO outptSettleDTO;
}
