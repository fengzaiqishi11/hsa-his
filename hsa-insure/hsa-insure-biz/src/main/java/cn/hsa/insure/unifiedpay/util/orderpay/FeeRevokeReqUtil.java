package cn.hsa.insure.unifiedpay.util.orderpay;

import cn.hsa.insure.unifiedpay.util.InsureCommonUtil;
import cn.hsa.insure.util.BaseReqUtil;
import cn.hsa.insure.util.Constant;
import cn.hsa.module.insure.module.dto.InsureInterfaceParamDTO;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description: 6401
 * @Author: 医保开发二部-湛康
 * @Date: 2022-05-07 10:23
 */
@Service("newInsure" + Constant.UnifiedPay.REGISTER.UP_6401)
public class FeeRevokeReqUtil<T> extends InsureCommonUtil implements BaseReqUtil<T> {

  @Override
  public InsureInterfaceParamDTO initRequest(T param) {
    Map map = (Map) param;
    checkRequest(map);
    return getInsurCommonParam(map);
  }

  @Override
  public boolean checkRequest(Map param) {
    return true;
  }
}
