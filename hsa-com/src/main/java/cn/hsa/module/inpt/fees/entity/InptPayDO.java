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
* @Class_name: InptPayDO
* @Describe: 住院结算方式model 支付方式代码（ZFFS）：0、现金，1、微信，2、支付宝，3、pos，4、转账，5、支票
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InptPayDO extends PageDO implements Serializable {
        //序列号
        private static final long serialVersionUID = -1253413788496082551L;
        //主键
        private String id;
        //医院编码
        private String hospCode;
        //结算ID
        private String settleId;
        //就诊ID
        private String visitId;
        //支付方式代码（ZFFS）
        private String payCode;
        //支付金额
        private BigDecimal price;
        //票据号（微信条码号、支付宝条码号、支票号码）
        private String orderNo;
        //手续费（pos）
        private BigDecimal servicePrice;

}