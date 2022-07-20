package cn.hsa.report.business.bo.impl.factory;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.outpt.service.InsureUnifiedPayReversalTradeService;
import cn.hsa.module.report.business.bo.factory.ReportBusinessBO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.Constants;
import cn.hsa.util.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SettleDeclareDetlSheetBOImpl
 * @Deacription 结算申报单业务处理
 * @Author liuzhuoting
 * @Date 2022/02/24 11:26
 * @Version 1.0
 **/

@Service
public class SettleDeclareDetlSheetBOImpl implements ReportBusinessBO {

    @Resource
    private InsureUnifiedPayReversalTradeService insureUnifiedPayReversalTradeService_consumer;


    @Resource
    private SysParameterService sysParameterService_consumer;

    @Override
    public Map getReportDataMap(Map map) {
        WrapperResponse<Map<String, Object>> result = insureUnifiedPayReversalTradeService_consumer.queryDeclareInfosPrint(map);
        map.putAll(result.getData());
        //查询参数
        Map<String, Object> sysMap = new HashMap<>();
        sysMap.put("hospCode", MapUtils.get(map, "hospCode"));
        sysMap.put("code", "JX_DECLARE_CODE");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(sysMap).getData();
        String value = Constants.SF.F;
        if (sysParameterDTO != null && sysParameterDTO.getValue() != null && !"".equals(sysParameterDTO.getValue())) {
            value = sysParameterDTO.getValue();
        }
        switch (map.get("declaraType").toString()) {
            // 城镇职工（住院）
            case Constants.SBLX.CZJM_ZY:
                map.put("tempCode", "his_insur_declare_detl_0001");
                //江西报表
                if(Constants.SF.S.equals(value)){
                    map.put("tempCode", "his_insur_declare_detl_0010");
                }
                break;
            // 城乡居民（住院）
            case Constants.SBLX.CXJM_ZY:
                map.put("tempCode", "his_insur_declare_detl_0002");
                //江西报表
                if(Constants.SF.S.equals(value)){
                    map.put("tempCode", "his_insur_declare_detl_0011");
                }
                break;
            // 离休（住院）
            case Constants.SBLX.LX_ZY:
                map.put("tempCode", "his_insur_declare_detl_0003");
                break;
            // 一站式
            case Constants.SBLX.YZS:
                map.put("tempCode", "his_insur_declare_detl_0004");
                break;
            default:
                break;
        }
        return map;
    }
}

