package cn.hsa.outpt.lis.bo.impl;

import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.base.ba.dto.BaseAdviceDTO;
import cn.hsa.module.base.dept.dto.BaseDeptDTO;
import cn.hsa.module.outpt.lis.bo.PacsBO;
import cn.hsa.module.outpt.lis.dao.PacsDao;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Package_name: cn.hsa.outpt.lis.bo.impl
 * @Class_name: PacsBOImpl
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2021-01-11 15:12
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
@Slf4j
public class PacsBOImpl implements PacsBO {

    @Resource
    PacsDao pacsDao;

    /**
     * @Method getPacsDeptList
     * @Desrciption 上传科室信息
       @params [map]
     * @Author chenjun
     * @Date   2021-01-13 09:14
     * @Return java.lang.Boolean
    **/
    @Override
    public Boolean getPacsDeptList(Map map) {
        String hospCode = (String) map.get("hospCode");
        List<BaseDeptDTO> list = pacsDao.getPacsDeptList(map);
        if(ListUtils.isEmpty(list)){
            throw new AppException("科室信息为空");
        }
        List<Map> deptList = new ArrayList<>();
        list.forEach(dto -> {
            Map map1 = new HashMap();
            map1.put("deptName", dto.getName());
            map1.put("isSupervise", dto.getTypeCode().equalsIgnoreCase("12") ? 1 : 0);
            map1.put("isClinical", Arrays.asList ("2", "7", "8", "9", "10") .contains(dto.getTypeCode()) ? 1 : 0);
            map1.put("isFinance", dto.getTypeCode().equalsIgnoreCase("12") ? 1 : 0);
            map1.put("isOperation", dto.getTypeCode().equalsIgnoreCase("9") ? 1 : 0);
            map1.put("deptId", dto.getId());
            map1.put("isChineseRoom", dto.getTypeCode().equalsIgnoreCase("4") ? 1 : 0);
            map1.put("isOutpharmacy", dto.getTypeCode().equalsIgnoreCase("4") ? 1 : 0);
            map1.put("deptStatus", 1);
            map1.put("isStorage", dto.getTypeCode().equalsIgnoreCase("3") ? 1 : 0);
            map1.put("isRadiology", dto.getTypeCode().equalsIgnoreCase("8") ? 1 : 0);
            map1.put("isCharge", dto.getTypeCode().equalsIgnoreCase("12") ? 1 : 0);
            map1.put("isOutpatientInfusion", dto.getTypeCode().equalsIgnoreCase("10") ? 1 : 0);
            map1.put("isInpharmacy", dto.getTypeCode().equalsIgnoreCase("5") ? 1 : 0);
            map1.put("posId", hospCode);
            deptList.add(map1);
        });
        String param = toJSONString2(deptList);
        sendUrlPost(Constants.PACSURL.DEPTURL, param);
        return true;
    }

    /**
     * @Method getPacsDocList
     * @Desrciption 上传人员信息
       @params [map]
     * @Author chenjun
     * @Date   2021-01-13 09:14
     * @Return java.lang.Boolean
    **/
    @Override
    public Boolean getPacsDocList(Map map) {
        String hospCode = (String) map.get("hospCode");
        List<SysUserDTO> list = pacsDao.getPacsDocList(map);
        if(ListUtils.isEmpty(list)){
            throw new AppException("查询人员为空");
        }

        List<Map> docListNew = new ArrayList<>();
        list.forEach(dto -> {
            Map map1 = new HashMap();
            map1.put("empId", dto.getId());
            map1.put("empName", dto.getName());
            map1.put("empGender", dto.getSex());
            if(Arrays.asList ("101", "102", "103", "104", "105") .contains(dto.getWorkTypeCode())){
                map1.put("empProperty", "2");
            }else if(Arrays.asList ("201", "202", "203", "204", "205") .contains(dto.getWorkTypeCode())){
                map1.put("empProperty", "6");
            }else if(Arrays.asList ("301", "302", "303", "304", "305") .contains(dto.getWorkTypeCode())){
                map1.put("empProperty", "4");
            }else if(Arrays.asList ("401", "402") .contains(dto.getWorkTypeCode())){
                map1.put("empProperty", "5");
            }else if(dto.getWorkTypeCode().equals("0")){
                map1.put("empProperty", "7");
            }else if(dto.getWorkTypeCode().equals("1")){
                map1.put("empProperty", "3");
            }
            map1.put("empRin", dto.getCertNo());
            map1.put("posId", hospCode);
            map1.put("deptId", dto.getDeptId());
            docListNew.add(map1);
        });
        String param = toJSONString2(docListNew);
        sendUrlPost(Constants.PACSURL.DOCURL, param);
        return true;
    }

