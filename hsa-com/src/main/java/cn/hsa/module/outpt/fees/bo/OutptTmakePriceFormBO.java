package cn.hsa.module.outpt.fees.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.base.deptDrug.dto.BaseDeptDrugStoreDTO;
import cn.hsa.module.dzpz.hainan.SeltSucCallbackDTO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleInvoiceContentDTO;
import cn.hsa.module.outpt.fees.dto.OutptSettleInvoiceDTO;
import cn.hsa.module.outpt.fees.entity.OutptPayDO;
import cn.hsa.module.outpt.outinInvoice.dto.OutinInvoiceDTO;
import cn.hsa.module.outpt.outinInvoice.entity.OutinInvoiceDO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;

import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.module.outpt.fees.bo
 * @Class_name: OutptTmakePriceFormBO
 * @Describe(描述):门诊划价收费BO层
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/08/25 10:37
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
public interface OutptTmakePriceFormBO {

    /**
     * @param outptVisitDTO 请求参数
     * @Menthod queryOutptVisitPage
     * @Desrciption 查询门诊就诊患者信息
     * @Author Ou·Mr
     * @Date 2020/8/25 10:46
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    WrapperResponse queryOutptVisitPage(OutptVisitDTO outptVisitDTO);

    /**
     * @param outinInvoiceDTO 请求参数
     * @Menthod queryOutinInvoice
     * @Desrciption 获取当前发票号
     * @Author Ou·Mr
     * @Date 2020/8/25 10:47
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    WrapperResponse queryOutinInvoice(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @param param 请求参数
     * @Menthod queryOutptCostList
     * @Desrciption 根据处方id查询门诊费用
     * @Author Ou·Mr
     * @Date 2020/8/25 10:48
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    WrapperResponse queryOutptCostList(Map param);

    /**
     * @Description: 查询患者费用，用于体检
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/11/22 20:23
     * @Return
     */
    WrapperResponse queryOutptCostListTJ(Map param);

    /**
     * @param param 请求参数
     * @Menthod queryBaseByPage
     * @Desrciption 获取医院 项目、材料、药品 信息
     * @Author Ou·Mr
     * @Date 2020/8/25 10:54
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    WrapperResponse queryBaseByPage(Map param);

    /**
     * @param outptVisitDTO 请求参数
     * @Menthod saveOutptCostAndVisit
     * @Desrciption 暂存门诊划价收费 患者信息、费用信息。
     * @Author Ou·Mr
     * @Date 2020/8/25 10:56
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    WrapperResponse saveOutptCostAndVisit(OutptVisitDTO outptVisitDTO);

    /**
     * @param outptVisitDTO 请求参数
     * @Menthod saveOutptSettleMoney
     * @Desrciption 门诊划价收费试算、计算支付金额
     * @Author Ou·Mr
     * @Date 2020/8/25 10:58
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    WrapperResponse saveOutptSettleMoney(OutptVisitDTO outptVisitDTO);

    /**
     * @param outptSettleDTO 请求参数
     * @Menthod saveOutptSettle
     * @Desrciption 门诊划价收费支付结算
     * @Author Ou·Mr
     * @Date 2020/8/25 11:00
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    WrapperResponse saveOutptSettle(OutptVisitDTO outptVisitDTO, OutptSettleDTO outptSettleDTO, List<OutptPayDO> outptPayDOList);

    /**
     * @param param 查询条件
     * @Menthod getBasePreferentialType
     * @Desrciption 获取医院优惠列表
     * @Author Ou·Mr
     * @Date 2020/8/27 10:10
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    WrapperResponse getBasePreferentialType(Map param);

    /**
     * @param outptVisitDTO 患者费用参数
     * @Menthod updateVerifyCoupon
     * @Desrciption 计算优惠金额
     * @Author Ou·Mr
     * @Date 2020/8/27 13:49
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    WrapperResponse updateVerifyCoupon(OutptVisitDTO outptVisitDTO);

    /**
     * @param outptCostDTOS 患者费用参数
     * @Menthod updateList
     * @Desrciption 批量修改费用信息
     * @Author xingyu.xie
     * @Date 2020/8/29 10:54
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     **/
    WrapperResponse updateList(List<OutptCostDTO> outptCostDTOS);

