package cn.hsa.module.sys.log.bo;

import cn.hsa.base.dto.HisLogInfo;
import cn.hsa.module.sys.log.dto.HisLogInfoCzDTO;
import cn.hsa.module.sys.log.dto.HisLogInfoYcDTO;

import java.util.List;

/**
 * @Package_name:cn.hsa.module.sys.log.bo
 * @Class_name:hisLogInfoCz
 * @Project_name:hsa-his
 * @Describe: 操作日志BO
 * @Author: zibo.yuan
 * @Date: 2020/12/1 11:10
 * @Email: zibo.yuan@powersi.com.cn
 */
public interface HisLogInfoCzBO {
    /***
     * @Description 新增正常日志
     * @param hisLogInfoCzDTO
     * @author: zibo.yuan
     * @date: 2020/12/1
     * @return: java.lang.Boolean
     **/
    Boolean saveLogCz(HisLogInfoCzDTO hisLogInfoCzDTO);

    /**
    * @Method queryLogInfo
    * @Desrciption 从日志文件中获取日志信息
    * @param hisLogInfoYcDTO
    * @Author liuqi1
    * @Date   2020/12/10 15:20
    * @Return java.util.List<cn.hsa.module.sys.log.dto.HisLogInfoYcDTO>
    **/
    List<HisLogInfoYcDTO> queryLogInfoWithFile(HisLogInfoYcDTO hisLogInfoYcDTO);

}
