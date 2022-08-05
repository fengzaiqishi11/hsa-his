package cn.hsa.interf.dzpz.controller.statement.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.interf.statement.bo.PatientCostLedgerBO;
import cn.hsa.module.interf.statement.service.PatientCostLedgerService;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeDTO;
import cn.hsa.module.stro.stock.dto.StroStockDTO;
import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.outpt.statement.service.impl
 * @Class_name: PatientCostLedgerServiceImpl
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/11/10 15:22
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/outpt/statement")
@Slf4j
@Service("patientCostLedgerService_provider")
public class PatientCostLedgerServiceImpl extends HsafService implements PatientCostLedgerService {

  @Resource
  private PatientCostLedgerBO patientCostLedgerBO;

  /**
   * @Menthod queryPatirntCostLedger
   * @Desrciption 查询病人费用台账
   * @Param
   * [map]
   * @Author jiahong.yang
   * @Date   2020/11/10 15:22
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>>
   **/
  @Override
  public WrapperResponse<Map> queryPatirntCostLedger(Map map) {
    InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
    return WrapperResponse.success(patientCostLedgerBO.queryPatirntCostLedger(inptVisitDTO));
  }

  /**
   * @Menthod queryPatirntCostLedgerList
   * @Desrciption 查询病人费用台账
   * @Param
   * [list]
   * @Author caoliang
   * @Date   2020/11/10 15:22
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>>
   **/
  @Override
  public WrapperResponse<PageDTO> queryPatirntCostLedgerList(Map map) {
    InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
    return WrapperResponse.success(patientCostLedgerBO.queryPatirntCostLedgerList(inptVisitDTO));
  }
  @Override
  public WrapperResponse<Map> queryPatirntCostLedgerSum(Map map) {
    InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
    return WrapperResponse.success(patientCostLedgerBO.queryPatirntCostLedger(inptVisitDTO));
  }

  /**
  * @Menthod queryStockTime
  * @Desrciption 查询月底库存
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/12/14 15:52
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
  **/
  @Override
  public WrapperResponse<PageDTO> queryStockTime(Map map) {
    StroStockDTO stroStockDTO = MapUtils.get(map, "stroStockDTO");
    return WrapperResponse.success(patientCostLedgerBO.queryStockTime(stroStockDTO));
  }

  /**
   * @Menthod queryStroInvoicingLedger
   * @Desrciption 药房药库实时进销存报表
   *
   * @Param
   * [map]
   *
   * @Author jiahong.yang
   * @Date   2020/11/11 14:32
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
   **/
  @Override
  public WrapperResponse<PageDTO> queryStroInvoicingLedger(Map map) {
    StroInvoicingDTO stroInvoicingDTO = MapUtils.get(map, "stroInvoicingDTO");
    if(StringUtils.isEmpty(stroInvoicingDTO.getBizId())) {
      throw new AppException("请先选择库位");
    }
    return WrapperResponse.success(patientCostLedgerBO.queryStroInvoicingLedger(stroInvoicingDTO));
  }

  /**
  * @Menthod queryReturnDrugLedger
  * @Desrciption 药房退药报表查询
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2021/4/14 9:13
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
  **/
  @Override
  public WrapperResponse<PageDTO> queryReturnDrugLedger(Map map) {
    PharOutDistributeDTO pharOutDistributeDTO = MapUtils.get(map, "pharOutDistributeDTO");
    return WrapperResponse.success(patientCostLedgerBO.queryReturnDrugLedger(pharOutDistributeDTO));
  }

  /**
  * @Menthod queryCollectorInComeSta
  * @Desrciption 住院收费人员收入统计
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/12/11 14:55
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
  **/
  @Override
  public WrapperResponse<PageDTO> queryCollectorInComeSta(Map map) {
    return WrapperResponse.success(patientCostLedgerBO.queryCollectorInComeSta(map));
  }

  /**
  * @Menthod queryInPatient
  * @Desrciption 在院/出院病人统计报表
  *
  * @Param
  * [map]
  *
  * @Author jiahong.yang
  * @Date   2020/12/12 15:30
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>>
  **/
  @Override
  public WrapperResponse<PageDTO> queryInPatient(Map map) {
    InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
    return WrapperResponse.success(patientCostLedgerBO.queryInPatient(inptVisitDTO));
  }

