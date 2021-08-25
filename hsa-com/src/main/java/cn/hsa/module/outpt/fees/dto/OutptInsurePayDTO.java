package cn.hsa.module.outpt.fees.dto;

import cn.hsa.module.outpt.fees.entity.OutptInsurePayDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
* @Package_name: cn.hsa.module.outpt.fees.dto
* @Class_name: OutptInsurePayDTO
* @Describe: 合同单位结算情况ModelDTO;合同单位结算情况表   合同单位明细代码： 0、个人账户，1、基本医疗，
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutptInsurePayDTO extends OutptInsurePayDO implements Serializable {
        //序列号
        private static final long serialVersionUID = 364538489795237521L;
}