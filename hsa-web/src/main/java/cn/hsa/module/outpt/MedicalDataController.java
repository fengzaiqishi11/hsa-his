package cn.hsa.module.outpt;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.medic.data.dto.MedicalDataDTO;
import cn.hsa.module.medic.data.dto.MedicalDataDetailDTO;
import cn.hsa.module.medic.data.service.MedicalDataService;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Package_name: cn.hsa.module.outpt
* @class_name: MedicalDataController
* @Description:
* @Author: youxianlin
* @Email: 254580179@qq.com
* @Date: 2021/1/28 15:45
* @Company: 创智和宇
**/
@RestController
@RequestMapping("/web/outpt/medicalData")
@Slf4j
public class MedicalDataController extends BaseController {

    @Resource
    private MedicalDataService medicalDataService_consumer;

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
        return medicalDataService_consumer.getTyeps(map);
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
        return medicalDataService_consumer.getMedicalDatas(map);
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
        return medicalDataService_consumer.getMedicalDataDetails(map);
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
        return medicalDataService_consumer.getMedicalData(map);
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
        return medicalDataService_consumer.getMedicalDataDetail(map);
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
        return medicalDataService_consumer.insertMedicalData(map);
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
        SysUserDTO userDTO = getSession(req, res) ;
        medicalDataDetailDTOList.stream().forEach(detail->{
            detail.setId(SnowflakeUtils.getId());
            detail.setType(medicalDataDTO.getType());
            detail.setHospCode(userDTO.getHospCode());
            detail.setCrteId(userDTO.getId());
            detail.setCrteName(userDTO.getName());
            detail.setCrteTime(DateUtils.getNow());
        });
        medicalDataDTO.setMedicalDataDetailDTOList(medicalDataDetailDTOList);
        medicalDataDTO.setHospCode(userDTO.getHospCode());
        Map map = new HashMap<>();
        map.put("hospCode", userDTO.getHospCode());
        map.put("medicalDataDTO", medicalDataDTO);
        return medicalDataService_consumer.insertMedicalDataDetails(map);
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
        return medicalDataService_consumer.updateMedicalData(map);
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
    public WrapperResponse<Boolean> deleteMedicalData(@RequestBody List<MedicalDataDTO> medicalDataDTOList, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO userDTO = getSession(req, res) ;
        medicalDataDTOList.stream().forEach(medicalDataDTO -> {
            medicalDataDTO.setHospCode(userDTO.getHospCode());
        });

        Map map = new HashMap<>();
        map.put("hospCode", userDTO.getHospCode());
        map.put("medicalDataDTOList", medicalDataDTOList);
        return medicalDataService_consumer.deleteMedicalData(map);
    }
}