package cn.hsa.module.insure.emr.dto;


import java.io.Serializable;
import java.util.List;

import cn.hsa.base.PageDO;
import lombok.Data;

/**
 * @ClassName InsureEmrUnifiedDTO
 * @Deacription 医保电子病历上传dto层
 * @Author liuhuiming
 * @Date 2022-03-25
 * @Version 1.0
 **/
@Data
public class InsureEmrDetailDTO implements Serializable {

    /**
     * 入院记录
     */
    private InsureEmrAdminfoDTO insureEmrAdminfoDTO;

    /**
     * 病程记录
     */
    private List<InsureEmrCoursrinfoDTO> insureEmrCoursrinfoDTOList;

    /**
     * 死亡记录
     */
    private List<InsureEmrDieinfoDTO> insureEmrDieinfoDTOList;

    /**
     * 诊断信息
     */
    private List<InsureEmrDiseinfoDTO> insureEmrDiseinfoDTOList;

    /**
     * 出院记录
     */
    private List<InsureEmrDscginfoDTO> insureEmrDscginfoDTOList;

    /**
     * 手术记录
     */
    private List<InsureEmrOprninfoDTO> insureEmrOprninfoDTOList;

    /**
     * 病情抢救记录
     */
    private List<InsureEmrRescinfoDTO> insureEmrRescinfoDTOList;
}
