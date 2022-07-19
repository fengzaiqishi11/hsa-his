package cn.hsa.insure.unifiedpay.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.inpt.bo.InsureUnifiedBaseBO;
import cn.hsa.module.insure.inpt.service.InsureUnifiedBaseService;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.service.impl
 * @class_name: InsureUnifiedBaseServiceImpl
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/4/24 21:22
 * @Company: 创智和宇
 **/
@Slf4j
@HsafRestPath("/service/insure/insureUnifiedBase")
@Service("insureUnifiedBaseService_provider")
public class InsureUnifiedBaseServiceImpl extends HsafService implements InsureUnifiedBaseService {

    @Resource
    private InsureUnifiedBaseBO insureUnifiedBaseBO;

    /**
     * @Method queryUnifiedDept
     * @Desrciption 科室信息查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/23 12:47
     * @Return
     **/
    @Override
    public WrapperResponse<Map<String, Object>> queryUnifiedDept(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedBaseBO.queryUnifiedDept(map));
    }

    /**
     * @param map
     * @Method queryDoctorInfo
     * @Desrciption 医执人员信息查询
     * @Param
     * @Author fuhui
     * @Date 2021/4/23 12:47
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> queryDoctorInfo(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedBaseBO.queryDoctorInfo(map));
    }

    /**
     * @param map
     * @Method queryVisitInfo
     * @Desrciption 就诊信息查询
     * @Param
     * @Author fuhui
     * @Date 2021/4/23 12:47
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> queryVisitInfo(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedBaseBO.queryVisitInfo(map));
    }

    /**
     * @Method queryDiagnoseInfo
     * @Desrciption 诊断信息查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/23 12:47
     * @Return
     **/
    @Override
    public WrapperResponse<Map<String, Object>> queryDiagnoseInfo(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedBaseBO.queryDiagnoseInfo(map));
    }

    /**
     * @Method updateFormalData
     * @Desrciption 门诊选点改点
     * @Param
     *
     * @Author caoliang
     * @Date   2021/6/30 15:47
     * @Return
     **/
    @Override
    public WrapperResponse<Map<String, Object>> updateFormalData(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedBaseBO.updateFormalData(map));
    }


    /**
     * @param map
     * @Method querySettleInfo
     * @Desrciption 结算信息查询
     * @Param
     * @Author fuhui
     * @Date 2021/4/23 12:47
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> updateSettleInfo(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedBaseBO.updateSettleInfo(map));
    }

    /**
     * @param map
     * @Method queryFeeDetailInfo
     * @Desrciption 费用明细查询
     * @Param
     * @Author fuhui
     * @Date 2021/4/23 12:47
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> queryFeeDetailInfo(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedBaseBO.queryFeeDetailInfo(map));
    }

    /**
     * @param map
     * @Method querySpecialUserDrug
     * @Desrciption 人员慢特病用药记录查询
     * @Param
     * @Author fuhui
     * @Date 2021/4/23 12:47
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> querySpecialUserDrug(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedBaseBO.querySpecialUserDrug(map));
    }

    /**
     * @param map
     * @Method queryPatientSumInfo
     * @Desrciption 人员累计信息查询
     * @Param
     * @Author fuhui
     * @Date 2021/4/23 12:47
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> queryPatientSumInfo(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedBaseBO.queryPatientSumInfo(map));
    }

    /**
     * @param map
     * @Method queryItemConfirm
     * @Desrciption 项目互认信息查询
     * @Param
     * @Author fuhui
     * @Date 2021/4/23 12:47
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> queryItemConfirm(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedBaseBO.queryItemConfirm(map));
    }

    /**
     * @param map
     * @Method queryPage
     * @Desrciption 分页查询医保就诊信息
     * @Param
     * @Author fuhui
     * @Date 2021/4/26 9:20
     * @Return
     */
    @Override
    public WrapperResponse<PageDTO> queryPage(Map<String, Object> map) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map,"insureIndividualVisitDTO");
        return WrapperResponse.success(insureUnifiedBaseBO.queryPage(insureIndividualVisitDTO));
    }

    /**
     * @Method queryPatientInfo
     * @Desrciption 在院信息查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/23 12:47
     * @Return
     **/
    @Override
    public WrapperResponse<PageDTO> queryPatientInfo(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedBaseBO.queryPatientInfo(map));
    }

    /**
     * @Description: 人员定点信息查询
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
     * @Author: zhangxuan
     * @Date: 2021-04-28
     */
    @Override
    public WrapperResponse<Map<String, Object>> queryPersonFixInfo(Map<String, Object> map){
        return WrapperResponse.success(insureUnifiedBaseBO.queryPersonFixInfo(map));
    }

    /**
     * @Description: 报告明细信息查询
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
     * @Author: zhangxuan
     * @Date: 2021-04-28
     */
    @Override
    public WrapperResponse<Map<String, Object>> queryReportDetails(Map<String, Object> map){
        return WrapperResponse.success(insureUnifiedBaseBO.queryReportDetails(map));
    }

    /**
     * @param map
     * @Method queryTransfInfo
     * @Desrciption 转院信息查询
     * @Param
     * @Author fuhui
     * @Date 2021/4/23 12:47
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> queryTransfInfo(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedBaseBO.queryTransfInfo(map));
    }

    /**
     * @param map
     * @Method queryFixRecordInfo
     * @Desrciption 人员定点备案信息查询
     * @Param
     * @Author fuhui
     * @Date 2021/4/23 12:47
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> queryFixRecordInfo(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedBaseBO.queryFixRecordInfo(map));
    }

    /**
     * @param map
     * @Method queryFixRecordInfo
     * @Desrciption 人员慢特病备案信息查询
     * @Param
     * @Author fuhui
     * @Date 2021/4/23 12:47
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> querySpecialRecord(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedBaseBO.querySpecialRecord(map));
    }

    /**
     * @param map
     * @Method queryMzSpecialLimitPrice
     * @Desrciption 门诊特病限额使用情况查询
     * @Param
     * @Author fuhui
     * @Date 2021/6/8 14:09
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> queryMzSpecialLimitPrice(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedBaseBO.queryMzSpecialLimitPrice(map));
    }

    /**
     * @param map
     * @Method updateUnifiedDeptInfo
     * @Desrciption
     * @Param
     * @Author fuhui
     * @Date 2021/6/10 15:00
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> updateUnifiedDeptInfo(Map<String, Object> map) {
        return  WrapperResponse.success(insureUnifiedBaseBO.updateUnifiedDeptInfo(map));
    }

    /**
     * @param map
     * @Method updateUnifiedDept
     * @Desrciption 科室信息上传
     * @Param
     * @Author fuhui
     * @Date 2021/6/10 14:45
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> updateUnifiedDept(Map<String, Object> map) {
        return  WrapperResponse.success(insureUnifiedBaseBO.updateUnifiedDept(map));
    }

    /**
     * @param map
     * @Method deleteUnifiedDeptInfo
     * @Desrciption 撤销科室信息。
     * @Param
     * @Author fuhui
     * @Date 2021/6/10 15:02
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> deleteUnifiedDeptInfo(Map<String, Object> map) {
        return  WrapperResponse.success(insureUnifiedBaseBO.deleteUnifiedDeptInfo(map));
    }

    /**
     * @param map
     * @Method queryInform
     * @Desrciption 告知单查询
     * @Param
     * @Author fuhui
     * @Date 2021/9/6 15:35
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> queryInform(Map<String, Object> map) {
        return  WrapperResponse.success(insureUnifiedBaseBO.queryInform(map));
    }

    /**
     * @param map
     * @Method updateInsureInptRegisterStatus
     * @Desrciption 更新医保登记状态（医保已经登记 而his没有登记）
     * @Param
     * @Author fuhui
     * @Date 2021/10/8 8:33
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> updateInsureInptRegisterStatus(Map<String, Object> map) {
        return  WrapperResponse.success(insureUnifiedBaseBO.updateInsureInptRegisterStatus(map));
    }

    /**
     * @param map
     * @Method updateInsureInptSettleStatus
     * @Desrciption 更新医保结算状态（医保已经结算 而his没有结算）
     * @Param
     * @Author fuhui
     * @Date 2021/10/8 10:07
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> updateInsureInptSettleStatus(Map<String, Object> map) {
        return  WrapperResponse.success(insureUnifiedBaseBO.updateInsureInptSettleStatus(map));
    }

    /**
     * @Method updateInsureInptCancelSettleStatus
     * @Desrciption  同步取消结算状态  his和医保
     * @Param
     *
     * @Author fuhui
     * @Date   2021/10/8 10:21
     * @Return
     **/
    @Override
    public WrapperResponse<Boolean> updateInsureInptCancelSettleStatus(Map<String, Object> map) {
        return  WrapperResponse.success(insureUnifiedBaseBO.updateInsureInptCancelSettleStatus(map));
    }

    /**
     * @param map
     * @Method updateInptPatientCode
     * @Desrciption 修改病人类型
     * @Param
     * @Author fuhui
     * @Date 2021/10/8 10:21
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> updateInptPatientCode(Map<String, Object> map) {
        return  WrapperResponse.success(insureUnifiedBaseBO.updateInptPatientCode(map));
    }

    /**
     * @param map
     * @Method 查询住院医保病人类型
     * @Desrciption
     * @Param
     * @Author fuhui
     * @Date 2021/10/9 9:42
     * @Return
     */
    @Override
    public WrapperResponse<PageDTO> queryInptInsurePatient(Map<String,Object>map) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map,"insureIndividualVisitDTO");
        return  WrapperResponse.success(insureUnifiedBaseBO.queryInptInsurePatient(insureIndividualVisitDTO));
    }

    @Override
    public WrapperResponse<Map<String,Object>> querySettleDeInfo(Map<String, Object> map) {
        return  WrapperResponse.success(insureUnifiedBaseBO.querySettleDeInfo(map));
    }


    @Override
    public WrapperResponse<Map<String,Object>> querySettleDeInfoBySettleId(Map<String, Object> map) {
        return  WrapperResponse.success(insureUnifiedBaseBO.querySettleDeInfoBySettleId(map));
    }

    /**
     * @Method queryPolicyInfo
     * @Desrciption  政策项查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/10/23 15:47
     * @Return
     **/
    @Override
    public WrapperResponse<Map<String, Object>> queryPolicyInfo(Map<String, Object> map) {
        return  WrapperResponse.success(insureUnifiedBaseBO.queryPolicyInfo(map));
    }

    /**
     * @param map
     * @Method checkOneSettle
     * @Desrciption 判读是否打印一站式结算单
     * @Param map insureIndividualVisitDTO
     * @Author fuhui
     * @Date 2021/10/23 15:20
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> checkOneSettle(Map<String, Object> map) {
        return  WrapperResponse.success(insureUnifiedBaseBO.checkOneSettle(map));
    }

    /**
     * @Method queryBalanceCount
     * @Desrciption  6.3.1.3个人账户扣减
     * @Param
     *
     * @Author fuhui
     * @Date   2022/3/15 15:33
     * @Return
     **/
    @Override
    public WrapperResponse<Map<String, Object>> updateBalanceCountDecrease(Map<String, Object> map) {
        return  WrapperResponse.success(insureUnifiedBaseBO.updateBalanceCountDecrease(map));
    }

    /**
     * @param map
     * @Method queryBalanceCount
     * @Desrciption 6.3.1.3账户余额信息查询
     * @Param
     * @Author fuhui
     * @Date 2022/3/15 15:33
     * @Return
     */
    @Override
    public WrapperResponse<Map<String, Object>> queryBalanceCount(Map<String, Object> map) {
        return  WrapperResponse.success(insureUnifiedBaseBO.queryBalanceCount(map));
    }

}
