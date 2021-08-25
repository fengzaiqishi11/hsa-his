package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.medic.data.dto.MedicalDataDTO;
import cn.hsa.module.medic.data.dto.MedicalDataDetailDTO;
import cn.hsa.module.outpt.lis.service.LisService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.*;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt
 * @Class_name: LisController
 * @Describe:
 * @Author: chenjun
 * @Eamil: chenjun@powersi.com.cn
 * @Date: 2021-01-04 09:58
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/web/outpt/lis")
@Slf4j
public class LisController extends BaseController {

    @Resource
    LisService lisService;

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
    @PostMapping("/getDeptList")
    public WrapperResponse<Boolean> getDeptList(HttpServletRequest req, HttpServletResponse res) {
        Map map = new HashMap();
        SysUserDTO userDTO = getSession(req, res) ;
        map.put("hospCode", userDTO.getHospCode());
        return lisService.getDeptList(map);
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
    @PostMapping("/getDocList")
    public WrapperResponse<Boolean> getDocList(HttpServletRequest req, HttpServletResponse res) {
        Map map = new HashMap();
        SysUserDTO userDTO = getSession(req, res) ;
        map.put("hospCode", userDTO.getHospCode());
        return lisService.getDocList(map);
    }

    /**
     * @Method getItemList
     * @Desrciption 项目接口
     @params [map]
     1、hospCode
     2.type(1:lis,2:pacs,空:所有)
      * @Author chenjun
     * @Date   2021-01-13 15:01
     * @Return java.lang.Boolean
     **/
    @PostMapping("/getItemList")
    public WrapperResponse<Boolean> getItemList(@RequestBody Map map,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        map.put("hospCode",userDTO.getHospCode());
        return lisService.getItemList(map);
    }

    /**
     * @Method saveInspectApply
     * @Desrciption 提交检验申请单接口
     @params [map]
     1、hospCode
     2、type (1:lis,2:pacs,空:所有)
      * @Author chenjun
     * @Date   2021-01-06 10:08
     * @Return java.lang.Boolean
     **/
    @PostMapping("/saveInspectApply")
    public WrapperResponse<Boolean> saveInspectApply(@RequestBody Map map,HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(MapUtils.get(map,"type"))) {
            throw new AppException("类型为空");
        }
        if (StringUtils.isEmpty(MapUtils.get(map,"applyIds"))) {
            throw new AppException("医技申请单Ids为空");
        }
        SysUserDTO userDTO = getSession(req, res) ;
        map.put("hospCode", userDTO.getHospCode());
        return lisService.saveInspectApply(map);
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
    @PostMapping("/saveInspectResultActive")
    public WrapperResponse<Boolean> saveInspectResultActive(@RequestBody Map map,HttpServletRequest req, HttpServletResponse res) {
        if (StringUtils.isEmpty(MapUtils.get(map,"pid"))) {
            throw new AppException("医技申请单pid为空");
        }
        if (StringUtils.isEmpty(MapUtils.get(map,"type"))) {
            throw new AppException("类型为空");
        }
        SysUserDTO userDTO = getSession(req, res) ;
        map.put("hospCode", userDTO.getHospCode());
        map.put("userId", userDTO.getId());
        map.put("userName", userDTO.getName());
        return lisService.saveInspectResultActive(map);
    }

    /**
     * @Method saveInspectResult
     * @Desrciption 回传检验结果接口(LIS推送)
     @params [map]
      * @Author chenjun
     * @Date   2021-01-13 08:44
     * @Return java.lang.Boolean
     **/
    @PostMapping("/saveInspectResult")
    public WrapperResponse<String> saveInspectResult(@RequestBody Map jsonMap,HttpServletRequest req, HttpServletResponse res) {
        if (jsonMap==null || jsonMap.size()<=0) {
            throw new AppException("参数为空");
        }
        Map map = new HashMap();
        SysUserDTO userDTO = getSession(req, res) ;
        map.put("hospCode", userDTO.getHospCode());
        map.put("userId", userDTO.getId());
        map.put("userName", userDTO.getName());
        map.put("jsonStr", MapUtils.get(jsonMap,"jsonStr"));
        lisService.saveInspectResult(map);
        Map retMap = new HashMap();
        retMap.put("state", "1");
        retMap.put("msg", "Success");
        return WrapperResponse.success(JSONObject.toJSONString(retMap));
    }

    /**
     * @Method getPDFReport
     * @Desrciption 查询PDF接口
     @params [map]
      * @Author chenjun
     * @Date   2021-01-13 08:44
     * @Return java.lang.Boolean
     **/
    @PostMapping("/getPDFReport")
    public WrapperResponse<Boolean> getPDFReport(HttpServletRequest req, HttpServletResponse res) {
        Map map = new HashMap();
        SysUserDTO userDTO = getSession(req, res) ;
        map.put("hospCode", userDTO.getHospCode());
        return lisService.getPDFReport(map);
    }

    /**
     * @Method callbackStatus
     * @Desrciption 回传状态
     @params [map]
      * @Author chenjun
     * @Date   2021-01-13 08:45
     * @Return java.lang.Boolean
     **/
    @PostMapping("/callbackStatus")
    public WrapperResponse<String> callbackStatus(HttpServletRequest req, HttpServletResponse res) {
        Map map = new HashMap();
        SysUserDTO userDTO = getSession(req, res) ;
        map.put("hospCode", userDTO.getHospCode());
        lisService.callbackStatus(map);
        Map retMap = new HashMap();
        retMap.put("state", "1");
        retMap.put("msg", "Success");
        return WrapperResponse.success(JSONObject.toJSONString(retMap));
    }

    /**
     * @Method callbackCriticalValue
     * @Desrciption 回传危机值结果信息（Lis主动推送）
     @params [map]
      * @Author chenjun
     * @Date   2021-01-13 09:16
     * @Return java.lang.Boolean
     **/
    @PostMapping("/callbackCriticalValue")
    public WrapperResponse<String> callbackCriticalValue(HttpServletRequest req, HttpServletResponse res) {
        Map map = new HashMap();
        SysUserDTO userDTO = getSession(req, res) ;
        map.put("hospCode", userDTO.getHospCode());
        lisService.callbackCriticalValue(map);
        Map retMap = new HashMap();
        retMap.put("state", "1");
        retMap.put("msg", "Success");
        return WrapperResponse.success(JSONObject.toJSONString(retMap));
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
    @PostMapping("/criticalValue")
    public WrapperResponse<Boolean> criticalValue(HttpServletRequest req, HttpServletResponse res) {
        Map map = new HashMap();
        SysUserDTO userDTO = getSession(req, res) ;
        map.put("hospCode", userDTO.getHospCode());
        return lisService.criticalValue(map);
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
    @GetMapping("/getTyeps")
    public WrapperResponse<List<Map>> getTyeps(HttpServletRequest req, HttpServletResponse res) {
        Map map = new HashMap<>();
        SysUserDTO userDTO = getSession(req, res) ;
        map.put("hospCode", userDTO.getHospCode());
        return lisService.getTyeps(map);
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
    @GetMapping("/getMedicalDatas")
    public WrapperResponse<PageDTO> getMedicalDatas(MedicalDataDTO medicalDataDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        medicalDataDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode", userDTO.getHospCode());
        map.put("medicalDataDTO", medicalDataDTO);
        return lisService.getMedicalDatas(map);
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
    @GetMapping("/getMedicalDataDetails")
    public WrapperResponse<PageDTO> getMedicalDataDetails(MedicalDataDetailDTO medicalDataDetailDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        medicalDataDetailDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode", userDTO.getHospCode());
        map.put("medicalDataDetailDTO", medicalDataDetailDTO);
        return lisService.getMedicalDataDetails(map);
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
    @GetMapping("/getMedicalData")
    public WrapperResponse<MedicalDataDTO> getMedicalData(MedicalDataDTO medicalDataDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        medicalDataDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode", userDTO.getHospCode());
        map.put("medicalDataDTO", medicalDataDTO);
        return lisService.getMedicalData(map);
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
    @GetMapping("/getMedicalDataDetail")
    public WrapperResponse<MedicalDataDetailDTO> getMedicalDataDetail(MedicalDataDetailDTO medicalDataDetailDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        medicalDataDetailDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode", userDTO.getHospCode());
        map.put("medicalDataDetailDTO", medicalDataDetailDTO);
        return lisService.getMedicalDataDetail(map);
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
    @PostMapping("/insertMedicalData")
    public WrapperResponse<Boolean> insertMedicalData(@RequestBody MedicalDataDTO medicalDataDTO,HttpServletRequest req, HttpServletResponse res) {
        medicalDataDTO.setId(SnowflakeUtils.getId());
        SysUserDTO userDTO = getSession(req, res) ;
        medicalDataDTO.setHospCode(userDTO.getHospCode());
        medicalDataDTO.setCrteId(userDTO.getId());
        medicalDataDTO.setCrteName(userDTO.getName());
        medicalDataDTO.setCrteTime(DateUtils.getNow());
        medicalDataDTO.setStatusCode(Constants.SF.S);

        Map map = new HashMap<>();
        map.put("hospCode", userDTO.getHospCode());
        map.put("medicalDataDTO", medicalDataDTO);
        return lisService.insertMedicalData(map);
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
    @PostMapping("/insertMedicalDataDetails")
    public WrapperResponse<Boolean> insertMedicalDataDetails(@RequestBody MedicalDataDTO medicalDataDTO,HttpServletRequest req, HttpServletResponse res) {
        List<MedicalDataDetailDTO> medicalDataDetailDTOList = medicalDataDTO.getMedicalDataDetailDTOList();
        if (ListUtils.isEmpty(medicalDataDetailDTOList)) {
            return WrapperResponse.error(500, "配置明细数据为空",null);
        }
        if (StringUtils.isEmpty(medicalDataDTO.getType())) {
            return WrapperResponse.error(500, "配置类型为空",null);
        }
        List<MedicalDataDetailDTO> list = new ArrayList<>();
        SysUserDTO userDTO = getSession(req, res) ;
        medicalDataDetailDTOList.stream().forEach(detail->{
            if (!StringUtils.isEmpty(detail.getResourceEName()) && !StringUtils.isEmpty(detail.getResourceEName())) {
                detail.setId(SnowflakeUtils.getId());
                detail.setType(medicalDataDTO.getType());
                detail.setHospCode(userDTO.getHospCode());
                detail.setCrteId(userDTO.getId());
                detail.setCrteName(userDTO.getName());
                detail.setCrteTime(DateUtils.getNow());
                list.add(detail);
            }
        });
        medicalDataDTO.setMedicalDataDetailDTOList(list);
        medicalDataDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode", userDTO.getHospCode());
        map.put("medicalDataDTO", medicalDataDTO);
        return lisService.insertMedicalDataDetails(map);
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
    @PostMapping("/updateMedicalData")
    public WrapperResponse<Boolean> updateMedicalData(@RequestBody MedicalDataDTO medicalDataDTO,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        medicalDataDTO.setHospCode(userDTO.getHospCode());
        medicalDataDTO.setCrteId(userDTO.getId());
        medicalDataDTO.setCrteName(userDTO.getName());
        medicalDataDTO.setCrteTime(DateUtils.getNow());

        Map map = new HashMap<>();
        map.put("hospCode", userDTO.getHospCode());
        map.put("medicalDataDTO", medicalDataDTO);
        return lisService.updateMedicalData(map);
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
    @PostMapping("/deleteMedicalData")
    public WrapperResponse<Boolean> deleteMedicalData(@RequestBody List<MedicalDataDTO> medicalDataDTOList,HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        medicalDataDTOList.stream().forEach(medicalDataDTO -> {
            medicalDataDTO.setHospCode(userDTO.getHospCode());
        });

        Map map = new HashMap<>();
        map.put("hospCode", userDTO.getHospCode());
        map.put("medicalDataDTOList", medicalDataDTOList);
        return lisService.deleteMedicalData(map);
    }
}