package cn.hsa.module.sys.log.dao;

import cn.hsa.module.sys.log.dto.HisLogInfoCzDTO;

/**
 * @Package_name:cn.hsa.module.sys.log.bo
 * @Class_name:hisLogInfoCz
 * @Project_name:hsa-his
 * @Describe: 操作日志BO
 * @Author: zibo.yuan
 * @Date: 2020/12/1 11:10
 * @Email: zibo.yuan@powersi.com.cn
 */
public interface HisLogInfoCzDao {
    /***
     * @Description 新增正常日志
     * @param hisLogInfoCzDTO
     * @author: zibo.yuan
     * @date: 2020/12/1
     * @return: java.lang.Boolean
     **/
    int saveLogCz(HisLogInfoCzDTO hisLogInfoCzDTO);

}
