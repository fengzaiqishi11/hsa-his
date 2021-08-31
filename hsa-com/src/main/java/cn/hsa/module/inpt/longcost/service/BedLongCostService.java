package cn.hsa.module.inpt.longcost.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.longcost.service
 * @Class_name: bedLongCostService
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/10/20 10:23
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-inpt")
public interface BedLongCostService {

    @PostMapping("/service/inpt/longcost/saveBedLongCost")
    WrapperResponse<Boolean> saveBedLongCost(Map map);
}
