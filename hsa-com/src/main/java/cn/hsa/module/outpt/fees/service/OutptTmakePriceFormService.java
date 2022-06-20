package cn.hsa.module.outpt.fees.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.dzpz.hainan.SeltSucCallbackDTO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.fees.dto.SetlResultQueryDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.fees.service
 * @Class_name: OutptTmakePriceFormService
 * @Describe(描述):划价收费Service接口
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/08/25 10:30
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@FeignClient(value = "hsa-outpt")
public interface OutptTmakePriceFormService {

    /**
     * @Menthod queryOutptVisitPage
     * @Desrciption 查询门诊就诊患者信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/25 10:46
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    @GetMapping(value = "/service/outpt/tmakePriceForm/queryOutptVisitPage")
    WrapperResponse queryOutptVisitPage(Map param);

    /**
     * @Menthod queryOutinInvoice
     * @Desrciption 获取当前发票号
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/25 10:47
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    @GetMapping(value = "/service/outpt/tmakePriceForm/queryOutinInvoice")
    WrapperResponse queryOutinInvoice(Map param);

    /**
     * @Menthod queryOutptCostList
     * @Desrciption 根据处方id查询门诊费用
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/25 10:48
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    @GetMapping(value = "/service/outpt/tmakePriceForm/queryOutptCostList")
    WrapperResponse queryOutptCostList(Map param);

    /**
     * @Description: 体检
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/11/22 20:21
     * @Return
     */
    @GetMapping(value = "/service/outpt/tmakePriceForm/queryOutptCostListTJ")
    WrapperResponse queryOutptCostListTJ(Map param);


    /**
     * @Menthod queryBaseByPage
     * @Desrciption  获取医院 项目、材料、药品 信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/25 10:54
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    @GetMapping(value = "/service/outpt/tmakePriceForm/queryBaseByPage")
    WrapperResponse queryBaseByPage(Map param);

    /**
     * @Menthod saveOutptCostAndVisit
     * @Desrciption  暂存门诊划价收费 患者信息、费用信息。
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/25 10:56
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    @PostMapping(value = "/service/outpt/tmakePriceForm/saveOutptCostAndVisit")
    WrapperResponse saveOutptCostAndVisit(Map param);

    /**
     * @Menthod saveOutptSettleMoney
     * @Desrciption 门诊划价收费试算、计算支付金额
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/25 10:58
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    @PostMapping(value = "/service/outpt/tmakePriceForm/saveOutptSettleMoney")
    WrapperResponse saveOutptSettleMoney(Map param);

    /**
     * @Menthod saveOutptSettle
     * @Desrciption  门诊划价收费支付结算
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/25 11:00
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    @PostMapping(value = "/service/outpt/tmakePriceForm/saveOutptSettle")
    WrapperResponse saveOutptSettle(Map param);


    /**
     * @Menthod getBasePreferentialType
     * @Desrciption  获取医院优惠列表
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/27 10:10
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping(value = "/service/outpt/tmakePriceForm/getBasePreferentialType")
    WrapperResponse getBasePreferentialType(Map param);

    /**
     * @Menthod verifyCoupon
     * @Desrciption 计算优惠金额
     * @param param 患者费用参数
     * @Author Ou·Mr
     * @Date 2020/8/27 11:55
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @PutMapping(value = "/service/outpt/tmakePriceForm/updateVerifyCoupon")
    WrapperResponse updateVerifyCoupon(Map param);

    /**
     * @Menthod updateList
     * @Desrciption  批量修改费用信息
     * @param param 患者费用参数
     * @Author xingyu.xie
     * @Date   2020/8/29 10:58
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     **/
    @PostMapping(value = "/service/outpt/tmakePriceForm/updateList" )
    WrapperResponse updateList(Map param);

