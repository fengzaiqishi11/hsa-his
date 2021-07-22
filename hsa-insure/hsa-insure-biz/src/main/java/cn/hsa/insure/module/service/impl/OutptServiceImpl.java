package cn.hsa.insure.module.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.outpt.bo.OutptBo;
import cn.hsa.module.insure.outpt.service.OutptService;
import cn.hsa.util.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.module.service.impl
 * @Class_name: OutptServiceImpl
 * @Describe(描述): 门诊医保开放统一接口 service实现层
 * @Author: Ou·Mr
 * @Eamil: oubo@powersi.com.cn
 * @Date: 2020/11/09 9:11
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 */
@HsafRestPath("/service/insure/outpt")
@Service("outptService_provider")
public class OutptServiceImpl extends HsafService implements OutptService {

    @Resource
    private OutptBo outptBo;

    /**
     * @Method getInsureOutptOutFeeInfo
     * @Desrciption 医保退费时提取门诊业务信息 - 返回医保中心数据
     * @param param
     * @Author 廖继广
     * @Date   2020/10/29 09:56
     * @Return java.util.Map
     **/
    @Override
    public Map<String, Object> getInsureOutptOutFeeInfo(Map<String,Object> param) {
        return outptBo.getInsureOutptOutFeeInfo(param);
    }

    /**
     * @Menthod getOutptVisitInfo
     * @Desrciption 门诊获取医保个人信息
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/10 14:33
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public WrapperResponse getOutptVisitInfo(Map<String, Object> param) {
        return WrapperResponse.success(outptBo.getOutptVisitInfo(param));
    }

    /**
     * @Menthod setOutptCostUpload
     * @Desrciption 门诊费用上传并试算
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/26 10:02
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public Map<String, Object> setOutptCostUploadAndTrial(Map<String, Object> param) {
        return outptBo.setOutptCostUploadAndTrial(param);
    }

    /**
     * @Menthod setOutptCostUploadAndSettlement
     * @Desrciption 门诊费用上传并结算
     * @param param 请求参数
     * @Author Ou·Mr
     * @Date 2020/11/26 10:07
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public Map<String, Object> setOutptCostUploadAndSettlement(Map<String, Object> param) {
        return outptBo.setOutptCostUploadAndSettlement(param);
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
    public Map<String, Object> selectCheckInfo(Map<String, Object> map) {
        return outptBo.selectCheckInfo(map);
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
    public Boolean uploadCheckInfo(Map<String, Object> map) {
        return outptBo.uploadCheckInfo(map);
    }

    /**
     * @param map
     * @Desrciption uploadCheckInfo 门特病人取消结算以后 取消登记
     * @Param map
     * @Author fuhui
     * @Date 2021/2/1 19:28
     * @Return boolean
     */
    @Override
    public Boolean cancelRegister(Map<String, Object> map) {
        return outptBo.cancelRegister(map);
    }


}
