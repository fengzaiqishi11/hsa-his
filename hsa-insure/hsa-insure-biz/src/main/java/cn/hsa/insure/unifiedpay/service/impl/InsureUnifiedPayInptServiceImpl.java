package cn.hsa.insure.unifiedpay.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.inpt.bo.InsureUnifiedPayInptBO;
import cn.hsa.module.insure.inpt.service.InsureUnifiedPayInptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.service.impl
 * @class_name: InsureUnifiedPayInptServiceImpl
 * @Description: 医保统一支付平台：住院业务模块服务层实现类创建
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/2/10 9:11
 * @Company: 创智和宇
 **/
@Slf4j
@HsafRestPath("/service/insure/insureUnifiedPayInpt")
@Service("insureUnifiedPayInptService_provider")
public class InsureUnifiedPayInptServiceImpl extends HsafService implements InsureUnifiedPayInptService {

    @Resource
    private InsureUnifiedPayInptBO insureUnifiedPayInptBO;

    /**
     * @param map
     * @Method saveInptFeeTransmit
     * @Desrciption 医保统一支付平台：住院业务模块--住院费用传输
     * @Param map
     * @Author fuhui
     * @Date 2021/2/10 9:52
     * @Return boolean
     */
    @Override
    public WrapperResponse<Map<String,Object>> UP_2301(Map<String,Object> map) {

        return WrapperResponse.success(insureUnifiedPayInptBO.UP_2301(map));
    }

    /**
     * @param insureUnifiedMap
     * @Method UP_2302
     * @Desrciption 住院费用明细撤销
     * @Param insureUnifiedMap
     * @Author fuhui
     * @Date 2021/2/24 14:43
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> UP_2302(Map<String, Object> insureUnifiedMap) {
        return WrapperResponse.success(insureUnifiedPayInptBO.UP_2302(insureUnifiedMap));
    }

    /**
     * @param unifiedMap
     * @Method UP_2303
     * @Desrciption 住院预结算
     * @Param unifiedMap
     * @Author fuhui
     * @Date 2021/2/25 10:32
     * @Return map
     */
    @Override
    public WrapperResponse<Map<String, String>> UP_2303(Map<String, Object> unifiedMap) {
        return  WrapperResponse.success(insureUnifiedPayInptBO.UP_2303(unifiedMap));
    }

    /**
     * @param settleMap
     * @Method UP_2304
     * @Desrciption 住院结算
     * @Param settleMap
     * @Author fuhui
     * @Date 2021/2/25 11:04
     * @Return map
     */
    @Override
    public WrapperResponse<Map<String, String>> UP_2304(Map<String, Object> settleMap) {
        return WrapperResponse.success(insureUnifiedPayInptBO.UP_2304(settleMap));
    }

    /**
     * @Menthod: UP_2305
     * @Desrciption: 统一支付平台-住院结算撤销
     * @Param: 节点标识-flag-节点标识：data
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-02-10 11:00
     * @Return:
     **/
    @Override
    public Map<String,Object> UP_2305(Map<String,Object> insureUnifiedMap) {
        return insureUnifiedPayInptBO.UP_2305(insureUnifiedMap);
    }

    /**
     * @param map
     * @return
     * @Author pengbo
     * @Description：住院管理-入院办理
     * @Date 17:05 2021-02-20
     * @Param
     */
    @Override
    public Map<String, Object> UP_2401(Map<String, Object> map) {
        return insureUnifiedPayInptBO.UP_2401(map);
    }

    /**
     * @param map
     * @return
     * @Author pengbo
     * @Description：住院管理-出院办理
     * @Date 2021-02-20 17:11
     * @Param
     */
    @Override
    public Map<String, Object> UP_2402(Map<String, Object> map) {
        return insureUnifiedPayInptBO.UP_2402(map);
    }

    /**
     * @param map
     * @return
     * @Author pengbo
     * @Description：住院管理-住院信息变更
     * @Date 2021-02-20 17:11
     * @Param
     */
    @Override
    public WrapperResponse<Boolean> UP_2403(Map<String, Object> map)
    {
        return WrapperResponse.success(insureUnifiedPayInptBO.UP_2403(map));
    }

    /**
     * @param map
     * @return
     * @Author pengbo
     * @Description：住院管理-入院办理撤销
     * @Date 2021-02-20 17:11
     * @Param
     */
    @Override
    public Map<String, Object> UP_2404(Map<String, Object> map) {
        return insureUnifiedPayInptBO.UP_2404(map);
    }

    /**
     * @param map
     * @return
     * @Author pengbo
     * @Description：住院管理-出院办理撤销
     * @Date 2021-02-20 17:11
     * @Param
     */
    @Override
    public Map<String, Object> UP_2405(Map<String, Object> map) {
        return insureUnifiedPayInptBO.UP_2405(map);
    }


    /**
     * @Menthod: UP_4602
     * @Desrciption: 统一支付平台-护理操作生命体征测量记录【4602】
     * @Param: visitId-就诊id
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-08-21 16:22
     * @Return:
     **/
    @Override
    public WrapperResponse<Map<String, Object>> UP_4602(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedPayInptBO.UP4602(map));
    }

    /**
     * 【2001】人员待遇享受检查
     * @param insureUnifiedMap
     * @Author 医保开发二部-湛康
     * @Date 2022-07-13 8:58
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    @Override
    public Map<String, Object> UP_2001(Map<String, Object> insureUnifiedMap) {
      return insureUnifiedPayInptBO.UP_2001(insureUnifiedMap);
    }
}
