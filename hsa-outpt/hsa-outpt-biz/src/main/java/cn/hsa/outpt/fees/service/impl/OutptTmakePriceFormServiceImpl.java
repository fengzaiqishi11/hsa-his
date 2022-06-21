package cn.hsa.outpt.fees.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.dzpz.hainan.SeltSucCallbackDTO;
import cn.hsa.module.outpt.fees.bo.OutptTmakePriceFormBO;
import cn.hsa.module.outpt.fees.dto.OutptCostDTO;
import cn.hsa.module.outpt.fees.service.OutptTmakePriceFormService;
import cn.hsa.module.outpt.outinInvoice.dto.OutinInvoiceDTO;
import cn.hsa.module.outpt.visit.dto.OutptVisitDTO;
import cn.hsa.util.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Package_name: cn.hsa.outpt.fees.service.impl
 * @Class_name: OutptTmakePriceFormServiceImpl
 * @Describe(描述):门诊划价收费ServiceImpl
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/08/25 10:32
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/outpt/tmakePriceForm")
@Service("outptTmakePriceFormService_provider")
public class OutptTmakePriceFormServiceImpl extends HsafService implements OutptTmakePriceFormService {

    @Resource
    private OutptTmakePriceFormBO outptTmakePriceFormBO;

    /**
     * @Menthod queryOutptVisitPage
     * @Desrciption 查询门诊就诊患者信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/25 10:46
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    @Override
    public WrapperResponse queryOutptVisitPage(Map param) {
        OutptVisitDTO outptVisitDTO = MapUtils.get(param,"outptVisitDTO");
        return outptTmakePriceFormBO.queryOutptVisitPage(outptVisitDTO);
    }

    /**
     * @Menthod queryOutinInvoice
     * @Desrciption 获取当前发票号
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/25 10:47
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    @Override
    public WrapperResponse queryOutinInvoice(Map param) {
        OutinInvoiceDTO outinInvoiceDTO = MapUtils.get(param,"outinInvoiceDTO");
        return outptTmakePriceFormBO.queryOutinInvoice(outinInvoiceDTO);
    }

    /**
     * @Menthod queryOutptCostList
     * @Desrciption 根据处方id查询门诊费用
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/25 10:48
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    @Override
    public WrapperResponse queryOutptCostList(Map param) {
        return outptTmakePriceFormBO.queryOutptCostList(param);
    }

    /**
     * @Description: 查询患者费用，用于体检
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/11/22 20:22
     * @Return
     */
    @Override
    public WrapperResponse queryOutptCostListTJ(Map param) {
        return outptTmakePriceFormBO.queryOutptCostListTJ(param);
    }

    /**
     * @Menthod queryBaseByPage
     * @Desrciption  获取医院 项目、材料、药品 信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/25 10:54
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    @Override
    public WrapperResponse queryBaseByPage(Map param) {
        return outptTmakePriceFormBO.queryBaseByPage(param);
    }

    /**
     * @Menthod saveOutptCostAndVisit
     * @Desrciption  暂存门诊划价收费 患者信息、费用信息。
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/25 10:56
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    @Override
    public WrapperResponse saveOutptCostAndVisit(Map param) {
        OutptVisitDTO outptVisitDTO = MapUtils.get(param,"outptVisitDTO");
        return outptTmakePriceFormBO.saveOutptCostAndVisit(outptVisitDTO);
    }

    /**
     * @Menthod saveOutptSettleMoney
     * @Desrciption 门诊划价收费试算、计算支付金额
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/25 10:58
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    @Override
    public WrapperResponse saveOutptSettleMoney(Map param) {
        return outptTmakePriceFormBO.saveOutptSettleMoney(MapUtils.get(param,"outptVisitDTO"));
    }

    /**
     * @Menthod saveOutptSettle
     * @Desrciption  门诊划价收费支付结算
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/25 11:00
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse 请求结果
     */
    @Override
    public WrapperResponse saveOutptSettle(Map param) {
        WrapperResponse wrapperResponse = outptTmakePriceFormBO.saveOutptSettleInvoice(param);
        // 处理发票信息
        return wrapperResponse;
    }

    /**
     * @Menthod getBasePreferentialType
     * @Desrciption  获取医院优惠列表
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/8/27 10:10
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse getBasePreferentialType(Map param) {
        return outptTmakePriceFormBO.getBasePreferentialType(param);
    }

    /**
     * @Menthod verifyCoupon
     * @Desrciption 计算优惠金额
     * @param param 患者费用参数
     * @Author Ou·Mr
     * @Date 2020/8/27 11:55
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse updateVerifyCoupon(Map param) {
        return outptTmakePriceFormBO.updateVerifyCoupon(MapUtils.get(param,"outptVisitDTO"));
    }

    /**
     * @Menthod updateList
     * @Desrciption  批量修改费用信息
     * @param map 患者费用参数
     * @Author xingyu.xie
     * @Date   2020/8/29 10:56
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     **/
    public WrapperResponse updateList(Map map){
        return outptTmakePriceFormBO.updateList(MapUtils.get(map,"outptCostDTOList"));
    }

