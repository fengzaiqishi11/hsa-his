package cn.hsa.module.inpt.individualInpatient.service;


import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.inpt.individualInpatient.dto.InsureIndividualInpatientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(value = "hsa-insure")
public interface InsureIndividualInpatientService {

    /**
    * @Method getIndividualInpatient
    * @Param [map]
    * @description   获取住院业务信息
    * @author marong
    * @date 2020/11/5 15:08
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.insure.insureIndividualInpatient.dto.InsureIndividualInpatientDTO>
    * @throws
    */
    @PostMapping("/service/insure/insureIndividualInpatient/getIndividualInpatient")
    WrapperResponse<InsureIndividualInpatientDTO> getIndividualInpatient(Map map);

    /**
    * @Method getIndividualInpatientByRemote
    * @Param [map]
    * @description   获取异地住院业务信息
    * @author marong
    * @date 2020/11/5 15:10
    * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.insure.insureIndividualInpatient.dto.InsureIndividualInpatientDTO>
    * @throws
    */
    @PostMapping("/service/insure/insureIndividualInpatient/getIndividualInpatientByRemote")
    WrapperResponse<InsureIndividualInpatientDTO> getIndividualInpatientByRemote(Map map);
}
