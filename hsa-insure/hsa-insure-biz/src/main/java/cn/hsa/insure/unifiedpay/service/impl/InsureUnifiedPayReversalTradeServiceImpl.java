package cn.hsa.insure.unifiedpay.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.outpt.bo.InsureUnifiedPayReversalTradeBO;
import cn.hsa.module.insure.outpt.service.InsureUnifiedPayReversalTradeService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *@Package_name: cn.hsa.insure.unifiedpay.service.impl
 *@Class_name: insureUnifiedPayReversalTradeServiceImpl
 *@Describe: 统一支付平台---冲正交易
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2021/4/12 11:11
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Slf4j
@HsafRestPath("/service/insure/insureUnifiedPayReversalTrade")
@Service("insureUnifiedPayReversalTradeService_provider")
public class InsureUnifiedPayReversalTradeServiceImpl extends HsafService implements InsureUnifiedPayReversalTradeService {

    @Resource
    InsureUnifiedPayReversalTradeBO  insureUnifiedPayReversalTradeBO;

    /**医保通一支付平台,冲正交易
    * @Method UP_2601
    * @Desrciption
    * @param map
    * @Author liuqi1
    * @Date   2021/4/12 11:12
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    @Override
    public WrapperResponse<Boolean> updateUP_2601(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedPayReversalTradeBO.updateUP_2601(map));
    }

    /**冲正交易查询
    * @Method queryReversalTradePage
    * @Desrciption
    * @param map
    * @Author liuqi1
    * @Date   2021/4/12 19:45
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @Override
    public WrapperResponse<PageDTO> queryReversalTradePage(Map map) {
        return WrapperResponse.success(insureUnifiedPayReversalTradeBO.queryReversalTradePage(MapUtils.get(map,"insureReversalTradeDTO")));
    }


    /**医药机构费用结算对总账数据查询
     * @Method queryDataWith3201
     * @Desrciption
     * @param parameterMap
     * @Author liuqi1
     * @Date   2021/4/13 20:11
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryDataWith5261(Map<String, Object> parameterMap) {
        return WrapperResponse.success(insureUnifiedPayReversalTradeBO.queryDataWith5261(parameterMap));
    }

    /**医药机构费用结算对总账查询
     * @Method queryDataWith3201
     * @Desrciption
     * @param map
     * @Author liuqi1
     * @Date   2021/4/16 11:20
     * @Return cn.hsa.base.PageDTO
     **/
    @Override
    public WrapperResponse<PageDTO> queryDataWith3201(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedPayReversalTradeBO.queryDataWith3201(map));
    }

    /**医保通一支付平台,医药机构费用结算对总账接口调用
     * @Method updateUP_3201
     * @Desrciption
     * @param parameterMap
     * @Author liuqi1
     * @Date   2021/4/13 17:02
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public WrapperResponse<Map<String, Object>> updateUP_3201(Map<String, Object> parameterMap) {
        return WrapperResponse.success(insureUnifiedPayReversalTradeBO.updateUP_3201(parameterMap));
    }

    /**	医保通一支付平台,医药机构费用结算对明细账接口调用
     * @Method updateUP_3202
     * @Desrciption
     * @param parameterMap
     * @Author liuqi1
     * @Date   2021/4/13 17:30
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     **/
    @Override
    public WrapperResponse<Map<String, Object>> updateUP_3202(Map<String, Object> parameterMap) {
        return WrapperResponse.success(insureUnifiedPayReversalTradeBO.updateUP_3202(parameterMap));
    }

    /**医保通一支付平台,结算单查询接口调用
     * @Method updateUP_5261
     * @Desrciption
     * @param parameterMap
     * @Author liuqi1
     * @Date   2021/4/13 17:30
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     **/
    @Override
    public WrapperResponse<Map<String, Object>> updateUP_5261(Map<String, Object> parameterMap) {
        return WrapperResponse.success(insureUnifiedPayReversalTradeBO.updateUP_5261(parameterMap));
    }

    /**医保通一支付平台,结算信息查询接口调用
     * @Method updateUP_5262
     * @Desrciption
     * @param parameterMap
     * @Author liuqi1
     * @Date   2021/4/13 17:30
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     **/
    @Override
    public WrapperResponse<Map<String, Object>> updateUP_5262(Map<String, Object> parameterMap) {
        return WrapperResponse.success(insureUnifiedPayReversalTradeBO.updateUP_5262(parameterMap));
    }

    /**医保通一支付平台,结算单下载接口调用
     * @Method updateUP_5265
     * @Desrciption
     * @param parameterMap
     * @Author liuqi1
     * @Date   2021/4/16 11:21
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @Override
    public WrapperResponse<Map<String, Object>> updateUP_5265(Map<String, Object> parameterMap) {
        return WrapperResponse.success(insureUnifiedPayReversalTradeBO.updateUP_5265(parameterMap));
    }



}
