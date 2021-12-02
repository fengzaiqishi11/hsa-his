package cn.hsa.interf.nation.drug.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO;
import cn.hsa.module.center.nationstandarddrug.service.NationStandardDrugService;
import cn.hsa.module.interf.nation.bo.NationDrugBO;
import cn.hsa.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Package_name: cn.hsa.interf.nation.drug.bo.impl
 * @Class_name: NationDrugBOImpl
 * @Describe: 国家三大贯标-药品
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-12-01 10:22
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class NationDrugBOImpl implements NationDrugBO {

    @Resource
    private NationStandardDrugService nationStandardDrugService_consumer;

    @Override
    public PageDTO queryNationStandardDrugPage(NationStandardDrugDTO nationStandardDrugDTO) {
        nationStandardDrugDTO.setIsValid(Constants.SF.S);
        PageDTO data = nationStandardDrugService_consumer.queryNationStandardDrugPage(nationStandardDrugDTO).getData();
        return data;
    }
}
