package cn.hsa.module.inpt.fees.entity;

import cn.hsa.base.PageDO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 合同单位明细代码：
0、个人账户，1、基本医疗，2、补充医疗，3、民政，4、协议支付(InptInsurePay)实体类
 *
 * @author zhongming
 * @since 2020-09-28 09:24:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InptInsurePayDO extends PageDO implements Serializable {
    private static final long serialVersionUID = -98484987344096023L;
    /**
    * 主键
    */
    private String id;
    /**
    * 医院编码
    */
    private String hospCode;
    /**
    * 结算ID
    */
    private String settleId;
    /**
    * 就诊ID
    */
    private String visitId;
    /**
    * 合同单位明细代码（HTDW）
    */
    private String typeCode;
    /**
    * 医保机构编码
    */
    private String orgNo;
    /**
    * 医保机构名称
    */
    private String orgName;
    /**
    * 医保报销总金额
    */
    private BigDecimal totalPrice;

}