    /**
     * @Method getPacsItemDocList
     * @Desrciption 上传项目信息
       @params [map]
     * @Author chenjun
     * @Date   2021-01-13 09:15
     * @Return java.lang.Boolean
    **/
    @Override
    public Boolean getPacsItemDocList(Map map) {
        String url = (String) map.get("url");
        String hospCode = (String) map.get("hospCode");
        List<BaseAdviceDTO> adviceList = pacsDao.getPacsItemList(map);
        if(ListUtils.isEmpty(adviceList)){
            throw new AppException("项目列表为空");
        }
        List<Map> adviceListParam = new ArrayList<>();
        adviceList.forEach(dto -> {
            Map map1 = new HashMap();
            map1.put("itemId", dto.getId());
            map1.put("itemName", dto.getName());
            map1.put("itemPrice", dto.getPrice());
            map1.put("posId", hospCode);
            adviceListParam.add(map1);
        });
        String param = toJSONString2(adviceListParam);
        sendUrlPost(Constants.PACSURL.ITEMURL, param);
        return true;
    }

    /**
     * @Method savePacsInspectApply
     * @Desrciption 检查申请信息接口
       @params [map]
     * @Author chenjun
     * @Date   2021-01-13 09:15
     * @Return java.lang.Boolean
    **/
    @Override
    public Boolean savePacsInspectApply(Map map) {
        String hospCode =  MapUtils.get(map, "hospCode");
        // 1：处方 2：医嘱
        String type = MapUtils.get(map, "type");
        if("1".equals(type)){
            List<Map> prescribeLisList = pacsDao.getPacsPrescribe(map);
            if(ListUtils.isEmpty(prescribeLisList)){
                throw new AppException("没有找到申请的项目");
            }
            String param = toJSONString2(prescribeLisList);
            sendUrlPost(Constants.PACSURL.SQDURL, param);
        }else if("2".equals(type)){
            List<Map> adviceLisList = pacsDao.getPacsAdvice(map);
            if(ListUtils.isEmpty(adviceLisList)){
                throw new AppException("没有找到申请的项目");
            }
            String param = toJSONString2(adviceLisList);
            sendUrlPost(Constants.PACSURL.SQDURL, param);
        }
        return true;
    }


    /**
     * @Method SavePacsInspectCallback
     * @Desrciption 结果回传
       @params [map]
     * @Author chenjun
     * @Date   2021-01-13 09:15
     * @Return java.lang.Integer
    **/
    @Override
    public Integer SavePacsInspectCallback(Map map) {
        String param = MapUtils.get(map, "param");
        Map mapParam = (Map)JSON.parse(param);
        return pacsDao.insertPacsInspectResult(mapParam);
    }


    private String sendUrlPost(String url, String param) {
        String retStr = HttpConnectUtil.postMethod(url, param);
        JSONObject jsonObject = JSONObject.parseObject(retStr);
        if (!jsonObject.get("code").toString().equals("1")) {
            throw new AppException("接收失败," + jsonObject.get("msg").toString());
        }
        return retStr;
    }

    private String sendUrlGet(String url, String param) {
        String retStr = HttpConnectUtil.getMethod(url, param);
        JSONObject jsonObject = JSONObject.parseObject(retStr);
        if (jsonObject.get("code").toString().equals("0")) {
            throw new AppException("接收失败," + jsonObject.get("msg").toString());
        }
//        logger.info("接收成功," +  jsonObject.get("msg").toString());
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
}