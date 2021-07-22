package cn.hsa.center.nationstandarddrug.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.nationstandarddrug.bo.NationStandardDrugBO;
import cn.hsa.module.center.nationstandarddrug.bo.NationStandardDrugZYBO;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugZYDTO;
import cn.hsa.module.center.nationstandarddrug.service.NationStandardDrugService;
import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.center.nationstandarddrug.service.impl
 * @Class_name: NationStandardDrugServiceImpl
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/1/26 9:45
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@HsafRestPath("/service/center/nationstandarddrug")
@Slf4j
@Service("nationStandardDrugService_provider")
public class NationStandardDrugServiceImpl extends HsafService implements NationStandardDrugService {

  @Resource
  private NationStandardDrugBO nationStandardDrugBO;

  @Resource
  private NationStandardDrugZYBO nationStandardDrugZYBO;
  /**
  * @Menthod queryNationStandardDrugPage
  * @Desrciption 分页查询所有国家标准药品
  *
  * @Param
  * [nationStandardDrugDTO]
  *
  * @Author jiahong.yang
  * @Date   2021/1/26 9:46
  * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO>>
  **/
  @Override
  public WrapperResponse<PageDTO> queryNationStandardDrugPage(NationStandardDrugDTO nationStandardDrugDTO) {
    return WrapperResponse.success(nationStandardDrugBO.queryNationStandardDrugPage(nationStandardDrugDTO));
  }

  /**
     * @Describe: 根据id查询国家标准药品信息
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/4/27 11:24
  **/
  @Override
  public WrapperResponse<NationStandardDrugDTO> getById(NationStandardDrugDTO nationStandardDrugDTO) {
    return WrapperResponse.success(nationStandardDrugBO.getById(nationStandardDrugDTO));
  }

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

  @Override
  public void insertProcessedUploadData(Map<String, Object> dataMap) {
      nationStandardDrugBO.saveProcessedUploadDataBatch(dataMap);
  }
}
