package cn.hsa.module.interf.extract.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.extract.dto.ExtractConsumptionDTO;
import cn.hsa.module.interf.extract.dto.ExtractStroInvoicingDetailDTO;

import java.util.List;
import java.util.Map;

/**
 * @author gory
 * @date 2022/7/12 9:01
 * 消耗报表BO
 */
public interface ExtractConsumptionBO {
    /**
     * @Author gory
     * @Description 查询消耗报表
     * @Date 2022/7/12 9:03
     * @Param [extractConsumptionDTO]
     **/
    PageDTO queryExtractConsumptions(ExtractConsumptionDTO extractConsumptionDTO);

    PageDTO extractStroInvoicingDetailDTO(ExtractStroInvoicingDetailDTO extractStroInvoicingDetailDTO);
}
