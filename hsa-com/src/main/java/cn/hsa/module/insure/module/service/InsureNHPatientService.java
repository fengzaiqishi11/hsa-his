package cn.hsa.module.insure.module.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.module.service
 * @Class_name: InsureNHPatientService
 * @Describe: 农合医保病人数据下发
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/4/19 19:47
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-insure")
public interface InsureNHPatientService {

	/**
	 * @Description: 保存农合病人住院数据到医院本地数据库
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/4/19 19:56
	 * @Return
	 */
	WrapperResponse<Boolean> saveNHPatientToLocal(Map<String, Object> map);

}
