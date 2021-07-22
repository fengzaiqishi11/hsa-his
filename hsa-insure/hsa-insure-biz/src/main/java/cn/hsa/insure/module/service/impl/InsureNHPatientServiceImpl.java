package cn.hsa.insure.module.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.bo.InsureNHPatientBO;
import cn.hsa.module.insure.module.service.InsureNHPatientService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.module.service.impl
 * @Class_name: InsureNHPatientServiceImpl
 * @Describe: 农合医保患者信息数据下发
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2021/4/19 19:48
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/insure/insureNHPatient")
@Service("insureNHPatientService_provider")
public class InsureNHPatientServiceImpl extends HsafService implements InsureNHPatientService {

	@Resource
	private InsureNHPatientBO insureNHPatientBO;

	/**
	 * @Description: 保存农合病人住院数据到医院本地数据库
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2021/4/19 19:57
	 * @Return
	 */
	@Override
	public WrapperResponse<Boolean> saveNHPatientToLocal(Map<String, Object> map) {
		return WrapperResponse.success(insureNHPatientBO.saveNHPatientToLocal(map));
	}
}
