package cn.hsa.insure.unifiedpay.service.impl;

import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.outpt.bo.InsureUnifiedOutptBO;
import cn.hsa.module.insure.outpt.bo.InsureUnifiedPayOutptBO;
import cn.hsa.module.insure.outpt.service.InsureUnifiedPayOutptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.service.impl
 * @Class_name: InsureUnifiedPayOutptServiceImpl
 * @Describe: 统一支付平台
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/2/10 8:41
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@HsafRestPath("/service/insure/insureUnifiedPayOutpt")
@Service("insureUnifiedPayOutptService_provider")
public class InsureUnifiedPayOutptServiceImpl implements InsureUnifiedPayOutptService {

	@Resource
	private InsureUnifiedPayOutptBO insureUnifiedPayOutptBO;

	@Resource
	private InsureUnifiedOutptBO insureUnifiedOutptBO;
	/**
	 * @Description: 门诊患者信息上传
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/10 9:04
	 * @Return
	 */
	@Override
	public void UP_2203(Map<String, Object> unifiedPayMap) {
		insureUnifiedPayOutptBO.UP_2203(unifiedPayMap);
	}

	/**
	 * @Description: 门诊预结算
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/15 11:59
	 * @Return
	 */
	@Override
	public Map<String, Object> UP_2206(Map<String, Object> unifiedPayMap) {
		return insureUnifiedPayOutptBO.UP_2206(unifiedPayMap);
	}

	/**
	 * @Description: 门诊结算
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/22 10:36
	 * @Return
	 */
	@Override
	public Map<String,Object> UP_2207(Map<String, Object> unifiedPayMap) {
		return insureUnifiedPayOutptBO.UP_2207(unifiedPayMap);
	}

	/**
	 * @Menthod: UP_2201
	 * @Desrciption: 统一支付平台-门诊挂号
	 * @Param: his门诊挂号实体-outptRegisterDTO, 节点标识-flag
	 * @Author: luoyong
	 * @Email: luoyong@powersi.com.cn
	 * @Date: 2021-02-10 09:35
	 * @Return:
	 **/
	@Override
	public WrapperResponse<InsureIndividualVisitDTO> UP_2201(Map<String,Object>map) {
		return WrapperResponse.success(insureUnifiedPayOutptBO.UP_2201(map));
	}

	/**
	 * @Menthod: UP_2202
	 * @Desrciption: 统一支付平台-门诊挂号撤销
	 * @Param: his门诊挂号实体-outptRegisterDTO, 节点标识-flag
	 * @Author: luoyong
	 * @Email: luoyong@powersi.com.cn
	 * @Date: 2021-02-10 11:00
	 * @Return:
	 **/
	@Override
	public WrapperResponse<Boolean>  UP_2202(Map<String,Object> map) {
		return WrapperResponse.success(insureUnifiedPayOutptBO.UP_2202(map));
	}

	/**
	 * @Menthod: UP_2208
	 * @Desrciption: 统一支付平台-门诊结算撤销
	 * @Param: 节点标识-flag
	 * @Author: luoyong
	 * @Email: luoyong@powersi.com.cn
	 * @Date: 2021-02-10 11:00
	 * @Return:
	 **/
	@Override
	public WrapperResponse< Map<String,Object>> UP_2208(Map<String,Object> map) {
		return WrapperResponse.success(insureUnifiedPayOutptBO.UP_2208(map));
	}

	/**
	 * @param map
	 * @Method updateCancelFeeSubmit
	 * @Desrciption 门诊医保病人取消费用上传
	 * @Param
	 * @Author fuhui
	 * @Date 2021/3/3 16:44
	 * @Return
	 */
	@Override
	public WrapperResponse<Boolean> UP_2205(Map<String, Object> map) {
		return WrapperResponse.success(insureUnifiedPayOutptBO.UP_2205(map));
	}

	/**
	 * @param map
	 * @Method updateFeeSubmit()
	 * @Desrciption 门诊医保病人费用传输
	 * @Param
	 * @Author fuhui
	 * @Date 2021/3/3 16:38
	 * @Return
	 */
	@Override
	public WrapperResponse<Boolean> UP_2204(Map<String, Object> map) {
		return WrapperResponse.success(insureUnifiedPayOutptBO.UP_2204(map));
	}

	/**
	 * @param params
	 * @Method UP_5301
	 * @Desrciption 查询门特病人信息
	 * @Param UP_5301
	 * @Author fuhui
	 * @Date 2021/3/25 19:28
	 * @Return
	 */
	@Override
	public WrapperResponse<Map<String, Object>> UP_5301(Map<String, Object> params) {
		return WrapperResponse.success(insureUnifiedPayOutptBO.UP_5301(params));
	}

