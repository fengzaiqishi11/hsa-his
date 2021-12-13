package cn.hsa.module.inpt.patientcomprehensivequery.dto;

import cn.hsa.base.PageDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Package_name: cn.hsa.module.inpt.patientcomprehensivequery.dto
 * @Class_name: outptCostAndReigsterCostDTO
 * @Describe: 门诊和挂号已结算费用的DTO
 * @Author: xingyu.xie
 * @Eamil: xingyu.xie@powersi.com.cn
 * @Date: 2020/12/11 11:44
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class OutptCostAndReigsterCostDTO extends PageDO implements Serializable {

    private static final long serialVersionUID = -417251951666351706L;
    // 优惠后总金额
    private BigDecimal realityPrice;
    // 财务分类名
    private String bfcName;
    // 财务分类Id
    private String bfcId;
    // 开方/挂号 医生ID
    private String doctorId;
    // 开方/挂号 医生名
    private String doctorName;
    // 开方/挂号 科室ID
    private String deptId;
    // 开方/挂号 科室名
    private String deptName;
    // 就诊ID
    private String visitId;
    // 就诊名称
    private String visitName;
    // 收费员id
    private String chargeId;
    // 收费员姓名
    private String chargeName;
}
