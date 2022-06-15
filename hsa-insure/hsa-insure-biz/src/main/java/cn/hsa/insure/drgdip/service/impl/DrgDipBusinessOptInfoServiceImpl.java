package cn.hsa.insure.drgdip.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.insure.drgdip.bo.impl.DrgDipBusinessOptInfoBOImpl;
import cn.hsa.module.drgdip.bo.DrgDipBusinessOptInfoBO;
import cn.hsa.module.drgdip.service.DrgDipBusinessOptInfoLogService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@HsafRestPath("/service/insure/insureDrgDipBusinessOptInfo")
@Service("insureDrgDipBusinessOptInfo_provider")
public class DrgDipBusinessOptInfoServiceImpl extends HsafService implements DrgDipBusinessOptInfoLogService {

    @Autowired
    DrgDipBusinessOptInfoBO drgDipBusinessOptInfoBO;

    /**
     * @Author 医保二部-张金平
     * @Date 2022-06-07 13:45
     * @Description 保存drg、dip质控业务操作信息日志
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     */
    @Override
    public WrapperResponse<Boolean> insertDrgDipBusinessOptInfoLog(Map<String, Object> map) {
        return WrapperResponse.success(drgDipBusinessOptInfoBO.insertDrgDipBusinessOptInfoLog(map));
    }

    /**
     * @Author 医保二部-张金平
     * @Date 2022-06-08 14:01
     * @Description 查询dip、drg质控过程日志记录
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     */
    @Override
    public WrapperResponse<PageDTO> queryDrgDipBusinessOptInfoLogList(Map<String, Object> map) {
        return WrapperResponse.success(drgDipBusinessOptInfoBO.queryDrgDipBusinessOptInfoLogList(MapUtils.get(map,"drgDipBusinessOptInfoLogDO")));
    }

    @Override
    public WrapperResponse<PageDTO> getDrgDipBusinessOptInfoLogDetail(Map<String, Object> map) {
        return null;
    }

    /**
     * 质控信息查询统计
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-06-09 10:15
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     */
    @Override
    public WrapperResponse<PageDTO> getDrgDipInfoByParam(Map<String, Object> map) {
      return WrapperResponse.success(drgDipBusinessOptInfoBO.getDrgDipInfoByParam(map));
    }
}
