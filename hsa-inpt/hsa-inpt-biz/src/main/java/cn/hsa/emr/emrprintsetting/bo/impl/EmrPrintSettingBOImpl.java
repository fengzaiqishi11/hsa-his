package cn.hsa.emr.emrprintsetting.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.bor.service.BaseOrderRuleService;
import cn.hsa.module.emr.emrprintsetting.bo.EmrPrintSettingBO;
import cn.hsa.module.emr.emrprintsetting.dao.EmrPrintSettingDAO;
import cn.hsa.module.emr.emrprintsetting.dto.EmrPrintSettingDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.SnowflakeUtils;
import cn.hsa.util.StringUtils;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
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
public class EmrPrintSettingBOImpl extends HsafBO implements EmrPrintSettingBO {

  /**
   * 电子病历打印设置访问接口
   */
  @Resource
  private EmrPrintSettingDAO emrPrintSettingDAO;

  /**
   * 注入单据规则service层
   */
  @Resource
  private BaseOrderRuleService baseOrderRuleService;

  /**
  * @Menthod getById
  * @Desrciption 根据id查询单个电子病历打印设置
  *
  * @Param
  * [emrPrintSettingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 14:09
  * @Return cn.hsa.module.emr.emrprintsetting.dto.EmrPrintSettingDTO
  **/
  @Override
  public EmrPrintSettingDTO getById(EmrPrintSettingDTO emrPrintSettingDTO) {
    return emrPrintSettingDAO.getById(emrPrintSettingDTO);
  }

  /**
  * @Menthod queryPage
  * @Desrciption 分页查询电子病历打印设置
  *
  * @Param
  * [emrPrintSettingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 14:12
  * @Return cn.hsa.base.PageDTO
  **/
  @Override
  public PageDTO queryPage(EmrPrintSettingDTO emrPrintSettingDTO) {
    // 设置分页信息
    PageHelper.startPage(emrPrintSettingDTO.getPageNo(), emrPrintSettingDTO.getPageSize());
    // 根据条件查询所
    List<EmrPrintSettingDTO> emrPrintSettingDTOS = emrPrintSettingDAO.queryPageorAll(emrPrintSettingDTO);

    return PageDTO.of(emrPrintSettingDTOS);
  }

  /**
  * @Menthod queryAll
  * @Desrciption 查询所有电子病历打印设置
  *
  * @Param
  * [emrPrintSettingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 14:12
  * @Return java.util.List<cn.hsa.module.emr.emrprintsetting.dto.EmrPrintSettingDTO>
  **/
  @Override
  public List<EmrPrintSettingDTO> queryAll(EmrPrintSettingDTO emrPrintSettingDTO) {
    List<EmrPrintSettingDTO> emrPrintSettingDTOS = emrPrintSettingDAO.queryPageorAll(emrPrintSettingDTO);
    return emrPrintSettingDTOS;
  }

  /**
  * @Menthod save
  * @Desrciption  保存电子病历打印设置
  *
  * @Param
  * [emrPrintSettingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 14:14
  * @Return boolean
  **/
  @Override
  public boolean save(EmrPrintSettingDTO emrPrintSettingDTO) {
    if(StringUtils.isEmpty(emrPrintSettingDTO.getId())){
      //如果id为空则为新增数据
      return insert(emrPrintSettingDTO);
    } else {
      //不为空即为修改数据
      return update(emrPrintSettingDTO);
    }
  }

  /**
  * @Menthod insert
  * @Desrciption 新增电子病历打印设置
  *
  * @Param
  * [emrPrintSettingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 14:59
  * @Return java.lang.Boolean
  **/
  public Boolean insert(EmrPrintSettingDTO emrPrintSettingDTO){
    emrPrintSettingDTO.setId(SnowflakeUtils.getId());
    //获取编码
    HashMap map = new HashMap();
    map.put("hospCode",emrPrintSettingDTO.getHospCode());
    map.put("typeCode","38");
    WrapperResponse<String> orderNo = baseOrderRuleService.getOrderNo(map);
    emrPrintSettingDTO.setCode(orderNo.getData());
    emrPrintSettingDTO.setCrteTime(DateUtils.getNow());
    return emrPrintSettingDAO.insert(emrPrintSettingDTO) > 0;
  }

  /**
  * @Menthod update
  * @Desrciption 修改电子病历打印设置
  *
  * @Param
  * [emrPrintSettingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 14:59
  * @Return java.lang.Boolean
  **/
  public Boolean update(EmrPrintSettingDTO emrPrintSettingDTO){
    return emrPrintSettingDAO.update(emrPrintSettingDTO) > 0;
  }
  /**
  * @Menthod updateStatus
  * @Desrciption 修改电子病历打印设置有效状态
  *
  * @Param
  * [emrPrintSettingDTO]
  *
  * @Author jiahong.yang
  * @Date   2020/9/18 14:14
  * @Return boolean
  **/
  @Override
  public boolean updateStatus(EmrPrintSettingDTO emrPrintSettingDTO) {
    return emrPrintSettingDAO.updateStatus(emrPrintSettingDTO) > 0;
  }
}