    /**
     * @Menthod editOutinInvoice
     * @Desrciption 修改使用的发票状态
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/8/31 17:33
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse editOutinInvoiceIsStatusCode(Map param) {
        return outptTmakePriceFormBO.editOutinInvoiceIsStatusCode(MapUtils.get(param,"outinInvoiceDO"));
    }

    @Override
    public WrapperResponse editOutinInvoice(Map param) {
        return outptTmakePriceFormBO.editOutinInvoice(MapUtils. get(param,"outinInvoiceDTO"));
    }

    /**
     * @Menthod: queryCharge()
     * @Desrciption: 收费查询
     * @Param: map
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2020/9/2 14:19
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryCharge(Map paramMap) {
        PageDTO pageDTO = outptTmakePriceFormBO.queryCharge(MapUtils.get(paramMap,"outptSettleDTO"));
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Menthod queryBaseDept
     * @Desrciption  获取选择科室指定的药房科室id
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/9/18 15:26
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse queryBaseDept(Map param) {
        return outptTmakePriceFormBO.queryBaseDept(MapUtils.get(param,"baseDeptDrugStoreDTO"));
    }

    /**
     * @Menthod queryIsSettleCost
     * @Desrciption 获取费用信息
     * @param param 查询条件
     * @Author Ou·Mr
     * @Date 2020/10/11 15:15
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse queryIsSettleCost(Map param) {
        return outptTmakePriceFormBO.queryIsSettleCost(MapUtils.get(param,"outptCostDTO"));
    }

    /**
     * @Menthod queryOutptSettleVisitPage
     * @Desrciption 查询已结算患者信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/12/7 23:12
     * @Return cn.hsa.hsaf.core.framework.web.WrapperResponse
     */
    @Override
    public WrapperResponse queryOutptSettleVisitPage(Map param) {
        return outptTmakePriceFormBO.queryOutptSettleVisitPage(MapUtils.get(param,"outptVisitDTO"));
    }

    /**
     * @Description: 根据页面设置的自定义优惠类型计算优惠价格
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/1/26 9:17
     * @Return
     */
    @Override
    public WrapperResponse<OutptVisitDTO> customMoney(Map<String, Object> map) {
        return WrapperResponse.success(outptTmakePriceFormBO.customMoney(map));
    }

    /**
     * @Description: 保存自定义优惠
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/1/26 14:58
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> saveCustomMoney(Map<String, Object> map) {
        return WrapperResponse.success(outptTmakePriceFormBO.saveCustomMoney(map));
    }

    /**
     * @Method selectCheckInfo
     * @Desrciption  读取审批信息
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/2/1 19:31
     * @Return map
     **/
    @Override
    public WrapperResponse<Map<String, Object>> selectCheckInfo(Map<String, Object> map) {
        return WrapperResponse.success(outptTmakePriceFormBO.selectCheckInfo(map));
    }

    /**
     * @Desrciption  uploadCheckInfo 医院审批信息上报
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/2/1 19:28
     * @Return boolean
     **/
    @Override
    public WrapperResponse<Boolean> uploadCheckInfo(Map<String, Object> map) {
        return WrapperResponse.success(outptTmakePriceFormBO.uploadCheckInfo(map));
    }

    /**
     * @param map
     * @Desrciption cancelRegister 门特病人取消结算以后 取消登记
     * @Param map
     * @Author fuhui
     * @Date 2021/2/1 19:28
     * @Return boolean
     */
    @Override
    public WrapperResponse<Boolean> cancelRegister(Map<String, Object> map) {
        return WrapperResponse.success(outptTmakePriceFormBO.cancelRegister(map));
    }

    @Override
    public WrapperResponse saveOutptInsureVisit(Map param) {
        return WrapperResponse.success(outptTmakePriceFormBO.saveOutptInsureVisit(MapUtils.get(param,"outptVisitDTO")));
    }

    @Override
    public WrapperResponse saveOutptSettleMoneyDz(Map param) {
        return outptTmakePriceFormBO.saveOutptSettleMoneyDz(MapUtils.get(param,"outptVisitDTO"));
    }

    @Override
    public WrapperResponse<Boolean> revokeOrder(Map param) {
        return WrapperResponse.success(outptTmakePriceFormBO.saveRevokeOrder(MapUtils.get(param,"outptVisitDTO")));
    }

