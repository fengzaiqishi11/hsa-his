package cn.hsa.module.interf.healthInfo.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.healthInfo.entity.TbCwjsxx;
import cn.hsa.module.interf.healthInfo.entity.TbCwsffs;
import cn.hsa.module.interf.healthInfo.entity.TbCwsfmx;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.healthInfo.service
 * @Class_name: HealthSettleInfoService
 * @Describe:  财务报告信息业务传输层接口
 * @Author: liuliyun
 * @Email: liuliyun@powersi.com
 * @Date: 2022/5/10 11:25
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-health")
public interface HealthSettleInfoService {

  /**
   * @Menthod queryCwSettleInfo
   * @Desrciption 查询财务结算信息（日结缴款）
   * @Param [map]日结缴款
   * @Email: liuliyun@powersi.com
   * @Date: 2022/5/20 16:43
   * @Return WrapperResponse<List<TbCwjsxx>>
   **/
  @PostMapping("/service/health/info/queryCwSettleInfo")
  WrapperResponse<List<TbCwjsxx>> queryCwSettleInfo(Map map);

  /**
   * @Menthod queryCwSettleDetailInfo
   * @Desrciption 查询财务结算详细信息（日结缴款）
   * @Param [map]日结缴款
   * @Email: liuliyun@powersi.com
   * @Date: 2022/5/20 17:02
   * @Return WrapperResponse<List<TbCwsfmx>>
   **/
  @PostMapping("/service/health/info/queryCwSettleDetailInfo")
  WrapperResponse<List<TbCwsfmx>> queryCwSettleDetailInfo(Map map);

  /**
   * @Menthod queryCwPayInfo
   * @Desrciption 查询财务结算-收费方式（日结缴款）
   * @Param [map]日结缴款
   * @Email: liuliyun@powersi.com
   * @Date: 2022/5/23 09:34
   * @Return WrapperResponse<List<TbCwsffs>>
   **/
  @PostMapping("/service/health/info/queryCwPayInfo")
  WrapperResponse<List<TbCwsffs>> queryCwPayInfo(Map map);



}
