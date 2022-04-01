package cn.hsa.module.insure.emr.bo;

import cn.hsa.base.PageDTO;
import cn.hsa.module.insure.emr.dto.*;
import cn.hsa.hsaf.core.framework.web.WrapperResponse;
import cn.hsa.module.insure.emr.dto.InsureEmrDetailDTO;
import cn.hsa.module.insure.emr.dto.InsureEmrRescinfoDTO;
import cn.hsa.module.insure.emr.dto.InsureEmrUnifiedDTO;
import cn.hsa.module.insure.module.dto.InsureIndividualSettleDTO;

import java.util.List;
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
     * @Param insureEmrUnifiedDTO
     * @Author liuhuiming
     * @Date   2022/3/25 10:03
     * @Return
     **/
    PageDTO queryInsureUnifiedEmrInfo(InsureEmrUnifiedDTO insureEmrUnifiedDTO);


    void updateInsureUnifiedEmrUpload(InsureEmrUnifiedDTO insureEmrUnifiedDTO);

    /**
     * @Method queryInsureUnifiedEmrDetail
     * @Desrciption  电子病历上传-患者详情查询
     * @Param insureEmrUnifiedDTO
     * @Author liuhuiming
     * @Date   2022/3/25 10:03
     * @Return
     **/
    InsureEmrDetailDTO queryInsureUnifiedEmrDetail(InsureEmrUnifiedDTO insureEmrUnifiedDTO);

    void updateInsureUnifiedEmrSync(InsureEmrUnifiedDTO insureEmrUnifiedDTO);

    /**
     * @Method queryInsureUnifiedEmrDetail
     * @Desrciption  电子病历上传-患者基本信息修改
     * @Param insureEmrAdminfoDTO
     * @Author liuhuiming
     * @Date   2022/3/25 10:03
     * @Return
     **/
    InsureEmrAdminfoDTO updateInsureUnifiedEmrAdminfo(InsureEmrAdminfoDTO insureEmrAdminfoDTO);

    /**
     * @Method queryInsureUnifiedEmrDetail
     * @Desrciption  电子病历上传-患者诊断信息修改
     * @Param insureEmrDiseinfoDTO
     * @Author liuhuiming
     * @Date   2022/3/25 10:03
     * @Return
     **/
    InsureEmrDiseinfoDTO updateInsureUnifiedEmrDiseinfo(InsureEmrDiseinfoDTO insureEmrDiseinfoDTO);

    /**
     * @Method queryInsureUnifiedEmrDetail
     * @Desrciption  电子病历上传-患者病程信息修改
     * @Param insureEmrDiseinfoDTO
     * @Author liuhuiming
     * @Date   2022/3/25 10:03
     * @Return
     **/
    InsureEmrCoursrinfoDTO updateInsureUnifiedEmrCoursrinfo(InsureEmrCoursrinfoDTO insureEmrCoursrinfoDTO);

    /**
     * @Method queryInsureUnifiedEmrDetail
     * @Desrciption  电子病历上传-患者手术信息修改
     * @Param insureEmrDiseinfoDTO
     * @Author liuhuiming
     * @Date   2022/3/25 10:03
     * @Return
     **/
    InsureEmrOprninfoDTO updateInsureUnifiedEmrOprninfo(InsureEmrOprninfoDTO insureEmrOprninfoDTO);

    /**
     * @Method queryInsureUnifiedEmrDetail
     * @Desrciption  电子病历上传-患者病情抢救信息修改
     * @Param insureEmrDiseinfoDTO
     * @Author liuhuiming
     * @Date   2022/3/25 10:03
     * @Return
     **/
    InsureEmrRescinfoDTO updateInsureUnifiedEmrRescinfo(InsureEmrRescinfoDTO insureEmrRescinfoDTO);

    /**
     * @Method queryInsureUnifiedEmrDetail
     * @Desrciption  电子病历上传-患者死亡信息修改
     * @Param insureEmrDiseinfoDTO
     * @Author liuhuiming
     * @Date   2022/3/25 10:03
     * @Return
     **/
    InsureEmrDieinfoDTO updateInsureUnifiedEmrDieinfo(InsureEmrDieinfoDTO insureEmrDieinfoDTO);

    /**
     * @Method queryInsureUnifiedEmrDetail
     * @Desrciption  电子病历上传-患者出院信息修改
     * @Param insureEmrDiseinfoDTO
     * @Author liuhuiming
     * @Date   2022/3/25 10:03
     * @Return
     **/
    InsureEmrDscginfoDTO updateInsureUnifiedEmrDscginfo(InsureEmrDscginfoDTO insureEmrDscginfoDTO);

    /**
     * @Method queryInsureUnifiedEmrDetail
     * @Desrciption  电子病历上传-导出
     * @Param insureEmrUnifiedDTO
     * @Author liuhuiming
     * @Date   2022/3/25 10:03
     * @Return
     **/
    List<InsureEmrUnifiedDTO> export(InsureEmrUnifiedDTO insureEmrUnifiedDTO);

    List<InsureEmrDscginfoDTO> queryInsureUnifiedEmrDscginfo(InsureEmrUnifiedDTO insureEmrUnifiedDTO);

    List<InsureEmrDieinfoDTO> queryInsureUnifiedEmrDieinfo(InsureEmrUnifiedDTO insureEmrUnifiedDTO);

    List<InsureEmrRescinfoDTO> queryInsureUnifiedEmrRescinfo(InsureEmrUnifiedDTO insureEmrUnifiedDTO);

    List<InsureEmrOprninfoDTO> queryInsureUnifiedEmrOprninfo(InsureEmrUnifiedDTO insureEmrUnifiedDTO);

    List<InsureEmrCoursrinfoDTO> queryInsureUnifiedEmrCoursrinfo(InsureEmrUnifiedDTO insureEmrUnifiedDTO);

    List<InsureEmrDiseinfoDTO> queryInsureUnifiedEmrDiseinfo(InsureEmrUnifiedDTO insureEmrUnifiedDTO);

    InsureEmrAdminfoDTO queryInsureUnifiedEmrAdminfo(InsureEmrUnifiedDTO insureEmrUnifiedDTO);
}
