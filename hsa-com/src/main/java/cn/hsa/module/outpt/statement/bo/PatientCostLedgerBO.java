package cn.hsa.module.outpt.statement.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeDTO;
import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.statement.bo
 * @Class_name: PatirntCostLedgerBO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/11/10 15:13
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface PatientCostLedgerBO {

  /**
   * @Menthod queryPatirntCostLedger
   * @Desrciption 查询病人费用台账
   *
   * @Param
   * [inptVisitDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/11/10 15:16
   * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
   **/
  Map queryPatirntCostLedger(InptVisitDTO inptVisitDTO);

  /**
   * @Menthod queryPatirntCostLedger
   * @Desrciption 查询病人费用台账
   * @Param
   * [inptVisitDTO]
   * @Author jiahong.yang
   * @Date   2020/11/10 15:16
   * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
   **/
  PageDTO queryPatirntCostLedgerList(InptVisitDTO inptVisitDTO);
  Map queryPatirntCostLedgerSum(InptVisitDTO inptVisitDTO);
  /**
   * @Menthod queryStroInvoicingLedger
   * @Desrciption 药房药库实时进销存报表
   *
   * @Param
   * [stroInvoicingDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/11/11 14:23
   * @Return java.util.List<cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO>
   **/
  PageDTO queryStroInvoicingLedger(StroInvoicingDTO stroInvoicingDTO);

  /**
  * @Menthod queryReturnDrugLedger
  * @Desrciption 药房退药报表查询
  *
  * @Param
  * [pharOutDistributeDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/4/14 9:13
  * @Return java.util.List<java.util.Map>
  **/
  PageDTO queryReturnDrugLedger(PharOutDistributeDTO pharOutDistributeDTO);

  /**
  * @Menthod queryCollectorInComeSta
  * @Desrciption 住院收费人员收入统计
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/12/11 14:54
  * @Return java.util.List<java.util.Map>
  **/
  PageDTO queryCollectorInComeSta(Map map);

  /**
  * @Menthod queryCollectorInComeSta
  * @Desrciption 在院/出院病人统计报表
  *
  * @Param
  * [inptVisitDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/12/12 15:29
  * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
  **/
  PageDTO queryInPatient(InptVisitDTO inptVisitDTO);


  /**
   * @Method queryOutOrInPospitalItemInfo
   * @Desrciption 门诊住院项目使用量统计查询
   * @param paraMap
   * @Author liuqi1
   * @Date   2020/11/10 19:53
   * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
   **/
  PageDTO queryOutOrInPospitalItemInfo(Map<String,Object> paraMap);


  /**
   * @Method queryOutOrInPospitalItemInfoSum
   * @Desrciption 门诊住院项目使用量统计查询合计
   * @param paraMap
   * @Author caoliang
   * @Date   2021/6/15 14:50
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
   **/
  PageDTO queryOutOrInPospitalItemInfoSum(Map<String,Object> paraMap);


  /**
   * @Method queryOutptDeptIncome
   * @Desrciption 医生收入统计
   @params [inptVisitDTO]
    * @Author chenjun
   * @Date   2020-11-12 10:44
   * @Return java.util.List<java.util.Map>
   **/
  Map queryOutptDeptIncome(InptVisitDTO inptVisitDTO);

  /**
   * @Menthod stroBusinessSummary
   * @Desrciption 药库汇总统计
   * @param paraMap
   * @Author xingyu.xie
   * @Date   2020/11/14 15:24
   * @Return java.util.List<java.util.Map>
   **/
  PageDTO queryStroBusinessSummary(Map<String, Object> paraMap);
  PageDTO queryStroBusinessSummarySum(Map<String, Object> paraMap);
  /**
   * @Method queryFinancialIncome
   * @Desrciption 财务分类统计
     @params [map]
   * @Author chenjun
   * @Date   2020-12-29 16:35
   * @Return java.util.Map
  **/
  Map queryFinancialIncome(Map map);

  /**
   * @Menthod queryFinancialIncomeList
   * @Desrciption 财务收入统计
   * @param paraMap
   * @Author caoliang
   * @Date   2021/6/10 20:04
   * @Return java.util.List<java.util.Map>
   **/
  PageDTO queryFinancialIncomeList(Map map);

  /**
   * @Menthod queryPharBusinessSummary
   * @Desrciption 药房汇总统计
   * @param paraMap
   * @Author xingyu.xie
   * @Date   2020/11/14 15:24
   * @Return java.util.List<java.util.Map>
   **/
  PageDTO queryPharBusinessSummary(Map<String, Object> paraMap);

  /**
   * @Menthod queryOutptCostAndRegisterCost
   * @Desrciption  开方科室/开方医生收入统计
   * @param paraMap
   * @Author xingyu.xie
   * @Date   2020/12/12 10:57
   * @Return java.util.Map<java.lang.String,java.lang.Object>
   **/
  Map<String,Object> queryOutptCostAndRegisterCost(Map<String, Object> paraMap);

  Map<String,Object> queryOutptCostAndRegisterCostDaoChu(Map<String, Object> paraMap);
  Map<String,Object> queryOutptCostAndRegisterCostSum(Map<String, Object> paraMap);
  PageDTO queryOutptCostAndRegisterCostList(Map<String, Object> paraMap);

  /**
   * @Menthod queryPharConsume
   * @Desrciption  查询所有盘亏，报损，发药的台账数据
   * @param paraMap
   * @Author xingyu.xie
   * @Date   2020/11/11 20:36
   * @Return java.util.List<java.util.Map>
   **/
  Map<String,List<Map>> queryPharConsume (Map<String,Object> paraMap);

    /**
     * @param paraMap
     * @Menthod queryPharConsumeList
     * @Desrciption 查询所有盘亏，报损，发药的台账数据list
     * @Author caoliang
     * @Date 2021/6/11 10:34
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < java.util.Map>>
     **/
  PageDTO queryPharConsumeList (Map<String,Object> paraMap);

  /**
   * @Method queryOutptDeptIncome
   * @Desrciption 科室用药统计
   @params [inptVisitDTO]
    * @Author chenjun
   * @Date   2020-11-12 15:41
   * @Return java.util.Map
   **/
  PageDTO queryInptMedication(InptVisitDTO inptVisitDTO);

  /**
   * @Method queryIncomeClassifyInfo
   * @Desrciption 全院月收入分类统计
   * @param paraMap
   * @Author liuqi1
   * @Date   2020/11/18 17:09
   * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
   **/
//  List<Map<String,Object>> queryIncomeClassifyInfo(Map<String,Object> paraMap);
  PageDTO queryIncomeClassifyInfo(Map<String,Object> paraMap);

  /**
   * @Method queryInvoiceStatistics
   * @Desrciption 发票使用统计
     @params [paraMap]
   * @Author chenjun
   * @Date   2020-12-14 09:44
   * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
  **/
  PageDTO queryInvoiceStatistics(Map paraMap);

  /**
   * @Method queryChargeDetail
   * @Desrciption
     @params [paraMap]
   * @Author chenjun
   * @Date   2020-12-14 09:49
   * @Return java.util.List<java.util.Map>
  **/
  Map queryChargeDetail(Map paraMap);

  /**
   * @Method queryChargeDetailList
   * @Desrciption 门诊/住院明细收费项目查询（按结算时间/按费用发生时间）分页数据
   @params [paraMap]
    * @Author caoliang
   * @Date   2021-6-10 20:50
   * @Return java.util.List<java.util.Map>
   **/
  PageDTO queryChargeDetailList(Map paraMap);

  /**
   * @Method queryDayReportWithHospital
   * @Desrciption 全院日报表统计
   * @param paraMap
   * @Author liuqi1
   * @Date   2020/12/16 15:52
   * @Return java.util.Map<java.lang.String,java.lang.Object>
   **/
  Map<String,Object> queryDayReportWithHospital(Map<String,Object> paraMap);

  /**
   * @Method queryChargPatirntCostLedger
   * @Desrciption 病人收费统计
   * @param paraMap
   * @Author pengbo
   * @Date   2021/04/13 15:00
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
   **/
  Map queryChargPatirntCostLedger(Map<String, Object> paraMap);
  /**
   * @Method queryItemName
   * @Desrciption 项目查询
   * @Author lizihuan
   * @Date   2021/06/07 10:05
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
   **/
  PageDTO queryItemName(Map map);

  /**
   * @Description: 收费员收入统计
   * @Param:
   * @Author: guanhongqiang
   * @Email: hongqiang.guan@powersi.com.cn
   * @Date 2021/7/2 16:21
   * @Return
   */
  PageDTO queryTollCollectorIncomeStatistics(Map map);
}