	/**
	 * @Method UP_5302
	 * @Desrciption 医保统一支付平台：人员定点备案查询
	 * @Param map
	 *
	 * @Author fuhui
	 * @Date   2021/4/2 9:12
	 * @Return
	 **/
	@Override
	public WrapperResponse<Map<String, Object>> UP_5302(Map<String, Object> map) {
		return WrapperResponse.success(insureUnifiedPayOutptBO.UP_5302(map));
	}

	/**
	 * @Menthod: UP_4301
	 * @Desrciption: 医保统一支付平台：门急诊诊疗记录，上传单次病人就诊信息
	 * @Param:
	 * @Author: luoyong
	 * @Email: luoyong@powersi.com.cn
	 * @Date: 2021-08-17 19:45
	 * @Return:
	 **/
	@Override
	public WrapperResponse<Map<String, Object>> UP_4301(Map<String, Object> map) {
//		return WrapperResponse.success(insureUnifiedPayOutptBO.UP_4301(map));
		return WrapperResponse.success(insureUnifiedOutptBO.UP_4301(map));
	}

	/**
	 * @Menthod: UP_4302
	 * @Desrciption: 医保统一支付平台：急诊留观手术及抢救信息
	 * @Param:
	 * @Author: luoyong
	 * @Email: luoyong@powersi.com.cn
	 * @Date: 2021-08-17 19:45
	 * @Return:
	 **/
	@Override
	public WrapperResponse<Map<String, Object>> UP4302(Map<String, Object> map) {
//		return WrapperResponse.success(insureUnifiedPayOutptBO.UP4302(map));
		return WrapperResponse.success(insureUnifiedOutptBO.UP_4302(map));
	}

	/**
	 * 医保统一支付平台: 线上费用明细上传
	 * @param map
	 * @Author 医保开发二部-湛康
	 * @Date 2022-04-26 9:35
	 * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
	 */
  @Override
  public WrapperResponse<Map<String, Object>> UP6201(Map<String, Object> map) {
    return WrapperResponse.success(insureUnifiedOutptBO.UP_6201(map));
  }

  /**
   * 医保订单结算结果查询
   * @param map
   * @Author 医保开发二部-湛康
   * @Date 2022-05-09 16:39
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
   */
  @Override
  public WrapperResponse<Map<String, Object>> UP6301(Map<String, Object> map) {
    return WrapperResponse.success(insureUnifiedOutptBO.UP_6301(map));
  }

  /**
   * 费用明细上传撤销
   * @param map
   * @Author 医保开发二部-湛康
   * @Date 2022-05-10 14:08
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
   */
  @Override
  public WrapperResponse<Map<String, Object>> UP6401(Map<String, Object> map) {
    return WrapperResponse.success(insureUnifiedOutptBO.UP_6401(map));
  }

  /**
   *
   * @param map
   * @Author 医保开发二部-湛康
   * @Date 2022-05-17 10:00
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
   */
  @Override
  public WrapperResponse<Map<String, Object>> UP6203(Map<String, Object> map) {
    return WrapperResponse.success(insureUnifiedOutptBO.UP_6203(map));
  }

  /**
   * 线上医保移动支付完成的结算订单，可通过此接口进行退款
   * @param map
   * @Author 医保开发二部-湛康
   * @Date 2022-06-15 9:33
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
   */
  @Override
  public WrapperResponse<Map<String, Object>> AmpRefund(Map<String, Object> map) {
    return WrapperResponse.success(insureUnifiedOutptBO.AmpRefund(map));
  }

	/**
	 * @param map
	 * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map < java.lang.String, java.lang.Object>>
	 * @method refundInquiry
	 * @author wang'qiao
	 * @date 2022/6/20 14:55
	 * @description 查询退款结果（AMP_HOS_003） 调用AMP_HOS_002平台退款申请接口后，根据此状态来查询对应的退款具体结果
	 **/
	@Override
	public WrapperResponse<Map<String, Object>> refundInquiry(Map<String, Object> map) {
		return WrapperResponse.success(insureUnifiedOutptBO.refundInquiry(map));
	}

	/**
   * @param map
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map < java.lang.String, java.lang.Object>>
   * @method AMP_HOS_001
   * @author wang'qiao
   * @date 2022/6/14 16:21
   * @description 通过区域医保服务平台推送消息（待结算、结算成功、检查报告、挂号通知）等信息给用户,待结算消息推送（必选）
   **/
	@Override
	public WrapperResponse<Boolean> AMP_HOS_001(Map<String, Object> map) {
		return  WrapperResponse.success(insureUnifiedOutptBO.AMP_HOS_001(map));
	}
}
