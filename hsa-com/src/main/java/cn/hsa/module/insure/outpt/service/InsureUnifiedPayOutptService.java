package cn.hsa.module.insure.outpt.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.register.dto.OutptRegisterDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import org.springframework.cloud.openfeign.FeignClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.outpt.service
 * @Class_name: InsureUnifiedPayOutptService
 * @Describe: 统一支付平台，门诊医保
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/2/9 17:26
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-insure")
public interface InsureUnifiedPayOutptService {

	/**
	 * @Menthod: UP_2201
	 * @Desrciption: 统一支付平台-门诊挂号
	 * @Param: his门诊挂号实体-outptRegisterDTO, 节点标识-flag
	 * @Author: luoyong
	 * @Email: luoyong@powersi.com.cn
	 * @Date: 2021-02-10 09:35
	 * @Return:
	 **/
	WrapperResponse<InsureIndividualVisitDTO> UP_2201(Map<String,Object>map);

	/**
	 * @Menthod: UP_2202
	 * @Desrciption: 统一支付平台-门诊挂号撤销
	 * @Param: his门诊挂号实体-outptRegisterDTO, 节点标识-flag
	 * @Author: luoyong
	 * @Email: luoyong@powersi.com.cn
	 * @Date: 2021-02-10 11:00
	 * @Return:
	 **/
	WrapperResponse<Boolean> UP_2202(Map<String,Object> map);

	/**
	 * @Description: 门诊就诊信息上传
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/10 9:02
	 * @Return
	 */
	void UP_2203(Map<String, Object> unifiedPayMap);

	/**
	 * @param map
	 * @Method updateFeeSubmit()
	 * @Desrciption 门诊医保病人费用传输
	 * @Param
	 * @Author fuhui
	 * @Date 2021/3/3 16:38
	 * @Return
	 */
	WrapperResponse<Boolean> UP_2204(Map<String, Object> map);

	/**
	 * @param map
	 * @Method updateCancelFeeSubmit
	 * @Desrciption 门诊医保病人取消费用上传
	 * @Param
	 * @Author fuhui
	 * @Date 2021/3/3 16:44
	 * @Return
	 */
	WrapperResponse<Boolean> UP_2205(Map<String, Object> map);

	/**
	 * @Description: 门诊预结算
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/15 11:56
	 * @Return
	 */
	Map<String, Object> UP_2206(Map<String, Object> unifiedPayMap);

	/**
	 * @Description: 门诊结算
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/15 13:16
	 * @Return
	 */
	Map<String,Object> UP_2207(Map<String, Object> unifiedPayMap);

	/**
	 * @Menthod: UP_2208
	 * @Desrciption: 统一支付平台-门诊结算撤销
	 * @Param: 节点标识-flag
	 * @Author: luoyong
	 * @Email: luoyong@powersi.com.cn
	 * @Date: 2021-02-10 11:00
	 * @Return:
	 **/
	WrapperResponse< Map<String,Object>> UP_2208(Map<String,Object> map);





	/**
	 * @Method UP_5301
	 * @Desrciption  查询门特病人信息
	 * @Param UP_5301
	 *
	 * @Author fuhui
	 * @Date   2021/3/25 19:28
	 * @Return
	**/
    WrapperResponse<Map<String, Object>> UP_5301(Map<String, Object> params);

	/**
	 * @Method UP_5302
	 * @Desrciption 医保统一支付平台：人员定点备案查询
	 * @Param map
	 *
	 * @Author fuhui
	 * @Date   2021/4/2 9:12
	 * @Return
	 **/
	WrapperResponse<Map<String, Object>> UP_5302(Map<String, Object> map);

	/**
	 * @Menthod: UP_4301
	 * @Desrciption: 医保统一支付平台：门急诊诊疗记录，上传单次病人就诊信息
	 * @Param:
	 * @Author: luoyong
	 * @Email: luoyong@powersi.com.cn
	 * @Date: 2021-08-17 19:45
	 * @Return:
	 **/
	WrapperResponse<Map<String, Object>> UP_4301(Map<String, Object> map);

	/**
	 * @Menthod: UP_4302
	 * @Desrciption: 医保统一支付平台：急诊留观手术及抢救信息
	 * @Param:
	 * @Author: luoyong
	 * @Email: luoyong@powersi.com.cn
	 * @Date: 2021-08-17 19:45
	 * @Return:
	 **/
    WrapperResponse<Map<String, Object>> UP4302(Map<String, Object> map);

    /**
     * 医保统一支付平台：线上费用明细上传
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-04-26 9:34
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
     */
    WrapperResponse<Map<String, Object>> UP6201(Map<String, Object> map);

    /**
     * 医保订单结算结果查询
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-05-09 16:38
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
     */
    WrapperResponse<Map<String, Object>> UP6301(Map<String, Object> map);

    /**
     * 费用明细上传撤销
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-05-10 14:07
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
     */
    WrapperResponse<Map<String, Object>> UP6401(Map<String, Object> map);

    /**
     * 医保退费
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-05-17 10:00
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
     */
    WrapperResponse<Map<String, Object>> UP6203(Map<String, Object> map);

    /**
     * 线上医保移动支付完成的结算订单，可通过此接口进行退款
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-06-15 9:33
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
     */
    WrapperResponse<Map<String, Object>> AmpRefund(Map<String, Object> map);

}
