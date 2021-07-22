package cn.hsa.module.outpt.statement.service;


import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;


/**
 * @Package_name: cn.hsa.module.outpt.statement.service
 * @Class_name: PatirntCostLedger
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/11/10 14:30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-outpt")
public interface PatientCostLedgerService {

  /**
   * @Menthod queryPatirntCostLedger
   * @Desrciption 查询病人费用台账
   *
   * @Param
   * []
   *
   * @Author jiahong.yang
   * @Date   2020/11/10 15:05
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>>
   **/
  @GetMapping("/service/outpt/statement/queryPatirntCostLedger")
  WrapperResponse<Map> queryPatirntCostLedger(Map map);

  /**
   * @Menthod queryPatirntCostLedgerList
   * @Desrciption 查询病人费用台账数据分页
   * @Param
   * @Author caoliang
   * @Date   2021/6/11 10:22
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>>
   **/
  @GetMapping("/service/outpt/statement/queryPatirntCostLedger")
  WrapperResponse<PageDTO> queryPatirntCostLedgerList(Map map);
    @GetMapping("/service/outpt/statement/queryPatirntCostLedgerSum")
    WrapperResponse<Map> queryPatirntCostLedgerSum(Map map);
  /**
   * @Menthod queryStroInvoicingLedger
   * @Desrciption 药房药库实时进销存报表
   *
   * @Param
   * [map]
   *
   * @Author jiahong.yang
   * @Date   2020/11/11 14:20
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO>>
   **/
  @GetMapping("/service/outpt/statement/queryStroInvoicingLedger")
  WrapperResponse<PageDTO> queryStroInvoicingLedger(Map map);


  /**
  * @Menthod queryReturnDrugLedger
  * @Desrciption 药房退药报表查询
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/4/14 9:09
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
  **/
  @GetMapping("/service/outpt/statement/queryReturnDrugLedger")
  WrapperResponse<PageDTO> queryReturnDrugLedger(Map map);



  /**
  * @Menthod queryCollectorInComeSta
  * @Desrciption 住院收费人员收入统计
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/12/11 14:49
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
  **/
  @GetMapping("/service/outpt/statement/queryCollectorInComeSta")
  WrapperResponse<PageDTO> queryCollectorInComeSta(Map map);

  /**
  * @Menthod queryInPatient
  * @Desrciption 在院/出院病人查询报表
  *
  * @Param
  * [inptVisitDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/12/12 15:27
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>>
  **/
  @GetMapping("/service/outpt/statement/queryInPatient")
  WrapperResponse<PageDTO> queryInPatient(Map map);

  /**
   * @Method queryOutOrInPospitalItemInfo
   * @Desrciption 门诊住院项目使用量统计查询
   * @param paraMap
   * @Author liuqi1
   * @Date   2020/11/10 20:22
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
   **/
  @PostMapping("/service/outpt/statement/queryOutOrInPospitalItemInfo")
  WrapperResponse<PageDTO> queryOutOrInPospitalItemInfo(Map<String, Object> paraMap);

  @PostMapping("/service/outpt/statement/queryOutOrInPospitalItemInfoSum")
  WrapperResponse<PageDTO> queryOutOrInPospitalItemInfoSum(Map<String, Object> paraMap);
  /**
   * @Method queryOutptDeptIncome
   * @Desrciption 门诊科室/医生收入统计
   @params [paraMap]
    * @Author chenjun
   * @Date   2020-11-12 15:39
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
   **/
  @PostMapping("/service/outpt/statement/queryOutptDeptIncome")
  WrapperResponse<Map> queryOutptDeptIncome(Map paraMap);

  /**
   * @Method queryInptMedication
   * @Desrciption 住院科室用药统计
   @params [paraMap]
    * @Author chenjun
   * @Date   2020-11-12 15:39
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
   **/
  @PostMapping("/service/outpt/statement/queryInptMedication")
  WrapperResponse<PageDTO> queryInptMedication(Map paraMap);

  /**
   * @Method queryFinancialIncome
   * @Desrciption 财务收入统计
     @params [paraMap]
   * @Author chenjun
   * @Date   2020-12-29 16:34
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
  **/
  @GetMapping("/service/outpt/statement/queryFinancialIncome")
  WrapperResponse<Map> queryFinancialIncome(Map paraMap);

  /**
   * @Menthod queryFinancialIncomeList
   * @Desrciption 财务收入统计
   * @param paraMap
   * @Author caoliang
   * @Date   2021/6/10 20:04
   * @Return java.util.List<java.util.Map>
   **/
  @GetMapping("/service/outpt/statement/queryFinancialIncomeList")
  WrapperResponse<PageDTO> queryFinancialIncomeList(Map paraMap);

  /**
   * @Menthod queryStroBusinessSummary
   * @Desrciption  药库汇总统计
   * @param paraMap
   * @Author xingyu.xie
   * @Date   2020/11/14 15:26
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
   **/
  @GetMapping("/service/outpt/statement/queryStroBusinessSummary")
  WrapperResponse<PageDTO> queryStroBusinessSummary(Map<String, Object> paraMap);

  @GetMapping("/service/outpt/statement/queryStroBusinessSummarySum")
  WrapperResponse<PageDTO> queryStroBusinessSummarySum(Map<String, Object> paraMap);
  /**
   * @Menthod queryPharBusinessSummary
   * @Desrciption  药房汇总统计
   * @param paraMap
   * @Author xingyu.xie
   * @Date   2020/11/14 15:26
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
   **/
  @GetMapping("/service/outpt/statement/queryStroBusinessSummary")
  WrapperResponse<PageDTO> queryPharBusinessSummary(Map<String, Object> paraMap);

  /**
   * @Menthod queryOutptCostAndRegisterCost
   * @Desrciption  开方科室/开方医生收入统计
   * @param paraMap
   * @Author xingyu.xie
   * @Date   2020/12/12 10:59
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
   **/
  @PostMapping("/service/outpt/statement/queryOutptCostAndRegisterCost")
  WrapperResponse<Map<String,Object>> queryOutptCostAndRegisterCost(Map<String, Object> paraMap);

  /**
   * @Menthod queryOutptCostAndRegisterCostDaoChu
   * @Desrciption  开方科室/开方医生收入统计
   * @param paraMap
   * @Author xingyu.xie
   * @Date   2020/12/12 10:59
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
   **/
  @PostMapping("/service/outpt/statement/queryOutptCostAndRegisterCostDaoChu")
  WrapperResponse<Map<String,Object>> queryOutptCostAndRegisterCostDaoChu(Map<String, Object> paraMap);

  /**
   * @Menthod queryOutptCostAndRegisterCostList
   * @Desrciption  开方科室/开方医生收入统计分页数据
   * @param paraMap
   * @Author caoliang
   * @Date   2021/6/10 21:00
   * @Return java.util.List<java.lang.Map>
   **/
  @PostMapping("/service/outpt/statement/queryOutptCostAndRegisterCostList")
  WrapperResponse<PageDTO> queryOutptCostAndRegisterCostList(Map<String, Object> paraMap);

  @PostMapping("/service/outpt/statement/queryOutptCostAndRegisterCostSum")
  WrapperResponse<Map<String,Object>> queryOutptCostAndRegisterCostSum(Map<String, Object> paraMap);
  /**
   * @Menthod queryPharConsume
   * @Desrciption  查询所有盘亏，报损，发药的台账数据
   * @param paraMap
   * @Author xingyu.xie
   * @Date   2020/11/11 20:39
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
   **/
  @PostMapping("/service/outpt/statement/queryPharConsume")
  WrapperResponse<Map<String,List<Map>>> queryPharConsume(Map<String, Object> paraMap);

    /**
     * @param paraMap
     * @Menthod queryPharConsumeList
     * @Desrciption 查询所有盘亏，报损，发药的台账数据list
     * @Author caoliang
     * @Date 2021/6/11 9:34
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < java.util.Map>>
     **/
    @PostMapping("/service/outpt/statement/queryPharConsumeList")
    WrapperResponse<PageDTO> queryPharConsumeList(Map<String, Object> paraMap);

  /**
   * @Method queryIncomeClassifyInfo
   * @Desrciption 全院月收入分类统计
   * @param paraMap
   * @Author liuqi1
   * @Date   2020/11/18 17:07
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
   **/
  @PostMapping("/service/outpt/statement/queryIncomeClassifyInfo")
