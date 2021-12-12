package cn.hsa.module.outpt.statement.dao;

import cn.hsa.base.DynamicTable;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.patientcomprehensivequery.dto.OutptCostAndReigsterCostDTO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.statement.dto.IncomeDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeDTO;
import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.statement.dao
 * @Class_name: PatirntCostLedgerDAO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/11/10 15:16
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface PatientCostLedgerDAO {

  /**
   * @Menthod getVisitTitle
   * @Desrciption 病人台账标题
   *
   * @Param
   * [inptCostDTO]
   *
   * @Author zengfeng
   * @Date   2020/11/10 16:31
   * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptCostDTO>
   **/
  List<InptCostDTO> getVisitTitle(InptVisitDTO inptVisitDTO);

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
  List<Map> queryPatirntCostLedger(InptVisitDTO inptVisitDTO);

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
  List<Map> queryPatirntCostDeptLedger(InptVisitDTO inptVisitDTO);

  /**
   * @Menthod queryPatirntCostDetail
   * @Desrciption 查询病人费用信息
   *
   * @Param
   * [inptCostDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/11/10 16:31
   * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptCostDTO>
   **/
  List<InptCostDTO> queryPatirntCostDetail(InptCostDTO inptCostDTO);


  List<Map> queryCostByDept(InptVisitDTO inptVisitDTO);

  /**
   * @Menthod queryStroInvoicingLedger
   * @Desrciption 药库实时进销存报表/按零售价
   *
   * @Param
   * [stroInvoicingDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/11/11 14:25
   * @Return java.util.List<cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO>
   **/
  List<Map> queryStroInvoicingLedger(StroInvoicingDTO stroInvoicingDTO);

  /**
  * @Menthod queryStroInvoicingLedgerBuy
  * @Desrciption 药库实时进销存报表/按购进价
  *
  * @Param
  * [stroInvoicingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/12/16 9:42
  * @Return java.util.List<java.util.Map>
  **/
  List<Map> queryStroInvoicingLedgerBuy(StroInvoicingDTO stroInvoicingDTO);

  /**
   * @Menthod queryStroInvoicingLedger
   * @Desrciption 药房实时进销存报表
   *
   * @Param
   * [stroInvoicingDTO]
   *
   * @Author jiahong.yang
   * @Date   2020/11/11 14:25
   * @Return java.util.List<cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO>
   **/
  List<Map> queryPharInvoicingLedger(StroInvoicingDTO stroInvoicingDTO);

  /**
  * @Menthod queryPharInvoicingLedgerBuy
  * @Desrciption 药房实时进销存报表/按购进价
  *
  * @Param
  * [stroInvoicingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/12/16 10:09
  * @Return java.util.List<java.util.Map>
  **/
  List<Map> queryPharInvoicingLedgerBuy(StroInvoicingDTO stroInvoicingDTO);

  /**
  * @Menthod queryReturnOutDrugLedger
  * @Desrciption 门诊退药报表查询
  *
  * @Param
  * [stroInvoicingDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/4/14 9:20
  * @Return java.util.List<java.util.Map>
  **/
  List<Map> queryReturnOutDrugLedger(PharOutDistributeDTO pharOutDistributeDTO);

  /**
  * @Menthod queryReturnInDrugLedger
  * @Desrciption 住院报表查询
  *
  * @Param
  * [pharOutDistributeDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/4/14 11:04
  * @Return java.util.List<java.util.Map>
  **/
  List<Map> queryReturnInDrugLedger(PharOutDistributeDTO pharOutDistributeDTO);

  /**
  * @Menthod queryReturnAllDrugLedger
  * @Desrciption 门诊，住院报表查询
  *
  * @Param
  * [pharOutDistributeDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/4/14 11:08
  * @Return java.util.List<java.util.Map>
  **/
  List<Map> queryReturnAllDrugLedger(PharOutDistributeDTO pharOutDistributeDTO);


  /**
  * @Menthod queryCollectorInComeSta
  * @Desrciption 住院收费员收入统计
  *
  * @Param
  * [paraMap]
  *
  * @Author jiahong.yang
  * @Date   2020/12/11 9:31
  * @Return java.util.List<java.util.Map>
  **/
  List<Map> queryCollectorInComeSta(Map map);

  /**
  * @Menthod queryCollectorInComeStaDetail
  * @Desrciption 收费人员收入统计明细
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/12/29 20:37
  * @Return java.util.List<java.util.Map>
  **/
  List<Map> queryCollectorInComeStaDetail(Map map);

  /**
  * @Menthod queryInPatient
  * @Desrciption 按条件查询病人
  *
  * @Param
  * [inptVisitDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/12/12 14:39
  * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
  **/
  List<InptVisitDTO> queryInPatient(InptVisitDTO inptVisitDTO);


  /**
   * @Method queryInHospitalItemReportInfo
   * @Desrciption 门诊住院项目使用量统计查询，按业务类型、项目、开方医生分组
   * @param paraMap
   * @Author liuqi1
   * @Date   2020/11/10 20:02
   * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
   **/
  List<LinkedHashMap<String,Object>> queryHospitalItemReportInfoGroupThree(Map<String,Object> paraMap);

  /**
  * @Menthod queryHospitalItemReportInfoGroupFour
  * @Desrciption 按业务类型、项目明细统计
  *
  * @Param
  * [paraMap]
  *
  * @Author jiahong.yang
  * @Date   2021/6/9 11:02
  * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
  **/
  List<LinkedHashMap<String,Object>> queryHospitalItemReportInfoGroupFour(Map<String,Object> paraMap);

  /**
   * @Method queryOutHospitalItemReportInfo
   * @Desrciption  门诊住院项目使用量统计查询，按业务类型、项目分组
   * @param paraMap
   * @Author liuqi1
   * @Date   2020/11/10 20:02
   * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
   **/
  List<LinkedHashMap<String,Object>> queryHospitalItemReportInfoGroupTwo(Map<String,Object> paraMap);

  /**
   * @Method queryOutHospitalItemReportInfo
   * @Desrciption  门诊住院项目使用量统计查询，按业务类型、开方医生、项目、就诊病人分组
   * @param paraMap
   * @Author liuqi1
   * @Date   2020/11/10 20:02
   * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
   **/
  List<LinkedHashMap<String,Object>> queryHospitalItemReportInfoGroupOne(Map<String,Object> paraMap);

    /**
     * @Method
     * @Desrciption  门诊住院项目使用量统计查询，按业务类型、开方医生、项目、就诊病人分组合计
     * @param paraMap
     * @Author caoliang
     * @Date   2021/6/15 15:40
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
  LinkedHashMap<String,Object> queryHospitalItemReportInfoGroupOneSum(Map<String,Object> paraMap);
  LinkedHashMap<String,Object> queryHospitalItemReportInfoGroupTwoSum(Map<String,Object> paraMap);
  LinkedHashMap<String,Object> queryHospitalItemReportInfoGroupThreeSum(Map<String,Object> paraMap);
  LinkedHashMap<String, Object> queryHospitalItemReportInfoGroupFourSum(Map<String, Object> paraMap);

  List<Map> queryOutptCostBfcGroupMap(Map map);

  List<Map> queryInptCostBfcGroupMap(Map map);

  List<Map> queryFinancialIncome(Map map);

  List<Map> queryFinancialIncomeInpt(Map map);

  /**
   * @Method queryOutptCostBfcGroup
   * @Desrciption 查询bfcid分组
   @params [inptVisitDTO]
    * @Author chenjun
   * @Date   2020-11-12 10:44
   * @Return java.util.List<cn.hsa.module.outpt.fees.dto.OutptCostDTO>
   **/
  List<OutptCostDTO> queryOutptCostBfcGroup(InptVisitDTO inptVisitDTO);

  /**
   * @Method queryOutptDeptIncome
   * @Desrciption 门诊科室/医生收入统计
   @params [inptVisitDTO]
    * @Author chenjun
   * @Date   2020-11-12 10:44
   * @Return java.util.List<java.util.Map>
   **/
  List<Map<String ,Object>> queryOutptDeptIncome(InptVisitDTO inptVisitDTO);

  /**
   * @Method queryInptMedication
   * @Desrciption 住院科室用药统计
   @params [inptVisitDTO]
    * @Author chenjun
   * @Date   2020-11-12 15:43
   * @Return java.util.List<java.util.Map>
   **/
  List<Map> queryInptMedication(InptVisitDTO inptVisitDTO);

  /**
   * @Menthod queryStroBusinessSummary
   * @Desrciption 入库业务汇总 （按供应商）
   * @param paraMap
   * @Author xingyu.xie
   * @Date   2020/11/14 15:03
   * @Return java.util.List<java.util.Map>
   **/
  List<Map> queryStroBusinessSummary(Map<String,Object> paraMap);
  List<Map> queryStroBusinessSummarySum(Map<String,Object> paraMap);
  /**
   * @Menthod queryStroBusinessSummaryMain
   * @Desrciption   （按供应商/入库单据）
   * @param paraMap
   * @Author xingyu.xie
   * @Date   2020/11/16 17:32
   * @Return java.util.List<java.util.Map>
   **/
  List<Map> queryStroBusinessSummaryMain(Map<String,Object> paraMap);
  List<Map> queryStroBusinessSummaryMainSum(Map<String,Object> paraMap);

  /**
  * @Menthod queryStroBusinessSummaryMainDeTail
  * @Desrciption 单据明细
  *
  * @Param
  * [paraMap]
  *
  * @Author jiahong.yang
  * @Date   2021/7/8 13:59
  * @Return java.util.List<java.util.Map>
  **/
  List<Map> queryStroBusinessSummaryMainDeTail(Map<String,Object> paraMap);

  /**
   * @Menthod queryStroBusinessSummaryBfc
   * @Desrciption  按供应商/计费类别
   * @param paraMap
   * @Author xingyu.xie
   * @Date   2020/11/16 17:32
   * @Return java.util.List<java.util.Map>
   **/
  List<Map> queryStroBusinessSummaryBfc(Map<String,Object> paraMap);
  List<Map> queryStroBusinessSummaryBfcSum(Map<String,Object> paraMap);
  /**
   * @Menthod queryStroBusinessSummaryByItemAndBatchNo
   * @Desrciption  按供应商/品种
   * @param paraMap
   * @Author xingyu.xie
   * @Date   2020/11/16 17:33
   * @Return java.util.List<java.util.Map>
   **/
  List<Map> queryStroBusinessSummaryByItemAndBatchNo(Map<String,Object> paraMap);
  List<Map> queryStroBusinessSummaryByItemAndBatchNoSum(Map<String,Object> paraMap);
  /**
   * @Menthod queryStroBusinessSummaryItem
   * @Desrciption  按供应商/品种批次
   * @param paraMap
   * @Author xingyu.xie
   * @Date   2020/11/16 17:33
   * @Return java.util.List<java.util.Map>
   **/
  List<Map> queryStroBusinessSummaryItem(Map<String,Object> paraMap);
  List<Map> queryStroBusinessSummaryItemSum(Map<String,Object> paraMap);
  /**
   * @Menthod queryStroReportLosses
   * @Desrciption 药库业务报损汇总统计表按品种
   *
   * @Param
   * [map]
   *
   * @Author jiahong.yang
   * @Date   2020/11/17 15:27
   * @Return java.util.List<cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO>
   **/
  List<Map> queryStroReportLossesByKind(Map map);
  List<Map> queryStroReportLossesByKindSum(Map map);
  /**
   * @Menthod queryStroReportLossesByKindBatNO
   * @Desrciption 药库业务报损汇总统计表 按品种批号
   *
   * @Param
   * [map]
   *
   * @Author jiahong.yang
   * @Date   2020/11/17 17:11
   * @Return java.util.List<java.util.Map>
   **/
  List<Map> queryStroReportLossesByKindBatNO(Map map);
  List<Map> queryStroReportLossesByKindBatNOSum(Map map);
  /**
   * @Menthod queryStroReportLossesByOrder
   * @Desrciption 药库业务报损汇总统计表 按单号
   *
   * @Param
   * [map]
   *
   * @Author jiahong.yang
   * @Date   2020/11/17 17:11
   * @Return java.util.List<java.util.Map>
   **/
  List<Map> queryStroReportLossesByOrder(Map map);
  List<Map> queryStroReportLossesByOrderSum(Map map);
  /**
   * @Menthod queryStroReportLossesByBfc
   * @Desrciption 药库业务报损汇总统计表 按计费类别
   *
   * @Param
   * [map]
   *
   * @Author jiahong.yang
   * @Date   2020/11/17 17:12
   * @Return java.util.List<java.util.Map>
   **/
  List<Map> queryStroReportLossesByBfc(Map map);
  List<Map> queryStroReportLossesByBfcSum(Map map);
  /**
   * @Menthod queryStroReportAdjustPrice
   * @Desrciption 药库业务报损汇总统计表
   *
   * @Param
   * [map]
   *
   * @Author jiahong.yang
   * @Date   2020/11/18 16:52
   * @Return java.util.List<java.util.Map>
   **/
  List<Map> queryStroReportAdjustPrice(Map map);
  List<Map> queryStroReportAdjustPriceSum(Map map);
  /**
   * @Menthod queryStroTakeStock
   * @Desrciption 药库业务盘点汇总统计表
   *
   * @Param
   * [map]
   *
   * @Author jiahong.yang
   * @Date   2020/11/18 17:24
   * @Return java.util.List<java.util.Map>
   **/
  List<Map> queryStroTakeStock(Map map);
  List<Map> queryStroTakeStockSum(Map map);
  /**
   * @Menthod queryPharConsume
   * @Desrciption  查询所有盘亏，报损，发药的台账数据
   * @param paraMap
   * @Author xingyu.xie
   * @Date   2020/11/11 14:52
   * @Return java.util.List<cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO>
   **/
  List<StroInvoicingDTO> queryPharConsume(Map<String,Object> paraMap);

  /**
  * @Menthod queryPharConsumeNoPD
  * @Desrciption 不含盘点
  *
  * @Param
  * [paraMap]
  *
  * @Author jiahong.yang
  * @Date   2021/6/25 11:10
  * @Return java.util.List<cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO>
  **/
  List<StroInvoicingDTO> queryPharConsumeNoPD(Map<String,Object> paraMap);

  /**
  * @Menthod querySumPharConsume
  * @Desrciption 统计消耗数据
  *
  * @Param
  * [paraMap]
  *
  * @Author jiahong.yang
  * @Date   2021/7/14 14:25
  * @Return java.util.List<cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO>
  **/
  List<StroInvoicingDTO> querySumPharConsume(Map<String,Object> paraMap);



  /**
   * @Menthod queryOutptCostAndRegisterCost
   * @Desrciption  查询门诊所有已结算的费用信息
   * @param map
   * @Author xingyu.xie
   * @Date   2020/12/11 11:00
   * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
   **/
  List<OutptCostAndReigsterCostDTO> queryOutptCostAndRegisterCost(Map<String,Object> map);
  List<OutptCostAndReigsterCostDTO> queryOutptCostAndRegisterCostSum(Map<String,Object> map);
  /**
   * @Method queryCurrentInptIncomeClassify
   * @Desrciption 当前时间段住院收入分类统计查询
   * @param map
   * @Author liuqi1
   * @Date   2020/11/18 17:24
   * @Return java.util.List<java.util.Map>
   **/
  List<Map<String,Object>> queryCurrentInptIncomeClassify(Map<String,Object> map);

  /**
   * @Method queryYearInptIncomeClassify
   * @Desrciption 上一年当前时间段住院收入分类统计查询
   * @param map
   * @Author liuqi1
   * @Date   2020/11/18 17:25
   * @Return java.util.List<java.util.Map>
   **/
  List<Map<String,Object>> queryYearInptIncomeClassify(Map<String,Object> map);

  /**
   * @Method queryYearInptIncomeClassify
   * @Desrciption 上一月当前时间段住院收入分类统计查询
   * @param map
   * @Author liuqi1
   * @Date   2020/11/18 17:25
   * @Return java.util.List<java.util.Map>
   **/
  List<Map<String,Object>> queryMonthInptIncomeClassify(Map<String,Object> map);

  /**
   * @Method queryCurrentOutptIncomeClassify
   * @Desrciption 当前时间段门诊收入分类统计查询
   * @param map
   * @Author liuqi1
   * @Date   2020/11/18 17:26
   * @Return java.util.List<java.util.Map>
   **/
  List<Map<String,Object>> queryCurrentOutptIncomeClassify(Map<String,Object> map);

  /**
   * @Method queryYearInptOutcomeClassify
   * @Desrciption 上一年当前时间段住院收入分类统计查询
   * @param map
   * @Author liuqi1
   * @Date   2020/11/18 17:26
   * @Return java.util.List<java.util.Map>
   **/
  List<Map<String,Object>> queryYearInptOutcomeClassify(Map<String,Object> map);

  /**
   * @Method queryMonthInptOutcomeClassify
   * @Desrciption 上一月当前时间段住院收入分类统计查询
   * @param map
   * @Author liuqi1
   * @Date   2020/11/18 17:27
   * @Return java.util.List<java.util.Map>
   **/
  List<Map<String,Object>> queryMonthInptOutcomeClassify(Map<String,Object> map);


  /**
   * @Method queryInvoiceStatistics
   * @Desrciption 发票使使用统计
     @params [map]
   * @Author chenjun
   * @Date   2020-12-14 09:51
   * @Return java.util.List<java.util.Map>
  **/
  List<Map> queryInvoiceStatistics(Map map);

  /**
   * @Method queryChargeDetail
   * @Desrciption 门诊/住院明细收费项目查询（按结算时间/按费用发生时间）
     @params [map]
   * @Author chenjun
   * @Date   2020-12-14 09:51
   * @Return java.util.List<java.util.Map>
  **/
  List<Map> queryAllChargeDetail(Map map);

  List<Map> queryXmfbChargeDetail(Map map);

  List<Map> queryXmChargeDetail(Map map);

  List<Map> queryInptAllChargeDetail(Map map);

  List<Map> queryInptXmfbChargeDetail(Map map);

  List<Map> queryInptXmChargeDetail(Map map);



  /**
   * @Method queryOutPtRegister
   * @Desrciption 全院日报表：门诊信息查询
   * @param map
   * @Author liuqi1
   * @Date   2020/12/15 11:11
   * @Return java.util.Map<java.lang.String,java.lang.Object>
   **/
  Map<String,Object> queryOutptRegisterInfo(Map<String,Object> map);

  /**
   * @Method queryOutptVisitInfo
   * @Desrciption 全院日报表：住院基本信息查询
   * @param map
   * @Author liuqi1
   * @Date   2020/12/15 11:14
   * @Return java.util.Map<java.lang.String,java.lang.Object>
   **/
  Map<String,Object> queryInptBaseInfo(Map<String,Object> map);

  /**
   * @Method queryOutptVisitInfo
   * @Desrciption 全院日报表：住院在院信息查询
   * @param map
   * @Author liuqi1
   * @Date   2020/12/15 15:15
   * @Return java.util.Map<java.lang.String,java.lang.Object>
   **/
  Map<String,Object> queryInptInHospitalInfo(Map<String,Object> map);

  /**
   * @Method queryOutptVisitInfo
   * @Desrciption 全院日报表：住院结算信息查询
   * @param map
   * @Author liuqi1
   * @Date   2020/12/15 15:15
   * @Return java.util.Map<java.lang.String,java.lang.Object>
   **/
  Map<String,Object> queryInptSettleInfo(Map<String,Object> map);

  /**
   * @Method queryOutptVisitInfo
   * @Desrciption 全院日报表：住院保险报销信息查询
   * @param map
   * @Author liuqi1
   * @Date   2020/12/15 15:15
   * @Return java.util.Map<java.lang.String,java.lang.Object>
   **/
  Map<String,Object> queryInptInsuerInfo(Map<String,Object> map);

  /**
   * @Method queryMedicalTreatmentOutptInfo
   * @Desrciption 全院日报表：医疗信息(门诊收入、住院收入、总收入)
   * @param map
   * @Author liuqi1
   * @Date   2020/12/16 8:41
   * @Return java.util.Map<java.lang.String,java.lang.Object>
   **/
  Map<String,Object> queryMedicalTreatmentInfo(Map<String,Object> map);

  /**
   * @Method queryOperationInfo
   * @Desrciption 全院日报表：医疗手术信息
   * @param map
   * @Author liuqi1
   * @Date   2020/12/16 13:57
   * @Return java.util.Map<java.lang.String,java.lang.Object>
   **/
  Map<String,Object> queryMedicalOperationInfo(Map<String,Object> map);

  /**
   * @Method queryMedicalInptInfo
   * @Desrciption 全院日报表：医疗信息(在院、出院、转院)
   * @param map
   * @Author liuqi1
   * @Date   2020/12/16 15:00
   * @Return java.util.Map<java.lang.String,java.lang.Object>
   **/
  Map<String,Object> queryMedicalInOutptInfo(Map<String,Object> map);

  List<DynamicTable> queryBfcGroupMap(Map<String,Object> map);


  List<DynamicTable> queryBfcGroup(Map<String,Object> map);

  List<Map> queryFinancialCome(Map map);

  /**
   * @Method queryIncomeClassifyInfo
   * @Desrciption 全院日报表：计费类型
   * @param map
   * @Author 曾峰
   * @Date   2020/12/16 15:00
   * @Return java.util.Map<java.lang.String,java.lang.Object>
   **/
  List<Map<String,Object>> queryIncomeClassifyInfo(IncomeDTO incomeDTO);

  /**
   * @Method queryIncomeClassifyInfoBig
   * @Desrciption 全院日报表：大类计费类型
   * @param map
   * @Author 曾峰
   * @Date   2020/12/16 15:00
   * @Return java.util.Map<java.lang.String,java.lang.Object>
   **/
  List<Map<String,Object>> queryIncomeClassifyInfoBig(IncomeDTO incomeDTO);


  /**
   * @Method queryChargPatirntCostLedgerHeader
   * @Desrciption 病人费用统计动态表头查询
   * @param paraMap
   * @Author pengbo
   * @Date   2021/04/13 15:00
   * @Return java.util.Map<java.lang.String,java.lang.Object>
   **/
  List<Map<String,Object>>  queryChargPatirntCostLedgerHeader(Map<String, Object> paraMap);

  /**
   * @Method queryChargPatirntCostLedgerHeader
   * @Desrciption 病人费用统计数据查询
   * @param paraMap
   * @Author pengbo
   * @Date   2021/04/13 15:00
   * @Return java.util.Map<java.lang.String,java.lang.Object>
   **/
  List<Map<String,Object>> queryChargPatirntCostLedger(Map<String, Object> paraMap);


  /**
   * @Method queryItemName
   * @Desrciption 项目查询
   * @Author lizihuan
   * @Date   2021/06/07 10:05
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
   **/
List<InptCostDTO> queryItemName(InptCostDTO inptCostDTO);

  /**
   * @Description: 门诊收费员收入统计
   * @Param:
   * @Author: guanhongqiang
   * @Email: hongqiang.guan@powersi.com.cn
   * @Date 2021/6/22 20:19
   * @Return
   */
	List<Map> queryCollectorInComeStaMZ(Map map);

  /**
   * @Description: 门诊收费人员收入统计明细
   * @Param:
   * @Author: guanhongqiang
   * @Email: hongqiang.guan@powersi.com.cn
   * @Date 2021/6/22 20:20
   * @Return
   */
  List<Map> queryCollectorInComeStaDetailMZ(Map map);
  /**
   * @Method queryOutMedicationGet
   * @Desrciption 门诊领药统计
   * @Param [pharOutDistributeDTO]
   * @Author zhangguorui
   * @Date   2021/7/23 16:04
   * @Return java.util.List
   */
  List<Map> queryOutMedicationGet(PharOutDistributeDTO pharOutDistributeDTO);

  /**
   * @Description: 住院收费员收入统计，按缴款时间
   * @Param:
   * @Author: guanhongqiang
   * @Email: hongqiang.guan@powersi.com.cn
   * @Date 2021/8/3 10:16
   * @Return
   */
  List<Map> queryCollectorInComeStaZYJK(Map map);

  /**
   * @Menthod queryInPatientDaily
   * @Desrciption 按条件查询病人信息
   * @Param [inptVisitDTO]
   * @Author liuliyun
   * @Date   2021/08/05 11:53
   * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
   **/
  List<InptVisitDTO> queryInPatientDaily(InptVisitDTO inptVisitDTO);

  /**
   * @Menthod getMedicalCostMzTitle
   * @Desrciption  查询门诊计费类别
   * @Param map
   * @Author liuliyun
   * @Date   2021/08/24 17:05
   * @Return OutptCostDTO
   **/
  List<OutptCostDTO> getMedicalCostMzTitle(OutptVisitDTO outptVisitDTO);

  /**
   * @Menthod queryMedicalCostDetailMz
   * @Desrciption
   * @Param map
   * @Author liuliyun
   * @Date   2021/08/24 17:08
   * @Return List<Map>
   **/
  List<Map> queryMedicalCostDetailMz(OutptVisitDTO outptVisitDTO);

  /**
   * @Menthod queryMergeInPatient
   * @Desrciption 按条件查询病人
   * @Param [inptVisitDTO]
   * @Author liuliyun
   * @Date   2021/10/5 10:05
   * @Return java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>
   **/
  List<InptVisitDTO> queryMergeInPatient(InptVisitDTO inptVisitDTO);
  /**
   * @Meth: queryStroBusinessSummaryByMaterialClassification
   * @Description: 按材料分类汇总
   * @Param: [paraMap]
   * @return: java.util.List<java.util.Map>
   * @Author: zhangguorui
   * @Date: 2021/10/16
   */
  List<Map> queryStroBusinessSummaryByMaterialClassification(Map<String, Object> paraMap);

  /**
   * @Menthod getMedicalFinanceMzTitle
   * @Desrciption  查询门诊财务分类标题
   * @Param outptVisitDTO
   * @Author liuliyun
   * @Date   2021/10/22 15:30
   * @Return List<OutptCostDTO>
   **/
  List<OutptCostDTO> getMzMedicalFinanceTitle(OutptVisitDTO outptVisitDTO);

    /**
     * @Menthod getMzMedicalFinanceList
     * @Desrciption  查询门诊财务分类明细
     * @Param outptVisitDTO
     * @Author liuliyun
     * @Date   2021/10/22 16:17
     * @Return List<Map>
     **/
  List<Map> getMzMedicalFinanceList(OutptVisitDTO outptVisitDTO);

  /**
   * @Menthod getMzMedicalRegisterList
   * @Desrciption  查询门诊财务分类明细
   * @Param outptVisitDTO
   * @Author liuliyun
   * @Date   2021/10/25 8:52
   * @Return List<Map>
   **/
  List<Map> getMzMedicalRegisterList(OutptVisitDTO outptVisitDTO);


  /**
   * @Menthod getInptFinanceList
   * @Desrciption  查询住院财务分类明细
   * @Param inptVisitDTO
   * @Author liuliyun
   * @Date   2021/10/25 10:51
   * @Return List<Map>
   **/
  List<Map> getInptFinanceList(InptVisitDTO inptVisitDTO);

  /**
   * @Menthod getInptFinanceTitle
   * @Desrciption  查询门诊财务分类标题
   * @Param inptVisitDTO
   * @Author liuliyun
   * @Date   2021/10/25 10:52
   * @Return List<Map>
   **/
  List<InptCostDTO> getInptFinanceTitle(InptVisitDTO inptVisitDTO);

  /**
   * @Menthod getMzHospitalCardDetailList
   * @Desrciption 统计门诊已开住院证并住院的病人(明细)
   * @Param outptVisitDTO
   * @Author liuliyun
   * @Date   2021/12/06 11:00
   * @Return List<Map>
   **/
  List<Map> getMzHospitalCardDetailList(OutptVisitDTO outptVisitDTO);

  /**
   * @Menthod getMzHospitalCardTotalList
   * @Desrciption  查询门诊财务分类明细
   * @Param outptVisitDTO
   * @Author liuliyun
   * @Date   2021/12/05 11:01
   * @Return List<Map>
   **/
  List<Map> getMzHospitalCardTotalList(OutptVisitDTO outptVisitDTO);
  /**
   * @Meth: queryIncomeUpCode
   * @Description: 获得门诊、住院、挂号大类
   * @Param: [paraMap]
   * @return: java.util.List<java.lang.String>
   * @Author: zhangguorui
   * @Date: 2021/12/9
   */
  List<String> queryIncomeUpCode(IncomeDTO incomeDTO);
    /**
     * @Meth: queryIncomeOutptPrice
     * @Description: 门诊费用
     * 1.本月收入 outCurrentRealityPrice
     * 2.上年同期 outYearRealityPrice
     * 3.同比：outSameCompare
     * 4.上月收入：outMonthRealityPrice
     * 5.环比：outLinkCompare
     * @Param: [upCodeList]
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @Author: zhangguorui
     * @Date: 2021/12/10
     */
    List<IncomeDTO> queryIncomeOutptPrice(IncomeDTO incomeDTO);
    /**
     * @Meth: queryIncomeIntPrice
     * @Description: 住院费用
     * 1.本月收入 inCurrentRealityPrice
     * 2.上年同期 inYearRealityPrice
     * 3.同比：inSameCompare
     * 4.上月收入：inMonthRealityPrice
     * 5.环比：inLinkCompare
     * @Param: [upCodeList]
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @Author: zhangguorui
     * @Date: 2021/12/10
     */
    List<IncomeDTO> queryIncomeIntPrice(IncomeDTO incomeDTO);
    /**
     * @Meth: queryBaseFinanceClassify
     * @Description: 查出收入大类
     * @Param: [upCodeList]
     * @return: java.util.List<cn.hsa.module.outpt.statement.dto.IncomeDTO>
     * @Author: zhangguorui
     * @Date: 2021/12/10
     */
    List<IncomeDTO> queryBaseFinanceClassify(IncomeDTO incomeDTO);
}
