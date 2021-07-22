package cn.hsa.module.outpt.infectious.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.outpt.infectious.dto.InfectiousDiseaseDto;
import cn.hsa.module.outpt.infectious.entity.InfectiousDiseaseDO;

import java.util.List;

/**
 * @Package_name: cn.hsa.module.outpt.infectious.bo
 * @Class_name:: OutptInfectiousDiseaExecBO
 * @Description: 传染病执行BO接口层
 * @Author: liuliyun
 * @Date: 2021/04/21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptInfectiousDiseaExecBO {
    /**
     * @Menthod: update()
     * @Desrciption: 传染病执行
     * @Param: map
     * @Author: liuliyun
     * @Date: 2021/04/21
     * @Return: Boolean
     **/
    Boolean saveOutptInfectiousInfo(InfectiousDiseaseDO infectiousDiseaseDO);

    Boolean updateOutptInfectiousInfo(InfectiousDiseaseDO infectiousDiseaseDO);

    List<InfectiousDiseaseDO> findInfectiousById(InfectiousDiseaseDO infectiousDiseaseDO);

    PageDTO findInfectiousPage(InfectiousDiseaseDto infectiousDiseaseDO);

    List<InfectiousDiseaseDto> findInfectiousListByIds(InfectiousDiseaseDto infectiousDiseaseDto);
}
