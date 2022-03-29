package cn.hsa.report.business.bo.impl.factory;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.outpt.service.InsureUnifiedPayReversalTradeService;
import cn.hsa.module.report.business.bo.factory.ReportBusinessBO;
import cn.hsa.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName SettleDeclareSheetProcess
 * @Deacription 结算申报单业务处理
 * @Author liuzhuoting
 * @Date 2022/02/24 11:26
 * @Version 1.0
 **/
@Service
@Slf4j
public class SettleDeclareSheetBOImpl implements ReportBusinessBO {

    @Resource
    private InsureUnifiedPayReversalTradeService insureUnifiedPayReversalTradeService_consumer;

    @Override
    public Map getReportDataMap(Map map) {

        WrapperResponse<Map<String, Object>> result = insureUnifiedPayReversalTradeService_consumer.querySumDeclareInfos(map);
        map.putAll(result.getData());
        //打印日志
        log.error("获取到到数据为{}",map);

        switch (map.get("declaraType").toString()) {
            // 城镇职工（住院）
            case Constants.SBLX.CZJM_ZY:
                map.put("tempCode", "his_insur_declare_0001");
                break;
            // 城乡居民（住院）
            case Constants.SBLX.CXJM_ZY:
                map.put("tempCode", "his_insur_declare_0003");
                break;
            // 离休（住院）
            case Constants.SBLX.LX_ZY:
                map.put("tempCode", "his_insur_declare_0002");
                break;
            // 一站式
            case Constants.SBLX.YZS:
                map.put("tempCode", "his_insur_declare_0004");
                break;
            default:
                break;
        }
        //打印日志
        log.error("获取到到tempCode为{}",map.get("tempCode").toString());
        return map;
    }

}
