package cn.hsa.module.outpt.register.dto;

import cn.hsa.module.outpt.register.entity.OutptRegisterDetailDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @Package_name: cn.hsa.module.outpt.register.dto
 * @Class_name: OutptRegisterDetailDto
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/8/18 15:02
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OutptRegisterDetailDto extends OutptRegisterDetailDO {
    private String itemUnitCode; //项目单位
    private BigDecimal itemPrice; //项目单价
    private String name;  //项目名称
    private BigDecimal itemPriceAll; //项目总价格
    private BigDecimal itemPreferentialPrice; //优惠金额
    private BigDecimal itemPreferentialAllPrice; //优惠后金额
    private String bfcCode;  //财务分类
    private String bfcId;  //财务分类
}