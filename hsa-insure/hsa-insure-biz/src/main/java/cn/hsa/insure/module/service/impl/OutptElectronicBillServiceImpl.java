package cn.hsa.insure.module.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.emd.bo.OutptElectronicBillBO;
import cn.hsa.module.insure.emd.service.OutptElectronicBillService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.module.service.impl
 * @Class_name: OutptElectronicBillServiceImpl
 * @Describe(描述): 门诊电子凭证ServiceImpl
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2021/3/4 14:38
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/insure/outptElectronicBill")
@Service("outptElectronicBillService_provider")
public class OutptElectronicBillServiceImpl extends HsafService implements OutptElectronicBillService {

    @Resource
    private OutptElectronicBillBO outptElectronicBillBO;

    /**
     * @Menthod getPatientInfo
     * @Desrciption 门诊电子凭证获取个人信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/3/4 15:00
     * @return: WrapperResponse
     */
    @Override
    public WrapperResponse getPatientInfo(Map<String, Object> param) {
        return outptElectronicBillBO.getPatientInfo(param);
    }

    /**
     * @Menthod addPatientCost
     * @Desrciption 门诊电子凭证患者费用上传
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/3/4 15:18
     * @return: WrapperResponse
     */
    @Override
    public WrapperResponse addPatientCost(Map<String, Object> param) {
        return outptElectronicBillBO.addPatientCost(param);
    }

    /**
     * @Menthod deletePatientCost
     * @Desrciption 门诊电子凭证撤销费用明细上传
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/3/4 15:36
     * @return: WrapperResponse
     */
    @Override
    public WrapperResponse deletePatientCost(Map<String, Object> param) {
        return outptElectronicBillBO.deletePatientCost(param);
    }

    /**
     * @Menthod deletePatientCostPremium
     * @Desrciption 门诊电子凭证费用退费
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/3/4 15:39
     * @return: WrapperResponse
     */
    @Override
    public WrapperResponse deletePatientCostPremium(Map<String, Object> param) {
        return outptElectronicBillBO.deletePatientCostPremium(param);
    }

    /**
     * @Menthod getPayEffect
     * @Desrciption 门诊电子凭证获取支付结果
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/3/4 15:53
     * @return: WrapperResponse
     */
    @Override
    public WrapperResponse getPayEffect(Map<String, Object> param) {
        return outptElectronicBillBO.getPayEffect(param);
    }
}
