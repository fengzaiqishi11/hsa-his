package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDO;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.outpt.statement.service.PatientCostLedgerService;
import cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeDTO;
import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.DownloadFileUtil;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import groovy.util.logging.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt
 * @Class_name: StatemnetController
 * @Describe:
 * @Author: yangjiahongstatement
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/11/10 14:17
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping("/web/outpt/statement")
@Slf4j
public class StatemnetController extends BaseController {

  @Resource
  private PatientCostLedgerService patientCostLedgerService_consumer;


  /**
   * @Menthod 病人费用台账
   * @Desrciption
   * @Param [inptVisitDTO]
   * @Author jiahong.yang
   * @Date 2020/11/10 20:37
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < cn.hsa.module.inpt.doctor.dto.InptVisitDTO>>
   **/
  @PostMapping("/queryPatirntCostLedger")
  public WrapperResponse<Map> queryPatirntCostLedger(@RequestBody InptVisitDTO inptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO userDTO = getSession(req, res) ;
    inptVisitDTO.setHospCode(userDTO.getHospCode());
    map.put("hospCode", userDTO.getHospCode());
    map.put("inptVisitDTO", inptVisitDTO);
    return patientCostLedgerService_consumer.queryPatirntCostLedger(map);
  }


  /**
   * @Menthod 病人费用台账List
   * @Desrciption
   * @Param [inptVisitDTO]
   * @Author caoliang
   * @Date 2021/6/11 10:37
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < cn.hsa.module.inpt.doctor.dto.InptVisitDTO>>
   **/
  @GetMapping("/queryPatirntCostLedgerList")
  public WrapperResponse<PageDTO> queryPatirntCostLedgerList(InptVisitDTO inptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO userDTO = getSession(req, res) ;
    inptVisitDTO.setHospCode(userDTO.getHospCode());
    map.put("hospCode", userDTO.getHospCode());
    map.put("inptVisitDTO", inptVisitDTO);
    return patientCostLedgerService_consumer.queryPatirntCostLedgerList(map);
  }

  /**
   * @Menthod queryStroInvoicingLedger
   * @Desrciption 药房药库进销存实时报表
   * @Param [stroInvoicingDTO]
   * @Author jiahong.yang
   * @Date 2020/11/12 14:25
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < java.util.Map>>
   **/
  @GetMapping("/queryStroInvoicingLedger")
  public WrapperResponse<PageDTO> queryStroInvoicingLedger(StroInvoicingDTO stroInvoicingDTO,HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO userDTO = getSession(req, res) ;
    stroInvoicingDTO.setHospCode(userDTO.getHospCode());
//    stroInvoicingDTO.setBizId(loginDeptId);
    map.put("hospCode", userDTO.getHospCode());
    map.put("stroInvoicingDTO", stroInvoicingDTO);
    return patientCostLedgerService_consumer.queryStroInvoicingLedger(map);
  }

  /**
   * @Menthod queryReturnDrugLedger
   * @Desrciption 药房退药报表查询
   * @Param []
   * @Author jiahong.yang
   * @Date 2021/4/14 9:00
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < java.util.Map>>
   **/
  @GetMapping("/queryReturnDrugLedger")
  public WrapperResponse<PageDTO> queryReturnDrugLedger(PharOutDistributeDTO pharOutDistributeDTO,HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO userDTO = getSession(req, res) ;
    pharOutDistributeDTO.setDeptId(userDTO.getLoginBaseDeptDTO().getId());
    pharOutDistributeDTO.setHospCode(userDTO.getHospCode());
    map.put("hospCode", userDTO.getHospCode());
    map.put("pharOutDistributeDTO", pharOutDistributeDTO);
    return patientCostLedgerService_consumer.queryReturnDrugLedger(map);
  }


