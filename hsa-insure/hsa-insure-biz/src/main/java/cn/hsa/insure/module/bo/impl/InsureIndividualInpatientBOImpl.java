package cn.hsa.insure.module.bo.impl;

import cn.hsa.hsaf.core.framework.HsafBO;
import cn.hsa.insure.xiangtan.inpt.InptFunction;
import cn.hsa.module.inpt.individualInpatient.bo.InsureIndividualInpatientBO;
import cn.hsa.module.inpt.individualInpatient.dto.InsureIndividualInpatientDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class InsureIndividualInpatientBOImpl  extends HsafBO implements InsureIndividualInpatientBO {


    @Resource
    private InptFunction inptFunction;

    /**
     *
     * @Method getIndividualInpatient
     * @Param [map]
     * @description   获取住院业务信息
     * @author marong
     * @date 2020/11/5 15:08
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.insure.insureIndividualInpatient.dto.InsureIndividualInpatientDTO>
     * @throws
     */
    @Override
    public InsureIndividualInpatientDTO getIndividualInpatient(InsureIndividualInpatientDTO insureIndividualInpatientDTO) {
        return inptFunction.bizh120102(insureIndividualInpatientDTO);
    }

    /**
     * @Method getIndividualInpatientByRemote
     * @Param [map]
     * @description   获取异地住院业务信息
     * @author marong
     * @date 2020/11/5 15:10
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.insure.insureIndividualInpatient.dto.InsureIndividualInpatientDTO>
     * @throws
     */
    @Override
    public InsureIndividualInpatientDTO getIndividualInpatientByRemote(InsureIndividualInpatientDTO insureIndividualInpatientDTO) {
        return inptFunction.remote_bizc131251(insureIndividualInpatientDTO);
    }
}
