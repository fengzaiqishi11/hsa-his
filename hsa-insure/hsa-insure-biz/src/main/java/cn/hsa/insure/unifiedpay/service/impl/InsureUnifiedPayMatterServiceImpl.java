package cn.hsa.insure.unifiedpay.service.impl;

import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.outpt.bo.InsureUnifiedPayMatterBO;
import cn.hsa.module.insure.outpt.service.InsureUnifiedPayMatterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.service.impl
 * @Class_name: InsureUnifiedPayMatterServiceImpl
 * @Describe: 统一支付平台---事前事中分析
 * @Author: luoyong
 * @Eamil: luoyong@powersi.com.cn
 * @Date: 2021-02-24 15:05
 * @Company: CopyRight@2014 POWERSI Inc.All Rights Reserverd
 **/
@Slf4j
@HsafRestPath("/service/insure/insureUnifiedPayMatter")
@Service("insureUnifiedPayMatterService_provider")
public class InsureUnifiedPayMatterServiceImpl implements InsureUnifiedPayMatterService {

    @Resource
    private InsureUnifiedPayMatterBO insureUnifiedPayMatterBO;


    /**
     * @Menthod:
     * @Desrciption: 事前提醒
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-02-24 15:12
     * @Return:
     **/
    @Override
    public WrapperResponse<Map<String, Object>> UP_3660(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedPayMatterBO.UP_3660(map));
    }

    /**
     * @Menthod: UP_3661
     * @Desrciption: 事中预警
     * @Param:
     * @Author: luoyong
     * @Email: luoyong@powersi.com.cn
     * @Date: 2021-02-24 15:12
     * @Return:
     **/
    @Override
    public WrapperResponse<Map<String, Object>> UP_3661(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedPayMatterBO.UP_3661(map));
    }
}
