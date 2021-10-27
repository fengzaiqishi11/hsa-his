package cn.hsa.insure.unifiedpay.service.impl;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.bo.InsureUnifiedLogBO;
import cn.hsa.module.insure.module.service.InsureUnifiedLogService;
import cn.hsa.module.insure.module.entity.InsureFunctionLogDO;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.service.impl
 * @class_name: InsureUnifiedLogServiceImpl
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/10/11 13:36
 * @Company: 创智和宇
 **/
@Slf4j
@HsafRestPath("/service/insure/insureUnifiedLogService")
@Service("insureUnifiedLogService_provider")
public class InsureUnifiedLogServiceImpl extends HsafService implements InsureUnifiedLogService {

    @Resource
    private InsureUnifiedLogBO insureUnifiedLogBO;

    /**
     * @Method queryPage
     * @Desrciption  分页查询所有调用医保接口的日志信息
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/10/11 13:44
     * @Return
    **/
    @Override
    public WrapperResponse<PageDTO> queryPage(Map<String,Object> map) {
        InsureFunctionLogDO insureFunctionLogDO = MapUtils.get(map,"insureFunctionLogDO");
        return WrapperResponse.success(insureUnifiedLogBO.queryPage(insureFunctionLogDO));
    }

    /**
     * @Method insertInsureFunctionLog
     * @Desrciption  保存调用医保接口的日志信息
     * @Param map
     *
     * @Author fuhui
     * @Date   2021/10/11 13:44
     * @Return
     **/
    @Override
    public WrapperResponse<Boolean> insertInsureFunctionLog(Map<String,Object> map) {
        return WrapperResponse.success(insureUnifiedLogBO.insertInsureFunctionLog(map));
    }
}
