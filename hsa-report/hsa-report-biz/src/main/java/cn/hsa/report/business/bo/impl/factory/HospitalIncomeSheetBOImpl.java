package cn.hsa.report.business.bo.impl.factory;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.interf.statement.service.PatientCostLedgerService;
import cn.hsa.module.report.business.bo.factory.ReportBusinessBO;
import cn.hsa.module.report.config.dao.ReportConfigurationDAO;
import cn.hsa.module.report.config.dto.ReportConfigurationDTO;
import cn.hsa.util.Constants;
import cn.hsa.util.ListUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName SettleDeclareSheetProcess
 * @Deacription 医院收入报表业务处理
 * @Author liuzhuoting
 * @Date 2022/02/24 11:26
 * @Version 1.0
 **/
@Service
public class HospitalIncomeSheetBOImpl implements ReportBusinessBO {

    @Resource
    private PatientCostLedgerService patientCostLedgerService_consumer;

    @Autowired
    private ReportConfigurationDAO reportConfigurationDAO;

    @Override
    public Map getReportDataMap(Map map) {
        String hospCode = map.get("hospCode").toString();
        String tempCode = "his_iptopt_feeIncome_0001";
        ReportConfigurationDTO configuration = reportConfigurationDAO.queryByTempCode(hospCode, tempCode);
        String customConfigStr = configuration.getCustomConfig().replace("\\", "").replace("\"{", "{").replace("}\"", "}");
        // 自定义配置
        Map customConfigMap = JSON.parseObject(customConfigStr, Map.class);
        String titleTmp = customConfigMap.get("title").toString();

        List<Map<String, Object>> feeItemList;
        String title;
        if (Constants.VISITTYPE.INPT.equals(map.get("businessSources"))) {
            WrapperResponse<List<Map<String, Object>>> zyResult = patientCostLedgerService_consumer.queryZyFeeIncomeList(map);
            feeItemList = mergeFeeItemList(zyResult.getData());
            title = titleTmp.replace("$", "住院");
        } else if (Constants.VISITTYPE.OUTPT.equals(map.get("businessSources"))) {
            WrapperResponse<List<Map<String, Object>>> mzResult = patientCostLedgerService_consumer.queryMzFeeIncomeList(map);
            feeItemList = mergeFeeItemList(mzResult.getData());
            title = titleTmp.replace("$", "门诊");
        } else {
            WrapperResponse<List<Map<String, Object>>> zyResult = patientCostLedgerService_consumer.queryZyFeeIncomeList(map);
            WrapperResponse<List<Map<String, Object>>> mzResult = patientCostLedgerService_consumer.queryMzFeeIncomeList(map);
            feeItemList = mergeFeeItemList(ListUtils.union(zyResult.getData(), mzResult.getData()));
            title = titleTmp.replace("$", "全院");
        }
        if (ListUtils.isEmpty(feeItemList)) {
            throw new RuntimeException("收入统计无数据，请重新时间范围！");
        }
        map.put("title", title);
//        map.put("fileName", tempCode+hospCode+map.get("crteId"));
        map.put("feeItemList", feeItemList);
        map.put("tempCode", tempCode);
        return map;
    }

    private List<Map<String, Object>> mergeFeeItemList(List<Map<String, Object>> getFeeItemList) {
        Map<String, List<Map<String, Object>>> bfcMap = getFeeItemList.stream().
                collect(Collectors.groupingBy(a -> a.get("bfcId").toString()));

        Map<String, String> patientCodeMap = getFeeItemList.stream().
                collect(Collectors.toMap(s -> s.get("patientCode").toString(), s -> s.get("patientType").toString(), (s1, s2) -> s1));

        List<Map<String, Object>> feeItemList = new ArrayList<>();
        for (String bfcId : bfcMap.keySet()) {
            List<Map<String, Object>> bfcByList = bfcMap.get(bfcId);
            Map<String, List<Map<String, Object>>> patientMap = bfcByList.stream().
                    collect(Collectors.groupingBy(a -> a.get("patientCode").toString()));
            Map<String, String> copyPatientCodeMap = new HashMap<>(patientCodeMap);
            for (String patientCode : patientMap.keySet()) {
                List<Map<String, Object>> patientByList = patientMap.get(patientCode);
                BigDecimal realityPrice = patientByList.stream().
                        map(s -> (BigDecimal) s.get("realityPrice")).reduce(BigDecimal::add).get();
                Map resMap = new HashMap(5);
                resMap.put("realityPrice", realityPrice);
                resMap.put("bfcId", patientByList.get(0).get("bfcId"));
                resMap.put("bfcName", patientByList.get(0).get("bfcName"));
                resMap.put("patientCode", patientByList.get(0).get("patientCode"));
                resMap.put("patientType", patientByList.get(0).get("patientType"));
                feeItemList.add(resMap);
                copyPatientCodeMap.remove(patientCode);
            }
            for (String patientCode : copyPatientCodeMap.keySet()) {
                Map resMap = new HashMap(5);
                resMap.put("realityPrice", 0);
                resMap.put("bfcId", bfcId);
                resMap.put("bfcName", bfcByList.get(0).get("bfcName"));
                resMap.put("patientCode", patientCode);
                resMap.put("patientType", copyPatientCodeMap.get(patientCode));
                feeItemList.add(resMap);
            }
        }
        return feeItemList;
    }

}
