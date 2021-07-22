package cn.hsa.insure.module.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.insure.util.Constant;
import cn.hsa.insure.util.Transpond;
import cn.hsa.module.insure.emd.bo.OutptElectronicBillBO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.module.bo.impl
 * @Class_name: OutptElectronicBillBOImpl
 * @Describe(描述): 门诊电子凭证BoImpl
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2021/3/4 14:28
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@Component
public class OutptElectronicBillBOImpl extends HsafBO implements OutptElectronicBillBO {

    @Resource
    private Transpond transpond;

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
        String hospCode = (String) param.get("hospCode");//医院编码
        String insureRegCode = (String) param.get("insureRegCode");//医保注册编码
        transpond.to(hospCode,insureRegCode, Constant.FUNCTION.FC_EMD_11001,param);
        return null;
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
        String hospCode = (String) param.get("hospCode");//医院编码
        String insureRegCode = (String) param.get("insureRegCode");//医保注册编码
        return WrapperResponse.success(transpond.to(hospCode,insureRegCode, Constant.FUNCTION.FC_EMD_11002,param));
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
        String hospCode = (String) param.get("hospCode");//医院编码
        String insureRegCode = (String) param.get("insureRegCode");//医保注册编码
        return WrapperResponse.success(transpond.to(hospCode,insureRegCode, Constant.FUNCTION.FC_EMD_11003,param));
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
        String hospCode = (String) param.get("hospCode");//医院编码
        String insureRegCode = (String) param.get("insureRegCode");//医保注册编码
        return WrapperResponse.success(transpond.to(hospCode,insureRegCode, Constant.FUNCTION.FC_EMD_11004,param));
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
        String hospCode = (String) param.get("hospCode");//医院编码
        String insureRegCode = (String) param.get("insureRegCode");//医保注册编码
        return WrapperResponse.success(transpond.to(hospCode,insureRegCode, Constant.FUNCTION.FC_EMD_11005,param));
    }
}
