package cn.hsa.interf.dzpz.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.interf.outpt.bo.HaiNanDZPZPayCallBackBO;
import cn.hsa.module.outpt.fees.service.OutptTmakePriceFormService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.interf.dzpz.bo.impl
 * @Class_name: HaiNanDZPZPayCallBackBOImpl
 * @Describe: TODO
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/11/17 11:54
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Service
public class HaiNanDZPZPayCallBackBOImpl extends HsafBO implements HaiNanDZPZPayCallBackBO {

	@Resource
	private OutptTmakePriceFormService outptTmakePriceFormService_consumer;

	@Override
	public void saveDZPZSettle(Map<String, String> map) {
		outptTmakePriceFormService_consumer.seltSucCallback(map);
	}
}
