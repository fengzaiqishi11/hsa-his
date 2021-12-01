package cn.hsa.module.interf.nation.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Package_name: cn.hsa.module.interf.nation.drug.service
 * @Class_name: NationDrugService
 * @Describe: 国家三大贯标-药品
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-12-01 10:17
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-interf")
public interface NationDrugService {

    /**
     * 根据省份编码查询中心端的基础项目数据
     * @Author: luonianxin
     * @Email: nianxin.luo@powersi.com
     * @Date: 2021/6/11 14:07
     **/
    @GetMapping("/service/interf/nationDrug/queryCenterNationStandardDrugPage")
    WrapperResponse<PageDTO> queryNationStandardDrugPage(NationStandardDrugDTO nationStandardMaterialDTO);
}
