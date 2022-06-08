package cn.hsa.module.center.authorization.bo;

import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.center.authorization.entity.CenterFunctionAuthorizationDO;

/**
 * @author luonianxin
 * @version 1.0
 * @date 2022/6/7 15:41
 */
public interface CenterFunctionAuthorizationBO {

    /**
     *  根据医院编码与业务单据编码查询功能授权信息
     * @param hospCode 医院编码
     * @param orderTypeCode 单据编码
     * @return 中心端增值功能授权
     */
    WrapperResponse<CenterFunctionAuthorizationDO> queryBizAuthorizationByOrderTypeCode(String hospCode, String orderTypeCode);
}
