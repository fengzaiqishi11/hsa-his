package cn.hsa.module.outpt.infectious.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.infectious.entity.InfectiousDiseaseDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.infectious.service
 * @Class_name:: OutptInfectiousDiseaExecService
 * @Description: 传染病执行service接口层
 * @Author: liuliyun
 * @Date: 2021/4/21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "has-outpt")
public interface OutptInfectiousDiseaExecService {
    /**
     * @Menthod: save()
     * @Desrciption: 保存传染病信息
     * @Param: map
     * @Author: liuliyun
     * @Date: 2021/4/21
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     **/
    @PostMapping("/service/outpt/outptInfectiousExec/save")
    WrapperResponse<Boolean> save(Map map);

    /**
     * @Menthod: queryInfoById()
     * @Desrciption: 保存传染病信息
     * @Param: map
     * @Author: liuliyun
     * @Date: 2021/4/21
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     **/
    @PostMapping("/service/outpt/outptInfectiousExec/queryInfoById")
    WrapperResponse<List<InfectiousDiseaseDO>> queryInfoById(Map map);

    /**
     * @Menthod: queryInfoById()
     * @Desrciption: 保存传染病信息
     * @Param: map
     * @Author: liuliyun
     * @Date: 2021/4/21
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     **/
    @PostMapping("/service/outpt/outptInfectiousExec/queryInfectiousPage")
    WrapperResponse<PageDTO> queryInfectiousPage(Map map);

    /**
     * @Menthod: InfectiousDiseaseAudit()
     * @Desrciption: 保存传染病信息
     * @Param: map
     * @Author: liuliyun
     * @Date: 2021/4/25
     * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
     **/
    @PostMapping("/service/outpt/outptInfectiousExec/InfectiousDiseaseAudit")
    WrapperResponse InfectiousDiseaseAudit(Map map);
}
