package cn.hsa.module.insure.module.service;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;
import java.util.Map;

/**
 * @Description 就医特殊属性服务service
 * @ClassName InsureSpecialAttributeService
 * @Author yuelong.chen
 * @Date 2022/5/9 13:39
 * @Version 1.0
 **/
@FeignClient(value = "hsa-insure")
public interface InsureSpecialAttributeService {
    /**
     * @Method queryInsureUnifiedEmrInfo
     * @Desrciption  就医特殊属性上传病人查询
     * @Param map
     * @Author yuelong.chen
     * @Date   2022/5/15 10:03
     * @Return
     *
     * @return*/
    WrapperResponse<PageDTO> queryInsureSpecialAttributeInfoPage(Map<String,Object> map);

    /**
     * @Method uploadInsureSpecialAttribute
     * @Desrciption  就医特殊属性上传
     * @Param map
     * @Author yuelong.chen
     * @Date   2022/5/15 10:03
     * @Return
     **/
    void uploadInsureSpecialAttribute(Map map);

    WrapperResponse<PageDTO> qureyInsureSpecialAttribute(Map map);
}
