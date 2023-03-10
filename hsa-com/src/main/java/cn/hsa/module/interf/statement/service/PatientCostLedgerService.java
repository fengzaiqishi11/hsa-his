package cn.hsa.module.interf.statement.service;


import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
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
  * @Menthod queryStockTime
  * @Desrciption 查询月底库存
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/12/14 15:51
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
  **/
  @GetMapping("/service/outpt/statement/queryStockTime")
  WrapperResponse<PageDTO> queryStockTime(Map map);

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
  @PostMapping("/service/outpt/statement/queryOutptDeptIncomeTableHead")
  WrapperResponse<Map> queryOutptDeptIncomeTableHead(Map paraMap);
  /**
   * @Method queryOutptDeptIncome
   * @Desrciption 门诊科室/医生收入统计
   @params [paraMap]
    * @Author chenjun
   * @Date   2020-11-12 15:39
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
   **/
  @PostMapping("/service/outpt/statement/queryOutptDeptIncome")
  WrapperResponse<PageDTO> queryOutptDeptIncome(Map paraMap);

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
  /**
   * @Method queryOutMedicationGet
   * @Desrciption 门诊科室用药统计
   * @Param [PharOutDistributeDTO, req, res]
   * @Author zhangguorui
   * @Date   2021/7/23 15:44
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
   */
  @GetMapping("/service/outpt/statement/queryOutMedicationGet")
  WrapperResponse<PageDTO> queryOutMedicationGet(Map map);


  /**
   * @Menthod queryInPatientDaily
   * @Desrciption 在院病人实况查询   *
   * @Param
   * [inptVisitDTO]   *
   * @Author liuliyun
   * @Date   2021/08/05 11:19
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>>
   **/
  @GetMapping("/service/outpt/statement/queryInPatientDaily")
  WrapperResponse<PageDTO> queryInPatientDaily(Map map);

  /**
   * @Menthod queryMedicalCostMz
   * @Desrciption  门诊医疗汇总报表
   * @Param Map
   * @Author liuliyun
   * @Date   2021/08/24 16：40
   * @Return PageDTO
   **/
  @GetMapping("/service/outpt/statement/queryMedicalCostMz")
  WrapperResponse<PageDTO> queryMedicalCostMz(Map map);


  /**
   * @Menthod queryMedicalCostTitle
   * @Desrciption  门诊医疗汇总报表
   * @Param Map
   * @Author liuliyun
   * @Date   2021/08/24 20：21
   * @Return PageDTO
   **/
  @GetMapping("/service/outpt/statement/queryMedicalCostTitle")
  WrapperResponse<Map> queryMedicalCostTitle(Map map);


  /**
   * @Menthod queryMzPatientFinanceCostList
   * @Desrciption 查询门诊财务分类明细
   * @param map
   * @Author liuliyun
   * @Date   2021/10/22 16:26
   * @Return WrapperResponse<PageDTO>
   **/
  @GetMapping("/service/outpt/statement/queryMzPatientFinanceCostList")
  WrapperResponse<PageDTO> queryMzPatientFinanceCostList(Map map);


  /**
   * @Menthod getMedicalFinanceMzTitle
   * @Desrciption  查询门诊财务分类标题
   * @param map
   * @Author liuliyun
   * @Date   2021/10/22 16:25
   * @Return WrapperResponse<Map>
   **/
  @GetMapping("/service/outpt/statement/getMzMedicalFinanceTitle")
  WrapperResponse<Map> getMzMedicalFinanceTitle(Map map);


  /**
   * @Menthod getInptFinanceList
   * @Desrciption 查询住院财务分类明细
   * @param map
   * @Author liuliyun
   * @Date   2021/10/25 15:26
   * @Return WrapperResponse<PageDTO>
   **/
  @GetMapping("/service/outpt/statement/getInptFinanceList")
  WrapperResponse<PageDTO> getInptFinanceList(Map map);


  /**
   * @Menthod getInptFinanceTitle
   * @Desrciption  查询住院财务分类标题
   * @param map
   * @Author liuliyun
   * @Date   2021/10/25 15:25
   * @Return WrapperResponse<Map>
   **/
  @GetMapping("/service/outpt/statement/getInptFinanceTitle")
  WrapperResponse<Map> getInptFinanceTitle(Map map);

  /**
   * @Menthod queryHospitalCardList
   * @Desrciption 统计入院证
   * @Param inptVisitDTO
   * @Author liuliyun
   * @Date   2021/12/06 10:51
   * @Return WrapperResponse<PageDTO>
   **/
  @GetMapping("/service/outpt/statement/queryHospitalCardList")
  WrapperResponse<PageDTO> queryHospitalCardList(Map map);

  /**
   * @param paraMap
   * @Method queryOutptorInHosptialItemUseInfo
   * @Desrciption 门诊业务使用量统计按科室过滤
   * @Author liuliyun
   * @Date 2021/12/07 11:48
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < java.util.Map < java.lang.String, java.lang.Object>>>
   **/
  @PostMapping("/service/outpt/statement/queryOutptorInHosptialItemUseInfo")
  WrapperResponse<PageDTO> queryOutptorInHosptialItemUseInfo(Map<String, Object> paraMap);


  /**
   * @Menthod getInptOperFinanceList
   * @Desrciption 查询住院手术财务分类明细
   * @Param inptVisitDTO
   * @Author liuliyun
   * @Date   2021/12/14 10:10
   * @Return cn.hsa.base.PageDTO
   **/
  @GetMapping("/service/outpt/statement/getInptOperFinanceList")
  WrapperResponse<PageDTO> getInptOperFinanceList(Map map);


  /**
   * @Menthod getInptOperFinanceTitle
   * @Desrciption  查询住院手术财务分类标题
   * @Param inptVisitDTO
   * @Author liuliyun
   * @Date   2021/12/14 10:14
   * @Return Map
   **/
  @GetMapping("/service/outpt/statement/getInptOperFinanceTitle")
  WrapperResponse<Map> getInptOperFinanceTitle(Map map);

  /**
   * @Description: 查询门诊财务月报表，按选定的时间区间，逐日统计药品或项目的自费收入，医保收入
   * @Param:
   * @Author: guanhongqiang
   * @Email: hongqiang.guan@powersi.com.cn
   * @Date 2021/12/20 14:56
   * @Return
   */
  @GetMapping("/service/outpt/statement/queryMzMonthlyReport")
  WrapperResponse<PageDTO> queryMzMonthlyReport(Map<String, Object> paraMap);
  /**
   * @Menthod getoutptMonthDaily
   * @Desrciption  查询门诊月结报表
   * @Param OutptCostDTO
   * @Author yuelong.chen
   * @Date   2021/12/24 12:14
   * @Return List<OutptCostDTO>
   *
   * @return*/
  @GetMapping("/service/outpt/statement/queryoutptMonthDaily")
  WrapperResponse<Map<String, List<OutptCostDTO>>> queryoutptMonthDaily(Map map);

  /**
   * @Menthod queryOutptIncomePage
   * @Desrciption  门诊科室医生收入统计分页数据(加优惠前金额)
   * @param map
   * @Author liuliyun
   * @Date   2022/1/11 16:12
   * @Return java.util.List<java.lang.Map>
   **/
  @PostMapping("/service/outpt/statement/queryOutptIncomePage")
  WrapperResponse<PageDTO> queryOutptIncomePage(Map map);

  @PostMapping("/service/outpt/statement/queryOutptIncomeList")
  WrapperResponse<Map<String,Object>> queryOutptIncomeList(Map map);


    /**
     * @Menthod queryHosptialInComeList
     * @Desrciption  住院业务收入统计报表
     * @param map
     * @Author liuliyun
     * @Date   2022/2/10 15:12
     * @Return java.util.List<java.lang.Map>
     **/
  @PostMapping("/service/outpt/statement/queryHosptialInComeList")
  WrapperResponse<PageDTO> queryHosptialInComeList(Map map);

  /**
   * @Menthod queryHosptialInComeListTitle
   * @Desrciption  住院业务收入统计报表
   * @param map
   * @Author liuliyun
   * @Date   2022/2/10 11:54
   * @Return java.util.List<java.lang.Map>
   **/
  @PostMapping("/service/outpt/statement/queryHosptialInComeListTitle")
  WrapperResponse<Map<String,Object>> queryHosptialInComeListTitle(Map map);

  /**
   * 住院费用收入统计报表
   *
   * @param map
   * @Menthod queryZyFeeIncomeList
   * @Author liuzhuoting
   * @Date 2022/3/3 08:54
   * @return java.util.List<java.lang.Map>
   **/
  @PostMapping("/service/outpt/statement/queryZyFeeIncomeList")
  WrapperResponse<List<Map<String, Object>>> queryZyFeeIncomeList(Map map);

  /**
   * 门诊费用收入统计报表
   *
   * @param map
   * @Menthod queryMzFeeIncomeList
   * @Author liuzhuoting
   * @Date 2022/3/3 08:54
   * @return java.util.List<java.lang.Map>
   **/
  @PostMapping("/service/outpt/statement/queryMzFeeIncomeList")
  WrapperResponse<List<Map<String, Object>>> queryMzFeeIncomeList(Map map);

}

