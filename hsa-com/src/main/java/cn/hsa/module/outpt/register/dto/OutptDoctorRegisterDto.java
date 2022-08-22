package cn.hsa.module.outpt.register.dto;

import cn.hsa.module.outpt.register.entity.OutptDoctorRegisterDO;
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
public class OutptDoctorRegisterDto extends OutptDoctorRegisterDO {
    private String a;
    /**
     * 号源id，与微信小程序接口匹配
     */
    private String sourceId;

    /**
     * 科室姓名
     */
    private String deptName;

    /**
     * 医生姓名
     */
    private String doctorName;
}