package cn.hsa.module.interf.healthInfo.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.healthInfo.entity.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.healthInfo.service
 * @Class_name: HealthDiagnoseInfoService
 * @Describe: 诊断信息业务传输层接口
 * @Author: liuliyun
 * @Email: liuliyun@powersi.com
 * @Date: 2022/5/10 11:25
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-health")
public interface HealthDiagnoseInfoService {

   /**
   * @Menthod queryInptDiagnoseInfo
   * @Desrciption 按时间段查询住院诊断明细信息
   * @Param [map]住院
   * @Email: liuliyun@powersi.com
   * @Date: 2022/5/19 14:04
   * @Return WrapperResponse<List<TbZdmx>>
   **/
  @PostMapping("/service/health/info/queryInptDiagnoseInfo")
  WrapperResponse<List<TbZdmx>> queryInptDiagnoseInfo(Map map);

}
