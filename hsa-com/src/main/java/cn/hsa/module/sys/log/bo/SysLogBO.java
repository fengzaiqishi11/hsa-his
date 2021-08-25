package cn.hsa.module.sys.log.bo;

import cn.hsa.module.sys.log.dto.SysLogDTO;

/**
 * @Package_name
 * @class_nameSysLogDAO
 * @Description 日志收集DAO
 * @Author youxianlin
 * @Email: 254580179@qq.com
 * @Date2020/11/30 10:32
 * @Company 创智和宇
 **/
public interface SysLogBO {

    /**
    * @Method: insertMenuButton
    * @Description: 日志入库
    * @Param:
    * @Author: youxianlin
    * @Email: 254580179@qq.com
    * @Date: 2020/11/30 10:32
    * @Return:
    **/
    Boolean insertLog(SysLogDTO sysLogDTO);
    
}
