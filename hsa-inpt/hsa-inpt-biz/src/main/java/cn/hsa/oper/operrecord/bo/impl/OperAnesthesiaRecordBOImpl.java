package cn.hsa.oper.operrecord.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.oper.operrecord.bo.OperAnesthesiaRecordBO;
import cn.hsa.module.oper.operrecord.dao.OperAnesthesiaRecordDAO;
import cn.hsa.module.oper.operrecord.dto.OperAnesthesiaDurgDTO;
import cn.hsa.module.oper.operrecord.dto.OperAnesthesiaMonitorDTO;
import cn.hsa.module.oper.operrecord.dto.OperAnesthesiaRecordDTO;
import cn.hsa.util.SnowflakeUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.oper.operrecord.bo.impl
 * @Class_name: OperAnesthesiaRecordBOImpl
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/12/22 20:48
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class OperAnesthesiaRecordBOImpl implements OperAnesthesiaRecordBO {

  @Resource
  private OperAnesthesiaRecordDAO operAnesthesiaRecordDAO;

  /**
  * @Menthod getOperAnesthesiaRecordById
  * @Desrciption 根据id查询麻醉记录
  *
  * @Param
  * [operAnesthesiaRecord]
  *
  * @Author jiahong.yang
  * @Date   2020/12/22 20:53
  * @Return cn.hsa.module.oper.operrecord.dto.OperAnesthesiaRecordDTO
  **/
  @Override
  public OperAnesthesiaRecordDTO getOperAnesthesiaRecordById(OperAnesthesiaRecordDTO operAnesthesiaRecord) {
    OperAnesthesiaRecordDTO operAnesthesiaRecordById = operAnesthesiaRecordDAO.getOperAnesthesiaRecordById(operAnesthesiaRecord);
    if(operAnesthesiaRecordById == null){
      return new OperAnesthesiaRecordDTO();
    }
    OperAnesthesiaDurgDTO operAnesthesiaDurgDTO = new OperAnesthesiaDurgDTO();
    OperAnesthesiaMonitorDTO operAnesthesiaMonitorDTO = new OperAnesthesiaMonitorDTO();
    operAnesthesiaDurgDTO.setHospCode(operAnesthesiaRecordById.getHospCode());
    operAnesthesiaDurgDTO.setAnesthesiaId(operAnesthesiaRecordById.getId());
    operAnesthesiaMonitorDTO.setHospCode(operAnesthesiaRecordById.getHospCode());
    operAnesthesiaMonitorDTO.setAnesthesiaId(operAnesthesiaRecordById.getId());
    List<OperAnesthesiaDurgDTO> operAnesthesiaDurgDTOS = operAnesthesiaRecordDAO.queryOperAnesthesiaDurgAll(operAnesthesiaDurgDTO);
    List<OperAnesthesiaMonitorDTO> operAnesthesiaMonitorDTOS = operAnesthesiaRecordDAO.queryOperAnesthesiaMonitorAll(operAnesthesiaMonitorDTO);
    operAnesthesiaRecordById.setOperAnesthesiaDurgDTOS(operAnesthesiaDurgDTOS);
    operAnesthesiaRecordById.setOperAnesthesiaMonitorDTOS(operAnesthesiaMonitorDTOS);
    return operAnesthesiaRecordById;
  }

  /**
  * @Menthod queryOperAnesthesiaRecordPage
  * @Desrciption 分页查询病人麻醉记录单
  *
  * @Param
  * [operAnesthesiaRecordDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/12/23 14:27
  * @Return cn.hsa.base.PageDTO
  **/
  @Override
  public PageDTO queryOperAnesthesiaRecordPage(OperAnesthesiaRecordDTO operAnesthesiaRecordDTO) {
    PageHelper.startPage(operAnesthesiaRecordDTO.getPageNo(), operAnesthesiaRecordDTO.getPageSize());
    List<OperAnesthesiaRecordDTO> operAnesthesiaRecordDTOS = operAnesthesiaRecordDAO.queryOperAnesthesiaRecordAll(operAnesthesiaRecordDTO);
    for (int i = 0; i < operAnesthesiaRecordDTOS.size(); i++) {
      OperAnesthesiaDurgDTO operAnesthesiaDurgDTO = new OperAnesthesiaDurgDTO();
      OperAnesthesiaMonitorDTO operAnesthesiaMonitorDTO = new OperAnesthesiaMonitorDTO();
      operAnesthesiaDurgDTO.setHospCode(operAnesthesiaRecordDTOS.get(i).getHospCode());
      operAnesthesiaDurgDTO.setAnesthesiaId(operAnesthesiaRecordDTOS.get(i).getId());
      operAnesthesiaMonitorDTO.setHospCode(operAnesthesiaRecordDTOS.get(i).getHospCode());
      operAnesthesiaMonitorDTO.setAnesthesiaId(operAnesthesiaRecordDTOS.get(i).getId());
      List<OperAnesthesiaDurgDTO> operAnesthesiaDurgDTOS = operAnesthesiaRecordDAO.queryOperAnesthesiaDurgAll(operAnesthesiaDurgDTO);
      List<OperAnesthesiaMonitorDTO> operAnesthesiaMonitorDTOS = operAnesthesiaRecordDAO.queryOperAnesthesiaMonitorAll(operAnesthesiaMonitorDTO);
      operAnesthesiaRecordDTOS.get(i).setOperAnesthesiaMonitorDTOS(operAnesthesiaMonitorDTOS);
      operAnesthesiaRecordDTOS.get(i).setOperAnesthesiaDurgDTOS(operAnesthesiaDurgDTOS);
    }
    return PageDTO.of(operAnesthesiaRecordDTOS);
  }

  /**
  * @Menthod insertOperAnesthesiaRecord
  * @Desrciption 新增麻醉记录单
  *
  * @Param
  * [operAnesthesiaRecord]
  *
  * @Author jiahong.yang
  * @Date   2020/12/22 21:18
  * @Return java.lang.Boolean
  **/
  @Override
  public Boolean insertOperAnesthesiaRecord(OperAnesthesiaRecordDTO operAnesthesiaRecord) {
    OperAnesthesiaRecordDTO recordById = operAnesthesiaRecordDAO.getOperAnesthesiaRecordById(operAnesthesiaRecord);
    if (recordById != null){
      throw new AppException("该手术记录已存在手术麻醉记录单了，请勿重复操作！");
    }
    List<OperAnesthesiaDurgDTO> operAnesthesiaDurgDTOS = operAnesthesiaRecord.getOperAnesthesiaDurgDTOS();
    List<OperAnesthesiaMonitorDTO> operAnesthesiaMonitorDTOS = operAnesthesiaRecord.getOperAnesthesiaMonitorDTOS();
    operAnesthesiaRecord.setId(SnowflakeUtils.getId());
    for (int i = 0; i < operAnesthesiaDurgDTOS.size(); i++) {
      //添加医院编码
      operAnesthesiaDurgDTOS.get(i).setHospCode(operAnesthesiaRecord.getHospCode());
      // 添加麻醉记录id
      operAnesthesiaDurgDTOS.get(i).setAnesthesiaId(operAnesthesiaRecord.getId());
      // 添加主键
      operAnesthesiaDurgDTOS.get(i).setId(SnowflakeUtils.getId());
      // 创建人id
      operAnesthesiaDurgDTOS.get(i).setCrteId(operAnesthesiaRecord.getCrteId());
      // 创建人姓名
      operAnesthesiaDurgDTOS.get(i).setCrteName(operAnesthesiaRecord.getCrteName());
      // 创建时间
      operAnesthesiaDurgDTOS.get(i).setCrteTime(operAnesthesiaRecord.getCrteTime());
    }
    for (int i = 0; i < operAnesthesiaMonitorDTOS.size(); i++) {
      //添加医院编码
      operAnesthesiaMonitorDTOS.get(i).setHospCode(operAnesthesiaRecord.getHospCode());
      // 添加麻醉记录id
      operAnesthesiaMonitorDTOS.get(i).setAnesthesiaId(operAnesthesiaRecord.getId());
      // 添加主键
      operAnesthesiaMonitorDTOS.get(i).setId(SnowflakeUtils.getId());
      // 创建人id
      operAnesthesiaMonitorDTOS.get(i).setCrteId(operAnesthesiaRecord.getCrteId());
      // 创建人姓名
      operAnesthesiaMonitorDTOS.get(i).setCrteName(operAnesthesiaRecord.getCrteName());
      // 创建时间
      operAnesthesiaMonitorDTOS.get(i).setCrteTime(operAnesthesiaRecord.getCrteTime());
    }
    operAnesthesiaRecordDAO.insertOperAnesthesiaDurg(operAnesthesiaDurgDTOS);
    operAnesthesiaRecordDAO.insertOperAnesthesiaMonitor(operAnesthesiaMonitorDTOS);
    return operAnesthesiaRecordDAO.insertOperAnesthesiaRecord(operAnesthesiaRecord)>0;
  }

  /**
  * @Menthod updateOperAnesthesiaRecord
  * @Desrciption  修改麻醉记录单
  *
  * @Param
  * [operAnesthesiaRecord]
  *
  * @Author jiahong.yang
  * @Date   2020/12/22 21:18
  * @Return java.lang.Boolean
  **/
  @Override
  public Boolean updateOperAnesthesiaRecord(OperAnesthesiaRecordDTO operAnesthesiaRecord) {
    List<OperAnesthesiaDurgDTO> operAnesthesiaDurgDTOS = operAnesthesiaRecord.getOperAnesthesiaDurgDTOS();
    List<OperAnesthesiaMonitorDTO> operAnesthesiaMonitorDTOS = operAnesthesiaRecord.getOperAnesthesiaMonitorDTOS();
    for (int i = 0; i < operAnesthesiaDurgDTOS.size(); i++) {
      //修改人id
      operAnesthesiaDurgDTOS.get(i).setUpdtId(operAnesthesiaRecord.getUpdtId());
      // 修改人姓名
      operAnesthesiaDurgDTOS.get(i).setUpdtName(operAnesthesiaRecord.getUpdtName());
      // 修改时间
      operAnesthesiaDurgDTOS.get(i).setUpdtTime(operAnesthesiaRecord.getUpdtTime());
    }
    for (int i = 0; i < operAnesthesiaMonitorDTOS.size(); i++) {
      //修改人id
      operAnesthesiaMonitorDTOS.get(i).setUpdtId(operAnesthesiaRecord.getUpdtId());
      // 修改人姓名
      operAnesthesiaMonitorDTOS.get(i).setUpdtName(operAnesthesiaRecord.getUpdtName());
      // 修改时间
      operAnesthesiaMonitorDTOS.get(i).setUpdtTime(operAnesthesiaRecord.getUpdtTime());
    }
    operAnesthesiaRecordDAO.updateOperAnesthesiaDurg(operAnesthesiaDurgDTOS);
    operAnesthesiaRecordDAO.updateOperAnesthesiaMonitor(operAnesthesiaMonitorDTOS);
    return operAnesthesiaRecordDAO.updateOperAnesthesiaRecord(operAnesthesiaRecord) > 0;
  }

  @Override
  public PageDTO queryOperPatientPage(OperInfoRecordDTO operInfoRecordDTO) {
    return null;
  }

  @Override
  public Boolean deleteOperAnesthesiaRecordById(OperAnesthesiaRecordDTO operAnesthesiaRecord) {
    return null;
  }

  @Override
  public OperAnesthesiaDurgDTO getOperAnesthesiaDurgById(OperAnesthesiaDurgDTO operAnesthesiaDurg) {
    return null;
  }

  @Override
  public Boolean insertOperAnesthesiaDurg(OperAnesthesiaDurgDTO operAnesthesiaDurg) {
    return null;
  }

  @Override
  public Boolean updateOperAnesthesiaDurg(OperAnesthesiaDurgDTO operAnesthesiaDurg) {
    return null;
  }

  @Override
  public Boolean deleteOperAnesthesiaDurgById(OperAnesthesiaDurgDTO operAnesthesiaDurg) {
    return null;
  }

  @Override
  public OperAnesthesiaMonitorDTO getOperAnesthesiaMonitorById(OperAnesthesiaMonitorDTO operAnesthesiaMonitor) {
    return null;
  }

  @Override
  public Boolean insertOperAnesthesiaMonitor(OperAnesthesiaMonitorDTO operAnesthesiaMonitor) {
    return null;
  }

  @Override
  public Boolean updateOperAnesthesiaMonitor(OperAnesthesiaMonitorDTO operAnesthesiaMonitor) {
    return null;
  }

  @Override
  public Boolean deleteOperAnesthesiaMonitorById(OperAnesthesiaMonitorDTO operAnesthesiaMonitor) {
    return null;
  }
}
