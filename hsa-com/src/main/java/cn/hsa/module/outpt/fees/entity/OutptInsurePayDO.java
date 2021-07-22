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
* @Class_name: OutptInsurePayDO
* @Describe: 合同单位结算情况Model   合同单位明细代码： 0、个人账户，1、基本医疗，
* @Author: Ou·Mr
* @Email: oubo@powersi.com.cn
* @Date: 2020/7/30 21:12
* @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OutptInsurePayDO extends PageDO implements Serializable {
        //序列号
        private static final long serialVersionUID = 364538489795237521L;
        //主键
        private String id;
        //医院编码
        private String hospCode;
        //结算ID
        private String settleId;
        //就诊ID
        private String visitId;
        //合同单位明细代码（HTDW）
        private String typeCode;
        //医保机构编码
        private String orgNo;
        //医保机构名称
        private String orgName;
        //医保报销总金额
        private BigDecimal totalPrice;

}