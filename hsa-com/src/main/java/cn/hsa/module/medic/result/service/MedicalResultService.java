package cn.hsa.module.medic.result.service;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Package_name
 * @class_nameMedicalApplyDAO
 * @Description 医技BO
 * @Author youxianlin
 * @Email: 254580179@qq.com
 * @Date2020/12/11 10:30
 * @Company 创智和宇
 **/
@FeignClient(value = "hsa-medical")
public interface MedicalResultService {


}
