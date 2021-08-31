package cn.hsa.insure.module.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.emd.bo.InptElectronicBillBO;
import cn.hsa.module.insure.emd.service.InptElectronicBillService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.module.service.impl
 * @Class_name: InptElectronicBillServiceImpl
 * @Describe(描述): 住院电子凭证ServiceImpl
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2021/3/4 14:32
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/insure/inptElectronicBill")
@Service("inptElectronicBillService_provider")
public class InptElectronicBillServiceImpl extends HsafService implements InptElectronicBillService {

    @Resource
    private InptElectronicBillBO inptElectronicBillBO;

    /**
     * @Menthod getPatientInfo
     * @Desrciption 住院电子凭证获取个人信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/3/4 15:00
     * @return: WrapperResponse
     */
    @Override
    public WrapperResponse getPatientInfo(Map<String, Object> param) {
        return inptElectronicBillBO.getPatientInfo(param);
    }

    /**
     * @Menthod addPatientCost
     * @Desrciption 住院电子凭证患者费用上传
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/3/4 15:18
     * @return: WrapperResponse
     */
    @Override
    public WrapperResponse addPatientCost(Map<String, Object> param) {
        return inptElectronicBillBO.addPatientCost(param);
    }

    /**
     * @Menthod deletePatientCost
     * @Desrciption 住院电子凭证撤销费用明细上传
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/3/4 15:36
     * @return: WrapperResponse
     */
    @Override
    public WrapperResponse deletePatientCost(Map<String, Object> param) {
        return inptElectronicBillBO.deletePatientCost(param);
    }

    /**
     * @Menthod deletePatientCostPremium
     * @Desrciption 住院电子凭证费用退费
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/3/4 15:39
     * @return: WrapperResponse
     */
    @Override
    public WrapperResponse deletePatientCostPremium(Map<String, Object> param) {
        return inptElectronicBillBO.deletePatientCostPremium(param);
    }

    /**
     * @Menthod getPayEffect
     * @Desrciption 住院电子凭证获取支付结果
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2021/3/4 15:53
     * @return: WrapperResponse
     */
    @Override
    public WrapperResponse getPayEffect(Map<String, Object> param) {
        return inptElectronicBillBO.getPayEffect(param);
    }
}
