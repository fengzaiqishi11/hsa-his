package cn.hsa.module.inpt.fees.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.fees.bo
 * @Class_name: InptSettlementBO
 * @Describe(描述): 住院结算BO
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/09/25 9:20
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface InptSettlementBO {

    /**
     * @Menthod queryInptvisitByPage
     * @Desrciption 查询可结算的患者信息
     * @param inptVisitDTO 查询条件
     * @Author Ou·Mr
     * @Date 2020/9/25 11:55
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    WrapperResponse queryInptvisitByPage(InptVisitDTO inptVisitDTO);

    /**
     * @Menthod queryInptCostByList
     * @Desrciption 查询患者住院费用信息、预交金信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/9/25 11:56
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    WrapperResponse queryInptCostByList(Map param);

    /**
     * @Menthod saveCostTrial
     * @Desrciption 住院试算
     * @param inptVisitDTO 请求参数
     * @Author Ou·Mr
     * @Date 2020/9/25 11:56
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    WrapperResponse saveCostTrial(InptVisitDTO inptVisitDTO);

    /**
     * @Menthod saveSettle
     * @Desrciption 住院结算
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/9/25 11:57
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    WrapperResponse insureUnifiedPayInpt(Map param);

    /**
     * @Method insureUnifiedPayInpt
     * @Desrciption 住院预结算
     * @Param
     *
     * @Author 曹亮
     * @Date   2021/7/14 11:17
     * @Return
     **/
    WrapperResponse saveSettle(Map param);

    /**
     * @Method: querySettle
     * @Description:
     * @Param: [params]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/12/8 14:28
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String, Object> querySettle(String settleId,String hospCode,String userName);

    /**
     * @Menthod: queryDiagnose
     * @Desrciption: 根据就诊id查询对应的诊断列表
     * @Param: inptVisitDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-03-04 19:02
     * @Return: PageDTO
     **/
    PageDTO queryDiagnose(InptVisitDTO inptVisitDTO);

    /**
     * @param map
     * @Method editDischargeInpt
     * @Desrciption 医保出院办理
     * @Param params
     * @Author fuhui
     * @Date 2021/5/28 11:40
     * @Return
     */
    Boolean editDischargeInpt(Map<String, Object> map);

    /**
     * @param map
     * @Method editCancelDischargeInpt
     * @Desrciption 医保出院办理撤销
     * @Param params
     * @Author fuhui
     * @Date 2021/5/28 11:40
     * @Return
     */
    Boolean editCancelDischargeInpt(Map<String, Object> map);

    /**
     * @Menthod saveBabyCostTrial
     * @Desrciption 住院试算(婴儿)
     * @param inptVisitDTO 请求参数
     * @Author liuliyun
     * @Date 2021/05/17 15:11
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    WrapperResponse saveBabyCostTrial(InptVisitDTO inptVisitDTO);

    /**
     * @Menthod saveBabySettle
     * @Desrciption 住院结算(婴儿)
     * @param param 请求参数
     * @Author liuliyun
     * @Date 2021/05/17 15:39
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    WrapperResponse saveBabySettle(Map param);


    /**
    * @Menthod saveAttributionCostTrial
    * @Desrciption 归属结算试算
    *
    * @Param
    * [inptVisitDTO]
    *
    * @Author jiahong.yang
    * @Date   2021/9/1 14:30
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
    **/
    WrapperResponse saveAttributionCostTrial(InptVisitDTO inptVisitDTO);


    /**
    * @Menthod saveAttributionSettle
    * @Desrciption 归属结算
    *
    * @Param
    * [param]
    *
    * @Author jiahong.yang
    * @Date   2021/9/1 14:30
    * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
    **/
    WrapperResponse saveAttributionSettle(Map param);
}
