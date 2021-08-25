package cn.hsa.module.outpt.fees.dto;

import cn.hsa.module.outpt.fees.entity.OutptSettleInvoiceContentDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
* @Package_name: cn.hsa.module.outpt.fees.dto
* @Class_name: OutptSettleInvoiceContentDTO
* @Describe: 票据内容ModelDTO
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutptSettleInvoiceContentDTO extends OutptSettleInvoiceContentDO implements Serializable {
        //序列号
        private static final long serialVersionUID = -5161692447068585997L;
}
