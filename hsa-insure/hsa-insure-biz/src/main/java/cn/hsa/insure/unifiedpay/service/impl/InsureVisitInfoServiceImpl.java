package cn.hsa.insure.unifiedpay.service.impl;

import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.module.insure.outpt.bo.InsureVisitInfoBO;
import cn.hsa.module.insure.outpt.service.InsureVisitInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.service.impl
 * @Class_name: InsureVisitInfoServiceImpl
 * @Describe:
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/2/22 14:04
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@HsafRestPath("/service/insure/insureVisitInfo")
@Service("InsureVisitInfoServiceImpl_provider")
public class InsureVisitInfoServiceImpl implements InsureVisitInfoService {

	@Resource
	private InsureVisitInfoBO insureVisitInfoBO;

	/**
	 * @Description: 查询医保患者信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/22 14:13
	 * @Return
	 */
	@Override
	public Map<String, Object> getInsureVisitInfo(Map<String, Object> params) {
		return insureVisitInfoBO.getInsureVisitInfo(params);
	}

	/**
	 * @Description: 统一支付平台 医保目录下载
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/24 13:55
	 * @Return
	 */
	@Override
	public Map<String, Object> DOWN_3301(Map<String, Object> params) {
		return insureVisitInfoBO.DOWN_3301(params);
	}

	/**
	 * @Description: 统一支付平台 医保匹配目录上传
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/24 13:55
	 * @Return
	 */
	@Override
	public void UP_3302(Map<String, Object> params) {
		insureVisitInfoBO.UP_3302(params);
	}

	/**
	 * @Description: 统一支付平台 医保已经完成匹配目录查询
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/2/24 13:56
	 * @Return
	 */
	@Override
	public Map<String, Object> DOWN_5163(Map<String, Object> params) {
		return insureVisitInfoBO.DOWN_5163(params);
	}
}
