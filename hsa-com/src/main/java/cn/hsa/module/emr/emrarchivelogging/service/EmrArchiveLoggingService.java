package cn.hsa.module.emr.emrarchivelogging.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrarchivelogging.entity.EmrArchiveLoggingDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrarchivelogging.service
 * @Class_name: EmrArchiveLoggingService
 * @Describe: 记录病人病历归档情况
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/9/24 17:02
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(name = "hsa-emr")
public interface EmrArchiveLoggingService {

	/**
	 * @Description: 新增病人病历归档记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/24 16:54
	 * @Return
	 */
	@PostMapping("/service/emr/emrArchiveLogging/insertEmrArchiveLogging")
	WrapperResponse<Boolean> insertEmrArchiveLogging(Map map);

	/**
	 * @Description: 更新病人病历归档记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/24 16:55
	 * @Return
	 */
	@PostMapping("/service/emr/emrarchivelogging/updateEmrArchiveLogging")
	WrapperResponse<Boolean> updateEmrArchiveLogging(Map map);

	/**
	 * @Description: 查询病人病历归档记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/24 16:55
	 * @Return
	 */
	@PostMapping("/service/emr/emrarchivelogging/getEmrArchiveLogging")
	WrapperResponse<EmrArchiveLoggingDO> getEmrArchiveLogging(Map map);

	/**
	 * @Description: 查询所有住院病人信息，带出归档信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/13 20:33
	 * @Return
	 */
	@GetMapping("/service/emr/emrarchivelogging/getZYEmrFilePatients")
	WrapperResponse<PageDTO> getZYEmrFilePatients(Map map);

	/**
	 * @Description: 病人出院病历自动归档
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com.cn
	 * @Date 2021/06/25 16:54
	 * @Return
	 */
	@PostMapping("/service/emr/emrArchiveLogging/outHospInsertEmrArchiveLogging")
	WrapperResponse<Boolean> outHospInsertEmrArchiveLogging(Map map);

	/**
	 * @Description: 病人出院7天未归档写入消息
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com.cn
	 * @Date 2021/11/25 16:54
	 * @Return
	 */
	@PostMapping("/service/emr/emrArchiveLogging/insertOutHospEmrArchiveLogging")
	WrapperResponse<Boolean> insertOutHospEmrArchiveLogging(Map map);


}
