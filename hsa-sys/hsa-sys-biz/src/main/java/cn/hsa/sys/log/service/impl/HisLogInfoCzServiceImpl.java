package cn.hsa.sys.log.service.impl;

import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.sys.code.dto.SysCodeDTO;
import cn.hsa.module.sys.log.bo.HisLogInfoCzBO;
import cn.hsa.module.sys.log.dao.HisLogInfoCzDao;
import cn.hsa.module.sys.log.dto.HisLogInfoCzDTO;
import cn.hsa.module.sys.log.service.HisLogInfoCzService;
import cn.hsa.util.MapUtils;
import cn.hsa.util.StringUtils;
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
@Service("hisLogInfoCzService_provider")
public class HisLogInfoCzServiceImpl implements HisLogInfoCzService {
    @Resource
    HisLogInfoCzBO hisLogInfoCzBo;


    /***
     * @Description 保存操作日志
     * @param map
     * @author: zibo.yuan
     * @date: 2020/12/1
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> saveLogCz(Map map) {
        try {
            HisLogInfoCzDTO hisLogInfoCzDTO = MapUtils.get(map, "hisLogInfoCzDTO");
            // 参数校验
            if (hisLogInfoCzDTO == null) {
                return WrapperResponse.error(400, "参数不能为空", null);
            }
            return WrapperResponse.success(hisLogInfoCzBo.saveLogCz(hisLogInfoCzDTO));
        } catch (Exception e) {
            throw new AppException(e.getMessage());
        }
    }


}
