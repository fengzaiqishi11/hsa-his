package cn.hsa.module.center;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO;
import cn.hsa.module.center.nationstandarddrug.service.NationStandardDrugService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Package_name: cn.hsa.module.center
 * @Class_name: NationStandardDrugController
 * @Describe:
 * @Author: yangjiahong
 * @Email: jiahong.yang@powersi.com
 * @Date: 2021/1/26 10:22
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@RestController
@RequestMapping(value = "web/center/nationstandarddrug")
public class NationStandardDrugController {

  @Resource
  NationStandardDrugService nationStandardDrugService_consumer;

  @GetMapping("/queryNationStandardDrugPage")
  public WrapperResponse<PageDTO> queryNationStandardDrugPage(NationStandardDrugDTO nationStandardDrugDTO) {
    return nationStandardDrugService_consumer.queryNationStandardDrugPage(nationStandardDrugDTO);
  }
}
