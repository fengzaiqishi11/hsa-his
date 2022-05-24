package cn.hsa.insure.unifiedpay.service.impl;


import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.bo.InsureSpecialAttributeBO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import cn.hsa.module.insure.module.service.InsureSpecialAttributeService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @Package_name:
 * @class_name: InsureSpecialAttributeServiceImpl
 * @Description: TODO
 * @Author: yuelong.chen
 * @Date: 2022/5/16 21:22
 * @Company: 创智和宇
 **/
@Slf4j
@HsafRestPath("/service/insure/insureSpecialAttribute")
@Service("insureSpecialAttributeService_provider")
public class InsureSpecialAttributeServiceImpl implements InsureSpecialAttributeService {

    @Resource
    private InsureSpecialAttributeBO insureSpecialAttributeBO;
    /**
     * @Method queryInsureUnifiedEmrInfo
     * @Desrciption  就医特殊属性上传病人查询
     * @Param map
     * @Author yuelong.chen
     * @Date   2022/5/15 10:03
     * @Return
     *
     * @return*/
    @Override
    public WrapperResponse<PageDTO> queryInsureSpecialAttributeInfoPage(Map<String, Object> map) {
        return WrapperResponse.success(insureSpecialAttributeBO.queryInsureSpecialAttributeInfoPage(map));
    }
    /**
     * @Method uploadInsureSpecialAttribute
     * @Desrciption  就医特殊属性上传
     * @Param map
     * @Author yuelong.chen
     * @Date   2022/5/15 10:03
     * @Return
     **/
    @Override
    public void UPloadInsureSpecialAttribute(Map map) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map,"insureIndividualVisitDTO");
        insureSpecialAttributeBO.UPloadInsureSpecialAttribute(insureIndividualVisitDTO);
    }

    @Override
    public WrapperResponse<List<Map<String, Object>>> qureyInsureSpecialAttribute(Map map) {
        InsureIndividualVisitDTO insureIndividualVisitDTO = MapUtils.get(map,"insureIndividualVisitDTO");
        return WrapperResponse.success(insureSpecialAttributeBO.qureyInsureSpecialAttribute(insureIndividualVisitDTO));
    }
}
