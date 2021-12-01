package cn.hsa.interf.nation.drug.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO;
import cn.hsa.module.interf.nation.bo.NationDrugBO;
import cn.hsa.module.interf.nation.service.NationDrugService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Package_name: cn.hsa.interf.nation.drug.service.impl
 * @Class_name: NationStandardDrugServiceImpl
 * @Describe: 国家三大贯标-药品
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-12-01 10:18
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@Service("/nationDrugService_provider")
@HsafRestPath("/service/interf/nationDrug")
public class NationDrugServiceImpl extends HsafService implements NationDrugService {

    @Resource
    private NationDrugBO nationDrugBO;

    @Override
    public WrapperResponse<PageDTO> queryNationStandardDrugPage(NationStandardDrugDTO nationStandardMaterialDTO) {
        return WrapperResponse.success(nationDrugBO.queryNationStandardDrugPage(nationStandardMaterialDTO));
    }

}
