package cn.hsa.sys.log.service.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.sys.code.dto.SysCodeDTO;
import cn.hsa.module.sys.code.service.SysCodeService;
import cn.hsa.module.sys.log.bo.SysLogBO;
import cn.hsa.module.sys.log.dto.SysLogDTO;
import cn.hsa.module.sys.log.service.SysLogService;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name
 * @class_nameSysLogServiceImpl
 * @Description
 * @Author youxianlin
 * @Email: 254580179@qq.com
 * @Date2020/11/30 10:42
 * @Company 创智和宇
 **/
@HsafRestPath("/service/sys/log")
@Slf4j
@Service("sysLogService_provider")
public class SysLogServiceImpl extends HsafBO implements SysLogService {

    @Resource
    private SysLogBO sysLogBO;

    @Override
    public WrapperResponse<Boolean> insertLog(Map map) {
        SysLogDTO sysLogDTO = MapUtils.get(map, "sysLogDTO");
        // 参数校验
        if (sysLogDTO == null) {
            throw new AppException("参数不能为空");
        }
        return WrapperResponse.success(sysLogBO.insertLog(sysLogDTO));
    }
}
