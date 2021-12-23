package cn.hsa.insure.unifiedpay.service.impl;

import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.outpt.bo.InsureUnifiedUniversityBO;
import cn.hsa.module.insure.outpt.service.InsureUnifiedUniversityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Package_name: cn.hsa.insure.unifiedpay.service.impl
 * @class_name: InsureUnifiedUniversityServiceImpl
 * @Description: TODO
 * @Author: fuhui
 * @Email: 3277857701@qq.com
 * @Date: 2021/12/13 16:06
 * @Company: 创智和宇
 **/
@Slf4j
@HsafRestPath("/service/insure/insureUnifiedUniversity")
@Service("insureUnifiedUniversityService_provider")
public class InsureUnifiedUniversityServiceImpl extends HsafService implements InsureUnifiedUniversityService {

    @Resource
    private InsureUnifiedUniversityBO insureUnifiedUniversityBO;
    /**
     * @param map
     * @Method insertUniversityInsure
     * @Desrciption 大学生医保单独结算
     * @Param map：封装包含就诊id：visitId  settleId:结算id
     * @Author fuhui
     * @Date 2021/12/13 16:13
     * @Return
     */
    @Override
    public WrapperResponse<Boolean> insertUniversityInsure(Map<String, Object> map) {
        return WrapperResponse.success(insureUnifiedUniversityBO.insertUniversityInsure(map));
    }
}
