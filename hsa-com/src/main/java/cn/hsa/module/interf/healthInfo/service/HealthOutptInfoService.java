package cn.hsa.module.interf.healthInfo.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.healthInfo.entity.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.healthInfo.service
 * @Class_name: HealthOutptInfoService
 * @Describe: 患者健康信息业务传输层接口
 * @Author: liuliyun
 * @Email: liuliyun@powersi.com
 * @Date: 2022/5/10 11:25
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-health")
public interface HealthOutptInfoService {

  /**
  * @Menthod queryBaseProfile
  * @Desrciption 按时间段查询基础档案信息
  * @Param [map]门诊
  * @Email: liuliyun@powersi.com
  * @Date: 2022/5/10 11:25
  * @Return WrapperResponse<List<TbHzjbxx>>
  **/
  @PostMapping("/service/health/info/queryBaseProfile")
  WrapperResponse<List<TbHzjbxx>> queryBaseProfile(Map map);


  /**
   * @Menthod queryRegisterInfo
   * @Desrciption 按时间段查询挂号信息
   * @Param [map]门诊
   * @Email: liuliyun@powersi.com
   * @Date: 2022/5/10 15:45
   * @Return WrapperResponse<List<TbMzgh>>
   **/
  @PostMapping("/service/health/info/queryRegisterInfo")
  WrapperResponse<List<TbMzgh>> queryRegisterInfo(Map map);


  /**
   * @Menthod queryOutptVisitInfo
   * @Desrciption 按时间段查询就诊信息
   * @Param [map]门诊
   * @Email: liuliyun@powersi.com
   * @Date: 2022/5/10 16:59
   * @Return WrapperResponse<List<TbMzjzjl>>
   **/
  @PostMapping("/service/health/info/queryOutptVisitInfo")
  WrapperResponse<List<TbMzjzjl>> queryOutptVisitInfo(Map map);

  /**
   * @Menthod queryPrescribeInfo
   * @Desrciption 按时间段查询就诊信息
   * @Param [map]门诊
   * @Email: liuliyun@powersi.com
   * @Date: 2022/5/10 16:59
   * @Return WrapperResponse<List<TbMzcfzxx>>
   **/
  @PostMapping("/service/health/info/queryPrescribeInfo")
  WrapperResponse<List<TbMzcfzxx>> queryPrescribeInfo(Map map);

  /**
   * @Menthod queryOutptPrescribeDetailInfo
   * @Desrciption 按时间段查询处方信息
   * @Param [map]门诊
   * @Email: liuliyun@powersi.com
   * @Date: 2022/5/11 14:38
   * @Return WrapperResponse<List<TbMzcfmx>>
   **/
  @PostMapping("/service/health/info/queryOutptPrescribeDetailInfo")
  WrapperResponse<List<TbMzcfmx>> queryOutptPrescribeDetailInfo(Map map);


  /**
   * @Menthod queryOutptPrescribeDetailInfo
   * @Desrciption 按时间段查询处方信息
   * @Param [map]门诊
   * @Email: liuliyun@powersi.com
   * @Date: 2022/5/11 14:38
   * @Return WrapperResponse<List<TbMzcfzxjl>>
   **/
  @PostMapping("/service/health/info/queryOutptPrescribeExecuteInfo")
  WrapperResponse<List<TbMzcfzxjl>> queryOutptPrescribeExecuteInfo(Map map);


  /**
   * @Menthod queryOutptSettleInfo
   * @Desrciption 按时间段查询门诊收费结算
   * @Param [map]门诊
   * @Email: liuliyun@powersi.com
   * @Date: 2022/5/11 16:49
   * @Return WrapperResponse<List<TbMzsfhz>>
   **/
  @PostMapping("/service/health/info/queryOutptSettleInfo")
  WrapperResponse<List<TbMzsfhz>> queryOutptSettleInfo(Map map);


  /**
   * @Menthod queryOutptSettleCostDetailInfo
   * @Desrciption 按时间段查询门诊费用明细
   * @Param [map]门诊
   * @Email: liuliyun@powersi.com
   * @Date: 2022/5/11 19：35
   * @Return WrapperResponse<List<TbMzsfmx>>
   **/
  @PostMapping("/service/health/info/queryOutptSettleCostDetailInfo")
  WrapperResponse<List<TbMzsfmx>> queryOutptSettleCostDetailInfo(Map map);

}