  /**
   * @Menthod queryCollectorInComeSta
   * @Desrciption 住院收费人员收入统计
   * @Param [map]
   * @Author jiahong.yang
   * @Date 2020/12/11 14:50
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < java.util.Map>>
   **/
  @GetMapping("/queryCollectorInComeSta")
  public WrapperResponse<PageDTO> queryCollectorInComeStaGet(@RequestParam Map map,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    map.put("hospCode", userDTO.getHospCode());
    return patientCostLedgerService_consumer.queryCollectorInComeSta(map);
  }

  /**
   * @Description: 收费员收入统计
   * @Param:
   * @Author: guanhongqiang
   * @Email: hongqiang.guan@powersi.com.cn
   * @Date 2021/7/2 16:16
   * @Return
   */
  @GetMapping("/queryTollCollectorIncomeStatistics")
  public WrapperResponse<PageDTO> queryTollCollectorIncomeStatistics(@RequestParam Map map,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    map.put("hospCode", userDTO.getHospCode());
    return patientCostLedgerService_consumer.queryTollCollectorIncomeStatistics(map);
  }

  /**
   * @Menthod queryInPatient
   * @Desrciption 在院/出院病人统计报表
   * @Param [map]
   * @Author jiahong.yang
   * @Date 2020/12/12 15:26
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < cn.hsa.module.inpt.doctor.dto.InptVisitDTO>>
   **/
  @GetMapping("/queryInPatient")
  public WrapperResponse<PageDTO> queryInPatientGet(InptVisitDTO inptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO userDTO = getSession(req, res) ;
    map.put("hospCode", userDTO.getHospCode());
    inptVisitDTO.setHospCode(userDTO.getHospCode());
    // 注释by张国瑞 在院病人查询功能 改为下拉列表的形式 选择主管医生而不是选择当前登录的医生
//    if (inptVisitDTO != null && "1".equals(inptVisitDTO.getZgbrQuery())) {
//      inptVisitDTO.setZgDoctorId(userDTO.getId());
//    }
    map.put("inptVisitDTO", inptVisitDTO);
    return patientCostLedgerService_consumer.queryInPatient(map);
  }

  @PostMapping("/queryInPatient")
  public WrapperResponse<PageDTO> queryInPatient(@RequestBody InptVisitDTO inptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO userDTO = getSession(req, res) ;
    map.put("hospCode", userDTO.getHospCode());
    inptVisitDTO.setHospCode(userDTO.getHospCode());
    map.put("inptVisitDTO", inptVisitDTO);
    return patientCostLedgerService_consumer.queryInPatient(map);
  }

  /**
   * @param paraMap
   * @Method queryOutOrInPospitalItemInfo
   * @Desrciption 门诊住院项目使用量统计查询
   * @Author liuqi1
   * @Date 2020/11/10 20:24
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < java.util.Map < java.lang.String, java.lang.Object>>>
   **/
  @GetMapping("/queryOutOrInPospitalItemInfo")
  public WrapperResponse<PageDTO> queryOutOrInPospitalItemInfoGet(@RequestParam Map<String, Object> paraMap,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    paraMap.put("hospCode", userDTO.getHospCode());

    WrapperResponse<PageDTO> listWrapperResponse = patientCostLedgerService_consumer.queryOutOrInPospitalItemInfo(paraMap);
    return listWrapperResponse;
  }

  @GetMapping("/queryOutOrInPospitalItemInfoSum")
  public WrapperResponse<PageDTO> queryOutOrInPospitalItemInfoSum(@RequestParam Map<String, Object> paraMap,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    paraMap.put("hospCode", userDTO.getHospCode());

    WrapperResponse<PageDTO> listWrapperResponse = patientCostLedgerService_consumer.queryOutOrInPospitalItemInfoSum(paraMap);
    return listWrapperResponse;
  }
  /**
   * @Method queryOutptDeptIncome
   * @Desrciption 门诊科室/医生收入统计
   * @params [inptVisitDTO]
   * @Author chenjun
   * @Date 2020-11-12 10:14
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < cn.hsa.module.inpt.doctor.dto.InptVisitDTO>>
   **/
  @PostMapping("/queryOutptDeptIncome")
  public WrapperResponse<Map> queryOutptDeptIncome(@RequestBody InptVisitDTO inptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO userDTO = getSession(req, res) ;
    inptVisitDTO.setHospCode(userDTO.getHospCode());
    map.put("hospCode", userDTO.getHospCode());
    map.put("inptVisitDTO", inptVisitDTO);
    return patientCostLedgerService_consumer.queryOutptDeptIncome(map);
  }

