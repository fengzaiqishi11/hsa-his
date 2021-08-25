package cn.hsa.module.oper.operrecord.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.oper.operrecord.dto.OperAnesthesiaDurgDTO;
import cn.hsa.module.oper.operrecord.dto.OperAnesthesiaMonitorDTO;
import cn.hsa.module.oper.operrecord.dto.OperAnesthesiaRecordDTO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.oper.operrecord.bo
 * @Class_name: OperAnesthesiaRecordBO
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/12/21 21:59
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface OperAnesthesiaRecordBO {

  OperAnesthesiaRecordDTO getOperAnesthesiaRecordById(OperAnesthesiaRecordDTO operAnesthesiaRecord);

  /**
  * @Menthod q
  * @Desrciption 分页查询病人麻醉记录单
  *
  * @Param
  * [operAnesthesiaRecordDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/12/23 14:22
  * @Return java.util.List<cn.hsa.module.oper.operrecord.dto.OperAnesthesiaRecordDTO>
  **/
  PageDTO queryOperAnesthesiaRecordPage(OperAnesthesiaRecordDTO operAnesthesiaRecordDTO);

  /**
   * 新增数据
   *
   * @param operAnesthesiaRecord 实例对象
   * @return 实例对象
   */
  Boolean insertOperAnesthesiaRecord(OperAnesthesiaRecordDTO operAnesthesiaRecord);

  /**
   * 修改数据
   *
   * @param operAnesthesiaRecord 实例对象
   * @return 实例对象
   */
  Boolean updateOperAnesthesiaRecord(OperAnesthesiaRecordDTO operAnesthesiaRecord);

  /**
  * @Menthod queryOperPatientPage
  * @Desrciption
  *
  * @Param
  * [operInfoRecordDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/12/24 14:39
  * @Return cn.hsa.base.PageDTO
  **/
  PageDTO queryOperPatientPage(OperInfoRecordDTO operInfoRecordDTO);


  Boolean deleteOperAnesthesiaRecordById(OperAnesthesiaRecordDTO operAnesthesiaRecord);


  OperAnesthesiaDurgDTO getOperAnesthesiaDurgById(OperAnesthesiaDurgDTO operAnesthesiaDurg);


  /**
   * 新增数据
   *
   * @param operAnesthesiaDurg 实例对象
   * @return 实例对象
   */
  Boolean insertOperAnesthesiaDurg(OperAnesthesiaDurgDTO operAnesthesiaDurg);

  /**
   * 修改数据
   *
   * @param operAnesthesiaDurg 实例对象
   * @return 实例对象
   */
  Boolean updateOperAnesthesiaDurg(OperAnesthesiaDurgDTO operAnesthesiaDurg);

  Boolean deleteOperAnesthesiaDurgById(OperAnesthesiaDurgDTO operAnesthesiaDurg);


  OperAnesthesiaMonitorDTO getOperAnesthesiaMonitorById(OperAnesthesiaMonitorDTO operAnesthesiaMonitor);

  /**
   * 新增数据
   *
   * @param operAnesthesiaMonitor 实例对象
   * @return 实例对象
   */
  Boolean insertOperAnesthesiaMonitor(OperAnesthesiaMonitorDTO operAnesthesiaMonitor);

  /**
   * 修改数据
   *
   * @param operAnesthesiaMonitor 实例对象
   * @return 实例对象
   */
  Boolean updateOperAnesthesiaMonitor(OperAnesthesiaMonitorDTO operAnesthesiaMonitor);

  Boolean deleteOperAnesthesiaMonitorById(OperAnesthesiaMonitorDTO operAnesthesiaMonitor);
}
