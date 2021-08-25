package cn.hsa.module.outpt.register.dto;

import cn.hsa.module.outpt.register.entity.OutptRegisterPayDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Package_name: cn.hsa.module.outpt.register.dto
 * @Class_name: OutptRegisterPayDto
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/8/18 15:03
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OutptRegisterPayDto extends OutptRegisterPayDO {
    private String a;
}