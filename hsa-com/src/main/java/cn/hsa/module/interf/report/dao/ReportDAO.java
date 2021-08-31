package cn.hsa.module.interf.report.dao;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 罗年鑫
 */
public interface ReportDAO {

    /**
     * @Menthod queryStroInvoicingLedger
     * @Desrciption 药库实时进销存报表/按零售价
     *
     * @Param
     * [stroInvoicingDTO]
     *
     * @Author jiahong.yang
     * @Date   2020/11/11 14:25
     * @Return java.util.List<cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO>
     **/
    List<Map> queryStroInvoicingLedger(Map<String,Object> map);

    /**
     * @Menthod queryStroInvoicingLedgerBuy
     * @Desrciption 药库实时进销存报表/按购进价
     *
     * @Param
     * [stroInvoicingDTO]
     *
     * @Author jiahong.yang
     * @Date   2020/12/16 9:42
     * @Return java.util.List<java.util.Map>
     **/
    List<Map> queryStroInvoicingLedgerBuy(Map<String,Object> map);

    /**
     * @Menthod queryStroInvoicingLedger
     * @Desrciption 药房实时进销存报表
     *
     * @Param
     * [stroInvoicingDTO]
     *
     * @Author jiahong.yang
     * @Date   2020/11/11 14:25
     * @Return java.util.List<cn.hsa.module.stro.stroinvoicing.dto.StroInvoicingDTO>
     **/
    List<Map> queryPharInvoicingLedger(Map<String,Object> map);

    /**
     * @Menthod queryPharInvoicingLedgerBuy
     * @Desrciption 药房实时进销存报表/按购进价
     *
     * @Param
     * [stroInvoicingDTO]
     *
     * @Author jiahong.yang
     * @Date   2020/12/16 10:09
     * @Return java.util.List<java.util.Map>
     **/
    List<Map> queryPharInvoicingLedgerBuy(Map<String,Object> map);
    List<Map> queryByStrSQL(Map<String,Object> map);
}
