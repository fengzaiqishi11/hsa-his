package cn.hsa.report.business.bean;

import cn.hsa.util.Constants;
import cn.hsa.util.DateUtils;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName HospitalIncomeSheetBean
 * @Deacription 医院收入报表
 * @Author liuzhuoting
 * @Date 2022-03-03
 * @Version 1.0
 **/
@Component
@Slf4j
public class HospitalIncomeSheetBean extends GeneralTemplateBean {

    /**
     * 医院收入信息
     */
    public List<Map<String, Object>> getBaseInfo(String dsName, String datasetName, Map<String, Object> param) {
        if (MapUtils.isNotEmpty(param)) {
            Map info = new HashMap(2);
            info.put("printDate", DateUtils.format(new Date(), DateUtils.Y_M_D));
            info.put("title", param.get("title"));
            info.put("crteName", param.get("crteName"));
            info.put("startDate", param.get("startDate"));
            info.put("endDate", param.get("endDate"));
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