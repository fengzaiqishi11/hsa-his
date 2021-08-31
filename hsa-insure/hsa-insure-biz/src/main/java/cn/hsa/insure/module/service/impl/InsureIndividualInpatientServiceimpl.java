package cn.hsa.insure.module.service.impl;


import cn.hsa.hsaf.core.framework.HsafService;
import cn.hsa.hsaf.core.framework.web.HsafRestPath;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.hsaf.core.framework.web.exception.AppException;
import cn.hsa.module.inpt.individualInpatient.bo.InsureIndividualInpatientBO;
import cn.hsa.module.inpt.individualInpatient.dto.InsureIndividualInpatientDTO;
import cn.hsa.module.inpt.individualInpatient.service.InsureIndividualInpatientService;
import cn.hsa.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@HsafRestPath("/service/insure/insureIndividualInpatient")
@Service("insureIndividualInpatientService_provider")
public class InsureIndividualInpatientServiceimpl   extends HsafService implements InsureIndividualInpatientService {

    @Resource
    private InsureIndividualInpatientBO insureIndividualInpatientBO;

    /**
     * @Method getIndividualInpatient
     * @Param [map]
     * @description   获取住院业务信息
     * @author marong
     * @date 2020/11/5 15:08
     * @return cn.hsa.hsaf.core.framework.web.WrapperResponse<cn.hsa.module.insure.insureIndividualInpatient.dto.InsureIndividualInpatientDTO>
     * @throws
     */
    @Override
    public WrapperResponse<InsureIndividualInpatientDTO> getIndividualInpatient(Map map) {
        InsureIndividualInpatientDTO insureIndividualInpatientDTO = insureIndividualInpatientBO.getIndividualInpatient(MapUtils.get(map, "insureIndividualInpatientDTO"));

        if(!"0".equals(MapUtils.get(insureIndividualInpatientDTO.getResultMap(),"code","-1"))){
            throw new AppException("获取业务信息错误");
        }
        return WrapperResponse.success(insureIndividualInpatientDTO);
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
    public WrapperResponse<InsureIndividualInpatientDTO> getIndividualInpatientByRemote(Map map) {
        InsureIndividualInpatientDTO insureIndividualInpatientDTO = insureIndividualInpatientBO.getIndividualInpatientByRemote(MapUtils.get(map, "insureIndividualInpatientDTO"));

        if(!"0".equals(MapUtils.get(insureIndividualInpatientDTO.getResultMap(),"code","-1"))){
            throw new AppException("获取业务信息错误");
        }
        return WrapperResponse.success(insureIndividualInpatientDTO);
    }

}
