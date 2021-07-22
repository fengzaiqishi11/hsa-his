package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.NoRepeatSubmit;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.outinInvoice.dto.OutinInvoiceDTO;
import cn.hsa.module.outpt.outinInvoice.entity.OutinInvoiceDO;
import cn.hsa.module.outpt.outinInvoice.entity.OutinInvoiceDetailDO;
import cn.hsa.module.outpt.outinInvoice.service.OutinInvoiceService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.ListUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt
 * @Class_name:: OutptInvoiceController
 * @Description: 发票管理控制层
 * @Author: liaojiguang
 * @Date: 2020-08-24 10:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/outpt/outinInvoice")
@Slf4j
public class OutptInvoiceController extends BaseController {

      /**
       *  发票管理dubbo消费者接口
       */
      @Resource
      private OutinInvoiceService outinInvoiceService_consumer;

    /**
     * @Menthod getInvoicePage()
     * @Desrciption   根据条件分页查询发票领用信息
     * @Param [outinInvoiceDTO] 发票管理传输DTO对象
     * @Author liaojiguang
     * @Date   2020/08/22 15:15
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @GetMapping("/getInvoicePage")
    public WrapperResponse<PageDTO> getInvoicePage(OutinInvoiceDTO outinInvoiceDTO,HttpServletRequest req, HttpServletResponse res) {
      SysUserDTO userDTO = getSession(req, res) ;
      outinInvoiceDTO.setHospCode(userDTO.getHospCode());
      Map map = new HashMap();
      map.put("hospCode",userDTO.getHospCode());
      map.put("outinInvoiceDTO", outinInvoiceDTO);
      return outinInvoiceService_consumer.getInvoicePage(map);
    }

  /**
   * @Menthod queryPatientInvoicePage()
   * @Desrciption   根据条件分页查询发票结算信息
   * @Param billType 发票类型
   * @Param
   * @Author liaojiguang
   * @Date   2020/08/26 10:44
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
   **/
  @GetMapping("/queryPatientInvoicePage")
  public WrapperResponse<PageDTO> queryPatientInvoicePage(OutinInvoiceDTO outinInvoiceDTO,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    outinInvoiceDTO.setHospCode(userDTO.getHospCode());
    outinInvoiceDTO.setSystemCode(userDTO.getSystemCode());
    Map map = new HashMap();
    map.put("outinInvoiceDTO",outinInvoiceDTO);
    map.put("hospCode",userDTO.getHospCode());
    return outinInvoiceService_consumer.queryPatientInvoicePage(map);
  }