  /**
   * @Method queryOutOrInPospitalItemInfo
   * @Desrciption 门诊住院项目使用量统计查询
   * @param paraMap
   * @Author liuqi1
   * @Date   2020/11/10 20:22
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
   **/
  @Override
  public WrapperResponse<PageDTO> queryOutOrInPospitalItemInfo(Map<String, Object> paraMap) {
    return WrapperResponse.success(patientCostLedgerBO.queryOutOrInPospitalItemInfo(paraMap));
  }

  /**
   * @Method queryOutOrInPospitalItemInfoSum
   * @Desrciption 门诊住院项目使用量统计查询合计
   * @param paraMap
   * @Author caoliang
   * @Date   2021/6/15 14:50
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
   **/

  @Override
  public WrapperResponse<PageDTO> queryOutOrInPospitalItemInfoSum(Map<String, Object> paraMap) {
    return WrapperResponse.success(patientCostLedgerBO.queryOutOrInPospitalItemInfoSum(paraMap));
  }

  /**
   * @Method queryOutptDeptIncome
   * @Desrciption 门诊科室/医生收入统计
   @params [paraMap]
    * @Author chenjun
   * @Date   2020-11-12 10:15
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
   **/
  @Override
  public WrapperResponse<Map> queryOutptDeptIncomeTableHead(Map paraMap) {
    InptVisitDTO inptVisitDTO = MapUtils.get(paraMap, "inptVisitDTO");
    Map map = patientCostLedgerBO.queryOutptDeptIncomeTableHead(inptVisitDTO);
    return WrapperResponse.success(map);
  }
  /**
   * @Method queryOutptDeptIncome
   * @Desrciption 门诊科室/医生收入统计
   @params [paraMap]
    * @Author chenjun
   * @Date   2020-11-12 10:15
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
   **/
  @Override
  public WrapperResponse<PageDTO> queryOutptDeptIncome(Map paraMap) {
    InptVisitDTO inptVisitDTO = MapUtils.get(paraMap, "inptVisitDTO");
    PageDTO pageDTO = patientCostLedgerBO.queryOutptDeptIncome(inptVisitDTO);
    return WrapperResponse.success(pageDTO);
  }

  /**
   * @Method queryInptMedication
   * @Desrciption 住院科室用药统计
   @params [paraMap]
    * @Author chenjun
   * @Date   2020-11-12 15:40
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
   **/
  @Override
  public WrapperResponse<PageDTO> queryInptMedication(Map paraMap) {
    InptVisitDTO inptVisitDTO = MapUtils.get(paraMap, "inptVisitDTO");
    return WrapperResponse.success(patientCostLedgerBO.queryInptMedication(inptVisitDTO));
  }

  /**
   * @Method queryFinancialIncome
   * @Desrciption 财务收入统计表头
     @params [paraMap]
   * @Author chenjun
   * @Date   2020-12-29 16:34
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
  **/
  @Override
  public WrapperResponse<Map> queryFinancialIncome(Map paraMap) {
    Map map = patientCostLedgerBO.queryFinancialIncome(paraMap);
    return WrapperResponse.success(map);
  }

  /**
   * @Menthod queryFinancialIncomeList
   * @Desrciption 财务收入统计分页数据
   * @param paraMap
   * @Author caoliang
   * @Date   2021/6/10 20:04
   * @Return java.util.List<java.util.Map>
   **/
  @Override
  public WrapperResponse<PageDTO> queryFinancialIncomeList(Map paraMap) {
    return WrapperResponse.success(patientCostLedgerBO.queryFinancialIncomeList(paraMap));
  }

  /**
   * @Menthod queryStroBusinessSummary
   * @Desrciption 药库汇总统计
   * @param paraMap
   * @Author xingyu.xie
   * @Date   2020/11/14 15:25
   * @Return java.util.List<java.util.Map>
   **/
  public WrapperResponse<PageDTO> queryStroBusinessSummary(Map<String, Object> paraMap){ ;
    return WrapperResponse.success(patientCostLedgerBO.queryStroBusinessSummary(paraMap));
  }
//合计
  public WrapperResponse<PageDTO> queryStroBusinessSummarySum(Map<String, Object> paraMap){ ;
    return WrapperResponse.success(patientCostLedgerBO.queryStroBusinessSummarySum(paraMap));
  }
  /**
   * @Menthod queryStroBusinessSummary
   * @Desrciption 药房汇总统计
   * @param paraMap
   * @Author xingyu.xie
   * @Date   2020/11/14 15:25
   * @Return java.util.List<java.util.Map>
   **/
  public WrapperResponse<PageDTO> queryPharBusinessSummary(Map<String, Object> paraMap){
    return WrapperResponse.success(patientCostLedgerBO.queryPharBusinessSummary(paraMap));
  }

