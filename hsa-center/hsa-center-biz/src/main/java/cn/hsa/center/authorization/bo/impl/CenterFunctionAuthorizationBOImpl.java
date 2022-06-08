package cn.hsa.center.authorization.bo.impl;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.authorization.bo.CenterFunctionAuthorizationBO;
import cn.hsa.module.center.authorization.dao.CenterFunctionAuthorizationDAO;
import cn.hsa.module.center.authorization.entity.CenterFunctionAuthorizationDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author luonianxin
 * @version 1.0
 * @date 2022/6/7 16:30
 */
@Slf4j
@Component
public class CenterFunctionAuthorizationBOImpl implements CenterFunctionAuthorizationBO {

    private CenterFunctionAuthorizationDAO centerFunctionAuthorizationDAO;

    @Autowired
    public void setCenterFunctionAuthorizationDAO(CenterFunctionAuthorizationDAO centerFunctionAuthorizationDAO) {
        this.centerFunctionAuthorizationDAO = centerFunctionAuthorizationDAO;
    }

    @Override
    public WrapperResponse<CenterFunctionAuthorizationDO> queryBizAuthorizationByOrderTypeCode(String hospCode, String orderTypeCode) {
        CenterFunctionAuthorizationDO functionAuthorizationDO = centerFunctionAuthorizationDAO.queryBizAuthorizationByOrderTypeCode(hospCode,orderTypeCode);
        // 1.校验是否审核完成
        // 2.校验
        return WrapperResponse.success(functionAuthorizationDO);
    }
}
