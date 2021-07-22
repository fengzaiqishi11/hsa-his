package cn.hsa.module.inpt.individualInpatient.bo;


import cn.hsa.module.inpt.individualInpatient.dto.InsureIndividualInpatientDTO;

public interface InsureIndividualInpatientBO {

    /**
     * @Method getIndividualInpatient
     * @Param [map]
     * @description   获取住院业务信息
     * @author marong
     * @date 2020/11/5 15:08
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.insure.insureIndividualInpatient.dto.InsureIndividualInpatientDTO>
     * @throws
     */
    InsureIndividualInpatientDTO getIndividualInpatient(InsureIndividualInpatientDTO insureIndividualInpatientDTO);

    /**
     * @Method getIndividualInpatientByRemote
     * @Param [map]
     * @description   获取异地住院业务信息
     * @author marong
     * @date 2020/11/5 15:10
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.insure.insureIndividualInpatient.dto.InsureIndividualInpatientDTO>
     * @throws
     */
    InsureIndividualInpatientDTO getIndividualInpatientByRemote(InsureIndividualInpatientDTO insureIndividualInpatientDTO);
}
