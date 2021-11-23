package cn.hsa.interf.dzpz.service.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.module.interf.outpt.bo.HaiNanDZPZPayCallBackBO;
import cn.hsa.module.interf.outpt.service.HaiNanDZPZPayCallBackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.interf.dzpz.service.impl
 * @Class_name: HaiNanDZPZPayCallBackServiceImpl
 * @Describe: 海南电子凭证回调
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/11/17 11:49
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/interf/dzpz")
@Slf4j
@Service("haiNanDZPZPayCallBackService_provider")
public class HaiNanDZPZPayCallBackServiceImpl extends HsafBO implements HaiNanDZPZPayCallBackService {

	@Resource
	private HaiNanDZPZPayCallBackBO haiNanDZPZPayCallBackBO;


	@Override
	public void haiNanDZPZPayCallBack(Map<String, String> map) {
		haiNanDZPZPayCallBackBO.saveDZPZSettle(map);
	}
}