  /**
   * @Method queryInptMedication
   * @Desrciption 住院科室用药统计
   * @params [inptVisitDTO]
   * @Author chenjun
   * @Date 2020-11-12 15:39
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
   **/
  @GetMapping("/queryInptMedication")
  public WrapperResponse<PageDTO> queryInptMedicationGet(InptVisitDTO inptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO userDTO = getSession(req, res) ;
    inptVisitDTO.setHospCode(userDTO.getHospCode());
    map.put("hospCode", userDTO.getHospCode());
    map.put("inptVisitDTO", inptVisitDTO);
    return patientCostLedgerService_consumer.queryInptMedication(map);
  }


  /**
   * @Method queryOutptDeptIncome
   * @Desrciption 财务收入统计表头
   * @params [inptVisitDTO]
   * @Author chenjun
   * @Date 2020-12-29 16:30
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
   **/
  @PostMapping("/queryFinancialIncome")
  public WrapperResponse<Map> queryFinancialIncome(@RequestBody Map<String, Object> paraMap,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    paraMap.put("hospCode", userDTO.getHospCode());
    return patientCostLedgerService_consumer.queryFinancialIncome(paraMap);
  }

  /**
   * @Menthod queryFinancialIncomeList
   * @Desrciption 财务收入统计分页数据
   * @param paraMap
   * @Author caoliang
   * @Date   2021/6/10 20:04
   * @Return java.util.List<java.util.Map>
   **/
  @GetMapping("/queryFinancialIncomeList")
  public WrapperResponse<PageDTO> queryFinancialIncomeList(@RequestParam Map<String, Object> paraMap,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    paraMap.put("hospCode", userDTO.getHospCode());
    return patientCostLedgerService_consumer.queryFinancialIncomeList(paraMap);
  }


  /**
   * @param paraMap
   * @Menthod queryStroBusinessSummary
   * @Desrciption 入库汇总统计
   * @Author xingyu.xie
   * @Date 2020/11/14 15:27
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < java.util.Map>>
   **/
  @GetMapping("/queryStroBusinessSummary")
  WrapperResponse<PageDTO> queryStroBusinessSummary(@RequestParam Map<String, Object> paraMap,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    paraMap.put("hospCode", userDTO.getHospCode());
    WrapperResponse<PageDTO> listWrapperResponse = patientCostLedgerService_consumer.queryStroBusinessSummary(paraMap);
    return listWrapperResponse;
  }


  /**
   * @param paraMap
   * @Menthod queryPharBusinessSummarySum
   * @Desrciption 药房汇总统计合计
   * @Author caoliang
   * @Date 2021/6/15 14:12
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < java.util.Map>>
   **/
  @GetMapping("/queryStroBusinessSummarySum")
  WrapperResponse<PageDTO> queryStroBusinessSummarySum(@RequestParam Map<String, Object> paraMap,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    paraMap.put("hospCode", userDTO.getHospCode());
    WrapperResponse<PageDTO> listWrapperResponse = patientCostLedgerService_consumer.queryStroBusinessSummarySum(paraMap);
    return listWrapperResponse;
  }

  /**
   * @param paraMap
   * @Menthod queryPharBusinessSummarySum
   * @Desrciption 药房汇总统计合计
   * @Author caoliang
   * @Date 2021/6/15 14:12
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < java.util.Map>>
   **/
  @GetMapping("/queryPharBusinessSummary")
  WrapperResponse<PageDTO> queryPharBusinessSummary(@RequestParam Map<String, Object> paraMap,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    paraMap.put("hospCode", userDTO.getHospCode());
    WrapperResponse<PageDTO> listWrapperResponse = patientCostLedgerService_consumer.queryPharBusinessSummary(paraMap);
    return listWrapperResponse;
  }


