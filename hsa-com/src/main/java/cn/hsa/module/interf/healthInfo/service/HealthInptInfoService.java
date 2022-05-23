package cn.hsa.module.interf.healthInfo.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.healthInfo.entity.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.healthInfo.service
 * @Class_name: HealthInptInfoService
 * @Describe: 患者健康信息业务传输层接口
 * @Author: liuliyun
 * @Email: liuliyun@powersi.com
 * @Date: 2022/5/10 11:25
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-health")
public interface HealthInptInfoService {


  /**
   * @Menthod queryInptVisitInfo
   * @Desrciption 按时间段查询住院就诊记录
   * @Param [map]住院
   * @Email: liuliyun@powersi.com
   * @Date: 2022/5/12 13:50
   * @Return WrapperResponse<List<TbZyjzjl>>
   **/
  @PostMapping("/service/health/info/queryInptVisitInfo")
  WrapperResponse<List<TbZyjzjl>> queryInptVisitInfo(Map map);

  /**
   * @Menthod queryInptTurnDeptInfo
   * @Desrciption 按时间段查询住院转科信息
   * @Param [map]住院
   * @Email: liuliyun@powersi.com
   * @Date: 2022/5/12 15:03
   * @Return WrapperResponse<List<TbZkjl>>
   **/
  @PostMapping("/service/health/info/queryInptTurnDeptInfo")
  WrapperResponse<List<TbZkjl>> queryInptTurnDeptInfo(Map map);


  /**
   * @Menthod queryInptAdviceInfo
   * @Desrciption 按时间段查询住院医嘱信息
   * @Param [map]住院
   * @Email: liuliyun@powersi.com
   * @Date: 2022/5/12 20:03
   * @Return WrapperResponse<List<TbZyyzmx>>
   **/
  @PostMapping("/service/health/info/queryInptAdviceInfo")
  WrapperResponse<List<TbZyyzmx>> queryInptAdviceInfo(Map map);


  /**
   * @Menthod queryInptAdviceExecuteInfo
   * @Desrciption 按时间段查询住院医嘱执行信息
   * @Param [map]住院
   * @Email: liuliyun@powersi.com
   * @Date: 2022/5/12 20:04
   * @Return WrapperResponse<List<TbZyyzzxjl>>
   **/
  @PostMapping("/service/health/info/queryInptAdviceExecuteInfo")
  WrapperResponse<List<TbZyyzzxjl>> queryInptAdviceExecuteInfo(Map map);

  /**
   * @Menthod queryInptCostInfo
   * @Desrciption 按时间段查询住院收费明细
   * @Param [map]住院
   * @Email: liuliyun@powersi.com
   * @Date: 2022/5/13 11:04
   * @Return WrapperResponse<List<TbZysfmx>>
   **/
  @PostMapping("/service/health/info/queryInptCostInfo")
  WrapperResponse<List<TbZysfmx>> queryInptCostInfo(Map map);

  /**
   * @Menthod queryInptSettleInfo
   * @Desrciption 按时间段查询住院收费结算信息
   * @Param [map]住院
   * @Email: liuliyun@powersi.com
   * @Date: 2022/5/16 09:29
   * @Return WrapperResponse<List<TbZysfjs>>
   **/
  @PostMapping("/service/health/info/queryInptSettleInfo")
  WrapperResponse<List<TbZysfjs>> queryInptSettleInfo(Map map);

  /**
   * @Menthod queryInptThirdMeasurementInfo
   * @Desrciption 按时间段查询住院三测单记录信息
   * @Param [map]住院
   * @Email: liuliyun@powersi.com
   * @Date: 2022/5/16 09:29
   * @Return WrapperResponse<List<TbHlscdjl>>
   **/
  @PostMapping("/service/health/info/queryInptThirdMeasurementInfo")
  WrapperResponse<List<TbHlscdjl>> queryInptThirdMeasurementInfo(Map map);



  /**
   * @Menthod queryEmrIndexInfo
   * @Desrciption 按时间段查询住院电子病历索引信息
   * @Param [map]住院
   * @Email: liuliyun@powersi.com
   * @Date: 2022/5/16 14:34
   * @Return WrapperResponse<List<TbDzblsyxx>>
   **/
  @PostMapping("/service/health/info/queryEmrIndexInfo")
  WrapperResponse<List<TbDzblsyxx>> queryEmrIndexInfo(Map map);


  /**
   * @Menthod queryInptNurseRecordInfo
   * @Desrciption 按时间段查询一般护理记录
   * @Param [map]住院
   * @Email: liuliyun@powersi.com
   * @Date: 2022/5/16 16:06
   * @Return WrapperResponse<List<TbEmrYbhljl>>
   **/
  @PostMapping("/service/health/info/queryInptNurseRecordInfo")
  WrapperResponse<List<TbEmrYbhljl>> queryInptNurseRecordInfo(Map map);


  /**
   * @Menthod queryInptEmrOutInfo
   * @Desrciption 按时间段查询出院小结信息
   * @Param [map]住院
   * @Email: liuliyun@powersi.com
   * @Date: 2022/5/17 09:43
   * @Return WrapperResponse<List<TbEmrCyxj>>
   **/
  @PostMapping("/service/health/info/queryInptEmrOutInfo")
  WrapperResponse<List<TbEmrCyxj>> queryInptEmrOutInfo(Map map);


    /**
     * @Menthod queryInptBedInfo
     * @Desrciption 按时间段查询住院床位信息
     * @Param [map]住院
     * @Email: liuliyun@powersi.com
     * @Date: 2022/5/17 11:39
     * @Return WrapperResponse<List<TbZycwjl>>
     **/
    @PostMapping("/service/health/info/queryInptBedInfo")
    WrapperResponse<List<TbZycwjl>> queryInptBedInfo(Map map);


}