    @Override
    public WrapperResponse<Boolean> seltSucCallback(Map param) {
        return WrapperResponse.success(outptTmakePriceFormBO.saveSeltSucCallback(param));
    }

    /**
     * @Description: 根据费用id、优惠类别、默认数量为1来计算费用优惠金额(直接划价收费时，添加一条费用项目，立马计算当前项目优惠金额)
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/2/4 14:03
     * @Return
     */
    @Override
    public WrapperResponse<OutptCostDTO> getOutptCostDTOForPreferential(Map<String, Object> params) {
        return WrapperResponse.success(outptTmakePriceFormBO.getOutptCostDTOForPreferential(params));
    }

    /**
     * @param map
     * @Method updateCancelFeeSubmit
     * @Desrciption 门诊医保病人取消费用上传
     * @Param
     * @Author fuhui
     * @Date 2021/3/3 16:44
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> updateCancelFeeSubmit(Map<String, Object> map) {
        return WrapperResponse.success(outptTmakePriceFormBO.updateCancelFeeSubmit(map));
    }

    /**
     * @param map
     * @Method updateFeeSubmit()
     * @Desrciption 门诊医保病人费用传输
     * @Param
     * @Author fuhui
     * @Date 2021/3/3 16:38
     * @Return
     */
    @Override
    public WrapperResponse<Map<String,Object>> updateFeeSubmit(Map<String, Object> map) {
        return WrapperResponse.success(outptTmakePriceFormBO.updateFeeSubmit(map));
    }

    /**
     * @param map
     * @Method checkSettleMoney
     * @Desrciption 门诊预结算之前，需要判断his产生的费用，是否和上传到医保的费用相等。
     * @Param params
     * @Author fuhui
     * @Date 2021/3/12 11:25
     * @Return Map<String, Object>
     */
    @Override
    public WrapperResponse<Map<String, Object>> checkSettleMoney(Map<String, Object> map) {
        return WrapperResponse.success(outptTmakePriceFormBO.checkSettleMoney(MapUtils.get(map,"outptVisitDTO")));
    }

    /**
     * @Description: 不考虑发票分单，直接结算
     * @Param:
     * @Author: guanhongqiang
     * @Email: hongqiang.guan@powersi.com.cn
     * @Date 2021/5/11 19:42
     * @Return
     */
    @Override
    public WrapperResponse saveOutptSettleByTf(Map<String, Object> map) {

        return outptTmakePriceFormBO.saveOutptSettle(MapUtils.get(map, "outptVisitDTO"), MapUtils.get(map, "outptSettleDTO"), MapUtils.get(map, "outptPayDOList"));
    }

    /**
     * @param map
     * @Method updateVisitTime
     * @Desrciption 更新患者的就诊时间
     * @Param
     * @Author fuhui
     * @Date 2021/6/24 0:47
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> updateVisitTime(Map<String, Object> map) {
        return WrapperResponse.success(outptTmakePriceFormBO.updateVisitTime(map));
    }

    /**
     * @Description: lis数据下发
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     * @Author: zhangxuan
     * @Date: 2021-06-29
     */
    @Override
    public WrapperResponse<Boolean> lisData(Map map){
        return WrapperResponse.success(outptTmakePriceFormBO.lisData(map));
    }

    /**
     * @Method queryPatientPrescribeNoSettle
     * @Desrciption  查询病人已提交未结算的处方单号
     * @Param  outptPrescribeDO
     * @Author liuliyun
     * @Date  2021/09/03 10:45
     * @Return WrapperResponse<PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryPatientPrescribeNoSettle(Map map) {
        return WrapperResponse.success(outptTmakePriceFormBO.queryPatientPrescribeNoSettle(map));
    }

    /**
     * @Method queryOutptPrescribeCostList
     * @Desrciption  根据处方id查询处方费用
     * @Param  java.util.map
     * @Author liuliyun
     * @Date  2021/09/07 10:45
     * @Return WrapperResponse<List<OutptCostDTO>>
     **/
    @Override
    public WrapperResponse<List<OutptCostDTO>> queryOutptPrescribeCostList(Map map) {
        return WrapperResponse.success(outptTmakePriceFormBO.queryOutptPrescribeCostList(map));
    }

    /**
     * @Menthod: queryCreditCharge()
     * @Desrciption: 挂账查询
     * @Param: map
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/2/28 11:54
     * @Return: cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.sys.PageDTO>
     **/
    @Override
    public WrapperResponse<PageDTO> queryCreditCharge(Map paramMap) {
        PageDTO pageDTO = outptTmakePriceFormBO.queryCreditCharge(MapUtils.get(paramMap,"outptSettleDTO"));
        return WrapperResponse.success(pageDTO);
    }