  /**
   * @param paraMap
   * @Menthod queryPharConsume
   * @Desrciption 查询所有盘亏，报损，发药的台账数据
   * @Author xingyu.xie
   * @Date 2020/11/12 21:34
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map < java.lang.String, java.util.List < java.util.Map>>>
   **/
  @PostMapping("/queryPharConsume")
  WrapperResponse<Map<String, List<Map>>> queryPharConsume(@RequestBody Map<String, Object> paraMap,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    paraMap.put("hospCode", userDTO.getHospCode());
    WrapperResponse<Map<String, List<Map>>> listWrapperResponse = patientCostLedgerService_consumer.queryPharConsume(paraMap);
    return listWrapperResponse;
  }

  /**
   * @param paraMap
   * @Menthod queryPharConsumeList
   * @Desrciption 查询所有盘亏，报损，发药的台账数据list
   * @Author caoliang
   * @Date 2021/6/11 9:34
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < java.util.Map>>
   **/
  @GetMapping("/queryPharConsumeList")
  WrapperResponse<PageDTO> queryPharConsumeList(@RequestParam Map<String, Object> paraMap,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    paraMap.put("hospCode", userDTO.getHospCode());
    WrapperResponse<PageDTO> listWrapperResponse = patientCostLedgerService_consumer.queryPharConsumeList(paraMap);
    return listWrapperResponse;
  }

  /**
   * @param filePath 文件路径
   * @param response
   * @Method downloadModelExcel
   * @Desrciption 模板下载
   * @Author liuqi1
   * @Date 2020/11/25 13:54
   * @Return void
   **/
  @PostMapping("/downloadModelExcel")
  public void downloadModelExcel(String filePath, HttpServletResponse response,HttpServletRequest req, HttpServletResponse res) {
    //文件下载
    DownloadFileUtil.downloadFile(filePath, response);
  }

  /**
   * @param paraMap
   * @Method queryIncomeClassifyInfo
   * @Desrciption 全院月收入分类统计
   * @Author liuqi1
   * @Date 2020/11/18 17:05
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < java.util.Map < java.lang.String, java.lang.Object>>>
   **/
/*  @PostMapping("/queryIncomeClassifyInfo")
  public WrapperResponse<List<Map<String, Object>>> queryIncomeClassifyInfo(@RequestBody Map<String, Object> paraMap) {
    paraMap.put("hospCode", hospCode);

    WrapperResponse<List<Map<String, Object>>> listWrapperResponse = patientCostLedgerService_consumer.queryIncomeClassifyInfo(paraMap);
    return listWrapperResponse;
  }*/

  @GetMapping("/queryIncomeClassifyInfo")
  public WrapperResponse<PageDTO> queryIncomeClassifyInfo(@RequestParam Map<String, Object> paraMap,HttpServletRequest req, HttpServletResponse res) {
    if (MapUtils.get(paraMap, "startDate") == null || "".equals(MapUtils.get(paraMap, "startDate"))) {
      throw new AppException("开始时间不能为空");
    }
    SysUserDTO userDTO = getSession(req, res) ;
    paraMap.put("hospCode", userDTO.getHospCode());
    WrapperResponse<PageDTO> listWrapperResponse = patientCostLedgerService_consumer.queryIncomeClassifyInfo(paraMap);
    return listWrapperResponse;
  }

  /**
   * @param paraMap
   * @Method queryIncomeClassifyInfoList
   * @Desrciption 全院月收入分类统计分页数据
   * @Author caoliang
   * @Date 2021/6/11 10:05
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < java.util.Map >>
   *
  @GetMapping("/queryIncomeClassifyInfoList")
  public WrapperResponse<PageDTO> queryIncomeClassifyInfo(@RequestParam Map<String, Object> paraMap) {
    paraMap.put("hospCode", hospCode);

    WrapperResponse<PageDTO> listWrapperResponse = patientCostLedgerService_consumer.queryIncomeClassifyInfo(paraMap);
    return listWrapperResponse;
  }*/