    /**
     * @Menthod editOutinInvoice
     * @Desrciption 修改使用的发票状态
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/31 17:33
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @PutMapping(value = "/service/outpt/tmakePriceForm/editOutinInvoice")
    WrapperResponse editOutinInvoiceIsStatusCode(Map param);

    /**
     * @Menthod editOutinInvoice
     * @Desrciption 修改发票信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/31 19:17
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @PutMapping(value = "/service/outpt/tmakePriceForm/editOutinInvoice")
    WrapperResponse editOutinInvoice(Map param);

    /**
     * @Menthod: queryCharge()
     * @Desrciption: 收费查询
     * @Param: map
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/9/2 14:19
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    @GetMapping("/servcie/outpt/tmakePriceForm/queryCharge")
    WrapperResponse<PageDTO> queryCharge(Map paramMap);


    /**
     * @Menthod queryBaseDept
     * @Desrciption  获取选择科室指定的药房科室id
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/9/18 15:26
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping("/servcie/outpt/tmakePriceForm/queryBaseDept")
    WrapperResponse queryBaseDept(Map param);

    /**
     * @Menthod queryIsSettleCost
     * @Desrciption 获取费用信息
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/10/11 15:15
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping("/servcie/outpt/tmakePriceForm/queryIsSettleCost")
    WrapperResponse queryIsSettleCost(Map param);

    /**
     * @Menthod queryOutptSettleVisitPage
     * @Desrciption 查询已结算患者信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/7 23:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @GetMapping("/servcie/outpt/tmakePriceForm/queryOutptSettleVisitPage")
    WrapperResponse queryOutptSettleVisitPage(Map param);

    /**
     * @Description: 根据页面设置的自定义优惠类型计算优惠价格
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/1/26 9:16
     * @Return
     */
    @PostMapping("/servcie/outpt/tmakePriceForm/customMoney")
    WrapperResponse<OutptVisitDTO> customMoney(Map<String, Object> map);

    /**
     * @Description: 保存自定义优惠价格
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/1/26 14:55
     * @Return
     */
    @PostMapping("/servcie/outpt/tmakePriceForm/saveCustomMoney")
    WrapperResponse<Boolean> saveCustomMoney(Map<String, Object> map);

    @PostMapping(value = "/service/outpt/tmakePriceForm/saveOutptInsureVisit")
    WrapperResponse saveOutptInsureVisit(Map param);

    @PostMapping(value = "/service/outpt/tmakePriceForm/saveOutptSettleMoneyDz")
    WrapperResponse saveOutptSettleMoneyDz(Map param);

    @PostMapping(value = "/service/outpt/tmakePriceForm/revokeOrder")
    WrapperResponse revokeOrder(Map param);

    @PostMapping(value = "/service/outpt/tmakePriceForm/seltSucCallback")
    WrapperResponse seltSucCallback(Map param);

    /**
     * @Description: 根据费用id、优惠类别、默认数量为1来计算费用优惠金额(直接划价收费时，添加一条费用项目，立马计算当前项目优惠金额)
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/2/4 14:01
     * @Return
     */
    @PostMapping("/servcie/outpt/tmakePriceForm/getOutptCostDTOForPreferential")
    WrapperResponse<OutptCostDTO> getOutptCostDTOForPreferential(Map<String, Object> params);

    /**
     * @Method selectCheckInfo
     * @Desrciption  读取审批信息
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/2/1 19:31
     * @Return map
     **/
    @GetMapping("/servcie/outpt/tmakePriceForm/selectCheckInfo")
    WrapperResponse<Map<String, Object>> selectCheckInfo(Map<String, Object> map);

    /**
     * @Desrciption  uploadCheckInfo 医院审批信息上报
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/2/1 19:28
     * @Return boolean
     **/
    @GetMapping("/servcie/outpt/tmakePriceForm/selectCheckInfo")
    WrapperResponse<Boolean> uploadCheckInfo(Map<String, Object> map);

    /**
     * @Desrciption  cancelRegister 门特病人取消结算以后 取消登记
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/2/1 19:28
     * @Return boolean
     **/
    WrapperResponse<Boolean> cancelRegister(Map<String, Object> map);

    /**
     * @Method updateCancelFeeSubmit
     * @Desrciption  门诊医保病人取消费用上传
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/3 16:44
     * @Return
     **/
    WrapperResponse<Boolean>  updateCancelFeeSubmit(Map<String, Object> map);

    /**
     * @Method updateFeeSubmit()
     * @Desrciption  门诊医保病人费用传输
     * @Param
     *
     * @Author fuhui
     * @Date   2021/3/3 16:38
     * @Return
     **/
    WrapperResponse<Map<String,Object>> updateFeeSubmit(Map<String, Object> map);

