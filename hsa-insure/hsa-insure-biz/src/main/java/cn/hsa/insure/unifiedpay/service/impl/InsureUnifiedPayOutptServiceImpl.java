package cn.hsa.insure.unifiedpay.service.impl;

import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.outpt.bo.InsureUnifiedPayOutptBO;
import cn.hsa.module.insure.outpt.service.InsureUnifiedPayOutptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
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
	/**
	 * @Description: 门诊患者信息上传
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/10 9:04
	 * @Return
	 */
	@Override
	public void insureOutptVisitUpload(Map<String, Object> unifiedPayMap) {
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
	public Map<String, Object> insureOutptSettleAccountIn(Map<String, Object> unifiedPayMap) {
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
	public WrapperResponse<Boolean> updateCancelFeeSubmit(Map<String, Object> map) {
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
	public WrapperResponse<Boolean> updateFeeSubmit(Map<String, Object> map) {
		return WrapperResponse.success(insureUnifiedPayOutptBO.insertOutptUnifiedFee(map));
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
		return WrapperResponse.success(insureUnifiedPayOutptBO.UP_4301(map));
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
		return WrapperResponse.success(insureUnifiedPayOutptBO.UP4302(map));
	}

	/**
	 * @Method handlerInsurePatientSum
	 * @Desrciption  结算之前,保存个人累计信息
	 * @Param
	 *
	 * @Author fuhui
	 * @Date   2021/12/14 16:13
	 * @Return
	 **/
	@Override
	public WrapperResponse<Boolean> insertPatientSumInfo(Map<String, Object> map) {
		return WrapperResponse.success(insureUnifiedPayOutptBO.insertPatientSumInfo(map));
	}

	/**
	 * @Method updateInsureCost
	 * @Desrciption  费用传输以后：更新医保的反参数据
	 * @Param
	 *
	 * @Author fuhui
	 * @Date   2021/5/21 8:35
	 * @Return
	 **/
	@Override
	public WrapperResponse<Boolean> insertInsureCost(List<Map<String, Object>> resultDataMap, Map<String, Object> map,List<Map<String, Object>> list) {
		return WrapperResponse.success(insureUnifiedPayOutptBO.insertInsureCost(resultDataMap,map,list));
	}
}