  /**
   * @param paraMap
   * @Menthod queryOutptCostAndRegisterCost
   * @Desrciption 开方科室/开方医生收入统计
   * @Author xingyu.xie
   * @Date 2020/12/12 11:00
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map < java.lang.String, java.lang.Object>>
   **/
  @PostMapping("/queryOutptCostAndRegisterCost")
  public WrapperResponse<Map<String, Object>> queryOutptCostAndRegisterCost(@RequestBody Map<String, Object> paraMap,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    paraMap.put("hospCode", userDTO.getHospCode());
    String statement = MapUtils.get(paraMap, "statement");
    if (statement == null) {

      throw new AppException("请选择报表");

    }
    if (StringUtils.isEmpty(statement)) {
      paraMap.put("statement", "1");
    }
    return patientCostLedgerService_consumer.queryOutptCostAndRegisterCost(paraMap);
  }


  /**
   * @param paraMap
   * @Menthod queryOutptCostAndRegisterCostDaoChu
   * @Desrciption 开方科室/开方医生收入统计
   * @Author xingyu.xie
   * @Date 2020/12/12 11:00
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map < java.lang.String, java.lang.Object>>
   **/
  @PostMapping("/queryOutptCostAndRegisterCostDaoChu")
  public WrapperResponse<Map<String, Object>> queryOutptCostAndRegisterCostDaoChu(@RequestBody Map<String, Object> paraMap,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    paraMap.put("hospCode", userDTO.getHospCode());
    String statement = MapUtils.get(paraMap, "statement");
    if (statement == null) {

      throw new AppException("请选择报表");

    }
    if (StringUtils.isEmpty(statement)) {
      paraMap.put("statement", "1");
    }
    return patientCostLedgerService_consumer.queryOutptCostAndRegisterCostDaoChu(paraMap);
  }

    @GetMapping("/queryOutptCostAndRegisterCostSum")
    public WrapperResponse<Map<String, Object>> queryOutptCostAndRegisterCostSum(@RequestParam Map<String, Object> paraMap,HttpServletRequest req, HttpServletResponse res) {
      SysUserDTO userDTO = getSession(req, res) ;
      paraMap.put("hospCode", userDTO.getHospCode());
        String statement = MapUtils.get(paraMap, "statement");
        if (statement == null) {

            throw new AppException("请选择报表");

        }
        if (StringUtils.isEmpty(statement)) {
            paraMap.put("statement", "1");
        }
        return patientCostLedgerService_consumer.queryOutptCostAndRegisterCostSum(paraMap);
    }

  /**
   * @Menthod queryOutptCostAndRegisterCostList
   * @Desrciption  开方科室/开方医生收入统计分页数据
   * @param paraMap
   * @Author caoliang
   * @Date   2021/6/10 21:00
   * @Return java.util.List<java.lang.Map>
   **/
  @GetMapping("/queryOutptCostAndRegisterCostList")
  public WrapperResponse<PageDTO> queryOutptCostAndRegisterCostList(@RequestParam Map<String, Object> paraMap,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    paraMap.put("hospCode", userDTO.getHospCode());
    String statement = MapUtils.get(paraMap, "statement");
    if (statement == null) {

      throw new AppException("请选择报表");

    }
    if (StringUtils.isEmpty(statement)) {
      paraMap.put("statement", "1");
    }
    return patientCostLedgerService_consumer.queryOutptCostAndRegisterCostList(paraMap);
  }

  /**
   * @Method queryInvoiceStatistics
   * @Desrciption 发票使用统计
   * @params [map]
   * @Author chenjun
   * @Date 2020-12-14 09:41
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < java.util.Map>>
   **/
  @GetMapping("/queryInvoiceStatistics")
  public WrapperResponse<PageDTO> queryInvoiceStatisticsGet(@RequestParam Map paraMap,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    paraMap.put("hospCode", userDTO.getHospCode());
    return patientCostLedgerService_consumer.queryInvoiceStatistics(paraMap);
  }