    /**
     * @param outinInvoiceDO 请求参数
     * @Menthod editOutinInvoice
     * @Desrciption 修改使用的发票状态
     * @Author Ou·Mr
     * @Date 2020/8/31 17:33
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    WrapperResponse editOutinInvoiceIsStatusCode(OutinInvoiceDO outinInvoiceDO);

    /**
     * @param outinInvoiceDTO 发票信息参数
     * @Menthod editOutinInvoice
     * @Desrciption 修改发票信息
     * @Author Ou·Mr
     * @Date 2020/8/31 19:17
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    WrapperResponse editOutinInvoice(OutinInvoiceDTO outinInvoiceDTO);

    /**
     * @Menthod: queryCharge()
     * @Desrciption: 收费查询
     * @Param: OutptSettleDTO--门诊结算DTO
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/9/2 14:19
     * @Return: cn.hsa.sys.PageDTO
     **/
    PageDTO queryCharge(OutptSettleDTO outptSettleDTO);

    /**
     * @param baseDeptDrugStoreDTO 查询条件
     * @Menthod queryBaseDept
     * @Desrciption 获取选择科室指定的药房科室id
     * @Author Ou·Mr
     * @Date 2020/9/18 15:31
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    WrapperResponse queryBaseDept(BaseDeptDrugStoreDTO baseDeptDrugStoreDTO);

    /**
     * @param outptCostDTO 查询条件
     * @Menthod queryIsSettleCost
     * @Desrciption 获取费用信息
     * @Author Ou·Mr
     * @Date 2020/10/11 15:15
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    WrapperResponse queryIsSettleCost(OutptCostDTO outptCostDTO);

    /**
     * @param outptVisitDTO 查询条件
     * @Menthod queryOutptSettleVisitPage
     * @Desrciption 查询已结算患者信息
     * @Author Ou·Mr
     * @Date 2020/12/7 22:34
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    WrapperResponse queryOutptSettleVisitPage(OutptVisitDTO outptVisitDTO);

    /**
     * @Description: 根据页面设置的自定义优惠类型计算优惠价格
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/1/26 9:19
     * @Return
     */
    OutptVisitDTO customMoney(Map<String, Object> map);

    /**
     * @Description: 保存自定义优惠价格
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/1/26 14:59
     * @Return
     */
    boolean saveCustomMoney(Map<String, Object> map);

    /**
     * @Method selectCheckInfo
     * @Desrciption 读取审批信息
     * @Param map
     * @Author fuhui
     * @Date 2021/2/1 19:31
     * @Return map
     **/
    Map<String, Object> selectCheckInfo(Map<String, Object> map);

    /**
     * @Desrciption uploadCheckInfo 医院审批信息上报
     * @Param map
     * @Author fuhui
     * @Date 2021/2/1 19:28
     * @Return boolean
     **/
    Boolean uploadCheckInfo(Map<String, Object> map);

    /**
     * @param map
     * @Desrciption uploadCheckInfo 门特病人取消结算以后 取消登记
     * @Param map
     * @Author fuhui
     * @Date 2021/2/1 19:28
     * @Return boolean
     */
    Boolean cancelRegister(Map<String, Object> map);

    Boolean saveOutptInsureVisit(OutptVisitDTO outptVisitDTO);

    WrapperResponse saveOutptSettleMoneyDz(OutptVisitDTO outptVisitDTO);

    boolean saveRevokeOrder(OutptVisitDTO outptVisitDTO);

    boolean saveSeltSucCallback(Map map);

    /**
     * @Description: 根据费用id、优惠类别、默认数量为1来计算费用优惠金额(直接划价收费时，添加一条费用项目，立马计算当前项目优惠金额)
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/2/4 14:04
     * @Return
     */
    OutptCostDTO getOutptCostDTOForPreferential(Map<String, Object> params);

    /**
     * @param jzid
     * @param jsid
     * @param yybm
     * @Description 处理发票分单
     * @author: zibo.yuan
     * @date: 2021/3/8
     * @return: java.util.Map<java.lang.String, java.lang.Object>
     **/
    Map<String, Object> disposeFpfd(String jzid, String jsid, String yybm);


