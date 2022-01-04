package cn.hsa.module.emr.emrarchivelogging.dao;

import cn.hsa.module.emr.emrarchivelogging.dto.EmrArchiveLoggingDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.emr.emrarchivelogging.dao
 * @Class_name: EmrArchiveLoggingDAO
 * @Describe: 病人病历归档，以病人为对象，
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/9/24 15:49
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface EmrArchiveLoggingDAO {

	/**
	 * @Description: 新增病人病历归档记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/24 16:08
	 * @Return
	 */
	boolean insertEmrArchiveLoggingDO(List<EmrArchiveLoggingDTO> list);

	/**
	 * @Description: 更新病人病历归档记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/24 16:09
	 * @Return
	 */
	boolean updateEmrArchiveLoggingDO(EmrArchiveLoggingDTO emrArchiveLoggingDTO);

	/**
	 * @Description: 查询病人病历归档记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/24 16:09
	 * @Return
	 */
	EmrArchiveLoggingDTO getEmrArchiveLoggingDO(EmrArchiveLoggingDTO emrArchiveLoggingDTO);

	/**
	 * @Description: 查询住院病人归档信息,未归档病人
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/13 19:56
	 * @Return
	 */
	List<EmrArchiveLoggingDTO> getZYEmrFilePatients(EmrArchiveLoggingDTO emrArchiveLoggingDTO);

	/**
	 * @Description: 查询住院病人归档信息,归档病人
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/15 9:01
	 * @Return
	 */
	List<EmrArchiveLoggingDTO> getZYEmrFilePatientsGd(EmrArchiveLoggingDTO emrArchiveLoggingDTO);

	/**
	 * @Description: 根据选择的需要归档的病人，检查这些病人是否拥有需要送审的病历，且病历审核状态不是通过状态的病人名字
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/14 10:00
	 * @Return
	 */
	List<EmrArchiveLoggingDTO> getPatientIsArchive(String visitIds);

	/**
	 * @Description: 查询当前就诊id集合中已经归档的病人id
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/15 9:46
	 * @Return
	 */
	List<EmrArchiveLoggingDTO> getIsArchives(String visitIds);

	/**
	 * @Description: 删除归档信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/15 11:50
	 * @Return
	 */
	void deleteArchives(String visitIds);

	/**
	 * @Description: 根据就诊id集合，查询归档状态为已归档的归档信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/28 15:12
	 * @Return
	 */
	List<EmrArchiveLoggingDTO> getArchiveLoggingsByVisitIds(String toString);

	/**
	 * @Description: 查询病人出院7天，带出未归档信息
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/06/25 15:12
	 * @Return
	 */
	List<EmrArchiveLoggingDTO> getZYEmrFilePatientsNoArchived(Map param);

	List<EmrArchiveLoggingDTO> getZYEmrNoArchivedInfo(Map param);
}
