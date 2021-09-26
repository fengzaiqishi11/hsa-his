package cn.hsa.mris.mrisHome.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.mris.mrisHome.bo.InptMrisInfoBO;
import cn.hsa.module.mris.mrisHome.dao.InptMrisInfoDAO;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Package_name: cn.hsa.interf.inpt.bo.impl
 * @Class_name:: InptMrisInfoBOImpl
 * @Description: 病案首页BO层实现类
 * @Author: liuliyun
 * @Date: 2021-07-19 16:45
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class InptMrisInfoBOImpl extends HsafBO implements InptMrisInfoBO {
    @Resource
    InptMrisInfoDAO inptMrisInfoDAO;
    private final Pattern pattern = Pattern.compile(".+?(\\{.+?\\})");

    @Override
    public List<LinkedHashMap<String, Object>> importMrisInfo(Map map) throws Exception {
//        map.put("type","N042");
//        List<Map<String, Object>> uploadTypeList = inptMrisInfoDAO.queryUploadType(map);
        List<LinkedHashMap<String, Object>> data =inptMrisInfoDAO.getMrisBaseInfo(map);
//        Map<String, Object> uploadTypeMap =uploadTypeList.get(0);
//        //获取查询对应上报数据的sql配置文本,并且将where条件参数替换成对应值 如：{hospCode}
//        String sql_str = (String) uploadTypeMap.get("sql_str");
//        //获取上传的数据类型
//        String typeBm = (String) uploadTypeMap.get("type");
//        if (StringUtils.isNotEmpty(sql_str)) {
//            Matcher matcher = pattern.matcher(sql_str);
//            while (matcher.find()) {
//                String key = matcher.group(1).replaceAll("\\{", "").replace("}", "");
//                sql_str = sql_str.replaceAll("\\{" + key + "}", map.get(key) == null ? "" : map.get(key) instanceof Integer?(Integer) map.get(key)+"":(String) map.get(key));
//            }
//            data = inptMrisInfoDAO.queryData(sql_str);
        if (data!=null&&data.size()>0) {
            for (LinkedHashMap<String, Object> ma : data) {
                Map diagnoseParam = new HashMap();
                diagnoseParam.put("hospCode", (String) map.get("hospCode"));
                diagnoseParam.put("visitId", ma.get("visit_id"));
                List<Map<String, Object>> mrisDiagnose = inptMrisInfoDAO.getMrisDiagnose(diagnoseParam);
                LinkedHashMap<String, Object> diagnoseMap = new LinkedHashMap<>();
                diagnoseMap.put("C03C", "");
                diagnoseMap.put("C04N", "");
                diagnoseMap.put("C05C", "");
                diagnoseMap.put("C06x01C", "");
                diagnoseMap.put("C07x01N", "");
                diagnoseMap.put("C08x01C", "");
                int i = 2;
                if (mrisDiagnose != null && mrisDiagnose.size() > 0) {
                    for (Map<String, Object> mrisDiagnosedata : mrisDiagnose) {
                        if ((mrisDiagnosedata.get("disease_code") != null && mrisDiagnosedata.get("disease_code").equals("1"))
                                || (mrisDiagnosedata.get("disease_code1") != null && mrisDiagnosedata.get("disease_code1").equals("1"))) {
                            diagnoseMap.put("C03C", changeData(mrisDiagnosedata.get("disease_icd10_1")));
                            diagnoseMap.put("C04N", changeData(mrisDiagnosedata.get("disease_icd10_name1")));
                            diagnoseMap.put("C05C", changeData(mrisDiagnosedata.get("in_situation_code1")));
                            diagnoseMap.put("C06x01C", changeData(mrisDiagnosedata.get("disease_icd10_2")));
                            diagnoseMap.put("C07x01N", changeData(mrisDiagnosedata.get("disease_icd10_name2")));
                            diagnoseMap.put("C08x01C", changeData(mrisDiagnosedata.get("in_situation_code2")));
                        } else {
                            diagnoseMap.put("C06x0" + i + "C", changeData(mrisDiagnosedata.get("disease_icd10_1")));
                            diagnoseMap.put("C07x0" + i + "N", changeData(mrisDiagnosedata.get("disease_icd10_name1")));
                            diagnoseMap.put("C08x0" + i + "C", changeData(mrisDiagnosedata.get("in_situation_code1")));
                            diagnoseMap.put("C06x0" + (i + 1) + "C", changeData(mrisDiagnosedata.get("disease_icd10_2")));
                            diagnoseMap.put("C07x0" + (i + 1) + "N", changeData(mrisDiagnosedata.get("disease_icd10_name2")));
                            diagnoseMap.put("C08x0" + (i + 1) + "C", changeData(mrisDiagnosedata.get("in_situation_code2")));
                            i = i + 2;
                        }
                    }
                }
                ma.putAll(diagnoseMap);
                List<Map<String, Object>> mrisOperInfo = inptMrisInfoDAO.getMrisOperInfo(diagnoseParam);
                LinkedHashMap<String, Object> operInfoMap = new LinkedHashMap<>();
                if (mrisOperInfo != null && mrisOperInfo.size() > 0) {
                    Map<String, Object> dataMap = mrisOperInfo.get(0);
                    operInfoMap.put("C14x01C", changeData(dataMap.get("oper_disease_icd9")));
                    operInfoMap.put("C15x01N", changeData(dataMap.get("oper_disease_name")));
                    operInfoMap.put("C16x01", changeData(dataMap.get("oper_time")));
                    operInfoMap.put("C17x01", changeData(dataMap.get("oper_code")));
                    operInfoMap.put("F13", "");
                    operInfoMap.put("C18x01", changeData(dataMap.get("oper_doctor_name")));
                    operInfoMap.put("C19x01", changeData(dataMap.get("assistant_name1")));
                    operInfoMap.put("C20x01", changeData(dataMap.get("assistant_name2")));
                    operInfoMap.put("C21x01C", changeData(dataMap.get("heal_code")));
                    operInfoMap.put("C22x01C", changeData(dataMap.get("ana_code")));
                    operInfoMap.put("F15", "");
                    operInfoMap.put("C23x01", changeData(dataMap.get("ana_name1")));
                    for (int j = 1; j < mrisOperInfo.size(); j++) {
                        operInfoMap.put("C35x0" + j + "C", changeData(mrisOperInfo.get(j).get("oper_disease_icd9")));
                        operInfoMap.put("C36x0" + j + "N", changeData(mrisOperInfo.get(j).get("oper_disease_name")));
                        operInfoMap.put("C37x0" + j, changeData(mrisOperInfo.get(j).get("oper_time")));
                        operInfoMap.put("C38x0" + j, changeData(mrisOperInfo.get(j).get("oper_code")));
                        operInfoMap.put("F14x0" + j, "");
                        operInfoMap.put("C39x0" + j, changeData(mrisOperInfo.get(j).get("oper_doctor_name")));
                        operInfoMap.put("C40x0" + j, changeData(mrisOperInfo.get(j).get("assistant_name1")));
                        operInfoMap.put("C41x0" + j, changeData(mrisOperInfo.get(j).get("assistant_name2")));
                        operInfoMap.put("C42x0" + j + "C", changeData(mrisOperInfo.get(j).get("heal_code")));
                        operInfoMap.put("C43x0" + j + "C", changeData(mrisOperInfo.get(j).get("ana_code")));
                        operInfoMap.put("F16x0" + j, "");
                        operInfoMap.put("C44x0" + j, changeData(mrisOperInfo.get(j).get("ana_name1")));
                    }
                }
                ma.putAll(operInfoMap);
                ma.remove("visit_id");
            }
        }
        return data;
    }

    public String changeData(Object object){
        String data ="";
        if (object==null){
            return data;
        }
        data = (String) object;
        data = data.replace("\n","").replace("null","");
        return data;
    }
}
