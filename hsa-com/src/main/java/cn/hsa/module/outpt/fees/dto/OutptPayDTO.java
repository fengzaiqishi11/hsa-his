package cn.hsa.module.outpt.fees.dto;

import cn.hsa.module.outpt.fees.entity.OutptPayDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* @Package_name: cn.hsa.module.outpt.fees.dto
* @Class_name: OutptPayDTO
* @Describe: 门诊支付方式ModelDTO;支付方式代码（ZFFS）：0、现金，1、微信，2、支付宝，3、pos，4、转账，5、支票
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutptPayDTO extends OutptPayDO implements Serializable {
        //序列号
        private static final long serialVersionUID = 3282343617017236521L;

        // 实收金额
        private BigDecimal realityPrice;

        // 退款金额
        private BigDecimal outPrice;
}