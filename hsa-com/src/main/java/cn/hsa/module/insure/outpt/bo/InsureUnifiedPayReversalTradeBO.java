package cn.hsa.module.insure.outpt.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.outpt.dto.InsureReversalTradeDTO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 *@Package_name: cn.hsa.module.insure.outpt.bo
 *@Class_name: insureUnifiedPayReversalTradeBO
 *@Describe: 医保通一支付平台,冲正交易
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2021/4/12 11:14
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InsureUnifiedPayReversalTradeBO {

    /**医保通一支付平台,冲正交易接口调用
    * @Method UP_2601
    * @Desrciption
    * @param map
    * @Author liuqi1
    * @Date   2021/4/12 11:15
    * @Return java.util.Map<java.lang.String,java.lang.Object>
    **/
    Boolean updateUP_2601(Map<String, Object> map);

    /**调用医保统一支付，返回调用结果
    * @Method handleInputParamter
    * @Desrciption
    * @param hospCode 医院编码
    * @param insureRegCode 医保注册编码
    * @param infno 功能号
    * @param dataMap 接口数据
    * @Author liuqi1
    * @Date   2021/4/13 10:55
    * @Return java.lang.String
    **/
    Map<String,Object> invokingUpay (String hospCode,String insureRegCode,String infno,Map<String,Object> dataMap);

    /**冲正交易查询
    * @Method queryPage
    * @Desrciption 
    * @param insureReversalTradeDTO
    * @Author liuqi1
    * @Date   2021/4/12 19:43
    * @Return cn.hsa.base.PageDTO
    **/
    PageDTO queryReversalTradePage(InsureReversalTradeDTO insureReversalTradeDTO);


    /**医药机构费用结算对总账数据查询
     * @Method queryDataWith3201
     * @Desrciption
     * @param map
     * @Author liuqi1
     * @Date   2021/4/13 20:18
     * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    PageDTO queryDataWith5261(Map<String,Object> map);

    /**医药机构费用结算对总账查询
    * @Method queryDataWith3201
    * @Desrciption
    * @param map
    * @Author liuqi1
    * @Date   2021/4/16 11:20
    * @Return cn.hsa.base.PageDTO
    **/
    PageDTO queryDataWith3201(Map<String,Object> map);

    /**医保通一支付平台,医药机构费用结算对总账接口调用
     * @Method updateUP_3201
     * @Desrciption
     * @param parameterMap
     * @Author liuqi1
     * @Date   2021/4/13 16:43
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String,Object> updateUP_3201(Map<String,Object> parameterMap);

    /**	医保通一支付平台,医药机构费用结算对明细账接口调用
     * @Method updateUP_3202
     * @Desrciption
     * @param parameterMap
     * @Author liuqi1
     * @Date   2021/4/13 17:30
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String,Object> updateUP_3202(Map<String,Object> parameterMap);

    /**医保通一支付平台,结算单查询接口调用
     * @Method updateUP_5261
     * @Desrciption
     * @param parameterMap
     * @Author liuqi1
     * @Date   2021/4/13 17:30
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String,Object> updateUP_5261(Map<String,Object> parameterMap);

    /**医保通一支付平台,结算信息查询接口调用
     * @Method updateUP_5262
     * @Desrciption
     * @param parameterMap
     * @Author liuqi1
     * @Date   2021/4/13 17:30
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String,Object> updateUP_5262(Map<String,Object> parameterMap);

    /**医保通一支付平台,结算单下载接口调用
    * @Method updateUP_5265
    * @Desrciption
    * @param parameterMap
    * @Author liuqi1
    * @Date   2021/4/15 11:56
    * @Return java.util.Map<java.lang.String,java.lang.Object>
    **/
    Map<String,Object> updateUP_5265(Map<String, Object> parameterMap);

    /**
     * @param paraMap
     * @Method queryDeclareInfosPage
     * @Desrciption 清算申报报表
     * @Author liaojiguang
     * @Date 2021/10/21 09:01
     * @Return
     **/
    PageDTO queryDeclareInfosPage(Map<String, Object> paraMap);

    /**
     * @param paraMap
     * @Method querySumDeclareInfosPage
     * @Desrciption 清算申报合计报表
     * @Author liaojiguang
     * @Date 2021/10/21 09:01
     * @Return
     **/
    PageDTO querySumDeclareInfosPage(Map<String, Object> paraMap);

    Map<String,Object> queryStatementInfo(Map<String, Object> paraMap);

    /**
     * @Method downLoadSettleInfo
     * @Desrciption  HIS结算单
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/3 11:42
     * @Return
     **/
    Map<String,Object> downLoadSettleInfo(Map<String, Object> map);

    Map<String,Object> checkOneSettle(Map<String, Object> map, InsureIndividualVisitDTO insureIndividualVisitDTO);

    /**
     * @param paraMap
     * @Method querySumDeclareInfoPrint
     * @Desrciption 清算申报合计报表打印
     * @Author liaojiguang
     * @Date 2021/10/21 09:01
     * @Return
     **/
    Map<String,Object> querySumDeclareInfoPrint(Map<String, Object> paraMap);

    /**
     * @param paraMap
     * @Method querySumDeclareInfoPrint
     * @Desrciption 清算申报合计报表打印
     * @Author liuhuiming
     * @Date 2022/3/16 09:01
     * @Return
     **/
    Map<String,Object> querySumDeclareInfos(Map<String, Object> paraMap);

    /**
     * @param paraMap
     * @Method queryDeclareInfosPage
     * @Desrciption 清算申报报表明细打印
     * @Author liuhuiming
     * @Date 2022/3/16 09:01
     * @Return
     **/
    Map<String,Object> queryDeclareInfosPrint(Map<String, Object> paraMap);
    /**
     * @param map
     * @Method queryDeclareInfosPage
     * @Desrciption 清算申报报表明细打印
     * @Author liuhuiming
     * @Date 2022/3/16 09:01
     * @Return
     **/
    Map<String,Object> queryDeclareInfosPrint1(Map map);
}
