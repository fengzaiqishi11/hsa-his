package cn.hsa.module.inpt.fees.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
* @Package_name: cn.hsa.module.inpt.fees.entity
* @Class_name: InptSettleInvoiceDO
* @Describe: 住院结算发票情况model
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InptSettleInvoiceDO extends PageDO implements Serializable {
        //序列号
        private static final long serialVersionUID = 5492814188100894565L;
        //主键
        private String id;
        //医院编码
        private String hospCode;
        //结算ID
        private String settleId;
        //就诊ID
        private String visitId;
        //发票ID
        private String invoiceId;
        //发票明细ID
        private String invoiceDetailId;
        //发票号码
        private String invoiceNo;
        //发票总金额
        private BigDecimal totalPrice;
        //发票打印人ID
        private String printId;
        //发票打印人姓名
        private String printName;
        //发票打印时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date printTime;
        //发票打印次数
        private Integer printNum;
        //状态标志代码（ZTBZ）
        private String statusCode;
        //冲红ID
        private String redId;

}