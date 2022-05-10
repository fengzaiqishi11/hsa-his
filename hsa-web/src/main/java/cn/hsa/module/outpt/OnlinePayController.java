package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.dzpz.hainan.SeltSucCallbackDTO;
import cn.hsa.module.outpt.fees.dto.OnlinePayFeeDTO;
import cn.hsa.module.outpt.fees.dto.SetlResultQueryDTO;
import cn.hsa.module.outpt.fees.service.OutptTmakePriceFormService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
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
    return outptTmakePriceFormService_consumer.uploadOnlineFeeDetail(map);
  }

  /**
   * 【6301】医保订单结算结果查询
   * @param dto
   * @param req
   * @param res
   * @Author 医保开发二部-湛康
   * @Date 2022-05-09 14:47
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.dzpz.hainan.SeltSucCallbackDTO>
   */
  @PostMapping("/queryInsureSetlResult")
  public WrapperResponse<SeltSucCallbackDTO> queryInsureSetlResult(@RequestBody SetlResultQueryDTO dto,
                                                                   HttpServletRequest req, HttpServletResponse res) {
    SysUserDTO sysUserDTO = getSession(req, res);
    Map<String, Object> map = new HashMap<>();
    map.put("hospCode", sysUserDTO.getHospCode());
    map.put("setlResultQueryDTO", dto);
    return outptTmakePriceFormService_consumer.queryInsureSetlResult(map);
  }
}
