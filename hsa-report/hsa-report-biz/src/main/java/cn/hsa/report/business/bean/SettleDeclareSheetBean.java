package cn.hsa.report.business.bean;

import cn.hsa.module.report.business.bo.ReportBaseDataBO;
import cn.hsa.util.BigDecimalUtils;
import cn.hsa.util.DateUtils;
import cn.hsa.util.StringUtils;
import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private ReportBaseDataBO reportBaseDataBO;

    /**
     * 申报单信息
     */
    public List<Map<String, Object>> getDeclareInfo(String dsName, String datasetName, Map<String, Object> param) {
        if (MapUtils.isNotEmpty(param)) {
            Map info = new HashMap(2);
            String customConfigStr = (String) param.get("customConfig");
            String baseInfoStr = (String) param.get("baseInfo");
            Map baseInfo = JSONObject.parseObject(baseInfoStr, Map.class);
            String hospCode = baseInfo.get("hospCode").toString();
            String regCode = baseInfo.get("regCode").toString();

            Map customConfig = JSONObject.parseObject(customConfigStr, Map.class);
            String titleTmp = customConfig.get("title").toString();
            String regName = reportBaseDataBO.getAdmdvsName(hospCode, regCode);
            String cityName = reportBaseDataBO.getAdmdvsName(hospCode, regCode.substring(0, 4) + "00");
            String provinceName = reportBaseDataBO.getAdmdvsName(hospCode, regCode.substring(0, 2) + "0000");
            String titleTop = regCode.contains("9900") ? regName : provinceName + cityName;
            info.put("title", titleTmp.replace("$", titleTop));
            info.put("printDate", DateUtils.format(new Date(), DateUtils.Y_M_D));
            info.put("crteName", param.get("crterName"));
            List<Map<String, Object>> list = new ArrayList<>();
            list.add(info);
            return list;
        } else {
            String str = "填充通用数据报错, 模板类:" + dsName + "数据节点:" + datasetName + "模板参数:" + param;
            log.error(str);
            throw new RuntimeException(str);
        }
    }


    /**
     * 申报单汇总
     */
    public List<Map<String, Object>> getDeclareSummary(String dsName, String datasetName, Map<String, Object> param) {
        String data = (String) param.get("resultList");
        if (StringUtils.isNotEmpty(data)) {
            List<Map> maps = JSONArray.parseArray(data, Map.class);
            Map<String, Object> totalMap = new HashMap<>(8);
            BigDecimal totalPrice = BigDecimal.ZERO;
            BigDecimal planSumPrice = BigDecimal.ZERO;
            BigDecimal planPrice = BigDecimal.ZERO;
            BigDecimal insurePrice = BigDecimal.ZERO;
            for (Map map : maps) {
                totalPrice = BigDecimalUtils.add(totalPrice, new BigDecimal(map.get("totalPrice").toString())).setScale(2);
                planSumPrice = BigDecimalUtils.add(planSumPrice, new BigDecimal(map.get("planSumPrice").toString())).setScale(2);
                planPrice = BigDecimalUtils.add(planPrice, new BigDecimal(map.get("planPrice").toString())).setScale(2);
                insurePrice = BigDecimalUtils.add(insurePrice, new BigDecimal(map.get("insurePrice").toString())).setScale(2);
            }
            totalMap.put("totalPriceSum", totalPrice);
            totalMap.put("totalPriceSumCapital", Convert.digitToChinese(totalPrice));
            totalMap.put("planSumPriceSum", planSumPrice);
            totalMap.put("planSumPriceCapital", Convert.digitToChinese(planSumPrice));
            totalMap.put("planPriceSum", planPrice);
            totalMap.put("planPriceCapital", Convert.digitToChinese(planPrice));
            totalMap.put("insurePriceSum", insurePrice);
            totalMap.put("insurePriceCapital", Convert.digitToChinese(insurePrice));
            List<Map<String, Object>> list = new ArrayList<>();
            list.add(totalMap);
            return list;
        } else {
            String str = "填充通用数据报错, 模板类:" + dsName + "数据节点:" + datasetName + "模板参数:" + param;
            log.error(str);
            throw new RuntimeException(str);
        }
    }


}