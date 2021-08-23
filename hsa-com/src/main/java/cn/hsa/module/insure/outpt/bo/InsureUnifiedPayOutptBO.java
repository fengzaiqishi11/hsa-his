package cn.hsa.module.insure.outpt.bo;

import cn.hsa.module.center.outptprofilefile.dto.OutptProfileFileDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.prescribe.dto.OutptDiagnoseDTO;
import cn.hsa.module.outpt.register.dto.OutptRegisterDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.outpt.bo
 * @Class_name: InsureUnifiedPayOutptBo
 * @Describe: 统一支付平台，门诊实现层接口
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/2/10 8:07
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface InsureUnifiedPayOutptBO {

	/**
	 * @Description: 门诊患者信息上传
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/10 9:28
	 * @Return
	 */
	void UP_2203(Map<String, Object> unifiedPayMap);
	/**
	 * @Description: 门诊预结算
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/15 12:00
	 * @Return
	 */
	Map<String, Object> UP_2206(Map<String, Object> unifiedPayMap);

	/**
	 * @Description: 门诊结算
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/15 13:17
	 * @Return
	 */
	Map<String,Object> UP_2207(Map<String, Object> unifiedPayMap);

	/**
	 * @Menthod: UP_2201
	 * @Desrciption: 统一支付平台-门诊挂号
	 * @Param: his门诊挂号实体-outptRegisterDTO, 节点标识-flag
	 * @Author: luoyong
	 * @Email: luoyong@powersi.com.cn
	 * @Date: 2021-02-10 09:35
	 * @Return:
	 **/
	InsureIndividualVisitDTO UP_2201(Map<String,Object>map);

	/**
	 * @Menthod: UP_2202
	 * @Desrciption: 统一支付平台-门诊挂号撤销
	 * @Param: his门诊挂号实体-outptRegisterDTO, 节点标识-flag
	 * @Author: luoyong
	 * @Email: luoyong@powersi.com.cn
	 * @Date: 2021-02-10 11:00
	 * @Return:
	 **/
	Boolean UP_2202(Map<String,Object> map);

	/**
	 * @Menthod: UP_2208
	 * @Desrciption: 统一支付平台-门诊结算撤销
	 * @Param: 节点标识-flag
	 * @Author: luoyong
	 * @Email: luoyong@powersi.com.cn
	 * @Date: 2021-02-10 11:00
	 * @Return:
	 **/
	Map<String,Object> UP_2208(Map<String,Object> map);

	/**
	 * @param map
	 * @Method updateFeeSubmit()
	 * @Desrciption 门诊医保病人费用传输
	 * @Param
	 * @Author fuhui
	 * @Date 2021/3/3 16:38
	 * @Return
	 */
	Boolean insertOutptUnifiedFee(Map<String, Object> map);

	/**
	 * @param map
	 * @Method updateCancelFeeSubmit
	 * @Desrciption 门诊医保病人取消费用上传
	 * @Param
	 * @Author fuhui
	 * @Date 2021/3/3 16:44
	 * @Return
	 */
	Boolean UP_2205(Map<String, Object> map);

	/**
	 * @Method UP_5301
	 * @Desrciption  人员慢特病备案查询
	 * @Param
	 *
	 * @Author fuhui
	 * @Date   2021/3/25 10:41
	 * @Return
	 **/
	Map<String,Object> UP_5301(Map<String, Object> params);

	/**
	 * @Method UP_5302
	 * @Desrciption 医保统一支付平台：人员定点备案查询
	 * @Param map
	 *
	 * @Author fuhui
	 * @Date   2021/4/2 9:12
	 * @Return
	 **/
	Map<String,Object> UP_5302(Map<String, Object> map);

	/**
	 * @Menthod: UP_4301
	 * @Desrciption: 医保统一支付平台：门急诊诊疗记录，上传单次病人就诊信息
	 * @Param:
	 * @Author: luoyong
	 * @Email: luoyong@powersi.com.cn
	 * @Date: 2021-08-17 19:45
	 * @Return:
	 **/
    Map<String, Object> UP_4301(Map<String, Object> map);
}
