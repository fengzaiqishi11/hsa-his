package cn.hsa.module.insure.inpt.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.doctor.dto.InptVisitDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

/**
 * @Package_name: cn.hsa.module.insure.inpt.service
 * @class_name: InsureUnifiedBaseService
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/4/24 14:54
 * @Company: 创智和宇
 **/
@FeignClient(value = "hsa-insure")
public interface InsureUnifiedBaseService {

    /**
     * @Method queryUnifiedDept
     * @Desrciption 科室信息查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/23 12:47
     * @Return
     **/
    WrapperResponse<Map<String, Object>> queryUnifiedDept(Map<String, Object> map);

    /**
     * @Method queryDoctorInfo
     * @Desrciption 医执人员信息查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/23 12:47
     * @Return
     **/
    WrapperResponse<Map<String, Object>> queryDoctorInfo(Map<String, Object> map);

    /**
     * @Method queryDoctorInfo
     * @Desrciption 就诊信息查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/23 12:47
     * @Return
     **/
    WrapperResponse<Map<String, Object>> queryVisitInfo(Map<String, Object> map);

    /**
     * @Method queryDiagnoseInfo
     * @Desrciption 诊断信息查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/23 12:47
     * @Return
     **/
    WrapperResponse<Map<String, Object>> queryDiagnoseInfo(Map<String, Object> map);

    /**
     * @Method updateFormalData
     * @Desrciption 门诊选点改点
     * @Param
     *
     * @Author caoliang
     * @Date   2021/6/30 15:47
     * @Return
     **/
    WrapperResponse<Map<String, Object>> updateFormalData(Map<String, Object> map);

    /**
     * @Method querySettleInfo
     * @Desrciption 结算信息查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/23 12:47
     * @Return
     **/
    WrapperResponse<Map<String, Object>> updateSettleInfo(Map<String, Object> map);

    /**
     * @Method queryFeeDetailInfo
     * @Desrciption 费用明细查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/23 12:47
     * @Return
     **/
    WrapperResponse<Map<String, Object>> queryFeeDetailInfo(Map<String, Object> map);

    /**
     * @Method querySpecialUserDrug
     * @Desrciption 人员慢特病用药记录查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/23 12:47
     * @Return
     **/
    WrapperResponse<Map<String, Object>> querySpecialUserDrug(Map<String, Object> map);

    /**
     * @Method queryPatientSumInfo
     * @Desrciption 人员累计信息查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/23 12:47
     * @Return
     **/
    WrapperResponse<Map<String, Object>> queryPatientSumInfo(Map<String, Object> map);

    /**
     * @Method queryItemConfirm
     * @Desrciption 项目互认信息查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/23 12:47
     * @Return
     **/
    WrapperResponse<Map<String, Object>> queryItemConfirm(Map<String, Object> map);

    /**
     * @Method queryPage
     * @Desrciption  分页查询医保就诊信息
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/26 9:20
     * @Return
     **/
    WrapperResponse<PageDTO> queryPage(Map<String, Object> map);

    /**
     * @Method queryPatientInfo
     * @Desrciption 在院信息查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/23 12:47
     * @Return
     **/
    WrapperResponse<PageDTO> queryPatientInfo(Map<String, Object> map);

    /**
     * @Description: 人员定点信息查询
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
     * @Author: zhangxuan
     * @Date: 2021-04-28
     */
    WrapperResponse<Map<String, Object>> queryPersonFixInfo(Map<String, Object> map);

    /**
     * @Description: 报告明细信息查询
     * @Param: [map]
     * @return: cn.hsa.hsaf.core.framework.web.WrapperResponse<java.util.Map<java.lang.String,java.lang.Object>>
     * @Author: zhangxuan
     * @Date: 2021-04-28
     */
    WrapperResponse<Map<String, Object>> queryReportDetails(Map<String, Object> map);

    /**
     * @Method queryTransfInfo
     * @Desrciption 转院信息查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/23 12:47
     * @Return
     **/
    WrapperResponse<Map<String, Object>> queryTransfInfo(Map<String, Object> map);

    /**
     * @Method queryFixRecordInfo
     * @Desrciption 人员定点备案信息查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/23 12:47
     * @Return
     **/
    WrapperResponse<Map<String, Object>> queryFixRecordInfo(Map<String, Object> map);

    /**
     * @Method queryFixRecordInfo
     * @Desrciption 人员慢特病备案信息查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/4/23 12:47
     * @Return
     **/
    WrapperResponse<Map<String, Object>> querySpecialRecord(Map<String, Object> map);

