package cn.hsa.module.outpt.register.service;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Package_name: cn.hsa.module.outpt.register.service
 * @Class_name: OutptDoctorRegisterService
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/8/12 11:32
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-outpt")
public interface OutptDoctorRegisterService {
}
