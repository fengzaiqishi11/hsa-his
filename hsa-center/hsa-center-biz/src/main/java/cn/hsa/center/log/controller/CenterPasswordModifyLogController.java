package cn.hsa.center.log.controller;

import cn.hsa.base.CenterBaseController;
import cn.hsa.base.PageDTO;
import cn.hsa.base.RSAUtil;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.center.hospital.dto.CenterHospitalDTO;
import cn.hsa.module.center.hospital.dto.CenterSyncFlowDto;
import cn.hsa.module.center.hospital.entity.CenterRootDatabaseBO;
import cn.hsa.module.center.hospital.service.CenterHospitalService;
import cn.hsa.module.center.log.entity.CenterOperationLogDo;
import cn.hsa.module.center.log.entity.CenterPasswordModifyLogDo;
import cn.hsa.module.center.log.service.CenterPasswordModifyLogService;
import cn.hsa.module.center.parameter.dto.CenterParameterDTO;
import cn.hsa.module.center.parameter.service.CenterParameterService;
import cn.hsa.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.center
 * @Class_name:: CenterHospitalController
 * @Description: 医院信息管理控制层
 * @Author: zhangxuan
 * @Date: 2020-07-30 10:33
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@RestController
@RequestMapping("/center/passwordModifyLog")
@Slf4j
public class CenterPasswordModifyLogController extends CenterBaseController {

    /**
     *  密码修改日志记录接口
     */
    @Resource
    private CenterPasswordModifyLogService centerPasswordModifyLogService;


    /**
     * @Menthod queryPage()
     * @Desrciption   根据条件分页查询参数信息
     * @Param
     * 1. [CenterHospitalDTO] 参数数据传输DTO对象
     * @Author zhangxuan
     * @Date   2020/7/28 16:30
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.center.PageDTO>
     **/
    @GetMapping("/queryPage")
    public WrapperResponse<PageDTO> queryPage(CenterHospitalDTO centerHospitalDTO){
        return null;
    }

    @GetMapping("/insert")
    public WrapperResponse<Boolean> insert(){
        Map<String,Object> params = new HashMap<>();
        CenterPasswordModifyLogDo modifyLogDo = CenterPasswordModifyLogDo.builder()
                .id(SnowflakeUtils.getId())
                .crteId("1")
                .crteName("系统管理员")
                .crteTime(new Date())
                .hospCode("0001")
                .userCode("admin")
                .userId("11")
                .deptId("10001")
                .deptName("质量管理部")
                .hospName("测试医院")
                .ip("127.0.0.1").build();
        params.put("modifyLog",modifyLogDo);
        return centerPasswordModifyLogService.insertCenterPasswordModifyLog(params);
    }

    @GetMapping("/insertCenterOperationLog")
    public WrapperResponse<Boolean> insertCenterOperationLog(){
        Map<String,Object> params = new HashMap<>();
        CenterOperationLogDo renewalLog = CenterOperationLogDo.builder()
                .id(SnowflakeUtils.getId())
                .crteId("1")
                .crteName("系统管理员")
                .crteTime(new Date())
                .hospCode("0001")
                .userCode("admin")
                .userId("11")
                .deptId("10001")
                .deptName("质量管理部")
                .hospName("测试医院")
                .ip("127.0.0.1")
                .bizId("YYXQ100001").build();
        params.put("renewalLog",renewalLog);
        return centerPasswordModifyLogService.insertCenterOperationLog(params);
    }

}
