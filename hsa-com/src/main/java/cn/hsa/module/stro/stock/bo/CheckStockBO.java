package cn.hsa.module.stro.stock.bo;

import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.outpt.prescribeDetails.dto.OutptPrescribeDetailsDTO;
import cn.hsa.module.stro.stock.dto.CheckStockDTO;
import cn.hsa.module.stro.stock.dto.CheckStockRespDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.stro.stock.bo
 * @Class_name: CheckStockBO
 * @Describe: 校验库存bo
 * @Author: zhangguorui
 * @Eamil: guorui.zhang@powersi.com
 * @Date: 2021/8/9 20:08
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface CheckStockBO {
    /**
     * @Method queryOutDrugOrMeterialStock
     * @Desrciption 获得库存-占用-在途数量(可用的数量)
     * @Param [CheckStockDTO checkStockDTO]
     * @Author zhangguorui
     * @Date   2021/8/9 20:08
     * @Return java.lang.Integer
     */
    CheckStockRespDTO queryOutDrugOrMeterialStock(CheckStockDTO checkStockDTO);
    /**
     * @Method queryInptDrugOrMeterialStock
     * @Desrciption 获得库存-占用-在途数量（住院的）
     * @Param [inptAdviceDTO]
     * @Author zhangguorui
     * @Date   2021/8/10 20:01
     * @Return java.math.BigDecimal
     */
    BigDecimal queryInptDrugOrMeterialStock(InptAdviceDTO inptAdviceDTO);
}
