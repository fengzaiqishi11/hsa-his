package cn.hsa.sys.log.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.sys.log.bo.HisLogInfoYcBO;
import cn.hsa.module.sys.log.dto.HisLogInfoYcDTO;
import cn.hsa.module.sys.log.service.HisLogInfoYcService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name:cn.hsa.module.sys.log.bo
 * @Class_name:hisLogInfoCz
 * @Project_name:hsa-his
 * @Describe: 操作日志BO
 * @Author: zibo.yuan
 * @Date: 2020/12/1 11:10
 * @Email: zibo.yuan@powersi.com.cn
 */
@HsafRestPath("/service/sys/log")
@Slf4j
@Service("hisLogInfoYcService_provider")
public class HisLogInfoYcServiceImpl implements HisLogInfoYcService {

    @Resource
    HisLogInfoYcBO hisLogInfoYcBO;


    /***
     * @Description 保存异常信息
     * @param map
     * @author: zibo.yuan
     * @date: 2020/12/1
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> saveLogYc(Map map) {
        try {
            HisLogInfoYcDTO hisLogInfoYcDTO = MapUtils.get(map, "hisLogInfoYcDTO");
            // 参数校验
            if (hisLogInfoYcDTO == null) {
                return WrapperResponse.error(400, "参数不能为空", null);
            }
            return WrapperResponse.success(hisLogInfoYcBO.saveLogYc(hisLogInfoYcDTO));
        } catch (Exception e) {
            return WrapperResponse.error(500, e.getMessage(), null);
        }
    }

    /**
    * @Method queryLogInfo
    * @Desrciption 查询日志信息
    * @param map
    * @Author liuqi1
    * @Date   2020/12/9 14:37
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
    **/
    @Override
    public WrapperResponse<PageDTO> queryLogInfo(Map map) {
        HisLogInfoYcDTO hisLogInfoYcDTO = MapUtils.get(map, "hisLogInfoYcDTO");
        PageDTO pageDTO = hisLogInfoYcBO.queryLogInfo(hisLogInfoYcDTO);
        return WrapperResponse.success(pageDTO);
    }

    /**
    * @Method queryLogFileInfo
    * @Desrciption 查询日志文件信息
    * @param map
    * @Author liuqi1
    * @Date   2020/12/11 15:05
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryLogFileInfo(Map map) {
        HisLogInfoYcDTO hisLogInfoYcDTO = MapUtils.get(map, "hisLogInfoYcDTO");
        PageDTO pageDTO = hisLogInfoYcBO.queryLogFileInfo(hisLogInfoYcDTO);
        return WrapperResponse.success(pageDTO);
    }


}