//  WrapperResponse<List<Map<String, Object>>> queryIncomeClassifyInfo(Map<String, Object> paraMap);
  WrapperResponse<PageDTO> queryIncomeClassifyInfo(Map<String, Object> paraMap);

  /**
   * @Method queryInvoiceStatistics
   * @Desrciption 门诊/住院明细收费项目查询（按结算时间/按费用发生时间）
     @params [paraMap]
   * @Author chenjun
   * @Date   2020-12-14 09:47
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
  **/
  @PostMapping("/service/outpt/statement/queryInvoiceStatistics")
  WrapperResponse<PageDTO> queryInvoiceStatistics(Map paraMap);

  /**
   * @Method queryChargeDetail
   * @Desrciption 门诊/住院明细收费项目查询（按结算时间/按费用发生时间）
     @params [paraMap]
   * @Author chenjun
   * @Date   2020-12-14 09:47
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
  **/
  @PostMapping("/service/outpt/statement/queryChargeDetail")
  WrapperResponse<Map> queryChargeDetail(Map paraMap);

  /**
   * @Method queryChargeDetailList
   * @Desrciption 门诊/住院明细收费项目查询（按结算时间/按费用发生时间）分页数据
   @params [paraMap]
    * @Author caoliang
   * @Date   2021-6-10 20:50
   * @Return java.util.List<java.util.Map>
   **/
  @PostMapping("/service/outpt/statement/queryChargeDetailList")
  WrapperResponse<PageDTO> queryChargeDetailList(Map paraMap);

  /**
  * @Method queryDayReportWithHospital
  * @Desrciption 全院日报表统计
  * @param paraMap
  * @Author liuqi1
  * @Date   2020/12/16 15:50
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
  **/
  @PostMapping("/service/outpt/statement/queryDayReportWithHospital")
  WrapperResponse<Map<String,Object>> queryDayReportWithHospital(Map<String,Object> paraMap);


  /**
   * @Method queryChargPatirntCostLedger
   * @Desrciption 病人收费统计
   * @param paraMap
   * @Author pengbo
   * @Date   2021/04/13 15:00
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
   **/
  @PostMapping("/service/outpt/statement/queryChargPatirntCostLedger")
  WrapperResponse<Map<String, Object>> queryChargPatirntCostLedger(Map<String, Object> paraMap);


  /**
   * @Method queryItemName
   * @Desrciption 项目查询
   * @Author lizihuan
   * @Date   2021/06/07 10:05
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
   **/
  @PostMapping("/service/outpt/statement/queryItemName")
  WrapperResponse<PageDTO> queryItemName(Map map);

  /**
   * @Description: 收费员收入统计
   * @Param:
   * @Author: guanhongqiang
   * @Email: hongqiang.guan@powersi.com.cn
   * @Date 2021/7/2 16:19
   * @Return
   */
  @GetMapping("/service/outpt/statement/queryTollCollectorIncomeStatistics")
  WrapperResponse<PageDTO> queryTollCollectorIncomeStatistics(Map map);
}

