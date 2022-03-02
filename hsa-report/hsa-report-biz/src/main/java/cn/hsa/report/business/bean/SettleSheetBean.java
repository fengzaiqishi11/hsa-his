package cn.hsa.report.business.bean;


import cn.hsa.module.report.business.bo.ReportBaseDataBO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

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

    @Autowired
    private ReportBaseDataBO reportBaseDataBO;

    /**
     * 结算单信息
     */
    public  List<Map<String, Object>> getSettleInfo(String dsName, String datasetName, Map<String, Object> param) {
        if (MapUtils.isNotEmpty(param)) {
            String info = (String) param.get("baseInfoMap");
            String hospCode = (String) param.get("hospCode");
            String regCode = (String) param.get("mdtrtareaAdmvs");
            String insuplcAdmdvs = (String) param.get("insuplcAdmdvs");
            Map infoMap = JSONObject.parseObject(info, Map.class);
            //统筹区转义
            String mdtrtareaAdmvsName = reportBaseDataBO.getAdmdvsName(hospCode,regCode);
            String insuplcAdmdvsName = reportBaseDataBO.getAdmdvsName(hospCode,insuplcAdmdvs);
            infoMap.put("mdtrtareaAdmvsName",mdtrtareaAdmvsName);
            infoMap.put("insuplcAdmdvsName",insuplcAdmdvsName);

            //时间处理
            if(infoMap.get("begntime") != null && infoMap.get("endtime") != null){
                infoMap.put("begntime",DateUtils.getDateStr((Long)infoMap.get("begntime")));
                infoMap.put("endtime",DateUtils.getDateStr((Long)infoMap.get("endtime")));
            }
            //字典转义
            if(infoMap.get("psnType") != null){
                infoMap.put("psnTypeName",reportBaseDataBO.getInsureDictName(hospCode,regCode,"PSN_TYPE",infoMap.get("psnType").toString()));
            }
            if(infoMap.get("medType") != null){
                infoMap.put("medTypeName",reportBaseDataBO.getInsureDictName(hospCode,regCode,"MED_TYPE",infoMap.get("medType").toString()));
            }
            if(infoMap.get("psnIdetType") != null){
                infoMap.put("psnIdetTypeName",reportBaseDataBO.getInsureDictName(hospCode,regCode,"MAT_IDET_CODE",infoMap.get("psnIdetType").toString()));
            }
            if(infoMap.get("aae140") != null){
                infoMap.put("aae140Name",reportBaseDataBO.getInsureDictName(hospCode,regCode,"INSUTYPE",infoMap.get("aae140").toString()));
            }

            List<Map<String, Object>> list = new ArrayList<>();
            list.add(infoMap);
            return list;
        }else {
            String str = "填充通用数据报错, 模板类:" + dsName + "数据节点:" + datasetName + "模板参数:" + param;
            log.error(str);
            throw new RuntimeException(str);
        }
    }

    public  List<Map<String, Object>> getmedChrgitmType(String dsName, String datasetName, Map<String, Object> param) {
        if (MapUtils.isNotEmpty(param)) {
            String info1 = (String) param.get("feeMapList");
            String hospCode = (String) param.get("hospCode");
            String regCode = (String) param.get("mdtrtareaAdmvs");
            List<Map<String, Object>> feeMapList = new ArrayList<>();
            if (StringUtils.isNotEmpty(info1)) {
                List<Object> objs = JSONArray.parseArray(info1);
                for (Object obj : objs) {
                    feeMapList.add(JSONObject.parseObject(obj.toString(), Map.class));
                }
            }
            for(Map feeMap:feeMapList){
                if(feeMap.get("medChrgitmType") != null){
                    feeMap.put("medChrgitmTypeName",reportBaseDataBO.getInsureDictName(hospCode,regCode,"MED_CHRGITM_TYPE",feeMap.get("medChrgitmType").toString()));
                }
            }
            return feeMapList;
        }else {
            String str = "填充通用数据报错, 模板类:" + dsName + "数据节点:" + datasetName + "模板参数:" + param;
            log.error(str);
            throw new RuntimeException(str);
        }
    }

}