  /**
   * @Menthod getOutinInvoiceById()
   * @Desrciption   根据主键id查询发票领用详细信息
   * @Param [outinInvoiceDTO] 发票管理传输DTO对象
   * @Author liaojiguang
   * @Date   2020/08/25 17:00
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<OutinInvoiceDO>
   **/
  @GetMapping("/getOutinInvoiceById")
  public WrapperResponse<OutinInvoiceDO> getOutinInvoiceById(OutinInvoiceDTO outinInvoiceDTO,HttpServletRequest req, HttpServletResponse res) {
    if (StringUtils.isEmpty(outinInvoiceDTO.getId())) {
      return WrapperResponse.error(WrapperResponse.FAIL,"参数错误!",null);
    }
    SysUserDTO userDTO = getSession(req, res) ;
    outinInvoiceDTO.setHospCode(userDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",userDTO.getHospCode());
    map.put("outinInvoiceDTO", outinInvoiceDTO);
    return outinInvoiceService_consumer.getOutinInvoiceById(map);
  }


  /**
   * @Menthod updateInvoiceReceive()
   * @Desrciption 发票领用
   * @Param [outinInvoiceDTO] 发票管理传输DTO对象
   * @Author liaojiguang
   * @Date   2020/08/24 16:32base
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
   **/
  @PutMapping("/updateInvoiceReceive")
  public WrapperResponse<Boolean> updateInvoiceReceive(@RequestBody OutinInvoiceDTO outinInvoiceDTO,HttpServletRequest req, HttpServletResponse res) {
    // 领用人id
    Boolean params_1 = StringUtils.isEmpty(outinInvoiceDTO.getReceiveId());

    // 票据类型代码:0、全院通用，1、门诊发票，2、挂号发票，3、门诊通用，4、住院发票
    Boolean params_2 = StringUtils.isEmpty(outinInvoiceDTO.getTypeCode());

    // 票据前缀
    Boolean params_3 = StringUtils.isEmpty(outinInvoiceDTO.getPrefix());

    // 领用起始号码
    Boolean params_4 = StringUtils.isEmpty(outinInvoiceDTO.getStartNo());

    // 领用终止号码
    Boolean params_5 = StringUtils.isEmpty(outinInvoiceDTO.getEndNo());

    // 发票使用人
    Boolean params_6 = StringUtils.isEmpty(outinInvoiceDTO.getUseId());

    if (params_1 || params_2 || params_3 || params_4 || params_5 || params_6) {
      return WrapperResponse.error(WrapperResponse.FAIL,"参数错误!",null);
    }

    SysUserDTO userDTO = getSession(req, res) ;
    // 封装系统参数
    outinInvoiceDTO.setHospCode(userDTO.getHospCode());
    outinInvoiceDTO.setCrteId(userDTO.getId());
    outinInvoiceDTO.setCrteName(userDTO.getName());


    Map map = new HashMap();
    map.put("hospCode",userDTO.getHospCode());
    map.put("outinInvoiceDTO", outinInvoiceDTO);
    return outinInvoiceService_consumer.updateInvoiceReceive(map);
  }

  /**
   * @Menthod updateInvoiceOutReceive()
   * @Desrciption 发票退领
   * @Param [outinInvoiceDTO] 发票管理传输DTO对象
   * @Author liaojiguang
   * @Date   2020/08/24 16:32base
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
   **/
  @PutMapping("/updateInvoiceOutReceive")
  public WrapperResponse<Boolean> updateInvoiceOutReceive(@RequestBody OutinInvoiceDTO outinInvoiceDTO,HttpServletRequest req, HttpServletResponse res) {
    // 发票主键
    Boolean params_1 = StringUtils.isEmpty(outinInvoiceDTO.getId());

    // 票据类型代码:0、全院通用，1、门诊发票，2、挂号发票，3、门诊通用，4、住院发票
    Boolean params_2 = StringUtils.isEmpty(outinInvoiceDTO.getTypeCode());

    // 票据前缀
    Boolean params_3 = StringUtils.isEmpty(outinInvoiceDTO.getPrefix());

    // 退领起始号码
    Boolean params_4 = StringUtils.isEmpty(outinInvoiceDTO.getInvalidStartNo());

    // 退领终止号码
    Boolean params_5 = StringUtils.isEmpty(outinInvoiceDTO.getInvalidEndNo());

    if (params_1 || params_2 || params_3 || params_4 || params_5) {
      return WrapperResponse.error(WrapperResponse.FAIL,"参数错误!",null);
    }
    SysUserDTO userDTO = getSession(req, res) ;
    outinInvoiceDTO.setHospCode(userDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",userDTO.getHospCode());
    map.put("createId",userDTO.getId());
    map.put("createName",userDTO.getName());
    map.put("outinInvoiceDTO", outinInvoiceDTO);
    return outinInvoiceService_consumer.updateInvoiceOutReceive(map);
  }

  /**
   * @Description: 编辑发票信息（使用人，发票状态）
   * @Param:
   * @Author: guanhongqiang
   * @Email: hongqiang.guan@powersi.com.cn
   * @Date 2021/3/31 15:49
   * @Return
   */
  @PostMapping("/updateOutinInvoice")
  public WrapperResponse<Boolean> updateOutinInvoice(@RequestBody OutinInvoiceDTO outinInvoiceDTO,HttpServletRequest req, HttpServletResponse res) {
    Boolean params_1 = StringUtils.isEmpty(outinInvoiceDTO.getStatusCode());
    Boolean params_2 = StringUtils.isEmpty(outinInvoiceDTO.getUseId());
    Boolean params_3 = StringUtils.isEmpty(outinInvoiceDTO.getUseName());

    if (params_1 || params_2 || params_3) {
      return WrapperResponse.error(WrapperResponse.FAIL,"参数错误!",null);
    }
    SysUserDTO userDTO = getSession(req, res) ;
    outinInvoiceDTO.setHospCode(userDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",userDTO.getHospCode());
    map.put("outinInvoiceDTO", outinInvoiceDTO);
    return outinInvoiceService_consumer.updateOutinInvoice(map);
  }

  /**
   * @Menthod updateInvoiceInvalid()
   * @Desrciption 发票作废
   * @Param [outinInvoiceDTO] 发票管理传输DTO对象
   * @Author liaojiguang
   * @Date   2020/08/24 16:32base
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
   **/
  @PutMapping("/updateInvoiceInvalid")
  public WrapperResponse<Boolean> updateInvoiceInvalid(@RequestBody OutinInvoiceDTO outinInvoiceDTO,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;

    // 发票主键
    Boolean params_1 = StringUtils.isEmpty(outinInvoiceDTO.getId());

    // 票据类型代码:0、全院通用，1、门诊发票，2、挂号发票，3、门诊通用，4、住院发票
    Boolean params_2 = StringUtils.isEmpty(outinInvoiceDTO.getTypeCode());

    // 票据前缀
    Boolean params_3 = StringUtils.isEmpty(outinInvoiceDTO.getPrefix());

    // 作废起始号码
    Boolean params_4 = StringUtils.isEmpty(outinInvoiceDTO.getStartNo());

    // 作废终止号码
    Boolean params_5 = StringUtils.isEmpty(outinInvoiceDTO.getEndNo());

    if (params_1 || params_2 || params_3 || params_4 || params_5) {
      return WrapperResponse.error(WrapperResponse.FAIL,"参数错误!",null);
    }

    outinInvoiceDTO.setHospCode(userDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",userDTO.getHospCode());
    map.put("outinInvoiceDTO", outinInvoiceDTO);
    return outinInvoiceService_consumer.updateInvoiceInvalid(map);
  }

  @GetMapping("/getInvoiceList")
  public WrapperResponse<List<OutinInvoiceDTO>> getInvoiceList(OutinInvoiceDTO outinInvoiceDTO,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    outinInvoiceDTO.setHospCode(userDTO.getHospCode());
    outinInvoiceDTO.setUseId(userDTO.getHospCode());
    List<String> typeCodeList = new ArrayList<>();
    typeCodeList.add(Constants.PJLX.TY);
    typeCodeList.add(Constants.PJLX.GH);
    typeCodeList.add(Constants.PJLX.MZTY);
    outinInvoiceDTO.setTypeCodeList(typeCodeList);
    Map map = new HashMap();
    map.put("hospCode",userDTO.getHospCode());
    map.put("outinInvoiceDTO", outinInvoiceDTO);
    return outinInvoiceService_consumer.getInvoiceList(map);
  }
  @PutMapping("/updateOutinInvoiceStatus")
  public WrapperResponse<Boolean> updateOutinInvoiceStatus(@RequestBody OutinInvoiceDTO outinInvoiceDTO,HttpServletRequest req, HttpServletResponse res) {
    if (StringUtils.isEmpty(outinInvoiceDTO.getId()) || StringUtils.isEmpty(outinInvoiceDTO.getCurrNo())) {
      throw new AppException("参数错误");
    }
    SysUserDTO userDTO = getSession(req, res) ;
    outinInvoiceDTO.setHospCode(userDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",userDTO.getHospCode());
    map.put("outinInvoiceDTO", outinInvoiceDTO);
    return outinInvoiceService_consumer.updateOutinInvoiceStatus(map);
  }
  @PutMapping("/insertOutinInvoiceDetail")
  public WrapperResponse<Boolean> insertOutinInvoiceDetail(@RequestBody OutinInvoiceDetailDO outinInvoiceDetailDO,HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO userDTO = getSession(req, res) ;
    outinInvoiceDetailDO.setHospCode(userDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",userDTO.getHospCode());
    map.put("outinInvoiceDetailDO", outinInvoiceDetailDO);
    return outinInvoiceService_consumer.insertOutinInvoiceDetail(map);
  }

  /**
   * @Menthod getInvoiceDetailPage()
   * @Desrciption   根据条件分页查询发票领用详细信息
   * @Param 发票ID
   * @Param
   * @Author liaojiguang
   * @Date   2020/09/02 10:04
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
   **/
  @GetMapping("/queryInvoiceDetailPage")
  public WrapperResponse<PageDTO> queryInvoiceDetailPage(OutinInvoiceDetailDO outinInvoiceDetailDO,HttpServletRequest req, HttpServletResponse res) {
   /* if (StringUtils.isEmpty(outinInvoiceDetailDO.getInvoiceId())) {
      throw new AppException("参数错误");
    }*/
    SysUserDTO userDTO = getSession(req, res) ;
    Map map = new HashMap();
    outinInvoiceDetailDO.setHospCode(userDTO.getHospCode());
    map.put("outinInvoiceDetailDO",outinInvoiceDetailDO);
    map.put("hospCode",userDTO.getHospCode());
    return outinInvoiceService_consumer.queryInvoiceDetailPage(map);
  }

  /**
   * @Menthod queryItemInfoByParams()
   * @Desrciption   发票补打
   * @Param outinInvoiceDTO
   * @Author liaojiguang
   * @Date   2020/09/04 10:04
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List<Map<String, Object>>>
   **/
  @GetMapping("/queryItemInfoByParams")
  public WrapperResponse<List<Map<String,Object>>> queryItemInfoByParams(OutinInvoiceDTO outinInvoiceDTO,HttpServletRequest req, HttpServletResponse res) {
    Map map = new HashMap();
    // invoiceType 是自定义的参数，在获取列表时会查询带出，用于区分门诊通用或者全院通用具体使用挂号/门诊/住院某一个
    if (StringUtils.isEmpty(outinInvoiceDTO.getInvoiceType())) {
      throw new AppException("参数错误");
    }
    SysUserDTO userDTO = getSession(req, res) ;
    outinInvoiceDTO.setHospCode(userDTO.getHospCode());
    map.put("outinInvoiceDTO",outinInvoiceDTO);
    map.put("hospCode",userDTO.getHospCode());
    return outinInvoiceService_consumer.queryItemInfoByParams(map);
  }

  /**
   * @Menthod queryItemInfoDetails()
   * @Desrciption   获取费用明细信息（基础版）
   * @Param outinInvoiceDTO
   * @Author liaojiguang
   * @Date   2020/09/04 10:04
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List<Map<String, Object>>>
   **/
  @GetMapping("/queryItemInfoDetails")
  public WrapperResponse<List<Map<String,Object>>> queryItemInfoDetails(OutinInvoiceDTO outinInvoiceDTO,HttpServletRequest req, HttpServletResponse res) {

    // invoiceType 是自定义的参数，在获取列表时会查询带出，用于区分门诊通用或者全院通用具体使用挂号/门诊/住院某一个
    if (StringUtils.isEmpty(outinInvoiceDTO.getInvoiceType())) {
      throw new AppException("参数错误");
    }

    Map map = new HashMap();
    SysUserDTO userDTO = getSession(req, res) ;
    outinInvoiceDTO.setHospCode(userDTO.getHospCode());
    map.put("outinInvoiceDTO",outinInvoiceDTO);
    map.put("hospCode",userDTO.getHospCode());
    return outinInvoiceService_consumer.queryItemInfoDetails(map);
  }

  /**
   * @Menthod queryChangeDetails()
   * @Desrciption 获取费用明细信息
   * @Param outinInvoiceDTO
   * @Author caoliang
   * @Date 2021/05/20 10:52
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<List < Map < String, Object>>>
   *
   * @return*/
    @GetMapping("/queryChangeDetails")
    public WrapperResponse<PageDTO> queryChangeDetails(OutinInvoiceDTO outinInvoiceDTO,HttpServletRequest req, HttpServletResponse res) {
        Map map = new HashMap();
        SysUserDTO userDTO = getSession(req, res) ;
        outinInvoiceDTO.setHospCode(userDTO.getHospCode());
        map.put("outinInvoiceDTO",outinInvoiceDTO);
        map.put("hospCode",userDTO.getHospCode());
        return outinInvoiceService_consumer.queryChangeDetails(map);
    }


  /**
   * @Menthod updateInvoicePrint()
   * @Desrciption   发票补打重打
   * @Param [map] 查询条件对象
   * @Author liaojiguang
   * @Date   2020/08/26 10:00
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
   **/
  @PutMapping("/updateInvoicePrint")
  public WrapperResponse<Boolean> updateInvoicePrint(@RequestBody OutinInvoiceDTO outinInvoiceDTO,HttpServletRequest req, HttpServletResponse res) {
    if (StringUtils.isEmpty(outinInvoiceDTO.getPrintType()) || StringUtils.isEmpty(outinInvoiceDTO.getInvoiceType())) {
      throw new AppException("参数错误");
    }
    SysUserDTO userDTO = getSession(req, res) ;
    outinInvoiceDTO.setId(outinInvoiceDTO.getInvoiceId());
    outinInvoiceDTO.setHospCode(userDTO.getHospCode());
    outinInvoiceDTO.setUseId(userDTO.getId());
    outinInvoiceDTO.setUseName(userDTO.getName());
    Map map = new HashMap();
    map.put("hospCode",userDTO.getHospCode());
    map.put("outinInvoiceDTO", outinInvoiceDTO);
    return outinInvoiceService_consumer.updateInvoicePrint(map);
  }

  /**
   * @Menthod updatePrintChecklist()
   * @Desrciption  打印清单
   * @Param [outinInvoiceDTO]
   * @Author liaojiguang
   * @Date   2020/08/26 10:00
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
   **/
  @PutMapping("/updatePrintChecklist")
  public WrapperResponse<Boolean> updatePrintChecklist(@RequestBody OutinInvoiceDTO outinInvoiceDTO,HttpServletRequest req, HttpServletResponse res) {
    if (StringUtils.isEmpty(outinInvoiceDTO.getTypeCode()) || StringUtils.isEmpty(outinInvoiceDTO.getSettleId())) {
      throw new AppException("参数错误");
    }
    SysUserDTO userDTO = getSession(req, res) ;
    outinInvoiceDTO.setHospCode(userDTO.getHospCode());
    Map map = new HashMap();
    map.put("hospCode",userDTO.getHospCode());
    map.put("outinInvoiceDTO", outinInvoiceDTO);
    return outinInvoiceService_consumer.updatePrintChecklist(map);
  }

  /**
   * @Menthod queryInactiveOutinInvoices()
   * @Desrciption  获取发票
   * @Param [outinInvoiceDTO]
   * @Author liaojiguang
   * @Date   2020/08/26 10:00
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
   **/
  @PutMapping("/queryInactiveOutinInvoices")
  public WrapperResponse<List<OutinInvoiceDTO>> queryInactiveOutinInvoices(@RequestBody OutinInvoiceDTO outinInvoiceDTO,HttpServletRequest req, HttpServletResponse res) {
    if (ListUtils.isEmpty(outinInvoiceDTO.getTypeCodeList())) {
      throw new AppException("参数错误");
    }
    SysUserDTO userDTO = getSession(req, res) ;
    outinInvoiceDTO.setHospCode(userDTO.getHospCode());
    outinInvoiceDTO.setUseId(userDTO.getId());
    Map map = new HashMap();
    map.put("hospCode",userDTO.getHospCode());
    map.put("outinInvoiceDTO", outinInvoiceDTO);
    return outinInvoiceService_consumer.queryInactiveOutinInvoices(map);
  }

  /**
   * @Description: 查询费用清单，例如：材料费：200.00 西药费： 890.00
   * @Param:
   * @Author: guanhongqiang
   * @Email: hongqiang.guan@powersi.com.cn
   * @Date 2021/1/15 10:26
   * @Return
   */
  @GetMapping("/getCostInventory")
  public WrapperResponse<OutptCostDTO> getCostInventory(String settleNo, HttpServletRequest req, HttpServletResponse res) {
    if(StringUtils.isEmpty(settleNo)){
      throw new AppException("后台未获取到结算单号，请联系管理员");
    }
    Map<String, Object> map = new HashMap<>();
    SysUserDTO userDTO = getSession(req, res) ;
    map.put("hospCode", userDTO.getHospCode());
    map.put("settleNo", settleNo);
    return outinInvoiceService_consumer.getCostInventory(map);
  }

  /**
   * @Description: 查询门诊处方费用明细清单
   * @Param: 
   * @Author: guanhongqiang
   * @Email: hongqiang.guan@powersi.com.cn
   * @Date 2021/3/23 10:03
   * @Return 
   */
  @GetMapping("/queryCFCostDetails")
  public WrapperResponse<List<Map<String,Object>>> queryOutptCFCostDetails(OutinInvoiceDTO outinInvoiceDTO,HttpServletRequest req, HttpServletResponse res) {

    // invoiceType 是自定义的参数，在获取列表时会查询带出，用于区分门诊通用或者全院通用具体使用挂号/门诊/住院某一个
    if (StringUtils.isEmpty(outinInvoiceDTO.getInvoiceType())) {
      throw new AppException("参数错误");
    }
    SysUserDTO userDTO = getSession(req, res) ;
    Map<String, Object> map = new HashMap();
    outinInvoiceDTO.setHospCode(userDTO.getHospCode());
    map.put("outinInvoiceDTO",outinInvoiceDTO);
    map.put("hospCode",userDTO.getHospCode());
    return outinInvoiceService_consumer.queryOutptCFCostDetails(map);
  }

}
