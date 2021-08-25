package cn.hsa.module.sys.log.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.sys.log.dto.HisLogInfoYcDTO;

/**
 * @Package_name:cn.hsa.module.sys.log.bo
 * @Class_name:hisLogInfoCz
 * @Project_name:hsa-his
 * @Describe: 操作日志BO
 * @Author: zibo.yuan
 * @Date: 2020/12/1 11:10
 * @Email: zibo.yuan@powersi.com.cn
 */
public interface HisLogInfoYcBO {
    /***
     * @Description 新增异常日志
     * @param hisLogInfoYcDTO
     * @author: zibo.yuan
     * @date: 2020/12/1
     * @return: java.lang.Boolean
     **/
    Boolean saveLogYc(HisLogInfoYcDTO hisLogInfoYcDTO);

    /**
    * @Method queryLogInfo
    * @Desrciption 查询日志信息
    * @param hisLogInfoYcDTO
    * @Author liuqi1
    * @Date   2020/12/9 14:38
    * @Return cn.hsa.base.PageDTO
    **/
    PageDTO queryLogInfo(HisLogInfoYcDTO hisLogInfoYcDTO);

    /**
    * @Method queryLogFileInfo
    * @Desrciption 查询日志文件信息
    * @param hisLogInfoYcDTO
    * @Author liuqi1
    * @Date   2020/12/11 14:59
    * @Return cn.hsa.base.PageDTO
    **/
    PageDTO queryLogFileInfo(HisLogInfoYcDTO hisLogInfoYcDTO);

}
