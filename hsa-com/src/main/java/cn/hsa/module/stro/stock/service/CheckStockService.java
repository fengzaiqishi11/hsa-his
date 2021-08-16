package cn.hsa.module.stro.stock.service;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.stro.stock.dto.CheckStockRespDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.stro.stock.service
 * @Class_name: CheckStockService
 * @Describe: 门诊/住院校验库存Service
 * @Author: zhangguorui
 * @Eamil: guorui.zhang@powersi.com
 * @Date: 2021/8/9 19:44
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-stro")
public interface CheckStockService {
    /**
     * @Method checkOutDrugOrMeterialStock
     * @Desrciption 检查门诊药品或者材料是否足够，返回数量
     * 1.库存是否足够
     * 2.库存-占用是否足够
     * 3.库存-占用-在途是否足够
     * @Param [map]
     * @Author zhangguorui
     * @Date   2021/8/9 19:48
     * @Return java.lang.Integer
     */
    @GetMapping("/service/web/stro/checkStroStock/checkOutDrugOrMeterialStock")
    WrapperResponse<CheckStockRespDTO> checkOutDrugOrMeterialStock(Map map);
    /**
     * @Method checkInptDrugOrMeterialStock
     * @Desrciption 检查住院中开医嘱过程中，库存药品是否足够
     * @Param [map]
     * @Author zhangguorui
     * @Date   2021/8/10 19:55
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.math.BigDecimal>
     */
    @GetMapping("/service/web/stro/checkStroStock/checkInptDrugOrMeterialStock")
    WrapperResponse<BigDecimal> checkInptDrugOrMeterialStock(Map map);
}
