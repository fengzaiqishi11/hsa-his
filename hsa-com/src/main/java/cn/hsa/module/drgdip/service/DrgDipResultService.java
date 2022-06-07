package cn.hsa.module.drgdip.service;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Package_name: cn.hsa.module.insure.advice.service
 * @Class_name: AdviceService
 * @Describe(描述): 医嘱医保统一开放调用 Service 接口
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/09 9:24
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */

@FeignClient(value = "hsa-insure")
public interface DrgDipResultService {
}
