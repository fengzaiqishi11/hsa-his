package cn.hsa.module.inpt.fees.dto;

import cn.hsa.module.inpt.fees.entity.InptPayDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
* @Package_name: cn.hsa.module.inpt.fees.dto
* @Class_name: InptPayDTO
* @Describe: 支付方式DTO；支付方式代码（ZFFS）：0、现金，1、微信，2、支付宝，3、pos，4、转账，5、支票
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InptPayDTO extends InptPayDO implements Serializable {
        //序列号
        private static final long serialVersionUID = -1253413788496082551L;

        private String payName;
}