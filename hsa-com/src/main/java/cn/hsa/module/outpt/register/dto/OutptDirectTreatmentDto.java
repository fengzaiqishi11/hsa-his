package cn.hsa.module.outpt.register.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Package_name: cn.hsa.module.outpt.register.dto
 * @Class_name: OutptDoctorRegisterDto
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/8/12 11:32
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class OutptDirectTreatmentDto{
    /**
     * 主键
     */
    private String id;
    /**
     * 医院编码
     */
    private String hospCode;
}