    /**
     * @Method queryMzSpecialLimitPrice
     * @Desrciption  门诊特病限额使用情况查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/6/8 14:09
     * @Return
     **/
    WrapperResponse<Map<String, Object>> queryMzSpecialLimitPrice(Map<String, Object> map);


    /**
     * @Method updateUnifiedDeptInfo
     * @Desrciption
     * @Param
     *
     * @Author fuhui
     * @Date   2021/6/10 15:00
     * @Return
     **/
    WrapperResponse<Map<String, Object>> updateUnifiedDeptInfo(Map<String, Object> map);

    /**
     * @Method updateUnifiedDept
     * @Desrciption  科室信息上传
     * @Param
     *
     * @Author fuhui
     * @Date   2021/6/10 14:45
     * @Return
     **/
    WrapperResponse<Map<String, Object>> updateUnifiedDept(Map<String, Object> map);

    /**
     * @Method deleteUnifiedDeptInfo
     * @Desrciption  撤销科室信息。
     * @Param
     *
     * @Author fuhui
     * @Date   2021/6/10 15:02
     * @Return
     **/
    WrapperResponse<Map<String, Object>> deleteUnifiedDeptInfo(Map<String, Object> map);

    /**
     * @Method queryInform
     * @Desrciption  告知单查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/9/6 15:35
     * @Return
    **/
    WrapperResponse<Map<String, Object>> queryInform(Map<String, Object> map);


    /**
     * @Method updateInsureInptRegisterStatus
     * @Desrciption  更新医保登记状态（医保已经登记 而his没有登记）
     * @Param
     *
     * @Author fuhui
     * @Date   2021/10/8 8:33
     * @Return
     **/
    WrapperResponse<Boolean> updateInsureInptRegisterStatus(Map<String, Object> map);

    /**
     * @Method updateInsureInptSettleStatus
     * @Desrciption  更新医保结算状态（医保已经结算 而his没有结算）
     * @Param
     *
     * @Author fuhui
     * @Date   2021/10/8 10:07
     * @Return
     **/
    WrapperResponse<Boolean> updateInsureInptSettleStatus(Map<String, Object> map);

    /**
     * @Method updateInsureInptCancelSettleStatus
     * @Desrciption  同步取消结算状态  his和医保
     * @Param
     *
     * @Author fuhui
     * @Date   2021/10/8 10:21
     * @Return
     **/
    WrapperResponse<Boolean> updateInsureInptCancelSettleStatus(Map<String, Object> map);

    /**
     * @Method updateInptPatientCode
     * @Desrciption  修改病人类型
     * @Param
     *
     * @Author fuhui
     * @Date   2021/10/8 10:21
     * @Return
     **/
    WrapperResponse<Boolean> updateInptPatientCode(Map<String, Object> map);
    
    /**
     * @Method 查询住院医保病人类型
     * @Desrciption  
     * @Param 
     * 
     * @Author fuhui
     * @Date   2021/10/9 9:42 
     * @Return 
    **/
    WrapperResponse<PageDTO> queryInptInsurePatient(Map<String,Object>map);


    WrapperResponse<Map<String,Object>> querySettleDeInfo(Map<String, Object> map);

    /**
     * 结算单下载使用
     * @param map map
     * @return
     */
    WrapperResponse<Map<String,Object>> querySettleDeInfoBySettleId(Map<String, Object> map);

    /**
     * @Method queryPolicyInfo
     * @Desrciption  政策项查询
     * @Param
     *
     * @Author fuhui
     * @Date   2021/10/23 15:47
     * @Return
    **/
    WrapperResponse<Map<String,Object>> queryPolicyInfo(Map<String, Object> map);

    /**
     * @Method checkOneSettle
     * @Desrciption 判读是否打印一站式结算单
     * @Param map insureIndividualVisitDTO
     *
     * @Author fuhui
     * @Date   2021/10/23 15:20
     * @Return
     **/
    WrapperResponse<Map<String,Object>> checkOneSettle(Map<String, Object> map);

    /**
     * @Method queryBalanceCount
     * @Desrciption  6.3.1.3个人账户扣减
     * @Param
     *
     * @Author fuhui
     * @Date   2022/3/15 15:33
     * @Return
     **/
    WrapperResponse<Map<String, Object>> updateBalanceCountDecrease(Map<String, Object> map);

    /**
     * @Method queryBalanceCount
     * @Desrciption  6.3.1.3账户余额信息查询
     * @Param
     *
     * @Author fuhui
     * @Date   2022/3/15 15:33
     * @Return
     **/
    WrapperResponse<Map<String, Object>> queryBalanceCount(Map<String, Object> map);
}
