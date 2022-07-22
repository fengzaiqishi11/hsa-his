package cn.hsa.center.log.service.impl;


import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.log.dao.CenterOperationLogDAO;
import cn.hsa.module.center.log.dao.CenterPasswordModifyLogDAO;
import cn.hsa.module.center.log.entity.CenterOperationLogDo;
import cn.hsa.module.center.log.entity.CenterPasswordModifyLogDo;
import cn.hsa.module.center.log.service.CenterPasswordModifyLogService;
import cn.hsa.util.MapUtils;
import cn.hsa.util.SnowflakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  中心端日志信息记录服务
 * @Package_name: cn.hsa.center.log.service.impl
 * @Class_name: CenterPasswordModifyLogServiceImpl
 * @author:  luonianxin
 * @Email: nianxin.luo@powersi.com
 * @date: 2022/4/26 11:21
 **/
@Slf4j
@Service("centerPasswordModifyLogService_provider")
public class CenterPasswordModifyLogServiceImpl extends HsafService implements CenterPasswordModifyLogService {

    @Resource
    private CenterPasswordModifyLogDAO centerPasswordModifyLogDAO;
    @Resource
    private CenterOperationLogDAO centerOperationLogDAO;

    private final ReentrantLock lock = new ReentrantLock(true);

    /**
     *  插入密码修改日志
     * @param map 参数 CenterPasswordModifyLogDo 必传系统日志参数
     * @return
     */
    @Override
    public WrapperResponse<Boolean> insertCenterPasswordModifyLog(Map map) {
        lock.lock();
        try {
            CenterPasswordModifyLogDo modifyLogDo = MapUtils.get(map, "modifyLog");
            if (null == modifyLogDo) {
                return WrapperResponse.error(HttpStatus.NO_CONTENT.value(), "部分必填参数缺失请检查！！", false);
            }
            modifyLogDo.setId(SnowflakeUtils.getId());
            modifyLogDo.setCrteTime(new Date());
            int affectRows = centerPasswordModifyLogDAO.insertCenterPasswordModifyLog(modifyLogDo);
            return WrapperResponse.success(affectRows > 0);
        }finally {
            lock.unlock();
        }
    }

    /**
     * 系统操作日志记录
     *
     * @param map
     * @return
     */
    @Override
    public WrapperResponse<Boolean> insertCenterOperationLog(Map map) {
        lock.lock();
        try {
            CenterOperationLogDo renewalLog = MapUtils.get(map, "renewalLog");
            if (null == renewalLog) {
                return WrapperResponse.error(HttpStatus.NO_CONTENT.value(), "部分必填参数缺失请检查！！", false);
            }
            renewalLog.setId(SnowflakeUtils.getId());
            renewalLog.setCrteTime(new Date());
            int affectRows = centerOperationLogDAO.insertCenterOperationLogLog(renewalLog);
            return WrapperResponse.success(affectRows > 0);
        }finally {
            lock.unlock();
        }
    }
}
