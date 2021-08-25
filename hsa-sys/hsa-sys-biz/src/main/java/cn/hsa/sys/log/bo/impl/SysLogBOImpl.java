package cn.hsa.sys.log.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.module.sys.code.bo.SysCodeBO;
import cn.hsa.module.sys.code.dao.SysCodeDao;
import cn.hsa.module.sys.log.bo.SysLogBO;
import cn.hsa.module.sys.log.dao.SysLogDAO;
import cn.hsa.module.sys.log.dto.SysLogDTO;
import cn.hsa.util.DateUtils;
import cn.hsa.util.SnowflakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Package_name
 * @class_nameSysLogBOImpl
 * @Description 日志记录
 * @Author youxianlin
 * @Email: 254580179@qq.com
 * @Date2020/11/30 10:40
 * @Company 创智和宇
 **/
@Component
@Slf4j
public class SysLogBOImpl extends HsafBO implements SysLogBO {

    @Resource
    private SysLogDAO sysLogDAO;

    /**
     * @Method: insertLog
     * @Description: 日志入库
     * @Param: [sysLogDTO]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/11/30 10:42
     * @Return: java.lang.Boolean
     **/
    @Override
    public Boolean insertLog(SysLogDTO sysLogDTO) {
        sysLogDTO.setId(SnowflakeUtils.getId());
        sysLogDTO.setCrteTime(DateUtils.getNow());
        return sysLogDAO.insertLog(sysLogDTO)>0;
    }
}
