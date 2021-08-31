package cn.hsa.module.emr.emrarchivelogging.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.emr.emrarchivelogging.dto.EmrArchiveLoggingDTO;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrarchivelogging.bo
 * @Class_name: EmrArchiveLoggingBO
 * @Describe: 记录病人病历归档情况
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/9/24 16:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface EmrArchiveLoggingBO {

	/**
	 * @Description: 新增病人病历归档记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/24 16:54
	 * @Return
	 */
	boolean insertEmrArchiveLogging(EmrArchiveLoggingDTO emrArchiveLoggingDTO);

	/**
	 * @Description: 更新病人病历归档记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/24 16:55
	 * @Return
	 */
	boolean updateEmrArchiveLogging(EmrArchiveLoggingDTO emrArchiveLoggingDTO);

	/**
	 * @Description: 查询病人病历归档记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/24 16:55
	 * @Return
	 */
	EmrArchiveLoggingDTO getEmrArchiveLogging(EmrArchiveLoggingDTO emrArchiveLoggingDTO);

	/**
	 * @Description: 查询住院病人列表，带出归档状态
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/13 20:29
	 * @Return
	 */
	PageDTO getZYEmrFilePatients(EmrArchiveLoggingDTO emrArchiveLoggingDTO);

	/**
	 * @Description: 病人出院病历自动归档记录
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com.cn
	 * @Date 2021/6/22 14:54
	 * @Return
	 */
	boolean insertOutHospEmrArchiveLogging(Map param);


}
