package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDO;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.interf.statement.service.PatientCostLedgerService;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.statement.dto.IncomeDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.phar.pharoutdistribute.dto.PharOutDistributeDTO;
import cn.hsa.module.stro.stock.dto.StroStockDTO;
import cn.hsa.module.stro.stock.service.StroStockService;
import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
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

  @Resource
  private StroStockService stroStockService;


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
  * @Menthod queryStockTime
  * @Desrciption 查询月底库存
  *
  * @Param
  * [stroStockDTO, req, res]
  *
  * @Author jiahong.yang
  * @Date   2021/12/14 15:50
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
  **/
  @GetMapping("/queryStockTime")
  public WrapperResponse<PageDTO> queryStockTime(StroStockDTO stroStockDTO, HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO userDTO = getSession(req, res) ;
    stroStockDTO.setHospCode(userDTO.getHospCode());
    map.put("hospCode", userDTO.getHospCode());
    map.put("stroStockDTO", stroStockDTO);
    return patientCostLedgerService_consumer.queryStockTime(map);
  }

  /**
  * @Menthod queryStockTime
  * @Desrciption 自动生成上月月底库存
  *
  * @Param
  * [stroStockDTO, req, res]
  *
  * @Author jiahong.yang
  * @Date   2021/12/16 16:23
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
  **/
  @PostMapping("/insertStockByTime")
  public WrapperResponse<Boolean> insertStockByTime(@RequestBody StroStockDTO stroStockDTO, HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO userDTO = getSession(req, res) ;
    stroStockDTO.setHospCode(userDTO.getHospCode());
    stroStockDTO.setCrteTime(DateUtils.getNow());
    map.put("hospCode", userDTO.getHospCode());
    map.put("stroStockDTO", stroStockDTO);
    return stroStockService.insertStockByTime(map);
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
  @PostMapping("/queryOutptDeptIncomeTableHead")
  public WrapperResponse<Map> queryOutptDeptIncomeTableHead(@RequestBody InptVisitDTO inptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO userDTO = getSession(req, res) ;
    inptVisitDTO.setHospCode(userDTO.getHospCode());
    map.put("hospCode", userDTO.getHospCode());
    map.put("inptVisitDTO", inptVisitDTO);
    return patientCostLedgerService_consumer.queryOutptDeptIncomeTableHead(map);
  }
  /**
   * @Method queryOutptDeptIncome
   * @Desrciption 门诊科室/医生收入统计
   * @params [inptVisitDTO]
   * @Author chenjun
   * @Date 2020-11-12 10:14
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < cn.hsa.module.inpt.doctor.dto.InptVisitDTO>>
   **/
  @GetMapping("/queryOutptDeptIncome")
  public WrapperResponse<PageDTO> queryOutptDeptIncome(InptVisitDTO inptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
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
   * @Method queryOutMedicationGet
   * @Desrciption 门诊科室用药统计
   * @Param [PharOutDistributeDTO, req, res]
   * @Author zhangguorui
   * @Date   2021/7/23 15:44
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
   */
  @GetMapping("/queryOutMedicationGet")
  public WrapperResponse<PageDTO> queryOutMedicationGet(PharOutDistributeDTO pharOutDistributeDTO,
                                                        HttpServletRequest req, HttpServletResponse res){
    Map map = new HashMap();
    SysUserDTO userDTO = getSession(req,res);
    pharOutDistributeDTO.setHospCode(userDTO.getHospCode());
    map.put("hospCode",userDTO.getHospCode());
    map.put("pharOutDistributeDTO",pharOutDistributeDTO);
    return patientCostLedgerService_consumer.queryOutMedicationGet(map);
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
   * @param
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
  public WrapperResponse<PageDTO> queryIncomeClassifyInfo(IncomeDTO incomeDTO, HttpServletRequest req, HttpServletResponse res) {
    Date startDate = incomeDTO.getStartDate();
    Date endDate = incomeDTO.getEndDate();
    if (startDate == null || endDate == null) {
      return WrapperResponse.warning(200,"开始时间与结束时间不能为空",null);
    }
    SysUserDTO userDTO = getSession(req, res) ;
    Map paraMap = new HashMap();
    paraMap.put("hospCode", userDTO.getHospCode());
    incomeDTO.setHospCode(userDTO.getHospCode());
    paraMap.put("incomeDTO",incomeDTO);
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
    Boolean statusCode = MapUtils.get(paraMap,"statusCode");
    if(Boolean.TRUE.equals(statusCode)){
      paraMap.put("statusCode", Constants.ZTBZ.ZC);
    }else{
      paraMap.remove("statusCode");
    }
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

    Boolean statusCode = Boolean.valueOf(MapUtils.get(paraMap,"statusCode"));
    if(Boolean.TRUE.equals(statusCode)){
      paraMap.put("statusCode", Constants.ZTBZ.ZC);
    }else{
      paraMap.remove("statusCode");
    }
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

  @GetMapping("/queryInPatientDaily")
  public WrapperResponse<PageDTO> queryInPatientDaily(InptVisitDTO inptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO userDTO = getSession(req, res);
    map.put("hospCode", userDTO.getHospCode());
    inptVisitDTO.setHospCode(userDTO.getHospCode());
    map.put("inptVisitDTO", inptVisitDTO);
    return patientCostLedgerService_consumer.queryInPatientDaily(map);
  }

  @GetMapping("/queryMedicalCostMz")
  public WrapperResponse<PageDTO> queryMedicalCostMz(OutptVisitDTO outptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO userDTO = getSession(req, res);
    map.put("hospCode", userDTO.getHospCode());
    outptVisitDTO.setHospCode(userDTO.getHospCode());
    map.put("outptVisitDTO", outptVisitDTO);
    return patientCostLedgerService_consumer.queryMedicalCostMz(map);
  }

  /**
   * @Menthod 门诊医疗汇总报表表头
   * @Desrciption
   * @Param [inptVisitDTO]
   * @Author liuliyun
   * @Date 2021/08/24
   * @Return Map
   **/
  @PostMapping("/queryMedicalCostTitle")
  public WrapperResponse<Map> queryMedicalCostTitle(@RequestBody OutptVisitDTO outptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO userDTO = getSession(req, res) ;
    outptVisitDTO.setHospCode(userDTO.getHospCode());
    map.put("hospCode", userDTO.getHospCode());
    map.put("outptVisitDTO", outptVisitDTO);
    return patientCostLedgerService_consumer.queryMedicalCostTitle(map);
  }

  /**
   * @Menthod queryMzPatientFinanceCostList
   * @Desrciption 查询门诊财务分类明细
   * @Param outptVisitDTO
   * @Author liuliyun
   * @Date   2021/10/22 16:29
   * @Return WrapperResponse<PageDTO>
   **/
  @GetMapping("/queryMzPatientFinanceCostList")
  public WrapperResponse<PageDTO> queryMzPatientFinanceCostList(OutptVisitDTO outptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO userDTO = getSession(req, res);
    map.put("hospCode", userDTO.getHospCode());
    outptVisitDTO.setHospCode(userDTO.getHospCode());
    map.put("outptVisitDTO", outptVisitDTO);
    return patientCostLedgerService_consumer.queryMzPatientFinanceCostList(map);
  }

  /**
   * @Menthod getMedicalFinanceMzTitle
   * @Desrciption  查询门诊财务分类标题
   * @Param outptVisitDTO
   * @Author liuliyun
   * @Date   2021/10/22 16:28
   * @Return WrapperResponse<Map>
   **/
  @PostMapping("/getMzMedicalFinanceTitle")
  public WrapperResponse<Map> getMzMedicalFinanceTitle(@RequestBody OutptVisitDTO outptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO userDTO = getSession(req, res) ;
    outptVisitDTO.setHospCode(userDTO.getHospCode());
    map.put("hospCode", userDTO.getHospCode());
    map.put("outptVisitDTO", outptVisitDTO);
    return patientCostLedgerService_consumer.getMzMedicalFinanceTitle(map);
  }

  /**
   * @Menthod getInptFinanceList
   * @Desrciption 查询住院财务分类明细
   * @Param outptVisitDTO
   * @Author liuliyun
   * @Date   2021/10/25 15:29
   * @Return WrapperResponse<PageDTO>
   **/
  @GetMapping("/getInptFinanceList")
  public WrapperResponse<PageDTO> getInptFinanceList(InptVisitDTO inptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO userDTO = getSession(req, res);
    map.put("hospCode", userDTO.getHospCode());
    inptVisitDTO.setHospCode(userDTO.getHospCode());
    map.put("inptVisitDTO", inptVisitDTO);
    return patientCostLedgerService_consumer.getInptFinanceList(map);
  }

  /**
   * @Menthod getMedicalFinanceMzTitle
   * @Desrciption  查询住院财务分类标题
   * @Param outptVisitDTO
   * @Author liuliyun
   * @Date   2021/10/25 15:34
   * @Return WrapperResponse<Map>
   **/
  @PostMapping("/getInptFinanceTitle")
  public WrapperResponse<Map> getInptFinanceTitle(@RequestBody InptVisitDTO inptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO userDTO = getSession(req, res) ;
    inptVisitDTO.setHospCode(userDTO.getHospCode());
    map.put("hospCode", userDTO.getHospCode());
    map.put("inptVisitDTO", inptVisitDTO);
    return patientCostLedgerService_consumer.getInptFinanceTitle(map);
  }

  /**
   * @Menthod queryHospitalCardList
   * @Desrciption 统计入院证
   * @Param inptVisitDTO
   * @Author liuliyun
   * @Date   2021/12/06 10:49
   * @Return WrapperResponse<PageDTO>
   **/
  @GetMapping("/queryHospitalCardList")
  public WrapperResponse<PageDTO> queryHospitalCardList(OutptVisitDTO outptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO userDTO = getSession(req, res);
    map.put("hospCode", userDTO.getHospCode());
    outptVisitDTO.setHospCode(userDTO.getHospCode());
    map.put("outptVisitDTO", outptVisitDTO);
    return patientCostLedgerService_consumer.queryHospitalCardList(map);
  }

  /**
   * @param paraMap
   * @Method queryOutptorInHosptialItemUseInfo
   * @Desrciption 门诊业务使用量统计按科室过滤
   * @Author liuliyun
   * @Date 2021/12/07 11:46
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List < java.util.Map < java.lang.String, java.lang.Object>>>
   **/
  @GetMapping("/queryOutptorInHosptialItemUseInfo")
  public WrapperResponse<PageDTO> queryOutptorInHosptialItemUseInfo(@RequestParam Map<String, Object> paraMap,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    paraMap.put("hospCode", userDTO.getHospCode());
    paraMap.put("deptId", userDTO.getLoginBaseDeptDTO().getId());
    WrapperResponse<PageDTO> listWrapperResponse = patientCostLedgerService_consumer.queryOutptorInHosptialItemUseInfo(paraMap);
    return listWrapperResponse;
  }

  /**
   * @Menthod getInptOperFinanceList
   * @Desrciption 查询住院手术财务分类明细
   * @Param inptVisitDTO
   * @Author liuliyun
   * @Date   2021/12/14 10:10
   * @Return cn.hsa.base.PageDTO
   **/
  @GetMapping("/getInptOperFinanceList")
  public WrapperResponse<PageDTO> getInptOperFinanceList(InptVisitDTO inptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO userDTO = getSession(req, res);
    map.put("hospCode", userDTO.getHospCode());
    inptVisitDTO.setHospCode(userDTO.getHospCode());
    inptVisitDTO.setDeptId(userDTO.getLoginBaseDeptDTO().getId());
    map.put("inptVisitDTO", inptVisitDTO);
    return patientCostLedgerService_consumer.getInptOperFinanceList(map);
  }

  /**
   * @Menthod getInptOperFinanceTitle
   * @Desrciption  查询住院手术财务分类标题
   * @Param inptVisitDTO
   * @Author liuliyun
   * @Date   2021/12/14 10:14
   * @Return Map
   **/
  @PostMapping("/getInptOperFinanceTitle")
  public WrapperResponse<Map> getInptOperFinanceTitle(@RequestBody InptVisitDTO inptVisitDTO,HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO userDTO = getSession(req, res) ;
    inptVisitDTO.setHospCode(userDTO.getHospCode());
    inptVisitDTO.setDeptId(userDTO.getLoginBaseDeptDTO().getId());
    map.put("hospCode", userDTO.getHospCode());
    map.put("inptVisitDTO", inptVisitDTO);
    return patientCostLedgerService_consumer.getInptOperFinanceTitle(map);
  }

  /**
   * @Description: 查询门诊财务月报表，按选定的时间区间，逐日统计药品或项目的自费收入，医保收入
   * @Param:
   * @Author: guanhongqiang
   * @Email: hongqiang.guan@powersi.com.cn
   * @Date 2021/12/20 14:22
   * @Return
   */
  @RequestMapping("/queryMzMonthlyReport")
  public WrapperResponse<PageDTO> queryMzMonthlyReport(@RequestParam Map<String, Object> paraMap,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    paraMap.put("hospCode", userDTO.getHospCode());
    return patientCostLedgerService_consumer.queryMzMonthlyReport(paraMap);
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
  @GetMapping("/queryoutptMonthDaily")
  public WrapperResponse<Map<String, List<OutptCostDTO>>> queryoutptMonthDaily(OutptCostDTO outptCostDTO, HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    SysUserDTO userDTO = getSession(req, res);
    map.put("hospCode", userDTO.getHospCode());
    outptCostDTO.setHospCode(userDTO.getHospCode());
    map.put("outptCostDTO", outptCostDTO);
    return patientCostLedgerService_consumer.queryoutptMonthDaily(map);
  }

  /**
   * @Menthod queryOutptIncomePage
   * @Desrciption  开方科室/开方医生收入统计分页数据
   * @param paraMap
   * @Author caoliang
   * @Date   2021/6/10 21:00
   * @Return java.util.List<java.lang.Map>
   **/
  @GetMapping("/queryOutptIncomePage")
  public WrapperResponse<PageDTO> queryOutptIncomePage(@RequestParam Map<String, Object> paraMap,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    paraMap.put("hospCode", userDTO.getHospCode());
    String statement = MapUtils.get(paraMap, "statement");
    if (StringUtils.isEmpty(statement)) {
      paraMap.put("statement", "1");
    }
    return patientCostLedgerService_consumer.queryOutptIncomePage(paraMap);
  }

  /**
   * @param paraMap
   * @Menthod queryOutptCostAndRegisterCost
   * @Desrciption 开方科室/开方医生收入统计
   * @Author xingyu.xie
   * @Date 2020/12/12 11:00
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map < java.lang.String, java.lang.Object>>
   **/
  @PostMapping("/queryOutptIncomeList")
  public WrapperResponse<Map<String, Object>> queryOutptIncomeList(@RequestBody Map<String, Object> paraMap,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    paraMap.put("hospCode", userDTO.getHospCode());
    String statement = MapUtils.get(paraMap, "statement");
    if (StringUtils.isEmpty(statement)) {
      paraMap.put("statement", "1");
    }
    return patientCostLedgerService_consumer.queryOutptIncomeList(paraMap);
  }


  /**
   * @Menthod queryHosptialInComeList
   * @Desrciption  住院业务收入统计报表
   * @param paraMap
   * @Author liuliyun
   * @Date   2022/2/10 16:12
   * @Return java.util.List<java.lang.Map>
   **/
  @GetMapping("/queryHosptialInComeList")
  public WrapperResponse<PageDTO> queryHosptialInComeList(@RequestParam Map<String, Object> paraMap,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    paraMap.put("hospCode", userDTO.getHospCode());
    return patientCostLedgerService_consumer.queryHosptialInComeList(paraMap);
  }

  /**
   * @param paraMap
   * @Menthod queryHosptialInComeListTitle
   * @Desrciption 住院业务收入统计表头
   * @Author  liuliyun
   * @Date 2022/2/11 11:00
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map < java.lang.String, java.lang.Object>>
   **/
  @PostMapping("/queryHosptialInComeListTitle")
  public WrapperResponse<Map<String, Object>> queryHosptialInComeListTitle(@RequestBody Map<String, Object> paraMap,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    paraMap.put("hospCode", userDTO.getHospCode());
    return patientCostLedgerService_consumer.queryHosptialInComeListTitle(paraMap);
  }
}
