package cn.hsa.interf.lis.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.interf.phys.bo.LisResultBO;
import cn.hsa.module.interf.phys.dao.LisResultDAO;
import cn.hsa.module.medic.apply.dto.MedicalApplyDTO;
import cn.hsa.module.sys.parameter.dto.SysParameterDTO;
import cn.hsa.module.sys.parameter.service.SysParameterService;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LisResultBOImpl extends HsafBO implements LisResultBO {

    @Resource
    private LisResultDAO lisResultDAO;

    /**
     * 系统参数
     */
    @Resource
    private SysParameterService sysParameterService_consumer;
    /**

    /**
     * @Description: lis数据上传
     * @Param: [map]
     * @return: java.lang.Boolean
     * @Author: zhangxuan
     * @Date: 2021-07-05
     */
    @Override
    public Boolean saveLisResult(Map map) {
        List<MedicalApplyDTO> medicalApplyDTOList = MapUtils.get(map, "medicalApplyDTO");
        String hospCode = MapUtils.get(map,"hospCode");
        String ids = "";
        Map<String,Object> str = new HashMap<String,Object>();
        // 获取lis结果数据上传的地址
        map = new HashMap();
        map.put("hospCode", hospCode);
        map.put("code", "YJ_YHFSID");
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(map).getData();
        String url = sysParameterDTO.getValue();
        //            String url = "http://localhost:8080/upResult";
        if(medicalApplyDTOList.size() > 0){
            // 拼接ids（用于获取lis库里面的结果集）
            for (MedicalApplyDTO medicalApplyDTO : medicalApplyDTOList){
                if(StringUtils.isEmpty(ids)){
                    ids = medicalApplyDTO.getVisitId();
                } else {
                    ids = ids + "," + medicalApplyDTO.getVisitId();
                }
            }
            str.put("ids",ids);
            String str1 = JSONObject.toJSONString(str);
            Map<String,Object> result = HttpConnectUtil.sendPost(url,str1);
            List<MedicalApplyDTO> resultDetailDTOList = MapUtils.get(result,"result");
            System.out.println(resultDetailDTOList);
        } else {
            //自动获取登记几天内的lis数据的结果
            map = new HashMap();
            map.put("hospCode", hospCode);
            map.put("code", "YJ_YHFSID");
            SysParameterDTO sysParameterDTO1 = sysParameterService_consumer.getParameterByCode(map).getData();
            String days = sysParameterDTO1.getValue();
            List<MedicalApplyDTO> medicalApplyDTOS = lisResultDAO.queryNoResult(days);
            // 拼接ids（用于获取lis库里面的结果集）
            for (MedicalApplyDTO medicalApplyDTO : medicalApplyDTOS){
                if(StringUtils.isEmpty(ids)){
                    ids = medicalApplyDTO.getVisitId();
                } else {
                    ids = ids + "," + medicalApplyDTO.getVisitId();
                }
            }
            str.put("ids",ids);
            String str1 = JSONObject.toJSONString(str);
            Map<String,Object> result = HttpConnectUtil.sendPost(url,str1);
            List<MedicalApplyDTO> resultDetailDTOList = MapUtils.get(result,"result");
            System.out.println(resultDetailDTOList);
        }
        return lisResultDAO.saveLisResult(medicalApplyDTOList);
    }

    /**
     * @Description: 保存lis结果（lis系统对接，线上通用版）
     * @Param: [map]
     * @return: java.lang.Boolean
     * @Author: zhangxuan
     * @Date: 2021-07-09
     */
    @Override
    public Map insertLisResult(Map map) {
        List<Map> medicalResultDTOList = MapUtils.get(map, "lisResult");
        for(Map resultMap : medicalResultDTOList){
            resultMap.put("crteTime", DateUtils.getNow());
            resultMap.put("crteId", "lis");
            resultMap.put("crteName", "lis");
        }
        // 根据medic_result 的 id 删除已存在的lis结果
        lisResultDAO.deleteResult(medicalResultDTOList);
        // 新增结果
        lisResultDAO.insertResult(medicalResultDTOList);
        // 根据medic_apply 的 apply_no 更新申请单状态为 已完成状态
        List<String> applyNoList = medicalResultDTOList.stream().map(m -> MapUtils.get(m,"applyNo").toString()).distinct().collect(Collectors.toList());
        lisResultDAO.updateApplyStatusForLine(applyNoList);

        return map;
    }

    /**
     * @Description: lis结果数据存库(德星落地版)
     * @Param: [map]
     * @return: java.lang.Boolean
     * @Author: zhangxuan
     * @Date: 2021-09-09
     */
    @Override
    public Map insertDXLisResult(Map map) {
        List<Map> medicalResultDTOList = MapUtils.get(map, "lisResult");
        if(medicalResultDTOList.size() > 0){
            List<String> stringList = new ArrayList<>();
            for(Map resultMap : medicalResultDTOList){
                stringList.add(MapUtils.get(resultMap,"adviceId"));
                resultMap.put("id", SnowflakeUtils.getId());
            }
            List<String> collect = stringList.stream().distinct().collect(Collectors.toList());

            lisResultDAO.deleteResultDX(collect); // 删除已经有结果的结果
            // 新增结果
            lisResultDAO.insertDXResult(medicalResultDTOList);
            // 更新申请单状态
            lisResultDAO.updateApplyStatus(collect);
        }
        return map;
    }

    /**
    * @Description: 查询没有结果的申请单的医嘱id
    * @Param:
    * @return:
    * @Author: zhangxuan
    * @Date: 2021-09-11
    */
    @Override
    public List<String> queryDXNoResult(Map map){
        List<String> list = lisResultDAO.queryDXNoResult(map);
        return list;
    }

    /**
     * @Description: 查询没有结果的申请单的医嘱id
     * @Param:
     * @return:
     * @Author: zhangxuan
     * @Date: 2021-09-11
     */
    @Override
    public List<String> queryDXBackResult(Map map){
        List<String> list = lisResultDAO.queryDXBackResult(map);
        return list;
    }

    /** 
    * @Description: 获取没有结果的申请单的医嘱id
    * @Param: 
    * @return: 
    * @Author: zhangxuan
    * @Date: 2021-09-04
    */ 
    @Override
    public Map updateNoResultLis(Map map){
        Map codeMap = new HashMap();
        codeMap.put("hospCode", MapUtils.get(map,"hospCode"));
        codeMap.put("code", "LIS_TXM");
        // 查询 LIS_TXM 系统参数值，1：his系统打印条码  2：lis系统打印条码
        SysParameterDTO sysParameterDTO = sysParameterService_consumer.getParameterByCode(codeMap).getData();
        String a = sysParameterDTO.getValue();
        List<Map> mapList = new ArrayList<>();
        List<String> backList = new ArrayList<>();
        Map newMap = new HashMap();
        if(a.equals("1")){
            // 查询项目费用状态为已结算、已打印条码、未打印报告的申请单数据
            mapList = lisResultDAO.queryNoResultLis(map);
            if(mapList.size() > 0){
                lisResultDAO.updateStatusMap(mapList);
            }
        } else {
            // 查询项目费用状态为已结算和未打印报告的申请单数据
            mapList = lisResultDAO.queryNoResultLisS(map);
            if(mapList.size() > 0){
                lisResultDAO.updateStatusMap(mapList);
            }
        }
        // 查询当天已退费的申请单数据
        backList = lisResultDAO.queryDXBackResult(map);
        newMap.put("result",mapList);
        newMap.put("backList",backList);
        return newMap;

    }

    /**
     * @Description: 科室信息
     * @Param: [map]
     * @return: java.util.Map
     * @Author: zhangxuan
     * @Date: 2021-07-19
     */
    @Override
    public Map queryAdvice(Map map){
        List<Map> mapList = lisResultDAO.queryAdvice(map);
        Map newMap = new HashMap();
        newMap.put("result",mapList);
        return newMap;

    }

    /**
     * @Description: 医嘱目录信息
     * @Param: [map]
     * @return: java.util.Map
     * @Author: zhangxuan
     * @Date: 2021-07-19
     */
    @Override
    public Map queryDept(Map map){
        List<Map> mapList = lisResultDAO.queryDept(map);
        Map newMap = new HashMap();
        newMap.put("result",mapList);
        return newMap;

    }

    /**
     * @Description: 用户信息
     * @Param: [map]
     * @return: java.util.Map
     * @Author: zhangxuan
     * @Date: 2021-07-19
     */
    @Override
    public Map queryUser(Map map){
        List<Map> mapList = lisResultDAO.queryUser(map);

        Map newMap = new HashMap();
        newMap.put("result",mapList);
        return newMap;

    }
}
