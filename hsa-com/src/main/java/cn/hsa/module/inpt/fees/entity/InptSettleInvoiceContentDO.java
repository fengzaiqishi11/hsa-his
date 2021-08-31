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
* @Class_name: InptSettleInvoiceContentDO
* @Describe: 住院结算发票内容Model
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InptSettleInvoiceContentDO extends PageDO implements Serializable {
        //序列号
        private static final long serialVersionUID = -4393373402738955178L;
        //主键
        private String id;
        //医院编码
        private String hospCode;
        //结算发票ID（outpt_settle_invoice）
        private String settleInvoiceId;
        //住院发票代码
        private String inCode;
        //住院发票名称
        private String inName;
        //优惠后总金额
        private BigDecimal realityPrice;

}