package cn.hsa.inpt.longcost.service.impl;

import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.longcost.bo.BedLongCostBO;
import cn.hsa.module.inpt.longcost.service.BedLongCostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.inpt.longcost.service.impl
 * @Class_name: bedLongCostServiceImpl
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2020/10/20 10:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/inpt/bedlongcost")
@Service("bedLongCostService_provider")
public class BedLongCostServiceImpl implements BedLongCostService {

    @Resource
    BedLongCostBO bedLongCostBO;

    @Override
    public WrapperResponse<Boolean> saveBedLongCost(Map map) {
        return WrapperResponse.success(bedLongCostBO.saveBedLongCost(map));
    }
}