    /**
     * @Method checkSettleMoney
     * @Desrciption  门诊预结算之前，需要判断his产生的费用，是否和上传到医保的费用相等。
     * @Param params
     *
     * @Author fuhui
     * @Date   2021/3/12 11:25
     * @Return Map<String,Object>
     **/
    WrapperResponse<Map<String,Object>> checkSettleMoney(Map<String, Object> map);

    WrapperResponse saveOutptSettleByTf(Map<String, Object> map);

    /**
     * @Method updateVisitTime
     * @Desrciption  更新患者的就诊时间
     * @Param
     *
     * @Author fuhui
     * @Date   2021/6/24 0:47
     * @Return
     **/
    WrapperResponse<Boolean> updateVisitTime(Map<String, Object> map);

    /**
     * @Description: lis数据下发
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangxuan
     * @Date: 2021-06-29
     */
    WrapperResponse<Boolean> lisData(Map map);

    /**
     * @Method queryPatientPrescribeNoSettle
     * @Desrciption  查询病人已提交未结算的处方单号
     * @Param  outptPrescribeDO
     * @Author liuliyun
     * @Date  2021/09/03
     * @Return PageDTO
     **/
    WrapperResponse<PageDTO> queryPatientPrescribeNoSettle(Map map);


    /**
     * @Method queryOutptPrescribeCostList
     * @Desrciption  查询处方费用
     * @Param  java.util.Map
     * @Author liuliyun
     * @Date  2021/09/03 10:51
     * @Return  WrapperResponse<List<OutptCostDTO>>
     **/
    WrapperResponse<List<OutptCostDTO>> queryOutptPrescribeCostList(Map map);

    /**
     * @Menthod: queryCreditCharge()
     * @Desrciption: 挂账查询
     * @Param: map
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/2/28 11:56
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    @GetMapping("/servcie/outpt/tmakePriceForm/queryCreditCharge")
    WrapperResponse<PageDTO> queryCreditCharge(Map paramMap);

    /**
     * @Menthod: updateCreditStatus()
     * @Desrciption: 更新补缴状态
     * @Param: OutptSettleDTO--门诊结算DTO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/3/1 11:15
     * @Return: Boolean
     **/
    @PostMapping("/servcie/outpt/tmakePriceForm/updateCreditStatus")
    WrapperResponse<Boolean> updateCreditStatus(Map paramMap);

    /**
     * @Menthod: saveCreditInvoicePrint()
     * @Desrciption: 挂账发票打印
     * @Param: OutinInvoiceDTO--门诊结算DTO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/3/2 10：39
     * @Return: Boolean
     **/
    @PostMapping("/servcie/outpt/tmakePriceForm/updateCreditQueryInovicePrint")
    WrapperResponse<Boolean> updateCreditQueryInovicePrint(Map paramMap);

    /**
     * 【6201】费用明细上传
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-04-25 15:32
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     */
    WrapperResponse<Boolean> uploadOnlineFeeDetail(Map<String, Object> map);

    /**
     * 医保订单结算结果查询
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-05-09 15:14
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.dzpz.hainan.SeltSucCallbackDTO>
     */
    WrapperResponse<Map<String, Object>> queryInsureSetlResult(Map<String, Object> map);

    /**
     * 【6401】费用明细上传撤销
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-05-10 13:48
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     */
    WrapperResponse<Boolean> insureFeeRevoke(Map<String, Object> map);

    /**
     * 6203-医保退费
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-05-16 15:57
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     */
    WrapperResponse<Boolean> insureRefund(Map<String, Object> map);

    /**
     * 线上医保移动支付完成的结算订单，可通过此接口进行退款
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-06-15 9:02
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     */
    WrapperResponse<Map<String, Object>> ampRefund(Map<String, Object> map);

    /**
     * 查询结算结果
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-06-16 14:36
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
     */
    WrapperResponse<Map<String, Object>> querySettleResult(Map<String, Object> map);

    /**
      * @method AMP_HOS_001
      * @author wang'qiao
      * @date 2022/6/20 11:43
      *	@description  消息推送
      * @param  map
      * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
      *
     **/
    public WrapperResponse AMP_HOS_001(Map map);
}
