package cn.hsa.report.business.bo.impl.factory;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.outpt.service.InsureUnifiedPayReversalTradeService;
import cn.hsa.module.report.business.bo.ReportBusinessBO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
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
        if((boolean)map.get("jxSettle")){
//            江西
            map.put("tempCode","his_insur_settle_0006");
        }else  if("1".equals(map.get("isHospital").toString()) && (boolean)map.get("oneSettle") != true
                && !"340".equals(map.get("aae140").toString()) && (boolean)map.get("isRemote") != true){
//            住院
            map.put("tempCode","his_insur_settle_0001");
        }else if("0".equals(map.get("isHospital").toString()) && !"340".equals(map.get("aae140").toString()) && (boolean)map.get("isRemote") != true){
//            门诊
            map.put("tempCode","his_insur_settle_0002");
        }else if("340".equals(map.get("aae140").toString()) && (boolean)map.get("isRemote") != true){
//            离休
            map.put("tempCode","his_insur_settle_0003");
        }else if("1".equals(map.get("isHospital").toString()) && (boolean)map.get("oneSettle") == true
                && !"340".equals(map.get("aae140").toString()) && (boolean)map.get("isRemote") == false){
//            一站式
            map.put("tempCode","his_insur_settle_0004");
        }else if("1".equals(map.get("isHospital").toString()) && (boolean)map.get("isRemote") == true){
//            异地就医
            map.put("tempCode","his_insur_settle_0005");
        }
        return map;
    }

}
