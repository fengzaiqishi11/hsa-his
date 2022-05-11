package cn.hsa.module.stro.stroinvoicing.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingMonthlyDTO;

import java.util.Map;

/**
 * @Class_name: StroInvoicingMonthlyBO
 * @Describe:
 * @Author: zhangguorui
 * @Date: 2022/3/18 9:34
 **/
public interface StroInvoicingMonthlyBO {
    boolean insertCopyStroInvoicing(Map map);

    PageDTO queryPage(StroInvoicingMonthlyDTO stroInvoicingMonthlyDTO);

    PageDTO queryDetailByMonthlyId(StroInvoicingMonthlyDTO stroInvoicingMonthlyDTO);
}
