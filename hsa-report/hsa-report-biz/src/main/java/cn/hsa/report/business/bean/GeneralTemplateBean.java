package cn.hsa.report.business.bean;

import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName GeneralTemplateBean
 * @Deacription 通用模板配置
 * @Author liuzhuoting
 * @Date 2022-02-18
 * @Version 1.0
 **/
@Component
@Slf4j
public class GeneralTemplateBean {

    /**
     * 通用方法
     */
    public List<Map<String, Object>> generalMethod(String dsName, String datasetName, Map<String, Object> param) {
        String data = (String) param.get(datasetName);
        if (StringUtils.isNotEmpty(data)) {
            List<Map<String, Object>> list = new ArrayList<>();
            list.add(JSONObject.parseObject(data, Map.class));
            return list;
        } else {
            String str = "填充通用数据报错, 模板类:" + dsName + "数据节点:" + datasetName + "模板参数:" + param;
            log.error(str);
            throw new RuntimeException(str);
        }
    }
}
