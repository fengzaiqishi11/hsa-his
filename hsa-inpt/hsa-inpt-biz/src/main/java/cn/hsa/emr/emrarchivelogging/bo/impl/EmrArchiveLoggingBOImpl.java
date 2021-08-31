package cn.hsa.emr.emrarchivelogging.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.emr.emrarchivelogging.bo.EmrArchiveLoggingBO;
import cn.hsa.module.emr.emrarchivelogging.dao.EmrArchiveLoggingDAO;
import cn.hsa.module.emr.emrarchivelogging.dto.EmrArchiveLoggingDTO;
import cn.hsa.module.emr.emrpatient.dao.EmrPatientDAO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.SnowflakeUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.emr.emrarchivelogging.bo.impl
 * @Class_name: EmrArchiveLoggingBOImpl
 * @Describe: 病人病历归档
 * @Author: guanhongqiang
 * @Eamil: hongqiang.guan@powersi.com.cn
 * @Date: 2020/9/24 17:07
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class EmrArchiveLoggingBOImpl extends HsafBO implements EmrArchiveLoggingBO {

	@Resource
	private EmrArchiveLoggingDAO emrArchiveLoggingDAO;

	@Resource
	private EmrPatientDAO emrPatientDAO;
	/**
	 * @Description: 新增病人病历归档记录, 新增只会在设置为归档时才会增，如果有未审核完成病历，不允许归档
	 * 1.校验传入的就诊id的病历是否全部完成审核
	 * 2.写入就诊id的归档情况
	 * 3.取消归档后再归档时，先删除之前的归档记录，再插入新的归档记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/24 17:12
	 * @Return
	 */
	@Override
	public boolean insertEmrArchiveLogging(EmrArchiveLoggingDTO emrArchiveLoggingDTO) {
		// 取出就诊id集合
		List<String> visitIdList = emrArchiveLoggingDTO.getVisitIds();
		StringBuilder visitIds = new StringBuilder();
		List<EmrArchiveLoggingDTO> archiveLoggingDTOList = new ArrayList<>();
		if (visitIdList != null && visitIdList.size() > 0) {
			for (String visitid : visitIdList) {
				visitIds.append("'");
				visitIds.append(visitid);
				visitIds.append("',");
				// 准备归档数据
				EmrArchiveLoggingDTO dto = new EmrArchiveLoggingDTO();
				dto.setId(SnowflakeUtils.getId());
				dto.setVisitId(visitid);
				dto.setArchiveUserId(emrArchiveLoggingDTO.getArchiveUserId());
				dto.setArchiveName(emrArchiveLoggingDTO.getArchiveName());
				dto.setArchiveOption(emrArchiveLoggingDTO.getArchiveOption());
				dto.setArchiveState("1");
				dto.setArchiveTime(emrArchiveLoggingDTO.getArchiveTime());
				archiveLoggingDTOList.add(dto);
			}
			if (visitIds.length() > 0 && !"".equals(visitIds.toString())) {
				visitIds.deleteCharAt(visitIds.length()-1);
			}
		}
		// 查询当前传入的就诊id集合中，存在需要送审病历，但审核标识为未通过
		List<EmrArchiveLoggingDTO> list = emrArchiveLoggingDAO.getPatientIsArchive(visitIds.toString());
		List<EmrArchiveLoggingDTO> haveGuiDangList = emrArchiveLoggingDAO.getIsArchives(visitIds.toString());
		if (list != null && list.size() > 0) {
			StringBuilder patientName = new StringBuilder();
			for (EmrArchiveLoggingDTO e : list) {
				patientName.append(e.getPatientName());
				patientName.append(",");
			}
			if (patientName.length() > 0 && !"".equals(patientName.toString())) {
				patientName.deleteCharAt(patientName.length()-1);
			}
			throw new AppException("当前病人 ["+ patientName.toString() + "] 存在需要送审且已送审但审核未完成的病历，不能归档");
		} else if (haveGuiDangList != null && haveGuiDangList.size() > 0) {
			StringBuilder patientName = new StringBuilder();
			for (EmrArchiveLoggingDTO e : haveGuiDangList) {
				patientName.append(e.getPatientName());
				patientName.append(",");
			}
			if (patientName.length() > 0 && !"".equals(patientName.toString())) {
				patientName.deleteCharAt(patientName.length()-1);
			}
			throw new AppException("当前病人"+ patientName.toString() + "已经归档，不能重复归档");
		} else {
			// 归档前，先删除之前的归档数据
			emrArchiveLoggingDAO.deleteArchives(visitIds.toString());
			// 执行批量新增操作，批量归档
			return emrArchiveLoggingDAO.insertEmrArchiveLoggingDO(archiveLoggingDTOList);
		}
	}

	/**
	 * @Description: 将病人病历已归档记录修改为未归档，（取消归档）需要校验是否包含未归档病历，包含未归档病历不允许归档
	 *  1.将需要取消归档的病人id由list集合转为String,
	 *  2.校验是否存在未归档病历，存在未归档病历给出提示，且不能取消归档
	 *  3.将不能取消归档的病人名字返回给页面提示
	 *  4.批量更新就诊病人的归档状态为“未归档”
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/24 17:12
	 * @Return
	 */
	@Override
	public boolean updateEmrArchiveLogging(EmrArchiveLoggingDTO emrArchiveLoggingDTO) {
		// 取出就诊id集合
		List<String> visitIdList = emrArchiveLoggingDTO.getVisitIds();
		StringBuilder visitIds = new StringBuilder();
		if (visitIdList != null && visitIdList.size() > 0) {
			for (String visitid : visitIdList) {
				visitIds.append("'");
				visitIds.append(visitid);
				visitIds.append("',");
			}
			if (visitIds.length() > 0 && !"".equals(visitIds.toString())) {
				visitIds.deleteCharAt(visitIds.length()-1);
			}
		}
		// 存在病人就诊id
		if (visitIds.length() > 0) {
			//  校验是否存在未归档病历，存在未归档病历给出提示，且不能取消归档
			// 查询页面选中的就诊id集合的归档记录
			List<EmrArchiveLoggingDTO> list = emrArchiveLoggingDAO.getArchiveLoggingsByVisitIds(visitIds.toString());
			// 当前需要取消归档的病人信息集合
			Map<String, String> visitsMap = new HashMap<>();
			// 已经归档的病人集合
			Map<String, String> ygdMap = new HashMap<>();
			for (EmrArchiveLoggingDTO dto : list) {
				visitsMap.put(dto.getVisitId(), dto.getPatientName());
				if (dto.getArchiveState() != null && "1".equals(dto.getArchiveState())) {
					ygdMap.put(dto.getVisitId(), dto.getPatientName());
				}
			}
			StringBuilder patientNames = new StringBuilder();
			for (String visitId : visitIdList) {
				if (!ygdMap.containsKey(visitId)) {
					patientNames.append(visitsMap.get(visitId));
					patientNames.append(",");
				}
			}
			if (patientNames.length() > 0 && !"".equals(patientNames.toString())) {
				patientNames.deleteCharAt(patientNames.length()-1);
				throw new AppException("当前病人"+ patientNames.toString() + "未归档，不能取消归档");
			}
			emrArchiveLoggingDTO.setStringVisitIds(visitIds.toString());
			// 归档状态为 否
			emrArchiveLoggingDTO.setArchiveState("0");
			return emrArchiveLoggingDAO.updateEmrArchiveLoggingDO(emrArchiveLoggingDTO);
		}
		return false;
	}

	/**
	 * @Description: 查询病人病历归档记录
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/9/24 17:13
	 * @Return
	 */
	@Override
	public EmrArchiveLoggingDTO getEmrArchiveLogging(EmrArchiveLoggingDTO emrArchiveLoggingDTO) {
		return emrArchiveLoggingDAO.getEmrArchiveLoggingDO(emrArchiveLoggingDTO);
	}

	/**
	 * @Description: 查询住院病人信息，带出归档信息
	 * @Param:
	 * @Author: guanhongqiang
	 * @Email: hongqiang.guan@powersi.com.cn
	 * @Date 2020/10/13 20:34
	 * @Return
	 */
	@Override
	public PageDTO getZYEmrFilePatients(EmrArchiveLoggingDTO emrArchiveLoggingDTO) {
		PageHelper.startPage(emrArchiveLoggingDTO.getPageNo(), emrArchiveLoggingDTO.getPageSize());
		if (emrArchiveLoggingDTO != null && "1".equals(emrArchiveLoggingDTO.getArchiveState())) {
			// 查询已经归档的所有病人
			return PageDTO.of(emrArchiveLoggingDAO.getZYEmrFilePatientsGd(emrArchiveLoggingDTO));
		} else {
			// 查询未归档的所有病人信息
			return PageDTO.of(emrArchiveLoggingDAO.getZYEmrFilePatients(emrArchiveLoggingDTO));
		}
	}

	/**
	 * @Description: 病人出院7天自动归档记录
	 * @Param:
	 * @Author: liuliyun
	 * @Email: liyun.liu@powersi.com
	 * @Date 2021/06/25 11:32
	 * @Return
	 */
	@Override
	public boolean insertOutHospEmrArchiveLogging(Map param) {
		// 取出就诊id集合
		List<EmrArchiveLoggingDTO> visitIdList = emrArchiveLoggingDAO.getZYEmrFilePatientsNoArchived(param);
		StringBuilder visitIds = new StringBuilder();
		List<EmrArchiveLoggingDTO> archiveLoggingDTOList = new ArrayList<>();
		if (visitIdList != null && visitIdList.size() > 0) {
			for (EmrArchiveLoggingDTO archiveLoggingDTO : visitIdList) {
				visitIds.append("'");
				visitIds.append(archiveLoggingDTO.getVisitId());
				visitIds.append("',");
				// 准备归档数据
				EmrArchiveLoggingDTO dto = new EmrArchiveLoggingDTO();
				dto.setId(SnowflakeUtils.getId());
				dto.setVisitId(archiveLoggingDTO.getVisitId());
				dto.setArchiveUserId("-1");
				dto.setArchiveName("自动执行");
				dto.setArchiveOption("电子病历自动归档");
				dto.setArchiveState("1");
				dto.setArchiveTime(DateUtils.getNow());
				archiveLoggingDTOList.add(dto);
			}
			if (visitIds.length() > 0 && !"".equals(visitIds.toString())) {
				visitIds.deleteCharAt(visitIds.length()-1);
			}
		} else {
			// 没有病人不用归档
			return  true;
		}
		// 查询当前传入的就诊id集合中，存在需要送审病历，但审核标识为未通过
		List<EmrArchiveLoggingDTO> list = emrArchiveLoggingDAO.getPatientIsArchive(visitIds.toString());
		List<EmrArchiveLoggingDTO> haveGuiDangList = emrArchiveLoggingDAO.getIsArchives(visitIds.toString());
		if (list != null && list.size() > 0) {
			return true;
		} else if (haveGuiDangList != null && haveGuiDangList.size() > 0) {
			return true;
		} else {
			// 归档前，先删除之前的归档数据
			emrArchiveLoggingDAO.deleteArchives(visitIds.toString());
			// 执行批量新增操作，批量归档
			return emrArchiveLoggingDAO.insertEmrArchiveLoggingDO(archiveLoggingDTOList);
		}
	}
}
