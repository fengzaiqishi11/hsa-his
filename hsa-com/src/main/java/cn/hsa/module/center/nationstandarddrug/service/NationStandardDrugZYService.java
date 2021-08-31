package cn.hsa.module.center.nationstandarddrug.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugZYDTO;
import cn.hsa.util.ExcelResolveService;

import java.util.Map;

/**
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/1/26 9:36
 **/
public interface NationStandardDrugZYService extends ExcelResolveService {

  /**
   * @Describe: 分页查询所有国家标准药品
   * @Author: luonianxin
   * @Email: nianxin.luo@powersi.com
   * @Date: 2021/4/27 17:48
   **/
  WrapperResponse<PageDTO> queryNationStandardDrugZYPage(NationStandardDrugZYDTO nationStandardDrugZYDTO);

  /**
   * @Package_name: cn.hsa.module.center.nationstandarddrug.service
   * @Class_name: NationStandardDrugService
   * @Describe: 根据id查询国家标准药品信息(中药)
   * @Author: luonianxin
   * @Email: nianxin.luo@powersi.com
   * @Date: 2021/4/27 11:22
   * @Company: CopyRight@2014 POWERSI Inc.All RWrapperResponse<NationStandardDrugZYDTO> getZYByIdights Reserverd
   **/
  WrapperResponse<NationStandardDrugZYDTO> getZYById(NationStandardDrugZYDTO nationStandardDrugZYDTO);

  /**
   * 保存国家标准中药信息
   * @param map
   * @return 是否成功
   */
  WrapperResponse<Boolean> saveNationStandardDrugZY(Map map);
}