    /***
     * @Description 门诊结算BO
     * @param map
     * @author: zibo.yuan
     * @date: 2021/3/9
     * @return: void
     **/
    WrapperResponse saveOutptSettleInvoice(Map<String, Object> map);

    void saveMzfpDy(OutinInvoiceDTO outinInvoiceDTO, List<OutptSettleInvoiceDTO> pjList, List<OutptSettleInvoiceContentDTO> pjnrList);
    /**
     * @param map
     * @Method updateCancelFeeSubmit
     * @Desrciption 门诊医保病人取消费用上传
     * @Param
     * @Author fuhui
     * @Date 2021/3/3 16:44
     * @Return
     */
    Boolean updateCancelFeeSubmit(Map<String, Object> map);

    /**
     * @param
     * @Method updateFeeSubmit()
     * @Desrciption 门诊医保病人费用传输
     * @Param
     * @Author fuhui
     * @Date 2021/3/3 16:38
     * @Return
     */
    Map<String,Object> updateFeeSubmit(Map<String, Object> map);

    /**
     * @param outptVisitDTO
     * @Method checkSettleMoney
     * @Desrciption 门诊预结算之前，需要判断his产生的费用，是否和上传到医保的费用相等。
     * @Param params
     * @Author fuhui
     * @Date 2021/3/12 11:25
     * @Return Map<String, Object>
     */
    Map<String,Object> checkSettleMoney(OutptVisitDTO outptVisitDTO);

    /**
     * @param map
     * @Method updateVisitTime
     * @Desrciption 更新患者的就诊时间
     * @Param
     * @Author fuhui
     * @Date 2021/6/24 0:47
     * @ReturnA
     */
    Boolean updateVisitTime(Map<String, Object> map);

    /**
     * @Description: lis数据下发
     * @Param: [map]
     * @return: java.lang.Boolean
     * @Author: zhangxuan
     * @Date: 2021-06-29
     */
    Boolean lisData(Map map);

    /**
     * @Method queryPatientPrescribeNoSettle
     * @Desrciption  查询病人已提交未结算的处方单号
     * @Param  outptPrescribeDO
     * @Author liuliyun
     * @Date  2021/09/03 10:45
     * @Return PageDTO
     **/
    PageDTO queryPatientPrescribeNoSettle(Map map);

    /**
     * @Method queryOutptPrescribeCostList
     * @Desrciption  根据处方id查询处方费用list
     * @Param  java.util.Map
     * @Author liuliyun
     * @Date  2021/09/03 10:58
     * @Return OutptCostDTO
     **/
    List<OutptCostDTO> queryOutptPrescribeCostList(Map map);


    PageDTO queryCreditCharge(OutptSettleDTO outptSettleDTO);

    /**
     * @Menthod: updateCreditStatus()
     * @Desrciption: 更新补缴状态
     * @Param: OutptSettleDTO--门诊结算DTO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/3/1 11:15
     * @Return: Boolean
     **/
    Boolean updateCreditStatus(OutptSettleDTO outptSettleDTO);

    /**
     * @Menthod: saveCreditInvoicePrint()
     * @Desrciption: 挂账发票打印
     * @Param: OutinInvoiceDTO--门诊结算DTO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/3/2 10：39
     * @Return: Boolean
     **/
    Boolean updateCreditQueryInovicePrint(OutinInvoiceDTO OutinInvoiceDTO);

    /**
     * 【6201】费用明细上传
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-04-25 16:03
     * @return java.lang.Boolean
     */
    Boolean uploadOnlineFeeDetail(Map map);

    /**
     * 医保订单结算结果查询
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-05-09 15:15
     * @return cn.hsa.module.dzpz.hainan.SeltSucCallbackDTO
     */
    WrapperResponse queryInsureSetlResult(Map map);

    /**
     *【6401】费用明细上传撤销
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-05-10 13:51
     * @return java.lang.Boolean
     */
    Boolean insureFeeRevoke(Map map);

    /**
     * 6203-医保退费
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-05-16 15:59
     * @return java.lang.Boolean
     */
    Boolean insureRefund(Map map);

    /**
     * 线上医保移动支付完成的结算订单，可通过此接口进行退款
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-06-15 9:10
     * @return java.lang.Boolean
     */
    Boolean ampRefund(Map map);
}