  /**
   * @Menthod queryPharConsume
   * @Desrciption  查询所有盘亏，报损，发药的台账数据
   * @param paraMap
   * @Author xingyu.xie
   * @Date   2020/11/11 20:39
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
   **/
  public WrapperResponse<Map<String,List<Map>>> queryPharConsume(Map<String, Object> paraMap) {
    Map<String, List<Map>> stringListMap = patientCostLedgerBO.queryPharConsume(paraMap);
    return WrapperResponse.success(stringListMap);
  }

    /**
     * @param paraMap
     * @Menthod queryPharConsumeList
     * @Desrciption 查询所有盘亏，报损，发药的台账数据list
     * @Author caoliang
     * @Date 2021/6/11 9:34
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < java.util.Map>>
     **/
    public WrapperResponse<PageDTO> queryPharConsumeList(Map<String, Object> paraMap) {
        return WrapperResponse.success(patientCostLedgerBO.queryPharConsumeList(paraMap));
    }

  /**
   * @Method queryIncomeClassifyInfo
   * @Desrciption 全院月收入分类统计
   * @param paraMap
   * @Author liuqi1
   * @Date   2020/11/18 17:08
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
   **/
  @Override
//  public WrapperResponse<List<Map<String, Object>>> queryIncomeClassifyInfo(Map<String, Object> paraMap) {
  public WrapperResponse<PageDTO> queryIncomeClassifyInfo(Map<String, Object> paraMap) {
//    List<Map<String, Object>> list = patientCostLedgerBO.queryIncomeClassifyInfo(paraMap);
    return WrapperResponse.success( patientCostLedgerBO.queryIncomeClassifyInfo(MapUtils.get(paraMap,"incomeDTO")));
  }

  /**
  * @Menthod queryOutptCostAndRegisterCost
  * @Desrciption  开方科室/开方医生收入统计
   * @param paraMap
  * @Author xingyu.xie
  * @Date   2020/12/12 10:59
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
  **/
  public WrapperResponse<Map<String,Object>> queryOutptCostAndRegisterCost(Map<String, Object> paraMap){
    return WrapperResponse.success(patientCostLedgerBO.queryOutptCostAndRegisterCost(paraMap));
  }

  /**
   * @Menthod queryOutptCostAndRegisterCostDaoChu
   * @Desrciption  开方科室/开方医生收入统计
   * @param paraMap
   * @Author xingyu.xie
   * @Date   2020/12/12 10:59
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
   **/
  public WrapperResponse<Map<String,Object>> queryOutptCostAndRegisterCostDaoChu(Map<String, Object> paraMap){
    return WrapperResponse.success(patientCostLedgerBO.queryOutptCostAndRegisterCostDaoChu(paraMap));
  }


  /**
   * @Menthod queryOutptCostAndRegisterCostList
   * @Desrciption  开方科室/开方医生收入统计分页数据
   * @param paraMap
   * @Author caoliang
   * @Date   2021/6/10 21:00
   * @Return java.util.List<java.lang.Map>
   **/
  public WrapperResponse<PageDTO> queryOutptCostAndRegisterCostList(Map<String, Object> paraMap){
    return WrapperResponse.success(patientCostLedgerBO.queryOutptCostAndRegisterCostList(paraMap));
  }
  public WrapperResponse<Map<String,Object>> queryOutptCostAndRegisterCostSum(Map<String, Object> paraMap){
      return WrapperResponse.success(patientCostLedgerBO.queryOutptCostAndRegisterCostSum(paraMap));
  }
  /**
   * @Method queryInvoiceStatistics
   * @Desrciption 门诊/住院明细收费项目查询（按结算时间/按费用发生时间）
     @params [paraMap]
   * @Author chenjun
   * @Date   2020-12-14 09:47
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
  **/
  @Override
  public WrapperResponse<PageDTO> queryInvoiceStatistics(Map paraMap) {
    return WrapperResponse.success(patientCostLedgerBO.queryInvoiceStatistics(paraMap));
  }

