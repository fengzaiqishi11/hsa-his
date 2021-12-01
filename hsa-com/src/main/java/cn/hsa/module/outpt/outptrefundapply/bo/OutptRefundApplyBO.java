package cn.hsa.module.outpt.outptrefundapply.bo;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.outptrefundapply.bo
 * @Class_name: OutptRefundApplyBO
 * @Describe: 门诊医生退费申请
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/3/9 15:29
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface OutptRefundApplyBO {

	/**
	 * @Description: 门诊医生退费申请保存
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/9 15:40
	 * @Return
	 */
	Boolean saveOutptRefundAppy(Map<String, Object> map);

	/**
	 * @Description: 查询门诊已经收费结算
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/10 11:36
	 * @Return
	 */
	WrapperResponse queryOutChargePage(OutptSettleDTO outptSettleDTO);

	WrapperResponse queryOutptPrescribes(Map param);

	/**
	 * @Description: 确认门诊医生退费申请
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/10 16:28
	 * @Return
	 */
	Boolean updateOutptRefundAppyStatus(Map<String, Object> param);

	/**
	 * @Description: 门诊医生取消退费确认
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/12/01 14:43
	 * @Return
	 */
	Boolean updateUnconfirmedOutptRefundAppy(Map<String, Object> param);

	/**
	 * @Description: deleteOutptRefundAppy
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/12/01 15:50
	 * @Return
	 */
	Boolean deleteOutptRefundAppy(Map<String, Object> param);
}
