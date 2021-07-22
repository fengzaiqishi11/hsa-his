package cn.hsa.module.outpt.executioncardprint.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.executioncardprint.service
 * @Class_name:: ExecutionCardPrintService
 * @Description: 执行卡打印service层调用（提供给dubbo）
 * @Author: zhangxuan
 * @Date: 2020-08-17 19:17
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "outpt-executionCardPrint")
public interface OutptExecutionCardPrintService {
    /**
     * @Method queryPage
     * @Desrciption 根据条件分页查询执行卡打印信息
     * @Param
     *
     * @Author zhangxuan
     * @Date   2020-08-17 19:17
     * @Return
    **/
    @PostMapping("/service/outpt/executionCardPrint/queryPage")
    WrapperResponse<PageDTO> queryPage(Map map);
    /**
     * @Method update
     * @Desrciption 打印后改变打印状态
     * @Param
     *
     * @Author zhangxuan
     * @Date   2020-08-26 13:51
     * @Return
    **/
    @PostMapping("/service/outpt/executionCardPrint/queryPage")
    WrapperResponse<Boolean> update(Map map);
}
