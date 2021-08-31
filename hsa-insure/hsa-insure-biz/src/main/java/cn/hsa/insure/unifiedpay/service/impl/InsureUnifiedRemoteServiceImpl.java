package cn.hsa.insure.unifiedpay.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.inpt.bo.InsureUnifiedRemoteBO;
import cn.hsa.module.insure.inpt.service.InsureUnifiedRemoteService;
import cn.hsa.module.insure.module.entity.InsureUnifiedRemoteDO;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.service.impl
 * @class_name: InsureUnifiedRemoteServiceImpl
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/4/23 13:47
 * @Company: 创智和宇
 **/
@Slf4j
@HsafRestPath("/service/insure/insureUnifiedPayInpt")
@Service("insureUnifiedRemoteService_provider")
public class InsureUnifiedRemoteServiceImpl extends HsafService implements InsureUnifiedRemoteService {

    @Resource
    InsureUnifiedRemoteBO insureUnifiedRemoteBO;

    /**
     * @Method selectRemoteDetail
     * @Desrciption  提取异地清分明细:就医地使用此交易提取省内异地外来就医月度结算清分明细,供医疗机构进行确认处理
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/4/23 13:54
     * @Return
     **/
    @Override
    public WrapperResponse<Map<String, Object>> selectRemoteDetail(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedRemoteBO.insertRemoteDetail(map));
    }

    /**
     * @param map
     * @Method selectRemoteConfirmResult
     * @Desrciption 异地清分结果确认:就医地使用此交易提交省内异地外来就医月度结算清分确认结果。
     * @Param map
     * @Author fuhui
     * @Date 2021/4/23 13:54
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> selectRemoteConfirmResult(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedRemoteBO.updateRemoteConfirmResult(map));
    }

    /**
     * @param map
     * @Method selectRemoteConfirmResult
     * @Desrciption 异地清分结果确认回退:就医地使用此交易回退已经提交的就医月度结算清分确认结果。
     * @Param map
     * @Author fuhui
     * @Date 2021/4/23 13:54
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> updateRemoteConfirmBack(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedRemoteBO.updateRemoteConfirmBack(map));
    }

    /**
     * @param map
     * @Method 查询异地清分明细信息
     * @Desrciption
     * @Param
     * @Author fuhui
     * @Date 2021/6/8 20:22
     * @Return
     */
    @Override
    public WrapperResponse<PageDTO> queryPage(Map<String, Object> map) {
        InsureUnifiedRemoteDO insureUnifiedRemoteDO = MapUtils.get(map,"insureUnifiedRemoteDO");
        return WrapperResponse.success(insureUnifiedRemoteBO.queryPage(insureUnifiedRemoteDO));
    }
}
