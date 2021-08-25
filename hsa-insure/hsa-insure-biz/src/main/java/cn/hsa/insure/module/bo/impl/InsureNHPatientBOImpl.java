package cn.hsa.insure.module.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.insure.util.RequestInsure;
import cn.hsa.module.insure.module.bo.InsureNHPatientBO;
import cn.hsa.util.MapUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.module.bo.impl
 * @Class_name: InsureNHPatientBOImpl
 * @Describe: 农合医保患者信息下发本地数据库
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/4/19 19:51
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
public class InsureNHPatientBOImpl extends HsafBO implements InsureNHPatientBO {

	@Resource
	private RequestInsure requestInsure;


	/**
	 * @Description: 保存农合病人住院数据到医院本地数据库
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/4/19 20:00
	 * @Return
	 */
	@Override
	public Boolean saveNHPatientToLocal(Map<String, Object> map) {
		String hospCode = MapUtils.get(map, "hospCode");
		String kafkaIp = MapUtils.get(map, "KAFKA_IP");
		String url = MapUtils.get(map, "url");
		Map<String,Object> paramObj = map;
		requestInsure.saveNHPatientToLocal(hospCode, kafkaIp, url, paramObj);
		return null;
	}

}
