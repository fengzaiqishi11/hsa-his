package cn.hsa.mris.mrisHome.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.mris.mrisHome.bo.InptMrisInfoBO;
import cn.hsa.module.mris.mrisHome.dao.InptMrisInfoDAO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import com.alibaba.fastjson.JSON;
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

    @Resource
    SysParameterService sysParameterService_consumer;

    @Override
    public List<LinkedHashMap<String, Object>> importMrisInfo(Map map) throws Exception {
//        map.put("type","N042");
//        List<Map<String, Object>> uploadTypeList = inptMrisInfoDAO.queryUploadType(map);
        List<LinkedHashMap<String, Object>> data =inptMrisInfoDAO.getMrisBaseInfo(map);
        SysParameterDTO sysParameterDTO =null;
        Map<String, Object> isInsureUnifiedMap = new HashMap<>();
        isInsureUnifiedMap.put("hospCode", map.get("hospCode"));
        isInsureUnifiedMap.put("code", "BASFBM");
        sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
        Map sfMaps =null;
        // 获取病案首页省份编码
        if(sysParameterDTO !=null) {
            if (StringUtils.isNotEmpty(sysParameterDTO.getValue())) {
                sfMaps = (Map) JSON.parse(sysParameterDTO.getValue());
            }
        }
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
                if (!MapUtils.isEmpty(sfMaps)) {
                    String key = (String) ma.get("A23C");
                    if (StringUtils.isNotEmpty(key)) {
                        ma.put("A23C", sfMaps.get(key)!=null?sfMaps.get(key):"18");
                    }
                }else {
                    ma.put("A23C", "18");
                }
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
                        if (mrisOperInfo.get(j)!=null) {
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
                }
                ma.putAll(operInfoMap);
                ma.remove("visit_id");
            }
        }
        return data;
    }

    public String changeData(Object object){
        String data ="-";
        if (object==null){
            return data;
        }
        data = (String) object;
        if (StringUtils.isEmpty(data)){
            data="-";
        }
        data = data.replace("\n","").replace("null","").replace("\t","");
        return data;
    }


    @Override
    public List<LinkedHashMap<String, Object>> importTcmMrisInfo(Map map) throws Exception {
        List<LinkedHashMap<String, Object>> data =inptMrisInfoDAO.getTcmMrisBaseInfo(map);
        SysParameterDTO sysParameterDTO =null;
        Map<String, Object> isInsureUnifiedMap = new HashMap<>();
        isInsureUnifiedMap.put("hospCode", map.get("hospCode"));
        isInsureUnifiedMap.put("code", "BASFBM");
        sysParameterDTO = sysParameterService_consumer.getParameterByCode(isInsureUnifiedMap).getData();
        Map sfMaps =null;
        // 获取病案首页省份编码
        if(sysParameterDTO !=null) {
            if (StringUtils.isNotEmpty(sysParameterDTO.getValue())) {
                sfMaps = (Map) JSON.parse(sysParameterDTO.getValue());
            }
        }
        if (data!=null&&data.size()>0) {
            for (LinkedHashMap<String, Object> ma : data) {
                Map diagnoseParam = new HashMap();
                diagnoseParam.put("hospCode", (String) map.get("hospCode"));
                diagnoseParam.put("visitId", ma.get("visit_id"));
                if (!MapUtils.isEmpty(sfMaps)) {
                    String key = (String) ma.get("GG");
                    if (StringUtils.isNotEmpty(key)) {
                        ma.put("GG", sfMaps.get(key)!=null?sfMaps.get(key):"18");
                    }
                }else {
                    ma.put("GG", "18");
                }
                // 西医出院诊断
                List<Map<String, Object>> mrisDiagnose = inptMrisInfoDAO.getTcmMrisDiagnose(diagnoseParam);
                LinkedHashMap<String, Object> diagnoseMap = new LinkedHashMap<>();
                int i = 1;
                if (mrisDiagnose != null && mrisDiagnose.size() > 0) {
                    for (Map<String, Object> mrisDiagnosedata : mrisDiagnose) {
                        if (mrisDiagnosedata.get("disease_code") != null && mrisDiagnosedata.get("disease_code").equals("1")) {
                            diagnoseMap.put("ZYZD", mrisDiagnose.get(0).get("disease_icd10_name"));
                            diagnoseMap.put("ZYZD_JBBM", mrisDiagnose.get(0).get("disease_icd10"));
                            diagnoseMap.put("XY_RYBQ", mrisDiagnose.get(0).get("in_situation_code"));
                            diagnoseMap.put("XY_CYQK", "-");
                        } else {
                            diagnoseMap.put("QTZD" + i, changeData(mrisDiagnosedata.get("disease_icd10_name")));
                            diagnoseMap.put("ZYZD_JBBM" +i, changeData(mrisDiagnosedata.get("disease_icd10")));
                            diagnoseMap.put("RYBQ" + i, changeData(mrisDiagnosedata.get("in_situation_code")));
                            diagnoseMap.put("CYQK" + i, "-");
                            i = i + 1;
                        }
                    }
                }
                ma.putAll(diagnoseMap);
                // 中医出院诊断
                List<Map<String, Object>> tcmMrisDiagnose = inptMrisInfoDAO.getTcmDiagnose(diagnoseParam);
                LinkedHashMap<String, Object> tcmDiagnoseMap = new LinkedHashMap<>();
                int k = 1;
                if (tcmMrisDiagnose != null && tcmMrisDiagnose.size() > 0) {
                    for (Map<String, Object> mrisDiagnosedata : tcmMrisDiagnose) {
                        if ((mrisDiagnosedata.get("disease_code") != null && mrisDiagnosedata.get("disease_code").equals("1"))) {
                            tcmDiagnoseMap.put("ZB", changeData(mrisDiagnosedata.get("disease_icd10_name")));
                            tcmDiagnoseMap.put("ZB_JBBM", changeData(mrisDiagnosedata.get("disease_icd10")));
                            tcmDiagnoseMap.put("ZY_RYBQ_ZB", changeData(mrisDiagnosedata.get("in_situation_code")));
                            tcmDiagnoseMap.put("ZY_CYQK_ZB", "-");
                        } else {
                            tcmDiagnoseMap.put("ZZ" + k, changeData(mrisDiagnosedata.get("tcm_syndromes_name")));
                            tcmDiagnoseMap.put("ZZ_JBBM" +k, changeData(mrisDiagnosedata.get("tcm_syndromes_id")));
                            tcmDiagnoseMap.put("ZZ_RYBQ" + k, changeData(mrisDiagnosedata.get("in_situation_code")));
                            tcmDiagnoseMap.put("ZZ_CYQK" + k, "-");
                            tcmDiagnoseMap.put("ZZ_ZFMC" + k, "-");
                            tcmDiagnoseMap.put("ZZ_ZFBM" + k, "-");
                            k = k + 1;
                        }
                    }
                }
                ma.putAll(tcmDiagnoseMap);
                List<Map<String, Object>> tcmMrisOperInfo = inptMrisInfoDAO.getTcmMrisOperInfo(diagnoseParam);
                LinkedHashMap<String, Object> tcmOperInfoMap = new LinkedHashMap<>();
                if (tcmMrisOperInfo != null && tcmMrisOperInfo.size() > 0) {
                    Map<String, Object> dataMap = tcmMrisOperInfo.get(0);
                    tcmOperInfoMap.put("SSJCZBM1", changeData(dataMap.get("oper_disease_icd9")));
                    tcmOperInfoMap.put("SSJCZMC1", changeData(dataMap.get("oper_disease_name")));
                    tcmOperInfoMap.put("SSJCZRQ1", changeData(dataMap.get("oper_time")));
                    tcmOperInfoMap.put("SHJB1", changeData(dataMap.get("oper_code")));
                    tcmOperInfoMap.put("SZ1", changeData(dataMap.get("oper_doctor_name")));
                    tcmOperInfoMap.put("YZ1", changeData(dataMap.get("assistant_name1")));
                    tcmOperInfoMap.put("EZ1", changeData(dataMap.get("assistant_name2")));
                    tcmOperInfoMap.put("QKDJ1", changeData(dataMap.get("heal_code")));
                    tcmOperInfoMap.put("QKYLB1", changeData(dataMap.get("heal_type")));
                    tcmOperInfoMap.put("MZFS1", changeData(dataMap.get("ana_code")));
                    tcmOperInfoMap.put("MZYS1", changeData(dataMap.get("ana_name1")));
                    tcmOperInfoMap.put("SSCZSJ1", "");
                    tcmOperInfoMap.put("MZFJ1", "");
                    for (int j = 1; j < tcmMrisOperInfo.size(); j++) {
                        if (tcmMrisOperInfo.get(j)!=null) {
                            tcmOperInfoMap.put("SSJCZBM" + j, changeData(dataMap.get("oper_disease_icd9")));
                            tcmOperInfoMap.put("SSJCZMC" + j, changeData(dataMap.get("oper_disease_name")));
                            tcmOperInfoMap.put("SSJCZRQ"  + j, changeData(dataMap.get("oper_time")));
                            tcmOperInfoMap.put("SHJB" + j, changeData(dataMap.get("oper_code")));
                            tcmOperInfoMap.put("SZ" + j, changeData(dataMap.get("oper_doctor_name")));
                            tcmOperInfoMap.put("YZ" + j, changeData(dataMap.get("assistant_name1")));
                            tcmOperInfoMap.put("EZ" + j, changeData(dataMap.get("assistant_name2")));
                            tcmOperInfoMap.put("QKDJ" + j, changeData(dataMap.get("heal_code")));
                            tcmOperInfoMap.put("QKYLB" + j, changeData(dataMap.get("heal_type")));
                            tcmOperInfoMap.put("MZFS" + j, changeData(dataMap.get("ana_code")));
                            tcmOperInfoMap.put("MZYS" + j, changeData(dataMap.get("ana_name1")));
                            tcmOperInfoMap.put("SSCZSJ" + j, "");
                            tcmOperInfoMap.put("MZFJ" + j, "");
                        }
                    }
                }
                ma.putAll(tcmOperInfoMap);
                ma.remove("visit_id");
            }
        }
        return data;
    }
}
