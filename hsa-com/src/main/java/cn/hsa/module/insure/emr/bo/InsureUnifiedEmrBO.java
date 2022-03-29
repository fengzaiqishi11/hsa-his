package cn.hsa.module.insure.emr.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.emr.dto.InsureEmrDetailDTO;
import cn.hsa.module.insure.emr.dto.InsureEmrRescinfoDTO;
import cn.hsa.module.insure.emr.dto.InsureEmrUnifiedDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualSettleDTO;

import java.util.Map;

/**
* @ClassName InsureEmrRescinfoBO
* @Deacription 医保电子病历上传
* @Author liuhuiming
* @Date 2022-03-25
* @Version 1.0
**/
public interface InsureUnifiedEmrBO {

    /**
     * @Method queryInsureUnifiedEmrInfo
     * @Desrciption  电子病历上传-患者列表查询
     * @Param insureIndividualSettleDTO
     * @Author liuhuiming
     * @Date   2022/3/25 10:03
     * @Return
     **/
    PageDTO queryInsureUnifiedEmrInfo(InsureEmrUnifiedDTO insureEmrUnifiedDTO);


    void updateInsureUnifiedEmrUpload(InsureEmrUnifiedDTO insureEmrUnifiedDTO);

    /**
     * @Method queryInsureUnifiedEmrInfo
     * @Desrciption  电子病历上传-患者详情查询
     * @Param insureIndividualSettleDTO
     * @Author liuhuiming
     * @Date   2022/3/25 10:03
     * @Return
     **/
    InsureEmrDetailDTO queryInsureUnifiedEmrDetail(InsureEmrUnifiedDTO insureEmrUnifiedDTO);

    void updateInsureUnifiedEmrSync(InsureEmrUnifiedDTO insureEmrUnifiedDTO,Map<String, Object> emrMap);
}
