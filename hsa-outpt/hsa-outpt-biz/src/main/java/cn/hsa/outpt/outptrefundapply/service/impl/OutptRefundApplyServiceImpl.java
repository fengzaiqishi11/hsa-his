package cn.hsa.outpt.outptrefundapply.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.outptrefundapply.bo.OutptRefundApplyBO;
import cn.hsa.module.outpt.outptrefundapply.service.OutptRefundApplyService;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.outpt.outptrefundapply.service.impl
 * @Class_name: OutptRefundApplyServiceImpl
 * @Describe: 门诊医生退费申请
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/3/9 15:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/outpt/outptRefundApply")
@Slf4j
@Service("outptRefundApplyService_provider")
public class OutptRefundApplyServiceImpl extends HsafService implements OutptRefundApplyService {

	@Resource
	private OutptRefundApplyBO outptRefundApplyBO;

	/**
	 * @Description: 保存门诊医生退费申请
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/9 15:37
	 * @Return
	 */
	@Override
	public WrapperResponse<Boolean> saveOutptRefundAppy(Map map) {
		return WrapperResponse.success(outptRefundApplyBO.saveOutptRefundAppy(map));
	}

	@Override
	public WrapperResponse queryOutChargePage(Map param) {
		OutptSettleDTO outptSettleDTO = MapUtils.get(param,"outptSettleDTO");
		return outptRefundApplyBO.queryOutChargePage(outptSettleDTO);
	}

	public WrapperResponse queryOutptPrescribes(Map param) {
		return outptRefundApplyBO.queryOutptPrescribes(param);
	}

	/**
	 * @Description: 确认医生退费申请
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/3/10 16:27
	 * @Return
	 */
	@Override
	public WrapperResponse<Boolean> updateOutptRefundAppyStatus(Map<String, Object> param) {
		return WrapperResponse.success(outptRefundApplyBO.updateOutptRefundAppyStatus(param));
	}

	/**
	 * @Description: 门诊医生取消退费确认
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/12/01 14:30
	 * @Return
	 */
	@Override
	public WrapperResponse<Boolean> updateUnconfirmedOutptRefundAppy(Map<String, Object> param) {
		return WrapperResponse.success(outptRefundApplyBO.updateUnconfirmedOutptRefundAppy(param));
	}

	/**
	 * @Description: 门诊医生取消退费申请
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/12/01 15:50
	 * @Return
	 */
	@Override
	public WrapperResponse<Boolean> saveCancelOutptRefundAppy(Map<String, Object> param) {
		return WrapperResponse.success(outptRefundApplyBO.deleteOutptRefundAppy(param));
	}
}
