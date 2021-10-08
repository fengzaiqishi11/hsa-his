package cn.hsa.emr.emrqualityaging.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.emr.emrquality.bo.EmrQualityAgingBO;
import cn.hsa.module.emr.emrquality.dto.EmrQualityAgingDTO;
import cn.hsa.module.emr.emrquality.service.EmrQualityAgingService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.emr.emrqualityaging.service.impl
 * @Class_name: EmrQualityAgingServiceImpl
 * @Describe: 电子病历质控业务传输实现层
 * @Author: liuliyun
 * @Email: liuyun.liu@powersi.com
 * @Date: 2021/9/24 10:44
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/emr/emrqualityaging")
@Slf4j
@Service("emrQualityAgingService_provider")
public class EmrQualityAgingServiceImpl extends HsafService implements EmrQualityAgingService {
  @Resource
  private EmrQualityAgingBO emrQualityAgingBO;

  /**
   * @Menthod insertEmrQualityAging
   * @Desrciption 新增时效质控记录
   * @param map
   * @Author liuliyun
   * @Date   2021/9/24 11:47
   * @Return boolean
   **/
  @Override
  public WrapperResponse<Boolean> insertEmrQualityAging(Map map) {
         EmrQualityAgingDTO emrQualityAgingDTO = MapUtils.get(map,"emrQualityAgingDTO");
    return WrapperResponse.success(emrQualityAgingBO.insertEmrQualityAging(emrQualityAgingDTO));
  }

  /**
   * @Menthod updateEmrQualityAging
   * @Desrciption  更新时效质控记录
   * @param map
   * @Author liuliyun
   * @Date   2021/9/24 11:48
   * @Return boolean
   **/
  @Override
  public WrapperResponse<Boolean> updateEmrQualityAging(Map map) {
    EmrQualityAgingDTO emrQualityAgingDTO = MapUtils.get(map,"emrQualityAgingDTO");
    return WrapperResponse.success(emrQualityAgingBO.updateEmrQualityAging(emrQualityAgingDTO));
  }

  /**
   * @Menthod deleteEmrQualityAging
   * @Desrciption  删除时效质控记录
   * @param map
   * @Author liuliyun
   * @Date   2021/9/24 11:48
   * @Return boolean
   **/
  @Override
  public WrapperResponse<Boolean> deleteEmrQualityAging(Map map) {
    return WrapperResponse.success(emrQualityAgingBO.deleteEmrQualityAging(map));
  }

  /**
   * @Menthod queryEmrTemplateList
   * @Desrciption  查询病历模板list
   * @param map
   * @Author liuliyun
   * @Date   2021/9/24 11:49
   * @Return List<Map>
   **/
  @Override
  public WrapperResponse<List<Map>> queryEmrTemplateList(Map map) {
    return WrapperResponse.success(emrQualityAgingBO.queryEmrTemplateList(map));
  }

  /**
   * @Menthod queryEmrQualityList
   * @Desrciption  查询病历时效质控list
   * @param map
   * @Author liuliyun
   * @Date   2021/9/24 13:33
   * @Return List<Map>
   **/
  @Override
  public WrapperResponse<List<EmrQualityAgingDTO>> queryEmrQualityList(Map map) {
    return WrapperResponse.success(emrQualityAgingBO.queryEmrQualityList(map));
  }

  @Override
  public WrapperResponse<List<EmrQualityAgingDTO>> queryEmrQualityListById(Map map) {
    return WrapperResponse.success(emrQualityAgingBO.queryEmrQualityListById(map));
  }
}
