package cn.hsa.report.business.bean;

import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SettleDeclareSheetBean
 * @Deacription 结算申报单
 * @Author liuzhuoting
 * @Date 2022-02-18
 * @Version 1.0
 **/
@Component
@Slf4j
public class SettleDeclareSheetBean extends GeneralTemplateBean {

    /**
     * 通用方法
     */
    public List<Map<String, Object>> getDeclareInfo(String dsName, String datasetName, Map<String, Object> param) {
        String data = (String) param.get("customConfig");
        if (StringUtils.isNotEmpty(data)) {
            Map customConfig = JSONObject.parseObject(data, Map.class);
            Map info = new HashMap();
            String titleTmp = customConfig.get("title").toString();
            info.put("title", titleTmp.replace("$",param.get("admdvsName").toString()));
            List<Map<String, Object>> list = new ArrayList<>();
            list.add(info);
            return list;
        } else {
            String str = "填充通用数据报错, 模板类:" + dsName + "数据节点:" + datasetName + "模板参数:" + param;
            log.error(str);
            throw new RuntimeException(str);
        }
    }

}