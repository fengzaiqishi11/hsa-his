package cn.hsa.outpt.statement.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.outpt.statement.bo.PatientCostLedgerBO;
import cn.hsa.module.outpt.statement.service.PatientCostLedgerService;
import cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeDTO;
import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

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
  public WrapperResponse<Map> queryOutptDeptIncome(Map paraMap) {
    InptVisitDTO inptVisitDTO = MapUtils.get(paraMap, "inptVisitDTO");
    Map map = patientCostLedgerBO.queryOutptDeptIncome(inptVisitDTO);
    return WrapperResponse.success(map);
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
    return WrapperResponse.success( patientCostLedgerBO.queryIncomeClassifyInfo(paraMap));
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

}
