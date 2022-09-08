package cn.hsa.module.insure.outpt.service;


import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.outpt.service
 * @Class_name: insureUnifiedPayReversalTradeService
 * @Describe: 统一支付平台---冲正交易
 * @Author: liuqi1
 * @Eamil: liuqi1@powersi.com.cn
 * @Date: 2021/4/12 11:06
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@FeignClient(value = "hsa-insure")
public interface InsureUnifiedPayReversalTradeService {

    /**冲正交易
     * 可被冲正的交易包括：【2102】药店结算、【2103】药店结算撤销、【2207】门诊结算、【2208】门诊结算撤销、【2304】住院结算、【2207】住院结算撤销、【2401】入院办理
    * @Method UP_2601
    * @Desrciption
    * @param map
    * @Author liuqi1
    * @Date   2021/4/12 11:09
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    @PostMapping("serive/insure/reversalTrade/updateUP_2601")
    WrapperResponse<Boolean> updateUP_2601(Map<String, Object> map);

    /**冲正交易查询
    * @Method queryReversalTradePage
    * @Desrciption
    * @param map
    * @Author liuqi1
    * @Date   2021/4/12 19:45
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
    **/
    @PostMapping("serive/insure/reversalTrade/queryReversalTradePage")
    WrapperResponse<PageDTO> queryReversalTradePage(Map map);


    /**医药机构费用结算对总账数据查询
     * @Method queryDataWith3201
     * @Desrciption
     * @param parameterMap
     * @Author liuqi1
     * @Date   2021/4/13 20:11
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.base.PageDTO>
     **/
    @PutMapping("/service/insure/upOutptService/queryDataWith5261")
    WrapperResponse<PageDTO> queryDataWith5261(Map<String,Object> parameterMap);

    /**医药机构费用结算对总账查询
     * @Method queryDataWith3201
     * @Desrciption
     * @param map
     * @Author liuqi1
     * @Date   2021/4/16 11:20
     * @Return cn.hsa.base.PageDTO
     **/
    @PutMapping("/service/insure/upOutptService/queryDataWith3201")
    WrapperResponse<PageDTO> queryDataWith3201(Map<String,Object> map);

    /**医保通一支付平台,医药机构费用结算对总账接口调用
     * @Method updateUP_3201
     * @Desrciption
     * @param parameterMap
     * @Author liuqi1
     * @Date   2021/4/13 16:43
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    @PutMapping("/service/insure/upOutptService/updateUP_3201")
    WrapperResponse<Map<String,Object>> updateUP_3201(Map<String,Object> parameterMap);

    /**	医保通一支付平台,医药机构费用结算对明细账接口调用
     * @Method updateUP_3202
     * @Desrciption
     * @param parameterMap
     * @Author liuqi1
     * @Date   2021/4/13 17:30
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     **/
    @PutMapping("/service/insure/upOutptService/updateUP_3202")
    WrapperResponse<Map<String,Object>> updateUP_3202(Map<String,Object> parameterMap);

    /**医保通一支付平台,结算单查询接口调用
     * @Method updateUP_5261
     * @Desrciption
     * @param parameterMap
     * @Author liuqi1
     * @Date   2021/4/13 17:30
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     **/
    @PutMapping("/service/insure/upOutptService/updateUP_5261")
    WrapperResponse<Map<String,Object>> updateUP_5261(Map<String,Object> parameterMap);

    /**医保通一支付平台,结算信息查询接口调用
     * @Method updateUP_5262
     * @Desrciption
     * @param parameterMap
     * @Author liuqi1
     * @Date   2021/4/13 17:30
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     **/
    @PutMapping("/service/insure/upOutptService/updateUP_5262")
    WrapperResponse<Map<String,Object>> updateUP_5262(Map<String,Object> parameterMap);

    /**医保通一支付平台,结算单下载接口调用
    * @Method updateUP_5265
    * @Desrciption
    * @param parameterMap
    * @Author liuqi1
    * @Date   2021/4/16 11:21
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    @PutMapping("/service/insure/upOutptService/updateUP_5265")
    WrapperResponse<Map<String,Object>> updateUP_5265(Map<String,Object> parameterMap);

    /**
     * @Method queryStatementInfo
     * @Desrciption 对账单查询打印
     * @param paraMap
     * @Author liaojiguang
     * @Date   2021/10/21 09:01
     * @Return
     **/
    @GetMapping("/service/insure/upOutptService/queryStatementInfo")
    WrapperResponse<Map<String, Object>> queryStatementInfo(Map<String, Object> paraMap);

    /**
     * @Method queryDeclareInfos
     * @Desrciption 清算申报报表
     * @param paraMap
     * @Author liaojiguang
     * @Date   2021/10/21 09:01
     * @Return
     **/
    @GetMapping("/service/insure/upOutptService/queryDeclareInfosPage")
    WrapperResponse<PageDTO> queryDeclareInfosPage(Map<String, Object> paraMap);

    /**
     * @Method querySumDeclareInfosPage
     * @Desrciption 清算申报合计报表
     * @param paraMap
     * @Author liaojiguang
     * @Date   2021/10/21 09:01
     * @Return
     **/
    @GetMapping("/service/insure/upOutptService/querySumDeclareInfosPage")
    WrapperResponse<PageDTO> querySumDeclareInfosPage(Map<String, Object> paraMap);

    /**
     * @Method downLoadSettleInfo
     * @Desrciption  HIS结算单
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/3 11:42
     * @Return
     **/
    WrapperResponse<Map<String, Object>> downLoadSettleInfo(Map<String, Object> map);

    WrapperResponse<Map<String,Object>> checkOneSettle(Map<String, Object> map, InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @Method querySumDeclareInfoPrint
     * @Desrciption 清算申报合计报表打印
     * @param paraMap
     * @Author liaojiguang
     * @Date   2021/10/21 09:01
     * @Return
     **/
    WrapperResponse<Map<String, Object>> querySumDeclareInfoPrint(Map<String, Object> paraMap);

    /**
     * @Method querySumDeclareInfoPrint
     * @Desrciption 清算申报合计报表打印
     * @param paraMap
     * @Author liuhuiming
     * @Date 2022/3/16 09:01
     * @Return
     **/
    WrapperResponse<Map<String, Object>> querySumDeclareInfos(Map<String, Object> paraMap);

    /**
     * @Method queryDeclareInfos
     * @Desrciption 清算申报报表打印
     * @param paraMap
     * @Author liuhuiming
     * @Date 2022/3/16 09:01
     * @Return
     **/
    @GetMapping("/service/insure/upOutptService/queryDeclareInfos")
    WrapperResponse<Map<String, Object>> queryDeclareInfosPrint(Map<String, Object> paraMap);
    /**
     * @Method queryDeclareInfos
     * @Desrciption 清算申报报表打印
     * @param map
     * @Author liuhuiming
     * @Date 2022/3/16 09:01
     * @Return
     **/
    @GetMapping("/service/insure/upOutptService/queryDeclareInfos1")
    WrapperResponse<Map<String, Object>> queryDeclareInfosPrint1(Map map);
}