  /**
   * @Method queryChargeDetail
   * @Desrciption 门诊/住院明细收费项目查询（按结算时间/按费用发生时间）
     @params [paraMap]
   * @Author chenjun
   * @Date   2020-12-14 09:48
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map>>
  **/
  @Override
  public WrapperResponse<Map> queryChargeDetail(Map paraMap) {
    return WrapperResponse.success(patientCostLedgerBO.queryChargeDetail(paraMap));
  }

  /**
   * @Method queryChargeDetailList
   * @Desrciption 门诊/住院明细收费项目查询（按结算时间/按费用发生时间）分页数据
   @params [paraMap]
    * @Author caoliang
   * @Date   2021-6-10 20:50
   * @Return java.util.List<java.util.Map>
   **/
  @Override
  public WrapperResponse<PageDTO> queryChargeDetailList(Map paraMap) {
    return WrapperResponse.success(patientCostLedgerBO.queryChargeDetailList(paraMap));
  }

  /**
  * @Method queryDayReportWithHospital
  * @Desrciption 全院日报表统计
  * @param paraMap
  * @Author liuqi1
  * @Date   2020/12/16 15:51
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
  **/
  @Override
  public WrapperResponse<Map<String,Object>> queryDayReportWithHospital(Map<String,Object> paraMap) {
    Map map = patientCostLedgerBO.queryDayReportWithHospital(paraMap);
    return WrapperResponse.success(map);
  }

  /**
   * @param paraMap
   * @Method queryChargPatirntCostLedger
   * @Desrciption 病人收费统计
   * @Author pengbo
   * @Date 2021/04/13 15:00
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < java.util.Map < java.lang.String, java.lang.Object>>>
   **/
  @Override
  public WrapperResponse<Map<String, Object>> queryChargPatirntCostLedger(Map<String, Object> paraMap) {
    Map map = patientCostLedgerBO.queryChargPatirntCostLedger(paraMap);
    return WrapperResponse.success(map);
  }
  /**
   * @Method queryItemName
   * @Desrciption 项目查询
   * @Author lizihuan
   * @Date   2021/06/07 10:05
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
   **/

//  public WrapperResponse<PageDTO> queryItemName(Map map){
//    return WrapperResponse.success( patientCostLedgerBO.queryItemName(map));
//  };
  public WrapperResponse<PageDTO> queryItemName(Map map){
    return WrapperResponse.success( patientCostLedgerBO.queryItemName(map));
 }

 /**
  * @Description: 收费员收入统计
  * @Param:
  * @Author: guanhongqiang
  * @Email: hongqiang.guan@powersi.com.cn
  * @Date 2021/7/2 16:20
  * @Return
  */
  @Override
  public WrapperResponse<PageDTO> queryTollCollectorIncomeStatistics(Map map) {
    return WrapperResponse.success(patientCostLedgerBO.queryTollCollectorIncomeStatistics(map));
  }

  /**
   * @Method queryOutMedicationGet
   * @Desrciption 门诊科室用药统计
   * @Param [map]
   * @Author zhangguorui
   * @Date   2021/7/23 15:56
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
   */
  @Override
  public WrapperResponse<PageDTO> queryOutMedicationGet(Map map) {
    return WrapperResponse.success(patientCostLedgerBO.queryOutMedicationGet(MapUtils.get(map,"pharOutDistributeDTO")));
  }


  /**
   * @Menthod queryInPatientDaily
   * @Desrciption 在院病人实况统计报表
   * @Param [map]
   * @Author liuliyun
   * @Date   2021/08/05 11:21
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.inpt.doctor.dto.InptVisitDTO>>
   **/
  @Override
  public WrapperResponse<PageDTO> queryInPatientDaily(Map map) {
    InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
    return WrapperResponse.success(patientCostLedgerBO.queryInPatientDaily(inptVisitDTO));
  }

