package cn.hsa.center.authorization.service.impl;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.authorization.bo.CenterFunctionAuthorizationBO;
import cn.hsa.module.center.authorization.dao.CenterFunctionAuthorizationDAO;
import cn.hsa.module.center.authorization.entity.CenterFunctionAuthorizationDO;
import cn.hsa.module.center.authorization.service.CenterFunctionAuthorizationService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * @author luonianxin
 * @version 1.0
 * @date 2022/6/7 16:30
 */
@Slf4j
@Service("centerFunctionAuthorizationService_provider")
public class CenterFunctionAuthorizationServiceImpl implements CenterFunctionAuthorizationService {

    private CenterFunctionAuthorizationBO centerFunctionAuthorizationBO;

    @Autowired
    public void setCenterFunctionAuthorizationDAO(CenterFunctionAuthorizationBO centerFunctionAuthorizationBO) {
        this.centerFunctionAuthorizationBO = centerFunctionAuthorizationBO;
    }


    @Override
    public WrapperResponse<CenterFunctionAuthorizationDO> queryBizAuthorizationByOrderTypeCode(Map<String,Object> params) {
        String hospCode = MapUtils.getEmptyErr(params,"hospCode","医院编码【hospCode】不能为空!");
        String orderTypeCode = MapUtils.getEmptyErr(params,"orderTypeCode","业务编码【orderTypeCode】不能为空!");

        return centerFunctionAuthorizationBO.queryBizAuthorizationByOrderTypeCode(hospCode,orderTypeCode);
    }

    /**
     * 新增功能授权数据
     *
     * @param functionAuthorizationDO
     * @return
     */

    public WrapperResponse<CenterFunctionAuthorizationDO> insertBizAuthorization(CenterFunctionAuthorizationDO functionAuthorizationDO) {
        return centerFunctionAuthorizationBO.insertBizAuthorization(functionAuthorizationDO);
    }
}
