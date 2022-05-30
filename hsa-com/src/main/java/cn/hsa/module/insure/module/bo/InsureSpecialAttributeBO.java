package cn.hsa.module.insure.module.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualVisitDTO;

import java.util.List;
import java.util.Map;

/**
 * @Description 就医特殊属性服务service
 * @ClassName InsureSpecialAttributeService
 * @Author yuelong.chen
 * @Date 2022/5/9 13:39
 * @Version 1.0
 **/
public interface InsureSpecialAttributeBO {

    /**
     * @Method queryInsureUnifiedEmrInfo
     * @Desrciption  就医特殊属性上传病人查询
     * @Param map
     * @Author yuelong.chen
     * @Date   2022/5/15 10:03
     * @Return
     *
     * @return*/
    PageDTO queryInsureSpecialAttributeInfoPage(Map map);

    /**
     * @Method uploadInsureSpecialAttribute
     * @Desrciption  就医特殊属性上传
     * @Param map
     * @Author yuelong.chen
     * @Date   2022/5/15 10:03
     * @Return
     **/
    void UPloadInsureSpecialAttribute(InsureIndividualVisitDTO insureIndividualVisitDTO);


    List<Map<String, Object>> qureyInsureSpecialAttribute(InsureIndividualVisitDTO insureIndividualVisitDTO);
}
