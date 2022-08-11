package cn.hsa.module.insure.outpt.dao;

import cn.hsa.module.insure.module.dto.InsureConfigurationDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualSettleDTO;
import cn.hsa.module.insure.outpt.dto.InsureReversalTradeDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**冲正交易
 *@Package_name: cn.hsa.module.insure.outpt.dao
 *@Class_name: InsureReversalTradeDAO
 *@Describe:
 *@Author: liuqi1
 *@Eamil: liuqi1@powersi.com.cn
 *@Date: 2021/4/12 16:08
 *@Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InsureReversalTradeDAO {

    /**新增冲正交易记录
    * @Method insertInsureReversalTrade
    * @Desrciption
    * @param insureReversalTradeDTO
    * @Author liuqi1
    * @Date   2021/4/12 16:26
    * @Return int
    **/
    int insertInsureReversalTrade(InsureReversalTradeDTO insureReversalTradeDTO);

    /**冲正交易查询
    * @Method queryReversalTradePage
    * @Desrciption
    * @param insureReversalTradeDTO
    * @Author liuqi1
    * @Date   2021/4/12 19:52
    * @Return java.util.List<cn.hsa.module.insure.outpt.dto.InsureReversalTradeDTO>
    **/
    List<InsureReversalTradeDTO> queryReversalTradePage(InsureReversalTradeDTO insureReversalTradeDTO);

    /**结算单查询
    * @Method queryDataWith5261
    * @Desrciption 
    * @param map
    * @Author liuqi1
    * @Date   2021/4/13 21:05
    * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    List<Map<String,Object>> queryDataWith5261(Map<String,Object> map);

    /**医药机构费用结算对总账查询
    * @Method queryDataWith3201
    * @Desrciption 
    * @param map
    * @Author liuqi1
    * @Date   2021/4/16 11:13
    * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    List<Map<String,Object>> queryDataWith3201(Map<String,Object> map);

    /**医药机构费用结算对总账查询
    * @Method queryDataWith3201
    * @Desrciption
    * @param map
    * @Author liuqi1
    * @Date   2021/4/16 11:13
    * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    List<Map<String,Object>> queryDataXiZangWith3201(Map<String,Object> map);

    /**医药机构费用结算对明细账查询
    * @Method queryDataWith3202
    * @Desrciption
    * @param map
    * @Author liuqi1
    * @Date   2021/4/16 16:31
    * @Return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    **/
    List<Map<String,Object>> queryDataWith3202(Map<String,Object> map);

    Map<String,Object> getHisReckonInfo(Map<String,Object> map);

    /**
     * @param paraMap
     * @Method queryStatementInfo
     * @Desrciption 对账单查询打印
     * @Author liaojiguang
     * @Date 2021/10/21 09:01
     * @Return
     **/
    List<Map<String, Object>> queryStatementInfo(Map<String, Object> paraMap);

    /**
     * @param paraMap
     * @Method getStatementSumInfo
     * @Desrciption 对账单合计金额查询
     * @Author liaojiguang
     * @Date 2021/10/21 09:01
     * @Return
     **/
    Map<String, Object> getStatementSumInfo(Map<String, Object> paraMap);


    InsureConfigurationDTO getInsureConfiguration(Map<String, Object> paraMap);

    /**
     * @param paraMap
     * @Method queryDeclareInfosPage
     * @Desrciption 清算申报报表
     * @Author liaojiguang
     * @Date 2021/10/21 09:01
     * @Return
     **/
    List<Map<String, Object>> queryDeclareInfosPage(Map<String, Object> paraMap);

    /**
     * @param paraMap
     * @Method querySumDeclareInfosPage
     * @Desrciption 清算申报报表
     * @Author liaojiguang
     * @Date 2021/10/21 09:01
     * @Return
     **/
    List<Map<String,Object>> querySumDeclareInfosPage(Map<String, Object> paraMap);

    /**
     * @param paraMap
     * @Method querySumDeclareInfosPage
     * @Desrciption 清算申报报表(门诊)
     * @Author liaojiguang
     * @Date 2021/10/21 09:01
     * @Return
     **/
    List<Map<String,Object>> queryOutptDeclareInfosPage(Map<String, Object> paraMap);

    /**
     * @param paraMap
     * @Method querySumDeclareInfosPage
     * @Desrciption 清算申报合计报表(门诊)
     * @Author liaojiguang
     * @Date 2021/10/21 09:01
     * @Return
     **/
    List<Map<String,Object>> queryOutptSumDeclareInfosPage(Map<String, Object> paraMap);

    /**
     * @param paraMap
     * @Method queryYZSDeclareInfosPage
     * @Desrciption 一站式清算申报报表
     * @Author liaojiguang
     * @Date 2021/10/21 09:01
     * @Return
     **/
    List<Map<String,Object>> queryYZSDeclareInfosPage(Map<String, Object> paraMap);

    /**
     * @param paraMap
     * @Method querySumDeclareInfosPage
     * @Desrciption 一站式清算申报合计报表
     * @Author liaojiguang
     * @Date 2021/10/21 09:01
     * @Return
     **/
    List<Map<String,Object>> queryYZSSumDeclareInfosPage(Map<String, Object> paraMap);

    /**
     * @Method selectMdOrIns
     * @Desrciption  查询全国就医地区划和参保地区划
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/16 8:38
     * @Return
    **/
    List<Map<String,Object>> selectMdOrIns(@Param("hospCode") String hospCode);

    /**
     * @Method querySettleInfo
     * @Desrciption  查询结算信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/11/16 16:16
     * @Return
    **/
    InsureIndividualSettleDTO querySettleInfo(Map<String, Object> map);

    /**
     * @param paraMap
     * @Method querySumDeclareInfosPage
     * @Desrciption 清算申报合计报表
     * @Author liaojiguang
     * @Date 2021/10/21 09:01
     * @Return
     **/
    List<Map<String,Object>> querySumDeclareInfos(Map<String, Object> paraMap);

    /**
     * @param paraMap
     * @Method queryDeclareInfosPage
     * @Desrciption 清算申报报表
     * @Author liaojiguang
     * @Date 2021/10/21 09:01
     * @Return
     **/
    List<Map<String, Object>> queryDeclareInfos(Map<String, Object> paraMap);

    List<Map<String, Object>> queryDataJiuJiangMzWith3201(Map<String, Object> map);

    List<Map<String, Object>> queryDataJiuJiangZyWith3201(Map<String, Object> map);
}
