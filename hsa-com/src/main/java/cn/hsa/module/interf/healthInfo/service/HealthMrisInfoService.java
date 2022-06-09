package cn.hsa.module.interf.healthInfo.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.healthInfo.entity.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.healthInfo.service
 * @Class_name: HealthMrisInfoService
 * @Describe: 患者健康信息业务传输层接口
 * @Author: liuliyun
 * @Email: liuliyun@powersi.com
 * @Date: 2022/5/10 11:25
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-health")
public interface HealthMrisInfoService {

   /**
   * @Menthod queryInptMrisInfo
   * @Desrciption 按时间段查询住院病案首页主体信息
   * @Param [map]住院
   * @Email: liuliyun@powersi.com
   * @Date: 2022/5/17 19:46
   * @Return WrapperResponse<List<TbBasyztb>>
   **/
  @PostMapping("/service/health/info/queryInptMrisInfo")
  WrapperResponse<List<TbBasyztb>> queryInptMrisInfo(Map map);


  /**
   * @Menthod queryInptMrisDiagnoseInfo
   * @Desrciption 按时间段查询住院病案首页--诊断信息
   * @Param [map]住院
   * @Email: liuliyun@powersi.com
   * @Date: 2022/5/18 10:38
   * @Return WrapperResponse<List<TbBasyzdmx>>
   **/
  @PostMapping("/service/health/info/queryInptMrisDiagnoseInfo")
  WrapperResponse<List<TbBasyzdmx>> queryInptMrisDiagnoseInfo(Map map);


  /**
   * @Menthod queryInptMrisOperInfo
   * @Desrciption 按时间段查询住院病案首页--手术信息
   * @Param [map]住院
   * @Email: liuliyun@powersi.com
   * @Date: 2022/5/19 10:38
   * @Return WrapperResponse<List<TbBasyssmx>>
   **/
  @PostMapping("/service/health/info/queryInptMrisOperInfo")
  WrapperResponse<List<TbBasyssmx>> queryInptMrisOperInfo(Map map);

}
