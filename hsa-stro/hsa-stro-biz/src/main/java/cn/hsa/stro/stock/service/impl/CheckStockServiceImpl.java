package cn.hsa.stro.stock.service.impl;

import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.stro.stock.bo.CheckStockBO;
import cn.hsa.module.stro.stock.bo.StroStockBO;
import cn.hsa.module.stro.stock.dto.CheckStockRespDTO;
import cn.hsa.module.stro.stock.service.CheckStockService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @Package_name: cn.hsa.stro.stock.service.impl
 * @Class_name: CheckStockServiceImpl
 * @Describe:
 * @Author: zhangguorui
 * @Eamil: guorui.zhang@powersi.com
 * @Date: 2021/8/9 20:04
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/web/stro/checkStockService")
@Service("checkStockService_provider")
public class CheckStockServiceImpl implements CheckStockService {
    @Resource
    private CheckStockBO checkStockBO;
    /**
     * @Method checkOutDrugOrMeterialStock
     * @Desrciption 检查门诊药品或者材料是否足够，返回数量
     *  1.库存是否足够
     *  2.库存-占用是否足够
     *  3.库存-占用-在途是否足够
     * @Param [map]
     * @Author zhangguorui
     * @Date   2021/8/9 20:06
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Integer>
     */
    @HsafRestPath(value = "/checkOutDrugOrMeterialStock", method = RequestMethod.GET)
    @Override
    public WrapperResponse<CheckStockRespDTO> checkOutDrugOrMeterialStock(Map map) {
        return WrapperResponse.success(checkStockBO.queryOutDrugOrMeterialStock(MapUtils.get(map,"checkStockDTO")));
    }
    /**
     * @Method checkInptDrugOrMeterialStock
     * @Desrciption 检查住院中开医嘱过程中，库存药品是否足够
     * @Param [map]
     * @Author zhangguorui
     * @Date   2021/8/10 19:59
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.math.BigDecimal>
     */
    @Override
    public WrapperResponse<BigDecimal> checkInptDrugOrMeterialStock(Map map) {
        return WrapperResponse.success(checkStockBO.queryInptDrugOrMeterialStock(MapUtils.get(map,"checkStockDTO")));
    }
}
