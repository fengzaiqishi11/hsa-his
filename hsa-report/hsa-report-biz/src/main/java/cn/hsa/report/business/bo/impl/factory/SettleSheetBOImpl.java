package cn.hsa.report.business.bo.impl.factory;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.outpt.service.InsureUnifiedPayReversalTradeService;
import cn.hsa.module.report.business.bo.factory.ReportBusinessBO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @ClassName SettleSheetProcess
 * @Deacription 结算单业务处理
 * @Author liuzhuoting
 * @Date 2022/02/24 11:26
 * @Version 1.0
 **/
@Service
public class SettleSheetBOImpl implements ReportBusinessBO {

    @Resource
    private InsureUnifiedPayReversalTradeService insureUnifiedPayReversalTradeService_consumer;

    @Override
    public Map getReportDataMap(Map map) {
        WrapperResponse<Map<String, Object>> result = insureUnifiedPayReversalTradeService_consumer.downLoadSettleInfo(map);
        map.putAll(result.getData());
        if ((boolean) map.get("jxSettle")) {
            //江西
            map.put("tempCode", "his_insur_settle_0006");
        } else if ("1".equals(map.get("isHospital").toString()) && (Boolean.parseBoolean(map.get("isRemote").toString()) == true || Boolean.parseBoolean(map.get("snRemote").toString()) == true)) {
            //异地就医
            map.put("tempCode", "his_insur_settle_0005");
        } else if ("1".equals(map.get("isHospital").toString()) && Boolean.parseBoolean(map.get("oneSettle").toString()) != true
                && !"340".equals(map.get("aae140").toString()) ) {
            //住院
            map.put("tempCode", "his_insur_settle_0001");
        } else if ("0".equals(map.get("isHospital").toString()) && !"340".equals(map.get("aae140").toString()) ) {
            //门诊
            map.put("tempCode", "his_insur_settle_0002");
        } else if ("340".equals(map.get("aae140").toString()) ) {
            //离休
            map.put("tempCode", "his_insur_settle_0003");
        } else if ("1".equals(map.get("isHospital").toString()) && Boolean.parseBoolean(map.get("oneSettle").toString()) == true
                && !"340".equals(map.get("aae140").toString()) ) {
            //一站式
            map.put("tempCode", "his_insur_settle_0004");
        }
        return map;
    }

}