  /**
   * @Menthod queryMedicalCostMz
   * @Desrciption 门诊医疗汇总报表
   * @Param [map]
   * @Author liuliyun
   * @Date   2021/08/21 16：21
   * @Return PageDTO
   **/
  @Override
  public WrapperResponse<PageDTO> queryMedicalCostMz(Map map) {
    OutptVisitDTO outptVisitDTO =MapUtils.get(map,"outptVisitDTO");
    return WrapperResponse.success(patientCostLedgerBO.queryMedicalCostMz(outptVisitDTO));
  }

  /**
   * @Menthod queryMedicalCostTitle
   * @Desrciption 门诊医疗汇总报表
   * @Param [map]
   * @Author liuliyun
   * @Date   2021/08/24 20:21
   * @Return Map
   **/
  @Override
  public WrapperResponse<Map> queryMedicalCostTitle(Map map) {
    OutptVisitDTO outptVisitDTO = MapUtils.get(map, "outptVisitDTO");
    return WrapperResponse.success(patientCostLedgerBO.queryMedicalCostTitle(outptVisitDTO));
  }

  /**
   * @Menthod queryMzPatientFinanceCostList
   * @Desrciption 查询门诊财务分类明细
   * @Param map
   * @Author liuliyun
   * @Date   2021/10/22 16:28
   * @Return WrapperResponse<PageDTO>
   **/
  @Override
  public WrapperResponse<PageDTO> queryMzPatientFinanceCostList(Map map) {
    OutptVisitDTO outptVisitDTO =MapUtils.get(map,"outptVisitDTO");
    return WrapperResponse.success(patientCostLedgerBO.queryMzPatientFinanceCostList(outptVisitDTO));
  }

  /**
   * @Menthod getMedicalFinanceMzTitle
   * @Desrciption  查询门诊财务分类标题
   * @Param map
   * @Author liuliyun
   * @Date   2021/10/22 16:27
   * @Return WrapperResponse<Map>
   **/
  @Override
  public WrapperResponse<Map> getMzMedicalFinanceTitle(Map map) {
    OutptVisitDTO outptVisitDTO = MapUtils.get(map, "outptVisitDTO");
    return WrapperResponse.success(patientCostLedgerBO.getMzMedicalFinanceTitle(outptVisitDTO));
  }


  /**
   * @Menthod getInptFinanceList
   * @Desrciption 查询住院财务分类明细
   * @Param map
   * @Author liuliyun
   * @Date   2021/10/25 14:28
   * @Return WrapperResponse<PageDTO>
   **/
  @Override
  public WrapperResponse<PageDTO> getInptFinanceList(Map map) {
    InptVisitDTO inptVisitDTO =MapUtils.get(map,"inptVisitDTO");
    return WrapperResponse.success(patientCostLedgerBO.getInptFinanceList(inptVisitDTO));
  }

  /**
   * @Menthod getInptFinanceTitle
   * @Desrciption  查询住院财务分类标题
   * @Param map
   * @Author liuliyun
   * @Date   2021/10/25 14:27
   * @Return WrapperResponse<Map>
   **/
  @Override
  public WrapperResponse<Map> getInptFinanceTitle(Map map) {
    InptVisitDTO inptVisitDTO =MapUtils.get(map,"inptVisitDTO");
    return WrapperResponse.success(patientCostLedgerBO.getInptFinanceTitle(inptVisitDTO));
  }


  /**
   * @Menthod queryHospitalCardList
   * @Desrciption 统计入院证
   * @Param inptVisitDTO
   * @Author liuliyun
   * @Date   2021/12/06 10:51
   * @Return WrapperResponse<PageDTO>
   **/
  @Override
  public WrapperResponse<PageDTO> queryHospitalCardList(Map map) {
    OutptVisitDTO outptVisitDTO =MapUtils.get(map,"outptVisitDTO");
    return WrapperResponse.success(patientCostLedgerBO.queryHospitalCardList(outptVisitDTO));
  }

  /**
   * @Menthod queryHospitalCardList
   * @Desrciption 门诊住院项目使用量统计（按科室过滤）
   * @Param inptVisitDTO
   * @Author liuliyun
   * @Date   2021/12/07 11:51
   * @Return WrapperResponse<PageDTO>
   **/
  @Override
  public WrapperResponse<PageDTO> queryOutptorInHosptialItemUseInfo(Map<String, Object> paraMap) {
    return WrapperResponse.success(patientCostLedgerBO.queryOutptorInHosptialItemUseInfo(paraMap));
  }


