package cn.hsa.inpt.medicalcheck.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.bd.dto.BaseDiseaseDTO;
import cn.hsa.module.base.bd.service.BaseDiseaseService;
import cn.hsa.module.inpt.doctor.dto.InptAdviceDTO;
import cn.hsa.module.inpt.doctor.dto.InptCostDTO;
import cn.hsa.module.inpt.doctor.dto.InptDiagnoseDTO;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.inpt.doctor.entity.InptDiagnoseDO;
import cn.hsa.module.inpt.medicalcheck.bo.MedicalCheckBO;
import cn.hsa.module.inpt.medicaltechnology.dao.MedicalTechnologyDAO;
import cn.hsa.module.oper.operInforecord.dao.OperInfoRecordDAO;
import cn.hsa.module.oper.operInforecord.dto.OperInfoRecordDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.util.*;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Package_name: cn.hsa.inpt.medicalcheck.bo.impl
 * @Class_name: MedicalCheckBOImpl
 * @Describe:
 * @Author: liaojunjie
 * @Email: junjie.liao@powersi.com
 * @Date: 2021/1/22 10:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Component
@Slf4j
public class MedicalCheckBOImpl extends HsafBO implements MedicalCheckBO {

    @Resource
    BaseDiseaseService baseDiseaseService;

    @Resource
    private MedicalTechnologyDAO medicalTechnologyDAO;
    @Resource
    private OperInfoRecordDAO operInfoRecordDAO;

