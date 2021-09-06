package cn.hsa.center.nationstandarddrug.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.nationstandarddrug.bo.NationStandardDrugBO;
import cn.hsa.module.center.nationstandarddrug.bo.NationStandardDrugZYBO;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugZYDTO;
import cn.hsa.module.center.nationstandarddrug.entity.NationStandardDrugZYDO;
import cn.hsa.module.center.nationstandarddrug.service.NationStandardDrugService;
import cn.hsa.module.center.nationstandarddrug.service.NationStandardDrugZYService;
import cn.hsa.util.MapUtils;
import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: luonianxin
 * @Email: nianxin.luo@powersi.com
 * @Date: 2021/05/08 17:50
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/center/nationstandardZYDrug")
@Slf4j
@Service("nationStandardDrugZYService_provider")
public class NationStandardDrugZYServiceImpl extends HsafService implements NationStandardDrugZYService {

  @Resource
  private NationStandardDrugZYBO nationStandardDrugZYBO;


  /**
   * @Describe: 获取国家标准药品信息（中药）
   * @Author: luonianxin
   * @Date: 2021/4/27 17:43
   **/
  @Override
  public WrapperResponse<PageDTO> queryNationStandardDrugZYPage(NationStandardDrugZYDTO nationStandardDrugZYDTO) {
    return WrapperResponse.success(nationStandardDrugZYBO.queryNationStandardDrugZYPage(nationStandardDrugZYDTO));
  }

  /**
   * @Describe: 分页获取国家标准药品信息（中药）
   * @Author: luonianxin
   * @Email: nianxin.luo@powersi.com
   * @Date: 2021/4/27 17:44
   **/
  @Override
  public WrapperResponse<NationStandardDrugZYDTO> getZYById(NationStandardDrugZYDTO nationStandardDrugZYDTO) {
    return WrapperResponse.success(nationStandardDrugZYBO.getZYById(nationStandardDrugZYDTO));
  }

  /**
   * 保存国家标准中药信息
   *
   * @param map
   * @return 是否成功
   */
  @Override
  public WrapperResponse<Boolean> saveNationStandardDrugZY(Map map) {
    NationStandardDrugZYDO nationStandardDrugZYDO = MapUtils.get(map,"nationStandardDrugZYDO");
    return WrapperResponse.success(nationStandardDrugZYBO.saveNationStandardDrugZY(nationStandardDrugZYDO));
  }

  @Override
  public void insertProcessedUploadData(Map<String, Object> dataMap) {
    nationStandardDrugZYBO.saveProcessedUploadDataBatch(dataMap);
  }
}
