package cn.hsa.module.drgdip.service;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author 医保二部-张金平
 * @Date 2022-06-07 10:09
 * @Description DRG/DIP质控调用功能日志记录service接口
 */
@FeignClient(value = "hsa-insure")
public interface DrgDipQulityInfoLogService {
}
