package cn.hsa.insure.unifiedpay.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
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
    public WrapperResponse<Map<String, Object>> querySettleInfo(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedBaseBO.querySettleInfo(map));
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
    public WrapperResponse<Map<String, Object>> queryPatientInfo(Map<String, Object> map) {
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

}
