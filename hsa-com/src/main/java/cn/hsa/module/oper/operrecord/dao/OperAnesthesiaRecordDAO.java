package cn.hsa.module.oper.operrecord.dao;

import cn.hsa.module.oper.operrecord.dto.OperAnesthesiaDurgDTO;
import cn.hsa.module.oper.operrecord.dto.OperAnesthesiaMonitorDTO;
import cn.hsa.module.oper.operrecord.dto.OperAnesthesiaRecordDTO;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (OperAnesthesiaRecordDTO)表数据库访问层
 *
 * @author makejava
 * @since 2020-12-21 21:16:03
 */
public interface OperAnesthesiaRecordDAO {


    OperAnesthesiaRecordDTO getOperAnesthesiaRecordById(OperAnesthesiaRecordDTO operAnesthesiaRecord);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param operAnesthesiaRecord 实例对象
     * @return 对象列表
     */
    List<OperAnesthesiaRecordDTO> queryOperAnesthesiaRecordAll(OperAnesthesiaRecordDTO operAnesthesiaRecord);

    /**
     * 新增数据
     *
     * @param operAnesthesiaRecord 实例对象
     * @return 影响行数
     */
    int insertOperAnesthesiaRecord(OperAnesthesiaRecordDTO operAnesthesiaRecord);

    /**
     * 修改数据
     *
     * @param operAnesthesiaRecord 实例对象
     * @return 影响行数
     */
    int updateOperAnesthesiaRecord(OperAnesthesiaRecordDTO operAnesthesiaRecord);


    int deleteOperAnesthesiaRecordById(OperAnesthesiaDurgDTO operAnesthesiaDurg);

  /**
   * @Menthod getById
   * @Desrciption  查询单个麻醉用药记录
   *
   * @Param
   * [id]
   *
   * @Author jiahong.yang
   * @Date   2020/12/21 21:34
   * @Return cn.hsa.module.oper.operrecord.dto.OperAnesthesiaDurgDTO
   **/
  OperAnesthesiaDurgDTO getOperAnesthesiaDurgById(OperAnesthesiaDurgDTO operAnesthesiaDurg);


  /**
   * @Menthod queryAll
   * @Desrciption 查询所有麻醉用药记录
   *
   * @Param
   * [operAnesthesiaDurg]
   *
   * @Author jiahong.yang
   * @Date   2020/12/21 21:34
   * @Return java.util.List<cn.hsa.module.oper.operrecord.dto.OperAnesthesiaDurgDTO>
   **/
  List<OperAnesthesiaDurgDTO> queryOperAnesthesiaDurgAll(OperAnesthesiaDurgDTO operAnesthesiaDurg);

  /**
   * @Menthod insert
   * @Desrciption 新增麻醉用药记录
   *
   * @Param
   * [operAnesthesiaDurg]
   *
   * @Author jiahong.yang
   * @Date   2020/12/21 21:35
   * @Return int
   **/
  int insertOperAnesthesiaDurg(List<OperAnesthesiaDurgDTO> operAnesthesiaDurgDTOS);

  /**
   * @Menthod update
   * @Desrciption 修改麻醉用药记录
   *
   * @Param
   * [operAnesthesiaDurg]
   *
   * @Author jiahong.yang
   * @Date   2020/12/21 21:35
   * @Return int
   **/
  int updateOperAnesthesiaDurg(List<OperAnesthesiaDurgDTO> operAnesthesiaDurgDTOS);

  /**
   * @Menthod deleteById
   * @Desrciption 删除麻醉用药记录
   *
   * @Param
   * [id]
   *
   * @Author jiahong.yang
   * @Date   2020/12/21 21:35
   * @Return int
   **/
  int deleteOperAnesthesiaDurgById(OperAnesthesiaMonitorDTO operAnesthesiaMonitor);


  OperAnesthesiaMonitorDTO getOperAnesthesiaMonitorById(OperAnesthesiaMonitorDTO operAnesthesiaMonitor);


  /**
   * 通过实体作为筛选条件查询
   *
   * @param operAnesthesiaMonitor 实例对象
   * @return 对象列表
   */
  List<OperAnesthesiaMonitorDTO> queryOperAnesthesiaMonitorAll(OperAnesthesiaMonitorDTO operAnesthesiaMonitor);


  int insertOperAnesthesiaMonitor(List<OperAnesthesiaMonitorDTO> operAnesthesiaMonitorDTOS);


  int updateOperAnesthesiaMonitor(List<OperAnesthesiaMonitorDTO> operAnesthesiaMonitorDTOS);


  int deleteOperAnesthesiaMonitorById(OperAnesthesiaMonitorDTO operAnesthesiaMonitor);

}
