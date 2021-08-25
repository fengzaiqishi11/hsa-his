package cn.hsa.module.sys.log.dao;

import cn.hsa.base.dto.HisLogInfo;
import cn.hsa.module.sys.log.dto.HisLogInfoYcDTO;

import java.util.List;

/**
 * @Package_name:cn.hsa.module.sys.log.bo
 * @Class_name:hisLogInfoCz
 * @Project_name:hsa-his
 * @Describe: 异常日志BO
 * @Author: zibo.yuan
 * @Date: 2020/12/1 11:10
 * @Email: zibo.yuan@powersi.com.cn
 */
public interface HisLogInfoYcDao {
    /***
     * @Description 新增正常日志
     * @param hisLogInfoYcDTO
     * @author: zibo.yuan
     * @date: 2020/12/1
     * @return: java.lang.Boolean
     **/
    int saveLogYc(HisLogInfoYcDTO hisLogInfoYcDTO);

    /**
    * @Method queryLogInfoWithYc
    * @Desrciption 异常日志查询
    * @param hisLogInfoYcDTO
    * @Author liuqi1
    * @Date   2020/12/9 14:46
    * @Return java.util.List<cn.hsa.module.sys.log.dto.HisLogInfoYcDTO>
    **/
    List<HisLogInfoYcDTO> queryLogInfoWithYc(HisLogInfoYcDTO hisLogInfoYcDTO);

    /**
    * @Method queryLogInfoWithCz
    * @Desrciption 操作日志日志查询
    * @param hisLogInfoYcDTO
    * @Author liuqi1
    * @Date   2020/12/9 14:46
    * @Return java.util.List<cn.hsa.module.sys.log.dto.HisLogInfoYcDTO>
    **/
    List<HisLogInfoYcDTO> queryLogInfoWithCz(HisLogInfoYcDTO hisLogInfoYcDTO);

}
