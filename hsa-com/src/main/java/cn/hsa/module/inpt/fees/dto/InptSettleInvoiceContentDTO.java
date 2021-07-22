package cn.hsa.module.inpt.fees.dto;

import cn.hsa.module.inpt.fees.entity.InptSettleInvoiceContentDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
* @Package_name: cn.hsa.module.inpt.fees.dto
* @Class_name: InptSettleInvoiceContentDTO
* @Describe: 住院结算发票内容DTO
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InptSettleInvoiceContentDTO extends InptSettleInvoiceContentDO implements Serializable {
        //序列号
        private static final long serialVersionUID = -4393373402738955178L;
}