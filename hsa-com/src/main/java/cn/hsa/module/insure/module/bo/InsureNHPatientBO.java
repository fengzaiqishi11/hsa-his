package cn.hsa.module.insure.module.bo;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.module.bo
 * @Class_name: InsureNHPatientBO
 * @Describe: 医保农合病人数据下发本地数据库
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/4/19 19:46
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface InsureNHPatientBO {

	/**
	 * @Description: 保存农合病人住院数据到医院本地数据库
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/4/19 19:59
	 * @Return
	 */
	Boolean saveNHPatientToLocal(Map<String, Object> map);

}
