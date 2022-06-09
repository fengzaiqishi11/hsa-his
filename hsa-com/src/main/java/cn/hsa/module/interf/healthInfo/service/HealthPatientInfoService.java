package cn.hsa.module.interf.healthInfo.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.healthInfo.entity.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.healthInfo.service
 * @Class_name: HealthPatientInfoService
 * @Describe: 患者健康信息业务传输层接口
 * @Author: liuliyun
 * @Email: liuliyun@powersi.com
 * @Date: 2022/5/10 11:25
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-health")
public interface HealthPatientInfoService {

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
