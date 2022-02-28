package cn.hsa.report.business.bean;


import cn.hsa.util.DateUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SettleDeclareSheetBean
 * @Deacription 结算单
 * @Author liuzhuoting
 * @Date 2022-02-18
 * @Version 1.0
 **/
@Component
@Slf4j
public class SettleSheetBean extends GeneralTemplateBean  {

    /**
     * 结算单信息
     */
    public Map<String, Object> getSettleInfo(String dsName, String datasetName, Map<String, Object> param) {
        if (MapUtils.isNotEmpty(param)) {
            String info = (String) param.get("baseInfoMap");
            Map infoMap = JSONObject.parseObject(info, Map.class);
            infoMap.put("begntime",DateUtils.getDateStr((Long)infoMap.get("begntime")));
            infoMap.put("endtime",DateUtils.getDateStr((Long)infoMap.get("endtime")));
            return infoMap;
        }else {
            String str = "填充通用数据报错, 模板类:" + dsName + "数据节点:" + datasetName + "模板参数:" + param;
            log.error(str);
            throw new RuntimeException(str);
        }
    }

}
