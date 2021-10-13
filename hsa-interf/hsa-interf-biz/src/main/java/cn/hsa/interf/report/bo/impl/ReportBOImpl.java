package cn.hsa.interf.report.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.outpt.dao.OutptPrescribeDAO;
import cn.hsa.module.interf.report.bo.ReportBO;
import cn.hsa.module.interf.report.dao.ReportDAO;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author luonianxin
 */
@Component
@Slf4j
public class ReportBOImpl extends HsafBO implements ReportBO {

    @Resource
    private ReportDAO reportDAO;
    @Resource
    private OutptPrescribeDAO prescribeDAO;

    /**
     * 查询药库实时进销存报表
     *
     * @param map 查询参数
     * @return cn.hsa.hsaf.core.framework.web public class WrapperResponse 统一结果返回实体
     */
    @Override
    public PageDTO queryStroInvoicingLedger(Map<String, Object> map) {

        PageHelper.startPage(1, 20);

        List<Map> stroInvoicingDTOS = new ArrayList<>();
        List<Map<String,Object>> dataList = null;
        if("1".equals(map.get("flag"))){
            if("0".equals(map.get("buyOrSell"))){
                // 查询药库实时进销存台账/按零售价
                stroInvoicingDTOS = reportDAO.queryStroInvoicingLedger(map);
            } else {
                // 查询药库实时进销存台账/按购进价
                stroInvoicingDTOS = reportDAO.queryStroInvoicingLedgerBuy(map);
            }
        } else{
            if("0".equals(map.get("buyOrSell"))){
                // 查询药房实时进销存台账/按零售价

                stroInvoicingDTOS = reportDAO.queryPharInvoicingLedger(map);
                dataList = prescribeDAO.querySysUserAll(map);
            } else {
                // 查询药房实时进销存台账/按购进价
                stroInvoicingDTOS = reportDAO.queryPharInvoicingLedgerBuy(map);
            }
        }
        return PageDTO.of(stroInvoicingDTOS);

    }

    @Override
    public PageDTO queryByStrSQL(Map<String, Object> map) {
        return PageDTO.of(reportDAO.queryByStrSQL(map));
    }
}
