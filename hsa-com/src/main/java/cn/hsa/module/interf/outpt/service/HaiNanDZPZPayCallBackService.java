package cn.hsa.module.interf.outpt.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.interf.outpt.service
 * @Class_name: haiNanDZPZPayCallBack
 * @Describe: 海南电子凭证回调
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/11/17 11:24
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-interf")
public interface HaiNanDZPZPayCallBackService {
	
	/**
	 * @Description: 海南电子凭证回调
	 * @Param: 
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/11/17 11:28
	 * @Return 
	 */
	@GetMapping("/pmc/api/hos/seltSucCallback")
	void haiNanDZPZPayCallBack(Map<String, String> map);

}