    @Override
    public Map getBeforeCheckAdvice(Map<String, Object> map) {
        Map<String, Object> param = new HashMap<String, Object>();
        Map<String, Object> str = new HashMap<String, Object>();
        List<String> adviceList = new ArrayList<String>();
        String hospCode = MapUtils.get(map, "hospCode");
        List<InptAdviceDTO> inptAdviceDTOList = MapUtils.get(map, "inptAdviceDTOList");

        List<Map<String, String>> costList = new ArrayList<>();
        for (InptAdviceDTO inptAdviceDTO : inptAdviceDTOList) {
            Map costMap = new HashMap();
            costMap.put("unvln", inptAdviceDTO.getPrice() + "");
            costMap.put("num", inptAdviceDTO.getTotalNum() + "");
            costMap.put("discount_money", inptAdviceDTO.getTotalPrice() + "");
            costMap.put("prescr_doctor_id", inptAdviceDTO.getCrteId());
            costMap.put("prescr_dept_id", inptAdviceDTO.getDeptId());
            costMap.put("charge_detail_id", "");
            costMap.put("entiy_time", DateUtils.format(inptAdviceDTO.getCrteTime(), "yyyy-MM-dd HH:mm:ss"));
            costMap.put("items_id", inptAdviceDTO.getItemId());
            costMap.put("items_name", inptAdviceDTO.getItemName());
            if (Constants.XMLB.YZML.equals(inptAdviceDTO.getItemCode())
                    || Constants.XMLB.XM.equals(inptAdviceDTO.getItemCode())) {
                costMap.put("items_category", "2");
            } else if (Constants.XMLB.CL.equals(inptAdviceDTO.getItemCode())) {
                costMap.put("items_category", "5");
            } else {
                costMap.put("items_category", inptAdviceDTO.getItemCode());
            }
            costList.add(costMap);
        }
        // DRG??????????????????
        if (null == inptAdviceDTOList.get(0)) {
            throw new AppException("??????????????????");
        }
        Map<String, String> mapParameter = this.getParameterValue(hospCode, new String[]{"DRGIP","HOSPCODE"});
        String drgIp = MapUtils.getVS(mapParameter, "DRGIP", "");
        String hospitalId = MapUtils.getVS(mapParameter, "HOSPCODE", "");
        if (StringUtils.isEmpty(drgIp)) {
            throw new AppException("DRG???IP?????????????????????");
        }
        param.put("url", "http://" + drgIp + "/drg_web/api/call.action");
        str.put("function_id", "apiHnsService1001"); // ???????????????
        str.put("hospital_id", hospitalId); // ??????ID
        str.put("scene_type", "1"); // ???????????????1?????????????????????
        str.put("visit_id", inptAdviceDTOList.get(0).getVisitId());// ??????ID
        str.put("dept_id", inptAdviceDTOList.get(0).getInDeptId());// ????????????ID
        str.put("violations_monitor", adviceList);// ????????????
        str.put("charge_details", costList);// ????????????
        String json = JSONObject.toJSONString(str);
        param.put("param", json);
        String result = HttpConnectUtil.doPost(param);
        Map resultMap = new HashMap();
        try {
            Map xmlMap = XMLUtil.parseXmlToMap(result);
            if (xmlMap.get("return_code").equals("1")) {
                resultMap = xmlMap;
                resultMap.put("success", 1);
            } else {
                resultMap.put("success", 0);
            }
            resultMap.put("param", json);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return resultMap;
    }

    /**
     * ??????????????????
     *
     * @param hospCode
     * @param code
     * @return
     */
    public Map<String, String> getParameterValue(String hospCode, String[] code) {
        List<SysParameterDTO> list = medicalTechnologyDAO.getParameterValue(hospCode, code);
        Map<String, String> retMap = new HashMap<>();
        if (!MapUtils.isEmpty(list)) {
            for (SysParameterDTO hit : list) {
                retMap.put(hit.getCode(), hit.getValue());
            }
        }
        return retMap;
    }

    @Override
    public Map getBeforeCheckAdd(Map<String, Object> map) {
        Map<String, Object> param = new HashMap<String, Object>();
        String hospCode = MapUtils.get(map, "hospCode");
        Map<String, Object> str = new HashMap<String, Object>();
        List<String> adviceList = new ArrayList<String>();
        List<InptCostDTO> inptCostDTOS = MapUtils.get(map, "inptCostDTOS");

        if(ListUtils.isEmpty(inptCostDTOS)){
            throw new AppException("??????????????????????????????!");
        }

        List<Map<String, String>> costList = new ArrayList<>();
        for (InptCostDTO inptCostDTO : inptCostDTOS) {
            if (StringUtils.isEmpty(inptCostDTO.getVisitId())) {
                continue;
            }
            Map costMap = new HashMap();
            costList.add(costMap);
            costMap.put("unvln", inptCostDTO.getPrice() + "");
            costMap.put("num", inptCostDTO.getTotalNum() + "");
            costMap.put("discount_money", inptCostDTO.getTotalPrice() + "");
            costMap.put("prescr_doctor_id", map.get("userId"));
            costMap.put("prescr_dept_id", inptCostDTO.getDeptId());
            costMap.put("charge_detail_id", "");
            costMap.put("entiy_time", DateUtils.format(inptCostDTO.getCrteTime(), "yyyy-MM-dd HH:mm:ss"));
            costMap.put("items_id", inptCostDTO.getItemId());
            costMap.put("items_name", inptCostDTO.getItemName());
            if (Constants.XMLB.YZML.equals(inptCostDTO.getItemCode())
                    || Constants.XMLB.XM.equals(inptCostDTO.getItemCode())) {
                costMap.put("items_category", "2");
            } else if (Constants.XMLB.CL.equals(inptCostDTO.getItemCode())) {
                costMap.put("items_category", "5");
            } else {
                costMap.put("items_category", inptCostDTO.getItemCode());
            }
        }
        // DRG??????????????????
        Map<String, String> mapParameter = this.getParameterValue(hospCode, new String[]{"DRGIP","HOSPCODE"});
        String drgIp = MapUtils.getVS(mapParameter, "DRGIP", "");
        String hospitalId = MapUtils.getVS(mapParameter, "HOSPCODE", "");
        if (StringUtils.isEmpty(drgIp)) {
            throw new AppException("DRG???IP?????????????????????");
        }
        param.put("url", "http://" + drgIp + "/drg_web/api/call.action");
        str.put("function_id", "apiHnsService1001");
        str.put("hospital_id", hospitalId);
        str.put("scene_type", "1");
        str.put("visit_id", inptCostDTOS.get(0).getVisitId());
        str.put("dept_id", inptCostDTOS.get(0).getInDeptId());
        str.put("violations_monitor", adviceList);
        str.put("charge_details", costList);
        String json = JSONObject.toJSONString(str);
        param.put("param", json);
        String result = HttpConnectUtil.doPost(param);
        Map resultMap = new HashMap();
        try {
            Map xmlMap = XMLUtil.parseXmlToMap(result);
            if (xmlMap.get("return_code").equals("1")) {
                resultMap = xmlMap;
                resultMap.put("success", 1);
            } else {
                resultMap.put("success", 0);
            }
            resultMap.put("param", json);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return resultMap;
    }


    /**
     * @Method ??????????????????
     * @Desrciption ????????????????????????????????????????????????????????????????????????????????????????????????
     * ?????????????????????????????????????????????*???????????????????????????????????????????????????????????????
     * ????????????????????????????????????????????????????????????
     * @Param xm??????, xb??????, nl??????, zfy?????????, sjzyts??????????????????, rybq????????????, rytj????????????
     * lyfs????????????,zyzd??????????????????,jbdm???????????????
     * @Author zhangxuan
     * @Date 2021-01-23 9:40
     * @Return
     **/
    @Override
    public Map getDagns(Map<String, Object> map) {
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
        List<InptDiagnoseDTO> inptDiagnoseDTOS = MapUtils.get(map, "inptDiagnoseDTOS");
        // ?????????????????????????????????ICD10,???????????????
        List<String> ids = inptDiagnoseDTOS.stream().map(InptDiagnoseDO::getDiseaseId).collect(Collectors.toList());
        Map diseaseMap = new HashMap();
        BaseDiseaseDTO baseDiseaseDTO = new BaseDiseaseDTO();
        baseDiseaseDTO.setHospCode((String) map.get("hospCode"));
        baseDiseaseDTO.setIds(ids);
        diseaseMap.put("hospCode", map.get("hospCode"));
        diseaseMap.put("baseDiseaseDTO", baseDiseaseDTO);
        List<BaseDiseaseDTO> diseaseByIds = baseDiseaseService.getDiseaseByIds(diseaseMap);
        Map<String, BaseDiseaseDTO> collect = diseaseByIds.stream().collect(Collectors.toMap(BaseDiseaseDTO::getId, Function.identity(), (key1, key2) -> key2));
        StringBuffer sb = new StringBuffer();
        Map<String, String> mapParameter = this.getParameterValue((String) map.get("hospCode"), new String[]{"DIPIP"});
        String dipIp = MapUtils.getVS(mapParameter, "DIPIP", "");
        if (StringUtils.isEmpty(dipIp)) {
            throw new AppException("DIP???IP?????????????????????");
        }
        sb.append(" http://"+dipIp+"/drg_web/drgGroupThird/drg_dagns/list.action");
        sb.append("?xm=");
        sb.append(inptVisitDTO.getName());
        sb.append("&");
        sb.append("dept_code=");
        sb.append(inptVisitDTO.getGenderCode());
        sb.append("&");
        sb.append("nl=");
        sb.append(inptVisitDTO.getAge());
        sb.append("&");
        sb.append("zfy=");
        sb.append(inptVisitDTO.getTotalCost());
        sb.append("&");
        sb.append("sjzyts=");
        // ??????????????????
        if (inptVisitDTO.getOutTime() == null) {
            sb.append(daysBetween(inptVisitDTO.getInTime(), DateUtils.getNow()) + "");
        } else {
            sb.append(daysBetween(inptVisitDTO.getInTime(), inptVisitDTO.getOutTime()) + "");
        }
        // ????????????(??????4.7???????????????)??????????????????RYQK
        sb.append("&");
        sb.append("rybq=");
        if (Constants.RYQK.W.equals(inptVisitDTO.getInSituationCode()) || Constants.RYQK.J.equals(inptVisitDTO.getInSituationCode())) {
            sb.append("1");
        } else {
            sb.append("2");
        }
        sb.append("&");
        sb.append("rytj=");
        // ????????????
        if (Constants.RYTJ.ZJRY.equals(inptVisitDTO.getInModeCode()) || Constants.RYTJ.QT.equals(inptVisitDTO.getInModeCode())) {
            sb.append("9");
        } else {
            sb.append(inptVisitDTO.getInModeCode());
        }
        sb.append("&");
        sb.append("lyfs=");
        // ????????????
        if (StringUtils.isNotEmpty(inptVisitDTO.getOutModeCode())) {
            sb.append(inptVisitDTO.getOutModeCode());
        } else {
            sb.append("1");
        }
        if (!inptVisitDTO.getAgeUnitCode().equals("1")) {
            // ??????????????????????????????
            sb.append("&");
            sb.append("lyfs=");
            sb.append("0");
            // ??????????????????????????????
            sb.append("&");
            sb.append("lyfs=");
            sb.append("0");
            // ??????????????????????????????
            sb.append("&");
            sb.append("lyfs=");
            sb.append(daysBetween(inptVisitDTO.getBirthday(), DateUtils.getNow()));
        }

        int i = 0;
        for (InptDiagnoseDTO inptDiagnoseDTO : inptDiagnoseDTOS) {
            if (Constants.SF.S.equals(inptDiagnoseDTO.getIsMain())) {
                sb.append("&");
                sb.append("zyzd=");
                sb.append(inptDiagnoseDTO.getDiseaseName());
                sb.append("&");
                sb.append("jbdm=");
                sb.append(collect.get(inptDiagnoseDTO.getDiseaseId()).getNationCode());
                continue;
            } else {
                i++;
                sb.append("&");
                sb.append("jbdm" + i + "=");
                sb.append(collect.get(inptDiagnoseDTO.getDiseaseId()).getNationCode());
            }
        }
        OperInfoRecordDTO operInfoRecordDTO = new OperInfoRecordDTO();
        operInfoRecordDTO.setHospCode(inptVisitDTO.getHospCode());
        operInfoRecordDTO.setVisitId(inptVisitDTO.getId());
        List<OperInfoRecordDTO> operInfoRecordDTOS = operInfoRecordDAO.queryOperInfoRecordList(operInfoRecordDTO);
        i =1;
        for (OperInfoRecordDTO operInfoRecord : operInfoRecordDTOS) {
            if(StringUtils.isEmpty(operInfoRecord.getOperDiseaseIcd9()) || StringUtils.isEmpty(operInfoRecord.getOperDiseaseName())){
                continue;
            }

            sb.append("&");
            sb.append("ssjczbm"+i+"=");
            sb.append(operInfoRecord.getOperDiseaseIcd9());
            sb.append("&");
            sb.append("ssjczmc"+i+"=");
            sb.append(operInfoRecord.getOperDiseaseName());
            i++;
        }
        String URL = sb.toString();
        Map resultMap = new HashMap();
        resultMap.put("success", "1");
        resultMap.put("html", URL);
        return resultMap;
    }

    @Override
    public Map getDagnsDIP(Map<String, Object> map) {
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
        List<InptDiagnoseDTO> inptDiagnoseDTOS = MapUtils.get(map, "inptDiagnoseDTOS");
        // ?????????????????????????????????ICD10,???????????????
        List<String> ids = inptDiagnoseDTOS.stream().map(InptDiagnoseDO::getDiseaseId).collect(Collectors.toList());
        Map diseaseMap = new HashMap();
        BaseDiseaseDTO baseDiseaseDTO = new BaseDiseaseDTO();
        baseDiseaseDTO.setHospCode((String) map.get("hospCode"));
        baseDiseaseDTO.setIds(ids);
        diseaseMap.put("hospCode", map.get("hospCode"));
        diseaseMap.put("baseDiseaseDTO", baseDiseaseDTO);
        List<BaseDiseaseDTO> diseaseByIds = baseDiseaseService.getDiseaseByIds(diseaseMap);
        Map<String, BaseDiseaseDTO> collect = diseaseByIds.stream().collect(Collectors.toMap(BaseDiseaseDTO::getId, Function.identity(), (key1, key2) -> key2));
        StringBuffer sb = new StringBuffer();
        Map<String, String> mapParameter = this.getParameterValue((String) map.get("hospCode"), new String[]{"DIPIP"});
        String dipIp = MapUtils.getVS(mapParameter, "DIPIP", "");
        if (StringUtils.isEmpty(dipIp)) {
            throw new AppException("DIP???IP?????????????????????");
        }
        sb.append(" http://" + dipIp + "/drg_web/drgGroupThird/dip_dagns/list.action");
        sb.append("?xm=");
        sb.append(inptVisitDTO.getName());
        sb.append("&");
        sb.append("dept_code=");
        sb.append(inptVisitDTO.getGenderCode());
        sb.append("&");
        sb.append("nl=");
        sb.append(inptVisitDTO.getAge());
        sb.append("&");
        sb.append("zfy=");
        sb.append(inptVisitDTO.getTotalCost());
        sb.append("&");
        sb.append("sjzyts=");
        // ??????????????????
        if (inptVisitDTO.getOutTime() == null) {
            sb.append(daysBetween(inptVisitDTO.getInTime(), DateUtils.getNow()) + "");
        } else {
            sb.append(daysBetween(inptVisitDTO.getInTime(), inptVisitDTO.getOutTime()) + "");
        }
        // ????????????(??????4.7???????????????)??????????????????RYQK
        sb.append("&");
        sb.append("rybq=");
        if (Constants.RYQK.W.equals(inptVisitDTO.getInSituationCode()) || Constants.RYQK.J.equals(inptVisitDTO.getInSituationCode())) {
            sb.append("1");
        } else {
            sb.append("2");
        }
        sb.append("&");
        sb.append("rytj=");
        // ????????????
        if (Constants.RYTJ.ZJRY.equals(inptVisitDTO.getInModeCode()) || Constants.RYTJ.QT.equals(inptVisitDTO.getInModeCode())) {
            sb.append("9");
        } else {
            sb.append(inptVisitDTO.getInModeCode());
        }
        sb.append("&");
        sb.append("lyfs=");
        // ????????????
        if (StringUtils.isNotEmpty(inptVisitDTO.getOutModeCode())) {
            sb.append(inptVisitDTO.getOutModeCode());
        } else {
            sb.append("1");
        }
        if (!inptVisitDTO.getAgeUnitCode().equals("1")) {
            // ??????????????????????????????
            sb.append("&");
            sb.append("lyfs=");
            sb.append("0");
            // ??????????????????????????????
            sb.append("&");
            sb.append("lyfs=");
            sb.append("0");
            // ??????????????????????????????
            sb.append("&");
            sb.append("lyfs=");
            sb.append(daysBetween(inptVisitDTO.getBirthday(), DateUtils.getNow()));
        }

        int i = 0;
        for (InptDiagnoseDTO inptDiagnoseDTO : inptDiagnoseDTOS) {
            if (Constants.SF.S.equals(inptDiagnoseDTO.getIsMain())) {
                sb.append("&");
                sb.append("zyzd=");
                sb.append(inptDiagnoseDTO.getDiseaseName());
                sb.append("&");
                sb.append("jbdm=");
                sb.append(collect.get(inptDiagnoseDTO.getDiseaseId()).getNationCode());
                continue;
            } else {
                i++;
                sb.append("&");
                sb.append("jbdm" + i + "=");
                sb.append(collect.get(inptDiagnoseDTO.getDiseaseId()).getNationCode());
            }
        }
        OperInfoRecordDTO operInfoRecordDTO = new OperInfoRecordDTO();
        operInfoRecordDTO.setHospCode(inptVisitDTO.getHospCode());
        operInfoRecordDTO.setVisitId(inptVisitDTO.getId());
        List<OperInfoRecordDTO> operInfoRecordDTOS = operInfoRecordDAO.queryOperInfoRecordList(operInfoRecordDTO);
        i =1;
        for (OperInfoRecordDTO operInfoRecord : operInfoRecordDTOS) {

            if(StringUtils.isEmpty(operInfoRecord.getOperDiseaseIcd9()) || StringUtils.isEmpty(operInfoRecord.getOperDiseaseName())){
                continue;
            }

            sb.append("&");
            sb.append("ssjczbm"+i+"=");
            sb.append(operInfoRecord.getOperDiseaseIcd9());
            sb.append("&");
            sb.append("ssjczbm"+i+"=");
            sb.append(operInfoRecord.getOperDiseaseName());
            i++;
        }
        String URL = sb.toString();
        Map resultMap = new HashMap();
        resultMap.put("success", "1");
        resultMap.put("html", URL);
        return resultMap;
    }

    /**
     * @Description: ??????????????????id???????????????????????????????????????
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/3/15 15:43
     * @Return
     */
    @Override
    public boolean checkConfirmCost(Map<String, Object> map) {
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
        return medicalTechnologyDAO.getConfirmCost(inptVisitDTO) > 0;
    }

    /**
     * @Method getBeforeDRG
     * @Desrciption DRG????????????
     * @Param [map]
     * @Author liaojunjie
     * @Date 2021/1/26 10:32
     * @Return java.util.Map
     **/
    @Override
    public Map getBeforeDRG(Map<String, Object> map) {
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
        String hospCode = MapUtils.get(map, "hospCode");
        StringBuffer sb = new StringBuffer();
        Map<String, String> mapParameter = this.getParameterValue(hospCode, new String[]{"DRGIP"});
        String drgIp = MapUtils.getVS(mapParameter, "DRGIP", "");
        if (StringUtils.isEmpty(drgIp)) {
            throw new AppException("DRG???IP?????????????????????");
        }
        sb.append("http://" + drgIp + "/drg_web/drgGroupThird/drg_paper/list.action");
        sb.append("?visit_id=");
        sb.append(inptVisitDTO.getVisitId());
        String URL = sb.toString();
        Map resultMap = new HashMap();
        resultMap.put("success", "1");
        resultMap.put("html", URL);
        return resultMap;

    }

    /**
     * @Description: DIP????????????
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/4/14 15:56
     * @Return
     */
    @Override
    public Map getBeforeDIP(Map<String, Object> map) {
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
        StringBuffer sb = new StringBuffer();
        Map<String, String> mapParameter = this.getParameterValue((String) map.get("hospCode"), new String[]{"DIPIP"});
        String dipip = MapUtils.getVS(mapParameter, "DIPIP", "");
        if (StringUtils.isEmpty(dipip)) {
            throw new AppException("DIP???IP?????????????????????");
        }
        // http://172.18.23.18:8080/drg_web/drgGroupThird/dig_paper/list.action
        sb.append("http://" + dipip + "/drg_web/drgGroupThird/dip_paper/list.action");
        sb.append("?visit_id=");
        sb.append(inptVisitDTO.getVisitId());// ?????????
        String URL = sb.toString();
//        String html = HttpConnectUtil.doGet(URL);
        Map resultMap = new HashMap();
        resultMap.put("success", "1");
        resultMap.put("html", URL);
        return resultMap;

    }


    /**
     * @Method getBeforeOutHosp
     * @Desrciption ??????????????????
     * @Param [map]
     * @Author liaojunjie
     * @Date 2021/1/26 10:32
     * @Return java.util.Map
     **/
    @Override
    public Map getBeforeOutHosp(Map<String, Object> map) {
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
        String hospCode = MapUtils.get(map, "hospCode");
        Map<String, Object> param = new HashMap<String, Object>();
        Map<String, String> mapParameter = this.getParameterValue(hospCode, new String[]{"DRGIP","HOSPCODE"});
        String drgIp = MapUtils.getVS(mapParameter, "DRGIP", "");
        String hospitalId = MapUtils.getVS(mapParameter, "HOSPCODE", "");
        if (StringUtils.isEmpty(drgIp)) {
            throw new AppException("DRG???IP?????????????????????");
        }
        param.put("url", "http://" + drgIp + "/drg_web/api/call.action");
        Map<String, Object> str = new HashMap<String, Object>();
        Map returnMap = new HashMap();
        str.put("function_id", "apiHnsService1001");
        str.put("hospital_id", hospitalId);
        str.put("scene_type", "6");
        str.put("visit_id", inptVisitDTO.getVisitId());
        String json = JSONObject.toJSONString(str);
        param.put("param", json);
        String result = HttpConnectUtil.doPost(param);
        Map resultMap = new HashMap();
        try {
            Map xmlMap = XMLUtil.parseXmlToMap(result);
            if (xmlMap.get("return_code").equals("1")) {
                resultMap = xmlMap;
                resultMap.put("success", 1);
            } else {
                resultMap.put("success", 0);
            }
            resultMap.put("param", json);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return resultMap;
    }


    /**
     * @Method getBeforeRecord
     * @Desrciption ????????????????????????
     * @Param [map]
     * @Author liaojunjie
     * @Date 2021/1/26 10:34
     * @Return java.util.Map
     **/
    @Override
    public Map getBeforeRecord(Map<String, Object> map) {
        InptVisitDTO inptVisitDTO = MapUtils.get(map, "inptVisitDTO");
        String hospCode = MapUtils.get(map, "hospCode");
        StringBuffer sb = new StringBuffer();
        Map<String, String> mapParameter = this.getParameterValue(hospCode, new String[]{"DRGIP","HOSPCODE"});
        String drgIp = MapUtils.getVS(mapParameter, "DRGIP", "");
        String hospitalId = MapUtils.getVS(mapParameter, "HOSPCODE", "");
        if (StringUtils.isEmpty(drgIp)) {
            throw new AppException("DRG???IP?????????????????????");
        }
        sb.append("http://"+drgIp+"/drg_web/drgGroupThird/drg_quality/list.action");
        sb.append("?visit_id=");
        sb.append(inptVisitDTO.getId());
        String URL = sb.toString();
        Map resultMap = new HashMap();
        resultMap.put("success", "1");
        resultMap.put("html", URL);
        return resultMap;
    }


    /**
     * ???????????????????????????????????????
     *
     * @param smdate ???????????????
     * @param bdate  ???????????????
     * @return ????????????
     */
    public static int daysBetween(Date smdate, Date bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Long between_days = 0L;
        try {
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            between_days = (time2 - time1) / (1000 * 3600 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            return Integer.parseInt(String.valueOf(between_days));
        }
    }

    public static String doPost(Map<String, Object> param) {
        String URL = MapUtils.get(param, "url");
        String paramStr = MapUtils.get(param, "param");
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        HttpURLConnection conn = null;
        try {
            URL url = new URL(URL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            //??????POST?????????????????????true
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //?????????????????????????????????????????????
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-length",
                    String.valueOf(paramStr.getBytes().length));
            DataOutputStream printout = new DataOutputStream(
                    conn.getOutputStream());
            printout.write(Base64.encodeBase64(paramStr.getBytes()));
            printout.flush();
            printout.close();
            //???????????????????????????Reader??????
            if (200 == conn.getResponseCode()) {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
            } else {
                throw new RuntimeException("?????????????????????");
            }
        } catch (Exception e) {
            throw new RuntimeException("?????????????????????");
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                return result.toString();
            }
        }
    }


}