    /**
     * @Menthod: updateCreditStatus()
     * @Desrciption: 更新补缴状态
     * @Param: OutptSettleDTO--门诊结算DTO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/3/1 11:38
     * @Return: WrapperResponse<Boolean>
     **/
    @Override
    public WrapperResponse<Boolean> updateCreditStatus(Map paramMap) {
        Boolean status = outptTmakePriceFormBO.updateCreditStatus(MapUtils.get(paramMap,"outptSettleDTO"));
        return WrapperResponse.success(status);
    }

    /**
     * @Menthod: saveCreditInvoicePrint()
     * @Desrciption: 挂账发票打印
     * @Param: OutinInvoiceDTO--门诊结算DTO
     * @Author: liuliyun
     * @Email: liyun.liu@powersi.com
     * @Date: 2022/3/2 10：39
     * @Return: Boolean
     **/
    @Override
    public WrapperResponse<Boolean> updateCreditQueryInovicePrint(Map paramMap) {
        Boolean status = outptTmakePriceFormBO.updateCreditQueryInovicePrint(MapUtils.get(paramMap,"outinInvoiceDTO"));
        return WrapperResponse.success(status);
    }

    /**
     * 【6201】费用明细上传
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-04-25 16:04
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     */
  @Override
  public WrapperResponse<Boolean> uploadOnlineFeeDetail(Map<String, Object> map) {
    return WrapperResponse.success(outptTmakePriceFormBO.uploadOnlineFeeDetail(map));
  }

  /**
   * 医保订单结算结果查询
   * @param map
   * @Author 医保开发二部-湛康
   * @Date 2022-05-09 15:15
   * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.dzpz.hainan.SeltSucCallbackDTO>
   */
  @Override
  public WrapperResponse queryInsureSetlResult(Map<String, Object> map) {
    return outptTmakePriceFormBO.queryInsureSetlResult(map);
  }

    /**
     * 【6401】费用明细上传撤销
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-05-10 13:50
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     */
    @Override
    public WrapperResponse<Boolean> insureFeeRevoke(Map<String, Object> map) {
      return WrapperResponse.success(outptTmakePriceFormBO.insureFeeRevoke(map));
    }

    /**
     * 6203-医保退费
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-05-16 15:58
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     */
    @Override
    public WrapperResponse<Boolean> insureRefund(Map<String, Object> map) {
      return WrapperResponse.success(outptTmakePriceFormBO.insureRefund(map));
    }

    /**
     * 线上医保移动支付完成的结算订单，可通过此接口进行退款
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-06-15 9:04
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.lang.Boolean>
     */
    @Override
    public WrapperResponse<Map<String, Object>> ampRefund(Map<String, Object> map) {
      return WrapperResponse.success(outptTmakePriceFormBO.ampRefund(map));
    }

    /**
     * 查询结算结果
     * @param map
     * @Author 医保开发二部-湛康
     * @Date 2022-06-16 14:38
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
     */
    @Override
    public WrapperResponse<Map<String, Object>> querySettleResult(Map<String, Object> map) {
      return WrapperResponse.success(outptTmakePriceFormBO.querySettleResult(map));
    }

    /**
      * @method AMP_HOS_001
      * @author wang'qiao
      * @date 2022/6/20 11:47
      *	@description 	医疗消息推送
      * @param  map
      * @return cn.hsa.hsaf.core.framework.web.WrapperResponse
      *
     **/
    @Override
    public WrapperResponse AMP_HOS_001(Map map){
        return outptTmakePriceFormBO.AMP_HOS_001(map);
    }

    /**
     * @param param
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map < java.lang.String, java.lang.Object>>
     * @method refundInquiry
     * @author wang'qiao
     * @date 2022/6/20 14:44
     * @description 查询退款结果（AMP_HOS_003）
     **/
    @Override
    public WrapperResponse<Map<String, Object>> refundInquiry(Map<String, Object> param) {
        return outptTmakePriceFormBO.refundInquiry(param);
    }

    /**
     * @param map
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map < java.lang.String, java.lang.Object>>
     * @method reconciliationDocument
     * @author wang'qiao
     * @date 2022/6/20 19:48
     * @description 对账文件获取  下载后定点医疗机构可自行解析此对账文件并与定点机构的对账文件和医保核心的对账文件进行三方账目的对账
     **/
    @Override
    public WrapperResponse<Map<String, Object>> reconciliationDocument(Map<String, Object> map) {
        return outptTmakePriceFormBO.reconciliationDocument(map);
    }
}
