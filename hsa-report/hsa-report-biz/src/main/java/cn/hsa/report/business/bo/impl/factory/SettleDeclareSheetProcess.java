package cn.hsa.report.business.bo.impl.factory;

import cn.hsa.util.Constants;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName SettleDeclareSheetProcess
 * @Deacription 结算申报单业务处理
 * @Author liuzhuoting
 * @Date 2022/02/24 11:26
 * @Version 1.0
 **/
@Service
public class SettleDeclareSheetProcess implements ReportBusinessProcess {

    @Override
    public Map getReportDataMap(Map map) {
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
        return map;
    }

}
