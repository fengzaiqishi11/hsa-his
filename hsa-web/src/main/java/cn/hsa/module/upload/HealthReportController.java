
package cn.hsa.module.upload;

import cn.hsa.base.BaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sys.user.dto.SysUserDTO;
import cn.hsa.module.upload.healthreport.service.HealthReportService;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @公司 创智和宇信息技术股份有限公司 Copyright (c) 2006-2020,All Rights Reserved.
 * @备注 湖南卫生直报控制层
 * @创建者 pengbo
 * @修改者 pengbo
 * @版本 1
 * @日期 2020-12-28  17:27
 */
@RestController
@RequestMapping("/web/upload/health")
@Slf4j
public class HealthReportController extends BaseController {
    /**
     * 医院信息维护接口
     */
    @Resource
    private HealthReportService healthReportService_consumer;

    /**
     * @Method queryUploadTypeList
     * @Desrciption 根据医院编码查询
     * @Param id(主键), code（医院编码）
     * @Author pengbo
     * @Date 2020-12-29 11:01
     * @Return WrapperResponse<Map<String,Object>>
     *
     * @return*/
    @GetMapping("/queryUploadTypeList")
    public WrapperResponse<List<Map<String, Object>>> queryUploadTypeList(HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<String,Object> ();
        map.put("hospCode",sysUserDTO.getHospCode());
        return healthReportService_consumer.queryUploadTypeList(map);
    }

    /**
     * @Method queryDataPageByType
     * @Desrciption 根据医院编码查询
     * @Param id(主键), code（医院编码）
     * @Author pengbo
     * @Date 2020-12-29 11:01
     * @Return WrapperResponse<Map<String,Object>>
     *
     * @return*/
    @GetMapping("/queryDataPageByType")
    public WrapperResponse<PageDTO> queryDataPageByType(String start_time,String end_time,String type, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        Map<String,Object> map = new HashMap<String,Object> ();
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("start_time",start_time);
        map.put("end_time",end_time);
        map.put("type",type);
        return healthReportService_consumer.queryDataPageByType(map);
    }

    /**
     * @Method writeDbfZipFile
     * @Desrciption 根据医院编码查询
     * @Param id(主键), code（医院编码）
     * @Author pengbo
     * @Date 2020-12-29 11:01
     * @Return WrapperResponse<Map<String,Object>>
     *
     * @return*/
    @PostMapping("/writeDbfZipFile")
    public WrapperResponse<Boolean> writeDbfZipFile(@RequestBody Map<String,Object> map, HttpServletRequest req, HttpServletResponse res) {
        SysUserDTO sysUserDTO = getSession(req, res);
        map.put("hospCode",sysUserDTO.getHospCode());
        map.put("hospName",sysUserDTO.getHospName());
        return healthReportService_consumer.writeDbfZipFile(map);
    }
}
