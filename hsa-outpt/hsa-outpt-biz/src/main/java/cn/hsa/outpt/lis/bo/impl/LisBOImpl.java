package cn.hsa.outpt.lis.bo.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.ba.dto.BaseAdviceDTO;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.base.dept.service.BaseDeptService;
import cn.hsa.module.medic.data.dto.MedicTypeEnum;
import cn.hsa.module.medic.data.dto.MedicalDataDTO;
import cn.hsa.module.medic.data.dto.MedicalDataDetailDTO;
import cn.hsa.module.medic.result.dto.MedicalResultDTO;
import cn.hsa.module.outpt.lis.bo.LisBO;
import cn.hsa.module.outpt.lis.dao.LisDao;
import cn.hsa.module.outpt.lis.dto.LisPdfDTO;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Package_name: cn.hsa.outpt.lis.service.impl
 * @Class_name: LisBOImpl
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2021-01-04 10:07
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class LisBOImpl implements LisBO {

    @Resource
    LisDao lisDao;

    private Logger logger = LoggerFactory.getLogger(LisBOImpl.class);

    /**
     * @Method getDeptList
     * @Desrciption 科室接口
     * 1.查询配置表
     * 2.如果是填写的sql，那么直接执行
     * 3.如果没有填写sql，那么去配置明细表查找配置的字段，动态拼接sql,执行
     * 4.发请求
       @params [map]
       1、hoapCode
     * @Author chenjun
     * @Date   2021-01-13 14:37
     * @Return java.lang.Boolean
    **/
    @Override
    public Boolean modifyDeptList(Map map) {
        try {
            //获取配置信息
            MedicalDataDTO medicalDataDTO = getMedicData(map.get("hospCode").toString(), MedicTypeEnum.LIS_KS.getCode().toString());
            if (medicalDataDTO == null) {
                throw new AppException("获取lis科室同步配置信息为空");
            }

            //拼接sql
            StringBuffer sql = new StringBuffer();
            if (!StringUtils.isEmpty(medicalDataDTO.getTableSql())) {
                //配置了sql信息
                sql.append(medicalDataDTO.getTableSql());
            } else {
                //获取配置明细信息
                List<MedicalDataDetailDTO> detailDTOList = getMedicDataDetails(medicalDataDTO);

                sql.append("select ");
                for (int i=0;i<detailDTOList.size();i++) {
                    if (i == detailDTOList.size()-1) {
                        if (StringUtils.isEmpty(detailDTOList.get(i).getResultEName())) {
                            sql.append(detailDTOList.get(i).getResourceEName());
                        } else {
                            sql.append(detailDTOList.get(i).getResultEName()).append(" as ").append(detailDTOList.get(i).getResourceEName());
                        }
                    } else {
                        if (StringUtils.isEmpty(detailDTOList.get(i).getResultEName())) {
                            sql.append(detailDTOList.get(i).getResourceEName()).append(",");
                        } else {
                            sql.append(detailDTOList.get(i).getResultEName()).append(" as ").append(detailDTOList.get(i).getResourceEName()).append(",");
                        }
                    }
                }
                sql.append(" from ").append(medicalDataDTO.getResultName()).append(" where hosp_code='").append(medicalDataDTO.getHospCode())
                        .append("' and is_valid='1'");
            }
            List<Map> list = lisDao.getLisDataList(sql.toString());
            if (ListUtils.isEmpty(list)) {
                throw new AppException("未获取到科室数据");
            }
            String param = toJSONString2(list);
            if ("GET".equals(medicalDataDTO.getInterfaceType().toUpperCase())) {
                sendUrlGet(medicalDataDTO.getInterfaceUrl(), param);
            } else if ("POST".equals(medicalDataDTO.getInterfaceType().toUpperCase())) {
                sendUrlPost(medicalDataDTO.getInterfaceUrl(), param);
            } else {
                throw new AppException("请求方式未匹配到");
            }
        } catch (AppException e) {
            throw new AppException("lis同步科室失败:"+e.getMessage());
        }
//        String url = MapUtils.get(map, "url");
//        map.put("isValid", "1");
//        List<BaseDeptDTO> deptList = lisDao.getDeptList(map);
//        if(ListUtils.isEmpty(deptList)){
//            throw new AppException("科室列表为空");
//        }
//        List<Map> deptListParam = new ArrayList<>();
//        deptList.forEach(dto -> {
//            Map map1 = new HashMap();
//            map1.put("areaCode", dto.getHospCode());
//            map1.put("name", dto.getName());
//            map1.put("code", dto.getId());
//            map1.put("source", "HIS");
//            map1.put("isUse", 1);
//            deptListParam.add(map1);
//        });
//        String param = toJSONString2(deptListParam);
//        sendUrlPost(Constants.LISURL.DEPTURL, param);
        return true;
    }

    /**
     * @Method getDocList
     * @Desrciption 医生接口
       @params [map]
       1、 hospCode
     * @Author chenjun
     * @Date   2021-01-13 15:00
     * @Return java.lang.Boolean
    **/
    @Override
    public Boolean getDocList(Map map) {
        try {
            //获取配置信息
            MedicalDataDTO medicalDataDTO = getMedicData(map.get("hospCode").toString(), MedicTypeEnum.LIS_YS.getCode().toString());
            if (medicalDataDTO == null) {
                throw new AppException("获取lis医生同步配置信息为空");
            }

            //拼接sql
            StringBuffer sql = new StringBuffer();
            if (!StringUtils.isEmpty(medicalDataDTO.getTableSql())) {
                //配置了sql信息
                sql.append(medicalDataDTO.getTableSql());
            } else {
                //获取配置明细信息
                List<MedicalDataDetailDTO> detailDTOList = getMedicDataDetails(medicalDataDTO);

                sql.append("select ");
                for (int i=0;i<detailDTOList.size();i++) {
                    if (i == detailDTOList.size()-1) {
                        if (StringUtils.isEmpty(detailDTOList.get(i).getResultEName())) {
                            sql.append(detailDTOList.get(i).getResourceEName());
                        } else {
                            sql.append(detailDTOList.get(i).getResultEName()).append(" as ").append(detailDTOList.get(i).getResourceEName());
                        }
                    } else {
                        if (StringUtils.isEmpty(detailDTOList.get(i).getResultEName())) {
                            sql.append(detailDTOList.get(i).getResourceEName()).append(",");
                        } else {
                            sql.append(detailDTOList.get(i).getResultEName()).append(" as ").append(detailDTOList.get(i).getResourceEName()).append(",");
                        }
                    }
                }
                sql.append(" from ").append(medicalDataDTO.getResultName()).append(" where hosp_code='")
                        .append(medicalDataDTO.getHospCode()).append("'");
            }
            List<Map> list = lisDao.getLisDataList(sql.toString());
            if (ListUtils.isEmpty(list)) {
                throw new AppException("未获取到医生数据");
            }
            String param = JSONObject.toJSONString(list);
            if ("GET".equals(medicalDataDTO.getInterfaceType().toUpperCase())) {
                sendUrlGet(medicalDataDTO.getInterfaceUrl(), param);
            } else if ("POST".equals(medicalDataDTO.getInterfaceType().toUpperCase())) {
                sendUrlPost(medicalDataDTO.getInterfaceUrl(), param);
            } else {
                throw new AppException("请求方式未匹配到");
            }
            sendUrlPost(Constants.LISURL.DOCURL, param);
        } catch (AppException e) {
            throw new AppException("lis同步医生失败:"+e.getMessage());
        }
//        List workTypeList = new ArrayList();
//        workTypeList.add("101");
//        workTypeList.add("102");
//        workTypeList.add("103");
//        workTypeList.add("104");
//        workTypeList.add("105");
////        map.put("statusCode", "0");
//        map.put("isInJob", "1");
//        map.put("workTypeList", workTypeList);
//        List<SysUserDTO> docList = lisDao.getDocList(map);
//        if(ListUtils.isEmpty(docList)){
//            throw new AppException("医生列表为空");
//        }
//        List<Map> docListParam = new ArrayList<>();
//        docList.forEach(dto -> {
//            Map map1 = new HashMap();
//            map1.put("areaCode", dto.getHospCode());
//            map1.put("name", dto.getName());
//            map1.put("code", dto.getId());
//            map1.put("source", "HIS");
//            map1.put("isUse", 1);
//            map1.put("dptCode", dto.getDeptCode());
//            map1.put("mobile", "");
//            docListParam.add(map1);
//        });
//        String param = toJSONString2(docListParam);
//        sendUrlPost(Constants.LISURL.DOCURL, param);
        return true;
    }

    /**
     * @Method getItemList
     * @Desrciption 项目接口
       @params [map]
       1、hospCode
     * @Author chenjun
     * @Date   2021-01-13 15:01
     * @Return java.lang.Boolean
    **/
    @Override
    public Boolean getItemList(Map map) {
        try {
            //获取配置信息
            MedicalDataDTO medicalDataDTO = getMedicData(map.get("hospCode").toString(), MedicTypeEnum.LIS_SFXM.getCode().toString());
            if (medicalDataDTO == null) {
                throw new AppException("获取lis收费项目同步配置信息为空");
            }

            //拼接sql
            StringBuffer sql = new StringBuffer();
            if (!StringUtils.isEmpty(medicalDataDTO.getTableSql())) {
                //配置了sql信息
                sql.append(medicalDataDTO.getTableSql());
            } else {
                //获取配置明细信息
                List<MedicalDataDetailDTO> detailDTOList = getMedicDataDetails(medicalDataDTO);

                sql.append("select ");
                for (int i=0;i<detailDTOList.size();i++) {
                    if (i == detailDTOList.size()-1) {
                        if (StringUtils.isEmpty(detailDTOList.get(i).getResultEName())) {
                            sql.append(detailDTOList.get(i).getResourceEName());
                        } else {
                            sql.append(detailDTOList.get(i).getResultEName()).append(" as ").append(detailDTOList.get(i).getResourceEName());
                        }
                    } else {
                        if (StringUtils.isEmpty(detailDTOList.get(i).getResultEName())) {
                            sql.append(detailDTOList.get(i).getResourceEName()).append(",");
                        } else {
                            sql.append(detailDTOList.get(i).getResultEName()).append(" as ").append(detailDTOList.get(i).getResourceEName()).append(",");
                        }
                    }
                }
                sql.append(" from ").append(medicalDataDTO.getResultName()).append(" where hosp_code='").append(medicalDataDTO.getHospCode())
                        .append("' and is_valid='1' ");
                String type = MapUtils.get(map, "type");
                if ("1".equals(type)) {
                    sql.append(" and type_code='3'");
                } else if ("2".equals(type)) {
                    sql.append(" and type_code='12'");
                }
            }
            List<Map> list = lisDao.getLisDataList(sql.toString());
            if (ListUtils.isEmpty(list)) {
                throw new AppException("未获取到收费项目数据");
            }
            String param = JSONObject.toJSONString(list);
            if ("GET".equals(medicalDataDTO.getInterfaceType().toUpperCase())) {
                sendUrlGet(medicalDataDTO.getInterfaceUrl(), param);
            } else if ("POST".equals(medicalDataDTO.getInterfaceType().toUpperCase())) {
                sendUrlPost(medicalDataDTO.getInterfaceUrl(), param);
            } else {
                throw new AppException("请求方式未匹配到");
            }
        } catch (AppException e) {
            throw new AppException("lis同步收费项目失败:"+e.getMessage());
        }
//        map.put("isValid", "1");
//        List<BaseAdviceDTO> adviceList = lisDao.getItemList(map);
//        if(ListUtils.isEmpty(adviceList)){
//            throw new AppException("项目列表为空");
//        }
//        List<Map> adviceListParam = new ArrayList<>();
//        adviceList.forEach(dto -> {
//            Map map1 = new HashMap();
//            map1.put("areaCode", dto.getHospCode());
//            map1.put("name", dto.getName());
//            map1.put("code", dto.getCode());
//            map1.put("source", "HIS");
//            map1.put("isUse", 1);
//            map1.put("money", dto.getPrice());
////            map1.put("useCount", 1);
//            adviceListParam.add(map1);
//        });
//        String param = toJSONString2(adviceListParam);
//        sendUrlPost(Constants.LISURL.ITEMURL, param);
        return true;
    }

    /**
     * @Method saveInspectApply
     * @Desrciption 提交检验申请单接口
       @params [map]
       1、hospCode
       2、type    1：处方 2：医嘱
       3、id  处方或者医嘱
     * @Author chenjun
     * @Date   2021-01-06 10:08
     * @Return java.lang.Boolean
    **/
    @Override
    public Boolean saveInspectApply(Map map) {
        try {
            String type = MapUtils.get(map, "type");
            //获取配置信息
            MedicalDataDTO medicalDataDTO = getMedicData(map.get("hospCode").toString(), type);
            if (medicalDataDTO == null) {
                throw new AppException("获取lis门诊申请单配置信息为空");
            }

            //拼接sql
            StringBuffer sql = new StringBuffer();
            if (!StringUtils.isEmpty(medicalDataDTO.getTableSql())) {
                //配置了sql信息
                sql.append(medicalDataDTO.getTableSql());
            }

            //凭借传过来的医技申请单Id作为查询条件
            String[] applyIds = MapUtils.get(map, "applyIds").toString().split(",");
            if (applyIds!=null && applyIds.length>0) {
                sql.append(" and a.id in (");
                for (int i=0;i<applyIds.length;i++) {
                    if (i != applyIds.length-1) {
                        sql.append("'").append(applyIds[i]).append("',");
                    } else {
                        sql.append("'").append(applyIds[i]).append("'");
                    }
                }
                sql.append(" )");
            }

            List<Map> list = lisDao.getLisDataList(sql.toString());
            if (ListUtils.isEmpty(list)) {
                throw new AppException("未获取到门诊申请单数据");
            }
            String param = JSONObject.toJSONString(list);
            if ("GET".equals(medicalDataDTO.getInterfaceType().toUpperCase())) {
                sendUrlGet(medicalDataDTO.getInterfaceUrl(), param);
            } else if ("POST".equals(medicalDataDTO.getInterfaceType().toUpperCase())) {
                sendUrlPost(medicalDataDTO.getInterfaceUrl(), param);
            } else {
                throw new AppException("请求方式未匹配到");
            }
        } catch (AppException e) {
            throw new AppException("lis门诊申请单申请失败:"+e.getMessage());
        }
//        String hospCode =  MapUtils.get(map, "hospCode");
//        // 1：处方 2：医嘱
//        String type = MapUtils.get(map, "type");
//        if("1".equals(type)) {
//            List<Map> prescribeLisList = lisDao.getPrescribeLis(map);
//            if(ListUtils.isEmpty(prescribeLisList)){
//                throw new AppException("没有找到申请的项目");
//            }
//            prescribeLisList.forEach(map1 -> {
//                map1.put("areaCode", hospCode);
//                map1.put("hisid", SnowflakeUtils.getId());
//            });
//            String param = toJSONString2(prescribeLisList);
//            sendUrlPost(Constants.LISURL.JYSQURL, param);
//        } else if("2".equals(type)){
//            List<Map> adviceLisList = lisDao.getAdviceLis(map);
//            if(ListUtils.isEmpty(adviceLisList)){
//                throw new AppException("没有找到申请的项目");
//            }
//            adviceLisList.forEach(map2 -> {
//                map2.put("hisid", SnowflakeUtils.getId());
//            });
//            String param = toJSONString2(adviceLisList);
//            sendUrlPost(Constants.LISURL.JYSQURL, param);
//        }
        return true;
    }

    private List<MedicalDataDetailDTO> getMedicDataDetails(MedicalDataDTO medicalDataDTO) {
        MedicalDataDetailDTO medicalDataDetailDTO = new MedicalDataDetailDTO();
        medicalDataDetailDTO.setHospCode(medicalDataDTO.getHospCode());
        medicalDataDetailDTO.setType(medicalDataDTO.getType());
        List<MedicalDataDetailDTO> detailDTOList = lisDao.getMedicalDataDetails(medicalDataDetailDTO);
        if (ListUtils.isEmpty(detailDTOList)) {
            throw new AppException("获取配置明细数据为空");
        }
        return detailDTOList;
    }

    /**
     * @Method saveInspectResult
     * @Desrciption 回传检验结果接口(第三方主动获取)
       @params [map]
       1、hospCode
       2、pid 就诊卡号、门诊号/住院号/体检号等
       3、reportDate 报告时间
     * @Author chenjun
     * @Date   2021-01-06 10:10
     * @Return java.lang.Boolean
    *12*/
    @Override
    public Boolean saveInspectResultActive(Map map) {
        try {
            String type = MapUtils.get(map, "type");
            //获取配置信息
            MedicalDataDTO medicalDataDTO = getMedicData(map.get("hospCode").toString(), type);
            if (medicalDataDTO == null) {
                throw new AppException("获取lis主动获取结果配置信息为空");
            }

            //获取配置明细信息
            List<MedicalDataDetailDTO> detailDTOList = getMedicDataDetails(medicalDataDTO);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("areaCode", MapUtils.get(map,"hospCode"));
            jsonObject.put("pid", MapUtils.get(map,"pid"));
//            jsonObject.put("reportDate", "2021-01-03 08:00:00");
            String param = jsonObject.toJSONString();
            if ("GET".equals(medicalDataDTO.getInterfaceType().toUpperCase())) {
                sendUrlGet(medicalDataDTO.getInterfaceUrl(), param);
            } else if ("POST".equals(medicalDataDTO.getInterfaceType().toUpperCase())) {
                String jsonStr = sendUrlPostRetState(medicalDataDTO.getInterfaceUrl(), param);

                List<MedicalResultDTO> resultDTOList = new ArrayList<>();
                JSONObject jsonObject1 = JSONObject.parseObject(jsonStr);
                JSONArray jsonArray = (JSONArray) jsonObject1.get("data");
                for(int i=0;i<jsonArray.size();i++) {
                    JSONObject jsonObject2 = JSONObject.parseObject(jsonArray.get(i).toString());
                    JSONArray jsonArray1 = (JSONArray) jsonObject2.get("resultlist");
                    for(int j=0;j<jsonArray1.size();j++) {
                        JSONObject jsonObject3 = JSONObject.parseObject(jsonArray1.get(j).toString());

                        JSONObject detailMap = new JSONObject();
                        for (MedicalDataDetailDTO dataDetailDTO:detailDTOList) {
                            if (jsonObject2.get(dataDetailDTO.getResourceEName()) != null) {
                                detailMap.put(dataDetailDTO.getResultEName(), jsonObject2.get(dataDetailDTO.getResourceEName()).toString());
                            }
                            if (jsonObject3.get(dataDetailDTO.getResourceEName()) != null) {
                                detailMap.put(dataDetailDTO.getResultEName(), jsonObject3.get(dataDetailDTO.getResourceEName()).toString());
                            }
                        }

                        if (detailMap!=null && detailMap.size()>0) {
                            MedicalResultDTO resultDTO = JSONObject.parseObject(detailMap.toJSONString(), MedicalResultDTO.class);
                            if (StringUtils.isNotEmpty(resultDTO.getResultId())) {
                                lisDao.deleteResultByResultId(resultDTO.getHospCode(),resultDTO.getResultId());
                            }
                            resultDTOList.add(resultDTO);
                        }
                    }
                }

                //新增结果
                if (!ListUtils.isEmpty(resultDTOList)) {
                    for (MedicalResultDTO medicalResultDTO:resultDTOList) {
                        medicalResultDTO.setId(SnowflakeUtils.getId());
                        medicalResultDTO.setTypeCode("1");
                        medicalResultDTO.setCrteId(MapUtils.get(map,"userId"));
                        medicalResultDTO.setCrteName(MapUtils.get(map,"userName"));
                        medicalResultDTO.setCrteTime(DateUtils.getNow());
                    }
                    lisDao.insertResults(resultDTOList);
                }
            } else {
                throw new AppException("请求方式未匹配到");
            }
        } catch (AppException e) {
            throw new AppException("lis主动获取结果失败:"+e.getMessage());
        }

//        Map mapParam = new HashMap();
//        mapParam.put("areacode", MapUtils.get(map, "hospCode"));
//        mapParam.put("pid", MapUtils.get(map, "pid"));
//        mapParam.put("reportDate", MapUtils.get(map, "reportDate"));
//        String param = JSON.toJSONString(mapParam);
//        String jsonStr = sendUrlPostRetState(Constants.LISURL.JGHCDSFURL, param);
//
//        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
//        JSONArray jsonArray = (JSONArray) jsonObject.get("data");
//        String jsonArrayStr = JSONArray.toJSONString(jsonArray);
//        List<Map> list = parseArray(jsonArrayStr, Map.class);
//        lisDao.insertLisInspectResult(list);
        return null;
    }

    /**
     * @Method saveInspectResult
     * @Desrciption 回传检验结果接口(LIS推送)
       @params [map]
     * @Author chenjun
     * @Date   2021-01-13 08:44
     * @Return java.lang.Boolean
    **/
    @Override
    public Boolean saveInspectResult(Map map) {
        try {
            String type = MapUtils.get(map, "type");
            //获取配置信息
            MedicalDataDTO medicalDataDTO = getMedicData(map.get("hospCode").toString(), type);
            if (medicalDataDTO == null) {
                throw new AppException("获取lis主动获取结果配置信息为空");
            }

            //获取配置明细信息
            List<MedicalDataDetailDTO> detailDTOList = getMedicDataDetails(medicalDataDTO);

            String jsonStr = MapUtils.get(map,"jsonStr");
            List<MedicalResultDTO> resultDTOList = new ArrayList<>();
            JSONObject jsonObject1 = JSONObject.parseObject(jsonStr);
            JSONArray jsonArray = (JSONArray) jsonObject1.get("data");
            for(int i=0;i<jsonArray.size();i++) {
                JSONObject jsonObject2 = JSONObject.parseObject(jsonArray.get(i).toString());
                JSONArray jsonArray1 = (JSONArray) jsonObject2.get("resultlist");
                for(int j=0;j<jsonArray1.size();j++) {
                    JSONObject jsonObject3 = JSONObject.parseObject(jsonArray1.get(j).toString());

                    JSONObject detailMap = new JSONObject();
                    for (MedicalDataDetailDTO dataDetailDTO:detailDTOList) {
                        if (jsonObject2.get(dataDetailDTO.getResourceEName()) != null) {
                            detailMap.put(dataDetailDTO.getResultEName(), jsonObject2.get(dataDetailDTO.getResourceEName()).toString());
                        }
                        if (jsonObject3.get(dataDetailDTO.getResourceEName()) != null) {
                            detailMap.put(dataDetailDTO.getResultEName(), jsonObject3.get(dataDetailDTO.getResourceEName()).toString());
                        }
                    }

                    if (detailMap!=null && detailMap.size()>0) {
                        resultDTOList.add(JSONObject.parseObject(detailMap.toJSONString(), MedicalResultDTO.class));
                    }
                }
            }

            //新增结果
            if (!ListUtils.isEmpty(resultDTOList)) {
                for (MedicalResultDTO medicalResultDTO:resultDTOList) {
                    medicalResultDTO.setId(SnowflakeUtils.getId());
                    medicalResultDTO.setTypeCode("1");
                    medicalResultDTO.setCrteId(MapUtils.get(map,"userId"));
                    medicalResultDTO.setCrteName(MapUtils.get(map,"userName"));
                    medicalResultDTO.setCrteTime(DateUtils.getNow());
                }
                lisDao.insertResults(resultDTOList);
            }
        } catch (AppException e) {
            throw new AppException("lis主动获取结果失败:"+e.getMessage());
        }
//        String param = MapUtils.get(map, "param");
//        List<Map> list = parseArray(param, Map.class);
//        list.forEach(map1 -> {
//            String a = map1.get("testDate").toString();
//            DateUtils.parse(a, DateUtils.Y_M_DH_M_S);
//            Date testDate = DateUtils.parse((String) map1.get("testDate"), DateUtils.Y_M_DH_M_S);
//            Date gatherTime = DateUtils.parse((String) map1.get("gatherTime"), DateUtils.Y_M_DH_M_S);
//            Date receiveTime = DateUtils.parse((String) map1.get("receiveTime"), DateUtils.Y_M_DH_M_S);
//            Date orderTime = DateUtils.parse((String) map1.get("orderTime"), DateUtils.Y_M_DH_M_S);
//            Date checkTime = DateUtils.parse((String) map1.get("testTime"), DateUtils.Y_M_DH_M_S);
//            Date testTime = DateUtils.parse((String) map1.get("checkTime"), DateUtils.Y_M_DH_M_S);
//
//            map1.put("testDate", testDate);
//            map1.put("gatherTime", gatherTime);
//            map1.put("receiveTime", receiveTime);
//            map1.put("orderTime", orderTime);
//            map1.put("testTime", testTime);
//            map1.put("checkTime", checkTime);
//
//            List<Map> resultList = (List) map1.get("resultlist");
//            String resultStr = toJSONString2(resultList);
//            map1.put("resultlist", resultStr);
//
//        });
//        lisDao.insertLisInspectResult(list);
        return true;
    }

    /**
     * @Method getPDFReport
     * @Desrciption 查询PDF接口
       @params [map]
     * @Author chenjun
     * @Date   2021-01-13 08:44
     * @Return java.lang.Boolean
    **/
    @Override
    public Boolean getPDFReport(Map map) {
        try {
            //获取配置信息
            MedicalDataDTO medicalDataDTO = getMedicData(map.get("hospCode").toString(), Constants.MEDICTYPE.LIS_YSTB);
            if (medicalDataDTO == null) {
                throw new AppException("获取lis查询PDF报告配置信息为空");
            }

            //拼接sql
            StringBuffer sql = new StringBuffer();
            if (!StringUtils.isEmpty(medicalDataDTO.getTableSql())) {
                //配置了sql信息
                sql.append(medicalDataDTO.getTableSql());
            } else {
                sql.append("select ");
                MedicalDataDetailDTO medicalDataDetailDTO = new MedicalDataDetailDTO();
                medicalDataDetailDTO.setHospCode(medicalDataDTO.getHospCode());
                medicalDataDetailDTO.setType(medicalDataDTO.getType());
                List<MedicalDataDetailDTO> detailDTOList = lisDao.getMedicalDataDetails(medicalDataDetailDTO);
                if (ListUtils.isEmpty(detailDTOList)) {
                    throw new AppException("获取配置明细数据为空");
                }
                for (int i=0;i<detailDTOList.size();i++) {
                    if (i == detailDTOList.size()-1) {
                        sql.append(detailDTOList.get(i).getResultEName()).append(" as ").append(detailDTOList.get(i).getResourceEName());
                    } else {
                        sql.append(detailDTOList.get(i).getResultEName()).append(" as ").append(detailDTOList.get(i).getResourceEName()).append(",");
                    }
                }
                sql.append(" from ").append(medicalDataDTO.getResultName()).append(" where hosp_code='").append(medicalDataDTO.getHospCode());
            }
            List<Map> deptList = lisDao.getLisDataList(sql.toString());
            if (ListUtils.isEmpty(deptList)) {
                throw new AppException("未获取到查询PDF报告数据");
            }
            String param = toJSONString2(deptList);
            sendUrlPost(Constants.LISURL.PDFURL, param);
        } catch (AppException e) {
            throw new AppException("lis查询PDF报告失败:"+e.getMessage());
        }

//        Map<String, Object> mapParam = new HashMap();
//        mapParam.put("areacode", MapUtils.get(map, "hospCode"));
//        mapParam.put("patinfoid", MapUtils.get(map, "patinfoid"));
//        String getParam = "areacode=" + MapUtils.get(map, "hospCode") + "&" + "patinfoid=" + MapUtils.get(map, "patinfoid");
//        String jsonStr = sendUrlGet(Constants.LISURL.PDFURL, getParam);
//        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
//        JSONObject jsonObjectData = (JSONObject) jsonObject.get("data");
//        LisPdfDTO lisPdfDTO = JSONObject.toJavaObject(jsonObjectData, LisPdfDTO.class);
//        lisDao.insertLisPDF(lisPdfDTO);
        return true;
    }

    /**
     * @Method callbackStatus
     * @Desrciption 回传状态
       @params [map]
     * @Author chenjun
     * @Date   2021-01-13 08:45
     * @Return java.lang.Boolean
    **/
    @Override
    public Boolean callbackStatus(Map map) {
        String param = MapUtils.get(map, "param");
        List<Map> list = parseArray(param, Map.class);
        lisDao.insertLisCallbackStatus(list);
        return true;
    }

    /**
     * @Method callbackCriticalValue
     * @Desrciption 回传危机值结果信息（Lis主动推送）
       @params [map]
     * @Author chenjun
     * @Date   2021-01-13 09:16
     * @Return java.lang.Boolean
    **/
    @Override
    public Boolean callbackCriticalValue(Map map) {
        String param = MapUtils.get(map, "param");
        List<Map> list = parseArray(param, Map.class);
        list.forEach(map1 -> {
            map1.put("crteId", "");
            map1.put("crteName", "");
            map1.put("crteTime", new Date());
        });
        lisDao.insertLisCallbackCriticalValue(list);
        return true;
    }

    /**
     * @Method criticalValue
     * @Desrciption 提交危急值处理信息接口地址
       @params [map]
       入参示例
          {
               "areaCode":-1,
               "patInfoId":982564,
               "testItemCode":230,
               "opinionType":1,
               "opinion":"处理意见",
               "recordUserCode":1,
               "recordUserName":"张三",
               "criticalType":"▲"
           }
     * @Author chenjun
     * @Date   2021-01-13 09:16
     * @Return java.lang.Boolean
    **/
    @Override
    public Boolean criticalValue(Map map) {
        String hospCode =  MapUtils.get(map, "hospCode");
        String param =  MapUtils.get(map, "param");
        sendUrlPost(Constants.LISURL.WJZDSFURL, param);
        return true;
    }


    private String sendUrlPost(String url, String param) {
        String retStr = HttpConnectUtil.postMethod(url, param);
        if (StringUtils.isEmpty(retStr)) {
            throw new AppException("调用同步接口返回空");
        }
        JSONObject jsonObject = JSONObject.parseObject(retStr);
        if (!jsonObject.get("code").toString().equals("1")) {
            throw new AppException("接收失败," + jsonObject.get("msg").toString());
        }
        logger.info("接收成功," +  jsonObject.get("msg").toString());
        return retStr;
    }

    private String sendUrlPostRetState(String url, String param) {
        String retStr = HttpConnectUtil.postMethod(url, param);
        if (StringUtils.isEmpty(retStr)) {
            throw new AppException("调用同步接口返回空");
        }
        JSONObject jsonObject = JSONObject.parseObject(retStr);
        if (!jsonObject.get("state").toString().equals("1")) {
            throw new AppException("接收失败," + jsonObject.get("msg").toString());
        }
        logger.info("接收成功," +  jsonObject.get("msg").toString());
        return retStr;
    }

    private String sendUrlGet(String url, String param) {
        String retStr = HttpConnectUtil.getMethod(url, param);
        if (StringUtils.isEmpty(retStr)) {
            throw new AppException("调用同步接口返回空");
        }
        JSONObject jsonObject = JSONObject.parseObject(retStr);
        if (jsonObject.get("state").toString().equals("0")) {
            throw new AppException("接收失败," + jsonObject.get("msg").toString());
        }
        logger.info("接收成功," +  jsonObject.get("msg").toString());
        return retStr;
    }


    private static <T> List<T> parseArray(String text, Class<T> clazz) {
        try {
            return JSON.parseArray(text, clazz);
        } catch (Exception e) {
        }
        return Collections.emptyList();
    }


    private static String toJSONString2(Object obj) {
        JSONArray jsonObj = (JSONArray) JSONArray.toJSON(obj);
        return jsonObj.toJSONString();
    }

    /**
     * @Method: getMedicaData
     * @Description: 获取配置信息
     * @Param: [hospCode, type]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/29 17:09
     * @Return: void
     **/
    private MedicalDataDTO getMedicData(String hospCode,String type) {
        MedicalDataDTO medicalDataDTO = new MedicalDataDTO();
        medicalDataDTO.setHospCode(hospCode);
        medicalDataDTO.setType(type);
        List<MedicalDataDTO> list = lisDao.getMedicalDataByType(medicalDataDTO);
        if (ListUtils.isEmpty(list)) {
            throw new AppException("未获取到lis科室同步配置信息");
        }
        if (!ListUtils.isEmpty(list) && list.size()>1) {
            throw new AppException("lis科室同步配置启用了多条(只能启用一条)");
        }
        return list.get(0);
    }





    /**
     * @Method: getMedicalDatas
     * @Description: 获取配置集合
     * @Param: [medicalDataDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:49
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public PageDTO getMedicalDatas(MedicalDataDTO medicalDataDTO) {
        PageHelper.startPage(medicalDataDTO.getPageNo(),medicalDataDTO.getPageSize());
        List<MedicalDataDTO> list = lisDao.getMedicalDatas(medicalDataDTO);
        list.stream().forEach(medicalDataDTO1 -> {
            for (MedicTypeEnum typeEnum:MedicTypeEnum.values()) {
                if (typeEnum.getCode().toString().equals(medicalDataDTO1.getType())) {
                    medicalDataDTO1.setTypeStr(typeEnum.getName());
                }
            }
            if ("1".equals(medicalDataDTO1.getSourceType())) {
                medicalDataDTO1.setSourceTypeStr("表/视图");
            } else if ("2".equals(medicalDataDTO1.getSourceType())) {
                medicalDataDTO1.setSourceTypeStr("接口");
            }
        });
        return PageDTO.of(list);
    }

    /**
     * @Method: getMedicalDataDetails
     * @Description: 获取配置明细集合
     * @Param: [medicalDataDetailDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 15:58
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public PageDTO getMedicalDataDetails(MedicalDataDetailDTO medicalDataDetailDTO) {
        PageHelper.startPage(medicalDataDetailDTO.getPageNo(),100000);
        return PageDTO.of(lisDao.getMedicalDataDetails(medicalDataDetailDTO));
    }

    /**
     * @Method: getMedicalData
     * @Description: 获取配置对象
     * @Param: [medicalDataDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 16:11
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.medic.data.dto.MedicalDataDTO>
     **/
    @Override
    public MedicalDataDTO getMedicalData(MedicalDataDTO medicalDataDTO) {
        return lisDao.getMedicalData(medicalDataDTO);
    }

    /**
     * @Method: getMedicalData
     * @Description: 获取配置明细对象
     * @Param: [medicalDataDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 16:11
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.medic.data.dto.MedicalDataDTO>
     **/
    @Override
    public MedicalDataDetailDTO getMedicalDataDetail(MedicalDataDetailDTO medicalDataDetailDTO) {
        return lisDao.getMedicalDataDetail(medicalDataDetailDTO);
    }

    /**
     * @Method: insertMedicalData
     * @Description: 新增配置
     * @Param: [medicalDataDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 16:44
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.medic.data.dto.MedicalDataDetailDTO>
     **/
    @Override
    public Boolean insertMedicalData(MedicalDataDTO medicalDataDTO) {
        return lisDao.insertMedicalData(medicalDataDTO)>0;
    }

    /**
     * @Method: insertMedicalDataDetails
     * @Description: 批量新增配置明细
     * @Param: [medicalDataDetailDTOList]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 16:47
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public Boolean insertMedicalDataDetails(MedicalDataDTO medicalDataDTO) {
        //先删除
        lisDao.deleteMedicalDataDetails(medicalDataDTO);
        //再新增
        return lisDao.insertMedicalDataDetails(medicalDataDTO.getMedicalDataDetailDTOList())>0;
    }

    /**
     * @Method: updateMedicalData
     * @Description: 更新配置
     * @Param: [medicalDataDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 16:49
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public Boolean updateMedicalData(MedicalDataDTO medicalDataDTO) {
        return lisDao.updateMedicalData(medicalDataDTO)>0;
    }

    /**
     * @Method: deleteMedicalData
     * @Description: 删除配置
     * @Param: [medicalDataDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/1/28 16:54
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public Boolean deleteMedicalData(List<MedicalDataDTO> medicalDataDTOList) {
        medicalDataDTOList.stream().forEach(medicalDataDTO -> {
            String code = medicalDataDTO.getStatusCode();
            if (Constants.SF.F.equals(code)) {
                medicalDataDTO.setStatusCode(Constants.SF.S);
            } else if (Constants.SF.S.equals(code)) {
                medicalDataDTO.setStatusCode(Constants.SF.F);
            } else {
                throw new AppException("状态错误");
            }
            medicalDataDTO.setOldStatusCode(code);
        });
        lisDao.deleteMedicalData(medicalDataDTOList);
        return true;
    }

    /**
     * @Method: getTyeps
     * @Description: 获取类型
     * @Param: []
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2021/2/2 16:46
     * @Return: java.util.List<java.util.Map>
     **/
    @Override
    public List<Map> getTyeps() {
        List<Map> list = new ArrayList<>();
        for (MedicTypeEnum e : MedicTypeEnum.values()) {
            Map map = new HashMap();
            map.put("value", e.getCode().toString());
            map.put("label", e.getName());
            list.add(map);
        }
        return list;
    }
}