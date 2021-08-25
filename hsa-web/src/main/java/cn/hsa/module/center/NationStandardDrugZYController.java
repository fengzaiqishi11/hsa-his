package cn.hsa.module.center;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO;
import cn.hsa.module.center.nationstandarddrug.service.NationStandardDrugService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Package_name: cn.hsa.module.center
 * @Class_name: NationStandardDrugZYController
 * @Describe: 国家标准中药信息查询
 * @Author: luonianxin
 * @Email: nianxin.luo@powersi.com
 * @Date: 2021/04/26 10:22
. * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping(value = "web/center/nationstandarddrug")
public class NationStandardDrugZYController {

  @Resource
  NationStandardDrugService nationStandardDrugService_consumer;

  @GetMapping("/queryNationStandardDrugZYPage")
  public WrapperResponse<PageDTO> queryNationStandardDrugZYPage(NationStandardDrugDTO nationStandardDrugDTO) {
    return nationStandardDrugService_consumer.queryNationStandardDrugPage(nationStandardDrugDTO);
  }
}
