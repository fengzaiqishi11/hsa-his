package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.dzpz.hainan.SeltSucCallbackDTO;
import cn.hsa.module.outpt.fees.dto.*;
import cn.hsa.module.outpt.fees.entity.OutptPayDO;
import cn.hsa.module.outpt.fees.service.OutptTmakePriceFormService;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 线上支付相关医保接口
 * @Author: 医保开发二部-湛康
 * @Date: 2022-04-25 11:19
 */
@RestController
@RequestMapping("/web/online/orderPay")
public class OnlinePayController  extends BaseController {

  @Resource
  private OutptTmakePriceFormService outptTmakePriceFormService_consumer;

  /**
   * 【6201】费用明细上传
   * @param dto
   * @param req
   * @param res
   * @Author 医保开发二部-湛康
   * @Date 2022-04-25 15:33
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   */
  @PostMapping("/uploadOnlineFeeDetail")
  public WrapperResponse<Boolean> uploadOnlineFeeDetail(@RequestBody OnlinePayFeeDTO dto,
                                                        HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO sysUserDTO = getSession(req, res);
    Map<String, Object> map = new HashMap<>();
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("onlinePayFeeDTO", dto);
    map.put("crteName",sysUserDTO.getName());
    map.put("crteId",sysUserDTO.getUsId());
    return outptTmakePriceFormService_consumer.uploadOnlineFeeDetail(map);
  }

  /**
   * 【6301】医保订单结算结果查询
   * @param param
   * @param req
   * @param res
   * @Author 医保开发二部-湛康
   * @Date 2022-05-09 14:47
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.dzpz.hainan.SeltSucCallbackDTO>
   */
  @PostMapping("/queryInsureSetlResult")
  public WrapperResponse<Map<String, Object>> queryInsureSetlResult(@RequestBody Map<String,Object> param,
                                                                   HttpServletRequest req, HttpServletResponse res) {
    //获取个人信息
    OutptVisitDTO outptVisitDTO = JSON.parseObject (JSON.toJSONString(param.get("outptVisit")),OutptVisitDTO.class) ;
    //获取结算信息
    OutptSettleDTO outptSettleDTO = JSON.parseObject (JSON.toJSONString(param.get("outptSettle")),OutptSettleDTO.class);
    //是否打印发票
    outptSettleDTO.setIsInvoice((Boolean) param.get("isInvoice"));
    //支付方式信息
    //获取订单查询入参
    //SetlResultQueryDTO setlResultQueryDTO = JSON.parseObject (JSON.toJSONString(param.get("setlResultQueryDTO")),SetlResultQueryDTO.class);
    //判断参数是否正确（费用信息、就诊id、结算id）
    if (outptSettleDTO == null || outptVisitDTO == null
        || outptVisitDTO.getOutptCostDTOList() == null || outptVisitDTO.getOutptCostDTOList().isEmpty()
        || StringUtils.isEmpty(outptVisitDTO.getId()) || StringUtils.isEmpty(outptSettleDTO.getId())){
      return WrapperResponse.fail("参数错误。",null);
    }

    SysUserDTO userDTO = getSession(req, res) ;
    //医院编码
    outptVisitDTO.setHospCode(userDTO.getHospCode());
    //医院名称
    outptVisitDTO.setHospName(userDTO.getHospName());
    //当前用户id
    outptVisitDTO.setCrteId(userDTO.getId());
    outptVisitDTO.setCrteName(userDTO.getName());
    // 操作员编码
    outptVisitDTO.setCode(userDTO.getCode());
    //当前登录用户操作科室ID
    outptVisitDTO.setDeptId(userDTO.getLoginBaseDeptDTO().getId() );
    //医院编码
    outptSettleDTO.setHospCode(userDTO.getHospCode());

    Map params = new HashMap();
    params.put("hospCode",userDTO.getHospCode());
    //(个人信息、费用信息)
    params.put("outptVisitDTO",outptVisitDTO);
    return outptTmakePriceFormService_consumer.queryInsureSetlResult(params);
  }

  /**
   * 6401-费用明细上传撤销
   * @param dto
   * @param req
   * @param res
   * @Author 医保开发二部-湛康
   * @Date 2022-05-10 11:46
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.dzpz.hainan.SeltSucCallbackDTO>
   */
  @PostMapping("/insureFeeRevoke")
  public WrapperResponse<Boolean> insureFeeRevoke(@RequestBody SetlResultQueryDTO dto,
                                                                   HttpServletRequest req,
                                                                   HttpServletResponse res) {
    SysUserDTO sysUserDTO = getSession(req, res);
    Map<String, Object> map = new HashMap<>();
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("setlResultQueryDTO", dto);
    return outptTmakePriceFormService_consumer.insureFeeRevoke(map);
  }

  /**
   * 6203-医保退费
   * @param dto
   * @param req
   * @param res
   * @Author 医保开发二部-湛康
   * @Date 2022-05-11 13:58
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   */
  @PostMapping("/insureRefund")
  public WrapperResponse<Boolean> insureRefund(@RequestBody SetlRefundQueryDTO dto,
                                                  HttpServletRequest req,
                                                  HttpServletResponse res) {
    SysUserDTO sysUserDTO = getSession(req, res);
    Map<String, Object> map = new HashMap<>();
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("setlRefundQueryDTO", dto);
    return outptTmakePriceFormService_consumer.insureRefund(map);
  }

  /**
   * 线上医保移动支付完成的结算订单，可通过此接口进行退款
   * @param dto
   * @param req
   * @param res
   * @Author 医保开发二部-湛康
   * @Date 2022-06-15 8:57
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
   */
  @PostMapping("/ampRefund")
  public WrapperResponse<Boolean> ampRefund(@RequestBody SetlRefundQueryDTO dto,
                                               HttpServletRequest req,
                                               HttpServletResponse res) {
    SysUserDTO sysUserDTO = getSession(req, res);
    Map<String, Object> map = new HashMap<>();
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("setlRefundQueryDTO", dto);
    return outptTmakePriceFormService_consumer.ampRefund(map);
  }
}
