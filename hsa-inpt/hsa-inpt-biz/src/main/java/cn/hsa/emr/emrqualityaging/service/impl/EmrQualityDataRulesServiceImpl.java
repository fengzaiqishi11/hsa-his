package cn.hsa.emr.emrqualityaging.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrquality.bo.EmrQualityDataRulesBO;
import cn.hsa.module.emr.emrquality.dto.EmrQualityDataRulesDTO;
import cn.hsa.module.emr.emrquality.service.EmrQualityDataRulesService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.emr.emrqualityaging.service.impl
 * @Class_name: EmrQualityAgingServiceImpl
 * @Describe: 电子病历数据控制传输实现层
 * @Author: liuliyun
 * @Email: liuyun.liu@powersi.com
 * @Date: 2021/9/26 20:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/emr/emrqualitydatarules")
@Slf4j
@Service("emrQualityDataRulesService_provider")
public class EmrQualityDataRulesServiceImpl extends HsafService implements EmrQualityDataRulesService {
  @Resource
  private EmrQualityDataRulesBO emrQualityDataRulesBO;

  /**
   * @Menthod insertEmrQualityDataRules
   * @Desrciption 新增病历数据控制规则
   * @param map
   * @Author liuliyun
   * @Date   2021/9/26 20：10
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
   **/
  @Override
  public WrapperResponse<Boolean> insertEmrQualityDataRules(Map map) {
    EmrQualityDataRulesDTO emrQualityDataRulesDTO = MapUtils.get(map,"emrQualityDataRulesDTO");
    return WrapperResponse.success(emrQualityDataRulesBO.insertEmrQualityDataRules(emrQualityDataRulesDTO));
  }

  /**
   * @Menthod updateEmrQualityDataRules
   * @Desrciption 更新病历数据控制规则
   * @param map
   * @Author liuliyun
   * @Date   2021/9/26 20：11
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
   **/
  @Override
  public WrapperResponse<Boolean> updateEmrQualityDataRules(Map map) {
    EmrQualityDataRulesDTO emrQualityDataRulesDTO = MapUtils.get(map,"emrQualityDataRulesDTO");
    return WrapperResponse.success(emrQualityDataRulesBO.updateEmrQualityDataRules(emrQualityDataRulesDTO));
  }

  /**
   * @Menthod updateEmrQualityDataInvalid
   * @Desrciption 病历控制规则作废
   * @param map
   * @Author liuliyun
   * @Date   2021/9/26 20:28
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<Boolean>
   **/
  @Override
  public WrapperResponse<Boolean> updateEmrQualityDataInvalid(Map map) {
    return WrapperResponse.success(emrQualityDataRulesBO.updateEmrQualityDataInvalid(map));
  }

  /**
   * @Menthod queryEmrQualityDataRulesList
   * @Desrciption 查询病历数据控制规则列表
   * @param map
   * @Author liuliyun
   * @Date   2021/9/26 20：14
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<PageDTO>
   **/
  @Override
  public WrapperResponse<List<EmrQualityDataRulesDTO>> queryEmrQualityDataRulesList(Map map) {
    EmrQualityDataRulesDTO emrQualityDataRulesDTO = MapUtils.get(map,"emrQualityDataRulesDTO");
    return WrapperResponse.success(emrQualityDataRulesBO.queryEmrQualityDataRulesList(emrQualityDataRulesDTO));
  }

  /**
   * @Menthod queryEmrQualityDataRulesById
   * @Desrciption 根据id查询病历数据控制规则
   * @param map
   * @Author liuliyun
   * @Date   2021/9/26 20：13
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<EmrQualityDataRulesDTO>
   **/
  @Override
  public WrapperResponse<EmrQualityDataRulesDTO> queryEmrQualityDataRulesById(Map map) {
    EmrQualityDataRulesDTO emrQualityDataRulesDTO = MapUtils.get(map,"emrQualityDataRulesDTO");
    return WrapperResponse.success(emrQualityDataRulesBO.queryEmrQualityDataRulesById(emrQualityDataRulesDTO));
  }
}
