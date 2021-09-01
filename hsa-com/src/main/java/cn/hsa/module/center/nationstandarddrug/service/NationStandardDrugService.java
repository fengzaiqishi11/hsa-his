package cn.hsa.module.center.nationstandarddrug.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugZYDTO;
import cn.hsa.util.ExcelResolveService;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.center.nationstandarddrug.service
 * @Class_name: NationStandardDrugService
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/1/26 9:36
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface NationStandardDrugService extends ExcelResolveService {

  /**
   * @Menthod queryNationStandardDrugPage
   * @Desrciption 分页查询所有国家标准药品
   *
   * @Param
   * [nationStandardDrugDTO]
   *
   * @Author jiahong.yang
   * @Date   2021/1/26 9:40
   * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.List<cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO>>
   **/
  WrapperResponse<PageDTO> queryNationStandardDrugPage(NationStandardDrugDTO nationStandardDrugDTO);

  /**
   * @Package_name: cn.hsa.module.center.nationstandarddrug.service
   * @Class_name: NationStandardDrugService
   * @Describe: 根据id查询国家标准药品信息
   * @Author: luonianxin
   * @Email: nianxin.luo@powersi.com
   * @Date: 2021/4/27 11:22
   * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
   **/
  WrapperResponse<NationStandardDrugDTO> getById(NationStandardDrugDTO nationStandardDrugDTO);


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
   *  保存或更新国家标准药品信息
   * @Author: luonianxin
   * @Email: nianxin.luo@powersi.com
   * @Date: 2021/5/7 9:31
   **/
  WrapperResponse<Boolean> saveNationStandardDrug(Map nationStandardDrugDOMap);
}
