package cn.hsa.sync.syncemrprintsetting.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.center.syncorderrule.service.SyncOrderRuleService;
import cn.hsa.module.sync.syncemrprintsetting.bo.SyncEmrPrintSettingBO;
import cn.hsa.module.sync.syncemrprintsetting.dao.SyncEmrPrintSettingDAO;
import cn.hsa.module.sync.syncemrprintsetting.dto.SyncEmrPrintSettingDTO;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.emr.emrprintsetting.bo.impl
 * @Class_name: EmrPrintSettingBOImpl
 * @Describe: 电子病历打印设置业务实现层
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2020/9/18 13:42
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class SyncEmrPrintSettingBOImpl extends HsafBO implements SyncEmrPrintSettingBO {

  /**
   * 电子病历打印设置访问接口
   */
  @Resource
  private SyncEmrPrintSettingDAO syncEmrPrintSettingDAO;

  /**
   * 单据消费者
   */
  @Resource
  private SyncOrderRuleService syncOrderRuleService;

  /**
  * @Menthod getById
  * @Desrciption 根据id查询单个电子病历打印设置
  *
  * @Param
  * [syncEmrPrintSettingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 14:09
  * @Return cn.hsa.module.emr.emrprintsetting.dto.SyncEmrPrintSettingDTO
  **/
  @Override
  public SyncEmrPrintSettingDTO getById(SyncEmrPrintSettingDTO syncEmrPrintSettingDTO) {
    return syncEmrPrintSettingDAO.getById(syncEmrPrintSettingDTO);
  }

  /**
  * @Menthod queryPage
  * @Desrciption 分页查询电子病历打印设置
  *
  * @Param
  * [syncEmrPrintSettingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 14:12
  * @Return cn.hsa.base.PageDTO
  **/
  @Override
  public PageDTO queryPage(SyncEmrPrintSettingDTO syncEmrPrintSettingDTO) {
    // 设置分页信息
    PageHelper.startPage(syncEmrPrintSettingDTO.getPageNo(), syncEmrPrintSettingDTO.getPageSize());
    // 根据条件查询所
    List<SyncEmrPrintSettingDTO> syncEmrPrintSettingDTOS = syncEmrPrintSettingDAO.queryPageorAll(syncEmrPrintSettingDTO);

    return PageDTO.of(syncEmrPrintSettingDTOS);
  }

  /**
  * @Menthod queryAll
  * @Desrciption 查询所有电子病历打印设置
  *
  * @Param
  * [syncEmrPrintSettingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 14:12
  * @Return java.util.List<cn.hsa.module.emr.emrprintsetting.dto.SyncEmrPrintSettingDTO>
  **/
  @Override
  public List<SyncEmrPrintSettingDTO> queryAll(SyncEmrPrintSettingDTO syncEmrPrintSettingDTO) {
    List<SyncEmrPrintSettingDTO> syncEmrPrintSettingDTOS = syncEmrPrintSettingDAO.queryPageorAll(syncEmrPrintSettingDTO);
    return syncEmrPrintSettingDTOS;
  }

  /**
  * @Menthod save
  * @Desrciption  保存电子病历打印设置
  *
  * @Param
  * [syncEmrPrintSettingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 14:14
  * @Return boolean
  **/
  @Override
  public boolean save(SyncEmrPrintSettingDTO syncEmrPrintSettingDTO) {
    if(StringUtils.isEmpty(syncEmrPrintSettingDTO.getId())){
      //如果id为空则为新增数据
      return insert(syncEmrPrintSettingDTO);
    } else {
      //不为空即为修改数据
      return update(syncEmrPrintSettingDTO);
    }
  }

  /**
  * @Menthod insert
  * @Desrciption 新增电子病历打印设置
  *
  * @Param
  * [syncEmrPrintSettingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 14:59
  * @Return java.lang.Boolean
  **/
  public Boolean insert(SyncEmrPrintSettingDTO syncEmrPrintSettingDTO){
    syncEmrPrintSettingDTO.setId(SnowflakeUtils.getId());
    String orderNo = syncOrderRuleService.getOrderNo("10").getData();
    syncEmrPrintSettingDTO.setCode(orderNo);
    return syncEmrPrintSettingDAO.insert(syncEmrPrintSettingDTO) > 0;
  }

  /**
  * @Menthod update
  * @Desrciption 修改电子病历打印设置
  *
  * @Param
  * [syncEmrPrintSettingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 14:59
  * @Return java.lang.Boolean
  **/
  public Boolean update(SyncEmrPrintSettingDTO syncEmrPrintSettingDTO){
    return syncEmrPrintSettingDAO.update(syncEmrPrintSettingDTO) > 0;
  }
  /**
  * @Menthod updateStatus
  * @Desrciption 修改电子病历打印设置有效状态
  *
  * @Param
  * [syncEmrPrintSettingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 14:14
  * @Return boolean
  **/
  @Override
  public boolean updateStatus(SyncEmrPrintSettingDTO syncEmrPrintSettingDTO) {
    return syncEmrPrintSettingDAO.updateStatus(syncEmrPrintSettingDTO) > 0;
  }
}
