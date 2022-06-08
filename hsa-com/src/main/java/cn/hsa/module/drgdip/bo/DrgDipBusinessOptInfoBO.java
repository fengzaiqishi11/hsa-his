package cn.hsa.module.drgdip.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.insure.drgdip.entity.DrgDipBusinessOptInfoLogDO;

import java.util.Map;

/**
 * @Author 医保二部-张金平
 * @Date 2022-06-07 10:34
 * @Description DRG/DIP质控业务操作日志记录BO接口
 */
public interface DrgDipBusinessOptInfoBO {
    /**
     * @Author 医保二部-张金平
     * @Date 2022-06-07 13:45
     * @Description 保存drg、dip质控业务操作信息日志
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     */
    Boolean insertDrgDipBusinessOptInfoLog(Map<String, Object> map);

    /**
     * @Author 医保二部-张金平
     * @Date 2022-06-08 14:02
     * @Description 查询dip、drg质控过程日志记录
     * @param drgDipBusinessOptInfoLogDO
     * @return cn.hsa.base.PageDTO
     */
    PageDTO queryDrgDipBusinessOptInfoLogList(DrgDipBusinessOptInfoLogDO drgDipBusinessOptInfoLogDO);
}
