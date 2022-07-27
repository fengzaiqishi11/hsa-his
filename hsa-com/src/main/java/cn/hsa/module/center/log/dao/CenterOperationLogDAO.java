package cn.hsa.module.center.log.dao;

import cn.hsa.module.center.log.entity.CenterOperationLogDo;
import cn.hsa.module.center.log.entity.CenterPasswordModifyLogDo;


public interface CenterOperationLogDAO {
    /**
     *  新增中心端操作日志记录
     * @param logDTO
     * @return
     */
    int insertCenterOperationLogLog(CenterOperationLogDo logDTO);

}
