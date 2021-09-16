package cn.hsa.module.insure.outpt.dao;

import cn.hsa.module.insure.outpt.dto.InsureReversalTradeDTO;

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


}
