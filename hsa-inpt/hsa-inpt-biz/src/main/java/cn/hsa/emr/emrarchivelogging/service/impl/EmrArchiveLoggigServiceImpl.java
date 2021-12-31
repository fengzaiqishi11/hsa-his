package cn.hsa.emr.emrarchivelogging.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrarchivelogging.bo.EmrArchiveLoggingBO;
import cn.hsa.module.emr.emrarchivelogging.dto.EmrArchiveLoggingDTO;
import cn.hsa.module.emr.emrarchivelogging.entity.EmrArchiveLoggingDO;
import cn.hsa.module.emr.emrarchivelogging.service.EmrArchiveLoggingService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.emr.emrarchivelogging.service.impl
 * @Class_name: EmrArchiveLoggigServiceImpl
 * @Describe: 病人病历归档记录新增、修改、查询
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/9/24 17:14
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/emr/emrarchivelogging")
@Slf4j
@Service("emrArchiveLoggingService_provider")
public class EmrArchiveLoggigServiceImpl extends HsafService implements EmrArchiveLoggingService {

	@Resource
	private EmrArchiveLoggingBO emrArchiveLoggingBO;

	/**
	 * @Description: 新增病人病历归档记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/24 19:12
	 * @Return
	 */
	@Override
	public WrapperResponse<Boolean> insertEmrArchiveLogging(Map map) {
		EmrArchiveLoggingDTO emrArchiveLoggingDTO = MapUtils.get(map, "emrArchiveLoggingDTO");
		return WrapperResponse.success(emrArchiveLoggingBO.insertEmrArchiveLogging(emrArchiveLoggingDTO));
	}

	/**
	 * @Description: 更新病人病历归档记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/24 19:12
	 * @Return
	 */
	@Override
	public WrapperResponse<Boolean> updateEmrArchiveLogging(Map map) {
		EmrArchiveLoggingDTO emrArchiveLoggingDTO = MapUtils.get(map, "emrArchiveLoggingDTO");
		return WrapperResponse.success(emrArchiveLoggingBO.updateEmrArchiveLogging(emrArchiveLoggingDTO));
	}

	/**
	 * @Description: 查询病人病历归档记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/24 19:13
	 * @Return
	 */
	@Override
	public WrapperResponse<EmrArchiveLoggingDO> getEmrArchiveLogging(Map map) {
		EmrArchiveLoggingDTO emrArchiveLoggingDTO = MapUtils.get(map, "emrArchiveLoggingDTO");
		return WrapperResponse.success(emrArchiveLoggingBO.getEmrArchiveLogging(emrArchiveLoggingDTO));
	}

	/**
	 * @Description: 查询所有住院病人信息，带出归档状态
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/13 20:35
	 * @Return
	 */
	@Override
	public WrapperResponse<PageDTO> getZYEmrFilePatients(Map map) {
		EmrArchiveLoggingDTO emrArchiveLoggingDTO = MapUtils.get(map, "emrArchiveLoggingDTO");
		return WrapperResponse.success(emrArchiveLoggingBO.getZYEmrFilePatients(emrArchiveLoggingDTO));
	}

	/**
	 * @Description: 病人出院7天自动归档
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/06/25 11:39
	 * @Return
	 */
	@Override
	public WrapperResponse<Boolean> outHospInsertEmrArchiveLogging(Map map) {
		return WrapperResponse.success(emrArchiveLoggingBO.insertOutHospEmrArchiveLogging(map));
	}

	/**
	 * @Description: 病人出院七天未归档信息写入消息表
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/11/25 9:16
	 * @Return
	 */
	@Override
	public WrapperResponse<Boolean> insertOutHospEmrArchiveLogging(Map map) {
		return WrapperResponse.success(emrArchiveLoggingBO.insertOutHospEmrArchiveMsg(map));
	}

}
