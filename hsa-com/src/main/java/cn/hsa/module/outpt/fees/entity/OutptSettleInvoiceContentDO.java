package cn.hsa.module.outpt.fees.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
/**
* @Package_name: cn.hsa.module.outpt.fees.entity
* @Class_name: OutptSettleInvoiceContentDO
* @Describe: 票据内容Model
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutptSettleInvoiceContentDO extends PageDO implements Serializable {
        //序列号
        private static final long serialVersionUID = -5161692447068585997L;
        //主键
        private String id;
        //医院编码
        private String hospCode;
        //结算发票ID（outpt_settle_invoice）
        private String settleInvoiceId;
        //门诊发票代码
        private String outCode;
        //门诊发票名称
        private String outName;
        //优惠后总金额
        private BigDecimal realityPrice;


    public String getFpkey() {
        return this.settleInvoiceId + this.outName;
    }
}