  /**
   * @Menthod getInptOperFinanceList
   * @Desrciption 查询住院手术财务分类明细
   * @Param inptVisitDTO
   * @Author liuliyun
   * @Date   2021/12/14 10:10
   * @Return cn.hsa.base.PageDTO
   **/
  @Override
  public WrapperResponse<PageDTO> getInptOperFinanceList(Map map) {
    InptVisitDTO inptVisitDTO =MapUtils.get(map,"inptVisitDTO");
    return WrapperResponse.success(patientCostLedgerBO.getInptOperFinanceList(inptVisitDTO));
  }

  /**
   * @Menthod getInptOperFinanceTitle
   * @Desrciption  查询住院手术财务分类标题
   * @Param inptVisitDTO
   * @Author liuliyun
   * @Date   2021/12/14 10:14
   * @Return Map
   **/
  @Override
  public WrapperResponse<Map> getInptOperFinanceTitle(Map map) {
    InptVisitDTO inptVisitDTO =MapUtils.get(map,"inptVisitDTO");
    return WrapperResponse.success(patientCostLedgerBO.getInptOperFinanceTitle(inptVisitDTO));
  }

  /**
   * @Description: 查询门诊财务月报表，按选定的时间区间，逐日统计药品或项目的自费收入，医保收入
   * @Param:
   * @Author: guanhongqiang
   * @Email: hongqiang.guan@powersi.com.cn
   * @Date 2021/12/20 14:57
   * @Return
   */
  @Override
  public WrapperResponse<PageDTO> queryMzMonthlyReport(Map<String, Object> paraMap) {
    return WrapperResponse.success(patientCostLedgerBO.queryMzMonthlyReport(paraMap));
  }

  /**
   * @Menthod getoutptMonthDaily
   * @Desrciption  查询门诊月结报表
   * @Param OutptCostDTO
   * @Author yuelong.chen
   * @Date   2021/12/24 12:14
   * @Return List<OutptCostDTO>
   *
   * @return*/
  @Override
  public WrapperResponse<Map<String, List<OutptCostDTO>>> queryoutptMonthDaily(Map map) {
    OutptCostDTO outptCostDTO =MapUtils.get(map,"outptCostDTO");
    return WrapperResponse.success(patientCostLedgerBO.queryoutptMonthDaily(outptCostDTO));
  }


  /**
   * @Menthod queryOutptIncomePage
   * @Desrciption  门诊医生科室收入统计分页数据
   * @param map
   * @Author liuliyun
   * @Date   2022/1/11 16:10
   * @Return java.util.List<java.lang.Map>
   **/
  @Override
  public WrapperResponse<PageDTO> queryOutptIncomePage(Map map){
    return WrapperResponse.success(patientCostLedgerBO.queryOutptIncomePage(map));
  }

  public WrapperResponse<Map<String,Object>> queryOutptIncomeList(Map map){
    return WrapperResponse.success(patientCostLedgerBO.queryOutptIncomeList(map));
  }

  /**
   * @Menthod queryHosptialInComeList
   * @Desrciption  住院业务收入统计报表
   * @param map
   * @Author liuliyun
   * @Date   2022/2/10 16:10
   * @Return PageDTO
   **/
  @Override
  public WrapperResponse<PageDTO> queryHosptialInComeList(Map map){
    return WrapperResponse.success(patientCostLedgerBO.queryHosptialInComeList(map));
  }

  /**
   * @Menthod queryHosptialInComeListTitle
   * @Desrciption  住院业务收入统计报表表头
   * @param map
   * @Author liuliyun
   * @Date   2022/2/10 16:10
   * @Return java.util.List<java.lang.Map>
   **/
  @Override
  public WrapperResponse<Map<String,Object>> queryHosptialInComeListTitle(Map map){
    return WrapperResponse.success(patientCostLedgerBO.queryHosptialInComeListTitle(map));
  }

  @Override
  public WrapperResponse<List<Map<String, Object>>> queryZyFeeIncomeList(Map map) {
    return WrapperResponse.success(patientCostLedgerBO.queryZyFeeIncomeList(map));
  }

  @Override
  public WrapperResponse<List<Map<String, Object>>> queryMzFeeIncomeList(Map map) {
    return WrapperResponse.success(patientCostLedgerBO.queryMzFeeIncomeList(map));
  }
}
