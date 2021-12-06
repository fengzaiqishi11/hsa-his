package cn.hsa.module.interf.nation.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.center.nationstandarddrug.dto.NationStandardDrugDTO;

/**
 * @Package_name: cn.hsa.module.interf.nation.drug.bo
 * @Class_name: NationDrugBO
 * @Describe: 国家三大贯标-药品
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-12-01 10:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
public interface NationDrugBO {

    PageDTO queryNationStandardDrugPage(NationStandardDrugDTO nationStandardDrugDTO);
}
