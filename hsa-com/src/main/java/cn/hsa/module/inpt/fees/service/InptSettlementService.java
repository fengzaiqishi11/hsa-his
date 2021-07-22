package cn.hsa.module.inpt.fees.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.inpt.fees.service
 * @Class_name: InptSettlementService
 * @Describe(描述): 住院结算Service
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/09/25 9:21
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-inpt")
public interface InptSettlementService {

    /**
     * @Menthod queryInptvisitByPage
     * @Desrciption 查询可结算的患者信息
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/9/25 11:55
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping("/service/inpt/inptSettlement/queryInptvisitByPage")
    WrapperResponse queryInptvisitByPage(Map param);

    /**
     * @Menthod queryInptCostByList
     * @Desrciption 查询患者住院费用信息、预交金信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/9/25 11:56
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping("/service/inpt/inptSettlement/queryInptCostByList")
    WrapperResponse queryInptCostByList(Map param);

    /**
     * @Menthod saveCostTrial
     * @Desrciption 住院试算
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/9/25 11:56
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @PostMapping("/service/inpt/inptSettlement/saveCostTrial")
    WrapperResponse saveCostTrial(Map param);

    /**
     * @Menthod saveSettle
     * @Desrciption 住院结算
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/9/25 11:57
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @PostMapping("/service/inpt/inptSettlement/saveSettle")
    WrapperResponse saveSettle(Map param);

    /**
     * @Method: querySettle
     * @Description: 获取住院发票数据
     * @Param: [params]
     * @Author: youxianlin
     * @Email: 254580179@qq.com
     * @Date: 2020/12/8 14:27
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse
     **/
    @GetMapping("/service/inpt/inptSettlement/querySettle")
    WrapperResponse<Map<String,Object>> querySettle(Map<String, Object> params);

    /**
     * @Menthod: queryDiagnose
     * @Desrciption: 根据就诊id查询对应的诊断列表
     * @Param: inptVisitDTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-03-04 19:02
     * @Return: PageDTO
     **/
    @GetMapping("/service/inpt/inptSettlement/queryDiagnose")
    WrapperResponse<PageDTO> queryDiagnose(Map map);

    /**
     * @Method editDischargeInpt
     * @Desrciption  医保出院办理
     * @Param params
     *
     * @Author fuhui
     * @Date   2021/5/28 11:40
     * @Return
     **/
    @PostMapping("/service/inpt/inptSettlement/editDischargeInpt")
    WrapperResponse<Boolean> editDischargeInpt(Map<String, Object> map);

    /**
     * @Method editCancelDischargeInpt
     * @Desrciption  医保出院办理撤销
     * @Param params
     *
     * @Author fuhui
     * @Date   2021/5/28 11:40
     * @Return
     **/
    @PostMapping("/service/inpt/inptSettlement/editCancelDischargeInpt")
    WrapperResponse<Boolean> editCancelDischargeInpt(Map<String, Object> map);

    /**
     * @Menthod saveBabyCostTrial
     * @Desrciption 住院试算(婴儿)
     * @param param 请求参数
     * @Author liuliyun
     * @Date 2021/5/17 15:09
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @PostMapping("/service/inpt/inptSettlement/saveBabyCostTrial")
    WrapperResponse saveBabyCostTrial(Map param);

    /**
     * @Menthod saveBabySettle
     * @Desrciption 住院结算(婴儿)
     * @param param 请求参数
     * @Author liuliyun
     * @Date 2021/5/17 15:37
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @PostMapping("/service/inpt/inptSettlement/saveBabySettle")
    WrapperResponse saveBabySettle(Map param);
}