  @PostMapping("/queryInvoiceStatistics")
  public WrapperResponse<PageDTO> queryInvoiceStatistics(@RequestBody Map paraMap,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    paraMap.put("hospCode", userDTO.getHospCode());
    return patientCostLedgerService_consumer.queryInvoiceStatistics(paraMap);
  }


  /**
   * @Method queryChargeDetail
   * @Desrciption 门诊/住院明细收费项目查询（按结算时间/按费用发生时间）
   * @params [paraMap]
   * @Author chenjun
   * @Date 2020-12-14 16:37
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map>
   **/
  @PostMapping("/queryChargeDetail")
  public WrapperResponse<Map> queryChargeDetail(@RequestBody Map paraMap,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    paraMap.put("hospCode", userDTO.getHospCode());
    return patientCostLedgerService_consumer.queryChargeDetail(paraMap);
  }

  /**
   * @Method queryChargeDetailList
   * @Desrciption 门诊/住院明细收费项目查询（按结算时间/按费用发生时间）分页数据
   @params [paraMap]
    * @Author caoliang
   * @Date   2021-6-10 20:50
   * @Return java.util.List<java.util.Map>
   **/
  @GetMapping("/queryChargeDetailList")
  public WrapperResponse<PageDTO> queryChargeDetailList(@RequestParam Map paraMap,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    paraMap.put("hospCode", userDTO.getHospCode());
    return patientCostLedgerService_consumer.queryChargeDetailList(paraMap);
  }

  /**
   * @param paraMap
   * @Method queryDayReportWithHospital
   * @Desrciption 全院日报表统计
   * @Author liuqi1
   * @Date 2020/12/16 15:00
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < java.util.Map < java.lang.String, java.lang.Object>>>
   **/
  @PostMapping("/queryDayReportWithHospital")
  public WrapperResponse<Map<String, Object>> queryDayReportWithHospital(@RequestBody Map<String, Object> paraMap,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    paraMap.put("hospCode", userDTO.getHospCode());

    WrapperResponse<Map<String, Object>> listWrapperResponse = patientCostLedgerService_consumer.queryDayReportWithHospital(paraMap);
    return listWrapperResponse;
  }

  /**
   * @param paraMap
   * @Method queryChargPatirntCostLedger
   * @Desrciption 病人收费统计
   * @Author pengbo
   * @Date 2021/04/13 15:00
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < java.util.Map < java.lang.String, java.lang.Object>>>
   **/
  @PostMapping("/queryChargPatirntCostLedger")
  public WrapperResponse<Map<String, Object>> queryChargPatirntCostLedger(@RequestBody Map<String, Object> paraMap,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    paraMap.put("hospCode", userDTO.getHospCode());

    WrapperResponse<Map<String, Object>> listWrapperResponse = patientCostLedgerService_consumer.queryChargPatirntCostLedger(paraMap);
    return listWrapperResponse;
  }

  /**
   * @Method queryItemName
   * @Desrciption 项目查询
   * @Author lizihuan
   * @Date 2021/06/07 10:05
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < java.util.Map < java.lang.String, java.lang.Object>>>
   **/
  @GetMapping("/queryIteName")
  public WrapperResponse<PageDTO> queryItemName(@RequestParam Map paraMap,HttpServletRequest req, HttpServletResponse res) {
    PageDO pageDO=new PageDO();
    InptCostDTO inptCostDTO=new InptCostDTO();
    String itemName = inptCostDTO.getItemName();
    SysUserDTO userDTO = getSession(req, res) ;
    paraMap.put("hospCode", userDTO.getHospCode());
    paraMap.put("itemName",itemName);
    paraMap.put("pageDO",pageDO);
    return patientCostLedgerService_consumer.queryItemName( paraMap);

  };
}
